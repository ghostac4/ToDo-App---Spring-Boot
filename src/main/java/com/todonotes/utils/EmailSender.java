package com.todonotes.utils;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailSender {

	@Autowired
	private JavaMailSender emailSender;
	private static final Logger LOGGER = LoggerFactory.getLogger(EmailSender.class);
	private String verifyLink ="http://localhost:8081/verifyEmail/";
	private String message = "<!DOCTYPE html>\n" + 
			"<html lang=\"en\" dir=\"ltr\">\n" + 
			"<head>\n" + 
			"  <meta charset=\"utf-8\">\n" + 
			"  <title></title>\n" + 
			"</head>\n" + 
			"<body>\n" + 
			"  <div style=\"width: 50%;height: auto;margin: 0 auto;\">\n" + 
			"    <p style=\"color: #205081;font-size: 20px;line-height: 32px;text-align: left;font-weight: bold;\n" + 
			"	font-family: Helvetica neue, Helvetica, Arial, Verdana, sans-serif;\">\n" + 
			"       Please verify your email address</p>\n" + 
			"    <hr>\n" + 
			"    <div style=\"width: 70%; margin: 0 auto;\">\n" + 
			"      <img src=\"https://s3.us-east-2.amazonaws.com/fundoo-notes-images/616757999-USER_1522749770_nature.jpeg\" alt=\"todo-note\" width=\"450px\" height=\"270px\">\n" + 
			"    </div>\n" + 
			"    <p style=\"color: #333333;\n" + 
			"      font-size: 14px;\n" + 
			"      line-height: 24px;font-family: Helvetica neue, Helvetica, Arial, Verdana, sans-serif;\">\n" + 
			"      Hi $NAME$,<br> Please verify your email address so we know that it's really you!</p>\n" + 
			"    <button style=\"background-color: #3572b0; border: none;color: white;\n" + 
			"      padding: 10px; text-align: center; text-decoration: none;display: inline-block;\n" + 
			"      font-size: 16px;height: 40px;width: 200px;margin: 4px 2px;cursor: pointer;\n" + 
			"      border-radius: 8px;\" ><a style=\" text-decoration: none; color: white;\" href=\"$LINK$\" target=\"_blank\">Verify my email address</a></button>\n" + 
			"    <p style=\"color: #333333;\n" + 
			"      font-size: 14px;\n" + 
			"      line-height: 24px;font-family: Helvetica neue, Helvetica, Arial, Verdana, sans-serif;\">\n" + 
			"			Cheers, <br> The TodoNotes</p>\n" + 
			"    <hr>\n" + 
			"    <p style=\"color: #707070;\n" + 
			"      font-size: 12px;\n" + 
			"      line-height: 18px;\n" + 
			"      text-align: center;font-family: Helvetica neue, Helvetica, Arial, Verdana, sans-serif;\">This message was sent to you by Todo Note\n" + 
			"    </p>\n" + 
			"  </div>\n" + 
			"</body>\n" + 
			"</html>\n" + 
			"";

	private void sendEmail(String to) {
		String data[] = to.split(";");
		try {
			MimeMessage msg = emailSender.createMimeMessage();
			msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
			msg.addHeader("format", "flowed");
			msg.addHeader("Content-Transfer-Encoding", "8bit");
			msg.setFrom(new InternetAddress(data[0], "Todo Notes"));
			msg.setReplyTo(InternetAddress.parse("ghostac04@gmail.com", false));
			msg.setSubject("ToDo Notes - Verification Mail", "UTF-8");
			msg.setSentDate(new Date());
			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(data[0], false));

			BodyPart messageBodyPart = new MimeBodyPart();
			message = message.replace("$NAME$", data[1]);
			message = message.replace("$LINK$", verifyLink+data[2]);
			messageBodyPart.setContent(message, "text/html");

			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);
			msg.setContent(multipart);

			emailSender.send(msg);
			LOGGER.info("Email sent successfully to : "+data[0]);
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public void receiveMessage(String email) {
		LOGGER.info("Received <" + email + ">");
		sendEmail(email);
	}
}
