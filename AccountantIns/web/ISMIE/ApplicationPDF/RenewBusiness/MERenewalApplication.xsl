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
                    <fo:block text-align="left" font-size="8px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" color="grey">ISMIE AAA-04-S002 (04/01/2022)</fo:block>
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
          <fo:block text-align="left" color="grey">
					  <fo:external-graphic src="../AccountantIns/image/ISMIE_logo.png" content-width="200px"/>
			</fo:block>
          <fo:block>
            <fo:table margin-top="5mm">
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
                    	ACCOUNTANTS PROFESSIONAL LIABILITY RENEWAL APPLICATION – MAINE
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
						NOTICE: COVERAGE FOR WHICH THIS APPLICATION IS MADE IS WRITTEN ON A CLAIMS MADE AND REPORTED BASIS MEANING, EXCEPT AS OTHERWISE PROVIDED, COVERAGE APPLIES ONLY TO <fo:inline font-weight="bold" font-style="italic">CLAIMS</fo:inline> FIRST MADE AGAINST THE <fo:inline font-weight="bold" font-style="italic">INSURED</fo:inline> AND REPORTED TO THE <fo:inline font-weight="bold" font-style="italic">INSURER</fo:inline> DURING THE <fo:inline font-weight="bold" font-style="italic">POLICY PERIOD</fo:inline> OR DURING ANY APPLICABLE EXTENDED REPORTING PERIOD.
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
						THE APPLICATION, ITS ATTACHMENTS AND ALL PREVIOUS APPLICATIONS AND
						THEIR ATTACHMENTS SHALL SERVE AS THE BASIS OF THE POLICY. THE <fo:inline font-weight="bold" font-style="italic">INSURER</fo:inline> RELIES UPON THE APPLICATION IN ISSUING THE
						POLICY. COMPLETION OF THIS APPLICATION DOES NOT IN ANY WAY IMPLY SUCH
						COVERAGE UNDER THE POLICY. COVERAGE IS AFFORDED ONLY IF AND TO THE
						EXTENT INDICATED BY THE TERMS AND CONDITIONS OF THE POLICY IF ISSUED.
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
						Wherever the word “You, Your or Firm” is used, it will be deemed to include the Firm, its personnel, its predecessors and the Firm’s affiliates.
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
              <fo:table-column column-width="1in" />
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
                      Firm Name :

                    </fo:block>
                  </fo:table-cell>
                  <fo:table-cell  padding-top="18pt"  number-columns-spanned="7">
                    <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" border-bottom-width="0.9pt" border-bottom-style="solid" border-bottom-color="black">
                      <xsl:value-of select="response/policy_freeform_01/data/AccountName"/>

                    </fo:block>
                  </fo:table-cell>
                </fo:table-row>

                <fo:table-row>
                  <fo:table-cell  padding-top="10px">
                    <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left"
											>
                      Address :&#160;&#160;

                    </fo:block>
                  </fo:table-cell>
                  <fo:table-cell  padding-top="10px">
                    <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;"
											border-bottom-width="0.9pt" border-bottom-style="solid"
											border-bottom-color="black">
                        <xsl:value-of select="response/policy_freeform_01/data/Address1"/>

                    </fo:block>
                  </fo:table-cell>

                  <fo:table-cell  padding-top="10px">
                    <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
                      &#160;&#160;City :</fo:block>
                  </fo:table-cell>
                  <fo:table-cell  padding-top="10px">
                    <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;"
											border-bottom-width="0.9pt" border-bottom-style="solid"
											border-bottom-color="black">
		             <xsl:value-of select="response/policy_freeform_01/data/City"/>
					</fo:block>

                  </fo:table-cell>
                  <fo:table-cell  padding-top="10px">
                    <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
                      &#160;&#160;State :</fo:block>
                  </fo:table-cell>
                  <fo:table-cell  padding-top="10px">
                    <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;"
											border-bottom-width="0.9pt" border-bottom-style="solid"
											border-bottom-color="black">
		             <xsl:value-of select="response/policy_freeform_01/data/StateDesc"/>
                    </fo:block>

                  </fo:table-cell>
                  <fo:table-cell  padding-top="10px">
                    <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
                      &#160;&#160;Zip :</fo:block>
                  </fo:table-cell>
                  <fo:table-cell  padding-top="10px">
                    <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;"
											border-bottom-width="0.9pt" border-bottom-style="solid"
											border-bottom-color="black">
		            <xsl:value-of select="response/policy_freeform_01/data/Zip"/>&#160;<xsl:value-of select="response/policy_freeform_01/data/Zip4"/>
		            </fo:block>
				</fo:table-cell>
				</fo:table-row>
                <fo:table-row>
                  <fo:table-cell  padding-top="10px" number-columns-spanned="8">
                    <fo:block>
                    <fo:table>
                    <fo:table-column column-width="1.4in"/>
                    <fo:table-column column-width="1.6in"/>
                    <fo:table-column column-width="1.1in"/>
                    <fo:table-column column-width="2.8in"/>
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
	                    &#160; <xsl:value-of select="response/policy_freeform_01/data/Address2"/>
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
	                    &#160; <xsl:value-of select="response/policy_freeform_01/data/CountyDesc"/>
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
	                    <xsl:value-of select="response/policy_freeform_01/data/WorkPhone"/>&#160;<xsl:value-of select="response/policy_freeform_01/data/WorkExt"/>
                       
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
	                    <xsl:value-of select="response/policy_freeform_01/data/AccountEmail"/>
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
                    <fo:table-column column-width="2.6in"/>
                    <fo:table-column column-width="1.6in"/>
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
	                    <xsl:value-of select="response/policy_freeform_01/data/ContactPerson"/>
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
	                     <xsl:value-of select="response/policy_freeform_01/data/ContactPhone"/>&#160;<xsl:value-of select="response/policy_freeform_01/data/ContactPhoneExt"/>
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
          <fo:table-column column-width="3.5in"/>
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
          <fo:table-column column-width="85mm"/>
          <fo:table-column column-width="95mm"/>
          <fo:table-body>
          <fo:table-row>
          <fo:table-cell padding-top="10px">
          <fo:block font-size="10px"  font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          	What is the total number of personnel in your Firm?
          </fo:block>
          </fo:table-cell>
          <fo:table-cell padding-top="10px">
	         <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" border-bottom-width="0.9pt" border-bottom-style="solid" border-bottom-color="black">
	                      &#160;<xsl:value-of select="response/AboutFirm_FREEFORM_01/data/TotalFirmPersonnel"></xsl:value-of>
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
          <fo:table-cell padding-top="20px">
          <fo:block font-size="10px"  font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          	2.
          </fo:block>
          </fo:table-cell>
          <fo:table-cell number-columns-spanned="2" padding-top="10px" padding-left="2mm">
          <fo:block>
          <fo:table>
          <fo:table-column column-width="120mm"/>
          <fo:table-column column-width="30mm"/>
          <fo:table-column column-width="30mm"/>
          <fo:table-body>
          <fo:table-row>
          <fo:table-cell padding-top="10px">
          <fo:block font-size="10px"  font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          	Please provide your Firm’s revenue for the last completed business year:
          </fo:block>
          </fo:table-cell>
          <fo:table-cell padding-top="10px">
	         <fo:block font-size="10px" text-align="center" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" border-bottom-width="0.9pt" border-bottom-style="solid" border-bottom-color="black">
	                      &#160;<xsl:value-of select="response/YearEndDate" />
			 </fo:block>
			 <fo:block font-size="8px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" font-style="italic" text-align="center">
	                     MM / YYYY
				 </fo:block>
		  </fo:table-cell>
          <fo:table-cell padding-top="10px" padding-left="3mm">
	         <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" border-bottom-width="0.9pt" border-bottom-style="solid" border-bottom-color="black">
	                      <xsl:value-of select="response/Amount"></xsl:value-of>
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
          <fo:table-column column-width="3.5in"/>
          <fo:table-body>
          <fo:table-row>
          <fo:table-cell padding-top="10px">
          <fo:block font-size="10px"  font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          	3.
          </fo:block>
          </fo:table-cell>
          <fo:table-cell number-columns-spanned="2" padding-top="10px" padding-left="2mm">
          <fo:block font-size="10px"  font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          What percentage of gross annual revenue comes from each Area of Practice listed below?
          </fo:block>
          </fo:table-cell>
          </fo:table-row>
          <fo:table-row>
          <fo:table-cell number-columns-spanned="3" padding-top="5px">
          	<fo:block>
	          <fo:table border="0.5pt solid black">
	          <fo:table-column column-width="70mm"/>
	          <fo:table-column column-width="14mm"/>
	          <fo:table-column column-width="14mm"/>
	          <fo:table-column column-width="70mm"/>
	          <fo:table-column column-width="14mm"/>
	          <fo:table-column column-width="14mm"/>
	          <fo:table-body>
	          <fo:table-row>
	          <fo:table-cell padding-bottom="10px" padding-top="10px" border="1pt solid black">
	          <fo:block font-weight="bold" font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="center">
	          SERVICE AREA
	          </fo:block>
	          </fo:table-cell>
	          <fo:table-cell padding-bottom="5px" padding-top="5px" border="1pt solid black">
	          <fo:block font-weight="bold" font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="center">
	          LAST YEAR’S %
	          </fo:block>
	          </fo:table-cell>
	          <fo:table-cell padding-bottom="5px" padding-top="5px" border="1pt solid black">
	          <fo:block font-weight="bold" font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="center">
	          THIS YEAR’S %
	          </fo:block>
	          </fo:table-cell>
	          <fo:table-cell padding-bottom="10px" padding-top="10px" border="1pt solid black">
	          <fo:block font-weight="bold" font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="center">
	          SERVICE AREA
	          </fo:block>
	          </fo:table-cell>
	          <fo:table-cell padding-bottom="5px" padding-top="5px" border="1pt solid black">
	          <fo:block font-weight="bold" font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="center">
	          LAST YEAR’S %
	          </fo:block>
	          </fo:table-cell>
	          <fo:table-cell padding-bottom="5px" padding-top="5px" border="1pt solid black">
	          <fo:block font-weight="bold" font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="center">
	          THIS YEAR’S %
	          </fo:block>
	          </fo:table-cell>
	          </fo:table-row>
	          <fo:table-row>
	          <fo:table-cell padding-left="2mm" padding-top="5px" border="1pt solid black" number-columns-spanned="3" background-color="LightGray">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          Tax
	          </fo:block>
	          </fo:table-cell>
	          <fo:table-cell padding-left="2mm" padding-top="5px" border="1pt solid black" number-columns-spanned="3" background-color="LightGray">
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
	          <fo:table-cell padding-bottom="2px" padding-top="2px" border="1pt solid black" padding-left="3mm">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	           <xsl:value-of select="response/AOPOld_PercentageValue_1"/> %
	          </fo:block>
	          </fo:table-cell>
	          <fo:table-cell padding-bottom="2px" padding-top="2px" border="1pt solid black" padding-left="3mm">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	           <xsl:value-of select="response/AOP_PercentageValue_1"/> %
	          </fo:block>
	          </fo:table-cell>
	          <fo:table-cell padding-bottom="2px" padding-top="2px" border="1pt solid black" padding-left="6mm">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          K.&#160; Litigation Support
	          </fo:block>
	          </fo:table-cell>
	          <fo:table-cell padding-bottom="2px" padding-top="2px" border="1pt solid black" padding-left="3mm">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          	<xsl:value-of select="response/AOPOld_PercentageValue_11"/> %
	          </fo:block>
	          </fo:table-cell>
	          <fo:table-cell padding-bottom="2px" padding-top="2px" border="1pt solid black" padding-left="3mm">
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
	          <fo:table-cell padding-bottom="2px" padding-top="2px" border="1pt solid black" padding-left="3mm">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	           <xsl:value-of select="response/AOPOld_PercentageValue_2"/> %
	          </fo:block>
	          </fo:table-cell>
	          <fo:table-cell padding-bottom="2px" padding-top="2px" border="1pt solid black" padding-left="3mm">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	           <xsl:value-of select="response/AOP_PercentageValue_2"/> %
	          </fo:block>
	          </fo:table-cell>
	          <fo:table-cell padding-bottom="2px" padding-top="2px" border="1pt solid black" padding-left="6mm">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          L.&#160; General Business Advice
	          </fo:block>
	          </fo:table-cell>
	          <fo:table-cell padding-bottom="2px" padding-top="2px" border="1pt solid black" padding-left="3mm">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          	<xsl:value-of select="response/AOPOld_PercentageValue_22"/> %
	          </fo:block>
	          </fo:table-cell>
	          <fo:table-cell padding-bottom="2px" padding-top="2px" border="1pt solid black" padding-left="3mm">
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
	          <fo:table-cell padding-bottom="2px" padding-top="2px" border="1pt solid black" padding-left="3mm">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	           <xsl:value-of select="response/AOPOld_PercentageValue_3"/> %
	          </fo:block>
	          </fo:table-cell>
	          <fo:table-cell padding-bottom="2px" padding-top="2px" border="1pt solid black" padding-left="3mm">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	           <xsl:value-of select="response/AOP_PercentageValue_3"/> %
	          </fo:block>
	          </fo:table-cell>
	          <fo:table-cell padding-bottom="2px" padding-top="2px" border="1pt solid black" padding-left="6mm">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          M.&#160; Other Consulting<fo:inline vertical-align="super" font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;">(4)</fo:inline>
	          </fo:block>
	          </fo:table-cell>
	          <fo:table-cell padding-bottom="2px" padding-top="2px" border="1pt solid black" padding-left="3mm">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          	<xsl:value-of select="response/AOPOld_PercentageValue_12"/> %
	          </fo:block>
	          </fo:table-cell>
	          <fo:table-cell padding-bottom="2px" padding-top="2px" border="1pt solid black" padding-left="3mm">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	           <xsl:value-of select="response/AOP_PercentageValue_12"/> %
	          </fo:block>
	          </fo:table-cell>
	          </fo:table-row>
	          <fo:table-row>
	          <fo:table-cell padding-left="2mm" padding-top="5px" border="1pt solid black" number-columns-spanned="3" background-color="LightGray">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          	Accounting / Bookkeeping
	          </fo:block>
	          </fo:table-cell>
	          <fo:table-cell number-columns-spanned="3" border="1pt solid black">
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
				<xsl:value-of select="response/AOPOld_CommentDesc_12"/>
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
	          <fo:table-cell padding-bottom="2px" padding-top="2px" border="1pt solid black" padding-left="3mm">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	           <xsl:value-of select="response/AOPOld_PercentageValue_4"/> %
	          </fo:block>
	          </fo:table-cell>
	          <fo:table-cell padding-bottom="2px" padding-top="2px" border="1pt solid black" padding-left="3mm">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	           <xsl:value-of select="response/AOP_PercentageValue_4"/> %
	          </fo:block>
	          </fo:table-cell>
	          <fo:table-cell number-columns-spanned="3" border="1pt solid black">
	          <fo:block>
			  <xsl:value-of select="response/AOP_CommentDesc_12"/>
			  </fo:block>
	          </fo:table-cell>
	          </fo:table-row>
	          <fo:table-row>
	          <fo:table-cell padding-left="2mm" padding-top="10px" border="1pt solid black" number-columns-spanned="3" background-color="LightGray">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          	Attestation
	          </fo:block>
	          </fo:table-cell>
	          <fo:table-cell padding-bottom="2px" padding-top="2px" border="1pt solid black" padding-left="6mm">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          N.&#160; Management Consulting / Business Planning
	          </fo:block>
	          </fo:table-cell>
	          <fo:table-cell padding-bottom="2px" padding-top="2px" border="1pt solid black" padding-left="3mm">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	           <xsl:value-of select="response/AOPOld_PercentageValue_13"/> %
	          </fo:block>
	          </fo:table-cell>
	          <fo:table-cell padding-bottom="2px" padding-top="2px" border="1pt solid black" padding-left="3mm">
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
	          <fo:table-cell padding-bottom="2px" padding-top="2px" border="1pt solid black" padding-left="3mm">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	           <xsl:value-of select="response/AOPOld_PercentageValue_5"/> %
	          </fo:block>
	          </fo:table-cell>
	          <fo:table-cell padding-bottom="2px" padding-top="2px" border="1pt solid black" padding-left="3mm">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	           <xsl:value-of select="response/AOP_PercentageValue_5"/> %
	          </fo:block>
	          </fo:table-cell>
	          <fo:table-cell padding-bottom="2px" padding-top="2px" border="1pt solid black" padding-left="6mm">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          O.&#160; Computer Related Services<fo:inline vertical-align="super">(5)</fo:inline>
	          </fo:block>
	          </fo:table-cell>
	          <fo:table-cell padding-bottom="2px" padding-top="2px" border="1pt solid black" padding-left="3mm">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          	<xsl:value-of select="response/AOPOld_PercentageValue_14"/> %
	          </fo:block>
	          </fo:table-cell>
	          <fo:table-cell padding-bottom="2px" padding-top="2px" border="1pt solid black" padding-left="3mm">
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
	          <fo:table-cell padding-bottom="2px" padding-top="2px" border="1pt solid black" padding-left="3mm">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	           <xsl:value-of select="response/AOPOld_PercentageValue_6"/> %
	          </fo:block>
	          </fo:table-cell>
	          <fo:table-cell padding-bottom="2px" padding-top="2px" border="1pt solid black" padding-left="3mm">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	           <xsl:value-of select="response/AOP_PercentageValue_6"/> %
	          </fo:block>
	          </fo:table-cell>
	          <fo:table-cell padding-bottom="2px" padding-top="2px" border="1pt solid black" padding-left="2mm" number-columns-spanned="3">
	          <fo:block font-size="7.5px" font-style="italic" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	           <fo:inline vertical-align="super">(5)</fo:inline> Complete the Computer Related Services Supplement if an entry in O above
	          </fo:block>
	          </fo:table-cell>
	          </fo:table-row>
	          <fo:table-row>
	          <fo:table-cell padding-bottom="2px" padding-top="2px" border="1pt solid black" padding-left="2mm" number-columns-spanned="3">
	          <fo:block font-size="7.5px" font-style="italic" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	           <fo:inline vertical-align="super">(1)</fo:inline> Complete the Audit Supplement if an entry in E or F above
	          </fo:block>
	          </fo:table-cell>
	          <fo:table-cell padding-bottom="2px" padding-top="2px" border="1pt solid black" padding-left="6mm">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          P.&#160; Business Valuation
	          </fo:block>
	          </fo:table-cell>
	          <fo:table-cell padding-bottom="2px" padding-top="2px" border="1pt solid black" padding-left="3mm">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	           <xsl:value-of select="response/AOPOld_PercentageValue_15"/> %
	          </fo:block>
	          </fo:table-cell>
	          <fo:table-cell padding-bottom="2px" padding-top="2px" border="1pt solid black" padding-left="3mm">
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
	          <fo:table-cell padding-bottom="2px" padding-top="2px" border="1pt solid black" padding-left="3mm">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	           <xsl:value-of select="response/AOPOld_PercentageValue_7"/> %
	          </fo:block>
	          </fo:table-cell>
	          <fo:table-cell padding-bottom="2px" padding-top="2px" border="1pt solid black" padding-left="3mm">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	           <xsl:value-of select="response/AOP_PercentageValue_7"/> %
	          </fo:block>
	          </fo:table-cell>
	          <fo:table-cell padding-bottom="2px" padding-top="2px" border="1pt solid black" padding-left="6mm">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          Q.&#160; Forecasts / Projections
	          </fo:block>
	          </fo:table-cell>
	          <fo:table-cell padding-bottom="2px" padding-top="2px" border="1pt solid black" padding-left="3mm">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          	<xsl:value-of select="response/AOPOld_PercentageValue_16"/> %
	          </fo:block>
	          </fo:table-cell>
	          <fo:table-cell padding-bottom="2px" padding-top="2px" border="1pt solid black" padding-left="3mm">
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
	          <fo:table-cell padding-bottom="2px" padding-top="2px" border="1pt solid black" padding-left="3mm">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	           <xsl:value-of select="response/AOPOld_PercentageValue_8"/> %
	          </fo:block>
	          </fo:table-cell>
	          <fo:table-cell padding-bottom="2px" padding-top="2px" border="1pt solid black" padding-left="3mm">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	           <xsl:value-of select="response/AOP_PercentageValue_8"/> %
	          </fo:block>
	          </fo:table-cell>
	          <fo:table-cell padding-bottom="2px" padding-top="2px" border="1pt solid black" padding-left="6mm">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          R.&#160; Mergers / Acquisitions
	          </fo:block>
	          </fo:table-cell>
	          <fo:table-cell padding-bottom="2px" padding-top="2px" border="1pt solid black" padding-left="3mm">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          	<xsl:value-of select="response/AOPOld_PercentageValue_17"/> %
	          </fo:block>
	          </fo:table-cell>
	          <fo:table-cell padding-bottom="2px" padding-top="2px" border="1pt solid black" padding-left="3mm">
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
	          <fo:table-cell padding-bottom="2px" padding-top="2px" border="1pt solid black" padding-left="3mm">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          	<xsl:value-of select="response/AOPOld_PercentageValue_9"/> %
	          </fo:block>
	          </fo:table-cell>
	          <fo:table-cell padding-bottom="2px" padding-top="2px" border="1pt solid black" padding-left="3mm">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	           <xsl:value-of select="response/AOP_PercentageValue_9"/> %
	          </fo:block>
	          </fo:table-cell>
	          <fo:table-cell padding-left="2mm" padding-top="5px" border="1pt solid black" number-columns-spanned="3" background-color="LightGray">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          	Special Services
	          </fo:block>
	          </fo:table-cell>
	          </fo:table-row>
	          <fo:table-row>
	          <fo:table-cell number-columns-spanned="3" border="1pt solid black">
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
				<xsl:value-of select="response/AOPOld_CommentDesc_9"/>
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
	          <fo:table-cell padding-bottom="2px" padding-top="2px" border="1pt solid black" padding-left="3mm">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          	<xsl:value-of select="response/AOPOld_PercentageValue_18"/> %
	          </fo:block>
	          </fo:table-cell>
	          <fo:table-cell padding-bottom="2px" padding-top="2px" border="1pt solid black" padding-left="3mm">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	           <xsl:value-of select="response/AOP_PercentageValue_18"/> %
	          </fo:block>
	          </fo:table-cell>
	          </fo:table-row>
	          <fo:table-row>
	          <fo:table-cell padding-bottom="2px" padding-top="2px" border="1pt solid black" padding-left="6mm" number-columns-spanned="3">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
			  <xsl:value-of select="response/AOP_CommentDesc_9"/>
	          </fo:block>
	          </fo:table-cell>
	          <fo:table-cell padding-bottom="2px" padding-top="2px" border="1pt solid black" padding-left="2mm" number-columns-spanned="3">
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
	          <fo:table-cell padding-bottom="2px" padding-top="2px" border="1pt solid black" padding-left="3mm">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	           <xsl:value-of select="response/AOPOld_PercentageValue_10"/> %
	          </fo:block>
	          </fo:table-cell>
	          <fo:table-cell padding-bottom="2px" padding-top="2px" border="1pt solid black" padding-left="3mm">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	           <xsl:value-of select="response/AOP_PercentageValue_10"/> %
	          </fo:block>
	          </fo:table-cell>
	          <fo:table-cell padding-bottom="2px" padding-top="2px" border="1pt solid black" padding-left="6mm">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          T.&#160; SEC Work other than Audit
	          </fo:block>
	          </fo:table-cell>
	          <fo:table-cell padding-bottom="2px" padding-top="2px" border="1pt solid black" padding-left="3mm">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          	<xsl:value-of select="response/AOPOld_PercentageValue_21"/> %
	          </fo:block>
	          </fo:table-cell>
	          <fo:table-cell padding-bottom="2px" padding-top="2px" border="1pt solid black" padding-left="3mm">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	           <xsl:value-of select="response/AOP_PercentageValue_21"/> %
	          </fo:block>
	          </fo:table-cell>
	          </fo:table-row>
	          <fo:table-row>
	          <fo:table-cell number-columns-spanned="3" border="1pt solid black">
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
				<xsl:value-of select="response/AOPOld_CommentDesc_10"/>
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
	          <fo:table-cell padding-bottom="2px" padding-top="2px" border="1pt solid black" padding-left="3mm">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          	<xsl:value-of select="response/AOPOld_PercentageValue_19"/> %
	          </fo:block>
	          </fo:table-cell>
	          <fo:table-cell padding-bottom="2px" padding-top="2px" border="1pt solid black" padding-left="3mm">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	           <xsl:value-of select="response/AOP_PercentageValue_19"/> %
	          </fo:block>
	          </fo:table-cell>
	          </fo:table-row>
	          <fo:table-row>
	          <fo:table-cell number-columns-spanned="3">
	          	<fo:block>
				<xsl:value-of select="response/AOP_CommentDesc_10"/>
	          	</fo:block>
	          	</fo:table-cell>
	          <fo:table-cell padding-bottom="2px" padding-top="2px" border="1pt solid black" padding-left="2mm" number-columns-spanned="3">
	          <fo:block font-size="7.5px" font-style="italic" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          <fo:inline vertical-align="super">(7)</fo:inline> Complete the Investment/Financial Planning Supplement if entry in U above
	          </fo:block>
	          </fo:table-cell>
	          </fo:table-row>
	          <fo:table-row>
	          <fo:table-cell padding-bottom="2px" padding-top="2px" border="1pt solid black" padding-left="2mm" number-columns-spanned="3">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          </fo:block>
	          </fo:table-cell>
	          <fo:table-cell padding-bottom="2px" padding-top="2px" border="1pt solid black" padding-left="6mm">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          V.&#160; ERISA / Pension Plans
	          </fo:block>
	          </fo:table-cell>
	          <fo:table-cell padding-bottom="2px" padding-top="2px" border="1pt solid black" padding-left="3mm">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          	<xsl:value-of select="response/AOPOld_PercentageValue_23"/> %
	          </fo:block>
	          </fo:table-cell>
	          <fo:table-cell padding-bottom="2px" padding-top="2px" border="1pt solid black" padding-left="3mm">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          	<xsl:value-of select="response/AOP_PercentageValue_23"/> %
	          </fo:block>
	          </fo:table-cell>
	          </fo:table-row>
	          <fo:table-row>
	          <fo:table-cell padding-bottom="2px" padding-top="2px" border="1pt solid black" padding-left="2mm" number-columns-spanned="3">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          </fo:block>
	          </fo:table-cell>
	          <fo:table-cell padding-bottom="2px" padding-top="2px" border="1pt solid black" padding-left="6mm">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          W.&#160; Trustee Services
	          </fo:block>
	          </fo:table-cell>
	          <fo:table-cell padding-bottom="2px" padding-top="2px" border="1pt solid black" padding-left="3mm">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          	<xsl:value-of select="response/AOPOld_PercentageValue_24"/> %
	          </fo:block>
	          </fo:table-cell>
	          <fo:table-cell padding-bottom="2px" padding-top="2px" border="1pt solid black" padding-left="3mm">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          	<xsl:value-of select="response/AOP_PercentageValue_24"/> %
	          </fo:block>
	          </fo:table-cell>
	          </fo:table-row>
	          <fo:table-row>
	          <fo:table-cell padding-bottom="2px" padding-top="2px" border="1pt solid black" padding-left="2mm" number-columns-spanned="3">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          </fo:block>
	          </fo:table-cell>
	          <fo:table-cell padding-bottom="2px" padding-top="2px" border="1pt solid black" padding-left="6mm">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          X.&#160; Other(8)
	          </fo:block>
	          </fo:table-cell>
	          <fo:table-cell padding-bottom="2px" padding-top="2px" border="1pt solid black" padding-left="3mm">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          	<xsl:value-of select="response/AOPOld_PercentageValue_20"/> %
	          </fo:block>
	          </fo:table-cell>
	          <fo:table-cell padding-bottom="2px" padding-top="2px" border="1pt solid black" padding-left="3mm">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          	<xsl:value-of select="response/AOP_PercentageValue_20"/> %
	          </fo:block>
	          </fo:table-cell>
	          </fo:table-row>
	          <fo:table-row>
	          <fo:table-cell padding-bottom="2px" padding-top="2px" border="1pt solid black" padding-left="2mm" number-columns-spanned="3">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          </fo:block>
	          </fo:table-cell>
	          <fo:table-cell number-columns-spanned="3" border="1pt solid black">
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
				<xsl:value-of select="response/AOPOld_CommentDesc_20"/>
	          	</fo:block>
	          	</fo:table-cell>
	          	</fo:table-row>
	          	</fo:table-body>
	          	</fo:table>
	          </fo:block>
	          </fo:table-cell>
	          </fo:table-row>
	          <fo:table-row>
	          <fo:table-cell padding-bottom="2px" padding-top="2px" border="1pt solid black" padding-left="2mm" number-columns-spanned="3">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          &#160;
	          </fo:block>
	          </fo:table-cell>
	          <fo:table-cell padding-bottom="2px" padding-top="2px" border="1pt solid black" padding-left="2mm" number-columns-spanned="3">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
				<xsl:value-of select="response/AOP_CommentDesc_20"/>
	          </fo:block>
	          </fo:table-cell>
	          </fo:table-row>
	          <fo:table-row>
	          <fo:table-cell padding-bottom="2px" padding-top="2px" border="1pt solid black" padding-left="2mm" number-columns-spanned="3">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          </fo:block>
	          </fo:table-cell>
	          <fo:table-cell padding-bottom="2px" padding-top="2px" border="1pt solid black" padding-left="2mm">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" font-style="italic" font-weight="bold" text-align="left">
	          Total of all items must equal 100%:
	          </fo:block>
	          </fo:table-cell>
	          <fo:table-cell padding-bottom="2px" padding-top="2px" border="1pt solid black" padding-left="3mm">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          	<xsl:value-of select="response/aopold_total"/>%
	          </fo:block>
	          </fo:table-cell>
			  <fo:table-cell padding-bottom="2px" padding-top="2px" border="1pt solid black" padding-left="3mm">
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          	<xsl:value-of select="response/aop_total"/>%
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
	           If your revenue is above $100,000, within the last year, have you merged with or acquired the  
		  </fo:block>
		  </fo:table-cell>
          </fo:table-row>
          <fo:table-row>
          <fo:table-cell>
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	           business of any sole practitioner, accounting firm or other business entity?
		  </fo:block>
		  </fo:table-cell>
		  <fo:table-cell>
		  <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left" >
                      	<xsl:choose>
                            <xsl:when test="response/firm_freeform_01/data/IsAnyFirmMeargedOrAcquired = 'Y'">
                            Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
                            </xsl:when>
                            <xsl:when test="response/firm_freeform_01/data/IsAnyFirmMeargedOrAcquired = 'N'">
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
          	Within the past year have you entered into any new engagement requiring you to control or  
          </fo:block>
          </fo:table-cell>
          </fo:table-row>
          <fo:table-row>
          <fo:table-cell>
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          	disburse client funds other than for payroll or bookkeeping services?
          </fo:block>
          </fo:table-cell>
          <fo:table-cell>
		  <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left" >
                      	<xsl:choose>
                            <xsl:when test="response/firm_freeform_01/data/IsControlClientFunds = 'Y'">
                            Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
                            </xsl:when>
                            <xsl:when test="response/firm_freeform_01/data/IsControlClientFunds = 'N'">
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
          <fo:block font-size="9px" font-weight="bold" font-style="italic" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
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
          	If your revenue is above $100,000, has any client during the last year represented 40% or more of 
          </fo:block>
          </fo:table-cell>
		  </fo:table-row>
		  <fo:table-row>
          <fo:table-cell>
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          	your revenue?
          </fo:block>
          </fo:table-cell>
          <fo:table-cell>
		  <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left" >
                      	<xsl:choose>
                            <xsl:when test="response/firm_freeform_01/data/IsFirmsRevenueMorethan25Percent = 'Y'">
                            Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
                            </xsl:when>
                            <xsl:when test="response/firm_freeform_01/data/IsFirmsRevenueMorethan25Percent = 'N'">
                            Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline>
                            </xsl:when>
                            <xsl:otherwise>
                             Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
                            
                            </xsl:otherwise>
                           </xsl:choose>
                    </fo:block>
		  </fo:table-cell>
          </fo:table-row>
          <xsl:if test="response/firm_freeform_01/data/IsFirmsRevenueMorethan25Percent != 'N' and (response/StatusDesc != 'Issued' or response/firm_freeform_01/data/IsFirmsRevenueMorethan25Percent = 'Y')">
          <fo:table-row>
          <fo:table-cell padding-top="10px">
          <fo:block font-size="9px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          	If Yes, please indicate percentage of revenue from largest revenue client (include client’s affiliated entities):
          </fo:block>
          </fo:table-cell>
          <fo:table-cell padding-top="10px">
	         <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" border-bottom-width="0.9pt" border-bottom-style="solid" border-bottom-color="black">
	                     &#160;<xsl:value-of select="response/firm_freeform_01/data/PercentOfRevenueFromLargestClient"></xsl:value-of>
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
	              &#160;<xsl:value-of select="response/firm_freeform_01/data/ServiceIndustryofLargestClient"></xsl:value-of>
			 </fo:block>
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
          </fo:table-body>
          </fo:table>
          </fo:block>
          <fo:block page-break-before="always"/>
          <fo:block>
          <fo:table>
          <fo:table-column column-width="0.3in"/>
          <fo:table-column column-width="4in"/>
          <fo:table-column column-width="3.5in"/>
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
          	Within the past year have you entered into a new engagement serving as a trustee, co-trustee,  
          </fo:block>
          </fo:table-cell>
          </fo:table-row>
          <fo:table-row>
          <fo:table-cell>
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          	executor, administrator or personal representative for any client?
          </fo:block>
          </fo:table-cell>
          <fo:table-cell>
		  <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left" >
                      	<xsl:choose>
                            <xsl:when test="response/firm_freeform_01/data/IsServedAsTrustee = 'Y'">
                            Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
                            </xsl:when>
                            <xsl:when test="response/firm_freeform_01/data/IsServedAsTrustee = 'N'">
                            Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline>
                            </xsl:when>
                            <xsl:otherwise>
                             Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
                            
                            </xsl:otherwise>
                           </xsl:choose>
                    </fo:block>
		  </fo:table-cell>
          </fo:table-row>
          <xsl:if test="response/firm_freeform_01/data/IsServedAsTrustee != 'N' and (response/StatusDesc != 'Issued' or response/firm_freeform_01/data/IsServedAsTrustee = 'Y')">
          <fo:table-row>
          <fo:table-cell padding-top="10px">
          <fo:block font-size="9px" font-style="italic" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          	If Yes to question 7, please complete the following information for each new trust or estate below:
          </fo:block>
          </fo:table-cell>
          </fo:table-row>
          </xsl:if>
          </fo:table-body>
          </fo:table>
          </fo:block>
          </fo:table-cell>
          </fo:table-row>
          <xsl:if test="response/firm_freeform_01/data/IsServedAsTrustee != 'N' and (response/StatusDesc != 'Issued' or response/firm_freeform_01/data/IsServedAsTrustee = 'Y')">
          <fo:table-row>
          <fo:table-cell padding-top="5px" number-columns-spanned="3">
          <fo:block>
          <fo:table border="0.5pt solid black">
          <fo:table-column column-width="73mm"/>
          <fo:table-column column-width="30mm"/>
          <fo:table-column column-width="43mm"/>
          <fo:table-column column-width="50mm"/>
          <fo:table-body>
          <fo:table-row>
          <fo:table-cell padding-bottom="10px" padding-top="10px" border="0.5pt solid black">
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="center">
         	 Name of Trust or Estate
          </fo:block>
          </fo:table-cell>
          <fo:table-cell padding-bottom="10px" padding-top="10px" border="0.5pt solid black">
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="center">
         	 *Type
          </fo:block>
          </fo:table-cell>
          <fo:table-cell padding-bottom="5px" padding-top="5px" border="0.5pt solid black">
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="center">
         	 Value of Assets
          </fo:block>
          </fo:table-cell>
          <fo:table-cell padding-bottom="5px" padding-top="5px" border="0.5pt solid black">
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="center">
         	 **Beneficiary Interest?
          </fo:block>
          </fo:table-cell>
          </fo:table-row>
		  <xsl:if test="response/firm_freeform_01/data/IsServedAsTrustee != 'N' and (response/StatusDesc != 'Issued' or response/firm_freeform_01/data/IsServedAsTrustee = 'Y')">
		  <xsl:for-each select="response/general_list_01/data">
          <fo:table-row>
          <fo:table-cell padding-bottom="5px" padding-top="5px" border="0.5pt solid black" padding-left="2mm">
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="center">
         	 <xsl:value-of select="NameOfTrust"/>
          </fo:block>
          </fo:table-cell>
          <fo:table-cell padding-bottom="5px" padding-top="5px" border="0.5pt solid black" padding-left="2mm">
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="center">
         	 <xsl:value-of select="TypeofTrustDesc"/>
          </fo:block>
          </fo:table-cell>
          <fo:table-cell padding-bottom="5px" padding-top="5px" border="0.5pt solid black" padding-left="2mm">
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="center">
         	 <xsl:value-of select="ValueOfAssetsTrustformat"/>
          </fo:block>
          </fo:table-cell>
          <fo:table-cell padding-bottom="5px" padding-top="5px" border="0.5pt solid black" padding-left="2mm">
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="center">
         	<xsl:choose>
                            <xsl:when test="IsBeneficiaryInterestTrust = 'Y'">
                            Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
                            </xsl:when>
                            <xsl:when test="IsBeneficiaryInterestTrust = 'N'">
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
		  </xsl:if>  
          </fo:table-body>
          </fo:table>
          </fo:block>
          </fo:table-cell>
          </fo:table-row>
          <fo:table-row>
          <fo:table-cell padding-top="5px" number-columns-spanned="3">
          <fo:block>
          <fo:table>
          <fo:table-column column-width="195mm"/>
          <fo:table-body>
          <fo:table-row>
          <fo:table-cell padding-top="5px" padding-left="2mm">
          <fo:block font-size="8.5px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          *&#160; <fo:inline font-weight="bold">E</fo:inline> = Estate <fo:inline font-weight="bold">P</fo:inline> = Personal/Family trusts <fo:inline font-weight="bold">B</fo:inline> = Business trusts <fo:inline font-weight="bold">F</fo:inline> = Foundations <fo:inline font-weight="bold">C</fo:inline> = Charities <fo:inline font-weight="bold">R</fo:inline> = Real Estate <fo:inline font-weight="bold">I</fo:inline> = Irrevocable Life Insurance Trusts
          </fo:block>
          </fo:table-cell>
          </fo:table-row>
          <fo:table-row>
          <fo:table-cell padding-top="5px" padding-left="2mm">
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
          	Within the past year have you entered into a new engagement serving as an officer, director, 
          </fo:block>
          </fo:table-cell>
          </fo:table-row>
          <fo:table-row>
          <fo:table-cell>
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          	partner, manager or other member of a client’s governing body?  
          </fo:block>
          </fo:table-cell>
          <fo:table-cell>
		  <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left" >
                      	<xsl:choose>
                            <xsl:when test="response/firm_freeform_01/data/IsFirmRenderedServiceForAnyClient = 'Y'">
                            Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
                            </xsl:when>
                            <xsl:when test="response/firm_freeform_01/data/IsFirmRenderedServiceForAnyClient = 'N'">
                            Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline>
                            </xsl:when>
                            <xsl:otherwise>
                             Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
                            
                            </xsl:otherwise>
                           </xsl:choose>
                    </fo:block>
		  </fo:table-cell>
          </fo:table-row>
          <xsl:if test="response/firm_freeform_01/data/IsFirmRenderedServiceForAnyClient != 'N' and (response/StatusDesc != 'Issued' or response/firm_freeform_01/data/IsFirmRenderedServiceForAnyClient = 'Y')">
          <fo:table-row>
          <fo:table-cell>
          <fo:block font-size="9px" font-style="italic" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          	If Yes to question 8, please complete the following information below:
          </fo:block>
          </fo:table-cell>
          </fo:table-row>
          </xsl:if>
          </fo:table-body>
          </fo:table>
          </fo:block>
          </fo:table-cell>
          </fo:table-row>
          <xsl:if test="response/firm_freeform_01/data/IsFirmRenderedServiceForAnyClient != 'N' and (response/StatusDesc != 'Issued' or response/firm_freeform_01/data/IsFirmRenderedServiceForAnyClient = 'Y')">
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
          <xsl:for-each select="response/general_list_04/data">
          <fo:table-row>
          <fo:table-cell padding-bottom="5px" padding-top="5px" border="0.5pt solid black" padding-left="2mm">
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="center">
         	 <xsl:value-of select="ClientNameOsi"/>
          </fo:block>
          </fo:table-cell>
          <fo:table-cell padding-bottom="5px" padding-top="5px" border="0.5pt solid black" padding-left="2mm">
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="center">
         	 <xsl:value-of select="EntityNameOsi"/>
          </fo:block>
          </fo:table-cell>
          <fo:table-cell padding-bottom="5px" padding-top="5px" border="0.5pt solid black" padding-left="2mm">
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="center">
         	 <xsl:value-of select="ClientIndustryDesc"/>
          </fo:block>
          </fo:table-cell>
          <fo:table-cell padding-bottom="5px" padding-top="5px" border="0.5pt solid black" padding-left="2mm">
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="center">
         	<xsl:value-of select="PositionHeldOsi"/>
          </fo:block>
          </fo:table-cell>
          <fo:table-cell padding-bottom="5px" padding-top="5px" border="0.5pt solid black" padding-left="2mm">
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="center" >
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
         	<xsl:value-of select="PercentEquityInterestofOsi"/>
          </fo:block>
          </fo:table-cell>
          <fo:table-cell padding-bottom="5px" padding-top="5px" border="0.5pt solid black" padding-left="2mm">
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="center">
         	 <xsl:value-of select="ServiceIndustryDesc"/>
          </fo:block>
          </fo:table-cell>
          <fo:table-cell padding-bottom="5px" padding-top="5px" border="0.5pt solid black" padding-left="2mm">
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="center" >
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
          	Within the past year, have you initiated lawsuits or arbitration procedures to enforce the collection 
          </fo:block>
          </fo:table-cell>
		  </fo:table-row>
		  <fo:table-row>
          <fo:table-cell>
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          	of unpaid fees of any client?
          </fo:block>
          </fo:table-cell>
          <fo:table-cell>
		  <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left" >
                      	<xsl:choose>
                            <xsl:when test="response/firm_freeform_01/data/IsSuitInstituted = 'Y'">
                            Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
                            </xsl:when>
                            <xsl:when test="response/firm_freeform_01/data/IsSuitInstituted = 'N'">
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
          <fo:table-cell padding-top="5px">
          <fo:block font-size="9px" font-style="italic" font-weight="bold" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          	If Yes, please complete the FEE SUIT SUPPLEMENT.
          </fo:block>
          </fo:table-cell>
          </fo:table-row>
          <xsl:if test="(response/CYBERIMPLFLAG = true)">
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
	          	Are you interested in obtaining Cyber coverage?
	          </fo:block>
	          </fo:table-cell>
	          </fo:table-row>
	          <fo:table-row>
	          <fo:table-cell>
			  <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left" >
	                      	<xsl:choose>
	                            <xsl:when test="response/firm_freeform_01/data/needCyberCoverage = 'Y'">
	                            Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
	                            </xsl:when>
	                            <xsl:when test="response/firm_freeform_01/data/needCyberCoverage = 'N'">
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
	        </xsl:if>
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
          <fo:table-cell padding-top="10px" number-columns-spanned="2">
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          	After inquiry of all owners, partners, officers and other professional staff of the Firm, within the past year have any past or present personnel: 
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
                            <xsl:when test="response/firm_freeform_01/data/IsAnyRegulatoryInquiry = 'Y'">
                            Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
                            </xsl:when>
                            <xsl:when test="response/firm_freeform_01/data/IsAnyRegulatoryInquiry = 'N'">
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
          </fo:table-cell>
          </fo:table-row>
          <fo:table-row>
          <fo:table-cell padding-left="7mm">
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" border-bottom-width="0.9pt" border-bottom-style="solid" border-bottom-color="black">
                      <xsl:value-of select="response/firm_freeform_01/data/RegulatoryInquiryExaplain"></xsl:value-of>

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
          <fo:table-cell padding-top="10px">
		  <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left" >
                      	<xsl:choose>
                            <xsl:when test="response/firm_freeform_01/data/IsAwarenessOfAnyProfLiability = 'Y'">
                            Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
                            </xsl:when>
                            <xsl:when test="response/firm_freeform_01/data/IsAwarenessOfAnyProfLiability = 'N'">
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
                            <xsl:when test="response/firm_freeform_01/data/IsAwarenessOfAnyProfLiabilitySuitAgainst = 'Y'">
                            Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
                            </xsl:when>
                            <xsl:when test="response/firm_freeform_01/data/IsAwarenessOfAnyProfLiabilitySuitAgainst = 'N'">
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
          <fo:table-cell padding-top="10px" number-columns-spanned="2">
          <fo:block font-weight="bold" font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" font-style="italic" text-align="left">
          	If Yes to 10 b or c above, please complete the CLAIM SUPPLEMENT for each claim or potential claim.
          </fo:block>
          </fo:table-cell>
          </fo:table-row>
          <fo:table-row>
          <fo:table-cell padding-top="9.5px" number-columns-spanned="2">
          <fo:block font-weight="bold" font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          	NOTE: THE POLICY FOR WHICH THIS APPLICATION IS BEING MADE SHALL NOT APPLY TO ANY INCIDENTS OR CLAIMS DETAILED OR WHICH SHOULD HAVE BEEN DETAILED IN QUESTION 10 a, b or c ABOVE.
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
          <!-- <fo:block space-after="4mm" font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="justify">
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
          </fo:block> -->
          <xsl:choose>
          <xsl:when test="response/address_freeform_01/data/StateCode= 'WV' or response/address_freeform_01/data/StateCode= 'AL' 
          or response/address_freeform_01/data/StateCode= 'AR' or response/address_freeform_01/data/StateCode= 'LA'
          or response/address_freeform_01/data/StateCode= 'NM' or response/address_freeform_01/data/StateCode= 'RI'">
          <fo:block space-after="4mm" font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="justify">
		    <fo:inline font-weight="bold">NOTICE TO ALABAMA, ARKANSAS, LOUISIANA, NEW MEXICO, RHODE ISLAND AND
			WEST VIRGINIA APPLICANTS:</fo:inline> Any person who knowingly presents a false or
			fraudulent claim for payment of a loss or benefit or knowingly presents
			false information in an application for insurance is guilty of a crime
			and may be subject to fines and confinement in prison.
          </fo:block>
          </xsl:when>
          <xsl:when test="response/address_freeform_01/data/StateCode= 'CO'">
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
          <xsl:when test="response/address_freeform_01/data/StateCode= 'DC'">
          <fo:block space-after="4mm" font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="justify">
			<fo:inline font-weight="bold">NOTICE TO DISTRICT OF COLUMBIA APPLICANTS: WARNING:</fo:inline> It is a crime to
			provide false or misleading information to an insurer for the purpose
			of defrauding the insurer or any other person. Penalties include
			imprisonment and/or fines. In addition, an insurer may deny insurance
			benefits if false information materially related to a claim was
			provided by the applicant.
          </fo:block>
          </xsl:when>
          <xsl:when test="response/address_freeform_01/data/StateCode= 'FL'">
          <fo:block space-after="4mm" font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="justify">
			<fo:inline font-weight="bold">NOTICE TO FLORIDA APPLICANTS:</fo:inline> Any person who knowingly and with intent
			to injure, defraud, or deceive any insurer files a statement of claim
			or an application containing any false, incomplete, or misleading
			information is guilty of a felony of the third degree.
          </fo:block>
          </xsl:when>
          <xsl:when test="response/address_freeform_01/data/StateCode= 'KS'">
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
          <xsl:when test="response/address_freeform_01/data/StateCode= 'KY'">
          <fo:block space-after="4mm" font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="justify">
			<fo:inline font-weight="bold">NOTICE TO KENTUCKY APPLICANTS:</fo:inline> Any person who knowingly and with intent
			to defraud any insurance company or other person files an application
			for insurance containing any materially false information or conceals,
			for the purpose of misleading, information concerning any fact material
			thereto commits a fraudulent insurance act, which is a crime.
          </fo:block>
          </xsl:when>
          <xsl:when test="response/address_freeform_01/data/StateCode= 'ME'">
          <fo:block space-after="4mm" font-size="10px"
				font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;"
				text-align="justify">
				<fo:inline font-weight="bold">NOTICE TO MAINE APPLICANTS:
				</fo:inline>
				It is a crime to knowingly provide false, incomplete or misleading
				information to an insurance company for the purpose of defrauding the
				company. Penalties may include imprisonment, fines or denial of
				insurance benefits.
			</fo:block>
          </xsl:when>
          <xsl:when test="response/address_freeform_01/data/StateCode= 'MD'">
          <fo:block space-after="4mm" font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="justify">
			<fo:inline font-weight="bold">NOTICE OF UNDERWITING:</fo:inline> Any person who knowingly or willfully
			presents a false or fraudulent claim for payment of a loss or benefit
			or who knowingly or willfully presents false information in an
			application for insurance is guilty of a crime and may be subject to
			fines and confinement in prison.
          </fo:block>
          </xsl:when>
          <xsl:when test="response/address_freeform_01/data/StateCode= 'NJ'">
          <fo:block space-after="4mm" font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="justify">
			<fo:inline font-weight="bold">NOTICE TO NEW JERSEY APPLICANTS:</fo:inline> Any person who includes any false or
			misleading information on an application for an insurance policy is
			subject to criminal and civil penalties.
          </fo:block>
          </xsl:when>
          <xsl:when test="response/address_freeform_01/data/StateCode= 'NY'">
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
          <xsl:when test="response/address_freeform_01/data/StateCode= 'OH'">
          <fo:block space-after="4mm" font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="justify">
			<fo:inline font-weight="bold">NOTICE TO OHIO APPLICANTS:</fo:inline> Any person who, with intent to defraud or
			knowing that he is facilitating a fraud against an insurer, submits an
			application or files a claim containing a false or deceptive statement
			is guilty of insurance fraud.
          </fo:block>
          </xsl:when>
          <xsl:when test="response/address_freeform_01/data/StateCode= 'OK'">
          <fo:block space-after="4mm" font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="justify">
			<fo:inline font-weight="bold">NOTICE TO OKLAHOMA APPLICANTS:</fo:inline> WARNING: Any person who knowingly, and
			with intent to injure, defraud or deceive any insurer, makes any claim
			for the proceeds of an insurance policy containing any false,
			incomplete or misleading information is guilty of a felony.
          </fo:block>
          </xsl:when>
          <xsl:when test="response/address_freeform_01/data/StateCode= 'OR'">
          <fo:block space-after="4mm" font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="justify">
			<fo:inline font-weight="bold">NOTICE TO OREGON APPLICANTS:</fo:inline> Any person who knowingly and with intent
			to defraud or solicit another to defraud the insurer by submitting an
			application containing a false statement as to any material fact may be
			violating state law.
          </fo:block>
          </xsl:when>
          <xsl:when test="response/address_freeform_01/data/StateCode= 'TN' or response/address_freeform_01/data/StateCode= 'VA' 
          or response/address_freeform_01/data/StateCode= 'WA'">
          <fo:block space-after="4mm" font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="justify">
			<fo:inline font-weight="bold">NOTICE TO TENNESSEE, VIRGINIA AND WASHINGTON APPLICANTS:</fo:inline> It is a crime
			to knowingly provide false, incomplete, or misleading information to an
			insurance company for the purpose of defrauding the company. Penalties
			include imprisonment, fines, and denial of insurance benefits.
          </fo:block>
          </xsl:when>
          <xsl:when test="response/address_freeform_01/data/StateCode= 'VT'">
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
			correct the application or materials submitted therewith.
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
			        	<xsl:when test="response/address_freeform_01/data/StateCode ='FL'">			        
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
			        	<xsl:when test="response/address_freeform_01/data/StateCode ='FL'">			        
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
          </fo:block> -->
          <!-- <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" space-after="4mm">
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
			        	<xsl:when test="response/address_freeform_01/data/StateCode ='IA'">			        
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
          </fo:block> -->
         <!--  <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" space-after="4mm">
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
			        	<xsl:when test="response/address_freeform_01/data/StateCode ='NH'">			        
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
			        	<xsl:when test="response/address_freeform_01/data/StateCode ='NH'">			        
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
          <!-- <fo:block page-break-before="always"/> -->
		  <xsl:if test="(response/StatusDesc != 'Issued' and response/aop_total != 100) or response/showSupplementSuppPDF = 'Y'">
          <fo:block space-before="8mm">
            <fo:table margin-top="1mm" border="1pt solid black" background-color="LightGray ">
              <fo:table-column></fo:table-column>
              <fo:table-body>
                <fo:table-row>
                  <fo:table-cell>
                    <fo:block margin-top="1mm" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" font-weight="bold" font-size="10px">
                    INVESTMENT / FINANCIAL PLANNING SUPPLEMENT (<fo:inline font-style="italic">As required in question 3.U.</fo:inline>)
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
                            <xsl:when test="response/invest_finan_planing_freeform_01/data/IsFinancialPlansPreparing = 'Y'">
                            Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
                            </xsl:when>
                            <xsl:when test="response/invest_finan_planing_freeform_01/data/IsFinancialPlansPreparing = 'N'">
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
                            <xsl:when test="response/invest_finan_planing_freeform_01/data/IsDiscretionaryAssetMangt = 'Y'">
                            Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
                            </xsl:when>
                            <xsl:when test="response/invest_finan_planing_freeform_01/data/IsDiscretionaryAssetMangt = 'N'">
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
                            <xsl:when test="response/invest_finan_planing_freeform_01/data/IsNonDiscretionaryAssetMangt = 'Y'">
                            Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
                            </xsl:when>
                            <xsl:when test="response/invest_finan_planing_freeform_01/data/IsNonDiscretionaryAssetMangt = 'N'">
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
                            <xsl:when test="response/invest_finan_planing_freeform_01/data/IsSecuritiesSales = 'Y'">
                            Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
                            </xsl:when>
                            <xsl:when test="response/invest_finan_planing_freeform_01/data/IsSecuritiesSales = 'N'">
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
                            <xsl:when test="response/invest_finan_planing_freeform_01/data/IsContractualRelationShip = 'Y'">
                            Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
                            </xsl:when>
                            <xsl:when test="response/invest_finan_planing_freeform_01/data/IsContractualRelationShip = 'N'">
                            Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline>
                            </xsl:when>
                            <xsl:otherwise>
                             Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
                            
                            </xsl:otherwise>
                           </xsl:choose>
                    </fo:block>
		  </fo:table-cell>
          </fo:table-row>
		  <xsl:if test="response/invest_finan_planing_freeform_01/data/IsContractualRelationShip != 'N' and (response/StatusDesc != 'Issued' or response/invest_finan_planing_freeform_01/data/IsContractualRelationShip = 'Y')">
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
                            <xsl:when test="response/invest_finan_planing_freeform_01/data/IsOmissionsPolicy = 'Y'">
                            Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
                            </xsl:when>
                            <xsl:when test="response/invest_finan_planing_freeform_01/data/IsOmissionsPolicy = 'N'">
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
          	Within the past year, have any personnel recommended any non-public investments to clients in 
          </fo:block>
          </fo:table-cell>
          </fo:table-row>
          <fo:table-row>
          <fo:table-cell>
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          	which the Firm or their personnel have an ownership interest?
          </fo:block>
          </fo:table-cell>
          <fo:table-cell>
		  <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left" >
                      	<xsl:choose>
                            <xsl:when test="response/invest_finan_planing_freeform_01/data/IsAnyNonPublicInvestments = 'Y'">
                            Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
                            </xsl:when>
                            <xsl:when test="response/invest_finan_planing_freeform_01/data/IsAnyNonPublicInvestments = 'N'">
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
          	Has the Firm or its predecessors within the past year received commissions, referral fees,   
          </fo:block>
          </fo:table-cell>
          </fo:table-row>
          <fo:table-row>
          <fo:table-cell>
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          	reciprocity or other inducements arising from the sale, promotion or recommendation of securities,   
          </fo:block>
          </fo:table-cell>
          </fo:table-row>
          <fo:table-row>
          <fo:table-cell>
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          	insurance products, real estate or other investments?
          </fo:block>
          </fo:table-cell>
          <fo:table-cell>
		  <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left" >
                      	<xsl:choose>
                            <xsl:when test="response/invest_finan_planing_freeform_01/data/IsAnyReferralFeesInvestments = 'Y'">
                            Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
                            </xsl:when>
                            <xsl:when test="response/invest_finan_planing_freeform_01/data/IsAnyReferralFeesInvestments = 'N'">
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
                            <xsl:when test="response/invest_finan_planing_freeform_01/data/IsAnyFirmAffiliateLicensed = 'Y'">
                            Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
                            </xsl:when>
                            <xsl:when test="response/invest_finan_planing_freeform_01/data/IsAnyFirmAffiliateLicensed = 'N'">
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
          	Does your Firm or its personnel have discretionary authority to invest for any employee benefit 
          </fo:block>
          </fo:table-cell>
          </fo:table-row>
          <fo:table-row>
          <fo:table-cell>
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          	plan? 
          </fo:block>
          </fo:table-cell>
          <fo:table-cell>
		  <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left" >
                      	<xsl:choose>
                            <xsl:when test="response/invest_finan_planing_freeform_01/data/IsAnyEmployeeBenefitPlan = 'Y'">
                            Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
                            </xsl:when>
                            <xsl:when test="response/invest_finan_planing_freeform_01/data/IsAnyEmployeeBenefitPlan = 'N'">
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
		  <xsl:if test="(response/StatusDesc != 'Issued' and response/aop_total != 100) or response/showAttesationSuppPDF = 'Y'">
          <fo:block space-after="4mm" space-before="8mm">
            <fo:table margin-top="1mm" border="1pt solid black" background-color="LightGray ">
              <fo:table-column></fo:table-column>
              <fo:table-body>
                <fo:table-row>
                  <fo:table-cell>
                    <fo:block margin-top="1mm" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" font-weight="bold" font-size="10px">
                    AUDIT SUPPLEMENT (<fo:inline font-style="italic">As required in question 3.E. and 3.F.</fo:inline>)
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
					<xsl:for-each select="response/attestation_list_03/data">
	                <fo:table-row>
	                <fo:table-cell border="0.5pt solid black" padding="2px">
	                <fo:block>
	                	<xsl:value-of select="ClientIndustrySrNo"></xsl:value-of>&#160;<xsl:value-of select="ClientIndustryDesc"></xsl:value-of>
	                </fo:block>
	                </fo:table-cell>
	                <fo:table-cell border="0.5pt solid black">
	                <fo:block text-align="center">
	                 <xsl:value-of select="AuditClients"></xsl:value-of>
	                </fo:block>
	                </fo:table-cell>
	                <fo:table-cell border="0.5pt solid black">
	                <fo:block text-align="center">
	                 <xsl:value-of select="ClientsAssetsOver"></xsl:value-of>
	                </fo:block>
	                </fo:table-cell>
	                <fo:table-cell border="0.5pt solid black">
	                <fo:block text-align="center">
	                	<xsl:value-of select="NetLossForLastFYE"></xsl:value-of>
	                </fo:block>
	                </fo:table-cell>
	                </fo:table-row>
	                </xsl:for-each>
	                <fo:table-row>
	                <fo:table-cell border="0.5pt solid black" number-columns-spanned="4" padding="2px">
	                <fo:block>
	                	<xsl:value-of select="response/attestation_freeform_02/data/AuditComment"></xsl:value-of>
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
	          	Within the past year, how many clients for whom you rendered audit or attestation services   
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
                      <xsl:value-of select="response/attestation_freeform_02/data/FirmRenderedAuditAtt"></xsl:value-of>
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
	          	Within the past year, have you undergone a peer or quality review offered by the AICPA or any 
	          </fo:block>
	          </fo:table-cell>
	          </fo:table-row>
	          <fo:table-row>
	          <fo:table-cell>
	          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
	          	state CPA Society? 
	          </fo:block>
	          </fo:table-cell>
	          <fo:table-cell>
		  		<fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left" >
                      	<xsl:choose>
                            <xsl:when test="response/attestation_freeform_02/data/IsAualityReviewAtt = 'Y'">
                            Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
                            </xsl:when>
                            <xsl:when test="response/attestation_freeform_02/data/IsAualityReviewAtt = 'N'">
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
	          	Was your last peer review modified, qualified or adverse?  
	          </fo:block>
	          </fo:table-cell>
	          <fo:table-cell padding-top="10px">
		  		<fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left" >
                      	<xsl:choose>
                            <xsl:when test="response/attestation_freeform_02/data/IsLastPeerReviewAtt = 'Y'">
                            Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
                            </xsl:when>
                            <xsl:when test="response/attestation_freeform_02/data/IsLastPeerReviewAtt = 'N'">
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
	          	Within the past year have you: 
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
	          		provided services to investment funds, hedge funds, mutual funds or syndication managers?
	          	</fo:block>
	          	</fo:list-item-body>
	          	</fo:list-item>
	          	</fo:list-block>
		       </fo:block>
		       </fo:table-cell>
		       <fo:table-cell padding-top="10px">
			   <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left" >
	                      	<xsl:choose>
	                            <xsl:when test="response/firm_freeform_01/data/IsSyndicationManagers = 'Y'">
	                            Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
	                            </xsl:when>
	                            <xsl:when test="response/firm_freeform_01/data/IsSyndicationManagers = 'N'">
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
	          		arranged debt or equity financing, acted as a business broker, underwritten the offering of public or private securities, or prepared fairness opinions?
	          	</fo:block>
	          	</fo:list-item-body>
	          	</fo:list-item>
	          	</fo:list-block>
		       </fo:block>
		       </fo:table-cell>
		       <fo:table-cell padding-top="20px">
			   <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left" >
	                      	<xsl:choose>
	                            <xsl:when test="response/firm_freeform_01/data/IsDebtOrEquityFinancing = 'Y'">
	                            Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
	                            </xsl:when>
	                            <xsl:when test="response/firm_freeform_01/data/IsDebtOrEquityFinancing = 'N'">
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
			  <xsl:if test="(response/StatusDesc != 'Issued' and response/aop_total != 100) or response/showComputerSuppPDF = 'Y'">
	          <fo:block space-after="4mm" space-before="8mm">
            <fo:table margin-top="1mm" border="1pt solid black" background-color="LightGray ">
              <fo:table-column></fo:table-column>
              <fo:table-body>
                <fo:table-row>
                  <fo:table-cell>
                    <fo:block margin-top="1mm" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" font-weight="bold" font-size="10px">
                    COMPUTER RELATED SERVICES SUPPLEMENT (<fo:inline font-style="italic">As required in question 3.O.</fo:inline>)
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
					<xsl:for-each select="response/computerservice_list_03/data">
	                <fo:table-row>
	                <fo:table-cell border="0.5pt solid black" padding="2px" padding-left="2px">
	                <fo:block>
	                	<xsl:value-of select="ITServiceCategoryDesc"/>
	                </fo:block>
	                </fo:table-cell>
	                <fo:table-cell border="0.5pt solid black" padding="2px" padding-left="2px">
	                <fo:block>
	                	$<xsl:value-of select="RevenuesFromLastYear"/>
	                </fo:block>
	                </fo:table-cell>
	                <fo:table-cell border="0.5pt solid black" padding="2px" padding-left="2px">
	                <fo:block>
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
					</fo:table-body>
				  </fo:table>
				  </fo:block>
				  </fo:table-cell>
				  </fo:table-row>
				  </fo:table-body>
				  </fo:table>
				  </fo:block>
				</xsl:if>
			
			<xsl:if test="(response/firm_freeform_01/data/IsControlClientFunds != 'N' and (response/StatusDesc != 'Issued' or response/firm_freeform_01/data/IsControlClientFunds = 'Y')) or ((response/StatusDesc != 'Issued' and response/aop_total != 100) or response/showBusinessSuppPDF = 'Y')">
		    <fo:block space-after="4mm" space-before="8mm">
            <fo:table margin-top="1mm" border="1pt solid black" background-color="LightGray ">
              <fo:table-column></fo:table-column>
              <fo:table-body>
                <fo:table-row>
                  <fo:table-cell>
                    <fo:block margin-top="1mm" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" font-weight="bold" font-size="10px">
                    BUSINESS MANAGEMENT SUPPLEMENT (<fo:inline font-style="italic">As required in question 5 and 3.S.</fo:inline>)
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
                            <xsl:when test="response/general_freeform_03/data/HaveInvestmentAuthorityFC = 'Y'">
                            Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
                            </xsl:when>
                            <xsl:when test="response/general_freeform_03/data/HaveInvestmentAuthorityFC = 'N'">
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
                      &#160; <xsl:value-of select="response/general_freeform_03/data/TotlaNumberOfClientsFc"></xsl:value-of>
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
			  <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" border-bottom-width="0.9pt" border-bottom-style="solid" border-bottom-color="black">
                      &#160; <xsl:value-of select="response/TotalAmountOfClientFundsFcFormat"></xsl:value-of>
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
                            <xsl:when test="response/general_freeform_03/data/IsControlFundsForBusinessAndEntimentFc = 'Y'">
                            Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
                            </xsl:when>
                            <xsl:when test="response/general_freeform_03/data/IsControlFundsForBusinessAndEntimentFc = 'N'">
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
                            <xsl:when test="response/general_freeform_03/data/IsCountersignatureRequiredFc = 'Y'">
                            Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
                            </xsl:when>
                            <xsl:when test="response/general_freeform_03/data/IsCountersignatureRequiredFc = 'N'">
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
                            <xsl:when test="response/general_freeform_03/data/IsBankAccountReconciledFc = 'Y'">
                            Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
                            </xsl:when>
                            <xsl:when test="response/general_freeform_03/data/IsBankAccountReconciledFc = 'N'">
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
				Have you, within the past year provided management services for investment ventures?  
			  </fo:block>
			  </fo:table-cell>
			  <fo:table-cell padding-top="10px">
			  <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left" >
                      	<xsl:choose>
                            <xsl:when test="response/general_freeform_03/data/IsFirmPersonnelBusinessManagerFc = 'Y'">
                            Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
                            </xsl:when>
                            <xsl:when test="response/general_freeform_03/data/IsFirmPersonnelBusinessManagerFc = 'N'">
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
                            <xsl:when test="response/general_freeform_03/data/IsFirmPersonnelBusinessManagerFc = 'Y'">
                            Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
                            </xsl:when>
                            <xsl:when test="response/general_freeform_03/data/IsFirmPersonnelBusinessManagerFc = 'N'">
                            Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline>
                            </xsl:when>
                            <xsl:otherwise>
                             Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
                            
                            </xsl:otherwise>
                           </xsl:choose>
						</fo:block>
			  </fo:table-cell>
			  </fo:table-row>
			  <xsl:if test="response/general_freeform_03/data/IsFirmPersonnelBusinessManagerFc != 'N' and (response/StatusDesc != 'Issued' or response/general_freeform_03/data/IsFirmPersonnelBusinessManagerFc = 'Y')">
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
                      &#160; <xsl:value-of select="response/general_freeform_03/data/ProvideNumberOfClientFc"></xsl:value-of>
               </fo:block>
			  </fo:table-cell>
			  <fo:table-cell padding-top="10px" padding-left="2mm">
			  <fo:block>
			  	Client Industry:
			  </fo:block>
			  </fo:table-cell>
			  <fo:table-cell padding-top="10px">
			  	<fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" border-bottom-width="0.9pt" border-bottom-style="solid" border-bottom-color="black">
                      &#160; <xsl:value-of select="response/general_freeform_03/data/ClientIndustryFc"></xsl:value-of>
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
                      &#160; <xsl:value-of select="response/general_freeform_03/data/NumberOfFirmEmployeesFc"></xsl:value-of>
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
                            <xsl:when test="response/general_freeform_03/data/IsEmployeeDishonestyCoverageFc = 'Y'">
                            Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
                            </xsl:when>
                            <xsl:when test="response/general_freeform_03/data/IsEmployeeDishonestyCoverageFc = 'N'">
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
			<xsl:if test="(response/firm_freeform_01/data/IsAwarenessOfAnyProfLiability != 'N' and (response/StatusDesc != 'Issued' or response/firm_freeform_01/data/IsAwarenessOfAnyProfLiability = 'Y')) or (response/firm_freeform_01/data/IsAwarenessOfAnyProfLiabilitySuitAgainst != 'N' and (response/StatusDesc != 'Issued' or response/firm_freeform_01/data/IsAwarenessOfAnyProfLiabilitySuitAgainst = 'Y'))">				
	        <fo:block space-after="4mm" space-before="8mm">
            <fo:table margin-top="1mm" border="1pt solid black" background-color="LightGray ">
              <fo:table-column></fo:table-column>
              <fo:table-body>
                <fo:table-row>
                  <fo:table-cell>
                    <fo:block margin-top="1mm" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" font-weight="bold" font-size="10px">
                    CLAIM SUPPLEMENT (<fo:inline font-style="italic">As required in question 10.b. or 10.c.</fo:inline>)
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
		  <xsl:for-each select="response/BasicInfo_list_04/data">
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
                      &#160; <xsl:value-of select="NameOfClaimantCi"></xsl:value-of>
               </fo:block>
			  </fo:table-cell>
			  <fo:table-cell padding-top="10px" padding-left="2mm">
			  <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left" >
                      	<xsl:choose>
                            <xsl:when test="IsClientCi='Y'">
                            <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline> &#160; Client &#160;<fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline> &#160; Non-Client
                            </xsl:when>
                            <xsl:when test="IsClientCi='N'">
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
			  <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;">
                      &#160; 
               </fo:block>
			  </fo:table-cell>
			  <fo:table-cell padding-top="10px" padding-left="2mm">
			  <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left" >
                      	<xsl:choose>
                            <xsl:when test="IsClaimInSuit='X'">
                             N/A <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline> &#160; Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
                            </xsl:when>
							<xsl:when test="IsClaimInSuit='Y'">
                             N/A <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline> &#160; Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
                            </xsl:when>
                            <xsl:when test="IsClaimInSuit='N'">
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
                      &#160; <xsl:value-of select="ClaimNotifiedCiDate"></xsl:value-of>
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
                      &#160; <xsl:value-of select="AllegedErrorCiDate"></xsl:value-of>
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
                      &#160; <xsl:value-of select="NameOfFirmPersonnelCi"/>
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
                      &#160; <xsl:value-of select="DescOfClaimCi"/>
               </fo:block>
			  </fo:table-cell>
			  </fo:table-row>
			  <fo:table-row>
			  <fo:table-cell number-columns-spanned="2" padding-top="10px">
			  <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" border-bottom-width="0.9pt" border-bottom-style="solid" border-bottom-color="black">
                      &#160; <!-- $ <xsl:value-of select="response/policy_freeform_01/data/AccountName"></xsl:value-of> -->
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
				Has this claim or potential claim been reported to your insurance company?
			  </fo:block>
			  </fo:table-cell>
			  <fo:table-cell padding-top="10px">
			  <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left" >
                      	<xsl:choose>
                            <xsl:when test="IsThisClaimReportedCi='Y'">
                            Yes <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#x2714;</fo:inline> &#160; No <fo:inline font-family="ZapfDingbats" font-size="8pt" border="1pt black solid">&#xA0;&#xA0;&#xA0;</fo:inline>
                            </xsl:when>
                            <xsl:when test="IsThisClaimReportedCi='N'">
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
                      &#160; <xsl:value-of select="NameOfInsuranceCompanyCi"></xsl:value-of>
               </fo:block>
			  </fo:table-cell>
			  <fo:table-cell padding-top="10px" padding-left="2mm">
			  <fo:block>
			  	Date reported to insurance company:
			  	</fo:block>
			  </fo:table-cell>
			  <fo:table-cell padding-top="10px">
			  <fo:block text-align="center" font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" border-bottom-width="0.9pt" border-bottom-style="solid" border-bottom-color="black">
                      &#160; <xsl:value-of select="ReportedCiDate"></xsl:value-of>
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
			  <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" border-bottom-width="0.9pt" border-bottom-style="solid" border-bottom-color="black">
                      &#160; <xsl:value-of select="InsurerLoss"/>
               </fo:block>
			  </fo:table-cell>
			  <fo:table-cell padding-top="10px" padding-left="2mm">
			  <fo:block>
			  	Claimant’s last demand:
			  	</fo:block>
			  </fo:table-cell>
			  <fo:table-cell padding-top="10px">
			  <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" border-bottom-width="0.9pt" border-bottom-style="solid" border-bottom-color="black">
                      $&#160; <xsl:value-of select="ClaimantLastDemandCi"></xsl:value-of>
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
                      &#160; <xsl:value-of select="CurrentStatus"></xsl:value-of>
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
                      &#160; <xsl:value-of select="ClosingCiDate"></xsl:value-of>
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
                      $&#160; <xsl:value-of select="TotalClaimExpensesCi"></xsl:value-of>
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
                      &#160; <xsl:value-of select="StepsTakenToPreventClaimCi"></xsl:value-of>
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
			  </xsl:for-each>
			  </fo:block>
			  </xsl:if>
			<xsl:if test="response/firm_freeform_01/data/IsSuitInstituted != 'N' and (response/StatusDesc != 'Issued' or response/firm_freeform_01/data/IsSuitInstituted = 'Y')">
	           <fo:block space-before="4mm" space-after="4mm">
            	<fo:table margin-top="1mm" border="1pt solid black" background-color="LightGray ">
	              <fo:table-column></fo:table-column>
	              <fo:table-body>
	                <fo:table-row>
	                  <fo:table-cell>
	                    <fo:block margin-top="1mm" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" font-weight="bold" font-size="10px">
	                    FEE SUIT SUPPLEMENT (<fo:inline font-style="italic">As required in question 9</fo:inline>)
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
            <fo:table-column column-width="3.5in"/>
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
          <fo:table-column column-width="30mm"/>
          <fo:table-body>
          <fo:table-row>
          <fo:table-cell padding-top="10px" number-columns-spanned="2">
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" text-align="left">
          	Please complete the following for each suit you have filed within the past year against a client for collection of fees due:
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
	                  <fo:table-cell padding-top="10px" border="0.5pt solid black" padding-left="2mm" padding-right="2mm">
                    <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" font-weight="bold" text-align="center">
                      Amount of Fees Sued For
                    </fo:block>
                  </fo:table-cell>
                  <fo:table-cell padding-top="10px" border="0.5pt solid black" padding-left="2mm" padding-right="2mm">
                    <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" font-weight="bold" text-align="center">
                      Date Fee Suit Filed
                    </fo:block>
                  </fo:table-cell>
                  <fo:table-cell text-align="right" border="0.5pt solid black" padding-left="2mm" padding-right="2mm">
                    <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" font-weight="bold" text-align="center">
                      Was There a Counter Claim or Allegation of Malpractice?
                    </fo:block>
                  </fo:table-cell>
                  <fo:table-cell padding-top="5px" border="0.5pt solid black" padding-left="2mm" padding-right="2mm">
                    <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" font-weight="bold" text-align="center">
                      Disposition of Fee Suit*
                    </fo:block>
                  </fo:table-cell>
                </fo:table-row>
				<xsl:for-each select="response/renewbasicinfo_list_feesuit/data">
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
                     <xsl:value-of select="FeeSuitDesc"/>
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
          <fo:block font-size="10px" font-family="Arial, 'Helvetica Neue', Helvetica, sans-serif;" start-indent="7mm" space-after="15mm">
            *<fo:inline font-weight="bold">P</fo:inline> = Fees paid in full, <fo:inline font-weight="bold">NS</fo:inline> = Negotiated Settlement, <fo:inline font-weight="bold">JP</fo:inline> = Judgment Plaintiff, JD = Judgment Defense, <fo:inline font-weight="bold">O</fo:inline> = Open
          </fo:block>
		  </xsl:if>
          <fo:block id="TheVeryLastPage"> </fo:block>
          </fo:flow>
        </fo:page-sequence>
       </fo:root>
       </xsl:template>
       </xsl:stylesheet>
      
            