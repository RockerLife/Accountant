<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="1.1"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:fo="http://www.w3.org/1999/XSL/Format"
	exclude-result-prefixes="fo"
	xmlns:fn="http://www.w3.org/2005/02/xpath-functions"
	xmlns:java="java">
  <xsl:template match="/" name="AAA04F043">
           <fo:block>
				<xsl:call-template name="CommonHeader" />
			</fo:block>
				   
				      <fo:block margin-top="12mm"/>
				     <fo:block text-align="center" font-weight="bold" font-size="10px"  >NEW JERSEY AMENDATORY ENDORSEMENT</fo:block>
				     <fo:block margin-top="8mm"/>
				    
				   
				     <fo:block text-align="left" font-size="10px" >This policy may not be canceled by the <fo:inline font-weight="bold" font-style="italic"> Insurer </fo:inline> for any reason at any time except for non payment of the premium or deductible by the<fo:inline font-weight="bold" font-style="italic"> Named Insured</fo:inline>. However, VI., General Conditions, D. Cancellation and Nonrenewal, paragraph #3 is deleted and replaced with the following:</fo:block>
				     <fo:block margin-top="4mm"/>
				     <fo:block text-align="left" font-size="10px" start-indent="2cm" >3. This policy may be non-renewed by the <fo:inline font-weight="bold" font-style="italic"> Insurer </fo:inline>by delivering to the <fo:inline font-weight="bold" font-style="italic"> Named Insured</fo:inline>, or by first class mailing to the 

				    <fo:inline font-weight="bold" font-style="italic"> Named Insured </fo:inline> at its last known address written notice of such non renewal including the reason, at least thirty days but not more than 120 days prior to the expiration of the <fo:inline font-weight="bold" font-style="italic">Policy Period</fo:inline>, or prior to the anniversary date if the policy is written without a fixed expiration date. The proof of mailing such notice shall be sufficient proof of such notice. The <fo:inline font-weight="bold" font-style="italic">Insurer</fo:inline> shall not be required to deliver or mail such notice if the <fo:inline font-weight="bold" font-style="italic"> Named Insured </fo:inline> has replaced coverage elsewhere or has requested termination.</fo:block>
				    
				    
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
         <fo:block margin-top="20mm"/>
                   <fo:block  font-size="10px" color="grey" text-align="left">ISMIE AAA-04-F043 (04012022)
			       	 <fo:block margin-top="-0.5cm" font-size="10px" color="grey" text-align="right">Page 1 of 1</fo:block>
			       	 </fo:block>
          
  </xsl:template>
  
</xsl:stylesheet>
