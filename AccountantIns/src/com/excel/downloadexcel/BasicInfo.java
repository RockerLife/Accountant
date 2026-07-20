package com.excel.downloadexcel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.excel.oldpim.ExcelUtility;
import com.manage.managemetadata.functions.commonfunctions.Math;
import com.ormapping.ibatis.SqlResources;
import com.util.Context;


public class BasicInfo {
	
	public void getDataForBasicInfo(Context ctx) throws Exception
	{
		Object objAccount =SqlResources.getSqlMapProcessor(ctx).findByKey("Account.findByKey", ctx);	
		ctx.put(Constants.ACCOUNT_FREEFORM_01, objAccount);
		
		Object objAddress =SqlResources.getSqlMapProcessor(ctx).findByKey("SqlStmts.BasicInfoviewgetAddressdetails", ctx);	
		ctx.put(Constants.ADDRESS_FREEFORM_01, objAddress);
		
		Object objPolicy =SqlResources.getSqlMapProcessor(ctx).findByKey("Policy.findByKey", ctx);	
		ctx.put(Constants.POLICY_FREEFORM_01, objPolicy);
		
		Object objFirm =SqlResources.getSqlMapProcessor(ctx).findByKey("Firm.findByKey", ctx);	
		ctx.put(Constants.FIRM_FREEFORM_01, objFirm);
		
		if(ctx.get("manualrating") == null || (ctx.get("manualrating")!= null && "N".equals(ctx.get("manualrating"))))
		{
			Object objPolicyCoverage = null;
			objPolicyCoverage =SqlResources.getSqlMapProcessor(ctx).findByKey("SqlStmts.UserStatementsaveAccountstmtsCoveragePolicyforexcel", ctx);
			
			if(objPolicyCoverage == null)
			objPolicyCoverage =SqlResources.getSqlMapProcessor(ctx).findByKey("SqlStmts.BasicInfoviewCoveragePolicy", ctx);
			
			ctx.put(Constants.POLICYCOVERAGE_FREEFORM_01, objPolicyCoverage);
		}
		else
		{
			Map mapPolicyCoverage = new HashMap();
			mapPolicyCoverage.put(Constants.IsClaimExpensesType, ctx.get(Constants.IsClaimExpensesType));
			mapPolicyCoverage.put(Constants.IsClaimOptionType, ctx.get(Constants.IsClaimOptionType));
			
			Object objLimit =SqlResources.getSqlMapProcessor(ctx).findByKey("SqlStmts.UserStatementsaveAccountstmtspopulateLimit", ctx);
			Object objDeductible =SqlResources.getSqlMapProcessor(ctx).findByKey("SqlStmts.UserStatementsaveAccountstmtspopulateDeductible", ctx);
			
			if(objLimit != null && objLimit instanceof Map)
			{
				mapPolicyCoverage.put(Constants.AggregateLimit, ((Map)objLimit).get(Constants.AggregateLimit));
				mapPolicyCoverage.put(Constants.OccuranceLimit, ((Map)objLimit).get(Constants.OccuranceLimit));
			}
			if(objDeductible != null && objDeductible instanceof Map)
			{
				mapPolicyCoverage.put(Constants.DeductibleAmount, ((Map)objDeductible).get(Constants.DeductibleAmount));
			}
			
			mapPolicyCoverage.put(Constants.Premium, ctx.get("ManualPremium"));
			ctx.put(Constants.POLICYCOVERAGE_FREEFORM_01, mapPolicyCoverage);
		}
		
		
		
		Object objProfessionalLiabilityInsDetail =SqlResources.getSqlMapProcessor(ctx).findByKey("ProfessionalLiabilityInsDetail.findByKey", ctx);
		ctx.put(Constants.PROFESSIONALLIABILITYINS_FREEFORM_01, objProfessionalLiabilityInsDetail);		
		
		Object objLimitDeductibleProfessionalLiabilityInsDetail = SqlResources.getSqlMapProcessor(ctx).findByKey("SqlStmts.UserStatementsaveAccountstmtsgeLimitDeductibleProfessionalLiabilityInsDetail", ctx);
		ctx.put(Constants.LIMITDEDUCTIBLEPROFESSIONALLIABILITYINS_FREEFORM_01, objLimitDeductibleProfessionalLiabilityInsDetail);
		
		Object listClaimIncidentSupp =SqlResources.getSqlMapProcessor(ctx).select("SqlStmts.BasicInfoviewgetClaimIncidentSupp", ctx);
		ctx.put(Constants.BASICINFO_LIST_04, listClaimIncidentSupp);
		
		Object listPersonnelAffiliation =SqlResources.getSqlMapProcessor(ctx).select("SqlStmts.BasicInfoviewmomfirmaffiliates", ctx);
		ctx.put(Constants.BASICINFO_LIST_01, listPersonnelAffiliation);
		
		Object listGrossRevenueForFirm =SqlResources.getSqlMapProcessor(ctx).select("SqlStmts.BasicInfoviewmomgrossrevenue", ctx);
		ctx.put(Constants.BASICINFO_LIST_03, listGrossRevenueForFirm);
		
		Object listPublicPrivateOfferingSupp =SqlResources.getSqlMapProcessor(ctx).select("SqlStmts.BasicInfoviewListPublicPrivateOfferingSupp", ctx);
		ctx.put(Constants.BASICINFO_LIST_06, listPublicPrivateOfferingSupp);
		
		ctx.put("PolicyKey", Integer.parseInt(ctx.get("PolicyKey").toString()));
		ctx.put("VersionSequence", Integer.parseInt(ctx.get("VersionSequence").toString()));
//		Object listAOP =SqlResources.getSqlMapProcessor(ctx).select("SqlStmts.UserStatementsaveAccountstmtspopulateAOPFields", ctx);
		Object listAOP = SqlResources.getSqlMapProcessor(ctx).select("AreaOfPractice.GETAOPFIELDS", ctx);
		ctx.put("PolicyKey",ctx.get("PolicyKey").toString());
		ctx.put("VersionSequence", ctx.get("VersionSequence").toString());
		
		ctx.put(Constants.BASICINFO_LIST_AOP, listAOP);
		
		//new General().getDataForGeneral(ctx);
			
	}	
	
	public void populateBasicInfoData(Context ctx, Workbook wb) throws Exception
	{
		Sheet sheet = wb.getSheet("Basic Info");
        CellStyle string= wb.createCellStyle();
        string.setDataFormat((short) 38);
        CellStyle numeric= wb.createCellStyle();
        numeric.setDataFormat((short) 38);
        
        populateFirmInfo(ctx, sheet);
        populatePeronnalAffiliation(ctx, sheet);
        populateGrossRevenue(ctx, sheet);
        populateAOPList(ctx, sheet);
        populateClaimList(ctx, sheet);
        populatePublicPrivateOfferingList(ctx, sheet);
	}
	
	public void populateFirmInfo(Context ctx, Sheet sheet) throws Exception
	{
		Object objFirm = ctx.get(Constants.FIRM_FREEFORM_01);
		Map firmMap = new HashMap();
		if(objFirm != null)
			firmMap = (Map)objFirm;
		
		Object objAccount = ctx.get(Constants.ACCOUNT_FREEFORM_01);
		Map accountMap = new HashMap();
		if(objAccount != null)
			accountMap = (Map)objAccount;
		
		Object objAddress = ctx.get(Constants.ADDRESS_FREEFORM_01);
		Map addressMap = new HashMap();
		if(objAddress != null)
			addressMap = (Map)objAddress;
		
		Object objPolicy = ctx.get(Constants.POLICY_FREEFORM_01);
		Map policyMap = new HashMap();
		if(objPolicy != null)
			policyMap = (Map)objPolicy;		

		Object objPolicyCoverage = ctx.get(Constants.POLICYCOVERAGE_FREEFORM_01);
		Map policyCoverageMap = new HashMap();
		if(objPolicyCoverage != null)
			policyCoverageMap = (Map)objPolicyCoverage;
		
//		if(firmMap == null && accountMap == null && addressMap == null && policyMap == null && policyCoverageMap ==null)
//			return;

		Map excelFieldMapping = (HashMap) ctx.get("EXCEL_FIELD_MAPPING");
		
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(policyMap, Constants.QuoteNumber, false), excelFieldMapping.get(Constants.QuoteNumber), 0);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(policyMap, Constants.PolicyNumber, false), excelFieldMapping.get(Constants.PolicyNumber), 0);
		
		ExcelUtility.setCellValue(sheet, ExcelUtility.getFormattedDateFromObject(policyMap.get(Constants.PolicyEffectiveDate)), excelFieldMapping.get(Constants.PolicyEffectiveDate), 0);
		
		String str = ExcelUtility.getValueFromMap(policyMap, Constants.IsFullQuote, true);
		if("Yes".equals(str))
			str = "Full Quote";
		else if("No".equals(str))
			str = "Quick Quote";		
		ExcelUtility.setCellValue(sheet, str, excelFieldMapping.get(Constants.IsFullQuote), 0);
		
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(accountMap, Constants.AccountName, false), excelFieldMapping.get(Constants.AccountName), 0);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(accountMap, Constants.AccountEmail, false), excelFieldMapping.get(Constants.AccountEmail), 0);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(accountMap, Constants.Website, false), excelFieldMapping.get(Constants.Website), 0);
		
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(firmMap, Constants.ContactPerson, false), excelFieldMapping.get(Constants.ContactPerson), 0);
		
		String ContactPhone = ExcelUtility.getValueFromMap(firmMap, Constants.ContactPhone, false);
		String ContactPhoneExt = ExcelUtility.getValueFromMap(firmMap, Constants.ContactPhoneExt, false);
		if(!"".equals(ContactPhoneExt))
			ContactPhone = ContactPhone + " Ext " + ContactPhoneExt;
		ExcelUtility.setCellValue(sheet, ContactPhone, excelFieldMapping.get(Constants.ContactPhone), 0);
		
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(addressMap, Constants.Address1, false) + "  " + ExcelUtility.getValueFromMap(addressMap, Constants.Address2, false), excelFieldMapping.get(Constants.Address), 0);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(addressMap, Constants.City, false), excelFieldMapping.get(Constants.City), 0);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(addressMap, Constants.StateDesc, false), excelFieldMapping.get(Constants.StateDesc), 0);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(addressMap, Constants.StateDesc, false), excelFieldMapping.get(Constants.State), 0);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(addressMap, Constants.CountyDesc, false), excelFieldMapping.get(Constants.CountyDesc), 0);
		
		String Zip = ExcelUtility.getValueFromMap(addressMap, Constants.Zip, false);
		String Zip4 = ExcelUtility.getValueFromMap(addressMap, Constants.Zip4, false);
		if(!"".equals(Zip4))
			Zip = Zip +"-" + Zip4;
		
		String WorkPhone = ExcelUtility.getValueFromMap(addressMap, Constants.WorkPhone, false);
		String WorkExt = ExcelUtility.getValueFromMap(addressMap, Constants.WorkExt, false);
		if(!"".equals(WorkExt))
			WorkPhone = WorkPhone + " Ext " + WorkExt;
		
		ExcelUtility.setCellValue(sheet,  Zip, excelFieldMapping.get(Constants.Zip), 0);
		ExcelUtility.setCellValue(sheet,  WorkPhone, excelFieldMapping.get(Constants.WorkPhone), 0);
				
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(firmMap, Constants.IsFirmsRevenueMorethan25Percent, true), excelFieldMapping.get(Constants.IsFirmsRevenueMorethan25Percent), 0);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(firmMap, Constants.PercentOfRevenueFromLargestClient, false), excelFieldMapping.get(Constants.PercentOfRevenueFromLargestClient), 0);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(firmMap, Constants.PercentOfRevenueFromSecondLargestClient, false), excelFieldMapping.get(Constants.PercentOfRevenueFromSecondLargestClient), 0);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(firmMap, Constants.ServiceIndustryofLargestClient, false), excelFieldMapping.get(Constants.ServiceIndustryofLargestClient), 0);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(firmMap, Constants.ServiceIndustryofSecondLargestClient, false), excelFieldMapping.get(Constants.ServiceIndustryofSecondLargestClient), 0);
		
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(firmMap, Constants.IsFirmCarryingProfLiabilityIns, true), excelFieldMapping.get(Constants.IsFirmCarryingProfLiabilityIns), 0);
		populateProLiabilityIns(ctx, sheet);		
		
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(firmMap, Constants.IsSignedEngagement, true), excelFieldMapping.get(Constants.IsSignedEngagement), 0);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(firmMap, Constants.IsFirmAttendedRiskMagtSeminarIn3Years, true), excelFieldMapping.get(Constants.IsFirmAttendedRiskMagtSeminarIn3Years), 0);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(firmMap, Constants.IsPublicTradedAudited, true), excelFieldMapping.get(Constants.IsPublicTradedAudited), 0);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(firmMap, Constants.IsCommissionRecieved, true), excelFieldMapping.get(Constants.IsCommissionRecieved), 0);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(firmMap, Constants.IsPublicPrivateOfferingsDone, true), excelFieldMapping.get(Constants.IsPublicPrivateOfferingsDone), 0);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(firmMap, Constants.IsAnyRegulatoryInquiry, true), excelFieldMapping.get(Constants.IsAnyRegulatoryInquiry), 0);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(firmMap, Constants.RegulatoryInquiryExaplain, false), excelFieldMapping.get(Constants.RegulatoryInquiryExaplain), 0);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(firmMap, Constants.RegulatoryInquiryComment, false), excelFieldMapping.get(Constants.RegulatoryInquiryComment), 0);
		
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(firmMap, Constants.IsAwarenessOfAnyProfLiability, true), excelFieldMapping.get(Constants.IsAwarenessOfAnyProfLiability), 0);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(firmMap, Constants.IsAwarenessOfAnyProfLiabilitySuitAgainst, true), excelFieldMapping.get(Constants.IsAwarenessOfAnyProfLiabilitySuitAgainst), 0);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(firmMap, Constants.IsFirmDeclinedOtherthanNonPayment, true), excelFieldMapping.get(Constants.IsFirmDeclinedOtherthanNonPayment), 0);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(firmMap, Constants.ReasonForDecline, false), excelFieldMapping.get(Constants.ReasonForDecline), 0);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(firmMap, Constants.ReasonForDeclineComment, false), excelFieldMapping.get(Constants.ReasonForDeclineComment), 0);
		
		ExcelUtility.setCellValue(sheet, Math.removeAmountFormat(policyCoverageMap.get(Constants.Premium)!=null ? policyCoverageMap.get(Constants.Premium).toString() : 0), excelFieldMapping.get(Constants.Premium), 0);
		
		String strAggregateLimit = ExcelUtility.getValueFromMap(policyCoverageMap, Constants.AggregateLimit, false);
		String strOccuranceLimit = ExcelUtility.getValueFromMap(policyCoverageMap, Constants.OccuranceLimit, false);		
		String strLimitAmount = "$" + strAggregateLimit + "/$" + strOccuranceLimit;		
		ExcelUtility.setCellValue(sheet, strLimitAmount, excelFieldMapping.get(Constants.LimitAmount), 0);
		
//		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(policyCoverageMap, Constants.LimitAmount, false), excelFieldMapping.get(Constants.LimitAmount), 0);
		ExcelUtility.setCellValue(sheet, Math.removeAmountFormat(policyCoverageMap.get(Constants.DeductibleAmount)), excelFieldMapping.get(Constants.DeductibleAmount), 0);
		
		String IsClaimExpensesType = ExcelUtility.getValueFromMap(policyCoverageMap, Constants.IsClaimExpensesType, false);
		if("Y".equals(IsClaimExpensesType))
			IsClaimExpensesType = "Yes";
		else
			IsClaimExpensesType = "No";
		ExcelUtility.setCellValue(sheet, IsClaimExpensesType, excelFieldMapping.get(Constants.IsClaimExpensesType), 0);
		
		String IsClaimOptionType = ExcelUtility.getValueFromMap(policyCoverageMap, Constants.IsClaimOptionType, false);
		if("Y".equals(IsClaimOptionType))
			IsClaimOptionType = "Yes";
		else
			IsClaimOptionType = "No";
		ExcelUtility.setCellValue(sheet, IsClaimOptionType, excelFieldMapping.get(Constants.IsClaimOptionType), 0);
		
		
	}
	
	public void populateProLiabilityIns(Context ctx, Sheet sheet) throws Exception
	{
		Object objProLiabilityIns = ctx.get(Constants.PROFESSIONALLIABILITYINS_FREEFORM_01);
		Map proLiabilityInsMap = new HashMap();
		if(objProLiabilityIns != null)
			proLiabilityInsMap = (Map)objProLiabilityIns;
		
		Object objLimitDeductibleProLiabilityIns = ctx.get(Constants.LIMITDEDUCTIBLEPROFESSIONALLIABILITYINS_FREEFORM_01);
		Map proLimitDeductibleLiabilityInsMap = new HashMap();
		if(objLimitDeductibleProLiabilityIns != null)
			proLimitDeductibleLiabilityInsMap = (Map)objLimitDeductibleProLiabilityIns;
		
		Map excelFieldMapping = (HashMap) ctx.get("EXCEL_FIELD_MAPPING");		
		
		String strLimitAmount = ExcelUtility.getValueFromMap(proLimitDeductibleLiabilityInsMap, Constants.LimitAmount, false);
		Object objDeductibleAmount = ExcelUtility.getValueFromMap(proLimitDeductibleLiabilityInsMap, Constants.DeductibleAmount, false);
		
		if(!"".equals(strLimitAmount))
			strLimitAmount = "$" + strLimitAmount;
		ExcelUtility.setCellValue(sheet, strLimitAmount, excelFieldMapping.get(Constants.LimitAmountProIns), 0);
		
		String strDeductibleAmount = "";
		if(objDeductibleAmount != null && !"".equals(objDeductibleAmount.toString()))
			strDeductibleAmount = ExcelUtility.removeAmountFormat(objDeductibleAmount).toString();
		ExcelUtility.setCellValue(sheet, strDeductibleAmount, excelFieldMapping.get(Constants.DeductibleAmountProIns), 0);
		
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(proLiabilityInsMap, Constants.InsuranceCompanyNamePross, false), excelFieldMapping.get(Constants.InsuranceCompanyNamePross), 0);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getFormattedDateFromObject(proLiabilityInsMap.get(Constants.PolicyExpirationDatePross)), excelFieldMapping.get(Constants.PolicyExpirationDatePross), 0);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getFormattedDateFromObject(proLiabilityInsMap.get(Constants.ProInsPremium)), excelFieldMapping.get(Constants.ProInsPremium), 0);
		
		String strExp = ExcelUtility.getValueFromMap(proLiabilityInsMap, Constants.IsClaimExpLiability, true);
		if("Yes".equals(strExp))
		{
			ExcelUtility.setCellValue(sheet, "Yes", excelFieldMapping.get(Constants.IsClaimExpLiabilityYes), 0);
			ExcelUtility.setCellValue(sheet, "No", excelFieldMapping.get(Constants.IsClaimExpLiabilityNo), 0);
		}
		else if("No".equals(strExp))
		{
			ExcelUtility.setCellValue(sheet, "No", excelFieldMapping.get(Constants.IsClaimExpLiabilityYes), 0);
			ExcelUtility.setCellValue(sheet, "Yes", excelFieldMapping.get(Constants.IsClaimExpLiabilityNo), 0);	
		}
		else
		{
			ExcelUtility.setCellValue(sheet, "", excelFieldMapping.get(Constants.IsClaimExpLiabilityYes), 0);
			ExcelUtility.setCellValue(sheet, "", excelFieldMapping.get(Constants.IsClaimExpLiabilityNo), 0);
		}
		
		String strPerClaim = ExcelUtility.getValueFromMap(proLiabilityInsMap, Constants.IsPerClaim, true);
		if("Yes".equals(strPerClaim))
		{
			ExcelUtility.setCellValue(sheet, "Yes", excelFieldMapping.get(Constants.IsPerClaimYes), 0);
			ExcelUtility.setCellValue(sheet, "No", excelFieldMapping.get(Constants.IsPerClaimNo), 0);
		}
		else if("No".equals(strPerClaim))
		{
			ExcelUtility.setCellValue(sheet, "No", excelFieldMapping.get(Constants.IsPerClaimYes), 0);
			ExcelUtility.setCellValue(sheet, "Yes", excelFieldMapping.get(Constants.IsPerClaimNo), 0);	
		}
		else
		{
			ExcelUtility.setCellValue(sheet, "", excelFieldMapping.get(Constants.IsPerClaimYes), 0);
			ExcelUtility.setCellValue(sheet, "", excelFieldMapping.get(Constants.IsPerClaimNo), 0);
		}
		
		String strPriorDateFull = ExcelUtility.getValueFromMap(proLiabilityInsMap, Constants.IsPriorActDateFull, true);
		if("Yes".equals(strPriorDateFull))
		{
			ExcelUtility.setCellValue(sheet, "Yes", excelFieldMapping.get(Constants.IsPriorActDateFullYes), 0);
			ExcelUtility.setCellValue(sheet, "No", excelFieldMapping.get(Constants.IsPriorActDateFullNo), 0);
			ExcelUtility.setCellValue(sheet, "", excelFieldMapping.get(Constants.PriorActDatePross), 0);
			ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(proLiabilityInsMap, Constants.FirmYear, false), excelFieldMapping.get(Constants.FirmYear), 0);
			
		}
		else if("No".equals(strPriorDateFull))
		{
			ExcelUtility.setCellValue(sheet, "No", excelFieldMapping.get(Constants.IsPriorActDateFullYes), 0);
			ExcelUtility.setCellValue(sheet, "Yes", excelFieldMapping.get(Constants.IsPriorActDateFullNo), 0);	
			ExcelUtility.setCellValue(sheet, ExcelUtility.getFormattedDateFromObject(proLiabilityInsMap.get(Constants.PriorActDatePross)), excelFieldMapping.get(Constants.PriorActDatePross), 0);
			ExcelUtility.setCellValue(sheet, "", excelFieldMapping.get(Constants.FirmYear), 0);
			
		}
		else
		{
			ExcelUtility.setCellValue(sheet, "", excelFieldMapping.get(Constants.IsPriorActDateFullYes), 0);
			ExcelUtility.setCellValue(sheet, "", excelFieldMapping.get(Constants.IsPriorActDateFullNo), 0);
			ExcelUtility.setCellValue(sheet, "", excelFieldMapping.get(Constants.FirmYear), 0);
			ExcelUtility.setCellValue(sheet, "", excelFieldMapping.get(Constants.PriorActDatePross), 0);			
		}			
		
	}
	
	public void populatePeronnalAffiliation(Context ctx, Sheet sheet) throws Exception
	{
		Object attestationObj = ctx.get(Constants.BASICINFO_LIST_01);
		if(attestationObj == null)
			return;
		
		List attestationList = null;
		if (attestationObj != null)
			attestationList = (ArrayList) attestationObj;
		
		Map excelFieldMapping = (HashMap) ctx.get("EXCEL_FIELD_MAPPING");
		
		for (int i = 0; i < attestationList.size(); i++) {

			Map map = (HashMap) attestationList.get(i);			
			ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.NumberOfPersonnel, false), excelFieldMapping.get(Constants.NumberOfPersonnel +"_" + i), 0);
			
		}

	}
	
	public void populateGrossRevenue(Context ctx, Sheet sheet) throws Exception
	{
		Object attestationObj = ctx.get(Constants.BASICINFO_LIST_03);
		if(attestationObj == null)
			return;
		
		List attestationList = null;
		if (attestationObj != null)
			attestationList = (ArrayList) attestationObj;
		
		Map excelFieldMapping = (HashMap) ctx.get("EXCEL_FIELD_MAPPING");
		
		for (int i = 0; i < attestationList.size(); i++) {

			Map map = (HashMap) attestationList.get(i);			
			ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.YearEndDate, false), excelFieldMapping.get(Constants.YearEndDate +"_" + i), 0);
			ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.Amount, false), excelFieldMapping.get(Constants.Amount +"_" + i), 0);
			
		}
	}
	
	public void populateAOPList(Context ctx, Sheet sheet) throws Exception	
	{
		Object attestationObj = ctx.get(Constants.BASICINFO_LIST_AOP);
		if(attestationObj == null)
			return;
		
		List attestationList = null;
		if (attestationObj != null)
			attestationList = (ArrayList) attestationObj;
		
		Map excelFieldMapping = (HashMap) ctx.get("EXCEL_FIELD_MAPPING");
		
		for (int i = 0; i < attestationList.size(); i++) {

			Map map = (HashMap) attestationList.get(i);		
			int seq = Integer.parseInt(map.get("AOPSequence").toString());
			//seq = seq-1;
			
			ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.PercentageValue, false), excelFieldMapping.get(Constants.AOP_Percentage +"_" + seq), 0);
			if(excelFieldMapping.get(Constants.AOPCommentDesc +"_" + seq) != null)
				ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.AOPCommentDesc, false), excelFieldMapping.get(Constants.AOPCommentDesc +"_" + seq), 0);
			
		}
	}
	
	public void populatePublicPrivateOfferingList(Context ctx, Sheet sheet) throws Exception	
	{
		Object attestationObj = ctx.get(Constants.BASICINFO_LIST_06);
		if(attestationObj == null)
			return;
		
		List attestationList = null;
		if (attestationObj != null)
			attestationList = (ArrayList) attestationObj;
		
		Map excelFieldMapping = (HashMap) ctx.get("EXCEL_FIELD_MAPPING");
		RowColumnBean rb_federal = (RowColumnBean) excelFieldMapping.get(Constants.ClientNamePpo);
		
		for(int i = rb_federal.getRownum() + attestationList.size(); i< rb_federal.getRownum() + 16; i++)
        {
        	Row row =  sheet.getRow(i);    		
        	sheet.removeRow(row);
        }
		
		int rowtoshiftup_bld = -15 + attestationList.size();
		sheet.shiftRows(rb_federal.getRownum() + 16, sheet.getLastRowNum(), rowtoshiftup_bld);
		
		for (int i = 0; i < attestationList.size(); i++) {

			Map map = (HashMap) attestationList.get(i);
			
			ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.ClientNamePpo, false), excelFieldMapping.get(Constants.ClientNamePpo), i);
			ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.ClientIndustryDesc, false), excelFieldMapping.get(Constants.ClientIndustryDesc), i);
			ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.ServiceIndustryDesc, false), excelFieldMapping.get(Constants.ServiceIndustryDesc), i);
			
			ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.YearServiceRenderedPpo, false), excelFieldMapping.get(Constants.YearServiceRenderedPpo), i);
			ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.SizeOfOfferingPpo, false), excelFieldMapping.get(Constants.SizeOfOfferingPpo), i);
			ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.FeesChargedPpo, false), excelFieldMapping.get(Constants.FeesChargedPpo), i);
			
			ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.TypeOfOfferingDesc, false), excelFieldMapping.get(Constants.TypeOfOfferingDesc), i);
			
		}
	}
	
	public void populateClaimList(Context ctx, Sheet sheet) throws Exception	
	{
		Object attestationObj = ctx.get(Constants.BASICINFO_LIST_04);
		if(attestationObj == null)
			return;
		
		List attestationList = null;
		if (attestationObj != null)
			attestationList = (ArrayList) attestationObj;
		
		Map excelFieldMapping = (HashMap) ctx.get("EXCEL_FIELD_MAPPING");
		RowColumnBean rb_federal = (RowColumnBean) excelFieldMapping.get(Constants.NameOfClaimantCi);
		
		for(int i = rb_federal.getRownum() + attestationList.size(); i< rb_federal.getRownum() + 16; i++)
        {
        	Row row =  sheet.getRow(i);    		
        	sheet.removeRow(row);
        }
		
		int rowtoshiftup_bld = -15 + attestationList.size();
		sheet.shiftRows(rb_federal.getRownum() + 16, sheet.getLastRowNum(), rowtoshiftup_bld);
		
		for (int i = 0; i < attestationList.size(); i++) {

			Map map = (HashMap) attestationList.get(i);
			
			ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.NameOfClaimantCi, false), excelFieldMapping.get(Constants.NameOfClaimantCi), i);
			ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.IsThisClaimReportedCi, true), excelFieldMapping.get(Constants.IsThisClaimReportedCi), i);
			ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.NameOfInsuranceCompanyCi, false), excelFieldMapping.get(Constants.NameOfInsuranceCompanyCi), i);
			
			ExcelUtility.setCellValue(sheet, ExcelUtility.getFormattedDateFromObject(map.get(Constants.AllegedErrorCiDate)), excelFieldMapping.get(Constants.AllegedErrorCiDate), i);
			ExcelUtility.setCellValue(sheet, ExcelUtility.getFormattedDateFromObject(map.get(Constants.ReportedCiDate)), excelFieldMapping.get(Constants.ReportedCiDate), i);
			ExcelUtility.setCellValue(sheet, ExcelUtility.getFormattedDateFromObject(map.get(Constants.ClaimNotifiedCiDate)), excelFieldMapping.get(Constants.ClaimNotifiedCiDate), i);
			
			ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.NameOfFirmPersonnelCi, false), excelFieldMapping.get(Constants.NameOfFirmPersonnelCi), i);
			ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.InsurerLossReserveCi, false), excelFieldMapping.get(Constants.InsurerLossReserveCi), i);
			
			//ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.LastSettlementOfferCi, false), excelFieldMapping.get(Constants.LastSettlementOfferCi), i);
			String str = ExcelUtility.getValueFromMap(map, Constants.IsClientCi, true);
			if("Yes".equals(str))
				str = "Client";
			else if("No".equals(str))
				str = "Non-Client";		
			
			ExcelUtility.setCellValue(sheet, str, excelFieldMapping.get(Constants.IsClientCi), i);
			
			ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.ClaimantLastDemandCi, false), excelFieldMapping.get(Constants.ClaimantLastDemandCi), i);
			ExcelUtility.setCellValue(sheet, ExcelUtility.getFormattedDateFromObject(map.get(Constants.ClosingCiDate)), excelFieldMapping.get(Constants.ClosingCiDate), i);
			
			ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.TotalClaimExpensesCi, false), excelFieldMapping.get(Constants.TotalClaimExpensesCi), i);
			ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.DescOfClaimCi, false), excelFieldMapping.get(Constants.DescOfClaimCi), i);
			ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.StepsTakenToPreventClaimCi, false), excelFieldMapping.get(Constants.StepsTakenToPreventClaimCi), i);
			ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.CurrentStatus, false), excelFieldMapping.get(Constants.CurrentStatus), i);
			
		}
	}
	
	
	


}
