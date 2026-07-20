<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet version="1.1"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:fo="http://www.w3.org/1999/XSL/Format"
	exclude-result-prefixes="fo"
	xmlns:fn="http://www.w3.org/2005/02/xpath-functions"
	xmlns:java="java">
	
	<xsl:template match="/" name="exposureendorsement20190501">
		
				<fo:block text-align="left" margin-left="1in"
					margin-right="1.3in" font-size="11px" margin-top="1cm"
					font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;"
				  space-after="1cm" color="grey"><fo:inline font-weight="bold">THIS ENDORSEMENT CHANGES THE POLICY, READ IT CAREFULLY.</fo:inline>
				</fo:block>
		<fo:block  margin-left="0.3in" padding-left="5pt" margin-right="0.3in"
			font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;"
			margin-top="0.3in" font-size="10px" text-align="justify">
				   
				    <fo:block space-after="4mm">
					<fo:table  text-align="left">
					    <fo:table-column column-width = "90mm" />
				        <fo:table-column column-width = "90mm" />
				       
				        <fo:table-body>
					    		  <fo:table-row>
					    		         <fo:table-cell start-indent="1mm" padding-left="4pt"><fo:block font-weight="bold" font-size="10px"  text-align="left">To be attached to and form part of Policy No:<xsl:value-of select="response/policy_freeform_01/data/PolicyNumber" /></fo:block></fo:table-cell>
					                    <fo:table-cell  start-indent="1mm" padding-left="4pt"><fo:block font-weight="bold" font-size="10px"  text-align="left">Effective Date of Endorsement:<xsl:value-of select="response/policy_freeform_01/data/PolicyEffectiveDate" /></fo:block></fo:table-cell>
					    		  </fo:table-row>
					    		 
					    		  <fo:table-row>
					               		<fo:table-cell start-indent="1mm" padding-left="4pt" padding-top="4pt"><fo:block font-weight="bold" font-size="10px"  text-align="left">Issued to: <xsl:value-of select="response/policy_freeform_01/data/AccountName" /></fo:block></fo:table-cell>
					               		<fo:table-cell start-indent="1mm" padding-left="4pt"  padding-top="4pt">
					               			 <fo:block font-weight="bold" font-size="10px"  text-align="left">Endorsement No: <xsl:value-of select="response/endorsements_freeform_01/data/APL1242011" /></fo:block>
					               		</fo:table-cell>
					    		  </fo:table-row>
					    		 
					     </fo:table-body>
				     </fo:table>	   
				</fo:block>
				    <fo:block space-after=".1in" text-align-last="justify">
			    <fo:leader rule-thickness="2pt" leader-pattern="rule" />
			   </fo:block>
			   
			   
			   
			   <fo:block margin-top="12mm"/>
		     <fo:block start-indent="1.5cm" text-align="center" font-weight="bold" font-size="10px">CHANGES IN EXPOSURE ENDORSEMENT – FLORIDA, MONTANA, VERMONT</fo:block>
		     <fo:block margin-top="8mm"/>
		     
		     <fo:block>In consideration of the premium charged, it is agreed that if the Named Insured during the Policy Period:</fo:block>
			   
			   <fo:block>1. &#160; &#160;merges; or</fo:block>
               <fo:block>2.	&#160; &#160;consolidates;</fo:block>
               <fo:block>with another entity in a manner such that the Named Insured is not the surviving entity, coverage under this policy for Professional Services:</fo:block>
			   <fo:block start-indent="2cm">a. &#160; &#160;rendered; or</fo:block>
			   <fo:block start-indent="2cm">b. &#160; &#160;which should have been rendered;</fo:block>
			   
			   <fo:block>subsequent to the effective date of such transaction shall be excluded for the remainder of the Policy Period.  Coverage shall then apply to Professional Services:</fo:block>
			   <fo:block>1.	&#160; &#160;rendered; or</fo:block>
			   <fo:block>2.	&#160; &#160;which should have been rendered;</fo:block>
			   <fo:block>between:</fo:block>
			   <fo:block start-indent="2cm">a.	&#160;&#160;the Prior Acts Date stated in Item 6. of the Declarations; and</fo:block>
			   <fo:block start-indent="2cm">b.	&#160;&#160;the effective date of the transaction whereby the Named Insured is not the surviving entity;</fo:block>
			   
			   <fo:block space-after="1cm">subject to all the other terms, conditions and exclusions of this policy.</fo:block>
			   
			   <fo:block space-after="2cm">All other terms and conditions of the policy remain unchanged.</fo:block>
			   
			   
                  		<fo:table>
                          <fo:table-column column-width="80mm"/>
                          <fo:table-column column-width="40mm"/>
                          <fo:table-column column-width="60mm"/>
                          <fo:table-body>
                                          <fo:table-row>
                                          <fo:table-cell>
										  <fo:block>
                                          &#160;
                                          </fo:block>
                                          <fo:block font-size="9px" color="black" border-bottom-width="0.9pt" border-bottom-style="solid" border-bottom-color="black" text-align="center">
                                          &#160;
                                          </fo:block>
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
                                          <fo:block margin-top="3mm" font-size="9px" color="black"  border-bottom-style="solid" border-bottom-color="black" text-align="center">
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
				       
		       <fo:block margin-top="9cm" font-size="10px" color="grey" start-indent="17mm"
					text-align="left">APL-129 (11/18) FL, MT, VT &#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;
		           &#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;
		           &#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;
		           &#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;
		           &#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;
		           Page 1 of 1
				</fo:block>	
			
     </xsl:template>
</xsl:stylesheet>




					
				    	