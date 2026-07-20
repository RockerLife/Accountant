$(document).ready(function() {
	try {
		$("*.addCommas").keyup(function() {
			var nStr = $(this).val();
			nStr += '';
			var x = nStr.split('.');
			var x1 = x[0];
			var x2 = x.length > 1 ? '.' + x[1] : '';
			var rgx = /(\d{3})(\d{3})/;
			while (rgx.test(x1)) {
				x1 = x1.replace(rgx, '$1' + ',' + '$2');
			}
			$("#" + this.id).val(x1 + x2);
		});
		$("*.addCommas").trigger("keyup");
	} catch (e) {
		console.log("Error in addCommas function : " + e);
	}
	try{
		$("*.accordion").accordion({
			header : "h3",
			collapsible : true,
			active : false
		});
	}catch (e) {
		console.log("Error in accordion function : " + e);
	}
});
function IsNumeric(obj, event, allowDecimal, allowNegative, allowCharacter) {
	var key;
	var isCtrl = false;
	var keychar;
	var reg;
	var typeCharacterOlnyNumSymbolsMsg = " ";

	if (window.event) {
		key = event.keyCode;
		isCtrl = window.event.ctrlKey;
	} else if (event.which) {
		key = event.which;
		isCtrl = event.ctrlKey;
	}

	if (isNaN(key))
		return true;

	keychar = String.fromCharCode(key);

	// check for backspace or delete, or if Ctrl was pressed
	if (key === 8 || isCtrl) {
		return true;
	}
	if (allowCharacter !== "") {
		for (var allowCharacterCount = 0; allowCharacterCount < allowCharacter.length; allowCharacterCount++) {
			// alert(allowCharacter[allowCharacterCount]);
			if (keychar === allowCharacter[allowCharacterCount]) {
				return true;
			}
		}
	}

	var allowCharacterValid = "";
	if (allowCharacter !== "") {
		allowCharacterValid = "and (";
		for (var allowCharacterCount = 0; allowCharacterCount < allowCharacter.length; allowCharacterCount++) {

			if (allowCharacterCount < allowCharacter.length - 1) {
				allowCharacterValid += allowCharacter[allowCharacterCount]
						+ ", ";
			} else {
				allowCharacterValid += allowCharacter[allowCharacterCount];
			}
		}
		allowCharacterValid += ") ";
	}

	reg = /^\s*[0-9]+\s*$/;
	return reg.test(keychar);
}

function IsFloat(myfield, event, decimalLength)
{

	//alert("is Float "+decimalLength);
    var str = myfield.value;
//    alert(str);
    var decPos = str.split('.');
    var fldDecimalIndex=str.indexOf(".");

    var key;
    var keychar;

    if (window.event)
        key = window.event.keyCode;
    else if (event)
        key = event.which;
    else
        return true;

    keychar = String.fromCharCode(key);
//    alert(key);

    // control keys
    if ((key == null) || (key === 0) || (key === 8) || (key === 9) || (key === 13) || (key === 27) || (key === 45)) {
        return true;
    } else if (key === 32) {
        return false;
    } else if (fldDecimalIndex > -1){
        var selectedValue = $.trim(myfield.value.substring(myfield.selectionStart, myfield.selectionEnd));
        if(selectedValue === ""){
			var subfld=str.substr(fldDecimalIndex+1, str.length);
	        var subfldLength=subfld.length;
	        if(subfldLength == decimalLength){
	            //alert(subfldLength);
	        	return false;
	            //alert(subfldLength);
	        }
        }
    } else if (keychar === ".") { // numbers
        return true;
    }

    reg = /^\s*[0-9]+\s*$/;
    return reg.test(keychar);
}

function IsFloatUp(myfield, event, decimalLength)
{
    var str = myfield.value;
    var key = event.keyCode;

    var decPos = str.split('.');

    if (str.indexOf(".") > -1) {
        if (decPos.length > 1) {
            decPos = decPos[1];
            if (decPos.length > decimalLength) {
                myfield.value = "";
            }
        }
    }
	for (var allowNegative = 1; allowNegative < myfield.value.length; allowNegative++) {
		if ("-" === myfield.value[allowNegative]) {
			myfield.value = "";
		}
	}
}