package com.icat.service;

import java.io.File;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.icat.bean.MonitorInfo;
import com.icat.bean.Processes;

import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.CentralProcessor.TickType;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.hardware.NetworkIF;
import oshi.hardware.Sensors;
import oshi.software.os.OSProcess;
import oshi.software.os.OperatingSystem;
import oshi.software.os.OperatingSystem.ProcessSort;
import oshi.util.FormatUtil;
import oshi.util.Util;

public class SystemMonitorService {
	public MonitorInfo monitorInfo = new MonitorInfo();
	SystemInfo si = new SystemInfo();
	HardwareAbstractionLayer hal = si.getHardware();

	public void getMemory() {
		GlobalMemory memory = hal.getMemory();
		monitorInfo.setUseMemory(FormatUtil.formatBytes(memory.getAvailable()));
		monitorInfo.setTotalMemory(FormatUtil.formatBytes(memory.getTotal()));
		monitorInfo.setSwapUsed(FormatUtil.formatBytes(memory.getSwapUsed()));
		monitorInfo.setSwapTotal(FormatUtil.formatBytes(memory.getSwapTotal()));
	}

	public void getCpu() {
		CentralProcessor processor = hal.getProcessor();
		monitorInfo.setRunTime(FormatUtil.formatElapsedSecs(processor.getSystemUptime()));
		long[] prevTicks = processor.getSystemCpuLoadTicks();
		Util.sleep(1000);
		long[] ticks = processor.getSystemCpuLoadTicks();
		long user = ticks[TickType.USER.getIndex()] - prevTicks[TickType.USER.getIndex()];
		long nice = ticks[TickType.NICE.getIndex()] - prevTicks[TickType.NICE.getIndex()];
		long sys = ticks[TickType.SYSTEM.getIndex()] - prevTicks[TickType.SYSTEM.getIndex()];
		long idle = ticks[TickType.IDLE.getIndex()] - prevTicks[TickType.IDLE.getIndex()];
		long iowait = ticks[TickType.IOWAIT.getIndex()] - prevTicks[TickType.IOWAIT.getIndex()];
		long irq = ticks[TickType.IRQ.getIndex()] - prevTicks[TickType.IRQ.getIndex()];
		long softirq = ticks[TickType.SOFTIRQ.getIndex()] - prevTicks[TickType.SOFTIRQ.getIndex()];
		long steal = ticks[TickType.STEAL.getIndex()] - prevTicks[TickType.STEAL.getIndex()];
		long totalCpu = user + nice + sys + idle + iowait + irq + softirq + steal;
		monitorInfo.setCpuIdle(String.valueOf(100d * idle / totalCpu) + "%");
		monitorInfo.setCpuSystem(String.valueOf(100d * sys / totalCpu) + "%");
		monitorInfo.setCpuUser(String.valueOf(100d * user / totalCpu) + "%");
		monitorInfo.setiOwait(String.valueOf(100d * iowait / totalCpu) + "%");
		monitorInfo.setiRQ(String.valueOf(100d * irq / totalCpu) + "%");
		monitorInfo.setSoftIRQ(String.valueOf(100d * softirq / totalCpu) + "%");
		monitorInfo.setSteal(String.valueOf(100d * steal / totalCpu) + "%");
	}

	public void getProcesses() {
		OperatingSystem os = si.getOperatingSystem();
		GlobalMemory memory = hal.getMemory();
		monitorInfo.setProcesse(String.valueOf(os.getProcessCount()));
		monitorInfo.setThreads(String.valueOf(os.getThreadCount()));
		List<OSProcess> procs = Arrays.asList(os.getProcesses(5, ProcessSort.CPU));
		List<Processes> list = new ArrayList<Processes>();
		for (int i = 0; i < procs.size() && i < 5; i++) {
			OSProcess p = procs.get(i);
			Processes processes = new Processes();
			processes.setCPU(String.valueOf(100d * (p.getKernelTime() + p.getUserTime()) / p.getUpTime()) + "%");
			processes.setMEM(String.valueOf(100d * p.getResidentSetSize() / memory.getTotal() + "%"));
			processes.setNAME(p.getName());
			processes.setPID(String.valueOf(p.getProcessID()));
			processes.setRSS(FormatUtil.formatBytes(p.getResidentSetSize()));
			processes.setVSZ(FormatUtil.formatBytes(p.getVirtualSize()));
			list.add(processes);
		}
		monitorInfo.setProcesses(list);
	}

	public void printSensors() {
		Sensors sensors = hal.getSensors();
		monitorInfo.setTemperature(sensors.getCpuTemperature() + "°C");
	}

	public void getSpace() {
		File[] roots = File.listRoots();
		for (File file : roots) {
			long free = file.getFreeSpace();
			long total = file.getTotalSpace();
			long use = total - free;
			monitorInfo.setIoFree(getBigDecimal(String.valueOf(free)) + "G");
			monitorInfo.setIoUse(getBigDecimal(String.valueOf(use)) + "G");
			monitorInfo.setIoTotal(getBigDecimal(String.valueOf(total)) + "G");
			monitorInfo.setIoBfb(bfb(Long.valueOf(use), Long.valueOf(total)) + "%");
		}
	}

	public static BigDecimal getBigDecimal(String value) {
		BigDecimal b1 = new BigDecimal(value);
		BigDecimal a = new BigDecimal(1048576);
		BigDecimal c = b1.divide(a, 2, 0);
		BigDecimal d = new BigDecimal(1024);
		return c.divide(d, 2, 0);
	}

	public static String bfb(Object num1, Object num2) {
		double val1 = Double.valueOf(num1.toString()).doubleValue();
		double val2 = Double.valueOf(num2.toString()).doubleValue();
		if (val2 == 0.0D) {
			return "0.0%";
		}
		DecimalFormat df = new DecimalFormat("#0.00");
		return df.format(val1 / val2 * 100.0D) + "%";
	}

	public void getNetInfo() {
		NetworkIF[] networkIFs = hal.getNetworkIFs();
		for (NetworkIF net : networkIFs) {
			if (net.getName().equals("en0")) {
				long start = System.currentTimeMillis();
				long rxBytesStart = net.getBytesRecv();
				long txBytesStart = net.getBytesSent();
				try {
					Thread.sleep(1000L);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				long end = System.currentTimeMillis();
				long rxBytesEnd = 0, txBytesEnd = 0;
				NetworkIF[] networkIFs2 = hal.getNetworkIFs();
				for (NetworkIF networkIF : networkIFs2) {
					if (networkIF.getName().equals("en0")) {
						rxBytesEnd = networkIF.getBytesRecv();
						txBytesEnd = networkIF.getBytesSent();
					}
				}
				long rxbps = (rxBytesEnd - rxBytesStart) * 8L / (end - start) * 1000L;
				long txbps = (txBytesEnd - txBytesStart) * 8L / (end - start) * 1000L;
				rxbps /= 1024L;
				txbps /= 1024L;
				BigDecimal a = new BigDecimal(rxbps);
				BigDecimal b = new BigDecimal(txbps);
				BigDecimal c = new BigDecimal(1014);
				if ((rxbps > 10000L) || (txbps > 10000L)) {
					monitorInfo.setNetRxbps(a.divide(c, 2, 0) + "MB");
					monitorInfo.setNettxbps(b.divide(c, 2, 0) + "MB");
				} else {
					monitorInfo.setNetRxbps(rxbps + "k");
					monitorInfo.setNettxbps(txbps + "K");
				}
			}
		}

	}

}