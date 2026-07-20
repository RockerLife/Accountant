package com.fop;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.fop.servlet.ServletContextURIResolver;
import org.jdom.Element;

import com.manage.managemetadata.functions.commonfunctions.RuleUtils;
import com.ormapping.ibatis.SqlResources;
import com.pdf.PDFConstants;
import com.pdf.PDFGenerator;
import com.processor.RequestProcessor;
import com.userbo.AccountUtils;
import com.userbo.AccountantValidationUtils;
import com.userbo.DocumentManagment;
import com.util.Context;
import com.util.DocumentGenerationBO;
import com.util.HtmlConstants;
import com.util.IOUtils;
import com.util.InetLogger;
import com.util.SystemProperties;
import com.util.XMLUtils;

public class DownloadFOP {

	private static InetLogger logger = InetLogger
			.getInetLogger(DownloadFOP.class);

	public void makePdf(Context ctx, HttpServletRequest req,
			HttpServletResponse res, ServletContextURIResolver uriResolver,
			ServletContext servletContext) {

		String xmlFile = "";
		String foFile = "";

		try {

			xmlFile = SystemProperties.getInstance().getString("html.basedir")
					+ "data//quoteletter.xml";
			// foFile = SystemProperties.getInstance().getString("html.basedir")
			// + "foxsl//quoteletter.xsl";
			foFile = SystemProperties.getInstance().getString("html.basedir")
					+ "foxsl//wrapquote.xsl";

			fetchDataForXml(ctx);
			populateDataXml(ctx, xmlFile);
			new XML2PDF().convertPOToPDF(foFile, xmlFile, ctx, req, res,
					(ServletContextURIResolver)ctx.get(HtmlConstants.DOCUMENTURIRESOLVER), servletContext);

		} catch (Exception e) {
			logger
					.error("Problem in generating quote letter for Quote Number : "
							+ ctx.get("QuoteNumber")+ " " + e.getMessage());
		}
	}
	
	public void makeRenewPdf(Context ctx, HttpServletRequest req,
			HttpServletResponse res, ServletContextURIResolver uriResolver,
			ServletContext servletContext) {

		String xmlFile = "";
		String foFile = "";

		try {

			xmlFile = SystemProperties.getInstance().getString("html.basedir")
					+ "data//quoteletter.xml";
			
			foFile = SystemProperties.getInstance().getString("html.basedir")
					+ "foxsl//renewapplication.xsl";

			fetchDataForRenewXml(ctx);
			populateDataXml(ctx, xmlFile);
			new XML2PDF().convertPOToPDF(foFile, xmlFile, ctx, req, res,
					(ServletContextURIResolver)ctx.get(HtmlConstants.DOCUMENTURIRESOLVER), servletContext);

		} catch (Exception e) {
			logger
					.error("Problem in generating quote letter for Quote Number : "
							+ ctx.get("QuoteNumber")+ " " + e.getMessage());
		}
	}

	public void makePdf(Context ctx, OutputStream out, String baseUrl,
			ServletContextURIResolver uriResolver) {

		String xmlFile = "";
		String foFile = "";
		
		try {
			
			xmlFile = SystemProperties.getInstance().getString("html.basedir")
					+ "data//quoteletter.xml";
			if("3".equals(ctx.get("CompanyKey").toString()))
				foFile = SystemProperties.getInstance().getString("html.basedir")+ "ISMIE//QuoteLetter//quoteletter.xsl";
			else
				foFile = SystemProperties.getInstance().getString("html.basedir")+ "foxsl//wrapquote.xsl";

			// foFile = SystemProperties.getInstance().getString("html.basedir")
			// + "foxsl//quoteletter.xsl";

			fetchDataForXml(ctx);
			populateDataXml(ctx, xmlFile);
			baseUrl = "file:///" + ((ServletContext)ctx.get(HtmlConstants.DOCUMENTSERVLETCONTEXT)).getRealPath("/");
			new XML2PDF().convertPOToPDF(foFile, xmlFile, out, baseUrl,
					(ServletContextURIResolver)ctx.get(HtmlConstants.DOCUMENTURIRESOLVER),ctx);
		} catch (Exception e) {
			logger
					.error("Problem in generating quote letter for Quote Number : "
							+ ctx.get("QuoteNumber")+ " " + e.getMessage());
		}
	}
	
	public void makeRenewPdf(Context ctx, OutputStream out, String baseUrl,
			ServletContextURIResolver uriResolver,ServletContext servletContext) {

		String xmlFile = "";
		String foFile = "";

		try {

			xmlFile = SystemProperties.getInstance().getString("html.basedir")
					+ "data//quoteletter.xml";
			Object objnew = RuleUtils.executeRule(ctx, "AccountantRule.newAppFlow");
			boolean flagnew = false;			
			if(objnew instanceof Boolean)
				flagnew = (Boolean) objnew;
			
			if(flagnew)
			{
				//New Flow
				if(!"3".equals(ctx.get("CompanyKey").toString()))
				{
					if(ctx.get("StateCode").equals("NY"))
						foFile = SystemProperties.getInstance().getString("html.basedir")+ "foxsl//renewalapplicationnewappNYFTZ.xsl";
						
					else if(ctx.get("StateCode").equals("ME"))
						foFile = SystemProperties.getInstance().getString("html.basedir")+ "foxsl//MERenewalApplication.xsl";
						
					else if(ctx.get("StateCode").equals("NC"))
						foFile = SystemProperties.getInstance().getString("html.basedir")+ "foxsl//NCRenewalApplication.xsl";
					else
							foFile = SystemProperties.getInstance().getString("html.basedir")+ "foxsl//renewalapplicationnewapp.xsl";
					
				}
				if("3".equals(ctx.get("CompanyKey").toString()))
				{
					if(ctx.get("StateCode").equals("NY"))
						foFile = SystemProperties.getInstance().getString("html.basedir")+ "ISMIE//ApplicationPDF//RenewBusiness//renewalapplicationnewappNYFTZ.xsl";
						
					else if(ctx.get("StateCode").equals("ME"))
						foFile = SystemProperties.getInstance().getString("html.basedir")+ "ISMIE//ApplicationPDF//RenewBusiness//MERenewalApplication.xsl";
						
					else if(ctx.get("StateCode").equals("NC"))
						foFile = SystemProperties.getInstance().getString("html.basedir")+ "ISMIE//ApplicationPDF//RenewBusiness//NCRenewalApplication.xsl";
					else
						foFile = SystemProperties.getInstance().getString("html.basedir")+ "ISMIE//ApplicationPDF//RenewBusiness//renewalapplicationnewapp.xsl";
					
				}
				new DocumentGenerationBO().fetchDataForRenewApplicationPDF(ctx);
			}
			else{
				// Old Flow
			foFile = SystemProperties.getInstance().getString("html.basedir")
					+ "foxsl//renewapplication.xsl";
			fetchDataForRenewXml(ctx);
			}
			
			/*foFile = SystemProperties.getInstance().getString("html.basedir")
					+ "foxsl//renewapplication.xsl";

			// foFile = SystemProperties.getInstance().getString("html.basedir")
			// + "foxsl//quoteletter.xsl";

			fetchDataForRenewXml(ctx);*/
			populateDataXml(ctx, xmlFile);
			new XML2PDF().convertPOToPDF(foFile, xmlFile, out,(ServletContextURIResolver)ctx.get(HtmlConstants.DOCUMENTURIRESOLVER), servletContext,ctx);
		} catch (Exception e) {
			logger
					.error("Problem in generating quote letter for Quote Number : "
							+ ctx.get("QuoteNumber")+ " " + e.getMessage());
		}
	}
	
	public void makePdfNewFlow(Context ctx, OutputStream out, String baseUrl,
			ServletContextURIResolver uriResolver,ServletContext servletContext) {
		String xmlFile = "";
		String foFile = "";

		try {

			xmlFile = SystemProperties.getInstance().getString("html.basedir")
					+ "data//quoteletter.xml";
			
			if(!"3".equals(ctx.get("CompanyKey").toString()))
			{
				if(ctx.get("StateCode").equals("NY")){
				foFile = SystemProperties.getInstance().getString("html.basedir")
						+ "foxsl//NewApplicationPDFNYFTZ.xsl";
				}
				else if(ctx.get("StateCode").equals("MD")){
					foFile = SystemProperties.getInstance().getString("html.basedir")
							+ "foxsl//MDNewBusinessApplication.xsl";
					}
				
				else if(ctx.get("StateCode").equals("ME")){
					foFile = SystemProperties.getInstance().getString("html.basedir")
							+ "foxsl//MENewBusinessApplication.xsl";
					}
				else if(ctx.get("StateCode").equals("NC")){
					foFile = SystemProperties.getInstance().getString("html.basedir")
							+ "foxsl//NCNewBusinessApplication.xsl";
					}
				else{
				foFile = SystemProperties.getInstance().getString("html.basedir")
						+ "foxsl//NewApplicationPDF.xsl";
				}	
			}
			if("3".equals(ctx.get("CompanyKey").toString()))
			{
				if(ctx.get("StateCode").equals("NY"))
					foFile = SystemProperties.getInstance().getString("html.basedir")+ "ISMIE//ApplicationPDF//NewBusiness//NewApplicationPDFNYFTZ.xsl";
					
				else if(ctx.get("StateCode").equals("MD"))
					foFile = SystemProperties.getInstance().getString("html.basedir")+ "ISMIE//ApplicationPDF//NewBusiness//MDNewBusinessApplication.xsl";
									
				else if(ctx.get("StateCode").equals("ME"))
					foFile = SystemProperties.getInstance().getString("html.basedir")+ "ISMIE//ApplicationPDF//NewBusiness//MENewBusinessApplication.xsl";
					
				else if(ctx.get("StateCode").equals("NC"))
					foFile = SystemProperties.getInstance().getString("html.basedir")+ "ISMIE//ApplicationPDF//NewBusiness//NCNewBusinessApplication.xsl";
					
				else
				foFile = SystemProperties.getInstance().getString("html.basedir")+ "ISMIE//ApplicationPDF//NewBusiness//NewApplicationPDF.xsl";
				
				
			}				

			new DocumentGenerationBO().fetchDataForNewApplicationPDF(ctx);			
			populateDataXml(ctx, xmlFile);
			new XML2PDF().convertPOToPDF(foFile, xmlFile, out,(ServletContextURIResolver)ctx.get(HtmlConstants.DOCUMENTURIRESOLVER), servletContext,ctx);
		
		} catch (Exception e) {
			logger
					.error("Problem in generating quote letter for Quote Number : "
							+ ctx.get("QuoteNumber")+ " " + e.getMessage());
		}
	}
	
	public void fetchDataForRenewXml(Context ctx) throws Exception{
		
		
		Object objPolicy = SqlResources.getSqlMapProcessor(ctx).findByKey("Policy.findByKey", ctx);
		
		if(objPolicy != null && objPolicy instanceof Map){
			Map objMap = (Map)objPolicy;
			
			ctx.put("AccountID",objMap.get("AccountID"));
			
		}
		
		
		Object obj = SqlResources
		.getSqlMapProcessor(ctx)
		.select(
				"SqlStmts.UserStatementrenewapplicationgetAllPolicyOfAnAccount",
				ctx);
		
		if(obj != null && obj instanceof List){
			Context previousContext = new Context();

			List policyList = (List) obj;

			if (policyList != null && policyList.size() > 0) {

				Map policyMap = (Map) policyList.get(policyList.size() - 2);

				previousContext.putAll(ctx);
				previousContext.put("PolicyKey", policyMap.get("PolicyKey"));

				Object objMap = SqlResources.getSqlMapProcessor(previousContext).findByKey("SqlStmts.UserStatementprocessquotegetPolicyKeys",
						previousContext);
				if (objMap != null && objMap instanceof Map) {

					Map keyMap = (Map) objMap;

					previousContext.put("VersionSequence", keyMap.get("VersionSequence") != null ? keyMap.get("VersionSequence") : null);

				}
			}

			List aopPreviousList = SqlResources.getSqlMapProcessor(previousContext).select("SqlStmts.UserStatementsaveAccountstmtspopulateAOPFields",previousContext);
			if(aopPreviousList != null && aopPreviousList.size() > 0){
				int total = 0;
				for(int i = 0 ; i < aopPreviousList.size() ; i++){
					Map map = (Map)aopPreviousList.get(i);
					if(map.get("PercentageValue") != null && !map.get("PercentageValue").equals("")){
						total = total + Integer.parseInt(map.get("PercentageValue").toString());
						ctx.put("AOPOld_PercentageValue_"+map.get("AOPKey"), map.get("PercentageValue").toString());
					}else{
						ctx.put("AOPOld_PercentageValue_"+map.get("AOPKey"), "0");
					}
					ctx.put("AOPOld_CommentDesc_"+map.get("AOPKey"), map.get("AOPCommentDesc"));
				}
				
				ctx.put("aopold_total", total);
			}

			List aoplist = SqlResources.getSqlMapProcessor(ctx).select("SqlStmts.UserStatementsaveAccountstmtspopulateAOPFields",ctx);
			if(aoplist != null && aoplist.size() > 0){
				int total = 0;
				for(int i = 0 ; i < aoplist.size() ; i++){
					Map map = (Map)aoplist.get(i);
					if(map.get("PercentageValue") != null && !map.get("PercentageValue").equals("")){
						total = total + Integer.parseInt(map.get("PercentageValue").toString());
						ctx.put("AOP_PercentageValue_"+map.get("AOPKey"), map.get("PercentageValue").toString());
					}else{
						ctx.put("AOP_PercentageValue_"+map.get("AOPKey"), "0");
					}
					ctx.put("AOP_CommentDesc_"+map.get("AOPKey"), map.get("AOPCommentDesc"));
				}
				
				ctx.put("aop_total", total);
			}
		}
		
		Object objAddress = SqlResources.getSqlMapProcessor(ctx).findByKey("SqlStmts.UserStatementpdfquotelettergetAddressdetails", ctx);
		ctx.put("address_freeform_01", objAddress);
		
		Object objPoilcy = SqlResources.getSqlMapProcessor(ctx).findByKey("SqlStmts.UserStatementpdfquotelettergetPriorPolicyInfo",	ctx);
		ctx.put("policy_freeform_01", objPoilcy);
		
		
		
		Object objFirm = SqlResources.getSqlMapProcessor(ctx).findByKey("Firm.findByKey",	ctx);
		ctx.put("firm_freeform_01", objFirm);
		
		Object listPersonnelAffiliation = SqlResources.getSqlMapProcessor(ctx).select("SqlStmts.BasicInfoviewmomfirmaffiliates", ctx);
		ctx.put(PDFConstants.BASICINFO_LIST_01, listPersonnelAffiliation);
		
		if(listPersonnelAffiliation != null && listPersonnelAffiliation instanceof List){
			int totalPersonnel = 0 ;
			List list = (List)listPersonnelAffiliation;
			
			for(int i = 0 ; i < list.size() ; i++){
				
				Map objPersonnel = (Map)list.get(i);
				if(objPersonnel.get("PersonnelTypeNumbering") != null && !"".equals(objPersonnel.get("PersonnelTypeNumbering").toString())){
					int personnelValue = AccountantValidationUtils.convertStringToInteger(objPersonnel.get("NumberOfPersonnel") != null ? objPersonnel.get("NumberOfPersonnel").toString() : "");
					totalPersonnel = totalPersonnel + personnelValue ;
				}
				
			}
			
			ctx.put("TotalPersonnel", totalPersonnel);
			
		}
		
		Object listGrossRevenueForFirm = SqlResources.getSqlMapProcessor(ctx)
		.select("SqlStmts.BasicInfoviewmomgrossrevenue", ctx);
		ctx.put(PDFConstants.BASICINFO_LIST_03, listGrossRevenueForFirm);
		
		if(listGrossRevenueForFirm != null && listGrossRevenueForFirm instanceof List){
			
			List grossList = (List)listGrossRevenueForFirm;
				
					
					Map o = (Map)grossList.get(0);
					
					String year = o.get("YearEndDate") != null ? o.get("YearEndDate").toString() :"";
					String amount = o.get("Amount") != null ? o.get("Amount").toString() :"0";
					amount = PDFGenerator.formatter.format(Long.parseLong(amount));
					amount = DownloadFOP.formateAmountString(amount);
					ctx.put("YearEndDate", year);
					
					ctx.put("Amount", amount);
		}
		
		
		
		
		Object listTrusteeOrEstateSupp = SqlResources.getSqlMapProcessor(ctx)
		.select("SqlStmts.generalviewListTrusteeOrEstateSupp", ctx);
		ctx.put(PDFConstants.GENERAL_LIST_01, listTrusteeOrEstateSupp);
		
		Object listOutsideInterestSupp = SqlResources.getSqlMapProcessor(ctx)
		.select("SqlStmts.generalviewgetOutsideInterestSupp", ctx);
		ctx.put(PDFConstants.GENERAL_LIST_04, listOutsideInterestSupp);
		
		Object listClaimIncidentSupp = SqlResources.getSqlMapProcessor(ctx)
		.select("SqlStmts.BasicInfoviewgetClaimIncidentSupp", ctx);		
		ctx.put(PDFConstants.BASICINFO_LIST_04, listClaimIncidentSupp);
		
		Object listFeeSuitSupp = SqlResources.getSqlMapProcessor(ctx)
		.select("SqlStmts.RenewBasicInfoviewgetFeesSuedList", ctx);
		ctx.put(PDFConstants.RENEWBASICINFO_LIST_FEESUIT, listFeeSuitSupp);
		
		// Investment/Financial Planning Supplement
		Object objFinancialPlanningAndIAServiceSupp =SqlResources.getSqlMapProcessor(ctx).findByKey("FinancialPlanningAndIAServiceSupp.findByKey", ctx);
		ctx.put(PDFConstants.INVEST_FINAN_PLANING_FREEFORM_01, objFinancialPlanningAndIAServiceSupp);
		
		// Audit Supplement
		Object objAttestation =SqlResources.getSqlMapProcessor(ctx).findByKey("Attestation.findByKey", ctx);
		ctx.put(PDFConstants.ATTESTATION_FREEFORM_02, objAttestation);		
		
		Object listAttestationAuditWork =SqlResources.getSqlMapProcessor(ctx).select("SqlStmts.attestationviewmomAuditwork", ctx);
		ctx.put(PDFConstants.ATTESTATION_LIST_03, listAttestationAuditWork);
		
		//Computer Releated Services
		Object listITServicesSupp =SqlResources.getSqlMapProcessor(ctx).select("SqlStmts.computer-servicesviewgetITServicesSupp", ctx);
		ctx.put(PDFConstants.COMPUTERSERVICE_LIST_03, listITServicesSupp);
				
		//Business Management Supplement
		Object objFundsControlledSupp =SqlResources.getSqlMapProcessor(ctx).findByKey("FundsControlledSupp.findByKey", ctx);
		ctx.put(PDFConstants.GENERAL_FREEFORM_03, objFundsControlledSupp);	
		
		Object objClientServiceDescFundControl =SqlResources.getSqlMapProcessor(ctx).findByKey("SqlStmts.UserStatementsaveAccountstmtsgeClientServiceDescFundControl", ctx);
		ctx.put(PDFConstants.CLIENTSERVICEDESC_FUNDCONTROL_FREEFORM_01, objClientServiceDescFundControl);
				
		Object objClientServiceDescFirm =SqlResources.getSqlMapProcessor(ctx).findByKey("SqlStmts.UserStatementsaveAccountstmtsgeClientServiceDescFirm", ctx);
		ctx.put(PDFConstants.CLIENTSERVICEDESC_FIRM_FREEFORM_01, objClientServiceDescFirm);
		
		
		new AccountUtils().getAverageRevenueRenewApplication(ctx);
		new AccountantValidationUtils().hideShowSupplementOnRenewPDF(ctx);
	
	}
	
	
	public static void main(String[] a){
		
		String amount = "$656,344,554.00";
		if(amount != null && !"".equals(amount)){
			
			amount = amount.substring(0,amount.lastIndexOf("."));
			
		}
		System.out.println(amount);
	}
	
	public static String formateAmountString(String amount){
		
		if(amount != null && !"".equals(amount)){
			
			if(amount.lastIndexOf(".") != -1)			
				amount = amount.substring(0,amount.lastIndexOf("."));
			
		}
		return amount ;
	}

	public void fetchDataForXml(Context ctx) {

		try {
			Object objPoilcy = SqlResources.getSqlMapProcessor(ctx).findByKey("SqlStmts.UserStatementpdfquotelettergetPriorPolicyInfo",	ctx);
			ctx.put("policy_freeform_01", objPoilcy);

			Object objAddress = SqlResources.getSqlMapProcessor(ctx).findByKey("SqlStmts.UserStatementpdfquotelettergetAddressdetails", ctx);
			ctx.put("address_freeform_01", objAddress);

			Object objFooterDate = SqlResources.getSqlMapProcessor(ctx).select("SqlStmts.UserStatementpdfquotelettergetQuoteFooterDate", ctx);
			ctx.put("date_freeform_01", objFooterDate);

			Object objFooterTime = SqlResources.getSqlMapProcessor(ctx).select("SqlStmts.UserStatementpdfquotelettergetQuoteFooterTime", ctx);
			ctx.put("time_freeform_01", objFooterTime);

			Object listQuoteOption = SqlResources.getSqlMapProcessor(ctx).select("SqlStmts.UserStatementpdfquotelettergetQuoteOptedList", ctx);

			String IsClaimExpensesTypeFlag = "N";
			List quoteList = new ArrayList();
			if (listQuoteOption != null && listQuoteOption instanceof List) {
				List list = (List) listQuoteOption;
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
						quoteList.add(map);
					}

					if (map.get("IsClaimExpensesType") != null && "Y".equals(map.get("IsClaimExpensesType").toString()))
						IsClaimExpensesTypeFlag = "Y";
				}
			}

			ctx.put("quoteOpted_list_01", quoteList);
			//ctx.put("ClaimExpensesTypeFlag", IsClaimExpensesTypeFlag);
			 listQuoteOption = SqlResources.getSqlMapProcessor(ctx).select("SqlStmts.UserStatementpdfquotelettergetQuoteOptedListNew",ctx);
				ctx.put("quoteOptedUpdated_list_01", listQuoteOption);
			if(objAddress != null){
				
				if(((Map)objAddress).get("StateCode") != null && "PA".equals(((Map)objAddress).get("StateCode").toString() )){
					boolean isClaimExpenseInsideFlag = AccountUtils.checkForAnyQuoteWithInsideLimit(ctx);
					if(isClaimExpenseInsideFlag)
						ctx.put("ClaimExpensesTypeFlag", "N");
					else
						ctx.put("ClaimExpensesTypeFlag", "Y");
				}else if(((Map)objAddress).get("StateCode") != null && "ND".equals(((Map)objAddress).get("StateCode").toString() )){
					boolean isClaimExpenseInsideFlag = AccountUtils.checkForAnyQuoteWithInsideLimit(ctx);
					if(isClaimExpenseInsideFlag)
						ctx.put("ClaimExpensesTypeFlag", "N");
					else
						ctx.put("ClaimExpensesTypeFlag", "Y");
				}
			}
			

			Object objProLiabIns = SqlResources.getSqlMapProcessor(ctx).findByKey("SqlStmts.UserStatementpdfquotelettergetProfessionalLiabilityInsDetail",	ctx);
			ctx.put("professionalliabilityinsdetail_freeform_01", objProLiabIns);

			List listFormID = DocumentManagment.getPolicyFormID(ctx);
			ctx.put("stateform_list_01", listFormID);
			

		} catch (Exception e) {
			logger.error("Problem in fetching data for Quote Number : "	+ ctx.get("QuoteNumber") + " " + e.getMessage());
		}

	}

	public void populateDataXml(Context ctx, String xmlFile) {
		StringBuffer buffer = new StringBuffer(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");

		try {
			// Data writer
			IOUtils.writeToFile(xmlFile, buffer.append(new RequestProcessor()
					.generateResponseXml(ctx,false)));
		} catch (Exception e) {
			logger.error("Problem in generating data xml for Quote Number : "
					+ ctx.get("QuoteNumber") + " " + e.getMessage());
		}
	}

	public Map populateOutput(Element element) {
		StringBuffer buffer = new StringBuffer();
		List children = element.getChildren();
		if (children == null)
			return new HashMap();
		Map out = new HashMap();
		for (int i = 0; i < children.size(); i++) {
			Element child = (Element) children.get(i);
			String name = child.getName();
			String str = child.getTextTrim();
			out.put(name, str);
		}

		return out;
	}

	public static Map populateFooter(Object listPolicyFormFooter)
			throws Exception {
		List listFormFooter = new ArrayList();
		// Object objPolicyFormFooter =
		// SqlResources.getSqlMapProcessor(listStateForm).select("PolicyForm.findAllByPartialKey",
		// listStateForm);

		if (listPolicyFormFooter != null
				&& listPolicyFormFooter instanceof List)
			listFormFooter = (List) listPolicyFormFooter;

		Map mapFooter = new HashMap();
		for (int i = 0; i < listFormFooter.size(); i++) {

			Map map = (Map) listFormFooter.get(i);
			if (map.get("FormFooter") == null)
				continue;

			String formID = map.get("FormFooter").toString();

			if ("APL 100-106-2010".equals(formID))
				mapFooter.put("APL1062010", formID);
			else if (formID.indexOf("(") < 0)
				mapFooter.put(formID.replace("-", "").replace(" ", "").replace(
						"#", ""), formID);
			else if (formID.indexOf("(") > 0)
				mapFooter.put(formID.replace("-", "").replace(" ", "").replace(
						"(", "").replace("/", "").replace(")", "").replace("#",
						""), formID);
		}
		logger.debug("generating footer for Quote  : ");
		return mapFooter;
	}

	public void makeQuickQuoteListPdf(Context ctx, OutputStream out,
			String baseUrl, ServletContextURIResolver uriResolver) {

		String xmlFile = "";
		String foFile = "";

		try {

			xmlFile = SystemProperties.getInstance().getString("html.basedir")
					+ "data//quoteletter.xml";
			foFile = SystemProperties.getInstance().getString("html.basedir")
					+ "foxsl//quickquoteletter.xsl";

			// foFile = SystemProperties.getInstance().getString("html.basedir")
			// + "foxsl//quoteletter.xsl";

			fetchDataForQuickQuoteXml(ctx);
			populateDataXml(ctx, xmlFile);
			baseUrl = "file:///" + ((ServletContext)ctx.get(HtmlConstants.DOCUMENTSERVLETCONTEXT)).getRealPath("/");
			new XML2PDF().convertPOToPDF(foFile, xmlFile, out, baseUrl,
					(ServletContextURIResolver)ctx.get(HtmlConstants.DOCUMENTURIRESOLVER),ctx);
		} catch (Exception e) {
			logger
					.error("Problem in generating quote letter for Quote Number : "
							+ ctx.get("QuoteNumber") + " " + e.getMessage());
		}
	}

	public void fetchDataForQuickQuoteXml(Context ctx) {

		try {
			Object objPoilcy = SqlResources.getSqlMapProcessor(ctx).findByKey("SqlStmts.UserStatementpdfquotelettergetPriorPolicyInfo",ctx);
			ctx.put("policy_freeform_01", objPoilcy);

			Object objAddress = SqlResources.getSqlMapProcessor(ctx).findByKey("SqlStmts.UserStatementpdfquotelettergetAddressdetails",	ctx);
			ctx.put("address_freeform_01", objAddress);

			Object objFooterDate = SqlResources.getSqlMapProcessor(ctx).select("SqlStmts.UserStatementpdfquotelettergetQuoteFooterDate",ctx);
			ctx.put("date_freeform_01", objFooterDate);

			Object objFooterTime = SqlResources.getSqlMapProcessor(ctx).select("SqlStmts.UserStatementpdfquotelettergetQuoteFooterTime",ctx);
			ctx.put("time_freeform_01", objFooterTime);

			Object listQuoteOption = SqlResources.getSqlMapProcessor(ctx).select("SqlStmts.UserStatementpdfquotelettergetQuoteListAll",ctx);
			ctx.put("quoteOpted_list_01", listQuoteOption);

			Object objProfessionalLiability = SqlResources.getSqlMapProcessor(ctx).findByKey("SqlStmts.UserStatementpdfquotelettergetFirmYear", ctx);
			ctx.put("firmyear_freeform_01", objProfessionalLiability);
			
			Object objGrossRevenueForFirm = SqlResources.getSqlMapProcessor(ctx).findByKey("SqlStmts.UserStatementpdfquotelettergetLastRevenue", ctx);
			ctx.put("lastyearrevenue_freeform_01", objGrossRevenueForFirm);
			
			

			List aoplist = SqlResources.getSqlMapProcessor(ctx).select("SqlStmts.UserStatementsaveAccountstmtspopulateAOPFieldsQuickQuote",ctx);
			//ctx.remove("AOP_freeform_20");

			ctx.put("aoplist", aoplist);
			Map covMap = new HashMap();
			if (aoplist != null) {
				Map map = new HashMap();
				int total = 0;
				for (int i = 0; i < aoplist.size(); i++) {
					map = (HashMap) aoplist.get(i);
					ctx.put("AOP_Percentage_" + map.get("AOPKey"), map.get("PercentageValue"));
					if (map.get("PercentageValue") != null	&& !map.get("PercentageValue").equals("")) {
						total = total + Integer.parseInt(map.get("PercentageValue").toString());
						ctx.put("AOP_PercentageValue_" + i, map.get("PercentageValue").toString());
					}
					ctx.put("AOPCommentDesc_" + map.get("AOPKey"), map.get("AOPCommentDesc"));
				}
				
				ctx.put("aop_total", total);
			}

		} catch (Exception e) {
			logger.error("Problem in fetching data for quick quote insured : " + " " + e.getMessage());
		}

	}
	
	public void makeRenewPdfForMail(Context ctx, OutputStream out, String baseUrl,
			ServletContextURIResolver uriResolver) {

		String xmlFile = "";
		String foFile = "";

		try {

			xmlFile = SystemProperties.getInstance().getString("html.basedir")
					+ "data//quoteletter.xml";
			Object objnew = RuleUtils.executeRule(ctx, "AccountantRule.newAppFlow");
			boolean flagnew = false;			
			if(objnew instanceof Boolean)
				flagnew = (Boolean) objnew;
			
			if(flagnew)
			{
				if(!"3".equals(ctx.get("CompanyKey").toString()))
				{
					if(ctx.get("StateCode").equals("NY"))
						foFile = SystemProperties.getInstance().getString("html.basedir")+ "foxsl//renewalapplicationnewappNYFTZ.xsl";
						
					else if(ctx.get("StateCode").equals("ME"))
						foFile = SystemProperties.getInstance().getString("html.basedir")+ "foxsl//MERenewalApplication.xsl";
						
					else if(ctx.get("StateCode").equals("NC"))
						foFile = SystemProperties.getInstance().getString("html.basedir")+ "foxsl//NCRenewalApplication.xsl";
					else
							foFile = SystemProperties.getInstance().getString("html.basedir")+ "foxsl//renewalapplicationnewapp.xsl";
					
				}
				if("3".equals(ctx.get("CompanyKey").toString()))
				{
					if(ctx.get("StateCode").equals("NY"))
						foFile = SystemProperties.getInstance().getString("html.basedir")+ "ISMIE//ApplicationPDF//RenewBusiness//renewalapplicationnewappNYFTZ.xsl";
						
					else if(ctx.get("StateCode").equals("ME"))
						foFile = SystemProperties.getInstance().getString("html.basedir")+ "ISMIE//ApplicationPDF//RenewBusiness//MERenewalApplication.xsl";
						
					else if(ctx.get("StateCode").equals("NC"))
						foFile = SystemProperties.getInstance().getString("html.basedir")+ "ISMIE//ApplicationPDF//RenewBusiness//NCRenewalApplication.xsl";
					else
						foFile = SystemProperties.getInstance().getString("html.basedir")+ "ISMIE//ApplicationPDF//RenewBusiness//renewalapplicationnewapp.xsl";
					
				}
				new DocumentGenerationBO().fetchDataForRenewApplicationPDF(ctx);
			}
			else{
			foFile = SystemProperties.getInstance().getString("html.basedir")
					+ "foxsl//renewapplication.xsl";
			fetchDataForRenewXml(ctx);
			}
			// foFile = SystemProperties.getInstance().getString("html.basedir")
			// + "foxsl//quoteletter.xsl";

			
			populateDataXml(ctx, xmlFile);
			if(ctx.get(HtmlConstants.DOCUMENTSERVLETCONTEXT) != null)
				baseUrl = "file:///" + ((ServletContext)ctx.get(HtmlConstants.DOCUMENTSERVLETCONTEXT)).getRealPath("/");
			
			new XML2PDF().convertPOToPDF(foFile, xmlFile, out,baseUrl,uriResolver,ctx);
		} catch (Exception e) {
			logger
					.error("Problem in generating quote letter for Quote Number : "
							+ ctx.get("QuoteNumber") + " " + e.getMessage());
		}
	}
	
	public void makePAPdf(Context ctx, OutputStream out, String baseUrl,
			ServletContextURIResolver uriResolver) {

		String xmlFile = "";
		String foFile = "";

		try {

			xmlFile = SystemProperties.getInstance().getString("html.basedir")
					+ "data//quoteletter.xml";
			foFile = SystemProperties.getInstance().getString("html.basedir")
					+ "foxsl//LPLNOTICEPA2.xsl";			
			baseUrl = "file:///" + ((ServletContext)ctx.get(HtmlConstants.DOCUMENTSERVLETCONTEXT)).getRealPath("/");
			new XML2PDF().convertPOToPDF(foFile, xmlFile, out,baseUrl,(ServletContextURIResolver)ctx.get(HtmlConstants.DOCUMENTURIRESOLVER),ctx);
		} catch (Exception e) {
			logger
					.error("Problem in generating quote letter PA for Quote Number : "
							+ ctx.get("QuoteNumber") + " " + e.getMessage());
		}
	}
	
	public void makeNDPdf(Context ctx, OutputStream out, String baseUrl,
			ServletContextURIResolver uriResolver) {

		String xmlFile = "";
		String foFile = "";

		try {

			xmlFile = SystemProperties.getInstance().getString("html.basedir")
					+ "data//quoteletter.xml";
			foFile = SystemProperties.getInstance().getString("html.basedir")
					+ "foxsl//group5//MO_42_001_06_15.xsl";			
			baseUrl = "file:///" + ((ServletContext)ctx.get(HtmlConstants.DOCUMENTSERVLETCONTEXT)).getRealPath("/");
			new XML2PDF().convertPOToPDF(foFile, xmlFile, out,baseUrl,(ServletContextURIResolver)ctx.get(HtmlConstants.DOCUMENTURIRESOLVER),ctx);
		} catch (Exception e) {
			logger
					.error("Problem in generating quote letter ND for Quote Number : "
							+ ctx.get("QuoteNumber") + " " + e.getMessage());
		}
	}
	
	public void makeOffRiskPDF(Context ctx, OutputStream out, String baseUrl,
			ServletContextURIResolver uriResolver) {

		String xmlFile = "";
		String foFile = "";

		try {

			xmlFile = SystemProperties.getInstance().getString("html.basedir")
					+ "data//quoteletter.xml";
			foFile = SystemProperties.getInstance().getString("html.basedir")
					+ "foxsl//OffRiskLetterAccountant.xsl";

			fetchDataForXmlOffRisk(ctx);
			populateDataXml(ctx, xmlFile);
			baseUrl = "file:///" + ((ServletContext)ctx.get(HtmlConstants.DOCUMENTSERVLETCONTEXT)).getRealPath("/");
			new XML2PDF().convertPOToPDF(foFile, xmlFile, out, baseUrl,(ServletContextURIResolver)ctx.get(HtmlConstants.DOCUMENTURIRESOLVER),ctx);
		} catch (Exception e) {
			logger
					.error("Problem in generating quote letter for Quote Number : "
							+ ctx.get("QuoteNumber") + " " + e.getMessage());
		}
	}
	
	public void fetchDataForXmlOffRisk(Context ctx) {
		try {
			Object objPoilcy = SqlResources.getSqlMapProcessor(ctx).findByKey(
					"Policy.FetchAllPolicyDataFromPolicyKey",
					ctx);
			ctx.put("policy_freeform_01", objPoilcy);

		} catch (Exception e) {
			logger.error("Problem in fetching data for Quote Number : "
					+ ctx.get("QuoteNumber") + " " + e.getMessage());
		}
	}

	
	public void makePdfForMail(Context ctx, OutputStream out, String baseUrl,
			ServletContextURIResolver uriResolver) {

		String xmlFile = "";
		String foFile = "";

		try {

			xmlFile = SystemProperties.getInstance().getString("html.basedir")
					+ "data//quoteletter.xml";
			
			if(!"3".equals(ctx.get("CompanyKey").toString()))
			{
				if(ctx.get("StateCode").equals("NY")){
				foFile = SystemProperties.getInstance().getString("html.basedir")
						+ "foxsl//NewApplicationPDFNYFTZ.xsl";
				}
				else if(ctx.get("StateCode").equals("MD")){
					foFile = SystemProperties.getInstance().getString("html.basedir")
							+ "foxsl//MDNewBusinessApplication.xsl";
					}
				
				else if(ctx.get("StateCode").equals("ME")){
					foFile = SystemProperties.getInstance().getString("html.basedir")
							+ "foxsl//MENewBusinessApplication.xsl";
					}
				else if(ctx.get("StateCode").equals("NC")){
					foFile = SystemProperties.getInstance().getString("html.basedir")
							+ "foxsl//NCNewBusinessApplication.xsl";
					}
				else{
				foFile = SystemProperties.getInstance().getString("html.basedir")
						+ "foxsl//NewApplicationPDF.xsl";
				}	
			}
			if("3".equals(ctx.get("CompanyKey").toString()))
			{
				if(ctx.get("StateCode").equals("NY"))
					foFile = SystemProperties.getInstance().getString("html.basedir")+ "ISMIE//ApplicationPDF//NewBusiness//NewApplicationPDFNYFTZ.xsl";
					
				else if(ctx.get("StateCode").equals("MD"))
					foFile = SystemProperties.getInstance().getString("html.basedir")+ "ISMIE//ApplicationPDF//NewBusiness//MDNewBusinessApplication.xsl";
									
				else if(ctx.get("StateCode").equals("ME"))
					foFile = SystemProperties.getInstance().getString("html.basedir")+ "ISMIE//ApplicationPDF//NewBusiness//MENewBusinessApplication.xsl";
					
				else if(ctx.get("StateCode").equals("NC"))
					foFile = SystemProperties.getInstance().getString("html.basedir")+ "ISMIE//ApplicationPDF//NewBusiness//NCNewBusinessApplication.xsl";
					
				else
				foFile = SystemProperties.getInstance().getString("html.basedir")+ "ISMIE//ApplicationPDF//NewBusiness//NewApplicationPDF.xsl";
				
				
			}
			// foFile = SystemProperties.getInstance().getString("html.basedir")
			// + "foxsl//quoteletter.xsl";

			new DocumentGenerationBO().fetchDataForNewApplicationPDF(ctx);
			populateDataXml(ctx, xmlFile);
			baseUrl = "file:///" + ((ServletContext)ctx.get(HtmlConstants.DOCUMENTSERVLETCONTEXT)).getRealPath("/");
			
			new XML2PDF().convertPOToPDF(foFile, xmlFile, out,baseUrl,(ServletContextURIResolver)ctx.get(HtmlConstants.DOCUMENTURIRESOLVER),ctx);
		} catch (Exception e) {
			logger
					.error("Problem in generating quote letter for Quote Number : "
							+ ctx.get("QuoteNumber") + " " + e.getMessage());
		}
	}
	
}
