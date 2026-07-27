package com.userbo;

import com.util.InetLogger;
import com.util.SystemProperties;

public class AccountantConstants {

	private static InetLogger logger = InetLogger.getInetLogger(AccountantConstants.class);
	public static String LEVEL1FORMCHANGES = "level1FormChanges";
	//Endorsement Start
		public static final String CANCELLATION_ENDORSEMENT = "7";
		public static final String REINSTATEMENT_ENDORSEMENT = "8";
		public static final String LIMIT_ENDORSEMENT = "9";
		public static final String DEDUCTIBLE_ENDORSEMENT = "10";
		public static final String NAMEADDRESS_ENDORSEMENT = "11";
		public static final String PRIORACTDATE_ENDORSEMENT = "12";
		public static final String POLICYEXTENSION_ENDORSEMENT = "13";
		public static final String INSURANCE_CERTIFICATE = "17";
		public static final String ADDCHANGEATTORNEY_ENDORSEMENT = "14";
		public static final String DELETEATTORNEY_ENDORSEMENT = "15";
		public static final String ERP_ENDORSEMENT = "16";
		public static final String ENDORSEMENT_EMAIL = "EndorsementEmailAcc";
		public static final String CANCELLATIONENDORSEMENT_EMAIL = "CancellationEndorsementAcc";
		public static final String REINSTATEMENTENDORSEMENT_EMAIL = "ReinstatementEndorsementAcc";
		public static final String INSURANCECERTIFICATE_EMAIL = "InsuranceCertificateAcc";
		public static final String QUOTELETTER_EMAIL = "ERPQuoteLetterAcc";
		public static final int DAYS_OF_YEAR =AccountUtils.getDaysOfYear();
		
		public static int CLAIM_AGE_COUNT = 12;
		public static String NEWAPPFLOW_CUT_OFF_DATE = "01/01/2050";
		public static String CUT_OFF_DATE = "01/01/2050";
		public static String CUT_OFF_DATE_GROUP2 = "01/01/2050";	
		public static String CUT_OFF_DATE_GROUP3 = "01/01/2050";
		public static String CUT_OFF_DATE_GROUP4 = "01/01/2050";
		public static String CUT_OFF_DATE_GROUP5 = "01/01/2050";
		public static String CUT_OFF_DATE_GROUP6 = "01/01/2050";
		public static String CUT_OFF_DATE_GROUP7 = "01/01/2050";
		public static String CUT_OFF_DATE_GROUP8 = "01/01/2050";
		public static String CUT_OFF_DATE_CCBSupp = "01/01/2050";
		public static String CUT_OFF_DATE_2020 = "01/01/2050";
		public static String END_DATE_CF_POLICYISSUANCE = "03/31/2022";

	static {
		
		try {
			
			LEVEL1FORMCHANGES = SystemProperties.getInstance().getString("appl.AccountantIns.formchanges.level1.Date", LEVEL1FORMCHANGES);
			
		} catch (Exception e) {
			logger.error("error in Assigning dates for form changes", e);
		}
		
		try {
			NEWAPPFLOW_CUT_OFF_DATE = SystemProperties.getInstance().getString("appl.AccountantIns.newAppFlow.cutOffDate", NEWAPPFLOW_CUT_OFF_DATE);
			CUT_OFF_DATE = SystemProperties.getInstance().getString("appl.AccountantIns.cutOffDate", CUT_OFF_DATE);
			CUT_OFF_DATE_GROUP2 = SystemProperties.getInstance().getString("appl.AccountantIns.Group2.cutOffDate", CUT_OFF_DATE_GROUP2);
			CUT_OFF_DATE_GROUP3 = SystemProperties.getInstance().getString("appl.AccountantIns.Group3.cutOffDate", CUT_OFF_DATE_GROUP3);
			CUT_OFF_DATE_GROUP4 = SystemProperties.getInstance().getString("appl.AccountantIns.Group4.cutOffDate", CUT_OFF_DATE_GROUP4);
			CUT_OFF_DATE_GROUP5 = SystemProperties.getInstance().getString("appl.AccountantIns.Group5.cutOffDate", CUT_OFF_DATE_GROUP5);
			CUT_OFF_DATE_GROUP6 = SystemProperties.getInstance().getString("appl.AccountantIns.Group6.cutOffDate", CUT_OFF_DATE_GROUP6);
			CUT_OFF_DATE_GROUP7 = SystemProperties.getInstance().getString("appl.AccountantIns.Group7.CutOffDate", CUT_OFF_DATE_GROUP7);
			CUT_OFF_DATE_GROUP8 = SystemProperties.getInstance().getString("appl.AccountantIns.Group8.cutOffDate", CUT_OFF_DATE_GROUP8);
			CUT_OFF_DATE_CCBSupp = SystemProperties.getInstance().getString("appl.AccountantIns.CCBSupp.cutOffDate", CUT_OFF_DATE_CCBSupp);
			CUT_OFF_DATE_2020 = SystemProperties.getInstance().getString("appl.AccountantIns.Group1.cutOffDate.2020", CUT_OFF_DATE_2020);
			END_DATE_CF_POLICYISSUANCE= SystemProperties.getInstance().getString("appl.AccountantIns.CF.policyissuance", END_DATE_CF_POLICYISSUANCE);
		} catch (Exception e) {
			logger.error("CutOff dates are not defined for rating/new app flow", e);
		}
	}
}
