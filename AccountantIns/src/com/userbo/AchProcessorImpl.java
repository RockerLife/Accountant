package com.userbo;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.ws.rs.core.MediaType;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mail.MailSender;
import com.ormapping.ibatis.SqlResources;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.util.Context;
import com.util.InetLogger;
import com.util.ResourceLoader;
import com.util.SystemProperties;

import sun.misc.BASE64Encoder;

public class AchProcessorImpl {
	private static InetLogger logger = InetLogger.getInetLogger(AchProcessorImpl.class);	

	public static void main(String[] a) {
		loadAccountantInsInsProjectFiles(a);
	}
	public static void loadAccountantInsInsProjectFiles(String[] a){
		{ if (logger.isDebugEnabled()) logger.debug("ACH Batch Started....." + new Timestamp(new Date().getTime())); }
		Context ctx = new Context();
		
		a = new String[4];
		a[0] = "AccountantIns";
		a[1] = "C:/Batches/AccountantACHUtility/Accountant.properties";
		a[2] = "C:/Batches/AccountantACHUtility/XML";
		a[3] = "-20";

		String project = a[0];
		String propertyPathMain = a[1];
		String xmlPath = a[2];
		
		{ if (logger.isDebugEnabled()) logger.debug("project " + project + " " + new Timestamp(new Date().getTime())); }
		{ if (logger.isDebugEnabled()) logger.debug("propertyPathMain " + propertyPathMain + " " + new Timestamp(new Date().getTime())); }
		{ if (logger.isDebugEnabled()) logger.debug("xmlPath " + xmlPath + " " + new Timestamp(new Date().getTime())); }
		
		ctx.setProject(project);
		ctx.put("LastUpdateTimeStamp", new Timestamp(new Date().getTime()));
		try {
			SystemProperties.setPropertyFileName(propertyPathMain);
			SqlResources.load(project, xmlPath + "/AccountantIns/ibatis/maps/SqlMapConfig.xml");
			ResourceLoader.load(xmlPath + "/AccountantIns/rules/rules.xml", "rules", project);
			ResourceLoader.load(xmlPath + "/AccountantIns/functions.xml", "functions", project);
			ResourceLoader.load(xmlPath + "/AccountantIns/metadata/metadata.xml", "metadata", project);
		} catch (Exception e1) {
			logger.error("Error in loading XML Files " + e1.getMessage() + " " + new Timestamp(new Date().getTime()), e1);
		}
		{ if (logger.isDebugEnabled()) logger.debug("XML Files Loaded...." + new Timestamp(new Date().getTime())); }
		setCutoffDate(ctx);
		checkACHStatus(ctx, Integer.parseInt(a[3]));
		{ if (logger.isDebugEnabled()) logger.debug("ACH Batch Ended......" + new Timestamp(new Date().getTime())); }
	}
	
	public static void setCutoffDate(Context ctx){
		ctx.put("CutOffDate", AccountantConstants.CUT_OFF_DATE);
		ctx.put("CutOffDateGroup2", AccountantConstants.CUT_OFF_DATE_GROUP2);
		ctx.put("NewAppFlowCutOffDate", AccountantConstants.NEWAPPFLOW_CUT_OFF_DATE);
		ctx.put("CutOffDateGroup3", AccountantConstants.CUT_OFF_DATE_GROUP3);
		ctx.put("CutOffDateGroup4", AccountantConstants.CUT_OFF_DATE_GROUP4);
		ctx.put("CutOffDateGroup5", AccountantConstants.CUT_OFF_DATE_GROUP5);
		ctx.put("CutOffDateGroup6", AccountantConstants.CUT_OFF_DATE_GROUP6);
		ctx.put("CutOffDateGroup7", AccountantConstants.CUT_OFF_DATE_GROUP7);
		ctx.put("CutOffDateGroup8", AccountantConstants.CUT_OFF_DATE_GROUP8);
		ctx.put("CutOffDateCCBSupp", AccountantConstants.CUT_OFF_DATE_CCBSupp);
		ctx.put("CutOffDate2020", AccountantConstants.CUT_OFF_DATE_2020);
	}
	
	public static boolean checkACHStatus(Context ctx, int backDate){
		try{
			ArrayList approvedStatusList = new ArrayList();
			ArrayList processingStatusList = new ArrayList();
			ArrayList manualApprovedStatusList = new ArrayList();
			ArrayList declinedStatusList = new ArrayList();
			
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, backDate);
			String backTransactionDate = new SimpleDateFormat("MM/dd/yyyy").format(cal.getTime());			
			ctx.put("BackTransactionDate", backTransactionDate);
			
			List getPaymentDetailsGreaterThanThreeList = (List)SqlResources.getSqlMapProcessor(ctx).select("SqlStmts.UserStatementManualBoQueriesgetACHPaymentDetailGreaterThanThree", ctx);
    		
			if(getPaymentDetailsGreaterThanThreeList != null && getPaymentDetailsGreaterThanThreeList.size() > 0 
					&& !getPaymentDetailsGreaterThanThreeList.isEmpty()){
				{ if (logger.isDebugEnabled()) logger.debug("Policy Count...." + getPaymentDetailsGreaterThanThreeList.size()); }
    			
    			System.setProperty("javax.net.ssl.trustStore", SystemProperties.getInstance().getString("appl.AccountantIns.webservice.securitycacerts.keyStore"));
    			System.setProperty("javax.net.ssl.keyStorePassword", SystemProperties.getInstance().getString("appl.AccountantIns.webservice.securitycacerts.keyStorePassword"));
    			System.setProperty("https.protocols", "TLSv1.2,TLSv1.1,SSLv3");
    			
    			final String webServiceURI = SystemProperties.getInstance().getString("appl.AccountantIns.ach.checkstatus.webserviceurl");
    			final String username = SystemProperties.getInstance().getString("appl.AccountantIns.ach.username");
    			final String password = SystemProperties.getInstance().getString("appl.AccountantIns.ach.password");
    			final String merchid = SystemProperties.getInstance().getString("appl.AccountantIns.ach.checkstatus.merchid");   			
    			
//				String webServiceURI = "https://fts.cardconnect.com:6443/cardconnect/rest/funding";
//				String username = "testing";
//				String password = "testing123";
//				String merchid = "542041";
    			
    			//Creating map having multiple payment done on particular transaction date
    			Map achDetailObj = new HashMap<String, ArrayList>();
    			
    			for(int i = 0; i < getPaymentDetailsGreaterThanThreeList.size(); i++){
		        	Map getPaymentDetailsObj = (Map)getPaymentDetailsGreaterThanThreeList.get(i);		        	
		        	String getPaymentTransactionDate = (getPaymentDetailsObj.get("TransactionDate") == null ? "" : getPaymentDetailsObj.get("TransactionDate").toString());
					{ if (logger.isDebugEnabled()) logger.debug("TransactionDate....." + getPaymentTransactionDate); }
		        	
		        	Date date = new SimpleDateFormat("MM/dd/yyyy").parse(getPaymentTransactionDate); // your date
	        		// Choose time zone in which you want to interpret your Date
		        	
		        	Date date2 = new Date();
				    long diff = date2.getTime() - date.getTime();
				    long days = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
		        	
		        	for(int j=0; j<days; j++){
		        		Calendar cal1 = Calendar.getInstance();
						{ if (logger.isDebugEnabled()) logger.debug("Cal Date....." + cal1.getTime()); }
		        		cal1.setTime(date);
		        		cal1.add(Calendar.DATE, j+1);
		        		
		                int year = cal1.get(Calendar.YEAR);
		                int month = cal1.get(Calendar.MONTH);
		                int day = cal1.get(Calendar.DAY_OF_MONTH);
		                String submitedDate = (String.format("%02d", month+1)) + String.format("%02d", day) + (char)(65+i);
		                { if (logger.isDebugEnabled()) logger.debug("SubmitedDate " + submitedDate); }
		                
		                if(i == 0 || !achDetailObj.containsKey(submitedDate)){
		                	ArrayList achDetailList = new ArrayList();
			                achDetailList.add(getPaymentDetailsObj);
			        		achDetailObj.put(submitedDate, achDetailList);
			        	} else if(achDetailObj.containsKey(submitedDate)){
			        		ArrayList oldACHDetailList = (ArrayList)achDetailObj.get(submitedDate);
			        		oldACHDetailList.add(getPaymentDetailsObj);
			        		achDetailObj.put(submitedDate, oldACHDetailList);
			        	}
		        	}
    			}
    			
    			Set keySet = achDetailObj.keySet();
	        	Iterator keys = keySet.iterator();
	        	while (keys.hasNext()) {
			        String keyValue = (String)keys.next();
			        ArrayList getACHDetailList = (ArrayList)achDetailObj.get(keyValue);
	    	        
					String authRequestURL = webServiceURI + "?merchid=" + merchid;
					authRequestURL += "&date=" + keyValue.substring(0,4);
					
					{ if (logger.isDebugEnabled()) logger.debug("Going to call web service URL with date parameter...." + authRequestURL); }
					JSONObject authResponse = authorizeTransaction(authRequestURL,username,password);
					logger.debug("Web service has been called.");
					
			        if(authResponse.has("txns")){
			        	JSONArray jsonArray = new JSONArray(authResponse.get("txns").toString());
			        	if(jsonArray.length() > 0){
			        		for(int i = 0 ; i < getACHDetailList.size(); i++){		    		        	
		    		        	Map getPaymentDetailsObj = (Map)getACHDetailList.get(i);
		    	    			String getPaymentPolicyKey = (getPaymentDetailsObj.get("PolicyKey") == null ? "" : getPaymentDetailsObj.get("PolicyKey").toString());
		    	    			String getPaymentTransactionSequence = (getPaymentDetailsObj.get("TransactionSequence") == null ? "" : getPaymentDetailsObj.get("TransactionSequence").toString());
		    	    			String getPaymentDetail = (getPaymentDetailsObj.get("Detail") == null ? "" : getPaymentDetailsObj.get("Detail").toString());
		    	    			String getPaymentAmount = (getPaymentDetailsObj.get("Amount") == null ? "" : getPaymentDetailsObj.get("Amount").toString());
		    	    			String getPaymentTransactionID = (getPaymentDetailsObj.get("TransactionID") == null ? "" : getPaymentDetailsObj.get("TransactionID").toString());
		    	    			String getPaymentTransactionDate = (getPaymentDetailsObj.get("TransactionDate") == null ? "" : getPaymentDetailsObj.get("TransactionDate").toString());
		    	    			String getPaymentTransactionStatus = (getPaymentDetailsObj.get("TransactionStatus") == null ? "" : getPaymentDetailsObj.get("TransactionStatus").toString());
		    	    			String getPaymentPaymentMode = (getPaymentDetailsObj.get("PaymentMode") == null ? "" : getPaymentDetailsObj.get("PaymentMode").toString());
		    	    			String getLastUpdateUserID = (getPaymentDetailsObj.get("LastUpdateUserID") == null ? "" : getPaymentDetailsObj.get("LastUpdateUserID").toString());
		    	    			String quoteNumber = (getPaymentDetailsObj.get("QuoteNumber") == null ? "" : getPaymentDetailsObj.get("QuoteNumber").toString());
		    	    			String accountName = (getPaymentDetailsObj.get("AccountName") == null ? "" : getPaymentDetailsObj.get("AccountName").toString());
		    	    			
				        		for(int j=0; j<jsonArray.length(); j++){
				        			JSONObject object = jsonArray.getJSONObject(j);
				    		        String retrefCode = (object.isNull("retref") ? "" : object.getString("retref"));
				    		        
				    		        if(!"".equals(retrefCode)){	
				    		        	
										{ if (logger.isDebugEnabled()) logger.debug("retref :    " + retrefCode); }
				    	    			if(retrefCode.equals(getPaymentTransactionID)){
				    	    				String statusValue = (object.isNull("status") ? "" : object.getString("status"));
						    		        String achreturnCode = (object.isNull("achreturncode") ? "" : object.getString("achreturncode"));
											{ if (logger.isDebugEnabled()) logger.debug("Status    :    " + statusValue); }
											{ if (logger.isDebugEnabled()) logger.debug("AchreturnCode   :    " + achreturnCode); }
						    		        
											{ if (logger.isDebugEnabled()) logger.debug("Match retref Code " + getPaymentTransactionID + "  "+ new Timestamp(new Date().getTime())); }
											{ if (logger.isDebugEnabled()) logger.debug("Match policy " + quoteNumber + "  "+ new Timestamp(new Date().getTime())); }
				    	    				
				    	    				ctx.put("TransactionID", getPaymentTransactionID);
				    	    				ctx.put("Amount", getPaymentAmount);
				    	    				ctx.put("PaymentMode", getPaymentPaymentMode);
				    	    				ctx.put("TransactionDate", getPaymentTransactionDate);
				    	    				ctx.put("PolicyKey", getPaymentPolicyKey);
				    	    				ctx.put("TransactionSequence", getPaymentTransactionSequence);
				    	    				ctx.put("LastUpdateUserID", getLastUpdateUserID);
				    	    				
				    	    				//Going to issue policy
				    	    				if("".equals(achreturnCode) || achreturnCode == null){
					    	    				ctx.put("TransactionStatus", "Approved");
					    	    				ctx.put("Detail", "This transaction is successfully completed");
					    	    				
						    					List list = SqlResources.getSqlMapProcessor(ctx).select("SqlStmts.UserStatementManualBoQueriesgetFirmDataFromACH", ctx);
						    					if(list != null && list.size() == 1){
													{ if (logger.isDebugEnabled()) logger.debug("Got Firm data from ach " + new Timestamp(new Date().getTime())); }
						    						Map firmMap = (Map)list.get(0);
						    						ctx.putAll(firmMap);
						    					
							    					DocumentManagment mgt = new DocumentManagment();
							    					mgt.processDocumentManagment(ctx);							    					
													{ if (logger.isDebugEnabled()) logger.debug("Policy " + quoteNumber + "  " + " is successfully issued " + new Timestamp(new Date().getTime())); }
							    					
							    					SqlResources.getSqlMapProcessor(ctx).update("SqlStmts.UserStatementManualBoQueriesupdatePaymentDetailPayment", ctx);
							    					SqlResources.getSqlMapProcessor(ctx).update("SqlStmts.UserStatementManualBoQueriespolicyLastUpdateTimeStamp", ctx);
													{ if (logger.isDebugEnabled()) logger.debug("PaymentDetail " + quoteNumber + "is updated with status approved " + new Timestamp(new Date().getTime())); }
						    					}
						    					Map approvedStatusObj = new HashMap<String, ArrayList>();
						    					approvedStatusObj.put("QuoteNumber", quoteNumber);
						    					approvedStatusObj.put("AccountName", accountName);
						    					approvedStatusList.add(approvedStatusObj);
				    	    				} else if(!"".equals(achreturnCode) && achreturnCode != null){
				    	    					Map processedStatusObj = new HashMap<String, ArrayList>();
				    	    					processedStatusObj.put("QuoteNumber", quoteNumber);
				    	    					processedStatusObj.put("AccountName", accountName);
				    	    					processingStatusList.add(processedStatusObj);
									        } 
				    	    				break;
				    	    			}
				    	    		}
						        }
			        		}
			        	}
			        }
	        	}
			}
			
    		//Policies could not completed in certain time period
			List getPaymentDetailsLessThanThreeList = (List)SqlResources.getSqlMapProcessor(ctx).select("SqlStmts.UserStatementManualBoQueriesgetACHPaymentDetailLessThanThree", ctx);
    		if(getPaymentDetailsLessThanThreeList != null && getPaymentDetailsLessThanThreeList.size() > 0 
    				&& !getPaymentDetailsLessThanThreeList.isEmpty()){
				{ if (logger.isDebugEnabled()) logger.debug("Policies count " + getPaymentDetailsLessThanThreeList.size()); }
    			
    			for(int k = 0 ; k < getPaymentDetailsLessThanThreeList.size(); k++){
	    			Map getPaymentDetailsObj = (Map)getPaymentDetailsLessThanThreeList.get(k);
	    			String getPaymentPolicyKey = (getPaymentDetailsObj.get("PolicyKey") == null ? "" : getPaymentDetailsObj.get("PolicyKey").toString());
	    			String getPaymentTransactionSequence = (getPaymentDetailsObj.get("TransactionSequence") == null ? "" : getPaymentDetailsObj.get("TransactionSequence").toString());
	    			String getPaymentDetail = (getPaymentDetailsObj.get("Detail") == null ? "" : getPaymentDetailsObj.get("Detail").toString());
	    			String getPaymentAmount = (getPaymentDetailsObj.get("Amount") == null ? "" : getPaymentDetailsObj.get("Amount").toString());
	    			String getPaymentTransactionID = (getPaymentDetailsObj.get("TransactionID") == null ? "" : getPaymentDetailsObj.get("TransactionID").toString());
	    			String getPaymentTransactionDate = (getPaymentDetailsObj.get("TransactionDate") == null ? "" : getPaymentDetailsObj.get("TransactionDate").toString());
	    			String getPaymentTransactionStatus = (getPaymentDetailsObj.get("TransactionStatus") == null ? "" : getPaymentDetailsObj.get("TransactionStatus").toString());
	    			String getPaymentPaymentMode = (getPaymentDetailsObj.get("PaymentMode") == null ? "" : getPaymentDetailsObj.get("PaymentMode").toString());
	    			String getLastUpdateUserID = (getPaymentDetailsObj.get("LastUpdateUserID") == null ? "" : getPaymentDetailsObj.get("LastUpdateUserID").toString());
	    			String quoteNumber = (getPaymentDetailsObj.get("QuoteNumber") == null ? "" : getPaymentDetailsObj.get("QuoteNumber").toString());
	    			String accountName = (getPaymentDetailsObj.get("AccountName") == null ? "" : getPaymentDetailsObj.get("AccountName").toString());
					
	    			ctx.put("TransactionID", getPaymentTransactionID);
					ctx.put("Amount", getPaymentAmount);
					ctx.put("PaymentMode", getPaymentPaymentMode);
					ctx.put("TransactionDate", getPaymentTransactionDate);
					ctx.put("PolicyKey", getPaymentPolicyKey);
					ctx.put("TransactionSequence", getPaymentTransactionSequence);
					ctx.put("LastUpdateUserID", getLastUpdateUserID);
					ctx.put("TransactionStatus", "Declined");
					ctx.put("Detail", "This transaction is declined");
					Map declinedStatusObj = new HashMap<String, ArrayList>();
					declinedStatusObj.put("QuoteNumber", quoteNumber);
					declinedStatusObj.put("AccountName", accountName);
					declinedStatusList.add(declinedStatusObj);
					
					{ if (logger.isDebugEnabled()) logger.debug("Policy " + quoteNumber + " is declined " + new Timestamp(new Date().getTime())); }
					SqlResources.getSqlMapProcessor(ctx).update("SqlStmts.UserStatementManualBoQueriesupdatePaymentDetailPayment", ctx);
					SqlResources.getSqlMapProcessor(ctx).update("SqlStmts.UserStatementManualBoQueriespolicyLastUpdateTimeStamp", ctx);
					{ if (logger.isDebugEnabled()) logger.debug("PaymentDetail " + quoteNumber + " is updated with status Declined " + new Timestamp(new Date().getTime())); }
    			}
    		}
    		
    		//To issue policy manually 
    		List getPaymentDetailsManualApprovedList = (List)SqlResources.getSqlMapProcessor(ctx).select("SqlStmts.UserStatementManualBoQueriesgetACHPaymentDetailManual", ctx);
    		
			if(getPaymentDetailsManualApprovedList != null && getPaymentDetailsManualApprovedList.size() > 0 
					&& !getPaymentDetailsManualApprovedList.isEmpty()){
				{ if (logger.isDebugEnabled()) logger.debug("Policies count " + getPaymentDetailsManualApprovedList.size()); }
    			
    			for(int k = 0 ; k < getPaymentDetailsManualApprovedList.size(); k++){
	    			Map getPaymentDetailsObj = (Map)getPaymentDetailsManualApprovedList.get(k);
	    			String getPaymentPolicyKey = (getPaymentDetailsObj.get("PolicyKey") == null ? "" : getPaymentDetailsObj.get("PolicyKey").toString());
	    			String getPaymentTransactionSequence = (getPaymentDetailsObj.get("TransactionSequence") == null ? "" : getPaymentDetailsObj.get("TransactionSequence").toString());
	    			String getPaymentDetail = (getPaymentDetailsObj.get("Detail") == null ? "" : getPaymentDetailsObj.get("Detail").toString());
	    			String getPaymentAmount = (getPaymentDetailsObj.get("Amount") == null ? "" : getPaymentDetailsObj.get("Amount").toString());
	    			String getPaymentTransactionID = (getPaymentDetailsObj.get("TransactionID") == null ? "" : getPaymentDetailsObj.get("TransactionID").toString());
	    			String getPaymentTransactionDate = (getPaymentDetailsObj.get("TransactionDate") == null ? "" : getPaymentDetailsObj.get("TransactionDate").toString());
	    			String getPaymentTransactionStatus = (getPaymentDetailsObj.get("TransactionStatus") == null ? "" : getPaymentDetailsObj.get("TransactionStatus").toString());
	    			String getPaymentPaymentMode = (getPaymentDetailsObj.get("PaymentMode") == null ? "" : getPaymentDetailsObj.get("PaymentMode").toString());
	    			String getLastUpdateUserID = (getPaymentDetailsObj.get("LastUpdateUserID") == null ? "" : getPaymentDetailsObj.get("LastUpdateUserID").toString());
	    			String quoteNumber = (getPaymentDetailsObj.get("QuoteNumber") == null ? "" : getPaymentDetailsObj.get("QuoteNumber").toString());
	    			String accountName = (getPaymentDetailsObj.get("AccountName") == null ? "" : getPaymentDetailsObj.get("AccountName").toString());
					
	    			ctx.put("PolicyKey", getPaymentPolicyKey);
					ctx.put("TransactionSequence", getPaymentTransactionSequence);
					
					Map manualApprovedStatusObj = new HashMap<String, ArrayList>();
					manualApprovedStatusObj.put("QuoteNumber", quoteNumber);
					manualApprovedStatusObj.put("AccountName", accountName);
					manualApprovedStatusList.add(manualApprovedStatusObj);
					
					List list = SqlResources.getSqlMapProcessor(ctx).select("SqlStmts.UserStatementManualBoQueriesgetFirmDataFromACH", ctx);
					if(list != null && list.size() == 1){
						{ if (logger.isDebugEnabled()) logger.debug("Got Firm data from ach " + new Timestamp(new Date().getTime())); }
						Map firmMap = (Map)list.get(0);
						ctx.putAll(firmMap);
					
    					DocumentManagment mgt = new DocumentManagment();
    					mgt.processDocumentManagment(ctx);							    					
						{ if (logger.isDebugEnabled()) logger.debug("Policy " + quoteNumber + "  " + " is successfully issued " + new Timestamp(new Date().getTime())); }
    					
    					ctx.put("TransactionID", getPaymentTransactionID);
    					ctx.put("Amount", getPaymentAmount);
    					ctx.put("PaymentMode", getPaymentPaymentMode);
    					ctx.put("TransactionDate", getPaymentTransactionDate);
    					ctx.put("LastUpdateUserID", getLastUpdateUserID);
    					ctx.put("TransactionStatus", getPaymentTransactionStatus);
    					ctx.put("Detail", "This transaction is completed manually");
    					
    					SqlResources.getSqlMapProcessor(ctx).update("SqlStmts.UserStatementManualBoQueriesupdatePaymentDetailPayment", ctx);
    					SqlResources.getSqlMapProcessor(ctx).update("SqlStmts.UserStatementManualBoQueriespolicyLastUpdateTimeStamp", ctx);
						{ if (logger.isDebugEnabled()) logger.debug("PaymentDetail " + quoteNumber + "is updated with status Manual " + new Timestamp(new Date().getTime())); }
					}
    			}
			}
    		sendStatusMail(approvedStatusList, processingStatusList, manualApprovedStatusList, declinedStatusList);
    		
		}catch(Exception e){
			logger.error("Error in checkACHStatus " + e.getMessage() + " " + new Timestamp(new Date().getTime()), e);
		}
		return false;
	}
	
	public static JSONObject authorizeTransaction(String webServiceURI, String username, String password) {
		try {
			ClientConfig clientConfig = new DefaultClientConfig();

			Client client = Client.create(clientConfig);

			WebResource webResource = client.resource(webServiceURI);

			String authString = username + ":" + password;
			
			String authStringEnc = new BASE64Encoder().encode(authString.getBytes());
			
			ClientResponse response = webResource
					.accept(MediaType.APPLICATION_JSON)
					.type(MediaType.APPLICATION_JSON)
					.header("Authorization", "Basic " + authStringEnc)
					.get(ClientResponse.class);
			
			if (response.getStatus() != 200) {
				return null;
			}
			
			String result = response.getEntity(String.class);
			logger.debug(result);			
			return new JSONObject(result);
		} catch (Exception e) {
			logger.error("Error in authorizeTransaction " + e.getMessage() + " " + new Timestamp(new Date().getTime()), e);
		}
		return null;
	}
	
	public static void sendStatusMail(ArrayList approvedStatus, ArrayList processingStatus, ArrayList manualApprovedStatus, ArrayList declinedStatus){
		try{
			MailSender mailSender = new MailSender();				
			mailSender.setToAdd(SystemProperties.getInstance().getString("appl.AccountantIns.ach.mail.to.address"));
			mailSender.setCcAdd(SystemProperties.getInstance().getString("appl.AccountantIns.ach.mail.cc.address"));
			mailSender.setContentType("text/html");			
			mailSender.setSubject("ACH Status Email");
			mailSender.setBody(generatePolicyIssueBody(approvedStatus,processingStatus, manualApprovedStatus, declinedStatus));
			mailSender.sendMail();
		}catch(Exception e){
			logger.error("Error in sendStatusMail " + e.getMessage() + " " + new Timestamp(new Date().getTime()), e);
		}
	}
	
	private static String generatePolicyIssueBody(ArrayList approvedStatus, ArrayList processingStatus, ArrayList manualApprovedStatus, ArrayList declinedStatus) throws Exception
    {
		String msg = "";
		
		msg = msg + "<table>";
		msg = msg + "<tr>";
		msg = msg + "<td>";
		
		msg = msg + "ACH transactions has been processed on date " + new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a").format(new Timestamp(new Date().getTime())) + "<br/>";		
		msg = msg + "Please find status below" + "<br/><br/>";
		
		if(approvedStatus != null && approvedStatus.size() > 0 && !approvedStatus.isEmpty()){
			msg = msg + "Approved Policies : <br/>";
			msg += getStatusTable(approvedStatus) + "<br/>";
		} else {
			msg += "<br/>";
		}
		if(processingStatus != null && processingStatus.size() > 0 && !processingStatus.isEmpty()){
			msg = msg + "Under Processing Policies : <br/>";
			msg += getStatusTable(processingStatus) + "<br/>";
		} else {
			msg += "<br/>";
		}
		if(manualApprovedStatus != null && manualApprovedStatus.size() > 0 && !manualApprovedStatus.isEmpty()){
			msg = msg + "Manually Approved Policies : <br/>";
			msg += getStatusTable(manualApprovedStatus) + "<br/>";
		}
		if(declinedStatus != null && declinedStatus.size() > 0 && !declinedStatus.isEmpty()){
			msg = msg + "Declined Policies : <br/>";
			msg += getStatusTable(declinedStatus) + "<br/>";
		} else {
			msg += "<br/>";
		}
		 
		msg = msg + "Best Regards," + "<br/><br/>";
        msg = msg + "<b>Protexure Accountant</b>" + "<br/>";
        msg = msg + "<b>T:  877-569-4111" + "  " + "ext 1</b>" + "<br/>";
        msg = msg + "<b>F:  (440) 333-3214</b>" + "<br/>";
        msg = msg + "<b>www.protexureaccountants.com</b><br/><br/>";
        
		msg = msg + "</td>";
		msg = msg + "</tr>";		
		msg = msg + "</table>";
		
		return msg;
    }
	
	public static String getStatusTable(ArrayList approvedStatus){
		String msg = "";
		if(approvedStatus != null && approvedStatus.size() > 0 && !approvedStatus.isEmpty()){
			msg = msg + "<table border=\"1\">";
			msg = msg + "<tr>";
			msg = msg + "<td>QuoteNumber";
			msg = msg + "</td>";
			msg = msg + "<td>Account Name";
			msg = msg + "</td>";
			msg = msg + "</tr>";
			for(int paymentDetailCount = 0 ; paymentDetailCount < approvedStatus.size(); paymentDetailCount++){

	        	Map approvedStatusObj = (Map)approvedStatus.get(paymentDetailCount);
	        	String quoteNumber = (approvedStatusObj.get("QuoteNumber") == null ? "" : approvedStatusObj.get("QuoteNumber").toString());
	        	String accountName = (approvedStatusObj.get("AccountName") == null ? "" : approvedStatusObj.get("AccountName").toString());
	        	
		        msg = msg + "<tr>";
				msg = msg + "<td>" + quoteNumber;
				msg = msg + "</td>";
				msg = msg + "<td>" + accountName;
				msg = msg + "</td>";
				msg = msg + "</tr>";
	        }
			msg = msg + "</table>";
		}
		return msg;
	}
}
