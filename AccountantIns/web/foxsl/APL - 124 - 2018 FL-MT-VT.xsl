<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet version="1.1"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:fo="http://www.w3.org/1999/XSL/Format"
	exclude-result-prefixes="fo"
	xmlns:fn="http://www.w3.org/2005/02/xpath-functions"
	xmlns:java="java">
	
	<xsl:template match="/" name="SecurityLetter20190501">
		
				  
				    <fo:block text-align="left" margin-left="1in"
			margin-right="1.3in" font-size="11px" margin-top="1cm"
			font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;"
		  space-after="1cm" color="grey"><fo:inline font-weight="bold">THIS ENDORSEMENT CHANGES THE POLICY, READ IT CAREFULLY.</fo:inline>
		</fo:block>
		<fo:block  margin-left="0.3in" padding-left="5pt" margin-right="0.3in"
			font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;"
			margin-top="0.3in" font-size="10px" text-align="justify">
				   
				    <fo:block space-after="4mm">
					<fo:table  text-align="left">
					    <fo:table-column column-width = "90mm" />
				        <fo:table-column column-width = "90mm" />
				       
				        <fo:table-body>
					    		  <fo:table-row>
					    		         <fo:table-cell start-indent="1mm" padding-left="4pt"><fo:block font-weight="bold" font-size="10px"  text-align="left">To be attached to and form part of Policy No:<xsl:value-of select="response/policy_freeform_01/data/PolicyNumber" /></fo:block></fo:table-cell>
					                    <fo:table-cell  start-indent="1mm" padding-left="4pt"><fo:block font-weight="bold" font-size="10px"  text-align="left">Effective Date of Endorsement:<xsl:value-of select="response/policy_freeform_01/data/PolicyEffectiveDate" /></fo:block></fo:table-cell>
					    		  </fo:table-row>
					    		 
					    		  <fo:table-row>
					               		<fo:table-cell start-indent="1mm" padding-left="4pt" padding-top="4pt"><fo:block font-weight="bold" font-size="10px"  text-align="left">Issued to: <xsl:value-of select="response/policy_freeform_01/data/AccountName" /></fo:block></fo:table-cell>
					               		<fo:table-cell start-indent="1mm" padding-left="4pt"  padding-top="4pt">
					               			 <fo:block font-weight="bold" font-size="10px"  text-align="left">Endorsement No: <xsl:value-of select="response/endorsements_freeform_01/data/APL1242011" /></fo:block>
					               		</fo:table-cell>
					    		  </fo:table-row>
					    		 
					     </fo:table-body>
				     </fo:table>	   
				</fo:block>
				    <fo:block space-after=".1in" text-align-last="justify">
			    <fo:leader rule-thickness="2pt" leader-pattern="rule" />
			   </fo:block>
				      <fo:block margin-top="12mm"/>
				     <fo:block start-indent="1.5cm" text-align="left" font-weight="bold" font-size="10px">SECURITY INCIDENT AND IDENTIFICATION THEFT EXTENSION ENDORSEMENT – FLORIDA, MONTANA, VERMONT</fo:block>
				     <fo:block margin-top="8mm"/>
				    
				     <fo:block space-after="4mm" text-align="left"  font-size="10px" >In consideration of the premium charged, it is agreed that:</fo:block>
				     
				     	<fo:block>
							<fo:list-block>
								<fo:list-item space-after="4mm">
									<fo:list-item-label>
										<fo:block>
											<fo:inline>1.</fo:inline>
										</fo:block>
									</fo:list-item-label>
									<fo:list-item-body>
										<fo:block space-after="3mm" start-indent="2cm">
											Section III., Extensions, is amended by addition of the following:
										</fo:block>
										
										<fo:block>
										<fo:list-block>
										<fo:list-item>
										<fo:list-item-label>
										<fo:block start-indent="2cm">•</fo:block>
										</fo:list-item-label>
										<fo:list-item-body>
										<fo:block space-after="4mm" font-weight="bold" start-indent="2.5cm">Security Incident Extension</fo:block>
										<fo:block start-indent="2.5cm">For each <fo:inline font-weight="bold">Security Incident</fo:inline>: the <fo:inline font-weight="bold">Insurer</fo:inline> will reimburse the <fo:inline font-weight="bold">Named Insured</fo:inline> for expenses incurred to:</fo:block>
										
										<fo:block>
										<fo:list-block>
										<fo:list-item>
										<fo:list-item-label>
										<fo:block start-indent="2.5cm">1.</fo:block>
										</fo:list-item-label>
										<fo:list-item-body>
										<fo:block space-after="4mm"  start-indent="3cm">hire cyber forensic analysts to determine the extent of an actual security breach that has occurred; or</fo:block>
										</fo:list-item-body>
										
										</fo:list-item>
										</fo:list-block>
										
										</fo:block>
										<fo:block>
										<fo:list-block>
										<fo:list-item>
										<fo:list-item-label>
										<fo:block start-indent="2.5cm">2.</fo:block>
										</fo:list-item-label>
										<fo:list-item-body>
										<fo:block   start-indent="3cm">comply with state or local privacy laws; provided, however, that such laws require that:</fo:block>
										
										<fo:block>
										<fo:list-block>
										<fo:list-item>
										<fo:list-item-label>
										<fo:block start-indent="3cm">a.</fo:block>
										</fo:list-item-label>
										<fo:list-item-body>
										<fo:block space-after="4mm"  start-indent="3.5cm">notification; and</fo:block>
										</fo:list-item-body>
										
										</fo:list-item>
										</fo:list-block>
										
										</fo:block>
										
										
										<fo:block>
										<fo:list-block>
										<fo:list-item>
										<fo:list-item-label>
										<fo:block start-indent="3cm">b.</fo:block>
										</fo:list-item-label>
										<fo:list-item-body>
										<fo:block start-indent="3.5cm">credit monitoring services;</fo:block>
										
										<fo:block start-indent="3cm">are to be provided to individuals when the: (1) security; (2) confidentiality; or (3) integrity; of their personal information has been compromised;</fo:block>
										<fo:block space-after="4mm" start-indent="2.5cm">up to a maximum reimbursable amount of $25,000.</fo:block>
										
										<fo:block start-indent="2.5cm">This is the maximum amount the <fo:inline font-weight="bold">Insurer</fo:inline> will reimburse regardless of:</fo:block>
										
										<fo:block>
										<fo:list-block>
										<fo:list-item>
										<fo:list-item-label>
										<fo:block start-indent="2.5cm">1.</fo:block>
										</fo:list-item-label>
										<fo:list-item-body>
										<fo:block   start-indent="3cm">the number of such <fo:inline font-weight="bold">Security Incidents</fo:inline>; or</fo:block>
										<fo:block>
										<fo:list-block>
										<fo:list-item>
										<fo:list-item-label>
										<fo:block start-indent="2.5cm">2.</fo:block>
										</fo:list-item-label>
										<fo:list-item-body>
										<fo:block   start-indent="3cm">the number of Insureds involved in such <fo:inline font-weight="bold">Security Incidents</fo:inline>;</fo:block>
										<fo:block   start-indent="2.5cm">for all <fo:inline font-weight="bold">Security Incidents</fo:inline> reported to the <fo:inline font-weight="bold">Insurer</fo:inline>, during:</fo:block>
										<fo:block>
										<fo:list-block>
										<fo:list-item>
										<fo:list-item-label>
										<fo:block start-indent="3cm">a.</fo:block>
										</fo:list-item-label>
										<fo:list-item-body>
										<fo:block space-after="4mm"  start-indent="3.5cm">the <fo:inline font-weight="bold">Policy Period</fo:inline>; or</fo:block>
										</fo:list-item-body>
										</fo:list-item>
										</fo:list-block>
										</fo:block>
										
										<fo:block>
										<fo:list-block>
										<fo:list-item>
										<fo:list-item-label>
										<fo:block start-indent="3cm">b.</fo:block>
										</fo:list-item-label>
										<fo:list-item-body>
										<fo:block space-after="4mm"  start-indent="3.5cm">any applicable Extended Reporting Period.</fo:block>
										</fo:list-item-body>
										</fo:list-item>
										</fo:list-block>
										</fo:block>
										
										</fo:list-item-body></fo:list-item></fo:list-block></fo:block>
										</fo:list-item-body></fo:list-item>
										</fo:list-block></fo:block></fo:list-item-body></fo:list-item>
										</fo:list-block></fo:block></fo:list-item-body></fo:list-item>
										</fo:list-block></fo:block></fo:list-item-body></fo:list-item>
										</fo:list-block></fo:block>
									</fo:list-item-body>
								</fo:list-item>
							</fo:list-block>
						</fo:block>
						
						
						<fo:block space-after="1cm">
							<fo:list-block>
								<fo:list-item space-after="4mm">
									<fo:list-item-label>
										<fo:block>
											<fo:inline>2.</fo:inline>
										</fo:block>
									</fo:list-item-label>
									<fo:list-item-body>
										<fo:block space-after="3mm" start-indent="2cm">
											Section IV., Definitions, is amended by addition of the following:
										</fo:block>
										
										<fo:block>
										<fo:list-block>
										<fo:list-item>
										<fo:list-item-label>
										<fo:block start-indent="2cm">•</fo:block>
										</fo:list-item-label>
										<fo:list-item-body>
										<fo:block space-after="4mm"  start-indent="2.5cm"><fo:inline font-weight="bold">Security Incident</fo:inline> means the:</fo:block>
										
										<fo:block>
										<fo:list-block>
										<fo:list-item>
										<fo:list-item-label>
										<fo:block start-indent="2.5cm">1.</fo:block>
										</fo:list-item-label>
										<fo:list-item-body>
										<fo:block space-after="4mm"  start-indent="3cm">unauthorized access to;</fo:block>
										</fo:list-item-body>
										
										</fo:list-item>
										</fo:list-block>
										
										</fo:block>
										<fo:block>
										<fo:list-block>
										<fo:list-item>
										<fo:list-item-label>
										<fo:block start-indent="2.5cm">2.</fo:block>
										</fo:list-item-label>
										<fo:list-item-body>
										<fo:block   start-indent="3cm">use, misuse or unauthorized use of; or</fo:block>
										<fo:block>
										<fo:block>
										<fo:list-block>
										<fo:list-item>
										<fo:list-item-label>
										<fo:block start-indent="2.5cm">3.</fo:block>
										</fo:list-item-label>
										<fo:list-item-body>
										<fo:block space-after="4mm"  start-indent="3cm">disclosure of:</fo:block>
										</fo:list-item-body>
										
										</fo:list-item>
										</fo:list-block>
										
										</fo:block>
										<fo:list-block>
										<fo:list-item>
										<fo:list-item-label>
										<fo:block start-indent="3cm">a.</fo:block>
										</fo:list-item-label>
										<fo:list-item-body>
										<fo:block space-after="4mm"  start-indent="3.5cm">confidential or proprietary data; or</fo:block>
										</fo:list-item-body>
										
										</fo:list-item>
										</fo:list-block>
										
										</fo:block>
										
										
										<fo:block>
										<fo:list-block>
										<fo:list-item>
										<fo:list-item-label>
										<fo:block start-indent="3cm">b.</fo:block>
										</fo:list-item-label>
										<fo:list-item-body>
										<fo:block start-indent="3.5cm">personally identifiable: (1) information; or (2) data; containing private or confidential information;</fo:block>
										
										<fo:block start-indent="2.5cm">in connection with the performance of <fo:inline font-weight="bold">Professional Services</fo:inline> which results in the violation of any Privacy Regulation.</fo:block>
										</fo:list-item-body></fo:list-item>
										</fo:list-block></fo:block></fo:list-item-body></fo:list-item>
										</fo:list-block></fo:block></fo:list-item-body></fo:list-item>
										</fo:list-block></fo:block>
									</fo:list-item-body>
								</fo:list-item>
							</fo:list-block>
						</fo:block>
						
						
						<fo:block space-after="2cm">All other terms and conditions of the policy remain unchanged.</fo:block>
				     
				     
                  		<fo:table>
                          <fo:table-column column-width="80mm"/>
                           <fo:table-column column-width="30mm"/>
                          <fo:table-column column-width="70mm"/>
                         
                          <fo:table-body>
                                          <fo:table-row>
                                          <fo:table-cell>
										 <fo:block font-size="9px" color="black" border-bottom-width="0.9pt" border-bottom-style="solid" border-bottom-color="black" text-align="center">
                                          &#160;
                                          </fo:block>
                                          <fo:block font-size="9px" color="black" text-align="center">
                                          &#160;&#160;Authorized Representative
                                          </fo:block>
                                          </fo:table-cell>
                                          
                                          <fo:table-cell>
                                          <fo:block>&#160;</fo:block>
                                          </fo:table-cell>
                                         
                                          <fo:table-cell text-align="center">
                                           <fo:block margin-top="3mm" font-size="9px" color="black"  border-bottom-style="solid" border-bottom-color="black" text-align="center">
                                          <xsl:value-of select="response/general_freeform_01/data/CurrentDate"/>
                                          </fo:block>
                                          <fo:block font-size="9px" color="black">
                                          Date
                                          </fo:block>
                                          </fo:table-cell>
                                          </fo:table-row>
                                          </fo:table-body>
                          </fo:table>            
                   </fo:block>
				       
		       <fo:block margin-top="2cm" font-size="10px" color="grey" start-indent="17mm"
									text-align="left">APL-124 (11/18) FL, MT, VT &#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;
						           &#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;
						           &#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;
						           &#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;
						           &#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;
						           Page 1 of 1
								</fo:block>	
								
				
     </xsl:template>
</xsl:stylesheet>




					
				    	