package com.icat.autoconfigure;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = MailProperties.MAIL_PREFIX)
public class MailProperties {
	public static final String MAIL_PREFIX = "icatmail";
	private String mailUser;
	private String mailPassword;
	private String mailSmtpHost;
	private Map<String, String> propsMap;

	public Map<String, String> getPropsMap() {
		return propsMap;
	}

	public void setPropsMap(Map<String, String> propsMap) {
		this.propsMap = propsMap;
	}

	public String getMailUser() {
		return mailUser;
	}

	public void setMailUser(String mailUser) {
		this.mailUser = mailUser;
	}

	public String getMailPassword() {
		return mailPassword;
	}

	public void setMailPassword(String mailPassword) {
		this.mailPassword = mailPassword;
	}

	public String getMailSmtpHost() {
		return mailSmtpHost;
	}

	public void setMailSmtpHost(String mailSmtpHost) {
		this.mailSmtpHost = mailSmtpHost;
	}

}
