package com.userbo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.util.InetLogger;
import com.util.StringUtils;

public class AccountantDateUtils {

	/**
	 * This method takes input date in any format and returns day from the input
	 * date
	 * 
	 * @param objDate
	 * @return
	 */
	private static InetLogger logger = InetLogger.getInetLogger(AccountantDateUtils.class);
	private static String SQL_DATE_PATTERN = "yyyy-MM-dd";
	public static String getDayFromDate(Date objDate) {

		if (objDate == null) {
			return "";
		}

//		Date date = (Date) objDate;
//		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
//		String strDate = formatter.format(date);
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(objDate);
		int day = cal.get(Calendar.DAY_OF_MONTH);
		String strDay = String.valueOf(day);
		return strDay;
	}
	
	public static Date formatDate(Date date){
		
		
		
		SimpleDateFormat strFormat = new SimpleDateFormat("MM/dd/yyyy");
		String strDate = strFormat.format(date);
		Date newDate = new Date(strDate);
		
		return newDate;
	}

	/**
	 * This method takes input date in any format and returns Month from the
	 * input date
	 * 
	 * @param objDate
	 * @return
	 */
	public static String getMonthFromDate(Date objDate) {

		if (objDate == null) {
			return "";
		}

//		Date date = (Date) objDate;
//		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
//		String strDate = formatter.format(date);
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(objDate);
		int month = cal.get(Calendar.MONTH);

		String strMonth = String.valueOf(month);

		return strMonth;
	}
	
	public static long calculateDiffInDays(Date date1, Date date2)
	{
		if(date1 == null || date2 == null)
            return 0;
		
		Date date3=formatDate(date1);
		System.out.println(date3);
		Date date4=formatDate(date2);
		System.out.println(date4);
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(date3);
		Calendar cal1  = Calendar.getInstance();
		cal1.setTime(date4);
		
		long time1 = cal.getTimeInMillis();
		long time2 = cal1.getTimeInMillis();
		long timeInMilli = time1 - time2;
		Long numOfDays = timeInMilli/(24*60*60*1000);
		System.out.println("Number  of Days is - - - - - >" + numOfDays);
		return numOfDays;
		/*
		if(date1 == null || date2 == null)
            return 0;
		
		long lDifferenceInMS = 0;
		long lDays = 0;
		
		lDifferenceInMS = date1.getTime() - date2.getTime();
		lDays = lDifferenceInMS / (24*60*60*1000);
	
		return lDays;
		*/
	}

	/**
	 * This method takes input date in any format and returns Year from the
	 * input date
	 * 
	 * @param objDate
	 * @return
	 */
	public static Date convertStringToDate(String value, String pattern)
    {
          try
          {
                if (StringUtils.isBlank(value))
                      return null;
                
               // return new SimpleDateFormat(pattern).parse(value);
                return new SimpleDateFormat(pattern).parse(new SimpleDateFormat(pattern).format(new SimpleDateFormat("MM/dd/yyyy").parse(value)));
          }
          catch(ParseException pe)
          {
                try{
                      return new SimpleDateFormat(SQL_DATE_PATTERN).parse(value);
                }
                catch(Exception e)
                {
                    
                	//logger.trace(e);
                      return null;
                }                 
          }
          catch(Exception e)
          {
        	 
                //logger.trace(e);
                return null;
          }
    }
	public static int getYearFromDate(Date date) {
		
		if (date == null) {
			return 0;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		
		logger.debug(cal.get(Calendar.YEAR));
		return cal.get(Calendar.YEAR);


		/*Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		int currentYear = cal.get(Calendar.YEAR);
		
		if((currentYear-1 == intYear) || (currentYear+1 == intYear)){
			ctx.put("isDifferenceGreater", "Y");
		}*/

	}
	
//	if(isLessThan(getYearFromDate(:PolicyEffectiveDate),2010)
//			=>isEqual(1,2)->
//			orCondition(orCondition(isEqual(add(getYearFromDate(getTodaysDate()),-1),getYearFromDate(:PolicyEffectiveDate)),getYearFromDate(:PolicyEffectiveDate)),isEqual(add(getYearFromDate(getTodaysDate()),1)),isEqual(getYearFromDate(getTodaysDate()),getYearFromDate(:PolicyEffectiveDate))));"/>



	
	
//	public static int getYearFromDate(Context ctx) {
//
//		Object objDate = ctx.get("PolicyEffectiveDate");
//		if (objDate == null) {
//			return 0;
//		}
//		String strObjDate = objDate.toString();
//
//		String year = strObjDate.substring(6);
//
//		int intYear = Integer.parseInt(year);
//
//		Calendar cal = Calendar.getInstance();
//		cal.setTime(new Date());
//		int currentYear = cal.get(Calendar.YEAR);
//
//		if (currentYear >= 2012) {
//
//			if (currentYear > intYear) {
//				int difference = currentYear - intYear;
//				if (difference >= 2) {
//					ctx.put("isDifferenceGreater", "Y");
//				}
//			} 
//			else {
//				int difference = intYear - currentYear  ;
//				if (difference >= 2) {
//					ctx.put("isDifferenceGreater", "Y");
//				}
//			}
//
//		}
//	
//		System.out.println("year----" + year);
//
//		return intYear;
//	}

	/*
	 * // takes strDate in format mm/dd/yyyy public Boolean
	 * isDayOfDateEqual(Context ctx) {
	 * 
	 * String strDate1 = (String)ctx.get("PriorActDatePross"); String strDate2 =
	 * (String)ctx.get("PolicyEffectiveDate"); if (strDate1 != null && strDate2 !=
	 * null) {
	 * 
	 * Calendar cal = Calendar.getInstance(); cal.setTime(new Date(strDate1));
	 * int day1 = cal.get(Calendar.DAY_OF_MONTH);
	 * 
	 * cal.setTime(new Date(strDate2)); int day2 =
	 * cal.get(Calendar.DAY_OF_MONTH);
	 * 
	 * System.out.println(day1); System.out.println(day2);
	 * 
	 * if (day1 == day2) { System.out.println("inside true"); return true; }
	 * else { System.out.println("inside false"); return false; } }
	 * 
	 * System.out.println(" before retuning false"); return false; }
	 * 
	 * public Boolean isMonthOfDateEqual(Context ctx) {
	 * 
	 * String strDate1 = (String)ctx.get("PriorActDatePross"); String strDate2 =
	 * (String)ctx.get("PolicyEffectiveDate"); if (strDate1 != null && strDate2 !=
	 * null) {
	 * 
	 * Calendar cal = Calendar.getInstance(); cal.setTime(new Date(strDate1));
	 * int month1 = cal.get(Calendar.MONTH);
	 * 
	 * cal.setTime(new Date(strDate2)); int month2 = cal.get(Calendar.MONTH);
	 * 
	 * System.out.println(month1); System.out.println(month2);
	 * 
	 * if (month1 == month2) { System.out.println("inside true"); return true; }
	 * else { System.out.println("inside false"); return false; } }
	 * 
	 * System.out.println(" before retuning false"); return false; }
	 */
	public static void main(String a[]) throws ParseException {

		//AccountantDateUtils obj = new AccountantDateUtils();
		// Date objDate = new Date();
		
		String datetime = "Apr 4 2011 7:15:20:687PM";		
	
		Calendar cal = Calendar.getInstance();
		int day = cal.get(Calendar.HOUR);
		
		System.out.println(day);
//		SimpleDateFormat strFormat = new SimpleDateFormat("MM/dd/yyyy");
//		Date strDate = strFormat.parse(datetime);
//		System.out.println(strDate);

//			 SimpleDateFormat formatterdate = new SimpleDateFormat("hh:mm:ss");
//				String strDate = formatterdate.format(date);

			
	}

	//Endorsement Start
	public static String getCurrentDate(){		
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		String strDate = formatter.format(date);
		return strDate;
	}
}
