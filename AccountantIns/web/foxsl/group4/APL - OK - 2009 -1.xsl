<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.1"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:fo="http://www.w3.org/1999/XSL/Format"
	exclude-result-prefixes="fo"
	xmlns:fn="http://www.w3.org/2005/02/xpath-functions"
	xmlns:java="java">

	<xsl:template match="/" name="APL-State-OK-1" >
	
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
					               			 <fo:block font-weight="bold" font-size="10px"  text-align="left">Endorsement No: <xsl:value-of select="response/endorsements_freeform_01/data/APLAMENDOK2009" /></fo:block>
					               		</fo:table-cell>
					    		  </fo:table-row>
					    		 
					     </fo:table-body>
				     </fo:table>	   
				</fo:block>
				    
   <fo:block space-after=".1in" text-align-last="justify">
    <fo:leader leader-pattern="rule" />
   </fo:block>
				   
				      <fo:block margin-top="12mm"/>
				     <fo:block text-align="center" font-weight="bold" font-size="10px" >OKLAHOMA AMENDATORY ENDORSEMENT</fo:block>
				     <fo:block margin-top="4mm"/>
				    
				
				     <fo:block text-align="left" font-size="10px" >1.  This policy may not be canceled by the<fo:inline font-weight="bold" font-style="italic"> Insurer </fo:inline>for any reason at any time except for non payment of premium or</fo:block>
				     <fo:block text-align="left" font-size="10px" start-indent="0.5cm" >deductible by the<fo:inline font-weight="bold" font-style="italic"> Named Insured </fo:inline>. However, VI., General Conditions, D. Cancellation and Nonrenewal, paragraph #3 is deleted and replaced with the following: </fo:block>
				     <fo:block margin-top="4mm"/>


				     <fo:block text-align="left" font-size="10px" start-indent="2cm">3.  This policy may be non-renewed by the<fo:inline font-weight="bold" font-style="italic"> Insurer </fo:inline>by delivering to the<fo:inline font-weight="bold" font-style="italic"> Named Insured</fo:inline> or by mailing to</fo:block>
					 <fo:block text-align="left" font-size="10px" start-indent="2.3cm">the<fo:inline font-weight="bold" font-style="italic"> Named Insured </fo:inline>at its last known address written notice of such nonrenewal at least forty five days prior to the expiration of the<fo:inline font-weight="bold" font-style="italic"> Policy Period</fo:inline>, or prior to the anniversary date if the policy is written for a period greater than one year or with no fixed expiration date. The proof of mailing of such notice shall be sufficient proof of such notice.
					 </fo:block>
					 <fo:block margin-top="4mm"/>
					 
                     <fo:block text-align="left" font-size="10px" start-indent="2.3cm">If the<fo:inline font-weight="bold" font-style="italic"> Insurer </fo:inline> fails to meet the above requirements, the<fo:inline font-weight="bold" font-style="italic"> Policy Period </fo:inline>will be extended for forty five days from the date proper notice is given, and the premium for such extension shall be at pro rata of the expiring policy premium.</fo:block>
                     <fo:block margin-top="4mm"/>
                      
                      <fo:block text-align="left" font-size="10px" start-indent="2.3cm">The<fo:inline font-weight="bold" font-style="italic"> Insurer </fo:inline>is not required to provide such notice of non renewal if:</fo:block>
                      <fo:block text-align="left" font-size="10px" start-indent="3cm">- the<fo:inline font-weight="bold" font-style="italic"> Insurer </fo:inline>or an insurance company with in the same group as the<fo:inline font-weight="bold" font-style="italic"> Insurer </fo:inline>offers a renewal policy;</fo:block>
                      <fo:block text-align="left" font-size="10px" start-indent="3cm">- the<fo:inline font-weight="bold" font-style="italic"> Named Insured </fo:inline>has replaced coverage, or has agreed in writing to do so</fo:block>
                      <fo:block margin-top="4mm"/>
                       
                       <fo:block text-align="left" font-size="10px" start-indent="2.3cm">If the<fo:inline font-weight="bold" font-style="italic"> Insurer </fo:inline>has provided the proper notice as described herein and thereafter extends the<fo:inline font-weight="bold" font-style="italic"> Policy Period </fo:inline>for ninety days or less, the<fo:inline font-weight="bold" font-style="italic"> Insurer </fo:inline>shall not provide an additional non renewal notice with respect to such extension.
                       </fo:block>
                       <fo:block margin-top="4mm"/>
                       <fo:block text-align="left" font-size="10px" start-indent="2.3cm">If the<fo:inline font-weight="bold" font-style="italic"> Insurer </fo:inline>elects to offer a renewal of this policy it will provide written notice of any premium increase, change in deductible or reduction in limit of liability or coverage to the<fo:inline font-weight="bold" font-style="italic"> Named Insured </fo:inline>at the last mailing address known to the<fo:inline font-weight="bold" font-style="italic"> Insurer </fo:inline>at least forty five days before the end of the<fo:inline font-weight="bold" font-style="italic"> Policy Period </fo:inline>or anniversary date of this policy if written for a term longer than twelve months or with no fixed expiration date. Proof of mailing will be sufficient proof of notice. If notice is not delivered or mailed in accordance with this paragraph, the expiring premium, deductible, limit of liability and coverage in effect at the end of the<fo:inline font-weight="bold" font-style="italic"> Policy Period </fo:inline>will remain in effect for forty five days after such notice is, in fact sent, or until replacement coverage is obtained by the<fo:inline font-weight="bold" font-style="italic"> Named Insured</fo:inline>, whichever occurs first. The premium for such extension shall be calculated at pro rata of the expiring rate, or of the renewal rate, whichever is lower. If the<fo:inline font-weight="bold" font-style="italic"> Named Insured </fo:inline>accepts the renewal, the premium increase or coverage changes will be effective the day following the prior policy’s expiration or anniversary date.</fo:block>
                       <fo:block margin-top="4mm"/>
                       <fo:block text-align="left" font-size="10px" start-indent="2.3cm">Notice as described above shall not be required of the<fo:inline font-weight="bold" font-style="italic"> Insurer </fo:inline>under these circumstances:</fo:block>
                       <fo:block text-align="left" font-size="10px" start-indent="2.9cm">a) changes in a rate plan filed with or approved by the State Board for Property and Casualty Rates or</fo:block>
                       <fo:block text-align="left" font-size="10px" start-indent="3.1cm">filed pursuant to the Commercial Property and Casualty Competitive Loss Cost Rating Act applicable to an entire class of business;</fo:block>
                       <fo:block text-align="left" font-size="10px" start-indent="2.9cm">b) changes which are based upon the altered nature or extent of the risk insured; or</fo:block>
                       <fo:block text-align="left" font-size="10px" start-indent="2.9cm">c) changes in policy forms filed with or approved by the Insurance Commissioner and applicable to an</fo:block>
                       <fo:block text-align="left" font-size="10px" start-indent="3.1cm">entire class of business.</fo:block>
                        
                       <fo:block margin-top="4mm"/>
                       <fo:block text-align="left" font-size="10px" >2. Paragraph (c) under<fo:inline font-weight="bold" font-style="italic"> "Damages </fo:inline>shall not include…" in the Definition of<fo:inline font-weight="bold" font-style="italic"> Damages</fo:inline>, in Section IV of this policy Definitions, is deleted and replaced with the following: (c) punitive or exemplary damages, awards or judgments or any amounts which are a multiple of compensatory damages, awards and judgments, except to the extent, if any, permissible by Oklahoma statutes.</fo:block> 

                      <fo:block margin-top="2mm"/>
				    
				      <fo:block text-align="left" font-size="10px" >All other terms, conditions and limitations of the policy remain unaltered.</fo:block>
				      <fo:block margin-top="6mm"/>
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
			  <fo:block margin-top="5mm"/>
			  <fo:block  font-size="10px"  text-indent="2cm" color="grey" text-align="left"><xsl:value-of select="response/policyformfooter_freeform_01/data/APLAMENDOK2009" /></fo:block>
	  
			
     </xsl:template>
</xsl:stylesheet>
