package com.example.springboottodolist.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
public class UserServiceAspect {
  private static final Logger logger = LoggerFactory.getLogger(UserServiceAspect.class);

  @Before("execution(* com.example.springboottodolist.service.UserService.*(..))")
  public void before(JoinPoint joinPoint) {
    logger.info(" Get Aspect before ");
    logger.info(" Allowed execution for {}", joinPoint);
  }
}
