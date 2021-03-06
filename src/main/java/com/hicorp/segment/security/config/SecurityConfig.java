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
    // jackson ?????? JSON ??? Mapper ????????????????????????????????????????????? JSON ???????????? Java ???????????????????????????????????????JSON???
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
     * @ChineseDescription: FilterSecurityInterceptor ???????????? Spring Security ?????????????????????????????????????????????
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .anyRequest().authenticated()
                .and().httpBasic()
                // ?????? lambda ??????????????????????????????
                // ????????????????????????????????????
                .authenticationEntryPoint((request, response, exception) -> {
                    response.setContentType("application/json;charset=utf-8");
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    PrintWriter out = response.getWriter();
                    Map<String, Object> map = new HashMap<>();
                    map.put("code", 401);
                    map.put("msg", "?????????!");
                    out.write(objectMapper.writeValueAsString(map));
                    out.flush();
                    out.close();
                })
                .and()
                .formLogin()
                .permitAll()
                // ????????????
                .failureHandler((request, response, exception) -> {
                    response.setContentType("application/json;charset=utf-8");
//                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    PrintWriter out = response.getWriter();
                    Map<String, Object> map = new HashMap<>();
                    map.put("code", 401);
                    if (exception instanceof UsernameNotFoundException || exception instanceof BadCredentialsException) {
                        map.put("msg", "????????????????????????");
                    } else if (exception instanceof DisabledException) {
                        map.put("msg", "???????????????");
                    } else {
                        map.put("msg", "????????????!");
                    }
                    out.write(objectMapper.writeValueAsString(map));
                    out.flush();
                    out.close();
                })
                // ????????????
                .successHandler((request, response, authentication) -> {
                    WebAuthenticationDetails details = (WebAuthenticationDetails) authentication.getDetails();
                    Date date = new Date();
                    LoginRecord loginRecord = new LoginRecord(authentication.getName(), false, details.getRemoteAddress(), date);
                    recordLogin(loginRecord);
                    Map<String, Object> map = new HashMap<>();
                    map.put("code", 200);
                    map.put("msg", "????????????");
                    map.put("data", authentication);
                    response.setContentType("application/json;charset=utf-8");
                    PrintWriter out = response.getWriter();
                    out.write(objectMapper.writeValueAsString(map));
                    out.flush();
                    out.close();
                })
                .and()
                .exceptionHandling()
                // ????????????
                .accessDeniedHandler(((request, response, exception) -> {
                    response.setContentType("application/json;charset=utf-8");
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    PrintWriter out = response.getWriter();
                    Map<String, Object> map = new HashMap<>();
                    map.put("code", 403);
                    map.put("msg", "????????????????????????!");
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
                        map.put("msg", "????????????");
                        map.put("data", authentication);
                    } else {
                        map.put("code", 401);
                        map.put("msg", "?????????!");
                    }
                    response.setContentType("application/json;charset=utf-8");
                    PrintWriter out = response.getWriter();
                    out.write(objectMapper.writeValueAsString(map));
                    out.flush();
                    out.close();
                })
                .permitAll()
                // ????????????
                .and().cors().disable()
                // ??????????????????????????????????????? API POST (???:  postman )???????????????
                .csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint((request, response, exception) -> {
                    response.setContentType("application/json;charset=utf-8");
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    PrintWriter out = response.getWriter();
                    Map<String, Object> map = new HashMap<>();
                    map.put("code", 403);
                    map.put("msg", "?????????!");
                    out.write(objectMapper.writeValueAsString(map));
                    out.flush();
                    out.close();
                })
        ;
        http
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS).maximumSessions(3);
        //????????????????????????????????????
        http.addFilterBefore(securityInterceptor, FilterSecurityInterceptor.class)
                .csrf().disable();
    }

    private void recordLogin(LoginRecord loginRecord) {
        try {
            loginRecordMapper.insert(loginRecord);
        } catch (Exception e) {
            log.error(e.getMessage());
            // ????????????
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
    }

    // ??????????????????????????????Swagger2 ?????? API ?????????
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
        // ????????????
        auth.userDetailsService(userDetailServiceImpl)
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
