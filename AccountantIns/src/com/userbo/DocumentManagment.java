package com.userbo;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.fop.servlet.ServletContextURIResolver;

import com.fop.DownloadFOP;
import com.fop.XML2PDF;
import com.formgenerator.DownloadForm;
import com.mail.MailSender;
import com.manage.esign.EsignUtil;
import com.manage.managecomponent.components.SetParametersForStoredProcedures;
import com.manage.managemetadata.functions.commonfunctions.RuleUtils;
import com.ormapping.ibatis.SqlResources;
import com.pdf.PrintPDF;
import com.util.Constants;
import com.util.Context;
import com.util.FileSystemDocumentStorage;
import com.util.HtmlConstants;
import com.util.IContext;
import com.util.InetLogger;
import com.util.PolicyNumberException;
import com.util.SystemProperties;

public class DocumentManagment {

	public static String DATE_PATTERN = "MM-dd-yyyy";
	private static InetLogger logger = InetLogger.getInetLogger(DocumentManagment.class);
	private static final String ERROR_MESSAGE = "ERROR : ";	
	private static final String SUCCESS_MESSAGE = "SUCCESS";
	private static final String DOCUMENT_STORAGE_REFERENCE = "DocumentStorageReference";

	private String uploadToDocumentStorage(IContext ctx, String folderName,
			String docName, String sourceFile) throws Exception {
		ctx.remove(DOCUMENT_STORAGE_REFERENCE);
		// Legacy: new DocManagementUtil().uploadDocToSharePoint(...);
		String reference = FileSystemDocumentStorage.upload(folderName,
				SystemProperties.getInstance().getString("document.storage.directory"),
				docName, sourceFile);
		ctx.put(DOCUMENT_STORAGE_REFERENCE, reference);
		return "File added successfully!";
	}

	private String takeDocumentStorageReference(IContext ctx) throws Exception {
		Object reference = ctx.get(DOCUMENT_STORAGE_REFERENCE);
		ctx.remove(DOCUMENT_STORAGE_REFERENCE);
		if (reference == null || !reference.toString().startsWith("enc:v1:"))
			throw new Exception("Document storage reference is missing or invalid.");
		return reference.toString();
	}
	
	public void processDocument(IContext ctx) throws Exception {
		if(ctx.get("fileItems") == null || !(ctx.get("fileItems") instanceof List))
			return;
		
		String fileName = null;
		String DocTitle = null;
		String Comment = null;
		String DocFileName = null;
		FileItem uploadFile = null;
		String SearchType = null;
		String DocType = null;

		logger.debug("processDocument has started");
		try {
			/*DiskFileItemFactory factory = new DiskFileItemFactory();

			// maximum size that will be stored in memory
			factory.setSizeThreshold(25 * 1024 * 1024);

			// the location for saving data that is larger than
			// getSizeThreshold()
			String tempDirectory = SystemProperties.getInstance().getString(
					"fileupload.tempdirectory");
			factory.setRepository(new File(tempDirectory));

			ServletFileUpload upload = new ServletFileUpload(factory);

			// maximum size before a FileUploadException will be thrown
			Long size = new Long(25 * 1024 * 1024);
			upload.setSizeMax(size.longValue());*/

			//List fileItems = upload.parseRequest((HttpServletRequest) ctx.get(HtmlConstants.DOCUMENTREQUEST));
			List fileItems = (List)ctx.get("fileItems");

			// assume we know there are two files. The first file is a small
			// text file, the second is unknown and is written to a file on
			// the server
			Iterator i = fileItems.iterator();

			while (i.hasNext()) {
				FileItem fi = (FileItem) i.next();

				if (fi.isFormField()) {
					String name = fi.getFieldName();
					logger.debug(name);
					if (name.equalsIgnoreCase("DocTitle")) {
						DocTitle = fi.getString();
						DocTitle = DocTitle.replace("&", "$AND$");
						DocTitle = DocTitle.replace("%", "$PER$");
						DocTitle = DocTitle.replace("'", "$APH$");

					} else if (name.equalsIgnoreCase("Comment")) {
						Comment = fi.getString();
						Comment = Comment.replace("&", "$AND$");
						Comment = Comment.replace("%", "$PER$");
						Comment = Comment.replace("'", "$APH$");
					} else if (name.equalsIgnoreCase("SearchType")) {
						SearchType = fi.getString();
						{ if (logger.isDebugEnabled()) logger.debug("SearchType - " + SearchType); }
					}

					// String value = fi.getString();
				} else {

					// filename on the client
					fileName = fi.getName();
					uploadFile = fi;
					if (fileName == null) {
						continue;
					}
				}
				// break;
			}
		} catch (Exception e) {
			logger.error("Error in file process", e);
			throw e;
		}

		if (fileName == null || "".equals(fileName))
			AccountUtils.populateError(ctx, "DocFileName",
					"Document has not been selected");
		else if (fileName != null && !validateFileName(fileName))
			AccountUtils.populateError(ctx, "DocFileName",
					"Selected file is not correct");

		if (SearchType == null || "".equals(SearchType))
			AccountUtils.populateError(ctx, "SearchType",
					"Select Radio button ");

		if (Comment == null || "".equals(Comment))
			AccountUtils.populateError(ctx, "Comment", "Enter comment");

		if (SearchType != null && SearchType.equals("other")) {
			if (DocTitle == null || "".equals(DocTitle))
				AccountUtils.populateError(ctx, "DocTitle", "Enter title");
		}

		List errorList = null;
		if (ctx.get(Constants.INET_ERRORS_LIST) != null) {
			errorList = (List) ctx.get(Constants.INET_ERRORS_LIST);
			if (!errorList.isEmpty()) {
				ctx.put("SearchType", SearchType);
				ctx.put("SearchHidden", SearchType);

				if (Comment != null && !"".equals(Comment)) {

					Comment = Comment.replace("$AND$","&");
					Comment = Comment.replace("$PER$","%");
					Comment = Comment.replace("$APH$","'");
				}
				if (DocTitle != null && !"".equals(DocTitle)) {

					DocTitle = DocTitle.replace("$AND$","&");
					DocTitle = DocTitle.replace("$PER$","%");
					DocTitle = DocTitle.replace("$APH$","'");
				}

				ctx.put("Comment", Comment);
				ctx.put("DocTitle", DocTitle);
			}
		}

		if ((fileName == null || "".equals(fileName))
				|| (Comment == null || "".equals(Comment))
				|| (SearchType == null || "".equals(SearchType)))
			return;

		if (SearchType != null && SearchType.equals("other")) {
			if (DocTitle == null || "".equals(DocTitle)) {
				return;
			}
		}
		
		String isBrokerPolicy=ctx.get("isBrokerPolicy")!=null?ctx.get("isBrokerPolicy").toString():"N";
		String isBrokerageDocument=ctx.get("isBrokerageDocument")!=null?ctx.get("isBrokerageDocument").toString():"N";
		if("Y".equals(isBrokerPolicy) || "Y".equals(isBrokerageDocument))
		{
			if(ctx.get("BrokeragePolicyKey")==null )
				return;
			String brokerQN="BR-"+ctx.get("BrokeragePolicyKey");
			ctx.put("QuoteNumber", brokerQN);
		}
		int fileIndex = fileName.indexOf(".");
		String extension = fileName.substring(fileIndex);
		logger.debug(extension);
		if (SearchType != null && SearchType.equals("policyform")) {
			{ if (logger.isDebugEnabled()) logger.debug("ctx -----" + ctx.get("QuoteNumber").toString()); }
			fileName = "Policy Form_" + ctx.get("QuoteNumber").toString();
			DocType = "PF";

		} else if (SearchType != null && SearchType.equals("quoteletter")) {
			fileName = "QuoteLetter_" + ctx.get("QuoteNumber").toString();
			DocType = "QL";
		} else if (SearchType != null && SearchType.equals("signedform")) {
			fileName = "Application Form_" + ctx.get("QuoteNumber").toString();
			DocType = "SF";
		} else if (SearchType != null && SearchType.equals("lossRun")) {
			fileName = "LossRun_" + ctx.get("QuoteNumber").toString();
			DocType = "LR";
		} else if (SearchType != null && SearchType.equals("priorPolicy")) {
			fileName = "PriorPolicy_" + ctx.get("QuoteNumber").toString();
			DocType = "PP";
		} else if (SearchType != null && SearchType.equals("other")) {
			// fileName = DocTitle + ctx.get("QuoteNumber").toString();
			if (!AccountantValidationUtils.validateFileNameForSpecialChar(DocTitle))
			{
				AccountUtils.populateError(ctx, "DocTitle","Invalid File Name." );
				return;
			}
			fileName = DocTitle + "_" + ctx.get("QuoteNumber").toString();
			DocType = "OT";
		}
		try{
		String org_fileName = fileName;
		fileName = fileName + extension;
		logger.debug(fileName);

		DocFileName = fileName.substring(fileName.lastIndexOf("\\") + 1,
				fileName.length());
		File file = new File(fileName);
		String docName = file.getName();
		String docNameTemp = file.getName();
		
		AccountUtils.populateLastUpdateTimeStamp(ctx);

		String uploaddirectory = SystemProperties.getInstance().getString(
				"fileupload.uploaddirectory");

		try {
			uploadFile.write(new File(uploaddirectory, DocFileName));
		} catch (Exception e) {
			logger.error("Error in DocumentManagment.processDocument", e);
			AccountUtils.populateError(ctx, "DocUploadError",
					"Document could not be write to file" );
			

			return;
		}

		String result = null;
		try {
			result = uploadDocument(ctx, uploaddirectory + docName);
			//result = "File added successfully!";
			{ if (logger.isDebugEnabled()) logger.debug("Result from uploadDocument(ctx, uploaddirectory + docName)" + result); }
		} catch (Exception e) {
			logger.error("Error in DocumentManagment.processDocument", e);
			AccountUtils.populateError(ctx, "DocUploadError",
					"Document could not be uploaded");
			return;
		}
		if (result == null
				|| (result != null && !result
						.equals("File added successfully!"))) {
			
			AccountUtils.populateError(ctx, "DocUploadError",
					"Document could not be uploaded");

			return;
		}

		if (result != null && result.equals("File added successfully!")) {
			logger.debug("processDocument has done");
			// ctx.put("DocTitle", DocTitle);
			
			if (Comment != null && !"".equals(Comment)) {

				Comment = Comment.replace("$AND$","&");
				Comment = Comment.replace("$PER$","%");
				Comment = Comment.replace("$APH$","'");
			}
			if (DocTitle != null && !"".equals(DocTitle)) {

				DocTitle = DocTitle.replace("$AND$","&");
				DocTitle = DocTitle.replace("$PER$","%");
				DocTitle = DocTitle.replace("$APH$","'");
			}
			
			ctx.put("DocTitle", org_fileName);

			ctx.put("Comment", Comment);
			// ctx.put("DocFileName", DocFileName);

			int index = docName.indexOf(".");
			String fileName1 = docName.substring(0, index);
			String fileExt = docName.substring(index + 1, docName.length());
			docName = fileName1 + "_" + ctx.get("UploadedTime").toString()
					+ "." + fileExt;
			docName = docName.replace(" ", "").replace(":", "")
					.replace("-", "");

			ctx.put("DocFileName", docName);
			ctx.put("DocType", DocType);

			ctx.put("UploadedType", "Manual");

			setUrl(ctx, docName, "DocUrl");
			RuleUtils.executeRule(ctx,"AccountantRule.AssignCreatedByAndCreatedTimeStamp");
			if("Y".equals(isBrokerPolicy) || "Y".equals(isBrokerageDocument))
				insertInBrokerageDocumentLW(ctx);
			else
			insertInDocumentArchive(ctx);
		}

		File tempFile = new File(uploaddirectory + docNameTemp);
		if (tempFile.exists())
			tempFile.delete();
		}catch(Exception e){
			logger.error("Error in DocumentManagment.processDocument", e);
		}
	}

	public String uploadDocument(IContext ctx, String url) throws Exception {

		byte[] docContent = new EsignUtil().getDocument(url);

		File file = new File(url);
		String docName = file.getName();

		int index = docName.indexOf(".");
		String fileName = docName.substring(0, index);
		String fileExt = docName.substring(index + 1, docName.length());
		AccountUtils.populateLastUpdateTimeStamp(ctx);
		String DocFileName = fileName + "_"
				+ ctx.get("UploadedTime").toString() + "." + fileExt;
		DocFileName = DocFileName.replace(" ", "").replace(":", "").replace(
				"-", "");
		String folderName = ctx.get("QuoteNumber").toString();

		// String userName = "sanjayr";
		// String password = "Sanr55.bgp";
		// String domain = "in";
		// String baseDir = "TestJava";

		/*return new DocManagementUtil().uploadDocToSharePoint(docLibPathName,
				folderName, baseDir, DocFileName, docContent, userName,
				password, domain);*/
		//Raghu
		return uploadToDocumentStorage(ctx, folderName, DocFileName, url);

	}

	public void processSignedDocument(IContext ctx) throws Exception {

		logger.debug("processSignedDocument has started");
		// Populating all keys in Context
		Object obj = SqlResources.getSqlMapProcessor(ctx).findByKey(
				"SqlStmts.UserStatementprocessquotegetPolicyKeys", ctx);
		Map keysMap = null;

		if (obj != null && obj instanceof Map) {
			keysMap = (Map) obj;
			ctx.putAll(keysMap);
		}

		AccountUtils.populateLastUpdateTimeStamp(ctx);
		String result = uploadSignedDocument(ctx);
//		String result = "true";

		{ if (logger.isDebugEnabled()) logger.debug("Result from uploadSignedDocument(ctx) " + result); }

		if (result != null) {
			logger.debug("processSignedDocument has done");

			String DocFileName = "Signed_Form_"
					+ ctx.get("QuoteNumber").toString() + "_"
					+ ctx.get("UploadedTime").toString() + ".pdf";
			String DocTitle = "Signed_Form_"
					+ ctx.get("QuoteNumber").toString();
			ctx.put("DocTitle", DocTitle);
			DocFileName = DocFileName.replace(" ", "").replace(":", "")
					.replace("-", "");
			ctx.put("DocFileName", DocFileName);
			ctx.put("DocType", "SF");

			ctx.put("UploadedType", "Auto");

			setSignedPDFUrl(ctx, "DocUrl");
			insertInDocumentArchive(ctx);
		}
	}

	public String uploadSignedDocument(IContext ctx) throws Exception {

		String assuresignURL = SystemProperties.getInstance().getString(
				"esign.doc.retrieve.url");
		String SignedDocumentID = null;
		if (ctx.get("SignedDocumentID") != null)
			SignedDocumentID = ctx.get("SignedDocumentID").toString();

		String SignedDocumentToken = null;
		if (ctx.get("SignedDocumentToken") != null)
			SignedDocumentToken = ctx.get("SignedDocumentToken").toString();

		if (assuresignURL == null || SignedDocumentID == null
				|| SignedDocumentToken == null)
			return null;

		byte[] docContent = new EsignUtil().getDocument(assuresignURL, ctx.get(
				"SignedDocumentID").toString(), ctx.get("SignedDocumentToken")
				.toString());
		
		String outFile = SystemProperties.getInstance().getString("html.basedir") + "data//Signed_Form_" + ctx.get("QuoteNumber").toString() + ".pdf";
		FileOutputStream stream = new FileOutputStream(outFile);
	    try {
	        stream.write(docContent);
	    } finally {
	        stream.close();
	    }
	    
		String docName = "Signed_Form_" + ctx.get("QuoteNumber").toString()
				+ "_" + ctx.get("UploadedTime").toString() + ".pdf";
		docName = docName.replace(" ", "").replace(":", "").replace("-", "");
		String folderName = ctx.get("QuoteNumber").toString();

		// String userName = "sanjayr";
		// String password = "Sanr55.bgp";
		// String domain = "in";
		// String baseDir = "TestJava";

		/*return new DocManagementUtil().uploadDocToSharePoint(docLibPathName,
				folderName, baseDir, docName, docContent, userName, password,
				domain);*/
		//Raghu
		return uploadToDocumentStorage(ctx, folderName, docName, outFile);
	}


	public String processDocumentManagment(IContext ctx) throws Exception {
		{ if (logger.isDebugEnabled()) logger.debug("processDocumentManagment started  ........ " + ctx.get("QuoteNumber")); }
		ctx.remove("IsDocumentProcessed");
		boolean issuePolicy=false,IsManualPremium=false,alreadyIssuePolicy=false;
		String policyNumber ="";
		List attachments  = new ArrayList();		
		OutputStream out = null;
		File pdfFile = null;
		boolean dollarDefenseFlag=false;
		// PolicyNumber generation and Update
		try{
			
				
				{ if (logger.isDebugEnabled()) logger.debug("Checking if the Policy is not already Issued " + ctx.get("PolicyKey")); }

				Object objcheck = SqlResources.getSqlMapProcessor(ctx).select("SqlStmts.UserStatementsaveAccountstmtsgetStatusKeyFinalisedQuote",ctx);
			if (objcheck != null && objcheck instanceof List) {

				List objList = (List) objcheck;
				if (objList != null && objList.size() > 0) {
					Map objMap = (Map) objList.get(0);
					String statusKey = objMap.get("StatusKey") != null ? objMap.get("StatusKey").toString() : "";
					 policyNumber = objMap.get("PolicyNumber") != null ? objMap.get("PolicyNumber").toString() : "";
					logger.info("StatusKey of PolicyKey " + ctx.get("PolicyKey") + "and TransactionSequence " + ctx.get("FinalisedQuote_TransactionSequence") + " is " + statusKey);
					logger.info("PolicyNumber of PolicyKey " + ctx.get("PolicyKey") + " is " + policyNumber);

					if ("6".equals(statusKey) || !"".equals(policyNumber)) {
						logger.info("Going to Return as Policy is already Issued");
						alreadyIssuePolicy = true;
						return "Already Issued";
					} else if (!"6".equals(statusKey) && !"7".equals(statusKey)) {
						logger.info("Going to Return as Policy can't be Issued");
						alreadyIssuePolicy = true;
						return "Can't be Issued";
					}
				}
			}
				
				{ if (logger.isDebugEnabled()) logger.debug("processDocumentManagment started  ........ " + ctx.get("QuoteNumber")); }
				
				if("1".equals(ctx.get("CompanyKey").toString()))
					policyNumber = AccountUtils.generatePolicyNumber(ctx);
				if("3".equals(ctx.get("CompanyKey").toString()))
					policyNumber = AccountUtils.generatePolicyNumberISMIE(ctx);
					
			   
			//	policyNumber="570-109213-8";
				ctx.put("PolicyNumber", policyNumber);
				AccountUtils.populateLastUpdateTimeStamp(ctx);
				
				
				new SetParametersForStoredProcedures().setParametersInContext(ctx, "PolicyKey,VersionSequence,PolicyNumber,LastUpdateUserID");
				SqlResources.getSqlMapProcessor(ctx).update("Policy.IssuePolicyUpdateStateus_p",ctx);
				
				{ if (logger.isDebugEnabled()) logger.debug("Policynumber to "+policyNumber+" updated and Policy Status updated to ISSUED"); }
				
				try{
					AccountUtils.updateZOHORecords((Context) ctx);
				} catch(Exception e) {
					logger.error("Exception occured during ZOHO update "+e, e);
				}
				
				List getPolicyNumber=SqlResources.getSqlMapProcessor(ctx).select("SqlStmts.UserStatementManualBoQueriesgetPolicyNumber",ctx);
				ctx.putAll((Map) getPolicyNumber.get(0));
				String dbPolicyNumber=ctx.get("DBPolicyNumber")!=null?ctx.get("DBPolicyNumber").toString():"";
				if("".equals(dbPolicyNumber))
				{
					throw new PolicyNumberException();
					
				}
				//SqlResources.getSqlMapProcessor(ctx).update("SqlStmts.UserStatementformupdatePolicyNumber", ctx);
				
				//Populating all keys in Context
				Object obj = SqlResources.getSqlMapProcessor(ctx).findByKey("SqlStmts.UserStatementprocessquotegetPolicyKeys", ctx);
				Map keysMap = null;
				if (obj != null && obj instanceof Map) {
					keysMap = (Map) obj;
					ctx.putAll(keysMap);
				}

				obj = SqlResources.getSqlMapProcessor(ctx).findByKey("SqlStmts.BasicInfoviewCoveragePolicy", ctx);
				Map policyCovMap = null;

				if (obj != null && obj instanceof Map) {
					policyCovMap = (Map) obj;
					ctx.putAll(policyCovMap);
				}

				obj = SqlResources.getSqlMapProcessor(ctx).findByKey("SqlStmts.UserStatementpdfquotelettergetPriorPolicyInfo",ctx);
				Map policyInfo = null;

				if (obj != null && obj instanceof Map) {
					policyInfo = (Map) obj;
					ctx.putAll(policyInfo);
				}

				logger.debug("Populated all keys in Context...............");

				QuoteLetter.getFinalisedQuote(ctx);
				logger.debug("Get Finalised Quote.............................");

				List listFormID = AccountUtils.populateFinalisedPolicyForm(ctx);				
				// Form
				//List listFormID = getPolicyFormID(ctx);			

				dollarDefenseFlag = checkDollarDefenseFlagWithFinalisedOption(ctx);
				
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
				result = uploadFormDocument(ctx);
				{ if (logger.isDebugEnabled()) logger.debug("Result from uploadFormDocument(ctx) " + result); }

				
				// ---- Commenting for testing
				
				if (result == null || (result != null && !result.equals("File added successfully!"))) {
					AccountUtils.populateError(ctx, "DocUploadError", "Policy could not be issued");
					issuePolicy=false;
					return ERROR_MESSAGE + "DocUploadError";
				}

				if (result != null && result.equals("File added successfully!")) {

					String DocFileName = "Policy Form_"
							+ ctx.get("QuoteNumber").toString() + "_"
							+ ctx.get("UploadedTime").toString() + ".pdf";
					String DocTitle = "Policy Form_"
							+ ctx.get("QuoteNumber").toString();
					ctx.put("DocTitle", DocTitle);
					DocFileName = DocFileName.replace(" ", "").replace(":", "")
							.replace("-", "");

					ctx.put("DocFileName", DocFileName);
					ctx.put("Comment", "");
					ctx.put("DocType", "PF");

					ctx.put("UploadedType", "Auto");
					setFormUrl(ctx, "DocUrl");

					insertInDocumentArchive(ctx);
				}

				
				if (out != null)
					out.close();
				attachments.add(outFile);

				// Application PDF
				outFile = SystemProperties.getInstance().getString("html.basedir")+ "data//Accountant_" + ctx.get("QuoteNumber").toString()+ ".pdf";
				pdfFile = new File(outFile);
				out = new java.io.FileOutputStream(pdfFile);
				out = new java.io.BufferedOutputStream(out);

				if (ctx.get("PolicyStatusKey") != null&& "1".equals(ctx.get("PolicyStatusKey").toString())) {
					ByteArrayOutputStream baos = new PrintPDF().makePdf((Context) ctx);
					baos.writeTo(out);
				} else {
//					baseUrl = "file:///" + ((ServletContext)ctx.get(HtmlConstants.DOCUMENTSERVLETCONTEXT)).getRealPath("/");
					new DownloadFOP().makeRenewPdfForMail((Context) ctx, out,baseUrl,uriResolver);
				}

				if (out != null)
					out.close();
				
				
								
				ctx.remove("IssuedDate");
				ctx.remove("PolicyNumber");		
				ctx.put("IsDocumentProcessed","Y");
				issuePolicy=true;
				// Commention this as No longer application will be attached with the Issued mail 10 October 2015
				
				//attachments.add(outFile);
				
		}
		catch (Exception e)
		{
			ctx.put("IsDocumentProcessed", "N");
			logger.error("processDocumentManagment failed  ........" + e, e);
			issuePolicy=false;
			throw e;
		}finally {
			/*code by sukhi 26/09/2018*/
			pdfFile = null;
			if(out != null){
				out.close();
				out = null;
			}
			if(!issuePolicy && !alreadyIssuePolicy){
				logger.debug(issuePolicy);
				try{
					{ if (logger.isDebugEnabled()) logger.debug("Going to rollback all database changes for issuing policy having quote number"+ctx.get("QuoteNumber")); }
					new SetParametersForStoredProcedures().setParametersInContext(ctx, "PolicyKey,VersionSequence,LastUpdateUserID");
					SqlResources.getSqlMapProcessor(ctx).update("Policy.RollbackIssuePolicyUpdateStateus_p",ctx);
				} catch (Exception e1) {
					issuePolicy=false;
					logger.error("Exception occured during rollback all database changes for issuing policy ", e1);
				}
			}
			{ if (logger.isDebugEnabled()) logger.debug("issuing policy :  "+issuePolicy+" for policynumber :"+policyNumber+" having quote number"+ctx.get("QuoteNumber")); }
			
			try{
				AccountUtils.updateZOHORecords((Context) ctx);
			} catch(Exception e) {
				logger.error("Exception occured during ZOHO update "+e, e);
			}
		}
			if(issuePolicy)
			{
				File file = null;
				try
				{
						boolean producerCodeExist = AccountantValidationUtils.checkIfSubProducerExist(ctx);
						String specialEmailId = "";
						logger.debug("going to get Admin Email Id");			
						String adminEmail = AccountUtils.getAdminEmailID(ctx);
						{ if (logger.isDebugEnabled()) logger.debug("Admin email id = " + adminEmail); }
						boolean isSpecialIssuance = AccountUtils.checkIfSpecialIssuance(ctx);
						{ if (logger.isDebugEnabled()) logger.debug("Special issuance = " + isSpecialIssuance); }
						
						logger.debug("Check If PA and Defense Inside");
						boolean isPAAndDefenseInside = false ;
//						if(ctx.get("StateCode") !=null && "PA".equals(ctx.get("StateCode").toString())){
//							isPAAndDefenseInside = AccountUtils.checkForFinalisedQuoteWithInsideLimit(ctx);
//						}
//						logger.debug(" IS PA and Defense Inside ? " +isPAAndDefenseInside);
						if(isSpecialIssuance || producerCodeExist || isPAAndDefenseInside){
							logger.debug("get special issuance emailid ");
							specialEmailId = AccountUtils.getSpecialIssuanceEmail(ctx);
							{ if (logger.isDebugEnabled()) logger.debug("Got the special email id " + specialEmailId); }
						}
						String insuredEmail = "";			
						if(!producerCodeExist)
							insuredEmail = AccountUtils.getInsuredEmail(ctx);
						{ if (logger.isDebugEnabled()) logger.debug("Got the insured email as " + insuredEmail); }
						if ("".equals(insuredEmail))
							insuredEmail = adminEmail;
						
			//			logger.debug("Is special issuance then set this to special emailid");
			//			if(isSpecialIssuance)
			//				insuredEmail = specialEmailId ;
						String productionEnv = "N";
						try {
							productionEnv = SystemProperties.getInstance().getString("appl." + ctx.getProject() + ".environment.production");
						} catch (Exception e) {
							logger.error("error in geeting production environment", e);
						}
						String ccAddress="",bccAddress="";
						if ("N".equals(productionEnv)){
							ccAddress = SystemProperties.getInstance().getString("mail.admin.cc.address");
							{ if (logger.isDebugEnabled()) logger.debug("email id------->"+ccAddress); }
						}
						//if("".equals(ccAddress))
						//	ccAddress=SystemProperties.getInstance().getString("appl." + ctx.getProject() + ".admin.cc.email");
						if("".equals(bccAddress))
							bccAddress=SystemProperties.getInstance().getString("appl." + ctx.getProject() + ".admin.cc.email");
						String policy = "Issued Policy Number  " + policyNumber;
						if ("".equals(adminEmail))
							return ERROR_MESSAGE + "AdminEmailError";
			
						MailSender mailSender = new MailSender();
						mailSender.setSubject(policy);
						{ if (logger.isDebugEnabled()) logger.debug("dollarDefenseFlag ---  "+ dollarDefenseFlag ); }
						{ if (logger.isDebugEnabled()) logger.debug("isSpecialIssuance ---  "+ isSpecialIssuance ); }
						{ if (logger.isDebugEnabled()) logger.debug("producerCodeExist ---  "+ producerCodeExist ); }
						{ if (logger.isDebugEnabled()) logger.debug("isPAAndDefenseInside ---  "+isPAAndDefenseInside); }
						if(ctx.get("StateCode") !=null && "NY".equals(ctx.get("StateCode").toString())){
							//isPAAndDefenseInside = AccountUtils.checkForFinalisedQuoteWithInsideLimit(ctx);
							mailSender.setToAdd(adminEmail);
						}
						else if (!dollarDefenseFlag && !isSpecialIssuance && !producerCodeExist && !isPAAndDefenseInside){
							mailSender.setToAdd(insuredEmail);
						}
						else if(isSpecialIssuance || producerCodeExist || isPAAndDefenseInside){
							mailSender.setToAdd(specialEmailId);
						}
			//			if(isSpecialIssuance){
			//			mailSender.setBccAdd(specialEmailId);
			//			mailSender.setIsSentToCC("Y");
			//			}

						mailSender.setCcAdd(AccountUtils.getFirstReviewerAgentEmailID(ctx));
						mailSender.setBccAdd(bccAddress);
						mailSender.setContentType("text/html");
						mailSender.setBody(generatePolicyIssueBody(ctx));
						mailSender.setAttachments(attachments);
						mailSender.sendMail();
			
						for (int i = 0; i < attachments.size(); i++) {
							// File Delete
							file = new File(attachments.get(i).toString());
							if (file.exists())
								file.delete();
						}
						
						{ if (logger.isDebugEnabled()) logger.debug("Issued Mail has been sent to " + insuredEmail); }
			
				}
				catch(Exception e)
				{
					logger.error("Exception occured during sending policy to insured ", e);
					return ERROR_MESSAGE + "MailSendingError";
				}finally {
					/*code by sukhi 26/09/2018*/
					file=null;
				
					try{
						AccountUtils.updateZOHORecords((Context) ctx);
						EPICDataUpdate.insertEPICData((Context) ctx);
					}catch(Exception e){
						logger.error("Exception occured during ZOHO update "+e, e);
					}
				}
			}
			else
				logger.debug("Unable to send policy form due to Exception occured during policy generation ");
			
			logger.debug("processDocumentManagment end  ........");
			return SUCCESS_MESSAGE;
			
			
		}
				
		
	

	private boolean checkDollarDefenseFlagWithFinalisedOption(IContext ctx)
			throws Exception {

		boolean dollarDefenseFlag = false;
		Map mapDollarDefense = null;
		int dollarDefenceSize = 0;
		Object objDollarDefense = SqlResources
				.getSqlMapProcessor(ctx)
				.findByKey(
						"SqlStmts.UserStatementsaveAccountstmtsgetIsDollarDefenseWithFinalisedOption",
						ctx);

		if (objDollarDefense != null && objDollarDefense instanceof Map)
			mapDollarDefense = (Map) objDollarDefense;

		if (mapDollarDefense != null && mapDollarDefense.get("IsDollarDefense") != null) {

			if (Integer.parseInt(mapDollarDefense.get("IsDollarDefense")
					.toString()) > 0)
				dollarDefenseFlag = true;
		}

		return dollarDefenseFlag;
	}

	public String uploadFormDocument(IContext ctx) throws Exception {

		String docName = "Policy Form_" + ctx.get("QuoteNumber").toString()
				+ "_" + ctx.get("UploadedTime").toString() + ".pdf";
		docName = docName.replace(" ", "").replace(":", "").replace("-", "");
		String folderName = ctx.get("QuoteNumber").toString();

		// String userName = "sanjayr";
		// String password = "Sanr55.bgp";
		// String domain = "in";
		// String baseDir = "TestJava";
		
		String outFile = SystemProperties.getInstance().getString("html.basedir") + "data//Policy Form_" + ctx.get("QuoteNumber").toString() + ".pdf";
		/*byte[] docContent = DocManagementUtil.getBytesFromFile(new File(
				SystemProperties.getInstance().getString("html.basedir")
						+ "data//Policy Form_"
						+ ctx.get("QuoteNumber").toString() + ".pdf"));*/
		/*return new DocManagementUtil().uploadDocToSharePoint(docLibPathName,
				folderName, baseDir, docName, docContent, userName, password,
				domain);*/
		//Raghu
		return uploadToDocumentStorage(ctx, folderName, docName, outFile);

	}

	public static List getPolicyFormID(IContext ctx) throws Exception {
		/*AccountUtils.setFormChangesConditions((Context)ctx);
		Context newCtx = new Context();
		newCtx.putAll(ctx);

		newCtx.put("PolicyKey_form", ctx.get("PolicyKey") !=null ? ctx.get("PolicyKey").toString(): ctx.get("PolicyKey"));
		newCtx.put("StateCode_form", ctx.get("StateCode") != null ? ctx.get("StateCode").toString() : ctx.get("StateCode"));
		newCtx.put("PolicyEffectiveDate_form", ctx.get("PolicyEffectiveDate") != null ? ctx.get("PolicyEffectiveDate").toString() : ctx.get("PolicyEffectiveDate"));
		newCtx.put("IsFinalisedOption_form", ctx.get("IsThisOptionFinalised"));
		newCtx.put("isNewFormChanges", ctx.get("isNewFormChanges"));
		logger.debug("Going to execute the stored procedure for Form Attachment");
		List listForm = SqlResources.getSqlMapProcessor(newCtx).select("Account.AccountantFormAttachment_p", newCtx);
		logger.debug("SP has been executed for the Form Attachment");
				return listForm;*/
		List listFormID = new ArrayList();
		List listPolicyForm = null;
		List listPolicyCommonForm = new ArrayList();
		List listPolicyStateForm = new ArrayList();
		if(!"3".equals(ctx.get("CompanyKey").toString()))
		{
		
		// Object objPolicyForm =
		// SqlResources.getSqlMapProcessor(ctx).select("PolicyForm.findAllByPartialKey",
		// ctx);

		Object objPolicyCommonForm = SqlResources.getSqlMapProcessor(ctx)
				.select("SqlStmts.UserStatementpdfquotelettergetCommonForms",
						ctx);

		Object objPolicyStateForm = SqlResources.getSqlMapProcessor(ctx)
				.select("SqlStmts.UserStatementpdfquotelettergetStateForms",
						ctx);

		if (objPolicyCommonForm != null)
			listPolicyCommonForm = (List) objPolicyCommonForm;

		if (objPolicyStateForm != null)
			listPolicyStateForm = (List) objPolicyStateForm;

		if("CT".equals(ctx.get("StateCode").toString())){
			
			if(listPolicyStateForm != null && listPolicyStateForm.size() > 1){
				listPolicyCommonForm.add(Math.min(2, listPolicyCommonForm.size()), listPolicyStateForm.get(1));
				listPolicyStateForm.remove(1);
			}
			
		}
		
		listFormID.addAll(listPolicyCommonForm);

		listFormID.addAll(listPolicyStateForm);

		// if(objPolicyForm != null)
		// listPolicyForm = (List) objPolicyForm;

		// if(listPolicyForm == null)
		// return listFormID;

		// for(int i=0; i<listPolicyCommonForm.size(); i++)
		// {
		// Map map = (Map)listPolicyCommonForm.get(i);
		//			
		// String formID = map.get("FormID").toString();
		// if("OFAC Notice".equals(formID));
		//			
		// }

		// for(int i=0; i<listPolicyCommonForm.size(); i++)
		// {
		// Map map = (Map)listPolicyCommonForm.get(i);
		// listFormID.add(map.get("FormID").toString());
		//			
		// }
		// for(int i=0; i<listPolicyStateForm.size(); i++)
		// {
		// Map map = (Map)listPolicyStateForm.get(i);
		// listFormID.add(map.get("FormID").toString());
		//			
		// }
	}

	
	  if("3".equals(ctx.get("CompanyKey").toString())) {
		  Object objPolicyStateForm = SqlResources.getSqlMapProcessor(ctx).select("SqlStmts.UserStatementpdfquotelettergetPolicyForms",ctx);
		  if (objPolicyStateForm != null)
				listPolicyStateForm = (List) objPolicyStateForm;
		  
		  listFormID.addAll(listPolicyStateForm);
	  }
	 
		return listFormID;
		// List listFormID = new ArrayList();
		// List listForm =
		// SqlResources.getSqlMapProcessor(ctx).select("Form.findAll", ctx);
		// List listStateForm =
		// SqlResources.getSqlMapProcessor(ctx).select("SqlStmts.UserStatementpdfquoteletterStateForm",
		// ctx);
		//		
		// boolean dollarDefenseFlag = false;
		// String IsClaimExpensesType = "N";
		// String IsClaimOptionType = "N";
		// Context newctx = null;
		//		
		// Object objFinalisedQuote =
		// SqlResources.getSqlMapProcessor(ctx).findByKey("SqlStmts.UserStatementsaveAccountstmtsgetFinalisedQuote",
		// ctx);
		// Map mapFinalisedQuote = null;
		//		
		// if(objFinalisedQuote != null && objFinalisedQuote instanceof Map)
		// mapFinalisedQuote = (Map) objFinalisedQuote;
		//		
		// if(mapFinalisedQuote != null && !mapFinalisedQuote.isEmpty())
		// {
		// if(mapFinalisedQuote.get("IsDollarDefense") != null &&
		// "Y".equals(mapFinalisedQuote.get("IsDollarDefense").toString()))
		// dollarDefenseFlag = true;
		// if(mapFinalisedQuote.get("IsClaimExpensesType") != null)
		// IsClaimExpensesType =
		// mapFinalisedQuote.get("IsClaimExpensesType").toString();
		// if(mapFinalisedQuote.get("IsClaimOptionType") != null)
		// IsClaimOptionType =
		// mapFinalisedQuote.get("IsClaimOptionType").toString();
		// }
		//		
		// if(listForm == null && listStateForm == null)
		// return listFormID;
		//		
		//		
		//		
		//		
		// if("MA".equals(ctx.get("StateCode").toString()) ||
		// "OK".equals(ctx.get("StateCode").toString()))
		// {
		// if("Y".equals(IsClaimExpensesType))
		// listForm.remove(2);
		// else if("N".equals(IsClaimExpensesType))
		// {
		// listForm.remove(2);
		// if(listStateForm != null)
		// {
		//					
		// if("MA".equals(ctx.get("StateCode").toString()) &&
		// listStateForm.size()==3)
		// listStateForm.remove(1);
		// else if("OK".equals(ctx.get("StateCode").toString())&&
		// listStateForm.size()==2)
		// listStateForm.remove(1);
		// }
		//				
		// }
		//			
		// if(dollarDefenseFlag)
		// {
		// if("MA".equals(ctx.get("StateCode").toString()))
		// listForm.remove(1);
		// }
		// else
		// {
		// if("MA".equals(ctx.get("StateCode").toString()))
		// listForm.remove(1);
		// if("MA".equals(ctx.get("StateCode").toString()) &&
		// listStateForm.size()>=2)
		// listStateForm.remove(0);
		// }
		// }
		// else
		// {
		// if("N".equals(IsClaimExpensesType))
		// listForm.remove(2);
		//			
		// if(!dollarDefenseFlag)
		// listForm.remove(1);
		// }
		//		
		// if("MA".equals(ctx.get("StateCode").toString()))
		// {
		//			
		// // if("Y".equals(IsClaimOptionType) && listForm.size()==6)
		// // listForm.remove(3);
		// // else if("Y".equals(IsClaimOptionType) && listForm.size()==5)
		// // listForm.remove(2);
		// // else if("Y".equals(IsClaimOptionType) && listForm.size()==4)
		// // listForm.remove(1);
		//			
		// if("Y".equals(IsClaimOptionType) && listForm.size()==7)
		// listForm.remove(3);
		// else if("Y".equals(IsClaimOptionType) && listForm.size()==6)
		// listForm.remove(2);
		// else if("Y".equals(IsClaimOptionType) && listForm.size()==5)
		// listForm.remove(1);
		//			
		//			
		// else if("N".equals(IsClaimOptionType))
		// {
		// // if(listForm.size()==6)
		// // listForm.remove(3);
		// // else if(listForm.size()==5)
		// // listForm.remove(2);
		// // else if(listForm.size()==4)
		// // listForm.remove(1);
		//				
		// if(listForm.size()==7)
		// listForm.remove(3);
		// else if(listForm.size()==6)
		// listForm.remove(2);
		// else if(listForm.size()==5)
		// listForm.remove(1);
		//				
		//				
		//				
		//				
		//				
		// if(listStateForm != null )
		// {
		// if("MA".equals(ctx.get("StateCode").toString()) &&
		// listStateForm.size()==3)
		// listStateForm.remove(2);
		// else if("MA".equals(ctx.get("StateCode").toString()) &&
		// listStateForm.size()==2)
		// listStateForm.remove(1);
		// else if("MA".equals(ctx.get("StateCode").toString()) &&
		// listStateForm.size()==1)
		// listStateForm.remove(0);
		//
		// }
		// }
		// }
		// else
		// {
		// if("N".equals(IsClaimOptionType))
		// {
		// // if(listForm.size()==6)
		// // listForm.remove(3);
		// // else if(listForm.size()==5)
		// // listForm.remove(2);
		// // else if(listForm.size()==4)
		// // listForm.remove(1);
		//				
		// if(listForm.size()==7)
		// listForm.remove(3);
		// else if(listForm.size()==6)
		// listForm.remove(2);
		// else if(listForm.size()==5)
		// listForm.remove(1);
		// }
		// }
		//		
		// for(int i=0; i<listForm.size(); i++)
		// {
		// Map map = (Map)listForm.get(i);
		// listFormID.add(map.get("FormID"));
		//			
		// }
		//		
		// if(listStateForm == null)
		// return listFormID;
		//		
		// for(int i=0; i<listStateForm.size(); i++)
		// {
		// Map map = (Map)listStateForm.get(i);
		// listFormID.add(map.get("FormID"));
		//			
		// }

		// return listFormID;
	}

	public void setFormUrl(IContext ctx, String field) throws Exception {
		ctx.put(field, takeDocumentStorageReference(ctx));

	}

	public void setSignedPDFUrl(IContext ctx, String field) throws Exception {
		ctx.put(field, takeDocumentStorageReference(ctx));
	}

	public void setUrl(IContext ctx, String docName, String field)
			throws Exception {
		ctx.put(field, takeDocumentStorageReference(ctx));

	}
	public void insertInBrokerageDocumentLW(IContext ctx) throws Exception {
		
		SqlResources.getSqlMapProcessor(ctx).insert("BrokerageDocumentLW.insert", ctx);
	}
	public void insertInDocumentArchive(IContext ctx) throws Exception {
		if (ctx.get("DocFileName") == null || ctx.get("DocUrl") == null
				|| ctx.get("DocFileName").toString().trim().length() == 0
				|| ctx.get("DocUrl").toString().trim().length() == 0)
			throw new Exception("Document archive requires DocFileName and DocUrl.");
		SqlResources.getSqlMapProcessor(ctx).insert("DocumentArchive.insert",
				ctx);
	}

	public boolean validateFileName(String filePath) throws Exception {
		if (filePath == null)
			return true;

		if (filePath != null && "".equals(filePath.trim()))
			return true;

		int index = filePath.lastIndexOf("\\");
		// if(index <= 0)
		// return false;

		String fileName = null;
		if (index > 0) {
			fileName = filePath.substring(index + 1, filePath.length());
			if (fileName.indexOf(".") <= 0)
				return false;
		} else
			fileName = filePath;

		int indexExt = fileName.lastIndexOf(".");
		String ext = fileName.substring(indexExt + 1, fileName.length());

		if (ext.length() < 3)
			return false;

		return true;
	}

	public void downloadDocument(IContext ctx, HttpServletRequest req,
			HttpServletResponse res) throws Exception {
		String docUrl = null;
		if (ctx.get("DocUrl") != null)
			docUrl = ctx.get("DocUrl").toString();

		if (docUrl == null || docUrl.trim().length() == 0)
			throw new Exception("Document URL is missing.");
		String folderName = ctx.get("QuoteNumber") == null
				? null : ctx.get("QuoteNumber").toString();
		try (ServletOutputStream outputStream = res.getOutputStream()) {
			// Legacy: new DocManagementUtil().downloadDocFromSharePoint(...);
			long bytes = FileSystemDocumentStorage.downloadTo(docUrl,
					SystemProperties.getInstance().getString("document.storage.directory"),
					folderName, outputStream);
			if (bytes == 0)
				throw new Exception("No document content returned from storage for " + docUrl);
			outputStream.flush();
		}
	}

	public void uploadQuoteLetter(IContext ctx) throws Exception {
		logger.debug("Going to upload quote letter");
		String htmlDir = SystemProperties.getInstance().getString(
				"html.basedir");
		String outFile = htmlDir + "data//QuoteLetter_"
				+ ctx.get("QuoteNumber").toString() + ".pdf";

		// Uploading Quote letter
		AccountUtils.populateLastUpdateTimeStamp(ctx);
		
		 
		 String result = "";
		result = new DocumentManagment().uploadDocument(ctx, outFile);
		{ if (logger.isDebugEnabled()) logger.debug("Result from uploadQuoteLetter(IContext ctx) " + result); }
		String DocFileName = outFile.substring(outFile.lastIndexOf("//") + 2,
				outFile.length());
		if (result != null) {
			// ctx.put("DocTitle", DocFileName);

			int index = DocFileName.indexOf(".");
			String fileName1 = DocFileName.substring(0, index);
			ctx.put("DocTitle", fileName1);

			String fileExt = DocFileName.substring(index + 1, DocFileName
					.length());
			DocFileName = fileName1 + "_" + ctx.get("UploadedTime").toString()
					+ "." + fileExt;
			DocFileName = DocFileName.replace(" ", "").replace(":", "")
					.replace("-", "");

			ctx.put("Comment", "");
			ctx.put("DocFileName", DocFileName);

			ctx.put("UploadedType", "Auto");

			ctx.put("DocType", "QL");

			setUrl(ctx, DocFileName, "DocUrl");
			RuleUtils.executeRule(ctx,"AccountantRule.assignDateTimeNUser");
			insertInDocumentArchive(ctx);
			logger.debug("Upload Quote letter end");
		}
		// End uploading Quote letter
	}

	public void uploadQuickQuoteLetter(IContext ctx) throws Exception {
		logger.debug("Upload QuickQuoteLetter Start ");
		String htmlDir = SystemProperties.getInstance().getString(
				"html.basedir");
		String outFile = htmlDir + "data//QuickQuoteOptions_"
				+ ctx.get("QuoteNumber").toString() + ".pdf";

		// Uploading Quote letter
		AccountUtils.populateLastUpdateTimeStamp(ctx);
		String result = new DocumentManagment().uploadDocument(ctx, outFile);
		// String result = "";
		{ if (logger.isDebugEnabled()) logger.debug("Result from uploadQuickQuoteLetter(IContext ctx) " + result); }
		String DocFileName = outFile.substring(outFile.lastIndexOf("//") + 2,
				outFile.length());
		if (result != null) {
			// ctx.put("DocTitle", DocFileName);

			int index = DocFileName.indexOf(".");
			String fileName1 = DocFileName.substring(0, index);
			ctx.put("DocTitle", fileName1);

			String fileExt = DocFileName.substring(index + 1, DocFileName
					.length());
			DocFileName = fileName1 + "_" + ctx.get("UploadedTime").toString()
					+ "." + fileExt;
			DocFileName = DocFileName.replace(" ", "").replace(":", "")
					.replace("-", "");

			ctx.put("Comment", "");
			ctx.put("DocFileName", DocFileName);

			ctx.put("UploadedType", "Auto");

			ctx.put("DocType", "QQL");

			setUrl(ctx, DocFileName, "DocUrl");
			insertInDocumentArchive(ctx);
			logger.debug("Upload QuickQuoteLetter End ");
		}
		// End uploading Quick Quote option
	}

	public String getUserName() throws Exception {

		return "";
	}

	public String getUserPassword() throws Exception {

		return "";
	}

	public String getDomainName() throws Exception {

		return "";
	}

	public String getSharePointBaseDirectory() throws Exception {
		
		return SystemProperties.getInstance().getString("document.storage.directory");
	}

	public String getSharePointURL() throws Exception {
   
		return "/";
	}

	private String generatePolicyIssueBody(IContext ctx) throws Exception {
		String msg = "";

		msg = msg + "<table>";
		msg = msg + "<tr>";
		msg = msg + "<td>";

		msg = msg + "Dear " + ctx.get("AccountName").toString() + ","
				+ "<br/><br/>";
		msg = msg
				+ "Thank you for your business. We are honored to serve your professional liability insurance needs."
				+ "<br/><br/>";
		msg = msg
				+ "We will be contacting you throughout the upcoming year as we introduce more online features and our policyholder's only web resource center."
				+ "<br/><br/>";
		msg = msg
				+ "Attached is your policy information. Please review the attached policy carefully and advise us immediately if there are any conflicts between the actual policy and the information provided by us throughout the process. "
				+ "<br/><br/>";
		msg = msg + "Best Regards," + "<br/><br/>";
		msg = msg + "Kyle Nieman" + "<br/>";
		msg = msg + "President/CEO" + "<br/>";

		msg = msg + "</td>";
		msg = msg + "</tr>";
		msg = msg + "</table>";

		return msg;

	}

	public void downloadRiskDocument(IContext ctx, HttpServletRequest req,
			HttpServletResponse res) throws Exception {

		ServletOutputStream outputStream = res.getOutputStream();

		String DocFileName = null;
		if (ctx.get("DocFileName") != null)
			DocFileName = ctx.get("DocFileName").toString();

		if (DocFileName == null)
			return;

		String htmlDir = SystemProperties.getInstance().getString(
				"html.basedir");

		String filePath = htmlDir + "download//" + DocFileName;
		File file = new File(filePath);

		// Legacy: DocManagementUtil.getBytesFromFile(file);
		byte[] rb = Files.readAllBytes(file.toPath());
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		bout.write(rb);
		bout.writeTo(outputStream);

		outputStream.flush();
		bout.close();
		outputStream.close();

	}
	
	public void uploadOffRiskLetter(IContext ctx) throws Exception {

		String htmlDir = SystemProperties.getInstance().getString(
				"html.basedir");
		String outFile = htmlDir + "data//OffRiskLetters_" + ctx.get("AccountNameOffRisk") +
				".pdf";
				

		// Uploading Quote letter
		AccountUtils.populateLastUpdateTimeStamp(ctx);
		String result = new DocumentManagment().uploadDocument(ctx, outFile);
		{ if (logger.isDebugEnabled()) logger.debug("Result from DocumentManagment().uploadDocument(ctx, outFile); " + result); }
		String DocFileName = outFile.substring(outFile.lastIndexOf("//") + 2,
				outFile.length());
		{ if (logger.isDebugEnabled()) logger.debug("DocFileName - - " + DocFileName); }
		if (result != null) {
			// ctx.put("DocTitle", DocFileName);
			
			int index = DocFileName.lastIndexOf(".");
			String fileName1 = DocFileName.substring(0, index);
			ctx.put("DocTitle", fileName1);
			{ if (logger.isDebugEnabled()) logger.debug("DocTitle - - " + ctx.get("DocTitle")); }
			String fileExt = DocFileName.substring(index + 1, DocFileName
					.length());
			DocFileName = fileName1 + "_" + ctx.get("UploadedTime").toString()
					+ "." + fileExt;
			DocFileName = DocFileName.replace(" ", "").replace(":", "")
					.replace("-", "");

			ctx.put("Comment", "");
			ctx.put("DocFileName", DocFileName);
			{ if (logger.isDebugEnabled()) logger.debug("DocFileName - - " + ctx.get("DocFileName")); }
			ctx.put("UploadedType", "Auto");

			ctx.put("DocType", "OR");

			setUrl(ctx, DocFileName, "DocUrl");
			{ if (logger.isDebugEnabled()) logger.debug("DocUrl - - " + ctx.get("DocUrl")); }
		}
		// End uploading Quote letter
	}
	
	public void downloadDocumentOffRisk(IContext ctx) throws Exception {
		try {
			String uploaddirectorypath = SystemProperties.getInstance().getString("offriskupload.uploaddirectory");
			String fileName = uploaddirectorypath + ctx.get("DocFileName").toString();
	
			String docUrl = null;
			if (ctx.get("DocUrl") != null)
				docUrl = ctx.get("DocUrl").toString();
	
			if (docUrl == null)
				return;
	
			String folderName = ctx.get("QuoteNumber") == null
					? null : ctx.get("QuoteNumber").toString();
			try (FileOutputStream outputStream = new FileOutputStream(new File(fileName))) {
				// Legacy: new DocManagementUtil().downloadDocFromSharePoint(...);
				FileSystemDocumentStorage.downloadTo(docUrl,
						SystemProperties.getInstance().getString("document.storage.directory"),
						folderName, outputStream);
			}
		}catch(Exception ex){
			logger.error("error in downloadDocumentOffRisk in DocumentManagement::::"+ex.getMessage(), ex);
		}
	}
	

	public void processIndicationDocument (IContext ctx) throws Exception {		
		logger.debug("processIndicationDocument started  ........ ");			
		String environmentVar = null;
		try{
			String skipUpload = SystemProperties.getInstance().getString("appl." + ctx.getProject() + ".skipupload");
			if(!"Y".equals(skipUpload)){
			
			new AccountUtils().setImagesPathToTemplate(ctx);
			
			Object objIndication = SqlResources.getSqlMapProcessor(ctx).findByKey("SqlStmts.UserStatementsaveAccountstmtsgetMaxIndicationDocumentID", ctx);
			Map mapIndication = null;	
			int version = 0;
			if(objIndication != null && objIndication instanceof Map)
			{
				mapIndication = (Map) objIndication;
				version = Integer.parseInt(mapIndication.get("IndicationVersion").toString());
				version++;
			}
			
			String formNumber ="Indication_" + version;				
			String outFile = SystemProperties.getInstance().getString("html.basedir") + "data//" + formNumber + ".pdf";		
			File pdfFile = new File(outFile);
			
			OutputStream out = new java.io.FileOutputStream(pdfFile);
			out = new java.io.BufferedOutputStream(out);			
			String baseUrl = null;
			if(ctx.get("baseUrl") != null)
				baseUrl = ctx.get("baseUrl").toString();			
			baseUrl = "file:///" + ((ServletContext)ctx.get(HtmlConstants.DOCUMENTSERVLETCONTEXT)).getRealPath("/");
			
			ServletContextURIResolver uriResolver = null;
			
			if(ctx.get("DocumentUriResolver") != null)
				uriResolver = (ServletContextURIResolver)ctx.get(HtmlConstants.DOCUMENTURIRESOLVER);
			
			new DownloadForm().processIndicationForm((Context)ctx, out, baseUrl, uriResolver);			
			
			environmentVar = SystemProperties.getInstance().getString("appl." + ctx.getProject() + ".environment");
			if (environmentVar != null &&  !"DEV".equals(environmentVar))
				uploadIndication(ctx, outFile);
			
			if(pdfFile.exists())
				pdfFile.delete();
					
			logger.debug("processIndicationDocument end  ........");

			}
		}catch (Exception e){
			logger.error("Error in DocumentManagment.processIndicationDocument", e);
			throw e;
		}		
	}
	
	public void uploadIndication(IContext ctx, String outFile) throws Exception {
        
        AccountUtils.populateLastUpdateTimeStamp(ctx);              
        String result = uploadDocument(ctx, outFile);
        String DocFileName = outFile.substring(outFile.lastIndexOf("//")+2, outFile.length());
        
        if(result != null)
        {     
          int index = DocFileName.indexOf(".");
          String fileName1 = DocFileName.substring(0, index);
          ctx.put("DocTitle", fileName1);

          
          String fileExt = DocFileName.substring(index+1, DocFileName.length());
          DocFileName = fileName1 + "_" + ctx.get("UploadedTime").toString() + "."+ fileExt;
          DocFileName = DocFileName.replace(" ", "").replace(":", "").replace("-", "");
          
          ctx.put("Comment", "");                 
          ctx.put("DocFileName", DocFileName);
          
          ctx.put("UploadedType", "Auto");          
          ctx.put("DocType", "INJ");
          
          setUrl(ctx, DocFileName, "DocUrl");
          insertInDocumentArchive(ctx);
        }
        // End uploading Indication
	}
	
	// Endrosment start
	public void processEndorementDocument (IContext ctx) {		
		logger.debug("processEndorementDocument started  ........ ");
		if(ctx.get("TransactionTypeKey") == null) {
			logger.error("Endorsement Type for Policy Number : " + ctx.get("PolicyNumber") + " was not defined");
			return;
		}
		
		String TransactionTypeKey = ctx.get("TransactionTypeKey").toString();
		
		try{
			new AccountUtils().setImagesPathToTemplate(ctx);			
			List getPolicyNumber=SqlResources.getSqlMapProcessor(ctx).select("SqlStmts.UserStatementManualBoQueriesgetPolicyNumber",ctx);
			ctx.putAll((Map) getPolicyNumber.get(0));
			String dbPolicyNumber=ctx.get("DBPolicyNumber")!=null?ctx.get("DBPolicyNumber").toString():"";
			if("".equals(dbPolicyNumber)) {
				throw new PolicyNumberException();
			}
			
			//Populating all keys in Context
			Object obj = SqlResources.getSqlMapProcessor(ctx).findByKey("SqlStmts.UserStatementManualBoQueriesGetPolicyKeys", ctx);
			Map keysMap = null;		
			if(obj != null && obj instanceof Map){
				keysMap = (Map) obj;
				ctx.putAll(keysMap);
			}		
				
			logger.debug("Populated all keys in Context...............");
//			boolean isRatingNew = false;
//	        Object objRatingRule = RuleUtils.executeRule(ctx,"AccountantRule.checkNewFiling");
//	        if (objRatingRule != null && objRatingRule instanceof Boolean) {
//	               isRatingNew = (Boolean) objRatingRule;
//	        }
//	        if(isRatingNew)
//	        	ctx.put("IsNewFiling", "Y");
			
			List listFormID = null;
			String fileName = null,file=null;
			new DownloadForm().fetchDataForXmlEndorsement((Context)ctx);
			
			
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
			
			if(TransactionTypeKey.equals(com.userbo.AccountantConstants.CANCELLATION_ENDORSEMENT))
				fileName=fileName+"_Cancellation_Endorsement";
			if(TransactionTypeKey.equals(com.userbo.AccountantConstants.REINSTATEMENT_ENDORSEMENT))
				fileName=fileName+"_Reinstatement_Endorsement";
			if(TransactionTypeKey.equals(com.userbo.AccountantConstants.DEDUCTIBLE_ENDORSEMENT))
				fileName=fileName+"_Deductible_Endorsement";
			if(TransactionTypeKey.equals(com.userbo.AccountantConstants.LIMIT_ENDORSEMENT))
				fileName=fileName+"_Limit_Endorsement";
			if(TransactionTypeKey.equals(com.userbo.AccountantConstants.NAMEADDRESS_ENDORSEMENT))
				fileName=fileName+"_NameAddress_Endorsement";
			if(TransactionTypeKey.equals(com.userbo.AccountantConstants.PRIORACTDATE_ENDORSEMENT))
				fileName=fileName+"_PriorActDate_Endorsement";
			if(TransactionTypeKey.equals(com.userbo.AccountantConstants.POLICYEXTENSION_ENDORSEMENT))
				fileName=fileName+"_PolicyExtension_Endorsement";
			if(TransactionTypeKey.equals(com.userbo.AccountantConstants.ERP_ENDORSEMENT))
				fileName=fileName+"_ERP_ENDORSEMENT";
			
			String outFile = SystemProperties.getInstance().getString("html.basedir") + "data//" + fileName +".pdf";		
			File pdfFile = new File(outFile);
			
			OutputStream out = new java.io.FileOutputStream(pdfFile);
			out = new java.io.BufferedOutputStream(out);			
			String baseUrl = null;
			if(ctx.get("baseUrl") != null)
				baseUrl = ctx.get("baseUrl").toString();			
			baseUrl = "file:///" + ((ServletContext)ctx.get(HtmlConstants.DOCUMENTSERVLETCONTEXT)).getRealPath("/");
			
			ServletContextURIResolver uriResolver = null;
			
			if(ctx.get("DocumentUriResolver") != null)
				uriResolver = (ServletContextURIResolver)ctx.get(HtmlConstants.DOCUMENTURIRESOLVER);
			
			new DownloadForm().generateEndorsement((Context)ctx, out, baseUrl, uriResolver);			
			//new DownloadForm().generateEndorsement((Context)ctx, outFile);			
			//new DownloadForm().testForm((Context)ctx, listFormID, outFile);
			AccountUtils.getPolicyData((Context)ctx);
			AccountUtils.populateLastUpdateTimeStamp(ctx);
			String result = "";
			result = uploadEndorsementFormDocument(ctx, pdfFile.getName());
			
						
			if(result == null || (result != null && !result.equals("File added successfully!" ) )) {
				AccountUtils.populateError(ctx, "DocUploadError", "Endorsement could not be uploaded");
				return;
			}
				
			if(result != null && result.equals("File added successfully!")) {
				String docTitle = pdfFile.getName();				
				int index = docTitle.lastIndexOf(".");
				String docName = docTitle.substring(0, index);
				String docExt = docTitle.substring(index + 1, docTitle.length());
				docName = docName + "_" + ctx.get("UploadedTime").toString() + "." + docExt;				
				docName = docName.replace(" ", "").replace(":", "").replace("-", "");
//				String DocFileName = "Endorsement_Form_"+quoteNumber+"_"+formNumber+"_" +ctx.get("UploadedTime").toString() + ".pdf";	
//				DocFileName = DocFileName.replace(" ", "").replace(":", "").replace("-", "");				
//				String docTitle = "Endorsement_Form_"+quoteNumber+"_"+formNumber+".pdf";
				
				ctx.put("DocTitle", docTitle);
				ctx.put("DocFileName", docName);				
				ctx.put("DocType", "EF");				
				ctx.put("UploadedType", "Auto");
				setEndorsementFormUrl(ctx, "DocUrl",docName);	
				RuleUtils.executeRule(ctx,"AccountantRule.AssignCreatedByAndCreatedTimeStamp");
				insertInDocumentArchive(ctx);
			}
			
			if(TransactionTypeKey.equals(com.userbo.AccountantConstants.CANCELLATION_ENDORSEMENT)){
				ctx.put("TransactionDate", ctx.get("CancellationEffectiveDate"));
				ctx.put("event_name", com.userbo.AccountantConstants.CANCELLATIONENDORSEMENT_EMAIL);
			}else if(TransactionTypeKey.equals(com.userbo.AccountantConstants.REINSTATEMENT_ENDORSEMENT)){
				ctx.put("TransactionDate", ctx.get("ReinstatementEffectiveDate"));
				ctx.put("event_name", com.userbo.AccountantConstants.REINSTATEMENTENDORSEMENT_EMAIL);
			}else
				ctx.put("event_name", com.userbo.AccountantConstants.ENDORSEMENT_EMAIL);
			
			EndorsementUtilities.fetchDataForEmail((Context)ctx, outFile);
			if(pdfFile.exists())
				pdfFile.delete();
						
			logger.debug("processEndorsementDocument end  ........");			
		}catch (Exception e){
			logger.error("Error in DocumentManagment.processEndorementDocument", e);
		}		
	}
	
	public String uploadEndorsementFormDocument (IContext ctx, String fileName) throws Exception {
		
		//String docName = "Endorsement_Form_"+quoteNumber+"_"+formNumber+"_"+ctx.get("UploadedTime").toString() + ".pdf";
		//byte[] docContent = DocManagementUtil.getBytesFromFile(new File(SystemProperties.getInstance().getString("html.basedir") + "data//" + fileName));
		String outFile = SystemProperties.getInstance().getString("html.basedir") + "data//" + fileName;
		
		int index = fileName.lastIndexOf(".");
		String docName = fileName.substring(0, index);
		String docExt = fileName.substring(index + 1, fileName.length());
		docName = docName + "_" +ctx.get("UploadedTime").toString() + "." + docExt;
		
		docName = docName.replace(" ", "").replace(":", "").replace("-", "");
		String folderName = ctx.get("QuoteNumber").toString();		
		
//		String userName = "sanjayr";
//		String password = "Sanr55.bgp";
//		String domain = "in";
//		String baseDir = "TestJava";
		
		//return new DocManagementUtil().uploadDocToSharePoint(docLibPathName, folderName, baseDir, docName, docContent, userName, password, domain);
		//Raghu
		return uploadToDocumentStorage(ctx, folderName, docName, outFile);
	}
	
	public void setEndorsementFormUrl(IContext ctx, String field, String docName) throws Exception {			
		ctx.put(field, takeDocumentStorageReference(ctx));
	}
	
	public static void generateInsurenceCertificate (Context ctx) {		
		logger.debug("Insurance certificate generation started started  ........ ");
	
		
		String TransactionTypeKey = ctx.get("TransactionTypeKey").toString();
		DocumentManagment docManage = new DocumentManagment();
		try{
			String fileName="InsuranceCertificate";
			
			String outFile = SystemProperties.getInstance().getString("html.basedir") + "data//" + fileName +".pdf";		
			File pdfFile = new File(outFile);
			
			OutputStream out = new java.io.FileOutputStream(pdfFile);
			out = new java.io.BufferedOutputStream(out);			
			String baseUrl = null;
			if(ctx.get("baseUrl") != null)
				baseUrl = ctx.get("baseUrl").toString();			
			baseUrl = "file:///" + ((ServletContext)ctx.get(HtmlConstants.DOCUMENTSERVLETCONTEXT)).getRealPath("/");
			
			ServletContextURIResolver uriResolver = null;
			
			if(ctx.get("DocumentUriResolver") != null)
				uriResolver = (ServletContextURIResolver)ctx.get(HtmlConstants.DOCUMENTURIRESOLVER);
			
			
			String foFile = "";		
			uriResolver=(ServletContextURIResolver)ctx.get(HtmlConstants.DOCUMENTURIRESOLVER);
			try{
				AccountUtils.getPolicyData(ctx);
				if("3".equals(ctx.get("CompanyKey").toString()))	
					foFile = SystemProperties.getInstance().getString("xsl.filepath.COI") + "ISMIE//Endorsement/LiabilityInsCertificate.xsl";
				else
					foFile = SystemProperties.getInstance().getString("xsl.filepath.endorsement") + "foxslnew/endorsement/LiabilityInsCertificate.xsl";
					            
				{ if (logger.isDebugEnabled()) logger.debug("generateForm--------"+foFile); }
				new XML2PDF().convertPOToPDF(foFile, new StringBuffer(new com.formgenerator.DownloadForm().generateDataXml(ctx)), out, baseUrl, uriResolver);
			}
			catch(Exception e){
				logger.error("Problem in generating Insurance Certificate for Policy Number : " +ctx.get("PolicyNumber") + " " + e.getMessage(), e);
				throw e;
			}
			//new DownloadForm().generateEndorsement((Context)ctx, outFile);			
			//new DownloadForm().testForm((Context)ctx, listFormID, outFile);
			
			AccountUtils.populateLastUpdateTimeStamp(ctx);
			String result = "";
			result = docManage.uploadEndorsementFormDocument(ctx, pdfFile.getName());
			
						
			if(result == null || (result != null && !result.equals("File added successfully!") )) {
				AccountUtils.populateError(ctx, "DocUploadError", "Endorsement could not be uploaded");
				return;
			}
				
			if(result != null && result.equals("File added successfully!")) {
				String docTitle = pdfFile.getName();				
				int index = docTitle.lastIndexOf(".");
				String docName = docTitle.substring(0, index);
				String docExt = docTitle.substring(index + 1, docTitle.length());
				docName = docName + "_" + ctx.get("UploadedTime").toString() + "." + docExt;				
				docName = docName.replace(" ", "").replace(":", "").replace("-", "");
//				String DocFileName = "Endorsement_Form_"+quoteNumber+"_"+formNumber+"_" +ctx.get("UploadedTime").toString() + ".pdf";	
//				DocFileName = DocFileName.replace(" ", "").replace(":", "").replace("-", "");				
//				String docTitle = "Endorsement_Form_"+quoteNumber+"_"+formNumber+".pdf";
				
				ctx.put("DocTitle", docTitle);
				ctx.put("DocFileName", docName);				
				ctx.put("DocType", "EF");				
				ctx.put("UploadedType", "Auto");
				docManage.setEndorsementFormUrl(ctx, "DocUrl",docName);	
				docManage.insertInDocumentArchive(ctx);
			}
			
			if(TransactionTypeKey.equals(com.userbo.AccountantConstants.CANCELLATION_ENDORSEMENT)){
				ctx.put("TransactionDate", ctx.get("CancellationEffectiveDate"));
				ctx.put("event_name", com.userbo.AccountantConstants.CANCELLATIONENDORSEMENT_EMAIL);
			}else if(TransactionTypeKey.equals(com.userbo.AccountantConstants.REINSTATEMENT_ENDORSEMENT)){
				ctx.put("TransactionDate", ctx.get("ReinstatementEffectiveDate"));
				ctx.put("event_name", com.userbo.AccountantConstants.REINSTATEMENTENDORSEMENT_EMAIL);
			}
			else if(TransactionTypeKey.equals(com.userbo.AccountantConstants.INSURANCE_CERTIFICATE)){
				ctx.put("TransactionDate", ctx.get("CertificateRequestedDate"));
				ctx.put("event_name", com.userbo.AccountantConstants.INSURANCECERTIFICATE_EMAIL);
			}
			else
				ctx.put("event_name", com.userbo.AccountantConstants.ENDORSEMENT_EMAIL);
			
			EndorsementUtilities.fetchDataForEmail((Context)ctx, outFile);
			if(pdfFile.exists())
				pdfFile.delete();
						
			logger.debug("processEndorsementDocument end  ........");			
		}catch (Exception e){
			logger.error("Error in DocumentManagment.generateInsurenceCertificate", e);
		}		
	}
	
	
	public static void generateERPQuoteLetter (Context ctx) {		
		logger.debug("Erp letter generation has started  ........ ");
	
		File pdfFile = null;
		OutputStream out = null;
		AccountUtils.getPolicyData(ctx);
		DocumentManagment docManage = new DocumentManagment();
		try{
			EndorsementUtilities.fetchERPLetterData(ctx);
			String fileName="ERPQuoteLetter";
			
			String outFile = SystemProperties.getInstance().getString("html.basedir") + "data//" + fileName +".pdf";		
			pdfFile = new File(outFile);
			
			out = new java.io.FileOutputStream(pdfFile);
			out = new java.io.BufferedOutputStream(out);			
			String baseUrl = null;
			if(ctx.get("baseUrl") != null)
				baseUrl = ctx.get("baseUrl").toString();			
			baseUrl = "file:///" + ((ServletContext)ctx.get(HtmlConstants.DOCUMENTSERVLETCONTEXT)).getRealPath("/");
			
			ServletContextURIResolver uriResolver = null;
			
			if(ctx.get("DocumentUriResolver") != null)
				uriResolver = (ServletContextURIResolver)ctx.get(HtmlConstants.DOCUMENTURIRESOLVER);
			
			
			String foFile = "";		
			uriResolver=(ServletContextURIResolver)ctx.get(HtmlConstants.DOCUMENTURIRESOLVER);
			try{	
				
				if("3".equals(ctx.get("CompanyKey").toString()))
					foFile = SystemProperties.getInstance().getString("html.basedir") + "ISMIE//Endorsement//ERPQuoteLetter.xsl";
				else	
					foFile = SystemProperties.getInstance().getString("html.basedir") + "foxsl2017//foxslnew//endorsement//ERPQuoteLetter.xsl";
				
				String environment=SystemProperties.getInstance().getString("appl.AccountantIns.environment.production");
				ctx.put("environmentproduction",environment);
				{ if (logger.isDebugEnabled()) logger.debug("generateForm--------"+foFile); }
				new XML2PDF().convertPOToPDF(foFile, new StringBuffer(new com.formgenerator.DownloadForm().generateDataXml(ctx)), out, baseUrl, uriResolver);
			}
			catch(Exception e){
				logger.error("Problem in generating Insurance Certificate for Policy Number : " +ctx.get("PolicyNumber") + " " + e.getMessage(), e);
				throw e;
			}
			//new DownloadForm().generateEndorsement((Context)ctx, outFile);			
			//new DownloadForm().testForm((Context)ctx, listFormID, outFile);
			
			AccountUtils.populateLastUpdateTimeStamp(ctx);
			String result = "";
			result = docManage.uploadEndorsementFormDocument(ctx, pdfFile.getName());
			
						
			if(result == null || (result != null && !result.equals("File added successfully!") )) {
				AccountUtils.populateError(ctx, "DocUploadError", "ERP Letter could not be uploaded");
				return;
			}
				
			if(result != null && result.equals("File added successfully!")) {
				String docTitle = pdfFile.getName();				
				int index = docTitle.lastIndexOf(".");
				String docName = docTitle.substring(0, index);
				String docExt = docTitle.substring(index + 1, docTitle.length());
				docName = docName + "_" + ctx.get("UploadedTime").toString() + "." + docExt;				
				docName = docName.replace(" ", "").replace(":", "").replace("-", "");
//				String DocFileName = "Endorsement_Form_"+quoteNumber+"_"+formNumber+"_" +ctx.get("UploadedTime").toString() + ".pdf";	
//				DocFileName = DocFileName.replace(" ", "").replace(":", "").replace("-", "");				
//				String docTitle = "Endorsement_Form_"+quoteNumber+"_"+formNumber+".pdf";
				
				ctx.put("DocTitle", docTitle);
				ctx.put("DocFileName", docName);				
				ctx.put("DocType", "EF");				
				ctx.put("UploadedType", "Auto");
				docManage.setEndorsementFormUrl(ctx, "DocUrl",docName);	
				docManage.insertInDocumentArchive(ctx);
			}
			
			
				ctx.put("event_name", AccountantConstants.QUOTELETTER_EMAIL);
			
			EndorsementUtilities.fetchDataForEmail((Context)ctx, outFile);
			if(pdfFile.exists())
				pdfFile.delete();
						
			logger.debug("processEndorsementDocument end  ........");	
			
		}catch (Exception e){
			logger.error("error occured while processing "+e.getMessage(), e);
		} finally {
			/*code by sukhi 26/09/2018*/
			try {
				if(out != null){
					out.close();
					out = null;
				}
			} catch(Exception ex){
				logger.error("Error in DocumentManagment.generateERPQuoteLetter", ex);
			}
			pdfFile=null;
		}	
	}
	
	public void regeneratePolicyDocument(IContext ctx) throws Exception {
		HttpServletRequest req = (HttpServletRequest) ctx	.get("DocumentRequest");
		HttpServletResponse res = (HttpServletResponse) ctx
				.get("DocumentResponse");
		ServletContext servletContext = (ServletContext) ctx
				.get("DocumentServletContext");
	
	
		boolean IsManualPremium=false ;
		String policyNumber ="";
		OutputStream out = null;
		File pdfFile = null;
		boolean dollarDefenseFlag=false;
		
		try{
			Map dataMap=null;
			List dataList=SqlResources.getSqlMapProcessor(ctx).select("Policy.FetchIssuedPolicyDataForDoc_p",ctx);
				if(dataList!=null && !dataList.isEmpty())
					dataMap=(Map)dataList.get(0);
				logger.debug("Regenerating document");
				if (dataMap == null)
					throw new PolicyNumberException();
				  policyNumber =dataMap.get("PolicyNumber")!=null?dataMap.get("PolicyNumber").toString():"";
			    if("".equals(policyNumber))
					throw new PolicyNumberException();
				
			    ctx.put("PolicyNumber", policyNumber);
				AccountUtils.populateLastUpdateTimeStamp(ctx);
				
				Object obj = SqlResources.getSqlMapProcessor(ctx).findByKey("SqlStmts.UserStatementprocessquotegetPolicyKeys", ctx);
				Map keysMap = null;
				if (obj != null && obj instanceof Map) {
					keysMap = (Map) obj;
					ctx.putAll(keysMap);
				}

				obj = SqlResources.getSqlMapProcessor(ctx).findByKey("SqlStmts.BasicInfoviewCoveragePolicy", ctx);
				Map policyCovMap = null;

				if (obj != null && obj instanceof Map) {
					policyCovMap = (Map) obj;
					ctx.putAll(policyCovMap);
				}

				obj = SqlResources.getSqlMapProcessor(ctx).findByKey("SqlStmts.UserStatementpdfquotelettergetPriorPolicyInfo",ctx);
				Map policyInfo = null;

				if (obj != null && obj instanceof Map) {
					policyInfo = (Map) obj;
					ctx.putAll(policyInfo);
				}

				logger.debug("Populated all keys in Context...............");

				QuoteLetter.getFinalisedQuote(ctx);
				logger.debug("Get Finalised Quote.............................");

				List listFormID = AccountUtils.populateFinalisedPolicyForm(ctx);				
				// Form
				//List listFormID = getPolicyFormID(ctx);			

				dollarDefenseFlag = checkDollarDefenseFlagWithFinalisedOption(ctx);
				new DownloadForm().populateEndorsement((Context)ctx, listFormID);
				new DownloadForm().fetchDataForXml((Context)ctx);
				//String outFile = SystemProperties.getInstance().getString("html.basedir") + "data//Policy Form_"+ ctx.get("QuoteNumber").toString()	+ ".pdf";
				//pdfFile = new File(outFile);
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

				
				
				
				if (out != null)
					out.close();
			// Application PDF
				/*outFile = SystemProperties.getInstance().getString("html.basedir")+ "data//Accountant_" + ctx.get("QuoteNumber").toString()+ ".pdf";
				pdfFile = new File(outFile);
				out = new java.io.FileOutputStream(pdfFile);
				out = new java.io.BufferedOutputStream(out);*/

				/*if (ctx.get("PolicyStatusKey") != null&& "1".equals(ctx.get("PolicyStatusKey").toString())) {
					ByteArrayOutputStream baos = new PrintPDF().makePdf((Context) ctx);
					baos.writeTo(out);
				} else {
//					baseUrl = "file:///" + ((ServletContext)ctx.get(HtmlConstants.DOCUMENTSERVLETCONTEXT)).getRealPath("/");
					new DownloadFOP().makeRenewPdfForMail((Context) ctx, out,baseUrl,uriResolver);
				}

				if (out != null)
					out.close();
				
				*/
								
				ctx.remove("IssuedDate");
				ctx.remove("PolicyNumber");		
				ctx.put("IsDocumentProcessed","Y");
				
				
		}
		catch (Exception e)
		{
			ctx.put("IsDocumentProcessed", "N");
			logger.error("processDocumentManagment failed  ........" + e, e);
			throw e;
		}finally {
			/*code by sukhi 26/09/2018*/
			pdfFile = null;
			if(out != null){
				out.close();
				out = null;
			}
		
			
			
		}
				
		
	}
	
	public void processLossRunAndPriorDocument (IContext ctx) throws Exception {		
		logger.debug("processLossRunAndPriorDocument started  ........ ");			
		
		File pdfFile = null;
		FileItem uploadFile = null;
		String fileName = null;
		String docTitle = "T";
		String searchType = null;
		String docType = null;
		String docFileName = null;
		
		try{
			try {
				List fileItems = (List)ctx.get("fileItems");

				Iterator i = fileItems.iterator();

				while (i.hasNext()) {
					FileItem fi = (FileItem) i.next();

					if (fi.isFormField()) {
						String name = fi.getFieldName();
						logger.debug(name);
						if (name.equalsIgnoreCase("DocTitle")) {
							docTitle = fi.getString();
							docTitle = docTitle.replace("&", "$AND$");
							docTitle = docTitle.replace("%", "$PER$");
							docTitle = docTitle.replace("'", "$APH$");

						} else if (name.equalsIgnoreCase("SearchType")) {
							searchType = fi.getString();
							{ if (logger.isDebugEnabled()) logger.debug("SearchType - " + searchType); }
						}

						// String value = fi.getString();
					} else {

						// filename on the client
						fileName = fi.getName();
						uploadFile = fi;
						if (fileName == null) {
							continue;
						}
					}
					/*code by sukhi 26/09/2018*/
					fi=null;
					// break;
				}
			} catch (Exception e) {
				logger.error("Error in file process", e);
				throw e;
			}
			
//			if (!docTitle.isEmpty() && (fileName == null || "".equals(fileName))) {
//				AccountUtils.populateError(ctx, "DocFileName", "Document has not been selected");
//				ctx.put("DocumentID", 0);
//				return;
//			}
//			
//			if (!fileName.isEmpty() && (docTitle == null || "".equals(docTitle))) {
//				AccountUtils.populateError(ctx, "DocTitle", "Enter title");
//				ctx.put("DocumentID", 0);
//				return;
//			}
			
			if (!docTitle.isEmpty() && !fileName.isEmpty()) {
				
				int fileIndex = fileName.indexOf(".");
				String extension = fileName.substring(fileIndex);
				extension=extension.substring(extension.lastIndexOf('.'),extension.length());
				logger.debug(extension);
				
				if (!".pdf".equalsIgnoreCase(extension)) {
					AccountUtils.populateError(ctx, "DocUploadError", "Please select PDF file only" );
					ctx.put("DocumentID", 0);
					return;
				}
				
				if (searchType != null && searchType.equals("lossRun")) {
					fileName = "LossRun_" + ctx.get("QuoteNumber").toString();
					docType = "LR";
				} else if (searchType != null && searchType.equals("priorPolicy")) {
					fileName = "PriorPolicy_" + ctx.get("QuoteNumber").toString();
					docType = "PP";
				}
				
				String org_fileName = fileName;
				fileName = fileName + extension;
				logger.debug(fileName);
				
				docFileName = fileName.substring(fileName.lastIndexOf("\\") + 1, fileName.length());
			
				File file = new File(fileName);
				String docName = file.getName();
				String docNameTemp = file.getName();
				
				AccountUtils.populateLastUpdateTimeStamp(ctx);
				
				String uploaddirectory = SystemProperties.getInstance().getString("fileupload.uploaddirectory");
	
				try {
					uploadFile.write(new File(uploaddirectory, docFileName));
				} catch (Exception e) {
					logger.error("Error in DocumentManagment.processLossRunAndPriorDocument", e);
					AccountUtils.populateError(ctx, "DocUploadError", "Document could not be write to file" );
					ctx.put("DocumentID", 0);
					return;
				}
				
				String result = null;
				try {
					result = uploadDocument(ctx, uploaddirectory + docName);
					//result = "File added successfully!";
					{ if (logger.isDebugEnabled()) logger.debug("Result from uploadDocument(ctx, uploaddirectory + docName)" + result); }
					
				} catch (Exception e) {
					logger.error("Error in DocumentManagment.processLossRunAndPriorDocument", e);
					AccountUtils.populateError(ctx, "DocUploadError", "Document could not be uploaded");
					ctx.put("DocumentID", 0);
					return;
				}
				if (result == null || (result != null && !result.equals("File added successfully!"))) {
					AccountUtils.populateError(ctx, "DocUploadError", "Document could not be uploaded");
					ctx.put("DocumentID", 0);
					return;
				}
				
				if (result != null && result.equals("File added successfully!")) {
					logger.debug("processDocument has done");
					// ctx.put("DocTitle", DocTitle);
					
					
					if (docTitle != null && !"".equals(docTitle)) {
	
						docTitle = docTitle.replace("$AND$","&");
						docTitle = docTitle.replace("$PER$","%");
						docTitle = docTitle.replace("$APH$","'");
					}
					
					ctx.put("DocTitle", org_fileName);
					// ctx.put("DocFileName", DocFileName);
	
					int index = docName.indexOf(".");
					String fileName1 = docName.substring(0, index);
					String fileExt = docName.substring(index + 1, docName.length());
					docName = fileName1 + "_" + ctx.get("UploadedTime").toString() + "." + fileExt;
					docName = docName.replace(" ", "").replace(":", "").replace("-", "");
	
					ctx.put("DocFileName", docName);
					ctx.put("DocType", docType);
	
					ctx.put("UploadedType", "Manual (I)");
	
					setUrl(ctx, docName, "DocUrl");
					RuleUtils.executeRule(ctx, "AccountantRule.assignDateTimeNUser");
					AccountUtils.getPolicyData((Context)ctx);
					Object insertDocumentArchiveLW =  SqlResources.getSqlMapProcessor(ctx).insert("DocumentArchive.insert", ctx);
					{ if (logger.isDebugEnabled()) logger.debug(insertDocumentArchiveLW); }
					ctx.put("DocumentID", insertDocumentArchiveLW);
				}
				
				File tempFile = new File(uploaddirectory + docNameTemp);
				if (tempFile.exists())
					tempFile.delete();
				
				/*code by sukhi 26/09/2018*/
				tempFile=null;
				file=null;
				
			} else {
				ctx.put("DocumentID", 0);
			}
		}catch (Exception e){
			logger.error("Error in DocumentManagment.processLossRunAndPriorDocument", e);
			throw e;
		} finally {
			
		}
	}

	
}
