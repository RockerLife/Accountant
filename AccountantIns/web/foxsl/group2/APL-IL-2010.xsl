<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.1"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:fo="http://www.w3.org/1999/XSL/Format"
	exclude-result-prefixes="fo"
	xmlns:fn="http://www.w3.org/2005/02/xpath-functions"
	xmlns:java="java">

	<xsl:template match="/" name="APL-State-IL">
		
		
				    <fo:block text-align="center" font-size="12px" >THIS ENDORSEMENT CHANGES THE POLICY, READ IT CAREFULLY.</fo:block>
				    <fo:block margin-top="15mm"/>
				    
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
					               			 <fo:block font-weight="bold" font-size="10px"  text-align="left">Endorsement No: <xsl:value-of select="response/endorsements_freeform_01/data/APLAMENDIL2009" /></fo:block>
					               		</fo:table-cell>
					    		  </fo:table-row>
					    		 
					     </fo:table-body>
				     </fo:table>	   
				</fo:block>
				    
   <fo:block space-after=".1in" text-align-last="justify">
    <fo:leader leader-pattern="rule" />
   </fo:block>
				   
				      <fo:block margin-top="10mm"/>
				     <fo:block text-align="center" font-weight="bold" font-size="10px"  > ILLINOIS AMENDATORY ENDORSEMENT (Page One of Three)</fo:block>
				     <fo:block margin-top="10mm"/>
				     
				     
				     <fo:block text-align="left" font-size="10px" >1.  Section VI, General Conditions, D. Cancellation and Non Renewal:</fo:block>
				      <fo:block margin-top="4mm"/>
				     <fo:block text-align="left" font-size="10px" start-indent="2cm" >- paragraph #2, is deleted and replaced with the following 2.:</fo:block>
				      <fo:block margin-top="4mm"/>
				     <fo:block text-align="left" font-size="10px" >The <fo:inline font-weight="bold" font-style="italic"> Insurer </fo:inline>may only cancel this policy for nonpayment of premium. This policy may be canceled by or on behalf of the<fo:inline font-weight="bold" font-style="italic"> Insurer </fo:inline>by delivering to the <fo:inline font-weight="bold" font-style="italic"> Named Insured </fo:inline>, or by mailing to the <fo:inline font-weight="bold" font-style="italic"> Named Insured </fo:inline>at its last known mailing address, as well as to the<fo:inline font-weight="bold" font-style="italic"> Named Insured's </fo:inline> insurance agent or broker, if known,  written notice of cancellation at least 10 days before the effective date of cancellation. The insurance agent or broker, if known,  written notice of cancellation at least 10 days before the effective date of cancellation. The<fo:inline font-weight="bold" font-style="italic"> Insurer </fo:inline>must retain proof of mailing on a form acceptable to the U. S. Post office or other commercial mail delivery service. Mailing of a cancellation notice by any other means shall not be deemed a valid notice of cancelation. If the <fo:inline font-weight="bold" font-style="italic"> Insurer </fo:inline> cancels this policy for non payment of premium, the<fo:inline font-weight="bold" font-style="italic"> Insurer </fo:inline> shall credit the <fo:inline font-weight="bold" font-style="italic"> Insured </fo:inline>	90% of the unearned portion of the premium. Payment or tender of any unearned premium by the <fo:inline font-weight="bold" font-style="italic"> Insurer </fo:inline>shall not be a condition precedent to the effectiveness of cancellation, but such payment shall be as soon as practicable.</fo:block>
				     
				      <fo:block margin-top="4mm"/>
				      
				     <fo:block text-align="left" font-size="10px" start-indent="2cm" >- paragraph #3 is deleted and replaced with the following #3.:</fo:block>
				     
				      <fo:block margin-top="4mm"/>
				      
				    <fo:block text-align="left" font-size="10px"  > This policy may be non-renewed by the <fo:inline font-weight="bold" font-style="italic"> Insurer </fo:inline>by mailing to the <fo:inline font-weight="bold" font-style="italic"> Named Insured </fo:inline>at its last known address, as well as to the<fo:inline font-weight="bold" font-style="italic"> Insured's </fo:inline> insurance broker or agent , if known,  written notice of such nonrenewal at least sixty days prior to the expiration of the <fo:inline font-weight="bold" font-style="italic"> Policy Period </fo:inline>. The <fo:inline font-weight="bold" font-style="italic"> Insurer </fo:inline> must maintain proof of mailing on a form acceptable to the U.S. Post Office or other commercial mail delivery service. Mailing of non renewal notices by any other means shall not be deemed valid non renewal notice.</fo:block>
				     
				     <fo:block margin-top="4mm"/>
				     
				     <fo:block text-align="left" font-size="10px" >Even if the <fo:inline font-weight="bold" font-style="italic"> Insurer </fo:inline>fails to comply with the paragraph above, the policy will expire at the end of the <fo:inline font-weight="bold" font-style="italic"> Policy Period </fo:inline>if (a) the <fo:inline font-weight="bold" font-style="italic"> Named Insured </fo:inline>fails to pay any premium or installment premium due the <fo:inline font-weight="bold" font-style="italic"> Insurer </fo:inline>whether payable direct to the<fo:inline font-weight="bold" font-style="italic"> Insurer </fo:inline>	or to an agent of the <fo:inline font-weight="bold" font-style="italic"> Insurer </fo:inline>, or indirectly under any premium finance plan or extension of credit; (b) the<fo:inline font-weight="bold" font-style="italic"> Insurer </fo:inline>has indicated its willingness to the <fo:inline font-weight="bold" font-style="italic"> Named Insured </fo:inline>, or to its representative, to renew the policy, or (c) the<fo:inline font-weight="bold" font-style="italic"> Named Insured </fo:inline>has notified the <fo:inline font-weight="bold" font-style="italic"> Insurer </fo:inline>or the agent of the <fo:inline font-weight="bold" font-style="italic"> Insurer </fo:inline>	that the <fo:inline font-weight="bold" font-style="italic"> Named Insured </fo:inline>does not wish to renew the policy.</fo:block>
				     		     		             
				      <fo:block margin-top="8mm"/>
				      
				     <fo:block text-align="left" font-size="10px" >2.  Section VI., General Conditions, G. Other Insurance, is deleted and replaced with the following:</fo:block>
				     
				     <fo:block margin-top="4mm"/>
				      
				     <fo:block text-align="left" font-size="10px" start-indent="2cm" >All <fo:inline font-weight="bold" font-style="italic"> Damages  </fo:inline>and <fo:inline font-weight="bold" font-style="italic"> Defense Expenses </fo:inline>payable under this policy shall be shared proportionately with any other insurance, except insurance written to be specifically excess  above this policy, applicable to any <fo:inline font-weight="bold" font-style="italic"> Claim  </fo:inline>made under this policy. This policy shall not be the subject of any terms or conditions of such other insurance.</fo:block>
				      
				     <fo:block margin-top="8mm"/>
				     
				     <fo:block text-align="left" font-size="10px" >3. Item 7.  of the Declarations, Optional Extended Reporting Period:</fo:block>
				     <fo:block text-align="left" font-size="10px" start-indent="2cm" > - (a) shall not be for a period less than 12 months;</fo:block>
				     <fo:block text-align="left" font-size="10px" start-indent="2cm" >- (b) shall not be more than 150% of the annual policy premium </fo:block>
				     
				     
				     <fo:block margin-top="15mm"/>				     
				     <fo:block text-align="center" font-size="12px" ><fo:inline font-weight="bold" font-style="italic"> Continued, on Page Two</fo:inline></fo:block>
				    
				     <fo:block margin-top="15mm"/>
			  		<fo:block  font-size="10px"  text-indent="2cm" color="grey" text-align="left">APL - AMEND - IL - 2009</fo:block>
				      	 
				     <fo:block break-after="page" />    	 
				      	 
				     <fo:block text-align="center" font-size="12px" >THIS ENDORSEMENT CHANGES THE POLICY, READ IT CAREFULLY.</fo:block>  	 
				      	 
				      	 
				      <fo:block margin-top="8mm"/>
				     <fo:block text-align="center" font-weight="bold" font-size="10px"  >ILLINOIS AMENDATORY ENDORSEMENT (Page Two of Three) </fo:block>
				      <fo:block margin-top="8mm"/>
				      
				      <fo:block text-align="left" font-size="10px" >4.  Section III, Extensions of Coverage, D., Extended Reporting Period Extensions, paragraphs 1., 2. and 3. are deleted and replaced with the following paragraphs.:</fo:block>
				      
				      <fo:block margin-top="4mm"/>
				      				      
				      <fo:block text-align="left" font-size="10px" start-indent="2cm" ><fo:inline font-weight="bold" font-style="italic"> 1. Automatic Extended Reporting Period </fo:inline></fo:block>	 
				     <fo:block margin-top="4mm"/>
				     
				      <fo:block text-align="left" font-size="10px" start-indent="2cm" >Upon the expiration of this policy for any reason other than for cancellation for nonpayment of premium due hereunder, the <fo:inline font-weight="bold" font-style="italic"> Named Insured </fo:inline>shall be provided with an automatic and non cancelable period of sixty days, commencing on the policy expiration date, to report <fo:inline font-weight="bold" font-style="italic"> Claims </fo:inline>to the <fo:inline font-weight="bold" font-style="italic"> Insurer </fo:inline>pursuant to Section VI. A. of General Conditions, Notice of <fo:inline font-weight="bold" font-style="italic"> Claim or Circumstance.</fo:inline>. Coverage under this extension of time to report a <fo:inline font-weight="bold" font-style="italic"> Claim  </fo:inline>(hereinafter referred to as the Automatic Extended Reporting Period) shall apply solely to negligent acts, errors or omissions committed, attempted or allegedly committed or attempted, in whole, prior to the effective date of nonrenewal or cancellation, whichever occurs first, and which are not otherwise excluded by any terms, conditions or exclusions of this policy. This Automatic Extended Reporting Period shall not be applicable, however, in the event the <fo:inline font-weight="bold" font-style="italic"> Named Insured </fo:inline> has obtained another policy of Accountants Professional Liability insurance with an inception date as of the termination date of this policy.</fo:block>
				      
				      <fo:block margin-top="4mm"/>
				      <fo:block text-align="left" font-size="10px" start-indent="2cm" ><fo:inline font-weight="bold" font-style="italic"> 2.Optional Extended Reporting Period </fo:inline></fo:block>	 
				      <fo:block margin-top="4mm"/> 
				      
				      <fo:block text-align="left" font-size="10px" start-indent="2cm" >Upon the expiration, non renewal or cancellation of this policy for any reason, the <fo:inline font-weight="bold" font-style="italic"> Named Insured </fo:inline>shall have the right, upon payment of the additional premium stated in Item 7. (b) of the Declarations and solely for the length of time stated in Item 7. (a) of the Declarations commencing on the expiration date of the Automatic Extended Reporting Period, to report <fo:inline font-weight="bold" font-style="italic"> Claims </fo:inline>pursuant to Section VI.A. of General Conditions, Notice of <fo:inline font-weight="bold" font-style="italic"> Claim </fo:inline>or Circumstance. Coverage under such extension of time to report a <fo:inline font-weight="bold" font-style="italic"> Claim  </fo:inline>(hereinafter referred to as the Optional Extended Reporting Period) shall apply solely to negligent acts, errors or omissions committed, attempted or allegedly committed or attempted, in whole, prior to the effective date of nonrenewal or cancellation, whichever occurs first, and which are not otherwise excluded by any terms, conditions or exclusions of this policy.</fo:block>
				       <fo:block margin-top="4mm"/> 
				       
				       <fo:block text-align="left" font-size="10px" start-indent="2cm" >If the Optional Extended Reporting Period is requested by the <fo:inline font-weight="bold" font-style="italic"> Named Insured </fo:inline>, the additional premium stated in Item 7. (b) shall be fully earned, and the Optional Extended Reporting Period cannot be cancelled by the  <fo:inline font-weight="bold" font-style="italic"> Insureds </fo:inline> or the  <fo:inline font-weight="bold" font-style="italic"> Insurer </fo:inline>. This right to purchase the Optional Extended Reporting Period shall lapse unless written notice of such election, together with payment of the additional premium due, is received by the  <fo:inline font-weight="bold" font-style="italic"> Insurer </fo:inline>no later than sixty days following the effective date of cancellation or nonrenewal, whichever occurs first.</fo:block>
				      				      
				        <fo:block margin-top="4mm"/>
				      <fo:block text-align="left" font-size="10px" start-indent="2cm" ><fo:inline font-weight="bold" font-style="italic"> 3. Retired Insured Extended Reporting Period </fo:inline></fo:block>	 
				      <fo:block margin-top="4mm"/> 
				      	
				      <fo:block text-align="left" font-size="10px" start-indent="2cm" >Provided this policy (a) is not cancelled (b) no other Extended Reporting Period is in effect and (c) this policy remains in force as to all other  <fo:inline font-weight="bold" font-style="italic"> Insureds </fo:inline>, except in the case where all  <fo:inline font-weight="bold" font-style="italic"> Insureds </fo:inline> are <fo:inline font-weight="bold" font-style="italic">Retired Insureds </fo:inline>, a <fo:inline font-weight="bold" font-style="italic">Retired Insureds  </fo:inline> shall have the right to purchase an unlimited extension of time to report <fo:inline font-weight="bold" font-style="italic"> Claims </fo:inline> that are first made against such <fo:inline font-weight="bold" font-style="italic">Retired Insureds </fo:inline> and reported to the  <fo:inline font-weight="bold" font-style="italic"> Insurer </fo:inline> after the effective date of retirement by reason of negligent acts, errors or omissions alleged to have been committed subsequent to the applicable  <fo:inline font-weight="bold" font-style="italic"> Prior Acts Date, </fo:inline>, but before the effective date of retirement and not otherwise excluded by the agreements, conditions and exclusions of this policy. The additional premium for this <fo:inline font-weight="bold" font-style="italic">Retired Insured </fo:inline> Extended Reporting Period shall be 125% of the <fo:inline font-weight="bold" font-style="italic">Retired Insured's </fo:inline> proportionate share of the annual premium (275% of the annual premium in the case where all <fo:inline font-weight="bold" font-style="italic"> Insureds </fo:inline> are <fo:inline font-weight="bold" font-style="italic">Retired Insureds </fo:inline>) and is due and payable to the <fo:inline font-weight="bold" font-style="italic"> Insurer </fo:inline> no later than thirty days after the effective date of retirement, or cessation of the practice of accountancy.</fo:block>	 
				      
				      
				      	 <fo:block margin-top="15mm"/>				     
				     <fo:block text-align="center" font-size="12px" ><fo:inline font-weight="bold" font-style="italic"> Continued, on Page Three</fo:inline></fo:block>
				    
				     <fo:block margin-top="15mm"/>
			  		<fo:block  font-size="10px"  text-indent="2cm" color="grey" text-align="left">APL - AMEND - IL - 2009</fo:block>
	
				      	 
				  <fo:block break-after="page" />    	 
				      	 
				     <fo:block text-align="center" font-size="12px" >THIS ENDORSEMENT CHANGES THE POLICY, READ IT CAREFULLY.</fo:block>  	 
				      	 
				      	 
				      <fo:block margin-top="8mm"/>
				     <fo:block text-align="center" font-weight="bold" font-size="10px" >ILLINOIS AMENDATORY ENORSEMENT (Page Three of Three)</fo:block>
				      <fo:block margin-top="8mm"/>	 
				      
				      <fo:block text-align="left" font-size="10px" >5. Section IV, Definitions, definition of <fo:inline font-weight="bold" font-style="italic"> Damages </fo:inline>is amended as follows:</fo:block>
				      <fo:block margin-top="8mm"/>
				      
				      <fo:block text-align="left" font-size="10px" start-indent="2cm" >- the first two sentences of the definition of <fo:inline font-weight="bold" font-style="italic"> Damages  </fo:inline> are deleted and replaced with the following:</fo:block>
				      		<fo:block text-align="left" font-size="10px" start-indent="4cm" ><fo:inline font-weight="bold" font-style="italic">Damages </fo:inline> means any monetary judgment, monetary award or monetary settlement negotiated with the  <fo:inline font-weight="bold" font-style="italic"> Insurer's </fo:inline>  written consent.  Pre- and post- judgment interest awarded against the<fo:inline font-weight="bold" font-style="italic"> Insured </fo:inline> shall be deemed an element of <fo:inline font-weight="bold" font-style="italic"> Damages  </fo:inline> which are payable in addition to the Limit of Liability as a supplementary payment.</fo:block>
				      	 <fo:block margin-top="8mm"/>			      
				      <fo:block text-align="left" font-size="10px" start-indent="2cm" > - paragraph (c) under “<fo:inline font-weight="bold" font-style="italic"> Damages  </fo:inline>shall not include: …” is deleted and replaced with the following (c):</fo:block>	 
				      	 
				      	<fo:block margin-top="8mm"/>
				      	 <fo:block text-align="left" font-size="10px" start-indent="4cm" >(c) punitive or exemplary damages, awards or judgments or any amounts of compensatory damages/awards or judgments if awarded as a result on the <fo:inline font-weight="bold" font-style="italic"> Insured's </fo:inline> own misconduct; however,<fo:inline font-weight="bold" font-style="italic"> Insurer </fo:inline>shall have a  right and duty to defend and pay <fo:inline font-weight="bold" font-style="italic"> Defense Costs</fo:inline> for any <fo:inline font-weight="bold" font-style="italic"> Claim  </fo:inline>  which includes a demand for both punitive and covered compensatory <fo:inline font-weight="bold" font-style="italic"> Damages  </fo:inline>under this policy</fo:block>
				      
				      	 			      
				      
				            <fo:block margin-top="120mm"/>
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
				     
				     
				       <fo:block margin-top="8mm"/>
			  <fo:block  font-size="10px"  text-indent="2cm" color="grey" text-align="left"><xsl:value-of select="response/policyformfooter_freeform_01/data/APLAMENDIL2009" /></fo:block>
			 	 
     </xsl:template>
</xsl:stylesheet>