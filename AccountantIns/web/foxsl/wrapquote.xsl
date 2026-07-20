<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet version="1.1"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:fo="http://www.w3.org/1999/XSL/Format"
	exclude-result-prefixes="fo"
	xmlns:fn="http://www.w3.org/2005/02/xpath-functions"
	xmlns:java="java">
	 
	 <xsl:include href="quoteLetterXSLPath" />
	 <xsl:include href="APL_PA_2009Path" />
	 
	 
	
     
	  <xsl:template match="/">
		
		<xsl:variable name="protexureAccountantsLogo" select="response/protexureAccountantsLogoPath"></xsl:variable>
		<xsl:variable name="headerBottom" select="response/headerBottomPath"></xsl:variable>
		
		<fo:root>
		
			<fo:layout-master-set>
				<fo:simple-page-master master-name="A4-portrait" page-width="21cm"   
					page-height="29.7cm" margin-top="1.0cm" margin-bottom="0.7in" margin-left="0.5in" 
					margin-right="0.5in">
					<fo:region-body margin-top="1.8in"/>
					<fo:region-before extent="3.8in"/> 
					<fo:region-after /> 					
				</fo:simple-page-master>
			</fo:layout-master-set>	
	
		     <fo:page-sequence master-reference="A4-portrait">			
				
				<fo:static-content flow-name="xsl-region-before">
				 <fo:block text-align="left">				  	
				  	<fo:external-graphic src="{$protexureAccountantsLogo}" content-height="6em" content-width="20em"/>           	
				  </fo:block>	
				  
				  <fo:block>
					<fo:table>
				         <fo:table-column column-width = "80mm" />
				         <fo:table-column column-width = "50mm" />
				         <fo:table-column column-width = "70mm" />
				         <fo:table-body>
					           <fo:table-row>
					             <fo:table-cell ><fo:block font-size="9px" font-family="Arial" ></fo:block></fo:table-cell>
					             <fo:table-cell ><fo:block font-size="9px" font-family="Arial" ></fo:block></fo:table-cell>
					           	 <fo:table-cell ><fo:block font-size="9px" font-family="Arial" >4200 Commerce Court, Ste 102</fo:block></fo:table-cell>
					           </fo:table-row>
					           <fo:table-row>
					             <fo:table-cell ><fo:block font-size="9px" font-family="Arial" ></fo:block></fo:table-cell>
					             <fo:table-cell ><fo:block font-size="9px" font-family="Arial" ></fo:block></fo:table-cell>
					           	 <fo:table-cell ><fo:block font-size="9px" font-family="Arial" >Lisle, IL 60532</fo:block></fo:table-cell>
					           </fo:table-row>
					           <fo:table-row>
					             <fo:table-cell ><fo:block font-size="9px" font-family="Arial" ></fo:block></fo:table-cell>
					             <fo:table-cell ><fo:block font-size="9px" font-family="Arial" ></fo:block></fo:table-cell>
					           	 <fo:table-cell ><fo:block font-size="9px" font-family="Arial" >Phone (888) 803-9898</fo:block></fo:table-cell>
					           </fo:table-row>
					           <fo:table-row>
					             <fo:table-cell ><fo:block font-size="9px" font-family="Arial" ></fo:block></fo:table-cell>
					             <fo:table-cell ><fo:block font-size="9px" font-family="Arial" ></fo:block></fo:table-cell>
					           	 <fo:table-cell ><fo:block font-size="9px" font-family="Arial" >Fax (630) 799-1796</fo:block></fo:table-cell>
					           </fo:table-row>
						</fo:table-body>
					</fo:table>
					<fo:external-graphic src="{$headerBottom}" content-width="44em"/> 
				</fo:block>	
				
				<fo:table>
			         <fo:table-column column-width = "120mm" />
			         <fo:table-column column-width = "70mm" />
			         <fo:table-body>
			         	 <fo:table-row>
				             <fo:table-cell><fo:block font-size="10px" font-family="Arial" color="grey"><fo:inline font-weight="bold">Quotation for Accountants Professional Liability Insurance</fo:inline></fo:block></fo:table-cell>
				             <fo:table-cell><fo:block  font-size="10px" text-indent="1cm" color="grey">Date:<xsl:value-of select="response/date_freeform_01/data/QuoteDate" />&#160;&#160;<xsl:value-of select="response/time_freeform_01/data/QuoteTime" /></fo:block></fo:table-cell>
				           </fo:table-row>				         
			         </fo:table-body>				         
				</fo:table>
				
				
				</fo:static-content>
				
				<fo:static-content flow-name="xsl-region-after">
					<fo:block text-align="left" >Page <fo:page-number/> of <fo:page-number-citation ref-id="last-page"/></fo:block>
				</fo:static-content>
				
				<fo:flow flow-name="xsl-region-body">
				
					<fo:block>
						<xsl:call-template name="Quoteletter" />
						
						<xsl:if test="response/address_freeform_01/data/StateCode= 'PA'">
						  		<xsl:if test="response/ClaimExpensesTypeFlag= 'N'">
						  			<fo:block break-after="page" />							  									
									<xsl:call-template name="APL-State-PA-3" />	
								 </xsl:if>			
						</xsl:if>
						
						<!-- 
						<xsl:if test="response/address_freeform_01/data/StateCode= 'ND'">
						  		<xsl:if test="response/ClaimExpensesTypeFlag= 'N'">
						  			<fo:block break-after="page" />							  									
									<xsl:call-template name="ND-Defense-Outside" />	
								 </xsl:if>			
						</xsl:if>
						 -->						 						
				   </fo:block>				  
		
				  <fo:block id="last-page" font-size="11px"/>
				</fo:flow>
					
			</fo:page-sequence>
		
		</fo:root>
     </xsl:template>    
</xsl:stylesheet>
		    	