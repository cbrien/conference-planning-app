package com.prodyna.pac.conference.common.monitor;

import java.util.List;

public interface MonitoringMXBean {
	
	final String OBJECT_NAME = "com.prodyna.pac.conference:type=Monitoring";

	void logCall(String clazz, String method, long time);
	
	List<MethodCall> getMethodCalls();
	
	public MethodCall getLongestRunningMethodCall();
	
	public MethodCall getViewestCalledMethodCall();

	void reset();
	
}
