package com.hicorp.segment.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hicorp.segment.mapper.LoginRecordMapper;
import com.hicorp.segment.pojo.LoginRecord;
import com.hicorp.segment.security.filter.MyFilterSecurityInterceptor;
import com.hicorp.segment.security.services.UserDetailServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: wqs
 * @Date: Created in 17:15 2021/5/2
 * @Description:
 * @Modified_By:
 */
@Configuration
@EnableWebSecurity
@Slf4j
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final MyFilterSecurityInterceptor securityInterceptor;

    private final UserDetailServiceImpl userDetailServiceImpl;
    // jackson 解析 JSON 的 Mapper 类，可以从字符串、流或文件解析 JSON ，并创建 Java 对象或对象图来表示已解析的JSON。
    private final ObjectMapper objectMapper;

    @Resource
    private LoginRecordMapper loginRecordMapper;

    public SecurityConfig(UserDetailServiceImpl userDetailServiceImpl, ObjectMapper objectMapper, MyFilterSecurityInterceptor securityInterceptor) {
        this.userDetailServiceImpl = userDetailServiceImpl;
        this.objectMapper = objectMapper;
        this.securityInterceptor = securityInterceptor;
    }

    /**
     * @Author: 91074
     * @Date: Created in 19:45 2021/5/12
     * @Params: [http] [org.springframework.security.config.annotation.web.builders.HttpSecurity]
     * @Return: void
     * @Description:
     * @ChineseDescription: FilterSecurityInterceptor 过滤器是 Spring Security 过滤器链条中的最后一个过滤器。
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .anyRequest().authenticated()
                .and().httpBasic()
                // 使用 lambda 表达式，减少实现类。
                // 配置未登录时的返还提示。
                .authenticationEntryPoint((request, response, exception) -> {
                    response.setContentType("application/json;charset=utf-8");
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    PrintWriter out = response.getWriter();
                    Map<String, Object> map = new HashMap<>();
                    map.put("code", 401);
                    map.put("msg", "未登录!");
                    out.write(objectMapper.writeValueAsString(map));
                    out.flush();
                    out.close();
                })
                .and()
                .formLogin()
                .permitAll()
                // 登录失败
                .failureHandler((request, response, exception) -> {
                    response.setContentType("application/json;charset=utf-8");
//                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    PrintWriter out = response.getWriter();
                    Map<String, Object> map = new HashMap<>();
                    map.put("code", 401);
                    if (exception instanceof UsernameNotFoundException || exception instanceof BadCredentialsException) {
                        map.put("msg", "用户名或密码错误");
                    } else if (exception instanceof DisabledException) {
                        map.put("msg", "账户被禁用");
                    } else {
                        map.put("msg", "登录失败!");
                    }
                    out.write(objectMapper.writeValueAsString(map));
                    out.flush();
                    out.close();
                })
                // 登录成功
                .successHandler((request, response, authentication) -> {
                    WebAuthenticationDetails details = (WebAuthenticationDetails) authentication.getDetails();
                    Date date = new Date();
                    LoginRecord loginRecord = new LoginRecord(authentication.getName(), false, details.getRemoteAddress(), date);
                    recordLogin(loginRecord);
                    Map<String, Object> map = new HashMap<>();
                    map.put("code", 200);
                    map.put("msg", "登录成功");
                    map.put("data", authentication);
                    response.setContentType("application/json;charset=utf-8");
                    PrintWriter out = response.getWriter();
                    out.write(objectMapper.writeValueAsString(map));
                    out.flush();
                    out.close();
                })
                .and()
                .exceptionHandling()
                // 无权限时
                .accessDeniedHandler(((request, response, exception) -> {
                    response.setContentType("application/json;charset=utf-8");
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    PrintWriter out = response.getWriter();
                    Map<String, Object> map = new HashMap<>();
                    map.put("code", 403);
                    map.put("msg", "您没有相应的权限!");
                    out.write(objectMapper.writeValueAsString(map));
                    out.flush();
                    out.close();
                }))
                .and()
                .logout()
                .logoutSuccessHandler((request, response, authentication) -> {
                    Map<String, Object> map = new HashMap<>();
                    if (authentication != null) {
                        WebAuthenticationDetails details = (WebAuthenticationDetails) authentication.getDetails();
                        Date date = new Date();
                        LoginRecord loginRecord = new LoginRecord(authentication.getName(), true, details.getRemoteAddress(), date);
                        recordLogin(loginRecord);
                        map.put("code", 200);
                        map.put("msg", "退出成功");
                        map.put("data", authentication);
                    } else {
                        map.put("code", 401);
                        map.put("msg", "未登录!");
                    }
                    response.setContentType("application/json;charset=utf-8");
                    PrintWriter out = response.getWriter();
                    out.write(objectMapper.writeValueAsString(map));
                    out.flush();
                    out.close();
                })
                .permitAll()
                // 开启跨域
                .and().cors().disable()
                // 跨站请求伪造，开启后，允许 API POST (如:  postman )工具测试。
                .csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint((request, response, exception) -> {
                    response.setContentType("application/json;charset=utf-8");
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    PrintWriter out = response.getWriter();
                    Map<String, Object> map = new HashMap<>();
                    map.put("code", 403);
                    map.put("msg", "未登录!");
                    out.write(objectMapper.writeValueAsString(map));
                    out.flush();
                    out.close();
                })
        ;
        http
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS).maximumSessions(3);
        //增加自定义权限授权拦截器
        http.addFilterBefore(securityInterceptor, FilterSecurityInterceptor.class)
                .csrf().disable();
    }

    private void recordLogin(LoginRecord loginRecord) {
        try {
            loginRecordMapper.insert(loginRecord);
        } catch (Exception e) {
            log.error(e.getMessage());
            // 手动回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
    }

    // 跳过登录验证的接口，Swagger2 接口 API 页面。
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/doc.html")
                .antMatchers("/swagger**/**")
                .antMatchers("/webjars/**")
                .antMatchers("/v3/**");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 修改验证
        auth.userDetailsService(userDetailServiceImpl)
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
