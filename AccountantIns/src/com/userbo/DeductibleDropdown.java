package com.userbo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.manage.managemetadata.metadata.IDropDown;
import com.ormapping.ibatis.SqlResources;
import com.util.Constants;
import com.util.Context;
import com.util.IContext;

/**
 * @author Amit Jain
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DeductibleDropdown implements IDropDown {

	public List getDropDownData(IContext ctx, String field) throws Exception {

		List newDeductible = new ArrayList();
		
		AccountUtils.setCompanyForPolicy((Context)ctx);
		if(ctx.get("CompanyKey") != null && "3".equals(ctx.get("CompanyKey").toString())) {
			
			newDeductible = SqlResources.getSqlMapProcessor(ctx).select("SqlStmts.UserStatementdropdowndatadeductible", ctx);

			if (newDeductible == null || newDeductible.size() == 0)
				return new ArrayList();
		} else {
		
		if (ctx.get("inet_method") != null && "getProducerName".equals(ctx.get("inet_method").toString()))
		{
			return newDeductible;
		}
		List revenueList = SqlResources.getSqlMapProcessor(ctx).select("SqlStmts.BasicInfoviewmomgrossrevenue", ctx);

		if(ctx.get("PolicyStatusKey") != null && "1".equals(ctx.get("PolicyStatusKey").toString()))
		{
			if (((ctx.get("quoteTabPressed") != null && "Y".equals(ctx.get("quoteTabPressed").toString())) || (ctx.get("page") != null && "quoteOption".equals(ctx.get("page").toString()))) && ctx.get("User") != null&& "insured".equals(ctx.get("User").toString())) 
			{
				ctx.put("AggregateDeductible", "10000");
				newDeductible = SqlResources.getSqlMapProcessor(ctx).select("SqlStmts.UserStatementdropdowndatagetInsuredDeductible",ctx);
	
				if (newDeductible == null || newDeductible.size() == 0)
					return new ArrayList();
	
				return newDeductible;
			}
		}
		else if(ctx.get("PolicyStatusKey") != null && "2".equals(ctx.get("PolicyStatusKey").toString()) && "insured".equals(ctx.get("User").toString()))
		{
			Context newCtx = new Context();
			newCtx.putAll(ctx);
			Object obj = SqlResources.getSqlMapProcessor(newCtx).findByKey("SqlStmts.UserStatementdropdowndatagetCorrectTransactionSequence", newCtx);
			if (obj != null && obj instanceof Map)
			{
				newCtx.putAll((Map)obj);
			}
			Object object = SqlResources.getSqlMapProcessor(newCtx).findByKey("PolicyCoverage.findByKey", newCtx);
			if(object != null && object instanceof Map )
			{
				Map map = (Map)object;
				newCtx.put("AggregateDeductible", map.get("AggregateDeductible"));
				newCtx.put("OccuranceDeductible", map.get("OccuranceDeductible"));
				
				Object objLimitSeq = SqlResources.getSqlMapProcessor(newCtx).findByKey("Deductibles.findByKey", newCtx);
				if(objLimitSeq != null && objLimitSeq instanceof Map){
					Map limitMap = (Map)objLimitSeq;
					int limitSequence = limitMap.get("DeductibleSequence") != null ? AccountantValidationUtils.convertStringToInteger(limitMap.get("DeductibleSequence").toString()) : 0; 
					
					newCtx.put("upperdlimit", limitSequence + 1);
					newCtx.put("lowerdlimit", limitSequence - 1 );
					
					
					newDeductible = SqlResources.getSqlMapProcessor(newCtx).select("SqlStmts.UserStatementdropdowndatagetInsuredRenewDeductible", newCtx);
					if (newDeductible == null || newDeductible.size() == 0)
						return new ArrayList();
					
					return newDeductible ;
					
				}
			}
		}

		long average = 0;
		if (ctx.get(Constants.INET_FORM_DIRTY) != null && "Y".equals(ctx.get(Constants.INET_FORM_DIRTY))) 
		{
			getAverageRevenue(ctx);
			average = Long.parseLong(ctx.get("AverageRevFromAjax").toString());
		}
		else if (ctx.get("FromAjax") != null && "Y".equals(ctx.get("FromAjax")))
				average = Long.parseLong(ctx.get("AverageRevFromAjax").toString());
		else 
		{
			Map map = new HashMap();
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
			if (count > 0) {
				average = total / count;

			} else
				average = 0;

		}

		if (average == 0 || average <= 499999) {
			if (ctx.get("IsQuickQuote") != null && "Y".equals(ctx.get("IsQuickQuote").toString()) && ctx.get("IsFullQuote") != null && "N".equals(ctx.get("IsFullQuote").toString()) &&  !"insured".equals(ctx.get("User")))
				newDeductible = SqlResources.getSqlMapProcessor(ctx).select("SqlStmts.UserStatementdropdowndatadeductibleQQ", ctx);
			else if (ctx.get("IsQuickQuote") != null && "Y".equals(ctx.get("IsQuickQuote").toString()) && ctx.get("IsFullQuote") != null && "N".equals(ctx.get("IsFullQuote").toString()) && "insured".equals(ctx.get("User")))
				newDeductible = SqlResources.getSqlMapProcessor(ctx).select("SqlStmts.UserStatementdropdowndatadeductibleQQInsured", ctx);
			else if("insured".equals(ctx.get("User")) && ctx.get("IsFullQuote") != null && "Y".equals(ctx.get("IsFullQuote").toString()))
			{
				newDeductible = SqlResources.getSqlMapProcessor(ctx).select("SqlStmts.UserStatementdropdowndatadeductibleQQ", ctx);
				
			}
			else
				newDeductible = SqlResources.getSqlMapProcessor(ctx).select("SqlStmts.UserStatementdropdowndatadeductible", ctx);

			if (newDeductible == null || newDeductible.size() == 0)
				return new ArrayList();

		} else if (average >= 500000 && average <= 999999) 
		{

			ctx.put("AggregateDeductible", "2500");
			if (ctx.get("IsQuickQuote") != null && "Y".equals(ctx.get("IsQuickQuote").toString()) && ctx.get("IsFullQuote") != null && "N".equals(ctx.get("IsFullQuote").toString()) && !"insured".equals(ctx.get("User")))
				newDeductible = SqlResources.getSqlMapProcessor(ctx).select( "SqlStmts.UserStatementdropdowndatadeductible1QQ", ctx);
			else if (ctx.get("IsQuickQuote") != null && "Y".equals(ctx.get("IsQuickQuote").toString()) && ctx.get("IsFullQuote") != null && "N".equals(ctx.get("IsFullQuote").toString()) && "insured".equals(ctx.get("User")))
				newDeductible = SqlResources.getSqlMapProcessor(ctx).select("SqlStmts.UserStatementdropdowndatadeductibleQQInsured", ctx);
			else if("insured".equals(ctx.get("User")) && ctx.get("IsFullQuote") != null && "Y".equals(ctx.get("IsFullQuote").toString()))
			{
				newDeductible = SqlResources.getSqlMapProcessor(ctx).select("SqlStmts.UserStatementdropdowndatadeductibleQQ", ctx);
			}
			else
				newDeductible = SqlResources.getSqlMapProcessor(ctx).select("SqlStmts.UserStatementdropdowndatadeductible1", ctx);

			if (newDeductible == null || newDeductible.size() == 0)
				return new ArrayList();

		} 
		else if (average >= 1000000 && average <= 2000000)
		{
			ctx.put("AggregateDeductible", "5000");
			if (ctx.get("IsQuickQuote") != null && "Y".equals(ctx.get("IsQuickQuote").toString()) && ctx.get("IsFullQuote") != null && "N".equals(ctx.get("IsFullQuote").toString()) && !"insured".equals(ctx.get("User")))
				newDeductible = SqlResources.getSqlMapProcessor(ctx).select("SqlStmts.UserStatementdropdowndatadeductible1QQ", ctx);
			else if (ctx.get("IsQuickQuote") != null && "Y".equals(ctx.get("IsQuickQuote").toString()) && ctx.get("IsFullQuote") != null && "N".equals(ctx.get("IsFullQuote").toString()) && "insured".equals(ctx.get("User")))
				newDeductible = SqlResources.getSqlMapProcessor(ctx).select("SqlStmts.UserStatementdropdowndatadeductibleQQInsured", ctx);
			else if("insured".equals(ctx.get("User")) && ctx.get("IsFullQuote") != null && "Y".equals(ctx.get("IsFullQuote").toString()))
			{
				newDeductible = SqlResources.getSqlMapProcessor(ctx).select("SqlStmts.UserStatementdropdowndatadeductibleQQ", ctx);
			}
			else
				newDeductible = SqlResources.getSqlMapProcessor(ctx).select("SqlStmts.UserStatementdropdowndatadeductible1", ctx);

			if (newDeductible == null || newDeductible.size() == 0)
				return new ArrayList();
		}
		else
		{
			if (ctx.get("IsQuickQuote") != null && "Y".equals(ctx.get("IsQuickQuote").toString()) && ctx.get("IsFullQuote") != null && "N".equals(ctx.get("IsFullQuote").toString()) && !"insured".equals(ctx.get("User")))
				newDeductible = SqlResources.getSqlMapProcessor(ctx).select("SqlStmts.UserStatementdropdowndatadeductibleQQ", ctx);
			else if (ctx.get("IsQuickQuote") != null && "Y".equals(ctx.get("IsQuickQuote").toString()) && ctx.get("IsFullQuote") != null && "N".equals(ctx.get("IsFullQuote").toString()) && "insured".equals(ctx.get("User")))
				newDeductible = SqlResources.getSqlMapProcessor(ctx).select("SqlStmts.UserStatementdropdowndatadeductibleQQInsured", ctx);
			else if("insured".equals(ctx.get("User")) && ctx.get("IsFullQuote") != null && "Y".equals(ctx.get("IsFullQuote").toString()))
			{
				newDeductible = SqlResources.getSqlMapProcessor(ctx).select("SqlStmts.UserStatementdropdowndatadeductibleQQ", ctx);
			}
			else
				newDeductible = SqlResources.getSqlMapProcessor(ctx).select("SqlStmts.UserStatementdropdowndatadeductible", ctx);

			if (newDeductible == null || newDeductible.size() == 0)
				return new ArrayList();
		}
		
		}

		return newDeductible;

	}

	private void getAverageRevenue(IContext ctx) throws Exception {

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

}
