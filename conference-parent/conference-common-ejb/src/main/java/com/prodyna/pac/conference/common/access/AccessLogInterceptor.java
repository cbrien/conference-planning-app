package com.prodyna.pac.conference.common.access;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import org.slf4j.Logger;

@Logged
@Interceptor
public class AccessLogInterceptor {

	@Inject
	@AccessLog
	Logger logger;

	@AroundInvoke
	public Object logAccess(InvocationContext context) throws Exception {
		StringBuilder sb = new StringBuilder();
		sb.append(context.getTarget().getClass().getName());
		sb.append(".");
		sb.append(context.getMethod().getName());
		sb.append(" invoked with [");
		boolean firstEntry = true;
		for (Object parameter : context.getParameters()) {
			if (!firstEntry) {
				sb.append(", {");
			} else {
				sb.append("{");
			}
			sb.append(parameter);
			sb.append("}");
		}
		sb.append(" ]");
		long startTime = System.currentTimeMillis();
		try {
			return context.proceed();
		} finally {
			sb.append("in [");
			sb.append(System.currentTimeMillis() - startTime);
			sb.append("] ms");
			logger.info(sb.toString());
		}
	}
	
}
