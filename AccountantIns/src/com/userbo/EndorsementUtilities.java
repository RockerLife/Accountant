package com.userbo;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.mail.MailSender;
import com.manage.managecomponent.components.SetParametersForStoredProcedures;
import com.manage.managemetadata.functions.commonfunctions.RuleUtils;
import com.ormapping.ibatis.SqlResources;
import com.util.Context;
import com.util.HtmlConstants;
import com.util.IContext;
import com.util.InetLogger;
import com.util.MessageStringFormat;
import com.util.StringUtils;
import com.util.SystemProperties;

public class EndorsementUtilities {
	private static InetLogger logger = InetLogger.getInetLogger(EndorsementUtilities.class);
	public static void calculateCancellationPremium(Context ctx)
	{
		DecimalFormat  formatter = new DecimalFormat("#0.00");   
		int termDays=0,enndorsementDays=0;
		int cReason=ctx.get("cancellationReason")!=null && !ctx.get("cancellationReason").equals(HtmlConstants.EMPTY)?Integer.parseInt(ctx.get("cancellationReason").toString()):0;
		int cType=ctx.get("cancellationType")!=null && !ctx.get("cancellationType").equals(HtmlConstants.EMPTY)?Integer.parseInt(ctx.get("cancellationType").toString()):0;
		if(cReason==1 || cReason==2)
		{
			cType=2;
			ctx.put("cancellationType",2);
		}
		if(cType==1)
		{
			ctx.put("cancellationReturnPremium", ctx.get("TotalPremium"));
			ctx.put("cancellationReturnTax", ctx.get("TotalPolicyTaxAmount"));
			ctx.put("cancellationTotalReturnPremium", ctx.get("InvoicedPremium"));
			ctx.put("premimDifference", ctx.get("InvoicedPremium"));
			ctx.put("returnPremium","-"+ctx.get("TotalPremium"));
			ctx.put("returnTax","-"+ctx.get("TotalPolicyTaxAmount"));
		}
		if(cType==2)
		{
			
			//calculation of pro rata for unearned premium
			ctx.put("Date1", new SimpleDateFormat("MM/dd/yyyy").format(new Date(ctx.get("PolicyEffectiveDate").toString())));
			ctx.put("Date2", new SimpleDateFormat("MM/dd/yyyy").format(new Date(ctx.get("CancellationEffectiveDate").toString())));
			getDaysDifference(ctx);
			termDays=ctx.get("TermDays")!=null && !ctx.get("TermDays").equals(HtmlConstants.EMPTY)?Integer.parseInt(ctx.get("TermDays").toString()):0;
			enndorsementDays=ctx.get("EndorsementDays")!=null && !ctx.get("EndorsementDays").equals(HtmlConstants.EMPTY)?Integer.parseInt(ctx.get("EndorsementDays").toString()):0;
//			long days=(DateUtils.calculateDiffInDays(new Date(ctx.get("CancellationEffectiveDate").toString()),new Date(ctx.get("PolicyEffectiveDate").toString())));
			double premium=ctx.get("TotalPremium")!=null && !ctx.get("TotalPremium").equals(HtmlConstants.EMPTY)?Double.parseDouble(ctx.get("TotalPremium").toString()):0.0;
			double tax=ctx.get("TotalPolicyTaxAmount")!=null && !ctx.get("TotalPolicyTaxAmount").equals(HtmlConstants.EMPTY)?Double.parseDouble(ctx.get("TotalPolicyTaxAmount").toString()):0.0;
			double unearnedPremium=premium-(premium/termDays)*enndorsementDays;
			double unearnedTax=tax-(tax/termDays)*enndorsementDays;
			double totalOldPremium=Math.round(premium+tax);
			unearnedPremium=Math.round(unearnedPremium);
			unearnedTax=Math.round(unearnedTax);
			double totalunearnedPremium=unearnedPremium+unearnedTax;
			double premimDifference=totalOldPremium-totalunearnedPremium;
			ctx.put("cancellationReturnPremium", formatter.format(unearnedPremium));
			ctx.put("cancellationReturnTax", formatter.format(unearnedTax));
			ctx.put("cancellationTotalReturnPremium",formatter.format(totalunearnedPremium));
			ctx.put("totalOldPremium",totalOldPremium);
			ctx.put("premimDifference",formatter.format(premimDifference));
			ctx.put("returnPremium","-"+unearnedPremium);
			ctx.put("returnTax","-"+unearnedTax);
		}
		
		if(cType==3)
		{
			double percent=0.90;
			ctx.put("Date1", new SimpleDateFormat("MM/dd/yyyy").format(new Date(ctx.get("PolicyEffectiveDate").toString())));
			ctx.put("Date2", new SimpleDateFormat("MM/dd/yyyy").format(new Date(ctx.get("CancellationEffectiveDate").toString())));
			getDaysDifference(ctx);
			termDays=ctx.get("TermDays")!=null && !ctx.get("TermDays").equals(HtmlConstants.EMPTY)?Integer.parseInt(ctx.get("TermDays").toString()):0;
			enndorsementDays=ctx.get("EndorsementDays")!=null && !ctx.get("EndorsementDays").equals(HtmlConstants.EMPTY)?Integer.parseInt(ctx.get("EndorsementDays").toString()):0;
//			long days=(DateUtils.calculateDiffInDays(new Date(ctx.get("CancellationEffectiveDate").toString()),new Date(ctx.get("PolicyEffectiveDate").toString())));
			double premium=ctx.get("TotalPremium")!=null && !ctx.get("TotalPremium").equals(HtmlConstants.EMPTY)?Double.parseDouble(ctx.get("TotalPremium").toString()):0.0;
			double tax=ctx.get("TotalPolicyTaxAmount")!=null && !ctx.get("TotalPolicyTaxAmount").equals(HtmlConstants.EMPTY)?Double.parseDouble(ctx.get("TotalPolicyTaxAmount").toString()):0.0;
			double unearnedPremium=(premium-(double) Math.round(((premium/termDays)*enndorsementDays) * 100) / 100)*percent;
			double unearnedTax=(tax-(double) Math.round(((tax/termDays)*enndorsementDays) * 100) / 100)*percent;
			double totalOldPremium=Math.round(premium+tax);
			unearnedPremium=Math.round(unearnedPremium);
			unearnedTax=Math.round(unearnedTax);
			double totalunearnedPremium=unearnedPremium+unearnedTax;
			double premimDifference=totalOldPremium-totalunearnedPremium;
			ctx.put("cancellationReturnPremium", formatter.format(unearnedPremium));
			ctx.put("cancellationReturnTax", formatter.format(unearnedTax));
			ctx.put("cancellationTotalReturnPremium",formatter.format(totalunearnedPremium));
			ctx.put("totalOldPremium",totalOldPremium);
			ctx.put("premimDifference",formatter.format(premimDifference));
			ctx.put("returnPremium","-"+unearnedPremium);
			ctx.put("returnTax","-"+unearnedTax);
		}
		
		
	}
	public static void validateCancelEndorsementManually(Context ctx) throws Exception
	{
		//int cReason=ctx.get("cancellationReason")!=null && !ctx.get("cancellationReason").equals(HtmlConstants.EMPTY)?Integer.parseInt(ctx.get("cancellationReason").toString()):0;
		int cType=ctx.get("cancellationType")!=null && !ctx.get("cancellationType").equals(HtmlConstants.EMPTY)?Integer.parseInt(ctx.get("cancellationType").toString()):0;
		try	
		{
		if(cType==1)
		{
			Object objRule = RuleUtils.executeRule(ctx, "AccountantRule.cancelFlatEndorsementDateValidation");
			logger.debug("Going to validate Cancel Reinstate Endorsement ");
			if(objRule != null)
			{
				Boolean rule = (Boolean)objRule;
				if(rule==false)
					AccountUtils.populateError(ctx, "CancellationEffectiveDate","Cancellation effective Date should be equal to policy effective date.");
			}
			
		}
		}
		catch(Exception e)
		{
			logger.error("exception occured while validating Cancel Reinstate Endorsement  "+e, e);
			
		}
	}
	public static void calculateReinstatementPremium(Context ctx)
	{
		
		DecimalFormat  formatter = new DecimalFormat("#0.00");  
		double premium=ctx.get("TotalPremium")!=null && !ctx.get("TotalPremium").equals(HtmlConstants.EMPTY)?Double.parseDouble(ctx.get("TotalPremium").toString()):0.0;
		double tax=ctx.get("TotalPolicyTaxAmount")!=null && !ctx.get("TotalPolicyTaxAmount").equals(HtmlConstants.EMPTY)?Double.parseDouble(ctx.get("TotalPolicyTaxAmount").toString()):0.0;
		
		ctx.put("reinstatementReturnPremium", formatter.format(premium));
		ctx.put("reinstatementReturnTax", formatter.format(tax));
		ctx.put("reinstatementTotalReturnPremium", formatter.format(premium+tax));
		ctx.put("returnPremium",premium);
		ctx.put("returnTax",tax);
		
	}
	public static void setCurrentLimitData(Context ctx)
	{
	
		
		try {
					
				new SetParametersForStoredProcedures().setParametersInContext(ctx, "PolicyKey");
			List dataList=SqlResources.getSqlMapProcessor(ctx).select("PolicyTransaction.GetCurrentLimitDeductiblesData_p",ctx);
			if(dataList!=null && dataList.size()>0)
			{
				ctx.putAll((Map) dataList.get(0));
				
			}
			ctx.put("currentPolicyLimit", ctx.get("currentLimit"));
			ctx.put("currentPolicyClaimExpensesType", ctx.get("currentClaimExpensesType"));
			ctx.put("currentPolicyDollarDefense", ctx.get("currentDollarDefense"));
			ctx.put("currentPolicyDeductible", ctx.get("currentDeductible"));
			ctx.put("currentPolicyClaimOptionType", ctx.get("currentClaimOptionType"));
			ctx.put("IsClaimOptionType1", ctx.get("IsClaimOptionType"));
			ctx.put("IsDollarDefense1", ctx.get("IsDollarDefense"));
			
			}
		
			catch (Exception e) {
				logger.error("Error in EndorsementUtilities.setCurrentLimitData", e);
				}
	}
	public static void setRatingReponseValues(Context ctx)
	{
	
		
		try {
			
				/*new SetParametersForStoredProcedures().setParametersInContext(ctx, "PolicyKey,AccountKey,AddressKey,LastUpdateUserID,DateOfEndorsement,TypeOfEndorsement");
				List TransactionList=SqlResources.getSqlMapProcessor(ctx).select("Firm.LimitDeductiblesEndorsementLW_p",ctx);
				if(TransactionList!=null && TransactionList.size()>0)
					ctx.putAll((Map) TransactionList.get(0));*/
				
				new SetParametersForStoredProcedures().setParametersInContext(ctx, "PolicyKey");
				List dataList=SqlResources.getSqlMapProcessor(ctx).select("PolicyTransaction.ParseXMLToRequestAC_p",ctx);
				if(dataList!=null && dataList.size()>0)
					ctx.putAll((Map) dataList.get(0));
				
			/*	SqlResources.getSqlMapProcessor(ctx).update("SqlStmts.UserStatementManualBoQueriesupdatePolicyTransaction",ctx);
				SqlResources.getSqlMapProcessor(ctx).update("SqlStmts.UserStatementManualBoQueriesupdatePolicyCoverage",ctx);*/
				
			}
		
			catch (Exception e) {
				logger.error("Error in EndorsementUtilities.setRatingReponseValues", e);
				}
	}
	
	public static void fetchDataForEmail(Context ctx,String outFile)
	{
		
		try{
			String htmlDir = SystemProperties.getInstance().getString("html.basedir");
	    	String email_notification_event_name = ctx.get("event_name").toString();
	    	Context newCtx = new Context();
	    	newCtx.setProject(ctx.getProject());
	    	
	    	newCtx.put("UserNo", ctx.get("UserNo"));
	    	newCtx.put("PolicyKey", ctx.get("PolicyKey"));
	    	newCtx.put("event_name", email_notification_event_name);
	    	
	    	//going to get event description from database
	    	Object obj = SqlResources.getSqlMapProcessor(newCtx).select("SqlStmts.UserStatementManualBoQueriesgetEmailNotificationData", newCtx);
	    	if(obj != null && obj instanceof List){
	    		List list = (List)obj;
	    		for(int i=0; i<list.size(); i++){
	    			Map map = (Map)list.get(i);
	    			
	    			if(i > 0){
		    			ctx.put(HtmlConstants.EMAIL_NOTIFICATION_AGENCY_FROMEMAIL_ADDRESS, ctx.get(HtmlConstants.EMAIL_NOTIFICATION_AGENCY_FROMEMAIL_ADDRESS + "_"+i));
		    			ctx.put(HtmlConstants.EMAIL_NOTIFICATION_AGENCY_TOEMAIL_ADDRESS, ctx.get(HtmlConstants.EMAIL_NOTIFICATION_AGENCY_TOEMAIL_ADDRESS + "_"+i));
		    			ctx.put(HtmlConstants.EMAIL_NOTIFICATION_AGENCY_CCEMAIL_ADDRESS, ctx.get(HtmlConstants.EMAIL_NOTIFICATION_AGENCY_CCEMAIL_ADDRESS + "_"+i));
	    			}
	    			
	    			//sendEmailNotification(ctx, newCtx, map);
	    			sendEmailNotification(ctx, map,outFile);
	    			ctx.put("isEmailSent", "Y");
	    		}
	    	}
    	}catch (Exception e) {
            logger.error("Unable to send email notification due to error : " + e.getMessage(), e);
		}
		
	}
	private static void sendEmailNotification(Context ctx, Map map, String outFile) throws Exception {
		Object  obj = SqlResources.getSqlMapProcessor(ctx).findByKey("SqlStmts.UserStatementManualBoQueriesgetEndorsementAccountDetails", ctx);
	
	if(obj != null && obj instanceof Map){
        Map map1 = (Map)obj;
        ctx.putAll(map1);
    }
	
	
	
		List attachments = new ArrayList();
		attachments.add(outFile);	
		String htmlDir = SystemProperties.getInstance().getString("html.basedir");
		String to_email_address = null;
		String cc_email_address = null;
		String from_email_address = null;
		String subject = null;
		String bodyContent = null;
		
		String from_role_desc = map.get("from_role_desc") != null ? map.get("from_role_desc").toString() : null;
		String to_role_desc = map.get("to_role_desc") != null ? map.get("to_role_desc").toString() : null;
		String cc_role_desc = map.get("cc_role_desc") != null ? map.get("cc_role_desc").toString() : null;
		String email_template = map.get("email_template") != null ? map.get("email_template").toString() : null;
		
		String attachment_path = map.get("attachment_path") != null ? map.get("attachment_path").toString() : null;
		String attachment_content = map.get("attachment_content") != null ? map.get("attachment_content").toString() : null;
		ctx.put("Firm_Name", ctx.get("AccountName"));
		ctx.put("Quote_no", ctx.get("QuoteNumber"));
		ctx.put("PolicyNumber", ctx.get("PolicyNumber"));
		ctx.put("EffectiveDate", ctx.get("PolicyEffectiveDate"));
		ctx.put("ExpirationDate", ctx.get("PolicyExpirationDate"));
		ctx.put("CancellationDate",ctx.get("TransactionEffectiveDate"));
		String environmentVar = null;
		environmentVar = SystemProperties.getInstance().getString("appl." + ctx.getProject() + ".environment");
		//environmentVar="PD";
		if(ctx.get("isSendMailTrue") != null && !HtmlConstants.EMPTY.equals(ctx.get("isSendMailTrue").toString())&& "Y".equals(ctx.get("isSendMailTrue"))){
			
			boolean producerCodeExist = new AccountantValidationUtils().checkIfSubProducerExist(ctx);
			if (producerCodeExist)
			{
				try 
				{
					to_role_desc = SystemProperties.getInstance().getString("appl." + ctx.getProject() + ".admin.email");
				}
				catch (Exception e) 
				{logger.error("error in getting admin email address", e);}

			} else {
				to_role_desc = AccountUtils.getInsuredAccountEmail(ctx);
			}
		if(environmentVar.equals("DEV") || environmentVar.equals("QA")|| environmentVar.equals("UAT"))
		{
			from_role_desc=SystemProperties.getInstance().getString("mail.admin.from.address");
			to_role_desc = SystemProperties.getInstance().getString("mail.admin.to.address");
			cc_role_desc = SystemProperties.getInstance().getString("mail.admin.cc.address");
		}
		from_role_desc=SystemProperties.getInstance().getString("mail.admin.from.address");
		cc_role_desc = SystemProperties.getInstance().getString("mail.admin.cc.address");
		subject = map.get("subject") != null ? map.get("subject").toString() : HtmlConstants.EMPTY;
		if(!StringUtils.isBlank(subject))
			subject = new MessageStringFormat(ctx).format(subject);
		
		bodyContent = map.get("body") != null ? map.get("body").toString() : HtmlConstants.EMPTY;
		if(!StringUtils.isBlank(bodyContent))
			bodyContent = new MessageStringFormat(ctx).format(bodyContent);
		
		if ("".equals(to_role_desc))
				return;
			
				MailSender mailSender = new MailSender();
				mailSender.setContentType("text/html");
				
				mailSender.setFromAdd(from_role_desc);
				mailSender.setToAdd(to_role_desc);
				mailSender.setCcAdd(cc_role_desc);
				mailSender.setSubject(subject);
				mailSender.setBody(bodyContent);
				if(attachments!=null && attachments.size()>0)
				mailSender.setAttachments(attachments);
				mailSender.sendMail(ctx);
				
				logger.debug("Mail Sent");
				ctx.remove("email_notification_event_name");
			}
			
		}
	
  public static void setEndorsementPremium(Context ctx)
  {
	  DecimalFormat  formatter = new DecimalFormat("#0.00");   
	  int termDays=0,enndorsementDays=0;
	  try
	  {
		  List dataList = SqlResources.getSqlMapProcessor(ctx).select("SqlStmts.UserStatementManualBoQueriesgetFinilizedPremium", ctx);
		  if(dataList!=null && dataList.size()>0)
			{
				ctx.putAll((Map) dataList.get(0));
				
			}
		 
		 
		  	if("9".equals(ctx.get("EndorsementType").toString()))
		  			{
			  double litmitsEndorsementPremium=ctx.get("litmitsEndorsementPremium")!=null && !ctx.get("litmitsEndorsementPremium").equals(HtmlConstants.EMPTY)? Double.parseDouble(ctx.get("litmitsEndorsementPremium").toString()):0;
			  double litmitsEndorsementTax=ctx.get("litmitsEndorsementTax")!=null && !ctx.get("litmitsEndorsementTax").equals(HtmlConstants.EMPTY)? Double.parseDouble(ctx.get("litmitsEndorsementTax").toString()):0;
			  double totalPremium=ctx.get("TotalPremium")!=null && !ctx.get("TotalPremium").equals(HtmlConstants.EMPTY)? Double.parseDouble(ctx.get("TotalPremium").toString()):0;
			  double totalPolicyTaxAmount=ctx.get("TotalPolicyTaxAmount")!=null && !ctx.get("TotalPolicyTaxAmount").equals(HtmlConstants.EMPTY)? Double.parseDouble(ctx.get("TotalPolicyTaxAmount").toString()):0;
			  double premiumDifference=litmitsEndorsementPremium-totalPremium;
			  double taxDifference= litmitsEndorsementTax-totalPolicyTaxAmount;
				//  long days=(DateUtils.calculateDiffInDays(new Date(ctx.get("limitEndorsementEffectiveDate").toString()),new Date(ctx.get("PolicyEffectiveDate").toString())));
				 
				  //long days=(DateUtils.calculateDiffInDays(new Date(ctx.get("limitEndorsementEffectiveDate").toString()),new Date(ctx.get("PolicyEffectiveDate").toString())));
//				String limitEndorsementEffectiveDate=new SimpleDateFormat("MM/dd/yyyy").format(new Date(ctx.get("limitEndorsementEffectiveDate").toString()));
//				String policyEffectiveDate=new SimpleDateFormat("MM/dd/yyyy").format(new Date(ctx.get("PolicyEffectiveDate").toString()));
				ctx.put("Date1", new SimpleDateFormat("MM/dd/yyyy").format(new Date(ctx.get("PolicyEffectiveDate").toString())));
				ctx.put("Date2", new SimpleDateFormat("MM/dd/yyyy").format(new Date(ctx.get("limitEndorsementEffectiveDate").toString())));
				getDaysDifference(ctx);
				termDays=ctx.get("TermDays")!=null && !ctx.get("TermDays").equals(HtmlConstants.EMPTY)?Integer.parseInt(ctx.get("TermDays").toString()):0;
				enndorsementDays=ctx.get("EndorsementDays")!=null && !ctx.get("EndorsementDays").equals(HtmlConstants.EMPTY)?Integer.parseInt(ctx.get("EndorsementDays").toString()):0;
//				long days=(DateUtils.calculateDiffInDays(new Date(limitEndorsementEffectiveDate),new Date(policyEffectiveDate)));
				  
				double earnedPremium=(premiumDifference/termDays)*enndorsementDays;
				double earnedTax=(taxDifference/termDays)*enndorsementDays;
				double unearnedPremium=Math.round(premiumDifference-earnedPremium);
				double unearnedTax=Math.round(taxDifference-earnedTax);
				double result=Math.round((totalPremium+totalPolicyTaxAmount)+(unearnedPremium+unearnedTax));
				//double netPremium=premiumDifference+taxDifference;
				
				double premiumToShow=totalPremium+unearnedPremium+unearnedTax;
				ctx.put("returnPremium",unearnedPremium);
	  			ctx.put("returnTax",unearnedTax);
				ctx.put("litmitsEndorsementPremiumNew",formatter.format(unearnedPremium));
				ctx.put("litmitsEndorsementTotalPremiumNew",formatter.format(unearnedPremium+unearnedTax));
				ctx.put("litmitsEndorsementTaxNew",formatter.format(unearnedTax));
				ctx.put("litmitsEndorsementPremiumNew1",formatter.format(unearnedPremium+unearnedTax));
				ctx.put("prorataPremium",formatter.format(result));
				ctx.put("premiumToShow",formatter.format(premiumToShow));  
				  

		  			}
		  	else if("10".equals(ctx.get("EndorsementType").toString()))
		  	{
		  		double deductibleEndorsementPremium=ctx.get("deductibleEndorsementPremium")!=null && !ctx.get("deductibleEndorsementPremium").equals(HtmlConstants.EMPTY)? Double.parseDouble(ctx.get("deductibleEndorsementPremium").toString()):0;
		  		double deductibleEndorsementdTax=ctx.get("deductibleEndorsementdTax")!=null && !ctx.get("deductibleEndorsementdTax").equals(HtmlConstants.EMPTY)? Double.parseDouble(ctx.get("deductibleEndorsementdTax").toString()):0;
		  		double totalPremium=ctx.get("TotalPremium")!=null && !ctx.get("TotalPremium").equals(HtmlConstants.EMPTY)? Double.parseDouble(ctx.get("TotalPremium").toString()):0;
		  		double totalPolicyTaxAmount=ctx.get("TotalPolicyTaxAmount")!=null && !ctx.get("TotalPolicyTaxAmount").equals(HtmlConstants.EMPTY)? Double.parseDouble(ctx.get("TotalPolicyTaxAmount").toString()):0;
				double premiumDifference=deductibleEndorsementPremium-totalPremium;
				double taxDifference= deductibleEndorsementdTax-totalPolicyTaxAmount;
				//  long days=(DateUtils.calculateDiffInDays(new Date(ctx.get("limitEndorsementEffectiveDate").toString()),new Date(ctx.get("PolicyEffectiveDate").toString())));
				 
				  //long days=(DateUtils.calculateDiffInDays(new Date(ctx.get("limitEndorsementEffectiveDate").toString()),new Date(ctx.get("PolicyEffectiveDate").toString())));
//				String deductibleEndorsementEffectiveDate=new SimpleDateFormat("MM/dd/yyyy").format(new Date(ctx.get("deductibleEndorsementEffectiveDate").toString()));
//				String policyEffectiveDate=new SimpleDateFormat("MM/dd/yyyy").format(new Date(ctx.get("PolicyEffectiveDate").toString()));
//				long days=(DateUtils.calculateDiffInDays(new Date(deductibleEndorsementEffectiveDate),new Date(policyEffectiveDate)));
				ctx.put("Date1", new SimpleDateFormat("MM/dd/yyyy").format(new Date(ctx.get("PolicyEffectiveDate").toString())));
				ctx.put("Date2", new SimpleDateFormat("MM/dd/yyyy").format(new Date(ctx.get("deductibleEndorsementEffectiveDate").toString())));
				getDaysDifference(ctx);
				termDays=ctx.get("TermDays")!=null && !ctx.get("TermDays").equals(HtmlConstants.EMPTY)?Integer.parseInt(ctx.get("TermDays").toString()):0;
				enndorsementDays=ctx.get("EndorsementDays")!=null && !ctx.get("EndorsementDays").equals(HtmlConstants.EMPTY)?Integer.parseInt(ctx.get("EndorsementDays").toString()):0;
				double earnedPremium=(premiumDifference/termDays)*enndorsementDays;
				double earnedTax=(taxDifference/termDays)*enndorsementDays;
				double unearnedPremium=Math.round(premiumDifference-earnedPremium);
				double unearnedTax=Math.round(taxDifference-earnedTax);
					//double netPremium=premiumDifference+taxDifference;
				double premiumToShow=totalPremium+unearnedPremium+unearnedTax;
				double result=Math.round((totalPremium+totalPolicyTaxAmount)+(unearnedPremium+unearnedTax));
				ctx.put("returnPremium",unearnedPremium);
	  			ctx.put("returnTax",unearnedTax);
				ctx.put("deductibleEndorsementPremium",formatter.format(unearnedPremium+unearnedTax));
				ctx.put("deductibleEndorsementPremiumNew",formatter.format(unearnedPremium+unearnedTax));
				ctx.put("deductibleEndorsementPremiumNew1",formatter.format(unearnedPremium+unearnedTax));
				ctx.put("prorataPremium",formatter.format(result));
				ctx.put("premiumToShow",formatter.format(premiumToShow));
		  	}
			else if("12".equals(ctx.get("EndorsementType").toString()))
		  		{
		  	  double endorsementPremium=ctx.get("litmitsEndorsementPremium")!=null && !ctx.get("litmitsEndorsementPremium").equals(HtmlConstants.EMPTY)? Double.parseDouble(ctx.get("litmitsEndorsementPremium").toString()):0;
			  double litmitsEndorsementTax=ctx.get("litmitsEndorsementTax")!=null && !ctx.get("litmitsEndorsementTax").equals(HtmlConstants.EMPTY)? Double.parseDouble(ctx.get("litmitsEndorsementTax").toString()):0;
			  double totalPremium=ctx.get("TotalPremium")!=null && !ctx.get("TotalPremium").equals(HtmlConstants.EMPTY)? Double.parseDouble(ctx.get("TotalPremium").toString()):0;
			  double totalPolicyTaxAmount=ctx.get("TotalPolicyTaxAmount")!=null && !ctx.get("TotalPolicyTaxAmount").equals(HtmlConstants.EMPTY)? Double.parseDouble(ctx.get("TotalPolicyTaxAmount").toString()):0;
			  double premiumDifference=endorsementPremium-totalPremium;
			  double taxDifference= litmitsEndorsementTax-totalPolicyTaxAmount;
			//  long days=(DateUtils.calculateDiffInDays(new Date(ctx.get("limitEndorsementEffectiveDate").toString()),new Date(ctx.get("PolicyEffectiveDate").toString())));
			 
			  //long days=(DateUtils.calculateDiffInDays(new Date(ctx.get("limitEndorsementEffectiveDate").toString()),new Date(ctx.get("PolicyEffectiveDate").toString())));
//			  String limitEndorsementEffectiveDate=new SimpleDateFormat("MM/dd/yyyy").format(new Date(ctx.get("priorActEndorsementDate").toString()));
//			  String policyEffectiveDate=new SimpleDateFormat("MM/dd/yyyy").format(new Date(ctx.get("PolicyEffectiveDate").toString()));
//			  long days=(DateUtils.calculateDiffInDays(new Date(limitEndorsementEffectiveDate),new Date(policyEffectiveDate)));
			  
			/*  double unearnedPremium=(premiumDifference/AccountantConstants.DAYS_OF_YEAR)*days;
			double unearnedTax=(taxDifference/AccountantConstants.DAYS_OF_YEAR)*days;
			double earnedPremium=Math.round(premiumDifference-unearnedPremium);
			double earnedTax=Math.round(taxDifference-unearnedTax);
			double result=Math.round((totalPremium+totalPolicyTaxAmount)+(earnedPremium+earnedTax));
			//double netPremium=premiumDifference+taxDifference;
			double premiumToShow=totalPremium+earnedPremium+earnedTax;
			
 
			ctx.put("returnPremium",formatter.format(earnedPremium));
 			ctx.put("returnTax",formatter.format(earnedTax));
			ctx.put("totalReturnPremium",formatter.format(earnedPremium+earnedTax));
			ctx.put("returnTax",formatter.format(earnedTax));
			ctx.put("litmitsEndorsementPremiumNew1",formatter.format(earnedPremium+earnedTax));
  			ctx.put("prorataPremium",formatter.format(result));
  			ctx.put("premiumToShow",formatter.format(premiumToShow));*/
			  ctx.put("Date1", new SimpleDateFormat("MM/dd/yyyy").format(new Date(ctx.get("PolicyEffectiveDate").toString())));
				ctx.put("Date2", new SimpleDateFormat("MM/dd/yyyy").format(new Date(ctx.get("priorActEndorsementDate").toString())));
				getDaysDifference(ctx);
				termDays=ctx.get("TermDays")!=null && !ctx.get("TermDays").equals(HtmlConstants.EMPTY)?Integer.parseInt(ctx.get("TermDays").toString()):0;
				enndorsementDays=ctx.get("EndorsementDays")!=null && !ctx.get("EndorsementDays").equals(HtmlConstants.EMPTY)?Integer.parseInt(ctx.get("EndorsementDays").toString()):0;
				
			  double earnedPremium=(premiumDifference/termDays)*enndorsementDays;
				double earnedTax=(taxDifference/termDays)*enndorsementDays;
				double unearnedPremium=Math.round(premiumDifference-earnedPremium);
				double unearnedTax=Math.round(taxDifference-earnedTax);
				double result=Math.round((totalPremium+totalPolicyTaxAmount)+(unearnedPremium+unearnedTax));
				//double netPremium=premiumDifference+taxDifference;
				double premiumToShow=totalPremium+unearnedPremium+unearnedTax;
				
	 
				ctx.put("returnPremium",formatter.format(unearnedPremium));
	 			ctx.put("returnTax",formatter.format(unearnedTax));
				ctx.put("totalReturnPremium",formatter.format(unearnedPremium+earnedTax));
				ctx.put("returnTax",formatter.format(unearnedTax));
				ctx.put("litmitsEndorsementPremiumNew1",formatter.format(unearnedPremium+unearnedTax));
	  			ctx.put("prorataPremium",formatter.format(result));
	  			ctx.put("premiumToShow",formatter.format(premiumToShow));
			  	}
			else if("13".equals(ctx.get("EndorsementType").toString()))
	  		{
//				long days=(DateUtils.calculateDiffInDays(new Date(ctx.get("policyExtendExpirationDate").toString()),new Date(ctx.get("PolicyExpirationDate").toString())));
				 ctx.put("Date1", new SimpleDateFormat("MM/dd/yyyy").format(new Date(ctx.get("PolicyExpirationDate").toString())));
					ctx.put("Date2", new SimpleDateFormat("MM/dd/yyyy").format(new Date(ctx.get("policyExtendExpirationDate").toString())));
					getDaysDifference(ctx);
					termDays=ctx.get("TermDays")!=null && !ctx.get("TermDays").equals(HtmlConstants.EMPTY)?Integer.parseInt(ctx.get("TermDays").toString()):0;
					enndorsementDays=ctx.get("EndorsementDays")!=null && !ctx.get("EndorsementDays").equals(HtmlConstants.EMPTY)?Integer.parseInt(ctx.get("EndorsementDays").toString()):0;
					
				double premium=ctx.get("TotalPremium")!=null && !ctx.get("TotalPremium").equals(HtmlConstants.EMPTY)?Double.parseDouble(ctx.get("TotalPremium").toString()):0.0;
				double tax=ctx.get("TotalPolicyTaxAmount")!=null && !ctx.get("TotalPolicyTaxAmount").equals(HtmlConstants.EMPTY)?Double.parseDouble(ctx.get("TotalPolicyTaxAmount").toString()):0.0;
				double earnedPremium=(premium/termDays)*enndorsementDays;
				double earnedTax=tax-(tax/termDays)*enndorsementDays;
				earnedPremium=Math.round(earnedPremium);
				earnedTax=Math.round(earnedTax);
				//double totalOldPremium=Math.round(premium+tax);
				//double totalEarnedPremium=earnedPremium+earnedTax;
				double result=Math.round((premium+tax)+(earnedPremium+earnedTax));
				double premiumToShow=result;
				ctx.put("returnPremium",formatter.format(earnedPremium));
				ctx.put("totalReturnPremium",formatter.format(earnedPremium+earnedTax));
				ctx.put("returnTax",formatter.format(earnedTax));
				ctx.put("litmitsEndorsementPremiumNew1",formatter.format(earnedPremium+earnedTax));
		  		ctx.put("prorataPremium",formatter.format(result));
		  		ctx.put("premiumToShow",formatter.format(premiumToShow));
		  		ctx.put("TotalPremium", premium+earnedPremium);
		  		ctx.put("TotalPolicyTaxAmount", tax+earnedTax);
		  		ctx.put("totalUnearnedPremium", formatter.format(result));
			}
	  }
	  catch(Exception e)
	  {
        logger.error("Error in EndorsementUtilities.setEndorsementPremium", e);
	  }
	  
  }
public static void validateManualPremium(Context ctx)
{
	try
	{
		 List dataList = SqlResources.getSqlMapProcessor(ctx).select("SqlStmts.UserStatementManualBoQueriesgetFinilizedPremium", ctx);
		  if(dataList!=null && dataList.size()>0)
			{
				ctx.putAll((Map) dataList.get(0));
				
			}
		  String isManualPremiumSelected=ctx.get("IsManualPremium")!=null && !ctx.get("IsManualPremium").equals(HtmlConstants.EMPTY)?ctx.get("IsManualPremium").toString():"N";
		  if("Y".equals(isManualPremiumSelected))
			  AccountUtils.populateError(ctx, "endorsementError","Cannot endorse for limit/deductible for manual premium.");
	}
	catch(Exception e)
	{
		logger.error("errro in validating manual quote", e);
	}
}
public static  void calculateClaimAgeEndorsement(Context ctx) throws Exception {
	//DBUtils.executeDBOperation(ctx, "ClaimAgeLW", "2");
	com.userbo.AccountUtils utils=new com.userbo.AccountUtils();
	Object objPolicy = SqlResources.getSqlMapProcessor(ctx).findByKey("Policy.findByKey", ctx);
	Map poliMap = objPolicy != null && objPolicy instanceof Map ? (Map) objPolicy: null;
	
	// To check if IsFirmCarryingProfLiabilityIns equal Y/N
	String IsFirmCarryingProfLiabilityIns = "N";
	Object obj = SqlResources.getSqlMapProcessor(ctx).findByKey("SqlStmts.UserStatementManualBoQueriesgetIsFirmCarryingProfLiabilityIns",ctx);
	if(obj != null && obj instanceof Map){
		Map map = (Map) obj;
		IsFirmCarryingProfLiabilityIns = map.get("IsFirmHaveLawyersLiabilityInsurance") == null ? "N" : map.get("IsFirmHaveLawyersLiabilityInsurance").toString();
	}
	
	
	/*if("Y".equals(IsFirmCarryingProfLiabilityIns))
		firmStepRateYears = calculateFirmStepRateYears(ctx);*/
	int firmStepRateYears=0;
	Date priorActDate = ctx.get("priorActEndorsementPriorActDate") != null ? AccountantDateUtils.convertStringToDate(ctx.get("priorActEndorsementPriorActDate")
								.toString(), "yyyy-MM-dd") : null;
	
	ctx.put("PriorActDatePross", ctx.get("priorActEndorsementPriorActDate").toString());
				Date policyEffectiveDate = ctx.get("PolicyEffectiveDate") != null ? AccountantDateUtils.convertStringToDate(ctx.get("PolicyEffectiveDate").toString(), "yyyy-MM-dd") : null;

				if (priorActDate != null && policyEffectiveDate != null) {
					
					firmStepRateYears = utils.getStepRateYearBetweenTwoDate(priorActDate,policyEffectiveDate);
				}
	
	
//	Object objAttorney = (List) SqlResources.getSqlMapProcessor(ctx).select("SqlStmts.firmviewgetAttorneysList1",ctx);;
//	if (objAttorney != null && objAttorney instanceof List) {
//		List attorneyList = (List) objAttorney;
//		for (int i = 0; i < attorneyList.size(); i++) {
//			Map attornMap = (Map) attorneyList.get(i);
//			int attorneyStepRateYears = utils.calculateAttorneyStepRateYears(attornMap, poliMap);	
//			/*if("N".equals(IsFirmCarryingProfLiabilityIns) && firmStepRateYears == 0)
//				firmStepRateYears = attorneyStepRateYears;*/
//				
//			int claimAge = utils.getClaimAge(attorneyStepRateYears, firmStepRateYears);				
//			//saveClaimAgeDataFullQuote(ctx, claimAge);
//			int j=i+1;
//			ctx.put("ClaimAge_"+j,claimAge);
//		}
//		
//	}
}
public static void fetchERPLetterData(Context ctx)
{
	 DecimalFormat  formatter = new DecimalFormat("0,000");  
	logger.debug("fetching data for erp letter");
	try
	{
		Map mapGen = new HashMap();
		List listGen = new ArrayList();
		mapGen.put("CurrentDate", AccountantDateUtils.getCurrentDate());
		listGen.add(mapGen);
		ctx.put("general_freeform_01",listGen);
		
		String stateCode=ctx.get("StateCode").toString();
		Object obj = SqlResources.getSqlMapProcessor(ctx).findByKey("SqlStmts.UserStatementManualBoQueriesgetERPQuoteLetterData", ctx);
		if(obj != null && obj instanceof Map)
			ctx.putAll((Map)obj);
		new SetParametersForStoredProcedures().setParametersInContext(ctx, "PolicyKey");
		new SetParametersForStoredProcedures().setParametersInContext(ctx, "endorsementEffectiveDate");
		
		List data= (List) SqlResources.getSqlMapProcessor(ctx).select("PolicyTransaction.SetQuoteExpirationDate",ctx);
		ctx.putAll((Map) data.get(0));
		
		String isAccountantRetiring=ctx.get("isAccountantRetiring")!=null && !ctx.get("isAccountantRetiring").equals(HtmlConstants.EMPTY)?ctx.get("isAccountantRetiring").toString():"N";
		String isFirmERP=ctx.get("isFirmERP")!=null && !ctx.get("isFirmERP").equals(HtmlConstants.EMPTY)?ctx.get("isFirmERP").toString():"N";
		int practicePortion=ctx.get("practicePortion")!=null &&  !ctx.get("practicePortion").equals(HtmlConstants.EMPTY)?Integer.parseInt(ctx.get("practicePortion").toString()):0;
		
		int statusKey=ctx.get("StatusKey")!=null && !ctx.get("StatusKey").equals(HtmlConstants.EMPTY)?Integer.parseInt(ctx.get("StatusKey").toString()):0;
		if(statusKey==8)
		{
			Object obj1 = SqlResources.getSqlMapProcessor(ctx).findByKey("SqlStmts.UserStatementManualBoQueriesgetFinalizedInvoicedPremium", ctx);
			if(obj1!= null && obj1 instanceof Map)
			{
				ctx.putAll((Map)obj1);
			}
		}
		
		double invoicePremium=ctx.get("InvoicedPremium")!=null && !ctx.get("InvoicedPremium").equals(HtmlConstants.EMPTY)?Double.parseDouble(ctx.get("InvoicedPremium").toString()):0;
		
		invoicePremium=Math.round(invoicePremium);
		NumberFormat numberFormat =
			    NumberFormat.getCurrencyInstance(Locale.US);
		String endorsementPremiumpdf=numberFormat.format(invoicePremium);
		
		
		if("Y".equals(isAccountantRetiring))
		{
			ctx.put("InsuredName", ctx.get("nameOfAccountant"));
			double insuredPercentage=(practicePortion*invoicePremium)/100;
			double unlimitedYearQuote=0.0;
			/*if("AK".equals(stateCode))
				 unlimitedYearQuote=Math.round((insuredPercentage*200)/100);	
			else*/
			 unlimitedYearQuote=Math.round((insuredPercentage*125)/100);
			ctx.put("UnlimitedERPForAccountant",numberFormat.format(unlimitedYearQuote).split("\\.")[0]);
		}
			if("Y".equals(isFirmERP))
			{
				ctx.put("InsuredName", ctx.get("AccountName"));
				
				if(ctx.get("oneYear")!=null && "1".equals(ctx.get("oneYear").toString()))
					ctx.put("oneYearERP", endorsementPremiumpdf.split("\\.")[0]);
				if(ctx.get("threeYear")!=null && "3".equals(ctx.get("threeYear").toString()))
					ctx.put("threeYearsERP", numberFormat.format(Math.round((invoicePremium*185)/100)).split("\\.")[0]);
				if(ctx.get("fiveYear")!=null && "5".equals(ctx.get("fiveYear").toString()))
				{
					/*if("AK".equals(stateCode))
					ctx.put("fiveYearsERP", numberFormat.format(Math.round((invoicePremium*195)/100)).split("\\.")[0]);
					else*/
						ctx.put("fiveYearsERP", numberFormat.format(Math.round((invoicePremium*225)/100)).split("\\.")[0]);
				}
					if(ctx.get("sixYear")!=null && "6".equals(ctx.get("sixYear").toString()))
					ctx.put("sixYearsERP", numberFormat.format(Math.round((invoicePremium*250)/100)).split("\\.")[0]);
				/*if(ctx.get("unlimitedYears")!=null && "10".equals(ctx.get("unlimitedYears").toString()))
					ctx.put("unlimitedYearsERP", numberFormat.format(Math.round((invoicePremium*300)/100)).split("\\.")[0]);*/
			}
			
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
			@SuppressWarnings("deprecation")
			Date expirationDate = new Date(ctx.get("PolicyExpirationDate").toString());
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(expirationDate);
			calendar.add(Calendar.DAY_OF_YEAR, 60);
			Date erpExpirationdate = calendar.getTime();
			Date todaydt=new Date();
			String todaydate=sdf.format(todaydt);
			ctx.put("isAccountantRetiring", isAccountantRetiring);
			ctx.put("currentdate", todaydate);
	}
	catch(Exception e)
	{
		logger.error("exception occured while fetching data for quote letter : "+e.getMessage(), e);
	}
	logger.debug("fetching data for erp letter ended");
}
public static void calculateERPPremium(Context ctx) {
	logger.debug("going to calculate erp endorsement premium");
	DecimalFormat formatter = new DecimalFormat("0,000");
	long premium = 0,tax=0;
	try {
		
		String stateCode = ctx.get("StateCode").toString();
		Object obj = SqlResources.getSqlMapProcessor(ctx).findByKey("SqlStmts.UserStatementManualBoQueriesgetERPQuoteLetterData", ctx);
		if(obj != null && obj instanceof Map)
			ctx.putAll((Map)obj);
		
		int statusKey = ctx.get("StatusKey") != null && !ctx.get("StatusKey").equals(HtmlConstants.EMPTY) ? Integer.parseInt(ctx.get("StatusKey").toString()) : 0;
		if (statusKey == 8) {
			Object obj1 = SqlResources.getSqlMapProcessor(ctx).findByKey("SqlStmts.UserStatementManualBoQueriesgetFinalizedInvoicedPremium",ctx);
			if (obj1 != null && obj1 instanceof Map) {
				ctx.putAll((Map) obj1);
			}
		}
		double invoicePremium = ctx.get("TotalPremium") != null && !ctx.get("TotalPremium").equals(HtmlConstants.EMPTY) ? Double.parseDouble(ctx.get("TotalPremium").toString()) : 0;
		double invoicetax = ctx.get("TotalPolicyTaxAmount") != null && !ctx.get("TotalPolicyTaxAmount").equals(HtmlConstants.EMPTY) ? Double.parseDouble(ctx.get("TotalPolicyTaxAmount").toString()) : 0;
		
		if (ctx.get("isAccountantRetiring") != null && "Y".equals(ctx.get("isAccountantRetiring").toString())) {
			
			String isInsuredWithUsFromPast = ctx.get("isInsuredWithUsFromPast") != null && !ctx.get("isInsuredWithUsFromPast").equals(HtmlConstants.EMPTY) ? ctx.get("isInsuredWithUsFromPast").toString(): "NA";
			String isSoloAccountant = ctx.get("isSoloAccountant") != null && !ctx.get("isSoloAccountant").equals(HtmlConstants.EMPTY) ? ctx.get("isSoloAccountant").toString() : "NA";
			if( isSoloAccountant.equals("Y") && isInsuredWithUsFromPast.equals("N"))
			{
				int practicePortion = 1;
				double insuredPercentage = (practicePortion * invoicePremium);
				double taxWithInsuredPercentage = Math.round((practicePortion * invoicetax) / 100);
				premium = Math.round((insuredPercentage * 275) / 100);
				tax= Math.round((taxWithInsuredPercentage * 275) / 100);
			}
			if( isSoloAccountant.equals("N"))
			{
					int practicePortion = ctx.get("practicePortitonOfAccountant") != null && !ctx.get("practicePortitonOfAccountant").equals(HtmlConstants.EMPTY) ?
							Integer.parseInt(ctx.get("practicePortitonOfAccountant").toString()): 0;
					double insuredPercentage = (practicePortion * invoicePremium) / 100;
					double taxWithInsuredPercentage = Math.round((practicePortion * invoicetax) / 100);		
					
					if(practicePortion<100)
					{
						premium = Math.round((insuredPercentage * 275) / 100);
						tax= Math.round((taxWithInsuredPercentage * 275) / 100);
					}
					else
					{
						premium = Math.round((insuredPercentage * 125) / 100);
						tax= Math.round((taxWithInsuredPercentage * 125) / 100);
					}
				
				premium = Math.round((insuredPercentage * 275) / 100);
				tax= Math.round((taxWithInsuredPercentage * 275) / 100);
			}
			
		
		}
		String isFirmERP = ctx.get("isFirmERP") != null && !ctx.get("isFirmERP").equals(HtmlConstants.EMPTY) ? ctx.get("isFirmERP").toString(): "NA";
		if("Y".equals(isFirmERP))
			 {
			 int practicePortionPercentage=0;
				int yearsOfERP = ctx.get("yearsOfERP") != null 	&& !ctx.get("yearsOfERP").equals(HtmlConstants.EMPTY) ? Integer.parseInt(ctx.get("yearsOfERP").toString()) : 0;
				ctx.put("erpMonths",yearsOfERP*12);
				switch (yearsOfERP) {
				case 1:
					practicePortionPercentage=100;
					premium = Math.round(invoicePremium);
					tax = Math.round(invoicetax);
					ctx.put("practicePortionPercentage",practicePortionPercentage);
					
					break;
				case 3:
					practicePortionPercentage=185;
					premium = Math.round((invoicePremium * practicePortionPercentage) / 100);
					tax = Math.round((invoicetax * practicePortionPercentage) / 100);
					ctx.put("practicePortionPercentage",practicePortionPercentage);
					break;
				case 5:
					practicePortionPercentage=225;
					premium = Math.round((invoicePremium * practicePortionPercentage) / 100);
					tax = Math.round((invoicetax * practicePortionPercentage) / 100);
					ctx.put("ERPPremium",premium);
					
					break;
				case 6:
					practicePortionPercentage=250;
					premium = Math.round((invoicePremium * practicePortionPercentage) / 100);
					tax = Math.round((invoicetax * practicePortionPercentage) / 100);
					ctx.put("practicePortionPercentage",practicePortionPercentage);
					break;
			/*	case 10:
					premium = Math.round((invoicePremium * 300) / 100);
					tax = Math.round((invoicetax * 300) / 100);
					break;*/
		
				default:
					premium = 0;
					tax=0;
					break;
		
				}
		} 
	
	} catch (Exception e) {
		logger.error("Error occured while calculating ERP premium : " + e, e);
	}

	long totalPremium=premium+tax;
	NumberFormat numberFormat = NumberFormat.getCurrencyInstance(Locale.US);
	String formattedEndorsementPremium = numberFormat.format(premium);
	String formattedEndorsementTax = numberFormat.format(tax);
	String formattedTotalEndorsementPremium = numberFormat.format(totalPremium);
	
	ctx.put("endorsementPremiumExcludeTax", formattedEndorsementPremium.split("\\.")[0]);
	ctx.put("endorsementTax", formattedEndorsementTax.split("\\.")[0]);
	ctx.put("endorsementPremium", formattedTotalEndorsementPremium.split("\\.")[0]);

	ctx.put("ERPPremium",premium);
	ctx.put("ERPTax",tax);
	ctx.put("ERPTotalPremium",totalPremium);
	ctx.put("returnPremium",premium);
	ctx.put("returnTax",tax);
}

public static void getdateforERP(IContext ctx){
	try{
	SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
	Date todaydt=new Date();
	String todaydate=sdf.format(todaydt);
	ctx.put("CurrentDate", todaydate);
	}catch(Exception ex){
		logger.error("Error occured while calculating ERP date : "+ex, ex);
	}
	
	
}
@SuppressWarnings({ "unchecked", "rawtypes" })
public static void getDaysDifference(Context ctx)
{
	
	try
	{		
		new SetParametersForStoredProcedures().setParametersInContext(ctx, "Date1,Date2,PolicyEffectiveDate,PolicyExpirationDate");
		List dataList= (List) SqlResources.getSqlMapProcessor(ctx).select("Policy.GetDatesDifferenceInDaysACC_p",ctx);
		ctx.putAll((Map) dataList.get(0));	
				
	}
	catch(Exception e)
	{
			logger.error("exception occured while getting days difference" , e);
			
	}
	
}
}
