function setCookie(cname, cvalue, days) {
	var date = new Date();
	date.setTime(date.getTime()+(days*24*60*60*1000));
	var expires = "expires=" + date.toGMTString();
	document.cookie = cname + "=" + cvalue + ";" + expires +
";path=/";
}
function getCookie(cname) {
    var name = cname + "=";
    var decodedCookie = decodeURIComponent(document.cookie);
    var ca = decodedCookie.split(';');
    for(var i = 0; i <ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0) == ' ') {
        	c = c.substring(1);
        }
        if (c.indexOf(name) == 0) {
            return c.substring(name.length, c.length);
        }
	}
    return "";
}
function checkCookie() {
    var username = getCookie("username");
    if (username != "") {
    	alert("Welcome again " + username);
	} else {
    	username = prompt("Please enter your name:", "");
        if (username != "" && username != null) {
    		setCookie("username", username, 365);
    		}
	}
}


checkCookie();