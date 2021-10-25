package com.hicorp.segment.swagger;


import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.async.DeferredResult;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.lang.reflect.Field;
import java.util.Arrays;

/**
 * @Author: wqs
 * @Date: Created in 4:02 2021/5/19
 * @Description:
 * @ChineseDescription:
 * @Modified_By:
 */
@Configuration
@EnableOpenApi
@EnableKnife4j
public class SwaggerConfig implements InitializingBean {

    @Autowired
    private ApplicationContext applicationContext;

    // 默认版本的接口api-docs分组
    @Bean
    public Docket defaultDocket() {
        return new Docket(DocumentationType.OAS_30)
                .groupName("all_api")
                .apiInfo(buildApiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.hicorp.segment.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo buildApiInfo() {
        return new ApiInfoBuilder()
                .title("盾构管片智能制造信息管理系统API文档")
                .contact(new Contact("wqs", "https://www.baidu.com", "910749592@qq.com"))
                .version("1.0.0")
                .build();
    }

    public Docket buildDocket(String groupName) {
        return new Docket(DocumentationType.OAS_30)
                .groupName(groupName)
                .apiInfo(buildApiInfo())
                .genericModelSubstitutes(DeferredResult.class).useDefaultResponseMessages(false).forCodeGeneration(true)
                .select()
                .apis(method -> {
                    // 方法标注了版本
                    if (method.isAnnotatedWith(ApiVersion.class)) {
                        ApiVersion apiVersion = method.getHandlerMethod().getMethodAnnotation(ApiVersion.class);
                        assert apiVersion != null;
                        if (apiVersion.value().length != 0) {
                            return Arrays.asList(apiVersion.value()).contains(groupName);
                        }
                    }

                    // 方法所在的类标注了版本
                    ApiVersion annotationOnClass = method.getHandlerMethod().getBeanType().getAnnotation(ApiVersion.class);
                    if (annotationOnClass != null) {
                        if (annotationOnClass.value().length != 0) {
                            return Arrays.asList(annotationOnClass.value()).contains(groupName);
                        }
                    }
                    return false;
                })
                .apis(RequestHandlerSelectors.basePackage("com.hicorp.segment.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    // 动态注入bean
    @Override
    public void afterPropertiesSet() throws Exception {
        // 根据ApiConstantVersion构造 docket
        Class<ApiVersionConstant> constantClass = ApiVersionConstant.class;
        Field[] declaredFields = constantClass.getDeclaredFields();
        // 动态注入bean
        AutowireCapableBeanFactory autowireCapableBeanFactory = applicationContext.getAutowireCapableBeanFactory();
        if (autowireCapableBeanFactory instanceof DefaultListableBeanFactory capableBeanFactory) {
            for (Field declaredField : declaredFields) {
                AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder
                        .genericBeanDefinition()
                        .setFactoryMethodOnBean("buildDocket", "swaggerConfig")
                        .addConstructorArgValue(declaredField.get(ApiVersionConstant.class)).getBeanDefinition();
                capableBeanFactory.registerBeanDefinition(declaredField.getName(), beanDefinition);
            }
        }
    }
}
