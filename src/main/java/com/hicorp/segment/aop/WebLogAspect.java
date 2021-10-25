package com.hicorp.segment.aop;

import com.hicorp.segment.utils.ResultBean;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

/**
 * @Author: wqs
 * @Date: Created in 10:29 2021/5/10
 * @Description: this class implement the aop.
 * @Modified By: 加入 @Slf4j 注解，以代替 private final Logger logger = LoggerFactory.getLogger(WebLogAspect.class); 工厂方法。
 */
@Slf4j
@Aspect
@Component
public class WebLogAspect {

    ThreadLocal<Long> startTime = new ThreadLocal<>();

//    @Autowired
//    private OperationRecordService recordService;

    /**
     * @Author: 91074
     * @Date: Created in 10:34 2021/5/10
     * @Params: [] []
     * @Return: void
     * @Description: describe and name the pointcut.
     * @ChineseDescription: 描述并命名切入点。
     */
    @Pointcut("execution(public * com.wqs.haier.controller.*.*(..))")
    public void controllerPointCut() {
    }

    /**
     * @Author: 91074
     * @Date: Created in 10:38 2021/5/10
     * @Params: [joinPoint] [org.aspectj.lang.JoinPoint]
     * @Return: void
     * @Description: Pre notification, the content obtained in response to the request.
     * @ChineseDescription: 前置通知，响应请求时，获取的内容。
     */
    @Before("controllerPointCut()")
    public void logBeforeController(JoinPoint joinPoint) {

        String currentUserName = "";
        String operationName = "";

        // 执行当前操作的用户
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            assert authentication != null;
            currentUserName = authentication.getName();
        }

        // 获取RequestAttributes
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        assert attributes != null;
        // 从获取RequestAttributes中获取HttpServletRequest的信息
        HttpServletRequest request = ((ServletRequestAttributes) attributes).getRequest();

        // url、http_method、ip
        String url = request.getRequestURL().toString();
        String http_method = request.getMethod();
        String ip = request.getRemoteAddr();

        // 请求的内容
        String params = Arrays.toString(joinPoint.getArgs());

        // 包+类名、方法名
        String class_method = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();

        // 利用注解获取操作名
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Operation operation = method.getAnnotation(Operation.class);
        if (operation != null) {
            operationName = operation.summary();
        }
        // 记录请求内容
        log.info("### USER : " + currentUserName);
        log.info("### URL : " + url);
        log.info("### HTTP_METHOD : " + http_method);
        log.info("### IP : " + ip);
        log.info("### THE ARGS OF THE CONTROLLER : " + params);
        log.info("### CLASS_METHOD : " + class_method);
        log.info("### OPERATION NAME : " + operationName);
        //log.info("###TARGET: " + joinPoint.getTarget());// 返回需要加强的目标类的对象
        //log.info("###THIS: " + joinPoint.getThis());// 返回经过加强后的代理类的对象


//        OperationRecord operationRecord = new OperationRecord(currentUserName, ip, "http_method:" + http_method + ",url:" + url, class_method, operationName, params, new Date());
//        recordService.createRecord(operationRecord);
    }

    /**
     * @Author: 91074
     * @Date: Created in 15:30 2021/5/10
     * @Params: [resultBean] [com.wqs.pe.utils.ResultBean<?>]
     * @Return: void
     * @Description: Post notification, printing information returned to the front end.
     * @ChineseDescription: 后置通知，打印返还给前端的code和msg。
     */
    @AfterReturning(returning = "resultBean", value = "controllerPointCut()")
    public void logAfterReturningController(ResultBean<?> resultBean) {
        log.info("###REQUEST RESULT:" + resultBean.getCode() + resultBean.getMsg());
    }

    /**
     * @Author: 91074
     * @Date: Created in 15:33 2021/5/10
     * @Params: [point] [org.aspectj.lang.ProceedingJoinPoint]
     * @Return: java.lang.Object
     * @Description: Around notification, ProceedingJoinPoint is a child interface of JoinPoint, which can represent the execution target method.
     * It must be a return value of type object, it must accept a parameter, it must be of type ProceedingJoinPoint, it must throw throwable.
     * @ChineseDescription: 环绕通知， ProceedingJoinPoint 是 JoinPoint 的子接口，可表示执行目标方法。
     * 必须是Object类型的返还值，必须接受一个参数，类型为 ProceedingJoinPoint ，必须 throws Throwable 。
     */
    @Around("controllerPointCut()")
    public Object logAroundController(ProceedingJoinPoint point) throws Throwable {
        startTime.set(System.currentTimeMillis());
        Object obj;
        try {
            obj = point.proceed();
            log.info("### " + point.getSignature().getName() + " METHOD USED TIME: " + (System.currentTimeMillis() - startTime.get()));
        } catch (Throwable e) {
            obj = e.toString();
        }
        return obj;
    }

    /**
     * @Author: 91074
     * @Date: Created in 16:17 2021/5/10
     * @Params: [joinPoint, e] [org.aspectj.lang.JoinPoint, java.lang.Throwable]
     * @Return: void
     * @Description: Exception enhancement notification.
     * @ChineseDescription: 切入执行异常。
     */
    @AfterThrowing(value = "controllerPointCut()", throwing = "e")
    public void logAfterThrowingController(JoinPoint joinPoint, Throwable e) {
        String methodName = joinPoint.getSignature().getName();
        List<Object> args = Arrays.asList(joinPoint.getArgs());
        log.info("### CONNECT POINT METHOD: " + methodName);
        log.info("### PARAMS: " + args);
        log.info("### THROWING EXCEPTION: " + e);
    }

    @After("controllerPointCut()")
    public void logAfterController() {
    }
}
