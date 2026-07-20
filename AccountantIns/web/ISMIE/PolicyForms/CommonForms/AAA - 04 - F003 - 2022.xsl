<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet version="1.1"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:fo="http://www.w3.org/1999/XSL/Format"
	exclude-result-prefixes="fo"
	xmlns:fn="http://www.w3.org/2005/02/xpath-functions"
	xmlns:java="java">
	
	<xsl:template match="/" name="AAA04F003">
		
				     <fo:block>
				<xsl:call-template name="CommonHeader" />
			</fo:block>
				      <fo:block margin-top="12mm"/>
				     <fo:block text-align="center" font-weight="bold" font-size="10px"  >AGGREGATE DEDUCTIBLE FOR ALL <fo:inline font-style="italic">CLAIMS</fo:inline></fo:block>
				     <fo:block margin-top="8mm"/>
				    
				     <fo:block text-align="left"  font-size="10px" >In consideration of the premium charged it is agreed that:</fo:block>
				     <fo:block margin-top="4mm"/>
				     <fo:block text-align="left" font-size="10px" >(1)  Item 4 of the Declarations, Deductible, is deleted and replaced with the following amended Item 4:</fo:block>
				     <fo:block margin-top="4mm"/>
				     <fo:block text-align="left" text-indent="1.5cm" font-size="10px" >Item 4. Deductible</fo:block>
				      <fo:block text-align="left" text-indent="2cm" font-size="10px" ><xsl:value-of select="response/policycoverage_freeform_01/data/DeductibleAmount"/> Aggregate Deductible for all <fo:inline font-weight="bold" font-style="italic">Claims</fo:inline></fo:block>
				      <fo:block text-align="left" text-indent="1.5cm" font-size="10px" >This amount includes <fo:inline font-weight="bold" font-style="italic">Defense Expenses</fo:inline> unless this Section is amended by specific endorsement of this</fo:block>
				      <fo:block text-align="left" text-indent="1.5cm" font-size="10px" >policy.</fo:block>
				      
				      <fo:block margin-top="4mm"/>
				      <fo:block text-align="left" font-size="10px" >(2) Section II of the policy, Limits of Liability and Deductible, Paragraph B, Deductible, is deleted and replaced with the following Paragraph B.</fo:block>
				      <fo:block margin-top="2mm"/>
				      <fo:block text-align="left" font-size="10px" text-indent="1.5cm" >B. Deductible</fo:block>
				       <fo:block margin-top="2mm"/>
				        <fo:block text-align="left" font-size="10px" start-indent="1.5cm" >The <fo:inline font-weight="bold" font-style="italic">Insurer</fo:inline> shall be liable for amounts payable under this policy for <fo:inline font-weight="bold" font-style="italic">Damages</fo:inline> or <fo:inline font-weight="bold" font-style="italic">Defense Expenses</fo:inline> which are in excess of the Deductible amount shown in Item 4 in the Declarations. The Deductible amount shall be borne by the <fo:inline font-weight="bold" font-style="italic">Insured</fo:inline> and shall remain uninsured. The Deductible amount applies to the payment of <fo:inline font-weight="bold" font-style="italic">Damages</fo:inline> and <fo:inline font-weight="bold" font-style="italic">Defense Expenses</fo:inline>, unless amended by specific endorsement of this policy.  If the <fo:inline font-weight="bold" font-style="italic">Insurer</fo:inline> advances any amounts within the Deductible, the <fo:inline font-weight="bold" font-style="italic">Named Insured</fo:inline> shall reimburse the <fo:inline font-weight="bold" font-style="italic">Insurer</fo:inline> within thirty days of the <fo:inline font-weight="bold" font-style="italic">Insurer’s</fo:inline> request to do so.  In the event of <fo:inline font-weight="bold" font-style="italic">Related Claims</fo:inline>, a single Deductible amount will apply.</fo:block> 
				       
				         
				         
				        <fo:block margin-top="20mm"/>
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
				       
		       <fo:block margin-top="80mm"/>
               <fo:block  font-size="10px" color="grey" text-align="left">ISMIE AAA-04-F003 (04012022)
	       	 		<fo:block margin-top="-0.5cm" font-size="10px" color="grey" text-align="right">Page 1 of 1</fo:block>
	       	 	</fo:block>
				    
     </xsl:template>
</xsl:stylesheet>




					
				    	