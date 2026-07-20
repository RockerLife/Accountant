/*
 * Created on May 30, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.manage.process;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.util.SystemProperties;




 /* @author Amit Jain
 *
 * TODO don't forget to add the javadoc!
 */
public class FileUploadServlet extends HttpServlet 
{
	
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            doPost(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) {
       
    	String dynaKeys = request.getParameter("dynaKeys");
        String dynaValues = request.getParameter("dynaValues");
        String inet_method = request.getParameter("inet_method");
        String inet_page = request.getParameter("inet_page");
        String QN = request.getSession().getAttribute("QuoteNumber") != null ? request.getSession().getAttribute("QuoteNumber").toString():"";
        String FileNameCorrect = "N";
        
        try {
		    DiskFileItemFactory factory = new DiskFileItemFactory();
		
			// maximum size that will be stored in memory
			factory.setSizeThreshold(25*1024*1024);
		
			// the location for saving data that is larger than getSizeThreshold()
			String tempDirectory = SystemProperties.getInstance().getString(
					"fileupload.tempdirectory");
			factory.setRepository(new File(tempDirectory));
			
			
			ServletFileUpload upload = new ServletFileUpload(factory);
		
			// maximum size before a FileUploadException will be thrown
			Long size = new Long(25*1024*1024);
			upload.setSizeMax(size.longValue());	
		
			List fileItems = upload.parseRequest(request);
		
			// assume we know there are two files. The first file is a small
			// text file, the second is unknown and is written to a file on
			// the server
			Iterator i = fileItems.iterator();
		
			String fileName = "";
			String DocTitle = "";
			String Comment = "";
			String DocFileName = "";
			FileItem uploadFile = null;			
			
			while (i.hasNext()) {
				FileItem fi = (FileItem) i.next();
		
				if (fi.isFormField()) {
				   String name = fi.getFieldName();
				   if(name.equalsIgnoreCase("DocTitle")){
					   DocTitle = fi.getString();
					   DocTitle = DocTitle.replace("&", "$AND$");
					   DocTitle = DocTitle.replace("%", "$PER$");
					   DocTitle = DocTitle.replace("'", "$APH$");
					   
				   }else if(name.equalsIgnoreCase("Comment")){
					   Comment = fi.getString();
					   Comment = Comment.replace("&", "$AND$");
					   Comment = Comment.replace("%", "$PER$");
					   Comment = Comment.replace("'", "$APH$");
				   }
				 //  String value = fi.getString();
				}else{ 
				
					// filename on the client
					fileName = fi.getName();
					uploadFile = fi;
					if (fileName == null) {
						continue;
					}
				}
				//break;
			}
				         
	        if((fileName == null || "".equals(fileName)) || (DocTitle == null || "".equals(DocTitle))  || (Comment == null || "".equals(Comment)))
	        {
	        	if(validateFileName(fileName))
	        		FileNameCorrect = "Y";	
	        	
	        	RequestDispatcher dispatcher = request.getRequestDispatcher("/upload.do?dynaKeys="+dynaKeys+"&dynaValues="+dynaValues+"&inet_method="+inet_method+"&inet_page="+inet_page+"&uploadSuccess=Y&DocTitle="+DocTitle+"&Comment="+Comment+"&DocFileName="+DocFileName+"&FileNameCorrect="+FileNameCorrect+"&file_path="+fileName);
	        }
	        else
	        {	 
	        	DocFileName = fileName.substring(fileName.lastIndexOf("\\")+1, fileName.length());
		        DocFileName = QN + "_" + DocFileName;
		        
	        	if(validateFileName(fileName))
		        {
	        		FileNameCorrect = "Y";			        		        		
					// save comment and filename to database
					// write the file
		        	String uploaddirectory = SystemProperties.getInstance().getString("fileupload.uploaddirectory");				
					uploadFile.write(new File(uploaddirectory, DocFileName));
				}	
	        }
	        
			RequestDispatcher dispatcher = request.getRequestDispatcher("/upload.do?dynaKeys="+dynaKeys+"&dynaValues="+dynaValues+"&inet_method="+inet_method+"&inet_page="+inet_page+"&uploadSuccess="+FileNameCorrect+"&DocTitle="+DocTitle+"&Comment="+Comment+"&DocFileName="+DocFileName+"&FileNameCorrect="+FileNameCorrect+"&file_path="+fileName);
		    dispatcher.forward(request,response);
		    
		} catch (Exception e) {
		    RequestDispatcher dispatcher = request.getRequestDispatcher("/upload.do?dynaKeys="+dynaKeys+"&dynaValues="+dynaValues+"&inet_method="+inet_method+"&inet_page="+inet_page+"&uploadSuccess=N&FileNameCorrect=N");
		    try {
				dispatcher.forward(request,response);
			} catch (ServletException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		    e.printStackTrace();
		}
    }
    
    public boolean validateFileName(String filePath) throws Exception
    {
		if(filePath == null)
			return true;
		
		if(filePath != null && "".equals(filePath.trim()))
			return true;
		
		int index = filePath.lastIndexOf("\\");
//		if(index <= 0)
//			return false;
		
		String fileName = null;
		if(index > 0)
		{
			fileName = filePath.substring(index+1, filePath.length());
			if(fileName.indexOf(".") <= 0)
				return false;
		}		
		else
			fileName = filePath;
		
		int indexExt = fileName.lastIndexOf(".");
		String ext = fileName.substring(indexExt+1, fileName.length());
		
		if(ext.length() < 3)
			return false;
		
		return true;
		
    }

    
   
}
