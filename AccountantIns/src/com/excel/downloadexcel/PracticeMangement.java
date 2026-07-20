package com.excel.downloadexcel;

import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.excel.oldpim.ExcelUtility;
import com.ormapping.ibatis.SqlResources;
import com.pdf.PDFConstants;
import com.util.Context;

public class PracticeMangement {
	
	public void getDataForPracticeMangement(Context ctx) throws Exception
	{
		Object objFirm =SqlResources.getSqlMapProcessor(ctx).findByKey("Firm.findByKey", ctx);	
		ctx.put(PDFConstants.FIRM_FREEFORM_01, objFirm);
		
		Object objClientServiceDesc =SqlResources.getSqlMapProcessor(ctx).findByKey("SqlStmts.UserStatementsaveAccountstmtsgeClientServiceDescFirm", ctx);
		ctx.put(Constants.CLIENTSERVICEDESC_FIRM_FREEFORM_01, objClientServiceDesc);
		
	}
	
	public void populatePracticeMangementData(Context ctx, Workbook wb) throws Exception
	{
		Sheet sheet = wb.getSheet("Practice Management");
        CellStyle string= wb.createCellStyle();
        string.setDataFormat((short) 38);
        CellStyle numeric= wb.createCellStyle();
        numeric.setDataFormat((short) 38);
        
        Object objFirm = ctx.get(Constants.FIRM_FREEFORM_01);
		
//		if (objFirm == null){
//			return;
//		}
		
		Map firmMap = null;
		if (objFirm != null)
			firmMap = (HashMap) objFirm;

		Map excelFieldMapping = (HashMap) ctx.get("EXCEL_FIELD_MAPPING");
		
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(firmMap, Constants.IsSuitInstituted, true), excelFieldMapping.get(Constants.IsSuitInstituted), 0);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(firmMap, Constants.AgainstHowManyClients, false), excelFieldMapping.get(Constants.AgainstHowManyClients), 0);
		
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(firmMap, Constants.IsNonMonetaryCompensationRecieved, true), excelFieldMapping.get(Constants.IsNonMonetaryCompensationRecieved), 0);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(firmMap, Constants.CurrentValueNmc, false), excelFieldMapping.get(Constants.CurrentValueNmc), 0);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(firmMap, Constants.ClientNameNmc, false), excelFieldMapping.get(Constants.ClientNameNmc), 0);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(firmMap, Constants.IsReducedNumberOfOwners, true), excelFieldMapping.get(Constants.IsReducedNumberOfOwners), 0);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(firmMap, Constants.MergedOrNumberComment, false), excelFieldMapping.get(Constants.MergedOrNumberComment), 0);
		
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(firmMap, Constants.IsClientTerminated, true), excelFieldMapping.get(Constants.IsClientTerminated), 0);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(firmMap, Constants.IsAnyDeclination, true), excelFieldMapping.get(Constants.IsAnyDeclination), 0);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(firmMap, Constants.IsPolicyProhibiting, true), excelFieldMapping.get(Constants.IsPolicyProhibiting), 0);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(firmMap, Constants.IsWrittenAnyQualityControlDocument, true), excelFieldMapping.get(Constants.IsWrittenAnyQualityControlDocument), 0);
		
		populateClientServiceDescData(ctx, sheet);
	}
	
	public void populateClientServiceDescData(Context ctx, Sheet sheet) throws Exception
	{
		Object objClientServiceDesc = ctx.get(Constants.CLIENTSERVICEDESC_FIRM_FREEFORM_01);		
//		if (objClientServiceDesc == null){
//			return;
//		}
		
		Map clientServiceDescMap = null;
		if (objClientServiceDesc != null)
			clientServiceDescMap = (HashMap) objClientServiceDesc;
		
		Map excelFieldMapping = (HashMap) ctx.get("EXCEL_FIELD_MAPPING");
		
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(clientServiceDescMap, Constants.ClientIndustryDesc, false), excelFieldMapping.get(Constants.ClientIndustryNmcKey), 0);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(clientServiceDescMap, Constants.ServiceIndustryDesc, false), excelFieldMapping.get(Constants.ServiceIndustryNmcKey), 0);
		
	}

}
