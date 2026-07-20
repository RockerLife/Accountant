<%@page import="org.apache.log4j.Logger"%>
<%! static Logger logger = Logger.getLogger(com.userbo.AccountUtils.class); %>
<html>
<head>
	<title>Index</title>	
<script src="js/jquery.js"></script>
<script type="text/javascript" id="hs-script-loader" async defer src="//js.hs-scripts.com/3334505.js"></script> </head>
<% logger.info("This is for logger in jsp page."); %>
<BODY>
<%
	session.setAttribute("alsid",request.getParameter("alsid"));
%>
    <form name ="test" action="quote.do" method="post">
    <input type="hidden" name="inet_method" value="newApp"/>
    <input type="hidden" name="inet_page" value="indexinsured"/>
    <input type="hidden" name="dynaKeys" value="|isFromInsureLink|inet_skip_validation|MarketableProductKey|CoverageKey|AddressTypeKey|CreatedType|IsInsuredViewed|ProducerCode|"/>
    <input type="hidden" name="dynaValues" value="|Y|Y|1|2|1|insured|N|<%=request.getParameter("ProducerCode")==null?"SPACE":request.getParameter("ProducerCode")%>|<%=request.getParameter("MarketingId")==null?"MSPACE":request.getParameter("MarketingId")%>|-1|"/>
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

