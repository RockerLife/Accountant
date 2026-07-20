<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet version="1.1"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:fo="http://www.w3.org/1999/XSL/Format"
	exclude-result-prefixes="fo"
	xmlns:fn="http://www.w3.org/2005/02/xpath-functions"
	xmlns:java="java">
	
	<xsl:template match="/">
	
	<fo:root>
			<fo:layout-master-set>
				<fo:simple-page-master master-name="A4-portrait" page-width="21cm"   
					page-height="29.7cm" margin-top="0.2in" margin-bottom="1.0in" margin-left="0.5in" 
					margin-right="0.5in">
					<fo:region-body margin-top="2.0in"/>
					<fo:region-before extent="1.0in"/> 
					<fo:region-after /> 					
				</fo:simple-page-master>
			</fo:layout-master-set>

			<fo:page-sequence master-reference="A4-portrait">
			
				<fo:static-content flow-name="xsl-region-before">
				 <fo:block text-align="left">				  	
				  	<fo:external-graphic src="../AccountantIns/image/Protexure_Logo.png" content-height="6em" content-width="20em"/>           	
				  </fo:block>	
				  
				  <fo:block>
					<fo:table>
				         <fo:table-column column-width = "80mm" />
				         <fo:table-column column-width = "50mm" />
				         <fo:table-column column-width = "70mm" />
				         <fo:table-body>
					           <fo:table-row>
					             <fo:table-cell ><fo:block font-size="9px" font-family="Arial" ></fo:block></fo:table-cell>
					             <fo:table-cell ><fo:block font-size="9px" font-family="Arial" ></fo:block></fo:table-cell>
					           	 <fo:table-cell ><fo:block font-size="9px" font-family="Arial" >4200 Commerce Court, Ste 102</fo:block></fo:table-cell>
					           </fo:table-row>
					           <fo:table-row>
					             <fo:table-cell ><fo:block font-size="9px" font-family="Arial" ></fo:block></fo:table-cell>
					             <fo:table-cell ><fo:block font-size="9px" font-family="Arial" ></fo:block></fo:table-cell>
					           	 <fo:table-cell ><fo:block font-size="9px" font-family="Arial" >Lisle, IL 60532</fo:block></fo:table-cell>
					           </fo:table-row>
					           <fo:table-row>
					             <fo:table-cell ><fo:block font-size="9px" font-family="Arial" ></fo:block></fo:table-cell>
					             <fo:table-cell ><fo:block font-size="9px" font-family="Arial" ></fo:block></fo:table-cell>
					           	 <fo:table-cell ><fo:block font-size="9px" font-family="Arial" >Phone (888) 803-9898</fo:block></fo:table-cell>
					           </fo:table-row>
					           <fo:table-row>
					             <fo:table-cell ><fo:block font-size="9px" font-family="Arial" ></fo:block></fo:table-cell>
					             <fo:table-cell ><fo:block font-size="9px" font-family="Arial" ></fo:block></fo:table-cell>
					           	 <fo:table-cell ><fo:block font-size="9px" font-family="Arial" >Fax (630) 799-1796</fo:block></fo:table-cell>
					           </fo:table-row>
						</fo:table-body>
					</fo:table>
					<fo:external-graphic src="../AccountantIns/image/header_bottom.png" content-width="44em"/> 
				</fo:block>	
				
				<fo:table>
			         <fo:table-column column-width = "120mm" />
			         <fo:table-column column-width = "70mm" />
			         <fo:table-body>
			         	 <fo:table-row>
				             <fo:table-cell><fo:block font-size="10px" font-family="Arial" color="grey"><fo:inline font-weight="bold">Quick Quotation for Accountants Professional Liability Insurance</fo:inline></fo:block></fo:table-cell>
				             <fo:table-cell><fo:block  font-size="10px" text-indent="1cm" color="grey">Date:<xsl:value-of select="response/date_freeform_01/data/QuoteDate" />&#160;&#160;<xsl:value-of select="response/time_freeform_01/data/QuoteTime" /></fo:block></fo:table-cell>
				           </fo:table-row>				         
			         </fo:table-body>				         
				</fo:table>
				
				
				</fo:static-content>
								  				
				<fo:static-content flow-name="xsl-region-after">
				  <fo:block text-align="left">Page <fo:page-number/> of <fo:page-number-citation ref-id="last-page"/></fo:block>
				</fo:static-content>
				
				<fo:flow flow-name="xsl-region-body">
					
					<fo:block margin-top="3mm">
					<fo:table border="0.5pt solid black">
				         <fo:table-column column-width = "50mm" />
				         <fo:table-column column-width = "135mm" />
				         <fo:table-body>
					           <fo:table-row>
					             <fo:table-cell border="0.5pt solid black" padding-left="4pt"><fo:block font-size="10px" font-family="Arial" >Insured:</fo:block></fo:table-cell>
					             <fo:table-cell border="0.5pt solid black" padding-left="4pt"><fo:block font-size="10px" font-family="Arial" ><xsl:value-of select="response/policy_freeform_01/data/AccountName" /></fo:block></fo:table-cell>
					           </fo:table-row>
					           <fo:table-row>
					             <fo:table-cell border="0.5pt solid black" padding-left="4pt"><fo:block font-size="10px" font-family="Arial" >Policy Period:</fo:block></fo:table-cell>
					             <fo:table-cell border="0.5pt solid black" padding-left="4pt"><fo:block font-size="10px" font-family="Arial" ><xsl:value-of select="response/policy_freeform_01/data/PolicyEffectiveDate" />  -  <xsl:value-of select="response/address_freeform_01/data/PolicyExpirationDate" /></fo:block></fo:table-cell>
					           </fo:table-row>
					           <fo:table-row>
					             <fo:table-cell border="0.5pt solid black" padding-left="4pt"><fo:block font-size="10px" font-family="Arial" >Quotation #</fo:block></fo:table-cell>
					             <fo:table-cell border="0.5pt solid black" padding-left="4pt"><fo:block font-size="10px" font-family="Arial" ><xsl:value-of select="response/policy_freeform_01/data/QuoteNumber" /></fo:block></fo:table-cell>
					           </fo:table-row>
					        
					           
						</fo:table-body>
					</fo:table>
					</fo:block>
					
					<fo:block>
						<fo:table margin-top="3mm" border="0.0pt solid black">  
							<fo:table-column column-width="90mm"/>
								<fo:table-column column-width="100mm"/>
							<fo:table-body>								
						        <fo:table-row>							        		
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial">							        		
											What date would you like your policy to start? &#160;&#160;<xsl:value-of select="response/policy_freeform_01/data/PolicyEffectiveDate"/>&#160;&#160;&#160;&#160;&#160;						        			
								       	</fo:block></fo:table-cell>	
								       	<fo:table-cell><fo:block font-size="9px" font-family="Arial">							        		
											In what State is your firm located? &#160;<xsl:value-of select="response/address_freeform_01/data/StateDesc"/>&#160;&#160;&#160;&#160;&#160;						        			
								       	</fo:block></fo:table-cell>
							        	
						        </fo:table-row>						        						        
						        	
						        				        
						         <!-- <fo:table-row>
						         <fo:table-cell><fo:block font-size="9px" font-family="Arial">							        		
											How many years has your Firm had continuous insurance coverage? &#160;&#160;						        			
								         	
								         	<xsl:choose>
												<xsl:when test="response/firmyear_freeform_01/data/FirmYear = 0">
									         		0
									         	</xsl:when>
									         	<xsl:when test="response/firmyear_freeform_01/data/FirmYear = -1">
									         		1
									         	</xsl:when>
									         	<xsl:when test="response/firmyear_freeform_01/data/FirmYear = -2">
									         		2
									         	</xsl:when>
									         	<xsl:when test="response/firmyear_freeform_01/data/FirmYear = -3">
									         		3
									         	</xsl:when>
									         	<xsl:when test="response/firmyear_freeform_01/data/FirmYear = -4">
									         		4
									         	</xsl:when>									         	
									         	<xsl:when test="response/firmyear_freeform_01/data/FirmYear = -5">
									         	more than 4
									         	</xsl:when>
								         	</xsl:choose>
								         						    
								       </fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial">
								        
									        <xsl:choose>
												<xsl:when test="response/policy_freeform_01/data/IsClaimsInLast5Year = 'Y'">
													<fo:block font-size="9px" font-family="Arial" margin-top="3mm">Have you had any claims in last 5 years?
													        		Yes <fo:external-graphic src="../AccountantIns/image/check-btn6.png" />&#160;&#160;&#160;No <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/></fo:block>
													       	    
												</xsl:when>												
												<xsl:when test="response/policy_freeform_01/data/IsClaimsInLast5Year = 'N'">
													<fo:block font-size="9px" font-family="Arial" margin-top="3mm">Have you had any claims in last 5 years?
													        		Yes <fo:external-graphic src="../AccountantIns/image/check-btn4.png" />&#160;&#160;&#160;No <fo:external-graphic src="../AccountantIns/image/check-btn6.png"/></fo:block>
													        	
												</xsl:when>
									        </xsl:choose>
								        
								        </fo:block></fo:table-cell>
						        </fo:table-row>	 -->
						         
						        
						        <fo:table-row>
							        	 <fo:table-cell><fo:block font-size="9px" font-family="Arial" margin-top="3mm" >							        		
											Email : &#160;<xsl:value-of select="response/policy_freeform_01/data/AccountEmail"/>&#160;&#160;&#160;&#160;&#160;						        			
								       	</fo:block></fo:table-cell>						         									
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial" margin-top="3mm" >
								         Phone : &#160;<xsl:value-of select="response/policy_freeform_01/data/WorkPhone"/>&#160;&#160;&#160;&#160;&#160;
									            </fo:block></fo:table-cell>
						        </fo:table-row>	
						        				        
							</fo:table-body>
						</fo:table>
					</fo:block>
					
					
					
					<fo:block font-size="9px" font-family="Arial"  margin-top="4mm" >Last Year Revenue: &#160;&#160;<xsl:value-of select="response/lastyearrevenue_freeform_01/data/Amount"/></fo:block>
					
					<fo:block font-size="9px" font-family="Arial" margin-top="4mm" >What percentage of gross annual revenue comes from each Area of Practice listed below?</fo:block>
					
					<fo:block>
						<fo:table margin-top="2mm">
									<fo:table-column column-width="140mm"/>
									<fo:table-column column-width="30mm"/>
							<fo:table-body>
								
									<fo:table-row>	
							         		<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" text-align="center" font-family="Arial">AOPDesc</fo:block></fo:table-cell>
							         		<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial" text-align="center">Percentage</fo:block></fo:table-cell>
									</fo:table-row>
									<xsl:for-each select="response/aoplist/data">		
									<fo:table-row>	
																	
							         		<fo:table-cell border="0.5pt solid black" padding-left="2mm"><fo:block font-size="9px" font-family="Arial"><xsl:value-of select="AOPDesc"/></fo:block></fo:table-cell>
							         		<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial" text-align="center"><xsl:value-of select="PercentageValue"/>%</fo:block></fo:table-cell>
									</fo:table-row>
									</xsl:for-each>
									<!-- <fo:table-row>							
							         		<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial">&#160;&#160; A. Tax, Bookkeeping, Compilation and Review</fo:block></fo:table-cell>
							         		<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial" text-align="center"><xsl:value-of select="response/AOP_Percentage_4"/> %</fo:block></fo:table-cell>
									</fo:table-row>
									<fo:table-row>							
							         		<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial">&#160;&#160; B. Private Company Audit</fo:block></fo:table-cell>
							         		<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial" text-align="center"><xsl:value-of select="response/AOP_Percentage_6"/> %</fo:block></fo:table-cell>
									</fo:table-row>
									<fo:table-row>							
							         		<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial">&#160;&#160; C. Personal Financial Planning</fo:block></fo:table-cell>
							         		<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial" text-align="center"><xsl:value-of select="response/AOP_Percentage_19"/> %</fo:block></fo:table-cell>
									</fo:table-row>
									<fo:table-row>							
							         		<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial">&#160;&#160;  D. Business Valuation, IT consulting, Litigation </fo:block>
								         		<fo:block font-size="9px" font-family="Arial" text-indent="0.5cm">consulting, Management Advisory Svcs, and</fo:block>
								         		<fo:block font-size="9px" font-family="Arial" text-indent="0.5cm">Trustee Services</fo:block>
							         		</fo:table-cell>
							         		<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial" text-align="center"><xsl:value-of select="response/AOP_Percentage_15"/> %</fo:block></fo:table-cell>
									</fo:table-row>
									<fo:table-row>							
							         		<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial">&#160;&#160;  E. Other</fo:block>
								         		<fo:block>								         								         		
											         		<xsl:choose>
															     <xsl:when test="response/AOP_Percentage_20!='0'">
											         				<fo:block font-size="9px" font-family="Arial" >&#160;&#160;(Please describe) &#160;&#160;</fo:block>						         		
											         			</xsl:when>
											         		</xsl:choose>
										         	</fo:block>
							         		
							         		</fo:table-cell>
							         		<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial" text-align="center"><xsl:value-of select="response/AOP_Percentage_20"/> %</fo:block>
							         		
							         		<fo:block>
										         		<xsl:choose>
															     <xsl:when test="response/AOP_Percentage_20!='0'">
											         				<fo:block font-size="9px" font-family="Arial" text-align="center"><xsl:value-of select="response/AOPCommentDesc_20"/></fo:block>						         		
											         			</xsl:when>
											         	</xsl:choose>	
										         </fo:block>	
							         		</fo:table-cell>
									</fo:table-row> -->
									<fo:table-row>	
						         		<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" text-align="center" font-weight="bold" font-family="Arial">&#160;&#160;Total Percentage </fo:block></fo:table-cell>
						         		<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial" text-align="center"><xsl:value-of select="response/aop_total"/>%</fo:block></fo:table-cell>
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
											         	 
												           <fo:table-row>
												             <fo:table-cell number-columns-spanned="2" padding-left="4pt">
												             <!--  
												             <fo:block text-align="left" font-size="11px" font-weight="bold">ACCEPT</fo:block>-->
												            	<fo:block font-size="9px" start-indent="1cm" font-weight="bold">													            												            		
												            	 	Limit of Liability /
																<xsl:choose>
													                    <xsl:when test="IsClaimExpensesType='Yes'">
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
												             <fo:table-cell border="0.5pt solid black" padding-left="4pt"><fo:block font-size="11px" font-family="Arial" >Limit of Liability:</fo:block></fo:table-cell>
												             <fo:table-cell border="0.5pt solid black" padding-left="4pt">
												             	<fo:block font-size="9px" font-family="Arial" >
												             		<xsl:value-of select="AggregateLimit" /> Each <fo:inline font-weight="bold">Claim</fo:inline> / <xsl:value-of select="OccuranceLimit" /> Policy Aggregate
												             	</fo:block>
												             </fo:table-cell>
												           </fo:table-row>
												           <fo:table-row>
												             <fo:table-cell border="0.5pt solid black" padding-left="4pt"><fo:block font-size="11px" font-family="Arial" >Deductible:</fo:block></fo:table-cell>
												             <fo:table-cell border="0.5pt solid black" padding-left="4pt">
												             	<fo:block font-size="9px" font-family="Arial" >
												             		<xsl:value-of select="DeductibleAmount" /> 
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
												             <fo:table-cell border="0.5pt solid black" padding-left="4pt">
												             	<fo:block font-size="9px" font-family="Arial" >
												             		<xsl:choose>
													                    <xsl:when test="IsMinimumPremium='Y'">
													                        <fo:inline font-weight="bold">Total Premium:</fo:inline>
						        								        </xsl:when>
													                    <xsl:otherwise>
													                        <fo:inline font-weight="bold">Premium Range:</fo:inline> 
						        								        </xsl:otherwise>
													                </xsl:choose>
												             		
												             	</fo:block>
												             </fo:table-cell>
												             <fo:table-cell border="0.5pt solid black" padding-left="4pt">
												             	<fo:block font-size="9px" font-family="Arial" >
												             		<xsl:choose>
													                    <xsl:when test="IsMinimumPremium='Y'">
													                        <xsl:value-of select="TotalPremium" />
						        								        </xsl:when>
													                    <xsl:otherwise>
													                       <xsl:value-of select="EstimatePremiumRange" /> 
						        								        </xsl:otherwise>
													                </xsl:choose>
												             		
												             	</fo:block>
												             </fo:table-cell>
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
											
					<!--
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
						 
					<fo:block margin-top="5mm" font-size="11px" font-family="Arial">
					If between the date of this quotation and the effective date of the policy, there is a significant change in the condition of the applicant, or an event which could substantially change our underwriting evaluation of the applicant, then, at our discretion, this quotation may be withdrawn by written notice to the proposed <fo:inline font-weight="bold">applicant</fo:inline>.   
					</fo:block>	   
					<fo:block margin-top="5mm" font-size="11px" font-family="Arial">
						<fo:inline font-weight="bold">If you would like to receive additional quote options, please contact one of our Professional Liability Specialists toll-free at 888-803-9898</fo:inline>
					</fo:block>
					 <fo:block break-after="page" /> 
					<fo:block margin-top="5mm" font-size="11px" font-family="Arial"  >
						<fo:inline font-weight="bold">Endorsements</fo:inline>
					</fo:block>
					
					   
					<fo:table border="0.5pt solid black">
				         <fo:table-column column-width = "59mm" />
				         <fo:table-column column-width = "59mm" />
				         <fo:table-column column-width = "67mm" />			         
						 
						 <fo:table-body>	
						 	<fo:table-row>
							    <fo:table-cell border="0.5pt solid black">
							      <fo:block font-size="11px" font-style="italic" font-weight="bold">
							      	<fo:inline padding-left="4pt" text-decoration="underline">Form ID Number</fo:inline></fo:block>
							    </fo:table-cell>
							    <fo:table-cell border="0.5pt solid black">
							      <fo:block font-size="11px" border="0.5pt solid black" font-style="italic" font-weight="bold">
							      	<fo:inline text-decoration="underline" padding-left="4pt">Form Name</fo:inline></fo:block>
							    </fo:table-cell>
							    <fo:table-cell border="0.5pt solid black">
							      <fo:block font-size="11px" border="0.5pt solid black" font-style="italic" font-weight="bold">
							      	<fo:inline text-decoration="underline" padding-left="4pt">Brief Description</fo:inline></fo:block>
							    </fo:table-cell>
							  </fo:table-row>
							  
				         	<xsl:for-each select="response/stateform_list_01/data">				         	
								<fo:table-row>								
									<fo:table-cell border="0.5pt solid black" padding-left="4pt">
										<fo:block font-size="11px"  font-family="Arial">
											<xsl:value-of select="FormID" />
										</fo:block>
									</fo:table-cell>
									<fo:table-cell border="0.5pt solid black" padding-left="4pt">
										<fo:block font-size="11px"  font-family="Arial">
											<xsl:value-of select="FormName" />
										</fo:block>
									</fo:table-cell>
									<fo:table-cell border="0.5pt solid black" padding-left="4pt">
										<fo:block font-size="11px"  font-family="Arial">
											<xsl:value-of select="FormDesc" />
										</fo:block>
									</fo:table-cell>									
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
					<fo:table border="0.5pt solid black">
						 <fo:table-column column-width = "10mm" />
				         <fo:table-column column-width = "175mm" />				         
				         <fo:table-body>				         	   
					           <fo:table-row>
					           	 <fo:table-cell border="0.5pt solid black" padding-left="4pt"><fo:block font-size="11px"  font-family="Arial" text-align="left" >1.</fo:block></fo:table-cell>
					             <fo:table-cell border="0.5pt solid black" padding-left="4pt"><fo:block font-size="11px"  font-family="Arial" text-align="left"  >Please review all parts of this quotation.</fo:block></fo:table-cell>
					           </fo:table-row>
					           <fo:table-row>
					           	 <fo:table-cell border="0.5pt solid black" padding-left="4pt"><fo:block font-size="11px"  font-family="Arial" text-align="left" >2.</fo:block></fo:table-cell>
					             <fo:table-cell border="0.5pt solid black" padding-left="4pt"><fo:block font-size="11px"  font-family="Arial" text-align="left" >Complete, sign and date the attached United States Fire Insurance Company Accountants Professional Liability Application.</fo:block></fo:table-cell>
					           </fo:table-row>
					           <fo:table-row>
					           	 <fo:table-cell border="0.5pt solid black" padding-left="4pt"><fo:block font-size="11px"  font-family="Arial" text-align="left" >3.</fo:block></fo:table-cell>
					             <fo:table-cell border="0.5pt solid black" padding-left="4pt"><fo:block font-size="11px"  font-family="Arial" text-align="left" >Check the coverage option desired on the previous page.</fo:block></fo:table-cell>
					           </fo:table-row>
					           <fo:table-row>
					           	 <fo:table-cell border="0.5pt solid black" padding-left="4pt"><fo:block font-size="11px"  font-family="Arial" text-align="left"  >4.</fo:block></fo:table-cell>
					             <fo:table-cell border="0.5pt solid black" padding-left="4pt"><fo:block font-size="11px"  font-family="Arial" text-align="left" >Sign and date this form below, and return it along with your completed Application and your premium payment, prior to the quotation expiration date.</fo:block></fo:table-cell>
					           </fo:table-row>	
					           <fo:table-row>
					             <fo:table-cell border="0.5pt solid black" padding-left="4pt"><fo:block font-size="12px"  font-family="Arial" text-align="left" ><fo:inline font-weight="bold">5.</fo:inline></fo:block></fo:table-cell>
					             <fo:table-cell border="0.5pt solid black" padding-left="4pt"><fo:block font-size="12px"  font-family="Arial" text-align="left" >5.	Make checks payable to: <fo:inline font-weight="bold">AmerInst Professional Services Ltd. 4200 Commerce Ct. Suite 102, Lisle, IL 60532.</fo:inline></fo:block></fo:table-cell>
					           </fo:table-row>			           
						</fo:table-body>
					</fo:table>
					</fo:block>
					
					<fo:block margin-top="5mm">
					<fo:table border="0.5pt solid black">
				         <fo:table-column column-width = "105mm" />	
				         <fo:table-column column-width = "80mm" />			         
				         <fo:table-body>				         	   
					           <fo:table-row>
					             <fo:table-cell border="0.5pt solid black"  number-columns-spanned="2"><fo:block font-size="11px"  font-family="Arial" >My signature below constitutes my authorization for AmerInst Professional Services Ltd to bind the referred coverage with United States Fire Insurance Company as indicated.<fo:external-graphic src="../AccountantIns/image/spacer.png" /></fo:block></fo:table-cell>
					           </fo:table-row>
					           <fo:table-row>
					             <fo:table-cell border="0.5pt solid black"  ><fo:block font-size="11px"  font-family="Arial" text-align="left" margin-top="8mm"><fo:inline >Signature:_____________________</fo:inline></fo:block></fo:table-cell>
					          	 <fo:table-cell  border="0.5pt solid black"  padding-left="70pt"><fo:block font-size="11px"  font-family="Arial" text-align="left" margin-top="8mm"><fo:inline >Date:________________</fo:inline></fo:block></fo:table-cell> 
					           </fo:table-row>					           			           
						</fo:table-body>
					</fo:table>
					</fo:block>					
					-->
					  
					<fo:block id="last-page" font-size="11px"/>						
															
				</fo:flow>
			</fo:page-sequence>
		</fo:root>
 
	</xsl:template>

</xsl:stylesheet>
