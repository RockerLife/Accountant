<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.1"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:fo="http://www.w3.org/1999/XSL/Format"
	exclude-result-prefixes="fo"
	xmlns:fn="http://www.w3.org/2005/02/xpath-functions"
	xmlns:java="java">

	<xsl:template match="/" name="APL-State-KS" >
		
		
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
					               			 <fo:block font-weight="bold" font-size="10px"  text-align="left">Endorsement No: <xsl:value-of select="response/endorsements_freeform_01/data/APLAMENDKS2009" /></fo:block>
					               		</fo:table-cell>
					    		  </fo:table-row>
					    		 
					     </fo:table-body>
				     </fo:table>	   
				</fo:block>
				    
   <fo:block space-after=".1in" text-align-last="justify">
    <fo:leader leader-pattern="rule" />
   </fo:block>
				   
				      <fo:block margin-top="12mm"/>
				     <fo:block text-align="center" font-weight="bold" font-size="10px"  > KANSAS AMENDATORY ENDORSEMENT</fo:block>
				     <fo:block margin-top="8mm"/>
				     
				     
				     <fo:block text-align="left" font-size="10px" >1) This policy may be canceled by the <fo:inline font-weight="bold" font-style="italic"> Insurer </fo:inline>only for these reasons:</fo:block>
				     
				     <fo:block text-align="left" font-size="10px" start-indent="2cm" >(a)  for non payment of the premium or of the deductible by the <fo:inline font-weight="bold" font-style="italic"> Named Insured </fo:inline>, or</fo:block>
				     
				     <fo:block text-align="left" font-size="10px" start-indent="2cm" >(b)  if a determination by the Commissioner of the Kansas Department of Insurance that continuation of coverage could place the <fo:inline font-weight="bold" font-style="italic"> Insurer </fo:inline>in a hazardous financial condition or in violation of the laws of the state of Kansas, or</fo:block>
				     
				     <fo:block text-align="left" font-size="10px" start-indent="2cm" >(c)  a determination by the Commissioner of the Kansas Department of Insurance that the <fo:inline font-weight="bold" font-style="italic"> Insurer </fo:inline>no longer has adequate reinsurance to meet the <fo:inline font-weight="bold" font-style="italic"> Insurer </fo:inline>needs </fo:block>
				     <fo:block margin-top="4mm"/>
				     <fo:block text-align="left" font-size="10px">However, VI.,General Conditions, D. Cancellation and Nonrenewal, paragraph #3 is deleted and replaced with the following:</fo:block>
				     <fo:block margin-top="4mm"/>
				     <fo:block text-align="left" font-size="10px" start-indent="2cm" >3. This policy may be non-renewed by the<fo:inline font-weight="bold" font-style="italic"> Insurer </fo:inline>by delivering to the <fo:inline font-weight="bold" font-style="italic"> Named Insured </fo:inline>or by mailing to the <fo:inline font-weight="bold" font-style="italic"> Named Insured </fo:inline>, at its last known address,  written notice of  the <fo:inline font-weight="bold" font-style="italic"> Insurer's </fo:inline> intent not to renew the policy at least sixty days prior to the expiration of the <fo:inline font-weight="bold" font-style="italic"> Policy Period </fo:inline>.  Said notice must state the specific reasons why the policy is being non-renewed. The proof of mailing of such notice shall be sufficient proof of notice. The <fo:inline font-weight="bold" font-style="italic"> Insurer </fo:inline> may satisfy this obligation by causing such notice to be given by a licensed agent.</fo:block>
				     <fo:block margin-top="4mm"/>
				     <fo:block text-align="left" font-size="10px" >2) Section VI., General Conditions, H.,  Authorization and Sole Agent, is deleted and replaced with the following:</fo:block>
				     <fo:block margin-top="4mm"/>
				     <fo:block text-align="left" font-size="10px" start-indent="2cm" >P. Authorization and Sole Agent</fo:block>
				     <fo:block margin-top="4mm"/>
				     <fo:block text-align="left" font-size="10px" start-indent="2cm" >The <fo:inline font-weight="bold" font-style="italic"> Named Insured </fo:inline>will be the sole agent and will be authorized to act on behalf of all <fo:inline font-weight="bold" font-style="italic"> Insureds</fo:inline> for the purpose
                     of giving or receiving any notices, any amendments to or cancellation of this policy, for the completing of any
                     applications and the making of any statements or representations for the policy, for the payment of the deductible
                     and the exercising or declining to exercise any right under this policy, including the purchase of any Extended
                     Reporting Period.</fo:block>
				      
				     
				     
				       <fo:block margin-top="70mm"/>
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
		       <fo:block  font-size="10px"  text-indent="2cm" color="grey" text-align="left"><xsl:value-of select="response/policyformfooter_freeform_01/data/APLAMENDKS2009" /></fo:block>
			 	
     </xsl:template>
</xsl:stylesheet>
