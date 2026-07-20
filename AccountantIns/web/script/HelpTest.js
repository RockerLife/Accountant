//global variable for holding the help window
var objWinHelp = null;
var g_strHelpContext = null;



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
		objHelp.style.border = '1px solid #cccccc';
		objHelp.style.borderStyle = 'solid';
		objHelp.style.backgroundColor = '#FFFFFF';
		objHelp.style.width = '250px';
		objHelp.style.height = '100px';
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
		objHelp.src = strContext + 'Help/' + strFindFile(strField);
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
		//field level lookups
		case 'helpsample':
			strReturnURL = 'help_sample.html';
			break;
	}
	return strReturnURL;
}
