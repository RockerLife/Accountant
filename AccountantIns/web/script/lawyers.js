function addRightAOP(){
	var items=document.getElementsByName('AOPKey');
	var selectedItems="";
	for (var i = 0; i < items.length; i++) {

		var aopFlag = 0;
		var AOP_values = document.getElementById("AOP_values").value.split(",");
		for(aopCount = 0; aopCount < AOP_values.length; aopCount++){
			if(AOP_values[aopCount] == items[i].value){
				if(i>0 && selectedItems != ""){
					selectedItems += "," ;
				}
				selectedItems += items[i].value;
				aopFlag = 1;
			}
		}
		if(aopFlag == 0){
			if(items[i].type=='checkbox' && items[i].checked==true){
				if(i>0 && selectedItems != ""){
					selectedItems += "," ;
				}
				selectedItems += items[i].value;
			}
		}
	}
	document.getElementById("AOP_values").value = selectedItems;
	addAOP();
}

function addAOP(){
	if($.trim(document.getElementById("AOP_values").value) !== ""){
		var rightAOPHTML = "";
		var selectedAopValue = $.trim(document.getElementById("AOP_values").value).split(",");
		var items=document.getElementsByName('AOPKey');
		for(var selectedAopCount = 0; selectedAopCount < selectedAopValue.length; selectedAopCount++){
			for (var aopItemCount = 0; aopItemCount < items.length; aopItemCount++) {
				if(items[aopItemCount].type=='checkbox' && items[aopItemCount].value == selectedAopValue[selectedAopCount]){
					$("#aoplist_list_1").find("table tbody").find("input[id=AOPKey][value=" + selectedAopValue[selectedAopCount] + "]")[0].checked = false;
					var selectedAop = $("#aoplist_list_1").find("table tbody").find("tr:eq(" + aopItemCount +")").find("td:eq(1)").text();
					rightAOPHTML += "<tr id=\"" + selectedAopValue[selectedAopCount] + "\"><td class=\"aopName\" width=\"75%\">";
					rightAOPHTML += "<input id=\"AOPDesc_" + selectedAopValue[selectedAopCount] + "\" type=\"hidden\" value=\"" + selectedAop + "\" name=\"AOPDesc_" + selectedAopValue[selectedAopCount] + "\">";
					rightAOPHTML += "<label id=\"AOPDesc\" name=\"AOPDesc\">" + selectedAop + "</label>";
					rightAOPHTML += "</td><td class=\"aopPercentage\" width=\"20%\" valign=\"middle\">";
					if(selectedAopValue[selectedAopCount] == 20){
						rightAOPHTML += "<input onkeyup=\"pleaseDescribe5('Y');\" id=\"AOP_Percentage_" + selectedAopValue[selectedAopCount] + "\" type=\"text\" value=\"\" style=\"width: 70px; height: auto;\" onblur=\"populateTotalFieldValue('AOP_Percentage_','TotalAOP_Percentage');\" onkeypress='return IsNumeric(this, event, true, true, \"\");' ondrop=\"return false;\" onpaste=\"return false;\" maxlength=\"3\" placeholder=\"%\" name=\"AOP_Percentage_" + selectedAopValue[selectedAopCount] + "\">";
//					} else if(selectedAopValue[selectedAopCount] == 5){
//						rightAOPHTML += "<input id=\"AOP_Percentage_" + selectedAopValue[selectedAopCount] + "\" type=\"text\" value=\"\" style=\"width: 70px; height: auto;\" onblur=\"populateTotalFieldValue('AOP_Percentage_','TotalAOP_Percentage');\" onkeypress='return IsNumeric(this, event, true, true, \"\");' ondrop=\"return false;\" onpaste=\"return false;\"  readonly  maxlength=\"3\" placeholder=\"%\" name=\"AOP_Percentage_" + selectedAopValue[selectedAopCount] + "\">";
					}else {
						rightAOPHTML += "<input id=\"AOP_Percentage_" + selectedAopValue[selectedAopCount] + "\" type=\"text\" value=\"\" style=\"width: 70px; height: auto;\" onblur=\"populateTotalFieldValue('AOP_Percentage_','TotalAOP_Percentage');\" onkeypress='return IsNumeric(this, event, true, true, \"\");' ondrop=\"return false;\" onpaste=\"return false;\" maxlength=\"3\" placeholder=\"%\" name=\"AOP_Percentage_" + selectedAopValue[selectedAopCount] + "\">";
					} 
					rightAOPHTML += "</td><td class=\"aopPageSave\" width=\"5%\">";
					
					if(!(selectedAopValue[selectedAopCount] == 3 || selectedAopValue[selectedAopCount] == 1 
						|| selectedAopValue[selectedAopCount] == 4 || selectedAopValue[selectedAopCount] == 8 
						|| selectedAopValue[selectedAopCount] == 6)){
						rightAOPHTML += "<a class=\"Link_blue\" onclick=\"removeAOP('" + selectedAopValue[selectedAopCount] + "');\" ><i class=\"fa fa-times delete_icon\"> </i></a>";
					}
					rightAOPHTML += "</td></tr>";
					break;
				}
			}
		}
		
		var aopPercentageValue = "";
		var aopPercentageTRValue = "";
		if($.trim($("#rightaoplist_list").html()) != ""){
			for(var k = 0; k < $("#rightaoplist_list").find("tr").length; k++){
				if($.trim($("#rightaoplist_list").html()) !== ""){
					if(k>0){
						aopPercentageValue += ",";
						aopPercentageTRValue += ",";
					}
					aopPercentageValue += $("#rightaoplist_list").find("tr:eq(" + k + ")").find("td:eq(1)").find("input").val();
					aopPercentageTRValue += $("#rightaoplist_list").find("tr:eq(" + k + ")").attr("id");
				}
			}
			aopPercentageValue = aopPercentageValue.split(",");
			aopPercentageTRValue = aopPercentageTRValue.split(",");
		}
		$("#rightaoplist_list").html(rightAOPHTML);
		for(var k = 0; k < aopPercentageTRValue.length; k++){
			$("#rightaoplist_list").find("tr[id=" + aopPercentageTRValue[k] + "]").find("td:eq(1)").find("input").val(aopPercentageValue[k]);
		}
		$("#error_SelectAOP").removeClass("tr_errors");
		$("#error_SelectAOP").hide();
	} else {
		$("#error_SelectAOP").html("Please select any area of practice");
		$("#error_SelectAOP").addClass("tr_errors");
	}
}

function removeAOP(value){
	var selectedItems = $.trim(document.getElementById("AOP_values").value);
	selectedItems = selectedItems.replace(value,"0");
	document.getElementById("AOP_values").value = selectedItems;
	addAOP();
	populateTotalFieldValue('AOP_Percentage_','TotalAOP_Percentage');
}

function onPageLoad(){
	if($.trim(document.getElementById("AOP_values").value) !== ""){
		var selectedItems = $.trim(document.getElementById("AOP_values").value).split(",");
		for(var i = 0; i < selectedItems.length; i++){
			if(selectedItems[i] != 0){
				$("#aoplist_list_1").find("table tbody").find("input[id=AOPKey][value=" + selectedItems[i] + "]")[0].checked = true;
				if(selectedItems[i] == 3 || selectedItems[i] == 1 || selectedItems[i] == 4 || selectedItems[i] == 8 || selectedItems[i] == 6){
					$("#aoplist_list_1").find("table tbody").find("input[id=AOPKey][value=" + selectedItems[i] + "]").parent().parent().hide();
				}
			}
		}
		addAOP();
		var aopPercentageValue = $.trim(document.getElementById("aopPercentageValue").value);
		var aopPercentageTRValue = $.trim(document.getElementById("aopPercentageTRValue").value);
		aopPercentageValue = aopPercentageValue.split(",");
		aopPercentageTRValue = aopPercentageTRValue.split(",");
		for(var k = 0; k < aopPercentageTRValue.length; k++){
			$("#rightaoplist_list").find("tr[id=" + aopPercentageTRValue[k] + "]").find("td:eq(1)").find("input").val(aopPercentageValue[k]);
		}
//		populateTotalFieldValue('AOP_Percentage_','TotalAOP_Percentage');
	}
	if($.trim(document.getElementById("filedDisabled").value) === "YES"){
		$("#addRightAOPQQ").removeAttr("onclick");
		$("#addRightAOPQQ").attr("disabled", "disabled");
		$("#aoplist_list_1").find("table tbody").find("input[id=AOPKey][type=checkbox]").attr("disabled", "disabled");
		var aopPercentageTRValue = $.trim(document.getElementById("aopPercentageTRValue").value);
		aopPercentageTRValue = aopPercentageTRValue.split(",");
		for(var k = 0; k < aopPercentageTRValue.length; k++){
			$("#rightaoplist_list").find("tr[id=" + aopPercentageTRValue[k] + "]").find("td:eq(1)").find("input").attr("disabled", "disabled");
			$("#rightaoplist_list").find("tr[id=" + aopPercentageTRValue[k] + "]").find("td:eq(2)").remove();
		}
	}
	
	var aopTabIndex = 90;
	for(var aopListCount = 0; aopListCount<24; aopListCount++){
		aopTabIndex += 10;
		$("#leftAOPList").find("tbody").find("tr:eq(" + aopListCount + ")").find("input[id=AOPKey][name=AOPKey]").attr("tabindex",aopTabIndex);
	}
}

function getPercentageValue(){
	var aopPercentageValue = "";
	var aopPercentageTRValue = "";
	if($.trim($("#rightaoplist_list").html()) != ""){
		for(var k = 0; k < $("#rightaoplist_list").find("tr").length; k++){
			if($.trim($("#rightaoplist_list").html()) !== ""){
				if(k>0){
					aopPercentageValue += ",";
					aopPercentageTRValue += ",";
				}
				aopPercentageValue += $("#rightaoplist_list").find("tr:eq(" + k + ")").find("td:eq(1)").find("input").val();
				aopPercentageTRValue += $("#rightaoplist_list").find("tr:eq(" + k + ")").attr("id");
				
			}
		}
	}
	document.getElementById("aopPercentageValue").value = aopPercentageValue;
	document.getElementById("aopPercentageTRValue").value = aopPercentageTRValue;
}

function aopKeyPressEvent(event){
	var key;
	if (window.event) {
		key = event.keyCode;
	} else if (event.which) {
		key = event.which;
	}
	
	if (key === 32 || key === 13) {
		$("#addRightAOPQQ").trigger("click");
	}
}

function onAopNewAppPageLoad(){
	if($.trim(document.getElementById("AOP_values").value) !== ""){
		var selectedItems = $.trim(document.getElementById("AOP_values").value).split(",");
		for(var i = 0; i < selectedItems.length; i++){
			if(selectedItems[i] != 0 && selectedItems[i] != 3 && selectedItems[i] != 1 && selectedItems[i] != 4 && selectedItems[i] != 8 && selectedItems[i] != 6){
				$("#aoplist_list_1").find("table tbody").find("input[id=AOPKey][value=" + selectedItems[i] + "]")[0].checked = true;
			}
		}
		addAOP();
		var aopPercentageValue = $.trim(document.getElementById("aopPercentageValue").value);
		var aopPercentageTRValue = $.trim(document.getElementById("aopPercentageTRValue").value);
		aopPercentageValue = aopPercentageValue.split(",");
		aopPercentageTRValue = aopPercentageTRValue.split(",");
		for(var k = 0; k < aopPercentageTRValue.length; k++){
			$("#rightaoplist_list").find("tr[id=" + aopPercentageTRValue[k] + "]").find("td:eq(1)").find("input").val(aopPercentageValue[k]);
			$("#rightaopstatic_list").find("tr[id=" + aopPercentageTRValue[k] + "]").find("td:eq(1)").find("input").val(aopPercentageValue[k]);
		}
//		populateTotalFieldValue('AOP_Percentage_','TotalAOP_Percentage');
	}
	if($.trim(document.getElementById("filedDisabled").value) === "YES"){
		$("#addRightAOPQQ").removeAttr("onclick");
		$("#addRightAOPQQ").attr("disabled", "disabled");
		$("#aoplist_list_1").find("table tbody").find("input[id=AOPKey][type=checkbox]").attr("disabled", "disabled");
		var aopPercentageTRValue = $.trim(document.getElementById("aopPercentageTRValue").value);
		aopPercentageTRValue = aopPercentageTRValue.split(",");
		for(var k = 0; k < aopPercentageTRValue.length; k++){
			$("#rightaoplist_list").find("tr[id=" + aopPercentageTRValue[k] + "]").find("td:eq(1)").find("input").attr("disabled", "disabled");
			$("#rightaoplist_list").find("tr[id=" + aopPercentageTRValue[k] + "]").find("td:eq(2)").remove();
			$("#rightaopstatic_list").find("tr[id=" + aopPercentageTRValue[k] + "]").find("td:eq(1)").find("input").attr("disabled", "disabled");
			$("#rightaopstatic_list").find("tr[id=" + aopPercentageTRValue[k] + "]").find("td:eq(2)").remove();
		}
	}
	
	var aopTabIndex = 90;
	for(var aopListCount = 0; aopListCount<24; aopListCount++){
		aopTabIndex += 10;
		$("#leftAOPList").find("tbody").find("tr:eq(" + aopListCount + ")").find("input[id=AOPKey][name=AOPKey]").attr("tabindex",aopTabIndex);
	}
}

function formPreview(){
	var url = window.location.href;
	
	url = url.substring(0 , url.indexOf("AccountantIns/") + 14);
	
	url += "quickquoteoptionlist.do?User=Agent&role_id=3&AccountEmail=cbarndollar@protexure.com&divHideShow=&inet_context=";
	url += "&inet_method=printquickquotepdf&inet_page=quickquoteoptionlist&dynaKeys=&dynaValues=";
	url += "&ctxmenuparam=&dateFormat=&dateFormatTS=&quickquoteoptionlist_pageFieldsList=";
	
	url += "&TotalPremium="+$("#TotalPremium").val();
	url += "&PremiumRange="+$("#PremiumRange").val();
	url += "&AOP_Percentage_1="+$("#AOP_Percentage_1").val();
	url += "&AOP_Percentage_3="+$("#AOP_Percentage_3").val();
	url += "&AOP_Percentage_4="+$("#AOP_Percentage_4").val();
	url += "&AOP_Percentage_6="+$("#AOP_Percentage_6").val();
	url += "&AOP_Percentage_7="+$("#AOP_Percentage_7").val();
	url += "&AOP_Percentage_8="+$("#AOP_Percentage_8").val();
	url += "&AccountName="+$("#AccountName").val();
	url += "&IsLoginMailSent="+$("#IsLoginMailSent").val();
	url += "&jsessionid="+$("#jsessionid").val();
	url += "&PolicyKey="+$("#PolicyKey").val();
	url += "&QQRMLink="+$("#QQRMLink").val();
	url += "&QuoteNumber="+$("#QuoteNumber").val();
//	alert(url)
	
	var params = ['height='+screen.height,'width='+screen.width,'scrollbars=yes'].join(',');
	var win = window.open(url, 'previewQuickQuotePdf', params);
	win.moveTo(0,0);
	win.focus();
	win.print();
}

function fillLimitOfLiability(value)
{
	
	
	if(document.getElementById('IsBasicPageDefault') != null)
	{
		var val = document.getElementById('IsBasicPageDefault').value;
		
		/*if(val=='N')
		{
		*/	
			
			hideShowClaimExpenseField(document.getElementById('StateCode').value);
			//alert(value);
			document.getElementById('LimitSequence').value = value;
			makeDefenseOutsideRule(value);
			
		//}
		
	}
}
function fillDeductible(value)
{
	if(document.getElementById('IsBasicPageDefault') != null)
	{
		var val = document.getElementById('IsBasicPageDefault').value;
		//alert(val);
		/*if(val=='N')
		{*/
			//alert("inside");
			document.getElementById('DeductibleSequence').value = value;
		//}
	}
}
function fillDefenseExpense(value)
{	
	var stateCode = document.forms[0].StateCode.value;
	
	if(stateCode != 'VT' && stateCode != 'NM' && stateCode != 'ND'  && stateCode != 'SD')
	{		
		/*if(document.getElementById('IsBasicPageDefault') != null)
		{*/
			var val = document.getElementById('IsBasicPageDefault').value;
			if(value=='Y' && val=='N')
			{
				document.getElementById('IsClaimExpensesType').checked = false;
			}
			else if(value=='N' && val=='N')
			{
				document.getElementById('IsClaimExpensesType').checked = true;
			}		
		//}
	}
	
}
function fillClaim(value)
{
	/*if(document.getElementById('IsBasicPageDefault') != null)
	{*/
		var val = document.getElementById('IsBasicPageDefault').value;
		if(value=='Y' && val=='N')
		{
			document.getElementById('IsClaimOptionType').checked = false;
		}
		else if(value=='N' && val=='N')
		{
			document.getElementById('IsClaimOptionType').checked = true;
		}		
	//}
}
//----------------Validate Date-----------------------------------
function isLeapYear(dtYear)
{
   	dtYear = parseInt(dtYear);
    if(dtYear%4 == 0)
    {
       if(dtYear%100 != 0)
       {
       		return true;
       }
       else
       {
      	if(dtYear%400 == 0)
			return true;
        else
            return false;
       }
    }
	return false;
}

function validateLeapYearDate(thisField)
{
	  var fieldName = thisField.name;
	  var curVal = thisField.value;	  	
	  if(curVal != null && curVal !="")
	  {
		  	var curMonth = 	 curVal.substring(0,curVal.indexOf("/"));
		  	var cursubstr = curVal.substring(curVal.indexOf("/")+1,curVal.length);
		  	var curDay = cursubstr.substring(0,cursubstr.indexOf("/"));
		  	var curYear = (cursubstr.substring(cursubstr.indexOf("/")+1,cursubstr.length));
		  	curDay = parseInt(curDay);
		  	curMonth = parseInt(curMonth);
		  	if(curYear == "")
		  	{
		  		alert(curVal + " is incorrect date");
	  			setFocus(fieldName);
				thisField.focus();
				thisField.select();
		  	}
		  	else if(isLeapYear(curYear))
		  	{		  		
		  		if(curMonth == 2 && curDay > 29)
		  		{
		  			alert(curVal + " is invalid date");
		  			setFocus(fieldName);
					thisField.focus();
					thisField.select();
		  		}
		  	}
		  	else if(curMonth == 2 && curDay > 28)
	  		{
	  			alert(curVal + " is invalid date");		  			
	  			setFocus(fieldName);
				thisField.focus();
				thisField.select();
	  		}
	  		else if(curMonth != 2 && curDay > 31)
	  		{
	  			alert(curVal + " is invalid date");
		  		setFocus(fieldName);
				thisField.focus();
				thisField.select();
	  		}
		  	
	  }
}

function validateMMYYYYDate(thisField)
{
	  var fieldName = thisField.name;
	  var curVal = thisField.value;	  	
	  if(curVal != null && curVal !="")
	  {
		  	var curMonth = curVal.substring(0,curVal.indexOf("/"));
		  	var curYear = curVal.substring(curVal.indexOf("/")+1,curVal.length);
		  	curYear = parseInt(curYear);
		  	curMonth = parseInt(curMonth);
		  	if(curYear == "")
		  	{
		  		alert(curVal + " is incorrect date");
	  			setFocus(fieldName);
				thisField.focus();
				thisField.select();
		  	}
		  	else if(curMonth > 12)
		  	{		  		
		  		alert(curVal + " is invalid date");		  		
		  	}		  	
	  }
}

//----------------End Validate Date-----------------------------------

function populateTotalFieldValue(fieldPrefix,totalValContainerid){
	 var total = 0;
	 var err =0;
	 var myVal;

	 try{
	 //alert(fieldPrefix);
	 for (var i=0; i<document.forms[0].length; i++)
      {
            var n = document.forms[0].elements[i].name;
            if (n != null && n.indexOf(fieldPrefix) > -1)
            {
            	 myVal=document.forms[0].elements[i].value;
            	 if( myVal != null && myVal != '' )	
	            {
 		    		if(!isNumberCorrect(myVal)){
            		  		alert("That number is invalid.  Please enter again...");
							//document.forms[0].elements[i].select();
							//document.forms[0].elements[i].focus();
							return false;
            		  }else{
	
	 		    		var ival= parseInt(myVal);
            		  	if(n !="NumberOfPersonnel_1"){
    
            		  		total =parseInt(total) +ival;
            		  	try
            		  	{
            		  	
            		  		document.getElementById("ajax_field_totalNoOfPersonnel").value = total;
            		  	}
            		  	catch(e)
            		  	{}	
            		  	}else{
            		  		//total = parseInt( total);
            		  		}
            		  	}
            	}
            }
      }
    
	document.getElementById(totalValContainerid).innerHTML = total;
	
	if(fieldPrefix == 'AOP_Percentage_')
	{
		if (total == 100){
		document.getElementById(totalValContainerid).className = "txt12BoldBlue";
		}
		else{
		document.getElementById(totalValContainerid).className = "txt12BoldRed";
		}
	}
	showOtherDesc();
	 }catch (e) {
		// TODO: handle exception
	}
}

function isNumberCorrect(curVal){
		    if(curVal.indexOf(".") >= 0){
				return false;
		    }
			if (isNaN(curVal)) {
		    	return false;
			}
 return true;
}

function getTotalNoOfField(fieldPrefix){
	 var total = 0;
	 for (var i=0; i<document.forms[0].length; i++)
      {
        var n = document.forms[0].elements[i].name;
       
        if (n != null && n.indexOf(fieldPrefix) > -1)
        {          	 	
        	  total = total +1;
        }          
      }	
	return total;
}


function populateExpirationDate(thisField)
{
		
	  var fieldName = thisField.name;
	  var curVal = thisField.value;
	  if(fieldName == 'PolicyEffectiveDate' && curVal != null && curVal !=""){
		  	var curMonth = 	 curVal.substring(0,curVal.indexOf("/"));
		  	var cursubstr = curVal.substring(curVal.indexOf("/")+1,curVal.length);
		  	var curday = cursubstr.substring(0,cursubstr.indexOf("/"));
		  	var curYear = (cursubstr.substring(cursubstr.indexOf("/")+1,cursubstr.length));
		  	var nextYear=parseInt(curYear)+1;		  	
		  	document.getElementById("PolicyExpirationDate").value = curMonth+"/"+curday+"/"+nextYear;		  	
		  	//alert(document.getElementById("PolicyExpirationDate").value);	
		  	/*if(document.getElementById("PolicyExpirationDate").value == ''){		  		
	  			document.getElementById("PolicyExpirationDate").value = curMonth+"/"+curday+"/"+nextYear;
	  		}*/
	  }
}

function populateExpiration(thisField)
{
		
		if(document.getElementById("PolicyEffectiveDate") != null){
			var curVal = document.getElementById("PolicyEffectiveDate").value ;			
			if(curVal != null && curVal !=""){
		  	var curMonth = 	 curVal.substring(0,curVal.indexOf("/"));
		  	var cursubstr = curVal.substring(curVal.indexOf("/")+1,curVal.length);
		  	var curday = cursubstr.substring(0,cursubstr.indexOf("/"));
		  	var curYear = (cursubstr.substring(cursubstr.indexOf("/")+1,cursubstr.length));
		  	var nextYear=parseInt(curYear)+1;
		  	document.getElementById("PolicyExpirationDate").value = curMonth+"/"+curday+"/"+nextYear;		  	
		  	/*if(document.getElementById("PolicyExpirationDate").value == ''){		  		
	  			document.getElementById("PolicyExpirationDate").value = curMonth+"/"+curday+"/"+nextYear;
	  		}*/
	  }
			
			
		}
	  var fieldName = thisField.name;
	  var curVal = thisField.value;
	 
}

function populateYearEndDate(thisField)
{
		  var fieldName = thisField.name;
		  var curVal = thisField.value;
		  var curYear;  
		  var curMonth;	
		  
		  if(curVal != null && curVal !=""){
	  	    if (chkFiscalYearFormat(curVal) == false) {
				//alert(fieldName);
				//document.forms[0].fieldName.select();
				thisField.select();
				$(thisField).val("");
				thisField.focus();
				alert("This date is invalid.  Please enter again.");
							
				 	//setFocus('YearEndDate_0');
				
				
				//document.forms[0].fieldName.focus();
				
				return false;
			}else if(fieldName == 'YearEndDate_0'){
					//applyDateFormatMMYYYY(thisField);
				  	 curYear = curVal.substring(curVal.indexOf("/")+1,curVal.length);
				  	 curMonth = curVal.substring(0,curVal.indexOf("/"));
				  	 if(checkCurrentYear(curYear) == false)
						{
							//setTimeout(document.forms[0].fieldName.select(), 1);
							thisField.select();
							$(thisField).val("");
							thisField.focus();
							alert("The Year should be the current year or one year back.  Please enter again.");
							
								//setFocus('YearEndDate_0');
//								thisField.focus();
								//thisField.select();
							
							return false;
						}
				  	var objName =curMonth+"/"+"01/"+curYear;
				  	var totalFields = getTotalNoOfField('YearEndDate_');
				  	
				  	
				  	for(var i=1; i<totalFields;i++){
				  		
				  		var nextYear = curMonth + "/" + ( parseInt(curYear)-i);	  		
				  		document.getElementById("YearEndDate_"+i).value = nextYear;
				  		
				  	}
			  	}
	  		
	  }
}

function checkCurrentYear(curYear){
	return isCurrentOrPreviousYear(curYear, new Date().getFullYear());
}

function isCurrentOrPreviousYear(year, currentYear){
	year = parseInt(year, 10);
	return year === currentYear || year === currentYear - 1;
}

function chkFiscalYearFormat(curVal){
		  var err =0;	
		  var curYear;  
		  var curMonth;	
		    var firstIndex=curVal.indexOf("/");
		    if(curVal.indexOf(".") >= 0){
		    	err++;
				return false;
		    }
		    if(firstIndex == -1){
		    	err++;
				return false;
		    }
		    var secondIndex=(curVal.substring(firstIndex+1)).indexOf("/");
		    if(secondIndex >= 0){
		    	err++;
				return false;
		    }
		  	curYear = curVal.substring(firstIndex+1,curVal.length);
		  	curMonth = 	 curVal.substring(0,firstIndex);
			
			if (curYear.length <4) {
		    	err++;
				return false;
			}
			if (isNaN(curMonth)) {
		    	err++;
				return false;
			}
			if (isNaN(curYear)) {
		    	err++;
				return false;
			}
			if(curMonth.indexOf("0") == 0){
				curMonth = curMonth.substring(1,curMonth.length);
			}
			var intYear = parseInt(curYear);
			var intMonth = parseInt(curMonth);
			
			
			if (isNaN(intMonth)) {
		    	err++;
				return false;
			}
			if (isNaN(intYear)) {
		    	err++;
				return false;
			}
			if (intMonth<1 || intMonth>12) {
		    	err++;
				return false;
			}

}

function autocalc(oText)
{
if (isNaN(oText.value)) //filter input
{
alert('Numbers only!');
oText.value = '';
}
var field, val, oForm = oText.form, total = a = 0;
for (a; a < arguments.length; ++a) //loop through text elements
{
field = arguments[a];
val = parseFloat(field.value); //get value
if (!isNaN(val)) //number?
{
total += val; //accumulate
}
}
oForm.total.value = total; //out
}
//----------------------------------------tab---------------------------------------------------------
function submitform(a, b, c, d) {
	window.open(a, "_self");
}
function getRadioValue(radioObj)
{

	if(radioObj){
		var radioLength = radioObj.length;
			if(radioLength == undefined)
				if(radioObj.checked)
					return radioObj.value;
				else
					return "N";
	
		for(var i = 0; i < radioLength; i++) {
			if(radioObj[i].checked) {
				return radioObj[i].value;
			}	
		}
	}
	
	return "N";
}


function getRadioValueN(radioObj)
{
	var radioLength = radioObj.length;
		if(radioLength == undefined)
			if(radioObj.checked)
				return radioObj.value;
			else
				return "Y";

	for(var i = 0; i < radioLength; i++) {
		if(radioObj[i].checked) {
			return radioObj[i].value;
		}

	}

	return "Y";
}
function getListValue(listObj)
{
	var listLength = listObj.length;
		if(listLength == undefined)
			if(listObj.checked)
				return listObj.value;
			
	for(var i = 0; i < listLength; i++) {
		if(listObj[i].checked) {
			return listObj[i].value;
		}

	}
	return 0;
}
function getCheckBoxValue(ckObj)
{
	var ckLength = ckObj.length;
		if(ckLength == undefined)
			if(ckObj.checked)
				return ckObj.value;
			
	for(var i = 0; i < ckLength; i++) {
		if(ckObj[i].checked) {
			return ckObj[i].value;
		}

	}
	return 0;
}
// policy Info page

function showother(){
		if (getRadioValueN(document.forms[0].other)=='other')
	{
		showDivArea(['divCy'], ['divCn']);
	}
	else{
		showDivArea(['divCn'], ['divCy']);
	}
}
function loadPolicyholder()
{
	
	if (getRadioValueN(document.forms[0].IsMailingAddressDifferent)=='N')
	{
		
		showDivArea(['div5a'], ['div5b']);
	}
	else {
		
		showDivArea(['div5b'], ['div5a']);
	}
	
}

//---------------------------------------------------------start date function------------------------------------------------
 function GetDay(intDay){
    var DayArray = new Array("Sunday", "Monday", "Tuesday", "Wednesday", 
                         "Thursday", "Friday", "Saturday")
    return DayArray[intDay]
    }

  function GetMonth(intMonth){
    var MonthArray = new Array("January", "February", "March",
                               "April", "May", "June",
                               "July", "August", "September",
                               "October", "November", "December") 
    return MonthArray[intMonth] 	  	 
    }
  function getDateStrWithDOW(){
    var today = new Date()
    var year = today.getYear()
    if(year<1000) year+=1900
    var todayStr = GetDay(today.getDay()) + ", "
    todayStr += GetMonth(today.getMonth()) + " " + today.getDate()
    todayStr += ", " + year
    return todayStr
    }

//--------------------------------------------------------end date function --------------------------------------------------

//------------------------------------------------------start open and close div ----------------------------------------------
var openImg = new Image();
openImg.src = 'img/ftv2mlastnode.gif';
var closedImg = new Image();
closedImg.src = 'img/ftv2pnode.gif';
/*
function switchMenu(obj) {
if (old!=null){old.style.display=?none?;}
var el = document.getElementById(obj);
if ( el.style.display != ?none? ) {
el.style.display = ?none?;
}
else {
el.style.display = ?;
}
*/
var old = null;
function showBranch1(branch1,img1){
	if (old !=null){
		old.style.display="none";
		document.getElementById(img1).src = "img/icon.png";
		
	}
	var objBranch1 = document.getElementById(branch1);
	swapFolder(img1);
	if(objBranch1.style.display=="block"){	
		objBranch1.style.display="none";
		document.getElementById(img1).src = "img/icon.png";
	}
	else{
		
		objBranch1.style.display="block";
		document.getElementById(img1).src = "img/icon.png";
	}
	old=objBranch1;
	
}
function openLeftMenu(menu,img){
	
	var objBranch = document.getElementById(menu);
	if(objBranch.style.visibility=="hidden"){
		objBranch.style.visibility="visible";
		objBranch.style.display="inline-block";
		//objBranch.style.display="moz-inline-stack";
		objBranch.style.height="510px";
		document.getElementById(img).src = "images/yellow-strip.jpg";
	}
	else{
		
		objBranch.style.visibility="hidden";
		objBranch.style.display="none";
		document.getElementById(img).src = "images/yellow-strip1.jpg";
	}
}
function showBranch(branch,img){
	
	var objBranch = document.getElementById(branch);
	if(objBranch.style.display=="block"){
		objBranch.style.display="none";
		document.getElementById(img).src = "img/ftv2pnode.gif";
	}
	else{
		objBranch.style.display="block";
		document.getElementById(img).src = "img/ftv2mlastnode.gif";
	}
}
function showBranch3(branch){
	
	var objBranch = document.getElementById(branch);
	if(objBranch.style.display=="block"){
		objBranch.style.display="none";
		//document.getElementById(img).src = "img/icon_addproject.gif";
	}
	else{
		objBranch.style.display="block";
		//document.getElementById(img).src = "img/icon_addproject.gif";
	}
}
function showMain(main){
	document.getElementById(main).style.display="block";
}
function hideMain(){
	document.getElementById('main0').style.display="none";
}
function closeBranch(main){
	document.getElementById(main).style.display="none";
}
function swapFolder(img1){
	objImg = document.getElementById(img1);
	if(objImg.src.indexOf('img/ftv2pnode.gif')>-1)
		objImg.src = openImg.src;
	else
		objImg.src = closedImg.src;
}
//------------------------------------------------------end open and close div -------------------------------------------------

//-------------------------------------------------- include the page in html----------------------------------------------------


function ajaxsorting(page, containerid,dynakeys,dynavalues)
{
 
    ajaxpagination(page, containerid,dynakeys,dynavalues);

}

function ajaxpagination(page, containerid,dynakeys,dynavalues)
{
  
    var page_request = false;
	var temp = new Array();
    temp = dynakeys.split('|');
    
    var recordPerPage = document.getElementById(temp[6]);
    
    var rePerpagevalue = recordPerPage.options[recordPerPage.selectedIndex].value;
   // alert(rePerpagevalue);
	var pagenumber = document.getElementById(temp[7]);
	var pagenumbervalue = pagenumber.options[pagenumber.selectedIndex].value;
	// alert(pagenumbervalue);
	dynavalues = dynavalues+rePerpagevalue+'|'+pagenumbervalue+'|';
	queryst =  'dynaKeys='+dynakeys+'&dynaValues='+dynavalues+'&ajaxpage='+page;
	
    var url = 'ComponentProcessServlet.do?'+queryst;
  
	retrieveURL1( url, containerid, page_request);
}

var loadedobjects=""
var rootdomain="http://"+window.location.hostname

function ajaxpage(page, containerid,dynakeys,dynavalues)
{
   
   
 	var queryst ="";
   	 
   	var page_request = false;
   	
   	 // to handle the show hide in the list 
	var divid =  document.getElementById('divHideShow').value;
    
    if(divid!='')
    {
    	document.getElementById(divid).innerHTML = '';
    }
 	document.getElementById('divHideShow').value = containerid;
 	//
   	
   	 
	if(dynakeys=='')  
	{
		queryst ='dynaKeys='+'ajaxpage'+'&dynaValues='+'ajaxpage'+'&ajaxpage='+page
  	} else  {
  				queryst =  'dynaKeys='+dynakeys+'&dynaValues='+dynavalues+'&ajaxpage='+page
           //     alert(queryst);
		  	}
 
		var url = 'ComponentProcessServlet.do?'+queryst;
         //alert(url);
		retrieveURL1( url, containerid, page_request);
  }


function ajaxpageblocklink(page, containerid, dynakeys, dynavalues)
{
    
	var page_request = false;
	var queryst ='dynaKeys='+dynakeys  +'&dynaValues='+dynavalues +'&ajaxpage='+page;
	var url = 'ComponentProcessServlet.do?'+queryst;
    retrieveURL1( url, containerid, page_request);
}

function retrieveURL1( url, containerid, page_request)
{
  
	 
	if (window.XMLHttpRequest) // if Mozilla, Safari etc
		page_request = new XMLHttpRequest()
		else if (window.ActiveXObject)
		{                             // if IE
			try {
				page_request = new ActiveXObject("Msxml2.XMLHTTP")
				} 
			catch (e){
						try{
							page_request = new ActiveXObject("Microsoft.XMLHTTP")
						   }
						catch (e){}
					}
		}
		else
		return false
	
	page_request.onreadystatechange=function()
	{
		loadpage(page_request, containerid)
	}
	
	page_request.open('POST', url, true)
	page_request.send(null)

}

function loadpage(page_request, containerid)
{

	if (page_request.readyState == 4 && (page_request.status==200 || window.location.href.indexOf("http")==-1))
	document.getElementById(containerid).innerHTML=page_request.responseText;getTDHeight();
	// alert(page_request.responseText);
}

function loadobjs(){
if (!document.getElementById)
return
for (i=0; i<arguments.length; i++){
var file=arguments[i]
var fileref=""
if (loadedobjects.indexOf(file)==-1){ //Check to see if this object has not already been added to page before proceeding
if (file.indexOf(".js")!=-1){ //If object is a js file
fileref=document.createElement('script')
fileref.setAttribute("type","text/javascript");
fileref.setAttribute("src", file);
}
else if (file.indexOf(".css")!=-1){ //If object is a css file
fileref=document.createElement("link")
fileref.setAttribute("rel", "stylesheet");
fileref.setAttribute("type", "text/css");
fileref.setAttribute("href", file);
}
}
if (fileref!=""){
document.getElementsByTagName("head").item(0).appendChild(fileref)
loadedobjects+=file+" " //Remember this object as being already added to page
}
}
}
//----------------------------------------------------------------------------------------------------------------------------------

//-------------------------------------------common function------------------------------------------------------------

function submitform(a,b,c,d){
		window.open(a,'_self');
	}
//---------------------------------------------------------------------------------------------------------------

//------------------------------------delete confirmation-------------------------------------------------
function delete_this()

{
 var answer = confirm("Do you really want to delete this ??");
 
}
//----------------------------------------------------------------------------------------------------
function popitup(url) {
	newwindow=window.open(url,'name','height=500,width=743,top=100,left=200');
	if (window.focus) {newwindow.focus()}
	return false;
}

function openpopup(){
	
	
	if ((document.getElementById("dropdown").value)=="Y")
	{
		popitup('http://localhost/final/rangevalue.html')
	}
	
}
//-------------------------------------------------------------------------------
function specifyRow(){
		var x=screen.height-300;
		var y=document.getElementById('containerTable').rows
		var z=document.getElementById('leftmenuTable').rows
		var a=screen.height-300;
        y.high.height=x;
		z.lb.height=a;
    }
function fullscreen1xxxx(){
	var win = window.open('field1.html','','fullscreen=yes, toolbar=no,scrollbars=auto');
	//opener=self;
	window.close();

}

//---------------------------------------------rule------------------------------------------

var globalCursorPos; // global variabe to keep track of where the cursor was
//sets the global variable to keep track of the cursor position
function setCursorPos() {
 globalCursorPos = getCursorPos(myForm.myTextArea);
 //alert("ss");
}

//This function returns the index of the cursor location in
//the value of the input text element
//It is important to make sure that the sWeirdString variable contains
//a set of characters that will not be encountered normally in your
//text
function getCursorPos(textElement) {
 //save off the current value to restore it later,
 var sOldText = textElement.value;

//create a range object and save off it's text
 var objRange = document.selection.createRange();
 var sOldRange = objRange.text;

//set this string to a small string that will not normally be encountered
 var sWeirdString = '#%~';

//insert the weirdstring where the cursor is at
 objRange.text = sOldRange + sWeirdString; objRange.moveStart('character', (0 - sOldRange.length - sWeirdString.length));

//save off the new string with the weirdstring in it
 var sNewText = textElement.value;

//set the actual text value back to how it was
 objRange.text = sOldRange;
setTextAreasOnFocus();
//look through the new string we saved off and find the location of
//the weirdstring that was inserted and return that value
 for (i=0; i <= sNewText.length; i++) {
   var sTemp = sNewText.substring(i, i + sWeirdString.length);
   if (sTemp == sWeirdString) {
     var cursorPos = (i - sOldRange.length);
     return cursorPos;
   }
 }
}

//this function inserts the input string into the textarea
//where the cursor was at
function insertString(stringToInsert) {

 var firstPart = myForm.myTextArea.value.substring(0, globalCursorPos);
 var secondPart = myForm.myTextArea.value.substring(globalCursorPos, myForm.myTextArea.value.length);
  myForm.myTextArea.focus();
 myForm.myTextArea.value = firstPart + stringToInsert + secondPart;
}
function setTextAreasOnFocus() {
/***
 * This function will force the cursor to be positioned
 * at the end of all textareas when they receive focus.
 */
    var textAreas = document.getElementsByTagName('myTextArea');

    for(var i = 0; i < textAreas.length; i++) {
        textAreas[i].onfocus = function() {
            setCaretPosition(this.id, this.value.length);
        }
    }

    textAreas = null;
}

function loadQuickQuote()
{
	
	if (getRadioValue(document.forms[0].IsFirmCurrentlyInsuranced)=='Y')
	{
		showDivArea(['divAy'], ['divAn']);
	}
	else {
		
		showDivArea(['divAn'], ['DivAy']);
	}
}
function loadUnderWriting()
{
	
	if (getRadioValue(document.forms[0].HasAdditionalOffices)=='Y')
	{
		showDivArea(['divAy'], ['divAn']);
		showDivArea(['addy'], ['addn']);
		
	}
	else {
		
		showDivArea(['divAn'], ['DivAy']);
		showDivArea(['addn'], ['addy']);
	}
	
	if (getRadioValue(document.forms[0].IsStaffAttorneyRatioGreaterThan)=='Y')
	{
		showDivArea(['divCy'], ['divCn']);
		showDivArea(['addy2'], ['addn']);
		
	}
	else {
		
		showDivArea(['divCn'], ['DivCy']);
		showDivArea(['addn2'], ['addy2']);
	}
	
}
function getTDHeight(){
   // alert('ssss');
	contentTop 		= getPixelsFromTop(document.getElementById("content_top"))
	contentBottom 	= getPixelsFromTop(document.getElementById("content_bottom"))
	heightOfCell 	= (contentBottom - contentTop);
	//document.getElementsByName("heightValue")[0].value = "The cell is " + heightOfCell + "px tall.";
	
		var y=document.getElementById('containerTable').rows
		var z=document.getElementById('leftmenuTable').rows
		
		var h = heightOfCell;
        y.content.height= h
		h = h+8;
		z.content.height=h;
}
function getPixelsFromTop(obj){
	objFromTop = obj.offsetTop;
	while(obj.offsetParent!=null) {
		objParent = obj.offsetParent;
		objFromTop += objParent.offsetTop;
		obj = objParent;
	}
	return objFromTop;
}


function swapImage(newImage){
	document.getElementsByName("heightValue")[0].value = "checking..."
	document.getElementById("content").innerHTML = "<img src='gfx/"+newImage+"'>";
	setTimeout("getTDHeight();",1000); // need to give the image time to load
}
//---------------------------------------------rule------------------------------------------

var globalCursorPos; // global variabe to keep track of where the cursor was
//sets the global variable to keep track of the cursor position
function setCursorPos() {
 globalCursorPos = getCursorPos(myForm.myTextArea);
 //alert("ss");
}

//This function returns the index of the cursor location in
//the value of the input text element
//It is important to make sure that the sWeirdString variable contains
//a set of characters that will not be encountered normally in your
//text
function getCursorPos(textElement) {
 //save off the current value to restore it later,
 var sOldText = textElement.value;

//create a range object and save off it's text
 var objRange = document.selection.createRange();
 var sOldRange = objRange.text;

//set this string to a small string that will not normally be encountered
 var sWeirdString = '#%~';

//insert the weirdstring where the cursor is at
 objRange.text = sOldRange + sWeirdString; objRange.moveStart('character', (0 - sOldRange.length - sWeirdString.length));

//save off the new string with the weirdstring in it
 var sNewText = textElement.value;

//set the actual text value back to how it was
 objRange.text = sOldRange;
setTextAreasOnFocus();
//look through the new string we saved off and find the location of
//the weirdstring that was inserted and return that value
 for (i=0; i <= sNewText.length; i++) {
   var sTemp = sNewText.substring(i, i + sWeirdString.length);
   if (sTemp == sWeirdString) {
     var cursorPos = (i - sOldRange.length);
     return cursorPos;
   }
 }
}

//this function inserts the input string into the textarea
//where the cursor was at
function insertString(stringToInsert) {

 var firstPart = myForm.myTextArea.value.substring(0, globalCursorPos);
 var secondPart = myForm.myTextArea.value.substring(globalCursorPos, myForm.myTextArea.value.length);
  myForm.myTextArea.focus();
 myForm.myTextArea.value = firstPart + stringToInsert + secondPart;
}
function setTextAreasOnFocus() {
/***
 * This function will force the cursor to be positioned
 * at the end of all textareas when they receive focus.
 */
    var textAreas = document.getElementsByTagName('myTextArea');

    for(var i = 0; i < textAreas.length; i++) {
        textAreas[i].onfocus = function() {
            setCaretPosition(this.id, this.value.length);
        }
    }

    textAreas = null;
}



 
function loadQuickQuote()
{
	
	if (getRadioValue(document.forms[0].IsFirmCurrentlyInsuranced)=='Y')
	{
		showDivArea(['divAy'], ['divAn']);
	}
	else {
		
		showDivArea(['divAn'], ['DivAy']);
	}
}
function loadUnderWriting()
{
	
	if (getRadioValue(document.forms[0].HasAdditionalOffices)=='Y')
	{
		showDivArea(['divAy'], ['divAn']);
		showDivArea(['addy'], ['addn']);
		
	}
	else {
		
		showDivArea(['divAn'], ['DivAy']);
		showDivArea(['addn'], ['addy']);
	}
	
	if (getRadioValue(document.forms[0].IsStaffAttorneyRatioGreaterThan)=='Y')
	{
		showDivArea(['divCy'], ['divCn']);
		showDivArea(['addy2'], ['addn']);
		
	}
	else {
		
		showDivArea(['divCn'], ['DivCy']);
		showDivArea(['addn2'], ['addy2']);
	}
	
}
/*
//---------------------------------------floating menu-----------------------------------------------------
var ns = (navigator.appName.indexOf("Netscape") != -1);
var d = document;
function JSFX_FloatDiv(id, sx, sy)
{
	
	var el=d.getElementById?d.getElementById(id):d.all?d.all[id]:d.layers[id];
	var px = document.layers ? "" : "px";
	window[id + "_obj"] = el;
	if(d.layers)el.style=el;
	el.cx = el.sx = sx;el.cy = el.sy = sy;
	el.sP=function(x,y){this.style.left=x+px;this.style.top=y+px;};

	el.floatIt=function()
	{
		
		var pX, pY;
		pX = (this.sx >= 0) ? 0 : ns ? innerWidth : 
		document.documentElement && document.documentElement.clientWidth ? 
		document.documentElement.clientWidth : document.body.clientWidth;
		pY = ns ? pageYOffset : document.documentElement && document.documentElement.scrollTop ? 
		document.documentElement.scrollTop : document.body.scrollTop;
		if(this.sy<0) 
		pY += ns ? innerHeight : document.documentElement && document.documentElement.clientHeight ? 
		document.documentElement.clientHeight : document.body.clientHeight;
		this.cx += (pX + this.sx - this.cx)/8;this.cy += (pY + this.sy - this.cy)/8;
		this.sP(this.cx, this.cy);
		setTimeout(this.id + "_obj.floatIt()", 40);
	}
	return el;
}
JSFX_FloatDiv("leftmenu", 10,30).floatIt();
JSFX_FloatDiv("arrow",0,0).floatIt();
//--------------------------------------end floading menu------------------------------------------------
*/
function gotopage(){
	
	if(document.forms[0].username.value == 'Agent')
	{
		
		window.location = "firmInfo.html"

	}
	if(document.forms[0].username.value == 'Manager')
	{
		window.location = "workq.html"
	}
}

function pleaseDescribe1(isSetFocus) {

    var txtBox = document.getElementById("AOP_Percentage_9");
            if(txtBox.value !=0 && txtBox.value != null && txtBox.value != '')
            {
            	showDivArea(['dis1y'], ['dis1n']); 
            	//if(isSetFocus == 'Y')
            	//	document.getElementById("AOPCommentDesc_9").focus();    
            }
            else{
            	//if(document.getElementById("AOPCommentDesc_9"))
                //	document.getElementById("AOPCommentDesc_9").innerHTML = "";
                	
                showDivArea(['dis1n'], ['dis1y']);
                
                if(document.getElementById("error_AOPCommentDesc_9"))
                	document.getElementById("error_AOPCommentDesc_9").style.display = "none";                
           }

}
function pleaseDescribe2(isSetFocus) {

    var txtBox = document.getElementById("AOP_Percentage_10");
    
            if(txtBox.value !=0 && txtBox.value != null && txtBox.value != '')
            {
            	showDivArea(['dis2y'], ['dis2n']);
            	//if(isSetFocus == 'Y')
            	//	document.getElementById("AOPCommentDesc_10").focus();
            }
            else{
            	//if(document.getElementById("AOPCommentDesc_10"))
                //	document.getElementById("AOPCommentDesc_10").innerHTML = "";
                	
                showDivArea(['dis2n'], ['dis2y']);
                
                if(document.getElementById("error_AOPCommentDesc_10"))
                	document.getElementById("error_AOPCommentDesc_10").style.display = "none";                        
            }
	
}
function pleaseDescribe3(isSetFocus) {

    var txtBox = document.getElementById("AOP_Percentage_12");
            if(txtBox.value !=0 && txtBox.value != null && txtBox.value != '')
            {
	            showDivArea(['dis3y'], ['dis3n']);
	            //if(isSetFocus == 'Y')
	            //	document.getElementById("AOPCommentDesc_12").focus();
	        }
            else{
            	//if(document.getElementById("AOPCommentDesc_12"))
                //	document.getElementById("AOPCommentDesc_12").innerHTML = "";
                	
                showDivArea(['dis3n'], ['dis3y']);
                
                if(document.getElementById("error_AOPCommentDesc_12"))
                	document.getElementById("error_AOPCommentDesc_12").style.display = "none";
            }

}
function pleaseDescribe4(isSetFocus) {

    var txtBox = document.getElementById("AOP_Percentage_21");
            if(txtBox.value !=0 && txtBox.value != null && txtBox.value != '')
            {
	            showDivArea(['dis4y'], ['dis4n']);
	            //if(isSetFocus == 'Y')
	            //	document.getElementById("AOPCommentDesc_21").focus();
	        }
            else{
            	//if(document.getElementById("AOPCommentDesc_21"))
                //	document.getElementById("AOPCommentDesc_21").innerHTML = "";
                	
                showDivArea(['dis4n'], ['dis4y']);
                
                if(document.getElementById("error_AOPCommentDesc_21"))
                	document.getElementById("error_AOPCommentDesc_21").style.display = "none";
            }

}
function pleaseDescribe5(isSetFocus) {
	
	try{
    var txtBox = document.getElementById("AOP_Percentage_20");
    
            if(txtBox.value !=0 && txtBox.value != null && txtBox.value != '')
            {
            	showDivArea(['dis5y'], ['dis5n']);
            	//if(isSetFocus == 'Y')
            	//	document.getElementById("AOPCommentDesc_20").focus();
           	}
            else{
            	//if(document.getElementById("AOPCommentDesc_20"))
                //	document.getElementById("AOPCommentDesc_20").innerHTML = "";
                	
                showDivArea(['dis5n'], ['dis5y']);
                
                if(document.getElementById("error_AOPCommentDesc_20"))
                	document.getElementById("error_AOPCommentDesc_20").style.display = "none";
            }
	}catch (e) {
		// TODO: handle exception
	}
}

function nonedate() {

    var txtBox = document.getElementById("PriorActDatePross");
            if(txtBox.value == "none")
            {
            showDivArea(['divfrm'], ['divfnn']);
            }
            else{
                        showDivArea(['divfnn'], ['divfrm']);
            }
	
}


//------------------------------------ phone no format--------------------------------------------

var n;
var p;
var p1;
function ValidatePhone(){
p=p1.value
if(p.length==3){
	//d10=p.indexOf('(')
	pp=p;
	d4=p.indexOf('(')
	d5=p.indexOf(')')
	if(d4==-1){
		pp="("+pp;
	}
	if(d5==-1){
		pp=pp+")";
	}
	//pp="("+pp+")";
	//document.frmPhone.txtphone.value="";
	//document.frmPhone.txtphone.value=pp;
	p1.value="";
	p1.value=pp;
}
if(p.length>3){
	d1=p.indexOf('(')
	d2=p.indexOf(')')
	if (d2==-1){
		l30=p.length;
		p30=p.substring(0,4);
		//alert(p30);
		p30=p30+")"
		p31=p.substring(4,l30);
		pp=p30+p31;
		//alert(p31);
		//document.frmPhone.txtphone.value="";
		//document.frmPhone.txtphone.value=pp;
		p1.value="";
		p1.value=pp;
	}
	}
if(p.length>5){
	p11=p.substring(d1+1,d2);
	if(p11.length>3){
	p12=p11;
	l12=p12.length;
	l15=p.length
	//l12=l12-3
	p13=p11.substring(0,3);
	p14=p11.substring(3,l12);
	p15=p.substring(d2+1,l15);
	//document.frmPhone.txtphone.value="";
	p1.value="";
	pp="("+p13+")"+p14+p15;
	//document.frmPhone.txtphone.value=pp;
	
	p1.value=pp;
	}
	l16=p.length;
	p16=p.substring(d2+1,l16);
	l17=p16.length;
	if(l17>3&&p16.indexOf('-')==-1){
		p17=p.substring(d2+1,d2+4);
		p18=p.substring(d2+4,l16);
		p19=p.substring(0,d2+1);
		//alert(p19);
	pp=p19+p17+"-"+p18;
	//document.frmPhone.txtphone.value="";
	//document.frmPhone.txtphone.value=pp;
	p1.value="";
	p1.value=pp;
	}
}
//}
setTimeout(ValidatePhone,100)
}
function getIt(m){
n=m.name;
//p1=document.forms[0].elements[n]
p1=m
ValidatePhone()
}

function testphone(obj1){
p=obj1.value
//alert(p)
p=p.replace("(","")
p=p.replace(")","")
p=p.replace("-","")
p=p.replace("-","")
//alert(isNaN(p))
if (isNaN(p)==true){
alert("Check phone");
return false;
}
}

//---------------------------------------end ------------------------------------------------------


//------------------------------------Amount Formatting-----------------------------
var m1;
var m2;
var m3;

function validateAmountFormat(){
  	var nStr = m1.value;
  	var val = parseFloat(nStr);   	
  	if(val=='' || val=='0' || val=='0.0')
  	{
  		m1.value = '';
  		return;
  	}
  	
  	nStr = nStr.toString().replace(/\$|\,/g,'');     
	var objRegExp = /-?[0-9]|\.[0-9]{2}$/;
	if( objRegExp.test(nStr)) {
		objRegExp.compile('^-');
		nStr = addCommas(nStr);
		if (objRegExp.test(nStr)){
			nStr = '(' + nStr.replace(objRegExp,'') + ')';
		}
		m1.value =  '$' + nStr;
	}      
	else
	{
		m1.value =  nStr;
	}
  	setTimeout(validateAmountFormat,100)
}

function amountFormat(m){
	m1=m;
	validateAmountFormat()
}

function validateAmountFormatOnLoad(){
  	var nStr = m2.value; 
  	//alert('firstval...'+nStr);
  	var val = parseFloat(nStr);
  	//alert('lastval...'+val);  			
  	if(val=='' || val=='0' || val=='0.0')
  	{
  		m2.value = '';
  		return;
  	}
  		
  	val = val.toString().replace(/\$|\,/g,'');     
	var objRegExp = /-?[0-9]|\.[0-9]{2}$/;
	if( objRegExp.test(val)) {
		objRegExp.compile('^-');
		nStr = addCommas(nStr);
		if (objRegExp.test(val)){
			val = '(' + val.replace(objRegExp,'') + ')';
		}
		m2.value =  '$' + val;
	}      
	else
	{
		m2.value =  val;
	} 	
}

function amountFormatOnLoad(m){
	m2=m;
	validateAmountFormatOnLoad()
}

function validateNumeric(){
  	var nStr = m3.value; 
  	var val = parseFloat(nStr);  			
  	if(val=='' || val=='0' || val=='0.0')
  	{
  		m3.value = '';
  		return;
  	}
  	
  	m3.value =  nStr;
}

function validateNumericOnLoad(m){
	m3=m;
	validateNumeric()
}

var m4;
function addCommaAmountFormat(){
  	var nStr = m4.value;
  	var val = parseFloat(nStr);   	
  	if(val=='' || val=='0' || val=='0.0')
  	{
  		m4.value = '';
  		return;
  	}
  	
  	nStr = nStr.toString().replace(/\$|\,/g,'');     
	var objRegExp = /-?[0-9]|\.[0-9]{2}$/;
	if( objRegExp.test(nStr)) {
		objRegExp.compile('^-');
		nStr = addCommas(nStr);
		if (objRegExp.test(nStr)){
			nStr = '(' + nStr.replace(objRegExp,'') + ')';
		}
		m4.value =  nStr;
	}      
	else
	{
		m4.value =  nStr;
	}
  	setTimeout(addCommaAmountFormat,100)
}


function addCommaToAmount(m){
	m4=m;
	addCommaAmountFormat()
}

function addCommaAmountFormatOnLoad(){
  	var nStr = m4.value;
  	var val = parseFloat(nStr);   	
  	if(val=='' || val=='0' || val=='0.0')
  	{
  		m4.value = '';
  		return;
  	}
  	
  	nStr = nStr.toString().replace(/\$|\,/g,'');     
	var objRegExp = /-?[0-9]|\.[0-9]{2}$/;
	if( objRegExp.test(nStr)) {
		objRegExp.compile('^-');
		nStr = addCommas(nStr);
		if (objRegExp.test(nStr)){
			nStr = '(' + nStr.replace(objRegExp,'') + ')';
		}
		m4.value =  nStr;
	}      
	else
	{
		m4.value =  nStr;
	}
  	
}

function addCommaToAmountOnLoad(m){
	m4=m;
	addCommaAmountFormatOnLoad()
}

function attachAmountFormat(obj){

	if(obj == null)
		return;
		
	amountMask = new Mask("$#,###", "number");
	amountMask.attach(obj); 
}

function attachPercentageFormat(obj){
	if(obj == null)
		return;
		
	percentageMask = new Mask("###", "number");
	percentageMask.attach(obj); 
}

function attachMMYYYYDateFormat(obj){
	if(obj == null)
		return;
	
	dateMask = new Mask("mm/yyyy", "date");
	dateMask.attach(obj); 
}

function attachMMDDYYYYDateFormat(obj){
	if(obj == null)
		return;
		
	dateMask = new Mask("mm/dd/yyyy", "date");
	dateMask.attach(obj); 
}

function attachPhoneFormat(obj){
	if(obj == null)
		return;
		
	phoneMask = new Mask("(###)###-####");
	phoneMask.attach(obj); 
}


function amountFormatingForBasicinfo(){

	amountMask = new Mask("$#,###", "number");
	
	var amt0 = document.getElementById("Amount_0");
	var amt1 = document.getElementById("Amount_1");
	var amt2 = document.getElementById("Amount_2");
	var amt3 = document.getElementById("Premium");
	
	if(amt0 != null )
	{
		amountMask.attach(amt0);	
		if(amt0.value == '0')
			amt0.value = '';
			
		amt0.value=amountMask.format(amt0.value);	
			
	}
	
	if(amt1 != null)
	{
		amountMask.attach(amt1);  
		if(amt1.value == '0')
			amt1.value = '';
			
		amt1.value=amountMask.format(amt1.value);
	}
	if(amt2 != null)
	{
		amountMask.attach(amt2); 		
		if(amt2.value == '0')
			amt2.value = '';
			
		amt2.value=amountMask.format(amt2.value);
	}		
	if(amt3 != null )
	{
		amountMask.attach(amt3);  
		if(amt3.value == '0')
			amt3.value = '';
			
		amt3.value=amountMask.format(amt3.value);
	}
		
	var afamt0 = document.getElementById("ajax_field_InsurerLossReserveCi");
	var afamt1 = document.getElementById("ajax_field_LastSettlementOfferCi");
	var afamt2 = document.getElementById("ajax_field_ClaimantLastDemandCi");
	var afamt3 = document.getElementById("ajax_field_TotalClaimExpensesCi");
	var afamt4 = document.getElementById("ajax_field_FeesChargedPpo");
	if(afamt0 != null)
	{
		afamt0.value=amountMask.format(afamt0.value);	
		amountMask.attach(afamt0);  
	}
	if(afamt1 != null)
	{
		afamt1.value=amountMask.format(afamt1.value);	
		amountMask.attach(afamt1);   
	}
	if(afamt2 != null)
	{
		afamt2.value=amountMask.format(afamt2.value);	
		amountMask.attach(afamt2);    
	}
	if(afamt3 != null)
	{
		afamt3.value=amountMask.format(afamt3.value);	
		amountMask.attach(afamt3);   
	}
	if(afamt4 != null)
	{
		afamt4.value=amountMask.format(afamt4.value);	
		amountMask.attach(afamt4);    
	}	
	
	var afamt7 = document.getElementById("ProInsPremium");		
	if(afamt7 != null)
	{
		afamt7.value=amountMask.format(afamt7.value);	
		amountMask.attach(afamt7);      
	}
		
	numberMask = new Mask("###", "number");
	
    var person0 = document.getElementById("NumberOfPersonnel_0");
	var person1 = document.getElementById("NumberOfPersonnel_1");
	var person2 = document.getElementById("NumberOfPersonnel_2");
	var person3 = document.getElementById("NumberOfPersonnel_3");
	var person4 = document.getElementById("NumberOfPersonnel_4");
	var person5 = document.getElementById("NumberOfPersonnel_5");
	var person6 = document.getElementById("ajax_field_YearServiceRenderedPpo");
	var person7 = document.getElementById("ajax_field_SizeOfOfferingPpo");
	var person8 = document.getElementById("AOP_Percentage_1");
	var person9 = document.getElementById("AOP_Percentage_2");
	var person10 = document.getElementById("AOP_Percentage_3");
	var person11 = document.getElementById("AOP_Percentage_4");
	var person12 = document.getElementById("FirmYear");
	var person13 = document.getElementById("AOP_Percentage_5");
	var person14 = document.getElementById("AOP_Percentage_6");
	var person15 = document.getElementById("AOP_Percentage_7");
	var person16 = document.getElementById("AOP_Percentage_8");
	var person17 = document.getElementById("AOP_Percentage_9");
	var person18 = document.getElementById("AOP_Percentage_10");
	var person19 = document.getElementById("AOP_Percentage_11");
	var person20 = document.getElementById("AOP_Percentage_12");
	var person21 = document.getElementById("AOP_Percentage_13");
	var person22 = document.getElementById("AOP_Percentage_14");
	var person23 = document.getElementById("AOP_Percentage_15");
	var person24 = document.getElementById("AOP_Percentage_16");
	var person25 = document.getElementById("AOP_Percentage_17");
	var person26 = document.getElementById("AOP_Percentage_18");
	var person27 = document.getElementById("AOP_Percentage_19");
	var person28 = document.getElementById("AOP_Percentage_20");
	var person29 = document.getElementById("AOP_Percentage_21");
	var person30 = document.getElementById("PercentOfRevenueFromLargestClient");
	var person31 = document.getElementById("PercentOfRevenueFromSecondLargestClient");
	
	var person32 = document.getElementById("AOP_Percentage_22");
	var person33 = document.getElementById("AOP_Percentage_23");
	var person34 = document.getElementById("AOP_Percentage_24");
	
	
	if(person0 != null)
	{
		numberMask.attach(person0);
		if(person0.value =='0')			
			person0.value = ''; 
	}
	if(person1 != null)
	{
		numberMask.attach(person1);
		if(person1.value =='0')			
			person1.value = ''; 
	}
	if(person2 != null)
	{
		numberMask.attach(person2);
		if(person2.value =='0')			
			person2.value = ''; 
	}
	if(person3 != null)
	{
		numberMask.attach(person3);
		if(person3.value =='0')			
			person3.value = ''; 
	}
	if(person4 != null)
	{
		numberMask.attach(person4);
		if(person4.value =='0')			
			person4.value = ''; 
	}
	if(person5 != null)
	{
		numberMask.attach(person5);
		if(person5.value =='0')			
			person5.value = ''; 
	}			
	if(person6 != null)
	{
		numberMask.attach(person6);
		if(person6.value =='0')			
			person6.value = ''; 
	}			
	if(person7 != null)
	{
		numberMask.attach(person7);
		if(person7.value =='0')			
			person7.value = ''; 
	}			
	if(person8 != null)
	{
		numberMask.attach(person8);
		if(person8.value =='0')			
			person8.value = ''; 
	}			
	if(person9 != null)
	{
		numberMask.attach(person9);
		if(person9.value =='0')			
			person9.value = ''; 
	}			
	if(person10 != null)
	{
		numberMask.attach(person10);
		if(person10.value =='0')			
			person10.value = ''; 
	}			
	if(person11 != null)
	{
		numberMask.attach(person11);
		if(person11.value =='0')			
			person11.value = ''; 
	}			
	if(person12 != null)
	{
		numberMask.attach(person12);
		if(person12.value =='0')			
			person12.value = ''; 
	}			

	if(person13 != null)
	{
		numberMask.attach(person13);
		if(person13.value =='0')			
			person13.value = ''; 
	}			
	if(person14 != null)
	{
		numberMask.attach(person14);
		if(person14.value =='0')			
			person14.value = ''; 
	}			
	if(person15 != null)
	{
		numberMask.attach(person15);
		if(person15.value =='0')			
			person15.value = ''; 
	}			
	if(person16 != null)
	{
		numberMask.attach(person16);
		if(person16.value =='0')			
			person16.value = '';
	}			
	if(person17 != null)
	{
		numberMask.attach(person17);
		if(person17.value =='0')			
			person17.value = ''; 
	}			
	if(person18 != null)
	{
		numberMask.attach(person18);
		if(person18.value =='0')			
			person18.value = ''; 
	}			
	if(person19 != null)
	{
		numberMask.attach(person19);
		if(person19.value =='0')			
			person19.value = ''; 
	}			
	if(person20 != null)
	{
		numberMask.attach(person20);
		if(person20.value =='0')			
			person20.value = '';
	}			
	if(person21 != null)
	{
		numberMask.attach(person21);
		if(person21.value =='0')			
			person21.value = ''; 
	}			
	if(person22 != null)
	{
		numberMask.attach(person22);
		if(person22.value =='0')			
			person22.value = '';
	}			
	if(person23 != null)
	{
		numberMask.attach(person23);
		if(person23.value =='0')			
			person23.value = ''; 
	}			
	if(person24 != null)
	{
		numberMask.attach(person24);
		if(person24.value =='0')			
			person24.value = ''; 
	}			
	if(person25 != null)
	{
		numberMask.attach(person25);
		if(person25.value =='0')			
			person25.value = ''; 
	}			
	if(person26 != null)
	{
		numberMask.attach(person26);
		if(person26.value =='0')			
			person26.value = ''; 
	}			
	if(person27 != null)
	{
		numberMask.attach(person27);
		if(person27.value =='0')			
			person27.value = ''; 
	}		
	if(person28 != null)
	{
		numberMask.attach(person28);
		if(person28.value =='0')			
			person28.value = ''; 
	}
	if(person29 != null)
	{
		numberMask.attach(person29);
		if(person29.value =='0')			
			person29.value = ''; 
	}		
	if(person30 != null)
	{
		numberMask.attach(person30);
		if(person30.value =='0')			
			person30.value = ''; 
	}	
	if(person31 != null)
	{
		numberMask.attach(person31);
		if(person31.value =='0')			
			person31.value = ''; 
	}
	
	if(person32 != null)
	{
		numberMask.attach(person32);
		//alert('person32');
		if(person32.value =='0')	
		{
			person32.value = ''; 
			//alert('person32');
		}		
			
	}
	if(person33 != null)
	{
		numberMask.attach(person33);
		if(person33.value =='0')			
			person33.value = ''; 
	}
	if(person34 != null)
	{
		numberMask.attach(person34);
		if(person34.value =='0')			
			person34.value = ''; 
	}	
	/*
	var person35 = document.getElementById("Zip4");			
	if(person35 != null)
	{
		numberMask.attach(person35);
	}	
	*/
	phoneMask = new Mask("(###)###-####");
	
	var wrkphone = document.getElementById("WorkPhone");
	var cntphone = document.getElementById("ContactPhone");
	
	if(wrkphone != null)
	{	
		wrkphone.value=phoneMask.format(wrkphone.value);
		phoneMask.attach(wrkphone);						
	}
	if(cntphone != null)
	{	
		cntphone.value=phoneMask.format(cntphone.value);
		phoneMask.attach(cntphone);							
	}	
	
	mmyyyyMask = new Mask("mm/yyyy", "date");
	
	var YearEndDate_0 = document.getElementById("YearEndDate_0");
	var YearEndDate_1 = document.getElementById("YearEndDate_1");
	var YearEndDate_2 = document.getElementById("YearEndDate_2");
		
	if(YearEndDate_0 != null)
	{	
		//mmyyyyMask.attach(YearEndDate_0);
	}
	if(YearEndDate_1 != null)
	{	
		//mmyyyyMask.attach(YearEndDate_1);
	}
	if(YearEndDate_2 != null)
	{	
		//mmyyyyMask.attach(YearEndDate_2);
	}	
		
	mmddyyyyMask = new Mask("mm/dd/yyyy", "date");
	
	var YearEndDate_3 = document.getElementById("PolicyEffectiveDate");
	var YearEndDate_4 = document.getElementById("PolicyExpirationDatePross");
	var YearEndDate_5 = document.getElementById("PriorActDatePross");
	
	if(YearEndDate_3 != null)
	{	
		//mmddyyyyMask.attach(YearEndDate_3);
	}
	if(YearEndDate_4 != null)
	{	
		//mmddyyyyMask.attach(YearEndDate_4);
	}
	if(YearEndDate_5 != null)
	{	
		//mmddyyyyMask.attach(YearEndDate_5);
	}
	
	var afamt0 = document.getElementById("ajax_field_ValueOfAssetsTrust");
	var afamt1 = document.getElementById("ajax_field_AnnualIncomeofAssetsTrust");
	
	var afamt2 = document.getElementById("ajax_field_AmountOfFeesSued");
	
	if(afamt0 != null)
	{
		afamt0.value=amountMask.format(afamt0.value);	
		amountMask.attach(afamt0);     
	}
	if(afamt1 != null)
	{
		afamt1.value=amountMask.format(afamt1.value);	
		amountMask.attach(afamt1);     
	}
	if(afamt2 != null)
	{
		afamt2.value=amountMask.format(afamt2.value);	
		amountMask.attach(afamt2);     
	}	
	
}

function amountFormatingForGeneral(){

	amountMask = new Mask("$#,###", "number");
	
	var currYear = document.getElementById("EstimateCurrentYearGrossRevenueSe");
	var lastYear = document.getElementById("LastFiscalYearGrossRevenueSe");
	var clientfund = document.getElementById("TotalAmountOfClientFundsFc");	
		
	if(currYear != null)
	{
		currYear.value=amountMask.format(currYear.value);	
		amountMask.attach(currYear);     
	}
	if(lastYear != null)
	{
		lastYear.value=amountMask.format(lastYear.value);	
		amountMask.attach(lastYear);   
	}
	if(clientfund != null)
	{
		clientfund.value=amountMask.format(clientfund.value);	
		amountMask.attach(clientfund); 
	}
	
	var afamt0 = document.getElementById("ajax_field_ValueOfAssetsTrust");
	var afamt1 = document.getElementById("ajax_field_AnnualIncomeofAssetsTrust");
	
	var afamt2 = document.getElementById("ajax_field_AmountOfFeesSued");
	
	if(afamt0 != null)
	{
		afamt0.value=amountMask.format(afamt0.value);	
		amountMask.attach(afamt0);     
	}
	if(afamt1 != null)
	{
		afamt1.value=amountMask.format(afamt1.value);	
		amountMask.attach(afamt1);     
	}
	
	if(afamt2 != null)
	{
		afamt2.value=amountMask.format(afamt2.value);	
		amountMask.attach(afamt2);     
	}
	
	numberMask = new Mask("###", "number");
	
	var person9 = document.getElementById("AgainstHowManyClients");	
    var person0 = document.getElementById("TotalProfessionalStaffSe");
	var person1 = document.getElementById("TotalSupportStaffSe");
	var person2 = document.getElementById("TotlaNumberOfClientsFc");
	var person3 = document.getElementById("NumberOfClientsFc");
	var person4 = document.getElementById("ProvideNumberOfClientFc");
	var person5 = document.getElementById("NumberOfBeneficiariesTrust");
	var person6 = document.getElementById("NumberOfFirmEmployeesFc");
	//var person8 = document.getElementById("Zip4");
	
	if(person9 != null)
	{
		numberMask.attach(person9);		
	}
	if(person0 != null)
	{
		numberMask.attach(person0);		
	}
	if(person1 != null)
	{
		numberMask.attach(person1); 
	}
	if(person2 != null)
	{
		numberMask.attach(person2); 
	}
	if(person3 != null)
	{
		numberMask.attach(person3); 
	}
	if(person4 != null)
	{
		numberMask.attach(person4); 
	}
	if(person5 != null)
	{
		numberMask.attach(person5); 
	}
	if(person6 != null)
	{
		numberMask.attach(person6); 
	}	
	/*			
	if(person8 != null)
	{
		numberMask.attach(person8);
	}	
	
	
	phoneMask = new Mask("(###)###-####");
	
	var wrkphone = document.getElementById("WorkPhone");
	var cntphone = document.getElementById("ContactPhone");
	//var wrkphoneext = document.getElementById("WorkExt");
	//var cntphoneext = document.getElementById("ContactPhoneExt");
	
	if(wrkphone != null)
	{	
		wrkphone.value=phoneMask.format(wrkphone.value);
		phoneMask.attach(wrkphone);						
	}
	if(cntphone != null)
	{	
		cntphone.value=phoneMask.format(cntphone.value);
		phoneMask.attach(cntphone);							
	}
	*/
	/*
	if(wrkphoneext != null)
	{
		numberMask.attach(wrkphoneext); 
	}			
	if(cntphoneext != null)
	{
		numberMask.attach(cntphoneext);
	}
	*/
	
	mmyyyyMask = new Mask("mm/yyyy", "date");
	
	var YearEndDate_0 = document.getElementById("EstimateCurrentYearSeEndDate");
	var YearEndDate_1 = document.getElementById("LastFiscalYearSeEndDate");
		
	if(YearEndDate_0 != null)
	{	
		//mmyyyyMask.attach(YearEndDate_0);
	}
	if(YearEndDate_1 != null)
	{	
		//mmyyyyMask.attach(YearEndDate_1);
	}	
}

function amountFormatingForPM(){

	amountMask = new Mask("$#,###", "number");
	
	var amt0 = document.getElementById("CurrentValueNmc");
	if(amt0 != null)
	{
		amt0.value=amountMask.format(amt0.value);	
		amountMask.attach(amt0);     
	}
	
	
}


function amountFormatingForAttestaion(){
	
	var totalAuditClients = 0;
	var totalClientsAssetsOver = 0;
	var totalNetLossForLastFYE = 0; 
	
	numberMask = new Mask("###", "number");
	
	for(var i=0; i<26; i++)
	{
		if(document.getElementById("AuditClients_"+i) != null)
		{			
			if(document.getElementById("AuditClients_"+i).value != '')
				totalAuditClients = parseInt(document.getElementById("AuditClients_"+i).value) + totalAuditClients;
			
			numberMask.attach(document.getElementById("AuditClients_"+i));
			if(document.getElementById("AuditClients_"+i).value == '0')
			{
				document.getElementById("AuditClients_"+i).value ='';
			}
		}
		
		if(document.getElementById("ClientsAssetsOver_"+i) != null)
		{
			if(document.getElementById("ClientsAssetsOver_"+i).value != '')
				totalClientsAssetsOver = parseInt(document.getElementById("ClientsAssetsOver_"+i).value) + totalClientsAssetsOver;
			
			numberMask.attach(document.getElementById("ClientsAssetsOver_"+i));
			if(document.getElementById("ClientsAssetsOver_"+i).value == '0')
			{
				document.getElementById("ClientsAssetsOver_"+i).value ='';
			}
		}
		
		if(document.getElementById("NetLossForLastFYE_"+i) != null)
		{
			if(document.getElementById("NetLossForLastFYE_"+i).value != '')
				totalNetLossForLastFYE = parseInt(document.getElementById("NetLossForLastFYE_"+i).value) + totalNetLossForLastFYE;
			
			numberMask.attach(document.getElementById("NetLossForLastFYE_"+i));
			if(document.getElementById("NetLossForLastFYE_"+i).value == '0')
			{
				document.getElementById("NetLossForLastFYE_"+i).value ='';
			}
		}
		
	}	
	
	document.getElementById("TotalAuditClients").innerHTML = totalAuditClients;
	document.getElementById("TotalClientsAssetsOver").innerHTML = totalClientsAssetsOver;
	document.getElementById("TotalNetLossForLastFYE").innerHTML = totalNetLossForLastFYE;
	
	/*
	numberMask = new Mask("###", "number");
	
    var client0 = document.getElementById("AuditClients_0");
    var client1 = document.getElementById("AuditClients_1");
    var client2 = document.getElementById("AuditClients_2");
    var client3 = document.getElementById("AuditClients_3");
    var client4 = document.getElementById("AuditClients_4");
    var client5 = document.getElementById("AuditClients_5");
    var client6 = document.getElementById("AuditClients_6");
    var client7 = document.getElementById("AuditClients_7");
    var client8 = document.getElementById("AuditClients_8");
    var client9 = document.getElementById("AuditClients_9");
    var client10 = document.getElementById("AuditClients_10");
    var client11 = document.getElementById("AuditClients_11");
    var client12 = document.getElementById("AuditClients_12");
    var client13 = document.getElementById("AuditClients_13");
    var client14 = document.getElementById("AuditClients_14");
    var client15 = document.getElementById("AuditClients_15");
    var client16 = document.getElementById("AuditClients_16");
    var client17 = document.getElementById("AuditClients_17");
    var client18 = document.getElementById("AuditClients_18");
    var client19 = document.getElementById("AuditClients_19");
    var client20 = document.getElementById("AuditClients_20");
    var client21 = document.getElementById("AuditClients_21");
    var client22 = document.getElementById("AuditClients_22");
    var client23 = document.getElementById("AuditClients_23");
    var client24 = document.getElementById("AuditClients_24");    
    
	if(client0 != null)
	{
		numberMask.attach(client0);		 
	}
	if(client1 != null)
	{
		numberMask.attach(client1);		 
	}
	if(client2 != null)
	{
		numberMask.attach(client2);		 
	}
	if(client3 != null)
	{
		numberMask.attach(client3);		 
	}
	if(client4 != null)
	{
		numberMask.attach(client4);		 
	}
	if(client5 != null)
	{
		numberMask.attach(client5);		 
	}
	if(client6 != null)
	{
		numberMask.attach(client6);		 
	}
	if(client7 != null)
	{
		numberMask.attach(client7);		 
	}
	if(client8 != null)
	{
		numberMask.attach(client8);		 
	}
	if(client9 != null)
	{
		numberMask.attach(client9);		 
	}
	if(client10 != null)
	{
		numberMask.attach(client10);		 
	}
	if(client11 != null)
	{
		numberMask.attach(client11);		 
	}
	if(client12 != null)
	{
		numberMask.attach(client12);		 
	}
	if(client13 != null)
	{
		numberMask.attach(client13);		 
	}
	if(client14 != null)
	{
		numberMask.attach(client14);		 
	}
	if(client15 != null)
	{
		numberMask.attach(client15);		 
	}
	if(client16 != null)
	{
		numberMask.attach(client16);		 
	}
	if(client17 != null)
	{
		numberMask.attach(client17);		 
	}
	if(client18 != null)
	{
		numberMask.attach(client18);		 
	}
	if(client19 != null)
	{
		numberMask.attach(client19);		 
	}
	if(client20 != null)
	{
		numberMask.attach(client20);		 
	}
	if(client21 != null)
	{
		numberMask.attach(client21);		 
	}
	if(client22 != null)
	{
		numberMask.attach(client22);		 
	}
	if(client23 != null)
	{
		numberMask.attach(client23);		 
	}
	if(client24 != null)
	{
		numberMask.attach(client24);		 
	}
	
	
	amountMask = new Mask("$#,###", "number");
	
	var amt0 = document.getElementById("ClientsAssetsOver_0");
	var amt1 = document.getElementById("ClientsAssetsOver_1");
	var amt2 = document.getElementById("ClientsAssetsOver_2");
	var amt3 = document.getElementById("ClientsAssetsOver_3");
	var amt4 = document.getElementById("ClientsAssetsOver_4");	
	var amt5 = document.getElementById("ClientsAssetsOver_5");
	var amt6 = document.getElementById("ClientsAssetsOver_6");
	var amt7 = document.getElementById("ClientsAssetsOver_6");
	var amt8 = document.getElementById("ClientsAssetsOver_8");
	var amt9 = document.getElementById("ClientsAssetsOver_9");	
	var amt10 = document.getElementById("ClientsAssetsOver_10");
	var amt11 = document.getElementById("ClientsAssetsOver_11");
	var amt12 = document.getElementById("ClientsAssetsOver_12");
	var amt13 = document.getElementById("ClientsAssetsOver_13");
	var amt14 = document.getElementById("ClientsAssetsOver_14");	
	var amt15 = document.getElementById("ClientsAssetsOver_15");
	var amt16 = document.getElementById("ClientsAssetsOver_16");
	var amt17 = document.getElementById("ClientsAssetsOver_17");
	var amt18 = document.getElementById("ClientsAssetsOver_18");
	var amt19 = document.getElementById("ClientsAssetsOver_19");
	var amt20 = document.getElementById("ClientsAssetsOver_20");
	var amt21 = document.getElementById("ClientsAssetsOver_21");
	var amt22 = document.getElementById("ClientsAssetsOver_22");
	var amt23 = document.getElementById("ClientsAssetsOver_23");
	var amt24 = document.getElementById("ClientsAssetsOver_24");
	
	
	if(amt0 != null)
	{
		numberMask.attach(amt0); 		     
	}	
	if(amt1 != null)
	{
		numberMask.attach(amt1); 		     
	}
	if(amt2 != null)
	{
		numberMask.attach(amt2); 		     
	}
	if(amt3 != null)
	{
		numberMask.attach(amt3); 		     
	}
	if(amt4 != null)
	{
		numberMask.attach(amt4); 		     
	}
	if(amt5 != null)
	{
		numberMask.attach(amt5); 		     
	}
	if(amt6 != null)
	{
		numberMask.attach(amt6); 		     
	}
	if(amt7 != null)
	{
		numberMask.attach(amt7); 		     
	}
	if(amt8 != null)
	{
		numberMask.attach(amt8); 		     
	}
	if(amt9 != null)
	{
		numberMask.attach(amt9); 		     
	}
	
	if(amt10 != null)
	{
		numberMask.attach(amt10); 		     
	}	
	if(amt11 != null)
	{
		numberMask.attach(amt11); 		     
	}
	if(amt12 != null)
	{
		numberMask.attach(amt12); 		     
	}
	if(amt13 != null)
	{
		numberMask.attach(amt13); 		     
	}
	if(amt14 != null)
	{
		numberMask.attach(amt14); 		     
	}
	if(amt15 != null)
	{
		numberMask.attach(amt15); 		     
	}
	if(amt16 != null)
	{
		numberMask.attach(amt16); 		     
	}
	if(amt17 != null)
	{
		numberMask.attach(amt17); 		     
	}
	if(amt18 != null)
	{
		numberMask.attach(amt18); 		     
	}
	if(amt19 != null)
	{
		numberMask.attach(amt19); 		     
	}
	
	if(amt20 != null)
	{
		numberMask.attach(amt20); 		     
	}
	if(amt21 != null)
	{
		numberMask.attach(amt21); 		     
	}
	if(amt22 != null)
	{
		numberMask.attach(amt22); 		     
	}
	if(amt23 != null)
	{
		numberMask.attach(amt23); 		     
	}
	if(amt24 != null)
	{
		numberMask.attach(amt24); 		     
	}
	
	var amtt0 = document.getElementById("NetLossForLastFYE_0"); 
	var amtt1 = document.getElementById("NetLossForLastFYE_1");
	var amtt2 = document.getElementById("NetLossForLastFYE_2");
	var amtt3 = document.getElementById("NetLossForLastFYE_3");
	var amtt4 = document.getElementById("NetLossForLastFYE_4");	
	var amtt5 = document.getElementById("NetLossForLastFYE_5");
	var amtt6 = document.getElementById("NetLossForLastFYE_6");
	var amtt7 = document.getElementById("NetLossForLastFYE_6");
	var amtt8 = document.getElementById("NetLossForLastFYE_8");
	var amtt9 = document.getElementById("NetLossForLastFYE_9");	
	var amtt10 = document.getElementById("NetLossForLastFYE_10");
	var amtt11 = document.getElementById("NetLossForLastFYE_11");
	var amtt12 = document.getElementById("NetLossForLastFYE_12");
	var amtt13 = document.getElementById("NetLossForLastFYE_13");
	var amtt14 = document.getElementById("NetLossForLastFYE_14");	
	var amtt15 = document.getElementById("NetLossForLastFYE_15");
	var amtt16 = document.getElementById("NetLossForLastFYE_16");
	var amtt17 = document.getElementById("NetLossForLastFYE_17");
	var amtt18 = document.getElementById("NetLossForLastFYE_18");
	var amtt19 = document.getElementById("NetLossForLastFYE_19");
	var amtt20 = document.getElementById("NetLossForLastFYE_20");
	var amtt21 = document.getElementById("NetLossForLastFYE_21");
	var amtt22 = document.getElementById("NetLossForLastFYE_22");
	var amtt23 = document.getElementById("NetLossForLastFYE_23");
	var amtt24 = document.getElementById("NetLossForLastFYE_24");
	
	
	if(amtt0 != null)
	{
		numberMask.attach(amtt0); 		     
	}	
	if(amtt1 != null)
	{
		numberMask.attach(amtt1); 		     
	}
	if(amtt2 != null)
	{
		numberMask.attach(amtt2); 		     
	}
	if(amtt3 != null)
	{
		numberMask.attach(amtt3); 		     
	}
	if(amtt4 != null)
	{
		numberMask.attach(amtt4); 		     
	}
	if(amtt5 != null)
	{
		numberMask.attach(amtt5); 		     
	}
	if(amtt6 != null)
	{
		numberMask.attach(amtt6); 		     
	}
	if(amtt7 != null)
	{
		numberMask.attach(amtt7); 		     
	}
	if(amtt8 != null)
	{
		numberMask.attach(amtt8); 		     
	}
	if(amtt9 != null)
	{
		numberMask.attach(amtt9); 		     
	}
	
	if(amtt10 != null)
	{
		numberMask.attach(amtt10); 		     
	}	
	if(amtt11 != null)
	{
		numberMask.attach(amtt11); 		     
	}
	if(amtt12 != null)
	{
		numberMask.attach(amtt12); 		     
	}
	if(amtt13 != null)
	{
		numberMask.attach(amtt13); 		     
	}
	if(amtt14 != null)
	{
		numberMask.attach(amtt14); 		     
	}
	if(amtt15 != null)
	{
		numberMask.attach(amtt15); 		     
	}
	if(amtt16 != null)
	{
		numberMask.attach(amtt16); 		     
	}
	if(amtt17 != null)
	{
		numberMask.attach(amtt17); 		     
	}
	if(amtt18 != null)
	{
		numberMask.attach(amtt18); 		     
	}
	if(amtt19 != null)
	{
		numberMask.attach(amtt19); 		     
	}
	
	if(amtt20 != null)
	{
		numberMask.attach(amtt20); 		     
	}
	if(amtt21 != null)
	{
		numberMask.attach(amtt21); 		     
	}
	if(amtt22 != null)
	{
		numberMask.attach(amtt22); 		     
	}
	if(amtt23 != null)
	{
		numberMask.attach(amtt23); 		     
	}
	if(amtt24 != null)
	{
		numberMask.attach(amtt24); 		     
	}	
	*/
}


function amountFormatingForCS(){	

	amountMask = new Mask("$#,###", "number");
	
	var amt0 = document.getElementById("RevenuesFromLastYear_0");
	var amt1 = document.getElementById("RevenuesFromLastYear_1");
	var amt2 = document.getElementById("RevenuesFromLastYear_2");
	var amt3 = document.getElementById("RevenuesFromLastYear_3");
	var amt4 = document.getElementById("RevenuesFromLastYear_4");
	
	var amt5 = document.getElementById("EstimatedRevenue_0");
	var amt6 = document.getElementById("EstimatedRevenue_1");
	var amt7 = document.getElementById("EstimatedRevenue_2");
	var amt8 = document.getElementById("EstimatedRevenue_3");
	var amt9 = document.getElementById("EstimatedRevenue_4");
	
	var amt10 = document.getElementById("RevenuesFromLastYear_5");
	var amt11 = document.getElementById("EstimatedRevenue_5");
	
	if(amt0 != null)
	{
		amountMask.attach(amt0);	
		if(amt0.value == '0')
			amt0.value = '';
			
		amt0.value=amountMask.format(amt0.value);				     
	}	
	if(amt1 != null)
	{
		amountMask.attach(amt1);	
		if(amt1.value == '0')
			amt1.value = '';
			
		amt1.value=amountMask.format(amt1.value);		     
	}
	if(amt2 != null)
	{
		amountMask.attach(amt2);	
		if(amt2.value == '0')
			amt2.value = '';
			
		amt2.value=amountMask.format(amt2.value); 		     
	}
	if(amt3 != null)
	{
		amountMask.attach(amt3);	
		if(amt3.value == '0')
			amt3.value = '';
			
		amt3.value=amountMask.format(amt3.value);		     
	}
	if(amt4 != null)
	{
		amountMask.attach(amt4);	
		if(amt4.value == '0')
			amt4.value = '';
			
		amt4.value=amountMask.format(amt4.value); 		     
	}
	if(amt5 != null)
	{
		amountMask.attach(amt5);	
		if(amt5.value == '0')
			amt5.value = '';
			
		amt5.value=amountMask.format(amt5.value);		     
	}
	if(amt6 != null)
	{
		amountMask.attach(amt6);	
		if(amt6.value == '0')
			amt6.value = '';
			
		amt6.value=amountMask.format(amt6.value);		     
	}
	if(amt7 != null)
	{
		amountMask.attach(amt7);	
		if(amt7.value == '0')
			amt7.value = '';
			
		amt7.value=amountMask.format(amt7.value);		     
	}
	if(amt8 != null)
	{
		amountMask.attach(amt8);	
		if(amt8.value == '0')
			amt8.value = '';
			
		amt8.value=amountMask.format(amt8.value);	     
	}
	if(amt9 != null)
	{
		amountMask.attach(amt9);	
		if(amt9.value == '0')
			amt9.value = '';
			
		amt9.value=amountMask.format(amt9.value); 		     
	}
	if(amt10 != null)
	{
		amountMask.attach(amt10);	
		if(amt10.value == '0')
			amt10.value = '';
			
		amt10.value=amountMask.format(amt10.value); 		     
	}
	if(amt11 != null)
	{
		amountMask.attach(amt11);	
		if(amt11.value == '0')
			amt11.value = '';
			
		amt11.value=amountMask.format(amt11.value); 		     
	}
}

function amountFormatingForBM(){

	amountMask = new Mask("$#,###", "number");
	
	var amt0 = document.getElementById("TotalAmountOfClientFundsFc");
	if(amt0 != null)
	{
		amt0.value=amountMask.format(amt0.value);	
		amountMask.attach(amt0);     
	}	
}
	

function addCommas( strValue ) {
  	var objRegExp  = new RegExp('(-?[0-9]+)([0-9]{3})');
    //check for match to search criteria
    while(objRegExp.test(strValue)) {
       //replace original string with first group match,
       //a comma, then second group match
       strValue = strValue.replace(objRegExp, '$1,$2');
    }
  	return strValue; 
}

var checkFocus=true;checkEl=null;
function validateEmail(m,fieldname) {
	var strValue = m;
	if(strValue=='')
		return;
	
	//var objRegExp  = /(^[a-z]([a-z_\.]*)@([a-z_\.]*)([.][a-z]{3})$)|(^[a-z]([a-z_\.]*)@([a-z_\.]*)(\.[a-z]{3})(\.[a-z]{2})*$)/i;
	//var objRegExp = /b[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,4};	
	var objRegExp  = /(^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,3})+$)/;
	checkFocus=false;
	if(!strValue.match(objRegExp))
	{
		checkEl=fieldname;
		alert("Email address is not valid");
		
		setTimeout(function(){if(checkEl)checkEl.focus();checkEl.select();},100);
		
	}	
	checkFocus=true;
}

function validateWebAddress(m,fieldname) {
	var strValue = m;
	if(strValue=='')
		return;
		
	var objRegExp  = /^(((ht|f)tp(s?))\:\/\/)?(www.|[a-zA-Z0-9_-]+\.)*[a-zA-Z0-9_-]+(\.[a-z]{2,6}){1,2}$/;	
	checkFocus=false;
	if(!objRegExp.test(strValue))
	{
		checkEl=fieldname;
		alert("Website address is not valid");
		
		setTimeout(function(){if(checkEl)checkEl.focus();checkEl.select();},100);
	}
	checkFocus=true;
}

function validateAlphabet(m) {
	var strValue = m.value;
	if(strValue=='')
		return;
		
	var objRegExp  = /(^([a-zA-Z])+$)/;	
	if(!objRegExp.test(strValue))
	{
		alert("Value must be alphabetic");
		//m.focus();
		
		setFocus(m.name);
		m.focus();
		m.select();
	}
}

function validateCity(m) {
	var strValue = m.value;
	if(strValue=='')
		return;
		
	//(^([a-zA-Z][']?)*([\s])*([&-])?([\s])*([a-zA-Z][']?)*$)/;	
	
	var objRegExp  = /(^([a-zA-Z][']?)*([\s])*([&-]?[\s]*[a-zA-Z][']?)*$)/;	
	if(!objRegExp.test(strValue))
	{
		alert("Data is not valid");
		//m.focus();
		
		setFocus(m.name);
		m.focus();
		m.select();
	}
}



//------------------------------------- end ----------------------------------------
function hideQuickQuote(m){

	if(m.value == 'Y')
	{
		divQuickQuote.style.display = "none";
	}

}

function loadList(){	 
	
	amountMask = new Mask("$#,###", "number");
	
	var amt0 = document.getElementById("ajax_field_Premium");
	if(amt0 != null)
	{
		amt0.value=amountMask.format(amt0.value);	
		amountMask.attach(amt0);     
	}
}



function loadInsured(){	
	phoneMask = new Mask("(###)###-####");
	
	var wrkphone = document.getElementById("InsuredPassword");
	if(wrkphone != null)
	{	
		phoneMask.attach(wrkphone);						
	}

}

function loadBasicInfo1(){	
	
	//alert('loadBasicInfo1'+getRadioValue(document.forms[0].IsFirmsRevenueMorethan25Percent));

	if (getRadioValue(document.forms[0].IsFirmsRevenueMorethan25Percent)=='Y')
		showDivArea(['divAy'], ['divAn']);
	if (getRadioValue(document.forms[0].IsCommissionRecieved)=='Y')
		showDivArea(['divGy'], ['divGn']);
	if (getRadioValue(document.forms[0].IsAnyRegulatoryInquiry)=='Y')
		showDivArea(['divDy4'], ['divDn4']);
	if (getRadioValue(document.forms[0].IsAwarenessOfAnyProfLiability)=='Y')
	{
	//alert('IsAwarenessOfAnyProfLiability');
		openDivArea2();
	}
		
	if (getRadioValue(document.forms[0].IsAwarenessOfAnyProfLiabilitySuitAgainst)=='Y')
		showDivArea(['divAy5'], ['divAn5']); 
	if (getRadioValue(document.forms[0].IsFirmDeclinedOtherthanNonPayment)=='Y')
		showDivArea(['divCy7'], ['divCn7']);
		
	if (getRadioValue(document.forms[0].IsFirmCarryingProfLiabilityIns)=='Y')
	{	
		//alert('hello');
		showDivArea(['divCy'], ['divCn']);				
		//alert('bye');
		if (getRadioValuePriorActDate(document.forms[0].IsPriorActDateFull)=='Y')
		{
			//alert('hello');
			showDivArea(['full'], ['prioractdate']);
		}		
		else if(getRadioValuePriorActDate(document.forms[0].IsPriorActDateFull)=='N')
		{	
			//alert('bye');		
			showDivArea(['prioractdate'], ['full']);								
		}	
		
	}		
		
	pleaseDescribe1('N');
	pleaseDescribe2('N');
	pleaseDescribe3('N');
	pleaseDescribe4('N');
	pleaseDescribe5('N');	
	
	amountFormatingForBasicinfo();	
	
	if(document.getElementById('StateCode').value!=null || document.getElementById("StateCode").value!=''){
	
	if(!document.getElementById('StateCode').disabled)	
		makeExpenseOutside(document.getElementById('StateCode').value);
			
	if(!document.getElementById('StateCode').disabled)	
		makeDefenseOutsideRule(document.getElementById('LimitSequence').value);	
	
	if(!document.getElementById('StateCode').disabled)
		hideShowClaimExpenseField(document.getElementById('StateCode').value);
	}
}
function makeExpenseOutside(value)
{	
	
	//alert('value is ' + value);
	if(value=='VT' || value == 'NM')
	{
	//alert("test1"+value);
		document.getElementById('IsClaimExpensesType').checked = true;
		document.getElementById('IsClaimExpensesType').disabled=true;
		document.getElementById('expenseType').value = 'Y';
		
				
	}else if(value=='AR')
	{
	//alert("test2"+value);
		document.getElementById('IsClaimExpensesType').checked = false;
		document.getElementById('IsClaimExpensesType').disabled=true;
		}
	else
	{
		document.getElementById('IsClaimExpensesType').disabled=false;				
	}
	
	//hideShowClaimExpenseField(value);
	
}
function makeDefenseOutsideRule(value){

	var stateCode = document.forms[0].StateCode.value;
	
	if(stateCode == 'ND' || stateCode == 'AR'  )
	{
	if(value < 11){
		document.getElementById('IsClaimExpensesType').checked = true;
		document.getElementById('IsClaimExpensesType').disabled=false;
		//document.getElementById('IsClaimExpensesType').value='Y';
	}else{
		document.getElementById('IsClaimExpensesType').checked = false;
		document.getElementById('IsClaimExpensesType').disabled=false;
	}
	}
}
function getRadioValuePriorActDate(radioObj)
{
	if(radioObj){
		var radioLength = radioObj.length;
			if(radioLength == undefined)
				if(radioObj.checked)
					return radioObj.value;
				else
					return "NULL";
	
		for(var i = 0; i < radioLength; i++) {
			if(radioObj[i].checked) {
				return radioObj[i].value;
			}	
		}
	}
	
	return "NULL";
}

function loadGeneral(){
	 
	if (getRadioValue(document.forms[0].IsControlClientFunds)=="Y")
	{
		showDivArea(['divCyy'], ['divCnn']);
		if (getRadioValue(document.forms[0].IsControlFundsForBusinessAndEntimentFc)=="Y")	
		{
			showDivArea(['divifyes'], ['divifno']);
		}
		if (getRadioValue(document.forms[0].IsFirmPersonnelBusinessManagerFc)=="Y")
		{
			showDivArea(['divBy'], ['divBn']);
			
			if(document.getElementById("ClientIndustryFc").value=='25')
				document.getElementById("tr_ClientIndustryFcOthers").style.display = 'block';
			else
				document.getElementById("tr_ClientIndustryFcOthers").style.display = 'none';		
		}
		
	}
	
	if(getRadioValue(document.forms[0].IsSuitInstituted)=="Y")
		//showDivArea(['divEyyAA'], ['divEnnAA']);	
	if (getRadioValue(document.forms[0].IsPolicyExcludesCoverage)=="Y")
		showDivArea(['divBy1'], ['divBn1']);
	if (getRadioValue(document.forms[0].IsFirmRenderingServices)=="Y")
		showDivArea(['divEny'], ['divEnn']);	
	if (getRadioValue(document.forms[0].IsServedAsTrustee)=="Y")
		showDivArea(['divFy'], ['divFn']);
	if (getRadioValue(document.forms[0].IsFirmRenderedServiceForAnyClient)=="Y")
		showDivArea(['divGy'], ['divGn']);	
	if(getRadioValue(document.forms[0].IsSuitInstituted)=="Y")
		showDivArea(['div11y'], ['div11n']);
	else
		showDivArea(['div11n'], ['div11y']);
		
	amountFormatingForGeneral();
	
}

function showDivAreaOuter(areas_show, areas_hide){	
	
	showDivArea(areas_show, areas_hide);
		
	if (getRadioValue(document.forms[0].IsControlFundsForBusinessAndEntimentFc)=="Y")	
	{
		showDivArea(['divifyes'], ['divifno']);
	}
	if (getRadioValue(document.forms[0].IsFirmPersonnelBusinessManagerFc)=="Y")
	{
		showDivArea(['divBy'], ['divBn']);
	}
	
}
 
function loadPM(){
	
	if(getRadioValue(document.forms[0].IsReducedNumberOfOwners)=="Y")
		showDivArea(['divBy1'], ['divBn1']);
	if(getRadioValue(document.forms[0].IsNonMonetaryCompensationRecieved)=="Y")
	{
		showDivArea(['divDy1'], ['divDn1']);
		
		if(document.getElementById("ClientIndustryNmcKey").value=='25')
			document.getElementById("tr_ClientIndustryNmcKeyOthers").style.display = 'block';
		else
			document.getElementById("tr_ClientIndustryNmcKeyOthers").style.display = 'none';
			
			
		if(document.getElementById("ServiceIndustryNmcKey").value=='11' || document.getElementById("ServiceIndustryNmcKey").value=='16')
			document.getElementById("tr_ServiceIndustryNmcKeyOthers").style.display = 'block';
		else
			document.getElementById("tr_ServiceIndustryNmcKeyOthers").style.display = 'none';
	}
			
	amountFormatingForPM();
}

function loadAttestation(){
	if(getRadioValue(document.forms[0].IsLastPeerReviewAtt)=="Y")
		showDivArea(['divEy1'], ['divEn1']);
	
	amountFormatingForAttestaion();
	pleaseDescribeOtherAttestation();
}

function loadIFP(){
	if(getRadioValue(document.forms[0].IsContractualRelationShip)=="Y")
		showDivArea(['divDyy'], ['divDnn'])
	if(getRadioValue(document.forms[0].IsAnyFirmAffiliateLicensed)=="Y")
		showDivArea(['divEyy'], ['divEnn']);
	if(getRadioValue(document.forms[0].IsAnyEmployeeBenefitPlan)=="Y")
		showDivArea(['divFyy'], ['divFnn']);
}
function loadBMFC(){
	if(getRadioValue(document.forms[0].IsControlFundsForBusinessAndEntimentFc)=="Y")
		showDivArea(['divifyes'], ['divifno']);
	if(getRadioValue(document.forms[0].IsFirmPersonnelBusinessManagerFc)=="Y")
	{
		showDivArea(['divBy'], ['divBn']);
		
		if(document.getElementById("ClientIndustryFc").value=='25')
			document.getElementById("tr_ClientIndustryFcOthers").style.display = 'block';
		else
			document.getElementById("tr_ClientIndustryFcOthers").style.display = 'none';			
	}
	
	amountMask = new Mask("$#,###", "number");
	
	var amt0 = document.getElementById("TotalAmountOfClientFundsFc");
	if(amt0 != null)
	{
		if(amt0.value !='0'){
			amt0.value=amountMask.format(amt0.value);	
			amountMask.attach(amt0);  
		}
		else
		  amt0.value = '';     
	}
	
	numberMask = new Mask("###", "number");
	
    var person0 = document.getElementById("TotalProfessionalStaffSe");
	var person1 = document.getElementById("TotalSupportStaffSe");
	var person2 = document.getElementById("TotlaNumberOfClientsFc");
	var person3 = document.getElementById("NumberOfClientsFc");
	var person4 = document.getElementById("ProvideNumberOfClientFc");
	var person5 = document.getElementById("NumberOfBeneficiariesTrust");
	var person6 = document.getElementById("NumberOfFirmEmployeesFc");
	
	if(person0 != null)
	{
		numberMask.attach(person0);		
	}
	if(person1 != null)
	{
		numberMask.attach(person1); 
	}
	if(person2 != null)
	{
		numberMask.attach(person2); 
	}
	if(person3 != null)
	{
		numberMask.attach(person3); 
	}
	if(person4 != null)
	{
		numberMask.attach(person4); 
	}
	if(person5 != null)
	{
		numberMask.attach(person5); 
	}
	if(person6 != null)
	{
		numberMask.attach(person6); 
	}				
	
	
}
function loadCS(){
	pleaseDescribeOtherCS();
	amountFormatingForCS();
		
}
function pleaseDescribeOtherCS() {

    var txtBox = document.getElementById("RevenuesFromLastYear_5");

    var txtBox1 = document.getElementById("EstimatedRevenue_5");
    // if((txtBox != null && txtBox.value !='0' && txtBox.value !='$0' && trim(txtBox.value, '') != '') || (txtBox1 != null && txtBox1.value !='0' && txtBox1.value !='$0' && trim(txtBox1.value, '') != ''))
 if((txtBox != null && txtBox.value !='0' && txtBox.value !='$0' && txtBox.value.trim() != '') || (txtBox1 != null && txtBox1.value !='0' && txtBox1.value !='$0' && txtBox1.value.trim() != ''))


    {

    	showDivArea(['divAy'], ['divAn']);      	         	               
    }
    else
    {
        showDivArea(['divAn'], ['divAy']);               
    }
}


function showDivAreaProfIns(areas_show, areas_hide){
	
	/*
	if(document.getElementById("PriorActDatePross") !=  null)
		document.getElementById("PriorActDatePross").value='';
			
	if(document.getElementById("FirmYear") != null )
		document.getElementById("FirmYear").value='';
	*/
			
	if (getRadioValue(document.forms[0].IsPriorActDateFull)=='Y')
	{	
		document.getElementById("error_FirmYear").style.display = 'block';
		//document.getElementById("error_FirmYear").innerHTML = '';
		document.getElementById("error_PriorActDatePross").style.display = 'none';					
	}
	else if (getRadioValue(document.forms[0].IsPriorActDateFull)=='N')
	{	
		document.getElementById("error_FirmYear").style.display = 'none';
		//document.getElementById("error_PriorActDatePross").style.display = 'block';
		document.getElementById("error_PriorActDatePross").innerHTML = '';				
	}			
			
	showDivArea(areas_show, areas_hide);
}

function showDivAreaProLiabilityInsurance(areas_show, areas_hide, id){
	//alert('divarea');
	showDivArea(areas_show, areas_hide);
	
	/*if(document.getElementById('InsuranceCompanyKey').value =='10')
	{
		alert('value1==10');
		document.getElementById('div_header_'+id).style.display = 'block';
		document.getElementById('div_data_'+id).style.display = 'block';
		
		
		if(document.getElementById('eval:AccountantRule.div_header_title_'+id) != null)
			document.getElementById('eval:AccountantRule.div_header_title_'+id).style.display = 'none';
		if(document.getElementById('eval:AccountantRule.div_header_data_'+id) != null)
			document.getElementById('eval:AccountantRule.div_header_data_'+id).style.display = 'none';					
	}
	else
	{
		alert('value1!=10');
		document.getElementById('div_header_'+id).style.display = 'none';
		document.getElementById('div_data_'+id).style.display = 'none';
		document.getElementById(id).value = '';
		document.getElementById('error_'+id).style.display = 'none';		
		
		if(document.getElementById('eval:AccountantRule.div_header_title_'+id) != null)
			document.getElementById('eval:AccountantRule.div_header_title_'+id).style.display = 'none';
		if(document.getElementById('eval:AccountantRule.div_header_data_'+id) != null)
			document.getElementById('eval:AccountantRule.div_header_data_'+id).style.display = 'none';			
	}	*/
}

function showDivAreaInsurance(value,id){
	
	if(value=='10')
	{
		//alert('value1==10');
		document.getElementById('div_header_'+id).style.display = 'block';
		document.getElementById('div_data_'+id).style.display = 'block';
		
		
		if(document.getElementById('eval:AccountantRule.div_header_title_'+id) != null)
			document.getElementById('eval:AccountantRule.div_header_title_'+id).style.display = 'none';
		if(document.getElementById('eval:AccountantRule.div_header_data_'+id) != null)
			document.getElementById('eval:AccountantRule.div_header_data_'+id).style.display = 'none';
					
	}
	else
	{
		//alert('value1!=10');
		document.getElementById('div_header_'+id).style.display = 'none';
		document.getElementById('div_data_'+id).style.display = 'none';
		document.getElementById(id).value = '';
		document.getElementById('error_'+id).style.display = 'none';		
		
		if(document.getElementById('eval:AccountantRule.div_header_title_'+id) != null)
			document.getElementById('eval:AccountantRule.div_header_title_'+id).style.display = 'none';
		if(document.getElementById('eval:AccountantRule.div_header_data_'+id) != null)
			document.getElementById('eval:AccountantRule.div_header_data_'+id).style.display = 'none';
			
	}
}

function showDivAreaPMClient(value,id){
	
	
	if(value=='25')
	{
		document.getElementById('tr_'+id).style.display = 'block';
	}
	else
	{
		document.getElementById('tr_'+id).style.display = 'none';
		document.getElementById(id).value = '';
		document.getElementById('error_'+id).style.display = 'none';				
	}	
}

function showDivAreaPMPrimaryService(value,id){

	if(value=='11' || value=='16')
	{
		document.getElementById('tr_'+id).style.display = 'block';
	}
	else
	{
		document.getElementById('tr_'+id).style.display = 'none';
		document.getElementById(id).value = '';
		document.getElementById('error_'+id).style.display = 'none';
	}
}

function showDivAreaClientAjax(value,id){
	
	if(value=='25')
	{
		document.getElementById('div_header_'+id).style.display = 'block';
		document.getElementById('div_data_'+id).style.display = 'block';
		
		if(document.getElementById('eval:AccountantRule.div_header_title_'+id) != null)
			document.getElementById('eval:AccountantRule.div_header_title_'+id).style.display = 'none';
		if(document.getElementById('eval:AccountantRule.div_header_data_'+id) != null)
			document.getElementById('eval:AccountantRule.div_header_data_'+id).style.display = 'none';			
	}
	else
	{
		document.getElementById('div_header_'+id).style.display = 'none';
		document.getElementById('div_data_'+id).style.display = 'none';
		document.getElementById('ajax_field_'+id).value = '';
		
		if(document.getElementById('tr_ajax_error_'+id) != null)
			document.getElementById('tr_ajax_error_'+id).style.display = 'none';
		
		if(document.getElementById('eval:AccountantRule.div_header_title_'+id) != null)
			document.getElementById('eval:AccountantRule.div_header_title_'+id).style.display = 'none';
		if(document.getElementById('eval:AccountantRule.div_header_data_'+id) != null)
			document.getElementById('eval:AccountantRule.div_header_data_'+id).style.display = 'none';
			
	}
	
}

function showDivAreaPrimaryServiceAjax(value,id){

	if(value=='11' || value=='16')
	{
		document.getElementById('div_header_'+id).style.display = 'block';
		document.getElementById('div_data_'+id).style.display = 'block';
		
		if(document.getElementById('eval:AccountantRule.div_header_title_'+id) != null)
			document.getElementById('eval:AccountantRule.div_header_title_'+id).style.display = 'none';
		if(document.getElementById('eval:AccountantRule.div_header_data_'+id) != null)
			document.getElementById('eval:AccountantRule.div_header_data_'+id).style.display = 'none';	
		
	}
	else
	{
		document.getElementById('div_header_'+id).style.display = 'none';
		document.getElementById('div_data_'+id).style.display = 'none';
		document.getElementById('ajax_field_'+id).value = '';
		
		if(document.getElementById('tr_ajax_error_'+id) != null)
			document.getElementById('tr_ajax_error_'+id).style.display = 'none';
		
		if(document.getElementById('eval:AccountantRule.div_header_title_'+id) != null)
			document.getElementById('eval:AccountantRule.div_header_title_'+id).style.display = 'none';
		if(document.getElementById('eval:AccountantRule.div_header_data_'+id) != null)
			document.getElementById('eval:AccountantRule.div_header_data_'+id).style.display = 'none';
			
	}
}


function showDivAreaMonetoryCompensation(areas_show, areas_hide){
	
	if (getRadioValue(document.forms[0].IsNonMonetaryCompensationRecieved)!='Y')
	{	            
		document.getElementById("CurrentValueNmc").value = '';
		document.getElementById("ClientNameNmc").value = '';
		document.getElementById("ClientIndustryNmcKey").value = '';
		document.getElementById("ClientIndustryNmcKeyOthers").value = '';
		document.getElementById("ServiceIndustryNmcKey").value = '';
		document.getElementById("ServiceIndustryNmcKeyOthers").value = '';
	}
	
	showDivArea(areas_show, areas_hide);
}


function showDivAreaFundControl(areas_show, areas_hide){
	
	if (getRadioValue(document.forms[0].IsFirmPersonnelBusinessManagerFc)!='Y')
	{	              
		document.getElementById("ProvideNumberOfClientFc").value = '';
		document.getElementById("ClientIndustryFc").value = '';
		document.getElementById("ClientIndustryFcOthers").value = '';	
		document.getElementById("tr_ClientIndustryFcOthers").style.display = 'none';	
	}
	
	showDivArea(areas_show, areas_hide);
}

function resetSerachFormWorkQ() {
	
	if (document.getElementById("AccountNamesearch") != null)
		document.getElementById("AccountNamesearch").value = '';
	
	if (document.getElementById("WQAccountEmailSearch") != null)
		document.getElementById("WQAccountEmailSearch").value = '';
	
	if (document.getElementById("WQPolicyEffectiveDate_search") != null)
		document.getElementById("WQPolicyEffectiveDate_search").value = '';
	
	if (document.getElementById("WQPolicyEffectiveDate_searchTo") != null)
		document.getElementById("WQPolicyEffectiveDate_searchTo").value = '';
	
	if (document.getElementById("WQStateCode_search") != null)
		document.getElementById("WQStateCode_search").value = '';
	
	if (document.getElementById("WQStatusKey_search") != null)
		document.getElementById("WQStatusKey_search").value = '';
		
	if (document.getElementById("WQQuoteNumberSearch") != null)
		document.getElementById("WQQuoteNumberSearch").value = '';
	
	if (document.getElementById("WQPolicyNumberSearch") != null)
		document.getElementById("WQPolicyNumberSearch").value = '';
	
	if (document.getElementById("WQNumberOfDays_search") != null)
		document.getElementById("WQNumberOfDays_search").value = '';
	
	if (document.getElementById("WQIsRenewed_search").checked==true )
		document.getElementById("WQIsRenewed_search").checked = false;
	
	document.getElementById("WQIsLostWorkQPolicy_search").checked = true;
	
}

function closeErrorDiv(id)
{
	if(document.getElementById('error_'+id) != null)
		document.getElementById('error_'+id).style.display = 'none';	
}

function showDivAreaStatusFreeform(new_Selected, prev_Selected, quote_Type){	
	
	if(new_Selected=='3')
	{
		
		if(document.getElementById('tbl_Quote') != null)
		{	
			document.getElementById('tbl_Quote').style.display = 'block';			
		}
		//alert('hi');
		document.getElementById('tbl_QuotedToIssuedAccepted').style.display = 'none';
		document.getElementById('tbl_AcceptedToIssued').style.display = 'none';
		document.getElementById('tbl_Reason').style.display = 'none';	
		//alert('bye');			
	}
	else if(new_Selected=='4' || new_Selected=='5' || new_Selected=='8')
	{
		if(document.getElementById('tbl_Reason') != null)
		{
			if(new_Selected == '4'){
				$("select[id=ajax_field_Comment] option[value=relationship]").hide();
				$("select[id=ajax_field_Comment] option[value=noresponse]").hide();
				$("select[id=ajax_field_Comment] option[value=coverage]").hide();
				$("select[id=ajax_field_Comment] option[value=price]").hide();
				$("select[id=ajax_field_Comment] option[value=alreadyrenewed]").hide();
				
				$("select[id=ajax_field_Comment] option[value=aops]").show();
				$("select[id=ajax_field_Comment] option[value=claims_disciplinary]").show();
				$("select[id=ajax_field_Comment] option[value=non_renewed]").show();
				$("select[id=ajax_field_Comment] option[value=practice_management]").show();
				$("select[id=ajax_field_Comment] option[value=too_large]").show();
				$("select[id=ajax_field_Comment] option[value=underwriting_guidelines]").show();
				$("select[id=ajax_field_Comment] option[value=incomplete_application]").show();
			}
			if(new_Selected == '5'){
				
				$("select[id=ajax_field_Comment] option[value=aops]").hide();
				$("select[id=ajax_field_Comment] option[value=claims_disciplinary]").hide();
				$("select[id=ajax_field_Comment] option[value=non_renewed]").hide();
				$("select[id=ajax_field_Comment] option[value=practice_management]").hide();
				$("select[id=ajax_field_Comment] option[value=too_large]").hide();
				$("select[id=ajax_field_Comment] option[value=underwriting_guidelines]").hide();
				$("select[id=ajax_field_Comment] option[value=incomplete_application]").hide();
				
				$("select[id=ajax_field_Comment] option[value=relationship]").show();
				$("select[id=ajax_field_Comment] option[value=noresponse]").show();
				$("select[id=ajax_field_Comment] option[value=coverage]").show();
				$("select[id=ajax_field_Comment] option[value=price]").show();
				$("select[id=ajax_field_Comment] option[value=alreadyrenewed]").show();
			}
			document.getElementById('tbl_Reason').style.display = 'block';
		}
		document.getElementById('tbl_QuotedToIssuedAccepted').style.display = 'none';
		document.getElementById('tbl_Quote').style.display = 'none';
		document.getElementById('tbl_AcceptedToIssued').style.display = 'none';
	}
	else if(new_Selected=='6')
	{
		if(prev_Selected=='3' && quote_Type=='FQ')
		{
			if(document.getElementById('tbl_QuotedToIssuedAccepted') != null)
			{	
				document.getElementById('tbl_QuotedToIssuedAccepted').style.display = 'block';
			}
			if(document.getElementById('tbl_AcceptedToIssued') != null)
			{	
				document.getElementById('tbl_AcceptedToIssued').style.display = 'block';
			}
			
			document.getElementById('tbl_Reason').style.display = 'none';
			document.getElementById('tbl_Quote').style.display = 'none';
		}
		else if(prev_Selected=='7' && quote_Type=='FQ')
		{
			if(document.getElementById('tbl_AcceptedToIssued') != null)
			{	
				document.getElementById('tbl_AcceptedToIssued').style.display = 'block';
			}
			
			document.getElementById('tbl_QuotedToIssuedAccepted').style.display = 'none';
			document.getElementById('tbl_Reason').style.display = 'none';
			document.getElementById('tbl_Quote').style.display = 'none';
		}
	}
	else if(new_Selected=='7')
	{
		if(quote_Type=='FQ')
		{
			if(document.getElementById('tbl_QuotedToIssuedAccepted') != null)
			{	
				document.getElementById('tbl_QuotedToIssuedAccepted').style.display = 'block';
			}
		}	
		
		document.getElementById('tbl_AcceptedToIssued').style.display = 'none';
		document.getElementById('tbl_Reason').style.display = 'none';
		document.getElementById('tbl_Quote').style.display = 'none';	
	}	
	else if(new_Selected=='2')
	{
		document.getElementById('tbl_QuotedToIssuedAccepted').style.display = 'none';
		document.getElementById('tbl_Reason').style.display = 'none';
		document.getElementById('tbl_Quote').style.display = 'none';
		document.getElementById('tbl_AcceptedToIssued').style.display = 'none';
		
	}
	else if(new_Selected=='')
	{
		document.getElementById('tbl_AcceptedToIssued').style.display = 'none';		
		document.getElementById('tbl_QuotedToIssuedAccepted').style.display = 'none';
		document.getElementById('tbl_Reason').style.display = 'none';
		document.getElementById('tbl_Quote').style.display = 'none';
	}
	
	if(document.getElementById('updateStatusWithConfirmation').style.display=='block')
	{
		document.getElementById('updateStatus').style.display='block';
		document.getElementById('updateStatusWithConfirmation').style.display='none';
	}
	
}




//---------------------------------limit Textarea------------------------------------------------------------------------

var alert_title='Input Restriction'; 
function limitTextarea(el,maxLines,maxChar){ 
	if(!el.x){ 
		el.x=uniqueInt(); 
		el.onblur=function(){clearInterval(window['int'+el.x])} 
	} 
	window['int'+el.x]=setInterval(function(){ 
		var lines=el.value.replace(/\r/g,'').split('\n'), 
		char_removed; 
		
	if(maxChar){ 
		i=lines.length; 
		while(i-->0)if(lines[i].length>maxChar){ 
		lines[i]=lines[i].slice(0,maxChar); 
		char_removed=1 
	} 
	if(char_removed)alert('You can not enter more than '+maxChar+' characters') 
	} 
	if(char_removed||lines_removed)el.value=lines.join('\n') 
	},50); 
} 
 
function uniqueInt(){ 
var num,maxNum=100000; 
if(!uniqueInt.a||maxNum<=uniqueInt.a.length)uniqueInt.a=[]; 
do num=Math.ceil(Math.random()*maxNum); 
while(uniqueInt.a.hasMember(num)) 
uniqueInt.a[uniqueInt.a.length]=num; 
return num 
} 
 
Array.prototype.hasMember=function(testItem){ 
var i=this.length; 
while(i-->0)if(testItem==this[i])return 1; 
return 0 
}; 
 
function set_ie_alert(){ 
window.alert=function(msg_str){ 
vb_alert(msg_str); 
} 
}
//------------------------------------------------------------------------------------------------------

function limitText(limitField, limitCount, limitNum) {
	if (limitField.value.length > limitNum) {
		limitField.value = limitField.value.substring(0, limitNum);
	} else {
		limitCount.value = limitNum - limitField.value.length;
	}
}
//onKeyUp="limitText(this.form.limitedtextarea,this.form.countdown,30);"
//------------------------------------------End---------------------------------------------------------------


//------------------------ Trim --------------------------------------------------------
function trim(str, chars) {
	return ltrim(rtrim(str, chars), chars);
}
 
function ltrim(str, chars) {
	chars = chars || "\\s";
	return str.replace(new RegExp("^[" + chars + "]+", "g"), "");
}
 
function rtrim(str, chars) {
	chars = chars || "\\s";
	return str.replace(new RegExp("[" + chars + "]+$", "g"), "");
}


function setEffectiveDate() {
	
	if(document.getElementById("PolicyEffectiveDate1") != null && document.getElementById("PolicyEffectiveDate") != null)
	{
		document.getElementById("PolicyEffectiveDate1").innerHTML = document.getElementById("PolicyEffectiveDate").value;
	}		
}

//-------------- Basic Info Ajax Add Form -------------------------

function loadProfLSuit(){	
	
	if(document.forms[0].TotalClaimExpensesCi == null)
	{
		setTimeout(loadProfLSuit,5);
	}
	else
	{
		loadProfLSuit1();
	}
	
}

function loadProfLSuit1(){	
	
	//attachMMDDYYYYDateFormat(document.forms[0].AllegedErrorCiDate);
	//attachMMDDYYYYDateFormat(document.forms[0].ReportedCiDate);
	//attachMMDDYYYYDateFormat(document.forms[0].ClaimNotifiedCiDate);
	//attachMMDDYYYYDateFormat(document.forms[0].ClosingCiDate);
	attachAmountFormat(document.forms[0].ClaimantLastDemandCi);
	attachAmountFormat(document.forms[0].TotalClaimExpensesCi);		
}

function loadEditProfLSuit(){	
	//alert('java claim'  );
	if(document.forms[0].TotalClaimExpensesCi == null)
	{
		setTimeout(loadEditProfLSuit,5);
	}
	else
	{
		loadEditProfLSuit1();
	}
	
}

function loadEditProfLSuit1(){	
	
	//attachMMDDYYYYDateFormat(document.forms[0].AllegedErrorCiDate);
	//attachMMDDYYYYDateFormat(document.forms[0].ReportedCiDate);
	//attachMMDDYYYYDateFormat(document.forms[0].ClaimNotifiedCiDate);
	//attachMMDDYYYYDateFormat(document.forms[0].ClosingCiDate);
	attachAmountFormat(document.forms[0].ClaimantLastDemandCi);
	attachAmountFormat(document.forms[0].TotalClaimExpensesCi);	
	
	amountMask = new Mask("$#,###", "number");
	document.forms[0].ClaimantLastDemandCi.value=amountMask.format(document.forms[0].ClaimantLastDemandCi.value);
	document.forms[0].TotalClaimExpensesCi.value=amountMask.format(document.forms[0].TotalClaimExpensesCi.value);	
}



function loadOffering(){	
	
	if(document.forms[0].FeesChargedPpo == null)
	{
		setTimeout(loadOffering,5);
	}
	else
	{
		loadOffering1();
	}
	
}

function loadOffering1(){	
	
	attachAmountFormat(document.forms[0].FeesChargedPpo);
}

function loadEditOffering(){	
	
	if(document.forms[0].FeesChargedPpo == null)
	{
		setTimeout(loadEditOffering,5);
	}
	else
	{
		loadEditOffering1();
	}
	
}

function loadEditOffering1(){	
	
	attachAmountFormat(document.forms[0].FeesChargedPpo);	
	
	amountMask = new Mask("$#,###", "number");
	document.forms[0].FeesChargedPpo.value=amountMask.format(document.forms[0].FeesChargedPpo.value);
}

//---------------General Ajax Add Forms -------------------------------
function loadServedTrustee(){	
		
	if(document.forms[0].NumberOfBeneficiariesTrust == null)
	{
		setTimeout(loadServedTrustee,5);
	}
	else
	{
		loadServedTrustee1();
	}
	
}

function loadServedTrustee1(){	
	
	
	//attachMMDDYYYYDateFormat(document.forms[0].StartEngagemenTrusttDate);
	attachPercentageFormat(document.forms[0].NumberOfBeneficiariesTrust);
	attachAmountFormat(document.forms[0].ValueOfAssetsTrust);	
	attachAmountFormat(document.forms[0].AnnualIncomeofAssetsTrust);	
		
}




function loadEditServedTrustee(){	
	
	if(document.forms[0].NumberOfBeneficiariesTrust == null)
	{
		setTimeout(loadEditServedTrustee,5);
	}
	else
	{
		loadEditServedTrustee1();
	}
	
}

function loadEditServedTrustee1(){	
	
	//attachMMDDYYYYDateFormat(document.forms[0].StartEngagemenTrusttDate);
	attachPercentageFormat(document.forms[0].NumberOfBeneficiariesTrust);
	attachAmountFormat(document.forms[0].ValueOfAssetsTrust);	
	attachAmountFormat(document.forms[0].AnnualIncomeofAssetsTrust);
	
	amountMask = new Mask("$#,###", "number");
	document.forms[0].ValueOfAssetsTrust.value=amountMask.format(document.forms[0].ValueOfAssetsTrust.value);
	document.forms[0].AnnualIncomeofAssetsTrust.value=amountMask.format(document.forms[0].AnnualIncomeofAssetsTrust.value);	
}

function loadPracticeFeesSuit()
{
	if(document.forms[0].AmountOfFeesSued == null)
	{
		setTimeout(loadPracticeFeesSuit,5);
	}
	else
	{
		loadPracticeFeesSuit1();
	}
	
}

function loadPracticeFeesSuit1(){	
	
	
	attachAmountFormat(document.forms[0].AmountOfFeesSued);	
		
}

function loadEditPracticeFeesSuit(){	
	
	if(document.forms[0].AmountOfFeesSued == null)
	{
		setTimeout(loadEditPracticeFeesSuit,5);
	}
	else
	{
		loadEditPracticeFeesSuit1();
	}
	
}

function loadEditPracticeFeesSuit1(){	
	
	//attachMMDDYYYYDateFormat(document.forms[0].AllegedErrorCiDate);
	//attachMMDDYYYYDateFormat(document.forms[0].ReportedCiDate);
	//attachMMDDYYYYDateFormat(document.forms[0].ClaimNotifiedCiDate);
	//attachMMDDYYYYDateFormat(document.forms[0].ClosingCiDate);
	attachAmountFormat(document.forms[0].AmountOfFeesSued);
	//attachAmountFormat(document.forms[0].TotalClaimExpensesCi);	
	
	amountMask = new Mask("$#,###", "number");
	document.forms[0].AmountOfFeesSued.value=amountMask.format(document.forms[0].AmountOfFeesSued.value);
	//document.forms[0].TotalClaimExpensesCi.value=amountMask.format(document.forms[0].TotalClaimExpensesCi.value);	
}



function loadPremiumList(){	
	
	if(document.forms[0].Premium == null)
	{
		setTimeout(loadPremiumList,5);
	}
	else
	{
		loadPremiumList1();
	}
	
}

function loadPremiumList1(){	
	
	attachAmountFormat(document.forms[0].Premium);			
}

function limitAmountText(limitField, limitNum) {
	
	var myString = limitField.value;
	if(myString == '')
		return;
	
	var newString = "";
	for (i = 0; i < myString.length; i++) { 
        var ch = myString.substring(i, i+1); 
        if (ch !=','&& ch!='$'){ 
            newString += ch; 
        }
    } 
	if (newString.length > limitNum ) {
		newString = limitField.value.substring(0, limitNum);			
	} 
	
	var tempStr = "";
	for (j = 0; j < newString.length; j++) { 
        var ch = newString.substring(j, j+1); 
        if (ch !='-'){ 
            tempStr += ch; 
        }
        else if(j==0 && ch =='-'){ 
            tempStr += ch; 
        }
    }     
    limitField.value = tempStr;	
}

function checkStatus(id)
{
	if(document.getElementById('ajax_field_StatusKey') != null)
	{
		if(document.getElementById('ajax_field_StatusKey').value == "")
		{
			alert('Select Status');
			return false;
		}
	}
	return true;
}











function checkExpenses()
{
	
	var stateCode = document.forms[0].StateCode.value;
	
	var LimitSequence=document.forms[0].LimitSequence.value;
	
	if(LimitSequence>0)
		{
	if(stateCode=='AR' && LimitSequence<11 )
		document.getElementById('IsClaimExpensesType').checked = true;
		}
	/*if(stateCode='AR'{
			if(LimitSequence<11){
				document.getElementById('IsClaimExpensesType').checked = true;
				
			}*/
}

function hideShowClaimExpenseField(value){
	//alert(value);
	if(value == 'SD'){
		//alert("Not here");
		document.getElementById('expenseTypeOption').style.display = 'none';
	}else{
		//alert('Here');
		document.getElementById('expenseTypeOption').style.display = 'block';
	}
	
}

function hideShowDefenseExpenses1(){
	hideShowClaimExpenseField(document.getElementById('StateCode').value);
}

function showMessageForSD(){
	
	var stateCode = document.forms[0].StateCode.value;
	
	checkExpenses();
	if(stateCode == 'SD'){
		//alert("Here also");
		if(document.getElementById('IsClaimExpensesType').checked){
			//alert("Great");
			document.getElementById('jserror').innerHTML = "South Dakhota does not allow defense expenses to be paid inside the limits of liablity";
		}else{
			document.getElementById('jserror').innerHTML = "";
		}
		
	}
}

function showDivAreaManualPremium(controlId, divId)
{
	if(document.getElementById(divId) != null)
	{	 
		if(document.getElementById(controlId).checked)
		{
			document.getElementById(divId).style.display = 'block';	
			document.getElementById('saveLink').style.display = 'block';
			document.getElementById('getLink').style.display = 'none';
						
			if(document.getElementById('LimitSequence')!=null && document.getElementById('DeductibleSequence')!=null)
			{
				var LimitSequence = document.getElementById('LimitSequence');				
				var limit = LimitSequence.options[LimitSequence.selectedIndex].value; 
				
				var DeductibleSequence = document.getElementById('DeductibleSequence');				
				var deductible = DeductibleSequence.options[DeductibleSequence.selectedIndex].value; 
								
				if(limit =='' || deductible =='')
				{
					document.getElementById('downloadLinkText').style.display = 'block';
					document.getElementById('downloadLink').style.display = 'none';
				}
				else
				{
					document.getElementById('downloadLink').style.display = 'block';
					document.getElementById('downloadLinkText').style.display = 'none';
				}
			}
		}	
		else
		{
			document.getElementById(divId).style.display = 'none';
			document.getElementById('saveLink').style.display = 'none';
			document.getElementById('getLink').style.display = 'block';	
		}
	}
}

function loadQuoteOption()
{
	if(document.getElementById('IsDollarDefense') != null)
	{	 
		if(document.getElementById('IsDollarDefense').checked)
		{
			document.getElementById('saveLink').style.display = 'block';
			document.getElementById('getLink').style.display = 'none';
			
			if(document.getElementById('ManualPremium')!= null)
			{
				document.getElementById('ManualPremium').style.display = 'block';	
				if(document.getElementById('LimitSequence')!=null && document.getElementById('DeductibleSequence')!=null)
				{
					var LimitSequence = document.getElementById('LimitSequence');				
					var limit = LimitSequence.options[LimitSequence.selectedIndex].value; 
					
					var DeductibleSequence = document.getElementById('DeductibleSequence');				
					var deductible = DeductibleSequence.options[DeductibleSequence.selectedIndex].value; 
									
					if(limit =='' || deductible =='')
					{
						document.getElementById('downloadLinkText').style.display = 'block';
						document.getElementById('downloadLink').style.display = 'none';
					}
					else
					{
						document.getElementById('downloadLink').style.display = 'block';
						document.getElementById('downloadLinkText').style.display = 'none';
					}
				}
			}
		}
		else
		{
			document.getElementById('saveLink').style.display = 'none';
			document.getElementById('getLink').style.display = 'block';	
		}		
	}
	
	hideShowDefenseExpenses1();
	
}

function showHideRatingLink()
{
	//alert(document.getElementById('ManualPremium').style.display);
	if(document.getElementById('IsDollarDefense') != null)
	{	 
		if(document.getElementById('IsDollarDefense').checked)
		{
			if(document.getElementById('LimitSequence')!=null && document.getElementById('DeductibleSequence')!=null)
			{
				//alert(document.getElementById('ManualPremium').style.display);
				if(document.getElementById('ManualPremium').style.display == 'block')
				{
					var LimitSequence = document.getElementById('LimitSequence');				
					var limit = LimitSequence.options[LimitSequence.selectedIndex].value; 
					
					var DeductibleSequence = document.getElementById('DeductibleSequence');				
					var deductible = DeductibleSequence.options[DeductibleSequence.selectedIndex].value; 
									
					if(limit =='' || deductible =='')
					{
						document.getElementById('downloadLinkText').style.display = 'block';
						document.getElementById('downloadLink').style.display = 'none';
					}
					else
					{
						document.getElementById('downloadLink').style.display = 'block';
						document.getElementById('downloadLinkText').style.display = 'none';
					}
				}				
			}
			
		}				
	}
}

function loadQuickQuoteInsured(){
	
	amountMask = new Mask("$#,###", "number");
	var amt = document.getElementById("Amount");
	
	if(amt != null)
	{
		amountMask.attach(amt);	
		if(amt.value == '0')
			amt.value = '';
						
		amt.value=amountMask.format(amt.value);				     
	}
	
	for (i = 0; i < 3; i++) {
		amt = document.getElementById("Amount_" + i);
		
		if(amt != null)
		{
			amountMask.attach(amt);	
			if(amt.value == '0')
				amt.value = '';
							
			amt.value=amountMask.format(amt.value);				     
		}
	}
	
	for (i = 0; i < 6; i++) {
		amt = document.getElementById("RevenuesFromLastYear_" + i);
		
		if(amt != null)
		{
			amountMask.attach(amt);	
			if(amt.value == '0')
				amt.value = '';
							
			amt.value=amountMask.format(amt.value);				     
		}
	}
	
	for (i = 0; i < 6; i++) {
		amt = document.getElementById("EstimatedRevenue_" + i);
		
		if(amt != null)
		{
			amountMask.attach(amt);	
			if(amt.value == '0')
				amt.value = '';
							
			amt.value=amountMask.format(amt.value);				     
		}
	}
	
	mmddyyyyMask = new Mask("mm/dd/yyyy", "date");	
	var YearEndDate_3 = document.getElementById("PolicyEffectiveDate");
	if(YearEndDate_3 != null)
	{	
		//mmddyyyyMask.attach(YearEndDate_3);
	}
	
	numberMask = new Mask("###", "number");
	for (i = 1; i < 25; i++) { 
        var aop = document.getElementById("AOP_Percentage_"+i);
        if(aop != null)
        {
        	numberMask.attach(aop);
        	if(aop.value =='0')			
			aop.value = ''; 
        }
    }  
    
    pleaseDescribe5('N');
    
    /*
    phoneMask = new Mask("(###)###-####");
	
	var wrkphone = document.getElementById("WorkPhone");
	var wrkphoneext = document.getElementById("WorkExt");	
	if(wrkphone != null)
	{	
		wrkphone.value=phoneMask.format(wrkphone.value);
		phoneMask.attach(wrkphone);						
	}
	
	if(wrkphoneext != null)
	{
		numberMask.attach(wrkphoneext); 
	}	
	*/      
}


function loadQuickQuoteOptionList(){
	
	phoneMask = new Mask("(###)###-####");
	
	var wrkphone = document.getElementById("WorkPhone");
	if(wrkphone != null)
	{	
		wrkphone.value=phoneMask.format(wrkphone.value);
		phoneMask.attach(wrkphone);						
	}	      
}


function pleaseDescribeOtherAttestation() {
	
    var txtBox = document.getElementById("AuditClients_25");
    var txtBox1 = document.getElementById("ClientsAssetsOver_25");
    var txtBox2 = document.getElementById("NetLossForLastFYE_25");
   // alert('Test');
    
    
   /* if(txtBox1.value.trim()==null)
    	{
    	txtBox1.value='';
    	
    	}*/
    
    
    if((txtBox != null && txtBox.value !='0' && txtBox.value !='$0' && txtBox.value.trim() != '') || (txtBox1 != null && txtBox1.value !='0' && txtBox1.value !='$0' && txtBox1.value.trim() != '') || (txtBox2 != null && txtBox2.value !='0' && txtBox2.value !='$0' && txtBox2.value.trim() != ''))

/*    if((txtBox != null && txtBox.value !='0' && txtBox.value !='$0' && trim(txtBox.value, '') != '') || (txtBox1 != null && txtBox1.value !='0' && txtBox1.value !='$0' && trim(txtBox1.value, '') != '') || (txtBox2 != null && txtBox2.value !='0' && txtBox2.value !='$0' && trim(txtBox2.value, '') != ''))
*/    {
    	//alert('Hello');
    	showDivArea(['divAy'], ['divAn']);      	         	               
    }
    else
    {	
    	//alert('Bye');
        showDivArea(['divAn'], ['divAy']);               
    }
}


function loadPayment()
{
	if (getRadioValue(document.forms[0].PayFull) == 'payfull') {
	    showDivArea(['DivPayFull'], ['DivFin']);
	    checkUnCheckPaymentDiv(['PayFull'], ['PaymentModeF']);
	}
	if (getRadioValue(document.forms[0].PaymentMode) == 'finance') {
	    showDivArea(['DivFin'], ['DivPayFull', 'DivPaypal', 'DivEFT', 'DivACH']);
	    checkUnCheckPaymentDiv(['PaymentModeF'], ['PayFull']);
	}
	if (getRadioValue(document.forms[0].PaymentMode) == 'paypal') {
	    showDivArea(['DivPayFull'], ['DivFin']);
	    showDivArea(['DivPaypal'], ['DivFin', 'DivEFT', 'DivACH']);
	    checkUnCheckPaymentDiv(['PayFull'], ['PaymentModeF']);
	}
	if (getRadioValue(document.forms[0].PaymentMode) == 'eft') {
	    showDivArea(['DivEFT'], ['DivPaypal', 'DivFin', 'DivACH']);
	}
	if (getRadioValue(document.forms[0].PaymentMode) == 'ach') {
	    showDivArea(['DivPayFull'], ['DivFin']);
	    showDivArea(['DivACH'], ['DivPaypal', 'DivFin', 'DivEFT']);
	    checkUnCheckPaymentDiv(['PayFull'], ['PaymentModeF']);
	}
}

function applyDateFormat(d,e){
	var val = d.value;
	var flag = false;
	var len = d.value.length;
	if(len != 10){
		for(var i=0; i<len;i++){
			var c = d.value.charAt(i);
			if(c=='/'){
				d.value = d.value.replace(c,"");
				//alert(i);
				flag = true;	
			}	
		}
	}
	if(flag){
		return;
	}
	//alert(val);
	var str = "0123456789";
	var ch = val.charAt(len-1);
	//alert(val + ' ' + len + ' ' + ch + str.indexOf(ch));
	if(str.indexOf(ch)>=0){
		if(len == 2 || len == 5){
		//	d.value = d.value + '/';
		}
		//return true;
	}else{
		d.value = d.value.replace(ch,"");
		return;
	}
	//alert(len);
	if(len==8 && val.indexOf("/")<0){
		var mm = d.value.substr(0,2);
		var dd = d.value.substr(2,2);
		var yy = d.value.substr(4,4);
		//alert(mm + ' ' + dd + ' ' + yy);
		d.value = mm + '/' + dd + '/' + yy;
		//alert(d.value);
	}
}

function applyDateFormatMMYYYY(d){
	var val = d.value;
	var flag = false;
	var len = d.value.length;
	if(len != 7){
	for(var i=0; i<len;i++){
		var c = d.value.charAt(i);
		if(c=='/'){
			d.value = d.value.replace(c,"");
			//alert(i);
			flag = true;	
		}	
	}}
	if(flag){
		return;
	}
	//alert(val);
	
	var str = "0123456789";
	var ch = val.charAt(len-1);
	//alert(val + ' ' + len + ' ' + ch + str.indexOf(ch));
	if(str.indexOf(ch)>=0){
		if(len == 2){
		//	d.value = d.value + '/';
		}
		//return true;
	}else{
		d.value = d.value.replace(ch,"");
		return;
	}
	
	if(len==6 && val.indexOf("/")<0){
		var mm = d.value.substr(0,2);
		var dd = d.value.substr(2,4);
		//alert(mm + ' ' + dd);
		d.value = mm + '/' + dd;
		//alert(d.value);
	}
}



//added by monika
function showReportDiv(){
	var val = getRadioValue(document.getElementById("SearchType"));	
	
	
	if(val=='divA'){
		document.getElementById("divA").style.display= 'block';	
		document.getElementById("divB").style.display= 'none';	
		document.getElementById("divC").style.display= 'none';	
		document.getElementById("divD").style.display= 'none';
		document.getElementById("divE").style.display= 'none';
	    document.getElementById("divF").style.display= 'none';		
		
	}else if(val=='divB'){
		document.getElementById("divA").style.display= 'none';	
		document.getElementById("divB").style.display= 'block';	
		document.getElementById("divC").style.display= 'none';	
		document.getElementById("divD").style.display= 'none';
	    document.getElementById("divE").style.display= 'none';
	    document.getElementById("divF").style.display= 'none';		
		
	}else if(val=='divC'){	
		document.getElementById("divA").style.display= 'none';	
		document.getElementById("divB").style.display= 'none';	
		document.getElementById("divC").style.display= 'block';	
		document.getElementById("divD").style.display= 'none';
		document.getElementById("divE").style.display= 'none';
	    document.getElementById("divF").style.display= 'none';		
		
	}else if(val=='divD'){	
		document.getElementById("divA").style.display= 'none';	
		document.getElementById("divB").style.display= 'none';	
		document.getElementById("divC").style.display= 'none';	
		document.getElementById("divD").style.display= 'block';
		document.getElementById("divE").style.display= 'none';
	    document.getElementById("divF").style.display= 'none';		
		
	}else if(val=='divE'){	
		document.getElementById("divA").style.display= 'none';	
		document.getElementById("divB").style.display= 'none';	
		document.getElementById("divC").style.display= 'none';	
		document.getElementById("divD").style.display= 'none';
		document.getElementById("divE").style.display= 'block';	
	    document.getElementById("divF").style.display= 'none';		
			
	}else if(val=='divF'){	
		document.getElementById("divA").style.display= 'none';	
		document.getElementById("divB").style.display= 'none';	
		document.getElementById("divC").style.display= 'none';	
		document.getElementById("divD").style.display= 'none';
		document.getElementById("divE").style.display= 'none';	
	   document.getElementById("divF").style.display= 'block';		
			
	}
	
}


	function SetFocusOnCounty(){
		alert('IsCountyAjaxCall1');
     	var IsCountyAjaxCall = document.getElementById("IsCountyAjaxCall").value;
     	alert('IsCountyAjaxCall2');
     	if(IsCountyAjaxCall == 'Y')
     	{
    		alert('IsCountyAjaxCall3');
    		setFocus('StateCode');
    	}
	}
	
	function loadRenewBasicInfo(){
		if (getRadioValue(document.forms[0].IsControlClientFunds)=="Y")
		{
			showDivArea(['divCyy'], ['divCnn']);
		}
		if (getRadioValue(document.forms[0].IsControlFundsForBusinessAndEntimentFc)=="Y")	
		{
			showDivArea(['divifyes'], ['divifno']);
		}
		if (getRadioValue(document.forms[0].IsFirmPersonnelBusinessManagerFc)=="Y")
		{
			showDivArea(['divBy'], ['divBn']);
			
			if(document.getElementById("ClientIndustryFc").value=='25')
				document.getElementById("tr_ClientIndustryFcOthers").style.display = 'block';
			else
				document.getElementById("tr_ClientIndustryFcOthers").style.display = 'none';		
		}
		
	
	if (getRadioValue(document.forms[0].IsServedAsTrustee)=="Y")
		showDivArea(['divFy'], ['divFn']);
		
	if (getRadioValue(document.forms[0].IsFirmRenderedServiceForAnyClient)=="Y")
		showDivArea(['divGy'], ['divGn']);	
	
	
	if(getRadioValue(document.forms[0].IsSuitInstituted)=="Y")
		showDivArea(['div11y'], ['div11n']);
	else
		showDivArea(['div11n'], ['div11y']);
		
	if (getRadioValue(document.forms[0].IsAnyRegulatoryInquiry)=='Y')
		showDivArea(['divDy4'], ['divDn4']);
		
	if (getRadioValue(document.forms[0].IsAwarenessOfAnyProfLiability)=='Y')
	{
	
		openDivArea2();
	}
		
	if (getRadioValue(document.forms[0].IsAwarenessOfAnyProfLiabilitySuitAgainst)=='Y')
		showDivArea(['divAy5'], ['divAn5']); 
		
	if (getRadioValue(document.forms[0].IsFirmsRevenueMorethan25Percent)=='Y')
		showDivArea(['divAy'], ['divAn']);
		
			
		amountFormatingForBasicinfo();
		
		pleaseDescribe1('N');
		pleaseDescribe2('N');
		pleaseDescribe3('N');
		pleaseDescribe4('N');
		pleaseDescribe5('N');
		
		amountMask = new Mask("$#,###", "number");
	
	var amt0 = document.getElementById("Amount");
	
	//alert('Great');
	if(amt0 != null )
	{
		amountMask.attach(amt0);	
		if(amt0.value == '0')
			amt0.value = '';
			
		amt0.value=amountMask.format(amt0.value);	
			
	}
}

function showHideCommentDiv()
{
	
	
	if(document.forms[0].IsRenewalReview == null)
	{
		setTimeout(showHideCommentDiv,10);
	}
	else
	{
		showReviewCommentsBlock();
	}
	

}

	function showReviewCommentsBlock()
	{		
		
		
		if (getRadioValue(document.forms[0].IsRenewalReview)=='Y')
		{			
			showDivArea(['divRenewalReviewY'], ['divRenewalReviewN']);		
		}else{
			
			showDivArea(['divRenewalReviewN'], ['divRenewalReviewY']);
		}
	}
	
function fillDefaultPolicyExpirationDate()
{
	if(document.forms[0].PolicyEffectiveDate != null)
	{
		var policyeffectivedate = document.getElementById('PolicyEffectiveDate');
		alert(policyeffectivedate.value);
		//alert(policyeffectivedate.length());
		
		document.getElementById('PolicyExpirationDate').innerHTML = policyeffectivedate ;
		
		
	}
}

function makeDefenseExpenseOutsideOnQuoteOption(){
	//alert('-- > ' + document.getElementById('StateCode').value);
	/*if(document.getElementById('StateCode').value!=null){*/
	if(!document.getElementById('StateCode').disabled)	
		makeExpenseOutside(document.getElementById('StateCode').value);

	if(!document.getElementById('StateCode').disabled)	
		makeDefenseOutsideRule(document.getElementById('LimitSequence').value);
	//}
}

function confirmDelete(){
	
	var x = confirm("Are you sure you want to delete this quote ? ");
	return x ;
	
}


var toggle = true;
function toggleDivDisplay(ajaxdivids) {
            //alert(ajaxdivids);
      var divids = ajaxdivids.split(",");
      for (var i = 0; i < divids.length; i++) {
                  //alert(divids[i]);
            if (document.getElementById(divids[i]) != null) {
                  if (toggle) {
                        if (document.getElementById(divids[i]).style.display == "block") {
                              document.getElementById(divids[i]).style.display = "none";
                        }
                  }
                  toggle = true;
            }
      }
}
function nottoggleDivDisplay(element, id, divid) {
      if (document.getElementById(divid) != null) {
            if (element.id == id) {
                        //alert('hi');
                  if (document.getElementById(divid).style.display == "block") {
                        document.getElementById(divid).style.display = "none";
                        toggle = false;
                  }
            }
      }
}

function showSpecialIssuanceDiv(){	
	
	if(document.getElementById("IsSpecialIssuance") != null){		
		if(getRadioValue(document.forms[0].IsSpecialIssuance) == 'Y'){	
			document.getElementById("SpecialIssuanceCommentsDiv").style.display = "block";		
		}else{	
			document.getElementById("SpecialIssuanceCommentsDiv").style.display = "none";	
		}		
	}

	
}



function selectallcheckbox(){
	//alert("java");
	if(document.forms[0].selectall.checked){
		
		for(var i = 0 ; i < 100 ; i++){			
			if(document.getElementById("PolicyKey_"+i) == null)
				break;				
			if(document.getElementById("PolicyKey_"+i) != null ) {
				document.getElementById("PolicyKey_"+i).checked = true;
			}	
		
		}
		
	}
	
	if(!document.forms[0].selectall.checked){		
		for(var i = 0 ; i < 100 ; i++){			
			if(document.getElementById("PolicyKey_"+i) == null)
				break;				
			if(document.getElementById("PolicyKey_"+i) != null ) {
				document.getElementById("PolicyKey_"+i).checked = false;
			}
		
		}
		
	}
}

function getSelectedPolicyKey(){
	var offrsikListSize=document.getElementById('offrsikListSize').value;
	var strPolicyKey = "";
	for(var i = 0 ; i < offrsikListSize ; i++){			
		if(document.getElementById("PolicyKey_"+i) == null)
			break;		
		if(document.getElementById("PolicyKey_"+i).checked != null && document.getElementById("PolicyKey_"+i).checked) {			
			strPolicyKey = strPolicyKey.concat(document.getElementById("PolicyKey_"+i).value).concat(",");
		}		
	}	
	document.getElementById("strPolicyKey").value = strPolicyKey;
	//alert(strPolicyKey);
}

function showDocTitle(){
	try
	{
		var val = document.getElementById("SearchHidden").value;
		if(val == 'other'){
		showotherdiv(val, 'otherdiv');
		document.getElementById("SearchHidden").value = "";
	}
	else{
		document.getElementById("otherdiv").style.display="none";
		document.getElementById("SearchHidden").value = "";
	}
	}
	catch(e)
	{}
	try
	{
		var isBrokerageDocument=null,isBrokerPolicy=null;
			if(document.getElementById('isBrokerageDocument')!=null)
				isBrokerageDocument=document.getElementById('isBrokerageDocument').value;
			if(document.getElementById('isBrokerPolicy')!=null)
				isBrokerPolicy=document.getElementById('isBrokerPolicy').value;
			if(isBrokerageDocument=='Y' )
					document.getElementById('isBrokerageDoc').checked=true;
			else if(isBrokerPolicy=='Y')
				document.getElementById('isBrokerageDoc').checked=true;
			else
				document.getElementById('isBrokerageDoc').checked=false;
			
	}
	catch(e)
	{}
}


function showotherdiv(val, divid){
	//alert(val);
	//document.getElementById("test").value = val;
	if(val == 'other'){
		document.getElementById(divid).style.display="block";
		document.getElementById("SearchHidden").value = "other";
	}else{
		document.getElementById(divid).style.display="none";
		document.getElementById("SearchHidden").value = document.getElementById("SearchType").value;
	}
}
function getRadioButtonValue(inputControlName, outputControlName)
{

	var control = document.getElementsByName(inputControlName);
	var clearStatus;
	if (control) {
		if (control.length) {

			for ( var i = 0; i < control.length; i++) {
				if (control[i].checked) {
					clearStatus = control[i].value;
					//return rad_val;
				}
				/*if(i == ((control.length)-1)){
					alert("none is selected");
					//return null;
				}*/
			}
		} else {
			clearStatus=null;
			//return control.value;
		}
	}
	
	document.getElementById(outputControlName).value = clearStatus;
}
function getPricingApproval(controlName) {

	var control = document.getElementsByName(controlName);
	var pricingClearStatus;
	if (control) {
		if (control.length) {

			for ( var i = 0; i < control.length; i++) {
				if (control[i].checked) {
					pricingClearStatus = control[i].value;
					}

			}
		} else {
			pricingClearStatus = null;

		}
	}
	//alert(pricingClearStatus);
	document.getElementById("ajax_field_pricingClearStatus").value = pricingClearStatus;
	

}
function getcheckboxes() {
    var node_list = document.getElementsByTagName('input');
    var checkboxes = [];
    for (var i = 0; i < node_list.length; i++) 
    {
        var node = node_list[i];
        if (node.getAttribute('type') == 'checkbox') 
    {
            checkboxes.push(node);
        }
    } 
    return checkboxes;
}
function checkall(source) {
   checkboxes = getcheckboxes();
  for(var i=0, n=checkboxes.length;i<n;i++) 
  {
    checkboxes[i].checked = source.checked;
  }
}





function setProfileDropdownData()
{
	//alert('hello1');
	showSelectedFirstReviewer('StateCodefilter','StateCode_search');
	//showSelectedFirstReviewer('ProducerCodefilter','ajax_field_ProducerCode');
	//showSelectedFirstReviewer('FirstReviewerfilter','ajax_field_EligibleFirstReviewer');
	
	showSelectedFirstReviewer('ProducerCodefilter','ProducerCode_search');
	showSelectedFirstReviewer('EligibleFirstReviewerfilter','EligibleFirstReviewer_search');
	showSelectedFirstReviewer('CompanyKeyfilter','CompanyKey_search');
}
function showSelectedFirstReviewer(dstID,field)
{
	//alert('hello');
	//var fd = field.id;
	var fd = field;
	var operatingDivisionIds='';
    var operatingDivisionNames='';
    // debugger;
    var selected=[];
    $('#'+fd+' :selected').each(function(i, sel){
   	 	
           // var operatingDivisionId=$(sel).val();
           //foo[i] = $(selected).text(); 
         
         if(operatingDivisionIds ==''){
            operatingDivisionIds=$(sel).val();
            operatingDivisionNames= $(sel).text();
         }
         else{
            operatingDivisionIds=operatingDivisionIds+','+$(sel).val();
            operatingDivisionNames=operatingDivisionNames+','+$(sel).text();
         }
        
        });
    	
   
    if(document.getElementById(dstID)!=null)
        document.getElementById(dstID).value= operatingDivisionIds;
}
function loadProfile()
{
	//alert('loadedProfile');
	try
	{
		if(document.getElementById("StateCodefilter")!=null)
		{
			values=document.getElementById("StateCodefilter").value;
			setSelectedIndexProfile(document.getElementById('StateCode_search'),values);
		}
	}catch(err)
	{}
	try
	{
		if(document.getElementById("ProducerCodefilter")!=null)
		{
			values=document.getElementById("ProducerCodefilter").value;
			//alert(values);
			//setSelectedIndexProfile(document.getElementById('ajax_field_ProducerCode'),values);
			setSelectedIndexProfile(document.getElementById('ProducerCode_search'),values);
		}
	}catch(err)
	{}
	try
		{
		if(document.getElementById("EligibleFirstReviewerfilter")!=null)
		{
			values=document.getElementById("EligibleFirstReviewerfilter").value;
			//alert(values);
			//setSelectedIndexProfile(document.getElementById('ajax_field_EligibleFirstReviewer'),values);
			setSelectedIndexProfile(document.getElementById('EligibleFirstReviewer_search'),values);
		}
	}catch(err)
	{}
	try
	{
		if(document.getElementById("CompanyKeyfilter")!=null)
		{
			values=document.getElementById("CompanyKeyfilter").value;
			setSelectedIndexProfile(document.getElementById('CompanyKey_search'),values);
		}
	}catch(err)
	{}
}

function setSelectedIndexProfile(s, v) {
	//alert(v+"setSelectedIndexProfile");
	if($.trim(v) != ''){
		var ss = v.split(",");
		for ( var j in ss) {
			for ( var i = 0; i < s.options.length; i++) {
	
				if (s.options[i].value == ss[j]) {				
					s.options[i].selected = true;
				}
				else if(s.options[i].value=="")
				{
					s.options[i].selected = false;
				}
			}
		}
	}
	else
	{
		s.options[0].selected = true;
	}
}


/// indiction in jack

function setAccountType2(value){
//	alert(sessionStorage);
	sessionStorage.accounttype=value;
	//alert('setting : ' + sessionStorage.accounttype);	
}

function fillIndicationSequence(id, name)
{
	var formObject = document.forms[0];
 	var IsQuoteSelected= "";
 	//alert(id + '  ' + name);
	  for (var i=0; i<formObject.length; i++)
	  {	
	    var n = formObject.elements[i].name;
	    if (n.indexOf("IsThisOptionFinalised_") > -1)            
	    {
	    	if (formObject.elements[i].checked && n != name)
	    	{
	    		//alert(n);
	    		document.getElementById(n).checked = false;
	    	}
	   	}
	  }
	  
	  var index = name.substring(name.indexOf("_") + 1, name.length);
  	  document.getElementById(id).value = index;
}

function setCSSForAccountType2(){
	//alert('setCSSForAccountType2 : ' + sessionStorage.accounttype);	
	//alert(document.getElementById('accountstyle').getAttribute('href'));
	//alert('getting : ' + sessionStorage.accounttype)
	if(sessionStorage.accounttype=='aop_list'){
	//alert(sessionStorage.accounttype);
		if(document.getElementById('aop_search') != null){
			document.getElementById("aop_search").style.display = "none";
		}
		if(document.getElementById('aop_list') != null){
			document.getElementById("aop_list").style.display = "block";
		}
//		if(document.getElementById('calculateAndSave_btn') != null){
//            document.getElementById("calculateAndSave_btn").focus();
//		}
	} else if(sessionStorage.accounttype=='aop_search'){
		//alert(sessionStorage.accounttype);
		if(document.getElementById('aop_search') != null){
			document.getElementById("aop_search").style.display = "block";
		}
		if(document.getElementById('aop_list') != null){
			document.getElementById("aop_list").style.display = "none";
		}
		$(document).ready(function() {	
			$("#aop_search_btn").addClass("active");
			$("#aop_list_btn").removeClass("active");
		});
		if(document.getElementById('AccountName') != null){
            document.getElementById("AccountName").focus();
		}
	}
	else{
			$(document).ready(function(){
				//$("#rowSelect").removeClass("selected_color");
			});

			
	}							
}

function setAopPercentageKey(){
	try{
		var indicationAopLength = $("#indicationAopValues").find("tbody").length;
		
		for(var aopCount = 1; aopCount < indicationAopLength; aopCount++){
			var aopObj = $("#indicationAopValues").find("tbody:eq(" + aopCount + ")").find("input[id=AOP_Percentage_" + (aopCount - 1) + "]");
			var aopKeyValue = $("#indicationAopValues").find("tbody:eq(" + aopCount + ")").find("input[id=AOPKey_" + (aopCount - 1) + "]").val();
			var aopKeyPercentageValue = $("#indicationAopValues").find("tbody:eq(" + aopCount + ")").find("input[id=PercentageValue_" + (aopCount - 1) + "]").val();
			aopObj.attr("id", "AOP_Percentage_" + aopKeyValue);
			aopObj.attr("name", "AOP_Percentage_" + aopKeyValue);
			aopObj.attr("value",aopKeyPercentageValue);
		}
	}catch(e){
		alert(e)
	}
}

function formPreviewEndorsement(){
	var url = window.location.href;
	
	url = url.substring(0 , url.indexOf("AccountantIns/") + 14);
	
	url += "Endorsement.do?User=Agent&role_id=3&AccountEmail=cbarndollar@protexure.com&divHideShow=&inet_context=";
	url += "&inet_method=reviewInsurenceCertificate&inet_page=certificateInsurance&dynaKeys=&dynaValues=";
	url += "&ctxmenuparam=&dateFormat=&dateFormatTS=&quickquoteoptionlist_pageFieldsList=";
	
	url += "&CertificateHolderName="+$("#ajax_field_CertificateHolderName").val();
	url += "&CertificateHolderAddress="+$("#ajax_field_CertificateHolderAddress").val();
	url += "&CertificateHolderAddress2="+$("#ajax_field_CertificateHolderAddress2").val();
	url += "&CertificateHolderState="+$("#ajax_field_CertificateHolderState").val();
	url += "&CertificateHolderCity="+$("#ajax_field_CertificateHolderCity").val();
	url += "&CertificateHolderZip="+$("#ajax_field_CertificateHolderZip").val();
	url += "&CertificateRequestedDate="+$("#ajax_field_CertificateRequestedDate").val();
//	url += "&AOP_Percentage_7="+$("#AOP_Percentage_7").val();
//	url += "&AOP_Percentage_8="+$("#AOP_Percentage_8").val();
//	url += "&AccountName="+$("#AccountName").val();
//	url += "&IsLoginMailSent="+$("#IsLoginMailSent").val();
	url += "&jsessionid="+$("#jsessionid").val();
	url += "&PolicyKey="+$("#PolicyKey").val();
//	url += "&QQRMLink="+$("#QQRMLink").val();
//	url += "&QuoteNumber="+$("#QuoteNumber").val();
	
//	alert(url)
	var params = ['height='+screen.height,'width='+screen.width,'scrollbars=yes'].join(',');
	var win = window.open(url, 'reviewInsurenceCertificate', params);
	win.moveTo(0,0);
	win.focus();
//	win.print();
}

function loadCertificateInsurance() {
    zipMask = new Mask("#####-####");
    var zip = document.getElementById("ajax_field_CertificateHolderZip");
    if (zip != null) {
          zip.value = zipMask.format(zip.value);
          zipMask.attach(zip);
    }
    var zip = document.getElementById("ajax_field_nameAddressEndorsementZipCode");
    if (zip != null) {
          zip.value = zipMask.format(zip.value);
          zipMask.attach(zip);
    }
    
}
function showHidePremiumCalulator(id)
{
	var y=document.getElementById('ajax_field_TypeOfEndorsement').value;
	var  isManualPremiumPolicy=document.getElementById('isManualPremiumPolicy').value;
 	
 //alert(isManualPremiumPolicy);
	if(y=='priorActEndorsement')
	{
		
	var x=document.getElementById(id).checked;
	
	if(x!=true && isManualPremiumPolicy!='Y')
	{
		document.getElementById("buttonsDiv").style.display = "block";
		document.getElementById("ajax_field_premiumCalulated").value = 'Y';
		document.getElementById("ratingDiv").style.display = "block";
		
	}
	if(x==true || isManualPremiumPolicy=='Y')
	{
		document.getElementById("buttonsDiv").style.display = "none";
		document.getElementById("ajax_field_premiumCalulated").value = 'N';
		document.getElementById("ratingDiv").style.display= "none";
		if(isManualPremiumPolicy=='Y')
			document.getElementById("errorDivision").style.display = "block";
		else
			document.getElementById("errorDivision").style.display = "none";
	}
	
	}
	
	if(y=='policyExtensionEndorsement')
		document.getElementById("buttonsDiv").style.display = "block";
		
	
	/*alert(document.getElementById("ajax_field_premiumCalulated").value);*/
		
}
function displayEmailMessage(id)
{
	
	var val=document.getElementById(id).value;
	if(val==null || val=='')
		closePopUp('popupsave3');
	
}
function showHideNameChangeEndorsement(id)
{
	var value=document.getElementById(id).value;
	var  isManualPremiumPolicy=document.getElementById('isManualPremiumPolicy').value;
	if (value=='nameChangeEndorsement') 
	{
		document.getElementById("div1").style.display = "block";
		document.getElementById("buttonsDiv").style.display = "none";
		document.getElementById("div2").style.display = "none";
		document.getElementById("div3").style.display = "none";
		document.getElementById("ratingDiv").style.display = "none";
		document.getElementById("errorDivision").style.display = "none";
	}
	if(value=='priorActEndorsement')
	{
	
		if(isManualPremiumPolicy!='Y')
			{
		document.getElementById("div2").style.display = "block";
		document.getElementById("buttonsDiv").style.display = "block";
		document.getElementById("div1").style.display = "none";
		document.getElementById("div3").style.display = "none";
		document.getElementById("ratingDiv").style.display = "block";
		document.getElementById("errorDivision").style.display = "none";
			}
		else
			{
		document.getElementById("buttonsDiv").style.display = "none";
		document.getElementById("ajax_field_premiumCalulated").value = 'N';
		document.getElementById("ratingDiv").style.display= "none";
		document.getElementById("errorDivision").style.display = "block";
			}
		
			
		
				
	}
	if(value=='policyExtensionEndorsement')
	{
		document.getElementById("div3").style.display = "block";
		document.getElementById("buttonsDiv").style.display = "block";
		document.getElementById("div1").style.display = "none";
		document.getElementById("div2").style.display = "none";
		document.getElementById("ratingDiv").style.display = "block";
		document.getElementById("errorDivision").style.display = "none";
	}
	
}
function showHideDeductibleEndorsementContent(id)
{
	
	var value=document.getElementById(id).value;
	//alert(value);
	if (value=='DC') 
	{
		
		document.getElementById("changeDeductibles").style.display = "block";
		document.getElementById("changeLimits").style.display = "none";
		document.getElementById("deductibleChange").style.display = "block";
		document.getElementById("limitChange").style.display = "none";
	}
	else 
	{
		document.getElementById("changeDeductibles").style.display = "none";
		document.getElementById("changeLimits").style.display = "block";
		document.getElementById("changeDeductibles").style.display = "none";
		document.getElementById("deductibleChange").style.display = "none";
		document.getElementById("limitChange").style.display = "block";
		
	}
	
/*	document.getElementById("ajax_field_currentLimit").disabled = true;
	document.getElementById("ajax_field_currentClaimExpensesType").disabled = true;
	document.getElementById("ajax_field_currentClaimExpensesTypeOutside").disabled = true;
	document.getElementById("ajax_field_currentDollarDefense").disabled = true;*/
	
	
}
function setFieldsReadonly() {
   /* document.getElementById("ajax_field_currentLimit").readOnly = true;
    document.getElementById("ajax_field_currentClaimExpensesType").disabled = true;
    document.getElementById("ajax_field_currentClaimExpensesTypeOutside").disabled = true;
    document.getElementById("ajax_field_currentDollarDefense").disabled = true;*/
}
function setEndorsementType()
{
	var cancellationReason=document.getElementById("ajax_field_cancellationReason").value;
	if(cancellationReason==1 || cancellationReason==2)
		{
		document.getElementById("ajax_field_cancellationType").value = 2;
		  document.getElementById("ajax_field_cancellationType").disabled = true;
		} 
	else
		{
		//document.getElementById("ajax_field_cancellationType").value = 0;
			document.getElementById("ajax_field_cancellationType").disabled = false;
		}
}

function loadCancellationPage() {

	if(document.getElementById('ajax_field_cancellationReturnPremium')!=null)
	setAmoutFormat('ajax_field_cancellationReturnPremium');
	if(document.getElementById('ajax_field_cancellationReturnTax')!=null)
	setAmoutFormat('ajax_field_cancellationReturnTax');
	if(document.getElementById('ajax_field_cancellationTotalReturnPremium')!=null)
	setAmoutFormat('ajax_field_cancellationTotalReturnPremium');
	if(document.getElementById('ajax_field_reinstatementReturnPremium')!=null)
	setAmoutFormat('ajax_field_reinstatementReturnPremium');
	if(document.getElementById('ajax_field_reinstatementReturnTax')!=null)
	setAmoutFormat('ajax_field_reinstatementReturnTax');
	if(document.getElementById('ajax_field_reinstatementTotalReturnPremium')!=null)
	setAmoutFormat('ajax_field_reinstatementTotalReturnPremium');
}
function loadLimitEndorsement() {
	//alert("hello");
	if(document.getElementById('ajax_field_litmitsEndorsementPremiumNew')!=null)
	setAmoutFormat('ajax_field_litmitsEndorsementPremiumNew');
	if(document.getElementById('ajax_field_litmitsEndorsementTaxNew')!=null)
	setAmoutFormat('ajax_field_litmitsEndorsementTaxNew');
	if(document.getElementById('ajax_field_litmitsEndorsementTotalPremiumNew')!=null)
	setAmoutFormat('ajax_field_litmitsEndorsementTotalPremiumNew');
	if(document.getElementById('ajax_field_deductibleEndorsementPremiumNew')!=null)
	setAmoutFormat('ajax_field_deductibleEndorsementPremiumNew');

}
function setAmoutFormat(id)
{
	amountMask = new Mask("$#,###.####", "number");	
	if(document.getElementById(id) != null)	
		var class1volume = document.getElementById(id);
	if (class1volume != null) {
		class1volume.value = amountMask.format(class1volume.value);
		amountMask.attach(class1volume);
	
	}
}

function showHideInsuranceHistory() {
	var p = getGenericRadioValue("IsFirmCarryingProfLiabilityIns");
	//alert(p);
	if (p == 'Y') {
		$("#div2").show();
		$("#div1").show();
	} else if (p == 'N') {
		$("#div1").hide();
		$("#div2").show();
	} else {
		$("#div1").hide();
		$("#div2").hide();
	}
	var proInsPremium = document.getElementById("ajax_field_ProInsPremium");
	if (proInsPremium.value == '0')
		proInsPremium.value = '';
	loadAppointmentFeeInformation('ajax_field_ProInsPremium');

}
function showHideInsuranceHistory1() {
	var p = getGenericRadioValue("IsFirmCarryingProfLiabilityIns");
	$("#div2").show();
	$("#div1").show();
	//alert(p);
	document.getElementById("ajax_field_IsFirmCarryingProfLiabilityIns").value = p;

}
function showHideInsuranceHistory2()
{
	var p = getGenericRadioValue("IsFirmCarryingProfLiabilityIns");
	$("#div1").hide();
	$("#div2").show();
	//alert(p);
	document.getElementById("ajax_field_IsFirmCarryingProfLiabilityIns").value = p;
}

function showIsPriorActDateFullSection() {
	//alert("Hello");
	var p = getGenericRadioValue("IsPriorActDateFull");
	//alert(p);
	if (p == 'N') {
		$("#fullYear").hide();
		$("#priorActsSpecific").show();
	} else if (p == 'Y') {
		$("#priorActsSpecific").hide();
		$("#fullYear").show();
	} else {
		$("#fullYear").hide();
		$("#priorActsSpecific").hide();
	}
}
function getGenericRadioValue(controlName) {

	var control = document.getElementsByName(controlName);
	if (control) {
		if (control.length) {

			for (var i = 0; i < control.length; i++) {
				if (control[i].checked) {
					var rad_val = control[i].value;
					return rad_val;
				}
				if (i == ((control.length) - 1)) {
					return null;
				}
			}
		} else {

			return control.value;
		}
	}
}

function showHideClaimsSupp()
{
	  var p=getGenericRadioValue("IsAwarenessOfAnyProfLiabilitySuitAgainst");
	  var q=getGenericRadioValue("IsAwarenessOfAnyProfLiability");
	  var r=getGenericRadioValue("IsAnyRegulatoryInquiry");
	  var s=getGenericRadioValue("IsFirmDeclinedOtherthanNonPayment");

	  try
	 	{
		  if(p=='Y' || q=='Y'){
				$("*.claimsSupp").show();
		 	}
			else{
				$("*.claimsSupp").hide();
			}
	 		
	 	}
	 	catch(e)
	 	{}
	 	
	 	try
	 	{
	 		if(r=='Y')
		 		document.getElementById("criminalChargeDesc").style.display = "table-row";
			else
				document.getElementById("criminalChargeDesc").style.display = "none";
	 		
	 	}
	 	catch(e)
	 	{}
	 		
	 
	 	try
	 	{
	 		if(s=='Y')
		 		document.getElementById("declinedDesc").style.display = "table-row";
			else
				document.getElementById("declinedDesc").style.display = "none";
	 		
	 	}
	 	catch(e)
	 	{}
	 	
}
function loadtrustsandofficer()
{ 
	 var p=getGenericRadioValue("IsServedAsTrustee");
	 
	 if(p=='Y')
			$("#showTrustsOREstates").show();
		else
			$("#showTrustsOREstates").hide();
	 
	 var q=getGenericRadioValue("IsFirmRenderedServiceForAnyClient");
//	 alert(p)
	 if(q=='Y')
			$("#showOutsideInterest").show();
		else
			$("#showOutsideInterest").hide();
	 
}

function loadFeeSuitSupplement()
{ 
	 var p=getGenericRadioValue("IsSuitInstituted");
	 
	 if(p=='Y')
		$("#showFeeSuitSupp").show();
	else
		$("#showFeeSuitSupp").hide();
}

function getGenericRadioValue(controlName)
{
	
	var control =  document.getElementsByName(controlName);
	if(control){
	if(control.length){
		
		for (var i=0; i < control.length; i++)
   		{
   			if (control[i].checked)
      			{
      				var rad_val = control[i].value;
      				return rad_val;
      			}
      		if(i == ((control.length)-1)){
      			return null;
      		}
   		}
   	}else{
   		
   		return control.value;
   	}
	}
}
function loadFeeSupplement()
{
	  
	 var numberMask = new Mask("###", "number");
	 var formObject = document.forms[0];
		for ( var i = 0; i < formObject.length; i++) {
	
			var n = formObject.elements[i].name;
			if (n != null) {
	
				if (n.indexOf("AmountOfFeesSued_") > -1) {
					var index = n.substring(n.lastIndexOf("_") + 1, n.length);
					var fld = "ajax_field_AmountOfFeesSued_"+ index;
					var amount=document.getElementById(fld);
					
					numberMask.attach(amount);
					if (amount.value == '0')
						amount.value = '';
					
					loadAppointmentFeeInformation(fld);
				}
				
			}
			
		}
}
function loadAppointmentFeeInformation(id) {
	amountMask = new Mask("$#,###", "number");	
	var feeMask = new Mask("$#,###.##", "number")
	
	if(document.getElementById(id) != null)	
		var class1volume = document.getElementById(id);
	if (class1volume != null) {
		
		class1volume.value = amountMask.format(class1volume.value);
		amountMask.attach(class1volume);
	}
}

function validateFeeSuitData()
{  
	  var result = 0;
	  if($.trim($('input[name=IsSuitInstituted]:checked').val()) === 'Y'){
		  var feeSuitTrLength = $('#ajax_field_addFeeSuits').val();
		  for(var feeSuitCount = 0; feeSuitCount < feeSuitTrLength; feeSuitCount++){
			  var trObj = $("#FeeSuitData_list_01").find("tbody").find("tr[id=FeeSuitData_list_mom_data_01]").eq(feeSuitCount);
//			  var NamedDefendantsInSuit = trObj.find("input[name=NamedDefendantsInSuit_" + feeSuitCount + "]").val();
//			  var DueDateFees = trObj.find("input[name=DueDateFees_" + feeSuitCount + "]").val();
			  var AmountOfFeesSued = trObj.find("input[name=AmountOfFeesSued_" + feeSuitCount + "]").val();
			  var SuitFilesDateFees = trObj.find("input[name=SuitFilesDateFees_" + feeSuitCount + "]").val();
			  var IsAllegOfLegalMalPrac = trObj.find("input[name=IsAllegOfLegalMalPrac_" + feeSuitCount + "]:checked").val();
			  var FeeSuitDispositionKey = trObj.find("#ajax_field_FeeSuitDispositionKey_" + feeSuitCount + " :selected").val();
			  //$.trim(DueDateFees) == "" || $.trim(NamedDefendantsInSuit) == "" || 
			  if($.trim(AmountOfFeesSued) == "" || $.trim(SuitFilesDateFees) == "" || $.trim(FeeSuitDispositionKey) == "" || $.trim(IsAllegOfLegalMalPrac) == ""){
				  result = 1;
				  break;
			  }
		  }
	  }
	  if(result == 0){
		  return true;
	  } else {
		  var cvr = document.getElementById("cover");
		  var body = document.body,
		  html = document.documentElement;
			
		  var height = Math.max( body.scrollHeight, body.offsetHeight, html.clientHeight, html.scrollHeight, html.offsetHeight );
			
		  if (cvr != null) {
			  cvr.style.height = height + "px";
			  cvr.style.display = "block";
			  cvr.style.position = "fixed";
		  }
		  $('#msgBoxFeeSuitData').show();
		  return false;
	  }
}



function loadPracticeManagementSupp()
{
	var p = getGenericRadioValue("IsNonMonetaryCompensationRecieved");
	if (p == 'Y')
		$("#NonMonetaryCompRec").show();
	else
		$("#NonMonetaryCompRec").hide();
	
	p = getGenericRadioValue("IsReducedNumberOfOwners");
	if (p == 'Y')
		$("#ReducedNumOwners").show();
	else
		$("#ReducedNumOwners").hide();
}

function loadUnderwritingInfo() {
	
		
	var p = getGenericRadioValue("IsFirmPersonnelBusinessManagerFc");
	if (p == 'Y')
		$("#rowsm").show();
	else
		$("#rowsm").hide();
		setAmountFormatting('ajax_field_TotalAmountOfClientFundsFc');

}

function loadNonMonetaryCompRec() {
	
	
	var p = getGenericRadioValue("IsNonMonetaryCompensationRecieved");
	if (p == 'Y')
		$("#NonMonetaryCompRec").show();
	else
		$("#NonMonetaryCompRec").hide();
		setAmountFormatting('ajax_field_CurrentValueNmc');

}

function loadmorePractManagement()
{
	/*var p=getGenericRadioValue("IsSuitInstituted");
	 
	if(p=='Y')
		document.getElementById("suppDiv").style.display = "block";
	else
		document.getElementById("suppDiv").style.display = "none";*/
	
	p=getGenericRadioValue("IsPolicyExcludesCoverage");
//	 alert(p)
	if(p=='Y')
		$("#rowsm2").show();
	else
		$("#rowsm2").hide();
} 


function populateauditTotalFieldValue(fieldPrefix, totalValContainerid) {
	var total = 0;
	var err = 0;
	var myVal;
	//alert(fieldPrefix+":::::::"+totalValContainerid);
	try {
		//alert(fieldPrefix);
		for (var i = 0; i < document.forms[0].length; i++) {
			var n = document.forms[0].elements[i].name;
			//alert(n)
			if (n != null && n.indexOf(fieldPrefix) > -1) {
				myVal = document.forms[0].elements[i].value;
				if (myVal != null && myVal != '') {
					if (!isNumberCorrect(myVal)) {
						alert("That number is invalid.  Please enter again...");
						//document.forms[0].elements[i].select();
						//document.forms[0].elements[i].focus();
						return false;
					} else {
						var ival = parseInt(myVal);
						if (n != "NumberOfPersonnel_1") {

							total = parseInt(total) + ival;
							//alert(total);

							//document.getElementById("ajax_field_totalNoOfPersonnel").value = total;
							//alert(total+"end")
						} else {
							//total = parseInt( total);
						}
					}
				}
			}
		}
		// alert(total+">>>>>>>>>"+totalValContainerid);
		document.getElementById(totalValContainerid).innerHTML = total;
		//alert(total)
		if (fieldPrefix == 'AOP_Percentage_') {
			if (total == 100) {
				document.getElementById(totalValContainerid).className = "txt12BoldBlue";
			} else {
				document.getElementById(totalValContainerid).className = "txt12BoldRed";
			}
		}
	} catch (e) {
		// TODO: handle exception
	}
}


function loadFirmNewApp() {

    zipMask = new Mask("#####-####");
    var zip = document.getElementById("ajax_field_Zip");
    if (zip != null) {
          zip.value = zipMask.format(zip.value);
          zipMask.attach(zip);
    }
   
	phoneMask = new Mask("(###)###-####");
	var wrkphone = document.getElementById("ajax_field_WorkPhone");
	
	if (wrkphone != null) {
		wrkphone.value = phoneMask.format(wrkphone.value);
		phoneMask.attach(wrkphone);
	}
var wrkphoneAccountSearch = document.getElementById("ajax_field_WorkPhoneSearch");
	
	if (wrkphoneAccountSearch != null) {
		wrkphoneAccountSearch.value = phoneMask.format(wrkphoneAccountSearch.value);
		phoneMask.attach(wrkphoneAccountSearch);
	}
var otherphone = document.getElementById("ajax_field_ContactPhone");
	
	if (otherphone != null) {
		otherphone.value = phoneMask.format(otherphone.value);
		phoneMask.attach(otherphone);
	}
	
	
}
function SetLimitOfLiability(value)
{
	
//	hideShowClaimExpenseField(document.getElementById('StateCode').value);
	//alert(value);
	if(value<13 || value==19)
		document.getElementById('ajax_field_LimitSequence').value = value;
	else 
		document.getElementById('ajax_field_LimitSequence').value = 12;
	makeDefenseOutsideRuleNewApp(value);
	
}

function setDeductible(value)
{
	document.getElementById('ajax_field_DeductibleSequence').value = value;
	
}

function setDefenseExpense(value)
{	
	
	var stateCode = document.getElementById('StateCode').value;
	if(stateCode != 'VT' && stateCode != 'NM' && stateCode != 'ND'  && stateCode != 'SD'  && stateCode != 'AR')
	{		
		

			if(value=='Y')
			{
				document.getElementById('ajax_field_IsClaimExpensesType1').checked = true;
			}
			else if(value=='N' )
			{
				document.getElementById('ajax_field_IsClaimExpensesType2').checked = true;
			}		
		
	}
	
}
function setClaimValues(value)
{
	
		
		if(value=='Y' )
		{
			document.getElementById('ajax_field_IsClaimOptionType1').checked = true;
		}
		else if(value=='N')
		{
			document.getElementById('ajax_field_IsClaimOptionType2').checked = true;
		}		
	
}
function showPriorActsSection() { 
	var p=getGenericRadioValue("IsPriorActDateFull");
	$("#fullYear").hide();
	$("#priorActsSpecific").show();
	   document.getElementById("ajax_field_IsPriorActDateFull").value = p;
	}

function showFullActsSection() { 
	var p=getGenericRadioValue("IsPriorActDateFull");
	$("#fullYear").show();
	$("#priorActsSpecific").hide();
	   document.getElementById("ajax_field_IsPriorActDateFull").value = p;
	}
function showIsPriorActDateFullSection()
{
	//alert("Hello");
	var p=getGenericRadioValue("IsPriorActDateFull");
	//alert(p);
	if(p=='N')
	{
		$("#fullYear").hide();
		$("#priorActsSpecific").show();
	}
	else if(p=='Y')
	{	
		$("#priorActsSpecific").hide();
		$("#fullYear").show();
	}else{
		$("#fullYear").hide();
		$("#priorActsSpecific").hide();
	}
}
function showHideSecurityBroker()
{
	var p=getGenericRadioValue("IsContractualRelationShip");
	//alert(p);
	if(p=='Y')
		document.getElementById("trDiv1").style.display = "table-row";
	else if(p=='N')
		document.getElementById("trDiv1").style.display = "none";	
}

function getLoc() {
	
	var items=document.getElementsByName('AOPKey');
	var selectedItems="";
	for (i = 0; i < items.length; i++) { 
		if(items[i].type=='checkbox' && items[i].checked==true)
			selectedItems+=items[i].value+",";
	}
	document.getElementById("AOP_values").value = selectedItems;
}

function isAreasofPracticeValueChange(){
	if($.trim($("#PolicyStatusKeyAOP").val()) == '2' && ($.trim($("#StatusKeyAOP").val()) == '1' || $.trim($("#StatusKeyAOP").val()) == '2')){
	var p = getGenericRadioValue("IsAOPChange");
	
	var trLength = $("#aoplist_list_2").find("tbody").find("tr[id=aoplist_list_mom_data_2]").length;
	for(var aopCount = 0; aopCount < trLength; aopCount++){
		var trObj = $("#aoplist_list_2").find("tbody").find("tr[id=aoplist_list_mom_data_2]").eq(aopCount);
		var isEditValue = trObj.find("input[name=IsEdit_" + aopCount + "]").val();
		if (p == 'Y'){
			trObj.find("input[name=AOP_Percentage_" + aopCount + "]").removeAttr("readonly");
		} else if ((p == 'N' || $.trim(p) == '') && isEditValue == 'N'){
			trObj.find("input[name=AOP_Percentage_" + aopCount + "]").attr("readonly", "readonly");
		}
	}
	if (p == 'Y'){
		$("#addAopNewApp").show();
		$("#otherDescId").removeAttr("readonly");
	} else {
		$("#addAopNewApp").hide();
		$("#otherDescId").attr("readonly", "readonly");
	}
	}
	if(!($.trim($("#StatusKeyAOP").val()) == '1' || $.trim($("#StatusKeyAOP").val()) == '2')){
		$("#otherDescId").attr("readonly", "readonly");
	}
}

function showOtherDesc()
{
//	alert("")
	//	debugger;
	try {
		var formObject = document.forms[0];
		var  otherPerc = 0;
		var  otherDesc = "";
		for (var i = 0; i < formObject.length; i++) {
			var n = formObject.elements[i].name;
			if (n != null) {
				if (n.indexOf("AOP_Percentage_") > -1) {
					var index = n.substring(n.lastIndexOf("_") + 1, n.length);
					var fld = "AOP_Percentage_" + index;
					var values = document.getElementById(fld).value;

					if (values != null) {
						var desc = document.getElementById("AOPDesc_" + index).value;
						//alert("desc show other  "+desc);
						if (desc == "Other") {
//							alert("otherPerc")
							otherDesc = desc;
							var myVal = parseInt(values);
							otherPerc = myVal;

						}
					}
				}
			}
		}
	} catch (e) {
		// TODO: handle exception
	}
	try {
		if(otherDesc == "Other") {
			if (otherPerc > 0)
				$("#dis5y").show();
			else
				$("#dis5y").hide();
		}
	} catch (e) {
		// \: handle exception
	}

}

function validateRBPracticeManagementData(){
	if (validateServerTrusteeSuppData()) {
		if (validateFirmRenderedServiceData()) {
			if (validateFeeSuitData()) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	} else {
		return false;
	}
}

function validateNBTrustsAndOfficerData() {
	if (validateServerTrusteeSuppData()) {
		if (validateFirmRenderedServiceData()) {
			return true;
		} else {
			return false;
		}
	} else {
		return false;
	}
}


function validateServerTrusteeSuppData()
{  
	  var result = 0;
	  if($.trim($('input[name=IsServedAsTrustee]:checked').val()) === 'Y'){
		  var feeSuitTrLength = $('#ajax_field_addTrustsOREstates').val();
		  for(var feeSuitCount = 0; feeSuitCount < feeSuitTrLength; feeSuitCount++){
			  var trObj = $("#ServerTrusteeSupp_list_01").find("tbody").find("tr[id=ServerTrusteeSupp_list_mom_data_01]").eq(feeSuitCount);
			  
			  var NameOfTrust = trObj.find("input[name=NameOfTrust_" + feeSuitCount + "]").val();
			  var TypeofTrustKey = trObj.find("#ajax_field_TypeofTrustKey_" + feeSuitCount + " option:selected").val();
			  var ValueOfAssetsTrust = trObj.find("input[name=ValueOfAssetsTrust_" + feeSuitCount + "]").val();
			  var IsBeneficiaryInterestTrust = trObj.find("input[name=IsBeneficiaryInterestTrust_" + feeSuitCount + "]:checked").val();
			  
			  if($.trim(NameOfTrust) == "" || $.trim(TypeofTrustKey) == "" || $.trim(ValueOfAssetsTrust) == "" || $.trim(IsBeneficiaryInterestTrust) == ""){
				  result = 1;
				  break;
			  }
		  }
	  }
	  if(result == 0){
		  return true;
	  } else {
		  var cvr = document.getElementById("cover");
		  var body = document.body,
		  html = document.documentElement;
			
		  var height = Math.max( body.scrollHeight, body.offsetHeight, html.clientHeight, html.scrollHeight, html.offsetHeight );
			
		  if (cvr != null) {
			  cvr.style.height = height + "px";
			  cvr.style.display = "block";
			  cvr.style.position = "fixed";
		  }
		  $('#msgBoxTrustEstateData').show();
		  return false;
	  }
}

function validateFirmRenderedServiceData()
{  
	  var result = 0;
	  if($.trim($('input[name=IsFirmRenderedServiceForAnyClient]:checked').val()) === 'Y'){
		  var feeSuitTrLength = $('#ajax_field_addOutsideInterest').val();
		  for(var feeSuitCount = 0; feeSuitCount < feeSuitTrLength; feeSuitCount++){
			  var trObj = $("#OutsideInterestSupp_list_01").find("tbody").find("tr[id=OutsideInterestSupp_list_mom_data_01]").eq(feeSuitCount);
			  
			  var ClientNameOsi = trObj.find("input[name=ClientNameOsi_" + feeSuitCount + "]").val();
			  var EntityNameOsi = trObj.find("input[name=EntityNameOsi_" + feeSuitCount + "]").val();
			  var ClientIndustryOsiKey = trObj.find("#ajax_field_ClientIndustryOsiKey_" + feeSuitCount + " :selected").val();
			  var PositionHeldOsi = trObj.find("input[name=PositionHeldOsi_" + feeSuitCount + "]").val();
			  var DOInsuranceOsi = trObj.find("input[name=DOInsuranceOsi_" + feeSuitCount + "]:checked").val();
			  var PercentEquityInterestofOsi = trObj.find("input[name=PercentEquityInterestofOsi_" + feeSuitCount + "]").val();
			  var ServiceIndustryOsiKey = trObj.find("#ajax_field_ServiceIndustryOsiKey_" + feeSuitCount + " :selected").val();
			  var IndividualListedServicesOsi = trObj.find("input[name=IndividualListedServicesOsi_" + feeSuitCount + "]:checked").val();
			  
			  if($.trim(ClientNameOsi) == "" || $.trim(EntityNameOsi) == "" || $.trim(ClientIndustryOsiKey) == "" || $.trim(PositionHeldOsi) == "" || $.trim(DOInsuranceOsi) == "" || $.trim(PercentEquityInterestofOsi) == "" || $.trim(ServiceIndustryOsiKey) == "" || $.trim(IndividualListedServicesOsi) == ""){
				  result = 1;
				  break;
			  }
		  }
	  }
	  if(result == 0){
		  return true;
	  } else {
		  var cvr = document.getElementById("cover");
		  var body = document.body,
		  html = document.documentElement;
			
		  var height = Math.max( body.scrollHeight, body.offsetHeight, html.clientHeight, html.scrollHeight, html.offsetHeight );
			
		  if (cvr != null) {
			  cvr.style.height = height + "px";
			  cvr.style.display = "block";
			  cvr.style.position = "fixed";
		  }
		  $('#msgBoxFirmRenderedServiceData').show();
		  return false;
	  }

}

function loadAboutFirm()
{
	
	var p=getGenericRadioValue("IsFirmsRevenueMorethan25Percent");
	
	if(p=='Y')
		$("#rowsm1").show();
	else
		$("#rowsm1").hide();
}
function setAmountFormatting(id) {
	
		amountMask = new Mask("$#,###", "number");	
		var feeMask = new Mask("$#,###.##", "number")
		
		if(document.getElementById(id) != null)	
			var class1volume = document.getElementById(id);
		if (class1volume != null) {
			
			class1volume.value = amountMask.format(class1volume.value);
			amountMask.attach(class1volume);
		}
	
	
}
function loadTrustOfficers()
{

	  
	 var numberMask = new Mask("###", "number");
	 var formObject = document.forms[0];
		for ( var i = 0; i < formObject.length; i++) {
	
			var n = formObject.elements[i].name;
			if (n != null) {
	
				if (n.indexOf("ValueOfAssetsTrust_") > -1) {
					//alert('hi');
					var index = n.substring(n.lastIndexOf("_") + 1, n.length);
					var fld = "ajax_field_ValueOfAssetsTrust_"+ index;
					var amount=document.getElementById(fld);
					
					numberMask.attach(amount);
					if (amount.value == '0')
						amount.value = '';
					
					setAmountFormatting(fld);
				}
				
			}
			
		}

	
}
function makeDefenseOutsideRuleNewApp(value){

	var stateCode = document.forms[0].StateCode.value;
	
	if(stateCode == 'ND' || stateCode == 'AR'  )
	{
	if(value < 11){
		
		document.getElementById('ajax_field_IsClaimExpensesType2').checked = true;
		document.getElementById('ajax_field_IsClaimExpensesType2').disabled=false;
		document.getElementById('ajax_field_IsClaimExpensesType1').disabled=true;
		//document.getElementById('IsClaimExpensesType').value='Y';
	}else{
		
		document.getElementById('ajax_field_IsClaimExpensesType1').checked = false;
		document.getElementById('ajax_field_IsClaimExpensesType1').disabled=false;
		
	}
	}
}

function makeDefenseOutsideRuleOnPageLoad(){
	var limit = '';
	if (document.getElementById('ajax_field_LimitSequence') != null)
		limit = document.forms[0].LimitSequence.value;	
	if(limit != '')
		makeDefenseOutsideRuleNewApp(limit);

	}

function loadPracticeManagement()
{
	try
	{
	setAmountFormatting('ajax_field_TotalAmountOfClientFundsFc');
	var p=getGenericRadioValue("IsFirmsRevenueMorethan25Percent");
//	 alert(p)
	if(p=='Y')
		$("#rowsm1").show();
	else
		$("#rowsm1").hide();
	
	p = getGenericRadioValue("IsFirmRenderingServices");
	if (p == 'Y')
		$("#seperateentitysupp").show();
	else
		$("#seperateentitysupp").hide();
		
	p = getGenericRadioValue("IsControlClientFunds");
	if (p == 'Y')
		$("#businessmanagesupp").show();
	else
		$("#businessmanagesupp").hide();
	
	}
	catch(e)
	{}

	try
	{
		applyDateFormatMMYYYY(document.getElementById('EstablisedSeDateNew'));
		setPercentageFormat('PercentOfOwnershipSeNew');
		amountMask = new Mask("$#,###", "number");
		
		var amt0 = document.getElementById("LastFiscalYearGrossRevenueSe");
		var amt1 = document.getElementById("EstimateCurrentYearGrossRevenueSe");
				
		if(amt0 != null )
		{
			amountMask.attach(amt0);	
			if(amt0.value == '0')
				amt0.value = '';
				
			amt0.value=amountMask.format(amt0.value);	
				
		}
		if(amt1 != null )
		{
			amountMask.attach(amt1);	
			if(amt1.value == '0')
				amt1.value = '';
				
			amt1.value=amountMask.format(amt1.value);	
				
		}
	}
	catch(e1)
	{}
	
	
	

	
}
function validatePercentage(id)
{
	
	 var numberMask = new Mask("###", "number");
	 
	
		
		if(document.getElementById(id) != null)	
			var class1volume = document.getElementById(id);
		//alert(class1volume);
		if (class1volume != null) {
			
			class1volume.value = numberMask.format(class1volume.value);
			numberMask.attach(class1volume);
			
			
		}
		var percentage=document.getElementById(id).value;
		if(percentage>100)
			{
			alert("Entered percentage value can't be greater than 100%");
			document.getElementById(id).value=100;
			}
}

function hideClaimSuppl(){
    document.getElementById("divCov1By").style.display = "none";
}
function showClaimSuppl(){
    document.getElementById("divCov1By").style.display = "block";
}
function hideEditClaimSuppl(){
    document.getElementById("divEditClaimCov1By").style.display = "none";
}
function showEditClaimSuppl(){
    document.getElementById("divEditClaimCov1By").style.display = "block";
}
function detectBrowser(){
	var objAgent = window.navigator.userAgent; 
	var objbrowserName = ""; 
	
	if ((objAgent.indexOf("Chrome"))!=-1) { // In Chrome 
		objbrowserName = "Chrome";
	} else if ((objAgent.indexOf("MSIE"))!=-1 || navigator.userAgent.match(/Trident.*rv\:11\./)) { // In Microsoft internet explorer 
		objbrowserName = "Microsoft Internet Explorer";
	} else if ((objAgent.indexOf("Firefox"))!=-1) {  // In Firefox 
		objbrowserName = "Firefox"; 
	} else if ((objAgent.indexOf("Safari"))!=-1) {  // In Safari 
		objbrowserName = "Safari"; 
	}
	return objbrowserName;
}

function processBarIn(obj) {
	var cvr = document.getElementById("cover");
	var body = document.body,
	html = document.documentElement;

	var height = Math.max(body.scrollHeight, body.offsetHeight, html.clientHeight, html.scrollHeight, html.offsetHeight);

	if (cvr != null) {
		cvr.style.height = height + "px";
		cvr.style.display = "block";
		cvr.style.position = "fixed";
	}
	$("#processing").show();
}

function processBarOut() {
	var cvr = document.getElementById("cover");
	var body = document.body,
	html = document.documentElement;

	var height = Math.max(body.scrollHeight, body.offsetHeight, html.clientHeight, html.scrollHeight, html.offsetHeight);

	if (cvr != null) {
		cvr.style.height = height + "px";
		cvr.style.display = "none";
		cvr.style.position = "";
	}
	$("#processing").hide();
}
function toUpperCase(id) {
	  var str = document.getElementById(id).value;
	  var res = str.toUpperCase();
	  document.getElementById(id).value=res;
}
function setlinkDelink()
{
	try
	{
	var val=document.getElementById('accountActivity').value;
	//alert(val);
	if(val==null || val=='')
	{
		//alert('1');
		document.getElementById('ajax_field_linkAccounts').checked=false;
		document.getElementById('ajax_field_deLinkAccounts').checked=false;
	}
	
	if(val=='linkAccounts')
		{
		//alert('2');
		document.getElementById('ajax_field_linkAccounts').checked=true;
		
		}
	 if(val=='deLinkAccounts')
		 {
		// alert('3');
		document.getElementById('ajax_field_deLinkAccounts').checked=true;
		 }
	}
	catch (e) {
		// TODO: handle exception
	}
	 
	}

function clearSearchData()
{
	document.getElementById('AccountNameSearch').value=null;
	document.getElementById('AccountEmailSearch').value=null;
	document.getElementById('ajax_field_PolicyEffectiveMonthYear').value=null;
	document.getElementById('ajax_field_WorkPhoneSearch').value=null;
	document.getElementById('ajax_field_MergeStateCode').value="";
	document.getElementById('ajax_field_AccountNumberSearch').value=null;
	document.getElementById('errorsDiv').style.display="none";
	document.getElementById('ajax_field_linkAccounts').checked=false;
	document.getElementById('ajax_field_deLinkAccounts').checked=false;
}
function updateData()
{
	var activityType=document.getElementById('accountActivity').value.length;
	var selectedAccounts=document.getElementById('strPolicyKey').value.length;
	
	if(activityType==0)
		{
		//alert('Please select Link or De-link activity type');
		showPopUp2('popupsave3','500','400');
		return;
		}
	if(selectedAccounts==0)
		{
		//alert('Please select accounts to perform activity');
		showPopUp2('popupsave4','500','400');
		return;
		}
	if(activityType>0 && selectedAccounts>0)
		{
		showPopUp2('popupsave2','500','400');
		}
}
function showPopUp2(el, wdth, hght) {
	window.scrollTo(0,0);
	var wBody = parseInt(document.body.scrollWidth, 10);
	var wPopup = parseInt(document.getElementById(el).style.width, 10);
	document.documentElement.style.overflow = document.body.style.overflow = "auto";
	var w = ((wBody - wPopup) / 2);
	var h = 100;
	var cvr = document.getElementById("cover");
	var dlg = document.getElementById(el);
	//var height = document.body.clientHeight;
	var body = document.body,
	html = document.documentElement;
	
	var height = Math.max( body.scrollHeight, body.offsetHeight, 
	                              html.clientHeight, html.scrollHeight, html.offsetHeight );
	if (dlg != null && w!=null) {
		dlg.style.display = "block";
		dlg.style.left = w + "px";
		dlg.style.top = h + "px";
		//window.scrollTo(0,0);
		//alert(w+" "+h);
	}
	if (cvr != null) {
		cvr.style.height = height + "px";
		cvr.style.display = "block";
		//alert(height);
		cvr.style.position = "fixed";
	}
}
function closePopUp2(el) {
	document.documentElement.style.overflow = document.body.style.overflow = "auto";
	var cvr = document.getElementById("cover");
	var dlg = document.getElementById(el);
	//alert("--------"+cvr);
	if(cvr!=null){
		//alert(cvr.style);
		cvr.style.display = "none";
		dlg.style.display = "none";
	}
	//alert('agter');
	if(document.getElementById("tax_popup_error")!=null)
	{
		document.getElementById("tax_popup_error").innerHTML = "";
	}
	
}
function linkDelinkAccounts()
{
	var field1=document.getElementById('ajax_field_linkAccounts').checked;
	var field2=document.getElementById('ajax_field_deLinkAccounts').checked;
	
	if(field1==true)
		{
		document.getElementById('accountActivity').value='linkAccounts';
		document.getElementById('actionName').innerHTML ='link';
		}
	if(field2==true)
	{
		document.getElementById('accountActivity').value='deLinkAccounts';
		document.getElementById('actionName').innerHTML ='de-link';
	}
	
	
}

function getSelectedPolicyKeyAccountManagement(){
	var searchlistSize=Number(document.getElementById('SearchlistSize').value);
	var accountIds = [];
	for(var i = 0 ; i < searchlistSize ; i++)
	{
		var account = document.getElementById("AccountID_"+i);
		if(account == null)
			break;
		if(account.checked)
			accountIds.push(account.value);
	}
	document.getElementById("strPolicyKey").value = accountIds.join(",");
	document.getElementById("activityAccountNames").innerHTML = accountIds.join(", ");
}

function selectAllAccountManagement(checked){
	var searchlistSize=Number(document.getElementById('SearchlistSize').value);
	for(var i = 0 ; i < searchlistSize ; i++)
	{
		var account = document.getElementById("AccountID_"+i);
		if(account == null)
			break;
		account.checked = checked;
	}
	getSelectedPolicyKeyAccountManagement();
}

function checkUnCheckPaymentDiv(areas_show, areas_hide){
//alert(areas_show);
	for (var i = 0; i < areas_show.length; i++)
	{	
		ge = document.getElementById(areas_show[i]);		
		if(ge){
			//	alert(ge);
			ge.checked = true;
		}
	}
	for (var i = 0; i < areas_hide.length; i++)
	{
		var gee = document.getElementById(areas_hide[i]);		
		if(gee){
			gee.checked = false;
		}
		
	}
}
function ipfsPFAPreview(){
	var url = window.location.href;
	
	url = url.substring(0 , url.indexOf("AccountantIns/") + 14);
	
	url += "Endorsement.do?User=insured&role_id=1&AccountEmail=cbarndollar@protexure.com&divHideShow=&inet_context=";
	url += "&inet_method=ipfsPFAPreview&inet_page=paymentmethod&dynaKeys=&dynaValues=";
	url += "&ctxmenuparam=&dateFormat=&dateFormatTS=&quickquoteoptionlist_pageFieldsList=";
	
	url += "&jsessionid="+$("#jsessionid").val();
	url += "&IPFSPFAURL="+$("#IPFSPFAURL").val();
	url += "&PaymentMode="+document.getElementById('PaymentMode').value;
	
	var params = ['height='+screen.height,'width='+screen.width,'scrollbars=yes'].join(',');
	var win = window.open(url, 'ipfsPFAPreview', params);
	win.moveTo(0,0);
	win.focus();
//	win.print();
}
function setPayemntMode()
{
	var paymentMode=document.getElementById("Payment_Selection_Mode").value ;
	//alert(paymentMode);
	
	//document.getElementById('PaymentMode_CreditCard').disabled=true;
//	document.getElementById('PaymentMode_ACH').disabled=true;
//	document.getElementById('PaymentMode_ManualCheck').disabled=true;
	//document.getElementById('PaymentMode_IPFS').disabled=true;
	if(paymentMode=='paypal' || paymentMode=='AuthorizeDotNet')
		document.getElementById('PaymentMode_CreditCard').checked=true;
	if(paymentMode=='ach')
		document.getElementById('PaymentMode_ACH').checked=true;
	if(paymentMode=='eft')
		document.getElementById('PaymentMode_ManualCheck').checked=true;
	if( paymentMode=='finance')
		document.getElementById('PaymentMode_IPFS').checked=true;
}
function setPercentageFormat(id)
{
	try
	{
		//alert(id);
		if(document.getElementById(id) != null) {
			var percentageAmount=document.getElementById(id).value;
		//alert(percentageAmount);
		if(percentageAmount.length>0 && !percentageAmount.includes('%'))
			document.getElementById(id).value=percentageAmount+'%';
		
	}
	}
	catch(e)
	{}
}

function setDataToFileds(source,target)
{
	try
	{
		if(document.getElementById(source)!=null)
			{
				var data=document.getElementById(source).value;
				data=data.replace(/[^\w\s]/g, '');
				document.getElementById(target).value=data;
			}
	}
	
	catch(e)
	{}
}
function setDataTest(id)
{
	alert('hi');
	
}


function showPopUp(el) {

	var w = (screen.width - 434) / 2;
	var t = (screen.height - 450) / 2;
	var cvr = document.getElementById("cover");
	var dlg = document.getElementById(el);
	var ss = document.getElementById("StateCode");
	cvr.style.display = "block";
	dlg.style.display = "block";
	dlg.style.left = w;
	dlg.style.top = t;
	document.getElementById("cover").style.height = document.body.scrollHeight;
}
function showPopUpOverloaded(el, wdth, hght) {

	window.scrollTo(0,0);
	var wBody = parseInt(document.body.scrollWidth, 10);
	var wPopup = parseInt(document.getElementById(el).style.width, 10);
//	document.documentElement.style.overflow = document.body.style.overflow = "auto";
	var w = ((wBody - wPopup) / 2);
	var h = 100;
	var cvr = document.getElementById("cover");
	var dlg = document.getElementById(el);
	//var height = document.body.clientHeight;
	var body = document.body,
	html = document.documentElement;
	
	var height = Math.max( body.scrollHeight, body.offsetHeight, html.clientHeight, html.scrollHeight, html.offsetHeight );
	if (dlg != null && w!=null) {
		dlg.style.display = "block";
		dlg.style.left = w + "px";
		dlg.style.top = h + "px";
		//window.scrollTo(0,0);
		//alert(w+" "+h);
	}
	if (cvr != null) {
		cvr.style.height = height + "px";
		cvr.style.display = "block";
		//alert(height);
		cvr.style.position = "fixed";
	}
}

function closePopUp(el) {
	try{
		var ss = document.getElementById("StateCode");
		ss.style.display = "block";
		var ss1 = document.getElementById("DeductibleSequence");
		ss1.style.display = "block";
	}
	catch(e)
	{}
	var cvr = document.getElementById("cover");
	var dlg = document.getElementById(el);
	cvr.style.display = "none";
	dlg.style.display = "none";
	
}

function loadExpiringInsuranceDiv()
{
	try
	{
		var p=getGenericRadioValue("IsFirmCarryingProfLiabilityIns");
//	 alert(p)
		if(p=='Y')
			document.getElementById("expiringInsuranceDiv").style.display="block";
		else
			document.getElementById("expiringInsuranceDiv").style.display="none";
	}
	catch(e)
	{}
}

function onSelectUploadInsuedFile(obj){
	$("#path").val($(obj).val())
}

function toggleDisplay(id) {
	  var x = document.getElementById(id);
	  if (x.style.display === "none") {
	    x.style.display = "block";
	  } else {
	    x.style.display = "none";
	  }
	}
	function resetLimitIndiaction(){	
	if (document.getElementById('LimitSequence') != null){
		limit = document.forms[0].LimitSequence.value = "";	
	}
}

function populateTotalFieldValueEstimateForm(fieldPrefix,totalValContainerid){
	 var total = 0;
	 var err =0;
	 var myVal;

	 try{
	 
	 for (var i=0; i<document.forms[0].length; i++)
      {
            var n = document.forms[0].elements[i].name;
            if (n != null && n.indexOf(fieldPrefix) > -1)
            {
            	 myVal=document.forms[0].elements[i].value;
            	 if( myVal != null && myVal != '' )	
	            {
 		    		if(!isNumberCorrect(myVal)){
            		  		alert("That number is invalid.  Please enter again...");
							//document.forms[0].elements[i].select();
							//document.forms[0].elements[i].focus();
							return false;
            		  }else{
	 		    		var ival= parseInt(myVal);
            		  	if(n !="NumberOfPersonnel_1"){
            		  		
            		  		total =parseInt(total) +ival;
            		  		
            		  	
            		  		//document.getElementById("ajax_field_totalNoOfPersonnel").value = total;
            		  		
            		  	}
            		  	}
            	}
            }
      }
      
	document.getElementById(totalValContainerid).innerHTML = total;
	//alert(total)
	if(fieldPrefix == 'AOP_Percentage_')
	{
		if (total == 100){
		document.getElementById(totalValContainerid).className = "txt12BoldBlue";
		}
		else{
		document.getElementById(totalValContainerid).className = "txt12BoldRed";
		}
	}
	showOtherDesc();
	 }catch (e) {
		// TODO: handle exception
	}
}
