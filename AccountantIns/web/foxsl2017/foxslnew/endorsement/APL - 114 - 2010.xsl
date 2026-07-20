<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet version="1.1"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:fo="http://www.w3.org/1999/XSL/Format"
	exclude-result-prefixes="fo"
	xmlns:fn="http://www.w3.org/2005/02/xpath-functions"
	xmlns:java="java">
	
	
	<xsl:template match="/" name="Policycoverletter14">
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
					               			 <fo:block font-weight="bold" font-size="10px"  text-align="left">Endorsement No: APL-114-2010<!-- <xsl:value-of select="response/endorsements_freeform_01/data/APL1082010"/> --></fo:block>
					               		</fo:table-cell>
					    		  </fo:table-row>
					    		 
					     </fo:table-body>
				     </fo:table>	   
				</fo:block>
				<fo:block space-after=".1in" text-align-last="justify">
    				<fo:leader leader-pattern="rule"/>
   				</fo:block>
				   				   	
				     <fo:block margin-top="6mm"/>
				     <fo:block text-align="center" font-weight="bold" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" font-size="12px"><fo:inline border-bottom="0.2pt solid black">&#160;CANCELLATION OF THE POLICY</fo:inline></fo:block>
				    <!--  <fo:block text-align="center" font-weight="bold" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" font-size="12px"  >(CHECK ONE BOX ONLY)</fo:block> -->
				     <fo:block margin-top="6mm"/>			    
				     
				     
				     <fo:block text-align="left" font-size="10px" >
				     <xsl:choose>
				         <xsl:when test="response/endorsement_freeform_01/data/CancellationReasonID = '1' or response/endorsement_freeform_01/data/CancellationReasonID = '2' or response/endorsement_freeform_01/data/CancellationReasonID = '3'">
				           <fo:external-graphic src="../AccountantIns/image//check-btn6.png" content-height="1em" content-width="1em"/>
				         </xsl:when>
				         <xsl:otherwise>
				          <fo:external-graphic src="../AccountantIns/image//check-btn4.png" content-height="1em" content-width="1em"/>
				         </xsl:otherwise>
				     </xsl:choose>				     
				       <fo:inline border-bottom="0.2pt solid black">&#160;CANCELLATION BY THE</fo:inline>  <fo:inline font-weight="bold" > NAMED INSURED</fo:inline>:
				     </fo:block>
				     <fo:block margin-top="4mm"/>
				     <fo:block text-align="left" text-indent="0.5cm" font-size="10px" >In consideration of a return premium of
					     <xsl:choose>
					         <xsl:when test="response/endorsement_freeform_01/data/CancellationReasonID = '1' or response/endorsement_freeform_01/data/CancellationReasonID = '2' or response/endorsement_freeform_01/data/CancellationReasonID = '3'">
					           <xsl:value-of select="response/endorsement_freeform_01/data/InvoicedPremium" /> 				     
					         </xsl:when>
					         <xsl:otherwise>
					          $______
					         </xsl:otherwise>
					     </xsl:choose>   
				     and in accordance with the <fo:inline font-weight="bold" >Named Insured's</fo:inline> request,</fo:block>
				     
				     <fo:block margin-top="6mm"/>
				     <fo:block text-align="left" font-size="10px" >
					     <xsl:choose>
					         <xsl:when test="response/endorsement_freeform_01/data/CancellationReasonID = '4'">
					           <fo:external-graphic src="../AccountantIns/image//check-btn6.png" content-height="1em" content-width="1em"/>
					         </xsl:when>
					         <xsl:otherwise>
					          <fo:external-graphic src="../AccountantIns/image//check-btn4.png" content-height="1em" content-width="1em"/>
					         </xsl:otherwise>
					     </xsl:choose>
				      <fo:inline border-bottom="0.2pt solid black">&#160;CANCELLATION BY THE</fo:inline><fo:inline font-weight="bold" > INSURER</fo:inline>:</fo:block>
				     <fo:block margin-top="4mm"/>
				     <fo:block text-align="left" text-indent="0.5cm" font-size="10px" >In consideration of a return premium of
					     <xsl:choose>
					         <xsl:when test="response/endorsement_freeform_01/data/CancellationReasonID = '4'">
					           <xsl:value-of select="response/endorsement_freeform_01/data/InvoicedPremium" /> 				     
					         </xsl:when>
					         <xsl:otherwise>
					         $______,
					         </xsl:otherwise>
					     </xsl:choose> 
				     </fo:block>
				    
				     
				     <fo:block margin-top="6mm"/>
				     <fo:block text-align="left" font-size="10px" >it is agreed that this policy is cancelled effective <xsl:value-of select="response/endorsement_freeform_01/data/TransactionEffectiveDate" />; thus, Item 2 of the Declarations is amended to read:</fo:block>
                     
                     <fo:block margin-top="4mm"/>
                     <fo:table border="2pt solid black" text-align="center">
					    <!-- <fo:table-column column-width = "20mm" /> -->
				        <fo:table-column column-width = "170mm" />				
						<fo:table-body>
							<fo:table-row>
			               		<!-- <fo:table-cell border="2pt solid black" padding-bottom="4mm" padding-left="4pt"><fo:block font-size="10px"   text-align="left">Item 2.</fo:block></fo:table-cell>
			               		 --><fo:table-cell border="2pt solid black" padding-bottom="4mm" padding-left="4pt">
			               			 <fo:block  font-size="10px"  start-indent="2cm" text-align="left" margin-top="2mm">Item 2. Policy Period: </fo:block> 
			               			  <fo:block start-indent="-4.7cm" margin-top="3mm" font-size="10px" > From  <xsl:value-of select="response/policy_freeform_01/data/PolicyEffectiveDate" />  (Inception Date)&#160;&#160;&#160;&#160;&#160;&#160; &#160;&#160;&#160;&#160;&#160;&#160; To  <xsl:value-of select="response/endorsement_freeform_01/data/TransactionEffectiveDate" />  (Expiration Date)  </fo:block>
			               			 <fo:block start-indent="2cm" font-size="10px"   text-align="left" >(Both dates 12:01 a.m. local time at the address shown in Item 1.)</fo:block>
			               		</fo:table-cell>
			    		  	</fo:table-row>
						</fo:table-body>	
					</fo:table>     
			        <fo:block margin-top="6mm"/>
			        <fo:block text-align="left" font-size="10px" >All other terms, conditions and limitations of the policy remain unaltered.</fo:block>
				         
				         
			          <fo:block margin-top="100mm"/>
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
				
				  			       
	         	<fo:block margin-top="1cm"/>
	         	<fo:block  font-size="10px" color="grey" text-align="left">APL-114-2010&#160;&#160;&#160;&#160;&#160;&#160; &#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;
		        &#160;&#160; &#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160; &#160;&#160;&#160;&#160;&#160;&#160;
		        &#160;&#160;&#160;&#160;&#160;&#160; &#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;
		        &#160;&#160; &#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160; &#160;&#160;&#160;&#160;&#160;&#160;
		        &#160;&#160;&#160;&#160;&#160;&#160; &#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;
		        &#160;&#160; &#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160; &#160;&#160;&#160;&#160;&#160;&#160;
		        &#160;&#160;&#160;&#160;&#160;&#160; &#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;<fo:inline text-align="right" width="50%">Page 1 of 1</fo:inline></fo:block>
	         	  
     </xsl:template>
</xsl:stylesheet>




					
				    	