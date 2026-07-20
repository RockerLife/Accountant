

window.history.forward();
window.name='ProducerOnePerformance';
	


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
	var flag = false;
	if (whKey == 13){
		
		var linkID = "submitForm";
		var activeElementID = document.activeElement.id;
		if(activeElementID == "searchByEntityName" || activeElementID == "searchByIndName" || activeElementID == "searchByProdNumber" || activeElementID == "agency_system_code"){
			linkID = activeElementID + "Link";
		}
		
	// *** PLACE CODE TO BE EXECUTED ON ENTER PRESS HERE
			//this code searches the document for a link with id attribute set to "submitForm" and if it finds it, evaluates the function in its href
			//link href must be of the format: href="javascript:function();"
			//note this will not simulate a click event on the link so it won't work with links that rely on the onClick() event
			for (var i=0; i<document.links.length; i++){
				
				if (document.links[i].id == linkID){					
					//window.location = document.links[i].href	//use this if you want to call a regular link instead of a js function
					if(activeElementID != linkID){
						if(document.links[i].onclick){
							document.links[i].onclick();
							}
						eval(unescape(document.links[i].href))	//unescape is used to prevent spaces between function arguments from being escaped and causing a syntax error
						
						flag = true;
					}
				}
			}
			if(flag == false){
				linkID = "submitForm";
				for (var i=0; i<document.links.length; i++){
				
					if (document.links[i].id == linkID){					
						//window.location = document.links[i].href	//use this if you want to call a regular link instead of a js function
						
						if(activeElementID != linkID){
							if(document.links[i].onclick){
							document.links[i].onclick();
							}
							eval(unescape(document.links[i].href))	//unescape is used to prevent spaces between function arguments from being escaped and causing a syntax error
							
						}
					}
				}
			}
			
	// *** END ENTER PRESS HANDLER CODE
	//to simply submit a form on enter press, use this code: document.formName.submit()
	}
}



function spaces(count,relatonship,last_location_or_individual,person_id,parent_is_last){
	
	var imageLineSrc = 'images/HierarchyBgLine.png';
	var imageTeeArrowSrc = 'images/heirarchy_arrowBorder.png';
	var imageLArrowSrc = 'images/heirarchy_arrow.png';
	
	/*	for(var i=0; i<count; i++){
		document.write("&nbsp;&nbsp;&nbsp;");
		}
	*/
	
	//alert(relatonship + " -  "  +last_location_or_individual);
	
		if(relatonship == 'Producer' || relatonship == 'Sub Producer' || relatonship == 'Super Producer' 
			|| relatonship == 'Appointment' || relatonship == 'Termination' || relatonship == 'Authorization'){
			//alert(person_id);
			for(var i=1; i<=count; i++){
				
				if(i < count){
					if(count == 2 && parent_is_last == 1)
						document.write("<span>&nbsp;&nbsp;&nbsp;&nbsp;</span>");
					else					
						document.write("<img src='"+imageLineSrc + "' border='0'></img>");			
				}			
				else if(person_id == null || person_id == "null")
					{
						if(last_location_or_individual == 1)
							document.write("<span>&nbsp;&nbsp;&nbsp;&nbsp;</span>");
						else
							document.write("<img src='"+imageLineSrc + "' border='0'></img>");
					}
				else				
				    document.write("<span>&nbsp;&nbsp;&nbsp;&nbsp;</span>");
			}
		}
		if(relatonship == 'Location' || relatonship == 'Individual'){
			for(var i=1; i<=count; i++){
				if(i < count){
					if(count == 2 && parent_is_last == 1)
						document.write("<span>&nbsp;&nbsp;&nbsp;&nbsp;</span>");
					else					
						document.write("<img src='"+imageLineSrc + "' border='0'></img>");			
				}		
				else if (last_location_or_individual == 1)
					document.write("<img src='"+imageLArrowSrc + "' border='0'></img>");
				else			
					document.write("<img src='"+imageTeeArrowSrc + "' border='0'></img>");	
					
				
			}
		}
	
	
	/*var imageSrc = 'images/arrowRedForHierarchy.gif';
	if(relatonship == 'Individual' || relatonship == 'Appointment' || relatonship == 'Authorization')	
	{
		
		imageSrc = 'images/arrowBlackForHierarchy.gif';
	} 
    if(count>0)
    {       
    	document.write("<img src='"+imageSrc + "' border='0'></img>");
    }   */     
}
function spacesProducerHierarchy(count,relatonship,last_location_or_individual,person_id,parent_is_last){
	
	var imageLineSrc = 'images/HierarchyBgLine.png';
	var imageTeeArrowSrc = 'images/heirarchy_arrowBorder.png';
	var imageLArrowSrc = 'images/heirarchy_arrow.png';
	
		for(var i=0; i<count; i++){
		document.write("&nbsp;&nbsp;&nbsp;");
		}
	
}


//-------------manage submit form start--------------------------------------
function managesubmitformforupload(af_1, method, as_dynaKeys, as_dynaValues, action)
{
  	  //alert(as_dynaKeys); alert(as_dynaValues);
      //af_1.dynaKeys.value = as_dynaKeys;
      //af_1.dynaValues.value = as_dynaValues;
      //af_1.inet_method.value = method;
      //af_1.inet_page.value= action;
      //af_1.submit();
      
      var fileName =af_1.file_path.value;
	 
	  if(fileName==""){
		alert("Please select a Excel to upload.");
		}
	  else{
		af_1.action= "upload?dynaKeys="+as_dynaKeys+"&dynaValues="+as_dynaValues+"&inet_method="+method+"&inet_page="+action;
		af_1.submit();		
		}
      
}


function disableBody(){
//alert('1');
window.scrollTo(0,0);	
//document.body.style.cursor='wait';
//document.body.disabled = true;


//document.body.pause();
	var cvr = document.getElementById("progressBarDiv");
	//alert(cvr);
	if(cvr!=null)
	{
	cvr.style.display = "block";
	
	document.documentElement.style.overflow = document.body.style.overflow = "hidden";
	//showPopUp('cover1','50','50');
	if (document.body.style.overflow = "hidden") {
		cvr.style.width = "100%";
		cvr.style.height = "100%";
	}
	}
	
}
 
function enableBody(containerid){

//document.body.disabled = false;
	//document.body.style.cursor='default';

//document.body.resume();
	
	var cvr = document.getElementById("progressBarDiv");
	//alert(cvr);
	if(cvr != null){
		//closePopUp('cover1');
		cvr.style.display = "none";
		
		document.documentElement.style.overflow = document.body.style.overflow = "auto";
		//var containerid = document.getElementById('divHideShow').value;
		//alert(containerid);
		
		if(containerid != null && containerid != '')
		{
			getElementForFocus(containerid);
		}
	}
	
} 

function manageshowdialogbox(frm,inet_method, dynakeys,dynavalues,page,containerid,nextpage)
{
             
                   //alert(dynakeys+' '+dynavalues);
                   
                var url ='test.do;jsessionid='+frm.jsessionid.value+'?inet_method='+inet_method+'&inet_page='+nextpage+'&dynaKeys='+dynakeys+'inet_previouspage|'+'&dynaValues='+dynavalues+page+'|'; 
               //alert(url);
               
              // document.body.style.filter = true ? "filter: alpha(opacity=75);" : "" ;
              
              disableBody();
               //,dialogLeft:300px, dialogRight:300px,dialogTop:50px,dialogWidth:600px,dialogHeight:300px
                 // window.showModalDialog(url, '', 'dialogTop:100px;dialogWidth:700px;dialogHeight:370px;status:off;resizable:no;'); 
             var newWin =  window.open(url,'',"width=850,height=320,left=75,top=200,toolbar=no,status=off,resizable=no,scrollbars=no,menubar=no,status=no,directories=no");
             newWin.focus();
                  //managesubmitpopupform(frm,inet_method,dynakeys, dynavalues,page);
                  
                 // managesubmitformwithajax(frm,inet_method,dynakeys, dynavalues,page,containerid);
                  
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
	disableBody(); 
    retrieveURL1( url, containerid, page_request,page,inet_method);
}

function managesubmitpopupform(af_1,method,as_dynaKeys, as_dynaValues,action){
      as_dynaKeys = as_dynaKeys + 'popuppage|';
      as_dynaValues = as_dynaValues + 'popuppage|';
      //alert(as_dynaKeys);
      af_1.dynaKeys.value = as_dynaKeys;
      af_1.dynaValues.value = as_dynaValues;
      af_1.inet_method.value = method;
      af_1.inet_page.value= action;
     //af_1.method="post";
      af_1.submit();
      //window.close();
}



//-------------------------------------------------------------  Runtime Script-----------------------------------------------------------------
  	var loadedobjects=""
	var rootdomain="http://"+window.location.hostname

    //----start of ajaxpage block link------------
	function manageajaxpageblocklink(frm, inetmethod, dynakeys, dynavalues, page, containerid){
		// var StateId = document.getElementById('state_id').value;
	 	// alert("- - - - - >" + StateId);
	  	//alert(containerid);
	  	var page_request ;
		var submitUrl =  parseElement(document.forms[0]);
    	//alert(submitUrl);
    	var queryst = submitUrl;
		// alert( frm+'--'+ inetmethod +'----'+ dynakeys+'--------'+dynavalues+'------'+containerid)
		queryst =queryst+ 'dynaKeys='+dynakeys+'inet_method|datadisplayid|'  +'&dynaValues='+dynavalues+inetmethod+'|'+containerid+'|' +'&ajaxpage='+page;
		
		//alert(document.getElementById("append_IsFirmCarryingProfLiabilityIns").value);
		// divhideshow (containerid);
		//  alert("1- - - - - >" + CountryId);
        
        queryst = setFilterDataForProdValidation(queryst);
        queryst = setAjaxFieldValuesForSubmit(queryst);
        
		//alert(queryst);
		//if(inetmethod !="getAgencyList" && inetmethod !="GetAgentLastNameList" && inetmethod !="searchByProdNumber") 
		//disableBody();
		
		//Added code to get html page source for upload document case
		if(inetmethod != null && inetmethod == 'uploadAttachment')
			getPageSource();
		//ended code	 
			 
		var url = queryst;
		
	  	//alert(dynakeys);
	 	//alert(containerid);
	    //alert(url);
	    //Added Session Handling Code 
	    // alert(containerid);
	   
	    var isConfirmDeleteFound = false; 
	    if(dynakeys != null && dynavalues != null){
	        var keysArray = dynakeys.split('|');
	        var valuesArray = dynavalues.split('|');
	        if(keysArray != null && keysArray.length > 0){
	       		for(var i=0; i< keysArray.length; i++){
	      			var key = keysArray[i];
	      			if(key == 'showdeleteconfirmation' && valuesArray[i] == 'Y'){
	      				showPopUp('deleteConfirmation','400','300');
	      				isConfirmDeleteFound = true;
	      				
	      				frm.dynaKeys.value = dynakeys;
	      				frm.dynaValues.value = dynavalues;
      					frm.inet_method.value = inetmethod;
      					frm.inet_page.value= page;
      					break;
	      			}
	      		}
	        }
	        if(isConfirmDeleteFound){
	        	document.getElementById('ajax_field_skipDeletePopup').value = 'Y';
	       		return;
	        }
        }
        //alert(containerid);
        if(!containerid.match("ajaxxmlname")){
			
			disableBody();
			//alert('1');
		}
       
        retrieveURL1(url, containerid, page_request, page, document.forms[0].jsessionid.value,inetmethod);
	}
	
	
  //----end of ajaxpage block link---------------
  
  
  function setRadioValue(controlName,controlValue)
	{
		
		var control =  document.getElementsByName(controlName);
		if(control){
		if(control.length){
			
			for (var i=0; i < control.length; i++)
	   		{	
	   			if (control[i].value == controlValue)
	      			{
	      				control[i].checked = true;
	      			}
	   		}
	   	}
		}
    }
  
  
   function getRadioValue(controlName)
	{
		
		var control =  document.getElementsByName(controlName);
		if(control){
		if(control.length){
			//alert("size"+ control.length);
			for (var i=0; i < control.length; i++)
	   		{
	   			if (control[i].checked)
	      			{
	      				
	      				var rad_val = control[i].value;
	      				return rad_val;
	      			}
	   		}
	   	}else{
	   		
	   		return control.value;
	   	}
		}
    }
    
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
  		queryst ='dynaKeys='+dynakeys+'ajaxpage|&dynaValues='+dynavalues +'ajaxpage|&ajaxpage='+page + '&inet_method='+inet_method;
  		var url =  queryst; //'ComponentProcessServlet.do?'+queryst;
  		//alert('33');
  		retrieveURL1( url, containerid, page_request,page,frm.jsessionid.value,inet_method);
    } else
    	 { 
    	 	managesubmitform(document.forms[0], inet_method, dynakeys, dynavalues, page);
  		  }
   }
//------------------------------- end of right click menu-------------------------------------------

//-------------manage submit form start--------------------------------------
function managesubmitform(af_1, method, as_dynaKeys, as_dynaValues, action){
	var isPageErrorFound = false;
	isPageErrorFound = checkForPageErrorFromJavaScript(isPageErrorFound, action, method);
	if(isPageErrorFound)
		return;
	
	if(as_dynaValues.indexOf('#')!=-1){ //if # exists in dynaValue
		as_dynaValues = as_dynaValues.replace(/#/g, '_HASH_');
    }
	
  	// alert(as_dynaKeys); alert(as_dynaValues);
      af_1.dynaKeys.value = as_dynaKeys;
      af_1.dynaValues.value = as_dynaValues;
      af_1.inet_method.value = method;
      af_1.inet_page.value= action;

	//alert(af_1.inet_page.value);
	
      var isConfirmDeleteFound = false; 
      var isConfirmationShowFlagFound = false;
      var isConfirmationMsgFound = false;
      var confirmationMsg = "";
      var reactivate_label = "";
      
      var keysArray = as_dynaKeys.split('|');
      var valuesArray = as_dynaValues.split('|');
      if(keysArray != null && keysArray.length > 0){
      	for(var i=0; i< keysArray.length; i++){
      		var key = keysArray[i];
      		var keyValue = valuesArray[i];
      		if(key == 'showdeleteconfirmation' && valuesArray[i] == 'Y'){
      			showPopUp('deleteConfirmation','400','300');
      			isConfirmDeleteFound = true;
      			//alert('found');
      		}
      		if(key == 'showConfirmationMsg' && valuesArray[i] == 'Y'){
      			isConfirmationShowFlagFound = true;
      			
      		}
      		
      		if(key == 'confirmation_msg_text'){
      			confirmationMsg = valuesArray[i];
      			isConfirmationMsgFound = true;
      		}
      		
      		if(key == 'reactivate_label'){
      			reactivate_label = valuesArray[i];
      		}
      		
      		if(key == 'showIVANSdeleteconfirmation' && valuesArray[i] == 'Y'){ 
      			showPopUp('deleteIVANSConfirmation','400','300');
      			isConfirmDeleteFound = true;
      			//alert('found');
      		}
      		if(key == 'showdeleteProducerconfirmation' && valuesArray[i] == 'Y'){
      			showPopUp('deleteProducerConfirmation','400','300');
      		}

      	}
      	
      	if(isConfirmationShowFlagFound && isConfirmationMsgFound){
	      	showConfirmationPopup('showConfirmationMsgPopup', confirmationMsg, reactivate_label);
	      	isConfirmDeleteFound = true;
      			
      	}
      	
      }
      if(isConfirmDeleteFound){
    	  return;
      }
      
      //alert(as_dynaValues);
      if(af_1.enctype == 'multipart/form-data'){
            af_1.action= "test.do;jsessionid="+af_1.jsessionid.value+"?dynaKeys="+as_dynaKeys+"&dynaValues="+as_dynaValues+"&inet_method="+method+"&inet_page="+action;
      }
     // alert(af_1.action);
     //document.body.style.cursor='wait';
     
     
     //Added code to get html page source for upload document case
	 //if(method != null && (method == 'uploadAttachment' || method.indexOf('download') != -1)){
	 if(method != null && method == 'uploadAttachment'){
	 	if(method == 'uploadAttachment' && document.getElementById('file') != null && document.getElementById('file').value == '' && document.getElementById('error_fileError') != null){
	 		document.getElementById('error_fileError').innerHTML = 'Please select a file to upload';	
	 		return;
	 	}	
	 	getPageSource();
	 }	
	 //ended code
	//Pace.start();	
	
	if(method.indexOf('download')==-1 && method.indexOf('export')==-1)
		disableBody();
    
     if(method=="viewBGReport"){
    	// alert(valuesArray[0]);alert(valuesArray[1]);
     	window.open(valuesArray[1]);
     }
     else{
    	af_1.target = "_self";
    	af_1.submit();
     }
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
    	//alert(submitUrl);
    	queryst = submitUrl;
    	//Added for Agency List Accepted Public Submit Edit - ProducerOne
    	if(document.getElementById('aar_taxid_pagination') != null && document.getElementById('aar_taxid_pagination') != ''){
			queryst = queryst + 'aar_taxid_pagination=' + document.getElementById('aar_taxid_pagination').value + '&'; 	
		}
		//Ended
		var containerIndex =  containerid.substring(containerid.lastIndexOf('_'),containerid.length);
        if(containerid=='ajax_sorting'){
		        var recordPerPage = document.getElementById(temp[6]);
		    	
		    	var rePerpagevalue = recordPerPage.options[recordPerPage.selectedIndex].value;
				var pagenumber = document.getElementById(temp[7]);
				var pagenumbervalue = pagenumber.options[pagenumber.selectedIndex].value;
				dynavalues = dynavalues+rePerpagevalue+'|'+pagenumbervalue+'|';
				queryst = queryst+ 'dynaKeys='+dynakeys+'&dynaValues='+dynavalues+'&ajaxpage='+page;
        }else
        {
		        var recordPerPage = document.getElementById(temp[6]+containerIndex);
		    	
		    	var rePerpagevalue = recordPerPage.options[recordPerPage.selectedIndex].value;
				var pagenumber = document.getElementById(temp[7]+containerIndex);
				var pagenumbervalue = pagenumber.options[pagenumber.selectedIndex].value;
				dynavalues = dynavalues+rePerpagevalue+'|'+pagenumbervalue+'|';
				queryst = queryst+ 'dynaKeys='+dynakeys+'&dynaValues='+dynavalues+'&ajaxpage='+page;
		}
		if(document.getElementById('is_hidden') != null){
			if(document.getElementById('is_hidden').checked)
				queryst = queryst + '&is_hidden=' + document.getElementById('is_hidden').value;
		}
		if(document.getElementById('hide_show') != null){
			if(document.getElementById('hide_show').checked)
				queryst = queryst + '&hide_show=' + document.getElementById('hide_show').value;
		}
		if(document.getElementById('searchtype') != null){
			queryst = queryst + '&searchtype=' + document.getElementById('searchtype').value;
		}
		if(document.getElementById('individual_name_search') != null){
			queryst = queryst + '&individual_name_search=' + document.getElementById('individual_name_search').value;
		}
		if(document.getElementById('system_code_search') != null){
			queryst = queryst + '&system_code_search=' + document.getElementById('system_code_search').value;
		}
		if(document.getElementById('producer_name_search') != null){
			queryst = queryst + '&producer_name_search=' + document.getElementById('producer_name_search').value;
		}
		if(document.getElementById('producer_number_id_search') != null){
			queryst = queryst + '&producer_number_id_search=' + document.getElementById('producer_number_id_search').value;
		}
		if(document.getElementById('year_var') != null){
			queryst = queryst + '&year_var=' + document.getElementById('year_var').value;
		}	
		//alert(queryst +'  ' + containerid);
		
		queryst = setDocumentElementIdsForProdValidation(queryst);
		//alert(queryst);
    	var url = queryst;
    	//alert(url);
		retrieveURL1( url, containerid, page_request,page,document.forms[0].jsessionid.value,null);
		if(page=='massAttachmentCommissionScheduleStep3' || page=='massAttachmentCommissionScheduleStep3.html'){
				if(document.getElementById('ajax_field_selectAllRecords')!=null && document.getElementById('ajax_field_selectAllRecords').value=="1")
					selectAllReocrdsOfListData("selectAllRecords");			
					
				
			}
		
	}
//----------------end of ajaxpagination -----------------------------------



//----------start of the retrieveURL----------------------------------------------------------
	function retrieveURL1( url, containerid, page_request,page,jsessionid,inet_method)
	{
	  // alert(window.XMLHttpRequest);
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
				loadpage(page_request, containerid,page,inet_method)
			}
	
			//alert(url);
			
			//Added Session Handling Code
			var doVar = "test.do";
			//alert(jsessionid);
			if(jsessionid != null){
				doVar = doVar + ";jsessionid="+jsessionid;
			}
			page_request.open('POST', doVar, true);
			//Ended Code
			page_request.setRequestHeader("Content-Type",
			"application/x-www-form-urlencoded");	
			
			page_request.send(url);
			//alert('6');
		}catch (e){ alert(e)}
	}
//--------------------end of the retrieveURL--------------------------------------------------

//--------------------start of load page------------------------------------------------------
	function loadpage(page_request, containerid,page,inet_method){
	    //	alert('loadpage start..');
		//	alert(page_request.readyState);
		//alert(page_request.status);
		//alert(window.location.href.indexOf("http"));
		//alert(containerid);
		
		if (page_request.readyState == 4 && ((page_request && page_request.status==200) || window.location.href.indexOf("http")==-1)){
			//alert('inside if   ' + containerid);
			//alert('response....'+page_request.responseText);
			if(page_request.responseText != null && page_request.responseText.length >= 10){
				if(page_request.responseText.substr(0,10) != null){
					var result = page_request.responseText.substr(0,10);
					//alert(result);
					if(result.indexOf('ajaxerrors')>=0){
						result = page_request.responseText; //removing ajaxerrors from response
						enableBody();
						callJavaScriptMethodsForAjax(page,inet_method);
						populaterrorinajaxform(result);
						return;
					}
				}		
			}
			if(containerid.length >= 3){
				var str = containerid.substring(0,7);
				//	alert(str);
				//alert(containerid);
				//enableBody();
				if(str == "ajaxxml"){
					document.getElementById(containerid).innerHTML = '';
					//alert(page_request);
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
						
						if(inet_method=='getPolicyNumberList'){
							//alert(nodevalue);
							
							var nodeValueKey = null;
							if(nodevalue.indexOf("=")!=-1){
								nodeValueKey = nodevalue.substring(0, nodevalue.indexOf("="));
								nodevalue = nodevalue.substring(nodevalue.indexOf("=")+1, nodevalue.length);
							}else{
								nodeValueKey = nodevalue;
							}
							
							var fieldid = 'to_select_filter_data';//containerid;
							
							var search = "this.innerHTML,\'" + containerid + "\',\'" + fieldid + "\',\'" + nodevalue + "\'";
							//alert(search);
							var suggest = '<div onmouseover="javascript:suggestOver(this);"';
							suggest += 'onmouseout="javascript:suggestOut(this);"';
							suggest += 'onclick="javascript:managereportsetSearch('+search+');"';
							suggest += 'class="suggest_link">' + nodeValueKey + '</div>';
							
							//alert(suggest);
							document.getElementById(containerid).innerHTML += suggest;
						}else{
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
					}
					if(nodes.length > 0){
						if(document.getElementById(containerid))
							document.getElementById(containerid).style.display = "block";
					}
                     //alert(document.getElementById(containerid).innerHTML)                   
					return;
				}
				
			}
	
			enableBody(containerid);
			
			
			if(containerid == 'downloadUploadDiv')
			{
				showPopUp('downloadUploadDiv','800','300');
			}
			else{
				document.getElementById(containerid).style.display='block';
			}
			
			//alert(document.getElementById(containerid).innerHTML);
			//alert("page-----"+page_request.responseText);
			document.getElementById(containerid).innerHTML=page_request.responseText;
			
			if(document.getElementById("cover")!=null){
				if(document.getElementById("cover").style.display !="block")
					document.documentElement.style.overflow = document.body.style.overflow = "auto";
			}else{
				document.documentElement.style.overflow = document.body.style.overflow = "auto";
			}
			
	
			callJavaScriptMethodsForAjax(page,inet_method);
		}
		//getElementForFocus(containerid); //setting focus for ajax page first enabled field.
	}
	
//----------------------end of load page--------------------------------------------------------	
//-------------------------------------------------------------------------------------------------------------------


/***************** ajax page validation *********************************************/
  
  var req;
  var url ="test.do";
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
		if(method=='closeSendForReviewPopup1'){
	 		clearOuterDiv('popupSendForReview');
	 	}
	 	
	 	var isPageErrorFound = false;
	 	isPageErrorFound = checkForPageErrorFromJavaScript(isPageErrorFound, action, method);
	 	if(isPageErrorFound)
	 		return;
	 	
	 	if(as_dynaValues.indexOf('#')!=-1){ //if # exists in dynaValue
			as_dynaValues = as_dynaValues.replace(/#/g, '_HASH_');
    	}
	 	//show confirmation popup on managesubmitformajax 
	 	var isConfirmDeleteFound = false; 
	    if(as_dynaKeys != null && as_dynaValues != null){
	        var keysArray = as_dynaKeys.split('|');
	        var valuesArray = as_dynaValues.split('|');
	        if(keysArray != null && keysArray.length > 0){
	       		for(var i=0; i< keysArray.length; i++){
	      			var key = keysArray[i];
	      			//alert(key);
	      			if(key == 'showConfirmation' && valuesArray[i] == 'Y'){
	      				showPopUp('commissionConfirmation','400','300');
	      				isConfirmDeleteFound = true;
	      				af_1.dynaKeys.value = as_dynaKeys;
	      				af_1.dynaValues.value = as_dynaValues;
      					af_1.inet_method.value = method;
      					af_1.inet_page.value= action;
      					break;
	      			}
	      		}
	        }
	        if(isConfirmDeleteFound){
	        	document.getElementById('ajax_field_skipDeletePopup').value = 'Y';
	       		return;
	        }
        }
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
	        
	        //Added Session Handling Code 
	        if(url == 'test.do'){
	       		url = url + ";jsessionid="+form.jsessionid.value;		
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
	        	//enableBody();
	        	populaterrorinajaxform(result);
	        		        	
	        }
			else
			{			
				//set_af_1.submit();
				if(set_method=='downloadAppointmentLetter' || set_method=='downloadTerminationLetter')
				{
				managesubmitform(set_af_1, set_method, set_dynaKeys, set_dynaValues, set_inet_page);
				}else{
				manageajaxsubmitform(set_af_1,set_method,set_dynaKeys,set_dynaValues,set_inet_page,set_form_containerid);
				}
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
			
		}
		
		if(is_inet_error='Y')
		{			
			if(document.getElementById('ajax_error_indication_div') != null)
			{
				document.getElementById('ajax_error_indication_div').innerHTML = 'Please correct the errors highlighted in red below';
				document.getElementById('ajax_error_indication_div').style.display='block';
			}
		}
		
		//enableBody();
			
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
		
		
		queryst = setPersonalInformationData(queryst);
		
		var url = queryst;
		//alert(url + " --- " + page);
	  //  alert(containerid);
	     // alert(url);
	     
		
		
    	retrieveURL1( url, containerid, req,page,frm.jsessionid.value,inetmethod);
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

function getCheckedStates()
		{
		 	
		 	var formObject = document.myForm;
		 	
		 	var states_val= "";
		 	
			  for (var i=0; i<formObject.length; i++)
		      {
		      		
		            var n = formObject.elements[i].name;
		            if (n.indexOf("StateDesc_") > -1)		            
		            {
		            	if (formObject.elements[i].checked){
		            		if(states_val == ""){
		            			states_val = formObject.elements[i].value ;
		            		}else{		            			            		
		            			states_val = states_val + ", " + formObject.elements[i].value ;
		            			}
		            	}
		           	}
		      }
		      return states_val;
		}

		function post_value(){	
			//alert(opener.document.forms[0].LicensedStates.value);					
			opener.document.forms[0].LicensedStates.value = getCheckedStates();
			self.close();
		}
		
		 //Mouse over function
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

function getElementForFocus(containerid){
	if(document.getElementById(containerid) == null)
		return;
	
	if(document.getElementById(containerid).getElementsByTagName('') != null){
		var fldArray = document.getElementById(containerid).getElementsByTagName('*');
		//alert(fldArray.length);
		
		var focusfield;
		for(var i=0; i<fldArray.length; i++){
			var fld = fldArray[i];
			if(fld.getAttribute("id") != null){
				if(!document.getElementById(fld.getAttribute("id")).disabled &&
					(fld.type == 'text' || 
					fld.type == 'select-one' ||
					fld.type == 'select-one' ||
					fld.type == 'textarea' ||
					fld.type == 'radio' ||
					fld.type == 'checkbox' ||
					(fld.type == 'hidden' && fld.name=='hiddenfocus'))){
					
					//alert(fld.name + ' -- ' + fld.type);
					focusfield = fld.type;	
					break;
					//if(i == 4)
					//	break;
				}
			}
		}
		
		if(focusfield == 'text' || focusfield == 'checkbox' || focusfield == 'radio' || focusfield == 'hidden')
			focusfield = 'input';
		else if(focusfield == 'select-one' || focusfield == 'select')
			focusfield = 'select';	
			
		//alert(focusfield);
		fldArray = document.getElementById(containerid).getElementsByTagName(focusfield);
		for(var i=0; i<fldArray.length; i++){
			var fld = fldArray[i];
			if(fld.getAttribute("id") != null){
				fld = document.getElementById(fld.getAttribute("id"));
				if(!document.getElementById(fld.getAttribute("id")).disabled){
					fld.focus();
					return;	
				}
			}
		}
		fldArray = document.getElementById(containerid).getElementsByTagName(focusfield);
		for(var i=0; i<fldArray.length; i++){
			var fld = fldArray[i];
			if(fld.getAttribute("id") != null){
				fld = document.getElementById(fld.getAttribute("id"));
				if(!document.getElementById(fld.getAttribute("id")).disabled){
					fld.focus();
					return;	
				}
			}
		}
		fldArray = document.getElementById(containerid).getElementsByTagName(focusfield);
		for(var i=0; i<fldArray.length; i++){
			var fld = fldArray[i];
			if(fld.getAttribute("id") != null){
				fld = document.getElementById(fld.getAttribute("id"));
				if(!document.getElementById(fld.getAttribute("id")).disabled){
					fld.focus();
					return;	
				}
			}
		}
	}
}

function managereport(dynaKeys,dynaValues,method,action){
	// alert(as_dynaKeys); alert(as_dynaValues);
	
//	var as_dynaKeys = dynaKeys;
//	var as_dynaValues = "|"+ reportID + "|";
		
						
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
	if(method.indexOf('INET_SHOW_REPORT')==-1 && method.indexOf('INET_SUBMIT_EXPORT_POPUP')==-1 && 
		method.indexOf('INET_DOWNLOAD_BATCH_REPORT')==-1 && method.indexOf('INET_SHOW_HREF_PAGE')
		&& method.indexOf('INET_SUBMIT_ACCORDIAN_EXPORT_POPUP')==-1)
		disableBody();
	
	if(method != null && method == 'INET_SHOW_HREF_PAGE'){
		document.forms[0].target = "ProducerOne";
	}
				
	document.forms[0].submit();
}

function getPageSource(){
	var source = document.getElementsByTagName('html')[0].innerHTML;
	
	//alert(source);
	var fldArray = document.forms[0].elements;
	//alert(fldArray.length);
	for (var i = 0; i < fldArray.length; i++) {
		var fld = fldArray[i];
		if(fld == null)
			continue;
			
		//alert(fld + ' ' + i);
		if(fld != null && fld.name != null){	
			var name = fld.name;
			var id = fld.id;
			//alert(name + ' : ' + id);
			
			if(id != null && id != '' && id != 'htmlSource'){
				//alert(document.getElementById(id));
				var value = document.getElementById(id).value;
				//alert(name + ' = ' + id + ' = ' + value);
			
				if(value != null && value != ''){ //adding values explicitly bcoz few browser not show entered values in javascript, like Mozilla
					if(source.indexOf("id="+id) != -1){ // in case of IE
						//working fine in IE
					}
					if(source.indexOf("id=\""+id+"\"") != -1){
						//alert(navigator.appName);
						//alert(fld.type);
						if(fld.type == 'textarea'){
							//alert(id);
							source = source.replace("id=\""+id+"\"", "id=\""+id+"\"");
							var source1 = source.substring(0,source.indexOf("id=\""+id+"\""));
							var source2 = source.substring(source.indexOf("id=\""+id+"\""), source.length);
							var source3 = source2.substring(source2.indexOf("id=\""+id+"\""), source2.indexOf(">")+1);
							//alert(source3);
							var source4 = source2.substring(source3.indexOf(">")+1, source2.indexOf("</textarea>"));
							//alert(source4);
							var source5 = source2.substring(source2.indexOf("</textarea>"), source2.length);
							
							//document.getElementById(id).innerHTML = '';
							//document.getElementById('htmlSource1').value = source3;
							//document.getElementById('htmlSource2').value = source4;
							
							//alert(source1);
							//alert(source2);
							//alert(source3);
							//alert(id + ' === ' + value);
							//source3 = source3 + value;
							source4 = value;
							//alert(source4);
							
							source = source1 + source3 + source4 + source5;
						}else if(fld.type == 'select-one'){
							//alert(fld.id + ' = ' + value);
							//alert(fld.options);
							source = source.replace("id=\""+id+"\"", "id=\""+id+"\"");
							var source1 = source.substring(0,source.indexOf("id=\""+id+"\""));
							var source2 = source.substring(source.indexOf("id=\""+id+"\""), source.length);
							var source3 = source2.substring(source2.indexOf("id=\""+id+"\""), source2.indexOf(">")+1);
							var source4 = source2.substring(source3.indexOf(">")+1, source2.indexOf("</select>"));
							var source5 = source2.substring(source2.indexOf("</select>"), source2.length);
							
							var selectSource = '';
							if(fld.options != null && fld.options.length > 0){
								for(var j=0; j<fld.options.length; j++){
									var option = fld.options[j];
									var isSelected = false;
									if(option.value != null && option.value == value){
										isSelected = true;
									}
									
									if(isSelected)
										selectSource = selectSource + "<option value=\"" + option.value + "\" selected=\"selected\">";
									else
										selectSource = selectSource + "<option value=\"" + option.value + "\">";
									
									selectSource = selectSource + option.innerHTML + "</option>";
								}
							}
							
							source4 = selectSource;
							//alert(source4);
							source = source1 + source3 + source4 + source5;
						}else
							source = source.replace("id=\""+id+"\"", "id=\""+id+"\" value=\""+value+"\"");
							
					}
					//source = source.replace("id=\""+id+"\"", "id=\""+id+"\" value=\""+value+"\"");
					//source = source.replace("id="+id, "id="+id + " value="+value);
				}
			}
		}
	}
	
	//alert(source);
	source = source.replace("</ ", "</");
	source = source.replace("<?xml:namespace prefix = css3vml ns = \"urn:schemas-microsoft-com:vml\" />", "");
		
	if(document.getElementById('htmlSource') != null)
		document.getElementById('htmlSource').value = '<html>'+source+'</html>';
}

function callAjaxDelete(containerid){
	if(document.getElementById('ajaxdeletedivid') != null && document.getElementById('ajaxdeletedivid').value != ''){
		containerid = document.getElementById('ajaxdeletedivid').value;
	}

	var inetmethod = document.forms[0].inet_method.value;
	var dynakeys = document.forms[0].dynaKeys.value;
	var dynavalues = document.forms[0].dynaValues.value;
	var page = document.forms[0].inet_page.value;
	
	var page_request;
	var submitUrl = parseElement(document.forms[0]);
    //alert(submitUrl);
    var queryst = submitUrl;
	
	queryst = queryst+ 'dynaKeys='+dynakeys+'inet_method|datadisplayid|'  +'&dynaValues='+dynavalues+inetmethod+'|'+containerid+'|' +'&ajaxpage='+page;
		
	//queryst = setAjaxFieldValuesForSubmit(queryst);
        
	//alert(queryst);
	
    retrieveURL1(queryst, containerid, page_request, page, document.forms[0].jsessionid.value,inetmethod);
}

function submitForDelete(){
	/*var source = document.getElementsByTagName('html')[0].innerHTML;
	var fldArray = document.forms[0].elements;
	for (var i = 0; i < fldArray.length; i++) {
		var fld = fldArray[i];
		if(fld == null)
			continue;
			
		if(fld != null && fld.name != null){	
			var id = fld.id;
			if(id != null && id != '' && id != 'htmlSource'){
				var value = document.getElementById(id).value;
				//alert(name + ' = ' + id + ' = ' + value);
			
				if(value != null && value != ''){ //adding values explicitly bcoz few browser not show entered values in javascript, like Mozilla
					if(source.indexOf("id="+id) != -1){ // in case of IE
						//working fine in IE
					}
					if(source.indexOf("id=\""+id+"\"") != -1){
						//alert(fld.type);
						if(fld.name == 'inet_method')
							source = source.replace("name=\""+name+"\"", "name=\""+name+"\" value=\"delete\"");
						else if(fld.name == 'inet_page')
							source = source.replace("name=\""+name+"\"", "name=\""+name+"\" value=\""+inet_page+"\"");
						else	
							source = source.replace("id=\""+id+"\"", "id=\""+id+"\" value=\""+value+"\"");
					}
				}
			}
		}
	}
	
	//alert(source);
	source = source.replace("</ ", "</");
	source = source.replace("<?xml:namespace prefix = css3vml ns = \"urn:schemas-microsoft-com:vml\" />", "");
		
	document.getElementById('htmlSource').value = '<html>'+source+'</html>';*/
	
	//alert(document.forms[0].getAttribute("action"));
	
	var action = document.forms[0].getAttribute("action");
	if(action.indexOf("?") != -1){
		action = action.substring(0, action.indexOf("?"));
		document.forms[0].setAttribute("action", action);
		//alert(action);
	}
	
	document.forms[0].submit();
	
}

function resolveControlValueForMultipleSelect(node){
	var value = '';
	
	if(node.options != null && node.options.length > 0){
		for(var i=0; i<node.options.length; i++){
			//alert(node.options[i].selected);
			if(node.options[i].selected){
				if(value == '')
					value = node.options[i].value; 
				else
					value = value + ',' + node.options[i].value;
			}	
		}
	}
	//alert(value);
	return value;
}

function loadManageGraphDataTemplate(){
	//alert(document.getElementById('issearch'))
	if(document.getElementById('issearch') != null && document.getElementById('issearch').value == 'Y')
		showPopUp("popupAprooved", "600", "550");
		
 	//alert('call graph' + (document.getElementById('isgraph') != null ? document.getElementById('isgraph').value : ''));
	if(document.getElementById('isgraph') != null && document.getElementById('isgraph').value == 'Y'){
		loadManageGraphFromAJAX();
	}	
}

function loadManageGraphFromAJAX(){
	if(document.getElementById('graphtype') == null || document.getElementById('graphtype').value == '')
		return;
	
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
	//alert(url);
	
	disableBody();
	xhr.onreadystatechange=function(){
  		if(xhr.readyState === 4 && (xhr.status === 200 || xhr.status === 0)){
  			var responseText = xhr.responseText;
  			//alert(responseText);
  			if(responseText == '' || responseText == 'No Data Found'){
  				document.getElementById('graph_div').innerHTML = 'No Data Found';
  				return;
  			}
  			//alert(responseText);
  			var jsonString = responseText.substring(0, responseText.indexOf("#PARAMETERS#"));
  			//alert(jsonString);
    		var dataFromJSON = JSON.parse(jsonString);
    		//var dataFromJSON = xhr.responseText;
			//alert(dataFromJSON);
    		
    		var parameters = responseText.substring(responseText.indexOf("#PARAMETERS#=")+13, responseText.length);
    		//alert(parameters);
    		drawManageGraph(dataFromJSON, parameters);

  		}else if( xhr.readyState === 4 && xhr.status !== 200){
    		document.getElementById('graph_div').innerHTML = "Error loading data: " + xhr.statusText;
    		enableBody();
  		} else {
     		document.getElementById('graph_div').innerHTML = "Generating graph... ";
     		enableBody();
      	}
	};
	//alert('calling22');
	xhr.send(null);
}

function drawManageGraph(dataObject, parameters) {
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
	var haxisformat = '';
	var vaxisformat = '';
	
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
		else if(name == 'HAXISFORMAT')	
			haxisformat = value;
		else if(name == 'VAXISFORMAT')	
			vaxisformat = value;						
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
		
		/*if(i == graphdatatablecolumnsArray.length-1){	
			data.addColumn({type:'number',role:'tooltip'});
			break;
		}*/
			
		data.addColumn(type, name);
		
		if(type == 'number')//to apply currency format on column
			formatter.format(data, i);
			
	}
	
	//data.addColumn({type:'string',role:'tooltip'});
	
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
        width: (width == ' ' ? 1000 : width),
        height: (height == ' ' ? 500 : height),
        is3D: (is3D == ' ' ? false : true),
        isStacked: (isStacked == ' ' ? false : true),
        
        keepAspectRatio: 'false', //to use given height and width parameters value
        //vAxis:{title:'Sale/Expenses',titleTextStyle: {color: 'red', bold:true, fontSize: 20}},
        vAxis: {title: yaxislabel,titleTextStyle: {color: 'black', bold:true, fontSize: 14}, format: vaxisformat},
        //ticks: [0, 500, 1000, 1500, 2000, 2500, 3000, 3500, 4000, 4500, 5000]
        //hAxis:{title:'Month',titleTextStyle: {color: 'red', bold:true, fontSize: 20}},
        hAxis: {title: xaxislabel,titleTextStyle: {color: 'black', bold:true, fontSize: 14}, format: haxisformat},
        bar: {groupWidth: '35%'},
        //tooltip: { isHtml: true},
  		legend: {position:'bottom', textStyle: {color: 'black', fontSize: 12}},
  		colorAxis: {colors: ['#00853f', 'black', '#e31b23']},
  		//backgroundColor: '#81d4fa',
        datalessRegionColor: 'fefbd9',
        //defaultColor: 'yellow',
        displayMode: 'markers',
        //region:'IT',
      	region: 'US',
        //forceIFrame: true,
       	resolution : 'provinces',
       	//markerOpacity: 3.0
        //backgroundColor:'pink',
        //colors: ['red', 'black', 'black', 'purple']
        enableRegionInteractivity: 'true',
        //sizeAxis: {minSize:1,  maxSize: 1},
         
    };
    //alert('drawing')
    // Create and draw the visualization.
    //alert(graphtype)
    enableBody();
    if(graphtype != null){
    	if(graphtype=='bar')
    		new google.visualization.BarChart(document.getElementById('graph_div')).draw(data, options);
     	else if(graphtype=='pie')	
    		new google.visualization.PieChart(document.getElementById('graph_div')).draw(data, options);
    	else if(graphtype=='line')	
    		new google.visualization.LineChart(document.getElementById('graph_div')).draw(data, options);
    	else if(graphtype=='geo')	
    		new google.visualization.GeoChart(document.getElementById('graph_div')).draw(data, options);
    	else if(graphtype=='bubble')	
    		new google.visualization.BubbleChart(document.getElementById('graph_div')).draw(data, options);		
    	else if(graphtype=='column')	
    		new google.visualization.ColumnChart(document.getElementById('graph_div')).draw(data, options);	
    			
    	if(document.getElementById('graphtype') != null)
    		document.getElementById('graphtype').value = graphtype;		
    }else{
    	new google.visualization.ColumnChart(document.getElementById('graph_div')).draw(data, options);
    }
 }

