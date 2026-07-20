package com.userbo;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.manage.managecomponent.components.SetParametersForStoredProcedures;
import com.manage.managemetadata.functions.commonfunctions.DataUtils;
import com.ormapping.ibatis.SqlResources;
import com.processor.RequestProcessor;
import com.util.Context;
import com.util.HtmlConstants;
import com.util.IContext;
import com.util.InetLogger;
import com.util.SystemProperties;

public class AccountsBatch{

	static List <String> headers = new ArrayList <String> ();
	private static InetLogger logger = InetLogger
			.getInetLogger(AccountsBatch.class);
	
	public static void lawyerBatchXML(Context ctx)  {
		try {
			
			HttpServletResponse response = (HttpServletResponse) ctx.get("DocumentResponse");
			
			//String filePath = "D:\\workspace_product\\LawyersIns\\Excel\\download\\Rating.xls";
			String excelname="Rating.xls";
			String filePath =SystemProperties.getInstance().getString("appl." + ctx.getProject() + ".ratingexceldownloadpath");
			filePath=filePath+excelname;
//			List inputList=new ArrayList();
			populateExcel(ctx,"filterCriteria","updateCriteria");
			
			
			String limit=ctx.get("LimitSequence").toString();
			limit=limit.replace(",", "");
			
			String PolicyEffectiveDate = ctx.get("LBEf;fectiveDate_update")!=""?ctx.get("LBEffectiveDate_update").toString():"";
			String Modifier = ctx.get("Modifier")!=""?ctx.get("Modifier").toString():"";
			
			Map map = new HashMap();
			map.put("PolicyEffectiveDateFrom", ctx.get("LBEffectiveDate_searchFrom"));
			map.put("PolicyEffectiveDateTo", ctx.get("LBEffectiveDate_searchTo"));
			map.put("PolicyType", ctx.get("LBpolicy_type"));
			map.put("StateCode", ctx.get("WQStateCode_search"));
			map.put("SubProduced", ctx.get("LBSubProduced"));
			map.put("SubProducerNumber", ctx.get("ProducerCode_search"));
			map.put("Limit", limit);
			map.put("PolicyEffectiveDate", ctx.get("LBEffectiveDate_update"));
			map.put("Modifier", ctx.get("Modifier"));
//			inputList.add(map);
			ctx.putAll(map);
			 
			//ctx.put("filterCriteria",populateInputCriteria(ctx, "filterCriteria"));
			//ctx.put("filterCriteria",populateInputCriteria(ctx, "filterCriteria"));
			//ctx.put("updateCriteria", populateInputCriteria(ctx, "updateCriteria"));
			
			Context ctxNew = new Context();
			ctxNew.setProject(ctx.getProject());
			ctxNew.put("filterCriteria",populateInputCriteria(ctx, "filterCriteria"));
			
			if(PolicyEffectiveDate != "" && Modifier != "")
				ctxNew.put("updateCriteria",populateInputCriteria(ctx, "updateCriteria"));
			
			String requestxml = new RequestProcessor().generateRequestXml(ctxNew);
			ctxNew.put("inputXml", requestxml);
			List list = fetchBatchRatingData(ctxNew);
			System.out.println(list.size());
			
			generateDataSheet(ctx, "Data", list);
			
			FormatExcel(filePath);
			
			
//			exportFileDownLoad(ctx,filePath,response);
			
			
       	 /*DataUtils.populateError(ctx, "successmessage", "File Downloaded Successfully");*/
			
			/*String request=new RequestProcessor().generateRequestXml(ctx);
			System.out.println("request>>>>>>>"+request);*/
	
		}catch(Exception e) {
			e.printStackTrace();
			
		}
	}
	
	public static void populateExcel(IContext ctx,String sheetname,String sheetname1){
		
		String excelname="Rating.xls";
		String filePath="";
		try {
			filePath = SystemProperties.getInstance().getString("appl." + ctx.getProject() + ".ratingexceldownloadpath");
			filePath=filePath+excelname;
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		String limit=ctx.get("LimitSequence").toString();
		limit=limit.replace(",", "");
		
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheetupd = workbook.createSheet(sheetname1);
		
		CellStyle style = workbook.createCellStyle();
		Font font = workbook.createFont();
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		style.setFont(font);
	   
		
		Map<String, Object[]> data = new LinkedHashMap<String, Object[]>();
		data.put("1", new Object[]{"PolicyEffectiveDate", "Modifier"});
		data.put("2", new Object[]{ctx.get("LBEffectiveDate_update"),ctx.get("Modifier")});

	Set<String> keyset = data.keySet();
    int rownum = 0;
    for (String key : keyset) {
    	if(rownum==0){
        Row row = sheetupd.createRow(rownum++);
        
        Object[] objArr = data.get(key);
        int cellnum = 0;
        for (Object obj : objArr) {
            Cell cell = row.createCell(cellnum++);
            if (obj instanceof Date) {
                cell.setCellValue((Date) obj);
                cell.setCellStyle(style);
            } else if (obj instanceof Boolean) {
                cell.setCellValue((Boolean) obj);
                cell.setCellStyle(style);
            } else if (obj instanceof String) {
                cell.setCellValue((String) obj);
                cell.setCellStyle(style);
            } else if (obj instanceof Double) {
                cell.setCellValue((Double) obj);
                cell.setCellStyle(style);
            }
        }
        
    	}
    	else{
    		 Row row = sheetupd.createRow(rownum++);
    		Object[] objArr = data.get(key);
	        int cellnum = 0;
	        for (Object obj : objArr) {
	            Cell cell = row.createCell(cellnum++);
	            if (obj instanceof Date) {
	                cell.setCellValue((Date) obj);
	            } else if (obj instanceof Boolean) {
	                cell.setCellValue((Boolean) obj);
	            } else if (obj instanceof String) {
	                cell.setCellValue((String) obj);
	            } else if (obj instanceof Double) {
	                cell.setCellValue((Double) obj);
	            }
	        }
	     
    	}
    }
	    HSSFSheet sheet = workbook.createSheet(sheetname);
	    
		  
	    Map<String, Object[]> data1 = new LinkedHashMap<String, Object[]>();
			data1.put("1", new Object[]{"PolicyEffectiveDateFrom", "PolicyEffectiveDateTo", "PolicyType","StateCode","SubProduced","SubProducerNumber","Limit"});
			data1.put("2", new Object[]{ctx.get("LBEffectiveDate_searchFrom"),ctx.get("LBEffectiveDate_searchTo"),ctx.get("LBpolicy_type"),ctx.get("WQStateCode_search"),ctx.get("LBSubProduced"),ctx.get("ProducerCode_search"),limit});

		Set<String> keyset1 = data1.keySet();
	    int rownum1 = 0;
	    for (String key : keyset1) {
	    	if(rownum1==0){
	        Row row1 = sheet.createRow(rownum1++);
	        
	        Object[] objArr1 = data1.get(key);
	        int cellnumm = 0;
	        for (Object obj : objArr1) {
	            Cell cell1 = row1.createCell(cellnumm++);
	            if (obj instanceof Date) {
	                cell1.setCellValue((Date) obj);
	                cell1.setCellStyle(style);
	            } else if (obj instanceof Boolean) {
	                cell1.setCellValue((Boolean) obj);
	                cell1.setCellStyle(style);
	            } else if (obj instanceof String) {
	                cell1.setCellValue((String) obj);
	                cell1.setCellStyle(style);
	            } else if (obj instanceof Double) {
	                cell1.setCellValue((Double) obj);
	                cell1.setCellStyle(style);
	            }
	        }
	        
	    	}
	    	else{
	    		 Row row1 = sheet.createRow(rownum1++);
	    		Object[] objArr1 = data1.get(key);
		        int cellnumm = 0;
		        for (Object obj : objArr1) {
		            Cell cell1 = row1.createCell(cellnumm++);
		            if (obj instanceof Date) {
		                cell1.setCellValue((Date) obj);
		            } else if (obj instanceof Boolean) {
		                cell1.setCellValue((Boolean) obj);
		            } else if (obj instanceof String) {
		                cell1.setCellValue((String) obj);
		            } else if (obj instanceof Double) {
		                cell1.setCellValue((Double) obj);
		            }
		        }
		     
	    	}
	    }
	    sheet.autoSizeColumn(7);
	    try {
	        FileOutputStream out
	                = new FileOutputStream(new File(filePath));
	        workbook.write(out);
	        out.close();
	        System.out.println("Excel written successfully..");
	    } catch (FileNotFoundException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	
	}
	
	public static Map <String, String> populateInputCriteria(IContext ctx, String sheetName) throws Exception {
		//String path = "D:\\workspace_product\\LawyersIns\\Excel\\download\\Rating.xls";	
		String excelname="Rating.xls";
		String path =SystemProperties.getInstance().getString("appl." + ctx.getProject() + ".ratingexceldownloadpath");
		path=path+excelname;
		String path1 = (String) ctx.get(path);
		FileInputStream input = new FileInputStream(path);		
		Workbook workbook = null;
		int rows; // No of rows
		String value = "";	
		Map <String, String>valuesMap = null;
		Row row;
		//XSSFRow row;
	    Cell cell = null;
	    Sheet sheet;
	    
	   try {
	          workbook = new HSSFWorkbook(input);
	          sheet = workbook.getSheet(sheetName);
			  rows = sheet.getPhysicalNumberOfRows();
			  row = sheet.getRow(0);
			  headers.clear();
	          if(row != null) {
	              for(int c=0; c < row.getLastCellNum(); c++){
	            	  headers.add(row.getCell(c).getStringCellValue());
	              }
	          }
	          
	          for(int r = 1; r < rows; r++) {      	    	
	  	        row = sheet.getRow(r);
	  	      if(row == null){
	              row = sheet.createRow(r);
	          }

	  	        valuesMap = new HashMap <String, String>();      	        
	  	        if(row != null) {
	  	        	for(int c=0; c < row.getLastCellNum(); c++){
	  	        		cell = row.getCell(c);
	  	        		if(cell == null){
	  	                  cell = row.createCell(c);
	  	              }

	  	        		if(cell != null){
	  	        			if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
	  	        				if(HSSFDateUtil.isCellDateFormatted(cell)) {
	  	        					SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
	  	        					String  date =  sdf.format(cell.getDateCellValue());
	  	        					valuesMap.put(headers.get(c), date); 
	  	        				} else {
			      	        		cell.setCellType(Cell.CELL_TYPE_STRING);
			      	        		valuesMap.put(headers.get(c), cell.getStringCellValue()); 
	      	        			}
	  	        				
	  	        			} else {
		      	        		cell.setCellType(Cell.CELL_TYPE_STRING);
		      	        		valuesMap.put(headers.get(c), cell.getStringCellValue()); 
	  	        			}
	  	        			
	  	        		}
	  	        	}     	        	
	  	        }      	        
	  	        break;
	  	    } 
	        input.close();
	        
		} catch (FileNotFoundException e) {
			logger.debug("Problem in reading file " + path + " " + e.getMessage());
		}
		catch (IOException e) {
			logger.debug("Problem in reading file " + path + " " + e.getMessage());
		}
	   return valuesMap;
	}
	
	public static void generateDataSheet(IContext ctx, String sheetName, List list) throws Exception {
		//String path = "D:\\workspace_product\\LawyersIns\\Excel\\download\\Rating.xls";	
		String excelname="Rating.xls";
		String path =SystemProperties.getInstance().getString("appl." + ctx.getProject() + ".ratingexceldownloadpath");
		path=path+excelname;
		String path1 = (String) ctx.get(path);
		FileInputStream input = null;		
		Workbook workbook = null;
		int rows; // No of rows
		String value = "";	
		Map <String, String>valuesMap = null;
		List headerList = new ArrayList();
		File file = null;
		FileOutputStream fileOut = null;
		Row row;
		//XSSFRow row;
	    Cell cell = null;
	    Sheet sheet;
	    String key = "";
	    String val = "";
	    
	   try {
		   	  input = new FileInputStream(path);
	          workbook = new HSSFWorkbook(input);
	          sheet = workbook.createSheet(sheetName);
			  headers.clear();
			  if(list == null)
				  return;
			  
			  CellStyle style = workbook.createCellStyle();
			  Font font = workbook.createFont();
			  font.setBoldweight(Font.BOLDWEIGHT_BOLD);
			  style.setFont(font);
			  
			    
			  if(list.size() >= 1){
				  Row headerRow = sheet.createRow(0);	
				  for(int j=0; j<RateBatchConstants.headers.length; j++){
					  cell = headerRow.createCell(j);
			          cell.setCellValue(RateBatchConstants.headers[j]);
			          cell.setCellStyle(style);
			          sheet.autoSizeColumn(j);    
				  }
			  }
				  
			  	int rowNum = 1;
		        for(int j=0; j<list.size(); j++) {
		            row = sheet.createRow(rowNum++);		            
		            Map valueMap = (Map) list.get(j);
		            for(int k=0; k<RateBatchConstants.headers.length; k++){
		            	key = RateBatchConstants.headers[k];
		            	if(valueMap.get(key) != null)
		            		val = valueMap.get(key).toString();
		            	else
		            		val = "";
		            	row.createCell(k).setCellValue(val);
		            }
		            sheet.autoSizeColumn(j);
		        }
		        
		        input.close();
		        file = new File(path);
		        if(file.exists())
		        	file.delete();
		        
		        fileOut = new FileOutputStream(path);
		        workbook.write(fileOut);
		        fileOut.close();
		       
		} catch (Exception e) {
			logger.debug("Problem in writing in file " + path + " " + e.getMessage());
		} finally {
			/*code by sukhi 26/09/2018*/
			if(input != null){
				input.close();
				input = null;
			}
			if(fileOut != null){
				fileOut.close();
				fileOut = null;
			}
			file=null;
		}
		
	   
	}

	public static List fetchBatchRatingData(IContext ctx)throws Exception {
		logger.debug("Going to execute the stored procedure BatchRatingForLawyers_Proc");
		new SetParametersForStoredProcedures().setParametersInContext(ctx, "inputXml");
		List list = (List)SqlResources.getSqlMapProcessor(ctx).select("Policy.BatchRatingForLawyers_Proc", ctx);
		logger.debug("BatchRatingForLawyers_Proc has been executed");
		return list;
	}

	
	public static void lawyerBatchExel(IContext ctx){
		try{
			System.out.println(ctx);
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

	public static void exportFileDownLoad(Context ctx,String filePath, HttpServletResponse response){
		
		File downloadFile = null;
		ServletOutputStream outStream = null;
		FileInputStream inStream = null;
		
		try{
	    	
	        if(filePath != null && filePath != "" && filePath != "null"){

	        	//System.out.println(filePath);
	        	downloadFile = new File(filePath);
	        	
	        	if(downloadFile.exists()){
	        		System.out.println(downloadFile.getAbsolutePath().substring(downloadFile.getAbsolutePath().lastIndexOf("\\")+1));
	        		/*inStream = new FileInputStream(downloadFile);
		        	    
		        	    // gets MIME type of the file
	        		String mimeType = "application/octet-stream";
		        	    //System.out.println("MIME type: " + mimeType);

	        		    // modifies response
	        		response.setContentType(mimeType);
	        		response.setContentLength((int) downloadFile.length());
	        		
		        	    // forces download
	        		String headerKey = "Content-Disposition";
	        		String headerValue = String.format("inline; filename=\"%s\"", downloadFile.getName());//attachment
	        		response.setHeader(headerKey, headerValue);
		        	    //System.out.println(headerKey+"   fgfgfgf   "+ headerValue);
		        	    
	        		outStream = response.getOutputStream();
		            
	        		response.flushBuffer();
	        		
	        		byte[] buffer = new byte[(int) downloadFile.length()];
	        		int bytesRead = -1;
		                 
	        		while ((bytesRead = inStream.read(buffer)) != -1) {
	        			outStream.write(buffer, 0, bytesRead);
	        		}
		                 
	        		if(inStream != null)
			    		inStream.close();
	        		if(outStream != null)
			    		outStream.close();
	        		*/       		
	        		
	        		
	        		
	        		String filename = downloadFile.toString();
	               /* response.setContentType("application/octet-stream");
	                response.setHeader("Content-Disposition",
	                        "attachment;filename="+filename);*/
	        		HttpServletRequest req=(HttpServletRequest)ctx.get("DocumentRequest");	        		
	        		ServletContext servletContext=(ServletContext)ctx.get("DocumentServletContext");
	        		
	        		String contentType = servletContext.getMimeType(
	        				(String) ctx.get("DocFileName"));
	        		response.setContentType(contentType);
	        		response.setHeader("Content-disposition", "attachment; filename="
	        				+ filename);

	                File file = new File(filename);
	                InputStream fileIn = new FileInputStream(file);
	                outStream = response.getOutputStream();

	                byte[] outputByte = new byte[(int)file.length()];
	                //copy binary contect to output stream
	                /*while(fileIn.read(outputByte, 0, (int)file.length()) != -1)
	                {
	                	outStream.write(outputByte, 0, (int)file.length());
	                }*/
	                
	                fileIn.read(outputByte);
	                fileIn.close();
	                
	                ByteArrayOutputStream bout = new ByteArrayOutputStream();
	        		bout.write(outputByte);
	        		bout.writeTo(outStream);

//	        		outStream.flush();
	        		bout.close();
	        		outStream.close();
	                
	        		DataUtils.populateError(ctx, "successmessage", "File Downloaded Successfully");
	        		
	        		//response.flushBuffer();
	        		ctx.put("showCallRating","Y");
	        		//System.out.println("sssssss");
	        		return;
	        	}
	        	
	        }
	        ctx.put("showCallRating","N");
	        return;
		} catch(Exception ee) {
			ee.printStackTrace();
			return;
	    } finally {
	    	/*code by sukhi 26/09/2018*/
	    	try {
		    	if(inStream != null){
		    		inStream.close();
		    		inStream = null;
		    	}
		    	if(outStream != null){
		    		outStream.close();
		    		outStream = null;
		    	}
		    	downloadFile = null;
	    	}catch(Exception e){
	    		e.printStackTrace();
	    	}
	    }
	}

	public void RatingProcessExcel(IContext ctx){
		try{
		processExcel("Data",ctx);
		DataUtils.populateError((Context) ctx, "successmessage", "Call Rating Successfully");
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public void processExcel(String sheetName,IContext ctx) throws Exception {
		headers.clear();
		//String path = "C:\\Users\\raghurajs\\Desktop\\RatingOut.xls";
		//String path = "D:\\workspace_product\\LawyersIns\\Excel\\upload\\Rating.xls";
		String excelname="Rating.xls";
		String path =SystemProperties.getInstance().getString("appl." + ctx.getProject() + ".ratingexceldownloadpath");
		path=path+excelname;
		FileInputStream input = null;
		File file = null;
		FileOutputStream outFile = null;
		Workbook workbook = null;
		int rows; // No of rows
		String value = "";	
		Map <String, String>valuesMap = null;
		Row row;
		//XSSFRow row;
	    Cell cell = null;
	    Sheet sheet;
	    Map <String, Integer> headersIndexUpdated = new HashMap <String, Integer> ();
	   try {
		   input = new FileInputStream(path);
		   	workbook = new HSSFWorkbook(input);
		   	sheet = workbook.getSheet(sheetName); 
			rows = sheet.getPhysicalNumberOfRows();
			row = sheet.getRow(0);
	          if(row != null) {
	              for(int c=0; c < row.getLastCellNum(); c++){
	            	  headers.add(row.getCell(c).getStringCellValue());
	              }
	          }
	          
	          for(int c=0; c < RateBatchConstants.headersUpdated.length; c++){
	        	  String header = RateBatchConstants.headersUpdated[c];
	        	  if(headers.contains(header)){
	        		  for(int k=(headers.size())-1; k >=0 ; k--){
	        			if(header.equals(headers.get(k))){
	        				headersIndexUpdated.put(header, new Integer(k));
	        				break;
	        			}
	        		  }
	        	  }
	        	  
	          }
	          
	          System.out.println(headersIndexUpdated);
	          
	          for(int r = 1; r < rows; r++) {      	    	
	  	        row = sheet.getRow(r);
	  	        if(row == null){
	  	        	row = sheet.createRow(r);
	  	        }

	  	        valuesMap = new HashMap <String, String>();      	        
	  	        if(row != null) {
	  	        	for(int c=0; c < row.getLastCellNum(); c++){
	  	        		cell = row.getCell(c);
	  	        		if(cell == null){
	  	                  cell = row.createCell(c);
	  	        		}

	  	        		if(cell != null){
	  	        			if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
	  	        				if(HSSFDateUtil.isCellDateFormatted(cell)) {
	  	        					SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
	  	        					String  date =  sdf.format(cell.getDateCellValue());
	  	        					valuesMap.put(headers.get(c), date); 
	  	        				} else {
			      	        		cell.setCellType(Cell.CELL_TYPE_STRING);
			      	        		valuesMap.put(headers.get(c), cell.getStringCellValue()); 
	      	        			}
	  	        				
	  	        			} else {
		      	        		cell.setCellType(Cell.CELL_TYPE_STRING);
		      	        		valuesMap.put(headers.get(c), cell.getStringCellValue()); 
	  	        			}
	  	        			
	  	        		}
	  	        	}
	  	        	
	  	        	proceeExcelRow(valuesMap);        	        	
	  	        	
	  	        	Iterator <String> itr= headersIndexUpdated.keySet().iterator();
	  	        	String key = "";
	  	        	while(itr.hasNext()){
	  	        		key = itr.next();  
	  	        		int index = headersIndexUpdated.get(key);
	  	        		cell = row.getCell(index);
	  	        		if(cell == null){
	  	                  cell = row.createCell(index);
	  	        		}

		        	if(cell != null){
		        		cell.setCellType(Cell.CELL_TYPE_STRING);
		        		cell.setCellValue(valuesMap.get(key));
		        	}
	  	        	}
	  	        	
	  	        } 
	  	        
	          } 
	          
	          for(int i = 0; i < RateBatchConstants.headers.length; i++) {
		            sheet.autoSizeColumn(i);
	          }
	          
	          input.close();
	          file = new File(path);
	          if(file.exists())
	        	  file.delete();
		        
	         //FileOutputStream outFile =new FileOutputStream(new File("C:\\Users\\raghurajs\\Desktop\\temp.xls"));
	         outFile = new FileOutputStream(path);
	          workbook.write(outFile);
	          outFile.close();
	         
	          
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			/*code by sukhi 26/09/2018*/
	    	try {
		    	if(input != null){
		    		input.close();
		    		input = null;
		    	}
		    	if(outFile != null){
		    		outFile.close();
		    		outFile = null;
		    	}
		    	file = null;
	    	}catch(Exception e){
	    		e.printStackTrace();
	    	}
	    }
		
	}
	public void proceeExcelRow(Map <String, String>valuesMap) throws Exception {
		RatingBatch.callingRatingEngine(valuesMap);
		//System.out.println(valuesMap.get("Premium").toString());
		System.out.println("Row has processed");
	}

	public static void ValidateDownloadExcel(Context ctx){
		try{
			String successdate="true";
			String successlimit="true";
		if((ctx.get("LBEffectiveDate_searchFrom") != null && !HtmlConstants.EMPTY.equals(ctx.get("LBEffectiveDate_searchFrom").toString()))
	    		&& (ctx.get("LBEffectiveDate_searchTo") != null && !HtmlConstants.EMPTY.equals(ctx.get("LBEffectiveDate_searchTo").toString()))){
			ctx.put("isDataFound", "Y");
			
			String issued_date=ctx.get("LBEffectiveDate_searchFrom").toString();
			String iisued_dateTo=ctx.get("LBEffectiveDate_searchTo").toString();
			
			 SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
	         Date date1 = sdf.parse(issued_date);
	         Date date2 = sdf.parse(iisued_dateTo);
				
	         if(date2.before(date1)){
	        	 AccountUtils.populateError(ctx, "LBEffectiveDate_searchTo", "Effective Date To should be greater than Effective Date From");
	        	 successdate="false";
	         }
							
			
		}
		else{
			if (ctx.get("LBEffectiveDate_searchFrom") == null || HtmlConstants.EMPTY.equals(ctx.get("LBEffectiveDate_searchFrom").toString())) {
				AccountUtils.populateError(ctx, "LBEffectiveDate_searchFrom", "Effective Date From is required field");
				successdate="false";
			}
			if (ctx.get("LBEffectiveDate_searchTo") == null || HtmlConstants.EMPTY.equals(ctx.get("LBEffectiveDate_searchTo").toString())) {
				AccountUtils.populateError(ctx, "LBEffectiveDate_searchTo","Effective Date To is required field");
	    	successdate="false";
	    } 
		}
		
		if(ctx.get("LimitSequence") == "" || ctx.get("LimitSequence") == null){
			AccountUtils.populateError(ctx, "LimitSequence","Limit is required field");
			successlimit="false";
		}
		
		if(successdate.equals("true") && successlimit.equals("true")){
			DataUtils.populateError(ctx,"validatesuccessmessage","Fields Validated Successfully");
			ctx.put("showCallRating","Y");
		}
		
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return;
	}

	public static void uploadExcel(IContext ctx) throws Exception {
        logger.debug("processDocument has started");
        if(ctx.get("fileItems") == null || !(ctx.get("fileItems") instanceof List))
              return;
        
        String fileName = null;
        String DocFileName = null;
        FileItem uploadFile = null;

        
        try {
              List fileItems = (List)ctx.get("fileItems");
              Iterator i = fileItems.iterator();

              while (i.hasNext()) {
                    FileItem fi = (FileItem) i.next();
                    if (!fi.isFormField()) {
                          // filename on the client
                          fileName = fi.getName();
                          uploadFile = fi;
                          if (fileName == null) {
                                continue;
                          }
                    }
              }
              
        } catch (Exception e) {
              logger.debug("Error in file process");
              e.printStackTrace();
              throw e;
        }

        if (fileName == null || "".equals(fileName))
        	AccountUtils.populateError(ctx, "DocFileName", "Document has not been selected");
        

        
        int fileIndex = fileName.indexOf(".");
        String extension = fileName.substring(fileIndex);
        logger.debug(extension);
        File file = null;
        File upfile = null;
        
        try{
              String org_fileName = fileName;
              //fileName = fileName + extension;
              logger.debug(fileName);

//              DocFileName = fileName.substring(fileName.lastIndexOf("\\") + 1,fileName.length());
              DocFileName="Rating.xls";
              
              file = new File(fileName);
              String docName = file.getName();
              String docNameTemp = file.getName();
              
              AccountUtils.populateLastUpdateTimeStamp(ctx);

              String uploaddirectory = SystemProperties.getInstance().getString("appl." + ctx.getProject() + ".ratingexceluploadpath");
              upfile = new File(uploaddirectory, DocFileName);
              if(upfile.exists()){
            	  upfile.delete();
              }
              try {
                    uploadFile.write(new File(uploaddirectory, DocFileName));
              } catch (Exception e) {
                    System.out.println("Exception " + e );
                    AccountUtils.populateError(ctx, "DocUploadError",
                                "Document could not be write to file" );
                    

                    return;
              }

              String result = null;
              /*try {
                    
                    result = new DocumentManagment().uploadDocumentForSubproducer(ctx, uploaddirectory + docName);
                    //result = "File added successfully!";
                    logger.debug("Result from uploadDocument(ctx, uploaddirectory + docName)" + result);
              } catch (Exception e) {
                    System.out.println("Exception --->" + e);
                    AccountUtils.populateError(ctx, "DocUploadError",
                                "Document could not be uploaded");
                    return;
              }
              if (result == null
                          || (result != null && !result
                                      .equals("File added successfully!"))) {
                    
                    AccountUtils.populateError(ctx, "DocUploadError",
                                "Document could not be uploaded");

                    return;
              }

              if (result != null && result.equals("File added successfully!")) {
                    logger.debug("processDocument has done");
                    // ctx.put("DocTitle", DocTitle);
              
                    ctx.put("DocTitle", org_fileName);

              
                    // ctx.put("DocFileName", DocFileName);

                    int index = docName.indexOf(".");
                    String fileName1 = docName.substring(0, index);
                    String fileExt = docName.substring(index + 1, docName.length());
                    docName = fileName1 + "_" + ctx.get("UploadedTime").toString()
                                + "." + fileExt;
                    docName = docName.replace(" ", "").replace(":", "")
                                .replace("-", "");

                    ctx.put("DocFileName", docName);
                    ctx.put("DocType", DocType);

                    ctx.put("UploadedType", "Manual");
                    
                    new DocumentManagment().setUrlForSubproducer(ctx, docName, "file_path");
                    new DocumentManagment().insertInSubproducerTable(ctx);
              }

              File tempFile = new File(uploaddirectory + docNameTemp);
              if (tempFile.exists())
                    tempFile.delete();*/
              
              DataUtils.populateError((Context) ctx, "successmessage", "File Uploaded Successfully");
              
              
              }catch(Exception e){
                    System.out.println("Exception in DocFileName" + e);
                    e.printStackTrace();
              } finally {
            	  /*code by sukhi 26/09/2018*/
                  upfile=null;
                  file=null;
              }
  }
	
	public static void FormatExcel(String path){
		String excelFilePath = path;
		FileInputStream inputStream = null;
		FileOutputStream out = null;
        try {
            inputStream = new FileInputStream(new File(excelFilePath));
            Workbook workbook = WorkbookFactory.create(inputStream);
            int sheets=workbook.getNumberOfSheets();
            for(int i=0;i<sheets;i++){
            Sheet sheet = workbook.getSheetAt(i);
            int numberOfCells =0;
            int rowCount = sheet.getLastRowNum();
            Iterator rowIterator = sheet.rowIterator();
            /**
             * Escape the header row *
             */
            if (rowIterator.hasNext())
            {
                Row headerRow = (Row) rowIterator.next();
                Iterator<Cell> cellIterator = headerRow.cellIterator();

                while (cellIterator.hasNext()) {
               Cell cell = cellIterator.next();
               int cellnum=cell.getColumnIndex();
               System.out.println(cell.getStringCellValue());
               sheet.autoSizeColumn(cellnum);
                }
                
                //get the number of cells in the header row
                	
                }
            out = new FileOutputStream(path);
            workbook.write(out);
            
            
            /*code by sukhi 26/09/2018*/
            out.close();
            inputStream.close();
            }
	}catch(Exception e){
		System.out.println(e.toString());
		e.printStackTrace();
	} finally {
		/*code by sukhi 26/09/2018*/
		try {
			if(out != null){
				out.close();
				out = null;
			}
			if(inputStream != null){
				inputStream.close();
				inputStream = null;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	}
	
}
