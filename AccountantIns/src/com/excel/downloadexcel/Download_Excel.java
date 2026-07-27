package com.excel.downloadexcel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellReference;

import com.excel.oldpim.ExcelDatabaseMapping;
import com.manage.managemetadata.functions.commonfunctions.DateUtils;
import com.util.Context;
import com.util.IContext;
import com.util.InetLogger;
import com.util.SystemProperties;

public class Download_Excel implements Constants {

	private static InetLogger logger = InetLogger.getInetLogger(Download_Excel.class);
	private final String BASIC_INFO_SHEET = "Basic Info"; 
	private final String GENERAL_SHEET = "General"; 
	private final String SUPP_INVEST_FINANCIAL_PLANNING = "Supp Invest Financial Planning"; 
	private final String SUPP_ATTESTATION = "Supp Attestation"; 
	private final String SUPP_COMPUTER_RELATED_SERVICES = "Supp Computer Related Services"; 
	private final String PRACTICE_MANAGEMENT_SHEET = "Practice Management"; 
	private final String BUSINESS_MANAGEMENT_SHEET = "Supp Business Management";
	
	Map dbValues = null;
	private int cols = 0; // No of columns	
	
	private static String projectName;
	private final String[] sheets ={"Basic Info", "General", "Practice Management", 
									"Supp Invest Financial Planning", "Supp Attestation", 
									"Supp Computer Related Services", "Supp Business Management"};
	private final String[] sheetsReport ={"Data"};	
	
	private final String[] sheetsStateMonthReport ={"month1","month2","month3","month4","month5","month6","month7","month8","month9","month10","month11","month12"};
	
	private final String[] formulas ={"Basic Info", "General", "Practice Management", 
			"Supp Invest Financial Planning", "Supp Attestation", 
			"Supp Computer Related Services", "Supp Business Management", "Rating"};
	
	public static void main(String[] ar){
		try {
			
			Context ctx = new Context();
//			projectName = "AccountantIns";
//			String resourcePath = SystemProperties.getInstance().getString("xml.basedir") + projectName + "//ibatis//maps//SqlMapConfig.xml";
//			SqlResources.load(projectName, resourcePath);
//			
//			ctx.setProject(projectName);
//			ctx.put("PolicyKey", "294");
//			ctx.put("TransactionSequence", "294");
//			ctx.put("VersionSequence", "282");
//			ctx.put("CoverageSequence", "295");
//			ctx.put("AccountKey", "305");
//			ctx.put("AddressKey", "295");
//			
//			ctx.put("FromDate", "06-20-2010");
//			ctx.put("ToDate", "10-20-2010");
			
//			HSSFWorkbook wb;		
//			String file = SystemProperties.getInstance().getString("xml.basedir") + "excel//RatingTemplate.xls";
//			FileInputStream input = new FileInputStream(file);
//			wb = new HSSFWorkbook(input);
//			
//	        new DownloadExcel().populateDataIntoExcel(ctx, wb);		
//	        FileOutputStream out = new FileOutputStream("C://Documents and Settings//raghurajs//Desktop//Rating.xls");
//			wb.write(out);
//			out.flush();
//			out.close();	
//			input.close();
//			
//			String file = "D://EDRIVE//LawyersWork//2015//DataDump//AllDataReport.xlsx";
//			FileInputStream input = new FileInputStream(file);
//			XSSFWorkbook b = new XSSFWorkbook();
//			
//			System.out.println("Java");
//			input.close();
		} catch (Throwable e) {
			logger.error("Error in Download_Excel.main", e);
			
		}
		finally{
			
		}
	}
	
	public void downloadExcel(Context ctx, HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		FileInputStream input = null; 
		ServletOutputStream outputStream = null;
		
		try {			
			Workbook wb;			
			String file = SystemProperties.getInstance().getString("xml.basedir") + "excel//RatingTemplate.xls";
			
			input = new FileInputStream(file);
			wb = new HSSFWorkbook(input);
			
            populateDataIntoExcel(ctx, wb);
            
            outputStream = res.getOutputStream();
			wb.write(outputStream);
			outputStream.flush();			
			
		} catch (Exception e) {
			logger.error("Error in Download_Excel.downloadExcel", e);
			throw e;
		}
		finally{
			if(input != null)
				try {
					input.close();
				} catch (IOException e) {
					logger.error("Error in Download_Excel.downloadExcel", e);
					
					throw e;
				}
			if(outputStream != null)
				try {
					outputStream.close();
				} catch (IOException e) {
					logger.error("Error in Download_Excel.downloadExcel", e);
					
					throw e;
				}			
		}
	}
	
	public void populateDataIntoExcel(Context ctx, Workbook wb) throws Exception 
	{
		Map getSheetDatabasefield = null;
		
		for(int i=0; i<sheets.length; i++)
		{
			String sheet = sheets[i];
			getSheetDatabasefield = new ExcelDatabaseMapping().getCellInfo(ctx, wb.getSheet(sheet));        
			ctx.put("EXCEL_FIELD_MAPPING", getSheetDatabasefield);
		}		
		
		new BasicInfo().getDataForBasicInfo(ctx); 
        new BasicInfo().populateBasicInfoData(ctx, wb);
        
        new General().getDataForGeneral(ctx);
        new General().populateGeneralData(ctx, wb);
        
        new PracticeMangement().getDataForPracticeMangement(ctx);
        new PracticeMangement().populatePracticeMangementData(ctx, wb);
        
        new Investment().getDataForInvestment(ctx);
        new Investment().populateInvestmentData(ctx, wb);
        
        new Audit().getDataForAudit(ctx);
        new Audit().populateAuditData(ctx, wb);
        
        new ComputerService().getDataForComputerService(ctx);
        new ComputerService().populateComputerServiceData(ctx, wb);
        
        new BusinessManagment().getDataForBusinessManagment(ctx);
        new BusinessManagment().populateBusinessManagmentData(ctx, wb);   
        
        FormulaExecutor(wb,formulas);
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
								logger.error("## ERROR IN CELL ## "+ new CellReference(r.getRowNum(),c.getColumnIndex()).formatAsString(), e);
								//e.printStackTrace();
								
							}
						}
					}
				}
			}
		}
        		
	}
		
	public Download_Excel() {
		
	}
	
	public Download_Excel(String projectName) {
		setProjectName(projectName);
	}	

	public void processExcel() throws Exception {
		try {
		
		String file = SystemProperties.getInstance().getString("xml.basedir") + "excel//RatingTemplate.xls";
		
		FileInputStream fis = new FileInputStream(file);
	    POIFSFileSystem fs = new POIFSFileSystem(fis);
	    Workbook wb = new HSSFWorkbook(fs);

	    Sheet sheet = null;
	    
	    logger.debug(BASIC_INFO_SHEET);
	    sheet =  wb.getSheet(BASIC_INFO_SHEET);
		processSheet(sheet);
		
//		System.out.println(GENERAL_SHEET);
//	    sheet =  wb.getSheet(GENERAL_SHEET);
//		processSheet(sheet);
//		
//		System.out.println(SUPP_INVEST_FINANCIAL_PLANNING);
//	    sheet =  wb.getSheet(SUPP_INVEST_FINANCIAL_PLANNING);
//		processSheet(sheet);
//		
//		System.out.println(SUPP_ATTESTATION);
//	    sheet =  wb.getSheet(SUPP_ATTESTATION);
//		processSheet(sheet);
//		
//		System.out.println(SUPP_COMPUTER_RELATED_SERVICES);
//	    sheet =  wb.getSheet(SUPP_COMPUTER_RELATED_SERVICES);
//		processSheet(sheet);
//		
//		System.out.println(PRACTICE_MANAGEMENT_SHEET);
//		sheet =  wb.getSheet(PRACTICE_MANAGEMENT_SHEET);
//		processSheet(sheet);
//		
//		System.out.println(BUSINESS_MANAGEMENT_SHEET);
//		sheet =  wb.getSheet(BUSINESS_MANAGEMENT_SHEET);
//		processSheet(sheet);
		
        FileOutputStream out = new FileOutputStream("C://Documents and Settings//raghurajs//Desktop//Rating.xls");
		wb.write(out);
		out.flush();
		out.close();	
		fis.close();     
		
		} catch (FileNotFoundException e) {
			logger.error("Error in Download_Excel.processExcel", e);
			
		}
		catch (IOException e) {
			logger.error("Error in Download_Excel.processExcel", e);
			
		}
		
	}

	private void processSheet(Sheet sheet) throws Exception{
		Row row;
	    Cell cell;
	    int rows; // No of rows
	    rows = sheet.getPhysicalNumberOfRows();
	    int tmp = 0;
	    
	    populateDBValue();
	    
	    // This trick ensures that we get the data properly even if it doesn't start from first few rows
	    for(int i = 0; i < 10 || i < rows; i++) {
	        row = sheet.getRow(i);
	        if(row != null) {
	            tmp = sheet.getRow(i).getPhysicalNumberOfCells();
	            if(tmp > cols) 
	            	cols = tmp;
	        }
	    }

	    for(int r = 0; r < rows; r++) {
		   
	        row = sheet.getRow(r);
	        if(row != null) {
	        	for(int c=0; c < row.getLastCellNum(); c++){
					{ if (logger.isDebugEnabled()) logger.debug("\t" + row.getCell(c)); }
	        		if(null!=row.getCell(c) 
	        				&& Cell.CELL_TYPE_STRING == row.getCell(c).getCellType()
	        				&& row.getCell(c).getStringCellValue().length() > 1
	        				&& row.getCell(c).getStringCellValue().startsWith(CHECK_FIELD_SIGN) 
	        				&& row.getCell(c).getStringCellValue().endsWith(CHECK_FIELD_SIGN)){
//		        		System.out.println(row.getRowNum() + " " + row.getCell(c).getCellNum() + "\t" + row.getCell(c));
	        			populateExcelCell(row.getCell(c));
	        		}
	        	}
	        }
	    }
	}
	
	public void populateDBValue() throws Exception {
		
		if(dbValues == null){
			AccessDB accessDB = new AccessDB(getProjectName());
			dbValues = accessDB.populateDBValues();
		}
		
	}	
	
	public void populateExcelCell(Cell cell) throws Exception {
		
		String dbFieldToBeCalculated = cell.getStringCellValue().substring(1, cell.getStringCellValue().length()-1);
		cell.setCellValue("Calculated Value for " + dbValues.get(dbFieldToBeCalculated) + " from database.");
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
			String file = SystemProperties.getInstance().getString("xml.basedir") + "excel//WeeklyReportTemplate.xls";
			
			input = new FileInputStream(file);
			wb = new HSSFWorkbook(input);
			
			populateWeeklyReportDataIntoExcel(ctx, wb);
            
            outputStream = res.getOutputStream();
			wb.write(outputStream);
			outputStream.flush();			
			
		} catch (Exception e) {
			logger.error("Error in Download_Excel.downloadWeeklyReport", e);
		
		}
		finally{
			if(input != null)
				try {
					input.close();
				} catch (IOException e) {
					logger.error("Error in Download_Excel.downloadWeeklyReport", e);
				}
				
			if(outputStream != null)
				try {
					outputStream.close();
				} catch (IOException e) {
					logger.error("Error in Download_Excel.downloadWeeklyReport", e);
				}			
		}
	}
	
	public void downloadAllReport(Context ctx, HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		FileInputStream input = null; 
		ServletOutputStream outputStream = null;
		
		try {			
			Workbook wb;			
			String file = SystemProperties.getInstance().getString("xml.basedir") + "excel//AllDataReport.xls";
			
			input = new FileInputStream(file);
			wb = new HSSFWorkbook(input);
			
			populateAllDataIntoExcel(ctx, wb);
            
            outputStream = res.getOutputStream();
			wb.write(outputStream);
			outputStream.flush();			
			
		} catch (Exception e) {
			logger.error("Error in Download_Excel.downloadAllReport", e);
		
		}
		finally{
			if(input != null)
				try {
					input.close();
				} catch (IOException e) {
					logger.error("Error in Download_Excel.downloadAllReport", e);
				}
				
			if(outputStream != null)
				try {
					outputStream.close();
				} catch (IOException e) {
					logger.error("Error in Download_Excel.downloadAllReport", e);
				}			
		}
	}
	
	public void downloadStateMonthReport(Context ctx, HttpServletRequest req, HttpServletResponse res) throws Exception {
	
		FileInputStream input = null; 
		ServletOutputStream outputStream = null;
		
		try {			
			Workbook wb;			
			String file = SystemProperties.getInstance().getString("xml.basedir") + "excel//StateReportTemplate.xls";
			
			input = new FileInputStream(file);
			wb = new HSSFWorkbook(input);
			
			populateStateMonthReportDataIntoExcel(ctx, wb);
	        
	        outputStream = res.getOutputStream();
			wb.write(outputStream);
			outputStream.flush();			
			
		} catch (Exception e) {
			logger.error("Error in Download_Excel.downloadStateMonthReport", e);
			throw e;
		}
		finally{
			if(input != null)
				try {
					input.close();
				} catch (IOException e) {
					logger.error("Error in Download_Excel.downloadStateMonthReport", e);
				}
			if(outputStream != null)
				try {
					outputStream.close();
				} catch (IOException e) {
					logger.error("Error in Download_Excel.downloadStateMonthReport", e);
				}			
		}
	}
	
	public void downloadReport(Context ctx, HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		FileInputStream input = null; 
		ServletOutputStream outputStream = null;
		
		try {			
			outputStream = res.getOutputStream();
			
            if("QUICKBOOK".equals(ctx.get("ReportType"))){
            	new Report().getDataForQuickBookReport(ctx);

            	StringBuffer list = new Report().createQBReportData(ctx);
				outputStream.write(list.toString().getBytes());
			} else if("BROKERAGE".equals(ctx.get("ReportType"))){
            	
            	String file = SystemProperties.getInstance().getString("xml.basedir") + "excel//BrokerageReport.xls";
				input = new FileInputStream(file);
				Workbook wb = new HSSFWorkbook(input);
				
				populateReportDataIntoExcel(ctx, wb);
				
				wb.write(outputStream);
			}
			else if("ISMIE".equals(ctx.get("ReportType"))){
				String file = SystemProperties.getInstance().getString("xml.basedir") + "excel//ISMIEReportTemplate.xls";
				input = new FileInputStream(file);
				Workbook wb = new HSSFWorkbook(input);
				
				populateReportDataIntoExcel(ctx, wb);
				
				wb.write(outputStream);
			}
			else if("MCGOWAN".equals(ctx.get("ReportType"))){
				
				String []templates= {"ManualEntryPolicyLoadTemplate.xls","ManualEntryClientLoadTemplate.xls","TransactionTemplate.xls"};
				String reports[]= new String[3];
				String dirctoryPath=SystemProperties.getInstance().getString("tomcat.home.dir") +"webapps\\AccountantUploadFiles\\McGowanReports";
				if("Acc".equals(ctx.get("ProjectType").toString()))
				{
					reports[0]="McGowanPolicyReport_A.xls";
					reports[1]="McGowanClientReport_A.xls";
					reports[2]="McGowanTransactionReport_A.xls";
				}
				else
				{
					reports[0]="McGowanPolicyReport_L.xls";
					reports[1]="McGowanClientReport_L.xls";
					reports[2]="McGowanTransactionReport_L.xls";
				}
				for(int i=0;i<templates.length;i++)
				{
					Context newctx = new Context();
					newctx.setProject(ctx.getProject());
					newctx.put("ProjectType",ctx.get("ProjectType"));
					newctx.put("ReportType",ctx.get("ReportType"));
					newctx.put("SearchType",ctx.get("SearchType"));
					newctx.put("FromDate",ctx.get("FromDate"));
					newctx.put("ToDate",ctx.get("ToDate"));
					newctx.put("CompanyKey",ctx.get("CompanyKey"));
					generateMcGowanReport(newctx,templates[i],reports[i],dirctoryPath);
				}
				addFilesToZipAndDownload(ctx, dirctoryPath, outputStream);
				clearDirectory(new File(dirctoryPath));
			}
			else {
				String file = SystemProperties.getInstance().getString("xml.basedir") + "excel//ReportTemplate.xls";
				input = new FileInputStream(file);
				Workbook wb = new HSSFWorkbook(input);
				
				populateReportDataIntoExcel(ctx, wb);
				
				wb.write(outputStream);
			}
			outputStream.flush();			
			
		} catch (Exception e) {
			logger.error("Error in Download_Excel.downloadReport", e);
			
		}
		finally{
			if(input != null)
				try {
					input.close();
				} catch (IOException e) {
					logger.error("Error in Download_Excel.downloadReport", e);
					
				}
			if(outputStream != null)
				try {
					outputStream.close();
				} catch (IOException e) {
					logger.error("Error in Download_Excel.downloadReport", e);
					
					
				}			
		}
	}
		
	public void populateReportDataIntoExcel(Context ctx, Workbook wb) throws Exception 
	{
		Map getSheetDatabasefield = null;
		
		for(int i=0; i<sheetsReport.length; i++)
		{
			String sheet = sheetsReport[i];
			getSheetDatabasefield = new ExcelDatabaseMapping().getCellInfo(ctx, wb.getSheet(sheet));        
			ctx.put("EXCEL_FIELD_MAPPING", getSheetDatabasefield);
		}		
		
		new Report().getDataForReport(ctx); 
        new Report().populateReportData(ctx, wb, sheetsReport[0]);
		
        FormulaExecutor(wb,sheetsReport);
	}

	
	public void populateWeeklyReportDataIntoExcel(Context ctx, Workbook wb) throws Exception 
	{
		Map getSheetDatabasefield = null;
		
		String sheetsname=null;
		String sheetsids=null;
		
		try
		{
			sheetsname= SystemProperties.getInstance().getString("app.AccountantIns.Weeklyreport.sheetnames");			
			sheetsids= SystemProperties.getInstance().getString("app.AccountantIns.Weeklyreport.userids");
			
		}catch (Exception e) {
			logger.error("Error in Download_Excel.populateWeeklyReportDataIntoExcel", e);
			
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
				new WeeklyReport().getDataForWeeklyReport(ctx,sheetname,sheet); 
				
			}	
		    
		    FormulaExecutor(wb,sheetsWeeklyReport);
		}		
		
		
		
	}
	
	public void populateAllDataIntoExcel(Context ctx, Workbook wb) throws Exception 
	{
		Map getSheetDatabasefield = null;
		
		String sheetsname="Accountant:Lawyers";
		//String sheetsname="Accountant";
		String[] sheetsWeeklyReport=null;
		
		if(sheetsname!=null)
		{			
			sheetsWeeklyReport = sheetsname.split(":");	
			for(int i=0; i<sheetsWeeklyReport.length; i++)
			{
				String sheet = sheetsWeeklyReport[i];
				getSheetDatabasefield = new ExcelDatabaseMapping().getCellInfo(ctx, wb.getSheet(sheet));        
				ctx.put("EXCEL_FIELD_MAPPING", getSheetDatabasefield);	
				
				Sheet sheetname = wb.getSheet(sheet);
				new WeeklyReport().getAllDataForWeeklyReport(ctx,sheetname,sheet); 
				ctx.remove("EXCEL_FIELD_MAPPING");
			}
		}		
		
		
		
	}
	
	public void populateStateMonthReportDataIntoExcel(Context ctx, Workbook wb) throws Exception 
	{
		Map getSheetDatabasefield = null;
		
		for(int i=0; i<sheetsStateMonthReport.length; i++)
		{
			
			String sheet = sheetsStateMonthReport[i];
			getSheetDatabasefield = new ExcelDatabaseMapping().getCellInfo(ctx, wb.getSheet(sheet));        
			ctx.put("EXCEL_FIELD_MAPPING", getSheetDatabasefield);
			
			Sheet sheetname = wb.getSheet(sheet);
			new StateMonthReport().getDataForStateMonthReport(ctx ,sheetname); 
			
			
			
			 Calendar cal = Calendar.getInstance();
			 SimpleDateFormat sdf1 = new SimpleDateFormat("MM/dd/yyyy");
			 String weekdate=(String) ctx.get("WeekFromDate");
			 
			 Date date = new Date(weekdate);
			 cal.setTime(date);
			 cal.add(Calendar.MONTH,1);
			 date = cal.getTime();
			 String weekD =  DateUtils.convertDateToString(date, "MM/dd/yyyy");
			 ctx.put("WeekFromDate", weekD);
		}		
		 
		FormulaExecutor(wb,sheetsStateMonthReport);
		
	  
	}   
	
	
public void downloadEndorsementPdf(Context ctx, HttpServletRequest req, HttpServletResponse response,String outFile,String fileName) throws Exception {
		
		String file;
		
		response.setContentType("text/plain");
		  response.setHeader("Content-disposition","attachment; filename=" + fileName+ ".pdf"); // Used to name the download file and its format
           file = outFile;
   		 File my_file = new File(file); // We are downloading .txt file, in the format of doc with name check - check.doc

        
        OutputStream out = response.getOutputStream();
        FileInputStream in = new FileInputStream(my_file);
        byte[] buffer = new byte[4096];
        int length;
        while ((length = in.read(buffer)) > 0){
           out.write(buffer, 0, length);
        }
        in.close();
        out.flush();
	}

	public void addFilesToZipAndDownload(IContext ctx, String downLoadDirectoryPath, ServletOutputStream outputStream) throws Exception {
		try {
			File directory = new File(downLoadDirectoryPath);
			File[] fList = directory.listFiles();
			byte[] buffer = new byte[1024];
			ZipOutputStream zout = new ZipOutputStream(outputStream);
			for (int i = 0; i < fList.length; i++) {
	
				{ if (logger.isDebugEnabled()) logger.debug("Adding " + fList[i].getAbsolutePath()); }
	
				FileInputStream fin = new FileInputStream(fList[i].getAbsolutePath());
				zout.putNextEntry(new ZipEntry(fList[i].getName()));
				int length;
				while ((length = fin.read(buffer)) > 0) {
					zout.write(buffer, 0, length);
				}
				zout.closeEntry();
				fin.close();
			}
			zout.close();
			{ if (logger.isDebugEnabled()) logger.debug("Zip file has been created!"); }
		} catch (Exception e) {
			logger.error("Error in Download_Excel.addFilesToZipAndDownload", e);
		}
	}
	public void populateReportDataIntoExcelDynamic(Context ctx, Workbook wb,String templateName) throws Exception 
	{
		Map getSheetDatabasefield = null;
//		new Report().getDataForReport(ctx); 
		
		for(int i=0; i<wb.getNumberOfSheets(); i++)
		{
			
			String sheet = wb.getSheetName(i);
			
			new Report().getDataForMcGowanReport(ctx,sheet,templateName); 
			getSheetDatabasefield = new ExcelDatabaseMapping().getCellInfo(ctx, wb.getSheet(sheet));        
			ctx.put("EXCEL_FIELD_MAPPING", getSheetDatabasefield);
			new Report().populateReportDataDynamic(ctx, wb, sheet);
			FormulaExecutor(wb,sheetsReport);
			
		}
	}
	public void generateMcGowanReport(Context ctx,String template,String reportName,String dirctoryPath)throws Exception
	{
		File f =null;
		FileOutputStream fileOut=null;FileInputStream input = null;

		
			String file = SystemProperties.getInstance().getString("xml.basedir") + "excel//"+template;
			input = new FileInputStream(file);
			Workbook wb = new HSSFWorkbook(input);
			populateReportDataIntoExcelDynamic(ctx, wb,template);
			String filePath = dirctoryPath+"\\"+reportName;
			 f = new File(filePath);
			f.getParentFile().mkdirs(); 
			f.createNewFile();
			fileOut = new FileOutputStream(f,false);
			wb.write(fileOut);
			
			if(input!=null)
				input.close();
			if(fileOut!=null)
				fileOut.close();
			
		
	}

	static public void clearDirectory(File file) throws IOException, InterruptedException {

		File[] files = file.listFiles();
		for (File f : files) {
			if (f.isFile() && f.exists()) {
				f.delete();
				{ if (logger.isDebugEnabled()) logger.debug("successfully deleted"); }
			} else {
				{ if (logger.isDebugEnabled()) logger.debug("cant delete a file due to open or error"); }
			}
		}
	}
}
