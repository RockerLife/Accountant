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
                               page-height="29.7cm" margin-top="0.1in" margin-bottom="0.5in" margin-left="0.3in" margin-right="0.3in">
          <fo:region-body margin-top="0.3in"/>
          <fo:region-before extent="1.0in"/>
          <fo:region-after />
        </fo:simple-page-master>
      </fo:layout-master-set>

      <fo:page-sequence master-reference="A4-portrait" >
       <fo:static-content flow-name="xsl-region-before">
	   <fo:block>
            <fo:table margin-top="3mm">
              <fo:table-column column-width = "200mm"/>
              <fo:table-body>
                  <fo:table-row>
                  <fo:table-cell>
                     <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;">&#160;</fo:block>
                  </fo:table-cell>
                </fo:table-row>
				
				<fo:table-row>
                  <fo:table-cell>
                     <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;">&#160;</fo:block>
                  </fo:table-cell>
                </fo:table-row>
				<fo:table-row>
                  <fo:table-cell>
                     <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;">&#160;</fo:block>
                  </fo:table-cell>
                </fo:table-row>
				<fo:table-row>
                  <fo:table-cell>
                     <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;">&#160;</fo:block>
                  </fo:table-cell>
                </fo:table-row>
              </fo:table-body>
            </fo:table>
          </fo:block>
		  
		
          <fo:block margin-left="340px" color="grey">
            <!--  	<fo:external-graphic src="../AccountantIns/image/crum_logo.png" content-height="4em" content-width="12em"/>-->

           

          </fo:block>
        </fo:static-content>
        
        <fo:static-content flow-name="xsl-region-after">
          <fo:table>
            <fo:table-column column-width = "85mm" />
            <fo:table-column column-width = "100mm" />
            <fo:table-body>
            <fo:table-row>
                <fo:table-cell>
                  <fo:block text-align="left" font-size="8px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" color="grey">
                    <fo:block text-align="left" font-size="8px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" color="grey">APL-101 (11/18) NC</fo:block>
                  </fo:block>
                </fo:table-cell>
                
                <fo:table-cell>
                  <fo:block text-align="right" font-size="8px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" color="grey">
                   Page <fo:page-number/> of 
						<fo:page-number-citation 
						ref-id="TheVeryLastPage"/>
					</fo:block>
                </fo:table-cell>
              </fo:table-row>
            </fo:table-body>
          </fo:table>
        </fo:static-content>
        
        <fo:flow flow-name="xsl-region-body" >
          <fo:block>
            <fo:table>
              <fo:table-column column-width = "70mm" />
              <fo:table-column column-width = "50mm" />
              <fo:table-body>
                <fo:table-row>
                  <fo:table-cell>
                    <fo:block font-size="12px" font-weight="bold" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" >
                     <!--  <xsl:choose>
                        <xsl:when test="response/policy_freeform_01/data/StateCode= 'MT'">
                          Note: Except as may be amended by endorsement
                          to this policy Defense Expenses shall erode
                          the each Claim and Policy Aggregate Limit of
                          Liability available to pay Damages unless the
                          Policy Aggregate is less than $1,000,000, in
                          which case Defense Expenses do not begin to
                          erode the Policy Aggregate until they exceed
                          $50,000.
                        </xsl:when>
                        <xsl:otherwise>

                        </xsl:otherwise>
                      </xsl:choose> -->
                    </fo:block>
                  </fo:table-cell>
                  <fo:table-cell>
                    <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" >
                      <fo:table>
                        <fo:table-column column-width = "4.7in" />
						<fo:table-body>
						<fo:table-row>
                  <fo:table-cell>
                     <fo:block text-align="right" color="grey">
					  <fo:external-graphic src="../AccountantIns/image/crum2_logo.png" content-width="200px"/>
					 </fo:block>
                  </fo:table-cell>
                </fo:table-row>
						<fo:table-row>
							<fo:table-cell>
								<fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;">&#160;</fo:block>
							</fo:table-cell>
				  		</fo:table-row>
                          <fo:table-row>
                            <fo:table-cell >
                              <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" font-weight="bold" text-align="right">UNITED STATES FIRE INSURANCE COMPANY</fo:block>
                		 </fo:table-cell>
                          </fo:table-row>
                          <fo:table-row> 
                            <fo:table-cell >
                              <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" font-weight="bold" text-align="right">305 MADISON AVENUE, MORRISTOWN, NJ 07962</fo:block>
                            </fo:table-cell>
                          </fo:table-row>
                        </fo:table-body>
                      </fo:table>

                    </fo:block>
                  </fo:table-cell>
                </fo:table-row>
              </fo:table-body>
            </fo:table>
          </fo:block>
          <fo:block>
            <fo:table margin-top="3mm">
              <fo:table-column column-width = "200mm"/>
              <fo:table-body>
                <fo:table-row>
                  <fo:table-cell>
                    <fo:block></fo:block>
                  </fo:table-cell>
                </fo:table-row>
                <fo:table-row>
                  <fo:table-cell>
                    <fo:block  font-size="14px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" font-weight="bold" text-align="center">
                    	ACCOUNTANTS PROFESSIONAL LIABILITY APPLICATION – NORTH CAROLINA
                    </fo:block>
                  </fo:table-cell>
                </fo:table-row>
              </fo:table-body>
            </fo:table>
          </fo:block>
          <fo:block text-align="justify">
            <fo:table margin-top="3mm" border="0.0pt solid black">
              <fo:table-column/>
              <fo:table-body>
                <fo:table-row >
                  <fo:table-cell >
                    <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;">
						NOTICE: COVERAGE FOR WHICH THIS APPLICATION IS MADE IS WRITTEN ON A
						CLAIMS MADE AND REPORTED BASIS MEANING, EXCEPT AS OTHERWISE PROVIDED,
						COVERAGE APPLIES ONLY TO <fo:inline font-weight="bold">CLAIMS</fo:inline> FIRST MADE AGAINST THE <fo:inline font-weight="bold">INSURED</fo:inline> AND
						REPORTED TO THE <fo:inline font-weight="bold">INSURER</fo:inline> DURING THE <fo:inline font-weight="bold">POLICY PERIOD</fo:inline> OR DURING ANY
						APPLICABLE EXTENDED REPORTING PERIOD.
					</fo:block>
                  </fo:table-cell>
                </fo:table-row>
				 <fo:table-row >
                  <fo:table-cell >
                    <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;">&#160;</fo:block>
                  </fo:table-cell>
                </fo:table-row>
				<fo:table-row >
                  <fo:table-cell >
                    <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;">&#160;</fo:block>
                  </fo:table-cell>
                </fo:table-row>
				 <fo:table-row >
                  <fo:table-cell>
                    <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;">
						CAREFULLY READ THE ENTIRE POLICY FOR WHICH THIS APPLICATION IS MADE.
						WORDS AND PHRASES WHICH ARE PRINTED IN <fo:inline font-weight="bold" font-style="italic">BOLD ITALIC TYPEFACE</fo:inline> HAVE
						SPECIFIC MEANING AND ARE DEFINED IN SECTION IV. OF THE POLICY.
                    </fo:block>
                  </fo:table-cell>
                </fo:table-row>
				<fo:table-row >
                  <fo:table-cell >
                    <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;">&#160;</fo:block>
                  </fo:table-cell>
                </fo:table-row>
				<fo:table-row >
                  <fo:table-cell>
                    <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;">
					THE APPLICATION, ITS ATTACHMENTS AND ALL PREVIOUS APPLICATIONS AND THEIR
					ATTACHMENTS SHALL SERVE AS THE BASIS OF THE POLICY. THE <fo:inline font-weight="bold" font-style="italic">INSURER</fo:inline> RELIES
					UPON THE APPLICATION IN ISSUING THE POLICY. COMPLETION OF THIS
					APPLICATION DOES NOT IN ANY WAY IMPLY SUCH COVERAGE UNDER THE POLICY.
					COVERAGE IS AFFORDED ONLY IF AND TO THE EXTENT INDICATED BY THE TERMS
					AND CONDITIONS OF THE POLICY IF ISSUED.
					</fo:block>
                  </fo:table-cell>
                </fo:table-row>
				<fo:table-row >
                  <fo:table-cell >
                    <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;">&#160;</fo:block>
				  </fo:table-cell>
                </fo:table-row>
                <fo:table-row >
                  <fo:table-cell >
                    <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" font-weight="bold">
						Wherever the word “You, Your or Firm” is used, it will be deemed to
						include the Firm, its personnel, its predecessors and the Firm’s
						affiliates.
                    </fo:block>
				</fo:table-cell>
				</fo:table-row>
              </fo:table-body>
            </fo:table>
            </fo:block>
            <fo:block>
            	&#160;
            </fo:block>
            <fo:block>
            <fo:table margin-top="1mm" border="1pt solid black" background-color="LightGray ">
              <fo:table-column></fo:table-column>
              <fo:table-body>
                <fo:table-row>
                  <fo:table-cell>
                    <fo:block margin-top="1mm" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" font-weight="bold" font-size="10px">&#160;&#160;FIRM INFORMATION </fo:block>
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
            <fo:table>
              <fo:table-column column-width="0.9in" />
              <fo:table-column column-width="1.8in"/>
              <fo:table-column  column-width="0.5in"/>
              <fo:table-column column-width="1.2in"/>
              <fo:table-column column-width="0.5in" />
              <fo:table-column column-width="1.1in"/>
              <fo:table-column  column-width="0.5in"/>
              <fo:table-column column-width="1in"/>
              <fo:table-body>
                <fo:table-row>
                  <fo:table-cell  padding-top="18pt">
                    <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left"
											>
                      Firm Name:

                    </fo:block>
                  </fo:table-cell>
                  <fo:table-cell  padding-top="18pt"  number-columns-spanned="7">
                    <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" border-bottom-width="0.9pt" border-bottom-style="solid" border-bottom-color="black">
                      <xsl:value-of select="response/ACCOUNT_FREEFORM_01/data/AccountName"></xsl:value-of>

                    </fo:block>
                  </fo:table-cell>
                </fo:table-row>

                <fo:table-row>
                  <fo:table-cell  padding-top="10px">
                    <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left"
											>
                      Address:&#160;&#160;

                    </fo:block>
                  </fo:table-cell>
                  <fo:table-cell  padding-top="10px">
                    <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;"
											border-bottom-width="0.9pt" border-bottom-style="solid"
											border-bottom-color="black">
                        <xsl:value-of select="response/ADDRESS_FREEFORM_01/data/Address1"></xsl:value-of>

                    </fo:block>
                  </fo:table-cell>

                  <fo:table-cell  padding-top="10px">
                    <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
                      &#160;&#160;City:</fo:block>
                  </fo:table-cell>
                  <fo:table-cell  padding-top="10px">
                    <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;"
											border-bottom-width="0.9pt" border-bottom-style="solid"
											border-bottom-color="black">
		                   
					<xsl:choose>
						<xsl:when test="response/ADDRESS_FREEFORM_01/data/City != '' ">
							<xsl:value-of select="response/ADDRESS_FREEFORM_01/data/City"></xsl:value-of>
						</xsl:when>
						<xsl:otherwise>
							<fo:block>&#160;</fo:block>
						</xsl:otherwise>
					</xsl:choose>
		                       
                    </fo:block>

                  </fo:table-cell>
                  <fo:table-cell  padding-top="10px">
                    <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
                      &#160;&#160;State:</fo:block>
                  </fo:table-cell>
                  <fo:table-cell  padding-top="10px">
                    <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;"
											border-bottom-width="0.9pt" border-bottom-style="solid"
											border-bottom-color="black">
		                   
							<xsl:value-of select="response/ADDRESS_FREEFORM_01/data/StateDesc"></xsl:value-of>
					 </fo:block>

                  </fo:table-cell>
                  <fo:table-cell  padding-top="10px">
                    <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
                      &#160;&#160;Zip:</fo:block>
                  </fo:table-cell>
                  <fo:table-cell  padding-top="10px">
                    <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;"
											border-bottom-width="0.9pt" border-bottom-style="solid"
											border-bottom-color="black">
		                   
					<xsl:value-of select="response/ADDRESS_FREEFORM_01/data/Zip"/>&#160;<xsl:value-of select="response/ADDRESS_FREEFORM_01/data/Zip4"/>
		            </fo:block>
				</fo:table-cell>
				</fo:table-row>
                <fo:table-row>
                  <fo:table-cell  padding-top="10px" number-columns-spanned="8">
                    <fo:block>
                    <fo:table>
                    <fo:table-column column-width="1.4in"/>
                    <fo:table-column column-width="2in"/>
                    <fo:table-column column-width="1.1in"/>
                    <fo:table-column column-width="2.4in"/>
                    <fo:table-body>
                    <fo:table-row>
                    <fo:table-cell padding-top="10px">
                    <fo:block font-size="10px"  font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
						Address Line2:
					</fo:block>
                    </fo:table-cell>
                    <fo:table-cell  padding-top="10px" start-indent="0.3cm">
                    <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;"
											border-bottom-width="0.9pt" border-bottom-style="solid"
											border-bottom-color="black" >
	                    &#160; <xsl:value-of select="response/ADDRESS_FREEFORM_01/data/Address2"/>
          			</fo:block>
                  </fo:table-cell>
                  <fo:table-cell padding-top="10px">
                    <fo:block font-size="10px"  font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
						County:
					</fo:block>
                    </fo:table-cell>
                    <fo:table-cell  padding-top="10px" start-indent="0.3cm">
                    <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;"
											border-bottom-width="0.9pt" border-bottom-style="solid"
											border-bottom-color="black" >
	                    &#160; <xsl:value-of select="response/ADDRESS_FREEFORM_01/data/CountyDesc"/>
          			</fo:block>
                  </fo:table-cell>
                    </fo:table-row>
                    <fo:table-row>
                    <fo:table-cell padding-top="10px">
                    <fo:block font-size="10px"  font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
						Firm Phone Number:
					</fo:block>
                    </fo:table-cell>
                    <fo:table-cell  padding-top="10px" start-indent="0.3cm">
                    <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;"
											border-bottom-width="0.9pt" border-bottom-style="solid"
											border-bottom-color="black" >
	                     <xsl:value-of select="response/ADDRESS_FREEFORM_01/data/WorkPhone"/>&#160;<xsl:value-of select="response/ADDRESS_FREEFORM_01/data/WorkExt"/>
                       
          			</fo:block>
                  </fo:table-cell>
                  <fo:table-cell padding-top="10px" padding-left="2mm">
                    <fo:block font-size="10px"  font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
						E-mail Address:
					</fo:block>
                    </fo:table-cell>
                    <fo:table-cell  padding-top="10px" start-indent="0.3cm">
                    <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;"
											border-bottom-width="0.9pt" border-bottom-style="solid"
											border-bottom-color="black" >
	                     <xsl:value-of select="response/ACCOUNT_FREEFORM_01/data/AccountEmail"/>
          			</fo:block>
                  </fo:table-cell>
                  </fo:table-row>
                    
                    </fo:table-body>
                    </fo:table>
                    </fo:block>
                  </fo:table-cell>
				</fo:table-row>
                <fo:table-row>
                <fo:table-cell  padding-top="10px" number-columns-spanned="8">
                    <fo:block>
                    <fo:table>
                    <fo:table-column column-width="1.6in"/>
                    <fo:table-column column-width="1.8in"/>
                    <fo:table-column column-width="2.7in"/>
                    <fo:table-column column-width="1.5in"/>
                    <fo:table-body>
                    <fo:table-row>
                    <fo:table-cell padding-top="10px">
                    <fo:block font-size="10px"  font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
						Contacts:&#160;
						<fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline> Mr. &#160; 
						<fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline> Ms.
					</fo:block>
                    </fo:table-cell>
                    <fo:table-cell  padding-top="10px" start-indent="0.3cm">
                    <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;"
											border-bottom-width="0.9pt" border-bottom-style="solid"
											border-bottom-color="black" >
	                   &#160; <xsl:value-of select="response/FIRM_FREEFORM_01/data/ContactPerson"/>
          			</fo:block>
                  </fo:table-cell>
                  <fo:table-cell padding-top="10px" padding-left="2mm">
                    <fo:block font-size="10px"  font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
						Contact Phone (if different than above):
					</fo:block>
                    </fo:table-cell>
                    <fo:table-cell  padding-top="10px" start-indent="0.3cm">
                    <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;"
											border-bottom-width="0.9pt" border-bottom-style="solid"
											border-bottom-color="black" >
	                     <xsl:value-of select="response/FIRM_FREEFORM_01/data/ContactPhone"/>&#160;<xsl:value-of select="response/FIRM_FREEFORM_01/data/ContactPhoneExt"/>
          			</fo:block>
                  </fo:table-cell>
                  </fo:table-row>
                  </fo:table-body>
                  </fo:table>
                  </fo:block>
                  </fo:table-cell>
                </fo:table-row>
                </fo:table-body>
            </fo:table>
          </fo:block>
          <fo:block>
          <fo:table>
          <fo:table-column column-width="0.3in"/>
          <fo:table-column column-width="4in"/>
          <fo:table-column column-width="3in"/>
          <fo:table-body>
          <fo:table-row>
          <fo:table-cell padding-top="10px">
          <fo:block font-size="10px"  font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          	1.
          </fo:block>
          </fo:table-cell>
          <fo:table-cell number-columns-spanned="2" padding-left="2mm">
          <fo:block>
          <fo:table>
          <fo:table-column column-width="80mm"/>
          <fo:table-column column-width="100mm"/>
          <fo:table-body>
          <fo:table-row>
          <fo:table-cell padding-top="10px">
          <fo:block font-size="10px"  font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          	When would you like your insurance to start?
          </fo:block>
          </fo:table-cell>
          <fo:table-cell padding-top="10px">
	         <fo:block text-align="center" font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" border-bottom-width="0.9pt" border-bottom-style="solid" border-bottom-color="black">
	                      &#160;<xsl:value-of select="response/PolicyEffectiveDate"></xsl:value-of>
			 </fo:block>
				 <fo:block font-size="8px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" font-style="italic" text-align="center">
	                     MM / DD / YYYY
				 </fo:block>
          </fo:table-cell>
          </fo:table-row>
          </fo:table-body>
          </fo:table>
          </fo:block>
          </fo:table-cell>
          </fo:table-row>
          <fo:table-row>
          <fo:table-cell padding-top="20px">
          <fo:block font-size="10px"  font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          	2.
          </fo:block>
          </fo:table-cell>
          <fo:table-cell number-columns-spanned="2" padding-top="10px" padding-left="2mm">
          <fo:block>
          <fo:table>
          <fo:table-column column-width="100mm"/>
          <fo:table-column column-width="80mm"/>
          <fo:table-body>
          <fo:table-row>
          <fo:table-cell padding-top="10px">
          <fo:block font-size="10px"  font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          	What is the total number of personnel in your Firm?
          </fo:block>
          </fo:table-cell>
          <fo:table-cell padding-top="10px">
	         <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" border-bottom-width="0.9pt" border-bottom-style="solid" border-bottom-color="black">
	                      &#160;<xsl:value-of select="response/FIRM_FREEFORM_01/data/TotalFirmPersonnel"></xsl:value-of>
			 </fo:block>
			
          </fo:table-cell>
          </fo:table-row>
          <fo:table-row>
          <fo:table-cell padding-top="10px">
          <fo:block font-size="10px"  font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          	How many of the above are CPAs?
          </fo:block>
          </fo:table-cell>
          <fo:table-cell padding-top="10px">
	         <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" border-bottom-width="0.9pt" border-bottom-style="solid" border-bottom-color="black">
	                      &#160;<xsl:value-of select="response/AboutFirm_FREEFORM_01/data/CPAPersonnel"></xsl:value-of>
			 </fo:block>
			
          </fo:table-cell>
          </fo:table-row>
          </fo:table-body>
          </fo:table>
          </fo:block>
          </fo:table-cell>
          </fo:table-row>
          <fo:table-row>
          <fo:table-cell padding-top="10px">
          <fo:block font-size="10px"  font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          	3.
          </fo:block>
          </fo:table-cell>
          <fo:table-cell number-columns-spanned="2" padding-top="10px" padding-left="2mm">
          <fo:block font-size="10px"  font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          Please provide your Firm’s revenue for the last three years, starting with the last completed business year:
          </fo:block>
          </fo:table-cell>
          </fo:table-row>
          <fo:table-row>
          <fo:table-cell>
          <fo:block font-size="10px"  font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          </fo:block>
          </fo:table-cell>
          <fo:table-cell number-columns-spanned="2" padding-top="3px">
          <fo:block font-size="10px"  font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          <fo:table border="0.5pt solid black">
          <fo:table-column column-width="60mm"/>
          <fo:table-column column-width="60mm"/>
          <fo:table-column column-width="65mm"/>
          <fo:table-body>
          <fo:table-row>
          <fo:table-cell border="0.5pt solid black" padding-top="5px" padding-bottom="5px">
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="center">
          	*Last Completed Year*
          </fo:block>
          </fo:table-cell>
          <fo:table-cell border="0.5pt solid black" padding-top="5px" padding-bottom="5px">
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="center">
          	Second Completed Fiscal Year
          </fo:block>
          </fo:table-cell>
          <fo:table-cell border="0.5pt solid black" padding-top="5px" padding-bottom="5px">
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="center">
          	Third Completed Fiscal Year
          </fo:block>
          </fo:table-cell>
          </fo:table-row>
          <fo:table-row>
          <fo:table-cell border="0.5pt solid black" padding-top="10px" padding-bottom="10px">
          <fo:table>
          <fo:table-column column-width="30mm"/>
          <fo:table-column column-width="30mm"/>
          <fo:table-body>
          <fo:table-row>
          <fo:table-cell padding-left="2mm">
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="center">
          	Year:
          </fo:block>
          </fo:table-cell>
           <fo:table-cell padding-left="2mm" padding-right="2mm">
          <fo:block text-align="center" font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" border-bottom-width="0.9pt" border-bottom-style="solid" border-bottom-color="black">
	                      &#160;<xsl:value-of select="response/YearEndDate_0" />
			 </fo:block>
			 <fo:block font-size="8px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" font-style="italic" text-align="center">
	                     MM / YYYY
				 </fo:block>
          </fo:table-cell>
          </fo:table-row>
          </fo:table-body>
          </fo:table>
          </fo:table-cell>
          <fo:table-cell border="0.5pt solid black" padding-top="10px" padding-bottom="10px">
          <fo:table>
          <fo:table-column column-width="30mm"/>
          <fo:table-column column-width="30mm"/>
          <fo:table-body>
          <fo:table-row>
          <fo:table-cell padding-left="2mm">
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="center">
          	Year:
          </fo:block>
          </fo:table-cell>
           <fo:table-cell padding-left="2mm" padding-right="2mm">
          <fo:block text-align="center" font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" border-bottom-width="0.9pt" border-bottom-style="solid" border-bottom-color="black">
	                      &#160;<xsl:value-of select="response/YearEndDate_1" />
			 </fo:block>
			 <fo:block font-size="8px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" font-style="italic" text-align="center">
	                     MM / YYYY
				 </fo:block>
          </fo:table-cell>
          </fo:table-row>
          </fo:table-body>
          </fo:table>
          </fo:table-cell>
          <fo:table-cell border="0.5pt solid black" padding-top="10px" padding-bottom="10px">
          <fo:table>
          <fo:table-column column-width="30mm"/>
          <fo:table-column column-width="30mm"/>
          <fo:table-body>
          <fo:table-row>
          <fo:table-cell padding-left="2mm">
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="center">
          	Year:
          </fo:block>
          </fo:table-cell>
           <fo:table-cell padding-left="2mm" padding-right="2mm">
          <fo:block text-align="center" font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" border-bottom-width="0.9pt" border-bottom-style="solid" border-bottom-color="black">
	                      &#160;<xsl:value-of select="response/YearEndDate_2" />
			 </fo:block>
			 <fo:block font-size="8px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" font-style="italic" text-align="center">
	                     MM / YYYY
				 </fo:block>
          </fo:table-cell>
          </fo:table-row>
          </fo:table-body>
          </fo:table>
          </fo:table-cell>
          </fo:table-row>
          <fo:table-row>
          <fo:table-cell padding-left="2mm" border="0.5pt solid black" padding-top="5px" padding-bottom="5px">
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          	<xsl:value-of select="response/Amount_0" />
          </fo:block>
          </fo:table-cell>
          <fo:table-cell padding-left="2mm" border="0.5pt solid black" padding-top="5px" padding-bottom="5px">
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          	<xsl:value-of select="response/Amount_1" />
          </fo:block>
          </fo:table-cell>
          <fo:table-cell padding-left="2mm" border="0.5pt solid black" padding-top="5px" padding-bottom="5px">
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          	<xsl:value-of select="response/Amount_2" />
          </fo:block>
          </fo:table-cell>
          </fo:table-row>
          </fo:table-body>
          </fo:table>
          </fo:block>
          </fo:table-cell>
          </fo:table-row>
          <fo:table-row>
          <fo:table-cell padding-top="10px">
          <fo:block font-size="10px"  font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          	
          </fo:block>
          </fo:table-cell>
          <fo:table-cell number-columns-spanned="2">
          <fo:block>
          <fo:table>
          <fo:table-column column-width="40mm"/>
          <fo:table-column column-width="150mm"/>
          <fo:table-body>
          <fo:table-row>
          <fo:table-cell padding-top="5px" number-columns-spanned="2">
          <fo:block font-size="9px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" font-weight="bold" font-style="italic" text-align="left">
	           * If the revenue estimate for the current year is above $500,000; complete the PRACTICE MANAGEMENT SUPPLEMENT.
		  </fo:block>
		  </fo:table-cell>
          </fo:table-row>
          </fo:table-body>
          </fo:table>
          </fo:block>
          </fo:table-cell>
          </fo:table-row>
          </fo:table-body>
          </fo:table>
          </fo:block>
          <fo:block page-break-before="always"/>
          <fo:block>
          <fo:table>
          <fo:table-column column-width="0.3in"/>
          <fo:table-column column-width="4in"/>
          <fo:table-column column-width="2in"/>
          <fo:table-body>
          
          <fo:table-row>
          <fo:table-cell padding-top="10px">
          <fo:block font-size="10px"  font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          	4.
          </fo:block>
          </fo:table-cell>
          <fo:table-cell number-columns-spanned="2" padding-left="2mm">
          <fo:block>
          <fo:table>
          <fo:table-column column-width="155mm"/>
          <fo:table-column column-width="35mm"/>
          <fo:table-body>
          <fo:table-row>
          <fo:table-cell padding-top="10px">
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	           Does your Firm or any owners, partners or officers render services or conduct any business activities under a separate entity name which require a professional license or professional 
		  </fo:block>
		  </fo:table-cell>
          </fo:table-row>
          <fo:table-row>
          <fo:table-cell>
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	           designation? 
		  </fo:block>
		  </fo:table-cell>
		  <fo:table-cell>
		  <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left" >
                      	<xsl:choose>
                            <xsl:when test="response/FIRM_FREEFORM_01/data/IsFirmRenderingServices = 'Y'">
                            Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
                            </xsl:when>
                            <xsl:when test="response/FIRM_FREEFORM_01/data/IsFirmRenderingServices = 'N'">
                            Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline>
                            </xsl:when>
                            <xsl:otherwise>
                             Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
                            
                            </xsl:otherwise>
                           </xsl:choose>
                    </fo:block>
		  </fo:table-cell>
          </fo:table-row>
          <fo:table-row>
          <fo:table-cell number-columns-spanned="2" padding-top="5px">
          <fo:block font-size="9.5px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" font-weight="bold" font-style="italic" text-align="left">
	           If Yes, complete the SEPARATE ENTITY SUPPLEMENT for all such entities whether coverage is desired or not.
		  </fo:block>
		  </fo:table-cell>
		  </fo:table-row>
		  <fo:table-row>
          <fo:table-cell number-columns-spanned="2" padding-top="5px">
          <fo:block font-size="9.5px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" font-style="italic" text-align="left">
	           Coverage may be available for such entities by endorsement to your policy subject to underwriting approval.
		  </fo:block>
		  </fo:table-cell>
		  </fo:table-row>
          </fo:table-body>
          </fo:table>
          </fo:block>
          </fo:table-cell>
          </fo:table-row>
          <fo:table-row>
          <fo:table-cell padding-top="10px">
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          	5.
          </fo:block>
          </fo:table-cell>
          <fo:table-cell number-columns-spanned="2" padding-left="2mm">
          <fo:block>
          <fo:table>
          <fo:table-column column-width="155mm"/>
          <fo:table-column column-width="25mm"/>
          <fo:table-body>
          <fo:table-row>
          <fo:table-cell padding-top="10px">
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          	If your revenue is above $100,000, has any client during the last three years represented 40% or  
          </fo:block>
          </fo:table-cell>
          </fo:table-row>
          <fo:table-row>
          <fo:table-cell>
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          	more of your revenue?
          </fo:block>
          </fo:table-cell>
          <fo:table-cell>
		  <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left" >
                      	<xsl:choose>
                            <xsl:when test="response/FIRM_FREEFORM_01/data/IsFirmsRevenueMorethan25Percent = 'Y'">
                            Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
                            </xsl:when>
                            <xsl:when test="response/FIRM_FREEFORM_01/data/IsFirmsRevenueMorethan25Percent = 'N'">
                            Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline>
                            </xsl:when>
                            <xsl:otherwise>
                             Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
                            
                            </xsl:otherwise>
                           </xsl:choose>
                    </fo:block>
		  </fo:table-cell>
          </fo:table-row>
          <fo:table-row>
          <fo:table-cell padding-top="10px">
          <fo:block font-size="9px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          	If Yes, please indicate percentage of revenue from largest revenue client (include client’s affiliated entities):
          </fo:block>
          </fo:table-cell>
          <fo:table-cell padding-top="10px">
	         <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" border-bottom-width="0.9pt" border-bottom-style="solid" border-bottom-color="black">
	                      &#160; <xsl:value-of select="response/FIRM_FREEFORM_01/data/PercentOfRevenueFromLargestClient" />%
			 </fo:block>
		  </fo:table-cell>
          </fo:table-row>
          <fo:table-row>
          <fo:table-cell padding-top="10px" number-columns-spanned="2">
          <fo:block>
          <fo:table>
          <fo:table-column column-width="32mm"/>
          <fo:table-column column-width="148mm"/>
          <fo:table-body>
          <fo:table-row>
          <fo:table-cell padding-top="10px">
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          	Services rendered:
          </fo:block>
          </fo:table-cell>
          <fo:table-cell padding-top="10px">
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          	<fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" border-bottom-width="0.9pt" border-bottom-style="solid" border-bottom-color="black">
	              <xsl:value-of select="response/FIRM_FREEFORM_01/data/ServiceIndustryofLargestClient"></xsl:value-of>
			 </fo:block>
          </fo:block>
          </fo:table-cell>
          </fo:table-row>
          </fo:table-body>
          </fo:table>
          </fo:block>
          </fo:table-cell>
          </fo:table-row>
          </fo:table-body>
          </fo:table>
          </fo:block>
          </fo:table-cell>
          </fo:table-row>
          <fo:table-row>
          <fo:table-cell padding-top="10px">
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          	6.
          </fo:block>
          </fo:table-cell>
          <fo:table-cell number-columns-spanned="2" padding-left="2mm">
          <fo:block>
          <fo:table>
          <fo:table-column column-width="155mm"/>
          <fo:table-column column-width="25mm"/>
          <fo:table-body>
          <fo:table-row>
          <fo:table-cell padding-top="10px">
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          	Do you obtain a signed engagement letter or written agreement updated annually outlining the  
          </fo:block>
          </fo:table-cell>
          </fo:table-row>
          <fo:table-row>
          <fo:table-cell>
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          	services the Firm will perform for each of the Firm’s clients?
          </fo:block>
          </fo:table-cell>
          <fo:table-cell>
		  <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left" >
                      	<xsl:choose>
                            <xsl:when test="response/FIRM_FREEFORM_01/data/IsSignedEngagement = 'Y'">
                            Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
                            </xsl:when>
                            <xsl:when test="response/FIRM_FREEFORM_01/data/IsSignedEngagement = 'N'">
                            Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline>
                            </xsl:when>
                            <xsl:otherwise>
                             Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
                            
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
          <fo:table-row>
          <fo:table-cell padding-top="10px">
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          	7.
          </fo:block>
          </fo:table-cell>
          <fo:table-cell number-columns-spanned="2" padding-left="2mm">
          <fo:block>
          <fo:table>
          <fo:table-column column-width="155mm"/>
          <fo:table-column column-width="25mm"/>
          <fo:table-body>
          <fo:table-row>
          <fo:table-cell padding-top="10px">
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          	If your revenue is above $100,000, within the last 10 years, has your Firm merged with or  
          </fo:block>
          </fo:table-cell>
          </fo:table-row>
          <fo:table-row>
          <fo:table-cell>
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          	acquired the business of any sole practitioner, accounting firm or other business entity?
          </fo:block>
          </fo:table-cell>
          <fo:table-cell>
		  <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left" >
                      	<xsl:choose>
                            <xsl:when test="response/FIRM_FREEFORM_01/data/IsAnyFirmMeargedOrAcquired = 'Y'">
                            Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
                            </xsl:when>
                            <xsl:when test="response/FIRM_FREEFORM_01/data/IsAnyFirmMeargedOrAcquired = 'N'">
                            Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline>
                            </xsl:when>
                            <xsl:otherwise>
                             Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
                            
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
          <fo:table-row>
          <fo:table-cell padding-top="10px">
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          	8.
          </fo:block>
          </fo:table-cell>
          <fo:table-cell number-columns-spanned="2" padding-left="2mm">
          <fo:block>
          <fo:table>
          <fo:table-column column-width="155mm"/>
          <fo:table-column column-width="25mm"/>
          <fo:table-body>
          <fo:table-row>
          <fo:table-cell padding-top="10px">
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          	Do you control or disburse client funds other than for payroll or bookkeeping services?
          </fo:block>
          </fo:table-cell>
          <fo:table-cell padding-top="10px">
		  <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left" >
                      	<xsl:choose>
                            <xsl:when test="response/FIRM_FREEFORM_01/data/IsControlClientFunds = 'Y'">
                            Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
                            </xsl:when>
                            <xsl:when test="response/FIRM_FREEFORM_01/data/IsControlClientFunds = 'N'">
                            Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline>
                            </xsl:when>
                            <xsl:otherwise>
                             Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
                            
                            </xsl:otherwise>
                           </xsl:choose>
                    </fo:block>
		  </fo:table-cell>
          </fo:table-row>
          <fo:table-row>
          <fo:table-cell>
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" font-weight="bold" font-style="italic" text-align="left">
          	If Yes, please complete the BUSINESS MANAGEMENT SUPPLEMENT.
          </fo:block>
          </fo:table-cell>
          
          </fo:table-row>
          </fo:table-body>
          </fo:table>
          </fo:block>
          </fo:table-cell>
          </fo:table-row>
          <fo:table-row>
          <fo:table-cell padding-top="10px">
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          	9.
          </fo:block>
          </fo:table-cell>
          <fo:table-cell number-columns-spanned="2" padding-left="2mm">
          <fo:block>
          <fo:table>
          <fo:table-column column-width="155mm"/>
          <fo:table-column column-width="25mm"/>
          <fo:table-body>
          <fo:table-row>
          <fo:table-cell padding-top="10px">
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          	Have you been involved in any of the following within the past five years?
          </fo:block>
          </fo:table-cell>
          <fo:table-cell padding-top="10px">
		  <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left" >
                      	<xsl:choose>
                            <xsl:when test="response/FIRM_FREEFORM_01/data/IsReferralsInvestmentVentures = 'Y'">
                            Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
                            </xsl:when>
                            <xsl:when test="response/FIRM_FREEFORM_01/data/IsReferralsInvestmentVentures = 'N'">
                            Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline>
                            </xsl:when>
                            <xsl:otherwise>
                             Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
                            
                            </xsl:otherwise>
                           </xsl:choose>
                    </fo:block>
		  </fo:table-cell>
          </fo:table-row>
          <fo:table-row>
          <fo:table-cell>
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          	<fo:list-block>
                <fo:list-item>
                  <fo:list-item-label>
                    <fo:block>
                      <fo:inline>a.</fo:inline>
                    </fo:block>
                  </fo:list-item-label>
                  <fo:list-item-body start-indent="7mm">
					<fo:block space-after="3mm" >
                   		Organization or promotion in investment ventures;
                    </fo:block>
                  </fo:list-item-body>
                </fo:list-item>
            </fo:list-block>
          </fo:block>
          </fo:table-cell>
		  <fo:table-cell>
		  <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left" >
          </fo:block>
		  </fo:table-cell>
		  </fo:table-row>
		  
		  <fo:table-row>
          <fo:table-cell>
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          	<fo:list-block>
                <fo:list-item>
                  <fo:list-item-label>
                    <fo:block>
                      <fo:inline>b.</fo:inline>
                    </fo:block>
                  </fo:list-item-label>
                  <fo:list-item-body start-indent="7mm">
					<fo:block space-after="3mm" >
                   		Referrals to invest in investment ventures; or
                    </fo:block>
                  </fo:list-item-body>
                </fo:list-item>
            </fo:list-block>
          </fo:block>
          </fo:table-cell>
		  <fo:table-cell>
		  <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left" >
          </fo:block>
		  </fo:table-cell>
		  </fo:table-row>
		  
		  <fo:table-row>
          <fo:table-cell>
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          	<fo:list-block>
                <fo:list-item>
                  <fo:list-item-label>
                    <fo:block>
                      <fo:inline>c.</fo:inline>
                    </fo:block>
                  </fo:list-item-label>
                  <fo:list-item-body start-indent="7mm">
					<fo:block space-after="3mm" >
                   		Arranged debit or equity financing, acted as a business broker, underwritten the offering of public or private securities, or prepared fairness opinions.
                    </fo:block>
                  </fo:list-item-body>
                </fo:list-item>
             </fo:list-block>
          </fo:block>
          </fo:table-cell>
		  <fo:table-cell>
		  <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left" >
          </fo:block>
		  </fo:table-cell>
		  </fo:table-row>
		  
          </fo:table-body>
          </fo:table>
          </fo:block>
          </fo:table-cell>
          </fo:table-row>
          <fo:table-row>
          <fo:table-cell padding-top="10px">
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          	10.
          </fo:block>
          </fo:table-cell>
          <fo:table-cell number-columns-spanned="2" padding-left="2mm">
          <fo:block>
          <fo:table>
          <fo:table-column column-width="155mm"/>
          <fo:table-column column-width="25mm"/>
          <fo:table-body>
          <fo:table-row>
          <fo:table-cell padding-top="10px">
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          	Have you or your predecessors within the past five years, provided services to investment funds,  
          </fo:block>
          </fo:table-cell>
          </fo:table-row>
          <fo:table-row>
          <fo:table-cell>
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          	hedge funds, mutual funds or syndication managers?
          </fo:block>
          </fo:table-cell>
          <fo:table-cell>
		  <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left" >
                      	<xsl:choose>
                            <xsl:when test="response/FIRM_FREEFORM_01/data/IsSyndicationManagers = 'Y'">
                            Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
                            </xsl:when>
                            <xsl:when test="response/FIRM_FREEFORM_01/data/IsSyndicationManagers = 'N'">
                            Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline>
                            </xsl:when>
                            <xsl:otherwise>
                             Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
                            
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
          <fo:table-row>
          <fo:table-cell padding-top="10px">
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          	11.
          </fo:block>
          </fo:table-cell>
          <fo:table-cell number-columns-spanned="2" padding-left="2mm">
          <fo:block>
          <fo:table>
          <fo:table-column column-width="155mm"/>
          <fo:table-column column-width="25mm"/>
          <fo:table-body>
          <fo:table-row>
          <fo:table-cell padding-top="10px">
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          	Have you, your personnel or your predecessors within the past five years, invested in any non-
          </fo:block>
          </fo:table-cell>
          </fo:table-row>
          <fo:table-row>
          <fo:table-cell>
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          	public investment venture in which a client has also invested?
          </fo:block>
          </fo:table-cell>
          <fo:table-cell>
		  <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left" >
                      	<xsl:choose>
                            <xsl:when test="response/FIRM_FREEFORM_01/data/IsNonPublicInvestmentDone = 'Y'">
                            Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
                            </xsl:when>
                            <xsl:when test="response/FIRM_FREEFORM_01/data/IsNonPublicInvestmentDone = 'N'">
                            Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline>
                            </xsl:when>
                            <xsl:otherwise>
                             Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
                            
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
          <fo:table-row>
          <fo:table-cell padding-top="10px">
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          	12.
          </fo:block>
          </fo:table-cell>
          <fo:table-cell number-columns-spanned="2" padding-left="2mm">
          <fo:block>
          <fo:table>
          <fo:table-column column-width="155mm"/>
          <fo:table-column column-width="25mm"/>
          <fo:table-body>
          <fo:table-row>
          <fo:table-cell padding-top="10px">
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          	Have you within the past five years, served as a trustee, co-trustee, executor, administrator or  
          </fo:block>
          </fo:table-cell>
          </fo:table-row>
          <fo:table-row>
          <fo:table-cell>
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          	personal representative for any client?
          </fo:block>
          </fo:table-cell>
          <fo:table-cell>
		  <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left" >
                      	<xsl:choose>
                            <xsl:when test="response/FIRM_FREEFORM_01/data/IsServedAsTrustee = 'Y'">
                            Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
                            </xsl:when>
                            <xsl:when test="response/FIRM_FREEFORM_01/data/IsServedAsTrustee = 'N'">
                            Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline>
                            </xsl:when>
                            <xsl:otherwise>
                             Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
                            
                            </xsl:otherwise>
                           </xsl:choose>
                    </fo:block>
		  </fo:table-cell>
          </fo:table-row>
          <xsl:if test="response/FIRM_FREEFORM_01/data/IsServedAsTrustee = 'Y'">
          <fo:table-row>
          <fo:table-cell padding-top="5px">
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" font-style="italic" text-align="left">
          	If Yes to question 12, please complete the following information for each trust or estate below:
          </fo:block>
          </fo:table-cell>
          </fo:table-row>
          </xsl:if>
          </fo:table-body>
          </fo:table>
          </fo:block>
          </fo:table-cell>
          </fo:table-row>
          <xsl:if test="response/FIRM_FREEFORM_01/data/IsServedAsTrustee = 'Y'">
          <fo:table-row>
          <fo:table-cell padding-top="5px" number-columns-spanned="3">
          <fo:block>
          <fo:table border="0.5pt solid black">
          <fo:table-column column-width="75mm"/>
          <fo:table-column column-width="25mm"/>
          <fo:table-column column-width="43mm"/>
          <fo:table-column column-width="53mm"/>
          <fo:table-body>
          <fo:table-row>
          <fo:table-cell padding-bottom="2px" padding-top="2px" border="0.5pt solid black">
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="center">
         	 Name of Trust or Estate
          </fo:block>
          </fo:table-cell>
          <fo:table-cell padding-bottom="2px" padding-top="2px" border="0.5pt solid black">
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="center">
         	 *Type
          </fo:block>
          </fo:table-cell>
          <fo:table-cell padding-bottom="2px" padding-top="2px" border="0.5pt solid black">
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="center">
         	 Value of Assets
          </fo:block>
          </fo:table-cell>
          <fo:table-cell padding-bottom="2px" padding-top="2px" border="0.5pt solid black">
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="center">
         	 **Beneficiary Interest?
          </fo:block>
          </fo:table-cell>
          </fo:table-row>
		  <xsl:for-each select="response/GENERAL_LIST_01/data">
          <fo:table-row>
          <fo:table-cell padding-bottom="5px" padding-top="5px" border="0.5pt solid black" padding-left="2mm">
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="center">
         	 <xsl:value-of select="NameOfTrust"/>
          </fo:block>
          </fo:table-cell>
          <fo:table-cell padding-bottom="5px" padding-top="5px" border="0.5pt solid black" padding-left="2mm">
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="center">
         	 <xsl:value-of select="TypeofTrustKey"/>
          </fo:block>
          </fo:table-cell>
          <fo:table-cell padding-bottom="5px" padding-top="5px" border="0.5pt solid black" padding-left="2mm">
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="center">
         	 <xsl:value-of select="ValueOfAssetsTrustformat"/>
          </fo:block>
          </fo:table-cell>
          <fo:table-cell padding-bottom="5px" padding-top="5px" border="0.5pt solid black" padding-left="2mm">
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="center">
         	<xsl:value-of select="IsBeneficiaryInterestTrust"/>
          </fo:block>
          </fo:table-cell>
          </fo:table-row>
		  </xsl:for-each> 
          </fo:table-body>
          </fo:table>
          </fo:block>
          </fo:table-cell>
          </fo:table-row>
          <fo:table-row>
          <fo:table-cell number-columns-spanned="3">
          <fo:block>
          <fo:table>
          <fo:table-column column-width="195mm"/>
          <fo:table-body>
          <fo:table-row>
          <fo:table-cell padding-left="2mm">
          <fo:block font-size="8.5px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          *&#160; <fo:inline font-weight="bold">E</fo:inline> = Estate <fo:inline font-weight="bold">P</fo:inline> = Personal/Family trusts <fo:inline font-weight="bold">B</fo:inline> = Business trusts <fo:inline font-weight="bold">F</fo:inline> = Foundations <fo:inline font-weight="bold">C</fo:inline> = Charities <fo:inline font-weight="bold">R</fo:inline> = Real Estate <fo:inline font-weight="bold">I</fo:inline> = Irrevocable Life Insurance Trusts
          </fo:block>
          </fo:table-cell>
          </fo:table-row>
          <fo:table-row>
          <fo:table-cell padding-top="2px" padding-left="2mm">
          <fo:block font-size="8.5px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          ** Beneficiary interest means any personal interest you or your relatives might have as heir or beneficiary of the trust or estate funds, other than 
          </fo:block>
          </fo:table-cell>
          </fo:table-row>
          <fo:table-row>
          <fo:table-cell padding-left="5mm">
          <fo:block font-size="8.5px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          customary fees as trustee, administrator, executor or personal representative to which you are entitled. 
          </fo:block>
          </fo:table-cell>
          </fo:table-row>
          </fo:table-body>
          </fo:table>
          </fo:block>
          </fo:table-cell>
          </fo:table-row>
          </xsl:if>
          </fo:table-body>
          </fo:table>
          </fo:block>
          <xsl:if test="response/FIRM_FREEFORM_01/data/IsServedAsTrustee = 'Y'">
		  <fo:block page-break-before="always" />
		  </xsl:if>
          <fo:block space-before="5mm" font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          	<fo:table>
            <fo:table-column column-width="0.3in"/>
            <fo:table-column column-width="4in"/>
            <fo:table-column column-width="2in"/>
            <fo:table-body>
          <fo:table-row>
          <fo:table-cell padding-top="10px">
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          	13.
          </fo:block>
          </fo:table-cell>
          <fo:table-cell number-columns-spanned="2" padding-left="2mm">
          <fo:block>
          <fo:table>
          <fo:table-column column-width="155mm"/>
          <fo:table-column column-width="25mm"/>
          <fo:table-body>
          <fo:table-row>
          <fo:table-cell padding-top="10px">
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          	Have you served as an officer, director, partner, manager or other member of a client’s governing 
          </fo:block>
          </fo:table-cell>
          </fo:table-row>
          <fo:table-row>
          <fo:table-cell>
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          	body?
          </fo:block>
          </fo:table-cell>
          <fo:table-cell>
		  <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left" >
                      	<xsl:choose>
                            <xsl:when test="response/FIRM_FREEFORM_01/data/IsFirmRenderedServiceForAnyClient = 'Y'">
                            Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
                            </xsl:when>
                            <xsl:when test="response/FIRM_FREEFORM_01/data/IsFirmRenderedServiceForAnyClient = 'N'">
                            Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline>
                            </xsl:when>
                            <xsl:otherwise>
                             Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
                            
                            </xsl:otherwise>
                           </xsl:choose>
                    </fo:block>
		  </fo:table-cell>
          </fo:table-row>
          <xsl:if test="response/FIRM_FREEFORM_01/data/IsFirmRenderedServiceForAnyClient = 'Y'">
          <fo:table-row>
          <fo:table-cell padding-top="5px">
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" font-style="italic" text-align="left">
          	If Yes to question 13, please complete the following information below:
          </fo:block>
          </fo:table-cell>
          </fo:table-row>
          </xsl:if>
          </fo:table-body>
          </fo:table>
          </fo:block>
          </fo:table-cell>
          </fo:table-row>
          <xsl:if test="response/FIRM_FREEFORM_01/data/IsFirmRenderedServiceForAnyClient = 'Y'">
          <fo:table-row>
          <fo:table-cell padding-top="5px" number-columns-spanned="3">
          <fo:block>
          <fo:table border="0.5pt solid black">
          <fo:table-column column-width="20mm"/>
          <fo:table-column column-width="30mm"/>
          <fo:table-column column-width="25mm"/>
          <fo:table-column column-width="20mm"/>
          <fo:table-column column-width="27mm"/>
          <fo:table-column column-width="20mm"/>
          <fo:table-column column-width="29mm"/>
          <fo:table-column column-width="25mm"/>
          <fo:table-body>
          <fo:table-row>
          <fo:table-cell padding-bottom="10px" padding-top="20px" border="0.5pt solid black">
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="center">
         	 Firm Member
          </fo:block>
          </fo:table-cell>
          <fo:table-cell padding-bottom="10px" padding-top="25px" border="0.5pt solid black">
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="center">
         	 Entity Name
          </fo:block>
          </fo:table-cell>
          <fo:table-cell padding-bottom="10px" padding-top="25px" border="0.5pt solid black">
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="center">
         	 Industry
          </fo:block>
          </fo:table-cell>
          <fo:table-cell padding-bottom="10px" padding-top="20px" border="0.5pt solid black">
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="center">
         	 Position Held
          </fo:block>
          </fo:table-cell>
          <fo:table-cell border="0.5pt solid black" padding-top="10px">
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="center">
         	 Is Director / Officer Insurance in Force?
          </fo:block>
          </fo:table-cell>
          <fo:table-cell padding-bottom="5px" padding-top="15px" border="0.5pt solid black">
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="center">
         	 Percent Equity Interest
          </fo:block>
          </fo:table-cell>
          <fo:table-cell padding-bottom="10px" padding-top="25px" border="0.5pt solid black">
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="center">
         	 Firm Services
          </fo:block>
          </fo:table-cell>
          <fo:table-cell padding-bottom="5px" padding-top="5px" border="0.5pt solid black">
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="center">
         	 Does the Individual Listed Perform These Services?
          </fo:block>
          </fo:table-cell>
          </fo:table-row>
		  <xsl:for-each select="response/GENERAL_LIST_04/data">
          <fo:table-row>
          <fo:table-cell padding-bottom="5px" padding-top="5px" border="0.5pt solid black" padding-left="2mm">
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="center">
         	 <xsl:value-of select="ClientNameOsi"></xsl:value-of>
          </fo:block>
          </fo:table-cell>
          <fo:table-cell padding-bottom="5px" padding-top="5px" border="0.5pt solid black" padding-left="2mm">
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="center">
         	 <xsl:value-of select="EntityNameOsi"></xsl:value-of>
          </fo:block>
          </fo:table-cell>
          <fo:table-cell padding-bottom="5px" padding-top="5px" border="0.5pt solid black" padding-left="2mm">
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="center">
         	 <xsl:value-of select="ClientIndustryDesc"></xsl:value-of>
          </fo:block>
          </fo:table-cell>
          <fo:table-cell padding-bottom="5px" padding-top="5px" border="0.5pt solid black" padding-left="2mm">
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="center">
         	<xsl:value-of select="PositionHeldOsi"></xsl:value-of>
          </fo:block>
          </fo:table-cell>
          <fo:table-cell padding-bottom="5px" padding-top="5px" border="0.5pt solid black" padding-left="2mm">
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left" >
                      	<xsl:choose>
                            <xsl:when test="DOInsuranceOsi = 'Y'">
                            Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
                            </xsl:when>
                            <xsl:when test="DOInsuranceOsi = 'N'">
                            Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline>
                            </xsl:when>
                            <xsl:otherwise>
                             Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
                            
                            </xsl:otherwise>
                           </xsl:choose>
                    </fo:block>
          </fo:table-cell>
          <fo:table-cell padding-bottom="5px" padding-top="5px" border="0.5pt solid black" padding-left="2mm">
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="center">
         	<xsl:value-of select="PercentEquityInterestofOsi"></xsl:value-of>%
          </fo:block>
          </fo:table-cell>
          <fo:table-cell padding-bottom="5px" padding-top="5px" border="0.5pt solid black" padding-left="2mm">
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="center">
         	 <xsl:value-of select="ServiceIndustryDesc"></xsl:value-of>
          </fo:block>
          </fo:table-cell>
          <fo:table-cell padding-bottom="5px" padding-top="5px" border="0.5pt solid black" padding-left="2mm">
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left" >
                      	<xsl:choose>
                            <xsl:when test="IndividualListedServicesOsi = 'Y'">
                            Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
                            </xsl:when>
                            <xsl:when test="IndividualListedServicesOsi = 'N'">
                            Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline>
                            </xsl:when>
                            <xsl:otherwise>
                             Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
                            
                            </xsl:otherwise>
                           </xsl:choose>
                    </fo:block>
          </fo:table-cell>
          </fo:table-row>
		  </xsl:for-each> 
          </fo:table-body>
          </fo:table>
          </fo:block>
          </fo:table-cell>
          </fo:table-row>
          </xsl:if>
          </fo:table-body>
          </fo:table>
          </fo:block>
          <fo:block>
            <fo:table margin-top="5mm" border="1pt solid black" background-color="LightGray ">
              <fo:table-column></fo:table-column>
              <fo:table-body>
                <fo:table-row>
                  <fo:table-cell>
                    <fo:block margin-top="1mm" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" font-weight="bold" font-size="10px">&#160;&#160;NATURE OF PRACTICE </fo:block>
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
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          	<fo:table>
            <fo:table-column column-width="0.3in"/>
            <fo:table-column column-width="4in"/>
            <fo:table-column column-width="3.3in"/>
            <fo:table-body>
          	<fo:table-row>
          	<fo:table-cell padding-top="10px">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          	14.
	          </fo:block>
	        </fo:table-cell>
	        <fo:table-cell number-columns-spanned="2" padding-left="2mm" padding-top="10px">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          	What percentage of gross annual revenue comes from each Area of Practice listed below?
	          </fo:block>
            </fo:table-cell>
          	</fo:table-row>
          	<fo:table-row>
          	<fo:table-cell number-columns-spanned="3" padding-top="5px">
          	<fo:block>
	          <fo:table border="0.5pt solid black">
	          <fo:table-column column-width="71mm"/>
	          <fo:table-column column-width="27mm"/>
	          <fo:table-column column-width="71mm"/>
	          <fo:table-column column-width="27mm"/>
	          <fo:table-body>
	          <fo:table-row>
	          <fo:table-cell padding-top="5px" border="1pt solid black">
	          <fo:block font-weight="bold" font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="center">
	          SERVICE AREA
	          </fo:block>
	          </fo:table-cell>
	          <fo:table-cell border="1pt solid black">
	          <fo:block font-weight="bold" font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="center">
	          % OF REVENUE
	          </fo:block>
	          </fo:table-cell>
	          <fo:table-cell padding-top="5px" border="1pt solid black">
	          <fo:block font-weight="bold" font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="center">
	          SERVICE AREA
	          </fo:block>
	          </fo:table-cell>
	          <fo:table-cell border="1pt solid black">
	          <fo:block font-weight="bold" font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="center">
	          % OF REVENUE
	          </fo:block>
	          </fo:table-cell>
	          </fo:table-row>
	          <fo:table-row>
	          <fo:table-cell padding-left="2mm" padding-top="5px" border="1pt solid black" number-columns-spanned="2" background-color="LightGray">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          Tax
	          </fo:block>
	          </fo:table-cell>
	          <fo:table-cell padding-left="2mm" padding-top="5px" border="1pt solid black" number-columns-spanned="2" background-color="LightGray">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          Consulting
	          </fo:block>
	          </fo:table-cell>
	          </fo:table-row>
	          <fo:table-row>
	          <fo:table-cell padding-bottom="2px" padding-top="2px" border="1pt solid black" padding-left="6mm">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          A.&#160; Business Tax Services
	          </fo:block>
	          </fo:table-cell>
	          <fo:table-cell padding-bottom="2px" padding-top="2px" border="1pt solid black" padding-left="6mm">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	           <xsl:value-of select="response/AOP_PercentageValue_1"/> %
	          </fo:block>
	          </fo:table-cell>
	          <fo:table-cell padding-bottom="2px" padding-top="2px" border="1pt solid black" padding-left="6mm">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          K.&#160; Litigation Support
	          </fo:block>
	          </fo:table-cell>
	          <fo:table-cell padding-bottom="2px" padding-top="2px" border="1pt solid black" padding-left="6mm">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          	<xsl:value-of select="response/AOP_PercentageValue_11"/> %
	          </fo:block>
	          </fo:table-cell>
	          </fo:table-row>
	          <fo:table-row>
	          <fo:table-cell padding-bottom="2px" padding-top="2px" border="1pt solid black" padding-left="6mm">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          B.&#160; Estate Tax Services
	          </fo:block>
	          </fo:table-cell>
	          <fo:table-cell padding-bottom="2px" padding-top="2px" border="1pt solid black" padding-left="6mm">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	           <xsl:value-of select="response/AOP_PercentageValue_2"/> %
	          </fo:block>
	          </fo:table-cell>
	          <fo:table-cell padding-bottom="2px" padding-top="2px" border="1pt solid black" padding-left="6mm">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          L.&#160; General Business Advice
	          </fo:block>
	          </fo:table-cell>
	          <fo:table-cell padding-bottom="2px" padding-top="2px" border="1pt solid black" padding-left="6mm">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          	<xsl:value-of select="response/AOP_PercentageValue_22"/> %
	          </fo:block>
	          </fo:table-cell>
	          </fo:table-row>
	          <fo:table-row>
	          <fo:table-cell padding-bottom="2px" padding-top="2px" border="1pt solid black" padding-left="6mm">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          C.&#160; Individual Tax Services
	          </fo:block>
	          </fo:table-cell>
	          <fo:table-cell padding-bottom="2px" padding-top="2px" border="1pt solid black" padding-left="6mm">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	           <xsl:value-of select="response/AOP_PercentageValue_3"/> %
	          </fo:block>
	          </fo:table-cell>
	          <fo:table-cell padding-bottom="2px" padding-top="2px" border="1pt solid black" padding-left="6mm">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          M.&#160; Other Consulting<fo:inline vertical-align="super" font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;">(4)</fo:inline>
	          </fo:block>
	          </fo:table-cell>
	          <fo:table-cell padding-bottom="2px" padding-top="2px" border="1pt solid black" padding-left="6mm">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          	<xsl:value-of select="response/AOP_PercentageValue_12"/> %
	          </fo:block>
	          </fo:table-cell>
	          </fo:table-row>
	          <fo:table-row>
	          <fo:table-cell padding-left="2mm" padding-top="5px" border="1pt solid black" number-columns-spanned="2" background-color="LightGray">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          	Accounting / Bookkeeping
	          </fo:block>
	          </fo:table-cell>
	          <fo:table-cell number-columns-spanned="2" border="1pt solid black">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          	<fo:table>
	          	<fo:table-column column-width="49mm"/>
	          	<fo:table-column column-width="49mm"/>
	          	<fo:table-body>
	          	<fo:table-row>
	          	<fo:table-cell border="0.5pt solid black" padding-top="5px" padding-left="2mm">
	          	<fo:block>
	          	<fo:inline vertical-align="super">(4)</fo:inline> Please describe:
	          	</fo:block>
	          	</fo:table-cell>
	          	<fo:table-cell>
	          	<fo:block>
				<xsl:value-of select="response/AOP_CommentDesc_12"/>
	          	</fo:block>
	          	</fo:table-cell>
	          	</fo:table-row>
	          	</fo:table-body>
	          	</fo:table>
	          </fo:block>
	          </fo:table-cell>
	          </fo:table-row>
	          <fo:table-row>
	          <fo:table-cell padding-bottom="2px" padding-top="2px" border="1pt solid black" padding-left="6mm">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          D.&#160; Bookkeeping / Write-Up
	          </fo:block>
	          </fo:table-cell>
	          <fo:table-cell padding-bottom="2px" padding-top="2px" border="1pt solid black" padding-left="6mm">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	           <xsl:value-of select="response/AOP_PercentageValue_4"/> %
	          </fo:block>
	          </fo:table-cell>
	          <fo:table-cell number-columns-spanned="2" border="1pt solid black">
	          <fo:block></fo:block>
	          </fo:table-cell>
	          </fo:table-row>
	          <fo:table-row>
	          <fo:table-cell padding-left="2mm" padding-top="10px" border="1pt solid black" number-columns-spanned="2" background-color="LightGray">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          	Attestation
	          </fo:block>
	          </fo:table-cell>
	          <fo:table-cell padding-bottom="2px" padding-top="2px" border="1pt solid black" padding-left="6mm">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          N.&#160; Management Consulting / Business &#160;&#160;&#160;&#160;&#160;&#160;Planning
	          </fo:block>
	          </fo:table-cell>
	          <fo:table-cell padding-bottom="2px" padding-top="2px" border="1pt solid black" padding-left="6mm">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	           <xsl:value-of select="response/AOP_PercentageValue_13"/> %
	          </fo:block>
	          </fo:table-cell>
	          </fo:table-row>
	          <fo:table-row>
	          <fo:table-cell padding-bottom="2px" padding-top="2px" border="1pt solid black" padding-left="6mm">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          E.&#160; Audit: Publicly-held clients<fo:inline vertical-align="super">(1)</fo:inline>
	          </fo:block>
	          </fo:table-cell>
	          <fo:table-cell padding-bottom="2px" padding-top="2px" border="1pt solid black" padding-left="6mm">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	           <xsl:value-of select="response/AOP_PercentageValue_5"/> %
	          </fo:block>
	          </fo:table-cell>
	          <fo:table-cell padding-bottom="2px" padding-top="2px" border="1pt solid black" padding-left="6mm">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          O.&#160; Computer Related Services<fo:inline vertical-align="super">(5)</fo:inline>
	          </fo:block>
	          </fo:table-cell>
	          <fo:table-cell padding-bottom="2px" padding-top="2px" border="1pt solid black" padding-left="6mm">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          	<xsl:value-of select="response/AOP_PercentageValue_14"/> %
	          </fo:block>
	          </fo:table-cell>
	          </fo:table-row>
	          <fo:table-row>
	          <fo:table-cell padding-bottom="2px" padding-top="2px" border="1pt solid black" padding-left="6mm">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          F.&#160; Audit: Non-public clients<fo:inline vertical-align="super">(1)</fo:inline>
	          </fo:block>
	          </fo:table-cell>
	          <fo:table-cell padding-bottom="2px" padding-top="2px" border="1pt solid black" padding-left="6mm">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	           <xsl:value-of select="response/AOP_PercentageValue_6"/> %
	          </fo:block>
	          </fo:table-cell>
	          <fo:table-cell padding-bottom="2px" padding-top="2px" border="1pt solid black" padding-left="2mm" number-columns-spanned="2">
	          <fo:block font-size="7.5px" font-style="italic" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	           <fo:inline vertical-align="super">(5)</fo:inline> Complete the Computer Related Services Supplement if an entry in O above
	          </fo:block>
	          </fo:table-cell>
	          </fo:table-row>
	          <fo:table-row>
	          <fo:table-cell padding-bottom="2px" padding-top="2px" border="1pt solid black" padding-left="2mm" number-columns-spanned="2">
	          <fo:block font-size="7.5px" font-style="italic" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	           <fo:inline vertical-align="super">(1)</fo:inline> Complete the Audit Supplement if an entry in E or F above
	          </fo:block>
	          </fo:table-cell>
	          <fo:table-cell padding-bottom="2px" padding-top="2px" border="1pt solid black" padding-left="6mm">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          P.&#160; Business Valuation
	          </fo:block>
	          </fo:table-cell>
	          <fo:table-cell padding-bottom="2px" padding-top="2px" border="1pt solid black" padding-left="6mm">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	           <xsl:value-of select="response/AOP_PercentageValue_15"/> %
	          </fo:block>
	          </fo:table-cell>
	          </fo:table-row>
	          <fo:table-row>
	          <fo:table-cell padding-bottom="2px" padding-top="2px" border="1pt solid black" padding-left="6mm">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          G.&#160; Review
	          </fo:block>
	          </fo:table-cell>
	          <fo:table-cell padding-bottom="2px" padding-top="2px" border="1pt solid black" padding-left="6mm">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	           <xsl:value-of select="response/AOP_PercentageValue_7"/> %
	          </fo:block>
	          </fo:table-cell>
	          <fo:table-cell padding-bottom="2px" padding-top="2px" border="1pt solid black" padding-left="6mm">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          Q.&#160; Forecasts / Projections
	          </fo:block>
	          </fo:table-cell>
	          <fo:table-cell padding-bottom="2px" padding-top="2px" border="1pt solid black" padding-left="6mm">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          	<xsl:value-of select="response/AOP_PercentageValue_16"/> %
	          </fo:block>
	          </fo:table-cell>
	          </fo:table-row>
	          <fo:table-row>
	          <fo:table-cell padding-bottom="2px" padding-top="2px" border="1pt solid black" padding-left="6mm">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          H.&#160; Compilation
	          </fo:block>
	          </fo:table-cell>
	          <fo:table-cell padding-bottom="2px" padding-top="2px" border="1pt solid black" padding-left="6mm">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	           <xsl:value-of select="response/AOP_PercentageValue_8"/> %
	          </fo:block>
	          </fo:table-cell>
	          <fo:table-cell padding-bottom="2px" padding-top="2px" border="1pt solid black" padding-left="6mm">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          R.&#160; Mergers / Acquisitions
	          </fo:block>
	          </fo:table-cell>
	          <fo:table-cell padding-bottom="2px" padding-top="2px" border="1pt solid black" padding-left="6mm">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          	<xsl:value-of select="response/AOP_PercentageValue_17"/> %
	          </fo:block>
	          </fo:table-cell>
	          </fo:table-row>
	          <fo:table-row>
	          <fo:table-cell padding-bottom="2px" padding-top="2px" border="1pt solid black" padding-left="6mm">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          I.&#160; Agreed upon Procedures<fo:inline vertical-align="super">(2)</fo:inline>
	          </fo:block>
	          </fo:table-cell>
	          <fo:table-cell padding-bottom="2px" padding-top="2px" border="1pt solid black" padding-left="6mm">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          	<xsl:value-of select="response/AOP_PercentageValue_9"/> %
	          </fo:block>
	          </fo:table-cell>
	          <fo:table-cell padding-left="2mm" padding-top="5px" border="1pt solid black" number-columns-spanned="2" background-color="LightGray">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          	Special Services
	          </fo:block>
	          </fo:table-cell>
	          </fo:table-row>
	          <fo:table-row>
	          <fo:table-cell number-columns-spanned="2" border="1pt solid black">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          	<fo:table>
	          	<fo:table-column column-width="49mm"/>
	          	<fo:table-column column-width="49mm"/>
	          	<fo:table-body>
	          	<fo:table-row>
	          	<fo:table-cell border="0.5pt solid black" padding-top="5px" padding-left="2mm">
	          	<fo:block>
	          	<fo:inline vertical-align="super">(2)</fo:inline> Please describe:
	          	</fo:block>
	          	</fo:table-cell>
	          	<fo:table-cell>
	          	<fo:block>
				<xsl:value-of select="response/AOP_CommentDesc_9"/>
	          	</fo:block>
	          	</fo:table-cell>
	          	</fo:table-row>
	          	</fo:table-body>
	          	</fo:table>
	          </fo:block>
	          </fo:table-cell>
	          <fo:table-cell padding-bottom="2px" padding-top="2px" border="1pt solid black" padding-left="6mm">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          S.&#160; Business Management<fo:inline vertical-align="super">(6)</fo:inline>
	          </fo:block>
	          </fo:table-cell>
	          <fo:table-cell padding-bottom="2px" padding-top="2px" border="1pt solid black" padding-left="6mm">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          	<xsl:value-of select="response/AOP_PercentageValue_18"/> %
	          </fo:block>
	          </fo:table-cell>
	          </fo:table-row>
	          <fo:table-row>
	          <fo:table-cell padding-bottom="2px" padding-top="2px" border="1pt solid black" padding-left="6mm" number-columns-spanned="2">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          </fo:block>
	          </fo:table-cell>
	          <fo:table-cell padding-bottom="2px" padding-top="2px" border="1pt solid black" padding-left="2mm" number-columns-spanned="2">
	          <fo:block font-size="7.5px" font-style="italic" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	           <fo:inline vertical-align="super">(6)</fo:inline> Complete the Business Management Supplement if an entry in S above
	          </fo:block>
	          </fo:table-cell>
	          </fo:table-row>
	          <fo:table-row>
	          <fo:table-cell padding-bottom="2px" padding-top="2px" border="1pt solid black" padding-left="6mm">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          J.&#160; Other Assurance Services<fo:inline vertical-align="super">(3)</fo:inline>
	          </fo:block>
	          </fo:table-cell>
	          <fo:table-cell padding-bottom="2px" padding-top="2px" border="1pt solid black" padding-left="6mm">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	           <xsl:value-of select="response/AOP_PercentageValue_10"/> %
	          </fo:block>
	          </fo:table-cell>
	          <fo:table-cell padding-bottom="2px" padding-top="2px" border="1pt solid black" padding-left="6mm">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          T.&#160; SEC Work other than Audit
	          </fo:block>
	          </fo:table-cell>
	          <fo:table-cell padding-bottom="2px" padding-top="2px" border="1pt solid black" padding-left="6mm">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          	<xsl:value-of select="response/AOP_PercentageValue_21"/> %
	          </fo:block>
	          </fo:table-cell>
	          </fo:table-row>
	          <fo:table-row>
	          <fo:table-cell number-columns-spanned="2" border="1pt solid black">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          	<fo:table>
	          	<fo:table-column column-width="49mm"/>
	          	<fo:table-column column-width="49mm"/>
	          	<fo:table-body>
	          	<fo:table-row>
	          	<fo:table-cell border="0.5pt solid black" padding-top="5px" padding-left="2mm">
	          	<fo:block>
	          	<fo:inline vertical-align="super">(3)</fo:inline> Please describe:
	          	</fo:block>
	          	</fo:table-cell>
	          	<fo:table-cell>
	          	<fo:block>
				<xsl:value-of select="response/AOP_CommentDesc_10"/>
	          	</fo:block>
	          	</fo:table-cell>
	          	</fo:table-row>
	          	</fo:table-body>
	          	</fo:table>
	          </fo:block>
	          </fo:table-cell>
	          <fo:table-cell padding-bottom="2px" padding-top="2px" border="1pt solid black" padding-left="6mm">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          U.&#160; Investment / Financial Planning<fo:inline vertical-align="super">(7)</fo:inline>
	          </fo:block>
	          </fo:table-cell>
	          <fo:table-cell padding-bottom="2px" padding-top="2px" border="1pt solid black" padding-left="6mm">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          	<xsl:value-of select="response/AOP_PercentageValue_19"/> %
	          </fo:block>
	          </fo:table-cell>
	          </fo:table-row>
	          <fo:table-row>
	          <fo:table-cell number-columns-spanned="2" padding-bottom="2px" padding-top="2px" border="1pt solid black" padding-left="6mm">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          
	          </fo:block>
	          </fo:table-cell>
	          <fo:table-cell padding-bottom="2px" padding-top="2px" border="1pt solid black" padding-left="2mm" number-columns-spanned="2">
	          <fo:block font-size="7.5px" font-style="italic" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          <fo:inline vertical-align="super">(7)</fo:inline> Complete the Investment/Financial Planning Supplement if entry in U above
	          </fo:block>
	          </fo:table-cell>
	          </fo:table-row>
	          <fo:table-row>
	          <fo:table-cell padding-bottom="2px" padding-top="2px" border="1pt solid black" padding-left="2mm" number-columns-spanned="2">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          </fo:block>
	          </fo:table-cell>
	          <fo:table-cell padding-bottom="2px" padding-top="2px" border="1pt solid black" padding-left="6mm">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          V.&#160; ERISA / Pension Plans
	          </fo:block>
	          </fo:table-cell>
	          <fo:table-cell padding-bottom="2px" padding-top="2px" border="1pt solid black" padding-left="6mm">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          	<xsl:value-of select="response/AOP_PercentageValue_23"/> %
	          </fo:block>
	          </fo:table-cell>
	          </fo:table-row>
	          <fo:table-row>
	          <fo:table-cell padding-bottom="2px" padding-top="2px" border="1pt solid black" padding-left="2mm" number-columns-spanned="2">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          </fo:block>
	          </fo:table-cell>
	          <fo:table-cell padding-bottom="2px" padding-top="2px" border="1pt solid black" padding-left="6mm">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          W.&#160; Trustee Services
	          </fo:block>
	          </fo:table-cell>
	          <fo:table-cell padding-bottom="2px" padding-top="2px" border="1pt solid black" padding-left="6mm">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          	<xsl:value-of select="response/AOP_PercentageValue_24"/> %
	          </fo:block>
	          </fo:table-cell>
	          </fo:table-row>
	          <fo:table-row>
	          <fo:table-cell padding-bottom="2px" padding-top="2px" border="1pt solid black" padding-left="2mm" number-columns-spanned="2">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          </fo:block>
	          </fo:table-cell>
	          <fo:table-cell padding-bottom="2px" padding-top="2px" border="1pt solid black" padding-left="6mm">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          X.&#160; Other(8)
	          </fo:block>
	          </fo:table-cell>
	          <fo:table-cell padding-bottom="2px" padding-top="2px" border="1pt solid black" padding-left="6mm">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          	<xsl:value-of select="response/AOP_PercentageValue_20"/> %
	          </fo:block>
	          </fo:table-cell>
	          </fo:table-row>
	          <fo:table-row>
	          <fo:table-cell padding-bottom="2px" padding-top="2px" border="1pt solid black" padding-left="2mm" number-columns-spanned="2">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          </fo:block>
	          </fo:table-cell>
	          <fo:table-cell number-columns-spanned="2" border="1pt solid black">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          	<fo:table>
	          	<fo:table-column column-width="49mm"/>
	          	<fo:table-column column-width="49mm"/>
	          	<fo:table-body>
	          	<fo:table-row>
	          	<fo:table-cell border="0.5pt solid black" padding-top="5px" padding-left="2mm">
	          	<fo:block>
	          	<fo:inline vertical-align="super">(8)</fo:inline> Please describe:
	          	</fo:block>
	          	</fo:table-cell>
	          	<fo:table-cell>
	          	<fo:block>
				<xsl:value-of select="response/AOP_CommentDesc_20"/>
	          	</fo:block>
	          	</fo:table-cell>
	          	</fo:table-row>
	          	</fo:table-body>
	          	</fo:table>
	          </fo:block>
	          </fo:table-cell>
	          </fo:table-row>
	          <fo:table-row>
	          <fo:table-cell padding-bottom="2px" padding-top="2px" border="1pt solid black" padding-left="2mm" number-columns-spanned="2">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          &#160;
	          </fo:block>
	          </fo:table-cell>
	          <fo:table-cell padding-bottom="2px" padding-top="2px" border="1pt solid black" padding-left="2mm" number-columns-spanned="2">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          &#160;
	          </fo:block>
	          </fo:table-cell>
	          </fo:table-row>
	          <fo:table-row>
	          <fo:table-cell padding-bottom="2px" padding-top="2px" border="1pt solid black" padding-left="2mm" number-columns-spanned="2">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          </fo:block>
	          </fo:table-cell>
	          <fo:table-cell padding-bottom="2px" padding-top="2px" border="1pt solid black" padding-left="2mm">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" font-style="italic" font-weight="bold" text-align="left">
	          Total of all items must equal 100%:
	          </fo:block>
	          </fo:table-cell>
	          <fo:table-cell padding-bottom="2px" padding-top="2px" border="1pt solid black" padding-left="6mm">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          	<xsl:value-of select="response/aop_total"/> %
	          </fo:block>
	          </fo:table-cell>
	          </fo:table-row>
	          </fo:table-body>
	          </fo:table>
	          </fo:block>
            </fo:table-cell>
          	</fo:table-row>
          	<fo:table-row>
          <fo:table-cell padding-top="10px">
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          	15.
          </fo:block>
          </fo:table-cell>
          <fo:table-cell number-columns-spanned="2" padding-left="2mm">
          <fo:block>
          <fo:table>
          <fo:table-column column-width="155mm"/>
          <fo:table-column column-width="25mm"/>
          <fo:table-body>
          <fo:table-row>
          <fo:table-cell padding-top="10px">
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          	In the past three years have you initiated lawsuits or arbitration procedures to enforce the  
          </fo:block>
          </fo:table-cell>
          </fo:table-row>
          <fo:table-row>
          <fo:table-cell>
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          	collection of unpaid fees of any client?
          </fo:block>
          </fo:table-cell>
          <fo:table-cell>
		  <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left" >
                      	<xsl:choose>
                            <xsl:when test="response/FIRM_FREEFORM_01/data/IsSuitInstituted = 'Y'">
                            Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
                            </xsl:when>
                            <xsl:when test="response/FIRM_FREEFORM_01/data/IsSuitInstituted = 'N'">
                            Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline>
                            </xsl:when>
                            <xsl:otherwise>
                             Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
                            
                            </xsl:otherwise>
                           </xsl:choose>
                    </fo:block>
		  </fo:table-cell>
          </fo:table-row>
          <fo:table-row>
          <fo:table-cell>
          <fo:block font-weight="bold" font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" font-style="italic" text-align="left">
          	If Yes, please complete the FEE SUIT SUPPLEMENT.
          </fo:block>
          </fo:table-cell>
          </fo:table-row>
          </fo:table-body>
          </fo:table>
          </fo:block>
          </fo:table-cell>
          </fo:table-row>
          <fo:table-row>
          <fo:table-cell padding-top="10px">
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          	16.
          </fo:block>
          </fo:table-cell>
          <fo:table-cell number-columns-spanned="2" padding-left="2mm">
          <fo:block>
          <fo:table>
          <fo:table-column column-width="155mm"/>
          <fo:table-column column-width="25mm"/>
          <fo:table-body>
          <fo:table-row>
          <fo:table-cell padding-top="10px" number-columns-spanned="2">
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          	After inquiry of all owners, partners, officers and other professional staff of the Firm, within the past five years have any past or present personnel: 
          </fo:block>
          </fo:table-cell>
          </fo:table-row>
          <fo:table-row>
          <fo:table-cell padding-top="10px">
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          <fo:list-block>
          <fo:list-item>
          <fo:list-item-label>
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          a.
          </fo:block>
          </fo:list-item-label>
          <fo:list-item-body start-indent="7mm">
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          been the subject of any regulatory investigation or inquiry; suspended from practice; or charged, indicted or been convicted of any criminal charge?
          </fo:block>
          </fo:list-item-body>
          </fo:list-item>
          </fo:list-block>
          </fo:block>
          </fo:table-cell>
          <fo:table-cell padding-top="20px">
		  <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left" >
                      	<xsl:choose>
                            <xsl:when test="response/FIRM_FREEFORM_01/data/IsAnyRegulatoryInquiry = 'Y'">
                            Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
                            </xsl:when>
                            <xsl:when test="response/FIRM_FREEFORM_01/data/IsAnyRegulatoryInquiry = 'N'">
                            Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline>
                            </xsl:when>
                            <xsl:otherwise>
                             Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
                            
                            </xsl:otherwise>
                           </xsl:choose>
                    </fo:block>
		  </fo:table-cell>
          </fo:table-row>
          <fo:table-row>
          <fo:table-cell padding-top="10px" padding-left="7mm" number-columns-spanned="2">
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" font-style="italic" text-align="left">
          	If Yes, please provide details and dates:
          </fo:block>
           <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" border-bottom-width="0.9pt" border-bottom-style="solid" border-bottom-color="black">
                      <xsl:value-of select="response/FIRM_FREEFORM_01/data/RegulatoryInquiryExaplain"></xsl:value-of>

                    </fo:block>
          </fo:table-cell>
          </fo:table-row>
          <fo:table-row>
          <fo:table-cell padding-left="7mm">
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" font-style="italic" text-align="left">
          	&#160;
          </fo:block>
          </fo:table-cell>
          </fo:table-row>
          <fo:table-row>
          <fo:table-cell padding-top="10px">
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          <fo:list-block>
          <fo:list-item>
          <fo:list-item-label>
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          b.
          </fo:block>
          </fo:list-item-label>
          <fo:list-item-body start-indent="7mm">
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          know of any professional liability claims made against the firm, its affiliates, its personnel?
          </fo:block>
          </fo:list-item-body>
          </fo:list-item>
          </fo:list-block>
          </fo:block>
          </fo:table-cell>
          <fo:table-cell padding-top="20px">
		  <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left" >
                      	<xsl:choose>
                            <xsl:when test="response/FIRM_FREEFORM_01/data/IsAwarenessOfAnyProfLiability = 'Y'">
                            Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
                            </xsl:when>
                            <xsl:when test="response/FIRM_FREEFORM_01/data/IsAwarenessOfAnyProfLiability = 'N'">
                            Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline>
                            </xsl:when>
                            <xsl:otherwise>
                             Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
                            
                            </xsl:otherwise>
                           </xsl:choose>
                    </fo:block>
		  </fo:table-cell>
          </fo:table-row>
          <fo:table-row>
          <fo:table-cell padding-top="10px">
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          <fo:list-block>
          <fo:list-item>
          <fo:list-item-label>
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          c.
          </fo:block>
          </fo:list-item-label>
          <fo:list-item-body start-indent="7mm">
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          become aware of any act, omission or fee dispute which might become the basis of a claim against the firm or its personnel?
          </fo:block>
          </fo:list-item-body>
          </fo:list-item>
          </fo:list-block>
          </fo:block>
          </fo:table-cell>
          <fo:table-cell padding-top="20px">
		  <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left" >
                      	<xsl:choose>
                            <xsl:when test="response/FIRM_FREEFORM_01/data/IsAwarenessOfAnyProfLiabilitySuitAgainst = 'Y'">
                            Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
                            </xsl:when>
                            <xsl:when test="response/FIRM_FREEFORM_01/data/IsAwarenessOfAnyProfLiabilitySuitAgainst = 'N'">
                            Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline>
                            </xsl:when>
                            <xsl:otherwise>
                             Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
                            
                            </xsl:otherwise>
                           </xsl:choose>
                    </fo:block>
		  </fo:table-cell>
          </fo:table-row>
          <fo:table-row>
          <fo:table-cell padding-top="10px" padding-left="7mm" number-columns-spanned="2">
          <fo:block font-weight="bold" font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" font-style="italic" text-align="left">
          	If Yes to 16 b or c above, please complete the CLAIM SUPPLEMENT for each claim or potential claim.
          </fo:block>
          </fo:table-cell>
          </fo:table-row>
          </fo:table-body>
          </fo:table>
          </fo:block>
          </fo:table-cell>
          </fo:table-row>
          <fo:table-row>
          <fo:table-cell padding-top="10px">
          <fo:block></fo:block>
          </fo:table-cell>
          <fo:table-cell padding-top="10px" number-columns-spanned="2" padding-left="2mm">
          <fo:block font-size="10.5px" font-weight="bold" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          	NOTE: THE POLICY FOR WHICH THIS APPLICATION IS BEING MADE SHALL NOT APPLY TO ANY INCIDENTS OR CLAIMS DETAILED OR WHICH SHOULD HAVE BEEN DETAILED IN QUESTION 16 a, b or c ABOVE. 
          </fo:block>
          </fo:table-cell>
          </fo:table-row>
          	</fo:table-body>
          	</fo:table>
          	</fo:block>
          	<fo:block space-before="4mm">
            <fo:table margin-top="1mm" border="1pt solid black" background-color="LightGray ">
              <fo:table-column></fo:table-column>
              <fo:table-body>
                <fo:table-row>
                  <fo:table-cell>
                    <fo:block margin-top="1mm" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" font-weight="bold" font-size="10px">&#160;&#160;PRIOR INSURANCE INFORMATION </fo:block>
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
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          	<fo:table>
            <fo:table-column column-width="0.3in"/>
            <fo:table-column column-width="4in"/>
            <fo:table-column column-width="3.3in"/>
            <fo:table-body>
          	<fo:table-row>
          	<fo:table-cell padding-top="10px">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          	17.
	          </fo:block>
	        </fo:table-cell>
	        <fo:table-cell number-columns-spanned="2" padding-left="2mm">
          <fo:block>
          <fo:table>
          <fo:table-column column-width="155mm"/>
          <fo:table-column column-width="25mm"/>
          <fo:table-body>
          <fo:table-row>
          <fo:table-cell padding-top="10px">
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          	Within the past five years has the firm or its personnel been declined, canceled or non-renewed
          </fo:block>
          </fo:table-cell>
          </fo:table-row>
          <fo:table-row>
          <fo:table-cell>
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          	for professional liability insurance for any reason? <!-- (NOT APPLICABLE IN MISSOURI) -->
          </fo:block>
          </fo:table-cell>
          <fo:table-cell>
		  <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left" >
                      	<xsl:choose>
                            <xsl:when test="response/FIRM_FREEFORM_01/data/IsFirmDeclinedOtherthanNonPayment = 'Y'">
                            Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
                            </xsl:when>
                            <xsl:when test="response/FIRM_FREEFORM_01/data/IsFirmDeclinedOtherthanNonPayment = 'N'">
                            Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline>
                            </xsl:when>
                            <xsl:otherwise>
                             Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
                            
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
	        <fo:table-row>
	        <fo:table-cell padding-top="10px">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          	
	          </fo:block>
	        </fo:table-cell>
          <fo:table-cell number-columns-spanned="2" padding-left="2mm">
          <fo:block>
          <fo:table>
          <fo:table-column column-width="70mm"/>
          <fo:table-column column-width="115mm"/>
          <fo:table-body>
          <fo:table-row>
          <fo:table-cell padding-top="10px">
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" font-style="italic" text-align="left">
          	If Yes, please provide reasons and dates:
          </fo:block>
          </fo:table-cell>
          <fo:table-cell padding-top="10px">
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" border-bottom-width="0.9pt" border-bottom-style="solid" border-bottom-color="black">
                      <xsl:value-of select="response/FIRM_FREEFORM_01/data/ReasonForDecline"></xsl:value-of>

                    </fo:block>
          </fo:table-cell>
          </fo:table-row>
          </fo:table-body>
          </fo:table>
          </fo:block>
          </fo:table-cell>
          </fo:table-row>
          <fo:table-row>
          	<fo:table-cell padding-top="10px">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          	18.
	          </fo:block>
	        </fo:table-cell>
	        <fo:table-cell number-columns-spanned="2" padding-left="2mm">
          <fo:block>
          <fo:table>
          <fo:table-column column-width="155mm"/>
          <fo:table-column column-width="25mm"/>
          <fo:table-body>
          <fo:table-row>
          <fo:table-cell padding-top="10px">
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          	Do you currently carry professional liability insurance?
          </fo:block>
          </fo:table-cell>
          <fo:table-cell padding-top="10px">
		  <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left" >
                      	<xsl:choose>
                            <xsl:when test="response/FIRM_FREEFORM_01/data/IsFirmCarryingProfLiabilityIns = 'Y'">
                            Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
                            </xsl:when>
                            <xsl:when test="response/FIRM_FREEFORM_01/data/IsFirmCarryingProfLiabilityIns = 'N'">
                            Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline>
                            </xsl:when>
                            <xsl:otherwise>
                             Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
                            
                            </xsl:otherwise>
                           </xsl:choose>
                    </fo:block>
		  </fo:table-cell>
          </fo:table-row>
          <xsl:if test="response/FIRM_FREEFORM_01/data/IsFirmCarryingProfLiabilityIns = 'Y'">
          <fo:table-row>
          <fo:table-cell padding-top="5px">
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" font-style="italic" text-align="left">
          	If Yes, please provide the information requested below:
          </fo:block>
          </fo:table-cell>
          </fo:table-row>
          </xsl:if>
          </fo:table-body>
          </fo:table>
          </fo:block>
          </fo:table-cell>
        </fo:table-row>
        <xsl:if test="response/FIRM_FREEFORM_01/data/IsFirmCarryingProfLiabilityIns = 'Y'">
          <fo:table-row>
          <fo:table-cell padding-top="10px">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          	
	          </fo:block>
	        </fo:table-cell>
          <fo:table-cell padding-top="10px" padding-left="2mm">
          <fo:block>
          <fo:table>
          <fo:table-column column-width="54mm"/>
          <fo:table-column column-width="45mm"/>
          <fo:table-column column-width="41mm"/>
          <fo:table-column column-width="45mm"/>
          <fo:table-body>
          <fo:table-row>
          <fo:table-cell padding-top="10px">
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          	Insurance Company <fo:inline font-style="italic">(not broker)</fo:inline>:
          </fo:block>
          </fo:table-cell>
          <fo:table-cell padding-top="10px">
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" border-bottom-width="0.9pt" border-bottom-style="solid" border-bottom-color="black">
                      <xsl:value-of select="response/PROFESSIONALLIABILITYINS_FREEFORM_01/data/InsuranceCompanyNamePross"></xsl:value-of>

                    </fo:block>
          </fo:table-cell>
          <fo:table-cell padding-top="10px" padding-left="2mm">
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          	Policy Expiration Date:
          </fo:block>
          </fo:table-cell>
          <fo:table-cell padding-top="10px">
          <fo:block text-align="center" font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" border-bottom-width="0.9pt" border-bottom-style="solid" border-bottom-color="black">
                      <xsl:value-of select="response/PROFESSIONALLIABILITYINS_FREEFORM_01/data/PolicyExpirationDatePross"></xsl:value-of>

                    </fo:block>
          </fo:table-cell>
          </fo:table-row>
          </fo:table-body>
          </fo:table>
          </fo:block>
          </fo:table-cell>
          </fo:table-row>
          <fo:table-row>
          <fo:table-cell padding-top="10px">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          	
	          </fo:block>
	        </fo:table-cell>
          <fo:table-cell padding-top="10px" padding-left="2mm">
          <fo:block>
          <fo:table>
          <fo:table-column column-width="30mm"/>
          <fo:table-column column-width="38mm"/>
          <fo:table-column column-width="20mm"/>
          <fo:table-column column-width="40mm"/>
          <fo:table-column column-width="20mm"/>
          <fo:table-column column-width="37mm"/>
          <fo:table-body>
          <fo:table-row>
          <fo:table-cell padding-top="10px">
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          	Limits of Liability:
          </fo:block>
          </fo:table-cell>
          <fo:table-cell padding-top="10px" padding-left="1mm">
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" border-bottom-width="0.9pt" border-bottom-style="solid" border-bottom-color="black">
                      $<xsl:value-of select="response/PROFESSIONALLIABILITYINS_FREEFORM_01_Format/data/LimitSequenceProssFormat"></xsl:value-of>
                    </fo:block>
          </fo:table-cell>
          <fo:table-cell padding-top="10px" padding-left="2mm">
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          	Deductible:
          </fo:block>
          </fo:table-cell>
          <fo:table-cell padding-top="10px" padding-left="2mm">
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" border-bottom-width="0.9pt" border-bottom-style="solid" border-bottom-color="black">
                       <xsl:value-of select="response/PROFESSIONALLIABILITYINS_FREEFORM_01_Format/data/DeductibleSequenceProssFormat"></xsl:value-of>

                    </fo:block>
          </fo:table-cell>
          <fo:table-cell padding-top="10px" padding-left="2mm">
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          	Premium:
          </fo:block>
          </fo:table-cell>
          <fo:table-cell padding-top="10px" padding-left="2mm">
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" border-bottom-width="0.9pt" border-bottom-style="solid" border-bottom-color="black">
                     &#160; <xsl:value-of select="response/PROFESSIONALLIABILITYINS_FREEFORM_01_Format/data/ProInsPremiumFormat"></xsl:value-of>

                    </fo:block>
          </fo:table-cell>
          </fo:table-row>
          </fo:table-body>
          </fo:table>
          </fo:block>
          </fo:table-cell>
          </fo:table-row>
          <fo:table-row>
          <fo:table-cell padding-top="10px">
          <fo:block></fo:block>
          </fo:table-cell>
          <fo:table-cell padding-top="20px" padding-left="2mm" number-columns-spanned="2">
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          <xsl:choose>
           <xsl:when test="response/PROFESSIONALLIABILITYINS_FREEFORM_01/data/IsClaimExpLiability = 'Y'">
           <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline> &#160;Defense expenses <fo:inline font-weight="bold">reduce</fo:inline> limits of liability&#160;&#160; or&#160;&#160; <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline> Defense expenses are paid in <fo:inline font-weight="bold">addition</fo:inline> to limits of liability
           </xsl:when>
           <xsl:when test="response/PROFESSIONALLIABILITYINS_FREEFORM_01/data/IsClaimExpLiability = 'N'">
           <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline> &#160;Defense expenses <fo:inline font-weight="bold">reduce</fo:inline> limits of liability&#160;&#160; or&#160;&#160; <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline> Defense expenses are paid in <fo:inline font-weight="bold">addition</fo:inline> to limits of liability
           </xsl:when>
           <xsl:otherwise>
           <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline> &#160;Defense expenses <fo:inline font-weight="bold">reduce</fo:inline> limits of liability&#160;&#160; &#160;&#160; or&#160;&#160; &#160;&#160; <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline> Defense expenses are paid in <fo:inline font-weight="bold">addition</fo:inline> to limits of liability
           </xsl:otherwise>
          </xsl:choose>
          </fo:block>
          </fo:table-cell>
          </fo:table-row>
          <fo:table-row>
          <fo:table-cell padding-top="10px">
          <fo:block></fo:block>
          </fo:table-cell>
          <fo:table-cell padding-top="10px" padding-left="2mm" number-columns-spanned="2">
          <fo:block>
          <fo:table>
          <fo:table-column column-width="145mm"/>
          <fo:table-column column-width="40mm"/>
          <fo:table-body>
          <fo:table-row>
          <fo:table-cell padding-top="10px">
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          Indicate the prior acts date (also known as retroactive date) for your policy:&#160;&#160;&#160;Prior Acts Date:
          </fo:block>
          </fo:table-cell>
          <fo:table-cell padding-top="10px">
          <fo:block text-align="center" font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" border-bottom-width="0.9pt" border-bottom-style="solid" border-bottom-color="black">
             &#160;<xsl:value-of select="response/PROFESSIONALLIABILITYINS_FREEFORM_01/data/PriorActDatePross"></xsl:value-of>
		  </fo:block>
		  <fo:block font-size="8px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" font-style="italic" text-align="center">
	                     MM / DD / YYYY
				 </fo:block>
          </fo:table-cell>
          </fo:table-row>
          </fo:table-body>
          </fo:table>
          </fo:block>
          </fo:table-cell>
          </fo:table-row>
		  
		  <fo:table-row>
          <fo:table-cell padding-top="10px">
          <fo:block></fo:block>
          </fo:table-cell>
          <fo:table-cell padding-top="10px" padding-left="2mm" number-columns-spanned="2">
          <fo:block>
          <fo:table>
          <fo:table-column column-width="160mm"/>
          <fo:table-column column-width="20mm"/>
          <fo:table-body>
          <fo:table-row>
          <fo:table-cell padding-top="10px">
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          If your current policy has Full prior acts, please provide the number of years of continuous insurance:
          </fo:block>
          </fo:table-cell>
          <fo:table-cell padding-top="10px">
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" border-bottom-width="0.9pt" border-bottom-style="solid" border-bottom-color="black">
             &#160;<xsl:value-of select="response/PROFESSIONALLIABILITYINS_FREEFORM_01/data/FirmYear"></xsl:value-of>
		  </fo:block>
          </fo:table-cell>
          </fo:table-row>
          </fo:table-body>
          </fo:table>
          </fo:block>
          </fo:table-cell>
          </fo:table-row>
		  </xsl:if>
          <fo:table-row>
          	<fo:table-cell padding-top="10px">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          	19.
	          </fo:block>
	        </fo:table-cell>
	        <fo:table-cell number-columns-spanned="2" padding-left="2mm">
          <fo:block>
          <fo:table>
          <fo:table-column column-width="155mm"/>
          <fo:table-column column-width="25mm"/>
          <fo:table-body>
          <fo:table-row>
          <fo:table-cell padding-top="10px">
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          	Does the policy above exclude coverage for any predecessor Firms, Firm affiliates, clients, 
          </fo:block>
          </fo:table-cell>
          </fo:table-row>
          <fo:table-row>
          <fo:table-cell>
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          	specific engagements or other circumstances?
          </fo:block>
          </fo:table-cell>
          <fo:table-cell>
		  <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left" >
                      	<xsl:choose>
                            <xsl:when test="response/FIRM_FREEFORM_01/data/IsPolicyExcludesCoverage = 'Y'">
                            Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
                            </xsl:when>
                            <xsl:when test="response/FIRM_FREEFORM_01/data/IsPolicyExcludesCoverage = 'N'">
                            Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline>
                            </xsl:when>
                            <xsl:otherwise>
                             Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
                            
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
        <xsl:if test="response/FIRM_FREEFORM_01/data/IsPolicyExcludesCoverage = 'Y'">
        <fo:table-row>
	        <fo:table-cell padding-top="10px">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          	
	          </fo:block>
	        </fo:table-cell>
          <fo:table-cell number-columns-spanned="2" padding-left="2mm">
          <fo:block>
          <fo:table>
          <fo:table-column column-width="40mm"/>
          <fo:table-column column-width="145mm"/>
          <fo:table-body>
          <fo:table-row>
          <fo:table-cell padding-top="10px">
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" font-style="italic" text-align="left">
          	If Yes, please describe:
          </fo:block>
          </fo:table-cell>
          <fo:table-cell padding-top="10px">
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" border-bottom-width="0.9pt" border-bottom-style="solid" border-bottom-color="black">
                      <xsl:value-of select="response/FIRM_FREEFORM_01/data/RetroFirmName"></xsl:value-of>

                    </fo:block>
          </fo:table-cell>
          </fo:table-row>
          </fo:table-body>
          </fo:table>
          </fo:block>
          </fo:table-cell>
          </fo:table-row>
          </xsl:if>
        </fo:table-body>
        </fo:table>
        </fo:block>
        <fo:block space-before="4mm">
            <fo:table margin-top="1mm" border="1pt solid black" background-color="LightGray ">
              <fo:table-column></fo:table-column>
              <fo:table-body>
                <fo:table-row>
                  <fo:table-cell>
                    <fo:block margin-top="1mm" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" font-weight="bold" font-size="10px">&#160;&#160;COVERAGE SELECTION </fo:block>
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
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          	<fo:table>
            <fo:table-column column-width="0.3in"/>
            <fo:table-column column-width="4in"/>
            <fo:table-column column-width="3.3in"/>
            <fo:table-body>
          	<fo:table-row>
          	<fo:table-cell padding-top="10px" number-columns-spanned="3">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          	Indicate your desired coverage selection:
	          </fo:block>
	        </fo:table-cell>
          	</fo:table-row>
          	<fo:table-row>
          <fo:table-cell padding-top="10px">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          	20.
	          </fo:block>
	        </fo:table-cell>
          <fo:table-cell padding-left="2mm">
          <fo:block>
          <fo:table>
          <fo:table-column column-width="35mm"/>
          <fo:table-column column-width="20mm"/>
          <fo:table-column column-width="50mm"/>
          <fo:table-column column-width="35mm"/>
          <fo:table-column column-width="45mm"/>
          <fo:table-body>
          <fo:table-row>
          <fo:table-cell padding-top="10px">
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          	Limits of Liability:
          </fo:block>
          </fo:table-cell>
          <fo:table-cell padding-top="10px">
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          	Per Claim:
          </fo:block>
          </fo:table-cell>
          <fo:table-cell padding-top="10px" padding-left="2mm">
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" border-bottom-width="0.9pt" border-bottom-style="solid" border-bottom-color="black">
                      $ <xsl:value-of select="response/POLICYCOVERAGE_FREEFORM_01/data/AggregateLimit"></xsl:value-of>
                    </fo:block>
          </fo:table-cell>
          <fo:table-cell padding-top="10px" padding-left="2mm">
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          	Annual Aggregate:
          </fo:block>
          </fo:table-cell>
          <fo:table-cell padding-top="10px" padding-left="2mm">
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" border-bottom-width="0.9pt" border-bottom-style="solid" border-bottom-color="black">
                      $ <xsl:value-of select="response/POLICYCOVERAGE_FREEFORM_01/data/OccuranceLimit"></xsl:value-of>
                    </fo:block>
          </fo:table-cell>
          </fo:table-row>
          </fo:table-body>
          </fo:table>
          </fo:block>
          </fo:table-cell>
          </fo:table-row>
          <fo:table-row>
          <fo:table-cell padding-top="10px">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          	21.
	          </fo:block>
	        </fo:table-cell>
          <fo:table-cell padding-left="2mm">
          <fo:block>
          <fo:table>
          <fo:table-column column-width="35mm"/>
          <fo:table-column column-width="20mm"/>
          <fo:table-column column-width="50mm"/>
          <fo:table-column column-width="35mm"/>
          <fo:table-column column-width="45mm"/>
          <fo:table-body>
          <fo:table-row>
          <fo:table-cell padding-top="10px" number-columns-spanned="2">
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          	Deductible:
          </fo:block>
          </fo:table-cell>
          <fo:table-cell padding-top="10px" padding-left="2mm">
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" border-bottom-width="0.9pt" border-bottom-style="solid" border-bottom-color="black">
                      $ <xsl:value-of select="response/POLICYCOVERAGE_FREEFORM_01/data/DeductibleAmount"></xsl:value-of>
                    </fo:block>
          </fo:table-cell>
          <fo:table-cell padding-top="10px" padding-left="5mm" number-columns-spanned="2">
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          	<xsl:choose>
             <xsl:when test="response/POLICYCOVERAGE_FREEFORM_01/data/IsClaimOptionType = 'N'">
             <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline> &#160;Per Claim &#160;&#160;or&#160;&#160; <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline> &#160;Annual Aggregate
             </xsl:when>
             <xsl:when test="response/POLICYCOVERAGE_FREEFORM_01/data/IsClaimOptionType = 'Y'">
             <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline> &#160;Per Claim &#160;&#160;or&#160;&#160; <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline> &#160;Annual Aggregate
             </xsl:when>
             <xsl:otherwise>
              <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline> &#160;Per Claim &#160;&#160;or&#160;&#160; <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline> &#160;Annual Aggregate
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
          <fo:table-row>
          <fo:table-cell padding-top="10px">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          	22.
	          </fo:block>
	        </fo:table-cell>
          <fo:table-cell padding-left="2mm">
          <fo:block>
          <fo:table>
          <fo:table-column column-width="35mm"/>
          <fo:table-column column-width="20mm"/>
          <fo:table-column column-width="50mm"/>
          <fo:table-column column-width="35mm"/>
          <fo:table-column column-width="45mm"/>
          <fo:table-body>
          <fo:table-row>
          <fo:table-cell padding-top="10px">
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          	Defense Expenses:
          </fo:block>
          </fo:table-cell>
          <fo:table-cell padding-top="10px" padding-left="2mm" number-columns-spanned="4">
          <fo:block>
          <fo:list-block>
          <fo:list-item>
          <fo:list-item-label>
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          	<xsl:choose>
             <xsl:when test="response/POLICYCOVERAGE_FREEFORM_01/data/IsClaimExpensesType = 'N'">
             <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline>
             </xsl:when>
			 <xsl:otherwise>
              <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
			 </xsl:otherwise>
            </xsl:choose>
          </fo:block>
          </fo:list-item-label>
          <fo:list-item-body start-indent="7mm">
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          	Defense expenses reduce limits of liability  or  
          </fo:block>
          </fo:list-item-body>
          </fo:list-item>
          <fo:list-item>
          <fo:list-item-label>
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          	<xsl:choose>
             <xsl:when test="response/POLICYCOVERAGE_FREEFORM_01/data/IsClaimExpensesType = 'Y'">
             <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline>
             </xsl:when>
			 <xsl:otherwise>
              <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
			 </xsl:otherwise>
            </xsl:choose>
          </fo:block>
          </fo:list-item-label>
          <fo:list-item-body start-indent="7mm">
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          	Defense expenses paid in addition to limits of liability
          </fo:block>
          </fo:list-item-body>
          </fo:list-item>
          </fo:list-block>
          </fo:block>
          </fo:table-cell>
          </fo:table-row>
          </fo:table-body>
          </fo:table>
          </fo:block>
          </fo:table-cell>
          </fo:table-row>
       	  </fo:table-body>
       	  </fo:table>
       	  </fo:block>
       	  <fo:block page-break-before="always" />
       	  <fo:block>
          <fo:block space-after="4mm" font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="justify">
			ANY PERSON WHO KNOWINGLY AND WITH INTENT TO DEFRAUD ANY INSURANCE
			COMPANY OR OTHER PERSON FILES AN APPLICATION FOR INSURANCE OR STATEMENT
			OF CLAIM CONTAINING ANY MATERIALLY FALSE INFORMATION OR CONCEALS, FOR
			THE PURPOSE OF MISLEADING, INFORMATION CONCERNING ANY FACT MATERIAL
			THERETO COMMITS A FRAUDULENT INSURANCE ACT, WHICH IS A CRIME AND
			SUBJECTS SUCH PERSON TO CRIMINAL AND CIVIL PENALTIES. (Not applicable
			in AL, AR, CO, DC, FL, KS, KY, LA, MD, ME, NJ, NM, NY, OH, OK, OR, RI,
			TN, VA, VT, WA or WV – see Additional Fraud Notices for these States
			below).
          </fo:block>
          <fo:block space-after="4mm" font-weight="bold" font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="center">
          	ADDITIONAL FRAUD NOTICES
          </fo:block>
          <xsl:choose>
          <xsl:when test="response/ADDRESS_FREEFORM_01/data/StateCode= 'WV' or response/ADDRESS_FREEFORM_01/data/StateCode= 'AL' 
          or response/ADDRESS_FREEFORM_01/data/StateCode= 'AR' or response/ADDRESS_FREEFORM_01/data/StateCode= 'LA'
          or response/ADDRESS_FREEFORM_01/data/StateCode= 'NM' or response/ADDRESS_FREEFORM_01/data/StateCode= 'RI'">
          <fo:block space-after="4mm" font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="justify">
		    <fo:inline font-weight="bold">NOTICE TO ALABAMA, ARKANSAS, LOUISIANA, NEW MEXICO, RHODE ISLAND AND
			WEST VIRGINIA APPLICANTS:</fo:inline> Any person who knowingly presents a false or
			fraudulent claim for payment of a loss or benefit or knowingly presents
			false information in an application for insurance is guilty of a crime
			and may be subject to fines and confinement in prison.
          </fo:block>
          </xsl:when>
          <xsl:when test="response/ADDRESS_FREEFORM_01/data/StateCode= 'CO'">
          <fo:block space-after="4mm" font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="justify">
			<fo:inline font-weight="bold">NOTICE TO COLORADO APPLICANTS:</fo:inline> It is unlawful to knowingly provide
			false, incomplete, or misleading facts or information to an insurance
			company for the purpose of defrauding or attempting to defraud the
			company. Penalties may include imprisonment, fines, denial of
			insurance, and civil damages. Any insurance company or agent of an
			insurance company who knowingly provides false, incomplete, or
			misleading facts or information to a policyholder or claimant for the
			purpose of defrauding or attempting to defraud the policyholder or
			claimant with regard to a settlement or award payable from insurance
			proceeds shall be reported to the Colorado Division of Insurance within
			the Department of Regulatory Agencies.
          </fo:block>
          </xsl:when>
          <xsl:when test="response/ADDRESS_FREEFORM_01/data/StateCode= 'DC'">
          <fo:block space-after="4mm" font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="justify">
			<fo:inline font-weight="bold">NOTICE TO DISTRICT OF COLUMBIA APPLICANTS: WARNING:</fo:inline> It is a crime to
			provide false or misleading information to an insurer for the purpose
			of defrauding the insurer or any other person. Penalties include
			imprisonment and/or fines. In addition, an insurer may deny insurance
			benefits if false information materially related to a claim was
			provided by the applicant.
          </fo:block>
          </xsl:when>
          <xsl:when test="response/ADDRESS_FREEFORM_01/data/StateCode= 'FL'">
          <fo:block space-after="4mm" font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="justify">
			<fo:inline font-weight="bold">NOTICE TO FLORIDA APPLICANTS:</fo:inline> Any person who knowingly and with intent
			to injure, defraud, or deceive any insurer files a statement of claim
			or an application containing any false, incomplete, or misleading
			information is guilty of a felony of the third degree.
          </fo:block>
          </xsl:when>
          <xsl:when test="response/ADDRESS_FREEFORM_01/data/StateCode= 'KS'">
          <fo:block space-after="4mm" font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="justify">
			<fo:inline font-weight="bold">NOTICE TO KANSAS APPLICANTS:</fo:inline> Any person who, knowingly and with intent
			to defraud, presents, causes to be presented or prepares with knowledge
			or belief that it will be presented to or by an insurer, purported
			insurer, broker or any agent thereof, any written, electronic,
			electronic impulse, facsimile, magnetic, oral, or telephonic
			communication or statement as part of, or in support of, an application
			for the issuance of, or the rating of an insurance policy for personal
			or commercial insurance, or a claim for payment or other benefit
			pursuant to an insurance policy for commercial or personal insurance
			which such person knows to contain materially false information
			concerning any fact material thereto; or conceals, for the purpose of
			misleading, information concerning any fact material thereto commits a
			fraudulent insurance act.
          </fo:block>
          </xsl:when>
          <xsl:when test="response/ADDRESS_FREEFORM_01/data/StateCode= 'KY'">
          <fo:block space-after="4mm" font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="justify">
			<fo:inline font-weight="bold">NOTICE TO KENTUCKY APPLICANTS:</fo:inline> Any person who knowingly and with intent
			to defraud any insurance company or other person files an application
			for insurance containing any materially false information or conceals,
			for the purpose of misleading, information concerning any fact material
			thereto commits a fraudulent insurance act, which is a crime.
          </fo:block>
          </xsl:when>
          <xsl:when test="response/ADDRESS_FREEFORM_01/data/StateCode= 'ME'">
          <fo:block space-after="4mm" font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="justify">
			<fo:inline font-weight="bold">NOTICE TO MAINE APPLICANTS:</fo:inline> It is a crime to knowingly provide false,
			incomplete or misleading information to an insurance company for the
			purpose of defrauding the company. Penalties may include imprisonment,
			fines or denial of insurance benefits.
          </fo:block>
          </xsl:when>
          <xsl:when test="response/ADDRESS_FREEFORM_01/data/StateCode= 'MD'">
          <fo:block space-after="4mm" font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="justify">
			<fo:inline font-weight="bold">NOTICE OF UNDERWITING:</fo:inline> Any person who knowingly or willfully
			presents a false or fraudulent claim for payment of a loss or benefit
			or who knowingly or willfully presents false information in an
			application for insurance is guilty of a crime and may be subject to
			fines and confinement in prison.
          </fo:block>
          </xsl:when>
          <xsl:when test="response/ADDRESS_FREEFORM_01/data/StateCode= 'NJ'">
          <fo:block space-after="4mm" font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="justify">
			<fo:inline font-weight="bold">NOTICE TO NEW JERSEY APPLICANTS:</fo:inline> Any person who includes any false or
			misleading information on an application for an insurance policy is
			subject to criminal and civil penalties.
          </fo:block>
          </xsl:when>
          <xsl:when test="response/ADDRESS_FREEFORM_01/data/StateCode= 'NY'">
          <fo:block space-after="4mm" font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="justify">
			<fo:inline font-weight="bold">NOTICE TO NEW YORK APPLICANTS:</fo:inline> Any person who knowingly and with intent
			to defraud any insurance company or other person files an application
			for insurance or statement of claim containing any materially false
			information, or conceals for the purpose of misleading, information
			concerning any fact material thereto, commits a fraudulent insurance
			act, which is a crime, and shall also be subject to a civil penalty not
			to exceed five thousand dollars and the stated value of the claim for
			each such violation.
          </fo:block>
          </xsl:when>
          </xsl:choose>
          </fo:block>
          <fo:block>
          <xsl:choose>
          <xsl:when test="response/ADDRESS_FREEFORM_01/data/StateCode= 'OH'">
          <fo:block space-after="4mm" font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="justify">
			<fo:inline font-weight="bold">NOTICE TO OHIO APPLICANTS:</fo:inline> Any person who, with intent to defraud or
			knowing that he is facilitating a fraud against an insurer, submits an
			application or files a claim containing a false or deceptive statement
			is guilty of insurance fraud.
          </fo:block>
          </xsl:when>
          <xsl:when test="response/ADDRESS_FREEFORM_01/data/StateCode= 'OK'">
          <fo:block space-after="4mm" font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="justify">
			<fo:inline font-weight="bold">NOTICE TO OKLAHOMA APPLICANTS:</fo:inline> WARNING: Any person who knowingly, and
			with intent to injure, defraud or deceive any insurer, makes any claim
			for the proceeds of an insurance policy containing any false,
			incomplete or misleading information is guilty of a felony.
          </fo:block>
          </xsl:when>
          <xsl:when test="response/ADDRESS_FREEFORM_01/data/StateCode= 'OR'">
          <fo:block space-after="4mm" font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="justify">
			<fo:inline font-weight="bold">NOTICE TO OREGON APPLICANTS:</fo:inline> Any person who knowingly and with intent
			to defraud or solicit another to defraud the insurer by submitting an
			application containing a false statement as to any material fact may be
			violating state law.
          </fo:block>
          </xsl:when>
          <xsl:when test="response/ADDRESS_FREEFORM_01/data/StateCode= 'TN' or response/ADDRESS_FREEFORM_01/data/StateCode= 'VA' 
          or response/ADDRESS_FREEFORM_01/data/StateCode= 'WA'">
          <fo:block space-after="4mm" font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="justify">
			<fo:inline font-weight="bold">NOTICE TO TENNESSEE, VIRGINIA AND WASHINGTON APPLICANTS:</fo:inline> It is a crime
			to knowingly provide false, incomplete, or misleading information to an
			insurance company for the purpose of defrauding the company. Penalties
			include imprisonment, fines, and denial of insurance benefits.
          </fo:block>
          </xsl:when>
          <xsl:when test="response/ADDRESS_FREEFORM_01/data/StateCode= 'VT'">
          <fo:block space-after="8mm" font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="justify">
			<fo:inline font-weight="bold">NOTICE TO VERMONT APPLICANTS:</fo:inline> Any person who knowingly presents a false
			statement in an application for insurance may be guilty of a criminal
			offense and subject to penalties under state law.
          </fo:block>
          </xsl:when>
          </xsl:choose>
          <fo:block space-after="4mm" font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="justify">
			Completion and/or signing of this application does not bind the firm to
			purchase, nor the <fo:inline font-weight="bold">Insurer</fo:inline> to provide, any insurance policy; however, no
			policy can be issued unless the application is properly completed,
			signed and dated.
          </fo:block>
          <fo:block space-after="4mm" font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="justify">
			The signatory declares that (s)he is authorized by the firm to sign
			this application on behalf of all prospective <fo:inline font-weight="bold">Insureds</fo:inline> and that to the
			best of his/her knowledge the statements herein are true. The signatory
			agrees that if the information supplied in this application and the
			materials submitted therewith should change between the date this
			application is signed and the effective date of the proposed insurance,
			the signatory shall immediately notify the <fo:inline font-weight="bold">Insurer</fo:inline> of such and shall
			provide the <fo:inline font-weight="bold">Insurer</fo:inline> with information that would complete, update or
			correct the application or materials submitted therewith. <!-- The <fo:inline font-weight="bold">Insurer</fo:inline>
			may withdraw or modify any of the terms or conditions of coverage
			accordingly. -->
          </fo:block>
          <fo:block font-weight="bold" space-after="4mm" font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="justify">
			ALL WRITTEN STATEMENTS, SUPPLEMENTAL APPLICATION AND MATERIALS
			FURNISHED TO THE INSURER IN CONJUNCTION WITH THIS APPLICATION ARE
			INCORPORATED BY REFERENCE INTO THIS APPLICATION AND MADE A PART
			THEREOF, AND DEEMED ATTACHED HERETO.
          </fo:block>
          
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" space-after="4mm" padding-top="15px">
            <fo:table border="0.9pt solid white">
              <fo:table-column column-width="1.5in"/>
              <fo:table-column column-width="2.5in"/>
              <fo:table-column column-width="1.5in"/>
              <fo:table-column column-width="2.0in"/>
              <fo:table-body>
                <fo:table-row>
                  <fo:table-cell display-align="after">
                    <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
                      SIGNATURE*
                    </fo:block>
                  </fo:table-cell>
                  <fo:table-cell border-bottom="0.2pt solid black" display-align="after">
                    <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;">
                      &#160;
                    </fo:block>
                  </fo:table-cell>
                  <fo:table-cell display-align="after">
                    <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="center">
                      PRINTED NAME*
                    </fo:block>
                  </fo:table-cell>
                  <fo:table-cell border-bottom="0.2pt solid black" display-align="after">
                    <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
                      &#160;
                    </fo:block>
                  </fo:table-cell>
                </fo:table-row>
                <fo:table-row>
                  <fo:table-cell display-align="after" number-columns-spanned="4" padding-top="7px" padding-bottom="15px">
                    <fo:block font-size="9px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left" space-after="4mm">
                      *MUST BE SIGNED BY A DULY AUTHORIZED OFFICER OF THE FIRM ON BEHALF OF ALL INSUREDS
                    </fo:block>
                  </fo:table-cell>
                </fo:table-row>
                <fo:table-row>
                  <fo:table-cell display-align="after">
                    <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
                      TITLE OF SIGNATORY:
                    </fo:block>
                  </fo:table-cell>
                  <fo:table-cell border-bottom="0.2pt solid black" display-align="after">
                    <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;">
                      &#160;
                    </fo:block>
                  </fo:table-cell>
                  <fo:table-cell display-align="after">
                    <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="center">
                      DATE SIGNED:
                    </fo:block>
                  </fo:table-cell>
                  <fo:table-cell border-bottom="0.2pt solid black" display-align="after">
                    <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
                      &#160;
                    </fo:block>
                  </fo:table-cell>
                </fo:table-row>
                <fo:table-row>
                  <fo:table-cell display-align="after">
                    <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
                      &#160;
                    </fo:block>
                  </fo:table-cell>
                  <fo:table-cell  display-align="after">
                    <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;">
                      &#160;
                    </fo:block>
                  </fo:table-cell>
                  <fo:table-cell display-align="after">
                    <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
                      &#160;
                    </fo:block>
                  </fo:table-cell>
                  <fo:table-cell display-align="after">
                    <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="center">
                      MM / DD / YYYY
                    </fo:block>
                  </fo:table-cell>
                </fo:table-row>
              </fo:table-body>
            </fo:table>
          </fo:block>
          <!-- <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" space-after="4mm">
            <fo:table border="0.2pt solid white">
              <fo:table-column column-width="2.1in"/>
              <fo:table-column column-width="2.6in"/>
              <fo:table-column column-width="0.2in"/>
              <fo:table-column column-width="2.6in"/>
              <fo:table-body>
                <fo:table-row>
                  <fo:table-cell display-align="after">
                    <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="justify">
                      FLORIDA FIRMS ONLY:
                    </fo:block>
                  </fo:table-cell>
                  <fo:table-cell border-bottom="0.2pt solid black" display-align="after">
                    <xsl:choose>
			        	<xsl:when test="response/ADDRESS_FREEFORM_01/data/StateCode ='FL'">			        
				          	<fo:block text-align="center" font-size="12px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;">
		                      Kyle Nieman
		                    </fo:block>
            			</xsl:when>
            			<xsl:otherwise>
            				<fo:block>
		                      &#160;
		                    </fo:block>
            			</xsl:otherwise>          
          			</xsl:choose>                    
                  </fo:table-cell>
                  <fo:table-cell  display-align="after">
                    <fo:block>
                      &#160;
                    </fo:block>
                  </fo:table-cell>
                  <fo:table-cell border-bottom="0.2pt solid black" display-align="after">
                    <xsl:choose>
			        	<xsl:when test="response/ADDRESS_FREEFORM_01/data/StateCode ='FL'">			        
				          	<fo:block text-align="center" font-size="12px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;">
		                      P227965
		                    </fo:block>
            			</xsl:when>
            			<xsl:otherwise>
            				<fo:block>
		                      &#160;
		                    </fo:block>
            			</xsl:otherwise>          
          			</xsl:choose>
                  </fo:table-cell>
                </fo:table-row>                
                <fo:table-row>
                  <fo:table-cell display-align="after">
                    <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="justify">                      
                    </fo:block>
                  </fo:table-cell>
                  <fo:table-cell display-align="after">
                    <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="center">
                      Producer’s Name

                    </fo:block>
                  </fo:table-cell>
                  <fo:table-cell  display-align="after">
                    <fo:block>
                      &#160;
                    </fo:block>
                  </fo:table-cell>
                  <fo:table-cell  display-align="after">
                    <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="center">
                      Producer’s Florida License Number
                    </fo:block>
                  </fo:table-cell>
                </fo:table-row>
              </fo:table-body>
            </fo:table>
          </fo:block>
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" space-after="4mm">
            <fo:table border="0.2pt solid white">
              <fo:table-column column-width="3.2in"/>
              <fo:table-column column-width="4.3in"/>
              <fo:table-body>
                <fo:table-row>
                  <fo:table-cell display-align="after">
                    <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="justify">
                      IOWA FIRMS ONLY: &#160; &#160; &#160; &#160; &#160; &#160; &#160; &#160;Producer’s Name:
                    </fo:block>
                  </fo:table-cell>

                  <fo:table-cell  display-align="after">
                  	<xsl:choose>
			        	<xsl:when test="response/ADDRESS_FREEFORM_01/data/StateCode ='IA'">			        
				          	<fo:block text-align="center" font-size="12px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" border-bottom-width="0.9pt" border-bottom-style="solid" border-bottom-color="black">
		                      Kyle Nieman
		                    </fo:block>
            			</xsl:when>
            			<xsl:otherwise>
            				<fo:block text-align="center" font-size="12px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" border-bottom-width="0.9pt" border-bottom-style="solid" border-bottom-color="black">
		                    </fo:block>
            			</xsl:otherwise>          
          			</xsl:choose>                   
                  </fo:table-cell>
                </fo:table-row>
              </fo:table-body>
            </fo:table>
          </fo:block>
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" space-after="4mm">
            <fo:table border="0.2pt solid white">
              <fo:table-column column-width="2.3in"/>
              <fo:table-column column-width="2.5in"/>
              <fo:table-column column-width="0.2in"/>
              <fo:table-column column-width="2.5in"/>
              <fo:table-body>
                <fo:table-row>
                  <fo:table-cell display-align="after" >
                    <fo:block font-size="10px" font-weight="bold" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
                      NEW HAMPSHIRE FIRMS ONLY:
                    </fo:block>
                  </fo:table-cell>
                  <fo:table-cell border-bottom="0.2pt solid black" display-align="after">
                    <xsl:choose>
			        	<xsl:when test="response/ADDRESS_FREEFORM_01/data/StateCode ='NH'">			        
				          	<fo:block text-align="center" font-size="12px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;">
		                      Kyle Nieman
		                    </fo:block>
            			</xsl:when>
            			<xsl:otherwise>
            				<fo:block>
		                      &#160;
		                    </fo:block>
            			</xsl:otherwise>          
          			</xsl:choose>                     
                  </fo:table-cell>
                  <fo:table-cell  display-align="after">
                    <fo:block>
                      &#160;
                    </fo:block>
                  </fo:table-cell>
                  <fo:table-cell border-bottom="0.2pt solid black" display-align="after">
                  	<xsl:choose>
			        	<xsl:when test="response/ADDRESS_FREEFORM_01/data/StateCode ='NH'">			        
				          	<fo:block text-align="center">
								<fo:external-graphic  height=".60in"  content-width=".80in"  src="../AccountantIns/image/kyle.png" />
							</fo:block>
            			</xsl:when>
            			<xsl:otherwise>
            				<fo:block>
		                      &#160;
		                    </fo:block>
            			</xsl:otherwise>          
          			</xsl:choose>                    
                  </fo:table-cell>
                </fo:table-row>

                <fo:table-row>
                  <fo:table-cell display-align="after">
                    <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">

                    </fo:block>
                  </fo:table-cell>
                  <fo:table-cell  display-align="after">
                    <fo:block font-size="10px" text-align="center" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;">
                      Producer’s Name

                    </fo:block>
                  </fo:table-cell>
                  <fo:table-cell  display-align="after">
                    <fo:block>
                      &#160;
                    </fo:block>
                  </fo:table-cell>
                  <fo:table-cell  display-align="after">
                    <fo:block font-size="10px" text-align="center" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;">
                      Producer’s Signature
                    </fo:block>
                  </fo:table-cell>
                </fo:table-row>
              </fo:table-body>
            </fo:table>
          </fo:block> -->
          </fo:block>
        
		  <xsl:if test="response/GrossAmount_0 > 500000">
          <fo:block space-before="4mm">
            <fo:table margin-top="1mm" border="1pt solid black" background-color="LightGray ">
              <fo:table-column></fo:table-column>
              <fo:table-body>
                <fo:table-row>
                  <fo:table-cell>
                    <fo:block margin-top="1mm" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" font-weight="bold" font-size="10px">
                    PRACTICE MANAGEMENT SUPPLEMENT (<fo:inline font-style="italic">As required in question 3</fo:inline>)
                    </fo:block>
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
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          <fo:table>
            <fo:table-column column-width="0.3in"/>
            <fo:table-column column-width="4in"/>
            <fo:table-column column-width="3.3in"/>
            <fo:table-body>
          	<fo:table-row>
          	<fo:table-cell padding-top="10px">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          	1.
	          </fo:block>
	        </fo:table-cell>
	        <fo:table-cell number-columns-spanned="2" padding-left="2mm">
          <fo:block>
          <fo:table>
          <fo:table-column column-width="155mm"/>
          <fo:table-column column-width="25mm"/>
          <fo:table-body>
          <fo:table-row>
          <fo:table-cell padding-top="10px">
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          	Do you have dis-engagement procedures for terminating client relationships?
          </fo:block>
          </fo:table-cell>
          <fo:table-cell padding-top="10px">
		  <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left" >
                      	<xsl:choose>
                            <xsl:when test="response/FIRM_FREEFORM_01/data/IsClientTerminated = 'Y'">
                            Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
                            </xsl:when>
                            <xsl:when test="response/FIRM_FREEFORM_01/data/IsClientTerminated = 'N'">
                            Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline>
                            </xsl:when>
                            <xsl:otherwise>
                             Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
                            
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
          <fo:table-row>
          	<fo:table-cell padding-top="10px">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          	2.
	          </fo:block>
	        </fo:table-cell>
	        <fo:table-cell number-columns-spanned="2" padding-left="2mm">
          <fo:block>
          <fo:table>
          <fo:table-column column-width="155mm"/>
          <fo:table-column column-width="25mm"/>
          <fo:table-body>
          <fo:table-row>
          <fo:table-cell padding-top="10px">
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          	Are declination / non-engagement letters used on all matters declined by the Firm?
          </fo:block>
          </fo:table-cell>
          <fo:table-cell padding-top="10px">
		  <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left" >
                      	<xsl:choose>
                            <xsl:when test="response/FIRM_FREEFORM_01/data/IsAnyDeclination = 'Y'">
                            Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
                            </xsl:when>
                            <xsl:when test="response/FIRM_FREEFORM_01/data/IsAnyDeclination = 'N'">
                            Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline>
                            </xsl:when>
                            <xsl:otherwise>
                             Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
                            
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
          <fo:table-row>
          	<fo:table-cell padding-top="10px">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          	3.
	          </fo:block>
	        </fo:table-cell>
	        <fo:table-cell number-columns-spanned="2" padding-left="2mm">
          <fo:block>
          <fo:table>
          <fo:table-column column-width="155mm"/>
          <fo:table-column column-width="25mm"/>
          <fo:table-body>
          <fo:table-row>
          <fo:table-cell padding-top="10px">
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          	Do you have a written policy prohibiting business ventures with clients of the Firm?
          </fo:block>
          </fo:table-cell>
          <fo:table-cell padding-top="10px">
		  <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left" >
                      	<xsl:choose>
                            <xsl:when test="response/FIRM_FREEFORM_01/data/IsPolicyProhibiting = 'Y'">
                            Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
                            </xsl:when>
                            <xsl:when test="response/FIRM_FREEFORM_01/data/IsPolicyProhibiting = 'N'">
                            Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline>
                            </xsl:when>
                            <xsl:otherwise>
                             Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
                            
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
          <fo:table-row>
          	<fo:table-cell padding-top="10px">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          	4.
	          </fo:block>
	        </fo:table-cell>
	        <fo:table-cell number-columns-spanned="2" padding-left="2mm">
          <fo:block>
          <fo:table>
          <fo:table-column column-width="155mm"/>
          <fo:table-column column-width="25mm"/>
          <fo:table-body>
          <fo:table-row>
          <fo:table-cell padding-top="10px">
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          	Do you have a written internal quality control document?
          </fo:block>
          </fo:table-cell>
          <fo:table-cell padding-top="10px">
		  <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left" >
                      	<xsl:choose>
                            <xsl:when test="response/FIRM_FREEFORM_01/data/IsWrittenAnyQualityControlDocument = 'Y'">
                            Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
                            </xsl:when>
                            <xsl:when test="response/FIRM_FREEFORM_01/data/IsWrittenAnyQualityControlDocument = 'N'">
                            Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline>
                            </xsl:when>
                            <xsl:otherwise>
                             Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
                            
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
          <fo:table-row>
          	<fo:table-cell padding-top="10px">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          	5.
	          </fo:block>
	        </fo:table-cell>
	        <fo:table-cell number-columns-spanned="2" padding-left="2mm">
          <fo:block>
          <fo:table>
          <fo:table-column column-width="155mm"/>
          <fo:table-column column-width="25mm"/>
          <fo:table-body>
          <fo:table-row>
          <fo:table-cell padding-top="10px">
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          	Within the past five years have you or your personnel received non-monetary compensation for 
          </fo:block>
          </fo:table-cell>
          </fo:table-row>
          <fo:table-row>
          <fo:table-cell>
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          	professional services performed for a client or its related entities?
          </fo:block>
          </fo:table-cell>
          <fo:table-cell>
		  <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left" >
                      	<xsl:choose>
                            <xsl:when test="response/FIRM_FREEFORM_01/data/IsNonMonetaryCompensationRecieved = 'Y'">
                            Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
                            </xsl:when>
                            <xsl:when test="response/FIRM_FREEFORM_01/data/IsNonMonetaryCompensationRecieved = 'N'">
                            Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline>
                            </xsl:when>
                            <xsl:otherwise>
                             Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
                            
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
          <fo:table-row>
          	<fo:table-cell padding-top="10px">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          	
	          </fo:block>
	        </fo:table-cell>
	        <fo:table-cell number-columns-spanned="2" padding-left="2mm">
          <fo:block>
          <fo:table>
          <fo:table-column column-width="25mm"/>
          <fo:table-column column-width="30mm"/>
          <fo:table-column column-width="25mm"/>
          <fo:table-column column-width="30mm"/>
          <fo:table-column column-width="40mm"/>
          <fo:table-column column-width="30mm"/>
          <fo:table-body>
          <fo:table-row>
          <fo:table-cell number-columns-spanned="5" padding-top="10px">
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          	If Yes, current value of such non-monetary compensation from client (include related entities):
          </fo:block>
          </fo:table-cell>
          <fo:table-cell padding-top="10px">
          	<fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" border-bottom-width="0.9pt" border-bottom-style="solid" border-bottom-color="black">
                      $ <xsl:value-of select="response/FIRM_FREEFORM_01/data/CurrentValueNmc"></xsl:value-of>
                    </fo:block>
          </fo:table-cell>
          </fo:table-row>
          <fo:table-row>
          <fo:table-cell padding-top="10px">
          	<fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          		Client Industry:
          	</fo:block>
          </fo:table-cell>
          <fo:table-cell padding-top="10px">
          	<fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" border-bottom-width="0.9pt" border-bottom-style="solid" border-bottom-color="black">
                      $ <xsl:value-of select="response/FIRM_FREEFORM_01/data/ClientIndustryNmcKey"></xsl:value-of>
                    </fo:block>
          </fo:table-cell>
          <fo:table-cell padding-top="10px" padding-left="2mm">
          	<fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          		Client Name:
          	</fo:block>
          </fo:table-cell>
          <fo:table-cell padding-top="10px">
          	<fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" border-bottom-width="0.9pt" border-bottom-style="solid" border-bottom-color="black">
                      $ <xsl:value-of select="response/FIRM_FREEFORM_01/data/ClientNameNmc"></xsl:value-of>
                    </fo:block>
          </fo:table-cell>
          <fo:table-cell padding-top="10px" padding-left="2mm">
          	<fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          		Services rendered:
          	</fo:block>
          </fo:table-cell>
          <fo:table-cell padding-top="10px">
          	<fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" border-bottom-width="0.9pt" border-bottom-style="solid" border-bottom-color="black">
                      $ <xsl:value-of select="response/FIRM_FREEFORM_01/data/ServiceIndustryNmcKey"></xsl:value-of>
                    </fo:block>
          </fo:table-cell>
          </fo:table-row>
          </fo:table-body>
          </fo:table>
          </fo:block>
          </fo:table-cell>
          </fo:table-row>
          <fo:table-row>
          	<fo:table-cell padding-top="10px">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          	6.
	          </fo:block>
	        </fo:table-cell>
	        <fo:table-cell number-columns-spanned="2" padding-left="2mm">
          <fo:block>
          <fo:table>
          <fo:table-column column-width="155mm"/>
          <fo:table-column column-width="25mm"/>
          <fo:table-body>
          <fo:table-row>
          <fo:table-cell padding-top="10px">
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          	Within the past five years, have you reduced the number of owners, partners or officers by 50% or 
          </fo:block>
          </fo:table-cell>
          </fo:table-row>
          <fo:table-row>
          <fo:table-cell>
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          	more?
          </fo:block>
          </fo:table-cell>
          <fo:table-cell>
		  <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left" >
                      	<xsl:choose>
                            <xsl:when test="response/FIRM_FREEFORM_01/data/IsReducedNumberOfOwners = 'Y'">
                            Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
                            </xsl:when>
                            <xsl:when test="response/FIRM_FREEFORM_01/data/IsReducedNumberOfOwners = 'N'">
                            Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline>
                            </xsl:when>
                            <xsl:otherwise>
                             Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
                            
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
          <fo:table-row>
          <fo:table-cell padding-top="10px">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          	
	          </fo:block>
	        </fo:table-cell>
          <fo:table-cell number-columns-spanned="2" padding-left="2mm">
          <fo:block>
          <fo:table>
          <fo:table-column column-width="45mm"/>
          <fo:table-column column-width="135mm"/>
          <fo:table-body>
          <fo:table-row>
          <fo:table-cell padding-top="10px">
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	      	If Yes, provide explanation:
	      </fo:block>
          </fo:table-cell>
          <fo:table-cell padding-top="10px">
          	<fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" border-bottom-width="0.9pt" border-bottom-style="solid" border-bottom-color="black">
                      $ <xsl:value-of select="response/policy_freeform_01/data/MergedOrNumberComment"></xsl:value-of>
                    </fo:block>
          </fo:table-cell>
          </fo:table-row>
          </fo:table-body>
          </fo:table>
          </fo:block>
          </fo:table-cell>
          </fo:table-row>
          </fo:table-body>
          </fo:table>
          </fo:block>
		  </xsl:if>
		  <xsl:if test="response/AOP_PercentageValue_19 > 0">
          <fo:block space-before="8mm">
			<fo:table margin-top="1mm" border="1pt solid black" background-color="LightGray ">
              <fo:table-column></fo:table-column>
              <fo:table-body>
                <fo:table-row>
                  <fo:table-cell>
                    <fo:block margin-top="1mm" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" font-weight="bold" font-size="10px">
                    INVESTMENT / FINANCIAL PLANNING SUPPLEMENT (<fo:inline font-style="italic">As required in question 14.U.</fo:inline>)
                    </fo:block>
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
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          <fo:table>
            <fo:table-column column-width="0.3in"/>
            <fo:table-column column-width="4in"/>
            <fo:table-column column-width="3.3in"/>
            <fo:table-body>
          	<fo:table-row>
          	<fo:table-cell padding-top="10px">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          	1.
	          </fo:block>
	        </fo:table-cell>
	        <fo:table-cell number-columns-spanned="2" padding-left="2mm">
          <fo:block>
          <fo:table>
          <fo:table-column column-width="155mm"/>
          <fo:table-column column-width="25mm"/>
          <fo:table-body>
          <fo:table-row>
          <fo:table-cell padding-top="10px">
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          	Do the Investment and Financial Planning Services provided by your Firm include:
          </fo:block>
          </fo:table-cell>
          </fo:table-row>
          <fo:table-row>
          <fo:table-cell padding-top="10px">
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          	<fo:list-block>
          	<fo:list-item>
          	<fo:list-item-label>
          	<fo:block>
          		a.
          	</fo:block>
          	</fo:list-item-label>
          	<fo:list-item-body start-indent="7mm">
          	<fo:block>
          		Preparation of Financial Plans?
          	</fo:block>
          	</fo:list-item-body>
          	</fo:list-item>
          	</fo:list-block>
          </fo:block>
          </fo:table-cell>
          <fo:table-cell padding-top="10px">
		  <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left" >
                      	<xsl:choose>
                            <xsl:when test="response/INVEST_FINAN_PLANING_FREEFORM_01/data/IsFinancialPlansPreparing = 'Y'">
                            Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
                            </xsl:when>
                            <xsl:when test="response/INVEST_FINAN_PLANING_FREEFORM_01/data/IsFinancialPlansPreparing = 'N'">
                            Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline>
                            </xsl:when>
                            <xsl:otherwise>
                             Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
                            
                            </xsl:otherwise>
                           </xsl:choose>
                    </fo:block>
		  </fo:table-cell>
          </fo:table-row>
          <fo:table-row>
          <fo:table-cell padding-top="10px">
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          	<fo:list-block>
          	<fo:list-item>
          	<fo:list-item-label>
          	<fo:block>
          		b.
          	</fo:block>
          	</fo:list-item-label>
          	<fo:list-item-body start-indent="7mm">
          	<fo:block>
          		Discretionary investment authority of client funds?
          	</fo:block>
          	</fo:list-item-body>
          	</fo:list-item>
          	</fo:list-block>
          </fo:block>
          </fo:table-cell>
          <fo:table-cell padding-top="10px">
		  <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left" >
                      	<xsl:choose>
                            <xsl:when test="response/INVEST_FINAN_PLANING_FREEFORM_01/data/IsDiscretionaryAssetMangt = 'Y'">
                            Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
                            </xsl:when>
                            <xsl:when test="response/INVEST_FINAN_PLANING_FREEFORM_01/data/IsDiscretionaryAssetMangt = 'N'">
                            Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline>
                            </xsl:when>
                            <xsl:otherwise>
                             Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
                            
                            </xsl:otherwise>
                           </xsl:choose>
                    </fo:block>
		  </fo:table-cell>
          </fo:table-row>
          <fo:table-row>
          <fo:table-cell padding-top="10px">
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          	<fo:list-block>
          	<fo:list-item>
          	<fo:list-item-label>
          	<fo:block>
          		c.
          	</fo:block>
          	</fo:list-item-label>
          	<fo:list-item-body start-indent="7mm">
          	<fo:block>
          		Non-Discretionary investment authority of client funds?
          	</fo:block>
          	</fo:list-item-body>
          	</fo:list-item>
          	</fo:list-block>
          </fo:block>
          </fo:table-cell>
          <fo:table-cell padding-top="10px">
		  <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left" >
                      	<xsl:choose>
                            <xsl:when test="response/INVEST_FINAN_PLANING_FREEFORM_01/data/IsNonDiscretionaryAssetMangt = 'Y'">
                            Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
                            </xsl:when>
                            <xsl:when test="response/INVEST_FINAN_PLANING_FREEFORM_01/data/IsNonDiscretionaryAssetMangt = 'N'">
                            Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline>
                            </xsl:when>
                            <xsl:otherwise>
                             Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
                            
                            </xsl:otherwise>
                           </xsl:choose>
                    </fo:block>
		  </fo:table-cell>
          </fo:table-row>
          <fo:table-row>
          <fo:table-cell padding-top="10px">
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          	<fo:list-block>
          	<fo:list-item>
          	<fo:list-item-label>
          	<fo:block>
          		d.
          	</fo:block>
          	</fo:list-item-label>
          	<fo:list-item-body start-indent="7mm">
          	<fo:block>
          		Securities Sales?
          	</fo:block>
          	</fo:list-item-body>
          	</fo:list-item>
          	</fo:list-block>
          </fo:block>
          </fo:table-cell>
          <fo:table-cell padding-top="10px">
		  <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left" >
                      	<xsl:choose>
                            <xsl:when test="response/INVEST_FINAN_PLANING_FREEFORM_01/data/IsSecuritiesSales = 'Y'">
                            Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
                            </xsl:when>
                            <xsl:when test="response/INVEST_FINAN_PLANING_FREEFORM_01/data/IsSecuritiesSales = 'N'">
                            Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline>
                            </xsl:when>
                            <xsl:otherwise>
                             Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
                            
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
          <fo:table-row>
          	<fo:table-cell padding-top="10px">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          	2.
	          </fo:block>
	        </fo:table-cell>
	        <fo:table-cell number-columns-spanned="2" padding-left="2mm">
          <fo:block>
          <fo:table>
          <fo:table-column column-width="155mm"/>
          <fo:table-column column-width="25mm"/>
          <fo:table-body>
          <fo:table-row>
          <fo:table-cell padding-top="10px">
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          	Do you have a contractual relationship with a securities broker or dealer?
          </fo:block>
          </fo:table-cell>
          <fo:table-cell padding-top="10px">
		  <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left" >
                      	<xsl:choose>
                            <xsl:when test="response/INVEST_FINAN_PLANING_FREEFORM_01/data/IsContractualRelationShip = 'Y'">
                            Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
                            </xsl:when>
                            <xsl:when test="response/INVEST_FINAN_PLANING_FREEFORM_01/data/IsContractualRelationShip = 'N'">
                            Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline>
                            </xsl:when>
                            <xsl:otherwise>
                             Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
                            
                            </xsl:otherwise>
                           </xsl:choose>
                    </fo:block>
		  </fo:table-cell>
          </fo:table-row>
          <xsl:if test="response/INVEST_FINAN_PLANING_FREEFORM_01/data/IsContractualRelationShip = 'Y'">
          <fo:table-row>
          <fo:table-cell padding-top="10px">
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          	If Yes, are you or individual registered representatives of your Firm insured under a broker or 
          </fo:block>
          </fo:table-cell>
          </fo:table-row>
          <fo:table-row>
          <fo:table-cell>
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          	dealer’s errors and omissions policy? 
          </fo:block>
          </fo:table-cell>
          <fo:table-cell>
		  <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left" >
                      	<xsl:choose>
                            <xsl:when test="response/INVEST_FINAN_PLANING_FREEFORM_01/data/IsOmissionsPolicy = 'Y'">
                            Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
                            </xsl:when>
                            <xsl:when test="response/INVEST_FINAN_PLANING_FREEFORM_01/data/IsOmissionsPolicy = 'N'">
                            Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline>
                            </xsl:when>
                            <xsl:otherwise>
                             Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
                            
                            </xsl:otherwise>
                           </xsl:choose>
                    </fo:block>
		  </fo:table-cell>
          </fo:table-row>
          </xsl:if>
          </fo:table-body>
          </fo:table>
          </fo:block>
          </fo:table-cell>
          </fo:table-row>
          <fo:table-row>
          	<fo:table-cell padding-top="10px">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          	3.
	          </fo:block>
	        </fo:table-cell>
	        <fo:table-cell number-columns-spanned="2" padding-left="2mm">
          <fo:block>
          <fo:table>
          <fo:table-column column-width="155mm"/>
          <fo:table-column column-width="25mm"/>
          <fo:table-body>
          <fo:table-row>
          <fo:table-cell padding-top="10px">
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          	Within the past five years, have any personnel recommended any non-public investments to 
          </fo:block>
          </fo:table-cell>
          </fo:table-row>
          <fo:table-row>
          <fo:table-cell>
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          	clients in which the Firm or their personnel have an ownership interest? 
          </fo:block>
          </fo:table-cell>
          <fo:table-cell>
		  <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left" >
                      	<xsl:choose>
                            <xsl:when test="response/INVEST_FINAN_PLANING_FREEFORM_01/data/IsAnyNonPublicInvestments = 'Y'">
                            Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
                            </xsl:when>
                            <xsl:when test="response/INVEST_FINAN_PLANING_FREEFORM_01/data/IsAnyNonPublicInvestments = 'N'">
                            Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline>
                            </xsl:when>
                            <xsl:otherwise>
                             Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
                            
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
          <fo:table-row>
          	<fo:table-cell padding-top="10px">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          	4.
	          </fo:block>
	        </fo:table-cell>
	        <fo:table-cell number-columns-spanned="2" padding-left="2mm">
          <fo:block>
          <fo:table>
          <fo:table-column column-width="155mm"/>
          <fo:table-column column-width="25mm"/>
          <fo:table-body>
          <fo:table-row>
          <fo:table-cell padding-top="10px">
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          	Have you, within the past five years, received commissions, referral fees, reciprocity or other  
          </fo:block>
          </fo:table-cell>
          </fo:table-row>
          <fo:table-row>
          <fo:table-cell>
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          	inducements arising from the sale, promotion or recommendation of securities, insurance  
          </fo:block>
          </fo:table-cell>
          </fo:table-row>
          <fo:table-row>
          <fo:table-cell>
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          	products, real estate or other investments?
          </fo:block>
          </fo:table-cell>
          <fo:table-cell>
		  <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left" >
                      	<xsl:choose>
                            <xsl:when test="response/INVEST_FINAN_PLANING_FREEFORM_01/data/IsAnyReferralFeesInvestments = 'Y'">
                            Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
                            </xsl:when>
                            <xsl:when test="response/INVEST_FINAN_PLANING_FREEFORM_01/data/IsAnyReferralFeesInvestments = 'N'">
                            Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline>
                            </xsl:when>
                            <xsl:otherwise>
                             Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
                            
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
          <fo:table-row>
          	<fo:table-cell padding-top="10px">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          	5.
	          </fo:block>
	        </fo:table-cell>
	        <fo:table-cell number-columns-spanned="2" padding-left="2mm">
          <fo:block>
          <fo:table>
          <fo:table-column column-width="155mm"/>
          <fo:table-column column-width="25mm"/>
          <fo:table-body>
          <fo:table-row>
          <fo:table-cell padding-top="10px">
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          	Is any person in your Firm licensed as a life/health/accident/disability insurance agent or broker?
          </fo:block>
          </fo:table-cell>
          <fo:table-cell padding-top="10px">
		  <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left" >
                      	<xsl:choose>
                            <xsl:when test="response/INVEST_FINAN_PLANING_FREEFORM_01/data/IsAnyFirmAffiliateLicensed = 'Y'">
                            Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
                            </xsl:when>
                            <xsl:when test="response/INVEST_FINAN_PLANING_FREEFORM_01/data/IsAnyFirmAffiliateLicensed = 'N'">
                            Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline>
                            </xsl:when>
                            <xsl:otherwise>
                             Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
                            
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
          <fo:table-row>
          	<fo:table-cell padding-top="10px">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          	6.
	          </fo:block>
	        </fo:table-cell>
	        <fo:table-cell number-columns-spanned="2" padding-left="2mm">
          <fo:block>
          <fo:table>
          <fo:table-column column-width="155mm"/>
          <fo:table-column column-width="25mm"/>
          <fo:table-body>
          <fo:table-row>
          <fo:table-cell padding-top="10px">
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          	Do you have discretionary authority to invest for any employee benefit plan? 
          </fo:block>
          </fo:table-cell>
          <fo:table-cell padding-top="10px">
		  <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left" >
                      	<xsl:choose>
                            <xsl:when test="response/INVEST_FINAN_PLANING_FREEFORM_01/data/IsAnyEmployeeBenefitPlan = 'Y'">
                            Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
                            </xsl:when>
                            <xsl:when test="response/INVEST_FINAN_PLANING_FREEFORM_01/data/IsAnyEmployeeBenefitPlan = 'N'">
                            Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline>
                            </xsl:when>
                            <xsl:otherwise>
                             Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
                            
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
          </fo:table-body>
          </fo:table>
          </fo:block>
          <fo:block page-break-before="always"/>
		  </xsl:if>
          
		  <xsl:if test="response/FIRM_FREEFORM_01/data/IsSuitInstituted = 'Y'">
          <fo:block space-before="4mm" space-after="4mm">
            <fo:table margin-top="1mm" border="1pt solid black" background-color="LightGray ">
              <fo:table-column></fo:table-column>
              <fo:table-body>
                <fo:table-row>
                  <fo:table-cell>
                    <fo:block margin-top="1mm" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" font-weight="bold" font-size="10px">
                    FEE SUIT SUPPLEMENT (<fo:inline font-style="italic">As required in question 15</fo:inline>)
                    </fo:block>
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
          <fo:block space-after="1mm">
          	    <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          <fo:table>
            <fo:table-column column-width="0.3in"/>
            <fo:table-column column-width="4in"/>
            <fo:table-column column-width="3.3in"/>
            <fo:table-body>
          	<fo:table-row>
          	<fo:table-cell padding-top="10px">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          	1.
	          </fo:block>
	        </fo:table-cell>
	        <fo:table-cell number-columns-spanned="2" padding-left="2mm">
          <fo:block>
          <fo:table>
          <fo:table-column column-width="155mm"/>
          <fo:table-column column-width="25mm"/>
          <fo:table-body>
          <fo:table-row>
          <fo:table-cell padding-top="10px">
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          	Please complete the following for each suit you have filed within the past three years against a client for collection of fees due:
          </fo:block>
          </fo:table-cell>
          </fo:table-row>
          </fo:table-body>
           </fo:table>
           </fo:block>
          </fo:table-cell>
          </fo:table-row>
          <fo:table-row>
          <fo:table-cell>
            <fo:table border="0.5pt solid white">
              <fo:table-column column-width="1.9in"/>
              <fo:table-column column-width="1.9in"/>
              <fo:table-column column-width="1.9in"/>
              <fo:table-column column-width="1.9in"/>
              <fo:table-body>
                <fo:table-row>
                  <fo:table-cell border="0.5pt solid black" padding-top="20px" border-right="0.5pt solid black" padding-left="2mm" padding-right="2mm">
                    <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" font-weight="bold" text-align="center">
                      Amount of Fees Sued For
                    </fo:block>
                  </fo:table-cell>
                  <fo:table-cell padding-top="10px" border="0.5pt solid black" padding-left="2mm" padding-right="2mm">
                    <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" font-weight="bold" text-align="center">
                      Date Fee Suit Filed
                    </fo:block>
                  </fo:table-cell>
                  <fo:table-cell padding-top="10px" border="0.5pt solid black" padding-left="2mm" padding-right="2mm">
                    <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" font-weight="bold" text-align="center">
                      Was There a Counter Claim or Allegation of Malpractice?
                    </fo:block>
                  </fo:table-cell>
                  <fo:table-cell padding-top="10px" border="0.5pt solid black" padding-left="2mm" padding-right="2mm">
                    <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" font-weight="bold" text-align="center">
                      Disposition of Fee Suit*
                    </fo:block>
                  </fo:table-cell>
                </fo:table-row>
				<xsl:for-each select="response/RENEWBASICINFO_LIST_FEESUIT/data">
                <fo:table-row>
                  <fo:table-cell border="0.5pt solid black" padding-top="5px">
                    <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;"  text-align="center">
                      <xsl:value-of select="AmountOfFeesSuedFormat"/>
                    </fo:block>
                  </fo:table-cell>
                  <fo:table-cell border="0.5pt solid black" padding-top="5px">
                    <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;"  text-align="center">
                      <xsl:value-of select="SuitFilesDateFees"/>
                    </fo:block>
                  </fo:table-cell>
                  <fo:table-cell text-align="center" border="0.5pt solid black" padding-top="5px">
                    <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;">
						<xsl:choose>
                            <xsl:when test="IsAllegOfLegalMalPrac = 'Y'">
                            Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
                            </xsl:when>
                            <xsl:when test="IsAllegOfLegalMalPrac = 'N'">
                            Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline>
                            </xsl:when>
                            <xsl:otherwise>
                             Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
                            
                            </xsl:otherwise>
                            </xsl:choose>
					</fo:block>
                  </fo:table-cell>                  
                  <fo:table-cell text-align="right" border="0.5pt solid black">
                    <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="center">
                     &#160;<xsl:value-of select="FeeSuitDispositionKey"/>
                    </fo:block>
                  </fo:table-cell>
                </fo:table-row>
				</xsl:for-each>
                </fo:table-body>
            </fo:table>
            </fo:table-cell>
            </fo:table-row>
            </fo:table-body>
            </fo:table>
            </fo:block>
          </fo:block>
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" start-indent="7mm" space-after="4mm">
            *<fo:inline font-weight="bold">P</fo:inline> = Fees paid in full, <fo:inline font-weight="bold">NS</fo:inline> = Negotiated Settlement, <fo:inline font-weight="bold">JP</fo:inline> = Judgment Plaintiff, JD = Judgment Defense, <fo:inline font-weight="bold">O</fo:inline> = Open
          </fo:block>
		  </xsl:if>
		  <xsl:if test="(response/AOP_PercentageValue_5 > 0) or (response/AOP_PercentageValue_6 > 0)">
          <fo:block space-after="4mm">
            <fo:table margin-top="1mm" border="1pt solid black" background-color="LightGray ">
              <fo:table-column></fo:table-column>
              <fo:table-body>
                <fo:table-row>
                  <fo:table-cell>
                    <fo:block margin-top="1mm" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" font-weight="bold" font-size="10px">
                    AUDIT SUPPLEMENT (<fo:inline font-style="italic">As required in question 14.E. and 14.F.</fo:inline>)
                    </fo:block>
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
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          <fo:table>
          <fo:table-column column-width="0.3in"/>
          <fo:table-column column-width="4in"/>
          <fo:table-column column-width="3.3in"/>
          <fo:table-body>
        	<fo:table-row>
        	<fo:table-cell padding-top="10px">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          	1.
	          </fo:block>
	        </fo:table-cell>
	        <fo:table-cell padding-top="10px" number-columns-spanned="2" padding-left="2mm">
          	<fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          	Please indicate the following for all Audit work:
          	</fo:block>
          	</fo:table-cell>
          </fo:table-row>
          <fo:table-row>
        	<fo:table-cell padding-top="10px">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          </fo:block>
	        </fo:table-cell>
	        <fo:table-cell padding-top="10px" number-columns-spanned="2">
          	<fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          		<fo:table border="0.5pt solid white">
	              <fo:table-column column-width="3in"/>
	              <fo:table-column column-width="1.3in"/>
	              <fo:table-column column-width="1.4in"/>
	              <fo:table-column column-width="1.5in"/>
	              <fo:table-body>
	                <fo:table-row>
	                <fo:table-cell border="0.5pt solid black" padding-top="10px">
	                <fo:block text-align="center">
	                Client Industry
	                </fo:block>
	                </fo:table-cell>
	                <fo:table-cell border="0.5pt solid black" padding-top="10px">
	                <fo:block text-align="center">
	                # of Audit Clients
	                </fo:block>
	                </fo:table-cell>
	                <fo:table-cell border="0.5pt solid black">
	                <fo:block text-align="center">
	                # of Clients with Total Assets over $5,000,000
	                </fo:block>
	                </fo:table-cell>
	                <fo:table-cell border="0.5pt solid black">
	                <fo:block text-align="center">
	                # of Clients with Net Loss for the past Fiscal Year
	                </fo:block>
	                </fo:table-cell>
	                </fo:table-row>
					<xsl:for-each select="response/ATTESTATION_LIST_03/data">
	                <fo:table-row>
	                <fo:table-cell border="0.5pt solid black" padding="2px">
	                <fo:block>
	                	<xsl:value-of select="ClientIndustrySrNo"/>&#160;<xsl:value-of select="ClientIndustryDesc"/>
	                </fo:block>
	                </fo:table-cell>
	                <fo:table-cell border="0.5pt solid black">
	                <fo:block text-align="center">
	                 <xsl:value-of select="AuditClients"/>
	                </fo:block>
	                </fo:table-cell>
	                <fo:table-cell border="0.5pt solid black">
	                <fo:block text-align="center">
	                 <xsl:value-of select="ClientsAssetsOver"/>
	                </fo:block>
	                </fo:table-cell>
	                <fo:table-cell border="0.5pt solid black">
	                <fo:block text-align="center">
	                	<xsl:value-of select="NetLossForLastFYE"/>
	                </fo:block>
	                </fo:table-cell>
	                </fo:table-row>
	                </xsl:for-each>
	                <fo:table-row>
	                <fo:table-cell border="0.5pt solid black" number-columns-spanned="4" padding="2px">
	                <fo:block>
	                	*&#160;<xsl:value-of select="response/OtherAuditAttesdesc"/>
	                </fo:block>
	                </fo:table-cell>
	                </fo:table-row>
	                </fo:table-body>
	                </fo:table>
          	</fo:block>
          	</fo:table-cell>
          </fo:table-row>
          </fo:table-body>
          </fo:table>
          </fo:block>
		  <fo:block page-break-before="always"/>
			<fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
			<fo:table>
          <fo:table-column column-width="0.3in"/>
          <fo:table-column column-width="4in"/>
          <fo:table-column column-width="3.3in"/>
          <fo:table-body>
        	<fo:table-row>
        	<fo:table-cell padding-top="10px">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          	2.
	          </fo:block>
	        </fo:table-cell>
	        <fo:table-cell number-columns-spanned="2" padding-left="2mm">
	        <fo:block>
	          <fo:table>
	          <fo:table-column column-width="155mm"/>
	          <fo:table-column column-width="25mm"/>
	          <fo:table-body>
	          <fo:table-row>
	          <fo:table-cell padding-top="10px">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          	Within the past five years, how many clients for whom you rendered audit or attestation services  
	          </fo:block>
	          </fo:table-cell>
	          </fo:table-row>
	          <fo:table-row>
	          <fo:table-cell>
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          	subsequently declared or filed bankruptcy, defaulted on a debt obligation or became insolvent? 
	          </fo:block>
	          </fo:table-cell>
	          <fo:table-cell>
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" border-bottom-width="0.9pt" border-bottom-style="solid" border-bottom-color="black">
                      &#160; <xsl:value-of select="response/ATTESTATION_FREEFORM_02/data/FirmRenderedAuditAtt"/>
               </fo:block>
	          </fo:table-cell>
	          </fo:table-row>
	          </fo:table-body>
	          </fo:table>
	          </fo:block>
	          </fo:table-cell>
	          </fo:table-row>
	          <fo:table-row>
        	  <fo:table-cell padding-top="10px">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          	3.
	          </fo:block>
	          </fo:table-cell>
	          <fo:table-cell number-columns-spanned="2" padding-left="2mm">
	          <fo:block>
	          <fo:table>
	          <fo:table-column column-width="155mm"/>
	          <fo:table-column column-width="25mm"/>
	          <fo:table-body>
	          <fo:table-row>
	          <fo:table-cell padding-top="10px">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          	Within the past five years have you performed audits for SEC-regulated entities (other than for   
	          </fo:block>
	          </fo:table-cell>
	          </fo:table-row>
	          <fo:table-row>
	          <fo:table-cell>
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          	broker-dealers)?
	          </fo:block>
	          </fo:table-cell>
	          <fo:table-cell>
		  		<fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left" >
                      	<xsl:choose>
                            <xsl:when test="response/FIRM_FREEFORM_01/data/IsPublicTradedAudited = 'Y'">
                            Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
                            </xsl:when>
                            <xsl:when test="response/FIRM_FREEFORM_01/data/IsPublicTradedAudited = 'N'">
                            Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline>
                            </xsl:when>
                            <xsl:otherwise>
                             Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
                            
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
	          <fo:table-row>
        	  <fo:table-cell padding-top="10px">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          	4.
	          </fo:block>
	          </fo:table-cell>
	          <fo:table-cell number-columns-spanned="2" padding-left="2mm">
	          <fo:block>
	          <fo:table>
	          <fo:table-column column-width="155mm"/>
	          <fo:table-column column-width="25mm"/>
	          <fo:table-body>
	          <fo:table-row>
	          <fo:table-cell padding-top="10px">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          	Do you have a written policy on audit-related CPE training, including required courses and CPE    
	          </fo:block>
	          </fo:table-cell>
	          </fo:table-row>
	          <fo:table-row>
	          <fo:table-cell>
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          	hours per year specific to audit services? 
	          </fo:block>
	          </fo:table-cell>
	          <fo:table-cell>
		  		<fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left" >
                      	<xsl:choose>
                            <xsl:when test="response/ATTESTATION_FREEFORM_02/data/IsFirmHaveAWrittenPolicyAtt = 'Y'">
                            Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
                            </xsl:when>
                            <xsl:when test="response/ATTESTATION_FREEFORM_02/data/IsFirmHaveAWrittenPolicyAtt = 'N'">
                            Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline>
                            </xsl:when>
                            <xsl:otherwise>
                             Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
                            
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
	          <fo:table-row>
        	  <fo:table-cell padding-top="10px">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          	5.
	          </fo:block>
	          </fo:table-cell>
	          <fo:table-cell number-columns-spanned="2" padding-left="2mm">
	          <fo:block>
	          <fo:table>
	          <fo:table-column column-width="155mm"/>
	          <fo:table-column column-width="25mm"/>
	          <fo:table-body>
	          <fo:table-row>
	          <fo:table-cell padding-top="10px">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          	Does a second partner review all audit workpapers and the audit report prior to signing and    
	          </fo:block>
	          </fo:table-cell>
	          </fo:table-row>
	          <fo:table-row>
	          <fo:table-cell>
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          	releasing of such audit report? 
	          </fo:block>
	          </fo:table-cell>
	          <fo:table-cell>
		  		<fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left" >
                      	<xsl:choose>
                            <xsl:when test="response/ATTESTATION_FREEFORM_02/data/IsSecondPartnerReviewAtt = 'Y'">
                            Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
                            </xsl:when>
                            <xsl:when test="response/ATTESTATION_FREEFORM_02/data/IsSecondPartnerReviewAtt = 'N'">
                            Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline>
                            </xsl:when>
                            <xsl:otherwise>
                             Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
                            
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
	          <fo:table-row>
        	  <fo:table-cell padding-top="10px">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          	6.
	          </fo:block>
	          </fo:table-cell>
	          <fo:table-cell number-columns-spanned="2" padding-left="2mm">
	          <fo:block>
	          <fo:table>
	          <fo:table-column column-width="155mm"/>
	          <fo:table-column column-width="25mm"/>
	          <fo:table-body>
	          <fo:table-row>
	          <fo:table-cell padding-top="10px">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          	Does a second partner or committee approve and sign off on the acceptance of new audit     
	          </fo:block>
	          </fo:table-cell>
	          </fo:table-row>
	          <fo:table-row>
	          <fo:table-cell>
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          	engagements? 
	          </fo:block>
	          </fo:table-cell>
	          <fo:table-cell>
		  		<fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left" >
                      	<xsl:choose>
                            <xsl:when test="response/ATTESTATION_FREEFORM_02/data/IsAuditEngagementsAtt = 'Y'">
                            Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
                            </xsl:when>
                            <xsl:when test="response/ATTESTATION_FREEFORM_02/data/IsAuditEngagementsAtt = 'N'">
                            Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline>
                            </xsl:when>
                            <xsl:otherwise>
                             Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
                            
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
	          <fo:table-row>
        	  <fo:table-cell padding-top="10px">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          	7.
	          </fo:block>
	          </fo:table-cell>
	          <fo:table-cell number-columns-spanned="2" padding-left="2mm">
	          <fo:block>
	          <fo:table>
	          <fo:table-column column-width="155mm"/>
	          <fo:table-column column-width="25mm"/>
	          <fo:table-body>
	          <fo:table-row>
	          <fo:table-cell padding-top="10px">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          	Within the past three years, have you undergone a peer or quality review offered by the AICPA or      
	          </fo:block>
	          </fo:table-cell>
	          </fo:table-row>
	          <fo:table-row>
	          <fo:table-cell>
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          	any state CPA Society? 
	          </fo:block>
	          </fo:table-cell>
	          <fo:table-cell>
		  		<fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left" >
                      	<xsl:choose>
                            <xsl:when test="response/ATTESTATION_FREEFORM_02/data/IsAualityReviewAtt = 'Y'">
                            Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
                            </xsl:when>
                            <xsl:when test="response/ATTESTATION_FREEFORM_02/data/IsAualityReviewAtt = 'N'">
                            Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline>
                            </xsl:when>
                            <xsl:otherwise>
                             Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
                            
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
	          <fo:table-row>
        	  <fo:table-cell padding-top="10px">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          	8.
	          </fo:block>
	          </fo:table-cell>
	          <fo:table-cell number-columns-spanned="2" padding-left="2mm">
	          <fo:block>
	          <fo:table>
	          <fo:table-column column-width="155mm"/>
	          <fo:table-column column-width="25mm"/>
	          <fo:table-body>
	          <fo:table-row>
	          <fo:table-cell padding-top="10px">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          	Was your last peer review modified, qualified or adverse?    
	          </fo:block>
	          </fo:table-cell>
	          <fo:table-cell padding-top="10px">
		  		<fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left" >
                      	<xsl:choose>
                            <xsl:when test="response/ATTESTATION_FREEFORM_02/data/IsLastPeerReviewAtt = 'Y'">
                            Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
                            </xsl:when>
                            <xsl:when test="response/ATTESTATION_FREEFORM_02/data/IsLastPeerReviewAtt = 'N'">
                            Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline>
                            </xsl:when>
                            <xsl:otherwise>
                             Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
                            
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
	          </fo:table-body>
	          </fo:table>
	          </fo:block>
			  </xsl:if>
			  <xsl:if test="response/AOP_PercentageValue_14 > 0">
	          <fo:block space-after="4mm" space-before="8mm">
            <fo:table margin-top="1mm" border="1pt solid black" background-color="LightGray ">
              <fo:table-column></fo:table-column>
              <fo:table-body>
                <fo:table-row>
                  <fo:table-cell>
                    <fo:block margin-top="1mm" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" font-weight="bold" font-size="10px">
                    COMPUTER RELATED SERVICES SUPPLEMENT (<fo:inline font-style="italic">As required in question 14.O.</fo:inline>)
                    </fo:block>
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
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          <fo:table>
          <fo:table-column column-width="0.3in"/>
          <fo:table-column column-width="4in"/>
          <fo:table-column column-width="3.3in"/>
          <fo:table-body>
        	<fo:table-row>
        	<fo:table-cell padding-top="10px">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          	1.
	          </fo:block>
	        </fo:table-cell>
	        <fo:table-cell padding-top="10px" number-columns-spanned="2" padding-left="2mm">
          	<fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          	Please complete below the type of Information Technology Services:
          	</fo:block>
          	</fo:table-cell>
          </fo:table-row>
          <fo:table-row>
        	<fo:table-cell padding-top="10px">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          </fo:block>
	        </fo:table-cell>
	        <fo:table-cell padding-top="10px" number-columns-spanned="2">
          	<fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          		<fo:table border="0.5pt solid white">
	              <fo:table-column column-width="3in"/>
	              <fo:table-column column-width="1.3in"/>
	              <fo:table-column column-width="1.5in"/>
	              <fo:table-column column-width="1.6in"/>
	              <fo:table-body>
	                <fo:table-row>
	                <fo:table-cell border="0.5pt solid black" padding-top="10px">
	                <fo:block text-align="center">
	                
	                </fo:block>
	                </fo:table-cell>
	                <fo:table-cell border="0.5pt solid black" padding-top="5px" padding-bottom="5px">
	                <fo:block text-align="center">
	                Revenues from Last Fiscal Year
	                </fo:block>
	                </fo:table-cell>
	                <fo:table-cell border="0.5pt solid black">
	                <fo:block text-align="center">
	                Estimated Revenue from Current Fiscal Year
	                </fo:block>
	                </fo:table-cell>
	                <fo:table-cell border="0.5pt solid black">
	                <fo:block text-align="center">
	                Are Written Agreements or Engagement Letters Used Annually?
	                </fo:block>
	                </fo:table-cell>
	                </fo:table-row>
					<xsl:for-each select="response/COMPUTERSERVICE_LIST_03/data">
	                <fo:table-row>
	                <fo:table-cell border="0.5pt solid black" padding="2px" padding-left="2px">
	                <fo:block>
	                	<xsl:value-of select="ITServiceCategoryDesc"/>
	                </fo:block>
	                </fo:table-cell>
	                <fo:table-cell border="0.5pt solid black" padding="2px" padding-left="2px">
	                <fo:block text-align="center">
	                	$<xsl:value-of select="RevenuesFromLastYear"/>
	                </fo:block>
	                </fo:table-cell>
	                <fo:table-cell border="0.5pt solid black" padding="2px" padding-left="2px">
	                <fo:block text-align="center">
	                	$<xsl:value-of select="EstimatedRevenue"/>
	                </fo:block>
	                </fo:table-cell>
	                <fo:table-cell border="0.5pt solid black" padding="2px" padding-left="2px">
	                <fo:block text-align="center">
	                	<fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;">
                      	<xsl:choose>
                            <xsl:when test="IsAnnually = 'Y'">
                            Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
                            </xsl:when>
                            <xsl:when test="IsAnnually = 'N'">
                            Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline>
                            </xsl:when>
                            <xsl:otherwise>
                             Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
                            
                            </xsl:otherwise>
                           </xsl:choose>
                    </fo:block>
	                </fo:block>
	                </fo:table-cell>
	                </fo:table-row>
					</xsl:for-each>
					<xsl:if test="(response/FIRM_FREEFORM_01/data/OtherITServiceComment != '') or (response/FIRM_FREEFORM_01/data/OtherITServiceComment != 'null')">
	                <fo:table-row>
	                <fo:table-cell border="0.5pt solid black" padding="2px" padding-left="2px">
	                <fo:block>
	                	&#160;<xsl:value-of select="response/FIRM_FREEFORM_01/data/OtherITServiceComment"/>
	                </fo:block>
	                </fo:table-cell>
					</fo:table-row>
					</xsl:if>
				  </fo:table-body>
				  </fo:table>
				  </fo:block>
				  </fo:table-cell>
				  </fo:table-row>
				  </fo:table-body>
				  </fo:table>
				  </fo:block>
			</xsl:if>
			<xsl:if test="(response/FIRM_FREEFORM_01/data/IsControlClientFunds = 'Y') or (response/AOP_PercentageValue_18 > 0 ) ">
		    <fo:block space-after="4mm" space-before="8mm">
            <fo:table margin-top="1mm" border="1pt solid black" background-color="LightGray ">
              <fo:table-column></fo:table-column>
              <fo:table-body>
                <fo:table-row>
                  <fo:table-cell>
                    <fo:block margin-top="1mm" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" font-weight="bold" font-size="10px">
                    BUSINESS MANAGEMENT SUPPLEMENT (<fo:inline font-style="italic">As required in question 8 and 14.S.</fo:inline>)
                    </fo:block>
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
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          <fo:table>
          <fo:table-column column-width="0.3in"/>
          <fo:table-column column-width="4in"/>
          <fo:table-column column-width="3.3in"/>
          <fo:table-body>
        	<fo:table-row>
        	<fo:table-cell padding-top="10px">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          	1.
	          </fo:block>
	        </fo:table-cell>
	        <fo:table-cell number-columns-spanned="2" padding-left="2mm">
			<fo:block>
			  <fo:table>
			  <fo:table-column column-width="155mm"/>
			  <fo:table-column column-width="25mm"/>
			  <fo:table-body>
			  <fo:table-row>
			  <fo:table-cell padding-top="10px">
			  <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
				Do you have discretionary investment authority of client funds?  
			  </fo:block>
			  </fo:table-cell>
			  <fo:table-cell padding-top="10px">
			  <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left" >
                      	<xsl:choose>
                            <xsl:when test="response/GENERAL_FREEFORM_03/data/HaveInvestmentAuthorityFC = 'Y'">
                            Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
                            </xsl:when>
                            <xsl:when test="response/GENERAL_FREEFORM_03/data/HaveInvestmentAuthorityFC = 'N'">
                            Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline>
                            </xsl:when>
                            <xsl:otherwise>
                             Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
                            
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
	        <fo:table-row>
        	<fo:table-cell padding-top="10px">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          	2.
	          </fo:block>
	        </fo:table-cell>
	        <fo:table-cell number-columns-spanned="2" padding-left="2mm">
			<fo:block>
			  <fo:table>
			  <fo:table-column column-width="155mm"/>
			  <fo:table-column column-width="25mm"/>
			  <fo:table-body>
			  <fo:table-row>
			  <fo:table-cell padding-top="10px">
			  <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
				What is the total number of clients for which you control or disburse funds? 
			  </fo:block>
			  </fo:table-cell>
			  <fo:table-cell padding-top="10px">
			  <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" border-bottom-width="0.9pt" border-bottom-style="solid" border-bottom-color="black">
                      &#160;<xsl:value-of select="response/GENERAL_FREEFORM_03/data/TotlaNumberOfClientsFc"/>
               </fo:block>
			  </fo:table-cell>
			  </fo:table-row>
			  </fo:table-body>
			  </fo:table>
			  </fo:block>
			  </fo:table-cell>
			  </fo:table-row>
			  <fo:table-row>
        	<fo:table-cell padding-top="10px">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          	3.
	          </fo:block>
	        </fo:table-cell>
	        <fo:table-cell number-columns-spanned="2" padding-left="2mm">
			<fo:block>
			  <fo:table>
			  <fo:table-column column-width="155mm"/>
			  <fo:table-column column-width="25mm"/>
			  <fo:table-body>
			  <fo:table-row>
			  <fo:table-cell padding-top="10px">
			  <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
				List total amount of client funds you disburse annually:
			  </fo:block>
			  </fo:table-cell>
			  <fo:table-cell padding-top="10px">
			  <xsl:choose>
			  <xsl:when test="response/GENERAL_FREEFORM_03/data/TotalAmountOfClientFundsFc != '' or response/GENERAL_FREEFORM_03/data/TotalAmountOfClientFundsFc != null">
			  <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" border-bottom-width="0.9pt" border-bottom-style="solid" border-bottom-color="black">
                      <xsl:value-of select="response/TotalAmountOfClientFundsFcFormat"/>
               </fo:block>
               </xsl:when>
               <xsl:otherwise>
               <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" border-bottom-width="0.9pt" border-bottom-style="solid" border-bottom-color="black">
                      &#160;
               </fo:block>
               </xsl:otherwise>
               </xsl:choose>
			  </fo:table-cell>
			  </fo:table-row>
			  </fo:table-body>
			  </fo:table>
			  </fo:block>
			  </fo:table-cell>
			  </fo:table-row>
			  <fo:table-row>
        	<fo:table-cell padding-top="10px">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          	4.
	          </fo:block>
	        </fo:table-cell>
	        <fo:table-cell number-columns-spanned="2" padding-left="2mm">
			<fo:block>
			  <fo:table>
			  <fo:table-column column-width="155mm"/>
			  <fo:table-column column-width="25mm"/>
			  <fo:table-body>
			  <fo:table-row>
			  <fo:table-cell padding-top="10px">
			  <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
				Other than for the preparation and filing of federal, state or local tax returns, do you provide   
			  </fo:block>
			  </fo:table-cell>
			  </fo:table-row>
			  <fo:table-row>
			  <fo:table-cell>
			  <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
				business/personal management or family office services or control funds for clients in the
			  </fo:block>
			  </fo:table-cell>
			  </fo:table-row>
			  <fo:table-row>
			  <fo:table-cell>
			  <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
				entertainment or sports industry?
			  </fo:block>
			  </fo:table-cell>
			  <fo:table-cell>
			  <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left" >
                      	<xsl:choose>
                            <xsl:when test="response/GENERAL_FREEFORM_03/data/IsControlFundsForBusinessAndEntimentFc = 'Y'">
                            Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
                            </xsl:when>
                            <xsl:when test="response/GENERAL_FREEFORM_03/data/IsControlFundsForBusinessAndEntimentFc = 'N'">
                            Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline>
                            </xsl:when>
                            <xsl:otherwise>
                             Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
                            
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
	        <fo:table-row>
        	<fo:table-cell padding-top="10px">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          	5.
	          </fo:block>
	        </fo:table-cell>
	        <fo:table-cell number-columns-spanned="2" padding-left="2mm">
			<fo:block>
			  <fo:table>
			  <fo:table-column column-width="155mm"/>
			  <fo:table-column column-width="25mm"/>
			  <fo:table-body>
			  <fo:table-row>
			  <fo:table-cell padding-top="10px">
			  <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
				Is a client countersignature required on all client checks issued by personnel of the Firm?   
			  </fo:block>
			  </fo:table-cell>
			  <fo:table-cell padding-top="10px">
			  <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left" >
                      	<xsl:choose>
                            <xsl:when test="response/GENERAL_FREEFORM_03/data/IsCountersignatureRequiredFc = 'Y'">
                            Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
                            </xsl:when>
                            <xsl:when test="response/GENERAL_FREEFORM_03/data/IsCountersignatureRequiredFc = 'N'">
                            Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline>
                            </xsl:when>
                            <xsl:otherwise>
                             Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
                            
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
			  <fo:table-row>
        	<fo:table-cell padding-top="10px">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          	6.
	          </fo:block>
	        </fo:table-cell>
	        <fo:table-cell number-columns-spanned="2" padding-left="2mm">
			<fo:block>
			  <fo:table>
			  <fo:table-column column-width="155mm"/>
			  <fo:table-column column-width="25mm"/>
			  <fo:table-body>
			  <fo:table-row>
			  <fo:table-cell padding-top="10px">
			  <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
				Are all client bank accounts reconciled by someone other than your personnel authorized to   
			  </fo:block>
			  </fo:table-cell>
			  </fo:table-row>
			  <fo:table-row>
			  <fo:table-cell>
			  <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
				deposit or withdraw therefrom?   
			  </fo:block>
			  </fo:table-cell>
			  <fo:table-cell>
			  <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left" >
                      	<xsl:choose>
                            <xsl:when test="response/GENERAL_FREEFORM_03/data/IsBankAccountReconciledFc = 'Y'">
                            Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
                            </xsl:when>
                            <xsl:when test="response/GENERAL_FREEFORM_03/data/IsBankAccountReconciledFc = 'N'">
                            Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline>
                            </xsl:when>
                            <xsl:otherwise>
                             Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
                            
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
	        </fo:table-body>
	        </fo:table>
	        </fo:block>
	        <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          <fo:table>
          <fo:table-column column-width="0.3in"/>
          <fo:table-column column-width="4in"/>
          <fo:table-column column-width="3.3in"/>
          <fo:table-body>
        	<fo:table-row>
        	<fo:table-cell padding-top="10px">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          	7.
	          </fo:block>
	        </fo:table-cell>
	        <fo:table-cell number-columns-spanned="2" padding-left="2mm">
			<fo:block>
			  <fo:table>
			  <fo:table-column column-width="155mm"/>
			  <fo:table-column column-width="25mm"/>
			  <fo:table-body>
			  <fo:table-row>
			  <fo:table-cell padding-top="10px">
			  <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
				Within the past five years have you provided management services for investment ventures?  
			  </fo:block>
			  </fo:table-cell>
			  <fo:table-cell padding-top="10px">
			  <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left" >
                      	<xsl:choose>
                            <xsl:when test="response/GENERAL_FREEFORM_03/data/IsManagementServiceProvided = 'Y'">
                            Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
                            </xsl:when>
                            <xsl:when test="response/GENERAL_FREEFORM_03/data/IsManagementServiceProvided = 'N'">
                            Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline>
                            </xsl:when>
                            <xsl:otherwise>
                             Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
                            
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
	        <fo:table-row>
        	<fo:table-cell padding-top="10px">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          	8.
	          </fo:block>
	        </fo:table-cell>
	        <fo:table-cell number-columns-spanned="2" padding-left="2mm">
			<fo:block>
			  <fo:table>
			  <fo:table-column column-width="155mm"/>
			  <fo:table-column column-width="25mm"/>
			  <fo:table-body>
			  <fo:table-row>
			  <fo:table-cell padding-top="10px">
			  <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
				Do you act as a manager or CFO for clients? 
			  </fo:block>
			  </fo:table-cell>
			  <fo:table-cell padding-top="10px">
			  <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left" >
                      	<xsl:choose>
                            <xsl:when test="response/GENERAL_FREEFORM_03/data/IsFirmPersonnelBusinessManagerFc = 'Y'">
                            Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
                            </xsl:when>
                            <xsl:when test="response/GENERAL_FREEFORM_03/data/IsFirmPersonnelBusinessManagerFc = 'N'">
                            Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline>
                            </xsl:when>
                            <xsl:otherwise>
                             Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
                            
                            </xsl:otherwise>
                           </xsl:choose>
						</fo:block>
			  </fo:table-cell>
			  </fo:table-row>
			  <xsl:if test="response/GENERAL_FREEFORM_03/data/IsFirmPersonnelBusinessManagerFc = 'Y'">
			  <fo:table-row>
			  <fo:table-cell>
			  <fo:block>
			  <fo:table>
			   <fo:table-column column-width="70mm"/>
			  <fo:table-column column-width="20mm"/>
			  <fo:table-column column-width="30mm"/>
			  <fo:table-column column-width="60mm"/>
			  <fo:table-body>
			  <fo:table-row>
			  <fo:table-cell padding-top="10px">
			  <fo:block>
			  	If Yes, provide the number of such clients:
			  </fo:block>
			  </fo:table-cell>
			  <fo:table-cell padding-top="10px">
			  	<fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" border-bottom-width="0.9pt" border-bottom-style="solid" border-bottom-color="black">
                      &#160; <xsl:value-of select="response/GENERAL_FREEFORM_03/data/ProvideNumberOfClientFc"/>
               </fo:block>
			  </fo:table-cell>
			  <fo:table-cell padding-top="10px" padding-left="2mm">
			  <fo:block>
			  	Client Industry:
			  </fo:block>
			  </fo:table-cell>
			  <fo:table-cell padding-top="10px">
			  	<fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" border-bottom-width="0.9pt" border-bottom-style="solid" border-bottom-color="black">
                      &#160;<xsl:value-of select="response/GENERAL_FREEFORM_03/data/ClientIndustryFc" />
               </fo:block>
			  </fo:table-cell>
			  </fo:table-row>
			  </fo:table-body>
			  </fo:table>
			  </fo:block>
			  </fo:table-cell>
			  </fo:table-row>
			  </xsl:if>
			  </fo:table-body>
			  </fo:table>
			  </fo:block>
			  </fo:table-cell>
			  </fo:table-row>
			  <fo:table-row>
        	<fo:table-cell padding-top="10px">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          	9.
	          </fo:block>
	        </fo:table-cell>
	        <fo:table-cell number-columns-spanned="2" padding-left="2mm">
			<fo:block>
			  <fo:table>
			  <fo:table-column column-width="155mm"/>
			  <fo:table-column column-width="25mm"/>
			  <fo:table-body>
			  <fo:table-row>
			  <fo:table-cell padding-top="10px">
			  <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
				Provide the number of your employees who control or disburse funds for the Firm <fo:inline font-weight="bold">or</fo:inline> clients:  
			  </fo:block>
			  </fo:table-cell>
			  <fo:table-cell padding-top="10px">
			  <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" border-bottom-width="0.9pt" border-bottom-style="solid" border-bottom-color="black">
                      &#160;<xsl:value-of select="response/GENERAL_FREEFORM_03/data/NumberOfFirmEmployeesFc"/>
               </fo:block>
			  </fo:table-cell>
			  </fo:table-row>
			  </fo:table-body>
			  </fo:table>
			  </fo:block>
			  </fo:table-cell>
			  </fo:table-row>
			  <fo:table-row>
        	<fo:table-cell padding-top="10px">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          	10.
	          </fo:block>
	        </fo:table-cell>
	        <fo:table-cell number-columns-spanned="2" padding-left="2mm">
			<fo:block>
			  <fo:table>
			  <fo:table-column column-width="155mm"/>
			  <fo:table-column column-width="25mm"/>
			  <fo:table-body>
			  <fo:table-row>
			  <fo:table-cell padding-top="10px">
			  <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
				Do you carry Employee Dishonesty Coverage?
			  </fo:block>
			  </fo:table-cell>
			  <fo:table-cell padding-top="10px">
			  <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left" >
                      	<xsl:choose>
                            <xsl:when test="response/GENERAL_FREEFORM_03/data/IsEmployeeDishonestyCoverageFc = 'Y'">
                            Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
                            </xsl:when>
                            <xsl:when test="response/GENERAL_FREEFORM_03/data/IsEmployeeDishonestyCoverageFc = 'N'">
                            Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline>
                            </xsl:when>
                            <xsl:otherwise>
                             Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
                            
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
	        </fo:table-body>
	        </fo:table>
	        </fo:block>
			</xsl:if>
			<xsl:if test="(response/FIRM_FREEFORM_01/data/IsAwarenessOfAnyProfLiability = 'Y') or (response/FIRM_FREEFORM_01/data/IsAwarenessOfAnyProfLiabilitySuitAgainst = 'Y')">
	        <fo:block space-after="4mm" space-before="8mm">
            <fo:table margin-top="1mm" border="1pt solid black" background-color="LightGray ">
              <fo:table-column></fo:table-column>
              <fo:table-body>
                <fo:table-row>
                  <fo:table-cell>
                    <fo:block margin-top="1mm" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" font-weight="bold" font-size="10px">
                    CLAIM SUPPLEMENT (<fo:inline font-style="italic">As required in question 16.b. or 16.c.</fo:inline>)
                    </fo:block>
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
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          <fo:table>
          <fo:table-column column-width="0.3in"/>
          <fo:table-column column-width="4in"/>
          <fo:table-column column-width="3.3in"/>
          <fo:table-body>
        	<fo:table-row>
        	<fo:table-cell padding-top="10px">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          	1.
	          </fo:block>
	        </fo:table-cell>
	        <fo:table-cell number-columns-spanned="2" padding-left="2mm">
			<fo:block>
			  <fo:table>
			  <fo:table-column column-width="70mm"/>
			  <fo:table-column column-width="65mm"/>
			  <fo:table-column column-width="45mm"/>
			  <fo:table-body>
			  <fo:table-row>
			  <fo:table-cell padding-top="10px">
			  <fo:block>
			  	Full name of claimant or potential claimant:
			  </fo:block>
			  </fo:table-cell>
			  <fo:table-cell padding-top="10px">
			  <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" border-bottom-width="0.9pt" border-bottom-style="solid" border-bottom-color="black">
                      &#160;<xsl:value-of select="response/BASICINFO_LIST_04/data/NameOfClaimantCi"/>
               </fo:block>
			  </fo:table-cell>
			  <fo:table-cell padding-top="10px" padding-left="2mm">
			  <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left" >
                      	<xsl:choose>
                            <xsl:when test="response/BASICINFO_LIST_04/data/IsClientCi = 'Y'">
                            <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline> &#160; Client &#160;<fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline> &#160; Non-Client
                            </xsl:when>
                            <xsl:when test="response/BASICINFO_LIST_04/data/IsClientCi = 'N'">
                            <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline> &#160; Client &#160;<fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline> &#160; Non-Client
                            </xsl:when>
                            <xsl:otherwise>
                             <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline> &#160; Client &#160;<fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline> &#160; Non-Client
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
			  <fo:table-row>
        	<fo:table-cell padding-top="10px">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          	2.
	          </fo:block>
	        </fo:table-cell>
	        <fo:table-cell number-columns-spanned="2" padding-left="2mm">
			<fo:block>
			  <fo:table>
			  <fo:table-column column-width="70mm"/>
			  <fo:table-column column-width="65mm"/>
			  <fo:table-column column-width="45mm"/>
			  <fo:table-body>
			  <fo:table-row>
			  <fo:table-cell padding-top="10px">
			  <fo:block>
			  	Is the claim in suit?
			  </fo:block>
			  </fo:table-cell>
			  <fo:table-cell padding-top="10px">
			  <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" border-bottom-width="0.9pt" border-bottom-style="solid" border-bottom-color="black">
                      &#160; 
               </fo:block>
			  </fo:table-cell>
			  <fo:table-cell padding-top="10px" padding-left="2mm">
			  <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left" >
                      	<xsl:choose>
                            <xsl:when test="response/BASICINFO_LIST_04/data/IsClaimInSuit = 'X'">
                             N/A <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline> &#160; Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
                            </xsl:when>
							<xsl:when test="response/BASICINFO_LIST_04/data/IsClaimInSuit = 'Y'">
                             N/A <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline> &#160; Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
                            </xsl:when>
                            <xsl:when test="response/BASICINFO_LIST_04/data/IsClaimInSuit = 'N'">
                            N/A <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline> &#160; Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline>
                            </xsl:when>
                            <xsl:otherwise>
                             N/A <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline> &#160; Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
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
			  <fo:table-row>
        	<fo:table-cell padding-top="10px">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          	3.
	          </fo:block>
	        </fo:table-cell>
	        <fo:table-cell number-columns-spanned="2" padding-left="2mm">
			<fo:block>
			  <fo:table>
			  <fo:table-column column-width="55mm"/>
			  <fo:table-column column-width="40mm"/>
			  <fo:table-column column-width="40mm"/>
			  <fo:table-column column-width="45mm"/>
			  <fo:table-body>
			  <fo:table-row>
			  <fo:table-cell padding-top="10px">
			  <fo:block>
			  	Date you were notified of claim or became aware of error/incident:
			  </fo:block>
			  </fo:table-cell>
			  <fo:table-cell padding-top="20px">
			  <fo:block text-align="center" font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" border-bottom-width="0.9pt" border-bottom-style="solid" border-bottom-color="black">
                      &#160; <xsl:value-of select="response/BASICINFO_LIST_04/data/ClaimNotifiedCiDate"></xsl:value-of>
               </fo:block>
               <fo:block font-size="8px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" font-style="italic" text-align="center">
	                     MM / DD / YYYY
				 </fo:block>
			  </fo:table-cell>
			  <fo:table-cell padding-top="20px" padding-left="2mm">
			  <fo:block>
			  	Date of alleged error:
			  	</fo:block>
			  </fo:table-cell>
			  <fo:table-cell padding-top="20px">
			  <fo:block text-align="center" font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" border-bottom-width="0.9pt" border-bottom-style="solid" border-bottom-color="black">
                      &#160; <xsl:value-of select="response/BASICINFO_LIST_04/data/AllegedErrorCiDate"></xsl:value-of>
               </fo:block>
               <fo:block font-size="8px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" font-style="italic" text-align="center">
	                     MM / DD / YYYY
				 </fo:block>
			  </fo:table-cell>
			  </fo:table-row>
			  </fo:table-body>
			  </fo:table>
			  </fo:block>
			  </fo:table-cell>
			  </fo:table-row>
			  <fo:table-row>
        	<fo:table-cell padding-top="10px">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          	4.
	          </fo:block>
	        </fo:table-cell>
	        <fo:table-cell number-columns-spanned="2" padding-left="2mm">
			<fo:block>
			  <fo:table>
			  <fo:table-column column-width="105mm"/>
			  <fo:table-column column-width="75mm"/>
			  <fo:table-body>
			  <fo:table-row>
			  <fo:table-cell padding-top="10px">
			  <fo:block>
			  	Names of firm personnel involved in the claim or potential claim:
			  </fo:block>
			  </fo:table-cell>
			  <fo:table-cell padding-top="10px">
			  <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" border-bottom-width="0.9pt" border-bottom-style="solid" border-bottom-color="black">
                      &#160; <xsl:value-of select="response/BASICINFO_LIST_04/data/NameOfFirmPersonnelCi"></xsl:value-of>
               </fo:block>
			  </fo:table-cell>
			  </fo:table-row>
			  </fo:table-body>
			  </fo:table>
			  </fo:block>
			  </fo:table-cell>
			  </fo:table-row>
			  <fo:table-row>
        	<fo:table-cell padding-top="10px">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          	5.
	          </fo:block>
	        </fo:table-cell>
	        <fo:table-cell number-columns-spanned="2" padding-left="2mm">
			<fo:block>
			  <fo:table>
			  <fo:table-column column-width="60mm"/>
			  <fo:table-column column-width="120mm"/>
			  <fo:table-body>
			  <fo:table-row>
			  <fo:table-cell padding-top="10px">
			  <fo:block>
			  	Description of claim or potential claim:
			  </fo:block>
			  </fo:table-cell>
			  <fo:table-cell padding-top="10px" padding-left="2mm">
			  <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" border-bottom-width="0.9pt" border-bottom-style="solid" border-bottom-color="black">
                      &#160; <xsl:value-of select="response/BASICINFO_LIST_04/data/DescOfClaimCi"></xsl:value-of>
               </fo:block>
			  </fo:table-cell>
			  </fo:table-row>
			 <!--  <fo:table-row>
			  <fo:table-cell number-columns-spanned="2" padding-top="10px">
			  <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" border-bottom-width="0.9pt" border-bottom-style="solid" border-bottom-color="black">
                      &#160;  $ <xsl:value-of select="response/policy_freeform_01/data/AccountName"></xsl:value-of>
               </fo:block>
			  </fo:table-cell>
			  </fo:table-row> -->
			  </fo:table-body>
			  </fo:table>
			  </fo:block>
			  </fo:table-cell>
			  </fo:table-row>
			  <fo:table-row>
        	<fo:table-cell padding-top="10px">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          	6.
	          </fo:block>
	        </fo:table-cell>
	        <fo:table-cell number-columns-spanned="2" padding-left="2mm">
			<fo:block>
			  <fo:table>
			  <fo:table-column column-width="155mm"/>
			  <fo:table-column column-width="25mm"/>
			  <fo:table-body>
			  <fo:table-row>
			  <fo:table-cell padding-top="10px">
			  <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
				Has this claim or potential claim been reported to your insurance company? 
			  </fo:block>
			  </fo:table-cell>
			  <fo:table-cell padding-top="10px">
			  <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left" >
                      	<xsl:choose>
                            <xsl:when test="response/BASICINFO_LIST_04/data/IsThisClaimReportedCi = 'Y'">
                            Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
                            </xsl:when>
                            <xsl:when test="response/BASICINFO_LIST_04/data/IsThisClaimReportedCi = 'N'">
                            Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline>
                            </xsl:when>
                            <xsl:otherwise>
                             Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
                            
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
			  <fo:table-row>
        	<fo:table-cell padding-top="10px">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          	7.
	          </fo:block>
	        </fo:table-cell>
	        <fo:table-cell number-columns-spanned="2" padding-left="2mm">
			<fo:block>
			  <fo:table>
			  <fo:table-column column-width="50mm"/>
			  <fo:table-column column-width="30mm"/>
			  <fo:table-column column-width="65mm"/>
			  <fo:table-column column-width="35mm"/>
			  <fo:table-body>
			  <fo:table-row>
			  <fo:table-cell padding-top="10px">
			  <fo:block>
			  	Name of insurance company:
			  </fo:block>
			  </fo:table-cell>
			  <fo:table-cell padding-top="10px" padding-left="2mm">
			  <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" border-bottom-width="0.9pt" border-bottom-style="solid" border-bottom-color="black">
                      &#160;<xsl:value-of select="response/BASICINFO_LIST_04/data/NameOfInsuranceCompanyCi"></xsl:value-of>
               </fo:block>
			  </fo:table-cell>
			  <fo:table-cell padding-top="10px" padding-left="2mm">
			  <fo:block>
			  	Date reported to insurance company:
			  	</fo:block>
			  </fo:table-cell>
			  <fo:table-cell padding-top="10px">
			  <fo:block text-align="center" font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" border-bottom-width="0.9pt" border-bottom-style="solid" border-bottom-color="black">
                      &#160;<xsl:value-of select="response/BASICINFO_LIST_04/data/ReportedCiDate"></xsl:value-of>
               </fo:block>
               <fo:block font-size="8px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" font-style="italic" text-align="center">
	                     MM / DD / YYYY
				 </fo:block>
			  </fo:table-cell>
			  </fo:table-row>
			  </fo:table-body>
			  </fo:table>
			  </fo:block>
			  </fo:table-cell>
			  </fo:table-row>
			  <fo:table-row>
        	<fo:table-cell padding-top="10px">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          	8.
	          </fo:block>
	        </fo:table-cell>
	        <fo:table-cell number-columns-spanned="2" padding-left="2mm">
			<fo:block>
			  <fo:table>
			  <fo:table-column column-width="65mm"/>
			  <fo:table-column column-width="35mm"/>
			  <fo:table-column column-width="45mm"/>
			  <fo:table-column column-width="35mm"/>
			  <fo:table-body>
			  <fo:table-row>
			  <fo:table-cell padding-top="10px">
			  <fo:block>
			  	If open, provide: Insurer’s loss reserve:
			  </fo:block>
			  </fo:table-cell>
			  <fo:table-cell padding-top="10px" padding-left="2mm">
			  <xsl:choose>
			  <xsl:when test="response/BASICINFO_LIST_04/data/InsurerLoss != '' or response/BASICINFO_LIST_04/data/InsurerLoss != null">
			  <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" border-bottom-width="0.9pt" border-bottom-style="solid" border-bottom-color="black">
                      $<xsl:value-of select="response/BASICINFO_LIST_04/data/InsurerLoss"></xsl:value-of>
               </fo:block>
               </xsl:when>
               <xsl:otherwise>
               <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" border-bottom-width="0.9pt" border-bottom-style="solid" border-bottom-color="black">
                     &#160;
               </fo:block>
               </xsl:otherwise>
               </xsl:choose>
			  </fo:table-cell>
			  <fo:table-cell padding-top="10px" padding-left="2mm">
			  <fo:block>
			  	Claimant’s last demand:
			  	</fo:block>
			  </fo:table-cell>
			  <fo:table-cell padding-top="10px">
			  <xsl:choose>
			  <xsl:when test="response/BASICINFO_LIST_04/data/ClaimantLastDemandCi != '' or response/BASICINFO_LIST_04/data/ClaimantLastDemandCi != null">
			  <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" border-bottom-width="0.9pt" border-bottom-style="solid" border-bottom-color="black">
                      $<xsl:value-of select="response/BASICINFO_LIST_04/data/ClaimantLastDemandCi"></xsl:value-of>
               </fo:block>
               </xsl:when>
               <xsl:otherwise>
               <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" border-bottom-width="0.9pt" border-bottom-style="solid" border-bottom-color="black">
                     &#160;
               </fo:block>
               </xsl:otherwise>
               </xsl:choose>
			  <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" border-bottom-width="0.9pt" border-bottom-style="solid" border-bottom-color="black">
                      
               </fo:block>
			  </fo:table-cell>
			  </fo:table-row>
			  <fo:table-row>
			  <fo:table-cell number-columns-spanned="2">
			  <fo:block>
			  <fo:table>
			  <fo:table-column column-width="25mm"/>
			  <fo:table-column column-width="155mm"/>
			  <fo:table-body>
			  	<fo:table-row>
			  	<fo:table-cell padding-top="10px">
			  	<fo:block>
			  	Current status:
			  	</fo:block>
			  	</fo:table-cell>
			  	<fo:table-cell padding-top="10px">
			  <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" border-bottom-width="0.9pt" border-bottom-style="solid" border-bottom-color="black">
                      &#160;<xsl:value-of select="response/BASICINFO_LIST_04/data/CurrentStatus"></xsl:value-of>
               </fo:block>
			  </fo:table-cell>
			  	</fo:table-row>
			  </fo:table-body>
			  </fo:table>
			  </fo:block>
			  </fo:table-cell>
			  </fo:table-row>
			  </fo:table-body>
			  </fo:table>
			  </fo:block>
			  </fo:table-cell>
			  </fo:table-row>
			  <fo:table-row>
        	<fo:table-cell padding-top="20px">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          	9.
	          </fo:block>
	        </fo:table-cell>
	        <fo:table-cell number-columns-spanned="2" padding-left="2mm">
			<fo:block>
			  <fo:table>
			  <fo:table-column column-width="50mm"/>
			  <fo:table-column column-width="33mm"/>
			  <fo:table-column column-width="57mm"/>
			  <fo:table-column column-width="40mm"/>
			  <fo:table-body>
			  <fo:table-row>
			  <fo:table-cell padding-top="10px" number-columns-spanned="2">
			  <fo:block></fo:block>
			  </fo:table-cell>
			  <fo:table-cell padding-top="10px" padding-left="2mm">
			  <fo:block>
			  ; total amount paid (including 
			  </fo:block>
			  </fo:table-cell>
			  <fo:table-cell padding-top="10px">
			  <fo:block></fo:block>
			  </fo:table-cell>
			  </fo:table-row>
			  <fo:table-row>
			  <fo:table-cell>
			  <fo:block>
			  	If closed, provide: date closed:
			  </fo:block>
			  </fo:table-cell>
			  <fo:table-cell padding-left="2mm">
			  <fo:block text-align="center" font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" border-bottom-width="0.9pt" border-bottom-style="solid" border-bottom-color="black">
                      &#160;<xsl:value-of select="response/BASICINFO_LIST_04/data/ClosingCiDate"></xsl:value-of>
               </fo:block>
               <fo:block font-size="8px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" font-style="italic" text-align="center">
	                     MM / DD / YYYY
				 </fo:block>
			  </fo:table-cell>
			  <fo:table-cell padding-left="2mm">
			  <fo:block>
			  	damages and defense expenses):
			  	</fo:block>
			  </fo:table-cell>
			  <fo:table-cell>
			  <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" border-bottom-width="0.9pt" border-bottom-style="solid" border-bottom-color="black">
                      $&#160;<xsl:value-of select="response/BASICINFO_LIST_04/data/TotalClaimExpensesCi"></xsl:value-of>
               </fo:block>
			  </fo:table-cell>
			  </fo:table-row>
			  </fo:table-body>
			  </fo:table>
			  </fo:block>
			  </fo:table-cell>
			  </fo:table-row>
			  <fo:table-row>
        	<fo:table-cell padding-top="10px">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          	10.
	          </fo:block>
	        </fo:table-cell>
	        <fo:table-cell number-columns-spanned="2" padding-left="2mm">
			<fo:block>
			  <fo:table>
			  <fo:table-column column-width="115mm"/>
			  <fo:table-column column-width="65mm"/>
			  <fo:table-body>
			  <fo:table-row>
			  <fo:table-cell padding-top="10px" number-columns-spanned="2">
			  <fo:block>
			  	What steps have you taken to prevent similar allegations from being made in future claims/incidents?
			  </fo:block>
			  </fo:table-cell>
			  </fo:table-row>
			  <fo:table-row>
			  <fo:table-cell number-columns-spanned="2" padding-top="10px">
			  <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" border-bottom-width="0.9pt" border-bottom-style="solid" border-bottom-color="black">
                      &#160;<xsl:value-of select="response/BASICINFO_LIST_04/data/StepsTakenToPreventClaimCi"></xsl:value-of>
               </fo:block>
			  </fo:table-cell>
			  </fo:table-row>
			  </fo:table-body>
			  </fo:table>
			  </fo:block>
			  </fo:table-cell>
			  </fo:table-row>
			  </fo:table-body>
			  </fo:table>
			  </fo:block>
			  </xsl:if>
			  <xsl:if test="response/FIRM_FREEFORM_01/data/IsFirmRenderingServices = 'Y'">
			  <fo:block space-after="4mm" space-before="6mm">
            <fo:table margin-top="1mm" border="1pt solid black" background-color="LightGray ">
              <fo:table-column></fo:table-column>
              <fo:table-body>
                <fo:table-row>
                  <fo:table-cell>
                    <fo:block margin-top="1mm" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" font-weight="bold" font-size="10px">
                    SEPARATE ENTITY SUPPLEMENT (<fo:inline font-style="italic">As required in question 4</fo:inline>)
                    </fo:block>
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
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          <fo:table>
          <fo:table-column column-width="0.3in"/>
          <fo:table-column column-width="4in"/>
          <fo:table-column column-width="3.3in"/>
          <fo:table-body>
        	<fo:table-row>
        	<fo:table-cell padding-top="10px" number-columns-spanned="3">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          	Provide the following for each entity:
	          </fo:block>
	        </fo:table-cell>
	        </fo:table-row>
	        <fo:table-row>
	        <fo:table-cell padding-top="10px">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          	1.
	          </fo:block>
	        </fo:table-cell>
	        <fo:table-cell number-columns-spanned="2" padding-left="2mm">
			<fo:block>
			  <fo:table>
			  <fo:table-column column-width="90mm"/>
			  <fo:table-column column-width="90mm"/>
			  <fo:table-body>
			  <fo:table-row>
			  <fo:table-cell padding-top="10px">
			  <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
				Name of entity and form of entity (LLC, LLP, Inc., etc.): 
			  </fo:block>
			  </fo:table-cell>
			  <fo:table-cell padding-top="10px">
			  <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" border-bottom-width="0.9pt" border-bottom-style="solid" border-bottom-color="black">
                      &#160;<xsl:value-of select="response/GENERAL_FREEFORM_02/data/EntityNameSe"></xsl:value-of>
               </fo:block>
			  </fo:table-cell>
			  </fo:table-row>
			  </fo:table-body>
			  </fo:table>
			  </fo:block>
			  </fo:table-cell>
	        </fo:table-row>
	        <fo:table-row>
	        <fo:table-cell padding-top="10px">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          	2.
	          </fo:block>
	        </fo:table-cell>
	        <fo:table-cell number-columns-spanned="2" padding-left="2mm">
			<fo:block>
			  <fo:table>
			  <fo:table-column column-width="30mm"/>
			  <fo:table-column column-width="150mm"/>
			  <fo:table-body>
			  <fo:table-row>
			  <fo:table-cell padding-top="10px">
			  <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
				Date established:
			  </fo:block>
			  </fo:table-cell>
			  <fo:table-cell padding-top="10px">
			  <fo:block text-align="center" font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" border-bottom-width="0.9pt" border-bottom-style="solid" border-bottom-color="black">
                      &#160;<xsl:value-of select="response/GENERAL_FREEFORM_02/data/EstablisedSeDate"></xsl:value-of>
               </fo:block>
               <fo:block font-size="8px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" font-style="italic" text-align="center">
	                     YYYY
				 </fo:block>
			  </fo:table-cell>
			  </fo:table-row>
			  </fo:table-body>
			  </fo:table>
			  </fo:block>
			  </fo:table-cell>
	        </fo:table-row>
	        <fo:table-row>
        	<fo:table-cell padding-top="10px">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          	3.
	          </fo:block>
	        </fo:table-cell>
	        <fo:table-cell number-columns-spanned="2" padding-left="2mm">
			<fo:block>
			  <fo:table>
			  <fo:table-column column-width="155mm"/>
			  <fo:table-column column-width="25mm"/>
			  <fo:table-body>
			  <fo:table-row>
			  <fo:table-cell padding-top="10px">
			  <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
				Percent of ownership held by your Firm:
			  </fo:block>
			  </fo:table-cell>
			  <fo:table-cell padding-top="10px">
			  <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" border-bottom-width="0.9pt" border-bottom-style="solid" border-bottom-color="black">
                      &#160;<xsl:value-of select="response/GENERAL_FREEFORM_02/data/PercentOfOwnershipSe"></xsl:value-of>%
               </fo:block>
			  </fo:table-cell>
			  </fo:table-row>
			  </fo:table-body>
			  </fo:table>
			  </fo:block>
			  </fo:table-cell>
			  </fo:table-row>
			  <fo:table-row>
        	<fo:table-cell padding-top="10px">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          	4.
	          </fo:block>
	        </fo:table-cell>
	        <fo:table-cell number-columns-spanned="2" padding-left="2mm">
			<fo:block>
			  <fo:table>
			  <fo:table-column column-width="45mm"/>
			  <fo:table-column column-width="40mm"/>
			  <fo:table-column column-width="45mm"/>
			  <fo:table-column column-width="50mm"/>
			  <fo:table-body>
			  <fo:table-row>
			  <fo:table-cell padding-top="10px">
			  <fo:block>
			  	Total professional staff:
			  </fo:block>
			  </fo:table-cell>
			  <fo:table-cell padding-top="10px" padding-left="2mm">
			  <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" border-bottom-width="0.9pt" border-bottom-style="solid" border-bottom-color="black">
                      &#160;<xsl:value-of select="response/GENERAL_FREEFORM_02/data/TotalProfessionalStaffSe"></xsl:value-of>
               </fo:block>
			  </fo:table-cell>
			  <fo:table-cell padding-top="10px" padding-left="10mm">
			  <fo:block>
			  	Total support staff:
			  	</fo:block>
			  </fo:table-cell>
			  <fo:table-cell padding-top="10px">
			  <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" border-bottom-width="0.9pt" border-bottom-style="solid" border-bottom-color="black">
                      &#160;<xsl:value-of select="response/GENERAL_FREEFORM_02/data/TotalSupportStaffSe"></xsl:value-of>
               </fo:block>
			  </fo:table-cell>
			  </fo:table-row>
			  </fo:table-body>
			  </fo:table>
			  </fo:block>
			  </fo:table-cell>
			  </fo:table-row>
			  <fo:table-row>
        	<fo:table-cell padding-top="10px">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          	5.
	          </fo:block>
	        </fo:table-cell>
	        <fo:table-cell number-columns-spanned="2" padding-left="2mm">
			<fo:block>
			  <fo:table>
			  <fo:table-column column-width="155mm"/>
			  <fo:table-column column-width="25mm"/>
			  <fo:table-body>
			  <fo:table-row>
			  <fo:table-cell padding-top="10px">
			  <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
				Is the staff noted here included in question 2. on the main application?
			  </fo:block>
			  </fo:table-cell>
			  <fo:table-cell padding-top="10px">
			  <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left" >
                      	<xsl:choose>
                            <xsl:when test="response/GENERAL_FREEFORM_02/data/IsStaffIncludedInMain = 'Y'">
                            Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
                            </xsl:when>
                            <xsl:when test="response/GENERAL_FREEFORM_02/data/IsStaffIncludedInMain = 'N'">
                            Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline>
                            </xsl:when>
                            <xsl:otherwise>
                             Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
                            
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
	        </fo:table-body>
	        </fo:table>
	        </fo:block>
	        <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          <fo:table>
          <fo:table-column column-width="0.3in"/>
          <fo:table-column column-width="4in"/>
          <fo:table-column column-width="3.3in"/>
          <fo:table-body>
          <fo:table-row>
        	<fo:table-cell padding-top="10px">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          	6.
	          </fo:block>
	        </fo:table-cell>
	        <fo:table-cell number-columns-spanned="2" padding-left="2mm">
			<fo:block>
			  <fo:table>
			  <fo:table-column column-width="85mm"/>
			  <fo:table-column column-width="95mm"/>
			  <fo:table-body>
			  <fo:table-row>
			  <fo:table-cell padding-top="10px">
			  <fo:block>
			  	Provide a detailed description of the entity’s services:
			  </fo:block>
			  </fo:table-cell>
			  <fo:table-cell padding-top="10px">
			  <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" border-bottom-width="0.9pt" border-bottom-style="solid" border-bottom-color="black">
                      &#160;<xsl:value-of select="response/GENERAL_FREEFORM_02/data/DetailedDescSe"></xsl:value-of>
               </fo:block>
			  </fo:table-cell>
			  </fo:table-row>
			  </fo:table-body>
			  </fo:table>
			  </fo:block>
			  </fo:table-cell>
			  </fo:table-row>
			  <fo:table-row>
        	<fo:table-cell padding-top="10px">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          	7.
	          </fo:block>
	        </fo:table-cell>
	        <fo:table-cell number-columns-spanned="2" padding-left="2mm" padding-top="10px">
	        <fo:block>
	        	Gross Annual Revenue:
	        </fo:block>
	        </fo:table-cell>
	        </fo:table-row>
	        <fo:table-row>
	        <fo:table-cell>
	        <fo:block></fo:block>
	        </fo:table-cell>
	        <fo:table-cell number-columns-spanned="2" padding-top="5px">
	        <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
			  <fo:table border="0.5pt solid black">
			  <fo:table-column column-width="90mm"/>
			  <fo:table-column column-width="90mm"/>
			  <fo:table-body>
			  <fo:table-row>
			  <fo:table-cell border="0.5pt solid black" padding="5px">
			  <fo:block text-align="center">
			  	Last Fiscal Year
			  </fo:block>
			  </fo:table-cell>
			  <fo:table-cell border="0.5pt solid black" padding="5px">
			  <fo:block text-align="center">
			  	Estimate for Current Year
			  </fo:block>
			  </fo:table-cell>
			  </fo:table-row>
			  <fo:table-row>
			  <fo:table-cell border="0.5pt solid black">
			  <fo:block>
			  <fo:table>
			  <fo:table-column column-width="30mm"/>
			  <fo:table-column column-width="50mm"/>
			  <fo:table-column column-width="10mm"/>
			  <fo:table-body>
			  <fo:table-row>
			  <fo:table-cell padding-top="5px" padding-left="2mm">
			  <fo:block>
			  	Year:
			  </fo:block>
			  </fo:table-cell>
			  <fo:table-cell padding-top="10px">
			  <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" border-bottom-width="0.9pt" border-bottom-style="solid" border-bottom-color="black">
                      &#160;<xsl:value-of select="response/GENERAL_FREEFORM_02/data/LastFiscalYearSeEndDate"></xsl:value-of>
               </fo:block>
               <fo:block font-size="8px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" font-style="italic" text-align="center">
	                     MM / YYYY
				 </fo:block>
			  </fo:table-cell>
			  <fo:table-cell>
			  <fo:block></fo:block>
			  </fo:table-cell>
			  </fo:table-row>
			  </fo:table-body>
			  </fo:table>
			  </fo:block>
			  </fo:table-cell>
			  <fo:table-cell border="0.5pt solid black">
			  <fo:block>
			  <fo:table>
			  <fo:table-column column-width="30mm"/>
			  <fo:table-column column-width="50mm"/>
			  <fo:table-column column-width="10mm"/>
			  <fo:table-body>
			  <fo:table-row>
			  <fo:table-cell padding-top="5px" padding-left="2mm">
			  <fo:block>
			  	Year:
			  </fo:block>
			  </fo:table-cell>
			  <fo:table-cell padding-top="10px">
			  <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" border-bottom-width="0.9pt" border-bottom-style="solid" border-bottom-color="black">
                      &#160;<xsl:value-of select="response/GENERAL_FREEFORM_02/data/EstimateCurrentYearSeEndDate"></xsl:value-of>
               </fo:block>
               <fo:block font-size="8px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" font-style="italic" text-align="center">
	                     MM / YYYY
				 </fo:block>
			  </fo:table-cell>
			  <fo:table-cell>
			  <fo:block></fo:block>
			  </fo:table-cell>
			  </fo:table-row>
			  </fo:table-body>
			  </fo:table>
			  </fo:block>
			  </fo:table-cell>
			  </fo:table-row>
			  <fo:table-row>
			  <fo:table-cell border="0.5pt solid black" padding-top="5px" padding-left="2mm">
			  <fo:block>
			  	$<xsl:value-of select="response/GENERAL_FREEFORM_02/data/LastFiscalYearGrossRevenueSe"></xsl:value-of>
			  </fo:block>
			  </fo:table-cell>
			  <fo:table-cell border="0.5pt solid black" padding-top="5px" padding-left="2mm">
			  <fo:block>
			  	$<xsl:value-of select="response/GENERAL_FREEFORM_02/data/EstimateCurrentYearGrossRevenueSe"></xsl:value-of>
			  </fo:block>
			  </fo:table-cell>
			  </fo:table-row>
			  </fo:table-body>
			  </fo:table>
			  </fo:block>
	        </fo:table-cell>
	        </fo:table-row>
	        <fo:table-row>
        	<fo:table-cell padding-top="10px">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          	8.
	          </fo:block>
	        </fo:table-cell>
	        <fo:table-cell number-columns-spanned="2" padding-left="2mm">
			<fo:block>
			  <fo:table>
			  <fo:table-column column-width="155mm"/>
			  <fo:table-column column-width="25mm"/>
			  <fo:table-body>
			  <fo:table-row>
			  <fo:table-cell padding-top="10px">
			  <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
				Is the revenue noted here included in question 3. on the main application?
			  </fo:block>
			  </fo:table-cell>
			  <fo:table-cell padding-top="10px">
			  <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left" >
                      	<xsl:choose>
                            <xsl:when test="response/GENERAL_FREEFORM_02/data/IsRevenueIncludedInMain = 'Y'">
                            Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
                            </xsl:when>
                            <xsl:when test="response/GENERAL_FREEFORM_02/data/IsRevenueIncludedInMain = 'N'">
                            Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline>
                            </xsl:when>
                            <xsl:otherwise>
                             Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
                            
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
	        </fo:table-body>
          </fo:table>
          </fo:block>
		  </xsl:if>
          <fo:block id="TheVeryLastPage"> </fo:block>
          </fo:flow>
        </fo:page-sequence>
       </fo:root>
       </xsl:template>
       </xsl:stylesheet>
      
            