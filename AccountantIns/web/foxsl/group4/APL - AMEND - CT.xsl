<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet version="1.1"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:fo="http://www.w3.org/1999/XSL/Format"
	exclude-result-prefixes="fo"
	xmlns:fn="http://www.w3.org/2005/02/xpath-functions"
	xmlns:java="java">
	
	<xsl:template match="/" name="APL-AMEND-CT">
		
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
					               			 <fo:block font-weight="bold" font-size="10px"  text-align="left">Endorsement No: <xsl:value-of select="response/endorsements_freeform_01/data/APLAMENDCT0811" /></fo:block>
					               		</fo:table-cell>
					    		  </fo:table-row>
					    		 
					     </fo:table-body>
				     </fo:table>	   
				</fo:block>
				    <fo:block space-after=".1in" text-align-last="justify">
				    	<fo:leader leader-pattern="rule" />
				   </fo:block>
				     <fo:block margin-top="10mm"/>
				     <fo:block text-align="center" font-weight="bold" font-size="10px">CONNECTICUT STATE AMENDATORY ENDORSEMENT</fo:block>
				     
				     <fo:block margin-top="10mm"/>
				     <fo:block text-align="left" font-size="10px">1.	Section IV. DEFINTIONS  is amended by the addition of the following:</fo:block>
				    
				     <fo:block margin-top="8mm"/>
				     <fo:block text-align="left" start-indent="1.5cm" font-size="10px"><fo:inline font-weight="bold" font-style="italic">Termination of Coverage</fo:inline> means, whether made by the <fo:inline font-weight="bold" font-style="italic">Insurer</fo:inline> or the <fo:inline font-weight="bold" font-style="italic">Named Insured</fo:inline> at any time, either the cancellation or non renewal of this policy or, a decrease in Limit of Liability, an increase in Deductible, the attachment of a new exclusion or any other change in coverage less favorable to the <fo:inline font-weight="bold" font-style="italic">Named Insured</fo:inline>.</fo:block>
				     
				     <fo:block margin-top="10mm"/>
				     <fo:block text-align="left"  font-size="10px" >2.	   Item 7. of the Declarations shall read (a) 36 months; (b) Additional Premium: 200% of the annual premium</fo:block>
				     
				     <fo:block margin-top="10mm"/>
				     <fo:block text-align="left" font-size="10px" >3.     Section III, EXTENTIONS, Section D. Extended Reporting Period Extensions, is amended as follows:</fo:block> 
				     
				     <fo:block margin-top="8mm"/> 
				     <fo:block text-align="left" text-indent="1.5cm" font-size="10px" >Paragraph 1. is amended by deletion of the first sentence and replacement with the following first sentence:</fo:block>
				     
				     
				     <fo:block margin-top="5mm"/>
				     <fo:block text-align="left"  start-indent="3cm" font-size="10px" >Upon <fo:inline font-weight="bold" font-style="italic">Termination of Coverage</fo:inline>  the <fo:inline font-weight="bold" font-style="italic">Named Insured</fo:inline> shall be provided with an automatic and non- cancelable period of sixty (60) days commencing on the date of <fo:inline font-weight="bold" font-style="italic">Termination of Coverage</fo:inline> to report <fo:inline font-weight="bold" font-style="italic">Claims</fo:inline> to the <fo:inline font-weight="bold" font-style="italic">Insurer</fo:inline> pursuant to  Section VI. A. of General Conditions,Notice of <fo:inline font-weight="bold" font-style="italic">Claim</fo:inline> or Circumstance.</fo:block>
				      
				      
				     <fo:block margin-top="5mm"/>
				     <fo:block text-align="left"  start-indent="1.5cm" font-size="10px">Paragraph 2.  is amended by deletion of the first sentence and replacement with the following first sentence</fo:block>
				     
				     <fo:block margin-top="5mm"/>
				     <fo:block text-align="left"  start-indent="3cm" font-size="10px">Upon <fo:inline font-weight="bold" font-style="italic">Termination of Coverage</fo:inline> the <fo:inline font-weight="bold" font-style="italic">Named Insured</fo:inline> shall have the right upon payment of 200% of the annual premium based upon rates in effect on the later of the date the policy was  issued or, last renewed, and solely for 36 months commencing on the expiration date of the Automatic Extended Reporting Period, to report <fo:inline font-weight="bold" font-style="italic">Claims</fo:inline> pursuant to Section VI. A. of general Conditions, Notice of <fo:inline font-weight="bold" font-style="italic">Claim</fo:inline> or Circumstance.</fo:block>
				     
				     <fo:block margin-top="10mm"/>
				     <fo:block text-align="left"  start-indent="3cm" font-size="10px">4.	Section VI. General Conditions D. Cancellation and Nonrenewal, Paragraph 3 is amended by deletion of these words in the first sentence:  "… written notice of nonrenewal at least thirty days prior to the expiration date…" and  replacing such words with:  " … written notice of non renewal at least sixty days prior to the expiration date… ".</fo:block>
				         
				        <fo:block margin-top="5mm"/>
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
		       <fo:block  font-size="10px" text-indent="2cm"  color="grey" text-align="left"><xsl:value-of select="response/policyformfooter_freeform_01/data/APLAMENDCT0811" /></fo:block>
				  
     </xsl:template>
</xsl:stylesheet>




					
				    	