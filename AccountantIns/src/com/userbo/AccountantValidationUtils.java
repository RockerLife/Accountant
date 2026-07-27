package com.userbo;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.jdom.Element;

import com.mail.MailSender;
import com.manage.managemetadata.functions.commonfunctions.RuleUtils;
import com.ormapping.ibatis.SqlResources;
import com.util.Context;
import com.util.HtmlConstants;
import com.util.IContext;
import com.util.InetLogger;
import com.util.SystemProperties;
import com.util.XMLUtils;

public class AccountantValidationUtils {
	final String key = "Acc";
	private static InetLogger logger = InetLogger
			.getInetLogger(AccountUtils.class);

	public static void checkIfRenewIsReffered(IContext ctx) throws Exception {

		boolean isRenewPolicyReferred = false;

		Object obj = SqlResources
				.getSqlMapProcessor(ctx)
				.select(
						"SqlStmts.UserStatementrenewapplicationgetAllPolicyOfAnAccount",
						ctx);
		if (obj != null && obj instanceof List) {
			Context previousContext = new Context();
			List policyList = (List) obj;

			if (policyList != null && policyList.size() > 0) {
				Map policyMap = (Map) policyList.get(policyList.size() - 1);
				previousContext.put("PolicyKey", policyMap.get("PolicyKey"));
			}

			if (checkIfAccountNameIsChanged(ctx))
				isRenewPolicyReferred = true;

			Object objFirm = SqlResources.getSqlMapProcessor(ctx).findByKey(
					"Firm.findByKey", ctx);
			if (objFirm != null && objFirm instanceof Map) {

				Map objMap = (Map) objFirm;

				String IsAnyFirmMeargedOrAcquired = objMap
						.get("IsAnyFirmMeargedOrAcquired") != null ? objMap
						.get("IsAnyFirmMeargedOrAcquired").toString() : "";
				if (IsAnyFirmMeargedOrAcquired.equals("Y"))
					isRenewPolicyReferred = true;

				String IsControlClientFunds = objMap
						.get("IsControlClientFunds") != null ? objMap.get(
						"IsControlClientFunds").toString() : "";
				if (IsControlClientFunds.equals("Y"))
					isRenewPolicyReferred = true;

				String IsServedAsTrustee = objMap.get("IsServedAsTrustee") != null ? objMap
						.get("IsServedAsTrustee").toString()
						: "";
				if (IsServedAsTrustee.equals("Y"))
					isRenewPolicyReferred = true;

				String IsFirmRenderedServiceForAnyClient = objMap
						.get("IsFirmRenderedServiceForAnyClient") != null ? objMap
						.get("IsFirmRenderedServiceForAnyClient").toString()
						: "";
				if (IsFirmRenderedServiceForAnyClient.equals("Y"))
					isRenewPolicyReferred = true;

				String IsSuitInstituted = objMap.get("IsSuitInstituted") != null ? objMap
						.get("IsSuitInstituted").toString()
						: "";
				if (IsSuitInstituted.equals("Y"))
					isRenewPolicyReferred = true;
			}

			if (checkIfValueInAuditWork(ctx))
				isRenewPolicyReferred = true;

			Object objAttestation = SqlResources.getSqlMapProcessor(ctx)
					.findByKey("Attestation.findByKey", ctx);
			if (objAttestation != null && objAttestation instanceof Map) {

				Map objMap = (Map) objAttestation;

				String FirmRenderedAuditAtt = objMap
						.get("FirmRenderedAuditAtt") != null ? objMap.get(
						"FirmRenderedAuditAtt").toString() : "";
				if (!"".equals(FirmRenderedAuditAtt))
					isRenewPolicyReferred = true;

				String IsSecondPartnerReviewAtt = objMap
						.get("IsSecondPartnerReviewAtt") != null ? objMap.get(
						"IsSecondPartnerReviewAtt").toString() : "";
				if ("N".equals(IsSecondPartnerReviewAtt))
					isRenewPolicyReferred = true;

				String IsAuditEngagementsAtt = objMap
						.get("IsAuditEngagementsAtt") != null ? objMap.get(
						"IsAuditEngagementsAtt").toString() : "";
				if ("Y".equals(IsAuditEngagementsAtt))
					isRenewPolicyReferred = true;
			}

			Object objBusiness = SqlResources.getSqlMapProcessor(ctx)
					.findByKey("FundsControlledSupp.findByKey", ctx);
			if (objBusiness != null && objBusiness instanceof Map) {

				Map objMap = (Map) objBusiness;

				String IsCountersignatureRequiredFc = objMap
						.get("IsCountersignatureRequiredFc") != null ? objMap
						.get("IsCountersignatureRequiredFc").toString() : "";
				if ("Y".equals(IsCountersignatureRequiredFc))
					isRenewPolicyReferred = true;
			}
		}
		if (isRenewPolicyReferred)
			ctx.put("referRuleTriggered", "Y");
		else
			ctx.put("referRuleTriggered", "N");

		SqlResources
				.getSqlMapProcessor(ctx)
				.update(
						"SqlStmts.UserStatementrenewapplicationupdateIsRenewFirmReferred",
						ctx);

	}

	public static boolean checkIfValueInAuditWork(IContext ctx)
			throws Exception {

		Object o = SqlResources.getSqlMapProcessor(ctx).select("SqlStmts.attestationviewmomAuditwork", ctx);
		if (o != null && o instanceof List) {
			List workList = (List) o;
			if (workList != null && workList.size() > 0) {
				for (int i = 0; i < workList.size(); i++) {
					Map workMap = (Map) workList.get(i);
					int industryKey = workMap.get("ClientIndustryKey") != null ? convertStringToInteger(workMap
							.get("ClientIndustryKey").toString())
							: 0;
					if (industryKey == 2 || industryKey == 3
							|| industryKey == 9 || industryKey == 10) {

						int auditClients = workMap.get("AuditClients") != null ? convertStringToInteger(workMap
								.get("AuditClients").toString())
								: 0;
						int ClientsAssestsOver = workMap
								.get("ClientsAssestsOver") != null ? convertStringToInteger(workMap
								.get("ClientsAssestsOver").toString())
								: 0;
						int NetLossForLastFYE = workMap
								.get("NetLossForLastFYE") != null ? convertStringToInteger(workMap
								.get("NetLossForLastFYE").toString())
								: 0;
						if (auditClients > 0 || ClientsAssestsOver > 0
								|| NetLossForLastFYE > 0)
							return true;
					}
				}
			}

		}

		return false;
	}

	@SuppressWarnings("rawtypes")
	public static boolean checkIfAccountNameIsChanged(IContext ctx)
			throws Exception {

		Object accountObject = SqlResources
				.getSqlMapProcessor(ctx)
				.select(
						"SqlStmts.UserStatementrenewapplicationgetAllAccountOfAnAccountID",
						ctx);

		if (accountObject != null && accountObject instanceof List) {

			List accountList = (List) accountObject;
			if (accountList.size() > 1) {
				String accountNamePrevious = ((Map) accountList.get(accountList
						.size() - 2)).get("AccountName").toString().trim();

				String accountNameCurrent = ((Map) accountList.get(accountList
						.size() - 1)).get("AccountName").toString().trim();

				if (!accountNamePrevious.equals(accountNameCurrent))
					return true;
			}

		}
		return false;
	}

	public static int convertStringToInteger(String str) throws Exception {

		int intVar = 0;
		if (str != null && !"".equals(str)) {
			intVar = Integer.parseInt(str);
		}
		return intVar;
	}

	public static long convertStringToLong(String str) throws Exception {

		long intVar = 0;
		if (str != null && !"".equals(str)) {
			intVar = Long.parseLong(str);
		}
		return intVar;
	}

	public static void saveRegistrationPageSaveForAllAccount(IContext ctx)
			throws Exception {

		List list = SqlResources.getSqlMapProcessor(ctx).select(
				"SqlStmts.UserStatementrenewapplicationAllDataOfAnAccountID",
				ctx);
		Context registerCtx = new Context();
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Map map = (Map) list.get(i);
				registerCtx.putAll(ctx);
				registerCtx.put("PolicyKey", map.get("PolicyKey") != null ? map
						.get("PolicyKey").toString() : "");
				registerCtx.put("VersionSequence",
						map.get("VersionSequence") != null ? map.get(
								"VersionSequence").toString() : "");
				SqlResources
						.getSqlMapProcessor(registerCtx)
						.update(
								"SqlStmts.UserStatementrenewapplicationupdateRegistrationPageSave",
								registerCtx);

			}
		}
	}

	public String encrypt(String message) throws Exception {
		final MessageDigest md = MessageDigest.getInstance("md5");
		final byte[] digestOfPassword = md.digest(key.getBytes("utf-8"));
		final byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
		for (int j = 0, k = 16; j < 8;) {
			keyBytes[k++] = keyBytes[j++];
		}

		final SecretKey key = new SecretKeySpec(keyBytes, "DESede");
		final IvParameterSpec iv = new IvParameterSpec(new byte[8]);
		final Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, key);

		final byte[] plainTextBytes = message.getBytes("utf-8");
		final byte[] cipherText = cipher.doFinal(plainTextBytes);
		final String encodedCipherText = new sun.misc.BASE64Encoder()
				.encode(cipherText);

		return encodedCipherText;
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
			final Cipher decipher = Cipher
					.getInstance("DESede/ECB/PKCS5Padding");
			decipher.init(Cipher.DECRYPT_MODE, key);
			final byte[] encData = new sun.misc.BASE64Decoder()
					.decodeBuffer(message);
			final byte[] plainText = decipher.doFinal(encData);
			return new String(plainText, "UTF-8");
		} catch (Exception e) {
			logger.error("Error in AccountantValidationUtils.decrypt", e);
			return message;
		}
	}

	public static void decryptyPolicyKey(IContext ctx) throws Exception {
		String policyKey = ctx.get("EncryptedPolicyKey") != null ? ctx.get("EncryptedPolicyKey").toString() : "";
		
		if("".equals(policyKey))
				policyKey = ctx.get("PolicyKey") != null ? ctx.get("PolicyKey").toString() : "";
		try
		{
			Long.parseLong(policyKey);
			ctx.put("PolicyKey", policyKey);
			ctx.put("ParentPolicyKey", policyKey);
			
		}
		catch(NumberFormatException ne)
		{
			logger.error("Error in AccountantValidationUtils.decryptyPolicyKey", ne);
			AccountantValidationUtils o = new AccountantValidationUtils();
			ctx.put("PolicyKey", o.decrypt(policyKey));
			ctx.put("ParentPolicyKey", o.decrypt(policyKey));
		}

		if(ctx.get("CompanyKey") == null || "".equals(ctx.get("CompanyKey")) || "SPACE".equals(ctx.get("CompanyKey"))) {
			
			List getCompanyKeyList = (List)SqlResources.getSqlMapProcessor(ctx).select("SqlStmts.UserStatementManualBoQueriesgetCompanyKey", ctx);
    		if(getCompanyKeyList != null && getCompanyKeyList.size() > 0 && !getCompanyKeyList.isEmpty()){
    			Map getCompanyKeyMap = (Map)getCompanyKeyList.get(0);
    			String companyKey = (getCompanyKeyMap.get("CompanyKey") == null ? "" : getCompanyKeyMap.get("CompanyKey").toString());
    			ctx.put("CompanyKey",companyKey);
    		}
		}
	}

	/*
	 * This method will clear the GroosRevenue Amount for the recent year on
	 * Renewing the Policy.
	 */
	public void deleteGrossRevenueAndFirmData(IContext ctx) throws Exception {

		Object objRevenue = SqlResources
				.getSqlMapProcessor(ctx)
				.update(
						"SqlStmts.UserStatementrenewapplicationdeleteGrossRevenueAmount",
						ctx);

		// deleteFirmDataOnRenew(ctx);

	}

	public void deleteFirmDataOnRenew(IContext ctx) throws Exception {

		Object obj = SqlResources.getSqlMapProcessor(ctx).findByKey(
				"Firm.findByKey", ctx);
		if (obj != null && obj instanceof Map) {

			Map firmMap = (Map) obj;

			if (firmMap != null) {

				Context newCtx = new Context();
				newCtx.putAll(ctx);
				newCtx.putAll(firmMap);

				String IsServedAsTrustee = firmMap.get("IsServedAsTrustee") != null ? firmMap
						.get("IsServedAsTrustee").toString()
						: "";
				String IsFirmRenderedServiceForAnyClient = firmMap
						.get("IsFirmRenderedServiceForAnyClient") != null ? firmMap
						.get("IsFirmRenderedServiceForAnyClient").toString()
						: "";
				String IsSuitInstituted = firmMap.get("IsSuitInstituted") != null ? firmMap
						.get("IsSuitInstituted").toString()
						: "";

				String IsAwarenessOfAnyProfLiability = firmMap
						.get("IsAwarenessOfAnyProfLiability") != null ? firmMap
						.get("IsAwarenessOfAnyProfLiability").toString() : "";
				String IsAwarenessOfAnyProfLiabilitySuitAgainst = firmMap
						.get("IsAwarenessOfAnyProfLiabilitySuitAgainst") != null ? firmMap
						.get("IsAwarenessOfAnyProfLiabilitySuitAgainst")
						.toString()
						: "";

				if (!IsServedAsTrustee.equals("Y"))
					newCtx.put("IsServedAsTrustee", "");
				if (!IsFirmRenderedServiceForAnyClient.equals("Y"))
					newCtx.put("IsFirmRenderedServiceForAnyClient", "");
				if (!IsSuitInstituted.equals("Y"))
					newCtx.put("IsSuitInstituted", "");
				if (!IsAwarenessOfAnyProfLiability.equals("Y"))
					newCtx.put("IsAwarenessOfAnyProfLiability", "");
				if (!IsAwarenessOfAnyProfLiabilitySuitAgainst.equals("Y"))
					newCtx.put("IsAwarenessOfAnyProfLiabilitySuitAgainst", "");

				SqlResources
						.getSqlMapProcessor(newCtx)
						.update(
								"SqlStmts.UserStatementrenewapplicationupdateFirmDataRenew",
								newCtx);

			}

		}

	}

	public void checkIfOneOptionIsFinalised(IContext ctx) throws Exception {

		List quotesList = SqlResources.getSqlMapProcessor(ctx).select(
				"SqlStmts.UserStatementpdfquotelettergetQuoteOptedList", ctx);

		if (quotesList != null && quotesList.size() > 0) {
			boolean flag = false;
			int count = 0;
			for (int i = 0; i < quotesList.size(); i++) {

				Map map = (Map) quotesList.get(i);
				String isThisOptionFinalised = map.get("IsThisOptionFinalised") != null ? map
						.get("IsThisOptionFinalised").toString()
						: "";
				if (isThisOptionFinalised.equals("Y")) {
					flag = true;
					count++;
				}
			}
			if (flag && count == 1) {

			} else {
				AccountUtils.populateError(ctx, "ApplicationComplete",
						"Atleast one option should be finalised.");
			}

		}

	}

	public void checkIfApplicationIsComplete(IContext ctx) throws Exception {

		if (ctx.get("PolicyStatusKey") != null
				&& "1".equals(ctx.get("PolicyStatusKey").toString())) {
			checkIfApplicationIsCompleteNewBusiness(ctx);
		} else if (ctx.get("PolicyStatusKey") != null
				&& "2".equals(ctx.get("PolicyStatusKey").toString())) {
			checkIfApplicationIsCompleteRenew(ctx);
		}

	}

	public void checkIfApplicationIsCompleteNewBusiness(IContext ctx)
			throws Exception {

		Object object = SqlResources.getSqlMapProcessor(ctx).findByKey(
				"Firm.findByKey", ctx);

		if (object != null && object instanceof Map) {

			Map firmMap = (Map) object;

			String IsBasicInfoPage = firmMap.get("IsBasicPage") != null ? firmMap
					.get("IsBasicPage").toString()
					: "";
			String RenewBasicInfoPage = firmMap.get("RenewBasicInfoPage") != null ? firmMap
					.get("RenewBasicInfoPage").toString()
					: "";
			String IsGeneralPage = firmMap.get("IsGeneralPage") != null ? firmMap
					.get("IsGeneralPage").toString()
					: "";
			String IsPractiseManagementPage = firmMap.get("IsPracriceMangPage") != null ? firmMap
					.get("IsPracriceMangPage").toString()
					: "";
			String IsAttestationPage = firmMap.get("IsAttesationPage") != null ? firmMap
					.get("IsAttesationPage").toString()
					: "";
			String IsInvestmentPlanning = firmMap.get("IsSupplementPage") != null ? firmMap
					.get("IsSupplementPage").toString()
					: "";
			String IsComputerServices = firmMap.get("IsComputerServicePage") != null ? firmMap
					.get("IsComputerServicePage").toString()
					: "";
			String IsBusinessmanagement = firmMap.get("IsBusinessFundPage") != null ? firmMap
					.get("IsBusinessFundPage").toString()
					: "";
			// String IsControlClientFunds = firmMap.get("IsControlClientFunds")
			// != null ? firmMap.get("IsControlClientFunds").toString() : "";

			if (!IsBasicInfoPage.equals("Y") || !IsGeneralPage.equals("Y"))
				AccountUtils.populateError(ctx, "ApplicationComplete",
						"Please complete the full application.");

			// if (isPMSupplementExist(ctx)) {
			// if (!IsPractiseManagementPage.equals("Y"))
			// AccountUtils.populateError(ctx, "ApplicationComplete",
			// "Please complete the full application.");
			// }

			List aoplist = SqlResources.getSqlMapProcessor(ctx).select(
					"SqlStmts.UserStatementsaveAccountstmtspopulateAOPFields",
					ctx);

			Map covMap = new HashMap();
			if (aoplist != null) {
				for (int i = 0; i < aoplist.size(); i++) {
					Map map = (HashMap) aoplist.get(i);
					covMap.put("AOP_Percentage_" + map.get("AOPKey"), map
							.get("PercentageValue"));
				}
			}

			// Audit: Publicly-held clients
			int auditpercentage1 = convertStringToInteger(covMap
					.get("AOP_Percentage_5") != null ? covMap.get(
					"AOP_Percentage_5").toString() : "0");

			// Audit: Non-public clients
			int auditpercentage2 = convertStringToInteger(covMap
					.get("AOP_Percentage_6") != null ? covMap.get(
					"AOP_Percentage_6").toString() : "0");
			if (auditpercentage1 > 0 || auditpercentage2 > 0) {
				if (!IsAttestationPage.equals("Y"))
					AccountUtils
							.populateError(ctx, "ApplicationComplete",
									"Audit supplement is not saved. Please complete the full application.");

			}

			int businesspercentage = convertStringToInteger(covMap
					.get("AOP_Percentage_18") != null ? covMap.get(
					"AOP_Percentage_18").toString() : "0");
			if (businesspercentage > 0) {
				if (!IsBusinessmanagement.equals("Y"))
					AccountUtils
							.populateError(ctx, "ApplicationComplete",
									"Business Management is not saved. Please complete the full application.");

			}

			int investmentpercentage = convertStringToInteger(covMap
					.get("AOP_Percentage_19") != null ? covMap.get(
					"AOP_Percentage_19").toString() : "0");
			if (investmentpercentage > 0) {
				if (!IsInvestmentPlanning.equals("Y"))
					AccountUtils
							.populateError(
									ctx,
									"ApplicationComplete",
									"Investment / Financial Planning supplement is not saved. Please complete the full application.");

			}

			int computerpercentage = convertStringToInteger(covMap
					.get("AOP_Percentage_14") != null ? covMap.get(
					"AOP_Percentage_14").toString() : "0");
			if (computerpercentage > 0) {
				if (!IsComputerServices.equals("Y"))
					AccountUtils
							.populateError(
									ctx,
									"ApplicationComplete",
									"Computer-Services supplement is not saved. Please complete the full application.");

			}

		}

	}

	public void checkIfApplicationIsCompleteRenew(IContext ctx)
			throws Exception {

		Object object = SqlResources.getSqlMapProcessor(ctx).findByKey(
				"Firm.findByKey", ctx);

		if (object != null && object instanceof Map) {

			Map firmMap = (Map) object;

			String RenewBasicInfoPage = firmMap.get("RenewBasicInfoPage") != null ? firmMap
					.get("RenewBasicInfoPage").toString()
					: "";
			String IsGeneralPage = firmMap.get("IsGeneralPage") != null ? firmMap
					.get("IsGeneralPage").toString()
					: "";
			String IsPractiseManagementPage = firmMap.get("IsPracriceMangPage") != null ? firmMap
					.get("IsPracriceMangPage").toString()
					: "";
			String IsAttestationPage = firmMap.get("IsAttesationPage") != null ? firmMap
					.get("IsAttesationPage").toString()
					: "";
			String IsInvestmentPlanning = firmMap.get("IsSupplementPage") != null ? firmMap
					.get("IsSupplementPage").toString()
					: "";
			String IsComputerServices = firmMap.get("IsComputerServicePage") != null ? firmMap
					.get("IsComputerServicePage").toString()
					: "";
			String IsBusinessmanagement = firmMap.get("IsBusinessFundPage") != null ? firmMap
					.get("IsBusinessFundPage").toString()
					: "";

			if (!RenewBasicInfoPage.equals("Y"))
				AccountUtils
						.populateError(ctx, "ApplicationComplete",
								"Basic Page is not saved. Please complete the full application.");

			if (IsAuditRequiredInRenew(ctx)) {

				if (!IsAttestationPage.equals("Y"))
					AccountUtils
							.populateError(ctx, "ApplicationComplete",
									"Audit supplement is not saved. Please complete the full application.");

			}

			if (IsComputerRequiredInRenew(ctx)) {

				if (!IsComputerServices.equals("Y"))
					AccountUtils
							.populateError(
									ctx,
									"ApplicationComplete",
									"Computer-Services supplement is not saved. Please complete the full application.");

			}

			if (IsInvestmentRequiredInRenew(ctx)) {

				if (!IsInvestmentPlanning.equals("Y"))
					AccountUtils
							.populateError(
									ctx,
									"ApplicationComplete",
									"Investment / Financial Planning supplement is not saved. Please complete the full application.");

			}

			if (IsBusinessRequiredInRenew(ctx)) {

				if (!IsBusinessmanagement.equals("Y"))
					AccountUtils
							.populateError(ctx, "ApplicationComplete",
									"Business Management is not saved. Please complete the full application.");

			}

		}

	}

	public boolean isPMSupplementExist(IContext ctx) throws Exception {

		boolean flag = false;
		int count = 0;
		long total = 0;
		long average = 0;

		Object objRevenue = SqlResources.getSqlMapProcessor(ctx).select(
				"SqlStmts.UserStatementrenewapplicationmomgrossrevenueData",
				ctx);

		if (objRevenue != null && objRevenue instanceof List) {
			List revenueList = (List) objRevenue;
			for (int i = 0; i < revenueList.size(); i++) {
				Map map = (Map) revenueList.get(i);
				Object objAmount = AccountUtils.removeAmountFormat(map
						.get("Amount"));
				if (objAmount != null && !"".equals(objAmount.toString())
						&& !"0".equals(objAmount.toString())) {
					total = total + Long.parseLong(objAmount.toString());
					count = count + 1;
				}
			}
			if (count > 0) {
				long AverageRevenue = total / count;
				if (AverageRevenue > 500000)
					flag = true;
			}
		}
		return flag;
	}

	public static boolean validatePageOnAcceptIssue(Context ctx)
			throws Exception {
		boolean isOldFlow = false;
		Object obj = RuleUtils.executeRule(ctx, "AccountantRule.oldAppFlow");
		if (obj != null && obj instanceof Boolean) {
			isOldFlow = (Boolean) obj;
		}
		if(isOldFlow)
		{
		AccountantValidationUtils utils = new AccountantValidationUtils();
		utils.checkIfApplicationIsComplete(ctx);
		utils.checkIfOneOptionIsFinalised(ctx);
		}
		return true;
	}

	public static boolean validateRenewalReview(Context ctx) throws Exception {

		if (ctx.get("IsRenewalReview") == null)
			AccountUtils.populateError(ctx, "IsRenewalReview",
					"Please Indicate Yes or No");

		if (ctx.get("IsRenewalReview") != null
				&& "".equals(ctx.get("IsRenewalReview").toString()))
			AccountUtils.populateError(ctx, "IsRenewalReview",
					"Please Indicate Yes or No");

		if (ctx.get("IsRenewalReview") != null
				&& "Y".equals(ctx.get("IsRenewalReview").toString())) {

			if (ctx.get("RenewalReviewComments") == null)
				AccountUtils.populateError(ctx, "RenewalReviewComments",
						"Please provide comments.");

			if (ctx.get("RenewalReviewComments") != null
					&& "".equals(ctx.get("RenewalReviewComments").toString()))
				AccountUtils.populateError(ctx, "RenewalReviewComments",
						"Please provide comments.");

		}

		return true;
	}

	public static boolean validatePageOnAcceptIssue(IContext ctx)
			throws Exception {

		AccountantValidationUtils utils = new AccountantValidationUtils();

		utils.checkIfApplicationIsComplete(ctx);
		utils.checkIfOneOptionIsFinalised(ctx);

		return true;
	}

	public static boolean setPageFlagsOnRenew(IContext ctx) throws Exception {

		// Required Just to put Page flags in Context
		Object objFirm = SqlResources.getSqlMapProcessor(ctx).findByKey(
				"Firm.findByKey", ctx);

		ctx.putAll((Map) objFirm);

		Object obj = SqlResources
				.getSqlMapProcessor(ctx)
				.select(
						"SqlStmts.UserStatementrenewapplicationgetAllPolicyOfAnAccount",
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

					previousContext.put("VersionSequence", keyMap
							.get("VersionSequence") != null ? keyMap
							.get("VersionSequence") : null);

				}
			}

			List aopPreviousList = SqlResources.getSqlMapProcessor(
					previousContext).select(
					"SqlStmts.UserStatementsaveAccountstmtspopulateAOPFields",
					previousContext);

			List aoplist = SqlResources.getSqlMapProcessor(ctx).select(
					"SqlStmts.UserStatementsaveAccountstmtspopulateAOPFields",
					ctx);

			if (aopPreviousList != null && aoplist != null) {

				if ((aopPreviousList.size() == aoplist.size())
						&& (aopPreviousList.size() > 0 && aoplist.size() > 0)) {

					for (int i = 0; i < aoplist.size(); i++) {

						Map currentMap = (HashMap) aoplist.get(i);
						Map previousMap = (HashMap) aopPreviousList.get(i);

						long percentageValue = 0;

						String aopPreviousKey = previousMap.get("AOPKey") != null ? previousMap
								.get("AOPKey").toString()
								: "";
						String aopCurrentKey = currentMap.get("AOPKey") != null ? currentMap
								.get("AOPKey").toString()
								: "";

						long previousPercentage = convertStringToLong(previousMap
								.get("PercentageValue") != null ? previousMap
								.get("PercentageValue").toString() : "0");
						long currentPercentage = convertStringToLong(currentMap
								.get("PercentageValue") != null ? currentMap
								.get("PercentageValue").toString() : "0");

						if ("6".equals(aopPreviousKey)
								&& "6".equals(aopCurrentKey)) {
							if (currentPercentage > 0) {
								if (getPercentDecrease(previousPercentage,
										currentPercentage) >= 5
										|| getPercentIncrease(
												previousPercentage,
												currentPercentage) >= 5) {
									ctx.put("IsAttesationPage", "N");

								}
							}
						}

						if ("14".equals(aopPreviousKey)
								&& "14".equals(aopCurrentKey)) {

							if (getPercentDecrease(previousPercentage,
									currentPercentage) >= 5
									|| getPercentIncrease(previousPercentage,
											currentPercentage) >= 5) {
								ctx.put("IsComputerServicePage", "N");

							}

						}

						if ("18".equals(aopPreviousKey)
								&& "18".equals(aopCurrentKey)) {

							if (getPercentDecrease(previousPercentage,
									currentPercentage) >= 5
									|| getPercentIncrease(previousPercentage,
											currentPercentage) >= 5) {
								ctx.put("IsBusinessFundPage", "N");

							}

						}

						if ("19".equals(aopPreviousKey)
								&& "19".equals(aopCurrentKey)) {

							if (getPercentDecrease(previousPercentage,
									currentPercentage) >= 5
									|| getPercentIncrease(previousPercentage,
											currentPercentage) >= 5) {
								ctx.put("IsSupplementPage", "N");

							}

						}

					}

					SqlResources
							.getSqlMapProcessor(ctx)
							.update(
									"SqlStmts.UserStatementrenewapplicationupdateSupplementPages",
									ctx);

				}
			}

		}
		return true;

	}

	public static boolean hideShowSupplementOnRenew(IContext ctx)
			throws Exception {

		ctx.put("showAttesationSupp", "N");
		ctx.put("showComputerSupp", "N");
		ctx.put("showBusinessSupp", "N");
		ctx.put("showSupplementSupp", "N");

		// Required Just to put Page flags in Context
		Object objFirm = SqlResources.getSqlMapProcessor(ctx).findByKey(
				"Firm.findByKey", ctx);

		String IsControlClientFunds = ((Map) objFirm)
				.get("IsControlClientFunds") != null ? ((Map) objFirm).get(
				"IsControlClientFunds").toString() : "";

		ctx.putAll((Map) objFirm);

		Object obj = SqlResources
				.getSqlMapProcessor(ctx)
				.select(
						"SqlStmts.UserStatementrenewapplicationgetAllPolicyOfAnAccount",
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

					previousContext.put("VersionSequence", keyMap
							.get("VersionSequence") != null ? keyMap
							.get("VersionSequence") : null);

				}
			}

			List aopPreviousList = SqlResources.getSqlMapProcessor(
					previousContext).select(
					"SqlStmts.UserStatementsaveAccountstmtspopulateAOPFields",
					previousContext);

			List aoplist = SqlResources.getSqlMapProcessor(ctx).select(
					"SqlStmts.UserStatementsaveAccountstmtspopulateAOPFields",
					ctx);

			if (aopPreviousList != null && aoplist != null) {

				if ((aopPreviousList.size() == aoplist.size())
						&& (aopPreviousList.size() > 0 && aoplist.size() > 0)) {

					for (int i = 0; i < aoplist.size(); i++) {

						Map currentMap = (HashMap) aoplist.get(i);
						Map previousMap = (HashMap) aopPreviousList.get(i);

						long percentageValue = 0;

						String aopPreviousKey = previousMap.get("AOPKey") != null ? previousMap
								.get("AOPKey").toString()
								: "";
						String aopCurrentKey = currentMap.get("AOPKey") != null ? currentMap
								.get("AOPKey").toString()
								: "";

						long previousPercentage = convertStringToLong(previousMap
								.get("PercentageValue") != null ? previousMap
								.get("PercentageValue").toString() : "0");
						long currentPercentage = convertStringToLong(currentMap
								.get("PercentageValue") != null ? currentMap
								.get("PercentageValue").toString() : "0");

						if ("6".equals(aopPreviousKey) && "6".equals(aopCurrentKey)) {
							if (currentPercentage - previousPercentage >= 5 || (previousPercentage==0 && currentPercentage>previousPercentage)) {
								ctx.put("showAttesationSupp", "Y");
							}
						}
						if ("14".equals(aopPreviousKey) && "14".equals(aopCurrentKey)) {
							if (currentPercentage - previousPercentage >= 5 || (previousPercentage==0 && currentPercentage>previousPercentage)) {
								ctx.put("showComputerSupp", "Y");
							}
						}

						if ("18".equals(aopPreviousKey) && "18".equals(aopCurrentKey)) {
							if (currentPercentage > 0) {
								if (currentPercentage - previousPercentage >= 5 || (previousPercentage==0 && currentPercentage>previousPercentage)) {
									ctx.put("showBusinessSupp", "Y");
								}
							}
						}
						if ("Y".equals(IsControlClientFunds)) {
							ctx.put("showBusinessSupp", "Y");
						}

						if ("19".equals(aopPreviousKey) && "19".equals(aopCurrentKey)) {
							if (currentPercentage - previousPercentage >= 5 || (previousPercentage==0 && currentPercentage>previousPercentage)) {
								ctx.put("showSupplementSupp", "Y");
							}
						}

					}
				}
			}
		}
		return true;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public boolean IsAuditRequiredInRenew(IContext ctx) throws Exception {

		boolean IsAuditRequired = false;

		Object objFirm = SqlResources.getSqlMapProcessor(ctx).findByKey(
				"Firm.findByKey", ctx);

		ctx.putAll((Map) objFirm);

		Object obj = SqlResources
				.getSqlMapProcessor(ctx)
				.select(
						"SqlStmts.UserStatementrenewapplicationgetAllPolicyOfAnAccount",
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

					previousContext.put("VersionSequence", keyMap
							.get("VersionSequence") != null ? keyMap
							.get("VersionSequence") : null);

				}
			}

			List aopPreviousList = SqlResources.getSqlMapProcessor(
					previousContext).select(
					"SqlStmts.UserStatementsaveAccountstmtspopulateAOPFields",
					previousContext);

			List aoplist = SqlResources.getSqlMapProcessor(ctx).select(
					"SqlStmts.UserStatementsaveAccountstmtspopulateAOPFields",
					ctx);

			if (aopPreviousList != null && aoplist != null) {

				if ((aopPreviousList.size() == aoplist.size())
						&& (aopPreviousList.size() > 0 && aoplist.size() > 0)) {

					for (int i = 0; i < aoplist.size(); i++) {

						Map currentMap = (HashMap) aoplist.get(i);
						Map previousMap = (HashMap) aopPreviousList.get(i);

						long percentageValue = 0;

						String aopPreviousKey = previousMap.get("AOPKey") != null ? previousMap
								.get("AOPKey").toString()
								: "";
						String aopCurrentKey = currentMap.get("AOPKey") != null ? currentMap
								.get("AOPKey").toString()
								: "";

						long previousPercentage = convertStringToLong(previousMap
								.get("PercentageValue") != null ? previousMap
								.get("PercentageValue").toString() : "0");
						long currentPercentage = convertStringToLong(currentMap
								.get("PercentageValue") != null ? currentMap
								.get("PercentageValue").toString() : "0");

						if ("6".equals(aopPreviousKey)
								&& "6".equals(aopCurrentKey)) {
							if (currentPercentage - previousPercentage >= 5) {
								IsAuditRequired = true;
							}
							break;
						}
					}
				}
			}
		}
		return IsAuditRequired;

	}

	public boolean IsComputerRequiredInRenew(IContext ctx) throws Exception {

		boolean IsComputerRequired = false;

		Object objFirm = SqlResources.getSqlMapProcessor(ctx).findByKey(
				"Firm.findByKey", ctx);

		ctx.putAll((Map) objFirm);

		Object obj = SqlResources
				.getSqlMapProcessor(ctx)
				.select(
						"SqlStmts.UserStatementrenewapplicationgetAllPolicyOfAnAccount",
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

					previousContext.put("VersionSequence", keyMap
							.get("VersionSequence") != null ? keyMap
							.get("VersionSequence") : null);

				}
			}

			List aopPreviousList = SqlResources.getSqlMapProcessor(
					previousContext).select(
					"SqlStmts.UserStatementsaveAccountstmtspopulateAOPFields",
					previousContext);

			List aoplist = SqlResources.getSqlMapProcessor(ctx).select(
					"SqlStmts.UserStatementsaveAccountstmtspopulateAOPFields",
					ctx);

			if (aopPreviousList != null && aoplist != null) {

				if ((aopPreviousList.size() == aoplist.size())
						&& (aopPreviousList.size() > 0 && aoplist.size() > 0)) {

					for (int i = 0; i < aoplist.size(); i++) {

						Map currentMap = (HashMap) aoplist.get(i);
						Map previousMap = (HashMap) aopPreviousList.get(i);

						long percentageValue = 0;

						String aopPreviousKey = previousMap.get("AOPKey") != null ? previousMap
								.get("AOPKey").toString()
								: "";
						String aopCurrentKey = currentMap.get("AOPKey") != null ? currentMap
								.get("AOPKey").toString()
								: "";

						long previousPercentage = convertStringToLong(previousMap
								.get("PercentageValue") != null ? previousMap
								.get("PercentageValue").toString() : "0");
						long currentPercentage = convertStringToLong(currentMap
								.get("PercentageValue") != null ? currentMap
								.get("PercentageValue").toString() : "0");

						if ("14".equals(aopPreviousKey)
								&& "14".equals(aopCurrentKey)) {
							if (currentPercentage - previousPercentage >= 5) {
								IsComputerRequired = true;
							}
							break;
						}
					}
				}
			}
		}
		return IsComputerRequired;

	}

	public boolean IsInvestmentRequiredInRenew(IContext ctx) throws Exception {

		boolean IsInvestmentRequired = false;

		Object objFirm = SqlResources.getSqlMapProcessor(ctx).findByKey(
				"Firm.findByKey", ctx);

		ctx.putAll((Map) objFirm);

		Object obj = SqlResources
				.getSqlMapProcessor(ctx)
				.select(
						"SqlStmts.UserStatementrenewapplicationgetAllPolicyOfAnAccount",
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

					previousContext.put("VersionSequence", keyMap
							.get("VersionSequence") != null ? keyMap
							.get("VersionSequence") : null);

				}
			}

			List aopPreviousList = SqlResources.getSqlMapProcessor(
					previousContext).select(
					"SqlStmts.UserStatementsaveAccountstmtspopulateAOPFields",
					previousContext);

			List aoplist = SqlResources.getSqlMapProcessor(ctx).select(
					"SqlStmts.UserStatementsaveAccountstmtspopulateAOPFields",
					ctx);

			if (aopPreviousList != null && aoplist != null) {

				if ((aopPreviousList.size() == aoplist.size())
						&& (aopPreviousList.size() > 0 && aoplist.size() > 0)) {

					for (int i = 0; i < aoplist.size(); i++) {

						Map currentMap = (HashMap) aoplist.get(i);
						Map previousMap = (HashMap) aopPreviousList.get(i);

						long percentageValue = 0;

						String aopPreviousKey = previousMap.get("AOPKey") != null ? previousMap
								.get("AOPKey").toString()
								: "";
						String aopCurrentKey = currentMap.get("AOPKey") != null ? currentMap
								.get("AOPKey").toString()
								: "";

						long previousPercentage = convertStringToLong(previousMap
								.get("PercentageValue") != null ? previousMap
								.get("PercentageValue").toString() : "0");
						long currentPercentage = convertStringToLong(currentMap
								.get("PercentageValue") != null ? currentMap
								.get("PercentageValue").toString() : "0");

						if ("19".equals(aopPreviousKey)
								&& "19".equals(aopCurrentKey)) {
							if (currentPercentage - previousPercentage >= 5) {
								IsInvestmentRequired = true;
							}
							break;
						}
					}
				}
			}
		}
		return IsInvestmentRequired;

	}

	public boolean IsBusinessRequiredInRenew(IContext ctx) throws Exception {

		boolean IsBusinessRequired = false;

		Object objFirm = SqlResources.getSqlMapProcessor(ctx).findByKey(
				"Firm.findByKey", ctx);

		String IsControlClientFunds = ((Map) objFirm)
				.get("IsControlClientFunds") != null ? ((Map) objFirm).get(
				"IsControlClientFunds").toString() : "";
		if (IsControlClientFunds.equals("Y"))
			IsBusinessRequired = true;

		ctx.putAll((Map) objFirm);

		Object obj = SqlResources
				.getSqlMapProcessor(ctx)
				.select(
						"SqlStmts.UserStatementrenewapplicationgetAllPolicyOfAnAccount",
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

					previousContext.put("VersionSequence", keyMap
							.get("VersionSequence") != null ? keyMap
							.get("VersionSequence") : null);

				}
			}

			List aopPreviousList = SqlResources.getSqlMapProcessor(
					previousContext).select(
					"SqlStmts.UserStatementsaveAccountstmtspopulateAOPFields",
					previousContext);

			List aoplist = SqlResources.getSqlMapProcessor(ctx).select(
					"SqlStmts.UserStatementsaveAccountstmtspopulateAOPFields",
					ctx);

			if (aopPreviousList != null && aoplist != null) {

				if ((aopPreviousList.size() == aoplist.size())
						&& (aopPreviousList.size() > 0 && aoplist.size() > 0)) {

					for (int i = 0; i < aoplist.size(); i++) {

						Map currentMap = (HashMap) aoplist.get(i);
						Map previousMap = (HashMap) aopPreviousList.get(i);

						long percentageValue = 0;

						String aopPreviousKey = previousMap.get("AOPKey") != null ? previousMap
								.get("AOPKey").toString()
								: "";
						String aopCurrentKey = currentMap.get("AOPKey") != null ? currentMap
								.get("AOPKey").toString()
								: "";

						long previousPercentage = convertStringToLong(previousMap
								.get("PercentageValue") != null ? previousMap
								.get("PercentageValue").toString() : "0");
						long currentPercentage = convertStringToLong(currentMap
								.get("PercentageValue") != null ? currentMap
								.get("PercentageValue").toString() : "0");

						if ("18".equals(aopPreviousKey)
								&& "18".equals(aopCurrentKey)) {
							if (currentPercentage - previousPercentage >= 5) {
								IsBusinessRequired = true;
							}
							break;
						}

					}

				}
			}
		}
		return IsBusinessRequired;

	}

	public static boolean hideShowSupplementOnRenewPDF(IContext ctx)
			throws Exception {

		ctx.put("showAttesationSuppPDF", "N");
		ctx.put("showComputerSuppPDF", "N");
		ctx.put("showBusinessSuppPDF", "N");
		ctx.put("showSupplementSuppPDF", "N");

		// Required Just to put Page flags in Context
		Object objFirm = SqlResources.getSqlMapProcessor(ctx).findByKey(
				"Firm.findByKey", ctx);

		ctx.putAll((Map) objFirm);

		Object obj = SqlResources
				.getSqlMapProcessor(ctx)
				.select(
						"SqlStmts.UserStatementrenewapplicationgetAllPolicyOfAnAccount",
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

					previousContext.put("VersionSequence", keyMap
							.get("VersionSequence") != null ? keyMap
							.get("VersionSequence") : null);

				}
			}

			List aopPreviousList = SqlResources.getSqlMapProcessor(
					previousContext).select(
					"SqlStmts.UserStatementsaveAccountstmtspopulateAOPFields",
					previousContext);

			List aoplist = SqlResources.getSqlMapProcessor(ctx).select(
					"SqlStmts.UserStatementsaveAccountstmtspopulateAOPFields",
					ctx);

			if (aopPreviousList != null && aoplist != null) {

				if ((aopPreviousList.size() == aoplist.size())
						&& (aopPreviousList.size() > 0 && aoplist.size() > 0)) {

					for (int i = 0; i < aoplist.size(); i++) {

						Map currentMap = (HashMap) aoplist.get(i);
						Map previousMap = (HashMap) aopPreviousList.get(i);

						long percentageValue = 0;

						String aopPreviousKey = previousMap.get("AOPKey") != null ? previousMap
								.get("AOPKey").toString()
								: "";
						String aopCurrentKey = currentMap.get("AOPKey") != null ? currentMap
								.get("AOPKey").toString()
								: "";

						long previousPercentage = convertStringToLong(previousMap
								.get("PercentageValue") != null ? previousMap
								.get("PercentageValue").toString() : "0");
						long currentPercentage = convertStringToLong(currentMap
								.get("PercentageValue") != null ? currentMap
								.get("PercentageValue").toString() : "0");

						if ("6".equals(aopPreviousKey)
								&& "6".equals(aopCurrentKey)) {
							if (currentPercentage - previousPercentage >= 5) {
								ctx.put("showAttesationSuppPDF", "Y");
							}
						}
						if ("14".equals(aopPreviousKey)
								&& "14".equals(aopCurrentKey)) {
							if (currentPercentage - previousPercentage >= 5) {
								ctx.put("showComputerSuppPDF", "Y");
							}
						}

						if ("18".equals(aopPreviousKey)
								&& "18".equals(aopCurrentKey)) {
							if (currentPercentage > 0) {
								if (getPercentDecrease(previousPercentage,
										currentPercentage) >= 5
										|| getPercentIncrease(
												previousPercentage,
												currentPercentage) >= 5) {
									ctx.put("showBusinessSuppPDF", "Y");
								}
							}
						}

						if ("19".equals(aopPreviousKey)
								&& "19".equals(aopCurrentKey)) {
							if (currentPercentage - previousPercentage >= 5) {
								ctx.put("showSupplementSuppPDF", "Y");
							}
						}

					}
				}
			}

		}
		return true;
	}

	public static float getPercentIncrease(long previousVal, long currentValue)
			throws Exception {

		if (currentValue > previousVal) {
			float difference = currentValue - previousVal;
			float percentIncrease = difference / previousVal;
			percentIncrease = percentIncrease * 100;

			return percentIncrease;
		}

		return 0;
	}

	public static double getPercentDecrease(long previousVal, long currentValue)
			throws Exception {

		if (previousVal > currentValue) {
			long difference = previousVal - currentValue;
			double percentIncrease = difference / previousVal;
			percentIncrease = percentIncrease * 100;

			return percentIncrease;
		}

		return 0;
	}

	public static void main(String[] args) throws Exception {

//		String policyEffectiveDate = "2012-2-15 00:00:00.000";
//		Date ped = AccountantDateUtils.convertStringToDate(policyEffectiveDate,
//				"mm/dd/yyyy");
//		String month = AccountantDateUtils.getMonthFromDate(ped);
//		int intMonth = month != null && !month.equals("") ? Integer
//				.parseInt(month) : 0;
//		intMonth = intMonth + 1 ;
//		String yearenddate = "04/2012";
//
//		String revenuemonth = !yearenddate.equals("") ? yearenddate.substring(
//				0, 2) : "";
//		String revenueyear = !yearenddate.equals("") ? yearenddate.substring(3)
//				: "";
//		int intrevenuemonth = revenuemonth != null && !revenuemonth.equals("") ? Integer
//				.parseInt(revenuemonth)
//				: 0;
//		int intrevenueyear = revenueyear != null && !revenueyear.equals("") ? Integer
//				.parseInt(revenueyear)
//				: 0;
//		if (intrevenuemonth >= intMonth) {
//			intrevenueyear = intrevenueyear - 1;
//		}
//			revenueyear = String.valueOf(intrevenueyear);
//
//			String revenueYear = revenuemonth.concat("/").concat(revenueyear);

		

		// Context ctx = new Context();
		// AccountantValidationUtils o = new AccountantValidationUtils();
		// o.checkIfOneOptionIsFinalised(ctx);
		// String ot = o.decrypt("g5EiSg k/Wc=");
		// String a = o.encrypt("9067");
		// System.out.println(a);
		// System.out.println(" - - > " + o.decrypt(a));

	}

	public static boolean isManualInPriorYear(Context previousContext)
			throws Exception {

		Object obj = SqlResources
				.getSqlMapProcessor(previousContext)
				.findByKey(
						"SqlStmts.UserStatementsaveAccountstmtscheckIsDollarDefenseWithFinalised",
						previousContext);
		if (obj != null && obj instanceof Map) {

			Map map = (Map) obj;

			String IsDollarDefense = map.get("IsDollarDefense") != null ? map
					.get("IsDollarDefense").toString() : "";

			if ("Y".equals(IsDollarDefense))
				return true;

		}

		return false;
	}

	public void insertDataForAlreadyRenewedPolicy(IContext ctx)
			throws Exception {

		{ if (logger.isDebugEnabled()) logger.debug("Old Policy Key" + ctx.get("ParentPolicyKey")); }
		Object object = SqlResources.getSqlMapProcessor(ctx).findByKey(
				"Policy.findByKey", ctx);

		if (object != null && object instanceof Map) {

			Map accountmap = (Map) object;
			String accountId = accountmap.get("AccountID") != null ? accountmap
					.get("AccountID").toString() : "";
			ctx.put("AccountID", accountmap.get("AccountID"));
			{ if (logger.isDebugEnabled()) logger.debug("AccountID is "
					+ accountmap.get("AccountID").toString()); }
			Object alldataObject = SqlResources.getSqlMapProcessor(ctx).select(
					"Policy.SearchAccountHistory_Proc", ctx);

			if (alldataObject != null && alldataObject instanceof List) {
				List list = (List) alldataObject;
				for (int i = 0; i < 1; i++) {
					Map map = (Map) list.get(list.size() - 1);

					ctx.put("PolicyKey", map.get("PolicyKey"));
					ctx.put("VersionSequence", map.get("VersionSequence"));
					ctx.put("TransactionSequence", map
							.get("TransactionSequence"));
					ctx.put("CoverageSequence", map.get("CoverageSequence"));
					ctx.put("AccountKey", map.get("AccountKey"));
					ctx.put("AddressKey", map.get("AddressKey"));
				}

			}
		}

		// if (obj != null) {
		// List list = (List) obj;
		//
		// for (int i = 0; i < list.size(); i++) {
		// Map map = (Map) list.get(i);
		//
		// ctx.put("PolicyKey", map.get("PolicyKey"));
		// ctx.put("VersionSequence", map.get("VersionSequence"));
		// ctx.put("TransactionSequence", map.get("TransactionSequence"));
		// ctx.put("CoverageSequence", map.get("CoverageSequence"));
		// ctx.put("AccountKey", map.get("AccountKey"));
		// ctx.put("AddressKey", map.get("AddressKey"));
		// }
		// }

	}
	
	public static boolean checkIfSubProducerExist(IContext ctx)
			throws Exception {
		logger.debug("Going to check if producer code exist");
		Object obj = SqlResources.getSqlMapProcessor(ctx).findByKey(
				"PolicyTransaction.findByKey", ctx);
		boolean producerCodeExist = false;

		if (obj != null && obj instanceof Map) {

			Map objMap = (Map) obj;

			if (objMap.get("ProducerCode") != null
					&& !"".equals(objMap.get("ProducerCode").toString())) {
				producerCodeExist = true;
			} else {
				producerCodeExist = false;
			}
		} else {
			producerCodeExist = false;
		}
		{ if (logger.isDebugEnabled()) logger.debug("Producer Code exist ? " +  producerCodeExist); }
		return producerCodeExist;

	}
	
	public boolean checkForAnyQuoteWithInsideLimit(IContext ctx)
			throws Exception {

		boolean flag = false;

		List quotesList = SqlResources.getSqlMapProcessor(ctx).select(
				"SqlStmts.UserStatementpdfquotelettergetQuoteOptedList", ctx);

		boolean IsClaimExpensesTypeFlag = false;

		if (quotesList != null) {
			for (int i = 0; i < quotesList.size(); i++) {
				Map map = (Map) quotesList.get(i);

				if (map.get("IsClaimExpensesType") != null
						&& "N".equals(map.get("IsClaimExpensesType").toString()))
					IsClaimExpensesTypeFlag = true;
			}
		}

		if (IsClaimExpensesTypeFlag)
			flag = true;

		return flag;

	}

	/// Indication In jack
	
	public static void showCityDropDown(IContext ctx) throws Exception {

		Object obj = SqlResources.getSqlMapProcessor(ctx).findByKey("State.findByKey", ctx);

		if (obj != null && obj instanceof Map) {

			Map objMap = (Map) obj;

			String showCityDropDown = objMap.get("ShowCityDropDown") != null ? objMap.get("ShowCityDropDown").toString() : "";

			if (showCityDropDown.equals("Y")) {
				ctx.put("ShowCityDropDown", "Y");
			} else {
				ctx.put("ShowCityDropDown", "N");
			}
			//ctx.put("ShowCityDropDown", "Y");

		}

	}
	
	public Element checkIfErrorOutput(Object obj) throws Exception {
		Element outputElement = null;

		if (obj != null && obj instanceof Map) {

			Map responseMap = (Map) obj;
			if (responseMap != null) {

				String outputString = responseMap.get("OutputXML") != null ? responseMap
						.get("OutputXML").toString() : "";
				Element root = XMLUtils.parseXMLRootElement(outputString);
				outputElement = root.getChild("ValidationErrors");

				return outputElement;

			}

		}

		return outputElement;
	}
	
	public List getErrorListFromResponse(Element element) {
		List children = element.getChildren();
		if (children == null)
			return new ArrayList();
		List errorList = new ArrayList();
		for (int i = 0; i < children.size(); i++) {
			Element child = (Element) children.get(i);
			String name = child.getName();
			String str = child.getTextTrim();
			errorList.add(str);
		}

		return errorList;
	}
	
	public StringBuffer addListToInetErrorList(List list) throws Exception {

		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < list.size(); i++) {
			String errorStr = (String) list.get(i);
			buffer.append(errorStr);
			if (i < list.size() - 1)
				buffer.append(",");
		}
		return buffer;
	}
	
	public static void validateEmail(IContext ctx, String field, String strEmail)
			throws Exception {
		String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		if (strEmail != null && !"".equals(strEmail)) {

			// Set the email pattern string
			// Pattern p =
			// Pattern.compile(".+@.+\\.[a-z]",Pattern.CASE_INSENSITIVE);
			Pattern p = Pattern.compile(EMAIL_PATTERN);

			// Match the given string with the pattern
			Matcher m = p.matcher(strEmail);

			// check whether match is found
			boolean matchFound = m.matches();

			if (!matchFound)
				AccountUtils.populateError(ctx, field, "Invalid Email Address");

		} else {
			AccountUtils.populateError(ctx, field, "Invalid Email Address");
		}
	}
	
	public static boolean validateEmail(String email){
		// Email Regex java
		final String EMAIL_REGEX = "^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$";

		// static Pattern object, since pattern is fixed
		Pattern pattern = Pattern.compile(EMAIL_REGEX, Pattern.CASE_INSENSITIVE);

		// non-static Matcher object because it's created from the input String
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}

	public static boolean validateStatusChange(Context ctx) throws Exception 
	{
		try
		{
			if(ctx.get("PolicyKey")!=null)
			{
				
				Object obj = SqlResources.getSqlMapProcessor(ctx).findByKey("SqlStmts.UserStatementManualBoQueriesgetStatusOfPolicy", ctx);
	//			if(obj != null && obj instanceof Map)
	//				ctx.putAll((Map)obj);
				Map statusDetails = (Map)obj;
				int statusKey=statusDetails.get("StatusKey")!=null && !statusDetails.get("StatusKey").equals(HtmlConstants.EMPTY)?Integer.parseInt(statusDetails.get("StatusKey").toString()):0;
				String statusDesc=statusDetails.get("StatusDesc")!=null && !statusDetails.get("StatusDesc").equals(HtmlConstants.EMPTY)?statusDetails.get("StatusDesc").toString():"No Status";
				String headerStatusDescUpdated=ctx.get("headerStatusDescUpdated")!=null && !ctx.get("headerStatusDescUpdated").equals(HtmlConstants.EMPTY)?ctx.get("headerStatusDescUpdated").toString():"No Status";
				
				if("Indication".equals(statusDesc)){
					headerStatusDescUpdated=ctx.get("StatusDesc")!=null && !ctx.get("StatusDesc").equals(HtmlConstants.EMPTY)?ctx.get("StatusDesc").toString():"No Status";
				}
				//int previosStatusKey=ctx.get("previousStatus")!=null && !ctx.get("previousStatus").equals(HtmlConstants.EMPTY)?Integer.parseInt(ctx.get("previousStatus").toString()):0;
//				if(!"No Status".equals(headerStatusDescUpdated))
//				{
				headerStatusDescUpdated=(headerStatusDescUpdated.contains("(")) ? headerStatusDescUpdated.substring(0,headerStatusDescUpdated.indexOf('(')).trim() : headerStatusDescUpdated;
				if(!statusDesc.equals(headerStatusDescUpdated))
					return true;
//				}
			}
			
		}
		catch(Exception e)
		{
			logger.error("error occured while validating status :"+e, e);
		}
		return false;
	}
	
	
	public static void validateUserLogin(Context ctx) throws Exception 
	{
		try {
			if (ctx.get("UserLoginKey") == null)
				return;
			if(ctx.get("AccountEmail") != null){
				ctx.put("UserEmail", ctx.get("AccountEmail").toString());
			}
			List existingUserSessionLoginDetails = SqlResources.getSqlMapProcessor(ctx).select("UserSessionLogin.ValidateUserLogin_p", ctx);
    		if(existingUserSessionLoginDetails != null && !existingUserSessionLoginDetails.isEmpty()){
				ctx.put("IS_SESSION_NEW", "N");
				Map map = (HashMap) existingUserSessionLoginDetails.get(0);
				ctx.put("UserLoginID", map.get("UserLoginID"));
				ctx.put("UserEmail", null);
				ctx.put("UserLoginKey", null);
				SqlResources.getSqlMapProcessor(ctx).update("UserSessionLogin.update", ctx);
			}
		} catch(Exception e) {
			logger.error("error occured while validating status :"+e, e);
		}
	}
	
	public static boolean validateFileNameForSpecialChar(String inputString) {
		boolean result=false;
	     if (inputString == null || inputString.trim().isEmpty()) 
	           return result;
	     else if(inputString.length()>25)
	    	 return result;
	     else
	     {
	        Pattern p = Pattern.compile ("[!@#$%&*()+=|<>?{}\\[\\]~-]");
	     Matcher m = p.matcher(inputString);
	 	 boolean flag = m.find();
	     if (flag == true )
	    	 result=false;
	     else
	    	 result=true;
	     return result;
	     }
	     
	 }
	public static void validatePLPolicynumberForBrokerage(Context ctx)
	{	
		boolean result=false;
		try
		{ 
			String plPolicyNumber=!ctx.get("PLPolicyNumber").equals(HtmlConstants.EMPTY) && ctx.get("PLPolicyNumber")!=null?ctx.get("PLPolicyNumber").toString():"";
			if("".equals(plPolicyNumber) || plPolicyNumber.length()<10)
			{
				AccountUtils.populateError(ctx, "InvalidPLPolicyNumberError","Policy number not found.");
				return;
			}
			//new SetParametersForStoredProcedures().setParametersInContext(ctx, "PolicyKey,AccountKey,litigationNewImplDate,IsCCBFlag,RenewalSupplementNewImplDate");
			List validatePolicyNumber =(List)SqlResources.getSqlMapProcessor(ctx).select("BrokeragePolicy.ValidatePLPolicyNumberForBrokerage_p", ctx);
			
			if(validatePolicyNumber!=null && validatePolicyNumber.size()>0)
			ctx.putAll((Map) validatePolicyNumber.get(0));
			String isValidPLPolicyNumber=!ctx.get("isValidPLPolicyNumber").equals(HtmlConstants.EMPTY) && ctx.get("isValidPLPolicyNumber")!=null?ctx.get("isValidPLPolicyNumber").toString():"";
			if("Y".equals(isValidPLPolicyNumber))
			{
				result=true;
			//ctx.put("PLEffectiveDateNew", "2020-04-21");
			}
			if("N".equals(isValidPLPolicyNumber))
			{
				ctx.put("PolicyEffectiveDate", null);
				ctx.put("PolicyExpirationDate", null);
			}
			
		}
		catch(Exception e)
		{
			logger.error("Error occured while validating PL Policy Number for Brokerage", e);
			
		}
		try
		{
		if(result==false)
		{
			AccountUtils.populateError(ctx, "InvalidPLPolicyNumberError","Policy number not found.");
		}
		}
		catch(Exception e1)
		{
			logger.error("Error in AccountantValidationUtils.validatePLPolicynumberForBrokerage", e1);}
		//return result;
	}

    //-- ZOHO --
	public static boolean validateZohoStatusChange(Context ctx) throws Exception 
	{
		try {
			if(ctx.get("PolicyKey")!=null) {
				
				ctx.put("ZohoNameClearancePolicyKey",ctx.get("PolicyKey"));
				Map nameClearanceDetails = (Map)SqlResources.getSqlMapProcessor(ctx).findByKey("SqlStmts.UserStatementManualBoQueriesgetZohoNameClearanceReferralCountTriggered", ctx);
				String nameClearanceFlag = "";
				if(nameClearanceDetails != null){
					nameClearanceFlag = nameClearanceDetails.get("ClearStatusFlag")!=null && !HtmlConstants.EMPTY.equals(nameClearanceDetails.get("ClearStatusFlag").toString())?nameClearanceDetails.get("ClearStatusFlag").toString():"";
				}
				if(!"N".equals(nameClearanceFlag)){
					
//					boolean isInsured = false;
//					Object objInsured = RuleUtils.executeRule(ctx, "AccountantRule.isInsured");
//					if (objInsured != null && objInsured instanceof Boolean) {
//						isInsured = (Boolean) objInsured;
//					}
				
					Map statusDetails = (Map)SqlResources.getSqlMapProcessor(ctx).findByKey("SqlStmts.UserStatementManualBoQueriesgetStatusOfPolicy", ctx);
					int statusKey=statusDetails.get("StatusKey")!=null && !statusDetails.get("StatusKey").equals(HtmlConstants.EMPTY)?Integer.parseInt(statusDetails.get("StatusKey").toString()):0;
					String statusDesc=statusDetails.get("StatusDesc")!=null && !statusDetails.get("StatusDesc").equals(HtmlConstants.EMPTY)?statusDetails.get("StatusDesc").toString():"No Status";
					String headerStatusDescUpdated=ctx.get("headerStatusDescUpdated")!=null && !ctx.get("headerStatusDescUpdated").equals(HtmlConstants.EMPTY)?ctx.get("headerStatusDescUpdated").toString():"No Status";
					headerStatusDescUpdated=(headerStatusDescUpdated.contains("(")) ? headerStatusDescUpdated.substring(0,headerStatusDescUpdated.indexOf('(')).trim() : headerStatusDescUpdated;
					
					if("Y".equals(ctx.get("isRequotePolicy") == null ? "" : ctx.get("isRequotePolicy").toString()) && "New".equals(statusDesc))
						return false;
					
					if((!"Issued".equals(statusDesc)) && (!statusDesc.equals(headerStatusDescUpdated) || ("Indication".equals(headerStatusDescUpdated) && "Y".equals(ctx.get("IsAccountDetailChanged") == null ? "" : ctx.get("IsAccountDetailChanged").toString())))) //isInsured && (ctx.get("isNameClearanceRequired") == null || "".equalsIgnoreCase(ctx.get("isNameClearanceRequired").toString().trim()) || ctx.get("isNameClearanceRequired").toString().isEmpty())
						return true;
				
				}
			}
		} catch(Exception e) {
			logger.error("error occured while validating status :"+e, e);
		}
		return false;
	}
	
	public static void validateNameClearanceQQIndicationFlagsCleared(Context ctx) {

		try {
			ctx.put("ZohoNameClearancePolicyKey",ctx.get("PolicyKey"));
			Map nameClearanceDetails = (Map)SqlResources.getSqlMapProcessor(ctx).findByKey("SqlStmts.UserStatementManualBoQueriesgetZohoNameClearanceReferralCountTriggered", ctx);
			if(nameClearanceDetails != null){
				ctx.putAll(nameClearanceDetails);
			}
			
			boolean isZohoNameClearanceQQIndicationPopupFlag = false;
			Object objNameClearanceQQIndicationPopupFlag = RuleUtils.executeRule(ctx, "AccountantRule.isNameClearanceQQIndicationPopupFlag");
			if (objNameClearanceQQIndicationPopupFlag != null && objNameClearanceQQIndicationPopupFlag instanceof Boolean) {
				isZohoNameClearanceQQIndicationPopupFlag = (Boolean) objNameClearanceQQIndicationPopupFlag;
			}

			if (isZohoNameClearanceQQIndicationPopupFlag) {
				AccountUtils.populateError(ctx, "flagsNotCleard","All Referrals flags are not cleared for the policy.");
			}

		} catch (Exception e) {
			logger.error("Error in AccountantValidationUtils.validateNameClearanceQQIndicationFlagsCleared", e);
		}

	}
	
	public static boolean validateExistingZohoRecordInProdDB(Context ctx){
  		try{
  			if(ctx.get("MarketableProductKey") instanceof String)
  				ctx.put("MarketableProductKey",Integer.parseInt(ctx.get("MarketableProductKey").toString()));
    		
  			ctx.put("PolicyKey",Integer.parseInt(ctx.get("PolicyKey").toString()));
    		List existingNameClearanceDetails = SqlResources.getSqlMapProcessor(ctx).select("Policy.GetZohoNameClearanceFlagInJack_p", ctx);
    		ctx.put("PolicyKey",String.valueOf(ctx.get("PolicyKey")));
			if(existingNameClearanceDetails != null && !existingNameClearanceDetails.isEmpty()){
				return true;
			}
    		
  		} catch(Exception e){
            logger.error("Error in AccountantValidationUtils.validateExistingZohoRecordInProdDB", e);
  		}
		return false;
  	}
    //-- ZOHO end--
	
	 public static void mailSentBeyondWorkQCovrageDays(Context ctx) throws Exception {

		try{
			String emailID = SystemProperties.getInstance().getString("appl." + ctx.getProject() + ".mailidbeyondworkqcovragedays");
			String subject = "";
			String body = "";
			
			if ("".equals(emailID))
				return;
			String productionEnv = "N";
			try {
				productionEnv = SystemProperties.getInstance().getString("appl." + ctx.getProject() + ".environment.production");
			} catch (Exception e) {
				logger.error("error in geeting production environment", e);
			}
			
			if ("N".equals(productionEnv)){
				emailID = SystemProperties.getInstance().getString("appl." + ctx.getProject() + ".admin.email");
				{ if (logger.isDebugEnabled()) logger.debug("email id------->"+emailID); }
			}
			
			Context newCtx = new Context();
	    	newCtx.setProject(ctx.getProject());
	    	newCtx.put("LastUpdateTimeStamp",ctx.get("LastUpdateTimeStamp"));
	    	newCtx.put("LastUpdateUserID",ctx.get("LastUpdateUserID"));
	    	newCtx.put("UserNo", ctx.get("UserNo"));
	    	newCtx.put("PolicyKey", ctx.get("PolicyKey"));
	    	newCtx.put("mailSentBeyondWorkQCovrageDays", ctx.get("mailSentBeyondWorkQCovrageDays").toString());
			
			Object obj = SqlResources.getSqlMapProcessor(newCtx).select("SqlStmts.UserStatementManualBoQueriesmailSentBeyondWorkQCovrageDays", newCtx);
			
			if(obj != null && obj instanceof List){
	    		List list = (List)obj;
	    		for(int i=0; i<list.size(); i++){
	    			Map map = (Map)list.get(i);
	    			subject = map.get("subject").toString();
	    			body = map.get("body").toString();
	    		}
			}
			
			body = body.replace("#Firm_Name#", ctx.get("AccountName").toString());
			body = body.replace("#Quote_Number#", ctx.get("QuoteNumber").toString());
			body = body.replace("#Effective_Date#", ctx.get("PolicyEffectiveDate").toString().split(" ")[0]);

			logger.debug("Mail is going---- ");
			MailSender mail = new MailSender();
			mail.setToAdd(emailID);
			mail.setSubject(subject);
			mail.setContentType("text/html");
			mail.setBody(body);
			mail.sendMail((Context) ctx);
			logger.debug("Mail has sent---- ");
			
			SqlResources.getSqlMapProcessor(ctx).delete("SqlStmts.UserStatementManualBoQueriesUpdateMailSentBeyondWorkQCovrageDaysFlag", ctx);
			
		} catch(Exception e) {
			logger.error("Error occured while Update MailSentBeyondWorkQCovrageDays Flag", e);
		}

	}
	 
	 
	 public static void mailSentAfterPayment(Context ctx) throws Exception {

		try{
			String emailID = SystemProperties.getInstance().getString("appl." + ctx.getProject() + ".mailsentafterpayment");
			String subject = "";
			String body = "";
			String paymentMode = "";
			String firstReviewUserEmail = "";
			
			if ("".equals(emailID))
				return;
			
			String productionEnv = "N";
			try {
				productionEnv = SystemProperties.getInstance().getString("appl." + ctx.getProject() + ".environment.production");
			} catch (Exception e) {
				logger.error("error in geeting production environment", e);
			}
			
			if ("N".equals(productionEnv)){
				emailID = SystemProperties.getInstance().getString("appl." + ctx.getProject() + ".admin.email");
				{ if (logger.isDebugEnabled()) logger.debug("email id------->"+emailID); }
			}
			
			Context newCtx = new Context();
	    	newCtx.setProject(ctx.getProject());
	    	newCtx.put("LastUpdateTimeStamp",ctx.get("LastUpdateTimeStamp"));
	    	newCtx.put("LastUpdateUserID",ctx.get("LastUpdateUserID"));
	    	newCtx.put("UserNo", ctx.get("UserNo"));
	    	newCtx.put("PolicyKey", ctx.get("PolicyKey"));
	    	newCtx.put("mailSentAfterPayment", ctx.get("mailSentAfterPayment").toString());
	    	
	    	Object getFirstReviewUserEmailObj = SqlResources.getSqlMapProcessor(newCtx).select("SqlStmts.UserStatementManualBoQueriesgetFirstReviewUserEmail", newCtx);
			
			if(getFirstReviewUserEmailObj != null && getFirstReviewUserEmailObj instanceof List){
	    		List getFirstReviewUserEmailList = (List)getFirstReviewUserEmailObj;
	    		for(int i=0; i<getFirstReviewUserEmailList.size(); i++){
	    			Map getFirstReviewUserEmailMap = (Map)getFirstReviewUserEmailList.get(i);
	    			firstReviewUserEmail = getFirstReviewUserEmailMap.get("AliasEmail").toString();
	    		}
			}
			
			Object obj = SqlResources.getSqlMapProcessor(newCtx).select("SqlStmts.UserStatementManualBoQueriesmailSentAfterPayment", newCtx);
			
			if(obj != null && obj instanceof List){
	    		List list = (List)obj;
	    		for(int i=0; i<list.size(); i++){
	    			Map map = (Map)list.get(i);
	    			subject = map.get("subject").toString();
	    			body = map.get("body").toString();
	    		}
			}
			
			body = body.replace("#Firm_Name#", ctx.get("AccountName").toString());
			body = body.replace("#Quote_Number#", ctx.get("QuoteNumber").toString());
			body = body.replace("#Effective_Date#", ctx.get("PolicyEffectiveDate").toString().split(" ")[0]);
			
			if("eft".equals(ctx.get("PaymentMode"))) {
				paymentMode = "Manual Check";
			} else if("paypal".equals(ctx.get("PaymentMode"))) {
				paymentMode = "Debit/Credit Card";
			} else if("ach".equals(ctx.get("PaymentMode"))) {
				paymentMode = "ACH";
			} else if("finance".equals(ctx.get("PaymentMode"))) {
				paymentMode = "Financing";
				
				if("EFT".equals(ctx.get("FinanceDownPayment"))) {
					paymentMode += " (" + "Credit Card/ACH, " + ctx.get("FinanceInterval") + ")";
				} else {
					paymentMode += " (" + "Manual Check, " + ctx.get("FinanceInterval") + ")";
				}
			}
			
			body = body.replace("#Payment_Mode#", paymentMode);
			
			subject = ctx.get("QuoteNumber").toString() + ", " + ctx.get("AccountName").toString() + ", " + paymentMode;

			logger.debug("Mail is going---- ");
			MailSender mail = new MailSender();
			mail.setToAdd(firstReviewUserEmail);
			mail.setBccAdd(emailID);
			mail.setSubject(subject);
			mail.setContentType("text/html");
			mail.setBody(body);
			mail.sendMail((Context) ctx);
			logger.debug("Mail has sent---- ");
			
		} catch(Exception e) {
			logger.error("Error occured while Update mailSentAfterPayment Flag", e);
		}

	}
	 public static boolean isCFAllowedToIssuePolicy(Context ctx) throws Exception 
		{
			try {
				AccountUtils.setCompanyForPolicy(ctx);
				return "3".equals(String.valueOf(ctx.get("CompanyKey")));
			} catch(Exception e) {
				logger.error("error occured while validating status :"+e, e);
			}
			return false;
		}
	 
	 public static boolean validateIPFSPolicyEffDate29Days(IContext ctx)
				throws Exception {

			return validatePaymentPolicyEffDate29Days(ctx, "AccountantRule.isIPFSShowToInsured");
		}
		public static boolean validatePaypalPolicyEffDate29Days(IContext ctx)
				throws Exception {

			return validatePaymentPolicyEffDate29Days(ctx, "AccountantRule.showPaypalDiv");
		}
		public static boolean validateACHPolicyEffDate29Days(IContext ctx)
				throws Exception {

			return validatePaymentPolicyEffDate29Days(ctx, "AccountantRule.showACHDiv");
		}
		
		public static boolean validatePaymentPolicyEffDate29Days(IContext ctx, String existingRule)
				throws Exception {

			Context newCtx=new Context();
			newCtx.setProject(ctx.getProject());
			newCtx.putAll(ctx);

			Map map = (Map) SqlResources.getSqlMapProcessor(newCtx).findByKey("Firm.findByKey", newCtx);
			if (map != null && map.size() > 0) {
				newCtx.put("QuoteEffectiveDate", map.get("QuoteEffectiveDate"));
				newCtx.put("QuoteExpiryDate", map.get("QuoteExpiryDate"));
				newCtx.put("QuoteSentDate", map.get("QuoteSentDate"));
			}
			
			Object objQuoteExp = RuleUtils.executeRule(newCtx, "AccountantRule.isPEDLessFrmQEDAndInsured");
			boolean flag = false;
			if (objQuoteExp != null && objQuoteExp instanceof Boolean)
				flag = (Boolean) objQuoteExp;

			boolean flagQuoteDate = false;
			if(flag) {
				//Less QuoteEffectiveDate
				Object objQuoteExpDate = RuleUtils.executeRule(newCtx, existingRule);
				if (objQuoteExpDate != null && objQuoteExpDate instanceof Boolean)
					flagQuoteDate = (Boolean) objQuoteExpDate;
			} else {
				//Greater QuoteEffectiveDate
				Object objPolciyEffDate = RuleUtils.executeRule(newCtx, "AccountantRule.isPolicyEffDate5DaysAndInsured");
				if (objPolciyEffDate != null && objPolciyEffDate instanceof Boolean)
					flagQuoteDate = (Boolean) objPolciyEffDate;
				
				if(flagQuoteDate)
					flagQuoteDate = QuoteLetter.isQuoteEffDateGreater5DaysLess29DaysFrmPED(newCtx, newCtx.get("PolicyEffectiveDate").toString(), newCtx.get("QuoteSentDate").toString());
			}

			if (!flagQuoteDate){
				return true;
			} else {
				return false;
			}
		}
		
		public static boolean showConfirmationPage29Days(IContext ctx)
				throws Exception {

			Context newCtx=new Context();
			newCtx.setProject(ctx.getProject());
			newCtx.putAll(ctx);

			Map map = (Map) SqlResources.getSqlMapProcessor(newCtx).findByKey("Firm.findByKey", newCtx);
			if (map != null && map.size() > 0) {
				newCtx.put("QuoteEffectiveDate", map.get("QuoteEffectiveDate"));
				newCtx.put("QuoteExpiryDate", map.get("QuoteExpiryDate"));
				newCtx.put("QuoteSentDate", map.get("QuoteSentDate"));
			}
			
			Object objQuoteExp = RuleUtils.executeRule(newCtx, "AccountantRule.isPEDLessFrmQEDAndInsured");
			boolean flag = false;
			if (objQuoteExp != null && objQuoteExp instanceof Boolean)
				flag = (Boolean) objQuoteExp;

			boolean flagQuoteDate = false;
			if(flag) {
				//Less QuoteEffectiveDate
				Object objQuoteExpDate = RuleUtils.executeRule(newCtx, "AccountantRule.isQuotationExpiredAndInsured");
				if (objQuoteExpDate != null && objQuoteExpDate instanceof Boolean)
					flagQuoteDate = (Boolean) objQuoteExpDate;
			} else {
				//Greater QuoteEffectiveDate
				Object objPolciyEffDate = RuleUtils.executeRule(newCtx, "AccountantRule.isPolicyEffDate5DaysAndInsured");
				if (objPolciyEffDate != null && objPolciyEffDate instanceof Boolean)
					flagQuoteDate = (Boolean) objPolciyEffDate;
				
				if(flagQuoteDate)
					flagQuoteDate = QuoteLetter.isQuoteEffDateGreater5DaysLess29DaysFrmPED(newCtx, newCtx.get("PolicyEffectiveDate").toString(), newCtx.get("QuoteSentDate").toString());
			}

			if (!flagQuoteDate){
				return true;
			} else {
				return false;
			}
		}
}
