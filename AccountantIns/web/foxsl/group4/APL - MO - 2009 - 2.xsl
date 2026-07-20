<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.1"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:fo="http://www.w3.org/1999/XSL/Format"
	exclude-result-prefixes="fo"
	xmlns:fn="http://www.w3.org/2005/02/xpath-functions"
	xmlns:java="java">

	<xsl:template match="/" name="APL-State-MO-2" >
		
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
					               			 <fo:block font-weight="bold" font-size="10px"  text-align="left">Endorsement No: <xsl:value-of select="response/endorsements_freeform_01/data/APLAMENDMO20092" /></fo:block>
					               		</fo:table-cell>
					    		  </fo:table-row>
					    		 
					     </fo:table-body>
				     </fo:table>	   
				</fo:block>
				    
   <fo:block space-after=".1in" text-align-last="justify">
    <fo:leader leader-pattern="rule" />
   </fo:block>
				   
				      <fo:block margin-top="12mm"/>
				     <fo:block text-align="center" font-weight="bold" font-size="10px" >MISSOURI AMENDATORY ENDORSEMENT #2</fo:block>
				     <fo:block margin-top="4mm"/>
				    
				
				     <fo:block text-align="left" font-size="10px" >I. This policy may not be canceled by the<fo:inline font-weight="bold" font-style="italic"> Insurer </fo:inline>for any reason at any time except for non payment of premium or deductible by the <fo:inline font-weight="bold" font-style="italic"> Named Insured </fo:inline>.  However, VI., General Conditions, D. Cancellation and Nonrenewal, paragraph #3 is deleted and replaced with the following:</fo:block>
				     <fo:block margin-top="4mm"/>


				     <fo:block text-align="left" font-size="10px" start-indent="3cm">3. This policy may be non-renewed by the<fo:inline font-weight="bold" font-style="italic"> Insurer </fo:inline>by delivering to the<fo:inline font-weight="bold" font-style="italic"> Named Insured</fo:inline>, or by mailing to the<fo:inline font-weight="bold" font-style="italic"> Named Insured </fo:inline>at its last known address written notice of such non renewal, including the reason for  non-renewal, at least  sixty days prior to the expiration of the<fo:inline font-weight="bold" font-style="italic"> Policy Period</fo:inline>. The proof of mailing such notice shall be sufficient proof of notice.</fo:block>
					
					<fo:block margin-top="4mm"/>
					 <fo:block text-align="left" font-size="10px" >II. The following supersedes any other policy provisions to the contrary:</fo:block>
                     <fo:block text-align="left" font-size="10px" start-indent="1cm"><fo:inline font-weight="bold" font-style="italic">MISSOURI PROPERTY AND CASUALTY INSURANCE GUARANTY ASSOCIATION COVERAGE LIMITATIONS</fo:inline></fo:block>    
                      <fo:block margin-top="4mm"/>
                     
                     
                     <fo:block text-align="left" font-size="10px" start-indent="1cm">1. Subject to the provisions of the Missouri Property and Casualty Insurance Guaranty Association Act (to be referred to as the “Act”) if the<fo:inline font-weight="bold" font-style="italic"> Insurer </fo:inline>is a member of the Missouri property and Casualty Insurance Guaranty Association (to be referred to as the “Association”) the Association will pay<fo:inline font-weight="bold" font-style="italic"> Claim </fo:inline>covered under the Act if the<fo:inline font-weight="bold" font-style="italic"> Insurer </fo:inline>becomes insolvent.</fo:block>
                    <fo:block margin-top="4mm"/>
                    <fo:block text-align="left" font-size="10px" start-indent="1cm">2. The Act contains various exclusions, conditions and limitations that govern a claimant’s eligibility to collect payment from the Association and affect the amount of any payment. The following limitations apply subject to all other provisions of the Act:</fo:block>
                    <fo:block text-align="left" font-size="10px" start-indent="2.5cm">(a) <fo:inline font-weight="bold" font-style="italic"> Claims </fo:inline>covered by the Association do not include a<fo:inline font-weight="bold" font-style="italic"> Claim </fo:inline>by or against an<fo:inline font-weight="bold" font-style="italic"> Insured </fo:inline>of an insolvent<fo:inline font-weight="bold" font-style="italic"> Insurer </fo:inline>in the<fo:inline font-weight="bold" font-style="italic"> Insured </fo:inline>has a net worth of more than $25 million on the later of the end of the<fo:inline font-weight="bold" font-style="italic"> Insured's </fo:inline>most recent fiscal year or the December 31st of the year next preceding the date the<fo:inline font-weight="bold" font-style="italic"> Insurer </fo:inline>becomes insolvent; provided that an<fo:inline font-weight="bold" font-style="italic"> Insured's </fo:inline>net worth on such date shall be deemed to include the aggregate net worth of the<fo:inline font-weight="bold" font-style="italic"> Insured </fo:inline>and of all its affiliates as calculated on a consolidated basis.</fo:block>
                    <fo:block text-align="left" font-size="10px" start-indent="2.5cm">(b) Payments made by the Association for covered<fo:inline font-weight="bold" font-style="italic"> Claims </fo:inline>will include only that amount of each<fo:inline font-weight="bold" font-style="italic"> Claim </fo:inline>which is less than $300,000.</fo:block>
                                                                 
                     <fo:block margin-top="4mm"/>  
                     
                     <fo:block text-align="left" font-size="10px" start-indent="2.4cm" >However, the Association will not:</fo:block>
                        <fo:block margin-top="4mm"/>  
                	 <fo:block text-align="left" font-size="10px" start-indent="2.5cm" >(1) pay an amount excess of the applicable limit of insurance of the policy from which the<fo:inline font-weight="bold" font-style="italic"> Claim </fo:inline>arises; or,</fo:block>
							
					<fo:block text-align="left" font-size="10px" start-indent="2.5cm" >(2) return to an<fo:inline font-weight="bold" font-style="italic"> Insured </fo:inline>any unearned premium in excess of $25,000.</fo:block>	
			 		<fo:block margin-top="4mm"/> 
			 		 <fo:block text-align="left" font-size="10px" start-indent="2.5cm" >These limitations have no effect on the coverage the<fo:inline font-weight="bold" font-style="italic"> Insurer </fo:inline>provides under this policy.</fo:block>
			 		
				   
				     <fo:block margin-top="25mm"/>
				    
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
			  <fo:block  font-size="10px"  text-indent="2cm" color="grey" text-align="left"><xsl:value-of select="response/policyformfooter_freeform_01/data/APLAMENDMO20092" /></fo:block>
		 
			
     </xsl:template>
</xsl:stylesheet>
