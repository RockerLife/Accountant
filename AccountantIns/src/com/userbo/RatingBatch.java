package com.userbo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jdom.Element;

import com.manage.managecomponent.components.WebservicecallImpl;
import com.manage.managemetadata.functions.commonfunctions.Math;
import com.manage.managemetadata.functions.commonfunctions.RuleUtils;
import com.ormapping.ibatis.SqlResources;
import com.processor.RequestProcessor;
import com.util.Context;
import com.util.IContext;
import com.util.InetLogger;
import com.util.SystemProperties;

public class RatingBatch {
	private static InetLogger logger = InetLogger
			.getInetLogger(RatingBatch.class);
	public static void callingRatingEngine(Map quoteMap) throws Exception {
		WebservicecallImpl webSer = null;
		IContext newCtx = new Context();

		newCtx.setProject("AccountantIns");
		newCtx.put("PolicyKey", quoteMap.get("PolicyKey"));
		newCtx.put("VersionSequence", quoteMap.get("VersionSequence"));
		newCtx.put("TransactionSequence", quoteMap.get("TransactionSequence"));
		newCtx.put("PolicyEffectiveDate", quoteMap.get("PolicyEffectiveDate"));
		newCtx.put("CompanyKey", 1);
		newCtx.put("LOBKey", 1);
		newCtx.put("LookupStateCode", "ALL");
		newCtx.put("ERPYear", 1);	
		Context newCtx1 = new Context();
		newCtx1.setProject("AccountantIns");
		newCtx1.putAll(quoteMap);
	//	new AccountUtils().calculateClaimAgeRequote(newCtx1);
		populateDataInRequest(newCtx, quoteMap);
		webSer = new WebservicecallImpl();
		webSer.setInput("AccountantsInfo");
		webSer.setOutput("PremiumInfo");
		webSer.setName("calculatePremiumForAccountants");

		String url = null;
		try {
		
			url = SystemProperties.getInstance().getString("appl.AccountantIns.webserviceurl");
		} 
		catch (Exception e) {
			logger.error("Error in RatingBatch.callingRatingEngine", e);

		}

		if (url == null)
			return;

		webSer.setServiceurl(url);
		newCtx.put("responseMap", webSer.execute(newCtx));

		Element outElementError = new AccountantValidationUtils().checkIfErrorOutput(newCtx.get("responseMap"));

		if (outElementError != null) {

			List responseErrorList = new AccountantValidationUtils().getErrorListFromResponse(outElementError);
			StringBuffer buffer = new AccountantValidationUtils().addListToInetErrorList(responseErrorList);
			
			quoteMap.put("Validation", buffer.toString());
		} else {
			
			populateOuputResponse(newCtx);	
			
			{ if (logger.isDebugEnabled()) logger.debug("Outputresponse:::::::::>>>>>>>>>?????????"+newCtx.get("XmlOutputDatafromRating")); }
			{ if (logger.isDebugEnabled()) logger.debug("Inputresponse:::::::::>>>>>>>>>?????????"+newCtx.get("XmlInputDatatoRating")); }
			quoteMap.put("Premium", newCtx.get("Premium"));
			quoteMap.put("RatingInput", newCtx.get("XmlInputDatatoRating"));
			quoteMap.put("RatingOutput", newCtx.get("XmlOutputDatafromRating"));
			
		}
	}
	
	protected static void populateDataInRequest(IContext ctx, Map inputMap) throws Exception {	
		
		//Common Data
		ctx.put("PolicyEffectiveDate", inputMap.get("PolicyEffectiveDate"));
		ctx.put("LimitAmount", inputMap.get("LimitAmount"));
		ctx.put("DeductibleAmount", inputMap.get("DeductibleAmount"));
		ctx.put("IsClaimOptionType", inputMap.get("IsClaimOptionType"));
		ctx.put("IsClaimExpensesType", inputMap.get("IsClaimExpensesType"));
		ctx.put("StateCode", inputMap.get("StateCode"));
		ctx.put("LimitSequence",inputMap.get("LimitSequence"));
		
	//	RuleUtils.executeRule(ctx,"AccountantRule.AssignClaimExpensesAndDollarDefense");
		
		// For County and City
		if (inputMap.get("City") != null) {
			String city = inputMap.get("City").toString();
			city = city.replace("&", "#AMP#");
			ctx.put("City", city);
		}
		if (inputMap.get("CountyDesc") != null) {
			String county = inputMap.get("CountyDesc").toString();
			county = county.replace("&", "#AMP#");
			ctx.put("CountyDesc", county);
		}
		ctx.put("ManualPremium",inputMap.get("ManualPremium") != null ? inputMap.get("ManualPremium") : null);
		
		ctx.put("SchduleRatingModifier1",inputMap.get("TotalCovModifierPercentage"));
		ctx.put("SchduleRatingModifier2",inputMap.get("SchduleRatingModifier2"));
		ctx.put("SchduleRatingModifier3",inputMap.get("SchduleRatingModifier3"));
		ctx.put("PolicyEffectiveDate", inputMap.get("PolicyEffectiveDate"));
		ctx.put("AverageRevenueAmount", inputMap.get("AverageRevenue"));		
		ctx.put("BusinessTaxServicesPercentage", inputMap.get("BusinessTaxServicesPercentage"));
		ctx.put("EstateTaxServicesPercentage", inputMap.get("EstateTaxServicesPercentage"));
		ctx.put("IndividualTaxServicesPercentage", inputMap.get("IndividualTaxServicesPercentage"));
		ctx.put("BookKeepPercentage", inputMap.get("BookKeepPercentage"));
		ctx.put("AuditNonpublicClientsPercentage", inputMap.get("AuditNonpublicClientsPercentage"));
		ctx.put("ReviewPercentage", inputMap.get("ReviewPercentage"));
		ctx.put("CompilationPercentage", inputMap.get("CompilationPercentage"));
		ctx.put("AgreedUponProceduresPercentage", inputMap.get("AgreedUponProceduresPercentage"));
		ctx.put("OtherAssurancePercentage", inputMap.get("OtherAssurancePercentage"));
		ctx.put("LitigationSupportPercentage", inputMap.get("LitigationSupportPercentage"));
		ctx.put("OtherConsultingPercentage", inputMap.get("OtherConsultingPercentage"));
		ctx.put("ManagementConsultingBusinessPlanningPercentage",inputMap.get("ManagementConsultingBusinessPlanningPercentage"));
		ctx.put("ComputerRelatedServicesPercentage", inputMap.get("ComputerRelatedServicesPercentage"));
		ctx.put("BusinessValuationPercentage", inputMap.get("BusinessValuationPercentage"));
		ctx.put("ForecastsProjectionsPercentage", inputMap.get("ForecastsProjectionsPercentage"));
		ctx.put("MergersAndAcquisitionsPercentage", inputMap.get("MergersAndAcquisitionsPercentage"));
		ctx.put("BusinessManagementPercentage", inputMap.get("BusinessManagementPercentage"));
		ctx.put("InvestmentFinancialPlanningPercentage", inputMap.get("InvestmentFinancialPlanningPercentage"));
		ctx.put("SECWorkOtherThanAuditPercentage", inputMap.get("SECWorkOtherThanAuditPercentage"));
		ctx.put("GeneralBusinessAdvicePercentage", inputMap.get("GeneralBusinessAdvicePercentage"));
		ctx.put("ErisaPensionPlansPercentage", inputMap.get("ErisaPensionPlansPercentage"));
		ctx.put("TrusteeServicesPercentage", inputMap.get("TrusteeServicesPercentage"));
		ctx.put("OtherPercentage", inputMap.get("OtherPercentage"));
		ctx.put("IsFirmCarryingProfLiabilityIns", inputMap.get("IsFirmCarryingProfLiabilityIns"));
		ctx.put("IsPriorActDateFull", inputMap.get("IsPriorActDateFull"));
		ctx.put("PriorActDatePross", inputMap.get("PriorActDatePross"));
		ctx.put("IsDollarDefense", inputMap.get("IsDollarDefense"));	
		List itSupplist = SqlResources.getSqlMapProcessor(ctx).select("SqlStmts.UserStatementsaveAccountstmtsgetAllITServicesSupp", ctx);
		Map map= null;
		boolean flag = false;
		if(itSupplist!=null){				
			for(int i=0;i<itSupplist.size();i++){	
				map = (HashMap)itSupplist.get(i);				
				String ITServiceCategoryKey = map.get("ITServiceCategoryKey").toString();	
				
				String RevenuesFromLastYear = null;
				if(map.get("RevenuesFromLastYear") != null)
					RevenuesFromLastYear = map.get("RevenuesFromLastYear").toString();
				
				String EstimatedRevenue = null;
				if(map.get("EstimatedRevenue") != null)
					EstimatedRevenue = map.get("EstimatedRevenue").toString();
				
				if("1".equals(ITServiceCategoryKey) || "2".equals(ITServiceCategoryKey))
				{
					
					if(RevenuesFromLastYear != null && (!"".equals(RevenuesFromLastYear) && !"0".equals(RevenuesFromLastYear)))
						flag = true;					
					
					if(EstimatedRevenue != null && (!"".equals(EstimatedRevenue) && !"0".equals(EstimatedRevenue)))
						flag = true;					
				}
				else
				{
					if(RevenuesFromLastYear != null && (!"".equals(RevenuesFromLastYear) && !"0".equals(RevenuesFromLastYear)))
						flag = false;					
					
					if(EstimatedRevenue != null && (!"".equals(EstimatedRevenue) && !"0".equals(EstimatedRevenue)))
						flag = false;
				}				
			}					
		}
		
		if(flag)
			ctx.put("DataProcessingInstalledSoftware", "Y");
		else
			ctx.put("DataProcessingInstalledSoftware", "N");
		
		
		ctx.put("requestxml", new RequestProcessor().generateRequestXml((Context) ctx));
		
	}

	public static void populateOuputResponse(IContext ctx) {

		try{
		Map responseMap = null;
		if(ctx.get("responseMap") != null)
			responseMap = (Map)ctx.get("responseMap");
		
		AccountUtils.populateLastUpdateTimeStamp(ctx);	
		
		if(responseMap != null)
		{
			logger.debug(ctx.get("IsTaxCalculation"));

			Object obj = RuleUtils.executeRule(ctx, "AccountantRule.IsSkipTaxCalculation");			
			boolean flag = true;
			if(obj != null && obj instanceof Boolean )
				flag =(Boolean) obj;
			
			double mTCountyTax = 0.0;		
			double finalBaseLimitPrem = 0.0;
			
			//if skip tax is not selected then this will not be used;
			/*if(flag){
					if(responseMap.get("MTTaxAmmount") !=null && !"".equals(responseMap.get("MTTaxAmmount").toString()) && new Double(responseMap.get("MTTaxAmmount").toString()).doubleValue() != 0)
					{				
						mTCountyTax = Double.parseDouble(responseMap.get("MTTaxAmmount").toString());
						ctx.put("MTTaxAmmount", Math.round(responseMap.get("MTTaxAmmount")));
					}
			}
				if(responseMap.get("CountyTaxAmmount") !=null && !"".equals(responseMap.get("CountyTaxAmmount").toString()) && new Double(responseMap.get("CountyTaxAmmount").toString()).doubleValue() != 0)
				{
					mTCountyTax = Double.parseDouble(responseMap.get("CountyTaxAmmount").toString());
					ctx.put("CountyTaxAmmount", Math.round(responseMap.get("CountyTaxAmmount")));
				}
			*/
			
			// Skip No
			if (!flag) {
                if (responseMap.get("MTTaxAmmount") != null
                          && !"".equals(responseMap.get("MTTaxAmmount")
                                   .toString())) {
                     mTCountyTax = Double.parseDouble(responseMap.get(
                               "MTTaxAmmount").toString());
                     ctx.put("MTTaxAmmount", Math.round(responseMap
                               .get("MTTaxAmmount")));
                }
           }
			
           if (responseMap.get("CountyTaxAmmount") != null
                     && !"".equals(responseMap.get("CountyTaxAmmount")
                               .toString())) {
        	   
                if (mTCountyTax <= 0 ) {
                     mTCountyTax = mTCountyTax
                               + Double.parseDouble(responseMap.get(
                                        "CountyTaxAmmount").toString());
                     ctx.put("CountyTaxAmmount", Math.round(responseMap
                               .get("CountyTaxAmmount")));
                }
           }

			
			if(responseMap.get("State1TaxAmmount") !=null && !"".equals(responseMap.get("State1TaxAmmount").toString()))
			{
				mTCountyTax = mTCountyTax + Double.parseDouble(responseMap.get("State1TaxAmmount").toString());
				ctx.put("State1TaxAmmount", Math.round(responseMap.get("State1TaxAmmount")));
			}
			if(responseMap.get("State2TaxAmmount") !=null && !"".equals(responseMap.get("State2TaxAmmount").toString()))
			{
				mTCountyTax = mTCountyTax + Double.parseDouble(responseMap.get("State2TaxAmmount").toString());
				ctx.put("State2TaxAmmount", Math.round(responseMap.get("State2TaxAmmount")));
			}
		
			
			ctx.put("TotalPolicyTaxAmount", Math.round(mTCountyTax));
			ctx.put("TotalCoverageTaxAmount", Math.round(mTCountyTax));
			
			
			if(responseMap.get("FinalBaseLimitPremium") !=null && !"".equals(responseMap.get("FinalBaseLimitPremium").toString()))
			{
				finalBaseLimitPrem = mTCountyTax +  Double.parseDouble(responseMap.get("FinalBaseLimitPremium").toString());	
			}
			
					
			// Policy Transaction
			ctx.put("InvoicedPremium", Math.round(finalBaseLimitPrem));	
			// Policy Coverage
			ctx.put("Premium", Math.round(finalBaseLimitPrem)); 
			
			if(responseMap.get("FinalBaseLimitPremium") !=null && !"".equals(responseMap.get("FinalBaseLimitPremium").toString()))
			{
				ctx.put("TotalPremium", Math.round(responseMap.get("FinalBaseLimitPremium")));	
				ctx.put("InitialCovPremium", Math.round(responseMap.get("FinalBaseLimitPremium")));
			}	
			
			
			ctx.put("ClaimsMadeYearDesc", responseMap.get("ClaimsMadeYearDesc"));	
			ctx.put("IsMinimumPremium", responseMap.get("IsMinimumPremium"));
			ctx.put("MinimumPremiumValue", responseMap.get("MinimumPremiumValue"));
			
			
			if(ctx.get("TotalPremium") != null && !"".equals(ctx.get("TotalPremium").toString()))
			{
				int divider = 10;  	//Default
				
				if(SystemProperties.getInstance().getString("appl."+ctx.getProject()+".firstrangedivider") != null)
					divider = new Integer(SystemProperties.getInstance().getString("appl."+ctx.getProject()+".firstrangedivider"));
				
				double FirstRange = Double.parseDouble(ctx.get("TotalPremium").toString())/divider;  
				
				if(ctx.get("IsMinimumPremium") != null && "Y".equals(ctx.get("IsMinimumPremium").toString()))
				{
					ctx.put("TotalPremiumFirstRange",ctx.get("TotalPremium"));
				}
				else
				{	
					FirstRange = Double.parseDouble(ctx.get("TotalPremium").toString()) - FirstRange;
					if(ctx.get("MinimumPremiumValue") != null && !"".equals(ctx.get("MinimumPremiumValue").toString()))
					{
						double minPreVal = new Double(ctx.get("MinimumPremiumValue").toString());
						if(FirstRange < minPreVal)
							FirstRange = minPreVal;
					}
					
					Object TotalPremiumFirstRange = Math.decimalFormat(FirstRange, 0);
					ctx.put("TotalPremiumFirstRange", TotalPremiumFirstRange);
				}
			}
			else
				ctx.put("TotalPremiumFirstRange", 0);
			
			
//			if(responseMap.get("InputXML") !=null && !"".equals(responseMap.get("InputXML").toString()))
//				ctx.put("XmlInputDatatoRating", responseMap.get("InputXML").toString());
//			if(responseMap.get("OutputXML") !=null && !"".equals(responseMap.get("OutputXML").toString()))
//				ctx.put("XmlOutputDatafromRating", responseMap.get("OutputXML").toString());	
		
			
			if(responseMap.get("InputXML") != null)
				ctx.put("XmlInputDatatoRating", responseMap.get("InputXML").toString());
			if(responseMap.get("OutputXML") != null)
				ctx.put("XmlOutputDatafromRating", responseMap.get("OutputXML").toString());			
		}
		
		
		int totalPMP = 0;
		if(ctx.get("SchduleRatingModifier1") !=null && !"".equals(ctx.get("SchduleRatingModifier1").toString()))
			totalPMP = Integer.parseInt((ctx.get("SchduleRatingModifier1").toString()));
		if(ctx.get("SchduleRatingModifier2") !=null && !"".equals(ctx.get("SchduleRatingModifier2").toString()))
			totalPMP = totalPMP + Integer.parseInt((ctx.get("SchduleRatingModifier2").toString()));
		if(ctx.get("SchduleRatingModifier3") !=null && !"".equals(ctx.get("SchduleRatingModifier3").toString()))
			totalPMP = totalPMP + Integer.parseInt((ctx.get("SchduleRatingModifier3").toString()));
		if(ctx.get("SchduleRatingModifier4") !=null && !"".equals(ctx.get("SchduleRatingModifier4").toString()))
			totalPMP = totalPMP + Integer.parseInt((ctx.get("SchduleRatingModifier4").toString()));
		
		ctx.put("TotalPolicyModifierPercentage", totalPMP);
		ctx.put("TotalCovModifierPercentage", totalPMP);
		//ctx.put("LastUpdateUserID", ctx.get("FirstReviewUserID"));
		
		}
		catch (Exception e) {
			logger.error("populate output reponse-"+e, e);
//			System.out.println("populate output reponse---"+e);
			
		}
	
		
	}
}
