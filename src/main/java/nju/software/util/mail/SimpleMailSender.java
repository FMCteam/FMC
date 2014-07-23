package nju.software.util.mail;

import java.util.Date;
import java.util.Properties;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * @author luxiangfan
 * 简单邮件（不带附件的邮件）发送器   
 */
public class SimpleMailSender {
	/**
	 * 以文本格式发送邮件
	 * @param mailInfo 待发送的邮件的信息
	 * @return
	 */
	public static boolean sendTextMail(MailSenderInfo mailInfo) {
		// 判断是否需要身份认证
		MyAuthenticator authenticator = null;
		Properties pro = mailInfo.getProperties();
		if (mailInfo.isValidate()) {
			// 如果需要身份认证，则创建一个密码验证器
			authenticator = new MyAuthenticator(mailInfo.getUserName(),
					mailInfo.getPassword());
		}
		// 根据邮件会话属性和密码验证器构造一个发送邮件的session
		Session sendMailSession = Session
				.getDefaultInstance(pro, authenticator);
		try {

			Message mailMessage = new MimeMessage(sendMailSession);// 根据session创建一个邮件消息
			Address from = new InternetAddress(mailInfo.getFromAddress());// 创建邮件发送者地址
			mailMessage.setFrom(from);// 设置邮件消息的发送者
			Address to = new InternetAddress(mailInfo.getToAddress());// 创建邮件的接收者地址，并设置到邮件消息中
			mailMessage.setRecipient(Message.RecipientType.TO, to);
			mailMessage.setSubject(mailInfo.getSubject());// 设置邮件消息的主题
			mailMessage.setSentDate(new Date());// 设置邮件消息发送的时间
			String mailContent = mailInfo.getContent();// 设置邮件消息的主要内容
			mailMessage.setText(mailContent);
			Transport.send(mailMessage);// 发送邮件

			return true;
		} catch (MessagingException ex) {
			ex.printStackTrace();
		}
		return false;
	}

	/**
	 * 以HTML格式发送邮件
	 * @param mailInfo 待发送的邮件信息
	 * @return
	 */
	public static boolean sendHtmlMail(MailSenderInfo mailInfo) {
		// 判断是否需要身份认证
		MyAuthenticator authenticator = null;
		Properties pro = mailInfo.getProperties();
		// 如果需要身份认证，则创建一个密码验证器
		if (mailInfo.isValidate()) {
			authenticator = new MyAuthenticator(mailInfo.getUserName(),
					mailInfo.getPassword());
		}
		// 根据邮件会话属性和密码验证器构造一个发送邮件的session
		Session sendMailSession = Session
				.getDefaultInstance(pro, authenticator);
		try {

			Message mailMessage = new MimeMessage(sendMailSession);// 根据session创建一个邮件消息
			Address from = new InternetAddress(mailInfo.getFromAddress());// 创建邮件发送者地址
			mailMessage.setFrom(from);// 设置邮件消息的发送者
			Address to = new InternetAddress(mailInfo.getToAddress());// 创建邮件的接收者地址，并设置到邮件消息中
			mailMessage.setRecipient(Message.RecipientType.TO, to);// Message.RecipientType.TO属性表示接收者的类型为TO
			mailMessage.setSubject(mailInfo.getSubject());// 设置邮件消息的主题
			mailMessage.setSentDate(new Date());// 设置邮件消息发送的时间
			Multipart mainPart = new MimeMultipart();// MiniMultipart类是一个容器类，包含MimeBodyPart类型的对象
			BodyPart html = new MimeBodyPart();// 创建一个包含HTML内容的MimeBodyPart
			html.setContent(mailInfo.getContent(), "text/html; charset=utf-8");// 设置HTML内容
			mainPart.addBodyPart(html);
			mailMessage.setContent(mainPart);// 将MiniMultipart对象设置为邮件内容
			Transport.send(mailMessage);// 发送邮件

			return true;
		} catch (MessagingException ex) {
			ex.printStackTrace();
		}
		return false;
	}
}
