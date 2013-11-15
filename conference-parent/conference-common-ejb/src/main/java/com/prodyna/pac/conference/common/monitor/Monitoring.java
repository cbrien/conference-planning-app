package com.prodyna.pac.conference.common.monitor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Monitoring implements MonitoringMXBean {

	Map<String, Map<String, MethodCall>> methodCalls = new TreeMap<String, Map<String,MethodCall>>();
	
	@Override
	public void logCall(String clazz, String method, long time) {
		getMethodEntry(clazz, method).log(time);
	}

	private MethodCall getMethodEntry(String clazz, String method) {
		Map<String, MethodCall> methodMap = methodCalls.get(clazz);
		if (methodMap == null) {
			methodMap = new TreeMap<String, MethodCall>();
			methodCalls.put(clazz, methodMap);
		}
		
		MethodCall methodCall = methodMap.get(method);
		if (methodCall == null) {
			methodCall = new MethodCall(clazz, method);
			methodMap.put(method, methodCall);
		}
		
		return methodCall;
	}

	@Override
	public List<MethodCall> getMethodCalls() {
		List<MethodCall> resultList = new ArrayList<MethodCall>();
		for (Map<String, MethodCall> methodMap : methodCalls.values()) {
			resultList.addAll(methodMap.values());
		}
		return resultList;
	}
	

	@Override
	public void reset() {
		methodCalls.clear();
	}

	@Override
	public MethodCall getLongestRunningMethodCall() {
		long longestTime = 0;
		MethodCall methodCall = null;
		for (MethodCall call : getMethodCalls()) {
			if (call.getMaxTime() > longestTime) {
				longestTime = call.getMaxTime();
				methodCall = call;
			}
		}
		return methodCall;
	}

	@Override
	public MethodCall getViewestCalledMethodCall() {
		long viewestCount = -1;
		MethodCall methodCall = null;
		for (MethodCall call : getMethodCalls()) {
			if (viewestCount < 0 || call.getCount() < viewestCount) {
				viewestCount = call.getCount();
				methodCall = call;
			}
		}
		return methodCall;
	}

}
