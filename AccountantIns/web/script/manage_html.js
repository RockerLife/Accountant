//-------------------------------------------------- include the page in html----------------------------------------------------

function rightclickmenu(page, action, dynakeys, dynavalues, containerid)
{
   if(containerid!='')
   {
    var page_request = false;
    retrieveURL1( page, containerid, page_request);
    }else{
      submitform(page,action,dynakeys,dynavalues);
    }
}          

function ajaxpageblocklink(page, action, dynakeys, dynavalues, containerid)
{
    var page_request = false;
    retrieveURL1( page, containerid, page_request);
}

function ajaxpage(page, action,dynakeys,dynavalues,containerid)
{
     var page_request = false;
     retrieveURL1( page, containerid, page_request);
}


function ajaxsubmitform(page, action, dynakeys, dynavalues, containerid)
{  
  	  submitform(page,action,dynakeys,dynavalues);
}
	
function submitform(a,b,c,d)
{
		window.open(a,'_self');
}
		
 
function showdialogbox(currentpage,method,dynakeys,dynavalues,containerid,nextpage)
{
		window.document.body.disabled=true;
		var myArguments = new Object();
		//myArguments.param1 = 'Driver';
		//setOpacity(document.body, 50);//Sets the opacity to 50%
		window.showModalDialog(nextpage , '', "dialogwidth: 800px; dialogheight: 500px; resizable: No"); 
		window.document.body.disabled=false;
			
}
		