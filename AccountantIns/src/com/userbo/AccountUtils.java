package com.userbo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.PutMethod;
import org.apache.fop.servlet.ServletContextURIResolver;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.jdom.Element;
import org.jdom.output.XMLOutputter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.fop.DownloadFOP;
import com.fop.XML2PDF;
import com.formgenerator.DownloadForm;
import com.mail.MailSender;
import com.manage.managecomponent.components.ComponentImpl;
import com.manage.managecomponent.components.ComponentResources;
import com.manage.managecomponent.components.SetParametersForStoredProcedures;
import com.manage.managemetadata.functions.commonfunctions.DBUtils;
import com.manage.managemetadata.functions.commonfunctions.DataUtils;
import com.manage.managemetadata.functions.commonfunctions.DateUtils;
import com.manage.managemetadata.functions.commonfunctions.RuleUtils;
import com.manage.managemetadata.util.exception.ValidationException;
import com.ormapping.ibatis.SqlResources;
import com.pdf.PrintTextLocations;
import com.util.Constants;
import com.util.Context;
import com.util.HtmlConstants;
import com.util.IContext;
import com.util.InetLogger;
import com.util.MessageStringFormat;
import com.util.ResourceLoader;
import com.util.RuleEngineUtility;
import com.util.RuleEngineUtilityNewApp;
import com.util.SharedHttpClient;
import com.util.StringUtils;
import com.util.SystemProperties;
import com.util.XMLUtils;

public class AccountUtils {

	private static String[] countyRequiredStates = { "CA", "KY", "FL", "NJ", "PA", "MI", "TX", "IL" };
	public static String DATE_PATTERN = "MM-dd-yyyy hh:mm:ss";
	public static String[] group2State = { "AR", "AZ", "DE", "GA", "IL", "IN", "KS", "KY", "OH", "TN", "WI" };
	public final String[] referredStates = { "AR", "OK", "SD", "VT", "NM", "CT" };
	private static InetLogger logger = InetLogger.getInetLogger(AccountUtils.class);
	final String key = "Acc";

	@SuppressWarnings("unchecked")
	public static void populateError(IContext ctx, String field, String msg) throws Exception {
		List errorList = new ArrayList();
		if (ctx.get(Constants.INET_ERRORS_LIST) != null)
			errorList = (List) ctx.get(Constants.INET_ERRORS_LIST);

		ValidationException e = new ValidationException(msg);
		e.setField(field);
		errorList.add(e);
		ctx.put(Constants.INET_ERRORS_LIST, errorList);
		ctx.put(Constants.INET_FORM_DIRTY, Constants.YES);
	}

	@SuppressWarnings("unchecked")
	public void generateQuoteNumber(IContext ctx) throws Exception {

		if (ctx.get(com.userbo.Constants.POLICY_KEY) != null) {
			String PolicyKey = ctx.get(com.userbo.Constants.POLICY_KEY).toString().trim();
			ctx.put(com.userbo.Constants.QUOTE_NUMBER, getNewQuoteNumber(PolicyKey));
		}

		if (ctx.get(com.userbo.Constants.ACCOUNT_KEY) != null) {
			String AccountKey = ctx.get(com.userbo.Constants.ACCOUNT_KEY).toString().trim();
			ctx.put(com.userbo.Constants.ACCOUNT_ID, AccountKey);
		}
	}

	public String getNewQuoteNumber(Object submissionID) {

		if (submissionID == null)
			return null;

		String policyNum = "";

		for (int i = 0; i < 7 - submissionID.toString().length(); i++)
			policyNum = policyNum + "0";

		policyNum = "QN-" + policyNum + submissionID.toString();

		return policyNum;
	}

	public void checkTotalPercentage(IContext ctx) throws Exception {
		Set set = ctx.keySet();
		int total = 0;

		for (Iterator it = set.iterator(); it.hasNext();) {

			String key = it.next().toString();
			if (key.contains("NumberOfPersonnel_")) {
				if (ctx.get(key) != null && !"".equals(ctx.get(key))) {
					if (!"NumberOfPersonnel_1".equals(key.toString())) {
						{ if (logger.isDebugEnabled()) logger.debug(ctx.get(key).toString()); }
						total = total + Integer.parseInt(ctx.get(key).toString());
					}
				}

			}
		}

		// if(total <= 0)
		// {
		// populateError(ctx, "TotalFirmPersonnel", "Total Firm Personnel should
		// be equal or greater than 1");
		// }

		if (total >= 1) {
			Object obj = DBUtils.executeDBOperation(ctx, "PersonnelType", "15");
			if (obj != null) {
				savePersonnelAffiliationMOMList(ctx, total, obj);
			}
		}

	}

	@SuppressWarnings("unchecked")
	private void savePersonnelAffiliationMOMList(IContext ctx, int total, Object obj) throws Exception {
		List personnelList = (List) obj;
		for (int i = 0; i < personnelList.size(); i++) {
			Map map = (Map) personnelList.get(i);

			Context newctx = new Context();
			// newctx.setProject("AccountantIns");

			newctx.setProject(ctx.getProject());
			newctx.put("PersonnelTypeKey", ctx.get("PersonnelTypeKey" + "_" + i));
			newctx.put("PolicyKey", ctx.get("PolicyKey"));
			newctx.put("VersionSequence", ctx.get("VersionSequence"));
			newctx.put("TransactionSeq", ctx.get("TransactionSeq"));
			newctx.put("NumberOfPersonnel", ctx.get("NumberOfPersonnel" + "_" + i));
			Object obj2 = DBUtils.executeDBOperation(newctx, "PersonnelAffiliation", "3");

			if (obj2 == null) {
				DBUtils.executeDBOperation(newctx, "PersonnelAffiliation", "1");

			} else {

				DBUtils.executeDBOperation(newctx, "PersonnelAffiliation", "2");
				DBUtils.executeDBOperation(newctx, "PersonnelAffiliation", "1");
			}

		}

		ctx.put("TotalFirmPersonnel", total);
		DBUtils.executeDBOperation(ctx, "Firm", "4");
	}

	@SuppressWarnings("unchecked")
	protected static void percentageError(IContext ctx, int total, String percentage) throws Exception {
		populateError(ctx, "TotalAOP_Percentage",
				"The total AOP percentage is required to be equal 100%, current total is " + total + "%.");

		// List errorList = new ArrayList();
		// if (ctx.get(Constants.INET_ERRORS_LIST) != null)
		// errorList = (List) ctx.get(Constants.INET_ERRORS_LIST);
		//
		// // errorList.add(new ValidationException("The total percentage should
		// be equal to"+percentage+"%."));
		// // errorList.add(new ValidationException("Current total percentage is
		// : "+total + "%"));
		//
		// ValidationException e = new ValidationException("Total percentage
		// should be equal to "+percentage+ "% and Current total percentage is
		// "+total + "%");
		// e.setField("TotalAOP_Percentage");
		// errorList.add(e);
		// ctx.put(Constants.INET_ERRORS_LIST, errorList);
		// ctx.put(Constants.INET_FORM_DIRTY, Constants.YES);
	}

	public static void validateAOPComment(IContext ctx) throws Exception {

		Map map = new HashMap();
		List limtTypes = SqlResources.getSqlMapProcessor(ctx).select("AOP.findAll", ctx);

		if (limtTypes != null) {
			for (int i = 0; i < limtTypes.size(); i++) {
				map = (HashMap) limtTypes.get(i);
				// if(map.get("AOPKey").toString().equals("9") ||
				// map.get("AOPKey").toString().equals("10") ||
				// map.get("AOPKey").toString().equals("12") ||
				// map.get("AOPKey").toString().equals("19") ||
				// map.get("AOPKey").toString().equals("20"))
				if (map.get("AOPKey").toString().equals("9") || map.get("AOPKey").toString().equals("10")
						|| map.get("AOPKey").toString().equals("12") || map.get("AOPKey").toString().equals("20")
						|| map.get("AOPKey").toString().equals("21")) {
					String percentageValue = ctx.get("AOP_Percentage_" + map.get("AOPKey").toString()) == null ? null
							: ctx.get("AOP_Percentage_" + map.get("AOPKey").toString()).toString();
					String comment = ctx.get("AOPCommentDesc_" + map.get("AOPKey").toString()) == null ? null
							: ctx.get("AOPCommentDesc_" + map.get("AOPKey").toString()).toString();

					if (!(percentageValue == null || "".equals(percentageValue.trim())
							|| "0".equals(percentageValue))) {
						if (comment == null || "".equals(comment.trim())) {
							String msg = "Fill in please describe";
							if (map.get("AOPKey").toString().equals("10"))
								msg = "Please describe the Services";
							else if (map.get("AOPKey").toString().equals("12"))
								msg = "Please describe the Consulting services";
							else if (map.get("AOPKey").toString().equals("20"))
								msg = "Please describe the Other Services";

							populateError(ctx, "AOPCommentDesc_" + map.get("AOPKey").toString(), msg);
						}
					} else {
						if (comment != null && !"".equals(comment)) {
							ctx.put("AOPCommentDesc_" + map.get("AOPKey").toString(), "");
						}

					}
				}
			}
		}
	}

	public static void validateAOPPercentage(IContext ctx) throws Exception {

		int total = 0;
		Map map = new HashMap();
		List limtTypes = SqlResources.getSqlMapProcessor(ctx).select("AOP.findAll", ctx);

		if (limtTypes != null) {
			for (int i = 0; i < limtTypes.size(); i++) {
				map = (HashMap) limtTypes.get(i);
				String percentageValue = ctx.get("AOP_Percentage_" + map.get("AOPKey").toString()) == null ? null
						: ctx.get("AOP_Percentage_" + map.get("AOPKey").toString()).toString();
				if (percentageValue == null || "".equals(percentageValue.trim()))
					percentageValue = "0";
				try {
					total = total + Integer.parseInt(percentageValue);
				} catch (Exception e) {
					logger.error("Error in AccountUtils.validateAOPPercentage", e);
					populateError(ctx, "TotalAOP_Percentage", "Invalid data");
				}

			}
		}

		if (total < 100 || total > 100) {
			percentageError(ctx, total, "100");
		}
	}

	public static void validateClientList(IContext ctx) throws Exception {

		if (ctx.get("IsCommissionRecieved") != null && "Y".equals(ctx.get("IsCommissionRecieved").toString())) {
			List list = SqlResources.getSqlMapProcessor(ctx)
					.select("SqlStmts.UserStatementcheckListSizeListPublicPrivateOfferingSupp", ctx);

			if (list == null)
				populateError(ctx, "IsCommissionRecieved", "Client list is empty, atleast one record is required");
			else if (list != null && list.size() == 0)
				populateError(ctx, "IsCommissionRecieved", "Client list is empty, atleast one record is required");
		} else if (ctx.get("IsCommissionRecieved") != null && "N".equals(ctx.get("IsCommissionRecieved").toString())) {
			SqlResources.getSqlMapProcessor(ctx).delete("SqlStmts.UserStatementdeleteListPublicPrivateOfferingSupp",
					ctx);
		}
	}

	public static void validateClaimList(IContext ctx) throws Exception {

		if ((ctx.get("IsAwarenessOfAnyProfLiability") != null
				&& "Y".equals(ctx.get("IsAwarenessOfAnyProfLiability").toString()))
				|| (ctx.get("IsAwarenessOfAnyProfLiabilitySuitAgainst") != null
						&& "Y".equals(ctx.get("IsAwarenessOfAnyProfLiabilitySuitAgainst").toString()))) {
			List list = SqlResources.getSqlMapProcessor(ctx)
					.select("SqlStmts.UserStatementcheckListSizeListClaimIncidentSupp", ctx);

			if (ctx.get("IsAwarenessOfAnyProfLiability") != null
					&& "Y".equals(ctx.get("IsAwarenessOfAnyProfLiability").toString())) {
				if (list == null)
					populateError(ctx, "IsAwarenessOfAnyProfLiability",
							"Claim list is empty, atleast one record is required");
				else if (list != null && list.size() == 0)
					populateError(ctx, "IsAwarenessOfAnyProfLiability",
							"Claim list is empty, atleast one record is required");
			}
			if (ctx.get("IsAwarenessOfAnyProfLiabilitySuitAgainst") != null
					&& "Y".equals(ctx.get("IsAwarenessOfAnyProfLiabilitySuitAgainst").toString())) {
				if (list == null)
					populateError(ctx, "IsAwarenessOfAnyProfLiabilitySuitAgainst",
							"Claim list is empty, atleast one record is required");
				else if (list != null && list.size() == 0)
					populateError(ctx, "IsAwarenessOfAnyProfLiabilitySuitAgainst",
							"Claim list is empty, atleast one record is required");
			}
		} else if ((ctx.get("IsAwarenessOfAnyProfLiability") != null
				&& "N".equals(ctx.get("IsAwarenessOfAnyProfLiability").toString()))
				&& (ctx.get("IsAwarenessOfAnyProfLiabilitySuitAgainst") != null
						&& "N".equals(ctx.get("IsAwarenessOfAnyProfLiabilitySuitAgainst").toString()))) {
			SqlResources.getSqlMapProcessor(ctx).delete("SqlStmts.UserStatementdeleteListClaimIncidentSupp", ctx);
		}
	}

	public static void validateClaimListRenew(IContext ctx) throws Exception {

		if ((ctx.get("IsAwarenessOfAnyProfLiability") != null
				&& "Y".equals(ctx.get("IsAwarenessOfAnyProfLiability").toString()))
				|| (ctx.get("IsAwarenessOfAnyProfLiabilitySuitAgainst") != null
						&& "Y".equals(ctx.get("IsAwarenessOfAnyProfLiabilitySuitAgainst").toString()))) {
			List list = SqlResources.getSqlMapProcessor(ctx)
					.select("SqlStmts.UserStatementcheckListSizeListClaimIncidentSupp", ctx);

			if (ctx.get("IsAwarenessOfAnyProfLiability") != null
					&& "Y".equals(ctx.get("IsAwarenessOfAnyProfLiability").toString())) {
				if (list == null)
					populateError(ctx, "IsAwarenessOfAnyProfLiability",
							"Claim list is empty, atleast one record is required");
				else if (list != null && list.size() == 0)
					populateError(ctx, "IsAwarenessOfAnyProfLiability",
							"Claim list is empty, atleast one record is required");
			}
			if (ctx.get("IsAwarenessOfAnyProfLiabilitySuitAgainst") != null
					&& "Y".equals(ctx.get("IsAwarenessOfAnyProfLiabilitySuitAgainst").toString())) {
				if (list == null)
					populateError(ctx, "IsAwarenessOfAnyProfLiabilitySuitAgainst",
							"Claim list is empty, atleast one record is required");
				else if (list != null && list.size() == 0)
					populateError(ctx, "IsAwarenessOfAnyProfLiabilitySuitAgainst",
							"Claim list is empty, atleast one record is required");
			}
		} else if ((ctx.get("IsAwarenessOfAnyProfLiability") != null
				&& "N".equals(ctx.get("IsAwarenessOfAnyProfLiability").toString()))
				&& (ctx.get("IsAwarenessOfAnyProfLiabilitySuitAgainst") != null
						&& "N".equals(ctx.get("IsAwarenessOfAnyProfLiabilitySuitAgainst").toString()))) {

			// SqlResources.getSqlMapProcessor(ctx).delete("SqlStmts.UserStatementdeleteListClaimIncidentSupp",
			// ctx);
		}
	}

	public static void validatePolicyCoverage(IContext ctx) throws Exception {

		if (ctx.get("StateCode") != null) {
			String stateCode = ctx.get("StateCode").toString();
			if ("VT".equals(stateCode) || "NM".equals(stateCode)) {
				ctx.put("IsClaimExpensesType", "Y");
			}
		}

		// commented for ARKANSAS as it was overwriting values of context for
		// claimexpenses
		/*
		 * if (ctx.get("StateCode") != null &&
		 * ("AR".equals(ctx.get("StateCode").toString()) || "SD"
		 * .equals(ctx.get("StateCode").toString()))) ctx.put("IsClaimExpensesType",
		 * "N");
		 */

		// Updated Block
		if (ctx.get("StateCode") != null && ("SD".equals(ctx.get("StateCode").toString())))
			ctx.put("IsClaimExpensesType", "N");
		if (ctx.get("IsClaimExpensesType") == null)
			ctx.put("IsClaimExpensesType", "N");
		else if (ctx.get("IsClaimExpensesType") != null && "".equals(ctx.get("IsClaimExpensesType").toString()))
			ctx.put("IsClaimExpensesType", "N");

		if (ctx.get("IsClaimOptionType") == null)
			ctx.put("IsClaimOptionType", "N");
		else if (ctx.get("IsClaimOptionType") != null && "".equals(ctx.get("IsClaimOptionType").toString()))
			ctx.put("IsClaimOptionType", "N");

	}

	public static void validateQuoteByEmailAgentCall(IContext ctx) throws Exception {

		if ((ctx.get("IsFullQuote") != null && "Y".equals(ctx.get("IsFullQuote").toString()))
				&& (ctx.get("IsQuickQuote") != null && "N".equals(ctx.get("IsQuickQuote").toString()))) {
			if ("BasicInfo".equals(ctx.get("page").toString())) {
				if (ctx.get("IsQuickQuoteByEmail") == null)
					ctx.put("IsQuickQuoteByEmail", "N");
				else if (ctx.get("IsQuickQuoteByEmail") != null && "".equals(ctx.get("IsQuickQuoteByEmail").toString()))
					ctx.put("IsQuickQuoteByEmail", "N");

				if (ctx.get("IsAnAgentCall") == null)
					ctx.put("IsAnAgentCall", "N");
				else if (ctx.get("IsAnAgentCall") != null && "".equals(ctx.get("IsAnAgentCall").toString()))
					ctx.put("IsAnAgentCall", "N");

				if ("N".equals(ctx.get("IsQuickQuoteByEmail").toString())
						&& "N".equals(ctx.get("IsAnAgentCall").toString()))
					populateError(ctx, "IsAnAgentCall", "Either an email or call from specialist is required");
			}
		}
	}

	public static void validatePersonnelNumber(IContext ctx) throws Exception {

		Set set = ctx.keySet();
		int total = 0;
		int NumberOfPersonnel_0 = 0;
		int NumberOfPersonnel_1 = 0;

		for (Iterator it = set.iterator(); it.hasNext();) {

			String key = it.next().toString();
			if (key.contains("NumberOfPersonnel_")) {
				if (key.equals("NumberOfPersonnel_1")) {
					if (ctx.get(key) != null && !"".equals(ctx.get(key).toString().trim()))
						NumberOfPersonnel_1 = Integer.parseInt(ctx.get(key).toString().trim());
					if (ctx.get("NumberOfPersonnel_0") != null
							&& !"".equals(ctx.get("NumberOfPersonnel_0").toString().trim()))
						NumberOfPersonnel_0 = Integer.parseInt(ctx.get("NumberOfPersonnel_0").toString().trim());
				}
				int numberkey = 0;
				if (ctx.get(key) != null && !"".equals(ctx.get(key).toString().trim())) {
					try {
						numberkey = Integer.parseInt(ctx.get(key).toString().trim());
					} catch (Exception e) {
						logger.error("Error in AccountUtils.validatePersonnelNumber", e);
						populateError(ctx, "TotalFirmPersonnel", "That number is invalid.  Please enter again");
					}
					// total = total
					// + Integer.parseInt(ctx.get(key).toString().trim());

					total = total + numberkey;
				}
			}
		}

		if (total <= 0) {
			populateError(ctx, "TotalFirmPersonnel", "Total Firm Personnel should be equal or greater than 1");
		} else if (NumberOfPersonnel_1 > NumberOfPersonnel_0) {
			populateError(ctx, "TotalFirmPersonnel",
					"\"Number above that are CPA's\" should be equal or less than \"Owners Partners or Officers\"");
		}
	}

	public static void validateBusinessState(IContext ctx) throws Exception {

		if (ctx.get("StateCode") != null && !"".equals(ctx.get("StateCode").toString())) {
			Object obj = SqlResources.getSqlMapProcessor(ctx).findByKey("State.findByKey", ctx);
			if (obj != null && obj instanceof Map) {
				Map map = (Map) obj;
				ctx.put("IsBusinessActive", map.get("IsBusinessActive"));

				// if(map.get("IsBusinessActive") == null)
				// {
				// populateError(ctx, "StateCode", "We currently don't write
				// business for this state, please contact at 1.888.803.9898");
				// }
				// else if(map.get("IsBusinessActive") != null &&
				// "N".equals(map.get("IsBusinessActive").toString().trim()))
				// {
				// populateError(ctx, "StateCode", "We currently don't write
				// business for this state, please contact at 1.888.803.9898");
				// }
			}
		}

	}

	public void validateBusinessStateOnContinue(IContext ctx) throws Exception {

		if (ctx.get("StateCode") != null && !"".equals(ctx.get("StateCode").toString())) {
			Object obj = SqlResources.getSqlMapProcessor(ctx).findByKey("State.findByKey", ctx);
			if (obj != null && obj instanceof Map) {
				Map map = (Map) obj;
				ctx.put("IsBusinessActive", map.get("IsBusinessActive"));

				// if(map.get("IsBusinessActive") == null)
				// {
				// populateError(ctx, "StateCode", "We currently don't write
				// business for this state, please contact at 1.888.803.9898");
				// }
				// else if(map.get("IsBusinessActive") != null &&
				// "N".equals(map.get("IsBusinessActive").toString().trim()))
				// {
				// populateError(ctx, "StateCode", "We currently don't write
				// business for this state, please contact at 1.888.803.9898");
				// }
			}
		}

	}

	public static boolean validateBasicinfo(Context ctx) throws Exception {

		trimFirmApplicantName(ctx);
		boolean flag = false;
		// Object obj = RuleUtils.executeRule(ctx, "AccountantRule.isAgent");

		Object obj = RuleUtils.executeRule(ctx, "AccountantRule.validateEmailOnFirmSave");
		if (obj != null && obj instanceof Boolean) {
			flag = (Boolean) obj;

			if (flag) {
				checkFirmWithEmailExist(ctx);
			}

		}

		validateNorthDakotaStateRule(ctx, "IsClaimExpensesType");
		validateSouthDakotaStateRule(ctx, "IsClaimExpensesType");
		validateBusinessState(ctx);
		validatePersonnelNumber(ctx);
		validateEstimateForCurrentYear(ctx);
		validateAOPPercentage(ctx);
		validateAOPComment(ctx);
		// validateClientList(ctx);
		validateClaimList(ctx);
		validatePolicyCoverage(ctx);
		validateQuoteByEmailAgentCall(ctx);
		validateBasicinfoDateField(ctx);
		validatePolicyExpirationDate(ctx);
		validateCountyForTX(ctx);
		validatePolicyEffectiveDateYear(ctx);

		// validateGrossRevenueYear(ctx);

		return true;
	}

	static void validateSouthDakotaStateRule(IContext ctx, String errorField) throws Exception {

		if (ctx.get("StateCode") != null && ctx.get("StateCode").toString().equals("SD")) {
			String IsClaimExpensesType = ctx.get("IsClaimExpensesType") != null
					? ctx.get("IsClaimExpensesType").toString()
					: "";
			if ("Y".equals(IsClaimExpensesType)) {
				ctx.put("IsClaimExpensesType", "N");
				// populateError(ctx, errorField, "South Dakhota does not allow defense expenses
				// to be paid inside the limits of liablity");
			}

		}

	}

	private static void validatePolicyEffectiveDateYear(IContext ctx) throws Exception {

		String policyEffectiveDate = ctx.get("PolicyEffectiveDate") != null ? ctx.get("PolicyEffectiveDate").toString()
				: "";
		if (!"".equals(policyEffectiveDate)) {
			SimpleDateFormat f = new SimpleDateFormat("MM/dd/yyyy");
			Date d = f.parse(policyEffectiveDate);
			Calendar policycalendar = Calendar.getInstance();
			policycalendar.setTime(d);
			int policyYear = policycalendar.get(Calendar.YEAR);

			Calendar current = Calendar.getInstance();
			int currentYear = current.get(Calendar.YEAR);

			if (policyYear - currentYear > 1) {
				populateError(ctx, "PolicyEffectiveDate", "Policy Effective year is invalid");
			}
//			else if(policyYear < currentYear){
//				populateError(ctx, "PolicyEffectiveDate", "Policy Effective year is invalid");
//			}
		} else {
			populateError(ctx, "PolicyEffectiveDate", "Please enter policy effective date.");
		}

	}

	public static void validateCountyForTX(IContext ctx) throws Exception {

		if (ctx.get("StateCode") != null && ctx.get("StateCode").toString().equals("TX")) {
			String countyDesc = ctx.get("CountyDesc") != null ? ctx.get("CountyDesc").toString() : "";
			if ("".equals(countyDesc)) {
				AccountUtils.populateError(ctx, "CountyDesc", "County is required.");
			} else {

				List countyListTX = SqlResources.getSqlMapProcessor(ctx)
						.select("SqlStmts.UserStatementsaveAccountstmtscheckCountyListTX", ctx);
				if (countyListTX != null && countyListTX.size() > 0) {

				} else {
					AccountUtils.populateError(ctx, "CountyDesc", "Your county is not listed please contact us at "
							+ com.userbo.Constants.PHONENUMBER + " for assistance.");
				}

			}

		}

	}

	public static void validatePolicyExpirationDate(IContext ctx) throws Exception {

		if (ctx.get("PolicyEffectiveDate") != null && !ctx.get("PolicyEffectiveDate").toString().isEmpty()
				&& ctx.get("PolicyExpirationDate") != null && !ctx.get("PolicyExpirationDate").toString().isEmpty()) {

			String strPolicyEffDate = ctx.get("PolicyEffectiveDate").toString();

			String strPriorActDate = ctx.get("PolicyExpirationDate").toString();

			Date policyDate = new Date(strPolicyEffDate);
			Date expirationDate = new Date(strPriorActDate);

			Calendar policyCal = Calendar.getInstance();
			policyCal.set(policyDate.getYear(), policyDate.getMonth(), policyDate.getDate());

			Calendar expCal = Calendar.getInstance();
			expCal.set(expirationDate.getYear(), expirationDate.getMonth(), expirationDate.getDate());

			if (!expCal.after(policyCal))

				populateError(ctx, "PolicyExpirationDate",
						"Policy expiration date should be later than policy effective date");

		}

	}

	public static void trimFirmApplicantName(IContext ctx) {

		if (ctx.get("AccountName") != null && !"".equals(ctx.get("AccountName").toString()))
			ctx.put("AccountName", ctx.get("AccountName").toString().trim());
	}

	public static void validateTrusteeList(IContext ctx) throws Exception {
		if (ctx.get("IsServedAsTrustee") != null && "Y".equals(ctx.get("IsServedAsTrustee").toString())) {
			List list = SqlResources.getSqlMapProcessor(ctx)
					.select("SqlStmts.UserStatementcheckListSizeListTrusteeOrEstateSupp", ctx);

			if (list == null)
				populateError(ctx, "IsServedAsTrustee", "Served trustee list is empty, atleast one record is required");
			else if (list != null && list.size() == 0)
				populateError(ctx, "IsServedAsTrustee", "Served trustee list is empty, atleast one record is required");
		} else if (ctx.get("IsServedAsTrustee") != null && "N".equals(ctx.get("IsServedAsTrustee").toString())) {
			SqlResources.getSqlMapProcessor(ctx).delete("SqlStmts.UserStatementdeleteListTrusteeOrEstateSupp", ctx);
		}
	}

	public static void validateTrusteeListRenew(IContext ctx) throws Exception {
		if (ctx.get("IsServedAsTrustee") != null && "Y".equals(ctx.get("IsServedAsTrustee").toString())) {
			List list = SqlResources.getSqlMapProcessor(ctx)
					.select("SqlStmts.UserStatementcheckListSizeListTrusteeOrEstateSupp", ctx);

			if (list == null)
				populateError(ctx, "IsServedAsTrustee", "Served trustee list is empty, atleast one record is required");
			else if (list != null && list.size() == 0)
				populateError(ctx, "IsServedAsTrustee", "Served trustee list is empty, atleast one record is required");
		} else if (ctx.get("IsServedAsTrustee") != null && "N".equals(ctx.get("IsServedAsTrustee").toString())) {
			// Do not delete the information on RenewBasic Info save
			// SqlResources.getSqlMapProcessor(ctx).delete("SqlStmts.UserStatementdeleteListTrusteeOrEstateSupp",
			// ctx);
		}
	}

	public static void validateFirmRenderedList(IContext ctx) throws Exception {
		if (ctx.get("IsFirmRenderedServiceForAnyClient") != null
				&& "Y".equals(ctx.get("IsFirmRenderedServiceForAnyClient").toString())) {
			List list = SqlResources.getSqlMapProcessor(ctx)
					.select("SqlStmts.UserStatementcheckListSizeListOutsideInterestSupp", ctx);

			if (list == null)
				populateError(ctx, "IsFirmRenderedServiceForAnyClient",
						"Firm list is empty, atleast one record is required");
			else if (list != null && list.size() == 0)
				populateError(ctx, "IsFirmRenderedServiceForAnyClient",
						"Firm list is empty, atleast one record is required");
		} else if (ctx.get("IsFirmRenderedServiceForAnyClient") != null
				&& "N".equals(ctx.get("IsFirmRenderedServiceForAnyClient").toString())) {
			SqlResources.getSqlMapProcessor(ctx).delete("SqlStmts.UserStatementdeleteListOutsideInterestSupp", ctx);
		}
	}

	public static void validateFirmRenderedListRenew(IContext ctx) throws Exception {
		if (ctx.get("IsFirmRenderedServiceForAnyClient") != null
				&& "Y".equals(ctx.get("IsFirmRenderedServiceForAnyClient").toString())) {
			List list = SqlResources.getSqlMapProcessor(ctx)
					.select("SqlStmts.UserStatementcheckListSizeListOutsideInterestSupp", ctx);

			if (list == null)
				populateError(ctx, "IsFirmRenderedServiceForAnyClient",
						"Firm list is empty, atleast one record is required");
			else if (list != null && list.size() == 0)
				populateError(ctx, "IsFirmRenderedServiceForAnyClient",
						"Firm list is empty, atleast one record is required");
		} else if (ctx.get("IsFirmRenderedServiceForAnyClient") != null
				&& "N".equals(ctx.get("IsFirmRenderedServiceForAnyClient").toString())) {

			// SqlResources.getSqlMapProcessor(ctx).delete("SqlStmts.UserStatementdeleteListOutsideInterestSupp",
			// ctx);
		}
	}

	public static void validateFirmInitLawSuitList(IContext ctx) throws Exception {
		if (ctx.get("IsSuitInstituted") != null && "Y".equals(ctx.get("IsSuitInstituted").toString())) {
			List list = SqlResources.getSqlMapProcessor(ctx)
					.select("SqlStmts.UserStatementcheckListSizeListFirmInitLawsuits", ctx);

			if (list == null)
				populateError(ctx, "IsSuitInstituted", "Fees Suit list is empty, atleast one record is required");
			else if (list != null && list.size() == 0)
				populateError(ctx, "IsSuitInstituted", "Fees Suit list is empty, atleast one record is required");
		} else if (ctx.get("IsSuitInstituted") != null && "N".equals(ctx.get("IsSuitInstituted").toString())) {
			SqlResources.getSqlMapProcessor(ctx)
					.delete("SqlStmts.UserStatementrenewapplicationdeleteFirmInitLawsuitsData", ctx);
		}
	}

	public static void validateFirmInitLawSuitListRenew(IContext ctx) throws Exception {
		if (ctx.get("IsSuitInstituted") != null && "Y".equals(ctx.get("IsSuitInstituted").toString())) {
			List list = SqlResources.getSqlMapProcessor(ctx)
					.select("SqlStmts.UserStatementcheckListSizeListFirmInitLawsuits", ctx);

			if (list == null)
				populateError(ctx, "IsSuitInstituted", "Fee Suit list is empty, atleast one record is required");
			else if (list != null && list.size() == 0)
				populateError(ctx, "IsSuitInstituted", "Fee Suit list is empty, atleast one record is required");
		} else if (ctx.get("IsSuitInstituted") != null && "N".equals(ctx.get("IsSuitInstituted").toString())) {
			// SqlResources.getSqlMapProcessor(ctx).delete("SqlStmts.UserStatementrenewapplicationdeleteFirmInitLawsuitsData",ctx);
		}
	}

	public static boolean validateGeneral(Context ctx) throws Exception {
		validateTrusteeList(ctx);
		validateFirmRenderedList(ctx);
		validateGeneralDateField(ctx);
		validateGeneralPhoneNumber(ctx);
		validateQuoteByEmailAgentCall(ctx);
		validateFirmInitLawSuitList(ctx);
		return true;
	}

	public void checkAOPPercentage(IContext ctx) throws Exception {
		Set set = ctx.keySet();
		int total = 0;
		for (Iterator it = set.iterator(); it.hasNext();) {
			String key = it.next().toString();
			if (key.contains("PercentageValue_")) {
				if (ctx.get(key) != null && !"".equals(ctx.get(key).toString().trim()))
					total = total + Integer.parseInt(ctx.get(key).toString().trim());
			}
		}

		Object obj = DBUtils.executeDBOperation(ctx, "AOP", "15");
		if (obj != null) {
			saveAOPMOMList(ctx, total, obj);
		}
	}

	@SuppressWarnings("unchecked")
	private void saveAOPMOMList(IContext ctx, int total, Object obj) throws Exception {
		String value = "";
		List personnelList = (List) obj;
		for (int i = 0; i < personnelList.size(); i++) {
			Map map = (Map) personnelList.get(i);

			Context newctx = new Context();
			// newctx.setProject("AccountantIns");

			newctx.setProject(ctx.getProject());
			newctx.put("AOPKey", ctx.get("AOPKey" + "_" + i));
			newctx.put("PolicyKey", ctx.get("PolicyKey"));
			newctx.put("VersionSequence", ctx.get("VersionSequence"));
			newctx.put("TransactionSeq", ctx.get("TransactionSeq"));

			if (ctx.get("PercentageValue" + "_" + i) != null)
				value = ctx.get("PercentageValue" + "_" + i).toString().trim();

			if ("".equals(value))
				value = "0";

			newctx.put("PercentageValue", value);
			Object obj2 = DBUtils.executeDBOperation(newctx, "AreaOfPractice", "3");

			if (obj2 == null) {
				DBUtils.executeDBOperation(newctx, "AreaOfPractice", "1");

			} else {

				DBUtils.executeDBOperation(newctx, "AreaOfPractice", "2");
				DBUtils.executeDBOperation(newctx, "AreaOfPractice", "1");
			}

		}

		ctx.put("TotalAOP", total);
		if (null != ctx.get("FirmKey"))
			DBUtils.executeDBOperation(ctx, "Firm", "4");
	}

	public void checkInsertGrossRevenue(IContext ctx) throws Exception {
		long total = 0;
		int count = 0;

		boolean bool = false; // validateEstimateForCurrentYear(ctx);
		if (!bool) {
			Object obj = DBUtils.executeDBOperation(ctx, "GrossRevenue", "15");
			if (obj != null) {
				List personnelList = (List) obj;
				for (int i = 0; i < personnelList.size(); i++) {
					Map map = (Map) personnelList.get(i);
					Context newctx = new Context();
					// newctx.setProject("AccountantIns");

					newctx.setProject(ctx.getProject());
					newctx.put("GrossRevenueKey", ctx.get("GrossRevenueKey" + "_" + i));
					newctx.put("PolicyKey", ctx.get("PolicyKey"));
					newctx.put("VersionSequence", ctx.get("VersionSequence"));
					newctx.put("TransactionSeq", ctx.get("TransactionSeq"));
					newctx.put("YearEndDate", ctx.get("YearEndDate" + "_" + i));
					// newctx.put("Amount" , ctx.get( "Amount"+"_"+i) );

					Object objAmount = removeAmountFormat(ctx.get("Amount" + "_" + i));
					if (objAmount != null && !"".equals(objAmount.toString()) && !"0".equals(objAmount.toString())) {
						total = total + Long.parseLong(objAmount.toString());
						count = count + 1;
					}

					newctx.put("Amount", objAmount);

					Object obj2 = DBUtils.executeDBOperation(newctx, "GrossRevenueForFirm", "3");
					if (obj2 == null) {
						DBUtils.executeDBOperation(newctx, "GrossRevenueForFirm", "1");
					} else {
						DBUtils.executeDBOperation(newctx, "GrossRevenueForFirm", "2");
						DBUtils.executeDBOperation(newctx, "GrossRevenueForFirm", "1");
					}

				}

				if (count > 0) {
					long AverageRevenue = total / count;
					ctx.put("AverageRevenue", AverageRevenue);
				} else
					ctx.put("AverageRevenue", 0);

			}
		}
	}

	public void insertGrossRevenueQuickQuote(IContext ctx) throws Exception {
		long total = 0;
		int count = 0;

		boolean bool = false; // validateEstimateForCurrentYear(ctx);
		if (!bool) {
			Object obj = DBUtils.executeDBOperation(ctx, "GrossRevenue", "15");
			if (obj != null) {
				List personnelList = (List) obj;
				if (personnelList != null && personnelList.size() > 0) {
					Map map = (Map) personnelList.get(0);
					Context newctx = new Context();
					// newctx.setProject("AccountantIns");
					newctx.setProject(ctx.getProject());
					newctx.put("GrossRevenueKey", map.get("GrossRevenueKey"));
					newctx.put("PolicyKey", ctx.get("PolicyKey"));
					newctx.put("VersionSequence", ctx.get("VersionSequence"));
					newctx.put("TransactionSeq", ctx.get("TransactionSeq"));
					newctx.put("LastUpdateTimeStamp", ctx.get("LastUpdateTimeStamp"));
					newctx.put("CreatedDate", ctx.get("CreatedDate"));
					newctx.put("CreatedBy", ctx.get("CreatedBy"));
					newctx.put("VersionKey", ctx.get("VersionKey"));
					Object objAmount = removeAmountFormat(ctx.get("Amount"));
					total = Long.parseLong(objAmount.toString());
					newctx.put("Amount", objAmount);
					populateLastUpdateTimeStamp(newctx);

					Object obj2 = DBUtils.executeDBOperation(newctx, "GrossRevenueForFirm", "3");
					if (obj2 == null) {
						DBUtils.executeDBOperation(newctx, "GrossRevenueForFirm", "1");
					} else {
						DBUtils.executeDBOperation(newctx, "GrossRevenueForFirm", "2");
						DBUtils.executeDBOperation(newctx, "GrossRevenueForFirm", "1");
					}

					ctx.put("AverageRevenue", total);

				}
			}
		}
	}

	public void checkInsertGrossRevenueRenew(IContext ctx) throws Exception {
		long total = 0;
		int count = 0;

		Object objRevenue = SqlResources.getSqlMapProcessor(ctx)
				.select("SqlStmts.UserStatementrenewapplicationmomgrossrevenueData", ctx);

		if (objRevenue != null && objRevenue instanceof List) {

			List revenueList = (List) objRevenue;

			Map objMap = (Map) revenueList.get(0);
			Context newctx = new Context();

			newctx.setProject(ctx.getProject());
			newctx.put("GrossRevenueKey", objMap.get("GrossRevenueKey"));
			newctx.put("PolicyKey", objMap.get("PolicyKey"));
			newctx.put("VersionSequence", objMap.get("VersionSequence"));
			newctx.put("YearEndDate", ctx.get("YearEndDate"));
			newctx.put("Amount", removeAmountFormat(ctx.get("Amount")));

			SqlResources.getSqlMapProcessor(newctx).update("GrossRevenueForFirm.update", newctx);

			objRevenue = SqlResources.getSqlMapProcessor(ctx)
					.select("SqlStmts.UserStatementrenewapplicationmomgrossrevenueData", ctx);
			revenueList.clear();
			if (objRevenue != null && objRevenue instanceof List) {
				revenueList = (List) objRevenue;
				for (int i = 0; i < revenueList.size(); i++) {
					Map map = (Map) revenueList.get(i);
					Object objAmount = removeAmountFormat(map.get("Amount"));
					if (objAmount != null && !"".equals(objAmount.toString()) && !"0".equals(objAmount.toString())) {
						total = total + Long.parseLong(objAmount.toString());
						count = count + 1;
					}
				}
				if (count > 0) {
					long AverageRevenue = total / count;
					ctx.put("AverageRevenue", AverageRevenue);
				} else
					ctx.put("AverageRevenue", 0);

			}
		}

	}

	public static boolean validateAttestation(Context ctx) throws Exception {

		int total = 0;
		int auditClients = 0;
		long clientsAssetsOver = 0;
		long netLossForLastFYE = 0;

		// Object obj = DBUtils.executeDBOperation(ctx, "ClientIndustry", "15");

		Object obj = SqlResources.getSqlMapProcessor(ctx)
				.select("SqlStmts.UserStatementsaveAccountstmtsgetClientIndustry", ctx);
		if (obj != null) {
			List personnelList = (List) obj;
			for (int i = 0; i < personnelList.size(); i++) {
				if (ctx.get("AuditClients" + "_" + i) != null
						&& !"".equals(ctx.get("AuditClients" + "_" + i).toString().trim())) {
					total += Integer.parseInt(ctx.get("AuditClients" + "_" + i).toString().trim());
					if (i == 25)
						auditClients += Integer
								.parseInt(removeAmountFormat(ctx.get("AuditClients" + "_" + i)).toString());
				}

				if (ctx.get("ClientsAssetsOver" + "_" + i) != null
						&& !"".equals(ctx.get("ClientsAssetsOver" + "_" + i).toString().trim())) {
					if (i == 25)
						clientsAssetsOver += Long
								.parseLong(removeAmountFormat(ctx.get("ClientsAssetsOver" + "_" + i)).toString());
				}

				if (ctx.get("NetLossForLastFYE" + "_" + i) != null
						&& !"".equals(ctx.get("NetLossForLastFYE" + "_" + i).toString().trim())) {
					if (i == 25)
						netLossForLastFYE += Long
								.parseLong(removeAmountFormat(ctx.get("NetLossForLastFYE" + "_" + i)).toString());
				}

			}
		}

		if (total == 0)
			populateError(ctx, "TotalAuditClients", "Audit clients needs to be greater than zero");

		if (auditClients > 0 || clientsAssetsOver > 0 || netLossForLastFYE > 0) {
			if (ctx.get("AuditComment") == null)
				populateError(ctx, "AuditComment", "Please describe the other");
			else if (ctx.get("AuditComment") != null && "".equals(ctx.get("AuditComment").toString().trim()))
				populateError(ctx, "AuditComment", "Please describe the other");
		} else {
			ctx.put("AuditComment", "");
		}

		if (ctx.get("FirmRenderedAuditAtt") != null && !"".equals(ctx.get("FirmRenderedAuditAtt").toString())) {
			if (!StringUtils.isNumeric(ctx.get("FirmRenderedAuditAtt").toString()))
				populateError(ctx, "FirmRenderedAuditAtt", "Firm rendered audit must be numeric");
		}

		if (ctx.get("PolicyStatusKey") != null && "2".equals(ctx.get("PolicyStatusKey").toString())) {

			/*
			 * if (ctx.get("IsSyndicationManagers") == null) populateError(ctx,
			 * "IsSyndicationManagers", "Please indicate Yes or No.");
			 * 
			 * if (ctx.get("IsSyndicationManagers") != null &&
			 * "".equals(ctx.get("IsSyndicationManagers").toString())) { populateError(ctx,
			 * "IsSyndicationManagers", "Please indicate Yes or No."); }
			 */

			/*
			 * if (ctx.get("IsDebtOrEquityFinancing") == null) populateError(ctx,
			 * "IsDebtOrEquityFinancing", "Please indicate Yes or No.");
			 * 
			 * if (ctx.get("IsDebtOrEquityFinancing") != null &&
			 * "".equals(ctx.get("IsDebtOrEquityFinancing").toString())) {
			 * populateError(ctx, "IsDebtOrEquityFinancing", "Please indicate Yes or No.");
			 * }
			 */

			/*
			 * if (ctx.get("IsCommissionRecieved") == null) populateError(ctx,
			 * "IsCommissionRecieved", "Please indicate Yes or No."); if
			 * (ctx.get("IsCommissionRecieved") != null &&
			 * "".equals(ctx.get("IsCommissionRecieved").toString())) { populateError(ctx,
			 * "IsCommissionRecieved", "Please indicate Yes or No."); }
			 */
		}

		return true;
	}

	public void insertAttestationAuditWork(IContext ctx) throws Exception {
		boolean bool = true; // isEstimateForCurrentYear(ctx);
		if (bool) {
			Object obj = DBUtils.executeDBOperation(ctx, "ClientIndustry", "15");
			if (obj != null) {
				List personnelList = (List) obj;
				for (int i = 0; i < personnelList.size(); i++) {
					Map map = (Map) personnelList.get(i);
					Context newctx = new Context();
					// newctx.setProject("AccountantIns");

					newctx.setProject(ctx.getProject());
					newctx.put("ClientIndustryKey", ctx.get("ClientIndustryKey" + "_" + i));
					newctx.put("VersionKey", ctx.get("VersionKey"));
					newctx.put("PolicyKey", ctx.get("PolicyKey"));
					newctx.put("VersionSequence", ctx.get("VersionSequence"));
					newctx.put("TransactionSeq", ctx.get("TransactionSeq"));
					newctx.put("AuditClients", ctx.get("AuditClients" + "_" + i));
					newctx.put("ClientsAssetsOver", removeAmountFormat(ctx.get("ClientsAssetsOver" + "_" + i)));
					newctx.put("NetLossForLastFYE", removeAmountFormat(ctx.get("NetLossForLastFYE" + "_" + i)));

					Object obj2 = DBUtils.executeDBOperation(newctx, "AttestationAuditWork", "3");
					if (obj2 == null) {
						DBUtils.executeDBOperation(newctx, "AttestationAuditWork", "1");
					} else {
						DBUtils.executeDBOperation(newctx, "AttestationAuditWork", "2");
						DBUtils.executeDBOperation(newctx, "AttestationAuditWork", "1");
					}

				}
			}
		}
	}

	public static boolean validateITServices(Context ctx) throws Exception {

		long total = 0;
		long otherLastYearRevenue = 0;
		long otherEstimatedRevenue = 0;

		Object obj = DBUtils.executeDBOperation(ctx, "ITServiceCategory", "15");
		if (obj != null) {
			List personnelList = (List) obj;
			for (int i = 0; i < personnelList.size(); i++) {
				if (ctx.get("RevenuesFromLastYear" + "_" + i) != null
						&& !"".equals(ctx.get("RevenuesFromLastYear" + "_" + i).toString().trim())) {
					total += Long.parseLong(removeAmountFormat(ctx.get("RevenuesFromLastYear" + "_" + i)).toString());
					if (i == 5)
						otherLastYearRevenue += Long
								.parseLong(removeAmountFormat(ctx.get("RevenuesFromLastYear" + "_" + i)).toString());
				}
				if (ctx.get("EstimatedRevenue" + "_" + i) != null
						&& !"".equals(ctx.get("EstimatedRevenue" + "_" + i).toString().trim())) {
					total += Long.parseLong(removeAmountFormat(ctx.get("EstimatedRevenue" + "_" + i)).toString());
					if (i == 5)
						otherEstimatedRevenue += Long
								.parseLong(removeAmountFormat(ctx.get("EstimatedRevenue" + "_" + i)).toString());
				}

				// if(ctx.get("IsAnnually"+"_"+i) == null )
				// ctx.put("IsAnnually"+"_"+i,"N");
				//
			}
		}

		if (total == 0)
			populateError(ctx, "TotalITRevenue",
					"Current or Prior fiscal year for one of the items needs to be greater than zero");

		if (otherLastYearRevenue > 0 || otherEstimatedRevenue > 0) {
			if (ctx.get("OtherITServiceComment") == null)
				populateError(ctx, "OtherITServiceComment", "Please describe the IT services provided");
			else if (ctx.get("OtherITServiceComment") != null
					&& "".equals(ctx.get("OtherITServiceComment").toString().trim()))
				populateError(ctx, "OtherITServiceComment", "Please describe the IT services provided");
		} else {
			ctx.put("OtherITServiceComment", "");
		}

		return true;
	}

	public void insertITServiceSupp(IContext ctx) throws Exception {
		Object obj = DBUtils.executeDBOperation(ctx, "ITServiceCategory", "15");
		if (obj != null) {
			List personnelList = (List) obj;
			for (int i = 0; i < personnelList.size(); i++) {
				Map map = (Map) personnelList.get(i);
				Context newctx = new Context();
				newctx.putAll(map);
				newctx.setProject(ctx.getProject());
				newctx.put("VersionKey", ctx.get("VersionKey"));
				newctx.put("ITServiceCategoryKey", ctx.get("ITServiceCategoryKey" + "_" + i));
				newctx.put("PolicyKey", ctx.get("PolicyKey"));
				newctx.put("VersionSequence", ctx.get("VersionSequence"));
				newctx.put("EstimatedRevenue", removeAmountFormat(ctx.get("EstimatedRevenue" + "_" + i)));
				newctx.put("RevenuesFromLastYear", removeAmountFormat(ctx.get("RevenuesFromLastYear" + "_" + i)));

				if (ctx.get("IsAnnually" + "_" + i) != null && ("on".equals(ctx.get("IsAnnually" + "_" + i).toString())
						|| "Y".equals(ctx.get("IsAnnually" + "_" + i).toString())
						|| "N".equals(ctx.get("IsAnnually" + "_" + i).toString())))
					newctx.put("IsAnnually", "Y");
				else
					newctx.put("IsAnnually", null);

				Object obj2 = DBUtils.executeDBOperation(newctx, "ITServicesSupp", "3");
				if (obj2 == null) {
					DBUtils.executeDBOperation(newctx, "ITServicesSupp", "1");
				} else {
					DBUtils.executeDBOperation(newctx, "ITServicesSupp", "2");
					DBUtils.executeDBOperation(newctx, "ITServicesSupp", "1");
				}
			}
		}
	}

	public void insertITServiceNewAppSupp(IContext ctx) throws Exception {
		Object obj = DBUtils.executeDBOperation(ctx, "ITServiceCategory", "15");

		if (obj != null) {
			List personnelList = (List) obj;
			for (int i = 0; i < personnelList.size(); i++) {
				Map map = (Map) personnelList.get(i);
				Context newctx = new Context();
				newctx.putAll(map);
				newctx.setProject(ctx.getProject());
				newctx.put("VersionKey", ctx.get("VersionKey"));
				RuleUtils.executeRule(newctx, "AccountantRule.AssignCreatedByAndCreatedTimeStamp");
				newctx.put("ITServiceCategoryKey", ctx.get("ITServiceCategoryKey" + "_" + i));
				newctx.put("PolicyKey", ctx.get("PolicyKey"));
				newctx.put("VersionSequence", ctx.get("VersionSequence"));
				newctx.put("EstimatedRevenue", removeAmountFormat(ctx.get("EstimatedRevenue" + "_" + i)));
				newctx.put("RevenuesFromLastYear", removeAmountFormat(ctx.get("RevenuesFromLastYear" + "_" + i)));
				newctx.put("IsAnnually", ctx.get("IsAnnually" + "_" + i));

				Object obj2 = DBUtils.executeDBOperation(newctx, "ITServicesSupp", "3");
				if (obj2 == null) {
					DBUtils.executeDBOperation(newctx, "ITServicesSupp", "1");
				} else {
					DBUtils.executeDBOperation(newctx, "ITServicesSupp", "2");
					DBUtils.executeDBOperation(newctx, "ITServicesSupp", "1");
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	private static boolean validateEstimateForCurrentYear(IContext ctx) throws Exception {

		boolean flag = false;
		if (ctx.get("YearEndDate_0") == null && ctx.get("Amount_0") == null) {
			populateError(ctx, "estimate_year", "Fiscal Year End date and amount are required");
			flag = true;
		} else {

			if ((ctx.get("Amount_0") != null && "".equals(ctx.get("Amount_0").toString().trim()))
					&& (ctx.get("YearEndDate_0") != null && "".equals(ctx.get("YearEndDate_0").toString().trim()))) {
				populateError(ctx, "estimate_year", "Fiscal Year End date and amount are required");
				flag = true;
			} else if (ctx.get("YearEndDate_1") == null || "".equals(ctx.get("YearEndDate_1").toString().trim())) {
				populateError(ctx, "estimate_year", "One Prior Fiscal Year End date is required");
				flag = true;
			} else if (ctx.get("YearEndDate_2") == null || "".equals(ctx.get("YearEndDate_2").toString().trim())) {
				populateError(ctx, "estimate_year", "Two Prior Fiscal Year End date is required");
				flag = true;
			} else if (ctx.get("Amount_0") == null || "".equals(ctx.get("Amount_0").toString().trim())) {
				populateError(ctx, "estimate_year", "Last Completed Year Amount is required");
				flag = true;
			} else if (ctx.get("Amount_1") == null || "".equals(ctx.get("Amount_1").toString().trim())) {
				populateError(ctx, "estimate_year", "One Year Prior Amount is required");
				flag = true;
			} else if (ctx.get("Amount_2") == null || "".equals(ctx.get("Amount_2").toString().trim())) {
				populateError(ctx, "estimate_year", "Two Years Prior Amount is required");
				flag = true;
			}
		}

		return flag;
	}

	public void sendMail(IContext ctx) throws Exception {
		String mailingOnOFF = null;
		try {

			mailingOnOFF = SystemProperties.getInstance().getString("Insured.sendmail");
		} catch (Exception e) {
			logger.error("Error in AccountUtils.sendMail", e);
			throw e;
		}

		if (mailingOnOFF != null && "Y".equals(mailingOnOFF)) {
			// if(ctx.get("IsQuickQuoteByEmail") != null &&
			// "Y".equals(ctx.get("IsQuickQuoteByEmail").toString()))
			// {

			String emailID = getEmailID(ctx);
			if ("".equals(emailID))
				return;
			String bccAddress = "";
			bccAddress = SystemProperties.getInstance().getString("appl." + ctx.getProject() + ".admin.cc.email");

			logger.debug("Mail is going---- ");
			MailSender mail = new MailSender();
			mail.setToAdd(emailID);
			mail.setBccAdd(bccAddress);
			mail.setSubject("Thank you for your interest in Protexure Accountants");
			mail.setContentType("text/html");
			mail.setBody(generateThanksLetterBody(ctx));
			mail.sendMail((Context) ctx);
			logger.debug("Mail has sent---- ");
			// }
		}

	}

	private String generateThanksLetterBody(IContext ctx) throws Exception {
		String msg = "";
		msg = msg + "<table>";
		msg = msg + "<tr>";
		msg = msg + "<td>";

		msg = msg + "Dear " + ctx.get("ContactPerson") + ",<br/><br/>";

		msg = msg
				+ "Thank you for beginning the Protexure Accountants application. We appreciate the opportunity to provide you with a quote. Choosing the right plan is very important and we believe that the options we offer will provide you and your firm with peace of mind."
				+ "<br/><br/>";

		msg = msg
				+ "Once you have completed your application, our underwriters will begin the review process and will contact you within one business day. "
				+ "<br/><br/>";

		msg = msg
				+ "The security of your information is important to us. If you have not already registered please <a href="
				+ getProjectUrl(ctx) + "/login.jsp?PolicyKey=" + ctx.get("PolicyKey").toString()
				+ " >click here</a>. To review your application securely you will need to register." + "<br/><br/>";
		msg = msg + "If you have already registered, <a href=" + getProjectUrl(ctx) + "/accountrep.jsp?PolicyKey="
				+ ctx.get("PolicyKey").toString() + ">click here</a> if you would like to review your application."
				+ "<br/><br/>";
		msg = msg
				+ "If you have any questions or concerns, please call us toll free at 1-888-803-9898, and one of our Account Executives will assist you."
				+ "<br/><br/>";

		msg = msg + "Thank you again for considering Protexure Accountant for your Professional Liability needs."
				+ "<br/><br/>";
		msg = msg + "Best regards," + "<br/><br/>";
		msg = msg + "<b>Protexure Accountants</b><br/>";
		msg = msg + "<b>1-888-803-9898</b>";

		msg = msg + "</td>";
		msg = msg + "</tr>";
		msg = msg + "</table>";

		return msg;
	}

	@SuppressWarnings("unchecked")
	public void currentPage(IContext ctx) throws Exception {

		if (ctx.get("page") != null) {
			if ("BasicInfo".equals(ctx.get("page"))) {
				if ("".equals(ctx.get("IsBasicPage")) || ctx.get("IsBasicPage") == null)
					ctx.put("IsBasicPage", "N");
				else
					ctx.put("IsBasicPage", ctx.get("IsBasicPage"));
			}
			if ("general".equals(ctx.get("page"))) {
				if ("".equals(ctx.get("IsGeneralPage")) || ctx.get("IsGeneralPage") == null)
					ctx.put("IsBasicPage", "N");
				else
					ctx.put("IsBasicPage", ctx.get("IsGeneralPage"));
			}
			if ("invest-finan-planing".equals(ctx.get("page"))) {
				if ("".equals(ctx.get("IsSupplementPage")) || ctx.get("IsSupplementPage") == null)
					ctx.put("IsBasicPage", "N");
				else
					ctx.put("IsBasicPage", ctx.get("IsSupplementPage"));
			}
			if ("attestation".equals(ctx.get("page"))) {
				if ("".equals(ctx.get("IsAttesationPage")) || ctx.get("IsAttesationPage") == null)
					ctx.put("IsBasicPage", "N");
				else
					ctx.put("IsBasicPage", ctx.get("IsAttesationPage"));
			}
			if ("computer-services".equals(ctx.get("page"))) {
				if ("".equals(ctx.get("IsComputerServicePage")) || ctx.get("IsComputerServicePage") == null)
					ctx.put("IsBasicPage", "N");
				else
					ctx.put("IsBasicPage", ctx.get("IsComputerServicePage"));
			}
			if ("Business-funds-control".equals(ctx.get("page"))) {
				if ("".equals(ctx.get("IsBusinessFundPage")) || ctx.get("IsBusinessFundPage") == null)
					ctx.put("IsBasicPage", "N");
				else
					ctx.put("IsBasicPage", ctx.get("IsBusinessFundPage"));
			}
			if ("Practice-Managemen".equals(ctx.get("page"))) {
				if ("".equals(ctx.get("IsPracriceMangPage")) || ctx.get("IsPracriceMangPage") == null)
					ctx.put("IsBasicPage", "N");
				else
					ctx.put("IsBasicPage", ctx.get("IsPracriceMangPage"));
			}
		}
	}

	@SuppressWarnings("unchecked")
	public void checkIssuedAndOtherStateAgent(IContext ctx) throws Exception {
		ctx.put("isPolicyIssued", "N");
		ctx.put("isOtherStateAgent", "N");
		if (ctx.get("StatusKey") != null) {
			if ("5".equals(ctx.get("StatusKey"))) {
				// ctx.put("User", "insured");
				ctx.put("isPolicyIssued", "Y");

			}
		}

		if (ctx.get("AgentState") != null && ctx.get("StateCode") != null) {
			if (!ctx.get("StateCode").toString().equals(ctx.get("AgentState"))) {
				// ctx.put("User", "insured");
				ctx.put("isOtherStateAgent", "Y");
			}
		}
	}

	public void populateLicenseNumber(IContext ctx) throws Exception {

		if (ctx.get("labels_freeform_99") != null) {
			Map map = (Map) ctx.get("labels_freeform_99");
			if (map.get("LicenseNumber") == null && !"Y".equals(map.get("IsLicenseNumberUpdated"))) {
				map.put("LicenseNumber", ctx.get("Org_LicenseNumber"));
				SqlResources.getSqlMapProcessor(ctx).update("SqlStmts.UserStatementsaveAccountstmtsUpdateFirm", ctx);
			}

			// map.put("labels_freeform_99", map);
		}
	}

	@SuppressWarnings("unchecked")
	public void isFirmExist(IContext ctx) throws Exception {
		Object obj = ctx.get("Account_Info");

		if (obj != null && ((List) obj).size() != 0 && ((List) obj).size() == 1) {
			Map map = (Map) ((List) obj).get(0);
			if (ctx.get("InsuredLoginID") != null && !"".equals(ctx.get("InsuredLoginID"))) {
				if (ctx.get("InsuredPassword") != null && !"".equals(ctx.get("InsuredPassword"))) {
					if (map.get("WorkPhone").equals(ctx.get("InsuredPassword"))
							&& map.get("AccountEmail").equals(ctx.get("InsuredLoginID"))) {
						ctx.putAll(map);
						return;
					} else {
						List errorList = new ArrayList();
						if (ctx.get(Constants.INET_ERRORS_LIST) != null)
							errorList = (List) ctx.get(Constants.INET_ERRORS_LIST);

						errorList.add(new ValidationException(
								"Email/Phone do not match. Please contact 1-888-803-9898 for any assiatance"));
						ctx.put(Constants.INET_ERRORS_LIST, errorList);
						ctx.put(Constants.INET_FORM_DIRTY, Constants.YES);
					}
				}
			}
		} else {
			List errorList = new ArrayList();
			if (ctx.get(Constants.INET_ERRORS_LIST) != null)
				errorList = (List) ctx.get(Constants.INET_ERRORS_LIST);

			errorList.add(new ValidationException(
					"Email/Phone do not match. Please contact 1.888.803.9898 for any assiatance"));
			ctx.put(Constants.INET_ERRORS_LIST, errorList);
			ctx.put(Constants.INET_FORM_DIRTY, Constants.YES);
		}
	}

	public static boolean checkReferRuleforClaims1(List claimsList, Date effectiveDate) throws Exception {

		if (claimsList == null || claimsList.isEmpty() || effectiveDate == null)
			return false;
		Map map = new HashMap();
		for (int i = 0; i < claimsList.size(); i++) {
			map = (HashMap) claimsList.get(i);
			if (map.get("ClaimNotifiedCiDate") != null
					&& DateUtils.calculateDiffInDays(DateUtils.addMonthsToDate(effectiveDate, -12),
							(Date) map.get("ClaimNotifiedCiDate")) <= 0
					&& DateUtils.calculateDiffInDays(effectiveDate, (Date) map.get("ClaimNotifiedCiDate")) >= 0) {
				if (map.get("TotalClaimExpensesCi") != null
						&& Long.parseLong(map.get("TotalClaimExpensesCi").toString()) > 0)
					return true;
			}

		}

		return false;
	}

	public static boolean checkReferRuleforClaims2(List claimsList, Date effectiveDate) throws Exception {

		if (claimsList == null || claimsList.isEmpty() || effectiveDate == null)
			return false;
		Map map = new HashMap();
		int count = 0;
		boolean flag = false;
		for (int i = 0; i < claimsList.size(); i++) {
			map = (HashMap) claimsList.get(i);
			if (map.get("ClaimNotifiedCiDate") != null
					&& DateUtils.calculateDiffInDays(DateUtils.addMonthsToDate(effectiveDate, -60),
							(Date) map.get("ClaimNotifiedCiDate")) <= 0
					&& DateUtils.calculateDiffInDays(effectiveDate, (Date) map.get("ClaimNotifiedCiDate")) >= 0) {
				count = count + 1;
			}

			if (map.get("ClaimNotifiedCiDate") != null
					&& DateUtils.calculateDiffInDays(DateUtils.addMonthsToDate(effectiveDate, -60),
							(Date) map.get("ClaimNotifiedCiDate")) <= 0
					&& DateUtils.calculateDiffInDays(DateUtils.addMonthsToDate(effectiveDate, -12),
							(Date) map.get("ClaimNotifiedCiDate")) >= 0
					&& map.get("TotalClaimExpensesCi") != null
					&& Long.parseLong(map.get("TotalClaimExpensesCi").toString()) >= 50000)
				flag = true;
		}
		if (flag || count >= 3)
			return true;

		return false;
	}

	public static long calculateMeanForCompleteList(List datalist, String property) {
		if (datalist == null || datalist.isEmpty())
			return 0;

		List list = new ArrayList();
		for (int i = 0; i < datalist.size(); i++) {
			Map map = (Map) datalist.get(i);
			if (null != map.get(property) && !"".equals(map.get(property).toString()))
				list.add(Long.parseLong(map.get(property).toString()));

		}
		return calculateMean(list);
	}

	private static long calculateMean(List list) {

		if (list == null || list.isEmpty())
			return 0;

		long sumtotal = 0;

		for (int i = 0; i < list.size(); i++) {

			sumtotal = sumtotal + (Long) list.get(i);
		}

		if (sumtotal == 0)
			return 0;

		return sumtotal / list.size();
	}

	public void getAverageRevenue(IContext ctx) throws Exception {

		int count = 0;
		long total = 0;
		long average = 0;

		Object obj0 = removeAmountFormat(ctx.get("Amount_0"));
		Object obj1 = removeAmountFormat(ctx.get("Amount_1"));
		Object obj2 = removeAmountFormat(ctx.get("Amount_2"));

		if (obj0 != null && !"".equals(obj0.toString()) && !"0".equals(obj0.toString())) {
			count = count + 1;
			total = total + Long.parseLong(obj0.toString());
		}
		if (obj1 != null && !"".equals(obj1.toString()) && !"0".equals(obj1.toString())) {
			count = count + 1;
			total = total + Long.parseLong(obj1.toString());
		}
		if (obj2 != null && !"".equals(obj2.toString()) && !"0".equals(obj2.toString())) {
			count = count + 1;
			total = total + Long.parseLong(obj2.toString());
		}

		if (count != 0 && total != 0) {
			average = total / count;
		}

		ctx.put("AverageRevFromAjax", average);
		SqlResources.getSqlMapProcessor(ctx).update("SqlStmts.UserStatementsaveAccountstmtsupdateAverageRevenue", ctx);

	}

	public void getAverageRevenueRenewApplication(IContext ctx) throws Exception {

		int count = 0;
		long total = 0;
		long average = 0;

		Object objRevenue = SqlResources.getSqlMapProcessor(ctx)
				.select("SqlStmts.UserStatementrenewapplicationmomgrossrevenueData", ctx);

		if (objRevenue != null && objRevenue instanceof List) {
			List revenueList = (List) objRevenue;
			for (int i = 0; i < revenueList.size(); i++) {
				Map map = (Map) revenueList.get(i);
				Object objAmount = removeAmountFormat(map.get("Amount"));
				if (objAmount != null && !"".equals(objAmount.toString()) && !"0".equals(objAmount.toString())) {
					total = total + Long.parseLong(objAmount.toString());
					count = count + 1;
				}
			}
			if (count > 0) {
				long AverageRevenue = total / count;
				ctx.put("AverageRevFromAjax", AverageRevenue);
				ctx.put("AverageRevenue", AverageRevenue);
				SqlResources.getSqlMapProcessor(ctx)
						.update("SqlStmts.UserStatementsaveAccountstmtsupdateAverageRevenue", ctx);
			} else {
				ctx.put("AverageRevFromAjax", 0);
				ctx.put("AverageRevenue", 0);
			}
		}
	}

	public static Object removeAmountFormat(Object arg1) {
		if (arg1 == null || "".equals(arg1.toString().trim()))
			return "";

		String amount = arg1.toString();
		return amount.replace("$", "").replace(",", "");
	}

	public void processLimitDeductibleDropDown(IContext ctx) throws Exception {
		processDeductibeDropDown(ctx);
		processLimitDropDown(ctx);
	}

	public void processDeductibeDropDown(IContext ctx) throws Exception {

		Object obj = ctx.get("getLimitDeductibleDropDownPross");
		List listDeductible = null;
		if (obj != null)
			listDeductible = (List) obj;

		if (listDeductible == null)
			return;

		if (ctx.get("general_freeform_01") == null || ctx.get("BasicInfo_freeform_00") == null)
			return;

		if (((Map) ctx.get("general_freeform_01")).get("DeductibleSequencePross") == null
				|| ((Map) ctx.get("BasicInfo_freeform_00")).get("DeductibleSequence") == null)
			return;

		String strDeductibleSequencePross = ((Map) ctx.get("general_freeform_01")).get("DeductibleSequencePross")
				.toString();
		String strDeductibleSequence = ((Map) ctx.get("BasicInfo_freeform_00")).get("DeductibleSequence").toString();

		int indexPross = 0;
		int indexPolicyCov = 0;
		long amountPross = 0;
		long amountPolicyCov = 0;

		for (int i = 0; i < listDeductible.size(); i++) {
			Map map = (Map) listDeductible.get(i);

			String strDeductibleSeq = map.get("DeductibleSequence").toString();
			String strDeductibleAmount = removeAmountFormat(map.get("DeductibleAmount")).toString();
			if (strDeductibleSequencePross.equals(strDeductibleSeq)) {
				indexPross = i;
				// amountPross = Long.parseLong(strDeductibleAmount);
			}
			if (strDeductibleSequence.equals(strDeductibleSeq)) {
				indexPolicyCov = i;
				// amountPolicyCov = Long.parseLong(strDeductibleAmount);
			}
		}

		// indexPross = indexPross - 2;
		if (indexPross > indexPolicyCov) {
			indexPross = indexPross - indexPolicyCov;
		} else if (indexPross < indexPolicyCov) {
			indexPross = indexPolicyCov - indexPross;
		} else {
			indexPross = indexPross - indexPolicyCov;
		}

		if (indexPross > 2)
			ctx.put("referDeductible", "Y");
		else
			ctx.put("referDeductible", "N");

		/*
		 * if(indexPross > 0) ctx.put("referDeductible", "N"); else if(indexPolicyCov <
		 * indexPross) ctx.put("referDeductible", "Y"); else ctx.put("referDeductible",
		 * "N");
		 */
	}

	public void processLimitDropDown(IContext ctx) throws Exception {

		Object obj = ctx.get("getDropDownLimitsPross");
		List listLimit = null;
		if (obj != null)
			listLimit = (List) obj;

		if (listLimit == null)
			return;

		if (ctx.get("general_freeform_01") == null || ctx.get("BasicInfo_freeform_00") == null)
			return;

		if (((Map) ctx.get("general_freeform_01")).get("LimitSequencePross") == null
				|| ((Map) ctx.get("BasicInfo_freeform_00")).get("LimitSequence") == null)
			return;

		String strLimitSequencePross = ((Map) ctx.get("general_freeform_01")).get("LimitSequencePross").toString();
		String strLimitSequence = ((Map) ctx.get("BasicInfo_freeform_00")).get("LimitSequence").toString();

		long amountPross = 0;
		long amountPolicyCov = 0;
		long uperLimitAmt = 0;
		long lowerLimtAmt = 0;
		int amountProssIndex = 0;
		boolean upperFlag = false;
		boolean amountProssFlag = false;

		for (int i = 0; i < listLimit.size(); i++) {
			Map map = (Map) listLimit.get(i);

			String strLimitSeq = map.get("LimitSequence").toString();
			String strLimitAmount = removeAmountFormat(map.get("LimitAmount")).toString();
			strLimitAmount = strLimitAmount.substring(0, strLimitAmount.indexOf("/"));
			if (strLimitSequencePross.equals(strLimitSeq)) {
				amountPross = Long.parseLong(strLimitAmount);
				amountProssFlag = true;
				amountProssIndex = i;
			}
			if (!upperFlag && amountProssFlag && Long.parseLong(strLimitAmount) > amountPross) {
				uperLimitAmt = Long.parseLong(strLimitAmount);
				upperFlag = true;
			}
			if (strLimitSequence.equals(strLimitSeq)) {

				amountPolicyCov = Long.parseLong(strLimitAmount);
			}
		}

		for (; amountProssIndex >= 0; amountProssIndex--) {
			Map map = (Map) listLimit.get(amountProssIndex);

			String strLimitSeq = map.get("LimitSequence").toString();
			String strLimitAmount = removeAmountFormat(map.get("LimitAmount")).toString();
			strLimitAmount = strLimitAmount.substring(0, strLimitAmount.indexOf("/"));

			if (Long.parseLong(strLimitAmount) < amountPross) {
				lowerLimtAmt = Long.parseLong(strLimitAmount);
				break;
			}

		}

		if (amountPolicyCov > uperLimitAmt)
			ctx.put("referLimit", "Y");
		else if (amountPolicyCov < lowerLimtAmt)
			ctx.put("referLimit", "Y");
		else
			ctx.put("referLimit", "N");

	}

	public static void validateBasicinfoDateField(IContext ctx) throws Exception {
		String strDate = null;
		boolean flag = false;

		if (ctx.get("PolicyEffectiveDate") != null && !"".equals(ctx.get("PolicyEffectiveDate").toString())) {
			strDate = ctx.get("PolicyEffectiveDate").toString();

			Object obj = RuleUtils.executeRule(ctx, "AccountantRule.IsPolicyRenewTypeAndNotReadOnly");
			if (obj != null && obj instanceof Boolean)
				flag = (Boolean) obj;

			if (flag)
				validateLeapYearDate(ctx, "PolicyEffectiveDate", strDate);

		}

		if (ctx.get("IsFirmCarryingProfLiabilityIns") != null
				&& "Y".equals(ctx.get("IsFirmCarryingProfLiabilityIns").toString())) {
			if (ctx.get("PolicyExpirationDatePross") != null
					&& !"".equals(ctx.get("PolicyExpirationDatePross").toString())) {
				strDate = ctx.get("PolicyExpirationDatePross").toString();
				validateLeapYearDate(ctx, "PolicyExpirationDatePross", strDate);
			}

			if (ctx.get("IsPriorActDateFull") != null && "N".equals(ctx.get("IsPriorActDateFull").toString())) {
				if (ctx.get("PriorActDatePross") != null && !"".equals(ctx.get("PriorActDatePross").toString())) {
					strDate = ctx.get("PriorActDatePross").toString();
					validateLeapYearDate(ctx, "PriorActDatePross", strDate);
				}

			}
		}

		if (ctx.get("YearEndDate_0") != null && !"".equals(ctx.get("YearEndDate_0").toString())) {
			strDate = ctx.get("YearEndDate_0").toString();
			validateMMYYYYDate(ctx, "estimate_year", strDate);
		}
		if (ctx.get("YearEndDate_1") != null && !"".equals(ctx.get("YearEndDate_1").toString())) {
			strDate = ctx.get("YearEndDate_1").toString();
			validateMMYYYYDate(ctx, "estimate_year", strDate);
		}
		if (ctx.get("YearEndDate_2") != null && !"".equals(ctx.get("YearEndDate_2").toString())) {
			strDate = ctx.get("YearEndDate_2").toString();
			validateMMYYYYDate(ctx, "estimate_year", strDate);
		}
	}

	public static void validateGeneralDateField(IContext ctx) throws Exception {
		String strDate = null;
		if (ctx.get("IsFirmRenderingServices") != null && "Y".equals(ctx.get("IsFirmRenderingServices").toString())) {
			if (ctx.get("EstimateCurrentYearSeEndDate") != null
					&& !"".equals(ctx.get("EstimateCurrentYearSeEndDate").toString())) {
				strDate = ctx.get("EstimateCurrentYearSeEndDate").toString();
				validateMMYYYYDate(ctx, "EstimateCurrentYearSeEndDate", strDate);
			}

			if (ctx.get("LastFiscalYearSeEndDate") != null
					&& !"".equals(ctx.get("LastFiscalYearSeEndDate").toString())) {
				strDate = ctx.get("LastFiscalYearSeEndDate").toString();
				validateMMYYYYDate(ctx, "LastFiscalYearSeEndDate", strDate);
			}
		}
	}

	public static void validateGeneralPhoneNumber(IContext ctx) throws Exception {

		if ((ctx.get("IsFullQuote") != null && "Y".equals(ctx.get("IsFullQuote").toString()))
				&& (ctx.get("IsQuickQuote") != null && "N".equals(ctx.get("IsQuickQuote").toString()))) {
			if (ctx.get("page") != null && ("general".equals(ctx.get("page").toString()))) {
				if (ctx.get("WorkPhone") != null && !"".equals(ctx.get("WorkPhone").toString())) {
					if (!validatePhoneNumber(ctx.get("WorkPhone").toString().trim()))
						populateError(ctx, "WorkPhone", "Phone should be at least of 10 digit.");
				}

				if (ctx.get("ContactPhone") != null && !"".equals(ctx.get("ContactPhone").toString())) {
					if (!validatePhoneNumber(ctx.get("ContactPhone").toString().trim()))
						populateError(ctx, "ContactPhone", "Phone should be at least of 10 digit.");
				}
			}

			if ("BasicInfo".equals(ctx.get("page").toString())) {
				if (ctx.get("WorkPhone") != null && !"".equals(ctx.get("WorkPhone").toString())) {
					if (!validatePhoneNumber(ctx.get("WorkPhone").toString().trim()))
						populateError(ctx, "WorkPhone", "Phone should be at least of 10 digit.");
				}
			}
		}

	}

	public static boolean validatePhoneNumber(String phoneNumber) throws Exception {

		if (phoneNumber == null)
			return false;

		phoneNumber = phoneNumber.replace("(", "").replace(")", "").replace("-", "");
		if (phoneNumber.length() < 10)
			return false;

		return true;
	}

	public static void validateLeapYearDate(IContext ctx, String field, String strDate) throws Exception {
		try {
			if (strDate != null && !strDate.equals("")) {
				strDate = strDate.trim();
				String strMonth = strDate.substring(0, strDate.indexOf("/"));
				String cursubstr = strDate.substring(strDate.indexOf("/") + 1, strDate.length());
				String strDay = cursubstr.substring(0, cursubstr.indexOf("/"));
				String strYear = cursubstr.substring(cursubstr.indexOf("/") + 1, cursubstr.length());

				int curMonth = Integer.parseInt(strMonth);
				int curDay = Integer.parseInt(strDay);
				int curYear = Integer.parseInt(strYear);

				if (strYear.trim().length() < 4) {
					populateError(ctx, field, "Invalid date format.");
				} else if (curMonth <= 0 || curDay <= 0 || curYear <= 0) {
					populateError(ctx, field, "Invalid date format.");
				} else if (isLeapYear(curYear)) {
					if (curMonth == 2 && curDay > 29) {
						populateError(ctx, field, "Invalid date.");
					} else if (curMonth != 2 && curDay > 31) {
						populateError(ctx, field, "Invalid date.");
					} else if (curMonth > 13) {
						populateError(ctx, field, "Invalid date.");
					} else if ((curMonth == 4 || curMonth == 6 || curMonth == 9 || curMonth == 11) && curDay > 30) {
						populateError(ctx, field, "Invalid date.");
					} else if (curYear < 1800) {
						populateError(ctx, field, "Invalid date.");
					}
				} else if (curMonth == 2 && curDay > 28) {
					populateError(ctx, field, "Invalid date.");
				} else if (curMonth != 2 && curDay > 31) {
					populateError(ctx, field, "Invalid date.");
				} else if (curMonth > 13) {
					populateError(ctx, field, "Invalid date.");
				} else if ((curMonth == 4 || curMonth == 6 || curMonth == 9 || curMonth == 11) && curDay > 30) {
					populateError(ctx, field, "Invalid date.");
				} else if (curYear < 1800) {
					populateError(ctx, field, "Invalid date.");
				}
			}
		} catch (Exception e) {
			logger.error("Error in AccountUtils.validateLeapYearDate", e);
			populateError(ctx, field, "Invalid date format.");
		}
	}

	/*
	 * public static void validateLeapYearDate(IContext ctx, String field, String
	 * strDate) throws Exception { try { if (strDate != null && strDate != "") {
	 * strDate = strDate.trim(); String strMonth = strDate.substring(0,
	 * strDate.indexOf("/")); String cursubstr =
	 * strDate.substring(strDate.indexOf("/") + 1, strDate.length()); String strDay
	 * = cursubstr.substring(0, cursubstr.indexOf("/")); String strYear =
	 * cursubstr.substring( cursubstr.indexOf("/") + 1, cursubstr.length());
	 * 
	 * int curMonth = Integer.parseInt(strMonth); int curDay =
	 * Integer.parseInt(strDay); int curYear = Integer.parseInt(strYear);
	 * 
	 * if (curYear < 1900) { populateError(ctx, field, "Invalid year"); }
	 * 
	 * if (strYear.trim().length() < 4) { populateError(ctx, field, "Invalid date
	 * format"); } else if (curMonth == 0 || curDay == 0 || curYear == 0) {
	 * populateError(ctx, field, "Invalid date format"); } else if
	 * (isLeapYear(curYear)) { if (curMonth == 2 && curDay > 29) {
	 * populateError(ctx, field, "Invalid date"); } else if (curMonth != 2 && curDay
	 * > 31) { populateError(ctx, field, "Invalid date"); } else if (curMonth > 13)
	 * { populateError(ctx, field, "Invalid date"); } } else if (curMonth == 2 &&
	 * curDay > 28) { populateError(ctx, field, "Invalid date"); } else if (curMonth
	 * != 2 && curDay > 31) { populateError(ctx, field, "Invalid date"); } else if
	 * (curMonth > 13) { populateError(ctx, field, "Invalid date"); } } } catch
	 * (Exception e) { populateError(ctx, field, "Invalid date format"); throw e; }
	 * }
	 */
	public static void validateMMYYYYDate(IContext ctx, String field, String strDate) throws Exception {
		try {
			if (strDate != null && strDate != "") {
				String strMonth = strDate.substring(0, strDate.indexOf("/"));
				String strYear = strDate.substring(strDate.indexOf("/") + 1, strDate.length());
				int curMonth = Integer.parseInt(strMonth);
				int curYear = Integer.parseInt(strYear);
				if (strYear.trim().length() < 4) {
					populateError(ctx, field, "Invalid date format");
				} else if (curMonth == 0 || curYear == 0) {
					populateError(ctx, field, "Invalid date");
				} else if (curMonth > 12) {
					populateError(ctx, field, "Invalid date");
				}

			}
		} catch (Exception e) {
			logger.error("Error in AccountUtils.validateMMYYYYDate", e);
			populateError(ctx, field, "Invalid date format");
			throw e;
		}
	}

	public static boolean isLeapYear(int dtYear) {
		if (dtYear % 4 == 0) {
			if (dtYear % 100 != 0) {
				return true;
			} else {
				if (dtYear % 400 == 0)
					return true;
				else
					return false;
			}
		}
		return false;
	}

	public static boolean validateTrustee(Context ctx) throws Exception {
		String strDate = null;
		if (ctx.get("StartEngagemenTrusttDate") != null && !"".equals(ctx.get("StartEngagemenTrusttDate").toString())) {
			strDate = ctx.get("StartEngagemenTrusttDate").toString();
			validateLeapYearDate(ctx, "StartEngagemenTrusttDate", strDate);
		}

		return true;
	}

	public static boolean validateClaim(Context ctx) throws Exception {
		String strDate = null;
		if (ctx.get("AllegedErrorCiDate") != null && !"".equals(ctx.get("AllegedErrorCiDate").toString())) {
			strDate = ctx.get("AllegedErrorCiDate").toString();
			validateLeapYearDate(ctx, "AllegedErrorCiDate", strDate);
		}

		if (ctx.get("ReportedCiDate") != null && !"".equals(ctx.get("ReportedCiDate").toString())) {
			strDate = ctx.get("ReportedCiDate").toString();
			validateLeapYearDate(ctx, "ReportedCiDate", strDate);
		}

		if (ctx.get("ClaimNotifiedCiDate") != null && !"".equals(ctx.get("ClaimNotifiedCiDate").toString())) {
			strDate = ctx.get("ClaimNotifiedCiDate").toString();
			validateLeapYearDate(ctx, "ClaimNotifiedCiDate", strDate);
		}

		if (ctx.get("ClosingCiDate") != null && !"".equals(ctx.get("ClosingCiDate").toString())) {
			strDate = ctx.get("ClosingCiDate").toString();
			validateLeapYearDate(ctx, "ClosingCiDate", strDate);
		}

		return true;
	}

	public void processSupplement(IContext ctx) throws Exception {

		isPMSupplementExist(ctx);
		isInvestmentSupplementExist(ctx);
		isAuditSupplementExist(ctx);
		isComputerServiceSupplementExist(ctx);
		isBusinessSupplementExist(ctx);
		validateCompleteness(ctx);
	}

	public static void validateCompleteness(IContext ctx) throws Exception {

		if (ctx.get("AverageRevFromAjax") != null) {
			long avg = Long.parseLong(ctx.get("AverageRevFromAjax").toString());
			if (avg <= 500000) {
				ctx.put("IsPracriceMangPage", "N");
				((HttpServletRequest) ctx.get(Constants.HTTPREQUEST)).getSession()
						.removeAttribute("IsPracriceMangPage");
			}
		}

		if (ctx.get("AOP_Percentage_19") == null || "".equals(ctx.get("AOP_Percentage_19").toString().trim())
				|| Integer.parseInt(ctx.get("AOP_Percentage_19").toString().trim()) == 0) {
			ctx.put("IsSupplementPage", "N");
			((HttpServletRequest) ctx.get(Constants.HTTPREQUEST)).getSession().removeAttribute("IsSupplementPage");
		}

		if ((ctx.get("AOP_Percentage_5") == null || "".equals(ctx.get("AOP_Percentage_5").toString().trim())
				|| Integer.parseInt(ctx.get("AOP_Percentage_5").toString().trim()) == 0)
				&& (ctx.get("AOP_Percentage_6") == null || "".equals(ctx.get("AOP_Percentage_6").toString().trim())
						|| Integer.parseInt(ctx.get("AOP_Percentage_6").toString().trim()) == 0)) {
			ctx.put("IsAttesationPage", "N");
			((HttpServletRequest) ctx.get(Constants.HTTPREQUEST)).getSession().removeAttribute("IsAttesationPage");
		}

		if (ctx.get("AOP_Percentage_14") == null || "".equals(ctx.get("AOP_Percentage_14").toString().trim())
				|| Integer.parseInt(ctx.get("AOP_Percentage_14").toString().trim()) == 0) {
			ctx.put("IsComputerServicePage", "N");
			((HttpServletRequest) ctx.get(Constants.HTTPREQUEST)).getSession().removeAttribute("IsComputerServicePage");
		}

		String IsControlClientFunds = "";
		Object obj = SqlResources.getSqlMapProcessor(ctx).findByKey("Firm.findByKey", ctx);
		if (obj != null) {
			Map map = (Map) obj;
			if (map.get("IsControlClientFunds") != null && !"".equals(map.get("IsControlClientFunds").toString()))
				IsControlClientFunds = map.get("IsControlClientFunds").toString();
		}

		if ((ctx.get("AOP_Percentage_18") == null || "".equals(ctx.get("AOP_Percentage_18").toString().trim())
				|| Integer.parseInt(ctx.get("AOP_Percentage_18").toString().trim()) == 0)
				&& ("".equals(IsControlClientFunds) || "N".equals(IsControlClientFunds))) {
			ctx.put("IsBusinessFundPage", "N");
			((HttpServletRequest) ctx.get(Constants.HTTPREQUEST)).getSession().removeAttribute("IsBusinessFundPage");
		}

		SqlResources.getSqlMapProcessor(ctx).update("Firm.update", ctx);

	}

	public void isPMSupplementExist(IContext ctx) throws Exception {

		if (ctx.get("PolicyStatusKey") != null && !"".equals(ctx.get("PolicyStatusKey").toString())) {

			if ("2".equals(ctx.get("PolicyStatusKey").toString()))
				getAverageRevenueRenewApplication(ctx);
			else
				getAverageRevenue(ctx);

		}

		if (ctx.get("AverageRevFromAjax") != null)

		{
			long avg = Long.parseLong(ctx.get("AverageRevFromAjax").toString());
			if (avg <= 500000) {
				// ctx.put("IsSuitInstituted", "");
				// ctx.put("AgainstHowManyClients", "");
				ctx.put("IsNonMonetaryCompensationRecieved", "");
				ctx.put("CurrentValueNmc", "");
				ctx.put("ClientNameNmc", "");
				ctx.put("ClientIndustryNmcKey", 0);
				ctx.put("ClientIndustryNmcKeyOthers", "");
				ctx.put("ServiceIndustryNmcKey", 0);
				ctx.put("ServiceIndustryNmcKeyOthers", "");
				ctx.put("IsReducedNumberOfOwners", "");
				ctx.put("MergedOrNumberComment", "");
				ctx.put("IsClientTerminated", "");
				ctx.put("IsAnyDeclination", "");
				ctx.put("IsPolicyProhibiting", "");
				ctx.put("IsWrittenAnyQualityControlDocument", "");

				SqlResources.getSqlMapProcessor(ctx).update("Firm.update", ctx);
			}
		}
	}

	public void isInvestmentSupplementExist(IContext ctx) throws Exception {

		if (ctx.get("AOP_Percentage_19") == null || "".equals(ctx.get("AOP_Percentage_19").toString().trim())
				|| Integer.parseInt(ctx.get("AOP_Percentage_19").toString().trim()) == 0) {
			ctx.put("IsFinancialPlansPreparing", "");
			ctx.put("IsDiscretionaryAssetMangt", "");
			ctx.put("IsNonDiscretionaryAssetMangt", "");
			ctx.put("IsSecuritiesSales", "");
			ctx.put("IsContractualRelationShip", "");
			ctx.put("IsOmissionsPolicy", "");
			ctx.put("IsAnyNonPublicInvestments", "");
			ctx.put("IsAnyFirmAffiliateLicensed", "");
			ctx.put("IsAnyEmployeeBenefitPlan", "");

			SqlResources.getSqlMapProcessor(ctx).update("FinancialPlanningAndIAServiceSupp.update", ctx);
		}
	}

	public void isAuditSupplementExist(IContext ctx) throws Exception {

		if ((ctx.get("AOP_Percentage_5") == null || "".equals(ctx.get("AOP_Percentage_5").toString().trim())
				|| Integer.parseInt(ctx.get("AOP_Percentage_5").toString().trim()) == 0)
				&& (ctx.get("AOP_Percentage_6") == null || "".equals(ctx.get("AOP_Percentage_6").toString().trim())
						|| Integer.parseInt(ctx.get("AOP_Percentage_6").toString().trim()) == 0)) {
			ctx.put("FirmRenderedAuditAtt", "");
			ctx.put("IsFirmHaveAWrittenPolicyAtt", "");
			ctx.put("IsSecondPartnerReviewAtt", "");
			ctx.put("IsAuditEngagementsAtt", "");
			ctx.put("IsAualityReviewAtt", "");
			ctx.put("IsLastPeerReviewAtt", "");
			ctx.put("LastPeerReviewAttComment", "");

			if (ctx.get("PolicyStatusKey") != null && "2".equals(ctx.get("PolicyStatusKey").toString())) {
				ctx.put("IsSyndicationManagers", "");
				ctx.put("IsDebtOrEquityFinancing", "");
				ctx.put("IsCommissionRecieved", "");

				SqlResources.getSqlMapProcessor(ctx)
						.update("SqlStmts.UserStatementsaveAccountstmtsupdateGeneralDataOnAttestationPage", ctx);
			}

			SqlResources.getSqlMapProcessor(ctx).update("Attestation.update", ctx);
			SqlResources.getSqlMapProcessor(ctx).delete("SqlStmts.UserStatementdeleteListAttestationAuditWork", ctx);

		}
	}

	public void isComputerServiceSupplementExist(IContext ctx) throws Exception {

		if (ctx.get("AOP_Percentage_14") == null || "".equals(ctx.get("AOP_Percentage_14").toString().trim())
				|| Integer.parseInt(ctx.get("AOP_Percentage_14").toString().trim()) == 0) {

			ctx.put("OtherITServiceComment", "");

			SqlResources.getSqlMapProcessor(ctx).update("Firm.update", ctx);
			SqlResources.getSqlMapProcessor(ctx).delete("SqlStmts.UserStatementdeleteListITServicesSupp", ctx);

		}

	}

	public void isBusinessSupplementExist(IContext ctx) throws Exception {

		String IsControlClientFunds = "";
		Object obj = SqlResources.getSqlMapProcessor(ctx).findByKey("Firm.findByKey", ctx);
		if (obj != null) {
			Map map = (Map) obj;
			if (map.get("IsControlClientFunds") != null && !"".equals(map.get("IsControlClientFunds").toString()))
				IsControlClientFunds = map.get("IsControlClientFunds").toString();
		}

		if ((ctx.get("AOP_Percentage_18") == null || "".equals(ctx.get("AOP_Percentage_18").toString().trim())
				|| Integer.parseInt(ctx.get("AOP_Percentage_18").toString().trim()) == 0)
				&& ("".equals(IsControlClientFunds) || "N".equals(IsControlClientFunds))) {
			ctx.put("TotalAmountOfClientFundsFc", "");
			ctx.put("IsCountersignatureRequiredFc", "");
			ctx.put("IsBankAccountReconciledFc", "");
			ctx.put("IsFirmPersonnelBusinessManagerFc", "");
			ctx.put("NumberOfClientsFc", "");
			ctx.put("NumberOfFirmEmployeesFc", "");
			ctx.put("IsEmployeeDishonestyCoverageFc", "");
			ctx.put("TotlaNumberOfClientsFc", "");
			ctx.put("IsControlFundsForBusinessAndEntimentFc", "");
			ctx.put("ProvideNumberOfClientFc", "");
			ctx.put("ClientIndustryFc", "");
			ctx.put("ClientIndustryFcOthers", "");
			ctx.put("HaveInvestmentAuthorityFC", "");

			SqlResources.getSqlMapProcessor(ctx).update("FundsControlledSupp.update", ctx);
		}
	}

	public void processSearchList(IContext ctx) throws Exception {

		Map map = new HashMap();
		map.put("paginationaction", "setrecordcount");
		map.put("pagenumber", "1");
		map.put("recordsperpage", "20");
		map.put("listKey", "Search_firm_list_01");
		map.put("list", ctx.get("Search_firm_list_01"));
		map.put("datadisplayid", "ajax_sorting");

		ctx.put("paginationinfo", map);
		// new PaginationUtils().populatePaginatedList((Context) ctx, "");

	}

	public static void populatePolicyForm(IContext ctx) throws Exception {
		setFormChangesConditions((Context) ctx);
		Context newCtx = new Context();
		newCtx.putAll(ctx);

		newCtx.put("PolicyKey_form",
				ctx.get("PolicyKey") != null ? ctx.get("PolicyKey").toString() : ctx.get("PolicyKey"));
		newCtx.put("StateCode_form",
				ctx.get("StateCode") != null ? ctx.get("StateCode").toString() : ctx.get("StateCode"));
		newCtx.put("PolicyEffectiveDate_form",
				ctx.get("PolicyEffectiveDate") != null ? ctx.get("PolicyEffectiveDate").toString()
						: ctx.get("PolicyEffectiveDate"));
		newCtx.put("IsFinalisedOption_form", "N");
		// added for putting new form changes effective from 05/01/2019
		newCtx.put("isNewFormChanges", ctx.get("isNewFormChanges"));
		logger.debug("Going to execute the stored procedure for Form Attachment");
		SqlResources.getSqlMapProcessor(newCtx).select("Account.AccountantFormAttachment_p", newCtx);
		logger.debug("SP has been execute for the Form Attachment");

		/*
		 * List listForm = SqlResources.getSqlMapProcessor(ctx).select( "Form.findAll",
		 * ctx); List listStateForm = SqlResources.getSqlMapProcessor(ctx).select(
		 * "SqlStmts.UserStatementpdfquoteletterStateForm", ctx);
		 * 
		 * boolean dollarDefenseFlag = false; String IsClaimExpensesType = "N"; String
		 * IsClaimOptionType = "N"; Context newctx = null;
		 * 
		 * List quotesList = SqlResources.getSqlMapProcessor(ctx).select(
		 * "SqlStmts.UserStatementpdfquotelettergetQuoteOptedList", ctx);
		 * 
		 * boolean IsClaimExpensesTypeFlag = false; boolean IsClaimOptionTypeFlag =
		 * false;
		 * 
		 * if (quotesList != null) { for (int i = 0; i < quotesList.size(); i++) { Map
		 * map = (Map) quotesList.get(i); if (map.get("IsDollarDefense") != null &&
		 * "Y".equals(map.get("IsDollarDefense").toString())) // dollarDefenseFlag =
		 * true; dollarDefenseFlag = false; if (map.get("IsClaimExpensesType") != null
		 * && "Y" .equals(map.get("IsClaimExpensesType") .toString()))
		 * IsClaimExpensesTypeFlag = true; if (map.get("IsClaimOptionType") != null &&
		 * "Y".equals(map.get("IsClaimOptionType").toString())) IsClaimOptionTypeFlag =
		 * true; } }
		 * 
		 * if (IsClaimExpensesTypeFlag) IsClaimExpensesType = "Y"; if
		 * (IsClaimOptionTypeFlag) IsClaimOptionType = "Y";
		 * 
		 * if (listForm == null && listStateForm == null) return;
		 * 
		 * if ("MA".equals(ctx.get("StateCode").toString()) ||
		 * "OK".equals(ctx.get("StateCode").toString())) { if
		 * ("Y".equals(IsClaimExpensesType)) listForm.remove(2); else if
		 * ("N".equals(IsClaimExpensesType)) { listForm.remove(2); if (listStateForm !=
		 * null) {
		 * 
		 * if ("MA".equals(ctx.get("StateCode").toString()) && listStateForm.size() ==
		 * 3) listStateForm.remove(1); else if
		 * ("OK".equals(ctx.get("StateCode").toString()) && listStateForm.size() == 2)
		 * listStateForm.remove(1); }
		 * 
		 * }
		 * 
		 * if (dollarDefenseFlag) { if ("MA".equals(ctx.get("StateCode").toString()))
		 * listForm.remove(1); } else { if ("MA".equals(ctx.get("StateCode").toString())
		 * || "OK".equals(ctx.get("StateCode").toString())) listForm.remove(1); if
		 * ("MA".equals(ctx.get("StateCode").toString()) && listStateForm.size() >= 2)
		 * listStateForm.remove(0);
		 * 
		 * } } else { if ("N".equals(IsClaimExpensesType)) listForm.remove(2);
		 * 
		 * if (!dollarDefenseFlag) listForm.remove(1); }
		 * 
		 * if ("MA".equals(ctx.get("StateCode").toString())) {
		 * 
		 * if ("Y".equals(IsClaimOptionType) && listForm.size() == 9)
		 * listForm.remove(3); else if ("Y".equals(IsClaimOptionType) && listForm.size()
		 * == 8) listForm.remove(2); else if ("Y".equals(IsClaimOptionType) &&
		 * listForm.size() == 7) listForm.remove(1); else if
		 * ("N".equals(IsClaimOptionType)) {
		 * 
		 * if (listForm.size() == 9) listForm.remove(3); else if (listForm.size() == 8)
		 * listForm.remove(2); else if (listForm.size() == 7) listForm.remove(1);
		 * 
		 * if (listStateForm != null) { if ("MA".equals(ctx.get("StateCode").toString())
		 * && listStateForm.size() == 3) listStateForm.remove(2); else if
		 * ("MA".equals(ctx.get("StateCode").toString()) && listStateForm.size() == 2)
		 * listStateForm.remove(1); else if
		 * ("MA".equals(ctx.get("StateCode").toString()) && listStateForm.size() == 1)
		 * listStateForm.remove(0);
		 * 
		 * } } } else { if ("N".equals(IsClaimOptionType)) {
		 * 
		 * if (listForm.size() == 9) listForm.remove(3); else if (listForm.size() == 8)
		 * listForm.remove(2); else if (listForm.size() == 7) listForm.remove(1); } }
		 * 
		 * if ("PA".equals(ctx.get("StateCode").toString())) { // if
		 * ("Y".equals(IsClaimExpensesType)) { // listStateForm.remove(2); //
		 * listStateForm.remove(1); // } boolean IsClaimWithinLimit =
		 * checkForAnyQuoteWithInsideLimit(ctx); if (!IsClaimWithinLimit) {
		 * listStateForm.remove(2); listStateForm.remove(1); } }
		 * 
		 * if ("KY".equals(ctx.get("StateCode").toString())) {
		 * 
		 * boolean IsTaxablePremium = checkForKYMuncipalityTax(ctx); if
		 * (!IsTaxablePremium) listStateForm.remove(1); }
		 * 
		 * if ("NM".equals(ctx.get("StateCode").toString())) { boolean
		 * IsClaimWithOutsideLimit = checkForAnyQuoteWithOutsideLimit(ctx); if
		 * (!IsClaimWithOutsideLimit) { listStateForm.remove(0); }else
		 * if(IsClaimWithOutsideLimit){ listForm.remove(1); } }
		 * 
		 * if ("CT".equals(ctx.get("StateCode").toString())) {
		 * 
		 * if (IsClaimOptionType.equals("Y")) { if (listForm.size() == 9)
		 * listForm.remove(3); else if (listForm.size() == 8) listForm.remove(2); else
		 * if (listForm.size() == 7) listForm.remove(1); else if (listForm.size() == 6)
		 * listForm.remove(3); } if (!IsClaimOptionType.equals("Y")) {
		 * listStateForm.remove(1); }
		 * 
		 * }
		 * 
		 * SqlResources.getSqlMapProcessor(ctx).delete(
		 * "SqlStmts.UserStatementpdfquoteletterdeletePolicyForm", ctx);
		 * 
		 * for (int i = 0; i < listForm.size(); i++) { Map map = (Map) listForm.get(i);
		 * newctx = new Context(); newctx.putAll(map);
		 * newctx.setProject(ctx.getProject()); newctx.put("PolicyKey",
		 * ctx.get("PolicyKey")); newctx.put("StateCode", ctx.get("StateCode"));
		 * newctx.put("LastUpdateUserID", ctx.get("LastUpdateUserID"));
		 * newctx.put("FormType", "C"); populateLastUpdateTimeStamp(newctx);
		 * 
		 * DBUtils.executeDBOperation(newctx, "PolicyForm", "1"); }
		 * 
		 * if (listStateForm == null) return;
		 * 
		 * for (int i = 0; i < listStateForm.size(); i++) { Map map = (Map)
		 * listStateForm.get(i); newctx = new Context(); newctx.putAll(map);
		 * newctx.setProject(ctx.getProject()); newctx.put("PolicyKey",
		 * ctx.get("PolicyKey")); newctx.put("StateCode", ctx.get("StateCode"));
		 * newctx.put("LastUpdateUserID", ctx.get("LastUpdateUserID"));
		 * 
		 * newctx.put("FormType", map.get("StateFormType"));
		 * populateLastUpdateTimeStamp(newctx);
		 * 
		 * DBUtils.executeDBOperation(newctx, "PolicyForm", "1"); }
		 * 
		 * newctx.remove("FormType");
		 */
	}

	public static boolean validateFileName(Context ctx, String file) throws Exception {
		boolean flag = true;
		String filePath = null;

		if (ctx.get(file) != null)
			filePath = (String) ctx.get(file);

		if (filePath == null)
			return false;

		int index = filePath.lastIndexOf("\\");
		if (index <= 0)
			return false;

		String fileName = filePath.substring(index + 1, filePath.length() - 1);
		if (fileName.indexOf(".") <= 0)
			return false;

		int indexExt = filePath.lastIndexOf(".");
		String ext = fileName.substring(indexExt + 1, fileName.length() - 1);

		if (ext.length() < 3)
			return false;

		return true;
	}

	public static boolean validateQuickQuote(Context ctx) throws Exception {
		if (ctx.get("IsQuickQuote") != null && "Y".equals(ctx.get("IsQuickQuote").toString())
				&& ctx.get("IsFullQuote") != null && "N".equals(ctx.get("IsFullQuote").toString())) {
			// checkFirmWithEmailExist(ctx);
			validateHistory(ctx);
			validateAOPPercentage(ctx);
			validateAOPCommentQuickQuote(ctx);
			validateQuickQuotePhoneNumber(ctx);
			validateQuickQuoteDateField(ctx);
		}

		return true;
	}

	public static void validateAOPCommentQuickQuote(IContext ctx) throws Exception {

		Map map = new HashMap();
		List limtTypes = SqlResources.getSqlMapProcessor(ctx).select("AOP.findAll", ctx);

		if (limtTypes != null) {
			for (int i = 0; i < limtTypes.size(); i++) {
				map = (HashMap) limtTypes.get(i);
				if (map.get("AOPKey").toString().equals("20")) {
					String percentageValue = ctx.get("AOP_Percentage_" + map.get("AOPKey").toString()) == null ? null
							: ctx.get("AOP_Percentage_" + map.get("AOPKey").toString()).toString();
					String comment = ctx.get("AOPCommentDesc_" + map.get("AOPKey").toString()) == null ? null
							: ctx.get("AOPCommentDesc_" + map.get("AOPKey").toString()).toString();

					if (!(percentageValue == null || "".equals(percentageValue.trim())
							|| "0".equals(percentageValue))) {
						if (comment == null || "".equals(comment.trim())) {
							String msg = "Please describe the other services.";
							populateError(ctx, "AOPCommentDesc_" + map.get("AOPKey").toString(), msg);
						}
					} else {
						if (comment != null && !"".equals(comment)) {
							ctx.put("AOPCommentDesc_" + map.get("AOPKey").toString(), "");
						}

					}
				}
			}
		}
	}

	public static void validateHistory(IContext ctx) throws Exception {

		if (ctx.get("FirmYear") != null && "".equals(ctx.get("FirmYear").toString()))
			populateError(ctx, "FirmYear", "Please select one option.");

		/*
		 * if (ctx.get("IsClaimsInLast5Year") == null) populateError(ctx,
		 * "IsClaimsInLast5Year", "Please indicate yes or no.");
		 */

		Object obj0 = removeAmountFormat(ctx.get("Amount"));
		if (obj0 == null)
			populateError(ctx, "Amount", "Last year revenue is required.");
		else if (obj0 != null && ("".equals(obj0.toString().trim()) // || "0".equals(obj0.toString().trim())
		))
			populateError(ctx, "Amount", "Last year revenue is required.");

	}

	public static void validateQuickQuoteDateField(IContext ctx) throws Exception {
		String strDate = null;
		if (ctx.get("PolicyEffectiveDate") != null && !"".equals(ctx.get("PolicyEffectiveDate").toString())) {
			strDate = ctx.get("PolicyEffectiveDate").toString();
			validateLeapYearDate(ctx, "PolicyEffectiveDate", strDate);
		}
	}

	public void getAverageRevenueQuickQuote(IContext ctx) throws Exception {

		int count = 0;
		long total = 0;
		long average = 0;

		Object obj0 = removeAmountFormat(ctx.get("Amount"));
		if (obj0 != null && !"".equals(obj0) && !"0".equals(obj0)) {
			count = count + 1;
			total = total + Long.parseLong(obj0.toString());
		}

		if (count != 0 && total != 0) {
			average = total / count;
		}

		ctx.put("AverageRevFromAjax", average);
	}

	public void populateAuditComment(IContext ctx) throws Exception {

		if (ctx.get("attestation_freeform_02") != null) {
			ctx.put("AuditComment", ((Map) ctx.get("attestation_freeform_02")).get("AuditComment"));
		}
	}

	public void isRegistrationSaved(IContext ctx) throws Exception {
		Object obj = ctx.get("Policy_Info");
		if (obj != null && ((List) obj).size() != 0 && ((List) obj).size() == 1) {
			Map map = (Map) ((List) obj).get(0);
			ctx.putAll(map);
		}

	}

	public static String getEmailID(IContext ctx) throws Exception {
		String emailID = "";
		String productionEnv = "N";
		try {
			productionEnv = SystemProperties.getInstance()
					.getString("appl." + ctx.getProject() + ".environment.production");
		} catch (Exception e) {
			logger.error("Error in AccountUtils.getEmailID", e);
			throw e;
		}

		if ("N".equals(productionEnv))
			emailID = SystemProperties.getInstance().getString("appl." + ctx.getProject() + ".admin.email");
		else if (ctx.get("AccountEmail") != null)
			emailID = ctx.get("AccountEmail").toString();

		return emailID;

	}

	public static String getIssueEmailID(IContext ctx) throws Exception {

		String emailID = "";
		String productionEnv = "Y";
		String policyIssueEmailAdmin = "N";

		try {
			productionEnv = SystemProperties.getInstance()
					.getString("appl." + ctx.getProject() + ".environment.production");
			policyIssueEmailAdmin = SystemProperties.getInstance().getString("policy.issue.admin.email");
		} catch (Exception e) {
			logger.error("Error in AccountUtils.getIssueEmailID", e);
			throw e;
		}

		// productionEnv="Y";
		if ("N".equals(productionEnv)) {
			emailID = SystemProperties.getInstance().getString("appl." + ctx.getProject() + ".admin.email");
		} else {
			boolean flag = true;
			flag = isMailSendToInsuredAdmin(ctx);
			if (flag == true) {
				policyIssueEmailAdmin = "Y";
			} else {
				policyIssueEmailAdmin = "N";
			}

			if ("Y".equals(policyIssueEmailAdmin))
				emailID = SystemProperties.getInstance().getString("appl." + ctx.getProject() + ".admin.email");
			else if (ctx.get("AccountEmail") != null)
				emailID = ctx.get("AccountEmail").toString();
		}

		return emailID;

	}

	public static boolean isMailSendToInsuredAdmin(IContext ctx) throws Exception {
		Object objIssueMailAcc = SqlResources.getSqlMapProcessor(ctx)
				.select("SqlStmts.UserStatementsaveAccountstmtsgetIssueMailAcc", ctx);
		Object objAddress = SqlResources.getSqlMapProcessor(ctx).findByKey("SqlStmts.BasicInfoviewgetAddressdetails",
				ctx);

		List listIssueMailAcc = null;
		Map mapAddress = null;

		if (objIssueMailAcc != null)
			listIssueMailAcc = (List) objIssueMailAcc;

		if (objAddress != null)
			mapAddress = (Map) objAddress;

		if (listIssueMailAcc == null || objAddress == null)
			return false;

		String StateCode = "";
		String issueStateCode = "";

		for (int i = 0; i < listIssueMailAcc.size(); i++) {
			Map mapIssueMail = (Map) listIssueMailAcc.get(i);
			issueStateCode = mapIssueMail.get("StateCode").toString();
			StateCode = mapAddress.get("StateCode").toString();
			if (issueStateCode.equals(StateCode)) {
				return true;
			}
		}

		return false;

	}

	public void sendWorkQMail(IContext ctx) throws Exception {
		String bccAddress = "";
		String emailID = getEmailID(ctx);
		if ("".equals(emailID))
			return;
		bccAddress = SystemProperties.getInstance().getString("appl." + ctx.getProject() + ".admin.cc.email");
		logger.debug("Mail is going sendWorkQMail---- ");
		MailSender mail = new MailSender();
		mail.setToAdd(emailID);
		mail.setBccAdd(bccAddress);
		mail.setSubject("Thank You from Protexure");
		mail.setContentType("text/html");
		mail.setBody(generateWorkQMailBody(ctx));
		mail.sendMail((Context) ctx);
		logger.debug("Mail has sent- sendWorkQMail--- ");

	}

	private String generateWorkQMailBody(IContext ctx) throws Exception {
		String msg = "";

		msg = msg + "<table>";
		msg = msg + "<tr>";
		msg = msg + "<td>";

		msg = msg + "Dear (" + ctx.get("AccountName").toString() + ")," + "<br/><br/>";
		msg = msg + "Thank you for contacting Protexure. " + "<a href=" + getProjectUrl(ctx) + "/login.jsp?PolicyKey="
				+ ctx.get("PolicyKey").toString() + ">Click here</a>"
				+ " for the registration link to set up your account with us." + "<br/><br/> ";

		// msg = msg + "Thank you for contacting Protexure. " + "<a href="
		// + getProjectUrl(ctx) + "/login.jsp?AccountEmail="
		// + ctx.get("AccountEmail").toString() + ">Click here</a>"
		// + " for the registration link to set up your account with us."
		// + "<br/><br/> ";

		// msg = msg + " done so, please " + "<a href=" + getProjectUrl(ctx) +
		// "/login.jsp?AccountEmail="+ctx.get("AccountEmail").toString() +
		// ">click this link</a>" + " to register and receive a secure id and
		// password. " + "If you have already registered please " + "<a href=" +
		// getProjectUrl(ctx) +
		// "/accountrep.jsp?AccountEmail="+ctx.get("AccountEmail").toString() +
		// ">click here </a>" + " to review your estimate." + "<br/><br/>";

		msg = msg + "If you have any questions please contact us at 888-803-9898. " + "<br/><br/>";
		msg = msg + "Sincerely," + "<br/><br/>";
		msg = msg + "<b>The Protexure Accountants Team</b>" + "<br/>";

		msg = msg + "</td>";
		msg = msg + "</tr>";
		msg = msg + "</table>";

		return msg;
	}

	public void sendLoginMail(IContext ctx) throws Exception {
		String emailID = getEmailID(ctx);
		if ("".equals(emailID))
			return;

		ctx.put("LastUpdateTimeStamp", new Timestamp(new Date().getTime()).toString());
		ctx.put("UploadedTime", ctx.get("LastUpdateTimeStamp"));
		List list = SqlResources.getSqlMapProcessor(ctx).select("SqlStmts.UserStatementdeletegetPolicyQuote", ctx);

		ArrayList<String> attachments = new ArrayList<String>();
		logger.debug("QuickQuote Mail is going---- ");
		String htmlDir = SystemProperties.getInstance().getString("html.basedir");
		
		if(list != null && list.size() == 1)
		{
			OutputStream out = null;			
			try
			{
				// PDF Writer
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
					
			}
			catch(Exception e)
			{
				logger.error("Quick quote option could not be uploaded", e);
			}	
		}

		
		String outFileQuickQuote = htmlDir + "data//RiskManagementAnalysis_" + ctx.get("QuoteNumber").toString() + ".pdf";

		String foFile = SystemProperties.getInstance().getString("xsl.filepath") + "quickquote.xsl";
		setQuickQuoteImagesPathToTemplate(ctx);
		new XML2PDF().convertPOToPDF(foFile, new StringBuffer(new DownloadForm().generateDataXml((Context) ctx)), outFileQuickQuote);

		attachments.add(outFileQuickQuote);

		MailSender mail = new MailSender();
		mail.setToAdd(emailID);
		mail.setSubject("Thank you from Protexure");
		mail.setIsSentToCC("Y");
		mail.setContentType("text/html");
		if ("QUICKQUOTE".equals(ctx.get("QQRMLink"))) {
			mail.setBody(generateLinkBodyQQ(ctx));
		} else {
			mail.setBody(generateLinkBodyRM(ctx));
		}
		mail.setAttachments(attachments);
		mail.sendMail((Context) ctx);
		
		for (int i=0; i<attachments.size(); i++)
		{
			// File Delete
			File file = new File(attachments.get(i).toString());
			if(file.exists())
				file.delete();
		}
		
		logger.debug("Mail has sent---- ");

		logger.debug("Quote option mail is going---- ");
		// new QuoteLetter().generateQuickQuoteListLetter(ctx);

		logger.debug("Quote option mail has sent---- ");

	}

	public String generateLinkBodyQQ(IContext ctx) throws Exception {
		String msg = "";

		msg = msg + "<table>";
		msg = msg + "<tr>";
		msg = msg + "<td>";

		msg = msg + "Dear " + ctx.get("AccountName").toString() + ",";

		msg += "<br/><br/>Thank you for completing our quick quote. We appreciate your interest in Protexure Accountants.";

		msg += "<br/><br/>For a Limit of liability of :"+ctx.get("LimitAmount");
		msg += "<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
				+ " Deductible :"+ctx.get("DeductibleAmount");
		
//		if (ctx.get("PremiumRange") == null || "0".equals(ctx.get("PremiumRange").toString())
//				|| "Washington".equals(ctx.get("StateDesc")) || "Nebraska".equals(ctx.get("StateDesc"))) {
		if( "Y".equals(ctx.get("IsMinimumPremium").toString() )){
			msg += "<br/><br/><h2><center>Your Premium is :"+ctx.get("TotalPremium")+"</h2></center>";
		} else {
			msg += "<br/><br/><h2><center>Your Premium Estimate is :"+ctx.get("EstimatePremiumRange")+"</h2></center>";
		}
		
		msg += "<br/><br/>To review your information or to continue on to our quick and easy full application please click below:";
		
		msg += "<br><br><a href=\"" + getProjectUrl(ctx) + "/qqlogin.jsp?PolicyKey=" + ctx.get("PolicyKey").toString()
				+ "&AccountKey=" + ctx.get("AccountKey").toString() + "&TransactionSequence="
				+ ctx.get("TransactionSequence").toString() + "&VersionSequence="
				+ ctx.get("VersionSequence").toString() + "&CoverageSequence=" + ctx.get("CoverageSequence").toString()
				+ "&CompanyKey=" + ctx.get("CompanyKey").toString() + "\">Continue to your Full Online Application</a>";
		
		msg += "<br/><br/>At Protexure Accountants we are committed to helping firms like yours manage their professional liability risk. Based on the specific area(s) of practice you selected, we have created a risk management assessment. Attached below you will find a personalized risk assessment complete with claims scenarios and best practices to help you minimize your exposure.";

		msg += "<br/><br/>If you have any questions please contact one of our experienced professional liability specialists at 888-803-9898.";
		msg += "<br/><br/>Sincerely,";
		msg += "<br/><br/><b>The Protexure Accountants Team</b>" + "<br/>";

		msg = msg + "</td>";
		msg = msg + "</tr>";
		msg = msg + "</table>";

		return msg;
	}

	public String generateLinkBodyRM(IContext ctx) throws Exception {
		String msg = "";

		msg = msg + "<table>";
		msg = msg + "<tr>";
		msg = msg + "<td>";

		msg = msg + "Dear " + ctx.get("AccountName").toString() + ",";

		msg += "<br/><br/>Thank you for your interest in our Risk Management Assessment. Attached below is your personalized risk assessment complete with claims scenarios and best practices to help you protect your firm.";
		msg += "<br/><br/>Even if you have all the right risk management measures in place, mistakes still happen. One way to manage risk is with accountant's professional liability insurance. Based on the information that you shared with us, we would like to provide you with a premium indication.";

		msg += "<br/><br/>For a Limit of liability of :"+ctx.get("LimitAmount");
		msg += "<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
				+ " Deductible :"+ctx.get("DeductibleAmount");
		
//		if (ctx.get("PremiumRange") == null || "0".equals(ctx.get("PremiumRange").toString())
//				|| "Washington".equals(ctx.get("StateDesc")) || "Nebraska".equals(ctx.get("StateDesc"))) {
		if( "Y".equals(ctx.get("IsMinimumPremium").toString() )){
			msg += "<br/><br/><h2><center>Your Premium is :"+ctx.get("TotalPremium")+"</h2></center>";
		} else {
			msg += "<br/><br/><h2><center>Your Premium Estimate is :"+ctx.get("EstimatePremiumRange")+"</h2></center>";
		}
		
		msg += "<br/><br/>We appreciate your interest in Protexure Accountants. To review your information or to continue on to our quick and easy full application please click below:";
		msg += "<br><br><a href=\"" + getProjectUrl(ctx) + "/qqlogin.jsp?PolicyKey=" + ctx.get("PolicyKey").toString()
				+ "&AccountKey=" + ctx.get("AccountKey").toString() + "&TransactionSequence="
				+ ctx.get("TransactionSequence").toString() + "&VersionSequence="
				+ ctx.get("VersionSequence").toString() + "&CoverageSequence=" + ctx.get("CoverageSequence").toString()
				+ "&CompanyKey=" + ctx.get("CompanyKey").toString() + "\">Continue to your Full Online Application</a>";

		msg += "<br/><br/>If you have any questions please contact one of our experienced professional liability specialists at 888-803-9898.";
		msg += "<br/><br/>Sincerely,";
		msg += "<br/><br/><b>The Protexure Accountants Team</b>" + "<br/>";

		msg = msg + "</td>";
		msg = msg + "</tr>";
		msg = msg + "</table>";

		return msg;
	}

	public String generateLinkBody(IContext ctx) throws Exception {
		String msg = "";

		msg = msg + "<table>";
		msg = msg + "<tr>";
		msg = msg + "<td>";

		msg = msg + "Dear (" + ctx.get("AccountName").toString() + ")," + "<br/><br/>";
		msg = msg
				+ "Thank you for completing our premium estimate. We appreciate your interest in Protexure Accountants. We at Protexure Accountants strive to provide the highest levels of security for your information. To review your information or to continue on to our quick and easy full application, if you have not";
		msg = msg + " done so, please " + "<a href=" + getProjectUrl(ctx) + "/login.jsp?PolicyKey="
				+ ctx.get("PolicyKey").toString() + ">click this link</a>"
				+ " to register and receive a secure id and password. " + "If you have already registered please "
				+ "<a href=" + getProjectUrl(ctx) + "/accountrep.jsp?PolicyKey=" + ctx.get("PolicyKey").toString()
				+ ">click here </a>" + " to review your estimate." + "<br/><br/>";

		// msg = msg + " done so, please " + "<a href=" + getProjectUrl(ctx) +
		// "/login.jsp?AccountEmail="+ctx.get("AccountEmail").toString() +
		// ">click this link</a>" + " to register and receive a secure id and
		// password. " + "If you have already registered please " + "<a href=" +
		// getProjectUrl(ctx) +
		// "/accountrep.jsp?AccountEmail="+ctx.get("AccountEmail").toString() +
		// ">click here </a>" + " to review your estimate." + "<br/><br/>";
		// msg = msg + "<a href=" + getProjectUrl(ctx) +
		// "/accountrep.jsp?AccountEmail="+ctx.get("AccountEmail").toString() +
		// ">click this link</a>" + "<br/>";
		// msg = msg + "If you have already registered please " + "<a href=" +
		// getProjectUrl(ctx) +
		// "/accountrep.jsp?AccountEmail="+ctx.get("AccountEmail").toString() +
		// ">click here </a>" + " to review your estimate." + "<br/>";
		// msg = msg + getProjectUrl(ctx) +
		// "/accountrep.jsp?AccountEmail="+ctx.get("AccountEmail").toString() +
		// "<br/><br/>";

		msg = msg
				+ "If you have any questions please contact one of our experienced professional liability specialists at 888-803-9898."
				+ "<br/><br/>";
		msg = msg + "Sincerely," + "<br/><br/>";
		msg = msg + "<b>The Protexure Accountants Team</b>" + "<br/>";

		msg = msg + "</td>";
		msg = msg + "</tr>";
		msg = msg + "</table>";

		return msg;

	}

	public void sendRetreivalMail(IContext ctx) throws Exception {
		String emailID = getEmailID(ctx);
		if ("".equals(emailID))
			return;
		String bccAddress = "";
		bccAddress = SystemProperties.getInstance().getString("appl." + ctx.getProject() + ".admin.cc.email");

		logger.debug("send Retreival Mail has been sending");
		MailSender mail = new MailSender();
		mail.setToAdd(emailID);
		mail.setBccAdd(bccAddress);
		mail.setSubject("Application retrieval link");
		mail.setContentType("text/html");
		mail.setBody(generateRetreivalBody(ctx));
		mail.sendMail((Context) ctx);
		logger.debug("send Retreival Mail has been sent");

	}

	private String generateRetreivalBody(IContext ctx) throws Exception {
		String url = "You have successfully registered with us. Please click on below given link to retrive your application :-";
		url = url + "                                                          ";
		url = url + getProjectUrl(ctx);

		if (url != null)
			url = url + "/accountrep.jsp?AccountEmail=" + ctx.get("AccountEmail").toString();
		else
			url = "";

		return url;
	}

	public void sendAppChangeMail(IContext ctx) throws Exception {
		String emailID = getFirstReviewerAgentEmailID(ctx);

		if ("".equals(emailID))
			return;

		String bccAddress = "";
		bccAddress = SystemProperties.getInstance().getString("mail.admin.cc.address");

		logger.debug("Mail is going for sendAppChangeMail--- ");
		MailSender mail = new MailSender();
		mail.setContentType("text/html");
		mail.setToAdd(emailID);
		mail.setBccAdd(bccAddress);

		mail.setSubject(ctx.get("AccountName").toString() + "-" + ctx.get("QuoteNumber").toString());
		mail.setBody(generateAppChangeBody(ctx));

		mail.sendMail((Context) ctx);
		logger.debug("Mail has sent-for sendAppChangeMail--- ");

	}

	private String generateAppChangeBody(IContext ctx) throws Exception {
		String msg = "";

		msg = msg + "<table>";
		msg = msg + "<tr>";
		msg = msg + "<td>";

		msg = msg + "This firm has requested assistance with their application. Please contact them ASAP.";

		msg = msg + "</td>";
		msg = msg + "</tr>";
		msg = msg + "</table>";

		return msg;

	}

	public static String getFirstReviewerAgentEmailID(IContext ctx) throws Exception {
		String emailID = "";
		try {
			List getFirstReviewerEmail = (List) SqlResources.getSqlMapProcessor(ctx).select("SqlStmts.UserStatementManualBoQueriesgetZohoFirstReviewer", ctx);
			if (getFirstReviewerEmail != null && getFirstReviewerEmail.size() > 0 && !getFirstReviewerEmail.isEmpty()) {
				Map getFirstReviewerList = (Map) getFirstReviewerEmail.get(0);
				emailID = (getFirstReviewerList.get("AgentEmail") == null ? "" : getFirstReviewerList.get("AgentEmail").toString().trim());
			}

			if(emailID != "") {
	    		String productionEnv = "N";
				try {
					productionEnv = SystemProperties.getInstance().getString("appl." + ctx.getProject() + ".environment.production");
				} catch (Exception e) {
					logger.error("error in geeting production environment", e);
				}
				if ("N".equals(productionEnv))
					emailID = SystemProperties.getInstance().getString("appl." + ctx.getProject() + ".admin.cc.email");
    		}
		} catch (Exception e) {
			logger.error("Error in AccountUtils.getFirstReviewerAgentEmailID", e);
			throw e;
		}

		return emailID;
	}

	private String getProjectUrl(IContext ctx) throws Exception {
		String resourcePath = null;
		resourcePath = SystemProperties.getInstance().getString("appl." + ctx.getProject() + ".url");

		return resourcePath;

	}

	public static void checkFirmWithEmailExist(IContext ctx) throws Exception {

		boolean isFirmExist = false;
		boolean flag = false;

		if (ctx.get("AccountEmail") != null) {
			// List listFirm =
			// SqlResources.getSqlMapProcessor(ctx).select("SqlStmts.indexagentlogingetAccountDetails",
			// ctx);
			List listFirm = SqlResources.getSqlMapProcessor(ctx)
					.select("SqlStmts.UserStatementsaveAccountstmtsgetAccountEmailDetails", ctx);

			if (listFirm != null && listFirm.size() > 0)
				isFirmExist = true;

			List listFirmEmail = null;
			if (isFirmExist)
				listFirmEmail = SqlResources.getSqlMapProcessor(ctx).select("Account.getExistingPolicy", ctx);

			if (listFirmEmail != null && listFirmEmail.size() > 0) {

				ctx.put("SubSearch_firm_list_01", listFirmEmail);
				ctx.put("showSubList", "Y");
				populateError(ctx, "AccountEmail",
						"This user already exists, please contact 1-888-803-9898 for any assistance");
			}

		}
	}

	public static boolean validateUserDetail(Context ctx) throws Exception {
		if (ctx.get("AccountEmail") == null)
			populateError(ctx, "AccountEmail", "Email ID is required.");
		else if (ctx.get("AccountEmail") != null && "".equals(ctx.get("AccountEmail").toString()))
			populateError(ctx, "AccountEmail", "Email ID is required.");

		if (ctx.get("PassWord") == null)
			populateError(ctx, "PassWord", "Password is required.");
		else if (ctx.get("PassWord") != null && "".equals(ctx.get("PassWord").toString().trim()))
			populateError(ctx, "PassWord", "Password is required.");

		if (ctx.get("WorkPhone") == null)
			populateError(ctx, "WorkPhone", "Phone is required.");
		else if (ctx.get("WorkPhone") != null && "".equals(ctx.get("WorkPhone").toString()))
			populateError(ctx, "WorkPhone", "Phone is required.");

		if (ctx.get("firstname") == null)
			populateError(ctx, "firstname", "First name is required.");
		else if (ctx.get("firstname") != null && "".equals(ctx.get("firstname").toString()))
			populateError(ctx, "firstname", "First name is required.");

		if (ctx.get("lastname") == null)
			populateError(ctx, "lastname", "Last name is a required.");
		else if (ctx.get("lastname") != null && "".equals(ctx.get("lastname").toString()))
			populateError(ctx, "lastname", "Last name is required.");

		if (ctx.get("ChallengeQuesID") != null && "".equals(ctx.get("ChallengeQuesID").toString()))
			populateError(ctx, "ChallengeQuesID", "First Question is required.");

		if (ctx.get("ChallengeAns1") == null)
			populateError(ctx, "ChallengeAns1", "Answer for first question is required.");
		else if (ctx.get("ChallengeAns1") != null && "".equals(ctx.get("ChallengeAns1").toString()))
			populateError(ctx, "ChallengeAns1", "Answer for first question is required.");

		if (ctx.get("PassWord") != null && !"".equals(ctx.get("PassWord").toString().trim())) {
			Object obj = RuleUtils.executeRule(ctx, "AccountantRule.validatePassword");
			if (obj != null && obj instanceof Boolean) {
				boolean flag = (Boolean) obj;
				if (!flag)
					populateError(ctx, "PassWord", "Password is not in correct format.");
			}
		}

		if (ctx.get("IsReceiveNewsLetter") == null)
			ctx.put("IsReceiveNewsLetter", "N");
		else if (ctx.get("IsReceiveNewsLetter") != null && "".equals(ctx.get("IsReceiveNewsLetter").toString()))
			ctx.put("IsReceiveNewsLetter", "N");

		// if(ctx.get("ChallengeQues2") != null &&
		// "".equals(ctx.get("ChallengeQues2").toString()))
		// populateError(ctx, "ChallengeQues2", "Second Question is a
		// required.");
		//
		// if(ctx.get("ChallengeAns2") == null)
		// populateError(ctx, "ChallengeAns2", "Answer for second question is a
		// required.");
		// else if(ctx.get("ChallengeAns2") != null &&
		// "".equals(ctx.get("ChallengeAns2").toString()))
		// populateError(ctx, "ChallengeAns2", "Answer for second question is a
		// required.");
		//
		// if(ctx.get("ChallengeQues3") != null &&
		// "".equals(ctx.get("ChallengeQues3").toString()))
		// populateError(ctx, "ChallengeQues3", "Third Question is a
		// required.");
		//
		// if(ctx.get("ChallengeAns3") == null)
		// populateError(ctx, "ChallengeAns3", "Answer for third question is a
		// required.");
		// else if(ctx.get("ChallengeAns3") != null &&
		// "".equals(ctx.get("ChallengeAns3").toString()))
		// populateError(ctx, "ChallengeAns3", "Answer for third question is a
		// required.");

		return true;
	}

	public void processView(IContext ctx) throws Exception {
		if (ctx.get("AccountEmail") != null && "SPACE".equals(ctx.get("AccountEmail").toString()))
			ctx.put("AccountEmail", "");

		if (ctx.get("StateCode") != null && "SPACE".equals(ctx.get("StateCode").toString()))
			ctx.put("StateCode", "");

		if (ctx.get("PolicyEffectiveDate") != null && "SPACE".equals(ctx.get("PolicyEffectiveDate").toString()))
			ctx.put("PolicyEffectiveDate", "");

		populateEnvironment(ctx);
	}

	public static void validateQuickQuotePhoneNumber(IContext ctx) throws Exception {

		if (ctx.get("AccountName") == null || "".equals(ctx.get("AccountName").toString())) {
			populateError(ctx, "AccountName", "Please enter name.");
		}
		if (ctx.get("AccountEmail") == null || "".equals(ctx.get("AccountEmail").toString())) {
			populateError(ctx, "AccountEmail", "Please enter email.");
		}
		if (ctx.get("StateCode") == null || "".equals(ctx.get("StateCode").toString())) {
			populateError(ctx, "StateCode", "Please select any state.");
		}
		if (ctx.get("WorkPhone") != null && !"".equals(ctx.get("WorkPhone").toString())) {
			if (!validatePhoneNumber(ctx.get("WorkPhone").toString().trim()))
				populateError(ctx, "WorkPhone", "Phone should be at least of 10 digit.");
		} else {
			populateError(ctx, "WorkPhone", "Please enter phone.");
		}
	}

	public void setFirmDetail(IContext ctx) throws Exception {
		Object obj = ctx.get("Firm_Info");

		if (obj != null && ((List) obj).size() != 0 && ((List) obj).size() == 1) {
			Map map = (Map) ((List) obj).get(0);
			ctx.putAll(map);
		} else if (obj != null && ((List) obj).size() == 0) {
			populateError(ctx, "AccountEmail",
					"Credentials do not match, please contact 1-888-803-9898 for any assistance.");
		} else if (obj != null && ((List) obj).size() > 1) {
			populateError(ctx, "AccountEmail", "Please contact 1-888-803-9898 for any assistance.");
		}
	}

	public void populateEnvironment(IContext ctx) throws Exception {
		try {

			/*
			 * try { if(ctx.get("PolicyEffectiveDate") != null &&
			 * !"".equals(ctx.get("PolicyEffectiveDate")) &&
			 * !"SPACE".equals(ctx.get("PolicyEffectiveDate")) &&
			 * (ctx.get("PolicyExpirationDate") == null ||
			 * "".equals(ctx.get("PolicyExpirationDate")))){ Calendar date =
			 * Calendar.getInstance(); Date date1 = new
			 * SimpleDateFormat("MM/dd/yyyy").parse(ctx.get("PolicyEffectiveDate").toString(
			 * )); date.setTime(date1); date.add(Calendar.YEAR,1); SimpleDateFormat f = new
			 * SimpleDateFormat("MM/dd/yyyy");
			 * ctx.put("PolicyExpirationDate",f.format(date.getTime())); } } catch(Exception
			 * e) { e.printStackTrace(); }
			 */

			if (ctx.get("AccountEmail") != null && "SPACE".equals(ctx.get("AccountEmail").toString()))
				ctx.put("AccountEmail", "");

			if (ctx.get("StateCode") != null && "SPACE".equals(ctx.get("StateCode").toString()))
				ctx.put("StateCode", "");

			if (ctx.get("PolicyEffectiveDate") != null && "SPACE".equals(ctx.get("PolicyEffectiveDate").toString()))
				ctx.put("PolicyEffectiveDate", "");

			if (ctx.get("ProducerCode") != null && "SPACE".equals(ctx.get("ProducerCode").toString()))
				ctx.put("ProducerCode", "");

			String environmentVar = null;
			environmentVar = SystemProperties.getInstance().getString("appl." + ctx.getProject() + ".environment");
			if (environmentVar == null)
				environmentVar = "QA";
			ctx.put("environmentVar", environmentVar);

			String isPaypalProduction = null;
			isPaypalProduction = SystemProperties.getInstance().getString("paypal.production");
			if (isPaypalProduction == null)
				isPaypalProduction = "N";
			ctx.put("isPaypalProduction", isPaypalProduction);

		} catch (Exception e) {
			logger.error("error in login " + e, e);
			throw e;
		}

	}

	public void checkAgentExist(IContext ctx) throws Exception {
		if (ctx.get("AccountEmail") == null)
			AccountUtils.populateError(ctx, "AccountEmail", "Email ID is required");
		else if (ctx.get("AccountEmail") != null && "".equals(ctx.get("AccountEmail").toString().trim()))
			AccountUtils.populateError(ctx, "AccountEmail", "Email ID is required");

		if (ctx.get("PassWord") == null)
			AccountUtils.populateError(ctx, "PassWord", "Password is required");
		else if (ctx.get("PassWord") != null && "".equals(ctx.get("PassWord").toString().trim()))
			AccountUtils.populateError(ctx, "PassWord", "Password is required");

		Object obj = ctx.get("userdetailsInfoList");
		if (obj != null) {
			List list = (List) obj;
			int count = 0;
			boolean emailMatched = false;
			boolean pwdMatched = false;

			for (int i = 0; i < list.size(); i++) {
				Map map = (Map) list.get(i);
				if (map.get("AgentEmail").toString().equalsIgnoreCase(ctx.get("AccountEmail").toString())) {
					ctx.put("AgentState", map.get("AgentState"));
					ctx.put("AgentEmail", map.get("AgentEmail"));
					ctx.put("LastUpdateUserID", map.get("UserNo"));
					ctx.put("UserNo", map.get("UserNo"));
					ctx.put("AgentName", map.get("AgentName"));
					ctx.put("LicenseNumber", map.get("LicenseNumber"));
					ctx.put("Org_LicenseNumber", map.get("LicenseNumber"));
					break;
				}
			}
		}
	}

	public void populateAgentDetail(IContext ctx) throws Exception {
		Object obj1 = RuleUtils.executeRule(ctx, "AccountantRule.isAgent");
		boolean flag = false;
		if (obj1 != null && obj1 instanceof Boolean)
			flag = (Boolean) obj1;

		if (flag) {
			Object obj = ctx.get("userdetailsInfoList");

			if (obj != null) {
				List list = (List) obj;
				int count = 0;
				boolean emailMatched = false;
				boolean pwdMatched = false;

				for (int i = 0; i < list.size(); i++) {
					Map map = (Map) list.get(i);
					if (map.get("AgentEmail").toString().equalsIgnoreCase(
							ctx.get("AccountEmail") != null ? ctx.get("AccountEmail").toString() : "")) {
						ctx.put("AgentState", map.get("AgentState"));
						ctx.put("AgentEmail", map.get("AgentEmail"));

						ctx.put("LastUpdateUserID", map.get("UserNo"));
						{ if (logger.isDebugEnabled()) logger.debug(map.get("UserNo")); }
						ctx.put("UserNo", map.get("UserNo"));
						ctx.put("AgentName", map.get("AgentName"));
						ctx.put("LicenseNumber", map.get("LicenseNumber"));
						ctx.put("Org_LicenseNumber", map.get("LicenseNumber"));
						break;
					}
				}
			}
		} else
			ctx.put("UserNo", ctx.get("FirstReviewUserID"));
	}

	public void isReferRuleTriggered(IContext ctx) throws Exception {

		String policyStatusKey = ctx.get("PolicyStatusKey") != null ? ctx.get("PolicyStatusKey").toString() : "";

		int PolicyKey = ctx.get("PolicyKey") != null && !ctx.get("PolicyKey").equals(HtmlConstants.EMPTY)
				? Integer.parseInt(ctx.get("PolicyKey").toString())
				: 0;
		;
		ctx.put("PolicyKey", PolicyKey);
		List detailsObj = (List) SqlResources.getSqlMapProcessor(ctx).select("Firm.GetDataForInsured_p", ctx);
		ctx.putAll((Map) detailsObj.get(0));

		if ("1".equals(policyStatusKey)) {
			boolean IsReferRuleTriggered = false;

			List revenueList = SqlResources.getSqlMapProcessor(ctx).select("SqlStmts.BasicInfoviewmomgrossrevenue",
					ctx);
			long average = 0;

			if (revenueList != null) {
				Map map = new HashMap();
				long total = 0;
				int count = 0;
				for (int i = 0; i < revenueList.size(); i++) {
					map = (HashMap) revenueList.get(i);
					Object objAmount = map.get("Amount");
					if (objAmount != null && !"".equals(objAmount.toString()) && !"0".equals(objAmount.toString())) {
						total = total + Long.parseLong(objAmount.toString());
						count = count + 1;
					}
				}
				if (count > 0) {
					average = total / count;
				}
			}

			if (average > 750000 || average < 25000)
				IsReferRuleTriggered = true;

			/*
			 * if(ctx.get("Address1")!=null) {
			 * 
			 * boolean containsPOBOX=false;
			 * 
			 * String address1 = ctx.get("Address1") != null ?
			 * ctx.get("Address1").toString().trim().toLowerCase() : "";
			 * 
			 * String
			 * poBoxNamePatterns="P.O. Box,p.o.box,P. o. Box,p. O. Box,p. o. box,p. O. box,P. o. box,PO Box,po Box,PO box,po box,PO. Box,po. box,PO. box,po.box,pobox"
			 * ; String[]
			 * poPatterns=RuleEngineUtility.getTokens(poBoxNamePatterns.toString().trim().
			 * toLowerCase(),",");
			 * 
			 * for(int i=0;i<poPatterns.length;i++) { if(address1.contains(poPatterns[i]))
			 * ||address2.contains(poPatterns[i])) { containsPOBOX=true; }
			 * 
			 * } if(containsPOBOX) IsReferRuleTriggered = true; }
			 */
			Object objAddress = SqlResources.getSqlMapProcessor(ctx)
					.findByKey("SqlStmts.BasicInfoviewgetAddressdetails", ctx);
			if (objAddress != null) {

				boolean containsPOBOX = false;
				Map mapAddress = null;
				if (objAddress != null)
					mapAddress = (Map) objAddress;

				String addressPOBox = "";
				String address1 = "";

				for (int i = 0; i < mapAddress.size(); i++) {
					Map mapIssueMail = (Map) mapAddress.get(i);
					address1 = mapAddress.get("Address1") != null
							? mapAddress.get("Address1").toString().trim().toLowerCase()
							: "";

				}

				/* New Changes Start */

				String sentence1 = address1.toString().replaceAll("[-,., ,,,]", "");
				String search = "pobox";

				if (sentence1.toLowerCase().indexOf(search.toLowerCase()) != -1) {

					containsPOBOX = true;

				}

				/* New changes ends */

				/* old start */
				/*
				 * String
				 * poBoxNamePatterns="P.O. Box,p.o.box,P. o. Box,p. O. Box,p. o. box,p. O. box,P. o. box,PO Box,po Box,PO box,po box,PO. Box,po. box,PO. box,po.box,pobox"
				 * ; String[]
				 * poPatterns=RuleEngineUtility.getTokens(poBoxNamePatterns.toString().trim().
				 * toLowerCase(),",");
				 * 
				 * for(int i=0;i<poPatterns.length;i++) { if(address1.contains(poPatterns[i]))
				 * ||address2.contains(poPatterns[i])) { containsPOBOX=true; }
				 * 
				 * }
				 */

				/* old ends */
				if (containsPOBOX)
					IsReferRuleTriggered = true;
			}

			Object firmObject = SqlResources.getSqlMapProcessor(ctx).findByKey("Firm.findByKey", ctx);
			if (firmObject != null && firmObject instanceof Map) {
				Map firmMap = (Map) firmObject;

				int totalPersonals = firmMap.get("TotalFirmPersonnel") != null
						? Integer.valueOf(firmMap.get("TotalFirmPersonnel").toString())
						: 0;
				if (totalPersonals > 10)
					IsReferRuleTriggered = true;

				if (firmMap.get("IsFirmRenderingServices") != null
						&& "Y".equals(firmMap.get("IsFirmRenderingServices").toString()))
					IsReferRuleTriggered = true;

				if (firmMap.get("IsFirmsRevenueMorethan25Percent") != null
						&& "Y".equals(firmMap.get("IsFirmsRevenueMorethan25Percent").toString()))
					IsReferRuleTriggered = true;

				if (firmMap.get("IsAnyFirmMeargedOrAcquired") != null
						&& "Y".equals(firmMap.get("IsAnyFirmMeargedOrAcquired").toString()))
					IsReferRuleTriggered = true;

				if (firmMap.get("IsControlClientFunds") != null
						&& "Y".equals(firmMap.get("IsControlClientFunds").toString()))
					IsReferRuleTriggered = true;

				if (firmMap.get("IsInvestmentVenture") != null
						&& "Y".equals(firmMap.get("IsInvestmentVenture").toString()))
					IsReferRuleTriggered = true;

				if (firmMap.get("IsSyndicationManagers") != null
						&& "Y".equals(firmMap.get("IsSyndicationManagers").toString()))
					IsReferRuleTriggered = true;

				if (firmMap.get("IsFirmRenderedAuditOrAttestService") != null
						&& "Y".equals(firmMap.get("IsFirmRenderedAuditOrAttestService").toString()))
					IsReferRuleTriggered = true;

				if (firmMap.get("IsManagementServiceProvided") != null
						&& "Y".equals(firmMap.get("IsManagementServiceProvided").toString()))
					IsReferRuleTriggered = true;

				if (firmMap.get("IsCommissionRecieved") != null
						&& "Y".equals(firmMap.get("IsCommissionRecieved").toString()))
					IsReferRuleTriggered = true;

				if (firmMap.get("IsDebtOrEquityFinancing") != null
						&& "Y".equals(firmMap.get("IsDebtOrEquityFinancing").toString()))
					IsReferRuleTriggered = true;

				if (firmMap.get("IsNonPublicInvestmentDone") != null
						&& "Y".equals(firmMap.get("IsNonPublicInvestmentDone").toString()))
					IsReferRuleTriggered = true;

				if (firmMap.get("IsFirmRenderedServiceForAnyClient") != null
						&& "Y".equals(firmMap.get("IsFirmRenderedServiceForAnyClient").toString()))
					IsReferRuleTriggered = true;

				if (firmMap.get("IsSuitInstituted") != null && "Y".equals(firmMap.get("IsSuitInstituted").toString())) {
					List firmInitLawsuits = SqlResources.getSqlMapProcessor(ctx)
							.select("SqlStmts.UserStatementdroolQueriesgetFirmInitLawsuits", ctx);
					// System.out.println("in suits"+firmInitLawsuits);
					int count = 0;
					int totalAmountfee = 0;
					long daysDifferenceForFeeSuits = 0;
					boolean flagFeeSuitMonth = true;
					// String totalAmountfee="";
					if (firmInitLawsuits != null) {

						for (int i = 0; i < firmInitLawsuits.size(); i++) {
							Map dataMap = (Map) firmInitLawsuits.get(i);
							String str = dataMap.get("SuitFilesDateFees").toString();
							String effectiveDatePolicy = "";
							if (ctx.get("PolicyEffectiveDate").toString().contains(":")) {
								effectiveDatePolicy = new SimpleDateFormat("MM/dd/yyyy")
										.format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S")
												.parse(ctx.get("PolicyEffectiveDate").toString()));
							} else {
								effectiveDatePolicy = new SimpleDateFormat("MM/dd/yyyy")
										.format(new Date(ctx.get("PolicyEffectiveDate").toString()));
							}
							String amountFee = dataMap.get("AmountOfFeesSued").toString();
							int amountFees = Integer.parseInt(amountFee);

							ctx.put("effectiveDateOfPolicy", effectiveDatePolicy);
							ctx.put("SuitFilesDateFeesFormat", new SimpleDateFormat("MM/dd/yyyy")
									.format(new Date(dataMap.get("SuitFilesDateFees").toString())));

							int feeSuedDate = Integer.valueOf(str.substring(str.length() - 4, str.length()));

							SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
							int currentYear = Integer.valueOf(sdf.format(new Date(effectiveDatePolicy)).toString());

							/*
							 * int currentYear=Integer.valueOf(sdf.format(new Date()).toString());
							 */ { if (logger.isDebugEnabled()) logger.debug(currentYear + " " + (currentYear - 1) + " " + (currentYear - 2) + " "
									+ (currentYear - 3)); }
							{ if (logger.isDebugEnabled()) logger.debug(feeSuedDate); }

							// daysDifferenceForFeeSuits=DateUtils.calculateDiffInDays(new
							// Date(ctx.get("effectiveDateOfPolicy").toString()),new
							// Date(ctx.get("SuitFilesDateFeesFormat").toString()));
							daysDifferenceForFeeSuits = DateUtils.calculateDiffInDays(
									new Date(ctx.get("SuitFilesDateFeesFormat").toString()),
									new Date(ctx.get("effectiveDateOfPolicy").toString()));

							if (feeSuedDate == (currentYear - 3)) {
								int monthFeeSuit = Integer.valueOf(str.substring(0, Math.min(str.length(), 2)));
								int monthEffectiveDatePolicy = Integer.valueOf(
										effectiveDatePolicy.substring(0, Math.min(effectiveDatePolicy.length(), 2)));
								{ if (logger.isDebugEnabled()) logger.debug("Fee Siut Month..." + monthFeeSuit + " Effective date Month.."
										+ monthEffectiveDatePolicy); }
								if (monthFeeSuit < monthEffectiveDatePolicy) {
									flagFeeSuitMonth = false;
								}

							}

							if ((feeSuedDate == (currentYear - 1) || feeSuedDate == (currentYear - 2)
									|| feeSuedDate == (currentYear - 3) || feeSuedDate == (currentYear))
									&& daysDifferenceForFeeSuits < 0 && flagFeeSuitMonth) {
								count++;
								totalAmountfee = totalAmountfee + amountFees;

							}
						}

					}
					if (count > 3 || totalAmountfee > 25000)
						IsReferRuleTriggered = true;

				}
				//

				if (firmMap.get("IsAnyRegulatoryInquiry") != null
						&& "Y".equals(firmMap.get("IsAnyRegulatoryInquiry").toString()))
					IsReferRuleTriggered = true;

				if (firmMap.get("IsAwarenessOfAnyProfLiability") != null
						&& "Y".equals(firmMap.get("IsAwarenessOfAnyProfLiability").toString()))
					IsReferRuleTriggered = true;

				if (firmMap.get("IsAwarenessOfAnyProfLiabilitySuitAgainst") != null
						&& "Y".equals(firmMap.get("IsAwarenessOfAnyProfLiabilitySuitAgainst").toString()))
					IsReferRuleTriggered = true;

				if (firmMap.get("IsPolicyExcludesCoverage") != null
						&& "Y".equals(firmMap.get("IsPolicyExcludesCoverage").toString()))
					IsReferRuleTriggered = true;
				if (firmMap.get("IsPublicTradedAudited") != null
						&& "Y".equals(firmMap.get("IsPublicTradedAudited").toString()))
					IsReferRuleTriggered = true;
				if (firmMap.get("IsPublicPrivateOfferingsDone") != null
						&& "Y".equals(firmMap.get("IsPublicPrivateOfferingsDone").toString()))
					IsReferRuleTriggered = true;
				// ServerTrustee
				if (firmMap.get("IsServedAsTrustee") != null
						&& "Y".equals(firmMap.get("IsServedAsTrustee").toString())) {
					// For TrusteeList
					List trusteelist = SqlResources.getSqlMapProcessor(ctx)
							.select("SqlStmts.generalviewListTrusteeOrEstateSupp", ctx);

					if (trusteelist != null) {
						Map map = new HashMap();
						for (int i = 0; i < trusteelist.size(); i++) {
							map = (HashMap) trusteelist.get(i);

							if ("3".equals(map.get("TypeofTrustKey").toString())) {
								IsReferRuleTriggered = true;
								break;
							}
							if ("Y".equals(map.get("IsBeneficiaryInterestTrust").toString())) {
								IsReferRuleTriggered = true;
								break;
							}
						}
					}
				}

				if (firmMap.get("IsFirmCarryingProfLiabilityIns") != null
						&& "Y".equals(firmMap.get("IsFirmCarryingProfLiabilityIns").toString())) {
					Object objPro = SqlResources.getSqlMapProcessor(ctx)
							.findByKey("ProfessionalLiabilityInsDetail.findByKey", ctx);
					if (objPro != null) {
						Map mapPro = (Map) objPro;
						if (mapPro.get("IsPriorActDateFull") != null
								&& "Y".equals(mapPro.get("IsPriorActDateFull").toString())) {
							if (mapPro.get("FirmYear") == null)
								IsReferRuleTriggered = true;
							else if (mapPro.get("FirmYear") != null
									&& new Integer(mapPro.get("FirmYear").toString()) <= 10)
								IsReferRuleTriggered = true;
						}
					}
				}

				if (ctx.get("StateCode") != null) {

					if (!ctx.get("StateCode").toString().equals("MO")) {

						if (firmMap.get("IsFirmDeclinedOtherthanNonPayment") != null
								&& "Y".equals(firmMap.get("IsFirmDeclinedOtherthanNonPayment").toString()))
							IsReferRuleTriggered = true;
					}
				}
			}

			/*
			 * if (ctx.get("StateCode") != null) {
			 * 
			 * if (!ctx.get("StateCode").toString().equals("MO")) {
			 * 
			 * if (ctx.get("IsFirmDeclinedOtherthanNonPayment") != null &&
			 * "Y".equals(ctx.get( "IsFirmDeclinedOtherthanNonPayment") .toString()))
			 * IsReferRuleTriggered = true; } }
			 */

			// AOP
			List aoplist = SqlResources.getSqlMapProcessor(ctx)
					.select("SqlStmts.UserStatementsaveAccountstmtspopulateAOPFields", ctx);

			if (aoplist != null) {
				Map map = new HashMap();
				for (int i = 0; i < aoplist.size(); i++) {
					map = (HashMap) aoplist.get(i);
					int aopkey = map.get("AOPKey") != null && !map.get("AOPKey").equals(HtmlConstants.EMPTY)
							? Integer.parseInt(map.get("AOPKey").toString())
							: 0;
					if (aopkey != 1 && aopkey != 2 && aopkey != 3 && aopkey != 4 && aopkey != 7 && aopkey != 8
							&& aopkey != 9 && aopkey != 10 && aopkey != 11 && aopkey != 14 && aopkey != 22)
					/*
					 * if (!"1".equals(map.get("AOPKey").toString())&&
					 * !"2".equals(map.get("AOPKey").toString()) &&
					 * !"3".equals(map.get("AOPKey").toString()) &&
					 * !"4".equals(map.get("AOPKey").toString()) &&
					 * !"7".equals(map.get("AOPKey").toString()) &&
					 * !"8".equals(map.get("AOPKey").toString()) &&
					 * !"14".equals(map.get("AOPKey").toString()) &&
					 * !"22".equals(map.get("AOPKey").toString()))
					 */ {
						if (map.get("PercentageValue") != null && !"".equals(map.get("PercentageValue").toString())
								&& !"0".equals(map.get("PercentageValue").toString())) {
							IsReferRuleTriggered = true;
							// break;
						}
					}

					// CS
					if (aopkey == 14)
					/* if ("14".equals(map.get("AOPKey").toString())) */
					{
						if (map.get("PercentageValue") != null && !"".equals(map.get("PercentageValue").toString())
								&& !"0".equals(map.get("PercentageValue").toString())) {
							// CS List
							List cslist = SqlResources.getSqlMapProcessor(ctx)
									.select("SqlStmts.computer-servicesviewgetITServicesSupp", ctx);
							if (cslist != null) {
								Map csmap = new HashMap();
								for (int j = 0; j < cslist.size(); j++) {
									csmap = (HashMap) cslist.get(j);
									if (!"1".equals(csmap.get("ITServiceCategoryKey").toString())
											&& !"2".equals(csmap.get("ITServiceCategoryKey").toString())
											&& !"3".equals(csmap.get("ITServiceCategoryKey").toString())) {
										if (csmap.get("RevenuesFromLastYear") != null
												&& !"".equals(csmap.get("RevenuesFromLastYear").toString())
												&& !"0".equals(csmap.get("RevenuesFromLastYear").toString())) {
											IsReferRuleTriggered = true;
											// break;
										}
										if (csmap.get("EstimatedRevenue") != null
												&& !"".equals(csmap.get("EstimatedRevenue").toString())
												&& !"0".equals(csmap.get("EstimatedRevenue").toString())) {
											IsReferRuleTriggered = true;
											// break;
										}
									}

								}
							}
						}
					}
					// End CS
				}
			}
			// End AOP

			// PolicyCoverage
			Object objPC = SqlResources.getSqlMapProcessor(ctx).findByKey("SqlStmts.BasicInfoviewCoveragePolicy", ctx);
			if (objPC != null && objPC instanceof Map) {

				Map map = (Map) objPC;
				Object objDeductible = removeAmountFormat(map.get("DeductibleAmount"));
				if (objDeductible != null && !"".equals(objDeductible.toString())
						&& !"0".equals(objDeductible.toString())) {
					if (Integer.parseInt(objDeductible.toString()) > 10000)
						IsReferRuleTriggered = true;
				}

				Object objAggregateLimit = removeAmountFormat(map.get("AggregateLimit"));
				Object objOccuranceLimit = removeAmountFormat(map.get("OccuranceLimit"));
				if ((objAggregateLimit != null && !"".equals(objAggregateLimit.toString())
						&& !"0".equals(objAggregateLimit.toString()))
						&& (objOccuranceLimit != null && !"".equals(objOccuranceLimit.toString())
								&& !"0".equals(objOccuranceLimit.toString()))) {
					if (Integer.parseInt(objAggregateLimit.toString()) > 1000000
							|| Integer.parseInt(objOccuranceLimit.toString()) > 1000000)
						IsReferRuleTriggered = true;
				}
			}
			// End PolicyCoverage

//			 Object objPolicyEffectiveDateRefer = RuleUtils.executeRule(ctx,
//			 		"AccountantRule.isPolicyEffectiveDateRefer");
//			 boolean flagPolicyEffectiveDateRefer = false;
//			 if (objPolicyEffectiveDateRefer instanceof Boolean)
//				 flagPolicyEffectiveDateRefer = (Boolean) objPolicyEffectiveDateRefer;
//			
//			 if (flagPolicyEffectiveDateRefer)
//				 IsReferRuleTriggered = true;

			Object objPriorActDateRefer = RuleUtils.executeRule(ctx, "AccountantRule.isPriorActDateRefer");
			boolean flagPriorActDateRefer = false;
			if (objPriorActDateRefer instanceof Boolean)
				flagPriorActDateRefer = (Boolean) objPriorActDateRefer;

			if (flagPriorActDateRefer)
				IsReferRuleTriggered = true;

			Object objPolicyExpirationDateProssRefer = RuleUtils.executeRule(ctx,
					"AccountantRule.isPolicyExpirationDateProssRefer");
			boolean flagPolicyExpirationDateProssRefer = false;
			if (objPolicyExpirationDateProssRefer instanceof Boolean)
				flagPolicyExpirationDateProssRefer = (Boolean) objPolicyExpirationDateProssRefer;

			if (flagPolicyExpirationDateProssRefer)
				IsReferRuleTriggered = true;

			if (ctx.get("PolicyExpirationDatePross") != null
					&& !ctx.get("PolicyExpirationDatePross").toString().equals("")) {
				// int daysDifferenceForEfectiveDate=(int) (DateUtils.calculateDiffInDays(new
				// Date(new SimpleDateFormat("MM/dd/yyyy").format(new
				// Date(ctx.get("PolicyExpirationDatePross").toString()))),new Date(new
				// SimpleDateFormat("MM/dd/yyyy").format(new Date())))+1);
				int daysDifferenceForEfectiveDate = (int) (DateUtils
						.calculateDiffInDays(new Date(ctx.get("PolicyExpirationDatePross").toString()), new Date()));
				if (daysDifferenceForEfectiveDate >= 0 || daysDifferenceForEfectiveDate < -60)
					IsReferRuleTriggered = true;

				/*
				 * int daysDifferenceForExpiryDate=(int) (DateUtils.calculateDiffInDays(new
				 * Date(new SimpleDateFormat("MM/dd/yyyy").format(new
				 * Date(ctx.get("PolicyExpirationDatePross").toString()))),new Date(new
				 * SimpleDateFormat("MM/dd/yyyy").format(new
				 * Date(ctx.get("PolicyEffectiveDate").toString())))));
				 * if(daysDifferenceForExpiryDate!=0) IsReferRuleTriggered = true;
				 */

			}
			Context newCtx = new Context();
			newCtx.setProject(ctx.getProject());
			newCtx.put("PolicyKey", ctx.get("PolicyKey"));
			newCtx.put("VersionSequence", ctx.get("VersionSequence"));
			newCtx.put("TransactionSequence", ctx.get("TransactionSequence"));
			newCtx.put("CoverageSequence", ctx.get("CoverageSequence"));

			List limitSequence = SqlResources.getSqlMapProcessor(newCtx)
					.select("SqlStmts.UserStatementManualBoQueriesgetPolicyCoverageDetails", newCtx);
			if (limitSequence != null && limitSequence.size() > 0)
				newCtx.putAll((Map) limitSequence.get(0));
			List limitSequencePrior = SqlResources.getSqlMapProcessor(newCtx)
					.select("SqlStmts.UserStatementManualBoQueriesgetProfessionalLiabilityDetailList", newCtx);
			if (limitSequencePrior != null && limitSequencePrior.size() > 0)
				newCtx.putAll((Map) limitSequencePrior.get(0));
			if (newCtx.get("LimitSequence") != null && newCtx.get("LimitSequencePross") != null) {
				int limitSequenceDifference = Integer.valueOf(newCtx.get("LimitSequence").toString())
						- Integer.valueOf(newCtx.get("LimitSequencePross").toString());
				if (limitSequenceDifference > 2)
					IsReferRuleTriggered = true;
			}
			if (newCtx.get("FirmYear") != null) {
				int firmYear = newCtx.get("FirmYear") != null ? Integer.valueOf(newCtx.get("FirmYear").toString()) : 0;
				if (firmYear < 10)
					IsReferRuleTriggered = true;
			}
			// Referred States
//			if (ctx.get("StateCode") != null) {
//
//				for (int i = 0; i < referredStates.length; i++) {
//					if (ctx.get("StateCode").toString().equals(
//							referredStates[i])) {
//						IsReferRuleTriggered = true;
//						break;
//					}
//				}
//			}

			// Do not show Quote Option if Policy is in Under Review Status . ie 2
//			String statusKey = ctx.get("StatusKey") != null ? ctx.get("StatusKey").toString() : "";
//			if("2".equals(statusKey))
//				IsReferRuleTriggered = true;

			// ctx.put("referRuleTriggered", referRuleTriggered);
			if (IsReferRuleTriggered) {
				ctx.put("IsFirmReferred", "Y");
			} else {
				ctx.put("IsFirmReferred", "N");
			}
			SqlResources.getSqlMapProcessor(ctx).update("SqlStmts.UserStatementsaveAccountstmtsupdateFirmReferred",
					ctx);

		} else {

			boolean isRenewPolicyReferred = false;
			AccountantValidationUtils validationUtils = new AccountantValidationUtils();

			Object obj = SqlResources.getSqlMapProcessor(ctx)
					.select("SqlStmts.UserStatementrenewapplicationgetAllPolicyOfAnAccount", ctx);
			if (obj != null && obj instanceof List) {

				Context previousContext = new Context();

				List policyList = (List) obj;

				if (policyList != null && policyList.size() > 1) {

					Map policyMap = (Map) policyList.get(policyList.size() - 2);

					previousContext.putAll(ctx);
					previousContext.put("PolicyKey", policyMap.get("PolicyKey"));

					Object objMap = SqlResources.getSqlMapProcessor(previousContext)
							.findByKey("SqlStmts.UserStatementprocessquotegetPolicyKeys", previousContext);
					if (objMap != null && objMap instanceof Map) {

						Map keyMap = (Map) objMap;

						previousContext.put("VersionSequence",
								keyMap.get("VersionSequence") != null ? keyMap.get("VersionSequence") : null);

					}
				}

				Object objPC = SqlResources.getSqlMapProcessor(ctx)
						.findByKey("SqlStmts.RenewBasicInfoviewCoveragePolicy", ctx);
				if (objPC != null && objPC instanceof Map) {

					Map map = (Map) objPC;
					Object objDeductible = removeAmountFormat(map.get("DeductibleAmount"));
					if (objDeductible != null && !"".equals(objDeductible.toString())
							&& !"0".equals(objDeductible.toString())) {
						if (Integer.parseInt(objDeductible.toString()) > 10000)
							isRenewPolicyReferred = true;
					}

					Object objAggregateLimit = removeAmountFormat(map.get("AggregateLimit"));
					Object objOccuranceLimit = removeAmountFormat(map.get("OccuranceLimit"));
					if ((objAggregateLimit != null && !"".equals(objAggregateLimit.toString())
							&& !"0".equals(objAggregateLimit.toString()))
							&& (objOccuranceLimit != null && !"".equals(objOccuranceLimit.toString())
									&& !"0".equals(objOccuranceLimit.toString()))) {
						if (Integer.parseInt(objAggregateLimit.toString()) > 1000000
								|| Integer.parseInt(objOccuranceLimit.toString()) > 1000000)
							isRenewPolicyReferred = true;
					}
				}

				List previousPolicyKey = (List) SqlResources.getSqlMapProcessor(ctx)
						.select("SqlStmts.UserStatementdroolQueriesgetPreviousPolicyKey", ctx);
				ctx.putAll((Map) previousPolicyKey.get(0));
				Context oldPolicyContext = new Context();
				oldPolicyContext.setProject(ctx.getProject());
				oldPolicyContext.put("PolicyKey", ctx.get("PreviousPolicykey"));
				oldPolicyContext.put("VersionSequence", ctx.get("previousVersionSequence"));
				List statecodeOld = SqlResources.getSqlMapProcessor(oldPolicyContext)
						.select("SqlStmts.UserStatementdroolQueriesgetAddressDetails", oldPolicyContext);
				oldPolicyContext.putAll((Map) statecodeOld.get(0));
				if (!(ctx.get("StateCode").toString()).equalsIgnoreCase(oldPolicyContext.get("StateCode").toString())) {
					isRenewPolicyReferred = true;
				}

				List aopPreviousList = SqlResources.getSqlMapProcessor(previousContext)
						.select("SqlStmts.UserStatementsaveAccountstmtspopulateAOPFields", previousContext);

				List aoplist = SqlResources.getSqlMapProcessor(ctx)
						.select("SqlStmts.UserStatementsaveAccountstmtspopulateAOPFields", ctx);

				if (aopPreviousList != null && aoplist != null) {

					if ((aopPreviousList.size() == aoplist.size())
							&& (aopPreviousList.size() > 0 && aoplist.size() > 0)) {

						for (int i = 0; i < aoplist.size(); i++) {

							Map currentMap = (HashMap) aoplist.get(i);
							Map previousMap = (HashMap) aopPreviousList.get(i);

							long percentageValue = 0;

							String aopPreviousKey = previousMap.get("AOPKey") != null
									? previousMap.get("AOPKey").toString()
									: "";
							String aopCurrentKey = currentMap.get("AOPKey") != null
									? currentMap.get("AOPKey").toString()
									: "";

							if ("5".equals(aopCurrentKey)) {

								percentageValue = AccountantValidationUtils
										.convertStringToInteger(currentMap.get("PercentageValue") != null
												? currentMap.get("PercentageValue").toString()
												: "0");

								if (percentageValue > 0)
									isRenewPolicyReferred = true;

							}

							if (("6".equals(aopPreviousKey) && "6".equals(aopCurrentKey))
									|| ("9".equals(aopPreviousKey) && "9".equals(aopCurrentKey))
									|| ("10".equals(aopPreviousKey) && "10".equals(aopCurrentKey))
									|| ("11".equals(aopPreviousKey) && "11".equals(aopCurrentKey))) {

								long previousPercentage = AccountantValidationUtils
										.convertStringToLong(previousMap.get("PercentageValue") != null
												? previousMap.get("PercentageValue").toString()
												: "0");
								long currentPercentage = AccountantValidationUtils
										.convertStringToLong(currentMap.get("PercentageValue") != null
												? currentMap.get("PercentageValue").toString()
												: "0");

								if (currentPercentage - previousPercentage > 10)
									isRenewPolicyReferred = true;
							}
							if (("13".equals(aopPreviousKey) && "13".equals(aopCurrentKey))
									|| ("17".equals(aopPreviousKey) && "17".equals(aopCurrentKey))
									|| ("18".equals(aopPreviousKey) && "18".equals(aopCurrentKey))
									|| ("23".equals(aopPreviousKey) && "23".equals(aopCurrentKey))
									|| ("24".equals(aopPreviousKey) && "24".equals(aopCurrentKey))) {

								long previousPercentage = AccountantValidationUtils
										.convertStringToLong(previousMap.get("PercentageValue") != null
												? previousMap.get("PercentageValue").toString()
												: "0");
								long currentPercentage = AccountantValidationUtils
										.convertStringToLong(currentMap.get("PercentageValue") != null
												? currentMap.get("PercentageValue").toString()
												: "0");
								if (previousPercentage == 0 && currentPercentage > 0)
									isRenewPolicyReferred = true;
								if (currentPercentage - previousPercentage > 10)
									isRenewPolicyReferred = true;
							}

							if (("16".equals(aopPreviousKey) && "16".equals(aopCurrentKey))) {
								long previousPercentage = AccountantValidationUtils
										.convertStringToLong(previousMap.get("PercentageValue") != null
												? previousMap.get("PercentageValue").toString()
												: "0");
								long currentPercentage = AccountantValidationUtils
										.convertStringToLong(currentMap.get("PercentageValue") != null
												? currentMap.get("PercentageValue").toString()
												: "0");

								if (currentPercentage - previousPercentage > 5)
									isRenewPolicyReferred = true;
							}

							if ("12".equals(aopCurrentKey) && "12".equals(aopPreviousKey)
									|| ("15".equals(aopPreviousKey) && "15".equals(aopCurrentKey))) {

								int previousPercentage = AccountantValidationUtils
										.convertStringToInteger(previousMap.get("PercentageValue") != null
												? previousMap.get("PercentageValue").toString()
												: "0");
								int currentPercentage = AccountantValidationUtils
										.convertStringToInteger(currentMap.get("PercentageValue") != null
												? currentMap.get("PercentageValue").toString()
												: "0");

								if (currentPercentage > previousPercentage)
									isRenewPolicyReferred = true;

							}

							// if ("18".equals(aopCurrentKey)) {
							//
							// percentageValue = AccountantValidationUtils
							// .convertStringToInteger(currentMap
							// .get("PercentageValue") != null ? currentMap
							// .get("PercentageValue")
							// .toString()
							// : "0");
							//
							// if (percentageValue > 0)
							// isRenewPolicyReferred = true;
							//
							// }

							if ("19".equals(aopCurrentKey)) {

								percentageValue = AccountantValidationUtils
										.convertStringToInteger(currentMap.get("PercentageValue") != null
												? currentMap.get("PercentageValue").toString()
												: "0");

								if (percentageValue > 0)
									isRenewPolicyReferred = true;

							}

							if ("20".equals(aopCurrentKey)) {

								percentageValue = AccountantValidationUtils
										.convertStringToInteger(currentMap.get("PercentageValue") != null
												? currentMap.get("PercentageValue").toString()
												: "0");

								if (percentageValue > 0)
									isRenewPolicyReferred = true;

							}

							if ("21".equals(aopCurrentKey)) {

								percentageValue = AccountantValidationUtils
										.convertStringToInteger(currentMap.get("PercentageValue") != null
												? currentMap.get("PercentageValue").toString()
												: "0");

								if (percentageValue > 0)
									isRenewPolicyReferred = true;

							}
							/*
							 * if (("14".equals(aopPreviousKey) && "14".equals(aopCurrentKey)) ) {
							 * 
							 * long previousPercentage =
							 * AccountantValidationUtils.convertStringToLong(previousMap.get(
							 * "PercentageValue") != null ? previousMap .get("PercentageValue").toString():
							 * "0"); long currentPercentage = AccountantValidationUtils
							 * .convertStringToLong(currentMap.get("PercentageValue") != null ?
							 * currentMap.get("PercentageValue").toString(): "0"); if(previousPercentage==0
							 * && currentPercentage>0) isRenewPolicyReferred = true; if(currentPercentage -
							 * previousPercentage > 0 ) isRenewPolicyReferred = true; }
							 */
						}

					}
				}

				List ITServiceListPrevious = SqlResources.getSqlMapProcessor(previousContext)
						.select("SqlStmts.computer-servicesviewgetITServicesSupp", previousContext);
				List ITServiceListCurrent = SqlResources.getSqlMapProcessor(ctx)
						.select("SqlStmts.computer-servicesviewgetITServicesSupp", ctx);

				if (ITServiceListCurrent != null && ITServiceListCurrent.size() > 0) {

					if (ITServiceListPrevious != null && ITServiceListPrevious.size() > 0) {

						for (int i = 0; i < ITServiceListCurrent.size(); i++) {

							Map previousMap = (Map) ITServiceListPrevious.get(i);
							Map currentMap = (Map) ITServiceListCurrent.get(i);

							String ITServiceCategoryKeyPrevious = previousMap.get("ITServiceCategoryKey") != null
									? previousMap.get("ITServiceCategoryKey").toString()
									: "";
							String ITServiceCategoryKeyCurrent = currentMap.get("ITServiceCategoryKey") != null
									? currentMap.get("ITServiceCategoryKey").toString()
									: "";

							if (("3".equals(ITServiceCategoryKeyPrevious) && "3".equals(ITServiceCategoryKeyCurrent))
									|| ("4".equals(ITServiceCategoryKeyPrevious)
											&& "4".equals(ITServiceCategoryKeyCurrent))
									|| ("5".equals(ITServiceCategoryKeyPrevious)
											&& "5".equals(ITServiceCategoryKeyCurrent))
									|| ("6".equals(ITServiceCategoryKeyPrevious)
											&& "6".equals(ITServiceCategoryKeyCurrent))) {

								int revenueFromLastYearPrevious = AccountantValidationUtils
										.convertStringToInteger(previousMap.get("RevenuesFromLastYear") != null
												? previousMap.get("RevenuesFromLastYear").toString()
												: "0");
								int revenueFromLastYearCurrent = AccountantValidationUtils
										.convertStringToInteger(currentMap.get("RevenuesFromLastYear") != null
												? currentMap.get("RevenuesFromLastYear").toString()
												: "0");

								if (revenueFromLastYearPrevious != revenueFromLastYearCurrent)
									isRenewPolicyReferred = true;

								int estimatedRevenuePrevious = AccountantValidationUtils
										.convertStringToInteger(previousMap.get("EstimatedRevenue") != null
												? previousMap.get("EstimatedRevenue").toString()
												: "0");
								int estimatedRevenueCurrent = AccountantValidationUtils
										.convertStringToInteger(currentMap.get("EstimatedRevenue") != null
												? currentMap.get("EstimatedRevenue").toString()
												: "0");

								if (estimatedRevenuePrevious != estimatedRevenueCurrent)
									isRenewPolicyReferred = true;

								String isAnnuallyPrevious = previousMap.get("IsAnnually") != null
										? previousMap.get("IsAnnually").toString()
										: "";
								String isAnnuallyCurrent = currentMap.get("IsAnnually") != null
										? currentMap.get("IsAnnually").toString()
										: "";

								if (!isAnnuallyPrevious.equals(isAnnuallyCurrent))
									isRenewPolicyReferred = true;

							}
						}
					} else {

						for (int i = 0; i < ITServiceListCurrent.size(); i++) {

							Map currentMap = (Map) ITServiceListCurrent.get(i);

							String ITServiceCategoryKeyCurrent = currentMap.get("ITServiceCategoryKey") != null
									? currentMap.get("ITServiceCategoryKey").toString()
									: "";

							if ("3".equals(ITServiceCategoryKeyCurrent) || "4".equals(ITServiceCategoryKeyCurrent)
									|| "5".equals(ITServiceCategoryKeyCurrent)
									|| "6".equals(ITServiceCategoryKeyCurrent)) {

								int revenueFromLastYearCurrent = AccountantValidationUtils
										.convertStringToInteger(currentMap.get("RevenuesFromLastYear") != null
												? currentMap.get("RevenuesFromLastYear").toString()
												: "0");

								if (revenueFromLastYearCurrent > 0)
									isRenewPolicyReferred = true;

								int estimatedRevenueCurrent = AccountantValidationUtils
										.convertStringToInteger(currentMap.get("EstimatedRevenue") != null
												? currentMap.get("EstimatedRevenue").toString()
												: "0");

								if (estimatedRevenueCurrent > 0)
									isRenewPolicyReferred = true;

								String isAnnuallyCurrent = currentMap.get("IsAnnually") != null
										? currentMap.get("IsAnnually").toString()
										: "";

								if (!isAnnuallyCurrent.equals(""))
									isRenewPolicyReferred = true;

							}

						}
					}

				}

				/* new */
				long totalFirmPersonnel = 0;
				Object objMapFirm = SqlResources.getSqlMapProcessor(previousContext)
						.findByKey("SqlStmts.generalviewgetFirmDetails", ctx);
				if (objMapFirm != null && objMapFirm instanceof Map) {

					Map keyMapFirm = (Map) objMapFirm;

					totalFirmPersonnel = keyMapFirm.get("TotalFirmPersonnel") != null ? AccountantValidationUtils
							.convertStringToLong(keyMapFirm.get("TotalFirmPersonnel").toString()) : 0;
					if (totalFirmPersonnel > 15)
						isRenewPolicyReferred = true;

				}

				if (AccountantValidationUtils.checkIfAccountNameIsChanged(ctx))
					isRenewPolicyReferred = true;

				if (AccountantValidationUtils.isManualInPriorYear(previousContext))
					isRenewPolicyReferred = true;

				Object objFirm = SqlResources.getSqlMapProcessor(ctx).findByKey("Firm.findByKey", ctx);
				if (objFirm != null && objFirm instanceof Map) {

					Map objMap = (Map) objFirm;

					String IsAnyFirmMeargedOrAcquired = objMap.get("IsAnyFirmMeargedOrAcquired") != null
							? objMap.get("IsAnyFirmMeargedOrAcquired").toString()
							: "";
					if (IsAnyFirmMeargedOrAcquired.equals("Y"))
						isRenewPolicyReferred = true;

					String IsControlClientFunds = objMap.get("IsControlClientFunds") != null
							? objMap.get("IsControlClientFunds").toString()
							: "";
					if (IsControlClientFunds.equals("Y"))
						isRenewPolicyReferred = true;

					String IsServedAsTrustee = objMap.get("IsServedAsTrustee") != null
							? objMap.get("IsServedAsTrustee").toString()
							: "";
					if (IsServedAsTrustee.equals("Y"))
						isRenewPolicyReferred = true;

					String IsFirmRenderedServiceForAnyClient = objMap.get("IsFirmRenderedServiceForAnyClient") != null
							? objMap.get("IsFirmRenderedServiceForAnyClient").toString()
							: "";
					if (IsFirmRenderedServiceForAnyClient.equals("Y"))
						isRenewPolicyReferred = true;

					String IsSuitInstituted = objMap.get("IsSuitInstituted") != null
							? objMap.get("IsSuitInstituted").toString()
							: "";
					if (IsSuitInstituted.equals("Y"))
						isRenewPolicyReferred = true;

					String IsFirmsRevenueMorethan25Percent = objMap.get("IsFirmsRevenueMorethan25Percent") != null
							? objMap.get("IsFirmsRevenueMorethan25Percent").toString()
							: "";
					if (IsFirmsRevenueMorethan25Percent.equals("Y"))
						isRenewPolicyReferred = true;

					String IsAnyRegulatoryInquiry = objMap.get("IsAnyRegulatoryInquiry") != null
							? objMap.get("IsAnyRegulatoryInquiry").toString()
							: "";
					if (IsAnyRegulatoryInquiry.equals("Y"))
						isRenewPolicyReferred = true;

					String IsAwarenessOfAnyProfLiability = objMap.get("IsAwarenessOfAnyProfLiability") != null
							? objMap.get("IsAwarenessOfAnyProfLiability").toString()
							: "";
					if (IsAwarenessOfAnyProfLiability.equals("Y"))
						isRenewPolicyReferred = true;

					String IsAwarenessOfAnyProfLiabilitySuitAgainst = objMap
							.get("IsAwarenessOfAnyProfLiabilitySuitAgainst") != null
									? objMap.get("IsAwarenessOfAnyProfLiabilitySuitAgainst").toString()
									: "";
					if (IsAwarenessOfAnyProfLiabilitySuitAgainst.equals("Y"))
						isRenewPolicyReferred = true;

					String IsSyndicationManagers = objMap.get("IsSyndicationManagers") != null
							? objMap.get("IsSyndicationManagers").toString()
							: "";

					if ("Y".equals(IsSyndicationManagers))
						isRenewPolicyReferred = true;

					String IsDebtOrEquityFinancing = objMap.get("IsDebtOrEquityFinancing") != null
							? objMap.get("IsDebtOrEquityFinancing").toString()
							: "";

					if ("Y".equals(IsDebtOrEquityFinancing))
						isRenewPolicyReferred = true;

					String IsCommissionRecieved = objMap.get("IsCommissionRecieved") != null
							? objMap.get("IsCommissionRecieved").toString()
							: "";

					if ("Y".equals(IsCommissionRecieved))
						isRenewPolicyReferred = true;

				}

				if (AccountantValidationUtils.checkIfValueInAuditWork(ctx))
					isRenewPolicyReferred = true;

				Object objRenewAttestation = SqlResources.getSqlMapProcessor(ctx).findByKey("Attestation.findByKey",
						ctx);
				if (objRenewAttestation != null && objRenewAttestation instanceof Map) {

					Map map = (Map) objRenewAttestation;

					int FirmRenderedAuditAtt = map.get("FirmRenderedAuditAtt") != null
							? Integer.parseInt(map.get("FirmRenderedAuditAtt").toString())
							: 0;
					if (FirmRenderedAuditAtt > 0)
						isRenewPolicyReferred = true;

					String IsLastPeerReviewAtt = map.get("IsLastPeerReviewAtt") != null
							? map.get("IsLastPeerReviewAtt").toString()
							: "";
					if ("Y".equals(IsLastPeerReviewAtt))
						isRenewPolicyReferred = true;

					String FirmProvidedInvestmentFunds = map.get("FirmProvidedInvestmentFunds") != null
							? map.get("FirmProvidedInvestmentFunds").toString()
							: "";

					// String FirmActedAsBusinessBroker = map
					// .get("FirmActedAsBusinessBroker") != null ? map
					// .get("FirmActedAsBusinessBroker").toString()
					// : "";
					//
					// String IsFirmPerformedServicesOrConsented = map
					// .get("IsFirmPerformedServicesOrConsented") != null ?
					// map
					// .get("IsFirmPerformedServicesOrConsented")
					// .toString()
					// : "";
					//
					// if ("Y".equals(FirmProvidedInvestmentFunds)
					// || "Y".equals(FirmActedAsBusinessBroker)
					// || "Y"
					// .equals(IsFirmPerformedServicesOrConsented))
					// isRenewPolicyReferred = true;

				}

				Object objBusiness = SqlResources.getSqlMapProcessor(ctx).findByKey("FundsControlledSupp.findByKey",
						ctx);
				if (objBusiness != null && objBusiness instanceof Map) {

					Map objMap = (Map) objBusiness;

					// String IsCountersignatureRequiredFc = objMap
					// .get("IsCountersignatureRequiredFc") != null ? objMap
					// .get("IsCountersignatureRequiredFc").toString()
					// : "";
					// if ("Y".equals(IsCountersignatureRequiredFc))
					// isRenewPolicyReferred = true;

					String HaveInvestmentAuthorityFC = objMap.get("HaveInvestmentAuthorityFC") != null
							? objMap.get("HaveInvestmentAuthorityFC").toString()
							: "";
					if ("Y".equals(HaveInvestmentAuthorityFC))
						isRenewPolicyReferred = true;

					String IsControlFundsForBusinessAndEntimentFc = objMap
							.get("IsControlFundsForBusinessAndEntimentFc") != null
									? objMap.get("IsControlFundsForBusinessAndEntimentFc").toString()
									: "";
					if ("Y".equals(IsControlFundsForBusinessAndEntimentFc))
						isRenewPolicyReferred = true;

					String IsFirmPersonnelBusinessManagerFc = objMap.get("IsFirmPersonnelBusinessManagerFc") != null
							? objMap.get("IsFirmPersonnelBusinessManagerFc").toString()
							: "";
					if ("Y".equals(IsFirmPersonnelBusinessManagerFc))
						isRenewPolicyReferred = true;

				}

				long previousAverageRevenue = 0;
				Object objpmap = SqlResources.getSqlMapProcessor(previousContext).findByKey("Firm.findByKey",
						previousContext);
				if (objpmap != null && objpmap instanceof Map) {

					Map firmMap = (Map) objpmap;
					previousAverageRevenue = firmMap.get("AverageRevenue") != null
							? AccountantValidationUtils.convertStringToLong(firmMap.get("AverageRevenue").toString())
							: 0;

				}

				long currentAverageRevenue = 0;

				Object objcmap = SqlResources.getSqlMapProcessor(ctx).findByKey("Firm.findByKey", ctx);
				if (objcmap != null && objcmap instanceof Map) {

					Map firmMap = (Map) objcmap;
					currentAverageRevenue = firmMap.get("AverageRevenue") != null
							? AccountantValidationUtils.convertStringToLong(firmMap.get("AverageRevenue").toString())
							: 0;

				}

				double revenuePercent = getPercentIncrease(previousAverageRevenue, currentAverageRevenue);
				if (revenuePercent >= 50)
					isRenewPolicyReferred = true;

				// if(currentAverageRevenue < 25000)

				if (currentAverageRevenue > 750000 || currentAverageRevenue < 20000)
					isRenewPolicyReferred = true;

				// Referred States
//				if (ctx.get("StateCode") != null) {
//
//					for (int i = 0; i < referredStates.length; i++) {
//						if (ctx.get("StateCode").toString().equals(
//								referredStates[i])) {
//							isRenewPolicyReferred = true;
//							break;
//						}
//					}
//				}

				// Do not show Quote option to insured if policy is in under review status
//				String statusKey = ctx.get("StatusKey") != null ? ctx.get("StatusKey").toString() : "";
//				if("2".equals(statusKey))
//					isRenewPolicyReferred = true;

				previousContext.clear();

				if (isRenewPolicyReferred) {
					ctx.put("referRuleTriggered", "Y");
					ctx.put("IsFirmReferred", "Y");
				} else {
					ctx.put("referRuleTriggered", "N");
					ctx.put("IsFirmReferred", "N");
				}

				SqlResources.getSqlMapProcessor(ctx)
						.update("SqlStmts.UserStatementrenewapplicationupdateIsRenewFirmReferred", ctx);

			}
		}

	}

	/*
	 * public void isReferRuleTriggeredNewApp(IContext ctx) throws Exception {
	 * 
	 * String policyStatusKey = ctx.get("PolicyStatusKey") != null ?
	 * ctx.get("PolicyStatusKey").toString() : ""; int referralCounts=0; int
	 * PolicyKey=ctx.get("PolicyKey")!=null &&
	 * !ctx.get("PolicyKey").equals(HtmlConstants.EMPTY) ?
	 * Integer.parseInt(ctx.get("PolicyKey").toString()):0;;
	 * ctx.put("PolicyKey",PolicyKey); List detailsObj =(List)
	 * SqlResources.getSqlMapProcessor(ctx).select("Firm.GetDataForInsured_p", ctx);
	 * ctx.putAll((Map)detailsObj.get(0)); Map obj_code1 =
	 * (Map)SqlResources.getSqlMapProcessor(ctx).findByKey(
	 * "SqlStmts.UserStatementManualBoQueriesgetReferralCountTriggered", ctx);
	 * if(obj_code1 != null){
	 * referralCounts=obj_code1.get("referralCounts")!=null?Integer.parseInt(
	 * obj_code1.get("referralCounts").toString()):0;
	 * 
	 * }
	 * 
	 * List claimReferralList =(List) SqlResources.getSqlMapProcessor(ctx).select(
	 * "ClaimIncidentSupp.GetClaimsAgainstAccountLW_p", ctx);
	 * ctx.putAll((Map)claimReferralList.get(0));
	 * 
	 * if ("1".equals(policyStatusKey)) {
	 * 
	 * boolean IsReferRuleTriggered = true; //Comment by client request ,reference
	 * quote number is JIRA PA-1454 if("Y".equals(ctx.get("isReferred").toString()))
	 * IsReferRuleTriggered=true; if(!"0".equals((ctx.get("IndicationCount") ==
	 * null) ? "0" : ctx.get("IndicationCount").toString())) IsReferRuleTriggered =
	 * true; List revenueList = SqlResources.getSqlMapProcessor(ctx).select(
	 * "SqlStmts.BasicInfoviewmomgrossrevenue", ctx); long average = 0;
	 * 
	 * if (revenueList != null) { Map map = new HashMap(); long total = 0; int count
	 * = 0; for (int i = 0; i < revenueList.size(); i++) { map = (HashMap)
	 * revenueList.get(i); Object objAmount = map.get("Amount"); if (objAmount !=
	 * null && !"".equals(objAmount.toString()) &&
	 * !"0".equals(objAmount.toString())) { total = total +
	 * Long.parseLong(objAmount.toString()); count = count + 1; } } if (count > 0) {
	 * average = total / count; } } if (average < 20000 || average > 750000)
	 * IsReferRuleTriggered = true; old flow if (average > 750000 || average <
	 * 25000) IsReferRuleTriggered = true;
	 * 
	 * new flow if("NY".equalsIgnoreCase(ctx.get("StateCode").toString()) ||
	 * "NM".equalsIgnoreCase(ctx.get("StateCode").toString())) IsReferRuleTriggered
	 * = true;
	 * 
	 * 
	 * Object objAddress = SqlResources.getSqlMapProcessor(ctx).findByKey(
	 * "SqlStmts.BasicInfoviewgetAddressdetails", ctx); if(objAddress!=null) {
	 * boolean containsPOBOX=false; Map mapAddress = null; if (objAddress != null)
	 * mapAddress = (Map) objAddress;
	 * 
	 * String addressPOBox = ""; String address1 = "";
	 * 
	 * for (int i = 0; i < mapAddress.size(); i++) { Map mapIssueMail = (Map)
	 * mapAddress.get(i); address1 = mapAddress.get("Address1") != null ?
	 * mapAddress.get("Address1").toString().trim().toLowerCase() : ""; }
	 * 
	 * New Changes Start String
	 * sentence1=address1.toString().replaceAll("[-,., ,,,]",""); String
	 * search="pobox";
	 * 
	 * if (sentence1.toLowerCase().indexOf(search.toLowerCase()) != -1 ) {
	 * containsPOBOX=true; } New changes ends
	 * 
	 * if(containsPOBOX) IsReferRuleTriggered = true; }
	 * 
	 * Object firmObject =
	 * SqlResources.getSqlMapProcessor(ctx).findByKey("Firm.findByKey", ctx); if
	 * (firmObject != null && firmObject instanceof Map) { Map firmMap = (Map)
	 * firmObject; int totalPersonals=firmMap.get("TotalFirmPersonnel") != null ?
	 * Integer.valueOf(firmMap.get("TotalFirmPersonnel").toString()):0;
	 * 
	 * if (totalPersonals > 10) IsReferRuleTriggered = true;
	 * 
	 * if (firmMap.get("IsFirmRenderingServices") != null &&
	 * "Y".equals(firmMap.get("IsFirmRenderingServices") .toString()))
	 * IsReferRuleTriggered = true;
	 * 
	 * if (firmMap.get("IsFirmsRevenueMorethan25Percent") != null &&
	 * "Y".equals(firmMap.get( "IsFirmsRevenueMorethan25Percent").toString()))
	 * IsReferRuleTriggered = true;
	 * 
	 * if (firmMap.get("IsAnyFirmMeargedOrAcquired") != null &&
	 * "Y".equals(firmMap.get("IsAnyFirmMeargedOrAcquired") .toString()))
	 * IsReferRuleTriggered = true;
	 * 
	 * if (firmMap.get("IsControlClientFunds") != null &&
	 * "Y".equals(firmMap.get("IsControlClientFunds") .toString()))
	 * IsReferRuleTriggered = true;
	 * 
	 * commented by sukhi if (firmMap.get("IsInvestmentVenture") != null &&
	 * "Y".equals(firmMap.get("IsInvestmentVenture") .toString()))
	 * IsReferRuleTriggered = true;
	 * 
	 * 
	 * edited by sukhi
	 * 
	 * if ((firmMap.get("IsOrganisationInvestmentVentures") != null &&
	 * "Y".equals(firmMap.get("IsOrganisationInvestmentVentures").toString())) ||
	 * (firmMap.get("IsReferralsInvestmentVentures") != null &&
	 * "Y".equals(firmMap.get("IsReferralsInvestmentVentures") .toString())) ||
	 * (firmMap.get("IsArrangedDebtorEquity") != null &&
	 * "Y".equals(firmMap.get("IsArrangedDebtorEquity") .toString())))
	 * IsReferRuleTriggered = true;
	 * 
	 * end
	 * 
	 * if (firmMap.get("IsSyndicationManagers") != null &&
	 * "Y".equals(firmMap.get("IsSyndicationManagers") .toString()))
	 * IsReferRuleTriggered = true;
	 * 
	 * if (firmMap.get("IsFirmRenderedAuditOrAttestService") != null &&
	 * "Y".equals(firmMap.get( "IsFirmRenderedAuditOrAttestService") .toString()))
	 * IsReferRuleTriggered = true;
	 * 
	 * if (firmMap.get("IsManagementServiceProvided") != null && "Y".equals(firmMap
	 * .get("IsManagementServiceProvided").toString())) IsReferRuleTriggered = true;
	 * 
	 * if (firmMap.get("IsCommissionRecieved") != null &&
	 * "Y".equals(firmMap.get("IsCommissionRecieved") .toString()))
	 * IsReferRuleTriggered = true;
	 * 
	 * if (firmMap.get("IsDebtOrEquityFinancing") != null &&
	 * "Y".equals(firmMap.get("IsDebtOrEquityFinancing") .toString()))
	 * IsReferRuleTriggered = true;
	 * 
	 * if (firmMap.get("IsNonPublicInvestmentDone") != null &&
	 * "Y".equals(firmMap.get("IsNonPublicInvestmentDone") .toString()))
	 * IsReferRuleTriggered = true;
	 * 
	 * if (firmMap.get("IsFirmRenderedServiceForAnyClient") != null &&
	 * "Y".equals(firmMap.get( "IsFirmRenderedServiceForAnyClient") .toString()))
	 * IsReferRuleTriggered = true;
	 * 
	 * if (firmMap.get("IsSuitInstituted") != null &&
	 * "Y".equals(firmMap.get("IsSuitInstituted").toString())){ List
	 * firmInitLawsuits=SqlResources.getSqlMapProcessor(ctx).select(
	 * "SqlStmts.UserStatementdroolQueriesgetFirmInitLawsuits",ctx); int count=0;
	 * int totalAmountfee=0; long daysDifferenceForFeeSuits=0; boolean
	 * flagFeeSuitMonth=true;
	 * 
	 * if(firmInitLawsuits!=null){ for (int i = 0; i < firmInitLawsuits.size(); i++)
	 * { Map dataMap = (Map) firmInitLawsuits.get(i); String
	 * str=dataMap.get("SuitFilesDateFees").toString(); String effectiveDatePolicy =
	 * ""; if(ctx.get("PolicyEffectiveDate").toString().contains(":")){
	 * effectiveDatePolicy=new SimpleDateFormat("MM/dd/yyyy").format(new
	 * SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").parse(ctx.get("PolicyEffectiveDate"
	 * ).toString())); } else { effectiveDatePolicy=new
	 * SimpleDateFormat("MM/dd/yyyy").format(new
	 * Date(ctx.get("PolicyEffectiveDate").toString())); } String
	 * amountFee=dataMap.get("AmountOfFeesSued").toString(); int
	 * amountFees=Integer.parseInt(amountFee);
	 * 
	 * ctx.put("effectiveDateOfPolicy", effectiveDatePolicy);
	 * ctx.put("SuitFilesDateFeesFormat", new
	 * SimpleDateFormat("MM/dd/yyyy").format(new
	 * Date(dataMap.get("SuitFilesDateFees").toString())));
	 * 
	 * int feeSuedDate=Integer.valueOf(str.substring(str.length()-4,str.length()));
	 * 
	 * SimpleDateFormat sdf=new SimpleDateFormat("yyyy"); int
	 * currentYear=Integer.valueOf(sdf.format(new
	 * Date(effectiveDatePolicy)).toString());
	 * 
	 * System.out.println(currentYear+" "+(currentYear-1)+" "+(currentYear-2)+" "+(
	 * currentYear-3)); System.out.println(feeSuedDate);
	 * 
	 * //daysDifferenceForFeeSuits=DateUtils.calculateDiffInDays(new
	 * Date(ctx.get("effectiveDateOfPolicy").toString()),new
	 * Date(ctx.get("SuitFilesDateFeesFormat").toString()));
	 * daysDifferenceForFeeSuits=DateUtils.calculateDiffInDays(new
	 * Date(ctx.get("SuitFilesDateFeesFormat").toString()),new
	 * Date(ctx.get("effectiveDateOfPolicy").toString()));
	 * 
	 * if(feeSuedDate==(currentYear-3)){ int monthFeeSuit
	 * =Integer.valueOf(str.substring(0, Math.min(str.length(),2))); int
	 * monthEffectiveDatePolicy=Integer.valueOf(effectiveDatePolicy.substring(0,
	 * Math.min(effectiveDatePolicy.length(),2)));
	 * System.out.println("Fee Siut Month..."+monthFeeSuit+" Effective date Month.."
	 * +monthEffectiveDatePolicy); if(monthFeeSuit<monthEffectiveDatePolicy){
	 * flagFeeSuitMonth=false; } }
	 * 
	 * if((feeSuedDate==(currentYear-1)||feeSuedDate==(currentYear-2)||feeSuedDate==
	 * (currentYear-3) ||feeSuedDate==(currentYear))&& daysDifferenceForFeeSuits<0
	 * && flagFeeSuitMonth ){ count++; totalAmountfee=totalAmountfee+amountFees; } }
	 * }
	 * 
	 * if(count > 3 || totalAmountfee > 25000) IsReferRuleTriggered = true; }
	 * 
	 * 
	 * if (firmMap.get("IsAnyRegulatoryInquiry") != null &&
	 * "Y".equals(firmMap.get("IsAnyRegulatoryInquiry") .toString()))
	 * IsReferRuleTriggered = true;
	 * 
	 * if (firmMap.get("IsAwarenessOfAnyProfLiability") != null &&
	 * "Y".equals(firmMap.get( "IsAwarenessOfAnyProfLiability").toString()))
	 * IsReferRuleTriggered = true;
	 * 
	 * if (firmMap.get("IsAwarenessOfAnyProfLiabilitySuitAgainst") != null &&
	 * "Y".equals(firmMap.get( "IsAwarenessOfAnyProfLiabilitySuitAgainst")
	 * .toString())) IsReferRuleTriggered = true;
	 * 
	 * if (firmMap.get("IsPolicyExcludesCoverage") != null &&
	 * "Y".equals(firmMap.get("IsPolicyExcludesCoverage") .toString()))
	 * IsReferRuleTriggered = true;
	 * 
	 * if (firmMap.get("IsPublicTradedAudited") != null &&
	 * "Y".equals(firmMap.get("IsPublicTradedAudited").toString()))
	 * IsReferRuleTriggered = true;
	 * 
	 * if (firmMap.get("IsPublicPrivateOfferingsDone") != null &&
	 * "Y".equals(firmMap.get("IsPublicPrivateOfferingsDone").toString()))
	 * IsReferRuleTriggered = true; //Raghu //commented by sukhi if
	 * (firmMap.get("IsPolicyProhibiting") == null ||
	 * (firmMap.get("IsPolicyProhibiting") != null &&
	 * "N".equals(firmMap.get("IsPolicyProhibiting").toString())) )
	 * IsReferRuleTriggered = true;
	 * 
	 * //Sukhi if (firmMap.get("IsPolicyProhibiting") != null &&
	 * "N".equals(firmMap.get("IsPolicyProhibiting").toString()))
	 * IsReferRuleTriggered = true;
	 * 
	 * if (firmMap.get("IsNonMonetaryCompensationRecieved") != null &&
	 * "Y".equals(firmMap.get("IsNonMonetaryCompensationRecieved").toString()))
	 * IsReferRuleTriggered = true;
	 * 
	 * if (firmMap.get("IsReducedNumberOfOwners") != null &&
	 * "Y".equals(firmMap.get("IsReducedNumberOfOwners").toString()))
	 * IsReferRuleTriggered = true;
	 * 
	 * if (firmMap.get("IsAnyNonPublicInvestments") != null &&
	 * "Y".equals(firmMap.get("IsAnyNonPublicInvestments").toString()))
	 * IsReferRuleTriggered = true;
	 * 
	 * if (firmMap.get("IsContractualRelationShip") != null &&
	 * "Y".equals(firmMap.get("IsContractualRelationShip").toString()))
	 * IsReferRuleTriggered = true;
	 * 
	 * IsDiscretionaryAssetMangt IsSecuritiesSales IsAnyFirmAffiliateLicensed
	 * IsAnyEmployeeBenefitPlan IsAnyReferralFeesInvestments --
	 * FinancialPlanningAndIAServiceSupp
	 * 
	 * Object financialPlanSuppObj = SqlResources.getSqlMapProcessor(ctx).findByKey(
	 * "FinancialPlanningAndIAServiceSupp.findByKey", ctx); if (financialPlanSuppObj
	 * != null && financialPlanSuppObj instanceof Map) { Map financialPlanSuppMap =
	 * (Map) financialPlanSuppObj;
	 * 
	 * if (financialPlanSuppMap.get("IsAnyReferralFeesInvestments") != null &&
	 * "Y".equals(financialPlanSuppMap.get("IsAnyReferralFeesInvestments").toString(
	 * ))) IsReferRuleTriggered = true; }
	 * 
	 * // ServerTrustee if (firmMap.get("IsServedAsTrustee") != null &&
	 * "Y".equals(firmMap.get("IsServedAsTrustee").toString())) {
	 * IsReferRuleTriggered = true; // For TrusteeList List trusteelist =
	 * SqlResources.getSqlMapProcessor(ctx).select(
	 * "SqlStmts.generalviewListTrusteeOrEstateSupp",ctx); if (trusteelist != null)
	 * { Map map = new HashMap(); for (int i = 0; i < trusteelist.size(); i++) { map
	 * = (HashMap) trusteelist.get(i);
	 * 
	 * if ("3".equals(map.get("TypeofTrustKey") .toString())) { IsReferRuleTriggered
	 * = true; break; } if ("Y".equals(map .get("IsBeneficiaryInterestTrust")
	 * .toString())) { IsReferRuleTriggered = true; break; } } } }
	 * 
	 * if (firmMap.get("IsFirmCarryingProfLiabilityIns") != null &&
	 * "Y".equals(firmMap.get("IsFirmCarryingProfLiabilityIns").toString())) {
	 * 
	 * Object objPro = SqlResources.getSqlMapProcessor(ctx).findByKey(
	 * "ProfessionalLiabilityInsDetail.findByKey",ctx); if (objPro != null) { Map
	 * mapPro = (Map) objPro; if (mapPro.get("IsPriorActDateFull") != null &&
	 * "Y".equals(mapPro.get("IsPriorActDateFull") .toString())) { if
	 * (mapPro.get("FirmYear") == null) IsReferRuleTriggered = true; else if
	 * (mapPro.get("FirmYear") != null && new Integer(mapPro.get("FirmYear")
	 * .toString()) < 10) IsReferRuleTriggered = true; } } }
	 * 
	 * if (ctx.get("StateCode") != null) { if
	 * (!ctx.get("StateCode").toString().equals("MO")) { if
	 * (firmMap.get("IsFirmDeclinedOtherthanNonPayment") != null &&
	 * "Y".equals(firmMap.get("IsFirmDeclinedOtherthanNonPayment") .toString()))
	 * IsReferRuleTriggered = true; } } }
	 * 
	 * // AOP List aoplist = SqlResources.getSqlMapProcessor(ctx).select(
	 * "SqlStmts.UserStatementsaveAccountstmtspopulateAOPFields",ctx);
	 * 
	 * if (aoplist != null) { Map map = new HashMap(); for (int i = 0; i <
	 * aoplist.size(); i++) { map = (HashMap) aoplist.get(i); int
	 * aopkey=map.get("AOPKey")!=null &&
	 * !map.get("AOPKey").equals(HtmlConstants.EMPTY)
	 * ?Integer.parseInt(map.get("AOPKey").toString()):0; if(aopkey!=1 && aopkey!=2
	 * && aopkey!=3 && aopkey!=4 && aopkey!=7 && aopkey!=8 && aopkey!=9 &&
	 * aopkey!=10 && aopkey!=11 && aopkey!=22) { if (map.get("PercentageValue") !=
	 * null && !"".equals(map.get("PercentageValue").toString()) &&
	 * !"0".equals(map.get("PercentageValue").toString())) { IsReferRuleTriggered =
	 * true; // break; } }
	 * 
	 * // CS if(aopkey==14) { if (map.get("PercentageValue") != null &&
	 * !"".equals(map.get("PercentageValue").toString()) &&
	 * !"0".equals(map.get("PercentageValue").toString())) { // Computer Related
	 * Services List itSupplementlist = SqlResources.getSqlMapProcessor(ctx).select(
	 * "SqlStmts.UserStatementManualBoQueriesgetITServicesDataList",ctx); if
	 * (itSupplementlist != null && itSupplementlist.size() > 0)
	 * IsReferRuleTriggered = true; // CS List List cslist =
	 * SqlResources.getSqlMapProcessor(ctx).select(
	 * "SqlStmts.computer-servicesviewgetITServicesSupp",ctx); if (cslist != null) {
	 * Map csmap = new HashMap(); for (int j = 0; j < cslist.size(); j++) { csmap =
	 * (HashMap) cslist.get(j); if
	 * (!"1".equals(csmap.get("ITServiceCategoryKey").toString())&&
	 * !"2".equals(csmap.get("ITServiceCategoryKey").toString()) &&
	 * !"3".equals(csmap.get("ITServiceCategoryKey").toString())) { if
	 * (csmap.get("RevenuesFromLastYear") != null &&
	 * !"".equals(csmap.get("RevenuesFromLastYear").toString()) &&
	 * !"0".equals(csmap.get("RevenuesFromLastYear").toString())) {
	 * IsReferRuleTriggered = true; // break; } if (csmap.get("EstimatedRevenue") !=
	 * null && !"".equals(csmap.get("EstimatedRevenue").toString()) &&
	 * !"0".equals(csmap.get("EstimatedRevenue").toString())) { IsReferRuleTriggered
	 * = true; // break; } }
	 * 
	 * } } } } // End CS } } // End AOP
	 * 
	 * // PolicyCoverage
	 * 
	 * //commented by sukhi Object objPC =
	 * SqlResources.getSqlMapProcessor(ctx).findByKey(
	 * "SqlStmts.BasicInfoviewCoveragePolicy", ctx); if (objPC != null && objPC
	 * instanceof Map) { Map map = (Map) objPC; Object objDeductible =
	 * removeAmountFormat(map .get("DeductibleAmount")); if (objDeductible != null
	 * && !"".equals(objDeductible.toString()) &&
	 * !"0".equals(objDeductible.toString())) { if
	 * (Integer.parseInt(objDeductible.toString()) > 10000) IsReferRuleTriggered =
	 * true; }
	 * 
	 * Object objAggregateLimit = removeAmountFormat(map.get("AggregateLimit"));
	 * Object objOccuranceLimit = removeAmountFormat(map.get("OccuranceLimit")); if
	 * ((objAggregateLimit != null && !"".equals(objAggregateLimit.toString()) &&
	 * !"0".equals(objAggregateLimit.toString())) && (objOccuranceLimit != null &&
	 * !"".equals(objOccuranceLimit.toString()) &&
	 * !"0".equals(objOccuranceLimit.toString()))) { if
	 * (Integer.parseInt(objAggregateLimit.toString()) > 1000000 ||
	 * Integer.parseInt(objOccuranceLimit.toString()) > 1000000)
	 * IsReferRuleTriggered = true; } } // End // End PolicyCoverage
	 * 
	 * Object objPolicyEffectiveDateRefer = RuleUtils.executeRule(ctx,
	 * "AccountantRule.isPolicyEffectiveDateRefer"); boolean
	 * flagPolicyEffectiveDateRefer = false; if (objPolicyEffectiveDateRefer
	 * instanceof Boolean) flagPolicyEffectiveDateRefer = (Boolean)
	 * objPolicyEffectiveDateRefer; if (flagPolicyEffectiveDateRefer)
	 * IsReferRuleTriggered = true;
	 * 
	 * Object objPolicyExpirationDateRefer = RuleUtils.executeRule(ctx,
	 * "AccountantRule.isPolicyExpirationDateRefer"); boolean
	 * flagPolicyExpirationDateRefer = false; if (objPolicyExpirationDateRefer
	 * instanceof Boolean) flagPolicyEffectiveDateRefer = (Boolean)
	 * objPolicyExpirationDateRefer; if (flagPolicyExpirationDateRefer)
	 * IsReferRuleTriggered = true;
	 * 
	 * commented by sukhi Object objPriorActDateRefer = RuleUtils.executeRule(ctx,
	 * "AccountantRule.isPriorActDateRefer"); boolean flagPriorActDateRefer = false;
	 * if (objPriorActDateRefer instanceof Boolean) flagPriorActDateRefer =
	 * (Boolean) objPriorActDateRefer; if (flagPriorActDateRefer)
	 * IsReferRuleTriggered = true;
	 * 
	 * end
	 * 
	 * Object objPolicyExpirationDateProssRefer = RuleUtils.executeRule(ctx,
	 * "AccountantRule.isPolicyExpirationDateProssRefer"); boolean
	 * flagPolicyExpirationDateProssRefer = false; if
	 * (objPolicyExpirationDateProssRefer instanceof Boolean)
	 * flagPolicyExpirationDateProssRefer = (Boolean)
	 * objPolicyExpirationDateProssRefer; if (flagPolicyExpirationDateProssRefer)
	 * IsReferRuleTriggered = true;
	 * 
	 * if(ctx.get("PolicyExpirationDatePross")!=null &&
	 * !ctx.get("PolicyExpirationDatePross").toString().equals("")){ //int
	 * daysDifferenceForEfectiveDate=(int) (DateUtils.calculateDiffInDays(new
	 * Date(new SimpleDateFormat("MM/dd/yyyy").format(new
	 * Date(ctx.get("PolicyExpirationDatePross").toString()))),new Date(new
	 * SimpleDateFormat("MM/dd/yyyy").format(new Date())))+1); int
	 * daysDifferenceForEfectiveDate=(int) (DateUtils.calculateDiffInDays(new
	 * Date(ctx.get("PolicyExpirationDatePross").toString()),new Date()));
	 * if(daysDifferenceForEfectiveDate>=0 || daysDifferenceForEfectiveDate<-60)
	 * IsReferRuleTriggered = true;
	 * 
	 * int daysDifferenceForExpiryDate=(int) (DateUtils.calculateDiffInDays(new
	 * Date(new SimpleDateFormat("MM/dd/yyyy").format(new
	 * Date(ctx.get("PolicyExpirationDatePross").toString()))),new Date(new
	 * SimpleDateFormat("MM/dd/yyyy").format(new
	 * Date(ctx.get("PolicyEffectiveDate").toString())))));
	 * if(daysDifferenceForExpiryDate!=0) IsReferRuleTriggered = true; }
	 * 
	 * Context newCtx=new Context(); newCtx.setProject(ctx.getProject());
	 * newCtx.put("PolicyKey",ctx.get("PolicyKey"));
	 * newCtx.put("VersionSequence",ctx.get("VersionSequence"));
	 * newCtx.put("TransactionSequence",ctx.get("TransactionSequence"));
	 * newCtx.put("CoverageSequence",ctx.get("CoverageSequence"));
	 * 
	 * List limitSequence=SqlResources.getSqlMapProcessor(newCtx).select(
	 * "SqlStmts.UserStatementManualBoQueriesgetPolicyCoverageDetails",newCtx);
	 * if(limitSequence!=null && limitSequence.size()>0) newCtx.putAll((Map)
	 * limitSequence.get(0));
	 * 
	 * List limitSequencePrior=SqlResources.getSqlMapProcessor(newCtx).select(
	 * "SqlStmts.UserStatementManualBoQueriesgetProfessionalLiabilityDetailList",
	 * newCtx); if(limitSequencePrior!=null && limitSequencePrior.size()>0)
	 * newCtx.putAll((Map) limitSequencePrior.get(0));
	 * 
	 * if(newCtx.get("LimitSequence")!=null &&
	 * newCtx.get("LimitSequencePross")!=null){ int
	 * limitSequenceDifference=Integer.valueOf(newCtx.get("LimitSequence").toString(
	 * ))-Integer.valueOf(newCtx.get("LimitSequencePross").toString());
	 * 
	 * commented by sukhi if(limitSequenceDifference>2) IsReferRuleTriggered = true;
	 * 
	 * added by sukhi if(limitSequenceDifference>2 || limitSequenceDifference<-2)
	 * IsReferRuleTriggered = true; }
	 * 
	 * 
	 * //Raghu if(newCtx.get("FirmYear")!=null ) { int
	 * firmYear=newCtx.get("FirmYear")!=null?Integer.valueOf(newCtx.get("FirmYear").
	 * toString()):0; if(firmYear < 10) IsReferRuleTriggered = true; }
	 * 
	 * //Business or Fund Supplement Object fundObject =
	 * SqlResources.getSqlMapProcessor(ctx).findByKey(
	 * "FundsControlledSupp.findByKey", ctx); if (fundObject != null && fundObject
	 * instanceof Map) { Map map = (Map) fundObject; int total =
	 * map.get("TotlaNumberOfClientsFc") != null ?
	 * Integer.parseInt(map.get("TotlaNumberOfClientsFc").toString()) : 0; if(total
	 * > 0) IsReferRuleTriggered = true;
	 * 
	 * total = map.get("TotalAmountOfClientFundsFc") != null ?
	 * Integer.parseInt(map.get("TotalAmountOfClientFundsFc").toString()) : 0;
	 * if(total > 0) IsReferRuleTriggered = true;
	 * 
	 * edited by sukhi if (map.get("HaveInvestmentAuthorityFC") != null &&
	 * "Y".equals(map.get("HaveInvestmentAuthorityFC").toString()))
	 * IsReferRuleTriggered = true; end
	 * 
	 * if (map.get("IsControlFundsForBusinessAndEntimentFc") != null &&
	 * "Y".equals(map.get("IsControlFundsForBusinessAndEntimentFc").toString()))
	 * IsReferRuleTriggered = true;
	 * 
	 * if (map.get("IsCountersignatureRequiredFc") != null &&
	 * "N".equals(map.get("IsCountersignatureRequiredFc").toString()))
	 * IsReferRuleTriggered = true;
	 * 
	 * if (map.get("IsBankAccountReconciledFc") != null &&
	 * "N".equals(map.get("IsBankAccountReconciledFc").toString()))
	 * IsReferRuleTriggered = true;
	 * 
	 * if (map.get("IsManagementServiceProvided") != null &&
	 * "Y".equals(map.get("IsManagementServiceProvided").toString()))
	 * IsReferRuleTriggered = true;
	 * 
	 * if (map.get("IsFirmPersonnelBusinessManagerFc") != null &&
	 * "Y".equals(map.get("IsFirmPersonnelBusinessManagerFc").toString()))
	 * IsReferRuleTriggered = true;
	 * 
	 * } Map obj_code = (Map)SqlResources.getSqlMapProcessor(ctx).findByKey(
	 * "SqlStmts.UserStatementManualBoQueriesgetManualPremiumIssuedPolicyCount",
	 * ctx); if(obj_code != null){ int
	 * isNotShowPremiumQuoteCount=obj_code.get("isNotShowPremiumQuoteCount")!=null?
	 * Integer.parseInt(obj_code.get("isNotShowPremiumQuoteCount").toString()):0;
	 * if(isNotShowPremiumQuoteCount>0) IsReferRuleTriggered = true; }
	 * 
	 * // Referred States // if (ctx.get("StateCode") != null) { // // for (int i =
	 * 0; i < referredStates.length; i++) { // if
	 * (ctx.get("StateCode").toString().equals( // referredStates[i])) { //
	 * IsReferRuleTriggered = true; // break; // } // } // }
	 * 
	 * // Do not show Quote Option if Policy is in Under Review Status . ie 2 //
	 * String statusKey = ctx.get("StatusKey") != null ?
	 * ctx.get("StatusKey").toString() : ""; // if("2".equals(statusKey)) //
	 * IsReferRuleTriggered = true; //IsReferRuleTriggered = false; //
	 * ctx.put("referRuleTriggered", referRuleTriggered); if(referralCounts>0)
	 * IsReferRuleTriggered = true; if (IsReferRuleTriggered) {
	 * ctx.put("IsFirmReferred", "Y"); } else { ctx.put("IsFirmReferred", "N"); }
	 * //ctx.put("IsFirmReferred", "Y");
	 * SqlResources.getSqlMapProcessor(ctx).update(
	 * "SqlStmts.UserStatementsaveAccountstmtsupdateFirmReferred", ctx);
	 * 
	 * 
	 * } else {
	 * 
	 * boolean isRenewPolicyReferred = true;
	 * 
	 * //Comment by client request ,reference quote number is JIRA PA-1454
	 * AccountantValidationUtils validationUtils = new AccountantValidationUtils();
	 * if("Y".equals(ctx.get("isReferred").toString())) isRenewPolicyReferred=true;
	 * if(!"0".equals(ctx.get("IndicationCount").toString())) isRenewPolicyReferred
	 * = true;
	 * 
	 * populateAllCoverageModifiers(ctx); int
	 * mod1=ctx.get("SchduleRatingModifier1")!=null &&
	 * !ctx.get("SchduleRatingModifier1").equals(HtmlConstants.EMPTY)
	 * ?Integer.parseInt(ctx.get("SchduleRatingModifier1").toString()):0; int
	 * mod2=ctx.get("SchduleRatingModifier2")!=null &&
	 * !ctx.get("SchduleRatingModifier2").equals(HtmlConstants.EMPTY)
	 * ?Integer.parseInt(ctx.get("SchduleRatingModifier2").toString()):0; int
	 * mod3=ctx.get("SchduleRatingModifier3")!=null &&
	 * !ctx.get("SchduleRatingModifier3").equals(HtmlConstants.EMPTY)
	 * ?Integer.parseInt(ctx.get("SchduleRatingModifier3").toString()):0; int
	 * mod4=ctx.get("SchduleRatingModifier4")!=null &&
	 * !ctx.get("SchduleRatingModifier4").equals(HtmlConstants.EMPTY)
	 * ?Integer.parseInt(ctx.get("SchduleRatingModifier4").toString()):0; int
	 * mod5=ctx.get("SchduleRatingModifier5")!=null &&
	 * !ctx.get("SchduleRatingModifier5").equals(HtmlConstants.EMPTY)
	 * ?Integer.parseInt(ctx.get("SchduleRatingModifier5").toString()):0; int
	 * mod6=ctx.get("SchduleRatingModifier6")!=null &&
	 * !ctx.get("SchduleRatingModifier6").equals(HtmlConstants.EMPTY)
	 * ?Integer.parseInt(ctx.get("SchduleRatingModifier6").toString()):0;
	 * 
	 * if(mod1!=0 || mod2!=0 || mod3!=0 || mod4!=0 || mod5!=0 || mod6!=0)
	 * isRenewPolicyReferred=true;
	 * 
	 * Object obj = SqlResources.getSqlMapProcessor(ctx).select(
	 * "SqlStmts.UserStatementrenewapplicationgetAllPolicyOfAnAccount", ctx); if
	 * (obj != null && obj instanceof List) { Context previousContext = new
	 * Context(); List policyList = (List) obj;
	 * 
	 * if (policyList != null && policyList.size() > 0) { Map policyMap = (Map)
	 * policyList.get(policyList.size() - 2); previousContext.putAll(ctx);
	 * previousContext.put("PolicyKey", policyMap.get("PolicyKey"));
	 * 
	 * Object objMap = SqlResources.getSqlMapProcessor(previousContext).findByKey(
	 * "SqlStmts.UserStatementprocessquotegetPolicyKeys", previousContext); if
	 * (objMap != null && objMap instanceof Map) { Map keyMap = (Map) objMap;
	 * previousContext.put("VersionSequence", keyMap.get("VersionSequence") != null
	 * ? keyMap.get("VersionSequence") : null); previousContext.put("TotalPremium",
	 * keyMap.get("TotalPremium") != null ? keyMap.get("TotalPremium") : null); } }
	 * 
	 * double nbTotalPremium = (previousContext.get("TotalPremium") == null ? 0 :
	 * Double.parseDouble(previousContext.get("TotalPremium").toString())); double
	 * rbTotalPremium = 0;
	 * 
	 * Object objCurrentMap = SqlResources.getSqlMapProcessor(ctx).findByKey(
	 * "SqlStmts.UserStatementprocessquotegetPolicyKeys", ctx); if (objCurrentMap !=
	 * null && objCurrentMap instanceof Map) { Map keyMap = (Map) objCurrentMap;
	 * rbTotalPremium = ((keyMap.get("TotalPremium") == null ||
	 * "".equals(keyMap.get("TotalPremium"))) ? 0 :
	 * Double.parseDouble(keyMap.get("TotalPremium").toString())); }
	 * 
	 * if(ctx.get("TotalPremium") != null && rbTotalPremium == 0){ rbTotalPremium =
	 * Double.parseDouble(ctx.get("TotalPremium").toString()); }
	 * 
	 * if(rbTotalPremium != 0){ double premuimDifference=Math.abs(rbTotalPremium -
	 * nbTotalPremium); double tenPercent=nbTotalPremium * .1; if((rbTotalPremium >
	 * 3000) || rbTotalPremium < (nbTotalPremium - tenPercent) || rbTotalPremium >
	 * (nbTotalPremium + tenPercent)){ isRenewPolicyReferred = true; } }
	 * if("NY".equalsIgnoreCase(ctx.get("StateCode").toString()) ||
	 * "NM".equalsIgnoreCase(ctx.get("StateCode").toString())) isRenewPolicyReferred
	 * = true; //commented by sukhi Object objPC =
	 * SqlResources.getSqlMapProcessor(ctx).findByKey(
	 * "SqlStmts.RenewBasicInfoviewCoveragePolicy", ctx); if (objPC != null && objPC
	 * instanceof Map) {
	 * 
	 * Map map = (Map) objPC; Object objDeductible =
	 * removeAmountFormat(map.get("DeductibleAmount")); if (objDeductible != null &&
	 * !"".equals(objDeductible.toString()) &&
	 * !"0".equals(objDeductible.toString())) { if
	 * (Integer.parseInt(objDeductible.toString()) > 10000) isRenewPolicyReferred =
	 * true; }
	 * 
	 * Object objAggregateLimit = removeAmountFormat(map.get("AggregateLimit"));
	 * Object objOccuranceLimit = removeAmountFormat(map.get("OccuranceLimit")); if
	 * ((objAggregateLimit != null && !"".equals(objAggregateLimit.toString()) &&
	 * !"0" .equals(objAggregateLimit.toString())) && (objOccuranceLimit != null &&
	 * !"".equals(objOccuranceLimit.toString()) &&
	 * !"0".equals(objOccuranceLimit.toString()))) { if
	 * (Integer.parseInt(objAggregateLimit.toString()) > 1000000 ||
	 * Integer.parseInt(objOccuranceLimit.toString()) > 1000000)
	 * isRenewPolicyReferred = true; } }
	 * 
	 * boolean auditTriggered=false,businessSuppTriggered=false; List
	 * previousPolicyKey= (List) SqlResources.getSqlMapProcessor(ctx).select(
	 * "SqlStmts.UserStatementdroolQueriesgetPreviousPolicyKey",ctx);
	 * ctx.putAll((Map) previousPolicyKey.get(0)); Context oldPolicyContext=new
	 * Context(); oldPolicyContext.setProject(ctx.getProject());
	 * oldPolicyContext.put("PolicyKey",ctx.get("PreviousPolicykey"));
	 * oldPolicyContext.put("VersionSequence",ctx.get("previousVersionSequence"));
	 * List statecodeOld = SqlResources.getSqlMapProcessor(oldPolicyContext).select(
	 * "SqlStmts.UserStatementdroolQueriesgetAddressDetails",oldPolicyContext);
	 * oldPolicyContext.putAll((Map) statecodeOld.get(0));
	 * if(!(ctx.get("StateCode").toString()).equalsIgnoreCase(oldPolicyContext.get(
	 * "StateCode").toString())){ isRenewPolicyReferred = true; }
	 * 
	 * List aopPreviousList = SqlResources .getSqlMapProcessor(previousContext)
	 * .select("SqlStmts.UserStatementsaveAccountstmtspopulateAOPFields",
	 * previousContext);
	 * 
	 * List aoplist = SqlResources .getSqlMapProcessor(ctx)
	 * .select("SqlStmts.UserStatementsaveAccountstmtspopulateAOPFields",ctx);
	 * 
	 * if (aopPreviousList != null && aoplist != null) { if ((aopPreviousList.size()
	 * == aoplist.size()) && (aopPreviousList.size() > 0 && aoplist.size() > 0)) {
	 * for (int i = 0; i < aoplist.size(); i++) { Map currentMap = (HashMap)
	 * aoplist.get(i); Map previousMap = (HashMap) aopPreviousList.get(i); long
	 * percentageValue = 0;
	 * 
	 * String aopPreviousKey = previousMap.get("AOPKey") != null ?
	 * previousMap.get("AOPKey").toString() : ""; String aopCurrentKey =
	 * currentMap.get("AOPKey") != null ? currentMap.get("AOPKey").toString() : "";
	 * 
	 * if ("5".equals(aopCurrentKey)) {
	 * 
	 * percentageValue = AccountantValidationUtils
	 * .convertStringToInteger(currentMap.get("PercentageValue") != null ?
	 * currentMap.get("PercentageValue").toString() : "0");
	 * 
	 * if (percentageValue > 0) isRenewPolicyReferred = true; }
	 * 
	 * //old policy flow rule -- commented by sukhi if (("6".equals(aopPreviousKey)
	 * && "6".equals(aopCurrentKey))) {
	 * 
	 * long previousPercentage = AccountantValidationUtils
	 * .convertStringToLong(previousMap.get("PercentageValue") != null ?
	 * previousMap.get("PercentageValue").toString(): "0"); long currentPercentage =
	 * AccountantValidationUtils
	 * .convertStringToLong(currentMap.get("PercentageValue") != null ?
	 * currentMap.get("PercentageValue").toString(): "0");
	 * 
	 * if(previousPercentage==0) { if(currentPercentage>0) isRenewPolicyReferred =
	 * true;
	 * 
	 * } if(previousPercentage>0) { if (currentPercentage - previousPercentage > 10)
	 * auditTriggered=true; } }
	 * 
	 * if ("6".equals(aopCurrentKey)) {
	 * 
	 * percentageValue = AccountantValidationUtils
	 * .convertStringToLong(currentMap.get("PercentageValue") != null ?
	 * currentMap.get("PercentageValue").toString(): "0");
	 * auditPercentage=percentageValue; if (percentageValue > 0)
	 * isRenewPolicyReferred = true; }
	 * 
	 * old policy flow rule -- commented by sukhi if ("11".equals(aopPreviousKey) &&
	 * "11".equals(aopCurrentKey)) {
	 * 
	 * long previousPercentage = AccountantValidationUtils
	 * .convertStringToLong(previousMap.get("PercentageValue") != null ?
	 * previousMap.get("PercentageValue").toString(): "0"); long currentPercentage =
	 * AccountantValidationUtils
	 * .convertStringToLong(currentMap.get("PercentageValue") != null ?
	 * currentMap.get("PercentageValue").toString(): "0");
	 * 
	 * if (currentPercentage - previousPercentage > 0) isRenewPolicyReferred = true;
	 * }
	 * 
	 * if (("13".equals(aopPreviousKey) && "13".equals(aopCurrentKey)) ||
	 * ("17".equals(aopPreviousKey) && "17".equals(aopCurrentKey)) ||
	 * ("24".equals(aopPreviousKey) && "24".equals(aopCurrentKey))) {
	 * 
	 * long previousPercentage = AccountantValidationUtils
	 * .convertStringToLong(previousMap.get("PercentageValue") != null ?
	 * previousMap.get("PercentageValue").toString(): "0"); long currentPercentage =
	 * AccountantValidationUtils
	 * .convertStringToLong(currentMap.get("PercentageValue") != null ?
	 * currentMap.get("PercentageValue").toString(): "0");
	 * 
	 * if(previousPercentage==0 && currentPercentage > 0) isRenewPolicyReferred =
	 * true; if(currentPercentage - previousPercentage > 10) isRenewPolicyReferred =
	 * true;
	 * 
	 * 
	 * 
	 * }
	 * 
	 * 
	 * if (("18".equals(aopPreviousKey) && "18".equals(aopCurrentKey))) {
	 * 
	 * long previousPercentage = AccountantValidationUtils
	 * .convertStringToLong(previousMap.get("PercentageValue") != null ?
	 * previousMap.get("PercentageValue").toString(): "0"); long currentPercentage =
	 * AccountantValidationUtils
	 * .convertStringToLong(currentMap.get("PercentageValue") != null ?
	 * currentMap.get("PercentageValue").toString(): "0");
	 * 
	 * if(previousPercentage==0) { if(currentPercentage>0) isRenewPolicyReferred =
	 * true;
	 * 
	 * } if(previousPercentage>0) { if (currentPercentage - previousPercentage > 10)
	 * businessSuppTriggered=true; } }
	 * 
	 * 
	 * if ( ("15".equals(aopPreviousKey) && "15".equals(aopCurrentKey))) { long
	 * previousPercentage = AccountantValidationUtils
	 * .convertStringToLong(previousMap.get("PercentageValue") != null ?
	 * previousMap.get("PercentageValue").toString(): "0"); long currentPercentage =
	 * AccountantValidationUtils
	 * .convertStringToLong(currentMap.get("PercentageValue") != null ?
	 * currentMap.get("PercentageValue").toString(): "0");
	 * 
	 * if (currentPercentage - previousPercentage > 25) isRenewPolicyReferred =
	 * true; } if ( ("16".equals(aopPreviousKey) && "16".equals(aopCurrentKey))) {
	 * long previousPercentage = AccountantValidationUtils
	 * .convertStringToLong(previousMap.get("PercentageValue") != null ?
	 * previousMap.get("PercentageValue").toString(): "0"); long currentPercentage =
	 * AccountantValidationUtils
	 * .convertStringToLong(currentMap.get("PercentageValue") != null ?
	 * currentMap.get("PercentageValue").toString(): "0");
	 * 
	 * if(previousPercentage==0 && currentPercentage>0) isRenewPolicyReferred =
	 * true; if (currentPercentage - previousPercentage > 5) isRenewPolicyReferred =
	 * true; } if ("12".equals(aopCurrentKey)&& "12".equals(aopPreviousKey)) {
	 * 
	 * int previousPercentage = AccountantValidationUtils
	 * .convertStringToInteger(previousMap.get("PercentageValue") != null ?
	 * previousMap.get("PercentageValue").toString() : "0"); int currentPercentage =
	 * AccountantValidationUtils
	 * .convertStringToInteger(currentMap.get("PercentageValue") != null ?
	 * currentMap.get("PercentageValue").toString() : "0");
	 * 
	 * if (currentPercentage > previousPercentage) isRenewPolicyReferred = true; }
	 * 
	 * // if ("18".equals(aopCurrentKey)) { // // percentageValue =
	 * AccountantValidationUtils // .convertStringToInteger(currentMap //
	 * .get("PercentageValue") != null ? currentMap // .get("PercentageValue") //
	 * .toString() // : "0"); // // if (percentageValue > 0) //
	 * isRenewPolicyReferred = true; // // } if ("19".equals(aopCurrentKey) ||
	 * "21".equals(aopCurrentKey)) { percentageValue = AccountantValidationUtils
	 * .convertStringToInteger(currentMap.get("PercentageValue") != null ?
	 * currentMap.get("PercentageValue").toString() : "0");
	 * 
	 * if (percentageValue > 0) isRenewPolicyReferred = true; } if
	 * ("20".equals(aopCurrentKey)) { long previousPercentage =
	 * AccountantValidationUtils
	 * .convertStringToLong(previousMap.get("PercentageValue") != null ?
	 * previousMap.get("PercentageValue").toString(): "0"); long currentPercentage =
	 * AccountantValidationUtils
	 * .convertStringToLong(currentMap.get("PercentageValue") != null ?
	 * currentMap.get("PercentageValue").toString(): "0");
	 * 
	 * if(currentPercentage - previousPercentage > 0) isRenewPolicyReferred = true;
	 * 
	 * String prevComment = previousMap.get("AOPCommentDesc") != null ?
	 * previousMap.get("AOPCommentDesc").toString().trim() : ""; String currComment
	 * = currentMap.get("AOPCommentDesc") != null ?
	 * currentMap.get("AOPCommentDesc").toString().trim() : "";
	 * if(!prevComment.equals(currComment)) isRenewPolicyReferred = true; } if
	 * ("23".equals(aopPreviousKey) && "23".equals(aopCurrentKey)) {
	 * 
	 * long previousPercentage = AccountantValidationUtils
	 * .convertStringToLong(previousMap.get("PercentageValue") != null ?
	 * previousMap.get("PercentageValue").toString(): "0"); long currentPercentage =
	 * AccountantValidationUtils
	 * .convertStringToLong(currentMap.get("PercentageValue") != null ?
	 * currentMap.get("PercentageValue").toString(): "0");
	 * 
	 * if(previousPercentage==0 && currentPercentage > 0) isRenewPolicyReferred =
	 * true; if(currentPercentage - previousPercentage > 10) isRenewPolicyReferred =
	 * true; if(currentPercentage> 25) isRenewPolicyReferred = true; } // CS
	 * 
	 * }
	 * 
	 * } }
	 * 
	 * 
	 * if (aoplist != null) { Map map = new HashMap(); int aopkey = 0; for (int i =
	 * 0; i < aoplist.size(); i++) { map = (HashMap) aoplist.get(i);
	 * aopkey=map.get("AOPKey")!=null &&
	 * !map.get("AOPKey").equals(HtmlConstants.EMPTY)
	 * ?Integer.parseInt(map.get("AOPKey").toString()):0;
	 * 
	 * commented by sukhi if(aopkey!=1 && aopkey!=2 && aopkey!=3 && aopkey!=4 &&
	 * aopkey!=7 && aopkey!=8 && aopkey!=9 && aopkey!=10 && aopkey!=11 && aopkey!=14
	 * && aopkey!=22) if (map.get("PercentageValue") != null &&
	 * !"".equals(map.get("PercentageValue").toString()) &&
	 * !"0".equals(map.get("PercentageValue").toString())) { isRenewPolicyReferred =
	 * true; }
	 * 
	 * // Computer Supplement if(aopkey==14) { if (map.get("PercentageValue") !=
	 * null && !"".equals(map.get("PercentageValue").toString()) &&
	 * !"0".equals(map.get("PercentageValue").toString())) { List itSupplementlist =
	 * SqlResources.getSqlMapProcessor(ctx).select(
	 * "SqlStmts.UserStatementManualBoQueriesgetITServicesDataList",ctx); if
	 * (itSupplementlist != null && itSupplementlist.size() > 0)
	 * isRenewPolicyReferred = true; } } } }
	 * 
	 * commented by sukhi List ITServiceListPrevious =
	 * SqlResources.getSqlMapProcessor(
	 * previousContext).select("SqlStmts.computer-servicesviewgetITServicesSupp",
	 * previousContext);
	 * 
	 * 
	 * if (ITServiceListCurrent != null && ITServiceListCurrent.size() > 0) { if
	 * (ITServiceListPrevious != null && ITServiceListPrevious.size() > 0) { for
	 * (int i = 0; i < ITServiceListCurrent.size(); i++) { Map previousMap = (Map)
	 * ITServiceListPrevious.get(i); Map currentMap = (Map)
	 * ITServiceListCurrent.get(i);
	 * 
	 * String ITServiceCategoryKeyPrevious = previousMap
	 * .get("ITServiceCategoryKey") != null ?
	 * previousMap.get("ITServiceCategoryKey").toString() : ""; String
	 * ITServiceCategoryKeyCurrent = currentMap .get("ITServiceCategoryKey") != null
	 * ? currentMap.get("ITServiceCategoryKey").toString() : "";
	 * 
	 * if (("3".equals(ITServiceCategoryKeyPrevious) &&
	 * "3".equals(ITServiceCategoryKeyCurrent)) ||
	 * ("4".equals(ITServiceCategoryKeyPrevious) &&
	 * "4".equals(ITServiceCategoryKeyCurrent)) ||
	 * ("5".equals(ITServiceCategoryKeyPrevious) &&
	 * "5".equals(ITServiceCategoryKeyCurrent)) ||
	 * ("6".equals(ITServiceCategoryKeyPrevious) &&
	 * "6".equals(ITServiceCategoryKeyCurrent))) {
	 * 
	 * int revenueFromLastYearPrevious =
	 * AccountantValidationUtils.convertStringToInteger(previousMap.get(
	 * "RevenuesFromLastYear") != null ?
	 * previousMap.get("RevenuesFromLastYear").toString(): "0"); int
	 * revenueFromLastYearCurrent =
	 * AccountantValidationUtils.convertStringToInteger(currentMap.get(
	 * "RevenuesFromLastYear") != null ?
	 * currentMap.get("RevenuesFromLastYear").toString(): "0");
	 * 
	 * if (revenueFromLastYearPrevious != revenueFromLastYearCurrent)
	 * isRenewPolicyReferred = true;
	 * 
	 * int estimatedRevenuePrevious =
	 * AccountantValidationUtils.convertStringToInteger(previousMap.get(
	 * "EstimatedRevenue") != null ? previousMap.get("EstimatedRevenue").toString():
	 * "0"); int estimatedRevenueCurrent =
	 * AccountantValidationUtils.convertStringToInteger(currentMap.get(
	 * "EstimatedRevenue") != null ? currentMap.get("EstimatedRevenue").toString():
	 * "0");
	 * 
	 * if (estimatedRevenuePrevious != estimatedRevenueCurrent)
	 * isRenewPolicyReferred = true;
	 * 
	 * String isAnnuallyPrevious = previousMap.get("IsAnnually") != null ?
	 * previousMap.get("IsAnnually").toString(): ""; String isAnnuallyCurrent =
	 * currentMap.get("IsAnnually") != null ?
	 * currentMap.get("IsAnnually").toString() : "";
	 * 
	 * if (!isAnnuallyPrevious.equals(isAnnuallyCurrent)) isRenewPolicyReferred =
	 * true; } } } else { for (int i = 0; i < ITServiceListCurrent.size(); i++) {
	 * Map currentMap = (Map) ITServiceListCurrent.get(i); String
	 * ITServiceCategoryKeyCurrent = currentMap.get("ITServiceCategoryKey") != null
	 * ? currentMap.get("ITServiceCategoryKey").toString(): "";
	 * 
	 * if ("3".equals(ITServiceCategoryKeyCurrent) ||
	 * "4".equals(ITServiceCategoryKeyCurrent) ||
	 * "5".equals(ITServiceCategoryKeyCurrent) ||
	 * "6".equals(ITServiceCategoryKeyCurrent)) {
	 * 
	 * int revenueFromLastYearCurrent =
	 * AccountantValidationUtils.convertStringToInteger(currentMap.get(
	 * "RevenuesFromLastYear") != null ?
	 * currentMap.get("RevenuesFromLastYear").toString(): "0");
	 * 
	 * if (revenueFromLastYearCurrent > 0) isRenewPolicyReferred = true;
	 * 
	 * int estimatedRevenueCurrent =
	 * AccountantValidationUtils.convertStringToInteger(currentMap.get(
	 * "EstimatedRevenue") != null ? currentMap.get("EstimatedRevenue").toString():
	 * "0");
	 * 
	 * if (estimatedRevenueCurrent > 0) isRenewPolicyReferred = true;
	 * 
	 * String isAnnuallyCurrent = currentMap.get("IsAnnually") != null ?
	 * currentMap.get("IsAnnually").toString() : "";
	 * 
	 * if (!isAnnuallyCurrent.equals("")) isRenewPolicyReferred = true; } } } }
	 * 
	 * new List ITServiceListCurrent = SqlResources .getSqlMapProcessor(ctx).select(
	 * "SqlStmts.computer-servicesviewgetITServicesSupp",ctx); for (int i = 0; i <
	 * ITServiceListCurrent.size(); i++) { Map currentMap = (Map)
	 * ITServiceListCurrent.get(i); String ITServiceCategoryKeyCurrent =
	 * currentMap.get("ITServiceCategoryKey") != null ?
	 * currentMap.get("ITServiceCategoryKey").toString(): "";
	 * 
	 * if ("3".equals(ITServiceCategoryKeyCurrent) ||
	 * "4".equals(ITServiceCategoryKeyCurrent) ||
	 * "5".equals(ITServiceCategoryKeyCurrent) ||
	 * "6".equals(ITServiceCategoryKeyCurrent)) {
	 * 
	 * int revenueFromLastYearCurrent =
	 * AccountantValidationUtils.convertStringToInteger(currentMap.get(
	 * "RevenuesFromLastYear") != null ?
	 * currentMap.get("RevenuesFromLastYear").toString(): "0");
	 * 
	 * if (revenueFromLastYearCurrent > 0) isRenewPolicyReferred = true;
	 * 
	 * int estimatedRevenueCurrent =
	 * AccountantValidationUtils.convertStringToInteger(currentMap.get(
	 * "EstimatedRevenue") != null ? currentMap.get("EstimatedRevenue").toString():
	 * "0");
	 * 
	 * if (estimatedRevenueCurrent > 0) isRenewPolicyReferred = true;
	 * 
	 * String isAnnuallyCurrent = currentMap.get("IsAnnually") != null ?
	 * currentMap.get("IsAnnually").toString() : "";
	 * 
	 * if (!isAnnuallyCurrent.equals("")) isRenewPolicyReferred = true; } }
	 * 
	 * 
	 * Object objAddress = SqlResources.getSqlMapProcessor(ctx).findByKey(
	 * "SqlStmts.BasicInfoviewgetAddressdetails", ctx); if(objAddress!=null) {
	 * boolean containsPOBOX=false; Map mapAddress = null; if (objAddress != null)
	 * mapAddress = (Map) objAddress;
	 * 
	 * String addressPOBox = ""; String address1 = "";
	 * 
	 * for (int i = 0; i < mapAddress.size(); i++) { Map mapIssueMail = (Map)
	 * mapAddress.get(i); address1 = mapAddress.get("Address1") != null ?
	 * mapAddress.get("Address1").toString().trim().toLowerCase() : ""; }
	 * 
	 * String sentence1=address1.toString().replaceAll("[-,., ,,,]",""); String
	 * search="pobox";
	 * 
	 * if (sentence1.toLowerCase().indexOf(search.toLowerCase()) != -1 ) {
	 * containsPOBOX=true; } if(containsPOBOX) isRenewPolicyReferred = true; }
	 * 
	 * Object objPolicyEffectiveDatePastRefer = RuleUtils.executeRule(ctx,
	 * "AccountantRule.isPolicyEffectiveDatePastRefer"); boolean
	 * flagPolicyEffectiveDatePastRefer = false; if (objPolicyEffectiveDatePastRefer
	 * instanceof Boolean) flagPolicyEffectiveDatePastRefer = (Boolean)
	 * objPolicyEffectiveDatePastRefer; if (flagPolicyEffectiveDatePastRefer)
	 * isRenewPolicyReferred = true;
	 * 
	 * long totalFirmPersonnel=0; Object objMapFirm =
	 * SqlResources.getSqlMapProcessor(ctx).findByKey(
	 * "SqlStmts.generalviewgetFirmDetails", ctx); if (objMapFirm != null &&
	 * objMapFirm instanceof Map) { Map keyMapFirm = (Map) objMapFirm;
	 * totalFirmPersonnel= keyMapFirm.get("TotalFirmPersonnel") != null ?
	 * AccountantValidationUtils
	 * .convertStringToLong(keyMapFirm.get("TotalFirmPersonnel").toString()) : 0; if
	 * (totalFirmPersonnel > 10) isRenewPolicyReferred = true; }
	 * 
	 * commented by sukhi if
	 * (AccountantValidationUtils.checkIfAccountNameIsChanged(ctx))
	 * isRenewPolicyReferred = true;
	 * 
	 * if(AccountantValidationUtils.isManualInPriorYear(previousContext))
	 * isRenewPolicyReferred = true ;
	 * 
	 * Object objFirm =
	 * SqlResources.getSqlMapProcessor(ctx).findByKey("Firm.findByKey", ctx); if
	 * (objFirm != null && objFirm instanceof Map) {
	 * 
	 * Map objMap = (Map) objFirm;
	 * 
	 * String IsAnyFirmMeargedOrAcquired = objMap.get("IsAnyFirmMeargedOrAcquired")
	 * != null ? objMap.get("IsAnyFirmMeargedOrAcquired").toString() : ""; if
	 * (IsAnyFirmMeargedOrAcquired.equals("Y")) isRenewPolicyReferred = true;
	 * 
	 * String IsControlClientFunds = objMap .get("IsControlClientFunds") != null ?
	 * objMap.get( "IsControlClientFunds").toString() : ""; if
	 * (IsControlClientFunds.equals("Y")) isRenewPolicyReferred = true;
	 * 
	 * String IsServedAsTrustee = objMap.get("IsServedAsTrustee") != null ? objMap
	 * .get("IsServedAsTrustee").toString() : ""; if (IsServedAsTrustee.equals("Y"))
	 * isRenewPolicyReferred = true;
	 * 
	 * String IsFirmRenderedServiceForAnyClient = objMap
	 * .get("IsFirmRenderedServiceForAnyClient") != null ? objMap
	 * .get("IsFirmRenderedServiceForAnyClient") .toString() : ""; if
	 * (IsFirmRenderedServiceForAnyClient.equals("Y")) isRenewPolicyReferred = true;
	 * 
	 * String IsSuitInstituted = objMap.get("IsSuitInstituted") != null ? objMap
	 * .get("IsSuitInstituted").toString() : ""; if (IsSuitInstituted.equals("Y"))
	 * isRenewPolicyReferred = true;
	 * 
	 * String IsFirmsRevenueMorethan25Percent = objMap
	 * .get("IsFirmsRevenueMorethan25Percent") != null ? objMap
	 * .get("IsFirmsRevenueMorethan25Percent").toString() : ""; if
	 * (IsFirmsRevenueMorethan25Percent.equals("Y")) isRenewPolicyReferred = true;
	 * 
	 * String IsAnyRegulatoryInquiry = objMap .get("IsAnyRegulatoryInquiry") != null
	 * ? objMap .get("IsAnyRegulatoryInquiry").toString() : ""; if
	 * (IsAnyRegulatoryInquiry.equals("Y")) isRenewPolicyReferred = true;
	 * 
	 * String IsAwarenessOfAnyProfLiability = objMap
	 * .get("IsAwarenessOfAnyProfLiability") != null ? objMap
	 * .get("IsAwarenessOfAnyProfLiability").toString() : ""; if
	 * (IsAwarenessOfAnyProfLiability.equals("Y")) isRenewPolicyReferred = true;
	 * 
	 * String IsAwarenessOfAnyProfLiabilitySuitAgainst = objMap
	 * .get("IsAwarenessOfAnyProfLiabilitySuitAgainst") != null ? objMap
	 * .get("IsAwarenessOfAnyProfLiabilitySuitAgainst") .toString() : ""; if
	 * (IsAwarenessOfAnyProfLiabilitySuitAgainst.equals("Y")) isRenewPolicyReferred
	 * = true;
	 * 
	 * 
	 * String IsCommissionRecieved = objMap .get("IsCommissionRecieved") != null ?
	 * objMap.get( "IsCommissionRecieved").toString() : "";
	 * 
	 * if ("Y".equals(IsCommissionRecieved)) isRenewPolicyReferred = true;
	 * 
	 * }
	 * 
	 * Commented by sukhi if
	 * (AccountantValidationUtils.checkIfValueInAuditWork(ctx))
	 * isRenewPolicyReferred = true; if(auditTriggered==true){ Object
	 * objRenewAttestation =
	 * SqlResources.getSqlMapProcessor(ctx).findByKey("Attestation.findByKey", ctx);
	 * if (objRenewAttestation != null && objRenewAttestation instanceof Map) { Map
	 * map = (Map) objRenewAttestation; int FirmRenderedAuditAtt =
	 * map.get("FirmRenderedAuditAtt") != null ? Integer
	 * .parseInt(map.get("FirmRenderedAuditAtt").toString()) : 0; if
	 * (FirmRenderedAuditAtt > 0) isRenewPolicyReferred = true;
	 * 
	 * New Added By Sukhi
	 * 
	 * String IsAualityReviewAtt = map.get("IsAualityReviewAtt") != null ?
	 * map.get("IsAualityReviewAtt").toString() : ""; if
	 * ("N".equals(IsAualityReviewAtt)) isRenewPolicyReferred = true;
	 * 
	 * String IsLastPeerReviewAtt = map.get("IsLastPeerReviewAtt") != null ?
	 * map.get("IsLastPeerReviewAtt").toString() : ""; if
	 * ("N".equals(IsLastPeerReviewAtt)) isRenewPolicyReferred = true; }
	 * 
	 * if (objFirm != null && objFirm instanceof Map) {
	 * 
	 * Map objMap = (Map) objFirm; String IsSyndicationManagers = objMap
	 * .get("IsSyndicationManagers") != null ? objMap.get(
	 * "IsSyndicationManagers").toString() : "";
	 * 
	 * if ("Y".equals(IsSyndicationManagers)) isRenewPolicyReferred = true;
	 * 
	 * String IsDebtOrEquityFinancing = objMap .get("IsDebtOrEquityFinancing") !=
	 * null ? objMap .get("IsDebtOrEquityFinancing").toString() : "";
	 * 
	 * if ("Y".equals(IsDebtOrEquityFinancing)) isRenewPolicyReferred = true;
	 * 
	 * }
	 * 
	 * }
	 * 
	 * if(businessSuppTriggered==true) { Object objBusiness =
	 * SqlResources.getSqlMapProcessor(ctx).findByKey(
	 * "FundsControlledSupp.findByKey", ctx); if (objBusiness != null && objBusiness
	 * instanceof Map) { Map objMap = (Map) objBusiness;
	 * 
	 * String HaveInvestmentAuthorityFC = objMap .get("HaveInvestmentAuthorityFC")
	 * != null ? objMap .get("HaveInvestmentAuthorityFC").toString() : ""; if
	 * ("Y".equals(HaveInvestmentAuthorityFC)) isRenewPolicyReferred = true;
	 * 
	 * String IsControlFundsForBusinessAndEntimentFc = objMap
	 * .get("IsControlFundsForBusinessAndEntimentFc") != null ? objMap
	 * .get("IsControlFundsForBusinessAndEntimentFc") .toString() : ""; if
	 * ("Y".equals(IsControlFundsForBusinessAndEntimentFc)) isRenewPolicyReferred =
	 * true;
	 * 
	 * String IsFirmPersonnelBusinessManagerFc = objMap
	 * .get("IsFirmPersonnelBusinessManagerFc") != null ? objMap
	 * .get("IsFirmPersonnelBusinessManagerFc").toString() : ""; if
	 * ("Y".equals(IsFirmPersonnelBusinessManagerFc)) isRenewPolicyReferred = true;
	 * 
	 * New Added By Sukhi String IsCountersignatureRequiredFc = objMap
	 * .get("IsCountersignatureRequiredFc") != null ? objMap
	 * .get("IsCountersignatureRequiredFc").toString() : ""; if
	 * ("N".equals(IsCountersignatureRequiredFc)) isRenewPolicyReferred = true;
	 * 
	 * String IsBankAccountReconciledFc = objMap .get("IsBankAccountReconciledFc")
	 * != null ? objMap .get("IsBankAccountReconciledFc").toString() : ""; if
	 * ("N".equals(IsBankAccountReconciledFc)) isRenewPolicyReferred = true;
	 * 
	 * String IsManagementServiceProvided = objMap
	 * .get("IsManagementServiceProvided") != null ? objMap
	 * .get("IsManagementServiceProvided").toString() : ""; if
	 * ("Y".equals(IsManagementServiceProvided)) isRenewPolicyReferred = true;
	 * 
	 * End new } } commented by sukhi long previousAverageRevenue = 0; Object
	 * objpmap =
	 * SqlResources.getSqlMapProcessor(previousContext).findByKey("Firm.findByKey",
	 * previousContext); if (objpmap != null && objpmap instanceof Map) { Map
	 * firmMap = (Map) objpmap; previousAverageRevenue =
	 * firmMap.get("AverageRevenue") != null ? AccountantValidationUtils
	 * .convertStringToLong(firmMap.get("AverageRevenue").toString()) : 0; }
	 * 
	 * long currentAverageRevenue = 0; Object objcmap =
	 * SqlResources.getSqlMapProcessor(ctx).findByKey("Firm.findByKey", ctx); if
	 * (objcmap != null && objcmap instanceof Map) { Map firmMap = (Map) objcmap;
	 * currentAverageRevenue = firmMap.get("AverageRevenue") != null ?
	 * AccountantValidationUtils
	 * .convertStringToLong(firmMap.get("AverageRevenue").toString()) : 0; }
	 * 
	 * double revenuePercent = getPercentIncrease(previousAverageRevenue,
	 * currentAverageRevenue); if (revenuePercent >= 50) isRenewPolicyReferred =
	 * true;
	 * 
	 * if(currentAverageRevenue > 2000000 || currentAverageRevenue < 20000 )
	 * isRenewPolicyReferred = true;
	 * 
	 * added by sukhi long previousAverageRevenue = 0; Object objpmap =
	 * SqlResources.getSqlMapProcessor(previousContext).select(
	 * "SqlStmts.UserStatementrenewapplicationgetGrossRevenueYearofPreviousPolicy",
	 * previousContext); if (objpmap != null && objpmap instanceof List) { List
	 * firmlist = (List) objpmap; if (firmlist != null && firmlist.size() > 0) for
	 * (int i = 0; i < firmlist.size(); i++) { Map firmMap = (Map) firmlist.get(i);
	 * previousAverageRevenue = firmMap.get("NBAmount") != null ?
	 * AccountantValidationUtils
	 * .convertStringToLong(firmMap.get("NBAmount").toString()) : 0; } }
	 * 
	 * long currentAverageRevenue = 0; Object objcmap =
	 * SqlResources.getSqlMapProcessor(ctx).select(
	 * "SqlStmts.UserStatementrenewapplicationgetGrossRevenueForFirmRBData", ctx);
	 * if (objcmap != null && objcmap instanceof List) { List firmlist = (List)
	 * objcmap; if (firmlist != null && firmlist.size() > 0) for (int i = 0; i <
	 * firmlist.size(); i++) { Map firmMap = (Map) firmlist.get(i);
	 * currentAverageRevenue = firmMap.get("Amount") != null ?
	 * AccountantValidationUtils
	 * .convertStringToLong(firmMap.get("Amount").toString()) : 0; } }
	 * 
	 * if((currentAverageRevenue != previousAverageRevenue)&&(currentAverageRevenue
	 * > 2000000 || currentAverageRevenue < 20000)) isRenewPolicyReferred = true;
	 * 
	 * // Referred States // if (ctx.get("StateCode") != null) { // // for (int i =
	 * 0; i < referredStates.length; i++) { // if
	 * (ctx.get("StateCode").toString().equals( // referredStates[i])) { //
	 * isRenewPolicyReferred = true; // break; // } // } // }
	 * 
	 * // Do not show Quote option to insured if policy is in under review status //
	 * String statusKey = ctx.get("StatusKey") != null ?
	 * ctx.get("StatusKey").toString() : ""; // if("2".equals(statusKey)) //
	 * isRenewPolicyReferred = true;
	 * 
	 * previousContext.clear(); Map obj_code =
	 * (Map)SqlResources.getSqlMapProcessor(ctx).findByKey(
	 * "SqlStmts.UserStatementManualBoQueriesgetManualPremiumIssuedPolicyCount",
	 * ctx); if(obj_code != null){ int
	 * isNotShowPremiumQuoteCount=obj_code.get("isNotShowPremiumQuoteCount")!=null?
	 * Integer.parseInt(obj_code.get("isNotShowPremiumQuoteCount").toString()):0;
	 * if(isNotShowPremiumQuoteCount>0) isRenewPolicyReferred = true; }
	 * if(referralCounts>0) isRenewPolicyReferred = true; if (isRenewPolicyReferred)
	 * { ctx.put("referRuleTriggered", "Y"); ctx.put("IsFirmReferred", "Y"); } else
	 * { ctx.put("referRuleTriggered", "N"); ctx.put("IsFirmReferred", "N"); }
	 * 
	 * SqlResources.getSqlMapProcessor(ctx).update(
	 * "SqlStmts.UserStatementrenewapplicationupdateIsRenewFirmReferred",ctx);
	 * 
	 * //} Comment by client request ,reference quote number is JIRA PA-1454 } }
	 */

	public void testing(IContext ctx) throws Exception {
		// System.out.println("testinh-----------" + ctx.get("AccountKey"));
		// System.out.println("testinh-----------" +
		// ctx.get("CoverageSequence"));
		// ctx.get("IsTaxCalculation");
		// ctx.get("StateCode");
		// ctx.get("PolicyEffectiveDate");
		// ctx.get("IsQuotePage");
		// ctx.get("AccountName");
		// ctx.get("AccountEmail");
		// ctx.get("isSigned");
		// ctx.get("CreatedType");
		// ctx.get("ESModeCopy");
		// ctx.get("SignedDocumentID");
		// ctx.get("SignedDocumentToken");
		// ctx.get("SignatoryID");
		// ctx.get("SignedAuthToken");
		// ctx.get("OrderNumber");
		// ctx.get("DocumentStatus");
		// ctx.get("PriorActDatePross");
		// ctx.get("PolicyKey");
		// ctx.get("TransactionSequence");
		// ctx.get("QuoteEffectiveDate");
		// ctx.get("UserNo");
		{ if (logger.isDebugEnabled()) logger.debug(ctx.get("NameOfClaimantCi") + "\t" + ctx.get("IsClientCi")); }
		ctx.get("NameOfClaimantCi");
		ctx.get("WeekFromDate");
		ctx.get("WeekToDate");
		ctx.get("ProjectType");
		ctx.get("FirstReviewUserID");
		ctx.get("SumTotal");
	}

	//

	public static float getPercentIncrease(long previousVal, long currentValue) throws Exception {
		if (previousVal == 0 && currentValue > 0) {
			return 100;
		} else if (previousVal != 0) {
			if (currentValue > previousVal) {
				float difference = currentValue - previousVal;
				float percentIncrease = difference / previousVal;
				percentIncrease = percentIncrease * 100;

				return percentIncrease;
			}
		}
		return 0;
	}

	public static float getPercentDecrease(int previousVal, int currentValue) throws Exception {
		if (previousVal != 0) {
			if (previousVal > currentValue) {
				float difference = previousVal - currentValue;
				float percentdecrease = difference / previousVal;
				percentdecrease = percentdecrease * 100;

				return percentdecrease;
			}
		}
		return 0;
	}

	public static synchronized String generatePolicyNumber(IContext ctx) throws Exception {

		String prefix = "570";
		String initialPolicyNo = null;
		Object objPNo = SqlResources.getSqlMapProcessor(ctx)
				.findByKey("SqlStmts.UserStatementprocessquotegetMaxPolicyNo", ctx);
		if (objPNo != null && objPNo instanceof Map) {
			Map map = (Map) objPNo;
			if (map.get("PolicyNumber") != null) {
				String PolicyNumber = map.get("PolicyNumber").toString();
				{ if (logger.isDebugEnabled()) logger.debug(
						"LAST MAX POLICY NUMBER:-" + PolicyNumber + " LAST UPDATED AT" + System.currentTimeMillis()); }
				int index = PolicyNumber.indexOf("-");
				// int lastIndex = PolicyNumber.lastIndexOf("-");

				String number = PolicyNumber.substring(index + 1, index + 7);

				int policyNo = Integer.parseInt(number);
				policyNo = policyNo + 1;
				initialPolicyNo = new Integer(policyNo).toString();
			}
		}

		if (initialPolicyNo == null)
			initialPolicyNo = SystemProperties.getInstance().getString("appl." + ctx.getProject() + ".initialPolicyNo");

		double sum = 0;
		String number = prefix + initialPolicyNo;

		for (int i = 0; i < number.length(); i++) {
			sum = sum + Integer.parseInt(number.substring(i, i + 1));
		}

		double result = sum / 9;

		String strResult = new Double(result).toString();
		int rdp = 0;
		int val = 0;
		boolean flag = false;
		int decIndex = 0;
		decIndex = strResult.lastIndexOf(".");

		if (decIndex > 0) {
			String strRDP = strResult.substring(decIndex + 1, decIndex + 2);
			rdp = Integer.parseInt(strRDP);
			val = 9 - rdp;
		}
		{ if (logger.isDebugEnabled()) logger.debug("NEWLY GENERATED POLICY NUMBER:-" + prefix + "-" + initialPolicyNo + "-"
				+ new Integer(val).toString() + " LAST UPDATED AT" + System.currentTimeMillis()); }
		return prefix + "-" + initialPolicyNo + "-" + new Integer(val).toString();
	}

	public static void validatePayment(IContext ctx) throws Exception {
		populateLastUpdateTimeStamp(ctx);
		if (ctx.get("PaymentMode") == null)
			populateError(ctx, "PaymentMode", "Choose any payment option");
		else if (ctx.get("PaymentMode") != null && "".equals(ctx.get("PaymentMode").toString()))
			populateError(ctx, "PaymentMode", "Choose any payment option");

	}

	public static void validateComment(IContext ctx) throws Exception {
		if (ctx.get("CommentUploaded") == null)
			populateError(ctx, "CommentUploaded", "Enter comment");
		else if (ctx.get("CommentUploaded") != null && "".equals(ctx.get("CommentUploaded").toString()))
			populateError(ctx, "CommentUploaded", "Enter comment");

		populateLastUpdateTimeStamp(ctx);
		// System.out.println("last updatted userr id: "+ctx.get("LastUpdateUserID"));
		if (ctx.get("UserNo") != null)
			ctx.put("CommentUploadedBy", ctx.get("UserNo"));

	}

	public static void populateLastUpdateTimeStamp(IContext ctx) throws Exception {
		ctx.put("LastUpdateTimeStamp", new Timestamp(new Date().getTime()).toString());
		if (ctx.get("LastUpdateTimeStamp") != null)
			ctx.put("UploadedTime", ctx.get("LastUpdateTimeStamp"));
	}

	public static void processStatus(IContext ctx) throws Exception {
		if (ctx.get("StatusKey_Prev") != null && "7".equals(ctx.get("StatusKey_Prev").toString())
				&& ctx.get("StatusKey") != null && "6".equals(ctx.get("StatusKey").toString())) {
			processIssueStatus(ctx);
		}
	}

	public static void processIssueStatus(IContext ctx) throws Exception {
		Context newCtx = new Context();
		newCtx.putAll(ctx);
		Object objTransac = SqlResources.getSqlMapProcessor(ctx)
				.select("SqlStmts.UserStatementsaveAccountstmtsgetFinalisedQuote", ctx);
		if (objTransac != null && objTransac instanceof List) {
			List transList = (List) objTransac;

			if (transList != null && transList.size() > 0)
				for (int i = 0; i < transList.size(); i++) {
					Map transMap = (Map) transList.get(i);
					newCtx.put("TransactionSequence",
							transMap.get("TransactionSequence") != null ? transMap.get("TransactionSequence").toString()
									: ctx.get("TransactionSequence"));
				}
		}

		Object objPaymentDetail = SqlResources.getSqlMapProcessor(newCtx).findByKey("PaymentDetail.findByKey", newCtx);
		Map mapPaymentDetail = null;

		if (objPaymentDetail != null && objPaymentDetail instanceof Map) {
			mapPaymentDetail = (Map) objPaymentDetail;
			if (mapPaymentDetail.get("PaymentMode") != null
					&& "paypal".equals(mapPaymentDetail.get("PaymentMode").toString()))
				populateError(ctx, "PolicyNumber", "Policy can not be issued");
		} else if (objPaymentDetail == null)
			populateError(ctx, "PolicyNumber", "Select any payment option first");

		Object obj = RuleUtils.executeRule(ctx, "AccountantRule.isQuotationExpired");
		boolean flag = false;
		if (obj instanceof Boolean)
			flag = (Boolean) obj;

		if (flag) {
			AccountUtils.populateError(ctx, "PolicyNumber", "Quote expired and moved to under review bucket");
			SqlResources.getSqlMapProcessor(ctx).update("SqlStmts.UserStatementManualBoQueriesUpdateStatusUnderreview",
					ctx);
		}

		Object objRule = RuleUtils.executeRule(ctx, "AccountantRule.IsDaysMoreThan90");
		if (objRule != null) {
			Boolean rule = (Boolean) objRule;
			if (rule) {
				populateError(ctx, "PolicyNumber", "Cannot Issue. Policy Effective Date is 90 days out.");
			}
		}
	}

	public static void checkLimitDeductible(IContext ctx) throws Exception {
		populateLastUpdateTimeStamp(ctx);
		ctx.put("LastUpdateUserID", ctx.get("FirstReviewUserID"));

		Object objBI = SqlResources.getSqlMapProcessor(ctx).findByKey("SqlStmts.BasicInfoviewCoveragePolicy", ctx);

		Object objLD = SqlResources.getSqlMapProcessor(ctx)
				.findByKey("SqlStmts.UserStatementsaveAccountstmtsgetLimitDeductible", ctx);

		if ((objLD != null && objLD instanceof Map) && (objBI != null && objBI instanceof Map)) {
			Map mapLD = (Map) objLD;
			Context newctx = new Context();
			newctx.putAll(mapLD);

			Map mapBI = (Map) objBI;
			newctx.put("CoverageSequence", mapBI.get("CoverageSequence"));
			newctx.put("PolicyKey", mapBI.get("PolicyKey"));
			newctx.put("TransactionSequence", mapBI.get("TransactionSequence"));
			newctx.put("RatingTraceSequence", mapBI.get("RatingTraceSequence"));
			if (mapBI.get("AggregateLimit") != null) {
				newctx.put("AggregateLimit", removeAmountFormat(mapLD.get("AggregateLimit")));
			}
			if (mapBI.get("OccuranceLimit") != null) {
				newctx.put("OccuranceLimit", removeAmountFormat(mapLD.get("OccuranceLimit")));
			}
			if (mapBI.get("OccuranceDeductible") != null) {
				newctx.put("OccuranceDeductible", removeAmountFormat(mapLD.get("OccuranceDeductible")));
			}
			if (mapBI.get("AggregateDeductible") != null) {
				newctx.put("AggregateDeductible", removeAmountFormat(mapLD.get("AggregateDeductible")));
			}
			SqlResources.getSqlMapProcessor(ctx).update("PolicyCoverage.update", newctx);
			SqlResources.getSqlMapProcessor(ctx).update("PolicyTransaction.update", newctx);
			SqlResources.getSqlMapProcessor(ctx).update("Quote.update", newctx);
			SqlResources.getSqlMapProcessor(ctx).update("RatingTrace.update", newctx);
		} else {
			ctx.put("CoverageSelected_Matched", "N");
		}

		if ((objBI != null && objBI instanceof Map) && (objLD != null && objLD instanceof Map)) {
			Map mapBI = (Map) objBI;
			Context newctx = new Context();
			newctx.putAll(mapBI);

			Map mapLD = (Map) objLD;
			newctx.put("CoverageSequence", mapLD.get("CoverageSequence"));
			newctx.put("PolicyKey", mapLD.get("PolicyKey"));
			newctx.put("TransactionSequence", mapLD.get("TransactionSequence"));
			newctx.put("RatingTraceSequence", mapLD.get("RatingTraceSequence"));
			if (mapLD.get("AggregateLimit") != null) {
				newctx.put("AggregateLimit", removeAmountFormat(mapBI.get("AggregateLimit")));
			}
			if (mapLD.get("OccuranceLimit") != null) {
				newctx.put("OccuranceLimit", removeAmountFormat(mapBI.get("OccuranceLimit")));
			}
			if (mapLD.get("OccuranceDeductible") != null) {
				newctx.put("OccuranceDeductible", removeAmountFormat(mapBI.get("OccuranceDeductible")));
			}
			if (mapLD.get("AggregateDeductible") != null) {
				newctx.put("AggregateDeductible", removeAmountFormat(mapBI.get("AggregateDeductible")));
			}
			SqlResources.getSqlMapProcessor(ctx).update("PolicyCoverage.update", newctx);
			SqlResources.getSqlMapProcessor(ctx).update("PolicyTransaction.update", newctx);
			SqlResources.getSqlMapProcessor(ctx).update("Quote.update", newctx);
			SqlResources.getSqlMapProcessor(ctx).update("RatingTrace.update", newctx);
		} else {
			ctx.put("CoverageSelected_Matched", "N");
		}

	}

	public static void validateWeeklyReport(IContext ctx) throws Exception {

		if ((ctx.get("WeekFromDate") == null) && (ctx.get("WeekToDate") == null)) {
			AccountUtils.populateError(ctx, "WeekFromDate ", "Start Date is required");
			AccountUtils.populateError(ctx, "WeekToDate", "End Date is required");

		} else {
			if (ctx.get("WeekFromDate") != null && "".equals(ctx.get("WeekFromDate").toString())) {
				AccountUtils.populateError(ctx, "WeekFromDate", "Start Date is required");
			}
			if (ctx.get("WeekToDate") != null && "".equals(ctx.get("WeekToDate").toString())) {
				AccountUtils.populateError(ctx, "WeekToDate", "End Date is required");
			}

			if (ctx.get("WeekFromDate") != null && !"".equals(ctx.get("WeekFromDate").toString())) {
				AccountUtils.validateLeapYearDate(ctx, "WeekFromDate", ctx.get("WeekFromDate").toString());
			}
			if (ctx.get("WeekToDate") != null && !"".equals(ctx.get("WeekToDate").toString())) {
				AccountUtils.validateLeapYearDate(ctx, "WeekToDate", ctx.get("WeekToDate").toString());
			}
		}

	}

	public static void validateReport(IContext ctx) throws Exception {
		if (ctx.get("FromDate") == null)
			populateError(ctx, "FromDate", "Start Date is required");
		else if (ctx.get("FromDate") != null && "".equals(ctx.get("FromDate").toString()))
			populateError(ctx, "FromDate", "Start Date is required");

		if (ctx.get("ToDate") == null)
			populateError(ctx, "ToDate", "End Date is required");
		else if (ctx.get("ToDate") != null && "".equals(ctx.get("ToDate").toString()))
			populateError(ctx, "ToDate", "End Date is required");
	}

	public static void savePaymentDetail(IContext ctx) throws Exception {
		boolean finalisedFlag = false;

		List quoteList = SqlResources.getSqlMapProcessor(ctx)
				.select("SqlStmts.UserStatementManualBoQueriesgetQuoteListAll", ctx);
		if (quoteList != null) {
			for (int i = 0; i < quoteList.size(); i++) {
				Map quote = (Map) quoteList.get(i);
				if (quote.get("IsThisOptionFinalised") != null && quote.get("IsQuoteSelected") != null) {
					if ("Y".equals(quote.get("IsThisOptionFinalised").toString())
							&& "Y".equals(quote.get("IsQuoteSelected").toString())) {
						ctx.put("TransactionSequence", quote.get("TransactionSequence"));
						finalisedFlag = true;
					}
				}
			}
		}

		if (!finalisedFlag)
			return;

		Object objPaymentExist = SqlResources.getSqlMapProcessor(ctx)
				.findByKey("SqlStmts.UserStatementsaveAccountstmtsgetPaymentDetail", ctx);

		Object obj = SqlResources.getSqlMapProcessor(ctx)
				.findByKey("SqlStmts.UserStatementsaveAccountstmtsgetFullPaymentDetail", ctx);
		Map paymentDetailMap = null;

		if (obj != null && obj instanceof Map) {
			paymentDetailMap = (Map) obj;
			ctx.putAll(paymentDetailMap);
		}

		boolean finalisedOptionChanged = false;
		if (objPaymentExist != null && obj == null)
			finalisedOptionChanged = true;

		RuleUtils.executeRule(ctx, "AccountantRule.AssignLastUpdateTimeStamp");
		if (ctx.get("LastUpdateTimeStamp") != null)
			ctx.put("TransactionDate", ctx.get("LastUpdateTimeStamp"));

		if (ctx.get("PaymentMode") == null)
			ctx.put("PaymentMode", "eft");
		if (ctx.get("TransactionStatus") == null)
			ctx.put("TransactionStatus", "Completed");

		Object objLastUpdateUserID = SqlResources.getSqlMapProcessor(ctx)
				.findByKey("SqlStmts.quoteOptionsaveQuotegetLastUpdateUserID", ctx);

		if (objLastUpdateUserID != null && objLastUpdateUserID instanceof Map) {

			Map mapLastUpdateUserID = (Map) objLastUpdateUserID;
			ctx.put("LastUpdateUserID", mapLastUpdateUserID.get("LastUpdateUserID"));
		}
		int amount = ctx.get("Amount") != null && !ctx.get("Amount").equals(HtmlConstants.EMPTY)
				? Integer.parseInt(ctx.get("Amount").toString().replaceAll("[,$]", ""))
				: 0;
		ctx.put("Amount", amount);
		if (finalisedOptionChanged) {
			SqlResources.getSqlMapProcessor(ctx).delete("SqlStmts.UserStatementsaveAccountstmtsdeletePaymentDetail",
					ctx);
			SqlResources.getSqlMapProcessor(ctx).insert("PaymentDetail.insert", ctx);

		} else {

			if (paymentDetailMap == null)
				SqlResources.getSqlMapProcessor(ctx).insert("PaymentDetail.insert", ctx);
			else if (paymentDetailMap != null)
				SqlResources.getSqlMapProcessor(ctx).update("PaymentDetail.update", ctx);
		}

		ctx.remove("IsDocumentProcessed");
	}

	public static void populateCreatedtype(IContext ctx) throws Exception {
		Object obj1 = RuleUtils.executeRule(ctx, "AccountantRule.isAgent");
		boolean flag = false;
		if (obj1 != null && obj1 instanceof Boolean)
			flag = (Boolean) obj1;

		logger.debug(ctx.get("AgentName"));

		if (flag)
			ctx.put("CreatedType", ctx.get("AgentName"));
	}

	public static void checkInvestFinancial(IContext ctx) throws Exception {

		Object objFirmDetails = SqlResources.getSqlMapProcessor(ctx).findByKey("SqlStmts.generalviewgetFirmDetails",
				ctx);
		String IsPracriceMangPage = "N";

		if (objFirmDetails != null && objFirmDetails instanceof Map) {

			Map mapFirmDetails = (Map) objFirmDetails;
			IsPracriceMangPage = mapFirmDetails.get("IsPracriceMangPage").toString();
			boolean skipMethod = false;
			skipMethod = checkAuditAOPM(ctx, IsPracriceMangPage);
		}
	}

	public static void checkAudit(IContext ctx) throws Exception {

		Object objFirmDetails = SqlResources.getSqlMapProcessor(ctx).findByKey("SqlStmts.generalviewgetFirmDetails",
				ctx);

		String IsPracriceMangPage = "N";
		String IsSupplementPage = "N";

		if (objFirmDetails != null && objFirmDetails instanceof Map) {

			Map mapFirmDetails = (Map) objFirmDetails;
			IsPracriceMangPage = mapFirmDetails.get("IsPracriceMangPage").toString();
			IsSupplementPage = mapFirmDetails.get("IsSupplementPage").toString();

		}

		boolean skipMethod = false;
		skipMethod = checkAuditAOPM(ctx, IsPracriceMangPage);
		if (skipMethod == false) {
			skipMethod = checkAuditAOP19(ctx, IsSupplementPage);

		}

	}

	public static void checkComputer(IContext ctx) throws Exception {

		Object objFirmDetails = SqlResources.getSqlMapProcessor(ctx).findByKey("SqlStmts.generalviewgetFirmDetails",
				ctx);
		String IsPracriceMangPage = "N";
		String IsSupplementPage = "N";
		String IsAttesationPage = "N";

		if (objFirmDetails != null && objFirmDetails instanceof Map) {

			Map mapFirmDetails = (Map) objFirmDetails;
			IsPracriceMangPage = mapFirmDetails.get("IsPracriceMangPage").toString();
			IsSupplementPage = mapFirmDetails.get("IsSupplementPage").toString();
			IsAttesationPage = mapFirmDetails.get("IsAttesationPage").toString();

		}

		boolean skipMethod = false;
		skipMethod = checkAuditAOPM(ctx, IsPracriceMangPage);
		if (skipMethod == false) {
			skipMethod = checkAuditAOP19(ctx, IsSupplementPage);
			if (skipMethod == false) {
				skipMethod = checkAuditAOP56(ctx, IsAttesationPage);

			}
		}

	}

	public static void checkBuisness(IContext ctx) throws Exception {

		Object objFirmDetails = SqlResources.getSqlMapProcessor(ctx).findByKey("SqlStmts.generalviewgetFirmDetails",
				ctx);

		String IsPracriceMangPage = "N";
		String IsSupplementPage = "N";
		String IsAttesationPage = "N";
		String IsComputerServicePage = "N";

		if (objFirmDetails != null && objFirmDetails instanceof Map) {

			Map mapFirmDetails = (Map) objFirmDetails;
			IsPracriceMangPage = mapFirmDetails.get("IsPracriceMangPage").toString();
			IsSupplementPage = mapFirmDetails.get("IsSupplementPage").toString();
			IsAttesationPage = mapFirmDetails.get("IsAttesationPage").toString();
			IsComputerServicePage = mapFirmDetails.get("IsComputerServicePage").toString();

		}

		boolean skipMethod = false;
		skipMethod = checkAuditAOPM(ctx, IsPracriceMangPage);
		if (skipMethod == false) {
			skipMethod = checkAuditAOP19(ctx, IsSupplementPage);
			if (skipMethod == false) {
				skipMethod = checkAuditAOP56(ctx, IsAttesationPage);
				{ if (logger.isDebugEnabled()) logger.debug(skipMethod); }
				if (skipMethod == false) {
					skipMethod = checkAuditAOP14(ctx, IsComputerServicePage);

				}
			}
		}

	}

	public static void showNextPage(IContext ctx) throws Exception {

		Object objFirmDetails = SqlResources.getSqlMapProcessor(ctx).findByKey("SqlStmts.generalviewgetFirmDetails",
				ctx);

		String IsPracriceMangPage = "N";
		String IsSupplementPage = "N";
		String IsAttesationPage = "N";
		String IsComputerServicePage = "N";
		String IsBusinessFundPage = "N";

		if (objFirmDetails != null && objFirmDetails instanceof Map) {

			Map mapFirmDetails = (Map) objFirmDetails;
			IsPracriceMangPage = mapFirmDetails.get("IsPracriceMangPage").toString();
			IsSupplementPage = mapFirmDetails.get("IsSupplementPage").toString();
			IsAttesationPage = mapFirmDetails.get("IsAttesationPage").toString();
			IsComputerServicePage = mapFirmDetails.get("IsComputerServicePage").toString();
			IsBusinessFundPage = mapFirmDetails.get("IsBusinessFundPage").toString();
		}

		boolean skipMethod = false;
		skipMethod = checkAuditAOPM(ctx, IsPracriceMangPage);
		if (skipMethod == false) {
			skipMethod = checkAuditAOP19(ctx, IsSupplementPage);
			if (skipMethod == false) {
				skipMethod = checkAuditAOP56(ctx, IsAttesationPage);
				{ if (logger.isDebugEnabled()) logger.debug(skipMethod); }
				if (skipMethod == false) {
					skipMethod = checkAuditAOP14(ctx, IsComputerServicePage);
					if (skipMethod == false) {
						skipMethod = checkAuditAOP18(ctx, IsBusinessFundPage);
					}
				}
			}
		}

	}

	private static boolean checkAuditAOP19(IContext ctx, String isSupplementPage2) throws Exception {

		boolean flagAOP19 = false;
		String IsSupplementPage = "N";

		Object obCheckAOP19 = RuleUtils.executeRule(ctx, "AccountantRule.isAuditAOP19");

		if (obCheckAOP19 != null && obCheckAOP19 instanceof Boolean)
			flagAOP19 = (Boolean) obCheckAOP19;

		if (flagAOP19) {
			if (ctx.get("IsSupplementPage") != null)
				// IsSupplementPage = ctx.get("IsSupplementPage").toString();
				if (isSupplementPage2.equals("N")) {

					populateError(ctx, "PracriceMangPage",
							"Please fill the Investment / Financial Planning Supplement");

					return true;
				}

		}

		return false;

	}

	private static boolean checkAuditAOP56(IContext ctx, String isAttesationPage2) throws Exception {
		boolean flagAOP56 = false;
		String IsAttesationPage = "N";

		Object obCheckAOP56 = RuleUtils.executeRule(ctx, "AccountantRule.isAuditAOP56");

		if (obCheckAOP56 != null && obCheckAOP56 instanceof Boolean)
			flagAOP56 = (Boolean) obCheckAOP56;

		if (flagAOP56) {
			if (ctx.get("IsAttesationPage") != null)
				// IsAttesationPage = ctx.get("IsAttesationPage").toString();
				if (isAttesationPage2.equals("N")) {

					populateError(ctx, "PracriceMangPage", "Please fill the Audit Supplement");

					return true;

				}

		}
		return false;
	}

	private static boolean checkAuditAOP14(IContext ctx, String isComputerServicePage2) throws Exception {
		boolean flagAOP14 = false;
		String IsComputerServicePage = "N";

		Object obCheckAOP14 = RuleUtils.executeRule(ctx, "AccountantRule.isAuditAOP14");

		if (obCheckAOP14 != null && obCheckAOP14 instanceof Boolean)
			flagAOP14 = (Boolean) obCheckAOP14;

		if (flagAOP14) {
			if (ctx.get("IsComputerServicePage") != null)
				// IsComputerServicePage =
				// ctx.get("IsComputerServicePage").toString();
				if (isComputerServicePage2.equals("N")) {

					populateError(ctx, "PracriceMangPage", "Please fill the Computer Related Services ");

					return true;

				}

		}
		return false;
	}

	private static boolean checkAuditAOP18(IContext ctx, String isBusinessFundPage2) throws Exception {
		boolean flagAOP18 = false;
		String isBusinessFundPage = "N";

		Object obCheckAOP18 = RuleUtils.executeRule(ctx, "AccountantRule.isAuditAOP18");

		if (obCheckAOP18 != null && obCheckAOP18 instanceof Boolean)
			flagAOP18 = (Boolean) obCheckAOP18;

		if (flagAOP18) {
			if (ctx.get("IsBusinessFundPage") != null)
				// isBusinessFundPage =
				// ctx.get("IsBusinessFundPage").toString();
				if (isBusinessFundPage2.equals("N")) {

					populateError(ctx, "PracriceMangPage", "Please fill the Business Management / Funds Control");

					return true;

				}

		}
		return false;
	}

	private static boolean checkAuditAOPM(IContext ctx, String isPracriceMangPage2) throws Exception {
		boolean flagAOPM = false;
		String isPracriceMangPage = "N";

		Object obCheckAOPM = RuleUtils.executeRule(ctx, "AccountantRule.isAuditAOPPM");

		if (obCheckAOPM != null && obCheckAOPM instanceof Boolean)
			flagAOPM = (Boolean) obCheckAOPM;

		if (flagAOPM) {
			if (ctx.get("IsPracriceMangPage") != null)
				// isPracriceMangPage =
				// ctx.get("IsPracriceMangPage").toString();
				if (isPracriceMangPage2.equals("N")) {

					populateError(ctx, "PracriceMangPage", "Please fill the Practice Management Supplement");

					return true;
				}

		}
		return false;
	}

	public static void checkAccountEmailExist(IContext ctx) throws Exception {

		{ if (logger.isDebugEnabled()) logger.debug(ctx.get("fromClickRegistrationLink")); }
		{ if (logger.isDebugEnabled()) logger.debug(ctx.get("fromRegistrationLink")); }
		boolean isAccountEmailExist = false;

		List listFirm = SqlResources.getSqlMapProcessor(ctx)
				.select("SqlStmts.UserStatementsaveAccountstmtsgetRegistrationDetails", ctx);

		if (listFirm != null && listFirm.size() >= 1)
			isAccountEmailExist = true;

		if (isAccountEmailExist)
			populateError(ctx, "AccountEmail",
					"This user already exists, please contact 1-888-803-9898 for any assistance");

	}

	public void checkForOldPolicy(IContext ctx) throws Exception {

		ctx.get("fromRegistrationLink");
		ctx.get("PolicyKey");
		ctx.get("AccountEmail");

		if (ctx.get("fromRegistrationLink") != null && ctx.get("fromRegistrationLink").toString().equals("Y")) {

			if (ctx.get("PolicyKey") != null && !"null".equals(ctx.get("PolicyKey").toString())) {

				ctx.put("IsOldPolicy", "N");

			} else {
				ctx.put("IsOldPolicy", "Y");
			}

		} else {
			ctx.put("IsOldPolicy", "N");
		}

	}

	public void checkForPolicyKey(IContext ctx) throws Exception {

		// String orgPolicyKey="";

		if (ctx.get("orgPolicyKey") == null) {
			if (ctx.get("PolicyKey") != null && !"null".equals(ctx.get("PolicyKey").toString())) {
				ctx.put("orgPolicyKey", ctx.get("PolicyKey").toString());
			}
		} else {
			ctx.put("PolicyKey", ctx.get("orgPolicyKey").toString());
		}
	}

	public void insertDataForRenewPolicy(IContext ctx) throws Exception {
		{ if (logger.isDebugEnabled()) logger.debug("Renew policy----" + ctx.get("RenewPolicy_Data")); }
		Object obj = ctx.get("RenewPolicy_Data");

		if (obj != null) {
			List list = (List) obj;
			for (int i = 0; i < list.size(); i++) {
				Map map = (Map) list.get(i);
				ctx.put("PolicyKey", map.get("PolicyKey"));
				ctx.put("VersionSequence", map.get("VersionSequence"));
				ctx.put("TransactionSequence", map.get("TransactionSequence"));
				ctx.put("CoverageSequence", map.get("CoverageSequence"));
				ctx.put("AccountKey", map.get("AccountKey"));
				ctx.put("AddressKey", map.get("AddressKey"));
			}
		}
		Object objPolicy = SqlResources.getSqlMapProcessor(ctx).findByKey("Policy.findByKey", ctx);
		if (objPolicy != null)
			ctx.putAll((Map) objPolicy);
	}

	public static void checkPolicyEffectiveDateExist(IContext ctx) throws Exception {
		String strDate;
		boolean flag = false;
		if (ctx.get("PolicyEffectiveDate") != null && ctx.get("PolicyEffectiveDate") == "") {
			populateError(ctx, "PolicyEffectiveDate", "Policy Effective Date is a required field");

		}
	}

	public static List populateFinalisedPolicyForm(IContext ctx) throws Exception {
		setFormChangesConditions((Context) ctx);
		Context newCtx = new Context();
		newCtx.putAll(ctx);

		newCtx.put("PolicyKey_form",
				ctx.get("PolicyKey") != null ? ctx.get("PolicyKey").toString() : ctx.get("PolicyKey"));
		newCtx.put("StateCode_form",
				ctx.get("StateCode") != null ? ctx.get("StateCode").toString() : ctx.get("StateCode"));
		newCtx.put("PolicyEffectiveDate_form",
				ctx.get("PolicyEffectiveDate") != null ? ctx.get("PolicyEffectiveDate").toString()
						: ctx.get("PolicyEffectiveDate"));
		newCtx.put("IsFinalisedOption_form", "Y");
		newCtx.put("isNewFormChanges", ctx.get("isNewFormChanges"));
		logger.debug("Going to execute the stored procedure for Form Attachment");
		List listForm = SqlResources.getSqlMapProcessor(newCtx).select("Account.AccountantFormAttachment_p", newCtx);
		logger.debug("SP has been executed for the Form Attachment");
		return listForm;

		/*
		 * List listForm = SqlResources.getSqlMapProcessor(ctx).select( "Form.findAll",
		 * ctx); List listStateForm = SqlResources.getSqlMapProcessor(ctx).select(
		 * "SqlStmts.UserStatementpdfquoteletterStateForm", ctx);
		 * 
		 * boolean dollarDefenseFlag = false; String IsClaimExpensesType = "N"; String
		 * IsClaimOptionType = "N"; Context newctx = null;
		 * 
		 * Object objFinalisedQuote = SqlResources .getSqlMapProcessor(ctx) .findByKey(
		 * "SqlStmts.UserStatementsaveAccountstmtsgetFinalisedQuote", ctx); Map
		 * mapFinalisedQuote = null;
		 * 
		 * if (objFinalisedQuote != null && objFinalisedQuote instanceof Map)
		 * mapFinalisedQuote = (Map) objFinalisedQuote;
		 * 
		 * if (mapFinalisedQuote != null && !mapFinalisedQuote.isEmpty()) { if
		 * (mapFinalisedQuote.get("IsDollarDefense") != null &&
		 * "Y".equals(mapFinalisedQuote.get("IsDollarDefense") .toString())) { //
		 * dollarDefenseFlag = true; } if (mapFinalisedQuote.get("IsClaimExpensesType")
		 * != null) IsClaimExpensesType = mapFinalisedQuote.get(
		 * "IsClaimExpensesType").toString(); if
		 * (mapFinalisedQuote.get("IsClaimOptionType") != null) IsClaimOptionType =
		 * mapFinalisedQuote.get("IsClaimOptionType") .toString(); }
		 * 
		 * if (listForm == null && listStateForm == null) return;
		 * 
		 * if ("MA".equals(ctx.get("StateCode").toString()) ||
		 * "OK".equals(ctx.get("StateCode").toString())) { if
		 * ("Y".equals(IsClaimExpensesType)) listForm.remove(2); else if
		 * ("N".equals(IsClaimExpensesType)) { listForm.remove(2); if (listStateForm !=
		 * null) {
		 * 
		 * if ("MA".equals(ctx.get("StateCode").toString()) && listStateForm.size() ==
		 * 3) listStateForm.remove(1); else if
		 * ("OK".equals(ctx.get("StateCode").toString()) && listStateForm.size() == 2)
		 * listStateForm.remove(1); }
		 * 
		 * }
		 * 
		 * if (dollarDefenseFlag) { if ("MA".equals(ctx.get("StateCode").toString()))
		 * listForm.remove(1); } else { if ("MA".equals(ctx.get("StateCode").toString())
		 * || "OK".equals(ctx.get("StateCode").toString())) listForm.remove(1); if
		 * ("MA".equals(ctx.get("StateCode").toString()) && listStateForm.size() >= 2)
		 * listStateForm.remove(0);
		 * 
		 * } } else { if ("N".equals(IsClaimExpensesType)) listForm.remove(2);
		 * 
		 * if (!dollarDefenseFlag) listForm.remove(1); }
		 * 
		 * if ("MA".equals(ctx.get("StateCode").toString())) {
		 * 
		 * if ("Y".equals(IsClaimOptionType) && listForm.size() == 9)
		 * listForm.remove(3); else if ("Y".equals(IsClaimOptionType) && listForm.size()
		 * == 8) listForm.remove(2); else if ("Y".equals(IsClaimOptionType) &&
		 * listForm.size() == 7) listForm.remove(1);
		 * 
		 * else if ("N".equals(IsClaimOptionType)) { if (listForm.size() == 9)
		 * listForm.remove(3); else if (listForm.size() == 8) listForm.remove(2); else
		 * if (listForm.size() == 7) listForm.remove(1);
		 * 
		 * if (listStateForm != null) { if ("MA".equals(ctx.get("StateCode").toString())
		 * && listStateForm.size() == 3) listStateForm.remove(2); else if
		 * ("MA".equals(ctx.get("StateCode").toString()) && listStateForm.size() == 2)
		 * listStateForm.remove(1); else if
		 * ("MA".equals(ctx.get("StateCode").toString()) && listStateForm.size() == 1)
		 * listStateForm.remove(0);
		 * 
		 * } } } else { if ("N".equals(IsClaimOptionType)) {
		 * 
		 * if (listForm.size() == 9) listForm.remove(3); else if (listForm.size() == 8)
		 * listForm.remove(2); else if (listForm.size() == 7) listForm.remove(1); } }
		 * 
		 * if ("PA".equals(ctx.get("StateCode").toString())) { if
		 * ("Y".equals(IsClaimExpensesType)) { listStateForm.remove(2);
		 * listStateForm.remove(1);
		 * 
		 * } }
		 * 
		 * if ("KY".equals(ctx.get("StateCode").toString())) {
		 * 
		 * boolean IsTaxablePremium = checkForKYMuncipalityTax(ctx, true); if
		 * (!IsTaxablePremium) listStateForm.remove(1); } if
		 * ("NM".equals(ctx.get("StateCode").toString())) { if
		 * (IsClaimExpensesType.equals("N")){ listStateForm.remove(0); } else
		 * if(IsClaimExpensesType.equals("Y")){ listForm.remove(1); } } if
		 * ("CT".equals(ctx.get("StateCode").toString())) { if
		 * (IsClaimOptionType.equals("N")) listStateForm.remove(1); if
		 * ("Y".equals(IsClaimOptionType)) {
		 * 
		 * if (listForm.size() == 9) listForm.remove(3); else if (listForm.size() == 8)
		 * listForm.remove(2); else if (listForm.size() == 7) listForm.remove(1); }
		 * 
		 * }
		 * 
		 * SqlResources.getSqlMapProcessor(ctx).delete(
		 * "SqlStmts.UserStatementpdfquoteletterdeletePolicyForm", ctx);
		 * 
		 * for (int i = 0; i < listForm.size(); i++) { Map map = (Map) listForm.get(i);
		 * newctx = new Context(); newctx.putAll(map);
		 * newctx.setProject(ctx.getProject()); newctx.put("PolicyKey",
		 * ctx.get("PolicyKey")); newctx.put("StateCode", ctx.get("StateCode"));
		 * newctx.put("LastUpdateUserID", ctx.get("LastUpdateUserID"));
		 * newctx.put("FormType", "C"); populateLastUpdateTimeStamp(newctx);
		 * 
		 * DBUtils.executeDBOperation(newctx, "PolicyForm", "1"); }
		 * 
		 * if (listStateForm == null) return;
		 * 
		 * for (int i = 0; i < listStateForm.size(); i++) { Map map = (Map)
		 * listStateForm.get(i); newctx = new Context(); newctx.putAll(map);
		 * newctx.setProject(ctx.getProject()); newctx.put("PolicyKey",
		 * ctx.get("PolicyKey")); newctx.put("StateCode", ctx.get("StateCode"));
		 * newctx.put("LastUpdateUserID", ctx.get("LastUpdateUserID"));
		 * 
		 * newctx.put("FormType", map.get("StateFormType"));
		 * populateLastUpdateTimeStamp(newctx);
		 * 
		 * DBUtils.executeDBOperation(newctx, "PolicyForm", "1"); }
		 * 
		 * newctx.remove("FormType");
		 */
	}

	public void validateWorkQSearch(IContext ctx) throws Exception {
		int count = 0;
		if (ctx.get("WQPolicyEffectiveDate_search") != null
				&& !"".equals(ctx.get("WQPolicyEffectiveDate_search").toString())) {
			String strDate = ctx.get("WQPolicyEffectiveDate_search").toString();
			validateLeapYearDate(ctx, "WQPolicyEffectiveDate_search", strDate);

			String d = ctx.get("WQPolicyEffectiveDate_searchTo") != null
					? ctx.get("WQPolicyEffectiveDate_searchTo").toString()
					: "";
			if ("".equals(d)) {
				populateError(ctx, "WQPolicyEffectiveDate_searchTo", "Date is a required field");
			}

		}

		if (ctx.get("WQPolicyEffectiveDate_searchTo") != null
				&& !"".equals(ctx.get("WQPolicyEffectiveDate_searchTo").toString())) {
			String strDate = ctx.get("WQPolicyEffectiveDate_searchTo").toString();
			validateLeapYearDate(ctx, "WQPolicyEffectiveDate_searchTo", strDate);

			String d = ctx.get("WQPolicyEffectiveDate_search") != null
					? ctx.get("WQPolicyEffectiveDate_search").toString()
					: "";
			if ("".equals(d)) {
				populateError(ctx, "WQPolicyEffectiveDate_search", "Date is a required field");
			}

		}

		String AccountNamesearch = (ctx.get("AccountNamesearch") == null || "".equals(ctx.get("AccountNamesearch")))
				? ""
				: ctx.get("AccountNamesearch").toString().trim();
		if (AccountNamesearch.contains("'"))
			AccountNamesearch = AccountNamesearch.replace("'", "''");
		if (!"".equals(AccountNamesearch))
			count = count + 1;
		ctx.put("AccountNamesearch", AccountNamesearch);
		String wqAccountEmailSearch = (ctx.get("WQAccountEmailSearch") == null
				|| "".equals(ctx.get("WQAccountEmailSearch"))) ? "" : ctx.get("WQAccountEmailSearch").toString().trim();
		if (!"".equals(wqAccountEmailSearch))
			count = count + 1;
		ctx.put("WQAccountEmailSearch", wqAccountEmailSearch);
		String wqPolicyEffectiveDate_search = (ctx.get("WQPolicyEffectiveDate_search") == null
				|| "".equals(ctx.get("WQPolicyEffectiveDate_search"))) ? ""
						: ctx.get("WQPolicyEffectiveDate_search").toString().trim();
		ctx.put("WQPolicyEffectiveDate_search", wqPolicyEffectiveDate_search);
		if (!"".equals(wqPolicyEffectiveDate_search))
			count = count + 1;
		String wqPolicyEffectiveDate_searchTo = (ctx.get("WQPolicyEffectiveDate_searchTo") == null
				|| "".equals(ctx.get("WQPolicyEffectiveDate_searchTo"))) ? ""
						: ctx.get("WQPolicyEffectiveDate_searchTo").toString().trim();
		ctx.put("WQPolicyEffectiveDate_searchTo", wqPolicyEffectiveDate_searchTo);
		if (!"".equals(wqPolicyEffectiveDate_searchTo))
			count = count + 1;
		String wqStateCode_search = (ctx.get("WQStateCode_search") == null || "".equals(ctx.get("WQStateCode_search")))
				? ""
				: ctx.get("WQStateCode_search").toString().trim();
		ctx.put("WQStateCode_search", wqStateCode_search);
		if (!"".equals(wqStateCode_search))
			count = count + 1;
		String wqStatusKey_search = (ctx.get("WQStatusKey_search") == null || "".equals(ctx.get("WQStatusKey_search")))
				? ""
				: ctx.get("WQStatusKey_search").toString().trim();
		ctx.put("WQStatusKey_search", wqStatusKey_search);
		if (!"".equals(wqStatusKey_search))
			count = count + 1;
		String wqQuoteNumberSearch = (ctx.get("WQQuoteNumberSearch") == null
				|| "".equals(ctx.get("WQQuoteNumberSearch"))) ? "" : ctx.get("WQQuoteNumberSearch").toString().trim();
		ctx.put("WQQuoteNumberSearch", wqQuoteNumberSearch);
		if (!"".equals(wqQuoteNumberSearch))
			count = count + 1;
		String wqPolicyNumberSearch = (ctx.get("WQPolicyNumberSearch") == null
				|| "".equals(ctx.get("WQPolicyNumberSearch"))) ? "" : ctx.get("WQPolicyNumberSearch").toString().trim();
		ctx.put("WQPolicyNumberSearch", wqPolicyNumberSearch);
		if (!"".equals(wqPolicyNumberSearch))
			count = count + 1;
		String wqNumberOfDays_search = (ctx.get("WQNumberOfDays_search") == null
				|| "".equals(ctx.get("WQNumberOfDays_search"))) ? ""
						: ctx.get("WQNumberOfDays_search").toString().trim();
		ctx.put("WQNumberOfDays_search", wqNumberOfDays_search);
		if (count < 2 & "".equals(AccountNamesearch) & "".equals(wqAccountEmailSearch) & "".equals(wqQuoteNumberSearch)
				& "".equals(wqPolicyNumberSearch) & "".equals(wqNumberOfDays_search))
			populateError(ctx, "SearchCriteria_error", "Two search criteria are required to search.");
	}

	public void populatePriorActDate(IContext ctx) throws Exception {

		if (ctx.get("FirmYear") != null && !"".equals(ctx.get("FirmYear").toString())) {
			int FirmYear = Integer.parseInt("-" + ctx.get("FirmYear").toString());
			int multMonth = 12;
			multMonth = multMonth * FirmYear;
			ctx.put("Month", multMonth);
		} else {
			ctx.put("Month", 0);
		}

		RuleUtils.executeRule(ctx, "AccountantRule.assignPriorActDatePross");
	}

	public static boolean checkForAnyQuoteWithInsideLimit(IContext ctx) throws Exception {

		boolean flag = false;
		List quotesList = SqlResources.getSqlMapProcessor(ctx)
				.select("SqlStmts.UserStatementpdfquotelettergetQuoteOptedList", ctx);

		boolean IsClaimExpensesTypeFlag = false;
		if (quotesList != null) {
			for (int i = 0; i < quotesList.size(); i++) {
				Map map = (Map) quotesList.get(i);
				if (map.get("IsClaimExpensesType") != null && "N".equals(map.get("IsClaimExpensesType").toString()))
					IsClaimExpensesTypeFlag = true;
			}
		}

		if (IsClaimExpensesTypeFlag)
			flag = true;

		return flag;
	}

	public static boolean checkForAnyQuoteWithOutsideLimit(IContext ctx) throws Exception {

		boolean flag = false;
		List quotesList = SqlResources.getSqlMapProcessor(ctx)
				.select("SqlStmts.UserStatementpdfquotelettergetQuoteOptedList", ctx);

		boolean IsClaimExpensesTypeFlag = false;
		if (quotesList != null) {
			for (int i = 0; i < quotesList.size(); i++) {
				Map map = (Map) quotesList.get(i);
				if (map.get("IsClaimExpensesType") != null && "Y".equals(map.get("IsClaimExpensesType").toString()))
					IsClaimExpensesTypeFlag = true;
			}
		}

		if (IsClaimExpensesTypeFlag)
			flag = true;

		return flag;
	}

	public static boolean checkForKYMuncipalityTax(IContext ctx) throws Exception {

		return checkForKYMuncipalityTax(ctx, false);
	}

	public static boolean checkForKYMuncipalityTax(IContext ctx, boolean isFinalised) throws Exception {

		boolean flag = false;

		Object objFirm = SqlResources.getSqlMapProcessor(ctx)
				.findByKey("SqlStmts.UserStatementsaveAccountstmtsgetTaxCalculation", ctx);

		if (objFirm != null && objFirm instanceof Map) {
			Map objMap = (Map) objFirm;

			String IsTaxCalculation = objMap.get("IsTaxCalculation") != null ? objMap.get("IsTaxCalculation").toString()
					: "";

			if ("Y".equals(IsTaxCalculation)) {
				flag = false;
			} else {
				boolean flag1 = false;
				if (isFinalised)
					flag1 = checkIfMTTaxAmountPresentFinalised(ctx);
				else
					flag1 = checkIfMTTaxAmountPresent(ctx);

				if (flag1)
					flag = true;
			}
		}

		return flag;
	}

	public static boolean checkIfMTTaxAmountPresent(IContext ctx) throws Exception {

		boolean flag = false;

		Object objRatingTrace = SqlResources.getSqlMapProcessor(ctx)
				.select("SqlStmts.UserStatementsaveAccountstmtsgetRatingTrace", ctx);

		if (objRatingTrace != null && objRatingTrace instanceof List) {

			List ratingList = (List) objRatingTrace;
			for (int i = 0; i < ratingList.size(); i++) {
				Map objRatingMap = (Map) ratingList.get(i);
				flag = checkForMTTax(objRatingMap.get("XmlOutputDatafromRating") != null
						? objRatingMap.get("XmlOutputDatafromRating").toString()
						: "");
				break;
			}
		}

		return flag;
	}

	public static boolean checkIfMTTaxAmountPresentFinalised(IContext ctx) throws Exception {

		boolean flag = false;

		Map objRatingMap = (Map) SqlResources.getSqlMapProcessor(ctx)
				.findByKey("SqlStmts.UserStatementsaveAccountstmtsgetRatingTraceFinalised", ctx);

		if (objRatingMap != null) {
			boolean mtTaxpre = checkForMTTax(objRatingMap.get("XmlOutputDatafromRating") != null
					? objRatingMap.get("XmlOutputDatafromRating").toString()
					: "");
			if (mtTaxpre)
				flag = true;
		}

		return flag;
	}

	public static boolean checkForMTTax(String outXML) throws Exception {

		boolean taxPresent = false;

		if (outXML == null || outXML.equals(""))
			return taxPresent;

		Element out = getOutPutElement(outXML);

		double mtTax = getOutput(out);

		if (mtTax > 0)
			taxPresent = true;

		return taxPresent;
	}

	public static Element getOutPutElement(String outXML) throws Exception {
		Element outputElement = null;

		Element root = XMLUtils.parseXMLRootElement(outXML);
		outputElement = root.getChild("PremiumInfo");

		return outputElement;
	}

	public static double getOutput(Element element) {
		double mtTax = 0.0;
		List children = element.getChildren();
		if (children == null)
			return 0.0;
		List errorList = new ArrayList();
		for (int i = 0; i < children.size(); i++) {
			Element child = (Element) children.get(i);
			String name = child.getName();
			if (name.equals("MTTaxAmmount")) {
				String str = child.getTextTrim();
				mtTax = Double.parseDouble(str);
			}

		}

		return mtTax;
	}

	public static String getAdminEmailID(IContext ctx) throws Exception {

		String emailID = "";
		emailID = SystemProperties.getInstance().getString("appl." + ctx.getProject() + ".admin.email");
		return emailID;
	}

	public static String getInsuredEmail(IContext ctx) throws Exception {
		logger.debug("check if mailing is on for insured in properties file");
		String emailId = "";
		String isIssuedMailSendToInsured = "N";

		try {
			isIssuedMailSendToInsured = SystemProperties.getInstance()
					.getString("appl." + ctx.getProject() + ".Insured.SendIssuedEmail");

		} catch (Exception e) {
			logger.error("Error in getting data from properties file " + e, e);
			throw e;
		}
		{ if (logger.isDebugEnabled()) logger.debug("Got the value appl.AccountantIns.Insured.SendIssuedEmail  " + isIssuedMailSendToInsured); }
		if ("Y".equals(isIssuedMailSendToInsured)) {

			boolean isMailSendToInsured = isPolicyIssuedMailSendToInsured(ctx);
			if (isMailSendToInsured)
				emailId = getEmailID(ctx);
		}

		return emailId;
	}

	public static boolean isPolicyIssuedMailSendToInsured(IContext ctx) throws Exception {
		logger.debug("check if mail is allowed for a particular state");
		Object objState = SqlResources.getSqlMapProcessor(ctx).findByKey("State.findByKey", ctx);

		Map objStateMap = null;
		if (objState != null)
			objStateMap = (Map) objState;

		if (objStateMap != null) {
			String issueMailLW = objStateMap.get("IssueMailAcc") != null ? objStateMap.get("IssueMailAcc").toString()
					: "";
			if ("Y".equals(issueMailLW))
				return true;
		}
		logger.debug("Going to return false, mail not allowed for this state to be sent to insured ");
		return false;
	}

	/*
	 * public static void main(String args[]) { try {
	 * 
	 * AccountUtils.validateGrossRevenueYear(null);
	 * 
	 * } catch (Exception e) { System.out.println("exception " + e); }
	 * 
	 * }
	 */

	public static void main(String args[]) {

		Context ctx = new Context();
		ctx.put("PolicyKey", "77418");
		ctx.put("PolicyStatusKey", "2");
//		ctx.put("QuoteNumber", "QN-0064439");
//		ctx.put("TransactionTypeKey", Constants.LIMIT_ENDORSEMENT);
//		ctx.put("PolicyEffectiveDate", "02/01/2017");
//		ctx.put("CutOffDate", "01/10/2018");

		ctx.put("AccountKey", "77463");
//		ctx.put("AddressKey", "62131");
//		ctx.put("MarketableProductKey", "1");
		ctx.put("TransactionSequence", "123143");
//		ctx.put("FinalisedQuote_TransactionSequence", "101447");
		ctx.put("VersionSequence", "19873");
		ctx.put("CoverageSequence", "121548");
//		ctx.put("PolicyNumber", "580-316151-6");
		ctx.put("User", "insured");
		ctx.put("FirmYear", "2");
		ctx.put("PolicyExpirationDatePross", "2018-08-04 00:00:00.000");
		ctx.setProject("AccountantIns");

		try {
			SystemProperties.setPropertyFileName("D:\\eclipse\\AccountantIns.properties");
			SqlResources.load("AccountantIns",
					"D:\\ProtexureWorkspace\\Accountant\\11.0.0.3\\codebase\\AccountantIns\\web\\XML\\AccountantIns\\ibatis\\maps\\SqlMapConfig.xml");
			ResourceLoader.load(
					"D:\\ProtexureWorkspace\\Accountant\\11.0.0.3\\codebase\\AccountantIns\\web\\XML\\AccountantIns\\rules\\rules.xml",
					"rules", "AccountantIns");
			ResourceLoader.load(
					"D:\\ProtexureWorkspace\\Accountant\\11.0.0.3\\codebase\\AccountantIns\\web\\XML\\AccountantIns\\functions.xml",
					"functions", "AccountantIns");
			ResourceLoader.load(
					"D:\\ProtexureWorkspace\\Accountant\\11.0.0.3\\codebase\\AccountantIns\\web\\XML\\AccountantIns\\metadata\\metadata.xml",
					"metadata", "AccountantIns");
		} catch (Exception e1) {
			logger.error("Error in AccountUtils.main", e1);
		}

		AccountUtils au = new AccountUtils();

		try {
			au.isReferRuleTriggered(ctx);
//			dm.processEndorementDocument(ctx);
//			dm.processIndicationDocument(ctx);

//			Map map = new HashMap();
//			List list = new ArrayList();
//			list.add(map);
//			ctx.put("general_freeform_01",list);
//			
//			String dataXML = new RequestProcessor().generateResponseXml(ctx,false);	
//			System.out.println(dataXML);
		} catch (Exception e) {
			logger.error("Error in AccountUtils.main", e);
			// TODO Auto-generated catch block
		}
	}

	public void saveRegistrationInfo(IContext ctx) throws Exception {

		List accountList = SqlResources.getSqlMapProcessor(ctx)
				.select("SqlStmts.UserStatementsaveAccountstmtsgetFirstdataInAccount", ctx);
		logger.info("Number of Records per AccountID " + accountList != null ? accountList.size() : null);
		if (accountList != null && accountList.size() > 0) {

			Map accountMap = (Map) accountList.get(0);
			String accountKey = accountMap.get("AccountKey") != null ? accountMap.get("AccountKey").toString() : "";

			ctx.put("AccountKey_Register", accountKey);

		}

	}

	public static boolean validateRenewBasicInfo(Context ctx) throws Exception {

		// if(ctx.get("Amount") == null)
		// populateError(ctx, "Amount", "Gross Revenue Amount is a required
		// field.");
		//
		// if(ctx.get("Amount") != null &&
		// "".equals(ctx.get("Amount").toString()))
		// populateError(ctx, "Amount", "Gross Revenue Amount is a required
		// field.");

		validatePersonnelNumber(ctx);
		validateTrusteeListRenew(ctx);
		validateFirmRenderedListRenew(ctx);
		validateClaimListRenew(ctx);
		validateFirmInitLawSuitListRenew(ctx);

		cleanRenewBasicInfoDataOnPageSave(ctx);
		validateCountyForTX(ctx);
		return true;
	}

	public static void cleanRenewBasicInfoDataOnPageSave(IContext ctx) throws Exception {

		if (ctx.get("IsAnyRegulatoryInquiry") != null && !"Y".equals(ctx.get("IsAnyRegulatoryInquiry").toString())) {
			ctx.put("RegulatoryInquiryExaplain", "");
			ctx.put("RegulatoryInquiryComment", "");
		}
		if (ctx.get("IsAwarenessOfAnyProfLiability") != null
				&& !"Y".equals(ctx.get("IsAwarenessOfAnyProfLiability").toString())) {
			if (ctx.get("IsAwarenessOfAnyProfLiabilitySuitAgainst") != null
					&& !"Y".equals(ctx.get("IsAwarenessOfAnyProfLiabilitySuitAgainst").toString())) {
				// SqlResources.getSqlMapProcessor(ctx).delete(
				// "ClaimIncidentSupp.deleteAllByPartialKey", ctx);
			}
		}

		if (ctx.get("IsSuitInstituted") != null && !"Y".equals(ctx.get("IsSuitInstituted").toString())) {
			// SqlResources
			// .getSqlMapProcessor(ctx)
			// .delete(
			// "SqlStmts.UserStatementrenewapplicationdeleteFirmInitLawsuitsData",
			// ctx);
		}
		if (ctx.get("IsFirmRenderedServiceForAnyClient") != null
				&& !"Y".equals(ctx.get("IsFirmRenderedServiceForAnyClient").toString())) {
			// SqlResources.getSqlMapProcessor(ctx).delete(
			// "OutsideInterestSupp.deleteAllByPartialKey", ctx);
		}

		if (ctx.get("IsServedAsTrustee") != null && !"Y".equals(ctx.get("IsServedAsTrustee").toString())) {
			// SqlResources
			// .getSqlMapProcessor(ctx)
			// .delete(
			// "SqlStmts.UserStatementrenewapplicationdeleteTrusteeOrEstateSuppData",
			// ctx);
		}

		if (ctx.get("IsControlClientFunds") != null && !"Y".equals(ctx.get("IsControlClientFunds").toString())) {
			// SqlResources.getSqlMapProcessor(ctx).delete(
			// "FundsControlledSupp.deleteAllByPartialKey", ctx);
		}

	}

	public static boolean validateFeeSued(Context ctx) throws Exception {

		String date = ctx.get("SuitFilesDateFees") != null ? ctx.get("SuitFilesDateFees").toString() : "";
		validateLeapYearDate(ctx, "SuitFilesDateFees", date);

		date = ctx.get("DueDateFees") != null ? ctx.get("DueDateFees").toString() : "";
		validateLeapYearDate(ctx, "DueDateFees", date);

		if (ctx.get("AmountOfFeesSued") != null && !"".equals(ctx.get("AmountOfFeesSued").toString()))
			ctx.put("AmountOfFeesSued", removeAmountFormat(ctx.get("AmountOfFeesSued")));

		return true;

	}

	public static String getSpecialIssuanceEmail(IContext ctx) {

		String email = "";
		try {
			email = SystemProperties.getInstance().getString("appl." + ctx.getProject() + ".specialissue.emailid");
		} catch (Exception e) {
			logger.error("Error in AccountUtils.getSpecialIssuanceEmail", e);
		}
		return email;

	}

	public static boolean checkIfSpecialIssuance(IContext ctx) throws Exception {
		boolean specialIssuance = false;
		Object ob = SqlResources.getSqlMapProcessor(ctx).findByKey("Firm.findByKey", ctx);
		if (ob != null && ob instanceof Map) {
			Map map = (Map) ob;
			String strSpecialIssuance = map.get("IsSpecialIssuance") != null ? map.get("IsSpecialIssuance").toString()
					: "N";
			if (strSpecialIssuance.equals("Y")) {
				specialIssuance = true;
			}
		}
		return specialIssuance;
	}

	public static void validateGrossRevenueYear(IContext ctx) throws Exception {

		String grossRevenueYear = ctx.get("YearEndDate_0") != null ? ctx.get("YearEndDate_0").toString() : "";

		if (!"".equals(grossRevenueYear)) {

			if (grossRevenueYear.length() == 7) {

				String ryear = grossRevenueYear.substring(3);
				int intyear = AccountantValidationUtils.convertStringToInteger(ryear);
				Calendar cal = Calendar.getInstance();
				int currentYear = cal.get(Calendar.YEAR);

				{ if (logger.isDebugEnabled()) logger.debug(currentYear + " is the current year"); }
				if (!(currentYear - intyear == 1)) {
					populateError(ctx, "estimate_year",
							"Last Completed Year should be one year prior to the current year.");
				}

			}

		}

	}

	public static boolean checkForFinalisedQuoteWithInsideLimit(IContext ctx) throws Exception {

		boolean flag = false;
		List quotesList = SqlResources.getSqlMapProcessor(ctx)
				.select("SqlStmts.UserStatementManualBoQueriesgetFinalisedQuote", ctx);

		boolean IsClaimExpensesTypeFlag = false;
		if (quotesList != null) {
			for (int i = 0; i < quotesList.size(); i++) {
				Map map = (Map) quotesList.get(i);
				if (map.get("IsClaimExpensesType") != null && "N".equals(map.get("IsClaimExpensesType").toString()))
					IsClaimExpensesTypeFlag = true;
			}
		}

		if (IsClaimExpensesTypeFlag)
			flag = true;

		return flag;
	}

	public void sendSignAndPayMail(IContext ctx) throws Exception {
		String emailID = getEmailID(ctx);
		if ("".equals(emailID))
			return;
		String bccAddress = "";
		bccAddress = SystemProperties.getInstance().getString("appl." + ctx.getProject() + ".admin.cc.email");

		{ if (logger.isDebugEnabled()) logger.debug(" - - - Sending Sign and Pay Email to - - - " + emailID); }
		MailSender mail = new MailSender();
		mail.setToAdd(emailID);
		mail.setCcAdd(getFirstReviewerAgentEmailID(ctx));
		mail.setBccAdd(bccAddress);
		mail.setSubject("Accountant's Sign and Pay email");
		mail.setContentType("text/html");
		mail.setBody(generateSignAndPayMailBody(ctx));
		mail.sendMail((Context) ctx);
		logger.debug("Sign and Pay email sent.");

	}

	public String generateSignAndPayMailBody(IContext ctx) throws Exception {

		AccountantValidationUtils utils = new AccountantValidationUtils();
		String encrytedPolicyKey = utils.encrypt(ctx.get("PolicyKey").toString());
		encrytedPolicyKey = encrytedPolicyKey.replace("=", "%3D");
		encrytedPolicyKey = encrytedPolicyKey.replace("+", "%2B");

		String msg = "";
		msg = msg + "<table>";
		msg = msg + "<tr>";
		msg = msg + "<td>";

		msg = msg + ctx.get("AccountName") + "<br/>";
		msg = msg + ctx.get("Address1");
		String address2 = ctx.get("Address2") != null ? " ," + ctx.get("Address2") : "";
		msg = msg + address2 + "<br/>";
		msg = msg + ctx.get("City") + ", " + ctx.get("StateDesc") + ", " + ctx.get("Zip") + "<br/><br/>";

		msg = msg + "Dear " + ctx.get("ContactPerson") + ":<br/><br/>";

		msg = msg + "Thank you for completing your Protexure Accountants application." + "<br/><br/>";

		if (ctx.get("QuoteExpiryDate") != null && !"".equals(ctx.get("QuoteExpiryDate").toString()))
			msg = msg + " Your quotation expires " + ctx.get("QuoteExpiryDate") + ".<br/><br/>";

		msg = msg + "To finalize your coverage electronically please follow the link below:" + "<br><br>" + "<a href="
				+ getProjectUrl(ctx) + "/signandpay.jsp?PolicyKey=" + encrytedPolicyKey + ">Finalize Your Coverage</a>"
				+ "<br><br>"
				+ "This link is specifically for your account; you will be able to securely review and sign your application. Once coverage has been accepted, you will have the opportunity to continue with your payment option."
				+ "<br/><br/>";

		/*
		 * msg = msg +
		 * "Another option to accept coverage is to follow the instructions in the attached quote letter."
		 * 
		 * + "<br/><br/>";
		 */

		msg = msg
				+ "If you have any additional questions, please contact us at our toll-free number 888-803-9898 and one of our licensed professional liability specialists will be happy to assist you."
				+ "<br/><br/>";

		msg = msg + "Regards," + "<br/><br/>";
		msg = msg + "The Protexure Accountants Team<br/>";
		msg = msg + "Phone 888-803-9898<br/>";
		msg = msg + "Fax (440) 333-3214<br/><br/>";

		msg = msg
				+ "<tr style=\"font-weight:bold;color:#787878;font-size:14px;font-style:normal;\"><td>P.S. Protexure also offers a Property and General Liability program tailored to small firms.  Privacy/Cyber Protection, Employee Dishonesty, Workers Comp and a Business Owners Policy for a home based business are available as well.  Please email or call to inquire about any of our offerings.</td></tr>";

		msg = msg + "</td>";
		msg = msg + "</tr>";
		msg = msg + "</table>";

		return msg;

	}

	public static boolean validateNorthDakotaStateRule(IContext ctx, String errorField) {

		try {

			if (ctx.get("StateCode") != null && ctx.get("StateCode").toString().equals("ND")) {

				logger.debug("Check for North Dakota StateRule");
				String limitSequence = ctx.get("LimitSequence") != null ? ctx.get("LimitSequence").toString() : "";
				{ if (logger.isDebugEnabled()) logger.debug("Limit Sequence " + limitSequence); }
				String IsClaimExpensesType = ctx.get("IsClaimExpensesType") != null
						? ctx.get("IsClaimExpensesType").toString()
						: "";
				List list = SqlResources.getSqlMapProcessor(ctx)
						.select("SqlStmts.UserStatementManualBoQueriesgetLimitFromSequnce", ctx);
				if (list != null && list.size() > 0) {
					Map limitMap = (Map) list.get(0);
					int aggregate = Integer.parseInt(limitMap.get("AggregateLimit").toString());
					int occurance = Integer.parseInt(limitMap.get("OccuranceLimit").toString());

					if (aggregate < 1000000) {
						ctx.put("IsClaimExpensesType", "Y");
					}

				}
			}
		} catch (Exception e) {
			logger.error("Exception in validateNorthDakotaStateRule " + e.getMessage(), e);
		}

		return true;

	}

	public static void generateDummyError(Context ctx) throws Exception {
		DataUtils.populateError(ctx, "fielderror", "");
	}

	public static void validateSelection(Context ctx) {
		String policyKey = ctx.get("strPolicyKey").toString();
		// System.out.println("*******************"+policyKey+"***************");
		try {
			if (policyKey == null | policyKey.equals(""))
				AccountUtils.populateError(ctx, "noSelecion", "No offrisk policy has selected");
		} catch (Exception e) {
			logger.error("Error in AccountUtils.validateSelection", e);
			// TODO Auto-generated catch block
		}

	}

	/*
	 * //Method created to reload xmls from config database public static void
	 * reloadXmls(Context ctx) throws Exception{ try { String project_resource =
	 * AccountantInsServlet.projectNames; String[] projects = null;
	 * 
	 * if (project_resource.contains(",")) projects = project_resource.split(",");
	 * 
	 * String projectName = ctx.getProject();
	 * 
	 * String releaseno = ctx.get("releaseno_id_desc") != null ?
	 * ctx.get("releaseno_id_desc").toString() : null;
	 * //SystemProperties.getInstance().getString("appl."+projectName+".releaseno");
	 * String releaseversionno = ctx.get("releaseversionno_id_desc") != null ?
	 * ctx.get("releaseversionno_id_desc").toString() : null;
	 * //SystemProperties.getInstance().getString("appl."+projectName+
	 * ".releaseversionno");
	 * 
	 * if(StringUtils.isBlank(releaseno) || StringUtils.isBlank(releaseversionno))
	 * return;
	 * 
	 * String url =
	 * SystemProperties.getInstance().getString("appl.configuration.service.url");
	 * 
	 * logger.debug("Going to reload xmls from url "+url+" at time : " + new
	 * Date());
	 * 
	 * ConfigurationService service = new ConfigurationServiceProxy(url);
	 * logger.debug(service);
	 * 
	 * String user_id = null;
	 * 
	 * if((SystemProperties.getInstance().getProperty(
	 * "appl.configuration.applicable") != null &&
	 * SystemProperties.getInstance().getString("appl.configuration.applicable").
	 * equals("Y")) && (SystemProperties.getInstance().getProperty(
	 * "appl.configuration.loadconfigurationxmls") != null &&
	 * SystemProperties.getInstance().getString(
	 * "appl.configuration.loadconfigurationxmls").equals("Y"))){
	 * 
	 * if(SystemProperties.getInstance().getProperty(
	 * "appl.configuration.configurationuserid") != null) user_id =
	 * SystemProperties.getInstance().getString(
	 * "appl.configuration.configurationuserid"); }
	 * 
	 * if (projects != null) { for(int i = 0; i < projects.length; i++) {
	 * projectName = projects[i];
	 * 
	 * String bypassXmls = null;
	 * if(SystemProperties.getInstance().getProperty("appl."+projectName+
	 * ".configuration.bypassxmls") != null) bypassXmls =
	 * SystemProperties.getInstance().getString("appl."+projectName+
	 * ".configuration.bypassxmls");
	 * 
	 * XmlStatus xmlStatus =
	 * service.getXmlStatusById(TabsConfiguration.Client_ID+"", releaseno,
	 * releaseversionno, projectName, bypassXmls, user_id);
	 * 
	 * XMLObject[] obj = service.generateXmls(TabsConfiguration.Client_ID+"",
	 * releaseno, releaseversionno, projectName, bypassXmls, user_id);
	 * 
	 * if(obj != null && obj.length > 0){ for(int j=0; j<obj.length; j++){ XMLObject
	 * xmlObj = obj[j];
	 * 
	 * if(xmlObj.getXmlName().equals("labelconf")){
	 * logger.debug("going to generate labelconf xml");
	 * 
	 * String resourcePath =
	 * SystemProperties.getInstance().getString("appl."+projectName+
	 * ".labelconf.path"); FileOutputStream fout = new
	 * FileOutputStream(resourcePath);
	 * fout.write(xmlObj.getXmlContent().getBytes()); fout.close();
	 * 
	 * logger.debug("labelconf xml generated");
	 * 
	 * new AccountantInsServlet().loadResource(projectName, "labelconf"); new
	 * LabelConfiguration().getLabelConfiguration(projectName); }else
	 * if(xmlObj.getXmlName().equals("messages")){
	 * logger.debug("going to generate messages xml");
	 * 
	 * String resourcePath = SystemProperties.getInstance().getString("xml.basedir")
	 * + projectName + File.separator + "messages.xml"; FileOutputStream fout = new
	 * FileOutputStream(resourcePath);
	 * fout.write(xmlObj.getXmlContent().getBytes()); fout.close();
	 * 
	 * logger.debug("messages xml generated");
	 * 
	 * new AccountantInsServlet().loadResource(projectName, "messages"); }else
	 * if(xmlObj.getXmlName().equals("tabsconfigurationresource")){
	 * logger.debug("going to generate tabsconfigurationresource xml");
	 * 
	 * String resourcePath = SystemProperties.getInstance().getString("xml.basedir")
	 * + projectName + File.separator + "tabsconfiguration.xml"; FileOutputStream
	 * fout = new FileOutputStream(resourcePath);
	 * fout.write(xmlObj.getXmlContent().getBytes()); fout.close();
	 * 
	 * logger.debug("tabsconfigurationresource xml generated");
	 * 
	 * new AccountantInsServlet().loadResource(projectName, "tabsconfiguration");
	 * new TabsConfiguration().getTabsConf(); }else
	 * if(xmlObj.getXmlName().equals("functions")){
	 * logger.debug("going to generate functions xml");
	 * 
	 * String resourcePath = SystemProperties.getInstance().getString("xml.basedir")
	 * + projectName + File.separator + "functions.xml"; FileOutputStream fout = new
	 * FileOutputStream(resourcePath);
	 * fout.write(xmlObj.getXmlContent().getBytes()); fout.close();
	 * 
	 * logger.debug("functions xml generated");
	 * 
	 * new AccountantInsServlet().loadResource(projectName, "functions"); }else
	 * if(xmlObj.getXmlName().equals("security")){
	 * logger.debug("going to generate security xml");
	 * 
	 * String resourcePath = SystemProperties.getInstance().getString("xml.basedir")
	 * + projectName + File.separator + "security.xml"; FileOutputStream fout = new
	 * FileOutputStream(resourcePath);
	 * fout.write(xmlObj.getXmlContent().getBytes()); fout.close();
	 * 
	 * logger.debug("security xml generated");
	 * 
	 * new AccountantInsServlet().loadResource(projectName, "security"); }else
	 * if(xmlObj.getXmlName().equals("reports")){
	 * logger.debug("going to generate reports xml");
	 * 
	 * String resourcePath = SystemProperties.getInstance().getString("xml.basedir")
	 * + projectName + File.separator + "reports.xml"; FileOutputStream fout = new
	 * FileOutputStream(resourcePath);
	 * fout.write(xmlObj.getXmlContent().getBytes()); fout.close();
	 * 
	 * logger.debug("reports xml generated");
	 * 
	 * new AccountantInsServlet().loadResource(projectName, "reports"); }else
	 * if(xmlObj.getXmlName().equals("rules")){
	 * logger.debug("going to generate rules xml");
	 * 
	 * String resourcePath = SystemProperties.getInstance().getString("xml.basedir")
	 * + projectName + File.separator + "rules.xml"; File file = new
	 * File(resourcePath); if(file.exists() && file.isFile()){ file.delete(); }
	 * 
	 * resourcePath = SystemProperties.getInstance().getString("xml.basedir") +
	 * projectName + File.separator + "rules"; file = new File(resourcePath);
	 * if(file.exists() && file.isDirectory()){ new
	 * AccountantInsServlet().delete(file, null); }
	 * 
	 * file.mkdir();
	 * 
	 * if(xmlObj.getXmlObject() != null){ Map map = (Map)xmlObj.getXmlObject();
	 * Iterator itr = map.keySet().iterator(); while(itr.hasNext()){ String key =
	 * itr.next().toString();
	 * 
	 * String content = map.get(key).toString(); content = content.replace("&gt;",
	 * ">").replace("&lt;", "<");
	 * 
	 * FileOutputStream fout = new
	 * FileOutputStream(resourcePath+File.separator+key);
	 * fout.write(content.getBytes()); fout.close(); } }
	 * 
	 * logger.debug("rules xml generated");
	 * 
	 * new AccountantInsServlet().loadResource(projectName, "rules"); }else
	 * if(xmlObj.getXmlName().equals("metadatafields")){
	 * logger.debug("going to generate metadatafields xml");
	 * 
	 * //String resourcePath =
	 * SystemProperties.getInstance().getString("xml.basedir") + projectName +
	 * File.separator + "metadata" + File.separator + "metadata" + ".xml"; String
	 * resourcePath = SystemProperties.getInstance().getString("xml.basedir") +
	 * projectName + File.separator + "metadata" + ".xml"; File file = new
	 * File(resourcePath); if(file.exists() && file.isFile()){ file.delete(); }
	 * 
	 * resourcePath = SystemProperties.getInstance().getString("xml.basedir") +
	 * projectName + File.separator + "metadata"; file = new File(resourcePath);
	 * if(file.exists() && file.isDirectory()){ new
	 * AccountantInsServlet().delete(file, null); }
	 * 
	 * file.mkdir();
	 * 
	 * resourcePath = SystemProperties.getInstance().getString("xml.basedir") +
	 * projectName + File.separator + "metadata" + File.separator + "metadatafields"
	 * + ".xml"; FileOutputStream fout = new FileOutputStream(resourcePath);
	 * fout.write(xmlObj.getXmlContent().getBytes()); fout.close();
	 * 
	 * logger.debug("metadatafields xml generated"); }else
	 * if(xmlObj.getXmlName().equals("metadata")){
	 * logger.debug("going to generate metadata xml");
	 * 
	 * String resourcePath = SystemProperties.getInstance().getString("xml.basedir")
	 * + projectName + File.separator + "metadata" + File.separator + "metadata" +
	 * ".xml"; FileOutputStream fout = new FileOutputStream(resourcePath);
	 * fout.write(xmlObj.getXmlContent().getBytes()); fout.close();
	 * 
	 * logger.debug("metadata xml generated");
	 * 
	 * new AccountantInsServlet().loadResource(projectName, "metadata"); }else
	 * if(xmlObj.getXmlName().equals("components")){
	 * logger.debug("going to generate components xml");
	 * 
	 * String resourcePath = SystemProperties.getInstance().getString("xml.basedir")
	 * + projectName + File.separator + "components" + File.separator + "components"
	 * + ".xml"; File file = new File(resourcePath); if(file.exists() &&
	 * file.isFile()){ file.delete(); }
	 * 
	 * resourcePath = SystemProperties.getInstance().getString("xml.basedir") +
	 * projectName + File.separator + "components"; file = new File(resourcePath);
	 * if(file.exists() && file.isDirectory()){ new
	 * AccountantInsServlet().delete(file, null); }
	 * 
	 * file.mkdir();
	 * 
	 * if(xmlObj.getXmlObject() != null){ Map map = (Map)xmlObj.getXmlObject();
	 * Iterator itr = map.keySet().iterator(); while(itr.hasNext()){ String key =
	 * itr.next().toString();
	 * 
	 * String content = map.get(key).toString(); content =
	 * content.replace("&amp;lt;", "&lt;").replace("&amp;gt;", "&gt;");
	 * 
	 * FileOutputStream fout = new
	 * FileOutputStream(resourcePath+File.separator+key);
	 * fout.write(content.getBytes()); fout.close(); } }
	 * 
	 * logger.debug("components xml generated");
	 * 
	 * new AccountantInsServlet().loadResource(projectName, "components"); }else
	 * if(xmlObj.getXmlName().equals("componentshelp")){
	 * logger.debug("going to generate componentshelp xml");
	 * 
	 * String resourcePath = SystemProperties.getInstance().getString("xml.basedir")
	 * + projectName + File.separator + "componentshelp"; File file = new
	 * File(resourcePath); if(file.exists() && file.isDirectory()){ new
	 * AccountantInsServlet().delete(file, null); }
	 * 
	 * file.mkdir();
	 * 
	 * if(xmlObj.getXmlObject() != null){ Map map = (Map)xmlObj.getXmlObject();
	 * Iterator itr = map.keySet().iterator(); while(itr.hasNext()){ String key =
	 * itr.next().toString();
	 * 
	 * String content = map.get(key).toString(); //content = content.replace("&gt;",
	 * ">").replace("&lt;", "<");
	 * 
	 * FileOutputStream fout = new
	 * FileOutputStream(resourcePath+File.separator+key);
	 * fout.write(content.getBytes()); fout.close(); } }
	 * 
	 * logger.debug("componentshelp xml generated"); } } }
	 * 
	 * BuildProcess buildProcess = new BuildProcess(projectName); if(xmlStatus ==
	 * null || !xmlStatus.getIsprocessflowgenerated().equals("Y")){
	 * logger.debug("going to generate processflow");
	 * 
	 * buildProcess.generateProcessFlow();
	 * 
	 * logger.debug("processflow generated");
	 * 
	 * new AccountantInsServlet().loadResource(projectName, "processflow"); }
	 * 
	 * if(xmlStatus == null || !xmlStatus.getIsibatisgenerated().equals("Y")){
	 * String resourcePath = SystemProperties.getInstance().getString("xml.basedir")
	 * + projectName + File.separator + "ibatis"; File file = new
	 * File(resourcePath); if(file.exists() && file.isDirectory()){ new
	 * AccountantInsServlet().delete(file, null); }
	 * 
	 * int techType = 1; // 1 for Java and 2 for .Net
	 * 
	 * logger.debug("going to generate ibatis");
	 * buildProcess.generateIbatis(techType, true);
	 * 
	 * logger.debug("ibatis generated");
	 * 
	 * new AccountantInsServlet().loadResource(projectName, "ibatisconfig"); }
	 * 
	 * LookupResourceLoader.loadLookupResources(projectName);
	 * LoadCommonResources.loadCommonResources(projectName); } }
	 * 
	 * logger.debug("Xmls reloaded successfully at time : " + new Date()); } catch
	 * (Exception e) { logger.error("Unable to reload config xmls due to error : " +
	 * e.getMessage()); } }
	 */
	// Method created to generate xmls from config database
	public static void generateXmls(Context ctx, String labelconfPath, String tabsconfigPath, String messagesPath,
			String rulesPath, String functionsPath, String securityPath, String componentsPath,
			String metadatafieldsPath, String metadataPath, String reportsPath) throws Exception {
		/*
		 * try { String projectName = ctx.getProject();
		 * 
		 * String url =
		 * SystemProperties.getInstance().getString("appl.configuration.service.url");
		 * 
		 * logger.debug("Going to generate xmls from url "+url+" at time : " + new
		 * Date());
		 * 
		 * ConfigurationService service = new ConfigurationServiceProxy(url);
		 * logger.debug(service);
		 * 
		 * String client_id = ctx.get("client_id_desc") != null ?
		 * ctx.get("client_id_desc").toString() : null;
		 * //SystemProperties.getInstance().getString("appl."+projectName+".releaseno");
		 * String releaseno = ctx.get("releaseno_id_desc") != null ?
		 * ctx.get("releaseno_id_desc").toString() : null;
		 * //SystemProperties.getInstance().getString("appl."+projectName+".releaseno");
		 * String releaseversionno = ctx.get("releaseversionno_id_desc") != null ?
		 * ctx.get("releaseversionno_id_desc").toString() : null;
		 * //SystemProperties.getInstance().getString("appl."+projectName+
		 * ".releaseversionno");
		 * 
		 * if(StringUtils.isBlank(releaseno) || StringUtils.isBlank(releaseversionno))
		 * return;
		 * 
		 * String bypassXmls = null; if(ctx.get("bypassxmls") != null) bypassXmls =
		 * ctx.get("bypassxmls").toString();
		 * 
		 * XMLObject[] obj = service.generateXmls(client_id, releaseno,
		 * releaseversionno, projectName, bypassXmls, null);
		 * 
		 * for(int i=0; i<obj.length; i++){ XMLObject xmlObj = obj[i];
		 * 
		 * if(xmlObj.getXmlName().equals("labelconf")){
		 * logger.debug("going to generate labelconf xml");
		 * 
		 * String resourcePath = null; if(StringUtils.isNotBlank(labelconfPath))
		 * resourcePath = labelconfPath; else resourcePath =
		 * SystemProperties.getInstance().getString("appl."+projectName+
		 * ".labelconf.path");
		 * 
		 * FileOutputStream fout = new FileOutputStream(resourcePath);
		 * fout.write(xmlObj.getXmlContent().getBytes()); fout.close();
		 * 
		 * logger.debug("labelconf xml generated"); }else
		 * if(xmlObj.getXmlName().equals("messages")){
		 * logger.debug("going to generate messages xml");
		 * 
		 * String resourcePath = null; if(StringUtils.isNotBlank(messagesPath))
		 * resourcePath = messagesPath; else resourcePath =
		 * SystemProperties.getInstance().getString("xml.basedir") + projectName +
		 * File.separator + "messages.xml";
		 * 
		 * FileOutputStream fout = new FileOutputStream(resourcePath);
		 * fout.write(xmlObj.getXmlContent().getBytes()); fout.close();
		 * 
		 * logger.debug("messages xml generated"); }else
		 * if(xmlObj.getXmlName().equals("tabsconfigurationresource")){
		 * logger.debug("going to generate tabsconfigurationresource xml");
		 * 
		 * String resourcePath = null; if(StringUtils.isNotBlank(tabsconfigPath))
		 * resourcePath = tabsconfigPath; else resourcePath =
		 * SystemProperties.getInstance().getString("xml.basedir") + projectName +
		 * File.separator + "tabsconfiguration.xml";
		 * 
		 * FileOutputStream fout = new FileOutputStream(resourcePath);
		 * fout.write(xmlObj.getXmlContent().getBytes()); fout.close();
		 * 
		 * logger.debug("tabsconfigurationresource xml generated"); }else
		 * if(xmlObj.getXmlName().equals("functions")){
		 * logger.debug("going to generate functions xml");
		 * 
		 * String resourcePath = null; if(StringUtils.isNotBlank(functionsPath))
		 * resourcePath = functionsPath; else resourcePath =
		 * SystemProperties.getInstance().getString("xml.basedir") + projectName +
		 * File.separator + "functions.xml";
		 * 
		 * FileOutputStream fout = new FileOutputStream(resourcePath);
		 * fout.write(xmlObj.getXmlContent().getBytes()); fout.close();
		 * 
		 * logger.debug("functions xml generated"); }else
		 * if(xmlObj.getXmlName().equals("security")){
		 * logger.debug("going to generate security xml");
		 * 
		 * String resourcePath = null; if(StringUtils.isNotBlank(securityPath))
		 * resourcePath = securityPath; else resourcePath =
		 * SystemProperties.getInstance().getString("xml.basedir") + projectName +
		 * File.separator + "security.xml";
		 * 
		 * FileOutputStream fout = new FileOutputStream(resourcePath);
		 * fout.write(xmlObj.getXmlContent().getBytes()); fout.close();
		 * 
		 * logger.debug("security xml generated"); }else
		 * if(xmlObj.getXmlName().equals("reports")){
		 * logger.debug("going to generate reports xml");
		 * 
		 * String resourcePath = SystemProperties.getInstance().getString("xml.basedir")
		 * + projectName + File.separator + "reports.xml"; FileOutputStream fout = new
		 * FileOutputStream(resourcePath);
		 * fout.write(xmlObj.getXmlContent().getBytes()); fout.close();
		 * 
		 * logger.debug("reports xml generated"); }else
		 * if(xmlObj.getXmlName().equals("rules")){
		 * logger.debug("going to generate rules xml");
		 * 
		 * String resourcePath = null; if(StringUtils.isNotBlank(rulesPath))
		 * resourcePath = rulesPath; else resourcePath =
		 * SystemProperties.getInstance().getString("xml.basedir") + projectName +
		 * File.separator + "rules";
		 * 
		 * File file = new File(resourcePath); if(file.exists() && file.isDirectory()){
		 * new AccountantInsServlet().delete(file, null); }
		 * 
		 * file.mkdir();
		 * 
		 * if(xmlObj.getXmlObject() != null){ Map map = (Map)xmlObj.getXmlObject();
		 * Iterator itr = map.keySet().iterator(); while(itr.hasNext()){ String key =
		 * itr.next().toString();
		 * 
		 * String content = map.get(key).toString(); content = content.replace("&gt;",
		 * ">").replace("&lt;", "<");
		 * 
		 * FileOutputStream fout = new
		 * FileOutputStream(resourcePath+File.separator+key);
		 * fout.write(content.getBytes()); fout.close(); } }
		 * 
		 * logger.debug("rules xml generated"); }else
		 * if(xmlObj.getXmlName().equals("metadatafields")){
		 * logger.debug("going to generate metadatafields xml"); String resourcePath =
		 * null; if(StringUtils.isNotBlank(metadatafieldsPath)) resourcePath =
		 * metadatafieldsPath; else resourcePath =
		 * SystemProperties.getInstance().getString("xml.basedir") + projectName +
		 * File.separator + "metadata" + File.separator + "metadatafields" + ".xml";
		 * 
		 * if(!resourcePath.endsWith(".xml")){ File file = new File(resourcePath);
		 * if(!file.exists()){ file.mkdir(); }
		 * 
		 * resourcePath = resourcePath + File.separator + "metadatafields.xml"; }
		 * 
		 * FileOutputStream fout = new FileOutputStream(resourcePath);
		 * fout.write(xmlObj.getXmlContent().getBytes()); fout.close();
		 * 
		 * logger.debug("metadatafields xml generated"); }else
		 * if(xmlObj.getXmlName().equals("metadata")){
		 * logger.debug("going to generate metadata xml");
		 * 
		 * String resourcePath = null; if(StringUtils.isNotBlank(metadataPath))
		 * resourcePath = metadataPath; else resourcePath =
		 * SystemProperties.getInstance().getString("xml.basedir") + projectName +
		 * File.separator + "metadata" + File.separator + "metadata" + ".xml";
		 * 
		 * if(!resourcePath.endsWith(".xml")){ File file = new File(resourcePath);
		 * if(file.exists() && file.isDirectory()){ new
		 * AccountantInsServlet().delete(file, "metadatafields.xml"); }
		 * 
		 * file.mkdir();
		 * 
		 * resourcePath = resourcePath + File.separator + "metadata.xml"; }
		 * 
		 * FileOutputStream fout = new FileOutputStream(resourcePath);
		 * fout.write(xmlObj.getXmlContent().getBytes()); fout.close();
		 * 
		 * logger.debug("metadata xml generated"); }else
		 * if(xmlObj.getXmlName().equals("components")){
		 * logger.debug("going to generate components xml");
		 * 
		 * String resourcePath = null; if(StringUtils.isNotBlank(componentsPath))
		 * resourcePath = componentsPath; else resourcePath =
		 * SystemProperties.getInstance().getString("xml.basedir") + projectName +
		 * File.separator + "components";
		 * 
		 * File file = new File(resourcePath); if(file.exists() && file.isDirectory()){
		 * new AccountantInsServlet().delete(file, null); }
		 * 
		 * file.mkdir();
		 * 
		 * if(xmlObj.getXmlObject() != null){ Map map = (Map)xmlObj.getXmlObject();
		 * Iterator itr = map.keySet().iterator(); while(itr.hasNext()){ String key =
		 * itr.next().toString();
		 * 
		 * String content = map.get(key).toString(); //content = content.replace("&gt;",
		 * ">").replace("&lt;", "<");
		 * 
		 * FileOutputStream fout = new
		 * FileOutputStream(resourcePath+File.separator+key);
		 * fout.write(content.getBytes()); fout.close(); } }
		 * 
		 * logger.debug("components xml generated"); }else
		 * if(xmlObj.getXmlName().equals("componentshelp")){
		 * logger.debug("going to generate componentshelp xml");
		 * 
		 * String resourcePath = null; if(StringUtils.isNotBlank(componentsPath))
		 * resourcePath = componentsPath; else resourcePath =
		 * SystemProperties.getInstance().getString("xml.basedir") + projectName +
		 * File.separator + "componentshelp";
		 * 
		 * File file = new File(resourcePath); if(file.exists() && file.isDirectory()){
		 * new AccountantInsServlet().delete(file, null); }
		 * 
		 * file.mkdir();
		 * 
		 * if(xmlObj.getXmlObject() != null){ Map map = (Map)xmlObj.getXmlObject();
		 * Iterator itr = map.keySet().iterator(); while(itr.hasNext()){ String key =
		 * itr.next().toString();
		 * 
		 * String content = map.get(key).toString(); //content = content.replace("&gt;",
		 * ">").replace("&lt;", "<");
		 * 
		 * FileOutputStream fout = new
		 * FileOutputStream(resourcePath+File.separator+key);
		 * fout.write(content.getBytes()); fout.close(); } }
		 * 
		 * logger.debug("componentshelp xml generated"); } }
		 * 
		 * logger.debug("Xmls generated successfully at time : " + new Date()); } catch
		 * (Exception e) { e.printStackTrace();
		 * logger.error("Unable to generate config xmls due to error : " +
		 * e.getMessage()); }
		 */}

	public static void checkRightClickPageDivId(Context ctx, String divajaxpostionid, String fieldid) {
		try {
			if (ctx.get("operationType") == null)
				return;

			if (ctx.get("operationType").toString().equals("I")) {
				String collapserightclickpageid = ctx.get("collapserightclickpageid") != null
						? ctx.get("collapserightclickpageid").toString()
						: null;

				if (collapserightclickpageid == null)
					collapserightclickpageid = divajaxpostionid + ":" + ctx.get(fieldid);
				else {
					collapserightclickpageid = ctx.get("collapserightclickpageid").toString();

					collapserightclickpageid = collapserightclickpageid + "," + divajaxpostionid + ":"
							+ ctx.get(fieldid);
				}

				ctx.put("collapserightclickpageid", collapserightclickpageid);
			} else if (ctx.get("operationType").toString().equals("D")) {
				String collapserightclickpageid = ctx.get("collapserightclickpageid") != null
						? ctx.get("collapserightclickpageid").toString()
						: null;

				if (collapserightclickpageid != null) {
					StringTokenizer tokens = new StringTokenizer(collapserightclickpageid, ",");

					String newTokens = null;
					while (tokens.hasMoreTokens()) {
						String token = tokens.nextToken();

						if (token.equals(divajaxpostionid + ":" + ctx.get(fieldid))) {
							continue;
						}

						if (newTokens == null)
							newTokens = token;
						else
							newTokens = newTokens + "," + token;
					}

					ctx.put("collapserightclickpageid", newTokens);
				}
			}
		} catch (Exception e) {
			logger.error("Error in AccountUtils.checkRightClickPageDivId", e);
		}
	}

	public static void validateAllFlagsCleared(Context ctx) {
		int css = 0;
		if (ctx.get("clearstatuscount") != null) {
			css = (Integer) ctx.get("clearstatuscount");
		}

		try {
			if (css > 0) {
				populateError(ctx, "flagsNotCleard", "All Referrals flags are not cleared for the policy.");

			}
			if (ctx.get("isPricingApproved") == null || (ctx.get("isPricingApproved").toString().equals("N")
					&& !ctx.get("PricingStatus").toString().equals("AI"))) {
				AccountUtils.populateError(ctx, "pricingNotCleard", "Pricing still needs to be apprve..");
				populateError(ctx, "flagsNotCleard", "All Referrals flags are not cleared for the policy.");
			}

		} catch (Exception e) {
			logger.error("Error in AccountUtils.validateAllFlagsCleared", e);

		}

	}

	public static void fetchDataForEmail(Context ctx) {
		{ if (logger.isDebugEnabled()) logger.debug("Exceute"); }
		try {
			String email_notification_event_name = ctx.get("event_name").toString();
			Context newCtx = new Context();
			newCtx.setProject(ctx.getProject());

			newCtx.put("UserNo", ctx.get("UserNo"));
			newCtx.put("PolicyKey", ctx.get("PolicyKey"));
			newCtx.put("event_name", email_notification_event_name);

			// going to get event description from database
			Object obj = SqlResources.getSqlMapProcessor(newCtx)
					.select("SqlStmts.UserStatementManualBoQueriesgetEmailNotificationData", newCtx);
			if (obj != null && obj instanceof List) {
				List list = (List) obj;
				for (int i = 0; i < list.size(); i++) {
					Map map = (Map) list.get(i);

					if (i > 0) {
						ctx.put(HtmlConstants.EMAIL_NOTIFICATION_AGENCY_FROMEMAIL_ADDRESS,
								ctx.get(HtmlConstants.EMAIL_NOTIFICATION_AGENCY_FROMEMAIL_ADDRESS + "_" + i));
						ctx.put(HtmlConstants.EMAIL_NOTIFICATION_AGENCY_TOEMAIL_ADDRESS,
								ctx.get(HtmlConstants.EMAIL_NOTIFICATION_AGENCY_TOEMAIL_ADDRESS + "_" + i));
						ctx.put(HtmlConstants.EMAIL_NOTIFICATION_AGENCY_CCEMAIL_ADDRESS,
								ctx.get(HtmlConstants.EMAIL_NOTIFICATION_AGENCY_CCEMAIL_ADDRESS + "_" + i));
					}

					sendEmailNotification(ctx, map);
				}
			}
		} catch (Exception e) {
			logger.error("Unable to send email notification due to error : " + e.getMessage(), e);
		}

	}

	private static void sendEmailNotification(Context ctx, Map map) throws Exception {
		List attachments = new ArrayList();
		List objAcountInfo = SqlResources.getSqlMapProcessor(ctx)
				.select("SqlStmts.UserStatementManualBoQueriesgetAccountInfoForMail", ctx);
		if (objAcountInfo != null)
			ctx.putAll((Map) objAcountInfo.get(0));
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
		String attachment_content = map.get("attachment_content") != null ? map.get("attachment_content").toString()
				: null;
		ctx.put("Firm_Name", ctx.get("AccountName"));
		ctx.put("Quote_no", ctx.get("QuoteNumber"));
		if (attachment_content != null) {
			String[] attachmentpath = RuleEngineUtility.getTokens(attachment_content, ",");

			for (int i = 0; i < attachmentpath.length; i++)
				attachments.add(htmlDir + attachmentpath[i]);
		}

		String environmentVar = null;
		environmentVar = SystemProperties.getInstance().getString("appl." + ctx.getProject() + ".environment");
		if (!"CyberCoverage_Email_Protexure_ACC".equals(map.get("event_name").toString())
				&& !"UnderwriterAcknowledgementAcc".equals(map.get("event_name").toString())
				&& !"UnderwriterAcknowledgement_DeclineStatus".equals(map.get("event_name").toString()))
			to_role_desc = ctx.get("AccountEmail").toString();
		if (environmentVar.equals("DEV") || environmentVar.equals("QA") || environmentVar.equals("UAT")) {
			from_role_desc = SystemProperties.getInstance().getString("mail.admin.from.address");
			to_role_desc = SystemProperties.getInstance().getString("mail.admin.to.address");
			cc_role_desc = SystemProperties.getInstance().getString("mail.admin.cc.address");

		}

		subject = map.get("subject") != null ? map.get("subject").toString() : HtmlConstants.EMPTY;
		if (!StringUtils.isBlank(subject)) {
			subject = new MessageStringFormat(ctx).format(subject);
		}

		bodyContent = map.get("body") != null ? map.get("body").toString() : HtmlConstants.EMPTY;
		if (!StringUtils.isBlank(bodyContent)) {
			bodyContent = new MessageStringFormat(ctx).format(bodyContent);
		}
		if (ctx.get("isSendMailTrue") != null && !HtmlConstants.EMPTY.equals(ctx.get("isSendMailTrue").toString())
				&& "Y".equals(ctx.get("isSendMailTrue"))) {

			MailSender mailSender = new MailSender();
			mailSender.setContentType("text/html");

			mailSender.setFromAdd(from_role_desc);
			mailSender.setToAdd(to_role_desc);
			mailSender.setBccAdd(cc_role_desc);
			mailSender.setSubject(subject);
			mailSender.setBody(bodyContent);
			if (attachments != null && attachments.size() > 0)
				mailSender.setAttachments(attachments);
			mailSender.sendMail(ctx);

			logger.debug("Mail Sent");
			ctx.remove("email_notification_event_name");
		}
	}

	/*
	 * public static void validateAllPagesSaved(Context ctx) throws Exception {
	 * boolean flag=false; List objFirm =
	 * SqlResources.getSqlMapProcessor(ctx).select(
	 * "SqlStmts.UserStatementdroolQueriesgetFirmDetails", ctx); List
	 * objPolicyTransactionFirm = SqlResources.getSqlMapProcessor(ctx).select(
	 * "SqlStmts.UserStatementdroolQueriesgetPolicyTransaction", ctx); List
	 * objPolicy= SqlResources.getSqlMapProcessor(ctx).select(
	 * "SqlStmts.UserStatementdroolQueriesgetPolicyData", ctx); ctx.putAll((Map)
	 * objFirm.get(0)); ctx.putAll((Map) objPolicyTransactionFirm.get(0));
	 * ctx.putAll((Map) objPolicy.get(0)); List aoplist =
	 * SqlResources.getSqlMapProcessor(ctx).select(
	 * "SqlStmts.UserStatementsaveAccountstmtspopulateAOPFields", ctx); changes List
	 * previousPolicyKey= (List) SqlResources.getSqlMapProcessor(ctx).select(
	 * "SqlStmts.UserStatementdroolQueriesgetPreviousPolicyKey",ctx);
	 * 
	 * ctx.putAll((Map) previousPolicyKey.get(0)); Context previousContext=new
	 * Context(); previousContext.setProject(ctx.getProject());
	 * previousContext.put("PolicyKey",ctx.get("PreviousPolicykey"));
	 * previousContext.put("VersionSequence",ctx.get("previousVersionSequence"));
	 * 
	 * List aopPreviousList =
	 * SqlResources.getSqlMapProcessor(previousContext).select(
	 * "SqlStmts.UserStatementsaveAccountstmtspopulateAOPFields",previousContext);
	 * 
	 * System.out.println("in change..."+ aopPreviousList); Map previousMap = new
	 * HashMap(); if (aopPreviousList != null) { Map map = new HashMap(); for (int i
	 * = 0; i < aopPreviousList.size(); i++) { map = (HashMap)
	 * aopPreviousList.get(i); previousMap.put("AOP_Percentage_" +
	 * map.get("AOPKey"),map.get("PercentageValue")); } }
	 * 
	 * Map covMap = new HashMap(); if (aoplist != null) { Map map = new HashMap();
	 * for (int i = 0; i < aoplist.size(); i++) { map = (HashMap) aoplist.get(i);
	 * covMap.put("AOP_Percentage_" + map.get("AOPKey"),map.get("PercentageValue"));
	 * } } if(ctx.get("PolicyStatusKey")!=null &&
	 * ctx.get("PolicyStatusKey").toString().equals("1") ) {
	 * 
	 * if( (ctx.get("IsBasicPage")!=null &&
	 * !ctx.get("IsBasicPage").toString().equals("Y"))
	 * ||(ctx.get("IsGeneralPage")!=null &&
	 * !ctx.get("IsGeneralPage").toString().equals("Y"))) flag=true; }
	 * if(ctx.get("PolicyStatusKey")!=null &&
	 * ctx.get("PolicyStatusKey").toString().equals("2") ) { if(
	 * ctx.get("RenewBasicInfoPage")!=null &&
	 * !ctx.get("RenewBasicInfoPage").toString().equals("Y")) flag=true; } if
	 * (covMap != null) { int percentageVal1 =covMap.get("AOP_Percentage_5") != null
	 * ? Integer.parseInt(covMap.get("AOP_Percentage_5").toString()):0; int
	 * percentagePreVal1 =previousMap.get("AOP_Percentage_5") != null ?
	 * Integer.parseInt(previousMap.get("AOP_Percentage_5").toString()):0;
	 * 
	 * int percentageVal5 =covMap.get("AOP_Percentage_6") != null ?
	 * Integer.parseInt(covMap.get("AOP_Percentage_6").toString()):0; int
	 * percentagePreVal5 =previousMap.get("AOP_Percentage_6") != null ?
	 * Integer.parseInt(previousMap.get("AOP_Percentage_6").toString()):0;
	 * 
	 * int percentageVal2 =covMap.get("AOP_Percentage_14") != null ?
	 * Integer.parseInt(covMap.get("AOP_Percentage_14").toString()):0; int
	 * percentagePreVal2 =previousMap.get("AOP_Percentage_14") != null ?
	 * Integer.parseInt(previousMap.get("AOP_Percentage_14").toString()):0;
	 * 
	 * int percentageVal3= covMap.get("AOP_Percentage_18") != null ?
	 * Integer.parseInt(covMap.get("AOP_Percentage_18").toString()):0; int
	 * percentagePreVal3= previousMap.get("AOP_Percentage_18") != null ?
	 * Integer.parseInt(previousMap.get("AOP_Percentage_18").toString()):0;
	 * 
	 * int percentageVal4 =covMap.get("AOP_Percentage_19") != null ?
	 * Integer.parseInt(covMap.get("AOP_Percentage_19").toString()):0; int
	 * percentagePreVal4 =previousMap.get("AOP_Percentage_19") != null ?
	 * Integer.parseInt(previousMap.get("AOP_Percentage_19").toString()):0;
	 * 
	 * 
	 * if (percentageVal1 > 0 || percentageVal2 > 0 || percentageVal3 > 0 ||
	 * percentageVal4 > 0) { if(percentageVal1 > 0 && percentageVal5 > 0) if
	 * ((percentageVal1 - percentagePreVal1 >= 5 || (percentagePreVal1==0 &&
	 * percentageVal1>percentagePreVal1))&& (percentageVal5 - percentagePreVal5 >= 5
	 * || (percentagePreVal5==0 && percentageVal5>percentagePreVal5))) {
	 * if(ctx.get("IsAttesationPage")!=null &&
	 * !ctx.get("IsAttesationPage").toString().equals("Y") ) flag=true; }
	 * if(percentageVal2 > 0 ) if (percentageVal2 - percentagePreVal2 >= 5 ||
	 * (percentagePreVal2==0 && percentageVal2>percentagePreVal2)) {
	 * if(ctx.get("IsComputerServicePage")!=null &&
	 * !ctx.get("IsComputerServicePage").toString().equals("Y") ) flag=true; }
	 * if(percentageVal3 > 0 ) if (percentageVal3 - percentagePreVal3 >= 5 ||
	 * (percentagePreVal3==0 && percentageVal3>percentagePreVal3)) {
	 * if(ctx.get("IsBusinessFundPage")!=null &&
	 * !ctx.get("IsBusinessFundPage").toString().equals("Y") ) flag=true; }
	 * if(percentageVal4 > 0 ) if (percentageVal4 - percentagePreVal4 >= 5 ||
	 * (percentagePreVal4==0 && percentageVal4>percentagePreVal4)) {
	 * if(ctx.get("IsSupplementPage")!=null &&
	 * !ctx.get("IsSupplementPage").toString().equals("Y") ) flag=true; } } if(flag)
	 * { AccountUtils.populateError(ctx,
	 * "StatusKey","Please complete all  pages for Quote Options.");
	 * 
	 * }
	 * 
	 * } }
	 */
	public static void validateAllPagesSaved(Context ctx) throws Exception {
		boolean flag = false;
		List objFirm = SqlResources.getSqlMapProcessor(ctx).select("SqlStmts.UserStatementdroolQueriesgetFirmDetails",
				ctx);
		ctx.putAll((Map) objFirm.get(0));
		List objPolicy = SqlResources.getSqlMapProcessor(ctx).select("SqlStmts.UserStatementdroolQueriesgetPolicyData",
				ctx);
		ctx.putAll((Map) objPolicy.get(0));
		List aoplist = SqlResources.getSqlMapProcessor(ctx)
				.select("SqlStmts.UserStatementsaveAccountstmtspopulateAOPFields", ctx);
		Map covMap = new HashMap();
		if (aoplist != null) {
			Map map = new HashMap();
			for (int i = 0; i < aoplist.size(); i++) {
				map = (HashMap) aoplist.get(i);
				covMap.put("AOP_Percentage_" + map.get("AOPKey"), map.get("PercentageValue"));
			}
		}
		boolean isOldFlow = false;
		Object obj = RuleUtils.executeRule(ctx, "AccountantRule.oldAppFlow");
		if (obj != null && obj instanceof Boolean) {
			isOldFlow = (Boolean) obj;
		}
		if (isOldFlow) {
			if (ctx.get("PolicyStatusKey") != null && ctx.get("PolicyStatusKey").toString().equals("1")) {

				if ((ctx.get("IsBasicPage") != null && !ctx.get("IsBasicPage").toString().equals("Y"))
						|| (ctx.get("IsGeneralPage") != null && !ctx.get("IsGeneralPage").toString().equals("Y"))) {
					flag = true;
				}
				if (covMap != null) {
					int percentageVal1 = covMap.get("AOP_Percentage_5") != null
							? Integer.parseInt(covMap.get("AOP_Percentage_5").toString())
							: 0;
					int percentageVal5 = covMap.get("AOP_Percentage_6") != null
							? Integer.parseInt(covMap.get("AOP_Percentage_6").toString())
							: 0;
					int percentageVal2 = covMap.get("AOP_Percentage_14") != null
							? Integer.parseInt(covMap.get("AOP_Percentage_14").toString())
							: 0;
					int percentageVal3 = covMap.get("AOP_Percentage_18") != null
							? Integer.parseInt(covMap.get("AOP_Percentage_18").toString())
							: 0;
					int percentageVal4 = covMap.get("AOP_Percentage_19") != null
							? Integer.parseInt(covMap.get("AOP_Percentage_19").toString())
							: 0;
					if (percentageVal1 > 0 || percentageVal2 > 0 || percentageVal3 > 0 || percentageVal4 > 0) {
						if (percentageVal1 > 0 && percentageVal5 > 0) {
							if (ctx.get("IsAttesationPage") != null
									&& !ctx.get("IsAttesationPage").toString().equals("Y"))
								flag = true;
						}
						if (percentageVal2 > 0) {
							if (ctx.get("IsComputerServicePage") != null
									&& !ctx.get("IsComputerServicePage").toString().equals("Y"))
								flag = true;
						}
						if (percentageVal3 > 0) {
							if (ctx.get("IsBusinessFundPage") != null
									&& !ctx.get("IsBusinessFundPage").toString().equals("Y"))
								flag = true;
						}
						if (percentageVal4 > 0) {
							if (ctx.get("IsSupplementPage") != null
									&& !ctx.get("IsSupplementPage").toString().equals("Y"))
								flag = true;
						}
					}

				}
			}

			if (ctx.get("PolicyStatusKey") != null && ctx.get("PolicyStatusKey").toString().equals("2")) {
				List previousPolicyKey = (List) SqlResources.getSqlMapProcessor(ctx)
						.select("SqlStmts.UserStatementdroolQueriesgetPreviousPolicyKey", ctx);
				ctx.putAll((Map) previousPolicyKey.get(0));
				Context previousContext = new Context();
				previousContext.setProject(ctx.getProject());
				previousContext.put("PolicyKey", ctx.get("PreviousPolicykey"));
				previousContext.put("VersionSequence", ctx.get("previousVersionSequence"));

				List aopPreviousList = SqlResources.getSqlMapProcessor(previousContext)
						.select("SqlStmts.UserStatementsaveAccountstmtspopulateAOPFields", previousContext);
				{ if (logger.isDebugEnabled()) logger.debug("in change..." + aopPreviousList); }
				Map previousMap = new HashMap();
				if (aopPreviousList != null) {
					Map map = new HashMap();
					for (int i = 0; i < aopPreviousList.size(); i++) {
						map = (HashMap) aopPreviousList.get(i);
						previousMap.put("AOP_Percentage_" + map.get("AOPKey"), map.get("PercentageValue"));
					}
				}
				if (ctx.get("RenewBasicInfoPage") != null && !ctx.get("RenewBasicInfoPage").toString().equals("Y"))
					flag = true;
				if (covMap != null) {
					int percentageVal1 = covMap.get("AOP_Percentage_5") != null
							? Integer.parseInt(covMap.get("AOP_Percentage_5").toString())
							: 0;
					int percentagePreVal1 = previousMap.get("AOP_Percentage_5") != null
							? Integer.parseInt(previousMap.get("AOP_Percentage_5").toString())
							: 0;

					int percentageVal5 = covMap.get("AOP_Percentage_6") != null
							? Integer.parseInt(covMap.get("AOP_Percentage_6").toString())
							: 0;
					int percentagePreVal5 = previousMap.get("AOP_Percentage_6") != null
							? Integer.parseInt(previousMap.get("AOP_Percentage_6").toString())
							: 0;

					int percentageVal2 = covMap.get("AOP_Percentage_14") != null
							? Integer.parseInt(covMap.get("AOP_Percentage_14").toString())
							: 0;
					int percentagePreVal2 = previousMap.get("AOP_Percentage_14") != null
							? Integer.parseInt(previousMap.get("AOP_Percentage_14").toString())
							: 0;

					int percentageVal3 = covMap.get("AOP_Percentage_18") != null
							? Integer.parseInt(covMap.get("AOP_Percentage_18").toString())
							: 0;
					int percentagePreVal3 = previousMap.get("AOP_Percentage_18") != null
							? Integer.parseInt(previousMap.get("AOP_Percentage_18").toString())
							: 0;

					int percentageVal4 = covMap.get("AOP_Percentage_19") != null
							? Integer.parseInt(covMap.get("AOP_Percentage_19").toString())
							: 0;
					int percentagePreVal4 = previousMap.get("AOP_Percentage_19") != null
							? Integer.parseInt(previousMap.get("AOP_Percentage_19").toString())
							: 0;

					if (percentageVal1 > 0 || percentageVal2 > 0 || percentageVal3 > 0 || percentageVal4 > 0) {

						if ((percentageVal1 - percentagePreVal1 >= 5
								|| (percentagePreVal1 == 0 && percentageVal1 > percentagePreVal1))
								&& (percentageVal5 - percentagePreVal5 >= 5
										|| (percentagePreVal5 == 0 && percentageVal5 > percentagePreVal5))) {
							if (ctx.get("IsAttesationPage") != null
									&& !ctx.get("IsAttesationPage").toString().equals("Y"))
								flag = true;
						}
						if (percentageVal2 - percentagePreVal2 >= 5
								|| (percentagePreVal2 == 0 && percentageVal2 > percentagePreVal2)) {
							if (ctx.get("IsComputerServicePage") != null
									&& !ctx.get("IsComputerServicePage").toString().equals("Y"))
								flag = true;
						}

						if (percentageVal3 - percentagePreVal3 >= 5
								|| (percentagePreVal3 == 0 && percentageVal3 > percentagePreVal3)) {
							if (ctx.get("IsBusinessFundPage") != null
									&& !ctx.get("IsBusinessFundPage").toString().equals("Y"))
								flag = true;
						}

						if (percentageVal4 - percentagePreVal4 >= 5
								|| (percentagePreVal4 == 0 && percentageVal4 > percentagePreVal4)) {
							if (ctx.get("IsSupplementPage") != null
									&& !ctx.get("IsSupplementPage").toString().equals("Y"))
								flag = true;
						}
					}

				}

			}
		} else {
			List detailsObj = (List) SqlResources.getSqlMapProcessor(ctx)
					.select("Firm.ValideApplicationForCompleteionAcc_p", ctx);
			ctx.putAll((Map) detailsObj.get(0));
			String isAppFilledCompleted = ctx.get("isAppFullyCompleted") != null
					&& !ctx.get("isAppFullyCompleted").equals(HtmlConstants.EMPTY)
							? ctx.get("isAppFullyCompleted").toString()
							: "N";
			if (isAppFilledCompleted.equals("N"))
				flag = true;
		}
		if (flag) {
			AccountUtils.populateError(ctx, "StatusKey", "Please complete application.");

		}

	}

	public static void compareReferrals(Context ctx) {
		Object obj = null;
		try {
			List firmDetails = SqlResources.getSqlMapProcessor(ctx)
					.select("SqlStmts.UserStatementdroolQueriesgetFirmDetails", ctx);
			ctx.putAll((Map) firmDetails.get(0));
			Context newCtx = new Context();
			newCtx.setProject(ctx.getProject());
			newCtx.put("PolicyKey", ctx.get("PolicyKey"));
			List getCompleteReferralCount = SqlResources.getSqlMapProcessor(newCtx)
					.select("SqlStmts.UserStatementManualBoQueriesgetCompleteReferralCount", newCtx);
			newCtx.putAll((Map) getCompleteReferralCount.get(0));
			List getReferralCountForAI = SqlResources.getSqlMapProcessor(newCtx)
					.select("SqlStmts.UserStatementManualBoQueriesgetReferralCountForAI", newCtx);
			newCtx.putAll((Map) getReferralCountForAI.get(0));
			List triggredReferrals = SqlResources.getSqlMapProcessor(newCtx)
					.select("SqlStmts.UserStatementManualBoQueriesgetCurrentTriggeredReferralsCount", newCtx);
			newCtx.putAll((Map) triggredReferrals.get(0));
			List historyReferrals = SqlResources.getSqlMapProcessor(newCtx)
					.select("SqlStmts.UserStatementManualBoQueriesgetReferralHistoryCount", newCtx);
			newCtx.putAll((Map) historyReferrals.get(0));
			ctx.putAll((Map) historyReferrals.get(0));
			List NewReferralCount = SqlResources.getSqlMapProcessor(newCtx)
					.select("SqlStmts.UserStatementManualBoQueriesgetCurrentApprovedReferralsCount", newCtx);
			newCtx.putAll((Map) NewReferralCount.get(0));
			List allReferralsCleared = SqlResources.getSqlMapProcessor(newCtx)
					.select("SqlStmts.UserStatementManualBoQueriesgetAllClearedReferrals", newCtx);
			newCtx.putAll((Map) allReferralsCleared.get(0));
			List getReferralCountForAIPricing = SqlResources.getSqlMapProcessor(newCtx)
					.select("SqlStmts.UserStatementManualBoQueriesgetReferralCountForAIPricing", newCtx);
			newCtx.putAll((Map) getReferralCountForAIPricing.get(0));
			obj = SqlResources.getSqlMapProcessor(newCtx)
					.findByKey("SqlStmts.UserStatementManualBoQueriesgetReferralClearSubjectiveCount", newCtx);
			if (obj != null && obj instanceof Map)
				ctx.putAll((Map) obj);
			int totalReferralCount = Integer.valueOf(newCtx.get("totalReferralCount").toString());
			int AICount = Integer.valueOf(newCtx.get("AICount").toString());
			int totalReferralsCount = Integer.valueOf(newCtx.get("totalReferralTrigered").toString());
			int historyReferralsCount = Integer.valueOf(newCtx.get("ReferralCountHistory").toString());
			int newReferralCount = Integer.valueOf(newCtx.get("NewReferralCount").toString());
			int noClearedReferralCount = Integer.valueOf(newCtx.get("noClearedReferralCount").toString());
			int AIPricingCount = Integer.valueOf(newCtx.get("PricingAICount").toString());
			ctx.put("noClearedReferralCount", noClearedReferralCount);

			newCtx.put("user_id", ctx.get("user_id"));
			if (ctx.get("User") != null && "insured".equals(ctx.get("User").toString()) && totalReferralCount == 0) {
				newCtx.put("isPricingApproved", "Y");
				ctx.put("isPricingApproved", "Y");
			} else
				newCtx.put("isPricingApproved", ctx.get("isPricingApproved"));

			if (noClearedReferralCount == 0 && ctx.get("isPricingApproved").toString().equals("Y")) {
				newCtx.put("FollowUnderwriterFlow", "N");
				ctx.put("FollowUnderwriterFlow", "N");
			}
			newCtx.put("FollowUnderwriterFlow", ctx.get("FollowUnderwriterFlow"));
			newCtx.put("isRatingsChnaged", ctx.get("isRatingsChnaged"));
			newCtx.put("isComingFromUWFlow", ctx.get("isComingFromUWFlow"));
			ctx.put("isShowUWReview", "YES");
			ctx.put("AICount", AICount);
			// if((historyReferralsCount!=0) &&
			// (totalReferralsCount!=historyReferralsCount))
			if (newReferralCount > 0
					|| ((historyReferralsCount != 0) && (totalReferralsCount != historyReferralsCount))) {
				// isPricingApproved='N',FollowUnderwriterFlow='Y',isRatingsChnaged='Y'
				newCtx.put("isPricingApproved", "N");
				newCtx.put("FollowUnderwriterFlow", "Y");
				newCtx.put("isRatingsChnaged", "N");
				newCtx.put("isComingFromUWFlow", "YES");
				ctx.put("isShowUWReview", "YES");
			}

			if (ctx.get("isRatingsChnaged") != null && ctx.get("isRatingsChnaged").toString().equals("Y")) {
				newCtx.put("isPricingApproved", "N");
				newCtx.put("FollowUnderwriterFlow", "Y");
				newCtx.put("isRatingsChnaged", "Y");
				newCtx.put("isComingFromUWFlow", "NO");
				ctx.put("isShowUWReview", "YES");
			}
			if (AICount > 0 || AIPricingCount > 0) {
				newCtx.put("FollowUnderwriterFlow", "Y");
			}
			// if( && ctx.get("isPricingApproved").toString().equals("Y") )
			if (newReferralCount == 0 && (totalReferralsCount == historyReferralsCount)
					&& ctx.get("isPricingApproved").toString().equals("Y")
					&& ctx.get("FollowUnderwriterFlow").toString().equals("N") && AICount == 0) {

				newCtx.put("isPricingApproved", "Y");
				newCtx.put("FollowUnderwriterFlow", "N");
				newCtx.put("isRatingsChnaged", "N");
				newCtx.put("isComingFromUWFlow", "NO");
				if (ctx.get("StatusKey") != null && ctx.get("StatusKey").toString().equals("3"))
					ctx.put("isShowUWReview", "YES");
				else
					ctx.put("isShowUWReview", "NO");
			}
			RuleUtils.executeRule(newCtx, "AccountantRule.assignTimeStampAndUser");
			SqlResources.getSqlMapProcessor(newCtx).update("SqlStmts.UserStatementManualBoQueriesresetRatingsAndUWflow",
					newCtx);
		} catch (Exception e) {
			logger.error("Error in AccountUtils.compareReferrals", e);

		}
	}

	public static void checkAllReferralsCleared(Context ctx) {
		int count = 0;
		String user = ctx.get("User") != null && !ctx.get("User").equals(HtmlConstants.EMPTY) ? ctx.get("User").toString()
						: "N";
		if (ctx.get("unClearedFLagCount") != null) {
			count = Integer.valueOf(ctx.get("unClearedFLagCount").toString());
		}

		try {
			if (count > 0) {
				if("insured".equals(user))
					AccountUtils.populateError(ctx, "referalsFlagsNotCleard","The application review process has not been completed. Please contact Protexure by calling 1-888-803-9898.");
				else
					AccountUtils.populateError(ctx, "referalsFlagsNotCleard","All Referrals Flags are not cleared for the policy.");
			}

		} catch (Exception e) {
			logger.error("Error in AccountUtils.checkAllReferralsCleared", e);

		}

	}

	public static void validateSavedPageManually(Context ctx) {
		try {
			if (ctx.get("StatusKey") != null && ctx.get("StatusKey").toString().equals("9")) {
				AccountUtils.validateAllPagesSaved(ctx);
				RuleUtils.executeRule(ctx, "AccountantRule.isNotIssuedAndAssignFirstReviewer");
				if ((Boolean) RuleUtils.executeRule(ctx, "AccountantRule.isInetErrorListEmpty")) {
					SqlResources.getSqlMapProcessor(ctx)
							.update("SqlStmts.UserStatementManualBoQueriesupdateStatusToUWreview", ctx);
				}
			}
		} catch (Exception e) {
			logger.error("Error in AccountUtils.validateSavedPageManually", e);
		}

	}

	public static void validateIsAppCompletlyFilled(Context ctx) {
		try {
			new SetParametersForStoredProcedures().setParametersInContext(ctx, "PolicyKey,AccountKey");
			Object obj = SqlResources.getSqlMapProcessor(ctx)
					.findByKey("AreaOfPractice.ValidateIsAppCompletedAccountIns_p", ctx);
			if (obj != null && obj instanceof Map) {
				Map map1 = (Map) obj;
				ctx.putAll(map1);
			}
			String isAppFilledCompleted = ctx.get("CompletlyFilled") != null
					&& !ctx.get("CompletlyFilled").equals(HtmlConstants.EMPTY) ? ctx.get("CompletlyFilled").toString()
							: "N";
			ctx.put("isAppFilledCompleted", isAppFilledCompleted);
			if ("N".equals(isAppFilledCompleted))
				AccountUtils.populateError(ctx, "incompleteApp", "Please complete the application.");

		} catch (Exception e) {
			logger.error("Error in AccountUtils.validateIsAppCompletlyFilled", e);
		}

	}

	public static void setQuickQuoteImagesPathToTemplate(IContext ctx) throws Exception {
		String homePath = SystemProperties.getInstance().getString("appl.home.dir");
		String quickQuoteImage2 = homePath + "image//line1.PNG";
		String quickQuoteImage14 = homePath + "image//line2.PNG";
		String quickQuoteImage3 = homePath + "image//indvidual.PNG";
		String quickQuoteImage1 = homePath + "image//1.PNG";
		String quickQuoteImage4 = homePath + "image//3.PNG";
		String quickQuoteImage5 = homePath + "image//4.PNG";
		String quickQuoteImage31 = homePath + "image//5.PNG";
		String quickQuoteImage20 = homePath + "image//6.PNG";
		String quickQuoteImage21 = homePath + "image//7.PNG";
		String quickQuoteImage6 = homePath + "image//n1.PNG";
		String quickQuoteImage7 = homePath + "image//n2.PNG";
		String quickQuoteImage8 = homePath + "image//n3.PNG";
		String quickQuoteImage9 = homePath + "image//n4.PNG";
		String quickQuoteImage10 = homePath + "image//n5.PNG";
		String quickQuoteImage11 = homePath + "image//n6.PNG";
		String quickQuoteImage12 = homePath + "image//n7.PNG";
		String quickQuoteImage13 = homePath + "image//n8.PNG";
		String quickQuoteImage25 = homePath + "image//n9.PNG";
		String quickQuoteImage36 = homePath + "image//n10.PNG";
		String quickQuoteImage37 = homePath + "image//n11.PNG";
		String quickQuoteImage38 = homePath + "image//n12.PNG";

		ctx.put("quickQuoteImage1", quickQuoteImage1);
		ctx.put("quickQuoteImage2", quickQuoteImage2);
		ctx.put("quickQuoteImage3", quickQuoteImage3);
		ctx.put("quickQuoteImage4", quickQuoteImage4);
		ctx.put("quickQuoteImage5", quickQuoteImage5);
		ctx.put("quickQuoteImage6", quickQuoteImage6);
		ctx.put("quickQuoteImage7", quickQuoteImage7);
		ctx.put("quickQuoteImage8", quickQuoteImage8);
		ctx.put("quickQuoteImage9", quickQuoteImage9);
		ctx.put("quickQuoteImage10", quickQuoteImage10);
		ctx.put("quickQuoteImage11", quickQuoteImage11);
		ctx.put("quickQuoteImage12", quickQuoteImage12);
		ctx.put("quickQuoteImage13", quickQuoteImage13);
		ctx.put("quickQuoteImage14", quickQuoteImage14);
		ctx.put("quickQuoteImage20", quickQuoteImage20);
		ctx.put("quickQuoteImage21", quickQuoteImage21);
		ctx.put("quickQuoteImage25", quickQuoteImage25);
		ctx.put("quickQuoteImage31", quickQuoteImage31);
		ctx.put("quickQuoteImage36", quickQuoteImage36);
		ctx.put("quickQuoteImage37", quickQuoteImage37);
		ctx.put("quickQuoteImage38", quickQuoteImage38);
	}

	public static void printQuickQuoteContent(IContext ctx) {

		HttpServletRequest request = (HttpServletRequest) ctx.get("DocumentRequest");
		HttpServletResponse response = (HttpServletResponse) ctx.get("DocumentResponse");
		try {
			String htmlDir = SystemProperties.getInstance().getString("html.basedir");
			String filePath = htmlDir + "data\\QuickQuoteOptions_" + ctx.get("QuoteNumber").toString() + ".pdf";

			String foFile = SystemProperties.getInstance().getString("xsl.filepath") + "quickquote.xsl";

			String environment = SystemProperties.getInstance().getString("appl.AccountantIns.environment.production");
			ctx.put("environmentproduction", environment);
			setQuickQuoteImagesPathToTemplate(ctx);
			new XML2PDF().convertPOToPDF(foFile, new StringBuffer(new DownloadForm().generateDataXml((Context) ctx)),
					filePath);

			exportFileDownLoad(filePath, response);
			if (filePath != null && !filePath.isEmpty()) {
				deleteDirectory(new File(filePath));
			}
		} catch (Exception e) {
			logger.error("Error in AccountUtils.printQuickQuoteContent", e);
		}
	}

	public static void exportFileDownLoad(String filePath, HttpServletResponse response) {

		try {

			if (filePath != null && filePath != "" && filePath != "null") {

				// System.out.println(filePath);
				File downloadFile = new File(filePath);

				if (downloadFile.exists()) {

					FileInputStream inStream = new FileInputStream(downloadFile);

					// gets MIME type of the file
					String mimeType = "application/pdf";
					// System.out.println("MIME type: " + mimeType);

					// modifies response
					response.setContentType(mimeType);
					response.setContentLength((int) downloadFile.length());

					// forces download
					String headerKey = "Content-Disposition";
					String headerValue = String.format("inline; filename=\"%s\"", downloadFile.getName());// attachment
					response.setHeader(headerKey, headerValue);
					response.setHeader("Expires", "0");
					response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
					// System.out.println(headerKey+" fgfgfgf "+ headerValue);

					OutputStream outStream = response.getOutputStream();

					byte[] buffer = new byte[(int) downloadFile.length()];
					int bytesRead = -1;

					while ((bytesRead = inStream.read(buffer)) != -1) {
						outStream.write(buffer, 0, bytesRead);
					}

					inStream.close();
					outStream.close();

					// System.out.println("sssssss");
				}
			}
		} catch (Exception ee) {
			logger.error("Error in AccountUtils.exportFileDownLoad", ee);
		}
	}

	public static boolean deleteDirectory(File dir) {
		if (dir.isDirectory()) {
			File[] children = dir.listFiles();
			for (int i = 0; i < children.length; i++) {
				boolean success = deleteDirectory(children[i]);
				if (!success) {
					return false;
				}
			}
		}
		return dir.delete();
	}

	public static void convertPolicyExpirationDate(Context ctx) throws Exception {
		try {
			if (ctx.get("PolicyExpirationDate") != null) {

				String sDate1 = (String) ctx.get("PolicyExpirationDate");
				Date date1 = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy").parse(sDate1);
				{ if (logger.isDebugEnabled()) logger.debug(sDate1 + "\t" + date1); }
				ctx.put("PolicyExpirationDate", date1);
			}
		} catch (Exception e) {
			logger.error("Error in AccountUtils.convertPolicyExpirationDate", e);
//			e.printStackTrace();
		}
	}

	public static void processAcknowledgement(Context ctx) {
		{ if (logger.isDebugEnabled()) logger.debug("Response came back from external service for policykey= " + ctx.get("PolicyKey")); }

	}

	/// Indication IN jack

	public void checkBusiness(IContext ctx) throws Exception {

		if (ctx.get("StateCode") != null && !"".equals(ctx.get("StateCode").toString())) {
			Object obj = SqlResources.getSqlMapProcessor(ctx).findByKey("State.findByKey", ctx);
			if (obj != null && obj instanceof Map) {
				Map map = (Map) obj;
				ctx.put("IsBusinessActive", map.get("IsBusinessActive"));
			}
		}
	}

	public static void checkNewFiling(IContext ctx) throws Exception {
		// To set new filing flag
		String dt = ctx.get("PolicyEffectiveDate") == null ? "" : ctx.get("PolicyEffectiveDate").toString();
		boolean isRatingNew = false;
		Object objRatingRule = RuleUtils.executeRule(ctx, "AccountantRule.checkNewFiling");
		if (objRatingRule != null && objRatingRule instanceof Boolean) {
			isRatingNew = (Boolean) objRatingRule;
		}
		if (isRatingNew)
			ctx.put("IsNewFiling", "Y");
		else
			ctx.put("IsNewFiling", "N");

		// Signing Form State MT if final quote is defense inside
		/*
		 * if("MT".equals(ctx.get("StateCode").toString())){ String IsClaimExpensesType
		 * = "N"; Object objFinalisedQuote =
		 * SqlResources.getSqlMapProcessor(ctx).findByKey(
		 * "SqlStmts.UserStatementformgetFinalisedQuote", ctx); Map mapFinalisedQuote =
		 * null;
		 * 
		 * if (objFinalisedQuote != null && objFinalisedQuote instanceof Map)
		 * mapFinalisedQuote = (Map) objFinalisedQuote;
		 * 
		 * if (mapFinalisedQuote != null && !mapFinalisedQuote.isEmpty()) { if
		 * (mapFinalisedQuote.get("IsClaimExpensesType") != null) IsClaimExpensesType =
		 * mapFinalisedQuote.get("IsClaimExpensesType").toString(); }
		 * 
		 * if("N".equals(IsClaimExpensesType)){ ctx.put("IsSignFormMT", "Y"); } }
		 */
	}

	public static void populateIndication(IContext ctx) throws Exception {
		ctx.get("IsClaimExpensesType");
		ctx.get("IsDollarDefense");

		ctx.put("showUnderwriterLink", "N");
		ctx.put("SchduleRatingModifier1", ctx.get("TotalPolicyModifierPercentage"));
		ctx.put("TotalPremium", ctx.get("TotalPremium"));
		ctx.put("InvoicedPremium", ctx.get("InvoicedPremium"));
		ctx.put("XmlOutputDatafromRating", ctx.get("XmlOutputDatafromRating"));

		// Refresh County
		/*
		 * if(ctx.get("countyChanged") != null &&
		 * "Y".equals((String)ctx.get("countyChanged"))){ ctx.remove("CountyDesc");
		 * ctx.remove("City"); }
		 */

	}

	public static boolean validateCounty(Context ctx) {
		boolean flag = true;
		String state = null;
		String county = "";
		if (ctx.get("StateCode") != null)
			state = ctx.get("StateCode").toString();
		if (ctx.get("CountyDesc") != null)
			county = ctx.get("CountyDesc").toString();

		for (int i = 0; i < countyRequiredStates.length; i++) {
			if (countyRequiredStates[i].equals(state) && ("".equals(county) || "Select".equals(county)))
				flag = false;
		}

		/*
		 * if(!flag){ try { populateError(ctx, "CountyDesc",
		 * "County is a required field."); } catch (Exception e) { e.printStackTrace();
		 * } }
		 */
		return flag;
	}

	public static void validateAopPercentageIndication(Context ctx) throws Exception {
		validateHistory(ctx);
		if (ctx.get("StateCode") == null || "".equals(ctx.get("StateCode").toString())) {
			populateError(ctx, "StateCode", "Please select any state.");
		}
		// if(orCondition(isGreaterThan(:SchduleRatingModifier1,25),isLessThan(:SchduleRatingModifier1,-25))=>isEqual(1,2)->isEqual(1,1))
		if (ctx.get("SchduleRatingModifier1") != null && !"".equals(ctx.get("SchduleRatingModifier1"))) {
			if (Double.parseDouble(ctx.get("SchduleRatingModifier1").toString()) > 25
					|| Double.parseDouble(ctx.get("SchduleRatingModifier1").toString()) < -25) {
				populateError(ctx, "SchduleRatingModifier1", "Schedule modifier must exist between -25 and +25.");
			}
		}
		List aopList = SqlResources.getSqlMapProcessor(ctx).select("AOP.GetAOPFieldsIndication_p", ctx);
		Context ctx1;
		int total = 0, percentage = 0;
		if (aopList != null) {

			try {
				Map map = new HashMap();
				for (int i = 0; i < aopList.size(); i++) {
					map = (HashMap) aopList.get(i);
					String percentageValue = ctx.get("AOP_Percentage_" + map.get("AOPKey").toString()) == null ? null
							: ctx.get("AOP_Percentage_" + map.get("AOPKey").toString()).toString().trim();

					if (percentageValue == null || "".equals(percentageValue) || "0".equals(percentageValue))
						percentageValue = "0";

					// logger.debug("AOP_Percentage_" + map.get("AOPKey").toString() + " ....
					// percentageValue" + percentageValue);
					percentage = Integer.parseInt(percentageValue);
					total = total + percentage;
				}
				if (total > 100 || total < 100) {
					populateError(ctx, "totalPercentageError",
							"The total AOP percentage is required to equal 100%, current total is " + total + "%");
				}
			} catch (Exception e) {
				logger.error("Error in AccountUtils.validateAopPercentageIndication", e);
			}
		}
	}

	public void processIndication(IContext ctx) throws Exception {

		String IsPolicyDetailChanged = "N";
		String IsPolicyCoverageChanged = "N";
		String IsAccountDetailChanged = "N";
		String IsIndicationBatchUpdated = "N";
		boolean accFlag = true;
		boolean polFlag = true;
		boolean covFlag = true;
		boolean modifierFlag = true;

		// To check changes in existing policy
		if (ctx.get("IsPolicyCreatedNew") == null
				|| (ctx.get("IsPolicyCreatedNew") != null && "N".equals(ctx.get("IsPolicyCreatedNew").toString()))) {
			Object objPolicy = SqlResources.getSqlMapProcessor(ctx).findByKey("Policy.findByKey", ctx);
			Map mapPolicy = new HashMap();
			if (objPolicy != null)
				mapPolicy = (Map) objPolicy;

			Object objFirmLW = SqlResources.getSqlMapProcessor(ctx).findByKey("Firm.findByKey", ctx);
			Map mapFirmLW = new HashMap();
			if (objFirmLW != null)
				mapFirmLW = (Map) objFirmLW;

			Object objAccount = SqlResources.getSqlMapProcessor(ctx)
					.findByKey("SqlStmts.indicationinjackviewgetAccountData", ctx);
			Map mapAccount = new HashMap();
			if (objAccount != null)
				mapAccount = (Map) objAccount;

			Object objAddress = SqlResources.getSqlMapProcessor(ctx)
					.findByKey("SqlStmts.indicationinjackviewgetAddressdetails", ctx);
			Map mapAddress = new HashMap();
			if (objAddress != null)
				mapAddress = (Map) objAddress;

			Object objPolicyCoverage = SqlResources.getSqlMapProcessor(ctx)
					.findByKey("SqlStmts.indicationinjackviewgetPolicyCoverage", ctx);
			Map mapPolicyCoverage = new HashMap();
			if (objPolicyCoverage != null)
				mapPolicyCoverage = (Map) objPolicyCoverage;

			Object objGrossRevenue = SqlResources.getSqlMapProcessor(ctx)
					.findByKey("SqlStmts.indicationinjackviewgetGrossRevenueForFirmData", ctx);
			Map mapGrossRevenue = new HashMap();
			if (objGrossRevenue != null)
				mapGrossRevenue = (Map) objGrossRevenue;

			Object objProfLiab = SqlResources.getSqlMapProcessor(ctx)
					.findByKey("ProfessionalLiabilityInsDetail.findByKey", ctx);
			Map mapProfLiab = new HashMap();
			if (objProfLiab != null)
				mapProfLiab = (Map) objProfLiab;

			// To check Account changes
			if (!mapAccount.isEmpty()) {
				if (accFlag)
					accFlag = compareValues(ctx.get("AccountName"), mapAccount.get("AccountName"));
				if (accFlag)
					accFlag = compareValues(ctx.get("AccountEmail"), mapAccount.get("AccountEmail"));
			}
			if (!mapAddress.isEmpty()) {
				if (accFlag)
					accFlag = compareValues(ctx.get("WorkPhone"), mapAddress.get("WorkPhone"));
			}
			if (!accFlag)
				IsAccountDetailChanged = "Y";

			// To check Policy Coverage or Modifier changes
			if (!mapPolicyCoverage.isEmpty()) {
				if (covFlag)
					covFlag = compareValues(ctx.get("LimitSequence"), mapPolicyCoverage.get("LimitSequence"));
				if (covFlag)
					covFlag = compareValues(ctx.get("DeductibleSequence"), mapPolicyCoverage.get("DeductibleSequence"));
				if (covFlag)
					covFlag = compareValues(ctx.get("IsClaimExpensesType"),
							mapPolicyCoverage.get("IsClaimExpensesType"));
				if (covFlag)
					covFlag = compareValues(ctx.get("IsClaimOptionType"), mapPolicyCoverage.get("IsClaimOptionType"));
				if (covFlag)
					covFlag = compareValues(ctx.get("IsDollarDefense"), mapPolicyCoverage.get("IsDollarDefense"));
				if (modifierFlag)
					modifierFlag = compareValues(ctx.get("SchduleRatingModifier1"),
							mapPolicyCoverage.get("TotalPolicyModifierPercentage"));
			}
			if (!covFlag)
				IsPolicyCoverageChanged = "Y";
			if (!modifierFlag)
				IsIndicationBatchUpdated = "Y";

			// To check Policy Level changes
			if (!mapPolicy.isEmpty()) {
				if (polFlag) {
					long diff = AccountantDateUtils.calculateDiffInDays(
							AccountantDateUtils.convertStringToDate(ctx.get("PolicyEffectiveDate").toString(),
									"MM/dd/yyyy"),
							AccountantDateUtils.convertStringToDate(mapPolicy.get("PolicyEffectiveDate").toString(),
									"MM/dd/yyyy"));
					if (diff != 0)
						polFlag = false;
				}
			}

			if (!mapAddress.isEmpty()) {
				if (polFlag)
					polFlag = compareValues(ctx.get("StateCode"),
							mapAddress.get("StateCode") != null ? mapAddress.get("StateCode") : "");
				if (polFlag)
					polFlag = compareValues(ctx.get("CountyDesc"),
							mapAddress.get("CountyDesc") != null ? mapAddress.get("CountyDesc") : "");
				if (polFlag)
					polFlag = compareValues(ctx.get("City"),
							mapAddress.get("City") != null ? mapAddress.get("City") : "");
			}
			if (!mapGrossRevenue.isEmpty()) {
				if (polFlag) {
					Object objAmount = removeAmountFormat(ctx.get("Amount") != null ? ctx.get("Amount") : "0");
					Object mapObjAmount = removeAmountFormat(
							mapGrossRevenue.get("Amount") != null ? mapGrossRevenue.get("Amount") : "0");
					polFlag = compareValues(objAmount, mapObjAmount);
				}
			}
			if (!mapProfLiab.isEmpty()) {
				if (polFlag) {
					polFlag = compareValues(ctx.get("FirmYear"),
							mapProfLiab.get("FirmYear") != null ? mapProfLiab.get("FirmYear") : "0");
				}
			}

//			 if(polFlag){	 
//				 List objList = SqlResources.getSqlMapProcessor(ctx).select("ClaimAgeLW.findAllByPartialKey", ctx);
//				 if (objList != null && objList.size() > 0) {
//					for (int i = 0; i < objList.size(); i++) {
//						Map objMap = (Map) objList.get(i);
//						if(!polFlag)
//							break;
//						polFlag = compareValues(ctx.get("ClaimAge_" + (i + 1)), objMap.get("Year"));
//					}
//				 }
//			 }

			if (polFlag) {
				List aoplist = SqlResources.getSqlMapProcessor(ctx)
						.select("SqlStmts.UserStatementsaveAccountstmtspopulateAOPFields", ctx);
				if (aoplist != null) {
					Map map = new HashMap();
					for (int i = 0; i < aoplist.size(); i++) {
						map = (HashMap) aoplist.get(i);
						if (!polFlag)
							break;
						polFlag = compareValues(
								"".equals(ctx.get("AOP_Percentage_" + map.get("AOPKey")))
										|| ctx.get("AOP_Percentage_" + map.get("AOPKey")) == null ? "0"
												: ctx.get("AOP_Percentage_" + map.get("AOPKey")),
								map.get("PercentageValue"));
					}
				}
			}

			if (!polFlag) {
				IsPolicyDetailChanged = "Y";
				IsIndicationBatchUpdated = "Y";
			}
		}

		ctx.put("IsPolicyDetailChanged", IsPolicyDetailChanged);
		ctx.put("IsPolicyCoverageChanged", IsPolicyCoverageChanged);
		ctx.put("IsAccountDetailChanged", IsAccountDetailChanged);
		ctx.put("IsIndicationBatchUpdated", IsIndicationBatchUpdated);
		ctx.put("OldPolicyKey", ctx.get("PolicyKey"));

		if (ctx.get("SchduleRatingModifier1") == null || (ctx.get("SchduleRatingModifier1") != null
				&& "".equals(ctx.get("SchduleRatingModifier1").toString())))
			ctx.put("SchduleRatingModifier1", "0");
	}

	public void deleteIndication(IContext ctx) throws Exception {
		// To check existing indication for changes in Account section and delete all
		// and their siblings
		if ("Y".equals(ctx.get("IsAccountDetailChanged").toString())
				|| (ctx.get("IsPolicyCreatedNew") != null && "Y".equals(ctx.get("IsPolicyCreatedNew").toString()))) {
			List list = SqlResources.getSqlMapProcessor(ctx)
					.select("SqlStmts.UserStatementsaveAccountstmtsgetIndicationAccount", ctx);
			if (list != null && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					ctx.putAll((Map) list.get(i));
					deleteAccountPolicy(ctx);
					ctx.put("IsExistingPolicyDeleted", "Y");
				}
			}
		}
	}

	public boolean compareValues(Object obj1, Object obj2) {
		boolean flag = true;
		if (obj1 == null && obj2 == null)
			return true;
		if (obj1 == null && obj2 != null)
			return false;
		if (obj1 != null && obj2 == null)
			return false;

		String val1 = obj1.toString();
		String val2 = obj2.toString();
		if (!val1.equals(val2))
			return false;

		return flag;
	}

	public void updatePolicyAccountsData(IContext ctx) throws Exception {
		IContext ctxNew = new Context();
		ctxNew.setProject(ctx.getProject());
		ctxNew.put("AccountName", ctx.get("AccountName"));
//		 ctxNew.put("AccountEmail", ctx.get("AccountEmail"));
//		 ctxNew.put("WorkPhone", ctx.get("WorkPhone"));
		ctxNew.put("PolicyKey", ctx.get("PolicyKey"));
		ctxNew.put("MainPolicyKey", ctx.get("PolicyKey"));
		updatePolicyAccountData(ctxNew);
	}

	public void updatePolicyAccountData(IContext ctx) throws Exception {

		Object objPolicyTransition = SqlResources.getSqlMapProcessor(ctx)
				.findByKey("PolicyTransition.findAllByPartialKey", ctx);
		Map mapPolicyTransition = null;
		if (objPolicyTransition != null) {
			mapPolicyTransition = (Map) objPolicyTransition;

			Object objAccount = SqlResources.getSqlMapProcessor(ctx)
					.findByKey("SqlStmts.UserStatementsaveAccountstmtsgetIndicationAccountKeys", ctx);
			Map mapAccount = null;
			if (objAccount != null) {
				mapAccount = (Map) objAccount;
				String addKey = null;
				String accKey = null;
				if (mapAccount.get("AccountKey") != null)
					accKey = mapAccount.get("AccountKey").toString();
				if (mapAccount.get("AddressKey") != null)
					addKey = mapAccount.get("AddressKey").toString();

				ctx.put("AccountKey", accKey);
				ctx.put("AddressKey", addKey);
				SqlResources.getSqlMapProcessor(ctx).update("SqlStmts.UserStatementsaveAccountstmtsupdateAddress", ctx);
				SqlResources.getSqlMapProcessor(ctx).update("SqlStmts.UserStatementsaveAccountstmtsupdateAccount", ctx);

				String nextPolicyKey = null;
				String previousPolicyKey = null;

				if (mapPolicyTransition.get("NextPolicyKey") != null)
					nextPolicyKey = mapPolicyTransition.get("NextPolicyKey").toString();
				if (mapPolicyTransition.get("PreviousPolicyKey") != null)
					previousPolicyKey = mapPolicyTransition.get("PreviousPolicyKey").toString();
				if (nextPolicyKey != null && !nextPolicyKey.equals(ctx.get("MainPolicyKey").toString())) {
					ctx.put("PolicyKey", nextPolicyKey);
					updatePolicyAccountData(ctx);
				}
				if (previousPolicyKey != null && !previousPolicyKey.equals(ctx.get("MainPolicyKey").toString())) {
					ctx.put("PolicyKey", previousPolicyKey);
					updatePolicyAccountData(ctx);
				}
			}
		}
	}

	public void setImagesPathToTemplate(IContext ctx) throws Exception {
		String homePath = SystemProperties.getInstance().getString("appl.home.dir");
		String checkboxYPath = homePath + "image//check-btn6.png";
		String checkboxNPath = homePath + "image//check-btn4.png";
		String cfLogoPath = homePath + "image//crum_logo.png";
		String protexureaccountantsLogoPath = homePath + "image//logo_protexureaccountants1.png";
		String headerBottomPath = homePath + "image//header_bottom.png";
		String spacerPath = homePath + "image//spacer.png";
		String spacerPath1 = homePath + "img//space.gif";
		String freeTradeZoneStampPath = homePath + "image//free_trade_zone_stamp.png";
		String crumLogoPath = homePath + "image//crum_logo.png";
		String boxCrossPath = homePath + "image//boxcross.png";
		String noCrossPath = homePath + "image//nocross.jpg";
		String sign1Path = homePath + "image//sign1.png";
		String sign2Path = homePath + "image//sign2.png";
		String logoProtexureaccountants1Path = homePath + "image//logo_protexureaccountants1.png";
		String kylePath = homePath + "image//kyle.png";

		ctx.put("checkboxYPath", checkboxYPath);
		ctx.put("checkboxNPath", checkboxNPath);
		ctx.put("cfLogoPath", cfLogoPath);
		ctx.put("protexureaccountantsLogoPath", protexureaccountantsLogoPath);
		ctx.put("headerBottomPath", headerBottomPath);
		ctx.put("spacerPath", spacerPath);
		ctx.put("freeTradeZoneStampPath", freeTradeZoneStampPath);
		ctx.put("crumLogoPath", crumLogoPath);
		ctx.put("boxCrossPath", boxCrossPath);
		ctx.put("noCrossPath", noCrossPath);
		ctx.put("sign1Path", sign1Path);
		ctx.put("sign2Path", sign2Path);
		ctx.put("logoProtexureaccountants1Path", logoProtexureaccountants1Path);
		ctx.put("kylePath", kylePath);
		ctx.put("spacerPath1", spacerPath1);
	}

	public void processIndicationQuotes(IContext ctx) throws Exception {
		Object obj = SqlResources.getSqlMapProcessor(ctx)
				.select("SqlStmts.UserStatementsaveAccountstmtsgetIndicationQuotesList", ctx);
		if (obj == null)
			return;

		ctx.put("MainVersionSequence", ctx.get("VersionSequence"));
		ctx.put("MainTransactionSequence", ctx.get("TransactionSequence"));
		ctx.put("MainCoverageSequence", ctx.get("CoverageSequence"));
		ctx.put("MainRatingTraceSequence", ctx.get("RatingTraceSequence"));
		ctx.put("MainSchduleRatingModifier1", ctx.get("SchduleRatingModifier1"));

		List quoteList = null;
		Map mapQuote = null;
//		 boolean flag = true;
//		 DocumentManagment docMngt = null;
		if (obj != null) {
			quoteList = (List) obj;
			for (int i = 0; i < quoteList.size(); i++) {
				mapQuote = (Map) quoteList.get(i);
//				 if(flag){
				ctx.putAll(mapQuote);
				QuoteLetter.populateQuoteIndication(ctx);
				Rating.processQuotesListQuickQuote(ctx);
//					 flag = processIndicationQuote(ctx);
//					 if(flag){
//						 SqlResources.getSqlMapProcessor(ctx).update("PolicyTransaction.update", ctx);
//						 SqlResources.getSqlMapProcessor(ctx).update("PolicyCoverage.update", ctx);
//						 SqlResources.getSqlMapProcessor(ctx).update("RatingTrace.update", ctx);
//						 SqlResources.getSqlMapProcessor(ctx).update("Quote.update",ctx);
//					 }
//				 }

//				 if(!flag)
//					 break;
			}

//			 if(flag){
//				 SqlResources.getSqlMapProcessor(ctx).delete("SqlStmts.UserStatementsaveAccountstmtsdeleteDocumentArchiveLWBatch", ctx);
//				 docMngt = new DocumentManagment();
//				 for(int i=0; i< quoteList.size(); i++){
//					 mapQuote = (Map) quoteList.get(i);
//					 ctx.putAll(mapQuote);
//					 //docMngt.processIndicationDocument(ctx);
//				 }
//			 }
		}

		// To populate initial quote
		ctx.put("VersionSequence", ctx.get("MainVersionSequence"));
		ctx.put("TransactionSequence", ctx.get("MainTransactionSequence"));
		ctx.put("CoverageSequence", ctx.get("MainCoverageSequence"));
		ctx.put("RatingTraceSequence", ctx.get("MainRatingTraceSequence"));

		ctx.remove("MainVersionSequence");
		ctx.remove("MainTransactionSequence");
		ctx.remove("MainCoverageSequence");
		ctx.remove("MainRatingTraceSequence");
		ctx.remove("MainSchduleRatingModifier1");
	}

	public void processSelectedIndication(IContext ctx) throws Exception {
		if (ctx.get("SchduleRatingModifier1") == null || (ctx.get("SchduleRatingModifier1") != null
				&& "".equals(ctx.get("SchduleRatingModifier1").toString())))
			ctx.put("SchduleRatingModifier1", "0");

		List listIndication = null;
		if (ctx.get("indication_list_01") != null)
			listIndication = (List) ctx.get("indication_list_01");

		if (listIndication == null)
			return;

		int index = 0;
		if (ctx.get("SelectedIndicationIndex") != null
				&& !"".equals(ctx.get("SelectedIndicationIndex").toString().trim()))
			index = Integer.parseInt(ctx.get("SelectedIndicationIndex").toString());
		else {
			for (int i = 0; i < listIndication.size(); i++) {
				Map objMap = (Map) listIndication.get(i);
				if (objMap.get("IsQuoteSelected") != null && "Y".equals(objMap.get("IsQuoteSelected").toString())) {
					index = i;
					break;
				}
			}

		}

		Map indicationMap = null;
		try {
			indicationMap = (Map) listIndication.get(index);
			if (indicationMap != null) {
				ctx.put("TransactionSequence", indicationMap.get("TransactionSequence"));
				ctx.put("CoverageSequence", indicationMap.get("CoverageSequence"));
				ctx.put("RatingTraceSequence", indicationMap.get("RatingTraceSequence"));
			}
		} catch (ArrayIndexOutOfBoundsException ae) {
			logger.error("Error in AccountUtils.processSelectedIndication", ae);
			indicationMap = (Map) listIndication.get(0);
			if (indicationMap != null) {
				ctx.put("TransactionSequence", indicationMap.get("TransactionSequence"));
				ctx.put("CoverageSequence", indicationMap.get("CoverageSequence"));
				ctx.put("RatingTraceSequence", indicationMap.get("RatingTraceSequence"));
			}
		}

	}

	public void deleteAccountPolicy(IContext ctx) throws Exception {

		/*
		 * Object objPolicyTransition =
		 * SqlResources.getSqlMapProcessor(ctx).findByKey("Policy.findByKey", ctx); Map
		 * mapPolicyTransition = null; if(objPolicyTransition != null){
		 * mapPolicyTransition = (Map) objPolicyTransition;
		 * 
		 * Object objAccount = SqlResources.getSqlMapProcessor(ctx).findByKey(
		 * "SqlStmts.UserStatementsaveAccountstmtsgetIndicationAccountKeys", ctx); Map
		 * mapAccount = null; if(objAccount != null && !mapPolicyTransition.isEmpty()){
		 * mapAccount = (Map) objAccount; String addKey = null; String accKey = null;
		 * if(mapAccount.get("AccountKey") != null) accKey =
		 * mapAccount.get("AccountKey").toString(); if(mapAccount.get("AddressKey") !=
		 * null) addKey = mapAccount.get("AddressKey").toString();
		 * 
		 * ctx.put("AccountKey",accKey); ctx.put("AddressKey",addKey);
		 * 
		 * SqlResources.getSqlMapProcessor(ctx).delete(
		 * "SqlStmts.UserStatementsaveAccountstmtsdeleteRatingTrace", ctx);
		 * SqlResources.getSqlMapProcessor(ctx).delete(
		 * "SqlStmts.UserStatementsaveAccountstmtsdeleteQuote", ctx);
		 * SqlResources.getSqlMapProcessor(ctx).delete(
		 * "SqlStmts.UserStatementsaveAccountstmtsdeleteSurchargeTax", ctx);
		 * 
		 * SqlResources.getSqlMapProcessor(ctx).delete(
		 * "SqlStmts.UserStatementsaveAccountstmtsdeleteCoverageModifiers", ctx);
		 * SqlResources.getSqlMapProcessor(ctx).delete(
		 * "SqlStmts.UserStatementsaveAccountstmtsdeletePolicyCoverage", ctx);
		 * SqlResources.getSqlMapProcessor(ctx).delete(
		 * "SqlStmts.UserStatementsaveAccountstmtsdeletePolicyTransaction", ctx);
		 * 
		 * SqlResources.getSqlMapProcessor(ctx).delete(
		 * "SqlStmts.UserStatementsaveAccountstmtsdeleteCommentDetail", ctx);
		 * SqlResources.getSqlMapProcessor(ctx).delete(
		 * "SqlStmts.UserStatementsaveAccountstmtsdeletePricingComment", ctx);
		 * 
		 * SqlResources.getSqlMapProcessor(ctx).delete("AccountAddress.delete", ctx);
		 * SqlResources.getSqlMapProcessor(ctx).delete("Account.delete", ctx);
		 * SqlResources.getSqlMapProcessor(ctx).delete("Address.delete", ctx);
		 * 
		 * SqlResources.getSqlMapProcessor(ctx).delete(
		 * "SqlStmts.UserStatementsaveAccountstmtsdeleteAreaOfPractice", ctx);
		 * SqlResources.getSqlMapProcessor(ctx).delete(
		 * "SqlStmts.UserStatementsaveAccountstmtsdeleteDocumentArchive", ctx);
		 * 
		 * SqlResources.getSqlMapProcessor(ctx).delete(
		 * "SqlStmts.UserStatementsaveAccountstmtsdeleteGrossRevenueForFirm", ctx);
		 * SqlResources.getSqlMapProcessor(ctx).delete(
		 * "SqlStmts.UserStatementsaveAccountstmtsdeleteProfessionalLiabilityInsDetail",
		 * ctx);
		 * 
		 * SqlResources.getSqlMapProcessor(ctx).delete("Firm.deleteAllByPartialKey",
		 * ctx);
		 * 
		 * deletePoliciesData(ctx);
		 * 
		 * SqlResources.getSqlMapProcessor(ctx).delete(
		 * "PolicyTransition.deleteAllByPartialKey", ctx);
		 * SqlResources.getSqlMapProcessor(ctx).delete("Policy.delete", ctx);
		 * 
		 * } }
		 */
		try {
			SqlResources.getSqlMapProcessor(ctx)
					.delete("SqlStmts.UserStatementManualBoQueriesdeleteIndicationLogically", ctx);

		} catch (Exception e) {
			logger.error("Error occured while deleting indication", e);

		}
	}

	public void deletePoliciesData(IContext ctx) throws Exception {

		Object objPolicyTransition = SqlResources.getSqlMapProcessor(ctx)
				.findByKey("PolicyTransition.findAllByPartialKey", ctx);
		IContext ctxNew = new Context();
		ctxNew.setProject(ctx.getProject());
		ctxNew.put("MainPolicyKey", ctx.get("PolicyKey"));

		Map mapPolicyTransition = null;
		if (objPolicyTransition != null) {
			mapPolicyTransition = (Map) objPolicyTransition;

			String nextPolicyKey = null;
			String previousPolicyKey = null;

			if (mapPolicyTransition.get("NextPolicyKey") != null)
				nextPolicyKey = mapPolicyTransition.get("NextPolicyKey").toString();
			if (mapPolicyTransition.get("PreviousPolicyKey") != null)
				previousPolicyKey = mapPolicyTransition.get("PreviousPolicyKey").toString();

			if (nextPolicyKey != null && !nextPolicyKey.equals(ctxNew.get("MainPolicyKey").toString())) {
				ctxNew.put("PolicyKey", nextPolicyKey);
				deletePolicyData(ctxNew);
			}
			if (previousPolicyKey != null && !previousPolicyKey.equals(ctxNew.get("MainPolicyKey").toString())) {
				ctxNew.put("PolicyKey", previousPolicyKey);
				deletePolicyData(ctxNew);
			}

			SqlResources.getSqlMapProcessor(ctx).delete("PolicyTransition.deleteAllByPartialKey", ctx);
		}
	}

	public void deletePolicyData(IContext ctx) throws Exception {

		Object objPolicyTransition = SqlResources.getSqlMapProcessor(ctx)
				.findByKey("PolicyTransition.findAllByPartialKey", ctx);
		Map mapPolicyTransition = null;
		if (objPolicyTransition != null) {
			mapPolicyTransition = (Map) objPolicyTransition;

			Object objAccount = SqlResources.getSqlMapProcessor(ctx)
					.findByKey("SqlStmts.UserStatementsaveAccountstmtsgetIndicationAccountKeys", ctx);
			Map mapAccount = null;
			if (objAccount != null && !mapPolicyTransition.isEmpty()) {
				mapAccount = (Map) objAccount;
				String addKey = null;
				String accKey = null;
				if (mapAccount.get("AccountKey") != null)
					accKey = mapAccount.get("AccountKey").toString();
				if (mapAccount.get("AddressKey") != null)
					addKey = mapAccount.get("AddressKey").toString();

				ctx.put("AccountKey", accKey);
				ctx.put("AddressKey", addKey);

				SqlResources.getSqlMapProcessor(ctx).delete("SqlStmts.UserStatementsaveAccountstmtsdeleteRatingTrace",
						ctx);
				SqlResources.getSqlMapProcessor(ctx).delete("SqlStmts.UserStatementsaveAccountstmtsdeleteQuote", ctx);
				SqlResources.getSqlMapProcessor(ctx).delete("SqlStmts.UserStatementsaveAccountstmtsdeleteSurchargeTax",
						ctx);

				SqlResources.getSqlMapProcessor(ctx)
						.delete("SqlStmts.UserStatementsaveAccountstmtsdeleteCoverageModifiers", ctx);
				SqlResources.getSqlMapProcessor(ctx)
						.delete("SqlStmts.UserStatementsaveAccountstmtsdeletePolicyCoverage", ctx);
				SqlResources.getSqlMapProcessor(ctx)
						.delete("SqlStmts.UserStatementsaveAccountstmtsdeletePolicyTransaction", ctx);

				SqlResources.getSqlMapProcessor(ctx).delete("SqlStmts.UserStatementsaveAccountstmtsdeleteCommentDetail",
						ctx);
				SqlResources.getSqlMapProcessor(ctx)
						.delete("SqlStmts.UserStatementsaveAccountstmtsdeletePricingComment", ctx);

				SqlResources.getSqlMapProcessor(ctx).delete("AccountAddress.delete", ctx);
				SqlResources.getSqlMapProcessor(ctx).delete("Account.delete", ctx);
				SqlResources.getSqlMapProcessor(ctx).delete("Address.delete", ctx);

				SqlResources.getSqlMapProcessor(ctx)
						.delete("SqlStmts.UserStatementsaveAccountstmtsdeleteAreaOfPractice", ctx);
				SqlResources.getSqlMapProcessor(ctx)
						.delete("SqlStmts.UserStatementsaveAccountstmtsdeleteDocumentArchive", ctx);

				SqlResources.getSqlMapProcessor(ctx)
						.delete("SqlStmts.UserStatementsaveAccountstmtsdeleteGrossRevenueForFirm", ctx);
				SqlResources.getSqlMapProcessor(ctx)
						.delete("SqlStmts.UserStatementsaveAccountstmtsdeleteProfessionalLiabilityInsDetail", ctx);

				SqlResources.getSqlMapProcessor(ctx).delete("Firm.deleteAllByPartialKey", ctx);
				SqlResources.getSqlMapProcessor(ctx).delete("PolicyTransition.deleteAllByPartialKey", ctx);
				SqlResources.getSqlMapProcessor(ctx).delete("Policy.delete", ctx);

				String nextPolicyKey = null;
				String previousPolicyKey = null;

				if (mapPolicyTransition.get("NextPolicyKey") != null)
					nextPolicyKey = mapPolicyTransition.get("NextPolicyKey").toString();
				if (mapPolicyTransition.get("PreviousPolicyKey") != null)
					previousPolicyKey = mapPolicyTransition.get("PreviousPolicyKey").toString();

				if (nextPolicyKey != null && !nextPolicyKey.equals(ctx.get("MainPolicyKey").toString())) {
					ctx.put("PolicyKey", nextPolicyKey);
					deletePolicyData(ctx);
				}
				if (previousPolicyKey != null && !previousPolicyKey.equals(ctx.get("MainPolicyKey").toString())) {
					ctx.put("PolicyKey", previousPolicyKey);
					deletePolicyData(ctx);
				}
			}
		}
	}

	// Endorsement Start
	public int getStepRateYearBetweenTwoDate(Date startDate, Date endDate) {
		int startYear = startDate.getYear();
		int endYear = endDate.getYear();
		int startMonth = startDate.getMonth();
		int endMonth = endDate.getMonth();
		int startDay = startDate.getDate();
		int endDay = endDate.getDate();

		int stepYear = endYear - startYear;

		int stepmonth = endMonth - startMonth;
		if (stepmonth > 0)
			stepYear = stepYear + 1;
		else if (stepmonth == 0) {
			int stepDay = endDay - startDay;
			if (stepDay > 0) {
				stepYear = stepYear + 1;
			}

		}
		return stepYear;
	}

	public int calculateAttorneyStepRateYears(Map attorneyMap, Map policyMap) throws Exception {

		int attorneyStepRateYears = 0;
		if (policyMap != null && attorneyMap != null) {

			Date policyEffectiveDate = policyMap.get("PolicyEffectiveDate") != null
					? AccountantDateUtils.convertStringToDate(policyMap.get("PolicyEffectiveDate").toString(),
							"yyyy-MM-dd")
					: null;
			Date attorneyJoinedDate = attorneyMap.get("AttorneyPriorActDate") != null
					? AccountantDateUtils.convertStringToDate(attorneyMap.get("AttorneyPriorActDate").toString(),
							"yyyy-MM-dd")
					: null;

			if (policyEffectiveDate != null && attorneyJoinedDate != null) {
				attorneyStepRateYears = getStepRateYearBetweenTwoDate(attorneyJoinedDate, policyEffectiveDate);
			}

			// policyEffectiveDate, attorneyJoinedDate);
			//
			// double diff = Double.parseDouble(String.valueOf(daysDiff));
			//
			// int numberOfLeapYears =
			// getNumberOfNonLeapYears(attorneyJoinedDate, policyEffectiveDate);
			// diff = diff + numberOfLeapYears ;
			//
			// double year = diff / 366;
			// attorneyStepRateYears = (int) Math.ceil(year);

			if (attorneyStepRateYears > 5) {

				attorneyStepRateYears = 5;
			}

		}
		return attorneyStepRateYears;
	}

	public int getClaimAge(int attorneyStepRateYears, int firmStepRateYears) {

		if (attorneyStepRateYears < 0)
			attorneyStepRateYears = 0;

		if (firmStepRateYears < 0)
			attorneyStepRateYears = 0;

		if (attorneyStepRateYears < firmStepRateYears)
			return attorneyStepRateYears;
		else
			return firmStepRateYears;

	}

	public static Object amountFormatLawyers(Object arg1) {
		if (arg1 == null || arg1.equals(HtmlConstants.EMPTY))
			return 0;

		String amount = arg1.toString();
		DecimalFormat df = new DecimalFormat("$###,###.###");
		return df.format(Double.parseDouble(amount));
	}

	public static void downloadInsurenceCertificate(Context ctx) {
		String foFile = null;
		HttpServletRequest request = (HttpServletRequest) ctx.get("DocumentRequest");
		HttpServletResponse response = (HttpServletResponse) ctx.get("DocumentResponse");
		try {
			String htmlDir = SystemProperties.getInstance().getString("html.basedir");
			String filePath = htmlDir + "data\\InsurenceCertificate_" + ctx.get("PolicyKey").toString() + ".pdf";
			getPolicyData(ctx);
			if ("3".equals(ctx.get("CompanyKey").toString()))
				foFile = SystemProperties.getInstance().getString("xsl.filepath.COI")
						+ "ISMIE//Endorsement/LiabilityInsCertificate.xsl";
			else
				foFile = SystemProperties.getInstance().getString("xsl.filepath.endorsement")
						+ "foxslnew/endorsement/LiabilityInsCertificate.xsl";

			String environment = SystemProperties.getInstance().getString("appl.AccountantIns.environment.production");
			ctx.put("environmentproduction", environment);
			new XML2PDF().convertPOToPDF(foFile, new StringBuffer(new DownloadForm().generateDataXml(ctx)), filePath);

			exportFileDownLoad(filePath, response);
			if (filePath != null && !filePath.isEmpty()) {
				deleteDirectory(new File(filePath));
			}
		} catch (Exception e) {
			logger.error("Error in AccountUtils.downloadInsurenceCertificate", e);
		}
	}

	public static void validatePolicyExtensionDate(Context ctx) {
		try {
			if (ctx.get("policyExtendExpirationDate") != null
					&& !ctx.get("policyExtendExpirationDate").equals(HtmlConstants.EMPTY)) {
				/*
				 * Calendar date = Calendar.getInstance(); Date date1 = new
				 * SimpleDateFormat("MM/dd/yyyy").parse(ctx.get("PolicyExpirationDate").toString
				 * ()); date.setTime(date1); date.add(Calendar.MONTH,6); SimpleDateFormat f =
				 * new SimpleDateFormat("MM/dd/yyyy");
				 */
				// ctx.put("policyExtendExpirationDate",f.format(date.getTime()));
				long daysDifference1 = DateUtils.calculateDiffInDays(
						new Date(ctx.get("policyExtendExpirationDate").toString()),
						new Date(ctx.get("PolicyExpirationDate").toString()));
				long daysDifference = DateUtils.calculateDiffInDays(
						new Date(ctx.get("policyExtendExpirationDate").toString()),
						new Date(ctx.get("PolicyEffectiveDate").toString()));
				{ if (logger.isDebugEnabled()) logger.debug("days difference " + daysDifference); }
				if (daysDifference1 < 0) {
					populateError(ctx, "policyExtendExpirationDateError",
							"Requested policy expiration date can't be less than of current policy expiration date");
					return;
				}
				if (daysDifference > 548) {

					populateError(ctx, "policyExtendExpirationDateError",
							"New policy expiration date can't exceed six months to current expiration date");
					return;
				}

			} else
				populateError(ctx, "policyExtendExpirationDateError", "Please enter policy expiration date");
		} catch (Exception e) {
			logger.error("execption occured in validatePolicyExtensionDate method :" + e, e);
		}
	}

	public static Object getAccountPropertyFromPropertiesFiles(String propertyName) {
		Object obj = null;
		try {
			obj = SystemProperties.getInstance().getString(propertyName);

		} catch (Exception e) {
			logger.error("error in fetching data from property file", e);
		}
		return obj;
	}

	public static boolean validateFirmNewApp(Context ctx) throws Exception {

		boolean isAgent = false;
		Object objAgentRule = RuleUtils.executeRule(ctx, "AccountantRule.isAgent");
		if (objAgentRule != null && objAgentRule instanceof Boolean) {
			isAgent = (Boolean) objAgentRule;
		}
		/*
		 * boolean flag = false; Object obj = RuleUtils.executeRule(ctx,
		 * "AccountantRule.validateEmailOnFirmSave"); if (obj != null && obj instanceof
		 * Boolean) { flag = (Boolean) obj;
		 * 
		 * if (flag) { checkFirmWithEmailExist(ctx); }
		 * 
		 * }
		 */

		trimFirmApplicantName(ctx);
		if (!validateCounty(ctx)) {
			populateError(ctx, "CountyDesc", "County is a required field");
		}
		validateCityField(ctx);

		return true;
	}

	private static void validateCityField(IContext ctx) throws Exception {

		Object objCity = SqlResources.getSqlMapProcessor(ctx).select("City.getAllCityForCountyList", ctx);
		if (objCity != null && objCity instanceof List) {

			List cityList = (List) objCity;

			if (cityList != null && cityList.size() > 0) {

				String city = ctx.get("City") != null ? ctx.get("City").toString() : "";
				if (city.equals(""))
					populateError(ctx, "City", "City is a required field");

			}

		}

	}

	public void decryptPolicyNumber(IContext ctx) {
		try {
			String pNumberpolicy = ctx.get("PolicyKey") != null && !HtmlConstants.EMPTY.equals(ctx.get("PolicyKey"))
					&& !"SPACE".equals(ctx.get("PolicyKey")) ? ctx.get("PolicyKey").toString() : "";
			if (!"".equals(pNumberpolicy)) {
				HttpServletRequest req = (HttpServletRequest) ctx.get("DocumentRequest");
				HttpSession sess = req.getSession();

				String decryptPnumberpolicy = new AccountantValidationUtils().decrypt(pNumberpolicy);
				ctx.put("PolicyKey", decryptPnumberpolicy);
			}
			String pNumber = ctx.get("SubProducer") != null && !ctx.get("SubProducer").equals(HtmlConstants.EMPTY)
					? ctx.get("SubProducer").toString()
					: "";
			if (!"".equals(pNumber)) {
				HttpServletRequest req = (HttpServletRequest) ctx.get("DocumentRequest");
				HttpSession sess = req.getSession();

				String decryptPnumber = new AccountantValidationUtils().decrypt(pNumber);
				ctx.put("SubProducer", decryptPnumber);
			}
		} catch (Exception e) {
			logger.error("Error in AccountUtils.decryptPolicyNumber", e);
		}
	}

	public static void supplementTabs(Context ctx) {
		try {
			try {
				new SetParametersForStoredProcedures().setParametersInContext(ctx, "PolicyKey, AccountKey");
				List validatePageData = (List) SqlResources.getSqlMapProcessor(ctx)
						.select("Firm.ValidateIndivisualPageDataACC_p", ctx);

				if (validatePageData != null && validatePageData.size() > 0)
					ctx.putAll((Map) validatePageData.get(0));
			} catch (Exception etab) {
				logger.error("Error in AccountUtils.supplementTabs", etab);
			}

			HashMap cmap = null;
			// new SetParametersForStoredProcedures().setParametersInContext(ctx,
			// "PolicyKey,litigationDateNEW");
			List dataList = SqlResources.getSqlMapProcessor(ctx).select("Firm.TabsWorkFlowAcc_p", ctx);

			for (int i = 0; i < dataList.size(); i++) {
				ctx.put("tabs_tabsResult_list_01_" + i, dataList.get(i));
			}

			// ctx.put("tabs_tabsResult_list_01", dataList);
			// logger.debug("policy----" + ctx.get("tabs_tabsResult_list_01_7"));
			Object obj = ctx.get("tabs_tabsResult_list_01_7");
			if (obj != null) {
				List list = (List) obj;
				for (int i = 0; i < list.size(); i++) {
					Map map = (Map) list.get(i);
					ctx.put("flag_for_supplment", map.get("flag_for_supplment"));
					{ if (logger.isDebugEnabled()) logger.debug("flag for policy----" + ctx.get("flag_for_supplment")); }
				}
			}

			Object objSupplement = ctx.get("tabs_tabsResult_list_01_1");
			List listSupplement = null;
			Map mapSupplement = null;
			String initialSupplementPage = "";
			if (objSupplement != null) {
				listSupplement = (List) objSupplement;
				if (listSupplement != null && listSupplement.size() > 0) {
					mapSupplement = (Map) listSupplement.get(0);
					initialSupplementPage = mapSupplement.get("object_name").toString();
					ctx.put("initialSupplementPage", initialSupplementPage);
				}
			}

		} catch (Exception e) {
			logger.error("Error in AccountUtils.supplementTabs", e);
		}
	}

	public static void assignDefenseExpenses(IContext ctx) throws Exception {

		if (ctx.get("IsDollarDefense") != null && !"".equals(ctx.get("IsDollarDefense").toString())) {
			ctx.put("IsDollarDefense", "Y");
		} else {
			ctx.put("IsDollarDefense", "N");
		}
	}

	public static void assignFirmNewDetails(IContext ctx) {
		try {
			if (ctx.get("Zip") != null && !"".equals(ctx.get("Zip").toString())) {
				ctx.put("Zip", ctx.get("Zip").toString().substring(0, 5));
			}
		} catch (Exception e) {
			logger.error("Error in AccountUtils.assignFirmNewDetails", e);
		}

	}

	public static void validateAopNewApp(Context ctx) throws Exception {
		validateAOPPercentageNewApp(ctx);
		validateAOPCommentNewApp(ctx);
	}

	public static void removeSymbolFormat(IContext ctx) {
		try {
			if (ctx.get("ProInsPremium") != null && !"".equals(ctx.get("ProInsPremium").toString())) {
				Object objAmount = removeAmountFormat(ctx.get("ProInsPremium"));
				ctx.put("ProInsPremium", objAmount);
			}
		} catch (Exception e) {
			logger.error("Error in AccountUtils.removeSymbolFormat", e);
		}
	}

	public static Object convertListDataToXML(Context ctx, String inputListName, String outputXmlName)
			throws Exception {

		if (inputListName == null || HtmlConstants.EMPTY.equals(inputListName) || outputXmlName == null
				|| HtmlConstants.EMPTY.equals(outputXmlName))
			return null;

		org.jdom.Element root = null;

		List dataList = ctx.get(inputListName) != null ? (List) ctx.get(inputListName) : null;
		if (dataList != null && dataList.size() > 0) {

			root = new org.jdom.Element("Root");
			for (int i = 0; i < dataList.size(); i++) {
				Map map = (Map) dataList.get(i);
				org.jdom.Element child = new org.jdom.Element("Root1");
				if (map != null) {
					Set set = map.keySet();
					Iterator<String> itr = set.iterator();
					while (itr.hasNext()) {
						String key = itr.next();
						org.jdom.Element rowElement = new org.jdom.Element(key);

						if (ctx.get(key + "_" + i) != null) {
							if (!HtmlConstants.EMPTY.equals(ctx.get(key + "_" + i).toString())) {
								String value = ctx.get(key + "_" + i).toString();
								rowElement.addContent(value);
								child.addContent(rowElement);
							} else {
								rowElement.addContent("");
								child.addContent(rowElement);
							}

						} else {
							// for checkbox you have to mention the key else it will update the map value
							// there
							if (key.equals("is_included")) {
								rowElement.addContent("N");
								child.addContent(rowElement);
								continue;
							}

							if (map.get(key) != null) {
								rowElement.addContent(map.get(key).toString());
								child.addContent(rowElement);
							} else if (map.get(key) == null) {
								rowElement.addContent("");
								child.addContent(rowElement);
							}
						}

					}
					root.addContent(child);
				}
			}
		}
		XMLOutputter output = new XMLOutputter();
		String xml = output.outputString(root);
		ctx.put(outputXmlName, xml);
		return null;
	}

	public static void viewFeeSuitData(Context ctx) {
		HttpServletRequest req = (HttpServletRequest) ctx.get("DocumentRequest");
		HttpSession sess = req.getSession();
		List finalList = null;
		if (ctx.get("FeeSuitData_list_01") == null)
			ctx.put("FeeSuitData_list_01", sess.getAttribute("FeeSuitData_list_01"));

		if (ctx.get("FeeSuitData_list_01") != null && ctx.get("FeeSuitData_list_01") instanceof List) {
			finalList = (List) ctx.get("FeeSuitData_list_01");
			ctx.put("addFeeSuits", finalList.size());
			int feesuitsCount = ctx.get("addFeeSuits") != null ? Integer.parseInt(ctx.get("addFeeSuits").toString())
					: finalList.size();
			if (feesuitsCount > finalList.size()) {

				Map map = new HashMap();
				for (int i = finalList.size(); i < finalList.size() + (feesuitsCount - finalList.size()); i++) {
					map.put("FirminitLawsuitsKey", null);
					map.put("AmountOfFeesSued", null);
					map.put("SuitFilesDateFees", null);
//					map.put("NamedDefendantsInSuit", null);
//					map.put("DueDateFees", null);
					map.put("IsAllegOfLegalMalPrac", null);
					map.put("FeeSuitDispositionKey", null);
					finalList.add(map);

				}
				ctx.put("FeeSuitData_list_01", finalList);
			}
			sess.setAttribute("FeeSuitData_list_01", ctx.get("FeeSuitData_list_01"));
		}
	}

	public static void saveFeeSuitData(Context ctx) {
		try {
			HttpServletRequest req = (HttpServletRequest) ctx.get("DocumentRequest");
			HttpSession sess = req.getSession();

			List finalList = null;
			List inputList = new ArrayList();
			Context newCtx = new Context();
			newCtx.setProject(ctx.getProject());
			newCtx.putAll(ctx);

			if (ctx.get("FeeSuitData_list_01") == null)
				ctx.put("FeeSuitData_list_01", sess.getAttribute("FeeSuitData_list_01"));

			convertStringToList(ctx, "FeeSuitData_list_01");

			if (ctx.get("FeeSuitData_list_01") != null && ctx.get("FeeSuitData_list_01") instanceof List) {
				finalList = (List) ctx.get("FeeSuitData_list_01");
				for (int i = 0; i < finalList.size(); i++) {
					newCtx.remove("AmountOfFeesSued_" + i);
				}
				Map map = null;
				for (int i = 0; i < finalList.size(); i++) {
					map = new HashMap();
					if (!ctx.get("AmountOfFeesSued_" + i).equals(HtmlConstants.EMPTY)
							&& ctx.get("AmountOfFeesSued_" + i) != null
							&& !ctx.get("SuitFilesDateFees_" + i).equals(HtmlConstants.EMPTY)
							&& ctx.get("SuitFilesDateFees_" + i) != null &&
//						!ctx.get("NamedDefendantsInSuit_" + i).equals(HtmlConstants.EMPTY) && ctx.get("NamedDefendantsInSuit_" + i) != null &&
//						!ctx.get("DueDateFees_" + i).equals(HtmlConstants.EMPTY) && ctx.get("DueDateFees_" + i) != null &&
							!ctx.get("IsAllegOfLegalMalPrac_" + i).equals(HtmlConstants.EMPTY)
							&& ctx.get("IsAllegOfLegalMalPrac_" + i) != null
							&& !ctx.get("FeeSuitDispositionKey_" + i).equals(HtmlConstants.EMPTY)
							&& ctx.get("FeeSuitDispositionKey_" + i) != null) {
						int amount = Integer.parseInt(removeAmountFormat(ctx.get("AmountOfFeesSued_" + i)).toString());
						map.put("AmountOfFeesSued", amount);
						map.put("SuitFilesDateFees", ctx.get("SuitFilesDateFees_" + i));
//						map.put("NamedDefendantsInSuit", ctx.get("NamedDefendantsInSuit_" + i));
//						map.put("DueDateFees", ctx.get("DueDateFees_" + i));
						map.put("IsAllegOfLegalMalPrac", ctx.get("IsAllegOfLegalMalPrac_" + i));
						map.put("FeeSuitDispositionKey", ctx.get("FeeSuitDispositionKey_" + i));
						map.put("PolicyKey", ctx.get("PolicyKey"));
						map.put("VersionSequence", ctx.get("VersionSequence"));
						map.put("LastUpdateUserID", ctx.get("LastUpdateUserID"));
						inputList.add(map);
					}
				}

				newCtx.put("inputList", inputList);
				convertListDataToXML(newCtx, "inputList", "outputList");
				ctx.put("outputList", newCtx.get("outputList"));
//				ctx.put("FeeSuitData_list_01",inputList);
			}
		} catch (Exception e) {
			logger.error("Error in AccountUtils.saveFeeSuitData", e);
		}
	}

	public static void addNewFeeSuit(Context ctx) {
		HttpServletRequest req = (HttpServletRequest) ctx.get("DocumentRequest");
		HttpSession sess = req.getSession();
		List finalList = null;
		List<Map<String, Object>> newList = new ArrayList<Map<String, Object>>();
		try {
			if (ctx.get("FeeSuitData_list_01") == null)
				ctx.put("FeeSuitData_list_01", sess.getAttribute("FeeSuitData_list_01"));

			convertStringToList(ctx, "FeeSuitData_list_01");

			if (ctx.get("FeeSuitData_list_01") != null && ctx.get("FeeSuitData_list_01") instanceof List) {
				finalList = (List) ctx.get("FeeSuitData_list_01");
				Map map1 = null, map2 = null;
				for (int i = 0; i < finalList.size(); i++) {
					map1 = new HashMap();
					map2 = (HashMap) finalList.get(i);
					map1.put("FirminitLawsuitsKey", map2.get("FirminitLawsuitsKey"));
//					map1.put("NamedDefendantsInSuit", ("".equals(ctx.get("NamedDefendantsInSuit_" + i))) ? null :ctx.get("NamedDefendantsInSuit_" + i));
//					map1.put("DueDateFees", ("".equals(ctx.get("DueDateFees_" + i))) ? null :ctx.get("DueDateFees_" + i));
					map1.put("IsAllegOfLegalMalPrac", ("".equals(ctx.get("IsAllegOfLegalMalPrac_" + i))) ? null
							: ctx.get("IsAllegOfLegalMalPrac_" + i));
					map1.put("SuitFilesDateFees",
							("".equals(ctx.get("SuitFilesDateFees_" + i))) ? null : ctx.get("SuitFilesDateFees_" + i));
					map1.put("FeeSuitDispositionKey", ("".equals(ctx.get("FeeSuitDispositionKey_" + i))) ? null
							: ctx.get("FeeSuitDispositionKey_" + i));
					map1.put("AmountOfFeesSued",
							("".equals(ctx.get("AmountOfFeesSued_" + i))) ? null : ctx.get("AmountOfFeesSued_" + i));
					map1.put("PolicyKey", ctx.get("PolicyKey"));
					map1.put("VersionSequence", ctx.get("VersionSequence"));
					map1.put("isSaved", map2.get("isSaved"));
					map1.put("ROWID", map2.get("ROWID"));
					newList.add(map1);

				}

				int savedFeeSuits = ctx.get("savedCount") != null && !ctx.get("savedCount").equals(HtmlConstants.EMPTY)
						? Integer.parseInt(ctx.get("savedCount").toString())
						: 0;
				int newFeeSuitsList = ctx.get("addFeeSuits") != null
						? Integer.parseInt(ctx.get("addFeeSuits").toString())
						: finalList.size();

				if (newFeeSuitsList > finalList.size()) {
					int index = finalList.size();
					Map map = null;
					for (int i = 0; i < newFeeSuitsList - finalList.size(); i++) {
						map = new HashMap();
						index = index + 1;
						map.put("FirminitLawsuitsKey", null);
//						map.put("NamedDefendantsInSuit", null);
//						map.put("DueDateFees", null);
						map.put("IsAllegOfLegalMalPrac", null);
						map.put("SuitFilesDateFees", null);
						map.put("FeeSuitDispositionKey", null);
						map.put("AmountOfFeesSued", null);
						map.put("isSaved", null);
						map.put("PolicyKey", ctx.get("PolicyKey"));
						map.put("VersionSequence", ctx.get("VersionSequence"));
						map.put("ROWID", index);
						newList.add(map);
					}
				} else if (newFeeSuitsList == 0) {
					int index = finalList.size();
					Map map = null;
					for (int i = 0; i <= newFeeSuitsList - finalList.size(); i++) {
						map = new HashMap();
						index = index + 1;
						map.put("FirminitLawsuitsKey", null);
//						map.put("NamedDefendantsInSuit", null);
//						map.put("DueDateFees", null);
						map.put("IsAllegOfLegalMalPrac", null);
						map.put("SuitFilesDateFees", null);
						map.put("FeeSuitDispositionKey", null);
						map.put("AmountOfFeesSued", null);
						map.put("isSaved", null);
						map.put("PolicyKey", ctx.get("PolicyKey"));
						map.put("VersionSequence", ctx.get("VersionSequence"));
						map.put("ROWID", index);
						newList.add(map);
					}
				} else {
					if (newFeeSuitsList < savedFeeSuits) {
						new AccountUtils().populateError(ctx, "feesuitIncompleteData",
								"Selected fee suit count cannot be less than of saved fee suit count.");
						ctx.put("addFeeSuits", savedFeeSuits);
					} else {
						int result = finalList.size() - newFeeSuitsList;
						int temp = finalList.size() - 1;
						for (int j = 0; j < result; j++)
							newList.remove(temp - j);
						ctx.put("addFeeSuits", newList.size());
					}
				}
				ctx.put("addFeeSuits", newList.size());
				ctx.put("FeeSuitData_list_01", newList);
				sess.setAttribute("FeeSuitData_list_01", newList);

			}
		} catch (Exception e) {
			logger.error("Error in AccountUtils.addNewFeeSuit", e);
		}
	}

	public static void updateFeeSuitList(Context ctx) {
		HttpServletRequest req = (HttpServletRequest) ctx.get("DocumentRequest");
		HttpSession sess = req.getSession();
		List<Map<String, Object>> newList = new ArrayList<Map<String, Object>>();
		List finalList = null;
		Map map1 = null, map2 = null;
		try {
			int ROWID = 0;
			int index = ctx.get("ROWID") != null && !ctx.get("ROWID").equals(HtmlConstants.EMPTY)
					? Integer.parseInt(ctx.get("ROWID").toString())
					: -2;
			if (ctx.get("FeeSuitData_list_01") == null)
				ctx.put("FeeSuitData_list_01", sess.getAttribute("FeeSuitData_list_01"));

			convertStringToList(ctx, "FeeSuitData_list_01");

			if (ctx.get("FeeSuitData_list_01") != null && ctx.get("FeeSuitData_list_01") instanceof List) {

				finalList = (List) ctx.get("FeeSuitData_list_01");

				for (int i = 0; i < finalList.size(); i++) {

					map1 = new HashMap();
					map2 = (HashMap) finalList.get(i);
					map1.put("FirminitLawsuitsKey", map2.get("FirminitLawsuitsKey"));
//					map1.put("NamedDefendantsInSuit", ("".equals(ctx.get("NamedDefendantsInSuit_" + i))) ? null :ctx.get("NamedDefendantsInSuit_" + i));
//					map1.put("DueDateFees", ("".equals(ctx.get("DueDateFees_" + i))) ? null :ctx.get("DueDateFees_" + i));
					map1.put("IsAllegOfLegalMalPrac", ("".equals(ctx.get("IsAllegOfLegalMalPrac_" + i))) ? null
							: ctx.get("IsAllegOfLegalMalPrac_" + i));
					map1.put("SuitFilesDateFees",
							("".equals(ctx.get("SuitFilesDateFees_" + i))) ? null : ctx.get("SuitFilesDateFees_" + i));
					map1.put("FeeSuitDispositionKey", ("".equals(ctx.get("FeeSuitDispositionKey_" + i))) ? null
							: ctx.get("FeeSuitDispositionKey_" + i));
					map1.put("AmountOfFeesSued",
							("".equals(ctx.get("AmountOfFeesSued_" + i))) ? null : ctx.get("AmountOfFeesSued_" + i));
					map1.put("PolicyKey", ctx.get("PolicyKey"));
					map1.put("VersionSequence", ctx.get("VersionSequence"));
					map1.put("isSaved", map2.get("isSaved"));
					map1.put("ROWID", map2.get("ROWID"));
					newList.add(map1);
				}
			}
			if (index != -2)
				newList.remove(index - 1);
			for (int i = 0; i < newList.size(); i++) {
				ROWID = ROWID + 1;
				map2 = (HashMap) newList.get(i);
				map2.put("ROWID", ROWID);
			}
			ctx.put("FeeSuitData_list_01", newList);
			sess.setAttribute("FeeSuitData_list_01", newList);
		} catch (Exception e) {
			logger.error("Error in AccountUtils.updateFeeSuitList", e);
		}
	}

	public static void convertStringToList(Context ctx, String list) {
		if (ctx.get(list) instanceof String) {
			String value = (String) ctx.get(list);

			value = value.substring(1, value.length() - 1); // remove curly brackets
			String[] mapDetails = value.split("},");

			List l = new ArrayList();
			for (int i = 0; i < mapDetails.length; i++) {
				int count = 0;
				if (i == (mapDetails.length - 1)) {
					count = 1;
				}
				String listValue = mapDetails[i].trim();
				String kayValue = listValue.substring(1, listValue.length() - count);
				String[] keyValuePairs = kayValue.split(", "); // split the string to creat key-value pairs
				Map map = new HashMap();
				for (String pair : keyValuePairs) // iterate over the pairs
				{
					String[] entry = pair.split("="); // split the pairs to get key and value
					map.put(entry[0].trim(), (entry[1] == null && "".equals(entry[1])) ? null : entry[1].trim()); // add
																													// them
																													// to
																													// the
																													// hashmap
																													// and
																													// trim
																													// whitespaces
				}
				l.add(map);
			}
			ctx.put(list, l);
		}
	}

	public static void saveFirmGrossRevenue(Context ctx) {
		try {
			HttpServletRequest req = (HttpServletRequest) ctx.get("DocumentRequest");
			HttpSession sess = req.getSession();

			List finalList = null;
			List inputList = new ArrayList();
			Context newCtx = new Context();
			newCtx.setProject(ctx.getProject());
			newCtx.putAll(ctx);
			if (sess.getAttribute("AboutFirm_list_01") != null
					&& sess.getAttribute("AboutFirm_list_01") instanceof List) {
				finalList = (List) sess.getAttribute("AboutFirm_list_01");

				for (int i = 0; i < finalList.size(); i++) {
					newCtx.remove("Amount_" + i);
				}

				Map map = null;
				for (int i = 0; i < finalList.size(); i++) {
					map = new HashMap();

					long amount = ctx.get("Amount_" + i) != null && !ctx.get("Amount_" + i).equals(HtmlConstants.EMPTY)
							? Long.parseLong(removeAmountFormat(ctx.get("Amount_" + i)).toString())
							: 0;

					map.put("Amount", amount);
					map.put("YearEndDate", ctx.get("YearEndDate_" + i));
					map.put("GrossRevenueKey", ctx.get("GrossRevenueKey_" + i));
					map.put("PolicyKey", ctx.get("PolicyKey"));
					map.put("VersionSequence", ctx.get("VersionSequence"));
					map.put("LastUpdateUserID", ctx.get("LastUpdateUserID"));

					inputList.add(map);

				}

				newCtx.put("inputList", inputList);
				AccountUtils.convertListDataToXML(newCtx, "inputList", "outputList");
				ctx.put("outputList", newCtx.get("outputList"));

			} else {
				if (ctx.get("Amount") != null && !"".equals(ctx.get("Amount"))) {
					long amount = Long.parseLong(removeAmountFormat(ctx.get("Amount")).toString());
					ctx.put("Amount", amount);
				}
			}
		} catch (Exception e) {
			logger.error("Error in AccountUtils.saveFirmGrossRevenue", e);
		}
	}

	public static void savePageOnTabs(Context ctx) {
		// Context localCtx = new Context();
		// localCtx.setProject(ctx.getProject());
		ctx.remove("isNotCompletlyFilled");
		ComponentImpl compImpl;
		try {
			Object fromPage = ctx.get("from_page");
			if (fromPage == null)
				return;
			compImpl = ComponentResources.getInstance(ctx).getComponent(fromPage.toString());
			// localCtx.putAll(ctx);
			// localCtx.put("inet_method", "save");
			/*
			 * Object objCompleted = SqlResources.getSqlMapProcessor(ctx).findByKey(
			 * "SqlStmts.UserStatementManualBoQueriesisAppCompletedForTabs", ctx);
			 * ctx.put("isAppCompletedSave", objCompleted); ctx.get("isAppCompletedSave");
			 */
			/*
			 * List firmDetails= (List) SqlResources.getSqlMapProcessor(ctx).select(
			 * "SqlStmts.UserStatementManualBoQueriesisAppCompletedForTabs",ctx);
			 * ctx.putAll((Map) firmDetails.get(0));
			 */

			boolean isContinue = false;
			Object objIsContinue = RuleUtils.executeRule(ctx, "AccountantRule.isContinueAction");
			if (objIsContinue != null && objIsContinue instanceof Boolean) {
				isContinue = (Boolean) objIsContinue;
			}
			/*
			 * String isAppCompleted=ctx.get("isAppCompleted")!=null &&
			 * !ctx.get("isAppCompleted").equals(HtmlConstants.EMPTY)?ctx.get(
			 * "isAppCompleted").toString():"N";
			 */
			if (isContinue == true) {
				ctx.put("inet_method", "continue");

				ctx.put("inet_skip_validation", "Y");
			} else {
				ctx.put("inet_method", "save");

			}
			compImpl.validate(ctx);

			compImpl.performAction(ctx);

			/*
			 * if(ctx.get("object_name")!=null &&
			 * ctx.get("object_name").toString().equals("quoteOption")) { int
			 * statusKey=ctx.get("StatusKey")!=null &&
			 * !ctx.get("StatusKey").equals(HtmlConstants.EMPTY)?Integer.parseInt(ctx.get(
			 * "StatusKey").toString()):0; if(statusKey==1 || statusKey==2)
			 * validateApplicationForcompletion(ctx); //validateAllPagesSavedNewApp(ctx); }
			 */
			if (ctx.get("inet_errors_list") == null)
				ctx.put("WORKFLOW_FORWARD", ctx.get("object_name"));
			else
				ctx.put("WORKFLOW_FORWARD", ctx.get("from_page"));
		} catch (Exception e) {
			logger.error("Error in AccountUtils.savePageOnTabs", e);
			// TODO Auto-generated catch block
		}

	}

	public static void viewTrusteeOrEstate(Context ctx) {
		HttpServletRequest req = (HttpServletRequest) ctx.get("DocumentRequest");
		HttpSession sess = req.getSession();
		List finalList = null;
		if (ctx.get("ServerTrusteeSupp_list_01") == null)
			ctx.put("ServerTrusteeSupp_list_01", sess.getAttribute("ServerTrusteeSupp_list_01"));

		if (ctx.get("ServerTrusteeSupp_list_01") != null && ctx.get("ServerTrusteeSupp_list_01") instanceof List) {
			finalList = (List) ctx.get("ServerTrusteeSupp_list_01");
			ctx.put("addTrustsOREstates", finalList.size());
			int feesuitsCount = ctx.get("addTrustsOREstates") != null
					? Integer.parseInt(ctx.get("addTrustsOREstates").toString())
					: finalList.size();
			if (feesuitsCount > finalList.size()) {

				Map map = new HashMap();
				for (int i = finalList.size(); i < finalList.size() + (feesuitsCount - finalList.size()); i++) {
					map.put("TrusteeOrEstateSuppKey", null);
					map.put("NameOfTrust", null);
					map.put("TypeofTrustKey", null);
					map.put("ValueOfAssetsTrust", null);
					map.put("IsBeneficiaryInterestTrust", null);
					finalList.add(map);

				}
				ctx.put("ServerTrusteeSupp_list_01", finalList);
			}
			sess.setAttribute("ServerTrusteeSupp_list_01", ctx.get("ServerTrusteeSupp_list_01"));
		}
	}

	public static void addNewTrusteeOrEstate(Context ctx) {
		HttpServletRequest req = (HttpServletRequest) ctx.get("DocumentRequest");
		HttpSession sess = req.getSession();
		List finalList = null;
		List<Map<String, Object>> newList = new ArrayList<Map<String, Object>>();
		try {
			if (ctx.get("ServerTrusteeSupp_list_01") == null)
				ctx.put("ServerTrusteeSupp_list_01", sess.getAttribute("ServerTrusteeSupp_list_01"));

			if (ctx.get("ServerTrusteeSupp_list_01") != null && ctx.get("ServerTrusteeSupp_list_01") instanceof List) {
				finalList = (List) ctx.get("ServerTrusteeSupp_list_01");
				Map map1 = null, map2 = null;
				for (int i = 0; i < finalList.size(); i++) {
					map1 = new HashMap();
					map2 = (HashMap) finalList.get(i);
					map1.put("TrusteeOrEstateSuppKey", map2.get("TrusteeOrEstateSuppKey"));
					map1.put("NameOfTrust",
							("".equals(ctx.get("NameOfTrust_" + i))) ? null : ctx.get("NameOfTrust_" + i));
					map1.put("TypeofTrustKey",
							("".equals(ctx.get("TypeofTrustKey_" + i))) ? null : ctx.get("TypeofTrustKey_" + i));
					map1.put("ValueOfAssetsTrust", ("".equals(ctx.get("ValueOfAssetsTrust_" + i))) ? null
							: ctx.get("ValueOfAssetsTrust_" + i));
					map1.put("IsBeneficiaryInterestTrust",
							("".equals(ctx.get("IsBeneficiaryInterestTrust_" + i))) ? null
									: ctx.get("IsBeneficiaryInterestTrust_" + i));
					map1.put("PolicyKey", ctx.get("PolicyKey"));
					map1.put("VersionSequence", ctx.get("VersionSequence"));
					map1.put("isSaved", map2.get("isSaved"));
					map1.put("ROWID", map2.get("ROWID"));
					newList.add(map1);

				}

				int savedFeeSuits = ctx.get("savedCount") != null && !ctx.get("savedCount").equals(HtmlConstants.EMPTY)
						? Integer.parseInt(ctx.get("savedCount").toString())
						: 0;
				int newFeeSuitsList = ctx.get("addTrustsOREstates") != null
						? Integer.parseInt(ctx.get("addTrustsOREstates").toString())
						: finalList.size();

				if (newFeeSuitsList > finalList.size()) {
					int index = finalList.size();
					Map map = null;
					for (int i = 0; i < newFeeSuitsList - finalList.size(); i++) {
						map = new HashMap();
						index = index + 1;
						map.put("TrusteeOrEstateSuppKey", null);
						map.put("NameOfTrust", null);
						map.put("TypeofTrustKey", null);
						map.put("ValueOfAssetsTrust", null);
						map.put("IsBeneficiaryInterestTrust", null);
						map.put("isSaved", null);
						map.put("PolicyKey", ctx.get("PolicyKey"));
						map.put("VersionSequence", ctx.get("VersionSequence"));
						map.put("ROWID", index);
						newList.add(map);
					}
				} else if (newFeeSuitsList == 0) {
					int index = finalList.size();
					Map map = null;
					for (int i = 0; i <= newFeeSuitsList - finalList.size(); i++) {
						map = new HashMap();
						index = index + 1;
						map.put("TrusteeOrEstateSuppKey", null);
						map.put("NameOfTrust", null);
						map.put("TypeofTrustKey", null);
						map.put("ValueOfAssetsTrust", null);
						map.put("IsBeneficiaryInterestTrust", null);
						map.put("isSaved", null);
						map.put("PolicyKey", ctx.get("PolicyKey"));
						map.put("VersionSequence", ctx.get("VersionSequence"));
						map.put("ROWID", index);
						newList.add(map);
					}
				} else {
					if (newFeeSuitsList < savedFeeSuits) {
						new AccountUtils().populateError(ctx, "IsServerTrusteeSupp",
								"Selected fee suit count cannot be less than of saved fee suit count.");
						ctx.put("addTrustsOREstates", savedFeeSuits);
					} else {
						int result = finalList.size() - newFeeSuitsList;
						int temp = finalList.size() - 1;
						for (int j = 0; j < result; j++)
							newList.remove(temp - j);
						ctx.put("addTrustsOREstates", newList.size());
					}
				}
				ctx.put("addTrustsOREstates", newList.size());
				ctx.put("ServerTrusteeSupp_list_01", newList);
				sess.setAttribute("ServerTrusteeSupp_list_01", newList);

			}
		} catch (Exception e) {
			logger.error("Error in AccountUtils.addNewTrusteeOrEstate", e);
		}
	}

	public static void updateTrusteeOrEstateList(Context ctx) {
		HttpServletRequest req = (HttpServletRequest) ctx.get("DocumentRequest");
		HttpSession sess = req.getSession();
		List<Map<String, Object>> newList = new ArrayList<Map<String, Object>>();
		List finalList = null;
		Map map1 = null, map2 = null;
		try {
			int ROWID = 0;
			int index = ctx.get("ROWID") != null && !ctx.get("ROWID").equals(HtmlConstants.EMPTY)
					? Integer.parseInt(ctx.get("ROWID").toString())
					: -2;
			if (ctx.get("ServerTrusteeSupp_list_01") == null)
				ctx.put("ServerTrusteeSupp_list_01", sess.getAttribute("ServerTrusteeSupp_list_01"));

			if (ctx.get("ServerTrusteeSupp_list_01") != null && ctx.get("ServerTrusteeSupp_list_01") instanceof List) {

				finalList = (List) ctx.get("ServerTrusteeSupp_list_01");

				for (int i = 0; i < finalList.size(); i++) {

					map1 = new HashMap();
					map2 = (HashMap) finalList.get(i);
					map1.put("TrusteeOrEstateSuppKey", map2.get("TrusteeOrEstateSuppKey"));
					map1.put("NameOfTrust",
							("".equals(ctx.get("NameOfTrust_" + i))) ? null : ctx.get("NameOfTrust_" + i));
					map1.put("TypeofTrustKey",
							("".equals(ctx.get("TypeofTrustKey_" + i))) ? null : ctx.get("TypeofTrustKey_" + i));
					map1.put("ValueOfAssetsTrust", ("".equals(ctx.get("ValueOfAssetsTrust_" + i))) ? null
							: ctx.get("ValueOfAssetsTrust_" + i));
					map1.put("IsBeneficiaryInterestTrust",
							("".equals(ctx.get("IsBeneficiaryInterestTrust_" + i))) ? null
									: ctx.get("IsBeneficiaryInterestTrust_" + i));
					map1.put("PolicyKey", ctx.get("PolicyKey"));
					map1.put("VersionSequence", ctx.get("VersionSequence"));
					map1.put("isSaved", map2.get("isSaved"));
					map1.put("ROWID", map2.get("ROWID"));
					newList.add(map1);
				}
			}
			if (index != -2)
				newList.remove(index - 1);
			for (int i = 0; i < newList.size(); i++) {
				ROWID = ROWID + 1;
				map2 = (HashMap) newList.get(i);
				map2.put("ROWID", ROWID);
			}
			ctx.put("ServerTrusteeSupp_list_01", newList);
			sess.setAttribute("ServerTrusteeSupp_list_01", newList);
		} catch (Exception e) {
			logger.error("Error in AccountUtils.updateTrusteeOrEstateList", e);
		}
	}

	public static void saveTrusteeOrEstateData(Context ctx) {
		try {
			HttpServletRequest req = (HttpServletRequest) ctx.get("DocumentRequest");
			HttpSession sess = req.getSession();

			List finalList = null;
			List inputList = new ArrayList();
			Context newCtx = new Context();
			newCtx.setProject(ctx.getProject());
			newCtx.putAll(ctx);

			if (ctx.get("ServerTrusteeSupp_list_01") == null)
				ctx.put("ServerTrusteeSupp_list_01", sess.getAttribute("ServerTrusteeSupp_list_01"));

			if (ctx.get("ServerTrusteeSupp_list_01") != null && ctx.get("ServerTrusteeSupp_list_01") instanceof List) {
				finalList = (List) ctx.get("ServerTrusteeSupp_list_01");
				for (int i = 0; i < finalList.size(); i++) {
					newCtx.remove("ValueOfAssetsTrust_" + i);
				}
				Map map = null;
				for (int i = 0; i < finalList.size(); i++) {
					map = new HashMap();
					if (!ctx.get("NameOfTrust_" + i).equals(HtmlConstants.EMPTY) && ctx.get("NameOfTrust_" + i) != null
							&& !ctx.get("TypeofTrustKey_" + i).equals(HtmlConstants.EMPTY)
							&& ctx.get("TypeofTrustKey_" + i) != null
							&& !ctx.get("ValueOfAssetsTrust_" + i).equals(HtmlConstants.EMPTY)
							&& ctx.get("ValueOfAssetsTrust_" + i) != null
							&& !ctx.get("IsBeneficiaryInterestTrust_" + i).equals(HtmlConstants.EMPTY)
							&& ctx.get("IsBeneficiaryInterestTrust_" + i) != null) {
						map.put("NameOfTrust", ctx.get("NameOfTrust_" + i));
						map.put("TypeofTrustKey", ctx.get("TypeofTrustKey_" + i));
						map.put("ValueOfAssetsTrust", removeAmountFormat(ctx.get("ValueOfAssetsTrust_" + i)));
						map.put("IsBeneficiaryInterestTrust", ctx.get("IsBeneficiaryInterestTrust_" + i));
						map.put("PolicyKey", ctx.get("PolicyKey"));
						map.put("VersionSequence", ctx.get("VersionSequence"));
						inputList.add(map);
					}
				}

				newCtx.put("inputList", inputList);
				convertListDataToXML(newCtx, "inputList", "outputList");
				ctx.put("outputList", newCtx.get("outputList"));
			}
		} catch (Exception e) {
			logger.error("Error in AccountUtils.saveTrusteeOrEstateData", e);
		}
	}

	public static void viewOutsideInterest(Context ctx) {
		HttpServletRequest req = (HttpServletRequest) ctx.get("DocumentRequest");
		HttpSession sess = req.getSession();
		List finalList = null;
		if (ctx.get("OutsideInterestSupp_list_01") == null)
			ctx.put("OutsideInterestSupp_list_01", sess.getAttribute("OutsideInterestSupp_list_01"));

		if (ctx.get("OutsideInterestSupp_list_01") != null && ctx.get("OutsideInterestSupp_list_01") instanceof List) {
			finalList = (List) ctx.get("OutsideInterestSupp_list_01");
			ctx.put("addOutsideInterest", finalList.size());
			int feesuitsCount = ctx.get("addOutsideInterest") != null
					? Integer.parseInt(ctx.get("addOutsideInterest").toString())
					: finalList.size();
			if (feesuitsCount > finalList.size()) {

				Map map = new HashMap();
				for (int i = finalList.size(); i < finalList.size() + (feesuitsCount - finalList.size()); i++) {
					map.put("OutsideInterestSuppKey", null);
					map.put("ClientNameOsi", null);
					map.put("EntityNameOsi", null);
					map.put("ClientIndustryOsiKey", null);
					map.put("PositionHeldOsi", null);
					map.put("DOInsuranceOsi", null);
					map.put("PercentEquityInterestofOsi", null);
					map.put("ServiceIndustryOsiKey", null);
					map.put("IndividualListedServicesOsi", null);
					finalList.add(map);

				}
				ctx.put("OutsideInterestSupp_list_01", finalList);
			}
			sess.setAttribute("OutsideInterestSupp_list_01", ctx.get("OutsideInterestSupp_list_01"));
		}
	}

	public static void addNewOutsideInterest(Context ctx) {
		HttpServletRequest req = (HttpServletRequest) ctx.get("DocumentRequest");
		HttpSession sess = req.getSession();
		List finalList = null;
		List<Map<String, Object>> newList = new ArrayList<Map<String, Object>>();
		try {
			if (ctx.get("OutsideInterestSupp_list_01") == null)
				ctx.put("OutsideInterestSupp_list_01", sess.getAttribute("OutsideInterestSupp_list_01"));

			if (ctx.get("OutsideInterestSupp_list_01") != null
					&& ctx.get("OutsideInterestSupp_list_01") instanceof List) {
				finalList = (List) ctx.get("OutsideInterestSupp_list_01");
				Map map1 = null, map2 = null;
				for (int i = 0; i < finalList.size(); i++) {
					map1 = new HashMap();
					map2 = (HashMap) finalList.get(i);
					map1.put("OutsideInterestSuppKey", map2.get("OutsideInterestSuppKey"));
					map1.put("ClientNameOsi",
							("".equals(ctx.get("ClientNameOsi_" + i))) ? null : ctx.get("ClientNameOsi_" + i));
					map1.put("EntityNameOsi",
							("".equals(ctx.get("EntityNameOsi_" + i))) ? null : ctx.get("EntityNameOsi_" + i));
					map1.put("ClientIndustryOsiKey", ("".equals(ctx.get("ClientIndustryOsiKey_" + i))) ? null
							: ctx.get("ClientIndustryOsiKey_" + i));
					map1.put("PositionHeldOsi",
							("".equals(ctx.get("PositionHeldOsi_" + i))) ? null : ctx.get("PositionHeldOsi_" + i));
					map1.put("DOInsuranceOsi",
							("".equals(ctx.get("DOInsuranceOsi_" + i))) ? null : ctx.get("DOInsuranceOsi_" + i));
					map1.put("PercentEquityInterestofOsi",
							("".equals(ctx.get("PercentEquityInterestofOsi_" + i))) ? null
									: ctx.get("PercentEquityInterestofOsi_" + i));
					map1.put("ServiceIndustryOsiKey", ("".equals(ctx.get("ServiceIndustryOsiKey_" + i))) ? null
							: ctx.get("ServiceIndustryOsiKey_" + i));
					map1.put("IndividualListedServicesOsi",
							("".equals(ctx.get("IndividualListedServicesOsi_" + i))) ? null
									: ctx.get("IndividualListedServicesOsi_" + i));
					map1.put("PolicyKey", ctx.get("PolicyKey"));
					map1.put("VersionSequence", ctx.get("VersionSequence"));
					map1.put("isSaved", map2.get("isSaved"));
					map1.put("ROWID", map2.get("ROWID"));
					newList.add(map1);

				}

				int savedFeeSuits = ctx.get("savedCount") != null && !ctx.get("savedCount").equals(HtmlConstants.EMPTY)
						? Integer.parseInt(ctx.get("savedCount").toString())
						: 0;
				int newFeeSuitsList = ctx.get("addOutsideInterest") != null
						? Integer.parseInt(ctx.get("addOutsideInterest").toString())
						: finalList.size();

				if (newFeeSuitsList > finalList.size()) {
					int index = finalList.size();
					Map map = null;
					for (int i = 0; i < newFeeSuitsList - finalList.size(); i++) {
						map = new HashMap();
						index = index + 1;
						map.put("OutsideInterestSuppKey", null);
						map.put("ClientNameOsi", null);
						map.put("EntityNameOsi", null);
						map.put("ClientIndustryOsiKey", null);
						map.put("PositionHeldOsi", null);
						map.put("DOInsuranceOsi", null);
						map.put("PercentEquityInterestofOsi", null);
						map.put("ServiceIndustryOsiKey", null);
						map.put("IndividualListedServicesOsi", null);
						map.put("isSaved", null);
						map.put("PolicyKey", ctx.get("PolicyKey"));
						map.put("VersionSequence", ctx.get("VersionSequence"));
						map.put("ROWID", index);
						newList.add(map);
					}
				} else if (newFeeSuitsList == 0) {
					int index = finalList.size();
					Map map = null;
					for (int i = 0; i <= newFeeSuitsList - finalList.size(); i++) {
						map = new HashMap();
						index = index + 1;
						map.put("OutsideInterestSuppKey", null);
						map.put("ClientNameOsi", null);
						map.put("EntityNameOsi", null);
						map.put("ClientIndustryOsiKey", null);
						map.put("PositionHeldOsi", null);
						map.put("DOInsuranceOsi", null);
						map.put("PercentEquityInterestofOsi", null);
						map.put("ServiceIndustryOsiKey", null);
						map.put("IndividualListedServicesOsi", null);
						map.put("isSaved", null);
						map.put("PolicyKey", ctx.get("PolicyKey"));
						map.put("VersionSequence", ctx.get("VersionSequence"));
						map.put("ROWID", index);
						newList.add(map);
					}

				} else {
					if (newFeeSuitsList < savedFeeSuits) {
						new AccountUtils().populateError(ctx, "IsOutsideInterestSupp",
								"Selected fee suit count cannot be less than of saved fee suit count.");
						ctx.put("addOutsideInterest", savedFeeSuits);
					} else {
						int result = finalList.size() - newFeeSuitsList;
						int temp = finalList.size() - 1;
						for (int j = 0; j < result; j++)
							newList.remove(temp - j);
						ctx.put("addOutsideInterest", newList.size());
					}
				}
				ctx.put("addOutsideInterest", newList.size());
				ctx.put("OutsideInterestSupp_list_01", newList);
				sess.setAttribute("OutsideInterestSupp_list_01", newList);

			}
		} catch (Exception e) {
			logger.error("Error in AccountUtils.addNewOutsideInterest", e);
		}
	}

	public static void updateOutsideInterestList(Context ctx) {
		HttpServletRequest req = (HttpServletRequest) ctx.get("DocumentRequest");
		HttpSession sess = req.getSession();
		List<Map<String, Object>> newList = new ArrayList<Map<String, Object>>();
		List finalList = null;
		Map map1 = null, map2 = null;
		try {
			int ROWID = 0;
			int index = ctx.get("ROWID") != null && !ctx.get("ROWID").equals(HtmlConstants.EMPTY)
					? Integer.parseInt(ctx.get("ROWID").toString())
					: -2;
			if (ctx.get("OutsideInterestSupp_list_01") == null)
				ctx.put("OutsideInterestSupp_list_01", sess.getAttribute("OutsideInterestSupp_list_01"));

			if (ctx.get("OutsideInterestSupp_list_01") != null
					&& ctx.get("OutsideInterestSupp_list_01") instanceof List) {

				finalList = (List) ctx.get("OutsideInterestSupp_list_01");

				for (int i = 0; i < finalList.size(); i++) {

					map1 = new HashMap();
					map2 = (HashMap) finalList.get(i);
					map1.put("OutsideInterestSuppKey", map2.get("OutsideInterestSuppKey"));
					map1.put("ClientNameOsi",
							("".equals(ctx.get("ClientNameOsi_" + i))) ? null : ctx.get("ClientNameOsi_" + i));
					map1.put("EntityNameOsi",
							("".equals(ctx.get("EntityNameOsi_" + i))) ? null : ctx.get("EntityNameOsi_" + i));
					map1.put("ClientIndustryOsiKey", ("".equals(ctx.get("ClientIndustryOsiKey_" + i))) ? null
							: ctx.get("ClientIndustryOsiKey_" + i));
					map1.put("PositionHeldOsi",
							("".equals(ctx.get("PositionHeldOsi_" + i))) ? null : ctx.get("PositionHeldOsi_" + i));
					map1.put("DOInsuranceOsi",
							("".equals(ctx.get("DOInsuranceOsi_" + i))) ? null : ctx.get("DOInsuranceOsi_" + i));
					map1.put("PercentEquityInterestofOsi",
							("".equals(ctx.get("PercentEquityInterestofOsi_" + i))) ? null
									: ctx.get("PercentEquityInterestofOsi_" + i));
					map1.put("ServiceIndustryOsiKey", ("".equals(ctx.get("ServiceIndustryOsiKey_" + i))) ? null
							: ctx.get("ServiceIndustryOsiKey_" + i));
					map1.put("IndividualListedServicesOsi",
							("".equals(ctx.get("IndividualListedServicesOsi_" + i))) ? null
									: ctx.get("IndividualListedServicesOsi_" + i));
					map1.put("PolicyKey", ctx.get("PolicyKey"));
					map1.put("VersionSequence", ctx.get("VersionSequence"));
					map1.put("isSaved", map2.get("isSaved"));
					map1.put("ROWID", map2.get("ROWID"));
					newList.add(map1);
				}
			}
			if (index != -2)
				newList.remove(index - 1);
			for (int i = 0; i < newList.size(); i++) {
				ROWID = ROWID + 1;
				map2 = (HashMap) newList.get(i);
				map2.put("ROWID", ROWID);
			}
			ctx.put("OutsideInterestSupp_list_01", newList);
			sess.setAttribute("OutsideInterestSupp_list_01", newList);
		} catch (Exception e) {
			logger.error("Error in AccountUtils.updateOutsideInterestList", e);
		}
	}

	public static void saveOutsideInterestData(Context ctx) {
		try {
			HttpServletRequest req = (HttpServletRequest) ctx.get("DocumentRequest");
			HttpSession sess = req.getSession();

			List finalList = null;
			List inputList = new ArrayList();
			Context newCtx = new Context();
			newCtx.setProject(ctx.getProject());
			newCtx.putAll(ctx);

			if (ctx.get("OutsideInterestSupp_list_01") == null)
				ctx.put("OutsideInterestSupp_list_01", sess.getAttribute("OutsideInterestSupp_list_01"));

			if (ctx.get("OutsideInterestSupp_list_01") != null
					&& ctx.get("OutsideInterestSupp_list_01") instanceof List) {
				finalList = (List) ctx.get("OutsideInterestSupp_list_01");
				// for (int i = 0; i < finalList.size(); i++) {
				// newCtx.remove("AmountOfFeesSued_" + i);
				// }
				Map map = null;
				for (int i = 0; i < finalList.size(); i++) {
					map = new HashMap();
					if (!ctx.get("ClientNameOsi_" + i).equals(HtmlConstants.EMPTY)
							&& ctx.get("ClientNameOsi_" + i) != null
							&& !ctx.get("EntityNameOsi_" + i).equals(HtmlConstants.EMPTY)
							&& ctx.get("EntityNameOsi_" + i) != null
							&& !ctx.get("ClientIndustryOsiKey_" + i).equals(HtmlConstants.EMPTY)
							&& ctx.get("ClientIndustryOsiKey_" + i) != null
							&& !ctx.get("PositionHeldOsi_" + i).equals(HtmlConstants.EMPTY)
							&& ctx.get("PositionHeldOsi_" + i) != null
							&& !ctx.get("DOInsuranceOsi_" + i).equals(HtmlConstants.EMPTY)
							&& ctx.get("DOInsuranceOsi_" + i) != null
							&& !ctx.get("PercentEquityInterestofOsi_" + i).equals(HtmlConstants.EMPTY)
							&& ctx.get("PercentEquityInterestofOsi_" + i) != null
							&& !ctx.get("ServiceIndustryOsiKey_" + i).equals(HtmlConstants.EMPTY)
							&& ctx.get("ServiceIndustryOsiKey_" + i) != null
							&& !ctx.get("IndividualListedServicesOsi_" + i).equals(HtmlConstants.EMPTY)
							&& ctx.get("IndividualListedServicesOsi_" + i) != null) {
						map.put("ClientNameOsi", ctx.get("ClientNameOsi_" + i));
						map.put("EntityNameOsi", ctx.get("EntityNameOsi_" + i));
						map.put("ClientIndustryOsiKey", ctx.get("ClientIndustryOsiKey_" + i));
						map.put("PositionHeldOsi", ctx.get("PositionHeldOsi_" + i));
						map.put("DOInsuranceOsi", ctx.get("DOInsuranceOsi_" + i));
						map.put("PercentEquityInterestofOsi", ctx.get("PercentEquityInterestofOsi_" + i));
						map.put("ServiceIndustryOsiKey", ctx.get("ServiceIndustryOsiKey_" + i));
						map.put("IndividualListedServicesOsi", ctx.get("IndividualListedServicesOsi_" + i));
						map.put("PolicyKey", ctx.get("PolicyKey"));
						map.put("VersionSequence", ctx.get("VersionSequence"));
						inputList.add(map);
					}
				}

				newCtx.put("inputList", inputList);
				convertListDataToXML(newCtx, "inputList", "outputList");
				ctx.put("outputList", newCtx.get("outputList"));
			}
		} catch (Exception e) {
			logger.error("Error in AccountUtils.saveOutsideInterestData", e);
		}
	}

	public static void validateRevenueDetails(Context ctx) {
		logger.debug("going to validate revenue details");
		try {
			validateEstimateForCurrentYear((IContext) ctx);
		} catch (Exception e) {
			logger.error("exeception occured while validating revenue details " + e, e);
		}
	}

	private String generateCompleteAppLinkMailBody(IContext ctx) throws Exception {
		AccountantValidationUtils utils = new AccountantValidationUtils();
		String encrytedPolicyKey = utils.encrypt(ctx.get("PolicyKey").toString());
		encrytedPolicyKey = encrytedPolicyKey.replace("=", "%3D");
		encrytedPolicyKey = encrytedPolicyKey.replace("+", "%2B");

		String msg = "";
		msg = msg + "<table>";
		msg = msg + "<tr>";
		msg = msg + "<td>";

		msg = msg + "Thank you for beginning the application process with Protexure!" + "<br/><br/>";

		msg = msg + "If you are unable to complete the application for any reason, don't worry!" + "<br/>";
		msg = msg + "The information you've provided isn't lost." + "<br/><br/>";

		/*
		 * msg = msg + "Dear (" + ctx.get("AccountName").toString() + ")," +
		 * "<br/><br/>";
		 */
		msg = msg + "All you need to do is  <a href=" + getProjectUrl(ctx) + "/completeapplink.jsp?PolicyKey="
				+ encrytedPolicyKey + ">continue here</a>, and you will be able to:"
				/* + " to complete your account with us." */

				+ "<br/><br/> ";

		msg = msg + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; " + " &#8226;   "
				+ "&nbsp; <a href=" + getProjectUrl(ctx) + "/completeapplink.jsp?PolicyKey=" + encrytedPolicyKey
				+ ">View the information entered so far</a>" + "<br/> ";

		msg = msg + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; " + " &#8226;   "
				+ "&nbsp; <a href=" + getProjectUrl(ctx) + "/completeapplink.jsp?PolicyKey=" + encrytedPolicyKey
				+ ">Edit and continue to complete the application</a>" + "<br/> ";

		msg = msg + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; " + " &#8226;   "
				+ "&nbsp; <a href=" + getProjectUrl(ctx) + "/completeapplink.jsp?PolicyKey=" + encrytedPolicyKey
				+ ">Get your Quote!</a>" + "<br/><br/> ";

		msg = msg

				+ "Applications can be a pain, Protexure offers an easy to complete annual online application process."
				+ "<br/><br/>";
		msg = msg
				+ "If you have any questions, or need assistance with completing the application, please feel free to call us"

				+ " at 877-569-4111 and one of our licensed insurance professionals will gladly assist you."
				+ "<br/><br/>";
		msg = msg + "Sincerely," + "<br/><br/>";
		msg = msg + "The Protexure Team" + "<br/>";

		msg = msg + "</td>";
		msg = msg + "</tr>";
		msg = msg + "</table>";

		return msg;
	}

	public static boolean checkIfSubProducerExist(IContext ctx) throws Exception {

		Object obj = SqlResources.getSqlMapProcessor(ctx).findByKey("PolicyTransaction.findByKey", ctx);
		boolean producerCodeExist = false;

		if (obj != null && obj instanceof Map) {

			Map objMap = (Map) obj;

			if (objMap.get("ProducerCode") != null && !"".equals(objMap.get("ProducerCode").toString())) {
				producerCodeExist = true;
			} else {
				producerCodeExist = false;
			}
		} else {
			producerCodeExist = false;
		}
		return producerCodeExist;

	}

	public void sendCompleteAppLinkMail(IContext ctx) throws Exception {

		Object obj = SqlResources.getSqlMapProcessor(ctx)
				.findByKey("SqlStmts.UserStatementManualBoQueriesgetAccountDetailsForCompleteAppLink", ctx);

		if (obj != null && obj instanceof Map) {
			Map map1 = (Map) obj;
			ctx.putAll(map1);
		}

		String dstId = "";
		// Do not send quote letter mail to insured if producer code is
		// there
		boolean producerCodeExist = checkIfSubProducerExist(ctx);
		if (producerCodeExist) {
			try {
				dstId = SystemProperties.getInstance().getString("appl." + ctx.getProject() + ".admin.email");
			} catch (Exception e) {
				logger.error("Error in AccountUtils.sendCompleteAppLinkMail", e);
			}
		} else {
			dstId = getEmailID(ctx);
		}

		// String cc_role_desc =
		// SystemProperties.getInstance().getString("mail.admin.cc.addr");
		if ("".equals(dstId))
			return;
		String bccAddress = "";
		bccAddress = SystemProperties.getInstance().getString("appl." + ctx.getProject() + ".admin.cc.email");

		logger.debug("Mail is going---- ");
		MailSender mail = new MailSender();
		mail.setToAdd(dstId);
		mail.setCcAdd(getFirstReviewerAgentEmailID(ctx));
		mail.setBccAdd(bccAddress);
		mail.setSubject("Protexure Accountant Application for " + ctx.get("AccountName").toString() + "");
		mail.setContentType("text/html");
		mail.setBody(generateCompleteAppLinkMailBody(ctx));
		mail.sendMail((Context) ctx);
		logger.debug("Mail has sent---- ");

	}

	public static void handleRequestParams(IContext ctx) throws Exception {

		ctx.remove("IsSubProducerFlow");

		if (ctx.get("ProducerCode") != null && "SPACE".equals(ctx.get("ProducerCode").toString())) {

			ctx.put("ProducerCode", "");
			ctx.put("IsSubProducerPolicy", "N");
			ctx.put("ComingFromSubProducerLink", "N");

		} else if (ctx.get("ProducerCode") != null && !"SPACE".equals(ctx.get("ProducerCode").toString())
				&& !"".equals(ctx.get("ProducerCode").toString())) {

			String producerCode = ctx.get("ProducerCode").toString();
			if (producerCode.length() > 50) {

				producerCode = producerCode.substring(0, 48);
				ctx.put("ProducerCode", producerCode);

			} else {
				ctx.put("ProducerCode", producerCode);
			}

			/*
			 * Object object = SqlResources .getSqlMapProcessor(ctx) .findByKey(
			 * "SqlStmts.UserStatementManualBoQueriesSubProducerData", ctx); if (object !=
			 * null && object instanceof Map) {
			 * 
			 * Map producerMap = (Map) object; if (producerMap != null) {
			 * 
			 * if (producerMap.get("IsActive") != null &&
			 * "Y".equals(producerMap.get("IsActive") .toString())) {
			 * 
			 * ctx.put("IsSubProducerFlow", "Y");
			 * 
			 * }
			 */

		}
	}

	public String decrypt(String message) throws Exception {
		try {
			final MessageDigest md = MessageDigest.getInstance("md5");
			final byte[] digestOfPassword = md.digest(key.getBytes("utf-8"));
			final byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
			for (int j = 0, k = 16; j < 8;) {
				keyBytes[k++] = keyBytes[j++];
			}
			final SecretKey key = new SecretKeySpec(keyBytes, "DESede");
			final IvParameterSpec iv = new IvParameterSpec(new byte[8]);
			final Cipher decipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
			decipher.init(Cipher.DECRYPT_MODE, key);
			final byte[] encData = new sun.misc.BASE64Decoder().decodeBuffer(message);
			final byte[] plainText = decipher.doFinal(encData);
			return new String(plainText, "UTF-8");
		} catch (Exception e) {
			logger.error("Error in AccountUtils.decrypt", e);
			return message;
		}
	}

	public void decryptSubproducerNumber(IContext ctx) {
		try {
			String pNumber = ctx.get("SubProducer") != null && !ctx.get("SubProducer").equals(HtmlConstants.EMPTY)
					? ctx.get("SubProducer").toString()
					: "";
			if (!"".equals(pNumber)) {
				HttpServletRequest req = (HttpServletRequest) ctx.get("DocumentRequest");
				HttpSession sess = req.getSession();

				String decryptPnumber = decrypt(pNumber);
				ctx.put("SubProducer", decryptPnumber);
			}
		} catch (Exception e) {
			logger.error("Error in AccountUtils.decryptSubproducerNumber", e);

		}
	}

	public static void validateSubProducerNumber(Context ctx) {
		logger.debug("going to validate subproducer number");
		try {
			String pNumber = ctx.get("SubProducer") != null && !ctx.get("SubProducer").equals(HtmlConstants.EMPTY)
					? ctx.get("SubProducer").toString()
					: "";
			if (pNumber.equals("SPACE"))
				ctx.put("SubProducer", "");
		} catch (Exception e) {
			logger.error("Error in AccountUtils.validateSubProducerNumber", e);

		}
	}

	public void updatePolicyData(IContext ctx) throws Exception {
		RuleUtils.executeRule(ctx, "AccountantRule.AssignLastUpdateTimeStamp");

		SqlResources.getSqlMapProcessor(ctx).update("SqlStmts.UserStatementManualBoQueriesupdatePolicy", ctx);

		Object objAccount = SqlResources.getSqlMapProcessor(ctx)
				.findByKey("SqlStmts.UserStatementsaveAccountstmtsgetIndicationAccountKeys", ctx);
		Map mapAccount = null;
		if (objAccount != null) {
			mapAccount = (Map) objAccount;
			String addKey = null;
			String accKey = null;
			if (mapAccount.get("AccountKey") != null)
				accKey = mapAccount.get("AccountKey").toString();
			if (mapAccount.get("AddressKey") != null)
				addKey = mapAccount.get("AddressKey").toString();

			ctx.put("AddressKey", addKey);
			SqlResources.getSqlMapProcessor(ctx).update("SqlStmts.UserStatementManualBoQueriesupdatePolicyAddress",
					ctx);
		}

		SqlResources.getSqlMapProcessor(ctx).update("SqlStmts.UserStatementManualBoQueriesupdateFirmYear", ctx);
	}

	public static void sendIndicationToInsured(IContext ctx) {
		String mailingOnOFF = null;
		try {

			mailingOnOFF = SystemProperties.getInstance().getString("Insured.sendmail");
		} catch (Exception e) {
			logger.error("Error in AccountUtils.sendIndicationToInsured", e);
		}

		try {
			if (mailingOnOFF != null && "Y".equals(mailingOnOFF)) {
				// if(ctx.get("IsQuickQuoteByEmail") != null &&
				// "Y".equals(ctx.get("IsQuickQuoteByEmail").toString()))
				// {
				String bccAddress = "";
				String emailID = getEmailID(ctx);
				if ("".equals(emailID))
					return;
				bccAddress = SystemProperties.getInstance().getString("appl." + ctx.getProject() + ".admin.cc.email");
				logger.debug("Mail is going---- ");
				MailSender mail = new MailSender();
				mail.setToAdd(emailID);
				mail.setCcAdd(getFirstReviewerAgentEmailID(ctx));
				mail.setBccAdd(bccAddress);
				mail.setSubject("Professional Liability Estimate from Protexure");
				mail.setContentType("text/html");
				mail.setBody(generateIndicationBody(ctx, mail));
				mail.sendMail((Context) ctx);
				logger.debug("Mail has sent---- ");
				// }
			}
		} catch (Exception e) {
			logger.error("Error in AccountUtils.sendIndicationToInsured", e);
		}
	}

	private static String generateIndicationBody(IContext ctx, MailSender mail) throws Exception {

		AccountantValidationUtils utils = new AccountantValidationUtils();
		String encrytedPolicyKey = utils.encrypt(ctx.get("PolicyKey").toString());
		encrytedPolicyKey = encrytedPolicyKey.replace("=", "%3D");
		encrytedPolicyKey = encrytedPolicyKey.replace("+", "%2B");
		NumberFormat numberFormat = NumberFormat.getCurrencyInstance(Locale.US);
		AccountUtils accountUtils = new AccountUtils();
		String msg = "";
		msg = msg + "<table>";
		msg = msg + "<tr>";
		msg = msg + "<td>";

		msg = msg + ctx.get("AccountName") + ",<br/><br/>";

		msg = msg
				+ "Thank you for giving us the opportunity to provide you with an estimate for professional liability coverage!"
				+ "<br/><br/>";

		msg = msg + "Based on the information you provided we have generated the following premium estimate(s): "
				+ "<br/><br/>";

		List aoplist = null;
		if (ctx.get("indication_list_01") != null) {
			aoplist = (List) ctx.get("indication_list_01");
		} else {
			aoplist = SqlResources.getSqlMapProcessor(ctx).select("PolicyTransition.GetPolicyIndications_p", ctx);
		}
		if (aoplist != null) {
			Map map = new HashMap();
			for (int i = 0; i < aoplist.size(); i++) {
				map = (HashMap) aoplist.get(i);
				/*
				 * if("Y".equals(map.get("IsQuoteSelected"))){ msg = msg +
				 * "<img src=\"cid:memememe\"/>" + "&nbsp;&nbsp;";
				 * mail.setImgPath(SystemProperties.getInstance().getString("appl.home.dir") +
				 * "images//check-btn4.png"); } else { msg = msg +
				 * "<img src=\"cid:memememe1\"/>" + "&nbsp;&nbsp;";
				 * mail.setImgPath1(SystemProperties.getInstance().getString("appl.home.dir") +
				 * "images//check-btn4.png"); }
				 */
				int count = i + 1;
				msg = msg + "<b><u>Option " + count + "</u></b><br/>";

				msg = msg + "Limit: <b>" + map.get("LimitAmount") + "</b>&nbsp;&nbsp;<br/>";
				if (map.get("IsClaimExpensesType").equals("Yes")) {
					msg = msg + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
							+ "Claims Expense Outside the Limit <br/>";
				}
				if (map.get("IsClaimExpensesType").equals("No")) {
					msg = msg + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
							+ "Claims Expenses Inside the Limit <br/>";
				}

				msg = msg + "Deductible: <b>" + map.get("DeductibleAmount") + "</b>&nbsp;&nbsp;<br/>";

				if (map.get("IsClaimOptionType").equals("Yes")) {
					msg = msg + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + "Aggregate ";
				}
				if (map.get("IsClaimOptionType").equals("No")) {
					msg = msg + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + "Per Claim";
				}
				if (map.get("IsDollarDefenseText").equals("Yes")) {
					msg = msg + ", First Dollar Defense";

				}
				msg = msg + "<br/>";

				String invoicedPremium = removeAmountFormat(map.get("InvoicedPremium")).toString();
				Double d = (Double.parseDouble(invoicedPremium) / 10);
				int premium = Integer.parseInt(d.toString().split("\\.")[1]);
				if (premium < 5) {
					msg = msg + "Premium: <b>"
							+ numberFormat.format((Double.parseDouble(invoicedPremium) - premium)).split("\\.")[0]
							+ "</b>&nbsp;";
				} else {
					msg = msg
							+ "Premium: <b>" + numberFormat
									.format((Double.parseDouble(invoicedPremium) + (10 - premium))).split("\\.")[0]
							+ "</b>&nbsp;";
				}

				if (i == 0) {
					msg = msg + "<br/><br/>";
				} else {
					msg = msg + "<br/><br/>";
				}
			}
		}
		msg = msg + "<br/>";
		msg = msg
				+ "If you have any questions regarding our program, please give us a call at 888-803-9898.  You can access additional information about <a href='https://www.protexureaccountants.com/protexure-policy-features/'> Protexure Accountants here.</a>"
				+ "<br/><br/>";
		msg = msg
				+ "To receive a full quotation, follow the link below and complete the application. To make the process easier, the information you provided has been pre-entered into our online system."
				+ "<br/><br/>";

		msg = msg + "<a href=" + accountUtils.getProjectUrl(ctx) + "/indicationInsured.jsp?PolicyKey="
				+ encrytedPolicyKey + "&VersionSequence=" + ctx.get("VersionSequence").toString()
				+ "&TransactionSequence=" + ctx.get("TransactionSequence").toString() + "&CoverageSequence="
				+ ctx.get("CoverageSequence").toString() + "&RatingTraceSequence="
				+ ctx.get("RatingTraceSequence").toString() + "&AccountID=" + ctx.get("AccountID").toString()
				+ "&AccountKey=" + ctx.get("AccountKey").toString() + "&AddressKey=" + ctx.get("AddressKey").toString()
				+ "&CompanyKey=" + ctx.get("CompanyKey").toString() + ">Continue to Application</a>" + "<br/><br/>";

		/*
		 * msg = msg +
		 * "If you have any questions please contact one of our experienced professional liability specialists at 888-803-9898."
		 * + "<br/><br/>";
		 */
		msg = msg + "Sincerely," + "<br/><br/>";
		msg = msg + "<b>The Protexure Team</b><br/>";
		msg = msg + "<b>888-803-9898</b><br/>";
		msg = msg + "<b>Fax: (440) 333-3214</b><br/>";
		msg = msg + "<b>www.protexureaccountants.com</b>";
		msg = msg + "</td>";
		msg = msg + "</tr>";
		msg = msg + "</table>";

		return msg;
	}

	public static boolean validateDateFieldData(String dateString) {
		boolean flag = false;
		try {
			logger.debug("going to  Validate  Date field data");
			// String str1=dateString.replace("/", "#");
			StringTokenizer st = new StringTokenizer(dateString, "/");

			int month = Integer.parseInt(st.nextToken().trim());
			int day = Integer.parseInt(st.nextToken().trim());
			int year = Integer.parseInt(st.nextToken().trim());

			int length = dateString.length();
			if (length != 10)
				flag = true;
			if (day < 1 || day > 31)
				flag = true;
			if (month < 1 || month > 12)
				flag = true;
			// As per Pat on 01/18/2018
			if (year < 1900)
				flag = true;

		} catch (Exception e) {
			logger.error("Exception occured at Validation date data", e);
			flag = true;
		}
		return flag;
	}

	public static void validateFeeSuitData(Context ctx) {
		try {
			HttpServletRequest req = (HttpServletRequest) ctx.get("DocumentRequest");
			HttpSession sess = req.getSession();
			// boolean isDateValid=true;
			String dateData = null;
			List finalList = null;
			List inputList = new ArrayList();
			if (sess.getAttribute("FeeSuitData_list_01") != null
					&& sess.getAttribute("FeeSuitData_list_01") instanceof List) {
				finalList = (List) sess.getAttribute("FeeSuitData_list_01");

				Map map = new HashMap();
				for (int i = 0; i < finalList.size(); i++) {

//						ctx.get("NamedDefendantsInSuit_" + i).equals(HtmlConstants.EMPTY) || ctx.get("NamedDefendantsInSuit_" + i) == null ||
//						ctx.get("DueDateFees_" + i).equals(HtmlConstants.EMPTY) || ctx.get("DueDateFees_" + i) == null ||
					if (ctx.get("AmountOfFeesSued_" + i).equals(HtmlConstants.EMPTY)
							|| ctx.get("AmountOfFeesSued_" + i) == null
							|| ctx.get("SuitFilesDateFees_" + i).equals(HtmlConstants.EMPTY)
							|| ctx.get("SuitFilesDateFees_" + i) == null
							|| ctx.get("IsAllegOfLegalMalPrac_" + i).equals(HtmlConstants.EMPTY)
							|| ctx.get("IsAllegOfLegalMalPrac_" + i) == null
							|| ctx.get("FeeSuitDispositionKey_" + i).equals(HtmlConstants.EMPTY)
							|| ctx.get("FeeSuitDispositionKey_" + i) == null) {
						populateError(ctx, "needFirstRowfee", "All added fee suits data need to be filled.");
					}

					if (!ctx.get("SuitFilesDateFees_" + i).equals(HtmlConstants.EMPTY)
							&& ctx.get("SuitFilesDateFees_" + i) != null) {
						dateData = ctx.get("SuitFilesDateFees_" + i).toString();
						if (validateDateFieldData(dateData))
							populateError(ctx, "needFirstRowfee", "Please enter valid date.");
					}
//					if (!ctx.get("DueDateFees_" + i).equals(HtmlConstants.EMPTY) && ctx.get("DueDateFees_" + i) != null) {
//						dateData = ctx.get("DueDateFees_" + i).toString();
//						if (validateDateFieldData(dateData))
//							populateError(ctx, "needFirstRow", "Please enter valid date.");
//					}
				}
			}
		} catch (Exception e) {
			logger.error("Error in AccountUtils.validateFeeSuitData", e);
		}
	}

	public static void validateServerTrustee(Context ctx) {
		try {
			HttpServletRequest req = (HttpServletRequest) ctx.get("DocumentRequest");
			HttpSession sess = req.getSession();
			// boolean isDateValid=true;
			String dateData = null;
			List finalList = null;
			List inputList = new ArrayList();
			if (sess.getAttribute("ServerTrusteeSupp_list_01") != null
					&& sess.getAttribute("ServerTrusteeSupp_list_01") instanceof List) {
				finalList = (List) sess.getAttribute("ServerTrusteeSupp_list_01");

				Map map = new HashMap();
				for (int i = 0; i < finalList.size(); i++) {

					if (ctx.get("NameOfTrust_" + i).equals(HtmlConstants.EMPTY) || ctx.get("NameOfTrust_" + i) == null
							|| ctx.get("TypeofTrustKey_" + i).equals(HtmlConstants.EMPTY)
							|| ctx.get("TypeofTrustKey_" + i) == null
							|| ctx.get("ValueOfAssetsTrust_" + i).equals(HtmlConstants.EMPTY)
							|| ctx.get("ValueOfAssetsTrust_" + i) == null
							|| ctx.get("IsBeneficiaryInterestTrust_" + i).equals(HtmlConstants.EMPTY)
							|| ctx.get("IsBeneficiaryInterestTrust_" + i) == null) {
						populateError(ctx, "needFirstRow", "All added trust or estate need to be filled.");
					}
				}
			}
		} catch (Exception e) {
			logger.error("Error in AccountUtils.validateServerTrustee", e);
		}
	}

	public static void validateFirmRenderedService(Context ctx) {
		try {
			HttpServletRequest req = (HttpServletRequest) ctx.get("DocumentRequest");
			HttpSession sess = req.getSession();
			// boolean isDateValid=true;
			String dateData = null;
			List finalList = null;
			List inputList = new ArrayList();
			if (sess.getAttribute("OutsideInterestSupp_list_01") != null
					&& sess.getAttribute("OutsideInterestSupp_list_01") instanceof List) {
				finalList = (List) sess.getAttribute("OutsideInterestSupp_list_01");

				Map map = new HashMap();
				for (int i = 0; i < finalList.size(); i++) {

					if (ctx.get("ClientNameOsi_" + i).equals(HtmlConstants.EMPTY)
							|| ctx.get("ClientNameOsi_" + i) == null
							|| ctx.get("EntityNameOsi_" + i).equals(HtmlConstants.EMPTY)
							|| ctx.get("EntityNameOsi_" + i) == null
							|| ctx.get("ClientIndustryOsiKey_" + i).equals(HtmlConstants.EMPTY)
							|| ctx.get("ClientIndustryOsiKey_" + i) == null
							|| ctx.get("PositionHeldOsi_" + i).equals(HtmlConstants.EMPTY)
							|| ctx.get("PositionHeldOsi_" + i) == null
							|| ctx.get("DOInsuranceOsi_" + i).equals(HtmlConstants.EMPTY)
							|| ctx.get("DOInsuranceOsi_" + i) == null
							|| ctx.get("PercentEquityInterestofOsi_" + i).equals(HtmlConstants.EMPTY)
							|| ctx.get("PercentEquityInterestofOsi_" + i) == null
							|| ctx.get("ServiceIndustryOsiKey_" + i).equals(HtmlConstants.EMPTY)
							|| ctx.get("ServiceIndustryOsiKey_" + i) == null
							|| ctx.get("IndividualListedServicesOsi_" + i).equals(HtmlConstants.EMPTY)
							|| ctx.get("IndividualListedServicesOsi_" + i) == null) {
						populateError(ctx, "needFirstRowFirmRendServ",
								"All added firm rendered services need to be filled.");
					}
				}
			}
		} catch (Exception e) {
			logger.error("Error in AccountUtils.validateFirmRenderedService", e);
		}
	}

	public static void test(IContext ctx) {
		{ if (logger.isDebugEnabled()) logger.debug("ss"); }
	}

	public static void getRBPolicyGrossRevenueYear(IContext ctx) {
		if (ctx.get("YearEndDate") == null || "".equals(ctx.get("YearEndDate"))) {
			if (ctx.get("NBYearEndDate") != null) {
				String yearEndDate = ctx.get("NBYearEndDate").toString();
				yearEndDate = yearEndDate.split("/")[0] + "/"
						+ String.valueOf((Integer.parseInt(yearEndDate.split("/")[1]) + 1));
				ctx.put("YearEndDate", yearEndDate);
			}
		}
	}

	public static void validateApplicationForcompletion(Context ctx) {
		try {
			new SetParametersForStoredProcedures().setParametersInContext(ctx, "PolicyKey,AccountKey");
			List validatePageData = (List) SqlResources.getSqlMapProcessor(ctx)
					.select("Firm.ValidateIndivisualPageCompletionACC_p", ctx);

			if (validatePageData != null && validatePageData.size() > 0)
				ctx.putAll((Map) validatePageData.get(0));
			String isNotCompletlyFilled = !ctx.get("isNotCompletlyFilled").equals(HtmlConstants.EMPTY)
					&& ctx.get("isNotCompletlyFilled") != null ? ctx.get("isNotCompletlyFilled").toString() : "";

			if ("Y".equals(isNotCompletlyFilled))
				AccountUtils.populateError(ctx, "ApplicationNotCompleted", "Please complete the application.");

		} catch (Exception e) {
			logger.error("Error in AccountUtils.validateApplicationForcompletion", e);
		}

	}

	public static void validateAOPPercentageNewApp(IContext ctx) throws Exception {

		int total = 0;
		Map map = new HashMap();
		List limtTypes = SqlResources.getSqlMapProcessor(ctx).select("AOP.findAll", ctx);

		if (limtTypes != null) {
			for (int i = 0; i < limtTypes.size(); i++) {
				map = (HashMap) limtTypes.get(i);
				String percentageValue = ctx.get("AOP_Percentage_" + i) == null ? null
						: ctx.get("AOP_Percentage_" + i).toString();
				if (percentageValue == null || "".equals(percentageValue.trim()))
					percentageValue = "0";
				try {
					total = total + Integer.parseInt(percentageValue);
				} catch (Exception e) {
					logger.error("Error in AccountUtils.validateAOPPercentageNewApp", e);
					populateError(ctx, "TotalAOP_Percentage", "Invalid data");
				}

			}
		}

		if (total < 100 || total > 100) {
			percentageError(ctx, total, "100");
		}
	}

	public static void validateAOPCommentNewApp(IContext ctx) throws Exception {
		List finalList = new ArrayList();
		if (ctx.get("aoplist_list_2") != null && ctx.get("aoplist_list_2") instanceof List) {
			finalList = (List) ctx.get("aoplist_list_2");
		}
		Context ctx2 = new Context();
		ctx2.setProject(ctx.getProject());
		ctx2.putAll(ctx);
		if (finalList != null) {
			for (int i = 0; i < finalList.size(); i++) {
				ctx2.remove("AOP_Percentage_" + i);

			}
			Map map1 = new HashMap();
			for (int i = 0; i < finalList.size(); i++) {
				map1 = (HashMap) finalList.get(i);
				ctx2.put("AOP_Percentage_" + map1.get("Aopkey"), ctx.get("AOP_Percentage_" + i));
			}
		}

		Map map = new HashMap();
		List limtTypes = SqlResources.getSqlMapProcessor(ctx).select("AOP.findAll", ctx);
		if (limtTypes != null) {
			for (int i = 0; i < limtTypes.size(); i++) {
				map = (HashMap) limtTypes.get(i);
				if (map.get("AOPKey").toString().equals("20")) {
					String percentageValue = ctx2.get("AOP_Percentage_" + map.get("AOPKey").toString()) == null ? null
							: ctx2.get("AOP_Percentage_" + map.get("AOPKey").toString()).toString();
					String comment = ctx.get("AOPCommentDesc_" + map.get("AOPKey").toString()) == null ? null
							: ctx.get("AOPCommentDesc_" + map.get("AOPKey").toString()).toString();
					if (!(percentageValue == null || "".equals(percentageValue.trim())
							|| "0".equals(percentageValue))) {
						if (comment == null || "".equals(comment.trim())) {
							String msg = "Fill in please describe";
							/*
							 * if (map.get("AOPKey").toString().equals("20")) msg =
							 * "Please fill describe the Other Services";
							 */
							populateError(ctx, "AOPCommentDesc_" + map.get("AOPKey").toString(), msg);
						}
					} else {
						if (comment != null && !"".equals(comment)) {
							ctx.put("AOPCommentDesc_" + map.get("AOPKey").toString(), "");
						}
					}
				}
			}
		}
	}

	public static void reatinAddedAops(Context ctx) {
		try {
			List inputList = new ArrayList();
			Context newCtx = new Context();
			newCtx.setProject(ctx.getProject());
			newCtx.putAll(ctx);
			try {

				List finalList = new ArrayList();
				if (ctx.get("aoplist_list_2") != null && ctx.get("aoplist_list_2") instanceof List) {
					finalList = (List) ctx.get("aoplist_list_2");
				}
				Map reqMap = new HashMap();
				Map map = null;
				;
				for (int i = 0; i < finalList.size(); i++) {
					reqMap = (HashMap) finalList.get(i);
					map = new HashMap();
					map.put("AOPKey", reqMap.get("Aopkey"));
					map.put("PercentageOld",
							("".equals(ctx.get("AOP_PercentageOld_" + i))
									|| "0".equals(ctx.get("AOP_PercentageOld_" + i))) ? null
											: ctx.get("AOP_PercentageOld_" + i));
					map.put("Percentage",
							("".equals(ctx.get("AOP_Percentage_" + i)) || "0".equals(ctx.get("AOP_Percentage_" + i)))
									? null
									: ctx.get("AOP_Percentage_" + i));
					map.put("PolicyKey", ctx.get("PolicyKey"));
					map.put("IsEdit", reqMap.get("IsEdit"));
					inputList.add(map);
				}
				newCtx.put("inputList", inputList);
				convertListDataToXML(newCtx, "inputList", "outputList");
				ctx.put("outputList", newCtx.get("outputList"));
			} catch (Exception e) {
				logger.error("Error in AccountUtils.reatinAddedAops", e);
			}

		} catch (Exception e) {
			logger.error("problem occured in retainingAop method", e);
		}
	}

	public static void validateClaimSupplement(Context ctx) {
		try {
			List claimList = SqlResources.getSqlMapProcessor(ctx)
					.select("SqlStmts.UserStatementManualBoQueriesgetProfLiabClmAgntList", ctx);
			if (claimList.size() < 1)
				AccountUtils.populateError(ctx, "ClaimSupplemnts",
						"Claims information is empty, atleast one record is required.");
		} catch (Exception e) {
			logger.error("error occured while validating claim supplement page", e);
		}
	}

	public static void validateRenewalSupplementFlow(Context ctx) {
		try {
			Object objInvoiceData = SqlResources.getSqlMapProcessor(ctx)
					.select("Firm.SupplementFlowControlRenewalAcc_p", ctx);
			if (objInvoiceData != null) {
				List finalList = (List) objInvoiceData;
				if (finalList.size() > 0) {

					Map map = new HashMap();
					for (int i = 0; i < finalList.size(); i++) {
						map = (HashMap) finalList.get(i);
						ctx.put("isInvestSuppVisible", map.get("isInvestSuppVisible"));
						ctx.put("isComputerSuppVisible", map.get("isComputerSuppVisible"));
						ctx.put("isBusinessManagementSuppVisible", map.get("isBusinessManagementSuppVisible"));
						ctx.put("isAuditSuppVisible", map.get("isAuditSuppVisible"));
					}
				}

			}
			// AOP.getAopsValues(ctx);

		} catch (Exception e) {
			logger.error("Error in AccountUtils.validateRenewalSupplementFlow", e);
		}
	}

	public static void validateAopPercentageManually(Context ctx) {
		HttpServletRequest req = (HttpServletRequest) ctx.get("DocumentRequest");
		HttpSession sess = req.getSession();
		int total = 0, percentage = 0;
		try {
			int policyStatusKey = ctx.get("PolicyStatusKey") != null
					&& !ctx.get("PolicyStatusKey").equals(HtmlConstants.EMPTY)
							? Integer.parseInt(ctx.get("PolicyStatusKey").toString())
							: 0;
			if (policyStatusKey == 2) {
				if (ctx.get("IsAOPChange") == null)
					populateError(ctx, "IsAOPChange", "Please select Yes or No");
			}
			if (ctx.get(Constants.INET_ERRORS_LIST) == null) {
				// List limtTypes = SqlResources.getSqlMapProcessor(ctx).select("AOP.findAll",
				// ctx);

				List finalList = new ArrayList();
				if (sess.getAttribute("aoplist_list_2") != null && sess.getAttribute("aoplist_list_2") instanceof List)
					finalList = (List) sess.getAttribute("aoplist_list_2");

				Map map = new HashMap();
				for (int i = 0; i < finalList.size(); i++) {
					Context ctx1 = new Context();
					ctx1.setProject(ctx.getProject());
					map = (HashMap) finalList.get(i);
					ctx1.put("PolicyKey", ctx.get("PolicyKey"));
					ctx1.put("AOPkey", map.get("Aopkey"));
					ctx1.put("percentage", ctx.get("AOP_Percentage_" + i));
					// SqlResources.getSqlMapProcessor(ctx1).update("SqlStmts.UserStatementManualBoQueriesupdateSmartSearchValue",
					// ctx1);
					percentage = ctx.get("AOP_Percentage_" + i) != null
							&& !ctx.get("AOP_Percentage_" + i).equals(HtmlConstants.EMPTY)
									? Integer.parseInt(ctx.get("AOP_Percentage_" + i).toString())
									: 0;
					;
					total = total + percentage;
				}
				if (total > 100 || total < 100) {
					populateError(ctx, "totalPercentageError",
							"The total AOP percentage is required to equal 100%, current total is " + total + "%");
				}
			}

		} catch (Exception e) {
			logger.error("Error in AccountUtils.validateAopPercentageManually", e);
		}
	}

	public static void cleanPagesData(Context ctx) {
		try {
			String currentPageName = ctx.get("inet_page") != null && !ctx.get("inet_page").equals(HtmlConstants.EMPTY)
					? ctx.get("inet_page").toString()
					: "";
			if (currentPageName.equals("practiceManagement") || currentPageName.equals("aboutFirm"))
				SqlResources.getSqlMapProcessor(ctx).update("Firm.CleanPracticeManagementDataAcc_p", ctx);
			if (currentPageName.equals("morePracticeManagement"))
				SqlResources.getSqlMapProcessor(ctx).update("Firm.CleanMorePracticeManagementDataAcc_p", ctx);
			if (currentPageName.equals("trustsAndOfficer"))
				SqlResources.getSqlMapProcessor(ctx).update("Firm.CleanTrustOfficerPageDataAcc_p", ctx);
			if (currentPageName.equals("pastClaims"))
				SqlResources.getSqlMapProcessor(ctx).update("Firm.CleanPastClaimPageDataAcc_p", ctx);
			if (currentPageName.equals("practiceManagementSupp"))
				SqlResources.getSqlMapProcessor(ctx).update("Firm.CleanPracticeManagementSuppPageDataAcc_p", ctx);

		} catch (Exception e) {
			logger.error("Error in AccountUtils.cleanPagesData", e);
		}
	}

	public static void checkFinalisedQuote(IContext ctx) throws Exception {

		Object obj = RuleUtils.executeRule(ctx, "AccountantRule.isNotcheckedQuoteSelectedFlag");

		boolean flag = false;
		if (obj instanceof Boolean)
			flag = (Boolean) obj;
		if (flag) {
			AccountUtils.populateError(ctx, "FinalisedQuote", "please select a quote option below");

		}
	}

	public static int getPageNumber(String filePath, String strToCheck) throws IOException {
		File file = null;

		try {
			file = new File(filePath);
			PDDocument pddoc = PDDocument.load(file);
			for (int pageNumber = 1; pageNumber <= pddoc.getNumberOfPages(); pageNumber++) {
				PDFTextStripper s = new PDFTextStripper();
				s.setStartPage(pageNumber);
				s.setEndPage(pageNumber);
				String contents = s.getText(pddoc);
				if (contents.contains(strToCheck)) {
					return pageNumber;
				}
			}

		} catch (Exception e) {
			logger.error("Error in AccountUtils.getPageNumber", e);
		} finally {
			/* code by sukhi 26/09/2018 */
			file = null;
		}

		return -1;
	}

	public static Map<String, Double> getCoordinateXY(String filePath, String strToCheck, int pageNo)
			throws IOException {
		return new PrintTextLocations().printSubwords(filePath, strToCheck, pageNo);
	}

	public static Object roundCeiling(Object arg1, Object places) {
		if (arg1 == null)
			return 0;

		String formatter = "1";
		int plVal = 0;
		double factor = 0.0;
		if (places != null)
			plVal = Integer.parseInt(places.toString());

		if (plVal == 0)
			return round(arg1);
		else {
			for (int i = 0; i < plVal; i++) {
				formatter = formatter + "0";
			}
			formatter = formatter + ".0";
			factor = Double.parseDouble(formatter);
			return java.lang.Math.round(Double.parseDouble(arg1.toString()) * factor) / factor;
		}
	}

	public static Object round(Object arg1) {
		if (arg1 == null)
			return 0;

		return java.lang.Math.round(Double.parseDouble(arg1.toString()));
	}

	public static Object round(Object arg1, int places) {
		if (arg1 == null)
			return 0;

		BigDecimal bd = new BigDecimal(Double.parseDouble(arg1.toString()));
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}

	public static void populateAccountsList(Context ctx) {
		boolean flag = false;
		try {
			if ((ctx.get("MergeStateCode") != null && !"".equals(ctx.get("MergeStateCode").toString()))
					|| (ctx.get("PolicyEffectiveMonthYear") != null
							&& !"".equals(ctx.get("PolicyEffectiveMonthYear").toString()))) {
				if (ctx.get("AccountNameSearch") != null && !"".equals(ctx.get("AccountNameSearch").toString()))
					flag = true;
				if (ctx.get("AccountEmailSearch") != null && !"".equals(ctx.get("AccountEmailSearch").toString()))
					flag = true;
				if (ctx.get("AccountNumberSearch") != null && !"".equals(ctx.get("AccountNumberSearch").toString()))
					flag = true;
				if (ctx.get("WorkPhoneSearch") != null && !"".equals(ctx.get("WorkPhoneSearch").toString()))
					flag = true;

				if (flag != true) {
					populateError(ctx, "noMandetoryFiletr",
							"State or Effective date can't be used independently, one more additional filter is required");
					return;
				}

			}
			flag = false;
			if (ctx.get("AccountNameSearch") != null && !"".equals(ctx.get("AccountNameSearch").toString()))
				flag = true;
			if (ctx.get("AccountEmailSearch") != null && !"".equals(ctx.get("AccountEmailSearch").toString()))
				flag = true;
			if (ctx.get("AccountNumberSearch") != null && !"".equals(ctx.get("AccountNumberSearch").toString()))
				flag = true;
			if (ctx.get("WorkPhoneSearch") != null && !"".equals(ctx.get("WorkPhoneSearch").toString()))
				flag = true;
			if (ctx.get("MergeStateCode") != null && !"".equals(ctx.get("MergeStateCode").toString()))
				flag = true;
			if (ctx.get("PolicyEffectiveMonthYear") != null
					&& !"".equals(ctx.get("PolicyEffectiveMonthYear").toString()))
				flag = true;
			if (flag == true) {
				new SetParametersForStoredProcedures().setParametersInContext(ctx,
						"AccountNameSearch,AccountNumberSearch,AccountEmailSearch,PolicyEffectiveMonthYear,WorkPhoneSearch,MergeStateCode");
				List policyAccountsList = (List) SqlResources.getSqlMapProcessor(ctx)
						.select("Policy.AccountManagementSearchACC_p", ctx);
				ctx.put("SearchlistSize", policyAccountsList.size());
				if (policyAccountsList.size() > 0) {
					ctx.put("AccountsSearch_list_01", policyAccountsList);
				}
			}
		} catch (Exception e) {
			logger.error("error occured while population Accounts list data " + e, e);
		}
	}

	public static void validateAccountManagement(Context ctx) {
		boolean flag = false, flag1 = false;
		try {
			String accountIds = ctx.get("strPolicyKey") == null ? "" : ctx.get("strPolicyKey").toString().trim();
			String accountActivity = ctx.get("accountActivity") == null ? ""
					: ctx.get("accountActivity").toString().trim();
			String methodType = ctx.get("inet_method") != null && !ctx.get("inet_method").equals(HtmlConstants.EMPTY)
					? ctx.get("inet_method").toString()
					: "back";
			if ("search".equalsIgnoreCase(methodType)) {

				if (ctx.get("AccountNameSearch") != null && !"".equals(ctx.get("AccountNameSearch").toString()))
					flag = true;
				if (ctx.get("AccountEmailSearch") != null && !"".equals(ctx.get("AccountEmailSearch").toString()))
					flag = true;
				if (ctx.get("AccountNumberSearch") != null && !"".equals(ctx.get("AccountNumberSearch").toString()))
					flag = true;
				if (ctx.get("WorkPhoneSearch") != null && !"".equals(ctx.get("WorkPhoneSearch").toString()))
					flag = true;
				if (ctx.get("PolicyEffectiveMonthYear") != null
						&& !"".equals(ctx.get("PolicyEffectiveMonthYear").toString()))
					flag1 = true;
				if (ctx.get("MergeStateCode") != null && !"".equals(ctx.get("MergeStateCode").toString()))
					flag1 = true;

				if (flag != true && flag1 != true) {
					populateError(ctx, "noMandetoryFiletr", "You need to select one of the filter.");
					return;
				}
				if (flag != true && flag1 == true) {
					populateError(ctx, "noMandetoryFiletr",
							"State or Effective date can't be used independently, additonal to these one more filter required.");
					return;
				}

			}
			if ("save".equalsIgnoreCase(methodType)) {
				if (accountIds.equals("")) {
					populateError(ctx, "noSelecion", "No account has selected for Link/Delink activity.");
					return;
				}
				if ("linkAccounts".equalsIgnoreCase(accountActivity) && accountIds.indexOf(',') < 0) {
					populateError(ctx, "singalAccountActivity", "You need to select other account to perform linking.");
					return;
				}
				if (accountActivity.equals("")) {
					populateError(ctx, "noAccountActivity",
							"Please choose type of activity you need to perform with selected accounts.");
					return;
				}

			}

		} catch (Exception e) {
			logger.error("error occured while validating Account management data", e);
		}

	}

	public static void displayEndorsementOptions(Context ctx) {
		logger.debug("loading Endorsement options");
		try {
			new SetParametersForStoredProcedures().setParametersInContext(ctx, "PolicyKey");
			List endorsementOptionsList = (List) SqlResources.getSqlMapProcessor(ctx)
					.select("Firm.EndorsementOptionsDisplayACC_p", ctx);
			if (endorsementOptionsList != null && endorsementOptionsList.size() > 0)
				ctx.putAll((Map) endorsementOptionsList.get(0));

		} catch (Exception e) {
			logger.error("Error occured while displaying Endorsement Options", e);
		}
	}

	public static void setFormChangesConditions(Context ctx) {
		logger.error("setting up fom changes conditions");
		try {
			Object obj = RuleUtils.executeRule(ctx, "AccountantRule.isEligibleLevelOneFormChanges");
			boolean flag = true;
			if (obj != null && obj instanceof Boolean)
				flag = (Boolean) obj;

			if (flag) {
				ctx.put("isNewFormChanges", "Y");
			} else
				ctx.put("isNewFormChanges", "N");

		} catch (Exception e) {
			logger.error("exception occured while validating fom changes conditions", e);

		}
	}

	public static String premiumAmountFormator(double premiumAmount) {
		try {
			Double currencyAmount = new Double(premiumAmount);

			// Get current locale information
			Locale currentLocale = Locale.getDefault();

			// Get currency instance from locale; This will have all currency related
			// information
			Currency currentCurrency = Currency.getInstance(currentLocale);

			// Currency Formatter specific to locale
			NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(currentLocale);

			// Test the output
			/*
			 * System.out.println(currentLocale.getDisplayName());
			 * 
			 * System.out.println(currentCurrency.getDisplayName());
			 * 
			 * System.out.println(currencyFormatter.format(currencyAmount));
			 */
			String result = currencyFormatter.format(currencyAmount);
			return result.substring(0, result.indexOf('.'));
		} catch (Exception e) {
			logger.error("Exception ocured while formationg premium", e);
			return new Double(premiumAmount).toString();
		}
	}

	public static int getDaysOfYear() {
		Calendar cal = Calendar.getInstance();
		return cal.getActualMaximum(Calendar.DAY_OF_YEAR);
		////// System.out.println("Maximum month:" + a);
	}

	public static void validateStatesEligibleForQQ(Context ctx) {
		boolean flag = false;
		try {

			Object obj = RuleUtils.executeRule(ctx, "AccountantRule.stopCompletePolicyForStates");
			if (obj != null && obj instanceof Boolean) {
				flag = (Boolean) obj;

				if (flag)
					populateError(ctx, "stateEligibilityError",
							"At present we are not writing policies for this state");

			}

		} catch (Exception e) {
			logger.error("Exception ocured while validateStatesEligibleForQQ premium", e);
		}
	}

	public static void validateQuoteAddition(Context ctx) {
		logger.debug("validating quotes for duplicacy.");
		try {
			if (ctx.get("inet_method").equals("saveQuote") || ctx.get("inet_method").equals("addIndication")) {
				new SetParametersForStoredProcedures().setParametersInContext(ctx,
						"PolicyKey,LimitSequence,DeductibleSequence,CoverageKey,IsClaimExpensesType,IsDollarDefense,IsClaimOptionType");
				List validateQuoteData = (List) SqlResources.getSqlMapProcessor(ctx)
						.select("Quote.ValidateQuoteAdditionACC_p", ctx);
				if (validateQuoteData != null && validateQuoteData.size() > 0)
					ctx.putAll((Map) validateQuoteData.get(0));
				// saveQuote
				String duplicateQuote = ctx.get("duplicateQuote") != null ? ctx.get("duplicateQuote").toString() : "N";
				if ("Y".equals(duplicateQuote)) {
					populateError(ctx, "duplicateQuoteError",
							"Requested quote already exist. Please select different options.");
				}
			}

		} catch (Exception e) {
			logger.error("Error ocured while validating quotes for duplicacy. " + e, e);

		}

	}

	// IPFS , CC and ACH

	public static void isPaymentDetailExist(IContext ctx) {
		List paymentDetailList = null;
		try {
			paymentDetailList = SqlResources.getSqlMapProcessor(ctx)
					.select("SqlStmts.UserStatementManualBoQueriesgetPaymentDetail", ctx);
			if (paymentDetailList != null && paymentDetailList.size() > 0) {
				Map map = (Map) paymentDetailList.get(0);
				ctx.put("PaymentDetailId", map.get("PaymentDetailId"));
				ctx.put("TransactionSequence", map.get("TransactionSequence"));
				ctx.put("isPaymentDetailExist", "Y");
			} else {
				ctx.remove("isPaymentDetailExist");
			}
			ctx.put("TransactionDate", ctx.get("LastUpdateTimeStamp"));

		} catch (Exception e) {
			logger.error("Error in AccountUtils.isPaymentDetailExist", e);
		}
	}

	public static void processFinanceData(IContext ctx) {
		try {
			if (ctx.get("IPFSFinanceQurMon") != null && "IPFSFinanceQuarterly".equals(ctx.get("IPFSFinanceQurMon"))) {
				ctx.put("FinanceInterval", "Quarterly");// Q
			} else {
				ctx.put("FinanceInterval", "Monthly");// M
			}

			if (ctx.get("IPFSFinanceCCACHMC") != null && "IPFSFinanceCCACH".equals(ctx.get("IPFSFinanceCCACHMC"))) {
				ctx.put("FinanceDownPayment", "EFT");
			} else if (ctx.get("IPFSFinanceCCACHMC") != null
					&& "IPFSFinanceManualCheck".equals(ctx.get("IPFSFinanceCCACHMC"))) {
				ctx.put("FinanceDownPayment", "ManualCheck");
			}
			ctx.put("Amount", ctx.get("PremiumAmount"));
			ctx.put("TransactionStatus", "Pending");

			logger.debug("Payment Detail has been updated by FinanceInterval and FinanceDownPayment...");

		} catch (Exception e) {
			logger.error("Error in AccountUtils.processFinanceData", e);
		}
	}

	public static void generateReportForRequote(Context ctx) {
		try {

			HttpServletResponse response = (HttpServletResponse) ctx.get("DocumentResponse");

			String excelname = "RequoteList.xls";
			String filePath = SystemProperties.getInstance()
					.getString("appl." + ctx.getProject() + ".ratingexceldownloadpath");
			filePath = filePath + excelname;
			String reportHeaders = ctx.get("ReportHeaders").toString();
			ArrayList<String> headerlist = new ArrayList<String>(Arrays.asList(reportHeaders.split(",")));
			String[] sheetHeaders = new String[headerlist.size()];
			for (int i = 0; i < sheetHeaders.length; i++)
				sheetHeaders[i] = headerlist.get(i);

			/*
			 * String requestXml=
			 * "<request><requestdata><inet_project_id>LawyersIns</inet_project_id><filterCriteria><data><PolicyEffectiveDateFrom>06/01/2018</PolicyEffectiveDateFrom><SubProducerNumber></SubProducerNumber><SubProduced>N</SubProduced><Limit>100000/300000</Limit><StateCode>AL</StateCode><PolicyType>NB</PolicyType><PolicyEffectiveDateTo>12/18/2018</PolicyEffectiveDateTo></data></filterCriteria></requestdata></request>";
			 * ctx.put("inputXml", requestXml);
			 */
			logger.debug("Going to execute the stored procedure BatchRatingForLawyers_Proc");
			new SetParametersForStoredProcedures().setParametersInContext(ctx,
					"EffectiveDateFrom,EffectiveDateTo,stateCodes,aopCodes,revenueCount,reQuoteURL,requoteTransactionType");
			List list = (List) SqlResources.getSqlMapProcessor(ctx).select("Firm.RequoteReportDataACC_p", ctx);
			logger.debug("BatchRatingForLawyers_Proc has been executed");
			{ if (logger.isDebugEnabled()) logger.debug(list.size()); }

			generateReportSheet(ctx, "Data", list, excelname, sheetHeaders);

			AccountsBatch.FormatExcel(filePath);

		} catch (Exception e) {
			logger.error("Error in AccountUtils.generateReportForRequote", e);

		}
	}

	public static void generateReportSheet(Context ctx, String sheetName, List list, String excelname,
			String[] sheetHeaders) throws Exception {

		String path = SystemProperties.getInstance().getString("appl." + ctx.getProject() + ".ratingexceldownloadpath");

		path = path + excelname;
		String path1 = (String) ctx.get(path);
		FileInputStream input = null;
		Workbook workbook = null;
		int rows; // No of rows
		String value = "";
		Map<String, String> valuesMap = null;
		List headerList = new ArrayList();
		File file = null;
		FileOutputStream fileOut = null;
		Row row;
		// XSSFRow row;
		Cell cell = null;
		Sheet sheet;
		String key = "";
		String val = "";

		try {

			fileOut = new FileOutputStream(new File(path));
			workbook = new HSSFWorkbook();
			sheet = workbook.createSheet(sheetName);
			// headers.clear();
			if (list == null)
				return;

			CellStyle style = workbook.createCellStyle();
			Font font = workbook.createFont();
			font.setBoldweight(Font.BOLDWEIGHT_BOLD);
			style.setFont(font);

			if (list.size() >= 1) {
				Row headerRow = sheet.createRow(0);
				for (int j = 0; j < sheetHeaders.length; j++) {
					cell = headerRow.createCell(j);
					cell.setCellValue(sheetHeaders[j]);
					cell.setCellStyle(style);
					sheet.autoSizeColumn(j);
				}
			}

			int rowNum = 1;
			for (int j = 0; j < list.size(); j++) {
				row = sheet.createRow(rowNum++);
				Map valueMap = (Map) list.get(j);
				// System.out.println("PolicyKey :"+valueMap.get("PolicyKey"));
				RatingBatch.callingRatingEngine(valueMap);
				for (int k = 0; k < sheetHeaders.length; k++) {

					key = sheetHeaders[k];
					if (valueMap.get(key) != null)
						val = valueMap.get(key).toString();
					else
						val = "";
					row.createCell(k).setCellValue(val);
				}
				sheet.autoSizeColumn(j);

			}

			// input.close();
			file = new File(path);
			if (file.exists())
				file.delete();

			// fileOut = new FileOutputStream(path);
			workbook.write(fileOut);
			fileOut.close();

		} catch (Exception e) {
			logger.error("Problem in writing in file " + path + " " + e.getMessage(), e);
		} finally {
			/* code by sukhi 26/09/2018 */
			if (input != null) {
				input.close();
				input = null;
			}
			if (fileOut != null) {
				fileOut.close();
				fileOut = null;
			}
			file = null;
		}

	}

	public static void decrytPolicyKeyFromDB(Context context) {
		try {
			Map obj = (Map) SqlResources.getSqlMapProcessor(context).findByKey(
					"SqlStmts.UserStatementManualBoQueriesgetDecryptedPolicyKeyAndEffectiveDateFromDB", context);
			if (obj != null && obj instanceof Map) {
				context.put("PolicyKey", obj.get("decryptedPolicyKey"));
				context.put("requotePolicyEffectiveDate", obj.get("decryptedEffectiveDate"));
			}
		} catch (Exception e) {
			logger.error("Error in AccountUtils.decrytPolicyKeyFromDB", e);
		}

	}

	public static void insertDataForCopiedPolicy(Context ctx) throws Exception {

		boolean oldFlow = false, newFlow = false;
		Object obj = null;
		{ if (logger.isDebugEnabled()) logger.debug("Copied policy----" + ctx.get("CopyPolicyData_list")); }

		obj = ctx.get("CopyPolicyData_list");

		if (obj != null) {
			List list = (List) obj;

			for (int i = 0; i < list.size(); i++) {
				Map map = (Map) list.get(i);

				ctx.put("PolicyKey", map.get("PolicyKey"));
				ctx.put("VersionSequence", map.get("VersionSequence"));
				ctx.put("TransactionSequence", map.get("TransactionSequence"));
				ctx.put("CoverageSequence", map.get("CoverageSequence"));
				ctx.put("AccountKey", map.get("AccountKey"));
				ctx.put("AddressKey", map.get("AddressKey"));
			}
		}

		Object objPolicy = SqlResources.getSqlMapProcessor(ctx).findByKey("Policy.findByKey", ctx);
		if (objPolicy != null)
			ctx.putAll((Map) objPolicy);

	}

	public static void validateCoverageModifiersForURFlow(Context ctx) {
		try {
			Object obj = SqlResources.getSqlMapProcessor(ctx)
					.findByKey("SqlStmts.UserStatementManualBoQueriesgetCocerageModifiers", ctx);
			if (obj != null && obj instanceof Map) {
				ctx.putAll((Map) obj);
			}
			int existingModifier = ctx.get("ExistingModifier") != null
					&& !ctx.get("ExistingModifier").equals(HtmlConstants.EMPTY)
							? Integer.parseInt(ctx.get("ExistingModifier").toString())
							: 0;
			int SchduleRatingModifier1 = ctx.get("SchduleRatingModifier1") != null
					&& !ctx.get("SchduleRatingModifier1").equals(HtmlConstants.EMPTY)
							? Integer.parseInt(ctx.get("SchduleRatingModifier1").toString())
							: 0;
			int SchduleRatingModifier2 = ctx.get("SchduleRatingModifier2") != null
					&& !ctx.get("SchduleRatingModifier2").equals(HtmlConstants.EMPTY)
							? Integer.parseInt(ctx.get("SchduleRatingModifier2").toString())
							: 0;
			int SchduleRatingModifier3 = ctx.get("SchduleRatingModifier3") != null
					&& !ctx.get("SchduleRatingModifier3").equals(HtmlConstants.EMPTY)
							? Integer.parseInt(ctx.get("SchduleRatingModifier3").toString())
							: 0;
			int SchduleRatingModifier4 = ctx.get("SchduleRatingModifier4") != null
					&& !ctx.get("SchduleRatingModifier4").equals(HtmlConstants.EMPTY)
							? Integer.parseInt(ctx.get("SchduleRatingModifier4").toString())
							: 0;
			int SchduleRatingModifier5 = ctx.get("SchduleRatingModifier5") != null
					&& !ctx.get("SchduleRatingModifier5").equals(HtmlConstants.EMPTY)
							? Integer.parseInt(ctx.get("SchduleRatingModifier5").toString())
							: 0;
			int SchduleRatingModifier6 = ctx.get("SchduleRatingModifier6") != null
					&& !ctx.get("SchduleRatingModifier6").equals(HtmlConstants.EMPTY)
							? Integer.parseInt(ctx.get("SchduleRatingModifier6").toString())
							: 0;
			int totalModifiers = SchduleRatingModifier1 + SchduleRatingModifier2 + SchduleRatingModifier3
					+ SchduleRatingModifier4 + SchduleRatingModifier5 + SchduleRatingModifier6;
			if (totalModifiers > existingModifier)
				ctx.put("resetRatingFlag", "N");
			else
				ctx.put("resetRatingFlag", "Y");
		} catch (Exception e) {
			logger.error("Exception occured in validateCoverageModifiersForURFlow method", e);
		}
	}

	public static void updateLoginDetalis(IContext ctx) {
		try {
			SqlResources.getSqlMapProcessor(ctx).insert("UserSessionLogin.insert", ctx);
		} catch (Exception e) {
			logger.error("Exception occured while updateLoginDetalis", e);
		}
	}

	public static void fetchDataForRLIEmail(Context ctx) {

		try {

			List Firmlist = SqlResources.getSqlMapProcessor(ctx)
					.select("SqlStmts.UserStatementdroolQueriesgetFirmDetails", ctx);
			if (Firmlist != null)
				ctx.putAll((Map) Firmlist.get(0));

			List firmGrossRevenueList = SqlResources.getSqlMapProcessor(ctx)
					.select("SqlStmts.UserStatementdroolQueriesgetGrossRevenueForFirmRenew", ctx);
			if (firmGrossRevenueList != null)
				ctx.putAll((Map) firmGrossRevenueList.get(0));

			firmGrossRevenueList = SqlResources.getSqlMapProcessor(ctx)
					.select("SqlStmts.UserStatementdroolQueriesgetProfessionalLiabilityInsDetail", ctx);
			if (firmGrossRevenueList != null)
				ctx.putAll((Map) firmGrossRevenueList.get(0));

			String htmlDir = SystemProperties.getInstance().getString("html.basedir");

			Context newCtx = new Context();
			newCtx.setProject(ctx.getProject());

			newCtx.put("PolicyKey", ctx.get("PolicyKey"));

			// going to get event description from database
			newCtx.put("StateCode", ctx.get("StateCode"));
			Object obj = SqlResources.getSqlMapProcessor(newCtx).select("Firm.GetCybercoverageEmailDataACC_p", newCtx);
			if (obj != null && obj instanceof List) {
				List list = (List) obj;
				for (int i = 0; i < list.size(); i++) {
					Map map = (Map) list.get(i);

					if (i > 0) {
						ctx.put(HtmlConstants.EMAIL_NOTIFICATION_AGENCY_FROMEMAIL_ADDRESS,
								ctx.get(HtmlConstants.EMAIL_NOTIFICATION_AGENCY_FROMEMAIL_ADDRESS + "_" + i));
						ctx.put(HtmlConstants.EMAIL_NOTIFICATION_AGENCY_TOEMAIL_ADDRESS,
								ctx.get(HtmlConstants.EMAIL_NOTIFICATION_AGENCY_TOEMAIL_ADDRESS + "_" + i));
						ctx.put(HtmlConstants.EMAIL_NOTIFICATION_AGENCY_CCEMAIL_ADDRESS,
								ctx.get(HtmlConstants.EMAIL_NOTIFICATION_AGENCY_CCEMAIL_ADDRESS + "_" + i));
					}
//		    			map.put("to_role_desc", dstId);
//		    			map.put("cc_role_desc", SystemProperties.getInstance().getString("mail.admin.cc.address"));

					sendEmailNotification(ctx, map);
				}
			}
		} catch (Exception e) {
			logger.error("Unable to send email notification due to error : " + e.getMessage(), e);
		}

	}

	public static void sendErrorEmail(Context ctx, String eventName) {
		Map map = null;
		try {
			ctx.put("event_name", eventName);
			Object obj = SqlResources.getSqlMapProcessor(ctx)
					.findByKey("SqlStmts.UserStatementManualBoQueriesgetEmailNotificationData", ctx);
			if (obj != null && obj instanceof Map)
				map = (Map) obj;
			if (map == null)
				return;
			String subject = map.get("subject") != null ? map.get("subject").toString() : "EMPTY";
			String body = map.get("body") != null ? map.get("body").toString() : "EMPTY";
			String to_role_desc = SystemProperties.getInstance().getString("appl.errorEmail.toAddress");
			String cc_role_desc = SystemProperties.getInstance().getString("appl.errorEmail.ccAddress");
			ctx.put("isSendMailTrue", "Y");
			// SubproducerMailer.sendEmailAsSubProducer(ctx);
			sendEmailNotification(ctx, map);

		} catch (Exception e) {
			logger.error("Error in AccountUtils.sendErrorEmail", e);
		}
	}

	public static String uploadFile(Context ctx, String uploaddirectory) throws Exception {
		String filePath = null;
		FileItem uploadFile_logo = null;
		File tempFile1 = null;

		try {
			List fileItems = (List) ctx.get("fileItems");
			Iterator i = fileItems.iterator();

			while (i.hasNext()) {
				FileItem fi = (FileItem) i.next();
				if (!fi.isFormField()) {
					// filename on the client
					if ("file_path".equals(fi.getFieldName())) {
						filePath = fi.getName();
						uploadFile_logo = fi;
						ctx.put("ReportName", filePath);
					}
				}
			}
			{ if (logger.isDebugEnabled()) logger.debug(filePath); }
		} catch (Exception e) {
			logger.error("Error in file process", e);
		}

		filePath = filePath + "_" + generateUniqueId();

		tempFile1 = new File(uploaddirectory);
		if (!tempFile1.exists()) {
			tempFile1.mkdir();
		}
		try {
			uploadFile_logo.write(new File(uploaddirectory, filePath));
		} catch (Exception e) {
			logger.error("Error in AccountUtils.uploadFile", e);
			populateError(ctx, "DocUploadError", "Document could not be write to file");

		}
		return filePath;
	}

	public static void uploadClaimSummaryReport(Context ctx) throws Exception {
		logger.debug("going to upload claims report");

		String uploaddirectory = SystemProperties.getInstance().getString("fileupload.uploaddirectory");

		String filePath = uploadFile(ctx, uploaddirectory);

		try {
			readExcelFile(ctx, new File(uploaddirectory + filePath));
		} catch (Exception e) {
			logger.error("Got exception while uploading report", e);
		}
	}

	public static void readExcelFile(Context ctx, File file) {
		try {
			POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(file));
			HSSFWorkbook wb = new HSSFWorkbook(fs);
			HSSFSheet sheet = wb.getSheetAt(0);
			HSSFRow row;
			HSSFCell cell;
			List inputList = new ArrayList();

			int rows; // No of rows
			rows = sheet.getPhysicalNumberOfRows();

			int cols = 0; // No of columns
			int tmp = 0;
			boolean flag = false;

			// This trick ensures that we get the data properly even if it doesn't start
			// from first few rows
			for (int i = 0; i < 10 || i < rows; i++) {
				row = sheet.getRow(i);
				if (row != null) {
					tmp = sheet.getRow(i).getPhysicalNumberOfCells();
					if (tmp > cols)
						cols = tmp;
				}
			}
			List<String> columnsList = new ArrayList();
			for (int r = 0; r < 1; r++) {
				row = sheet.getRow(r);
				boolean result = checkIfRowIsEmpty(row);
				if (!result) {
					for (int c = 0; c < cols; c++) {
						cell = row.getCell((short) c);
						if (cell != null) {
							if ("AccDescPart3Text".equals((cell + "").replaceAll("[^a-zA-Z0-9]", ""))) {
								ctx.put("ClaimReportDescriptionColumn", "AccDescPart3Text");
								flag = true;
							}
							columnsList.add((cell + "").replaceAll("[^a-zA-Z0-9]", ""));
						}
					}
				}
			}
			String[] columnsDescription = new String[columnsList.size()];
			columnsDescription = columnsList.toArray(columnsDescription);

			for (int r = 1; r < rows; r++) {
				row = sheet.getRow(r);
				boolean result = checkIfRowIsEmpty(row);
				if (!result) {
				    Map map = new HashMap();
				    String companyCode = "";
		            for(int c = 0; c < cols; c++) {
		                cell = row.getCell((short)c);
		                if(cell != null) {
		                	if("CompanyCode".equals(columnsDescription[c])){
		                		companyCode = cell + "";
		                		map.put(columnsDescription[c], removeSpecialSymbolsFromExcel(cell));
		                	} else if("PolicyNumber".equals(columnsDescription[c])){
		                		if("CNF".equals(companyCode)) {
		                			map.put(columnsDescription[c], (cell.toString().length() > 9) ? cell.toString().replace(".", "").substring(0, 9) : cell.toString());
		                		} else if("ISMIE".equals(companyCode)) {
			                		map.put(columnsDescription[c], removeSpecialSymbolsFromExcel(cell));
		                		}
		                	} else if(("ClaimNumber".equals(columnsDescription[c]) || "AccDescPart3Text".equals(columnsDescription[c])) && flag){
		                		map.put(columnsDescription[c], removeSpecialSymbolsFromExcel(cell));
		                	} else if(flag == false){
		                		map.put(columnsDescription[c], removeSpecialSymbolsFromExcel(cell));
		                	}
		                }
		            }
					inputList.add(map);
		        }
			}

			if (inputList.size() > 0) {
				ctx.put("inputList", inputList);
				convertListDataToXML(ctx, "inputList", "outputList");
			}
		} catch (Exception ioe) {
			logger.error("Error in AccountUtils.readExcelFile", ioe);
		}
	}

	public static boolean checkIfRowIsEmpty(Row row) {
		if (row == null) {
			return true;
		}
		if (row.getLastCellNum() <= 0) {
			return true;
		}
		for (int cellNum = row.getFirstCellNum(); cellNum < row.getLastCellNum(); cellNum++) {
			Cell cell = row.getCell(cellNum);
			if (cell != null && cell.getCellType() != Cell.CELL_TYPE_BLANK && StringUtils.isNotBlank(cell.toString())) {
				return false;
			}
		}
		return true;
	}

	public static String generateUniqueId() {
		// System.out.println("ASDADASDASSADASD");
		String uniqueId = "";
		try {
			uniqueId = new Timestamp(new Date().getTime()).toString().replaceAll("[.,:\\ ]", "");
			// System.out.println("uniqueId" + uniqueId);
		} catch (Exception e) {
			logger.error("Error in AccountUtils.generateUniqueId", e);
			uniqueId = "Not Found";
		}
		return uniqueId;
	}

	public static Object removeSpecialSymbolsFromExcel(Object obj) {
		obj = obj.toString().replace("'", "''");
		return obj;
	}

	public static Object removePercentageFormat(Object arg1) {
		if (arg1 == null || arg1.equals(HtmlConstants.EMPTY))
			return 0.0;

		String data = arg1.toString();
		if (data.contains("%"))
			data = data.substring(0, data.length() - 1);

		return data;
	}

	public void populateAllCoverageModifiers(IContext ctx) throws Exception {

		List modifiersList = SqlResources.getSqlMapProcessor(ctx)
				.select("SqlStmts.UserStatementsaveAccountstmtspopulateCoverageModifiers", ctx);
		ctx.remove("CoverageModifiers_freeform_01");

		Map covMap = new HashMap();
		if (modifiersList != null) {
			Map map = new HashMap();
			for (int i = 0; i < modifiersList.size(); i++) {
				map = (HashMap) modifiersList.get(i);
				covMap.put("SchduleRatingModifier" + map.get("ModifierKey"),
						map.get("Percentage") != null && "0".equals(map.get("Percentage").toString()) ? ""
								: map.get("Percentage"));
			}
		}

		ctx.put("SchduleRatingModifier1", covMap.get("SchduleRatingModifier1"));
		ctx.put("SchduleRatingModifier2", covMap.get("SchduleRatingModifier2"));
		ctx.put("SchduleRatingModifier3", covMap.get("SchduleRatingModifier3"));
		ctx.put("SchduleRatingModifier4", covMap.get("SchduleRatingModifier4"));
		ctx.put("SchduleRatingModifier5", covMap.get("SchduleRatingModifier5"));
		ctx.put("SchduleRatingModifier6", covMap.get("SchduleRatingModifier6"));

	}

	public static void validateQuoteLimitIncrease(Context ctx) {

		try {
			if ("saveQuote".equals(ctx.get("inet_method").toString())) {
				List dataList = (List) SqlResources.getSqlMapProcessor(ctx).select("Firm.ValidateQuoteLimitIncrease_p",
						ctx);

				if (dataList != null && dataList.size() > 0)
					ctx.putAll((Map) dataList.get(0));
				String limitIncreaseFlag = !ctx.get("limitIncreaseFlag").equals(HtmlConstants.EMPTY)
						&& ctx.get("limitIncreaseFlag") != null ? ctx.get("limitIncreaseFlag").toString() : "";
				if ("Y".equals(limitIncreaseFlag)) {
					populateError(ctx, "limitIncreaseFlagError", " C & F is not allowing any increase in limit.");
				}
			}
		} catch (Exception e) {
			logger.error("exception occurred while validating imit increase" + e, e);
		}

	}

	public static void getBrokerPolicyDetails(Context ctx) {
		try {
			boolean flag = false;
			Object brokerageDataUpdate = RuleUtils.executeRule(ctx, "AccountantRule.isBrokerageDataUpdate");
			if (brokerageDataUpdate != null && brokerageDataUpdate instanceof Boolean) {
				flag = (Boolean) brokerageDataUpdate;
			}

			if (flag) {
				Context newCtx = new Context();
				newCtx.setProject(ctx.getProject());
				newCtx.putAll(ctx);
				String accountName = ctx.get("AccountName") != null
						&& !ctx.get("AccountName").equals(HtmlConstants.EMPTY) ? ctx.get("AccountName").toString()
								: "N";
				accountName = accountName.replace("'", "''");
				newCtx.put("AccountName", accountName);

				if (ctx.get("Address") != null) {
					String address = ctx.get("Address").toString();
					address = address.replace("'", "''");
					newCtx.put("Address", address);
				}
				if (ctx.get("Address2") != null) {
					String address2 = ctx.get("Address2").toString();
					address2 = address2.replace("'", "''");
					newCtx.put("Address2", address2);
				}
				new SetParametersForStoredProcedures().setParametersInContext(newCtx,
						"CarrierKey,BrokerPolicyEffectiveDate,BrokerPolicyExpirationDate,AccountName,Address,State,City,Zip,Phone,Email,LOBKey,BrokerKey,Commission,ExpiringPolicyNumber,StatusKey,Premium,PremiumTax,AccountID,TransactionTypeKey,Fees,Address2");
				List brokeragePolicyDataList = (List) SqlResources.getSqlMapProcessor(newCtx)
						.select("BrokeragePolicy.GetBrokeragePolicyData_p", newCtx);

				if (brokeragePolicyDataList != null && brokeragePolicyDataList.size() > 0)
					ctx.putAll((Map) brokeragePolicyDataList.get(0));
			}
			String brokerageDtaUpdate = ctx.get("BrokerageDataUpdate") != null
					&& !ctx.get("BrokerageDataUpdate").equals(HtmlConstants.EMPTY)
							? ctx.get("BrokerageDataUpdate").toString()
							: "N";
			if (!"Y".equals(brokerageDtaUpdate)) {
				if (ctx.get("PolicyKey") != null && ctx.get("PolicyKey").equals(HtmlConstants.EMPTY))
					ctx.put("PolicyKey", null);
				if (ctx.get("BrokeragePolicyKey") != null && ctx.get("BrokeragePolicyKey").equals(HtmlConstants.EMPTY))
					ctx.put("BrokeragePolicyKey", null);
				if (ctx.get("TransactionTypeKey") != null && ctx.get("TransactionTypeKey").equals(HtmlConstants.EMPTY))
					ctx.put("TransactionTypeKey", null);
			} else {
				ctx.put("RenewAsBrokerage", "N");
			}

			if (ctx.get("BrokeragePolicyKey") != null && ctx.get("PolicyKey") == null)
				ctx.put("isNewBrokerPolicy", "N");
			new SetParametersForStoredProcedures().setParametersInContext(ctx,
					"PolicyKey,BrokeragePolicyKey,isNewBrokerPolicy,TransactionTypeKey,RenewAsBrokerage,MarketableProductKey,LastUpdateUserID");
			List claimSuppData = (List) SqlResources.getSqlMapProcessor(ctx)
					.select("BrokeragePolicy.GetBrokerPolicyDetailsLW_p", ctx);
			if (claimSuppData != null && claimSuppData.size() > 0)
				ctx.putAll((Map) claimSuppData.get(0));

		} catch (Exception e) {
			logger.error("Error occured while fetching details of broker poliy", e);
		}
	}

	public static Object removeAmountFormatAccounts(Object arg1) {
		if (arg1 == null || arg1.equals(HtmlConstants.EMPTY))
			return 0.0;

		String amount = arg1.toString();
		amount = amount.replace("$", "").replace(",", "");
		if (amount.contains("%"))
			amount = amount.substring(0, amount.length() - 1);

		return amount;
	}

	public static void validateBrokeragePolicy(Context ctx) {
		boolean flag = false;
		try {
			String comingFrom = ctx.get("comingFrom") != null && !ctx.get("comingFrom").equals(HtmlConstants.EMPTY)
					? ctx.get("comingFrom").toString()
					: "Other";
			Object obj = RuleUtils.executeRule(ctx, "AccountantRule.isInetErrorListEmpty");
			if (obj instanceof Boolean)
				flag = (Boolean) obj;

			if ("addEditBrokeragePolicy".equals(comingFrom) && flag == true)
				ctx.put("isShowDocUpload", "Y");

		} catch (Exception e) {
			logger.error("Error in AccountUtils.validateBrokeragePolicy", e);
		}
	}

	public static void saveUpdateBrokeragePolicyDetails(Context ctx) {
		try {
			logger.debug("going to save brokerage policy data");
			Context newCtx = new Context();
			newCtx.setProject(ctx.getProject());
			newCtx.putAll(ctx);
			String accountName = ctx.get("AccountName") != null && !ctx.get("AccountName").equals(HtmlConstants.EMPTY)
					? ctx.get("AccountName").toString()
					: "N";
			accountName = accountName.replace("'", "''");
			newCtx.put("AccountName", accountName);

			if (ctx.get("Address") != null) {
				String address = ctx.get("Address").toString();
				address = address.replace("'", "''");
				newCtx.put("Address", address);
			}
			if (ctx.get("Address2") != null) {
				String address2 = ctx.get("Address2").toString();
				address2 = address2.replace("'", "''");
				newCtx.put("Address2", address2);
			}
			new SetParametersForStoredProcedures().setParametersInContext(newCtx,
					"BrokeragePolicyKey,CarrierKey,BrokerPolicyEffectiveDate,AccountName,Address,State,City,Zip,Phone,Email,LOBKey,haveProfessionalLiabilityInsurance,PLEffectiveDate,PLExpirationDate,PLPolicyNumber,ContractComments,ExpiringCarrier,BrokerKey,Commission,ExpiringPolicyNumber,StatusKey,Premium,PremiumTax,LastUpdateUserID,LastUpdateTimeStamp,BrokeredPolicyNumber,BrokerPolicyExpirationDate,AccountID,ExpiringPremium,ExpiringPolicyEffectiveDate,ExpiringPolicyExpirationDate,TransactionTypeKey,Fees,TotalCalculatedPremium,BrokeragePaymentType,Address,RenewAsBrokerage,PolicyKey,isConvertedPolicy,ProducerCode,LimitSequence,DeductibleSequence,IsClaimExpensesType,IsClaimOptionType,IsDollarDefense");
			SqlResources.getSqlMapProcessor(newCtx).update("BrokeragePolicy.BrokeragePolicyDetailsLW_p", newCtx);

		} catch (Exception e) {
			logger.error("Error occured while saving brokerage policy data", e);
		}
	}

	public static void getPolicyData(Context ctx) {
		logger.debug("goinng to set companycode as per user role");
		try {

			List getPolicyData = SqlResources.getSqlMapProcessor(ctx)
					.select("SqlStmts.UserStatementManualBoQueriesgetPolicyUpdatedData", ctx);
			ctx.putAll((Map) getPolicyData.get(0));

		} catch (Exception e) {
			logger.error("Error in AccountUtils.getPolicyData", e);
		}

	}

	private static final String COMPANY_LOOKUP_CACHE_KEY = "GetCompanyListACC_p_CacheKey";
	private static final String COMPANY_LOOKUP_CACHE_RESULT = "GetCompanyListACC_p_CacheResult";

	public static void setCompanyForPolicy(Context ctx) throws Exception {
		Object policyEffectiveDate = ctx.get("PolicyEffectiveDate");
		if (!isValidPolicyEffectiveDate(policyEffectiveDate))
			return;

		try {
			String normalizedPolicyEffectiveDate = policyEffectiveDate instanceof Date
					? new SimpleDateFormat("MM/dd/yyyy").format((Date) policyEffectiveDate)
					: policyEffectiveDate.toString().trim();
			String lookupKey = companyLookupKey(ctx, normalizedPolicyEffectiveDate);
			Object cachedResult = ctx.get(COMPANY_LOOKUP_CACHE_RESULT);
			if (lookupKey.equals(ctx.get(COMPANY_LOOKUP_CACHE_KEY)) && cachedResult instanceof Map) {
				ctx.putAll((Map) cachedResult);
				return;
			}

			ctx.put("PolicyEffectiveDate", normalizedPolicyEffectiveDate);

			new SetParametersForStoredProcedures().setParametersInContext(ctx,
					"StateCode,MarketableProductKey,PolicyEffectiveDate");
			Object obj = SqlResources.getSqlMapProcessor(ctx).findByKey("Policy.GetCompanyListACC_p", ctx);
			if (obj != null && obj instanceof Map) {
				Map objMap = (Map) obj;
				ctx.putAll(objMap);
				// ponytail: policy-context cache assumes StateCompany mappings stay fixed during the policy session.
				ctx.put(COMPANY_LOOKUP_CACHE_RESULT, new HashMap(objMap));
				ctx.put(COMPANY_LOOKUP_CACHE_KEY, lookupKey);
			}
		} catch (Exception e) {
			logger.error("error occured while settong up company :" + e, e);
		} finally {
			ctx.put("PolicyEffectiveDate", policyEffectiveDate);
		}
	}

	static String companyLookupKey(Context ctx, String policyEffectiveDate) {
		return String.valueOf(ctx.get("StateCode")) + "|" + String.valueOf(ctx.get("MarketableProductKey")) + "|"
				+ policyEffectiveDate;
	}

	static boolean isValidPolicyEffectiveDate(Object value) {
		if (value instanceof Date)
			return true;
		if (value == null || value.toString().trim().isEmpty())
			return false;
		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
		format.setLenient(false);
		String date = value.toString().trim();
		try {
			return date.equals(format.format(format.parse(date)));
		} catch (Exception e) {
			logger.error("Error in AccountUtils.isValidPolicyEffectiveDate", e);
			return false;
		}
	}

	public static void executeUnderwritingRules(Context ctx) {

		try {
			new SetParametersForStoredProcedures().setParametersInContext(ctx,
					"PolicyKey,AccountKey,AccountID,AddressKey,VersionSequence,PolicyStatusKey,LastUpdateUserID");
			SqlResources.getSqlMapProcessor(ctx).update("rulepolicytransaction.UWRules_AboutYou_ACC", ctx);
		} catch (Exception e) {
			logger.error("Error in AccountUtils.executeUnderwritingRules", e);
		}

		try {
			SqlResources.getSqlMapProcessor(ctx).update("rulepolicytransaction.UWRules_InsuranceHistory_ACC", ctx);
		} catch (Exception e) {
			logger.error("Error in AccountUtils.executeUnderwritingRules", e);
		}
		try {
			SqlResources.getSqlMapProcessor(ctx).update("rulepolicytransaction.UWRules_FirmInfo_ACC", ctx);
		} catch (Exception e) {
			logger.error("Error in AccountUtils.executeUnderwritingRules", e);
		}
		try {
			SqlResources.getSqlMapProcessor(ctx).update("rulepolicytransaction.UWRules_LawPractice_ACC", ctx);
		} catch (Exception e) {
			logger.error("Error in AccountUtils.executeUnderwritingRules", e);
		}

		try {
			SqlResources.getSqlMapProcessor(ctx).update("rulepolicytransaction.UWRules_PracticeManagement_ACC", ctx);
		} catch (Exception e) {
			logger.error("Error in AccountUtils.executeUnderwritingRules", e);
		}

		try {
			SqlResources.getSqlMapProcessor(ctx).update("rulepolicytransaction.UWRules_PracticemanagementSupp_ACC",
					ctx);
		} catch (Exception e) {
			logger.error("Error in AccountUtils.executeUnderwritingRules", e);
		}
		try {
			SqlResources.getSqlMapProcessor(ctx).update("rulepolicytransaction.UWRules_InvestmentFinancialSupp_ACC",
					ctx);
		} catch (Exception e) {
			logger.error("Error in AccountUtils.executeUnderwritingRules", e);
		}
		try {
			SqlResources.getSqlMapProcessor(ctx).update("rulepolicytransaction.UWRules_AuditSupp_ACC", ctx);
		} catch (Exception e) {
			logger.error("Error in AccountUtils.executeUnderwritingRules", e);
		}
		try {
			SqlResources.getSqlMapProcessor(ctx).update("rulepolicytransaction.UWRules_ComputerRelatedSupp_ACC", ctx);
		} catch (Exception e) {
			logger.error("Error in AccountUtils.executeUnderwritingRules", e);
		}
		try {
			SqlResources.getSqlMapProcessor(ctx).update("rulepolicytransaction.UWRules_BusinessManagementSupp_ACC",
					ctx);
		} catch (Exception e) {
			logger.error("Error in AccountUtils.executeUnderwritingRules", e);
		}
		try {
			SqlResources.getSqlMapProcessor(ctx).update("rulepolicytransaction.UWRules_QuoteOptions_ACC", ctx);
		} catch (Exception e) {
			logger.error("Error in AccountUtils.executeUnderwritingRules", e);
		}

	}

	public static boolean validatePolicyIssuanceForCompany(Context ctx) {
		boolean result = false;
		int statusKey = 0;
		String IsQuickQuote = "";
		try {
			IsQuickQuote = ctx.get("IsQuickQuote") != null ? ctx.get("IsQuickQuote").toString() : "NA";
			if (!"Y".equals(IsQuickQuote))
				statusKey = ctx.get("StatusKey") != null ? Integer.parseInt(ctx.get("StatusKey").toString()) : 0;
			if ("Y".equals(IsQuickQuote))
				statusKey = 11;
			// statusKey=ctx.get("StatusKey")!=null?
			// Integer.parseInt(ctx.get("StatusKey").toString()):0;
			if (statusKey == 9 || statusKey == 10 || statusKey == 3 || statusKey == 7 || statusKey == 11) {
				setCompanyForPolicy(ctx);
				if ("3".equals(String.valueOf(ctx.get("CompanyKey"))))
					result = true;
			}
		} catch (Exception e) {
			logger.error("error occured while settong up company :" + e, e);
		}
		return result;
	}

	public static synchronized String generatePolicyNumberISMIE(IContext ctx) throws Exception {

		String prefix = "AAA-04", suffix = "", newPolicyNumber = "";
		Map map = null;
		;
		String initialPolicyNo = null;
		int comapnyAccountCount = 0, policyStatusKey = 0, oldSuffix = 0;

		Context newCtx = new Context();
		newCtx.setProject(ctx.getProject());
		newCtx.put("PolicyKey", ctx.get("PolicyKey"));
		newCtx.put("MarketableProductKey", ctx.get("MarketableProductKey"));
		new SetParametersForStoredProcedures().setParametersInContext(newCtx, "PolicyKey,MarketableProductKey");
		List dataList = (List) SqlResources.getSqlMapProcessor(newCtx).select("Policy.GetDataForPolicyNumber_p",
				newCtx);
		if (dataList != null && dataList.size() > 0)
			map = (Map) dataList.get(0);
		if (map == null)
			throw new IllegalStateException("Policy data is required to generate a policy number");
		policyStatusKey = map.get("policyStatusKey") != null && !map.get("policyStatusKey").equals(HtmlConstants.EMPTY)
				? Integer.parseInt(map.get("policyStatusKey").toString())
				: 0;
		comapnyAccountCount = map.get("comapnyAccountCount") != null
				&& !map.get("comapnyAccountCount").equals(HtmlConstants.EMPTY)
						? Integer.parseInt(map.get("comapnyAccountCount").toString())
						: 0;

		if (policyStatusKey == 1 || (policyStatusKey == 2 && comapnyAccountCount == 0)) {

			if (map.get("PolicyNumber") != null) {
				String PolicyNumber = map.get("PolicyNumber").toString();
				{ if (logger.isDebugEnabled()) logger.debug(
						"LAST MAX POLICY NUMBER:-" + PolicyNumber + " LAST UPDATED AT" + System.currentTimeMillis()); }
				int index = PolicyNumber.indexOf("-");
				int second = PolicyNumber.indexOf("-", index + 1);
				int third = PolicyNumber.indexOf("-", second + 1);
				int policyNo = Integer.parseInt(PolicyNumber.substring(second + 1, third)) + 1;
				initialPolicyNo = new Integer(policyNo).toString();
				int limit = 6;
				int initialPolicyNoLength = initialPolicyNo.length();
				for (int i = 0; i < limit - initialPolicyNoLength; i++)
					initialPolicyNo = "0" + initialPolicyNo;
				newPolicyNumber = prefix + "-" + initialPolicyNo + "-01";

			}
		} else {
			oldSuffix=map.get("OldSuffix")!=null && !map.get("OldSuffix").equals(HtmlConstants.EMPTY) ?Integer.parseInt(map.get("OldSuffix").toString()):0;
			oldSuffix=oldSuffix+1;
			suffix=new Integer(oldSuffix).toString();
			if(suffix.length()==1)
				suffix="0"+suffix;
			newPolicyNumber=map.get("LastIssuedPoliyNumberForAccountWithoutSuffix").toString()+"-"+suffix;
			newCtx.put("newPolicyNumber",newPolicyNumber);
			if(validateIfPolicynumberExists(newCtx))
			{		
				oldSuffix=oldSuffix+1;
				suffix=new Integer(oldSuffix).toString();
				if(suffix.length()==1)
					suffix="0"+suffix;
				newPolicyNumber=map.get("LastIssuedPoliyNumberForAccountWithoutSuffix").toString()+"-"+suffix;	
			}	
		}

		{ if (logger.isDebugEnabled()) logger.debug(
				"NEWLY GENERATED POLICY NUMBER:-" + newPolicyNumber + " LAST UPDATED AT" + System.currentTimeMillis()); }
		return newPolicyNumber;
	}

/////------------------------ ZOHO ------------------------

	public static void insertZOHORecords(Context ctx) {
		try {
			getZohoToken(ctx);
			String zohoAccountID = insertZOHORecordsOfAccounts(ctx);
			if (zohoAccountID != null && !"".equals(zohoAccountID) && !zohoAccountID.isEmpty()) {
				insertZOHORecordsOfPotentials(ctx, zohoAccountID);
			}
		} catch (Exception e) {
			logger.error("Error in AccountUtils.insertZOHORecords", e);
		}
	}

	public static void updateZOHORecords(Context ctx) {
		try {
			getZohoToken(ctx);
			String zohoAccountID = insertZOHORecordsOfAccounts(ctx);
			if (zohoAccountID != null && !"".equals(zohoAccountID) && !zohoAccountID.isEmpty()) {
				insertZOHORecordsOfPotentials(ctx, zohoAccountID);
			}
		} catch (Exception e) {
			logger.error("Error in AccountUtils.updateZOHORecords", e);
		}
	}

	public static void updateZohoToken(IContext ctx) {
		try {
			int status = SqlResources.getSqlMapProcessor(ctx)
					.update("SqlStmts.UserStatementManualBoQueriesupdateZohoAccessToken", ctx);
			{ if (logger.isDebugEnabled()) logger.debug(status); }
		} catch (Exception e) {
			logger.error("Exception occured while updateZohoToken", e);
		}
	}

	public static void getZohoToken(IContext ctx) {
		try {
			if (ctx.get("isZohoAccessToken") == null || "".equals(ctx.get("isZohoAccessToken"))) {
				String getZohoAccessToken = "";
				List getZohoAccessTokenList = (List) SqlResources.getSqlMapProcessor(ctx)
						.select("SqlStmts.UserStatementManualBoQueriesgetZohoAccessToken", ctx);
				if (getZohoAccessTokenList != null && getZohoAccessTokenList.size() > 0
						&& !getZohoAccessTokenList.isEmpty()) {
					Map getZohoAccessTokenMap = (Map) getZohoAccessTokenList.get(0);
					getZohoAccessToken = (getZohoAccessTokenMap.get("ZohoAccessToken") == null ? ""
							: getZohoAccessTokenMap.get("ZohoAccessToken").toString());
				}
				ctx.put("isZohoAccessToken", getZohoAccessToken);
			}
		} catch (Exception e) {
			logger.error("Exception occured while updateZohoToken", e);
		}
	}

	public static JSONObject getZOHORecordOfPotentials(Context ctx, String[] matchColumns, String[] matchColumnValues,
			String zohoAccountID) {
		GetMethod get = null;
		try {

			String serachTargetURL = SystemProperties.getInstance().getString("appl.AccountantIns.zoho.modulesurl")
					+ "/Potentials/search?";
			// https://crm.zoho.com/crm/private/xml/Potentials/searchRecords?
			// https://www.zohoapis.com/crm/v2/Potentials/search?
			// https://www.zohoapis.com/crm/v2/Leads/search?
			String authtoken = "Zoho-oauthtoken " + ctx.get("isZohoAccessToken");// If you don't have a authtoken please
																					// refer this wiki
																					// https://zohocrmapi.wiki.zoho.com/using-authtoken.html
			String selectColumns = "Account_Name,Deal_Name,Owner,Closing_Date,Stage,Premium,Policy,Expiring_Premium,Carrier";
			String criteria = "(";
			for (int criteriaCount = 0; criteriaCount < matchColumns.length; criteriaCount++) {

				if ("Account_Name".equals(matchColumns[criteriaCount])
						&& ctx.get(matchColumnValues[criteriaCount]) != null
						&& !"".equals(ctx.get(matchColumnValues[criteriaCount]))) {
					criteria += "(" + matchColumns[criteriaCount] + ".id:equals:" + zohoAccountID + ")";
				}
				// if("Account_Name".equals(matchColumns[criteriaCount]) &&
				// ctx.get(matchColumnValues[criteriaCount]) != null &&
				// !"".equals(ctx.get(matchColumnValues[criteriaCount]))){
				// criteria += "((" + matchColumns[criteriaCount] + ":equals:" +
				// URLEncoder.encode(ctx.get(matchColumnValues[criteriaCount]).toString(),
				// "UTF-8") + ")";
				// criteria += "AND(" + matchColumns[criteriaCount] + ".id:equals:" +
				// zohoAccountID + "))";
				// }
				if ("Deal_Name".equals(matchColumns[criteriaCount]) && ctx.get(matchColumnValues[criteriaCount]) != null
						&& !"".equals(ctx.get(matchColumnValues[criteriaCount]))) {
					criteria += "AND(" + matchColumns[criteriaCount] + ":equals:"
							+ ctx.get(matchColumnValues[criteriaCount]).toString() + ")";
				}
			}
			criteria += ")";

			// Authorization: Zoho-oauthtoken d92d401c803988c5cb849d0b4215f52"
			// ----------------------------Fetch ZOHO Data ----------------------
			serachTargetURL += "fields=" + selectColumns + "&criteria=" + criteria;
			get = new GetMethod(serachTargetURL);
			get.setRequestHeader("Authorization", authtoken);

			HttpClient httpclient = SharedHttpClient.get();
			int result = httpclient.executeMethod(get);
			String responseBody = get.getResponseBodyAsString();

			{ if (logger.isDebugEnabled()) logger.debug(result); }
			{ if (logger.isDebugEnabled()) logger.debug("status     " + responseBody); }

			if (responseBody != null && !"".equals(responseBody)) {
				return new JSONObject(responseBody);
			}
		} catch (Exception e) {
			logger.error("Error in AccountUtils.getZOHORecordOfPotentials", e);
		}
		return null;
	}

	public static void insertZOHORecordsOfPotentials(Context ctx, String zohoAccountID) {
		try {
			Map<String,String> ownerMap = new HashMap();
////    		ownerMap.put("Abbey Kenney", "203455000026483009");
////    		ownerMap.put("Alex Pendzinski", "203455000065574005");
//    		ownerMap.put("Amber Weiss", "203455000125787001");
//    		ownerMap.put("Andrea Lakatos", "203455000210815001");
////    		ownerMap.put("Brett Walker", "203455000006109001");
////    		ownerMap.put("Brian Vierk", "203455000021508463");
////    		ownerMap.put("Clayton Todd", "203455000063136023");
////    		ownerMap.put("Cloud Bakers", "203455000063357085");
//    		ownerMap.put("Curtis Barndollar", "203455000025471105");
//    		ownerMap.put("Dane Keelan", "203455000147603001");
////    		ownerMap.put("Danielle Peterson", "203455000022242025");
////    		ownerMap.put("Dean Kostopoulos", "203455000100117001");
//    		ownerMap.put("Emma Sepke", "203455000020490353");
//    		ownerMap.put("Fran Moreno", "203455000000166011");
////    		ownerMap.put("Gabe", "203455000006109007");
////    		ownerMap.put("GWeiss", "203455000006109007");
//    		ownerMap.put("HubSpot  Sync", "203455000115995118");
////    		ownerMap.put("J Meyer Meyer", "203455000006899216");
////    		ownerMap.put("J Meyer", "203455000006899216");
////    		ownerMap.put("Jennifer Seay", "203455000041792003");
//    		ownerMap.put("Jessica Wolf", "203455000028699009");
////    		ownerMap.put("Jody Montalto", "203455000026628013");
////    		ownerMap.put("Kandace Dickeson", "203455000070024001");
//    		ownerMap.put("Katie Long", "203455000167715001");
////    		ownerMap.put("Karly Wells", "203455000046971004");
//    		ownerMap.put("Lorelei Lorenzen", "203455000000112003");
////    		ownerMap.put("Lori Michi", "203455000023364001");
//    		ownerMap.put("Natalie Long", "203455000133617001");
////    		ownerMap.put("Nicohle Johnson", "203455000175798001");
//    		ownerMap.put("Outline Systems", "203455000031179005");
//    		ownerMap.put("Pat Cosulich", "203455000000033001");
//    		ownerMap.put("Quiana Bennett", "203455000173489001");
//    		ownerMap.put("Ryan Schwartz", "203455000000166005");
//    		ownerMap.put("Sales Archive", "203455000125034001");
//    		ownerMap.put("Sara Lang", "203455000008114038");
////    		ownerMap.put("Terry Odekirk", "203455000000268538");
////    		ownerMap.put("Tonya Wentz", "203455000013177421");
			
			List getZohoUserDetails = (List)SqlResources.getSqlMapProcessor(ctx).select("SqlStmts.UserStatementManualBoQueriesgetZohoUserDetails", ctx);
			if(getZohoUserDetails != null && getZohoUserDetails.size() > 0 && !getZohoUserDetails.isEmpty()){
				for (int zohoUserDetailsCount = 0; zohoUserDetailsCount < getZohoUserDetails.size(); zohoUserDetailsCount++) {
					Map getZohoUserDetailsList = (Map)getZohoUserDetails.get(zohoUserDetailsCount);
					ownerMap.put(getZohoUserDetailsList.get("AgentName").toString(), getZohoUserDetailsList.get("ZohoUserID").toString());
				}
			}
			
			String previousPolicyKey = "";
			String previousVersionSequence = "";
			List getZohoPreviousPolicyKey = (List) SqlResources.getSqlMapProcessor(ctx)
					.select("SqlStmts.UserStatementdroolQueriesgetPreviousPolicyKey", ctx);
			if (getZohoPreviousPolicyKey != null && getZohoPreviousPolicyKey.size() > 0
					&& !getZohoPreviousPolicyKey.isEmpty()) {
				Map getZohoPreviousPolicyKeyList = (Map) getZohoPreviousPolicyKey.get(0);
				previousPolicyKey = (getZohoPreviousPolicyKeyList.get("PreviousPolicykey") == null ? ""
						: getZohoPreviousPolicyKeyList.get("PreviousPolicykey").toString());
				previousVersionSequence = (getZohoPreviousPolicyKeyList.get("previousVersionSequence") == null ? ""
						: getZohoPreviousPolicyKeyList.get("previousVersionSequence").toString());
			}

			String zohoPotentialsID = "";
			List getZohoPotentialsID = (List) SqlResources.getSqlMapProcessor(ctx)
					.select("SqlStmts.UserStatementManualBoQueriesgetZohoAccountID", ctx);
			if (getZohoPotentialsID != null && getZohoPotentialsID.size() > 0 && !getZohoPotentialsID.isEmpty()) {
				Map getZohoPotentialsIDList = (Map) getZohoPotentialsID.get(0);
				zohoPotentialsID = (getZohoPotentialsIDList.get("ZohoPotentialsID") == null ? ""
						: getZohoPotentialsIDList.get("ZohoPotentialsID").toString());
			}

			String statusDesc = "";
			String stateCode = "";
			String accountName = "";
			List getPolicyStatus = (List) SqlResources.getSqlMapProcessor(ctx)
					.select("SqlStmts.UserStatementManualBoQueriesgetZohoStatus", ctx);
			if (getPolicyStatus != null && getPolicyStatus.size() > 0 && !getPolicyStatus.isEmpty()) {
				Map getPolicyStatusList = (Map) getPolicyStatus.get(0);
				stateCode = (getPolicyStatusList.get("StateCode") == null ? ""
						: getPolicyStatusList.get("StateCode").toString());
				statusDesc = (getPolicyStatusList.get("StatusDesc") == null ? ""
						: getPolicyStatusList.get("StatusDesc").toString());
				accountName = (getPolicyStatusList.get("AccountName") == null ? ""
						: getPolicyStatusList.get("AccountName").toString());
			}

			if ("New".equals(statusDesc)) { // && "Y".equals(ctx.get("IsQuickQuote"))
				statusDesc = "Indicated";
			}
			if ("UnderReview".equals(statusDesc) || "UW Review".equals(statusDesc)
					|| "UW Completed".equals(statusDesc)) {
				statusDesc = "Under Review";
			}
			if ("Indication".equals(statusDesc)) {
				statusDesc = "Indicated";
			}
			if ("Declined".equals(statusDesc)) {
				statusDesc = "Declined/Non-Renewed";
			}

			String totalPremium = "";
			ctx.put("PolicyKey", Integer.parseInt(ctx.get("PolicyKey").toString()));
			List getPolicyTotalPremium = (List) SqlResources.getSqlMapProcessor(ctx).select("Quote.GetZohoPremiumLW_p",
					ctx);
			if (getPolicyTotalPremium != null && getPolicyTotalPremium.size() > 0 && !getPolicyTotalPremium.isEmpty()) {
				Map getPolicyTotalPremiumList = (Map) getPolicyTotalPremium.get(0);
				totalPremium = (getPolicyTotalPremiumList.get("InvoicedPremium") == null ? ""
						: getPolicyTotalPremiumList.get("InvoicedPremium").toString());
			}
			ctx.put("PolicyKey", String.valueOf(ctx.get("PolicyKey")));

			String firstReviewer = "";
			List getZohoFirstReviewer = (List) SqlResources.getSqlMapProcessor(ctx)
					.select("SqlStmts.UserStatementManualBoQueriesgetZohoFirstReviewer", ctx);
			if (getZohoFirstReviewer != null && getZohoFirstReviewer.size() > 0 && !getZohoFirstReviewer.isEmpty()) {
				Map getFirstReviewerList = (Map) getZohoFirstReviewer.get(0);
				firstReviewer = (getFirstReviewerList.get("AgentName") == null ? ""
						: getFirstReviewerList.get("AgentName").toString().trim());
			}

			String expiringPremium = "";
			String carrier = "";
			String priorActDatePross = "";
			List getZohoPriorActDatePross = (List) SqlResources.getSqlMapProcessor(ctx)
					.select("SqlStmts.UserStatementManualBoQueriesgetZohoPriorActDateProssDetails", ctx);
			if (getZohoPriorActDatePross != null && getZohoPriorActDatePross.size() > 0
					&& !getZohoPriorActDatePross.isEmpty()) {
				Map getPriorActDateProssList = (Map) getZohoPriorActDatePross.get(0);
				priorActDatePross = (getPriorActDateProssList.get("PriorActDatePross") == null ? ""
						: getPriorActDateProssList.get("PriorActDatePross").toString());
				expiringPremium = (getPriorActDateProssList.get("ProInsPremium") == null ? ""
						: getPriorActDateProssList.get("ProInsPremium").toString());
				carrier = (getPriorActDateProssList.get("InsuranceCompanyNamePross") == null ? ""
						: getPriorActDateProssList.get("InsuranceCompanyNamePross").toString());
			}

			String policyStatusKey = "";
			String policyEffectiveDate = "";
			List getPolicyEffectiveDate = (List) SqlResources.getSqlMapProcessor(ctx)
					.select("SqlStmts.UserStatementManualBoQueriesgetZohoPolicyEffectiveDate", ctx);
			if (getPolicyEffectiveDate != null && getPolicyEffectiveDate.size() > 0
					&& !getPolicyEffectiveDate.isEmpty()) {
				Map getPolicyEffectiveDateList = (Map) getPolicyEffectiveDate.get(0);
				policyEffectiveDate = (getPolicyEffectiveDateList.get("PolicyEffectiveDate") == null ? ""
						: getPolicyEffectiveDateList.get("PolicyEffectiveDate").toString());
				policyStatusKey = (getPolicyEffectiveDateList.get("PolicyStatusKey") == null ? ""
						: getPolicyEffectiveDateList.get("PolicyStatusKey").toString());
			}

			String subProducerName = "";
    		String producerCode = "";
			List getSubProducerNameList = (List) SqlResources.getSqlMapProcessor(ctx)
					.select("SqlStmts.UserStatementManualBoQueriesgetZohoDealSubProducerName", ctx);
			if (getSubProducerNameList != null && getSubProducerNameList.size() > 0
					&& !getSubProducerNameList.isEmpty()) {
				Map getSubProducerNameMap = (Map) getSubProducerNameList.get(0);
				subProducerName = (getSubProducerNameMap.get("ProducerCompanyName") == null ? ""
						: getSubProducerNameMap.get("ProducerCompanyName").toString());
				producerCode = (getSubProducerNameMap.get("ProducerCode") == null ? "" : getSubProducerNameMap.get("ProducerCode").toString());
			}

			String[] matchColumns = { "Account_Name", "Deal_Name" };
			String[] matchColumnValues = { "Account_Name", "Deal_Name" };

			String keys = "Account_Name,Deal_Name,Owner,Closing_Date,Stage,Premium,Policy,Expiring_Premium,Carrier,id";
			String values = "Account_Name,Deal_Name,Owner,Closing_Date,Stage,Premium,Policy,Expiring_Premium,Carrier,id";

			if ("2".equals(policyStatusKey)) {
				keys = "Account_Name,Deal_Name,Owner,Closing_Date,Stage,Premium,Policy,Expiring_Premium,id";
				values = "Account_Name,Deal_Name,Owner,Closing_Date,Stage,Premium,Policy,Expiring_Premium,id";

				Context ctx1 = new Context();
				ctx1.putAll(ctx);

				ctx1.put("PolicyKey", Integer.parseInt(previousPolicyKey));
				List getPolicyExpiringPremium = (List) SqlResources.getSqlMapProcessor(ctx1)
						.select("Quote.GetZohoPremiumLW_p", ctx1);
				if (getPolicyExpiringPremium != null && getPolicyExpiringPremium.size() > 0
						&& !getPolicyExpiringPremium.isEmpty()) {
					Map getPolicyExpiringPremiumList = (Map) getPolicyExpiringPremium.get(0);
					expiringPremium = (getPolicyExpiringPremiumList.get("InvoicedPremium") == null ? ""
							: getPolicyExpiringPremiumList.get("InvoicedPremium").toString());
				}
			}

			if("".equals(subProducerName)){ // For Direct
				if(firstReviewer == null || "".equals(firstReviewer) || ownerMap.get(firstReviewer) == null) {
		    		if("2".equals(policyStatusKey)){
	
						String forDirectFRRS = SystemProperties.getInstance().getString("appl.AccountantIns.zoho.fordirectrenew.firstreviewer.RS");
//						String forDirectFRNN = SystemProperties.getInstance().getString("appl.AccountantIns.zoho.fordirectrenew.firstreviewer.NN");
						String forDirectFRJF = SystemProperties.getInstance().getString("appl.AccountantIns.zoho.fordirectrenew.firstreviewer.JF");
						String forDirectFRAL = SystemProperties.getInstance().getString("appl.AccountantIns.zoho.fordirectrenew.firstreviewer.AL");
	
						if("CO#IL#NM".contains(stateCode)){
		    				firstReviewer = forDirectFRRS;//Ryan Schwartz
		    			} else if("CT#DE#DC#FL#GA#IN#MD#MA#ME#MI#NE#NH#NJ#NY#NC#OH#PA#RI#VT#WV".contains(stateCode)){
		    				firstReviewer = forDirectFRJF;//Jack Fink
		    			} else if("AL#AZ#AR#CA#HI#ID#IA#KS#KY#LA#MN#MS#MO#MT#NV#ND#OK#OR#SC#SD#TN#TX#UT#VT#WA#WI#WY".contains(stateCode)){
		    				firstReviewer = forDirectFRAL;//Andrea Lakatos
		    			}
		    		} else {
//						String forDirectFRRS = SystemProperties.getInstance().getString("appl.AccountantIns.zoho.fordirectrenew.firstreviewer.RS");
						String forDirectFRQB = SystemProperties.getInstance().getString("appl.AccountantIns.zoho.fordirectnew.firstreviewer.QB");
//		    			String forDirectFRDK = SystemProperties.getInstance().getString("appl.AccountantIns.zoho.fordirectnew.firstreviewer.DK");
//						String forDirectFRSH = SystemProperties.getInstance().getString("appl.AccountantIns.zoho.fordirectnew.firstreviewer.SH");
//						String forDirectFRCC = SystemProperties.getInstance().getString("appl.AccountantIns.zoho.fordirectnew.firstreviewer.CC");
						String forDirectFRXR = SystemProperties.getInstance().getString("appl.AccountantIns.zoho.fordirectnew.firstreviewer.XR");
	
//						if("CT#GA#FL#IL".contains(stateCode)){
//		    				firstReviewer = forDirectFRRS;//Ryan Schwartz
	    				if("CT#GA#FL#IL#PA#NC#KS#UT#MD#ME#NY#NH#VT#AL#AR#HI#IA#MS".contains(stateCode)){
		    				firstReviewer = forDirectFRXR;//Xavier
						} else if("CA#CO#IN#TX#WA#ID#AZ#MA#NJ#MN#WI#NV#OH#MO#MI#VA#TN#AK#KY#MT#ND#NM#SC#SD#WY#NE#OK#DE#DC#LA#WV#RI".contains(stateCode)){
							firstReviewer = forDirectFRQB;//Quiana
//		    			} else if("CT#GA#FL#KS#VA#TN#UT#NC#SC#OH#IL#MO#MI#AL#AR#HI#IA#MS#NE#OK#DE#DC#LA#WV#RI".contains(stateCode)){
//		    				firstReviewer = forDirectFRDK;//Dane
//		    			} else if("AZ#MA#MI#NV#NC#PA#SC#DE#DC#MD#ME#NH#RI#VT".contains(stateCode)){
//		    				firstReviewer = forDirectFRSH;//Sharon
//		    			} else if("OH#IL#MN#MO#NJ#WI#LA#NY#WV#MI#NV#PA#NH#RI#VT".contains(stateCode)){
//		    				firstReviewer = forDirectFRCC;//Chaz
		    			}
		    		}
				}
			} else {
				String forSubProducerDH = SystemProperties.getInstance().getString("appl.AccountantIns.zoho.forsubproducer.firstreviewer.DH");
				String forSubProducerJW = SystemProperties.getInstance().getString("appl.AccountantIns.zoho.forsubproducer.firstreviewer.JW");
				
				if("P0000005".equals(producerCode) || "P0000012".equals(producerCode)) {
					if("2".equals(policyStatusKey)){
						firstReviewer = forSubProducerDH;//Daniel O'Halloran
					} else  {
						firstReviewer = forSubProducerJW;//Jessica Wolf
					}
				}
			}

			// CREATE XML COLUMN LIST
			String[] keyList = keys.split(",");
			String[] keyValue = values.split(",");

			Date date = null;
			if ("".equals(policyEffectiveDate)) {
				date = new SimpleDateFormat("MM/dd/yyyy").parse(ctx.get("PolicyEffectiveDate").toString());
			} else {
				date = new SimpleDateFormat("yyyy-MM-dd").parse(policyEffectiveDate);
			}
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			ctx.put("Deal_Name", cal.get(Calendar.YEAR) + "");
			if (ownerMap.get(firstReviewer) != null) {
				ctx.put("Owner", firstReviewer);
			} else {
				ctx.put("Owner", "");
			}
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			{ if (logger.isDebugEnabled()) logger.debug(dateFormat.format(date)); } // 2016/11/16 12:08:43
			ctx.put("Closing_Date", dateFormat.format(date));
			ctx.put("Stage", statusDesc);
			ctx.put("Premium", (totalPremium == null || totalPremium.isEmpty()) ? "" : Integer.parseInt(totalPremium));
			ctx.put("Policy", "APL");
			ctx.put("Expiring_Premium",
					(expiringPremium == null || expiringPremium.isEmpty()) ? ""
							: (expiringPremium.length() > 5) ? Integer.parseInt(expiringPremium.substring(0, 5))
									: expiringPremium);
			ctx.put("Carrier", carrier);
			ctx.put("Account_Name", accountName);

			updateZOHORecordsOfPotentials(accountName, zohoPotentialsID, zohoAccountID, ctx, keyList, keyValue,
					matchColumns, matchColumnValues, ownerMap);
		} catch (Exception e) {
			logger.error("Error in AccountUtils.insertZOHORecordsOfPotentials", e);
		}
	}

	public static void updateZOHORecordsOfPotentials(String accountName, String zohoPotentialsID, String zohoAccountID,
			Context ctx, String[] keyList, String[] keyValue, String[] matchColumns, String[] matchColumnValues,
			Map<String, String> ownerMap) throws Exception {
		String xmlDataString = "";
		boolean flag = true;

		// if(zohoPotentialsID != null && !"".equals(zohoPotentialsID) &&
		// !zohoPotentialsID.isEmpty()){
		// ctx.put("id", zohoPotentialsID);
		// ctx.put("Account_Name", "");
		// xmlDataString = createZohoUpdateJsonObjectOfPotentials(ctx, keyList,
		// keyValue, ownerMap);
		// if(!xmlDataString.isEmpty()){
		// logger.debug("update " + xmlDataString);
		// boolean result = updateRecordsIntoZOHODataOfPotentials(ctx, xmlDataString,
		// zohoPotentialsID, "NO");
		// if(!result){
		// zohoPotentialsID = "";
		// ctx.put("Account_Name", accountName);
		// updateZOHORecordsOfPotentials(accountName, zohoPotentialsID, zohoAccountID,
		// ctx, keyList, keyValue, matchColumns, matchColumnValues, ownerMap);
		// }
		// }
		// }else {
		JSONObject captureZohoData = getZOHORecordOfPotentials(ctx, matchColumns, matchColumnValues, zohoAccountID);
		if (captureZohoData != null && !captureZohoData.isNull("data")) {
			JSONArray captureZohoDataList = (JSONArray) captureZohoData.get("data");
			for (int captureZohoDataCount = 0; captureZohoDataCount < captureZohoDataList
					.length(); captureZohoDataCount++) {
				captureZohoData = new JSONObject(captureZohoDataList.get(captureZohoDataCount).toString());

				if (!captureZohoData.isNull("Policy")
						&& captureZohoData.get("Policy").toString().equalsIgnoreCase(ctx.get("Policy").toString())) {

					zohoPotentialsID = (captureZohoData.isNull("id")) ? null : (String) captureZohoData.get("id");
					ctx.put("id", zohoPotentialsID);
					ctx.put("Account_Name", "");
					xmlDataString = createZohoUpdateJsonObjectOfPotentials(ctx, keyList, keyValue, ownerMap);
					if (!xmlDataString.isEmpty()) {
						{ if (logger.isDebugEnabled()) logger.debug("update   " + xmlDataString); }
						updateRecordsIntoZOHODataOfPotentials(ctx, xmlDataString, zohoPotentialsID, "NO");
					}
					flag = false;
					break;
				}
			}
		}

		if (flag && xmlDataString.isEmpty()) {

			ctx.put("id", zohoPotentialsID);
			ctx.put("AccountZohoID", zohoAccountID);
			xmlDataString = createZohoUpdateJsonObjectOfPotentials(ctx, keyList, keyValue, ownerMap);
			if (!xmlDataString.isEmpty()) {
				{ if (logger.isDebugEnabled()) logger.debug("Insert   " + xmlDataString); }
				insertRecordsIntoZOHODataOfPotentials(ctx, xmlDataString);
			}
		}
		// }
	}

	public static String createZohoUpdateJsonObjectOfPotentials(Context ctx, String[] keyList, String[] keyValue,
			Map<String, String> ownerMap) {
		try {
			JSONArray captureZohoDataList = new JSONArray();
			JSONObject captureZohoObject = new JSONObject();
			JSONObject captureZohoData = new JSONObject();

			for (int statusCount = 0; statusCount < keyList.length; statusCount++) {
				if (ctx.get(keyValue[statusCount]) != null) {
					if ("Account_Name".equals(keyList[statusCount])) {
						if (!"".equals(ctx.get(keyValue[statusCount]))) {
							JSONObject accountZohoData = new JSONObject();
							accountZohoData.put("name", ctx.get(keyValue[statusCount]));
							accountZohoData.put("id", ctx.get("AccountZohoID"));

							captureZohoData.put(keyList[statusCount], accountZohoData);
						}
					} else if ("Owner".equals(keyList[statusCount])) {
						if (!"".equals(ctx.get(keyValue[statusCount]))) {
							JSONObject dealOwnerZohoData = new JSONObject();
							dealOwnerZohoData.put("name", ctx.get(keyValue[statusCount]));
							dealOwnerZohoData.put("id", ownerMap.get(ctx.get(keyValue[statusCount])));

							captureZohoData.put(keyList[statusCount], dealOwnerZohoData);
						}
					} else {
						captureZohoData.put(keyList[statusCount], ctx.get(keyValue[statusCount]));
					}

				}
			}
			captureZohoDataList.put(captureZohoData);
			captureZohoObject.put("data", captureZohoDataList);

			captureZohoDataList = new JSONArray();
			captureZohoData = new JSONObject();
			captureZohoData.put("workflow", "");
			captureZohoData.put("approval", "");
			captureZohoData.put("blueprint", "");

			captureZohoDataList.put(captureZohoData);
			captureZohoObject.put("triggger", captureZohoDataList);

			return captureZohoObject.toString();
		} catch (JSONException e) {
			logger.error("Error in AccountUtils.createZohoUpdateJsonObjectOfPotentials", e);
			// TODO Auto-generated catch block
		}
		return "";
	}

	public static void insertRecordsIntoZOHODataOfPotentials(Context ctx, String XmlString) {
		try {
			// ----------------------------Fetch Auth Token ----------------------
			// String targetURL =
			// "https://crm.zoho.com/crm/private/xml/Potentials/insertRecords";
			String targetURL = SystemProperties.getInstance().getString("appl.AccountantIns.zoho.modulesurl")
					+ "/Potentials";
			String authtoken = "Zoho-oauthtoken " + ctx.get("isZohoAccessToken");// If you don't have a authtoken please
																					// refer this wiki
																					// https://zohocrmapi.wiki.zoho.com/using-authtoken.html

			PostMethod post = null;
			// Execute http request
			try {
				post = new PostMethod(targetURL);
				post.setRequestHeader("Authorization", authtoken);
				post.setRequestHeader("Content-Type", "application/json");
				post.setRequestBody(XmlString);

				HttpClient httpclient = SharedHttpClient.get();

				int result = httpclient.executeMethod(post);
				String responseBody = post.getResponseBodyAsString();
				logger.debug(result);
				{ if (logger.isDebugEnabled()) logger.debug(responseBody); }

				JSONObject captureZohoData = new JSONObject(responseBody);
				if (captureZohoData != null && !captureZohoData.isNull("data")) {
					JSONArray captureZohoDataList = (JSONArray) captureZohoData.get("data");
					for (int captureZohoDataCount = 0; captureZohoDataCount < captureZohoDataList
							.length(); captureZohoDataCount++) {
						captureZohoData = new JSONObject(captureZohoDataList.get(captureZohoDataCount).toString());

						if (!captureZohoData.isNull("details")) {
							JSONObject captureZohoDataChild = (JSONObject) captureZohoData.get("details");
							if (!captureZohoDataChild.isNull("id")) {
								String zohoPotentialsID = (captureZohoDataChild.isNull("id")) ? null
										: (String) captureZohoDataChild.get("id");
								ctx.put("ZohoPotentialsID", zohoPotentialsID);
								int status = SqlResources.getSqlMapProcessor(ctx)
										.update("SqlStmts.UserStatementManualBoQueriesupdateZohoPotentialsID", ctx);
								{ if (logger.isDebugEnabled()) logger.debug(status); }
							}
						}
					}
				}

			} catch (Exception e) {
				logger.error("Error in AccountUtils.insertRecordsIntoZOHODataOfPotentials", e);
			} finally {
				post.releaseConnection();
			}
		} catch (Exception e) {
			logger.error("Error in AccountUtils.insertRecordsIntoZOHODataOfPotentials", e);
		}
	}

	public static boolean updateRecordsIntoZOHODataOfPotentials(Context ctx, String XmlString, String zohoPotentialsID,
			String isZohoPotentialsIDUpdate) {
		try {
			// ----------------------------Fetch Auth Token ----------------------
			// String targetURL =
			// "https://crm.zoho.com/crm/private/xml/Potentials/updateRecords";
			String targetURL = SystemProperties.getInstance().getString("appl.AccountantIns.zoho.modulesurl")
					+ "/Potentials/" + zohoPotentialsID;
			String authtoken = "Zoho-oauthtoken " + ctx.get("isZohoAccessToken");// If you don't have a authtoken please
																					// refer this wiki
																					// https://zohocrmapi.wiki.zoho.com/using-authtoken.html

			PutMethod post = null;
			// Execute http request
			try {
				post = new PutMethod(targetURL);
				post.setRequestHeader("Authorization", authtoken);
				post.setRequestHeader("Content-Type", "application/json");
				post.setRequestBody(XmlString);

				HttpClient httpclient = SharedHttpClient.get();

				int result = httpclient.executeMethod(post);
				String responseBody = post.getResponseBodyAsString();
				logger.debug(result);
				{ if (logger.isDebugEnabled()) logger.debug(responseBody); }
				if (responseBody != null && !"".equals(responseBody)) {
					JSONObject captureZohoData = new JSONObject(responseBody);
					if (captureZohoData != null && !captureZohoData.isNull("data")) {
						JSONArray captureZohoDataList = (JSONArray) captureZohoData.get("data");
						for (int captureZohoDataCount = 0; captureZohoDataCount < captureZohoDataList
								.length(); captureZohoDataCount++) {
							captureZohoData = new JSONObject(captureZohoDataList.get(captureZohoDataCount).toString());
							if (!captureZohoData.isNull("message")) {
								String message = (String) captureZohoData.get("message");
								if ("the id given seems to be invalid".equals(message)) {
									return false;
								}
							}
						}
					}
				}
				if ("YES".equals(isZohoPotentialsIDUpdate)) {
					ctx.put("ZohoPotentialsID", zohoPotentialsID);
					int status = SqlResources.getSqlMapProcessor(ctx)
							.update("SqlStmts.UserStatementManualBoQueriesupdateZohoPotentialsID", ctx);
				}

			} catch (Exception e) {
				logger.error("Error in AccountUtils.updateRecordsIntoZOHODataOfPotentials", e);
			} finally {
				post.releaseConnection();
			}
		} catch (Exception e) {
			logger.error("Error in AccountUtils.updateRecordsIntoZOHODataOfPotentials", e);
		}
		return true;
	}

	public static JSONObject getZOHORecordOfAccounts(Context ctx, String[] matchColumns, String[] matchColumnValues) {
		JSONObject copyCaptureZohoData = null;
		try {
			for (int criteriaCount = 0; criteriaCount < matchColumns.length; criteriaCount++) {
				String serachTargetURL = SystemProperties.getInstance().getString("appl.AccountantIns.zoho.modulesurl")
						+ "/Accounts/search?";
				// https://crm.zoho.com/crm/private/xml/Accounts/searchRecords?
				// https://www.zohoapis.com/crm/v2/Accounts/search?
				// https://www.zohoapis.com/crm/v2/Leads/search?
				String authtoken = "Zoho-oauthtoken " + ctx.get("isZohoAccessToken");// If you don't have a authtoken
																						// please refer this wiki
																						// https://zohocrmapi.wiki.zoho.com/using-authtoken.html
				String selectColumns = "Account_Name,Billing_Code,Billing_Street,Billing_State,Billing_City,County,Phone,Account_Email,Sub_Producer,NameClearStatus,NameClearType,NameClearDate,RequestedAppName,Deductible,Firm_Limit,Ex_Date";
				String criteria = "(";
				// if(!"(".equals(criteria) && criteriaCount > 0 &&
				// ctx.get(matchColumnValues[criteriaCount]) != null &&
				// !"".equals(ctx.get(matchColumnValues[criteriaCount]))){
				// criteria += conditionOpertor;
				// }
				if ("Account_Name".equals(matchColumns[criteriaCount])
						&& ctx.get(matchColumnValues[criteriaCount]) != null
						&& !"".equals(ctx.get(matchColumnValues[criteriaCount]))) {
					criteria += "(" + matchColumns[criteriaCount] + ":equals:"
							+ URLEncoder.encode(ctx.get(matchColumnValues[criteriaCount]).toString(), "UTF-8") + ")";
				}
				// if("Billing_State".equals(matchColumns[criteriaCount]) &&
				// ctx.get(matchColumnValues[criteriaCount]) != null &&
				// !"".equals(ctx.get(matchColumnValues[criteriaCount]))){
				// criteria += "AND(" + matchColumns[criteriaCount] + ":equals:" +
				// URLEncoder.encode(ctx.get(matchColumnValues[criteriaCount]).toString(),
				// "UTF-8") + "))";
				// }
				if ("Phone".equals(matchColumns[criteriaCount]) && ctx.get(matchColumnValues[criteriaCount]) != null
						&& !"".equals(ctx.get(matchColumnValues[criteriaCount]))) {
					String workPhone = ctx.get(matchColumnValues[criteriaCount]).toString().replaceAll("\\D+", "");
					workPhone = workPhone.substring(0, 3) + "-" + workPhone.substring(3, 6) + "-"
							+ workPhone.substring(6, 10);
					criteria += "(" + matchColumns[criteriaCount] + ":equals:" + URLEncoder.encode(workPhone, "UTF-8")
							+ ")";
				}
				if ("Account_Email".equals(matchColumns[criteriaCount])
						&& ctx.get(matchColumnValues[criteriaCount]) != null
						&& !"".equals(ctx.get(matchColumnValues[criteriaCount]))) {
					criteria += "(" + matchColumns[criteriaCount] + ":equals:"
							+ URLEncoder.encode(ctx.get(matchColumnValues[criteriaCount]).toString(), "UTF-8") + ")";
				}
				criteria += ")";

				// Authorization: Zoho-oauthtoken d92d401c803988c5cb849d0b4215f52"
				// ----------------------------Fetch ZOHO Data ----------------------
				serachTargetURL += "fields=" + selectColumns + "&criteria=" + criteria;
				GetMethod get = new GetMethod(serachTargetURL);
				get.setRequestHeader("Authorization", authtoken);

				HttpClient httpclient = SharedHttpClient.get();
				int result = httpclient.executeMethod(get);
				String responseBody = get.getResponseBodyAsString();

				{ if (logger.isDebugEnabled()) logger.debug(result); }
				{ if (logger.isDebugEnabled()) logger.debug("status     " + responseBody); }

				if (responseBody != null && !"".equals(responseBody)) {
					JSONObject captureZohoData = new JSONObject(responseBody);
					if (!(captureZohoData != null && !captureZohoData.isNull("status")
							&& "error".equals(captureZohoData.get("status")))) {
						if (copyCaptureZohoData == null) {
							copyCaptureZohoData = new JSONObject(captureZohoData, JSONObject.getNames(captureZohoData));
						} else {
							if (captureZohoData != null && !captureZohoData.isNull("data")) {
								JSONArray captureZohoDataList = (JSONArray) captureZohoData.get("data");
								for (int captureZohoDataCount = 0; captureZohoDataCount < captureZohoDataList
										.length(); captureZohoDataCount++) {
									((JSONArray) copyCaptureZohoData.get("data")).put(
											new JSONObject(captureZohoDataList.get(captureZohoDataCount).toString()));
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("Error in AccountUtils.getZOHORecordOfAccounts", e);
		}
		return copyCaptureZohoData;
	}

	public static String insertZOHORecordsOfAccounts(Context ctx) {
		try {
			String maxPolicyKey = "";
			List getMaxPolicyKey = (List) SqlResources.getSqlMapProcessor(ctx)
					.select("SqlStmts.UserStatementManualBoQueriesgetZohoMaxPolicyKey", ctx);
			if (getMaxPolicyKey != null && getMaxPolicyKey.size() > 0 && !getMaxPolicyKey.isEmpty()) {
				Map getMaxPolicyKeyList = (Map) getMaxPolicyKey.get(0);
				maxPolicyKey = (getMaxPolicyKeyList.get("MaxPolicyKey") == null ? ""
						: getMaxPolicyKeyList.get("MaxPolicyKey").toString());
			}

			if (maxPolicyKey == null || "".equals(maxPolicyKey)
					|| maxPolicyKey.equals(String.valueOf(ctx.get("PolicyKey")))) {

				String totalPremium = "";
				String transactionSequence = "";
				ctx.put("PolicyKey", Integer.parseInt(ctx.get("PolicyKey").toString()));
				List getPolicyTotalPremium = (List) SqlResources.getSqlMapProcessor(ctx)
						.select("Quote.GetZohoPremiumLW_p", ctx);
				if (getPolicyTotalPremium != null && getPolicyTotalPremium.size() > 0
						&& !getPolicyTotalPremium.isEmpty()) {
					Map getPolicyTotalPremiumList = (Map) getPolicyTotalPremium.get(0);
					totalPremium = (getPolicyTotalPremiumList.get("InvoicedPremium") == null ? ""
							: getPolicyTotalPremiumList.get("InvoicedPremium").toString());
					transactionSequence = (getPolicyTotalPremiumList.get("TransactionSequence") == null ? ""
							: getPolicyTotalPremiumList.get("TransactionSequence").toString());
				}
				ctx.put("PolicyKey", String.valueOf(ctx.get("PolicyKey")));

				String isZohoInsert = "";
				String zohoAccountID = "";
	    		String isFirmHaveLawyersLiabilityInsurance = "";
				List getZohoAccountID = (List) SqlResources.getSqlMapProcessor(ctx)
						.select("SqlStmts.UserStatementManualBoQueriesgetZohoAccountID", ctx);
				if (getZohoAccountID != null && getZohoAccountID.size() > 0 && !getZohoAccountID.isEmpty()) {
					Map getZohoAccountIDList = (Map) getZohoAccountID.get(0);
					zohoAccountID = (getZohoAccountIDList.get("ZohoAccountID") == null ? ""
							: getZohoAccountIDList.get("ZohoAccountID").toString());
					isZohoInsert = (getZohoAccountIDList.get("ISZohoInsert") == null ? ""
							: getZohoAccountIDList.get("ISZohoInsert").toString());
					isFirmHaveLawyersLiabilityInsurance = (getZohoAccountIDList.get("IsFirmHaveLawyersLiabilityInsurance") == null ? ""
							: getZohoAccountIDList.get("IsFirmHaveLawyersLiabilityInsurance").toString());
				}

				String accountName = "";
				String stateCode = "";
				String workPhone = "";
				String accountEmail = "";
				String address1 = "";
				String city = "";
				String zip = "";
				String countyDesc = "";
				String street = "";
				String stateDesc = "";
				String statusDesc = "";
				List getPolicyStatus = (List) SqlResources.getSqlMapProcessor(ctx)
						.select("SqlStmts.UserStatementManualBoQueriesgetZohoStatus", ctx);
				if (getPolicyStatus != null && getPolicyStatus.size() > 0 && !getPolicyStatus.isEmpty()) {
					Map getPolicyStatusList = (Map) getPolicyStatus.get(0);
					accountName = (getPolicyStatusList.get("AccountName") == null ? ""
							: getPolicyStatusList.get("AccountName").toString());
					accountEmail = (getPolicyStatusList.get("AccountEmail") == null ? ""
							: getPolicyStatusList.get("AccountEmail").toString());
					address1 = (getPolicyStatusList.get("Address1") == null ? ""
							: getPolicyStatusList.get("Address1").toString());
					city = (getPolicyStatusList.get("City") == null ? "" : getPolicyStatusList.get("City").toString());
					stateCode = (getPolicyStatusList.get("StateCode") == null ? ""
							: getPolicyStatusList.get("StateCode").toString());
					zip = (getPolicyStatusList.get("Zip") == null ? "" : getPolicyStatusList.get("Zip").toString());
					zip += (getPolicyStatusList.get("Zip4") == null ? ""
							: "-" + getPolicyStatusList.get("Zip4").toString());
					workPhone = (getPolicyStatusList.get("WorkPhone") == null ? ""
							: getPolicyStatusList.get("WorkPhone").toString());
					countyDesc = (getPolicyStatusList.get("CountyDesc") == null ? ""
							: getPolicyStatusList.get("CountyDesc").toString());
					street = (getPolicyStatusList.get("Street") == null ? ""
							: getPolicyStatusList.get("Street").toString());
					stateDesc = (getPolicyStatusList.get("StateDesc") == null ? ""
							: getPolicyStatusList.get("StateDesc").toString());
					statusDesc = (getPolicyStatusList.get("StatusDesc") == null ? ""
							: getPolicyStatusList.get("StatusDesc").toString());
				}

				accountName = ("".equals(accountName)) ? (String) ctx.get("AccountName") : accountName;
				stateCode = ("".equals(stateCode)) ? (String) ctx.get("StateCode") : stateCode;
				address1 = ("".equals(address1)) ? (String) ctx.get("Address1") : address1;
				countyDesc = ("".equals(countyDesc)) ? (String) ctx.get("CountyDesc") : countyDesc;
				city = ("".equals(city)) ? (String) ctx.get("City") : city;
				zip = ("".equals(zip)) ? (String) ctx.get("Zip") : zip;
				workPhone = ("".equals(workPhone)) ? (String) ctx.get("WorkPhone") : workPhone;
				accountEmail = ("".equals(accountEmail)) ? (String) ctx.get("AccountEmail") : accountEmail;

				String subProducerName = "";
				List getSubProducerNameList = (List) SqlResources.getSqlMapProcessor(ctx)
						.select("SqlStmts.UserStatementManualBoQueriesgetZohoSubProducerName", ctx);
				if (getSubProducerNameList != null && getSubProducerNameList.size() > 0
						&& !getSubProducerNameList.isEmpty()) {
					Map getSubProducerNameMap = (Map) getSubProducerNameList.get(0);
					subProducerName = (getSubProducerNameMap.get("ProducerCompanyName") == null ? ""
							: getSubProducerNameMap.get("ProducerCompanyName").toString());
				}

				String totalFirmPersonnel = "";
				List getZohoTotalFirmPersonnel = (List) SqlResources.getSqlMapProcessor(ctx)
						.select("SqlStmts.UserStatementManualBoQueriesgetZohoTotalFirmPersonnel", ctx);
				if (getZohoTotalFirmPersonnel != null && getZohoTotalFirmPersonnel.size() > 0
						&& !getZohoTotalFirmPersonnel.isEmpty()) {
					Map getTotalFirmPersonnelList = (Map) getZohoTotalFirmPersonnel.get(0);
					totalFirmPersonnel = (getTotalFirmPersonnelList.get("TotalFirmPersonnel") == null ? ""
							: getTotalFirmPersonnelList.get("TotalFirmPersonnel").toString());
				}

				String grossRevenueForFirm = "";
				List getZohoGrossRevenueForFirm = (List) SqlResources.getSqlMapProcessor(ctx)
						.select("SqlStmts.UserStatementManualBoQueriesgetZohoGrossRevenueForFirm", ctx);
				if (getZohoGrossRevenueForFirm != null && getZohoGrossRevenueForFirm.size() > 0
						&& !getZohoGrossRevenueForFirm.isEmpty()) {
					Map getGrossRevenueForFirmList = (Map) getZohoGrossRevenueForFirm.get(0);
					grossRevenueForFirm = (getGrossRevenueForFirmList.get("AnnualRevenue") == null ? ""
							: getGrossRevenueForFirmList.get("AnnualRevenue").toString());
				}

				String priorActDatePross = "";
	    		String isPriorActDateFull = "";
				List getZohoPriorActDatePross = (List) SqlResources.getSqlMapProcessor(ctx)
						.select("SqlStmts.UserStatementManualBoQueriesgetZohoPriorActDateProssDetails", ctx);
				if (getZohoPriorActDatePross != null && getZohoPriorActDatePross.size() > 0
						&& !getZohoPriorActDatePross.isEmpty()) {
					Map getPriorActDateProssList = (Map) getZohoPriorActDatePross.get(0);
					priorActDatePross = (getPriorActDateProssList.get("PriorActDatePross") == null ? ""
							: getPriorActDateProssList.get("PriorActDatePross").toString());
					isPriorActDateFull = (getPriorActDateProssList.get("IsPriorActDateFull") == null ? "" 
							: getPriorActDateProssList.get("IsPriorActDateFull").toString());
				}

				if("Y".equals(isPriorActDateFull)){
	    			priorActDatePross = "Full";
	    		}

				String aggregateLimit = "";
				String occuranceLimit = "";
				String aggregateDeductible = "";
				String occuranceDeductible = "";
				ctx.put("TransactionSequenceZoho", transactionSequence);
				List getDeductibleLimitList = (List) SqlResources.getSqlMapProcessor(ctx)
						.select("SqlStmts.UserStatementManualBoQueriesgetZohoDeductible", ctx);
				if (getDeductibleLimitList != null && getDeductibleLimitList.size() > 0
						&& !getDeductibleLimitList.isEmpty()) {
					Map getDeductibleLimitMap = (Map) getDeductibleLimitList.get(0);
					aggregateLimit = (getDeductibleLimitMap.get("AggregateLimit") == null ? ""
							: getDeductibleLimitMap.get("AggregateLimit").toString());
					occuranceLimit = (getDeductibleLimitMap.get("OccuranceLimit") == null ? ""
							: getDeductibleLimitMap.get("OccuranceLimit").toString());
					aggregateDeductible = (getDeductibleLimitMap.get("AggregateDeductible") == null ? ""
							: getDeductibleLimitMap.get("AggregateDeductible").toString());
					occuranceDeductible = (getDeductibleLimitMap.get("OccuranceDeductible") == null ? ""
							: getDeductibleLimitMap.get("OccuranceDeductible").toString());
				}

				if (aggregateLimit != null && !aggregateLimit.isEmpty()) {
					int aggrLimit = Integer.parseInt(aggregateLimit);
					if (aggrLimit >= 100000 && aggrLimit < 1000000) {
						aggregateLimit = (String) amountFormatLawyers(aggregateLimit);
					} else if (aggrLimit >= 1000000) {
						aggregateLimit = "$" + aggregateLimit.substring(0, 1) + "M";
					}
					aggregateLimit += "/";
				}

				if (occuranceLimit != null && !occuranceLimit.isEmpty()) {
					int occurLimit = Integer.parseInt(occuranceLimit);
					if (occurLimit >= 100000 && occurLimit < 1000000) {
						occuranceLimit = (String) amountFormatLawyers(occuranceLimit);
					} else if (occurLimit >= 1000000) {
						occuranceLimit = "$" + occuranceLimit.substring(0, 1) + "M";
					}
				}

				String quoteType = "";
				String policyEffectiveDate = "";
				String policyStatusKey = "";
				String quoteNumber = "";
				String companyName = "";
				List getPolicyEffectiveDate = (List) SqlResources.getSqlMapProcessor(ctx)
						.select("SqlStmts.UserStatementManualBoQueriesgetZohoPolicyEffectiveDate", ctx);
				if (getPolicyEffectiveDate != null && getPolicyEffectiveDate.size() > 0
						&& !getPolicyEffectiveDate.isEmpty()) {
					Map getPolicyEffectiveDateList = (Map) getPolicyEffectiveDate.get(0);
					policyEffectiveDate = (getPolicyEffectiveDateList.get("PolicyEffectiveDate") == null ? ""
							: getPolicyEffectiveDateList.get("PolicyEffectiveDate").toString());
					quoteType = (getPolicyEffectiveDateList.get("QuoteType") == null ? ""
							: getPolicyEffectiveDateList.get("QuoteType").toString());
					policyStatusKey = (getPolicyEffectiveDateList.get("PolicyStatusKey") == null ? ""
							: getPolicyEffectiveDateList.get("PolicyStatusKey").toString());
					quoteNumber = (getPolicyEffectiveDateList.get("QuoteNumber") == null ? ""
							: getPolicyEffectiveDateList.get("QuoteNumber").toString());
					companyName = (getPolicyEffectiveDateList.get("CompanyName") == null ? ""
							: getPolicyEffectiveDateList.get("CompanyName").toString());
				}

				String[] matchColumns = { "Account_Name", "Phone", "Account_Email" };
				String[] matchColumnValues = { "Account_Name", "Phone", "Account_Email" };

				// CREATE XML COLUMN LIST
				String[] keyList = { "Account_Name", "Billing_State", "Billing_Street", "County", "Billing_City",
						"Billing_Code", "Phone", "Account_Email", "NameClearDate", "NameClearStatus", "NameClearType",
						"RequestedAppName", "Sub_Producer", "Our_Premium", "Quote_Number", "Firm_Limit", "Deductible",
						"Ex_Date", "Professionals", "Annual_Revenue", "Prior_Acts_Date", "Industry", "id" };
				String[] keyValue = { "Account_Name", "Billing_State", "Billing_Street", "County", "Billing_City",
						"Billing_Code", "Phone", "Account_Email", "NameClearDate", "NameClearStatus", "NameClearType",
						"RequestedAppName", "Sub_Producer", "Our_Premium", "Quote_Number", "Firm_Limit", "Deductible",
						"Ex_Date", "Professionals", "Annual_Revenue", "Prior_Acts_Date", "Industry", "id" };

				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				Date date = new Date();
				{ if (logger.isDebugEnabled()) logger.debug(dateFormat.format(date)); } // 2016/11/16 12:08:43
				Date exDate = null;
				if ("".equals(policyEffectiveDate)) {
					exDate = new SimpleDateFormat("MM/dd/yyyy").parse(ctx.get("PolicyEffectiveDate").toString());
				} else {
					exDate = new SimpleDateFormat("yyyy-MM-dd").parse(policyEffectiveDate);
				}
				ctx.put("Account_Name", accountName);
				ctx.put("Billing_State", stateCode);
				ctx.put("Billing_Street", address1);
				ctx.put("County", countyDesc);
				ctx.put("Billing_City", city);
				ctx.put("Billing_Code", zip);
				ctx.put("Phone", workPhone);
				ctx.put("Account_Email", accountEmail);
				ctx.put("Ex_Date", dateFormat.format(exDate));
				ctx.put("Our_Premium", totalPremium);
				ctx.put("Quote_Number", quoteNumber);
				ctx.put("Firm_Limit", aggregateLimit + occuranceLimit);
				ctx.put("Deductible", aggregateDeductible);
				ctx.put("Professionals", totalFirmPersonnel);
				ctx.put("Annual_Revenue", ("".equals(grossRevenueForFirm) ? ""
						: grossRevenueForFirm.substring(0, grossRevenueForFirm.indexOf("."))));
				if(!("New".equals(statusDesc) && "Y".equals(ctx.get("IsQuickQuote"))) && !"Indication".equals(statusDesc)) {
					if(!"".equals(priorActDatePross) && !priorActDatePross.isEmpty()){
						ctx.put("Prior_Acts_Date",priorActDatePross);
					} else if("1".equals(policyStatusKey) && "N".equals(isFirmHaveLawyersLiabilityInsurance)){
						ctx.put("Prior_Acts_Date",new SimpleDateFormat("MM/dd/yyyy").format(exDate));
					}
				}
				ctx.put("Industry", "Accountants");
				ctx.put("RequestedAppName", "AccountantIns");
				ctx.put("Sub_Producer", subProducerName);

				String flagStatus = ctx.get("ZohoStatusDescUpdated") == null ? ""
						: ctx.get("ZohoStatusDescUpdated").toString().replaceAll("\\(P\\)", "").trim();
				if (("Y".equals(
						ctx.get("IsAccountDetailChanged") == null ? "" : ctx.get("IsAccountDetailChanged").toString()))
						|| ("1".equals(policyStatusKey)
								&& (ctx.get("isNameClearanceRequired") == null
										|| "".equalsIgnoreCase(ctx.get("isNameClearanceRequired").toString().trim())
										|| ctx.get("isNameClearanceRequired").toString().isEmpty())
								&& (("FQ".equals(quoteType) && "New".equals(flagStatus)
										&& "FirmNewApp".equalsIgnoreCase(ctx.get("inet_page").toString())
										&& "save".equalsIgnoreCase(ctx.get("inet_method").toString()))
										|| ("IND".equals(quoteType) && "IndicationZohoStatus".equals(flagStatus)
												&& "indicationinjack".equalsIgnoreCase(ctx.get("inet_page").toString())
												&& "addIndication".equalsIgnoreCase(ctx.get("inet_method").toString()))
										|| ("QQ".equals(quoteType) && "QuickQuoteZohoStatus".equals(flagStatus)
												&& "quickquoteinsured".equalsIgnoreCase(ctx.get("inet_page").toString())
												&& "save".equalsIgnoreCase(ctx.get("inet_method").toString()))))) {

					if (AccountantValidationUtils.validateExistingZohoRecordInProdDB(ctx)
							|| "Y".equals(ctx.get("IsAccountDetailChanged") == null ? ""
									: ctx.get("IsAccountDetailChanged").toString())) {
						zohoAccountID = generateZohoNameClearanceFlag(ctx);
					} else {
						zohoAccountID = updateZohoDirectAccount(dateFormat, date, accountName, stateCode, accountEmail,
								workPhone, isZohoInsert, zohoAccountID, ctx, keyList, keyValue, matchColumns,
								matchColumnValues);
					}
				} else {
					zohoAccountID = updateZohoDirectAccount(dateFormat, date, accountName, stateCode, accountEmail,
							workPhone, isZohoInsert, zohoAccountID, ctx, keyList, keyValue, matchColumns,
							matchColumnValues);
				}
				int status = SqlResources.getSqlMapProcessor(ctx)
						.update("SqlStmts.UserStatementManualBoQueriesupdateZohoSubProducerNameClearance", ctx);
				{ if (logger.isDebugEnabled()) logger.debug(status); }

				return zohoAccountID;
			}
		} catch (Exception e) {
			logger.error("Error in AccountUtils.insertZOHORecordsOfAccounts", e);
		}
		return "";
	}

	public static String updateZohoDirectAccount(SimpleDateFormat dateFormat, Date date, String accountName,
			String stateCode, String accountEmail, String workPhone, String isZohoInsert, String zohoAccountID,
			Context ctx, String[] keyList, String[] keyValue, String[] matchColumns, String[] matchColumnValues)
			throws Exception {
		final String nameClearStatus = "UnderReview";
		String xmlDataString = "";
		boolean flag = true;

		ctx.put("NameClearDate", dateFormat.format(date));
		ctx.put("NameClearStatus", nameClearStatus);
		ctx.put("NameClearType", "Automatic");

		if (zohoAccountID != null && !"".equals(zohoAccountID) && !zohoAccountID.isEmpty()) {
			ctx.put("id", zohoAccountID);
			xmlDataString = createZohoUpdateXML(ctx, keyList, keyValue);
			if (!xmlDataString.isEmpty()) {
				{ if (logger.isDebugEnabled()) logger.debug("update   " + xmlDataString); }
				boolean result = updateRecordsIntoZOHOData(ctx, xmlDataString, zohoAccountID, "NO", isZohoInsert);
				if (!result) {
					zohoAccountID = "";
					zohoAccountID = updateZohoDirectAccount(dateFormat, date, accountName, stateCode, accountEmail,
							workPhone, isZohoInsert, zohoAccountID, ctx, keyList, keyValue, matchColumns,
							matchColumnValues);
				}
			}
		} else {

			JSONObject captureZohoData = getZOHORecordOfAccounts(ctx, matchColumns, matchColumnValues);
			if (captureZohoData != null && !captureZohoData.isNull("data")) {
				JSONArray captureZohoDataList = (JSONArray) captureZohoData.get("data");
				for (int captureZohoDataCount = 0; captureZohoDataCount < captureZohoDataList
						.length(); captureZohoDataCount++) {
					captureZohoData = new JSONObject(captureZohoDataList.get(captureZohoDataCount).toString());

					if (((!captureZohoData.isNull("Account_Name")
							&& captureZohoData.get("Account_Name").toString().replaceAll("[^a-zA-Z0-9\\s]", "")
									.equalsIgnoreCase(accountName.replaceAll("[^a-zA-Z0-9\\s]", "")))
							&& (!captureZohoData.isNull("Billing_State")
									&& captureZohoData.get("Billing_State").toString().equalsIgnoreCase(stateCode)))
							|| ((!captureZohoData.isNull("Account_Name")
									&& captureZohoData.get("Account_Name").toString().replaceAll("[^a-zA-Z0-9\\s]", "")
											.equalsIgnoreCase(accountName.replaceAll("[^a-zA-Z0-9\\s]", "")))
									&& (!captureZohoData.isNull("Phone") && captureZohoData.get("Phone").toString()
											.replaceAll("\\D+", "").equalsIgnoreCase(workPhone.replaceAll("\\D+", ""))))
							|| (!captureZohoData.isNull("Account_Email") && captureZohoData.get("Account_Email")
									.toString().equalsIgnoreCase(accountEmail))) {

						zohoAccountID = (captureZohoData.isNull("id")) ? null : (String) captureZohoData.get("id");
						ctx.put("id", zohoAccountID);
						xmlDataString = createZohoUpdateXML(ctx, keyList, keyValue);
						if (!xmlDataString.isEmpty()) {
							{ if (logger.isDebugEnabled()) logger.debug("update   " + xmlDataString); }
							updateRecordsIntoZOHOData(ctx, xmlDataString, zohoAccountID, "YES", isZohoInsert);
						}
						flag = false;
						break;
					}
				}
			}
			if (flag && xmlDataString.isEmpty()) {
				ctx.put("id", zohoAccountID);
				xmlDataString = createZohoUpdateXML(ctx, keyList, keyValue);
				if (!xmlDataString.isEmpty()) {
					{ if (logger.isDebugEnabled()) logger.debug("Insert   " + xmlDataString); }
					zohoAccountID = insertRecordsIntoZOHOData(ctx, xmlDataString);
					ctx.put("isNameClearanceRequired", "N");
				}
			}
		}
		return zohoAccountID;
	}

	public static String generateZohoNameClearanceFlag(Context ctx) throws Exception {
		String ruleId = "NameClearance_Accountant", desc = "Name Clearance Required",
				tooltip = "Name Clearance Required";
		RuleEngineUtilityNewApp.insertRulesToDatabase(ctx, null, ruleId, desc, tooltip);
		ctx.put("isNameClearanceRequired", "Y");
		ctx.put("NameClearanceIndicationFlag", "Y");
		return "";
	}

	public static String createZohoUpdateXML(Context ctx, String[] keyList, String[] keyValue) {
		try {
			JSONArray captureZohoDataList = new JSONArray();
			JSONObject captureZohoObject = new JSONObject();
			JSONObject captureZohoData = new JSONObject();

			for (int statusCount = 0; statusCount < keyList.length; statusCount++) {
				if (ctx.get(keyValue[statusCount]) != null) {
					if ("Phone".equals(keyList[statusCount])) {
						captureZohoData.put(keyList[statusCount],
								ctx.get(keyValue[statusCount]).toString().replaceAll("\\(", "").replaceAll("\\)", "-"));
					} else {
						captureZohoData.put(keyList[statusCount], ctx.get(keyValue[statusCount]));
					}
				}
			}
			captureZohoDataList.put(captureZohoData);
			captureZohoObject.put("data", captureZohoDataList);

			captureZohoDataList = new JSONArray();
			captureZohoData = new JSONObject();
			captureZohoData.put("workflow", "");
			captureZohoData.put("approval", "");
			captureZohoData.put("blueprint", "");

			captureZohoDataList.put(captureZohoData);
			captureZohoObject.put("triggger", captureZohoDataList);

			return captureZohoObject.toString();
		} catch (JSONException e) {
			logger.error("Error in AccountUtils.createZohoUpdateXML", e);
			// TODO Auto-generated catch block
		}
		return "";
	}

	public static String insertRecordsIntoZOHOData(Context ctx, String XmlString) {
		try {
			// ----------------------------Fetch Auth Token ----------------------
			// String targetURL =
			// "https://crm.zoho.com/crm/private/xml/Accounts/insertRecords";
			String targetURL = SystemProperties.getInstance().getString("appl.AccountantIns.zoho.modulesurl")
					+ "/Accounts";
			String authtoken = "Zoho-oauthtoken " + ctx.get("isZohoAccessToken");// If you don't have a authtoken please
																					// refer this wiki
																					// https://zohocrmapi.wiki.zoho.com/using-authtoken.html

			String zohoAccountID = "";
			PostMethod post = null;
			// Execute http request
			try {
				post = new PostMethod(targetURL);
				post.setRequestHeader("Authorization", authtoken);
				post.setRequestHeader("Content-Type", "application/json");
				post.setRequestBody(XmlString);

				HttpClient httpclient = SharedHttpClient.get();

				int result = httpclient.executeMethod(post);
				String responseBody = post.getResponseBodyAsString();
				logger.debug(result);
				{ if (logger.isDebugEnabled()) logger.debug(responseBody); }

				JSONObject captureZohoData = new JSONObject(responseBody);
				if (captureZohoData != null && !captureZohoData.isNull("data")) {
					JSONArray captureZohoDataList = (JSONArray) captureZohoData.get("data");
					for (int captureZohoDataCount = 0; captureZohoDataCount < captureZohoDataList
							.length(); captureZohoDataCount++) {
						captureZohoData = new JSONObject(captureZohoDataList.get(captureZohoDataCount).toString());

						if (!captureZohoData.isNull("details")) {
							JSONObject captureZohoDataChild = (JSONObject) captureZohoData.get("details");
							if (!captureZohoDataChild.isNull("id")) {
								zohoAccountID = (captureZohoDataChild.isNull("id")) ? null
										: (String) captureZohoDataChild.get("id");
								ctx.put("ZohoAccountID", zohoAccountID);
								ctx.put("ISZohoInsert", "Insert");
								int status = SqlResources.getSqlMapProcessor(ctx)
										.update("SqlStmts.UserStatementManualBoQueriesupdateZohoAccountID", ctx);
								status = SqlResources.getSqlMapProcessor(ctx)
										.update("SqlStmts.UserStatementManualBoQueriesupdateISZohoInsert", ctx);
								{ if (logger.isDebugEnabled()) logger.debug(status); }
							}
						}
					}
				}
				return zohoAccountID;
			} catch (Exception e) {
				logger.error("Error in AccountUtils.insertRecordsIntoZOHOData", e);
			} finally {
				post.releaseConnection();
			}
		} catch (Exception e) {
			logger.error("Error in AccountUtils.insertRecordsIntoZOHOData", e);
		}
		return "";
	}

	public static boolean updateRecordsIntoZOHOData(Context ctx, String XmlString, String zohoAccountID,
			String isZohoAccountIDUpdate, String isZohoInsert) {
		try {
			// ----------------------------Fetch Auth Token ----------------------
			// String targetURL =
			// "https://crm.zoho.com/crm/private/xml/Accounts/updateRecords";
			String targetURL = SystemProperties.getInstance().getString("appl.AccountantIns.zoho.modulesurl")
					+ "/Accounts/" + zohoAccountID;
			String authtoken = "Zoho-oauthtoken " + ctx.get("isZohoAccessToken");// If you don't have a authtoken please
																					// refer this wiki
																					// https://zohocrmapi.wiki.zoho.com/using-authtoken.html

			PutMethod post = null;
			// Execute http request
			try {
				post = new PutMethod(targetURL);
				post.setRequestHeader("Authorization", authtoken);
				post.setRequestHeader("Content-Type", "application/json");
				post.setRequestBody(XmlString);

				HttpClient httpclient = SharedHttpClient.get();

				int result = httpclient.executeMethod(post);
				String responseBody = post.getResponseBodyAsString();
				logger.debug(result);
				{ if (logger.isDebugEnabled()) logger.debug(responseBody); }

				if (responseBody != null && !"".equals(responseBody)) {
					JSONObject captureZohoData = new JSONObject(responseBody);
					if (captureZohoData != null && !captureZohoData.isNull("data")) {
						JSONArray captureZohoDataList = (JSONArray) captureZohoData.get("data");
						for (int captureZohoDataCount = 0; captureZohoDataCount < captureZohoDataList
								.length(); captureZohoDataCount++) {
							captureZohoData = new JSONObject(captureZohoDataList.get(captureZohoDataCount).toString());
							if (!captureZohoData.isNull("message")) {
								String message = (String) captureZohoData.get("message");
								if ("the id given seems to be invalid".equals(message)) {
									return false;
								}
							}
						}
					}
				}
				if ("YES".equals(isZohoAccountIDUpdate)) {
					ctx.put("ZohoAccountID", zohoAccountID);
					int status = SqlResources.getSqlMapProcessor(ctx)
							.update("SqlStmts.UserStatementManualBoQueriesupdateZohoAccountID", ctx);
				}
				if (!("Insert".equals(isZohoInsert) || "Update".equals(isZohoInsert))) {
					ctx.put("ISZohoInsert", "Update");
					int status = SqlResources.getSqlMapProcessor(ctx)
							.update("SqlStmts.UserStatementManualBoQueriesupdateISZohoInsert", ctx);
				}
			} catch (Exception e) {
				logger.error("Error in AccountUtils.updateRecordsIntoZOHOData", e);
			} finally {
				post.releaseConnection();
			}
		} catch (Exception e) {
			logger.error("Error in AccountUtils.updateRecordsIntoZOHOData", e);
		}
		return true;
	}

	public static void getZOHOIndicationRecordsOfAccounts(Context ctx) {
		try {
			if (ctx.get("WorkPhone") != null && !"".equals(ctx.get("WorkPhone"))
					|| (ctx.get("AccountEmail") != null && !"".equals(ctx.get("AccountEmail")))) {

				String[] matchColumns = { "Account_Email", "Phone" };
				String[] matchColumnValues = { "AccountEmail", "WorkPhone" };

				int count = 0;

				JSONObject captureZohoData = getZOHOIndicationRecordAccounts(ctx, matchColumns, matchColumnValues);
				if (captureZohoData != null && !captureZohoData.isNull("data")) {
					JSONArray captureZohoDataList = (JSONArray) captureZohoData.get("data");
					for (int captureZohoDataCount = 0; captureZohoDataCount < captureZohoDataList
							.length(); captureZohoDataCount++) {
						captureZohoData = new JSONObject(captureZohoDataList.get(captureZohoDataCount).toString());

						if (!captureZohoData.isNull("Account_Email") && captureZohoData.get("Account_Email").toString()
								.equalsIgnoreCase(ctx.get("AccountEmail").toString())) {
							count++;
						}
						if (!captureZohoData.isNull("Phone")
								&& captureZohoData.get("Phone").toString().replaceAll("\\D+", "")
										.equalsIgnoreCase(ctx.get("WorkPhone").toString().replaceAll("\\D+", ""))) {
							count++;
						}
						if (count != 0) {

							boolean flag = false;
							String zohoAccountID = (captureZohoData.isNull("id")) ? null
									: (String) captureZohoData.get("id");
							String county = (captureZohoData.isNull("County")) ? null
									: (String) captureZohoData.get("County");
							if (county != null && !"".equals(county)) {
								ctx.put("CountyDesc", county);
							} else {
								flag = true;
							}
							String city = (captureZohoData.isNull("Billing_City")) ? null
									: (String) captureZohoData.get("Billing_City");
							if (city != null && !"".equals(city)) {
								ctx.put("City", city);
							} else {
								flag = true;
							}
							String accountEmail = (captureZohoData.isNull("Account_Email")) ? null
									: (String) captureZohoData.get("Account_Email");
							if (accountEmail != null && !"".equals(accountEmail)) {
								ctx.put("AccountEmail", accountEmail);
							} else {
								flag = true;
							}
							String accountName = (captureZohoData.isNull("Account_Name")) ? null
									: (String) captureZohoData.get("Account_Name");
							if (accountName != null && !"".equals(accountName)) {
								ctx.put("AccountName", accountName);
							} else {
								flag = true;
							}
							String state = (captureZohoData.isNull("Billing_State")) ? null
									: (String) captureZohoData.get("Billing_State");
							if (state != null && !"".equals(state)) {
								ctx.put("StateCode", state);
							} else {
								flag = true;
							}
							String phone = (captureZohoData.isNull("Phone")) ? null
									: (String) captureZohoData.get("Phone");
							if (phone != null && !"".equals(phone)) {
								ctx.put("WorkPhone", phone);
							} else {
								flag = true;
							}
							boolean isNotSubProducer = false;
							Object objSubProducerRule = RuleUtils.executeRule(ctx,
									"AccountantRule.isNotSubproducerLoggedIn");
							if (objSubProducerRule != null && objSubProducerRule instanceof Boolean) {
								isNotSubProducer = (Boolean) objSubProducerRule;
							}

							if (isNotSubProducer) {
								String subProducer = (captureZohoData.isNull("Sub_Producer")) ? null
										: (String) captureZohoData.get("Sub_Producer");
								if (subProducer != null && !"".equals(subProducer)) {

									try {
										ctx.put("ProducerCompanyName", subProducer);
										String subProducerCode = "";
										List getSubProducerCodeList = (List) SqlResources.getSqlMapProcessor(ctx)
												.select("SqlStmts.UserStatementManualBoQueriesgetZohoSubProducerCode",
														ctx);
										if (getSubProducerCodeList != null && getSubProducerCodeList.size() > 0
												&& !getSubProducerCodeList.isEmpty()) {
											Map getSubProducerCodeMap = (Map) getSubProducerCodeList.get(0);
											subProducerCode = (getSubProducerCodeMap.get("ProducerCode") == null ? ""
													: getSubProducerCodeMap.get("ProducerCode").toString());
										}
										ctx.put("ProducerCode", subProducerCode);
									} catch (Exception e) {
										logger.error("Error in AccountUtils.getZOHOIndicationRecordsOfAccounts", e);
									}

									// } else {
									// flag = true;
								}
							}
							String effectiveDate = (captureZohoData.isNull("Ex_Date")) ? null
									: (String) captureZohoData.get("Ex_Date");
							if (effectiveDate != null && !"".equals(effectiveDate)) {
								SimpleDateFormat inSDF = new SimpleDateFormat("yyyy-MM-dd");
								SimpleDateFormat outSDF = new SimpleDateFormat("MM/dd/yyyy");
								String outDate = "";
								try {
									Date date = inSDF.parse(effectiveDate);
									outDate = outSDF.format(date);
								} catch (Exception ex) {
									logger.error("Error in AccountUtils.getZOHOIndicationRecordsOfAccounts", ex);
								}

								ctx.put("PolicyEffectiveDate", outDate);
								// } else {
								// flag = true;
							}
							String billingStreet = (captureZohoData.isNull("Billing_Street")) ? null
									: (String) captureZohoData.get("Billing_Street");
							if (billingStreet != null && !"".equals(billingStreet)) {
								ctx.put("Street", billingStreet);
							} else {
								flag = true;
							}
							String zipCode = (captureZohoData.isNull("Billing_Code")) ? null
									: (String) captureZohoData.get("Billing_Code");
							if (zipCode != null && !"".equals(zipCode)) {
								ctx.put("Zip", zipCode);
							} else {
								flag = true;
							}

							if (flag) {
								getZOHOIndicationRecordsOfContacts(ctx, flag);
							}
							break;
						}
						count = 0;
					}
				} else {
					// populateError(ctx, "ZohoError","No Data Found in Zoho CRM.");
					getZOHOIndicationRecordsOfContacts(ctx, false);
				}
			} else {
				populateError(ctx, "ZohoError", "Please Enter Insured Email.");
			}
		} catch (Exception e) {
			logger.error("Error in AccountUtils.getZOHOIndicationRecordsOfAccounts", e);
		}
	}

	public static JSONObject getZOHOIndicationRecordAccounts(Context ctx, String[] matchColumns,
			String[] matchColumnValues) {
		GetMethod get = null;
		try {

			String serachTargetURL = SystemProperties.getInstance().getString("appl.AccountantIns.zoho.modulesurl")
					+ "/Accounts/search?";
			// https://crm.zoho.com/crm/private/xml/Accounts/searchRecords?
			// https://www.zohoapis.com/crm/v2/Accounts/search?
			// https://www.zohoapis.com/crm/v2/Leads/search?
			String authtoken = "Zoho-oauthtoken " + ctx.get("isZohoAccessToken");// If you don't have a authtoken please
																					// refer this wiki
																					// https://zohocrmapi.wiki.zoho.com/using-authtoken.html
			String selectColumns = "Account_Name,Billing_Code,Billing_Street,Billing_State,Billing_City,County,Phone,Account_Email,Sub_Producer,NameClearStatus,NameClearType,NameClearDate,RequestedAppName,Deductible,Firm_Limit,Ex_Date";
			String criteria = "(";
			for (int criteriaCount = 0; criteriaCount < matchColumns.length; criteriaCount++) {
				if (!"(".equals(criteria) && criteriaCount > 0 && ctx.get(matchColumnValues[criteriaCount]) != null
						&& !"".equals(ctx.get(matchColumnValues[criteriaCount]))) {
					criteria += "OR";
				}
				if ("Phone".equals(matchColumns[criteriaCount]) && ctx.get(matchColumnValues[criteriaCount]) != null
						&& !"".equals(ctx.get(matchColumnValues[criteriaCount]))) {
					String workPhone = ctx.get(matchColumnValues[criteriaCount]).toString().replaceAll("\\D+", "");
					workPhone = workPhone.substring(0, 3) + "-" + workPhone.substring(3, 6) + "-"
							+ workPhone.substring(6, 10);
					criteria += "(" + matchColumns[criteriaCount] + ":equals:" + URLEncoder.encode(workPhone, "UTF-8")
							+ ")";
				} else if ("Account_Name".equals(matchColumns[criteriaCount])
						&& ctx.get(matchColumnValues[criteriaCount]) != null
						&& !"".equals(ctx.get(matchColumnValues[criteriaCount]))) {
					criteria += "(" + matchColumns[criteriaCount] + ":equals:"
							+ URLEncoder.encode(
									ctx.get(matchColumnValues[criteriaCount]).toString().replaceAll(" ", "_"), "UTF-8")
							+ ")";
				} else if (ctx.get(matchColumnValues[criteriaCount]) != null
						&& !"".equals(ctx.get(matchColumnValues[criteriaCount]))) {
					criteria += "(" + matchColumns[criteriaCount] + ":equals:"
							+ URLEncoder.encode(ctx.get(matchColumnValues[criteriaCount]).toString(), "UTF-8") + ")";
				}
			}
			criteria += ")";

			// Authorization: Zoho-oauthtoken d92d401c803988c5cb849d0b4215f52"
			// ----------------------------Fetch ZOHO Data ----------------------
			serachTargetURL += "fields=" + selectColumns + "&criteria=" + criteria;
			get = new GetMethod(serachTargetURL);
			get.setRequestHeader("Authorization", authtoken);

			HttpClient httpclient = SharedHttpClient.get();
			int result = httpclient.executeMethod(get);
			String responseBody = get.getResponseBodyAsString();

			{ if (logger.isDebugEnabled()) logger.debug(result); }
			{ if (logger.isDebugEnabled()) logger.debug("status     " + responseBody); }

			if (responseBody != null && !"".equals(responseBody)) {
				return new JSONObject(responseBody);
			}
		} catch (Exception e) {
			logger.error("Error in AccountUtils.getZOHOIndicationRecordAccounts", e);
		}
		return null;
	}

	public static void getZOHOIndicationRecordsOfContacts(Context ctx, boolean flag) {
		try {
			if (ctx.get("WorkPhone") != null && !"".equals(ctx.get("WorkPhone"))
					|| (ctx.get("AccountEmail") != null && !"".equals(ctx.get("AccountEmail")))) {

				String[] matchColumns = { "Email", "Phone" };
				String[] matchColumnValues = { "AccountEmail", "WorkPhone" };

				int count = 0;

				JSONObject captureZohoData = getZOHOIndicationRecordContacts(ctx, matchColumns, matchColumnValues);
				if (captureZohoData != null && !captureZohoData.isNull("data")) {
					JSONArray captureZohoDataList = (JSONArray) captureZohoData.get("data");
					for (int captureZohoDataCount = 0; captureZohoDataCount < captureZohoDataList
							.length(); captureZohoDataCount++) {
						captureZohoData = new JSONObject(captureZohoDataList.get(captureZohoDataCount).toString());

						if (!captureZohoData.isNull("Email") && captureZohoData.get("Email").toString()
								.equalsIgnoreCase(ctx.get("AccountEmail").toString())) {
							count++;
						}
						if (!captureZohoData.isNull("Phone")
								&& captureZohoData.get("Phone").toString().replaceAll("\\D+", "")
										.equalsIgnoreCase(ctx.get("WorkPhone").toString().replaceAll("\\D+", ""))) {
							count++;
						}
						if (count != 0) {

							String zohoAccountID = (captureZohoData.isNull("id")) ? null
									: (String) captureZohoData.get("id");
							String accountName = (captureZohoData.isNull("Account_Name")) ? null
									: (String) ((JSONObject) captureZohoData.get("Account_Name")).get("name");
							if (accountName != null && !"".equals(accountName) && "".equals(ctx.get("AccountName"))) {
								ctx.put("AccountName", accountName);
							}
							String phone = (captureZohoData.isNull("Phone")) ? null
									: (String) captureZohoData.get("Phone");
							if (phone != null && !"".equals(phone) && "".equals(ctx.get("WorkPhone"))) {
								ctx.put("WorkPhone", phone);
							}
							String accountEmail = (captureZohoData.isNull("Email")) ? null
									: (String) captureZohoData.get("Email");
							if (accountEmail != null && !"".equals(accountEmail)
									&& "".equals(ctx.get("AccountEmail"))) {
								ctx.put("AccountEmail", accountEmail);
							}
							String county = (captureZohoData.isNull("County")) ? null
									: (String) captureZohoData.get("County");
							if (county != null && !"".equals(county) && "".equals(ctx.get("CountyDesc"))) {
								ctx.put("CountyDesc", county);
							}
							String billingStreet = (captureZohoData.isNull("Mailing_Street")) ? null
									: (String) captureZohoData.get("Mailing_Street");
							if (billingStreet != null && !"".equals(billingStreet) && "".equals(ctx.get("Street"))) {
								ctx.put("Street", billingStreet);
							}
							String city = (captureZohoData.isNull("Mailing_City")) ? null
									: (String) captureZohoData.get("Mailing_City");
							if (city != null && !"".equals(city) && "".equals(ctx.get("City"))) {
								ctx.put("City", city);
							}
							String state = (captureZohoData.isNull("Mailing_State")) ? null
									: (String) captureZohoData.get("Mailing_State");
							if (state != null && !"".equals(state) && "".equals(ctx.get("StateCode"))) {
								ctx.put("StateCode", state);
							}
							String zipCode = (captureZohoData.isNull("Mailing_Zip")) ? null
									: (String) captureZohoData.get("Mailing_Zip");
							if (zipCode != null && !"".equals(zipCode) && "".equals(ctx.get("Zip"))) {
								ctx.put("Zip", zipCode);
							}

							if (!flag)
								getZOHOIndicationRecordsOfAccountsSecond(ctx);

							break;
						}
						count = 0;
					}
				} else {
					if (!flag)
						populateError(ctx, "ZohoError", "No Data Found in Zoho CRM.");
				}
			} else {
				populateError(ctx, "ZohoError", "Please Enter Insured Email.");
			}
		} catch (Exception e) {
			logger.error("Error in AccountUtils.getZOHOIndicationRecordsOfContacts", e);
		}
	}

	public static JSONObject getZOHOIndicationRecordContacts(Context ctx, String[] matchColumns,
			String[] matchColumnValues) {
		GetMethod get = null;
		try {

			String serachTargetURL = SystemProperties.getInstance().getString("appl.AccountantIns.zoho.modulesurl")
					+ "/Contacts/search?";
			// https://crm.zoho.com/crm/private/xml/Accounts/searchRecords?
			// https://www.zohoapis.com/crm/v2/Accounts/search?
			// https://www.zohoapis.com/crm/v2/Leads/search?
			String authtoken = "Zoho-oauthtoken " + ctx.get("isZohoAccessToken");// If you don't have a authtoken please
																					// refer this wiki
																					// https://zohocrmapi.wiki.zoho.com/using-authtoken.html
			String selectColumns = "Account_Name,Phone,Email,County,Mailing_Street,Mailing_City,Mailing_State,Mailing_Zip";
			String criteria = "(";
			for (int criteriaCount = 0; criteriaCount < matchColumns.length; criteriaCount++) {
				if (!"(".equals(criteria) && criteriaCount > 0 && ctx.get(matchColumnValues[criteriaCount]) != null
						&& !"".equals(ctx.get(matchColumnValues[criteriaCount]))) {
					criteria += "OR";
				}
				if ("Phone".equals(matchColumns[criteriaCount]) && ctx.get(matchColumnValues[criteriaCount]) != null
						&& !"".equals(ctx.get(matchColumnValues[criteriaCount]))) {
					String workPhone = ctx.get(matchColumnValues[criteriaCount]).toString().replaceAll("\\D+", "");
					workPhone = workPhone.substring(0, 3) + "-" + workPhone.substring(3, 6) + "-"
							+ workPhone.substring(6, 10);
					criteria += "(" + matchColumns[criteriaCount] + ":equals:" + URLEncoder.encode(workPhone, "UTF-8")
							+ ")";
				} else if ("Account_Name".equals(matchColumns[criteriaCount])
						&& ctx.get(matchColumnValues[criteriaCount]) != null
						&& !"".equals(ctx.get(matchColumnValues[criteriaCount]))) {
					criteria += "(" + matchColumns[criteriaCount] + ":equals:"
							+ URLEncoder.encode(
									ctx.get(matchColumnValues[criteriaCount]).toString().replaceAll(" ", "_"), "UTF-8")
							+ ")";
				} else if (ctx.get(matchColumnValues[criteriaCount]) != null
						&& !"".equals(ctx.get(matchColumnValues[criteriaCount]))) {
					criteria += "(" + matchColumns[criteriaCount] + ":equals:"
							+ URLEncoder.encode(ctx.get(matchColumnValues[criteriaCount]).toString(), "UTF-8") + ")";
				}
			}
			criteria += ")";

			// Authorization: Zoho-oauthtoken d92d401c803988c5cb849d0b4215f52"
			// ----------------------------Fetch ZOHO Data ----------------------
			serachTargetURL += "fields=" + selectColumns + "&criteria=" + criteria;
			get = new GetMethod(serachTargetURL);
			get.setRequestHeader("Authorization", authtoken);

			HttpClient httpclient = SharedHttpClient.get();
			int result = httpclient.executeMethod(get);
			String responseBody = get.getResponseBodyAsString();

			{ if (logger.isDebugEnabled()) logger.debug(result); }
			{ if (logger.isDebugEnabled()) logger.debug("status     " + responseBody); }

			if (responseBody != null && !"".equals(responseBody)) {
				return new JSONObject(responseBody);
			}
		} catch (Exception e) {
			logger.error("Error in AccountUtils.getZOHOIndicationRecordContacts", e);
		}
		return null;
	}

	public static void getZOHOIndicationRecordsOfAccountsSecond(Context ctx) {
		try {
			if (ctx.get("WorkPhone") != null && !"".equals(ctx.get("WorkPhone"))
					|| (ctx.get("AccountEmail") != null && !"".equals(ctx.get("AccountEmail")))) {

				String[] matchColumns = { "Account_Email", "Phone" };
				String[] matchColumnValues = { "AccountEmail", "WorkPhone" };

				int count = 0;

				JSONObject captureZohoData = getZOHOIndicationRecordAccounts(ctx, matchColumns, matchColumnValues);
				if (captureZohoData != null && !captureZohoData.isNull("data")) {
					JSONArray captureZohoDataList = (JSONArray) captureZohoData.get("data");
					for (int captureZohoDataCount = 0; captureZohoDataCount < captureZohoDataList
							.length(); captureZohoDataCount++) {
						captureZohoData = new JSONObject(captureZohoDataList.get(captureZohoDataCount).toString());

						if (!captureZohoData.isNull("Account_Email") && captureZohoData.get("Account_Email").toString()
								.equalsIgnoreCase(ctx.get("AccountEmail").toString())) {
							count++;
						}
						if (!captureZohoData.isNull("Phone")
								&& captureZohoData.get("Phone").toString().replaceAll("\\D+", "")
										.equalsIgnoreCase(ctx.get("WorkPhone").toString().replaceAll("\\D+", ""))) {
							count++;
						}
						if (count != 0) {

							String zohoAccountID = (captureZohoData.isNull("id")) ? null
									: (String) captureZohoData.get("id");
							String county = (captureZohoData.isNull("County")) ? null
									: (String) captureZohoData.get("County");
							if (county != null && !"".equals(county)) {
								ctx.put("CountyDesc", county);
							}
							String city = (captureZohoData.isNull("Billing_City")) ? null
									: (String) captureZohoData.get("Billing_City");
							if (city != null && !"".equals(city)) {
								ctx.put("City", city);
							}
							String accountEmail = (captureZohoData.isNull("Account_Email")) ? null
									: (String) captureZohoData.get("Account_Email");
							if (accountEmail != null && !"".equals(accountEmail)) {
								ctx.put("AccountEmail", accountEmail);
							}
							String accountName = (captureZohoData.isNull("Account_Name")) ? null
									: (String) captureZohoData.get("Account_Name");
							if (accountName != null && !"".equals(accountName)) {
								ctx.put("AccountName", accountName);
							}
							String state = (captureZohoData.isNull("Billing_State")) ? null
									: (String) captureZohoData.get("Billing_State");
							if (state != null && !"".equals(state)) {
								ctx.put("StateCode", state);
							}
							String phone = (captureZohoData.isNull("Phone")) ? null
									: (String) captureZohoData.get("Phone");
							if (phone != null && !"".equals(phone)) {
								ctx.put("WorkPhone", phone);
							}
							boolean isNotSubProducer = false;
							Object objSubProducerRule = RuleUtils.executeRule(ctx,
									"AccountantRule.isNotSubproducerLoggedIn");
							if (objSubProducerRule != null && objSubProducerRule instanceof Boolean) {
								isNotSubProducer = (Boolean) objSubProducerRule;
							}

							if (isNotSubProducer) {
								String subProducer = (captureZohoData.isNull("Sub_Producer")) ? null
										: (String) captureZohoData.get("Sub_Producer");
								if (subProducer != null && !"".equals(subProducer)) {

									try {
										ctx.put("ProducerCompanyName", subProducer);
										String subProducerCode = "";
										List getSubProducerCodeList = (List) SqlResources.getSqlMapProcessor(ctx)
												.select("SqlStmts.UserStatementManualBoQueriesgetZohoSubProducerCode",
														ctx);
										if (getSubProducerCodeList != null && getSubProducerCodeList.size() > 0
												&& !getSubProducerCodeList.isEmpty()) {
											Map getSubProducerCodeMap = (Map) getSubProducerCodeList.get(0);
											subProducerCode = (getSubProducerCodeMap.get("ProducerCode") == null ? ""
													: getSubProducerCodeMap.get("ProducerCode").toString());
										}
										ctx.put("ProducerCode", subProducerCode);
									} catch (Exception e) {
										logger.error("Error in AccountUtils.getZOHOIndicationRecordsOfAccountsSecond", e);
									}
								}
							}
							String effectiveDate = (captureZohoData.isNull("Ex_Date")) ? null
									: (String) captureZohoData.get("Ex_Date");
							if (effectiveDate != null && !"".equals(effectiveDate)) {
								SimpleDateFormat inSDF = new SimpleDateFormat("yyyy-MM-dd");
								SimpleDateFormat outSDF = new SimpleDateFormat("MM/dd/yyyy");
								String outDate = "";
								try {
									Date date = inSDF.parse(effectiveDate);
									outDate = outSDF.format(date);
								} catch (Exception ex) {
									logger.error("Error in AccountUtils.getZOHOIndicationRecordsOfAccountsSecond", ex);
								}

								ctx.put("PolicyEffectiveDate", outDate);
							}
							String billingStreet = (captureZohoData.isNull("Billing_Street")) ? null
									: (String) captureZohoData.get("Billing_Street");
							if (billingStreet != null && !"".equals(billingStreet)) {
								ctx.put("Street", billingStreet);
							}
							String zipCode = (captureZohoData.isNull("Billing_Code")) ? null
									: (String) captureZohoData.get("Billing_Code");
							if (zipCode != null && !"".equals(zipCode)) {
								ctx.put("Zip", zipCode);
							}
							break;
						}
						count = 0;
					}
				}
			}
		} catch (Exception e) {
			logger.error("Error in AccountUtils.getZOHOIndicationRecordsOfAccountsSecond", e);
		}
	}

	public static String getInsuredAccountEmail(Context ctx) {
		logger.debug("going to fetch insured AccountEmail");
		String emailID = "";
		try {

			if (ctx.get("AccountEmail") != null)
				emailID = ctx.get("AccountEmail").toString();
			try {
				Object obj = SqlResources.getSqlMapProcessor(ctx)
						.findByKey("SqlStmts.UserStatementManualBoQueriesgetFirmAddressDetails", ctx);
				if (obj != null && obj instanceof Map) {
					ctx.putAll((Map) obj);
				}
			} catch (Exception e) {
				logger.error("Error occured while fetching secondary email address of firm :" + e, e);
			}

			if (ctx.get("secondaryEmail") != null)
				emailID = emailID + "," + ctx.get("secondaryEmail").toString();

		} catch (Exception e) {
			logger.error("Error occured while  fetching insured AccountEmail", e);
		}
		return emailID;
	}
//////----------------------- ZOHO END

	public void isReferRuleTriggeredNewApp(IContext ctx) {
		String IsReferRuleTriggered = "";
		try {
			new SetParametersForStoredProcedures().setParametersInContext((Context) ctx,
					"PolicyKey,AccountKey,AccountID,AddressKey,VersionSequence,PolicyStatusKey,LastUpdateUserID");
			ctx.put("AccountKey", Integer.valueOf(ctx.get("AccountKey").toString()));
			Object obj = SqlResources.getSqlMapProcessor(ctx).findByKey("rulepolicytransaction.AutoQuote_ACC", ctx);
			Map keysMap = null;
			if (obj != null && obj instanceof Map) {
				keysMap = (Map) obj;
				ctx.putAll(keysMap);
			}

			IsReferRuleTriggered = ctx.get("IsReferRuleTriggered") != null
					&& !ctx.get("IsReferRuleTriggered").equals(HtmlConstants.EMPTY)
							? ctx.get("IsReferRuleTriggered").toString()
							: "N";
			if ("Y".equals(IsReferRuleTriggered)) {
				ctx.put("IsFirmReferred", "Y");
			} else {
				ctx.put("IsFirmReferred", "N");
			}
			// ctx.put("IsFirmReferred", "Y");
			SqlResources.getSqlMapProcessor(ctx).update("SqlStmts.UserStatementsaveAccountstmtsupdateFirmReferred",
					ctx);

		} catch (Exception e) {
			logger.error("error occured while executing isReferRuleTriggeredNewApp method" + e, e);
		}

	}
	public void sendAutoQuoteMail(IContext ctx) {
		try
		{
			Object obj = SqlResources.getSqlMapProcessor(ctx).findByKey("SqlStmts.UserStatementManualBoQueriesgetAccountDetailsForCompleteAppLink", ctx);
	
			if (obj != null && obj instanceof Map) {
				Map map = (Map) obj;
				ctx.putAll(map);
			}
			
			obj = SqlResources.getSqlMapProcessor(ctx).findByKey("SqlStmts.UserStatementManualBoQueriesgetPremiumDetailsForAutoQuote", ctx);
	
			if (obj != null && obj instanceof Map) {
				Map map = (Map) obj;
				ctx.putAll(map);
			}
	
			String dstId =SystemProperties.getInstance().getString("appl." + ctx.getProject() + ".autoquote.to.email");
			// Do not send quote letter mail to insured if producer code is
			// there
			
	//			dstId = getEmailID(ctx);
			// String cc_role_desc =
			// SystemProperties.getInstance().getString("mail.admin.cc.addr");
			if ("".equals(dstId))
				return;
	//		String bccAddress = "";
	//		bccAddress = SystemProperties.getInstance().getString("appl." + ctx.getProject() + ".autoquote.cc.email");
	
			logger.debug("Mail is going---- ");
			MailSender mail = new MailSender();
			mail.setToAdd(dstId);
	//		mail.setBccAdd(bccAddress);
			mail.setSubject( ctx.get("QuoteNumber").toString()+", "+ ctx.get("AccountName").toString()+" has been been handled by the auto quote functionality");
			mail.setContentType("text/html");
			mail.setBody(generateAutoQuoteMailBody(ctx));
			mail.sendMail((Context) ctx);
			logger.debug("Mail has sent---- ");
			SqlResources.getSqlMapProcessor(ctx).update("SqlStmts.UserStatementManualBoQueriesUpdateAutoQuoteEmailFlag",ctx);
			
			try{
				AccountUtils.updateZOHORecords((Context) ctx);
			} catch(Exception e) {
				logger.error("Exception occured during ZOHO update "+e, e);
			}
		
		}
		catch(Exception e)
		{
			logger.error("exception occured while sending Auto quote email for "+ctx.get("PolicyKey")+"\t"+e.getMessage(), e);
		}

	}
	
	private String generateAutoQuoteMailBody(IContext ctx) throws Exception {
		
		String msg = "";
		msg = msg + "<table>";
		msg = msg + "<tr>";
		msg = msg + "<td>";
		msg=msg+"Hi Team,<br/><br/>";
		msg = msg + ctx.get("QuoteNumber").toString()+", "+ ctx.get("AccountName").toString()+" has been been handled by the auto quote functionality." + "<br/><br/>";

		msg = msg + "<b>Limits			:	</b>"+ctx.get("LimitAmount").toString() + "<br/>";
		msg = msg + "<b>Deductibles	:	</b>"+ctx.get("DeductibleAmount").toString() + "<br/>";
		msg = msg + "<b>Inside/Outside	:	</b>"+ctx.get("ClaimExpensesTypeText").toString() + "<br/>";
		msg = msg + "<b>Claim/Aggregate:	</b>"+ctx.get("IsClaimOptionType").toString() + "<br/>";
		msg = msg + "<b>Premium Amount	:	</b>"+ctx.get("InvoicedPremium").toString() + "<br/>";
		msg = msg +  "<br/><br/> ";

		msg=msg+"Best Regards <br/>";
		msg = msg + "The Protexure Team." + "<br/>";

		msg = msg + "</td>";
		msg = msg + "</tr>";
		msg = msg + "</table>";

		return msg;
	}
// End Of class
	
	public static void ValidatePremiumMods(Context ctx)
	{
		double priorInvoicedPremium=0.0,invoicedPremium=0.0;
		try
		{
			invoicedPremium =ctx.get("InvoicedPremium")!=null && !ctx.get("InvoicedPremium").equals(HtmlConstants.EMPTY) ?Double.parseDouble(ctx.get("InvoicedPremium").toString()):0.0;
			List previousPolicyKey= (List) SqlResources.getSqlMapProcessor(ctx).select("SqlStmts.UserStatementdroolQueriesgetPreviousPolicyKey",ctx);
			ctx.putAll((Map) previousPolicyKey.get(0));
			Context newCtx=new Context();
			newCtx.setProject(ctx.getProject());
			newCtx.put("PolicyKey",ctx.get("PreviousPolicykey"));
			newCtx.put("VersionSequence",ctx.get("previousVersionSequence"));
			
			Object obj = SqlResources.getSqlMapProcessor(newCtx).findByKey("SqlStmts.UserStatementManualBoQueriesgetPriorPolicyPremium", newCtx);
			if (obj != null) {
				Map map = (Map) obj;
				priorInvoicedPremium =map.get("InvoicedPremium")!=null && !map.get("InvoicedPremium").equals(HtmlConstants.EMPTY) ?Double.parseDouble(map.get("InvoicedPremium").toString()):0.0;
				
			}
			if(invoicedPremium<priorInvoicedPremium)
			{
				SqlResources.getSqlMapProcessor(ctx).update("Firm.UpdateMods_AutoQuote",ctx);
				com.userbo.Rating.processQuotesList((IContext)ctx);
			}
			
		}
		catch(Exception e)
		{
			logger.error("exception occured while adding mods to auto quote premium. "+e.getMessage(), e);
		}
	}
	public static boolean validateIfPolicynumberExists(Context ctx)
	{ 
		boolean result=true;
		try {
			
			new SetParametersForStoredProcedures().setParametersInContext(ctx, "newPolicyNumber");
			List validatePolicyData =(List)SqlResources.getSqlMapProcessor(ctx).select("Policy.ValidatePolicynumber_P", ctx);
			
			if(validatePolicyData!=null && validatePolicyData.size()>0)
			ctx.putAll((Map) validatePolicyData.get(0));
			result=ctx.get("result")!=null && !ctx.get("result").equals(HtmlConstants.EMPTY)?Boolean.parseBoolean(ctx.get("result").toString()):false;
			
		} catch (Exception e) {
			logger.error("Error in AccountUtils.validateIfPolicynumberExists", e);
		}
		
		return result;
	}

public static void saveEstimateFormData(Context ctx)  {
	
	try {
		
		
		saveEstimateFormAOP(ctx);
		saveRevenueForEstimateForm(ctx);
		new SetParametersForStoredProcedures().setParametersInContext(ctx, "LimitSequence,DeductibleSequence,IsClaimExpensesType,IsClaimOptionType,IsDollarDefense,InsuranceCompanyNamePross,PolicyExpirationDatePross,ProInsPremium,IsPriorActDateFull,PriorActDatePross,FirmYear,hasAnyFirmPersonnelReported,AccountName,ContactPerson,Address1,StateCode,CountyDesc,City,Zip,WorkPhone,AccountEmail,LastUpdateTimeStamp,addAttorneys,IsSignedEngagement,outputList,MarketableProductKey,LastUpdateTimeStamp,LastUpdateUserID");
		SqlResources.getSqlMapProcessor(ctx).update("EstimateFormGeneralInfo.InsertUpdateProtexureFormGeneralData_ACC_p", ctx);

		Object obj = SqlResources.getSqlMapProcessor(ctx).findByKey("SqlStmts.UserStatementManualBoQueriesgetEstimateFormGeneralInfoDetails", ctx);
		if(obj != null && obj instanceof Map)
		ctx.putAll((Map)obj);
		
		sendProtexureEstimateForm(ctx);


	} catch (Exception e) {
		logger.error("error occured while executing isReferRuleTriggeredNewApp method" + e, e);
	}
	
}
public static void saveRevenueForEstimateForm(Context ctx)  {
	logger.debug("preparing data of gross revenue to save");
	Context newCtx=new Context();
	newCtx.setProject(ctx.getProject());
//	newCtx.putAll(ctx);
	Map map =null,reqMap=null; 
	Object objAmount=null;
	List dataList=null;
	try
	{
		
	List inputList=new ArrayList();
	  if(ctx.get("GrossRevenue_list_01") != null &&   ctx.get("GrossRevenue_list_01") instanceof List)
      {
		  dataList = (List) ctx.get("GrossRevenue_list_01");
			 if(dataList.size()>0)
		        {
					for (int i = 0; i < dataList.size(); i++) {
						map = (Map) dataList.get(i);
						reqMap=new HashMap<>();
						reqMap.put("GrossRevenueKey", ctx.get("GrossRevenueKey" + "_" + i));
						reqMap.put("YearEndDate", ctx.get("YearEndDate" + "_" + i));
						 objAmount = removeAmountFormat(ctx.get("Amount" + "_" + i)!=null && !ctx.get("Amount" + "_" + i).equals(HtmlConstants.EMPTY) ?ctx.get("Amount" + "_" + i):"0");
						reqMap.put("Amount", objAmount);
						inputList.add(reqMap);
					}
		        }
			if(inputList != null && inputList.size()>0)
			{
				
				newCtx.put("inputList", inputList);
				AccountUtils.convertListDataToXML(newCtx,"inputList","outputList");
				ctx.put("outputList",newCtx.get("outputList"));
			}
      }
	}
	catch(Exception e)
	{
		logger.error("Exception occured while preparing data of gross revenue to save "+e.getMessage(), e);
	}
	
		
	
}
public static void validateEstimateFormAopPercentage(Context ctx) throws Exception {
	
	/*
	 * if (ctx.get("StateCode") == null ||
	 * "".equals(ctx.get("StateCode").toString())) { populateError(ctx, "StateCode",
	 * "Please select any state."); }
	 */	List aopList = SqlResources.getSqlMapProcessor(ctx).select("AOP.GetAOPFieldsIndication_p", ctx);
	Context ctx1;
	int total = 0, percentage = 0;
	if (aopList != null) {

		try {
			Map map = new HashMap();
			for (int i = 0; i < aopList.size(); i++) {
				map = (HashMap) aopList.get(i);
				String percentageValue = ctx.get("AOP_Percentage_" + map.get("AOPKey").toString()) == null ? null
						: ctx.get("AOP_Percentage_" + map.get("AOPKey").toString()).toString().trim();

				if (percentageValue == null || "".equals(percentageValue) || "0".equals(percentageValue))
					percentageValue = "0";
				percentage = Integer.parseInt(percentageValue);
				total = total + percentage;
			}
			if (total > 100 || total < 100) {
				populateError(ctx, "totalPercentageError",
						"The total AOP percentage is required to equal 100%, current total is " + total + "%");
			}
		} catch (Exception e) {
			logger.error("Error in AccountUtils.validateEstimateFormAopPercentage", e);
		}
	}
}

public static void saveEstimateFormAOP(Context ctx)
{
	logger.debug("Saving Estimate form AOPs.");
	try
	{	
		
		List aopList = SqlResources.getSqlMapProcessor(ctx).select("AOP.findAll", ctx);
		List inputList=new ArrayList();
		Map reqMap = null;
	    Context newCtx=new Context();
		newCtx.setProject(ctx.getProject());
		newCtx.putAll(ctx);
		if (aopList != null) {
			
			try {
				Map map = new HashMap();				
				for (int i = 0; i < aopList.size(); i++) {
					map = (HashMap) aopList.get(i);
					reqMap = new HashMap();
					String percentageValue = ctx.get("AOP_Percentage_" + map.get("AOPKey").toString()) == null ? null: ctx.get("AOP_Percentage_" + map.get("AOPKey").toString()).toString().trim();
					
					if (percentageValue == null || "".equals(percentageValue) || "0".equals(percentageValue))
						percentageValue = "0";
						reqMap.put("AOPKey", map.get("AOPKey"));
						reqMap.put("percentageValue", percentageValue);
						
					
						if (reqMap.get("AOPKey").toString().equals("20"))
						{

							if (ctx.get("AOPCommentDesc_20") != null)
								reqMap.put("AOPCommentDesc",ctx.get("AOPCommentDesc_20"));
						}
					
						 
					inputList.add(reqMap);
				}
				if(inputList != null && inputList.size()>0)
				{
				newCtx.put("inputList", inputList);
				AccountUtils.convertListDataToXML(newCtx,"inputList","outputList");
				ctx.put("outputList1",newCtx.get("outputList"));
				}
				 logger.debug(ctx.get("outputList"));
			} catch (Exception e) {
				logger.error("Exception Occured while saving Estimate form AOP"+e.getMessage(), e);			}
		}
		
       

	}
	catch(Exception e)
	{
		logger.error("problem occured in retainingAop method", e);
	}
}

public static void sendProtexureEstimateForm(Context ctx){
	try{
		String ccAddress=SystemProperties.getInstance().getString("mail.admin.cc.address");
		String emailID = getProtexureEstimateEmailID(ctx);
		if ("".equals(emailID))
			return;
		logger.debug("Protexure Estimate Mail is going---- ");
		MailSender mail = new MailSender();
		mail.setToAdd(emailID);
		mail.setCcAdd(ccAddress);
		mail.setSubject("Protexure Estimate Form");
		mail.setContentType("text/html");
		mail.setBody(generateProtexureEstimateBody(ctx));
		mail.sendMail((Context) ctx);
		logger.debug("Mail has sent---- ");
	}catch(Exception e){
		logger.error("Error in AccountUtils.sendProtexureEstimateForm", e);
	}
}


	public static String getProtexureEstimateEmailID(Context ctx) throws Exception {
		String stateCode = ctx.get("StateCode").toString();
		String emailID = "";
		String firstReviewer = "";
		String productionEnv = "N";
		try {
			productionEnv = SystemProperties.getInstance().getString("appl." + ctx.getProject() + ".environment.production");
		} catch (Exception e) {
			logger.error("error in geeting production environment", e);
		}
		if ("N".equals(productionEnv)){
			emailID = SystemProperties.getInstance().getString("appl." + ctx.getProject() + ".admin.email");
			{ if (logger.isDebugEnabled()) logger.debug("email id------->"+emailID); }
		} else { 
			String forDirectFRRS = SystemProperties.getInstance().getString("appl.AccountantIns.zoho.fordirectrenew.firstreviewer.RS");
			String forDirectFRQB = SystemProperties.getInstance().getString("appl.AccountantIns.zoho.fordirectnew.firstreviewer.QB");
//			String forDirectFRDK = SystemProperties.getInstance().getString("appl.AccountantIns.zoho.fordirectnew.firstreviewer.DK");
//			String forDirectFRSH = SystemProperties.getInstance().getString("appl.AccountantIns.zoho.fordirectnew.firstreviewer.SH");
//			String forDirectFRCC = SystemProperties.getInstance().getString("appl.AccountantIns.zoho.fordirectnew.firstreviewer.CC");

			if("CT#GA#FL#IL".contains(stateCode)){
				firstReviewer = forDirectFRRS;//Ryan Schwartz
			} else if("CA#CO#IN#TX#WA#ID#AZ#MA#NJ#MN#WI#NV#PA#AK#KY#MT#ND#NM#SD#WY#MD#ME#NY#NH#VT#KS#VA#TN#UT#NC#SC#OH#MO#MI#AL#AR#HI#IA#MS#NE#OK#DE#DC#LA#WV#RI".contains(stateCode)){
				firstReviewer = forDirectFRQB;//Quiana
//			} else if("CT#GA#FL#KS#VA#TN#UT#NC#SC#OH#IL#MO#MI#AL#AR#HI#IA#MS#NE#OK#DE#DC#LA#WV#RI".contains(stateCode)){
//				firstReviewer = forDirectFRDK;//Dane
//			} else if("AZ#MA#MI#NV#NC#PA#SC#DE#DC#MD#ME#NH#RI#VT".contains(stateCode)){
//				firstReviewer = forDirectFRSH;//Sharon
//			} else if("OH#IL#MN#MO#NJ#WI#LA#NY#WV#MI#NV#PA#NH#RI#VT".contains(stateCode)){
//				firstReviewer = forDirectFRCC;//Chaz
			}
			
			ctx.put("agentname", firstReviewer);
			List getFirstReviewerEmail = (List)SqlResources.getSqlMapProcessor(ctx).select("SqlStmts.UserStatementManualBoQueriesgetProtexureEstimateEmail", ctx);
    		if(getFirstReviewerEmail != null && getFirstReviewerEmail.size() > 0 && !getFirstReviewerEmail.isEmpty()){
    			Map getFirstReviewerList = (Map)getFirstReviewerEmail.get(0);
    			emailID = (getFirstReviewerList.get("AgentEmail") == null ? "" : getFirstReviewerList.get("AgentEmail").toString().trim());
    		}
		}
		return emailID;
	}
	private static String generateProtexureEstimateBody(Context ctx) throws Exception {

		 

	       

		 

        String msg = "<table cellpadding=\"5\" cellspacing=\"5\">";

 

        msg = msg + "<tr><td colspan=\"3\">Hi" + "<br/><br/></td></tr>";

 

        msg = msg + "<tr><td colspan=\"3\">The following Firm has completed their application" + "<br/><br/></td></tr>";

 

        msg = msg + "<tr><td colspan=\"2\" style=\"width:30%\">Law firm Name </td><td><b>:</b>&nbsp;&nbsp;"+ctx.get("AccountName")+ "</td></tr>";

 

        msg = msg + "<tr><td colspan=\"2\" style=\"width:30%\">Contact Person </td><td><b>:</b>&nbsp;&nbsp;"+ctx.get("ContactPerson")+ "</td></tr>";

 

        msg = msg + "<tr><td colspan=\"2\" style=\"width:30%\">Address  </td><td><b>:</b>&nbsp;&nbsp;"+ctx.get("Address1")+ "</td></tr>";

 

        msg = msg + "<tr><td colspan=\"2\" style=\"width:30%\">State  </td><td><b>:</b>&nbsp;&nbsp;"+ctx.get("StateCode")+ "</td></tr>";

 

        msg = msg + "<tr><td colspan=\"2\" style=\"width:30%\">County  </td><td><b>:</b>&nbsp;&nbsp;"+ctx.get("CountyDesc")+ "</td></tr>";

 

        msg = msg + "<tr><td colspan=\"2\" style=\"width:30%\">City  </td><td><b>:</b>&nbsp;&nbsp;"+ctx.get("City")+ "</td></tr>";

 

        msg = msg + "<tr><td colspan=\"2\" style=\"width:30%\">Zip Code  </td><td><b>:</b>&nbsp;&nbsp;"+ctx.get("Zip")+ "</td></tr>";

 

        msg = msg + "<tr><td colspan=\"2\" style=\"width:30%\">Phone Number  </td><td><b>:</b>&nbsp;&nbsp;"+ctx.get("WorkPhone")+ "</td></tr>";

 

        msg = msg + "<tr><td colspan=\"2\" style=\"width:30%\">Email  </td><td><b>:</b>&nbsp;&nbsp;"+ctx.get("AccountEmail")+ "</td></tr>";

 

        msg = msg + "<tr><td colspan=\"2\" style=\"width:30%\">Limit </td><td><b>:</b>&nbsp;&nbsp;"+ctx.get("AggregateLimit")+"/"+ctx.get("OccuranceLimit")+ "</td></tr>";

 

        msg = msg + "<tr><td colspan=\"2\" style=\"width:30%\">Deductible </td><td><b>:</b>&nbsp;&nbsp;"+ctx.get("OccuranceDeductible")+"</td></tr>";

 

        msg = msg + "<tr><td colspan=\"2\" style=\"width:30%\">Expenses Outside Limit </td><td><b>:</b>&nbsp;&nbsp;"+ctx.get("IsClaimExpensesType")+ "</td></tr>";

        if(ctx.get("IsDollarDefense")!=null)

        msg = msg + "<tr><td colspan=\"2\" style=\"width:30%\">IsDollarDefense </td><td><b>:</b>&nbsp;&nbsp;"+ctx.get("IsDollarDefense")+ "</td></tr>";

 

        msg = msg + "<tr><td colspan=\"2\" style=\"width:30%\">Current Carrier </td><td><b>:</b>&nbsp;&nbsp;"+ctx.get("InsuranceCompanyNamePross")+ "</td></tr>";

 

        msg = msg + "<tr><td colspan=\"2\" style=\"width:30%\">Current policy expiration date </td><td><b>:</b>&nbsp;&nbsp;"+ctx.get("PolicyExpirationDatePross")+ "</td></tr>";

 

        msg = msg + "<tr><td colspan=\"2\" style=\"width:30%\">Current Premium </td><td><b>:</b>&nbsp;&nbsp;"+ctx.get("ProInsPremium")+ "</td></tr>";

 

        msg = msg + "<tr><td colspan=\"2\" style=\"width:30%\">Has Prior Acts Date  </td><td><b>:</b>&nbsp;&nbsp;"+ctx.get("IsPriorActDateFull")+ "</td></tr>";

        if(ctx.get("PriorActDatePross")!=null)

        msg = msg + "<tr><td colspan=\"2\" style=\"width:30%\">What is your prior acts date?  </td><td><b>:</b>&nbsp;&nbsp;"+ctx.get("PriorActDatePross")+ "</td></tr>";

        if(ctx.get("FirmYear")!=null)
        msg = msg + "<tr><td colspan=\"2\" style=\"width:30%\">If Full, pleaser provide number of years </td><td><b>:</b>&nbsp;&nbsp;"+ctx.get("FirmYear")+ "</td></tr>";

        msg = msg + "<tr><td colspan=\"3\" style=\"width:30%\">Within the past five (5) years has any firm personnel reported claims or been the subject of a disciplinary matter? </td><td><b>:</b>&nbsp;&nbsp;"+ctx.get("hasAnyFirmPersonnelReported")+ "</td></tr>";

        msg = msg + "<tr><td colspan=\"3\" style=\"width:30%\">Do you obtain a signed engagement letter or written agreement updated annually outlining the services the Firm will perform for each of the Firm's clients? </td><td><b>:</b>&nbsp;&nbsp;"+ctx.get("IsSignedEngagement")+ "</td></tr>";
        
        msg = msg +"<tr><td colspan=\"3\"><b>Revenue</b><br/></td></tr>";
        
        msg = msg +"<tr><td colspan=\"3\"><table>";
        
        List getEstimateFormGrossRevenueList = (List)SqlResources.getSqlMapProcessor(ctx).select("SqlStmts.UserStatementManualBoQueriesgetEstimateformGrossRevenueDetails", ctx);

        if(getEstimateFormGrossRevenueList != null && getEstimateFormGrossRevenueList.size() > 0 && !getEstimateFormGrossRevenueList.isEmpty()){

             for (int i = 0; i < getEstimateFormGrossRevenueList.size(); i++) {

                   Map getEstimateFormGrossRevenueMap = (Map)getEstimateFormGrossRevenueList.get(i);

                   msg = msg + "<tr><td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + getEstimateFormGrossRevenueMap.get("GrossRevenueDesc")+" </td><td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+getEstimateFormGrossRevenueMap.get("YearEndDate")+ "</td><td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+getEstimateFormGrossRevenueMap.get("Amount")+ "</td></tr>";
             }

       }
        msg = msg +"</table></td></tr><br>";
        msg = msg +"<tr><td colspan=\"3\"><b>Area of Practice</b><br/><br/></td></tr>";
        
        List getEstimateFormAOPDetailsList = (List)SqlResources.getSqlMapProcessor(ctx).select("SqlStmts.UserStatementManualBoQueriesgetProtexureEstimateEmailAOPDetail", ctx);

         if(getEstimateFormAOPDetailsList != null && getEstimateFormAOPDetailsList.size() > 0 && !getEstimateFormAOPDetailsList.isEmpty()){

              for (int i = 0; i < getEstimateFormAOPDetailsList.size(); i++) {

                    Map getEstimateFormAOPDetailsMap = (Map)getEstimateFormAOPDetailsList.get(i);

                    msg = msg + "<tr><td colspan=\"2\" style=\"width:30%\">" + getEstimateFormAOPDetailsMap.get("AOPDesc")+" </td><td><b>:</b>&nbsp;&nbsp;"+getEstimateFormAOPDetailsMap.get("PercentageValue")+ "</td></tr>";
              	}
        }
        msg = msg + "</table>";

        return msg;

              }
	
	
public static void validateEstimateFormRevenueDetail(Context ctx)  {
		try
		{

		
		if (ctx.get("YearEndDate_0") == null && ctx.get("Amount_0") == null) {
			populateError(ctx, "estimate_year", "Fiscal Year End date and amount are required");
		
		} 
		else {

			if ((ctx.get("Amount_0") != null && "".equals(ctx.get("Amount_0").toString().trim()))
					&& (ctx.get("YearEndDate_0") != null && "".equals(ctx.get("YearEndDate_0").toString().trim())))
			{
				populateError(ctx, "estimate_year", "Fiscal Year End date and amount are required");
			
			} 
//			else if (ctx.get("YearEndDate_1") == null || "".equals(ctx.get("YearEndDate_1").toString().trim()))
//			{
//				populateError(ctx, "estimate_year", "One Prior Fiscal Year End date is required");
//				flag = true;
//			} 
//			else if (ctx.get("YearEndDate_2") == null || "".equals(ctx.get("YearEndDate_2").toString().trim()))
//			{
//				populateError(ctx, "estimate_year", "Two Prior Fiscal Year End date is required");
//				flag = true;
//			} 
			else if (ctx.get("Amount_0") == null || "".equals(ctx.get("Amount_0").toString().trim()))
			{
				populateError(ctx, "estimate_year", "Last Completed Year Amount is required");
				
			} 
//			else if (ctx.get("Amount_1") == null || "".equals(ctx.get("Amount_1").toString().trim())) 
//			{
//				populateError(ctx, "estimate_year", "One Year Prior Amount is required");
//				
//			}
//			else if (ctx.get("Amount_2") == null || "".equals(ctx.get("Amount_2").toString().trim())) 
//			{
//				populateError(ctx, "estimate_year", "Two Years Prior Amount is required");
//				flag = true;
//			}
		}
		boolean flag=false;
		Object obj = RuleUtils.executeRule(ctx, "AccountantRule.isInetErrorListNotEmpty");
		if (obj != null && obj instanceof Boolean) {
			flag = (Boolean) obj;

			if (flag) 
				populateError(ctx, "estimateFormPage", "There are issues with the data being submitted. Please correct any errors noted below and resubmit.");
			
		}
	
		}
		catch(Exception e)
		{
			logger.error("Error in AccountUtils.validateEstimateFormRevenueDetail", e);
		}
		
	}


}
