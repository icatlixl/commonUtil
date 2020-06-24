package com.icat.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.icat.bean.MonitorInfo;
import com.icat.common.R;
import com.icat.common.util.excel.ExcelData;
import com.icat.common.util.excel.ExportExcel;
import com.icat.service.SystemMonitorService;

@Controller
public class ServerMonitorController {
	@ResponseBody
	@RequestMapping(value = "/server/monitor/list", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public R<MonitorInfo> serverMonitorList() {
		SystemMonitorService monitorService = new SystemMonitorService();
		monitorService.getCpu();
		monitorService.getMemory();
		monitorService.getProcesses();
		monitorService.getSpace();
		return R.success(monitorService.monitorInfo);
	}

	@RequestMapping("/monitor/main")
	public String monitorMain() {
		return "monitor";
	}

	
}
