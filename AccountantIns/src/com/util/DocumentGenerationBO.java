package com.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.fop.servlet.ServletContextURIResolver;
import org.jdom.Element;

import com.excel.downloadexcel.Download_Excel;
import com.excel.downloadexcel.lawyers.DownloadExcelLawyers;
import com.fop.DownloadFOP;
import com.fop.DownloadRating;
import com.formgenerator.DownloadForm;
import com.manage.esign.EsignUtil;
import com.manage.managemetadata.functions.commonfunctions.Math;
import com.manage.managemetadata.functions.commonfunctions.RuleUtils;
import com.manage.util.PageUtils;
import com.ormapping.ibatis.SqlResources;
import com.pdf.PDFConstants;
import com.pdf.PDFGenerator;
import com.pdf.PrintPDF;
import com.userbo.AccountUtils;
import com.userbo.AccountantValidationUtils;
import com.userbo.DocumentManagment;
import com.userbo.OffRiskAccountantUtils;
import com.userbo.QuoteLetter;

public class DocumentGenerationBO {

	private static InetLogger logger = InetLogger
			.getInetLogger(DocumentGenerationBO.class);
	
	private static final String ERROR_MESSAGE = "ERROR : ";
	private static final String SUCCESS_MESSAGE = "SUCCESS";

	public void downloadPDF(IContext ctx) throws Exception {
		HttpServletResponse res = (HttpServletResponse) ctx
				.get("DocumentResponse");
		{ if (logger.isDebugEnabled()) logger.debug("DownloadPDF Start for Quote Number : "
				+ ctx.get("QuoteNumber")); }
		{ if (logger.isDebugEnabled()) logger.debug("DownloadPDF Start for Quote Number : "
				+ ctx.get("QuoteNumber")); }
		OutputStream out = null;
		ByteArrayOutputStream baos = null;
		try {
			baos = new PrintPDF().makePdf((Context) ctx);
			if (res != null) {
				// setting some response headers
				res.setHeader("Expires", "0");
				// response.setHeader("Cache-Control",
				// "must-revalidate, post-check=0, pre-check=0");
				res.setHeader("Content-disposition", "attachment; filename=\""
						+ "Accountant_" + ctx.get("QuoteNumber").toString()
						+ ".pdf\"");
				res.setHeader("Pragma", "public");

				// setting the content type
				res.setContentType("application/pdf");
				// the contentlength is needed for MSIE!!!
				res.setContentLength(baos.size());
			}

			if (baos != null) {
				out = res.getOutputStream();
				baos.writeTo(out);
				out.flush();
			}

		} catch (Exception e) {

			logger.error(e);
			throw e;
		} finally {
			if (baos != null)
				try {
					baos.close();
				} catch (Exception e) {
					logger.error("Error in DocumentGenerationBO.downloadPDF", e);
				}
			if (out != null)
				try {
					out.close();
				} catch (Exception e) {
					logger.error(e);
					throw e;
				}
		}

		{ if (logger.isDebugEnabled()) logger.debug("DownloadPDF end for Quote Number : "
				+ ctx.get("QuoteNumber")); }
	}

	public void downloadRatingPDF(Context ctx) {
		{ if (logger.isDebugEnabled()) logger.debug("Download rating start for Quote Number : "
				+ ctx.get("QuoteNumber")); }
		{ if (logger.isDebugEnabled()) logger.debug("Download rating start for transaction sequence : "
				+ ctx.get("TransactionSequence")); }
		HttpServletRequest req = (HttpServletRequest) ctx
				.get("DocumentRequest");
		HttpServletResponse res = (HttpServletResponse) ctx
				.get("DocumentResponse");
		ServletContext servletContext = (ServletContext) ctx
				.get("DocumentServletContext");
		ServletOutputStream out = null;
		String baseUrl = null;
		try {
			out = res.getOutputStream();
			if (res != null) {
				// setting some response headers
				res.setHeader("Expires", "0");
				res.setHeader("Content-disposition", "attachment; filename=\""
						+ "Accountant_Rating_"
						+ ctx.get("QuoteNumber").toString() + ".pdf\"");
				res.setHeader("Pragma", "public");
			}

			// new DownloadFOP().makePdf(ctx, req, res, uriResolver,
			// servletContext);
			// baseUrl = "file:///" + servletContext.getRealPath("/");
			baseUrl = "file:///"
					+ ((ServletContext) ctx
							.get(HtmlConstants.DOCUMENTSERVLETCONTEXT))
							.getRealPath("/");
			new DownloadRating().makeRatingPdf(ctx, out, baseUrl, null);

			out = res.getOutputStream();
			out.flush();
		} catch (Exception e) {
			logger.error(e);
		} finally {
			if (out != null)
				try {
					out.close();
				} catch (Exception e) {
					logger.error("Error in DocumentGenerationBO.downloadRatingPDF", e);

				}
		}
		{ if (logger.isDebugEnabled()) logger.debug("Download rating end for Quote Number : "
				+ ctx.get("QuoteNumber")); }
	}

	public static Object fetchDataForReports(Context ctx) throws Exception {
		boolean errorFlag = false;
		HttpServletRequest request = (HttpServletRequest) ctx.get("DocumentRequest");
		HttpServletResponse response = (HttpServletResponse) ctx.get("DocumentResponse");
		if (ctx.get("FromDate") == null && ctx.get("ToDate") == null) {
			AccountUtils.populateError(ctx, "FromDate",
					"Start Date is required");
			AccountUtils.populateError(ctx, "ToDate", "End Date is required");
			errorFlag = true;
		} else {
			if (ctx.get("FromDate") != null
					&& "".equals(ctx.get("FromDate").toString())) {
				AccountUtils.populateError(ctx, "FromDate",
						"Start Date is required");
				errorFlag = true;
			}
			if (ctx.get("ToDate") != null
					&& "".equals(ctx.get("ToDate").toString())) {
				AccountUtils.populateError(ctx, "ToDate",
						"End Date is required");
				errorFlag = true;
			}
		}

		if (ctx.get("FromDate") != null
				&& !"".equals(ctx.get("FromDate").toString())) {
			AccountUtils.validateLeapYearDate(ctx, "FromDate",
					ctx.get("FromDate").toString());
			List errorList = null;
			if (ctx.get(Constants.INET_ERRORS_LIST) != null)
				errorList = (List) ctx.get(Constants.INET_ERRORS_LIST);

			if (errorList != null && errorList.size() > 0)
				errorFlag = true;
		}

		if (ctx.get("ToDate") != null
				&& !"".equals(ctx.get("ToDate").toString())) {
			AccountUtils.validateLeapYearDate(ctx, "ToDate", ctx.get("ToDate")
					.toString());
			List errorList = null;
			if (ctx.get(Constants.INET_ERRORS_LIST) != null)
				errorList = (List) ctx.get(Constants.INET_ERRORS_LIST);

			if (errorList != null && errorList.size() > 0)
				errorFlag = true;
		}

		if (errorFlag) {
			PageUtils utils = new PageUtils();
			String next_page = utils.populateNextComponentpage(ctx, request);
			utils.populateSession(request, ctx);
			String htmlDoc = utils.parseHtmlDocument(ctx, next_page,request);
			utils.writeResponse(response, htmlDoc, "text/html");
		} else {
			String newFileName = "";
			if("QUICKBOOK".equals(ctx.get("ReportType"))){
				response.setContentType("text/plain");
				newFileName = "Report (" + ctx.get("FromDate") + ") .IIF";
			}
			else if("BROKERAGE".equals(ctx.get("ReportType")))
			{
				response.setContentType("application/vnd.ms-excel");
				newFileName = "BrokerageReport.xls";
			}
			else if("ISMIE".equals(ctx.get("ReportType")))
			{
				response.setContentType("application/vnd.ms-excel");
				newFileName = "ISMIEReport.xls";
			}
			else if("MCGOWAN".equals(ctx.get("ReportType")))
			{
				response.setContentType("application/zip");
				newFileName = "MCGOWAN.zip";
			}
			else {
				response.setContentType("application/vnd.ms-excel");
				newFileName = "Report.xls";
			}
			response.setHeader("Content-disposition","attachment; filename=\"" + newFileName + "\"");	
			AccountUtils.validateReport(ctx);
			new Download_Excel().downloadReport(ctx, request, response);				
		}

		return null;
	}

	public static Object fetchWeeklyDataForReports(Context ctx)
			throws Exception {

	/*	String sheetsname = null;
		String sheetsids = null;
		String[] sheetsWeeklyReport = null;
		String[] firstviewerId = null;
		String sheetName = null;

		if ("Acc".equals(ctx.get("ProjectType").toString())) {

			sheetsname = SystemProperties.getInstance().getString(
					"app.AccountantIns.Weeklyreport.sheetnames");
			sheetsids = SystemProperties.getInstance().getString(
					"app.AccountantIns.Weeklyreport.userids");
			ctx.put("ReportName", "AccountantWeeklyReports");
		} else if ("Lwy".equals(ctx.get("ProjectType").toString())) {

			sheetsname = SystemProperties.getInstance().getString(
					"app.LawyersIns.Weeklyreport.sheetnames");
			sheetsids = SystemProperties.getInstance().getString(
					"app.LawyersIns.Weeklyreport.userids");
			ctx.put("ReportName", "LawyerWeeklyReports");
		}

		if (sheetsname != null) {
			sheetsWeeklyReport = sheetsname.split(":");
			firstviewerId = sheetsids.split(":");

			for (int i = 0; i < sheetsWeeklyReport.length; i++) {
				sheetName = sheetsWeeklyReport[i];

				if ("NULL".equals(firstviewerId[i]))
					ctx.put("FirstReviewUserID", null);
				else
					ctx.put("FirstReviewUserID", firstviewerId[i]);

				if (sheetName.equals("All_Issued_Policies")) {
					Object issuedlist = SqlResources
							.getSqlMapProcessor(ctx)
							.select("SqlStmts.UserStatementreportgenerationgetReportListByIssuedPolicy",
									ctx);
					ctx.put(sheetName + "_list_01", issuedlist);

				} else {
					getDataForWeeklyReport(ctx, sheetName);
				}
			}
		}*/
		HttpServletRequest request = (HttpServletRequest) ctx.get("DocumentRequest");
		HttpServletResponse response = (HttpServletResponse) ctx.get("DocumentResponse");
		boolean errorFlag = false;
		if((ctx.get("WeekFromDate") == null) && (ctx.get("WeekToDate") == null) )
		{
			AccountUtils.populateError(ctx, "WeekFromDate ", "Start Date is required");
			AccountUtils.populateError(ctx, "WeekToDate", "End Date is required");
			errorFlag = true;
		}
		else 
		{
			if(ctx.get("WeekFromDate") != null && "".equals(ctx.get("WeekFromDate").toString()))
			{
				AccountUtils.populateError(ctx, "WeekFromDate", "Start Date is required");
				errorFlag = true;
			}
			if(ctx.get("WeekToDate") != null && "".equals(ctx.get("WeekToDate").toString()))
			{
				AccountUtils.populateError(ctx, "WeekToDate", "End Date is required");
				errorFlag = true;
			}
		}
		
		if(ctx.get("WeekFromDate") != null && !"".equals(ctx.get("WeekFromDate").toString())){
			AccountUtils.validateLeapYearDate(ctx, "WeekFromDate", ctx.get("WeekFromDate").toString());
			List errorList = null;
			if (ctx.get(Constants.INET_ERRORS_LIST) != null)
				errorList = (List) ctx.get(Constants.INET_ERRORS_LIST);
			
			if(errorList != null && errorList.size() > 0)
				errorFlag = true;							
		}
		
		if(ctx.get("WeekToDate") != null && !"".equals(ctx.get("WeekToDate").toString())){
			AccountUtils.validateLeapYearDate(ctx, "WeekToDate", ctx.get("WeekToDate").toString());
			List errorList = null;
			if (ctx.get(Constants.INET_ERRORS_LIST) != null)
				errorList = (List) ctx.get(Constants.INET_ERRORS_LIST);
			
			if(errorList != null && errorList.size() > 0)
				errorFlag = true;							
		}	
		
		if(errorFlag){
			PageUtils utils = new PageUtils();
			String next_page=utils.populateNextComponentpage(ctx, request); 						
			utils.populateSession(request, ctx);					             
			String htmlDoc =  utils.parseHtmlDocument(ctx,next_page,request); 						
			utils.writeResponse(response,htmlDoc,"text/html");
		}
		else
		{
			//downloadWeeklyReport(ctx, request, response);
			String newFileName="";
			logger.debug("Download Report Start.....................");		
			response.setContentType("application/vnd.ms-excel");		
			newFileName = "Report.xls";
			response.setHeader("Content-disposition","attachment; filename=\"" + newFileName + "\"");			
			
			Download_Excel downloadExcel = null;
			Object projectType = ctx.get("ProjectType");
			if("Acc".equals(projectType))
				downloadExcel = new Download_Excel();
			else if("Lwy".equals(projectType))
				downloadExcel = new DownloadExcelLawyers();
			if(downloadExcel == null)
				throw new IllegalArgumentException("Unsupported project type: " + projectType);
			
			downloadExcel.downloadWeeklyReport(ctx, request, response);				
			logger.debug("Download Report End ......................");	
			
		}
		return null;

	}

	private static void getDataForWeeklyReport(Context ctx,
			String excelSheetName) throws Exception {

		Object statedatelist = SqlResources.getSqlMapProcessor(ctx).select(
				"Account.weeklyreport_by_state", ctx);
		ctx.put(excelSheetName + "_statedate_list_01", statedatelist);

		if ("Export_All_Report".equals(excelSheetName)) {
			Object effectivedaterenewallist = SqlResources.getSqlMapProcessor(
					ctx).select("Account.weeklyreport_effectivedate_renewal",
					ctx);
			ctx.put(excelSheetName + "_effectivedaterenewal_list_01",
					effectivedaterenewallist);

			Object lastweeklist = SqlResources.getSqlMapProcessor(ctx).select(
					"Account.weeklyreport_creationdate_LastWeek", ctx);
			ctx.put(excelSheetName + "_lastweek_list_01", lastweeklist);
		}

		Object effectivedatelist = SqlResources.getSqlMapProcessor(ctx).select(
				"Account.weeklyreport_effectivedate_newbusiness", ctx);
		ctx.put(excelSheetName + "_effectivedate_list_01", effectivedatelist);

		Object creativedatelist = SqlResources.getSqlMapProcessor(ctx).select(
				"Account.weeklyreport_creationdate", ctx);
		ctx.put(excelSheetName + "_creativedate_list_01", creativedatelist);
	}

	public static Object fetchStateMonthDataForReports(Context ctx)
			throws Exception {
		/*if ("Acc".equals(ctx.get("ProjectType").toString())) {
			ctx.put("ReportName", "AccountantStateMonthReports");
		} else if ("Lwy".equals(ctx.get("ProjectType").toString())) {
			ctx.put("ReportName", "LawyerStateMonthReports");
		}
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf1 = new SimpleDateFormat("MM/dd/yyyy");
		Date date = null;
		String sheetName = null;
		String weekD = null;
		String weekdate = null;
		for (int i = 1; i <= 12; i++) {
			sheetName = "month" + i;

			weekdate = (String) ctx.get("WeekFromDate");
			date = new Date(weekdate);
			cal.setTime(date);
			cal.add(Calendar.MONTH, 1);
			date = cal.getTime();
			weekD = DateUtils.convertDateToString(date, "MM/dd/yyyy");
			ctx.put("WeekFromDate", weekD);

			Object statemonthreportlist = SqlResources.getSqlMapProcessor(ctx)
					.select("Account.weeklyreport_statemonth", ctx);
			ctx.put(sheetName + "_statemonthreport_list_01",
					statemonthreportlist);
		}*/
		HttpServletRequest request = (HttpServletRequest) ctx.get("DocumentRequest");
		HttpServletResponse response = (HttpServletResponse) ctx.get("DocumentResponse");
		boolean errorFlag = false;
		if((ctx.get("WeekFromDate") == null) && (ctx.get("WeekToDate") == null) )
		{
			AccountUtils.populateError(ctx, "WeekFromDate ", "Start Date is required");
			AccountUtils.populateError(ctx, "WeekToDate", "End Date is required");
			errorFlag = true;								
		}
		else 
		{
			if(ctx.get("WeekFromDate") != null && "".equals(ctx.get("WeekFromDate").toString()))
			{
				AccountUtils.populateError(ctx, "WeekFromDate", "Start Date is required");
				errorFlag = true;								
			}
			if(ctx.get("WeekToDate") != null && "".equals(ctx.get("WeekToDate").toString()))
			{
				AccountUtils.populateError(ctx, "WeekToDate", "End Date is required");
				errorFlag = true;								
			}
		}
		
		if(ctx.get("WeekFromDate") != null && !"".equals(ctx.get("WeekFromDate").toString())){
			AccountUtils.validateLeapYearDate(ctx, "WeekFromDate", ctx.get("WeekFromDate").toString());
			List errorList = null;
			if (ctx.get(Constants.INET_ERRORS_LIST) != null)
				errorList = (List) ctx.get(Constants.INET_ERRORS_LIST);
			
			if(errorList != null && errorList.size() > 0)
				errorFlag = true;							
		}
		
		if(ctx.get("WeekToDate") != null && !"".equals(ctx.get("WeekToDate").toString())){
			AccountUtils.validateLeapYearDate(ctx, "WeekToDate", ctx.get("WeekToDate").toString());
			List errorList = null;
			if (ctx.get(Constants.INET_ERRORS_LIST) != null)
				errorList = (List) ctx.get(Constants.INET_ERRORS_LIST);
			
			if(errorList != null && errorList.size() > 0)
				errorFlag = true;							
		}	
		PageUtils utils=new PageUtils();
		if(errorFlag){
			String next_page=utils.populateNextComponentpage(ctx, request); 						
			utils.populateSession(request, ctx);					             
			String htmlDoc =  utils.parseHtmlDocument(ctx,next_page,request); 						
			utils.writeResponse(response,htmlDoc,"text/html");
		}
		else
		{
			String newFileName="";
			logger.debug("Download Report Start.....................");		
			response.setContentType("application/vnd.ms-excel");				
			newFileName = "Report.xls";
			response.setHeader("Content-disposition","attachment; filename=\"" + newFileName + "\"");		
			
			new Download_Excel().downloadStateMonthReport(ctx, request, response);				
			logger.debug("Download statemonthReport End ......................");	

		}	
		return null;
	}

	public static Object fetchAllDataReports(Context ctx) throws Exception {
		/*Object allDataList = null;
		ctx.put("ReportName", "AccountantLawyersAllDataReports");

		allDataList = SqlResources.getSqlMapProcessor(ctx).select(
				"Account.FetchAllAccountantData", ctx);
		ctx.put("AllDataList_Accountant_list_01", allDataList);

		allDataList = SqlResources.getSqlMapProcessor(ctx).select(
				"Account.FetchAllLawyersData", ctx);
		ctx.put("AllDataList_Lawyers_list_01", allDataList);*/
		HttpServletRequest req = (HttpServletRequest) ctx.get("DocumentRequest");
		HttpServletResponse res = (HttpServletResponse) ctx.get("DocumentResponse");
		String newFileName="";
		logger.debug("Download All data Report Start.....................");		
		res.setContentType("application/vnd.ms-excel");		
		newFileName = "AllDataReport.xls";
		res.setHeader("Content-disposition","attachment; filename=\"" + newFileName + "\"");			
		
		Download_Excel downloadExcel = new Download_Excel();	
		
		downloadExcel.downloadAllReport(ctx, req, res);				
		logger.debug("Download Report End ......................");		

		return null;
	}

	public static Object fetchDataForQuoteRatingPdf(Context ctx) {

		try {

			Object objAddress = SqlResources.getSqlMapProcessor(ctx).findByKey(
					"SqlStmts.UserStatementpdfquotelettergetAddressdetails",
					ctx);
			ctx.put("address_freeform_01", objAddress);

			String TransactionSequenceFlag = ctx.get("TransactionSequenceFlag")
					.toString();
			if (TransactionSequenceFlag.equals("Y")) {
				Object objTransactionSequence = SqlResources
						.getSqlMapProcessor(ctx)
						.findByKey(
								"SqlStmts.UserStatementsaveAccountstmtsgetTransactionsequence",
								ctx);
				if (objTransactionSequence != null) {
					ctx.put("TransactionSequence",
							((Map) objTransactionSequence)
									.get("TransactionSequence"));
				}
			}

			Object objRatingData = SqlResources
					.getSqlMapProcessor(ctx)
					.findByKey(
							"SqlStmts.UserStatementsaveAccountstmtsgetRatingData",
							ctx);
			if (objRatingData != null) {

				ctx.put("RatingData", objRatingData);
			}

			Object objTaxCalculation = SqlResources
					.getSqlMapProcessor(ctx)
					.findByKey(
							"SqlStmts.UserStatementsaveAccountstmtsgetTaxCalculation",
							ctx);
			if (objTaxCalculation != null) {
				ctx.putAll((Map) objTaxCalculation);
			}

			Object objRatingTrace = SqlResources.getSqlMapProcessor(ctx)
					.findByKey("RatingTrace.findByKey", ctx);
			if (objRatingTrace != null) {
				Map mapRatingTrace = (Map) objRatingTrace;
				if (mapRatingTrace.get("XmlOutputDatafromRating") != null) {
					// ClobImpl clobImpl = (ClobImpl)
					// mapRatingTrace.get("XmlOutputDatafromRating");
					// String outputString =
					// clobImpl.getSubString(Long.parseLong("1"),
					// Integer.parseInt(new
					// Long(clobImpl.length()).toString()));
					String outputString = mapRatingTrace.get(
							"XmlOutputDatafromRating").toString();
					Element root = XMLUtils.parseXMLRootElement(outputString);
					Element outputElement = root.getChild("PremiumInfo");
					Map out = populateOutput(outputElement);
					{ if (logger.isDebugEnabled()) logger.debug("MTTaxAmmount-------"
							+ out.get("MTTaxAmmount")); }

					Map outData = calculateTax(out, ctx);

					// For Skip Tax Calulation

					// ///////////
					// ctx.put("PremiumInfo", out);
					ctx.put("PremiumInfo", outData);

				}
			}

		} catch (Exception e) {
			logger.error("Problem in fetching data for Quote Number : "
					+ ctx.get("QuoteNumber"), e);
		}
		return null;
	}

	public void downloadDocument(IContext ctx) throws Exception {
		HttpServletRequest req = (HttpServletRequest) ctx
				.get("DocumentRequest");
		HttpServletResponse res = (HttpServletResponse) ctx
				.get("DocumentResponse");
		ServletContext servletContext = (ServletContext) ctx
				.get("DocumentServletContext");

		{ if (logger.isDebugEnabled()) logger.debug("DownloadDocument Start for Document : "
				+ ctx.get("DocFileName")); }
		String contentType = servletContext.getMimeType((String) ctx
				.get("DocFileName"));
		res.setContentType(contentType);
		String newFileName = (String) ctx.get("DocFileName");

		res.setHeader("Content-disposition", "attachment; filename="
				+ newFileName);
		new DocumentManagment().downloadDocument(ctx, req, res);
		{ if (logger.isDebugEnabled()) logger.debug("DownloadDocument End for Document : "
				+ ctx.get("DocFileName")); }
	}

	public static Map calculateTax(Map out, Context ctx) throws Exception {
		// out.get("MTTaxAmmount"))

		Object obj = RuleUtils.executeRule(ctx,
				"AccountantRule.IsSkipTaxCalculation");
		boolean flag = true;
		if (obj != null && obj instanceof Boolean)
			flag = (Boolean) obj;

		double mTCountyTax = 0.0;
		double finalBaseLimitPrem = 0.0;

		// Skip No
		if (!flag) {
			if (out.get("MTTaxAmmount") != null
					&& !"".equals(out.get("MTTaxAmmount").toString())) {
				mTCountyTax = Double.parseDouble(out.get("MTTaxAmmount")
						.toString());

				// if(mTCountyTax ==0 )
				// out.put("IsSkipTaxCalculation", "N");
				// out.put("MTTaxAmmount",mTCountyTax ) ;
			}
		} else {
			out.put("MTTaxAmmount", 0.0);
		}
		// else
		// {
		// out.put("MTTaxAmmount",0.0);
		// out.put("IsSkipTaxCalculation", "Y");
		// }

		if (out.get("CountyTaxAmmount") != null
				&& !"".equals(out.get("CountyTaxAmmount").toString())) {
			if (mTCountyTax <= 0) {
				mTCountyTax = mTCountyTax
						+ Double.parseDouble(out.get("CountyTaxAmmount")
								.toString());
				{ if (logger.isDebugEnabled()) logger.debug("countrytaxamount------"
						+ Double.parseDouble(out.get("CountyTaxAmmount")
								.toString())); }

				out.put("CountyTaxAmmount", Double.parseDouble(out.get(
						"CountyTaxAmmount").toString()));
			} else
				out.put("CountyTaxAmmount", 0.0);
		}

		if (out.get("State1TaxAmmount") != null
				&& !"".equals(out.get("State1TaxAmmount").toString())) {
			mTCountyTax = mTCountyTax
					+ Double.parseDouble(out.get("State1TaxAmmount").toString());
			out.put("State1TaxAmmount", Math.round(out.get("State1TaxAmmount")));
		}
		if (out.get("State2TaxAmmount") != null
				&& !"".equals(out.get("State2TaxAmmount").toString())) {
			mTCountyTax = mTCountyTax
					+ Double.parseDouble(out.get("State2TaxAmmount").toString());
			out.put("State2TaxAmmount",
					Double.parseDouble(out.get("State2TaxAmmount").toString()));
		}

		out.put("Total_tax", Math.round(mTCountyTax));
		out.put("TotalCoverageTaxAmount", Math.round(mTCountyTax));

		if (out.get("FinalBaseLimitPremium") != null
				&& !"".equals(out.get("FinalBaseLimitPremium").toString())) {
			finalBaseLimitPrem = mTCountyTax
					+ Double.parseDouble(out.get("FinalBaseLimitPremium")
							.toString());
		}

		out.put("FinalBaseLimitPremiumWithTax", Math.round(finalBaseLimitPrem));

		return out;
	}

	// public void populateDataXml(Context ctx, String xmlFile) {
	//
	// StringBuffer buffer = new
	// StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
	//
	// try{
	// // Data writer
	// IOUtils.writeToFile(xmlFile, buffer.append(new
	// RequestProcessor().generateResponseXml(ctx)));
	// }
	// catch(Exception e){
	// logger.error("Problem in generating data xml for Quote Number : "
	// +ctx.get("QuoteNumber"));
	// }
	// }

	public static Map populateOutput(Element element) {
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

	public static Object fetchDataForViewQuoteLetter(Context ctx)
			throws Exception {

		HttpServletRequest req = (HttpServletRequest) ctx	.get("DocumentRequest");
		HttpServletResponse res = (HttpServletResponse) ctx
				.get("DocumentResponse");
		ServletContext servletContext = (ServletContext) ctx
				.get("DocumentServletContext");
		boolean isDollarDefenceSelected = QuoteLetter
				.checkDollarDefenseWithQuoteSelected(ctx);
		if (isDollarDefenceSelected) {
			AccountUtils.populateError(
							ctx,
							"isQuoteLetterSentView",
							"Quote letters cannot be viewed for manual selected quotes,please create a Manual quote letter.");

		}

		if (!isDollarDefenceSelected) {
			boolean isquoteselected = false;
			if ("2".equals(ctx.get("StatusKey").toString())
					|| "3".equals(ctx.get("StatusKey").toString())
					|| "9".equals(ctx.get("StatusKey").toString())
					||"10".equals(ctx.get("StatusKey").toString())) {
				QuoteLetter.saveCommentToPDF(ctx);
				// QuoteLetter.saveQuoteSentToPDF(ctx);
				QuoteLetter.viewQuoteSentToPDF(ctx);
				isquoteselected = QuoteLetter.isViewSendQuoteLetter(ctx);

				if (isquoteselected)
					AccountUtils.populatePolicyForm(ctx);
			} else
				ctx.remove("downloadquoteletterpdf_list");

			if (!isquoteselected) {
				AccountUtils.populateError(ctx, "isQuoteLetterSentView",
						"Select a quote option below");

			} else {
				fetchDataFromDBViewQuoteLetterPdf(ctx);
			}
		}

		return null;
	}

	public static Object fetchDataFromDBViewQuoteLetterPdf(Context ctx)
			throws Exception {
		String homePath = SystemProperties.getInstance().getString(
				"appl.home.dir");
		String quoteLetterXSLPath = homePath + "foxsl//quoteletter.xsl";
		String APL_PA_2009Path = homePath+ "foxsl//group4//APL - PA - 2009 - 3.xsl";
		ctx.put("quoteLetterXSLPath", quoteLetterXSLPath);
		ctx.put("APL_PA_2009Path", APL_PA_2009Path);
		String protexureAccountantsLogoPath = homePath+ "image//Protexure_Logo.png";
		ctx.put("protexureAccountantsLogoPath", protexureAccountantsLogoPath);
		String checkButtonPath = homePath + "image//check-btn4.png";
		ctx.put("checkButtonPath", checkButtonPath);
		String headerBottomPath = homePath + "image//header_bottom.png";
		ctx.put("headerBottomPath", headerBottomPath);
		String spaceBarPath = homePath + "image//spacer.png";
		ctx.put("spaceBarPath", spaceBarPath);
		try {
			Object objPoilcy = SqlResources.getSqlMapProcessor(ctx).findByKey(
					"SqlStmts.UserStatementpdfquotelettergetPriorPolicyInfo",
					ctx);
			Map objPoilcyMap=(Map)objPoilcy;
			RuleUtils.executeRule(ctx, "AccountantRule.fillQuoteDates");
			objPoilcyMap.put("QuoteEffectiveDate", ctx.get("QuoteEffectiveDate"));
			objPoilcyMap.put("QuoteExpiryDate", ctx.get("QuoteExpiryDate"));
			ctx.put("policy_freeform_01", objPoilcyMap);
			
			
			
			Object objAddress = SqlResources.getSqlMapProcessor(ctx).findByKey(
					"SqlStmts.UserStatementpdfquotelettergetAddressdetails",
					ctx);
			ctx.put("address_freeform_01", objAddress);

			Object objFooterDate = SqlResources.getSqlMapProcessor(ctx).select(
					"SqlStmts.UserStatementpdfquotelettergetQuoteFooterDate",
					ctx);
			ctx.put("date_freeform_01", objFooterDate);

			Object objFooterTime = SqlResources.getSqlMapProcessor(ctx).select(
					"SqlStmts.UserStatementpdfquotelettergetQuoteFooterTime",
					ctx);
			ctx.put("time_freeform_01", objFooterTime);

			Object listQuoteOption = SqlResources
					.getSqlMapProcessor(ctx)
					.select("SqlStmts.UserStatementpdfquotelettergetQuoteOptedList",
							ctx);
			Object dataObj = SqlResources.getSqlMapProcessor(ctx).findByKey(
					"SqlStmts.UserStatementpdfquotelettergetIsDirectNonBareFirmDetails",
					ctx);
			ctx.put("miscellaneousData_freeform_01", dataObj);
			String IsClaimExpensesTypeFlag = "N";
			List quoteList = new ArrayList();
			if (listQuoteOption != null && listQuoteOption instanceof List) {
				List list = (List) listQuoteOption;
				// For RatingTrace
				for (int i = 0; i < list.size(); i++) {
					Map map = (Map) list.get(i);
					if (map.get("XmlOutputDatafromRating") != null) {
						String outputString = map
								.get("XmlOutputDatafromRating").toString();
						Element root = XMLUtils
								.parseXMLRootElement(outputString);
						Element outputElement = root.getChild("PremiumInfo");
						Map out = populateOutput(outputElement);
						map.putAll(out);
						map.remove("XmlOutputDatafromRating");
						quoteList.add(map);
					}

					if (map.get("IsClaimExpensesType") != null
							&& "Y".equals(map.get("IsClaimExpensesType")
									.toString()))
						IsClaimExpensesTypeFlag = "Y";
				}
			}

			ctx.put("quoteOpted_list_01", quoteList);
			// ctx.put("ClaimExpensesTypeFlag", IsClaimExpensesTypeFlag);
			listQuoteOption = SqlResources.getSqlMapProcessor(ctx).select("SqlStmts.UserStatementpdfquotelettergetQuoteOptedListNew",ctx);
			ctx.put("quoteOptedUpdated_list_01", listQuoteOption);
			if (objAddress != null) {

				if (((Map) objAddress).get("StateCode") != null
						&& "PA".equals(((Map) objAddress).get("StateCode")
								.toString())) {
					boolean isClaimExpenseInsideFlag = AccountUtils
							.checkForAnyQuoteWithInsideLimit(ctx);
					if (isClaimExpenseInsideFlag)
						ctx.put("ClaimExpensesTypeFlag", "N");
					else
						ctx.put("ClaimExpensesTypeFlag", "Y");
				} else if (((Map) objAddress).get("StateCode") != null
						&& "ND".equals(((Map) objAddress).get("StateCode")
								.toString())) {
					boolean isClaimExpenseInsideFlag = AccountUtils
							.checkForAnyQuoteWithInsideLimit(ctx);
					if (isClaimExpenseInsideFlag)
						ctx.put("ClaimExpensesTypeFlag", "N");
					else
						ctx.put("ClaimExpensesTypeFlag", "Y");
				}
			}

			Object objProLiabIns = SqlResources
					.getSqlMapProcessor(ctx)
					.findByKey(
							"SqlStmts.UserStatementpdfquotelettergetProfessionalLiabilityInsDetail",
							ctx);
			ctx.put("professionalliabilityinsdetail_freeform_01", objProLiabIns);

			List listFormID = DocumentManagment.getPolicyFormID(ctx);
			ctx.put("stateform_list_01", listFormID);

		} catch (Exception e) {
			logger.error("Problem in fetching data for Quote Number : "
					+ ctx.get("QuoteNumber") + " " + e.getMessage(), e);
		}

		return null;

	}

	public static Object fetchDataForInvoiceDownload(Context ctx)
			throws Exception {

		try {
			ctx.put("PolicyKey_Invoice", ctx.get("PolicyKey"));
			Object objInvoiceData = SqlResources.getSqlMapProcessor(ctx)
					.select("Account.getInvoicedData", ctx);
			if (objInvoiceData != null) {
				ctx.put("InvoiceDataLW", objInvoiceData);
			}
		} catch (Exception e) {
			logger.error("Problem in fetching Invoiced  data for Quote Number : "
					+ ctx.get("QuoteNumber"), e);
		}
		ctx.remove("PolicyKey_Invoice");
		return null;
	}

	public void processDocForESign(IContext ctx) throws Exception {

		HttpServletRequest req = (HttpServletRequest) ctx
				.get("DocumentRequest");
		HttpServletResponse res = (HttpServletResponse) ctx
				.get("DocumentResponse");
		ServletContext servletContext = (ServletContext) ctx
				.get("DocumentServletContext");
		Object objLicenseNumber = SqlResources.getSqlMapProcessor(ctx)
				.findByKey(
						"SqlStmts.UserStatementprocessquotegetLicenseNumber",
						ctx);

		if (objLicenseNumber == null
				|| (objLicenseNumber != null && (((Map) objLicenseNumber)
						.get("LicenseNumber") == null || (((Map) objLicenseNumber)
						.get("LicenseNumber") != null && ""
						.equals(((Map) objLicenseNumber).get("LicenseNumber")
								.toString()))))) {

			try {

				Object objLicenseNumberRequired = RuleUtils.executeRule(ctx,
						"AccountantRule.fillLicenseNumberInsured");
				boolean flagLicenseNumberRequired = false;
				if (objLicenseNumberRequired instanceof Boolean)
					flagLicenseNumberRequired = (Boolean) objLicenseNumberRequired;

				String userno = null;
				try {
					userno = SystemProperties.getInstance().getString(
							"appl." + ctx.getProject() + ".userno");
				} catch (Exception e) {
					logger.error("Error in DocumentGenerationBO.processDocForESign", e);
				}

				if (flagLicenseNumberRequired) {
					Context newCtx = new Context();
					newCtx.setProject(ctx.getProject());
					newCtx.put("PolicyKey", ctx.get("PolicyKey"));
					if (userno == null) {

						Object objUserNo = SqlResources
								.getSqlMapProcessor(ctx)
								.findByKey(
										"SqlStmts.UserStatementprocessquotegetUserNo",
										ctx);
						newCtx.put("UserNo", ((Map) objUserNo).get("UserNo"));
						ctx.put("UserNo", ((Map) objUserNo).get("UserNo"));

					} else {

						newCtx.put("UserNo", userno);
						ctx.put("UserNo", userno);
					}
					Object objUser = SqlResources.getSqlMapProcessor(newCtx)
							.findByKey("UserDetails.findByKey", newCtx);
					if (objUser != null) {
						if (((Map) objUser).get("LicenseNumber") != null) {

							newCtx.put("LicenseNumber",
									((Map) objUser).get("LicenseNumber"));
							// newCtx.put("FirstReviewUserID", "3");

							newCtx.put("FirstReviewUserID",
									((Map) objUser).get("UserNo"));
							newCtx.put("IsLicenseNumberUpdated", "Y");

							SqlResources
									.getSqlMapProcessor(ctx)
									.update("SqlStmts.UserStatementprocessquoteupdateLicenseNumberInsured",
											newCtx);
						}
					}
				}

			} catch (Exception e) {
				logger.error("Error in updating LicenseNumber for Insured------"
						+ e.getMessage(), e);
				throw e;
			}
		}

		String assureURL = processAssureSign((Context) ctx, req, res);

		req.getSession().putValue("isSigned", "N");

		if (assureURL != null && !"".equals(assureURL)) {
			req.getSession().putValue("ESModeCopy", ctx.get("ESModeCopy"));
			req.getSession().removeValue("ESMode");
			req.getSession().putValue("isSigned", "Y");
			res.sendRedirect(assureURL);

		}

	}

	private String processAssureSign(Context ctx, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		{ if (logger.isDebugEnabled()) logger.debug("ES Value: " + ctx.get("ESMode").toString()); }
		{ if (logger.isDebugEnabled()) logger.debug("AccountName: " + ctx.get("AccountName").toString()); }
		{ if (logger.isDebugEnabled()) logger.debug("AccountEmail: " + ctx.get("AccountEmail").toString()); }
		{ if (logger.isDebugEnabled()) logger.debug("PolicyEffectiveDate: "
				+ ctx.get("PolicyEffectiveDate").toString()); }
		ServletContext servletContext = (ServletContext) ctx
				.get("DocumentServletContext");
		String assuresignurl = "";
		Map<String, String> data = new HashMap();

		data.put("AccountName", ctx.get("AccountName").toString());
		data.put("AccountEmail", ctx.get("AccountEmail").toString());
		data.put("PolicyEffectiveDate", ctx.get("PolicyEffectiveDate")
				.toString());
		
		Object objnew = RuleUtils.executeRule(ctx, "AccountantRule.newAppFlow");
		boolean flagnew = false;			
		if(objnew instanceof Boolean)
			flagnew = (Boolean) objnew;
		
		byte[] pdfBytes = null;
		if (ctx.get("PolicyStatusKey") != null
				&& "1".equals(ctx.get("PolicyStatusKey").toString())) {
			ByteArrayOutputStream baos = null;
			
			if(flagnew){
				// New Flow
				OutputStream out = null;
				String htmlDir = SystemProperties.getInstance().getString(
						"html.basedir");
				String outFileQuickQuote = htmlDir
						+ "data//Accountant_Application_"
						+ ctx.get("QuoteNumber").toString() + ".pdf";
				out = new java.io.FileOutputStream(outFileQuickQuote);

				ByteArrayOutputStream bufferout = new java.io.ByteArrayOutputStream();

				String baseUrl = null;
				if (ctx.get("baseUrl") != null)
					baseUrl = ctx.get("baseUrl").toString();

				ServletContextURIResolver uriResolver = null;
				if (ctx.get("uriResolver") != null)
					uriResolver = (ServletContextURIResolver) ctx
							.get("uriResolver");
				baseUrl = "file:///"
						+ ((ServletContext) ctx
								.get(HtmlConstants.DOCUMENTSERVLETCONTEXT))
								.getRealPath("/");
				new DownloadFOP().makePdfNewFlow(ctx, bufferout, baseUrl,
						(ServletContextURIResolver) ctx
								.get(HtmlConstants.DOCUMENTURIRESOLVER),
						servletContext);

				pdfBytes = bufferout.toByteArray();
			} else {
				//Old Flow
				baos = new PrintPDF().makePdf(ctx);
				if (baos != null)
					pdfBytes = baos.toByteArray();
				if (baos != null)
					try {
						baos.close();
					} catch (Exception e) {
						logger.error("Error in DocumentGenerationBO.processAssureSign", e);
						throw e;
					}
			}
			
		} else {
			OutputStream out = null;
			String htmlDir = SystemProperties.getInstance().getString(
					"html.basedir");
			String outFileQuickQuote = htmlDir
					+ "data//Accountant_Application_"
					+ ctx.get("QuoteNumber").toString() + ".pdf";
			out = new java.io.FileOutputStream(outFileQuickQuote);

			ByteArrayOutputStream bufferout = new java.io.ByteArrayOutputStream();

			String baseUrl = null;
			if (ctx.get("baseUrl") != null)
				baseUrl = ctx.get("baseUrl").toString();

			ServletContextURIResolver uriResolver = null;
			if (ctx.get("uriResolver") != null)
				uriResolver = (ServletContextURIResolver) ctx
						.get("uriResolver");
			baseUrl = "file:///"
					+ ((ServletContext) ctx
							.get(HtmlConstants.DOCUMENTSERVLETCONTEXT))
							.getRealPath("/");
			new DownloadFOP().makeRenewPdf(ctx, bufferout, baseUrl,
					(ServletContextURIResolver) ctx
							.get(HtmlConstants.DOCUMENTURIRESOLVER),
					servletContext);

			pdfBytes = bufferout.toByteArray();
		}
		String contextIdentifier = null;
		String userName = null;
		String webServiceUrl = null;
		String returnUrl = null;
		OutputStream out = null;

		try {

			contextIdentifier = SystemProperties.getInstance().getString(
					"appl." + ctx.getProject()
							+ ".assuresign.contextIdentifier");
			userName = SystemProperties.getInstance().getString(
					"appl." + ctx.getProject() + ".assuresign.userName");
			webServiceUrl = SystemProperties.getInstance().getString(
					"esign.ws.url");
			returnUrl = SystemProperties.getInstance().getString(
					"esign.return.url");
			returnUrl = returnUrl + "?jsessionid=" + ctx.get("jsessionid");

			String outFile = SystemProperties.getInstance().getString("html.basedir") + "data//Accountant_Application_" + ctx.get("QuoteNumber").toString() + ".pdf";
			if (pdfBytes == null)
				throw new IllegalStateException("Application PDF generation returned no data");
			out = new java.io.FileOutputStream(outFile);
	        out.write(pdfBytes);
	                
	        int pageNo = AccountUtils.getPageNumber(outFile, "SIGNATURE*");
	        Map<String, Double> xyMap = AccountUtils.getCoordinateXY (outFile, "SIGNATURE*", pageNo);        
	        File file = new File(outFile);
	        if(file.exists())
	        	file.delete();
			
	        String policyStatusKey =  String.valueOf(ctx.get("PolicyStatusKey"));
//				if (ctx.get("PolicyStatusKey") != null
//						&& "1".equals(ctx.get("PolicyStatusKey").toString()))
//					assuresignurl = new EsignUtil().doSignature(contextIdentifier,
//							userName, data, pdfBytes, 5, returnUrl, xyMap);
//				else
					assuresignurl = new EsignUtil().doSignature(contextIdentifier,
							userName, data, pdfBytes, pageNo, returnUrl, xyMap, flagnew,policyStatusKey);
					
			logger.debug("Signing document has completed");

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			logger.debug("Problem in signing document");
		} finally{
			out.close();
		}

		return assuresignurl;
	}

	public static Object fetchDataForRenewApplicationPDF(Context ctx)
			throws Exception {
		Object objPolicy = SqlResources.getSqlMapProcessor(ctx).findByKey(
				"Policy.findByKey", ctx);

		if (objPolicy != null && objPolicy instanceof Map) {
			Map objMap = (Map) objPolicy;

			ctx.put("AccountID", objMap.get("AccountID"));

		}

		Object obj = SqlResources
				.getSqlMapProcessor(ctx)
				.select("SqlStmts.UserStatementrenewapplicationgetAllPolicyOfAnAccount",
						ctx);

		if (obj != null && obj instanceof List) {
			Context previousContext = new Context();

			List policyList = (List) obj;

			if (policyList != null && policyList.size() > 0) {

				Map policyMap = (Map) policyList.get(policyList.size() - 2);

				previousContext.putAll(ctx);
				previousContext.put("PolicyKey", policyMap.get("PolicyKey"));

				Object objMap = SqlResources
						.getSqlMapProcessor(previousContext)
						.findByKey(
								"SqlStmts.UserStatementprocessquotegetPolicyKeys",
								previousContext);
				if (objMap != null && objMap instanceof Map) {

					Map keyMap = (Map) objMap;

					previousContext.put(
							"VersionSequence",
							keyMap.get("VersionSequence") != null ? keyMap
									.get("VersionSequence") : null);

				}
			}

			List aopPreviousList = SqlResources.getSqlMapProcessor(
					previousContext).select(
					"SqlStmts.UserStatementsaveAccountstmtspopulateAOPFields",
					previousContext);
			if (aopPreviousList != null && aopPreviousList.size() > 0) {
				int total = 0;
				for (int i = 0; i < aopPreviousList.size(); i++) {
					Map map = (Map) aopPreviousList.get(i);
					if (map.get("PercentageValue") != null
							&& !map.get("PercentageValue").equals("")) {
						total = total
								+ Integer.parseInt(map.get("PercentageValue")
										.toString());
						ctx.put("AOPOld_PercentageValue_" + map.get("AOPKey"),
								map.get("PercentageValue").toString());
					} else {
						ctx.put("AOPOld_PercentageValue_" + map.get("AOPKey"),
								"0");
					}
					ctx.put("AOPOld_CommentDesc_" + map.get("AOPKey"),
							map.get("AOPCommentDesc"));
				}

				ctx.put("aopold_total", total);
			}

			List aoplist = SqlResources.getSqlMapProcessor(ctx).select(
					"SqlStmts.UserStatementsaveAccountstmtspopulateAOPFields",
					ctx);
			if (aoplist != null && aoplist.size() > 0) {
				int total = 0;
				for (int i = 0; i < aoplist.size(); i++) {
					Map map = (Map) aoplist.get(i);
					if (map.get("PercentageValue") != null
							&& !map.get("PercentageValue").equals("")) {
						total = total
								+ Integer.parseInt(map.get("PercentageValue")
										.toString());
						ctx.put("AOP_PercentageValue_" + map.get("AOPKey"), map
								.get("PercentageValue").toString());
					} else {
						ctx.put("AOP_PercentageValue_" + map.get("AOPKey"), "0");
					}
					ctx.put("AOP_CommentDesc_" + map.get("AOPKey"),
							map.get("AOPCommentDesc"));
				}

				ctx.put("aop_total", total);
			}
		}

		Object objAddress = SqlResources.getSqlMapProcessor(ctx).findByKey(
				"SqlStmts.UserStatementpdfquotelettergetAddressdetails", ctx);
		ctx.put("address_freeform_01", objAddress);

		Object objPoilcy = SqlResources.getSqlMapProcessor(ctx).findByKey(
				"SqlStmts.UserStatementpdfquotelettergetPriorPolicyInfo", ctx);
		ctx.put("policy_freeform_01", objPoilcy);

		Object objFirm = SqlResources.getSqlMapProcessor(ctx).findByKey(
				"Firm.findByKey", ctx);
		ctx.put("firm_freeform_01", objFirm);
		
		Object objaboutfrim = SqlResources.getSqlMapProcessor(ctx).findByKey(
				"SqlStmts.aboutFirmviewgetFirmData", ctx);
		ctx.put("AboutFirm_FREEFORM_01", objaboutfrim);
		
		Object listPersonnelAffiliation = SqlResources.getSqlMapProcessor(ctx)
				.select("SqlStmts.BasicInfoviewmomfirmaffiliates", ctx);
		ctx.put(PDFConstants.BASICINFO_LIST_01, listPersonnelAffiliation);

		if (listPersonnelAffiliation != null
				&& listPersonnelAffiliation instanceof List) {
			int totalPersonnel = 0;
			List list = (List) listPersonnelAffiliation;

			for (int i = 0; i < list.size(); i++) {

				Map objPersonnel = (Map) list.get(i);
				if (objPersonnel.get("PersonnelTypeNumbering") != null
						&& !"".equals(objPersonnel
								.get("PersonnelTypeNumbering").toString())) {
					int personnelValue = AccountantValidationUtils
							.convertStringToInteger(objPersonnel
									.get("NumberOfPersonnel") != null ? objPersonnel
									.get("NumberOfPersonnel").toString() : "");
					totalPersonnel = totalPersonnel + personnelValue;
				}

			}

			ctx.put("TotalPersonnel", totalPersonnel);

		}

		Object listGrossRevenueForFirm = SqlResources.getSqlMapProcessor(ctx)
				.select("SqlStmts.BasicInfoviewmomgrossrevenue", ctx);
		ctx.put(PDFConstants.BASICINFO_LIST_03, listGrossRevenueForFirm);

		if (listGrossRevenueForFirm != null
				&& listGrossRevenueForFirm instanceof List) {

			List grossList = (List) listGrossRevenueForFirm;

			Map o = (Map) grossList.get(0);

			String year = o.get("YearEndDate") != null ? o.get("YearEndDate")
					.toString() : "";
			String amount = o.get("Amount") != null ? o.get("Amount")
					.toString() : "0";
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

		Object listFeeSuitSupp = SqlResources.getSqlMapProcessor(ctx).select(
				"SqlStmts.RenewBasicInfoviewgetFeesSuedList", ctx);
		ctx.put(PDFConstants.RENEWBASICINFO_LIST_FEESUIT, listFeeSuitSupp);

		// Investment/Financial Planning Supplement
		Object objFinancialPlanningAndIAServiceSupp = SqlResources
				.getSqlMapProcessor(ctx).findByKey(
						"FinancialPlanningAndIAServiceSupp.findByKey", ctx);
		ctx.put(PDFConstants.INVEST_FINAN_PLANING_FREEFORM_01,
				objFinancialPlanningAndIAServiceSupp);

		// Audit Supplement
		Object objAttestation = SqlResources.getSqlMapProcessor(ctx).findByKey(
				"Attestation.findByKey", ctx);
		ctx.put(PDFConstants.ATTESTATION_FREEFORM_02, objAttestation);
		
		if (objAttestation != null && objAttestation instanceof Map) {
			Map mp = (Map) objAttestation;
			ctx.put("OtherAuditAttesdesc" ,mp.get("AuditComment"));
			
		}
		
		Object listAttestationAuditWork = SqlResources.getSqlMapProcessor(ctx)
				.select("SqlStmts.UserStatementManualBoQueriesAttestationAuditWorkNewPDF", ctx);
		ctx.put(PDFConstants.ATTESTATION_LIST_03, listAttestationAuditWork);

		// Computer Releated Services
		Object listITServicesSupp = SqlResources.getSqlMapProcessor(ctx)
				.select("SqlStmts.computer-servicesviewgetITServicesSupp", ctx);
		ctx.put(PDFConstants.COMPUTERSERVICE_LIST_03, listITServicesSupp);

		// Business Management Supplement
		Object objFundsControlledSupp = SqlResources.getSqlMapProcessor(ctx)
				.findByKey("FundsControlledSupp.findByKey", ctx);
		ctx.put(PDFConstants.GENERAL_FREEFORM_03, objFundsControlledSupp);
		
		if (objFundsControlledSupp != null
				&& objFundsControlledSupp instanceof Map) {
				
			Map mp = (Map) objFundsControlledSupp;
				String TotalAmountOfClientFundsFcFormat= mp.get("TotalAmountOfClientFundsFc") != null ? mp.get("TotalAmountOfClientFundsFc").toString() : "0"; 
				
				TotalAmountOfClientFundsFcFormat = PDFGenerator.formatter.format(Long.parseLong(TotalAmountOfClientFundsFcFormat));
				TotalAmountOfClientFundsFcFormat = DownloadFOP.formateAmountString(TotalAmountOfClientFundsFcFormat);
				
				ctx.put("TotalAmountOfClientFundsFcFormat", TotalAmountOfClientFundsFcFormat);
		}

		Object objClientServiceDescFundControl = SqlResources
				.getSqlMapProcessor(ctx)
				.findByKey(
						"SqlStmts.UserStatementsaveAccountstmtsgeClientServiceDescFundControl",
						ctx);
		ctx.put(PDFConstants.CLIENTSERVICEDESC_FUNDCONTROL_FREEFORM_01,
				objClientServiceDescFundControl);

		Object objClientServiceDescFirm = SqlResources
				.getSqlMapProcessor(ctx)
				.findByKey(
						"SqlStmts.UserStatementsaveAccountstmtsgeClientServiceDescFirm",
						ctx);
		ctx.put(PDFConstants.CLIENTSERVICEDESC_FIRM_FREEFORM_01,
				objClientServiceDescFirm);

		new AccountUtils().getAverageRevenueRenewApplication(ctx);
		new AccountantValidationUtils().hideShowSupplementOnRenewPDF(ctx);
		
		Object isShowCyberQuestion = RuleUtils.executeRule(ctx, "AccountantRule.isShowCyberQuestion_Renewal");
		boolean cyberImplFlag = false;			
		if(isShowCyberQuestion instanceof Boolean)
			cyberImplFlag = (Boolean) isShowCyberQuestion;
		
		ctx.put("CYBERIMPLFLAG", cyberImplFlag);

		return null;
	}

	public static Object fetchDataForQuoteLetter(Context ctx, String filename) {
		try {
			String homePath = SystemProperties.getInstance().getString(
					"appl.home.dir");
			String checkboxYPath = homePath + "image//check-btn6.png";
			String checkboxNPath = homePath + "image//check-btn4.png";
			String cfLogoPath = homePath + "image//crum_logo.png";
			String protexureaccountantsLogoPath = homePath
					+ "image//logo_protexureaccountants1.png";
			String headerBottomPath = homePath + "image//header_bottom.png";
			String spacerPath = homePath + "image//spacer.png";
			ctx.put("checkboxYPath", checkboxYPath);
			ctx.put("checkboxNPath", checkboxNPath);
			ctx.put("cfLogoPath", cfLogoPath);
			ctx.put("protexureaccountantsLogoPath",
					protexureaccountantsLogoPath);
			ctx.put("headerBottomPath", headerBottomPath);
			ctx.put("spacerPath", spacerPath);
			Object objPoilcy = SqlResources.getSqlMapProcessor(ctx).findByKey(
					"SqlStmts.UserStatementpdfquotelettergetPriorPolicyInfo",
					ctx);
			ctx.put("policy_freeform_01", objPoilcy);

			Object objAddress = SqlResources.getSqlMapProcessor(ctx).findByKey(
					"SqlStmts.UserStatementpdfquotelettergetAddressdetails",
					ctx);
			ctx.put("address_freeform_01", objAddress);

			// Object objCoveragePoilcy =
			// SqlResources.getSqlMapProcessor(ctx).findByKey("SqlStmts.UserStatementpdfquoteletterCoveragePolicy",
			// ctx);
			// ctx.put("policycoverage_freeform_01", objCoveragePoilcy);

			Object listQuoteOption = SqlResources
					.getSqlMapProcessor(ctx)
					.select("SqlStmts.UserStatementpdfquotelettergetQuoteOptedList",
							ctx);
			ctx.put("quoteOpted_list_01", listQuoteOption);
			listQuoteOption = SqlResources.getSqlMapProcessor(ctx).select("SqlStmts.UserStatementpdfquotelettergetQuoteOptedListNew",ctx);
			ctx.put("quoteOptedUpdated_list_01", listQuoteOption);
			Object objProLiabIns = SqlResources
					.getSqlMapProcessor(ctx)
					.findByKey(
							"SqlStmts.UserStatementpdfquotelettergetProfessionalLiabilityInsDetail",
							ctx);
			ctx.put("professionalliabilityinsdetail_freeform_01", objProLiabIns);

			Object objFooterTime = SqlResources.getSqlMapProcessor(ctx).select(
					"SqlStmts.UserStatementpdfquotelettergetQuoteFooterTime",
					ctx);
			ctx.put("time_freeform_01", objFooterTime);

			Object listStateForm = SqlResources.getSqlMapProcessor(ctx).select(
					"SqlStmts.UserStatementManualBoQueriesPolicyFormLW", ctx);
			ctx.put("stateform_list_01", listStateForm);

			if (filename.equals("quoteletter")) {
				ctx.put("quoteletterfilename", "QuoteLetterView");
			} else {
				ctx.put("quoteletterfilename", "QuoteLetterSend");
			}

		} catch (Exception e) {
			logger.error("Problem in fetching data for Quote Number : "
					+ ctx.get("QuoteNumber") + " " + e.getMessage(), e);
		}

		return null;
	}

	public static Object downloadAndPrintOffRiskDocs(IContext ctx) {

		try {
			OffRiskAccountantUtils offrisk = new OffRiskAccountantUtils();
			offrisk.OffRiskUpload(ctx);
			offrisk.downloadOffRiskLetter(ctx);
		} catch (Exception e) {
			logger.error("Error in DocumentGenerationBO.downloadAndPrintOffRiskDocs", e);

		}
		return null;
	}
	
	public static void downloadExcelReport(Context ctx)
	{	
		HttpServletRequest req = (HttpServletRequest) ctx.get("DocumentRequest");
		HttpServletResponse res = (HttpServletResponse) ctx.get("DocumentResponse");
		res.setContentType("application/vnd.ms-excel");
		String newFileName = "Account_"+ctx.get("QuoteNumber")+".xls";
		res.setHeader("Content-disposition","attachment; filename=\"" + newFileName + "\"");		
		
		try 
		{
			new Download_Excel().downloadExcel(ctx, req, res);
		} 
		catch (Exception e)
		{
			logger.error("Error in DocumentGenerationBO.downloadExcelReport", e);
		}				
			
	}
	
	//Endorsement Start
	public static Object fetchDataForPolicyEndorsement(Context ctx) {
		HttpServletRequest req=(HttpServletRequest)ctx.get("DocumentRequest");
		HttpServletResponse res=(HttpServletResponse)ctx.get("DocumentResponse");
		ServletContext servletContext=(ServletContext)ctx.get("DocumentServletContext");
		logger.debug("View endorsement started");
		List attachments  = new ArrayList();		
		OutputStream out = null;
		File pdfFile = null;
		List listFormID=null;
		String fileName = null, file = null,baseUrl = null, outFile=null;
		if(ctx.get("TransactionTypeKey") == null) {
			logger.error("Endorsement Type for Policy Number : " + ctx.get("PolicyNumber") + " was not defined");
			return null;
		}
		
		try {			
			new AccountUtils().setImagesPathToTemplate(ctx);			
			
			String TransactionTypeKey = ctx.get("TransactionTypeKey").toString();
			//Populating all keys in Context
			Object obj = SqlResources.getSqlMapProcessor(ctx).findByKey("SqlStmts.UserStatementManualBoQueriesGetPolicyKeys", ctx);
			Map keysMap = null;		
			if(obj != null && obj instanceof Map){
				keysMap = (Map) obj;
				ctx.putAll(keysMap);
			}
			
			Object objPoilcy = SqlResources.getSqlMapProcessor(ctx).findByKey("SqlStmts.UserStatementManualBoQueriesGetPolicyEndorsment", ctx);
			ctx.put("policy_freeform_01", objPoilcy);
			
			Object objCoveragePoilcyNew = SqlResources.getSqlMapProcessor(ctx).findByKey("SqlStmts.UserStatementManualBoQueriesGetEndorsementCoverage", ctx);
			if(objCoveragePoilcyNew != null)
				ctx.put("PolicyExpirationDate", ((Map) objCoveragePoilcyNew).get("PolicyExpirationDate"));	
			
			Object objQuote = SqlResources.getSqlMapProcessor(ctx).findByKey("SqlStmts.UserStatementManualBoQueriesgetCalculateLimitDeductiblePremium", ctx);
			if(objQuote != null)
				ctx.put("ProrataPremium", ((Map) objQuote).get("ProrataPremium"));
				
			Map map = new HashMap();
			List list = new ArrayList();
			list.add(map);
			
			Map mapEnd = new HashMap();
			List listEnd = new ArrayList();
			listEnd.add(mapEnd);
			Map mapEndNew = new HashMap();
			List listEndNew = new ArrayList();
			listEndNew.add(mapEndNew);		
			if(TransactionTypeKey.equals(com.userbo.AccountantConstants.CANCELLATION_ENDORSEMENT)){
				//ctx.put("endorsementname", "CancellationEndorsement");				
				if(ctx.get("cancellationReason") != null)
					map.put("CancellationReasonID", ctx.get("cancellationReason").toString());									
				if(ctx.get("CancellationEffectiveDate") != null)
					map.put("TransactionEffectiveDate", ctx.get("CancellationEffectiveDate").toString());
				if(ctx.get("cancellationTotalReturnPremium") != null)
					map.put("InvoicedPremium", AccountUtils.amountFormatLawyers(ctx.get("cancellationTotalReturnPremium")));
				ctx.put("endorsement_freeform_01",list);
			} else if(TransactionTypeKey.equals(com.userbo.AccountantConstants.REINSTATEMENT_ENDORSEMENT)){
				//ctx.put("endorsementname", "ReinstatmentEndorsement");				
				if(ctx.get("ReinstatementEffectiveDate") != null)
					map.put("TransactionEffectiveDate", ctx.get("ReinstatementEffectiveDate").toString());
				if(ctx.get("reinstatementReturnPremium") != null)
					map.put("InvoicedPremium", AccountUtils.amountFormatLawyers(ctx.get("reinstatementReturnPremium")));
				ctx.put("endorsement_freeform_01",list);
			} else if(TransactionTypeKey.equals(com.userbo.AccountantConstants.LIMIT_ENDORSEMENT)){
				//ctx.put("endorsementname", "LimitEndorsement");
				ctx.put("TypeOfEndorsement","9");
				if(ctx.get("AggregateLimit") != null)
					map.put("AggregateLimitText", AccountUtils.amountFormatLawyers(ctx.get("AggregateLimit")));
				if(ctx.get("OccuranceLimit") != null)
					map.put("OccuranceLimitText", AccountUtils.amountFormatLawyers(ctx.get("OccuranceLimit")));	
				/*if(ctx.get("litmitsEndorsementTotalPremium") != null){
					map.put("InvoicedPremium", AccountUtils.amountFormatLawyers(ctx.get("litmitsEndorsementTotalPremium")));
					ctx.put("NewInvoicedPremium", AccountUtils.amountFormatLawyers(ctx.get("litmitsEndorsementTotalPremium")));
				}*/
				if(ctx.get("ProrataPremium") != null){
					map.put("InvoicedPremium", AccountUtils.amountFormatLawyers(ctx.get("ProrataPremium")));
					ctx.put("NewInvoicedPremium", ctx.get("ProrataPremium"));
				}
				// Need LimitSequence, IsClaimExpenseType, IsDollarDefense				
				if(ctx.get("LimitSequence") != null)
					map.put("LimitSequence", ctx.get("LimitSequence").toString());
				if(ctx.get("IsClaimExpenseType") != null)
					map.put("IsClaimExpensesType", ctx.get("IsClaimExpenseType").toString());
				if(ctx.get("IsDollarDefense") != null)
					map.put("IsDollarDefense", ctx.get("IsDollarDefense").toString());
				
				if(ctx.get("limitEndorsementEffectiveDate") != null)
					map.put("TransactionEffectiveDate", ctx.get("limitEndorsementEffectiveDate").toString());
				if(ctx.get("PolicyExpirationDate") != null)
					map.put("PolicyExpirationDate", ctx.get("PolicyExpirationDate").toString());
				
				ctx.put("policycoverage_freeform_01",list);
				ctx.put("endorsement_freeform_01",listEnd);				
			} else if(TransactionTypeKey.equals(com.userbo.AccountantConstants.DEDUCTIBLE_ENDORSEMENT)){
				//ctx.put("endorsementname", "DeductibleEndorsement");
				ctx.put("TypeOfEndorsement","10");
				if(ctx.get("AggregateDeductible") != null)
					map.put("DeductibleAmount", AccountUtils.amountFormatLawyers(ctx.get("AggregateDeductible")));
				/*if(ctx.get("deductibleEndorsementPremium") != null){					
					ctx.put("NewInvoicedPremium", AccountUtils.amountFormatLawyers(ctx.get("deductibleEndorsementPremium")));
				}*/
				if(ctx.get("ProrataPremium") != null){					
					ctx.put("InvoicedPremium", AccountUtils.amountFormatLawyers(ctx.get("ProrataPremium")));
					ctx.put("NewInvoicedPremium", ctx.get("ProrataPremium"));
				}
				// Need DeductibleSequence, IsClaimOptionType				
				if(ctx.get("DeductibleSequence") != null)
					map.put("DeductibleSequence", ctx.get("DeductibleSequence").toString());
				if(ctx.get("IsClaimOptionType") != null)
					map.put("IsClaimOptionType", ctx.get("IsClaimOptionType").toString());
				
				if(ctx.get("deductibleEndorsementEffectiveDate") != null)
					map.put("TransactionEffectiveDate", ctx.get("deductibleEndorsementEffectiveDate"));
				if(ctx.get("PolicyExpirationDate") != null)
					map.put("PolicyExpirationDate", ctx.get("PolicyExpirationDate").toString());
				
				ctx.put("policycoverage_freeform_01",list);
				ctx.put("endorsement_freeform_01",listEnd);
			} else if(TransactionTypeKey.equals(com.userbo.AccountantConstants.NAMEADDRESS_ENDORSEMENT)){
				//ctx.put("endorsementname", "ReinstatmentEndorsement");	
				ctx.put("TypeOfEndorsement","11");
				Map policyMap=(Map)ctx.get("policy_freeform_01");
				
				if(ctx.get("nameAddressEndorsementDate") != null) {
					mapEnd.put("TransactionEffectiveDate", ctx.get("nameAddressEndorsementDate").toString());
					ctx.put("endorsement_freeform_01",listEnd);
				}
				if(ctx.get("nameAddressEndorsementAccountName") != null)
					policyMap.put("AccountName", ctx.get("nameAddressEndorsementAccountName").toString());
				if(ctx.get("nameAddressEndorsementAddress1") != null)
					policyMap.put("Address1", ctx.get("nameAddressEndorsementAddress1").toString());
				if(ctx.get("nameAddressEndorsementAddress2") != null)
					policyMap.put("Address2", ctx.get("nameAddressEndorsementAddress2").toString());
				if(ctx.get("nameAddressEndorsementCity") != null)
					policyMap.put("City", ctx.get("nameAddressEndorsementCity").toString());
				if(ctx.get("nameAddressEndorsementZipCode") != null) {
					String zipCode=ctx.get("nameAddressEndorsementZipCode").toString();
					if(zipCode.length()<7)
						zipCode=zipCode.replace("-", "");
						policyMap.put("Zip", zipCode);
				}
			if(ctx.get("nameAddressEndorsementDate") != null)
					map.put("TransactionEffectiveDate", ctx.get("nameAddressEndorsementDate"));
				map.put("PolicyExpirationDate", ctx.get("PolicyExpirationDate"));
				ctx.put("policycoverage_freeform_01",list);
				ctx.put("policy_freeform_01", policyMap);
			} else if(TransactionTypeKey.equals(com.userbo.AccountantConstants.PRIORACTDATE_ENDORSEMENT)) {
				if(ctx.get("ProrataPremium") != null){
					map.put("InvoicedPremium", AccountUtils.amountFormatLawyers(ctx.get("ProrataPremium")));
					ctx.put("NewInvoicedPremium", ctx.get("ProrataPremium"));
				}
				ctx.put("TypeOfEndorsement","12");
				String isPremiumCalculated=ctx.get("premiumCalulated")!=null && !ctx.get("premiumCalulated").equals(HtmlConstants.EMPTY) ?ctx.get("premiumCalulated").toString():"N";
				
				ctx.put("isPremiumCalculated",isPremiumCalculated);
				
				if(ctx.get("priorActEndorsementDate") != null)
					map.put("TransactionEffectiveDate", ctx.get("priorActEndorsementDate"));
				map.put("PolicyExpirationDate", ctx.get("PolicyExpirationDate"));
				
				ctx.put("policycoverage_freeform_01",list);
				//if(ctx.get())
				Map objPriorActDetail = (Map) SqlResources.getSqlMapProcessor(ctx).findByKey("SqlStmts.UserStatementpdfquotelettergetProfessionalLiabilityInsDetail", ctx);
				//ctx.put("professionalliabilityinsdetail_freeform_01", objPriorActDetail);
				//List dataList=new ArrayList();
			//	Map map1=(Map) objPriorActDetail.get(0);
				
				objPriorActDetail.put("PriorActDatePross", ctx.get("priorActEndorsementPriorActDate"));
				//((List) objPriorActDetail).add(map1);
				ctx.put("professionalliabilityinsdetail_freeform_01",objPriorActDetail);
			} else if(TransactionTypeKey.equals(com.userbo.AccountantConstants.POLICYEXTENSION_ENDORSEMENT)) {
				//policy_freeform_01
				if(ctx.get("objCoveragePoilcyNew")==null)
				{	
					if(ctx.get("policyExtendEndorsementEffectiveDate") != null)
						map.put("TransactionEffectiveDate", ctx.get("policyExtendEndorsementEffectiveDate").toString());
					if(ctx.get("policyExtendExpirationDate") != null)
						map.put("PolicyExpirationDate", ctx.get("policyExtendExpirationDate").toString());
					ctx.put("policycoverage_freeform_01",list);
				}
				
				
				if(ctx.get("ProrataPremium") != null){
					map.put("InvoicedPremium", AccountUtils.amountFormatLawyers(ctx.get("ProrataPremium")));
					ctx.put("NewInvoicedPremium", ctx.get("ProrataPremium"));
				}
				ctx.put("TypeOfEndorsement","13");
			}
			new DownloadForm().processEndorsementData(ctx);
			
			
			if(ctx.get("listFormID") != null){
				listFormID = (List)ctx.get("listFormID");
				if(!listFormID.isEmpty()){
					if("3".equals(ctx.get("CompanyKey").toString()))
						file = "AAA";
					else
						 file = "APL";
					for(int i=0; i<listFormID.size(); i++)
						file = file +"_" + (String) listFormID.get(i);
					fileName = file;
				}
			}
			if(fileName == null)
				fileName ="APL_DEFAULT";
			
			ctx.put("endorsementname", fileName);
			outFile = SystemProperties.getInstance().getString("html.basedir") + "data//" + fileName +".pdf";		
			pdfFile = new File(outFile);
			out = new java.io.FileOutputStream(pdfFile);
			out = new java.io.BufferedOutputStream(out);			
			if(ctx.get("baseUrl") != null)
				baseUrl = ctx.get("baseUrl").toString();

			if(ctx.get(HtmlConstants.DOCUMENTSERVLETCONTEXT) != null)
				baseUrl = "file:///" + ((ServletContext)ctx.get(HtmlConstants.DOCUMENTSERVLETCONTEXT)).getRealPath("/");
			
			ServletContextURIResolver uriResolver = null;
			
			if(ctx.get("DocumentUriResolver") != null)
				uriResolver = (ServletContextURIResolver)ctx.get(HtmlConstants.DOCUMENTURIRESOLVER);
			
			new DownloadForm().generateEndorsement((Context)ctx, out, baseUrl, uriResolver);		
			new Download_Excel().downloadEndorsementPdf(ctx, req, res,outFile,fileName);
			/*String foFile = SystemProperties.getInstance().getString("html.basedir") + "foxsl2017//WrapEndorsementAcc.xsl";
			String quoteNumber=ctx.get("QuoteNumber").toString();
			String outFile = SystemProperties.getInstance().getString("html.basedir") + "data//Endorsement_Form_"+quoteNumber+".pdf";		
			File pdfFile = new File(outFile);
			OutputStream out = new java.io.FileOutputStream(pdfFile);
			out = new java.io.BufferedOutputStream(out);
			new XML2PDF().convertPOToPDF(foFile, new StringBuffer(new DownloadForm().generateDataXml(ctx)), outFile);
			*/
		} catch (Exception e) {
			logger.error("Problem in fetching data for Policy Number : "	+ ctx.get("PolicyNumber") + " " + e.getMessage(), e);
		}
		logger.debug("fetchDataForPolicyEndorsement end  ........");		
		return null;
	}
	
	public static void setDataForInsurenceCertificate(Context ctx)
	{
		try
		{
			List dataList= (List) SqlResources.getSqlMapProcessor(ctx).select("SqlStmts.UserStatementManualBoQueriesgetInsuranceCertificateData",ctx);
			ctx.putAll((Map) dataList.get(0));
			logger.debug("certificate data successfully fetched from database and populated in context");
			/*AccountUtils.downloadInsurenceCertificate((IContext)ctx);*/
		}
		catch(Exception e)
		{
			logger.error("exception in generating Insurance certificate", e);
		}
	}
	
	public static Object fetchDataForNewApplicationPDF(Context ctx)
			throws Exception {
			
			Object objAccount = SqlResources.getSqlMapProcessor(ctx).findByKey(
				"Account.findByKey", ctx);
		ctx.put("ACCOUNT_FREEFORM_01", objAccount);

		Object objAddress = SqlResources.getSqlMapProcessor(ctx).findByKey(
				"SqlStmts.BasicInfoviewgetAddressdetails", ctx);
		ctx.put("ADDRESS_FREEFORM_01", objAddress);

		Object objPolicy = SqlResources.getSqlMapProcessor(ctx).findByKey(
				"Policy.findByKey", ctx);
		ctx.put("POLICY_FREEFORM_01", objPolicy);
		
		if (objPolicy != null
				&& objPolicy instanceof Map) {
			
				Map o = (Map) objPolicy;
				String pdate=o.get("PolicyEffectiveDate").toString();
				Date dt = com.util.DateUtils.convertStringToDate(pdate, "MM/dd/yyyy");
				{ if (logger.isDebugEnabled()) logger.debug(dt.toString()); }
				String policyeffectivedate = com.util.DateUtils.convertDateToString(dt, "MM/dd/yyyy");
				
				ctx.put("PolicyEffectiveDate", policyeffectivedate);
				}
		
		
		Object objaboutfrim = SqlResources.getSqlMapProcessor(ctx).findByKey(
				"SqlStmts.aboutFirmviewgetFirmData", ctx);
		ctx.put("AboutFirm_FREEFORM_01", objaboutfrim);
		
		Object objFirm = SqlResources.getSqlMapProcessor(ctx).findByKey(
				"Firm.findByKey", ctx);
		ctx.put("FIRM_FREEFORM_01", objFirm);

		Object objFinalisedcount = SqlResources.getSqlMapProcessor(ctx)
				.findByKey("SqlStmts.BasicInfoviewgetFinalisedcount", ctx);

		Object objPolicyCoverage = null;
		if (objFinalisedcount != null) {
			int finalisesQuotes = Integer.parseInt(((Map) objFinalisedcount)
					.get("FinalisedOptionCount").toString());
			if (finalisesQuotes > 0) {
				objPolicyCoverage = SqlResources
						.getSqlMapProcessor(ctx)
						.findByKey(
								"SqlStmts.UserStatementpdfquotelettergetFinalisedCoveragePolicy",
								ctx);
			} else {
				objPolicyCoverage = SqlResources
						.getSqlMapProcessor(ctx)
						.findByKey(
								"SqlStmts.UserStatementpdfquoteletterCoveragePolicy",
								ctx);
			}

		}

		ctx.put("POLICYCOVERAGE_FREEFORM_01", objPolicyCoverage);

		Object objProfessionalLiabilityInsDetail = SqlResources
				.getSqlMapProcessor(ctx).findByKey(
						"ProfessionalLiabilityInsDetail.findByKey", ctx);
		ctx.put("PROFESSIONALLIABILITYINS_FREEFORM_01",
				objProfessionalLiabilityInsDetail);
		
		Object listProfessionalLiabilityInsDetail =SqlResources.getSqlMapProcessor(ctx).findByKey("SqlStmts.UserStatementManualBoQueriesgetProfessionalLiabilityInsDetailFormat", ctx);
		ctx.put("PROFESSIONALLIABILITYINS_FREEFORM_01_Format", listProfessionalLiabilityInsDetail);
		
		Object objLimitDeductibleProfessionalLiabilityInsDetail = SqlResources
				.getSqlMapProcessor(ctx)
				.findByKey(
						"SqlStmts.UserStatementsaveAccountstmtsgeLimitDeductibleProfessionalLiabilityInsDetail",
						ctx);
		ctx
				.put(
						"LIMITDEDUCTIBLEPROFESSIONALLIABILITYINS_FREEFORM_01",
						objLimitDeductibleProfessionalLiabilityInsDetail);

		Object listClaimIncidentSupp = SqlResources.getSqlMapProcessor(ctx)
				.select("SqlStmts.BasicInfoviewgetClaimIncidentSupp", ctx);
		ctx.put("BASICINFO_LIST_04", listClaimIncidentSupp);
		
		if((ctx.get("CompanyKey") instanceof Integer ? (Integer)ctx.get("CompanyKey") == 3 : "3".equals(ctx.get("CompanyKey"))) && !"Issued".equals(ctx.get("StatusDesc")) && (listClaimIncidentSupp == null || ((List)listClaimIncidentSupp).size() < 1 || ((List)listClaimIncidentSupp).isEmpty())) {
			List listClaimIncidentSuppList = new ArrayList();
			Map listClaimIncidentSuppMap = new HashMap();
			listClaimIncidentSuppMap.put("NameOfClaimantCi", "");
			listClaimIncidentSuppMap.put("IsClientCi", "");
			listClaimIncidentSuppMap.put("IsClaimInSuit", "");
			listClaimIncidentSuppMap.put("ClaimNotifiedCiDate", "");
			listClaimIncidentSuppMap.put("AllegedErrorCiDate", "");
			listClaimIncidentSuppMap.put("NameOfFirmPersonnelCi", "");
			listClaimIncidentSuppMap.put("DescOfClaimCi", "");
			listClaimIncidentSuppMap.put("IsThisClaimReportedCi", "");
			listClaimIncidentSuppMap.put("NameOfInsuranceCompanyCi", "");
			listClaimIncidentSuppMap.put("ReportedCiDate", "");
			listClaimIncidentSuppMap.put("InsurerLoss", "");
			listClaimIncidentSuppMap.put("ClaimantLastDemandCi", "");
			listClaimIncidentSuppMap.put("CurrentStatus", "");
			listClaimIncidentSuppMap.put("ClosingCiDate", "");
			listClaimIncidentSuppMap.put("TotalClaimExpensesCi", "");
			listClaimIncidentSuppMap.put("StepsTakenToPreventClaimCi", "");
			listClaimIncidentSuppList.add(listClaimIncidentSuppMap);
			ctx.put("BASICINFO_LIST_04", listClaimIncidentSuppList);
		}
		
		Object listPersonnelAffiliation = SqlResources.getSqlMapProcessor(ctx)
				.select("SqlStmts.BasicInfoviewmomfirmaffiliates", ctx);
		ctx.put("BASICINFO_LIST_01", listPersonnelAffiliation);

		Object listGrossRevenueForFirm = SqlResources.getSqlMapProcessor(ctx)
				.select("SqlStmts.BasicInfoviewmomgrossrevenue", ctx);
		ctx.put("BASICINFO_LIST_03", listGrossRevenueForFirm);
		
		if (listGrossRevenueForFirm != null
				&& listGrossRevenueForFirm instanceof List) {

			List grossList = (List) listGrossRevenueForFirm;
			for(int i=0;i<grossList.size();i++){
				Map o = (Map) grossList.get(i);

				String year = o.get("YearEndDate") != null ? o.get("YearEndDate")
						.toString() : "";
				String amount = o.get("Amount") != null ? o.get("Amount")
						.toString() : "0";
			  ctx.put("GrossAmount_"+i, amount);
				amount = PDFGenerator.formatter.format(Long.parseLong(amount));
				amount = DownloadFOP.formateAmountString(amount);
				ctx.put("YearEndDate_"+i, year);

				ctx.put("Amount_"+i, amount);
				}
		}
		
		Object listPublicPrivateOfferingSupp = SqlResources.getSqlMapProcessor(
				ctx).select(
				"SqlStmts.BasicInfoviewListPublicPrivateOfferingSupp", ctx);
		ctx.put("BASICINFO_LIST_06", listPublicPrivateOfferingSupp);

		ctx.put("PolicyKey", Integer.parseInt(ctx.get("PolicyKey").toString()));
		ctx.put("VersionSequence", Integer.parseInt(ctx.get("VersionSequence").toString()));
//		Object listAOP = SqlResources.getSqlMapProcessor(ctx).select(
//				"SqlStmts.UserStatementsaveAccountstmtspopulateAOPFields", ctx);
		Object listAOP = SqlResources.getSqlMapProcessor(ctx).select("AreaOfPractice.GETAOPFIELDS", ctx);
		ctx.put("PolicyKey",ctx.get("PolicyKey").toString());
		ctx.put("VersionSequence", ctx.get("VersionSequence").toString());
		
		ctx.put("BASICINFO_LIST_AOP", listAOP);

		if (listAOP != null && ((List)listAOP).size() > 0) {
			List aoplist = (List) listAOP;
			Map map = new HashMap();
			for (int i = 0; i < aoplist.size(); i++) {
				
				int total = 0;
				for (int ii = 0; ii < aoplist.size(); ii++) {
					Map map1 = (Map) aoplist.get(ii);
					if (map1.get("PercentageValue") != null
							&& !map1.get("PercentageValue").equals("")) {
						total = total
								+ Integer.parseInt(map1.get("PercentageValue")
										.toString());
						ctx.put("AOP_PercentageValue_" + map1.get("AOPKey"), map1
								.get("PercentageValue").toString());
					} else {
						ctx.put("AOP_PercentageValue_" + map1.get("AOPKey"), "0");
					}
					ctx.put("AOP_CommentDesc_" + map1.get("AOPKey"),
							map1.get("AOPCommentDesc"));
				}

				ctx.put("aop_total", total);
			
			}
		} else {
			ctx.put("aop_total", "0");
		}

		ctx.put("OLD_UserNo", ctx.get("UserNo"));
		ctx.put("UserNo", SystemProperties.getInstance().getString(
				"appl." + ctx.getProject() + ".userno"));

		Object objUser = SqlResources.getSqlMapProcessor(ctx).findByKey(
				"UserDetails.findByKey", ctx);
		ctx.put("USER_FREEFORM_01", objUser);
		ctx.put("UserNo", ctx.get("OLD_UserNo"));
		
		Object objFirmlabel = SqlResources.getSqlMapProcessor(ctx).findByKey(
				"SqlStmts.UserStatementpdfquotelettergetPriorPolicyInfo", ctx);
		ctx.put("LABELS_FREEFORM_01", objFirmlabel);

		Object objSeperateEntitySupp = SqlResources.getSqlMapProcessor(ctx)
				.findByKey("SeperateEntitySupp.findByKey", ctx);
		ctx.put("GENERAL_FREEFORM_02", objSeperateEntitySupp);

	/*	Object objSeperateEntitySupp = SqlResources.getSqlMapProcessor(ctx).findByKey("SqlStmts.UserStatementManualBoQueriesgetSeperateEntityList", ctx);
		ctx.put("GENERAL_FREEFORM_02", objSeperateEntitySupp);*/
		if(objSeperateEntitySupp !=null && objSeperateEntitySupp instanceof Map)
		{
			
			Map mp=(Map)objSeperateEntitySupp;
			
			String establisedSeDate=mp.get("EstablisedSeDate")!=null?mp.get("EstablisedSeDate").toString():"";
			if(establisedSeDate.length()>4)
			{
				
				mp.put("EstablisedSeDate", establisedSeDate.substring(0, 2)+'/'+establisedSeDate.substring(2,establisedSeDate.length()));
			}
			String lastFiscalYearGrossRevenueSe=mp.get("LastFiscalYearGrossRevenueSe")!=null?mp.get("LastFiscalYearGrossRevenueSe").toString():"0";
			mp.put("LastFiscalYearGrossRevenueSe", DownloadFOP.formateAmountString(PDFGenerator.formatter.format(Long.parseLong(lastFiscalYearGrossRevenueSe))));
			
			String EstimateCurrentYearGrossRevenueSe=mp.get("EstimateCurrentYearGrossRevenueSe")!=null?mp.get("EstimateCurrentYearGrossRevenueSe").toString():"0";
			mp.put("EstimateCurrentYearGrossRevenueSe", DownloadFOP.formateAmountString(PDFGenerator.formatter.format(Long.parseLong(EstimateCurrentYearGrossRevenueSe))));
			ctx.put("GENERAL_FREEFORM_03", mp);
		}
		Object objFundsControlledSupp = SqlResources.getSqlMapProcessor(ctx)
				.findByKey("FundsControlledSupp.findByKey", ctx);
		
		ctx.put("GENERAL_FREEFORM_101", objFundsControlledSupp);
		if (objFundsControlledSupp != null
				&& objFundsControlledSupp instanceof Map) {
				
			Map mp = (Map) objFundsControlledSupp;
				String TotalAmountOfClientFundsFcFormat= mp.get("TotalAmountOfClientFundsFc") != null ? mp.get("TotalAmountOfClientFundsFc").toString() : "0"; 
				
				TotalAmountOfClientFundsFcFormat = PDFGenerator.formatter.format(Long.parseLong(TotalAmountOfClientFundsFcFormat));
				TotalAmountOfClientFundsFcFormat = DownloadFOP.formateAmountString(TotalAmountOfClientFundsFcFormat);
				
				ctx.put("TotalAmountOfClientFundsFcFormat", TotalAmountOfClientFundsFcFormat);
		}
		
		Object listTrusteeOrEstateSupp = SqlResources.getSqlMapProcessor(ctx)
				.select("SqlStmts.generalviewListTrusteeOrEstateSupp", ctx);
		ctx.put("GENERAL_LIST_01", listTrusteeOrEstateSupp);
		
		if((ctx.get("CompanyKey") instanceof Integer ? (Integer)ctx.get("CompanyKey") == 3 : "3".equals(ctx.get("CompanyKey"))) && !"Issued".equals(ctx.get("StatusDesc")) && (listTrusteeOrEstateSupp == null || ((List)listTrusteeOrEstateSupp).size() < 1 || ((List)listTrusteeOrEstateSupp).isEmpty())) {
			List listTrusteeOrEstateSuppList = new ArrayList();
			Map listTrusteeOrEstateSuppMap = new HashMap();
			listTrusteeOrEstateSuppMap.put("NameOfTrust", "");
			listTrusteeOrEstateSuppMap.put("TypeofTrustKey", "");
			listTrusteeOrEstateSuppMap.put("ValueOfAssetsTrustformat", "");
			listTrusteeOrEstateSuppMap.put("IsBeneficiaryInterestTrust", "");
			listTrusteeOrEstateSuppList.add(listTrusteeOrEstateSuppMap);
			ctx.put("firmlawsuit_practice_list_01", listTrusteeOrEstateSuppList);
		}

		Object listOutsideInterestSupp = SqlResources.getSqlMapProcessor(ctx)
				.select("SqlStmts.generalviewgetOutsideInterestSupp", ctx);
		ctx.put("GENERAL_LIST_04", listOutsideInterestSupp);
		
		if((ctx.get("CompanyKey") instanceof Integer ? (Integer)ctx.get("CompanyKey") == 3 : "3".equals(ctx.get("CompanyKey"))) && !"Issued".equals(ctx.get("StatusDesc")) && (listOutsideInterestSupp == null || ((List)listOutsideInterestSupp).size() < 1 || ((List)listOutsideInterestSupp).isEmpty())) {
			List listOutsideInterestSuppList = new ArrayList();
			Map listOutsideInterestSuppMap = new HashMap();
			listOutsideInterestSuppMap.put("ClientNameOsi", "");
			listOutsideInterestSuppMap.put("EntityNameOsi", "");
			listOutsideInterestSuppMap.put("ClientIndustryDesc", "");
			listOutsideInterestSuppMap.put("PositionHeldOsi", "");
			listOutsideInterestSuppMap.put("DOInsuranceOsi", "");
			listOutsideInterestSuppMap.put("PercentEquityInterestofOsi", "");
			listOutsideInterestSuppMap.put("ServiceIndustryDesc", "");
			listOutsideInterestSuppMap.put("IndividualListedServicesOsi", "");
			listOutsideInterestSuppList.add(listOutsideInterestSuppMap);
			ctx.put("firmlawsuit_practice_list_01", listOutsideInterestSuppList);
		}

		Object listFeeSuitSupp = SqlResources.getSqlMapProcessor(ctx).select(
				"SqlStmts.generalviewgetFeesSuedList", ctx);
		ctx.put("RENEWBASICINFO_LIST_FEESUIT", listFeeSuitSupp);
		
		if((ctx.get("CompanyKey") instanceof Integer ? (Integer)ctx.get("CompanyKey") == 3 : "3".equals(ctx.get("CompanyKey"))) && !"Issued".equals(ctx.get("StatusDesc")) && (listFeeSuitSupp == null || ((List)listFeeSuitSupp).size() < 1 || ((List)listFeeSuitSupp).isEmpty())) {
			List listFeeSuitSuppList = new ArrayList();
			Map listFeeSuitSuppMap = new HashMap();
			listFeeSuitSuppMap.put("AmountOfFeesSuedFormat", "");
			listFeeSuitSuppMap.put("SuitFilesDateFees", "");
			listFeeSuitSuppMap.put("IsAllegOfLegalMalPrac", "");
			listFeeSuitSuppMap.put("FeeSuitDispositionKey", "");
			listFeeSuitSuppList.add(listFeeSuitSuppMap);
			ctx.put("firmlawsuit_practice_list_01", listFeeSuitSuppList);
		}
		
		
		// Object objFirm =SqlResources.getSqlMapProcessor(ctx).findByKey("Firm.findByKey", ctx);	
		// ctx.put("FIRM_FREEFORM_01", objFirm);
		
		Object objClientServiceDescFirm =SqlResources.getSqlMapProcessor(ctx).findByKey("SqlStmts.UserStatementsaveAccountstmtsgeClientServiceDescFirm", ctx);
		ctx.put("CLIENTSERVICEDESC_FIRM_FREEFORM_01", objClientServiceDescFirm);
		
		Object objFinancialPlanningAndIAServiceSupp =SqlResources.getSqlMapProcessor(ctx).findByKey("FinancialPlanningAndIAServiceSupp.findByKey", ctx);
		ctx.put("INVEST_FINAN_PLANING_FREEFORM_01", objFinancialPlanningAndIAServiceSupp);
		
		Object objAttestation =SqlResources.getSqlMapProcessor(ctx).findByKey("Attestation.findByKey", ctx);
		ctx.put("ATTESTATION_FREEFORM_02", objAttestation);		
		
		if (objAttestation != null && objAttestation instanceof Map) {
			Map mp = (Map) objAttestation;
			ctx.put("OtherAuditAttesdesc" ,mp.get("AuditComment"));
			
		}
		
		Object listAttestationAuditWork =SqlResources.getSqlMapProcessor(ctx).select("SqlStmts.UserStatementManualBoQueriesAttestationAuditWorkNewPDF", ctx);
		ctx.put("ATTESTATION_LIST_03", listAttestationAuditWork);
		
		/*if (listAttestationAuditWork != null
				&& listAttestationAuditWork instanceof List) {

			List attestaudit = (List) listAttestationAuditWork;
			for(int i=0;i<attestaudit.size();i++){
			Map o = (Map) attestaudit.get(i);

			if ((o.get("ClientIndustrySrNo").equals("z.")) && (o.get("AuditClients") != null || o.get("AuditClients") != "")) {
				
			}
			}
			}*/
		
		
		Object listITServicesSupp =SqlResources.getSqlMapProcessor(ctx).select("SqlStmts.computerRelatedServicesviewgetITServicesSupp", ctx);
		ctx.put("COMPUTERSERVICE_LIST_03", listITServicesSupp);
		
		/* Object objFundsControlledSupp =SqlResources.getSqlMapProcessor(ctx).findByKey("FundsControlledSupp.findByKey", ctx);
		ctx.put("GENERAL_FREEFORM_03", objFundsControlledSupp); */	
		
		Object objClientServiceDescFundControl =SqlResources.getSqlMapProcessor(ctx).findByKey("SqlStmts.UserStatementsaveAccountstmtsgeClientServiceDescFundControl", ctx);
		ctx.put("CLIENTSERVICEDESC_FUNDCONTROL_FREEFORM_01", objClientServiceDescFundControl);
		
//		Object isShowCyberQuestion = RuleUtils.executeRule(ctx, "AccountantRule.isShowCyberQuestion_Renewal");
//		boolean cyberImplFlag = false;			
//		if(isShowCyberQuestion instanceof Boolean)
//			cyberImplFlag = (Boolean) isShowCyberQuestion;
//		
//		ctx.put("CYBERIMPLFLAG", cyberImplFlag);
		
		return null;
	}
	
	public static Object fetchDataInPdf(Context ctx)throws Exception
	{
		
		HttpServletRequest req=(HttpServletRequest)ctx.get("DocumentRequest");
		HttpServletResponse res=(HttpServletResponse)ctx.get("DocumentResponse");
		ServletContext servletContext=(ServletContext)ctx.get("DocumentServletContext");
		logger.debug("DownloadExcel Start for PDF");
		res.setContentType("application/vnd.ms-excel");
		res.setHeader("Content-disposition", "attachment; filename=\"Policy Form_"+ ctx.get("QuoteNumber").toString() + "\"");
		
		String policyNumber ="";
		List attachments  = new ArrayList();		
		OutputStream out = null;
		File pdfFile = null;
		boolean dollarDefenseFlag=false;
		
		List listFormID = AccountUtils.populateFinalisedPolicyForm(ctx);				
		
		String outFile = SystemProperties.getInstance().getString("html.basedir") + "data//Policy Form_"+ ctx.get("QuoteNumber").toString()	+ ".pdf";
		pdfFile = new File(outFile);
		out = new java.io.FileOutputStream(pdfFile);
		out = new java.io.BufferedOutputStream(out);

		String baseUrl = null;
		if (ctx.get("baseUrl") != null)
			baseUrl = ctx.get("baseUrl").toString();

		if(ctx.get(HtmlConstants.DOCUMENTSERVLETCONTEXT) != null)
			baseUrl = "file:///" + ((ServletContext)ctx.get(HtmlConstants.DOCUMENTSERVLETCONTEXT)).getRealPath("/");
		
		ServletContextURIResolver uriResolver = null;
		if (ctx.get(HtmlConstants.DOCUMENTURIRESOLVER) != null)
			uriResolver = (ServletContextURIResolver)ctx.get(HtmlConstants.DOCUMENTURIRESOLVER);
		
		new DownloadForm().generateForm((Context) ctx, listFormID, out,baseUrl, uriResolver);

		AccountUtils.populateLastUpdateTimeStamp(ctx);
		String result = "";
		String skipUpload = SystemProperties.getInstance().getString("appl." + ctx.getProject() + ".skipupload");
		
		new DownloadForm().downloadPolicyPdf(ctx, req, res);
		result = "File added successfully!";	
		
		{ if (logger.isDebugEnabled()) logger.debug("Result from uploadFormDocument(ctx) " + result); }

		
	  return SUCCESS_MESSAGE;
		
	}
	
	
	
	
	
	public static void main(String args[]){
		Date dt = com.util.DateUtils.convertStringToDate("2018-01-01", "MM/dd/yyyy");
		{ if (logger.isDebugEnabled()) logger.debug(dt.toString()); }
		String str = com.util.DateUtils.convertDateToString(dt, "MM/dd/yyyy");
		{ if (logger.isDebugEnabled()) logger.debug(str); }
	}
	
	

	

	
}
