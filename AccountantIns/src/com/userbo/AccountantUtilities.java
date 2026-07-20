package com.userbo;

import java.io.File;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.apache.commons.fileupload.FileItem;

import com.util.Context;
import com.util.HtmlConstants;
import com.util.InetLogger;

public class AccountantUtilities {
	
	private static final String CHAR_LIST = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static final int RANDOM_STRING_LENGTH = 10;
	public static String DATE_PATTERN = "MM-dd-yyyy hh:mm:ss";
	private static InetLogger logger = InetLogger.getInetLogger(AccountantUtilities.class);
	
	public static Object amountFormatLawyers(Object arg1){
	    if(arg1 == null || arg1.equals(HtmlConstants.EMPTY))
	        return 0;
	    
	    String amount = arg1.toString();      
		DecimalFormat df = new DecimalFormat("$###,###.###"); 
	    return df.format(Double.parseDouble(amount));
	}
	public static String generateUniqueId()
	{
		//System.out.println("ASDADASDASSADASD");
		String uniqueId ="";
		  try {
			  uniqueId = new Timestamp(new Date().getTime()).toString().replaceAll("[.,:\\ ]", "");
		//System.out.println("uniqueId" + uniqueId);
		  }
		  catch (Exception e) {
			  uniqueId="Not Found";
		  }
		  return uniqueId;
	}
	
	public static String generateRandomString(){
	    
	    StringBuffer randStr = new StringBuffer();
	    for(int i=0; i<RANDOM_STRING_LENGTH; i++){
	        int number = getRandomNumber();
	        char ch = CHAR_LIST.charAt(number);
	        randStr.append(ch);
	    }
	    return randStr.toString();
	}
	
	private static int getRandomNumber() {
	    int randomInt = 0;
	    Random randomGenerator = new Random();
	    randomInt = randomGenerator.nextInt(CHAR_LIST.length());
	    if (randomInt - 1 == -1) {
	        return randomInt;
	    } else {
	        return randomInt - 1;
	    }
	}
	public static String getCurrentDate() {
		return new  SimpleDateFormat("MM/dd/yyyy").format(new Date());
	}
	
	public static String uploadFile(Context ctx, String uploaddirectory) throws Exception
	{
		String filePath=null;
		FileItem uploadFile_logo = null;
		File tempFile1 = null;
		
		try {
			List fileItems = (List)ctx.get("fileItems");
			Iterator i = fileItems.iterator();
			
			while (i.hasNext()) {
				FileItem fi = (FileItem) i.next();
				if (!fi.isFormField()) {
					// filename on the client
					if("file_path".equals(fi.getFieldName())){
						filePath=fi.getName();
						uploadFile_logo = fi;
						ctx.put("ReportName",filePath);
					}
				}
			}
			System.out.println(filePath);	
		} catch (Exception e) {
			logger.debug("Error in file process");
			e.printStackTrace();
		}
		
		filePath = filePath + "_" + generateUniqueId();
		
		tempFile1 = new File(uploaddirectory);
		if (!tempFile1.exists()){
			tempFile1.mkdir();
		}
		try {
			uploadFile_logo.write(new File(uploaddirectory, filePath));
		} catch (Exception e) {
			System.out.println("Exception " + e );
			AccountUtils.populateError(ctx, "DocUploadError","Document could not be write to file" );
			
		}
		return filePath;
	}
	
	public static double getTwoDecimalPlaces(double input){
		DecimalFormat df2 = new DecimalFormat("#.##");
		
//	    System.out.println("double : " + input);
//	    System.out.println("double : " + df2.format(input));    //3.14
	    
	    return Double.parseDouble(df2.format(input));

//	    // DecimalFormat, default is RoundingMode.HALF_EVEN
//	    df2.setRoundingMode(RoundingMode.DOWN);
//	    System.out.println("\ndouble : " + df2.format(input));  //3.14
//
//	    df2.setRoundingMode(RoundingMode.UP);
//	    System.out.println("double : " + df2.format(input));    //3.15
	}
}
