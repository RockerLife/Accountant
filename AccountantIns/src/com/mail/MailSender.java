/*
 * Amit Jain
 * Created on Sep 7, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.mail;

import java.io.File;
import java.io.FileInputStream;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.util.Context;
import com.util.InetLogger;

// Send a simple, single part, text/plain e-mail
public class MailSender {

	public String toAdd = "";
	public String fromAdd = "";
	public String subject = "";
	public String body = "";
	public String contentType = "";
	public String ccAdd = "";
	public String bccAdd = "";
	public String pdfFile = "";
	public String name = "";
	public List attachments;
	public String isSentToCC = "";
	public List images;
	public String imgPath = "";
	public String imgPath1 = "";

	private static String MAIL_HOST_SERVER;
	private static String MAIL_DEBUG;
    private static String MAIL_PORT;
	private static String MAIL_ADMIN_ADDRESS;
	private static String MAIL_ADMIN_USERID;
	private static String MAIL_ADMIN_PASSWORD;
	private static String MAIL_AUTHENTICATION_REQUIRED;
	private static String MAIL_ERROR_NOTIFICATION_ADDRESS;
	private static String MAIL_TO_ADDRESS;
	private static String MAIL_CC_ADDRESS;

	private static InetLogger logger = InetLogger.getInetLogger(MailSender.class);
	private static Properties properties;
	private static FileInputStream in;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the attachments
	 */
	public List getAttachments() {
		return attachments;
	}

	/**
	 * @param attachments the attachments to set
	 */
	public void setAttachments(List attachments) {
		this.attachments = attachments;
	}

	/**
	 * @return the pdfFile
	 */
	public String getPdfFile() {
		return pdfFile;
	}

	/**
	 * @param pdfFile the pdfFile to set
	 */
	public void setPdfFile(String pdfFile) {
		this.pdfFile = pdfFile;
	}

	/**
	 * @return Returns the body.
	 */
	public String getBody() {
		return body;
	}

	/**
	 * @param body The body to set.
	 */
	public void setBody(String body) {
		this.body = body;
	}

	/**
	 * @return Returns the fromAdd.
	 */
	public String getFromAdd() {
		return fromAdd;
	}

	/**
	 * @param fromAdd The fromAdd to set.
	 */
	public void setFromAdd(String fromAdd) {
		this.fromAdd = fromAdd;
	}

	/**
	 * @return Returns the subject.
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * @param subject The subject to set.
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * @return Returns the toAdd.
	 */
	public String getToAdd() {
		return toAdd;
	}

	/**
	 * @param toAdd The toAdd to set.
	 */
	public void setToAdd(String toAdd) {
		this.toAdd = toAdd;
	}

	/**
	 * @return Returns the contentType.
	 */
	public String getContentType() {
		return contentType;
	}

	/**
	 * @param contentType The contentType to set.
	 */
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	/**
	 * @return Returns the ccAdd
	 */
	public String getCcAdd() {
		return ccAdd;
	}

	public void setCcAdd(String ccAdd) {
		this.ccAdd = ccAdd;
	}

	public String getBccAdd() {
		return bccAdd;
	}

	public void setBccAdd(String bccAdd) {
		this.bccAdd = bccAdd;
	}

	/**
	 * @return the isSentToCC
	 */
	public String getIsSentToCC() {
		return isSentToCC;
	}

	/**
	 * @param isSentToCC the isSentToCC to set
	 */
	public void setIsSentToCC(String isSentToCC) {
		this.isSentToCC = isSentToCC;
	}

	public boolean sendMail(Context ctx) throws Exception {
		try {
			sendMail();
			return true;
		} catch (Exception mex) {
			if (MAIL_ERROR_NOTIFICATION_ADDRESS == null || "".equals(MAIL_ERROR_NOTIFICATION_ADDRESS)
					|| MAIL_ERROR_NOTIFICATION_ADDRESS.equals(toAdd))
				return false;

			MailSender errorMail = new MailSender();
			errorMail.setToAdd(MAIL_ERROR_NOTIFICATION_ADDRESS);
			errorMail.setSubject("Mail error Notification");
			StringBuffer errorBody = new StringBuffer();
			errorBody.append("A failed attempt has been made to send mail because following exception \n\r ");
			errorBody.append("Exception :- \n\r ");
			errorBody.append(mex.getMessage());
			errorBody.append("\n\r\n\r\n\r ");
			errorBody.append("Original Subject : " + subject + " \n\r ");
			errorBody.append("Original Content : " + body + "  \n\r ");
			errorMail.setBody(errorBody.toString());
			errorMail.sendMail(ctx);

			return false;
		}
	}

	public boolean sendMail() throws Exception {
		// Create properties, get Session
		Properties props = new Properties();

		props.put("mail.smtp.user", MAIL_ADMIN_USERID);
		props.put("mail.smtp.host", MAIL_HOST_SERVER);
		props.put("mail.host", MAIL_HOST_SERVER);
		props.put("mail.smtp.auth", MAIL_AUTHENTICATION_REQUIRED);
		props.put("mail.smtp.port", MAIL_PORT);
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.debug", MAIL_DEBUG);
        if("465".equals(MAIL_PORT)) {
			props.put("mail.smtp.socketFactory.port", MAIL_PORT);
			props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			props.put("mail.smtp.socketFactory.fallback", "false");
        } else {
        	props.put("mail.smtp.ssl.protocols", "TLSv1.2");
        	props.put("mail.smtp.ssl.trust", "*");
        }
		// To see what is going on behind the scene
		Authenticator auth = new SMTPAuthenticator();
		Session session = Session.getDefaultInstance(props, auth);

		// Instantiate a message
		Message msg = new MimeMessage(session);

		if (fromAdd == null || "".equals(fromAdd))
			fromAdd = MAIL_ADMIN_ADDRESS;

		if (toAdd == null || "".equals(toAdd))
			toAdd = MAIL_TO_ADDRESS;

		/*
		 * if (ccAdd == null || "".equals(ccAdd)) ccAdd = MAIL_CC_ADDRESS;
		 */
		// Set message attributes
		msg.setFrom(new InternetAddress(fromAdd, "Protexure"));

		// Destination Address
		InternetAddress[] address = { new InternetAddress(toAdd) };
		msg.setRecipients(Message.RecipientType.TO, address);

		// CC Address
		if(ccAdd != null && !"".equals(ccAdd)){       	
        	
        	InternetAddress[] ccaddress = {new InternetAddress(ccAdd)};
            msg.setRecipients(Message.RecipientType.CC, ccaddress );
        }
		 
		// BCC Address
	   if(bccAdd != null && !"".equals(bccAdd)){       	
        	
        	InternetAddress[] ccaddress = {new InternetAddress(bccAdd)};
            msg.setRecipients(Message.RecipientType.BCC, ccaddress );
        }    

		Multipart mpart = new MimeMultipart("mixed");
		BodyPart attachPart = null;

		if (attachments != null && attachments.size() > 0) {
			for (int i = 0; i < attachments.size(); i++) {
				String filePath = attachments.get(i).toString();
				String fileName = filePath.substring(filePath.lastIndexOf("/") + 1);

				File file = new File(filePath);
				FileDataSource fds = new FileDataSource(file);

				attachPart = new MimeBodyPart();
				attachPart.setDataHandler(new DataHandler(fds));
				attachPart.setFileName(fileName);

				attachPart.setDisposition(Part.ATTACHMENT);
				mpart.addBodyPart(attachPart);
			}
		}

		if (body != null) {
			BodyPart bodyPart = new MimeBodyPart();
			if (contentType != null && !"".equals(contentType))
				bodyPart.setContent(body, contentType);
			else
				bodyPart.setContent(body, "text/plain");

			mpart.addBodyPart(bodyPart);

			if (imgPath != null && !"".equals(imgPath)) {
				bodyPart = new MimeBodyPart();
				DataSource fds = new FileDataSource(getImgPath());
				bodyPart.setDataHandler(new DataHandler(fds));
				bodyPart.setHeader("Content-Type", "image/jpeg;name=image.jpg");
				bodyPart.setHeader("Content-ID", "<memememe>");
				bodyPart.setHeader("Content-Disposition", "inline");
				mpart.addBodyPart(bodyPart);
			}

			if (imgPath1 != null && !"".equals(imgPath1)) {
				bodyPart = new MimeBodyPart();
				DataSource fds = new FileDataSource(getImgPath1());
				bodyPart.setDataHandler(new DataHandler(fds));
				bodyPart.setHeader("Content-Type", "image/jpeg;name=image.jpg");
				bodyPart.setHeader("Content-ID", "<memememe1>");
				bodyPart.setHeader("Content-Disposition", "inline");
				mpart.addBodyPart(bodyPart);
			}
		}

		msg.setSubject(subject);
		msg.setSentDate(new Date());
		msg.setContent(mpart);
		Transport.send(msg);
		return true;
	}

	static {
		try {
			PropertiesLoader.loadProperties();
			MAIL_HOST_SERVER = PropertiesLoader.getSystemProperty("mail.host.server");
			MAIL_DEBUG = PropertiesLoader.getSystemProperty("mail.debug");
			MAIL_PORT = PropertiesLoader.getSystemProperty("mail.port");
            MAIL_ADMIN_ADDRESS = PropertiesLoader.getSystemProperty("mail.admin.from.address");
			MAIL_ADMIN_USERID = PropertiesLoader.getSystemProperty("mail.admin.userid");
			MAIL_ADMIN_PASSWORD = PropertiesLoader.getSystemProperty("mail.admin.password");
			MAIL_AUTHENTICATION_REQUIRED = PropertiesLoader.getSystemProperty("mail.authetication.required");
			MAIL_ERROR_NOTIFICATION_ADDRESS = PropertiesLoader.getSystemProperty("mail.admin.address");
			MAIL_TO_ADDRESS = PropertiesLoader.getSystemProperty("mail.admin.to.address");
			MAIL_CC_ADDRESS = PropertiesLoader.getSystemProperty("mail.admin.cc.address");

		} catch (Exception e) {
			System.err.println(" <*|*> Not able to initialize Mail Properties, Exception : " + e.getMessage());
			logger.error(" <*|*> Not able to initialize Mail Properties, Exception : " + e.getMessage());
		}

	}

	public static void main(String args[]) throws Exception {
		// MailSender mail = new MailSender();
		// mail.setToAdd("raghurajs@outlinesys.com");
		// mail.setCcAdd("amitj@outlinesys.com");
		// mail.setSubject("Test mail");
		// mail.setBody("Test Mail,." );
		// mail.setName("test.html");
		// mail.setPdfFile("C:\\Tomcat\\webapps\\AccountantIns\\list.html");
		// mail.sendMail();
		String path = "C:\\Tomcat\\webapps\\AccountantIns\\list.html";
		System.out.println(path.substring(path.lastIndexOf("\\") + 1));
	}

	/**
	 * SimpleAuthenticator is used to do simple authentication when the SMTP server
	 * requires it.
	 */
	private class SMTPAuthenticator extends javax.mail.Authenticator {

		public PasswordAuthentication getPasswordAuthentication() {
			String username = MAIL_ADMIN_USERID;
			String password = MAIL_ADMIN_PASSWORD;
			return new PasswordAuthentication(username, password);
		}
	}

	/**
	 * @return the images
	 */
	public List getImages() {
		return images;
	}

	/**
	 * @param images the images to set
	 */
	public void setImages(List images) {
		this.images = images;
	}

	/**
	 * @return the imgPath
	 */
	public String getImgPath() {
		return imgPath;
	}

	/**
	 * @param imgPath the imgPath to set
	 */
	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	/**
	 * @return the imgPath1
	 */
	public String getImgPath1() {
		return imgPath1;
	}

	/**
	 * @param imgPath1 the imgPath1 to set
	 */
	public void setImgPath1(String imgPath1) {
		this.imgPath1 = imgPath1;
	}

}