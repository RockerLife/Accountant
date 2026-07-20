package com.excel.downloadexcel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import com.excel.oldpim.ExcelUtility;
import com.ormapping.ibatis.SqlResources;
import com.util.Context;

public class StateMonthReport {
	
	public void getDataForStateMonthReport(Context ctx, Sheet sheet ) throws Exception {

		Map excelFieldMapping = (HashMap) ctx.get("EXCEL_FIELD_MAPPING");
		
		Object statemonthreportlist = SqlResources.getSqlMapProcessor(ctx).select("Account.weeklyreport_statemonth", ctx);
		ctx.put("statemonthreportlist", statemonthreportlist);

		List statedatelist = (List) ctx.get("statemonthreportlist");			
	
		RowColumnBean rb_federal = (RowColumnBean) excelFieldMapping.get(Constants.StateName);
		for(int i = rb_federal.getRownum() + statedatelist.size(); i< rb_federal.getRownum() + 799; i++){
			Row row = null;
			if(sheet.getRow(i) != null)
			{
				row =  sheet.getRow(i);  
				sheet.removeRow(row);
			}
        }
		
		int rowtoshiftup_bld = -799 + statedatelist.size();
		sheet.shiftRows(rb_federal.getRownum() + 799, sheet.getLastRowNum(), rowtoshiftup_bld);
		
		for (int i = 0; i < statedatelist.size(); i++) {
			Map map = (HashMap) statedatelist.get(i);

			ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.StateName, false), excelFieldMapping.get(Constants.StateName), i);
			ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.TotalApps, false), excelFieldMapping.get(Constants.TotalAppsState), i);

			ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.UnderReviewNew, false), excelFieldMapping.get(Constants.UnderReviewNew), i);
			ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.Declined, false), excelFieldMapping.get(Constants.Declined), i);
			ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.QuotedNew, false), excelFieldMapping.get(Constants.QuotedNew), i);

			ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.Issued, false), excelFieldMapping.get(Constants.Issued), i);
			ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.HitRatio, false), excelFieldMapping.get(Constants.HitRatio), i);
			ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.Pending, false), excelFieldMapping.get(Constants.Pending), i);

			ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.Rejected, false), excelFieldMapping.get(Constants.Rejected), i);
			ExcelUtility.setCellValue(sheet, ExcelUtility.getValueFromMap(map,Constants.PremiumFromIssued, false),excelFieldMapping.get(Constants.PremiumFromIssued),i);
		}			 
	}	

}
