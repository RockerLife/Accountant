
<html>
<head>
	<title>Index</title>
<script src="js/jquery.js"></script>
<script type="text/javascript" id="hs-script-loader" async defer src="//js.hs-scripts.com/3334505.js"></script> </head>

<BODY>

    <form name ="test" action="confirmation.do" method="post">
    <input type="hidden" name="inet_method" value="showindexthanks"/>
    <input type="hidden" name="inet_page" value="showindexthanks"/>
    <input type="hidden" name="dynaKeys" value="|inet_skip_validation|MarketableProductKey|CoverageKey|AddressTypeKey|AccountEmail|TransactionID|TransactionStatus|PaymentMode|"/>
    <input type="hidden" name="dynaValues" value="|Y|1|2|1|<%=request.getParameter("AccountEmail")%>|<%=request.getParameter("TransactionID")%>|<%=request.getParameter("TransactionStatus")%>|<%=request.getParameter("PaymentMode")%>|"/>
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

