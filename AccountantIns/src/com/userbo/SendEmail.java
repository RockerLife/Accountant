package com.userbo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.mail.MailSender;
import com.util.Context;
import com.util.IContext;
import com.util.InetLogger;
import com.util.SystemProperties;

public class SendEmail {
	private static InetLogger logger = InetLogger.getInetLogger(SendEmail.class);
	public static void validateRiskEmail(IContext ctx) throws Exception {
		if (ctx.get("NatureofInquiry") == null)
			AccountUtils.populateError(ctx, "NatureofInquiry",
					"Nature of Inquiry is required");
		else if (ctx.get("NatureofInquiry") != null
				&& "".equals(ctx.get("NatureofInquiry").toString()))
			AccountUtils.populateError(ctx, "NatureofInquiry",
					"Nature of Inquiry is required");

		if (ctx.get("Description") == null)
			AccountUtils.populateError(ctx, "Description",
					"Description is required");
		else if (ctx.get("Description") != null
				&& "".equals(ctx.get("Description").toString()))
			AccountUtils.populateError(ctx, "Description",
					"Description is required");

	}

	public static void validateCommentsEmail(IContext ctx) throws Exception {
		if (ctx.get("Comments") == null)
			AccountUtils.populateError(ctx, "Comments", "Comments is required");
		else if (ctx.get("Comments") != null
				&& "".equals(ctx.get("Comments").toString()))
			AccountUtils.populateError(ctx, "Comments", "Comments is required");
	}

	public void sendRiskEmail(IContext ctx) throws Exception {
		// String dstId = SystemProperties.getInstance().getString("appl." +
		// ctx.getProject() +".admin.email");
		String dstId = SystemProperties.getInstance().getString(
				"appl." + ctx.getProject() + ".riskmanagement.email");
		if ("".equals(dstId)) {
			ctx.put("MailSent", 'N');
			return;
		}

//		String isMailing = SystemProperties.getInstance().getString(
//				"Insured.sendmail");
//
//		if (isMailing != null && "Y".equals(isMailing) && !"".equals(dstId)) {
			MailSender mailSender = new MailSender();
			mailSender.setSubject("My Policy  "
					+ ctx.get("AccountName").toString());
			mailSender.setToAdd(dstId);
			mailSender.setIsSentToCC("Y");
			mailSender.setContentType("text/html");

			mailSender.setBody(generateRiskLetterBody(ctx));
			// mailSender.setAttachments(attachments);

			mailSender.sendMail();
			ctx.put("MailSent", 'Y');
//		}

	}

	private String generateRiskLetterBody(IContext ctx) throws Exception {

		String msg = "";
		msg = "Nature of Inquiry = " + ctx.get("NatureofInquiry").toString()
				+ "<br/>";
		msg = msg + "Description  = " + ctx.get("Description").toString()
				+ "<br/>";
		msg = msg + "Firm Name = " + ctx.get("AccountName").toString()
				+ "<br/>";

		msg = msg + "Policy No = " + ctx.get("PolicyKey").toString() + "<br/>";

		msg = msg + "Email = " + ctx.get("AccountEmail").toString() + "<br/>";

		String policyEffectivedate = (String)ctx.get("PolicyEffectiveDate");
		policyEffectivedate=policyEffectivedate.trim();
		String date = getFormatDate(policyEffectivedate);	
		msg = msg + "Policy Effective Date = " + date+ "<br/>";

		return msg;
	}

	public void sendComment(IContext ctx) throws Exception {
		// String dstId = SystemProperties.getInstance().getString("appl." +
		// ctx.getProject() +".admin.email");
		String dstId = SystemProperties.getInstance().getString(
				"appl." + ctx.getProject() + ".riskmanagement.email");
		if ("".equals(dstId)) {
			ctx.put("MailSent", 'N');
			return;
		}
		
//		String isMailing = SystemProperties.getInstance().getString(
//				"Insured.sendmail");
//
//		if (isMailing != null && "Y".equals(isMailing) && !"".equals(dstId)) {
			MailSender mailSender = new MailSender();
			mailSender.setSubject("Comments  "
					+ ctx.get("AccountName").toString());
			mailSender.setToAdd(dstId);
			mailSender.setIsSentToCC("Y");
			mailSender.setContentType("text/html");

			mailSender.setBody(generateCommentEmailLetterBody(ctx));
			// mailSender.setAttachments(attachments);

			mailSender.sendMail();
			ctx.put("MailSent", 'Y');

//		}

	}

	private String generateCommentEmailLetterBody(IContext ctx) {

		String msg = "";

		msg = "Firm Name = " + ctx.get("AccountName").toString() + "<br/>";

		msg = msg + "Policy No = " + ctx.get("PolicyKey").toString() + "<br/>";

		msg = msg + "Email = " + ctx.get("AccountEmail").toString() + "<br/>";

		msg = msg + "Comment = " + ctx.get("Comments").toString() + "<br/>";
		
		
		String policyEffectivedate = (String)ctx.get("PolicyEffectiveDate");
		policyEffectivedate=policyEffectivedate.trim();
		String date = getFormatDate(policyEffectivedate);	
		msg = msg + "Policy Effective Date = " + date+ "<br/>";


		return msg;
	}

	public void sendPolicy(IContext ctx) throws Exception {
		String dstId = SystemProperties.getInstance().getString(
				"appl." + ctx.getProject() + ".riskmanagement.email");
		if ("".equals(dstId)) {
			ctx.put("MailSent", 'N');
			return;
		}

//		String isMailing = SystemProperties.getInstance().getString(
//				"Insured.sendmail");
//
//		if (isMailing != null && "Y".equals(isMailing) && !"".equals(dstId)) {
			MailSender mailSender = new MailSender();
			mailSender.setSubject("Risk Management  "
					+ ctx.get("AccountName").toString());
			mailSender.setToAdd(dstId);
			mailSender.setIsSentToCC("Y");
			mailSender.setContentType("text/html");

			mailSender.setBody(generatePolicyLetterBody(ctx));
			// mailSender.setAttachments(attachments);

			mailSender.sendMail();
			ctx.put("MailSent", 'Y');

//		}

	}

	private String generatePolicyLetterBody(IContext ctx) {
		String msg = "";

		msg = "Firm Name = " + ctx.get("AccountName").toString() + "<br/>";

		msg = msg + "Policy No = " + ctx.get("PolicyKey").toString() + "<br/>";

		msg = msg + "Email = " + ctx.get("AccountEmail").toString() + "<br/>";

		String policyEffectivedate = (String)ctx.get("PolicyEffectiveDate");
		policyEffectivedate=policyEffectivedate.trim();
		String date = getFormatDate(policyEffectivedate);	
		
		msg = msg + "Policy Effective Date = " + date+ "<br/>";

		return msg;
	}
	
	
	private String getFormatDate(String obj){
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		String strDate=null;
		try
		{
		Date date=df.parse(obj);
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		 strDate = formatter.format(date);
		logger.debug(strDate);
		}catch(Exception e)
		{
			logger.error("Error in SendEmail.getFormatDate", e);}
		return strDate;
	}
	
	
	
	public static void main(String args[]) {
		Context ctx = new Context();
		
		ctx.put("PolicyEffectiveDate", "2011-02-25 00:00:00.0");

		String policyEffectivedate = ctx.get("PolicyEffectiveDate").toString();
//		Date dt1 = null;
//		try {
//			String subStrDate = policyEffectivedate.substring(0, 10);
//			System.out.println(subStrDate);
//			//	    
//		} catch (Exception e) {
//			// System.out.println(e.getMessage());
//
//		}
//		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

		
	
		
		
	}

}
