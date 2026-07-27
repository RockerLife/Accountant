package com.userbo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.manage.managecomponent.components.SetParametersForStoredProcedures;
import com.manage.managemetadata.functions.commonfunctions.DBUtils;
import com.manage.managemetadata.functions.commonfunctions.RuleUtils;
import com.ormapping.ibatis.SqlResources;
import com.util.Context;
import com.util.HtmlConstants;
import com.util.IContext;
import com.util.InetLogger;

public class AOP {

	private Context ctx1;
	public final String[] referredStates = { "FL", "NY", "AR", "OK","SD" };
	private static InetLogger logger = InetLogger
	.getInetLogger(AccountUtils.class);

	public boolean validateAOPComment(IContext ctx, List limtTypes)
			throws Exception {

		boolean flag = false;
		Map map = new HashMap();
		for (int i = 0; i < limtTypes.size(); i++) {
			map = (HashMap) limtTypes.get(i);
			if (map.get("AOPKey").toString().equals("9")
					|| map.get("AOPKey").toString().equals("10")
					|| map.get("AOPKey").toString().equals("12")
					|| map.get("AOPKey").toString().equals("20")
					|| map.get("AOPKey").toString().equals("21")) {
				String percentageValue = ctx.get("AOP_Percentage_"
						+ map.get("AOPKey").toString()) == null ? null : ctx
						.get("AOP_Percentage_" + map.get("AOPKey").toString())
						.toString();
				if (!(percentageValue == null || "".equals(percentageValue) || "0"
						.equals(percentageValue))) {
					String comment = ctx.get("AOPCommentDesc_"
							+ map.get("AOPKey").toString()) == null ? null
							: ctx.get(
									"AOPCommentDesc_"
											+ map.get("AOPKey").toString())
									.toString();
					if (comment == null || "".equals(comment)) {
						new AccountUtils().populateError(ctx, "AOPCommentDesc_"
								+ map.get("AOPKey").toString(),
								"Fill in please describe");
						flag = true;
					}
				}
			}
		}

		return flag;
	}

	public void savePopulateFields(IContext ctx) throws Exception {

		List limtTypes = SqlResources.getSqlMapProcessor(ctx).select(
				"AOP.findAll", ctx);

		if (limtTypes != null) {
			int totalAOPpercent = checkAOPPercentage(limtTypes, ctx);
			// if(totalAOPpercent==100)
			// {
			// if(!validateAOPComment(ctx, limtTypes))
			// {
			try {
				Map map = new HashMap();
				for (int i = 0; i < limtTypes.size(); i++) {
					map = (HashMap) limtTypes.get(i);
					String percentageValue = ctx.get("AOP_Percentage_"
							+ map.get("AOPKey").toString()) == null ? null
							: ctx.get(
									"AOP_Percentage_"
											+ map.get("AOPKey").toString())
									.toString().trim();
					if (percentageValue == null || "".equals(percentageValue)
							|| "0".equals(percentageValue))
						percentageValue = "0";

					ctx1 = new Context();
					ctx1.put("VersionSequence", ctx.get("VersionSequence"));
					ctx1.put("PolicyKey", ctx.get("PolicyKey"));
					ctx1.put("PercentageValue", percentageValue);
					if (map.get("AOPKey").toString().equals("9")
							|| map.get("AOPKey").toString().equals("10")
							|| map.get("AOPKey").toString().equals("12")
							|| map.get("AOPKey").toString().equals("20")
							|| map.get("AOPKey").toString().equals("21"))
						ctx1.put("AOPCommentDesc", ctx.get("AOPCommentDesc_"
								+ map.get("AOPKey").toString()));

					ctx1.put("AOPKey", map.get("AOPKey"));

					ctx1.setProject(ctx.getProject());
					Object obj2 = DBUtils.executeDBOperation(ctx1,
							"AreaOfPractice", "3");

					if (obj2 == null) {
						DBUtils.executeDBOperation(ctx1, "AreaOfPractice", "1");

					} else {
						DBUtils.executeDBOperation(ctx1, "AreaOfPractice", "2");
						DBUtils.executeDBOperation(ctx1, "AreaOfPractice", "1");
					}
				}
			} catch (Exception e) {
				logger.error("Error in AOP.savePopulateFields", e);
			}
			// }
			// }
		}

	}

	public void savePopulateFieldsQuickQuote(IContext ctx) throws Exception {

		List limtTypes = SqlResources.getSqlMapProcessor(ctx).select(
				"AOP.findAll", ctx);

		if (limtTypes != null) {
			int totalAOPpercent = checkAOPPercentage(limtTypes, ctx);
			// if(totalAOPpercent==100)
			// {
			// if(!validateAOPComment(ctx, limtTypes))
			// {
			try {
				Map map = new HashMap();
				for (int i = 0; i < limtTypes.size(); i++) {
					map = (HashMap) limtTypes.get(i);
					String percentageValue = ctx.get("AOP_Percentage_"
							+ map.get("AOPKey").toString()) == null ? null
							: ctx.get(
									"AOP_Percentage_"
											+ map.get("AOPKey").toString())
									.toString().trim();
					if (percentageValue == null || "".equals(percentageValue)
							|| "0".equals(percentageValue))
						percentageValue = "0";

					ctx1 = new Context();
					ctx1.put("VersionSequence", ctx.get("VersionSequence"));
					ctx1.put("LastUpdateTimeStamp", ctx.get("LastUpdateTimeStamp"));
					ctx1.put("PolicyKey", ctx.get("PolicyKey"));
					ctx1.put("PercentageValue", percentageValue);
					ctx1.put("CreatedDate", ctx.get("CreatedDate"));
					ctx1.put("CreatedBy", ctx.get("CreatedBy"));
					ctx1.put("VersionKey", ctx.get("VersionKey"));
					
					if (map.get("AOPKey").toString().equals("9")
							|| map.get("AOPKey").toString().equals("10")
							|| map.get("AOPKey").toString().equals("12")
							|| map.get("AOPKey").toString().equals("20")
							|| map.get("AOPKey").toString().equals("21"))
						ctx1.put("AOPCommentDesc", ctx.get("AOPCommentDesc_"
								+ map.get("AOPKey").toString()));

					ctx1.put("AOPKey", map.get("AOPKey"));
					ctx1.setProject(ctx.getProject());

					AccountUtils.populateLastUpdateTimeStamp(ctx1);

					Object obj2 = DBUtils.executeDBOperation(ctx1,
							"AreaOfPracticeQuickQuote", "3");

					if (obj2 == null) {
						DBUtils.executeDBOperation(ctx1,
								"AreaOfPracticeQuickQuote", "1");

					} else {
						DBUtils.executeDBOperation(ctx1,
								"AreaOfPracticeQuickQuote", "2");
						DBUtils.executeDBOperation(ctx1,
								"AreaOfPracticeQuickQuote", "1");
					}
				}
			} catch (Exception e) {
				logger.error("Error in AOP.savePopulateFieldsQuickQuote", e);
			}
			// }
			// }
		}

	}

	private int checkAOPPercentage(List limtTypes, IContext ctx)
			throws Exception {
		int total = 0;
		Map map = new HashMap();
		for (int i = 0; i < limtTypes.size(); i++) {
			map = (HashMap) limtTypes.get(i);
			String percentageValue = ctx.get("AOP_Percentage_"
					+ map.get("AOPKey").toString()) == null ? null : ctx.get(
					"AOP_Percentage_" + map.get("AOPKey").toString())
					.toString().trim();
			if (percentageValue == null || "".equals(percentageValue))
				percentageValue = "0";

			total = total + Integer.parseInt(percentageValue);
		}

		if (total < 100 || total > 100) {
			AccountUtils.percentageError(ctx, total, "100");
		}

		return total;
	}

	@SuppressWarnings("unchecked")
	public void populateAOPFields(IContext ctx) throws Exception {
		List aoplist = SqlResources.getSqlMapProcessor(ctx).select(
				"SqlStmts.UserStatementsaveAccountstmtspopulateAOPFields", ctx);
		//ctx.remove("AOP_freeform_20");

		Map covMap = new HashMap();
		if (aoplist != null) {
			Map map = new HashMap();
			for (int i = 0; i < aoplist.size(); i++) {
				map = (HashMap) aoplist.get(i);
				ctx.put("AOP_Percentage_" + map.get("AOPKey"), map
						.get("PercentageValue"));
				// ctx.put("AOP_Percentage_"+map.get("AOPKey"),
				// map.get("PercentageValue"));
				ctx.put("AOPCommentDesc_" + map.get("AOPKey"), map
						.get("AOPCommentDesc"));
			}
		}

//		ctx.put("AOP_Percentage_5", covMap.get("AOP_Percentage_5"));
//		ctx.put("AOP_Percentage_6", covMap.get("AOP_Percentage_6"));
//		ctx.put("AOP_Percentage_14", covMap.get("AOP_Percentage_14"));
//		ctx.put("AOP_Percentage_18", covMap.get("AOP_Percentage_18"));
//		ctx.put("AOP_Percentage_19", covMap.get("AOP_Percentage_19"));
//
//		ctx.put("AOP_freeform_20", covMap);
		// return covMap;

	}

	@SuppressWarnings("unchecked")
	public void populateAOPFieldsQuickQuote(IContext ctx) throws Exception {
		List aoplist = SqlResources
				.getSqlMapProcessor(ctx)
				.select(
						"SqlStmts.UserStatementsaveAccountstmtspopulateAOPFieldsQuickQuote",
						ctx);
		ctx.remove("AOP_freeform_20");

		Map covMap = new HashMap();
		if (aoplist != null) {
			Map map = new HashMap();
			for (int i = 0; i < aoplist.size(); i++) {
				map = (HashMap) aoplist.get(i);
				covMap.put("AOP_Percentage_" + map.get("AOPKey"), map
						.get("PercentageValue"));
				// ctx.put("AOP_Percentage_"+map.get("AOPKey"),
				// map.get("PercentageValue"));
				covMap.put("AOPCommentDesc_" + map.get("AOPKey"), map
						.get("AOPCommentDesc"));
				
				ctx.put("AOP_Percentage_" + map.get("AOPKey"), map
						.get("PercentageValue"));
				// ctx.put("AOP_Percentage_"+map.get("AOPKey"),
				// map.get("PercentageValue"));
				ctx.put("AOPCommentDesc_" + map.get("AOPKey"), map
						.get("AOPCommentDesc"));
			}
		}

		ctx.put("AOP_freeform_20", covMap);
		// return covMap;

	}

	public void populateGrossRevenue(IContext ctx) throws Exception {

		long total = 0;
		int count = 0;
		List grRevList = null;
		if (ctx.get("BasicInfo_list_mom_03") != null) {
			grRevList = (List) ctx.get("BasicInfo_list_mom_03");
		}
		if (grRevList != null) {
			Map map = new HashMap();
			for (int i = 0; i < grRevList.size(); i++) {
				map = (HashMap) grRevList.get(i);
				Object objAmount = map.get("Amount");
				if(ctx.get("inet_errors_list")==null)
				{
					objAmount = map.get("Amount");
					ctx.put("Amount_" + i, map.get("Amount") == null ? "" : map
							.get("Amount").toString());
				}
				else
				{
					objAmount = ctx.get("Amount_"+i);
				}
					
				if (objAmount != null && !"".equals(objAmount.toString())
						&& !"0".equals(objAmount.toString())) {
					total = total + Long.parseLong(objAmount.toString().replace("$","").replace(",",""));
					count = count + 1;
					ctx.put("Amount_" + i, objAmount.toString());
				} else
					ctx.put("Amount_" + i, "");
			}

			if (count > 0) {
				long AverageRevenue = total / count;
				ctx.put("AverageRevenue", AverageRevenue);
			} else
				ctx.put("AverageRevenue", 0);
		}

	}

	public void populateGrossRevenueRenew(IContext ctx) throws Exception {

		long total = 0;
		int count = 0;
		List grRevList = null;
		if (ctx.get("BasicInfo_list_mom_03") != null) {
			grRevList = (List) ctx.get("BasicInfo_list_mom_03");
		}

		if (grRevList != null) {
			Map map = new HashMap();
			
			map = (HashMap) grRevList.get(0);			
			String policyeffectivedate = ctx.get("PolicyEffectiveDate") != null ? ctx.get("PolicyEffectiveDate").toString() : "" ;
			Object obj = RuleUtils.executeRule(ctx,"AccountantRule.IsRenewBasicInfoPageNotSaved");
			if (obj != null && obj instanceof Boolean) {
				boolean isRenewBasicNotSaved = (Boolean) obj;
				
				if (isRenewBasicNotSaved) {
					
					boolean swap = swapYears(map.get("YearEndDate") == null ? "" : map.get("YearEndDate").toString(),policyeffectivedate);
					Object objIsNotSwapped = RuleUtils.executeRule(ctx, "AccountantRule.IsNotSwapped");
					boolean isNotSwapped = false ;
					if(objIsNotSwapped !=null && objIsNotSwapped instanceof Boolean)
					{
						isNotSwapped = (Boolean) objIsNotSwapped ;
					}
					if(swap && isNotSwapped){
						SqlResources.getSqlMapProcessor(ctx).select("GrossRevenueForFirm.SwapRevenueYear", ctx);
					}
					String yearEndDate = populateYearEndDate(map.get("YearEndDate") == null ? "" : map.get("YearEndDate").toString(),policyeffectivedate);
					{ if (logger.isDebugEnabled()) logger.debug("Year End Date = " + yearEndDate); }
					ctx.put("YearEndDate", yearEndDate);			
					
				}else{
					
					ctx.put("YearEndDate", map.get("YearEndDate"));
					ctx.put("Amount", map.get("Amount"));
				}
			}

			for (int i = 0; i < grRevList.size(); i++) {
				map = (HashMap) grRevList.get(i);
				Object objAmount = map.get("Amount");

				if (objAmount != null && !"".equals(objAmount.toString())
						&& !"0".equals(objAmount.toString())) {
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

	public String populateYearEndDate(String yearEndDate,String policyeffectivedate) throws Exception {
		try{
		{ if (logger.isDebugEnabled()) logger.debug("Going to populate Year End Date with year end date " + yearEndDate +
				" and Policy effective date " + policyeffectivedate); }
		policyeffectivedate = policyeffectivedate.substring(0,10);
		Date d = AccountantDateUtils.convertStringToDate(policyeffectivedate, "mm/dd/yyyy");
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		int month = cal.get(Calendar.MONTH);
		int policyyear = cal.get(Calendar.YEAR);
		month = month + 1 ;
		if (!"".equals(yearEndDate)) {

			if (yearEndDate.indexOf("/") > 0) {
				String stryear = yearEndDate
						.substring(yearEndDate.indexOf("/") + 1);
				int grossyear = AccountantValidationUtils
						.convertStringToInteger(stryear);
				
				if(grossyear != (policyyear - 1))
				{
					grossyear = policyyear - 1 ;
				}
				
				{ if (logger.isDebugEnabled()) logger.debug("Year is " + grossyear); }
				String newStrYear = yearEndDate.replace(stryear, String
						.valueOf(grossyear));

				return newStrYear;
			}
		}
		}catch(Exception e){
			logger.error("Exception in populateYearEndDate " + e.getMessage(), e);
			throw e ;
		}
		return yearEndDate;

	}
	
	public boolean swapYears(String yearEndDate,String policyeffectivedate) throws Exception {
		boolean swapyears = false ;
		try{
			
		{ if (logger.isDebugEnabled()) logger.debug("Going to populate Year End Date with year end date " + yearEndDate +
				" and Policy effective date " + policyeffectivedate); }
		policyeffectivedate = policyeffectivedate.substring(0,10);
		Date d = AccountantDateUtils.convertStringToDate(policyeffectivedate, "mm/dd/yyyy");
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		int month = cal.get(Calendar.MONTH);
		int policyyear = cal.get(Calendar.YEAR);
		month = month + 1 ;
		if (!"".equals(yearEndDate)) {

			if (yearEndDate.indexOf("/") > 0) {
				String stryear = yearEndDate
						.substring(yearEndDate.indexOf("/") + 1);
				int grossyear = AccountantValidationUtils
						.convertStringToInteger(stryear);
				
				if(grossyear == (policyyear - 2)){
					swapyears = true ;
				}
			}
		}
		}catch(Exception e){
			logger.error("Exception in populateYearEndDate " + e.getMessage(), e);
			throw e ;
		}
		return swapyears;

	}
	
	public boolean incrementYear(IContext ctx) throws Exception{
		
		Object obj = SqlResources.getSqlMapProcessor(ctx).select("SqlStmts.UserStatementrenewapplicationgetAllAccountOfAnAccountID", ctx);
		
		if(obj != null && obj instanceof List){
			
			List accountList = (List)obj;
			
			if(accountList.size() > 2)
				return true ;
			
		}
		
		return false ;
		
		
	}

	public void populateGrossRevenueQuickQuote(IContext ctx) throws Exception {

		List grRevList = null;
		if (ctx.get("BasicInfo_list_mom_03") != null) {
			grRevList = (List) ctx.get("BasicInfo_list_mom_03");
		}

		if (grRevList != null) {
			Map map = (HashMap) grRevList.get(0);
			ctx.put("Amount", map.get("Amount") == null ? "" : map
					.get("Amount").toString());
		}
	}

	public void showQuickQuoteList(IContext ctx) throws Exception {

		boolean flag = true;

		{ if (logger.isDebugEnabled()) logger.debug(ctx.get("IsInsuredViewed")); }

		AccountUtils.populateLastUpdateTimeStamp(ctx);

		Object objAmount = ctx.get("Amount");
		if (objAmount != null) {
			long amt = Long.parseLong(objAmount.toString());
			if (amt > 1000000 )
				flag = false;
		}

		/*Object objOther = ctx.get("AOP_Percentage_20");
		if (objOther != null) {
			int per = Integer.parseInt(objOther.toString());
			if (per > 0)
				flag = false;
		}
		Object objClaim = ctx.get("IsClaimsInLast5Year");
		if (objClaim != null) {
			if ("Y".equals(objClaim.toString()))
				flag = false;
		}

		Object objDate1 = ctx.get("PolicyEffectiveDate");
		if (objDate1 != null) {

			/*Date policyEffDate = DateUtils.convertStringToDate(objDate1
					.toString(), "mm/dd/yyyy");
			long diff = DateUtils.calculateDiffInDays(new Date(objDate1.toString()), DateUtils
					.getTodaysDate());
			if (diff < 5)
				flag = false;
		}
	 */
		Object objStateCode = ctx.get("StateCode");
		if (objStateCode != null) {
			for (int i = 0; i < referredStates.length; i++) {
				if (ctx.get("StateCode").toString().equals(referredStates[i])) {
					flag = false;
					break;
				}
			}
		}

		if (flag)
			ctx.put("hideQuoteList", "N");
		else {
			/*if (ctx.get("User") != null) {
				String user = ctx.get("User").toString();
				if ("insured".equals(user))
					ctx.put("hideQuoteList", "Y");
				else
					ctx.put("hideQuoteList", "N");
			}*/
			ctx.put("hideQuoteList", "Y");
		}

	}

	public static void main(String args[]) throws Exception {

		String yeardate = "12/2011";
		if (yeardate.indexOf("/") > 0) {
			String year = yeardate.substring(yeardate.indexOf("/") + 1);
			int intyear = AccountantValidationUtils
					.convertStringToInteger(year);

			int newYear = intyear + 1;
			String newStrYear = yeardate.replace(year, String.valueOf(newYear));

			{ if (logger.isDebugEnabled()) logger.debug("=====> " + newStrYear); }
		}

	}

	/// indication in jack
	
	public static void populateIndicationAOP(IContext ctx) throws Exception {
		List aoplist = SqlResources.getSqlMapProcessor(ctx).select("AOP.GetAOPFieldsIndication_p", ctx);
		
		if (aoplist != null) {
			Map map = new HashMap();
			for (int i = 0; i < aoplist.size(); i++) {
				map = (HashMap) aoplist.get(i);
				if(ctx.get("AOP_Percentage_" + map.get("AOPKey")) != null && !"".equals(ctx.get("AOP_Percentage_" + map.get("AOPKey"))) && !"0".equals(ctx.get("AOP_Percentage_" + map.get("AOPKey")))){
					ctx.put("AOP_Percentage_" + map.get("AOPKey"), ctx.get("AOP_Percentage_" + map.get("AOPKey")));
				} else if(map.get("PercentageValue") != null &&  !"".equals(map.get("PercentageValue").toString().trim()) && !"0".equals(map.get("PercentageValue").toString().trim())){
					ctx.put("AOP_Percentage_" + map.get("AOPKey"), map.get("PercentageValue"));
				} else {
					ctx.put("AOP_Percentage_" + map.get("AOPKey"), null);
				}
			}
		}
	}
	
	public static void fillIndicationAOP(IContext ctx) throws Exception {
		int deletedRows = SqlResources.getSqlMapProcessor(ctx).delete("SqlStmts.UserStatementsaveAccountstmtsdeleteAreaOfPractice", ctx);
		List aopList = SqlResources.getSqlMapProcessor(ctx).select("AOP.findAll", ctx);
		Context ctx1;
		
		if (aopList != null) {
			
			try {
				Map map = new HashMap();
				for (int i = 0; i < aopList.size(); i++) {
					map = (HashMap) aopList.get(i);
					String percentageValue = ctx.get("AOP_Percentage_" + map.get("AOPKey").toString()) == null ? null
							: ctx.get("AOP_Percentage_" + map.get("AOPKey").toString()).toString().trim();
					
					if (percentageValue == null || "".equals(percentageValue) || "0".equals(percentageValue))
						percentageValue = "0";
					
					ctx1 = new Context();
					ctx1.put("AOPKey", map.get("AOPKey"));
					ctx1.put("PolicyKey", ctx.get("PolicyKey"));
					ctx1.put("VersionSequence", ctx.get("VersionSequence"));
					ctx1.put("PercentageValue", percentageValue);
					ctx1.put("Percentage", percentageValue);	
					ctx1.put("VersionKey", ctx.get("VersionKey"));	
					ctx1.put("CreatedDate", ctx.get("CreatedDate"));	
					ctx1.put("CreatedBy", ctx.get("CreatedBy"));	
					ctx1.setProject(ctx.getProject());
					DBUtils.executeDBOperation(ctx1, "AreaOfPractice", "1");
//					if(!"0".equals(percentageValue))
//						DBUtils.executeDBOperation(ctx1, "SmartSearchValue", "1");
				}
			} catch (Exception e) {
				logger.error("Error in AOP.fillIndicationAOP", e);
			}			
		}
	}
	public static void getAopsValues(Context ctx)
	{
			try 
			{
				int percentage=0;
				/*if(ctx.get(Constants.INET_ERRORS_LIST) != null){
					return ;
				}*/
				
				List aoplist = SqlResources.getSqlMapProcessor(ctx).select("SqlStmts.UserStatementManualBoQueriesgetAOPDetails", ctx);
				Map covMap = new HashMap();
				if (aoplist != null) 
				{
					Map map = new HashMap();
					for (int i = 0; i < aoplist.size(); i++) 
					{
						map = (HashMap) aoplist.get(i);
						percentage=map.get("PercentageValue")!=null && !map.get("PercentageValue").equals(HtmlConstants.EMPTY)?Integer.parseInt(map.get("PercentageValue").toString()):0;
						ctx.put("aopPercentage_" + map.get("AOPKey"),percentage );
						
					}
				}
				List lastyearRevenue = SqlResources.getSqlMapProcessor(ctx).select("SqlStmts.UserStatementManualBoQueriesgetLastYearRevenue", ctx);
				ctx.putAll((Map) lastyearRevenue.get(0));
			}
			catch (Exception e)
			{
				logger.error("Error in AOP.getAopsValues", e);
			}
	}
	
	public void savePopulateFieldsNewApp(IContext ctx)throws Exception {
		List finalList = new ArrayList();
		if (ctx.get("aoplist_list_2") != null && ctx.get("aoplist_list_2")instanceof List) {
			finalList = (List)ctx.get("aoplist_list_2");

			Map map = new HashMap();
			for (int i = 0; i < finalList.size(); i++) {
				Context ctx1 = new Context();
				ctx1.setProject(ctx.getProject());
				map = (HashMap)finalList.get(i);
				ctx1.put("PolicyKey", ctx.get("PolicyKey"));
				ctx1.put("AOPkey", map.get("Aopkey"));
				ctx1.put("percentage", ctx.get("AOP_Percentage_" + i));
				SqlResources.getSqlMapProcessor(ctx1).update("SqlStmts.UserStatementManualBoQueriesupdateSmartSearchValueACC", ctx1);
			}
		}
		
		
		Context ctx2 = new Context();
		ctx2.setProject(ctx.getProject());
		ctx2.putAll(ctx);
		if(finalList != null){
			for (int i = 0; i < finalList.size(); i++) {
				ctx2.remove("AOP_Percentage_" + i);
	
			}
			Map map1 = new HashMap();
			for (int i = 0; i < finalList.size(); i++) {
				map1 = (HashMap)finalList.get(i);
				ctx2.put("AOP_Percentage_" + map1.get("Aopkey"), ctx.get("AOP_Percentage_" + i));
			}
		}
		
		List limtTypes = SqlResources.getSqlMapProcessor(ctx).select("AOP.findAll", ctx);
		if (limtTypes != null) {
			try {
				Map map = new HashMap();
				for (int i = 0; i < limtTypes.size(); i++) {
					map = (HashMap)limtTypes.get(i);
					String percentageValue = ctx2.get("AOP_Percentage_" + map.get("AOPKey")) == null ? null : ctx2.get("AOP_Percentage_" + map.get("AOPKey")).toString().trim();
					if (percentageValue == null || "".equals(percentageValue) || "0".equals(percentageValue))
						percentageValue = "0";
					ctx1 = new Context();
					ctx1.put("VersionSequence", ctx.get("VersionSequence"));
					ctx1.put("PolicyKey", ctx.get("PolicyKey"));
					ctx1.put("PercentageValue", percentageValue);
					if (map.get("AOPKey").toString().equals("20"))
						ctx1.put("AOPCommentDesc", ctx.get("AOPCommentDesc_" + map.get("AOPKey").toString()));
					ctx1.put("AOPKey", map.get("AOPKey"));
					ctx1.setProject(ctx.getProject());
					
					Object obj2 = DBUtils.executeDBOperation(ctx1, "AreaOfPractice", "3");
					if (obj2 == null) {
						DBUtils.executeDBOperation(ctx1, "AreaOfPractice", "1");
					} else {
						DBUtils.executeDBOperation(ctx1, "AreaOfPractice", "2");
						DBUtils.executeDBOperation(ctx1, "AreaOfPractice", "1");
					}
				}
			} catch (Exception e) {
				logger.error("Error in AOP.savePopulateFieldsNewApp", e);
			}
		}
	}

	public void populateAOPFieldsNewApp(IContext ctx) throws Exception {
		List aoplist = SqlResources.getSqlMapProcessor(ctx).select(
				"SqlStmts.UserStatementsaveAccountstmtspopulateAOPFields", ctx);

		Map covMap = new HashMap();
		if (aoplist != null) {
			Map map = new HashMap();
			for (int i = 0; i < aoplist.size(); i++) {
				map = (HashMap) aoplist.get(i);
//				ctx.put("AOP_Percentage_" + i, map.get("PercentageValue"));
				// ctx.put("AOP_Percentage_"+map.get("AOPKey"),
				// map.get("PercentageValue"));
				ctx.put("AOPCommentDesc_" + map.get("AOPKey"), map.get("AOPCommentDesc"));
			}
		}

	}
	
	public static void updateAOPList(Context ctx) {
		HttpServletRequest req = (HttpServletRequest)ctx.get("DocumentRequest");
		HttpSession sess = req.getSession();
		List < Map < String,
		Object >> newList = new ArrayList < Map < String,
		Object >> ();
		List finalList = null;
		Map map1 = null,
		map2 = null;
		try {
			int rownum = 0;
			int index = ctx.get("rownum") != null && !ctx.get("rownum").equals(HtmlConstants.EMPTY) ? Integer.parseInt(ctx.get("rownum").toString()) : -2;
			if (ctx.get("aoplist_list_2") != null && ctx.get("aoplist_list_2")instanceof List) {
				// finalList = (List)sess.getAttribute("AttorneysDetailsList_list_01");
				finalList = (List)ctx.get("aoplist_list_2");

				for (int i = 0; i < finalList.size(); i++) {
					//,,
					map1 = new HashMap();
					map2 = (HashMap)finalList.get(i);
					map1.put("SmartSearchID", map2.get("SmartSearchID"));
					map1.put("DisplaySequence", map2.get("DisplaySequence"));
					map1.put("Aopkey", map2.get("Aopkey"));
					map1.put("AOPDesc", ctx.get("AOPDesc_" + i));
					map1.put("PercentageOld", ("".equals(ctx.get("AOP_PercentageOld_"+i)) || "0".equals(ctx.get("AOP_PercentageOld_"+i))) ? null : ctx.get("AOP_PercentageOld_"+i));
					map1.put("percentage", ("".equals(ctx.get("AOP_Percentage_"+i)) || "0".equals(ctx.get("AOP_Percentage_"+i))) ? null : ctx.get("AOP_Percentage_"+i));
					map1.put("rownum", map2.get("rownum"));
					map1.put("IsEdit", map2.get("IsEdit"));
					newList.add(map1);
				}

			}
			if (index != -2)
				newList.remove(index - 1);
			for (int i = 0; i < newList.size(); i++) {
				rownum = rownum + 1;
				map2 = (HashMap)newList.get(i);
				map2.put("rownum", rownum);

			}

			ctx.put("aoplist_list_2", newList);
			sess.setAttribute("aoplist_list_2", newList);

		} catch (Exception e) {
			logger.error("Error in AOP.updateAOPList", e);
		}
	}
	public void populateITSuppValues(Context ctx) throws Exception {
		List aoplist = SqlResources.getSqlMapProcessor(ctx).select("SqlStmts.UserStatementManualBoQueriesgetITServicesAopData", ctx);

		Map covMap = new HashMap();
		if (aoplist != null) 
		{
			Map map = new HashMap();
			for (int i = 0; i < aoplist.size(); i++) 
			{
				map = (HashMap) aoplist.get(i);
				int revenuesFromLastYear=map.get("RevenuesFromLastYear")!=null && !map.get("RevenuesFromLastYear").equals(HtmlConstants.EMPTY)?Integer.parseInt(map.get("RevenuesFromLastYear").toString()):0;
				ctx.put("RevenuesFromLastYear_" + map.get("ITServiceCategoryKey"),revenuesFromLastYear );
				int estimatedRevenue=map.get("EstimatedRevenue")!=null && !map.get("EstimatedRevenue").equals(HtmlConstants.EMPTY)?Integer.parseInt(map.get("EstimatedRevenue").toString()):0;
				ctx.put("EstimatedRevenue_" + map.get("ITServiceCategoryKey"),revenuesFromLastYear );
			}
		}

	}
	
	public static void addNewAopData(Context ctx)
	{
		
	        int total=0,percentage=0;
	        try
	        {
	        	 List limtTypes = SqlResources.getSqlMapProcessor(ctx).select("AOP.findAll", ctx);
	             List finalList = new ArrayList();
	             if(ctx.get("aoplist_list_2") != null &&  ctx.get("aoplist_list_2") instanceof List)
	             {
	                 finalList = (List)ctx.get("aoplist_list_2");
	             }
	             
	            
	             Map map = new HashMap();
	             for (int i = 0; i < finalList.size(); i++) 
	     		{	
	            	Context ctx1=new Context();
     				ctx1.setProject(ctx.getProject());
     				map = (HashMap) finalList.get(i);
     				ctx1.put("PolicyKey",ctx.get("PolicyKey"));
     				ctx1.put("AOPkey",map.get("Aopkey"));
     				if(ctx.get("AOP_Percentage_"+i)!=null && "0".equals(ctx.get("AOP_Percentage_"+i).toString()))
     					ctx1.put("percentage",null);
     				else
     				ctx1.put("percentage",ctx.get("AOP_Percentage_"+i));
     				SqlResources.getSqlMapProcessor(ctx1).update("SqlStmts.UserStatementManualBoQueriesupdateSmartSearchValue", ctx1);
     				percentage=ctx.get("AOP_Percentage_"+i)!=null && !ctx.get("AOP_Percentage_"+i).equals(HtmlConstants.EMPTY)?Integer.parseInt(ctx.get("AOP_Percentage_"+i).toString()):0;
     				total=total+percentage;
	     		}
	             ctx.put("totalPercentage", total);
	 			 Context ctx2=new Context();
  				 ctx2.setProject(ctx.getProject());
  				 ctx2.putAll(ctx);
  				 for (int i = 0; i < finalList.size(); i++) 
   	     		{
  					ctx2.remove("AOP_Percentage_"+i);
   					
   	     		}
  				Map map1 = new HashMap();
  				 for (int i = 0; i < finalList.size(); i++) 
  	     		{
  					map1 = (HashMap) finalList.get(i);
  					ctx2.put("AOP_Percentage_"+map1.get("Aopkey"),ctx.get("AOP_Percentage_"+i));
  	     		}
  				 
  				List inputList=new ArrayList();
  	    		
  				if(limtTypes!=null)
  	    		{	
  	    			Map map2=null;
  	    			for(int i=0;i<limtTypes.size();i++)
  	    			{
  	    				map2=new HashMap();
  	    				map1=(HashMap) limtTypes.get(i);
  	    				map2.put("AOPKey", map1.get("AOPKey"));
  	    				map2.put("PercentageValue",ctx2.get("AOP_Percentage_"+map1.get("AOPKey")));
  	    				
  	    				int key=map1.get("AOPKey")!=null && !map1.get("AOPKey").equals(HtmlConstants.EMPTY)?Integer.parseInt(map1.get("AOPKey").toString()):0;
  	    				int aopPercentage=ctx2.get("AOP_Percentage_"+map1.get("AOPKey"))!=null && !ctx2.get("AOP_Percentage_"+map1.get("AOPKey")).equals(HtmlConstants.EMPTY)?Integer.parseInt(ctx2.get("AOP_Percentage_"+map1.get("AOPKey")).toString()):0;;
  	    				ctx2.put("Aopkey", key);
  	    				if(key==6)
  	    				{
  	    					if(aopPercentage==0)
  	    					{
  	    						new SetParametersForStoredProcedures().setParametersInContext(ctx2, "PolicyKey,key,LastUpdateUserID,LastUpdateTimeStamp");
  	    						SqlResources.getSqlMapProcessor(ctx2).update("AreaOfPractice.UpdateAOPDetailsAfterDeleteACC_p",ctx2);
  	    					}
  	    					}
  	    				
  	    				if(key==20)
  	    				{
  	    					if(aopPercentage>0)
  	    					{
  	    						if(ctx2.get("AOPCommentDesc_20")!=null && !ctx2.get("AOPCommentDesc_20").equals(HtmlConstants.EMPTY))
  	    						map2.put("AOPCommentDesc", ctx2.get("AOPCommentDesc_20"));
  	    						else
  	    						{
  	    							AccountUtils.populateError(ctx, "otherAopDescError","Please provide description.");
  	    							logger.debug("unable to find other Aop description in request");
  	    						}
  	    					}
  	    					
  	    				}
  	    				
  	    				else
  	    				map2.put("AOPCommentDesc", ctx2.get("AOPCommentDesc_"+map1.get("AOPKey")));
  	    				
  	    				map2.put("PolicyKey", ctx2.get("PolicyKey"));
  	    				map2.put("VersionSequence", ctx2.get("VersionSequence"));
  	    				inputList.add(map2);
  	    			}
  	    			ctx2.put("inputList", inputList);
  	    			AccountUtils.convertListDataToXML(ctx2,"inputList","outputList");
  	    			ctx.put("outputList", ctx2.get("outputList"));
  	    		}
	        }
	        catch(Exception e)
	        {
                logger.error("Error in AOP.addNewAopData", e);
	        }
		
	}
	
	
	
}
