function clearText(theText) {
     if (theText.value == theText.defaultValue) {
         theText.value = ""
     }
 }

function focused (objElement)
 {
	objElement.className='focused';
 }

function blured (objElement)
 {
	objElement.className='blured';
 }

function focusedradio (objElement)
 {
	objElement.className='focusedradio';
 }

function bluredradio (objElement)
 {
	objElement.className='bluredradio';
 }

function toggleview(element1) {

element1 = document.getElementById(element1);

if (element1.style.display == 'block' || element1.style.display == '')
element1.style.display = 'none';
else
element1.style.display = 'block';

return;
}

var lastDiv = "";
var l ="";
function showDiv(divName, n) {
	// hide last div
	if (lastDiv) {
		document.getElementById(lastDiv).className = "hiddenDiv";
		document.getElementById(l).className = "hiddenDiv";
	}
	//if value of the box is not nothing and an object with that name exists, then change the class
	if (divName && document.getElementById(divName)) {
		document.getElementById(divName).className = "visibleDiv";
		document.getElementById(n).className = "visibleDiv";
		lastDiv = divName;
		l = n;
	}
}

var lastDiv2 = "";
function showDiv2(divName2) {
	// hide last div
	if (lastDiv2) {
		document.getElementById(lastDiv2).className = "hiddenDiv";
	}
	//if value of the box is not nothing and an object with that name exists, then change the class
	if (divName2 && document.getElementById(divName2)) {
		document.getElementById(divName2).className = "visibleDiv";
		lastDiv2 = divName2;
		
	}
}

var lastDiv3 = "";
function showDiv3(divName3) {
	// hide last div
	if (lastDiv3) {
		document.getElementById(lastDiv3).className = "hiddenDiv";
	}
	//if value of the box is not nothing and an object with that name exists, then change the class
	if (divName3 && document.getElementById(divName3)) {
		document.getElementById(divName3).className = "visibleDiv";
		lastDiv3 = divName3;
	}
}

var lastDiv4 = "";
function showDiv4(divName4) {
	// hide last div
	if (lastDiv4) {
		document.getElementById(lastDiv4).className = "hiddenDiv";
	}
	//if value of the box is not nothing and an object with that name exists, then change the class
	if (divName4 && document.getElementById(divName4)) {
		document.getElementById(divName4).className = "visibleDiv";
		lastDiv4 = divName4;
	}
}

var lastDiv5 = "";
function showDiv5(divName5) {
	// hide last div
	if (lastDiv5) {
		document.getElementById(lastDiv5).className = "hiddenDiv";
	}
	//if value of the box is not nothing and an object with that name exists, then change the class
	if (divName5 && document.getElementById(divName5)) {
		document.getElementById(divName5).className = "visibleDiv";
		lastDiv5 = divName5;
	}
}

var lastDivA = "";
function hideDiv(divNameA) {
	// hide last div
	if (lastDivA) {
		document.getElementById(lastDivA).className = "visibleDiv";
	}
	//if value of the box is not nothing and an object with that name exists, then change the class
	if (divNameA && document.getElementById(divNameA)) {
		document.getElementById(divName5).className = "hiddenDiv";
		lastDivA = divNameA;
	}
}


function hideDivArea(str_area_hide){
	tag = document.getElementsByTagName("div");
	for(x=0;x<tag.length; x++)
	{
		if(tag[x].getAttribute('id').indexOf(str_area_hide) != -1)
	{
		tag[x].style.display = "none";
	}
	}
}

function showDivArea(areas_show, areas_hide){

	//alert("areas_show"+areas_show);
	///alert("areas_hide"+areas_hide);
	for (var i = 0; i < areas_show.length; i++)
	{	
		
		ge = document.getElementById(areas_show[i]);		
		if(ge)
			ge.style.display = "block";
		
	}
	for (var i = 0; i < areas_hide.length; i++)
	{
		
		var gee = document.getElementById(areas_hide[i]);		
		if(gee)
			gee.style.display = "none";
		
	}
}
function getRadioValue(radioObj)
{
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
 return "N";
}


function openDivArea1(){
	if(getRadioValue(document.getElementById('entityname'))=='Y'){
		showDivArea(['divEy'], ['divEn']);
	}
	if(getRadioValue(document.getElementById('entity'))=='Y'){
		showDivArea(['divEy'], ['divEn']);
	}
	if((getRadioValue(document.getElementById('entityname'))=='Y') && (getRadioValue(document.getElementById('entity'))=='Y')){
		showDivArea(['divEy'], ['divEn']);
	}
	 if((getRadioValue(document.getElementById('entityname'))=='Y') && (getRadioValue(document.getElementById('entity'))=='N')){
		showDivArea(['divEn'], ['divEy']);
	}
	if((getRadioValue(document.getElementById('entityname'))=='N') && (getRadioValue(document.getElementById('entity'))=='N')){
		showDivArea(['divEn'], ['divEy']);
	}
	if((getRadioValue(document.getElementById('entityname'))=='N') && (getRadioValue(document.getElementById('entity'))=='Y')){
		showDivArea(['divEn'], ['divEy']);
	}
	
}

function openDivArea2(){
	if(getRadioValue(document.getElementById('firm'))=='Y'){
		showDivArea(['divAy'], ['divAn']);
	}
	if(getRadioValue(document.getElementById('personal'))=='Y'){
		showDivArea(['divAy'], ['divAn']);
	}
	if((getRadioValue(document.getElementById('firm'))=='Y') && (getRadioValue(document.getElementById('personal'))=='Y')){
		showDivArea(['divAy'], ['divAn']);
	}
	 if((getRadioValue(document.getElementById('firm'))=='Y') && (getRadioValue(document.getElementById('personal'))=='N')){
		showDivArea(['divAy'], ['divAn']);
	}
	if((getRadioValue(document.getElementById('firm'))=='N') && (getRadioValue(document.getElementById('personal'))=='N')){
		showDivArea(['divAn'], ['divAy']);
	}
	if((getRadioValue(document.getElementById('firm'))=='N') && (getRadioValue(document.getElementById('personal'))=='Y')){
		showDivArea(['divAy'], ['divAn']);
	}
	
}

/* -----------------------------------------------
   Floating layer - v.1
   (c) 2006 www.haan.net
   contact: jeroen@haan.net
   You may use this script but please leave the credits on top intact.
   Please inform us of any improvements made.
   When usefull we will add your credits.
  ------------------------------------------------ */

x = 20;
y = 70;
function setVisible(obj)
{
	obj = document.getElementById(obj);
	obj.style.visibility = (obj.style.visibility == 'visible') ? 'hidden' : 'visible';
}
function placeIt(obj)
{
	obj = document.getElementById(obj);
	if (document.documentElement)
	{
		theLeft = document.documentElement.scrollLeft;
		theTop = document.documentElement.scrollTop;
	}
	else if (document.body)
	{
		theLeft = document.body.scrollLeft;
		theTop = document.body.scrollTop;
	}
	theLeft += x;
	theTop += y;
	obj.style.left = theLeft + 'px' ;
	obj.style.top = theTop + 'px' ;
	setTimeout("placeIt('layer1')",500);
}
//window.onscroll = setTimeout("placeIt('layer1')",500);



function showPopUpConfirmation(el,wdth, hght) {
    //alert("test");
 /*  var w = (screen.width-434)/2;
    var t = (screen.height-450)/2;
    var cvr = document.getElementById("cover");
    var dlg = document.getElementById(el);
    //var ss = document.getElementById("StateCode");
    cvr.style.display = "block";
    dlg.style.display = "block";
    dlg.style.left =w;
    dlg.style.top =t;
    document.getElementById("cover").style.height =document.body.scrollHeight;
 
 */
 
 
  var w = (screen.width-wdth)/2 +'px';
    var t = (screen.height-hght)/2 +'px';
    var cvr = document.getElementById("cover");
    var dlg = document.getElementById(el);
    var ss = document.getElementById("StateCode");
    cvr.style.display = "block";
    dlg.style.display = "block";
    dlg.style.left =w;
    dlg.style.top =t;
    //document.getElementById("cover").style.height =document.body.scrollHeight;
    document.getElementById("cover").style.height = "1000px";
}



function showPopUpList(outerDiv,innerDiv) {
    //alert("test");
   
    
    if(document.getElementById(innerDiv) != null)
    {
     	//alert("test in if");
     	 document.documentElement.style.overflow=document.body.style.overflow='hidden';
	    var w = ((screen.width-964)/2)+"px";
	    var t = ((screen.height-550)/2)+"px";
	    var cvr = document.getElementById("cover");
	    var dlg = document.getElementById(outerDiv);
	    cvr.style.display = "block";
	    dlg.style.display = "block";
	    dlg.style.left =w;
	    dlg.style.top =t;
	    document.getElementById("cover").style.height =document.body.scrollHeight;
	   
    }
 
}

function showPopUpOnIssue(outerDiv,innerDiv) {
    alert("test");
   
    
    //var w = (screen.width-wdth)/2 +'px';
    //var t = (screen.height-hght)/2 +'px';
    var cvr = document.getElementById("cover");
    var dlg = document.getElementById(el);
    
    cvr.style.display = "block";
    dlg.style.display = "block";
    dlg.style.left =w;
    dlg.style.top =t;
    document.getElementById("cover").style.height =document.body.scrollHeight;
 
}



function openDivArea2(){
	if(getRadioValue(document.getElementById('firm'))=='Y'){
		showDivArea(['divAy5'], ['divAn5']);
	}
	if(getRadioValue(document.getElementById('IsAwarenessOfAnyProfLiabilitySuitAgainst'))=='Y'){
		showDivArea(['divAy5'], ['divAn5']);
	}
	if((getRadioValue(document.getElementById('firm'))=='Y') && (getRadioValue(document.getElementById('IsAwarenessOfAnyProfLiabilitySuitAgainst'))=='Y')){
		showDivArea(['divAy5'], ['divAn5']);
	}
	 if((getRadioValue(document.getElementById('firm'))=='Y') && (getRadioValue(document.getElementById('IsAwarenessOfAnyProfLiabilitySuitAgainst'))=='N')){
		showDivArea(['divAy5'], ['divAn5']);
	}
	if((getRadioValue(document.getElementById('firm'))=='N') && (getRadioValue(document.getElementById('IsAwarenessOfAnyProfLiabilitySuitAgainst'))=='N')){
		showDivArea(['divAn5'], ['divAy5']);
	}
	if((getRadioValue(document.getElementById('firm'))=='N') && (getRadioValue(document.getElementById('IsAwarenessOfAnyProfLiabilitySuitAgainst'))=='Y')){
		showDivArea(['divAy5'], ['divAn5']);
	}
	
}

function setFocus(objElement){

     var txtBox = document.getElementById(objElement)
     txtBox.focus();
}
function closePopUp(el) {

document.documentElement.style.overflow=document.body.style.overflow='auto';
var cvr = document.getElementById("cover")
var dlg = document.getElementById(el)
cvr.style.display = "none"
dlg.style.display = "none"
document.body.style.overflowY = "scroll"

}

function closePopupAfterIssue(){
alert('data is ' + document.getElementById('IsDocumentProcessed').value);
	if(document.getElementById('IsDocumentProcessed') != null){
		var IsDocumentProcessed = document.getElementById('IsDocumentProcessed').value;
		if(IsDocumentProcessed == 'Y')
		{
			closePopUp('popup');
		}
	}
	
}



