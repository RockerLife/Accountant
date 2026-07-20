package com.manage.util;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.util.Constants;
import com.util.Context;
import com.util.HtmlConstants;
import com.util.IContext;
import com.util.InetLogger;
import com.util.StringUtils;


public abstract class FormUtils
{
	private static final String SYSTEM_VARS[] = {Constants.DYNAKEYS, Constants.DYNAVALUES, Constants.INET_PAGE, Constants.INET_PROJECT_ID, Constants.INET_NEXT_PAGE};
	private static InetLogger logger = InetLogger.getInetLogger(FormUtils.class);
	
	public static void populatePageName(HttpServletRequest httpRequest, IContext ctx) throws Exception
	{
		String servletPath = httpRequest.getServletPath();
		int index = servletPath.lastIndexOf("/");
		if(index==-1)
			throw new Exception("Invalid request " + servletPath);
		
		int lastIndex = servletPath.lastIndexOf(".");
		
		servletPath = servletPath.substring(index+1, lastIndex);
		ctx.put(Constants.INET_PAGE, servletPath);
	}
	
	public static void populateSessionData(HttpServletRequest httpRequest, IContext ctx)
	throws Exception
	{
		Enumeration sessionAttributes = httpRequest.getSession().getAttributeNames();
		if(sessionAttributes==null)
			return;
		
		while(sessionAttributes.hasMoreElements())
		{
			String key = (String) sessionAttributes.nextElement();
			Object value = httpRequest.getSession().getAttribute(key);
			ctx.put(key, value);
		}
	}
	
	private static boolean isSystemParameter(String name)
	{
		for(int i=0; i<SYSTEM_VARS.length; i++)
			if(SYSTEM_VARS[i].equals(name))
				return true;
			
		return false;
	}
	
	public static void populateRequestData(HttpServletRequest httpRequest, Context ctx) throws Exception {
		/*if(ServletFileUpload.isMultipartContent(httpRequest)){
			populateMultipartFormData(httpRequest, ctx);
		}else {*/
			Enumeration paramNames = httpRequest.getParameterNames();
			String name = null;
			String val[] = null;
			
			while(paramNames.hasMoreElements()){
				name = (String) paramNames.nextElement();
				val = httpRequest.getParameterValues(name);
			
				ctx.put(name, resolveRequestParameterValue(val[0]));
			}
		//}
		
		Enumeration attNames = httpRequest.getAttributeNames();
		name = null;
		String value = null;
		
		while(attNames.hasMoreElements()){
			name = (String) attNames.nextElement();
			if(httpRequest.getAttribute(name) == null)
				continue;
			
			value = httpRequest.getAttribute(name).toString();
		
			ctx.put(name, resolveRequestParameterValue(value));
		}	
			
		populateDynaKeys(httpRequest, ctx);
	}		
	
	public static void populateRequestDataWithMultiSelect(HttpServletRequest httpRequest, Context ctx) throws Exception {
		Enumeration paramNames = httpRequest.getParameterNames();
		String name = null;
		String val[] = null;
		
		while (paramNames.hasMoreElements()){
			name = (String) paramNames.nextElement();
			if(name != null && name.equals("REQUEST_AUTH_TOKEN")) //Bcoz new REQUEST_AUTH_TOKEN already generated and updated in Session which will have to be set in Context
				continue;
			
			val = httpRequest.getParameterValues(name);
			ctx.put(name, processRequestParamVal(val));
			
		}
		
		populateDynaKeys(httpRequest, ctx);
	}
	
	public static void populateServerInfo(HttpServletRequest request, Context ctx)
	throws Exception
	{
		String referer = request.getHeader("referer");		
		ctx.put(Constants.HTTPREQUEST, "referer", referer);	

	}
	
	public static void populateDynaKeys(HttpServletRequest request, IContext populate_ctx) throws Exception {
		String keys = null;
		String values = null;
		
		/*if(ServletFileUpload.isMultipartContent(request)){
			keys = populate_ctx.get(Constants.DYNAKEYS) != null ? populate_ctx.get(Constants.DYNAKEYS).toString() : null;
			values = populate_ctx.get(Constants.DYNAVALUES) != null ? populate_ctx.get(Constants.DYNAVALUES).toString() : null;
		}else{*/
			keys = request.getParameter(Constants.DYNAKEYS);
			values = request.getParameter(Constants.DYNAVALUES);
			
			if(StringUtils.isBlank(keys))
				keys = request.getAttribute(Constants.DYNAKEYS) != null ? request.getAttribute(Constants.DYNAKEYS).toString() : HtmlConstants.EMPTY;
			if(StringUtils.isBlank(values))
				values = request.getAttribute(Constants.DYNAVALUES) != null ? request.getAttribute(Constants.DYNAVALUES).toString() : HtmlConstants.EMPTY;	
		//}
			
		if (keys != null && values != null && keys.length() > 0 && values.length() > 0){
			if(values.indexOf("_HASH_") != -1)
				values = values.replaceAll("_HASH_", "#");
			
			populateDynaKeys(keys, values, populate_ctx);
		}	
	}
	
	public static void populateDynaKeys(IContext ctx, IContext populate_ctx)
	throws Exception
	{
		String keys = (String) ctx.get(Constants.DYNAKEYS);
		String values = (String) ctx.get(Constants.DYNAVALUES);
		if (keys != null && values != null && keys.length() > 0 && values.length() > 0)
			populateDynaKeys(keys, values, populate_ctx);
	}
	
	private static void populateDynaKeys(String keys, String values, IContext ctx)
	throws Exception
	{
		String[] keysList = StringUtils.split(keys, Constants.DELIMITER);
		String[] valuesList = StringUtils.split(values, Constants.DELIMITER);
		
		if(keysList==null)
			return;
		
		for(int i=0; i<keysList.length; i++)
		{
			if(valuesList[i].equals(Constants.SYMBOL))
				valuesList[i] = "";
			
			valuesList[i] = resolveRequestParameterValue(valuesList[i]);
			
//			if(valuesList[i].contains("$APH$")){
//				valuesList[i] = valuesList[i].replace("$APH$", "'");
//			}
						
			ctx.put(keysList[i], valuesList[i].trim());
		}
		
	}
	
	public static void populatePagination(HttpServletRequest request, IContext populate_ctx) throws Exception 
	{
		if(null!=request.getParameter ("recordsperpage"))
			populate_ctx.put("recordsperpage",  request.getParameter ("recordsperpage"));
		if(null!=request.getParameter ("pagenumber"))
			populate_ctx.put("pagenumber",  request.getParameter ("pagenumber"));
//		populate_ctx.put("paginationaction",request.getParameter("paginationaction"));
		
	}
	
	public static boolean isFormDirty(IContext ctx)
	{
		return Constants.YES.equals(ctx.get(Constants.INET_FORM_DIRTY));
	}
	
	public static boolean isPageReadonly(IContext ctx)
	{
		return Constants.YES.equals(ctx.get(Constants.INET_READONLY_MODE));
	}
	
	public static boolean isBlockReadonly(String blkId, IContext ctx)
	{
		return Constants.YES.equals(ctx.get(blkId, Constants.INET_READONLY_MODE));
	}
	
//    public static IContext resolveHTMLFields(Map data)
//    {
//        if(data==null)
//            return null;
//        
//        IContext resolvedData = new Context();
//
//        Iterator keyIt = data.keySet().iterator();
//        while (keyIt.hasNext())
//        {
//            Object key = keyIt.next();
//            Object value = data.get(key);
//
//            resolvedData.put(key, value);
//            if(value == null)
//                continue;
//
//            if (!(value instanceof HTMLField))
//                continue;
//            
//            value = ((HTMLField) value).getValue();
//            resolvedData.put(key, value);
//        }
//
//        return resolvedData;
//    }	
	
	private static String resolveRequestParameterValue(String value){
		
		if(value != null && !"".equals(value)){
			
			if(value.contains("$AND$"))
				value = value.replace("$AND$", "&");
			if(value.contains("$PER$"))
				value = value.replace("$PER$", "%");
			if(value.contains("$APH$"))
				value = value.replace("$APH$", "'");
			if(value.contains("#AMP#"))
				value = value.replace("#AMP#", "&");
			if(value.contains("$AMP$"))
				value = value.replace("$AMP$", "&");
		}
		
		return value;
	}
	
	private static String processRequestParamVal(String[] val){
		
		String retVal=null;
		
		if(val.length > 1)
		{
			StringBuilder buffer = new StringBuilder();
			
			for(String str : val){
				if(str != null && !"".equals(str.trim()))
					buffer.append(resolveRequestParameterValue(str)).append(",");				
			}
			if(buffer != null && !"".equals(buffer.toString()))
				return buffer.substring(0,buffer.lastIndexOf(","));
			else
				return null;
		}
		else 
			return resolveRequestParameterValue(val[0]);
	}
	
	public static void populateMultipartFormData(HttpServletRequest req, IContext ctx) throws Exception {
		ServletFileUpload upload = new ServletFileUpload(new DiskFileItemFactory());
		List formDataList = upload.parseRequest(req);
		if(formDataList != null && formDataList.size() > 0){
			boolean isDocumentUploadFound = false;
			Map<String, String> multiPartFormDataMap = new HashMap<String, String>(); // created to get errors for xslt only
			for(int i=0; i<formDataList.size(); i++){
				FileItem item = (FileItem)formDataList.get(i);
				if(item != null && item.isFormField()){
					String key = item.getFieldName();
					String value = item.getString();
					
					/*if(key.equals("agency_planlob_id") || key.equals("dynaKeys") || key.equals("dynaValues"))
						System.out.println("Found === " + key + " = " + value);*/
					
					ctx.put(key, value);
					
					//setting for error html page
					req.setAttribute(key, value);
					
					//Added code to populate multipartdatalist to show errors incase of xslt
					multiPartFormDataMap.put(key, value);
				}else{
					isDocumentUploadFound = true;
				}
			}
			
			if(isDocumentUploadFound)
				ctx.put("fileItems", formDataList);
			
			ctx.put(Constants.MULTIPART_FORMDATA_MAP, multiPartFormDataMap);
		}
	}
	
	public static void populateMultipartDynaKeysForBreadCrumb(HttpServletRequest req, IContext ctx) throws Exception {
		if(!ServletFileUpload.isMultipartContent(req))
			return;
		
		ServletFileUpload upload = new ServletFileUpload(new DiskFileItemFactory());
		List formDataList = upload.parseRequest(req);
		if(formDataList != null && formDataList.size() > 0){
			for(int i=0; i<formDataList.size(); i++){
				FileItem item = (FileItem)formDataList.get(i);
				if(item != null && item.isFormField()){
					String key = item.getFieldName();
					String value = item.getString();
					
					if(key.equals(Constants.DYNAKEYS) || key.equals(Constants.DYNAVALUES)){
						ctx.put(key, value);
					}
				}
			}
		}
	}
	
}
