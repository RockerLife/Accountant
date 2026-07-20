package com.manage.process;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.BuildProcess;
import com.manage.managemetadata.util.LookupResourceLoader;
import com.processor.TabsConfiguration;
import com.service.ConfigurationService;
import com.service.ConfigurationServiceProxy;
import com.service.XMLObject;
import com.service.XmlStatus;
import com.userbo.AccountantConstants;
import com.util.Constants;
import com.util.Context;
import com.util.InetLogger;
import com.util.SystemProperties;


public class AccountantInsServlet extends ComponentProcessServlet{
	private String SSO_FORWARD_URL = "sso.logout.url";
	private static InetLogger logger = InetLogger.getInetLogger(AccountantInsServlet.class);
	public static String projectNames = null;
	
	public void init() throws ServletException {
		super.init();
		try {
			String projectName ="AccouantIns";
			
			if((SystemProperties.getInstance().getProperty("appl.configuration.applicable") != null && 
					SystemProperties.getInstance().getString("appl.configuration.applicable").equals("Y")) &&
					(SystemProperties.getInstance().getProperty("appl.configuration.loadconfigurationxmls") != null && 
					SystemProperties.getInstance().getString("appl.configuration.loadconfigurationxmls").equals("Y"))){
				
				String url = SystemProperties.getInstance().getString("appl.configuration.service.url");
				
				logger.debug("Going to load xmls from url "+url+" at time : " + new Date());
				
				ConfigurationService service = new ConfigurationServiceProxy(url);
				logger.debug(service);
				
				String releaseno = SystemProperties.getInstance().getString("appl.configuration.releaseno");
				String releaseversionno = SystemProperties.getInstance().getString("appl.configuration.releaseversionno");
				
				String user_id = null;
				if(SystemProperties.getInstance().getProperty("appl.configuration.configurationuserid") != null)
					user_id = SystemProperties.getInstance().getString("appl.configuration.configurationuserid");
				
				String project_resource = getServletConfig().getInitParameter(Constants.INET_PROJECT_ID);
				String[] projects = null;

				projectNames = project_resource;
				
				if (project_resource.contains(","))
					projects = project_resource.split(",");

				if (projects != null) {
					for(int i = 0; i < projects.length; i++) {
						projectName = projects[i];
						
						String bypassXmls = null;
						if(SystemProperties.getInstance().getProperty("appl."+projectName+".configuration.bypassxmls") != null)
							bypassXmls = SystemProperties.getInstance().getString("appl."+projectName+".configuration.bypassxmls");
						
						XmlStatus xmlStatus = service.getXmlStatusById(TabsConfiguration.Client_ID+"", releaseno, releaseversionno, projectName, bypassXmls, user_id);
						
						XMLObject[] obj = service.generateXmls(TabsConfiguration.Client_ID+"", releaseno, releaseversionno, projectName, bypassXmls, user_id);
						
						boolean islabelconfgenerated = false;
						boolean ismessagesgenerated = false;
						boolean istabsconfigurationresourcegenerated = false;
						boolean isfunctionsgenerated = false;
						boolean issecuritygenerated = false;
						boolean isreportsgenerated = false;
						boolean isrulesgenerated = false;
						boolean ismetadatagenerated = false;
						boolean iscomponentsgenerated = false;
						boolean isibatisgenerated = false;
						boolean isprocessflowgenerated = false;
						
						if(xmlStatus != null){
							if(xmlStatus.getIsprocessflowgenerated() != null && xmlStatus.getIsprocessflowgenerated().equals("Y"))
								isprocessflowgenerated = true;
							
							if(xmlStatus.getIsibatisgenerated() != null && xmlStatus.getIsibatisgenerated().equals("Y"))
								isibatisgenerated = true;
						}
						
						if(obj != null && obj.length > 0){
							for(int j=0; j<obj.length; j++){
								XMLObject xmlObj = obj[j];
								
								try{
									if(xmlObj.getXmlName().equals("labelconf")){
										logger.debug("going to generate labelconf xml");
										
										String resourcePath = SystemProperties.getInstance().getString("appl."+projectName+".labelconf.path");
										FileOutputStream fout = new FileOutputStream(resourcePath);
										fout.write(xmlObj.getXmlContent().getBytes());
										fout.close();
										
										logger.debug("labelconf xml generated");
									}else if(xmlObj.getXmlName().equals("messages")){
										logger.debug("going to generate messages xml");
										
										String resourcePath = SystemProperties.getInstance().getString("xml.basedir") + projectName + File.separator + "messages.xml";
										FileOutputStream fout = new FileOutputStream(resourcePath);
										fout.write(xmlObj.getXmlContent().getBytes());
										fout.close();
										
										logger.debug("messages xml generated");
									}else if(xmlObj.getXmlName().equals("tabsconfigurationresource")){
										logger.debug("going to generate tabsconfigurationresource xml");
										
										String resourcePath = SystemProperties.getInstance().getString("xml.basedir") + projectName + File.separator + "tabsconfiguration.xml";
										FileOutputStream fout = new FileOutputStream(resourcePath);
										fout.write(xmlObj.getXmlContent().getBytes());
										fout.close();
										
										logger.debug("tabsconfigurationresource xml generated");
									}else if(xmlObj.getXmlName().equals("functions")){
										logger.debug("going to generate functions xml");
										
										String resourcePath = SystemProperties.getInstance().getString("xml.basedir") + projectName + File.separator + "functions.xml";
										FileOutputStream fout = new FileOutputStream(resourcePath);
										fout.write(xmlObj.getXmlContent().getBytes());
										fout.close();
										
										logger.debug("functions xml generated");
									}else if(xmlObj.getXmlName().equals("security")){
										logger.debug("going to generate security xml");
										
										String resourcePath = SystemProperties.getInstance().getString("xml.basedir") + projectName + File.separator + "security.xml";
										FileOutputStream fout = new FileOutputStream(resourcePath);
										fout.write(xmlObj.getXmlContent().getBytes());
										fout.close();
										
										logger.debug("security xml generated");
									}else if(xmlObj.getXmlName().equals("reports")){
										logger.debug("going to generate reports xml");
										
										String resourcePath = SystemProperties.getInstance().getString("xml.basedir") + projectName + File.separator + "reports.xml";
										FileOutputStream fout = new FileOutputStream(resourcePath);
										fout.write(xmlObj.getXmlContent().getBytes());
										fout.close();
										
										logger.debug("reports xml generated");
									}else if(xmlObj.getXmlName().equals("rules")){
										logger.debug("going to generate rules xml");
										
										String resourcePath = SystemProperties.getInstance().getString("xml.basedir") + projectName + File.separator + "rules.xml";
										File file = new File(resourcePath);
										if(file.exists() && file.isFile()){
											file.delete();
										}
				
										resourcePath = SystemProperties.getInstance().getString("xml.basedir") + projectName + File.separator + "rules";
										file = new File(resourcePath);
										if(file.exists() && file.isDirectory()){
											delete(file, null);
										}
										
										file.mkdir();
										
										if(xmlObj.getXmlObject() != null){
											Map map = (Map)xmlObj.getXmlObject();
											Iterator itr = map.keySet().iterator();
											while(itr.hasNext()){
												String key = itr.next().toString();
												
												String content = map.get(key).toString();
												content = content.replace("&gt;", ">").replace("&lt;", "<");
												
												FileOutputStream fout = new FileOutputStream(resourcePath+File.separator+key);
												fout.write(content.getBytes());
												fout.close();
											}
										}
										
										logger.debug("rules xml generated");
									}else if(xmlObj.getXmlName().equals("metadatafields")){
										logger.debug("going to generate metadatafields xml");
										
										//String resourcePath = SystemProperties.getInstance().getString("xml.basedir") + projectName + File.separator + "metadata" + File.separator + "metadata" + ".xml";
										String resourcePath = SystemProperties.getInstance().getString("xml.basedir") + projectName + File.separator + "metadata" + ".xml";
										File file = new File(resourcePath);
										if(file.exists() && file.isFile()){
											file.delete();
										}
				
										resourcePath = SystemProperties.getInstance().getString("xml.basedir") + projectName + File.separator + "metadata";
										file = new File(resourcePath);
										if(file.exists() && file.isDirectory()){
											delete(file, null);
										}
										
										file.mkdir();
										
										resourcePath = SystemProperties.getInstance().getString("xml.basedir") + projectName + File.separator + "metadata" + File.separator + "metadatafields" + ".xml";
										FileOutputStream fout = new FileOutputStream(resourcePath);
										fout.write(xmlObj.getXmlContent().getBytes());
										fout.close();
										
										logger.debug("metadatafields xml generated");
									}else if(xmlObj.getXmlName().equals("metadata")){
										logger.debug("going to generate metadata xml");
										
										String resourcePath = SystemProperties.getInstance().getString("xml.basedir") + projectName + File.separator + "metadata" + File.separator + "metadata" + ".xml";
										FileOutputStream fout = new FileOutputStream(resourcePath);
										fout.write(xmlObj.getXmlContent().getBytes());
										fout.close();
										
										logger.debug("metadata xml generated");
									}else if(xmlObj.getXmlName().equals("components")){
										logger.debug("going to generate components xml");
										
										String resourcePath = SystemProperties.getInstance().getString("xml.basedir") + projectName + File.separator + "components" + File.separator + "components" + ".xml";
										File file = new File(resourcePath);
										if(file.exists() && file.isFile()){
											file.delete();
										}
										
										//deleting old components folder
										resourcePath = SystemProperties.getInstance().getString("xml.basedir") + projectName + File.separator + "components";
										file = new File(resourcePath);
										if(file.exists() && file.isDirectory()){
											delete(file, null);
										}
										
										file.mkdir();
										
										if(xmlObj.getXmlObject() != null){
											Map map = (Map)xmlObj.getXmlObject();
											Iterator itr = map.keySet().iterator();
											while(itr.hasNext()){
												String key = itr.next().toString();
												
												String content = map.get(key).toString();
												//content = content.replace("&gt;", ">").replace("&lt;", "<");
												
												FileOutputStream fout = new FileOutputStream(resourcePath+File.separator+key);
												fout.write(content.getBytes());
												fout.close();
											}
										}
										
										logger.debug("components xml generated");
									}else if(xmlObj.getXmlName().equals("componentshelp")){
										logger.debug("going to generate componentshelp xml");
										
										String resourcePath = SystemProperties.getInstance().getString("xml.basedir") + projectName + File.separator + "componentshelp";
										File file = new File(resourcePath);
										if(file.exists() && file.isDirectory()){
											delete(file, null);
										}
										
										file.mkdir();
										
										if(xmlObj.getXmlObject() != null){
											Map map = (Map)xmlObj.getXmlObject();
											Iterator itr = map.keySet().iterator();
											while(itr.hasNext()){
												String key = itr.next().toString();
												
												String content = map.get(key).toString();
												//content = content.replace("&gt;", ">").replace("&lt;", "<");
												
												FileOutputStream fout = new FileOutputStream(resourcePath+File.separator+key);
												fout.write(content.getBytes());
												fout.close();
											}
										}
										
										logger.debug("componentshelp xml generated");
									}
								}catch(Exception e1){
									logger.error("Error while generating " + xmlObj.getXmlName() + " xml due to error : " + e1.getMessage());
								}
							}
						}
						
						loadResource(projectName, "metadata");
						loadResource(projectName, "components");
						loadResource(projectName, "rules");
						loadResource(projectName, "reports");
						
						BuildProcess buildProcess = new BuildProcess(projectName); 
						if(!isprocessflowgenerated){
							logger.debug("going to generate processflow");
							buildProcess.generateProcessFlow();
							logger.debug("processflow generated");
						}
						
						if(!isibatisgenerated){
							String resourcePath = SystemProperties.getInstance().getString("xml.basedir") + projectName + File.separator + "ibatis";
							File file = new File(resourcePath);
							if(file.exists() && file.isDirectory()){
								delete(file, null);
							}
							
							file.mkdir();
							
							int techType = 1;  // 1 for Java and 2 for .Net
							logger.debug("going to generate ibatis");
						
							buildProcess.generateIbatis(techType, true);
							logger.debug("ibatis generated");
						}
						
						loadResource(projectName, "labelconf");
						new LabelConfiguration().getLabelConfiguration(projectName);
						loadResource(projectName, "messages");
						loadResource(projectName, "tabsconfiguration");
						loadResource(projectName, "functions");
						loadResource(projectName, "security");
						
						LookupResourceLoader.loadLookupResources(projectName);
				        LoadCommonResources.loadCommonResources(projectName);
						
						loadResource(projectName, "processflow");
						
						loadResource(projectName, "ibatisconfig");
						loadResource(projectName, "applicationworkflow");
						loadResource(projectName, "graphs");
					}
				}
				
				logger.debug("Xmls loaded successfully at time : " + new Date());
			}
		} catch (Exception e) {
			logger.error("Unable to generate config xmls due to error : " + e.getMessage());
		}
		
		new TabsConfiguration().getTabsConf();	
	}
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doGet(request, response);
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPost(request, response);
	}

	@Override
	protected void process(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	    long millis=0;
	    java.util.Date date=null;
	    millis=System.currentTimeMillis();  
	    date=new java.util.Date(millis);  
	    logger.debug("Request process input time ----------->>>>>>> "+date);
	      
		super.process(request, response);
		
		millis=System.currentTimeMillis();  
	    date=new java.util.Date(millis);  
	    logger.debug("Request process output time ---------->>>>>>> "+date);
	}

	
	public boolean validateApplication(HttpServletRequest req,
			HttpServletResponse resp,Context ctx) throws Exception {
		//going to filter fields based on showRightClickPageId flag
		
		
		ctx.put("level1formChangesDate", AccountantConstants.LEVEL1FORMCHANGES);
		
				if(ctx.get("showRightClickPageId") != null && ctx.get("showRightClickPageFields") != null){
					/*String showRightClickPageId = ctx.get("showRightClickPageId").toString();
					String showRightClickPageFields = ctx.get("showRightClickPageFields").toString();
					
					StringTokenizer tokens = new StringTokenizer(showRightClickPageFields, ",");
					while(tokens.hasMoreTokens()){
						String token = tokens.nextToken();
						
						ctx.put(token, ctx.get(token+"_"+ctx.get(showRightClickPageId)));
						ctx.remove(token+"_"+ctx.get(showRightClickPageId));
					}*/
					
					ctx.put("Comment", ctx.get("SuggestedAnswer"));
				}
		if(ctx.get("inet_page") != null && (ctx.get("inet_page").equals("login")
				||  ctx.get("inet_page").equals("indexinsured") ||  ctx.get("inet_page").equals("insured")
				||  ctx.get("inet_page").equals("registration") ||  ctx.get("inet_page").equals("indexsignandpay")
				|| ctx.get("inet_page").equals("indexrenew") || ctx.get("inet_page").equals("MANAGE_TESTHARNESS")
				|| ctx.get("inet_page").equals("completeapplink") || ctx.get("inet_page").equals("Estimate")|| ctx.get("inet_page").equals("EstimateForm")))
			return true;
		
		
		
		if(ctx.get("role_id") == null || ctx.get("role_id").equals("") || ctx.get("role_id").equals("null")){
			String url = "accountrep.jsp";
			//if(ctx.get("inet_page") != null && ctx.get("inet_page").equals("login"))
				url = url + "?pageError=Please select one Role";
				req.getRequestDispatcher(url).forward(req, resp);
				return false;	
		}
		
		return true;
	}
	@Override
	public String initLocalResources() throws Exception {
		//String encProps = "appl.ProducerOne.db.password";
		//encProps = encProps + "";
		return "";
	}

	@Override
	public boolean validateApplicationForSession(HttpServletRequest request,
			HttpServletResponse response, String action) throws Exception {
		if(action == null || (!"ManageIndex".equals(action) && !"quickquoteoptionlist".equals(action) && !"rminsured".equals(action) && !"qqlogin".equals(action)
				&& !"BasicInfo".equals(action) && !"login".equals(action)  && !"indexinsured".equals(action) && !"accountrep".equals(action))
				&& !"signandpay".equals(action) && !"indexsignandpay".equals(action) && !"payment".equals(action) && !"qqinsured".equals(action)
				&& !"indexrenew".equals(action) && !"insured".equals(action) && !"feeSuitSupplement".equals(action)  
				&& !"certificateInsurance".equals(action) && !"indicationInsured".equals(action) && !"MANAGE_TESTHARNESS".equals(action)
				&& !"completeapplink".equals(action) && !"RequotePolicy".equals(action) && !"paymentmethod".equals(action) 
				&& !"IPFSThankYouPage".equals(action) && !"IPFSCancelPage".equals(action)&& !"Estimate".equals(action) && !"EstimateForm".equals(action)  ){
			return false;
		}
		
		return true;
	}
	
	@Override
	public void logOut(Context ctx, HttpServletRequest request, HttpServletResponse response) {
		  try{
				response.sendRedirect(getErrorForwardUrl(ctx, true));
		  }catch (Exception e) {
				// TODO: handle exception
		  }
	}

	private String getErrorForwardUrl(Context ctx, boolean isLogOut){
		try{
			return SystemProperties.getInstance().getString("appl.error.forward.url");
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return null; 
	}
	
	public static void delete(File file, String skipFile) throws IOException{
		try{
	    	if(file.isDirectory()){
	    		//directory is empty, then delete it
	    		if(file.list().length==0){
	    		   file.delete();
	    		}else{
	    		    //list all the directory contents
	        	    String files[] = file.list();
	     
	        	    for (String temp : files) {
	        	    	//construct the file structure
	        	    	File fileDelete = new File(file, temp);
	        		 
	        	    	if(skipFile != null && fileDelete.getName().endsWith(skipFile))
	        	    		continue;
	        	    	
	        	        //recursive delete
	        	        delete(fileDelete, skipFile);
	        	    }
	        		
	        	    //check the directory again, if empty then delete it
	        	    if(file.list().length==0){
	        	    	file.delete();
	        	    }
	    		}
	    	}else{
	    		//if file, then delete it
	    		file.delete();
	    	}
		}catch(Exception e){
			logger.debug("Error while deleting file : " + file);
		}
    }
}