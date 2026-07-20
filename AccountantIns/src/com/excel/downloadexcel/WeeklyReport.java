package com.excel.downloadexcel;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.excel.oldpim.ExcelDatabaseMapping;
import com.excel.oldpim.ExcelUtility;
import com.ormapping.ibatis.SqlResources;
import com.util.Context;
import com.util.SystemProperties;

public class WeeklyReport {
	
	
	public void getDataForWeeklyReport(Context ctx, Sheet sheetname, String excelsheet) throws Exception {
			
		if(excelsheet.equals("ALL Issued Policies")){
			Object issuedlist = SqlResources.getSqlMapProcessor(ctx).select("SqlStmts.UserStatementreportgenerationgetReportListByIssuedPolicy", ctx);
			ctx.put("issuedlist", issuedlist);			
			
		}else{
			
			Object statedatelist = SqlResources.getSqlMapProcessor(ctx).select("Account.weeklyreport_by_state", ctx);
			ctx.put("statedatelist", statedatelist);
			
			if("ExportAllReport".equals(excelsheet))
			{
				Object effectivedaterenewallist = SqlResources.getSqlMapProcessor(ctx).select("Account.weeklyreport_effectivedate_renewal", ctx);
				ctx.put("effectivedaterenewallist", effectivedaterenewallist);
			}
			
			Object effectivedatelist = SqlResources.getSqlMapProcessor(ctx).select("Account.weeklyreport_effectivedate_newbusiness", ctx);
			ctx.put("effectivedatelist", effectivedatelist);		
		
			Object lastweeklist = SqlResources.getSqlMapProcessor(ctx).select("Account.weeklyreport_creationdate_LastWeek", ctx);
			ctx.put("lastweeklist", lastweeklist);		

			Object creativedatelist = SqlResources.getSqlMapProcessor(ctx).select("Account.weeklyreport_creationdate", ctx);
			ctx.put("creativedatelist", creativedatelist);	
		}
			//populateReportList(ctx, sheetname,excelsheet);
			
			
			populateReportList(ctx, sheetname,excelsheet);
		
	}
	
	public void getAllDataForWeeklyReport(Context ctx, Sheet sheetname, String excelsheet) throws Exception {
		Object allDataList = null ;
		if(excelsheet.equals("Accountant")){
			allDataList = SqlResources.getSqlMapProcessor(ctx).select("Account.FetchAllAccountantData", ctx);
			ctx.put("AllDataListAcc", allDataList);	
		}else if(excelsheet.equals("Lawyers")){
			allDataList = SqlResources.getSqlMapProcessor(ctx).select("Account.FetchAllLawyersData", ctx);
			ctx.put("AllDataListLaw", allDataList);	
		}
		
			
			
			populateAllReportList(ctx, sheetname,excelsheet);
		
	}
	
	public void populateAllReportList(Context ctx, Sheet sheet, String excelsheet) throws Exception{
		 Map excelFieldMapping = (HashMap) ctx.get("EXCEL_FIELD_MAPPING");
		 if(excelsheet.equals("Accountant")){
			 Map map = new HashMap(); ;
			
			 List allaccountantlist = (List)ctx.get("AllDataListAcc");
				for (int i = 0; i < allaccountantlist.size(); i++) {
					
					
					//map.putAll((HashMap)allaccountantlist.get(i));
					map = (HashMap) allaccountantlist.get(i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "PolicyKey", false), excelFieldMapping.get("PolicyKey"), i);	
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "AccountID", false), excelFieldMapping.get("AccountID"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "QuoteNumber", false), excelFieldMapping.get("QuoteNumber"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "AccountName", false), excelFieldMapping.get("AccountName"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "Status", false), excelFieldMapping.get("Status"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "State", false), excelFieldMapping.get("State"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "Limit", false), excelFieldMapping.get("Limit"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "Deductible", false), excelFieldMapping.get("Deductible"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "ClaimExpensesType", false), excelFieldMapping.get("ClaimExpensesType"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "TotalPremium", false), excelFieldMapping.get("TotalPremium"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "InvoicedPremium", false), excelFieldMapping.get("InvoicedPremium"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "Modifier", false), excelFieldMapping.get("Modifier"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "Amount1", false), excelFieldMapping.get("Amount1"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "Amount2", false), excelFieldMapping.get("Amount2"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "Amount3", false), excelFieldMapping.get("Amount3"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "IsFirmCarryingProfLiabilityIns", false), excelFieldMapping.get("IsFirmCarryingProfLiabilityIns"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "ExpiringCarrier", false), excelFieldMapping.get("ExpiringCarrier"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "LimitPross", false), excelFieldMapping.get("LimitPross"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "DeductiblePross", false), excelFieldMapping.get("DeductiblePross"), i);
					
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "IsPriorActDateFull", false), excelFieldMapping.get("IsPriorActDateFull"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "PriorActDatePross", false), excelFieldMapping.get("PriorActDatePross"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "FirmYear", false), excelFieldMapping.get("FirmYear"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "ProInsPremium", false), excelFieldMapping.get("ProInsPremium"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "PolicyExpirationDatePross", false), excelFieldMapping.get("PolicyExpirationDatePross"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "IsPublicTradedAudited", false), excelFieldMapping.get("IsPublicTradedAudited"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "IsPublicPrivateOfferingsDone", false), excelFieldMapping.get("IsPublicPrivateOfferingsDone"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "IsInvestmentVenture", false), excelFieldMapping.get("IsInvestmentVenture"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "IsSyndicationManagers", false), excelFieldMapping.get("IsSyndicationManagers"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "IsFirmRenderedAuditOrAttestService", false), excelFieldMapping.get("IsFirmRenderedAuditOrAttestService"), i);
					
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "IsManagementServiceProvided", false), excelFieldMapping.get("IsManagementServiceProvided"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "IsCommissionRecieved", false), excelFieldMapping.get("IsCommissionRecieved"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "IsDebtOrEquityFinancing", false), excelFieldMapping.get("IsDebtOrEquityFinancing"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "IsNonPublicInvestmentDone", false), excelFieldMapping.get("IsNonPublicInvestmentDone"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "IsServedAsTrustee", false), excelFieldMapping.get("IsServedAsTrustee"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "IsFirmRenderedServiceForAnyClient", false), excelFieldMapping.get("IsFirmRenderedServiceForAnyClient"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "AOP_Percentage_1", false), excelFieldMapping.get("AOP_Percentage_1"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "AOP_Percentage_2", false), excelFieldMapping.get("AOP_Percentage_2"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "AOP_Percentage_3", false), excelFieldMapping.get("AOP_Percentage_3"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "AOP_Percentage_4", false), excelFieldMapping.get("AOP_Percentage_4"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "AOP_Percentage_5", false), excelFieldMapping.get("AOP_Percentage_5"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "AOP_Percentage_6", false), excelFieldMapping.get("AOP_Percentage_6"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "AOP_Percentage_7", false), excelFieldMapping.get("AOP_Percentage_7"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "AOP_Percentage_8", false), excelFieldMapping.get("AOP_Percentage_8"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "AOP_Percentage_9", false), excelFieldMapping.get("AOP_Percentage_9"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "AOP_Percentage_10", false), excelFieldMapping.get("AOP_Percentage_10"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "AOP_Percentage_11", false), excelFieldMapping.get("AOP_Percentage_11"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "AOP_Percentage_12", false), excelFieldMapping.get("AOP_Percentage_12"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "AOP_Percentage_13", false), excelFieldMapping.get("AOP_Percentage_13"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "AOP_Percentage_14", false), excelFieldMapping.get("AOP_Percentage_14"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "AOP_Percentage_15", false), excelFieldMapping.get("AOP_Percentage_15"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "AOP_Percentage_16", false), excelFieldMapping.get("AOP_Percentage_16"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "AOP_Percentage_17", false), excelFieldMapping.get("AOP_Percentage_17"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "AOP_Percentage_18", false), excelFieldMapping.get("AOP_Percentage_18"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "AOP_Percentage_19", false), excelFieldMapping.get("AOP_Percentage_19"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "AOP_Percentage_20", false), excelFieldMapping.get("AOP_Percentage_20"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "AOP_Percentage_21", false), excelFieldMapping.get("AOP_Percentage_21"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "AOP_Percentage_22", false), excelFieldMapping.get("AOP_Percentage_22"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "AOP_Percentage_23", false), excelFieldMapping.get("AOP_Percentage_23"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "AOP_Percentage_24", false), excelFieldMapping.get("AOP_Percentage_24"), i);
					
					
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "AccountEmail", false), excelFieldMapping.get("AccountEmail"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "PolicyEffectiveDate", false),  excelFieldMapping.get("PolicyEffectiveDate") , i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "Address1", false), excelFieldMapping.get("Address1"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "Address2", false), excelFieldMapping.get("Address2"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "City", false), excelFieldMapping.get("City"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "Zip", false), excelFieldMapping.get("Zip"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "Comment", false), excelFieldMapping.get("Comment"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "IsBasicPage", false), excelFieldMapping.get("IsBasicPage"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "IsRenewed", false), excelFieldMapping.get("IsRenewed"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "SubProducerNumber", false), excelFieldMapping.get("SubProducerNumber"), i);
					
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "IsSuitInstituted", false), excelFieldMapping.get("IsSuitInstituted"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "IsAwarenessOfAnyProfLiability", false), excelFieldMapping.get("IsAwarenessOfAnyProfLiability"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "IsAwarenessOfAnyProfLiabilitySuitAgainst", false), excelFieldMapping.get("IsAwarenessOfAnyProfLiabilitySuitAgainst"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "PolicyStatus", false), excelFieldMapping.get("PolicyStatus"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "PolicyNumber", false), excelFieldMapping.get("PolicyNumber"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "RenewOfPolicyNumber", false), excelFieldMapping.get("RenewOfPolicyNumber"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "Reviewer", false), excelFieldMapping.get("Reviewer"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "PolicyExpirationDate", false), excelFieldMapping.get("PolicyExpirationDate"), i);
					
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "ContactPerson", false), excelFieldMapping.get("ContactPerson"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "County", false), excelFieldMapping.get("County"), i);					
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "Modifier1", false), excelFieldMapping.get("Modifier1"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "Modifier2", false), excelFieldMapping.get("Modifier2"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "Modifier3", false), excelFieldMapping.get("Modifier3"), i);
					
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "ClaimOptionType", false), excelFieldMapping.get("ClaimOptionType"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "CreatedDate", false), excelFieldMapping.get("CreatedDate"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "TotalFirmPersonnel", false), excelFieldMapping.get("TotalFirmPersonnel"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "Comment", false), excelFieldMapping.get("Comment"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "TotalModifiers", false), excelFieldMapping.get("TotalModifiers"), i);
				}
				
			 
		 }else if(excelsheet.equals("Lawyers")){
			 Map map = new HashMap(); ;
			 List alllawyerlist = (List)ctx.get("AllDataListLaw");
				for (int i = 0; i < alllawyerlist.size(); i++) {
					
					map = (HashMap) alllawyerlist.get(i);
					//map.putAll((HashMap)alllawyerlist.get(i));
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "PolicyKey", false), excelFieldMapping.get("PolicyKey"), i);	
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "AccountID", false), excelFieldMapping.get("AccountID"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "QuoteNumber", false), excelFieldMapping.get("QuoteNumber"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "AccountName", false), excelFieldMapping.get("AccountName"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "Status", false), excelFieldMapping.get("Status"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "State", false), excelFieldMapping.get("State"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "Limit", false), excelFieldMapping.get("Limit"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "Deductible", false), excelFieldMapping.get("Deductible"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "ClaimExpensesType", false), excelFieldMapping.get("ClaimExpensesType"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "IsClaimOptionType", false), excelFieldMapping.get("IsClaimOptionType"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "TotalPremium", false), excelFieldMapping.get("TotalPremium"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "InvoicedPremium", false), excelFieldMapping.get("InvoicedPremium"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "Modifier", false), excelFieldMapping.get("Modifier"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "NoOfAttroney", false), excelFieldMapping.get("NoOfAttroney"), i);
					
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "IsFirmCarryingProfLiabilityIns", false), excelFieldMapping.get("IsFirmCarryingProfLiabilityIns"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "ExpiringCarrier", false), excelFieldMapping.get("ExpiringCarrier"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "LimitPross", false), excelFieldMapping.get("LimitPross"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "DeductiblePross", false), excelFieldMapping.get("DeductiblePross"), i);
					
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "IsPriorActDateFull", false), excelFieldMapping.get("IsPriorActDateFull"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "PriorActDatePross", false), excelFieldMapping.get("PriorActDatePross"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "FirmYear", false), excelFieldMapping.get("FirmYear"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "ProInsPremium", false), excelFieldMapping.get("ProInsPremium"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "PolicyExpirationDatePross", false), excelFieldMapping.get("PolicyExpirationDatePross"), i);
					
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "IsFirmPage", false), excelFieldMapping.get("IsFirmPage"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "IsAopPage", false), excelFieldMapping.get("IsAopPage"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "IsPracticePage", false), excelFieldMapping.get("IsPracticePage"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "IsCoveragePage", false), excelFieldMapping.get("IsCoveragePage"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "IsTaxPage", false), excelFieldMapping.get("IsTaxPage"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "IsEnvironmentalPage", false), excelFieldMapping.get("IsEnvironmentalPage"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "IsTitlePage", false), excelFieldMapping.get("IsTitlePage"), i);
					
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "IsWillsEstatePage", false), excelFieldMapping.get("IsWillsEstatePage"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "IsPlaintiffPage", false), excelFieldMapping.get("IsPlaintiffPage"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "IsCorporatePage", false), excelFieldMapping.get("IsCorporatePage"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "IsFinancialInsPage", false), excelFieldMapping.get("IsFinancialInsPage"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "IsRealEstatePage", false), excelFieldMapping.get("IsRealEstatePage"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "PolicyEffectiveDate", false),  excelFieldMapping.get("PolicyEffectiveDate") , i);
					
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "IsFirmPracticeInOtherState", false),  excelFieldMapping.get("IsFirmPracticeInOtherState") , i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "IsFirmHaveClientInEntertainmentInd", false),  excelFieldMapping.get("IsFirmHaveClientInEntertainmentInd") , i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "IsFirmHaveEquityIntGrtThan10", false),  excelFieldMapping.get("IsFirmHaveEquityIntGrtThan10") , i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "IsAttorneyDeclineForProfLiability", false),  excelFieldMapping.get("IsAttorneyDeclineForProfLiability") , i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "IsFirmProvidedLegalServiceToSecurity", false),  excelFieldMapping.get("IsFirmProvidedLegalServiceToSecurity") , i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "IsFirmProvidedLegalServiceToInvestment", false),  excelFieldMapping.get("IsFirmProvidedLegalServiceToInvestment") , i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "IsFirmProvidedLegalServiceToMoney", false),  excelFieldMapping.get("IsFirmProvidedLegalServiceToMoney") , i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "IsFirmProvidedLegalServiceToInvClient", false),  excelFieldMapping.get("IsFirmProvidedLegalServiceToInvClient") , i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "IsFirmHaveClientMoreThan25PercentOfBilling", false),  excelFieldMapping.get("IsFirmHaveClientMoreThan25PercentOfBilling") , i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "IsPersonnelBeSubOfAnyInvest", false),  excelFieldMapping.get("IsPersonnelBeSubOfAnyInvest") , i);
					
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "IsLawyerProfLiabClaimAgntAppl", false),  excelFieldMapping.get("IsLawyerProfLiabClaimAgntAppl") , i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "IsAnyActOmmBecomeClaimAgntFirm", false),  excelFieldMapping.get("IsAnyActOmmBecomeClaimAgntFirm") , i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "Comment", false),  excelFieldMapping.get("Comment") , i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "IsFirmMergedWithOtherFirm", false),  excelFieldMapping.get("IsFirmMergedWithOtherFirm") , i);
					
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "AOP_Percentage_1", false), excelFieldMapping.get("AOP_Percentage_1"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "AOP_Percentage_2", false), excelFieldMapping.get("AOP_Percentage_2"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "AOP_Percentage_3", false), excelFieldMapping.get("AOP_Percentage_3"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "AOP_Percentage_4", false), excelFieldMapping.get("AOP_Percentage_4"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "AOP_Percentage_5", false), excelFieldMapping.get("AOP_Percentage_5"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "AOP_Percentage_6", false), excelFieldMapping.get("AOP_Percentage_6"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "AOP_Percentage_7", false), excelFieldMapping.get("AOP_Percentage_7"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "AOP_Percentage_8", false), excelFieldMapping.get("AOP_Percentage_8"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "AOP_Percentage_9", false), excelFieldMapping.get("AOP_Percentage_9"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "AOP_Percentage_10", false), excelFieldMapping.get("AOP_Percentage_10"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "AOP_Percentage_11", false), excelFieldMapping.get("AOP_Percentage_11"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "AOP_Percentage_12", false), excelFieldMapping.get("AOP_Percentage_12"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "AOP_Percentage_13", false), excelFieldMapping.get("AOP_Percentage_13"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "AOP_Percentage_14", false), excelFieldMapping.get("AOP_Percentage_14"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "AOP_Percentage_15", false), excelFieldMapping.get("AOP_Percentage_15"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "AOP_Percentage_16", false), excelFieldMapping.get("AOP_Percentage_16"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "AOP_Percentage_17", false), excelFieldMapping.get("AOP_Percentage_17"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "AOP_Percentage_18", false), excelFieldMapping.get("AOP_Percentage_18"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "AOP_Percentage_19", false), excelFieldMapping.get("AOP_Percentage_19"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "AOP_Percentage_20", false), excelFieldMapping.get("AOP_Percentage_20"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "AOP_Percentage_21", false), excelFieldMapping.get("AOP_Percentage_21"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "AOP_Percentage_22", false), excelFieldMapping.get("AOP_Percentage_22"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "AOP_Percentage_23", false), excelFieldMapping.get("AOP_Percentage_23"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "AOP_Percentage_24", false), excelFieldMapping.get("AOP_Percentage_24"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "AOP_Percentage_25", false), excelFieldMapping.get("AOP_Percentage_25"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "OthersDesc", false), excelFieldMapping.get("OthersDesc"), i);
					
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "IsManualPremium", false), excelFieldMapping.get("IsManualPremium"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "SubProducerNumber", false), excelFieldMapping.get("SubProducerNumber"), i);
					
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "ResidentialPurchaseandSale", false), excelFieldMapping.get("ResidentialPurchaseandSale"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "CommercialPurchaseandSale", false), excelFieldMapping.get("CommercialPurchaseandSale"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "MortgagesandDeed", false), excelFieldMapping.get("MortgagesandDeed"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "CondominiumsCooperativesandHomeownerAssociations", false), excelFieldMapping.get("CondominiumsCooperativesandHomeownerAssociations"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "HomeownerAssociations", false), excelFieldMapping.get("HomeownerAssociations"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "LandlordTenant", false), excelFieldMapping.get("LandlordTenant"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "PropertyValuationRealEstateTaxAbatement", false), excelFieldMapping.get("PropertyValuationRealEstateTaxAbatement"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "Foreclosures", false), excelFieldMapping.get("Foreclosures"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "ConstructionWorkandMechanicsLiens", false), excelFieldMapping.get("ConstructionWorkandMechanicsLiens"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "LoanWorkouts", false), excelFieldMapping.get("LoanWorkouts"), i);
					
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "LandUseDevelopment", false), excelFieldMapping.get("LandUseDevelopment"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "EminentDomainint", false), excelFieldMapping.get("EminentDomainint"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "SpeculativeRealEstate", false), excelFieldMapping.get("SpeculativeRealEstate"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "LimitedPartnerships", false), excelFieldMapping.get("LimitedPartnerships"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "RealEstateSyndications", false), excelFieldMapping.get("RealEstateSyndications"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "RealEstateTrusts", false), excelFieldMapping.get("RealEstateTrusts"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "OtherCommercial", false), excelFieldMapping.get("OtherCommercial"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "MortgagesandDeeds", false), excelFieldMapping.get("MortgagesandDeeds"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "ForeclosureLoanWorkouts", false), excelFieldMapping.get("ForeclosureLoanWorkouts"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "OtherResidential", false), excelFieldMapping.get("OtherResidential"), i);
					
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "LoanWorkoutsResi", false), excelFieldMapping.get("LoanWorkoutsResi"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "IsApplInitiatedLawsuitForFirm", false), excelFieldMapping.get("IsApplInitiatedLawsuitForFirm"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "PolicyStatus", false), excelFieldMapping.get("PolicyStatus"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "TotalNumOfNonAttorneyStaff", false), excelFieldMapping.get("TotalNumOfNonAttorneyStaff"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "PolicyNumber", false), excelFieldMapping.get("PolicyNumber"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "RenewOfPolicyNumber", false), excelFieldMapping.get("RenewOfPolicyNumber"), i);					
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "AccountEmail", false), excelFieldMapping.get("AccountEmail"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "Is9010Policy", false), excelFieldMapping.get("Is9010Policy"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "IsAttorneyAddedDeleted", false), excelFieldMapping.get("IsAttorneyAddedDeleted"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "Reviewer", false), excelFieldMapping.get("Reviewer"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "PolicyExpirationDate", false), excelFieldMapping.get("PolicyExpirationDate"), i);
					
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "ContactPerson", false), excelFieldMapping.get("ContactPerson"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "Address1", false), excelFieldMapping.get("Address1"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "Address2", false), excelFieldMapping.get("Address2"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "County", false), excelFieldMapping.get("County"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "Modifier1", false), excelFieldMapping.get("Modifier1"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "Modifier2", false), excelFieldMapping.get("Modifier2"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "Modifier3", false), excelFieldMapping.get("Modifier3"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "TotalModifiers", false), excelFieldMapping.get("TotalModifiers"), i);
					
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "IsDollarDefense", false), excelFieldMapping.get("IsDollarDefense"), i);
					
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "Commercial", false), excelFieldMapping.get("Commercial"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "Medical_Malpractise", false), excelFieldMapping.get("Medical_Malpractise"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "Other_Personal_Injury", false), excelFieldMapping.get("Other_Personal_Injury"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "Products_Liability", false), excelFieldMapping.get("Products_Liability"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "Professional_Liability_Non_Medical", false), excelFieldMapping.get("Professional_Liability_Non_Medical"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "Toxic_Tort", false), excelFieldMapping.get("Toxic_Tort"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "Workers_Compensation", false), excelFieldMapping.get("Workers_Compensation"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "Other", false), excelFieldMapping.get("Other"), i);
					
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "City", false), excelFieldMapping.get("City"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "CreatedDate", false), excelFieldMapping.get("CreatedDate"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "ZipCode", false), excelFieldMapping.get("ZipCode"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "YearEndDate_1", false), excelFieldMapping.get("YearEndDate_1"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "Amount_1", false), excelFieldMapping.get("Amount_1"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "YearEndDate_2", false), excelFieldMapping.get("YearEndDate_2"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "Amount_2", false), excelFieldMapping.get("Amount_2"), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, "NumberOfRatedAttorney", false), excelFieldMapping.get("NumberOfRatedAttorney"), i);
					
					
				}
		 }
	}
	


	public void populateReportList(Context ctx, Sheet sheet ,String excelsheet)
			throws Exception {

        Map excelFieldMapping = (HashMap) ctx.get("EXCEL_FIELD_MAPPING");

		if(excelsheet.equals("ALL Issued Policies")){
			
			List issuedlist = (List) ctx.get("issuedlist");
			
			RowColumnBean rb_federalstate = (RowColumnBean) excelFieldMapping.get(Constants.AccountName);
			for(int i = rb_federalstate.getRownum() + issuedlist.size(); i< rb_federalstate.getRownum() + 2999; i++){
				Row row = null;
				if(sheet.getRow(i) != null)
				{
					row =  sheet.getRow(i);  
					sheet.removeRow(row);
				}
	        }			
			
			for (int i = 0; i < issuedlist.size(); i++) {
				
				Map map = (HashMap) issuedlist.get(i);
				ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.AccountName, false), excelFieldMapping.get(Constants.AccountName), i);	
				ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.StateDesc, false), excelFieldMapping.get(Constants.StateDesc), i);
				ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.PolicyEffectiveDate, false), excelFieldMapping.get(Constants.PolicyEffectiveDate), i);
				
				ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.Amount, false), excelFieldMapping.get(Constants.Amount), i);
				ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.TotalPremium, false), excelFieldMapping.get(Constants.TotalPremium), i);
			
				ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.PolicyStatus, false), excelFieldMapping.get(Constants.PolicyStatus), i);
				ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.RenewOfPolicyNumber, false), excelFieldMapping.get(Constants.RenewOfPolicyNumber), i);
				
			}
		}
		else
		{
        				
			if("ExportAllReport".equals(excelsheet))
			{
				List statedatelistAll = (List) ctx.get("statedatelist");
				
				RowColumnBean rb_federalstateAll = (RowColumnBean) excelFieldMapping.get(Constants.StateNameAllReportMain);
				
				for(int i = rb_federalstateAll.getRownum() + statedatelistAll.size(); i< rb_federalstateAll.getRownum() + 799; i++){
					Row row = null;
					if(sheet.getRow(i) != null)
					{
						row =  sheet.getRow(i);  
						sheet.removeRow(row);
					}
		        }
				
			
				if(statedatelistAll.size() <= 0)
				{
					int rowtoshiftup_bldstate = -801 + statedatelistAll.size();				
					sheet.shiftRows(rb_federalstateAll.getRownum() + 801, sheet.getLastRowNum(), rowtoshiftup_bldstate);
				}
				else{
					int rowtoshiftup_bldstate = -799 + statedatelistAll.size();
					sheet.shiftRows(rb_federalstateAll.getRownum() + 799, sheet.getLastRowNum(), rowtoshiftup_bldstate);
				
				}
				
				
				for (int i = 0; i < statedatelistAll.size(); i++) 
				{
					Map map = (HashMap) statedatelistAll.get(i);

					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.StateName, false), excelFieldMapping.get(Constants.StateNameAllReportMain), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.TotalApps, false), excelFieldMapping.get(Constants.TotalAppsStateAllReportMain), i);

					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.UnderReviewNew, false), excelFieldMapping.get(Constants.UnderReviewNewAllReportMain), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.Declined, false), excelFieldMapping.get(Constants.DeclinedAllReportMain), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.QuotedNew, false), excelFieldMapping.get(Constants.QuotedNewAllReportMain), i);

					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.Issued, false), excelFieldMapping.get(Constants.IssuedAllReportMain), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.HitRatio, false), excelFieldMapping.get(Constants.HitRatioAllReportMain), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.Pending, false), excelFieldMapping.get(Constants.PendingAllReportMain), i);

					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.Rejected, false), excelFieldMapping.get(Constants.RejectedAllReportMain), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.PremiumFromIssued, false),excelFieldMapping.get(Constants.PremiumFromIssuedAllReportMain),i);
				
					
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.StateName, false), excelFieldMapping.get(Constants.StateNameSMain), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.AllPolicy, false), excelFieldMapping.get(Constants.AllPolicyStateMain), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.UnderReview, false), excelFieldMapping.get(Constants.UnderReviewStateMain), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.Declined, false), excelFieldMapping.get(Constants.DeclinedStateMain), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.Issued, false), excelFieldMapping.get(Constants.IssuedStateMain), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.Rejected, false), excelFieldMapping.get(Constants.RejectedStateMain), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.IssuedWithPremium, false), excelFieldMapping.get(Constants.IssuedWithPremiumStateMain), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.PremiumFromIssued, false), excelFieldMapping.get(Constants.TotalPremiumFromIssuedStateMain), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.Accepted, false), excelFieldMapping.get(Constants.AcceptedStateMain), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.Quoted, false), excelFieldMapping.get(Constants.QuotedStateMain), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.NewQQ, false), excelFieldMapping.get(Constants.NewQQStateMain), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.NewFQ, false), excelFieldMapping.get(Constants.NewFQStateMain), i);
				
				
				}

				
				List effectivedaterenewallist = (List) ctx.get("effectivedaterenewallist");
			    
			    RowColumnBean rb_federaleffrenewal = (RowColumnBean) excelFieldMapping.get(Constants.MonthOfPolicyEffAllReportRenewal);

				for(int i = rb_federaleffrenewal.getRownum() + effectivedaterenewallist.size(); i< rb_federaleffrenewal.getRownum() + 799; i++){
					Row row = null;
					if(sheet.getRow(i) != null)
					{
						row =  sheet.getRow(i);  
						sheet.removeRow(row);
					}
				}
			
				if(effectivedaterenewallist.size() <= 0)
				{
					int rowtoshiftup_bldeff = -801 + effectivedaterenewallist.size();
					sheet.shiftRows(rb_federaleffrenewal.getRownum() + 801, sheet.getLastRowNum(), rowtoshiftup_bldeff);
				}
				else
				{
					int rowtoshiftup_bldeff = -799 + effectivedaterenewallist.size();
					sheet.shiftRows(rb_federaleffrenewal.getRownum() + 799, sheet.getLastRowNum(), rowtoshiftup_bldeff);				
				}
				
				for (int i = 0; i < effectivedaterenewallist.size(); i++)
				{
					Map map = (HashMap) effectivedaterenewallist.get(i);
					
					if (map.get("ExDatesAvaialble") == null) {
						map.put("ExDatesAvaialble", 0);
					}
					if (map.get("BudgetForecastforApps") == null) {
						map.put("BudgetForecastforApps", 0);
					}

					if (map.get("PremiumForecast") == null) {
						map.put("PremiumForecast", 0);
					}

					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.MonthOfPolicyEff, false), excelFieldMapping.get(Constants.MonthOfPolicyEffAllReportRenewal), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.ExDatesAvaialble, false), excelFieldMapping.get(Constants.ExDatesAvaialbleAllReportRenewal), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.BudgetForecastforApps, false),excelFieldMapping.get(Constants.BudgetForecastforAppsAllReportRenewal),i);		
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.TotalApps, false), excelFieldMapping.get(Constants.TotalAppsEffAllReportRenewal), i);		
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.UnderReviewNew, false), excelFieldMapping.get(Constants.UnderReviewNewEffAllReportRenewal), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.Declined, false), excelFieldMapping.get(Constants.DeclinedEffAllReportRenewal), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.QuotedNew, false), excelFieldMapping.get(Constants.QuotedNewEffAllReportRenewal), i);		
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.Issued, false), excelFieldMapping.get(Constants.IssuedEffAllReportRenewal), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.HitRatio, false), excelFieldMapping.get(Constants.HitRatioEffAllReportRenewal), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.Pending, false), excelFieldMapping.get(Constants.PendingEffAllReportRenewal), i);		
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.Rejected, false), excelFieldMapping.get(Constants.RejectedEffAllReportRenewal), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.PremiumFromIssuedRen, false),excelFieldMapping.get(Constants.PremiumFromIssuedEffAllReportRenewal),i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.TotalExpiringPremium, false), excelFieldMapping.get(Constants.PremiumExpiringEffAllReportRenewal), i);
																			
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.MonthNo, false), excelFieldMapping.get(Constants.MonthNoRenewal), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.YearNo, false), excelFieldMapping.get(Constants.YearNoRenewal), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.AllPolicy, false), excelFieldMapping.get(Constants.AllPolicyEffecRenewal), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.UnderReview, false), excelFieldMapping.get(Constants.UnderReviewEffecRenewal), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.Declined, false), excelFieldMapping.get(Constants.DeclinedEffecRenewal), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.Issued, false), excelFieldMapping.get(Constants.IssuedEffecRenewal), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.Rejected, false), excelFieldMapping.get(Constants.RejectedEffecRenewal), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.IssuedWithPremium, false), excelFieldMapping.get(Constants.IssuedWithPremiumEffecRenewal), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.PremiumFromIssuedRen, false), excelFieldMapping.get(Constants.TotalPremiumFromIssuedEffecRenewal), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.Accepted, false), excelFieldMapping.get(Constants.AcceptedEffecRenewal), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.Quoted, false), excelFieldMapping.get(Constants.QuotedEffecRenewal), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.NewQQ, false), excelFieldMapping.get(Constants.NewQQEffecRenewal), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.NewFQ, false), excelFieldMapping.get(Constants.NewFQEffecRenewal), i);
										
				}

			}
			else
			{
				List statedatelist = (List) ctx.get("statedatelist");
				
				RowColumnBean rb_federalstate = (RowColumnBean) excelFieldMapping.get(Constants.StateNameAllReport);
				
				for(int i = rb_federalstate.getRownum() + statedatelist.size(); i< rb_federalstate.getRownum() + 799; i++){
					Row row = null;
					if(sheet.getRow(i) != null)
					{
						row =  sheet.getRow(i);  
						sheet.removeRow(row);
					}
		        }
				
			
				if(statedatelist.size() <= 0)
				{
					int rowtoshiftup_bldstate = -801+ statedatelist.size();				
					sheet.shiftRows(rb_federalstate.getRownum() + 801, sheet.getLastRowNum(), rowtoshiftup_bldstate);
				}
				else{
					int rowtoshiftup_bldstate = -799 + statedatelist.size();
					sheet.shiftRows(rb_federalstate.getRownum() + 799, sheet.getLastRowNum(), rowtoshiftup_bldstate);
				
				}
				

				
				
				for (int i = 0; i < statedatelist.size(); i++) 
				{
					Map map = (HashMap) statedatelist.get(i);

					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.StateName, false), excelFieldMapping.get(Constants.StateNameAllReport), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.TotalApps, false), excelFieldMapping.get(Constants.TotalAppsStateAllReport), i);

					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.UnderReviewNew, false), excelFieldMapping.get(Constants.UnderReviewNewAllReport), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.Declined, false), excelFieldMapping.get(Constants.DeclinedAllReport), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.QuotedNew, false), excelFieldMapping.get(Constants.QuotedNewAllReport), i);

					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.Issued, false), excelFieldMapping.get(Constants.IssuedAllReport), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.HitRatio, false), excelFieldMapping.get(Constants.HitRatioAllReport), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.Pending, false), excelFieldMapping.get(Constants.PendingAllReport), i);

					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.Rejected, false), excelFieldMapping.get(Constants.RejectedAllReport), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.PremiumFromIssued, false),excelFieldMapping.get(Constants.PremiumFromIssuedAllReport),i);
				
					
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.StateName, false), excelFieldMapping.get(Constants.StateNameS), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.AllPolicy, false), excelFieldMapping.get(Constants.AllPolicyState), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.UnderReview, false), excelFieldMapping.get(Constants.UnderReviewState), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.Declined, false), excelFieldMapping.get(Constants.DeclinedState), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.Issued, false), excelFieldMapping.get(Constants.IssuedState), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.Rejected, false), excelFieldMapping.get(Constants.RejectedState), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.IssuedWithPremium, false), excelFieldMapping.get(Constants.IssuedWithPremiumState), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.PremiumFromIssued, false), excelFieldMapping.get(Constants.TotalPremiumFromIssuedState), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.Accepted, false), excelFieldMapping.get(Constants.AcceptedState), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.Quoted, false), excelFieldMapping.get(Constants.QuotedState), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.NewQQ, false), excelFieldMapping.get(Constants.NewQQState), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.NewFQ, false), excelFieldMapping.get(Constants.NewFQState), i);
				
				
				}

			}
					   							
			if("ExportAllReport".equals(excelsheet))
			{
				List effectivedatelist = (List) ctx.get("effectivedatelist");			    
			    RowColumnBean rb_federaleff = (RowColumnBean) excelFieldMapping.get(Constants.MonthOfPolicyEffAllReport);

				for(int i = rb_federaleff.getRownum() + effectivedatelist.size(); i< rb_federaleff.getRownum() + 799; i++){
					Row row = null;
					if(sheet.getRow(i) != null)
					{
						row =  sheet.getRow(i);  
						sheet.removeRow(row);
					}		        	
		        }
			
				if(effectivedatelist.size() <= 0)
				{
					int rowtoshiftup_bldeff = -801 + effectivedatelist.size();
					sheet.shiftRows(rb_federaleff.getRownum() + 801, sheet.getLastRowNum(), rowtoshiftup_bldeff);
				}
				else
				{
					int rowtoshiftup_bldeff = -799 + effectivedatelist.size();
					sheet.shiftRows(rb_federaleff.getRownum() + 799, sheet.getLastRowNum(), rowtoshiftup_bldeff);				
				}
				
				for (int i = 0; i < effectivedatelist.size(); i++)
				{
					Map map = (HashMap) effectivedatelist.get(i);
					
					if (map.get("ExDatesAvaialble") == null) {
						map.put("ExDatesAvaialble", 0);
					}
					if (map.get("BudgetForecastforApps") == null) {
						map.put("BudgetForecastforApps", 0);
					}

					if (map.get("PremiumForecast") == null) {
						map.put("PremiumForecast", 0);
					}

					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.MonthOfPolicyEff, false), excelFieldMapping.get(Constants.MonthOfPolicyEffAllReport), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.ExDatesAvaialble, false), excelFieldMapping.get(Constants.ExDatesAvaialbleAllReport), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.BudgetForecastforApps, false),excelFieldMapping.get(Constants.BudgetForecastforAppsAllReport),i);		
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.TotalApps, false), excelFieldMapping.get(Constants.TotalAppsEffAllReport), i);		
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.UnderReviewNew, false), excelFieldMapping.get(Constants.UnderReviewNewEffAllReport), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.Declined, false), excelFieldMapping.get(Constants.DeclinedEffAllReport), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.QuotedNew, false), excelFieldMapping.get(Constants.QuotedNewEffAllReport), i);		
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.Issued, false), excelFieldMapping.get(Constants.IssuedEffAllReport), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.HitRatio, false), excelFieldMapping.get(Constants.HitRatioEffAllReport), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.Pending, false), excelFieldMapping.get(Constants.PendingEffAllReport), i);		
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.Rejected, false), excelFieldMapping.get(Constants.RejectedEffAllReport), i);
					
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.PremiumFromIssuedNB, false),excelFieldMapping.get(Constants.PremiumFromIssuedEffAllReportNB),i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.PremiumFromIssuedRen, false),excelFieldMapping.get(Constants.PremiumFromIssuedEffAllReportRen),i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.PremiumFromIssued, false),excelFieldMapping.get(Constants.PremiumFromIssuedEffAllMainReport),i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.PremiumForecast, false), excelFieldMapping.get(Constants.PremiumForecastAllMainReport), i);
																			
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.MonthNo, false), excelFieldMapping.get(Constants.MonthNo), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.YearNo, false), excelFieldMapping.get(Constants.YearNo), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.AllPolicy, false), excelFieldMapping.get(Constants.AllPolicyEffec), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.UnderReview, false), excelFieldMapping.get(Constants.UnderReviewEffec), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.Declined, false), excelFieldMapping.get(Constants.DeclinedEffec), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.Issued, false), excelFieldMapping.get(Constants.IssuedEffec), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.Rejected, false), excelFieldMapping.get(Constants.RejectedEffec), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.IssuedWithPremium, false), excelFieldMapping.get(Constants.IssuedWithPremiumEffec), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.PremiumFromIssued, false), excelFieldMapping.get(Constants.TotalPremiumFromIssuedEffec), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.Accepted, false), excelFieldMapping.get(Constants.AcceptedEffec), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.Quoted, false), excelFieldMapping.get(Constants.QuotedEffec), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.NewQQ, false), excelFieldMapping.get(Constants.NewQQEffec), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.NewFQ, false), excelFieldMapping.get(Constants.NewFQEffec), i);
										
				}

			}
			else
			{
				List effectivedatelist = (List) ctx.get("effectivedatelist");			    
			    RowColumnBean rb_federaleff = (RowColumnBean) excelFieldMapping.get(Constants.MonthOfPolicyEffAllReport);

				for(int i = rb_federaleff.getRownum() + effectivedatelist.size(); i< rb_federaleff.getRownum() + 799; i++){
					Row row = null;
					if(sheet.getRow(i) != null)
					{
						row =  sheet.getRow(i);  
						sheet.removeRow(row);
					}		        	
		        }
			
				if(effectivedatelist.size() <= 0)
				{
					int rowtoshiftup_bldeff = -801 + effectivedatelist.size();
					sheet.shiftRows(rb_federaleff.getRownum() + 801, sheet.getLastRowNum(), rowtoshiftup_bldeff);
				}
				else
				{
					int rowtoshiftup_bldeff = -799 + effectivedatelist.size();
					sheet.shiftRows(rb_federaleff.getRownum() + 799, sheet.getLastRowNum(), rowtoshiftup_bldeff);				
				}
				
				for (int i = 0; i < effectivedatelist.size(); i++)
				{
					Map map = (HashMap) effectivedatelist.get(i);
					
					if (map.get("ExDatesAvaialble") == null) {
						map.put("ExDatesAvaialble", 0);
					}
					if (map.get("BudgetForecastforApps") == null) {
						map.put("BudgetForecastforApps", 0);
					}

					if (map.get("PremiumForecast") == null) {
						map.put("PremiumForecast", 0);
					}

					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.MonthOfPolicyEff, false), excelFieldMapping.get(Constants.MonthOfPolicyEffAllReport), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.ExDatesAvaialble, false), excelFieldMapping.get(Constants.ExDatesAvaialbleAllReport), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.BudgetForecastforApps, false),excelFieldMapping.get(Constants.BudgetForecastforAppsAllReport),i);		
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.TotalApps, false), excelFieldMapping.get(Constants.TotalAppsEffAllReport), i);		
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.UnderReviewNew, false), excelFieldMapping.get(Constants.UnderReviewNewEffAllReport), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.Declined, false), excelFieldMapping.get(Constants.DeclinedEffAllReport), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.QuotedNew, false), excelFieldMapping.get(Constants.QuotedNewEffAllReport), i);		
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.Issued, false), excelFieldMapping.get(Constants.IssuedEffAllReport), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.HitRatio, false), excelFieldMapping.get(Constants.HitRatioEffAllReport), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.Pending, false), excelFieldMapping.get(Constants.PendingEffAllReport), i);		
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.Rejected, false), excelFieldMapping.get(Constants.RejectedEffAllReport), i);
					
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.PremiumFromIssuedNB, false),excelFieldMapping.get(Constants.PremiumFromIssuedEffAllReportNB),i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.PremiumFromIssuedRen, false),excelFieldMapping.get(Constants.PremiumFromIssuedEffAllReportRen),i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.PremiumFromIssued, false),excelFieldMapping.get(Constants.PremiumFromIssuedEffAllReport),i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.PremiumForecast, false), excelFieldMapping.get(Constants.PremiumForecastAllReport), i);
																			
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.MonthNo, false), excelFieldMapping.get(Constants.MonthNo), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.YearNo, false), excelFieldMapping.get(Constants.YearNo), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.AllPolicy, false), excelFieldMapping.get(Constants.AllPolicyEffec), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.UnderReview, false), excelFieldMapping.get(Constants.UnderReviewEffec), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.Declined, false), excelFieldMapping.get(Constants.DeclinedEffec), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.Issued, false), excelFieldMapping.get(Constants.IssuedEffec), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.Rejected, false), excelFieldMapping.get(Constants.RejectedEffec), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.IssuedWithPremium, false), excelFieldMapping.get(Constants.IssuedWithPremiumEffec), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.PremiumFromIssued, false), excelFieldMapping.get(Constants.TotalPremiumFromIssuedEffec), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.Accepted, false), excelFieldMapping.get(Constants.AcceptedEffec), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.Quoted, false), excelFieldMapping.get(Constants.QuotedEffec), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.NewQQ, false), excelFieldMapping.get(Constants.NewQQEffec), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.NewFQ, false), excelFieldMapping.get(Constants.NewFQEffec), i);
										
				}

			}
			   					
			
				
				
			if(excelsheet.equals("ExportAllReport")){
				
				
				List lastweeklist = (List) ctx.get("lastweeklist");
				for (int i = 0; i < lastweeklist.size(); i++) {
					Map map = (HashMap) lastweeklist.get(i);

					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.MonthofAppcrtDate, false), excelFieldMapping.get(Constants.MonthofAppcrtDateAllReport), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.TotalApps, false), excelFieldMapping.get(Constants.TotalAppsAllReport), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.NewQQ, false), excelFieldMapping.get(Constants.NewQQAllReport), i);
				
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.AllPolicy, false), excelFieldMapping.get(Constants.AllPolicyLastWeek), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.UnderReview, false), excelFieldMapping.get(Constants.UnderReviewLastWeek), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.Declined, false), excelFieldMapping.get(Constants.DeclinedLastWeek), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.Issued, false), excelFieldMapping.get(Constants.IssuedLastWeek), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.Rejected, false), excelFieldMapping.get(Constants.RejectedLastWeek), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.IssuedWithPremium, false), excelFieldMapping.get(Constants.IssuedWithPremiumLastWeek), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.TotalPremiumFromIssued, false), excelFieldMapping.get(Constants.TotalPremiumFromIssuedLastWeek), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.Accepted, false), excelFieldMapping.get(Constants.AcceptedLastWeek), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.Quoted, false), excelFieldMapping.get(Constants.QuotedLastWeek), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.NewQQ, false), excelFieldMapping.get(Constants.NewQQLastWeek), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.NewFQ, false), excelFieldMapping.get(Constants.NewFQLastWeek), i);
				
				
				
				}
			}	
				
				List creativedatelist = (List) ctx.get("creativedatelist");
				RowColumnBean rb_federal = (RowColumnBean) excelFieldMapping.get(Constants.MonthofAppcrtCreatedDateAllReport);
				for(int i = rb_federal.getRownum() + creativedatelist.size(); i< rb_federal.getRownum() + 799; i++){
					Row row = null;
					if(sheet.getRow(i) != null)
					{
						row =  sheet.getRow(i);  
						sheet.removeRow(row);
					}
		        }
			
				
				if(creativedatelist.size() <= 0)
				{
					int rowtoshiftup_bld = -801+ creativedatelist.size();
					sheet.shiftRows(rb_federal.getRownum() +801 , sheet.getLastRowNum(), rowtoshiftup_bld);
				}
				else{
					int rowtoshiftup_bld = -799+ creativedatelist.size();
					sheet.shiftRows(rb_federal.getRownum() +799 , sheet.getLastRowNum(), rowtoshiftup_bld);
				
				}
				
//				int rowtoshiftup_bld = -799+ creativedatelist.size();
//				sheet.shiftRows(rb_federal.getRownum() +799 , sheet.getLastRowNum(), rowtoshiftup_bld);
				
				
				
				for (int i = 0; i < creativedatelist.size(); i++) {
					Map map = (HashMap) creativedatelist.get(i);
					
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.MonthofAppcrtDate, false), excelFieldMapping.get(Constants.MonthofAppcrtCreatedDateAllReport), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.TotalApps, false), excelFieldMapping.get(Constants.TotalCreatedAppsAllReport), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.NewQQ, false), excelFieldMapping.get(Constants.CreatedNewQQAllReport), i);
				
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.CreatedMonth, false), excelFieldMapping.get(Constants.CreatedMonth), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.AllPolicy, false), excelFieldMapping.get(Constants.AllPolicyCreated), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.UnderReview, false), excelFieldMapping.get(Constants.UnderReviewCreated), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.Declined, false), excelFieldMapping.get(Constants.DeclinedCreated), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.Issued, false), excelFieldMapping.get(Constants.IssuedCreated), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.Rejected, false), excelFieldMapping.get(Constants.RejectedCreated), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.IssuedWithPremium, false), excelFieldMapping.get(Constants.IssuedWithPremiumCreated), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.TotalPremiumFromIssued, false), excelFieldMapping.get(Constants.TotalPremiumFromIssuedCreated), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.Accepted, false), excelFieldMapping.get(Constants.AcceptedCreated), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.Quoted, false), excelFieldMapping.get(Constants.QuotedCreated), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.NewQQ, false), excelFieldMapping.get(Constants.NewQQCreated), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.NewFQ, false), excelFieldMapping.get(Constants.NewFQCreated), i);
					ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.CreatedYear, false), excelFieldMapping.get(Constants.CreatedYear), i);
				
				} 
				
		}
	}
	public static void  main(String[] args) throws Exception
	{  
		
		Workbook wb;			
		String file = SystemProperties.getInstance().getString("xml.basedir") + "excel//ReportTemplate.xls";
		
		FileInputStream input = new FileInputStream(file);
		wb = new HSSFWorkbook(input);
		
		Context ctx = new Context();
		String projectName = "AccountantIns";
		String resourcePath = SystemProperties.getInstance().getString("xml.basedir") + projectName + "//ibatis//maps//Account.xml";
		SqlResources.load(projectName, resourcePath);
		
		ctx.setProject(projectName);
		Map getSheetDatabasefield = new ExcelDatabaseMapping().getCellInfo(ctx, wb.getSheet("ExportAllReport"));        
		ctx.put("EXCEL_FIELD_MAPPING", getSheetDatabasefield);
		
		//new WeeklyReport().getDataForWeeklyReport(ctx);
		 FileOutputStream out = new FileOutputStream("D://monika//Report.xls");
			wb.write(out);
			out.flush();
			out.close();	
			input.close();	
		
		
	}
}

