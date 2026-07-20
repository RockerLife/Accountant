<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet version="1.1"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:fo="http://www.w3.org/1999/XSL/Format"
	exclude-result-prefixes="fo"
	xmlns:fn="http://www.w3.org/2005/02/xpath-functions"
	xmlns:java="java">

	<xsl:template match="/" name="Quoteletter">
		
				<xsl:variable name="checkButton" select="response/checkButtonPath"></xsl:variable>
				<xsl:variable name="spaceBar" select="response/spaceBarPath"></xsl:variable>
				<xsl:variable name="protexureAccountantsLogo" select="response/protexureAccountantsLogoPath"></xsl:variable>
				<xsl:variable name="headerBottom" select="response/headerBottomPath"></xsl:variable>
					<fo:block margin-top="5mm">
					<fo:table>
				         <fo:table-column/>
				         <fo:table-body>
					           <fo:table-row>
					             <fo:table-cell padding-top="2mm" padding-left="4pt"><fo:block font-size="11px" font-family="Arial" >Insured: <xsl:value-of select="response/policy_freeform_01/data/AccountName" /></fo:block></fo:table-cell>
					             <!-- <fo:table-cell border="0.5pt solid black" padding-left="4pt"><fo:block font-size="11px" font-family="Arial" ><xsl:value-of select="response/policy_freeform_01/data/AccountName" /></fo:block></fo:table-cell> -->
					           </fo:table-row>
					           <fo:table-row>
					             <fo:table-cell padding-top="2mm" padding-left="4pt"><fo:block font-size="11px" font-family="Arial" >Policy Period: <xsl:value-of select="response/policy_freeform_01/data/PolicyEffectiveDate" />  -  <xsl:value-of select="response/address_freeform_01/data/PolicyExpirationDate" /></fo:block></fo:table-cell>
					             <!-- <fo:table-cell border="0.5pt solid black" padding-left="4pt"><fo:block font-size="11px" font-family="Arial" ><xsl:value-of select="response/policy_freeform_01/data/PolicyEffectiveDate" />  -  <xsl:value-of select="response/address_freeform_01/data/PolicyExpirationDate" /></fo:block></fo:table-cell> -->
					           </fo:table-row>
					           <fo:table-row>
					             <fo:table-cell padding-top="2mm" padding-left="4pt"><fo:block font-size="11px" font-family="Arial" >Quotation # <xsl:value-of select="response/policy_freeform_01/data/QuoteNumber" /></fo:block></fo:table-cell>
					             <!-- <fo:table-cell border="0.5pt solid black" padding-left="4pt"><fo:block font-size="11px" font-family="Arial" ><xsl:value-of select="response/policy_freeform_01/data/QuoteNumber" /></fo:block></fo:table-cell> -->
					           </fo:table-row>
					           
					           <fo:table-row>
					             <fo:table-cell padding-top="2mm" padding-left="4pt">
					             	<fo:block font-size="11px" font-family="Arial" >					             				
					             		Prior Acts<xsl:if test="response/professionalliabilityinsdetail_freeform_01/data/IsPriorActDateFull= 'N'"> Date</xsl:if><xsl:if test="response/policy_freeform_01/data/IsFirmCarryingProfLiabilityIns != 'Y'"> Date</xsl:if>:&#160; 						   					             	
					             	<xsl:if test="response/policy_freeform_01/data/IsFirmCarryingProfLiabilityIns= 'Y'">
						             		<xsl:if test="response/professionalliabilityinsdetail_freeform_01/data/IsPriorActDateFull= 'Y'"> 
										       No Limitation										       
										    </xsl:if>
										    <xsl:if test="response/professionalliabilityinsdetail_freeform_01/data/IsPriorActDateFull= 'N'"> 
										        <xsl:value-of select="response/professionalliabilityinsdetail_freeform_01/data/PriorActDatePross" /> 
										    </xsl:if>	
									    </xsl:if>	
									    <xsl:if test="response/policy_freeform_01/data/IsFirmCarryingProfLiabilityIns != 'Y'">
									    	<xsl:value-of select="response/professionalliabilityinsdetail_freeform_01/data/PriorActDatePross" />
									    </xsl:if>
					             	</fo:block>
					             </fo:table-cell>
					             <!-- <fo:table-cell border="0.5pt solid black" padding-left="4pt">
					             	<fo:block font-size="11px" font-family="Arial" >
					             		<xsl:if test="response/policy_freeform_01/data/IsFirmCarryingProfLiabilityIns= 'Y'">
						             		<xsl:if test="response/professionalliabilityinsdetail_freeform_01/data/IsPriorActDateFull= 'Y'"> 
										       No Limitation										       
										    </xsl:if>
										    <xsl:if test="response/professionalliabilityinsdetail_freeform_01/data/IsPriorActDateFull= 'N'"> 
										        <xsl:value-of select="response/professionalliabilityinsdetail_freeform_01/data/PriorActDatePross" /> 
										    </xsl:if>	
									    </xsl:if>	
									    <xsl:if test="response/policy_freeform_01/data/IsFirmCarryingProfLiabilityIns != 'Y'">
									    	<xsl:value-of select="response/professionalliabilityinsdetail_freeform_01/data/PriorActDatePross" />
									    </xsl:if>								    
					             	</fo:block>
					             </fo:table-cell> -->					             
					           </fo:table-row>
					           
					           <fo:table-row>
					             <fo:table-cell padding-top="2mm" padding-left="4pt"><fo:block font-size="11px" font-family="Arial" >This quotation expires on: <xsl:value-of select="response/policy_freeform_01/data/QuoteExpiryDate" /></fo:block></fo:table-cell>
					             <!-- <fo:table-cell  padding-left="4pt"><fo:block font-size="11px" font-family="Arial" ><xsl:value-of select="response/policy_freeform_01/data/QuoteExpiryDate" /></fo:block></fo:table-cell> -->
					           </fo:table-row>
					           
					           
						</fo:table-body>
					</fo:table>
					</fo:block>
					
					<fo:block >					
					<xsl:if test="response/quoteOpted_list_01/* or response/quoteOpted_list_01/text()">
						<fo:table margin-top="5mm">
					         <fo:table-column column-width = "185mm" />	    		         
							 
							  <fo:table-body>
							 							  
					         	<xsl:for-each select="response/quoteOpted_list_01/data">			         		
					         		<fo:table-row>
					         			<fo:table-cell >
						         			<fo:block>					         			
							         			<xsl:if test="position()mod 6=0">
												   <fo:block break-after="page" />
												</xsl:if>
											</fo:block>
										</fo:table-cell>
					         		</fo:table-row>
					         	   	<fo:table-row>				
										<fo:table-cell >
											<fo:block margin-top="5mm">
												<fo:table border="0.5pt solid black">
											         <fo:table-column column-width = "50mm" />
											         <fo:table-column column-width = "135mm" />
											         <fo:table-body>
											         		<!-- 
											         	   <fo:table-row>
												             <fo:table-cell number-columns-spanned="2">
												             	<fo:block font-size="11px" font-family="Arial" >
												             		<xsl:choose>
														                    <xsl:when test="IsThisOptionFinalised='Y'">
														                     Accept: Yes
							        								        </xsl:when>
														                    <xsl:otherwise></xsl:otherwise>
														         	</xsl:choose>
												             	</fo:block>
												             </fo:table-cell>
												           </fo:table-row>
												            -->
												           <fo:table-row>
												             <fo:table-cell number-columns-spanned="2" padding-left="4pt" >
												             <fo:block text-align="left" font-size="11px" font-weight="bold">ACCEPT</fo:block>
												            	<fo:block font-size="11px" start-indent="1cm" font-weight="bold">													            												            		
												            	   <fo:external-graphic src="{$checkButton}" /> Option <xsl:value-of select="ROWID" />:												        								   
																	Limit of Liability /
																<xsl:choose>
													                    <xsl:when test="IsClaimExpensesType='Y'">
													                        Claim Expenses In Addition to the Limit of Liability (Claim<fo:block start-indent="1.5cm" > Expenses capped at the Limit of Liability)</fo:block>
						        								        </xsl:when>
													                    <xsl:otherwise>
													                        Claim Expenses Inside the Limit of Liability
						        								        </xsl:otherwise>
													                </xsl:choose>
																</fo:block>
																
															</fo:table-cell>
												           </fo:table-row>
												           
												           <fo:table-row>
												             <fo:table-cell padding-top="2mm" padding-left="4pt"><fo:block font-size="11px" font-family="Arial" >Limit of Liability:</fo:block></fo:table-cell>
												             <fo:table-cell padding-top="2mm" padding-left="4pt">
												             	<fo:block font-size="11px" font-family="Arial" >
												             		$<xsl:value-of select="AggregateLimit" /> Each <fo:inline font-weight="bold">Claim</fo:inline> / $<xsl:value-of select="OccuranceLimit" /> Policy Aggregate
												             		
												             	</fo:block>
												             </fo:table-cell>
												           </fo:table-row>
												           <fo:table-row>
												             <fo:table-cell padding-top="2mm" padding-left="4pt"><fo:block font-size="11px" font-family="Arial" >Deductible:</fo:block></fo:table-cell>
												             <fo:table-cell padding-top="2mm" padding-left="4pt">
												             	<fo:block font-size="11px" font-family="Arial" >
												             		$<xsl:value-of select="DeductibleAmount" /> 
												             		<xsl:choose>
													                    <xsl:when test="IsClaimOptionType='Y'">
													                        Annual Aggregate
						        								        </xsl:when>
													                    <xsl:otherwise>
													                        Each <fo:inline font-weight="bold">Claim</fo:inline> 
						        								        </xsl:otherwise>
													                </xsl:choose>
												             	</fo:block>
												             </fo:table-cell>
												           </fo:table-row>
												         
												            
												           <fo:table-row>
												             <fo:table-cell padding-top="2mm" padding-left="4pt"><fo:block font-size="11px" font-family="Arial" ><fo:inline font-weight="bold">Total Premium:</fo:inline></fo:block></fo:table-cell>
												             <fo:table-cell padding-top="2mm" padding-left="4pt"><fo:block font-size="11px" font-family="Arial" >$<xsl:value-of select="InvoicedPremium" /></fo:block></fo:table-cell>
												           </fo:table-row>	
												           	
												          				           
													</fo:table-body>
												</fo:table>
											</fo:block>
										</fo:table-cell>																		
									</fo:table-row>	
								</xsl:for-each>           
							</fo:table-body>
							
						</fo:table>	
					</xsl:if>	
					</fo:block>					
								
					
					<fo:block margin-top="5mm" font-size="11px" font-family="Arial">
						Please note: The total premiums shown above include any applicable taxes.
					</fo:block>
					
					
					<fo:block margin-top="5mm" font-size="11px" font-family="Arial">  
					<fo:table>
				         <fo:table-column column-width = "185mm" />
				          <fo:table-body>	
						 	<fo:table-row>
							    <fo:table-cell >
							    	<fo:block font-size="11px" font-family="Arial" ><fo:inline font-weight="bold">This quotation is subject to the following conditions:</fo:inline></fo:block>
							    </fo:table-cell>
							</fo:table-row>
							<fo:table-row>
							    <fo:table-cell >
							    	<fo:block font-size="11px" font-family="Arial" ><xsl:value-of select="response/policy_freeform_01/data/QuoteComment" /></fo:block>
							    </fo:table-cell>
							</fo:table-row>
						  </fo:table-body>
					</fo:table>
					</fo:block>						  
						 
					<!-- <fo:block margin-top="5mm" font-size="11px" font-family="Arial">
					If between the date of this quotation and the effective date of the policy, there is a significant change in the condition of the applicant, or an event which could substantially change our underwriting evaluation of the applicant, then, at our discretion, this quotation may be withdrawn by written notice to the proposed <fo:inline font-weight="bold">applicant</fo:inline>.   
					</fo:block> -->	   
					<fo:block margin-top="5mm" font-size="11px" font-family="Arial">
						<fo:inline font-weight="bold">If you would like to receive additional quote options, please contact one of our Professional Liability Specialists toll-free at 888-803-9898</fo:inline>
					</fo:block>
					
					<fo:block break-after="page" /> 
					
					<fo:block margin-top="5mm" font-size="11px" font-family="Arial"  >
						<fo:inline font-weight="bold">Endorsements</fo:inline>
					</fo:block>
					
					   
					<fo:table border="0.5pt solid black">
				         <fo:table-column column-width = "59mm" />
				         <fo:table-column column-width = "126mm" />
				         <!-- <fo:table-column column-width = "67mm" />	 -->		         
						 
						 <fo:table-body>	
						 	<fo:table-row>
							    <fo:table-cell padding-top="2mm">
							      <fo:block font-size="11px" font-style="italic" font-weight="bold">
							      	<fo:inline padding-left="4pt" text-decoration="underline">Form ID Number</fo:inline></fo:block>
							    </fo:table-cell>
							    <fo:table-cell padding-top="2mm">
							      <fo:block font-size="11px" font-style="italic" font-weight="bold">
							      	<fo:inline text-decoration="underline" padding-left="4pt">Form Name</fo:inline></fo:block>
							    </fo:table-cell>
							    <!-- <fo:table-cell border="0.5pt solid black">
							      <fo:block font-size="11px" border="0.5pt solid black" font-style="italic" font-weight="bold">
							      	<fo:inline text-decoration="underline" padding-left="4pt">Brief Description</fo:inline></fo:block>
							    </fo:table-cell> -->
							  </fo:table-row>
							  
				         	<xsl:for-each select="response/stateform_list_01/data">				         	
								<fo:table-row>								
									<fo:table-cell padding-top="2mm" padding-left="4pt">
										<fo:block font-size="11px"  font-family="Arial">
											<xsl:value-of select="FormID" />
										</fo:block>
									</fo:table-cell>
									<fo:table-cell padding-top="2mm" padding-left="4pt">
										<fo:block font-size="11px"  font-family="Arial">
											<xsl:value-of select="FormName" />
										</fo:block>
									</fo:table-cell>
									<!-- <fo:table-cell padding-top="2mm" padding-left="4pt">
										<fo:block font-size="11px"  font-family="Arial">
											<xsl:value-of select="FormDesc" />
										</fo:block>
									</fo:table-cell>	 -->								
								</fo:table-row>								
							</xsl:for-each>           
						</fo:table-body>
						
					</fo:table>
					
					<fo:block margin-top="5mm" font-size="11px"  font-family="Arial" font-style="italic">  
						Please note that the title and brief description for each form listed in the section above may not fully describe the scope or intent of such form.  Please read each form carefully.
					</fo:block>
					
					<fo:block margin-top="5mm" font-size="11px"  font-family="Arial">
						<fo:inline font-weight="bold">Instructions for Binding Coverage</fo:inline>
					</fo:block>
					
					<fo:block margin-top="5mm">
					<fo:table>
						 <fo:table-column column-width = "10mm" />
				         <fo:table-column column-width = "175mm" />				         
				         <fo:table-body>				         	   
					           <fo:table-row>
					           	 <fo:table-cell padding-top="2mm" padding-left="4pt"><fo:block font-size="11px"  font-family="Arial" text-align="left" >1.</fo:block></fo:table-cell>
					             <fo:table-cell padding-top="2mm" padding-left="4pt"><fo:block font-size="11px"  font-family="Arial" text-align="left"  >Please review all parts of this quotation.</fo:block></fo:table-cell>
					           </fo:table-row>
					           <fo:table-row>
					           	 <fo:table-cell padding-top="2mm" padding-left="4pt"><fo:block font-size="11px"  font-family="Arial" text-align="left" >2.</fo:block></fo:table-cell>
					             <fo:table-cell padding-top="2mm" padding-left="4pt"><fo:block font-size="11px"  font-family="Arial" text-align="left" >Complete, sign and date the attached United States Fire Insurance Company Accountants Professional Liability Application.</fo:block></fo:table-cell>
					           </fo:table-row>
					           <fo:table-row>
					           	 <fo:table-cell padding-top="2mm" padding-left="4pt"><fo:block font-size="11px"  font-family="Arial" text-align="left" >3.</fo:block></fo:table-cell>
					             <fo:table-cell padding-top="2mm" padding-left="4pt"><fo:block font-size="11px"  font-family="Arial" text-align="left" >Check the coverage option desired on the previous page.</fo:block></fo:table-cell>
					           </fo:table-row>
					           <fo:table-row>
					           	 <fo:table-cell padding-top="2mm" padding-left="4pt"><fo:block font-size="11px"  font-family="Arial" text-align="left"  >4.</fo:block></fo:table-cell>
					             <fo:table-cell padding-top="2mm" padding-left="4pt"><fo:block font-size="11px"  font-family="Arial" text-align="left" >Sign and date this form below, and return it along with your completed Application and your premium payment, prior to the quotation expiration date.</fo:block></fo:table-cell>
					           </fo:table-row>	
					           <fo:table-row>
					             <fo:table-cell padding-top="2mm" padding-left="4pt"><fo:block font-size="11px"  font-family="Arial" text-align="left" ><fo:inline font-weight="bold">5.</fo:inline></fo:block></fo:table-cell>
					             <fo:table-cell padding-top="2mm" padding-left="4pt"><fo:block font-size="11px"  font-family="Arial" text-align="left" >Make checks payable to: <fo:inline font-weight="bold">Protexure</fo:inline></fo:block></fo:table-cell>
					           </fo:table-row>			           
						</fo:table-body>
					</fo:table>
					</fo:block>
					<fo:block margin-top="5mm" font-size="11px"  font-family="Arial" text-align="left"  >
						If between the date of this quotation and the effective date of the
						policy, there is a significant change in
						the condition of the applicant, or an event which could substantially
						change our underwriting evaluation of the applicant, then, at our
						discretion, this quotation may be withdrawn by written notice to the
						proposed applicant.
					</fo:block>
					<fo:block margin-top="5mm">
					<xsl:if test="response/policy_freeform_01/data/StateCode = 'NY'">
						<fo:block break-after="page"></fo:block>
						<fo:table>
				         <fo:table-column column-width = "185mm" />					        			         
				         <fo:table-body>				         	   
					           <fo:table-row>
					             <fo:table-cell><fo:block font-size="11px"  font-family="Arial" >Protexure Accountants® is an insurance producer licensed by the State of New
									York. Insurance producers are authorized by their license to confer with
									insurance purchasers about the benefits, terms and conditions of insurance
									contracts; to offer advice concerning the substantive benefits of particular
									insurance contracts; to sell insurance; and to obtain insurance for purchasers. The
									role of the producer in any particular transaction typically involves one or more of
									these activities.</fo:block><fo:block><fo:external-graphic src="{$spaceBar}" /></fo:block>
									<fo:block font-size="11px"  font-family="Arial">
									Compensation will be paid to the producer, based on the insurance contract the
									producer sells. Depending on the insurer(s) and insurance contract(s) the
									purchaser selects, compensation will be paid by the insurer(s) selling the
									insurance contract or by another third party. Such compensation may vary
									depending on a number of factors, including the insurance contract(s) and the
									insurer(s) the purchaser selects. In some cases, other factors such as the volume of
									business a producer provides to an insurer or the profitability of insurance
									contracts a producer provides to an insurer also may affect compensation.</fo:block><fo:block><fo:external-graphic src="{$spaceBar}" /></fo:block>	
									<fo:block font-size="11px"  font-family="Arial">
									The insurance purchaser may obtain information about compensation expected to
									be received by the producer based in whole or in part on the sale of insurance to
									the purchaser, and (if applicable) compensation expected to be received based in
									whole or in part on any alternative quotes presented to the purchaser by the
									producer, by requesting such information from the producer.


									</fo:block></fo:table-cell>
					           </fo:table-row>					          				           			           
						</fo:table-body>
					</fo:table>
					</xsl:if>
					</fo:block>
					
					<fo:block margin-top="5mm">
					<fo:table border="0.5pt solid black">
				         <fo:table-column column-width = "105mm" />	
				         <fo:table-column column-width = "80mm" />			         
				         <fo:table-body>				         	   
					           <fo:table-row>
					             <fo:table-cell border="0.5pt solid black"  number-columns-spanned="2"><fo:block font-size="11px"  font-family="Arial" >My signature below constitutes my authorization for Protexure Insurance Agency, Inc. to bind the referred coverage with United States Fire Insurance Company as indicated.<fo:external-graphic src="{$spaceBar}" /></fo:block></fo:table-cell>
					           </fo:table-row>					           
					           <fo:table-row>
					             <fo:table-cell border="0.5pt solid black"  ><fo:block font-size="11px"  font-family="Arial" text-align="left" margin-top="8mm"><fo:inline >Signature:_____________________</fo:inline></fo:block></fo:table-cell>
					          	 <fo:table-cell  border="0.5pt solid black"  padding-left="70pt"><fo:block font-size="11px"  font-family="Arial" text-align="left" margin-top="8mm"><fo:inline >Date:________________</fo:inline></fo:block></fo:table-cell> 
					           </fo:table-row>					           			           
						</fo:table-body>
					</fo:table>
					</fo:block>	
	</xsl:template>

</xsl:stylesheet>
