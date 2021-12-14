package org.zerock.aop;

import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j;

@Aspect
@Log4j
@Component // spring bean으로 등록해주는 annotation
public class LogAdvice {
	@Before("execution(* org.zerock.service.SampleService*.*(..))")
	public void logBefore() {
		log.info("============================");
		
	}
	@Before("execution(* org.zerock.service.SampleService*.*(..)) && args(str1, str2)")
	public void logBeforeWithParam(String str1, String str2) {
		log.info("str1" + str1);
		log.info("str2" + str2);
	}
	
	/**
	 * throwing == logException의 매개변수 이름
	 */
	@AfterThrowing(pointcut = "execution(* org.zerock.service.SampleService*.*(..))",throwing = "exceptionn")
	public void logException(Exception exceptionn) {
		log.info("Exception");
		log.info("exception: "+ exceptionn);
	}
	
	
	@Around("execution(* org.zerock.service.SampleService*.*(..))")
	public Object logTime(ProceedingJoinPoint pjp) {
		//pjp 실행된 메소드의 정보를 담음
		long start = System.currentTimeMillis();
		Object ret = null;
		log.info("Target: " + pjp.getTarget());
		log.info("Param" + Arrays.toString(pjp.getArgs()));
		//perform before @Before

		
		
		try {
			ret = pjp.proceed();
			// proceed to next advice or invoke method
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//perform after method
		long end = System.currentTimeMillis();	
		log.info("TIME: " + (end - start));
		return ret;
		
		
	}
}











