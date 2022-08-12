package com.study.security_junhyeong.handler.aop;

import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import com.study.security_junhyeong.handler.exception.CustomValidationApiException;

@Aspect
@Component
public class ValidationAop {

	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	@Pointcut("@annotation(com.study.security_junhyeong.handler.aop.annotation.ValidCheck)")
	public void enableValid() {}
	
	@Before("enableValid()")
	public void validBefore(JoinPoint joinPoint) {
		Object[] args = joinPoint.getArgs();
		LOGGER.info("유효성 검사중...");
		
		for(Object arg : args) {
			if(arg.getClass() == BeanPropertyBindingResult.class) {
				BindingResult bindingResult = (BindingResult) arg;
				if(bindingResult.hasErrors()) {
					Map<String, String> errorMap = new HashMap<String, String>();
					
					bindingResult.getFieldErrors().forEach(error -> {
						errorMap.put(error.getField(), error.getDefaultMessage());
						
					});
					
					throw new CustomValidationApiException("유효성 검사 실패", errorMap);
				}
			}
		}
	}
	
	@AfterReturning(value = "enableValid()", returning = "returnObj")
	private void validAfter(JoinPoint joinPoint, Object returnObj) {
		LOGGER.info("유효성 검사 완료 {}: " , returnObj);

	}
	
}
