<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.1"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:fo="http://www.w3.org/1999/XSL/Format"
	exclude-result-prefixes="fo"
	xmlns:fn="http://www.w3.org/2005/02/xpath-functions"
	xmlns:java="java">

	<xsl:template match="/" name="APL-State-PA-2" >
	
				    <fo:block text-align="center" font-size="12px" ><fo:inline font-weight="bold" font-style="italic"> NOTICE TO PENNSYLVANIA POLICYHOLDERS</fo:inline></fo:block>
				       				    
				    
				   
				      <fo:block margin-top="22mm"/>
				     <fo:block text-align="center" font-weight="bold" font-size="10px" ><fo:inline font-weight="bold" font-style="italic">DEFENSE WITHIN LIMITS NOTIFICATION</fo:inline></fo:block>
				     <fo:block margin-top="10mm"/>
				    
				
				     <fo:block text-align="left" font-size="10px" start-indent="2cm" ><fo:inline font-weight="bold" font-style="italic">ANY “DEFENSE EXPENSES” PAID UNDER THIS POLICY WILL REDUCE THE LIMIT OF INSURANCE AND MAY EXHAUST THE LIMIT COMPLETELY.</fo:inline></fo:block>
				     <fo:block margin-top="10mm"/>


				     <fo:block text-align="left" font-size="10px" start-indent="2cm" ><fo:inline font-weight="bold" font-style="italic">“DEFENSE EXPENSES”  MEAN REASONABLE AND NECESSARY FEES, COSTS AND EXPENSES CONSENTED TO BY THE INSURED RESULTING SOLELY FROM THE INVESTIGATION, LEGAL DEFENSE AND LEGAL APPEAL OF A CLAIM AGAINST THE INSURED, BUT EXCLUDING SALARIES OF OFFICERS AND EMPLOYERS OF THE INSURER.</fo:inline></fo:block>


				   
				   
				    
				    
				    <fo:block margin-top="180mm"/>
				    
				    
				 
			 
			  <fo:block  font-size="10px"  text-indent="2cm" color="grey" text-align="left"><xsl:value-of select="response/policyformfooter_freeform_01/data/APLAMENDPA20092" /></fo:block>
		  
     </xsl:template>
</xsl:stylesheet>
