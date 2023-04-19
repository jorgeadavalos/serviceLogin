function initFocus()
{
    var f = document.getElementById("j_username");
    if (f != undefined)
    {
        f.focus();
    }
}
function centerLoginBox(target) {
	var target = document.getElementById('Layer1');
    if (target == undefined)
        return;
    var w = 0;
    var h = 0;
    if (self.innerHeight) // all except Explorer
    {
        w = self.innerWidth;
        h = self.innerHeight;
    }
    else if (document.documentElement && document.documentElement.clientHeight)
        // Explorer 6 Strict Mode
    {
        w = document.documentElement.clientWidth;
        h = document.documentElement.clientHeight;
    }
    else if (document.body) // other Explorers
    {
        w = document.body.clientWidth;
        h = document.body.clientHeight;
    }
    target.style.position = 'relative';
    target.style.width = w + "px";
    target.style.height = h + "px";
}
function chgPswButton(parm) {
	var flds = getElementsByClass(parm);
	for (var i = 0;i<flds.length;i++) {
		flds[i].style.display = "block";
	}
	flds = getElementsByClass('loginbutton');
	for (var i = 0;i<flds.length;i++) {
		flds[i].style.display = "none";
	}
	return false;
}
function resetPswButton() {
	const field1 = document.getElementById("confpsw");
	const field2 = document.getElementById("newpsw");
	if (field1 == null || field2 == null ) return false;
	const value1 = field1.value;
	const value2 = field2.value;
	if (value1 !== value2 || value1.trim().length == 0) {
		alert("password must be the same and other than spaces ")
		return false;
	}
	const wrkstr = getParameterByName("jsonitem");
	let callerJson = JSON.parse(wrkstr);
	var jsonBody = {};
	jsonBody["loginid"] = callerJson['loginId'];
	jsonBody["service"] = callerJson['service'];
	jsonBody["id"] = callerJson['id'];
	jsonBody["password"] = value1;
	var obj = new ajaxObj(_RESTBASE+"/reset/password");
	obj.ajaxReqHeaders["caller"] = localStorage.getItem("caller");
	obj.body = JSON.stringify(jsonBody);
	obj.ajaxFunc = function(objResp) {
		return serverResp(objResp.ajaxmsg);
	};
	ajaxRequest(obj,"POST");
	return false;
}
function getElementsByClass(searchClass) { 
	var el = new Array();
	var tags = document.getElementsByTagName('*');
	var tcl = " "+searchClass+" ";
	for(i=0,j=0; i<tags.length; i++) { 
		var test = " " + tags[i].className + " ";
		if (test.indexOf(tcl) != -1) 
			el[j++] = tags[i];
	} 
	return el;
}
function checkInputs(wrkId,wrkpsw,wrkvpsw) {
	if (wrkId == null || wrkpsw == null) alert("not a login panel");
	var loginId = wrkId.value.trim();
	if (loginId == null || loginId.length == 0 ) {
		wrkId.focus();
		alert("must enter a value");
		return false;
	}
	var psw = wrkpsw.value.trim();
	if (psw == null || psw.length == 0 ) {
		wrkpsw.focus();
		alert("must enter a value");
		return false;
	}
	if (typeof wrkvpsw !== 'undefined' ) {
		if (wrkvpsw != null) {
			vpsw = wrkvpsw.value.trim();
			if (vpsw == null || vpsw.length == 0 || vpsw != psw) {
				wrkvpsw.focus();
				if (_CALLER === 'ajaxRestChgPsw')
					alert("'new password' and 'confirm password' must be the same");
				else alert("password and verify password must be the same\n");
				return false;
			}
		}
	}
	let caller = JSON.parse(localStorage.getItem("caller"));
	let contract = caller["contract"];
	if (contract != null && contract["loginid"] != null) {
		var instructions = contract["loginid"];
		var F= new Function ('arg1'  ,instructions);
		var jsonContract = (F(loginId));
		if (!jsonContract['result']) {
			const msg = "<b>The Service "+caller['contextPath']+"<br/>&nbsp;requirement: "+jsonContract['hint']+"</b>";
			setMsg("loginError",msg);
		}
		return jsonContract['result'];
	}
	return true;
}
function saveJsonCaller() {	
	const caller = getParameterByName('caller');
	if (caller == null) {
		alert(" this service requires a calling service" )
		return false;
	}
	localStorage.setItem("caller",caller);
	return true;
}
function populateCaller() {
	let callee = JSON.parse(localStorage.getItem("callee"));
	let caller = JSON.parse(localStorage.getItem("caller"));
	if (callee == null || caller == null) return;
	
		if (_SERVICE !== caller["serviceDest"]) {
			alert(_SERVICE +" "+ caller["serviceDEST"]+" don't match");
			return;
		}

	let users = callee["users"];
	if (typeof users !== 'undefined') {
		let user = users[0]; 
		caller['user'] = user;
	}
	localStorage.setItem("caller",JSON.stringify(caller));
}
function serverResp(msg) {
	localStorage.setItem(_SERVICE,msg);
	var wrkJson = JSON.parse(msg);
	if (!wrkJson["srvcompleted"]) {
		setMsg("loginError",wrkJson["infomsg"]);
		return false;
	}
	populateCaller();
	let wrkstr = JSON.stringify(wrkJson["users"][0]);
	wrkstr = encodeURIComponent(wrkstr);
	let url = wrkJson["html"]+"?"+_SERVICE+"="+wrkstr;
	openPage(url);
	return false;
}
function ajaxRestLogin() {
	const wrkId = document.getElementById("loginid");
	const wrkpsw = document.getElementById("psw");
	var psw = wrkpsw.value.trim();
	var loginId = wrkId.value.trim();
	if (!checkInputs(wrkId,wrkpsw)) return false;
	
	var jsonBody = {};
	jsonBody["loginid"] = loginId;
	jsonBody["password"] = psw;
	var obj = new ajaxObj(_RESTBASE+"/startloginAjax");
	obj.ajaxReqHeaders["caller"] = localStorage.getItem("caller");
	obj.body = JSON.stringify(jsonBody);
	obj.ajaxFunc = function(objResp) {
		return serverResp(objResp.ajaxmsg);
	};
	ajaxRequest(obj,"POST");
	return false;
}
function ajaxRestRegistration() {
	const wrkId = document.getElementById("loginid");
	const wrkpsw = document.getElementById("password");
	const wrkvpsw = document.getElementById("vpassword");
	if (!checkInputs(wrkId,wrkpsw,wrkvpsw)) return false;
	
	var obj = new ajaxObj(_RESTBASE+"/registrations");
	obj.body = JSON.stringify(bldJsonFromHTMLElements());
	obj.ajaxReqHeaders["caller"] = localStorage.getItem("caller");
	obj.ajaxFunc = function(objResp) {
		return serverResp(objResp.ajaxmsg);
	};
	ajaxRequest(obj,"POST");
	return false;
}
function ajaxRestChgPsw() {
	const wrkId = document.getElementById("loginid");
	const psw = document.getElementById("psw");
	const newpsw = document.getElementById("newpsw");
	const confpsw = document.getElementById("confpsw");
	_CALLER = 'ajaxRestChgPsw';
	if (!checkInputs(wrkId,newpsw,confpsw)) return false;
	
	var obj = new ajaxObj(_RESTBASE+"/change/password");
	jsonBody = bldJsonFromHTMLElements();
	jsonBody["password"] = psw.value;
	obj.body = JSON.stringify(jsonBody);
	obj.ajaxReqHeaders["caller"] = localStorage.getItem("caller");
	obj.ajaxFunc = function(objResp) {
		return serverResp(objResp.ajaxmsg);
	};
	ajaxRequest(obj,"POST");
	return false;
}
function ajaxRestForgotPsw(parm) {
	
	const wrkId = document.getElementById(parm);
	if (wrkId == null) return false;
	
	exitTask("myModal");
	var obj = new ajaxObj(_RESTBASE+"/forgot/password");
	jsonBody = {};
	jsonBody["loginid"] = wrkId.value;
	obj.body = JSON.stringify(jsonBody);
	obj.ajaxReqHeaders["caller"] = localStorage.getItem("caller");
	obj.ajaxFunc = function(objResp) {
		return serverResp(objResp.ajaxmsg);
	};
	ajaxRequest(obj,"POST");
	//return false;
}
function ajaxRestForgotId(parm) {
	
	const email = document.getElementById(parm);
	if (email == null) return false;
	
	exitTask("myModal");
	const wrkstr = localStorage.getItem("caller");
	let caller = JSON.parse(wrkstr);
	var obj = new ajaxObj(_RESTBASE+"/forgot/userid");
	jsonBody = {};
	jsonBody["email"] = email.value;
	jsonBody["service"] = caller["serviceOrig"];
	obj.body = JSON.stringify(jsonBody);
	obj.ajaxReqHeaders["caller"] = wrkstr;
	obj.ajaxFunc = function(objResp) {
		let wrkJson = JSON.parse(objResp.ajaxmsg);
		let loginid = document.getElementById("loginid");
		loginid.value = wrkJson["loginid"];
		setMsg("loginError",wrkJson["infomsg"]);

	};
	ajaxRequest(obj,"POST");
	//return false;
}
function loginForgotButton(parm) {
	
	let loginid = document.getElementById("loginid");
	let forgotPsw = document.getElementById("forgotPsw");
	forgotPsw.value = loginid.value;
	showMyModal(parm);
	return false;
}
function loginRegistrationButton() {
	const html = "register.jsp";
	if (!saveJsonCaller()) return;
	window.open(html,"_parent",ref="noreferrer");
	return false;
}
function onloadLogin() {
	centerLoginBox();
	snippets();
}
function onloadResetPSW() {
	centerLoginBox();
	const banner = document.getElementById("banner");
	const loginError = document.getElementById("loginError");
	const wrkstr = getParameterByName("jsonitem");
	let callerJson = JSON.parse(wrkstr);
	loginError.innerHTML += "<b>"+callerJson['service']+"</b>"
	banner.innerHTML += "<b>"+callerJson['loginid']+"</b>"
	var x=0;
}
function onloadRegister() {
	onloadLogin();
}
function confirmEmail() {
	const value = getParameterByName("jsonitem");
//	var page = _RESTBASE+"/confirmEmail/"+encodeURIComponent(value);
//	var page = _RESTBASE+"/confirmEmail?jsonitem="+encodeURIComponent(value);
	var page = _RESTBASE+"/confirmEmail";
	let jsonBody = {};
	jsonBody["jsonitem"] = value;
	var obj = new ajaxObj(page);
	obj.body = value;
	obj.ajaxReqHeaders["caller"] = localStorage.getItem("caller");
	obj.ajaxFunc = function(objResp) {
		return serverResp(objResp.ajaxmsg);
	}
	ajaxRequest(obj,"POST");
}
