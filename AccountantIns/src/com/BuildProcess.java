package com;

import java.io.IOException;
import java.util.List;

import com.manage.managecomponent.processflow.processflowgenerator.HTMLParser;
import com.manage.managedatabase.dblayergeneration.ibatisgeneration.SQLMapGenerator;
import com.manage.managedatabase.gendbfrommeta.RunDBSync;
import com.manage.managedatabase.genmetafromdb.RunGenMetaFromDB;
import com.util.InetLogger;
import com.util.ResourceLoader;
import com.util.SystemProperties;

public class BuildProcess {

	private static InetLogger logger = InetLogger.getInetLogger(BuildProcess.class);
		
	private String projectName="";
	
	public BuildProcess(String projectName){
		
		this.projectName = projectName;
	}
	
	public BuildProcess(String projectName,String propertiesPath){
		
		this.projectName = projectName;
		
		try {
			SystemProperties.getInstance(propertiesPath);
		} catch (IOException ioe) {
			logger.error("Properties file is not found at location " + propertiesPath, ioe);
		}
		catch (Exception e) {
			logger.error("Properties file is not loaded from location " + propertiesPath, e);
		}
	}
	
	public void generateProcessFlow()
	{
		HTMLParser parser = new HTMLParser();
        try {
        	String htmlbasedir = SystemProperties.getInstance().getString("html.basedir");
        	String htmlstartpage = SystemProperties.getInstance().getString("appl." + projectName + ".htmlstartpage");
        	
        	String baseDir = SystemProperties.getInstance().getString("xml.basedir");			
			String xmldir = baseDir + projectName + "//processflow.xml";
            
			ResourceLoader.load(baseDir + projectName + "//components//components.xml", "components", projectName);
			ResourceLoader.load(baseDir + projectName + "//rules.xml", "rules", projectName);
        	parser.setHtmlPath(htmlbasedir, projectName, xmldir);
			parser.parsingHTMLPage(htmlstartpage);
			parser.populateProcessFlow();
			
			/*
			FileInputStream fin = new FileInputStream(xmldir);
			byte rb[] = new byte[fin.available()];
			fin.read(rb);
			fin.close();
			
			String xml = new String(rb);
			
			String replaceWith = null;
			
			replaceWith = "\t<pagecomponent seq=\"300\" path=\"ManageIndex.jsp\" name=\"ManageIndex\">\n\t\t<formlink action=\"show\" seq=\"0\" name=\"startapplication\">\n\t\t\t<formlinkforward eval=\"ProducerOneRule.isNotAgentRole\" component=\"homeAction\" action=\"view\" seq=\"0\" name=\"show\"/>\n\t\t\t<formlinkforward eval=\"ProducerOneRule.isAgentRole\" component=\"agentGeneralDetails\" action=\"view\" seq=\"1\" name=\"show\"/>\n\t\t</formlink>\n\t</pagecomponent>\n</processflow>";
			xml = xml.replaceAll("</processflow>", replaceWith);
			
			//System.out.println(xml);
			replaceWith = "\t<pagecomponent seq=\"301\" path=\"userRoles.jsp\" name=\"userRoles\">\n\t\t<formlink action=\"show\" seq=\"0\" name=\"startapplication\">\n\t\t\t<formlinkforward component=\"login\" action=\"view\" seq=\"0\" name=\"show\"/>\n\t\t</formlink>\n\t</pagecomponent>\n</processflow>";
			xml = xml.replaceAll("</processflow>", replaceWith);
			//System.out.println(xml);
			
			FileOutputStream fout = new FileOutputStream(xmldir);
			fout.write(xml.getBytes());
			fout.close();*/
			
        }catch (Exception e) {
            logger.error("Process flow is not generated..."+ e.toString(), e);
		}
	}



	public void generateDBFromMetadata(){
		try {
			
			RunDBSync runDBSync = new RunDBSync(projectName);
			List errors = runDBSync.generateDBFromMetadata();
			
		}catch(Exception e) {
			logger.error("DB synchrnization is not completed..."+ e.toString(), e);
		}
	}
	
	public void generateMetaFromDB( ){
		try {		
			
			RunGenMetaFromDB genMeta = new RunGenMetaFromDB(projectName);
			String metaXml = genMeta.generateMetadataFromDB();
			
		}catch(Exception e)	{
			logger.error("Metadata is not generated..."+ e.toString(), e);
		}
	}
	
	
	public void generateIbatis(int techType, boolean flag){
		try	{
			
			String baseDir = SystemProperties.getInstance().getString("xml.basedir");			
			String metadatapath = baseDir + projectName + "//metadata//metadata.xml";	
			String componentpath = baseDir + projectName + "//components//components.xml";
			String reportspath = baseDir + projectName + "//reports.xml";
			//String graphspath = baseDir + projectName + "//graphs.xml";
			
			SQLMapGenerator sqlMapGenerator = new SQLMapGenerator(projectName, metadatapath , componentpath, baseDir, flag, reportspath, null ,null);			
			sqlMapGenerator.generateSQLMaps(techType, reportspath, null ,null);
			
		}catch(Exception e)	{
			logger.error("Ibatis are not generated..."+ e.toString(), e);
		}
	}
	public static void main(String[] args) throws Exception {
		
		String projectname = "AccountantIns";
		String propertiesPath = "AccountantInsMain.properties";
		if(args.length > 0) {
			propertiesPath = args[0];
		}
		
		BuildProcess buildProcess = new BuildProcess(projectname, propertiesPath); 
		
		buildProcess.generateProcessFlow();
		
		int techType = 1;  // 1 for Java and 2 for .Net
		buildProcess.generateIbatis(techType, true);
		
		
		
		//ExceptionReport report = new ExceptionReport(projectname, propertiesPath);
		//report.generateExcelForHtml("BasicInfo.html", "D:\\output.xls");
		
		
		//report.setHtmlDirectory("carrier,admin,reports");
		//report.generateExceptionReport("C:\\Users\\vikask\\Desktop\\output.xls");
		//call();
		
		//encryptXMLs(projectname);
//		encryptXML(SystemProperties.getInstance().getString("xml.basedir")+"ProducerOne\\processflowImpl.xml");
//		decryptXML("D:\\vikas_workspace\\ProducerOne\\web\\XML\\ProducerOne\\components\\components.xml");
		//decryptXML("D:\\vikas_workspace\\ProducerOne\\web\\XML\\ProducerOne\\security.xml");
		//decryptXML("D:\\vikas_workspace\\ProducerOne\\web\\XML\\ProducerOne\\components.xml");
//		decryptXML("D:\\vikas_workspace\\ProducerOne\\web\\XML\\ProducerOne\\processflowImpl.xml");
		
		{ if (logger.isDebugEnabled()) logger.debug("Generated."); }
		
	}
}
