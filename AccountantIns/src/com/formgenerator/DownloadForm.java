package com.formgenerator;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.fop.servlet.ServletContextURIResolver;
import org.jdom.Element;

import com.fop.DownloadFOP;
import com.fop.XML2PDF;
import com.manage.managecomponent.components.SetParametersForStoredProcedures;
import com.manage.managemetadata.functions.commonfunctions.RuleUtils;
import com.ormapping.ibatis.SqlResources;
import com.processor.RequestProcessor;
import com.userbo.AccountUtils;
import com.userbo.AccountantConstants;
import com.userbo.AccountantDateUtils;
import com.util.Context;
import com.util.HtmlConstants;
import com.util.InetLogger;
import com.util.SystemProperties;
import com.util.XMLUtils;

public class DownloadForm {
	
	private static InetLogger logger = InetLogger.getInetLogger(DownloadForm.class);
	
	public void generateForm(Context ctx, List listFormID, OutputStream out, String baseUrl, ServletContextURIResolver uriResolver) throws Exception {
		
		String foFile = "";		
		try{			
			
			//Asked By Pat
			Object objnew = RuleUtils.executeRule(ctx, "AccountantRule.newFormFlow");
			boolean flagnew = false;	

			if("1".equals(ctx.get("CompanyKey").toString()))
			{
				if(objnew instanceof Boolean)
					flagnew = (Boolean) objnew;
				if(flagnew)
					foFile = SystemProperties.getInstance().getString("html.basedir") + "foxsl//wrapnew.xsl";	
				else 
					foFile = SystemProperties.getInstance().getString("html.basedir") + "foxsl//wrap.xsl";
			}
			if("3".equals(ctx.get("CompanyKey").toString()))
				foFile = SystemProperties.getInstance().getString("html.basedir") + "ISMIE//PolicyForms//wrapnew.xsl";	
			logger.debug("generateForm--------"+foFile);			
			
			populateEndorsement(ctx, listFormID);
			fetchDataForXml(ctx);
			if(ctx.get(HtmlConstants.DOCUMENTSERVLETCONTEXT) != null)
				baseUrl = "file:///" + ((ServletContext)ctx.get(HtmlConstants.DOCUMENTSERVLETCONTEXT)).getRealPath("/");
			
			new XML2PDF().convertXMLtoStream(foFile, new StringBuffer(generateDataXml(ctx)), listFormID, out, baseUrl, uriResolver,ctx);
		}
		catch(Exception e){
			logger.error("Problem in generating forms for Policy Number : " +ctx.get("PolicyNumber"));
			throw e;
		}
	}
	
	public void populateEndorsement(Context ctx, List listFormID) {		
		
		String formIDs = "";
		Map mapEndorsment = new HashMap();
		for(int i=0; i<listFormID.size(); i++)
		{
			Map map = (Map) listFormID.get(i);
			String formID = (String) map.get("FormID");	
			
			//String formID = (String) listFormID.get(i);			
			if("APL 100-106-2010".equals(formID))
				mapEndorsment.put("APL1062010", formID);
//			else if("APL - 104 (12/09)".equals(formID))
//				mapEndorsment.put("APL1042009", formID);
//			 if("APL - 105 (12/09)".equals(formID))
//				mapEndorsment.put("APL1052009", formID);
//			else if("APL - AMEND - MT (12/09)".equals(formID))
//				mapEndorsment.put("APLAMENDMT2009", formID);
//			else if("APL - AMEND - ND (12/09)".equals(formID))
//				mapEndorsment.put("APLAMENDND2009", formID);
//			else if("APL-AMEND-SC (12/09)".equals(formID))
//				mapEndorsment.put("APLAMENDSC2009", formID);
//			else if(" APL - AMEND - NH (01/10)".equals(formID))
//				mapEndorsment.put("APLAMENDNH2010", formID);
//			else if("OR - AMEND - 2010".equals(formID))
//				mapEndorsment.put("ORAMEND2010", formID);			
			else if(formID.indexOf("(") < 0)
				mapEndorsment.put(formID.replace("-", "").replace(" ", "").replace("#", ""), formID);
			else if(formID.indexOf("(") > 0)
				mapEndorsment.put(formID.replace("-", "").replace(" ", "").replace("(", "").replace("/", "").replace(")","").replace("#", ""), formID);
			
			formIDs = formIDs + ", " + formID;			
			logger.debug("populateEndorsement......"+formIDs);
		}
		
		if(formIDs.length() > 0)
			ctx.put("Endorsements", formIDs.substring(1, formIDs.length()));
		else
			ctx.put("Endorsements", formIDs);
		
		ctx.put("endorsements_freeform_01", mapEndorsment);
	}

	public void fetchDataForXml(Context ctx) {		
		
		try{
		Object objPoilcy = SqlResources.getSqlMapProcessor(ctx).findByKey("SqlStmts.UserStatementpdfquotelettergetPriorPolicyInfo", ctx);
		ctx.put("policy_freeform_01", objPoilcy);
		
		/*Object objUserDetail = SqlResources.getSqlMapProcessor(ctx).select("SqlStmts.UserStatementpdfquotelettergetAgentDetail", ctx);
		ctx.put("userdetail_freeform_01",objUserDetail );*/
		getAgentData(ctx);
		
		Object objAddress = SqlResources.getSqlMapProcessor(ctx).findByKey("SqlStmts.UserStatementpdfquotelettergetAddressdetails", ctx);
		ctx.put("address_freeform_01", objAddress);
		
		Map mapFooter =null;	
		Object objPolicyFormFooter = SqlResources.getSqlMapProcessor(ctx).select("PolicyForm.findAllByPartialKey", ctx);
		
		mapFooter = DownloadFOP.populateFooter(objPolicyFormFooter);
		mapFooter.put("FormInclude", "Y");	
		if(mapFooter != null){
			ctx.put("policyformfooter_freeform_01", mapFooter);
		}
		
		Object objCoveragePoilcy = SqlResources.getSqlMapProcessor(ctx).findByKey("SqlStmts.confirmationviewgetQuoteDetail", ctx);
		Map map = null;
		
		if(objCoveragePoilcy != null && objCoveragePoilcy instanceof Map)
		{
			map = (Map)objCoveragePoilcy;
			if(map.get("XmlOutputDatafromRating") != null)
			{
				String outputString = map.get("XmlOutputDatafromRating").toString();
				Element root = XMLUtils.parseXMLRootElement(outputString);					
				Element outputElement = root.getChild("PremiumInfo");
				Map out = populateOutput(outputElement);
				map.putAll(out);
				map.remove("XmlOutputDatafromRating");
			}
		}		
		
		ctx.put("policycoverage_freeform_01", map);	
		
		Object objProLiabIns = SqlResources.getSqlMapProcessor(ctx).findByKey("SqlStmts.UserStatementpdfquotelettergetProfessionalLiabilityInsDetail", ctx);
		ctx.put("professionalliabilityinsdetail_freeform_01", objProLiabIns);
		
		Object objFinalisedQuote = SqlResources.getSqlMapProcessor(ctx).findByKey("SqlStmts.UserStatementsaveAccountstmtsgetFinalisedQuote", ctx);
		Map mapFinalisedQuote = null;
		String IsClaimExpensesTypeFlag = "N";
		String IsClaimOptionTypeFlag = "N";
		String OKStateRuleFlag = "N";
		
		if(objFinalisedQuote != null && objFinalisedQuote instanceof Map)	
			mapFinalisedQuote = (Map) objFinalisedQuote;
		
		if(mapFinalisedQuote != null && !mapFinalisedQuote.isEmpty())
		{			
			if(mapFinalisedQuote.get("IsClaimExpensesType") != null)
				IsClaimExpensesTypeFlag = mapFinalisedQuote.get("IsClaimExpensesType").toString();
			if(mapFinalisedQuote.get("IsClaimOptionType") != null)
				IsClaimOptionTypeFlag = mapFinalisedQuote.get("IsClaimOptionType").toString();
			
			int agrLim = 0;			
			try{
				agrLim = mapFinalisedQuote.get("AggregateLimit") !=null ? Integer.parseInt(mapFinalisedQuote.get("AggregateLimit").toString())  : 0;
			}catch(Exception e){
				logger.debug("Number Format Exception " + e.getMessage());
			}
			
			if(agrLim < 1000000 && "Y".equals(IsClaimExpensesTypeFlag))
				OKStateRuleFlag = "Y";
			
		}
		
		ctx.put("ClaimExpensesTypeFlag", IsClaimExpensesTypeFlag);	
		ctx.put("ClaimOptionTypeFlag", IsClaimOptionTypeFlag);
		ctx.put("OKStateRuleFlag", OKStateRuleFlag);
		
		if(objAddress != null){
			
			if(((Map)objAddress).get("StateCode") != null && "KY".equals(((Map)objAddress).get("StateCode").toString() )){
				boolean IsTaxablePremium = AccountUtils.checkForKYMuncipalityTax(ctx, true);	;
				if (!IsTaxablePremium)
					ctx.put("KYTaxFormFlag", "N");
				else
					ctx.put("KYTaxFormFlag", "Y");
			}
		}
		
		if(ctx.get("StateCode") != null && "ND".equals(ctx.get("StateCode").toString())){
			logger.debug("This is ND state | Check if any  quote is defense inside.");
			ctx.put("RuleRequestType","NDRuleForFormAttachmentPolicyForm");
			ctx.put("PolicyKey_rule",ctx.get("PolicyKey").toString());
			ctx.put("StateCode_rule",ctx.get("StateCode"));
			Object ruleMapObject = SqlResources.getSqlMapProcessor(ctx).findByKey("Account.StateSpecificRulesAccountant_proc", ctx); 
			if(ruleMapObject != null && ruleMapObject instanceof Map){						
				Map ruleMap = (Map)ruleMapObject;
				String IsDefensePaidWithinRuleND = ruleMap.get("IsDefensePaidWithinRuleND") != null ? ruleMap.get("IsDefensePaidWithinRuleND").toString() : "";
				logger.debug("Is IsDefensePaidWithinRuleND ? " + IsDefensePaidWithinRuleND);						
				ctx.put("IsDefensePaidWithinRuleND", IsDefensePaidWithinRuleND);
			}
		}
		
		
		ctx.remove("loggerbuffer");
		
		}
		catch(Exception e){
			logger.error("Problem in fetching data for Policy Number : " +ctx.get("PolicyNumber"));
			e.printStackTrace();
		}
		
	}
	
	public String generateDataXml(Context ctx) {
		
		String dataXML = "";
		
		try{			
			dataXML = new RequestProcessor().generateResponseXml(ctx,false);		
		
		}
		catch(Exception e){
			logger.error("Problem in generating data xml for Policy Number : " +ctx.get("PolicyNumber"));
		}
		
		//System.out.println(dataXML);
		return dataXML;
		//return "<response><address_freeform_01><data><AccountName>TestPolicy</AccountName><ContactPerson>Raghraj Singh</ContactPerson><Address1>No 224</Address1><Address2>NT</Address2><City>East Delhi</City><StateDesc>Delhi</StateDesc><Zip>110093</Zip></data></address_freeform_01><catalog><cd><title>Empire Burlesque</title><artist>Bob Dylan</artist><country>USA</country><company>Columbia</company><price>10.90</price><year>1985</year></cd></catalog></response>";  
		
	}
	
	public static Map populateOutput(Element element)
	{
    	StringBuffer buffer = new StringBuffer();    	
		List children = element.getChildren();
		if(children == null)
			return new HashMap();
		Map out = new HashMap();
		for(int i=0; i<children.size(); i++)
	    {
			Element child = (Element) children.get(i);
			String name = child.getName();
			String str = child.getTextTrim();			
			if("MTTaxAmmount".equals(name) || "CountyTaxAmmount".equals(name) || "State1TaxAmmount".equals(name) || "State2TaxAmmount".equals(name)) {
				str = String.valueOf(Math.round(Float.parseFloat(str)));
			}
			out.put(name, str);      
	    }  	
		
		return out;
	}
	private void getAgentData(Context ctx) throws Exception
	{
		ctx.put("OLD_UserNo", ctx.get("UserNo"));
		String agentNo = SystemProperties.getInstance().getString(
				"appl." + ctx.getProject() + ".userno");
		/*Context newCtx = new Context();
		newCtx.putAll(ctx);*/
		ctx.put("UserNo", agentNo);
		
		Object objUserDetail = SqlResources.getSqlMapProcessor(ctx).select("SqlStmts.UserStatementpdfquotelettergetAgentDetail", ctx);
		ctx.put("userdetail_freeform_01",objUserDetail );
		ctx.put("UserNo", ctx.get("OLD_UserNo"));
		
	}

	public void processIndicationForm(Context ctx, OutputStream out, String baseUrl, ServletContextURIResolver uriResolver) throws Exception {
		
		String foFile = SystemProperties.getInstance().getString("html.basedir") + "foxsl//indication.xsl";	
		//String foFile = SystemProperties.getInstance().getString("html.basedir") + "foxsl2017//ratingoutput.xsl";
		try{			
			
			fetchDataForIndication(ctx);
			//fetchDataForRatingOutput(ctx);
			/*StringBuffer xmlData = new StringBuffer(generateDataXml(ctx));
			new XML2PDF().convertPOToPDF(foFile, xmlData, out);*/
			new XML2PDF().convertPOToPDF(foFile, new StringBuffer(generateDataXml(ctx)), out, baseUrl, uriResolver);
		}
		catch(Exception e){
			logger.error("Problem in generating forms for Policy Number : " +ctx.get("PolicyNumber") + " " + e.getMessage());
			throw e;
		}
	}
	
	public static void fetchDataForIndication(Context ctx) throws Exception {
		try{
			
			Object objPoilcy = SqlResources.getSqlMapProcessor(ctx).findByKey("SqlStmts.UserStatementsaveAccountstmtsgetPriorPolicyInfoForPolicyForm", ctx);
			ctx.put("policy_freeform_01", objPoilcy);
			
			Object objUserDetail = SqlResources.getSqlMapProcessor(ctx).findByKey("SqlStmts.UserStatementpdfquotelettergetAgentDetail", ctx);
			ctx.put("userdetail_freeform_01",objUserDetail );
			
			Object objAddress = SqlResources.getSqlMapProcessor(ctx).findByKey("SqlStmts.UserStatementpdfquotelettergetAddressdetails", ctx);
			ctx.put("address_freeform_01", objAddress);
			
			Object objFirmData = SqlResources.getSqlMapProcessor(ctx).findByKey("SqlStmts.UserStatementsaveAccountstmtsgetFirmLWFullDetails", ctx);
			ctx.put("firm_freeform_01", objFirmData);
			
			Object objProfessionalLiabilityData = SqlResources.getSqlMapProcessor(ctx).findByKey("SqlStmts.UserStatementManualBoQueriesgetProfessionalLiabilityInsDetailForQQ", ctx);
			ctx.put("professionalLiability_freeform_01", objProfessionalLiabilityData);
			
			ctx.put("FinalisedQuote_TransactionSequence", ctx.get("TransactionSequence"));
			
			Object listIndication = SqlResources.getSqlMapProcessor(ctx).select("SqlStmts.indicationinjackdownloadIndicationgetIndicationList", ctx);

			String IsClaimExpensesTypeFlag = "N";
			List indicationList = new ArrayList();
			if (listIndication != null && listIndication instanceof List) {
				List list = (List) listIndication;
				// For RatingTrace
				for (int i = 0; i < list.size(); i++) {
					Map map = (Map) list.get(i);
					if (map.get("XmlOutputDatafromRating") != null) {
						String outputString = map.get("XmlOutputDatafromRating").toString();
						Element root = XMLUtils.parseXMLRootElement(outputString);
						Element outputElement = root.getChild("PremiumInfo");
						Map out = populateOutput(outputElement);
						map.putAll(out);
						map.remove("XmlOutputDatafromRating");
						indicationList.add(map);
					}

					if (map.get("IsClaimExpensesType") != null && "Y".equals(map.get("IsClaimExpensesType").toString()))
						IsClaimExpensesTypeFlag = "Y";
				}
			}

			ctx.put("indication_list_01", indicationList);
			
			
			Object objCoveragePoilcy = SqlResources.getSqlMapProcessor(ctx).findByKey("SqlStmts.UserStatementsaveAccountstmtsgetQuoteDetail", ctx);
			ctx.put("policycoverage_freeform_01", objCoveragePoilcy);
			
			ctx.put("PolicyKey", Integer.parseInt(ctx.get("PolicyKey").toString()));
			ctx.put("VersionSequence", Integer.parseInt(ctx.get("VersionSequence").toString()));
			List aoplist = SqlResources.getSqlMapProcessor(ctx).select("AOP.GetAOPFieldsIndication_p", ctx);
			
			
			if(aoplist != null && !aoplist.isEmpty()){
				int totalAopPer = 0;
				int value = 0;
				int div = aoplist.size()/2;
				int i=0;
				Map map = null;
				List fstlist = new ArrayList();
				for(i=0; i<div; i++){
					map = (Map)aoplist.get(i);
					value = Integer.parseInt(map.get("PercentageValue").toString());
					totalAopPer += value;
					if(value == 0)
						map.put("PercentageValue", "");
					fstlist.add(aoplist.get(i));
				}
				List sndlist = new ArrayList();
				for(; i<aoplist.size(); i++){
					map = (Map)aoplist.get(i);
					value = Integer.parseInt(map.get("PercentageValue").toString());
					totalAopPer += value;
					if(value == 0)
						map.put("PercentageValue", "");
					sndlist.add(aoplist.get(i));
				}
				ctx.put("aop_total", totalAopPer);
				ctx.put("aopData_list_01", fstlist);
				ctx.put("aopData_list_02", sndlist);
			}
			
			
			
			if(ctx.get("FirmYear") != null){
				if(Integer.parseInt(ctx.get("FirmYear").toString()) == -5){
					ctx.put("FirmYearIndiCation", "Mature");
				} else {
					ctx.put("FirmYearIndiCation", Math.abs(Integer.parseInt(ctx.get("FirmYear").toString())));
				}
			}
						
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void fetchDataForRatingOutput(Context ctx) throws Exception {
		try{			
			ctx.put("FinalisedQuote_TransactionSequence", ctx.get("TransactionSequence"));
			Object objCoveragePoilcy = SqlResources.getSqlMapProcessor(ctx).findByKey("SqlStmts.UserStatementsaveAccountstmtsgetQuoteDetail", ctx);
			ctx.put("policycoverage_freeform_01", objCoveragePoilcy);
			
			if(objCoveragePoilcy != null){
				Map mapCov = (Map) objCoveragePoilcy;
				if(mapCov.get("XmlOutputDatafromRating") != null){
					String xml = (String) mapCov.get("XmlOutputDatafromRating");
					String out = XMLUtils.beautifyXML(xml);
					ctx.put("XmlOutputDatafromRating", out);					
				}
			}			
					
		} catch (Exception e) {
			
		}
	}
	
	//Endorsement Start
	public void fetchDataForXmlEndorsement(Context ctx){
		try{
			Object objPoilcy = SqlResources.getSqlMapProcessor(ctx).findByKey("SqlStmts.UserStatementManualBoQueriesGetEndorsementDetail", ctx);
			ctx.put("policy_freeform_01", objPoilcy);
			
			Object objCancellationDetail = SqlResources.getSqlMapProcessor(ctx).findByKey("SqlStmts.UserStatementManualBoQueriesGetEndorsement", ctx);
			ctx.put("endorsement_freeform_01",objCancellationDetail);
			
			Object objCoveragePoilcyNew = SqlResources.getSqlMapProcessor(ctx).findByKey("SqlStmts.UserStatementManualBoQueriesGetEndorsementCoverage", ctx);
			ctx.put("policycoverage_freeform_01", objCoveragePoilcyNew);	
			
			Object objPriorActDetail = SqlResources.getSqlMapProcessor(ctx).findByKey("SqlStmts.UserStatementpdfquotelettergetProfessionalLiabilityInsDetail", ctx);
			ctx.put("professionalliabilityinsdetail_freeform_01", objPriorActDetail);
			
			/*Object objQuote = SqlResources.getSqlMapProcessor(ctx).findByKey("SqlStmts.UserStatementpdfquotelettergetFinalisedCoveragePolicy", ctx);
			ctx.put("quote_freeform_01", objQuote);*/
			
			Object objQuote = SqlResources.getSqlMapProcessor(ctx).findByKey("SqlStmts.UserStatementManualBoQueriesgetCalculateLimitDeductiblePremium", ctx);
			ctx.put("quote_freeform_01", objQuote);
			
			if(objQuote != null){
				Map mapQuote = (Map) objQuote;
				ctx.put("PreviousfinilazedTransactionSequence", mapQuote.get("PreviousfinilazedTransactionSequence"));
			}
			Object objCoveragePoilcyPreExisting = SqlResources.getSqlMapProcessor(ctx).findByKey("SqlStmts.UserStatementManualBoQueriesGetEndorsementCoveragePreExisting", ctx);
			ctx.put("pre_existing_policycoverage_freeform_01", objCoveragePoilcyPreExisting);
			
			
			processEndorsementData(ctx);
			
		} catch(Exception e){
			logger.error("Problem in fetching data for Policy Number : " +ctx.get("PolicyNumber") + " " + e.getMessage());
			
		}
	}
	
	public void processEndorsementData(Context ctx){
		try{
			//To fill current date
			Map newPC = null;
			DecimalFormat  formatter = new DecimalFormat("#0.00");   
			Map mapGen = new HashMap();
			List listGen = new ArrayList();
			mapGen.put("CurrentDate", AccountantDateUtils.getCurrentDate());
			listGen.add(mapGen);
			ctx.put("general_freeform_01",listGen);
			
			List <String>listFormID = new ArrayList <String> () ;
			
			//Check difference between new premium and existing premium
			Object objQuote1 = ctx.get("quote_freeform_01");
			Object objCoveragePoilcyPreExisting = ctx.get("pre_existing_policycoverage_freeform_01");
			
			double newPremium = 0;
			Map preExistPC = null;
			Map objQuote = null;
			if(ctx.get("TransactionTypeKey") != null && (AccountantConstants.LIMIT_ENDORSEMENT.equals(ctx.get("TransactionTypeKey").toString()) 
					|| AccountantConstants.DEDUCTIBLE_ENDORSEMENT.equals(ctx.get("TransactionTypeKey").toString())
					||AccountantConstants.PRIORACTDATE_ENDORSEMENT.equals(ctx.get("TransactionTypeKey").toString())
					||AccountantConstants.POLICYEXTENSION_ENDORSEMENT.equals(ctx.get("TransactionTypeKey").toString())
					))
			{
				if(objQuote1 == null) {
					 objQuote1 = SqlResources.getSqlMapProcessor(ctx).findByKey("SqlStmts.UserStatementpdfquotelettergetFinalisedCoveragePolicy", ctx);
					 ctx.put("quote_freeform_01", objQuote1);			
				}
				
				if(objCoveragePoilcyPreExisting == null) {					
					objCoveragePoilcyPreExisting = SqlResources.getSqlMapProcessor(ctx).findByKey("SqlStmts.UserStatementManualBoQueriesgetEndorsementCoveragePreExistingFinilized", ctx);
					ctx.put("pre_existing_policycoverage_fLawyersUtilities.amountFormatLawyersreeform_01", objQuote1);			
				}
			}
			
			if(objQuote1 != null){
				objQuote = (Map)objQuote1;
				
				if(ctx.get("NewInvoicedPremium") == null && objQuote.get("ProrataPremium") != null)
					newPremium = Double.parseDouble(objQuote.get("ProrataPremium").toString());
				else if(ctx.get("NewInvoicedPremium") != null)
					newPremium = Double.parseDouble(ctx.get("NewInvoicedPremium").toString());			
				
				if(objQuote.get("Premium") != null){				
					double existingPremium = Double.parseDouble(objQuote.get("Premium").toString());					
					double diffPremium = 0;
					if(existingPremium > newPremium){ // Return Premium
						diffPremium = existingPremium - newPremium;
						ctx.put("PremiumCharged","N");					
					} else if(existingPremium < newPremium){ // Charge Increased Premium
						diffPremium = newPremium - existingPremium;
						ctx.put("PremiumCharged","Y");
					} else if(existingPremium == newPremium){ // No Premium
						diffPremium = newPremium - existingPremium;
						ctx.put("PremiumCharged","S");
					}

					ctx.put("PremiumValue",(formatter.format(diffPremium)));				
				} else{
					ctx.put("PremiumValue",AccountUtils.amountFormatLawyers(newPremium));
					ctx.put("PremiumCharged","Y");
				}
				
			} else if(ctx.get("NewInvoicedPremium") != null){
				newPremium = Double.parseDouble(ctx.get("NewInvoicedPremium").toString());
				ctx.put("PremiumValue",AccountUtils.amountFormatLawyers(newPremium));
				ctx.put("PremiumCharged","Y");
			}
			
			//End Check difference between new premium and existing premium
			
			//Check  difference in Limit, Deductible, IsClaimExpensesType, IsClaimOptionType, IsDollarDefense
			//Object objCoveragePoilcyPreExisting = ctx.get("pre_existing_policycoverage_freeform_01");
			Object objCoveragePoilcyNew = ctx.get("policycoverage_freeform_01");
			if(objCoveragePoilcyPreExisting == null && objQuote1 != null)
				objCoveragePoilcyPreExisting = objQuote1;
			
			if(objCoveragePoilcyPreExisting != null  && objCoveragePoilcyNew != null){
				preExistPC = (Map) objCoveragePoilcyPreExisting;
				
				if(objCoveragePoilcyNew instanceof List)
					newPC = (Map) ((List) objCoveragePoilcyNew).get(0);
				else
					newPC = (Map) objCoveragePoilcyNew;
			}
			AccountUtils.getPolicyData(ctx);
			
			if("3".equals(ctx.get("CompanyKey").toString()))
				setEndorsementForIsmiePolicy(ctx,newPC,preExistPC);
			else
				setEndorsementForNonIsmiePolicy(ctx,newPC,preExistPC);
						
		} catch(Exception e){
			logger.error("Problem in fetching data for Policy Number : " +ctx.get("PolicyNumber") + " " + e.getMessage());
			
		}
	}
	
	public void generateEndorsement(Context ctx, OutputStream out, String baseUrl, ServletContextURIResolver uriResolver) throws Exception {
		
		String foFile = "";		
		uriResolver=(ServletContextURIResolver)ctx.get(HtmlConstants.DOCUMENTURIRESOLVER);
		try{	
			if("3".equals(ctx.get("CompanyKey").toString()))
				foFile = SystemProperties.getInstance().getString("html.basedir") + "ISMIE//Endorsement//WrapEndorsementAcc.xsl";
			else	
			foFile = SystemProperties.getInstance().getString("html.basedir") + "foxsl2017//WrapEndorsementAcc.xsl";
            //ISMIE\Endorsement\WrapEndorsementAcc.xsl
			logger.debug("generateForm--------"+foFile);	
			//populateEndorsement(ctx, listFormID);
			//new XML2PDF().convertXMLtoStream(foFile, new StringBuffer(generateDataXml(ctx)), listFormID, out, baseUrl, uriResolver, ctx);
			/*fetchDataForXmlEndorsement(ctx);
			new XML2PDF().convertPOToPDF(foFile, new StringBuffer(generateDataXml(ctx)), out, baseUrl, uriResolver);	
			fetchDataForXmlEndorsement(ctx);*/
			new XML2PDF().convertPOToPDF(foFile, new StringBuffer(generateDataXml(ctx)), out, baseUrl, uriResolver);
		}
		catch(Exception e){
			logger.error("Problem in generating endorsement for Policy Number : " +ctx.get("PolicyNumber") + " " + e.getMessage());
			throw e;
		}
	}
	
	public void downloadPolicyPdf(Context ctx, HttpServletRequest req, HttpServletResponse response) throws Exception {
		
		String file;
		
		response.setContentType("text/plain");
		 
		String outFile = SystemProperties.getInstance().getString("html.basedir") + "data//Policy Form_" + ctx.get("QuoteNumber").toString() + ".pdf";		
		
      
    	   response.setHeader("Content-disposition","attachment; filename=Policy Form_" + ctx.get("QuoteNumber").toString() + ".pdf"); // Used to name the download file and its format
           file = outFile;
   		  
      
        
        File my_file = new File(file); // We are downloading .txt file, in the format of doc with name check - check.doc

        
        OutputStream out = response.getOutputStream();
        FileInputStream in = new FileInputStream(my_file);
        byte[] buffer = new byte[4096];
        int length;
        while ((length = in.read(buffer)) > 0){
           out.write(buffer, 0, length);
        }
        in.close();
        out.flush();
	}
	
	public void setEndorsementForNonIsmiePolicy(Context ctx,Map newPC,Map preExistPC)
	{	
		List <String>listFormID = new ArrayList <String> () ;
		try
		{
			DecimalFormat  formatter = new DecimalFormat("#0.00");   
			NumberFormat numberFormat =
				    NumberFormat.getCurrencyInstance(Locale.US);
			// To Check Changes in Limit or Deductible 
			if(newPC!=null && preExistPC!=null)
			{
				

				// To Check Changes in Limit or Deductible 
				if(newPC.get("LimitSequence") != null && preExistPC.get("LimitSequence") != null
						&& !(newPC.get("LimitSequence").toString().equals(preExistPC.get("LimitSequence").toString()))	 						
				  ){
					//ctx.put("LPL_109_ENDORSEMENT","Y");
					ctx.put("APL_107_ENDORSEMENT","Y");
					listFormID.add("107");
				} else if(newPC.get("DeductibleSequence") != null && preExistPC.get("DeductibleSequence") != null
						&& !(newPC.get("DeductibleSequence").toString().equals(preExistPC.get("DeductibleSequence").toString())) 						
				  ){
					//ctx.put("LPL_109_ENDORSEMENT","Y");
					ctx.put("APL_107_ENDORSEMENT","Y");
					listFormID.add("107");
				}
				double premiumValue=ctx.get("PremiumValue")!=null?Double.parseDouble(AccountUtils.removeAmountFormat(ctx.get("PremiumValue")).toString()):0.0;
				ctx.put("PremiumValue", AccountUtils.premiumAmountFormator(premiumValue));
				if(ctx.get("TransactionTypeKey") != null && AccountantConstants.LIMIT_ENDORSEMENT.equals(ctx.get("TransactionTypeKey").toString())){
					// For Defense Expense Inside -> Outside
					if(newPC.get("IsClaimExpensesType") != null && "Y".equals(newPC.get("IsClaimExpensesType").toString())
							&& (preExistPC.get("IsClaimExpensesType") == null  
							|| ((preExistPC.get("IsClaimExpensesType") != null && "N".equals(preExistPC.get("IsClaimExpensesType").toString()))))						
					  ){
						//ctx.put("LPL_102_ENDORSEMENT","Y");
						ctx.put("APL_104_ENDORSEMENT","Y");
						listFormID.add("104");
					}	
					// For Defense Expense Outside -> Inside
					if(newPC.get("IsClaimExpensesType") != null && ("N".equals(newPC.get("IsClaimExpensesType").toString()) || "".equals(newPC.get("IsClaimExpensesType").toString())	)					 
							&& (preExistPC.get("IsClaimExpensesType") != null && "Y".equals(preExistPC.get("IsClaimExpensesType").toString()))						
					  ){
						//ctx.put("LPL_136_102_ENDORSEMENT","Y");
						ctx.put("APL_109_ENDORSEMENT","Y");
						listFormID.add("109");
					}				
					// For Dollar Defense N -> Y
					/*if(newPC.get("IsDollarDefense") != null && "Y".equals(newPC.get("IsDollarDefense").toString())
							&& (preExistPC.get("IsDollarDefense") == null  
							|| ((preExistPC.get("IsDollarDefense") != null && "N".equals(preExistPC.get("IsDollarDefense").toString()))))					
					  ){
						ctx.put("LPL_103_ENDORSEMENT","Y");
						listFormID.add("103");
					}
					// For Dollar Defense Y -> N
					if((newPC.get("IsDollarDefense") == null || (newPC.get("IsDollarDefense") != null && ("N".equals(newPC.get("IsDollarDefense").toString()) || "".equals(newPC.get("IsDollarDefense").toString()))))
							&& (preExistPC.get("IsDollarDefense") != null && "Y".equals(preExistPC.get("IsDollarDefense").toString()))					
					  ){
						ctx.put("LPL_136_103_ENDORSEMENT","Y");
						listFormID.add("136");
					}*/
					ctx.put("PolicyEndorsementEffectiveDate", ctx.get("limitEndorsementEffectiveDate"));
				}
					
				if(ctx.get("TransactionTypeKey") != null && AccountantConstants.DEDUCTIBLE_ENDORSEMENT.equals(ctx.get("TransactionTypeKey").toString())){
					// For Defense Option Per Claim -> Aggregate
					if(newPC.get("IsClaimOptionType") != null && "Y".equals(newPC.get("IsClaimOptionType").toString())
							&& (preExistPC.get("IsClaimOptionType") == null  
							|| ((preExistPC.get("IsClaimOptionType") != null && "N".equals(preExistPC.get("IsClaimOptionType").toString()))))					
					  ){
						//ctx.put("LPL_104_ENDORSEMENT","Y");
						ctx.put("APL_105_ENDORSEMENT","Y");
						listFormID.add("105");
					}
					// For Defense Option Aggregate -> Per Claim
					if((newPC.get("IsClaimOptionType") == null || (newPC.get("IsClaimOptionType") != null && ("N".equals(newPC.get("IsClaimOptionType").toString()) || "".equals(newPC.get("IsClaimOptionType").toString()) )))
							&& (preExistPC.get("IsClaimOptionType") != null && "Y".equals(preExistPC.get("IsClaimOptionType").toString()))					
					  ){
						//ctx.put("LPL_136_104_ENDORSEMENT","Y");
						ctx.put("APL_109_ENDORSEMENT","Y");
						listFormID.add("109");
					}
					
					ctx.put("PolicyEndorsementEffectiveDate", ctx.get("deductibleEndorsementEffectiveDate"));
				}
				
			} 
			//End Check  difference in Limit, Deductible, IsClaimExpensesType, IsClaimOptionType, IsDollarDefense

			if(ctx.get("TransactionTypeKey") != null && AccountantConstants.NAMEADDRESS_ENDORSEMENT.equals(ctx.get("TransactionTypeKey").toString())){
				// For Defense Option Per Claim -> Aggregate
					
					//ctx.put("LPL_109_ENDORSEMENT","Y");
					ctx.put("APL_107_ENDORSEMENT","Y");
					listFormID.add("107");
				
				//ctx.put("PremiumCharged","S");
					
					ctx.put("PolicyEndorsementEffectiveDate", ctx.get("nameAddressEndorsementDate"));
			}
			if(ctx.get("TransactionTypeKey") != null && AccountantConstants.PRIORACTDATE_ENDORSEMENT.equals(ctx.get("TransactionTypeKey").toString())){
				// For Defense Option Per Claim -> Aggregate
					
				String isPremiumCalculated=ctx.get("premiumCalulated")!=null && !ctx.get("premiumCalulated").equals(HtmlConstants.EMPTY) ?ctx.get("premiumCalulated").toString():"N";
					if("N".equals(isPremiumCalculated))
						{
						ctx.remove("PremiumCharged");
						ctx.remove("PremiumValue");
						ctx.remove("ProrataPremium");
						}
					//ctx.put("LPL_109_ENDORSEMENT","Y");
					//listFormID.add("109");
					ctx.put("APL_107_ENDORSEMENT","Y");
					listFormID.add("107");
				//ctx.put("PremiumCharged","S");
					
					ctx.put("PolicyEndorsementEffectiveDate", ctx.get("priorActEndorsementDate"));
			}
			
			if(ctx.get("TransactionTypeKey") != null && AccountantConstants.POLICYEXTENSION_ENDORSEMENT.equals(ctx.get("TransactionTypeKey").toString())){
				// For Defense Option Per Claim -> Aggregate
				
					//ctx.put("LPL_109_ENDORSEMENT","Y");
					//listFormID.add("109");
				ctx.put("APL_107_ENDORSEMENT","Y");
				listFormID.add("107");
				//ctx.put("PremiumCharged","S");
				ctx.put("PolicyEndorsementEffectiveDate", ctx.get("policyExtendEndorsementEffectiveDate"));
			}
			if(ctx.get("TransactionTypeKey") != null && AccountantConstants.CANCELLATION_ENDORSEMENT.equals(ctx.get("TransactionTypeKey").toString())){
				ctx.put("APL_114_ENDORSEMENT","Y");
				listFormID.add("114");
				ctx.put("PolicyEndorsementEffectiveDate", ctx.get("CancellationEffectiveDate"));
			}
			if(ctx.get("TransactionTypeKey") != null && AccountantConstants.REINSTATEMENT_ENDORSEMENT.equals(ctx.get("TransactionTypeKey").toString())){
				ctx.put("APL_131_ENDORSEMENT","Y");
				listFormID.add("131");
				ctx.put("PolicyEndorsementEffectiveDate", ctx.get("ReinstatementEffectiveDate"));
			}
			if(ctx.get("TransactionTypeKey") != null && AccountantConstants.ERP_ENDORSEMENT.equals(ctx.get("TransactionTypeKey").toString())){
				ctx.put("PolicyEndorsementEffectiveDate", ctx.get("endorsementEffectiveDate"));
				ctx.put("TransactionEffectiveDate", ctx.get("endorsementEffectiveDate"));
				
				String isAccountantRetiring=ctx.get("isAccountantRetiring")!=null && !ctx.get("isAccountantRetiring").equals(HtmlConstants.EMPTY)?ctx.get("isAccountantRetiring").toString():"A";
				String isInsuredWithUsFromPast=ctx.get("isInsuredWithUsFromPast")!=null && !ctx.get("isInsuredWithUsFromPast").equals(HtmlConstants.EMPTY)?ctx.get("isInsuredWithUsFromPast").toString():"A";
				String isDeathDisabilityERP=ctx.get("isDeathDisabilityERP")!=null && !ctx.get("isDeathDisabilityERP").equals(HtmlConstants.EMPTY)?ctx.get("isDeathDisabilityERP").toString():"A";
				String isFirmERP=ctx.get("isFirmERP")!=null && !ctx.get("isFirmERP").equals(HtmlConstants.EMPTY)?ctx.get("isFirmERP").toString():"A";
				String isSoloAccountant=ctx.get("isSoloAccountant")!=null && !ctx.get("isSoloAccountant").equals(HtmlConstants.EMPTY)?ctx.get("isSoloAccountant").toString():"NA";
				
				if("Y".equals(isAccountantRetiring) )
				{
					
					if("N".equals(isSoloAccountant) )
					{
				
					//ctx.put("LPL_105_ENDORSEMENT","Y");
					//listFormID.add("105");
					ctx.put("APL_146_ENDORSEMENT","Y");
					listFormID.add("146");
					ctx.put("AccountantName",ctx.get("nameOfAccountant"));
					ctx.put("CeasedDate",ctx.get("ceasedDate"));
					ctx.put("isThereAdditionalPremium", "N");
					}
					if("Y".equals(isSoloAccountant) && "Y".equals(isInsuredWithUsFromPast))
						{
					
						//ctx.put("LPL_105_ENDORSEMENT","Y");
						//listFormID.add("105");
						ctx.put("APL_146_ENDORSEMENT","Y");
						listFormID.add("146");
						ctx.put("AccountantName",ctx.get("nameOfAccountant"));
						ctx.put("CeasedDate",ctx.get("ceasedDate"));
						ctx.put("isThereAdditionalPremium", "Y");
						}
					
					
						if("Y".equals(isSoloAccountant) && "N".equals(isInsuredWithUsFromPast))
						{
					
						//ctx.put("LPL_105_ENDORSEMENT","Y");
						//listFormID.add("105");
						ctx.put("APL_146_ENDORSEMENT","Y");
						listFormID.add("146");
						ctx.put("AccountantName",ctx.get("nameOfAccountant"));
						ctx.put("CeasedDate",ctx.get("ceasedDate"));
						ctx.put("isThereAdditionalPremium", "Y");
						}
					
				}
				if("Y".equals(isDeathDisabilityERP) )
				{
					
				//	ctx.put("LPL_105_ENDORSEMENT","Y");
					ctx.put("APL_147_ENDORSEMENT","Y");
					listFormID.add("147");
					
					double erpTax=ctx.get("ERPTax")!=null?Double.parseDouble(ctx.get("ERPTax").toString()):0;
					if(erpTax>0)
						ctx.put("isThereStateTax", "Y");
					else
						ctx.put("isThereStateTax", "N");
					
					ctx.put("AccountantName",ctx.get("deathDisabilityFirmOrAccountantName"));
					ctx.put("CeasedDate",ctx.get("deathDiablityCeasedDate"));
					ctx.put("isThereAdditionalPremium", "N");
				}
				/*if("N".equals(isAttorneyRetiring) && "N".equals(isDeathDisabilityERP) )*/
				if("Y".equals(isFirmERP))
				{
					
					//ctx.put("LPL_123_ENDORSEMENT","Y");
					ctx.put("APL_125_ENDORSEMENT","Y");
					
					listFormID.add("125");
					int erpTax=ctx.get("erpTax")!=null?Integer.parseInt(ctx.get("ERPTax").toString()):0;
					if(erpTax>0)
						ctx.put("isThereStateTax", "Y");
					else
						ctx.put("isThereStateTax", "N");
					if("Y".equals(isFirmERP))
					{	
						int erpYears=ctx.get("yearsOfERP")!=null && !ctx.get("yearsOfERP").equals(HtmlConstants.EMPTY)?Integer.parseInt(ctx.get("yearsOfERP").toString()):0;
						new SetParametersForStoredProcedures().setParametersInContext(ctx, "PolicyKey,StateCode,yearsOfERP,endorsementEffectiveDate");
						List data= (List) SqlResources.getSqlMapProcessor(ctx).select("PolicyTransaction.GetERPExpirationDate_p",ctx);
						ctx.putAll((Map) data.get(0));
						ctx.put("isUnlimitedYearsERP", "N");
						
					}	
					//RuleUtils.executeRule(ctx,"AccountantRule.setErpExpirationDate");
					}
				
			}
			ctx.put("listFormID", listFormID);

		}
		
		
		catch(Exception e)
		{e.printStackTrace();}
	}
	public void setEndorsementForIsmiePolicy(Context ctx,Map newPC,Map preExistPC)
	{	
		List <String>listFormID = new ArrayList <String> () ;
		try
		{
			DecimalFormat  formatter = new DecimalFormat("#0.00");   
			NumberFormat numberFormat =NumberFormat.getCurrencyInstance(Locale.US);
			// To Check Changes in Limit or Deductible 
			if(newPC!=null && preExistPC!=null)
			{
				

				// To Check Changes in Limit or Deductible 
				if(newPC.get("LimitSequence") != null && preExistPC.get("LimitSequence") != null
						&& !(newPC.get("LimitSequence").toString().equals(preExistPC.get("LimitSequence").toString()))	 						
				  ){
					//ctx.put("LPL_109_ENDORSEMENT","Y");
					ctx.put("AAA_F004_ENDORSEMENT","Y");
					listFormID.add("F004");
				} else if(newPC.get("DeductibleSequence") != null && preExistPC.get("DeductibleSequence") != null
						&& !(newPC.get("DeductibleSequence").toString().equals(preExistPC.get("DeductibleSequence").toString())) 						
				  ){
					//ctx.put("LPL_109_ENDORSEMENT","Y");
					ctx.put("AAA_F004_ENDORSEMENT","Y");
					listFormID.add("F004");
				}
				double premiumValue=ctx.get("PremiumValue")!=null?Double.parseDouble(AccountUtils.removeAmountFormat(ctx.get("PremiumValue")).toString()):0.0;
				ctx.put("PremiumValue", AccountUtils.premiumAmountFormator(premiumValue));
				if(ctx.get("TransactionTypeKey") != null && AccountantConstants.LIMIT_ENDORSEMENT.equals(ctx.get("TransactionTypeKey").toString())){
					// For Defense Expense Inside -> Outside
//					if(newPC.get("IsClaimExpensesType") != null && "Y".equals(newPC.get("IsClaimExpensesType").toString())
//							&& (preExistPC.get("IsClaimExpensesType") == null  
//							|| ((preExistPC.get("IsClaimExpensesType") != null && "N".equals(preExistPC.get("IsClaimExpensesType").toString()))))						
//					  ){
//						//ctx.put("LPL_102_ENDORSEMENT","Y");
//						ctx.put("APL_104_ENDORSEMENT","Y");
//						listFormID.add("104");
//					}	
//					// For Defense Expense Outside -> Inside
					if(newPC.get("IsClaimExpensesType") != null && ("N".equals(newPC.get("IsClaimExpensesType").toString()) || "".equals(newPC.get("IsClaimExpensesType").toString())	)					 
							&& (preExistPC.get("IsClaimExpensesType") != null && "Y".equals(preExistPC.get("IsClaimExpensesType").toString()))						
					  ){
						//ctx.put("LPL_136_102_ENDORSEMENT","Y");
						ctx.put("AAA_F009_ENDORSEMENT","Y");
						listFormID.add("F009");
					}				
					
					ctx.put("PolicyEndorsementEffectiveDate", ctx.get("limitEndorsementEffectiveDate"));
				}
					
				if(ctx.get("TransactionTypeKey") != null && AccountantConstants.DEDUCTIBLE_ENDORSEMENT.equals(ctx.get("TransactionTypeKey").toString())){
					// For Defense Option Per Claim -> Aggregate
//					if(newPC.get("IsClaimOptionType") != null && "Y".equals(newPC.get("IsClaimOptionType").toString())
//							&& (preExistPC.get("IsClaimOptionType") == null  
//							|| ((preExistPC.get("IsClaimOptionType") != null && "N".equals(preExistPC.get("IsClaimOptionType").toString()))))					
//					  ){
//						//ctx.put("LPL_104_ENDORSEMENT","Y");
//						ctx.put("APL_105_ENDORSEMENT","Y");
//						listFormID.add("105");
//					}
					// For Defense Option Aggregate -> Per Claim
					if((newPC.get("IsClaimOptionType") == null || (newPC.get("IsClaimOptionType") != null && ("N".equals(newPC.get("IsClaimOptionType").toString()) || "".equals(newPC.get("IsClaimOptionType").toString()) )))
							&& (preExistPC.get("IsClaimOptionType") != null && "Y".equals(preExistPC.get("IsClaimOptionType").toString()))					
					  ){
						//ctx.put("LPL_136_104_ENDORSEMENT","Y");
						ctx.put("AAA_F009_ENDORSEMENT","Y");
						listFormID.add("F009");
					}
					
					ctx.put("PolicyEndorsementEffectiveDate", ctx.get("deductibleEndorsementEffectiveDate"));
				}
				
			} 
			//End Check  difference in Limit, Deductible, IsClaimExpensesType, IsClaimOptionType, IsDollarDefense

			if(ctx.get("TransactionTypeKey") != null && AccountantConstants.NAMEADDRESS_ENDORSEMENT.equals(ctx.get("TransactionTypeKey").toString())){
				// For Defense Option Per Claim -> Aggregate
					
					//ctx.put("LPL_109_ENDORSEMENT","Y");
				ctx.put("AAA_F004_ENDORSEMENT","Y");
				listFormID.add("F004");
				
				//ctx.put("PremiumCharged","S");
					
					ctx.put("PolicyEndorsementEffectiveDate", ctx.get("nameAddressEndorsementDate"));
			}
			if(ctx.get("TransactionTypeKey") != null && AccountantConstants.PRIORACTDATE_ENDORSEMENT.equals(ctx.get("TransactionTypeKey").toString())){
				// For Defense Option Per Claim -> Aggregate
					
				String isPremiumCalculated=ctx.get("premiumCalulated")!=null && !ctx.get("premiumCalulated").equals(HtmlConstants.EMPTY) ?ctx.get("premiumCalulated").toString():"N";
					if("N".equals(isPremiumCalculated))
						{
						ctx.remove("PremiumCharged");
						ctx.remove("PremiumValue");
						ctx.remove("ProrataPremium");
						}
					//ctx.put("LPL_109_ENDORSEMENT","Y");
					//listFormID.add("109");
					ctx.put("AAA_F004_ENDORSEMENT","Y");
					listFormID.add("F004");
				//ctx.put("PremiumCharged","S");
					Map objPriorActDetail = (Map) SqlResources.getSqlMapProcessor(ctx).findByKey("SqlStmts.UserStatementpdfquotelettergetProfessionalLiabilityInsDetail", ctx);
					objPriorActDetail.put("PriorActDatePross", ctx.get("priorActEndorsementPriorActDate"));
					
					ctx.put("professionalliabilityinsdetail_freeform_01",objPriorActDetail);
					ctx.put("PolicyEndorsementEffectiveDate", ctx.get("priorActEndorsementDate"));
					ctx.put("IsFirmHaveLawyersLiabilityInsurance", ctx.get("IsFirmHaveLawyersLiabilityInsurance")!=null?ctx.get("IsFirmHaveLawyersLiabilityInsurance"):"N");
			}
			
			if(ctx.get("TransactionTypeKey") != null && AccountantConstants.POLICYEXTENSION_ENDORSEMENT.equals(ctx.get("TransactionTypeKey").toString())){
				// For Defense Option Per Claim -> Aggregate
				
					//ctx.put("LPL_109_ENDORSEMENT","Y");
					//listFormID.add("109");
				ctx.put("AAA_F004_ENDORSEMENT","Y");
				listFormID.add("F004");
				//ctx.put("PremiumCharged","S");
				ctx.put("PolicyEndorsementEffectiveDate", ctx.get("policyExtendEndorsementEffectiveDate"));
			}
			if(ctx.get("TransactionTypeKey") != null && AccountantConstants.CANCELLATION_ENDORSEMENT.equals(ctx.get("TransactionTypeKey").toString())){
				ctx.put("AAA_S003_ENDORSEMENT","Y");
				listFormID.add("S003");
				ctx.put("PolicyEndorsementEffectiveDate", ctx.get("CancellationEffectiveDate"));
			}
			if(ctx.get("TransactionTypeKey") != null && AccountantConstants.REINSTATEMENT_ENDORSEMENT.equals(ctx.get("TransactionTypeKey").toString())){
				ctx.put("AAA_S005_ENDORSEMENT","Y");
				listFormID.add("S005");
				ctx.put("PolicyEndorsementEffectiveDate", ctx.get("ReinstatementEffectiveDate"));
			}
			if(ctx.get("TransactionTypeKey") != null && AccountantConstants.ERP_ENDORSEMENT.equals(ctx.get("TransactionTypeKey").toString())){
				ctx.put("PolicyEndorsementEffectiveDate", ctx.get("endorsementEffectiveDate"));
				ctx.put("TransactionEffectiveDate", ctx.get("endorsementEffectiveDate"));
				
				String isAccountantRetiring=ctx.get("isAccountantRetiring")!=null && !ctx.get("isAccountantRetiring").equals(HtmlConstants.EMPTY)?ctx.get("isAccountantRetiring").toString():"A";
				String isInsuredWithUsFromPast=ctx.get("isInsuredWithUsFromPast")!=null && !ctx.get("isInsuredWithUsFromPast").equals(HtmlConstants.EMPTY)?ctx.get("isInsuredWithUsFromPast").toString():"A";
				String isDeathDisabilityERP=ctx.get("isDeathDisabilityERP")!=null && !ctx.get("isDeathDisabilityERP").equals(HtmlConstants.EMPTY)?ctx.get("isDeathDisabilityERP").toString():"A";
				String isFirmERP=ctx.get("isFirmERP")!=null && !ctx.get("isFirmERP").equals(HtmlConstants.EMPTY)?ctx.get("isFirmERP").toString():"A";
				String isSoloAccountant=ctx.get("isSoloAccountant")!=null && !ctx.get("isSoloAccountant").equals(HtmlConstants.EMPTY)?ctx.get("isSoloAccountant").toString():"NA";
				
				if("Y".equals(isAccountantRetiring) )
				{
					
					if("N".equals(isSoloAccountant) )
					{
				
					//ctx.put("LPL_105_ENDORSEMENT","Y");
					//listFormID.add("105");
					ctx.put("AAA_F020_ENDORSEMENT","Y");
					listFormID.add("F020");
					ctx.put("AccountantName",ctx.get("nameOfAccountant"));
					ctx.put("CeasedDate",ctx.get("ceasedDate"));
					ctx.put("isThereAdditionalPremium", "N");
					}
					if("Y".equals(isSoloAccountant) && "Y".equals(isInsuredWithUsFromPast))
						{
					
						//ctx.put("LPL_105_ENDORSEMENT","Y");
						//listFormID.add("105");
						ctx.put("AAA_F020_ENDORSEMENT","Y");
						listFormID.add("F020");
						ctx.put("AccountantName",ctx.get("nameOfAccountant"));
						ctx.put("CeasedDate",ctx.get("ceasedDate"));
						ctx.put("isThereAdditionalPremium", "Y");
						}
					
					
						if("Y".equals(isSoloAccountant) && "N".equals(isInsuredWithUsFromPast))
						{
					
						//ctx.put("LPL_105_ENDORSEMENT","Y");
						//listFormID.add("105");
						ctx.put("AAA_F020_ENDORSEMENT","Y");
						listFormID.add("F020");
						ctx.put("AccountantName",ctx.get("nameOfAccountant"));
						ctx.put("CeasedDate",ctx.get("ceasedDate"));
						ctx.put("isThereAdditionalPremium", "Y");
						}
					
				}
				if("Y".equals(isDeathDisabilityERP) )
				{
					
				//	ctx.put("LPL_105_ENDORSEMENT","Y");
					ctx.put("AAA_F021_ENDORSEMENT","Y");
					listFormID.add("F021");
										
					ctx.put("AccountantName",ctx.get("deathDisabilityFirmOrAccountantName"));
					ctx.put("CeasedDate",ctx.get("deathDiablityCeasedDate"));
					ctx.put("isThereAdditionalPremium", "N");
				}
				/*if("N".equals(isAttorneyRetiring) && "N".equals(isDeathDisabilityERP) )*/
				if("Y".equals(isFirmERP))
				{
					
					//ctx.put("LPL_123_ENDORSEMENT","Y");
					ctx.put("AAA_F013_ENDORSEMENT","Y");
					listFormID.add("F013");
					double erpTax=ctx.get("ERPTax")!=null?Double.parseDouble(ctx.get("ERPTax").toString()):0;
					if(erpTax>0)
						ctx.put("isThereStateTax", "Y");
					else
						ctx.put("isThereStateTax", "N");
					if("Y".equals(isFirmERP))
					{	
						int erpYears=ctx.get("yearsOfERP")!=null && !ctx.get("yearsOfERP").equals(HtmlConstants.EMPTY)?Integer.parseInt(ctx.get("yearsOfERP").toString()):0;
						new SetParametersForStoredProcedures().setParametersInContext(ctx, "PolicyKey,StateCode,yearsOfERP,endorsementEffectiveDate");
						List data= (List) SqlResources.getSqlMapProcessor(ctx).select("PolicyTransaction.GetERPExpirationDate_p",ctx);
						ctx.putAll((Map) data.get(0));
						ctx.put("isUnlimitedYearsERP", "N");
						
					}	
					//RuleUtils.executeRule(ctx,"AccountantRule.setErpExpirationDate");
					}
				
			}
			ctx.put("listFormID", listFormID);

			
		
		}
		
		catch(Exception e)
		{e.printStackTrace();}
	}
	
	
}
