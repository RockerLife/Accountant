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
import com.ormapping.ibatis.SqlResources;
import com.util.Context;

public class General {
	
	public void getDataForGeneral(Context ctx) throws Exception
	{
		Object objFirm =SqlResources.getSqlMapProcessor(ctx).findByKey("SqlStmts.UserStatementpdfquotelettergetPriorPolicyInfo", ctx);	
		ctx.put(Constants.LABELS_FREEFORM_01, objFirm);
		
		Object objSeperateEntitySupp =SqlResources.getSqlMapProcessor(ctx).findByKey("SeperateEntitySupp.findByKey", ctx);	
		ctx.put(Constants.GENERAL_FREEFORM_02, objSeperateEntitySupp);
		
		Object objFundsControlledSupp =SqlResources.getSqlMapProcessor(ctx).findByKey("FundsControlledSupp.findByKey", ctx);
		ctx.put(Constants.GENERAL_FREEFORM_03, objFundsControlledSupp);	
		
		Object objClientServiceDescFundControl =SqlResources.getSqlMapProcessor(ctx).findByKey("SqlStmts.UserStatementsaveAccountstmtsgeClientServiceDescFundControl", ctx);
		ctx.put(Constants.CLIENTSERVICEDESC_FUNDCONTROL_FREEFORM_01, objClientServiceDescFundControl);
				
		Object listTrusteeOrEstateSupp =SqlResources.getSqlMapProcessor(ctx).select("SqlStmts.generalviewListTrusteeOrEstateSupp", ctx);
		ctx.put(Constants.GENERAL_LIST_01, listTrusteeOrEstateSupp);
		
		Object listOutsideInterestSupp =SqlResources.getSqlMapProcessor(ctx).select("SqlStmts.generalviewgetOutsideInterestSupp", ctx);
		ctx.put(Constants.GENERAL_LIST_04, listOutsideInterestSupp);
			
	}
	
	
	public void populateGeneralData(Context ctx, Workbook wb) throws Exception
	{
		Sheet sheet = wb.getSheet("General");
        CellStyle string= wb.createCellStyle();
        string.setDataFormat((short) 38);
        CellStyle numeric= wb.createCellStyle();
        numeric.setDataFormat((short) 38);
        
        populateFirmInfo(ctx, sheet);
        populateSeperateEntity(ctx, sheet);
        populateBusinessManagment(ctx, sheet);
        
        populateFirmRenderedList(ctx, sheet);
        populateTrusteeList(ctx, sheet);
	}
	
	public void populateFirmInfo(Context ctx, Sheet sheet) throws Exception
	{
		Object objFirm = ctx.get(Constants.FIRM_FREEFORM_01);
		Map firmMap = null;
		if(objFirm != null)
			firmMap = (Map)objFirm;
		
//		if(firmMap == null)
//			return;

		Map excelFieldMapping = (HashMap) ctx.get("EXCEL_FIELD_MAPPING");		
				
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(firmMap, Constants.IsPolicyExcludesCoverage, true), excelFieldMapping.get(Constants.IsPolicyExcludesCoverage), 0);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(firmMap, Constants.RetroFirmName, false), excelFieldMapping.get(Constants.RetroFirmName), 0);		
		
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(firmMap, Constants.IsFirmRenderingServices, true), excelFieldMapping.get(Constants.IsFirmRenderingServices), 0);		
		
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(firmMap, Constants.IsInvestmentVenture, true), excelFieldMapping.get(Constants.IsInvestmentVenture), 0);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(firmMap, Constants.IsSyndicationManagers, true), excelFieldMapping.get(Constants.IsSyndicationManagers), 0);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(firmMap, Constants.IsFirmRenderedAuditOrAttestService, true), excelFieldMapping.get(Constants.IsFirmRenderedAuditOrAttestService), 0);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(firmMap, Constants.IsManagementServiceProvided, true), excelFieldMapping.get(Constants.IsManagementServiceProvided), 0);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(firmMap, Constants.IsAnyFirmMeargedOrAcquired, true), excelFieldMapping.get(Constants.IsAnyFirmMeargedOrAcquired), 0);
		
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(firmMap, Constants.IsControlClientFunds, true), excelFieldMapping.get(Constants.IsControlClientFunds), 0);
		
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(firmMap, Constants.IsDebtOrEquityFinancing, true), excelFieldMapping.get(Constants.IsDebtOrEquityFinancing), 0);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(firmMap, Constants.IsNonPublicInvestmentDone, true), excelFieldMapping.get(Constants.IsNonPublicInvestmentDone), 0);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(firmMap, Constants.IsServedAsTrustee, true), excelFieldMapping.get(Constants.IsServedAsTrustee), 0);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(firmMap, Constants.IsFirmRenderedServiceForAnyClient, true), excelFieldMapping.get(Constants.IsFirmRenderedServiceForAnyClient), 0);
			
	}
	
	public void populateSeperateEntity(Context ctx, Sheet sheet) throws Exception
	{
		Object objSeperateEntity = ctx.get(Constants.GENERAL_FREEFORM_02);
		Map seperateEntityMap = null;
		if(objSeperateEntity != null)
			seperateEntityMap = (Map)objSeperateEntity;
		
		Map excelFieldMapping = (HashMap) ctx.get("EXCEL_FIELD_MAPPING");
		
//		if(seperateEntityMap == null)
//			return;
		
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(seperateEntityMap, Constants.EntityNameSe, false), excelFieldMapping.get(Constants.EntityNameSe), 0);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(seperateEntityMap, Constants.EstablisedSeDate, false), excelFieldMapping.get(Constants.EstablisedSeDate), 0);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(seperateEntityMap, Constants.PercentOfOwnershipSe, false), excelFieldMapping.get(Constants.PercentOfOwnershipSe), 0);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(seperateEntityMap, Constants.TotalProfessionalStaffSe, false), excelFieldMapping.get(Constants.TotalProfessionalStaffSe), 0);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(seperateEntityMap, Constants.TotalSupportStaffSe, false), excelFieldMapping.get(Constants.TotalSupportStaffSe), 0);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(seperateEntityMap, Constants.DetailedDescSe, false), excelFieldMapping.get(Constants.DetailedDescSe), 0);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(seperateEntityMap, Constants.EstimateCurrentYearSeEndDate, false), excelFieldMapping.get(Constants.EstimateCurrentYearSeEndDate), 0);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(seperateEntityMap, Constants.EstimateCurrentYearGrossRevenueSe, false), excelFieldMapping.get(Constants.EstimateCurrentYearGrossRevenueSe), 0);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(seperateEntityMap, Constants.LastFiscalYearSeEndDate, false), excelFieldMapping.get(Constants.LastFiscalYearSeEndDate), 0);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(seperateEntityMap, Constants.LastFiscalYearGrossRevenueSe, false), excelFieldMapping.get(Constants.LastFiscalYearGrossRevenueSe), 0);
		
	}
	
	public void populateBusinessManagment(Context ctx, Sheet sheet) throws Exception
	{
		Object objFirm = ctx.get(Constants.GENERAL_FREEFORM_03);
		
//		if (objFirm == null){
//			return;
//		}
		
		Map firmMap = null;
		if (objFirm != null)
			firmMap = (HashMap) objFirm;

		Map excelFieldMapping = (HashMap) ctx.get("EXCEL_FIELD_MAPPING");
		
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(firmMap, Constants.TotlaNumberOfClientsFc, false), excelFieldMapping.get(Constants.TotlaNumberOfClientsFc), 0);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(firmMap, Constants.TotalAmountOfClientFundsFc, false), excelFieldMapping.get(Constants.TotalAmountOfClientFundsFc), 0);
		
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(firmMap, Constants.IsControlFundsForBusinessAndEntimentFc, true), excelFieldMapping.get(Constants.IsControlFundsForBusinessAndEntimentFc), 0);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(firmMap, Constants.NumberOfClientsFc, false), excelFieldMapping.get(Constants.NumberOfClientsFc), 0);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(firmMap, Constants.IsCountersignatureRequiredFc, true), excelFieldMapping.get(Constants.IsCountersignatureRequiredFc), 0);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(firmMap, Constants.IsBankAccountReconciledFc, true), excelFieldMapping.get(Constants.IsBankAccountReconciledFc), 0);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(firmMap, Constants.IsFirmPersonnelBusinessManagerFc, true), excelFieldMapping.get(Constants.IsFirmPersonnelBusinessManagerFc), 0);
		
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(firmMap, Constants.ProvideNumberOfClientFc, false), excelFieldMapping.get(Constants.ProvideNumberOfClientFc), 0);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(firmMap, Constants.NumberOfFirmEmployeesFc, false), excelFieldMapping.get(Constants.NumberOfFirmEmployeesFc), 0);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(firmMap, Constants.IsEmployeeDishonestyCoverageFc, true), excelFieldMapping.get(Constants.IsEmployeeDishonestyCoverageFc), 0);
		
		populateClientServiceDescData(ctx, sheet);
	}
	
	public void populateClientServiceDescData(Context ctx, Sheet sheet) throws Exception
	{
		Object objClientServiceDesc = ctx.get(Constants.CLIENTSERVICEDESC_FUNDCONTROL_FREEFORM_01);		
//		if (objClientServiceDesc == null){
//			return;
//		}
		
		Map clientServiceDescMap = null;
		if (objClientServiceDesc != null)
			clientServiceDescMap = (HashMap) objClientServiceDesc;
		
		Map excelFieldMapping = (HashMap) ctx.get("EXCEL_FIELD_MAPPING");		
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(clientServiceDescMap, Constants.ClientIndustryDescFc, false), excelFieldMapping.get(Constants.ClientIndustryDescFc), 0);
		
	}
	
	public void populateTrusteeList(Context ctx, Sheet sheet) throws Exception	
	{
		Object attestationObj = ctx.get(Constants.GENERAL_LIST_01);
		if(attestationObj == null)
			return;
		
		List attestationList = null;
		if (attestationObj != null)
			attestationList = (ArrayList) attestationObj;
		
		Map excelFieldMapping = (HashMap) ctx.get("EXCEL_FIELD_MAPPING");
		RowColumnBean rb_federal = (RowColumnBean) excelFieldMapping.get(Constants.NameOfTrust);
		
		for(int i = rb_federal.getRownum() + attestationList.size(); i< rb_federal.getRownum() + 16; i++)
        {
        	Row row =  sheet.getRow(i);    		
        	sheet.removeRow(row);
        }
		
		int rowtoshiftup_bld = -15 + attestationList.size();
		sheet.shiftRows(rb_federal.getRownum() + 16, sheet.getLastRowNum(), rowtoshiftup_bld);
		
		for (int i = 0; i < attestationList.size(); i++) {

			Map map = (HashMap) attestationList.get(i);
			
			ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.NameOfTrust, false), excelFieldMapping.get(Constants.NameOfTrust), i);
			ExcelUtility.setCellValue(sheet, ExcelUtility.getFormattedDateFromObject(map.get(Constants.StartEngagemenTrusttDate)), excelFieldMapping.get(Constants.StartEngagemenTrusttDate), i);
			ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.TypeofTrustDesc, false), excelFieldMapping.get(Constants.TypeofTrustDesc), i);
			ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.ValueOfAssetsTrust, false), excelFieldMapping.get(Constants.ValueOfAssetsTrust), i);
			
			ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.AnnualIncomeofAssetsTrust, false), excelFieldMapping.get(Constants.AnnualIncomeofAssetsTrust), i);
			ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.NumberOfBeneficiariesTrust, false), excelFieldMapping.get(Constants.NumberOfBeneficiariesTrust), i);
			ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.IsBeneficiaryInterestTrust, true), excelFieldMapping.get(Constants.IsBeneficiaryInterestTrust), i);
			
		}
	}
	
	public void populateFirmRenderedList(Context ctx, Sheet sheet) throws Exception	
	{
		Object attestationObj = ctx.get(Constants.GENERAL_LIST_04);
		if(attestationObj == null)
			return;
		
		List attestationList = null;
		if (attestationObj != null)
			attestationList = (ArrayList) attestationObj;
		
		Map excelFieldMapping = (HashMap) ctx.get("EXCEL_FIELD_MAPPING");
		RowColumnBean rb_federal = (RowColumnBean) excelFieldMapping.get(Constants.ClientNameOsi);
		
		for(int i = rb_federal.getRownum() + attestationList.size(); i< rb_federal.getRownum() + 16; i++)
        {
        	Row row =  sheet.getRow(i);    		
        	sheet.removeRow(row);
        }
		
		int rowtoshiftup_bld = -15 + attestationList.size();
		sheet.shiftRows(rb_federal.getRownum() + 16, sheet.getLastRowNum(), rowtoshiftup_bld);
		
		for (int i = 0; i < attestationList.size(); i++) {

			Map map = (HashMap) attestationList.get(i);
			
			String ClientIndustryOsiKey = ExcelUtility.getValueFromMap(map, Constants.ClientIndustryDesc, false);
			String ClientIndustryOsiKeyOthers = ExcelUtility.getValueFromMap(map, "ClientIndustryOsiKeyOthers", false);
			if(!"".equals(ClientIndustryOsiKeyOthers))
				ClientIndustryOsiKey = ClientIndustryOsiKey +" - " + ClientIndustryOsiKeyOthers;
			
			ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.ClientNameOsi, false), excelFieldMapping.get(Constants.ClientNameOsi), i);
			ExcelUtility.setCellValue(sheet, ExcelUtility.getFormattedDateFromObject(map.get(Constants.StartEngagemenTrusttDate)), excelFieldMapping.get(Constants.StartEngagemenTrusttDate), i);
			ExcelUtility.setCellValue(sheet, ClientIndustryOsiKey, excelFieldMapping.get(Constants.ClientIndustryOsiKey), i);
			ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.ServiceIndustryDesc, false), excelFieldMapping.get(Constants.ServiceIndustryOsiKey), i);
			
			ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.PositionHeldOsi, false), excelFieldMapping.get(Constants.PositionHeldOsi), i);
			ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.DOInsuranceOsi, true), excelFieldMapping.get(Constants.DOInsuranceOsi), i);
			ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.PercentEquityInterestofOsi, false), excelFieldMapping.get(Constants.PercentEquityInterestofOsi), i);
			ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.IndividualListedServicesOsi, true), excelFieldMapping.get(Constants.IndividualListedServicesOsi), i);
			ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.EntityNameOsi, false), excelFieldMapping.get(Constants.EntityNameOsi), i);
			
			
		}
	}


}
