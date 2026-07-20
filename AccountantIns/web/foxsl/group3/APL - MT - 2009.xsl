<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.1"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:fo="http://www.w3.org/1999/XSL/Format"
	exclude-result-prefixes="fo"
	xmlns:fn="http://www.w3.org/2005/02/xpath-functions"
	xmlns:java="java">

	<xsl:template match="/" name="APL-State-MT" >
	
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
					               			 <fo:block font-weight="bold" font-size="10px"  text-align="left">Endorsement No: <xsl:value-of select="response/endorsements_freeform_01/data/APLAMENDMT1209" /></fo:block>
					               		</fo:table-cell>
					    		  </fo:table-row>
					    		 
					     </fo:table-body>
				     </fo:table>	   
				</fo:block>
				    
   <fo:block space-after=".1in" text-align-last="justify">
    <fo:leader leader-pattern="rule" />
   </fo:block>
				   
				      <fo:block margin-top="12mm"/>
				     <fo:block text-align="center" font-weight="bold" font-size="10px" >MONTANA AMENDATORY ENDORSEMENT</fo:block>
				     <fo:block margin-top="4mm"/>
				    
				
				     <fo:block text-align="left" font-size="10px" >1. This policy may not be canceled by the<fo:inline font-weight="bold" font-style="italic"> Insurer </fo:inline>for any reason at any time except for non payment of premium or deductible by the<fo:inline font-weight="bold" font-style="italic"> Named Insured </fo:inline>.  However, VI., General Conditions, D. Cancellation and Nonrenewal, paragraph #3 is deleted and replaced with the following: </fo:block>
				     <fo:block margin-top="4mm"/>


				     <fo:block text-align="left" font-size="10px" start-indent="2cm">3. This policy may be non-renewed by the<fo:inline font-weight="bold" font-style="italic"> Insurer </fo:inline>by delivering to the<fo:inline font-weight="bold" font-style="italic"> Named Insured </fo:inline>, or by mailing to the</fo:block>
					 <fo:block text-align="left" font-size="10px" start-indent="2.3cm"><fo:inline font-weight="bold" font-style="italic"> Named Insured </fo:inline>at its last known address written notice of such non renewal at least forty five days prior to the expiration of the<fo:inline font-weight="bold" font-style="italic"> Policy Period </fo:inline>. The proof of mailing such notice shall be sufficient proof of notice. The<fo:inline font-weight="bold" font-style="italic"> Insurer </fo:inline> is not required to deliver of mail such notice if the<fo:inline font-weight="bold" font-style="italic"> Named Insured </fo:inline>has purchased coverage elsewhere, accepted replacement coverage, requested or agreed to non- renewal or if this policy is expressly designated as non-renewable.</fo:block>

				     <fo:block margin-top="4mm"/>

				     <fo:block text-align="left" font-size="10px" >2. Section VI., General Conditions, is amended by the addition of the following paragraph M.:</fo:block>
                     <fo:block margin-top="4mm"/>  
                         
                       <fo:block text-align="left" font-size="10px" start-indent="2cm">M. Conformity with Montana Statutes</fo:block>
                        
                        <fo:block text-align="left" font-size="10px" start-indent="3.5cm">The provisions of this policy conform to the minimum requirements of Montana law and control over any conflicting statutes of any state in which the<fo:inline font-weight="bold" font-style="italic"> Insured </fo:inline>resides on or after the effective date of this policy.</fo:block>
                        
                        <fo:block margin-top="4mm"/>  
                         <fo:block text-align="left" font-size="10px" >3. Item 7. of the Declarations, Optional Extended Reporting Period:</fo:block>
                         <fo:block text-align="left" font-size="10px" start-indent="2cm">- (a) shall read: (i) 12 months or (ii) 24 months</fo:block>
                         <fo:block text-align="left" font-size="10px" start-indent="2cm">- (b) shall read; (i) not more than 150% (ii) not more than 200%</fo:block>
                          <fo:block margin-top="4mm"/>
							
						 <fo:block text-align="left" font-size="10px" >4. It is agreed that notwithstanding anything contained herein to the contrary, the coverage provisions of this policy,</fo:block>
						 <fo:block text-align="left" font-size="10px" start-indent="0.5cm">inclusive of coverage provisions afforded within any of the Extended Reporting Periods, shall not be subject to any restriction in respect of fully earned premium charges. Wherever such provisions appear, The<fo:inline font-weight="bold" font-style="italic"> Named Insured </fo:inline>may request cancellation and the<fo:inline font-weight="bold" font-style="italic"> Insurer </fo:inline>will  then cancel  the applicable coverage and return 90% of the unearned premium.</fo:block>	
			 	
				   
				 
				       <fo:block margin-top="58mm"/>
				    
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
			  <fo:block margin-top="15mm"/>
			  <fo:block  font-size="10px"  text-indent="2cm" color="grey" text-align="left"><xsl:value-of select="response/policyformfooter_freeform_01/data/APLAMENDMT1209" /></fo:block>
		    
			
     </xsl:template>
</xsl:stylesheet>
