package com.userbo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.manage.managecomponent.components.SetParametersForStoredProcedures;
import com.manage.managemetadata.functions.commonfunctions.RuleUtils;
import com.manage.managemetadata.metadata.IDropDown;
import com.ormapping.ibatis.SqlResources;
import com.util.Constants;
import com.util.Context;
import com.util.IContext;

/**
 * @author Amit Jain
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class LimitDropdown implements IDropDown {

	public List getDropDownData(IContext ctx, String field) throws Exception {
		
		List newLimits = new ArrayList();
		if(!"Estimate".equals(ctx.get("inet_page").toString()) && !"EstimateForm".equals(ctx.get("inet_page").toString()))
		{
			if(ctx.get("Policykey") != null)
				AccountUtils.getPolicyData((Context)ctx);
			if (ctx.get("inet_method") != null && "getProducerName".equals(ctx.get("inet_method").toString())) {
				return newLimits;
			}
			List revenueList = SqlResources.getSqlMapProcessor(ctx).select("SqlStmts.BasicInfoviewmomgrossrevenue", ctx);
	
			if (ctx.get("PolicyStatusKey") != null && "1".equals(ctx.get("PolicyStatusKey").toString())) 
			{
				if (((ctx.get("quoteTabPressed") != null && "Y".equals(ctx.get("quoteTabPressed").toString())) || (ctx.get("page") != null && "quoteOption".equals(ctx.get("page").toString())))&& "insured".equals(ctx.get("User").toString())) 
				{
					/*ctx.put("AggregateLimit", "1000000");
					ctx.put("OccuranceLimit", "1000000");
					newLimits = SqlResources.getSqlMapProcessor(ctx).select("SqlStmts.UserStatementdropdowndatagetInsuredLimits",ctx);
					if (newLimits == null || newLimits.size() == 0)
						return new ArrayList();
	*/
					long average = 0;
					average=getAverageRevenue(revenueList);
					if(ctx.get("inet_page")!=null || ctx.get("inet_page").toString().equals("qqinsured"))
						newLimits=getDropDownListDataOnRevenue(ctx,revenueList,average);
					else
						newLimits=getDropDownListDataOnRevenue(ctx,revenueList,average);	
					
					return newLimits;
	
				}
			}
			else if (ctx.get("PolicyStatusKey") != null && "2".equals(ctx.get("PolicyStatusKey").toString()) && "insured".equals(ctx.get("User").toString()))
			{
				Context newCtx = new Context();
				newCtx.putAll(ctx);
				if(newCtx.get("IsThisOptionFinalised")==null || !"Y".equals(newCtx.get("IsThisOptionFinalised").toString()))
				{
					try{
					new SetParametersForStoredProcedures().setParametersInContext(newCtx, "PolicyKey");
					Object obj = SqlResources.getSqlMapProcessor(newCtx).findByKey("SqlStmts.UserStatementdropdowndatagetCorrectTransactionSequenceWithDefaultQuote", newCtx);
					if (obj != null && obj instanceof Map)
					{
						newCtx.putAll((Map)obj);
					}
					}
							catch (Exception e) {
								e.printStackTrace();
							}
					
				}
				if(newCtx.get("IsThisOptionFinalised")!=null && "Y".equals(newCtx.get("IsThisOptionFinalised").toString()))
				{
					Object obj = SqlResources.getSqlMapProcessor(newCtx).findByKey("SqlStmts.UserStatementdropdowndatagetCorrectTransactionSequence", newCtx);
					if (obj != null && obj instanceof Map)
					{
						newCtx.putAll((Map)obj);
					}
				}
				// For One Limit up and One Limit down in Renew application
				Object object = SqlResources.getSqlMapProcessor(newCtx).findByKey("PolicyCoverage.findByKey", newCtx);
				if (object != null && object instanceof Map) {
					Map map = (Map) object;
					newCtx.put("AggregateLimit", map.get("AggregateLimit"));
					newCtx.put("OccuranceLimit", map.get("OccuranceLimit"));
	
					Object objLimitSeq = SqlResources.getSqlMapProcessor(newCtx).findByKey("Limits.findByKey", newCtx);
					if (objLimitSeq != null && objLimitSeq instanceof Map) {
						Map limitMap = (Map) objLimitSeq;
						int limitsSequence = limitMap.get("LimitSequence") != null ? AccountantValidationUtils.convertStringToInteger(limitMap.get("LimitSequence").toString()) : 0;
	
	
						newCtx.put("limitsSequence",limitsSequence);
						new SetParametersForStoredProcedures().setParametersInContext(newCtx, "PolicyKey,limitsSequence,CoverageKey");
						newLimits = SqlResources.getSqlMapProcessor(newCtx).select("Limits.getLimitSequence_p",newCtx);
					
						if (newLimits == null || newLimits.size() == 0)
							return new ArrayList();
	
						return newLimits;
	
					}
				}
			}
	
			long average = 0;
			if (ctx.get(Constants.INET_FORM_DIRTY) != null&& "Y".equals(ctx.get(Constants.INET_FORM_DIRTY)))
			{
				getAverageRevenue(ctx);
				average = Long.parseLong(ctx.get("AverageRevFromAjax").toString());
			}
			else if (ctx.get("FromAjax") != null && "Y".equals(ctx.get("FromAjax")))
				average = Long.parseLong(ctx.get("AverageRevFromAjax").toString());
			else 
			{
				
				average=getAverageRevenue(revenueList);
			}
	
			if( ctx.get("IsQuickQuote") != null && "Y".equals(ctx.get("IsQuickQuote").toString()) && "insured".equals(ctx.get("User")) && ctx.get("IsFullQuote") != null && "N".equals(ctx.get("IsFullQuote").toString()))
				newLimits=getLimitsequenceForQQInsured(ctx,revenueList,average);
			else
				newLimits=getDropDownListDataOnRevenue(ctx,revenueList,average);	
		}
		else
		{
			newLimits=SqlResources.getSqlMapProcessor(ctx).select("SqlStmts.UserStatementdropdowndatalimits", ctx);
		}
		return newLimits;
	}

	private void getAverageRevenue(IContext ctx) throws Exception 
	{

		int count = 0;
		long total = 0;
		long average = 0;
		Object obj0 = com.manage.managemetadata.functions.commonfunctions.Math
				.removeAmountFormat(ctx.get("Amount_0"));
		Object obj1 = com.manage.managemetadata.functions.commonfunctions.Math
				.removeAmountFormat(ctx.get("Amount_1"));
		Object obj2 = com.manage.managemetadata.functions.commonfunctions.Math
				.removeAmountFormat(ctx.get("Amount_2"));
		if (obj0 != null && !"".equals(obj0) && !"0".equals(obj0)) {
			count = count + 1;

			total = total + Long.parseLong(obj0.toString());
		}
		if (obj1 != null && !"".equals(obj1) && !"0".equals(obj1)) {
			count = count + 1;
			total = total + Long.parseLong(obj1.toString());
		}
		if (obj2 != null && !"".equals(obj2) && !"0".equals(obj2)) {
			count = count + 1;
			total = total + Long.parseLong(obj2.toString());
		}

		if (count != 0 && total != 0) {
			average = total / count;
		}

		ctx.put("AverageRevFromAjax", average);
	}

	public List getDropDownListDataOnRevenue(IContext ctx,List revenueList,long average)throws Exception
	{
		Context newCtx = new Context();
		newCtx.putAll(ctx);
		
		List newLimits = new ArrayList();		
		//Asked By Pat
		String inetPage=newCtx.get("inet_page")!=null?newCtx.get("inet_page").toString().trim():"N";
		Object objnew = RuleUtils.executeRule(newCtx, "AccountantRule.newAppFlow");
		boolean flagnew = false;			
		if(objnew instanceof Boolean)
			flagnew = (Boolean) objnew;
		if(flagnew || inetPage.equals("Endorsement") || inetPage.equals("limitsDeductibleOptions")){
			if("insured".equals(newCtx.get("User").toString())) {
				if(newCtx.get("CompanyKey") == null || "1".equals(newCtx.get("CompanyKey").toString())) {
					newCtx.put("AggregateLimit", "1000000");
					newCtx.put("OccuranceLimit", "3000000");
				} else {
					newCtx.put("AggregateLimit", "2000000");
					newCtx.put("OccuranceLimit", "4000000");
				}
			}
			else {
				if(newCtx.get("CompanyKey") == null || "1".equals(newCtx.get("CompanyKey").toString())) {
					newCtx.put("AggregateLimit", "1000000");
					newCtx.put("OccuranceLimit", "3000000");
				} else {
					newCtx.put("AggregateLimit", "2000000");
					newCtx.put("OccuranceLimit", "4000000");
				}
			}
			newLimits = SqlResources.getSqlMapProcessor(newCtx).select("SqlStmts.UserStatementdropdowndatalimits1", newCtx);
			if (newLimits == null || newLimits.size() == 0)
				return new ArrayList();
		} else {
			if (average == 0 || average <= 499999)
			{
				if(newCtx.get("CompanyKey") == null || "1".equals(newCtx.get("CompanyKey").toString())) {
					newCtx.put("AggregateLimit", "1000000");
					newCtx.put("OccuranceLimit", "3000000");
				} else {
					newCtx.put("AggregateLimit", "2000000");
					newCtx.put("OccuranceLimit", "4000000");
				}
				newLimits = SqlResources.getSqlMapProcessor(newCtx).select("SqlStmts.UserStatementdropdowndatalimits1", newCtx);
				if (newLimits == null || newLimits.size() == 0)
					return new ArrayList();

			} 
			else if (average >= 500000 && average <= 2000000) 
			{
				if(newCtx.get("CompanyKey") == null || "1".equals(newCtx.get("CompanyKey").toString())) {
					newCtx.put("AggregateLimit", "1000000");
					newCtx.put("OccuranceLimit", "3000000");
				} else {
					newCtx.put("AggregateLimit", "2000000");
					newCtx.put("OccuranceLimit", "4000000");
				}
				newLimits = SqlResources.getSqlMapProcessor(newCtx).select("SqlStmts.UserStatementdropdowndatalimits1", newCtx);
				if (newLimits == null || newLimits.size() == 0)
					return new ArrayList();

			} 
			else 
			{
				newLimits = SqlResources.getSqlMapProcessor(newCtx).select("SqlStmts.UserStatementdropdowndatalimits", newCtx);
				if (newLimits == null || newLimits.size() == 0)
					return new ArrayList();
			}
		}
		
		return newLimits;		
	}
	
	public long getAverageRevenue(List revenueList)
	{	Map map = new HashMap();
		long average=0;
		long total = 0;
		int count = 0;
		for (int i = 0; i < revenueList.size(); i++)
		{
			map = (HashMap) revenueList.get(i);
			Object objAmount = map.get("Amount");
			if (objAmount != null && !"".equals(objAmount.toString()) && !"0".equals(objAmount.toString()))
			{
				total = total + Long.parseLong(objAmount.toString());
				count = count + 1;
			}
		}
		if (count > 0)
		{
			average = total / count;

		} else
			average = 0;
		
		return average;
	}
	public List getLimitsequenceForQQInsured(IContext ctx,List revenueList,long average)throws Exception
	{
		Context newCtx = new Context();
		newCtx.putAll(ctx);
		List newLimits = new ArrayList();
		
		if(newCtx.get("CompanyKey") == null || "1".equals(newCtx.get("CompanyKey").toString())) {
			newCtx.put("AggregateLimit", "1000000");
			newCtx.put("OccuranceLimit", "3000000");
		} else {
			newCtx.put("AggregateLimit", "2000000");
			newCtx.put("OccuranceLimit", "4000000");
		}
			newLimits = SqlResources.getSqlMapProcessor(newCtx).select("SqlStmts.UserStatementdropdowndatalimits1", newCtx);
			if (newLimits == null || newLimits.size() == 0)
				return new ArrayList();
	
			
			
		return newLimits;
		
	}
	
}
