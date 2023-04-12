<!DOCTYPE html>
<html> 
<head>
    <script type="text/javascript" src="/includes/js/restAjax.js"></script>
    <script type="text/javascript" src="/includes/js/projects.js"></script>
    <script type="text/javascript" src="/includes/js/globalFunctions.js"></script>
    <script type="text/javascript" src="/includes/js/globalFields.js"></script>
    <link href="/includes/css/modal.css" type="text/css" rel="stylesheet" ></link>
</head>  
  <body> 
	<form>
	<h4>Login </h4>
		<div>
		  	<h4 id="infoMessage">${msgobj.msg}</h4>
			<table id="login" border="1">
			  <tr><td>Email </td><td><input type="text" id="email" value="${obj.email}"/></td></tr> 
			  <tr><td>Password </td><td><input type="password" id="psw" value=""/></td></tr> 	  
			</table>
			<button type="button" onclick="ajaxRestLogin('infoMessage')" id="restButton"><b>submit</b></button>
		</div>
		<div id="myModal" class="modal">
			<div id="myContent" class="modal-content">
				<h4>Data retrieve uisng Spring RESTFUL </h4>
				<table id="serverTable" border="1">			  
				</table>
				<div style="padding-top: 10px;">
					<button type="button" onclick="exitTask('myModal')" id="modalButton"><b>Done</b></button>
				</div>
			</div>
		</div>
	</form>
  </body> 
</html>