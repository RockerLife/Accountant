function getSelectedFilterDataDescription(){
	if(document.getElementById('#REPORTING_PAGE_ERROR#') != null){
		document.getElementById('#REPORTING_PAGE_ERROR#').innerHTML = '&nbsp;';
	}
	if(document.getElementById('searched_filter_data') != null && document.getElementById('selected_filter_data')){
		//document.getElementById('searched_filter_data').value = '';
		var searchFields = document.getElementById('searched_filter_data').value.split(';');
		//alert(document.getElementById('searched_filter_data').value);
		var filter_data = '';
		for(var i=0; i<searchFields.length; i++){
			var fld = searchFields[i];
			if(fld == '')
				continue;
				
			if(filter_data == '')
				filter_data = getSelectedValues(fld) + '|';
			else		
				filter_data = filter_data + getSelectedValues(fld) + '|';	
		}
		
		getSelectedGroupBySearch();
		
		getSelectedOutputColumns();
		
		if(document.getElementById('groupby') != null)
			filter_data = filter_data + 'groupby=' + document.getElementById('groupby').value + "|";
		
		document.getElementById('selected_filter_data').value = filter_data;
		//alert('Final Data : - ' + filter_data);
	}
}

function getSelectedValues(fld){
	//alert(fld);
	if(fld != null && document.getElementById('ajax_field_'+fld) != null){
		//alert(document.getElementById(fld));
		var obj = document.getElementById('ajax_field_'+fld);
		//alert(obj.type);
		if(obj.type.indexOf('select') > -1){
			if(!isPopupSearchColumnFound('ajax_field_'+fld))
				return fld + '=' + getMultipleSelectedValues(obj);
			else
				return fld + '=' + getMultipleSelectedValues1(obj);	
		}else if(obj.type.indexOf('text') > -1){
			return fld + '=' + document.getElementById('ajax_field_'+fld).value;
		}else if(obj.type.indexOf('checkbox') > -1 && obj.checked){
		//alert(document.getElementById('ajax_field_'+fld).value);
			return fld + '=' + document.getElementById('ajax_field_'+fld).value;
		}
	}
	
	return fld+'=';
}

function getMultipleSelectedValues(obj){
	var data = null;
	if(obj.options != null && obj.options.length > 0){
		for(var i=0; i<obj.options.length; i++){
			if(obj.options[i].selected){
				//alert(data);
				if(data == 'All'){
					obj.value = '';
					return 'All';
				}	
					
				if(data == null){
					if(obj.options[i].innerHTML == 'Select')
						data = 'All';
					else	
						data = obj.options[i].innerHTML.replace('&amp;','&');
				}else{
					if(obj.options[i].innerHTML == 'Select')
						data = 'All';
					else
						data = data + ', ' + obj.options[i].innerHTML.replace('&amp;','&');
				}	
				//alert(data);	 
			}	
		}
	}
	return data;
}

// ###############    SHOW POPUP    #################################
function showPopUp(el, wdth, hght) {
  	//alert('dfsdf');
  	window.scrollTo(0,0);
	var wBody = parseInt(document.body.scrollWidth, 10);
	var wPopup = parseInt(document.getElementById(el).style.width, 10);
	document.documentElement.style.overflow = document.body.style.overflow = "hidden";
	var w = ((wBody - wPopup) / 2);
	var h = 100;
	var cvr = document.getElementById("cover");
	var dlg = document.getElementById(el);
	if (dlg != null && w!=null) {
		dlg.style.display = "block";
		dlg.style.left = w + "px";
		dlg.style.top = h + "px";
		//window.scrollTo(0,0);
			//alert(w+" "+h);
	}
	if (cvr != null) {
		cvr.style.height = 100 + "%";
		cvr.style.display = "block";
		document.getElementById("cover").style.height = document.body.scrollHeight;
	}
}
function closePopUp(el) {
	document.documentElement.style.overflow = document.body.style.overflow = "auto";
	var cvr = document.getElementById("cover");
	var dlg = document.getElementById(el);
	//alert("--------"+cvr);
	if(cvr!=null){
	cvr.style.display = "none";
	dlg.style.display = "none";
	}
	document.getElementById('issearch').value='N';
}

function loadManageReportTemplate(){
	//alert(document.getElementById('issearch').value);
	if(document.getElementById('issearch') != null && document.getElementById('issearch').value == 'Y')
		showPopUp("popupAprooved", "600", "550");
	
	if((document.getElementById('from_select_filter_data') != null && 
		document.getElementById('from_select_filter_data').options.length == 0) && 
		(document.getElementById('popupsearch_data') != null && 
		document.getElementById('popupsearch_data').value != '')){
		document.getElementById('norecordfoundrow').style.display = 'block';
	}	
	
	loadManageReportTemplate1();
	
	if(document.getElementById('popupsearch_data') != null)
		document.getElementById('popupsearch_data').focus();
		
	//alert(document.getElementById('#REPORTING_PAGE_ERROR#').innerHTML);
	/*if(document.getElementById('#REPORTING_PAGE_ERROR#') != null && document.getElementById('#REPORTING_PAGE_ERROR#').innerHTML!=''){
		document.forms[0].target = "_self";
	}else
		document.forms[0].target = "_blank";*/	
}

function moveItemLeftToRight(){
	if(document.getElementById('from_select_filter_data') != null){
		var select = document.getElementById('from_select_filter_data');
		var fromSelect = document.createElement("select");
		for(var i=0; i<select.options.length; i++){
			var option = select.options[i];
			if(option != null && option.selected){
				//alert(option.value);
				document.getElementById('to_select_filter_data').options[document.getElementById('to_select_filter_data').options.length] = new Option(option.innerHTML.replace('&amp;','&'), option.value);
			}else{
				fromSelect.options[fromSelect.options.length] = new Option(option.innerHTML.replace('&amp;','&'), option.value);
			}
		}
		
		//alert(fromSelect.options.length);
		
		document.getElementById('from_select_filter_data').value = '';
		document.getElementById('from_select_filter_data').options.length = fromSelect.options.length;
		
		for(var i=0; i<fromSelect.options.length; i++){
			var option = fromSelect.options[i];
			document.getElementById('from_select_filter_data').options[i] = new Option(option.innerHTML.replace('&amp;','&'), option.value);
		}
	}
}

function moveItemRightToLeft(){
	if(document.getElementById('to_select_filter_data') != null){
		var select = document.getElementById('to_select_filter_data');
		var fromSelect = document.createElement("select");
		for(var i=0; i<select.options.length; i++){
			var option = select.options[i];
			if(option != null && option.selected){
				//alert(option.value);
				document.getElementById('from_select_filter_data').options[document.getElementById('from_select_filter_data').options.length] = new Option(option.innerHTML.replace('&amp;','&'), option.value);
			}else{
				fromSelect.options[fromSelect.options.length] = new Option(option.innerHTML.replace('&amp;','&'), option.value);
			}
		}
		
		//alert(fromSelect.options.length);
		
		document.getElementById('to_select_filter_data').value = '';
		document.getElementById('to_select_filter_data').options.length = fromSelect.options.length;
		
		for(var i=0; i<fromSelect.options.length; i++){
			var option = fromSelect.options[i];
			document.getElementById('to_select_filter_data').options[i] = new Option(option.innerHTML.replace('&amp;','&'), option.value);
		}
	}
}

function getSelectedToSelectFilterData(fld, iskeyvaluepair){
	if(document.getElementById('to_select_filter_data') != null){
		var to_filter_data = null;
		var select = document.getElementById('to_select_filter_data');
		for(var i=0; i<select.options.length; i++){
			var option = select.options[i];
			if(option != null){
				/*if(iskeyvaluepair=='Y'){
					if(to_filter_data == null)
						to_filter_data = option.innerHTML.replace('&amp;','&').replace(',','#COMMA#')+'='+option.value;
					else
						to_filter_data = to_filter_data + ',' + option.innerHTML.replace('&amp;','&').replace(',','#COMMA#')+'='+option.value;
				}else{
					if(to_filter_data == null)
						to_filter_data = option.innerHTML.replace('&amp;','&').replace(',','#COMMA#');
					else
						to_filter_data = to_filter_data + ', ' + option.innerHTML.replace('&amp;','&').replace(',','#COMMA#');
				}*/	
				if(iskeyvaluepair=='Y'){
					if(to_filter_data == null)
						to_filter_data = option.innerHTML.replace('&amp;','&')+'='+option.value;
					else
						to_filter_data = to_filter_data + '#Hash;#' + option.innerHTML.replace('&amp;','&')+'='+option.value;
				}else{
					if(to_filter_data == null)
						to_filter_data = option.innerHTML.replace('&amp;','&');
					else
						to_filter_data = to_filter_data + '#Hash;# ' + option.innerHTML.replace('&amp;','&');
				}
			}
		}
		//alert(to_filter_data);
		if(document.getElementById(fld) != null)
			document.getElementById(fld).value=to_filter_data;
		//alert(document.getElementById('to_filter_data').value);
	}		
}

function selectAll(val){
	if(val == 1){
		if(document.getElementById('from_select_filter_data') != null){
			var select = document.getElementById('from_select_filter_data');
			for(var i=0; i<select.options.length; i++){
				var option = select.options[i];
				option.selected = 'selected';
			}
		}
	}else{
		if(document.getElementById('to_select_filter_data') != null){
			var select = document.getElementById('to_select_filter_data');
			for(var i=0; i<select.options.length; i++){
				var option = select.options[i];
				option.selected = 'selected';
			}
		}
	}	
}

function showExportPopup(){
	showPopUp("popupExport", "600", "550");
	if(document.getElementById('exporttype_0') != null){
		document.getElementById('exporttype_0').checked='checked';
		if(document.getElementById('outputTypeDiv') != null && document.getElementById('outputtype_1') != null){
			document.getElementById('outputTypeDiv').style.display='block';
			document.getElementById('outputtype_1').checked='checked';
		}
	}
}

function swapOutputType(id){
	if(document.getElementById(id) != null){
		if(id=='exporttype_0' && document.getElementById('outputTypeDiv') != null){
			document.getElementById('outputTypeDiv').style.display='block';
		}else if(id='exporttype_1' && document.getElementById('outputTypeDiv') != null){
			document.getElementById('outputTypeDiv').style.display='none';
		}	
	}
}

function getSelectedGroupBySearch(){
	if(document.getElementById('groupby') == null)
		return;
	
	var groupby = '';	
	var len = document.forms[0].elements.length;
	var count = 0;
	for (var i=0; i<len; i++) {
		var n = document.forms[0].elements[i].name;
		if (n != null) {
			if (n.indexOf("groupby_") > -1) {
				if (document.getElementById(n).checked) {
					if(groupby=='')
						groupby = document.getElementById(n).value;
					else
						groupby = groupby + ',' + document.getElementById(n).value;
				}
			}
		}
	}
	//alert(groupby);
	document.getElementById('groupby').value = groupby;			
}

function loadManageReportTemplate1(){
	/*if(document.getElementById('checkAllGroupBySearch') != null && document.getElementById('checkAllGroupBySearch').value=='Y'){
		document.getElementById('checkAllGroupBySearch').value='';
		return;
	}*/
	
	if(document.getElementById('groupby')!=null){	
		var formObject = document.forms[0];
		var groupby = document.forms[0].groupby.value;
		var groupbyArray = new Array();
		groupbyArray = groupby.split(",");
		for (var i = 0; i < formObject.length; i++) {
			var n = formObject.elements[i].name;
			if (n != null) {
				if (n.indexOf("groupby_") > -1) {
					if (groupbyArray != null && groupbyArray.length > 0) {
						for (var j = 0; j < groupbyArray.length; j++) {
							document.getElementById(n).checked = false;
						}
					}
				}
			}
		}
	 	
	 	//alert(groupby);
		var count = 0;
		for (var i = 0; i < formObject.length; i++) {
			var n = formObject.elements[i].name;
			if (n != null) {
				if (n.indexOf("groupby_") > -1) {
					if (groupbyArray != null && groupbyArray.length > 0) {
						for (var j = 0; j < groupbyArray.length; j++) {
							if (formObject.elements[i].value == groupbyArray[j]) {
								document.getElementById(n).checked = true;
							}
						}
					}
				}
			}
		}
	}	
	
	if(document.getElementById('outputcolumns')!=null){
		var formObject = document.forms[0];
		var outputcolumns = document.forms[0].outputcolumns.value;
		var outputcolumnsArray = new Array();
		outputcolumnsArray = outputcolumns.split(",");
		for (var i = 0; i < formObject.length; i++) {
			var n = formObject.elements[i].name;
			if (n != null) {
				if (n.indexOf("outputcolumn_") > -1) {
					if (outputcolumnsArray != null && outputcolumnsArray.length > 0) {
						for (var j = 0; j < outputcolumnsArray.length; j++) {
							document.getElementById(n).checked = false;
						}
					}
				}
			}
		}
	 	
	 	//alert(groupby);
		var count = 0;
		for (var i = 0; i < formObject.length; i++) {
			var n = formObject.elements[i].name;
			if (n != null) {
				if (n.indexOf("outputcolumn_") > -1) {
					if (outputcolumnsArray != null && outputcolumnsArray.length > 0) {
						for (var j = 0; j < outputcolumnsArray.length; j++) {
							if (formObject.elements[i].value == outputcolumnsArray[j]) {
								document.getElementById(n).checked = true;
							}
						}
					}
				}
			}
		}
	}	
	
}

function getSelectedOutputColumns(){
	if(document.getElementById('outputcolumns') == null)
		return;
		
	var outputcolumn = '';	
	var len = document.forms[0].elements.length;
	var count = 0;
	for (var i=0; i<len; i++) {
		var n = document.forms[0].elements[i].name;
		if (n != null) {
			if (n.indexOf("outputcolumn_") > -1) {
				if (document.getElementById(n).checked) {
					if(outputcolumn=='')
						outputcolumn = document.getElementById(n).value;
					else
						outputcolumn = outputcolumn + ',' + document.getElementById(n).value;
				}
			}
		}
	}
	//alert(outputcolumn);
	document.getElementById('outputcolumns').value = outputcolumn;			
}

function isPopupSearchColumnFound(fld){
	if(document.getElementById('popupsearchcolumns') == null)
		return false;
		
	var popupsearchcolumns = document.getElementById('popupsearchcolumns').value;
	var popupsearchcolumnsArray = popupsearchcolumns.split(",");
	for(var i=0; i<popupsearchcolumnsArray.length; i++){
		var key = 'ajax_field_'+popupsearchcolumnsArray[i];
		if(key == fld)
			return true;
	}
	
	return false;
}

function getMultipleSelectedValues1(obj){
	var data = null;
	if(obj.options != null && obj.options.length > 0){
		for(var i=0; i<obj.options.length; i++){
			if(obj.options[i].selected){
				//alert(data);
				if(data == 'All'){
					obj.value = '';
					return 'All';
				}	
					
				if(data == null){
					if(obj.options[i].innerHTML == 'Select')
						data = 'All';
					else	
						data = obj.options[i].innerHTML.replace('&amp;','&') + "=" + obj.options[i].value;
				}else{
					if(obj.options[i].innerHTML == 'Select')
						data = 'All';
					else
						data = data + '#Hash;# ' + obj.options[i].innerHTML.replace('&amp;','&') + "=" + obj.options[i].value;
				}	
				//alert(data);	 
			}	
		}
	}
	return data;
}

function managereportajaxpageblocklink(dynakeys, dynavalues, inetmethod, page, containerid, currentfld){
//alert(dynakeys + " ---- " + dynavalues);
	var page_request;
	try{ 
		if(window.XMLHttpRequest){ // if Mozilla, Safari etc
			page_request = new XMLHttpRequest()
		}else if(window.ActiveXObject){ // if IE
			try {
				//alert('2');
				page_request = new ActiveXObject("Msxml2.XMLHTTP")
			}catch (e){
				try{
					//alert('3');
					page_request = new ActiveXObject("Microsoft.XMLHTTP")
				}catch (e){ 
					alert(e);
				}
			}
		}else{
			//alert('5');
			return false;
		}
		
		var submitUrl = parseElement(document.forms[0]);
    	//alert(submitUrl);
    	var queryst = submitUrl;
    	
		//queryst = queryst + 'dynaKeys='+dynakeys+'inet_method|datadisplayid|'  +'&dynaValues='+dynavalues+inetmethod+'|'+containerid+'|' +'&ajaxpage='+page + '&popupsearch_data'+'='+(document.getElementById('popupsearch_data')!=null ? document.getElementById('popupsearch_data').value : '$')+'&'+currentfld+'='+(document.getElementById(currentfld)!=null?document.getElementById(currentfld).value:'$');
		queryst = queryst + 'dynaKeys='+dynakeys+'inet_method|datadisplayid|'  +'&dynaValues='+dynavalues+inetmethod+'|'+containerid+'|' +'&ajaxpage='+page + '&popupsearch_data'+'='+(document.getElementById('popupsearch_data')!=null ? document.getElementById('popupsearch_data').value : '$');
		var url = queryst;
		
		//alert(page_request);
		page_request.onreadystatechange=function(){
			managereportloadpage(page_request, containerid,page,inetmethod)
		}
	
		//alert(url);
			
		//Added Session Handling Code
		var doVar = "test.do";
		//alert(jsessionid);
		if(document.getElementById('jsessionid') != null){
			doVar = doVar + ";jsessionid="+document.getElementById('jsessionid').value;
		}
		
		page_request.open('POST', doVar, true);
		//Ended Code
		
		page_request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");	
			
		page_request.send(url);
		//alert('6');
	}catch (e){ 
		alert(e);
	}
}

function managereportrightclickmenu(dynakeys, dynavalues, inet_method, page, containerid){ 
	var page_request;
 	var queryst="";
   	try{ 
		if(window.XMLHttpRequest){ // if Mozilla, Safari etc
			page_request = new XMLHttpRequest()
		}else if(window.ActiveXObject){ // if IE
			try {
				//alert('2');
				page_request = new ActiveXObject("Msxml2.XMLHTTP")
			}catch (e){
				try{
					//alert('3');
					page_request = new ActiveXObject("Microsoft.XMLHTTP")
				}catch (e){ 
					alert(e);
				}
			}
		}else{
			//alert('5');
			return false;
		}
		
	    // hidding of the div based on the container id  
	    divhideshow(containerid);
	     
	    // based on the dynakey value the page is submitted 
	    disableBody();  
	  	queryst ='dynaKeys='+dynakeys+'ajaxpage|&dynaValues='+dynavalues +'ajaxpage|&ajaxpage='+page + '&inet_method='+inet_method;
	  	
	  	var url =  queryst; //'ComponentProcessServlet.do?'+queryst;
	  	//alert('33');
	  	//alert(page_request + containerid + page + inet_method);
	  	page_request.onreadystatechange=function(){
			managereportloadpage(page_request, containerid,page,inet_method)
		}
		
		//Added Session Handling Code
		var doVar = "test.do";
		//alert(jsessionid);
		if(document.getElementById('jsessionid') != null){
			doVar = doVar + ";jsessionid="+document.getElementById('jsessionid').value;
		}
		
		page_request.open('POST', doVar, true);
		//Ended Code
		
		page_request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");	
		//alert(url);		
		page_request.send(url);
	}catch (e){ 
		alert(e);
	}
}
   
function managereportloadpage(page_request, containerid, page, inet_method){
	//alert('inside............');
	if (page_request.readyState == 4 && ((page_request && page_request.status==200) || window.location.href.indexOf("http")==-1)){
		//alert('inside if   ' + containerid);
		//alert('response....'+page_request);
		if(containerid.length >= 3){
			var str = containerid.substring(0,7);
			enableBody(containerid);
			//alert(str);	
			if(str == "ajaxxml"){
				//alert('xml found =  ' + document.getElementById(containerid));
				document.getElementById(containerid).innerHTML = '';
				//alert(page_request);
				var xml = page_request.responseXML;
				//alert(xml);
				var nodes = xml.getElementsByTagName(containerid);
				//alert(nodes.length); 
				var node, nodevalue;
				for(i=0; i <nodes.length; i++) {
					//alert('in');
					node = nodes[i];
					if(node != null){
						nodevalue = node.firstChild.nodeValue;
						//alert(nodevalue);
					}
					nodevalue = nodevalue.replace(/ /g, '&nbsp;');
					//alert(nodevalue);
					var nodeValueKey = nodevalue.substring(0, nodevalue.indexOf("="));
					//alert(nodeValueKey);
					nodevalue = nodevalue.substring(nodevalue.indexOf("=")+1, nodevalue.length);
					
					var fieldid = 'to_select_filter_data';//containerid;
					
					var search = "this.innerHTML,\'" + containerid + "\',\'" + fieldid + "\',\'" + nodevalue + "\'";
					//alert(search);
					var suggest = '<div onmouseover="javascript:suggestOver(this);"';
					suggest += 'onmouseout="javascript:suggestOut(this);"';
					suggest += 'onclick="javascript:managereportsetSearch('+search+');"';
					suggest += 'class="suggest_link">' + nodeValueKey + '</div>';
					
					//alert(suggest);
					document.getElementById(containerid).innerHTML += suggest;
				}//ending for loop
				
				if(nodes.length > 0){
					if(document.getElementById(containerid))
						document.getElementById(containerid).style.display = "block";
				}
                //alert(document.getElementById(containerid).innerHTML)                   
				return;
			}
			
			document.getElementById(containerid).style.display='block';
			
			//alert(document.getElementById(containerid).innerHTML);
			//alert("page-----"+page_request.responseText);
			document.getElementById(containerid).innerHTML=page_request.responseText;
		}
	}
	
	loadFilterHierarchy();
}

function managereportsetSearch(key, containerid, tofieldid, value) {
	//alert(key + ' = ' + containerid + ' = ' + tofieldid)
	if(key != null){
		key = key.replace(/&amp;/g,'&');
		key = key.replace(/&nbsp;/g,' ');
		//alert(document.getElementById(containerid));
		document.getElementById(containerid).innerHTML = '';
		document.getElementById(containerid).style.display='none';
		
		if(document.getElementById('popupsearch_data') != null){
			document.getElementById('popupsearch_data').focus();
			document.getElementById('popupsearch_data').value='';
		}
		
		var fieldid = null;
		if(containerid.indexOf("_")!=-1){
			fieldid = containerid.substring(containerid.lastIndexOf("_")+1, containerid.length);
		}
		//alert(fieldid);
		if(document.getElementById(fieldid) != null){
			document.getElementById(fieldid).focus();
			document.getElementById(fieldid).value='';
		}
		if(document.getElementById('ajax_field_'+fieldid) != null){
			document.getElementById('ajax_field_'+fieldid).focus();
			document.getElementById('ajax_field_'+fieldid).value='';
		}
	}
	
	if(document.getElementById(tofieldid) != null){
		var select = document.getElementById(tofieldid);
		var isDataFound = false;
		for(var i=0; i<select.options.length; i++){
			var option = select.options[i];
			if(option.value == key){
				isDataFound = true;
				break;
			}	
		}
		
		//alert(select.options.length);
		if(!isDataFound)
			select.options[select.options.length] = new Option(key, value);
	}
}

function resetSelectedToSelectFilterData(){
	if(document.getElementById('to_select_filter_data') != null){
		var select = document.getElementById('to_select_filter_data');
		select.options.length=0;
	}
}

function loadManageReportDataTemplate(){
	//alert(document.getElementById('isgraph').value);
	if(document.getElementById('isgraph') != null && document.getElementById('isgraph').value == 'Y'){
		loadGraphFromAJAX();
		showPopUp("popupGraph", "600", "550");
	}	
}

function loadGraphFromAJAX(){
	//alert('calling');
	var xhr = new XMLHttpRequest();
	var url = document.getElementById('graphlink') != null ? document.getElementById('graphlink').value : null;
	if(url != null && url != ''){
		if(document.getElementById('graphtype') != null && document.getElementById('graphtype').value != ''){
			url = url + '&graphtype='+document.getElementById('graphtype').value;
		}
	}/*else{
		url = 'graph;jsessionid='+document.getElementById('jsessionid').value+'&rnum='+Math.floor((Math.random() * 100000000) + 1)+'&graphID=graphTest';
		//url = 'graph?jsessionid=0CC358AB482FF5809EAC8DEA0E271A8E&rnum=-294467457215959639&graphID=graphTest';
	}*/
	//alert('URL : '+url);
	xhr.open('GET', url, true);
	
	xhr.onreadystatechange=function(){
  		if(xhr.readyState === 4 && (xhr.status === 200 || xhr.status === 0)){
  			var responseText = xhr.responseText;
  			//alert(responseText);
  			var jsonString = responseText.substring(0, responseText.indexOf("#PARAMETERS#"));
  			//alert(jsonString);
    		var dataFromJSON = JSON.parse(jsonString);
    		//var dataFromJSON = xhr.responseText;
			//alert(dataFromJSON);
    		
    		var parameters = responseText.substring(responseText.indexOf("#PARAMETERS#=")+13, responseText.length);
    		//alert(parameters);
    		drawGraph(dataFromJSON, parameters);

  		}else if( xhr.readyState === 4 && xhr.status !== 200){
    		document.getElementById('graph_div').innerHTML = "Error loading data: " + xhr.statusText;
  		} else {
     		document.getElementById('graph_div').innerHTML = "Generating graph... "
      	}
	};
	//alert('calling22');
	xhr.send(null);
}

function drawGraph(dataObject, parameters) {
	//alert(dataObject);
    var data = new google.visualization.DataTable();
    //alert(data);
    /*data.addColumn("string", "Year");
	data.addColumn("number", "Sales");
	data.addColumn("number", "Expenses");
	*/
	var title = '';
	var graphtype = '';
	var xaxislabel = '';
	var yaxislabel = '';
	var barcolors = '';
	var graphdatatablecolumns = '';
	var is3D = '';
	var isStacked = '';
	var height = '';
	var width = '';
	
	var parametersArray = parameters.split("#START#");
	//alert(parametersArray.length);
	for(var i=0; i<parametersArray.length; i++){
		var row = parametersArray[i];
		//alert(row);
		if(row == null || row == '')
			continue;
			
		var name = row.substring(0, row.indexOf("="));
		var value = row.substring(row.indexOf("=")+1, row.length);
		//alert(name + ' ' + type);
		//data.addColumn(type, name);
		
		if(name == null)
			continue;
			
		if(name == 'TITLE')	
			title = value;
		else if(name == 'GRAPHTYPE')	
			graphtype = value;
		else if(name == 'XAXISLABEL')	
			xaxislabel = value;
		else if(name == 'YAXISLABEL')	
			yaxislabel = value;
		else if(name == 'BARCOLORS')	
			barcolors = value;				
		else if(name == 'GRAPHDATATABLECOLUMNS')	
			graphdatatablecolumns = value;	
		else if(name == 'IS3D')	
			is3D = value;
		else if(name == 'ISSTACKED')	
			isStacked = value;
		else if(name == 'HEIGHT')	
			height = value;	
		else if(name == 'WIDTH')	
			width = value;					
	}
			    
    //var graphdatatablecolumns = document.getElementById('graphdatatablecolumns') != null ? document.getElementById('graphdatatablecolumns').value : null;
    //alert(graphdatatablecolumns);
    //alert(title + ' ' + graphtype + ' ' + xaxislabel + ' ' + yaxislabel + ' ' + barcolors + ' ' + graphdatatablecolumns);
    
    var formatter = new google.visualization.NumberFormat({negativeColor: 'red', negativeParens: true, pattern: '$###,###'});
    
    var graphdatatablecolumnsArray = new Array();
	graphdatatablecolumnsArray = graphdatatablecolumns.split("|");
    //alert(graphdatatablecolumnsArray);
    
	for(var i=0; i<graphdatatablecolumnsArray.length; i++){
		var row = graphdatatablecolumnsArray[i];
		//alert(row);
		var name = row.substring(0, row.lastIndexOf("="));
		var type = row.substring(row.lastIndexOf("=")+1, row.length);
		//alert(name + ' ' + type);
			
		data.addColumn(type, name);
		
		if(type == 'number')//to apply currency format on column
			formatter.format(data, i);
	}
	
	data.addRows(dataObject);
   				
	
   	//formatter.format(data, 1);
   	//formatter.format(data, 2);
   	
   	/*var view = new google.visualization.DataView(data);
      view.setColumns([0, 1,
                       { calc: "stringify",
                         sourceColumn: 1,
                         type: "number",
                         role: "annotation" },
                       2]);
    */                      				
	//var options = document.getElementById('graphoptions') != null ? document.getElementById('graphoptions').value : null;
	//options = '{'+options+'}';
	//alert(options);
	//var options1 = {
	var options = {
  		title: title,
  		//title: document.getElementById('graphtitle').value,
  		//subtitle:'Marks Per Subject',
        width: (width == ' ' ? 800 : width),
        height: (height == ' ' ? 400 : height),
        is3D: (is3D == ' ' ? false : true),
        isStacked: (isStacked == ' ' ? false : true),
        
        //vAxis:{title:'Sale/Expenses',titleTextStyle: {color: 'red', bold:true, fontSize: 20}},
        vAxis: {title: yaxislabel,titleTextStyle: {color: 'black', bold:true, fontSize: 12}},
        //ticks: [0, 500, 1000, 1500, 2000, 2500, 3000, 3500, 4000, 4500, 5000]
        //hAxis:{title:'Month',titleTextStyle: {color: 'red', bold:true, fontSize: 20}},
        hAxis: {title: xaxislabel,titleTextStyle: {color: 'black', bold:true, fontSize: 12}},
        bar: {groupWidth: '45%'},
        //tooltip: { trigger: 'none' },
  		legend: {position:'right', textStyle: {color: 'black', fontSize: 12}},
       
        //backgroundColor:'pink',
        //colors: ['red', 'black', 'black', 'purple']
    };
    //alert('drawing')
    // Create and draw the visualization.
    if(graphtype != null){
    	if(graphtype=='bar')
    		new google.visualization.ColumnChart(document.getElementById('graph_div')).draw(data, options);
     	else if(graphtype=='pie')	
    		new google.visualization.PieChart(document.getElementById('graph_div')).draw(data, options);
    	else if(graphtype=='line')	
    		new google.visualization.LineChart(document.getElementById('graph_div')).draw(data, options);
    	else if(graphtype=='geo')	
    		new google.visualization.GeoChart(document.getElementById('graph_div')).draw(data, options);	
    		
    	if(document.getElementById('graphtype') != null)
    		document.getElementById('graphtype').value = graphtype;		
    }else{
    	new google.visualization.ColumnChart(document.getElementById('graph_div')).draw(data, options);
    }
 }
 
 function loadGraphFromAJAXIndex(id){
 	if(document.getElementById('graph_div'+id) == null)
 		return;
 		
	//alert('calling');
	var xhr = new XMLHttpRequest();
	var url = document.getElementById('graphlink'+id) != null ? document.getElementById('graphlink'+id).value : null;
	if(url != null && url != ''){
		if(document.getElementById('graphtype'+id) != null && document.getElementById('graphtype'+id).value != ''){
			url = url + '&graphtype='+document.getElementById('graphtype'+id).value;
		}
	}/*else{
		url = 'graph;jsessionid='+document.getElementById('jsessionid').value+'&rnum='+Math.floor((Math.random() * 100000000) + 1)+'&graphID=graphTest';
		//url = 'graph?jsessionid=0CC358AB482FF5809EAC8DEA0E271A8E&rnum=-294467457215959639&graphID=graphTest';
	}*/
	//alert(url);
	url = url + "&" + parseElement(document.forms[0]);
	//alert(url);
	xhr.open('GET', url, true);
	
	xhr.onreadystatechange=function(){
  		if(xhr.readyState === 4 && (xhr.status === 200 || xhr.status === 0)){
  			var responseText = xhr.responseText;
  			//alert(responseText);
  			if(responseText == '' || responseText == 'No Data Found'){
  				document.getElementById('graph_div'+id).innerHTML = 'No Data Found';
  				return;
  			}
  			var jsonString = responseText.substring(0, responseText.indexOf("#PARAMETERS#"));
  			//alert(jsonString);
    		var dataFromJSON = JSON.parse(jsonString);
    		//var dataFromJSON = xhr.responseText;
			//alert(dataFromJSON);
    		
    		var parameters = responseText.substring(responseText.indexOf("#PARAMETERS#=")+13, responseText.length);
    		//alert(parameters);
    		drawGraphIndex(dataFromJSON, parameters, id);
  		}else if( xhr.readyState === 4 && xhr.status !== 200){
    		document.getElementById('graph_div'+id).innerHTML = "Error loading data: " + xhr.statusText;
  		} else {
     		document.getElementById('graph_div'+id).innerHTML = "Generating graph... "
      	}
	};
	//alert('calling22');
	xhr.send(null);
}

function drawGraphIndex(dataObject, parameters, id) {
	var data = new google.visualization.DataTable();
   
	var title = '';
	var graphtype = '';
	var xaxislabel = '';
	var yaxislabel = '';
	var barcolors = '';
	var graphdatatablecolumns = '';
	var is3D = '';
	var isStacked = '';
	var height = '';
	var width = '';
	var haxisformat = '';
	var vaxisformat = '';
	
	var parametersArray = parameters.split("#START#");
	for(var i=0; i<parametersArray.length; i++){
		var row = parametersArray[i];
		//alert(row);
		if(row == null || row == '')
			continue;
			
		var name = row.substring(0, row.indexOf("="));
		var value = row.substring(row.indexOf("=")+1, row.length);
		if(name == null)
			continue;
			
		if(name == 'TITLE')	
			title = value;
		else if(name == 'GRAPHTYPE')	
			graphtype = value;
		else if(name == 'XAXISLABEL')	
			xaxislabel = value;
		else if(name == 'YAXISLABEL')	
			yaxislabel = value;
		else if(name == 'BARCOLORS')	
			barcolors = value;				
		else if(name == 'GRAPHDATATABLECOLUMNS')	
			graphdatatablecolumns = value;	
		else if(name == 'IS3D')	
			is3D = value;
		else if(name == 'ISSTACKED')	
			isStacked = value;
		else if(name == 'HEIGHT')	
			height = value;	
		else if(name == 'WIDTH')	
			width = value;
		else if(name == 'HAXISFORMAT')	
			haxisformat = value;
		else if(name == 'VAXISFORMAT')	
			vaxisformat = value;							
	}
			    
    var formatter = new google.visualization.NumberFormat({negativeColor: 'red', negativeParens: true, pattern: '$###,###'});
    //alert(graphdatatablecolumns)
    var graphdatatablecolumnsArray = new Array();
	graphdatatablecolumnsArray = graphdatatablecolumns.split("|");
     
	for(var i=0; i<graphdatatablecolumnsArray.length; i++){
		var row = graphdatatablecolumnsArray[i];
		//alert(row);
		var name = row.substring(0, row.lastIndexOf("="));
		var type = row.substring(row.lastIndexOf("=")+1, row.length);
		//alert(name + ' ' + type);
			
		data.addColumn(type, name);
		
		if(type == 'number')//to apply currency format on column
			formatter.format(data, i);
	}
		
	data.addRows(dataObject);
   	
	var options = {
  		title: title,
  		//title: document.getElementById('graphtitle').value,
  		//subtitle:'Marks Per Subject',
        width: (width == ' ' ? 1000 : width),
        height: (height == ' ' ? 500 : height),
        is3D: (is3D == ' ' ? false : true),
        isStacked: (isStacked == ' ' ? false : true),
        
        keepAspectRatio: 'false', //to use given height and width parameters value
        //vAxis:{title:'Sale/Expenses',titleTextStyle: {color: 'red', bold:true, fontSize: 20}},
        vAxis: {title: yaxislabel,titleTextStyle: {color: 'black', bold:true, fontSize: 12}, format: vaxisformat},
        //ticks: [0, 500, 1000, 1500, 2000, 2500, 3000, 3500, 4000, 4500, 5000]
        //hAxis:{title:'Month',titleTextStyle: {color: 'red', bold:true, fontSize: 20}},
        hAxis: {title: xaxislabel,titleTextStyle: {color: 'black', bold:true, fontSize: 12}, format: haxisformat},
        bar: {groupWidth: '35%'},
        //tooltip: { isHtml: true},
  		legend: 'bottom',//{position:'right', textStyle: {color: 'black', fontSize: 12}},
  		colorAxis: {colors: ['#2c64a8', '#ce3628', '#f6c627', '#854e99', '#61b549', '#e9682b', '#32b9bb', 'eaeb6c']},
  		//backgroundColor: '#81d4fa',
        datalessRegionColor: 'fffac2',
        //defaultColor: 'yellow',
        displayMode: 'regions',
        //region:'IT',
      	region: 'US',
        //forceIFrame: true,
       	resolution : 'provinces',
       	//markerOpacity: 3.0
        //backgroundColor:'pink',
        //colors: ['red', 'black', 'black', 'purple']
        enableRegionInteractivity: 'true',
        //sizeAxis: {minSize:2,  maxSize: 2},
    };
    //alert('data')
    // Create and draw the visualization.
    if(graphtype != null){
    	if(graphtype=='bar')
    		new google.visualization.BarChart(document.getElementById('graph_div'+id)).draw(data, options);
     	else if(graphtype=='pie')	
    		new google.visualization.PieChart(document.getElementById('graph_div'+id)).draw(data, options);
    	else if(graphtype=='line')	
    		new google.visualization.LineChart(document.getElementById('graph_div'+id)).draw(data, options);
    	else if(graphtype=='geo')	
    		new google.visualization.GeoChart(document.getElementById('graph_div'+id)).draw(data, options);	
    	else if(graphtype=='column')	
    		new google.visualization.ColumnChart(document.getElementById('graph_div'+id)).draw(data, options);
    			
    	if(document.getElementById('graphtype'+id) != null)
    		document.getElementById('graphtype'+id).value = graphtype;		
    }else{
    	new google.visualization.ColumnChart(document.getElementById('graph_div'+id)).draw(data, options);
    }
 }
 
 function loadHomeActionGraphs(){
 	loadGraphFromAJAXIndex(1);
 	
 }
 function loadAgencyAdminDashboardGraphs(){
 	loadGraphFromAJAXIndex(1);
 	loadGraphFromAJAXIndex(2);
 }
 
 function loadFilterHierarchy(){
	 //alert(1)
	 var hierarchycolumns = document.getElementById('hierarchycolumns') != null ? document.getElementById('hierarchycolumns').value : '';
	 var hierarchycolumnsArray = new Array();
	 hierarchycolumnsArray = hierarchycolumns.split(",");
	 
	 for(var i=0; i<hierarchycolumnsArray.length; i++){
		 var column = hierarchycolumnsArray[i];
		 
		 loadFilterHierarchy1(column);
	 }
 }
 
 function loadFilterHierarchy1(column){
	var ctxurl = document.getElementById('contexturl') != null ? document.getElementById('contexturl').value : '';
	//var columnsToBeDisplayedOnHierarchyList = document.getElementById('columnsToBeDisplayedOnHierarchyList') != null ? document.getElementById('columnsToBeDisplayedOnHierarchyList').value : '';
	//var isSkipHeader = document.getElementById('isSkipHeader') != null ? document.getElementById('isSkipHeader').value : '';
	var reportID = document.getElementById('reportID') != null ? document.getElementById('reportID').value : '';
	
	//alert(ctxurl)
	$.ajax({
	    url : ctxurl + ';jsessionid='+document.getElementById('jsessionid').value + '?rnum=' + document.getElementById('randomnumber').value + '&hierarchyListName=hierarchyTree_list_1&inet_method=showManageReportHierarchy&column='+column+'&reportID='+reportID,
	    type : 'POST',
	   	dataType:'html',
	   	data : {
    	'numberOfWords' : 100
		},
	   	
	    success : function(data) {              
	        //alert('Data: '+document.getElementById('xmlMenuTree'));
	        document.getElementById('xmlMenuTree_'+column).innerHTML = data;
	        //alert('Data: '+data);
	        var table1 = $('#table1').tabelize({
			/*onRowClick : function(){
				alert('test');
			}*/
			fullRowClickable : false,
			onReady : function(){
				//console.log('ready');
			},
			onBeforeRowClick :  function(){
				//console.log('onBeforeRowClick');
			},
			onAfterRowClick :  function(){
				//console.log('onAfterRowClick');
			},
		});
	        
	},
	    error : function(request,error)
	    {
	        //alert("Request: "+JSON.stringify(request));
	        alert(error);
	        //document.getElementById('xmlMenuTree').innerHTML = JSON.stringify(request);
	    }
	});
}