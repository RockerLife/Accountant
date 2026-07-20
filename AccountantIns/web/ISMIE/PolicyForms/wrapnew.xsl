<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet version="1.1"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
xmlns:fo="http://www.w3.org/1999/XSL/Format"
exclude-result-prefixes="fo"
xmlns:fn="http://www.w3.org/2005/02/xpath-functions"
xmlns:java="java">

	
	<xsl:include  href="CommonForms/AAA - 04 - P001 - 2022.xsl" />
	<xsl:include  href="CommonForms/AAA - 04 - P002 - 2022.xsl" />
	<xsl:include  href="CommonForms/AAA - 04 - F002 - 2022.xsl" />
	<xsl:include  href="CommonForms/AAA - 04 - F003 - 2022.xsl" />
	<xsl:include  href="CommonForms/AAA - 04 - F007 - 2022.xsl" />
	<xsl:include  href="CommonForms/AAA - 04 - F012 - 2022.xsl" />
	<xsl:include  href="CommonForms/AAA - 04 - F018 - 2022.xsl" />
	
	<xsl:include  href="CommonForms/ofac_notice.xsl" />
    <xsl:include  href="CommonForms/CommonHeader.xsl" />
	
	<xsl:include  href="StateForms/group1/AAA - 04 - F005 - 2022.xsl" />
	<xsl:include  href="StateForms/group1/AAA - 04 - F011 - 2022.xsl" />
	<xsl:include  href="StateForms/group1/AAA - 04 - F017 - 2022.xsl" />
	<xsl:include  href="StateForms/group1/AAA - 04 - F039 - 2022.xsl" />
	<xsl:include  href="StateForms/group1/AAA - 04 - F028 - 2022.xsl" />
	<xsl:include  href="StateForms/group1/AAA - 04 - S006 - 2022.xsl" />
    <xsl:include  href="StateForms/group1/AAA - 04 - F040 - 2022.xsl" />  
    <xsl:include  href="StateForms/group1/AAA - 04 - F024 - 2022.xsl" />    
    <xsl:include  href="StateForms/group1/AAA - 04 - F022 - 2022.xsl" /> 
    <xsl:include  href="StateForms/group1/AAA - 04 - F036 - 2022.xsl" />  
    <xsl:include  href="StateForms/group1/AAA - 04 - F029 - 2022.xsl" />
    <xsl:include  href="StateForms/group1/AAA - 04 - F038 - 2022.xsl" />
    <xsl:include  href="StateForms/group1/AAA - 04 - F026 - 2022.xsl" />
    <xsl:include  href="StateForms/group1/AAA - 04 - F035 - 2022.xsl" />
    <xsl:include  href="StateForms/group1/AAA - 04 - F025 - 2022.xsl" />
    <xsl:include  href="StateForms/group1/AAA - 04 - F023 - 2022.xsl" />
    
    <xsl:include  href="StateForms/group1/AAA - 04 - F037 - 2022.xsl" />
    <xsl:include  href="StateForms/group1/AAA - 04 - F042 - 2022.xsl" />
    <xsl:include  href="StateForms/group1/AAA - 04 - F043 - 2022.xsl" />
    <xsl:include  href="StateForms/group1/AAA - 04 - F044 - 2022.xsl" />
    
    
    <xsl:include  href="StateForms/group1/AAA - 04 - F041 - 2022.xsl" />
	<xsl:template match="/">
		
		<fo:root>
		
			<fo:layout-master-set>
				<fo:simple-page-master master-name="A4-portrait" page-width="21cm"   
					page-height="29.7cm" margin-top="10mm" margin-bottom="1.0mm" margin-left="10mm" 
					margin-right="10mm">
					<fo:region-body margin-top="2.0mm"/>
					<fo:region-before extent="1.0mm"/> 
					<fo:region-after /> 					
				</fo:simple-page-master>
			</fo:layout-master-set>		
	
		     <fo:page-sequence master-reference="A4-portrait">			
				
				<fo:flow flow-name="xsl-region-body">
					<fo:block>
						
						<xsl:if test="response/templates/template_id_AAA_P002= 'AAA_P002'">
							<xsl:call-template name="AAA04P002" />						
							<fo:block break-after="page" />
						</xsl:if>
							
						<!-- Policy Form -->
						<xsl:if test="response/templates/template_id_AAA_P001= 'AAA_P001'">
							<xsl:call-template name="AAA04P001" />						
							<fo:block break-after="page" />
						</xsl:if>
						
						<!-- Defense Expenses in Addition to Limits of Liability -->
						<xsl:if test="response/templates/template_id_AAA_F002= 'AAA_F002'">
							<xsl:call-template name="AAA04F002" />						
							<fo:block break-after="page" />
						</xsl:if>					
										
						<!--Aggregate Deductible for All Claims  -->	
						<xsl:if test="response/templates/template_id_AAA_F003= 'AAA_F003'">
							<xsl:call-template name="AAA04F003" />						
							<fo:block break-after="page" />
						</xsl:if>	
						
						<!-- ACCOUNTANTS PROFESSIONAL LIABILITY  BROAD ADVANTAGE ENDORSEMENT -->
						<xsl:if test="response/templates/template_id_AAA_F007= 'AAA_F007'">
							<xsl:call-template name="AAA04F007" />						
							<fo:block break-after="page" />
						</xsl:if>	
								
								
						<!-- SECURITY INCIDENT AND IDENTIFICATION THEFT EXTENSION ENDORSEMENT -->		
						<xsl:if test="response/templates/template_id_AAA_F012= 'AAA_F012'">
							<xsl:call-template name="AAA04F012" />						
							<fo:block break-after="page" />
						</xsl:if>
							
						<!-- EXPOSURE ENDORSEMENT -->	
						<xsl:if test="response/templates/template_id_AAA_F018= 'AAA_F018'">
							<xsl:call-template name="AAA04F018" />						
							<fo:block break-after="page" />
						</xsl:if>
						
						<xsl:if test="response/templates/template_id_AAA_F005= 'AAA_F005'">
							<xsl:call-template name="AAA04F005" />						
							<fo:block break-after="page" />
						</xsl:if>
						<xsl:if test="response/templates/template_id_AAA_F011= 'AAA_F011'">
							<xsl:call-template name="AAA04F011" />						
							<fo:block break-after="page" />
						</xsl:if>
						
						<xsl:if test="response/templates/template_id_AAA_F017= 'AAA_F017'">
							<xsl:call-template name="AAA04F017" />						
							<fo:block break-after="page" />
						</xsl:if>
						
						<xsl:if test="response/templates/template_id_AAA_F039= 'AAA_F039'">
							<xsl:call-template name="AAA04F039" />						
							<fo:block break-after="page" />
						</xsl:if>
						
						<xsl:if test="response/templates/template_id_AAA_S006= 'AAA_S006'">
							<xsl:call-template name="AAA04S006" />						
							<fo:block break-after="page" />
						</xsl:if>
						
						<xsl:if test="response/templates/template_id_AAA_F028= 'AAA_F028'">
							<xsl:call-template name="AAA04F028" />						
							<fo:block break-after="page" />
						</xsl:if>
						
						<xsl:if test="response/templates/template_id_AAA_F040= 'AAA_F040'">
							<xsl:call-template name="AAA04F040" />						
							<fo:block break-after="page" />
						</xsl:if>
						
						<xsl:if test="response/templates/template_id_AAA_F024= 'AAA_F024'">
							<xsl:call-template name="AAA04F024" />						
							<fo:block break-after="page" />
						</xsl:if>
						
						<xsl:if test="response/templates/template_id_AAA_F022= 'AAA_F022'">
							<xsl:call-template name="AAA04F022" />						
							<fo:block break-after="page" />
						</xsl:if>
						
						<xsl:if test="response/templates/template_id_AAA_F036= 'AAA_F036'">
							<xsl:call-template name="AAA04F036" />						
							<fo:block break-after="page" />
						</xsl:if>
						
						<xsl:if test="response/templates/template_id_AAA_F029= 'AAA_F029'">
							<xsl:call-template name="AAA04F029" />						
							<fo:block break-after="page" />
						</xsl:if>
						
						<xsl:if test="response/templates/template_id_AAA_F038= 'AAA_F038'">
							<xsl:call-template name="AAA04F038" />						
							<fo:block break-after="page" />
						</xsl:if>
						
						<xsl:if test="response/templates/template_id_AAA_F026= 'AAA_F026'">
							<xsl:call-template name="AAA04F026" />						
							<fo:block break-after="page" />
						</xsl:if>
						
						<xsl:if test="response/templates/template_id_AAA_F035= 'AAA_F035'">
							<xsl:call-template name="AAA04F035" />						
							<fo:block break-after="page" />
						</xsl:if>
						
						<xsl:if test="response/templates/template_id_AAA_F025= 'AAA_F025'">
							<xsl:call-template name="AAA04F025" />						
							<fo:block break-after="page" />
						</xsl:if>
						
						<xsl:if test="response/templates/template_id_AAA_F041= 'AAA_F041'">
							<xsl:call-template name="AAA04F041" />						
							<fo:block break-after="page" />
						</xsl:if>
						
						<xsl:if test="response/templates/template_id_AAA_F023= 'AAA_F023'">
							<xsl:call-template name="AAA04F023" />						
							<fo:block break-after="page" />
						</xsl:if>
						
						<xsl:if test="response/templates/template_id_AAA_F037= 'AAA_F037'">
							<xsl:call-template name="AAA04F037" />						
							<fo:block break-after="page" />
						</xsl:if>
						
						<xsl:if test="response/templates/template_id_AAA_F042= 'AAA_F042'">
							<xsl:call-template name="AAA04F042" />						
							<fo:block break-after="page" />
						</xsl:if>
						
						<xsl:if test="response/templates/template_id_AAA_F043= 'AAA_F043'">
							<xsl:call-template name="AAA04F043" />						
							<fo:block break-after="page" />
						</xsl:if>
						
						<xsl:if test="response/templates/template_id_AAA_F044= 'AAA_F044'">
							<xsl:call-template name="AAA04F044" />						
							<fo:block break-after="page" />
						</xsl:if>
						
						<xsl:call-template name="Ofac" />
						<fo:block break-after="page" />					
						
				   </fo:block>
				</fo:flow>
					
			</fo:page-sequence>
		
		</fo:root>
     </xsl:template>    
</xsl:stylesheet>
		    	