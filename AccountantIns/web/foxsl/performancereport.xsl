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
        		<fo:simple-page-master master-name="Letter-Landscape" page-width="11in"
                       page-height="8.5in" margin-top="0.3in" margin-bottom="0.3in" margin-left="0.3in"
                       margin-right="0.3in">
          			<fo:region-body margin-top="1.7in"/>
          			<fo:region-before extent="3.0in"/>
          			<fo:region-after />
        		</fo:simple-page-master>
      		</fo:layout-master-set>
			<fo:page-sequence master-reference="Letter-Landscape">			
				<fo:static-content flow-name="xsl-region-before">
					<fo:block text-align="right" color="grey">
						<fo:external-graphic src="images/p1Logo.png" content-height="3em" content-width="10em"/> 				  	
				  	</fo:block>	
				</fo:static-content>
				<fo:flow flow-name="xsl-region-body">
					<fo:block>
						
						<fo:block font-size="9px" font-family="Arial" font-weight="bold">
							<xsl:value-of select="response/report_description"></xsl:value-of>
						</fo:block>
						
						<xsl:if test="response/isShowFiltersSearchTrue='true'">
						<fo:block font-size="7px" font-family="Arial" font-weight="bold" margin-top="5mm">
						Selected Filters
						</fo:block>
						<fo:block>
							<fo:table border="0.2pt solid #6bc2f8" margin-top="2mm">
								<fo:table-body>
									<xsl:for-each select="response/selected_filters_list_1/data">
										<fo:table-row>
											<fo:table-cell padding-start="3pt" padding-top="3pt" padding-bottom="3pt" padding-end="3pt"><fo:block font-size="7px" font-family="Arial" font-weight="bold"><xsl:value-of select="col1"/> : </fo:block></fo:table-cell>
											<fo:table-cell padding-start="3pt" padding-top="3pt" padding-bottom="3pt" padding-end="3pt"><fo:block font-size="7px" font-family="Arial"><xsl:value-of select="val1"/></fo:block></fo:table-cell>
											<fo:table-cell padding-start="3pt" padding-top="3pt" padding-bottom="3pt" padding-end="3pt"><fo:block font-size="7px" font-family="Arial" font-weight="bold">
												<xsl:value-of select="col2"/> <xsl:if test="col2 != ''"> : </xsl:if> 
											</fo:block></fo:table-cell>
											<fo:table-cell padding-start="3pt" padding-top="3pt" padding-bottom="3pt" padding-end="3pt"><fo:block font-size="7px" font-family="Arial"><xsl:value-of select="val2"/></fo:block></fo:table-cell>
										</fo:table-row>
									</xsl:for-each>
								</fo:table-body>
							</fo:table>			
						</fo:block>
						
						</xsl:if>	
						
						
						<xsl:if test="response/reportID = 'AllDataForAccountant'">
							<fo:table border="0.2pt solid #6bc2f8" margin-top="2mm">
								<fo:table-body>
									<xsl:for-each select="response/report_data_list_1/data">
										<xsl:variable name="i" select="position()"/>
										<fo:table-row>
											<xsl:choose>
												<xsl:when test="position()=1">
													<xsl:if test="PolicyKey != 'HIDDEN'">
														<fo:table-cell border="0.2pt solid #6bc2f8" padding-start="3pt" padding-top="3pt" padding-bottom="3pt" padding-end="3pt" background-color="#6bc2f8"><fo:block font-size="6px" font-family="Arial"><xsl:value-of select="PolicyKey"/></fo:block></fo:table-cell>
													</xsl:if> 
													<xsl:if test="AccountID != 'HIDDEN'">
														<fo:table-cell border="0.2pt solid #6bc2f8" padding-start="3pt" padding-top="3pt" padding-bottom="3pt" padding-end="3pt" background-color="#6bc2f8"><fo:block font-size="6px" font-family="Arial"><xsl:value-of select="AccountID"/></fo:block></fo:table-cell>
													</xsl:if>
													
													 
												</xsl:when>
												<xsl:otherwise>
													
													<xsl:if test="PolicyKey != 'HIDDEN'">
														<fo:table-cell border="0.2pt solid #6bc2f8" padding-start="3pt" padding-top="3pt" padding-bottom="3pt" padding-end="3pt" ><fo:block font-size="6px" font-family="Arial"><xsl:value-of select="PolicyKey"/></fo:block></fo:table-cell>
													</xsl:if> 
													<xsl:if test="AccountID != 'HIDDEN'">
														<fo:table-cell border="0.2pt solid #6bc2f8" padding-start="3pt" padding-top="3pt" padding-bottom="3pt" padding-end="3pt" ><fo:block font-size="6px" font-family="Arial"><xsl:value-of select="AccountID"/></fo:block></fo:table-cell>
													</xsl:if>
													
													 
												</xsl:otherwise>
											</xsl:choose>		
										</fo:table-row>
									</xsl:for-each>	
								</fo:table-body>
							</fo:table>
						</xsl:if>
						
						
						
						
						
					
					
					
					</fo:block>					
				</fo:flow>	
			</fo:page-sequence>
		</fo:root>
	</xsl:template>			
</xsl:stylesheet>	