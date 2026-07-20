<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet version="1.1"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:fo="http://www.w3.org/1999/XSL/Format"
	exclude-result-prefixes="fo"
	xmlns:fn="http://www.w3.org/2005/02/xpath-functions"
	xmlns:java="java">
	
	<xsl:template match="/" name="exposureendorsement">
		
				    <fo:block text-align="center" font-size="12px"></fo:block>
				    <fo:block margin-top="4mm"/>
				    
				    <fo:block>
					<fo:table  text-align="center">
					    <fo:table-column column-width = "90mm" />
				        <fo:table-column column-width = "90mm" />
				       
				        <fo:table-body>
					    		  <fo:table-row>
					                    <fo:table-cell padding-left="4pt"><fo:block font-weight="bold" font-size="10px"  text-align="left">To be attached to and form part of Policy No:<xsl:value-of select="response/policy_freeform_01/data/PolicyNumber" /></fo:block></fo:table-cell>
					                    <fo:table-cell  padding-left="4pt"><fo:block font-weight="bold" font-size="10px"  text-align="left">Effective Date of Endorsement:<xsl:value-of select="response/policy_freeform_01/data/PolicyEffectiveDate" /></fo:block></fo:table-cell>
					    		  </fo:table-row>
					    		 
					    		  <fo:table-row>
					               		<fo:table-cell padding-left="4pt" padding-top="4pt"><fo:block font-weight="bold" font-size="10px"  text-align="left">Issued to: <xsl:value-of select="response/policy_freeform_01/data/AccountName" /></fo:block></fo:table-cell>
					               		<fo:table-cell padding-left="4pt"  padding-top="4pt">
					               			 <fo:block font-weight="bold" font-size="10px"  text-align="left">Endorsement No: <xsl:value-of select="response/endorsements_freeform_01/data/APL1290612" /></fo:block>
					               		</fo:table-cell>
					    		  </fo:table-row>
					    		 
					     </fo:table-body>
				     </fo:table>	   
				</fo:block>
				    <fo:block space-after=".1in" text-align-last="justify">
				    	<fo:leader leader-pattern="rule" />
				   </fo:block>
				     <fo:block margin-top="10mm"/>
				     <fo:block text-align="center" font-weight="bold" font-size="10px">CHANGES IN EXPOSURE ENDORSEMENT</fo:block>
				     
				     <fo:block margin-top="10mm"/>
				     <fo:block text-align="left" font-size="10px">In consideration of the premium charged, it is agreed that If the <fo:inline font-weight="bold" font-style="italic">Named Insured</fo:inline> during the <fo:inline font-weight="bold" font-style="italic">Policy Period</fo:inline> merges or consolidates with another entity in a manner such that the <fo:inline font-weight="bold" font-style="italic">Named Insured</fo:inline> is not the surviving entity, coverage under this policy for <fo:inline font-weight="bold" font-style="italic">Professional Services</fo:inline> rendered, or which should have been rendered subsequent to the effective date of such transaction shall be excluded for the remainder of the <fo:inline font-weight="bold" font-style="italic">Policy Period</fo:inline>. Coverage shall then solely apply to <fo:inline font-weight="bold" font-style="italic">Professional Services</fo:inline> rendered, or which should have been rendered, between the <fo:inline font-weight="bold" font-style="italic">Prior Acts Date</fo:inline> stated in Item 6. of the Declarations and the effective date of the transaction whereby the <fo:inline font-weight="bold" font-style="italic">Named Insured</fo:inline> is not the surviving entity, subject to all the  other terms, conditions and exclusions of this policy.</fo:block>
				    
				     
				         
				        <fo:block margin-top="100mm"/>
				        <fo:block text-align="center" font-size="10px" >All other terms, conditions and limitations of the policy remain unchanged.</fo:block>
				         
				         
				           <fo:block margin-top="10mm"/>
				          <fo:block>
				       <fo:table  text-align="center">
					    <fo:table-column column-width = "80mm" />
				        <fo:table-column column-width = "80mm" />
				       
				        <fo:table-body>
					    		  <fo:table-row>
					                    <fo:table-cell padding-left="4pt"> <fo:block  font-size="10px"  text-align="center"><xsl:value-of select="response/freeform/data/authorized_signature"/></fo:block></fo:table-cell>
					                    <fo:table-cell  padding-left="4pt"><fo:block   font-size="10px"  text-align="center"><xsl:value-of select="response/freeform/data/date"/></fo:block></fo:table-cell>
					    		  </fo:table-row>
					    		  
					    		  <fo:table-row>
					    		  		<fo:table-cell padding-left="4pt" padding-top="4pt">
					               			<fo:block  font-size="10px"  text-align="center">________________________</fo:block>
					               		</fo:table-cell>
					               		<fo:table-cell padding-left="4pt"  padding-top="4pt">
					               			 <fo:block  font-size="10px"  text-align="center">________________________</fo:block>
					               		</fo:table-cell>
					             </fo:table-row>
					    		 
					    		  <fo:table-row>
					               		<fo:table-cell padding-left="4pt" padding-top="4pt"><fo:block  font-size="10px"  text-align="center">Authorized Representative</fo:block></fo:table-cell>
					               		<fo:table-cell padding-left="4pt"  padding-top="4pt">
					               			 <fo:block  font-size="10px"  text-align="center">Date</fo:block>
					               		</fo:table-cell>
					    		  </fo:table-row>
					    		 
					     </fo:table-body>
				     </fo:table>	   
				</fo:block>
				       
		       <fo:block margin-top="40mm"/>
		       <fo:block  font-size="10px" text-indent="2cm"  color="grey" text-align="left"><xsl:value-of select="response/policyformfooter_freeform_01/data/APL1290612" /></fo:block>
			
     </xsl:template>
</xsl:stylesheet>




					
				    	