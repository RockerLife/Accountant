<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.1"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:fo="http://www.w3.org/1999/XSL/Format"
	exclude-result-prefixes="fo"
	xmlns:fn="http://www.w3.org/2005/02/xpath-functions"
	xmlns:java="java">

	<xsl:template match="/" name="APL-OH-EX" >
	
		
				    <fo:block text-align="center" font-size="12px" >THIS ENDORSEMENT CHANGES THE POLICY, READ IT CAREFULLY.</fo:block>
				    <fo:block margin-top="10mm"/>
				    
				    <fo:block >
					<fo:table  text-align="center">
					    <fo:table-column column-width = "90mm" />
				        <fo:table-column column-width = "90mm" />
				       
				        <fo:table-body>
					    		  <fo:table-row>
					                    <fo:table-cell padding-left="4pt"><fo:block font-weight="bold" font-size="10px"  text-align="left">To be attached to and form part of Policy No: <xsl:value-of select="response/policy_freeform_01/data/PolicyNumber" /></fo:block></fo:table-cell>
					                    <fo:table-cell padding-left="4pt"><fo:block font-weight="bold"  font-size="10px"  text-align="left">Effective Date of Endorsement: <xsl:value-of select="response/policy_freeform_01/data/PolicyEffectiveDate" /></fo:block></fo:table-cell>
					    		  </fo:table-row>
					    		 
					    		  <fo:table-row>
					               		<fo:table-cell padding-left="4pt" padding-top="4pt"><fo:block font-weight="bold" font-size="10px"  text-align="left">Issued to: <xsl:value-of select="response/policy_freeform_01/data/AccountName" /></fo:block></fo:table-cell>
					               		<fo:table-cell padding-left="4pt"  padding-top="4pt">
					               			 <fo:block font-weight="bold" font-size="10px"  text-align="left">Endorsement No: OH EXCEPTION PAGE - APL- 2010</fo:block>
					               		</fo:table-cell>
					    		  </fo:table-row>
					    		 
					     </fo:table-body>
				     </fo:table>	   
				</fo:block>
				    
   <fo:block space-after=".1in" text-align-last="justify">
    <fo:leader leader-pattern="rule" />
   </fo:block>
				   
				     <fo:block margin-top="12mm"/>
				     <fo:block text-align="center" font-weight="bold" font-size="10px"  > STATE OF OHIO EXCEPTION PAGE </fo:block>
				      <fo:block text-align="center" font-weight="bold" font-size="10px"  > ACCOUNTANTS PROFESIONAL LIABILITY </fo:block>
				      <fo:block margin-top="8mm"/>
				     
				     <fo:block text-align="left" font-size="10px" >Ohio does not permit past risk characteristics to be used for experience rating.</fo:block>
				     <fo:block margin-top="4mm"/>
				      <fo:block text-align="left" font-size="10px" ><fo:inline font-weight="bold" font-style="italic"> NEW RULE: </fo:inline>Chart G. of the rating methodology is amended so that I. Claims Experience, is deleted in its entirety from this rating step, and shall not be considered.</fo:block>
				      
				     <fo:block margin-top="8mm"/>
				     <fo:block text-align="left" font-size="10px" >Ohio does permit, however, Claims Experience to be used for Rate Modification purposes.</fo:block>
				    
				     <fo:block margin-top="4mm"/>
				     <fo:block text-align="left" font-size="10px" ><fo:inline font-weight="bold" font-style="italic"> NEW RULE: </fo:inline>The base rate in OHIO as shown in the filing memorandum should first be modified in accordance with the chart below, prior to performing Steps 1 through 7.</fo:block>
				     
				     
				     <fo:block margin-top="15mm"/>
				     <fo:block>
				     <fo:table  text-align="center" border="2pt solid black" >
					    <fo:table-column column-width = "100mm" />
				        <fo:table-column column-width = "40mm" />
						<fo:table-column column-width = "40mm" />

					<fo:table-body>
						 <fo:table-row  border="1pt solid black" padding-bottom="2mm">
						   <fo:table-cell border="0.5pt solid black" padding-left="3pt"><fo:block font-size="10px"  text-align="left" text-decoration="underline"> Item </fo:block></fo:table-cell>
						  <fo:table-cell border="0.5pt solid black" padding-left="3pt"><fo:block font-size="10px"  text-align="left" text-decoration="underline"> Credit </fo:block></fo:table-cell>
						  <fo:table-cell border="0.5pt solid black" padding-left="3pt"><fo:block font-size="10px"  text-align="left" text-decoration="underline"> Debit </fo:block></fo:table-cell>
						  </fo:table-row>
						  
						  <fo:table-row  border="1pt solid black" padding-bottom="2mm">
						   <fo:table-cell  padding-left="8pt"><fo:block font-size="10px"  text-align="left" >I. Claims Experience:</fo:block></fo:table-cell>
						  <fo:table-cell><fo:block></fo:block></fo:table-cell>
						  <fo:table-cell><fo:block></fo:block></fo:table-cell>
						  </fo:table-row>
						  
						   <fo:table-row border="1pt solid black" padding-bottom="2mm">
						   		<fo:table-cell border="0.5pt solid black"  padding-left="8pt">
						   			<fo:block>
						   				<fo:table >
						   					<fo:table-column></fo:table-column>						   					
						   					<fo:table-body>
						   					<fo:table-row>
							   						<fo:table-cell padding-left="8pt">
											  		<fo:block text-align="left" font-size="10px" > None</fo:block>
											  		 </fo:table-cell>											  		
											 </fo:table-row>
											
											<fo:table-row>
							   						<fo:table-cell padding-left="8pt">
											  		<fo:block text-align="left" font-size="10px" > Minimal</fo:block>
											  		 </fo:table-cell>											  		
											 </fo:table-row>
											
											  	<fo:table-row>
							   						<fo:table-cell padding-left="8pt">
											  		<fo:block text-align="left" font-size="10px" > Cautionary</fo:block>
											  		 </fo:table-cell>											  		
											   </fo:table-row>
											   
											   <fo:table-row>
							   						<fo:table-cell padding-left="8pt">
											  		<fo:block text-align="left" font-size="10px" > Material</fo:block>
											  		 </fo:table-cell>											  		
											 </fo:table-row>
						   					</fo:table-body>
						   				</fo:table>
						   			</fo:block>
						   		</fo:table-cell>
						   		
						   		<fo:table-cell border="0.5pt solid black" padding-left="8pt">
						   			<fo:block>
						   				<fo:table>
						   					<fo:table-column></fo:table-column>						   					
						   					<fo:table-body>
						   					<fo:table-row>
							   						<fo:table-cell padding-left="8pt">
											  		<fo:block text-align="left" font-size="10px" >.10-.25</fo:block>
											  		 </fo:table-cell>											  		
											 </fo:table-row>
											
											<fo:table-row>
							   						<fo:table-cell padding-left="8pt">
											  		<fo:block text-align="left" font-size="10px" >0-.10</fo:block>
											  		 </fo:table-cell>											  		
											 </fo:table-row>
											
											  	<fo:table-row>
							   						<fo:table-cell padding-left="8pt">
											  		<fo:block text-align="left" font-size="10px" ></fo:block>
											  		 </fo:table-cell>											  		
											   </fo:table-row>
											   
											   <fo:table-row>
							   						<fo:table-cell padding-left="8pt">
											  		<fo:block text-align="left" font-size="10px" ></fo:block>
											  		 </fo:table-cell>											  		
											 </fo:table-row>
						   					</fo:table-body>
						   				</fo:table>
						   			</fo:block>
						   		</fo:table-cell>	   
						   		
						   		<fo:table-cell border="0.5pt solid black"  padding-left="8pt">
						   			<fo:block>
						   				<fo:table>
						   					<fo:table-column></fo:table-column>						   					
						   					<fo:table-body>
						   					<fo:table-row>
							   					<fo:table-cell><fo:block>&#160;  </fo:block></fo:table-cell>											  		
											 </fo:table-row>
											
											<fo:table-row>
							   						<fo:table-cell><fo:block> &#160; </fo:block></fo:table-cell>										  		
											 </fo:table-row>
											
											  	<fo:table-row>
							   						<fo:table-cell padding-left="8pt">
											  		<fo:block text-align="left" font-size="10px" >0-.10</fo:block>
											  		 </fo:table-cell>											  		
											   </fo:table-row>
											   
											   <fo:table-row>
							   						<fo:table-cell padding-left="8pt">
											  		<fo:block text-align="left" font-size="10px" >.10-.25</fo:block>
											  		 </fo:table-cell>											  		
											 </fo:table-row>
						   					</fo:table-body>
						   				</fo:table>
						   			</fo:block>
						   		</fo:table-cell>
						   		
						   		
						   		
						   			   
  						  </fo:table-row>
  						  
  						  <fo:table-row border="1pt solid black" padding-bottom="2mm">
  						  		<fo:table-cell padding-left="8pt">
  						  			<fo:block text-align="left" font-size="10px" start-indent= "1cm" >None = No claims in past 5 years</fo:block>
						 			<fo:block text-align="left" font-size="10px" start-indent= "1cm">Minimal =  Previous claims, but likelihood of payment is low</fo:block>
						  		 	<fo:block text-align="left" font-size="10px" start-indent= "1cm">Cautionary = Previous claims with payouts or reserves 10% of policy limit</fo:block>
						 			<fo:block text-align="left" font-size="10px" start-indent= "1cm">Material = Previous claim payments or reserves > 10% of policy limit</fo:block>
  						  		</fo:table-cell>
  						  		<fo:table-cell><fo:block></fo:block></fo:table-cell>
  						  		<fo:table-cell><fo:block></fo:block></fo:table-cell>
  						   </fo:table-row>
					</fo:table-body>

				        </fo:table> 
				     </fo:block>
				     
				     
				       <fo:block margin-top="100mm"/>
			  <fo:block  font-size="10px"  text-indent="2cm" color="grey" text-align="left">OH EXCEPTION PAGE - APL- 2010</fo:block>
			 	 
     </xsl:template>
</xsl:stylesheet>
