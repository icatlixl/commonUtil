package com.icat.common.util;

import java.io.Serializable;
import java.security.Security;
import java.util.Map;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.icat.autoconfigure.MailProperties;

/**
 * author:icat blog:https://blog.techauch.com 邮箱工具类 可以在项目启动是或者初始化可配置邮箱 例如：new
 * MailUtils("mail.user", "password", "mail.smtp.host",null);
 */
@Service
public class MailUtils implements Serializable {
	private static final long serialVersionUID = 1L;
	/** 发件人的账号 **/
	private static String mailUser;
	/** 访问SMTP服务时需要提供的密码 **/
	private static String mailPassword;
	/** SMTP服务器 **/
	private static String mailSmtpHost;
	/** propsMap:预留邮箱配置参数，默认配置，可以传空。 **/
	private static Map<String, String> propsMap;
	@Autowired
	private MailProperties mailProperties;

	public MailUtils(String mailUser, String mailPassword, String mailSmtpHost, Map<String, String> propsMap) {
		if (StrKit.notBlank(mailUser)) {
			MailUtils.mailUser = mailUser;
			MailUtils.mailPassword = mailPassword;
			MailUtils.mailSmtpHost = mailSmtpHost;
			MailUtils.propsMap = propsMap;
		}
	}

	public MailUtils() {
	};

	/**
	 * title：标题 content：内容 toEmail：推送人（英文逗号,隔开标示多个）
	 * 详细配置可见：https://blog.techauch.com/get/detail/204
	 * 
	 */
	@SuppressWarnings("restriction")
	public boolean sendMail(String title, String content, String toEmail) {
		try {
			if (StrKit.isBlank(MailUtils.mailUser)) {
				MailUtils.mailUser = mailProperties.getMailUser();
				MailUtils.mailPassword = mailProperties.getMailPassword();
				MailUtils.mailSmtpHost = mailProperties.getMailSmtpHost();
				MailUtils.propsMap = mailProperties.getPropsMap();
			}
			Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
			// 配置发送邮件的环境属性
			final Properties props = System.getProperties();
			// 表示SMTP发送邮件，需要进行身份验证
			props.put("mail.smtp.auth", true);
			// 设置端口
			props.put("mail.smtp.host", mailSmtpHost);
			// 发件人的账号
			props.put("mail.user", mailUser);
			// 访问SMTP服务时需要提供的密码
			props.put("mail.password", mailPassword);
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.properties.mail.smtp.starttls.required", "true");
			props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			props.put("mail.smtp.socketFactory.fallback", "false");
			props.put("mail.smtp.socketFactory.port", 465);
			if (propsMap != null) {
				for (Map.Entry<String, String> entry : propsMap.entrySet()) {
					props.put(entry.getKey(), entry.getValue());
				}
			}
			// 构建授权信息，用于进行SMTP进行身份验证
			Authenticator authenticator = new Authenticator() {
				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					// 用户名、密码
					String userName = props.getProperty("mail.user");
					String password = props.getProperty("mail.password");
					return new PasswordAuthentication(userName, password);
				}
			};
			// 使用环境属性和授权信息，创建邮件会话
			Session mailSession = Session.getInstance(props, authenticator);
			// 创建邮件消息
			Message message = new MimeMessage(mailSession);
			// 设置发件人
			InternetAddress form = new InternetAddress(props.getProperty("mail.user"));
			message.setFrom(form);

			String emails[] = toEmail.split(",");
			// 设置收件人
			InternetAddress[] toAddress = new InternetAddress[emails.length];
			for (int i = 0; i < emails.length; i++) {
				String email = emails[i];
				toAddress[i] = new InternetAddress(email);
			}
			message.setRecipients(MimeMessage.RecipientType.TO, toAddress);
			// 设置邮件标题
			message.setSubject(title);
			// 设置邮件的内容体
			message.setContent(content, "text/html;charset=UTF-8");

			message.saveChanges();
			// 发送邮件
			Transport.send(message);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	public String getMailUser() {
		return mailUser;
	}

	public void setMailUser(String mailUser) {
		MailUtils.mailUser = mailUser;
	}

	public String getMailPassword() {
		return mailPassword;
	}

	public void setMailPassword(String mailPassword) {
		MailUtils.mailPassword = mailPassword;
	}

	public String getMailSmtpHost() {
		return mailSmtpHost;
	}

	public void setMailSmtpHost(String mailSmtpHost) {
		MailUtils.mailSmtpHost = mailSmtpHost;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
