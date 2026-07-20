<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.1"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:fo="http://www.w3.org/1999/XSL/Format"
	exclude-result-prefixes="fo"
	xmlns:fn="http://www.w3.org/2005/02/xpath-functions"
	xmlns:java="java">

	<xsl:template match="/" name="APL-State-PA-1" >
	
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
					               			 <fo:block font-weight="bold" font-size="10px"  text-align="left">Endorsement No: <xsl:value-of select="response/endorsements_freeform_01/data/APLAMENDPA20091" /></fo:block>
					               		</fo:table-cell>
					    		  </fo:table-row>
					    		 
					     </fo:table-body>
				     </fo:table>	   
				</fo:block>
				    
   <fo:block space-after=".1in" text-align-last="justify">
    <fo:leader leader-pattern="rule" />
   </fo:block>
				   
				      <fo:block margin-top="12mm"/>
				     <fo:block text-align="center" font-weight="bold" font-size="10px" >PENNSYLVANIA AMENDATORY ENDORSEMENT #1</fo:block>
				     <fo:block margin-top="4mm"/>
				    
				
				     <fo:block text-align="left" font-size="10px" >1. This policy may not be canceled by the<fo:inline font-weight="bold" font-style="italic"> Insurer </fo:inline>for any reason at any time except for non payment of premium or deductible by the <fo:inline font-weight="bold" font-style="italic"> Named Insured </fo:inline>.  However, VI.  General Conditions, D. Cancellation and Nonrenewal is amended as follows:</fo:block>
				     <fo:block margin-top="4mm"/>


				     <fo:block text-align="left" font-size="10px" start-indent="2cm" >- in paragraph #2., where the phrase"....at least 10 days before the effective date of cancellation." appears in the
                     second sentence, such phrase is deleted and replaced with the phrase"...at least 15 days before the effective date of cancellation."</fo:block>


				     <fo:block margin-top="4mm"/>

				    <fo:block text-align="left" font-size="10px" start-indent="2cm">- paragraph #2 is amended by the addition of the following sentence: The notice of cancellation will state the reason for cancellation is the non payment of premium due under the policy and, at the<fo:inline font-weight="bold" font-style="italic"> Named Insured's </fo:inline> request the<fo:inline font-weight="bold" font-style="italic"> Insurer </fo:inline>shall provide loss information for the three years preceding such cancellation.
				     </fo:block>
                                    
                    
                     <fo:block margin-top="4mm"/>

				     <fo:block text-align="left" font-size="10px" start-indent="2cm" >- paragraph #3 is deleted an replaced with the following:</fo:block>
				  
				     <fo:block margin-top="4mm"/>

				    <fo:block text-align="left" font-size="10px" start-indent="2cm" >3. This policy may be non-renewed by the<fo:inline font-weight="bold" font-style="italic"> Insurer </fo:inline>by delivering to the<fo:inline font-weight="bold" font-style="italic"> Named Insured </fo:inline>or by first class</fo:block>
                    <fo:block text-align="left" font-size="10px" start-indent="2.5cm" >mailing to the<fo:inline font-weight="bold" font-style="italic"> Named Insured </fo:inline>at its last known address written notice of such nonrenewal, including the reason, at least sixty days prior to the expiration of the<fo:inline font-weight="bold" font-style="italic"> Policy Period</fo:inline>. The proof of mailing of such notice shall be sufficient proof of such notice. At the<fo:inline font-weight="bold" font-style="italic"> Named Insured's </fo:inline>request the<fo:inline font-weight="bold" font-style="italic"> Insurer </fo:inline>shall provide loss information for the three years preceding the non renewal date.</fo:block>
				    
				    <fo:block margin-top="8mm"/>
				    
				    <fo:block text-align="left" font-size="10px" >2. If the<fo:inline font-weight="bold" font-style="italic"> Insurer </fo:inline>offers renewal to the<fo:inline font-weight="bold" font-style="italic"> Named Insured </fo:inline>and such renewal includes an increase in premium the<fo:inline font-weight="bold" font-style="italic"> Insurer </fo:inline></fo:block>				    
   					<fo:block text-align="left" font-size="10px" start-indent="0.5cm"> will deliver or send by first class mail to the<fo:inline font-weight="bold" font-style="italic"> Named Insured </fo:inline>at its last known address, notice of the intent to increase the premium at least thirty days prior to the effective date of such premium increase. The proof of mailing of such notice shall be sufficient proof of such notice.
				     </fo:block>
				    
				     <fo:block margin-top="4mm"/>
				     <fo:block text-align="left" font-size="10px" >3. Item 7. of the Declarations, Optional Extended Reporting Period,</fo:block>
				     <fo:block text-align="left" font-size="10px" start-indent="2cm" >(a) shall read: (i) 12 months; (ii) 24 months</fo:block>
				    <fo:block text-align="left" font-size="10px" start-indent="2cm" >(b) shall read: (i) not more than 150%; (ii) not more than 200%</fo:block>
				    
				   
				    
				    
				    <fo:block margin-top="47mm"/>
				    
				    
				      <fo:block text-align="left" font-size="10px" >All other terms, conditions and limitations of the policy remain unaltered.</fo:block>
				      <fo:block margin-top="15mm"/>
				       <fo:block>
				       <fo:table  text-align="center">
					    <fo:table-column column-width = "80mm" />
				        <fo:table-column column-width = "80mm" />
				       
				        <fo:table-body>
					    		  <fo:table-row>
					                    <fo:table-cell padding-left="4pt"><fo:block  font-size="10px"  text-align="center"><xsl:value-of select="response/freeform/data/authorized_signature"/></fo:block></fo:table-cell>
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
			  <fo:block margin-top="12mm"/>
			  <fo:block  font-size="10px"  text-indent="2cm" color="grey" text-align="left"><xsl:value-of select="response/policyformfooter_freeform_01/data/APLAMENDPA20091" /></fo:block>
		  
     </xsl:template>
</xsl:stylesheet>
