<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="1.1"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:fo="http://www.w3.org/1999/XSL/Format"
	exclude-result-prefixes="fo"
	xmlns:fn="http://www.w3.org/2005/02/xpath-functions"
	xmlns:java="java">
  
  <xsl:template match="/" name="APL1472010">
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
					               			 <fo:block font-weight="bold" font-size="10px"  text-align="left">Endorsement No: APL-147 (01/19)<!-- <xsl:value-of select="response/endorsements_freeform_01/data/APL1082010"/> --></fo:block>
					               		</fo:table-cell>
					    		  </fo:table-row>
					    		 
					     </fo:table-body>
				     </fo:table>	   
				</fo:block>
				<fo:block space-after=".1in" text-align-last="justify">
    				<fo:leader leader-pattern="rule"/>
   				</fo:block>
   				
   				<fo:block margin-top="2mm">
					</fo:block>

          <fo:block text-align="center" font-size="12px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" space-before="5mm" space-after="7mm" font-weight="bold">

      DEATH OR DISABILITY EXTENDED REPORTING PERIOD ENDORSEMENT
   </fo:block>
          <fo:block padding-left="5pt" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" font-size="10px" text-align="justify">
          <fo:block>
          <fo:list-block>
               <!--  <fo:list-item space-after="4mm">
                  <fo:list-item-label>
                    <fo:block>
                      
                    </fo:block>
                  </fo:list-item-label>
                  <fo:list-item-body start-indent="4mm">
                 <fo:block space-after="4mm" >
               In consideration of: (check one box only)
                    </fo:block>
                  </fo:list-item-body>
                  </fo:list-item> -->
                  
                <!--   <fo:list-item space-after="2mm">
                  <fo:list-item-label start-indent="7mm">
                  <xsl:choose>
                       <xsl:when test="response/isThereAdditionalPremium='Y'">
                        <fo:block>
                          <fo:inline font-family="ZapfDingbats" font-size="8pt"
                            border="1pt black solid">&#x2714;</fo:inline> &#160;
                        </fo:block>
                      </xsl:when>
                      <xsl:when test="response/isThereAdditionalPremium='N'">
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
                  an additional premium of  <xsl:value-of select="response/endorsementPremium" />  
                  </fo:block>
                  </fo:list-item-body>
                  </fo:list-item> -->

               <!--    <fo:list-item space-after="4mm">
                  <fo:list-item-label start-indent="7mm">
                  <xsl:choose>
                      <xsl:when test="response/isThereAdditionalPremium='N'">
                        <fo:block>
                          <fo:inline font-family="ZapfDingbats" font-size="8pt"
                            border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline> &#160;
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
                  the premium charged        
                  </fo:block>
                  </fo:list-item-body>
                  </fo:list-item> -->
                  
                  <fo:list-item space-after="4mm">
                  <fo:list-item-label>
                    <fo:block>
                      
                    </fo:block>
                  </fo:list-item-label>
                  <fo:list-item-body start-indent="4mm">
                 <fo:block space-after="4mm" >
						In consideration of the premium charged, it is agreed that the Death or
						Disability Extended Reporting Period, as detailed in Section III.D. of
						the policy, shall be in effect for an unlimited period, starting on the
						date designated in Column B below, for the  <fo:inline font-weight="bold">Insured</fo:inline> designated in Column
						A below:
                    </fo:block>
                  </fo:list-item-body>
                  </fo:list-item>
                  </fo:list-block>
                  
                  <fo:block space-after="8mm">
                  <fo:table>
                  <fo:table-column column-width="95mm"/>
                  <fo:table-column column-width="95mm"/>
                  <fo:table-body>
                  <fo:table-row>
                  <fo:table-cell  padding-bottom="1mm" padding-top="1mm" text-align="center">
                  <fo:block>
                  A
                  </fo:block>
                  </fo:table-cell>
                  <fo:table-cell  padding-bottom="1mm" padding-top="1mm" text-align="center">
                  <fo:block>
                  B
                  </fo:block>
                  </fo:table-cell>
                  
                  </fo:table-row>
                  <fo:table-row>
                  <fo:table-cell  padding-bottom="1mm" padding-top="1mm" text-align="center">
                  <fo:block>
                  <fo:inline text-decoration="underline">
                  Name of <fo:inline font-weight="bold">Insured</fo:inline>
                  </fo:inline>
                  </fo:block>
                  </fo:table-cell>
                  <fo:table-cell  padding-bottom="1mm" padding-top="1mm" text-align="center">
                  <fo:block>
                  <fo:inline text-decoration="underline">
                  Date of Cessation of Practice of Law
                  </fo:inline>
                  </fo:block>
                  </fo:table-cell>
                  
                  </fo:table-row>
                  <fo:table-row>
                  <fo:table-cell  padding-bottom="1mm" padding-top="1mm" text-align="center">
                  <fo:block>&#160;<xsl:value-of select="response/AttorneyName" /></fo:block>
                  </fo:table-cell>
                  <fo:table-cell  padding-bottom="1mm" padding-top="1mm" text-align="center">
                  <fo:block>&#160;<xsl:value-of select="response/CeasedDate" /></fo:block>
                  </fo:table-cell>
                  
                  </fo:table-row>
                  <fo:table-row>
                  <fo:table-cell  padding-bottom="1mm" padding-top="1mm" text-align="center">
                  <fo:block>
                  &#160;
                  </fo:block>
                  </fo:table-cell>
                  <fo:table-cell  padding-bottom="1mm" padding-top="1mm" text-align="center">
                  <fo:block>
                  &#160;
                  </fo:block>
                  </fo:table-cell>
                  </fo:table-row>
                  </fo:table-body>
                  </fo:table>
                  </fo:block>
                  
                  </fo:block>
                  <fo:block start-indent="4mm" space-after="4mm">
					The <fo:inline font-weight="bold">Insurer’s</fo:inline> Limit of Liability for all <fo:inline font-weight="bold">Claims</fo:inline> first made against an
					<fo:inline font-weight="bold">Insured</fo:inline> during any Death or Disability Extended Reporting Period will be part of, and not in addition to, the Limit of Liability shown in Item 3. of the Declarations, regardless of the number of Death or Disability Extended Reporting Periods purchased.
                  </fo:block>
                  <fo:block start-indent="4mm" space-after="8mm">
						If the Death or Disability Extended Reporting Period herein applies to a
						<fo:inline font-weight="bold">Claim</fo:inline>, Section VI.G., Other Insurance, shall not apply to such <fo:inline font-weight="bold">Claim</fo:inline>.
						If any other policy of insurance in effect would apply to any <fo:inline font-weight="bold">Claim</fo:inline>
						first made against an <fo:inline font-weight="bold">Insured</fo:inline> during the Death or Disability Extended
						Reporting Period, then coverage provided under this policy during any
						Death or Disability Extended Reporting Period shall not apply. Such
						other insurance shall render this Death or Disability Extended
						Reporting Period inapplicable, even though the limit of liability of
						such other insurance may be inadequate to pay all <fo:inline font-weight="bold">Damages</fo:inline> and <fo:inline font-weight="bold">Defense
						Expenses</fo:inline> or the Deductible amount and retention provisions of such
						other insurance may be different from those of this policy.
					</fo:block>
                  <fo:block start-indent="4mm">
                  All other terms, conditions and limitations of the policy remain unaltered.
                  </fo:block>
                  <fo:block margin-top="2cm"/>
                  <fo:block space-after="6.5cm">
                 
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
						<fo:block>
						&#160;
						</fo:block>
						<fo:block>
						</fo:block>
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
                 <fo:block font-size="9px" color="grey" text-align="left">
						APL-147 (01/19)&#160; &#160; &#160; &#160; &#160; &#160; &#160;
						&#160; &#160; &#160; &#160; &#160; &#160; &#160; &#160; &#160;
						&#160;&#160; &#160; &#160; &#160; &#160; &#160; &#160;&#160;&#160; &#160;&#160; &#160;
						&#160; &#160; &#160; &#160; &#160; &#160; &#160; &#160; &#160; &#160; &#160; &#160;
						&#160;&#160;&#160; &#160;&#160; &#160;&#160;&#160;&#160; &#160;&#160;&#160;&#160; &#160;&#160;&#160; &#160;&#160; &#160;
						&#160; &#160; &#160; &#160; &#160; &#160; &#160;&#160;&#160; &#160;&#160; &#160;
						&#160; &#160; &#160; &#160; &#160; &#160; &#160; &#160; &#160;&#160;
						&#160;&#160; &#160;&#160; &#160; &#160; &#160; &#160;&#160; &#160;&#160;&#160;&#160; &#160;&#160; &#160; Page 1 of 1
					</fo:block>
                  </fo:block>
            

         
  </xsl:template>
 
</xsl:stylesheet>
