package com;


import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.ormapping.ibatis.SqlResources;
import com.pdf.PrintPDF;
import com.util.Context;
import com.util.InetLogger;
import com.util.SystemProperties;

public class AccountantServlet_old extends HttpServlet {
	
	protected Context ctx;
	protected String project;	
	protected static InetLogger logger = InetLogger.getInetLogger(AccountantServlet_old.class);
	protected static String project_resource = "AccountantIns";
	
	public void init() throws ServletException {
		
		try {
						
	    	loadResource(project_resource, "ibatisconfig");
	    	
		} catch (Exception e) {
			logger.error("Project resources are not initialized properly");
		}			
	}
	
	protected void loadResource(String projectName, String resourceType) throws Exception
	{
		String resourcePath = null;
		
		if(!resourceType.equals("ibatisconfig"))
		{
			resourcePath = SystemProperties.getInstance().getString("xml.basedir") + projectName +"//" + resourceType +".xml";
			//ResourceLoader.load(resourcePath, resourceType);
		}
		else
		{
			resourcePath = SystemProperties.getInstance().getString("xml.basedir") + projectName +"//ibatis//maps//SqlMapConfig.xml";
			SqlResources.load(projectName, resourcePath);	        
		}
	}
	
	public void doGet(HttpServletRequest req,HttpServletResponse res) throws IOException, ServletException {
		executeAction(req,res,"");
	}
	
	public void doPost(HttpServletRequest req,HttpServletResponse res) throws IOException, ServletException {
		executeAction(req,res,"");
	}	
	
	public void executeAction(HttpServletRequest req,HttpServletResponse res,String strXml) {
		try{
			
			ctx = new Context(); 			
			ctx.setProject(project_resource);
			
			String downloadFlag =  req.getParameter("downloadflag");
			String downloadpdfFlag =  req.getParameter("downloadpdfflag");
			
			if(downloadFlag != null && "Y".equals(downloadFlag)){
				downloadExcel(req, res);
			}else if(downloadpdfFlag != null && "Y".equals(downloadpdfFlag))
			{
				downloadPDF(req, res);
			}
			else {
				fileUpload(req);
			}			
		}
		catch(Exception e){
			logger.error("Unable to process request due to..." + e.getMessage());
			System.out.println("Unable to process request due to..." + e.getMessage());
		}
		
		
	}
	
	private void downloadPDF(HttpServletRequest req, HttpServletResponse res) {
		
		ctx.put("PolicyKey", req.getParameter("PolicyKey"));
		ctx.put("TransactionSequence", req.getParameter("TransactionSequence"));
		ctx.put("VersionSequence", req.getParameter("VersionSequence"));
		ctx.put("CoverageSequence", req.getParameter("CoverageSequence"));
		ctx.put("AccountKey", req.getParameter("AccountKey"));
		ctx.put("AddressKey", req.getParameter("AddressKey"));
		
		logger.debug("DownloadPDF Start for Quote Number : " +req.getParameter("QuoteNumber"));
		new PrintPDF().makePdf(ctx, req, res);
		logger.debug("DownloadPDF end for Quote Number : " +req.getParameter("QuoteNumber"));		
	}

	private void downloadExcel(HttpServletRequest req,HttpServletResponse res) throws Exception{
		
		ctx.put("PolicyKey", req.getParameter("PolicyKey"));
		ctx.put("TransactionSequence", req.getParameter("TransactionSequence"));
		ctx.put("VersionSequence", req.getParameter("VersionSequence"));
		ctx.put("CoverageSequence", req.getParameter("CoverageSequence"));
		ctx.put("AccountKey", req.getParameter("AccountKey"));
		ctx.put("AddressKey", req.getParameter("AddressKey"));
		
		logger.debug("DownloadExcel Start for Quote Number : " +req.getParameter("QuoteNumber"));		
		res.setContentType("application/vnd.ms-excel");
		String newFileName = "Account_"+req.getParameter("policynumber")+".xls";
		res.setHeader("Content-disposition","attachment; filename=\"" + newFileName + "\"");				
		//new DownloadExcel().downloadExcel(ctx, req, res);				
		logger.debug("DownloadExcel End for Quote Number : " +req.getParameter("QuoteNumber"));		
	}
		
	
	private void fileUpload(HttpServletRequest req) throws Exception {
//		 Create a factory for disk-based file items
		FileItemFactory factory = new DiskFileItemFactory();
//		 Create a new file upload handler
		ServletFileUpload upload = new ServletFileUpload(factory);
//		 Parse the request
		List  /* FileItem */ items = upload.parseRequest(req ); 

//		 Process the uploaded items
		Iterator  iter = items.iterator();

		while (iter.hasNext()) {
		   FileItem item = (FileItem) iter.next();
		   //handling a normal form-field
		   if (item.isFormField()) {

		      String name = item.getFieldName();
		      String value = item.getString();
		      
		   } else {//handling file loads
		     
		      String fieldName = item.getFieldName();
		      String fileName = item.getName();
		      String contentType = item.getContentType();
		      boolean isInMemory = item.isInMemory();
		      long sizeInBytes = item.getSize();
		     
		      byte[] data = item.get();
		      InputStream inputStream =  item.getInputStream();
		      String  sheetName =   req.getParameter("uploadaction");
		      System.out.println("Upload Start for : "+ sheetName);      

//		      if(null!=sheetName)
//		    	  new UploadLookupData().extractDataFromExcel(  ctx, inputStream, sheetName);
		      
		      System.out.println("Upload End for : "+ sheetName);
		   }
		}

	}

}
