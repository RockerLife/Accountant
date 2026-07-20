// JavaScript Document

function showHideAddBlock(){
	if(document.forms[0].EffectiveDate.disabled == false)
	{
		manageajaxpageblocklink(document.forms[0],'AddClaimInfo','|inet_skip_validation|ajaxpage|inet_method|inet_eventid|','|Y|ajaxpage|menuAdd|add|','ProfLiabilitySuit_freeform','ProfLiabilitySuit_freeform');
 	    manageajaxpageblocklink(document.forms[0],'Addpublicoffering','|IsBasicPage|inet_skip_validation|ajaxpage|inet_method|inet_eventid|','|Y|Y|ajaxpage|menuAdd|add|','publicprivateofferings_freeform','publicprivateofferings_freeform_freeform_01');
 	}

} 
 function cleanDiv(divID){ 
 	document.getElementById(divID).innerHTML = '';
 }
 
 function sendPaypal(isProd){	
	if(isProd == 'Y')
	{
		document.forms[0].action = "https://www.paypal.com/cgi-bin/webscr";
	}	
	else
	{
		document.forms[0].action = "https://www.sandbox.paypal.com/us/cgi-bin/webscr";
	}
	document.forms[0].submit();
}
function sendAuth(isProd) {	
	if(isProd == 'Y')
	{
		document.forms[0].action = "https://secure.authorize.net/gateway/transact.dll";
	}	
	else
	{
		document.forms[0].action = "https://test.authorize.net/gateway/transact.dll";
	}	
	document.forms[0].submit();
}
 
 function setScroll(){
	window.scrollTop(0, 300);
}

 function setSelectionOfQuotes(){

		for(var i = 0 ; i < 100 ; i++){	
			//alert(i);
			if(document.getElementById("IsQuoteSelected_"+i) == null)
				break;		
			if(document.getElementById("IsQuoteSelected_"+i).value == 'Y') {			
				document.getElementById("IsQuoteSelected_"+i).checked = true;
			}else{
				document.getElementById("IsQuoteSelected_"+i).checked = false ;
			}		
		}	
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

 function setERPOption()
 {
 	try
 	{
 	var field1=document.getElementById('ajax_field_isAccountantRetiring').checked;
 	var field2=document.getElementById('ajax_field_isFirmERP').checked;
 	//alert(field1+'     '+field2);
 	if(field1==true)
 	{
 		
 		document.getElementById('ajax_field_isFirmERP').disabled=true;
 		document.getElementById('ajax_field_oneYear').disabled=true;
 		document.getElementById('ajax_field_threeYear').disabled=true;
 		document.getElementById('ajax_field_fiveYear').disabled=true;
 		document.getElementById('ajax_field_sixYear').disabled=true;
 		document.getElementById('ajax_field_isAccountantRetiring').disabled=false;
 		document.getElementById('ajax_field_nameOfAccountant').disabled=false;
 		document.getElementById('ajax_field_practicePortion').disabled=false;
 	}
 	
 	else if(field2==true)
 	{
 		//alert('hello');
	 	document.getElementById('ajax_field_isAccountantRetiring').disabled=true;
	 	document.getElementById('ajax_field_nameOfAccountant').disabled=true;
	 	document.getElementById('ajax_field_practicePortion').disabled=true;
	 	document.getElementById('ajax_field_isFirmERP').disabled=false;
	 	document.getElementById('ajax_field_oneYear').disabled=false;
	 	document.getElementById('ajax_field_threeYear').disabled=false;
	 	document.getElementById('ajax_field_fiveYear').disabled=false;
	 	document.getElementById('ajax_field_sixYear').disabled=false;
	 	document.getElementById('ajax_field_isFirmERP').checked=true;
	 	document.getElementById('ajax_field_oneYear').checked=true;
	 	document.getElementById('ajax_field_threeYear').checked=true;
	 	document.getElementById('ajax_field_fiveYear').checked=true;
	 	document.getElementById('ajax_field_sixYear').checked=true;
	 	
	 	}
 	else
 	{
 		document.getElementById('ajax_field_isAccountantRetiring').disabled=false;
 		document.getElementById('ajax_field_nameOfAccountant').disabled=false;
 		document.getElementById('ajax_field_practicePortion').disabled=false;
 		document.getElementById('ajax_field_isFirmERP').disabled=false;
 		document.getElementById('ajax_field_oneYear').disabled=false;
 		document.getElementById('ajax_field_threeYear').disabled=false;
 		document.getElementById('ajax_field_fiveYear').disabled=false;
 		document.getElementById('ajax_field_sixYear').disabled=false;
 		document.getElementById('ajax_field_isFirmERP').checked=false;
 		document.getElementById('ajax_field_oneYear').checked=false;
 		document.getElementById('ajax_field_threeYear').checked=false;
 		document.getElementById('ajax_field_fiveYear').checked=false;
 		document.getElementById('ajax_field_sixYear').checked=false;
 		
 	}
 	}
 	catch (e) {
 		// TODO: handle exception
 	}
 }
 	function isAccountantWithUsFromPast()
 	{
 		
 		
 		var field=document.getElementById('ajax_field_isAccountantRetiring1').checked;
 		var field1=document.getElementById('ajax_field_isAccountantRetiring2').checked;
 		var field3=document.getElementById('ajax_field_isSoloAccountant1').checked;
 		var field4=document.getElementById('ajax_field_isSoloAccountant2').checked;
 		var isInsuredWithUsFromPast1=document.getElementById('ajax_field_isInsuredWithUsFromPast1').checked;
 		var isInsuredWithUsFromPast2=document.getElementById('ajax_field_isInsuredWithUsFromPast2').checked;

 		//alert(field+' '+field3+' '+isInsuredWithUsFromPast1);
 		if(field1==true)
 			document.getElementById('ajax_field_practicePortitonOfAccountant').disabled=true;
 		else if(field1==true && field4==true)
 			{
	 			document.getElementById('ajax_field_isInsuredWithUsFromPast1').disabled=true;
	 			document.getElementById('ajax_field_isInsuredWithUsFromPast2').disabled=true;
 			}
 		else if(isInsuredWithUsFromPast1==true && field==true &&  field3==true)
 				document.getElementById('ajax_field_practicePortitonOfAccountant').disabled=true;
 			
 		else if(isInsuredWithUsFromPast2==true && field==true &&  field3==true)
 				document.getElementById('ajax_field_practicePortitonOfAccountant').disabled=true;
			
 		else
 			document.getElementById('ajax_field_practicePortitonOfAccountant').disabled=false;	
 	}
 	
 	function loadDeathDiabilityERP()
 	{
 		var field1=document.getElementById('ajax_field_isAccountantRetiring2').checked;
 		var field2=document.getElementById('ajax_field_isDeathDisabilityERP1').checked;
 		var field3=document.getElementById('ajax_field_isDeathDisabilityERP2').checked;
 		//alert(field1+' '+field2+' '+field3 )
 		if(field2==true)
 			{
 			document.getElementById('ajax_field_isFirmERP1').disabled=true;
 			document.getElementById('ajax_field_isFirmERP2').disabled=true;
 			document.getElementById('ajax_field_yearsOfERP').disabled=true;
 			document.getElementById('ajax_field_deathDisabilityFirmOrAccountantName').disabled=false;
 			document.getElementById('ajax_field_deathDiablityCeasedDate').disabled=false;
 			document.getElementById('ajax_field_isAccountantRetiring1').disabled=true;
 			document.getElementById('ajax_field_isAccountantRetiring2').disabled=true;
 			document.getElementById('ajax_field_nameOfAccountant').disabled=true;
 			document.getElementById('ajax_field_ceasedDate').disabled=true;
 			document.getElementById('ajax_field_isInsuredWithUsFromPast1').disabled=true;
 			document.getElementById('ajax_field_isInsuredWithUsFromPast2').disabled=true;
 			document.getElementById('ajax_field_practicePortitonOfAccountant').disabled=true;
 			
 			}
 		else if(field3==true)
 			{
 			
 			document.getElementById('ajax_field_isFirmERP1').disabled=false;
 			document.getElementById('ajax_field_isFirmERP2').disabled=false;
 			document.getElementById('ajax_field_yearsOfERP').disabled=false;
 			document.getElementById('ajax_field_deathDisabilityFirmOrAccountantName').disabled=false;
 			document.getElementById('ajax_field_deathDiablityCeasedDate').disabled=false;
 			document.getElementById('ajax_field_isAccountantRetiring1').disabled=false;
 			document.getElementById('ajax_field_isAccountantRetiring2').disabled=false;
 			document.getElementById('ajax_field_nameOfAccountant').disabled=false;
 			document.getElementById('ajax_field_ceasedDate').disabled=false;
 			document.getElementById('ajax_field_isInsuredWithUsFromPast1').disabled=false;
 			document.getElementById('ajax_field_isInsuredWithUsFromPast2').disabled=false;
 			document.getElementById('ajax_field_practicePortitonOfAccountant').disabled=false;
 			
 			}
 		else
 			{
 			/*//alert('tata');
 			document.getElementById('ajax_field_isFirmERP1').disabled=false;
 			document.getElementById('ajax_field_isFirmERP2').disabled=false;
 			document.getElementById('ajax_field_yearsOfERP').disabled=false;
 			document.getElementById('ajax_field_deathDisabilityFirmOrAccountantName').disabled=false;
 			document.getElementById('ajax_field_deathDiablityCeasedDate').disabled=false;*/
 			}
 		
 		}


 function setERPEndorsement()
 {
	 try
	 {
 	var field=document.getElementById('ajax_field_isAccountantRetiring1').checked;
 	
 	if(field==true)
 	{//alert(field);
 		document.getElementById('ajax_field_isDeathDisabilityERP1').disabled=true;
 		document.getElementById('ajax_field_isDeathDisabilityERP2').disabled=true;
 		document.getElementById('ajax_field_deathDisabilityFirmOrAccountantName').disabled=true;
 		document.getElementById('ajax_field_deathDiablityCeasedDate').disabled=true;
 		document.getElementById('ajax_field_isFirmERP1').disabled=true;
 		document.getElementById('ajax_field_isFirmERP2').disabled=true;
 		document.getElementById('ajax_field_yearsOfERP').disabled=true;
 		document.getElementById('ajax_field_nameOfAccountant').disabled=false;
 		document.getElementById('ajax_field_ceasedDate').disabled=false;
 		document.getElementById('ajax_field_isInsuredWithUsFromPast1').disabled=false;
 		document.getElementById('ajax_field_isInsuredWithUsFromPast2').disabled=false;
 		document.getElementById('ajax_field_percentagePracticeOfAccountant').disabled=false;

 		/*document.getElementById('ajax_field_isAccountantRetiring1').disabled=true;
 		document.getElementById('ajax_field_isAccountantRetiring2').disabled=true;*/
 		document.getElementById('ajax_field_ceasedDate').disabled=true;
 		document.getElementById('ajax_field_isInsuredWithUsFromPast1').disabled=true;
 		document.getElementById('ajax_field_isInsuredWithUsFromPast2').disabled=true;
 		document.getElementById('ajax_field_practicePortitonOfAccountant').disabled=true;
 		/*document.getElementById('ajax_field_isDeathDisabilityERP1').checked=false;
 		document.getElementById('ajax_field_isFirmERP1').checked=false;
 		*/
 		
 	}
 	
 	var field1=document.getElementById('ajax_field_isAccountantRetiring2').checked;
 	//alert(field1);
 	if(field1==true)
 	
 	{
 		document.getElementById('ajax_field_isSoloAccountant1').checked=false;
 		document.getElementById('ajax_field_isSoloAccountant2').checked=false;
 		document.getElementById('ajax_field_isInsuredWithUsFromPast1').checked=false;
 		document.getElementById('ajax_field_isInsuredWithUsFromPast2').checked=false;
 		document.getElementById('ajax_field_nameOfAccountant').disabled=false;
 		document.getElementById('ajax_field_ceasedDate').disabled=false;
 		document.getElementById('ajax_field_isInsuredWithUsFromPast1').disabled=false;
 		document.getElementById('ajax_field_isInsuredWithUsFromPast2').disabled=false;
 		document.getElementById('ajax_field_practicePortitonOfAccountant').disabled=false;
 		document.getElementById('ajax_field_isDeathDisabilityERP1').disabled=false;
 		document.getElementById('ajax_field_isDeathDisabilityERP2').disabled=false;
 		document.getElementById('ajax_field_deathDisabilityFirmOrAccountantName').disabled=false;
 		document.getElementById('ajax_field_deathDiablityCeasedDate').disabled=false;
 		document.getElementById('ajax_field_isFirmERP1').disabled=false;
 		document.getElementById('ajax_field_isFirmERP2').disabled=false;
 		document.getElementById('ajax_field_yearsOfERP').disabled=false;
 		
 		
 		
 	}
 	
 	/*var field3=document.getElementById('ajax_field_isDeathDisabilityERP2').checked;
 	
 	 if(field1==true && field3==true)
 		 {
 				document.getElementById('ajax_field_isFirmERP1').disabled=true;
 				document.getElementById('ajax_field_isFirmERP2').disabled=true;
 				document.getElementById('ajax_field_yearsOfERP').disabled=true;
 				
 			
 		 }*/
 	/* if(field1!=true && field3!=true && field!=true)
 	 {
 			document.getElementById('ajax_field_isFirmERP1').disabled=false;
 			document.getElementById('ajax_field_isFirmERP2').disabled=false;
 			document.getElementById('ajax_field_yearsOfERP').disabled=false;
 			
 		
 	 }*/
	 }catch(e)
	 {}
 }
 function showHideERPYears()
 {
	 try
	 {
	 	var field1=document.getElementById('ajax_field_isFirmERP1').checked;
	 	//alert(field1);
	 	if(field1==true)
	 		document.getElementById('yearsDiv').style.display = 'block';
	 	else
	 		document.getElementById('yearsDiv').style.display = 'none';
	 }
	 catch(e)
	 {}
}
	 
 
 function loadfirmERP()
 {
	// alert('hi');
 	var field1=document.getElementById('ajax_field_isFirmERP1').checked;
 	var field2=document.getElementById('ajax_field_isFirmERP2').checked;
 	
 	//alert(field1+' '+field2+' '+field3 );
 	if(field1==true)
 		{
 		
 		document.getElementById('ajax_field_deathDisabilityFirmOrAccountantName').disabled=true;
 		document.getElementById('ajax_field_deathDiablityCeasedDate').disabled=true;
 		document.getElementById('ajax_field_isAccountantRetiring1').disabled=true;
 		document.getElementById('ajax_field_isAccountantRetiring2').disabled=true;
 		document.getElementById('ajax_field_nameOfAccountant').disabled=true;
 		document.getElementById('ajax_field_ceasedDate').disabled=true;
 		document.getElementById('ajax_field_isInsuredWithUsFromPast1').disabled=true;
 		document.getElementById('ajax_field_isInsuredWithUsFromPast2').disabled=true;
 		document.getElementById('ajax_field_practicePortitonOfAccountant').disabled=true;
 		document.getElementById('ajax_field_isDeathDisabilityERP1').disabled=true;
 		document.getElementById('ajax_field_isDeathDisabilityERP2').disabled=true;
 		
 		}
 	else if(field2==true)
 		{
 		
 		document.getElementById('ajax_field_isFirmERP1').disabled=false;
 		document.getElementById('ajax_field_isFirmERP2').disabled=false;
 		document.getElementById('ajax_field_yearsOfERP').disabled=false;
 		document.getElementById('ajax_field_deathDisabilityFirmOrAccountantName').disabled=false;
 		document.getElementById('ajax_field_deathDiablityCeasedDate').disabled=false;
 		document.getElementById('ajax_field_isAccountantRetiring1').disabled=false;
 		document.getElementById('ajax_field_isAccountantRetiring2').disabled=false;
 		document.getElementById('ajax_field_nameOfAccountant').disabled=false;
 		document.getElementById('ajax_field_ceasedDate').disabled=false;
 		document.getElementById('ajax_field_isInsuredWithUsFromPast1').disabled=false;
 		document.getElementById('ajax_field_isInsuredWithUsFromPast2').disabled=false;
 		document.getElementById('ajax_field_practicePortitonOfAccountant').disabled=false;
 		document.getElementById('ajax_field_isDeathDisabilityERP1').disabled=false;
 		document.getElementById('ajax_field_isDeathDisabilityERP2').disabled=false;
 		
 		}
 	else
 		{
 		
 		}
 	
 	}
 
 function getMonthDiff() {
		
		var d1=document.getElementById("EffectiveDateFrom").value;
		var d2=document.getElementById("EffectiveDateTo").value;
		
		//alert(d1+'   '+d2);
		
		var parts1 =d1.split('/');
		var dateFrom = new Date(parts1[2],parts1[0],parts1[1] ); 
		
		var parts2 =d2.split('/');
		var dateTo = new Date( parts2[2],parts2[0], parts2[1]); 
		
		
		 var monthdiff= dateTo.getMonth() - dateFrom.getMonth() +  (12 * (dateTo.getFullYear() - dateFrom.getFullYear()));
		
		 if(!isNaN(monthdiff))
			 { //alert(monthdiff);
				 if(monthdiff>6)
					 {
					 document.getElementById("downloadDiv").style.display = "none";
					 document.getElementById("effectiveDateDiv").style.display = "block";
					 
					 //alert("Selected effective date range can't be exceed 6 months");
					 
					 
					 }
				 else
					 {
					 document.getElementById("downloadDiv").style.display = "block";
					 document.getElementById("effectiveDateDiv").style.display = "none";
					 }
				}
		 
		 }
 function getStuffedString(dstID,field)
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
 function setInputValuesForRequote()
 {
	 //alert('hello');
 	getStuffedString('revenueCount','requote_revenue');
 	getStuffedString('aopCodes','aop_search');
 	getStuffedString('stateCodes','StateCode_search');

 }
 
 function manageEndorsementTypes()
 {
/* 	var  cancellEndorsement=document.getElementById('isShowCancelltionEndorsement').value;
 	
 //alert(cancellEndorsement);
 	if(cancellEndorsement=='N')
 	  document.getElementById("ajax_field_endorsementType").disabled = true;*/
 	var  isManualPremiumPolicy=document.getElementById('isManualPremiumPolicy').value;
 	//alert(isManualPremiumPolicy);
 	if(isManualPremiumPolicy=='Y')
 		{
 		document.getElementById('errorDivision').style.display = 'Block';
 		
 		  document.getElementById("ajax_field_endorsementType2").disabled = true;}

 }

 function setPercentageFormat(id)
 {
 	try
 	{
 	
 	if (document.getElementById(id) != null) {
 		var percentageAmount=document.getElementById(id).value;
 		if(percentageAmount.length>0 && !percentageAmount.includes('%'))
 			document.getElementById(id).value=percentageAmount+'%';
 		alert(Commission.value);
 	}
 	}
 	catch(e)
 	{}
 		
 }
 function updateBrokerStatus(fld)
 {
 	var policyNumber=document.getElementById(fld).value;
 	//alert(policyNumber);
 	if(policyNumber!=null)
 		{
 		document.getElementById("ajax_field_StatusKey").value=6;
 		
 		}
 }
 function loadBrokeragePage()
 {
 	
 	try
 	{
 		//readonlyDropdown($("#ajax_field_ProducerCode"));
 		var brokerEffectiveDate = document.getElementById("ajax_field_BrokerPolicyEffectiveDate");
 		if(brokerEffectiveDate.value==null || brokerEffectiveDate.value=='')
 			brokerEffectiveDate.value= getPresentDate();
 			
 		var lobKey = document.getElementById("ajax_field_LOBKey").value;
 		if(lobKey==10)
 			document.getElementById('otherLOBDescID').style.display="block";
 		else
 			document.getElementById('otherLOBDescID').style.display="none";
 			
 		var docType = document.getElementById("ajax_field_DocumentType").value;
 		if(docType=='Other')
 			document.getElementById('otherDocDescID').style.display="block";
 		else
 			document.getElementById('otherDocDescID').style.display="none";
 		
 	}
 	catch(e5)
 	{}
 	
 	try
 	{
 	
 		
 		var field1=document.getElementById('ajax_field_isConvertedPolicy');
 		if(field1!=null)
 			{
 			//alert(field1.value);
 				if(field1.value=='Y')
 					readonlyDropdown($("#ajax_field_LOBKey"));
 				else
 					removeReadonlyDropdown($("#ajax_field_LOBKey"));
 			}
 	}
 	catch(e4)
 	{}
 try
 {
 	
 		
 	//alert('HI');
 	amountMask = new Mask("$#,###", "number");
 	amountDecimalMask = new Mask("$#,###.##");
 	
 	 zipMask = new Mask("#####-####");
 	var BPremium = document.getElementById("ajax_field_Premium");
 	var BPremiumTax = document.getElementById("ajax_field_PremiumTax");
 	var fees=document.getElementById("ajax_field_Fees");
 	var TotalCalculatedPremium=document.getElementById("ajax_field_TotalCalculatedPremium");
 	var ExpiringPremium=document.getElementById("ajax_field_ExpiringPremium");
 	setDecimalMasking('ajax_field_Premium');
 	setDecimalMasking('ajax_field_PremiumTax');
 	setDecimalMasking('ajax_field_Fees');
 	setDecimalMasking('ajax_field_TotalCalculatedPremium');
 	
 	if (ExpiringPremium != null) {
 		ExpiringPremium.value = amountMask.format(ExpiringPremium.value);
 		amountMask.attach(ExpiringPremium);
 	}
     var zip = document.getElementById("ajax_field_Zip");
     if (zip != null) {
           zip.value = zipMask.format(zip.value);
           zipMask.attach(zip);
     }
    
 	phoneMask = new Mask("(###)###-####");
 	var wrkphone = document.getElementById("ajax_field_Phone");
 	if (wrkphone != null) {
 		wrkphone.value = phoneMask.format(wrkphone.value);
 		phoneMask.attach(wrkphone);
 	}	
 	
 }
 catch(e)
 {}
 try
 {
 	var field1=document.getElementById("ajax_field_StatusKey").value;
 	//alert(field1);
 	if(field1==6)
 		{
 		document.getElementById('premiumI').style.display="block";
 		document.getElementById('premiumNI').style.display="none";
 		document.getElementById('comissionI').style.display="block";
 		document.getElementById('comissionNI').style.display="none";
 		
 		}
 	else if(field1==3)
 		{
 		document.getElementById('premiumI').style.display="block";
 		document.getElementById('premiumNI').style.display="none";
 		document.getElementById('comissionI').style.display="none";
 		document.getElementById('comissionNI').style.display="block";
 		}
 	else
 		{
 		document.getElementById('premiumI').style.display="none";
 		document.getElementById('premiumNI').style.display="block";
 		document.getElementById('comissionI').style.display="none";
 		document.getElementById('comissionNI').style.display="block";
 		}
 }
 catch(e1)
 {}
 try
 {
 	var stateCode=document.getElementById("ajax_field_StatusKey").value;
 	//alert(stateCode);
 	if(stateCode=='TX' || stateCode=='NJ' || stateCode=='WV')
 		{
 		document.getElementById('PremiumTaxI').style.display="block";
 		document.getElementById('PremiumTaxNI').style.display="none";
 		
 		
 		}
 	else
 		{
 		document.getElementById('PremiumTaxI').style.display="none";
 		document.getElementById('PremiumTaxNI').style.display="block";
 		
 		}
 	
 }
 catch(e2)
 {}
 try
 {
 	setPercentageFormat('ajax_field_Commission');
 	
 }
 catch(e11)
 {}
 setBrokerPolicyDates('ajax_field_PLEffectiveDate','ajax_field_PLExpirationDate');
 showHideInsuranceHistoryBrokerage();
 showHideExpiringInfoBrokerage();
 setBrokerPolicyDates('ajax_field_BrokerPolicyEffectiveDate','ajax_field_BrokerPolicyExpirationDate');
 }
 function showHideOther()
 {
 	try
 	{
 		var lobKey = document.getElementById("ajax_field_LOBKey").value;
 		if(lobKey==10)
 			document.getElementById('otherLOBDescID').style.display="block";
 		else
 			document.getElementById('otherLOBDescID').style.display="none";
 	}catch(e)
 	{}
 	
 	try
 	{
 		var docType = document.getElementById("ajax_field_DocumentType").value;
 		if(docType=='Other')
 			document.getElementById('otherDocDescID').style.display="block";
 		else
 			document.getElementById('otherDocDescID').style.display="none";
 	}catch(e)
 	{}
 	
 	showHideInsuranceHistoryBrokerage();	
 } 

 function showHideInsuranceHistoryBrokerage()
 {
 	try
 	{
 		//alert('hi');
 		var field1=document.getElementById('isNewBrokerPolicy').value;
 		//alert(field1);
 		var field2=document.getElementById('ajax_field_RenewAsBrokerage').value;
 		//alert(field2);
 		var lob = document.getElementById("ajax_field_LOBKey").value;
 		
 		//alert(lob);
 		//alert('isNewBrokerPolicy :'+field1+'  RenewAsBrokerage'+field2+' LOB :'+lob+' TransactionTypeKey :'+field);
 		if(lob==2)
 			document.getElementById('priorInsuranceDiv').style.display="none";
 		else if(field1=='Y' && field2=='N')
 			document.getElementById('priorInsuranceDiv').style.display="none";
 		else if(document.getElementById('AccountID')!=null)
 			{
 			var accountID=document.getElementById('AccountID').value;
 			//alert(accountID);
 			if(accountID!=null && accountID!='')
 				document.getElementById('priorInsuranceDiv').style.display="none";
 			else
 				document.getElementById('priorInsuranceDiv').style.display="block";
 			}
 		else
 			document.getElementById('priorInsuranceDiv').style.display="block";
 	}
 	catch(e)
 	{}
 		
 }

 function showHideExpiringInfoBrokerage()
 {
 try
 {
 	var field=document.getElementById('ajax_field_TransactionTypeKey').value;
 	if(field==1)
 		document.getElementById('expiringInfoDiv').style.display="none";
 	 else
 		 document.getElementById('expiringInfoDiv').style.display="block";
 		 
 	}
 catch(e)
 {}
 }

 function setBrokerage()
 {
 	try
 	{
 		var isBrokerageDoc=document.getElementById('isBrokerageDoc');
 		if(isBrokerageDoc!=null)
 			{
 			//alert(isBrokerageDoc.checked);
 				if(isBrokerageDoc.checked==true)
 					document.getElementById('isBrokerageDocument').value='Y';
 				else
 					document.getElementById('isBrokerageDocument').value='N';
 			}
 	}
 	catch(e)
 	{}
 }

 function setPercentageFormatInteger(id)
 {
 	try
 	{
 	
 	if (document.getElementById(id) != null) {
 		var percentageAmount=document.getElementById(id).value;
 		if(percentageAmount.length>0 && !percentageAmount.includes('%'))
 			document.getElementById(id).value=percentageAmount+'%';
 		//alert(Commission.value);
 	}
 	}
 	catch(e)
 	{}
 		
 }
 
 function loadCComments()
 {
 	document.getElementById('ajax_field_CComents').value=document.getElementById('ajax_field_ContractComments').value;
 	}
 function setContractComments()
 {
 //alert('hi');	
 document.getElementById('ajax_field_ContractComments').value=document.getElementById('ajax_field_CComents').value;
 }

 function setPercentageFormat(id)
 {
 	try
 	{
 		percentageMask = new Mask("##.###");
 		var data=document.getElementById(id);
 		
 		if (data != null) {
 			var comission=data.value;
 				comission=comission.substring(0, comission.length);
 				comission = percentageMask.format(comission);
 			percentageMask.attach(data);
 			if(comission.length>0 && !comission.includes('%'))
 				data.value=comission+'%'
 				//data.value='12';
 			alert(Commission.value);
 		}	
 	}
 	catch(e)
 	{}
 }
 function manageLOB(id)
 {
 	try
 	{
 		var field=document.getElementById(id);
 		if(field!=null)
 		{	
 			if(field.value!=null && field.value!='')
 				showPopUp('popupsave1','500','400');
 		
 		}
 	}
 	catch(e)
 	{}
 }


 function showHideBrokerSetion()
 {
 	try
 	{
 	var field1=document.getElementById("ajax_field_isBrokeredY").checked;
 	var field2=document.getElementById("ajax_field_isBrokeredN").checked;
 	//alert(field1+'   '+field2);
 	
 	if(field1==false && field2==false)
 		{
 		$('#BrokerKey').prop('disabled', 'false');
 	
 		$('#CarrierKey').prop('disabled', 'false');
 	
 		}
 		
 	if(field1==true)
 		{
 		$("#BrokerKey").prop("disabled", '');
 		
 		$("#CarrierKey").prop("disabled", '');
 			
 		}
 	if(field2==true){
 		
 		$('#BrokerKey').prop("disabled", 'false');
 		
 		$("#CarrierKey").prop("disabled", 'false');
 		
 		}
 	
 	}
 	catch(e)
 	{}
 	
 }
 /*function setBrokerPolicyDates(field1,field2)
 {
 	try
 	{
 		
 		var effectiveDate= document.getElementById(field1);
 		
 		if(effectiveDate !=null)
 			{
 			//alert(effectiveDate);
 			var today=null;
 			if(effectiveDate.value=='')
 				today = new Date();
 			if(effectiveDate.value!='')
 			{
 				today=new Date(effectiveDate.value);
 				
 				//alert(today);
 				var dd = String(today.getDate()).padStart(2, '0');
 				var mm = String(today.getMonth() + 1).padStart(2, '0'); //January is 0!
 				var yyyy = today.getFullYear();
 				var expYear=parseInt(yyyy)+1;
 				today = mm + '/' + dd + '/' + yyyy;
 				var expiryDate=mm + '/' + dd + '/' + expYear;
 				document.getElementById(field1).value=today;
 				document.getElementById(field2).value=expiryDate;
 			}
 			}
 		
 	}
 	catch(e10)
 	{
 		
 	}	
 }*/
 function setDecimalMasking(fld)
 {
 	
 	var fldValue=document.getElementById(fld).value;
 	fldValue=fldValue.replace(/[^\d.-]/g, '');
 	if(fldValue!='')
 		{
 			const formatter = new Intl.NumberFormat('en-US', {
 		  style: 'currency',
 		  currency: 'USD',
 		  minimumFractionDigits: 2
 		
 		})
 		fldValue=formatter.format(parseFloat(fldValue).toFixed(2));
 		//alert(fldValue);
 		document.getElementById(fld).value=fldValue;
 	}
 	else
 		document.getElementById(fld).value=fldValue;
 }
 
 function getPresentDate()
 {
 	var today = new Date();
 	var dd = today.getDate();

 	var mm = today.getMonth()+1; 
 	var yyyy = today.getFullYear();
 	if(dd<10) 
 	    dd='0'+dd;
 	if(mm<10) 
 	   mm='0'+mm;
 		
 	today = mm+'/'+dd+'/'+yyyy;
 	
 	return today;
 	
 }

 function showotherdiv(val, divid){
		if(val == 'other'){
			document.getElementById(divid).style.display="block";
			document.getElementById("SearchHidden").value = "other";
		}else{
			document.getElementById(divid).style.display="none";
			document.getElementById("SearchHidden").value = "";
		}
	}	
 
 function calculateTotalPremium()
 {

 	try
 	{
 		var premium=document.getElementById("ajax_field_Premium");
 		var tax = document.getElementById("ajax_field_PremiumTax");
 		var fees=document.getElementById("ajax_field_Fees");
 		var totalPremium=0;
 		if(premium!=null)
 			{
 				premium=premium.value.replace(/[&\/\\#,+()$~%'":*?<>{}]/g, '');
 				premium=premium!=''?parseFloat(premium):0;
 				
 			}
 		if(tax!=null)
 			{
 				tax=tax.value.replace(/[&\/\\#,+()$~%'":*?<>{}]/g, '');
 				tax=tax!=''?parseFloat(tax):0;
 				
 			
 			}
 		if(fees!=null)
 			{
 			
 				fees=fees.value.replace(/[&\/\\#,+()$~%'":*?<>{}]/g, '');
 				fees=fees!=''?parseFloat(fees):0;
 				
 			}
 		totalPremium=premium+tax+fees;
 		//alert(totalPremium);
 		document.getElementById("ajax_field_TotalCalculatedPremium").value=totalPremium;
 		setDecimalMasking('ajax_field_TotalCalculatedPremium');
 	}
 	catch(e7)
 	{
 		
 	}	

 }
  function setworkq()
{
	var field=document.getElementById('setBrokerSearch').checked;
	if(field==true)
	{
		document.getElementById('WQIsBrokered_search').value='Y';
		document.getElementById('setBrokerSearch').value='Y';
		
		}
	else
	{
		document.getElementById('WQIsBrokered_search').value='N';
		document.getElementById('setBrokerSearch').value='N';
	}
		
	//	alert(document.getElementById('WQIsBrokered_search').value);
}
function setPaymentModeType()
{
	try {
		var field = document.getElementById('PaymentMode').value;
		//otherIPFSDiv
		//alert(field);
		if (field != '') {
			if (field == 'paypal' || field == 'ach') {
				document.getElementById("otherPaymentButtonDiv").style.display = "none";
				document.getElementById("EFTPaymentButtonDiv").style.display = "none";
				document.getElementById("otherIPFSDiv").style.display = "none";
				document.getElementById("CC_ACHPaymentDiv").style.display = "table-row";
			} else if (field == 'finance') {
				document.getElementById("otherPaymentButtonDiv").style.display = "none";
				document.getElementById("EFTPaymentButtonDiv").style.display = "none";
				document.getElementById("otherIPFSDiv").style.display = "table-row";
				document.getElementById("CC_ACHPaymentDiv").style.display = "none";
			} else if (field == 'eft') {
				document.getElementById("otherPaymentButtonDiv").style.display = "none";
				document.getElementById("EFTPaymentButtonDiv").style.display = "table-row";
				document.getElementById("otherIPFSDiv").style.display = "none";
				document.getElementById("CC_ACHPaymentDiv").style.display = "none";
			} else  {
				document.getElementById("otherPaymentButtonDiv").style.display = "table-row";
				document.getElementById("EFTPaymentButtonDiv").style.display = "none";
				document.getElementById("otherIPFSDiv").style.display = "none";
				document.getElementById("CC_ACHPaymentDiv").style.display = "none";
			}
		}
		else {
			document.getElementById("otherPaymentButtonDiv").style.display = "none";
			document.getElementById("EFTPaymentButtonDiv").style.display = "none";
			document.getElementById("otherIPFSDiv").style.display = "none";
			document.getElementById("CC_ACHPaymentDiv").style.display = "none";
		}
	}
	catch (err) { }
}
function populateTotalAOPValue(fieldPrefix,totalValContainerid){
	 var total = 0;
	 var myVal;

	 try{
	// alert(fieldPrefix);
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
								return false;
            		  }
            		  else
            		  {
	 		    		var ival= parseInt(myVal);
	 		    		total =parseInt(total) +ival;
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
			//alert('hi');
		document.getElementById(totalValContainerid).className = "txt12BoldRed";
		}
	}
	showOtherDesc();
	 }catch (e) {
		// TODO: handle exception
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
function populateEstimateFormYearEndDate(thisField)
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
				  	 if(checkEstimateFormCurrentYear(curYear) == false)
						{
							//setTimeout(document.forms[0].fieldName.select(), 1);
							thisField.select();
							$(thisField).val("");
							thisField.focus();
							alert("The Year should be current or one year back than the Policy Effective Date.  Please enter again.");
							
								//setFocus('YearEndDate_0');
//								thisField.focus();
								//thisField.select();
							
							return false;
						}
						
				  //	var objName =curMonth+"/"+"01/"+curYear;
				  	var totalFields = getTotalNoOfField('YearEndDate_');
				  	
				  	
				  	for(var i=1; i<totalFields;i++){
				  	
				  		var nextYear = curMonth + "/" + ( parseInt(curYear)-i);	  		
				  		document.getElementById("YearEndDate_"+i).value = nextYear;
				  		
				  	}
			  	}
	  		
	  }
}

function checkEstimateFormCurrentYear(curYear){
	var policystartdate = document.getElementById('ajax_field_PolicyExpirationDatePross').value;
	if($.trim(policystartdate) == ''){
		policystartdate = $('input[id=ajax_field_PolicyExpirationDatePross]').val();
	}
	//alert('policystartdate ' + policystartdate);
	var policyYear = policystartdate.substring(policystartdate.lastIndexOf("/")+1,policystartdate.length);
	//alert(policyYear);
	if(parseInt(curYear) < policyYear-1 || parseInt(curYear) > policyYear)
	{
		return false;
	}
	
	
	
	/*var d = new Date();	
	var curr_year = d.getFullYear(); 
	if(parseInt(curYear) < curr_year-1 || parseInt(curYear) > curr_year+1)
	{
		return false;
	}*/
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