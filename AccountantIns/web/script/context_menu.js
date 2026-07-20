var menuskin = "skin1"; // skin0, or skin1
var menuskin1 = "skin2"; // skin0, or skin1
var isOpen = false;
var display_url = 0; // Show URLs in status bar?
function showmenuie5() {
	hidemessage();
	var rightedge = document.body.clientWidth-event.clientX;
	var bottomedge = document.body.clientHeight-event.clientY;
	if (rightedge < ie5menu.offsetWidth)
		ie5menu.style.left = document.body.scrollLeft + event.clientX - ie5menu.offsetWidth;
	else
		ie5menu.style.left = document.body.scrollLeft + event.clientX;
	if (bottomedge < ie5menu.offsetHeight)
		ie5menu.style.top = document.body.scrollTop + event.clientY - ie5menu.offsetHeight;
	else
	ie5menu.style.top = document.body.scrollTop + event.clientY;
	ie5menu.style.visibility = "visible";
	ie5menu.style.display = "block";
	return false;
}
function jumptoie5() {
	if (event.srcElement.className == "menuitems") {
		if (event.srcElement.getAttribute("target") != null)
			window.open(event.srcElement.url, event.srcElement.getAttribute("target"));
		else
			window.location = event.srcElement.url;
  		 }
}
function hidemenuie5() {
ie5menu.style.visibility = "hidden";
ie5menu.style.display = "none";
displaymessage1();
//ie5menu.style.display = "block";
}
function highlightie5() {
if (event.srcElement.className == "menuitems") {
event.srcElement.style.backgroundColor = "highlight";
event.srcElement.style.color = "white";
if (display_url)
window.status = event.srcElement.url;
   }
}
function lowlightie5() {
if (event.srcElement.className == "menuitems") {
event.srcElement.style.backgroundColor = "";
event.srcElement.style.color = "black";
window.status = "";
   }
}
function showmenuie51() {
	
	hidemessage();
var rightedge1 = document.body.clientWidth-event.clientX;
var bottomedge1 = document.body.clientHeight-event.clientY;
if (rightedge1 < ie5menu1.offsetWidth)
ie5menu1.style.left = document.body.scrollLeft + event.clientX - ie5menu1.offsetWidth;
else
ie5menu1.style.left = document.body.scrollLeft + event.clientX;
if (bottomedge1 < ie5menu1.offsetHeight)
ie5menu1.style.top = document.body.scrollTop + event.clientY - ie5menu1.offsetHeight;
else
ie5menu1.style.top = document.body.scrollTop + event.clientY;
ie5menu1.style.visibility = "visible";
ie5menu1.style.display = "block";
return false;
}

function hidemenuie51() {
ie5menu1.style.visibility = "hidden";
ie5menu1.style.display = "none";

}

function highlightie51() {
if (event.srcElement.className == "menuitems") {
event.srcElement.style.backgroundColor = "highlight";
event.srcElement.style.color = "white";
if (display_url)
window.status = event.srcElement.url;
   }
}


function lowlightie51() {
if (event.srcElement.className == "menuitems") {
event.srcElement.style.backgroundColor = "";
event.srcElement.style.color = "black";
window.status = "";
   }
}
function jumptoie51() {
if (event.srcElement.className == "menuitems") {
if (event.srcElement.getAttribute("target") != null)
window.open(event.srcElement.url, event.srcElement.getAttribute("target"));
else
window.location = event.srcElement.url;
   }
}

function displaymessage()
	{
	 	ie5menu.className = menuskin;
		document.body.onclick = hidemessage;
		//document.getElementById("container").style.zIndex = -1;
		document.oncontextmenu = showmenuie5;
	}
function displaymessage1()
	{
		ie5menu1.className = menuskin1;
		document.body.onclick = hidemessage;
		//document.getElementById("container").style.zIndex = -1;
		document.oncontextmenu = showmenuie51;
	}
			
function hidemessage()
	{
		//alert("sunil");
		hidemenuie5();
		hidemenuie51();
		//document.getElementById("container").style.zIndex = 0;
	}

