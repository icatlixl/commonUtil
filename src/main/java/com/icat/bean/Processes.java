package com.icat.bean;

public class Processes {
	private String PID;// pid
	private String CPU;// cpu占用比例
	private String MEM;// 内存占用比例
	private String VSZ;// 虚拟内存到校
	private String RSS;// 常驻内存集合大小
	private String NAME;// 进程名称
	public String getPID() {
		return PID;
	}
	public void setPID(String pID) {
		PID = pID;
	}
	public String getCPU() {
		return CPU;
	}
	public void setCPU(String cPU) {
		CPU = cPU;
	}
	public String getMEM() {
		return MEM;
	}
	public void setMEM(String mEM) {
		MEM = mEM;
	}
	public String getVSZ() {
		return VSZ;
	}
	public void setVSZ(String vSZ) {
		VSZ = vSZ;
	}
	public String getRSS() {
		return RSS;
	}
	public void setRSS(String rSS) {
		RSS = rSS;
	}
	public String getNAME() {
		return NAME;
	}
	public void setNAME(String nAME) {
		NAME = nAME;
	}

}
