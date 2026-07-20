<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.1"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:fo="http://www.w3.org/1999/XSL/Format"
	exclude-result-prefixes="fo"
	xmlns:fn="http://www.w3.org/2005/02/xpath-functions"
	xmlns:java="java">

	<xsl:template match="/" name="APL-OH-ERP" >
		
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
					               			 <fo:block font-weight="bold" font-size="10px"  text-align="left">Endorsement No: APL 100 - 106 -2009</fo:block>
					               		</fo:table-cell>
					    		  </fo:table-row>
					    		 
					     </fo:table-body>
				     </fo:table>	   
				</fo:block>
				    
   <fo:block space-after=".1in" text-align-last="justify">
    <fo:leader leader-pattern="rule" />
   </fo:block>
				   
				      <fo:block margin-top="12mm"/>
				     <fo:block text-align="center" font-weight="bold" font-size="10px"  > EFFECT THE EXTENDED REPORTING PERIOD PER PARAGRAPH D OF SECTION III, FORM APL- 102 -2009 </fo:block>
				     <fo:block margin-top="8mm"/>
				     
				     
				      <fo:block text-align="left" font-size="10px" >In consideration of an additional premium of  $ ________(A)_____________ being _____(B)_____ % of the annual premium of  this policy of insurance, the Extended Reporting Period as detailed in paragraph D of Section III of this policy is in full force and effect for a period of ______(C)_________  months,  commencing on ________(D)___________ .</fo:block>
				     
				     <fo:block margin-top="8mm"/>
				     
				     <fo:block text-align="left" font-size="10px" >(A) STATE THE PREMIUM REQUIRED</fo:block>
				      <fo:block text-align="left" font-size="10px" >(B) STATE % THAT THE PREMIUM IN (A) REPRESENTS OF THE ANNUAL PREMIUM,FOR THE APPROPRIATE LENGTH OFTHE EXTENDED REPORTING PERIOD </fo:block>
				      <fo:block text-align="left" font-size="10px" >(C) STATE LENGTH OF EXTENDED REPORTING PERIOD</fo:block>
				      <fo:block text-align="left" font-size="10px" >(D) STATE DATE EXTENDED REPORTING PERIOD STARTS</fo:block>
				     
				     
				     
				     
				     
				       <fo:block margin-top="130mm"/>
				      <fo:block text-align="left" font-size="10px" >All other terms, conditions and limitations of the policy remain unaltered.</fo:block>
				      <fo:block margin-top="4mm"/>
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
				     
				     
				       <fo:block margin-top="10mm"/>
			  <fo:block  font-size="10px"  text-indent="2cm" color="grey" text-align="left">APL 100 - 106 -2009</fo:block>
			 	 
     </xsl:template>
</xsl:stylesheet>
