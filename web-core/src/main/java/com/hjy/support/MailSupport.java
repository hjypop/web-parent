package com.hjy.support;

import java.io.File;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang.StringUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.hjy.base.BaseClass;
import com.hjy.iface.IBaseInstance;
import com.hjy.system.TopConst;

/**
 * 
 * 类: MailSupport <br> properties配置信息核对完成
 * 描述: 发送邮件 <br>
 * 作者: 张海宇 zhanghaiyu@huijiayou.cn<br>
 * 时间: 2016年6月27日 下午3:29:02
 */
public class MailSupport extends BaseClass implements IBaseInstance {

	public final static MailSupport INSTANCE = new MailSupport();

	public boolean sendMail(String sReceive, String sTitle, String sContent) {

		boolean bFlagSuccess = false;

		try {
			bFlagSuccess = sendMail(sReceive, sTitle, sContent, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bFlagSuccess;
	}

	public boolean sendMail(String sReceive, String sTitle, String sContent, File[] files) {
		JavaMailSenderImpl senderImpl = new JavaMailSenderImpl();

		// 设定mail server
		senderImpl.setHost(getConfig("webcore.mail_host"));
		// 建立邮件消息,发送简单邮件和html邮件的区别
		MimeMessage mailMessage = senderImpl.createMimeMessage();
		// 注意这里的boolean,等于真的时候才能嵌套图片，在构建MimeMessageHelper时候，所给定的值是true表示启用，
		// multipart模式 为true时发送附件 可以设置html格式
		MimeMessageHelper messageHelper;
		try {
			messageHelper = new MimeMessageHelper(mailMessage, true, TopConst.CONST_BASE_ENCODING);

			// 设置收件人，寄件人
			messageHelper.setTo(StringUtils.split(StringUtils.replace(sReceive, " ", ""), ","));

			messageHelper.setFrom(getConfig("webcore.mail_name"));
			messageHelper.setSubject(sTitle);
			// true 表示启动HTML格式的邮件
			messageHelper.setText(sContent, true);

			if (files != null) {

				for (File file : files) {
					FileSystemResource fileSystemResource = new FileSystemResource(file);
					messageHelper.addAttachment(file.getName(), fileSystemResource);
				}

			}

		} catch (MessagingException e) {

			e.printStackTrace();
		}
		senderImpl.setUsername(getConfig("webcore.mail_name")); // 根据自己的情况,设置username
		senderImpl.setPassword(getConfig("webcore.mail_pass")); // 根据自己的情况,
																// 设置password
		Properties prop = new Properties();
		prop.put("mail.smtp.auth", "true"); // 将这个参数设为true，让服务器进行认证,认证用户名和密码是否正确
		prop.put("mail.smtp.timeout", "25000");
		senderImpl.setJavaMailProperties(prop);
		// 发送邮件
		senderImpl.send(mailMessage);

		return true;

	}

}
