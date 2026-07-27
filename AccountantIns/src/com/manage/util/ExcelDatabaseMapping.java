package com.manage.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellUtil;

import com.manage.managebusinessrules.rules.RuleImpl;
import com.manage.managebusinessrules.rules.RulesResources;
import com.manage.managemetadata.functions.commonfunctions.DataUtils;
import com.manage.managemetadata.metadata.MetaDataResources;
import com.manage.managemetadata.security.SecurityResources;
import com.util.Context;
import com.util.HtmlConstants;
import com.util.InetLogger;
import com.util.StringUtils;
import com.util.TripleDESEncryptionDecryption;

public class ExcelDatabaseMapping
{
  private static InetLogger logger = InetLogger.getInetLogger(ExcelDatabaseMapping.class);
  
  public Map getCellInformation(Context ctx, Sheet mainSheet, CellStyle numericStyle, CellStyle dateStyle, CellStyle textStyle) throws Exception {
		Map map = new HashMap();
		int rowNum = 0;
		for(int i=rowNum; i<=mainSheet.getLastRowNum(); i++){
			Row row = mainSheet.getRow(i);
	        if(row == null)
	        	continue;
      
	        for(int j = 0; j<61; j++){            	
	        	Cell cell = row.getCell(j);
          
	            if((cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK)){
	            	continue;
	            }
	            
	            if(cell.getCellType() == Cell.CELL_TYPE_STRING && cell.getStringCellValue().startsWith("start_") && 
	            		cell.getStringCellValue().contains("_repeatlist_")){
	            	
	            	String id = cell.getStringCellValue().substring(cell.getStringCellValue().indexOf("_")+1);
	            	logger.debug(id);
	            	
	            	mainSheet.removeRow(row);
	            	mainSheet.shiftRows(row.getRowNum()+1, mainSheet.getLastRowNum(), -1);
	            	
	            	//Added code if list have no header's, only have data
	            	int startIdRowNumber = row.getRowNum();
	            	Map listDataMap = getCellInfo(mainSheet, row.getRowNum(), startIdRowNumber, new HashMap());
	            	map.put(id, listDataMap);
	            	//Ended code
	            	
	            	int endIdRowNumber = 0;
	            	endIdRowNumber = getRowNumber(mainSheet, "end_"+id, row.getRowNum());
	            	
	            	//getting header's row cells data
	            	Map rowHeaderMap = getRowHeaderCellInfo(mainSheet, row.getRowNum(), endIdRowNumber-1, new LinkedHashMap());
	            	
	            	Map headerMap = getCellInfo(mainSheet, row.getRowNum()+1, endIdRowNumber-1, new LinkedHashMap());
	            	map.put(id, headerMap);
	            	
	            	int repeatCount = 0;
	            	int priorListSize = 0;
	            	int rowIndex = startIdRowNumber+priorListSize+1;
	            	
	            	if(mainSheet.getRow(row.getRowNum()+1) != null)
	            		mainSheet.removeRow(mainSheet.getRow(row.getRowNum()+1));
	            	
	            	if(endIdRowNumber < mainSheet.getLastRowNum()){
	            		mainSheet.shiftRows(row.getRowNum()+1+1, mainSheet.getLastRowNum(), -1);
	            	}
	            	
	            	while(repeatCount <= 100){
	            		if(ctx.get(id+"_"+repeatCount) != null && ctx.get(id+"_"+repeatCount) instanceof List){
	        				ArrayList dataList = (ArrayList)ctx.get(id+"_"+repeatCount);
	        				ctx.put(id, dataList);
	        				
	        				if(repeatCount > 0){
	        					mainSheet.shiftRows(rowIndex, mainSheet.getLastRowNum(), dataList.size()+2);
	        					
	        					//making header row here
	        					if(rowHeaderMap.size() > 0){
	        						Row headerRow = mainSheet.createRow(rowIndex);
	        						
		        					Set<Map.Entry> entrySet = rowHeaderMap.entrySet();
		        					Iterator<Map.Entry> itr = entrySet.iterator();
		        					int headerCellIndex = 0;
		        					while(itr.hasNext()){
		        						Map.Entry entry = itr.next();
		        						if(entry.getKey() != null){
		        							Cell headerCell = headerRow.createCell(headerCellIndex);
		        							
		        							Cell headerRowCell = (Cell)entry.getValue();
		        							headerCell.setCellValue(headerRowCell.getStringCellValue());
		        							headerCell.setCellStyle(headerRowCell.getCellStyle());
		        							headerCellIndex++;
		        						}
		        					}
		        					
		        					rowIndex++;
	        					}
	        				}else
	        					mainSheet.shiftRows(rowIndex, mainSheet.getLastRowNum(), dataList.size()+1);
	        				
	        				rowIndex = new ExcelGeneration().populateRepeatList(ctx, headerMap, id, mainSheet, startIdRowNumber, listDataMap, numericStyle, dateStyle, textStyle, rowIndex);
	        				rowIndex = rowIndex + 1;
	            		}else{
	            			break;
	            		}
	            		
	            		repeatCount++;
	            	}
	            	
	            	endIdRowNumber = getRowNumber(mainSheet, "end_"+id, row.getRowNum());
	            	
	            	rowNum = endIdRowNumber;
	            	i = rowNum-1;
	            	
	            	if(mainSheet.getRow(endIdRowNumber) != null)
	            		mainSheet.removeRow(mainSheet.getRow(endIdRowNumber));
	            	
	            	if(endIdRowNumber < mainSheet.getLastRowNum()){
	            		mainSheet.shiftRows(endIdRowNumber+1, mainSheet.getLastRowNum(), -1);
	            	}
	            }else if(cell.getCellType() == Cell.CELL_TYPE_STRING && cell.getStringCellValue().startsWith("start_") && 
	            		cell.getStringCellValue().contains("_repeatcontextdatalist_")){
	            	
	            	//CellStyle style = mainSheet.getWorkbook().createCellStyle();
	            	//Font font = (Font)mainSheet.getWorkbook().createFont();
	            	
	            	String id = cell.getStringCellValue().substring(cell.getStringCellValue().indexOf("_")+1);
	            	logger.debug(id);
	            	
	            	int endIdRowNumber = 0;
	            	endIdRowNumber = getRowNumber(mainSheet, "end_"+id, row.getRowNum());
	            	
	            	populateRepeatContextdataList(ctx, id, mainSheet, row, endIdRowNumber, map, numericStyle, dateStyle, textStyle);
	            	
	            	endIdRowNumber = getRowNumber(mainSheet, "end_"+id, row.getRowNum());
	            	
	            	rowNum = endIdRowNumber;
	            	i = rowNum-1;
	            	
	            	if(mainSheet.getRow(endIdRowNumber) != null)
	            		mainSheet.removeRow(mainSheet.getRow(endIdRowNumber));
	            	
	            	if(endIdRowNumber < mainSheet.getLastRowNum()){
	            		mainSheet.shiftRows(endIdRowNumber+1, mainSheet.getLastRowNum(), -1);
	            	}
	            }else if(cell.getCellType() == Cell.CELL_TYPE_STRING && cell.getStringCellValue().startsWith("start_") && cell.getStringCellValue().contains("_freeform_") &&
	            		!cell.getStringCellValue().contains("_contextdata_")){
	            	String id = cell.getStringCellValue().substring(cell.getStringCellValue().indexOf("_")+1);
	            	logger.debug(id);
	            	
	            	mainSheet.removeRow(row);
	            	mainSheet.shiftRows(row.getRowNum()+1, mainSheet.getLastRowNum(), -1);
	            	int endIdRowNumber = getRowNumber(mainSheet, "end_"+id, row.getRowNum());
	            	Map rowMap = getCellInfo(mainSheet, row.getRowNum(), endIdRowNumber-1, new HashMap());
	            	map.put(id, rowMap);
	            	
	            	new ExcelGeneration().populateFreeform(ctx, rowMap, id, mainSheet, numericStyle, dateStyle, textStyle);
	            	rowNum = endIdRowNumber;
	            	i = rowNum-1;
	            	
	            	mainSheet.removeRow(mainSheet.getRow(endIdRowNumber));
	            	if(endIdRowNumber < mainSheet.getLastRowNum()){
	            		mainSheet.shiftRows(endIdRowNumber+1, mainSheet.getLastRowNum(), -1);
	            	}
	            }else if(cell.getCellType() == Cell.CELL_TYPE_STRING && cell.getStringCellValue().startsWith("start_") && cell.getStringCellValue().contains("_contextdata_")){
	            	String id = cell.getStringCellValue().substring(cell.getStringCellValue().indexOf("_")+1);
	            	logger.debug(id);
	            	
	            	mainSheet.removeRow(row);
	            	mainSheet.shiftRows(row.getRowNum()+1, mainSheet.getLastRowNum(), -1);
	            	int endIdRowNumber = getRowNumber(mainSheet, "end_"+id, row.getRowNum());
	            	Map rowMap = getCellInfo(mainSheet, row.getRowNum(), endIdRowNumber-1, new HashMap());
	            	map.put(id, rowMap);
	            	
	            	new ExcelGeneration().populateFreeformContextdata(ctx, rowMap, id, mainSheet, numericStyle, dateStyle, textStyle);
	            	rowNum = endIdRowNumber;
	            	i = rowNum-1;
	            	
	            	mainSheet.removeRow(mainSheet.getRow(endIdRowNumber));
	            	if(endIdRowNumber < mainSheet.getLastRowNum()){
	            		mainSheet.shiftRows(endIdRowNumber+1, mainSheet.getLastRowNum(), -1);
	            	}
	            }else if(cell.getCellType() == Cell.CELL_TYPE_STRING && cell.getStringCellValue().startsWith("start_") && cell.getStringCellValue().contains("_list_")){
	            	String id = cell.getStringCellValue().substring(cell.getStringCellValue().indexOf("_")+1);
	            	logger.debug(id);
	            	
	            	if(id.endsWith("_new")){
	            		id = id.substring(0, id.indexOf("_new"));
	            		
	            		int startRowNumber = 0;
		            	int endIdRowNumber = 0;
		            	
	            		/*mainSheet.removeRow(row);
		            	mainSheet.shiftRows(row.getRowNum()+1, mainSheet.getLastRowNum(), -1);*/
		            	
		            	//Added code if list have no header's, only have data
		            	int startIdRowNumber = row.getRowNum();
		            	Map listDataMap = getCellInfo(mainSheet, row.getRowNum(), startIdRowNumber, new HashMap());
		            	map.put(id, listDataMap);
		            	//Ended code
		            	
		            	endIdRowNumber = getRowNumber(mainSheet, "end_"+id, row.getRowNum());
		            	
		            	Map headerMap = getCellInfo(mainSheet, row.getRowNum()+1, endIdRowNumber-1, new HashMap());
		            	map.put(id, headerMap);
		            	
		            	endIdRowNumber = new ExcelGeneration().populateListNew(ctx, id, mainSheet, startIdRowNumber, row, endIdRowNumber, numericStyle, dateStyle, textStyle);
		            	endIdRowNumber = getRowNumber(mainSheet, "end_"+id, row.getRowNum());
		            	
		            	rowNum = endIdRowNumber;
		            	i = rowNum-1;
		            	
		            	if(mainSheet.getRow(endIdRowNumber) != null)
		            		mainSheet.removeRow(mainSheet.getRow(endIdRowNumber));
		            	
		            	if(endIdRowNumber < mainSheet.getLastRowNum()){
		            		mainSheet.shiftRows(endIdRowNumber+1, mainSheet.getLastRowNum(), -1);
		            	}
	            	}else{
		            	//if list is accordian list then headers row will be the part of data list not seperate headers map
		            	if(id.endsWith("_accordianreport")){
		            		int startRowNumber = 0;
			            	int endIdRowNumber = 0;
			            	if(ctx.get("isDynamicList") != null && ctx.get("isDynamicList").equals("Y")){
			            		//mainSheet.removeRow(mainSheet.getRow(0));
			            		
			            		startRowNumber = row.getRowNum();
			            		//skipping headers row here but needs to increment one row to fetch data list
			            		endIdRowNumber = startRowNumber + 1; 
			            		
			            		endIdRowNumber = createDynamicAccordianDataRow(ctx, startRowNumber, endIdRowNumber, id, mainSheet);
			            		
			            		rowNum = endIdRowNumber;
				            	i = rowNum-1;
			            		
				            	if(mainSheet.getWorkbook() instanceof HSSFWorkbook){
					            	if(endIdRowNumber < 65535)//remove last row only because it will have id end_list_1
					            		mainSheet.removeRow(mainSheet.getRow(endIdRowNumber+1));
				            	}else{
				            		mainSheet.removeRow(mainSheet.getRow(endIdRowNumber+1));
				            	}
				            	
				            	mainSheet.removeRow(row);
				            	mainSheet.shiftRows(row.getRowNum()+1, mainSheet.getLastRowNum(), -1);
				            }
		            	}else{
			            	int startRowNumber = 0;
			            	int endIdRowNumber = 0;
			            	if(ctx.get("isDynamicList") != null && ctx.get("isDynamicList").equals("Y")){
			            		startRowNumber = row.getRowNum();
			            		
			            		//mainSheet.removeRow(row);
			            		//mainSheet.shiftRows(row.getRowNum()+1, mainSheet.getLastRowNum(), -1);
			            		
			            		createDynamicHeaderRow(ctx, startRowNumber, endIdRowNumber-1, id, mainSheet);
				            	//mainSheet.shiftRows(row.getRowNum()+1, mainSheet.getLastRowNum(), -1);
			            		
			            		endIdRowNumber = startRowNumber + 1; //getRowNumber(mainSheet, "end_"+id, row.getRowNum());
			            		
			            		//startRowNumber++;
			            		endIdRowNumber = createDynamicDataRow(ctx, startRowNumber, endIdRowNumber, id, mainSheet);
			            		
			            		//endIdRowNumber = getRowNumber(mainSheet, "end_"+id, row.getRowNum());
			            		rowNum = endIdRowNumber;
				            	i = rowNum-1;
			            		
				            	if(mainSheet.getWorkbook() instanceof HSSFWorkbook){
					            	if(endIdRowNumber < 65535)//remove last row only because it will have id end_list_1
					            		if(mainSheet.getRow(endIdRowNumber+1) != null)
					            			mainSheet.removeRow(mainSheet.getRow(endIdRowNumber+1));
					            		else{
					            			if(mainSheet.getRow(endIdRowNumber) != null)
					            				mainSheet.removeRow(mainSheet.getRow(endIdRowNumber));
					            		}
				            	}else{
				            		if(mainSheet.getRow(endIdRowNumber+1) != null)	
				            			mainSheet.removeRow(mainSheet.getRow(endIdRowNumber+1));
				            		else{
				            			if(mainSheet.getRow(endIdRowNumber) != null)
				            				mainSheet.removeRow(mainSheet.getRow(endIdRowNumber));
				            		}
				            	}
				            	
			            		/*mainSheet.removeRow(mainSheet.getRow(endIdRowNumber));
			            		if(endIdRowNumber < mainSheet.getLastRowNum()){
				            		mainSheet.shiftRows(endIdRowNumber+1, mainSheet.getLastRowNum(), -1);
				            	}*/
			            	}else{
				            	mainSheet.removeRow(row);
				            	mainSheet.shiftRows(row.getRowNum()+1, mainSheet.getLastRowNum(), -1);
				            	
				            	//Added code if list have no header's, only have data
				            	int startIdRowNumber = row.getRowNum();
				            	Map listDataMap = getCellInfo(mainSheet, row.getRowNum(), startIdRowNumber, new HashMap());
				            	map.put(id, listDataMap);
				            	//Ended code
				            	
				            	endIdRowNumber = getRowNumber(mainSheet, "end_"+id, row.getRowNum());
				            	
				            	Map headerMap = getCellInfo(mainSheet, row.getRowNum()+1, endIdRowNumber-1, new HashMap());
				            	map.put(id, headerMap);
				            	
				            	endIdRowNumber = new ExcelGeneration().populateList(ctx, headerMap, id, mainSheet, startIdRowNumber, listDataMap, numericStyle, dateStyle, textStyle);
				            	endIdRowNumber = getRowNumber(mainSheet, "end_"+id, row.getRowNum());
				            	
				            	rowNum = endIdRowNumber;
				            	i = rowNum-1;
				            	
				            	if(mainSheet.getRow(endIdRowNumber) != null)
				            		mainSheet.removeRow(mainSheet.getRow(endIdRowNumber));
				            	
				            	if(endIdRowNumber < mainSheet.getLastRowNum()){
				            		mainSheet.shiftRows(endIdRowNumber+1, mainSheet.getLastRowNum(), -1);
				            	}
			            	}
		            	}
	            	}
	            }
	        }
		}
		
		return map;
	}
  
  private int getRowNumber(Sheet mainSheet, String id, int rowNum) throws Exception {
		int endIdRowNum = 0;
		for(int i=rowNum; i<=mainSheet.getLastRowNum(); i++){
			Row row = mainSheet.getRow(i);
	        if(row == null)
	        	continue;
	        
	        for(int j = 0; j<61; j++){            	
	        	Cell cell = row.getCell(j);
	        	if((cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK)){
	            	continue;
	            }
	        	if(cell.getCellType() == Cell.CELL_TYPE_STRING && cell.getStringCellValue().startsWith(id)){
	            	endIdRowNum = row.getRowNum();
	            }
	        }
	    }
		return endIdRowNum;
	}
  
  public Map getCellInfo(Sheet mainSheet, int rowNum, int endIdRowNum, Map cellInfo)
    throws Exception
  {
    String setValue = null;
    for (int i = rowNum; i <= endIdRowNum; i++)
    {
      Row row = mainSheet.getRow(i);
      if (row != null) {
        for (int j = 0; j < 150; j++)
        {
          Cell cell = row.getCell(j);
          if ((cell != null) && (cell.getCellType() != 3)) {
            if (1 == cell.getCellType())
            {
              if (cell != null) {
                switch (cell.getCellType())
                {
                case 0: 
                  double setvalue = cell.getNumericCellValue();
                  setValue = Double.toString(setvalue);
                  break;
                case 1: 
                  setValue = cell.getStringCellValue();
                }
              }
              if ((setValue != null) && (setValue.startsWith(":")))
              {
                String Value = setValue.replace(":", "");
                if (!cellInfo.containsKey(Value)) {
                  cellInfo.put(Value, new RowColumnBean(i, j, cell));
                }
              }
              if ((setValue != null) && (setValue.startsWith("FORMULA="))) {
                cellInfo.put(setValue, new RowColumnBean(i, j, cell));
              }
            }
            else if (2 == cell.getCellType())
            {
              cellInfo.put(cell.getCellFormula(), new RowColumnBean(i, j, cell));
            }
          }
        }
      }
    }
    return cellInfo;
  }
  
  public void createDynamicHeaderRow(Context ctx, int startRowNumber, int endIdRowNumber, String id, Sheet mainSheet) throws Exception {
		if(ctx.get(id+"_headers") != null && ctx.get(id+"_headers") instanceof LinkedHashMap){
			LinkedHashMap headerMap = (LinkedHashMap)ctx.get(id+"_headers");
			Set keysSet = headerMap.keySet();
			Iterator itr = keysSet.iterator();
			Row headerRow = mainSheet.createRow(startRowNumber);
			int i = 0;
			while(itr.hasNext()){
				String key = itr.next().toString();
				if(headerMap.get(key) != null && headerMap.get(key).toString().equals("HIDDEN"))
					continue;
				
				Cell cell = headerRow.createCell(i);
				
				cell.setCellValue(headerMap.get(key).toString().replaceAll("&amp;", "&"));
				
				CellStyle style = (CellStyle)mainSheet.getWorkbook().createCellStyle();
				Font font = (Font)mainSheet.getWorkbook().createFont();
				font.setBoldweight(Font.BOLDWEIGHT_BOLD);
				style.setFont(font);
				style.setWrapText(true);
				
				//added to handle text alignment
				if(key.toLowerCase().contains("_textalign")){
					String align = key.substring(key.indexOf("_textalign")+11, key.length());
					//key = key.substring(0, key.lastIndexOf("_textalign"));
					
					short algn = -1;
					if(align != null){
						if(align.equalsIgnoreCase("right"))
							algn = CellStyle.ALIGN_RIGHT;
						else if(align.equalsIgnoreCase("left"))
							algn = CellStyle.ALIGN_LEFT;
						else if(align.equalsIgnoreCase("center"))
							algn = CellStyle.ALIGN_CENTER;
						
						if(algn > 0)
							style.setAlignment(algn);
					}
				}
				
				//Added code to apply currency format on cell value based on field type in metadata.xml
				try{
					if(!key.contains("Grand Total") && MetaDataResources.getInstance(ctx).getField(key) != null && 
							MetaDataResources.getInstance(ctx).getField(key).getType().equals("N")){
						style.setAlignment(CellStyle.ALIGN_RIGHT);
					}
					
					if(!key.contains("Grand Total") && MetaDataResources.getInstance(ctx).getField(key) != null &&
							MetaDataResources.getInstance(ctx).getField(key).getType().equals("D")){
						style.setAlignment(CellStyle.ALIGN_CENTER);
					}
				}catch (Exception e) {
					logger.error("Error in ExcelDatabaseMapping.createDynamicHeaderRow", e);
					// TODO: handle exception
				}
				//Ended code
				
				mainSheet.setColumnWidth(i,(short)5000);
				cell.setCellStyle(style);
				i++;
			}
		}
	}
  
  private int createDynamicDataRow(Context ctx, int startIdRowNumber, int endIdRowNumber, String id, Sheet mainSheet) throws Exception {
		if(ctx.get(id) != null && ctx.get(id) instanceof List){
			List dataList = (List)ctx.get(id);
			
			if(dataList.size() != 0){
				int rowsToBeCreated = (startIdRowNumber+1) + dataList.size();
				
				if(mainSheet.getWorkbook() instanceof HSSFWorkbook){
					if(rowsToBeCreated >= 65535){
						rowsToBeCreated = 65535 - ((startIdRowNumber+1) + mainSheet.getLastRowNum());
						mainSheet.shiftRows(startIdRowNumber+1, mainSheet.getLastRowNum(), rowsToBeCreated);
					}else{
						if(((startIdRowNumber+1) + mainSheet.getLastRowNum() + dataList.size()) > 65535)
							rowsToBeCreated = 65535 - ((startIdRowNumber+1) + mainSheet.getLastRowNum());
						
						mainSheet.shiftRows(startIdRowNumber+1, mainSheet.getLastRowNum(), rowsToBeCreated);
					}
				}else{
					mainSheet.shiftRows(startIdRowNumber+1, mainSheet.getLastRowNum(), rowsToBeCreated);
				}
			}
			
			Font font = (Font)mainSheet.getWorkbook().createFont();
			CellStyle style = mainSheet.getWorkbook().createCellStyle();
			
			for(int i=0; i<dataList.size(); i++){
				Map dataMap = (Map)dataList.get(i);
				
				if(mainSheet.getWorkbook() instanceof HSSFWorkbook){
					if(i>=65535)
						break;
				}
				
				Row dataRow = mainSheet.createRow(endIdRowNumber);
				
				//HSSFFont font = (HSSFFont)mainSheet.getWorkbook().createFont();
				
				if(ctx.get(id+"_headers") != null && ctx.get(id+"_headers") instanceof LinkedHashMap){
					LinkedHashMap headerMap = (LinkedHashMap)ctx.get(id+"_headers");
					Set keysSet = headerMap.keySet();
					Iterator itr = keysSet.iterator();
					int j = 0;
					boolean isGrandTotalRow = false;
					while(itr.hasNext()){
						Cell cell = dataRow.createCell(j);
						String key = itr.next().toString();
						cell.getCellStyle().setWrapText(true);
						//CellStyle style = mainSheet.getWorkbook().createCellStyle();
						
						if(key.toLowerCase().contains("_textalign")){
							String align = key.substring(key.lastIndexOf("_textalign")+11, key.length());
							key = key.substring(0, key.indexOf("_textalign"));
							
							short algn = -1;
							if(align != null){
								if(align.equalsIgnoreCase("left"))
									algn = CellStyle.ALIGN_LEFT;
								else if(align.equalsIgnoreCase("right"))
									algn = CellStyle.ALIGN_RIGHT;
								else if(align.equalsIgnoreCase("center"))
									algn = CellStyle.ALIGN_CENTER;
								
								if(algn > 0){
									style.setAlignment(algn);
									cell.setCellStyle(style);
								}
							}
						}
						
						//Added code to apply alignment format on cell value based on field type in metadata.xml
						try{
							if(!key.contains("Grand Total") && MetaDataResources.getInstance(ctx).getField(key) != null && 
									MetaDataResources.getInstance(ctx).getField(key).getType().equals("D")){
								style.setAlignment(CellStyle.ALIGN_CENTER);
								style.setDataFormat((short)0xe);
								cell.setCellStyle(style);
							}
						}catch (Exception e) {
							logger.error("Error in ExcelDatabaseMapping.createDynamicDataRow", e);
							// TODO: handle exception
						}
						
						String value = null;
						
						//going to check for encrypt value
						if(key.contains("_encrypt")){
							String encryptKey = key.substring(key.lastIndexOf("_encrypt")+9, key.length());
							
							key = key.substring(0, key.indexOf("_encrypt"));
							
							value = dataMap.get(key) != null ? dataMap.get(key).toString().replaceAll("&amp;", "&") : "";
							
							try{
								value = new TripleDESEncryptionDecryption().decrypt(value.toString());
								
								int accesstye = SecurityResources.getInstance(ctx).getAccessType(encryptKey, ctx);
								if(accesstye == SecurityResources.HIDE){
									String newValue = HtmlConstants.EMPTY;
									for(int k=0; k<value.length(); k++){
										newValue = newValue + "X";
									}
									
									value = newValue;
								}
								
								RuleImpl ruleImpl = RulesResources.getInstance(ctx).findRule(encryptKey);
								if(ruleImpl != null && !ruleImpl.evaluate(ctx, null)){
									String newValue = HtmlConstants.EMPTY;
									for(int k=0; k<value.length(); k++){
										newValue = newValue + "X";
									}
									
									value = newValue;
								}
							}catch (Exception e) {
								logger.error("Unable to decrypt " +key+ " due to error : " + e.getMessage(), e);
							}
						}else{
							value = dataMap.get(key) != null ? dataMap.get(key).toString().replaceAll("&amp;", "&") : "";
						}
						
						//Added code to apply currency format on cell value based on field type in metadata.xml
						try{
							if(!value.contains("Grand Total") && MetaDataResources.getInstance(ctx).getField(key) != null && 
									MetaDataResources.getInstance(ctx).getField(key).getType().equals("N")){
								value = DataUtils.removeAmountFormat(value) != null ? DataUtils.removeAmountFormat(value).toString() : value;
								
								//cell.getCellStyle().setDataFormat((short)8);
								style.setDataFormat((short)8);
								cell.setCellStyle(style);
								cell.setCellType(Cell.CELL_TYPE_NUMERIC);
								try{
									cell.setCellValue(Double.parseDouble(value.toString()));
								}catch (Exception e) {
									logger.error("Error in ExcelDatabaseMapping.createDynamicDataRow", e);
									cell.setCellValue(value.toString());
								}
								
								mainSheet.setColumnWidth(j,(short)5000);
								j++;
								
								continue;
							}
						}catch (Exception e) {
							logger.error("Error in ExcelDatabaseMapping.createDynamicDataRow", e);
							// TODO: handle exception
						}
						//Ended code
						
						/*if(value != null && value.contains("$")){
							value = DataUtils.removeAmountFormat(value) != null ? DataUtils.removeAmountFormat(value).toString() : value;
							//cell.setCellType(HSSFCell.CELL_TYPE_STRING);
							cell.getCellStyle().setDataFormat((short)7);
							cell.setCellType(Cell.CELL_TYPE_NUMERIC);
						}else{
							cell.getCellStyle().setDataFormat((short)7);
							cell.setCellType(Cell.CELL_TYPE_NUMERIC);
						}*/
						
						cell.setCellValue(value);
						
						//style.setWrapText(true);
						mainSheet.setColumnWidth(j,(short)5000);
						
						if(value.contains("Grand Total")){
							isGrandTotalRow = true;
						}
						
						//font.setColor(HSSFFont.COLOR_RED);
						
						if(isGrandTotalRow){
							font.setBoldweight(Font.BOLDWEIGHT_BOLD);
							style.setFont(font);
							cell.setCellStyle(style);
						}

						//style.setFont(font);

						//cell.setCellStyle(style);
						j++;
					}
				}
				
				endIdRowNumber++;
			}
		}
		
		return endIdRowNumber;
	}
  
//Method created to create accordian report having all headers row included in data list
	private int createDynamicAccordianDataRow(Context ctx, int startIdRowNumber, int endIdRowNumber, String id, Sheet mainSheet) throws Exception {
		if(ctx.get(id) != null && ctx.get(id) instanceof List){
			List dataList = (List)ctx.get(id);
			
			if(dataList.size() != 0){
				int rowsToBeCreated = (startIdRowNumber+1) + dataList.size();
				
				if(mainSheet.getWorkbook() instanceof HSSFWorkbook){
					if(rowsToBeCreated >= 65535){
						rowsToBeCreated = 65535 - ((startIdRowNumber+1) + mainSheet.getLastRowNum());
						mainSheet.shiftRows(startIdRowNumber+1, mainSheet.getLastRowNum(), rowsToBeCreated);
					}else{
						if(((startIdRowNumber+1) + mainSheet.getLastRowNum() + dataList.size()) > 65535)
							rowsToBeCreated = 65535 - ((startIdRowNumber+1) + mainSheet.getLastRowNum());
						
						mainSheet.shiftRows(startIdRowNumber+1, mainSheet.getLastRowNum(), rowsToBeCreated);
					}
				}else{
					mainSheet.shiftRows(startIdRowNumber+1, mainSheet.getLastRowNum(), rowsToBeCreated);
				}
			}
			
			Font font = (Font)mainSheet.getWorkbook().createFont();
			CellStyle style = mainSheet.getWorkbook().createCellStyle();
			//mainSheet.autoSizeColumn(0);
			
			for(int i=0; i<dataList.size(); i++){
				LinkedHashMap dataMap = (LinkedHashMap)dataList.get(i);
			
				if(mainSheet.getWorkbook() instanceof HSSFWorkbook){
					if(i>=65535)
						break;
				}
				
				Row dataRow = mainSheet.createRow(endIdRowNumber);
				
				//going to set bold formatting for header data row
				Set keysSet = dataMap.keySet();
				Iterator itr = keysSet.iterator();
				if(itr != null && itr.next().toString().endsWith("_header")){
					createDynamicAccordianHeaderRow(ctx, mainSheet, endIdRowNumber, dataMap);
					endIdRowNumber++;
					continue;
				}
				
				keysSet = dataMap.keySet();
				itr = keysSet.iterator();
				int j = 0;
				boolean isGrandTotalRow = false;
				while(itr.hasNext()){
					String key = itr.next().toString();
					
					if(key.equals("indent"))
						continue;
					
					int indent = 0;
					if(dataMap.get("indent") != null && !dataMap.get("indent").toString().equals(HtmlConstants.EMPTY)){
						indent = Integer.parseInt(dataMap.get("indent").toString());
					}
					
					Cell cell = null;
					
					if(indent > 0)
						cell = dataRow.createCell(j+indent);
					else
						cell = dataRow.createCell(j);
					
					//CellStyle style = cell.getCellStyle();
					cell.getCellStyle().setWrapText(true);
					
					if(key.toLowerCase().contains("_textalign")){
						String align = key.substring(key.lastIndexOf("_textalign")+11, key.length());
						key = key.substring(0, key.indexOf("_textalign"));
							
						short algn = -1;
						if(align != null){
							if(align.equalsIgnoreCase("left"))
								algn = CellStyle.ALIGN_LEFT;
							else if(align.equalsIgnoreCase("right"))
								algn = CellStyle.ALIGN_RIGHT;
							else if(align.equalsIgnoreCase("center"))
								algn = CellStyle.ALIGN_CENTER;
								
							if(algn > 0){
								style.setAlignment(algn);
								cell.setCellStyle(style);
							}
						}
					}
						
					//Added code to apply alignment format on cell value based on field type in metadata.xml
					try{
						if(!key.contains("Grand Total") && MetaDataResources.getInstance(ctx).getField(key) != null && 
								MetaDataResources.getInstance(ctx).getField(key).getType().equals("D")){
							style.setAlignment(CellStyle.ALIGN_CENTER);
							cell.setCellStyle(style);
						}
					}catch (Exception e) {
						logger.error("Error in ExcelDatabaseMapping.createDynamicAccordianDataRow", e);
						// TODO: handle exception
					}
						
					String value = null;
						
					//going to check for encrypt value
					if(key.contains("_encrypt")){
						String encryptKey = key.substring(key.lastIndexOf("_encrypt")+9, key.length());
							
						key = key.substring(0, key.indexOf("_encrypt"));
							
						value = dataMap.get(key) != null ? dataMap.get(key).toString().replaceAll("&amp;", "&") : "";
							
						try{
							value = new TripleDESEncryptionDecryption().decrypt(value.toString());
								
							int accesstye = SecurityResources.getInstance(ctx).getAccessType(encryptKey, ctx);
							if(accesstye == SecurityResources.HIDE){
								String newValue = HtmlConstants.EMPTY;
								for(int k=0; k<value.length(); k++){
									newValue = newValue + "X";
								}
								
								value = newValue;
							}
								
							RuleImpl ruleImpl = RulesResources.getInstance(ctx).findRule(encryptKey);
							if(ruleImpl != null && !ruleImpl.evaluate(ctx, null)){
								String newValue = HtmlConstants.EMPTY;
								for(int k=0; k<value.length(); k++){
									newValue = newValue + "X";
								}
									
								value = newValue;
							}
						}catch (Exception e) {
							logger.error("Unable to decrypt " +key+ " due to error : " + e.getMessage(), e);
						}
					}else{
						value = dataMap.get(key) != null ? dataMap.get(key).toString().replaceAll("&amp;", "&") : "";
					}
						
					//Added code to apply currency format on cell value based on field type in metadata.xml
					try{
						if(!value.contains("Grand Total") && MetaDataResources.getInstance(ctx).getField(key) != null && 
								MetaDataResources.getInstance(ctx).getField(key).getType().equals("N")){
							value = DataUtils.removeAmountFormat(value) != null ? DataUtils.removeAmountFormat(value).toString() : value;
							
							cell.getCellStyle().setDataFormat((short)8);
							cell.setCellType(Cell.CELL_TYPE_NUMERIC);
							try{
								cell.setCellValue(Double.parseDouble(value.toString()));
							}catch (Exception e) {
								logger.error("Error in ExcelDatabaseMapping.createDynamicAccordianDataRow", e);
								cell.setCellValue(value.toString());
							}
								
							mainSheet.setColumnWidth(j,(short)5000);
							j++;
								
							continue;
						}
					}catch (Exception e) {
						logger.error("Error in ExcelDatabaseMapping.createDynamicAccordianDataRow", e);
						// TODO: handle exception
					}
					//Ended code
						
					cell.setCellValue(value);
						
					mainSheet.setColumnWidth(j,(short)5000);
						
					if(value.contains("Grand Total")){
						isGrandTotalRow = true;
					}
						
					if(isGrandTotalRow){
						font.setBoldweight(Font.BOLDWEIGHT_BOLD);
						style.setFont(font);
						cell.setCellStyle(style);
					}

					j++;
				}
				
				endIdRowNumber++;
			}
		}
		
		return endIdRowNumber; 
	}
	
	private void createDynamicAccordianHeaderRow(Context ctx, Sheet mainSheet, int startRowNumber, LinkedHashMap dataMap) throws Exception {
		LinkedHashMap headerMap = (LinkedHashMap)dataMap;
		Set keysSet = headerMap.keySet();
		Iterator itr = keysSet.iterator();
		
		Row headerRow = mainSheet.createRow(startRowNumber);
		
		int indent = 0;
		if(dataMap.get("indent") != null && !dataMap.get("indent").toString().equals(HtmlConstants.EMPTY)){
			indent = Integer.parseInt(dataMap.get("indent").toString());
		}
		
		int i = 0;
		while(itr.hasNext()){
			String key = itr.next().toString();
			
			if(key.equals("indent"))
				continue;
			
			Cell cell = null;
			
			if(indent > 0){
				cell = headerRow.createCell(i+indent);
			}else{
				cell = headerRow.createCell(i);
			}
			
			cell.setCellValue(headerMap.get(key).toString().replaceAll("&amp;", "&"));
			
			CellStyle style = (CellStyle)mainSheet.getWorkbook().createCellStyle();
			Font font = (Font)mainSheet.getWorkbook().createFont();
			font.setBoldweight(Font.BOLDWEIGHT_BOLD);
			style.setFont(font);
			style.setWrapText(true);
			
			key = key.substring(0, key.indexOf("_header"));
			
			//added to handle text alignment
			if(key.toLowerCase().contains("_textalign")){
				String align = key.substring(key.indexOf("_textalign")+11, key.length());
				//key = key.substring(0, key.lastIndexOf("_textalign"));
				
				short algn = -1;
				if(align != null){
					if(align.equalsIgnoreCase("right"))
						algn = CellStyle.ALIGN_RIGHT;
					else if(align.equalsIgnoreCase("left"))
						algn = CellStyle.ALIGN_LEFT;
					else if(align.equalsIgnoreCase("center"))
						algn = CellStyle.ALIGN_CENTER;
					
					if(algn > 0)
						style.setAlignment(algn);
				}
			}
			
			//Added code to apply currency format on cell value based on field type in metadata.xml
			try{
				if(!key.contains("Grand Total") && MetaDataResources.getInstance(ctx).getField(key) != null && 
						MetaDataResources.getInstance(ctx).getField(key).getType().equals("N")){
					style.setAlignment(CellStyle.ALIGN_RIGHT);
				}
				
				if(!key.contains("Grand Total") && MetaDataResources.getInstance(ctx).getField(key) != null && 
						MetaDataResources.getInstance(ctx).getField(key).getType().equals("D")){
					style.setAlignment(CellStyle.ALIGN_CENTER);
				}
			}catch (Exception e) {
				logger.error("Error in ExcelDatabaseMapping.createDynamicAccordianHeaderRow", e);
				// TODO: handle exception
			}
			//Ended code
			
			mainSheet.setColumnWidth(i,(short)5000);
			cell.setCellStyle(style);
			i++;
		}
	}
	
	//Method created to create all records coming from database
	public void createDynamicDataRow(Context ctx, String id, Sheet mainSheet, CellStyle numericStyle, CellStyle dateStyle, CellStyle textStyle) throws Exception {
		if(ctx.get(id) != null && ctx.get(id) instanceof List){
			List dataList = (List)ctx.get(id);
			
			Font font = (Font)mainSheet.getWorkbook().createFont();
			//CellStyle style = mainSheet.getWorkbook().createCellStyle();
			
			for(int i=0; i<dataList.size(); i++){
				Map dataMap = (Map)dataList.get(i);
				
				if(mainSheet.getWorkbook() instanceof HSSFWorkbook){
					if(i>=65535)
						break;
				}
				
				Row dataRow = mainSheet.createRow(mainSheet.getLastRowNum()+1);
				
				if(ctx.get(id+"_headers") != null && ctx.get(id+"_headers") instanceof LinkedHashMap){
					LinkedHashMap headerMap = (LinkedHashMap)ctx.get(id+"_headers");
					Set keysSet = headerMap.keySet();
					Iterator itr = keysSet.iterator();
					int j = 0;
					boolean isGrandTotalRow = false;
					while(itr.hasNext()){
						Cell cell = dataRow.createCell(j);
						String key = itr.next().toString();
						if(headerMap.get(key) != null && headerMap.get(key).toString().equals("HIDDEN"))
							continue;
						
						cell.getCellStyle().setWrapText(true);
						
						if(key.toLowerCase().contains("_textalign")){
							String align = key.substring(key.lastIndexOf("_textalign")+11, key.length());
							key = key.substring(0, key.indexOf("_textalign"));
							
							short algn = -1;
							if(align != null){
								if(align.equalsIgnoreCase("left"))
									algn = CellStyle.ALIGN_LEFT;
								else if(align.equalsIgnoreCase("right"))
									algn = CellStyle.ALIGN_RIGHT;
								else if(align.equalsIgnoreCase("center"))
									algn = CellStyle.ALIGN_CENTER;
								
								if(algn > 0){
									cell.getCellStyle().setAlignment(algn);
									//cell.setCellStyle(style);
								}
							}
						}
						
						String value = null;
						
						//going to check for encrypt value
						if(key.contains("_encrypt")){
							String encryptKey = key.substring(key.lastIndexOf("_encrypt")+9, key.length());
							
							key = key.substring(0, key.indexOf("_encrypt"));
							
							value = dataMap.get(key) != null ? dataMap.get(key).toString().replaceAll("&amp;", "&") : "";
							
							try{
								value = new TripleDESEncryptionDecryption().decrypt(value.toString());
								
								int accesstye = SecurityResources.getInstance(ctx).getAccessType(encryptKey, ctx);
								if(accesstye == SecurityResources.HIDE){
									String newValue = HtmlConstants.EMPTY;
									for(int k=0; k<value.length(); k++){
										newValue = newValue + "X";
									}
									
									value = newValue;
								}
								
								RuleImpl ruleImpl = RulesResources.getInstance(ctx).findRule(encryptKey);
								if(ruleImpl != null && !ruleImpl.evaluate(ctx, null)){
									String newValue = HtmlConstants.EMPTY;
									for(int k=0; k<value.length(); k++){
										newValue = newValue + "X";
									}
									
									value = newValue;
								}
							}catch (Exception e) {
								logger.error("Unable to decrypt " +key+ " due to error : " + e.getMessage(), e);
							}
						}else{
							value = dataMap.get(key) != null ? dataMap.get(key).toString().replaceAll("&amp;", "&") : "";
						}
						
						//Added code to apply alignment format on cell value based on field type in metadata.xml
						try{
							if(!key.contains("Grand Total") && MetaDataResources.getInstance(ctx).getField(key) != null && 
									MetaDataResources.getInstance(ctx).getField(key).getType().equals("D")){
								/*style.setAlignment(CellStyle.ALIGN_CENTER);
								style.setDataFormat((short)0xe);
								cell.setCellStyle(style);*/
								cell.setCellValue(value);
								
								try{
									cell.setCellStyle(dateStyle);
								}catch (Exception e) {
									logger.error("Error in ExcelDatabaseMapping.createDynamicDataRow", e);
									cell.setCellStyle(textStyle);
								}
								
								cell.setCellType(Cell.CELL_TYPE_STRING);
								mainSheet.setColumnWidth(j,(short)5000);
								j++;
								
								continue;
							}
						}catch (Exception e) {
							logger.error("Error in ExcelDatabaseMapping.createDynamicDataRow", e);
							// TODO: handle exception
						}
						
						//Added code to apply currency format on cell value based on field type in metadata.xml
						try{
							if(!value.contains("Grand Total") && MetaDataResources.getInstance(ctx).getField(key) != null && 
									MetaDataResources.getInstance(ctx).getField(key).getType().equals("N")){
								value = DataUtils.removeAmountFormat(value) != null ? DataUtils.removeAmountFormat(value).toString() : value;
								
								if(StringUtils.isBlank(value))
									value = "0";
								
								if(value != null && value.startsWith("-") && value.endsWith("%"))
									value = "0";
								
								//cell.getCellStyle().setDataFormat((short)8);
								/*style.setDataFormat((short)8);
								cell.setCellStyle(style);*/
								
								cell.setCellStyle(numericStyle);
								cell.setCellType(Cell.CELL_TYPE_NUMERIC);
								
								try{
									cell.setCellValue(Double.parseDouble(value.toString()));
								}catch (Exception e) {
									logger.error("Error in ExcelDatabaseMapping.createDynamicDataRow", e);
									cell.setCellValue(value.toString());
								}
								
								mainSheet.setColumnWidth(j,(short)5000);
								j++;
								
								continue;
							}
						}catch (Exception e) {
							logger.error("Error in ExcelDatabaseMapping.createDynamicDataRow", e);
							// TODO: handle exception
							//logger.error("Error in setting numeric cell : " + key + " with value : " + value + " : " + e.getMessage());
						}
						//Ended code
						
						//Added code to apply number format on cell based on field type in metadata.xml
						try{
							if(!value.contains("Grand Total") && MetaDataResources.getInstance(ctx).getField(key) != null && 
									MetaDataResources.getInstance(ctx).getField(key).getType().equals("I")){
								
								try{
									if(StringUtils.isNotBlank(value.toString()) && !value.toString().equals(HtmlConstants.EMPTY)){
										cell.setCellValue(Integer.parseInt(value.toString()));
									}
								}catch (Exception e) {
									logger.error("Error in ExcelDatabaseMapping.createDynamicDataRow", e);
									// TODO: handle exception
								}
								
								mainSheet.setColumnWidth(j,(short)5000);
								j++;
								
								continue;
							}
						}catch (Exception e) {
							logger.error("Error in ExcelDatabaseMapping.createDynamicDataRow", e);
							// TODO: handle exception
						}
						//Ended code
						
						/*style.setDataFormat((short)0x31);
						cell.setCellStyle(style);*/
						cell.setCellStyle(textStyle);
						try{
							cell.setCellType(Cell.CELL_TYPE_STRING);
						}catch (Exception e) {
							logger.error("Error in ExcelDatabaseMapping.createDynamicDataRow", e);
							//logger.error("Error in setting cell : " + key + " with value : " + value + " : " + e.getMessage());
							cell.setCellType(Cell.CELL_TYPE_BLANK);
						}
						
						cell.setCellValue(value);
						
						mainSheet.setColumnWidth(j, (short)5000);
						
						if(value.contains("Grand Total")){
							isGrandTotalRow = true;
						}
						
						if(isGrandTotalRow){
							font.setBoldweight(Font.BOLDWEIGHT_BOLD);
							/*style.setFont(font);
							cell.setCellStyle(style);*/
							cell.getCellStyle().setFont(font);
						}

						j++;
					}
				}
			}
		}
		
		return;
	}
	
	//Method created to get header row cells data
	public Map getRowHeaderCellInfo(Sheet mainSheet, int rowNum, int endIdRowNum, Map cellInfo) throws Exception {
		    String setValue = null;
		    for (int i = rowNum; i <= endIdRowNum; i++)
		    {
		      Row row = mainSheet.getRow(i);
		      if (row != null) {
		        for (int j = 0; j < 61; j++)
		        {
		          Cell cell = row.getCell(j);
		          if ((cell != null) && (cell.getCellType() != 3)) {
		            if (1 == cell.getCellType())
		            {
		              if (cell != null) {
		                switch (cell.getCellType())
		                {
		                case 0: 
		                  double setvalue = cell.getNumericCellValue();
		                  setValue = Double.toString(setvalue);
		                  break;
		                case 1: 
		                  setValue = cell.getStringCellValue();
		                }
		              }
		              if (setValue != null && !setValue.contains(":")) {
		                cellInfo.put(setValue, cell);
		              }
		            }
		            else if (2 == cell.getCellType())
		            {
		              cellInfo.put(cell.getCellFormula(), cell);
		            }
		          }
		        }
		      }
		    }
		    return cellInfo;
	}
	
	//Method created to get index for repeat schema for a specific id
	public int getCellIndexForRepeatSchemasById(Sheet mainSheet, String startEndId, String id, int innerstartIdRowNumber, int endIdRowNumber){
		while(innerstartIdRowNumber <= endIdRowNumber){
			if(mainSheet.getRow(innerstartIdRowNumber) != null){
				Iterator itr = mainSheet.getRow(innerstartIdRowNumber).cellIterator();
				while(itr.hasNext()){
					Cell cell = (Cell)itr.next();
					
					if(cell.getStringCellValue().startsWith(startEndId) &&
							cell.getStringCellValue().contains(id)){
						return cell.getColumnIndex();
					}
				}
			}
			
			innerstartIdRowNumber++;
		}
		
		return 0;
	}
	
	//Method created to populate repeat contextdatalist
	private void populateRepeatContextdataListOld(Context ctx, String id, Sheet mainSheet, Row row, int endIdRowNumber, Map map,
			CellStyle numericStyle, CellStyle dateStyle, CellStyle textStyle) throws Exception{
		
		int repeatCellIndex = getCellIndexForRepeatSchemasById(mainSheet, "start_", "_repeatcontextdatalist_", row.getRowNum(), endIdRowNumber);
    	
    	mainSheet.removeRow(row);
    	mainSheet.shiftRows(row.getRowNum()+1, mainSheet.getLastRowNum(), -1);
    	
    	int startIdRowNumber = row.getRowNum();
    	
    	//Added code if list have no header's, only have data
    	Map listDataMap = getCellInfo(mainSheet, row.getRowNum(), startIdRowNumber, new HashMap());
    	map.put(id, listDataMap);
    	//Ended code
    	
    	//going to get contextdata and list rows schema only
    	LinkedHashMap repeatContextdataListMap = new LinkedHashMap();
    	int innerstartIdRowNumber = startIdRowNumber;
    	int count = 0;
    	
    	innerstartIdRowNumber = startIdRowNumber;
    	while(innerstartIdRowNumber <= endIdRowNumber){
    		if(mainSheet.getRow(innerstartIdRowNumber) != null && mainSheet.getRow(innerstartIdRowNumber).getCell(repeatCellIndex) != null &&
    				mainSheet.getRow(innerstartIdRowNumber).getCell(repeatCellIndex).getStringCellValue().startsWith("end_") &&
    				mainSheet.getRow(innerstartIdRowNumber).getCell(repeatCellIndex).getStringCellValue().contains("_repeatcontextdatalist_")){
    			break;
    		}
    		
    		if(mainSheet.getRow(innerstartIdRowNumber) != null && mainSheet.getRow(innerstartIdRowNumber).getCell(repeatCellIndex) != null &&
    				mainSheet.getRow(innerstartIdRowNumber).getCell(repeatCellIndex).getStringCellValue().startsWith("start_") &&
    				(mainSheet.getRow(innerstartIdRowNumber).getCell(repeatCellIndex).getStringCellValue().contains("_repeatcontextdata_") ||
    					mainSheet.getRow(innerstartIdRowNumber).getCell(repeatCellIndex).getStringCellValue().contains("_contextdata_"))){
    			
    			
    			innerstartIdRowNumber = getRepeatContextdataRowsSchema(mainSheet, innerstartIdRowNumber, endIdRowNumber, repeatContextdataListMap, mainSheet.getRow(innerstartIdRowNumber).getCell(repeatCellIndex).getStringCellValue(), repeatCellIndex);
    		}else if(mainSheet.getRow(innerstartIdRowNumber) != null && mainSheet.getRow(innerstartIdRowNumber).getCell(repeatCellIndex) != null &&
    				mainSheet.getRow(innerstartIdRowNumber).getCell(repeatCellIndex).getStringCellValue().startsWith("start_") &&
    				(mainSheet.getRow(innerstartIdRowNumber).getCell(repeatCellIndex).getStringCellValue().contains("_repeatlist_") ||
    					mainSheet.getRow(innerstartIdRowNumber).getCell(repeatCellIndex).getStringCellValue().contains("_list_"))){
    			
    			innerstartIdRowNumber = getRepeatListRowsSchema(mainSheet, innerstartIdRowNumber, endIdRowNumber, repeatContextdataListMap, mainSheet.getRow(innerstartIdRowNumber).getCell(repeatCellIndex).getStringCellValue(), repeatCellIndex);
    		}else
    			innerstartIdRowNumber++;
    	}
    	
    	//System.out.println(repeatContextdataListMap);
    	
    	//removing existing repeatcontextdatalist rows because we will create dynamically these rows based on repeatContextdataListMap
    	innerstartIdRowNumber = startIdRowNumber;
    	while(innerstartIdRowNumber < endIdRowNumber){
    		if(mainSheet.getRow(startIdRowNumber) != null)
        		mainSheet.removeRow(mainSheet.getRow(startIdRowNumber));
        	
        	if(startIdRowNumber < mainSheet.getLastRowNum()){
        		mainSheet.shiftRows(startIdRowNumber+1, mainSheet.getLastRowNum(), -1);
        	}
    		
    		innerstartIdRowNumber++;
    	}
    	
    	innerstartIdRowNumber = startIdRowNumber;
    	
    	int repeatCount = 0;
    	int maxRepeatCount = 10;
    	if(ctx.get(id+"_repeatcount") != null && !ctx.get(id+"_repeatcount").toString().equals(HtmlConstants.EMPTY)){
    		maxRepeatCount = Integer.parseInt(ctx.get(id+"_repeatcount").toString());
    	}
    	
    	while(repeatCount < maxRepeatCount){
    		Set repeatContextdataListMapSet = repeatContextdataListMap.entrySet();
    		Iterator repeatContextdataListMapSetItr = repeatContextdataListMapSet.iterator();
    		while(repeatContextdataListMapSetItr.hasNext()){
    			Map.Entry entry = (Map.Entry)repeatContextdataListMapSetItr.next();
    			
    			//System.out.println(entry);
    			
    			if(entry.getKey() == null)
    				continue;
    			
    			if(entry.getKey().toString().contains("_contextdata_") || entry.getKey().toString().contains("_repeatcontextdata_")){
    				String newId = entry.getKey().toString();
    				newId = newId.substring(newId.indexOf("start_")+6, newId.length());
    				
    				if(ctx.get(newId+"_"+repeatCount) == null)
    					continue;
    				
    				Context localCtx = new Context();
    				if(ctx.get(newId+"_"+repeatCount) != null)
    					localCtx.putAll((Map)ctx.get(newId+"_"+repeatCount));
    				
    				//going to get rows defined in contextdata rowset
    				LinkedHashMap rowMap = (LinkedHashMap)entry.getValue();
    				Set rowMapSet = rowMap.keySet();
    				Iterator rowMapSetItr = rowMapSet.iterator();
    				while(rowMapSetItr.hasNext()){
    					String rowId = (String)rowMapSetItr.next();
    					
    					//creating new row here
    					row = mainSheet.createRow(innerstartIdRowNumber);
    					mainSheet.shiftRows(innerstartIdRowNumber, mainSheet.getLastRowNum(), 1);
    					
    					LinkedHashMap rowMapCellsMap = (LinkedHashMap)rowMap.get(rowId);
    					Set rowMapCellsMapSet = rowMapCellsMap.entrySet();
    					Iterator rowMapCellsMapSetItr = rowMapCellsMapSet.iterator();
    					int cellCount = repeatCellIndex;
    					while(rowMapCellsMapSetItr.hasNext()){
    						Map.Entry cellEntry = (Map.Entry)rowMapCellsMapSetItr.next();
    						
    						String colKey = cellEntry.getKey().toString();
    						
    						if(colKey.contains("_contextdata_") || colKey.contains("_repeatcontextdata_"))
        						continue;
    						
    						//System.out.println(cellEntry);
    						
    						//creating new cell here
    						Cell newCell = row.createCell(cellCount);
    						//CellStyle newCellStyle = mainSheet.getWorkbook().createCellStyle();
    						CellStyle newCellStyle = null;
    						
    						String value = cellEntry.getValue().toString();
    						
    						String format = HtmlConstants.EMPTY;
    						if(value.contains("_format=")){
    							format = value.substring(value.indexOf("_format=")+8, value.length());
    							
    							value = value.substring(0, value.indexOf("_format="));
    						}
    						
    						if(value.contains("REMOVE"))
    							value = HtmlConstants.EMPTY;
    						
    						newCell.setCellValue(value);
    						
    						if(!value.startsWith(":"))
    							newCell.setCellValue(value);
    						else{
    							value = value.substring(1, value.length());
    							colKey = value;
    							
    							value = localCtx.get(value) != null ? localCtx.get(value).toString() : "";

    							newCell.setCellValue(value);
    							//newCellStyle = setRepeatCellValue(ctx, newCell, value, newCellStyle, colKey, mainSheet, numericStyle, dateStyle, textStyle);
    						}
    						
    						//apply formats
    						if(StringUtils.isNotBlank(format)){
        						StringTokenizer tokens = new StringTokenizer(format, "|");
        						while(tokens.hasMoreTokens()){
        							String token = tokens.nextToken();
        							
        							if(token.contains("height=")){
        								mainSheet.getRow(row.getRowNum()-1).setHeightInPoints(Integer.parseInt(token.substring(token.indexOf("=")+1, token.length())));
        							}
        							
        							/*if(token.contains("textalign")){
            							if(token.contains("textalign=right")){
            								newCellStyle.setAlignment(CellStyle.ALIGN_RIGHT);
            							}else if(token.contains("textalign=center")){
            								newCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
            							}else if(token.contains("textalign=left")){
            								newCellStyle.setAlignment(CellStyle.ALIGN_LEFT);
            							}
            						}*/
        							
        							if(token.contains("font")){
        								if(newCellStyle == null)
        									newCellStyle = mainSheet.getWorkbook().createCellStyle();
        								
        								Font font = mainSheet.getWorkbook().createFont();
        								String fontToken = token.substring(token.indexOf("font=")+5, token.length());
        								StringTokenizer fontTokens = new StringTokenizer(fontToken, ";");
        								while(fontTokens.hasMoreTokens()){
        									token = fontTokens.nextToken();
        									
        									if(token.contains("weight")){
		            							if(token.contains("weight=bold")){
		            								font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		            							}else
		            								font.setBoldweight(Font.BOLDWEIGHT_NORMAL);
		            						}
	            							
        									if(!token.contains("backgroundcolor") && token.contains("color")){
	            								if(token.contains("color=red")){
	            									font.setColor((short)HSSFColor.RED.index);
	            								}
		            						}
	            							
        									if(token.contains("backgroundcolor")){
	            								if(token.contains("backgroundcolor=grey")){
	            									newCellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
	            									newCellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
	            								}else if(token.contains("backgroundcolor=yellow")){
	            									newCellStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
	            									newCellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
	            								}else if(token.contains("backgroundcolor=white")){
	            									newCellStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());
	            									newCellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
	            								}
		            						}
	            							
	            							if(token.contains("backgroundfillpattern=nofill")){
	            								newCellStyle.setFillPattern(CellStyle.NO_FILL);
	            							}
	            							
	            							if(token.contains("size")){
	            								token = token.substring(token.indexOf("size=")+5, token.length());
	            								font.setFontHeightInPoints((short)Integer.parseInt(token));
	            							}
        								}
            							
            							newCellStyle.setFont(font);
            							newCell.setCellStyle(newCellStyle);
        							}
        						}
    						}
    						
    						cellCount++;
    					}
    					
    					innerstartIdRowNumber++;
    				}
    			}else if(entry.getKey().toString().contains("_list_") || entry.getKey().toString().contains("_repeatlist_")){
    				String newId = entry.getKey().toString();
    				newId = newId.substring(newId.indexOf("start_")+6, newId.length());
    				
    				if(ctx.get(newId+"_"+repeatCount) == null)
    					continue;
    				
    				//going to get rows defined in contextdata rowset
    				LinkedHashMap rowMap = (LinkedHashMap)entry.getValue();
    				
    				LinkedHashMap rowMapHeadersCellsMap = (LinkedHashMap)rowMap.get("ROW_1");
    				
    				//creating new header row here
					row = mainSheet.createRow(innerstartIdRowNumber);
					mainSheet.shiftRows(innerstartIdRowNumber, mainSheet.getLastRowNum(), 1);
					
					Set rowMapCellsMapSet = rowMapHeadersCellsMap.entrySet();
					Iterator rowMapCellsMapSetItr = rowMapCellsMapSet.iterator();
					int cellCount = repeatCellIndex;
					while(rowMapCellsMapSetItr.hasNext()){
						Map.Entry cellEntry = (Map.Entry)rowMapCellsMapSetItr.next();
						
						String colKey = cellEntry.getKey().toString();
						
						if(colKey.contains("_list_") || colKey.contains("_repeatlist_"))
    						continue;
						
						//System.out.println(cellEntry);
						
						//creating new cell here
						Cell newCell = row.createCell(cellCount);
						//CellStyle newCellStyle = mainSheet.getWorkbook().createCellStyle();
						CellStyle newCellStyle = null;
						
						String value = cellEntry.getValue().toString();
						
						String format = HtmlConstants.EMPTY;
						if(value.contains("_format=")){
							format = value.substring(value.indexOf("_format=")+8, value.length());
							
							value = value.substring(0, value.indexOf("_format="));
						}
						
						newCell.setCellValue(value);
						
						//apply formats
						if(StringUtils.isNotBlank(format)){
    						StringTokenizer tokens = new StringTokenizer(format, "|");
    						while(tokens.hasMoreTokens()){
    							String token = tokens.nextToken();
    							
    							if(token.contains("height=")){
    								mainSheet.getRow(row.getRowNum()-1).setHeightInPoints(Integer.parseInt(token.substring(token.indexOf("=")+1, token.length())));
    							}
    							
    							/*if(token.contains("textalign")){
        							if(token.contains("textalign=right")){
        								newCellStyle.setAlignment(CellStyle.ALIGN_RIGHT);
        							}else if(token.contains("textalign=center")){
        								newCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
        							}else if(token.contains("textalign=left")){
        								newCellStyle.setAlignment(CellStyle.ALIGN_LEFT);
        							}
        						}*/
    							
    							if(token.contains("font")){
    								if(newCellStyle == null)
    									newCellStyle = mainSheet.getWorkbook().createCellStyle();
    								
    								Font font = mainSheet.getWorkbook().createFont();
    								String fontToken = token.substring(token.indexOf("font=")+5, token.length());
    								StringTokenizer fontTokens = new StringTokenizer(fontToken, ";");
    								while(fontTokens.hasMoreTokens()){
    									token = fontTokens.nextToken();
    									
    									if(token.contains("weight")){
	            							if(token.contains("weight=bold")){
	            								font.setBoldweight(Font.BOLDWEIGHT_BOLD);
	            							}else
	            								font.setBoldweight(Font.BOLDWEIGHT_NORMAL);
	            						}
            							
            							if(!token.contains("backgroundcolor") && token.contains("color")){
            								if(token.contains("color=red")){
            									font.setColor((short)HSSFColor.RED.index);
            								}
	            						}
            							
            							if(token.contains("backgroundcolor")){
            								if(token.contains("backgroundcolor=grey")){
            									newCellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            									newCellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
            								}else if(token.contains("backgroundcolor=yellow")){
            									newCellStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
            									newCellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
            								}else if(token.contains("backgroundcolor=white")){
            									newCellStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());
            									newCellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
            								}
	            						}
            							
            							if(token.contains("backgroundfillpattern=nofill")){
            								newCellStyle.setFillPattern(CellStyle.NO_FILL);
            							}
            							
            							if(token.contains("size")){
            								token = token.substring(token.indexOf("size=")+5, token.length());
            								font.setFontHeightInPoints((short)Integer.parseInt(token));
            							}
    								}
        							
        							newCellStyle.setFont(font);
        							newCell.setCellStyle(newCellStyle);
    							}
    						}
						}
						
						//newCell.setCellStyle(newCellStyle);
						cellCount++;
					}
    				
					innerstartIdRowNumber++;
					
    				LinkedHashMap rowMapDataCellsMap = (LinkedHashMap)rowMap.get("ROW_2");
    				List dataList = (List)ctx.get(newId+"_"+repeatCount);
    				for(int m=0; m<dataList.size(); m++){
    					Map dataMap = (Map)dataList.get(m);
    					
    					//creating new data row here
    					row = mainSheet.createRow(innerstartIdRowNumber);
    					mainSheet.shiftRows(innerstartIdRowNumber, mainSheet.getLastRowNum(), 1);
    					
    					rowMapCellsMapSet = rowMapDataCellsMap.entrySet();
    					rowMapCellsMapSetItr = rowMapCellsMapSet.iterator();
    					cellCount = repeatCellIndex;
    					while(rowMapCellsMapSetItr.hasNext()){
    						Map.Entry cellEntry = (Map.Entry)rowMapCellsMapSetItr.next();
    						
    						String colKey = cellEntry.getKey().toString();
    						
    						if(colKey.contains("_list_") || colKey.contains("_repeatlist_"))
        						continue;
    						
    						//System.out.println(cellEntry);
    						
    						//creating new cell here
    						Cell newCell = row.createCell(cellCount);
    						//CellStyle newCellStyle = mainSheet.getWorkbook().createCellStyle();
    						CellStyle newCellStyle = null;
    						
    						String value = cellEntry.getValue().toString();
    						
    						String format = HtmlConstants.EMPTY;
    						if(value.contains("_format=")){
    							format = value.substring(value.indexOf("_format=")+8, value.length());
    							
    							value = value.substring(0, value.indexOf("_format="));
    						}
    						
    						if(!value.startsWith(":")){
    							newCell.setCellValue(value);
    						}else{
    							value = value.substring(1, value.length());
    							colKey = value;
    							
    							value = dataMap.get(value) != null ? dataMap.get(value).toString() : "";
    							newCellStyle = setRepeatCellValue(ctx, newCell, value, newCellStyle, colKey, mainSheet, numericStyle, dateStyle, textStyle);
    						}
    						
    						//apply formats
    						if(StringUtils.isNotBlank(format)){
        						StringTokenizer tokens = new StringTokenizer(format, "|");
        						while(tokens.hasMoreTokens()){
        							String token = tokens.nextToken();
        							
        							if(token.contains("height=")){
        								mainSheet.getRow(row.getRowNum()-1).setHeightInPoints(Integer.parseInt(token.substring(token.indexOf("=")+1, token.length())));
        							}
        							
        							/*if(token.contains("textalign")){
            							if(token.contains("textalign=right")){
            								newCellStyle.setAlignment(CellStyle.ALIGN_RIGHT);
            							}else if(token.contains("textalign=center")){
            								newCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
            							}else if(token.contains("textalign=left")){
            								newCellStyle.setAlignment(CellStyle.ALIGN_LEFT);
            							}
            						}*/
        							
        							if(token.contains("font")){
        								if(newCellStyle == null)
        									newCellStyle = mainSheet.getWorkbook().createCellStyle();
        								
        								Font font = mainSheet.getWorkbook().createFont();
        								String fontToken = token.substring(token.indexOf("font=")+5, token.length());
        								StringTokenizer fontTokens = new StringTokenizer(fontToken, ";");
        								while(fontTokens.hasMoreTokens()){
        									token = fontTokens.nextToken();
        									
        									if(token.contains("weight")){
		            							if(token.contains("weight=bold")){
		            								font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		            							}else
		            								font.setBoldweight((short)0);
		            						}
	            							
        									if(!token.contains("backgroundcolor") && token.contains("color")){
	            								if(token.contains("color=red")){
	            									font.setColor((short)HSSFColor.RED.index);
	            								}
		            						}
	            							
	            							if(token.contains("backgroundcolor")){
	            								if(token.contains("backgroundcolor=grey")){
	            									newCellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
	            									newCellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
	            								}else if(token.contains("backgroundcolor=yellow")){
	            									newCellStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
	            									newCellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
	            								}else if(token.contains("backgroundcolor=white")){
	            									newCellStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());
	            									newCellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
	            								}
		            						}
	            							
	            							if(token.contains("backgroundfillpattern=nofill")){
	            								newCellStyle.setFillPattern(CellStyle.NO_FILL);
	            							}
	            							
	            							if(token.contains("size")){
	            								token = token.substring(token.indexOf("size=")+5, token.length());
	            								font.setFontHeightInPoints((short)Integer.parseInt(token));
	            							}
        								}
            							
            							newCellStyle.setFont(font);
            							
        							}
        						}
    						}
    						
    						/*if(value.contains("Total")){
    							Font font = mainSheet.getWorkbook().createFont();
								font.setBoldweight(Font.BOLDWEIGHT_BOLD);
    							font.setFontHeightInPoints((short)8);
    							
								if(newCellStyle == null)
									newCellStyle = mainSheet.getWorkbook().createCellStyle();
								
								newCellStyle.setFont(font);
								newCell.setCellStyle(newCellStyle);
    						}*/
    						
    						//newCell.setCellStyle(newCellStyle);
    						cellCount++;
    					}
    					
    					innerstartIdRowNumber++;
    				}
    			}
    		}
    		
    		repeatCount++;
    	}
	}
	
	//Method created to populate repeat contextdatalist
	private void populateRepeatContextdataList(Context ctx, String id, Sheet mainSheet, Row row, int endIdRowNumber, Map map,
			CellStyle numericStyle, CellStyle dateStyle, CellStyle textStyle) throws Exception{
		
		int repeatCellIndex = getCellIndexForRepeatSchemasById(mainSheet, "start_", "_repeatcontextdatalist_", row.getRowNum(), endIdRowNumber);
    	
    	mainSheet.removeRow(row);
    	mainSheet.shiftRows(row.getRowNum()+1, mainSheet.getLastRowNum(), -1);
    	
    	int startIdRowNumber = row.getRowNum();
    	
    	//Added code if list have no header's, only have data
    	Map listDataMap = getCellInfo(mainSheet, row.getRowNum(), startIdRowNumber, new HashMap());
    	map.put(id, listDataMap);
    	//Ended code
    	
    	//going to get contextdata and list rows schema only
    	LinkedHashMap repeatContextdataListMap = new LinkedHashMap();
    	int innerstartIdRowNumber = startIdRowNumber;
    	int count = 0;
    	
    	innerstartIdRowNumber = startIdRowNumber;
    	while(innerstartIdRowNumber <= endIdRowNumber){
    		if(mainSheet.getRow(innerstartIdRowNumber) != null && mainSheet.getRow(innerstartIdRowNumber).getCell(repeatCellIndex) != null &&
    				mainSheet.getRow(innerstartIdRowNumber).getCell(repeatCellIndex).getStringCellValue().startsWith("end_") &&
    				mainSheet.getRow(innerstartIdRowNumber).getCell(repeatCellIndex).getStringCellValue().contains("_repeatcontextdatalist_")){
    			break;
    		}
    		
    		if(mainSheet.getRow(innerstartIdRowNumber) != null && mainSheet.getRow(innerstartIdRowNumber).getCell(repeatCellIndex) != null &&
    				mainSheet.getRow(innerstartIdRowNumber).getCell(repeatCellIndex).getStringCellValue().startsWith("start_") &&
    				(mainSheet.getRow(innerstartIdRowNumber).getCell(repeatCellIndex).getStringCellValue().contains("_repeatcontextdata_") ||
    					mainSheet.getRow(innerstartIdRowNumber).getCell(repeatCellIndex).getStringCellValue().contains("_contextdata_"))){
    			
    			
    			innerstartIdRowNumber = getRepeatContextdataRowsSchema(mainSheet, innerstartIdRowNumber, endIdRowNumber, repeatContextdataListMap, mainSheet.getRow(innerstartIdRowNumber).getCell(repeatCellIndex).getStringCellValue(), repeatCellIndex);
    		}else if(mainSheet.getRow(innerstartIdRowNumber) != null && mainSheet.getRow(innerstartIdRowNumber).getCell(repeatCellIndex) != null &&
    				mainSheet.getRow(innerstartIdRowNumber).getCell(repeatCellIndex).getStringCellValue().startsWith("start_") &&
    				(mainSheet.getRow(innerstartIdRowNumber).getCell(repeatCellIndex).getStringCellValue().contains("_repeatlist_") ||
    					mainSheet.getRow(innerstartIdRowNumber).getCell(repeatCellIndex).getStringCellValue().contains("_list_"))){
    			
    			innerstartIdRowNumber = getRepeatListRowsSchema(mainSheet, innerstartIdRowNumber, endIdRowNumber, repeatContextdataListMap, mainSheet.getRow(innerstartIdRowNumber).getCell(repeatCellIndex).getStringCellValue(), repeatCellIndex);
    		}else
    			innerstartIdRowNumber++;
    	}
    	
    	//System.out.println(repeatContextdataListMap);
    	
    	//removing existing repeatcontextdatalist rows because we will create dynamically these rows based on repeatContextdataListMap
    	innerstartIdRowNumber = startIdRowNumber;
    	while(innerstartIdRowNumber < endIdRowNumber){
    		if(mainSheet.getRow(startIdRowNumber) != null)
        		mainSheet.removeRow(mainSheet.getRow(startIdRowNumber));
        	
        	if(startIdRowNumber < mainSheet.getLastRowNum()){
        		mainSheet.shiftRows(startIdRowNumber+1, mainSheet.getLastRowNum(), -1);
        	}
    		
    		innerstartIdRowNumber++;
    	}
    	
    	innerstartIdRowNumber = startIdRowNumber;
    	
    	int repeatCount = 0;
    	int maxRepeatCount = 10;
    	if(ctx.get(id+"_repeatcount") != null && !ctx.get(id+"_repeatcount").toString().equals(HtmlConstants.EMPTY)){
    		maxRepeatCount = Integer.parseInt(ctx.get(id+"_repeatcount").toString());
    	}
    	
    	while(repeatCount < maxRepeatCount){
    		Set repeatContextdataListMapSet = repeatContextdataListMap.entrySet();
    		Iterator repeatContextdataListMapSetItr = repeatContextdataListMapSet.iterator();
    		while(repeatContextdataListMapSetItr.hasNext()){
    			Map.Entry entry = (Map.Entry)repeatContextdataListMapSetItr.next();
    			
    			//System.out.println(entry);
    			
    			if(entry.getKey() == null)
    				continue;
    			
    			if(entry.getKey().toString().contains("_contextdata_") || entry.getKey().toString().contains("_repeatcontextdata_")){
    				String newId = entry.getKey().toString();
    				newId = newId.substring(newId.indexOf("start_")+6, newId.length());
    				
    				if(ctx.get(newId+"_"+repeatCount) == null)
    					continue;
    				
    				Context localCtx = new Context();
    				if(ctx.get(newId+"_"+repeatCount) != null)
    					localCtx.putAll((Map)ctx.get(newId+"_"+repeatCount));
    				
    				//going to get rows defined in contextdata rowset
    				LinkedHashMap rowMap = (LinkedHashMap)entry.getValue();
    				Set rowMapSet = rowMap.keySet();
    				Iterator rowMapSetItr = rowMapSet.iterator();
    				while(rowMapSetItr.hasNext()){
    					String rowId = (String)rowMapSetItr.next();
    					
    					//creating new row here
    					row = mainSheet.createRow(innerstartIdRowNumber);
    					mainSheet.shiftRows(innerstartIdRowNumber, mainSheet.getLastRowNum(), 1);
    					
    					LinkedHashMap rowMapCellsMap = (LinkedHashMap)rowMap.get(rowId);
    					Set rowMapCellsMapSet = rowMapCellsMap.entrySet();
    					Iterator rowMapCellsMapSetItr = rowMapCellsMapSet.iterator();
    					int cellCount = repeatCellIndex;
    					while(rowMapCellsMapSetItr.hasNext()){
    						Map.Entry cellEntry = (Map.Entry)rowMapCellsMapSetItr.next();
    						
    						String colKey = cellEntry.getKey().toString();
    						
    						if(colKey.contains("_contextdata_") || colKey.contains("_repeatcontextdata_"))
        						continue;
    						
    						//System.out.println(cellEntry);
    						
    						//creating new cell here
    						Cell newCell = CellUtil.createCell(row, cellCount, null);
    						
    						String value = cellEntry.getValue().toString();
    						
    						String format = HtmlConstants.EMPTY;
    						if(value.contains("_format=")){
    							format = value.substring(value.indexOf("_format=")+8, value.length());
    							
    							value = value.substring(0, value.indexOf("_format="));
    							
    							if(ctx.get(value+"_format") != null && ctx.get(value+"_format") instanceof Font){
    								CellUtil.setFont(newCell, mainSheet.getWorkbook(), (Font)ctx.get(value+"_format"));
    							}
    						}
    						
    						if(value.contains("REMOVE"))
    							value = HtmlConstants.EMPTY;
    						
    						if(!value.startsWith(":")){
    							//newCell.setCellValue(value);
    						}else{
    							value = value.substring(1, value.length());
    							colKey = value;
    							
    							value = localCtx.get(value) != null ? localCtx.get(value).toString() : "";
    						}
    						
    						newCell.setCellValue(value);
    						
    						//apply formats
    						if(StringUtils.isNotBlank(format)){
        						StringTokenizer tokens = new StringTokenizer(format, "|");
        						while(tokens.hasMoreTokens()){
        							String token = tokens.nextToken();
        							
        							if(token.contains("height=")){
        								mainSheet.getRow(row.getRowNum()-1).setHeightInPoints(Integer.parseInt(token.substring(token.indexOf("=")+1, token.length())));
        							}
        							
        							if(token.contains("textalign")){
        								if(token.contains("textalign=lefttop")){
        									CellUtil.setAlignment(newCell, mainSheet.getWorkbook(), CellStyle.ALIGN_LEFT);
        									CellUtil.setCellStyleProperty(newCell, mainSheet.getWorkbook(), CellUtil.VERTICAL_ALIGNMENT, CellStyle.VERTICAL_TOP);
        								}else if(token.contains("textalign=centertop")){
        									CellUtil.setAlignment(newCell, mainSheet.getWorkbook(), CellStyle.ALIGN_CENTER);
        									CellUtil.setCellStyleProperty(newCell, mainSheet.getWorkbook(), CellUtil.VERTICAL_ALIGNMENT, CellStyle.VERTICAL_TOP);
        								}else if(token.contains("textalign=righttop")){
        									CellUtil.setAlignment(newCell, mainSheet.getWorkbook(), CellStyle.ALIGN_RIGHT);
        									CellUtil.setCellStyleProperty(newCell, mainSheet.getWorkbook(), CellUtil.VERTICAL_ALIGNMENT, CellStyle.VERTICAL_TOP);
        								}else if(token.contains("textalign=right")){
        									CellUtil.setAlignment(newCell, mainSheet.getWorkbook(), CellStyle.ALIGN_RIGHT);
        								}else if(token.contains("textalign=center")){
        									CellUtil.setAlignment(newCell, mainSheet.getWorkbook(), CellStyle.ALIGN_CENTER);
        								}else if(token.contains("textalign=left")){
        									CellUtil.setAlignment(newCell, mainSheet.getWorkbook(), CellStyle.ALIGN_LEFT);
        								} 
        							}
        							
        							if(token.contains("backgroundcolor")){
        								if(token.contains("backgroundcolor=grey")){
        									CellUtil.setCellStyleProperty(newCell, mainSheet.getWorkbook(), CellUtil.FILL_FOREGROUND_COLOR, IndexedColors.GREY_25_PERCENT.getIndex());
        									CellUtil.setCellStyleProperty(newCell, mainSheet.getWorkbook(), CellUtil.FILL_PATTERN, CellStyle.SOLID_FOREGROUND);
        								}else if(token.contains("backgroundcolor=yellow")){
        									CellUtil.setCellStyleProperty(newCell, mainSheet.getWorkbook(), CellUtil.FILL_FOREGROUND_COLOR, IndexedColors.YELLOW.getIndex());
        									CellUtil.setCellStyleProperty(newCell, mainSheet.getWorkbook(), CellUtil.FILL_PATTERN, CellStyle.SOLID_FOREGROUND);
        								}else if(token.contains("backgroundcolor=white")){
        									CellUtil.setCellStyleProperty(newCell, mainSheet.getWorkbook(), CellUtil.FILL_FOREGROUND_COLOR, IndexedColors.WHITE.getIndex());
        									CellUtil.setCellStyleProperty(newCell, mainSheet.getWorkbook(), CellUtil.FILL_PATTERN, CellStyle.SOLID_FOREGROUND);
        								}
            						}
        							
        							if(token.contains("backgroundfillpattern=nofill")){
        								CellUtil.setCellStyleProperty(newCell, mainSheet.getWorkbook(), CellUtil.FILL_PATTERN, CellStyle.NO_FILL);
        							}
        						}
    						}
    						
    						//apply text format if value is numeric and leading with 0 
    						if(StringUtils.isNumeric(value) && value.startsWith("0")){
    							CellUtil.setCellStyleProperty(newCell, mainSheet.getWorkbook(), CellUtil.DATA_FORMAT, (short)0x31);
    						}
    						
    						CellUtil.setCellStyleProperty(newCell, mainSheet.getWorkbook(), CellUtil.WRAP_TEXT, true);
    						cellCount++;
    					}
    					
    					innerstartIdRowNumber++;
    				}
    			}else if(entry.getKey().toString().contains("_list_") || entry.getKey().toString().contains("_repeatlist_")){
    				String newId = entry.getKey().toString();
    				newId = newId.substring(newId.indexOf("start_")+6, newId.length());
    				
    				if(ctx.get(newId+"_"+repeatCount) == null)
    					continue;
    				
    				//going to get rows defined in contextdata rowset
    				LinkedHashMap rowMap = (LinkedHashMap)entry.getValue();
    				
    				LinkedHashMap rowMapHeadersCellsMap = (LinkedHashMap)rowMap.get("ROW_1");
    				
    				//creating new header row here
					row = mainSheet.createRow(innerstartIdRowNumber);
					mainSheet.shiftRows(innerstartIdRowNumber, mainSheet.getLastRowNum(), 1);
					
					Set rowMapCellsMapSet = rowMapHeadersCellsMap.entrySet();
					Iterator rowMapCellsMapSetItr = rowMapCellsMapSet.iterator();
					int cellCount = repeatCellIndex;
					while(rowMapCellsMapSetItr.hasNext()){
						Map.Entry cellEntry = (Map.Entry)rowMapCellsMapSetItr.next();
						
						String colKey = cellEntry.getKey().toString();
						
						if(colKey.contains("_list_") || colKey.contains("_repeatlist_"))
    						continue;
						
						//System.out.println(cellEntry);
						
						String value = cellEntry.getValue().toString();
						
						Cell newCell = CellUtil.createCell(row, cellCount, null);
						
						String format = HtmlConstants.EMPTY;
						if(value.contains("_format=")){
							format = value.substring(value.indexOf("_format=")+8, value.length());
							
							value = value.substring(0, value.indexOf("_format="));
							
							if(ctx.get(value+"_format") != null && ctx.get(value+"_format") instanceof Font){
								CellUtil.setFont(newCell, mainSheet.getWorkbook(), (Font)ctx.get(value+"_format"));
							}
						}
						
						newCell.setCellValue(value);
						
						//apply formats
						if(StringUtils.isNotBlank(format)){
    						StringTokenizer tokens = new StringTokenizer(format, "|");
    						while(tokens.hasMoreTokens()){
    							String token = tokens.nextToken();
    							
    							if(token.contains("height=")){
    								mainSheet.getRow(row.getRowNum()-1).setHeightInPoints(Integer.parseInt(token.substring(token.indexOf("=")+1, token.length())));
    							}

    							if(token.contains("textalign")){
    								if(token.contains("textalign=lefttop")){
    									CellUtil.setAlignment(newCell, mainSheet.getWorkbook(), CellStyle.ALIGN_LEFT);
    									CellUtil.setCellStyleProperty(newCell, mainSheet.getWorkbook(), CellUtil.VERTICAL_ALIGNMENT, CellStyle.VERTICAL_TOP);
    								}else if(token.contains("textalign=centertop")){
    									CellUtil.setAlignment(newCell, mainSheet.getWorkbook(), CellStyle.ALIGN_CENTER);
    									CellUtil.setCellStyleProperty(newCell, mainSheet.getWorkbook(), CellUtil.VERTICAL_ALIGNMENT, CellStyle.VERTICAL_TOP);
    								}else if(token.contains("textalign=righttop")){
    									CellUtil.setAlignment(newCell, mainSheet.getWorkbook(), CellStyle.ALIGN_RIGHT);
    									CellUtil.setCellStyleProperty(newCell, mainSheet.getWorkbook(), CellUtil.VERTICAL_ALIGNMENT, CellStyle.VERTICAL_TOP);
    								}else if(token.contains("textalign=right")){
    									CellUtil.setAlignment(newCell, mainSheet.getWorkbook(), CellStyle.ALIGN_RIGHT);
    								}else if(token.contains("textalign=center")){
    									CellUtil.setAlignment(newCell, mainSheet.getWorkbook(), CellStyle.ALIGN_CENTER);
    								}else if(token.contains("textalign=left")){
    									CellUtil.setAlignment(newCell, mainSheet.getWorkbook(), CellStyle.ALIGN_LEFT);
    								} 
    							}
    							
    							if(token.contains("backgroundcolor")){
    								if(token.contains("backgroundcolor=grey")){
    									CellUtil.setCellStyleProperty(newCell, mainSheet.getWorkbook(), CellUtil.FILL_FOREGROUND_COLOR, IndexedColors.GREY_25_PERCENT.getIndex());
    									CellUtil.setCellStyleProperty(newCell, mainSheet.getWorkbook(), CellUtil.FILL_PATTERN, CellStyle.SOLID_FOREGROUND);
    								}else if(token.contains("backgroundcolor=yellow")){
    									CellUtil.setCellStyleProperty(newCell, mainSheet.getWorkbook(), CellUtil.FILL_FOREGROUND_COLOR, IndexedColors.YELLOW.getIndex());
    									CellUtil.setCellStyleProperty(newCell, mainSheet.getWorkbook(), CellUtil.FILL_PATTERN, CellStyle.SOLID_FOREGROUND);
    								}else if(token.contains("backgroundcolor=white")){
    									CellUtil.setCellStyleProperty(newCell, mainSheet.getWorkbook(), CellUtil.FILL_FOREGROUND_COLOR, IndexedColors.WHITE.getIndex());
    									CellUtil.setCellStyleProperty(newCell, mainSheet.getWorkbook(), CellUtil.FILL_PATTERN, CellStyle.SOLID_FOREGROUND);
    								}
        						}
    							
    							if(token.contains("backgroundfillpattern=nofill")){
    								CellUtil.setCellStyleProperty(newCell, mainSheet.getWorkbook(), CellUtil.FILL_PATTERN, CellStyle.NO_FILL);
    							}
    						}
						}
						
						CellUtil.setCellStyleProperty(newCell, mainSheet.getWorkbook(), CellUtil.WRAP_TEXT, true);
						cellCount++;
					}
    				
					innerstartIdRowNumber++;
					
    				LinkedHashMap rowMapDataCellsMap = (LinkedHashMap)rowMap.get("ROW_2");
    				List dataList = (List)ctx.get(newId+"_"+repeatCount);
    				for(int m=0; m<dataList.size(); m++){
    					Map dataMap = (Map)dataList.get(m);
    					
    					//creating new data row here
    					row = mainSheet.createRow(innerstartIdRowNumber);
    					mainSheet.shiftRows(innerstartIdRowNumber, mainSheet.getLastRowNum(), 1);
    					
    					rowMapCellsMapSet = rowMapDataCellsMap.entrySet();
    					rowMapCellsMapSetItr = rowMapCellsMapSet.iterator();
    					cellCount = repeatCellIndex;
    					while(rowMapCellsMapSetItr.hasNext()){
    						Map.Entry cellEntry = (Map.Entry)rowMapCellsMapSetItr.next();
    						
    						String colKey = cellEntry.getKey().toString();
    						
    						if(colKey.contains("_list_") || colKey.contains("_repeatlist_"))
        						continue;
    						
    						if(colKey.equals("BLANKROW")){
    		  					row = mainSheet.createRow(innerstartIdRowNumber);
    		  		  			mainSheet.shiftRows(innerstartIdRowNumber, mainSheet.getLastRowNum(), 1);
    		  		  			continue;
    		  				}
    						
    						//System.out.println(cellEntry);
    						
    						//creating new cell here
    						Cell newCell = CellUtil.createCell(row, cellCount, null);
    						
    						String value = cellEntry.getValue().toString();
    						
    						String format = HtmlConstants.EMPTY;
    						if(value.contains("_format=")){
    							format = value.substring(value.indexOf("_format=")+8, value.length());
    							
    							value = value.substring(0, value.indexOf("_format="));
    							
    							if(ctx.get(value+"_format") != null && ctx.get(value+"_format") instanceof Font){
    								CellUtil.setFont(newCell, mainSheet.getWorkbook(), (Font)ctx.get(value+"_format"));
    							}
    						}
    						
    						if(!value.startsWith(":")){
    							//newCell.setCellValue(value);
    						}else{
    							value = value.substring(1, value.length());
    							colKey = value;
    							
    							value = dataMap.get(value) != null ? dataMap.get(value).toString() : "";
    							
    							if(value != null && value.toString().contains("_format=")){
    			  					format = value.toString().substring(value.toString().indexOf("_format=")+8, value.toString().length());
    			  					
    			  					value = value.toString().substring(0, value.toString().indexOf("_format="));
    			  				}
    						}
    						
    						newCell.setCellValue(value);
    						
    						setRepeatCellValue(ctx, newCell, value, colKey, mainSheet, numericStyle, dateStyle, textStyle);
    						
    						//apply formats
    						if(StringUtils.isNotBlank(format)){
        						StringTokenizer tokens = new StringTokenizer(format, "|");
        						while(tokens.hasMoreTokens()){
        							String token = tokens.nextToken();
        							
        							if(token.contains("height=")){
        								mainSheet.getRow(row.getRowNum()-1).setHeightInPoints(Integer.parseInt(token.substring(token.indexOf("=")+1, token.length())));
        							}

        							if(token.contains("textalign")){
        								if(token.contains("textalign=lefttop")){
        									CellUtil.setAlignment(newCell, mainSheet.getWorkbook(), CellStyle.ALIGN_LEFT);
        									CellUtil.setCellStyleProperty(newCell, mainSheet.getWorkbook(), CellUtil.VERTICAL_ALIGNMENT, CellStyle.VERTICAL_TOP);
        								}else if(token.contains("textalign=centertop")){
        									CellUtil.setAlignment(newCell, mainSheet.getWorkbook(), CellStyle.ALIGN_CENTER);
        									CellUtil.setCellStyleProperty(newCell, mainSheet.getWorkbook(), CellUtil.VERTICAL_ALIGNMENT, CellStyle.VERTICAL_TOP);
        								}else if(token.contains("textalign=righttop")){
        									CellUtil.setAlignment(newCell, mainSheet.getWorkbook(), CellStyle.ALIGN_RIGHT);
        									CellUtil.setCellStyleProperty(newCell, mainSheet.getWorkbook(), CellUtil.VERTICAL_ALIGNMENT, CellStyle.VERTICAL_TOP);
        								}else if(token.contains("textalign=right")){
        									CellUtil.setAlignment(newCell, mainSheet.getWorkbook(), CellStyle.ALIGN_RIGHT);
        								}else if(token.contains("textalign=center")){
        									CellUtil.setAlignment(newCell, mainSheet.getWorkbook(), CellStyle.ALIGN_CENTER);
        								}else if(token.contains("textalign=left")){
        									CellUtil.setAlignment(newCell, mainSheet.getWorkbook(), CellStyle.ALIGN_LEFT);
        								} 
        							}
        							
        							if(token.contains("backgroundcolor")){
        								if(token.contains("backgroundcolor=grey")){
        									CellUtil.setCellStyleProperty(newCell, mainSheet.getWorkbook(), CellUtil.FILL_FOREGROUND_COLOR, IndexedColors.GREY_25_PERCENT.getIndex());
        									CellUtil.setCellStyleProperty(newCell, mainSheet.getWorkbook(), CellUtil.FILL_PATTERN, CellStyle.SOLID_FOREGROUND);
        								}else if(token.contains("backgroundcolor=yellow")){
        									CellUtil.setCellStyleProperty(newCell, mainSheet.getWorkbook(), CellUtil.FILL_FOREGROUND_COLOR, IndexedColors.YELLOW.getIndex());
        									CellUtil.setCellStyleProperty(newCell, mainSheet.getWorkbook(), CellUtil.FILL_PATTERN, CellStyle.SOLID_FOREGROUND);
        								}else if(token.contains("backgroundcolor=white")){
        									CellUtil.setCellStyleProperty(newCell, mainSheet.getWorkbook(), CellUtil.FILL_FOREGROUND_COLOR, IndexedColors.WHITE.getIndex());
        									CellUtil.setCellStyleProperty(newCell, mainSheet.getWorkbook(), CellUtil.FILL_PATTERN, CellStyle.SOLID_FOREGROUND);
        								}
            						}
        							
        							if(token.contains("backgroundfillpattern=nofill")){
        								CellUtil.setCellStyleProperty(newCell, mainSheet.getWorkbook(), CellUtil.FILL_PATTERN, CellStyle.NO_FILL);
        							}
        							
        							if(token.contains("type=V")){
        								CellUtil.setCellStyleProperty(newCell, mainSheet.getWorkbook(), CellUtil.DATA_FORMAT, (short)0x31);
        							}
        							
        							if(token.contains("font=")){
        								CellUtil.setFont(newCell, mainSheet.getWorkbook(), (Font)ctx.get("BOLD_format"));
        							}
        						}
    						}
    						
    						//apply text format if value is numeric and leading with 0 
    						if(StringUtils.isNumeric(value) && value.startsWith("0")){
    							CellUtil.setCellStyleProperty(newCell, mainSheet.getWorkbook(), CellUtil.DATA_FORMAT, (short)0x31);
    						}
    						
    						/*if(value.contains("Total")){
    							Font font = mainSheet.getWorkbook().createFont();
								font.setBoldweight(Font.BOLDWEIGHT_BOLD);
    							font.setFontHeightInPoints((short)8);
    							
								if(newCellStyle == null)
									newCellStyle = mainSheet.getWorkbook().createCellStyle();
								
								newCellStyle.setFont(font);
								newCell.setCellStyle(newCellStyle);
    						}*/
    						
    						//newCell.setCellStyle(newCellStyle);
    						
    						CellUtil.setCellStyleProperty(newCell, mainSheet.getWorkbook(), CellUtil.WRAP_TEXT, true);
    						cellCount++;
    					}
    					
    					innerstartIdRowNumber++;
    				}
    			}
    		}
    		
    		repeatCount++;
    	}
	}
	
	//Method created to set cell value
	public CellStyle setRepeatCellValue(Context ctx, Cell cell, Object value, CellStyle style, String colKey, Sheet mainSheet,
			CellStyle numericStyle, CellStyle dateStyle, CellStyle textStyle){
		
		try{
			if(MetaDataResources.getInstance(ctx).getField(colKey) != null && 
					MetaDataResources.getInstance(ctx).getField(colKey).getType().equals("N")){
				value = DataUtils.removeAmountFormat(value) != null ? DataUtils.removeAmountFormat(value).toString() : value;
				
				/*if(StringUtils.isBlank(value.toString()))
					value = "0";*/

				try{
					cell.setCellValue(Double.parseDouble(value.toString()));
					cell.setCellType(Cell.CELL_TYPE_NUMERIC);
				}catch (Exception e1) {
					logger.error("Error in ExcelDatabaseMapping.setRepeatCellValue", e1);
					cell.setCellValue(value.toString());
					cell.setCellType(Cell.CELL_TYPE_STRING);
				}
				
				//cell.setCellStyle(numericStyle);
				
				//style = mainSheet.getWorkbook().createCellStyle();
				//style.cloneStyleFrom(numericStyle);
				cell.setCellStyle(numericStyle);
				return style;
			}else if(MetaDataResources.getInstance(ctx).getField(colKey) != null && 
					MetaDataResources.getInstance(ctx).getField(colKey).getType().equals("D")){
				
				cell.setCellValue(value.toString());
		        cell.setCellType(Cell.CELL_TYPE_STRING);
		        //style.setAlignment((short)2);
		        //cell.setCellStyle(dateStyle);
		        
		        //style = mainSheet.getWorkbook().createCellStyle();
				//style.cloneStyleFrom(dateStyle);
				cell.setCellStyle(dateStyle);
				return style;
			}else if(MetaDataResources.getInstance(ctx).getField(colKey) != null && 
					MetaDataResources.getInstance(ctx).getField(colKey).getType().equals("V")){
				
				cell.setCellValue(value.toString());
		        cell.setCellType(Cell.CELL_TYPE_STRING);
		       // cell.setCellStyle(textStyle);
		        
		        //style = mainSheet.getWorkbook().createCellStyle();
				//style.cloneStyleFrom(textStyle);
				cell.setCellStyle(textStyle);
				return style;
			}else if(MetaDataResources.getInstance(ctx).getField(colKey) != null && 
					MetaDataResources.getInstance(ctx).getField(colKey).getType().equals("I")){
				
				cell.setCellValue(Integer.parseInt(value.toString()));
				CellUtil.setCellStyleProperty(cell, mainSheet.getWorkbook(), CellUtil.DATA_FORMAT, (short)0);
				//style = mainSheet.getWorkbook().createCellStyle();
				return style;
			}else{
				cell.setCellValue(value.toString());
				cell.setCellType(Cell.CELL_TYPE_STRING);
				//cell.setCellStyle(textStyle);
				
				//style = mainSheet.getWorkbook().createCellStyle();
				//style.cloneStyleFrom(textStyle);
				cell.setCellStyle(textStyle);
				return style;
			}
		}catch(Exception e){
			logger.error("Error in ExcelDatabaseMapping.setRepeatCellValue", e);
		}
		
		//style = mainSheet.getWorkbook().createCellStyle();
		return style;
	}
	
	//Method created to set cell value
	public void setRepeatCellValue(Context ctx, Cell cell, Object value, String colKey, Sheet mainSheet,
			CellStyle numericStyle, CellStyle dateStyle, CellStyle textStyle){
		
		try{
			if(MetaDataResources.getInstance(ctx).getField(colKey) != null && 
					MetaDataResources.getInstance(ctx).getField(colKey).getType().equals("N")){
				value = DataUtils.removeAmountFormat(value) != null ? DataUtils.removeAmountFormat(value).toString() : value;
				
				try{
					cell.setCellValue(Double.parseDouble(value.toString()));
					cell.setCellType(Cell.CELL_TYPE_NUMERIC);
				}catch (Exception e1) {
					logger.error("Error in ExcelDatabaseMapping.setRepeatCellValue", e1);
					cell.setCellValue(value.toString());
					cell.setCellType(Cell.CELL_TYPE_STRING);
				}
				
				CellUtil.setCellStyleProperty(cell, mainSheet.getWorkbook(), CellUtil.DATA_FORMAT, (short)8);
			}else if(MetaDataResources.getInstance(ctx).getField(colKey) != null && 
					MetaDataResources.getInstance(ctx).getField(colKey).getType().equals("D")){
				
				cell.setCellValue(value.toString());
		        cell.setCellType(Cell.CELL_TYPE_STRING);
		        
		        CellUtil.setCellStyleProperty(cell, mainSheet.getWorkbook(), CellUtil.DATA_FORMAT, (short)0xe);
			}else if(MetaDataResources.getInstance(ctx).getField(colKey) != null && 
					MetaDataResources.getInstance(ctx).getField(colKey).getType().equals("V")){
				
				cell.setCellValue(value.toString());
		        cell.setCellType(Cell.CELL_TYPE_STRING);
		        
		        CellUtil.setCellStyleProperty(cell, mainSheet.getWorkbook(), CellUtil.DATA_FORMAT, (short)0x31);
			}else if(MetaDataResources.getInstance(ctx).getField(colKey) != null && 
					MetaDataResources.getInstance(ctx).getField(colKey).getType().equals("I")){
				
				try{
					if(StringUtils.isNotBlank(value.toString()) && !value.toString().equals(HtmlConstants.EMPTY)){
						cell.setCellValue(Integer.parseInt(value.toString()));
					}
				}catch (Exception e) {
					logger.error("Error in ExcelDatabaseMapping.setRepeatCellValue", e);
					// TODO: handle exception
				}
				
				CellUtil.setCellStyleProperty(cell, mainSheet.getWorkbook(), CellUtil.DATA_FORMAT, (short)0);
			}else{
				cell.setCellValue(value.toString());
				cell.setCellType(Cell.CELL_TYPE_STRING);
				
				CellUtil.setCellStyleProperty(cell, mainSheet.getWorkbook(), CellUtil.DATA_FORMAT, (short)0x31);
			}
		}catch(Exception e){
			logger.error("Error in ExcelDatabaseMapping.setRepeatCellValue", e);
		}
	}
	
	//Method created to get repeatcontextdata schema
	private int getRepeatContextdataRowsSchema(Sheet mainSheet, int innerstartIdRowNumber, int endIdRowNumber, 
			LinkedHashMap repeatContextdataListMap, String id, int repeatCellIndex) throws Exception{
		
		int count = 0;
		
		LinkedHashMap map1 = new LinkedHashMap();
		while(innerstartIdRowNumber <= endIdRowNumber){
    		if(mainSheet.getRow(innerstartIdRowNumber) != null && mainSheet.getRow(innerstartIdRowNumber).getCell(repeatCellIndex) != null &&
    				mainSheet.getRow(innerstartIdRowNumber).getCell(repeatCellIndex).getStringCellValue().startsWith("end_") &&
    				(mainSheet.getRow(innerstartIdRowNumber).getCell(repeatCellIndex).getStringCellValue().contains("_repeatcontextdata_")) ||
    					mainSheet.getRow(innerstartIdRowNumber).getCell(repeatCellIndex).getStringCellValue().contains("_contextdata_")){
    			
    			Row row1 = mainSheet.getRow(innerstartIdRowNumber);
    			Iterator cellsItr = row1.cellIterator();
    			LinkedHashMap<String, String> rowCellMap = new LinkedHashMap<String, String>();
    			
    			while(cellsItr.hasNext()){
    				Cell rowCell = (Cell)cellsItr.next();
    				if(rowCell.getColumnIndex() < repeatCellIndex)
    					continue;
    				
    				rowCellMap.put(rowCell.getStringCellValue(), rowCell.getStringCellValue());
    			}
    			
    			map1.put("ROW_"+count, rowCellMap);
    			break;
    		}
    		
			Row row1 = mainSheet.getRow(innerstartIdRowNumber);
			Iterator cellsItr = row1.cellIterator();
			LinkedHashMap<String, String> rowCellMap = new LinkedHashMap<String, String>();
			while(cellsItr.hasNext()){
				Cell rowCell = (Cell)cellsItr.next();
				if(rowCell.getColumnIndex() < repeatCellIndex)
					continue;
				
				rowCellMap.put(rowCell.getStringCellValue(), rowCell.getStringCellValue());
			}
			
			map1.put("ROW_"+count, rowCellMap);
    		innerstartIdRowNumber++;
    		count++;
    	}
		
		repeatContextdataListMap.put(id, map1);
		
		return innerstartIdRowNumber;
	}
	
	//Method created to get repeatlist schema
	private int getRepeatListRowsSchema(Sheet mainSheet, int innerstartIdRowNumber, int endIdRowNumber, 
			LinkedHashMap repeatContextdataListMap, String id, int repeatCellIndex) throws Exception{
		
		int count = 0;
		LinkedHashMap map1 = new LinkedHashMap();
		
		while(innerstartIdRowNumber <= endIdRowNumber){
    		if(mainSheet.getRow(innerstartIdRowNumber) != null && mainSheet.getRow(innerstartIdRowNumber).getCell(repeatCellIndex) != null &&
    				mainSheet.getRow(innerstartIdRowNumber).getCell(repeatCellIndex).getStringCellValue().startsWith("end_") &&
    				(mainSheet.getRow(innerstartIdRowNumber).getCell(repeatCellIndex).getStringCellValue().contains("_repeatlist_")) ||
    					mainSheet.getRow(innerstartIdRowNumber).getCell(repeatCellIndex).getStringCellValue().contains("_list_")){
    			
    			Row row1 = mainSheet.getRow(innerstartIdRowNumber);
    			Iterator cellsItr = row1.cellIterator();
    			LinkedHashMap<String, String> rowCellMap = new LinkedHashMap<String, String>();
    			
    			while(cellsItr.hasNext()){
    				Cell rowCell = (Cell)cellsItr.next();
    				if(rowCell.getColumnIndex() < repeatCellIndex)
    					continue;
    				
    				rowCellMap.put(rowCell.getStringCellValue(), rowCell.getStringCellValue());
    			}
    			
    			map1.put("ROW_"+count, rowCellMap);
    			break;
    		}
    		
			Row row1 = mainSheet.getRow(innerstartIdRowNumber);
			Iterator cellsItr = row1.cellIterator();
			LinkedHashMap<String, String> rowCellMap = new LinkedHashMap<String, String>();
			
			while(cellsItr.hasNext()){
				Cell rowCell = (Cell)cellsItr.next();
				if(rowCell.getColumnIndex() < repeatCellIndex)
					continue;
				
				rowCellMap.put(rowCell.getStringCellValue(), rowCell.getStringCellValue());
			}
			
			map1.put("ROW_"+count, rowCellMap);
    		innerstartIdRowNumber++;
    		count++;
    	}
		
		repeatContextdataListMap.put(id, map1);
		
		return innerstartIdRowNumber;
	}
}
