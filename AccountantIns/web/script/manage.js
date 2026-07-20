var existingFormSerializeObj="";

$( window ).load(function() {
  // Run code
	
	if($("#headerStatusDesc").text() != "") {
		$("*#headerStatusDescUpdated").attr("value", $("#headerStatusDesc").text());
		$("*#ZohoStatusDescUpdated").attr("value", $("#headerStatusDesc").text());
	}
	if($("#StatusDesc").text() != "") {
		$("*#headerStatusDescUpdated").attr("value", $("#StatusDesc").text());
		$("*#ZohoStatusDescUpdated").attr("value", $("#StatusDesc").text());
	}
	
	setTimeout(function(){ 
		if($("#headerStatusDesc").text() != "") {
			$("*#headerStatusDescUpdated").attr("value", $("#headerStatusDesc").text());
			$("*#ZohoStatusDescUpdated").attr("value", $("#headerStatusDesc").text());
		}
		if($("#StatusDesc").text() != "") {
			$("*#headerStatusDescUpdated").attr("value", $("#StatusDesc").text());
			$("*#ZohoStatusDescUpdated").attr("value", $("#StatusDesc").text());
		}
		existingFormSerializeObj = $.trim($("form").find("select,textarea, input").not('[type="hidden"]').not('[readonly="readonly"]').not('[disabled="disabled"]').serialize());
//		taalert(existingFormSerializeObj)
	}, 100);
});


window.history.forward();
// **** KEYPRESS DETECT CODE TO DETECT ENTER KEY PRESS ****
var IE5=document.all? 1:0 
if(!IE5) // Must capture the event if it is NS6.0 and NS4.0+ 
{ 
	document.captureEvents(Event.KEYPRESS) 
} 
document.onkeypress = testKey; // when a key is pressed perform testkey function 
function testKey(e) 
{ 
	/* Grab the ascii code of the key that was pressed */ 
	whKey = !IE5? e.which:event.keyCode; 
	
	if (whKey == 13){
	// *** PLACE CODE TO BE EXECUTED ON ENTER PRESS HERE
			//this code searches the document for a link with id attribute set to "submitForm" and if it finds it, evaluates the function in its href
			//link href must be of the format: href="javascript:function();"
			//note this will not simulate a click event on the link so it won't work with links that rely on the onClick() event
			for (var i=0; i<document.links.length; i++){
				if (document.links[i].id == "submitForm"){
					//window.location = document.links[i].href	//use this if you want to call a regular link instead of a js function
					eval(unescape(document.links[i].href))	//unescape is used to prevent spaces between function arguments from being escaped and causing a syntax error
				}
			}
	// *** END ENTER PRESS HANDLER CODE
	//to simply submit a form on enter press, use this code: document.formName.submit()
	}
}

//-------------manage submit form start--------------------------------------
function managesubmitformforupload(af_1, method, as_dynaKeys, as_dynaValues, action)
{
  	var queryst ="upload?dynaKeys="+as_dynaKeys+"&dynaValues="+as_dynaValues+"&inet_method="+method+"&inet_page="+action;
	var currentFormSerializeObj = $.trim($("form").find("select,textarea, input").not('[type="hidden"]').not('[readonly="readonly"]').not('[disabled="disabled"]').serialize());
	    var isDataChange = "";
	    if(currentFormSerializeObj != existingFormSerializeObj){
	   	 isDataChange = "Y";
	    } else {
	   	 isDataChange = "N";
	    }
	  queryst = queryst + "&isDataChange=" + isDataChange;
	var fileName =af_1.file_path.value;      
    
    af_1.action= queryst;
		af_1.submit();	
		
	 /*if(fileName==""){
		alert("Please select a Excel to upload.");
	 }
	  else{
		af_1.action= queryst;
		af_1.submit();		
	 }*/
      
}


function disableBody() {
	//alert('1');
	//window.scrollTo(0,0);
	//document.body.style.cursor='wait';
	//document.body.disabled = true;


	//document.body.pause();
	var cvr = document.getElementById("progressBarDiv");
	//alert(cvr);
	if (cvr != null) {
		cvr.style.display = "block";

		document.documentElement.style.overflow = document.body.style.overflow = "auto";
		var height = document.body.clientHeight;
		//showPopUp('cover1','50','50');
		if (document.body.style.overflow = "hidden") {
			cvr.style.width = "100%";
			cvr.style.height = "100%";
			//alert();
			document.getElementById("progressBarDiv").style.height = height + "px";
		}
	}

}

function enableBody(containerid) {

	//document.body.disabled = false;
	//document.body.style.cursor='default';

	//document.body.resume();

	var cvr = document.getElementById("progressBarDiv");
	//		alert(cvr);
	if (cvr != null) {
		//closePopUp('cover1');
		cvr.style.display = "none";

		document.documentElement.style.overflow = document.body.style.overflow = "";
		//var containerid = document.getElementById('divHideShow').value;
		//alert(containerid);

		if (containerid != null && containerid != '') {
			getElementForFocus(containerid);
		}
	}
}
function getElementForFocus(containerid) {
	if (document.getElementById(containerid) == null)
		return;

	if (document.getElementById(containerid).getElementsByTagName('') != null) {
		var fldArray = document.getElementById(containerid).getElementsByTagName('*');
		//alert(fldArray.length);

		var focusfield;
		for (var i = 0; i < fldArray.length; i++) {
			var fld = fldArray[i];
			if (fld.getAttribute("id") != null && document.getElementById(fld.getAttribute("id")) != null) {
				if (!document.getElementById(fld.getAttribute("id")).disabled &&
					(fld.type == 'text' ||
						fld.type == 'select-one' ||
						fld.type == 'select-one' ||
						fld.type == 'textarea' ||
						fld.type == 'radio' ||
						fld.type == 'checkbox' ||
						(fld.type == 'hidden' && fld.name == 'hiddenfocus'))) {

					//alert(fld.name + ' -- ' + fld.type);
					focusfield = fld.type;
					break;
					//if(i == 4)
					//	break;
				}
			}
		}

		if (focusfield == 'text' || focusfield == 'checkbox' || focusfield == 'radio' || focusfield == 'hidden')
			focusfield = 'input';
		else if (focusfield == 'select-one' || focusfield == 'select')
			focusfield = 'select';

		//alert(focusfield);
		fldArray = document.getElementById(containerid).getElementsByTagName(focusfield);
		for (var i = 0; i < fldArray.length; i++) {
			var fld = fldArray[i];
			if (fld.getAttribute("id") != null) {
				fld = document.getElementById(fld.getAttribute("id"));
				if (!document.getElementById(fld.getAttribute("id")).disabled) {
					fld.focus();
					return;
				}
			}
		}
		fldArray = document.getElementById(containerid).getElementsByTagName(focusfield);
		for (var i = 0; i < fldArray.length; i++) {
			var fld = fldArray[i];
			if (fld.getAttribute("id") != null) {
				fld = document.getElementById(fld.getAttribute("id"));
				if (!document.getElementById(fld.getAttribute("id")).disabled) {
					fld.focus();
					return;
				}
			}
		}
		fldArray = document.getElementById(containerid).getElementsByTagName(focusfield);
		for (var i = 0; i < fldArray.length; i++) {
			var fld = fldArray[i];
			if (fld.getAttribute("id") != null) {
				fld = document.getElementById(fld.getAttribute("id"));
				if (!document.getElementById(fld.getAttribute("id")).disabled) {
					fld.focus();
					return;
				}
			}
		}
	}
}

function manageshowdialogbox(frm,inet_method, dynakeys,dynavalues,page,containerid,nextpage)
{
	var url ="";
                //   alert(inet_method);
               if(inet_method =="EditSubList")
                 {
                 // alert("0");
                	   url ='test.do?inet_method='+inet_method+'&inet_page='+nextpage+'&dynaKeys='+dynakeys+'inet_previouspage|'+'&dynaValues='+dynavalues+page+'|';
                   
                }else
               {
               // alert("1");
             	   url ='test.do;jsessionid='+frm.jsessionid.value+'?inet_method='+inet_method+'&inet_page='+nextpage+'&dynaKeys='+dynakeys+'inet_previouspage|'+'&dynaValues='+dynavalues+page+'|';
               }
              
               // var url ='test.do?inet_method='+inet_method+'&inet_page='+nextpage+'&dynaKeys='+dynakeys+'inet_previouspage|'+'&dynaValues='+dynavalues+page+'|'; 
               // alert(url);
               
              // document.body.style.filter = true ? "filter: alpha(opacity=75);" : "" ;
       		var currentFormSerializeObj = $.trim($("form").find("select,textarea, input").not('[type="hidden"]').not('[readonly="readonly"]').not('[disabled="disabled"]').serialize());
      	    var isDataChange = "";
      	    if(currentFormSerializeObj != existingFormSerializeObj){
      	   	 isDataChange = "Y";
      	    } else {
      	   	 isDataChange = "N";
      	    }
      	  url = url + "&isDataChange=" + isDataChange;
      	  
              disableBody();
               //,dialogLeft:300px, dialogRight:300px,dialogTop:50px,dialogWidth:600px,dialogHeight:300px
                 // window.showModalDialog(url, '', 'dialogTop:100px;dialogWidth:700px;dialogHeight:370px;status:off;resizable:no;'); 
                  window.open(url);
                  //managesubmitpopupform(frm,inet_method,dynakeys, dynavalues,page);
                  
                  managesubmitformwithajax(frm,inet_method,dynakeys, dynavalues,page,containerid);
                  
                 enableBody(); 
                //  document.body.style.filter = true ? "alpha(opacity=100); -moz-opacity:100; opacity=100" : "" ;
                  //window.location.reload(true);
                  
}


function managesubmitformwithajax(frm,inet_method,dynakeys, dynavalues,page,containerid)
{
  // alert(dynakeys);
   //alert(dynavalues);
    var page_request= false;
    var queryst ='dynaKeys='+dynakeys+'popuppage|datadisplayid|'  +'&dynaValues='+dynavalues+'popuppage|'+containerid+'|&ajaxpage='+page+'&ajaxpage=ajaxpage';
	var url =  queryst;
	var currentFormSerializeObj = $.trim($("form").find("select,textarea, input").not('[type="hidden"]').not('[readonly="readonly"]').not('[disabled="disabled"]').serialize());
	    var isDataChange = "";
	    if(currentFormSerializeObj != existingFormSerializeObj){
	   	 isDataChange = "Y";
	    } else {
	   	 isDataChange = "N";
	    }
	  url = url + "&isDataChange=" + isDataChange;
	disableBody(); 
    retrieveURL1( url, containerid, page_request);
}

function managesubmitpopupform(af_1,method,as_dynaKeys, as_dynaValues,action)
{
     // alert('kk');
      af_1.dynaKeys.value = as_dynaKeys;
      af_1.dynaValues.value = as_dynaValues;
      af_1.inet_method.value = method;
      af_1.inet_page.value= action;
     af_1.method="post";
      af_1.submit();
      window.close();
}


//-------------------------------------------------------------  Runtime Script-----------------------------------------------------------------
  	var loadedobjects=""
	var rootdomain="http://"+window.location.hostname

   //----start of ajaxpage block link------------
	function manageajaxpageblocklink(frm, inetmethod , dynakeys, dynavalues, page, containerid)
	{
		//alert('manageajaxpageblocklink');
	 	var page_request ;
	 	
	 	dynavalues = dynavalues.replace("&","#AMP#");
	 	dynavalues = dynavalues.replace("&","#AMP#");
	 	dynavalues = dynavalues.replace("&","#AMP#");
		var queryst ='dynaKeys='+dynakeys+'inet_method|datadisplayid|'  +'&dynaValues='+dynavalues+inetmethod+'|'+containerid+'|' +'&ajaxpage='+page;
		
		//alert(queryst);
		//alert(inetmethod);
		//alert(document.getElementById("CountyDesc"));
		
		//divhideshow (containerid);
		
		//alert(document.getElementById("CountyDesc") + " 1 ");
		
		var currentFormSerializeObj = $.trim($("form").find("select,textarea, input").not('[type="hidden"]').not('[readonly="readonly"]').not('[disabled="disabled"]').serialize());
  	    var isDataChange = "";
  	    if(currentFormSerializeObj != existingFormSerializeObj){
  	   	 isDataChange = "Y";
  	    } else {
  	   	 isDataChange = "N";
  	    }
  	  queryst = queryst + "&isDataChange=" + isDataChange;
		 
		if(document.getElementById("Amount"))
		{
			var node = document.getElementById("Amount");
			queryst = queryst+"&"+ node.name + "=" + $.trim(node.value) ;		
		} 
		//alert(document.getElementById("CountyDesc") + " 2");
		if(document.getElementById("Amount_0"))
		{
			var node = document.getElementById("Amount_0");
			queryst = queryst+"&"+ node.name + "=" + $.trim(node.value) ;		
		}
		//alert(document.getElementById("CountyDesc") + " 3");
		if(document.getElementById("Amount_1"))
		{
			var node = document.getElementById("Amount_1");
			queryst = queryst+"&"+ node.name + "=" + $.trim(node.value) ;
		}
		if(document.getElementById("Amount_2"))
		{
			var node = document.getElementById("Amount_2");
			queryst = queryst+"&"+ node.name + "=" + $.trim(node.value) ;
		}
		//alert(document.getElementById("CountyDesc") + " 4");
		if(document.getElementById("StateCode"))
		{
			var node = document.getElementById("StateCode");
			queryst = queryst+"&"+ node.name + "=" + $.trim(node.value) ;
		}
		if(document.getElementById("ajax_field_PolicyEffectiveDate"))
        {
              var node = document.getElementById("ajax_field_PolicyEffectiveDate");
             queryst = queryst+"&"+ node.name + "=" + $.trim(node.value) ;
             // alert(queryst);
        }
		if(document.getElementById("CompanyKey"))
        {
              var node = document.getElementById("CompanyKey");
              queryst = queryst+"&"+ node.name + "=" + $.trim(node.value);
        }
		if(document.getElementById("CountyDesc"))
        {
        		//alert("CountyDesc");
              var node = document.getElementById("CountyDesc");
              var county = node.value;
              county = county.replace("&","#AMP#");
              queryst = queryst+"&"+ node.name + "=" + $.trim(county);
              //alert(queryst);
        }
        if(document.getElementById("ajax_field_CountyDesc"))
        {
        	  //alert("CountyDesc");
              var node = document.getElementById("ajax_field_CountyDesc");
              var county = node.value;
              county = county.replace("&","#AMP#");
              queryst = queryst+"&"+ node.name + "=" + $.trim(county);
              //alert(queryst);
        }
		if(document.getElementById("searchByCountyDesc")){
			var node = document.getElementById("searchByCountyDesc");
            var county = node.value;
            county = county.replace("&","#AMP#");
            queryst = queryst+"&"+ node.name + "=" + $.trim(county);
			
		}
		if(document.getElementById("ProducerCode") && inetmethod == 'getProducerName'){
			
			var node = document.getElementById("ProducerCode");
            var ProducerCode = node.value;
            ProducerCode = ProducerCode.replace("&","#AMP#");
            queryst = queryst+"&"+ node.name + "=" + $.trim(ProducerCode) ;
            //alert(queryst);
		}
		if(document.getElementById("AccountNamesearch")){
			var node = document.getElementById("AccountNamesearch");
            var account = node.value;
            account = account.replace("&","#AMP#");
            queryst = queryst+"&"+ node.name + "=" + $.trim(account);
			
		}
		if(document.getElementById("WQAccountEmailSearch")){
			var node = document.getElementById("WQAccountEmailSearch");
            var account = node.value;
            account = account.replace("&","#AMP#");
            queryst = queryst+"&"+ node.name + "=" + $.trim(account);
			
		}
		if(document.getElementById("WQQuoteNumberSearch")){
			var node = document.getElementById("WQQuoteNumberSearch");
            var account = node.value;
            account = account.replace("&","#AMP#");
            queryst = queryst+"&"+ node.name + "=" + $.trim(account);
			
		}
		if(document.getElementById("WQPolicyNumberSearch")){
			var node = document.getElementById("WQPolicyNumberSearch");
            var account = node.value;
            account = account.replace("&","#AMP#");
            queryst = queryst+"&"+ node.name + "=" + $.trim(account);
			
		}
		if(document.getElementById("AccountNameSearch")){
			var node = document.getElementById("AccountNameSearch");
            var accountName = node.value;
            accountName = accountName.replace("&","#AMP#");
            queryst = queryst+"&"+ node.name + "=" + $.trim(accountName);
			
		}
		if(document.getElementById("AccountEmailSearch")){
			var node = document.getElementById("AccountEmailSearch");
            var accountName = node.value;
            accountName = accountName.replace("&","#AMP#");
            queryst = queryst+"&"+ node.name + "=" + $.trim(accountName);
			
		}
		if(document.getElementById("WQAccountEmailSearch") || document.getElementById("WQPolicyNumberSearch") || document.getElementById("WQQuoteNumberSearch") || document.getElementById("AccountNamesearch") || document.getElementById("AccountNameSearch") || document.getElementById("AccountEmailSearch")){
			var fieldid = containerid;
			fieldid = fieldid.substring(containerid.indexOf("_")+1);
			if(fieldid == 'AccountNameSearch'){
				fieldid += "MA";
			}
			queryst = queryst+"&"+ "SEARCHTYPE=" + fieldid;
		}
		//alert(queryst); 
		
		if(inetmethod =="showquotediv")
		{
			queryst = queryst + parseIsQuoteSelected(frm);
		}
		 if(inetmethod !="refreshLimits"){ 
			 
			disableBody();
		 }
		 /*if(inetmethod == "refreshLimits"){
			 
			 queryst = queryst+"&skipView=Y";
			 
		 }*/
		if(inetmethod == "delete" && page == "feeSuitSupplement")
		{
			queryst = queryst + "&" + parseElement(frm);
		}
		
		if(inetmethod == "delete" && page == "serverTrusteeSupp")
		{
			queryst = queryst + "&" + parseElement(frm);
		}
		
		if(inetmethod == "delete" && page == "firmRenderedServiceSupp")
		{
			queryst = queryst + "&" + parseElement(frm);
		}
		if(containerid ==  'insuredEmailZoho' )
        {
			queryst += $("#insuredEmailZoho :input").serialize();
        }
		 
		var url = queryst;
		//alert(url);
		//Added Session Handling Code 
    	retrieveURL1( url, containerid, page_request,document.forms[0].jsessionid.value);   
    	
    	
    	 	
    	
	}
  //----end of ajaxpage block link---------------
  
  function saveLimit()
  {
      
  }
  
  function get_radio_value()
	{
		for (var i=0; i < document.myForm.IsClaimExpensesType.length; i++)
   		{
   			if (document.myForm.IsClaimExpensesType[i].checked)
      			{
      				var rad_val = document.myForm.IsClaimExpensesType[i].value;
      				return rad_val;
      			}
   		}
    }
   function get_radio_value1()
	{
	    //alert(document.myForm.IsControlClientFunds.length);
		for (var i=0; i < document.myForm.IsControlClientFunds.length; i++)
   		{
   			if (document.myForm.IsControlClientFunds[i].checked)
      			{
      				var rad_val = document.myForm.IsControlClientFunds[i].value;
      				//alert(rad_val);
      				return rad_val;
      			}
   		}
    }
     function get_radio_value2()
	{
	    
		for (var i=0; i < document.myForm.IsFirmRenderedAuditOrAttestService.length; i++)
   		{
   			if (document.myForm.IsFirmRenderedAuditOrAttestService[i].checked)
      			{
      				var rad_val = document.myForm.IsFirmRenderedAuditOrAttestService[i].value;
      				return rad_val;
      			}
   		}
    }
    
 	function get_radio_value3()
	{
		for (var i=0; i < document.myForm.IsNonMonetaryCompensationRecieved.length; i++)
   		{
   			if (document.myForm.IsNonMonetaryCompensationRecieved[i].checked)
      			{
      				var rad_val = document.myForm.IsNonMonetaryCompensationRecieved[i].value;
      				return rad_val;
      			}
   		}
    }
  //-- start of manage right click menu-----------
	function managerightclickmenu(frm,  inet_method, dynakeys,dynavalues,page,containerid)
	{ 
	 
  		var page_request = false;
  		var starry = new Array();
  		var queryst ="";
  		var fieldno ="";

   		//populating the div postion id throw ctxmenu param  appening the primary key value to the container--------------------
  		var ctxmenuparamvalue = document.getElementsByName('ctxmenuparam').value;
  		 //alert(ctxmenuparamvalue);
		if(ctxmenuparamvalue){
			starry = ctxmenuparamvalue.split('|');
			 
	   		for(var i=0;i<starry.length;i++)
	  		{
	     		if(starry[i].length!=0)
	      		{
	         		if(i%2==1)
	         			dynakeys =dynakeys + starry[i]+'|';
	         		else{
	         		    dynavalues=dynavalues+ starry[i]+'|';
	        			}
	        	
		        	if(i==3)
		        	{
		        		if(dynavalues.match('menuAdd'))
		        		{
		         	 		containerid;
		         	 	}else{
		         	 	     fieldno = starry[2];
		         	 		containerid = containerid+"_"+fieldno;
		         	 	}
		         	}
	             }
	         }
		}
   // hidding of the div based on the container id  
     divhideshow (containerid);
     
    // based on the dynakey value the page is submitted 
    if(dynakeys.match('isajaxsubmit'))
    { 
    	disableBody();  
  		queryst ='dynaKeys='+dynakeys+'ajaxpage|&dynaValues='+dynavalues +'ajaxpage|&ajaxpage='+page;
  		var url =  queryst; //'ComponentProcessServlet.do?'+queryst;
  		//alert('33');
  		var currentFormSerializeObj = $.trim($("form").find("select,textarea, input").not('[type="hidden"]').not('[readonly="readonly"]').not('[disabled="disabled"]').serialize());
  	    var isDataChange = "";
  	    if(currentFormSerializeObj != existingFormSerializeObj){
  	   	 isDataChange = "Y";
  	    } else {
  	   	 isDataChange = "N";
  	    }
  	  url = url + "&isDataChange=" + isDataChange;
  	  
  		retrieveURL1( url, containerid, page_request,frm.jsessionid.value);
    } else
    	 { 
    	 	managesubmitform(document.forms[0], inet_method, dynakeys, dynavalues, page);
  		  }
   }
//------------------------------- end of right click menu-------------------------------------------

//-------------manage submit form start--------------------------------------
function managesubmitform(af_1, method, as_dynaKeys, as_dynaValues, action)
{
	if(!(method.indexOf("download") > -1) && method != 'showSessionList'){
		disableBody();
	}
  // alert(as_dynaKeys); alert(as_dynaValues);
      af_1.dynaKeys.value = as_dynaKeys;
      af_1.dynaValues.value = as_dynaValues;
      af_1.inet_method.value = method;
      af_1.inet_page.value= action;
      
      try{
      var currentFormSerializeObj = $.trim($("form").find("select,textarea, input").not('[type="hidden"]').not('[readonly="readonly"]').not('[disabled="disabled"]').serialize());
	    var isDataChange = "";
	    if(currentFormSerializeObj != existingFormSerializeObj){
	   	 isDataChange = "Y";
	    } else {
	   	 isDataChange = "N";
	    }
	    var status = $("#headerStatusDesc").text();
     if(status.indexOf('New')!= -1 && $("#User").val() == "Agent" && isDataChange == "N"){
    	 isDataChange = "Y";
     }
	    $(af_1).append('<input type="hidden" name="isDataChange" id="isDataChange" value="' + isDataChange + '" />');
      }catch (e) {}
      if(af_1.enctype == 'multipart/form-data')
      {
            af_1.action= "test.do;jsessionid="+af_1.jsessionid.value+"?dynaKeys="+as_dynaKeys+"&dynaValues="+as_dynaValues+"&inet_method="+method+"&inet_page="+action;
            
      }
     
      if(method == 'exportallexcel' || method == 'exportstatemonthexcel')
      {
       
      	 document.getElementById("error_indication_div").innerHTML = '';
      	document.getElementById("error_WeekToDate").innerHTML = '';	
		document.getElementById("error_WeekFromDate").innerHTML = '';          
        
      }
      
       if( method == 'getreport')
      {        
      	 document.getElementById("error_indication_div").innerHTML = '';      
         document.getElementById("error_FromDate").innerHTML = '';
         document.getElementById("error_ToDate").innerHTML = '';
       }
       if(method=='showSessionList' || (method=='viewAll' && action=='UWReview') || (method == 'downloadViewQuoteLetterPDF' && action == 'quoteOption')
      		|| (method == 'downloadpdf' && action == 'list') || (method == 'downloadpdf' && action == 'headerNewApp') || (method == 'downloadInvoice' && action == 'confirmation'))
        	af_1.target="_blank";
         else
       af_1.target="_self";
      
      
     // alert(af_1.action);
      af_1.submit();
}

 
//--------------end of the manage submit form------------------------------


//---------start of the div hide show----------------------------------
	//div Hide and Show on based on the container id ------
function divhideshow (containerid){
    //alert('container id---'+containerid);
    if(containerid != 'attachmentDiv'){
	      var tempcontainerid = containerid.substring(0, containerid.lastIndexOf(':'));
	      tempcontainerid = tempcontainerid.substring(tempcontainerid.lastIndexOf('_')+1, tempcontainerid.length);
	      //alert(tempcontainerid);
	      if(tempcontainerid.indexOf('multiple')!=-1){
	            //alert(tempcontainerid);
	            tempcontainerid = tempcontainerid.substring(tempcontainerid.indexOf('multiple')+8, tempcontainerid.length);
	            //alert(tempcontainerid);     
	            
	            if(document.getElementById('divHideShow'+tempcontainerid) != null){
	            var divid =  document.getElementById('divHideShow'+tempcontainerid).value;
	            
	            //alert('MUltiple div id---'+divid);
	          
	                  if(divid != ''){           
	                  if(document.getElementById(divid))
	                        document.getElementById(divid).innerHTML = '';
	                  }
	              
	              document.getElementById('divHideShow'+tempcontainerid).value = containerid;
	              
	             // alert('divHideShow1 ---  ' + document.getElementById('divHideShow'+tempcontainerid).value);
	         }  
	                  
	      }else{      
	            if(document.getElementById('divHideShow') != null){
	            var divid =  document.getElementById('divHideShow').value;
	            
	            //alert('div id---'+divid);
	          
	                  if(divid != ''){           
	                  if(document.getElementById(divid))
	                        document.getElementById(divid).innerHTML = '';
	                  }
	              
	              document.getElementById('divHideShow').value = containerid;
	         }
	   }
	  }
}



//---------- end of the div hideshow-----------------------------------

//---------start ajaxsorting ----------------------------------------------
	function ajaxsorting(page, containerid,dynakeys,dynavalues)
	{
    	ajaxpagination(page, containerid,dynakeys,dynavalues);
	}
//----------end of ajaxsorting--------------------------------------------


//--------------------start of ajaxpagination ---------------------------
	function ajaxpagination(page, containerid,dynakeys,dynavalues)
	{
		disableBody();
    	var page_request = false;
		var temp = new Array();
    	temp = dynakeys.split('|');
    	
    	var submitUrl =  parseElement(document.forms[0]);
    	var queryst =submitUrl;
    	var recordPerPage = document.getElementById(temp[6]);
    	var rePerpagevalue = recordPerPage.options[recordPerPage.selectedIndex].value;
		var pagenumber = document.getElementById(temp[7]);
		var pagenumbervalue = pagenumber.options[pagenumber.selectedIndex].value;
		dynavalues = dynavalues+rePerpagevalue+'|'+pagenumbervalue+'|';
		queryst = queryst+'dynaKeys='+dynakeys+'&dynaValues='+dynavalues+'&ajaxpage='+page;
		//alert(queryst +'  ' + containerid);
		
    	var url = queryst;
		retrieveURL1( url, containerid, page_request,document.forms[0].jsessionid.value);
	}
//----------------end of ajaxpagination -----------------------------------


//--------------start of AjaxPage -------------------------------------------
	// page will be displayed on based on the container id
	
	
	function manageajaxpage(frm , inet_method,dynakeys,dynavalues,page,containerid)
	{
     	if(dynavalues.match('select'))
     	{
          managesubmitform(document.forms[0], 'select', dynakeys, dynavalues, page);
     	}
      	else{
      		
      		disableBody();
      		
 			var queryst ="";
   			var page_request = false;
   	 		var divid = '';
   	 		if(document.getElementsByName('divHideShow'))
   	 			divid =  document.getElementsByName('divHideShow').value;
	    	
	    	if(divid!='')
	    	{
	    		document.getElementById(divid).innerHTML = '';
	    	}
 			
 			if(document.getElementsByName('divHideShow'))
 				document.getElementsByName('divHideShow').value = containerid;
			
			if(dynakeys=='')  
			{
				queryst ='dynaKeys='+'ajaxpage'+'&dynaValues='+'ajaxpage'+'&ajaxpage='+page
  			}else{
  			    queryst =  'dynaKeys='+dynakeys+'&dynaValues='+dynavalues+'&ajaxpage='+page
		  	}
			
			var url = queryst;
			//alert(url);
			var currentFormSerializeObj = $.trim($("form").find("select,textarea, input").not('[type="hidden"]').not('[readonly="readonly"]').not('[disabled="disabled"]').serialize());
		    var isDataChange = "";
		    if(currentFormSerializeObj != existingFormSerializeObj){
		   	 isDataChange = "Y";
		    } else {
		   	 isDataChange = "N";
		    }
		  url = url + "&isDataChange=" + isDataChange;
			retrieveURL1( url, containerid, page_request);
		}
  	}
//-------end of the ajaxpage------------------------------------------------------------------

//----------start of the retrieveURL----------------------------------------------------------
	function retrieveURL1( url, containerid, page_request,jsessionid)
	{
	   //alert(window.XMLHttpRequest);
	   //alert(window.ActiveXObject);
		try{ 
			   if (window.XMLHttpRequest){ // if Mozilla, Safari etc
					//alert('33');
					page_request = new XMLHttpRequest()
				}else if (window.ActiveXObject)
				{                             // if IE
					try {
		//			alert('2');
							page_request = new ActiveXObject("Msxml2.XMLHTTP")
						} 
					catch (e){
								try{
			//					alert('3');
										page_request = new ActiveXObject("Microsoft.XMLHTTP")
						  		    }
										catch (e){ alert(e);}
							  }
				}else{
				//alert('5');
					return false
				}
			page_request.onreadystatechange=function()
			{
				loadpage(page_request, containerid)
			}
	
			//alert(url);
			//Added Session Handling Code
			var doVar = "*.do";
			//alert(jsessionid);
        	if($.trim(jsessionid) == ""){
        		jsessionid = $("#jsessionid").val();
        	}
			if(jsessionid != null){
				doVar = doVar + ";jsessionid="+jsessionid;
			}
			//alert(doVar);

			if("countyList" == containerid){
				page_request.open('POST', doVar, false);
			} else {
				page_request.open('POST', doVar, true);
			}
			
			page_request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");	
			
			page_request.send(url);
			
			
			
			
			
			
			//alert('6');
		}catch (e){ }
	}
//--------------------end of the retrieveURL--------------------------------------------------

//--------------------start of load page------------------------------------------------------
	function loadpage(page_request, containerid)
	{
	    //	alert('loadpage start..');
		//	alert(page_request.readyState);
		//alert(page_request.status);
		//alert(window.location.href.indexOf("http"));
		try{
			if (page_request.readyState == 4 && ((page_request && page_request.status==200) || window.location.href.indexOf("http")==-1))
			{
				//alert('inside if   ' + containerid);
				//alert('response....'+page_request.responseText);
				if(page_request.responseText != null && page_request.responseText.length >= 10){
					if(page_request.responseText.substr(0,10) != null){
						var result = page_request.responseText.substr(0,10);
						if(result.indexOf('ajaxerrors')>=0){
							clearPageMsgOrPageErrorOnAjaxCall();
							result = page_request.responseText; //removing ajaxerrors from response
							//alert(result)
							
							populaterrorinajaxform(result);
							enableBody();
							
							return;
						}		
					}
				}
	//			enableBody();
				if(containerid.length >= 3)
				{
					var str = containerid.substring(0,7);
					//alert('inside if   ' + containerid + " ----- " + document.getElementById(containerid));
					//alert('response....'+page_request.responseText);
					if(str == "ajaxxml"){
						document.getElementById(containerid).innerHTML = '';
						//alert(page_request.responseXML);
						var xml = page_request.responseXML;
						//alert(xml);
						var nodes = xml.getElementsByTagName(containerid);
						//alert(nodes.length); 
						var node, nodevalue;
						for(i=0; i <nodes.length; i++) {
							node = nodes[i];
							if(node != null){
								nodevalue = node.firstChild.nodeValue;
								//alert(nodevalue);
							}
							nodevalue = nodevalue.replace(/ /g, '&nbsp;');
							var fieldid = containerid;
							fieldid = fieldid.substring(containerid.indexOf("_")+1);//"StatusKey_search";
							//alert(document.getElementById(fieldid)!=null);
							// this condition is added to solve the issue PONEJ-2863 (part 2) 
							if(document.getElementById(fieldid) == null){
								fieldid = "ajax_field_" +fieldid
							}	
							var search = "this.innerHTML,\'" + containerid + "\',\'" + fieldid + "\'";
							var suggest = '<div onmouseover="javascript:suggestOver(this);"';
							suggest += 'onmouseout="javascript:suggestOut(this);"';
							suggest += 'onclick="javascript:setSearch('+search+');"';
							suggest += 'class="suggest_link">' + nodevalue + '</div>';
							//alert(suggest);
							document.getElementById(containerid).innerHTML += suggest;
							if(document.getElementById(fieldid)){
								document.getElementById(fieldid).focus();
							}
							else if(document.getElementById('ajax_field_'+fieldid)){
								document.getElementById('ajax_field_'+fieldid).focus();
							}
							
						}
						if(nodes.length > 0){
						if(document.getElementById(containerid))
							document.getElementById(containerid).style.display = "block";
						} else {
							if(document.getElementById(containerid))
								document.getElementById(containerid).style.display = "none";
						}
		                    //alert(document.getElementById(containerid).innerHTML)                   
						enableBody();
						return;
					}
					
					
					//alert('if.response..2..');
				}
			
				if(containerid != 'countyList' )
				{
					document.getElementById(containerid).style.display='block';
				}
				//alert(document.getElementById(containerid).innerHTML);
				
				document.getElementById(containerid).innerHTML=page_request.responseText;
				
				// To get done from PIM Team 
				// Monika
				//alert('containerid--'+containerid);
				if(detectBrowser() == "Firefox"){
					if(containerid == 'countyList' )
					{
				 		document.getElementById('CountyDesc').focus();
				 		//alert('StateCode'+document.getElementById('StateCode').value);		 		
					}
					if(containerid ==  'cityList' )
					{
				 		document.getElementById('City').focus();
				 		
					}
				}
				if(containerid ==  'insuredEmailZoho' )
	            {
	            	loadIndicationPage();
	            	toggleDivDisplay('ajaxxmlCountyDesc_CountyDesc');
	            	makeDefenseOutsideRuleOnPageLoadIndication();
	            	loadQuickQuoteInsured();
	            	showIndicationOther('AOP_Percentage_25');
	            	$('.datepicker').datepicker();
	            	$("#PolicyEffectiveDate").change();
	            }
				enableBody();
				//alert('if.response..2..');
			}
		}catch (e) {
            enableBody();
			// TODO: handle exception
		}
		
		
			

	}
//----------------------end of load page--------------------------------------------------------	
//-------------------------------------------------------------------------------------------------------------------

/***************** ajax page validation *********************************************/
  
  var req;
  var url ="*.do";
  var set_af_1;
  var set_method;
  var set_dynaKeys;
  var set_dynaValues;
  var set_inet_page;
  var inet_ajax_form ="Y";
  var set_div_id = "ajax_errors_list";
  var set_form_containerid;
 
  function resolveControlValue(fieldVal){
  
  	if(fieldVal){
  		
  		if(fieldVal.indexOf("&") != -1){  		
  			fieldVal = fieldVal.replace(/&/gi,"$AND$"); 
  			}
  		if(fieldVal.indexOf("%") != -1){
  			fieldVal = fieldVal.replace(/%/gi,"$PER$");
  			}
  	}
  	return fieldVal;
  }	
  
    
  function parseElement(obj){
      var getstr="";       
      var objarr = new Array();
      objarr = obj.elements;

      for (i=0; i<objarr.length; i++){
      var node = objarr[i];                       
if (node.tagName == "INPUT"){   
      var id = node.id;
      if(id.match('ajax_field')){
                      if (node.type == "text"){
                      getstr = getstr+ node.name + "=" + resolveControlValue(node.value) + "&";                      
      }else if (node.type == "checkbox"){
                      if (node.checked) {
                                      getstr = getstr+ node.name + "=" + resolveControlValue(node.value) + "&";
                                      //alert(getstr);
                      } else {
                                      getstr = getstr+ node.name + "=&";
                                      //alert(getstr+"3333");
                      }
      }else if (node.type == "radio"){                 
                      if (node.checked){
                                      getstr = getstr+ node.name + "=" + resolveControlValue(node.value) + "&";
                      }
      }else if (node.type == "hidden"){
                      getstr = getstr+ node.name + "=" + resolveControlValue(node.value) + "&";                 
      }else if (node.type == "password"){
                      getstr = getstr+ node.name + "=" + resolveControlValue(node.value) + "&";                 
      }  
}    
}else if (node.tagName == "SELECT"){
      var id = node.id;
      //alert(id + ' == ' + node.getAttribute("multiple"));
      if(id.match('ajax_field')){
                      //alert(node.options[node.selectedIndex]);
                      //alert(node.selectedIndex);
                      if(node.selectedIndex >=0){
                                      if(node.getAttribute("multiple") == null || !node.getAttribute("multiple")=='multiple')
                                      getstr = getstr+ node.name + "=" + resolveControlValue(node.options[node.selectedIndex].value) + "&";
                      else
                                      getstr = getstr+ node.name + "=" + resolveControlValueForMultipleSelect(node) + "&";
      }
}
}else if (node.tagName == "TEXTAREA"){
      var id = node.id;
      if(id.match('ajax_field')){
      getstr = getstr+ node.name + "=" + resolveControlValue(node.value) + "&";
}
} 
} 

return getstr;
}


  
  function parseAjax(element_id)
  {
  	return parseElement(element_id);  	 	
  }  
  
  	function parseDefaultAjax()
 	{
 		var str = "dynaKeys="+set_dynaKeys+"&dynaValues="+set_dynaValues+"&inet_method="+set_method+"&inet_page="+set_inet_page+"&inet_ajax_form="+inet_ajax_form;
 		return str;
 	}
 

   function buildSubmitUrl(fr_id,form_containerid)
  {
  	var submitUrl =  parseElement(fr_id);  
  	submitUrl  =submitUrl +   "dynaKeys="+set_dynaKeys+"&dynaValues="+set_dynaValues+"&datadisplayid="+form_containerid + "&inet_method="+set_method+"&inet_page="+set_inet_page+"&inet_ajax_submit_form="+inet_ajax_form;
  	return submitUrl; 	
  }  
  
 	function setFormContent(af_1, method, as_dynaKeys, as_dynaValues, action, form_containerid)
  	{
  		af_1.dynaKeys.value = as_dynaKeys;
      	af_1.dynaValues.value = as_dynaValues;
      	af_1.inet_method.value = method;
      	af_1.inet_page.value= action;  
      	     	
  		set_af_1 = af_1;
  		set_method = method;
  		set_dynaKeys = as_dynaKeys;
  		set_dynaValues = as_dynaValues;   		
  		set_inet_page = action;  
  		set_form_containerid = 	form_containerid;	
  		
  	}
 	
	function managesubmitformajax(af_1, method, as_dynaKeys, as_dynaValues, action, form_containerid)
	{  
		setFormContent(af_1, method, as_dynaKeys, as_dynaValues, action, form_containerid);
  		processFormAjax(af_1);
	}  
 	
	function processFormAjax(form)
  	{
  		
	    if (window.XMLHttpRequest)
	    { 
	      // Non-IE browsers	
	      req = new XMLHttpRequest();	      
	    } 
	    else if (window.ActiveXObject) 
	    { 
		  // IE
		  req = new ActiveXObject("Microsoft.XMLHTTP");	
    	}
    	
    	if (req)
	    {	 	
			var param = parseAjax(form);
	        param += parseDefaultAjax();
	        var currentFormSerializeObj = $.trim($("form").find("select,textarea, input").not('[type="hidden"]').not('[readonly="readonly"]').not('[disabled="disabled"]').serialize());
		    var isDataChange = "";
		    if(currentFormSerializeObj != existingFormSerializeObj){
		   	 isDataChange = "Y";
		    } else {
		   	 isDataChange = "N";
		    }
		    param = param + "&isDataChange=" + isDataChange;
	       
	        //Added Session Handling Code 
	        if(url == '*.do'){
	        	var jsessionid = form.jsessionid.value;
	        	if($.trim(jsessionid) == ""){
	        		jsessionid = $("#jsessionid").val();
	        	}
	       		url = url + ";jsessionid="+jsessionid;		
	        }
	        //Ended Code
	       //alert(param);
	           
	        req.onreadystatechange = processRequest; 
	        
	        req.open("POST", url, true);
			req.setRequestHeader("Content-Type",
			"application/x-www-form-urlencoded");		
	        req.send(param);
 	    }	
  	}
  	
  	function processRequest()
  	{
	  	if (req.readyState == 4)
	    { 
	    	// Complete
	      if (req.status == 200) 
	      { 
	      	// OK response
	      	var result = req.responseText;	 
	      	
	      	     	
	      	if(result != "")
	        {
	        	//document.getElementById(set_div_id).innerHTML = result;
	        	enableBody();
	        	populaterrorinajaxform(result);
	        		        	
	        }
			else
			{			
				//set_af_1.submit();
				manageajaxsubmitform(set_af_1,set_method,set_dynaKeys,set_dynaValues,set_inet_page,set_form_containerid);
			}
	      }
	      else
	      {
	      	alert("Problem: " + req.statusText);
	      }
	    }
  	}
  	
  	function populaterrorinajaxform(result)
	{	
	//alert(result);
		var errorArr = new Array(); 
		errorArr = result.split('|');
		
		var objarr = document.getElementsByTagName("div");
  	 	//alert(errorArr.length);
	  	 for (i=0; i<objarr.length; i++)
	  	 {      	
	      	 var div_element = objarr[i];
	      	 var div_id = div_element.id;        	
		     if(div_id.match('ajax_error_'))
		     {     	
				div_element.innerHTML = '';
				//document.getElementById('tr_'+div_id).style.display='none';			
			 }    
		 }
		
		var is_inet_error = 'N';
		
		for (i=0; i<=errorArr.length-1; i++){
			var error=errorArr[i];
			var colonIndex = error.indexOf(':');
			var fld = null;
			if(error.indexOf('ajaxerrors') == -1){
				fld = error.substring(0,colonIndex);
			}else{	
				fld = error.substring(10,colonIndex);
			}
				
			var msg = error.substring(colonIndex+1);
			//alert(msg);alert(fld);
			//alert(fld +"...."+msg);
			//document.getElementById('tr_ajax_error_'+fld).style.display='block';
			//alert(fld);
			if(document.getElementById('ajax_error_'+fld) != null)
			{	
				is_inet_error = 'Y';
				if(document.getElementById('error_'+fld) != null)
					document.getElementById('error_'+fld).innerHTML = '';
				document.getElementById('ajax_error_'+fld).innerHTML = msg;
				document.getElementById('ajax_error_'+fld).style.display='block';
			}
			if(fld == "ZohoError"){
				showPopUpOverloaded('zohodata','500','400');
			}
		}
		
		if(is_inet_error='Y')
		{			
			if(document.getElementById('ajax_error_indication_div') != null)
			{
				document.getElementById('ajax_error_indication_div').innerHTML = 'Please correct the errors highlighted in red below';
				document.getElementById('ajax_error_indication_div').style.display='block';
			}
		}
		
		enableBody();
			
	}
  	
  	function manageajaxsubmitform(frm,  inetmethod , dynakeys, dynavalues,page,containerid)
	{
	 // alert('33');
		//var page_request ;
		 // alert( frm+'--'+ inetmethod +'----'+ dynakeys+'--------'+dynavalues+'------'+containerid)
		var queryst = buildSubmitUrl(frm,containerid);
		disableBody();
		
		//alert(document.getElementById("append_IsFirmCarryingProfLiabilityIns").value);
		 
		
		// alert(queryst);
		  // divhideshow (containerid);
		 
//		 if(document.getElementById("LimitKey_append"))
//		{
//			var node = document.getElementById("LimitKey_append");
//			queryst = queryst+"&"+ node.name + "=" + node.options[node.selectedIndex].value ;
//		//	alert(queryst);
//		}
		
		 
		var currentFormSerializeObj = $.trim($("form").find("select,textarea, input").not('[type="hidden"]').not('[readonly="readonly"]').not('[disabled="disabled"]').serialize());
	    var isDataChange = "";
	    if(currentFormSerializeObj != existingFormSerializeObj){
	   	 isDataChange = "Y";
	    } else {
	   	 isDataChange = "N";
	    }
	    queryst = queryst + "&isDataChange=" + isDataChange;
		 
		var url = queryst;
		 // alert(url);
	  //  alert(containerid);
	     // alert(url);
    	retrieveURL1( url, containerid, req, frm.jsessionid.value);
	}
  	

/*************************end of ajax validation************************************/

//---------------submit form for Menu-----------------------------------------------
	function submitFormMenu(af_1, method, as_dynaKeys, as_dynaValues, action, ex_dynaKey, ex_dynaVal)
	{
      af_1.dynaKeys.value = as_dynaKeys + ex_dynaKey;
      af_1.dynaValues.value = as_dynaValues + ex_dynaVal;
      af_1.inet_method.value = method;
      af_1.inet_page.value= action;
      
      af_1.submit();
	}
//---------------------------end of submit form for menu-----------------------------

//--------------------------------------------------------------------------------------------------------------------
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

 
//------------------------------------delete confirmation-------------------------------------------------
	function delete_this()
	{
 		var answer = confirm("Do you really want to delete this ??");
	}
//----------------------------------------------------------------------------------------------------
	
//-------------------------------------------------------------------------------
	


//---------------------------------------------rule------------------------------------------

var globalCursorPos; // global variabe to keep track of where the cursor was
//sets the global variable to keep track of the cursor position
function setCursorPos() {
 globalCursorPos = getCursorPos(document.forms[0].expression);
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
 
 var firstPart = document.forms[0].expression.value.substring(0, globalCursorPos);
 var secondPart = document.forms[0].expression.value.substring(globalCursorPos, document.forms[0].expression.value.length);
  document.forms[0].expression.focus();
 document.forms[0].expression.value = firstPart + stringToInsert + secondPart;
}
function setTextAreasOnFocus() {
/***
 * This function will force the cursor to be positioned
 * at the end of all textareas when they receive focus.
 */
    var textAreas = document.getElementsByTagName('expression');

    for(var i = 0; i < textAreas.length; i++) {
        textAreas[i].onfocus = function() {
            setCaretPosition(this.id, this.value.length);
        }
    }

    textAreas = null;
}




function showRuleset(ruleset){	
	
	if(ruleset.value == '0')
	{
		showDivArea(['divOtherRuleset'], ['divOtherRulesethide']);
		
	} else {
		showDivArea(['divOtherRulesethide'], ['divOtherRuleset']);
		document.forms[0].otherRuleSet.value='';
	}
}

function clearContents(){
	document.forms[0].DocTitle.value='';
	document.forms[0].Comment.value='';
	document.forms[0].file_path.value='';
}

function parseIsQuoteSelected(obj)
  {
  	  var getstr="";  	  
  	  var objarr = new Array();
  	  objarr = obj.elements;
  	   
  	  for (i=0; i<objarr.length; i++)
      {
      	 var node = objarr[i];		 
         if (node.tagName == "INPUT" && node.type == "checkbox")
         {   
         	var name = node.name;
         	if(name.match('IsQuoteSelected_'))
         	{
         			if (node.checked) {
	                  getstr = getstr+ "&" + node.name + "=" + resolveControlValue(node.value) ;	                 
	               } else {
	                  getstr = getstr+"&"+node.name + "=";	                  
	               }            
         	}    
         }            
       } 
       
       return getstr;
  }


function suggestOver(div_value) {
	div_value.className = 'suggest_link_over';
	}
	//Mouse out function
	function suggestOut(div_value) {
	div_value.className = 'suggest_link';
	}

	//Click function
	function setSearch(value,containerid,fieldid) {

	if(value != null){
	value = value.replace(/&amp;/g,'&');
	value = value.replace(/&nbsp;/g,' ');
	//alert(value);
	document.getElementById(fieldid).value = value;
	document.getElementById(containerid).innerHTML = '';
	document.getElementById(containerid).style.display='none';
	document.getElementById(fieldid).focus();
	}
	}





	function managereport(dynaKeys,dynaValues,method,action){
		// alert(as_dynaKeys); alert(as_dynaValues);
		
//		var as_dynaKeys = dynaKeys;
//		var as_dynaValues = "|"+ reportID + "|";
			
							
		document.forms[0].dynaKeys.value = dynaKeys;
		document.forms[0].dynaValues.value = dynaValues;
		document.forms[0].inet_method.value = method;
		document.forms[0].inet_page.value= action;
		document.forms[0].target = "_self";
		
		var isConfirmDeleteFound = false; 
	    if(method == 'INET_DELETE_SAVE_SEARCH'){
	    	showPopUp('deleteConfirmation','400','300');
	      	isConfirmDeleteFound = true;
	      	//alert('found');
	    }
	    
	    if(isConfirmDeleteFound){
	    	return;
	    }
	      
		var outputtype = '';
		if(document.getElementById('outputtype_0') != null 
			&& document.getElementById('outputtype_0').checked == true)
			outputtype = 'S';
		else if(document.getElementById('outputtype_1') != null 
			&& document.getElementById('outputtype_1').checked == true)
			outputtype = 'E';
		else if(document.getElementById('outputtype_2') != null 
			&& document.getElementById('outputtype_2').checked == true)
			outputtype = 'P';
		
		if(outputtype == '')
			outputtype = 'S';
		
		//alert(outputtype);
		if(method != null && method == 'INET_SHOW_REPORT' && outputtype == 'S'){
			if(document.getElementById('isDrillDownReportTrue') != null && document.getElementById('isDrillDownReportTrue').value=='Y')
				document.forms[0].target = "_self";
			else
				document.forms[0].target = "_blank";
		}else if(method != null && method == 'INET_SHOW_REPORT' && dynaValues.indexOf('isNewWindow') > 0){
			document.forms[0].target = "_blank";
		}else
			document.forms[0].target = "_self";
		
		if(method != null && method == 'INET_SHOW_REPORT_SAVEDSEARCH')
			document.forms[0].target = "_blank";
		
		/*if(method != null && method == 'INET_SUBMIT_EXPORT_POPUP'){
			var exporttype = '';
			if(document.getElementById('exporttype_0') != null 
				&& document.getElementById('exporttype_0').checked == true)
				document.getElementById('exporttype').value='1';
			else if(document.getElementById('exporttype_1') != null 
				&& document.getElementById('exporttype_1').checked == true)
				document.getElementById('exporttype').value='2';
				
				
				
		}*/
		//alert(method);
		if(method.indexOf('INET_SHOW_REPORT')==-1 && method.indexOf('INET_SUBMIT_EXPORT_POPUP')==-1 && method.indexOf('INET_DOWNLOAD_BATCH_REPORT')==-1)
			disableBody();
		
		if(method.indexOf('INET_SHOW_REPORT')!=1 && outputtype != 'S'){	
			//alert(dynaValues);
			var newdynaValues = null;
			var keysArray = dynaKeys.split('|');
	      	var valuesArray = dynaValues.split('|');
	      	if(keysArray != null && keysArray.length > 0){
	      		for(var i=0; i< keysArray.length; i++){
	      			var key = keysArray[i];
	      			var keyValue = valuesArray[i];
	      			
	      			if(key == 'ISSKIPREQUESTAUTHTOKEN'){
	      				valuesArray[i] = 'N';
	      				//alert('found');
	      			}
	      			
	      			//alert(key + ' = ' + valuesArray[i]);
	      			
	      			if(newdynaValues == null)
	      				newdynaValues = valuesArray[i];
	      			else	
	      				newdynaValues= newdynaValues + "|" + valuesArray[i];
	      		}
	    	}
	    	
	    	dynaValues = newdynaValues;
	    	document.forms[0].dynaValues.value = dynaValues;
	    	disableBody();
	    }
		
		if(method.indexOf('INET_SHOW_REPORT')!=1 && outputtype != 'S'){	
			//alert(dynaValues);
			var newdynaValues = null;
			var keysArray = dynaKeys.split('|');
	      	var valuesArray = dynaValues.split('|');
	      	if(keysArray != null && keysArray.length > 0){
	      		for(var i=0; i< keysArray.length; i++){
	      			var key = keysArray[i];
	      			var keyValue = valuesArray[i];
	      			
	      			if(key == 'ISSKIPREQUESTAUTHTOKEN'){
	      				valuesArray[i] = 'N';
	      				//alert('found');
	      			}
	      			
	      			//alert(key + ' = ' + valuesArray[i]);
	      			
	      			if(newdynaValues == null)
	      				newdynaValues = valuesArray[i];
	      			else	
	      				newdynaValues= newdynaValues + "|" + valuesArray[i];
	      		}
	    	}
	    	
	    	dynaValues = newdynaValues;
	    	document.forms[0].dynaValues.value = dynaValues;
	    	disableBody();
	    }
	    			
		document.forms[0].submit();
	}
	
	
function clearPageMsgOrPageErrorOnAjaxCall(){
	//alert("page : "  + page);
	if(document.getElementById("pageMsg") != null){
		document.getElementById('pageMsg').innerHTML = '';
	}
	if(document.getElementById("pageMsg1") != null){
		document.getElementById('pageMsg1').innerHTML = '';
	}
	if(document.getElementById("ajax_pageMsg3") != null){
		document.getElementById('ajax_pageMsg3').innerHTML = '';
	}
	if(document.getElementById("pageError") != null){
		document.getElementById('pageError').innerHTML = '';
	}
	if(document.getElementById("pageError1") != null){
		document.getElementById('pageError1').innerHTML = '';
	}
	if(document.getElementById("pageError23") != null){
		document.getElementById('pageError23').innerHTML = '';
	}
	
	var fldArray = document.getElementsByTagName('div');
	for (var i = 0; i < fldArray.length; i++) {
		var fld = fldArray[i];
		if(fld == null)
			continue;
		
		if(fld != null && fld.id != null){	
			var id = fld.id;
			//alert(id)
			if(id.indexOf("ajax_error_")>-1){
				//alert(id)
				document.getElementById(id).innerHTML='';
			}
		}
	}		
}