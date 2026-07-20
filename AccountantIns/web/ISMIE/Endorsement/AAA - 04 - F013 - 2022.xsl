<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="1.1"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:fo="http://www.w3.org/1999/XSL/Format"
	exclude-result-prefixes="fo"
	xmlns:fn="http://www.w3.org/2005/02/xpath-functions"
	xmlns:java="java">
  
  <xsl:template match="/" name="AAA04F013">
          	<fo:block text-align="center" color="grey" font-size="12px">THIS ENDORSEMENT CHANGES THE POLICY, READ IT CAREFULLY.</fo:block>
				    <fo:block margin-top="10mm"/>
				      <fo:block >
					<fo:table  text-align="center">
					    <fo:table-column column-width = "90mm" />
				        <fo:table-column column-width = "90mm" />
				       
				        <fo:table-body>
					    		  <fo:table-row>
					                    <fo:table-cell padding-left="4pt"><fo:block font-weight="bold"  font-size="10px"  text-align="left">To be attached to and form part of Policy No:<xsl:value-of select="response/policy_freeform_01/data/PolicyNumber" /></fo:block></fo:table-cell>
					                    <fo:table-cell  padding-left="4pt"><fo:block font-weight="bold" font-size="10px"  text-align="left">Effective Date of Endorsement:<xsl:value-of select="response/PolicyEndorsementEffectiveDate" /></fo:block></fo:table-cell>
					    		  </fo:table-row>
					    		 
					    		  <fo:table-row> 
					               		<fo:table-cell padding-left="4pt" padding-top="4pt"><fo:block font-weight="bold" font-size="10px"  text-align="left">Issued to: <xsl:value-of select="response/policy_freeform_01/data/AccountName" /></fo:block></fo:table-cell>
					               		<fo:table-cell padding-left="4pt"  padding-top="4pt">
					               			 <fo:block font-weight="bold" font-size="10px"  text-align="left">Endorsement No: AAA-04-F013<!-- <xsl:value-of select="response/endorsements_freeform_01/data/APL1082010"/> --></fo:block>
					               		</fo:table-cell>
					    		  </fo:table-row>
					    		 
					     </fo:table-body>
				     </fo:table>	   
				</fo:block>
				<fo:block space-after=".1in" text-align-last="justify">
    				<fo:leader leader-pattern="rule"/>
   				</fo:block>
   				
          <fo:block text-align="center" font-size="12px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" space-before="5mm" space-after="7mm" font-weight="bold">

      EFFECT THE EXTENDED REPORTING PERIOD PER PARAGRAPH D. 2. OF SECTION III. EXTENSIONS
   </fo:block>
          <fo:block padding-left="5pt" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" font-size="10px" text-align="justify">
          <fo:block space-after="12cm">
          <fo:list-block>
                <fo:list-item space-after="4mm">
                  <fo:list-item-label>
                    <fo:block>
                      
                    </fo:block>
                  </fo:list-item-label>
                  <fo:list-item-body start-indent="4mm">
                 <fo:block space-after="7mm" >
					In consideration of an additional premium of <xsl:value-of select="response/endorsementPremium" /> being  <xsl:value-of select="response/practicePortionPercentage" />% of the annual premium of this policy, the Extended Reporting Period as detailed in paragraph D. 2. of Section III of this policy is in full force and effect for a period of  <xsl:value-of select="response/erpMonths" /> months, commencing on <xsl:value-of select="response/PolicyEndorsementEffectiveDate" />.   
                  </fo:block>
                  
                   <xsl:choose>
	                    	<xsl:when test="response/isThereStateTax='Y'">
		                    	 <fo:block><fo:inline font-weight="bold">[If applicable]</fo:inline></fo:block>
		                    	 <fo:block>Additional Tax: $ <xsl:value-of select="response/ERPTax" /></fo:block>
						<fo:block>Total Due under this endorsement: $ <xsl:value-of select="response/ERPTotalPremium" /></fo:block>
						  	</xsl:when>
		            </xsl:choose>
		            </fo:list-item-body>
                  </fo:list-item> 
                  <!-- 
                   <fo:block>
                 <fo:inline font-weight="bold">[If applicable]</fo:inline></fo:block>
					<fo:block>Additional Tax: $_______(XXXX%)</fo:block>
					<fo:block>Total Due under this endorsement:  $____________ </fo:block>
                    </fo:list-item-body>
                  </fo:list-item> -->
                  
                  <!-- <fo:list-item space-after="2mm">
                  <fo:list-item-label start-indent="7mm">
                  <xsl:choose>
                      <xsl:when test="response/isUnlimitedYearsERP='N'">
                        <fo:block>
                          <fo:inline font-family="ZapfDingbats" font-size="8pt"
                            border="1pt black solid">&#x2714;</fo:inline> &#160;
                        </fo:block>
                      </xsl:when>
                      <xsl:otherwise>
                        <fo:block>
                          <fo:inline font-family="ZapfDingbats" font-size="8pt"
                            border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
                        </fo:block>
                      </xsl:otherwise>
                      </xsl:choose>
                  </fo:list-item-label>
                  <fo:list-item-body start-indent="14mm">
                  <fo:block>
                  from <xsl:value-of select="response/ERPEffective" /> to <xsl:value-of select="response/ErpExpirationDate" /> or      
                  </fo:block>
                  </fo:list-item-body>
                  </fo:list-item> -->

                  <!-- <fo:list-item space-after="4mm">
                  <fo:list-item-label start-indent="7mm">
                  <xsl:choose>
                       <xsl:when test="response/isUnlimitedYearsERP='Y'">
                        <fo:block>
                          <fo:inline font-family="ZapfDingbats" font-size="8pt"
                            border="1pt black solid">&#x2714;</fo:inline> &#160;
                        </fo:block>
                      </xsl:when>
                      <xsl:otherwise>
                        <fo:block>
                          <fo:inline font-family="ZapfDingbats" font-size="8pt"
                            border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
                        </fo:block>
                      </xsl:otherwise>
                      </xsl:choose>
                  </fo:list-item-label>
                  <fo:list-item-body start-indent="14mm">
                  <fo:block>
                  for an unlimited period.       
                  </fo:block>
                  </fo:list-item-body>
                  </fo:list-item> -->
                  
                  </fo:list-block>
                  
                  </fo:block>
                  
                  <fo:block start-indent="4mm" space-after="1cm">
                  All other terms, conditions and limitations of the policy remain unaltered.
                  </fo:block>
             
                  <fo:block space-after="1cm">
                  <fo:table>
					<fo:table-column column-width="80mm"/>
					<fo:table-column column-width="40mm"/>
					<fo:table-column column-width="60mm"/>
					<fo:table-body>
						<fo:table-row>
						<fo:table-cell>
						
						 <xsl:choose>
	                    	<xsl:when test="response/isShowSignature='Y'">
		                    	<fo:block font-size="9px" color="black" border-bottom-width="0.9pt" border-bottom-style="solid" border-bottom-color="black" text-align="center">
									<fo:external-graphic src="../AccountantIns/image/kyle.png" content-height="10em" content-width="8em"/>
								</fo:block> 
						  	</xsl:when>
		                  	<xsl:otherwise>
		                        <fo:block font-size="9px" color="black" border-bottom-width="0.9pt" border-bottom-style="solid" border-bottom-color="black" text-align="center">
									<fo:external-graphic src="../AccountantIns/image/BlankSingnature.png" content-height="10em" content-width="8em"/>
								</fo:block>
		                    </xsl:otherwise>
                   		  </xsl:choose>
						<fo:block font-size="9px" color="black" text-align="center">
						&#160;&#160;Authorized Representative
						</fo:block>
						</fo:table-cell>
						<fo:table-cell>
						<fo:block> &#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160; </fo:block>
						</fo:table-cell>
						<fo:table-cell text-align="center">
						<fo:block>&#160;</fo:block>
						<fo:block>&#160;</fo:block>
						
						<fo:block font-size="9px" color="black"  border-bottom-style="solid" border-bottom-color="black" text-align="center">
						<xsl:value-of select="response/general_freeform_01/data/CurrentDate"/>
						</fo:block>
						<fo:block font-size="9px" color="black">
						Date
						</fo:block>
						</fo:table-cell>
						</fo:table-row>
						</fo:table-body>
					</fo:table>	
                  
                  </fo:block>
                   <fo:block margin-top="20mm"/>
	                <fo:block  font-size="10px" color="grey" text-align="left">ISMIE AAA-04-F013 (04012022)
		       	 		<fo:block margin-top="-0.5cm" font-size="10px" color="grey" text-align="right">Page 1 of 1</fo:block>
		       	 	</fo:block>	  
                  </fo:block>
            

         
  </xsl:template>
 
</xsl:stylesheet>
