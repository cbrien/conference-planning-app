package com.prodyna.pac.conference.common.monitor;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.management.MBeanServer;
import javax.management.MBeanServerInvocationHandler;
import javax.management.ObjectName;

import org.slf4j.Logger;

@Monitored
@Interceptor
public class MonitoringInterceptor {

	@Inject
	Logger logger;

	@Inject
	MBeanServer mbeanServer;

	private MonitoringMXBean monitoringBean;

	@PostConstruct
	public void initialize(InvocationContext context) {
		try {
			ObjectName objectName = new ObjectName(MonitoringMXBean.OBJECT_NAME);
			monitoringBean = MBeanServerInvocationHandler.newProxyInstance(
					mbeanServer, objectName, MonitoringMXBean.class, false);
		} catch (Exception e) {
			logger.error("could not initialize monitoring bean", e);
		}

	}

	@AroundInvoke
	public Object monitorCall(InvocationContext context) throws Exception {
		long start = System.currentTimeMillis();
		try {
			return context.proceed();
		} finally {
			long time = System.currentTimeMillis() - start;
			if (monitoringBean != null) {
				monitoringBean.logCall(context.getTarget().getClass().getName(), context
						.getMethod().getName(), time);
			}
		}
	}
}
