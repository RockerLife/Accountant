<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:variable name="showNewPracticeManagementSupplement"
		select="/response/StatusDesc != 'Issued' or /response/GrossAmount_0 &gt; 500000" />
	<xsl:variable name="showNewClaimSupplement"
		select="/response/StatusDesc != 'Issued' or /response/FIRM_FREEFORM_01/data/IsAwarenessOfAnyProfLiability = 'Y' or /response/FIRM_FREEFORM_01/data/IsAwarenessOfAnyProfLiabilitySuitAgainst = 'Y'" />
	<xsl:variable name="showRenewClaimSupplement"
		select="/response/StatusDesc != 'Issued' or /response/firm_freeform_01/data/IsAwarenessOfAnyProfLiability = 'Y' or /response/firm_freeform_01/data/IsAwarenessOfAnyProfLiabilitySuitAgainst = 'Y'" />
</xsl:stylesheet>
