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
					<fo:region-body margin-top="0.5in"/>
					<fo:region-before extent="1.0in"/> 
					<fo:region-after /> 					
				</fo:simple-page-master>
			</fo:layout-master-set>			

			<fo:page-sequence master-reference="A4-portrait">			
				<fo:static-content flow-name="xsl-region-before">
					<fo:block text-align="right" color="grey">				  	
				  	<!--  	<fo:external-graphic src="../AccountantIns/image/crum_logo.png" content-height="4em" content-width="12em"/>-->
				  		
				  		<fo:external-graphic src="../AccountantIns/image/crum_logo.png" content-height="3em" content-width="10em"/>            	
				  	</fo:block>	
				</fo:static-content>
			
				<fo:static-content flow-name="xsl-region-after">
				<fo:table>
				         <fo:table-column column-width = "60mm" />
				         <fo:table-column column-width = "60mm" />
				         <fo:table-body>
					           <fo:table-row>
					           <fo:table-cell><fo:block text-align="left" font-size="8px" font-family="Arial" color="grey">APL - 120 – 8/11</fo:block></fo:table-cell>
					           <fo:table-cell><fo:block text-align="center" font-size="8px" font-family="Arial" color="grey">Page <fo:page-number/> of <fo:page-number-citation ref-id="last-page"/></fo:block></fo:table-cell>
					           </fo:table-row>
					     </fo:table-body>
				</fo:table>
				</fo:static-content>
				
			<fo:flow flow-name="xsl-region-body">
				<fo:block margin-top="2mm">
					<fo:table>
				         <fo:table-column column-width = "60mm" />
				         <fo:table-column column-width = "50mm" />
				         <fo:table-column column-width = "80mm" />
				         <fo:table-body>
					           <fo:table-row>
					             <fo:table-cell ><fo:block font-size="9px" font-family="Arial" ></fo:block></fo:table-cell>
					             <fo:table-cell ><fo:block font-size="9px" font-family="Arial" ></fo:block></fo:table-cell>
					           	 <fo:table-cell ><fo:block font-size="9px" font-family="Arial" font-weight="bold">UNITED STATES FIRE INSURANCE COMPANY</fo:block></fo:table-cell>
					           </fo:table-row>					           
					           <fo:table-row>
					             <fo:table-cell ><fo:block font-size="9px" font-family="Arial" ></fo:block></fo:table-cell>
					             <fo:table-cell ><fo:block font-size="9px" font-family="Arial" ></fo:block></fo:table-cell>
					           	 <fo:table-cell ><fo:block font-size="9px" font-family="Arial" font-weight="bold">305 MADISON AVENUE, MORRISTOWN, NJ 07962</fo:block></fo:table-cell>
					           </fo:table-row>
					  </fo:table-body>
					</fo:table>
					<!-- <fo:external-graphic src="../AccountantIns/image/header_bottom.png" content-width="44em"/> --> 
				</fo:block>	
				<fo:block>
					<fo:table margin-top="3mm">
						<fo:table-column column-width = "200mm"/>
						<fo:table-body>
							<fo:table-row>
					        	<fo:table-cell><fo:block></fo:block></fo:table-cell>
					        </fo:table-row>
					    	<fo:table-row>
					        	<fo:table-cell><fo:block  font-size="12px" font-family="Arial Black" font-weight="bold" text-align="center">ACCOUNTANT PROFESSIONAL LIABILITY APPLICATION</fo:block></fo:table-cell>
					        </fo:table-row>
					    </fo:table-body>
					</fo:table>
				</fo:block>	
				<fo:block>
					<fo:table margin-top="3mm">
						<fo:table-column/>
						<fo:table-body>
							<fo:table-row>
					        	<fo:table-cell><fo:block font-size="7px" font-family="Tahoma">NOTICE: COVERAGE FOR WHICH THIS APPLICATION IS MADE IS WRITTEN ON A CLAIMS MADE AND REPORTED BASIS MEANING, EXCEPT AS OTHERWISE</fo:block></fo:table-cell>
					        </fo:table-row>
					     </fo:table-body>
					</fo:table>
					<fo:table margin-top="0.5mm" border="0.0pt solid black">
						<fo:table-column column-width="49mm"/>
						<fo:table-column column-width="11mm"/>
						<fo:table-column column-width="32mm"/>
						<fo:table-column column-width="12mm"/>
						<fo:table-column column-width="29mm"/>
						<fo:table-column column-width="12mm"/>
						<fo:table-column column-width="16mm"/>						
						<fo:table-column/>						
						<fo:table-body>   
					        <fo:table-row>
					        	<fo:table-cell border="0.0pt solid black"><fo:block font-size="7px" font-family="Tahoma">PROVIDED, COVERAGE APPLIES ONLY TO</fo:block></fo:table-cell>
					        	<fo:table-cell border="0.0pt solid black"><fo:block font-style="italic" font-family="Tahoma" font-weight="bold" font-size="7px">&#160;CLAIMS</fo:block></fo:table-cell>
					        	<fo:table-cell border="0.0pt solid black"><fo:block font-size="7px" font-family="Tahoma"> FIRST MADE AGAINST THE </fo:block></fo:table-cell>
					        	<fo:table-cell border="0.0pt solid black"><fo:block font-style="italic" font-family="Tahoma" font-weight="bold" font-size="7px">INSURED</fo:block></fo:table-cell>
					        	<fo:table-cell border="0.0pt solid black"><fo:block font-size="7px" font-family="Tahoma"> AND REPORTED TO THE</fo:block></fo:table-cell>
					        	<fo:table-cell border="0.0pt solid black"><fo:block font-style="italic" font-family="Tahoma" font-weight="bold" font-size="7px">INSURER</fo:block></fo:table-cell>
					        	<fo:table-cell border="0.0pt solid black"><fo:block font-size="7px" font-family="Tahoma"> DURING THE </fo:block></fo:table-cell>
					        	<fo:table-cell border="0.0pt solid black"><fo:block font-style="italic" font-family="Tahoma" font-weight="bold" font-size="7px">POLICY PERIOD</fo:block>
					        	
					        	</fo:table-cell>
					        </fo:table-row>
					        
					     </fo:table-body>
					</fo:table>
					<fo:table>
						<fo:table-column/>
						<fo:table-body>
							<fo:table-row>
								<fo:table-cell border="0.0pt solid black"><fo:block font-style="italic" font-family="Tahoma" font-size="7px">
							<xsl:choose>							
					        		<xsl:when test="response/policy_freeform_01/data/StateCode= 'CT'">					        			
					        				OR DURING ANY APPLICABLE EXTENDED REPORTING PERIOD.					        					        			
					        		</xsl:when>
					        		<xsl:otherwise>					        		
					        		</xsl:otherwise>
					        	</xsl:choose>
					        	</fo:block></fo:table-cell>
					        	</fo:table-row>		
					     </fo:table-body>
					</fo:table>
					
					<fo:table margin-top="3mm">  
						<fo:table-column column-width="151mm"/>
						<fo:table-column/>
						<fo:table-body>      
					        <fo:table-row>
					        	<fo:table-cell><fo:block font-size="7px" font-family="Arial">CAREFULLY READ THE ENTIRE POLICY FOR WHICH THIS APPLICATION IS MADE. WORDS AND PHRASES WHICH ARE PRINTED IN</fo:block></fo:table-cell>
					        	<fo:table-cell border="0.0pt solid black"><fo:block font-style="italic" font-family="Arial" font-weight="bold" font-size="7px"> BOLD ITALIC </fo:block></fo:table-cell>
					        </fo:table-row>
					    </fo:table-body>
					</fo:table>
					<fo:table margin-top="0.5mm">  
						<fo:table-column column-width="13mm"/>
						<fo:table-column/>
						<fo:table-body>       
					        <fo:table-row>
					        	<fo:table-cell border="0.0pt solid black"><fo:block font-style="italic" font-family="Arial" font-weight="bold" font-size="7px">TYPEFACE</fo:block></fo:table-cell>
					        	<fo:table-cell><fo:block font-size="7px" font-family="Arial">&#160;HAVE SPECIFIC MEANING AND ARE DEFINED IN SECTION IV OF THE POLICY.</fo:block></fo:table-cell>
					        </fo:table-row>
					    </fo:table-body>
					</fo:table>
					
					<fo:table margin-top="3mm">  
						<fo:table-column/>    
					    <fo:table-body>       
					        <fo:table-row>
					        	<fo:table-cell><fo:block font-size="7px" font-family="Arial">THE APPLICATION, ITS ATTACHMENTS AND ALL PREVIOUS APPLICATIONS AND THEIR ATTACHMENTS SHALL SERVE AS THE BASIS OF THE POLICY, AND </fo:block></fo:table-cell>
					        </fo:table-row>
					    </fo:table-body>
					</fo:table>
					<fo:table margin-top="0.5mm">  
						<fo:table-column column-width="126mm"/>
						<fo:table-column column-width="12mm"/>
						<fo:table-column/>    
					    <fo:table-body>    
					        <fo:table-row>
					        	<fo:table-cell><fo:block font-size="7px" font-family="Arial">SHALL BECOME PART OF SUCH POLICY SHOULD A POLICY BE ISSUED, AS IF PHYSICALLY ATTACHED. THE</fo:block></fo:table-cell>
					        	<fo:table-cell border="0.0pt solid black"><fo:block font-style="italic" font-family="Arial" font-weight="bold" font-size="7px">INSURER</fo:block></fo:table-cell>
					        	<fo:table-cell><fo:block font-size="7px" font-family="Arial">RELIES UPON THE APPLICATION IN</fo:block></fo:table-cell>
					        </fo:table-row>
					    </fo:table-body>
					</fo:table>
					<fo:table margin-top="0.5mm">  
						<fo:table-column/>    
					    <fo:table-body>     
					        <fo:table-row>
					        	<fo:table-cell><fo:block font-size="7px" font-family="Arial">ISSUING THE POLICY. COMPLETION OF THIS APPLICATION DOES NOT IN ANY WAY IMPLY SUCH COVERAGE UNDER THE POLICY. COVERAGE IS </fo:block></fo:table-cell>
					        </fo:table-row>
					        <fo:table-row>
					        	<fo:table-cell><fo:block font-size="7px" font-family="Arial">AFFORDED ONLY IF AND TO THE EXTENT INDICATED BY THE TERMS AND CONDITIONS OF THE POLICY IF ISSUED.</fo:block></fo:table-cell>
					        </fo:table-row>
					    </fo:table-body>
					</fo:table>
				</fo:block>	
				
				<fo:block font-size="9px" font-family="Arial" font-weight="bold" margin-top="2mm">
					Please complete the application with respect to the Firm. Wherever the word “Firm” is used, it will be deemed to include the Firm affiliates. 
				</fo:block>
				<fo:block>
					<fo:table margin-top=".5mm" border="0.1pt solid black">
						<fo:table-column/>
						<fo:table-body>       
					        <fo:table-row>
					        	<fo:table-cell><fo:block></fo:block></fo:table-cell>
					        </fo:table-row>
					    </fo:table-body>    
					</fo:table>
				</fo:block>
				
				
				<fo:block>
				
				<fo:table margin-top="1mm">
				<fo:table-column column-width="5mm"></fo:table-column>
				<fo:table-column column-width="30mm"></fo:table-column>
				<fo:table-column column-width="60mm"></fo:table-column>
				<fo:table-column column-width="55mm"></fo:table-column>
				<fo:table-column column-width="35mm"></fo:table-column>
					<fo:table-body>
						<fo:table-row>
							<fo:table-cell><fo:block font-size="9px" font-family="Arial"></fo:block></fo:table-cell>
							<fo:table-cell><fo:block font-size="9px" font-family="Arial">Firm Name:</fo:block></fo:table-cell>
							<fo:table-cell><fo:block font-size="9px" font-family="Arial"><xsl:value-of select="response/policy_freeform_01/data/AccountName"/></fo:block></fo:table-cell>
							<fo:table-cell><fo:block font-size="9px" font-family="Arial">County:</fo:block></fo:table-cell>
							<fo:table-cell><fo:block font-size="9px" font-family="Arial"><xsl:value-of select="response/policy_freeform_01/data/CountyDesc"/></fo:block> </fo:table-cell>
						</fo:table-row>
						<fo:table-row>
							<fo:table-cell><fo:block font-size="9px" font-family="Arial"></fo:block></fo:table-cell>
							<fo:table-cell><fo:block font-size="9px" font-family="Arial">Address:</fo:block></fo:table-cell>
							<fo:table-cell><fo:block font-size="9px" font-family="Arial"><xsl:value-of select="response/policy_freeform_01/data/Address1"/>&#160;<xsl:value-of select="response/policy_freeform_01/data/Address2"/></fo:block></fo:table-cell>
							<fo:table-cell><fo:block font-size="9px" font-family="Arial">City:</fo:block></fo:table-cell>
							<fo:table-cell><fo:block font-size="9px" font-family="Arial"><xsl:value-of select="response/policy_freeform_01/data/City"/></fo:block> </fo:table-cell>
						</fo:table-row>
						<fo:table-row>
							<fo:table-cell><fo:block font-size="9px" font-family="Arial"></fo:block></fo:table-cell>
							<fo:table-cell><fo:block font-size="9px" font-family="Arial">Zip:</fo:block></fo:table-cell>
							<fo:table-cell><fo:block font-size="9px" font-family="Arial"><xsl:value-of select="response/policy_freeform_01/data/Zip"/>&#160;<xsl:value-of select="response/policy_freeform_01/data/Zip4"/></fo:block></fo:table-cell>
							<fo:table-cell><fo:block font-size="9px" font-family="Arial">State:</fo:block></fo:table-cell>
							<fo:table-cell><fo:block font-size="9px" font-family="Arial"><xsl:value-of select="response/policy_freeform_01/data/StateDesc"/></fo:block> </fo:table-cell>
						</fo:table-row>
						
						<fo:table-row>
							<fo:table-cell><fo:block font-size="9px" font-family="Arial"></fo:block></fo:table-cell>
							<fo:table-cell><fo:block font-size="9px" font-family="Arial">Firm Phone Number:</fo:block></fo:table-cell>
							<fo:table-cell><fo:block font-size="9px" font-family="Arial"><xsl:value-of select="response/policy_freeform_01/data/WorkPhone"/>&#160;<xsl:value-of select="response/policy_freeform_01/data/WorkExt"/></fo:block></fo:table-cell>
							<fo:table-cell><fo:block font-size="9px" font-family="Arial">Email Address:</fo:block></fo:table-cell>
							<fo:table-cell><fo:block font-size="9px" font-family="Arial"><xsl:value-of select="response/policy_freeform_01/data/AccountEmail"/></fo:block></fo:table-cell>
						</fo:table-row>
						
						<fo:table-row>
							<fo:table-cell><fo:block font-size="9px" font-family="Arial"></fo:block></fo:table-cell>
							<fo:table-cell><fo:block font-size="9px" font-family="Arial">Contact:</fo:block></fo:table-cell>
							<fo:table-cell><fo:block font-size="9px" font-family="Arial"><xsl:value-of select="response/policy_freeform_01/data/ContactPerson"/></fo:block></fo:table-cell>
							<fo:table-cell><fo:block font-size="9px" font-family="Arial">Contact Phone:&#160;(if different than above)&#160;</fo:block></fo:table-cell>
							<fo:table-cell><fo:block font-size="9px" font-family="Arial"><xsl:value-of select="response/policy_freeform_01/data/ContactPhone"/>&#160;<xsl:value-of select="response/policy_freeform_01/data/ContactPhoneExt"/></fo:block></fo:table-cell>
						</fo:table-row>
							
					</fo:table-body>
				</fo:table>
			</fo:block>
			
			<fo:block>
					<fo:table margin-top="0.5mm" border="0.0pt solid black">  
						<fo:table-column column-width="5mm"/>
						<fo:table-column column-width="31mm"/>
						<fo:table-column/>
						<fo:table-body>       
					        <fo:table-row>
					        	<fo:table-cell><fo:block font-size="9px" font-family="Arial"> </fo:block></fo:table-cell>
					        	<fo:table-cell><fo:block font-size="9px" font-family="Arial">Desired Effective Date: </fo:block></fo:table-cell>
					        	<fo:table-cell><fo:block font-size="9px" font-family="Arial"><xsl:value-of select="response/policy_freeform_01/data/PolicyEffectiveDate"/></fo:block></fo:table-cell>
					        </fo:table-row>
					        <fo:table-row>
					        	<fo:table-cell><fo:block font-size="9px" font-family="Arial">      </fo:block></fo:table-cell>
					        	<fo:table-cell><fo:block font-size="9px" font-family="Arial">              </fo:block></fo:table-cell>
					        	<fo:table-cell><fo:block font-size="5px" font-family="Arial" font-style="italic">        MM  /  DD  /  YYYY</fo:block></fo:table-cell>
					        </fo:table-row>
					    </fo:table-body>
					</fo:table>  
				</fo:block>	
				
				<fo:block>
					<fo:table margin-top="1mm" border="0.0pt solid black">
						<fo:table-column column-width="5mm"/>  
						<fo:table-column/>
						<fo:table-body>
					        <fo:table-row>
					        	<fo:table-cell><fo:block font-size="9px" font-family="Arial">1.</fo:block></fo:table-cell>
					        	<fo:table-cell><fo:block font-size="9px" font-family="Arial">Please indicate the number of personnel in the Firm:</fo:block></fo:table-cell>
					        	
					        </fo:table-row>
					    </fo:table-body>
					</fo:table>
					<fo:table  margin-top=".5mm" border="0.0pt solid black">
						<fo:table-column column-width="5mm"/>  
						<fo:table-column column-width="100mm"/>
						<fo:table-column column-width="50mm"/>											
						<fo:table-body>
							<xsl:for-each select="response/BasicInfo_list_01/data">
														
						        <fo:table-row>
						        	<fo:table-cell><fo:block font-size="9px" font-family="Arial" margin-top=".5mm"></fo:block></fo:table-cell>
						        	<fo:table-cell><fo:block font-size="9px" font-family="Arial" margin-top=".5mm"><xsl:value-of select="PersonnelTypeNumbering"></xsl:value-of>  <xsl:value-of select="PersonnelTypeDesc"></xsl:value-of> </fo:block></fo:table-cell>
						        	<fo:table-cell><fo:block font-size="9px" font-family="Arial" margin-top=".5mm"><xsl:value-of select="NumberOfPersonnel"></xsl:value-of> </fo:block></fo:table-cell>
						        </fo:table-row>							        
					        </xsl:for-each>				        
					    </fo:table-body>
					</fo:table>
					<fo:table margin-top="0.5mm" border="0.0pt solid black">  
						<fo:table-column column-width="5mm"/>
						<fo:table-column column-width="100mm"/>						
						<fo:table-column/>
						<fo:table-body>  
							   <fo:table-row>
					        	<fo:table-cell><fo:block font-size="9px" font-family="Arial"></fo:block></fo:table-cell>
					        	<fo:table-cell><fo:block font-size="9px" font-family="Arial" font-weight="bold">Total Firm Personnel  </fo:block></fo:table-cell>
					        	<fo:table-cell><fo:block font-size="9px" font-family="Arial" font-weight="bold"> <xsl:value-of select="response/TotalPersonnel"></xsl:value-of>   (A + B + C + D + E) </fo:block></fo:table-cell>
					        	
					        </fo:table-row>
					        
						</fo:table-body>
					</fo:table>	  
				</fo:block>	
				
				<fo:block>
						<fo:table margin-top="1mm" border="0.0pt solid black" >
						<fo:table-column column-width="5mm"/>  
						<fo:table-column/>
						<fo:table-body>
					        <fo:table-row>
					        	<fo:table-cell><fo:block font-size="9px" font-family="Arial">2.</fo:block></fo:table-cell>
					        	<fo:table-cell><fo:block font-size="9px" font-family="Arial">Gross annual revenue for the Firm for the last completed business year: </fo:block></fo:table-cell>					        	
					        </fo:table-row>
					    </fo:table-body>
					</fo:table>
					<fo:table margin-top=".5mm" >
						<fo:table-column column-width="100mm"/>  
						<fo:table-column />
						<fo:table-body>
					        <fo:table-row>
					        	<fo:table-cell border="0.1pt solid black" background-color="grey"><fo:block text-align="center" font-size="9px"  font-family="Arial" font-weight="bold">MM/YYYY</fo:block></fo:table-cell>
					        	<fo:table-cell border="0.1pt solid black" background-color="grey"><fo:block text-align="center" font-size="9px" font-family="Arial">$Amount</fo:block></fo:table-cell>					        	
					        </fo:table-row>
					        <fo:table-row>
					        	<fo:table-cell border="0.1pt solid black"><fo:block text-align="center" font-size="9px"  font-family="Arial"><xsl:value-of select="response/YearEndDate"></xsl:value-of> </fo:block></fo:table-cell>
					        	<fo:table-cell border="0.1pt solid black"><fo:block text-align="center" font-size="9px" font-family="Arial"><xsl:value-of select="response/Amount"></xsl:value-of></fo:block></fo:table-cell>					        	
					        </fo:table-row>
					    </fo:table-body>
					</fo:table>
				</fo:block>
				
				
				<fo:block>
					<fo:table margin-top="2mm" border="0.0pt solid black">  
						<fo:table-column column-width="5mm"/>
						<fo:table-column/>
						<fo:table-body>
							<fo:table-row>
								<fo:table-cell><fo:block font-size="9px" font-family="Arial">3.</fo:block></fo:table-cell>
								<fo:table-cell><fo:block font-size="9px" font-family="Arial">Please update the Firm’s percentage, rounded to the nearest 5%, to reflect the current year’s gross annual revenue derived from the areas of practice listed below: </fo:block></fo:table-cell>
							</fo:table-row>
						</fo:table-body>	
					</fo:table>
					<fo:table margin-top="1mm">
							<fo:table-column column-width="60mm"/>
							<fo:table-column column-width="16mm"/>
							<fo:table-column column-width="16mm"/>
							<fo:table-column column-width="60mm"/>
							<fo:table-column column-width="16mm"/>
							<fo:table-column column-width="16mm"/>
							<fo:table-body>
								<fo:table-row>
									<fo:table-cell border="0.5pt solid black" background-color="grey"><fo:block font-size="9px" font-family="Arial" text-align="center" font-weight="bold">SERVICE AREA</fo:block></fo:table-cell>
									<fo:table-cell border="0.5pt solid black" background-color="grey"><fo:block font-size="9px" font-family="Arial" text-align="center" font-weight="bold">Last Year's % </fo:block></fo:table-cell>
									<fo:table-cell border="0.5pt solid black" background-color="grey"><fo:block font-size="9px" font-family="Arial" text-align="center" font-weight="bold">This Year's %</fo:block></fo:table-cell>
									<fo:table-cell border="0.5pt solid black" background-color="grey"><fo:block font-size="9px" font-family="Arial" text-align="center" font-weight="bold">SERVICE AREA</fo:block></fo:table-cell>
									<fo:table-cell border="0.5pt solid black" background-color="grey"><fo:block font-size="9px" font-family="Arial" text-align="center" font-weight="bold">Last Year's % </fo:block></fo:table-cell>
									<fo:table-cell border="0.5pt solid black" background-color="grey"><fo:block font-size="9px" font-family="Arial" text-align="center" font-weight="bold">This Year's %</fo:block></fo:table-cell>
								</fo:table-row>
								
								<fo:table-row>
									<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" background-color="grey" text-align="center" font-family="Arial">Tax</fo:block></fo:table-cell>
									<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial" text-align="center" ></fo:block></fo:table-cell>
									<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial" text-align="center"></fo:block></fo:table-cell>
									<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" background-color="grey" text-align="center" font-family="Arial">Consulting</fo:block></fo:table-cell>
									<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial" text-align="center"></fo:block></fo:table-cell>
									<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial" text-align="center"></fo:block></fo:table-cell>
								</fo:table-row>
								<fo:table-row>
									<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial">A.	Business Tax Services</fo:block></fo:table-cell>
									<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial" text-align="center"><xsl:value-of select="response/AOPOld_PercentageValue_1"/> %</fo:block></fo:table-cell>
									<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial" text-align="center"><xsl:value-of select="response/AOP_PercentageValue_1"/> %</fo:block></fo:table-cell>
									<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial">K.	Litigation Support</fo:block></fo:table-cell>
									<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial" text-align="center"><xsl:value-of select="response/AOPOld_PercentageValue_11"/> %</fo:block></fo:table-cell>
									<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial" text-align="center"><xsl:value-of select="response/AOP_PercentageValue_11"/> %</fo:block></fo:table-cell>
								</fo:table-row>
								
								<fo:table-row>
									<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial">B.	Estate Tax Services</fo:block></fo:table-cell>
									<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial" text-align="center" ><xsl:value-of select="response/AOPOld_PercentageValue_2"/> %</fo:block></fo:table-cell>
									<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial" text-align="center"><xsl:value-of select="response/AOP_PercentageValue_2"/> %</fo:block></fo:table-cell>
									<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial">L.	General Business Advice</fo:block></fo:table-cell>
									<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial" text-align="center"><xsl:value-of select="response/AOPOld_PercentageValue_22"/> %</fo:block></fo:table-cell>
									<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial" text-align="center"><xsl:value-of select="response/AOP_PercentageValue_22"/> %</fo:block></fo:table-cell>
								</fo:table-row>
								
								<fo:table-row>
									<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial">C.	Individual Tax Services</fo:block></fo:table-cell>
									<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial" text-align="center"><xsl:value-of select="response/AOPOld_PercentageValue_3"/> %</fo:block></fo:table-cell>
									<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial" text-align="center"><xsl:value-of select="response/AOP_PercentageValue_3"/> %</fo:block></fo:table-cell>
									<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial">M.	Other Consulting</fo:block></fo:table-cell>
									<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial" text-align="center"><xsl:value-of select="response/AOPOld_PercentageValue_12"/> %</fo:block></fo:table-cell>
									<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial" text-align="center"><xsl:value-of select="response/AOP_PercentageValue_12"/> %</fo:block></fo:table-cell>
								</fo:table-row>
								
								
								<fo:table-row>
									<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" background-color="grey" text-align="center" font-family="Arial" font-weight="bold">Accounting / Bookkeeping</fo:block></fo:table-cell>
									<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial" text-align="center" font-weight="bold"></fo:block></fo:table-cell>
									<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial" text-align="center" font-weight="bold"></fo:block></fo:table-cell>
									<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial">Please describe</fo:block></fo:table-cell>
									<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial" text-align="center"><xsl:value-of select="response/AOPOld_CommentDesc_12"/></fo:block></fo:table-cell>
									<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial" text-align="center"><xsl:value-of select="response/AOP_CommentDesc_12"/></fo:block></fo:table-cell>
								</fo:table-row>
								
								
								<fo:table-row>
									<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial">D.	Bookkeeping/Write-up</fo:block></fo:table-cell>
									<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial" text-align="center"><xsl:value-of select="response/AOPOld_PercentageValue_4"/> %</fo:block></fo:table-cell>
									<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial" text-align="center"><xsl:value-of select="response/AOP_PercentageValue_4"/> %</fo:block></fo:table-cell>
									<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial">N.	Management Consulting /Business Planning</fo:block></fo:table-cell>
									<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial" text-align="center"><xsl:value-of select="response/AOPOld_PercentageValue_13"/> %</fo:block></fo:table-cell>
									<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial" text-align="center"><xsl:value-of select="response/AOP_PercentageValue_13"/> %</fo:block></fo:table-cell>
								</fo:table-row>
								
								<fo:table-row>
									<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" background-color="grey" text-align="center" font-family="Arial" font-weight="bold">Attestation</fo:block></fo:table-cell>
									<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial" text-align="center"></fo:block></fo:table-cell>
									<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial" text-align="center"></fo:block></fo:table-cell>
									<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial" >O.	Computer Related Services</fo:block></fo:table-cell>
									<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial" text-align="center"><xsl:value-of select="response/AOPOld_PercentageValue_14"/> %</fo:block></fo:table-cell>
									<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial" text-align="center"><xsl:value-of select="response/AOP_PercentageValue_14"/> %</fo:block></fo:table-cell>
								</fo:table-row>
								
								
								<fo:table-row>
									<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial">E.	Audit: Publicly-held clients</fo:block></fo:table-cell>
									<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial" text-align="center"><xsl:value-of select="response/AOPOld_PercentageValue_5"/> %</fo:block></fo:table-cell>
									<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial" text-align="center"><xsl:value-of select="response/AOP_PercentageValue_5"/> %</fo:block></fo:table-cell>
									<fo:table-cell border="0.5pt solid black"><fo:block font-size="7px" font-style="italic" font-family="Arial" font-weight="bold">Complete Computer Related Services Supplement  if entry in O above</fo:block></fo:table-cell>
									<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial" text-align="center"></fo:block></fo:table-cell>
									<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial" text-align="center"></fo:block></fo:table-cell>
								</fo:table-row>
								
								<fo:table-row>
									<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial">F.	Audit: Non-public clients</fo:block></fo:table-cell>
									<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial" text-align="center"><xsl:value-of select="response/AOPOld_PercentageValue_6"/> %</fo:block></fo:table-cell>
									<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial" text-align="center"><xsl:value-of select="response/AOP_PercentageValue_6"/> %</fo:block></fo:table-cell>
									<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial">P.	Business Valuation</fo:block></fo:table-cell>
									<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial" text-align="center"><xsl:value-of select="response/AOPOld_PercentageValue_15"/> %</fo:block></fo:table-cell>
									<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial" text-align="center"><xsl:value-of select="response/AOP_PercentageValue_15"/> %</fo:block></fo:table-cell>
								</fo:table-row>
								
								
								<fo:table-row>
									<fo:table-cell border="0.5pt solid black"><fo:block font-size="7px" font-style="italic" font-family="Arial">Complete the Audit Supplement if an entry in E or F above </fo:block></fo:table-cell>
									<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial" text-align="center"></fo:block></fo:table-cell>
									<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial" text-align="center"></fo:block></fo:table-cell>
									<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial">Q.	Forecasts/Projections</fo:block></fo:table-cell>
									<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial" text-align="center"><xsl:value-of select="response/AOPOld_PercentageValue_16"/> %</fo:block></fo:table-cell>
									<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial" text-align="center"><xsl:value-of select="response/AOP_PercentageValue_16"/> %</fo:block></fo:table-cell>
								</fo:table-row>
								
								<fo:table-row>
									<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial">G.	Review</fo:block></fo:table-cell>
									<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial" text-align="center"><xsl:value-of select="response/AOPOld_PercentageValue_7"/> %</fo:block></fo:table-cell>
									<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial" text-align="center"><xsl:value-of select="response/AOP_PercentageValue_7"/> %</fo:block></fo:table-cell>
									<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial">R.	Mergers/Acquisitions</fo:block></fo:table-cell>
									<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial" text-align="center"><xsl:value-of select="response/AOPOld_PercentageValue_17"/> %</fo:block></fo:table-cell>
									<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial" text-align="center"><xsl:value-of select="response/AOP_PercentageValue_17"/> %</fo:block></fo:table-cell>
								</fo:table-row>
								
								<fo:table-row>
									<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial">H.	Compilation</fo:block></fo:table-cell>
									<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial" text-align="center"><xsl:value-of select="response/AOPOld_PercentageValue_8"/> %</fo:block></fo:table-cell>
									<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial" text-align="center"><xsl:value-of select="response/AOP_PercentageValue_8"/> %</fo:block></fo:table-cell>
									<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" background-color="grey" text-align="center" font-family="Arial" font-weight="bold">Special Services</fo:block></fo:table-cell>
									<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial" text-align="center"></fo:block></fo:table-cell>
									<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial" text-align="center"></fo:block></fo:table-cell>
								</fo:table-row>
								
								
								<fo:table-row>
									<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial">I.	Agreed upon Procedures</fo:block></fo:table-cell>
									<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial" text-align="center"><xsl:value-of select="response/AOPOld_PercentageValue_9"/> %</fo:block></fo:table-cell>
									<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial" text-align="center"><xsl:value-of select="response/AOP_PercentageValue_9"/> %</fo:block></fo:table-cell>
									<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial">S.	Business Management</fo:block></fo:table-cell>
									<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial" text-align="center"><xsl:value-of select="response/AOPOld_PercentageValue_18"/> %</fo:block></fo:table-cell>
									<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial" text-align="center"><xsl:value-of select="response/AOP_PercentageValue_18"/> %</fo:block></fo:table-cell>
								</fo:table-row>
								
								
								<fo:table-row>
									<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial">Please describe</fo:block></fo:table-cell>
									<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial" text-align="center"><xsl:value-of select="response/AOPOld_CommentDesc_9"/></fo:block></fo:table-cell>
									<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial" text-align="center"><xsl:value-of select="response/AOP_CommentDesc_9"/></fo:block></fo:table-cell>
									<fo:table-cell border="0.5pt solid black"><fo:block font-size="7px" font-style="italic" font-family="Arial" font-weight="bold">Complete the Business Management Supplement if an entry in S above</fo:block></fo:table-cell>
									<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial" text-align="center"></fo:block></fo:table-cell>
									<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial" text-align="center"></fo:block></fo:table-cell>
								</fo:table-row>
								
								<fo:table-row>
									<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial">J.	Other Assurance Services</fo:block></fo:table-cell>
									<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial" text-align="center"><xsl:value-of select="response/AOPOld_PercentageValue_10"/> %</fo:block></fo:table-cell>
									<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial" text-align="center"><xsl:value-of select="response/AOP_PercentageValue_10"/> %</fo:block></fo:table-cell>
									<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial">T.	SEC Work other than Audit</fo:block></fo:table-cell>
									<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial" text-align="center"><xsl:value-of select="response/AOPOld_PercentageValue_21"/> %</fo:block></fo:table-cell>
									<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial" text-align="center"><xsl:value-of select="response/AOP_PercentageValue_21"/> %</fo:block></fo:table-cell>
								</fo:table-row>
								
								
								<fo:table-row>
									<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial">Please describe</fo:block></fo:table-cell>
									<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial" text-align="center"><xsl:value-of select="response/AOPOld_CommentDesc_10"/></fo:block></fo:table-cell>
									<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial" text-align="center"><xsl:value-of select="response/AOP_CommentDesc_10"/></fo:block></fo:table-cell>
									<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial">U.	Investment / Financial Planning</fo:block></fo:table-cell>
									<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial" text-align="center"><xsl:value-of select="response/AOPOld_PercentageValue_19"/> %</fo:block></fo:table-cell>
									<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial" text-align="center"><xsl:value-of select="response/AOP_PercentageValue_19"/> %</fo:block></fo:table-cell>
								</fo:table-row>
								
								<fo:table-row>
									<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial" text-align="center"></fo:block></fo:table-cell>
									<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial" text-align="center"></fo:block></fo:table-cell>
									<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial" text-align="center"></fo:block></fo:table-cell>
									<fo:table-cell border="0.5pt solid black"><fo:block font-size="7px" font-style="italic" font-family="Arial" font-weight="bold">Complete the Investment Financial Planning Supplement if entry in U above</fo:block></fo:table-cell>
									<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial" text-align="center"></fo:block></fo:table-cell>
									<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial" text-align="center"></fo:block></fo:table-cell>
								</fo:table-row>
								
								
								
								<fo:table-row>
									<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial" text-align="center"></fo:block></fo:table-cell>
									<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial" text-align="center"></fo:block></fo:table-cell>
									<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial" text-align="center"></fo:block></fo:table-cell>
									<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial">V.	Erisa / Pension Plans</fo:block></fo:table-cell>
									<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial" text-align="center"><xsl:value-of select="response/AOPOld_PercentageValue_23"/> %</fo:block></fo:table-cell>
									<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial" text-align="center"><xsl:value-of select="response/AOP_PercentageValue_23"/> %</fo:block></fo:table-cell>
								</fo:table-row>
								
								
								<fo:table-row>
									<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial" text-align="center"></fo:block></fo:table-cell>
									<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial" text-align="center"></fo:block></fo:table-cell>
									<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial" text-align="center"></fo:block></fo:table-cell>
									<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial">W.	Trustee Services</fo:block></fo:table-cell>
									<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial" text-align="center"><xsl:value-of select="response/AOPOld_PercentageValue_24"/> %</fo:block></fo:table-cell>
									<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial" text-align="center"><xsl:value-of select="response/AOP_PercentageValue_24"/> %</fo:block></fo:table-cell>
								</fo:table-row>
								
								
								<fo:table-row>
									<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial" text-align="center"></fo:block></fo:table-cell>
									<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial" text-align="center"></fo:block></fo:table-cell>
									<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial" text-align="center"></fo:block></fo:table-cell>
									<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial">X.	Other</fo:block></fo:table-cell>
									<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial" text-align="center"><xsl:value-of select="response/AOPOld_PercentageValue_20"/> %</fo:block></fo:table-cell>
									<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial" text-align="center"><xsl:value-of select="response/AOP_PercentageValue_20"/> %</fo:block></fo:table-cell>
								</fo:table-row>
								
								<fo:table-row>
									<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial" text-align="center"></fo:block></fo:table-cell>
									<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial" text-align="center"></fo:block></fo:table-cell>
									<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial" text-align="center"></fo:block></fo:table-cell>
									<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial">Please describe</fo:block></fo:table-cell>
									<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial" text-align="center"><xsl:value-of select="response/AOPOld_CommentDesc_20"/></fo:block></fo:table-cell>
									<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial" text-align="center"><xsl:value-of select="response/AOP_CommentDesc_20"/></fo:block></fo:table-cell>
								</fo:table-row>
								
								<fo:table-row>
									<fo:table-cell><fo:block font-size="9px" font-family="Arial" text-align="center"></fo:block></fo:table-cell>
									<fo:table-cell><fo:block font-size="9px" font-family="Arial" text-align="center"></fo:block></fo:table-cell>
									<fo:table-cell><fo:block font-size="9px" font-family="Arial" text-align="center"></fo:block></fo:table-cell>
									<fo:table-cell><fo:block font-size="9px" font-family="Arial" font-weight="bold"></fo:block></fo:table-cell>
									<fo:table-cell><fo:block font-size="9px" font-family="Arial" text-align="center" font-weight="bold"><xsl:value-of select="response/aopold_total"/>%</fo:block></fo:table-cell>
									<fo:table-cell><fo:block font-size="9px" font-family="Arial" text-align="center" font-weight="bold"><xsl:value-of select="response/aop_total"/>%</fo:block></fo:table-cell>
								</fo:table-row>
							</fo:table-body>
						</fo:table>
						
				</fo:block>
				
				<!-- <fo:block break-after="page"></fo:block> -->
				
			
				
				
				
				
				<fo:block>
					<xsl:choose>
						<xsl:when test="response/firm_freeform_01/data/IsAnyFirmMeargedOrAcquired = 'Y'">
							<fo:table margin-top="3mm" border="0.0pt solid black">  
								<fo:table-column column-width="5mm"/>
								<fo:table-column column-width="154mm"/>
								<fo:table-column/>
								<fo:table-body>
								    <fo:table-row>
								    	<fo:table-cell><fo:block font-size="9px" font-family="Arial">4.</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial">Within the last year, has your Firm merged with or acquired the business of any sole practitioner, accounting Firm or other business entity?</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial" text-align="right">Yes <fo:external-graphic src="../AccountantIns/image/check-btn6.png"/>&#160;&#160;&#160;No <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/></fo:block></fo:table-cell>
							        </fo:table-row>
							    </fo:table-body>
							</fo:table>
						</xsl:when>
						<xsl:when test="response/firm_freeform_01/data/IsAnyFirmMeargedOrAcquired = 'N'">
							<fo:table margin-top="3mm" border="0.0pt solid black">  
								<fo:table-column column-width="5mm"/>
								<fo:table-column column-width="154mm"/>
								<fo:table-column/>
								<fo:table-body>
								    <fo:table-row>
								    	<fo:table-cell><fo:block font-size="9px" font-family="Arial">4.</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial">Within the last year, has your Firm merged with or acquired the business of any sole practitioner, accounting Firm or other business entity?</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial" text-align="right">Yes <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/>&#160;&#160;&#160;No <fo:external-graphic src="../AccountantIns/image/check-btn6.png"/></fo:block></fo:table-cell>
							        </fo:table-row>
							    </fo:table-body>
							</fo:table>
						</xsl:when>
						<xsl:otherwise>
							<fo:table margin-top="3mm" border="0.0pt solid black">  
								<fo:table-column column-width="5mm"/>
								<fo:table-column column-width="154mm"/>
								<fo:table-column/>
								<fo:table-body>
								    <fo:table-row>
								    	<fo:table-cell><fo:block font-size="9px" font-family="Arial">4.</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial">Within the last year, has your Firm merged with or acquired the business of any sole practitioner, accounting Firm or other business entity?</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial" text-align="right">Yes <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/>&#160;&#160;&#160;No <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/></fo:block></fo:table-cell>
							        </fo:table-row>
							    </fo:table-body>
							</fo:table>
						</xsl:otherwise>
					</xsl:choose>					
				</fo:block>
				
				<fo:block>
					<xsl:choose>
						<xsl:when test="response/firm_freeform_01/data/IsControlClientFunds = 'Y'">
							<fo:table margin-top="3mm" border="0.0pt solid black">  
								<fo:table-column column-width="5mm"/>
								<fo:table-column column-width="154mm"/>
								<fo:table-column/>
								<fo:table-body>
								    <fo:table-row>
								    	<fo:table-cell><fo:block font-size="9px" font-family="Arial">5.</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial">Within the past year has the firm entered into any new engagement requiring the Firm to control or disburse client funds other than for payroll or bookkeeping services?</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial" text-align="right">Yes <fo:external-graphic src="../AccountantIns/image/check-btn6.png"/>&#160;&#160;&#160;No <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/></fo:block></fo:table-cell>
							        </fo:table-row>
							    </fo:table-body>
							</fo:table>
						</xsl:when>
						<xsl:when test="response/firm_freeform_01/data/IsControlClientFunds = 'N'">
							<fo:table margin-top="3mm" border="0.0pt solid black">  
								<fo:table-column column-width="5mm"/>
								<fo:table-column column-width="154mm"/>
								<fo:table-column/>
								<fo:table-body>
								    <fo:table-row>
								    	<fo:table-cell><fo:block font-size="9px" font-family="Arial">5.</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial">Within the past year has the firm entered into any new engagement requiring the Firm to control or disburse client funds other than for payroll or bookkeeping services?</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial" text-align="right">Yes <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/>&#160;&#160;&#160;No <fo:external-graphic src="../AccountantIns/image/check-btn6.png"/></fo:block></fo:table-cell>
							        </fo:table-row>
							    </fo:table-body>
							</fo:table>
						</xsl:when>
						<xsl:otherwise>
							<fo:table margin-top="3mm" border="0.0pt solid black">  
								<fo:table-column column-width="5mm"/>
								<fo:table-column column-width="154mm"/>
								<fo:table-column/>
								<fo:table-body>
								    <fo:table-row>
								    	<fo:table-cell><fo:block font-size="9px" font-family="Arial">5.</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial">Within the past year has the firm entered into any new engagement requiring the Firm to control or disburse client funds other than for payroll or bookkeeping services?</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial" text-align="right">Yes <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/>&#160;&#160;&#160;No <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/></fo:block></fo:table-cell>
							        </fo:table-row>
							    </fo:table-body>
							</fo:table>
						</xsl:otherwise>
					</xsl:choose>
					<fo:table margin-top="1mm" border="0.0pt solid black">  
						<fo:table-column column-width="5mm"/>
						<fo:table-column/>
						<fo:table-body>
							<fo:table-row>
						    	<fo:table-cell><fo:block font-size="9px" font-family="Arial"></fo:block></fo:table-cell>
					        	<fo:table-cell><fo:block font-size="9px" font-style="italic" font-weight="bold" font-family="Arial">If "Yes", please complete the BUSINESS MANAGEMENT SUPPLEMENT&#160; </fo:block></fo:table-cell>
					        </fo:table-row>
						</fo:table-body>
					</fo:table>  
				</fo:block>		
				
				
				
				
				
				<fo:block>
					<xsl:choose>
						<xsl:when test="response/firm_freeform_01/data/IsFirmsRevenueMorethan25Percent = 'Y'">
							<fo:table margin-top="3mm" border="0.0pt solid black">  
								<fo:table-column column-width="5mm"/>
								<fo:table-column column-width="154mm"/>
								<fo:table-column/>
								<fo:table-body>
								    <fo:table-row>
								    	<fo:table-cell><fo:block font-size="9px" font-family="Arial">6.</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial">During the last year has any client of the Firm represented more than 25% of the Firm's revenue?</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial" text-align="right">Yes <fo:external-graphic src="../AccountantIns/image/check-btn6.png"/>&#160;&#160;&#160;No <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/></fo:block></fo:table-cell>
							        </fo:table-row>
							    </fo:table-body>
							</fo:table>
						</xsl:when>
						<xsl:when test="response/firm_freeform_01/data/IsFirmsRevenueMorethan25Percent = 'N'">
							<fo:table margin-top="3mm" border="0.0pt solid black">  
								<fo:table-column column-width="5mm"/>
								<fo:table-column column-width="154mm"/>
								<fo:table-column/>
								<fo:table-body>
								    <fo:table-row>
								    	<fo:table-cell><fo:block font-size="9px" font-family="Arial">6.</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial">During the last year has any client of the Firm represented more than 25% of the Firm's revenue?</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial" text-align="right">Yes <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/>&#160;&#160;&#160;No <fo:external-graphic src="../AccountantIns/image/check-btn6.png"/></fo:block></fo:table-cell>
							        </fo:table-row>
							    </fo:table-body>
							</fo:table>
						</xsl:when>
						<xsl:otherwise>
							<fo:table margin-top="3mm" border="0.0pt solid black">  
								<fo:table-column column-width="5mm"/>
								<fo:table-column column-width="154mm"/>
								<fo:table-column/>
								<fo:table-body>
								    <fo:table-row>
								    	<fo:table-cell><fo:block font-size="9px" font-family="Arial">6.</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial">During the last year has any client of the Firm represented more than 25% of the Firm's revenue?</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial" text-align="right">Yes <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/>&#160;&#160;&#160;No <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/></fo:block></fo:table-cell>
							        </fo:table-row>
							    </fo:table-body>
							</fo:table>
						</xsl:otherwise>
					</xsl:choose>
					<fo:table margin-top="1mm" border="0.0pt solid black">  
						<fo:table-column column-width="5mm"/>
						<fo:table-column column-width="154mm"/>
						<fo:table-column/>
						<fo:table-body>
							<fo:table-row>
						    	<fo:table-cell><fo:block font-size="9px" font-family="Arial">a.</fo:block></fo:table-cell>
					        	<fo:table-cell><fo:block font-size="9px" font-family="Arial">If Yes, please indicate percentage of revenue from largest revenue client (include client’s affiliated entities):</fo:block></fo:table-cell>
					        	<fo:table-cell><fo:block font-size="9px" font-family="Arial"><xsl:value-of select="response/firm_freeform_01/data/PercentOfRevenueFromLargestClient"></xsl:value-of></fo:block></fo:table-cell>
					        </fo:table-row>
					        <fo:table-row>
						    	<fo:table-cell><fo:block font-size="9px" font-family="Arial"></fo:block></fo:table-cell>
					        	<fo:table-cell><fo:block font-size="9px" font-family="Arial">Services rendered : </fo:block></fo:table-cell>
					        	<fo:table-cell><fo:block font-size="9px" font-family="Arial"><xsl:value-of select="response/firm_freeform_01/data/ServiceIndustryofLargestClient"></xsl:value-of></fo:block></fo:table-cell>
					        </fo:table-row>
					        
					        <fo:table-row>
						    	<fo:table-cell><fo:block font-size="9px" font-family="Arial">b.</fo:block></fo:table-cell>
					        	<fo:table-cell><fo:block font-size="9px" font-family="Arial">Please indicate the percentage of revenue from second largest revenue client (include client’s affiliated entities):</fo:block></fo:table-cell>
					        	<fo:table-cell><fo:block font-size="9px" font-family="Arial"><xsl:value-of select="response/firm_freeform_01/data/PercentOfRevenueFromSecondLargestClient"></xsl:value-of></fo:block></fo:table-cell>
					        </fo:table-row>
					        <fo:table-row>
						    	<fo:table-cell><fo:block font-size="9px" font-family="Arial"></fo:block></fo:table-cell>
					        	<fo:table-cell><fo:block font-size="9px" font-family="Arial">Services rendered : </fo:block></fo:table-cell>
					        	<fo:table-cell><fo:block font-size="9px" font-family="Arial"><xsl:value-of select="response/firm_freeform_01/data/ServiceIndustryofSecondLargestClient"></xsl:value-of></fo:block></fo:table-cell>
					        </fo:table-row>
						</fo:table-body>
					</fo:table>  
				</fo:block>		
				
				
				
				<fo:block>
					<xsl:choose>
						<xsl:when test="response/firm_freeform_01/data/IsServedAsTrustee = 'Y'">
							<fo:table margin-top="3mm" border="0.0pt solid black">  
								<fo:table-column column-width="5mm"/>
								<fo:table-column column-width="154mm"/>
								<fo:table-column/>
								<fo:table-body>
								    <fo:table-row>
								    	<fo:table-cell><fo:block font-size="9px" font-family="Arial">7.</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial">Within the past year has the Firm, or its personnel entered into a new engagement serving as a trustee, co-trustee, executor, administrator or personal representative for any client?</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial" text-align="right">Yes <fo:external-graphic src="../AccountantIns/image/check-btn6.png"/>&#160;&#160;&#160;No <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/></fo:block></fo:table-cell>
							        </fo:table-row>
							    </fo:table-body>
							</fo:table>
						</xsl:when>
						<xsl:when test="response/firm_freeform_01/data/IsServedAsTrustee = 'N'">
							<fo:table margin-top="3mm" border="0.0pt solid black">  
								<fo:table-column column-width="5mm"/>
								<fo:table-column column-width="154mm"/>
								<fo:table-column/>
								<fo:table-body>
								    <fo:table-row>
								    	<fo:table-cell><fo:block font-size="9px" font-family="Arial">7.</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial">Within the past year has the Firm, or its personnel entered into a new engagement serving as a trustee, co-trustee, executor, administrator or personal representative for any client?</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial" text-align="right">Yes <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/>&#160;&#160;&#160;No <fo:external-graphic src="../AccountantIns/image/check-btn6.png"/></fo:block></fo:table-cell>
							        </fo:table-row>
							    </fo:table-body>
							</fo:table>
						</xsl:when>
						<xsl:otherwise>
							<fo:table margin-top="3mm" border="0.0pt solid black">  
								<fo:table-column column-width="5mm"/>
								<fo:table-column column-width="154mm"/>
								<fo:table-column/>
								<fo:table-body>
								    <fo:table-row>
								    	<fo:table-cell><fo:block font-size="9px" font-family="Arial">7.</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial">Within the past year has the Firm, or its personnel entered into a new engagement serving as a trustee, co-trustee, executor, administrator or personal representative for any client?</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial" text-align="right">Yes <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/>&#160;&#160;&#160;No <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/></fo:block></fo:table-cell>
							        </fo:table-row>
							    </fo:table-body>
							</fo:table>
						</xsl:otherwise>
					</xsl:choose>
					<fo:table margin-top="1mm" border="0.0pt solid black">  
						<fo:table-column column-width="5mm"/>
						<fo:table-column/>
						<fo:table-body>
							<fo:table-row>
						    	<fo:table-cell><fo:block font-size="9px" font-family="Arial"></fo:block></fo:table-cell>
					        	<fo:table-cell><fo:block font-size="9px" font-style="italic" font-family="Arial">If the answer to question 7 is yes, please complete the following information for each new trust or estate below:</fo:block></fo:table-cell>
					        </fo:table-row>
						</fo:table-body>
					</fo:table>  
					
					<fo:table margin-top="1mm">  
						<fo:table-column column-width="5mm"/>
						<fo:table-column/>
						<fo:table-body>       
					        <fo:table-row>
					        	<fo:table-cell><fo:block font-size="9px" font-family="Arial"></fo:block></fo:table-cell>
					        	<fo:table-cell>
					        		<fo:block>
					        			<fo:table margin-top="2mm">
									         <fo:table-column />	    		         
									         <fo:table-column/>
									         <fo:table-column/>
									         <fo:table-column/>
									         <fo:table-column/>
									         <fo:table-column/>
									        
											 <fo:table-body>
											 	<fo:table-row>							
									         		<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial" text-align="center" font-weight="bold">Name of Trust or Estate</fo:block></fo:table-cell>
									         		<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial" text-align="center" font-weight="bold">Start Date of Engagement</fo:block></fo:table-cell>
									         		<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial" text-align="center" font-weight="bold">Value of Assests</fo:block></fo:table-cell>
									         		<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial" text-align="center" font-weight="bold">Annual Income of Assets</fo:block></fo:table-cell>
									         		<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial" text-align="center" font-weight="bold">*Type</fo:block></fo:table-cell>
									         		<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial" text-align="center" font-weight="bold">**Beneficiary Interest</fo:block></fo:table-cell>
									         		
									         	</fo:table-row>
									         	<xsl:if test="response/firm_freeform_01/data/IsServedAsTrustee = 'Y'">
											 	<xsl:for-each select="response/general_list_01/data">
									         		<fo:table-row>							
									         		<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial" text-align="center"><xsl:value-of select="NameOfTrust"/></fo:block></fo:table-cell>
									         		<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial" text-align="center"><xsl:value-of select="StartEngagemenTrusttDate"/></fo:block></fo:table-cell>
									         		<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial" text-align="center"><xsl:value-of select="ValueOfAssetsTrust"/></fo:block></fo:table-cell>
									         		<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial" text-align="center"><xsl:value-of select="AnnualIncomeofAssetsTrust"/></fo:block></fo:table-cell>
									         		<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial" text-align="center"><xsl:value-of select="TypeofTrustDesc"/></fo:block></fo:table-cell>
									         		<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial" text-align="center"><xsl:value-of select="IsBeneficiaryInterestTrust"/></fo:block></fo:table-cell>									         		
									         	</fo:table-row>								
												</xsl:for-each> 
												</xsl:if>          
											</fo:table-body>
										</fo:table>
					        		</fo:block>
					        	</fo:table-cell>
					        </fo:table-row>
					    </fo:table-body>
					</fo:table> 
					 
					<fo:table margin-top="1mm" border="0.0pt solid black">  
						<fo:table-column column-width="5mm"/>
						<fo:table-column/>
						<fo:table-body>
					        <fo:table-row>
					        	<fo:table-cell><fo:block></fo:block></fo:table-cell>
					        	<fo:table-cell><fo:block font-size="8px" font-family="Arial">* E = Estate P = Personal/Family trusts B = Business trusts F = Foundations C = Charities R = Real Estate I = Irrevocable Life Insurance trust</fo:block></fo:table-cell>
					        </fo:table-row>
					    </fo:table-body>
					</fo:table> 
					<fo:table margin-top="1mm" border="0.0pt solid black">  
						<fo:table-column column-width="5mm"/>
						<fo:table-column/>
						<fo:table-body>
					        <fo:table-row>
					        	<fo:table-cell><fo:block></fo:block></fo:table-cell>
					        	<fo:table-cell><fo:block font-size="6px" font-family="Arial">**Beneficiary interest means any personal interest you or your relatives might have as heir or beneficiary of the trust or estate funds, other than customary fees as trustee, administrator, executor or personal representative to which you are entitled.</fo:block></fo:table-cell>
					        </fo:table-row>
					    </fo:table-body>
					</fo:table>
				</fo:block>	
				
				
				
				<fo:block>
					<xsl:choose>
						<xsl:when test="response/firm_freeform_01/data/IsFirmRenderedServiceForAnyClient = 'Y'">
							<fo:table margin-top="3mm" border="0.0pt solid black">  
								<fo:table-column column-width="5mm"/>
								<fo:table-column column-width="154mm"/>
								<fo:table-column/>
								<fo:table-body>
								    <fo:table-row>
								    	<fo:table-cell><fo:block font-size="9px" font-family="Arial">8.</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial">Within the past year has the Firm entered into a new engagement serving as an officer, director, partner, manager or other member of a client’s governing body?</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial" text-align="right">Yes <fo:external-graphic src="../AccountantIns/image/check-btn6.png"/>&#160;&#160;&#160;No <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/></fo:block></fo:table-cell>
							        </fo:table-row>
							    </fo:table-body>
							</fo:table>
						</xsl:when>
						<xsl:when test="response/firm_freeform_01/data/IsFirmRenderedServiceForAnyClient = 'N'">
							<fo:table margin-top="3mm" border="0.0pt solid black">  
								<fo:table-column column-width="5mm"/>
								<fo:table-column column-width="154mm"/>
								<fo:table-column/>
								<fo:table-body>
								    <fo:table-row>
								    	<fo:table-cell><fo:block font-size="9px" font-family="Arial">8.</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial">Within the past year has the Firm entered into a new engagement serving as an officer, director, partner, manager or other member of a client’s governing body?</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial" text-align="right">Yes <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/>&#160;&#160;&#160;No <fo:external-graphic src="../AccountantIns/image/check-btn6.png"/></fo:block></fo:table-cell>
							        </fo:table-row>
							    </fo:table-body>
							</fo:table>
						</xsl:when>
						<xsl:otherwise>
							<fo:table margin-top="3mm" border="0.0pt solid black">  
								<fo:table-column column-width="5mm"/>
								<fo:table-column column-width="154mm"/>
								<fo:table-column/>
								<fo:table-body>
								    <fo:table-row>
								    	<fo:table-cell><fo:block font-size="9px" font-family="Arial">8.</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial">Within the past year has the Firm entered into a new engagement serving as an officer, director, partner, manager or other member of a client’s governing body?</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial" text-align="right">Yes <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/>&#160;&#160;&#160;No <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/></fo:block></fo:table-cell>
							        </fo:table-row>
							    </fo:table-body>
							</fo:table>
						</xsl:otherwise>
					</xsl:choose>
					<fo:table margin-top="1mm" border="0.0pt solid black">  
						<fo:table-column column-width="5mm"/>
						<fo:table-column/>
						<fo:table-body>
							<fo:table-row>
						    	<fo:table-cell><fo:block font-size="9px" font-family="Arial"></fo:block></fo:table-cell>
					        	<fo:table-cell><fo:block font-size="9px" font-style="italic" font-family="Arial">If yes to Question 7, please complete the information below:</fo:block></fo:table-cell>
					        </fo:table-row>
						</fo:table-body>
					</fo:table>  
					
					<fo:table margin-top="1mm">  
						<fo:table-column column-width="5mm"/>
						<fo:table-column/>
						<fo:table-body>       
					        <fo:table-row>
					        	<fo:table-cell><fo:block font-size="9px" font-family="Arial"></fo:block></fo:table-cell>
					        	<fo:table-cell>
					        		<fo:block>
					        			<fo:table margin-top="2mm">
									         <fo:table-column />	    		         
									         <fo:table-column/>
									         <fo:table-column/>
									         <fo:table-column/>
									         <fo:table-column />	    		         
									         <fo:table-column/>
									         <fo:table-column/>
									         <fo:table-column/>
									        
											 <fo:table-body>
											 	<fo:table-row>							
									         		<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial" text-align="center" font-weight="bold">Firm Member</fo:block></fo:table-cell>
									         		<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial" text-align="center" font-weight="bold">Entity Name</fo:block></fo:table-cell>
									         		<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial" text-align="center" font-weight="bold">Industry</fo:block></fo:table-cell>
									         		<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial" text-align="center" font-weight="bold">Position held</fo:block></fo:table-cell>
									         		<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial" text-align="center" font-weight="bold">Is Director / Officer Insurance in force?</fo:block></fo:table-cell>
									         		<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial" text-align="center" font-weight="bold">Percent Equity Interest</fo:block></fo:table-cell>
									         		<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial" text-align="center" font-weight="bold">Firm Services</fo:block></fo:table-cell>
									         		<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial" text-align="center" font-weight="bold">Does the individual listed perform these services?</fo:block></fo:table-cell>
									         		
									         	</fo:table-row>
									         	<xsl:if test="response/firm_freeform_01/data/IsFirmRenderedServiceForAnyClient = 'Y'">
											 	<xsl:for-each select="response/general_list_04/data">
									         		<fo:table-row>							
									         		<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial" text-align="center"><xsl:value-of select="ClientNameOsi"/></fo:block></fo:table-cell>
									         		<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial" text-align="center"><xsl:value-of select="EntityNameOsi"/></fo:block></fo:table-cell>
									         		<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial" text-align="center"><xsl:value-of select="ClientIndustryDesc"/></fo:block></fo:table-cell>
									         		<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial" text-align="center"><xsl:value-of select="PositionHeldOsi"/></fo:block></fo:table-cell>
									         		<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial" text-align="center"><xsl:value-of select="DOInsuranceOsi"/></fo:block></fo:table-cell>
									         		<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial" text-align="center"><xsl:value-of select="PercentEquityInterestofOsi"/></fo:block></fo:table-cell>
									         		<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial" text-align="center"><xsl:value-of select="ServiceIndustryDesc"/></fo:block></fo:table-cell>
									         		<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial" text-align="center"><xsl:value-of select="IndividualListedServicesOsi"/></fo:block></fo:table-cell>									         		
									         	</fo:table-row>								
												</xsl:for-each>
												</xsl:if>           
											</fo:table-body>
										</fo:table>
					        		</fo:block>
					        	</fo:table-cell>
					        </fo:table-row>
					    </fo:table-body>
					</fo:table> 
				</fo:block>	
				
				
				
				
				<fo:block>
					<xsl:choose>
						<xsl:when test="response/firm_freeform_01/data/IsSuitInstituted = 'Y'">
							<fo:table margin-top="3mm" border="0.0pt solid black">  
								<fo:table-column column-width="5mm"/>
								<fo:table-column column-width="154mm"/>
								<fo:table-column/>
								<fo:table-body>
								    <fo:table-row>
								    	<fo:table-cell><fo:block font-size="9px" font-family="Arial">9.</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial">Within the past year, has your Firm instituted suit to collect fees, including in small claims court?</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial" text-align="right">Yes <fo:external-graphic src="../AccountantIns/image/check-btn6.png"/>&#160;&#160;&#160;No <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/></fo:block></fo:table-cell>
							        </fo:table-row>
							    </fo:table-body>
							</fo:table>
						</xsl:when>
						<xsl:when test="response/firm_freeform_01/data/IsSuitInstituted = 'N'">
							<fo:table margin-top="3mm" border="0.0pt solid black">  
								<fo:table-column column-width="5mm"/>
								<fo:table-column column-width="154mm"/>
								<fo:table-column/>
								<fo:table-body>
								    <fo:table-row>
								    	<fo:table-cell><fo:block font-size="9px" font-family="Arial">9.</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial">Within the past year, has your Firm instituted suit to collect fees, including in small claims court?</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial" text-align="right">Yes <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/>&#160;&#160;&#160;No <fo:external-graphic src="../AccountantIns/image/check-btn6.png"/></fo:block></fo:table-cell>
							        </fo:table-row>
							    </fo:table-body>
							</fo:table>
						</xsl:when>
						<xsl:otherwise>
							<fo:table margin-top="3mm" border="0.0pt solid black">  
								<fo:table-column column-width="5mm"/>
								<fo:table-column column-width="154mm"/>
								<fo:table-column/>
								<fo:table-body>
								    <fo:table-row>
								    	<fo:table-cell><fo:block font-size="9px" font-family="Arial">9.</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial">Within the past year, has your Firm instituted suit to collect fees, including in small claims court?</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial" text-align="right">Yes <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/>&#160;&#160;&#160;No <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/></fo:block></fo:table-cell>
							        </fo:table-row>
							    </fo:table-body>
							</fo:table>
						</xsl:otherwise>
					</xsl:choose>
					<fo:table margin-top="1mm" border="0.0pt solid black">  
						<fo:table-column column-width="5mm"/>
						<fo:table-column/>
						<fo:table-body>
							<fo:table-row>
						    	<fo:table-cell><fo:block font-size="9px" font-family="Arial"></fo:block></fo:table-cell>
					        	<fo:table-cell><fo:block font-size="9px" font-style="italic" font-weight="bold" font-family="Arial">If Yes, complete the FEE SUIT SUPPLEMENT</fo:block></fo:table-cell>
					        </fo:table-row>
						</fo:table-body>
					</fo:table>  
				</fo:block>	
				
				<fo:block>
					<fo:table margin-top="3mm" border="0.0pt solid black">  
								<fo:table-column column-width="5mm"/>
								<fo:table-column column-width="154mm"/>
								<fo:table-body>
								    <fo:table-row>
								    	<fo:table-cell><fo:block font-size="9px" font-family="Arial">10.	</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial">After inquiry of all owners, partners, officers and other professional staff of the Firm, within the past year have any past or present personnel : </fo:block></fo:table-cell>
							        	
							        </fo:table-row>
							    </fo:table-body>
					</fo:table>
					<xsl:choose>
						<xsl:when test="response/firm_freeform_01/data/IsAnyRegulatoryInquiry = 'Y'">
							<fo:table margin-top="3mm" border="0.0pt solid black">  
								<fo:table-column column-width="5mm"/>
								<fo:table-column column-width="154mm"/>
								<fo:table-column/>
								<fo:table-body>
								    <fo:table-row>
								    	<fo:table-cell><fo:block font-size="9px" font-family="Arial">a.	</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial">been the subject of any regulatory investigation or inquiry; suspended from practice; or charged, indicted, plead no contest (“nolo contendere”), plead guilty or convicted of any felony charge?</fo:block></fo:table-cell>
							        	
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial" text-align="right">Yes <fo:external-graphic src="../AccountantIns/image/check-btn6.png"/>&#160;&#160;&#160;No <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/></fo:block></fo:table-cell>
							        
							        </fo:table-row>
							    </fo:table-body>
							</fo:table>
						</xsl:when>
						<xsl:when test="response/firm_freeform_01/data/IsAnyRegulatoryInquiry = 'N'">
							<fo:table margin-top="3mm" border="0.0pt solid black">  
								<fo:table-column column-width="5mm"/>
								<fo:table-column column-width="154mm"/>
								<fo:table-column/>
								<fo:table-body>
								    <fo:table-row>
								    	<fo:table-cell><fo:block font-size="9px" font-family="Arial">a.	</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial">been the subject of any regulatory investigation or inquiry; suspended from practice; or charged, indicted, plead no contest (“nolo contendere”), plead guilty or convicted of any felony charge?</fo:block></fo:table-cell>
							        	
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial" text-align="right">Yes <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/>&#160;&#160;&#160;No <fo:external-graphic src="../AccountantIns/image/check-btn6.png"/></fo:block></fo:table-cell>
							        
							        </fo:table-row>
							    </fo:table-body>
							</fo:table>
						</xsl:when>
						<xsl:otherwise>
							<fo:table margin-top="3mm" border="0.0pt solid black">  
								<fo:table-column column-width="5mm"/>
								<fo:table-column column-width="154mm"/>
								<fo:table-column/>
								<fo:table-body>
								    <fo:table-row>
								    	<fo:table-cell><fo:block font-size="9px" font-family="Arial">a.	</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial">been the subject of any regulatory investigation or inquiry; suspended from practice; or charged, indicted, plead no contest (“nolo contendere”), plead guilty or convicted of any felony charge?</fo:block></fo:table-cell>
							        	
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial" text-align="right">Yes <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/>&#160;&#160;&#160;No <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/></fo:block></fo:table-cell>
							        
							        </fo:table-row>
							    </fo:table-body>
							</fo:table>
						</xsl:otherwise>
					</xsl:choose>
					<xsl:choose>
						<xsl:when test="response/firm_freeform_01/data/IsAwarenessOfAnyProfLiability = 'Y'">
							<fo:table margin-top="3mm" border="0.0pt solid black">  
								<fo:table-column column-width="5mm"/>
								<fo:table-column column-width="154mm"/>
								<fo:table-column/>
								<fo:table-body>
								    <fo:table-row>
								    	<fo:table-cell><fo:block font-size="9px" font-family="Arial">b.	</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial">become aware of any accountants professional liability claims made against the Firm, Firm affiliates, their personnel, or the Firm’s predecessors in business?</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial" text-align="right">Yes <fo:external-graphic src="../AccountantIns/image/check-btn6.png"/>&#160;&#160;&#160;No <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/></fo:block></fo:table-cell>
							        </fo:table-row>
							    </fo:table-body>
							</fo:table>
							</xsl:when>
							<xsl:when test="response/firm_freeform_01/data/IsAwarenessOfAnyProfLiability = 'N'">
							<fo:table margin-top="3mm" border="0.0pt solid black">  
								<fo:table-column column-width="5mm"/>
								<fo:table-column column-width="154mm"/>
								<fo:table-column/>
								<fo:table-body>
								    <fo:table-row>
								    	<fo:table-cell><fo:block font-size="9px" font-family="Arial">b.	</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial">become aware of any accountants professional liability claims made against the Firm, Firm affiliates, their personnel, or the Firm’s predecessors in business?</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial" text-align="right">Yes <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/>&#160;&#160;&#160;No <fo:external-graphic src="../AccountantIns/image/check-btn6.png"/></fo:block></fo:table-cell>
							        </fo:table-row>
							    </fo:table-body>
							</fo:table>
							</xsl:when>
							<xsl:otherwise>
								<fo:table margin-top="3mm" border="0.0pt solid black">  
								<fo:table-column column-width="5mm"/>
								<fo:table-column column-width="154mm"/>
								<fo:table-column/>
								<fo:table-body>
								    <fo:table-row>
								    	<fo:table-cell><fo:block font-size="9px" font-family="Arial">b.	</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial">become aware of any accountants professional liability claims made against the Firm, Firm affiliates, their personnel, or the Firm’s predecessors in business?</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial" text-align="right">Yes <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/>&#160;&#160;&#160;No <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/></fo:block></fo:table-cell>
							        </fo:table-row>
							    </fo:table-body>
							</fo:table>
							</xsl:otherwise>
						</xsl:choose>
						<xsl:choose>
							<xsl:when test="response/firm_freeform_01/data/IsAwarenessOfAnyProfLiabilitySuitAgainst = 'Y'">
							<fo:table margin-top="3mm" border="0.0pt solid black">  
								<fo:table-column column-width="5mm"/>
								<fo:table-column column-width="154mm"/>
								<fo:table-column/>
								<fo:table-body>
								    <fo:table-row>
								    	<fo:table-cell><fo:block font-size="9px" font-family="Arial">c.	</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial">become aware of any act, omission, or  fee dispute which could be the basis of a claim against the Firm,  its personnel, or the Firm predecessors in business?</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial" text-align="right">Yes <fo:external-graphic src="../AccountantIns/image/check-btn6.png"/>&#160;&#160;&#160;No <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/></fo:block></fo:table-cell>
							        </fo:table-row>
							    </fo:table-body>
							</fo:table>
							</xsl:when>	
							
							<xsl:when test="response/firm_freeform_01/data/IsAwarenessOfAnyProfLiabilitySuitAgainst = 'N'">
							<fo:table margin-top="3mm" border="0.0pt solid black">  
								<fo:table-column column-width="5mm"/>
								<fo:table-column column-width="154mm"/>
								<fo:table-column/>
								<fo:table-body>
								    <fo:table-row>
								    	<fo:table-cell><fo:block font-size="9px" font-family="Arial">c.	</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial">become aware of any act, omission, or  fee dispute which could be the basis of a claim against the Firm,  its personnel, or the Firm predecessors in business?</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial" text-align="right">Yes <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/>&#160;&#160;&#160;No <fo:external-graphic src="../AccountantIns/image/check-btn6.png"/></fo:block></fo:table-cell>
							        </fo:table-row>
							    </fo:table-body>
							</fo:table>
							</xsl:when>
							<xsl:otherwise>
								<fo:table margin-top="3mm" border="0.0pt solid black">  
								<fo:table-column column-width="5mm"/>
								<fo:table-column column-width="154mm"/>
								<fo:table-column/>
								<fo:table-body>
								    <fo:table-row>
								    	<fo:table-cell><fo:block font-size="9px" font-family="Arial">c.	</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial">become aware of any act, omission, or  fee dispute which could be the basis of a claim against the Firm,  its personnel, or the Firm predecessors in business?</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial" text-align="right">Yes <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/>&#160;&#160;&#160;No <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/></fo:block></fo:table-cell>
							        </fo:table-row>
							    </fo:table-body>
							</fo:table>
							</xsl:otherwise>
						</xsl:choose>	
					
					
					<fo:table margin-top="1mm" border="0.0pt solid black">  
						<fo:table-column column-width="5mm"/>
						<fo:table-column/>
						<fo:table-body>
							<fo:table-row>
						    	<fo:table-cell><fo:block font-size="9px" font-family="Arial"></fo:block></fo:table-cell>
					        	<fo:table-cell><fo:block font-size="9px" font-style="italic" font-weight="bold" font-family="Arial">If yes to 10 b or c above, please complete the CLAIM SUPPLEMENT for each claim or potential claim.</fo:block></fo:table-cell>
					        </fo:table-row>
						</fo:table-body>
					</fo:table>  					
				</fo:block>	
				
				<fo:block margin-top="1mm">
					<fo:table border="0.0pt solid black">
						<fo:table-column/>
						<fo:table-body>
							<fo:table-row>						    	
					        	<fo:table-cell><fo:block font-size="9px" font-weight="bold" font-family="Arial">NOTE: THE POLICY FOR WHICH THIS APPLICATION IS BEING MADE SHALL NOT APPLY TO ANY INCIDENTS OR CLAIMS DETAILED OR WHICH SHOULD HAVE BEEN DETAILED IN THE QUESTION 10 a, b or c ABOVE.</fo:block></fo:table-cell>
					        </fo:table-row>
						</fo:table-body>
					</fo:table>  
				</fo:block>
				<fo:block break-after="page"></fo:block>
			
					<fo:block margin-top="3mm">
						<xsl:if test="response/policy_freeform_01/data/StateCode='AK'">
							<fo:block font-family="Arial" font-size="9px">
								NOTICE: "A PERSON WHO KNOWINGLY AND WITH INTENT TO INJURE, DEFRAUD, OR DECEIVE AN INSURANCE COMPANY FILES A <fo:inline font-weight="bold" font-style="italic">CLAIM</fo:inline> CONTAINING FALSE, INCOMPLETE, OR MISLEADING
									INFORMATION MAY BE PROSECUTED UNDER STATE LAW."
							</fo:block>	
						</xsl:if>
						<xsl:if test="response/policy_freeform_01/data/StateCode='AR'">
							<fo:block font-family="Arial" font-size="9px">
								NOTICE: "ANY PERSON WHO KNOWINGLY PRESENTS A FALSE OR FRAUDULENT <fo:inline font-weight="bold" font-style="italic">CLAIM</fo:inline> FOR PAYMENT OF A <fo:inline font-weight="bold" font-style="italic">LOSS</fo:inline> OR BENEFIT OR KNOWINGLY PRESENTS FALSE INFORMATION IN AN APPLICATION FOR INSURANCE IS GUILTY OF A CRIME AND MAY BE SUBJECT TO FINES AND CONFINEMENT IN PRISON."
							</fo:block>	
						</xsl:if>
						<xsl:if test="response/policy_freeform_01/data/StateCode='AZ'">
							<fo:block font-family="Arial" font-size="9px">
								NOTICE: "ANY PERSON WHO KNOWINGLY PRESENTS A FALSE OR FRAUDULENT <fo:inline font-weight="bold" font-style="italic">CLAIM</fo:inline> FOR PAYMENT OF A <fo:inline font-weight="bold" font-style="italic">LOSS</fo:inline> IS SUBJECT TO CRIMINAL AND CIVIL PENALTIES."
							</fo:block>	
						</xsl:if>
						<xsl:if test="response/policy_freeform_01/data/StateCode='CA'">
							<fo:block font-family="Arial" font-size="9px">
								NOTICE: "ANY PERSON WHO KNOWINGLY PRESENTS A FALSE OR FRAUDULENT <fo:inline font-weight="bold" font-style="italic">CLAIM</fo:inline> FOR THE PAYMENT OF A <fo:inline font-weight="bold" font-style="italic">LOSS</fo:inline> IS GUILTY OF A CRIME AND MAY BE SUBJECT TO FINES AND CONFINEMENT IN STATE PRISON."
							</fo:block>	
						</xsl:if>
						<xsl:if test="response/policy_freeform_01/data/StateCode='CO'">
							<fo:block font-family="Arial" font-size="9px">
								NOTICE: "IT IS UNLAWFUL TO KNOWINGLY PROVIDE FALSE, INCOMPLETE OR MISLEADING FACTS OR INFORMATION TO AN INSURANCE COMPANY FOR THE PURPOSE OF DEFRAUDING OR ATTEMPTING TO DEFRAUD THE COMPANY.  PENALTIES MAY INCLUDE IMPRISONMENT, FINES, DENIAL OF INSURANCE AND CIVIL DAMAGES.  ANY INSURANCE COMPANY OR AGENT OF AN INSURANCE COMPANY WHO KNOWINGLY PROVIDES FALSE, INCOMPLETE OR MISLEADING FACTS OR INFORMATION TO A POLICYHOLDER OR CLAIMANT FOR THE PURPOSE OF DEFRAUDING OR ATTEMPTING TO DEFRAUD THE POLICYHOLDER OR CLAIMANT WITH REGARD TO A SETTLEMENT OR AWARD PAYABLE FROM INSURANCE PROCEEDS SHALL BE REPORTED TO THE COLORADO DIVISION OF INSURANCE WITHIN THE DEPARTMENT OF REGULATORY AGENCIES."
							</fo:block>	
						</xsl:if>
						<xsl:if test="response/policy_freeform_01/data/StateCode='DC'">
							<fo:block font-family="Arial" font-size="9px">
								NOTICE: "WARNING: IT IS A CRIME TO PROVIDE FALSE OR MISLEADING INFORMATION TO AN <fo:inline font-weight="bold" font-style="italic">INSURER</fo:inline> FOR THE PURPOSE OF DEFRAUDING THE <fo:inline font-weight="bold" font-style="italic">INSURER</fo:inline> OR ANY OTHER PERSON.  PENALTIES INCLUDE IMPRISONMENT AND/OR FINES.  IN ADDITION, AN INSURER MAY DENY INSURANCE BENEFITS IF FALSE INFORMATION MATERIALLY RELATED TO A <fo:inline font-weight="bold" font-style="italic">CLAIM</fo:inline> WAS PROVIDED BY THE APPLICANT."
							</fo:block>	
						</xsl:if>
						<xsl:if test="response/policy_freeform_01/data/StateCode='FL'">
							<fo:block font-family="Arial" font-size="9px">
								NOTICE: "ANY PERSON WHO KNOWINGLY AND WITH INTENT TO INJURE, DEFRAUD OR DECEIVE ANY <fo:inline font-weight="bold" font-style="italic">INSURER</fo:inline>, FILES A STATEMENT OF <fo:inline font-weight="bold" font-style="italic">CLAIM</fo:inline> OR AN APPLICATION CONTAINING ANY FALSE, INCOMPLETE, OR MISLEADING INFORMATION IS GUILTY OF A FELONY OF THE THIRD DEGREE."
							</fo:block>	
						</xsl:if>
						<xsl:if test="response/policy_freeform_01/data/StateCode='HI'">
							<fo:block font-family="Arial" font-size="9px">
								NOTICE: "FOR OUR PROTECTION, HAWAII LAW REQUIRES YOU TO BE INFORMED THAT PRESENTING A FRAUDULENT <fo:inline font-weight="bold" font-style="italic">CLAIM</fo:inline> FOR PAYMENT OF A <fo:inline font-weight="bold" font-style="italic">LOSS</fo:inline> OR BENEFIT IS A CRIME PUNISHABLE BY FINES OR IMPRISONMENT OR BOTH."
							</fo:block>	
						</xsl:if>
						<xsl:if test="response/policy_freeform_01/data/StateCode='ID'">
							<fo:block font-family="Arial" font-size="9px">
								NOTICE: "ANY PERSON WHO KNOWINGLY AND WITH INTENT TO DEFRAUD OR DECEIVE ANY INSURANCE COMPANY, FILES A STATEMENT CONTAINING ANY FALSE, INCOMPLETE OR MISLEADING INFORMATION IS GUILTY OF A FELONY."
							</fo:block>	
						</xsl:if>
						<xsl:if test="response/policy_freeform_01/data/StateCode='IN'">
							<fo:block font-family="Arial" font-size="9px">
								NOTICE: "ANY PERSON WHO KNOWINGLY AND WITH INTENT TO DEFRAUD AN <fo:inline font-weight="bold" font-style="italic">INSURER</fo:inline> FILES A STATEMENT OF <fo:inline font-weight="bold" font-style="italic">CLAIM</fo:inline> CONTAINING ANY FALSE, INCOMPLETE OR MISLEADING INFORMATION COMMITS A FELONY."
							</fo:block>	
						</xsl:if>
						<xsl:if test="response/policy_freeform_01/data/StateCode='KY'">
							<fo:block font-family="Arial" font-size="9px">
								NOTICE: "ANY PERSON WHO KNOWINGLY AND WITH INTENT TO DEFRAUD ANY INSURANCE COMPANY OR OTHER PERSON FILES AN APPLICATION FOR INSURANCE CONTAINING ANY MATERIALLY FALSE INFORMATION OR CONCEALS, FOR THE PURPOSE OF MISLEADING, INFORMATION CONCERNING ANY FACT MATERIAL THERETO COMMITS A FRAUDULENT ACT, WHICH IS A CRIME."
							</fo:block>	
						</xsl:if>
						<xsl:if test="response/policy_freeform_01/data/StateCode='LA'">
							<fo:block font-family="Arial" font-size="9px">
								NOTICE: "ANY PERSON WHO KNOWINGLY PRESENTS A FALSE OR FRAUDULENT CLAIM FOR PAYMENT OF A LOSS OR BENEFIT OR KNOWINGLY PRESENTS FALSE INFORMATION IN AN APPLICATION FOR INSURANCE IS GUILTY OF A CRIME AND MAY BE SUBJECT TO FINES AND CONFINEMENT IN PRISON."
							</fo:block>	
						</xsl:if>
						<xsl:if test="response/policy_freeform_01/data/StateCode='ME'">
							<fo:block font-family="Arial" font-size="9px">
								NOTICE: "IT IS A CRIME TO KNOWINGLY PROVIDE FALSE, INCOMPLETE, OR MISLEADING INFORMATION TO AN INSURANCE COMPANY FOR THE PURPOSE OF DEFRAUDING THE COMPANY. PENALTIES MAY INCLUDE IMPRISONMENT, FINES, OR DENIAL OF INSURANCE BENEFITS."
							</fo:block>	
						</xsl:if>
						<xsl:if test="response/policy_freeform_01/data/StateCode='MD'">
							<fo:block font-family="Arial" font-size="9px">
								NOTICE: "ANY PERSON WHO KNOWINGLY AND WILLFULLY PRESENTS A FALSE OR FRAUDULENT CLAIM FOR PAYMENT OF A LOSS OR BENEFIT OR WHO KNOWINGLY AND WILLFULLY PRESENTS FALSE INFORMATION IN AN APPLICATION FOR INSURANCE IS GUILTY OF A CRIME AND MAY BE SUBJECT TO FINES AND CONFINEMENT TO PRISON."
							</fo:block>	
						</xsl:if>
						<xsl:if test="response/policy_freeform_01/data/StateCode='MA'">
							<fo:block font-family="Arial" font-size="9px">
								NOTICE: "ANY PERSON WHO KNOWINGLY AND WITH INTENT TO DEFRAUD ANY INSURANCE COMPANY OR ANTOHER PERSON FILES AN APPLICATION FOR INSURANCE OR STATEMENT OF CLAIM CONTAINING ANY MATERIALLY FALSE INFORMATION, OR CONCEALS FOR THE PURPOSE OF MISLEADING INFORMATION CONCERNING ANY FACT MATERIAL THERETO, COMMITS A FRAUDULENT INSURANCE ACT, WHICH IS A CRIME AND MAY SUBJECT THE PERSON TO CRIMINAL AND CIVIL PENALTIES."
							</fo:block>	
						</xsl:if>
						<xsl:if test="response/policy_freeform_01/data/StateCode='MN'">
							<fo:block font-family="Arial" font-size="9px">
								NOTICE: "A PERSON WHO SUBMITS AN APPLICATION OR FILES A CLAIM WITH INTENT TO DEFRAUD OR HELPS COMMIT A FRAUD AGAINST AN <fo:inline font-weight="bold" font-style="italic">INSURER</fo:inline> IS GUILTY OF A CRIME."
							</fo:block>	
						</xsl:if>
						<xsl:if test="response/policy_freeform_01/data/StateCode='MO'">
							<fo:block font-family="Arial" font-size="9px">
								NOTICE: "<fo:inline font-weight="bold" font-style="italic">DEFENSE COSTS</fo:inline> PAID UNDER THE POLICY PROVISIONS WILL REDUCE THE AVAILABLE LIMIT OF LIABILITY AND MAY EXHAUST THEM COMPLETELY."
							</fo:block>	
						</xsl:if>
						<xsl:if test="response/policy_freeform_01/data/StateCode='NE'">
							<fo:block font-family="Arial" font-size="9px">
								NOTICE: "ANY PERSON WHO KNOWINGLY AND WITH INTENT TO DEFRAUD ANY INSURANCE COMPANY OR ANOTHER PERSON FILES AN APPLICATION FOR INSURANCE OR STATEMENT OF CLAIM CONTAINING ANY MATERIALLY FALSE INFORMATION, OR CONCEALS FOR THE PURPOSE OF MISLEADING INFORMATION CONCERNING ANY FACT MATERIAL THERETO, COMMITS A FRAUDULENT INSURANCE ACT, WHICH IS A CRIME AND MAY SUBJECT THE PERSON TO CRIMINAL AND CIVIL PENALTIES."
							</fo:block>	
						</xsl:if>
						<xsl:if test="response/policy_freeform_01/data/StateCode='NJ'">
							<fo:block font-family="Arial" font-size="9px">
								NOTICE: "ANY PERSON WHO INCLUDES ANY INFORMATION ON AN APPLICATION FOR AN INSURANCE POLICY IS SUBJECT TO CRIMINAL AND CIVIL PENALTIES."
							</fo:block>	
						</xsl:if>
						<xsl:if test="response/policy_freeform_01/data/StateCode='NM'">
							<fo:block font-family="Arial" font-size="9px">
								NOTICE: "ANY PERSON WHO KNOWINGLY PRESENTS A FALSE OR FRAUDULENT CLAIM FOR PAYMENT OF A LOSS OR BENEFIT OR KNOWINGLY PRESENTS FALSE INFORMATION IN AN APPLICATION FOR INSURANCE IS GUILTY OF A CRIME AND MY BE SUBJECT TO CIVIL FINES AND CRIMINAL PENALTIES."
							</fo:block>	
						</xsl:if>
						<xsl:if test="response/policy_freeform_01/data/StateCode='NY'">
							<fo:block font-family="Arial" font-size="9px">
								NOTICE: "ANY PERSON WHO KNOWINGLY AND WITH INTENT TO DEFRAUD ANY INSURANCE COMPANY OR OTHER PERSON FILES AN APPLICATION FOR INSURANCE OR STATEMENT OF CLAIM CONTAINING ANY MATERIALLY FALSE INFORMATION, OR CONCEALS FOR THE PURPOSE OF MISLEADING, INFORMATION CONCERNING ANY FACT MATERIAL THERETO, COMMITS A FRAUDULENT INSURANCE ACT, WHICH IS A CRIME, AND SHALL ALSO BE SUBJECT TO A CIVIL PENALTY NOT TO EXCEED FIVE THOUSAND DOLLARS AND THE STATED VALUE OF THE <fo:inline font-weight="bold" font-style="italic">CLAIM</fo:inline> FOR EACH SUCH VIOLATION."
							</fo:block>	
						</xsl:if>
						<xsl:if test="response/policy_freeform_01/data/StateCode='OH'">
							<fo:block font-family="Arial" font-size="9px">
								NOTICE: "ANY PERSON WHO WITH INTENT TO DEFRAUD OR KNOWING HE IS FACILITATING A FRAUD AGAINST AN INSURER, SUBMITS AN APPLICATION OR FILES A <fo:inline font-weight="bold" font-style="italic">CLAIM</fo:inline> CONTAINING A FALSE OR DECEPTIVE STATEMENT IS GUILTY OF INSURANCE FRAUD."
							</fo:block>	
						</xsl:if>
						<xsl:if test="response/policy_freeform_01/data/StateCode='OK'">
							<fo:block font-family="Arial" font-size="9px">
								NOTICE: "WARNING: ANY PERSON WHO KNOWINGLY, AND WITH INTENT TO INJURE, DEFRAUD OR DECEIVE ANY <fo:inline font-weight="bold" font-style="italic">INSURER</fo:inline>, MAKES ANY <fo:inline font-weight="bold" font-style="italic">CLAIM</fo:inline> FOR THE PROCEEDS OF AN INSURANCE POLICY CONTAINING ANY FALSE, INCOMPLETE OR MISLEADING INFORMATION IS GUILTY OF A FELONY."
							</fo:block>	
						</xsl:if>
						<xsl:if test="response/policy_freeform_01/data/StateCode='OR'">
							<fo:block font-family="Arial" font-size="9px">
								NOTICE: "ANY PERSON WHO KNOWINGLY AND WITH INTENT TO DEFRAUD ANY INSURANCE COMPANY OR ANTOHER PERSON FILES AN APPLICATION FOR INSURANCE OR STATEMENT OF <fo:inline font-weight="bold" font-style="italic">CLAIM</fo:inline> CONTAINING ANY MATERIALLY FALSE INFORMATION, OR CONCEALS FOR THE PURPOSE OF MISLEADING INFORMATION CONCERNING ANY FACT MATERIAL THERETO, COMMITS A FRAUDULENT INSURANCE ACT, WHICH IS A CRIME AND MAY SUBJECT THE PERSON TO CRIMINAL AND CIVIL PENALTIES."
							</fo:block>	
						</xsl:if>
						<xsl:if test="response/policy_freeform_01/data/StateCode='PA'">
							<fo:block font-family="Arial" font-size="9px">
								NOTICE: "ANY PERSON WHO KNOWINGLY AND WITH INTENT TO DEFRAUD ANY INSURANCE COMPANY OR OTHER PERSON FILES AN APPLICATION FOR INSURANCE OR STATEMENT OF <fo:inline font-weight="bold" font-style="italic">CLAIM</fo:inline> CONTAINING ANY MATERIALLY FALSE INFORMATION OR CONCEALS FOR THE PURPOSE OF MISLEADING, INFORMATION CONCERNING ANY FACT MATERIAL THERETO COMMITS A FRAUDULENT INSURANCE ACT, WHICH IS A CRIME AND SUBJECTS SUCH PERSON TO CRIMINAL AND CIVIL PENALTIES."
							</fo:block>	
						</xsl:if>
						<xsl:if test="response/policy_freeform_01/data/StateCode='TN'">
							<fo:block font-family="Arial" font-size="9px">
								NOTICE: "IT IS A CRIME TO KNOWINGLY PROVIDE FALSE, INCOMPLETE OR MISLEADING INFORMATION TO AN INSURANCE COMPANY FOR THE PURPOSE OF DEFRAUDING THE COMPANY.  PENALTIES INCLUDE IMPRISONMENT, FINES AND DENIAL OF INSURANCE BENEFITS."
							</fo:block>	
						</xsl:if>
						<xsl:if test="response/policy_freeform_01/data/StateCode='VT'">
							<fo:block font-family="Arial" font-size="9px">
								NOTICE: "ANY PERSON WHO KNOWINGLY PRESENTS A FALSE STATEMENT IN AN APPLICATION FOR INSURANCE MAY BE GUILTY OF A CRIMINAL OFFENSE AND SUBJECT TO PENALTIES UNDER STATE LAW."
							</fo:block>	
						</xsl:if>
						<xsl:if test="response/policy_freeform_01/data/StateCode='VA'">
							<fo:block font-family="Arial" font-size="9px">
								NOTICE: "IT IS A CRIME TO KNOWINGLY PROVIDE FALSE, INCOMPLETE OR MISLEADING INFORMATION TO AN INSURANCE COMPANY FOR THE PURPOSE OF DEFRAUDING THE COMPANY.  PENALTIES INCLUDE IMPRISONMENT, FINES AND DENIAL OF INSURANCE BENEFITS."
							</fo:block>	
						</xsl:if>
						<xsl:if test="response/policy_freeform_01/data/StateCode='WA'">
							<fo:block font-family="Arial" font-size="9px">
								NOTICE: "IT IS A CRIME TO KNOWINGLY PROVIDE FALSE, INCOMPLETE OR MISLEADING INFORMATION TO AN INSURANCE COMPANY FOR THE PURPOSE OF DEFRAUDING THE COMPANY.  PENALTIES INCLUDE IMPRISONMENT, FINES AND DENIAL OF INSURANCE BENEFITS."
							</fo:block>	
						</xsl:if>
						<xsl:if test="response/policy_freeform_01/data/StateCode='WV'">
							<fo:block font-family="Arial" font-size="9px">
								NOTICE: "ANY PERSON WHO KNOWINGLY PRESENTS A FALSE OR FRAUDULENT <fo:inline font-weight="bold" font-style="italic">CLAIM</fo:inline> FOR PAYMENT OF A <fo:inline font-weight="bold" font-style="italic">LOSS</fo:inline> OR BENEFIT OR KNOWINGLY PRESENTS FALSE INFORMATION IN AN APPLICATION FOR INSURANCE IS GUILTY OF A CRIME AND MAY BE SUBJECT TO FINES AND CONFINEMENT TO PRISON."
							</fo:block>	
						</xsl:if>
					</fo:block>
					
					<fo:block margin-top="3mm" font-family="Arial" font-size="8px">
						Completion and/or signing of this application does not bind the Applicant to purchase, nor the Insurer to provide, any insurance policy; however, no policy can be issued unless the application is properly completed, signed and dated.
					</fo:block>
					
					<fo:block margin-top="3mm" font-family="Arial" font-size="8px">
						The signatory declares that (s)he is authorized by the Applicant to sign this application on behalf of all prospective Insureds and that to the best of his/her knowledge the statements herein are true.  The signatory agrees that if the information supplied in this application and the materials submitted therewith should change between the date this application is signed and the effective date of the proposed insurance, the signatory shall immediately notify the Insurer of such and shall provide the Insurer with information that would complete, update or correct the application or materials submitted therewith. <!--  The Insurer may withdraw or modify any of the terms or conditions of coverage accordingly -->
					</fo:block>
					
					<fo:block margin-top="3mm" font-family="Arial" font-size="8px" font-weight="bold">
						ALL WRITTEN STATEMENTS, SUPPLEMENTAL APPLICATION AND MATERIALS FURNISHED TO THE INSURER IN CONJUNCTION WITH THIS APPLICATION ARE INCORPORATED BY REFERENCE INTO THIS APPLICATION AND MADE A PART THEREOF, AND DEEMED ATTACHED HERETO.
					</fo:block>
					
					<fo:block margin-top="3mm" font-family="Arial" font-size="8px" font-weight="bold">
						ANY PERSON WHO, WITH INTENT TO DEFRAUD OR KNOWING THAT (S)HE IS FACILITATING A FRAUD AGAINST AN INSURER, SUBMITS AN APPLICATION OR FILES A CLAIM CONTAINING A FALSE OR DECEPTIVE STATEMENT IS GUILTY OF INSURANCE FRAUD.
					</fo:block>
					
					<fo:block margin-top="3mm" font-family="Arial" font-size="9px">
						<fo:table>
							<fo:table-column></fo:table-column>
							<fo:table-column></fo:table-column>
							<fo:table-body>
								<fo:table-row>
									<fo:table-cell><fo:block>SIGNATURE*___________________________________</fo:block></fo:table-cell>
									<fo:table-cell><fo:block>PRINTED NAME*___________________________________</fo:block></fo:table-cell>
								</fo:table-row>
							</fo:table-body>
						</fo:table>
						*MUST BE SIGNED BY A DULY AUTHORIZED OFFICER OF THE APPLICANT ON BEHALF OF ALL INSUREDS.
					</fo:block>
					
					<fo:block margin-top="3mm" font-family="Arial" font-size="9px">
						<fo:table>
							<fo:table-column></fo:table-column>
							<fo:table-column></fo:table-column>
							<fo:table-body>
								<fo:table-row>
									<fo:table-cell><fo:block>TITLE OF SIGNATORY: ___________________________________</fo:block></fo:table-cell>
									<fo:table-cell><fo:block>DATE SIGNED:</fo:block></fo:table-cell>
								</fo:table-row>
							</fo:table-body>
						</fo:table>
						<fo:table>
							<fo:table-column column-width="115mm"></fo:table-column>
							<fo:table-column></fo:table-column>
							<fo:table-body>
								<fo:table-row>
									<fo:table-cell><fo:block></fo:block></fo:table-cell>
									<fo:table-cell><fo:block text-align="left"><fo:inline font-style="italic">MM / DD / YYYY</fo:inline></fo:block></fo:table-cell>
								</fo:table-row>
							</fo:table-body>
						</fo:table>		
					</fo:block>
					
					<!--  
					<xsl:if test="response/AverageRevFromAjax > 500000">
							<fo:block break-after="page"></fo:block>
								<fo:block>
									<fo:table margin-top="3mm" border="0.5pt solid grey" background-color="grey">
									<fo:table-column></fo:table-column>
									<fo:table-body>
										<fo:table-row>
											<fo:table-cell>
												<fo:block margin-top="1mm" font-family="Arial" font-weight="bold" font-size="9px">&#160;&#160;PRACTICE MANAGEMENT SUPPLEMENT <fo:inline font-style="italic"> (As required in question 2)</fo:inline></fo:block>
											</fo:table-cell>
										</fo:table-row>
										<fo:table-row>
											<fo:table-cell>
												<fo:block margin-top="1mm"></fo:block>
											</fo:table-cell>
										</fo:table-row>
									</fo:table-body>
								</fo:table>
							</fo:block>
							
							<fo:block>
								<xsl:choose>
								<xsl:when test="response/firm_freeform_01/data/IsClientTerminated = 'Y'">
									<fo:table margin-top="3mm" border="0.0pt solid black">  
										<fo:table-column column-width="5mm"/>
										<fo:table-column column-width="154mm"/>
										<fo:table-column/>
										<fo:table-body>
										    <fo:table-row>
										    	<fo:table-cell><fo:block font-size="9px" font-family="Arial">1.	</fo:block></fo:table-cell>
									        	<fo:table-cell><fo:block font-size="9px" font-family="Arial">Does your Firm have dis-engagement procedures for terminating client relationships?</fo:block></fo:table-cell>
									        	<fo:table-cell><fo:block font-size="9px" font-family="Arial" text-align="right">Yes <fo:external-graphic src="../AccountantIns/image/check-btn6.png"/>&#160;&#160;&#160;No <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/></fo:block></fo:table-cell>
									        </fo:table-row>
									    </fo:table-body>
									</fo:table>
								</xsl:when>
								<xsl:when test="response/firm_freeform_01/data/IsClientTerminated = 'N'">
									<fo:table margin-top="3mm" border="0.0pt solid black">  
										<fo:table-column column-width="5mm"/>
										<fo:table-column column-width="154mm"/>
										<fo:table-column/>
										<fo:table-body>
										    <fo:table-row>
										    	<fo:table-cell><fo:block font-size="9px" font-family="Arial">1.	</fo:block></fo:table-cell>
									        	<fo:table-cell><fo:block font-size="9px" font-family="Arial">Does your Firm have dis-engagement procedures for terminating client relationships?</fo:block></fo:table-cell>
									        	<fo:table-cell><fo:block font-size="9px" font-family="Arial" text-align="right">Yes <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/>&#160;&#160;&#160;No <fo:external-graphic src="../AccountantIns/image/check-btn6.png"/></fo:block></fo:table-cell>
									        </fo:table-row>
									    </fo:table-body>
									</fo:table>
								</xsl:when>
								<xsl:otherwise>
									<fo:table margin-top="3mm" border="0.0pt solid black">  
										<fo:table-column column-width="5mm"/>
										<fo:table-column column-width="154mm"/>
										<fo:table-column/>
										<fo:table-body>
										    <fo:table-row>
										    	<fo:table-cell><fo:block font-size="9px" font-family="Arial">1.	</fo:block></fo:table-cell>
									        	<fo:table-cell><fo:block font-size="9px" font-family="Arial">Does your Firm have dis-engagement procedures for terminating client relationships?</fo:block></fo:table-cell>
									        	<fo:table-cell><fo:block font-size="9px" font-family="Arial" text-align="right">Yes <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/>&#160;&#160;&#160;No <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/></fo:block></fo:table-cell>
									        </fo:table-row>
									    </fo:table-body>
									</fo:table>
								</xsl:otherwise>
							</xsl:choose>
						</fo:block>
						
						
						
						<fo:block>
								<xsl:choose>
								<xsl:when test="response/firm_freeform_01/data/IsAnyDeclination = 'Y'">
									<fo:table margin-top="3mm" border="0.0pt solid black">  
										<fo:table-column column-width="5mm"/>
										<fo:table-column column-width="154mm"/>
										<fo:table-column/>
										<fo:table-body>
										    <fo:table-row>
										    	<fo:table-cell><fo:block font-size="9px" font-family="Arial">2.	</fo:block></fo:table-cell>
									        	<fo:table-cell><fo:block font-size="9px" font-family="Arial">Are declination / non-engagement letters used on all matters declined by the Firm?</fo:block></fo:table-cell>
									        	<fo:table-cell><fo:block font-size="9px" font-family="Arial" text-align="right">Yes <fo:external-graphic src="../AccountantIns/image/check-btn6.png"/>&#160;&#160;&#160;No <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/></fo:block></fo:table-cell>
									        </fo:table-row>
									    </fo:table-body>
									</fo:table>
								</xsl:when>
								<xsl:when test="response/firm_freeform_01/data/IsAnyDeclination = 'N'">
									<fo:table margin-top="3mm" border="0.0pt solid black">  
										<fo:table-column column-width="5mm"/>
										<fo:table-column column-width="154mm"/>
										<fo:table-column/>
										<fo:table-body>
										    <fo:table-row>
										    	<fo:table-cell><fo:block font-size="9px" font-family="Arial">2.	</fo:block></fo:table-cell>
									        	<fo:table-cell><fo:block font-size="9px" font-family="Arial">Are declination / non-engagement letters used on all matters declined by the Firm?</fo:block></fo:table-cell>
									        	<fo:table-cell><fo:block font-size="9px" font-family="Arial" text-align="right">Yes <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/>&#160;&#160;&#160;No <fo:external-graphic src="../AccountantIns/image/check-btn6.png"/></fo:block></fo:table-cell>
									        </fo:table-row>
									    </fo:table-body>
									</fo:table>
								</xsl:when>
								<xsl:otherwise>
									<fo:table margin-top="3mm" border="0.0pt solid black">  
										<fo:table-column column-width="5mm"/>
										<fo:table-column column-width="154mm"/>
										<fo:table-column/>
										<fo:table-body>
										    <fo:table-row>
										    	<fo:table-cell><fo:block font-size="9px" font-family="Arial">2.	</fo:block></fo:table-cell>
									        	<fo:table-cell><fo:block font-size="9px" font-family="Arial">Are declination / non-engagement letters used on all matters declined by the Firm?</fo:block></fo:table-cell>
									        	<fo:table-cell><fo:block font-size="9px" font-family="Arial" text-align="right">Yes <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/>&#160;&#160;&#160;No <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/></fo:block></fo:table-cell>
									        </fo:table-row>
									    </fo:table-body>
									</fo:table>
								</xsl:otherwise>
							</xsl:choose>
						</fo:block>
						
						
						<fo:block>
								<xsl:choose>
								<xsl:when test="response/firm_freeform_01/data/IsPolicyProhibiting = 'Y'">
									<fo:table margin-top="3mm" border="0.0pt solid black">  
										<fo:table-column column-width="5mm"/>
										<fo:table-column column-width="154mm"/>
										<fo:table-column/>
										<fo:table-body>
										    <fo:table-row>
										    	<fo:table-cell><fo:block font-size="9px" font-family="Arial">3.	</fo:block></fo:table-cell>
									        	<fo:table-cell><fo:block font-size="9px" font-family="Arial">Does your Firm have a written policy prohibiting business ventures with clients of the Firm?</fo:block></fo:table-cell>
									        	<fo:table-cell><fo:block font-size="9px" font-family="Arial" text-align="right">Yes <fo:external-graphic src="../AccountantIns/image/check-btn6.png"/>&#160;&#160;&#160;No <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/></fo:block></fo:table-cell>
									        </fo:table-row>
									    </fo:table-body>
									</fo:table>
								</xsl:when>
								<xsl:when test="response/firm_freeform_01/data/IsPolicyProhibiting = 'N'">
									<fo:table margin-top="3mm" border="0.0pt solid black">  
										<fo:table-column column-width="5mm"/>
										<fo:table-column column-width="154mm"/>
										<fo:table-column/>
										<fo:table-body>
										    <fo:table-row>
										    	<fo:table-cell><fo:block font-size="9px" font-family="Arial">3.	</fo:block></fo:table-cell>
									        	<fo:table-cell><fo:block font-size="9px" font-family="Arial">Does your Firm have a written policy prohibiting business ventures with clients of the Firm?</fo:block></fo:table-cell>
									        	<fo:table-cell><fo:block font-size="9px" font-family="Arial" text-align="right">Yes <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/>&#160;&#160;&#160;No <fo:external-graphic src="../AccountantIns/image/check-btn6.png"/></fo:block></fo:table-cell>
									        </fo:table-row>
									    </fo:table-body>
									</fo:table>
								</xsl:when>
								<xsl:otherwise>
									<fo:table margin-top="3mm" border="0.0pt solid black">  
										<fo:table-column column-width="5mm"/>
										<fo:table-column column-width="154mm"/>
										<fo:table-column/>
										<fo:table-body>
										    <fo:table-row>
										    	<fo:table-cell><fo:block font-size="9px" font-family="Arial">3.	</fo:block></fo:table-cell>
									        	<fo:table-cell><fo:block font-size="9px" font-family="Arial">Does your Firm have a written policy prohibiting business ventures with clients of the Firm?</fo:block></fo:table-cell>
									        	<fo:table-cell><fo:block font-size="9px" font-family="Arial" text-align="right">Yes <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/>&#160;&#160;&#160;No <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/></fo:block></fo:table-cell>
									        </fo:table-row>
									    </fo:table-body>
									</fo:table>
								</xsl:otherwise>
							</xsl:choose>
						</fo:block>
						
						
						
						<fo:block>
								<xsl:choose>
								<xsl:when test="response/firm_freeform_01/data/IsWrittenAnyQualityControlDocument = 'Y'">
									<fo:table margin-top="3mm" border="0.0pt solid black">  
										<fo:table-column column-width="5mm"/>
										<fo:table-column column-width="154mm"/>
										<fo:table-column/>
										<fo:table-body>
										    <fo:table-row>
										    	<fo:table-cell><fo:block font-size="9px" font-family="Arial">4.	</fo:block></fo:table-cell>
									        	<fo:table-cell><fo:block font-size="9px" font-family="Arial">Does your Firm have a written internal quality control document?</fo:block></fo:table-cell>
									        	<fo:table-cell><fo:block font-size="9px" font-family="Arial" text-align="right">Yes <fo:external-graphic src="../AccountantIns/image/check-btn6.png"/>&#160;&#160;&#160;No <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/></fo:block></fo:table-cell>
									        </fo:table-row>
									    </fo:table-body>
									</fo:table>
								</xsl:when>
								<xsl:when test="response/firm_freeform_01/data/IsWrittenAnyQualityControlDocument = 'N'">
									<fo:table margin-top="3mm" border="0.0pt solid black">  
										<fo:table-column column-width="5mm"/>
										<fo:table-column column-width="154mm"/>
										<fo:table-column/>
										<fo:table-body>
										    <fo:table-row>
										    	<fo:table-cell><fo:block font-size="9px" font-family="Arial">4.	</fo:block></fo:table-cell>
									        	<fo:table-cell><fo:block font-size="9px" font-family="Arial">Does your Firm have a written internal quality control document?</fo:block></fo:table-cell>
									        	<fo:table-cell><fo:block font-size="9px" font-family="Arial" text-align="right">Yes <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/>&#160;&#160;&#160;No <fo:external-graphic src="../AccountantIns/image/check-btn6.png"/></fo:block></fo:table-cell>
									        </fo:table-row>
									    </fo:table-body>
									</fo:table>
								</xsl:when>
								<xsl:otherwise>
									<fo:table margin-top="3mm" border="0.0pt solid black">  
										<fo:table-column column-width="5mm"/>
										<fo:table-column column-width="154mm"/>
										<fo:table-column/>
										<fo:table-body>
										    <fo:table-row>
										    	<fo:table-cell><fo:block font-size="9px" font-family="Arial">4.	</fo:block></fo:table-cell>
									        	<fo:table-cell><fo:block font-size="9px" font-family="Arial">Does your Firm have a written internal quality control document?</fo:block></fo:table-cell>
									        	<fo:table-cell><fo:block font-size="9px" font-family="Arial" text-align="right">Yes <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/>&#160;&#160;&#160;No <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/></fo:block></fo:table-cell>
									        </fo:table-row>
									    </fo:table-body>
									</fo:table>
								</xsl:otherwise>
							</xsl:choose>
						</fo:block>
						
						
						
						<fo:block>
								<xsl:choose>
								<xsl:when test="response/firm_freeform_01/data/IsNonMonetaryCompensationRecieved = 'Y'">
									<fo:table margin-top="3mm" border="0.0pt solid black">  
										<fo:table-column column-width="5mm"/>
										<fo:table-column column-width="154mm"/>
										<fo:table-column/>
										<fo:table-body>
										    <fo:table-row>
										    	<fo:table-cell><fo:block font-size="9px" font-family="Arial">5.	</fo:block></fo:table-cell>
									        	<fo:table-cell><fo:block font-size="9px" font-family="Arial">Within the past 5 years has your Firm or its personnel received non-monetary compensation for professional services performed for a client or its related entities?</fo:block></fo:table-cell>
									        	<fo:table-cell><fo:block font-size="9px" font-family="Arial" text-align="right">Yes <fo:external-graphic src="../AccountantIns/image/check-btn6.png"/>&#160;&#160;&#160;No <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/></fo:block></fo:table-cell>
									        </fo:table-row>
									    </fo:table-body>
									</fo:table>
								</xsl:when>
								<xsl:when test="response/firm_freeform_01/data/IsNonMonetaryCompensationRecieved = 'N'">
									<fo:table margin-top="3mm" border="0.0pt solid black">  
										<fo:table-column column-width="5mm"/>
										<fo:table-column column-width="154mm"/>
										<fo:table-column/>
										<fo:table-body>
										    <fo:table-row>
										    	<fo:table-cell><fo:block font-size="9px" font-family="Arial">5.	</fo:block></fo:table-cell>
									        	<fo:table-cell><fo:block font-size="9px" font-family="Arial">Within the past 5 years has your Firm or its personnel received non-monetary compensation for professional services performed for a client or its related entities?</fo:block></fo:table-cell>
									        	<fo:table-cell><fo:block font-size="9px" font-family="Arial" text-align="right">Yes <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/>&#160;&#160;&#160;No <fo:external-graphic src="../AccountantIns/image/check-btn6.png"/></fo:block></fo:table-cell>
									        </fo:table-row>
									    </fo:table-body>
									</fo:table>
								</xsl:when>
								<xsl:otherwise>
									<fo:table margin-top="3mm" border="0.0pt solid black">  
										<fo:table-column column-width="5mm"/>
										<fo:table-column column-width="154mm"/>
										<fo:table-column/>
										<fo:table-body>
										    <fo:table-row>
										    	<fo:table-cell><fo:block font-size="9px" font-family="Arial">5.	</fo:block></fo:table-cell>
									        	<fo:table-cell><fo:block font-size="9px" font-family="Arial">Within the past 5 years has your Firm or its personnel received non-monetary compensation for professional services performed for a client or its related entities?</fo:block></fo:table-cell>
									        	<fo:table-cell><fo:block font-size="9px" font-family="Arial" text-align="right">Yes <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/>&#160;&#160;&#160;No <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/></fo:block></fo:table-cell>
									        </fo:table-row>
									    </fo:table-body>
									</fo:table>
								</xsl:otherwise>
							</xsl:choose>
							<fo:table margin-top="3mm" border="0.0pt solid black">  
										<fo:table-column column-width="5mm"/>
										<fo:table-column column-width="154mm"/>
										<fo:table-column/>
										<fo:table-body>
										    <fo:table-row>
										    	<fo:table-cell><fo:block font-size="9px" font-family="Arial">	</fo:block></fo:table-cell>
									        	<fo:table-cell><fo:block font-size="9px" font-family="Arial">If yes, current value of such non-monetary compensation from client (include related entities):</fo:block></fo:table-cell>
									        	<fo:table-cell><fo:block font-size="9px" font-family="Arial" text-align="right"><xsl:value-of select="response/firm_freeform_01/data/CurrentValueNmc"></xsl:value-of> </fo:block></fo:table-cell>
									        </fo:table-row>
									    </fo:table-body>
									</fo:table>
									
							<fo:table margin-top="3mm" border="0.0pt solid black">  
										<fo:table-column column-width="100mm"/>
										<fo:table-column column-width="100mm"/>
										<fo:table-column/>
										<fo:table-body>
										    <fo:table-row>
										    	<fo:table-cell><fo:block font-size="9px" font-family="Arial">Client Industry:	</fo:block></fo:table-cell>
									        	<fo:table-cell><fo:block font-size="9px" font-family="Arial"><xsl:value-of select="response/ClientServiceDesc_Firm_freeform_01/data/ClientIndustryDesc"></xsl:value-of> </fo:block></fo:table-cell>									        	
									        </fo:table-row>
									        <fo:table-row>
										    	<fo:table-cell><fo:block font-size="9px" font-family="Arial">Client Name:  </fo:block></fo:table-cell>
									        	<fo:table-cell><fo:block font-size="9px" font-family="Arial"><xsl:value-of select="response/firm_freeform_01/data/ClientNameNmc"></xsl:value-of> </fo:block></fo:table-cell>									        	
									        </fo:table-row>
									        <fo:table-row>
										    	<fo:table-cell><fo:block font-size="9px" font-family="Arial">Services rendered: </fo:block></fo:table-cell>
									        	<fo:table-cell><fo:block font-size="9px" font-family="Arial"><xsl:value-of select="response/ClientServiceDesc_Firm_freeform_01/data/ServiceIndustryDesc"></xsl:value-of> </fo:block></fo:table-cell>									        	
									        </fo:table-row>
									    </fo:table-body>
									</fo:table>
						</fo:block>
						
						
						<fo:block>
								<xsl:choose>
								<xsl:when test="response/firm_freeform_01/data/IsReducedNumberOfOwners = 'Y'">
									<fo:table margin-top="3mm" border="0.0pt solid black">  
										<fo:table-column column-width="5mm"/>
										<fo:table-column column-width="154mm"/>
										<fo:table-column/>
										<fo:table-body>
										    <fo:table-row>
										    	<fo:table-cell><fo:block font-size="9px" font-family="Arial">6.	</fo:block></fo:table-cell>
									        	<fo:table-cell><fo:block font-size="9px" font-family="Arial">Within the past 5 years, has your Firm reduced the number of its owners, partners, or officers by 50% or more?</fo:block></fo:table-cell>
									        	<fo:table-cell><fo:block font-size="9px" font-family="Arial" text-align="right">Yes <fo:external-graphic src="../AccountantIns/image/check-btn6.png"/>&#160;&#160;&#160;No <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/></fo:block></fo:table-cell>
									        </fo:table-row>
									    </fo:table-body>
									</fo:table>
								</xsl:when>
								<xsl:when test="response/firm_freeform_01/data/IsReducedNumberOfOwners = 'N'">
									<fo:table margin-top="3mm" border="0.0pt solid black">  
										<fo:table-column column-width="5mm"/>
										<fo:table-column column-width="154mm"/>
										<fo:table-column/>
										<fo:table-body>
										    <fo:table-row>
										    	<fo:table-cell><fo:block font-size="9px" font-family="Arial">6.	</fo:block></fo:table-cell>
									        	<fo:table-cell><fo:block font-size="9px" font-family="Arial">Within the past 5 years, has your Firm reduced the number of its owners, partners, or officers by 50% or more?</fo:block></fo:table-cell>
									        	<fo:table-cell><fo:block font-size="9px" font-family="Arial" text-align="right">Yes <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/>&#160;&#160;&#160;No <fo:external-graphic src="../AccountantIns/image/check-btn6.png"/></fo:block></fo:table-cell>
									        </fo:table-row>
									    </fo:table-body>
									</fo:table>
								</xsl:when>
								<xsl:otherwise>
									<fo:table margin-top="3mm" border="0.0pt solid black">  
										<fo:table-column column-width="5mm"/>
										<fo:table-column column-width="154mm"/>
										<fo:table-column/>
										<fo:table-body>
										    <fo:table-row>
										    	<fo:table-cell><fo:block font-size="9px" font-family="Arial">6.	</fo:block></fo:table-cell>
									        	<fo:table-cell><fo:block font-size="9px" font-family="Arial">Within the past 5 years, has your Firm reduced the number of its owners, partners, or officers by 50% or more?</fo:block></fo:table-cell>
									        	<fo:table-cell><fo:block font-size="9px" font-family="Arial" text-align="right">Yes <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/>&#160;&#160;&#160;No <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/></fo:block></fo:table-cell>
									        </fo:table-row>
									    </fo:table-body>
									</fo:table>
								</xsl:otherwise>
							</xsl:choose>
							<fo:table margin-top="3mm" border="0.0pt solid black">  
										<fo:table-column/>
										<fo:table-column />
										
										<fo:table-body>
										    <fo:table-row>
										    	<fo:table-cell><fo:block font-size="9px" font-family="Arial">If yes, provide explanation:</fo:block></fo:table-cell>
									        	<fo:table-cell><fo:block font-size="9px" font-family="Arial"><xsl:value-of select="response/firm_freeform_01/data/MergedOrNumberComment"></xsl:value-of> </fo:block></fo:table-cell>
									        	
									        </fo:table-row>
									    </fo:table-body>
									</fo:table>
						</fo:block>
						
					</xsl:if>
					-->
					
					
					<xsl:if test="response/showSupplementSuppPDF = 'Y'">
						<fo:block break-after="page"></fo:block>
						
						<fo:block>
							<fo:table margin-top="3mm" border="0.5pt solid grey" background-color="grey">
								<fo:table-column></fo:table-column>
								<fo:table-body>
									<fo:table-row>
										<fo:table-cell>
											<fo:block margin-top="1mm" font-family="Arial" font-weight="bold" font-size="9px">&#160;&#160;INVESTMENT/FINANCIAL PLANNING SUPPLEMENT <fo:inline font-style="italic">(As required in question 3. U )</fo:inline></fo:block>
										</fo:table-cell>
									</fo:table-row>
									<fo:table-row>
										<fo:table-cell>
											<fo:block margin-top="1mm"></fo:block>
										</fo:table-cell>
									</fo:table-row>
								</fo:table-body>
							</fo:table>
							<fo:table margin-top="3mm">
								<fo:table-column column-width="5mm"></fo:table-column>
								<fo:table-column></fo:table-column>
								<fo:table-body>
									<fo:table-row>
										<fo:table-cell><fo:block font-family="Arial" font-size="9px">1.</fo:block></fo:table-cell>
										<fo:table-cell>
											<fo:block font-family="Arial" font-size="9px">Do the Investment and Financial Planning Services provided by your Firm include:</fo:block>
										</fo:table-cell>
									</fo:table-row>
								</fo:table-body>
							</fo:table>
							<xsl:choose>
							<xsl:when test="response/invest_finan_planing_freeform_01/data/IsFinancialPlansPreparing = 'Y'">
							<fo:table margin-top="3mm" border="0.0pt solid black">  
								<fo:table-column column-width="5mm"/>
								<fo:table-column column-width="154mm"/>
								<fo:table-column/>
								<fo:table-body>
								    <fo:table-row>
								    	<fo:table-cell><fo:block font-size="9px" font-family="Arial" start-indent="1cm">a.</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial" start-indent="1cm">Preparation of Financial Plans</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial" text-align="right">Yes <fo:external-graphic src="../AccountantIns/image/check-btn6.png"/>&#160;&#160;&#160;No <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/></fo:block></fo:table-cell>
							        </fo:table-row>
							    </fo:table-body>
							</fo:table>
							</xsl:when>
							<xsl:when test="response/invest_finan_planing_freeform_01/data/IsFinancialPlansPreparing = 'N'">
							<fo:table margin-top="3mm" border="0.0pt solid black">  
								<fo:table-column column-width="5mm"/>
								<fo:table-column column-width="154mm"/>
								<fo:table-column/>
								<fo:table-body>
								    <fo:table-row>
								    	<fo:table-cell><fo:block font-size="9px" font-family="Arial" start-indent="1cm">a.</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial" start-indent="1cm">Preparation of Financial Plans</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial" text-align="right">Yes <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/>&#160;&#160;&#160;No <fo:external-graphic src="../AccountantIns/image/check-btn6.png"/></fo:block></fo:table-cell>
							        </fo:table-row>
							    </fo:table-body>
							</fo:table>
							</xsl:when>
							<xsl:otherwise>
							<fo:table margin-top="3mm" border="0.0pt solid black">  
								<fo:table-column column-width="5mm"/>
								<fo:table-column column-width="154mm"/>
								<fo:table-column/>
								<fo:table-body>
								    <fo:table-row>
								    	<fo:table-cell><fo:block font-size="9px" font-family="Arial" start-indent="1cm">a.</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial" start-indent="1cm">Preparation of Financial Plans</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial" text-align="right">Yes <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/>&#160;&#160;&#160;No <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/></fo:block></fo:table-cell>
							        </fo:table-row>
							    </fo:table-body>
							</fo:table>
							</xsl:otherwise>
							</xsl:choose>
							<xsl:choose>
							<xsl:when test="response/invest_finan_planing_freeform_01/data/IsDiscretionaryAssetMangt = 'Y'">
								<fo:table margin-top="3mm" border="0.0pt solid black">  
								<fo:table-column column-width="5mm"/>
								<fo:table-column column-width="154mm"/>
								<fo:table-column/>
								<fo:table-body>
								    <fo:table-row>
								    	<fo:table-cell><fo:block font-size="9px" font-family="Arial" start-indent="1cm">b.</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial" start-indent="1cm">Discretionary Asset Management Services</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial" text-align="right">Yes <fo:external-graphic src="../AccountantIns/image/check-btn6.png"/>&#160;&#160;&#160;No <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/></fo:block></fo:table-cell>
							        </fo:table-row>
							    </fo:table-body>
							</fo:table>
							</xsl:when>
							<xsl:when test="response/invest_finan_planing_freeform_01/data/IsDiscretionaryAssetMangt = 'N'">
								<fo:table margin-top="3mm" border="0.0pt solid black">  
								<fo:table-column column-width="5mm"/>
								<fo:table-column column-width="154mm"/>
								<fo:table-column/>
								<fo:table-body>
								    <fo:table-row>
								    	<fo:table-cell><fo:block font-size="9px" font-family="Arial" start-indent="1cm">b.</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial" start-indent="1cm">Discretionary Asset Management Services</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial" text-align="right">Yes <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/>&#160;&#160;&#160;No <fo:external-graphic src="../AccountantIns/image/check-btn6.png"/></fo:block></fo:table-cell>
							        </fo:table-row>
							    </fo:table-body>
							</fo:table>
							</xsl:when>
							<xsl:otherwise>
								<fo:table margin-top="3mm" border="0.0pt solid black">  
								<fo:table-column column-width="5mm"/>
								<fo:table-column column-width="154mm"/>
								<fo:table-column/>
								<fo:table-body>
								    <fo:table-row>
								    	<fo:table-cell><fo:block font-size="9px" font-family="Arial" start-indent="1cm">b.</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial" start-indent="1cm">Discretionary Asset Management Services</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial" text-align="right">Yes <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/>&#160;&#160;&#160;No <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/></fo:block></fo:table-cell>
							        </fo:table-row>
							    </fo:table-body>
							</fo:table>
							</xsl:otherwise>
							</xsl:choose>
							
							<xsl:choose>
								<xsl:when test="response/invest_finan_planing_freeform_01/data/IsNonDiscretionaryAssetMangt = 'Y'">
									<fo:table margin-top="3mm" border="0.0pt solid black">  
										<fo:table-column column-width="5mm"/>
										<fo:table-column column-width="154mm"/>
										<fo:table-column/>
										<fo:table-body>
								    	<fo:table-row>
								    			<fo:table-cell><fo:block font-size="9px" font-family="Arial" start-indent="1cm">c.	</fo:block></fo:table-cell>
							        			<fo:table-cell><fo:block font-size="9px" font-family="Arial" start-indent="1cm">Non-Discretionary Asset Management Services</fo:block></fo:table-cell>
							        			<fo:table-cell><fo:block font-size="9px" font-family="Arial" text-align="right">Yes <fo:external-graphic src="../AccountantIns/image/check-btn6.png"/>&#160;&#160;&#160;No <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/></fo:block></fo:table-cell>
							        	</fo:table-row>
							    	</fo:table-body>
									</fo:table>
								</xsl:when>
								<xsl:when test="response/invest_finan_planing_freeform_01/data/IsNonDiscretionaryAssetMangt = 'N'">
									<fo:table margin-top="3mm" border="0.0pt solid black">  
										<fo:table-column column-width="5mm"/>
										<fo:table-column column-width="154mm"/>
										<fo:table-column/>
										<fo:table-body>
								    	<fo:table-row>
								    			<fo:table-cell><fo:block font-size="9px" font-family="Arial" start-indent="1cm">c.	</fo:block></fo:table-cell>
							        			<fo:table-cell><fo:block font-size="9px" font-family="Arial" start-indent="1cm">Non-Discretionary Asset Management Services</fo:block></fo:table-cell>
							        			<fo:table-cell><fo:block font-size="9px" font-family="Arial" text-align="right">Yes <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/>&#160;&#160;&#160;No <fo:external-graphic src="../AccountantIns/image/check-btn6.png"/></fo:block></fo:table-cell>
							        	</fo:table-row>
							    	</fo:table-body>
									</fo:table>
								</xsl:when>
								<xsl:otherwise>
									<fo:table margin-top="3mm" border="0.0pt solid black">  
										<fo:table-column column-width="5mm"/>
										<fo:table-column column-width="154mm"/>
										<fo:table-column/>
										<fo:table-body>
								    	<fo:table-row>
								    			<fo:table-cell><fo:block font-size="9px" font-family="Arial" start-indent="1cm">c.	</fo:block></fo:table-cell>
							        			<fo:table-cell><fo:block font-size="9px" font-family="Arial" start-indent="1cm">Non-Discretionary Asset Management Services</fo:block></fo:table-cell>
							        			<fo:table-cell><fo:block font-size="9px" font-family="Arial" text-align="right">Yes <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/>&#160;&#160;&#160;No <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/></fo:block></fo:table-cell>
							        	</fo:table-row>
							    	</fo:table-body>
									</fo:table>
								</xsl:otherwise>
							</xsl:choose>
							
							<xsl:choose>
								<xsl:when test="response/invest_finan_planing_freeform_01/data/IsSecuritiesSales = 'Y'">
									<fo:table margin-top="3mm" border="0.0pt solid black">  
										<fo:table-column column-width="5mm"/>
										<fo:table-column column-width="154mm"/>
										<fo:table-column/>
										<fo:table-body>
										    <fo:table-row>
										    	<fo:table-cell><fo:block font-size="9px" font-family="Arial" start-indent="1cm">d.	</fo:block></fo:table-cell>
									        	<fo:table-cell><fo:block font-size="9px" font-family="Arial" start-indent="1cm">Securities Sales</fo:block></fo:table-cell>
									        	<fo:table-cell><fo:block font-size="9px" font-family="Arial" text-align="right">Yes <fo:external-graphic src="../AccountantIns/image/check-btn6.png"/>&#160;&#160;&#160;No <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/></fo:block></fo:table-cell>
									        </fo:table-row>
									    </fo:table-body>
									</fo:table>
								</xsl:when>
								<xsl:when test="response/invest_finan_planing_freeform_01/data/IsSecuritiesSales = 'N'">
									<fo:table margin-top="3mm" border="0.0pt solid black">  
										<fo:table-column column-width="5mm"/>
										<fo:table-column column-width="154mm"/>
										<fo:table-column/>
										<fo:table-body>
										    <fo:table-row>
										    	<fo:table-cell><fo:block font-size="9px" font-family="Arial" start-indent="1cm">d.	</fo:block></fo:table-cell>
									        	<fo:table-cell><fo:block font-size="9px" font-family="Arial" start-indent="1cm">Securities Sales</fo:block></fo:table-cell>
									        	<fo:table-cell><fo:block font-size="9px" font-family="Arial" text-align="right">Yes <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/>&#160;&#160;&#160;No <fo:external-graphic src="../AccountantIns/image/check-btn6.png"/></fo:block></fo:table-cell>
									        </fo:table-row>
									    </fo:table-body>
									</fo:table>
								</xsl:when>
								<xsl:otherwise>
									<fo:table margin-top="3mm" border="0.0pt solid black">  
										<fo:table-column column-width="5mm"/>
										<fo:table-column column-width="154mm"/>
										<fo:table-column/>
										<fo:table-body>
										    <fo:table-row>
										    	<fo:table-cell><fo:block font-size="9px" font-family="Arial" start-indent="1cm">d.	</fo:block></fo:table-cell>
									        	<fo:table-cell><fo:block font-size="9px" font-family="Arial" start-indent="1cm">Securities Sales</fo:block></fo:table-cell>
									        	<fo:table-cell><fo:block font-size="9px" font-family="Arial" text-align="right">Yes <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/>&#160;&#160;&#160;No <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/></fo:block></fo:table-cell>
									        </fo:table-row>
									    </fo:table-body>
									</fo:table>
								</xsl:otherwise>
							</xsl:choose>
							
							
						</fo:block>	
												
						<fo:block>
								<xsl:choose>
								<xsl:when test="response/invest_finan_planing_freeform_01/data/IsContractualRelationShip = 'Y'">
									<fo:table margin-top="3mm" border="0.0pt solid black">  
										<fo:table-column column-width="5mm"/>
										<fo:table-column column-width="154mm"/>
										<fo:table-column/>
										<fo:table-body>
										    <fo:table-row>
										    	<fo:table-cell><fo:block font-size="9px" font-family="Arial">2.	</fo:block></fo:table-cell>
									        	<fo:table-cell><fo:block font-size="9px" font-family="Arial">Does your Firm have a contractual relationship with a securities broker or dealer?</fo:block></fo:table-cell>
									        	<fo:table-cell><fo:block font-size="9px" font-family="Arial" text-align="right">Yes <fo:external-graphic src="../AccountantIns/image/check-btn6.png"/>&#160;&#160;&#160;No <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/></fo:block></fo:table-cell>
									        </fo:table-row>
									    </fo:table-body>
									</fo:table>
								</xsl:when>
								<xsl:when test="response/invest_finan_planing_freeform_01/data/IsContractualRelationShip = 'N'">
									<fo:table margin-top="3mm" border="0.0pt solid black">  
										<fo:table-column column-width="5mm"/>
										<fo:table-column column-width="154mm"/>
										<fo:table-column/>
										<fo:table-body>
										    <fo:table-row>
										    	<fo:table-cell><fo:block font-size="9px" font-family="Arial">2.	</fo:block></fo:table-cell>
									        	<fo:table-cell><fo:block font-size="9px" font-family="Arial">Does your Firm have a contractual relationship with a securities broker or dealer?</fo:block></fo:table-cell>
									        	<fo:table-cell><fo:block font-size="9px" font-family="Arial" text-align="right">Yes <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/>&#160;&#160;&#160;No <fo:external-graphic src="../AccountantIns/image/check-btn6.png"/></fo:block></fo:table-cell>
									        </fo:table-row>
									    </fo:table-body>
									</fo:table>
								</xsl:when>
								<xsl:otherwise>
									<fo:table margin-top="3mm" border="0.0pt solid black">  
										<fo:table-column column-width="5mm"/>
										<fo:table-column column-width="154mm"/>
										<fo:table-column/>
										<fo:table-body>
										    <fo:table-row>
										    	<fo:table-cell><fo:block font-size="9px" font-family="Arial">2.	</fo:block></fo:table-cell>
									        	<fo:table-cell><fo:block font-size="9px" font-family="Arial">Does your Firm have a contractual relationship with a securities broker or dealer?</fo:block></fo:table-cell>
									        	<fo:table-cell><fo:block font-size="9px" font-family="Arial" text-align="right">Yes <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/>&#160;&#160;&#160;No <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/></fo:block></fo:table-cell>
									        </fo:table-row>
									    </fo:table-body>
									</fo:table>
								</xsl:otherwise>
							</xsl:choose>
							<xsl:if test="response/invest_finan_planing_freeform_01/data/IsContractualRelationShip = 'Y'">
								<fo:block>
									<xsl:choose>
									<xsl:when test="response/invest_finan_planing_freeform_01/data/IsOmissionsPolicy = 'Y'">
											<fo:table margin-top="1mm" border="0.0pt solid black">  
												<fo:table-column column-width="5mm"/>
												<fo:table-column column-width="154mm"/>
												<fo:table-column/>
												<fo:table-body>
													<fo:table-row>
												    	<fo:table-cell><fo:block font-size="9px" font-family="Arial"></fo:block></fo:table-cell>
											        	<fo:table-cell><fo:block font-size="9px" font-family="Arial">If “Yes”, are you or individual registered representatives of your Firm insured under a broker or dealer’s errors and omissions policy? </fo:block></fo:table-cell>
													    <fo:table-cell><fo:block font-size="9px" font-family="Arial" text-align="right">Yes <fo:external-graphic src="../AccountantIns/image/check-btn6.png"/>&#160;&#160;&#160;No <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/></fo:block></fo:table-cell>
											        </fo:table-row>
												</fo:table-body>
											</fo:table>
									</xsl:when>
									<xsl:when test="response/invest_finan_planing_freeform_01/data/IsOmissionsPolicy = 'N'">
											<fo:table margin-top="1mm" border="0.0pt solid black">  
												<fo:table-column column-width="5mm"/>
												<fo:table-column column-width="154mm"/>
												<fo:table-column/>
												<fo:table-body>
													<fo:table-row>
												    	<fo:table-cell><fo:block font-size="9px" font-family="Arial"></fo:block></fo:table-cell>
											        	<fo:table-cell><fo:block font-size="9px" font-family="Arial">If “Yes”, are you or individual registered representatives of your Firm insured under a broker or dealer’s errors and omissions policy? </fo:block></fo:table-cell>
													    <fo:table-cell><fo:block font-size="9px" font-family="Arial" text-align="right">Yes <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/>&#160;&#160;&#160;No <fo:external-graphic src="../AccountantIns/image/check-btn6.png"/></fo:block></fo:table-cell>
											        </fo:table-row>
												</fo:table-body>
											</fo:table>
									</xsl:when>
									<xsl:otherwise>
											<fo:table margin-top="1mm" border="0.0pt solid black">  
												<fo:table-column column-width="5mm"/>
												<fo:table-column column-width="154mm"/>
												<fo:table-column/>
												<fo:table-body>
													<fo:table-row>
												    	<fo:table-cell><fo:block font-size="9px" font-family="Arial"></fo:block></fo:table-cell>
											        	<fo:table-cell><fo:block font-size="9px" font-family="Arial">If “Yes”, are you or individual registered representatives of your Firm insured under a broker or dealer’s errors and omissions policy? </fo:block></fo:table-cell>
													    <fo:table-cell><fo:block font-size="9px" font-family="Arial" text-align="right">Yes <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/>&#160;&#160;&#160;No <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/></fo:block></fo:table-cell>
											        </fo:table-row>
												</fo:table-body>
											</fo:table>
									</xsl:otherwise>
									</xsl:choose>							
								</fo:block>	
							</xsl:if>  
						</fo:block>
				
						<fo:block>
								<xsl:choose>
								<xsl:when test="response/invest_finan_planing_freeform_01/data/IsAnyNonPublicInvestments = 'Y'">
									<fo:table margin-top="3mm" border="0.0pt solid black">  
										<fo:table-column column-width="5mm"/>
										<fo:table-column column-width="154mm"/>
										<fo:table-column/>
										<fo:table-body>
										    <fo:table-row>
										    	<fo:table-cell><fo:block font-size="9px" font-family="Arial">3.	</fo:block></fo:table-cell>
									        	<fo:table-cell><fo:block font-size="9px" font-family="Arial">Within the past 5 years, have any personnel recommended any non-public investments to clients in which the Firm or their personnel have an ownership interest?</fo:block></fo:table-cell>
									        	<fo:table-cell><fo:block font-size="9px" font-family="Arial" text-align="right">Yes <fo:external-graphic src="../AccountantIns/image/check-btn6.png"/>&#160;&#160;&#160;No <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/></fo:block></fo:table-cell>
									        </fo:table-row>
									    </fo:table-body>
									</fo:table>
								</xsl:when>
								<xsl:when test="response/invest_finan_planing_freeform_01/data/IsAnyNonPublicInvestments = 'N'">
									<fo:table margin-top="3mm" border="0.0pt solid black">  
										<fo:table-column column-width="5mm"/>
										<fo:table-column column-width="154mm"/>
										<fo:table-column/>
										<fo:table-body>
										    <fo:table-row>
										    	<fo:table-cell><fo:block font-size="9px" font-family="Arial">3.	</fo:block></fo:table-cell>
									        	<fo:table-cell><fo:block font-size="9px" font-family="Arial">Within the past 5 years, have any personnel recommended any non-public investments to clients in which the Firm or their personnel have an ownership interest?</fo:block></fo:table-cell>
									        	<fo:table-cell><fo:block font-size="9px" font-family="Arial" text-align="right">Yes <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/>&#160;&#160;&#160;No <fo:external-graphic src="../AccountantIns/image/check-btn6.png"/></fo:block></fo:table-cell>
									        </fo:table-row>
									    </fo:table-body>
									</fo:table>
								</xsl:when>
								<xsl:otherwise>
									<fo:table margin-top="3mm" border="0.0pt solid black">  
										<fo:table-column column-width="5mm"/>
										<fo:table-column column-width="154mm"/>
										<fo:table-column/>
										<fo:table-body>
										    <fo:table-row>
										    	<fo:table-cell><fo:block font-size="9px" font-family="Arial">3.	</fo:block></fo:table-cell>
									        	<fo:table-cell><fo:block font-size="9px" font-family="Arial">Within the past 5 years, have any personnel recommended any non-public investments to clients in which the Firm or their personnel have an ownership interest?</fo:block></fo:table-cell>
									        	<fo:table-cell><fo:block font-size="9px" font-family="Arial" text-align="right">Yes <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/>&#160;&#160;&#160;No <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/></fo:block></fo:table-cell>
									        </fo:table-row>
									    </fo:table-body>
									</fo:table>
								</xsl:otherwise>
							</xsl:choose>
						</fo:block>
						
						<fo:block>
								<xsl:choose>
								<xsl:when test="response/invest_finan_planing_freeform_01/data/IsAnyFirmAffiliateLicensed = 'Y'">
									<fo:table margin-top="3mm" border="0.0pt solid black">  
										<fo:table-column column-width="5mm"/>
										<fo:table-column column-width="154mm"/>
										<fo:table-column/>
										<fo:table-body>
										    <fo:table-row>
										    	<fo:table-cell><fo:block font-size="9px" font-family="Arial">4.	</fo:block></fo:table-cell>
									        	<fo:table-cell><fo:block font-size="9px" font-family="Arial">Is any person in your Firm licensed as life/health/accident/disability insurance agent or broker?</fo:block></fo:table-cell>
									        	<fo:table-cell><fo:block font-size="9px" font-family="Arial" text-align="right">Yes <fo:external-graphic src="../AccountantIns/image/check-btn6.png"/>&#160;&#160;&#160;No <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/></fo:block></fo:table-cell>
									        </fo:table-row>
									    </fo:table-body>
									</fo:table>
								</xsl:when>
								<xsl:when test="response/invest_finan_planing_freeform_01/data/IsAnyFirmAffiliateLicensed = 'N'">
									<fo:table margin-top="3mm" border="0.0pt solid black">  
										<fo:table-column column-width="5mm"/>
										<fo:table-column column-width="154mm"/>
										<fo:table-column/>
										<fo:table-body>
										    <fo:table-row>
										    	<fo:table-cell><fo:block font-size="9px" font-family="Arial">4.	</fo:block></fo:table-cell>
									        	<fo:table-cell><fo:block font-size="9px" font-family="Arial">Is any person in your Firm licensed as life/health/accident/disability insurance agent or broker?</fo:block></fo:table-cell>
									        	<fo:table-cell><fo:block font-size="9px" font-family="Arial" text-align="right">Yes <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/>&#160;&#160;&#160;No <fo:external-graphic src="../AccountantIns/image/check-btn6.png"/></fo:block></fo:table-cell>
									        </fo:table-row>
									    </fo:table-body>
									</fo:table>
								</xsl:when>
								<xsl:otherwise>
									<fo:table margin-top="3mm" border="0.0pt solid black">  
										<fo:table-column column-width="5mm"/>
										<fo:table-column column-width="154mm"/>
										<fo:table-column/>
										<fo:table-body>
										    <fo:table-row>
										    	<fo:table-cell><fo:block font-size="9px" font-family="Arial">4.	</fo:block></fo:table-cell>
									        	<fo:table-cell><fo:block font-size="9px" font-family="Arial">Is any person in your Firm licensed as life/health/accident/disability insurance agent or broker?</fo:block></fo:table-cell>
									        	<fo:table-cell><fo:block font-size="9px" font-family="Arial" text-align="right">Yes <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/>&#160;&#160;&#160;No <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/></fo:block></fo:table-cell>
									        </fo:table-row>
									    </fo:table-body>
									</fo:table>
								</xsl:otherwise>
							</xsl:choose>
						</fo:block>
												
						<fo:block>
								<xsl:choose>
								<xsl:when test="response/invest_finan_planing_freeform_01/data/IsAnyEmployeeBenefitPlan = 'Y'">
									<fo:table margin-top="3mm" border="0.0pt solid black">  
										<fo:table-column column-width="5mm"/>
										<fo:table-column column-width="154mm"/>
										<fo:table-column/>
										<fo:table-body>
										    <fo:table-row>
										    	<fo:table-cell><fo:block font-size="9px" font-family="Arial">5.	</fo:block></fo:table-cell>
									        	<fo:table-cell><fo:block font-size="9px" font-family="Arial">Does your Firm or its personnel have discretionary authority to invest for any employee benefit plan?</fo:block></fo:table-cell>
									        	<fo:table-cell><fo:block font-size="9px" font-family="Arial" text-align="right">Yes <fo:external-graphic src="../AccountantIns/image/check-btn6.png"/>&#160;&#160;&#160;No <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/></fo:block></fo:table-cell>
									        </fo:table-row>
									    </fo:table-body>
									</fo:table>
								</xsl:when>
								<xsl:when test="response/invest_finan_planing_freeform_01/data/IsAnyEmployeeBenefitPlan = 'N'">
									<fo:table margin-top="3mm" border="0.0pt solid black">  
										<fo:table-column column-width="5mm"/>
										<fo:table-column column-width="154mm"/>
										<fo:table-column/>
										<fo:table-body>
										    <fo:table-row>
										    	<fo:table-cell><fo:block font-size="9px" font-family="Arial">5.	</fo:block></fo:table-cell>
									        	<fo:table-cell><fo:block font-size="9px" font-family="Arial">Does your Firm or its personnel have discretionary authority to invest for any employee benefit plan?</fo:block></fo:table-cell>
									        	<fo:table-cell><fo:block font-size="9px" font-family="Arial" text-align="right">Yes <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/>&#160;&#160;&#160;No <fo:external-graphic src="../AccountantIns/image/check-btn6.png"/></fo:block></fo:table-cell>
									        </fo:table-row>
									    </fo:table-body>
									</fo:table>
								</xsl:when>
								<xsl:otherwise>
									<fo:table margin-top="3mm" border="0.0pt solid black">  
										<fo:table-column column-width="5mm"/>
										<fo:table-column column-width="154mm"/>
										<fo:table-column/>
										<fo:table-body>
										    <fo:table-row>
										    	<fo:table-cell><fo:block font-size="9px" font-family="Arial">5.	</fo:block></fo:table-cell>
									        	<fo:table-cell><fo:block font-size="9px" font-family="Arial">Does your Firm or its personnel have discretionary authority to invest for any employee benefit plan?</fo:block></fo:table-cell>
									        	<fo:table-cell><fo:block font-size="9px" font-family="Arial" text-align="right">Yes <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/>&#160;&#160;&#160;No <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/></fo:block></fo:table-cell>
									        </fo:table-row>
									    </fo:table-body>
									</fo:table>
								</xsl:otherwise>
							</xsl:choose>
						</fo:block>
								
					</xsl:if>
				
				<xsl:choose>
					<xsl:when test="response/firm_freeform_01/data/IsControlClientFunds = 'Y'">
						<fo:block break-after="page"></fo:block>
						
								
									
				<fo:block>
							<fo:table margin-top="3mm" border="0.5pt solid grey" background-color="grey">
								<fo:table-column></fo:table-column>
								<fo:table-body>
									<fo:table-row>
										<fo:table-cell>
											<fo:block margin-top="1mm" font-family="Arial" font-weight="bold" font-size="9px">&#160;&#160;BUSINESS MANAGEMENT SUPPLEMENT (As required in question 5 and 3 S)</fo:block>
										</fo:table-cell>
									</fo:table-row>
									<fo:table-row>
										<fo:table-cell>
											<fo:block margin-top="1mm"></fo:block>
										</fo:table-cell>
									</fo:table-row>
								</fo:table-body>
							</fo:table>
					
				</fo:block>			
				
				
				
						
				<fo:block>
						<xsl:choose>
						<xsl:when test="response/general_freeform_03/data/HaveInvestmentAuthorityFC = 'Y'">
							<fo:table margin-top="3mm" border="0.0pt solid black">  
								<fo:table-column column-width="5mm"/>
								<fo:table-column column-width="154mm"/>
								<fo:table-column/>
								<fo:table-body>
								    <fo:table-row>
								    	<fo:table-cell><fo:block font-size="9px" font-family="Arial">1.	</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial">Does your Firm or any owners, partners or officers have discretionary authority over funds?</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial" text-align="right">Yes <fo:external-graphic src="../AccountantIns/image/check-btn6.png"/>&#160;&#160;&#160;No <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/></fo:block></fo:table-cell>
							        </fo:table-row>
							    </fo:table-body>
							</fo:table>
						</xsl:when>
						<xsl:when test="response/general_freeform_03/data/HaveInvestmentAuthorityFC = 'N'">
							<fo:table margin-top="3mm" border="0.0pt solid black">  
								<fo:table-column column-width="5mm"/>
								<fo:table-column column-width="154mm"/>
								<fo:table-column/>
								<fo:table-body>
								    <fo:table-row>
								    	<fo:table-cell><fo:block font-size="9px" font-family="Arial">1.	</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial">Does your Firm or any owners, partners or officers have discretionary authority over funds?</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial" text-align="right">Yes <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/>&#160;&#160;&#160;No <fo:external-graphic src="../AccountantIns/image/check-btn6.png"/></fo:block></fo:table-cell>
							        </fo:table-row>
							    </fo:table-body>
							</fo:table>
						</xsl:when>
						<xsl:otherwise>
							<fo:table margin-top="3mm" border="0.0pt solid black">  
								<fo:table-column column-width="5mm"/>
								<fo:table-column column-width="154mm"/>
								<fo:table-column/>
								<fo:table-body>
								    <fo:table-row>
								    	<fo:table-cell><fo:block font-size="9px" font-family="Arial">1.	</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial">Does your Firm or any owners, partners or officers have discretionary authority over funds?</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial" text-align="right">Yes <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/>&#160;&#160;&#160;No <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/></fo:block></fo:table-cell>
							        </fo:table-row>
							    </fo:table-body>
							</fo:table>
						</xsl:otherwise>
					</xsl:choose>					
				</fo:block>
								
				
				<fo:block>
						
							<fo:table margin-top="3mm" border="0.0pt solid black">  
								<fo:table-column column-width="5mm"/>
								<fo:table-column column-width="154mm"/>
								<fo:table-column/>
								<fo:table-body>
								    <fo:table-row>
								    	<fo:table-cell><fo:block font-size="9px" font-family="Arial">2.	</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial">What is the total number of clients for which your Firm controls or disburses funds?</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial" text-align="right"><xsl:value-of select="response/general_freeform_03/data/TotlaNumberOfClientsFc"></xsl:value-of> </fo:block></fo:table-cell>
							        </fo:table-row>
							    </fo:table-body>
							</fo:table>
						
				</fo:block>
				
				<fo:block>
						
							<fo:table margin-top="3mm" border="0.0pt solid black">  
								<fo:table-column column-width="5mm"/>
								<fo:table-column column-width="154mm"/>
								<fo:table-column/>
								<fo:table-body>
								    <fo:table-row>
								    	<fo:table-cell><fo:block font-size="9px" font-family="Arial">3.	</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial">List total amount of client funds your Firm disburses annually:</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial" text-align="right"><xsl:value-of select="response/general_freeform_03/data/TotalAmountOfClientFundsFc"></xsl:value-of> </fo:block></fo:table-cell>
							        </fo:table-row>
							    </fo:table-body>
							</fo:table>
						
				</fo:block>
								
				<fo:block>
						<xsl:choose>
						<xsl:when test="response/general_freeform_03/data/IsControlFundsForBusinessAndEntimentFc = 'Y'">
							<fo:table margin-top="3mm" border="0.0pt solid black">  
								<fo:table-column column-width="5mm"/>
								<fo:table-column column-width="154mm"/>
								<fo:table-column/>
								<fo:table-body>
								    <fo:table-row>
								    	<fo:table-cell><fo:block font-size="9px" font-family="Arial">4.	</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial">Other than for the preparation and filing of federal, state or local tax returns, do you provide business/personal management or family office services or control funds for clients in the entertainment or sports industry?</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial" text-align="right">Yes <fo:external-graphic src="../AccountantIns/image/check-btn6.png"/>&#160;&#160;&#160;No <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/></fo:block></fo:table-cell>
							        </fo:table-row>
							    </fo:table-body>
							</fo:table>
						</xsl:when>
						<xsl:when test="response/general_freeform_03/data/IsControlFundsForBusinessAndEntimentFc = 'N'">
							<fo:table margin-top="3mm" border="0.0pt solid black">  
								<fo:table-column column-width="5mm"/>
								<fo:table-column column-width="154mm"/>
								<fo:table-column/>
								<fo:table-body>
								    <fo:table-row>
								    	<fo:table-cell><fo:block font-size="9px" font-family="Arial">4.	</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial">Other than for the preparation and filing of federal, state or local tax returns, do you provide business/personal management or family office services or control funds for clients in the entertainment or sports industry?</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial" text-align="right">Yes <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/>&#160;&#160;&#160;No <fo:external-graphic src="../AccountantIns/image/check-btn6.png"/></fo:block></fo:table-cell>
							        </fo:table-row>
							    </fo:table-body>
							</fo:table>
						</xsl:when>
						<xsl:otherwise>
							<fo:table margin-top="3mm" border="0.0pt solid black">  
								<fo:table-column column-width="5mm"/>
								<fo:table-column column-width="154mm"/>
								<fo:table-column/>
								<fo:table-body>
								    <fo:table-row>
								    	<fo:table-cell><fo:block font-size="9px" font-family="Arial">4.	</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial">Other than for the preparation and filing of federal, state or local tax returns, do you provide business/personal management or family office services or control funds for clients in the entertainment or sports industry?</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial" text-align="right">Yes <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/>&#160;&#160;&#160;No <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/></fo:block></fo:table-cell>
							        </fo:table-row>
							    </fo:table-body>
							</fo:table>
						</xsl:otherwise>
					</xsl:choose>
				</fo:block>
											
				<fo:block>
						<xsl:choose>
						<xsl:when test="response/general_freeform_03/data/IsCountersignatureRequiredFc = 'Y'">
							<fo:table margin-top="3mm" border="0.0pt solid black">  
								<fo:table-column column-width="5mm"/>
								<fo:table-column column-width="154mm"/>
								<fo:table-column/>
								<fo:table-body>
								    <fo:table-row>
								    	<fo:table-cell><fo:block font-size="9px" font-family="Arial">5.	</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial">Is a client countersignature required on all client checks issued by personnel of the Firm?</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial" text-align="right">Yes <fo:external-graphic src="../AccountantIns/image/check-btn6.png"/>&#160;&#160;&#160;No <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/></fo:block></fo:table-cell>
							        </fo:table-row>
							    </fo:table-body>
							</fo:table>
						</xsl:when>
						<xsl:when test="response/general_freeform_03/data/IsCountersignatureRequiredFc = 'N'">
							<fo:table margin-top="3mm" border="0.0pt solid black">  
								<fo:table-column column-width="5mm"/>
								<fo:table-column column-width="154mm"/>
								<fo:table-column/>
								<fo:table-body>
								    <fo:table-row>
								    	<fo:table-cell><fo:block font-size="9px" font-family="Arial">5.	</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial">Is a client countersignature required on all client checks issued by personnel of the Firm?</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial" text-align="right">Yes <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/>&#160;&#160;&#160;No <fo:external-graphic src="../AccountantIns/image/check-btn6.png"/></fo:block></fo:table-cell>
							        </fo:table-row>
							    </fo:table-body>
							</fo:table>
						</xsl:when>
						<xsl:otherwise>
							<fo:table margin-top="3mm" border="0.0pt solid black">  
								<fo:table-column column-width="5mm"/>
								<fo:table-column column-width="154mm"/>
								<fo:table-column/>
								<fo:table-body>
								    <fo:table-row>
								    	<fo:table-cell><fo:block font-size="9px" font-family="Arial">5.	</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial">Is a client countersignature required on all client checks issued by personnel of the Firm?</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial" text-align="right">Yes <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/>&#160;&#160;&#160;No <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/></fo:block></fo:table-cell>
							        </fo:table-row>
							    </fo:table-body>
							</fo:table>
						</xsl:otherwise>
					</xsl:choose>
				</fo:block>
												
				<fo:block>
						<xsl:choose>
						<xsl:when test="response/general_freeform_03/data/IsBankAccountReconciledFc = 'Y'">
							<fo:table margin-top="3mm" border="0.0pt solid black">  
								<fo:table-column column-width="5mm"/>
								<fo:table-column column-width="154mm"/>
								<fo:table-column/>
								<fo:table-body>
								    <fo:table-row>
								    	<fo:table-cell><fo:block font-size="9px" font-family="Arial">6.	</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial">Are all client bank accounts reconciled by someone other than Firm personnel authorized to deposit or withdraw therefrom?</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial" text-align="right">Yes <fo:external-graphic src="../AccountantIns/image/check-btn6.png"/>&#160;&#160;&#160;No <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/></fo:block></fo:table-cell>
							        </fo:table-row>
							    </fo:table-body>
							</fo:table>
						</xsl:when>
						<xsl:when test="response/general_freeform_03/data/IsBankAccountReconciledFc = 'N'">
							<fo:table margin-top="3mm" border="0.0pt solid black">  
								<fo:table-column column-width="5mm"/>
								<fo:table-column column-width="154mm"/>
								<fo:table-column/>
								<fo:table-body>
								    <fo:table-row>
								    	<fo:table-cell><fo:block font-size="9px" font-family="Arial">6.	</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial">Are all client bank accounts reconciled by someone other than Firm personnel authorized to deposit or withdraw therefrom?</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial" text-align="right">Yes <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/>&#160;&#160;&#160;No <fo:external-graphic src="../AccountantIns/image/check-btn6.png"/></fo:block></fo:table-cell>
							        </fo:table-row>
							    </fo:table-body>
							</fo:table>
						</xsl:when>
						<xsl:otherwise>
							<fo:table margin-top="3mm" border="0.0pt solid black">  
								<fo:table-column column-width="5mm"/>
								<fo:table-column column-width="154mm"/>
								<fo:table-column/>
								<fo:table-body>
								    <fo:table-row>
								    	<fo:table-cell><fo:block font-size="9px" font-family="Arial">6.	</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial">Are all client bank accounts reconciled by someone other than Firm personnel authorized to deposit or withdraw therefrom?</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial" text-align="right">Yes <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/>&#160;&#160;&#160;No <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/></fo:block></fo:table-cell>
							        </fo:table-row>
							    </fo:table-body>
							</fo:table>
						</xsl:otherwise>
					</xsl:choose>
				</fo:block>
								
				<fo:block>
						<xsl:choose>
						<xsl:when test="response/general_freeform_03/data/IsFirmPersonnelBusinessManagerFc = 'Y'">
							<fo:table margin-top="3mm" border="0.0pt solid black">  
								<fo:table-column column-width="5mm"/>
								<fo:table-column column-width="154mm"/>
								<fo:table-column/>
								<fo:table-body>
								    <fo:table-row>
								    	<fo:table-cell><fo:block font-size="9px" font-family="Arial">7.	</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial">Do any Firm personnel act as a business manager or CFO for clients?</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial" text-align="right">Yes <fo:external-graphic src="../AccountantIns/image/check-btn6.png"/>&#160;&#160;&#160;No <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/></fo:block></fo:table-cell>
							        </fo:table-row>
							    </fo:table-body>
							</fo:table>
						</xsl:when>
						<xsl:when test="response/general_freeform_03/data/IsFirmPersonnelBusinessManagerFc = 'N'">
							<fo:table margin-top="3mm" border="0.0pt solid black">  
								<fo:table-column column-width="5mm"/>
								<fo:table-column column-width="154mm"/>
								<fo:table-column/>
								<fo:table-body>
								    <fo:table-row>
								    	<fo:table-cell><fo:block font-size="9px" font-family="Arial">7.	</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial">Do any Firm personnel act as a business manager or CFO for clients?</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial" text-align="right">Yes <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/>&#160;&#160;&#160;No <fo:external-graphic src="../AccountantIns/image/check-btn6.png"/></fo:block></fo:table-cell>
							        </fo:table-row>
							    </fo:table-body>
							</fo:table>
						</xsl:when>
						<xsl:otherwise>
							<fo:table margin-top="3mm" border="0.0pt solid black">  
								<fo:table-column column-width="5mm"/>
								<fo:table-column column-width="154mm"/>
								<fo:table-column/>
								<fo:table-body>
								    <fo:table-row>
								    	<fo:table-cell><fo:block font-size="9px" font-family="Arial">7.	</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial">Do any Firm personnel act as a business manager or CFO for clients?</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial" text-align="right">Yes <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/>&#160;&#160;&#160;No <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/></fo:block></fo:table-cell>
							        </fo:table-row>
							    </fo:table-body>
							</fo:table>
						</xsl:otherwise>
					</xsl:choose>
					<fo:table>
						<fo:table-column column-width="100mm"></fo:table-column>
						<fo:table-column column-width="100mm"></fo:table-column>
							<fo:table-body>
								    <fo:table-row>
								    	<fo:table-cell><fo:block font-size="9px" font-family="Arial">If yes, provide the number of such clients: </fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial"> <xsl:value-of select="response/general_freeform_03/data/ProvideNumberOfClientFc"></xsl:value-of> </fo:block></fo:table-cell>							        	
							        </fo:table-row>
							        <fo:table-row>
								    	<fo:table-cell><fo:block font-size="9px" font-family="Arial">Client Industry :</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial">   <xsl:value-of select="response/ClientServiceDesc_FundControl_Firm_freeform_01/data/ClientIndustryDescFc"></xsl:value-of></fo:block></fo:table-cell>							        	
							        </fo:table-row>
							</fo:table-body>
					</fo:table>
				</fo:block>
								
				<fo:block>
						
							<fo:table margin-top="3mm" border="0.0pt solid black">  
								<fo:table-column column-width="5mm"/>
								<fo:table-column column-width="154mm"/>
								<fo:table-column/>
								<fo:table-body>
								    <fo:table-row>
								    	<fo:table-cell><fo:block font-size="9px" font-family="Arial">8.	</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial">Provide the number of Firm employees who control or disburse funds for the Firm or clients</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial" text-align="right"><xsl:value-of select="response/general_freeform_03/data/NumberOfFirmEmployeesFc"></xsl:value-of> </fo:block></fo:table-cell>
							        </fo:table-row>
							    </fo:table-body>
							</fo:table>
						
				</fo:block>
				
				<fo:block>
						<xsl:choose>
						<xsl:when test="response/general_freeform_03/data/IsEmployeeDishonestyCoverageFc = 'Y'">
							<fo:table margin-top="3mm" border="0.0pt solid black">  
								<fo:table-column column-width="5mm"/>
								<fo:table-column column-width="154mm"/>
								<fo:table-column/>
								<fo:table-body>
								    <fo:table-row>
								    	<fo:table-cell><fo:block font-size="9px" font-family="Arial">9.	</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial">Does the Firm carry Employee Dishonesty Coverage?</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial" text-align="right">Yes <fo:external-graphic src="../AccountantIns/image/check-btn6.png"/>&#160;&#160;&#160;No <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/></fo:block></fo:table-cell>
							        </fo:table-row>
							    </fo:table-body>
							</fo:table>
						</xsl:when>
						<xsl:when test="response/general_freeform_03/data/IsEmployeeDishonestyCoverageFc = 'N'">
							<fo:table margin-top="3mm" border="0.0pt solid black">  
								<fo:table-column column-width="5mm"/>
								<fo:table-column column-width="154mm"/>
								<fo:table-column/>
								<fo:table-body>
								    <fo:table-row>
								    	<fo:table-cell><fo:block font-size="9px" font-family="Arial">9.	</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial">Does the Firm carry Employee Dishonesty Coverage?</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial" text-align="right">Yes <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/>&#160;&#160;&#160;No <fo:external-graphic src="../AccountantIns/image/check-btn6.png"/></fo:block></fo:table-cell>
							        </fo:table-row>
							    </fo:table-body>
							</fo:table>
						</xsl:when>
						<xsl:otherwise>
							<fo:table margin-top="3mm" border="0.0pt solid black">  
								<fo:table-column column-width="5mm"/>
								<fo:table-column column-width="154mm"/>
								<fo:table-column/>
								<fo:table-body>
								    <fo:table-row>
								    	<fo:table-cell><fo:block font-size="9px" font-family="Arial">9.	</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial">Does the Firm carry Employee Dishonesty Coverage?</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial" text-align="right">Yes <fo:external-graphic src="../AccountantIns/image/check-btn6.png"/>&#160;&#160;&#160;No <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/></fo:block></fo:table-cell>
							        </fo:table-row>
							    </fo:table-body>
							</fo:table>
						</xsl:otherwise>
					</xsl:choose>
				</fo:block>
				
			
			
						
						
					</xsl:when>
					<xsl:when test="response/showBusinessSuppPDF = 'Y' ">
						<fo:block break-after="page"></fo:block>
									
									
				<fo:block>
							<fo:table margin-top="3mm" border="0.5pt solid grey" background-color="grey">
								<fo:table-column></fo:table-column>
								<fo:table-body>
									<fo:table-row>
										<fo:table-cell>
											<fo:block margin-top="1mm" font-family="Arial" font-weight="bold" font-size="9px">&#160;&#160;BUSINESS MANAGEMENT SUPPLEMENT (As required in question 5 and 3 S)</fo:block>
										</fo:table-cell>
									</fo:table-row>
									<fo:table-row>
										<fo:table-cell>
											<fo:block margin-top="1mm"></fo:block>
										</fo:table-cell>
									</fo:table-row>
								</fo:table-body>
							</fo:table>
					
				</fo:block>			
				
				
				
						
				<fo:block>
						<xsl:choose>
						<xsl:when test="response/general_freeform_03/data/HaveInvestmentAuthority = 'Y'">
							<fo:table margin-top="3mm" border="0.0pt solid black">  
								<fo:table-column column-width="5mm"/>
								<fo:table-column column-width="154mm"/>
								<fo:table-column/>
								<fo:table-body>
								    <fo:table-row>
								    	<fo:table-cell><fo:block font-size="9px" font-family="Arial">1.	</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial">Does your Firm or any owners, partners or officers have discretionary authority over funds?</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial" text-align="right">Yes <fo:external-graphic src="../AccountantIns/image/check-btn6.png"/>&#160;&#160;&#160;No <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/></fo:block></fo:table-cell>
							        </fo:table-row>
							    </fo:table-body>
							</fo:table>
						</xsl:when>
						<xsl:when test="response/general_freeform_03/data/HaveInvestmentAuthority = 'N'">
							<fo:table margin-top="3mm" border="0.0pt solid black">  
								<fo:table-column column-width="5mm"/>
								<fo:table-column column-width="154mm"/>
								<fo:table-column/>
								<fo:table-body>
								    <fo:table-row>
								    	<fo:table-cell><fo:block font-size="9px" font-family="Arial">1.	</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial">Does your Firm or any owners, partners or officers have discretionary authority over funds?</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial" text-align="right">Yes <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/>&#160;&#160;&#160;No <fo:external-graphic src="../AccountantIns/image/check-btn6.png"/></fo:block></fo:table-cell>
							        </fo:table-row>
							    </fo:table-body>
							</fo:table>
						</xsl:when>
						<xsl:otherwise>
							<fo:table margin-top="3mm" border="0.0pt solid black">  
								<fo:table-column column-width="5mm"/>
								<fo:table-column column-width="154mm"/>
								<fo:table-column/>
								<fo:table-body>
								    <fo:table-row>
								    	<fo:table-cell><fo:block font-size="9px" font-family="Arial">1.	</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial">Does your Firm or any owners, partners or officers have discretionary authority over funds?</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial" text-align="right">Yes <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/>&#160;&#160;&#160;No <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/></fo:block></fo:table-cell>
							        </fo:table-row>
							    </fo:table-body>
							</fo:table>
						</xsl:otherwise>
					</xsl:choose>					
				</fo:block>
								
				
				<fo:block>
						
							<fo:table margin-top="3mm" border="0.0pt solid black">  
								<fo:table-column column-width="5mm"/>
								<fo:table-column column-width="154mm"/>
								<fo:table-column/>
								<fo:table-body>
								    <fo:table-row>
								    	<fo:table-cell><fo:block font-size="9px" font-family="Arial">2.	</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial">What is the total number of clients for which your Firm controls or disburses funds?</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial" text-align="right"><xsl:value-of select="response/general_freeform_03/data/TotlaNumberOfClientsFc"></xsl:value-of> </fo:block></fo:table-cell>
							        </fo:table-row>
							    </fo:table-body>
							</fo:table>
						
				</fo:block>
				
				<fo:block>
						
							<fo:table margin-top="3mm" border="0.0pt solid black">  
								<fo:table-column column-width="5mm"/>
								<fo:table-column column-width="154mm"/>
								<fo:table-column/>
								<fo:table-body>
								    <fo:table-row>
								    	<fo:table-cell><fo:block font-size="9px" font-family="Arial">3.	</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial">List total amount of client funds your Firm disburses annually:</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial" text-align="right"><xsl:value-of select="response/general_freeform_03/data/TotalAmountOfClientFundsFc"></xsl:value-of> </fo:block></fo:table-cell>
							        </fo:table-row>
							    </fo:table-body>
							</fo:table>
						
				</fo:block>
								
				<fo:block>
						<xsl:choose>
						<xsl:when test="response/general_freeform_03/data/IsControlFundsForBusinessAndEntimentFc = 'Y'">
							<fo:table margin-top="3mm" border="0.0pt solid black">  
								<fo:table-column column-width="5mm"/>
								<fo:table-column column-width="154mm"/>
								<fo:table-column/>
								<fo:table-body>
								    <fo:table-row>
								    	<fo:table-cell><fo:block font-size="9px" font-family="Arial">4.	</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial">Other than for the preparation and filing of federal, state or local tax returns, do you provide business/personal management or family office services or control funds for clients in the entertainment or sports industry?</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial" text-align="right">Yes <fo:external-graphic src="../AccountantIns/image/check-btn6.png"/>&#160;&#160;&#160;No <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/></fo:block></fo:table-cell>
							        </fo:table-row>
							    </fo:table-body>
							</fo:table>
						</xsl:when>
						<xsl:when test="response/general_freeform_03/data/IsControlFundsForBusinessAndEntimentFc = 'N'">
							<fo:table margin-top="3mm" border="0.0pt solid black">  
								<fo:table-column column-width="5mm"/>
								<fo:table-column column-width="154mm"/>
								<fo:table-column/>
								<fo:table-body>
								    <fo:table-row>
								    	<fo:table-cell><fo:block font-size="9px" font-family="Arial">4.	</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial">Other than for the preparation and filing of federal, state or local tax returns, do you provide business/personal management or family office services or control funds for clients in the entertainment or sports industry?</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial" text-align="right">Yes <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/>&#160;&#160;&#160;No <fo:external-graphic src="../AccountantIns/image/check-btn6.png"/></fo:block></fo:table-cell>
							        </fo:table-row>
							    </fo:table-body>
							</fo:table>
						</xsl:when>
						<xsl:otherwise>
							<fo:table margin-top="3mm" border="0.0pt solid black">  
								<fo:table-column column-width="5mm"/>
								<fo:table-column column-width="154mm"/>
								<fo:table-column/>
								<fo:table-body>
								    <fo:table-row>
								    	<fo:table-cell><fo:block font-size="9px" font-family="Arial">4.	</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial">Other than for the preparation and filing of federal, state or local tax returns, do you provide business/personal management or family office services or control funds for clients in the entertainment or sports industry?</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial" text-align="right">Yes <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/>&#160;&#160;&#160;No <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/></fo:block></fo:table-cell>
							        </fo:table-row>
							    </fo:table-body>
							</fo:table>
						</xsl:otherwise>
					</xsl:choose>
				</fo:block>
											
				<fo:block>
						<xsl:choose>
						<xsl:when test="response/general_freeform_03/data/IsCountersignatureRequiredFc = 'Y'">
							<fo:table margin-top="3mm" border="0.0pt solid black">  
								<fo:table-column column-width="5mm"/>
								<fo:table-column column-width="154mm"/>
								<fo:table-column/>
								<fo:table-body>
								    <fo:table-row>
								    	<fo:table-cell><fo:block font-size="9px" font-family="Arial">5.	</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial">Is a client countersignature required on all client checks issued by personnel of the Firm?</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial" text-align="right">Yes <fo:external-graphic src="../AccountantIns/image/check-btn6.png"/>&#160;&#160;&#160;No <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/></fo:block></fo:table-cell>
							        </fo:table-row>
							    </fo:table-body>
							</fo:table>
						</xsl:when>
						<xsl:when test="response/general_freeform_03/data/IsCountersignatureRequiredFc = 'N'">
							<fo:table margin-top="3mm" border="0.0pt solid black">  
								<fo:table-column column-width="5mm"/>
								<fo:table-column column-width="154mm"/>
								<fo:table-column/>
								<fo:table-body>
								    <fo:table-row>
								    	<fo:table-cell><fo:block font-size="9px" font-family="Arial">5.	</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial">Is a client countersignature required on all client checks issued by personnel of the Firm?</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial" text-align="right">Yes <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/>&#160;&#160;&#160;No <fo:external-graphic src="../AccountantIns/image/check-btn6.png"/></fo:block></fo:table-cell>
							        </fo:table-row>
							    </fo:table-body>
							</fo:table>
						</xsl:when>
						<xsl:otherwise>
							<fo:table margin-top="3mm" border="0.0pt solid black">  
								<fo:table-column column-width="5mm"/>
								<fo:table-column column-width="154mm"/>
								<fo:table-column/>
								<fo:table-body>
								    <fo:table-row>
								    	<fo:table-cell><fo:block font-size="9px" font-family="Arial">5.	</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial">Is a client countersignature required on all client checks issued by personnel of the Firm?</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial" text-align="right">Yes <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/>&#160;&#160;&#160;No <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/></fo:block></fo:table-cell>
							        </fo:table-row>
							    </fo:table-body>
							</fo:table>
						</xsl:otherwise>
					</xsl:choose>
				</fo:block>
												
				<fo:block>
						<xsl:choose>
						<xsl:when test="response/general_freeform_03/data/IsBankAccountReconciledFc = 'Y'">
							<fo:table margin-top="3mm" border="0.0pt solid black">  
								<fo:table-column column-width="5mm"/>
								<fo:table-column column-width="154mm"/>
								<fo:table-column/>
								<fo:table-body>
								    <fo:table-row>
								    	<fo:table-cell><fo:block font-size="9px" font-family="Arial">6.	</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial">Are all client bank accounts reconciled by someone other than Firm personnel authorized to deposit or withdraw therefrom?</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial" text-align="right">Yes <fo:external-graphic src="../AccountantIns/image/check-btn6.png"/>&#160;&#160;&#160;No <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/></fo:block></fo:table-cell>
							        </fo:table-row>
							    </fo:table-body>
							</fo:table>
						</xsl:when>
						<xsl:when test="response/general_freeform_03/data/IsBankAccountReconciledFc = 'N'">
							<fo:table margin-top="3mm" border="0.0pt solid black">  
								<fo:table-column column-width="5mm"/>
								<fo:table-column column-width="154mm"/>
								<fo:table-column/>
								<fo:table-body>
								    <fo:table-row>
								    	<fo:table-cell><fo:block font-size="9px" font-family="Arial">6.	</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial">Are all client bank accounts reconciled by someone other than Firm personnel authorized to deposit or withdraw therefrom?</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial" text-align="right">Yes <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/>&#160;&#160;&#160;No <fo:external-graphic src="../AccountantIns/image/check-btn6.png"/></fo:block></fo:table-cell>
							        </fo:table-row>
							    </fo:table-body>
							</fo:table>
						</xsl:when>
						<xsl:otherwise>
							<fo:table margin-top="3mm" border="0.0pt solid black">  
								<fo:table-column column-width="5mm"/>
								<fo:table-column column-width="154mm"/>
								<fo:table-column/>
								<fo:table-body>
								    <fo:table-row>
								    	<fo:table-cell><fo:block font-size="9px" font-family="Arial">6.	</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial">Are all client bank accounts reconciled by someone other than Firm personnel authorized to deposit or withdraw therefrom?</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial" text-align="right">Yes <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/>&#160;&#160;&#160;No <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/></fo:block></fo:table-cell>
							        </fo:table-row>
							    </fo:table-body>
							</fo:table>
						</xsl:otherwise>
					</xsl:choose>
				</fo:block>
								
				<fo:block>
						<xsl:choose>
						<xsl:when test="response/general_freeform_03/data/IsFirmPersonnelBusinessManagerFc = 'Y'">
							<fo:table margin-top="3mm" border="0.0pt solid black">  
								<fo:table-column column-width="5mm"/>
								<fo:table-column column-width="154mm"/>
								<fo:table-column/>
								<fo:table-body>
								    <fo:table-row>
								    	<fo:table-cell><fo:block font-size="9px" font-family="Arial">7.	</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial">Do any Firm personnel act as a business manager or CFO for clients?</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial" text-align="right">Yes <fo:external-graphic src="../AccountantIns/image/check-btn6.png"/>&#160;&#160;&#160;No <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/></fo:block></fo:table-cell>
							        </fo:table-row>
							    </fo:table-body>
							</fo:table>
						</xsl:when>
						<xsl:when test="response/general_freeform_03/data/IsFirmPersonnelBusinessManagerFc = 'N'">
							<fo:table margin-top="3mm" border="0.0pt solid black">  
								<fo:table-column column-width="5mm"/>
								<fo:table-column column-width="154mm"/>
								<fo:table-column/>
								<fo:table-body>
								    <fo:table-row>
								    	<fo:table-cell><fo:block font-size="9px" font-family="Arial">7.	</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial">Do any Firm personnel act as a business manager or CFO for clients?</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial" text-align="right">Yes <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/>&#160;&#160;&#160;No <fo:external-graphic src="../AccountantIns/image/check-btn6.png"/></fo:block></fo:table-cell>
							        </fo:table-row>
							    </fo:table-body>
							</fo:table>
						</xsl:when>
						<xsl:otherwise>
							<fo:table margin-top="3mm" border="0.0pt solid black">  
								<fo:table-column column-width="5mm"/>
								<fo:table-column column-width="154mm"/>
								<fo:table-column/>
								<fo:table-body>
								    <fo:table-row>
								    	<fo:table-cell><fo:block font-size="9px" font-family="Arial">7.	</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial">Do any Firm personnel act as a business manager or CFO for clients?</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial" text-align="right">Yes <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/>&#160;&#160;&#160;No <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/></fo:block></fo:table-cell>
							        </fo:table-row>
							    </fo:table-body>
							</fo:table>
						</xsl:otherwise>
					</xsl:choose>
					<fo:table>
						<fo:table-column column-width="100mm"></fo:table-column>
						<fo:table-column column-width="100mm"></fo:table-column>
							<fo:table-body>
								    <fo:table-row>
								    	<fo:table-cell><fo:block font-size="9px" font-family="Arial">If yes, provide the number of such clients: </fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial"> <xsl:value-of select="response/general_freeform_03/data/ProvideNumberOfClientFc"></xsl:value-of> </fo:block></fo:table-cell>							        	
							        </fo:table-row>
							        <fo:table-row>
								    	<fo:table-cell><fo:block font-size="9px" font-family="Arial">Client Industry :</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial">   <xsl:value-of select="response/ClientServiceDesc_FundControl_Firm_freeform_01/data/ClientIndustryDescFc"></xsl:value-of></fo:block></fo:table-cell>							        	
							        </fo:table-row>
							</fo:table-body>
					</fo:table>
				</fo:block>
								
				<fo:block>
						
							<fo:table margin-top="3mm" border="0.0pt solid black">  
								<fo:table-column column-width="5mm"/>
								<fo:table-column column-width="154mm"/>
								<fo:table-column/>
								<fo:table-body>
								    <fo:table-row>
								    	<fo:table-cell><fo:block font-size="9px" font-family="Arial">8.	</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial">Provide the number of Firm employees who control or disburse funds for the Firm or clients</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial" text-align="right"><xsl:value-of select="response/general_freeform_03/data/NumberOfFirmEmployeesFc"></xsl:value-of> </fo:block></fo:table-cell>
							        </fo:table-row>
							    </fo:table-body>
							</fo:table>
						
				</fo:block>
				
				<fo:block>
						<xsl:choose>
						<xsl:when test="response/general_freeform_03/data/IsEmployeeDishonestyCoverageFc = 'Y'">
							<fo:table margin-top="3mm" border="0.0pt solid black">  
								<fo:table-column column-width="5mm"/>
								<fo:table-column column-width="154mm"/>
								<fo:table-column/>
								<fo:table-body>
								    <fo:table-row>
								    	<fo:table-cell><fo:block font-size="9px" font-family="Arial">9.	</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial">Does the Firm carry Employee Dishonesty Coverage?</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial" text-align="right">Yes <fo:external-graphic src="../AccountantIns/image/check-btn6.png"/>&#160;&#160;&#160;No <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/></fo:block></fo:table-cell>
							        </fo:table-row>
							    </fo:table-body>
							</fo:table>
						</xsl:when>
						<xsl:when test="response/general_freeform_03/data/IsEmployeeDishonestyCoverageFc = 'N'">
							<fo:table margin-top="3mm" border="0.0pt solid black">  
								<fo:table-column column-width="5mm"/>
								<fo:table-column column-width="154mm"/>
								<fo:table-column/>
								<fo:table-body>
								    <fo:table-row>
								    	<fo:table-cell><fo:block font-size="9px" font-family="Arial">9.	</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial">Does the Firm carry Employee Dishonesty Coverage?</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial" text-align="right">Yes <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/>&#160;&#160;&#160;No <fo:external-graphic src="../AccountantIns/image/check-btn6.png"/></fo:block></fo:table-cell>
							        </fo:table-row>
							    </fo:table-body>
							</fo:table>
						</xsl:when>
						<xsl:otherwise>
							<fo:table margin-top="3mm" border="0.0pt solid black">  
								<fo:table-column column-width="5mm"/>
								<fo:table-column column-width="154mm"/>
								<fo:table-column/>
								<fo:table-body>
								    <fo:table-row>
								    	<fo:table-cell><fo:block font-size="9px" font-family="Arial">9.	</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial">Does the Firm carry Employee Dishonesty Coverage?</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial" text-align="right">Yes <fo:external-graphic src="../AccountantIns/image/check-btn6.png"/>&#160;&#160;&#160;No <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/></fo:block></fo:table-cell>
							        </fo:table-row>
							    </fo:table-body>
							</fo:table>
						</xsl:otherwise>
					</xsl:choose>
				</fo:block>
				
			
			
					</xsl:when>
				</xsl:choose>
				
				
				
				
				<xsl:if test="response/firm_freeform_01/data/IsSuitInstituted = 'Y'">
					<fo:block margin-top="10mm">	
								<fo:block break-after="page"></fo:block>
								<fo:block>
									<fo:table margin-top="3mm" border="0.5pt solid black" background-color="grey">
										<fo:table-column></fo:table-column>
										<fo:table-body>
											<fo:table-row>
												<fo:table-cell>
													<fo:block margin-top="1mm" font-family="Arial" font-weight="bold" font-size="9px">&#160;&#160;FEE SUIT SUPPLEMENT (As required in Question 22)</fo:block>
												</fo:table-cell>
											</fo:table-row>
											<fo:table-row>
												<fo:table-cell>
													<fo:block margin-top="1mm"></fo:block>
												</fo:table-cell>
											</fo:table-row>
										</fo:table-body>
									</fo:table>
									<fo:table margin-top="1mm" border="0.0pt solid black">  
										<fo:table-column column-width="5mm"/>
										<fo:table-column/>
										<fo:table-body>
											<fo:table-row>
										    	<fo:table-cell><fo:block font-size="9px" font-family="Arial">1.</fo:block></fo:table-cell>
										    	<fo:table-cell><fo:block font-size="9px" font-family="Arial">Please complete the following for each suit Applicant has filed against a client for collection of fees due Applicant.</fo:block></fo:table-cell>
									        </fo:table-row>
										</fo:table-body>
									</fo:table>
									<fo:table>
										<fo:table-column column-width="5mm"></fo:table-column>
										<fo:table-column></fo:table-column>
										<fo:table-body>
											<fo:table-row>
												<fo:table-cell><fo:block></fo:block></fo:table-cell>
												<fo:table-cell>
													<fo:block>
														<fo:table margin-top="1mm" border="0.5pt solid black">  
															<fo:table-column/>
															<fo:table-column/>
															<fo:table-column/>
															<fo:table-column/>
															<fo:table-column/>
															<fo:table-column/>
															<fo:table-body>       
														        <fo:table-row>
														        	<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial" text-align="center" font-weight="bold">Named Defendants in Suit</fo:block></fo:table-cell>
														        	<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial" text-align="center" font-weight="bold">Amount of Fees Sued For</fo:block></fo:table-cell>
														        	<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial" text-align="center" font-weight="bold">Date Fees were Due</fo:block></fo:table-cell>
														         	<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial" text-align="center" font-weight="bold">Date Fee Suit Filed</fo:block></fo:table-cell>
														         	
														         	<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial" text-align="center" font-weight="bold">Was there a Counter Claim or Allegation of Legal Malpractice</fo:block></fo:table-cell>
														         	<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial" text-align="center" font-weight="bold">Disposition of Fee Suit*</fo:block></fo:table-cell>
														        </fo:table-row>
								         	 					<xsl:for-each select="response/renewbasicinfo_list_feesuit/data">
													         		<fo:table-row>	
													         			<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial" text-align="center"><xsl:value-of select="NamedDefendantsInSuit"/></fo:block></fo:table-cell>						
														         		<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial" text-align="center"><xsl:value-of select="AmountOfFeesSued"/></fo:block></fo:table-cell>
														         		<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial" text-align="center"><xsl:value-of select="DueDateFees"/></fo:block></fo:table-cell>
														         		<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial" text-align="center"><xsl:value-of select="SuitFilesDateFees"/></fo:block></fo:table-cell>
														         		
														         		<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial" text-align="center"><xsl:choose>
															         		<xsl:when test="IsAllegOfLegalMalPrac = 'Y'"><fo:block font-size="9px" font-family="Arial">Yes <fo:external-graphic src="../AccountantIns/image/check-btn6.png" />&#160;&#160;&#160;No <fo:external-graphic src="../AccountantIns/image/check-btn4.png" /></fo:block></xsl:when>
																				<xsl:when test="IsAllegOfLegalMalPrac = 'N'"><fo:block font-size="9px" font-family="Arial">Yes <fo:external-graphic src="../AccountantIns/image/check-btn4.png" />&#160;&#160;&#160;No <fo:external-graphic src="../AccountantIns/image/check-btn6.png" /></fo:block></xsl:when>
																				</xsl:choose>
														         		</fo:block></fo:table-cell>
														         		<fo:table-cell border="0.5pt solid black"><fo:block font-size="9px" font-family="Arial" text-align="center"><xsl:value-of select="FeeSuitDesc"/></fo:block></fo:table-cell>
														         	</fo:table-row>								
																</xsl:for-each>
															</fo:table-body>
											  			</fo:table>	
													</fo:block>
												</fo:table-cell>
											</fo:table-row>
										</fo:table-body>
									</fo:table>
									<fo:table margin-top="1mm" border="0.0pt solid black">  
										<fo:table-column column-width="5mm"/>
										<fo:table-column/>
										<fo:table-body>       
									        <fo:table-row>
									        	<fo:table-cell><fo:block></fo:block></fo:table-cell>
									        	<fo:table-cell><fo:block font-size="9px" font-family="Arial">*P = Fees paid in full, NS = Negotiated Settlement, JP = Judgment Plaintiff, JD = Judgment Defense, O = Open</fo:block></fo:table-cell>
									        </fo:table-row>
								    	</fo:table-body>
									</fo:table> 
								</fo:block>	
								
						</fo:block>				
				</xsl:if>
				
				
				
				
				<xsl:if test="response/showComputerSuppPDF = 'Y'">
						<fo:block break-after="page"></fo:block>
						<fo:block>
							<fo:table margin-top="3mm" border="0.5pt solid grey" background-color="grey">
								<fo:table-column></fo:table-column>
								<fo:table-body>
									<fo:table-row>
										<fo:table-cell>
											<fo:block margin-top="1mm" font-family="Arial" font-weight="bold" font-size="9px">&#160;&#160;COMPUTER RELATED SERVICES SUPPLEMENT (As required in question 3. O)</fo:block>
										</fo:table-cell>
									</fo:table-row>
									<fo:table-row>
										<fo:table-cell>
											<fo:block margin-top="1mm"></fo:block>
										</fo:table-cell>
									</fo:table-row>
								</fo:table-body>
							</fo:table>
							
							<fo:table margin-top="3mm">
								<fo:table-column column-width="5mm"></fo:table-column>
								<fo:table-column></fo:table-column>
								<fo:table-body>
									<fo:table-row>
										<fo:table-cell><fo:block margin-top="1mm" font-family="Arial" font-size="9px">1. </fo:block></fo:table-cell>
										<fo:table-cell><fo:block margin-top="1mm" font-family="Arial" font-size="9px">Please complete below  the type of Information Technology Services:</fo:block></fo:table-cell>
									</fo:table-row>
								</fo:table-body>
							</fo:table>
							
							<fo:table margin-top="3mm" border="0.5pt solid grey" background-color="grey">
								<fo:table-column column-width="41mm"></fo:table-column>
								<fo:table-column column-width="41mm"></fo:table-column>
								<fo:table-column column-width="41mm"></fo:table-column>
								<fo:table-column column-width="62mm"></fo:table-column>
								<fo:table-body>
									<fo:table-row>
										<fo:table-cell  border="0.5pt solid black"><fo:block font-family="Arial" font-size="9px"></fo:block></fo:table-cell>
										<fo:table-cell  border="0.5pt solid black"><fo:block font-family="Arial" font-size="9px">Revenues From Last Fiscal Year</fo:block></fo:table-cell>
										<fo:table-cell  border="0.5pt solid black"><fo:block  font-family="Arial" font-size="9px">Estimated Revenue from Current Fiscal Year</fo:block></fo:table-cell>
										<fo:table-cell  border="0.5pt solid black"><fo:block  font-family="Arial" font-size="9px">Are Written Agreements or Engagement Letters used Annually?</fo:block></fo:table-cell>
									</fo:table-row>
								</fo:table-body>
							</fo:table>
							<fo:table border="0.5pt solid grey">
								<fo:table-column column-width="41mm"></fo:table-column>
								<fo:table-column column-width="41mm"></fo:table-column>
								<fo:table-column column-width="41mm"></fo:table-column>
								<fo:table-column column-width="62mm"></fo:table-column>
								
								<fo:table-body>
									<xsl:for-each select="response/computerservice_list_03/data">
										<fo:table-row>
											<fo:table-cell border="0.5pt solid black"><fo:block  font-family="Arial" font-size="9px"><xsl:value-of select="ITServiceCategoryDesc"></xsl:value-of></fo:block></fo:table-cell>
											<fo:table-cell  border="0.5pt solid black"><fo:block font-family="Arial" font-size="9px">$<xsl:value-of select="RevenuesFromLastYear"></xsl:value-of></fo:block></fo:table-cell>
											<fo:table-cell  border="0.5pt solid black"><fo:block font-family="Arial" font-size="9px">$<xsl:value-of select="EstimatedRevenue"></xsl:value-of></fo:block></fo:table-cell>
											<fo:table-cell  border="0.5pt solid black"><fo:block font-family="Arial" font-size="9px">
											<xsl:choose>
											<xsl:when test="IsAnnually = 'Y'"><fo:block font-size="9px" font-family="Arial">Yes <fo:external-graphic src="../AccountantIns/image/check-btn6.png" />&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;No <fo:external-graphic src="../AccountantIns/image/check-btn4.png" /></fo:block></xsl:when>
											<xsl:when test="IsAnnually = 'N'"><fo:block font-size="9px" font-family="Arial">Yes <fo:external-graphic src="../AccountantIns/image/check-btn4.png" />&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;No <fo:external-graphic src="../AccountantIns/image/check-btn6.png" /></fo:block></xsl:when>
											<xsl:otherwise><fo:block font-size="9px" font-family="Arial">Yes <fo:external-graphic src="../AccountantIns/image/check-btn4.png" />&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;No <fo:external-graphic src="../AccountantIns/image/check-btn4.png" /></fo:block></xsl:otherwise>
											</xsl:choose>
											</fo:block></fo:table-cell>
											
										</fo:table-row>
									</xsl:for-each>									
								</fo:table-body>
							</fo:table>
							
							<fo:table margin-top="2mm">
								<fo:table-column/>
								<fo:table-body>
									<fo:table-row>
										<fo:table-cell><fo:block font-size="9px" font-family="Arial"> Please Describe : <xsl:value-of select="response/firm_freeform_01/data/OtherITServiceComment"></xsl:value-of></fo:block> </fo:table-cell>
									</fo:table-row>							
								</fo:table-body>
							</fo:table>							
						</fo:block>
					
				</xsl:if>
				
				
				
				
						
						
			
					<xsl:if test="response/showAttesationSuppPDF = 'Y'">
						<fo:block break-after="page"></fo:block>
					
					
						<fo:block>
							<fo:table margin-top="3mm" border="0.5pt solid grey" background-color="grey">
								<fo:table-column></fo:table-column>
								<fo:table-body>
									<fo:table-row>
										<fo:table-cell>
											<fo:block margin-top="1mm" font-family="Arial" font-weight="bold" font-size="9px">&#160;&#160;AUDIT SUPPLEMENT <fo:inline font-style="italic">(As required in question 3 E - F.) </fo:inline>(Continued on the next page)</fo:block>
										</fo:table-cell>
									</fo:table-row>
									<fo:table-row>
										<fo:table-cell>
											<fo:block margin-top="1mm"></fo:block>
										</fo:table-cell>
									</fo:table-row>
								</fo:table-body>
							</fo:table>
							
							<fo:table margin-top="3mm">
								<fo:table-column column-width="5mm"></fo:table-column>
								<fo:table-column></fo:table-column>
								<fo:table-body>
									<fo:table-row>
										<fo:table-cell><fo:block margin-top="1mm" font-family="Arial" font-size="9px">1. </fo:block></fo:table-cell>
										<fo:table-cell><fo:block margin-top="1mm" font-family="Arial" font-size="9px">Please indicate the following for all Audit work.</fo:block></fo:table-cell>
									</fo:table-row>
								</fo:table-body>
							</fo:table>
							
							<fo:table margin-top="3mm" border="0.5pt solid grey">
								<fo:table-column column-width="41mm"></fo:table-column>
								<fo:table-column column-width="41mm"></fo:table-column>
								<fo:table-column column-width="41mm"></fo:table-column>
								<fo:table-column column-width="62mm"></fo:table-column>
								<fo:table-body>
									<fo:table-row>
										<fo:table-cell  border="0.5pt solid black"><fo:block font-family="Arial" font-size="9px">Client Industry</fo:block></fo:table-cell>
										<fo:table-cell  border="0.5pt solid black"><fo:block font-family="Arial" font-size="9px"># of Audit Clients</fo:block></fo:table-cell>
										<fo:table-cell  border="0.5pt solid black"><fo:block  font-family="Arial" font-size="9px"># of Clients with Total Assets over $5,000,000</fo:block></fo:table-cell>
										<fo:table-cell  border="0.5pt solid black"><fo:block  font-family="Arial" font-size="9px"># of Clients with Net Loss for the past Fiscal Year</fo:block></fo:table-cell>
									</fo:table-row>
									
									<xsl:for-each select="response/attestation_list_03/data">
									<fo:table-row>
										<fo:table-cell  border="0.5pt solid black"><fo:block font-family="Arial" font-size="9px"><xsl:value-of select="ClientIndustrySrNo"></xsl:value-of>&#160;<xsl:value-of select="ClientIndustryDesc"></xsl:value-of> </fo:block></fo:table-cell>
										<fo:table-cell  border="0.5pt solid black"><fo:block font-family="Arial" font-size="9px"><xsl:value-of select="AuditClients"></xsl:value-of></fo:block></fo:table-cell>
										<fo:table-cell  border="0.5pt solid black"><fo:block  font-family="Arial" font-size="9px"><xsl:value-of select="ClientsAssetsOver"></xsl:value-of></fo:block></fo:table-cell>
										<fo:table-cell  border="0.5pt solid black"><fo:block  font-family="Arial" font-size="9px"><xsl:value-of select="NetLossForLastFYE"></xsl:value-of></fo:block></fo:table-cell>
									</fo:table-row>
									</xsl:for-each>
									
									<fo:table-row>
										<fo:table-cell  border="0.5pt solid black"><fo:block font-family="Arial" font-size="9px"></fo:block></fo:table-cell>
										<fo:table-cell  border="0.5pt solid black"><fo:block font-family="Arial" font-size="9px"><xsl:value-of select="response/attestation_freeform_02/data/AuditComment"></xsl:value-of></fo:block></fo:table-cell>
										<fo:table-cell  border="0.5pt solid black"><fo:block  font-family="Arial" font-size="9px"></fo:block></fo:table-cell>
										<fo:table-cell  border="0.5pt solid black"><fo:block  font-family="Arial" font-size="9px"></fo:block></fo:table-cell>
									</fo:table-row>
									
								</fo:table-body>
							</fo:table>
						</fo:block>
												
						<fo:block>
							<fo:table margin-top="3mm" border="0.0pt solid black">  
								<fo:table-column column-width="5mm"/>
								<fo:table-column column-width="154mm"/>
								<fo:table-column/>
								<fo:table-body>
								    <fo:table-row>
								    	<fo:table-cell><fo:block font-size="9px" font-family="Arial">2.	</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial">Within the past year how many clients for whom your Firm rendered audit or attestation services subsequently declared or filed bankruptcy, defaulted on a debt obligation or became insolvent?</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial" text-align="right"><xsl:value-of select="response/attestation_freeform_02/data/FirmRenderedAuditAtt"></xsl:value-of></fo:block></fo:table-cell>
							        </fo:table-row>
							    </fo:table-body>
							</fo:table>						
						</fo:block>
								
						<fo:block>
								<xsl:choose>
								<xsl:when test="response/attestation_freeform_02/data/IsAualityReviewAtt = 'Y'">
									<fo:table margin-top="3mm" border="0.0pt solid black">  
										<fo:table-column column-width="5mm"/>
										<fo:table-column column-width="154mm"/>
										<fo:table-column/>
										<fo:table-body>
										    <fo:table-row>
										    	<fo:table-cell><fo:block font-size="9px" font-family="Arial">3.	</fo:block></fo:table-cell>
									        	<fo:table-cell><fo:block font-size="9px" font-family="Arial">Within the past year, has your Firm undergone a peer or quality review offered by the AICPA or any state CPA Society?</fo:block></fo:table-cell>
									        	<fo:table-cell><fo:block font-size="9px" font-family="Arial" text-align="right">Yes <fo:external-graphic src="../AccountantIns/image/check-btn6.png"/>&#160;&#160;&#160;No <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/></fo:block></fo:table-cell>
									        </fo:table-row>
									    </fo:table-body>
									</fo:table>
								</xsl:when>
								<xsl:when test="response/attestation_freeform_02/data/IsAualityReviewAtt = 'N'">
									<fo:table margin-top="3mm" border="0.0pt solid black">  
										<fo:table-column column-width="5mm"/>
										<fo:table-column column-width="154mm"/>
										<fo:table-column/>
										<fo:table-body>
										    <fo:table-row>
										    	<fo:table-cell><fo:block font-size="9px" font-family="Arial">3.	</fo:block></fo:table-cell>
									        	<fo:table-cell><fo:block font-size="9px" font-family="Arial">Within the past year, has your Firm undergone a peer or quality review offered by the AICPA or any state CPA Society?</fo:block></fo:table-cell>
									        	<fo:table-cell><fo:block font-size="9px" font-family="Arial" text-align="right">Yes <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/>&#160;&#160;&#160;No <fo:external-graphic src="../AccountantIns/image/check-btn6.png"/></fo:block></fo:table-cell>
									        </fo:table-row>
									    </fo:table-body>
									</fo:table>
								</xsl:when>
								<xsl:otherwise>
									<fo:table margin-top="3mm" border="0.0pt solid black">  
										<fo:table-column column-width="5mm"/>
										<fo:table-column column-width="154mm"/>
										<fo:table-column/>
										<fo:table-body>
										    <fo:table-row>
										    	<fo:table-cell><fo:block font-size="9px" font-family="Arial">3.	</fo:block></fo:table-cell>
									        	<fo:table-cell><fo:block font-size="9px" font-family="Arial">Within the past year, has your Firm undergone a peer or quality review offered by the AICPA or any state CPA Society?</fo:block></fo:table-cell>
									        	<fo:table-cell><fo:block font-size="9px" font-family="Arial" text-align="right">Yes <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/>&#160;&#160;&#160;No <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/></fo:block></fo:table-cell>
									        </fo:table-row>
									    </fo:table-body>
									</fo:table>
								</xsl:otherwise>
							</xsl:choose>
						</fo:block>	
						
						<fo:block>
								<xsl:choose>
								<xsl:when test="response/attestation_freeform_02/data/IsLastPeerReviewAtt = 'Y'">
									<fo:table margin-top="3mm" border="0.0pt solid black">  
										<fo:table-column column-width="5mm"/>
										<fo:table-column column-width="154mm"/>
										<fo:table-column/>
										<fo:table-body>
										    <fo:table-row>
										    	<fo:table-cell><fo:block font-size="9px" font-family="Arial">4.	</fo:block></fo:table-cell>
									        	<fo:table-cell><fo:block font-size="9px" font-family="Arial">Was the Firm’s last peer review modified, qualified, or adverse?</fo:block></fo:table-cell>
									        	<fo:table-cell><fo:block font-size="9px" font-family="Arial" text-align="right">Yes <fo:external-graphic src="../AccountantIns/image/check-btn6.png"/>&#160;&#160;&#160;No <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/></fo:block></fo:table-cell>
									        </fo:table-row>
									    </fo:table-body>
									</fo:table>
								</xsl:when>
								<xsl:when test="response/attestation_freeform_02/data/IsLastPeerReviewAtt = 'N'">
									<fo:table margin-top="3mm" border="0.0pt solid black">  
										<fo:table-column column-width="5mm"/>
										<fo:table-column column-width="154mm"/>
										<fo:table-column/>
										<fo:table-body>
										    <fo:table-row>
										    	<fo:table-cell><fo:block font-size="9px" font-family="Arial">4.	</fo:block></fo:table-cell>
									        	<fo:table-cell><fo:block font-size="9px" font-family="Arial">Was the Firm’s last peer review modified, qualified, or adverse?</fo:block></fo:table-cell>
									        	<fo:table-cell><fo:block font-size="9px" font-family="Arial" text-align="right">Yes <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/>&#160;&#160;&#160;No <fo:external-graphic src="../AccountantIns/image/check-btn6.png"/></fo:block></fo:table-cell>
									        </fo:table-row>
									    </fo:table-body>
									</fo:table>
								</xsl:when>
								<xsl:otherwise>
									<fo:table margin-top="3mm" border="0.0pt solid black">  
										<fo:table-column column-width="5mm"/>
										<fo:table-column column-width="154mm"/>
										<fo:table-column/>
										<fo:table-body>
										    <fo:table-row>
										    	<fo:table-cell><fo:block font-size="9px" font-family="Arial">4.	</fo:block></fo:table-cell>
									        	<fo:table-cell><fo:block font-size="9px" font-family="Arial">Was the Firm’s last peer review modified, qualified, or adverse?</fo:block></fo:table-cell>
									        	<fo:table-cell><fo:block font-size="9px" font-family="Arial" text-align="right">Yes <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/>&#160;&#160;&#160;No <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/></fo:block></fo:table-cell>
									        </fo:table-row>
									    </fo:table-body>
									</fo:table>
								</xsl:otherwise>
							</xsl:choose>
						</fo:block>		
						
						<fo:block>
						<fo:table margin-top="3mm" border="0.0pt solid black">  
								<fo:table-column column-width="5mm"/>
								<fo:table-column column-width="154mm"/>
								<fo:table-body>
								    <fo:table-row>
								    	<fo:table-cell><fo:block font-size="9px" font-family="Arial">5.	</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial">Within the past year has the Firm: </fo:block></fo:table-cell>
							        	
							        </fo:table-row>
							    </fo:table-body>
						</fo:table>
						<xsl:choose>
							<xsl:when test="response/firm_freeform_01/data/IsSyndicationManagers = 'Y'">
								<fo:table margin-top="3mm" border="0.0pt solid black">  
								<fo:table-column column-width="5mm"/>
								<fo:table-column column-width="154mm"/>
								<fo:table-column/>
								<fo:table-body>
								    <fo:table-row>
								    	<fo:table-cell><fo:block font-size="9px" font-family="Arial" start-indent="1cm">(a).	</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial" start-indent="1cm">provided services to investment funds, hedge funds, mutual funds or syndication managers?</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial" text-align="right">Yes <fo:external-graphic src="../AccountantIns/image/check-btn6.png"/>&#160;&#160;&#160;No <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/></fo:block></fo:table-cell>
							        </fo:table-row>
							    </fo:table-body>
							</fo:table>
							</xsl:when>								
							<xsl:when test="response/firm_freeform_01/data/IsSyndicationManagers = 'N'">
								<fo:table margin-top="3mm" border="0.0pt solid black">  
								<fo:table-column column-width="5mm"/>
								<fo:table-column column-width="154mm"/>
								<fo:table-column/>
								<fo:table-body>
								    <fo:table-row>
								    	<fo:table-cell><fo:block font-size="9px" font-family="Arial" start-indent="1cm">(a).	</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial" start-indent="1cm">provided services to investment funds, hedge funds, mutual funds or syndication managers?</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial" text-align="right">Yes <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/>&#160;&#160;&#160;No <fo:external-graphic src="../AccountantIns/image/check-btn6.png"/></fo:block></fo:table-cell>
							        </fo:table-row>
							    </fo:table-body>
							</fo:table>
							</xsl:when>
							<xsl:otherwise>
								<fo:table margin-top="3mm" border="0.0pt solid black">  
								<fo:table-column column-width="5mm"/>
								<fo:table-column column-width="154mm"/>
								<fo:table-column/>
								<fo:table-body>
								    <fo:table-row>
								    	<fo:table-cell><fo:block font-size="9px" font-family="Arial" start-indent="1cm">(a).	</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial" start-indent="1cm">provided services to investment funds, hedge funds, mutual funds or syndication managers?</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial" text-align="right">Yes <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/>&#160;&#160;&#160;No <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/></fo:block></fo:table-cell>
							        </fo:table-row>
							    </fo:table-body>
							</fo:table>
							</xsl:otherwise>
						 </xsl:choose>
							
						
						
						
						<xsl:choose>
							<xsl:when test="response/firm_freeform_01/data/IsDebtOrEquityFinancing = 'Y'">
								<fo:table margin-top="3mm" border="0.0pt solid black">  
								<fo:table-column column-width="5mm"/>
								<fo:table-column column-width="154mm"/>
								<fo:table-column/>
								<fo:table-body>
								    <fo:table-row>
								    	<fo:table-cell><fo:block font-size="9px" font-family="Arial" start-indent="1cm">(b).	</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial" start-indent="1cm">arranged debt or equity financing, acted as a business broker, underwritten the offering of public or private securities, or prepared fairness opinions?  </fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial" text-align="right">Yes <fo:external-graphic src="../AccountantIns/image/check-btn6.png"/>&#160;&#160;&#160;No <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/></fo:block></fo:table-cell>
							        </fo:table-row>
							    </fo:table-body>
							</fo:table>
							</xsl:when>
							<xsl:when test="response/firm_freeform_01/data/IsDebtOrEquityFinancing = 'N'">
									<fo:table margin-top="3mm" border="0.0pt solid black">  
								<fo:table-column column-width="5mm"/>
								<fo:table-column column-width="154mm"/>
								<fo:table-column/>
								<fo:table-body>
								    <fo:table-row>
								    	<fo:table-cell><fo:block font-size="9px" font-family="Arial" start-indent="1cm">(b).	</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial" start-indent="1cm">arranged debt or equity financing, acted as a business broker, underwritten the offering of public or private securities, or prepared fairness opinions?  </fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial" text-align="right">Yes <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/>&#160;&#160;&#160;No <fo:external-graphic src="../AccountantIns/image/check-btn6.png"/></fo:block></fo:table-cell>
							        </fo:table-row>
							    </fo:table-body>
							</fo:table>
							</xsl:when>
							<xsl:otherwise>
									<fo:table margin-top="3mm" border="0.0pt solid black">  
								<fo:table-column column-width="5mm"/>
								<fo:table-column column-width="154mm"/>
								<fo:table-column/>
								<fo:table-body>
								    <fo:table-row>
								    	<fo:table-cell><fo:block font-size="9px" font-family="Arial" start-indent="1cm">(b).	</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial" start-indent="1cm">arranged debt or equity financing, acted as a business broker, underwritten the offering of public or private securities, or prepared fairness opinions?  </fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial" text-align="right">Yes <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/>&#160;&#160;&#160;No <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/></fo:block></fo:table-cell>
							        </fo:table-row>
							    </fo:table-body>
							</fo:table>
							</xsl:otherwise>
						 </xsl:choose>
						
						
					
					
					
						<xsl:choose>
							<xsl:when test="response/firm_freeform_01/data/IsCommissionRecieved = 'Y'">
								<fo:table margin-top="3mm" border="0.0pt solid black">  
								<fo:table-column column-width="5mm"/>
								<fo:table-column column-width="154mm"/>
								<fo:table-column/>
								<fo:table-body>
								    <fo:table-row>
								    	<fo:table-cell><fo:block font-size="9px" font-family="Arial" start-indent="1cm">(c).	</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial" start-indent="1cm">Performed services or consented to the use of your work product in connection with public or private offerings of securities, real estate or other investments?</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial" text-align="right">Yes <fo:external-graphic src="../AccountantIns/image/check-btn6.png"/>&#160;&#160;&#160;No <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/></fo:block></fo:table-cell>
							        </fo:table-row>
							    </fo:table-body>
							</fo:table>
							</xsl:when>
							<xsl:when test="response/firm_freeform_01/data/IsCommissionRecieved = 'N'">
								<fo:table margin-top="3mm" border="0.0pt solid black">  
								<fo:table-column column-width="5mm"/>
								<fo:table-column column-width="154mm"/>
								<fo:table-column/>
								<fo:table-body>
								    <fo:table-row>
								    	<fo:table-cell><fo:block font-size="9px" font-family="Arial" start-indent="1cm">(c).	</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial" start-indent="1cm">Performed services or consented to the use of your work product in connection with public or private offerings of securities, real estate or other investments?</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial" text-align="right">Yes <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/>&#160;&#160;&#160;No <fo:external-graphic src="../AccountantIns/image/check-btn6.png"/></fo:block></fo:table-cell>
							        </fo:table-row>
							    </fo:table-body>
							</fo:table>
							</xsl:when>
							<xsl:otherwise>
								<fo:table margin-top="3mm" border="0.0pt solid black">  
								<fo:table-column column-width="5mm"/>
								<fo:table-column column-width="154mm"/>
								<fo:table-column/>
								<fo:table-body>
								    <fo:table-row>
								    	<fo:table-cell><fo:block font-size="9px" font-family="Arial" start-indent="1cm">(c).	</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial" start-indent="1cm">Performed services or consented to the use of your work product in connection with public or private offerings of securities, real estate or other investments?</fo:block></fo:table-cell>
							        	<fo:table-cell><fo:block font-size="9px" font-family="Arial" text-align="right">Yes <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/>&#160;&#160;&#160;No <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/></fo:block></fo:table-cell>
							        </fo:table-row>
							    </fo:table-body>
							</fo:table>
							</xsl:otherwise>
						 </xsl:choose>
													
						</fo:block>	
						
					</xsl:if>
					
			
						
						
						
						
				
				 <!-- Claim -->
				
						
				<xsl:choose>				
				<xsl:when test="response/firm_freeform_01/data/IsAwarenessOfAnyProfLiability = 'Y'">
				
								<fo:block break-after="page"></fo:block>
										<fo:block margin-top="10mm">
											<fo:table margin-top="3mm" border="0.5pt solid black" background-color="grey">
												<fo:table-column></fo:table-column>
												<fo:table-body>
													<fo:table-row>
														<fo:table-cell>
															<fo:block margin-top="1mm" font-family="Arial" font-weight="bold" font-size="9px">&#160;&#160;CLAIM SUPPLEMENT <fo:inline font-style="italics">(As required in  question 10 b or 10 c)</fo:inline></fo:block>
														</fo:table-cell>
													</fo:table-row>
													<fo:table-row>
														<fo:table-cell>
															<fo:block margin-top="1mm"></fo:block>
														</fo:table-cell>
													</fo:table-row>
												</fo:table-body>
											</fo:table>
										</fo:block>	
								
										<fo:block>
											<fo:table margin-top="3mm">
											<fo:table-column></fo:table-column>
											<fo:table-body>
												<xsl:for-each select="response/BasicInfo_list_04/data">
													<fo:table-row>
														<fo:table-cell>
															<fo:block>
																<fo:table margin-top="1mm">
																	<fo:table-column column-width="5mm"/>
																	<fo:table-column column-width="145mm"/>
																	<fo:table-column/>
																	<fo:table-body>
																		<fo:table-row>
																			<fo:table-cell><fo:block font-family="Arial" font-size="9px">1.</fo:block></fo:table-cell>
																			<fo:table-cell><fo:block font-family="Arial" font-size="9px">Full name of claimant or potential claimant:&#160;<xsl:value-of select="NameOfClaimantCi"></xsl:value-of></fo:block></fo:table-cell>
																			<fo:table-cell>
																				<xsl:choose>
																					<xsl:when test="IsClientCi='Y'">
																						<fo:block font-family="Arial" font-size="9px"><fo:external-graphic src="../AccountantIns/image/check-btn6.png"/>&#160;Client&#160;&#160;&#160;<fo:external-graphic src="../AccountantIns/image/check-btn4.png"/>&#160;Non-Client</fo:block>
																					</xsl:when>
																					<xsl:when test="IsClientCi='N'">
																						<fo:block font-family="Arial" font-size="9px"><fo:external-graphic src="../AccountantIns/image/check-btn4.png"/>&#160;Client&#160;&#160;&#160;<fo:external-graphic src="../AccountantIns/image/check-btn6.png"/>&#160;Non-Client</fo:block>
																					</xsl:when>
																					<xsl:otherwise>
																						<fo:block font-family="Arial" font-size="9px"><fo:external-graphic src="../AccountantIns/image/check-btn4.png"/>&#160;Client&#160;&#160;&#160;<fo:external-graphic src="../AccountantIns/image/check-btn4.png"/>&#160;Non-Client</fo:block>
																					</xsl:otherwise>
																				</xsl:choose>
																			</fo:table-cell>	
																		</fo:table-row>
																	</fo:table-body>
																</fo:table>																
																<fo:table margin-top="3mm">
																	<fo:table-column column-width="5mm"></fo:table-column>
																	<fo:table-column column-width="110mm"/>
																	<fo:table-column/>
																	<fo:table-body>
																		<fo:table-row>
																			<fo:table-cell><fo:block font-family="Arial" font-size="9px">2.</fo:block></fo:table-cell>
																			<fo:table-cell>
																				<fo:block font-family="Arial" font-size="9px">2.	Date Firm was notified of claim: &#160;<xsl:value-of select="ClaimNotifiedCiDate"></xsl:value-of></fo:block>
																			</fo:table-cell>
																			<fo:table-cell>
																				<fo:block font-family="Arial" font-size="9px">Date of alleged error:&#160;<xsl:value-of select="AllegedErrorCiDate"></xsl:value-of></fo:block>
																			</fo:table-cell>	
																		</fo:table-row>	
																		<fo:table-row>
																			<fo:table-cell><fo:block font-family="Arial" font-size="9px"></fo:block></fo:table-cell>
																			<fo:table-cell>
																				<fo:block font-family="Arial" font-size="5px"  font-style="italic" text-indent="7.2cm"> MM / DD / YYYY</fo:block>
																			</fo:table-cell>
																			<fo:table-cell>
																				<fo:block font-family="Arial" font-size="5px" text-indent="3cm" font-style="italic"> MM / DD / YYYY</fo:block>
																			</fo:table-cell>	
																		</fo:table-row>
																	</fo:table-body>
																</fo:table>
																<fo:table margin-top="3mm">
																	<fo:table-column column-width="5mm"></fo:table-column>
																	<fo:table-column/>
																	<fo:table-body>
																		<fo:table-row>
																			<fo:table-cell><fo:block font-family="Arial" font-size="9px">3.</fo:block></fo:table-cell>
																			<fo:table-cell>
																				<fo:block font-family="Arial" font-size="9px">Names of Firm personnel involved in the claim or potential claim: &#160;<xsl:value-of select="NameOfFirmPersonnelCi"/></fo:block>
																			</fo:table-cell>
																		</fo:table-row>
																	</fo:table-body>
																</fo:table>
																<fo:table margin-top="3mm">
																	<fo:table-column column-width="5mm"></fo:table-column>
																	<fo:table-column/>
																	<fo:table-body>
																		<fo:table-row>
																			<fo:table-cell><fo:block font-family="Arial" font-size="9px">4.</fo:block></fo:table-cell>
																			<fo:table-cell>
																				<fo:block font-family="Arial" font-size="9px">Description of claim or potential claim:&#160;<xsl:value-of select="DescOfClaimCi"/></fo:block>
																			</fo:table-cell>
																		</fo:table-row>
																	</fo:table-body>
																</fo:table>
																<xsl:choose>
																	<xsl:when test="IsThisClaimReportedCi='Y'">
																		<fo:table margin-top="3mm">
																			<fo:table-column column-width="5mm"></fo:table-column>
																			<fo:table-column column-width="154mm"/>
																			<fo:table-column/>
																			<fo:table-body>
																				<fo:table-row>
																					<fo:table-cell><fo:block font-family="Arial" font-size="9px">5.</fo:block></fo:table-cell>
																					<fo:table-cell>
																						<fo:block font-family="Arial" font-size="9px">Has this claim or potential claim been reported to your insurance company?</fo:block>
																					</fo:table-cell>
																					<fo:table-cell><fo:block font-family="Arial" font-size="9px" text-align="right">Yes <fo:external-graphic src="../AccountantIns/image/check-btn6.png"/>&#160;&#160;&#160;No <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/></fo:block></fo:table-cell>
																				</fo:table-row>
																			</fo:table-body>
																		</fo:table>
																	</xsl:when>
																	<xsl:when test="IsThisClaimReportedCi='N'">
																		<fo:table margin-top="3mm">
																			<fo:table-column column-width="5mm"></fo:table-column>
																			<fo:table-column column-width="154mm"/>
																			<fo:table-column/>
																			<fo:table-body>
																				<fo:table-row>
																					<fo:table-cell><fo:block font-family="Arial" font-size="9px">5.</fo:block></fo:table-cell>
																					<fo:table-cell>
																						<fo:block font-family="Arial" font-size="9px">Has this claim or potential claim been reported to your insurance company?</fo:block>
																					</fo:table-cell>
																					<fo:table-cell><fo:block font-family="Arial" font-size="9px" text-align="right">Yes <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/>&#160;&#160;&#160;No <fo:external-graphic src="../AccountantIns/image/check-btn6.png"/></fo:block></fo:table-cell>
																				</fo:table-row>
																			</fo:table-body>
																		</fo:table>
																	</xsl:when>
																	<xsl:otherwise>
																		<fo:table margin-top="3mm">
																			<fo:table-column column-width="5mm"></fo:table-column>
																			<fo:table-column column-width="153mm"/>
																			<fo:table-column/>
																			<fo:table-body>
																				<fo:table-row>
																					<fo:table-cell><fo:block font-family="Arial" font-size="9px">5.</fo:block></fo:table-cell>
																					<fo:table-cell>
																						<fo:block font-family="Arial" font-size="9px">Has this claim or potential claim been reported to your insurance company?</fo:block>
																					</fo:table-cell>
																					<fo:table-cell><fo:block font-family="Arial" font-size="9px" text-align="right">Yes <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/>&#160;&#160;&#160;No <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/></fo:block></fo:table-cell>
																				</fo:table-row>
																			</fo:table-body>
																		</fo:table>
																	</xsl:otherwise>
																</xsl:choose>
																<fo:table margin-top="3mm">
																	<fo:table-column column-width="5mm"></fo:table-column>
																	<fo:table-column column-width="110mm"/>
																	<fo:table-column/>
																	<fo:table-body>
																		<fo:table-row>
																			<fo:table-cell><fo:block font-family="Arial" font-size="9px">6.</fo:block></fo:table-cell>
																			<fo:table-cell>
																				<fo:block font-family="Arial" font-size="9px">Name of insurance company:&#160;<xsl:value-of select="NameOfInsuranceCompanyCi"></xsl:value-of></fo:block>
																			</fo:table-cell>
																			<fo:table-cell>
																				<fo:block font-family="Arial" font-size="9px">Date reported to insurance company:&#160;<xsl:value-of select="ReportedCiDate"></xsl:value-of></fo:block>
																			</fo:table-cell>	
																		</fo:table-row>
																		
																		<fo:table-row>
																			<fo:table-cell><fo:block font-family="Arial" font-size="9px"></fo:block></fo:table-cell>
																			<fo:table-cell>
																				<fo:block font-family="Arial" font-size="9px"></fo:block>
																			</fo:table-cell>
																			<fo:table-cell>
																				<fo:block font-family="Arial" font-size="5px" text-indent="5cm" font-style="italic"> MM / DD / YYYY</fo:block>
																			</fo:table-cell>	
																		</fo:table-row>
																	</fo:table-body>
																</fo:table>
																<fo:table margin-top="3mm">
																	<fo:table-column column-width="5mm"></fo:table-column>
																	<fo:table-column/>
																	<fo:table-body>
																		<fo:table-row>
																			<fo:table-cell><fo:block font-family="Arial" font-size="9px">7.</fo:block></fo:table-cell>
																			<fo:table-cell>
																				<fo:block font-family="Arial" font-size="9px">If claim is now open, provide: Insurer's loss
																																	reserve($):&#160;<xsl:value-of select="InsurerLossReserveCi"/>
																				</fo:block>
																			</fo:table-cell>
																		</fo:table-row>
																	</fo:table-body>
																</fo:table>
																<fo:table margin-top="3mm">
																	<fo:table-column column-width="5mm"></fo:table-column>
																	<fo:table-column column-width="65mm"/>
																	<fo:table-column/>
																	<fo:table-body>
																		<fo:table-row>
																			<fo:table-cell><fo:block font-family="Arial" font-size="9px"></fo:block></fo:table-cell>
																			<fo:table-cell>
																				<fo:block font-family="Arial" font-size="9px">Claimant's last demand($):&#160;<xsl:value-of select="ClaimantLastDemandCi"></xsl:value-of></fo:block>
																			</fo:table-cell>
																			<fo:table-cell>
																				<fo:block font-family="Arial" font-size="9px">Current Status:&#160;<xsl:value-of select="CurrentStatus"></xsl:value-of></fo:block>
																			</fo:table-cell>	
																		</fo:table-row>
																	</fo:table-body>
																</fo:table>
																<fo:table margin-top="3mm">
																	<fo:table-column column-width="5mm"></fo:table-column>
																	<fo:table-column column-width="60mm"/>
																	<fo:table-column/>
																	<fo:table-body>
																		<fo:table-row>
																			<fo:table-cell><fo:block font-family="Arial" font-size="9px">8.</fo:block></fo:table-cell>
																			<fo:table-cell>
																				<fo:block font-family="Arial" font-size="9px">If closed, provide: date closed:&#160;<xsl:value-of select="ClosingCiDate"></xsl:value-of></fo:block>
																			</fo:table-cell>
																			<fo:table-cell>
																				<fo:block font-family="Arial" font-size="9px" text-align="right">Total amount paid (including damages and defense
																																	expenses):&#160;<xsl:value-of select="TotalClaimExpensesCi"></xsl:value-of></fo:block>
																			</fo:table-cell>	
																		</fo:table-row>
																		
																		<fo:table-row>
																			<fo:table-cell><fo:block font-family="Arial" font-size="9px"></fo:block></fo:table-cell>
																			<fo:table-cell>
																				<fo:block font-family="Arial" font-size="5px" text-indent="4cm" font-style="italic"> MM / DD / YYYY</fo:block>
																			</fo:table-cell>
																			<fo:table-cell>
																				<fo:block font-family="Arial" font-size="9px" text-align="right"></fo:block>
																			</fo:table-cell>	
																		</fo:table-row>
																		
																		
																		
																	</fo:table-body>
																</fo:table>
																<fo:table margin-top="3mm">
																	<fo:table-column column-width="5mm"></fo:table-column>
																	<fo:table-column/>
																	<fo:table-body>
																		<fo:table-row>
																			<fo:table-cell><fo:block font-family="Arial" font-size="9px">9.</fo:block></fo:table-cell>
																			<fo:table-cell>
																				<fo:block font-family="Arial" font-size="9px">What steps have been taken within your Firm to prevent similar claims? &#160;<xsl:value-of select="StepsTakenToPreventClaimCi"/></fo:block>
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
									</fo:block>
				
				</xsl:when>
				
				<xsl:when test="response/firm_freeform_01/data/IsAwarenessOfAnyProfLiabilitySuitAgainst = 'Y'">
				
				<fo:block break-after="page"></fo:block>
										<fo:block margin-top="10mm">
											<fo:table margin-top="3mm" border="0.5pt solid black" background-color="grey">
												<fo:table-column></fo:table-column>
												<fo:table-body>
													<fo:table-row>
														<fo:table-cell>
															<fo:block margin-top="1mm" font-family="Arial" font-weight="bold" font-size="9px">&#160;&#160;CLAIM SUPPLEMENT <fo:inline font-style="italics">(As required in  question 10 b or 10 c)</fo:inline></fo:block>
														</fo:table-cell>
													</fo:table-row>
													<fo:table-row>
														<fo:table-cell>
															<fo:block margin-top="1mm"></fo:block>
														</fo:table-cell>
													</fo:table-row>
												</fo:table-body>
											</fo:table>
										</fo:block>	
								
										<fo:block>
											<fo:table margin-top="3mm">
											<fo:table-column></fo:table-column>
											<fo:table-body>
												<xsl:for-each select="response/BasicInfo_list_04/data">
													<fo:table-row>
														<fo:table-cell>
															<fo:block>
																<fo:table margin-top="1mm">
																	<fo:table-column column-width="5mm"/>
																	<fo:table-column column-width="145mm"/>
																	<fo:table-column/>
																	<fo:table-body>
																		<fo:table-row>
																			<fo:table-cell><fo:block font-family="Arial" font-size="9px">1.</fo:block></fo:table-cell>
																			<fo:table-cell><fo:block font-family="Arial" font-size="9px">Full name of claimant or potential claimant:&#160;<xsl:value-of select="NameOfClaimantCi"></xsl:value-of></fo:block></fo:table-cell>
																			<fo:table-cell>
																				<xsl:choose>
																					<xsl:when test="IsClientCi='Y'">
																						<fo:block font-family="Arial" font-size="9px"><fo:external-graphic src="../AccountantIns/image/check-btn6.png"/>&#160;Client&#160;&#160;&#160;<fo:external-graphic src="../AccountantIns/image/check-btn4.png"/>&#160;Non-Client</fo:block>
																					</xsl:when>
																					<xsl:when test="IsClientCi='N'">
																						<fo:block font-family="Arial" font-size="9px"><fo:external-graphic src="../AccountantIns/image/check-btn4.png"/>&#160;Client&#160;&#160;&#160;<fo:external-graphic src="../AccountantIns/image/check-btn6.png"/>&#160;Non-Client</fo:block>
																					</xsl:when>
																					<xsl:otherwise>
																						<fo:block font-family="Arial" font-size="9px"><fo:external-graphic src="../AccountantIns/image/check-btn4.png"/>&#160;Client&#160;&#160;&#160;<fo:external-graphic src="../AccountantIns/image/check-btn4.png"/>&#160;Non-Client</fo:block>
																					</xsl:otherwise>
																				</xsl:choose>
																			</fo:table-cell>	
																		</fo:table-row>
																	</fo:table-body>
																</fo:table>																
																<fo:table margin-top="3mm">
																	<fo:table-column column-width="5mm"></fo:table-column>
																	<fo:table-column column-width="110mm"/>
																	<fo:table-column/>
																	<fo:table-body>
																		<fo:table-row>
																			<fo:table-cell><fo:block font-family="Arial" font-size="9px">2.</fo:block></fo:table-cell>
																			<fo:table-cell>
																				<fo:block font-family="Arial" font-size="9px">2.	Date Firm was notified of claim: &#160;<xsl:value-of select="ClaimNotifiedCiDate"></xsl:value-of></fo:block>
																			</fo:table-cell>
																			<fo:table-cell>
																				<fo:block font-family="Arial" font-size="9px">Date of alleged error:&#160;<xsl:value-of select="AllegedErrorCiDate"></xsl:value-of></fo:block>
																			</fo:table-cell>	
																		</fo:table-row>	
																		<fo:table-row>
																			<fo:table-cell><fo:block font-family="Arial" font-size="9px"></fo:block></fo:table-cell>
																			<fo:table-cell>
																				<fo:block font-family="Arial" font-size="5px"  font-style="italic" text-indent="7.2cm"> MM / DD / YYYY</fo:block>
																			</fo:table-cell>
																			<fo:table-cell>
																				<fo:block font-family="Arial" font-size="5px" text-indent="3cm" font-style="italic"> MM / DD / YYYY</fo:block>
																			</fo:table-cell>	
																		</fo:table-row>
																	</fo:table-body>
																</fo:table>
																<fo:table margin-top="3mm">
																	<fo:table-column column-width="5mm"></fo:table-column>
																	<fo:table-column/>
																	<fo:table-body>
																		<fo:table-row>
																			<fo:table-cell><fo:block font-family="Arial" font-size="9px">3.</fo:block></fo:table-cell>
																			<fo:table-cell>
																				<fo:block font-family="Arial" font-size="9px">Names of Firm personnel involved in the claim or potential claim: &#160;<xsl:value-of select="NameOfFirmPersonnelCi"/></fo:block>
																			</fo:table-cell>
																		</fo:table-row>
																	</fo:table-body>
																</fo:table>
																<fo:table margin-top="3mm">
																	<fo:table-column column-width="5mm"></fo:table-column>
																	<fo:table-column/>
																	<fo:table-body>
																		<fo:table-row>
																			<fo:table-cell><fo:block font-family="Arial" font-size="9px">4.</fo:block></fo:table-cell>
																			<fo:table-cell>
																				<fo:block font-family="Arial" font-size="9px">Description of claim or potential claim:&#160;<xsl:value-of select="DescOfClaimCi"/></fo:block>
																			</fo:table-cell>
																		</fo:table-row>
																	</fo:table-body>
																</fo:table>
																<xsl:choose>
																	<xsl:when test="IsThisClaimReportedCi='Y'">
																		<fo:table margin-top="3mm">
																			<fo:table-column column-width="5mm"></fo:table-column>
																			<fo:table-column column-width="154mm"/>
																			<fo:table-column/>
																			<fo:table-body>
																				<fo:table-row>
																					<fo:table-cell><fo:block font-family="Arial" font-size="9px">5.</fo:block></fo:table-cell>
																					<fo:table-cell>
																						<fo:block font-family="Arial" font-size="9px">Has this claim or potential claim been reported to your insurance company?</fo:block>
																					</fo:table-cell>
																					<fo:table-cell><fo:block font-family="Arial" font-size="9px" text-align="right">Yes <fo:external-graphic src="../AccountantIns/image/check-btn6.png"/>&#160;&#160;&#160;No <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/></fo:block></fo:table-cell>
																				</fo:table-row>
																			</fo:table-body>
																		</fo:table>
																	</xsl:when>
																	<xsl:when test="IsThisClaimReportedCi='N'">
																		<fo:table margin-top="3mm">
																			<fo:table-column column-width="5mm"></fo:table-column>
																			<fo:table-column column-width="154mm"/>
																			<fo:table-column/>
																			<fo:table-body>
																				<fo:table-row>
																					<fo:table-cell><fo:block font-family="Arial" font-size="9px">5.</fo:block></fo:table-cell>
																					<fo:table-cell>
																						<fo:block font-family="Arial" font-size="9px">Has this claim or potential claim been reported to your insurance company?</fo:block>
																					</fo:table-cell>
																					<fo:table-cell><fo:block font-family="Arial" font-size="9px" text-align="right">Yes <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/>&#160;&#160;&#160;No <fo:external-graphic src="../AccountantIns/image/check-btn6.png"/></fo:block></fo:table-cell>
																				</fo:table-row>
																			</fo:table-body>
																		</fo:table>
																	</xsl:when>
																	<xsl:otherwise>
																		<fo:table margin-top="3mm">
																			<fo:table-column column-width="5mm"></fo:table-column>
																			<fo:table-column column-width="153mm"/>
																			<fo:table-column/>
																			<fo:table-body>
																				<fo:table-row>
																					<fo:table-cell><fo:block font-family="Arial" font-size="9px">5.</fo:block></fo:table-cell>
																					<fo:table-cell>
																						<fo:block font-family="Arial" font-size="9px">Has this claim or potential claim been reported to your insurance company?</fo:block>
																					</fo:table-cell>
																					<fo:table-cell><fo:block font-family="Arial" font-size="9px" text-align="right">Yes <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/>&#160;&#160;&#160;No <fo:external-graphic src="../AccountantIns/image/check-btn4.png"/></fo:block></fo:table-cell>
																				</fo:table-row>
																			</fo:table-body>
																		</fo:table>
																	</xsl:otherwise>
																</xsl:choose>
																<fo:table margin-top="3mm">
																	<fo:table-column column-width="5mm"></fo:table-column>
																	<fo:table-column column-width="110mm"/>
																	<fo:table-column/>
																	<fo:table-body>
																		<fo:table-row>
																			<fo:table-cell><fo:block font-family="Arial" font-size="9px">6.</fo:block></fo:table-cell>
																			<fo:table-cell>
																				<fo:block font-family="Arial" font-size="9px">Name of insurance company:&#160;<xsl:value-of select="NameOfInsuranceCompanyCi"></xsl:value-of></fo:block>
																			</fo:table-cell>
																			<fo:table-cell>
																				<fo:block font-family="Arial" font-size="9px">Date reported to insurance company:&#160;<xsl:value-of select="ReportedCiDate"></xsl:value-of></fo:block>
																			</fo:table-cell>	
																		</fo:table-row>
																		
																		<fo:table-row>
																			<fo:table-cell><fo:block font-family="Arial" font-size="9px"></fo:block></fo:table-cell>
																			<fo:table-cell>
																				<fo:block font-family="Arial" font-size="9px"></fo:block>
																			</fo:table-cell>
																			<fo:table-cell>
																				<fo:block font-family="Arial" font-size="5px" text-indent="5cm" font-style="italic"> MM / DD / YYYY</fo:block>
																			</fo:table-cell>	
																		</fo:table-row>
																	</fo:table-body>
																</fo:table>
																<fo:table margin-top="3mm">
																	<fo:table-column column-width="5mm"></fo:table-column>
																	<fo:table-column/>
																	<fo:table-body>
																		<fo:table-row>
																			<fo:table-cell><fo:block font-family="Arial" font-size="9px">7.</fo:block></fo:table-cell>
																			<fo:table-cell>
																				<fo:block font-family="Arial" font-size="9px">If claim is now open, provide: Insurer's loss
																																	reserve($):&#160;<xsl:value-of select="InsurerLossReserveCi"/>
																				</fo:block>
																			</fo:table-cell>
																		</fo:table-row>
																	</fo:table-body>
																</fo:table>
																<fo:table margin-top="3mm">
																	<fo:table-column column-width="5mm"></fo:table-column>
																	<fo:table-column column-width="65mm"/>
																	<fo:table-column/>
																	<fo:table-body>
																		<fo:table-row>
																			<fo:table-cell><fo:block font-family="Arial" font-size="9px"></fo:block></fo:table-cell>
																			<fo:table-cell>
																				<fo:block font-family="Arial" font-size="9px">Claimant's last demand($):&#160;<xsl:value-of select="ClaimantLastDemandCi"></xsl:value-of></fo:block>
																			</fo:table-cell>
																			<fo:table-cell>
																				<fo:block font-family="Arial" font-size="9px">Current Status:&#160;<xsl:value-of select="CurrentStatus"></xsl:value-of></fo:block>
																			</fo:table-cell>	
																		</fo:table-row>
																	</fo:table-body>
																</fo:table>
																<fo:table margin-top="3mm">
																	<fo:table-column column-width="5mm"></fo:table-column>
																	<fo:table-column column-width="60mm"/>
																	<fo:table-column/>
																	<fo:table-body>
																		<fo:table-row>
																			<fo:table-cell><fo:block font-family="Arial" font-size="9px">8.</fo:block></fo:table-cell>
																			<fo:table-cell>
																				<fo:block font-family="Arial" font-size="9px">If closed, provide: date closed:&#160;<xsl:value-of select="ClosingCiDate"></xsl:value-of></fo:block>
																			</fo:table-cell>
																			<fo:table-cell>
																				<fo:block font-family="Arial" font-size="9px" text-align="right">Total amount paid (including damages and defense
																																	expenses):&#160;<xsl:value-of select="TotalClaimExpensesCi"></xsl:value-of></fo:block>
																			</fo:table-cell>	
																		</fo:table-row>
																		
																		<fo:table-row>
																			<fo:table-cell><fo:block font-family="Arial" font-size="9px"></fo:block></fo:table-cell>
																			<fo:table-cell>
																				<fo:block font-family="Arial" font-size="5px" text-indent="4cm" font-style="italic"> MM / DD / YYYY</fo:block>
																			</fo:table-cell>
																			<fo:table-cell>
																				<fo:block font-family="Arial" font-size="9px" text-align="right"></fo:block>
																			</fo:table-cell>	
																		</fo:table-row>
																		
																		
																		
																	</fo:table-body>
																</fo:table>
																<fo:table margin-top="3mm">
																	<fo:table-column column-width="5mm"></fo:table-column>
																	<fo:table-column/>
																	<fo:table-body>
																		<fo:table-row>
																			<fo:table-cell><fo:block font-family="Arial" font-size="9px">9.</fo:block></fo:table-cell>
																			<fo:table-cell>
																				<fo:block font-family="Arial" font-size="9px">What steps have been taken within your Firm to prevent similar claims? &#160;<xsl:value-of select="StepsTakenToPreventClaimCi"/></fo:block>
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
									</fo:block>
				
				
				</xsl:when>
				</xsl:choose>		
						
				
					
						
				
				
					<fo:block id="last-page" font-size="11px"/>
											
				</fo:flow>
			</fo:page-sequence>

		</fo:root>


	</xsl:template>

</xsl:stylesheet>
