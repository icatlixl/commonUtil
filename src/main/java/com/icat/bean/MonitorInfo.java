package com.icat.bean;

import java.io.Serializable;
import java.util.List;

public class MonitorInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	private String useMemory;// 使用内存
	private String totalMemory;// 总内存
	private String swapUsed;// 虚拟使用内存
	private String swapTotal;// 虚拟总内存
	private String runTime;// 运行时间
	private String cpuUser;// cpu用户使用比例
	private String cpuSystem;// cpu系统使用比例
	private String cpuIdle;// cpu空闲比例
	private String iOwait;// Io等待进行比例
	private String iRQ;// 中断比例
	private String softIRQ;// 延迟中断比例
	private String steal;// 阈值
	private String processe;// 进程数量
	private String threads;// 进程数量
	private List<Processes> processes;// 进程集合
	private String temperature;// cpu温度
	private String ioFree;// 空闲
	private String ioUse;// 用户使用
	private String ioTotal;// 总使用
	private String ioBfb;// 百分比
	private String netRxbps;
	private String nettxbps;

	public String getNetRxbps() {
		return netRxbps;
	}

	public void setNetRxbps(String netRxbps) {
		this.netRxbps = netRxbps;
	}

	public String getNettxbps() {
		return nettxbps;
	}

	public void setNettxbps(String nettxbps) {
		this.nettxbps = nettxbps;
	}

	public String getUseMemory() {
		return useMemory;
	}

	public void setUseMemory(String useMemory) {
		this.useMemory = useMemory;
	}

	public String getTotalMemory() {
		return totalMemory;
	}

	public void setTotalMemory(String totalMemory) {
		this.totalMemory = totalMemory;
	}

	public String getSwapUsed() {
		return swapUsed;
	}

	public void setSwapUsed(String swapUsed) {
		this.swapUsed = swapUsed;
	}

	public String getSwapTotal() {
		return swapTotal;
	}

	public void setSwapTotal(String swapTotal) {
		this.swapTotal = swapTotal;
	}

	public String getRunTime() {
		return runTime;
	}

	public void setRunTime(String runTime) {
		this.runTime = runTime;
	}

	public String getCpuUser() {
		return cpuUser;
	}

	public void setCpuUser(String cpuUser) {
		this.cpuUser = cpuUser;
	}

	public String getCpuSystem() {
		return cpuSystem;
	}

	public void setCpuSystem(String cpuSystem) {
		this.cpuSystem = cpuSystem;
	}

	public String getCpuIdle() {
		return cpuIdle;
	}

	public void setCpuIdle(String cpuIdle) {
		this.cpuIdle = cpuIdle;
	}

	public String getiOwait() {
		return iOwait;
	}

	public void setiOwait(String iOwait) {
		this.iOwait = iOwait;
	}

	public String getiRQ() {
		return iRQ;
	}

	public void setiRQ(String iRQ) {
		this.iRQ = iRQ;
	}

	public String getSoftIRQ() {
		return softIRQ;
	}

	public void setSoftIRQ(String softIRQ) {
		this.softIRQ = softIRQ;
	}

	public String getSteal() {
		return steal;
	}

	public void setSteal(String steal) {
		this.steal = steal;
	}

	public String getProcesse() {
		return processe;
	}

	public void setProcesse(String processe) {
		this.processe = processe;
	}

	public String getThreads() {
		return threads;
	}

	public void setThreads(String threads) {
		this.threads = threads;
	}

	public List<Processes> getProcesses() {
		return processes;
	}

	public void setProcesses(List<Processes> processes) {
		this.processes = processes;
	}

	public String getTemperature() {
		return temperature;
	}

	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}

	public String getIoFree() {
		return ioFree;
	}

	public void setIoFree(String ioFree) {
		this.ioFree = ioFree;
	}

	public String getIoUse() {
		return ioUse;
	}

	public void setIoUse(String ioUse) {
		this.ioUse = ioUse;
	}

	public String getIoTotal() {
		return ioTotal;
	}

	public void setIoTotal(String ioTotal) {
		this.ioTotal = ioTotal;
	}

	public String getIoBfb() {
		return ioBfb;
	}

	public void setIoBfb(String ioBfb) {
		this.ioBfb = ioBfb;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}