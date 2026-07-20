package com.userbo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.manage.managecomponent.components.WebservicecallImpl;
import com.manage.managemetadata.functions.commonfunctions.DBUtils;
import com.manage.managemetadata.functions.commonfunctions.Math;
import com.manage.managemetadata.functions.commonfunctions.RuleUtils;
import com.ormapping.ibatis.SqlResources;
import com.processor.RequestProcessor;
import com.util.Context;
import com.util.HtmlConstants;
import com.util.IContext;
import com.util.InetLogger;
import com.util.SystemProperties;

public class Rating {
	
	private static InetLogger logger = InetLogger.getInetLogger(Rating.class);
	public static void processQuotesList(IContext ctx) throws Exception
	{
		List quotesList = null;
		Object obj = SqlResources.getSqlMapProcessor(ctx).select("SqlStmts.UserStatementManualBoQueriesgetQuoteListAll", ctx);
	
		
		if(obj != null && obj instanceof List)
			quotesList = (List) obj;
		
		if(quotesList == null)
			return;
		
		for(int i=0;i<quotesList.size();i++)
		{	
			Map quoteMap = (HashMap)quotesList.get(i);
			callingRatingEngine(ctx, quoteMap);
			
//			if(ctx.get("saveModifier") != null && "Y".equals(ctx.get("saveModifier")))
//			{
//				callingRatingEngine(ctx, quoteMap);
//			}			
//			else if(quoteMap.get("IsDollarDefense") == null || (quoteMap.get("IsDollarDefense")!= null && "N".equals(quoteMap.get("IsDollarDefense"))))
//			{
//				callingRatingEngine(ctx, quoteMap);
//			}	
			
		}				  
	}
	
	public static void callingRatingEngine(IContext ctx, Map quoteMap) throws Exception
	{
		WebservicecallImpl webSer = null; 
		IContext newCtx = new Context();
		
		newCtx.setProject(ctx.getProject());
		newCtx.putAll(quoteMap);
		newCtx.put("PolicyKey",ctx.get("PolicyKey"));
		newCtx.put("VersionSequence",ctx.get("VersionSequence"));
		newCtx.put("AddressKey",ctx.get("AddressKey"));
		newCtx.put("StateCode",ctx.get("StateCode"));
		newCtx.put("AddressTypeKey",ctx.get("AddressTypeKey"));
		newCtx.put("CompanyKey","1");
		newCtx.put("LOBKey",ctx.get("LOBKey"));
		newCtx.put("LookupStateCode",ctx.get("LookupStateCode"));
		newCtx.put("ERPYear",ctx.get("ERPYear"));
		newCtx.put("LastUpdateTimeStamp", ctx.get("LastUpdateTimeStamp"));
		newCtx.put("LastUpdateUserID", ctx.get("LastUpdateUserID"));
		
		AccountUtils.populateLastUpdateTimeStamp(newCtx);
		populateInputRequest(newCtx);
		webSer = new WebservicecallImpl();
		if(ctx.get("inputMO") != null)
			webSer.setInput(ctx.get("inputMO").toString());
		if(ctx.get("outputMO") != null)
			webSer.setOutput(ctx.get("outputMO").toString());
		if(ctx.get("webservicename") != null)
			webSer.setName(ctx.get("webservicename").toString());
		
		String url = null;
		try{
			url = SystemProperties.getInstance().getString("appl."+ctx.getProject()+".webserviceurl");
		}
		catch (Exception e){
			
		}
		
		if(url == null)
			return;
		
		webSer.setServiceurl(url);			
		newCtx.put("responseMap", webSer.execute(newCtx));
		populateOuputResponse(newCtx);
		
		SqlResources.getSqlMapProcessor(newCtx).update("PolicyTransaction.update", newCtx);
		SqlResources.getSqlMapProcessor(newCtx).update("PolicyCoverage.update", newCtx);
		SqlResources.getSqlMapProcessor(newCtx).update("RatingTrace.update", newCtx);
		SqlResources.getSqlMapProcessor(newCtx).update("Quote.update", newCtx);
		
		
//		Object obj = RuleUtils.executeRule(newCtx, "AccountantRule.IsTaxCalculationQuote");			
//		boolean flag = true;
//		if(obj != null && obj instanceof Boolean )
//			flag =(Boolean) obj;
//		
//		if(flag){
			populateSurchargeTax(newCtx);		
//		}
		
			
	}
	
	@SuppressWarnings("unchecked")
	public static void populateInputRequest(IContext ctx) throws Exception
	{
		ctx.put("OLDCompanyKey",ctx.get("CompanyKey"));
		Map<String, Object> inputMap = new HashMap<String, Object>();
		fetchData(ctx, inputMap);
		populateDataInRequest(ctx, inputMap);		  
	}
	
	protected static void fetchData(IContext ctx, Map<String, Object> inputMap) throws Exception
	{
		new Modifiers().populateCoverageModifiers(ctx);
		
		inputMap.put("SchduleRatingModifier1",ctx.get("SchduleRatingModifier1"));
		inputMap.put("SchduleRatingModifier2",ctx.get("SchduleRatingModifier2"));
		inputMap.put("SchduleRatingModifier3",ctx.get("SchduleRatingModifier3"));
		
		Object objPolicy = SqlResources.getSqlMapProcessor(ctx).findByKey("Policy.findByKey", ctx);
		if(objPolicy != null)
		{
			Map mapPolicy = (Map) objPolicy;
			inputMap.put("PolicyEffectiveDate", mapPolicy.get("PolicyEffectiveDate"));
		}
		
		Object objFirm = SqlResources.getSqlMapProcessor(ctx).findByKey("Firm.findByKey", ctx);
		if(objFirm != null)
		{
			Map mapFirm = (Map) objFirm;
			if(mapFirm.get("IsFirmCarryingProfLiabilityIns") !=null)
			{
				inputMap.put("IsFirmCarryingProfLiabilityIns", mapFirm.get("IsFirmCarryingProfLiabilityIns").toString());
				if("Y".equals(mapFirm.get("IsFirmCarryingProfLiabilityIns").toString()))
				{
					Object objPro = SqlResources.getSqlMapProcessor(ctx).findByKey("ProfessionalLiabilityInsDetail.findByKey", ctx);
					if(objPro != null)
					{
						Map mapPro = (Map) objPro;
						if(mapPro.get("IsPriorActDateFull") == null || mapPro.get("IsPriorActDateFull") == "") {
							inputMap.put("IsPriorActDateFull", "N");
						} else {
							inputMap.put("IsPriorActDateFull", mapPro.get("IsPriorActDateFull"));
						}
						inputMap.put("PriorActDatePross", mapPro.get("PriorActDatePross"));						
					}
				}
			}
		}		
		
		Object objPolicyCoverage = SqlResources.getSqlMapProcessor(ctx).findByKey("SqlStmts.UserStatementsaveAccountstmtsCoveragePolicy", ctx);
		if(objPolicyCoverage != null)
		{
			Map mapPolicyCoverage = (Map) objPolicyCoverage;
			inputMap.put("LimitAmount", mapPolicyCoverage.get("LimitAmount"));
			inputMap.put("DeductibleAmount", mapPolicyCoverage.get("DeductibleAmount"));
			
			inputMap.put("IsClaimOptionType", mapPolicyCoverage.get("IsClaimOptionType"));
			inputMap.put("IsClaimExpensesType", mapPolicyCoverage.get("IsClaimExpensesType"));
			
			inputMap.put("IsDollarDefense", mapPolicyCoverage.get("IsDollarDefense"));
			inputMap.put("ManualPremium", mapPolicyCoverage.get("ManualPremium"));
		}
		
		List aoplist = SqlResources.getSqlMapProcessor(ctx).select("SqlStmts.UserStatementsaveAccountstmtspopulateAOPFields", ctx);
		if(aoplist!=null){
			Map map= new HashMap();
			for(int i=0;i<aoplist.size();i++){	
				map = (HashMap)aoplist.get(i);
				logger.debug("AOP_Percentage_" + map.get("AOPKey") + "  " + map
						.get("PercentageValue"));
				inputMap.put("AOP_Percentage_"+map.get("AOPKey"), map.get("PercentageValue"));							
			}
		}
		
		populateGrossRevenue(ctx, inputMap);
		
		Object objAddress = SqlResources.getSqlMapProcessor(ctx).findByKey("Address.findByKey", ctx);
		if(objAddress != null)
		{
			Map mapAddress = (Map) objAddress;
			inputMap.put("City", mapAddress.get("City"));
			inputMap.put("CountyDesc", mapAddress.get("CountyDesc"));
			inputMap.put("StateCode", mapAddress.get("StateCode"));
		}
		
		
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
			inputMap.put("DataProcessingInstalledSoftware", "Y");
		else
			inputMap.put("DataProcessingInstalledSoftware", "N");
		  
	}	
	
	public static void populateGrossRevenue(IContext ctx, Map<String, Object> inputMap)throws Exception{
		
		double total = 0;
		int count = 0;
		List grRevList = SqlResources.getSqlMapProcessor(ctx).select("SqlStmts.BasicInfoviewmomgrossrevenue", ctx);
				
		if(grRevList!=null)
		{
			Map map= new HashMap();
			for(int i=0;i<grRevList.size();i++){	
				map = (HashMap)grRevList.get(i);
				Object objAmount = map.get("Amount");
				if(objAmount != null && !"".equals(objAmount.toString()) && !"0".equals(objAmount.toString()))
   			 	{
	   				total = total + Double.parseDouble(objAmount.toString());
	   				count = count + 1;
	   			}				
			}
			
			if(count > 0)
    		{
    			double AverageRevenue = total/count;
    			inputMap.put("AverageRevenueAmount", Math.decimalFormat(AverageRevenue,0));
    		}
    		else
    			inputMap.put("AverageRevenueAmount", 0);
		}		
	}
	
	
	@SuppressWarnings("unchecked")
	protected static void populateDataInRequest(IContext ctx, Map<String, Object> inputMap) throws Exception
	{
		if(ctx.get("accountantIndication") == null || (ctx.get("accountantIndication") != null && "N".equals(ctx.get("accountantIndication").toString())))
			ctx.put("SchduleRatingModifier1",inputMap.get("SchduleRatingModifier1"));
		
		ctx.put("SchduleRatingModifier2",inputMap.get("SchduleRatingModifier2"));
		ctx.put("SchduleRatingModifier3",inputMap.get("SchduleRatingModifier3"));
		
		ctx.put("PolicyEffectiveDate", inputMap.get("PolicyEffectiveDate"));
		ctx.put("AverageRevenueAmount", inputMap.get("AverageRevenueAmount"));		
		
		ctx.put("BusinessTaxServicesPercentage", inputMap.get("AOP_Percentage_1"));
		ctx.put("EstateTaxServicesPercentage", inputMap.get("AOP_Percentage_2"));
		ctx.put("IndividualTaxServicesPercentage", inputMap.get("AOP_Percentage_3"));
		ctx.put("BookKeepPercentage", inputMap.get("AOP_Percentage_4"));
		
		/*if(ctx.get("accountantIndication") != null && "Y".equals(ctx.get("accountantIndication").toString()))
			ctx.put("AuditNonpublicClientsPercentage", inputMap.get("AOP_Percentage_5"));
		else*/
			ctx.put("AuditNonpublicClientsPercentage", inputMap.get("AOP_Percentage_6"));
		
		ctx.put("ReviewPercentage", inputMap.get("AOP_Percentage_7"));
		
		ctx.put("CompilationPercentage", inputMap.get("AOP_Percentage_8"));
		
		ctx.put("AgreedUponProceduresPercentage", inputMap.get("AOP_Percentage_9"));
		
		ctx.put("OtherAssurancePercentage", inputMap.get("AOP_Percentage_10"));
		
		ctx.put("LitigationSupportPercentage", inputMap.get("AOP_Percentage_11"));
		ctx.put("OtherConsultingPercentage", inputMap.get("AOP_Percentage_12"));
		
		ctx.put("ManagementConsultingBusinessPlanningPercentage",inputMap.get("AOP_Percentage_13"));
		ctx.put("ComputerRelatedServicesPercentage", inputMap.get("AOP_Percentage_14"));
		ctx.put("BusinessValuationPercentage", inputMap.get("AOP_Percentage_15"));
		ctx.put("ForecastsProjectionsPercentage", inputMap.get("AOP_Percentage_16"));
		ctx.put("MergersAndAcquisitionsPercentage", inputMap.get("AOP_Percentage_17"));
		
		ctx.put("BusinessManagementPercentage", inputMap.get("AOP_Percentage_18"));
		ctx.put("InvestmentFinancialPlanningPercentage", inputMap.get("AOP_Percentage_19"));
		ctx.put("SECWorkOtherThanAuditPercentage", inputMap.get("AOP_Percentage_21"));
		
		ctx.put("GeneralBusinessAdvicePercentage", inputMap.get("AOP_Percentage_22"));
		ctx.put("ErisaPensionPlansPercentage", inputMap.get("AOP_Percentage_23"));
		ctx.put("TrusteeServicesPercentage", inputMap.get("AOP_Percentage_24"));
		ctx.put("OtherPercentage", inputMap.get("AOP_Percentage_20"));
		
		
		ctx.put("IsFirmCarryingProfLiabilityIns", inputMap.get("IsFirmCarryingProfLiabilityIns"));
		ctx.put("IsPriorActDateFull", inputMap.get("IsPriorActDateFull"));
		
		int TransactionTypeKey =ctx.get("TransactionTypeKey")!=null && !ctx.get("TransactionTypeKey").equals(HtmlConstants.EMPTY)?Integer.parseInt(ctx.get("TransactionTypeKey").toString()):0;
		if(TransactionTypeKey==12)
		{
			ctx.put("PriorActDatePross", ctx.get("PriorActDatePross"));
			ctx.put("IsPriorActDateFull", "N");
			ctx.put("IsFirmCarryingProfLiabilityIns", "Y");
			
		}
		else
			ctx.put("PriorActDatePross", inputMap.get("PriorActDatePross"));
		ctx.put("FirmYear", inputMap.get("FirmYear"));	
		
		ctx.put("LimitAmount", inputMap.get("LimitAmount"));
		ctx.put("DeductibleAmount", inputMap.get("DeductibleAmount"));		
		ctx.put("IsClaimOptionType", inputMap.get("IsClaimOptionType"));
		ctx.put("IsClaimExpensesType", inputMap.get("IsClaimExpensesType"));
		
		ctx.put("DataProcessingInstalledSoftware", inputMap.get("DataProcessingInstalledSoftware"));		
		ctx.put("StateCode", inputMap.get("StateCode"));	
		
		// For County and City
		if(inputMap.get("City") != null)
		{
			String city = inputMap.get("City").toString();
			city = city.replace("&", "#AMP#");
			ctx.put("City", city);
		}		
		if(inputMap.get("CountyDesc") != null)
		{
			String county = inputMap.get("CountyDesc").toString();
			county = county.replace("&", "#AMP#");
			ctx.put("CountyDesc", county);
		}	
		
		ctx.put("IsDollarDefense", inputMap.get("IsDollarDefense"));		
		ctx.put("ManualPremium", inputMap.get("ManualPremium"));
		  
		ctx.put("CompanyKey","1");
		ctx.put("requestxml",new RequestProcessor().generateRequestXml((Context)ctx));
		
	}
	
	
	public static void populateOuputResponse(IContext ctx) throws Exception
	{
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
			logger.error("populate output reponse-"+e);
//			System.out.println("populate output reponse---"+e);
			throw e;
		}

		ctx.put("CompanyKey",ctx.get("OLDCompanyKey"));
	}
	
	
	public static void populateSurchargeTax(IContext ctx) throws Exception
	{
		List chrgsList = null;
		Object obj1 = SqlResources.getSqlMapProcessor(ctx).select("SqlStmts.UserStatementsaveAccountstmtsSurchargeTax", ctx);
		
		if(obj1 != null && obj1 instanceof List)
			chrgsList = (List) obj1;
		
		Object obj = RuleUtils.executeRule(ctx, "AccountantRule.IsSkipTaxCalculation");			
		boolean flag = true;
		if(obj != null && obj instanceof Boolean )
			flag =(Boolean) obj;
		
		AccountUtils.populateLastUpdateTimeStamp(ctx);		
		
		double mTCountyTax =0.0;
		if(ctx.get("MTTaxAmmount") != null && !"".equals(ctx.get("MTTaxAmmount").toString()))
			mTCountyTax = Double.parseDouble(ctx.get("MTTaxAmmount").toString());
		
		
		if(chrgsList == null || (chrgsList != null && chrgsList.size() == 0))
		{
			if(!flag){
				if(ctx.get("MTTaxAmmount") != null)
				{
					ctx.put("SurchargeTaxTypeKey", 1);
					ctx.put("TaxAmount", ctx.get("MTTaxAmmount"));
					SqlResources.getSqlMapProcessor(ctx).insert("SurchargeTax.insert", ctx);
				}
			}
			
			 if (mTCountyTax <= 0) {
				if(ctx.get("CountyTaxAmmount") != null)
				{
					ctx.put("SurchargeTaxTypeKey", 2);
					ctx.put("TaxAmount", ctx.get("CountyTaxAmmount"));
					SqlResources.getSqlMapProcessor(ctx).insert("SurchargeTax.insert", ctx);
				}
			 }
			
			if(ctx.get("State1TaxAmmount") != null)
			{
				ctx.put("SurchargeTaxTypeKey", 3);
				ctx.put("TaxAmount", ctx.get("State1TaxAmmount"));
				SqlResources.getSqlMapProcessor(ctx).insert("SurchargeTax.insert", ctx);
			}
			if(ctx.get("State2TaxAmmount") != null)
			{
				ctx.put("SurchargeTaxTypeKey", 4);
				ctx.put("TaxAmount", ctx.get("State2TaxAmmount"));
				SqlResources.getSqlMapProcessor(ctx).insert("SurchargeTax.insert", ctx);
			}
		}
		
		IContext newCtx = null;
		if(chrgsList != null && chrgsList.size() > 0)
		{
			for(int i=0;i<chrgsList.size();i++)
			{	
				Map chrgsMap = (HashMap)chrgsList.get(i);
				newCtx = new Context();
				newCtx.putAll(chrgsMap);
				newCtx.setProject(ctx.getProject());
				newCtx.put("LastUpdateUserID", ctx.get("LastUpdateUserID"));				
				
				if(newCtx.get("SurchargeTaxTypeKey") != null && "1".equals(newCtx.get("SurchargeTaxTypeKey").toString()))
				{
					if(!flag){
						newCtx.put("TaxAmount", ctx.get("MTTaxAmmount"));
						SqlResources.getSqlMapProcessor(newCtx).update("SurchargeTax.update", newCtx);	
					}
				}				
				else if(newCtx.get("SurchargeTaxTypeKey") != null && "2".equals(newCtx.get("SurchargeTaxTypeKey").toString()))
				{
					if (mTCountyTax <= 0) {
						newCtx.put("TaxAmount", ctx.get("CountyTaxAmmount"));
						SqlResources.getSqlMapProcessor(newCtx).update("SurchargeTax.update", newCtx);		
					}
				}			
				else if(newCtx.get("SurchargeTaxTypeKey") != null && "3".equals(newCtx.get("SurchargeTaxTypeKey").toString()))
				{
					newCtx.put("TaxAmount", ctx.get("State1TaxAmmount"));
					SqlResources.getSqlMapProcessor(newCtx).update("SurchargeTax.update", newCtx);				
				}
				else if(newCtx.get("SurchargeTaxTypeKey") != null && "4".equals(newCtx.get("SurchargeTaxTypeKey").toString()))
				{
					newCtx.put("TaxAmount", ctx.get("State2TaxAmmount"));
					SqlResources.getSqlMapProcessor(newCtx).update("SurchargeTax.update", newCtx);				
				}				
			}
		}		
			
	}
	
	public static void processQuotesListQuickQuote(IContext ctx) throws Exception
	{
		List quotesList = null;
		Object obj = SqlResources.getSqlMapProcessor(ctx).select("SqlStmts.UserStatementManualBoQueriesgetQuoteListAll", ctx);
		
		if(obj != null && obj instanceof List)
			quotesList = (List) obj;
		
		
		
		if(quotesList == null)
			return;
		
		for(int i=0;i<quotesList.size();i++)
		{	
			Map quoteMap = (HashMap)quotesList.get(i);
			callingRatingEngineQuickQuote(ctx, quoteMap);	
			
		}				  
	}
	
	public static void callingRatingEngineQuickQuote(IContext ctx, Map quoteMap) throws Exception
	{
		WebservicecallImpl webSer = null; 
		IContext newCtx = new Context();
		newCtx.put("QuickQuoteModificationFactorFlag", ctx.get("QuickQuoteModificationFactorFlag"));
		newCtx.setProject(ctx.getProject());
		newCtx.putAll(quoteMap);
		newCtx.put("PolicyKey",ctx.get("PolicyKey"));
		newCtx.put("VersionKey", ctx.get("VersionKey"));
		newCtx.put("VersionSequence",ctx.get("VersionSequence"));
		newCtx.put("AddressKey",ctx.get("AddressKey"));
		newCtx.put("StateCode",ctx.get("StateCode"));
		newCtx.put("AddressTypeKey",ctx.get("AddressTypeKey"));
		newCtx.put("CompanyKey","1");
		newCtx.put("LOBKey",ctx.get("LOBKey"));
		newCtx.put("LookupStateCode",ctx.get("LookupStateCode"));
		newCtx.put("ERPYear",ctx.get("ERPYear"));
		newCtx.put("accountantIndication", ctx.get("accountantIndication"));
		newCtx.put("LastUpdateTimeStamp", ctx.get("LastUpdateTimeStamp"));
		
		if(ctx.get("accountantIndication") != null && "Y".equals(ctx.get("accountantIndication").toString()))
		{
		//	newCtx.put("SchduleRatingModifier1", ctx.get("SchduleRatingModifier1"));
			List modifierList =(List) SqlResources.getSqlMapProcessor(ctx).select("SqlStmts.UserStatementManualBoQueriesgetIndicationModifiersDetails", ctx);
			if(modifierList != null && modifierList instanceof List)
			{
				Map modifierMap=null;
				for(int i=0;i<modifierList.size();i++)
				{
					modifierMap=(Map)modifierList.get(i);
					newCtx.put("SchduleRatingModifier"+modifierMap.get("ModifierKey"),modifierMap.get("Percentage"));
					ctx.put("SchduleRatingModifier"+modifierMap.get("ModifierKey"),modifierMap.get("Percentage"));
				}
				
			}
		}
		newCtx.put("LastUpdateUserID", ctx.get("LastUpdateUserID"));
		
		AccountUtils.populateLastUpdateTimeStamp(newCtx);
		
		
		populateInputRequestQuickQuote(newCtx);
		webSer = new WebservicecallImpl();
		if(ctx.get("inputMO") != null)
			webSer.setInput(ctx.get("inputMO").toString());
		if(ctx.get("outputMO") != null)
			webSer.setOutput(ctx.get("outputMO").toString());
		if(ctx.get("webservicename") != null)
			webSer.setName(ctx.get("webservicename").toString());
		
		String url = null;
		try{
			url = SystemProperties.getInstance().getString("appl."+ctx.getProject()+".webserviceurl");
		}
		catch (Exception e){
			throw e;
		}
		
		if(url == null)
			return;
		
		webSer.setServiceurl(url);			
		newCtx.put("responseMap", webSer.execute(newCtx));
		populateOuputResponseQuickQuote(newCtx);
		
		SqlResources.getSqlMapProcessor(newCtx).update("PolicyTransaction.update", newCtx);
		SqlResources.getSqlMapProcessor(newCtx).update("PolicyCoverage.update", newCtx);
		SqlResources.getSqlMapProcessor(newCtx).update("RatingTrace.update", newCtx);
		SqlResources.getSqlMapProcessor(newCtx).update("Quote.update", newCtx);
		populateSurchargeTax(newCtx);
		RuleUtils.executeRule(newCtx,"AccountantRule.AssignCreatedByAndCreatedTimeStamp");
		DBUtils.executeDBOperation(newCtx, "QuotationEstimationACC", "1");
	}
	
	public static void populateInputRequestQuickQuote(IContext ctx) throws Exception
	{
		Map<String, Object> inputMap = new HashMap<String, Object>();
		fetchDataQuickQuote(ctx, inputMap);
		populateDataInRequest(ctx, inputMap);		  
	}
	
	protected static void fetchDataQuickQuote(IContext ctx, Map<String, Object> inputMap) throws Exception
	{		
		Object objPolicy = SqlResources.getSqlMapProcessor(ctx).findByKey("Policy.findByKey", ctx);
		if(objPolicy != null)
		{
			Map mapPolicy = (Map) objPolicy;
			inputMap.put("PolicyEffectiveDate", mapPolicy.get("PolicyEffectiveDate"));
		}
		
		Object objFirm = SqlResources.getSqlMapProcessor(ctx).findByKey("Firm.findByKey", ctx);
		if(objFirm != null)
		{
			Object objPro = SqlResources.getSqlMapProcessor(ctx).findByKey("ProfessionalLiabilityInsDetail.findByKey", ctx);
			if(objPro != null)
			{
				Map mapPro = (Map) objPro;
				inputMap.put("PriorActDatePross", mapPro.get("PriorActDatePross"));	
				inputMap.put("IsFirmCarryingProfLiabilityIns", "Y");
				inputMap.put("IsPriorActDateFull", "N");
				
				/*if(mapPro.get("FirmYear") != null && !"".equals(mapPro.get("FirmYear").toString()))
				{
					int FirmYear = Integer.parseInt(mapPro.get("FirmYear").toString());
					if(FirmYear == 0){
						inputMap.put("IsPriorActDateFull", "N");						
					}
					else {
						inputMap.put("IsPriorActDateFull", "Y");						
					}
				}
				else {
					inputMap.put("IsPriorActDateFull", "N");					
				}*/
				
				/*int firmYrs = 0;
				if(mapPro.get("FirmYear") != null){
					firmYrs = Integer.parseInt(mapPro.get("FirmYear").toString());
					if(firmYrs < 0)
						firmYrs = -1 * firmYrs;
				}
				inputMap.put("FirmYear", firmYrs);*/
			}
		}		
		
		Object objPolicyCoverage = SqlResources.getSqlMapProcessor(ctx).findByKey("SqlStmts.UserStatementsaveAccountstmtsCoveragePolicy", ctx);
		if(objPolicyCoverage != null)
		{
			Map mapPolicyCoverage = (Map) objPolicyCoverage;
			inputMap.put("LimitAmount", mapPolicyCoverage.get("LimitAmount"));
			inputMap.put("DeductibleAmount", mapPolicyCoverage.get("DeductibleAmount"));
			
			inputMap.put("IsClaimOptionType", mapPolicyCoverage.get("IsClaimOptionType"));
			inputMap.put("IsClaimExpensesType", mapPolicyCoverage.get("IsClaimExpensesType"));
			
			inputMap.put("IsDollarDefense", mapPolicyCoverage.get("IsDollarDefense"));
			inputMap.put("ManualPremium", mapPolicyCoverage.get("ManualPremium"));
		}
		
		List aoplist = null;
		if(ctx.get("accountantIndication") != null && "Y".equals(ctx.get("accountantIndication").toString()))
			aoplist = SqlResources.getSqlMapProcessor(ctx).select("SqlStmts.UserStatementsaveAccountstmtspopulateAOPFields", ctx);
		else
			aoplist = SqlResources.getSqlMapProcessor(ctx).select("SqlStmts.UserStatementsaveAccountstmtspopulateAOPFieldsQuickQuote", ctx);
		
		if(aoplist!=null){
			Map map= new HashMap();
			for(int i=0;i<aoplist.size();i++){	
				map = (HashMap)aoplist.get(i);
				inputMap.put("AOP_Percentage_"+map.get("AOPKey"), map.get("PercentageValue"));							
			}
		}
		
		populateGrossRevenue(ctx, inputMap);
		
		Object objAddress = SqlResources.getSqlMapProcessor(ctx).findByKey("Address.findByKey", ctx);
		if(objAddress != null)
		{
			Map mapAddress = (Map) objAddress;
			inputMap.put("City", mapAddress.get("City"));
			inputMap.put("CountyDesc", mapAddress.get("CountyDesc"));
			inputMap.put("StateCode", mapAddress.get("StateCode"));
		}		
		
		inputMap.put("DataProcessingInstalledSoftware", "N");
		
		/*new Modifiers().populateCoverageModifiers(ctx);		
		inputMap.put("SchduleRatingModifier1",ctx.get("SchduleRatingModifier1"));
		inputMap.put("SchduleRatingModifier2",ctx.get("SchduleRatingModifier2"));
		inputMap.put("SchduleRatingModifier3",ctx.get("SchduleRatingModifier3"));*/
		  
	}
	
	public static void populateOuputResponseQuickQuote(IContext ctx) throws Exception
	{
		Map responseMap = null;
		if(ctx.get("responseMap") != null)
			responseMap = (Map)ctx.get("responseMap");
		
		if(responseMap != null)
		{
			if(responseMap.get("FinalBaseLimitPremiumWithTax") !=null && !"".equals(responseMap.get("FinalBaseLimitPremiumWithTax").toString()))
			{			
				ctx.put("InvoicedPremium", Math.round(responseMap.get("FinalBaseLimitPremiumWithTax")));
				ctx.put("Premium", Math.round(responseMap.get("FinalBaseLimitPremiumWithTax")));
			}
			
			if(responseMap.get("FinalBaseLimitPremium") !=null && !"".equals(responseMap.get("FinalBaseLimitPremium").toString()))
			{			
				ctx.put("TotalPremium", Math.round(responseMap.get("FinalBaseLimitPremium")));
				ctx.put("InitialCovPremium", Math.round(responseMap.get("FinalBaseLimitPremium")));
			}					
			
//			ctx.put("InvoicedPremium", Math.round(responseMap.get("FinalBaseLimitPremiumWithTax")));			
//			ctx.put("TotalPremium", Math.round(responseMap.get("FinalBaseLimitPremium")));
//			ctx.put("Premium", Math.round(responseMap.get("FinalBaseLimitPremiumWithTax"))); 
//			ctx.put("InitialCovPremium", Math.round(responseMap.get("FinalBaseLimitPremium")));	
			
			
			ctx.put("ClaimsMadeYearDesc", responseMap.get("ClaimsMadeYearDesc"));
			ctx.put("IsMinimumPremium", responseMap.get("IsMinimumPremium"));
			ctx.put("MinimumPremiumValue", responseMap.get("MinimumPremiumValue"));
			
			
			if(ctx.get("TotalPremium") != null && !"".equals(ctx.get("TotalPremium").toString()))
			{
				int divider = 15;  	//Default
				
				if(SystemProperties.getInstance().getString("appl."+ctx.getProject()+".firstrangedividerQQ") != null)
					divider = new Integer(SystemProperties.getInstance().getString("appl."+ctx.getProject()+".firstrangedividerQQ"));
				
				if("Y".equals(ctx.get("QuickQuoteModificationFactorFlag")))
					divider = java.lang.Math.abs(new Integer(SystemProperties.getInstance().getString("appl."+ctx.getProject()+".ModifierQQ")));
				
				double FirstRange = 0;
				if(!"0".equals(ctx.get("TotalPremium").toString()))
					FirstRange = (Double.parseDouble(ctx.get("TotalPremium").toString()) * divider) / 100;  
				
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
			
			
			double mTCountyTax = 0.0;
			if(responseMap.get("MTTaxAmmount") !=null && !"".equals(responseMap.get("MTTaxAmmount").toString()) && new Double(responseMap.get("MTTaxAmmount").toString()).doubleValue() != 0)
			{
				mTCountyTax = Double.parseDouble(responseMap.get("MTTaxAmmount").toString());
				ctx.put("MTTaxAmmount", Math.round(responseMap.get("MTTaxAmmount")));
			}
			if(responseMap.get("CountyTaxAmmount") !=null && !"".equals(responseMap.get("CountyTaxAmmount").toString()) && new Double(responseMap.get("CountyTaxAmmount").toString()).doubleValue() != 0)
			{
				mTCountyTax = Double.parseDouble(responseMap.get("CountyTaxAmmount").toString());
				ctx.put("CountyTaxAmmount", Math.round(responseMap.get("CountyTaxAmmount")));
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
	}
	
	public static void prepareDataForRatingEngine(IContext ctx) {
		try {
			
			logger.debug("Going to Prepare Data For Rating Engine For State " + ctx.get("StateCode"));
			ctx.put("PolicyKey_rule", ctx.get("PolicyKey").toString());
			ctx.put("StateCode_rule", ctx.get("StateCode").toString());
			SqlResources.getSqlMapProcessor(ctx).update("RatingTrace.StateSpecificRatingDataAcc_proc", ctx);
			logger.debug("Data Prepared for PolicyKey - " + ctx.get("PolicyKey"));
			
		} catch (Exception e) {
			logger.debug("Exception in Method prepareDataForRatingEngine" + e.getMessage());
			e.printStackTrace();
		}
	}
	// Endrosment start
	public static void dataPreprationForEndorsementRating(Context ctx)
	{
		try
		{
			logger.debug("Going to preparing data for rating engine for endorsement ");
			String stateCode=ctx.get("StateCode").toString();
			long aggregateLimit =ctx.get("AggregateLimit")!=null &&!ctx.get("AggregateLimit").equals(HtmlConstants.EMPTY)?Long.parseLong(ctx.get("AggregateLimit").toString()):0;
			String  claimExpenseType=ctx.get("IsClaimExpenseType")!=null && !ctx.get("IsClaimExpenseType").equals(HtmlConstants.EMPTY)?ctx.get("IsClaimExpenseType").toString():"";
			char IsClaimExpenseType='\0';
			if( stateCode.equals("AR"))
			{
					if(aggregateLimit < 1000000 && claimExpenseType.equalsIgnoreCase("N"))
						ctx.put("IsClaimExpenseType",'Y');
			}		
				
			if( stateCode.equals("SD"))
				ctx.put("IsClaimExpenseType",'N');
				

			if( stateCode.equals("NM")||  stateCode.equals("NJ"))
				ctx.put("IsDollarDefense",'Y');
			if( stateCode.equals("NJ") || stateCode.equals("NM") || stateCode.equals("VT"))
				ctx.put("IsClaimExpenseType",'Y');
		}
		catch(Exception e)
		{
			e.printStackTrace();
			
		}
	}

	public static void populateEndorsementInputRequestData(Context ctx) throws Exception {
		Map<String, Object> inputMap = new HashMap<String, Object>();
		fetchDataForEndorsementInputRequest(ctx, inputMap);
		populateDataInRequest((IContext)ctx, inputMap);
	}
	public static void fetchDataForEndorsementInputRequest(Context ctx, Map<String, Object> inputMap)
	{
		try
		{
			int endorsementType=ctx.get("EndorsementType")!=null &&!ctx.get("EndorsementType").equals(HtmlConstants.EMPTY)?Integer.parseInt(ctx.get("EndorsementType").toString()):0;
			Context ruleCtx = new Context();
			Object objAddress = SqlResources.getSqlMapProcessor(ctx).findByKey("Address.findByKey", ctx);
			if (objAddress != null) 
			{
				Map mapAddress = (Map) objAddress;
				inputMap.put("City", mapAddress.get("City"));
				inputMap.put("CountyDesc", mapAddress.get("CountyDesc"));
				inputMap.put("StateCode", mapAddress.get("StateCode"));
				// This new context is only for the below rule
				ruleCtx.setProject(ctx.getProject());
				ruleCtx.put("StateCode", mapAddress.get("StateCode"));
			}

			// This code will populate Modifiers only for States that allow
			// Modifiers

			Boolean isMofifierState = false;
			Object obj = RuleUtils.executeRule(ruleCtx,"AccountantRule.IsModifierState");
			if (obj != null && obj instanceof Boolean) 
			{
				isMofifierState = (Boolean) obj;
			}

			if (isMofifierState)
				new Modifiers().populateCoverageModifiers(ctx);

			inputMap.put("SchduleRatingModifier1", ctx.get("SchduleRatingModifier1"));
			inputMap.put("SchduleRatingModifier2", ctx.get("SchduleRatingModifier2"));
			inputMap.put("SchduleRatingModifier3", ctx.get("SchduleRatingModifier3"));

			// fetch policy effective date
			Object objPolicy = SqlResources.getSqlMapProcessor(ctx).findByKey("Policy.findByKey", ctx);
			if (objPolicy != null) {
				Map mapPolicy = (Map) objPolicy;
				inputMap.put("PolicyEffectiveDate", mapPolicy.get("PolicyEffectiveDate"));
			}
			
			Object objFirm = SqlResources.getSqlMapProcessor(ctx).findByKey("Firm.findByKey", ctx);
			if(objFirm != null)
			{
				Map mapFirm = (Map) objFirm;
				if(mapFirm.get("IsFirmCarryingProfLiabilityIns") !=null)
				{
					inputMap.put("IsFirmCarryingProfLiabilityIns", mapFirm.get("IsFirmCarryingProfLiabilityIns").toString());
					if("Y".equals(mapFirm.get("IsFirmCarryingProfLiabilityIns").toString()))
					{
						Object objPro = SqlResources.getSqlMapProcessor(ctx).findByKey("ProfessionalLiabilityInsDetail.findByKey", ctx);
						if(objPro != null)
						{
							Map mapPro = (Map) objPro;
							inputMap.put("IsPriorActDateFull", mapPro.get("IsPriorActDateFull"));
							inputMap.put("PriorActDatePross", mapPro.get("PriorActDatePross"));						
						}
					}
				}
			}
			
			// fetch PolicyCoverage data and put in request
			if(endorsementType==9)
			{
				Object objPolicyCoverage = SqlResources.getSqlMapProcessor(ctx).findByKey("SqlStmts.UserStatementsaveAccountstmtsgetDeductiblesDataForEndorsement",ctx);
				
				if (objPolicyCoverage != null) 
				{
					Map mapPolicyCoverage = (Map) objPolicyCoverage;
					
					inputMap.put("DeductibleAmount", mapPolicyCoverage.get("DeductibleAmount"));
					inputMap.put("IsClaimOptionType", mapPolicyCoverage.get("IsClaimOptionType"));
					inputMap.put("IsDollarDefense",  mapPolicyCoverage.get("IsDollarDefense"));
					
				}
				inputMap.put("LimitAmount", ctx.get("LimitAmount"));
				inputMap.put("IsClaimExpensesType", ctx.get("IsClaimExpenseType"));
				inputMap.put("ManualPremium", "N");
				
			}
			else if(endorsementType==10)
			{
				Object objPolicyCoverage = SqlResources.getSqlMapProcessor(ctx).findByKey("SqlStmts.UserStatementsaveAccountstmtsSetCoverageData",ctx);
	
				if (objPolicyCoverage != null) {
					Map mapPolicyCoverage = (Map) objPolicyCoverage;
					inputMap.put("LimitAmount", mapPolicyCoverage.get("LimitAmount"));
					inputMap.put("IsClaimExpensesType", mapPolicyCoverage.get("IsClaimExpensesType"));
					inputMap.put("IsDollarDefense", mapPolicyCoverage.get("IsDollarDefense"));
					inputMap.put("ManualPremium", mapPolicyCoverage.get("ManualPremium"));
					inputMap.put("IsClaimOptionType", mapPolicyCoverage.get("IsClaimOptionType"));
				}
				inputMap.put("DeductibleAmount", ctx.get("DeductibleAmount"));
				
			}
			else if(endorsementType==13)
			{
				Object objPolicyCoverage = SqlResources.getSqlMapProcessor(ctx).findByKey("SqlStmts.UserStatementsaveAccountstmtsSetCoverageData",ctx);
	
				if (objPolicyCoverage != null) {
					Map mapPolicyCoverage = (Map) objPolicyCoverage;
					inputMap.put("LimitAmount", mapPolicyCoverage.get("LimitAmount"));
					inputMap.put("IsClaimExpensesType", mapPolicyCoverage.get("IsClaimExpensesType"));
					inputMap.put("IsDollarDefense", mapPolicyCoverage.get("IsDollarDefense"));
					inputMap.put("ManualPremium", mapPolicyCoverage.get("ManualPremium"));
					inputMap.put("IsClaimOptionType", mapPolicyCoverage.get("IsClaimOptionType"));
				}
				inputMap.put("DeductibleAmount", ctx.get("DeductibleAmount"));
				
			}
			else
			{
				Object objPolicyCoverage = SqlResources.getSqlMapProcessor(ctx).findByKey("SqlStmts.UserStatementsaveAccountstmtsCoveragePolicy",ctx);
	
				if (objPolicyCoverage != null) {
					Map mapPolicyCoverage = (Map) objPolicyCoverage;
					inputMap.put("LimitAmount", mapPolicyCoverage.get("LimitAmount"));
					inputMap.put("DeductibleAmount", mapPolicyCoverage.get("DeductibleAmount"));
					inputMap.put("IsClaimOptionType", mapPolicyCoverage.get("IsClaimOptionType"));
					inputMap.put("IsClaimExpensesType", mapPolicyCoverage.get("IsClaimExpensesType"));
					inputMap.put("IsDollarDefense", mapPolicyCoverage.get("IsDollarDefense"));
					inputMap.put("ManualPremium", mapPolicyCoverage.get("ManualPremium"));
	
				}
			}
			// fetch AOP data
			List aoplist = SqlResources.getSqlMapProcessor(ctx).select("SqlStmts.UserStatementsaveAccountstmtspopulateAOPFields", ctx);
			if(aoplist!=null){
				Map map= new HashMap();
				for(int i=0;i<aoplist.size();i++){	
					map = (HashMap)aoplist.get(i);
					logger.debug("AOP_Percentage_" + map.get("AOPKey") + "  " + map
							.get("PercentageValue"));
					inputMap.put("AOP_Percentage_"+map.get("AOPKey"), map.get("PercentageValue"));							
				}
			}

			populateGrossRevenue(ctx, inputMap);
			
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
				inputMap.put("DataProcessingInstalledSoftware", "Y");
			else
				inputMap.put("DataProcessingInstalledSoftware", "N");
				
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static void main(String args[])
	{
//		double mTCountyTax = 0.0; 
//		Double.parseDouble(0);
		
//		if( mTCountyTax<=0)
//		{
//			System.out.println("hi----"+mTCountyTax);
//		}
		//responseMap.get("State1TaxAmmount").toString())
		//System.out.println(Math.decimalFormat("3466.6789", 2));
		
		
		System.out.println(Double.parseDouble("0"));
		System.out.println(Math.round("0"));
	}

}
