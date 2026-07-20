<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet version="1.1"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:fo="http://www.w3.org/1999/XSL/Format"
	exclude-result-prefixes="fo"
	xmlns:fn="http://www.w3.org/2005/02/xpath-functions"
	xmlns:java="java">
	
	
	<xsl:template match="/" name="Policycoverletter36102">
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
					               			 <fo:block font-weight="bold" font-size="10px"  text-align="left">Endorsement No: APL-109 (12/09)<!-- <xsl:value-of select="response/endorsements_freeform_01/data/APL1082010"/> --></fo:block>
					               		</fo:table-cell>
					    		  </fo:table-row>
					    		 
					     </fo:table-body>
				     </fo:table>	   
				</fo:block>
				<fo:block space-after=".1in" text-align-last="justify">
    				<fo:leader leader-pattern="rule"/>
   				</fo:block>
   				
   				  <fo:block margin-top="8mm"/>
				     <fo:block text-align="center" font-weight="bold"  font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" font-size="12px"  >AMENDATORY ENDORSEMENT</fo:block>
				      		    
				     <fo:block margin-top="6mm"/>
				      <fo:block text-align="left" font-size="10px"  >In consideration of the premium charged, it is agreed that Endorsement #XXXX of this policy is hereby deleted, null and void.</fo:block>				     
				     <fo:block margin-top="3mm"/>
				    <fo:block color="red" font-size="10px" space-after="15cm">[Note:  The Effective Date of this endorsement would be the same as the Effective Date of the Policy.]</fo:block>
				    
				    <!--  <xsl:choose>
				     	<xsl:when test="response/PremiumCharged= 'Y'">
                    	 <fo:block margin-top="6mm"/>
					     <fo:block text-align="left" font-size="10px" >
					     <fo:external-graphic src="../AccountantIns/image/check-btn4.png" content-height="1em" content-width="1em"/>  
					     an additional premium of <xsl:value-of select="response/PremiumValue"/></fo:block>
					     <fo:block margin-top="6mm"/>
					     <fo:block text-align="left" font-size="10px" >
					     <fo:external-graphic src="../AccountantIns/image/check-btn4.png" content-height="1em" content-width="1em"/>  
					     premium charged </fo:block>
                      	</xsl:when>
                    	<xsl:when test="response/PremiumCharged= 'N'">
                         <fo:block margin-top="6mm"/>
					     <fo:block text-align="left" font-size="10px" >
					     <fo:external-graphic src="../AccountantIns/image/check-btn4.png" content-height="1em" content-width="1em"/>  
					     a return premium of <xsl:value-of select="response/PremiumValue"/></fo:block>
					     <fo:block margin-top="6mm"/>
					     <fo:block text-align="left" font-size="10px" >
					     <fo:external-graphic src="../AccountantIns/image/check-btn4.png" content-height="1em" content-width="1em"/>  
					     premium charged </fo:block>
                      </xsl:when>
                      <xsl:when test="response/PremiumCharged= 'S'">
                         <fo:block margin-top="6mm"/>
					     <fo:block text-align="left" font-size="10px" >
					     <fo:external-graphic src="../AccountantIns/image/check-btn4.png" content-height="1em" content-width="1em"/>  
					     a return premium of $</fo:block>
					     <fo:block margin-top="6mm"/>
					     <fo:block text-align="left" font-size="10px" >
					     <fo:external-graphic src="../AccountantIns/image/check-btn4.png" content-height="1em" content-width="1em"/>  
					     the premium charged </fo:block>
                      </xsl:when>                      
                      <xsl:otherwise>
                         <fo:block margin-top="6mm"/>
					     <fo:block text-align="left" font-size="10px" >
					     <fo:external-graphic src="../AccountantIns/image/check-btn4.png" content-height="1em" content-width="1em"/>  
					     a return premium of $</fo:block>
					     <fo:block margin-top="6mm"/>
					     <fo:block text-align="left" font-size="10px" >
					     <fo:external-graphic src="../AccountantIns/image/check-btn4.png" content-height="1em" content-width="1em"/>  
					     the premium charged </fo:block>
                      </xsl:otherwise>
                     </xsl:choose> -->
				     
				     <!-- <fo:block margin-top="10mm"/>
				     <fo:block text-align="left" font-size="10px" >it is agreed that Endorsement #LPL-102 (04/17) of this policy hereby deleted.</fo:block>
                     -->
                   
			        <fo:block text-align="left" font-size="10px" >All other terms, conditions and limitations of the policy remain unaltered.</fo:block>
				         
				    <fo:block margin-top="1cm"/>
			         <fo:block>
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
				
				  			       
	         	<fo:block margin-top="5mm"/>
	         	<fo:block  font-size="10px" color="grey" text-align="left">APL-109 (12/09)&#160;&#160;&#160;&#160;&#160;&#160; &#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;
		        &#160;&#160; &#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160; &#160;&#160;&#160;&#160;&#160;&#160;
		        &#160;&#160;&#160;&#160;&#160;&#160; &#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;
		        &#160;&#160; &#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160; &#160;&#160;&#160;&#160;&#160;&#160;
		        &#160;&#160;&#160;&#160;&#160;&#160; &#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;
		        &#160;&#160; &#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160; &#160;&#160;&#160;&#160;&#160;&#160;
		        &#160;&#160;&#160;&#160;&#160;&#160; &#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;<fo:inline text-align="right" width="50%">Page 1 of 1</fo:inline></fo:block>
	  
     </xsl:template>
</xsl:stylesheet>




					
				    	