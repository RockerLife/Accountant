function showPopUpRiskManagement(el, wdth, hght) {

    var w = (screen.width-wdth)/2 +'px';
    var t = (screen.height-hght)/2 +'px';
    var cvr = document.getElementById("cover");
    var dlg = document.getElementById(el);
    var ss = document.getElementById("StateCode");
    cvr.style.display = "block";
    dlg.style.display = "block";
    dlg.style.left =w;
    dlg.style.top =t;
    document.getElementById("cover").style.height =document.body.scrollHeight;
 
}


function closePopUp(el) {
	    document.documentElement.style.overflow=document.body.style.overflow='auto';
var cvr = document.getElementById("cover")
var dlg = document.getElementById(el)
cvr.style.display = "none"
dlg.style.display = "none"
document.body.style.overflowY = "scroll"
}
// JavaScript Document
function PopupCenter(pageURL, title,w,h) {
	var left = (screen.width/2)-(w/2);
	var top = (screen.height/2)-(h/2);
	var targetWin = window.open (pageURL, title, 'toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=yes, resizable=no, copyhistory=no, width='+w+', height='+h+', top='+top+', left='+left);
} 

function showDivArea(areas_show, areas_hide){
//alert("hello");
	for (var i = 0; i < areas_show.length; i++)
	{
		ge = document.getElementById(areas_show[i]);
		if(ge)
		ge.style.display = 'block';
	}
	for (var i = 0; i < areas_hide.length; i++)
	{
		gee = document.getElementById(areas_hide[i]);
		if(gee)
		gee.style.display = 'none';
	}
}
var openImg = new Image();
openImg.src = "img/minus.gif";
var closedImg = new Image();
closedImg.src = "img/plus.gif";

function showBranch5(branch){
	var objBranch = document.getElementById(branch);
	if(objBranch.style.display=="block")
		objBranch.style.display="none";	
	else	
		objBranch.style.display="block";
	
}

function swapFolder5(img1){
	objImg = document.getElementById(img1);
	if(objImg.src.indexOf('img/plus.gif')>-1)
		document.getElementById(img1).src = "img/minus.gif";
	else
		document.getElementById(img1).src = "img/plus.gif";
	
}
function setOpacity(e,opacity){
var o=e.style;
o.opacity=(opacity/100); //Opera
o.MozOpacity=(opacity/100); //Mozilla+Firefox
o.KhtmlOpacity=(opacity/100); //Konqueror
o.filter="alpha(opacity="+opacity+")"; //IE
}
function myfocus(){
	//window.document.body.disabled=false;
	//document.body.style.filter = true ? "alpha(opacity=100); -moz-opacity:100; opacity=100" : "" ; 
	setOpacity(document.body, 100);//Sets the opacity to 50%

}
function commissionWindow() {
var myArguments = new Object();
myArguments.param1 = "VIN";

setOpacity(document.body, 50);//Sets the opacity to 50%

window.showModalDialog("commission.html", myArguments, "dialogwidth: 850px; dialogheight: 400px; resizable: No"); 
}
