<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet version="1.1"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format"
	exclude-result-prefixes="fo" xmlns:fn="http://www.w3.org/2005/02/xpath-functions"
	xmlns:java="java">

	<xsl:template name="CommonHeader" match="/">
		<fo:block text-align="center" font-size="12px" font-weight="bold">THIS ENDORSEMENT CHANGES THE POLICY, PLEASE READ IT CAREFULLY.</fo:block>
		<fo:block margin-top="10mm"/>
		<fo:block>
			<fo:table text-align="center">
				<fo:table-column column-width="90mm"/>
				<fo:table-column column-width="90mm"/>
				<fo:table-body>
					<fo:table-row>
						<fo:table-cell padding-left="4pt">
							<fo:block font-weight="bold" font-size="10px" text-align="left">To be attached to and form part of Policy No:<xsl:value-of select="response/policy_freeform_01/data/PolicyNumber"/>
							</fo:block>
						</fo:table-cell>
						<fo:table-cell padding-left="4pt">
							<fo:block font-weight="bold" font-size="10px" text-align="left">Effective Date of Endorsement:<xsl:value-of select="response/policy_freeform_01/data/PolicyEffectiveDate"/>
							</fo:block>
						</fo:table-cell>
					</fo:table-row>
					<fo:table-row>
						<fo:table-cell padding-left="4pt" padding-top="4pt">
							<fo:block font-weight="bold" font-size="10px" text-align="left">Issued to: <xsl:value-of select="response/policy_freeform_01/data/AccountName"/>
							</fo:block>
						</fo:table-cell>
						<fo:table-cell padding-left="4pt" padding-top="4pt">
							<fo:block font-weight="bold" font-size="10px" text-align="left">Endorsement No: <xsl:value-of select="response/endorsements_freeform_01/data/APL1082010"/>
							</fo:block>
						</fo:table-cell>
					</fo:table-row>
				</fo:table-body>
			</fo:table>
		</fo:block>
		<fo:block space-after=".1in" text-align-last="justify">
			<fo:leader leader-pattern="rule"/>
		</fo:block>
	</xsl:template>
</xsl:stylesheet>




					
				    	