function andDoneOnLoad() {
	try{
		
		var paymentIntentToken = $.trim($("#isvalidid").val());
		//alert("Token   " + paymentIntentToken);
		console.log("Token  " + paymentIntentToken);
		
		let pluginOption = {
			theme: "classic",  // classic, modern, minimal, vibrant
			container: "mywidget",  // id of the div to render the widget
		}
	
		// Initialize the plugin with token and options
	
		//let andDone = new AndDone(paymentIntentToken);
	
		let andDone = new AndDone(paymentIntentToken, pluginOption);
	
	
		// Using onload function to perform some action after the widget is loaded  and ready
		andDone.onload = (plugin) => {
	
			console.log("Widget loaded successfully");
			getAndDoneResult();
		}
	} catch(e){
		console.log('error', error);
	}
	
}

function getAndDoneResult(){
	var url = window.location.href;
	
	url = url.replace("paymentmethod.do","AndDoneResponse");
	//alert(url)
	var result = "";
	var responseTime = setInterval(function () {
		jQuery.ajax({
			url: url,
			type: 'POST',
			success: function(data) {
				//alert(data);
				result = data;
				if("DOAGAIN" == data){
					
				} else if("SUCCESS" == data) {
    				clearInterval(responseTime);
					clearInterval(sessionTimeOut);
    				andDonePaymentSuccess();
				} else if("ERROR" == data) {
    				clearInterval(responseTime);
					clearInterval(sessionTimeOut);
    				andDonePaymentFailure();
				}
			}
		});
    },5000);
    
    
	var sessionTimeOut = setInterval(function () {
		clearInterval(responseTime);
		clearInterval(sessionTimeOut);
		if("DOAGAIN" == result){
			showPopUpOverloaded('andDoneSessionTimeOut','500','400');
		}
    },185000);
    
}

function andDonePaymentSuccess(){
	//alert("d")
	showPopUpOverloaded('andDoneSuccessFull','500','400');
	disableBody();
	window.location.href = $('#andDonePaymentSuccess').attr('href');
}

function andDonePaymentFailure(){
	//alert("d")
	showPopUpOverloaded('andDonePaymentFailureOut','500','400');
}

function onExistButtonClick(href){
	disableBody();
	var url = window.location.href;
	
	url = url.replace("paymentmethod.do","AndDoneResponse");
	jQuery.ajax({
		url: url,
		type: 'POST',
		success: function(data) {
			//alert(data);
			result = data;
			if("SUCCESS" == data) {
				window.location.href = $('#andDonePaymentSuccess').attr('href');
			} else if("ERROR" == data) {
				window.location.href = $('#' + href).attr('href');
			} else if("DOAGAIN" == data){
				window.location.href = $('#' + href).attr('href');
			}
		}
	});
}

function showPopUpOverloaded(el, wdth, hght) {

	window.scrollTo(0,0);
	var wBody = parseInt(document.body.scrollWidth, 10);
	var wPopup = parseInt(document.getElementById(el).style.width, 10);
//	document.documentElement.style.overflow = document.body.style.overflow = "auto";
	var w = ((wBody - wPopup) / 2);
	var h = 100;
	var cvr = document.getElementById("cover");
	var dlg = document.getElementById(el);
	//var height = document.body.clientHeight;
	var body = document.body,
	html = document.documentElement;
	
	var height = Math.max( body.scrollHeight, body.offsetHeight, html.clientHeight, html.scrollHeight, html.offsetHeight );
	if (dlg != null && w!=null) {
		dlg.style.display = "block";
		dlg.style.left = w + "px";
		dlg.style.top = h + "px";
		//window.scrollTo(0,0);
		//alert(w+" "+h);
	}
	if (cvr != null) {
		cvr.style.height = height + "px";
		cvr.style.display = "block";
		//alert(height);
		cvr.style.position = "fixed";
	}
}

function closePopUp(el) {
	try{
		var ss = document.getElementById("StateCode");
		ss.style.display = "block";
		var ss1 = document.getElementById("DeductibleSequence");
		ss1.style.display = "block";
	}
	catch(e)
	{}
	var cvr = document.getElementById("cover");
	var dlg = document.getElementById(el);
	cvr.style.display = "none";
	dlg.style.display = "none";
	
}