<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.1"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:fo="http://www.w3.org/1999/XSL/Format"
	exclude-result-prefixes="fo"
	xmlns:fn="http://www.w3.org/2005/02/xpath-functions"
	xmlns:java="java">

	<xsl:template match="/" name="APL-State-PA-3" >
	
	<fo:block margin-top="10mm"/>
	
				    <fo:block text-align="center" font-size="12px" ><fo:inline font-weight="bold" font-style="italic"> NOTICE TO PENNSYLVANIA POLICYHOLDERS</fo:inline></fo:block>
				       				 
				      <fo:block margin-top="10mm"/>
				     <fo:block text-align="center" font-weight="bold" font-size="10px" ><fo:inline font-weight="bold" font-style="italic">ACKNOWLEDGEMENT OF DEFENSE WITHIN LIMIT PROVISION</fo:inline></fo:block>
				     <fo:block margin-top="10mm"/>
				    
				
				     <fo:block text-align="left" font-size="10px" start-indent="2cm" ><fo:inline font-weight="bold" font-style="italic">I UNDERSTAND AND ACKNOWLEDGE THAT THE ATTACHED POLICY CONTAINS A DEFENSE WITHIN LIMITS PROVISION WHICH MEANS THAT DEFENSE EXPENSES WILL REDUCE MY LIMIT OF INSURANCE AND MAY EXHAUST THE LIMIT COMPLETELY. SHOULD THAT OCCUR, I SHALL BE LIABLE FOR ANY FURTHER LEGAL DEFENDE EXPENSES AND DAMAGES.</fo:inline></fo:block>
				     <fo:block margin-top="25mm"/>


				     <fo:table  text-align="right">
					    <fo:table-column column-width = "100mm" />
				        <fo:table-column column-width = "110mm" />
				         <fo:table-body>
					    		 
					    		  <fo:table-row>
					    		  		<fo:table-cell padding-left="4pt" padding-top="4pt">
					               			<fo:block  font-size="10px"  text-align="center">___________________________________</fo:block>
					               		</fo:table-cell>
					               		
					               		<fo:table-cell padding-left="4pt" padding-top="4pt">
					               			<fo:block  font-size="10px"  text-align="center">__________________________________</fo:block>
					               		</fo:table-cell>					               		
				      			 </fo:table-row>
				      			 
				      			  <fo:table-row>
					                    <fo:table-cell padding-left="4pt"><fo:block  font-size="10px"  text-align="center">Policyholder/Applicant Signature</fo:block></fo:table-cell>
					                    <fo:table-cell  padding-left="4pt"><fo:block   font-size="10px" text-align="center" >Insurance Company</fo:block></fo:table-cell>
					    		  </fo:table-row>
					    		  
					    		  <fo:table-row>
					    		  		<fo:table-cell><fo:block margin-top="15mm"/></fo:table-cell>
					    		    </fo:table-row>
					    		 
					    		  
					    		  
					    		  <fo:table-row>
					    		  		<fo:table-cell padding-left="4pt" padding-top="4pt">
					               			<fo:block  font-size="10px"  text-align="center">___________________________________</fo:block>
					               		</fo:table-cell>
					               		
					               		<fo:table-cell padding-left="4pt" padding-top="4pt">
					               			<fo:block  font-size="10px"  text-align="center">__________________________________</fo:block>
					               		</fo:table-cell>					               		
				      			 </fo:table-row>
				      			 
				      			  <fo:table-row>
					                    <fo:table-cell padding-left="4pt"><fo:block  font-size="10px"  text-align="center">Policyholder/Applicant Printed Name </fo:block></fo:table-cell>
					                    <fo:table-cell  padding-left="4pt"><fo:block   font-size="10px"  text-align="center">Policy Number</fo:block></fo:table-cell>
					    		  </fo:table-row>
					    		  
					    		  <fo:table-row>
					    		  		<fo:table-cell><fo:block margin-top="15mm"/></fo:table-cell>
					    		    </fo:table-row>
					    		   <fo:table-row>
					    		  		<fo:table-cell padding-left="4pt" padding-top="4pt">
					               			<fo:block  font-size="10px"  text-align="center">__________________________________</fo:block>
					               		</fo:table-cell>              		
					               					               		
				      			 </fo:table-row>
				      			 
				      			 <fo:table-row>
					                    <fo:table-cell padding-left="4pt"><fo:block  font-size="10px"  text-align="center">Date (MM/DD/YYYY)</fo:block></fo:table-cell>
		
					    		  </fo:table-row>				      			 
				        </fo:table-body>
				     </fo:table>
				  <xsl:if test="response/policyformfooter_freeform_01/data/FormInclude= 'Y'">
						<fo:block margin-top="115mm"/>			  
			   		    <fo:block  font-size="10px" text-indent="2cm" color="grey" text-align="left"><xsl:value-of select="response/policyformfooter_freeform_01/data/APLAMENDPA20093" /></fo:block>
		    		</xsl:if>
	
			
     </xsl:template>
</xsl:stylesheet>
