package com.study.security_junhyeong.handler.aop.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME) //언제 어노테이션을 적용시킬 것인지
@Target({ TYPE, METHOD }) //type: type(class)위에 달 수 있다. METHOD: Method 위에 달 수 있다. 즉 어디에 쓸 것인지 정하는 것이 Target이다.
public @interface Timer {

}
