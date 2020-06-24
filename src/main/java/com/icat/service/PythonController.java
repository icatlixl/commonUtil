package com.icat.service;

import java.io.IOException;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.icat.common.R;

/**
 * Created by joe强 on 2018/10/23 17:08
 */
@RestController
public class PythonController {
	@Autowired
	ObjectMapper objectMapper;

	/**
	 * filePath 脚本绝对路径
	 *
	 * @param request
	 * @param
	 * @throws IOException
	 */
	@RequestMapping(value = "/executeCommand", method = RequestMethod.POST)
	public R<String> getDate(HttpServletRequest request) {
		String ruseltJson = null;
		// 动态获取参数
		Enumeration<String> parameterNames = request.getParameterNames();
		String filePath = request.getParameter("filePath");
		if (StringUtils.isEmpty(filePath)) {
			return R.error();
		}
		String params = new String();
		while (parameterNames.hasMoreElements()) {
			String name = (String) parameterNames.nextElement();
			if (name.equals("filePath")) {
				String value = request.getParameter(name);
				params += name + "=" + value + " ";
			}
		}
		params = params.substring(0, params.length() - 1);
		String outFilePath = filePath + "/" + SshServerUtils.getUUID();

		// 拼接Liunx命令
		String command = String.format("cd %s && python gp.py search_grid_by_latlng %s > %s ", filePath, params,
				outFilePath);

		ruseltJson = SshServerUtils.execCmd(command, "ubuntu", "Lixinlin123", "111.230.227.29", 22, outFilePath);
		List data = null;
		if (!StringUtils.isEmpty(ruseltJson)) {
			// 设置objectMapper识别单引号
			objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
			// json字符串转换为对象
			return R.success(ruseltJson);
		} else {
			return R.error();
		}

	}
}
