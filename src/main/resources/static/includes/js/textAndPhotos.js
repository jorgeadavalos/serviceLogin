function textOnly(pageName) {
	var obj = new ajaxObj("ajaxs/ajaxText.xhtml");
	obj.ajaxFunc = function(objResp) {
		openPage(pageName);
	}
	ajaxRequest(obj);
 }
function setComments(name,value) {
	var el = findElement(name);
	if (el != null) {
		var obj = new ajaxObj("ajaxs/ajaxComments.xhtml"+window.location.search);
		obj.ajaxDestEl = el;
		obj.ajaxFunc = function(objResp) {
		}
		ajaxRequest(obj);
	}
}
function addReplyToComment() {
	var replyText = findElement('replyText');
	var parentId = findElement('parentId');
	var tableName = document.getElementById('tableName');
	if (replyText != null && parentId != null && tableName != null) {
		var obj = new ajaxObj("ajaxs/addReplyToParent.xhtml?replyText="+encodeURIComponent(replyText.value)+"&parentId="+parentId.value+"&tableName="+tableName.value);
		obj.ajaxFunc = function(objResp) {
		}
		ajaxRequest(obj);
	}
}
function bldReply(commentTitle,commentId,parentTableName) {
	var modal = document.getElementById('myModal');
	var modalTitle = document.getElementById('modalTitle');
	modalTitle.innerText = commentTitle;
	
	var parentId = document.getElementById('parentId');
	var tableName = document.getElementById('tableName');
	if (parentId != null) parentId.value = commentId;
	if (tableName != null) tableName.value = parentTableName;
    modal.style.display = "block";
}
