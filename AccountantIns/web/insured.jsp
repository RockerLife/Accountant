
<html>
<head>
	<title>Index</title>
<script src="js/jquery.js"></script>
<script type="text/javascript" id="hs-script-loader" async defer src="//js.hs-scripts.com/3334505.js"></script> </head>

<BODY>
<%
	session.setAttribute("alsid",request.getParameter("alsid"));
	

%>
    <form name ="test" action="insured.do" method="post">
    <input type="hidden" name="inet_method" value="show"/>
    <input type="hidden" name="inet_page" value="insured"/>
    <input type="hidden" name="dynaKeys" value=""/>
    <input type="hidden" name="dynaValues" value=""/>
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

