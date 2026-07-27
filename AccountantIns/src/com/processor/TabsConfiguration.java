/**
 * 
 */
package com.processor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.manage.managecomponent.tabsconfiguration.TabsConfigurationResources;
import com.manage.managecomponent.tabsconfiguration.TabsconfigurationresourceImpl;
import com.manage.managemetadata.functions.commonfunctions.DataUtils;
import com.util.CacheManager;
import com.util.Context;
import com.util.StringUtils;
import com.util.SystemProperties;

/**
 * @author Amit Jain, Outline Systems Inc.
 * @Created on Oct 27, 2011
 */
public class TabsConfiguration {
	private static Logger logger = Logger.getLogger(TabsConfiguration.class);
	
	public static final int Client_ID=1;
	
    public Map contextTabsConfigMap = new HashMap();
	
	public void getTabsConf(){
		//Use N for Hide
		
		Map<String, String> showHideTabsConfMap = null;
		if(CacheManager.get("Tabs_Conf") == null)
			showHideTabsConfMap = new HashMap<String, String>();
		else
			showHideTabsConfMap = (Map)CacheManager.get("Tabs_Conf");
		
		if(CacheManager.get("Tabs_Conf_Map") == null)
			contextTabsConfigMap = new HashMap<String, String>();
		else
			contextTabsConfigMap = (Map)CacheManager.get("Tabs_Conf_Map");
			
		//going to load tabsconfiguration.xml
		try{
			Context ctx = new Context();
			ctx.setProject("AccountantIns");
			List tabsConfigResList = TabsConfigurationResources.getInstance(ctx).getTabsConfigurationResourceList();
			if(tabsConfigResList != null && tabsConfigResList.size() > 0){
				for(int i=0; i<tabsConfigResList.size(); i++){
					TabsconfigurationresourceImpl impl = (TabsconfigurationresourceImpl)tabsConfigResList.get(i);
					
					showHideTabsConfMap.put(impl.getName(), impl.getAccesslevel() == 1 ? "Y" : "N");

					if(!StringUtils.isBlank(impl.getContextname()))
						contextTabsConfigMap.put(impl.getContextname(), impl.getAccesslevel() == 1 ? "Y" : "N");
					else
						contextTabsConfigMap.put(impl.getName(), impl.getAccesslevel() == 1 ? "Y" : "N");
					
					//going to set default value for configuration if mentioned in xml file
					if(!StringUtils.isBlank(impl.getValue())){
						String value = impl.getValue();
						
						//checking value from properties file
						if(value.length() > 1){
							try{
								String propertyValue = SystemProperties.getInstance().getProperty(value);
								if(propertyValue != null)
									value = propertyValue;
							}catch (Exception e) {
								logger.error("Error in TabsConfiguration.getTabsConf", e);
								// TODO: handle exception
							}
						}
						
						//checking value from label conf
						if(value.length() > 1){
							try{
								value = DataUtils.getLabelFromLabelConf(value);
							}catch(Exception e){
								logger.error("Error in TabsConfiguration.getTabsConf", e);
								// TODO: handle exception
							}
						}
						
						if(value == null)
							value = impl.getValue();
						
						if(!StringUtils.isBlank(impl.getContextname())){
							contextTabsConfigMap.put(impl.getContextname(), value);
						}else{
							contextTabsConfigMap.put(impl.getName(), value);
						}
					}
				}
			}
		}catch (Exception e) {
			logger.error("Unable to read tabsconfiguration.xml due to error : " + e.getMessage(), e);
		}
		//ended
		
	
		
	}
	
	
}
