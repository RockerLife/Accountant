<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet version="1.1"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:fo="http://www.w3.org/1999/XSL/Format"
	exclude-result-prefixes="fo"
	xmlns:fn="http://www.w3.org/2005/02/xpath-functions"
	xmlns:java="java">
	 
	  <xsl:include  href="CommonHeaderFilledACC.xsl" />
	  <xsl:include  href="CommonDetailFilledACC.xsl" />
      <xsl:include  href="AAA - 04 - S003 - 2022.xsl" />
      <xsl:include  href="AAA - 04 - S005 - 2022.xsl" />
      <xsl:include  href="AAA - 04 - F004 - 2022.xsl" />
      <xsl:include  href="AAA - 04 - F009 - 2022.xsl" />  
      <xsl:include  href="AAA - 04 - I004 - 2022.xsl" />  
      <xsl:include  href="AAA - 04 - F020 - 2022.xsl" />
      <xsl:include  href="AAA - 04 - F021 - 2022.xsl" />
      <xsl:include  href="AAA - 04 - F013 - 2022.xsl" />
	  <xsl:template match="/">
		
		<fo:root>
		
			<fo:layout-master-set>
				<fo:simple-page-master master-name="A4-portrait" page-width="21cm"   
					page-height="29.7cm" margin-top="10mm" margin-bottom="8mm" margin-left="10mm" 
					margin-right="10mm">
					<fo:region-body margin-top="2.0mm"/>
					<fo:region-before extent="1.0mm"/> 
					<fo:region-after /> 					
				</fo:simple-page-master>
			</fo:layout-master-set>		
	
		     <fo:page-sequence master-reference="A4-portrait">		
				        
				<fo:flow flow-name="xsl-region-body">					
					<fo:block>
						<!-- <xsl:if test="response/APL_107_ENDORSEMENT= 'Y'">
							<xsl:call-template name="Policycoverletter9" />	
							<fo:block break-after="page" />	
						</xsl:if> -->
						<xsl:if test="response/AAA_S003_ENDORSEMENT= 'Y'">
							<xsl:call-template name="AAA04S003" />	
							<fo:block break-after="page" />
						</xsl:if> 
						 <xsl:if test="response/AAA_S005_ENDORSEMENT= 'Y'">
							<xsl:call-template name="AAA04S005" />						
							<fo:block break-after="page" />
						</xsl:if> 					
						<xsl:if test="response/AAA_F004_ENDORSEMENT= 'Y'">
							<xsl:call-template name="AAA04F004" />						
							<fo:block break-after="page" />
						</xsl:if> 	
						<xsl:if test="response/AAA_F009_ENDORSEMENT= 'Y'">
							<xsl:call-template name="AAA04F009" />						
							<fo:block break-after="page" />
						</xsl:if> 	
						<xsl:if test="response/AAA_I004_ENDORSEMENT= 'Y'">
							<xsl:call-template name="AAA04I004" />						
							<fo:block break-after="page" />
						</xsl:if> 	
						<xsl:if test="response/AAA_F020_ENDORSEMENT= 'Y'">
							<xsl:call-template name="AAA04F020" />						
							<fo:block break-after="page" />
						</xsl:if> 	
						<xsl:if test="response/AAA_F021_ENDORSEMENT= 'Y'">
							<xsl:call-template name="AAA04F021" />						
							<fo:block break-after="page" />
						</xsl:if> 	
						<xsl:if test="response/AAA_F013_ENDORSEMENT= 'Y'">
							<xsl:call-template name="AAA04F013" />						
							<fo:block break-after="page" />
						</xsl:if> 	
				   	</fo:block>
				   	
				</fo:flow>
					
			</fo:page-sequence>
		
		</fo:root>
     </xsl:template>    
</xsl:stylesheet>
		    	