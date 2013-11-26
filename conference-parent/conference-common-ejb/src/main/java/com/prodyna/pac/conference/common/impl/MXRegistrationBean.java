package com.prodyna.pac.conference.common.impl;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.management.MBeanServer;
import javax.management.ObjectName;

import org.slf4j.Logger;

import com.prodyna.pac.conference.common.monitor.Monitoring;
import com.prodyna.pac.conference.common.monitor.MonitoringMXBean;

@Singleton
@Startup
public class MXRegistrationBean {

	@Inject
	Logger logger;

	@Inject
	private MBeanServer mbeanServer;

	@PostConstruct
	private void registerMXBeans() {
		try {
			ObjectName objectName = new ObjectName(MonitoringMXBean.OBJECT_NAME);
			mbeanServer.registerMBean(new Monitoring(), objectName);
		} catch (Exception e) {
			logger.error("unable to register MonitoringMXBean", e);
		}
	}

	@PreDestroy
	private void unregisterMXBeans() {
		try {
			ObjectName objectName = new ObjectName(MonitoringMXBean.OBJECT_NAME);
			mbeanServer.unregisterMBean(objectName);
		} catch (Exception e) {
			logger.error("unable to register MonitoringMXBean", e);
		}
	}

}
