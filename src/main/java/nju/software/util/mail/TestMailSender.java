package nju.software.util.mail;

public class TestMailSender {
	public static void main(String[] args) {
		// 这个类主要是设置邮件
		MailSenderInfo mailInfo = new MailSenderInfo();
		mailInfo.setMailServerHost("smtp.163.com");
		mailInfo.setMailServerPort("25");
		mailInfo.setValidate(true);
		mailInfo.setUserName("zhizaolian_nt@163.com");
		mailInfo.setPassword("zhizaolian");
		mailInfo.setFromAddress("zhizaolian_nt@163.com");
		mailInfo.setToAddress("zhizaolian_nt@163.com");
		mailInfo.setSubject("智造链 - 找回密码");
		mailInfo.setContent("请勿回复本邮件。点击下面的链接，重设密码<br/><a href=" + "http://localhost:8080/fmc" +" target='_BLANK'>点击我重新设置密码</a>" +
                "<br/>提示：本邮件超过30分钟，链接将会失效，需要重新申请");
		// 这个类主要来发送邮件
		SimpleMailSender sms = new SimpleMailSender();
		SimpleMailSender.sendTextMail(mailInfo);// 发送文文本格式
		SimpleMailSender.sendHtmlMail(mailInfo);// 发送Html格式
	}
}
