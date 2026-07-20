package com.fop;

import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.fop.servlet.ServletContextURIResolver;
import org.jdom.Element;

import com.manage.managemetadata.functions.commonfunctions.Math;
import com.manage.managemetadata.functions.commonfunctions.RuleUtils;
import com.ormapping.ibatis.SqlResources;
import com.processor.RequestProcessor;
import com.util.Context;
import com.util.HtmlConstants;
import com.util.IOUtils;
import com.util.InetLogger;
import com.util.SystemProperties;
import com.util.XMLUtils;

public class DownloadRating {
	
private static InetLogger logger = InetLogger.getInetLogger(DownloadRating.class);
	
	public void makeRatingPdf(Context ctx, OutputStream out, String baseUrl, ServletContextURIResolver uriResolver) {
		
		String xmlFile = "";
		String foFile = "";		
		
		try{			
			
			xmlFile = SystemProperties.getInstance().getString("html.basedir") + "data//rating.xml";
			foFile = SystemProperties.getInstance().getString("html.basedir") + "foxsl//rating.xsl";
			
			fetchDataForXml(ctx);
			populateDataXml(ctx, xmlFile);
			baseUrl = "file:///" + ((ServletContext)ctx.get(HtmlConstants.DOCUMENTSERVLETCONTEXT)).getRealPath("/");
			new XML2PDF().convertPOToPDF(foFile, xmlFile, out, baseUrl, (ServletContextURIResolver)ctx.get(HtmlConstants.DOCUMENTURIRESOLVER),ctx);			
		}
		catch(Exception e){
			logger.error("Problem in generating rating for Quote Number : " +ctx.get("QuoteNumber"));
		}
	}

	public void makeInvoicePdf(Context ctx, OutputStream out, String baseUrl, ServletContextURIResolver uriResolver) {
		
		String xmlFile = "";
		String foFile = "";		
		
		try{			
			
			xmlFile = SystemProperties.getInstance().getString("html.basedir") + "data//quoteletter.xml";
			foFile = SystemProperties.getInstance().getString("html.basedir") + "foxsl//Invoice.xsl";
			
			fetchDataForInvoiceXml(ctx);
			populateDataXml(ctx, xmlFile);
			baseUrl = "file:///" + ((ServletContext)ctx.get(HtmlConstants.DOCUMENTSERVLETCONTEXT)).getRealPath("/");
			new XML2PDF().convertPOToPDF(foFile, xmlFile, out, baseUrl, (ServletContextURIResolver)ctx.get(HtmlConstants.DOCUMENTURIRESOLVER),ctx);			
		}
		catch(Exception e){
			logger.error("Problem in generating rating for Quote Number : " +ctx.get("QuoteNumber"));
		}
	}
	public void fetchDataForInvoiceXml(Context ctx) {		
		
		try{
			
			Object objInvoiceData = SqlResources.getSqlMapProcessor(ctx).select("Account.getInvoicedData", ctx);
			if(objInvoiceData !=null)
			{				
				ctx.put("InvoiceDataLW", objInvoiceData);
			}			
		}
		catch(Exception e){
			e.printStackTrace();
			logger.error("Problem in fetching Invoiced  data for Quote Number : " +ctx.get("QuoteNumber"));
		}
		
	}
	
	public void fetchDataForXml(Context ctx) {		
		
		try{
			
			Object objAddress = SqlResources.getSqlMapProcessor(ctx).findByKey("SqlStmts.UserStatementpdfquotelettergetAddressdetails", ctx);
			ctx.put("address_freeform_01", objAddress);
			
			String TransactionSequenceFlag= ctx.get("TransactionSequenceFlag").toString();
			if(TransactionSequenceFlag.equals("Y"))
			{
				Object objTransactionSequence = SqlResources.getSqlMapProcessor(ctx).findByKey("SqlStmts.UserStatementsaveAccountstmtsgetTransactionsequence", ctx);
				if(objTransactionSequence != null)
				{
					ctx.put("TransactionSequence", ((Map)objTransactionSequence).get("TransactionSequence"));	
				}
			}
	
			Object objRatingData = SqlResources.getSqlMapProcessor(ctx).findByKey("SqlStmts.UserStatementsaveAccountstmtsgetRatingData", ctx);
			if(objRatingData !=null)
			{
				
				ctx.put("RatingData", objRatingData);
			}
			

			Object objTaxCalculation = SqlResources.getSqlMapProcessor(ctx).findByKey("SqlStmts.UserStatementsaveAccountstmtsgetTaxCalculation", ctx);
			if(objTaxCalculation !=null)
			{				
				ctx.putAll((Map)objTaxCalculation);
			}
			
			Object objRatingTrace = SqlResources.getSqlMapProcessor(ctx).findByKey("RatingTrace.findByKey", ctx);
			if(objRatingTrace != null)
			{
				Map mapRatingTrace = (Map)objRatingTrace;
				if(mapRatingTrace.get("XmlOutputDatafromRating") != null)
				{
//					ClobImpl clobImpl = (ClobImpl) mapRatingTrace.get("XmlOutputDatafromRating");
//					String outputString = clobImpl.getSubString(Long.parseLong("1"), Integer.parseInt(new Long(clobImpl.length()).toString()));
					String outputString = mapRatingTrace.get("XmlOutputDatafromRating").toString();
					Element root = XMLUtils.parseXMLRootElement(outputString);					
					Element outputElement = root.getChild("PremiumInfo");
					Map out = populateOutput( outputElement);
					System.out.println("MTTaxAmmount-------"+out.get("MTTaxAmmount"));
					
					Map outData = calculateTax(out, ctx);
					
					// For Skip Tax Calulation
					
					/////////////
					//ctx.put("PremiumInfo", out);
					ctx.put("PremiumInfo", outData);
					
				}
			}
				
		}
		catch(Exception e){
			e.printStackTrace();
			logger.error("Problem in fetching data for Quote Number : " +ctx.get("QuoteNumber"));
		}
		
	}
	
	public Map calculateTax(Map out, Context ctx) throws Exception {
		//out.get("MTTaxAmmount"))
		
		Object obj = RuleUtils.executeRule(ctx, "AccountantRule.IsSkipTaxCalculation");			
			boolean flag = true;
			if(obj != null && obj instanceof Boolean )
				flag =(Boolean) obj;
			
			double mTCountyTax = 0.0;		
			double finalBaseLimitPrem = 0.0;
			
			
			
			// Skip No
			if (!flag) {
                if (out.get("MTTaxAmmount") != null
                          && !"".equals(out.get("MTTaxAmmount")
                                   .toString())) {
                     mTCountyTax = Double.parseDouble(out.get(
                               "MTTaxAmmount").toString());
                     
                    // if(mTCountyTax ==0 )
                    // out.put("IsSkipTaxCalculation", "N");
                   // out.put("MTTaxAmmount",mTCountyTax ) ;
                }
			}else{
				 out.put("MTTaxAmmount",0.0);
			}
//           else
//           {
//        	   out.put("MTTaxAmmount",0.0);
//        	   out.put("IsSkipTaxCalculation", "Y");
//           }
			
           if (out.get("CountyTaxAmmount") != null
                     && !"".equals(out.get("CountyTaxAmmount")
                               .toString())) {
                if (mTCountyTax <= 0) {
                     mTCountyTax = mTCountyTax
                               + Double.parseDouble(out.get(
                                        "CountyTaxAmmount").toString());
                     System.out.println("countrytaxamount------"+Double.parseDouble(out.get("CountyTaxAmmount").toString()));
                     
                     out.put("CountyTaxAmmount", Double.parseDouble(out.get("CountyTaxAmmount").toString()));
                }
                else
                	out.put("CountyTaxAmmount",0.0);
           }

			
			if(out.get("State1TaxAmmount") !=null && !"".equals(out.get("State1TaxAmmount").toString()))
			{
				mTCountyTax = mTCountyTax + Double.parseDouble(out.get("State1TaxAmmount").toString());
				out.put("State1TaxAmmount", Math.round(out.get("State1TaxAmmount")));
			}
			if(out.get("State2TaxAmmount") !=null && !"".equals(out.get("State2TaxAmmount").toString()))
			{
				mTCountyTax = mTCountyTax + Double.parseDouble(out.get("State2TaxAmmount").toString());
				out.put("State2TaxAmmount", Double.parseDouble(out.get("State2TaxAmmount").toString()));
			}
		
			
			
			
			out.put("Total_tax", Math.round(mTCountyTax));
			out.put("TotalCoverageTaxAmount", Math.round(mTCountyTax));
			
			
			if(out.get("FinalBaseLimitPremium") !=null && !"".equals(out.get("FinalBaseLimitPremium").toString()))
			{
				finalBaseLimitPrem = mTCountyTax +  Double.parseDouble(out.get("FinalBaseLimitPremium").toString());	
			}
			
			
			out.put("FinalBaseLimitPremiumWithTax", Math.round(finalBaseLimitPrem));			
			
			
		
		
		return out;
	}

	public void populateDataXml(Context ctx, String xmlFile) {
		
		StringBuffer buffer = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		
		try{			
			// Data writer
			IOUtils.writeToFile(xmlFile, buffer.append(new RequestProcessor().generateResponseXml(ctx,false)));
		}
		catch(Exception e){
			logger.error("Problem in generating data xml for Quote Number : " +ctx.get("QuoteNumber"));
		}
	}
	
	public Map populateOutput(Element element)
	{
    	StringBuffer buffer = new StringBuffer();    	
		List children = element.getChildren();
		if(children == null)
			return new HashMap();
		Map out = new HashMap();
		for(int i=0; i<children.size(); i++)
	    {
			Element child = (Element) children.get(i);
			String name = child.getName();
			String str = child.getTextTrim();			
			out.put(name, str);      
	    }  	
		
		return out;
	}

}
