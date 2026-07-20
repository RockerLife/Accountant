<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet version="1.1"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:fo="http://www.w3.org/1999/XSL/Format"
	exclude-result-prefixes="fo"
	xmlns:fn="http://www.w3.org/2005/02/xpath-functions"
	xmlns:java="java">
	
	
	<xsl:template match="/" name="AAA04I004">
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
					               			 <fo:block font-weight="bold" font-size="10px"  text-align="left">Endorsement No: AAA-04-I004<!-- <xsl:value-of select="response/endorsements_freeform_01/data/APL1082010"/> --> </fo:block>
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
				   <!-- <fo:block text-align="center" font-size="12px"  >THIS ENDORSEMENT CHANGES THE POLICY, READ IT CAREFULLY.</fo:block>
				    <fo:block margin-top="10mm"/>
				    <fo:block >
					<fo:table  text-align="center">
					    <fo:table-column column-width = "90mm" />
				        <fo:table-column column-width = "90mm" />
				       
				        <fo:table-body>
					    		  <fo:table-row>
					                    <fo:table-cell padding-left="4pt"><fo:block font-weight="bold" font-size="10px"  text-align="left">To be attached to and form part of Policy No: <xsl:value-of select="response/policy_freeform_01/data/PolicyNumber" /></fo:block></fo:table-cell>
					                    <fo:table-cell  padding-left="4pt"><fo:block font-weight="bold" font-size="10px"  text-align="left">Effective Date of Endorsement: <xsl:value-of select="response/policy_freeform_01/data/PolicyEffectiveDate" /></fo:block></fo:table-cell>
					    		  </fo:table-row>
					    		 
					    		  <fo:table-row>
					               		<fo:table-cell padding-left="4pt" padding-top="4pt"><fo:block font-weight="bold" font-size="10px"  text-align="left">Issued to: <xsl:value-of select="response/policy_freeform_01/data/AccountName" /></fo:block></fo:table-cell>
					               		<fo:table-cell padding-left="4pt"  padding-top="4pt">
					               			 <fo:block font-weight="bold" font-size="10px"  text-align="left">Endorsement No: <xsl:value-of select="response/endorsements_freeform_01/data/LPL1020910" /></fo:block>
					               		</fo:table-cell>
					    		  </fo:table-row>
					    		 
					     </fo:table-body>
				     </fo:table>	   
				</fo:block> 
					
				    <fo:block space-after=".1in" text-align-last="justify">
				    	<fo:leader leader-pattern="rule" />
				   	</fo:block>-->
				   	
				     <fo:block margin-top="8mm"/>
				     <fo:block text-align="center" font-weight="bold" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" font-size="12px" >DEFENSE EXPENSES IN ADDITION TO THE LIMIT OF LIABILITY</fo:block>
				     <fo:block margin-top="8mm"/>
				    
				     <fo:block text-align="left"  font-size="10px" >This endorsement amends the limit of liability so that <fo:inline font-weight="bold" >Defense Expenses</fo:inline> are paid in addition to and equal to the limit of liability available to pay <fo:inline font-weight="bold" >Damages</fo:inline>.  Thereafter, <fo:inline font-weight="bold" >Defense Expenses</fo:inline> are part of and reduce the limit of liability to pay <fo:inline font-weight="bold" >Damages</fo:inline>.</fo:block>
				     <fo:block margin-top="4mm"/>
				   
				     
				     <!-- <xsl:choose>
				     	<xsl:when test="response/PremiumCharged= 'Y'">
                    	 <fo:block start-indent="7mm">
					     <fo:block text-align="left" font-size="10px" >
					     <fo:external-graphic src="../AccountantIns/image/check-btn4.png" content-height="1em" content-width="1em"/>  an additional premium of <xsl:value-of select="response/PremiumValue"/></fo:block>
					     <fo:block margin-top="4mm"/>
					     <fo:block text-align="left" font-size="10px" >
					     <fo:external-graphic src="../AccountantIns/image/check-btn4.png" content-height="1em" content-width="1em"/>  the premium charged</fo:block>
					     </fo:block>
                      	</xsl:when>
                      	<xsl:when test="response/PremiumCharged= 'N'">
                         <fo:block start-indent="7mm">					     
					     <fo:block text-align="left" font-size="10px" >
					     <fo:external-graphic src="../AccountantIns/image/check-btn4.png" content-height="1em" content-width="1em"/>  a return premium of <xsl:value-of select="response/PremiumValue"/></fo:block>
					     <fo:block margin-top="4mm"/>
					     <fo:block text-align="left" font-size="10px" >
					     <fo:external-graphic src="../AccountantIns/image/check-btn4.png" content-height="1em" content-width="1em"/>  the premium charged</fo:block>
					     </fo:block>
                      </xsl:when>
                      <xsl:when test="response/PremiumCharged= 'S'">
                        <fo:block start-indent="7mm">
					     <fo:block text-align="left" font-size="10px" >
					     <fo:external-graphic src="../AccountantIns/image/check-btn4.png" content-height="1em" content-width="1em"/>  an additional premium of $_____</fo:block>
					     <fo:block margin-top="4mm"/>
					     <fo:block text-align="left" font-size="10px" >
					     <fo:external-graphic src="../AccountantIns/image/check-btn4.png" content-height="1em" content-width="1em"/>  the premium charged</fo:block>
					     </fo:block>
                      </xsl:when>     
                      	<xsl:otherwise>
                         <fo:block start-indent="7mm">
					     <fo:block text-align="left" font-size="10px" >
					     <fo:external-graphic src="../AccountantIns/image/check-btn4.png" content-height="1em" content-width="1em"/>  an additional premium of $_____</fo:block>
					     <fo:block margin-top="4mm"/>
					     <fo:block text-align="left" font-size="10px" >
					     <fo:external-graphic src="../AccountantIns/image/check-btn4.png" content-height="1em" content-width="1em"/>  the premium charged</fo:block>
					     </fo:block>
                      </xsl:otherwise>
                     </xsl:choose> -->
                     				    
				     <fo:block margin-top="4mm"/>
				     <fo:block text-align="left" font-size="10px" >In consideration of the premium charged it is agreed that:</fo:block>
				     <fo:block margin-top="4mm"/>
				     
				    <fo:block text-align="left" font-size="10px" space-after="3mm" >1. The last sentence of the Preamble appearing above Item 1 of the Declarations is deleted.</fo:block>
				     
				     <fo:block text-align="left" font-size="10px" >2. Item 3 of the Declarations, Limit of Liability, is deleted and amended to read:</fo:block>
				     
				     
				        
                     <fo:block margin-top="5mm"/>
                     
                     <fo:table padding-left="5pt" border="1pt black">
						<!-- <fo:table-column column-width="10mm" /> -->
						<fo:table-column column-width="150mm" />				
						<fo:table-body>
							<fo:table-row>
								<!-- <fo:table-cell 	padding-bottom="4mm" padding-left="4pt">
									<fo:block font-size="10px" text-align="left">Item3.</fo:block>
									<fo:block></fo:block>
								</fo:table-cell> -->
								<fo:table-cell border="1pt solid black"	padding-bottom="4mm" padding-left="4pt">
									<fo:block text-align="left" font-size="10px" ><xsl:value-of select="response/policycoverage_freeform_01/data/AggregateLimitText"/>     &#160;&#160;&#160;&#160;&#160;&#160;      <fo:inline font-weight="bold" >Damages</fo:inline>, each <fo:inline font-weight="bold">Claim;</fo:inline></fo:block>
								    <fo:block text-align="left" font-size="10px" ><xsl:value-of select="response/policycoverage_freeform_01/data/AggregateLimitText"/>     &#160;&#160;&#160;&#160;&#160;&#160;      <fo:inline font-weight="bold" >Defense Expenses</fo:inline>, each <fo:inline font-weight="bold">Claim;</fo:inline></fo:block>
								    <fo:block margin-top="4mm"/>
								    <fo:block text-align="left" font-size="10px" ><xsl:value-of select="response/policycoverage_freeform_01/data/OccuranceLimitText"/>     &#160;&#160;&#160;&#160;&#160;&#160;      <fo:inline font-weight="bold" >Damages Policy Aggregate;</fo:inline></fo:block>
								    <fo:block text-align="left" font-size="10px" ><xsl:value-of select="response/policycoverage_freeform_01/data/OccuranceLimitText"/>     &#160;&#160;&#160;&#160;&#160;&#160;      <fo:inline font-weight="bold" >Defense Expenses Aggregate.</fo:inline></fo:block>
				      		</fo:table-cell>
							</fo:table-row>
						</fo:table-body>	
					</fo:table>
					
				        <fo:block margin-top="4mm"/>
				        <fo:block text-align="left" font-size="10px" >3.Section II of the Policy, Limits of Liability and Deductible, Paragraph A, Limits of Liability, is amended as follows: </fo:block> 
				        <!-- <fo:block text-align="left" font-size="10px" >&#160;&#160;&#160;&#160;the following:</fo:block> -->
				        <fo:block margin-top="4mm"/>
				        
				     <!--  <fo:block text-align="left" font-size="10px" text-indent="0.4cm" font-weight="bold" >A. Limits of Liability</fo:block> -->
				       <fo:block margin-top="2mm"/>
				 	   <fo:block text-align="justify" font-size="10px" start-indent="1cm">- the word "combined" is deleted where it appears in 1. and 2.;</fo:block>
		     			<fo:block text-align="justify" font-size="10px" start-indent="1cm">- 3. is deleted in its entirely;</fo:block>
				        <fo:block margin-top="4mm"/>
				       <fo:block text-align="left" font-size="10px" >4.The following is added to Section II of the policy, Limits of Liability and Deductible, paragraph A. Limits of Liability: </fo:block>
				       <fo:block margin-top="2mm"/>
				 	   <fo:block text-align="justify" font-size="10px" start-indent="1cm">
				 	  
				 	   In the event the amount shown above for <fo:inline font-weight="bold" font-style="italic">Defense Expenses</fo:inline>, each <fo:inline font-weight="bold" font-style="italic">Claim</fo:inline> is exhausted,
				 	    then <fo:inline font-weight="bold" font-style="italic">DefenseExpenses</fo:inline> incurred thereafter, shall be part of and
				 	    reduce the Limit of Liability as shown for <fo:inline font-weight="bold" font-style="italic">Damages</fo:inline>, each <fo:inline font-weight="bold" font-style="italic">Claim</fo:inline>.
				 	     In the event the amount shown above for <fo:inline font-weight="bold" font-style="italic">Defense Expenses Policy Aggregate</fo:inline> is exhausted, 
				 	    then Defense Expenses incurred thereafter, shall be part of and reduce the Limit of Liability as shown for <fo:inline font-weight="bold" font-style="italic">Damages Policy Aggregate</fo:inline>.
				 	   </fo:block>
		     			  
	         
	         <fo:block space-after="2cm"/>
				 <fo:block text-align="left" font-size="10px" >All other terms, conditions and limitations of the policy remain unaltered.</fo:block>
				 <fo:block space-after="2cm"/>
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
				  
					<fo:block margin-top="20mm"/>
	       	  <fo:block  font-size="10px" color="grey" text-align="left">ISMIE AAA-04-I004 (04012022)
	       	 		<fo:block margin-top="-0.5cm" font-size="10px" color="grey" text-align="right">Page 1 of 1</fo:block>
	       	 	</fo:block>	  	  		
            	  <!-- <fo:block font-size="10px" color="grey" text-align="right">page 2 of 2</fo:block> -->	       
	         	<fo:block  font-size="10px" color="grey" text-align="left"><xsl:value-of select="response/policyformfooter_freeform_01/data/LPL1020417"/></fo:block>
				 
     </xsl:template>
</xsl:stylesheet>




					
				    	