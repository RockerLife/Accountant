function getSize() {
  myHeight = 300;
  if( typeof( window.innerWidth ) == 'number' ) {
    //Non-IE
    myHeight = window.innerHeight;
  } else if( document.documentElement && ( document.documentElement.clientWidth || document.documentElement.clientHeight ) ) {
    //IE 6+ in 'standards compliant mode'
    myHeight = document.documentElement.clientHeight;
  } else if( document.body && ( document.body.clientWidth || document.body.clientHeight ) ) {
    //IE 4 compatible
    myHeight = document.body.clientHeight;
	
  }
   document.getElementById("content").style.height = myHeight - 315 + "px"; 
   
}

document.onkeyup = KeyCheck;       
function KeyCheck(e)
{
   var KeyID = (window.event) ? event.keyCode : e.keyCode;
   if (KeyID == 122) {
		if (window.screenLeft < 0) {
			getSize();
		} else {
			getSize();
		}
	}
}


function winClose(){ 

	window.top.opener = null; window.close(); 
}
