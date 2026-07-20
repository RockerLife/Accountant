<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet version="1.1"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:fo="http://www.w3.org/1999/XSL/Format"
	exclude-result-prefixes="fo"
	xmlns:fn="http://www.w3.org/2005/02/xpath-functions" xmlns:java="java">

	<xsl:template name="AAA04P002" match="/">

		<fo:block text-align="right">				  	
					 	<fo:external-graphic src="../AccountantIns/image/ISMIE_logo.png" content-height="4em" content-width="16em"/>           	
					 </fo:block>
				    
					 <fo:block font-weight="bold"  font-size="11px"   text-align="left">Policy Number: <xsl:value-of select="response/policy_freeform_01/data/PolicyNumber" /></fo:block>
                     <fo:block margin-top="2mm"/> 	
               		<fo:block font-weight="bold"  font-size="12px" text-align="center" >ACCOUNTANTS PROFESSIONAL LIABILITY POLICY DECLARATIONS</fo:block>
               		<fo:block margin-top="2mm"/> 
               		<fo:block font-weight="normal"  font-size="10px" text-align="justify">NOTICE: THIS IS A CLAIMS MADE AND REPORTED POLICY. EXCEPT AS OTHERWISE PROVIDED HEREIN, THIS POLICY PROVIDES COVERAGE FOR <fo:inline font-weight="bold" font-style="italic">CLAIMS</fo:inline> FIRST MADE AGAINST THE <fo:inline font-weight="bold" font-style="italic">INSUREDS</fo:inline> AND REPORTED TO THE <fo:inline font-weight="bold" font-style="italic">INSURER</fo:inline> DURING THE <fo:inline font-weight="bold" font-style="italic">POLICY PERIOD</fo:inline>. PLEASE READ THIS POLICY CAREFULLY. WORDS AND PHRASES WHICH ARE PRINTED IN <fo:inline font-weight="bold" font-style="italic">BOLD ITALIC TYPEFACE</fo:inline> HAVE SPECIFIC MEANING AND ARE DEFINED IN SECTION IV. OFTHE POLICY.  UNLESS STATED OTHERWISE BY SPECIFIC ENDORSEMENT, <fo:inline font-weight="bold" font-style="italic">DEFENSE EXPENSES</fo:inline> ARE INCLUDED IN THE LIMIT OF LIABILITY AND REDUCE THE LIMIT OF LIABILITY AVAILABLE TO PAY <fo:inline font-weight="bold" font-style="italic">DAMAGES</fo:inline>.</fo:block>
					
                <fo:block >
					<fo:table border="2pt solid black" text-align="center">
					    <fo:table-column column-width = "15mm" />
				        <fo:table-column column-width = "175mm" />
				       
				        <fo:table-body>
					    		  <fo:table-row>
					                    <fo:table-cell border="2pt solid black" padding-bottom="1mm" padding-left="4pt"><fo:block font-size="10px"   text-align="left">Item 1</fo:block></fo:table-cell>
					                    <fo:table-cell border="2pt solid black" padding-bottom="1mm" padding-left="4pt">
					                    	<fo:block font-weight="bold"  font-size="10px"   text-align="left">Named  Insured  &amp; Street Address: </fo:block>
					                    	<fo:block font-size="10px"   text-align="left">
					                    		<fo:table text-align="left">
					    							<fo:table-column column-width = "150mm" />
					    								<fo:table-body>
															<fo:table-row>
																<fo:table-cell><fo:block font-size="10px"   text-align="left"><xsl:value-of select="response/policy_freeform_01/data/AccountName" /></fo:block></fo:table-cell>
					               					    	</fo:table-row>
					               					    	<fo:table-row>
																<fo:table-cell>
																	<fo:block font-size="10px"   text-align="left"><xsl:value-of select="response/address_freeform_01/data/Address1" />
																		<xsl:if test="response/address_freeform_01/data/Address2!= ''"> 					    	
																	    	<fo:external-graphic src="../AccountantIns/image/spacer.png"/><xsl:value-of select="response/address_freeform_01/data/Address2" />  
																	    </xsl:if>
																	    
																	</fo:block>
																</fo:table-cell>
					               					    	</fo:table-row>
					               					    	
					               					    	<fo:table-row>
																<fo:table-cell>
																	<fo:block font-size="10px"   text-align="left">
																		<xsl:if test="response/address_freeform_01/data/City!= ''">
																			<xsl:value-of select="response/address_freeform_01/data/City" />, 
																		</xsl:if>
																		<xsl:if test="response/address_freeform_01/data/StateDesc!= ''">
																			<xsl:value-of select="response/address_freeform_01/data/StateDesc" /><fo:external-graphic src="../AccountantIns/image/spacer.png"/>
																		</xsl:if>
																		<xsl:value-of select="response/address_freeform_01/data/Zip" />																	
																	</fo:block>
																</fo:table-cell>
					               					    	</fo:table-row>					             				               					    	
					    		  						</fo:table-body>
					    						</fo:table>					    						 
					                    	</fo:block>
					                    </fo:table-cell>
					    		  </fo:table-row>
					    		 
					    		  <fo:table-row>
					               		<fo:table-cell border="2pt solid black" padding-bottom="1mm" padding-left="4pt"><fo:block font-size="10px"   text-align="left">Item 2</fo:block></fo:table-cell>
					               		<fo:table-cell border="2pt solid black" padding-bottom="1mm" padding-left="4pt">
					               			 <fo:block font-size="10px"   text-align="left"><fo:inline font-weight="bold" font-style="italic">Policy Period:</fo:inline>  From  <xsl:value-of select="response/policy_freeform_01/data/PolicyEffectiveDate" />  (Effective)    To  <xsl:value-of select="response/address_freeform_01/data/PolicyExpirationDate" />  (Expiration)  </fo:block>
					               			 <fo:block font-size="10px"   text-align="center">(12:01 a.m. local time at the address shown in Item 1)</fo:block>
					               		</fo:table-cell>
					    		  </fo:table-row>
					    		 
					    		 
					    		 <fo:table-row>
					               		<fo:table-cell padding-bottom="1mm" border="2pt solid black" padding-left="4pt"><fo:block font-size="10px"   text-align="left">Item 3</fo:block></fo:table-cell>
					               		<fo:table-cell padding-bottom="1mm" border="2pt solid black" padding-left="4pt">
					               		<fo:block font-size="10px"   >
					               		<fo:block font-weight="bold" font-size="10px"   text-align="left">Limit of Liability</fo:block>  
					               		<fo:block margin-top="2mm"/> 
					         			<fo:table  text-align="center">
				    						<fo:table-column column-width = "30mm" />
			        		                <fo:table-column column-width = "31mm" />
			        		                 <fo:table-column column-width = "78mm" />
						                    <fo:table-body>
							    		        <fo:table-row>
						                    		<fo:table-cell  padding-left="4pt">
							                    		<fo:block font-size="10px"   text-align="center">
								                    		<fo:block text-align="left"><xsl:value-of select="response/policycoverage_freeform_01/data/AggregateLimitText" /></fo:block>
								                    		<fo:block text-align="left"><xsl:value-of select="response/policycoverage_freeform_01/data/OccuranceLimitText" /></fo:block>
							                    		</fo:block>
						                    		</fo:table-cell>						                    		
						                    		<fo:table-cell padding-left="4pt">
							    		  		         <fo:block font-size="10px"   text-align="left">each <fo:inline font-weight="bold" font-style="italic">Claim</fo:inline>						    		  		       	
							    		  		       					    		  		       
							    		  		     	  </fo:block>							    		  		       
							    		  		         <fo:block font-size="10px"   text-align="left"><fo:inline font-weight="bold" font-style="italic">Policy Aggregate</fo:inline></fo:block>
						    		  		        </fo:table-cell>  
						    		  		       
					    		  				</fo:table-row>
					    		  			</fo:table-body>
				   						</fo:table>
					               		<fo:block font-size="8px" text-align="left">These amounts include <fo:inline font-weight="bold" font-style="italic">Defense Expenses </fo:inline>unless this Section is amended by specific endorsement of this policy.</fo:block>
					               		</fo:block>
					              	    </fo:table-cell>
					    		  </fo:table-row>
					    		 
					    		  <fo:table-row>
					               		<fo:table-cell border="2pt solid black" padding-left="4pt" padding-bottom="1mm"><fo:block font-size="10px"   text-align="left">Item 4</fo:block></fo:table-cell>
					               		<fo:table-cell border="2pt solid black" padding-left="4pt" padding-bottom="1mm">
						               		
						               		<fo:block font-size="10px"   >
					               		<fo:block font-weight="bold" font-size="10px"   text-align="left">Deductible:</fo:block>  
					               		<fo:block margin-top="2mm"/> 
					         			<fo:table  text-align="center">
				    						<fo:table-column column-width = "30mm" />
			        		                <fo:table-column column-width = "31mm" />
						                    <fo:table-body>
							    		        <fo:table-row>
						                    		<fo:table-cell  padding-left="4pt">
							                    		<fo:block font-size="10px"   text-align="center">
								                    		<fo:block text-align="left"><xsl:value-of select="response/policycoverage_freeform_01/data/DeductibleAmount" /></fo:block>
								                    		
							                    		</fo:block>
						                    		</fo:table-cell>
						                    		<fo:table-cell padding-left="4pt">
						                    			<fo:block font-size="10px"   text-align="left">each<fo:inline font-weight="bold" font-style="italic"> Claim</fo:inline></fo:block>
						                    							    		  		         
						    		  		        </fo:table-cell>  
					    		  				</fo:table-row>
					    		  			</fo:table-body>
				   						</fo:table>
				   						<fo:block-container position="absolute" top="48pt" left="30pt" width="100%">
							              	   	<fo:block>
							              	   		<xsl:if test="response/policy_freeform_01/data/StateCode= 'NY'">
														<fo:external-graphic src="../AccountantIns/image/free_trade_zone_stamp.png"/>
							  				  		</xsl:if>
							  				  	</fo:block>
						  				  	</fo:block-container>
					               		<fo:block font-size="8px" text-align="left">This amount applies to <fo:inline font-weight="bold" font-style="italic">Defense Expenses</fo:inline> unless this Section is amended by specific endorsement of this policy.</fo:block>
	 									<fo:block font-size="8px" text-align="left">This amount applies to each <fo:inline font-weight="bold" font-style="italic">Claim </fo:inline>unless this Section is amended by specific endorsement of this policy.</fo:block>
					               		</fo:block>
						               		
						               		
						               						              	    
					              	    </fo:table-cell>
					    		  </fo:table-row>
					    		 
					    		  <fo:table-row>
					               <fo:table-cell border="2pt solid black" padding-left="4pt" padding-bottom="1mm"><fo:block font-size="10px"   text-align="left">Item 5</fo:block></fo:table-cell>
					               <fo:table-cell border="2pt solid black" padding-left="4pt" padding-bottom="1mm">
					               	
					               	<fo:block  font-size="10px"  text-align="left">
					               		<fo:inline font-weight="bold">Premium: </fo:inline>
					               
					               		<xsl:value-of select="response/policycoverage_freeform_01/data/TotalPremium" /> 
					               		<xsl:if test="response/policycoverage_freeform_01/data/MTTaxAmmount != '0'">
						             		+ <xsl:value-of select="response/policycoverage_freeform_01/data/MTTaxAmmount" />(<xsl:value-of select="response/policycoverage_freeform_01/data/MTTaxPercentage" /> %, Municipal Tax) 
						             	</xsl:if>						             	
						             	<xsl:if test="response/policycoverage_freeform_01/data/CountyTaxAmmount != '0' ">
						             		<xsl:if test="response/policycoverage_freeform_01/data/MTTaxAmmount = '0'">
						             		+ <xsl:value-of select="response/policycoverage_freeform_01/data/CountyTaxAmmount" />(<xsl:value-of select="response/policycoverage_freeform_01/data/CountyTaxPercentage" /> %, County Tax) 
						             		</xsl:if>
						             	</xsl:if>
						             	<xsl:if test="response/policycoverage_freeform_01/data/State1TaxAmmount != '0'">
						             		+ <xsl:value-of select="response/policycoverage_freeform_01/data/State1TaxAmmount" />(<xsl:value-of select="response/policycoverage_freeform_01/data/State1Percentage" /> %, State1 Tax) 
						             	</xsl:if>
						             	<xsl:if test="response/policycoverage_freeform_01/data/State2TaxAmmount != '0'">
						             		+ <xsl:value-of select="response/policycoverage_freeform_01/data/State2TaxAmmount" />(<xsl:value-of select="response/policycoverage_freeform_01/data/State2Percentage" /> %, State2 Tax) 
						             	</xsl:if>						             				               		
					               		 for the <fo:inline font-weight="bold" font-style="italic">Policy Period</fo:inline>
					               	</fo:block>
					               	
					               	<fo:block margin-top="2mm" font-size="10px" font-weight="bold"  text-align="left">
					               		Total :<xsl:value-of select="response/policycoverage_freeform_01/data/InvoicedPremium" />
					               	</fo:block>
					               </fo:table-cell>
					    		  </fo:table-row>					    		  
					    		 
					    		  <fo:table-row>
					               <fo:table-cell border="2pt solid black" padding-left="4pt" padding-bottom="1mm"><fo:block font-size="10px"   text-align="left">Item 6</fo:block></fo:table-cell>
					               <fo:table-cell border="2pt solid black" padding-left="4pt" padding-bottom="1mm"><fo:block font-weight="bold"  font-size="10px"   text-align="left">Prior Acts<xsl:if test="response/professionalliabilityinsdetail_freeform_01/data/IsPriorActDateFull= 'N'"> Date</xsl:if><xsl:if test="response/policy_freeform_01/data/IsFirmCarryingProfLiabilityIns != 'Y'"> Date</xsl:if> :
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
									    </xsl:if></fo:block></fo:table-cell>
					    		 </fo:table-row>
					    		 
					    		 <fo:table-row>
					               <fo:table-cell border="2pt solid black" padding-left="4pt" padding-bottom="1mm"><fo:block font-size="10px"   text-align="left">Item 7</fo:block></fo:table-cell>
					               <fo:table-cell border="2pt solid black" padding-left="4pt" padding-bottom="1mm"><fo:block font-weight="bold"  font-size="10px"   text-align="left">Optional Extended Reporting Period:</fo:block>
					                 <fo:block  font-size="10px"   text-align="left">(a) 12  Months                   (b) Additional Premium: 100 % of the annual premium</fo:block>
					               </fo:table-cell>
					    		 </fo:table-row>
					    		 
					    		 <fo:table-row>
					               <fo:table-cell border="2pt solid black" padding-left="4pt" padding-bottom="4mm">
					               <fo:block font-size="10px"   text-align="left" padding-bottom="4mm">Item 8</fo:block>
					               </fo:table-cell>
					               
					               <fo:table-cell border="2pt solid black" padding-left="4pt">
					                    <fo:block font-size="10px"   text-align="left">The following endorsements, if any, are made a part of this policy at issuance:</fo:block>
					                    <fo:block font-weight="bold"  font-size="10px"   text-align="left"><xsl:value-of select="response/Endorsements"/>
					                    	<!--  <xsl:if test="response/policy_freeform_01/data/StateCode= 'MI'">
														, FM 303.0.26 02 14
										</xsl:if> -->
					                    </fo:block>
					               </fo:table-cell>
					    		 </fo:table-row>
					    		 
					    		
					    </fo:table-body>
				   </fo:table>
				</fo:block>	    	
					 
			 	<fo:block margin-top="2mm"/> 
			 	<fo:block font-size="10px" text-align="justify">These Declarations, the application, and the policy with endorsements attached thereto, constitute the entire agreement between the <fo:inline font-weight="bold" font-style="italic">Insurer</fo:inline> and the <fo:inline font-weight="bold" font-style="italic">Insured</fo:inline>.</fo:block>
				<fo:block margin-top="2mm" > 
					<fo:table>
					    <fo:table-column column-width = "120mm" />
				        <fo:table-column column-width = "70mm" />				       
				        <fo:table-body>
			    		  <fo:table-row>
			                    <fo:table-cell padding-bottom="1mm" ><fo:block font-size="10px"   text-align="left">Countersigned (if required by law):__________________</fo:block></fo:table-cell>
			                    <fo:table-cell padding-bottom="1mm" ><fo:block font-size="10px"   text-align="left">Date:__________________</fo:block></fo:table-cell>
			              </fo:table-row>
					    </fo:table-body>
					</fo:table>
				</fo:block>				
				
				<fo:block margin-top="2mm"/>
				<fo:block font-weight="bold" font-size="10px" text-align="left" >TO REPORT A LOSS - InsClaim Solutions, 300 S. Wacker Dr., 32nd FL. Chicago, Illinois 60606</fo:block>
				
				<fo:block margin-top="2mm"/>
				<fo:block font-weight="bold" font-size="10px" text-align="center" >Claim Reporting Line: 312-559-4665
				<fo:inline color="blue">Email: claims@ins-claim.com</fo:inline>
				</fo:block>
				
				<fo:block margin-top="1mm"/>
				<fo:block  font-size="8px" text-align="justify" border="1pt solid black" >IN WITNESS WHEREOF, ISMIE MUTUAL INSURANCE COMPANY has caused this Policy to be signed by its President and Secretary at 20 North Michigan Avenue, Suite 700 Chicago Illinois 60602</fo:block>
				
				<fo:block margin-top="2mm"/>
				<fo:block text-align="center">				  	
				 	<fo:external-graphic src="../AccountantIns/image/ISMIEPFormSignature.png" content-height="10em" content-width="45em"/>           	
				</fo:block>
				
				<fo:block margin-top="25mm"/>
				
				<fo:block  font-size="10px" color="grey" text-align="left">
					ISMIE AAA-04-P002 (04012022)<fo:block margin-top="-0.5cm" font-size="10px" color="grey" text-align="right">1 of 1</fo:block>
				</fo:block>
	</xsl:template>
</xsl:stylesheet>




					
				    	