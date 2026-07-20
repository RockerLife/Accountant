/*
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.pdf;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.PageSize;
import com.lowagie.text.pdf.PdfWriter;
import com.ormapping.ibatis.SqlResources;
import com.util.Context;
import com.util.InetLogger;
import com.util.SystemProperties;

 
 
 
public class PrintPDF  {
	
	private static final long serialVersionUID = 2788260006560387781L;	
	
	protected static InetLogger logger = InetLogger.getInetLogger(PrintPDF.class);
	public static void main(String[] args) throws Exception {
		Context ctx = new Context();
		String projectName = "AccountantIns";
		String resourcePath = SystemProperties.getInstance().getString(
				"xml.basedir")
				+ projectName + "//ibatis//maps//SqlMapConfig.xml";
		SqlResources.load(projectName, resourcePath);
		
		ctx.setProject(projectName);
		ctx.put("PolicyKey", "1156");
		ctx.put("TransactionSequence", "1677");
		ctx.put("VersionSequence", "1038");
		ctx.put("CoverageSequence", "1647");
		ctx.put("AccountKey", "1167");
		ctx.put("AddressKey", "1157");		
		ctx.put("UserNo","4");
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
	 	Document document = new Document(PageSize.LETTER,30f,30f,50f,30f);
	 	PdfWriter writer=PdfWriter.getInstance(document, baos);		
	 	writer.setPageEvent(new PdfGeneratorPageEvent());
	 	
		new PDFGenerator().execute(document, baos, ctx,writer);
		document.close();
		FileOutputStream out = new FileOutputStream("C://Documents and Settings//raghurajs//Desktop//AccountantIns.pdf");
	        
        baos.writeTo(out);
		out.flush(); 
		out.close();  
		
	}
	

    public void writePDF() {
    	logger.debug("into print pdf's method make pdf.");
		ByteArrayOutputStream baos = null;
	 	Document document = new Document(PageSize.A4);
		
		 
			//queryString = request.getQueryString();
			baos = new ByteArrayOutputStream();
			logger.debug(baos.toString());
			try {
				PdfWriter pdfWriter = PdfWriter.getInstance(document, baos);
//				PdfWriter.getInstance(document,new FileOutputStream("Auto Quote Summary"));
				pdfWriter.toString();
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.error(e);
			} 
    }
    
    public ByteArrayOutputStream makePdf(Context ctx) {
    	
    	ByteArrayOutputStream baos = null;
    	Document document = new Document(PageSize.LETTER,30f,30f,50f,30f); 	    	
		try {
			
			PdfWriter writer;
			baos = new ByteArrayOutputStream();			
			writer=PdfWriter.getInstance(document, baos);	
			
			Object objAddress = SqlResources.getSqlMapProcessor(ctx).findByKey(
					"SqlStmts.BasicInfoviewgetAddressdetails", ctx);
			
			String StateCode = null;
			 if(objAddress != null && objAddress instanceof Map)
			 {
				 Map mapAddress = (Map) objAddress;
				 if(mapAddress.get("StateCode") != null)
					 StateCode = mapAddress.get("StateCode").toString();
			 }
			
			if(StateCode == null)
				writer.setPageEvent(new PdfGeneratorPageEvent());
			else
				writer.setPageEvent(new PdfGeneratorPageEvent(StateCode));
			
			//this is the calling where we going to add in to document.
			
//			if(ctx.get("PolicyStatusKey") != null && !"".equals(ctx.get("PolicyStatusKey").toString())){
//				
//				if("1".equals(ctx.get("PolicyStatusKey").toString()))
//					new PDFGenerator().execute(document, null, ctx, writer);
//				else if("2".equals(ctx.get("PolicyStatusKey").toString()))
//					new PDFGenerator().executeRenew(document, null, ctx, writer);
//					
//			}
			logger.debug("Going to call PDFGenerator");
			new PDFGenerator().execute(document, null, ctx, writer);
			logger.debug("PDFGenerator ends " + new Date());
			document.close();	
			
			return baos;
			
		}catch(DocumentException de) {
			logger.error(de.getMessage());
		}
		catch (Exception e2) {
			logger.error("Error in " + getClass().getName() + "\n" + e2);
			e2.printStackTrace();
		}		
		finally{
			if(document != null)
				document.close();			
		}
		
		return null;
	}
	

	public void makePdf(Context ctx, HttpServletRequest request, HttpServletResponse response) {
		
		ByteArrayOutputStream baos = null;
		ServletOutputStream out = null;
		Document document = new Document(PageSize.LETTER,30f,30f,50f,30f);
	 	FileOutputStream fos = null;
		try {
			//System.out.println("into print pdf's method make pdf.");
			PdfWriter writer;
			if(request != null)
			{
				//queryString = request.getQueryString();
				baos = new ByteArrayOutputStream();
				System.out.println(baos.toString());
				writer=PdfWriter.getInstance(document, baos);
			}
			else
			{		
				fos = new FileOutputStream("AccountantSummary");
				writer=PdfWriter.getInstance(document,fos);
			}
		
			writer.setPageEvent(new PdfGeneratorPageEvent());
			//this is the calling where we going to add in to document.
			new PDFGenerator().execute(document, baos, ctx, writer);
			document.close();
			
			if(response != null)
			{
				// setting some response headers
				response.setHeader("Expires", "0");
				//response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
				response.setHeader("Content-disposition","attachment; filename=\"" + "Accountant_" + ctx.get("QuoteNumber").toString() + ".pdf\"");				
				response.setHeader("Pragma", "public");
				
				// setting the content type				
				// response.setHeader("Content-Disposition","attachment;filename=Timesheet.pdf");
			    response.setContentType("application/pdf");
				
				// the contentlength is needed for MSIE!!!
				response.setContentLength(baos.size());
				
				// write ByteArrayOutputStream to the ServletOutputStream
				out = response.getOutputStream();
				baos.writeTo(out);
				out.flush();				 
			}
		
			
		}catch(DocumentException de) {
            System.err.println(de.getMessage());
		}
		catch (Exception e2) {
			System.out.println("Error in " + getClass().getName() + "\n" + e2);
			e2.printStackTrace();
		}
		finally{
			if(document != null)
				document.close();
			if(baos != null)
				try {
					baos.close();
				} catch (IOException e) {
					
					e.printStackTrace();
				}
			if(out != null)
				try {
					out.close();
				} catch (IOException e) {
					
					e.printStackTrace();
				}
			
			
		}
	}
	/**
	 * @see javax.servlet.GenericServlet#destroy()
	 */
	public void destroy() {
		
	}
	


}
