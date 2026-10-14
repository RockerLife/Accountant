package com.userbo;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.fop.servlet.ServletContextURIResolver;

import com.fop.DownloadFOP;
import com.mail.MailSender;
import com.manage.managemetadata.functions.commonfunctions.DBUtils;
import com.manage.managemetadata.functions.commonfunctions.RuleUtils;
import com.ormapping.ibatis.SqlResources;
import com.pdf.PrintPDF;
import com.util.Context;
import com.util.HtmlConstants;
import com.util.IContext;
import com.util.InetLogger;
import com.util.SystemProperties;

public class QuoteLetter {
	
	private static InetLogger logger = InetLogger.getInetLogger(QuoteLetter.class);
	public static boolean validateQuote(Context ctx) throws Exception
    {	
				
		
		if((ctx.get("page")!= null && "status_freeform".equals(ctx.get("page").toString())) && (ctx.get("StatusKey_Prev")!= null && ("3".equals(ctx.get("StatusKey_Prev").toString()) || "7".equals(ctx.get("StatusKey_Prev").toString()))))
		{
			if("3".equals(ctx.get("StatusKey_Prev").toString()))
			{
				RuleUtils.executeRule(ctx, "AccountantRule.fillAcceptanceDate");
				
				//Object obj = RuleUtils.executeRule(ctx, "AccountantRule.isQuoteExpired");
				
				Object obj = RuleUtils.executeRule(ctx, "AccountantRule.isQuotationExpiredAgent");
				
				boolean flag = false;			
				if(obj instanceof Boolean)
					flag = (Boolean) obj;
				
				if(flag)
				{
					//AccountUtils.populateError(ctx, "AcceptanceDate", "Quote expired and moved to under review bucket");
					SqlResources.getSqlMapProcessor(ctx).update("SqlStmts.UserStatementManualBoQueriesUpdateStatusUnderreview", ctx);
				}
				
				if("7".equals(ctx.get("StatusKey").toString()))
				{
					obj = RuleUtils.executeRule(ctx, "AccountantRule.showAllQuote");
					flag = false;
					if(obj != null && obj instanceof Boolean)
						flag = (Boolean) obj;
					
					List quoteList = null;
					if(flag)
						quoteList =SqlResources.getSqlMapProcessor(ctx).select("SqlStmts.UserStatementManualBoQueriesgetQuoteListAll", ctx);
					else
						quoteList =SqlResources.getSqlMapProcessor(ctx).select("SqlStmts.UserStatementManualBoQueriesgetQuoteListSelected", ctx);
					
					boolean finalisedFlag = false;
					int noOfFinalisedQuotes = 0;
					
					if(quoteList!=null)
					{        	
			    		for(int i=0;i<quoteList.size();i++)
			    		{    			
			    			 Map quote = (Map) quoteList.get(i);
			    			 if(quote.get("IsThisOptionFinalised") != null)
			    			 {
			    				 if("Y".equals(quote.get("IsThisOptionFinalised").toString()))
			    				 {
			    					 finalisedFlag = true;
			    					 ctx.put("FinalisedQuote_TransactionSequence", quote.get("TransactionSequence"));    						 
			    					 noOfFinalisedQuotes++;					 
			    				 }
			    			 }
			    		}
					}	
							
					if(!finalisedFlag)
						AccountUtils.populateError(ctx, "AcceptanceDate", "To continue, please finalise a quote option");	
					else if(noOfFinalisedQuotes > 1)
						AccountUtils.populateError(ctx, "AcceptanceDate", "Finalise one quote only");
					
					new AccountantValidationUtils(). validatePageOnAcceptIssue(ctx);
				}			
				
			}
			else if("7".equals(ctx.get("StatusKey_Prev").toString()))
			{
				AccountUtils.processStatus(ctx);				
			}		
		}
		else
		{
			
			if(ctx.get("IsClaimExpensesType") == null)
				ctx.put("IsClaimExpensesType", "N");
			else if(ctx.get("IsClaimExpensesType") != null && "".equals(ctx.get("IsClaimExpensesType").toString()))
				ctx.put("IsClaimExpensesType", "N");
			
			
			if(ctx.get("StateCode")!=null && ("AR".equals(ctx.get("StateCode").toString())) )
				// commenting below call for keeping IsClaimExpensesType == Y for AR state
				//ctx.put("IsClaimExpensesType", "N");
			
			if (ctx.get("StateCode") != null){				
				String stateCode = ctx.get("StateCode").toString();
				if("VT".equals(stateCode) || "NM".equals(stateCode)){
					ctx.put("IsClaimExpensesType", "Y");
				}
			}
			
			
			if(ctx.get("IsClaimOptionType") == null)
				ctx.put("IsClaimOptionType", "N");
			else if(ctx.get("IsClaimOptionType") != null && "".equals(ctx.get("IsClaimOptionType").toString()))
				ctx.put("IsClaimOptionType", "N");
			
			if(ctx.get("IsDollarDefense") == null)
				ctx.put("IsDollarDefense", "N");
			else if(ctx.get("IsDollarDefense") != null && "".equals(ctx.get("IsDollarDefense").toString()))
				ctx.put("IsDollarDefense", "N");
			
			AccountUtils.validateNorthDakotaStateRule(ctx,"LimitSequence");
			Object obj =SqlResources.getSqlMapProcessor(ctx).findByKey("SqlStmts.UserStatementpdfquotelettergetQuote", ctx);	
			
			if(obj != null)
				AccountUtils.populateError(ctx, "LimitSequence", "This quote already exist, Please select different combination");
			
			AccountUtils.validateSouthDakotaStateRule(ctx, "LimitSequence");
			
			
		}
		
		
				
		return true;
    }	
	
	public void generateQuoteLetter(IContext ctx) throws Exception
    {
		Context ctx1=(Context)ctx;
		Object obj = RuleUtils.executeRule(ctx, "AccountantRule.isQuoteSend");
		boolean flag = false;			
		if(obj instanceof Boolean)
			flag = (Boolean) obj;
		
		if(flag)
		{
			ctx.put("QuoteNotSend", "N");
			//AccountUtils.populateError(ctx, "QuoteNotSend", "Quote Could Not Send");	
			return;
		}
		
		
		RuleUtils.executeRule(ctx, "AccountantRule.fillQuoteDates");
	
		SqlResources.getSqlMapProcessor(ctx).update("SqlStmts.UserStatementpdfquoteletterupdateFirm", ctx);		
				
		boolean docUploaded = false;
		String quote=null;
		logger.debug("make a Quote");
		
		
		if(ctx.get("MakeQuote") == null)
		{
			quote="N";
			
		}else{
			quote = (String) ctx.get("MakeQuote");
		}
		
		
		
		boolean dollarDefenseFlag = checkDollarDefenseFlag(ctx);
		
		
		
	 if(!(dollarDefenseFlag || quote.equals("Y"))){
			
			OutputStream out = null;
			try{
				String homePath=SystemProperties.getInstance().getString("appl.home.dir");
				String quoteLetterXSLPath=homePath+"foxsl//quoteletter.xsl";
				String APL_PA_2009Path=homePath+"foxsl//group4//APL - PA - 2009 - 3.xsl";
				ctx.put("quoteLetterXSLPath",quoteLetterXSLPath);
				ctx.put("APL_PA_2009Path",APL_PA_2009Path);
				String protexureAccountantsLogoPath=homePath+"image//Protexure_Logo.png";
				ctx.put("protexureAccountantsLogoPath", protexureAccountantsLogoPath);
				String checkButtonPath=homePath+"image//check-btn4.png";
				ctx.put("checkButtonPath",checkButtonPath);
				String headerBottomPath=homePath+"image//header_bottom.png";
				ctx.put("headerBottomPath",headerBottomPath);
				String spaceBarPath=homePath+"image//spacer.png";
				ctx.put("spaceBarPath",spaceBarPath);
				
				List attachments  = new ArrayList();
				
				// PDF Writer
				String htmlDir = SystemProperties.getInstance().getString("html.basedir");
				String outFile = htmlDir + "data//QuoteLetter_" + ctx.get("QuoteNumber").toString() + ".pdf";			
				out = new java.io.FileOutputStream(outFile);
				out = new java.io.BufferedOutputStream(out);	
				
				String baseUrl = null;
				if(ctx.get("baseUrl") != null)
					baseUrl = ctx.get("baseUrl").toString();
				
				ServletContextURIResolver uriResolver = null;
				if(ctx.get("uriResolver") != null)
					uriResolver = (ServletContextURIResolver)ctx.get("uriResolver");
				
				baseUrl = "file:///" + ((ServletContext)ctx.get(HtmlConstants.DOCUMENTSERVLETCONTEXT)).getRealPath("/");
					new DownloadFOP().makePdf((Context)ctx, out, baseUrl, (ServletContextURIResolver)ctx.get(HtmlConstants.DOCUMENTURIRESOLVER));
				
				
						
				
				if(out != null)
					out.close();
				attachments.add(outFile);			
				
				outFile = htmlDir + "data//Accountant_" + ctx.get("QuoteNumber").toString() + ".pdf";
				out = new java.io.FileOutputStream(outFile);
				out = new java.io.BufferedOutputStream(out);		
				
				if(ctx.get("PolicyStatusKey") != null && "1".equals(ctx.get("PolicyStatusKey").toString())){
					Object objnew = RuleUtils.executeRule(ctx, "AccountantRule.newAppFlow");
					boolean flagnew = false;			
					if(objnew instanceof Boolean)
						flagnew = (Boolean) objnew;
					
					if(flagnew)
					{
						baseUrl = "file:///" + ((ServletContext)ctx.get(HtmlConstants.DOCUMENTSERVLETCONTEXT)).getRealPath("/");
						new DownloadFOP().makePdfForMail((Context)ctx, out, baseUrl, (ServletContextURIResolver)ctx.get(HtmlConstants.DOCUMENTURIRESOLVER));
					}
					else{
					ByteArrayOutputStream baos = new PrintPDF().makePdf((Context)ctx);
					baos.writeTo(out);
					}
					
				}else{
					baseUrl = "file:///" + ((ServletContext)ctx.get(HtmlConstants.DOCUMENTSERVLETCONTEXT)).getRealPath("/");
					new DownloadFOP().makeRenewPdfForMail((Context)ctx, out, baseUrl, (ServletContextURIResolver)ctx.get(HtmlConstants.DOCUMENTURIRESOLVER));				
				}
				
				if(out != null)
					out.close();			
				attachments.add(outFile);	
				
				// This Part is for ND state, that attached MO_42_001_06_15.xsl with quote letter
				// for any selected quote with defense inside the limit.
				if(ctx.get("StateCode") != null && "ND".equals(ctx.get("StateCode").toString())){
					logger.debug("This is ND state | Check if any  quote is defense inside.");
					ctx.put("RuleRequestType","NDRuleForFormAttachmentQuoteletter");
					ctx.put("PolicyKey_rule",ctx.get("PolicyKey").toString());
					ctx.put("StateCode_rule",ctx.get("StateCode"));
					Object ruleMapObject = SqlResources.getSqlMapProcessor(ctx).findByKey("Account.StateSpecificRulesAccountant_proc", ctx); 
					if(ruleMapObject != null && ruleMapObject instanceof Map){						
						Map ruleMap = (Map)ruleMapObject;
						String IsDefensePaidWithinRuleND = ruleMap.get("IsDefensePaidWithinRuleND") != null ? ruleMap.get("IsDefensePaidWithinRuleND").toString() : "";
						{ if (logger.isDebugEnabled()) logger.debug("Is IsDefensePaidWithinRuleND ? " + IsDefensePaidWithinRuleND); }
						if("Y".equals(IsDefensePaidWithinRuleND)){
							outFile = htmlDir + "data//MO_42_001_06_15.pdf";
							out = new java.io.FileOutputStream(outFile);
							out = new java.io.BufferedOutputStream(out);
							logger.debug("Going to attach ND form with quote letter");
							baseUrl = "file:///" + ((ServletContext)ctx.get(HtmlConstants.DOCUMENTSERVLETCONTEXT)).getRealPath("/");
							new DownloadFOP().makeNDPdf((Context)ctx, out, baseUrl, (ServletContextURIResolver)ctx.get(HtmlConstants.DOCUMENTURIRESOLVER));
							
							if (out != null)
								out.close();

							attachments.add(outFile);
						}
					}
				}
				
				// End
				
				
				try{
					{ if (logger.isDebugEnabled()) logger.debug("Going to upload Quote letter for QuoteNumber " + ctx.get("QuoteNumber").toString() ); }
					new DocumentManagment().uploadQuoteLetter(ctx);
					docUploaded = true;
					{ if (logger.isDebugEnabled()) logger.debug("docUploaded value " + docUploaded); }
				}
				catch(Exception e){
					logger.error("Error in uploading quote letter ", e);
					ctx.put("QuoteLetterSent", "N");
				}
			
				
				
				if(docUploaded){
					String dstId = "";
					logger.debug("Check if producer code exist before sending the letter");
					if(AccountantValidationUtils.checkIfSubProducerExist(ctx)){
						dstId = AccountUtils.getAdminEmailID(ctx);
					}else{
						dstId = AccountUtils.getEmailID (ctx);
					}
					{ if (logger.isDebugEnabled()) logger.debug("Going to send mail to destination " + dstId); }
							//dstId = AccountUtils.getEmailID (ctx);
							if("".equals(dstId))
								return;					
							
							String isMailing = SystemProperties.getInstance().getString("Insured.sendmail");
							String bccAddress="";
							bccAddress=SystemProperties.getInstance().getString("appl." + ctx.getProject() + ".admin.cc.email");
							
							// Check Mailing is allowed If Y then Send
							if(isMailing != null && "Y".equals(isMailing) && !"".equals(dstId))
							{				
								MailSender mailSender = new MailSender();				
								mailSender.setSubject("Quotation for Accountants Professional Liability Insurance");
								mailSender.setToAdd(dstId);
								mailSender.setCcAdd(AccountUtils.getFirstReviewerAgentEmailID(ctx));
								mailSender.setBccAdd(bccAddress);
								mailSender.setIsSentToCC("Y");
								mailSender.setContentType("text/html");
								
//								String imgPath = htmlDir + "image//kyle.png";
//								mailSender.setImgPath(imgPath);
								
								mailSender.setBody(generateQuoteLetterBody(ctx));	
								mailSender.setAttachments(attachments);	
								
								mailSender.sendMail();
								
								for (int i=0; i<attachments.size(); i++)
								{
									// File Delete
									File file = new File(attachments.get(i).toString());
									if(file.exists())
										file.delete();
								}	
								
								ctx.put("QuoteLetterSent", "Y");
								ctx.put("QuoteLetterAuto", "Y");
								logger.debug("Quote letter has send");
			//					SqlResources.getSqlMapProcessor(ctx).update("SqlStmts.UserStatementpdfquoteletterupdateFirm", ctx);
								
								//SqlResources.getSqlMapProcessor(ctx).update("SqlStmts.UserStatementsaveAccountstmtsupdateQuoteDateTime", ctx);
							}		
					
	//				}else{
	//					logger.debug("Quote letter has send manual quote has been selected or Make a quote has been press");
	//					ctx.put("QuoteLetterSent", "Y");
	//					ctx.put("QuoteLetterAuto", "N");
	//					
	//				}
						
				   }		
					
				}
				catch(Exception e){
					logger.error("Quote letter  could not be send", e);
					ctx.put("QuoteLetterSent", "N");
					ctx.remove("RuleRequestType");
				}
			
		}else{
		logger.debug("Quote letter has send manual quote has been selected or Make a quote has been press");
		ctx.put("QuoteLetterSent", "Y");
		//if(dollarDefenseFlag){
			ctx.put("QuoteLetterAuto", "N");
		//}
		
	}	 
	 ctx.remove("RuleRequestType");
		
    }
	
	public boolean checkDollarDefenseFlag(IContext ctx) throws Exception {
		
		boolean dollarDefenseFlag = false;		
		List quoteList = SqlResources.getSqlMapProcessor(ctx).select(
                "SqlStmts.UserStatementsaveAccountstmtsgetAllQuotes", ctx);       

       boolean flag = false;
       if (quoteList != null && quoteList.size() > 0) {

           for (int i = 0; i < quoteList.size(); i++) {

                Map quoteMap = (Map) quoteList.get(i);

                if (quoteMap != null) {

                     String isManualPremium = quoteMap.get("IsDollarDefense") != null ? quoteMap
                               .get("IsDollarDefense").toString()
                               : "";
                     String isQuoteSelected = ctx.get("IsQuoteSelected_"+i) != null ? ctx.get("IsQuoteSelected_"+i).toString() : "";

                     if (isManualPremium.equals("Y") && (isQuoteSelected.equals("Y") || "on".equals(isQuoteSelected))) {
                    	 dollarDefenseFlag = true;
                    	 break;
                     }
                }
           }
       }
       
       if(dollarDefenseFlag)
    	   ctx.put("OneQuoteIsManual", 'Y');
       
       return dollarDefenseFlag;
       
       
       
//       if(dollarDefenceSize<=0)
//   		{
//   			dollarDefenseFlag = false;
//   		}else{
//   			
//   			ctx.put("OneQuoteIsManual", 'Y');
//   			dollarDefenseFlag = true;
//   		}
   		
//   		return dollarDefenseFlag;
       

		
//		String dollar=null;
//		Map mapDollarDefense = null;
//		int dollarDefenceSize=0;
//		Object objDollarDefense = SqlResources.getSqlMapProcessor(ctx).findByKey("SqlStmts.UserStatementsaveAccountstmtsgetIsDollarDefense", ctx);
//		
//	
//		if(objDollarDefense != null && objDollarDefense instanceof Map)
//			mapDollarDefense = (Map) objDollarDefense;
//		
//					
//		
//		if( mapDollarDefense.get("IsDollarDefense").toString() == null)
//		{
//			dollarDefenseFlag = false;
//		}
//		else
//		{
//			dollar = mapDollarDefense.get("IsDollarDefense").toString();
//		}
//		
//		
//		dollarDefenceSize = Integer.parseInt(dollar);
//
//		
//		if(dollarDefenceSize<=0)
//		{
//			dollarDefenseFlag = false;
//		}else{
//			
//			ctx.put("OneQuoteIsManual", 'Y');
//			dollarDefenseFlag = true;
//		}
		
//		return dollarDefenseFlag;
		
		
		
	}

	private String generateQuoteLetterBody(IContext ctx) throws Exception
    {
		Object objPoilcy = SqlResources.getSqlMapProcessor(ctx).findByKey("SqlStmts.UserStatementpdfquotelettergetPriorPolicyInfo", ctx);
		Map policyMap = null;
		if(objPoilcy != null && objPoilcy instanceof Map)
			policyMap = (Map)objPoilcy;
		
		String resourcePath = SystemProperties.getInstance().getString("appl." + ctx.getProject() +".url");
		AccountantValidationUtils utils = new AccountantValidationUtils();
		String encrytedPolicyKey=null;
		
		 encrytedPolicyKey = utils.encrypt(ctx.get("PolicyKey").toString());
		 encrytedPolicyKey = encrytedPolicyKey.replace("=", "%3D");
		 encrytedPolicyKey = encrytedPolicyKey.replace("+", "%2B");		
		
		String msg = "";
		
		if(policyMap != null)
		{
			if(!"3".equals(ctx.get("CompanyKey").toString()))
			{
			msg = msg + "<table>";
			
			msg = msg + "<tr>";
			msg = msg + "<td>";
			if(policyMap.get("AccountName") != null && !"".equals(policyMap.get("AccountName").toString()))
				msg = msg + policyMap.get("AccountName").toString() + "<br/>";	
			if(policyMap.get("Address1") != null && !"".equals(policyMap.get("Address1").toString()))
				msg = msg + policyMap.get("Address1").toString() + "<br/>";;
			if(policyMap.get("Address2") != null && !"".equals(policyMap.get("Address2").toString()))
				msg = msg + policyMap.get("Address2").toString() + "<br/>";;		
			
			if(policyMap.get("City") != null && !"".equals(policyMap.get("City").toString()))
				msg = msg + policyMap.get("City").toString();
			if(policyMap.get("City") != null && !"".equals(policyMap.get("StateDesc").toString()))
				msg = msg + ", " + policyMap.get("StateDesc").toString();
			if(policyMap.get("City") != null && !"".equals(policyMap.get("Zip").toString()))
				msg = msg + "  " + policyMap.get("Zip").toString();
			msg = msg + "</td>";
			msg = msg + "</tr>";
			
			msg = msg + "<tr>";
			msg = msg + "<td>";		
			msg = msg + "<br/>Dear " ;
			if(policyMap.get("ContactPerson") != null && !"".equals(policyMap.get("ContactPerson").toString()))
				msg = msg + policyMap.get("ContactPerson").toString();		
			msg = msg + ":";
			msg = msg + "</td>";
			msg = msg + "</tr>";
			
			msg = msg + "<tr>";
			msg = msg + "<td>";		
			msg = msg + "<br/>Thank you for completing your Protexure Accountants application. Please use the link "
					+ "below<br/> to view, accept and pay for your professional liability coverage online. <br/><br/>"
			  	  + " Your quotation expires ";
				if(policyMap.get("QuoteExpiryDate") != null && !"".equals(policyMap.get("QuoteExpiryDate").toString()))
				msg = msg + policyMap.get("QuoteExpiryDate").toString() + ".<br/><br/> <br/>";
			
			msg = msg + "<a href=" + resourcePath + "/signandpay.jsp?PolicyKey="+encrytedPolicyKey + ">Review your quotation </a><br/><br/> <br/>"
				
			  			  
		  +	"The above link is specifically for your account, you will be able to securely review <br/> and accept your coverage online. After you select your quote and e-sign your application,<br/>you "
		  + "will have the opportunity to continue with full payment or financing options.<br/><br/> If you would prefer to accept coverage by mail, you can print the attached documents, sign <br/>and send them with payment to PO Box 773197, Detroit, Michigan 48277-3197."
		  + "<br/><br/> To help you better evaluate your options we have included some additional information on<br/>"
		  + "our <a href='https://www.protexureaccountants.com/protexure-policy-features/'> policy features</a> and  <a href='https://www.protexureaccountants.com/claims/'>claims handling</a> ,<br/><br/> "
			  			  
			  
			//  + "The second option is to follow the instructions for binding coverage in the attached quote letter.<br/><br/> "
			  
			  + "If you have any additional questions, please contact us at our toll free number (888)803-9898<br/>and one of our licensed "
			  + "professional liability specialists will be happy to assist you.<br/><br/>";
			  
			//  + "Thank you again for considering Protexure Accountants for your Professional Liability needs.<br/><br/>";
			  
			msg = msg + "</td>";
			msg = msg + "</tr>";
			
			
			msg = msg + "<tr>";
			msg = msg + "<td>";		
			msg = msg + "Regards,<br/><br/>";
			msg = msg + "The Protexure Accountants Team<br/>";
			msg = msg + "Phone 1-888-803-9898<br/>";
			msg = msg + "Fax (440) 333-3214<br/>";
			msg = msg + "</td>";
			msg = msg + "</tr>";		
			
			msg = msg + "<tr><td><br/><br/></td></tr>";
			
			msg = msg + "<tr style=\"font-weight:bold;color:#787878;font-size:14px;font-style:italic;\"><td>P.S. Protexure also offers a Property and General Liability program tailored to small firms.  Privacy/Cyber Protection, Employee Dishonesty, Workers Comp and a Business Owners Policy for a home based business are available as well.  Please email or call to inquire about any of our offerings.</td></tr>";
			
			// 
			
			msg = msg + "</table>";

				 
//			msg = msg + "</form></body></html>";
		}
		else
		{
			msg = msg +"<table border='0' style='font-size: 16px;'> "
					+ "<tr> "
					+ "<td colspan='2' align='left' valign='top'>"+policyMap.get("CurrentDate")+"</td> "
					+ "</tr> "
					+ "<tr> "
					+ "<td colspan='2' align='right' valign='top'>Via Electronic Mail</td> "
					+ "</tr> "
					+ "<tr> "
					+ "<td colspan='2'>" +((policyMap.get("AccountName") == null) ? "" : policyMap.get("AccountName")) + "</td> "
					+ "</tr> "
					+ "<tr> "
					+ "<td colspan='2'>" + ((policyMap.get("Address1") == null) ? "" : policyMap.get("Address1")) + "</td> "
					+ "</tr> "
					+ "<tr> "
					+ "<td colspan='2'>" + ((policyMap.get("Address2") == null) ? "" : policyMap.get("Address2")) + "</td> "
					+ "</tr> "
					+ "<tr> "
					+ "<td colspan='2'>" + policyMap.get("City") + ", " + policyMap.get("StateDesc") + ", " + policyMap.get("Zip") + "</td> "
					+ "</tr> "
					+ "<tr> "
					+ "<td colspan='2'>&nbsp;</td> "
					+ "</tr> "
					+ "<tr> "
					+ "<td colspan='2'>&nbsp;</td>"
					+ "</tr> "
					+ "<tr> "
					+ "<td colspan='2'>Dear " + ((policyMap.get("ContactPerson") == null) ? "" : policyMap.get("ContactPerson")) + "</td> "
					+ "</tr> "
					+ "<tr> "
					+ "<td colspan='2'>&nbsp;</td> "
					+ "</tr>\r\n";
			msg=msg+"<tr> <td colspan='2' align='justify'>Thank you for allowing Protexure Insurance Agency, Inc. the opportunity to provide a quote for your firm's consideration of accountants Professional Liability options. </td> </tr>";
			
			Context newCtx=new Context();
			newCtx.setProject(ctx.getProject());
			newCtx.putAll(ctx);
			
			Map map = (Map) SqlResources.getSqlMapProcessor(newCtx).findByKey("Firm.findByKey", newCtx);
			if (map != null && map.size() > 0) {
				newCtx.put("QuoteEffectiveDate", map.get("QuoteEffectiveDate"));
				newCtx.put("QuoteExpiryDate", map.get("QuoteExpiryDate"));
				newCtx.put("QuoteSentDate", map.get("QuoteSentDate"));
			}
			
			Object objQuoteExp = RuleUtils.executeRule(newCtx, "AccountantRule.isPEDLessFrmQED");
			boolean flag = false;
			if (objQuoteExp != null && objQuoteExp instanceof Boolean)
				flag = (Boolean) objQuoteExp;

			boolean flagQuoteDate = false;
			if(flag) {
				//Less QuoteEffectiveDate
				Object objQuoteExpDate = RuleUtils.executeRule(newCtx, "AccountantRule.checkQuoteLatterEmailPolicyEffectiveDate");
				if (objQuoteExpDate != null && objQuoteExpDate instanceof Boolean)
					flagQuoteDate = (Boolean) objQuoteExpDate;
				if(flagQuoteDate)
					flagQuoteDate = false;
			} else {
				//Greater QuoteEffectiveDate
				Object objPolciyEffDate = RuleUtils.executeRule(newCtx, "AccountantRule.isPolicyEffDate5Days");
				if (objPolciyEffDate != null && objPolciyEffDate instanceof Boolean)
					flagQuoteDate = (Boolean) objPolciyEffDate;
				
				if(flagQuoteDate)
					flagQuoteDate = isQuoteEffDateGreater5DaysLess29DaysFrmPED(newCtx, newCtx.get("PolicyEffectiveDate").toString(), newCtx.get("QuoteSentDate").toString());
				
			}

			if (!flagQuoteDate){
				msg=msg+"<tr> <td colspan='2'>&nbsp;</td> </tr> <tr> <td colspan='2' align='justify'>Attached, you will find a comprehensive proposal with terms and conditions offered for your firm's upcoming annual Professional Liability policy.</td> </tr>";
				msg=msg+"<tr> <td colspan='2'>&nbsp;</td> </tr><tr> <td colspan='2' align='justify'>To finalize your coverage electronically please follow the link below:  </td> </tr>";
				msg=msg+"<tr> <td colspan='2' align='justify'><a href="+ resourcePath+ "/signandpay.jsp?PolicyKey="+ encrytedPolicyKey+ ">Finalize Your Coverage</a> </td> </tr>";
			} else {
				msg=msg+"<tr> <td colspan='2'>&nbsp;</td> </tr> <tr> <td colspan='2' align='justify'>Please sign and return both the quote letter and application documents attached.</td> </tr>";
			}
			
			msg=msg+"<tr> <td colspan='2'>&nbsp;</td> </tr><tr> <td colspan='2' align='justify'>This link is specifically for your account, you will be able to securely review and sign your application. Once coverage has been accepted, you will have the opportunity to continue with your payment option. </td> </tr>";
			msg=msg	+ "<tr> <td colspan='2'>&nbsp;</td> </tr><tr> <td colspan='2' align='justify'> Protexure Insurance Agency, Inc. is a full-service insurance agency specializing in the insurance needs of solo practitioners and small accounting firms. Our agency is built on establishing long-term relationships with our clients and providing comprehensive professional liability insurance solutions in a timely fashion. We look forward to continuing our business relationship. </td> </tr>";
			msg=msg+"<tr> <td colspan='2'>&nbsp;</td> </tr> <tr> <td colspan='2' align='justify'>To bind coverage, please follow the instructions on the quote acceptance page.</td> </tr> <tr> <td colspan='2'>&nbsp;</td> </tr> <tr> <td colspan='2' align='justify'>If you have any additional questions, please contact us at our toll-free number 888-803-9898 and one of our licensed Professional Liability Specialists will be happy to assist you.</td> </tr> <tr> <td colspan='2'>&nbsp;</td> </tr> <tr> <td colspan='2' align='left'>Regards,</td> </tr> <tr> <td colspan='2' align='left'>The Protexure Accountants Team</td> </tr> <tr> <td align='left'>PO Box 773197</td> <td align='right'>(888) 803-9898<b> / T</b></td> </tr> <tr> <td align='left'>Detroit, Michigan 48277-3197</td> <td align='right'>(440) 333-3214<b> / F</b></td> </tr> <tr> <td align='left'></td> <td align='right'>ACCTS.MAIL@PROTEXURE.COM<b> / E</b></td> </tr> <tr> <td colspan='2' align='right'>PROTEXUREACCOUNTANTS.COM<b> / W</b></td> </tr> </table>";
		
		}
		}
		
		return msg;
    }
    
	
	public static void main(String args[])
	{
		try{
			List attachments  = new ArrayList();
			
			MailSender mailSender = new MailSender();				
			mailSender.setSubject("Quotation for Accountants Professional Liability Insurance");
			mailSender.setToAdd("amit.jain.bhopal@gmail.com");
			mailSender.setIsSentToCC("Y");
			mailSender.setContentType("text/html");
			
			String imgPath = "C://Tomcat//webapps//AccountantIns//image//Signature_Only.jpg";
			mailSender.setImgPath(imgPath);
			attachments.add("C://result.pdf");
			mailSender.setAttachments(attachments);
			
			
//			String body ="Hello Mail";
			
			String body ="Hello Mail<br/>";
			body = body + "<table><tr><td>"            
			+ "<img src=\"cid:memememe\">"
			+ "</td><tr></table>";
			
			
			mailSender.setBody(body);		
			
//			String outFile = "C://Tomcat//webapps//AccountantIns//image//signature.jpg";
//			attachments.add(outFile);
//			mailSender.setImages(attachments);
			
			mailSender.sendMail();
		
		}catch(Exception e){
			logger.error("Error in QuoteLetter.main", e);
		}
	}
	
	
	
	public static boolean finalizeQuoteSelected(IContext ctx) throws Exception {

        List quoteList = SqlResources.getSqlMapProcessor(ctx).select(
                 "SqlStmts.UserStatementManualBoQueriesgetQuoteListAll", ctx);
        if (quoteList == null)
            return true;

        for (int i = 0; i < quoteList.size(); i++) {
            Map map = (Map) quoteList.get(i);

            if ((map.get("IsThisOptionFinalised") != null && "Y".equals(map
                      .get("IsThisOptionFinalised").toString()))) {
            	if(ctx.get("IsQuoteSelected"+"_"+i) != null && "".equals(ctx.get("IsQuoteSelected"+"_"+i).toString()))
                      return false;

                 if (ctx.get("IsQuoteSelected_" + i) == null)
                      return false;

            }
        }

        return true;
   }



	
	public static boolean isViewSendQuoteLetter(IContext ctx) throws Exception
    {		
		int countSelectedQuote = 0;
		List quoteList =SqlResources.getSqlMapProcessor(ctx).select("SqlStmts.UserStatementManualBoQueriesgetQuoteListAll", ctx);
		if(quoteList == null)
			return true;	
		
		for(int i=0; i<quoteList.size(); i++)
		{
			Map map = (Map) quoteList.get(i);	
			
			if((map.get("IsQuoteSelected") != null && "Y".equals(map.get("IsQuoteSelected").toString())))
				countSelectedQuote++;				
			
//			if((map.get("IsThisOptionFinalised") != null && "Y".equals(map.get("IsThisOptionFinalised").toString())) && (map.get("IsDollarDefense") != null && "Y".equals(map.get("IsDollarDefense").toString())))
//				return false;						
		}
		
		if(countSelectedQuote == 0)
			return false;
		else
			return true;	
		
    }
	
	
	
	
	
	
	public static void processViewSendQuoteLetter(IContext ctx) throws Exception
    {	
	
		
		if(!isViewSendQuoteLetter(ctx))
		{
			ctx.put("isQuoteLetterSentView", "N");
			ctx.remove("IsQuoteSelected");
			AccountUtils.populateError(ctx, "isQuoteLetterSentView", "Select a quote option below.");				
		}
		else
			ctx.put("isQuoteLetterSentView", "Y");

		
		        
    }
	
	public static void saveQuoteSentToPDF(IContext ctx) throws Exception
    {

		
		boolean flag = true;
        if (!finalizeQuoteSelected(ctx)) {
        	AccountUtils.populateError(ctx, "isQuoteLetterSentView",
                      "Finalized quote should be selected.");
            flag = false;

        }
        
        boolean isDollarDefenceSelected = true;
        
	
        
			isDollarDefenceSelected = QuoteLetter.checkDollarDefenseWithQuoteSelected(ctx);
			
	        if (isDollarDefenceSelected) {
	        	AccountUtils.populateError(ctx, "isQuoteLetterSentView","Quote letters cannot be sent automatically for Manual quotes, please create a Manual quote letter.");
	        	flag = false;
	        }	
		
        

		if(flag)
		{
//			List quoteList =SqlResources.getSqlMapProcessor(ctx).select("SqlStmts.UserStatementsaveAccountstmtsgetAllQuotes", ctx);
//			
//			if(quoteList!=null)
//			{        	
//	    		for(int i=0;i<quoteList.size();i++)
//	    		{    			
//	    			 Map map = (Map) quoteList.get(i);	        			 
//	    			 Context newctx = new Context();
//	    			 newctx.putAll(map);
//	    			 newctx.setProject(ctx.getProject());
//	    			 newctx.put("PolicyKey", map.get("PolicyKey"));
//	    			 newctx.put("TransactionSequence", map.get("TransactionSequence"));
//	    			 
//	
//					 if(ctx.get("IsQuoteSelected"+"_"+i) != null && ("on".equals(ctx.get("IsQuoteSelected"+"_"+i).toString())||"Y".equals(ctx.get("IsQuoteSelected"+"_"+i).toString()) ))
//					 {
//						 newctx.put("IsQuoteSelected","Y");
//					 }
//					 else if(ctx.get("IsQuoteSelected"+"_"+i) != null && "N".equals(ctx.get("IsQuoteSelected"+"_"+i).toString()))
//					 {
//						 newctx.put("IsQuoteSelected","Y");
//					 }
//	    			 else
//	    			 {
//	    				 newctx.put("IsQuoteSelected",null);
//	    				 newctx.put("IsThisOptionFinalised",null);
//	    			 }    			 
//	    				 
//	    			 Object obj2 = DBUtils.executeDBOperation(newctx, "Quote", "3");           	 	 
//	    			 
//	    			 if(obj2==null)
//	    			 {
//	    				 DBUtils.executeDBOperation(newctx, "Quote", "1");
//	    			 }
//	    			 else
//	    			 {
//					     DBUtils.executeDBOperation(newctx, "Quote", "2");
//	        	 	     DBUtils.executeDBOperation(newctx, "Quote", "1");
//	    			 }
//	    		}
//	        }	
			
			viewQuoteSentToPDF(ctx);
    }
			
	}
	
	public static void saveCommentToPDF(IContext ctx) throws Exception
    {	
		SqlResources.getSqlMapProcessor(ctx).update("SqlStmts.UserStatementsaveAccountstmtsupdateQuotecomment", ctx);	
		
		
	}
	
	public static void populateQuote(IContext ctx) throws Exception
    {					
		if(ctx.get("IsClaimExpensesType") == null )
			ctx.put("IsClaimExpensesType","N");
		if(ctx.get("IsClaimOptionType") == null )
			ctx.put("IsClaimOptionType","N");
		
		boolean multipleFlag = false;
		
		if("Y".equals(ctx.get("IsClaimExpensesType").toString()) && "Y".equals(ctx.get("IsClaimOptionType").toString()))
			multipleFlag = true;
		else if("N".equals(ctx.get("IsClaimExpensesType").toString()) && "N".equals(ctx.get("IsClaimOptionType").toString()))
			multipleFlag = true;
		
		DBUtils.executeDBOperation(ctx, "PolicyTransaction", "1");
		DBUtils.executeDBOperation(ctx, "PolicyCoverage", "1");
		
		DBUtils.executeDBOperation(ctx, "Quote", "1");
		DBUtils.executeDBOperation(ctx, "RatingTrace", "1");
		
		ctx.put("Org_CoverageSequence", ctx.get("CoverageSequence"));
		ctx.put("Org_TransactionSequence", ctx.get("TransactionSequence"));
		
//		if(multipleFlag)
//		{
//			ctx.put("IsFirstQuote","N");
//			
//			if("Y".equals(ctx.get("IsClaimExpensesType").toString()))
//				ctx.put("IsClaimExpensesType","N");
//			else if("N".equals(ctx.get("IsClaimExpensesType").toString()))
//				ctx.put("IsClaimExpensesType","Y");
//			
//			if("Y".equals(ctx.get("IsClaimOptionType").toString()))
//				ctx.put("IsClaimOptionType","N");
//			else if("N".equals(ctx.get("IsClaimOptionType").toString()))
//				ctx.put("IsClaimOptionType","N");
//				
//			DBUtils.executeDBOperation(ctx, "PolicyTransaction", "1");
//			DBUtils.executeDBOperation(ctx, "PolicyCoverage", "1");
//			
//			DBUtils.executeDBOperation(ctx, "Quote", "1");
//			DBUtils.executeDBOperation(ctx, "RatingTrace", "1");
//			
//			ctx.put("CoverageSequence", ctx.get("Org_CoverageSequence"));
//			ctx.put("TransactionSequence", ctx.get("Org_TransactionSequence"));
//		}		
			
    }	
	
	public static void populateQuoteIndication(IContext ctx) throws Exception
    {					
		if(ctx.get("IsClaimExpensesType") == null )
			ctx.put("IsClaimExpensesType","N");
		if(ctx.get("IsClaimOptionType") == null )
			ctx.put("IsClaimOptionType","N");
		
		boolean multipleFlag = false;
		
		if("Y".equals(ctx.get("IsClaimExpensesType").toString()) && "Y".equals(ctx.get("IsClaimOptionType").toString()))
			multipleFlag = true;
		else if("N".equals(ctx.get("IsClaimExpensesType").toString()) && "N".equals(ctx.get("IsClaimOptionType").toString()))
			multipleFlag = true;
		
		ctx.put("Org_CoverageSequence", ctx.get("CoverageSequence"));
		ctx.put("Org_TransactionSequence", ctx.get("TransactionSequence"));
    }
	
	public static void deleteSecondQuickQuote(IContext ctx) throws Exception
    {
		List list = SqlResources.getSqlMapProcessor(ctx).select("SqlStmts.UserStatementdeletegetPolicyQuote", ctx);
		
		if(list != null && list.size() > 1)
		{
			for (int i = 1; i < list.size(); i++) {

				Map map = (Map) list.get(i);

				Context newCtx = new Context();
				newCtx.setProject(ctx.getProject());
				newCtx.put("PolicyKey", map.get("PolicyKey"));
				newCtx.put("RatingTraceSequence", map.get("RatingTraceSequence"));
				newCtx.put("TransactionSequence", map.get("TransactionSequence"));
				newCtx.put("CoverageSequence", map.get("CoverageSequence"));

				DBUtils.executeDBOperation(newCtx, "RatingTrace", "2");
				DBUtils.executeDBOperation(newCtx, "Quote", "2");
				SqlResources.getSqlMapProcessor(newCtx).delete("SqlStmts.UserStatementdeleteDeleteSurchargeTax", newCtx);
				DBUtils.executeDBOperation(newCtx, "PolicyCoverage", "2");
				DBUtils.executeDBOperation(newCtx, "PolicyTransaction", "2");

			}			
			
		}
//		if(list != null && list.size() > 0)
//			generateQuickQuoteListLetter(ctx);
    }
	
	public static void processFinalisedQuote(IContext ctx) throws Exception
    {	
//		saveQuoteSentToPDF(ctx);
		
		Map map = (Map) SqlResources.getSqlMapProcessor(ctx).findByKey("Firm.findByKey", ctx);
		if (map != null && map.size() > 0) {
			ctx.put("QuoteEffectiveDate", map.get("QuoteEffectiveDate"));
			ctx.put("QuoteExpiryDate", map.get("QuoteExpiryDate"));
			ctx.put("QuoteSentDate", map.get("QuoteSentDate"));
		}
		
		Object obj = RuleUtils.executeRule(ctx, "AccountantRule.showAllQuote");
		boolean flag = false;
		if(obj != null && obj instanceof Boolean)
			flag = (Boolean) obj;
		
		List quoteList = null;
		if(flag)
			quoteList =SqlResources.getSqlMapProcessor(ctx).select("SqlStmts.UserStatementManualBoQueriesgetQuoteListAll", ctx);
		else
			quoteList =SqlResources.getSqlMapProcessor(ctx).select("SqlStmts.UserStatementManualBoQueriesgetQuoteListSelected", ctx);
		
		boolean finalisedFlag = false;
		int noOfFinalisedQuotes = 0;
		
		if(quoteList!=null)
		{        	
    		for(int i=0;i<quoteList.size();i++)
    		{    			
    			 Map quote = (Map) quoteList.get(i);
    			 if(quote.get("IsThisOptionFinalised") != null && quote.get("IsQuoteSelected") != null)
    			 {
    				 if("Y".equals(quote.get("IsThisOptionFinalised").toString()) && "Y".equals(quote.get("IsQuoteSelected").toString()))
    				 {
    					 finalisedFlag = true;
    					 ctx.put("FinalisedQuote_TransactionSequence", quote.get("TransactionSequence"));    						 
    					 noOfFinalisedQuotes++;					 
    				 }
    			 }
    		}
		}	
				
		if(!finalisedFlag)
			AccountUtils.populateError(ctx, "FinalisedQuote", "To continue, please select a quote option below");	
		else if(noOfFinalisedQuotes > 1)
			AccountUtils.populateError(ctx, "FinalisedQuote", "Finalise one quote only");
		
		if(ctx.get("User") != null && !"Agent".equals(ctx.get("User").toString()) && 
			ctx.get("StatusKey") != null && !"3".equals(ctx.get("StatusKey").toString()) && !"7".equals(ctx.get("StatusKey").toString()))
			AccountUtils.populateError(ctx, "FinalisedQuote", "Change status of policy to quoted");
		
		Object objQuoteExp = RuleUtils.executeRule(ctx, "AccountantRule.isPEDLessFrmQEDAndInsured");
		if (objQuoteExp != null && objQuoteExp instanceof Boolean)
			flag = (Boolean) objQuoteExp;

		boolean flagQuoteDate = false;
		if(flag) {
			//Less QuoteEffectiveDate
			Object objQuoteExpDate = RuleUtils.executeRule(ctx, "AccountantRule.isQuotationExpiredAndInsured");
			if (objQuoteExpDate != null && objQuoteExpDate instanceof Boolean)
				flagQuoteDate = (Boolean) objQuoteExpDate;
			
			if(flagQuoteDate)
				objQuoteExpDate = RuleUtils.executeRule(ctx, "AccountantRule.isQuotationExpired5DaysAndInsured");
				if (objQuoteExpDate != null && objQuoteExpDate instanceof Boolean)
					flagQuoteDate = (Boolean) objQuoteExpDate;
		
		} else {
			//Greater QuoteEffectiveDate
			Object objPolciyEffDate = RuleUtils.executeRule(ctx, "AccountantRule.isPolicyEffDate5DaysAndInsured");
			if (objPolciyEffDate != null && objPolciyEffDate instanceof Boolean)
				flagQuoteDate = (Boolean) objPolciyEffDate;
			
			if(flagQuoteDate)
				flagQuoteDate = isQuoteEffDateGreater5DaysLess29DaysFrmPED(ctx, ctx.get("PolicyEffectiveDate").toString(), ctx.get("QuoteSentDate").toString());
		}

		if (flagQuoteDate)
			SqlResources.getSqlMapProcessor(ctx).update("SqlStmts.UserStatementManualBoQueriesUpdateStatusUnderreview", ctx);
		
		
		objQuoteExp = RuleUtils.executeRule(ctx, "AccountantRule.isQuotationExpiredAndAgent");
		flag = false;
		if(objQuoteExp != null && objQuoteExp instanceof Boolean)
			flag = (Boolean) objQuoteExp;
		
		if(flag)
		{
			AccountUtils.populateError(ctx, "isQuoteExpired", "Quote has been expired");
			ctx.remove("IsQuoteSelected");
		}
		
		objQuoteExp = RuleUtils.executeRule(ctx, "AccountantRule.isAgentAndUnderreview");
		flag = false;
		if(objQuoteExp != null && objQuoteExp instanceof Boolean)
			flag = (Boolean) objQuoteExp;
		objQuoteExp = RuleUtils.executeRule(ctx,"AccountantRule.isAgentAndNotQuoted");
		flag = false;
		if (objQuoteExp != null && objQuoteExp instanceof Boolean)
			flag = (Boolean) objQuoteExp;
		if(flag)
			AccountUtils.populateError(ctx, "isQuoteExpired", "Policy must be quoted");
		
	 }
public static boolean isQuoteEffDateGreater5DaysLess29DaysFrmPED(IContext ctx, String policyEffectiveDateG, String quoteSentDateG){
		
		try {

			Calendar currentDate = Calendar.getInstance();
			
		    Date quoteSentDate = new SimpleDateFormat("MM/dd/yyyy").parse(quoteSentDateG);
		    Date policyEffDate = new SimpleDateFormat("MM/dd/yyyy").parse(policyEffectiveDateG);
		    
		    Calendar quoteSentCal = Calendar.getInstance();
		    quoteSentCal.setTime(quoteSentDate);
		    
		    Calendar quoteSentCal1Days = Calendar.getInstance();
		    quoteSentCal1Days.setTime(quoteSentDate);
		    quoteSentCal1Days.add(Calendar.DATE, 1);
		    
		    Calendar policyEffCal5Days = Calendar.getInstance();
		    policyEffCal5Days.setTime(policyEffDate);
		    policyEffCal5Days.add(Calendar.DATE, 5);
		    
		    Calendar policyEffCal29Days = Calendar.getInstance();
		    policyEffCal29Days.setTime(policyEffDate);
		    policyEffCal29Days.add(Calendar.DATE, 30);
		    
		    if(quoteSentCal.after(policyEffCal5Days) && quoteSentCal.before(policyEffCal29Days)) {
		    	
		    	if(currentDate.after(quoteSentCal) && currentDate.before(quoteSentCal1Days)){

		    		return false;
		    	} else {
		    		return true;
		    	}
		    } else {
	    		return true;
	    	}

		} catch (Exception e) {
			logger.error("Error in QuoteLetter.isQuoteEffDateGreater5DaysLess29DaysFrmPED", e);
		}
		return false;

	}
	public static void finaliseQuote(IContext ctx) throws Exception
    {
		List quoteList = SqlResources.getSqlMapProcessor(ctx).select("SqlStmts.UserStatementsaveAccountstmtsgetAllQuotes", ctx);
		
		if(quoteList!=null)
		{        	
    		for(int i=0;i<quoteList.size();i++)
    		{    			
    			 Map map = (Map) quoteList.get(i);	        			 
    			 Context newctx = new Context();
    			 newctx.putAll(map);
    			 newctx.setProject(ctx.getProject());
    			 newctx.put("PolicyKey", map.get("PolicyKey"));
    			 newctx.put("TransactionSequence", map.get("TransactionSequence"));
    			 
    			 if(ctx.get("IsThisOptionFinalised"+"_"+i) != null && "Y".equals(ctx.get("IsThisOptionFinalised"+"_"+i).toString()))
    				 newctx.put("IsThisOptionFinalised","Y");
    			 else
    				 newctx.put("IsThisOptionFinalised",null);
    				 
    			 SqlResources.getSqlMapProcessor(ctx).update("SqlStmts.UserStatementprocessquotefinaliseQuote", newctx);
    				
    		}
        }	
			
	}
	private String getProjectUrl(IContext ctx) throws Exception
    {
		String resourcePath = null;
		resourcePath = SystemProperties.getInstance().getString("appl." + ctx.getProject() +".url");
		
		return resourcePath;
    
    }
	
	public static void getFinalisedQuote(IContext ctx) throws Exception
    {	
		Object obj = RuleUtils.executeRule(ctx, "AccountantRule.showAllQuote");
		boolean flag = false;
		if(obj != null && obj instanceof Boolean)
			flag = (Boolean) obj;
		
		List quoteList = null;
		if(flag)
			quoteList =SqlResources.getSqlMapProcessor(ctx).select("SqlStmts.UserStatementManualBoQueriesgetQuoteListAll", ctx);
		else
			quoteList =SqlResources.getSqlMapProcessor(ctx).select("SqlStmts.UserStatementManualBoQueriesgetQuoteListSelected", ctx);
		
		boolean finalisedFlag = false;
		int noOfFinalisedQuotes = 0;
		
		if(quoteList!=null)
		{        	
    		for(int i=0;i<quoteList.size();i++)
    		{    			
    			 Map quote = (Map) quoteList.get(i);
    			 if(quote.get("IsThisOptionFinalised") != null && quote.get("IsQuoteSelected") != null)
    			 {
    				 if("Y".equals(quote.get("IsThisOptionFinalised").toString()) && "Y".equals(quote.get("IsQuoteSelected").toString()))
    				 {
    					 finalisedFlag = true;
    					 ctx.put("FinalisedQuote_TransactionSequence", quote.get("TransactionSequence"));    						 
    					 noOfFinalisedQuotes++;					 
    				 }
    			 }
    		}
		}	
		
    }
    
	
	
	
	
	public static boolean checkDollarDefenseWithQuoteSelected(IContext ctx) throws Exception {
		

        List quoteList = SqlResources.getSqlMapProcessor(ctx).select(
                 "SqlStmts.UserStatementsaveAccountstmtsgetAllQuotes", ctx);

        

        boolean flag = false;
        if (quoteList != null && quoteList.size() > 0) {

            for (int i = 0; i < quoteList.size(); i++) {

                 Map quoteMap = (Map) quoteList.get(i);

                 if (quoteMap != null) {

                      String isManualPremium = quoteMap.get("IsDollarDefense") != null ? quoteMap
                                .get("IsDollarDefense").toString()
                                : "";
                                String isQuoteSelected = null;
                            if(ctx.get("StatusKey")!=null)
                            {
                                if("2".equals(ctx.get("StatusKey").toString()) || "3".equals(ctx.get("StatusKey").toString()))
                                	isQuoteSelected = ctx.get("IsQuoteSelected_"+i) != null ? ctx.get("IsQuoteSelected_"+i).toString() : "";
                                else
                                	isQuoteSelected = quoteMap.get("IsQuoteSelected") != null ? quoteMap.get("IsQuoteSelected").toString() : "";
                            }    		
                      

                      if (isManualPremium.equals("Y") && (isQuoteSelected.equals("Y") || "on".equals(isQuoteSelected) || isQuoteSelected.equals("Y"))) {
                           flag = true;
                           break;
                      }
                 }
            }

            if (flag) {
                 return true ;
            }

        }
        return flag ;

		
//		String dollar=null;
//		Map mapDollarDefenseSelected = null;
//		int dollarDefenceSize=0;
//		Object objDollarDefenseSelected = SqlResources.getSqlMapProcessor(ctx).findByKey("SqlStmts.UserStatementsaveAccountstmtschechDollarDefenseWithSelectedQuote", ctx);
//		
//	
//		if(objDollarDefenseSelected != null && objDollarDefenseSelected instanceof Map)
//			mapDollarDefenseSelected = (Map) objDollarDefenseSelected;
//		
//		if(mapDollarDefenseSelected != null){
//			if(Integer.parseInt(mapDollarDefenseSelected.get("IsDollarDefense").toString()) > 0)
//				 dollarDefenseFlag = true;
//		}	
		
//		boolean dollarDefenseFlag = false;
//		List quoteList =SqlResources.getSqlMapProcessor(ctx).select("SqlStmts.UserStatementsaveAccountstmtsgetAllQuotes", ctx);
//		
//		if(quoteList!=null)
//		{        	
//    		for(int i=0;i<quoteList.size();i++)
//    		{    			
//    			 Map map = (Map) quoteList.get(i);	        			 
//    			 String IsDollarDefense = null;
//    			 
//    			 if(map.get("IsDollarDefense") != null)
//    				 IsDollarDefense = map.get("IsDollarDefense").toString();
//    			 
//    			 if("Y".equals(IsDollarDefense))
//    			 {
//    				 if(ctx.get("IsQuoteSelected"+"_"+i) != null && ("on".equals(ctx.get("IsQuoteSelected"+"_"+i).toString())||"Y".equals(ctx.get("IsQuoteSelected"+"_"+i).toString()) ))
//    				 {
//    					 return true;
//    				 }
////    				 else if(ctx.get("IsQuoteSelected"+"_"+i) != null && "N".equals(ctx.get("IsQuoteSelected"+"_"+i).toString()))
////    				 {
////    					 dollarDefenseFlag = true;
////    				 }
//    			 }
//    			 
//    		}	 
//		}		
//		
//		return dollarDefenseFlag;
		
		
	}
	
	
	
	public static void viewQuoteSentToPDF(IContext ctx) throws Exception
    {
		
		
			List quoteList =SqlResources.getSqlMapProcessor(ctx).select("SqlStmts.UserStatementsaveAccountstmtsgetAllQuotes", ctx);
			
			if(quoteList!=null)
			{        	
	    		for(int i=0;i<quoteList.size();i++)
	    		{    			
	    			 Map map = (Map) quoteList.get(i);	        			 
	    			 Context newctx = new Context();
	    			 newctx.putAll(map);
	    			 newctx.setProject(ctx.getProject());
	    			 newctx.put("PolicyKey", map.get("PolicyKey"));
	    			 newctx.put("TransactionSequence", map.get("TransactionSequence"));
	    			 
	
					 if(ctx.get("IsQuoteSelected"+"_"+i) != null && ("on".equals(ctx.get("IsQuoteSelected"+"_"+i).toString())||"Y".equals(ctx.get("IsQuoteSelected"+"_"+i).toString()) ))
					 {
						 newctx.put("IsQuoteSelected","Y");
					 }
					 else if(ctx.get("IsQuoteSelected"+"_"+i) != null && "N".equals(ctx.get("IsQuoteSelected"+"_"+i).toString()))
					 {
						 newctx.put("IsQuoteSelected","Y");
					 }
	    			 else
	    			 {
	    				// newctx.put("IsQuoteSelected",null);
	    				 newctx.put("IsThisOptionFinalised",null);
	    			 } 
					 
					 newctx.put("isShowQuote",map.get("isShowQuote"));
	    			 Object obj2 = DBUtils.executeDBOperation(newctx, "Quote", "3");           	 	 
	    			 
	    			 if(obj2==null)
	    			 {
	    				 DBUtils.executeDBOperation(newctx, "Quote", "1");
	    			 }
	    			 else
	    			 {
					     DBUtils.executeDBOperation(newctx, "Quote", "2");
	        	 	     DBUtils.executeDBOperation(newctx, "Quote", "1");
	    			 }
	    		}
			}
			
	}
	
	
	
	
	public static boolean checkDollarDefenseWithQuoteSelectedSend(IContext ctx) throws Exception {boolean dollarDefenseFlag = false;
    
    String dollar=null;
    Map mapDollarDefenseSelected = null;
    int dollarDefenceSize=0;
    Object objDollarDefenseSelected = SqlResources.getSqlMapProcessor(ctx).findByKey("SqlStmts.UserStatementsaveAccountstmtschechDollarDefenseWithSelectedQuote", ctx);
    

    if(objDollarDefenseSelected instanceof Map)
          mapDollarDefenseSelected = (Map) objDollarDefenseSelected;

    if(mapDollarDefenseSelected == null || mapDollarDefenseSelected.get("IsDollarDefense") == null)
          return false;

    dollar = mapDollarDefenseSelected.get("IsDollarDefense").toString();
    dollarDefenceSize = Integer.parseInt(dollar);

    
    if(dollarDefenceSize<=0)
    {
          dollarDefenseFlag = false;
    }else{
          
          //ctx.put("OneQuoteIsManual", 'Y');
          dollarDefenseFlag = true;
    }
    
    return dollarDefenseFlag;
}
	
	
	public static void generateQuickQuoteListLetter(IContext ctx) throws Exception
    {	
		ctx.put("UploadedTime", ctx.get("LastUpdateTimeStamp"));
		List list = SqlResources.getSqlMapProcessor(ctx).select("SqlStmts.UserStatementdeletegetPolicyQuote", ctx);
		
		if(list != null && list.size() == 1)
		{
			OutputStream out = null;			
			try
			{
				
				List attachments  = new ArrayList();
				
				// PDF Writer
				String htmlDir = SystemProperties.getInstance().getString("html.basedir");
				String outFileQuickQuote = htmlDir + "data//QuickQuoteOptions_" + ctx.get("QuoteNumber").toString() + ".pdf";			
				out = new java.io.FileOutputStream(outFileQuickQuote);
				out = new java.io.BufferedOutputStream(out);	
				
				String baseUrl = null;
				if(ctx.get("baseUrl") != null)
					baseUrl = ctx.get("baseUrl").toString();
				
				ServletContextURIResolver uriResolver = null;
				if(ctx.get("uriResolver") != null)
					uriResolver = (ServletContextURIResolver)ctx.get("uriResolver");
				baseUrl = "file:///" + ((ServletContext)ctx.get(HtmlConstants.DOCUMENTSERVLETCONTEXT)).getRealPath("/");
				new DownloadFOP().makeQuickQuoteListPdf((Context)ctx, out, baseUrl, (ServletContextURIResolver)ctx.get(HtmlConstants.DOCUMENTURIRESOLVER));		
				
//				String result = new DocumentManagment().uploadDocument(ctx, outFileQuickQuote);
//				System.out.println(" value of result---"+result);
//				String DocFileName = outFileQuickQuote.substring(outFileQuickQuote.lastIndexOf("//")+2, outFileQuickQuote.length());
//				if(result != null)
//				{
//					
//
//					int index = DocFileName.indexOf(".");
//			        String fileName1 = DocFileName.substring(0, index);
//			        ctx.put("DocTitle", fileName1);
//
//			        
//			        String fileExt = DocFileName.substring(index+1, DocFileName.length());
//			        DocFileName = fileName1 + "_" + ctx.get("UploadedTime").toString() + "."+ fileExt;
//			        DocFileName = DocFileName.replace(" ", "").replace(":", "").replace("-", "");
//			        
//			        ctx.put("Comment", "");			
//					ctx.put("DocFileName", DocFileName);
//					
//					 ctx.put("UploadedType", "Auto");
//					 
//					ctx.put("DocType", "QQL");
//					
//				  new DocumentManagment().setUrl(ctx, DocFileName, "DocUrl");
//				  new DocumentManagment().insertInDocumentArchive(ctx);
//					
//				}
				
				
				
				if(out != null)
					out.close();
				
				attachments.add(outFileQuickQuote);		
				
				boolean docUploaded = false;
				try{
					String skipUpload = SystemProperties.getInstance().getString("appl." + ctx.getProject() + ".skipupload");
					if(!"Y".equals(skipUpload)){
						new DocumentManagment().uploadQuickQuoteLetter(ctx);
						docUploaded = true;
					}
				}
				catch(Exception e){
					logger.error("Error in uploading quick quote letter  ", e);
				}
				String bccAddress="";
				bccAddress=SystemProperties.getInstance().getString("appl." + ctx.getProject() + ".admin.cc.email");
				
				MailSender mailSender = new MailSender();		
				mailSender.setBccAdd(bccAddress);	
				mailSender.setSubject("Quick Quotation Options");
				mailSender.setIsSentToCC("Y");
				mailSender.setContentType("text/html");
				
				mailSender.setBody(new AccountUtils().generateLinkBody(ctx));	
				mailSender.setAttachments(attachments);	
				
				mailSender.sendMail();
				
				for (int i=0; i<attachments.size(); i++)
				{
					// File Delete
					File file = new File(attachments.get(i).toString());
					if(file.exists())
						file.delete();
				}				
					
			}
			catch(Exception e)
			{
				logger.error("Quick quote option could not be uploaded", e);
			}	
		}
		
    }
	
	public static void signAndPay(IContext ctx)throws Exception{

		try{
			SimpleDateFormat myFormat = new SimpleDateFormat("MM/dd/yyyy");
			Date date = new Date();
			String inputString1 = myFormat.format(date);
			String inputString2 = String.valueOf(ctx.get("PolicyEffectiveDate"));

			if(inputString2.contains("-")){
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); 
				date = (Date)formatter.parse(inputString2);
				inputString2 = myFormat.format(date);
			}
			
			try {
			    Date date1 = myFormat.parse(inputString1);
			    Date date2 = myFormat.parse(inputString2);
			    long diff = date2.getTime() - date1.getTime();
			    long days = (diff / (1000*60*60*24));
			    { if (logger.isDebugEnabled()) logger.debug("Days: " + days); }
			   
			    if(days>90){
			    	AccountUtils.populateError(ctx, "PolicyEffectiveDateError", "Policy Effective date is Greater than 90 days");
			    }
			    
			} catch (Exception e) {
				logger.error("Error in QuoteLetter.signAndPay", e);
			}
		}catch(Exception e){
			logger.error("Error in QuoteLetter.signAndPay", e);
		}
	}

	
	
	
	
}
