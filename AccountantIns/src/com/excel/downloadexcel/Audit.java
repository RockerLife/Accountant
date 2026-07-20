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

public class Audit {
	
	public void getDataForAudit(Context ctx) throws Exception
	{
		Object objAttestation =SqlResources.getSqlMapProcessor(ctx).findByKey("Attestation.findByKey", ctx);
		ctx.put(Constants.ATTESTATION_FREEFORM_02, objAttestation);		
		
		Object listAttestationAuditWork =SqlResources.getSqlMapProcessor(ctx).select("SqlStmts.attestationviewmomAuditwork", ctx);
		ctx.put(Constants.ATTESTATION_LIST_03, listAttestationAuditWork);
	}	
	
	public void populateAuditData(Context ctx, Workbook wb) throws Exception
	{
		Sheet sheet = wb.getSheet("Supp Attestation");
        CellStyle string= wb.createCellStyle();
        string.setDataFormat((short) 38);
        CellStyle numeric= wb.createCellStyle();
        numeric.setDataFormat((short) 38);
        
        Object objFirm = ctx.get(Constants.ATTESTATION_FREEFORM_02);
		
//		if (objFirm == null){
//			return;
//		}
		
		Map firmMap = null;
		if (objFirm != null)
			firmMap = (HashMap) objFirm;

		Map excelFieldMapping = (HashMap) ctx.get("EXCEL_FIELD_MAPPING");
		
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(firmMap, Constants.AuditComment, false), excelFieldMapping.get(Constants.AuditComment), 0);
		
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(firmMap, Constants.FirmRenderedAuditAtt, false), excelFieldMapping.get(Constants.FirmRenderedAuditAtt), 0);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(firmMap, Constants.IsFirmHaveAWrittenPolicyAtt, true), excelFieldMapping.get(Constants.IsFirmHaveAWrittenPolicyAtt), 0);
		
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(firmMap, Constants.IsSecondPartnerReviewAtt, true), excelFieldMapping.get(Constants.IsSecondPartnerReviewAtt), 0);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(firmMap, Constants.IsAuditEngagementsAtt, true), excelFieldMapping.get(Constants.IsAuditEngagementsAtt), 0);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(firmMap, Constants.IsAualityReviewAtt, true), excelFieldMapping.get(Constants.IsAualityReviewAtt), 0);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(firmMap, Constants.IsLastPeerReviewAtt, true), excelFieldMapping.get(Constants.IsLastPeerReviewAtt), 0);
		ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(firmMap, Constants.LastPeerReviewAttComment, false), excelFieldMapping.get(Constants.LastPeerReviewAttComment), 0);
		
		populateAttestationList(ctx, sheet);
	}
	
	
	public void populateAttestationList(Context ctx, Sheet sheet) throws Exception
	{
		Object attestationObj = ctx.get(Constants.ATTESTATION_LIST_03);
		if(attestationObj == null)
			return;
		
		List attestationList = null;
		if (attestationObj != null)
			attestationList = (ArrayList) attestationObj;
		
		Map excelFieldMapping = (HashMap) ctx.get("EXCEL_FIELD_MAPPING");
		RowColumnBean rb_federal = (RowColumnBean) excelFieldMapping.get(Constants.AuditClientIndustryDesc);
		
		for(int i = rb_federal.getRownum() + attestationList.size(); i< rb_federal.getRownum() + 31; i++)
        {
        	Row row =  sheet.getRow(i);    		
        	sheet.removeRow(row);
        }
		
		int rowtoshiftup_bld = -30 + attestationList.size();
		sheet.shiftRows(rb_federal.getRownum() + 31, sheet.getLastRowNum(), rowtoshiftup_bld);
		
		for (int i = 0; i < attestationList.size(); i++) {

			Map map = (HashMap) attestationList.get(i);
			
			ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.ClientIndustrySrNo, false) + ExcelUtility.getValueFromMap(map, Constants.ClientIndustryDesc, false), excelFieldMapping.get(Constants.AuditClientIndustryDesc), i);
			ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.AuditClients, false), excelFieldMapping.get(Constants.AuditClients), i);
			ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.ClientsAssetsOver, false), excelFieldMapping.get(Constants.ClientsAssetsOver), i);
			ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map, Constants.NetLossForLastFYE, false), excelFieldMapping.get(Constants.NetLossForLastFYE), i);
			
		}
		
		
	}

}
