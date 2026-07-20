<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.1"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:fo="http://www.w3.org/1999/XSL/Format"
	exclude-result-prefixes="fo"
	xmlns:fn="http://www.w3.org/2005/02/xpath-functions"
	xmlns:java="java">

	<xsl:template match="/" name="APL-State-VT" >
	
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
					               			 <fo:block font-weight="bold" font-size="10px"  text-align="left">Endorsement No: <xsl:value-of select="response/endorsements_freeform_01/data/VTAMENDAPL1209" /></fo:block>
					               		</fo:table-cell>
					    		  </fo:table-row>
					    		 
					     </fo:table-body>
				     </fo:table>	   
				</fo:block>
				    
   <fo:block space-after=".1in" text-align-last="justify">
    <fo:leader leader-pattern="rule" />
   </fo:block>
				   
				     <fo:block margin-top="12mm"/>
				     <fo:block text-align="center" font-weight="bold" font-size="10px" >VERMONT AMENDATORY ENDORSEMENT</fo:block>
				     <fo:block margin-top="4mm"/>
				     <fo:block text-align="center" font-weight="bold" font-size="10px" >Part A:</fo:block>
					 <fo:block margin-top="4mm"/>
					 <fo:block text-align="center" font-weight="bold" font-size="10px" >DEFENSE EXPENSES IN ADDITION TO THE LIMIT OF LIABILITY</fo:block>
					 <fo:block margin-top="8mm"/>
					 
					 
				     <fo:block text-align="left" font-size="10px" >This Part A amends the limit of liability so that<fo:inline font-weight="bold" font-style="italic"> Defense Expenses </fo:inline>are paid in addition to and equal to the Limit of Liability available to pay<fo:inline font-weight="bold" font-style="italic"> Damages </fo:inline>.</fo:block>
				     <fo:block margin-top="4mm"/>

				     <fo:block text-align="left" font-size="10px">In consideration of the premium charged it is agreed that:</fo:block>
														
                     <fo:block margin-top="4mm"/> 
                    
                     <fo:block text-align="left" font-size="10px">1- The last sentence of the Preamble appearing above Item 1 of the Declarations is deleted.</fo:block>
                    
                     <fo:block margin-top="4mm"/> 
                  	 <fo:block text-align="left" font-size="10px">2. Item 3 of the Declarations, Limit of Liability, is deleted and amended to read:</fo:block>
				 	 <fo:block margin-top="4mm"/> 
				  
				  	 <fo:block text-align="left" font-size="10px"><xsl:value-of select="response/policycoverage_freeform_01/data/AggregateLimitText" /><fo:external-graphic src="../AccountantIns/image/spacer.png"/><fo:inline font-weight="bold" font-style="italic">Damages</fo:inline>, each <fo:inline font-weight="bold" font-style="italic">Claim</fo:inline>;</fo:block>
				   	 <fo:block text-align="left" font-size="10px"><xsl:value-of select="response/policycoverage_freeform_01/data/AggregateLimitText" /><fo:external-graphic src="../AccountantIns/image/spacer.png"/><fo:inline font-weight="bold" font-style="italic">Defense Expenses</fo:inline>, each <fo:inline font-weight="bold" font-style="italic">Claim</fo:inline>;</fo:block>
					 <fo:block margin-top="4mm"/> 
				 
				 	 <fo:block text-align="left" font-size="10px"><xsl:value-of select="response/policycoverage_freeform_01/data/OccuranceLimitText" /><fo:external-graphic src="../AccountantIns/image/spacer.png"/><fo:inline font-weight="bold" font-style="italic">Damages Policy Aggregate</fo:inline>;</fo:block>
				  	<fo:block text-align="left" font-size="10px"><xsl:value-of select="response/policycoverage_freeform_01/data/OccuranceLimitText" /><fo:external-graphic src="../AccountantIns/image/spacer.png"/><fo:inline font-weight="bold" font-style="italic">Defense Expenses Policy Aggregate</fo:inline>.</fo:block>
				  
				   	<fo:block margin-top="4mm"/> 
				   <fo:block text-align="left" font-size="10px">3. Section II of the Policy, Limits of Liability and Deductible, Paragraph A, Limits of Liability, is amended as follows:</fo:block>
				   <fo:block margin-top="4mm"/> 
				  <fo:block text-align="left" font-size="10px" start-indent="2cm">-  the word “combined” is deleted where it appears in 1. and 2.;</fo:block>
				  <fo:block text-align="left" font-size="10px" start-indent="2cm">-  3. is deleted in its entirety;</fo:block>
				  <fo:block margin-top="10mm"/> 
				   
				   <fo:block text-align="center" font-weight="bold" font-size="10px" >Part B:</fo:block>			  
				   <fo:block text-align="center" font-weight="bold" font-size="10px" >OTHER POLICY CHANGES</fo:block>
				   <fo:block margin-top="4mm"/> 
				   <fo:block text-align="left" font-size="10px">1. Section III, Extensions, Paragraph B., Spousal and Domestic Partner Extension, is amended by the addition of the following sentence:</fo:block>
				   <fo:block margin-top="4mm"/>
				   <fo:block text-align="left" font-size="10px" start-indent="2cm">The term spouse and the term domestic partner shall include parties to a civil union under Vermont law.</fo:block>
				    
				    <fo:block margin-top="4mm"/>
				   <fo:block text-align="left" font-size="10px">2. Section VI., General Conditions, Paragraph D. Cancellation and Non Renewal is amended as follows:</fo:block>
				   <fo:block margin-top="4mm"/>
				   <fo:block text-align="left" font-size="10px" start-indent="2cm">-  Paragraph 2 is amended by deletion of the phrase in the second sentence “… at least 10 days before the</fo:block>
				   
				  <fo:block text-align="left" font-size="10px" start-indent="2.2cm">effective date of cancellation…”,  and replacing it with the phrase “… at least 15 days before the effective date of cancellation…”.</fo:block>
				   <fo:block margin-top="4mm"/>
				   
				   <fo:block text-align="left" font-size="10px" start-indent="2cm">- Paragraph 3 is deleted in its entirety and replaced with the following Paragraph 3.:</fo:block>
				   
				   <fo:block text-align="left" font-size="10px" start-indent="4cm">This policy may be nonrenewed by the<fo:inline font-weight="bold" font-style="italic"> Insurer </fo:inline>by certified mailing to the address shown in Item. 1 of the Declarations notice of such non renewal, including the reasons for such nonrenewal, at least 45 days prior to the expiration date of the <fo:inline font-weight="bold" font-style="italic"> Policy Period </fo:inline>.</fo:block>
				   
				   <fo:block margin-top="20mm"/>
				   
				    <fo:table  text-align="center">
					    <fo:table-column column-width = "70mm" />
				        <fo:table-column column-width = "90mm" />				        
				         <fo:table-body>
				           <fo:table-row>
					                   <fo:table-cell padding-left="4pt"><fo:block  font-size="10px"  text-align="left">Page 1 of 2</fo:block></fo:table-cell>
					                   <fo:table-cell  padding-left="4pt"><fo:block   font-size="10px"  text-align="right">continued on Page 2</fo:block></fo:table-cell>
					       </fo:table-row>				         
				         </fo:table-body>    
				    </fo:table>
				    <fo:block margin-top="8mm"/>
				    <fo:block  font-size="10px"  text-indent="0.9cm" color="grey" text-align="left"><xsl:value-of select="response/policyformfooter_freeform_01/data/VTAMENDAPL1209" /></fo:block>
				   
				   <fo:block break-after="page" /> 
				  
				   <fo:block text-align="center" font-size="12px" >THIS ENDORSEMENT CHANGES THE POLICY, READ IT CAREFULLY.</fo:block>
				  
				  <fo:block margin-top="12mm"/>
				  
				  
				  <fo:block text-align="left" font-size="10px">3. Section II., Limits of Liability and Deductible,  Part A., Limits of Liability, Item 6. is deleted, and replaced with the following:</fo:block>				  
				   <fo:block margin-top="4mm"/>
				   
				   <fo:block text-align="left" font-size="10px" start-indent="2cm">6.  Not withstanding Items 1 and 2 of the Section II A., the Limit of Liability granted under Section III, Extensions, Paragraph D. 2., Optional Extended Reporting Period, shall be equal to the limit of Liability shown in Item 3. of the Declarations.</fo:block>
				   <fo:block margin-top="4mm"/>
				   <fo:block text-align="left" font-size="10px">4. Section II., Limits of Liability and Deductible, Paragraph B., Deductible, is amended by deletion of the second to last sentence and replacing it with the following sentence:</fo:block>
				   
				    <fo:block margin-top="4mm"/>
				   <fo:block text-align="left" font-size="10px" start-indent="2cm">If the<fo:inline font-weight="bold" font-style="italic"> Insurer </fo:inline>advances any amounts within the Deductible, the<fo:inline font-weight="bold" font-style="italic"> Named Insured </fo:inline>shall reimburse the<fo:inline font-weight="bold" font-style="italic"> Insurer </fo:inline>within thirty days if such advancement was with respect of<fo:inline font-weight="bold" font-style="italic"> Defense Expenses </fo:inline>; if such advancement is with respect to<fo:inline font-weight="bold" font-style="italic"> Damages </fo:inline>, such reimbursement shall be due the <fo:inline font-weight="bold" font-style="italic"> Insurer </fo:inline> upon payment of such <fo:inline font-weight="bold" font-style="italic"> Damages </fo:inline>.</fo:block>
				   
				       <fo:block margin-top="140mm"/>
				    
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
				
				<fo:block margin-top="14mm"/>
				<fo:table  text-align="center">
					    <fo:table-column column-width = "80mm" />
				        <fo:table-column column-width = "80mm" />				        
				         <fo:table-body>
				           <fo:table-row>
					                   <fo:table-cell padding-left="4pt"><fo:block  font-size="10px"  text-align="left">Page 2 of 2</fo:block></fo:table-cell>
					                   <fo:table-cell  padding-left="4pt"><fo:block   font-size="10px"  text-align="right"></fo:block></fo:table-cell>
					       </fo:table-row>				         
				         </fo:table-body>    
				    </fo:table>
				
			  <fo:block margin-top="7mm"/>
			  <fo:block  font-size="10px"  text-indent="0.9cm" color="grey" text-align="left"><xsl:value-of select="response/policyformfooter_freeform_01/data/VTAMENDAPL1209" /></fo:block>
		  
		 
     </xsl:template>
</xsl:stylesheet>
