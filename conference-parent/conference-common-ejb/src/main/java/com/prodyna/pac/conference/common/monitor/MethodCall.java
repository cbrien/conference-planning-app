package com.prodyna.pac.conference.common.monitor;

public class MethodCall {
		
	
	private String clazz;
	private String method;
	private long count = 0;
	private long minTime = -1;
	private long maxTime = 0;
	private long overallTime = 0;

	public MethodCall(String clazz, String method) {
		this.clazz = clazz;
		this.method = method;
	}
	
	public void log(long time){
		count++;
		overallTime += time;
		minTime = minTime < 0 ? time : Math.min(minTime, time);
		maxTime = Math.max(maxTime, time);
	}

	/**
	 * @return the clazz
	 */
	public String getClazz() {
		return clazz;
	}

	/**
	 * @return the method
	 */
	public String getMethod() {
		return method;
	}

	/**
	 * @return the minTime
	 */
	public long getMinTime() {
		return minTime;
	}
	
	/**
	 * @return the number of calls
	 */
	public long getCount() {
		return count;
	}
	

	/**
	 * @return the maxTime
	 */
	public long getMaxTime() {
		return maxTime;
	}

	/**
	 * @return the averageTime
	 */
	public long getAverageTime() {
		return overallTime / count;
	}

	/**
	 * @return the overallTime
	 */
	public long getOverallTime() {
		return overallTime;
	}

}
