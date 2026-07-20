package com.manage.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellUtil;

import com.manage.managemetadata.functions.commonfunctions.DataUtils;
import com.manage.managemetadata.metadata.MetaDataResources;
import com.util.Context;
import com.util.DateUtils;
import com.util.HtmlConstants;
import com.util.StringUtils;
import com.util.TripleDESEncryptionDecryption;

public class ExcelUtil
{
  private static Logger logger = Logger.getLogger(ExcelUtil.class);
  
  public static Object removeAmountFormat(Object arg1)
  {
    if (arg1 == null) {
      return Integer.valueOf(0);
    }
    String amount = arg1.toString();
    return amount.replace("$", "").replace(",", "");
  }
  
  public static String getValueFromMap(Map map, String name, boolean isBoolean)
    throws Exception
  {
    String result = "";
    if (map == null) {
      return result;
    }
    result = map.get(name) != null ? map.get(name).toString() : "";
    if (isBoolean) {
      if ("Y".equals(result)) {
        result = "Yes";
      } else if ("N".equals(result)) {
        result = "No";
      }
    }
    return result;
  }
  
  public static Cell getCell(Sheet sheet, int rowNum, int columnNum)
  {
    Row row = sheet.getRow(rowNum);
    return row.getCell(columnNum);
  }
  
  public static void setCellValue(Sheet sheet, int rowNum, int columnNum, Object value)
  {
    Row row = sheet.getRow(rowNum);
    row = row == null ? sheet.createRow(rowNum) : row;
    Cell cell = row.getCell(columnNum);
    cell = cell == null ? row.createCell(columnNum) : cell;
    if (value == null) {
      value = "";
    }
    switch (cell.getCellStyle().getDataFormat())
    {
    case 38: 
      if (!"".equals(value)) {
        cell.setCellValue(Double.parseDouble(value.toString()));
      } else {
        cell.setCellValue(0.0D);
      }
      break;
    case 207: 
      if (!"".equals(value)) {
        cell.setCellValue(Double.parseDouble(value.toString()));
      } else {
        cell.setCellValue("");
      }
      break;
    case 164: 
      if (value == null) {
        value = "";
      }
      if (!"".equals(value)) {
        cell.setCellValue(Double.parseDouble(value.toString()) / 100.0D);
      } else {
        cell.setCellValue(0.0D);
      }
      break;
    case 166: 
      if (value == null) {
        value = "";
      }
      if (!"".equals(value)) {
        cell.setCellValue(Double.parseDouble(value.toString()) / 100.0D);
      } else {
        cell.setCellValue(0.0D);
      }
      break;
    case 167: 
      if (value == null) {
        value = "";
      }
      if (!"".equals(value)) {
        cell.setCellValue(Double.parseDouble(value.toString()) / 100.0D);
      } else {
        cell.setCellValue(0.0D);
      }
      break;
    case 208: 
      if (value == null) {
        value = "";
      }
      if (!"".equals(value)) {
        cell.setCellValue(Double.parseDouble(value.toString()));
      } else {
        cell.setCellValue(0.0D);
      }
      break;
    case 210: 
      if (value == null) {
        value = "";
      }
      if (!"".equals(value)) {
        cell.setCellValue(Double.parseDouble(value.toString()));
      } else {
        cell.setCellValue(0.0D);
      }
      break;
    case 205: 
      if (!"".equals(value)) {
        cell.setCellValue(Double.parseDouble(value.toString()));
      } else {
        cell.setCellValue(0.0D);
      }
      break;
    case 9: 
      if (!"".equals(value)) {
        cell.setCellValue(Double.parseDouble(value.toString()) / 100.0D);
      } else {
        cell.setCellValue(0.0D);
      }
      break;
    case 10: 
      if (value == null) {
        value = "";
      }
      if (!"".equals(value)) {
        cell.setCellValue(Double.parseDouble(value.toString()) / 100.0D);
      } else {
        cell.setCellValue(0.0D);
      }
      break;
    case 14: 
      if (value == null) {
        value = "";
      }
      if (!"".equals(value)) {
        try
        {
          DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
          java.util.Date date = formatter
            .parse(getFormattedDateFromObject(value));
          cell.setCellValue(date);
        }
        catch (Exception e)
        {
          System.out.println("Exception :" + e);
        }
      } else {
        cell.setCellValue(0.0D);
      }
      break;
    case 203: 
      if (value == null) {
        value = "";
      }
      if (!"".equals(value)) {
        try
        {
          DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
          java.util.Date date = formatter
            .parse(getFormattedDateFromObject(value));
          cell.setCellValue(date);
        }
        catch (Exception e)
        {
          System.out.println("Exception :" + e);
        }
      } else {
        cell.setCellValue(0.0D);
      }
      break;
    case 49: 
      if (value == null) {
        value = "";
      }
      cell.setCellValue(value.toString());
      
      break;
    default: 
      cell.setCellValue(value.toString());
    }
  }
  
  public static void setCellValue(Sheet sheet, Object value, Object cellInfo,
			int addtoRowNum, String colKey, Context ctx, CellStyle style, boolean isCellFormula) throws Exception {
		
		RowColumnBean cellIndex = null;
		if (cellInfo != null)
			cellIndex = (RowColumnBean) cellInfo;
		else
			return;

		Row row = sheet.getRow(cellIndex.getRownum() + addtoRowNum);
		row = (row == null ? sheet.createRow(cellIndex.getRownum()) : row);
		Cell cell = row.getCell(cellIndex.getCellnum());
		cell = (cell == null ? row.createCell(cellIndex.getCellnum()) : cell);
		
		cell.getCellStyle().setDataFormat((short)0);
		
		//going to check for encrypt value
		if((colKey != null && colKey.endsWith("_encrypt")) && (value != null && !value.toString().equals(HtmlConstants.EMPTY))){
			String encrypt = colKey.substring(colKey.lastIndexOf("_encrypt")+9, colKey.length());
			
			colKey = colKey.substring(0, colKey.indexOf("_encrypt"));
			
			if(encrypt != null && encrypt.equals("Y"))
				value = new TripleDESEncryptionDecryption().decrypt(value.toString());
		}
		
		if(isCellFormula){
			try{
				value = value != null ? value.toString().replace("_rowindex", String.valueOf(row.getRowNum())) : HtmlConstants.EMPTY;
				value = value.toString().substring(value.toString().indexOf("FORMULA=")+8, value.toString().length());
				
				cell.setCellFormula(value != null ? value.toString() : HtmlConstants.EMPTY);
			}catch (Exception e) {
				logger.error("Error while creating index for formula execution : - " + value);
			}
			
			return;
		}
		
		boolean isFieldTypeFound = false;
		
		//Added code to apply currency format on cell value based on field type in metadata.xml
		try{
			String filteredColKey = colKey;
			String format = null;	
			
			if(filteredColKey.contains("_format")){
				format = filteredColKey.substring(filteredColKey.indexOf("_format")+8, filteredColKey.length());
				filteredColKey = filteredColKey.substring(0, filteredColKey.indexOf("_format"));
			}
			
			if(filteredColKey.contains("_textalign")){
				filteredColKey = filteredColKey.substring(0, filteredColKey.indexOf("_textalign"));
			}
			
			if(MetaDataResources.getInstance(ctx).getField(filteredColKey) != null && 
					MetaDataResources.getInstance(ctx).getField(filteredColKey).getType().equals("N")){
				
				value = DataUtils.removeAmountFormat(value) != null ? DataUtils.removeAmountFormat(value).toString() : value;
				
				try{
					cell.setCellValue(Double.parseDouble(value.toString()));
					isFieldTypeFound = true;
				}catch (Exception e) {
					cell.setCellValue(value.toString());
				}
				
				CellUtil.setCellStyleProperty(cell, sheet.getWorkbook(), CellUtil.DATA_FORMAT, (short)8);
				
				//cell.getCellStyle().setDataFormat((short)8);
		        // cell.setCellType(0);
		        //cell.setCellStyle(numericStyle);
				/*CellStyle style = sheet.getWorkbook().createCellStyle();
				style.cloneStyleFrom(cell.getCellStyle());
				
				style.setAlignment(numericStyle.getAlignment());
				style.setDataFormat(numericStyle.getDataFormat());
				cell.setCellStyle(style);*/
				
				/*if((cell.getCellStyle().getBorderBottom() != CellStyle.BORDER_NONE) ||
					(cell.getCellStyle().getBorderTop() != CellStyle.BORDER_NONE) ||
					(cell.getCellStyle().getBorderLeft() != CellStyle.BORDER_NONE) ||
					(cell.getCellStyle().getBorderRight() != CellStyle.BORDER_NONE)){
					
					CellStyle style = sheet.getWorkbook().createCellStyle();
					style.cloneStyleFrom(cell.getCellStyle());
					style.setAlignment(numericStyle.getAlignment());
					style.setDataFormat(numericStyle.getDataFormat());
					
					cell.setCellStyle(style);
				}else
					cell.setCellStyle(numericStyle);
				
				Font font = sheet.getWorkbook().getFontAt(cell.getCellStyle().getFontIndex());
				font.setFontName(HSSFFont.FONT_ARIAL);
				font.setFontHeightInPoints((short)8);
				
				cell.getCellStyle().setFont(font);*/
				
				//return;
			}else if(MetaDataResources.getInstance(ctx).getField(filteredColKey) != null && 
					MetaDataResources.getInstance(ctx).getField(filteredColKey).getType().equals("V")){
				
				/*CellStyle style = sheet.getWorkbook().createCellStyle();
				style.cloneStyleFrom(cell.getCellStyle());
				
				style.setAlignment(textStyle.getAlignment());
				style.setDataFormat(textStyle.getDataFormat());*/
				
				cell.setCellValue(value.toString());
		        cell.setCellType(1);
		        isFieldTypeFound = true;
		        
				//cell.setCellStyle(textStyle);
				/*//Added code to remove errors
				try{
					if(value.toString().contains(".") && Double.parseDouble(value.toString()) != -1){
						style.setDataFormat((short)0.00);
					}else if(Integer.parseInt(value.toString()) != -1){
						style.setDataFormat((short)0);
					}
				}catch (Exception e) {
					// TODO: handle exception
				}*/
				
				//Added code to handle alignment of text
				/*if(colKey.toLowerCase().contains("_textalign")){
					String align = colKey.substring(colKey.lastIndexOf("_textalign")+11, colKey.length());
					colKey = colKey.substring(0, colKey.indexOf("_textalign"));
					cell.setCellType(Cell.CELL_TYPE_STRING);
					short algn = -1;
					if(align != null){
						if(align.equalsIgnoreCase("left"))
							algn = HSSFCellStyle.ALIGN_LEFT;
						else if(align.equalsIgnoreCase("right"))
							algn = HSSFCellStyle.ALIGN_RIGHT;
						else if(align.equalsIgnoreCase("center"))
							algn = HSSFCellStyle.ALIGN_CENTER;
						
						if(algn > 0){
							CellStyle style = sheet.getWorkbook().createCellStyle();
							style.cloneStyleFrom(textStyle);
							style.setAlignment(algn);
							cell.setCellStyle(style);
						}
					}
				}else{
					if((cell.getCellStyle().getBorderBottom() != CellStyle.BORDER_NONE) ||
						(cell.getCellStyle().getBorderTop() != CellStyle.BORDER_NONE) ||
						(cell.getCellStyle().getBorderLeft() != CellStyle.BORDER_NONE) ||
						(cell.getCellStyle().getBorderRight() != CellStyle.BORDER_NONE)){
						
						CellStyle style = sheet.getWorkbook().createCellStyle();
						style.cloneStyleFrom(cell.getCellStyle());
						style.setAlignment(textStyle.getAlignment());
						style.setDataFormat(textStyle.getDataFormat());
						
						cell.setCellStyle(style);
					}else
						cell.setCellStyle(textStyle);
				}
				
				Font font = sheet.getWorkbook().getFontAt(cell.getCellStyle().getFontIndex());
				font.setFontName(HSSFFont.FONT_ARIAL);
				font.setFontHeightInPoints((short)8);
				
				cell.getCellStyle().setFont(font);
				//Ended code
*/				
				//return;
			}else if(MetaDataResources.getInstance(ctx).getField(filteredColKey) != null && 
					MetaDataResources.getInstance(ctx).getField(filteredColKey).getType().equals("D")){
				
				/*CellStyle style = sheet.getWorkbook().createCellStyle();
				style.cloneStyleFrom(cell.getCellStyle());
				
				style.setAlignment(dateStyle.getAlignment());
				style.setDataFormat(dateStyle.getDataFormat());
				cell.setCellStyle(style);*/
				
				cell.setCellValue(value.toString());
		        cell.setCellType(1);

		        CellUtil.setCellStyleProperty(cell, sheet.getWorkbook(), CellUtil.DATA_FORMAT, (short)0xe);
		        CellUtil.setCellStyleProperty(cell, sheet.getWorkbook(), CellUtil.ALIGNMENT, (short)2);
		        isFieldTypeFound = true;
		        
		        Font font = sheet.getWorkbook().getFontAt(cell.getCellStyle().getFontIndex());
				font.setFontName(HSSFFont.FONT_ARIAL);
				font.setFontHeightInPoints((short)8);
				
				//cell.getCellStyle().setFont(font);
				CellUtil.setFont(cell, sheet.getWorkbook(), font);
				
				/*if((cell.getCellStyle().getBorderBottom() != CellStyle.BORDER_NONE) ||
					(cell.getCellStyle().getBorderTop() != CellStyle.BORDER_NONE) ||
					(cell.getCellStyle().getBorderLeft() != CellStyle.BORDER_NONE) ||
					(cell.getCellStyle().getBorderRight() != CellStyle.BORDER_NONE)){
					
					CellStyle style = sheet.getWorkbook().createCellStyle();
					style.cloneStyleFrom(cell.getCellStyle());
					style.setAlignment(dateStyle.getAlignment());
					style.setDataFormat(dateStyle.getDataFormat());
					
					cell.setCellStyle(style);
				}else
					cell.setCellStyle(dateStyle);
				
				cell.setCellValue(value.toString());
				
				Font font = sheet.getWorkbook().getFontAt(cell.getCellStyle().getFontIndex());
				font.setFontName(HSSFFont.FONT_ARIAL);
				font.setFontHeightInPoints((short)8);
				
				cell.getCellStyle().setFont(font);*/
				//return;
			}else if(MetaDataResources.getInstance(ctx).getField(filteredColKey) != null && 
					MetaDataResources.getInstance(ctx).getField(filteredColKey).getType().equals("I")){
				
				cell.setCellValue(Integer.parseInt(value.toString()));
				
				//CellUtil.setCellStyleProperty(cell, sheet.getWorkbook(), CellUtil.DATA_FORMAT, (short)0);
				CellUtil.setCellStyleProperty(cell, sheet.getWorkbook(), CellUtil.DATA_FORMAT, sheet.getWorkbook().getCreationHelper().createDataFormat().getFormat("0"));
				isFieldTypeFound = true;
				//return;
			}
			
			//apply formats
			if(StringUtils.isNotBlank(format)){
				StringTokenizer tokens = new StringTokenizer(format, "|");
				while(tokens.hasMoreTokens()){
					String token = tokens.nextToken();
					
					if(token.contains("textalign")){
						if(token.contains("textalign=lefttop")){
							CellUtil.setAlignment(cell, sheet.getWorkbook(), CellStyle.ALIGN_LEFT);
							CellUtil.setCellStyleProperty(cell, sheet.getWorkbook(), CellUtil.VERTICAL_ALIGNMENT, CellStyle.VERTICAL_TOP);
						}else if(token.contains("textalign=centertop")){
							CellUtil.setAlignment(cell, sheet.getWorkbook(), CellStyle.ALIGN_CENTER);
							CellUtil.setCellStyleProperty(cell, sheet.getWorkbook(), CellUtil.VERTICAL_ALIGNMENT, CellStyle.VERTICAL_TOP);
						}else if(token.contains("textalign=righttop")){
							CellUtil.setAlignment(cell, sheet.getWorkbook(), CellStyle.ALIGN_RIGHT);
							CellUtil.setCellStyleProperty(cell, sheet.getWorkbook(), CellUtil.VERTICAL_ALIGNMENT, CellStyle.VERTICAL_TOP);
						}else if(token.contains("textalign=right")){
							CellUtil.setAlignment(cell, sheet.getWorkbook(), CellStyle.ALIGN_RIGHT);
						}else if(token.contains("textalign=center")){
							CellUtil.setAlignment(cell, sheet.getWorkbook(), CellStyle.ALIGN_CENTER);
						}else if(token.contains("textalign=left")){
							CellUtil.setAlignment(cell, sheet.getWorkbook(), CellStyle.ALIGN_LEFT);
						} 
					}
					
					if(token.contains("backgroundcolor")){
						if(token.contains("backgroundcolor=grey")){
							CellUtil.setCellStyleProperty(cell, sheet.getWorkbook(), CellUtil.FILL_FOREGROUND_COLOR, IndexedColors.GREY_25_PERCENT);
							CellUtil.setCellStyleProperty(cell, sheet.getWorkbook(), CellUtil.FILL_PATTERN, CellStyle.SOLID_FOREGROUND);
						}else if(token.contains("backgroundcolor=yellow")){
							CellUtil.setCellStyleProperty(cell, sheet.getWorkbook(), CellUtil.FILL_FOREGROUND_COLOR, IndexedColors.YELLOW);
							CellUtil.setCellStyleProperty(cell, sheet.getWorkbook(), CellUtil.FILL_PATTERN, CellStyle.SOLID_FOREGROUND);
						}else if(token.contains("backgroundcolor=white")){
							CellUtil.setCellStyleProperty(cell, sheet.getWorkbook(), CellUtil.FILL_FOREGROUND_COLOR, IndexedColors.WHITE);
							CellUtil.setCellStyleProperty(cell, sheet.getWorkbook(), CellUtil.FILL_PATTERN, CellStyle.SOLID_FOREGROUND);
						}
					}
					
					if(token.contains("backgroundfillpattern=nofill")){
						CellUtil.setCellStyleProperty(cell, sheet.getWorkbook(), CellUtil.FILL_PATTERN, CellStyle.NO_FILL);
					}
					
					if(token.contains("type=V")){
						CellUtil.setCellStyleProperty(cell, sheet.getWorkbook(), CellUtil.DATA_FORMAT, (short)0x31);
					}
					
					if(token.contains("font=")){
						CellUtil.setFont(cell, sheet.getWorkbook(), (Font)ctx.get("BOLD_format"));
					}
				}
			}
			
			if(StringUtils.isNumeric(value.toString()) && value.toString().startsWith("0")){
				cell.setCellValue(value.toString());
				cell.getCellStyle().setDataFormat((short)0x31);
				return;
			}
		}catch (Exception e) {
			
		}
		
		if(isFieldTypeFound)
			return;
		
		//Added code to handle alignment of text
		/*if(colKey.toLowerCase().contains("_textalign")){
			String align = colKey.substring(colKey.lastIndexOf("_textalign")+11, colKey.length());
			colKey = colKey.substring(0, colKey.indexOf("_textalign"));
			cell.setCellType(Cell.CELL_TYPE_STRING);
			short algn = -1;
			if(align != null){
				if(align.equalsIgnoreCase("left"))
					algn = HSSFCellStyle.ALIGN_LEFT;
				else if(align.equalsIgnoreCase("right"))
					algn = HSSFCellStyle.ALIGN_RIGHT;
				else if(align.equalsIgnoreCase("center"))
					algn = HSSFCellStyle.ALIGN_CENTER;
				
				//CellStyle style = sheet.getWorkbook().createCellStyle();
				if(algn > 0)
					style.setAlignment(algn);
				
				cell.setCellStyle(style);
			}
		}*/
		//Ended code
		
		switch (cell.getCellStyle().getDataFormat()) {
			case 5: {
				if (value == null)
					value = "";
	
				if (!"".equals(value)){
					try{
						cell.setCellValue(Double.parseDouble(value.toString()));
					}catch (Exception e) {
						cell.setCellValue(value.toString());
					}
				}else
					cell.setCellValue(Double.parseDouble("0"));
	
				break;
			}
			case 9: {
				if (value == null)
					value = "";
	
				if (!"".equals(value)){
					try{
						cell.setCellValue(Double.parseDouble(value.toString())/100);
					}catch (Exception e) {
						cell.setCellValue(value.toString());
					}
				}else
					cell.setCellValue(0);
	
				break;
			}
			case 10: {
				if (value == null)
					value = "";
	
				if (!"".equals(value)){
					try{
						cell.setCellValue(Double.parseDouble(value.toString())/100);
					}catch (Exception e) {
						cell.setCellValue(value.toString());
					}
				}else
					cell.setCellValue(0);
	
				break;
			}
			case 38: {
				if (value == null)
					value = "";
				if (!"".equals(value)){
					try{
						cell.setCellValue(Double.parseDouble(value.toString()));
					}catch (Exception e) {
						cell.setCellValue(value.toString());
					}
				}else
					cell.setCellValue(0);
	
				break;
			}
			case 210: {
				if (value == null)
					value = "";
				if (!"".equals(value)){
					try{
						cell.setCellValue(Double.parseDouble(value.toString()));
					}catch (Exception e) {
						cell.setCellValue(value.toString());
					}
				}
				else
					cell.setCellValue(0);
	
				break;
			}
			case 164: {
				if (value == null)
					value = "";
	
				if (!"".equals(value)){
					try{
						cell.setCellValue(Double.parseDouble(value.toString())/100);
					}catch (Exception e) {
						cell.setCellValue(value.toString());
					}
				}else
					cell.setCellValue(0);
	
				break;
			}
			case 166: {
				if (value == null)
					value = "";
	
				if (!"".equals(value)){
					try{
						cell.setCellValue(Double.parseDouble(value.toString())/100);
					}catch (Exception e) {
						cell.setCellValue(value.toString());
					}
				}else
					cell.setCellValue(0);
	
				break;
			}
			case 167: {
				if (value == null)
					value = "";
	
				if (!"".equals(value)){
					try{
						cell.setCellValue(Double.parseDouble(value.toString())/100);
					}catch (Exception e) {
						cell.setCellValue(value.toString());
					}
				}else
					cell.setCellValue(0);
	
				break;
			}
			case 170: {
				if (value == null)
					value = "";
	
				if (!"".equals(value)){
					try{
						cell.setCellValue(Integer.parseInt(value.toString()));
					}catch (Exception e) {
						cell.setCellValue(value.toString());
					}
				}else
					cell.setCellValue("");
	
				break;
			}
			case 14: {
				if (value == null)
					value = "";
				if (!"".equals(value)) {
					try {
						DateFormat formatter;
						java.util.Date date;
						formatter = new SimpleDateFormat("MM/dd/yyyy");
						date = (java.util.Date) formatter
								.parse(getFormattedDateFromObject(value));
						cell.setCellValue(date);
					} catch (Exception e) {
						System.out.println("Exception :" + e);
					}
					// DateUtil.isValidExcelDate(Double.parseDouble(getFormattedDateFromObject(value)));
				} else
					cell.setCellValue(0);
				break;
			}
			case 203: {
				if (value == null)
					value = "";
				if (!"".equals(value)) {
					try {
						DateFormat formatter;
						java.util.Date date;
						formatter = new SimpleDateFormat("MM/dd/yyyy");
						date = (java.util.Date) formatter
								.parse(getFormattedDateFromObject(value));
						cell.setCellValue(date);
					} catch (Exception e) {
						System.out.println("Exception :" + e);
					}
					// DateUtil.isValidExcelDate(Double.parseDouble(value.toString()));
				} else
					cell.setCellValue(0);
	
				break;
			}
			case 49: {
				if (value == null)
					value = "";
				try{
					cell.setCellValue(Integer.parseInt(value.toString()));
				}
				catch(NumberFormatException nfe){
					cell.setCellValue(value.toString());
				}		
	
				break;
			}
			default: {
				if (value == null)
					value = "";
				try{
					cell.setCellValue(Integer.parseInt(value.toString()));
				}
				catch(NumberFormatException nfe){
					cell.setCellValue(value.toString());
				}	
	
				break;
			}
		}
		// cell.setCellValue(value.toString());
	}
  
  public static String getCellValue(Sheet mainSheet, int rowNum, int columnNum)
  {
    Cell cell = getCell(mainSheet, rowNum, columnNum);
    return cell.getStringCellValue();
  }
  
  public static void setCellValueInContext(Context ctx, String setKey, Sheet mainSheet, int rowNum, int columnNum)
  {
    String setValue = getCellValue(mainSheet, rowNum, columnNum);
    if ((setValue != null) && (setValue.endsWith(":"))) {
      setValue = setValue.substring(0, setValue.lastIndexOf(":"));
    }
    ctx.put(setKey, setValue);
  }
  
  public static String retifyString(String st)
  {
    if ((st != null) && (st.endsWith(":"))) {
      return st.substring(0, st.lastIndexOf(":"));
    }
    return st;
  }
  
  public static Object convertTypetoString(Cell cell1)
  {
    switch (cell1.getCellType())
    {
    case 0: 
      switch (cell1.getCellStyle().getDataFormat())
      {
      case 9: 
        return cell1.getNumericCellValue() * 100.0D;
      case 10: 
        return cell1.getNumericCellValue() * 100.0D;
      case 164: 
        return cell1.getNumericCellValue() * 100.0D;
      case 166: 
        return cell1.getNumericCellValue() * 100.0D;
      case 167: 
        return cell1.getNumericCellValue() * 100.0D;
      case 210: 
        return cell1.getNumericCellValue() * 100.0D;
      }
      return cell1.getNumericCellValue();
    case 1: 
      return cell1.getStringCellValue();
    case 3: 
      return "";
    case 2: 
      return cell1.getNumericCellValue();
    }
    return "";
  }
  
  public static String retifyNumericValue(String st)
  {
    if ((st != null) && (st.contains("."))) {
      return st.substring(0, st.lastIndexOf("."));
    }
    return st;
  }
  
  public static String getFormattedDateFromObject(Object object)
  {
    String value = "";
    if (object == null) {
      return value;
    }
    if ((object instanceof java.sql.Date)) {
      value = DateUtils.convertDateToString((java.sql.Date)object, 
        "MM/dd/yyyy");
    } else if ((object instanceof Timestamp)) {
      value = DateUtils.convertDateToString((Timestamp)object, 
        "MM/dd/yyyy");
    } else {
      value = object.toString();
    }
    return value;
  }
  
  public static void copyCellFormula(Sheet templateSheet, int startrownum, int lastrownum, int startcolumnnum, int lastcolumnnum)
  {
    for (int r = startrownum; r < lastrownum; r++)
    {
      Row rowOld = templateSheet.getRow(r);
      
      Row rowNew = templateSheet.createRow(r);
      rowNew.setHeight(rowOld.getHeight());
      for (int c = startcolumnnum; c < lastcolumnnum; c++)
      {
        Cell cellOld = rowOld.getCell(c);
        if (cellOld != null)
        {
          Cell cellNew = rowNew.createCell(c);
          
          copyCell(cellOld, cellNew);
          
          cellNew.setCellType(cellOld.getCellType());
          
          String content = checkType(cellOld);
          
          cellNew.setCellValue(content);
        }
      }
    }
  }
  
  public static void copyCell(Cell cellOld, Cell cellNew)
  {
    switch (cellOld.getCellType())
    {
    case 2: 
      cellNew.setCellFormula(cellOld.getCellFormula());
    }
  }
  
  public static String checkType(Cell cell)
  {
    String value = null;
    switch (cell.getCellType())
    {
    case 2: 
      value = cell.getCellFormula();
    }
    return value;
  }
}
