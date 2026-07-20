
<html>
<head>
	<title>Index</title>
<script src="js/jquery.js"></script>
<script type="text/javascript" id="hs-script-loader" async defer src="//js.hs-scripts.com/3334505.js"></script> </head>

<BODY>
<%
	session.setAttribute("alsid",request.getParameter("alsid"));
%>
    <form name ="test" action="quickquote.do" method="post">
    <input type="hidden" name="inet_method" value="showrm"/>
    <input type="hidden" name="inet_page" value="rminsured"/>
    <input type="hidden" name="dynaKeys" value="|inet_skip_validation|MarketableProductKey|CoverageKey|AddressTypeKey|StatusKey|IsQuickQuote|IsFullQuote|User|IsQuickQuotePage|role_id|isOtherStateAgent|CreatedType|IsInsuredViewed|StateCode|PolicyEffectiveDate|ProducerCode|"/>
    <input type="hidden" name="dynaValues" value="|Y|1|2|1|1|Y|N|insured|N|1|N|insured|N|<%=request.getParameter("StateCode")==null?"SPACE":request.getParameter("StateCode")%>|<%=request.getParameter("PolicyEffectiveDate")==null?"SPACE":request.getParameter("PolicyEffectiveDate")%>|<%=request.getParameter("ProducerCode")==null?"SPACE":request.getParameter("ProducerCode")%>|"/>
    <input type="hidden" name="inet_context" value=""/>
    </form>
</BODY>
<Script language="javascript">

forward_page();	

	function forward_page()
	{
					
		document.forms[0].submit();		
	}
	

	</Script>

</html>

