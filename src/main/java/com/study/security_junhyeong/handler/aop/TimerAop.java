package com.study.security_junhyeong.handler.aop;

import java.lang.reflect.Parameter;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
public class TimerAop {

	//log를 찍는 역할
	private final Logger LOGGER = LoggerFactory.getLogger(getClass()); //getClass 우리 클래스를 가져와라 == TimerAopl.class == this.getClass()
	
	//execution은 메소드를 찾아가는 것
	//젤 앞의 * -> 접근지정자, returnType
	//컨트롤러 패키지 뒤의 .. -> 컨트롤러 이후에 오는 모든 클래스
	//*RestController 뒤에 RestContoller클래스 이름이 포함된 모든 클래스를 말한다.
	@Pointcut("execution(* com.study.security_junhyeong.web.controller..*.*(..))") //패키지명, 모든 클래스, 모든 메소드, 등등 
	private void pointCut() {}
	
	@Pointcut("@annotation(com.study.security_junhyeong.handler.aop.annotation.Timer)")
	private void enableTimer() {}
	
	// Aop 에 Throwable 는 모두 들어간다.
	@Around("pointCut() && enableTimer()") //controller 내의 모든 패키지 중에서 Timer 어노테이션이 달려 있는 것들에 적용
	public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
		StopWatch stopWatch = new StopWatch();
		stopWatch.start(); //stopWatch 시작
		
		Object result = joinPoint.proceed(); //joinpoint 실행, 실행된 결과 return 을 result에 담는다.
		
		stopWatch.stop();
		
		//클래스명 메소드명 = 메소드 결과 (시간) 
		LOGGER.info(">>>>> {}({}) 메소드 실행 시간: {}ms",  
				joinPoint.getSignature().getDeclaringTypeName(),
				joinPoint.getSignature().getName(),
				stopWatch.getTotalTimeSeconds());
		
		return result;
	}
	

}
