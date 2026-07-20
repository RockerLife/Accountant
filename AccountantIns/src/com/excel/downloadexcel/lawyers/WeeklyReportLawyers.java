package com.excel.downloadexcel.lawyers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import com.excel.downloadexcel.Constants;
import com.excel.downloadexcel.RowColumnBean;
import com.excel.oldpim.ExcelUtility;
import com.ormapping.ibatis.SqlResources;
import com.util.Context;

public class WeeklyReportLawyers {
	
	
	public void getDataForWeeklyReport(Context ctx, Sheet sheetname, String excelsheet) throws Exception {
			
		if(excelsheet.equals("ALL Issued Policies")){
			Object issuedlist = SqlResources.getSqlMapProcessor(ctx).select("SqlStmts.UserStatementreportgenerationgetReportListByIssuedPolicyLW", ctx);
			ctx.put("issuedlist", issuedlist);			
			
		}else{
			
			Object statedatelist = SqlResources.getSqlMapProcessor(ctx).select("Account.weeklyreport_by_state", ctx);
			ctx.put("statedatelist", statedatelist);			
			
			Object effectivedatelist = SqlResources.getSqlMapProcessor(ctx).select("Account.weeklyreport_effectivedate", ctx);
			ctx.put("effectivedatelist", effectivedatelist);		
		
			Object lastweeklist = SqlResources.getSqlMapProcessor(ctx).select("Account.weeklyreport_creationdate_LastWeek", ctx);
			ctx.put("lastweeklist", lastweeklist);		

			Object creativedatelist = SqlResources.getSqlMapProcessor(ctx).select("Account.weeklyreport_creationdate", ctx);
			ctx.put("creativedatelist", creativedatelist);	
		}
		
		populateReportList(ctx, sheetname,excelsheet);
		
	}
	


	public void populateReportList(Context ctx, Sheet sheet ,String excelsheet) throws Exception {

        Map excelFieldMapping = (HashMap) ctx.get("EXCEL_FIELD_MAPPING");

		if(excelsheet.equals("ALL Issued Policies")){
			
			List issuedlist = (List) ctx.get("issuedlist");
			
			RowColumnBean rb_federalstate = (RowColumnBean) excelFieldMapping.get(Constants.AccountName);
			for(int i = rb_federalstate.getRownum() + issuedlist.size(); i<= rb_federalstate.getRownum() + 2999; i++){
				Row row = null;
				if(sheet.getRow(i) != null)
				{
					row = sheet.getRow(i);  
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
	
	
	
	
	
//	public static void  main(String[] args) throws Exception
//	{  
//		
//		HSSFWorkbook wb;			
//		String file = SystemProperties.getInstance().getString("xml.basedir") + "excel//ReportTemplate.xls";
//		
//		FileInputStream input = new FileInputStream(file);
//		wb = new HSSFWorkbook(input);
//		
//		Context ctx = new Context();
//		String projectName = "AccountantIns";
//		String resourcePath = SystemProperties.getInstance().getString("xml.basedir") + projectName + "//ibatis//maps//Account.xml";
//		SqlResources.load(projectName, resourcePath);
//		
//		ctx.setProject(projectName);
//		Map getSheetDatabasefield = new ExcelDatabaseMapping().getCellInfo(ctx, wb.getSheet("ExportAllReport"));        
//		ctx.put("EXCEL_FIELD_MAPPING", getSheetDatabasefield);
//		
//		//new WeeklyReport().getDataForWeeklyReport(ctx);
//		 FileOutputStream out = new FileOutputStream("D://monika//Report.xls");
//			wb.write(out);
//			out.flush();
//			out.close();	
//			input.close();	
//		
//		
//	}
}

