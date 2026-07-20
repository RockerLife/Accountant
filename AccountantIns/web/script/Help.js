//global variable for holding the help window
var objWinHelp = null;
var g_strHelpContext = null;

//opens the help window with frames (page level)
function openHelp(strContext) {
	g_strHelpContext = strContext;
	var strPage;
	var strHelpURL;
	var found = false;
	var count = 0;

	if (openHelp.arguments.length >= 2) {
		strPage = openHelp.arguments[1];
	}
	else {
		strPage = '';
	}
	var strPageToDisplay = strContext + '/Help/' + strFindFile(strPage);
	var queryString;
	if (strPageToDisplay.length > 0) {
		queryString = '?turl=' + escape(strPageToDisplay)
	}
	
	objWinHelp = window.open(strContext +
				'/Help/default.htm' + queryString, 'winCISHelp', 'scrollbars=yes, resizable=yes, width=600, height=400, top=' +
				((screen.availHeight - 400)/2) + ', left=' + ((screen.availWidth- 600)/2));
	objWinHelp.focus();
	return false;
}

//shows the field level help
// binds the close function to the onmousedown function of the body tag
function showFieldHelp(strContext, strField, event) {
	g_strHelpContext = strContext;
	var objHelp = document.getElementById('divFieldHelp');
	var objBody;
	var left = 0;
	var top = 0;
	var width = 400;
	var height = 200;
	var pageWidth;
	var pageHeight;
	if (! event) event = window.event;
	if (! objHelp) {
		objBody = document.getElementsByTagName('body');
		objHelp = document.createElement('IFRAME');
		objHelp.src = g_strHelpContext + '/blank.htm';
		objHelp.id = 'divFieldHelp';
		objHelp.style.border = '1px solid #000000';
		objHelp.style.borderStyle = 'solid';
		objHelp.style.backgroundColor = '#FFFFFF';
		objHelp.style.width = '400px';
		objHelp.style.height = '200px';
		objHelp.style.position = 'absolute';
		objHelp.style.zIndex = '99999';
		objHelp.style.display = 'none';
		objBody[0].appendChild(objHelp);
		if (navigator.userAgent.indexOf('MSIE') > -1) {
			objBody[0].attachEvent('onmousedown', closeFieldHelp);
		} else {
			objBody[0].onmousedown = closeFieldHelp;
		}
	}
	if (objHelp) {
		if (event.pageX || event.pageY) {
			left = event.pageX;
			top = event.pageY;
		} else if (event.clientX || event.clientY) {
			left = event.clientX + document.body.scrollLeft;
			top = event.clientY + document.body.scrollTop;
		}
		if (showFieldHelp.arguments.length >= 4) {
			objHelp.style.width = showFieldHelp.arguments[3];
		}
		if (showFieldHelp.arguments.length >= 5) {
			objHelp.style.height = showFieldHelp.arguments[4];
		}
		//check the left and top posistions to make sure the window fits on the displayed page
		if (objHelp.style.width) width = getPixelNumber(objHelp.style.width);
		if (objHelp.style.height) height = getPixelNumber(objHelp.style.height);
		if (document.body.clientWidth) {
			pageWidth = document.body.clientWidth;
		} else if (window.innerWidth) {
			pageWidth = window.innerWidth;
		}
		if (document.body.clientHeight) {
			pageHeight = document.body.clientHeight;
		} else if (window.innerHeight) {
			pageHeight = window.innerHeight;
		}
		if (width + left > pageWidth) {
			left -= width;
		}
		if (height + top > pageHeight) {
			top -= height;
		}
		objHelp.style.left = left;
		objHelp.style.top = top;
		objHelp.src = strContext + '/Help/' + strFindFile(strField);
		objHelp.style.display = 'block';
	}
	return false;
}

//closes the visible field level help
function closeFieldHelp() {
	var objHelp = document.getElementById('divFieldHelp');
	if (objHelp != null) {
		objHelp.style.display = 'none';
		objHelp.src = g_strHelpContext + '/blank.htm';
	}
}

function getPixelNumber(strPixel) {
	var intValue = 0;
	if (strPixel) {
		intValue = parseInt(strPixel);
	}
	return intValue;
}

//maps the fields to help pages
function strFindFile(strField) {
	var strReturnURL = '';
	switch (strField.toLowerCase()) {
		//page level lookups
		case '/additionalcoverages.jsp':
			strReturnURL = 'coveragespolicycoveragesnonfleetandfleetapplication.htm';
			break;
		case '/address.jsp':
			strReturnURL = 'policyinfoaddressesnonfleetandfleetapplication.htm';
			break;
		case '/audithistory.jsp':
			strReturnURL = 'policyinfouserhistorynonfleetandfleetapplication.htm';
			break;
		case '/businesslist.jsp':
			strReturnURL = 'commercialconnectionhome.htm';
			break;
		case '/errors.jsp':
			strReturnURL = 'errorsnonfleetandfleetapplication.htm';
			break;
		case '/hirednonowned.jsp':
			strReturnURL = 'coverageshirednonownednonfleetandfleetapplication.htm';
			break;		
		case '/policyinfo.jsp':
			strReturnURL = 'policyinfogeneralnonfleetandfleetapplication.htm';
			break;
		case '/premiumratemod.jsp':
			strReturnURL = 'premiumsummarypremiumratemodificationnonfleetandfleetapplication.htm';
			break;
		case '/premiumsummary.jsp':
			strReturnURL = 'premiumsummarynonfleetandfleetapplication.htm';
			break;
		case '/vehiclegaraginglocation.jsp':
			strReturnURL = 'vehicleinformationgaraginglocationpopupnonfleetandfleet.htm';
			break;
		case '/vehicleinfocp.jsp':
			strReturnURL = 'vehicleinfocommercialpassengernonfleetandfleetapplication.htm';
			break;
		case '/vehicleinfomisc.jsp':
			strReturnURL = 'vehicleinfomiscellaneousnonfleetandfleetapplication.htm';
			break;
		case '/vehicleinfopublic.jsp':
			strReturnURL = 'vehicleinfopublicnonfleetandfleetapplication.htm';
			break;
		case '/vehicleinfottt.jsp':
			strReturnURL = 'vehicleinfotrucktractortrailernonfleetandfleetapplication.htm';
			break;
		case '/vehiclesearch.jsp': 
			strReturnURL = 'vehiclesearchnonfleetandfleetapplication.htm';
			break;
		case '/underwriting.jsp':
			strReturnURL ='underwritingquestionsnonfleetandfleetapplication.htm';
			break;
		case '/filings.jsp':
			strReturnURL ='policyinfofilingsnonfleetandfleetapplication.htm';
			break;
		case '/otherinterestentry.jsp':
			strReturnURL ='otherinterestsinfononfleetandfleetapplication.htm';
			break;	
		case '/otherinterestlist.jsp':
			strReturnURL ='otherinterestssearchnonfleetandfleetapplication.htm';
			break;
		case '/otherinterestassignment.jsp':
			strReturnURL ='otherinterestsassignnonfleetandfleetapplication.htm';
			break;
        case '/driversearch.jsp':
			strReturnURL = 'driverssearchnonfleetandfleetapplication.htm';
			break;
        case '/drivermvrinfo.jsp':
			strReturnURL = 'driversmvrinfononfleetandfleetapplication.htm';
			break;
        case '/drivermvrerrors.jsp':
			strReturnURL = 'driversmvrerrorsnonfleetandfleetapplication.htm';
			break;
		case '/driverinformation.jsp':
			strReturnURL = 'driversinfononfleetandfleetapplication.htm';
			break;
		case '/equipmentlist.jsp':
			strReturnURL = 'vehiclesequipmentnonfleetandfleetapplication.htm';
			break;
		case '/ownerslist.jsp':
			strReturnURL = 'vehiclesownersnonfleetandfleetapplication.htm';
			break;	
		case '/vehiclecoverages.jsp':
			strReturnURL = 'vehiclescoveragesnonfleetandfleetapplication.htm';
			break;	
		case '/vehicleaddcoverages.jsp':
			strReturnURL = 'vehiclesadditionalcoveragesnonfleetandfleetapplication.htm';
			break;	
		case '/vehiclecoverageassignment.jsp':
			strReturnURL = 'coveragesvehiclecoveragesnonfleetandfleetapplication.htm';
			break;
		case '/vehicleotherinterests.jsp':
			strReturnURL = 'vehiclesotherinterestsnonfleetandfleetapplication.htm';
			break;
		case '/documents.jsp':
			strReturnURL = 'documentsnonfleetandfleetapplication.htm';
			break;
		//field level lookups
		case 'accidentprevention':
			strReturnURL = 'gloss_accidentprevention.htm';
			break;
		case 'action':
			strReturnURL = 'gloss_action.htm';
			break;
		case 'actiondriversearch':
			strReturnURL = 'gloss_actiondriversearch.htm';
			break;	
		case 'actionvehiclesearch':
			strReturnURL = 'gloss_actionvehiclesearch.htm';
			break;	
		case 'addressotherinterestsearch':
			strReturnURL = 'gloss_address.htm';
			break;
		case 'address1':
			strReturnURL = 'gloss_address1.htm';
			break;
		case 'address2':
			strReturnURL = 'gloss_address2.htm';
			break;
		case 'agentcommissionreduction':
			strReturnURL = 'gloss_agentcommissionreduction.htm';
			break;
		case 'agentid':
			strReturnURL = 'gloss_agentid.htm';
			break;
		case 'agentnotes':
			strReturnURL = 'gloss_agentnotes.htm';
			break;
		case 'agentnumber':
			strReturnURL = 'gloss_agentnumber.htm';
			break;
		case 'agenttohomeofficedate':
			strReturnURL = 'gloss_refertohomeofficedate.htm';
			break;
		case 'amtapplied':
			strReturnURL = 'gloss_amtapplied.htm';
			break;
		case 'antilockbrakes':
			strReturnURL = 'gloss_antilockbrakes.htm';
			break;
		case 'antilockbrakespublic':
			strReturnURL = 'gloss_antilockbrakespublic.htm';
			break;
		case 'antitheft':
			strReturnURL = 'gloss_antitheft.htm';
			break;
		case 'antitheftpublic':
			strReturnURL = 'gloss_antitheftpublic.htm';
			break;
		case 'assignedid':
			strReturnURL = 'gloss_assignedid.htm';
			break;			
		case 'businessentity':
			strReturnURL = 'gloss_businessentity.htm';
			break;
		case 'businessname':
			strReturnURL = 'gloss_businessname.htm';
			break;;
		case 'businessname1':
			strReturnURL = 'gloss_businessname1.htm';
			break;;
		case 'businessname2':
			strReturnURL = 'gloss_businessname2.htm';
			break;;
		case 'businessoperation':
			strReturnURL = 'gloss_businessoperation.htm';
			break;
		case 'city':
			strReturnURL = 'gloss_city.htm';
			break;
		case 'cc':
			strReturnURL = 'gloss_cc.htm';
			break;
		case 'citycountycode':
			strReturnURL = 'gloss_citycountycode.htm';
			break;
		case 'classcommercialpassenger':
			strReturnURL = 'gloss_classcommercialpassenger.htm';
			break;
		case 'classpublic':
			strReturnURL = 'gloss_classpublic.htm';
			break;
		case 'classtrucktractortrailer':
			strReturnURL = 'gloss_classtrucktractortrailer.htm';
			break;
		case 'collision':
			strReturnURL = 'gloss_collision.htm';
			break;
		case 'collisionratemodification':
			strReturnURL = 'gloss_collisionratemodification.htm';
			break;
		case 'commercialpassengervehicletype':
			strReturnURL = 'gloss_commercialpassengervehicletype.htm';
			break;			
		case 'comprehensive':
			strReturnURL = 'gloss_comprehensive.htm';
			break;			
		case 'comprehensiveratemodification':
			strReturnURL = 'gloss_comprehensiveratemodification.htm';
			break;			
		case 'contactname':
			strReturnURL = 'gloss_contactname.htm';
			break;
		case 'copiedfrom':
			strReturnURL = 'gloss_copiedfrom.htm';
			break;
		case 'county':
			strReturnURL = 'gloss_county.htm';
			break;
		case 'coverage':
			strReturnURL = 'gloss_coverage.htm';
			break;
		case 'createdby':
			strReturnURL = 'gloss_createdby.htm';
			break;
		case 'createddate':
			strReturnURL = 'gloss_createddate.htm';
			break;
		case 'dateofbirth':
			strReturnURL = 'gloss_dateofbirth.htm';
			break;
		case 'driverage':
			strReturnURL = 'gloss_age.htm';
			break;
		case 'drivredateofbirth':
			strReturnURL = 'gloss_dateofbirth.htm';
			break;	
		case 'driverexcluded':
			strReturnURL = 'gloss_excludeddriver.htm';
			break;
		case 'driverexcludedspouse':
			strReturnURL = 'gloss_excludedspouse.htm';
			break;	
		case 'driverfirstname':
			strReturnURL = 'gloss_firstname.htm';
			break;
		case 'drivergender':
			strReturnURL = 'gloss_gender.htm';
			break;		
		case 'driverlastname':
			strReturnURL = 'gloss_lastname.htm';
			break;
		case 'driverlicensedate':
			strReturnURL = 'gloss_datelicensed.htm';
			break;
		case 'driverlicensenumber':
			strReturnURL = 'gloss_licensenumber.htm';
			break;	
		case 'driverlicensestate':
			strReturnURL = 'gloss_licensestate.htm';
			break;	
		case 'driverlicensestatus':
			strReturnURL = 'gloss_licensestatus.htm';
			break;
		case 'drivermaritalstatus':
			strReturnURL = 'gloss_maritalstatus.htm';
			break;
		case 'drivermvrstatus':
			strReturnURL = 'gloss_statusmvr.htm';
			break;
		case 'drivermiddlename':
			strReturnURL = 'gloss_middlename.htm';
			break;	
		case 'driversname':
			strReturnURL = 'gloss_namedriversearch.htm';
			break;	
		case 'driverorderclue':
			strReturnURL = 'gloss_orderclue.htm';
			break;
		case 'driverordermvr':
			strReturnURL = 'gloss_ordermvr.htm';
			break;
		case 'driveroverridemvr':
			strReturnURL = 'gloss_overrideoriginalmvr.htm';
			break;
		case 'driverprevlicensenumber':
			strReturnURL = 'gloss_licensenumber.htm';
			break;	
		case 'driverprevlicensestate':
			strReturnURL = 'gloss_licensestate.htm';
			break;
		case 'driverrelationship':
			strReturnURL = 'gloss_relationship.htm';
			break;
		case 'driverssn':
			strReturnURL = 'gloss_socialsecuritynumber.htm';
			break;	
		case 'driversuffixname':
			strReturnURL = 'gloss_suffixname.htm';
			break;	
		case 'docket'	:
			strReturnURL = 'gloss_docket.htm';
			break;			
		case 'dualrearaxle':
			strReturnURL = 'gloss_dualrearaxle.htm';
			break;	
		case 'earnedpremium':
			strReturnURL = 'gloss_earnedpremium.htm';
			break;
		case 'effdate':
			strReturnURL = 'gloss_effdate.htm';
			break;
		case 'effectivedate':
			strReturnURL = 'gloss_effectivedate.htm';
			break;
		case 'effectivetime':
			strReturnURL = 'gloss_timeeffective.htm';
			break;
		case 'equipmentamount':
			strReturnURL = 'gloss_equipmentamount.htm';
			break;	
		case 'equipmentdescription':
			strReturnURL = 'gloss_equipmentdescription.htm';
			break;
		case 'escalated':
			strReturnURL = 'gloss_referredtohomeoffice.htm';
			break;
		case 'form'	:
			strReturnURL = 'gloss_form.htm';
			break;
		case 'federalid':
			strReturnURL = 'gloss_federalid.htm';
			break;
		case 'federalidowners':
			strReturnURL = 'gloss_federalidowners.htm';
			break;
		case 'federalidnumber':
			strReturnURL = 'gloss_federalidnumber.htm';
			break;
		case 'filingname'	:
			strReturnURL = 'gloss_nameoffiling.htm';
			break;		
		case 'garaginglocation':
			strReturnURL = 'gloss_garaginglocation.htm';
			break;
		case 'gender':
			strReturnURL = 'gloss_gender.htm';
			break;
		case 'sizeandtype':
			strReturnURL = 'gloss_sizeandtype.htm';
			break;		
		case 'homeofficenotes':
			strReturnURL = 'gloss_homeofficenotes.htm';
			break;
		case 'homeofficetoagentdate':
			strReturnURL = 'gloss_returntoagentdate.htm';
			break;
		case 'homephonenumber':
			strReturnURL = 'gloss_home.htm';
			break;
		case 'instructions':
			strReturnURL = 'gloss_instructionsanddetails.htm';
			break;
		case 'insuredlongname':
			strReturnURL = 'gloss_insuredlongname.htm';
			break;;
		case 'lastupdatedid':
			strReturnURL = 'gloss_lastupdatedid.htm';
			break;
		case 'lineofbusiness':
			strReturnURL = 'gloss_lineofbusiness.htm';
			break;
		case 'liability':
			strReturnURL = 'gloss_liability.htm';
			break;
		case 'liabilityratemodification':
			strReturnURL = 'gloss_liabilityratemodification.htm';
			break;
		case 'licenseclass':
			strReturnURL = 'gloss_classmvr.htm';
			break;
		case 'licenseexpiredate':
			strReturnURL = 'gloss_expiredate.htm';
			break;
		case 'licenseissueddate':
			strReturnURL = 'gloss_issueddate.htm';
			break;
		case 'licensenumber':
			strReturnURL = 'gloss_licensenumber.htm';
			break;
		case 'licenserestrictions':
			strReturnURL = 'gloss_restrictions.htm';
			break;
		case 'licensestatus':
			strReturnURL = 'gloss_licensestatus.htm';
			break;
		case 'limitsdeductibles':
			strReturnURL = 'gloss_limitsdeductibles.htm';
			break;
		case 'lob':
			strReturnURL = 'gloss_lob.htm';
			break;
		case 'locationaddress':
			strReturnURL = 'gloss_locationaddress.htm';
			break;
		case 'mailingaddress':
			strReturnURL = 'gloss_mailingaddress.htm';
			break;
		case 'make':
			strReturnURL = 'gloss_make.htm';
			break;
		case 'makecommercialpassenger':
			strReturnURL = 'gloss_makecommercialpassenger.htm';
			break;
		case 'makemiscellaneous':
			strReturnURL = 'gloss_makemiscellaneous.htm';
			break;
		case 'makepublic':
			strReturnURL = 'gloss_makepublic.htm';
			break;
		case 'maketrucktractortrailer':
			strReturnURL = 'gloss_maketrucktractortrailer.htm';
			break;
		case 'marketinglead':
			strReturnURL = 'gloss_marketinglead.htm';
			break;				
		case 'mastercompanynumber':
			strReturnURL = 'gloss_mastercompanynumber.htm';
			break;
		case 'miscellaneous':
			strReturnURL = 'gloss_miscellaneous.htm';
			break;
		case 'miscellaneousvehicletype':
			strReturnURL = 'gloss_miscellaneousvehicletype.htm';
			break;
		case 'model':
			strReturnURL = 'gloss_model.htm';
			break;
		case 'modelcommercialpassenger':
			strReturnURL = 'gloss_modelcommercialpassenger.htm';
			break;
		case 'modelmiscellaneous':
			strReturnURL = 'gloss_modelmiscellaneous.htm';
			break;
		case 'modelpublic':
			strReturnURL = 'gloss_modelpublic.htm';
			break;
		case 'modeltrucktractortrailer':
			strReturnURL = 'gloss_modeltrucktractortrailer.htm';
			break;
		case 'mvrdob':
			strReturnURL = 'gloss_dateofbirthmvr.htm';
			break;
		case 'mvrdrivingrecordconvictiondate':
			strReturnURL = 'gloss_convreindate.htm';
			break;
		case 'mvrdrivingrecorddescription':
			strReturnURL = 'gloss_description.htm';
			break;
		case 'mvrdrivingrecordpts':
			strReturnURL = 'gloss_pts.htm';
			break;
		case 'mvrdrivingrecordtype':
			strReturnURL = 'gloss_typemvr.htm';
			break;
		case 'mvrdrivingrecordviolationdate':
			strReturnURL = 'gloss_violsusdate.htm';
			break;
		case 'mvrdrivingrecordviolconvcode':
			strReturnURL = 'gloss_violconvcode.htm';
			break;
		case 'mvrlicensenumber':
			strReturnURL = 'gloss_licensenumbermvr.htm';
			break;
		case 'mvrname':
			strReturnURL = 'gloss_namemvr.htm';
			break;
		case 'mvrorderdate':
			strReturnURL = 'gloss_mvrorderdate.htm';
			break;
		case 'mvrsocialsecuritynumber':
			strReturnURL = 'gloss_socialsecuritynumbermvr.htm';
			break;
		case 'mvrstate':
			strReturnURL = 'gloss_licensestate.htm';
			break;
		case 'mvrstatus':
			strReturnURL = 'gloss_statusmvr.htm';
			break;
		case 'number':
			strReturnURL = 'gloss_number.htm';
			break;	
		case 'ofpassengers':
			strReturnURL = 'gloss_ofpassengers.htm';
			break;
		case 'originalcostnew':
			strReturnURL = 'gloss_originalcostnew.htm';
			break;
		case 'originalcostnewtrucktractortrailer':
			strReturnURL = 'gloss_originalcostnewtrucktractortrailer.htm';
			break;
		case 'otherinterestaddress':
			strReturnURL = 'gloss_otherinterestaddress.htm';
			break;
		case 'otherinterestlongname':
			strReturnURL = 'gloss_otherinterestlongname.htm';
			break;
		case 'otherinterestname':
			strReturnURL = 'gloss_otherinterestname.htm';
			break;
		case 'otherinterestname1':
			strReturnURL = 'gloss_otherinterestname1.htm';
			break;
		case 'otherinterestname2':
			strReturnURL = 'gloss_otherinterestname2.htm';
			break;
		case 'otherinterestsortedby':
			strReturnURL = 'gloss_sortedbyotherinterestssearch.htm';
			break;
		case 'otherinteresttype':
			strReturnURL = 'gloss_typeotherinterests.htm';
			break;
		case 'owneraddress':
			strReturnURL = 'gloss_owneraddress.htm';
			break;
		case 'ownername':
			strReturnURL = 'gloss_ownername.htm';
			break;
		case 'ownertype':
			strReturnURL = 'gloss_ownertype.htm';
			break;
		case 'ownership':
			strReturnURL = 'gloss_ownership.htm';
			break;
		case 'passiverestraint':
			strReturnURL = 'gloss_passiverestraints.htm';
			break;
		case 'payment':
			strReturnURL = 'gloss_payment.htm';
			break;
		case 'payplan':
			strReturnURL = 'gloss_payplan.htm';
			break;
		case 'performance':
			strReturnURL = 'gloss_performance.htm';
			break;
		case 'platenumber':
			strReturnURL = 'gloss_platenumber.htm';
			break;	
		case 'policy':
			strReturnURL = 'gloss_policy.htm';
			break;
		case 'policycompanynumber':
			strReturnURL = 'gloss_policycompanynumber.htm';
			break;
		case 'policynumber':
			strReturnURL = 'gloss_policynumber.htm';
			break;
		case 'premium':
			strReturnURL = 'gloss_premium.htm';
			break;
		case 'premiumoverride':
			strReturnURL = 'gloss_premiumoverride.htm';
			break;
		case 'printeddate':
			strReturnURL = 'gloss_printeddate.htm';
			break;
		case 'publicvehicletype':
			strReturnURL = 'gloss_publicvehicletype.htm';
			break;	
		case 'purgedate':
			strReturnURL = 'gloss_purgedate.htm';
			break;
		case 'radiusofoperation':
			strReturnURL = 'gloss_radiusofoperation.htm';
			break;
		case 'rateddate':
			strReturnURL = 'gloss_rateddate.htm';
			break;
		case 'ratingstate':
			strReturnURL = 'gloss_ratingstate.htm';
			break;
		case 'ratingterritory':
			strReturnURL = 'gloss_ratingterritory.htm';
			break;
		case 'ratingzipcode':
			strReturnURL = 'gloss_ratingzipcode.htm';
			break;
		case 'runninglights':
			strReturnURL = 'gloss_runninglights.htm';
			break;
		case 'runninglightstrucktractortrailer':
			strReturnURL = 'gloss_runninglightstrucktractortrailer.htm';
			break;
		case 'rushindicator':
			strReturnURL = 'gloss_rush.htm';
			break;
		case 'sortedby':
			strReturnURL = 'gloss_sortedby.htm';
			break;
		case 'sortedbydriverssearch':
			strReturnURL = 'gloss_sortedbydrivers.htm';
			break; 	
		case 'sortedbyvehiclesearch':
			strReturnURL = 'gloss_sortedbyvehiclesearch.htm';
			break;
		case 'state':
			strReturnURL = 'gloss_state.htm';
			break;
		case 'status':
			strReturnURL = 'gloss_status.htm';
			break;
		case 'symbol':
			strReturnURL = 'gloss_symbol.htm';
			break;
		case 'symboltrucktractortrailer':
			strReturnURL = 'gloss_symboltrucktractortrailer.htm';
			break;
		case 'taxcode':
			strReturnURL = 'gloss_taxcode.htm';
			break;
		case 'territory':
			strReturnURL = 'gloss_territory.htm';
			break;
		case 'totalpremium':
			strReturnURL = 'gloss_totalpremium.htm';
			break;
		case 'totalvehiclecost':
			strReturnURL = 'gloss_totalvehiclecost.htm';
			break;
		case 'townshipname':
			strReturnURL = 'gloss_townshipname.htm';
			break;
		case 'truckerzones':
			strReturnURL = 'gloss_truckerzones.htm';
			break;
		case 'trucktractortrailervehicletype':
			strReturnURL = 'gloss_trucktractortrailervehicletype.htm';
			break;
		case 'typeaddress':
			strReturnURL = 'gloss_typeaddresses.htm';
			break;
		case 'typehome':
			strReturnURL = 'gloss_type.htm';
			break;
		case 'umuim':
			strReturnURL = 'gloss_umuim.htm';
			break;
		case 'underwritingcode':
			strReturnURL = 'gloss_underwritingcode.htm';
			break;
		case 'unitnumber':
			strReturnURL = 'gloss_unitnumber.htm';
			break;	
		case 'updatedby':
			strReturnURL = 'gloss_updatedby.htm';
			break;
		case 'updateddate':
			strReturnURL = 'gloss_updateddate.htm';
			break;
		case 'useclasscommercialpassenger':
			strReturnURL = 'gloss_useclasscommercialpassenger.htm';
			break;	
		case 'useclasspublic':
			strReturnURL = 'gloss_useclasspublic.htm';
			break;
		case 'useclasstrucktractortrailer':
			strReturnURL = 'gloss_useclasstrucktractortrailer.htm';
			break;	
		case 'usetype':
			strReturnURL = 'gloss_usetype.htm';
			break;
		case 'vehicletype':
			strReturnURL = 'gloss_vehicletype.htm';
			break;		
		case 'vin':
			strReturnURL = 'gloss_vin.htm';
			break;			
		case 'vincontrol':
			strReturnURL = 'gloss_vincontrol.htm';
			break;			
		case 'workphonenumber':
			strReturnURL = 'gloss_work.htm';
			break;
		case 'year':
			strReturnURL = 'gloss_year.htm';
			break;	
		case 'zipcode':
			strReturnURL = 'gloss_zipcode.htm';
			break;
		case 'rulename':
			strReturnURL = 'gloss_rulename.htm';
			break;
		case 'ruleid':
			strReturnURL = 'gloss_ruleid.htm';
			break;
		case 'rulereviewed':
			strReturnURL = 'gloss_rulereviewed.htm';
			break;
		case 'ruleeffectivedate':
			strReturnURL = 'gloss_ruleeffectivedate.htm';
			break;
		case 'ruleenddate':
			strReturnURL = 'gloss_ruleenddate.htm';
			break;
		case 'reviewuwrules':
			strReturnURL = 'gloss_reviewuwrules.htm';
			break;
		case 'reviewer':
			strReturnURL = 'gloss_reviewer.htm';
			break;
		case 'relationaloperator':
			strReturnURL = 'gloss_relationaloperator.htm';
			break;
		case 'screen':
			strReturnURL = 'gloss_screen.htm';
			break;
		case 'field':
			strReturnURL = 'gloss_field.htm';
			break;
		case 'value':
			strReturnURL = 'gloss_value.htm';
			break;
		case 'statusrulesmaintenancecenter':
			strReturnURL = 'gloss_statusrulesmaintenancecenter.htm';
			break;
		case '/underwritingruleinfo.jsp':
			strReturnURL = 'ruleinformc.htm';
			break;	
		case '/underwritingrmc.jsp':
			strReturnURL = 'rulesearchrmc.htm';
			break;	
		case '/rulesmaintenancecenterrmc1.jsp':
			strReturnURL = 'rulesmaintenancecenterrmc1.htm';
			break;	
		case 'appitem':
			strReturnURL = 'gloss_appitem.htm';
			break;	
		case 'applicablelob':
			strReturnURL = 'gloss_applicablelob.htm';
			break;	
		case 'applicablestate':
			strReturnURL = 'gloss_applicablestate.htm';
			break;	
		case 'completerulesmaintenancecenter':
			strReturnURL = 'gloss_completerulesmaintenancecenter.htm';
			break;	
		case 'condition':
			strReturnURL = 'gloss_condition.htm';
			break;	
		case 'appitemreviewed':
			strReturnURL = 'gloss_appitemreviewed.htm';
			break;	
		case 'comments':
			strReturnURL = 'gloss_comments.htm';
			break;
		case '/underwritingresults.jsp':
			strReturnURL = 'underwritingresultsnonfleetandfleetapplication.htm';
			break;
		case 'actionrulesearch':
			strReturnURL = 'gloss_actionrulesearch.htm';
			break;	
		case 'modelpopup':
			strReturnURL = 'gloss_locationaddress.htm';
			break;
		case 'tttgvw':
			strReturnURL = 'gloss_grossvehicleweightgvw.htm';
			break;		
		case 'tttgcw':
			strReturnURL = 'gloss_grosscombinedweightgcw.htm';
			break;	
	}
	return strReturnURL;
}
