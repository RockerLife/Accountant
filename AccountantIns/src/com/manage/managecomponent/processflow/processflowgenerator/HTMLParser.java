package com.manage.managecomponent.processflow.processflowgenerator; 


import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

// import com.manage.managecomponent.processflow.Generator.HTMLParser.HTML2XML;
import com.manage.managecomponent.components.ComponentResources;
import com.manage.managecomponent.processflow.processflowgenerator.processflowvalidation.ValidatePage;
import com.util.Constants;
import com.util.Context;
import com.util.HTML2XML;
import com.util.HtmlConstants;
import com.util.IOUtils;
import com.util.InetLogger;
import com.util.ResourceLoader;

public class HTMLParser
{
 
	
	private static  String htmlpath;
	
	private static  String projectName;
	
	private static  HTML2XML html2xml;
	
	private static InetLogger logger = InetLogger.getInetLogger(HTMLParser.class);
	
    private  BlockInfo blockInfo ; 	
	
    private Map pageMap = new HashMap ();
    
    private List componentList = new ArrayList ();
	
    private String PATH = "path";
    
    private String FORM_LINK = "form_link";
    
    private List menuList = new ArrayList();
    
    private  Map contextmenuMap = new HashMap();
    
    private String processflowpath;
    
    private List errorpages = new ArrayList();
    
    private List errorList = new ArrayList();
    
    private  ValidatePage validatePage ;
    
//    private String pagename ;
    public static Map accordianParentMap = new HashMap();
    
    public void setHtmlPath(String htmlpath, String projectName,String processflowpath) 
	{
		this.htmlpath = htmlpath;
		this.projectName = projectName;
		this.processflowpath = processflowpath;
		html2xml = new  HTML2XML();
		html2xml.setHtmlPath(htmlpath);
		 validatePage = new ValidatePage(projectName); 
//		html2xml.convert(htmlpath+"\\"+pagename);  
	}
	
	public String cleanContent(String htmlcontent) 
	{
		htmlcontent= htmlcontent.replaceAll("&nbsp;","");
		htmlcontent= htmlcontent.replaceAll("<\\/style>","</style>");
		
	  return htmlcontent;
    }
	
	
	private Document readingHTMLPage(String startpage) 
	{
//		this.pagename = pagename;
		htmlpath = htmlpath.replace("\\", File.separator);
		htmlpath = htmlpath.replace("//", File.separator);
		startpage = startpage.replace("//", File.separator);
		startpage = startpage.replace("\\", File.separator);
		
		 File file = new File( htmlpath+ "temp.xml");
		 if(file.exists())
		    file.delete();
		 html2xml.convert(htmlpath+File.separator+startpage);
		 String htmlcontent = null;
		 Document document=null;
		 
		  
		  try {
				htmlcontent = IOUtils.readFile( htmlpath+ "temp.xml"  );
				htmlcontent = htmlcontent.substring(htmlcontent.indexOf("<html"), htmlcontent.length());
				htmlcontent = cleanContent(htmlcontent);
			} catch (Exception e) {
                      errorpages.add(startpage);
			         logger.error( ">>>"+htmlpath+"\\"+  startpage +" does not located at desire path", e);
				}
			SAXBuilder builder = new SAXBuilder();
		    		if(htmlcontent!=null)
		    		{
		    			 try {
		    				  if(htmlcontent.contains( "&iuml;&raquo;&iquest;"))
		    				  {
		    					  htmlcontent = htmlcontent.replaceAll("&iuml;&raquo;&iquest;", "");
		    				  }
		    				 	document = builder.build(new StringReader(htmlcontent));
		    			 	 } catch (JDOMException e) {
		    			 		errorpages.add(startpage);

                                logger.error( ">>>"+htmlpath+"\\"+  startpage +"Error-->"+e, e);
		  	    	 		    logger.error( ">>>"+htmlpath+"\\"+  startpage +"unable to parse the Html file");
		                     } catch (IOException e) {
//		                    	e.printStackTrace();
		                    	 errorpages.add(startpage);
                                 logger.error(htmlpath+" page does not exit." +e, e);
		                     }
		    		}
		    		else{
		    				errorpages.add(startpage);
		    				logger.error(">>Html is not loaded");
		    			}
		return document;
	}
	
	public void parsingHTMLPage(String startpage){
		Context ctx = new Context();
		ctx.setProject(projectName);
		
		String startpageComp = startpage;
		try{
			if(startpageComp.contains("/"))
				startpageComp = startpageComp.substring(startpageComp.lastIndexOf("/")+1, startpageComp.length());
			else if(startpage.contains("//"))
				startpageComp = startpageComp.substring(startpageComp.lastIndexOf("//")+1, startpageComp.length());
			else if(startpage.contains("\\"))
				startpageComp = startpageComp.substring(startpageComp.lastIndexOf("\\")+1, startpageComp.length());
			
			if(startpageComp.contains(".html"))
				startpageComp = startpageComp.substring(0, startpageComp.indexOf(".html"));
			if(ComponentResources.getInstance(ctx).getComponent(startpageComp) == null){
				errorpages.add(startpageComp);
				
				{ if (logger.isDebugEnabled()) logger.debug("Start validation for "+startpage); }
				logger.error("Component "+startpageComp+" is not loaded due to error : " + "Component " + startpage + " does not exist.");
				{ if (logger.isDebugEnabled()) logger.debug("Ended validation for " + startpage); }
				return;
			}
		}catch (Exception e) {
			errorpages.add(startpageComp);
			
			{ if (logger.isDebugEnabled()) logger.debug("Start validation for "+startpage); }
			logger.error("Component "+startpageComp+" is not loaded due to error : " + e.getMessage(), e);
			{ if (logger.isDebugEnabled()) logger.debug("Ended validation for " + startpage); }
			return;
		}
		
		if(!componentList.contains(startpage)){
			if(!errorpages.contains(startpage)){  
				processHTMLPage(startpage);
			}
		}
		//if()
	}
	
	public void processHTMLPage(String startpage){
		try{
			Document document = readingHTMLPage(startpage);
			Element root = document.getRootElement();
			validatePage.validate(startpage, errorList, root);
			
//			if(!errorList.isEmpty())
			if(root != null){	  
				Element frm_body = root.getChild(HtmlConstants.BODY);
				if(frm_body == null){
					errorpages.add(startpage);
					logger.error(startpage+"page does not contain the form element");
				}
				
				if(frm_body != null){	  
					{ if (logger.isDebugEnabled()) logger.debug("Start processing for " + startpage); }
					List children = frm_body.getChildren();
					Element rootTable = null;
					blockInfo = new BlockInfo();			  
					if(children != null){
						populateChildren(children, startpage);
					}
					
					pageMap.put(startpage,blockInfo.getBlock());
					Map formlinkmap = blockInfo.getFormLinks();
					if(formlinkmap.isEmpty())
						pageMap.putAll(formlinkmap);
					
					{ if (logger.isDebugEnabled()) logger.debug("Ended processing for " + startpage); }
					populateForwardPages(startpage);
				}
		    }
		}catch (Exception e) {
			  errorpages.add(startpage);
			  logger.error(startpage+"error occured processing the page ", e);
		}
	}
	
	public void populateProcessFlow() throws Exception{
		ProcessFlowMaker flowMaker= new ProcessFlowMaker();
		flowMaker.populatePages(pageMap, projectName , componentList, processflowpath);
	}
	
	void populateForwardPages(String page) throws Exception{
		if(!componentList.contains(page)){	 
			componentList.add(page);
		    List blockList = (List)pageMap.get(page);
			if(blockList != null){	 
				for(int i =0; i< blockList.size(); i++){
					Map blkMap2 = (Map) blockList.get(i);
					Iterator  blkMap2iter =  blkMap2.entrySet().iterator();
					while(blkMap2iter.hasNext()){
						Entry map = (Entry)blkMap2iter.next();
						Object key = map.getKey();
				     
						if(key.toString().contains(Constants.BLOCKLINKS) || key.toString().contains(Constants.FORMLINKS) ||
								key.toString().contains("rowlink")){
					   	 List linksList = (List)blkMap2.get(map.getKey());
					   	 if(linksList != null){
						    for(int k=0; k<linksList.size(); k++){
								Map blockMap = (Map)linksList.get(k);
								if(!blockMap.isEmpty()){	
									Object path = blockMap.get(PATH);
									if(path!=null && !("".equals(path)))
										parsingHTMLPage(path.toString());
					   				}
					   		 	}
					   	 	}
						}
					}
			    }
			}
		}
	}

	private void populatepage(List page){
		for(int i=0; i<page.size(); i++){
			if(page.get(i) instanceof List){
				List new_name = (List)page.get(i);
			}else if(page.get(i) instanceof Map){
				Map new_name = (Map)page.get(i);
			}
		}
	}

	private void populateChildren(List childernlist, String startpage) throws Exception
	{
		      if (childernlist != null) 
		      {
		          for (int i = 0; i < childernlist.size(); i++) 
		          {
		        	    
		                Element blockelement = (Element) childernlist.get(i);
		                Attribute elementsAttr =   blockelement.getAttribute(HtmlConstants.ID);
		                	if(elementsAttr!=null)
		                	{
		                			String blockid = elementsAttr.getValue();
		                			if(blockid.contains(HtmlConstants.FREEFORM))
		                			{
		                				blockInfo.populateBlocksInfo(  blockelement, startpage ) ;
		                				continue;
		                			} 
		                			else if(blockid.contains(HtmlConstants.LIST))
		                			 {
		                			    String  blocklistid =  blockelement.getAttributeValue(HtmlConstants.ID);
		                				blockInfo.populateListBlock( blockelement,  blocklistid,menuList, startpage); 
		                				continue;
		                			 }
		                			else if(blockid.contains(HtmlConstants.CUSTOM_MENU_CONTEXT)){
		                			   populatecontextmenu(blockelement,startpage);
		                			   continue;
		                			}
		                	}
		                	 
        						blockInfo.populateFormLinks(blockelement, startpage);
        					 
		                populateChildren(blockelement.getChildren(), startpage);
		          }
		      }
	}

	private void populatecontextmenu(Element blockelement, String startpage) 
	{
		List contextmenuList =  blockelement.getChildren();
		for(int i=0;i<contextmenuList.size();i++){
			Element contextelement = (Element)contextmenuList.get(i);
			String  href =   contextelement.getAttributeValue(HtmlConstants.HREF);
			if(href!=null){
				Map contextmap = new HashMap();
				blockInfo.populateLinks(  contextelement, contextmap, startpage); 
				menuList.add(contextmap );	
			}
			populatecontextmenu(contextelement, startpage);
		}
	}

	public List getContextMenuItem()
	{
		return menuList;
	}
	
	public static void main(String[] args) 
	{
		HTMLParser parser = new HTMLParser();
        try {	 
        	ResourceLoader.load( "C://OutlineSys//AccountantInsurance//src//XML//AccountantInsurance//components.xml", "components", "");
			ResourceLoader.load( "C://OutlineSys//AccountantInsurance//src//XML//AccountantInsurance//rules.xml", "rules", "");
        	//C:\DSProHtml
        	parser.setHtmlPath("C://","AccountantInsurance","C://processflow.xml" ); 
        	//"C:\\OutlineSys\\Manage\\src\\com\\DSPro\\compiledHtmls\\add\\","DSPro");
			parser.parsingHTMLPage("OutlineSys//AccountantInsurance//web//firmInfo.html");
			parser.populateProcessFlow();
			
        }catch (Exception e) {
            logger.error("Error in HTMLParser.main", e);
		}

	}

}
