package com.excel.downloadexcel.lawyers;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellReference;

import com.excel.downloadexcel.Constants;
import com.excel.downloadexcel.Download_Excel;
import com.excel.oldpim.ExcelDatabaseMapping;
import com.util.Context;
import com.util.InetLogger;
import com.util.SystemProperties;

public class DownloadExcelLawyers extends Download_Excel implements Constants {
	
	private static InetLogger logger = InetLogger.getInetLogger(DownloadExcelLawyers.class);
	private final String BASIC_INFO_SHEET = "Basic Info"; 
	private final String GENERAL_SHEET = "General"; 
	private final String SUPP_INVEST_FINANCIAL_PLANNING = "Supp Invest Financial Planning"; 
	private final String SUPP_ATTESTATION = "Supp Attestation"; 
	private final String SUPP_COMPUTER_RELATED_SERVICES = "Supp Computer Related Services"; 
	private final String PRACTICE_MANAGEMENT_SHEET = "Practice Management"; 
	private final String BUSINESS_MANAGEMENT_SHEET = "Supp Business Management";
	
	Map dbValues = null;
	private int cols = 0;	
	
	private static String projectName;
	private final String[] sheets ={"Basic Info", "General", "Practice Management", 
									"Supp Invest Financial Planning", "Supp Attestation", 
									"Supp Computer Related Services", "Supp Business Management"};
	private final String[] sheetsReport ={"Data"};	
	
	private final String[] sheetsStateMonthReport ={"month1","month2","month3","month4","month5","month6","month7","month8","month9","month10","month11","month12"};
	
	private final String[] formulas ={"Basic Info", "General", "Practice Management", 
			"Supp Invest Financial Planning", "Supp Attestation", 
			"Supp Computer Related Services", "Supp Business Management", "Rating"};	
	
		
	public DownloadExcelLawyers() {
		
	}
	
	public DownloadExcelLawyers(String projectName) {
		setProjectName(projectName);
	}			

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
		
	public void downloadWeeklyReport(Context ctx, HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		FileInputStream input = null; 
		ServletOutputStream outputStream = null;
		
		try {			
			Workbook wb;			
			String file = SystemProperties.getInstance().getString("xml.basedir") + "excel//WeeklyReportTemplateLW.xls";
			
			input = new FileInputStream(file);
			wb = new HSSFWorkbook(input);
			
			populateWeeklyReportDataIntoExcel(ctx, wb);
            
            outputStream = res.getOutputStream();
			wb.write(outputStream);
			outputStream.flush();			
			
		} catch (Exception e) {
			e.printStackTrace();
		
		}
		finally{
			if(input != null)
				try {
					input.close();
				} catch (IOException e) {					
					e.printStackTrace();					
				}
				
			if(outputStream != null)
				try {
					outputStream.close();
				} catch (IOException e) {					
					e.printStackTrace();					
				}			
		}
	}
		
	public void populateWeeklyReportDataIntoExcel(Context ctx, Workbook wb) throws Exception 
	{
		Map getSheetDatabasefield = null;		
		String sheetsname=null;
		String sheetsids=null;
		
		try
		{
			sheetsname= SystemProperties.getInstance().getString("app.LawyersIns.Weeklyreport.sheetnames");			
			sheetsids= SystemProperties.getInstance().getString("app.LawyersIns.Weeklyreport.userids");
			
		}catch (Exception e) {
			e.printStackTrace();			
		}
		
		String[] sheetsWeeklyReport=null;
		String[] firstviewerId=null;
		if(sheetsname!=null)
		{			
			sheetsWeeklyReport = sheetsname.split(":");
			firstviewerId  = sheetsids.split(":");
			
			for(int i=0; i<sheetsWeeklyReport.length; i++)
			{
				String sheet = sheetsWeeklyReport[i];
				getSheetDatabasefield = new ExcelDatabaseMapping().getCellInfo(ctx, wb.getSheet(sheet)); 				
				ctx.put("EXCEL_FIELD_MAPPING", getSheetDatabasefield);
				
				if("NULL".equals(firstviewerId[i]))
					ctx.put("FirstReviewUserID", null);
				else
					ctx.put("FirstReviewUserID", firstviewerId[i]);
				
				Sheet sheetname = wb.getSheet(sheet);
				new WeeklyReportLawyers().getDataForWeeklyReport(ctx,sheetname,sheet); 
				
			}	
		    
		    FormulaExecutor(wb,sheetsWeeklyReport);
		}	
		
		/*
		List sheetList = null;		
		Map ExportAllReportMap = new HashMap();
		ExportAllReportMap.put("AgentName", "ExportAllReport");
		ExportAllReportMap.put("FirstReviewUserID", null);
		
		Map AllIssuedPoliciesMap = new HashMap();
		ExportAllReportMap.put("AgentName", "All Issued Policies");
		ExportAllReportMap.put("FirstReviewUserID", null);
		
		sheetList.add(ExportAllReportMap);
		sheetList.add(AllIssuedPoliciesMap);		
		*/
		
	}
			
	public void FormulaExecutor(Workbook wb,String[] sheets)
	{
		FormulaEvaluator evaluator = wb.getCreationHelper().createFormulaEvaluator();
		for(int i=0; i<sheets.length; i++)
		{
			String sheetName = sheets[i];
			Sheet sheet = wb.getSheet(sheetName); 
			if(sheet != null){
				for(Row r : sheet) {
					for(Cell c : r) {
						if(c.getCellType() == Cell.CELL_TYPE_FORMULA) {
	                	//System.out.println(c.getCellFormula());
							try{
								evaluator.evaluateFormulaCell(c);
							}catch(Exception e){
								System.out.println("## ERROR IN CELL ## "+ new CellReference(r.getRowNum(),c.getColumnIndex()).formatAsString());
								logger.error("## ERROR IN CELL ## "+ new CellReference(r.getRowNum(),c.getColumnIndex()).formatAsString());
								//e.printStackTrace();
								
							}
						}
					}
				}
			}
		}
        		
	}
        
	

}
