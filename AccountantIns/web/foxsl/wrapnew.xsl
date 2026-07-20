<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet version="1.1"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
xmlns:fo="http://www.w3.org/1999/XSL/Format"
exclude-result-prefixes="fo"
xmlns:fn="http://www.w3.org/2005/02/xpath-functions"
xmlns:java="java">

	<xsl:include  href="Policycoverletter.xsl" />
	<xsl:include  href="APL - 102 - 2009.xsl" />
	<xsl:include  href="APL - 103 - 2009.xsl" />
	<xsl:include  href="APL - 104 - 2009.xsl" />
	<xsl:include  href="APL - 105 - 2009.xsl" />
	<xsl:include  href="APL - 106 - 2010.xsl" />
	
	<xsl:include  href="APL - 108 - 2018.xsl" />
	<xsl:include  href="group4/APL - 108 - 2018 - AK.xsl" />
	<xsl:include  href="group3/APL - 108 - 2018 - VA.xsl" />        
	<xsl:include  href="APL - 124 - 2018.xsl" />
	<xsl:include  href="APL - 129 - 2018.xsl" />
	<xsl:include  href="ofac_notice.xsl" />
	
	<xsl:include  href="group2/APL-AR-2010.xsl" />
	<xsl:include  href="group2/APL-AZ-2010.xsl" />
	<xsl:include  href="group2/APL-DE-2010.xsl" />
	<xsl:include  href="group2/APL-GA-2010.xsl" />
	<xsl:include  href="group2/APL-IL-2010.xsl" />
	<xsl:include  href="group2/APL-IN-2010.xsl" />
	<xsl:include  href="group2/APL-IA-2010.xsl" />
	<xsl:include  href="group2/APL-KY-2010.xsl" />
	<xsl:include  href="group2/APL-KY-TAX.xsl" />
	<xsl:include  href="group2/APL-KS-2010.xsl" />
	<xsl:include  href="group2/APL-OH-2010.xsl" />
	<xsl:include  href="group2/APL-OH-ERP.xsl" />
	<xsl:include  href="group2/APL-OH-EX.xsl" />
	<xsl:include  href="group2/APL-TN-2010.xsl" />
	<xsl:include  href="group2/APL-WI-2010.xsl" />
	
	<xsl:include  href="group3/APL - LA - 2009.xsl" />
	<xsl:include  href="group3/APL - ME - 2009.xsl" />
	<xsl:include  href="group3/APL - MD - 2009.xsl" />
	<xsl:include  href="group3/APL - MT - 2009.xsl" />
	<xsl:include  href="group3/APL - NV - 2009.xsl" />
	<xsl:include  href="group3/APL - NM - 2009.xsl" />
	<xsl:include  href="group3/APL - ND - 2009.xsl" />
	<xsl:include  href="group3/APL - OR - 2009.xsl" />
	<xsl:include  href="group3/APL - RI - 2009.xsl" />
	<xsl:include  href="group3/APL - WA - 2009.xsl" />
	<xsl:include  href="group3/APL - SC - 2009.xsl" />
	<xsl:include  href="group3/APL - NH - 2009.xsl" />
	<xsl:include  href="group3/APL - VA - 2009.xsl" />
	<xsl:include  href="group3/APL - MN - 2009.xsl" />
	<xsl:include  href="group3/APL - NC - 2009.xsl" />
	<xsl:include  href="group3/APL - VT - 2009.xsl" />
	<xsl:include  href="group3/APL - NM Amendatory.xsl" /> 
	
	
	<xsl:include  href="group4/APL - DC - 2009.xsl" />
	<xsl:include  href="group4/APL - HI - 2009.xsl" />
	<xsl:include  href="group4/APL - MO - 2009 - 1.xsl" />
	<xsl:include  href="group4/APL - MO - 2009 - 2.xsl" />
	<xsl:include  href="group4/APL - SD - 2009.xsl" />
	<xsl:include  href="group4/APL - TX - 2009.xsl" />
	<xsl:include  href="group4/APL - notice - TX.xsl" />  
	<xsl:include  href="group4/MI-40-009-02-20-TX.xsl" />       
	<xsl:include  href="group4/APL - PA - 2009 - 1.xsl" />
	<xsl:include  href="group4/APL - PA - 2009 - 2.xsl" />
	<xsl:include  href="group4/APL - PA - 2009 - 3.xsl" />
	<xsl:include  href="group4/APL - AK - 2009.xsl" />
	<xsl:include  href="group4/APL - OK - 2009 -1.xsl"/>
	<xsl:include  href="group4/APL - OK - 2009 -2.xsl"/>
	
	<xsl:include  href="group4/APL - 103 - MA -  2009.xsl" />  
	<xsl:include  href="group4/APL - 104 - MA -  2009.xsl" />
	<xsl:include  href="group4/APL - 105 - MA -  2009.xsl" />
	<xsl:include  href="group4/APL - AMEND - CT.xsl" />
	<xsl:include  href="group4/APL - 105 - CT.xsl" />
	<xsl:include  href="group4/FM303-0-26-02-14.xsl" />
	<xsl:include  href="HotlineNotice.xsl" />
	
	<xsl:include  href="group5/CODisclosureForm.xsl" />
	<xsl:include  href="group5/APL-AMEND-CO.xsl" />
	<xsl:include  href="group5/FM_2_0_1033_03_12.xsl" />
	<xsl:include  href="group5/MI_43_002_06_15.xsl" />
	<xsl:include  href="group5/MO_42_001_06_15_policy.xsl" />
	 
	<xsl:include  href="Signature.xsl" />
	<xsl:include  href="Signature2021.xsl" />
	<xsl:include  href="APL - 108 - 2018 AK.xsl" />
	<xsl:include  href="APL - 108 - 2018 FL-MT-VT.xsl" />
	<xsl:include  href="APL - 124 - 2018 FL-MT-VT.xsl" />
	<xsl:include  href="APL - 129 - 2018 FL-MT-VT.xsl" />
	<xsl:include  href="MI_40_007_04_19_FL.xsl" />

         
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
						<xsl:call-template name="Policycoverletter" />
						<fo:block break-after="page" />
						
						<xsl:call-template name="HOTLINENOTICE" />
						<fo:block break-after="page" />
							
						<xsl:if test="response/templates/template_id_APL_102= 'APL_102'">
						<xsl:call-template name="Policycoverletter1" />
						<fo:block break-after="page" />
						</xsl:if>
						
						<!-- <xsl:call-template name="Signature" />
						<fo:block break-after="page" /> -->
							<xsl:call-template name="Signature2021" />
						<fo:block break-after="page" />
						<!-- Commented below block of Form attachment to stop automatic attachment of APL - 103 - 2009 in policy document -->								
						<xsl:if test="response/templates/template_id_APL_103= 'APL_103'">
						<xsl:call-template name="Policycoverletter2" />
						<fo:block break-after="page" />
						</xsl:if>						
												
						<xsl:if test="response/templates/template_id_APL_104= 'APL_104'">	
							<xsl:choose>						
								<xsl:when test="response/address_freeform_01/data/StateCode= 'MA'">	
									<xsl:if test="response/ClaimExpensesTypeFlag= 'Y'">							
										<xsl:call-template name="APL-104-MA" />
											<fo:block break-after="page" />													
										  </xsl:if>
								</xsl:when>							
								<xsl:otherwise>
									<xsl:call-template name="Policycoverletter3" />
										<fo:block break-after="page" />     
								 </xsl:otherwise>
							</xsl:choose>
						</xsl:if>
						
						<xsl:if test="response/templates/template_id_APL_105= 'APL_105'">
							<xsl:choose>
							<xsl:when test="response/address_freeform_01/data/StateCode= 'CT'">
								<xsl:if test="response/ClaimOptionTypeFlag ='Y' ">
					  				<xsl:call-template name="APL-105-CT" />
									<fo:block break-after="page" />
								</xsl:if>
							</xsl:when>
							<xsl:when test="response/address_freeform_01/data/StateCode= 'MA'">
								<xsl:if test="response/ClaimOptionTypeFlag ='Y' ">
					  				<xsl:call-template name="APL-105-MA" />
									<fo:block break-after="page" />
								</xsl:if>
							</xsl:when>							
							<xsl:otherwise>
								<xsl:call-template name="Policycoverletter4" />
								<fo:block break-after="page" />
							</xsl:otherwise>
							</xsl:choose>	
						</xsl:if>	
						 
						<xsl:if test="response/templates/template_id_APL_106= 'APL_106'">
							<xsl:call-template name="Policycoverletter5" />
							<fo:block break-after="page" />
						</xsl:if> 						
						
						<xsl:choose>						
							<xsl:when test="response/address_freeform_01/data/StateCode= 'AK' and response/isNewFormChanges= 'N'">															
								<xsl:call-template name="Policycoverletter8AK" />
									<fo:block break-after="page" />	
							</xsl:when>	
							<xsl:when test="response/address_freeform_01/data/StateCode= 'AK' and response/isNewFormChanges= 'Y'">															
								<xsl:call-template name="Policycoverletter8AK20190501" />
									<fo:block break-after="page" />	
							</xsl:when>	
							<xsl:when test="(response/isNewFormChanges= 'Y') and (response/address_freeform_01/data/StateCode= 'FL' or response/address_freeform_01/data/StateCode= 'MT' or response/address_freeform_01/data/StateCode= 'VT')">															
										<xsl:call-template name="Policycoverletter20190501" />
											<fo:block break-after="page" />	
							</xsl:when>	
							<xsl:when test="response/address_freeform_01/data/StateCode= 'VA'">															
								<xsl:call-template name="Policycoverletter8VA" />
									<fo:block break-after="page" />	
							</xsl:when>							
							<xsl:otherwise>									
								<xsl:call-template name="Policycoverletter8" />
									<fo:block break-after="page" />													                    
							 </xsl:otherwise>
						</xsl:choose>		
						
						
						
						<xsl:choose>						
							<xsl:when test="(response/isNewFormChanges= 'Y') and (response/address_freeform_01/data/StateCode= 'FL' or response/address_freeform_01/data/StateCode= 'MT' or response/address_freeform_01/data/StateCode= 'VT')">															
									<xsl:call-template name="SecurityLetter20190501" />
									<fo:block break-after="page" />						
							</xsl:when>	
							<xsl:otherwise>									
								<xsl:call-template name="SecurityLetter" />
								<fo:block break-after="page" />												                    
							 	</xsl:otherwise>
						</xsl:choose>	
						
						
							
						
						
						<xsl:choose>						
							<xsl:when test="(response/isNewFormChanges= 'Y') and (response/address_freeform_01/data/StateCode= 'FL' or response/address_freeform_01/data/StateCode= 'MT' or response/address_freeform_01/data/StateCode= 'VT')">															
									<xsl:call-template name="exposureendorsement20190501" />
									<fo:block break-after="page" />						
							</xsl:when>	
							<xsl:otherwise>									
								<xsl:call-template name="exposureendorsement" />
									<fo:block break-after="page" />								                    
							 	</xsl:otherwise>
						</xsl:choose>	
						
						
						
						<xsl:call-template name="Policycoverletter6" />
						<fo:block break-after="page" />
						
						<xsl:if test="response/address_freeform_01/data/StateCode= 'AR'">
							<xsl:call-template name="APL-State-AR" />
							<fo:block break-after="page" />
						</xsl:if>
						
						<xsl:if test="response/address_freeform_01/data/StateCode= 'AZ'">
							<xsl:call-template name="APL-State-AZ" />
							<fo:block break-after="page" />
						</xsl:if>
						
						<xsl:if test="response/address_freeform_01/data/StateCode= 'DE'">
							<xsl:call-template name="APL-State-DE" />
							<fo:block break-after="page" />
						</xsl:if>
						<xsl:if test="response/address_freeform_01/data/StateCode= 'FL'">
							<xsl:call-template name="MI_40_007_04_19_FL" />
							<fo:block break-after="page" />
						</xsl:if>
						
						<xsl:if test="response/address_freeform_01/data/StateCode= 'GA'">
							<xsl:call-template name="APL-State-GA" />
							<fo:block break-after="page" />
						</xsl:if>
						
						<xsl:if test="response/address_freeform_01/data/StateCode= 'IL'">
							<xsl:call-template name="APL-State-IL" />
							<fo:block break-after="page" />
						</xsl:if>
						
						<xsl:if test="response/address_freeform_01/data/StateCode= 'IN'">
							<xsl:call-template name="APL-State-IN" />
							<fo:block break-after="page" />
						</xsl:if>
						
						<xsl:if test="response/address_freeform_01/data/StateCode= 'IA'">
							<xsl:call-template name="APL-State-IA" />
							<fo:block break-after="page" />
						</xsl:if>
						
							<xsl:if test="response/address_freeform_01/data/StateCode= 'KS'">
							<xsl:call-template name="APL-State-KS" />
							<fo:block break-after="page" />
						</xsl:if>
						
						<xsl:if test="response/address_freeform_01/data/StateCode= 'KY'">
							<xsl:call-template name="APL-State-KY" />
							<xsl:if test="response/KYTaxFormFlag= 'Y'">							  									
								<fo:block break-after="page" />
								  <xsl:call-template name="APL-KY-TAX" />
								<fo:block break-after="page" />
							</xsl:if>
							
						</xsl:if>
						
											
						<xsl:if test="response/address_freeform_01/data/StateCode= 'OH'">
							<xsl:call-template name="APL-State-OH" />
							<fo:block break-after="page" />
							<!--  <xsl:call-template name="APL-OH-ERP" />
							<fo:block break-after="page" />
							<xsl:call-template name="APL-OH-EX" />
							<fo:block break-after="page" />-->
						</xsl:if>
						
						
						
						<xsl:if test="response/address_freeform_01/data/StateCode= 'TN'">
							<xsl:call-template name="APL-State-TN" />
							<fo:block break-after="page" />
						</xsl:if>
						
						<xsl:if test="response/address_freeform_01/data/StateCode= 'WI'">
							<xsl:call-template name="APL-State-WI" />
							<fo:block break-after="page" />
						</xsl:if>
						
						<xsl:if test="response/address_freeform_01/data/StateCode= 'LA'">
							<xsl:call-template name="APL-State-LA" />
							<fo:block break-after="page" />
						</xsl:if>
						
						<xsl:if test="response/address_freeform_01/data/StateCode= 'ME'">
							<xsl:call-template name="APL-State-ME" />
							<fo:block break-after="page" />
						</xsl:if>						
						
						<xsl:if test="response/address_freeform_01/data/StateCode= 'MD'">
							<xsl:call-template name="APL-State-MD" />
							<fo:block break-after="page" />
						</xsl:if>
						
						<xsl:if test="response/address_freeform_01/data/StateCode= 'MN'">
							<xsl:call-template name="APL-State-MN" />
							<fo:block break-after="page" />
						</xsl:if>
						
						<xsl:if test="response/address_freeform_01/data/StateCode= 'MT'">
							<xsl:call-template name="APL-State-MT" />
							<fo:block break-after="page" />
						</xsl:if>
						
						<xsl:if test="response/address_freeform_01/data/StateCode= 'NV'">
							<xsl:call-template name="APL-State-NV" />
							<fo:block break-after="page" />
						</xsl:if>
						
						<xsl:if test="response/address_freeform_01/data/StateCode= 'NM'">
							<!-- <xsl:call-template name="APL-State-NM" /> -->
							<xsl:if test="response/ClaimExpensesTypeFlag ='Y' ">
								  <xsl:call-template name="amendatoryform" />
								<fo:block break-after="page" />
							</xsl:if> 
							
						</xsl:if>
						
						<xsl:if test="response/address_freeform_01/data/StateCode= 'ND'">
							<xsl:call-template name="APL-State-ND" />
							<fo:block break-after="page" />
							<xsl:if test="response/IsDefensePaidWithinRuleND ='Y' ">
								  <xsl:call-template name="ND-Defense-Inside" />
								<fo:block break-after="page" />
							</xsl:if>
							
						</xsl:if>
						
						<xsl:if test="response/address_freeform_01/data/StateCode= 'OR'">
							<xsl:call-template name="APL-State-OR" />
							<fo:block break-after="page" />
						</xsl:if>
						
						<xsl:if test="response/address_freeform_01/data/StateCode= 'RI'">
							<xsl:call-template name="APL-State-RI" />
							<fo:block break-after="page" />
						</xsl:if>
						
						<xsl:if test="response/address_freeform_01/data/StateCode= 'VT'">
							<xsl:call-template name="APL-State-VT" />
							<fo:block break-after="page" />
						</xsl:if>
						
						<xsl:if test="response/address_freeform_01/data/StateCode= 'VA'">
							<xsl:call-template name="APL-State-VA" />
							<fo:block break-after="page" />
						</xsl:if>						
						
						<xsl:if test="response/address_freeform_01/data/StateCode= 'WA'">
							<xsl:call-template name="APL-State-WA" />
							<fo:block break-after="page" />
						</xsl:if>
						
						<xsl:if test="response/address_freeform_01/data/StateCode= 'SC'">
							<xsl:call-template name="APL-State-SC" />
							<fo:block break-after="page" />
						</xsl:if>
						
						<xsl:if test="response/address_freeform_01/data/StateCode= 'NH'">
							<xsl:call-template name="APL-State-NH" />
							<fo:block break-after="page" />
						</xsl:if>
						
						<xsl:if test="response/address_freeform_01/data/StateCode= 'NC'">
							<xsl:call-template name="APL-State-NC" />
							<fo:block break-after="page" />
						</xsl:if>
						
						<xsl:if test="response/address_freeform_01/data/StateCode= 'AK'">
							<xsl:call-template name="APL-State-AK" />
							<fo:block break-after="page" />
							<xsl:if test="response/ClaimExpensesTypeFlag ='Y' ">	
								<xsl:call-template name="Form_FM_201033_12" />
								<fo:block break-after="page" />
							</xsl:if>
							<xsl:if test="response/ClaimExpensesTypeFlag ='N' ">	
								<xsl:call-template name="MI_43_002_06_15" />
								<fo:block break-after="page" />
							</xsl:if>
								
						</xsl:if>
						
						<xsl:if test="response/address_freeform_01/data/StateCode= 'DC'">
							<xsl:call-template name="APL-State-DC" />
							<fo:block break-after="page" />
						</xsl:if>
						
						<xsl:if test="response/address_freeform_01/data/StateCode= 'HI'">
							<xsl:call-template name="APL-State-HI" />
							<fo:block break-after="page" />
						</xsl:if>
						
						<xsl:if test="response/address_freeform_01/data/StateCode= 'MO'">
							<xsl:call-template name="APL-State-MO-1" />
							<fo:block break-after="page" />
							 <xsl:call-template name="APL-State-MO-2" />
							<fo:block break-after="page" />
						</xsl:if>
						
						
						<xsl:if test="response/address_freeform_01/data/StateCode= 'OK'">	
							<xsl:call-template name="APL-State-OK-1" />
							<fo:block break-after="page" />
							<xsl:if test="response/OKStateRuleFlag= 'Y'">							
								<xsl:call-template name="APL-State-OK-2" />
									<fo:block break-after="page" />												
							</xsl:if>
						</xsl:if>
						
						<xsl:if test="response/address_freeform_01/data/StateCode= 'SD'">
							<xsl:call-template name="APL-State-SD" />
							<fo:block break-after="page" />
						</xsl:if>
						
						<xsl:if test="response/address_freeform_01/data/StateCode= 'TX'">
							<xsl:call-template name="APL-State-TX" />
							<fo:block break-after="page" />
							 <xsl:call-template name="APL-State-TX-Notice" />
							<fo:block break-after="page" />
							<xsl:call-template name="APL-State-TX-Notice-NEW" />
							<fo:block break-after="page" />
						</xsl:if>
						
						<xsl:if test="response/address_freeform_01/data/StateCode= 'PA'">
							<xsl:call-template name="APL-State-PA-1" />
							<fo:block break-after="page" />							
								<xsl:if test="response/ClaimExpensesTypeFlag= 'N'">							
									<xsl:call-template name="APL-State-PA-2" />
									<fo:block break-after="page" />
									<xsl:call-template name="APL-State-PA-3" />
									<fo:block break-after="page" />						
							  </xsl:if>
						</xsl:if>						
						
						<xsl:if test="response/address_freeform_01/data/StateCode= 'CT'">
							<xsl:call-template name="APL-AMEND-CT" />
							<fo:block break-after="page" />
						</xsl:if>
						<xsl:if test="response/address_freeform_01/data/StateCode= 'MI'">
							<xsl:call-template name="FM3030260214" />
							<fo:block break-after="page" />
						</xsl:if>
						
						<xsl:if test="response/address_freeform_01/data/StateCode= 'CO'">
							<xsl:call-template name="CODisclosureForm" />
							<fo:block break-after="page" />
							<xsl:call-template name="APL-AMEND-CO" />
							<fo:block break-after="page" />								
						</xsl:if>
						
				   </fo:block>
				</fo:flow>
					
			</fo:page-sequence>
		
		</fo:root>
     </xsl:template>    
</xsl:stylesheet>
		    	