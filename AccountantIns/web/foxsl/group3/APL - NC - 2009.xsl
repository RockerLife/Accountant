<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.1"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:fo="http://www.w3.org/1999/XSL/Format"
	exclude-result-prefixes="fo"
	xmlns:fn="http://www.w3.org/2005/02/xpath-functions"
	xmlns:java="java">

	<xsl:template match="/" name="APL-State-NC" >
	
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
					               			 <fo:block font-weight="bold" font-size="10px"  text-align="left">Endorsement No: <xsl:value-of select="response/endorsements_freeform_01/data/APLAMENDNC2009" /></fo:block>
					               		</fo:table-cell>
					    		  </fo:table-row>
					    		 
					     </fo:table-body>
				     </fo:table>	   
				</fo:block>
				    
  		 <fo:block space-after=".1in" text-align-last="justify">
    			<fo:leader leader-pattern="rule" />
  		 </fo:block>
				   
				      <fo:block margin-top="12mm"/>
				     <fo:block text-align="center" font-weight="bold" font-size="10px" >NORTH CAROLINA AMENDATORY ENDORSEMENT (Page 1 of 2)</fo:block>
				     <fo:block margin-top="10mm"/>
				    
				
				     <fo:block text-align="left" font-size="10px" >1. This policy may not be canceled by the <fo:inline font-weight="bold" font-style="italic">Insurer</fo:inline> for any reason at any time except for non payment of premium or deductible by the <fo:inline font-weight="bold" font-style="italic"> Named Insured </fo:inline>.   However, VI. General Conditions, D. Cancellation and Nonrenewal is amended as follows:</fo:block>
				     <fo:block margin-top="6mm"/>
				     
					 <fo:block text-align="left" font-size="10px" start-indent="1cm">- in paragraph 2., where the phrase “…at least 10 days before the effective date of cancellation.” appears in</fo:block>
					 <fo:block text-align="left" font-size="10px" start-indent="1.3cm">the second sentence, such phrase is deleted and replaced with the phrase “… at least 15 days before the effective date of cancellation.”</fo:block>
                     
                     
                     
                     <fo:block text-align="left" font-size="10px" start-indent="1cm">- paragraph #3 is deleted and replaced with the following:</fo:block>  
                      <fo:block margin-top="6mm"/>
                     <fo:block text-align="left" font-size="10px" start-indent="1cm">3. This policy may be non-renewed by the <fo:inline font-weight="bold" font-style="italic">Insurer</fo:inline> by delivering to the<fo:inline font-weight="bold" font-style="italic"> Named Insured </fo:inline>, or by mailing to</fo:block>
                      <fo:block text-align="left" font-size="10px" start-indent="1.3cm">the<fo:inline font-weight="bold" font-style="italic"> Named Insured </fo:inline>at its last known address, and to any mortgagee or loss payee at their addresses in the policy, or if their addresses are not indicated, the last known addresses, written notice of such nonrenewal, including the reason 	therefore, at least forty five days prior to the expiration of the <fo:inline font-weight="bold" font-style="italic">Policy Period</fo:inline>, or prior to the anniversary date if the policy has been written for more than one year or for an indefinite term. The proof of mailing such notice shall be sufficient proof of notice.</fo:block>
                     <fo:block margin-top="6mm"/> 
                     <fo:block text-align="left" font-size="10px" start-indent="1.3cm">The <fo:inline font-weight="bold" font-style="italic">Insurer</fo:inline> shall not be required to deliver or send such notice if the <fo:inline font-weight="bold" font-style="italic"> Named Insured </fo:inline> has insured property covered 
                   		under this policy under another insurance policy, accepted replacement coverage or requested or agreed to non renewal of this policy.</fo:block>  
           		 	<fo:block margin-top="10mm"/> 
            
              	 <fo:block text-align="left" font-size="10px" >2. In the event  Section III, Extensions, paragraph D., Extended Reporting Periods Extension, is effected, then within 45</fo:block>
              	   <fo:block text-align="left" font-size="10px" start-indent="0.3cm">days of receipt of a request by the<fo:inline font-weight="bold" font-style="italic"> Named Insured </fo:inline>for <fo:inline font-weight="bold" font-style="italic">Claims</fo:inline> information, the <fo:inline font-weight="bold" font-style="italic">Insurer</fo:inline> shall provide the Insured with the following information for the last three years of insurance purchased from the <fo:inline font-weight="bold" font-style="italic">Insurer</fo:inline>:</fo:block>
			
              	 <fo:block margin-top="6mm"/>  
                       
				<fo:block text-align="left" font-size="10px" start-indent="1.3cm">- aggregate information on total closed <fo:inline font-weight="bold" font-style="italic">Claims</fo:inline>, including date and description of circumstances and any paid</fo:block>
				<fo:block text-align="left" font-size="10px" start-indent="1.5cm">losses</fo:block>
                <fo:block text-align="left" font-size="10px" start-indent="1.3cm">- aggregate information on total open <fo:inline font-weight="bold" font-style="italic">Claims</fo:inline>, including date and description of circumstances and any amounts </fo:block>
                 <fo:block text-align="left" font-size="10px" start-indent="1.5cm">of payment</fo:block>

				<fo:block text-align="left" font-size="10px" start-indent="1.3cm">- information on any notice given to the <fo:inline font-weight="bold" font-style="italic">Insurer</fo:inline> in respect of circumstance which could give rise to a Claim, including date and description of such circumstances.</fo:block>
                     
                <fo:block margin-top="10mm"/>  
                 <fo:block text-align="left" font-size="10px" >3. Item 7. of the Declarations, Optional Extended Reporting Period:</fo:block> 	 
                 <fo:block text-align="left" font-size="10px" start-indent="1.3cm">(a) shall read: (i) 12 months (ii) 24 months</fo:block>	 
                 <fo:block text-align="left" font-size="10px" start-indent="1.3cm">(b) shall read: (i) 100% (ii) not more than 200%</fo:block>		 
                 	 
                 	 
                 	 
                <fo:block margin-top="27mm"/>
                
                <fo:block  font-size="10px"   text-align="center">Continued on Page 2</fo:block>
                
                <fo:block margin-top="14mm"/>
				<fo:block  font-size="10px"  text-indent="2cm" color="grey" text-align="left"><xsl:value-of select="response/policyformfooter_freeform_01/data/APLAMENDNC2009" /></fo:block>
									
				 <fo:block break-after="page" /> 
                 	 
       

			<fo:block text-align="center" font-size="12px" >THIS ENDORSEMENT CHANGES THE POLICY, READ IT CAREFULLY.</fo:block>
			<fo:block margin-top="10mm"/>

 			<fo:block text-align="center" font-size="12px" >Page 2 of 2</fo:block>
			<fo:block margin-top="4mm"/>
			<fo:block text-align="left" font-size="10px" >4. Section II., Limits of Liability and Deductible, A. Limit of Liability, 1., 2. and 6. are deleted and replaced with the following:</fo:block>
			<fo:block margin-top="4mm"/>

			<fo:block text-align="left" font-size="10px" start-indent="2cm">1.  Subject to 2. that follows, the <fo:inline font-weight="bold" font-style="italic">Insurer's</fo:inline> Limit of Liability for <fo:inline font-weight="bold" font-style="italic">Damages</fo:inline> and <fo:inline font-weight="bold" font-style="italic">Defense Expenses</fo:inline> combined</fo:block>
            <fo:block text-align="left" font-size="10px" start-indent="2.3cm">for each <fo:inline font-weight="bold" font-style="italic">Claim</fo:inline> reported to the <fo:inline font-weight="bold" font-style="italic">Insurer</fo:inline> during the <fo:inline font-weight="bold" font-style="italic">Policy Period</fo:inline> shall not exceed the amount shown in Item 3. of the Declarations under  “each <fo:inline font-weight="bold" font-style="italic">Claim</fo:inline>”.</fo:block>

			<fo:block text-align="left" font-size="10px" start-indent="2cm">2.  Subject to 1. above, the <fo:inline font-weight="bold" font-style="italic">Insurer's</fo:inline> Limit of Liability for <fo:inline font-weight="bold" font-style="italic">Damages</fo:inline> and <fo:inline font-weight="bold" font-style="italic">Defense Expenses</fo:inline> combined, for all</fo:block> 
			
			<fo:block text-align="left" font-size="10px" start-indent="2.3cm"><fo:inline font-weight="bold" font-style="italic">Claims</fo:inline> reported during the <fo:inline font-weight="bold" font-style="italic">Policy Period</fo:inline> shall not exceed the amount shown in Item 3. of the Declarations as Policy Aggregate.</fo:block>

			<fo:block text-align="left" font-size="10px" start-indent="2cm">3. The Limit of Liability available for <fo:inline font-weight="bold" font-style="italic">Claims</fo:inline> first made against the Insured during the Optional Extended</fo:block>
			<fo:block text-align="left" font-size="10px" start-indent="2.3cm">Reporting Period, if purchased, shall not exceed the amounts as stated above in 1. and 2.</fo:block>
			
			<fo:block margin-top="8mm"/>

			<fo:block text-align="left" font-size="10px" >5. Section VI. General Conditions, K. Changes, is deleted and replaced with the following:</fo:block>
			<fo:block margin-top="4mm"/>
			<fo:block text-align="left" font-size="10px" start-indent="2cm"><fo:inline font-weight="bold" font-style="italic"> N.</fo:inline> Notices of changes given by the<fo:inline font-weight="bold" font-style="italic"> Named Insured </fo:inline>to its agent, absent of fraud by the<fo:inline font-weight="bold" font-style="italic"> Named Insured </fo:inline>or its agent, shall be binding upon the <fo:inline font-weight="bold" font-style="italic">Insurer</fo:inline>.
			</fo:block>


				       <fo:block margin-top="130mm"/>
				    
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
			  <fo:block margin-top="13mm"/>
			  <fo:block  font-size="10px"  text-indent="2cm" color="grey" text-align="left"><xsl:value-of select="response/policyformfooter_freeform_01/data/APLAMENDNC2009" /></fo:block>


     </xsl:template>
</xsl:stylesheet>
