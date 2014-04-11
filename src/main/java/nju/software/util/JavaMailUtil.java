package nju.software.util;

import org.springframework.mail.javamail.JavaMailSenderImpl;

import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.internet.MimeMessage;

import java.util.Properties;

import java.util.Date;

/**

* @author wangjian

*/

public class JavaMailUtil {
	
   public static String PreSend(String subject, String msg, String sendTo, 
      String fromMail, String user, String pw, String fromName,  String host){
            	   try{
            		 final String username = user;
            		final String pass = pw;
            		Properties properties = new Properties();
                    properties.put("mail.smtp.host", host);
            		 properties.put("mail.smtp.auth", "true");
            		 properties.put("mail.transport.protocol", "smtp");
            		 properties.put("mail.from", fromMail);
            		 JavaMailSenderImpl sender = new JavaMailSenderImpl();
            		 sender.setHost(host);
            		 sender.setUsername(username);
            		 sender.setPassword(pass);
                     MimeMessage message = sender.createMimeMessage();
            		 message.addHeader("X-Mailer", "Java Mailer");
            		 MimeMessageHelper helper = new MimeMessageHelper(message);
            		 helper.setTo(sendTo);
            		 helper.setFrom(fromMail, fromName);
            		 helper.setSubject(subject);
            		 helper.setText(msg);
            		 helper.setSentDate(new Date());
            		 sender.setJavaMailProperties(properties);
            		 sender.send(message);
            		 }catch(Exception e){
            		 System.out.println("Error:" + e);
            		 return "Failure";
            		 }
            		return "Success";
            	   
               }
	
               
               public static void send(){  
            	 String subject = "测试邮件";//标题
            	 String ReceiverMail = "460761132@qq.com";//接收者邮件
            	 String fromMail = "zhizaolianFMC@163.com";//发送者邮件
            	 String user = "zhizaolianFMC@163.com";//发送者用户
            	 String password = "nanjingdaxue";//发送者邮件密码
                 String SenderName = "zhizaolian";//发送者名字
                 String host = "smtp.163.com";//发送主机
                 String message = "测试。";//发送内容
                 String result = PreSend(subject, message, ReceiverMail, fromMail, user, password, SenderName, host);

            	 System.out.println("邮件发送结果：" + result);
               }
               
               
               
               
               
}
