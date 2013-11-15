package com.prodyna.pac.conference.common.access;

import static java.lang.annotation.ElementType.METHOD;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.interceptor.InterceptorBinding;

@InterceptorBinding
@Retention(RetentionPolicy.RUNTIME)
@Target({ METHOD, ElementType.TYPE })
public @interface Logged {
}
