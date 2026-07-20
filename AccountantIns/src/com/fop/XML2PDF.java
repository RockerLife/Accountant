package com.fop;

//Java
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.OutputStream;
import java.io.StringReader;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;
import org.apache.fop.servlet.ServletContextURIResolver;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import com.util.Context;
import com.util.HtmlConstants;
import com.util.InetLogger;
//JAXP 
//FOP

public class XML2PDF {

	private String xmlFile = "data.xml";
	private String xslFile = "C://pdfGen.xsl";
	private String outFile = "C://result.pdf";
	private String foFile = "quoteletter.xml";
	private static InetLogger logger = InetLogger.getInetLogger(XML2PDF.class);

	public void convertPOToPDF(String foFile, String xmlFile, Context ctx,
			HttpServletRequest req, HttpServletResponse res,
			ServletContextURIResolver uriResolver, ServletContext servletContext) {
		ServletOutputStream out = null;

		try {
			File xmlfile = new File(xmlFile);
			File fofile = new File(foFile);

			// configure fopFactory as desired
			FopFactory fopFactory = FopFactory.newInstance();
			// tell the FOPFactory object where to look for resources
			fopFactory.setURIResolver((ServletContextURIResolver)ctx.get(HtmlConstants.DOCUMENTURIRESOLVER));

			// fopFactory.setUserConfig(new File("cfg.xml"));
			FOUserAgent foUserAgent = fopFactory.newFOUserAgent();
			foUserAgent
					.setBaseURL("file:///" + ((ServletContext)ctx.get(HtmlConstants.DOCUMENTSERVLETCONTEXT)).getRealPath("/"));

			// configure foUserAgent as desired
			// Setup output
			out = res.getOutputStream();

			try {
				if (res != null) {
					// setting some response headers
					res.setHeader("Expires", "0");
					res.setHeader("Content-disposition",
							"attachment; filename=\""
									+ "Accountant_Quote_Letter_"
									+ ctx.get("QuoteNumber").toString()
									+ ".pdf\"");
					res.setHeader("Pragma", "public");
				}

				// Construct fop with desired output format
				Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF,
						foUserAgent, out);

				// Setup XSLT
				TransformerFactory factory = TransformerFactory.newInstance();

				// tell the TransformerFactory where to find resources
				factory.setURIResolver((ServletContextURIResolver)ctx.get(HtmlConstants.DOCUMENTURIRESOLVER));

				Transformer transformer = factory
						.newTransformer(new StreamSource(fofile));

				// tell the Transformer object where to find resources
				transformer.setURIResolver((ServletContextURIResolver)ctx.get(HtmlConstants.DOCUMENTURIRESOLVER));

				// Setup input for XSLT transformation
				Source src = new StreamSource(xmlfile);

				// Resulting SAX events (the generated FO) must be piped through
				// to FOP
				Result result = new SAXResult(fop.getDefaultHandler());

				// Start XSLT transformation and FOP processing
				transformer.transform(src, result);

				out = res.getOutputStream();
				out.flush();

			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (out != null)
				try {
					out.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
		}
	}

	public OutputStream convertPOToPDF(String foFile, String xmlFile,
			OutputStream out,ServletContextURIResolver uriResolver, ServletContext servletContext,Context ctx) throws Exception {
		try {
			File xmlfile = new File(xmlFile);
			File fofile = new File(foFile);

			// configure fopFactory as desired
			FopFactory fopFactory = FopFactory.newInstance();
			FOUserAgent foUserAgent = fopFactory.newFOUserAgent();
			foUserAgent
			.setBaseURL("file:///" + ((ServletContext)ctx.get(HtmlConstants.DOCUMENTSERVLETCONTEXT)).getRealPath("/"));
			// Construct fop with desired output format
			Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent,
					out);

			// Setup XSLT
			TransformerFactory factory = TransformerFactory.newInstance();
			factory.setURIResolver(uriResolver);
			Transformer transformer = factory.newTransformer(new StreamSource(
					fofile));
			transformer.setURIResolver(uriResolver);
			// Set the value of a <param> in the stylesheet
			// transformer.setParameter("versionParam", "2.0");

			// Setup input for XSLT transformation
			Source src = new StreamSource(xmlfile);

			// Resulting SAX events (the generated FO) must be piped through to
			// FOP
			Result res = new SAXResult(fop.getDefaultHandler());

			// Start XSLT transformation and FOP processing
			transformer.transform(src, res);

			System.out.println("Success!");
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return out;
	}

	public void convertPOToPDF(String foFile, String xmlFile, OutputStream out,
			String baseUrl, ServletContextURIResolver uriResolver,Context ctx)
			throws Exception {
		// ServletOutputStream out = null;

		try {
			if(ctx.get(HtmlConstants.DOCUMENTSERVLETCONTEXT) != null)
				baseUrl = "file:///" + ((ServletContext)ctx.get(HtmlConstants.DOCUMENTSERVLETCONTEXT)).getRealPath("/");
			
			File xmlfile = new File(xmlFile);
			File fofile = new File(foFile);

			// configure fopFactory as desired
			FopFactory fopFactory = FopFactory.newInstance();
			// tell the FOPFactory object where to look for resources
			fopFactory.setURIResolver((ServletContextURIResolver)ctx.get(HtmlConstants.DOCUMENTURIRESOLVER));

			// fopFactory.setUserConfig(new File("cfg.xml"));
			FOUserAgent foUserAgent = fopFactory.newFOUserAgent();
			foUserAgent.setBaseURL(baseUrl);

			// configure foUserAgent as desired
			// Setup output
			// out = res.getOutputStream();

			try {
				// if(res != null)
				// {
				// // setting some response headers
				// res.setHeader("Expires", "0");
				// res.setHeader("Content-disposition","attachment; filename=\""
				// + "Accountant_Quote_Letter_" +
				// ctx.get("QuoteNumber").toString() + ".pdf\"");
				// res.setHeader("Pragma", "public");
				// }

				// Construct fop with desired output format
				Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF,
						foUserAgent, out);

				// Setup XSLT
				TransformerFactory factory = TransformerFactory.newInstance();

				// tell the TransformerFactory where to find resources
				factory.setURIResolver(uriResolver);

				/*Transformer transformer = factory
						.newTransformer(new StreamSource(fofile));*/
				Source xsltSrc = null;
                
                String templateContent = new XML2PDF().parseReportTemplate(ctx, foFile);
                xsltSrc = new StreamSource(new StringReader(templateContent));
            
                Transformer transformer = factory.newTransformer(xsltSrc);    


				// tell the Transformer object where to find resources
				transformer.setURIResolver((ServletContextURIResolver)ctx.get(HtmlConstants.DOCUMENTURIRESOLVER));

				// Setup input for XSLT transformation
				Source src = new StreamSource(xmlfile);

				// Resulting SAX events (the generated FO) must be piped through
				// to FOP
				Result result = new SAXResult(fop.getDefaultHandler());

				// Start XSLT transformation and FOP processing
				transformer.transform(src, result);
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	public void convertXMLtoStream(String foFile, StringBuffer xmlContent,
			List listFormID, OutputStream out, String baseUrl,
			ServletContextURIResolver uriResolver,Context ctx) throws Exception {
		try {

			String strStruct = "</response>";
			int iIndex = xmlContent.indexOf(strStruct);

			xmlContent.delete(iIndex, iIndex + strStruct.length());
			xmlContent.insert(iIndex, "<templates>");
			int k = 0;
			if(!"3".equals(ctx.get("CompanyKey").toString()))
        	{
				for (int i = 0; i < listFormID.size(); i++) {
					Map map = (Map) listFormID.get(i);
					String formID = map.get("FormID").toString();
					if (!"".equals(formID))
					{
						String TempID = "";
						if (formID.contains("102"))
							TempID = "APL_102";
						else if (formID.contains("103") && !formID.contains("FM 2.0.1033"))
							TempID = "APL_103";
						else if (formID.contains("104") && !formID.contains("OK"))
							TempID = "APL_104";
						else if (formID.contains("105"))
							TempID = "APL_105";
						else if (formID.contains("106"))
							TempID = "APL_106";
						xmlContent
								.append("<template_id_" + TempID + ">"
										+ TempID.trim() + "</template_id_" + TempID
										+ ">\n");
					}
					
				}
        	}
			if("3".equals(ctx.get("CompanyKey").toString()))
        	{
	            for(int i=0; i<listFormID.size(); i++)
	            {
	            	Map map = (Map) listFormID.get(i);
	            	String formID = map.get("FormID").toString();
	            	if(!"".equals(formID))
	            	{
	            		String TempID = "";
	            		if(formID.contains(" "))
	            			formID=formID.replace(" ","");
	            		if(formID.contains("-"))
	            			formID=formID.replace("-","");
	            		
	            		if(formID.contains("AAA04"))
	            			formID=formID.replace("AAA04","");
	            		if(formID.length()>16)
	            		{
		            		formID=formID.substring(0 ,5);
		                	TempID ="AAA_"+formID;
	            		}
	            		else
	            		{
	            			formID=formID.substring(0 ,4);
		                	TempID ="AAA_"+formID;
	            		}
	            		formID=formID.substring(0,4);
	                	TempID ="AAA_"+formID;
	                	
	                	xmlContent.append("<template_id_" + TempID +">" + TempID.trim() +"</template_id_" + TempID +">\n");
	                	
	            	}        	
       	
	            }
        	}
			xmlContent.append("</templates>\n");
			xmlContent.append("</response>");
			logger.debug(xmlContent != null && !"".equals(xmlContent.toString()) ? xmlContent.toString() : "Null XML Content" );
			
			if(ctx.get(HtmlConstants.DOCUMENTSERVLETCONTEXT) != null)
				baseUrl = "file:///" + ((ServletContext)ctx.get(HtmlConstants.DOCUMENTSERVLETCONTEXT)).getRealPath("/");
			
			convertPOToPDF(foFile, xmlContent, out, baseUrl, uriResolver,ctx);
		} catch (Exception e) {

			e.printStackTrace();
			throw e;
		}

	}

	public void convertPOToPDF(String foFile, StringBuffer xmlData,
			OutputStream out, String baseUrl,
			ServletContextURIResolver uriResolver,Context ctx) throws Exception {

		try {

			File fofile = new File(foFile);
			// File pdffile = new File(outFile);

			// configure fopFactory as desired
			FopFactory fopFactory = FopFactory.newInstance();
			fopFactory.setURIResolver(uriResolver);

			FOUserAgent foUserAgent = fopFactory.newFOUserAgent();
			
			if(ctx.get(HtmlConstants.DOCUMENTSERVLETCONTEXT) != null)
				baseUrl = "file:///" + ((ServletContext)ctx.get(HtmlConstants.DOCUMENTSERVLETCONTEXT)).getRealPath("/");
			
			foUserAgent.setBaseURL(baseUrl);

			// configure foUserAgent as desired
			// Setup output
			// OutputStream out = new java.io.FileOutputStream(pdffile);
			// out = new java.io.BufferedOutputStream(out);

			try {
				// Construct fop with desired output format
				Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF,
						foUserAgent, out);

				// Setup XSLT
				TransformerFactory factory = TransformerFactory.newInstance();
				Transformer transformer = factory
						.newTransformer(new StreamSource(fofile));

				// Set the value of a <param> in the stylesheet
				// transformer.setParameter("versionParam", "2.0");

				// Setup input for XSLT transformation

				Source src = new StreamSource(new ByteArrayInputStream(xmlData
						.toString().getBytes()));

				// Resulting SAX events (the generated FO) must be piped through
				// to FOP
				Result res = new SAXResult(fop.getDefaultHandler());

				// Start XSLT transformation and FOP processing
				transformer.transform(src, res);

			} finally {
				out.close();
			}

			
			logger.debug("Success!");
		} catch (Exception e) {
			e.printStackTrace();
			throw e ;
			//System.exit(-1);
		}
	}
	
	public void convertPOToPDF(String foFile, StringBuffer xmlData,
			String outFile) {
		try {
			File fofile = new File(foFile);
			File pdffile = new File(outFile);

			// configure fopFactory as desired
			FopFactory fopFactory = FopFactory.newInstance();
			FOUserAgent foUserAgent = fopFactory.newFOUserAgent();

			// configure foUserAgent as desired
			// Setup output
			OutputStream out = new java.io.FileOutputStream(pdffile);
			out = new java.io.BufferedOutputStream(out);

			try {
				// Construct fop with desired output format
				Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF,
						foUserAgent, out);

				// Setup XSLT
				TransformerFactory factory = TransformerFactory.newInstance();
				Transformer transformer = factory
						.newTransformer(new StreamSource(fofile));

				// Set the value of a <param> in the stylesheet
				// transformer.setParameter("versionParam", "2.0");

				// Setup input for XSLT transformation
				Source src = new StreamSource(new ByteArrayInputStream(xmlData
						.toString().getBytes()));

				// Resulting SAX events (the generated FO) must be piped through
				// to FOP
				Result res = new SAXResult(fop.getDefaultHandler());

				// Start XSLT transformation and FOP processing
				transformer.transform(src, res);

			} catch (Exception e) {
				e.printStackTrace();

			} finally {
				out.close();
			}

			System.out.println("Success!");

		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	public static void main(String args[]) {
		String strXML = "<response><address_freeform_01><data><AccountName>TestPolicy</AccountName><ContactPerson>Raghraj Singh</ContactPerson><Address1>No 224</Address1><Address2>NT</Address2><City>East Delhi</City><StateDesc>Delhi</StateDesc><Zip>110093</Zip></data></address_freeform_01><catalog><cd><title>Empire Burlesque</title><artist>Bob Dylan</artist><country>USA</country><company>Columbia</company><price>10.90</price><year>1985</year></cd></catalog>  </response>";
		StringBuffer xmlContent = new StringBuffer(strXML);

		// String[] arrTempID={"APL - 102"};
		// new XML2PDF().convertXMLtoStream(xmlContent,arrTempID);;

	}

	public static String parseReportTemplate(Context ctx, String templateFile) throws Exception{
        String templateContent = null;
        
        SAXBuilder builder = new SAXBuilder();
        Document doc = builder.build(new File(templateFile));
        Element root = doc.getRootElement();
        
        parseReportTemplate1(ctx, root);
        
        templateContent = new XMLOutputter(Format.getPrettyFormat()).outputString(root);
        templateContent = templateContent.replaceAll("COLON", ":");
        
        return templateContent;
  }


private static void parseReportTemplate1(Context ctx, Element element) throws Exception{
        if(element.getChildren() != null && element.getChildren().size() > 0){
              for(int i=0; i<element.getChildren().size(); i++){
                    Object obj = element.getChildren().get(i);
                    
                    if(obj != null && obj instanceof Element){
                          Element ele = (Element)obj;
                          String name = ele.getName();
                          String value = ele.getText();
                          
                          //going to override href for include and import tags based on context value
                          if((name.equalsIgnoreCase("include") || name.equalsIgnoreCase("import")) && ele.getAttributeValue(HtmlConstants.HREF) != null){
                                String href = ele.getAttributeValue(HtmlConstants.HREF);
                                href = ctx.get(href) != null ? ctx.get(href).toString() : href;
                                ele.setAttribute(HtmlConstants.HREF, href);
                                continue;
                          }
                          
                          
                          
                          parseReportTemplate1(ctx, ele);
                    }
              }
        }
        
        //return templateElement;
  }

	//Indication in jack
	public void convertPOToPDF(String foFile, StringBuffer xmlData, OutputStream out, String baseUrl, ServletContextURIResolver uriResolver)
	{
		
		try{
			
			File fofile = new File(foFile);
			//File pdffile = new File(outFile);			
		
			// configure fopFactory as desired
			FopFactory fopFactory = FopFactory.newInstance();
			fopFactory.setURIResolver(uriResolver);
			
			FOUserAgent foUserAgent = fopFactory.newFOUserAgent();
			foUserAgent.setBaseURL(baseUrl);
			 
			// configure foUserAgent as desired
			// Setup output					
			//OutputStream out = new java.io.FileOutputStream(pdffile);
			//out = new java.io.BufferedOutputStream(out);		
			
			
			try {
				// Construct fop with desired output format
				Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent, out);
				
				// Setup XSLT
				TransformerFactory factory = TransformerFactory.newInstance();
				Transformer transformer = factory.newTransformer(new StreamSource(fofile));
				
				// Set the value of a <param> in the stylesheet
				//transformer.setParameter("versionParam", "2.0");
				
				// Setup input for XSLT transformation
			
				Source src = new StreamSource(new ByteArrayInputStream(xmlData.toString().getBytes()));
				 
				// Resulting SAX events (the generated FO) must be piped through to FOP
				Result res = new SAXResult(fop.getDefaultHandler());
				
				// Start XSLT transformation and FOP processing
				transformer.transform(src, res);
				
			} catch (Exception e) {
				logger.debug("FOP Transformer Failed.." + e.getMessage());	
				e.printStackTrace();	
			} finally {			 
				out.close();
			}
			
			logger.debug("Success!");
		} 
		catch (Exception e) {
			 e.printStackTrace();				 
			 //System.exit(-1);			  
		}
	}
}
