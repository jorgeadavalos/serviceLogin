var viewIndex = 0;
var viewMax   = -1;
var photosB64 	= [];
function previousPhoto() {
	if (viewIndex == 0) viewIndex = viewMax;
	else viewIndex--;
	loadImg('myContent');
	return false;
}
function nextPhoto() {
	if (viewIndex >= viewMax) viewIndex = 0;
	else viewIndex++;
	loadImg('myContent');
	return false;
}
function loadImg(parm) {
	var displayImg = document.getElementById('fullPhoto');
	displayImg.src = photosB64[viewIndex];
	displayImg.onload = function() {
		setsizeImgs(parm);
		centerArrows(parm);
	};
}
function setsizeImgs(parm) {
	var modal = document.getElementById(parm);
	var displayImg = document.getElementById('fullPhoto');
	var nh = displayImg.naturalHeight;
	var nw = displayImg.naturalWidth;
	displayImg.width = nw;
	displayImg.height = nh;
	var maxDisplay = 400;
	var paddingTop = modal.clientHeight/2 - nh/2;
	if (paddingTop < 0) paddingTop= 0;
	displayImg.style.paddingTop = paddingTop+"px";
//	if (nw >= nh) {
//		displayImg.height = Math.round(maxDisplay*(nh/nw));
//		displayImg.width = maxDisplay;
//	} else  {
//		displayImg.width = Math.round(maxDisplay*(nw/nh));
//		displayImg.height = maxDisplay;
//	}
	
	if (modal.offsetTop+nh > window.innerHeight) maxDisplay = window.innerHeight-modal.offsetTop-50;
	if (nh < modal.clientHeight && nw < modal.clientWidth) return;
	
	displayImg.width = Math.round(maxDisplay*(nw/nh));
	displayImg.height = maxDisplay;
	modal.style.width = displayImg.width+"px";
	modal.style.height = displayImg.height+"px";
	modal.clientWidth = displayImg.width;
	modal.clientHeight = displayImg.height;
}
function centerArrows(parm) {
	var modal = document.getElementById(parm);
	
	var percent = (modal.clientWidth*30/100);
	var previousVTag = document.getElementById("previousVTag");
	var nextVTag = document.getElementById("nextVTag");
	previousVTag.style.position = "absolute";
	previousVTag.style.top = modal.offsetTop+"px";
	previousVTag.style.bottom = modal.offsetTop+modal.clientHeight+"px";
	previousVTag.style.height = modal.style.height;
	previousVTag.style.width = percent+"px";
	
	nextVTag.style.position = "absolute";
	nextVTag.style.top = modal.offsetTop+"px";
	nextVTag.style.bottom = modal.offsetTop+modal.clientHeight+"px";
	nextVTag.style.height = modal.style.height;
	nextVTag.style.width = percent+"px";
	nextVTag.style.left = (modal.offsetLeft +modal.clientWidth-percent)+"px";

	var previous = document.getElementById("previous");
	var next = document.getElementById("next");
	
	previous.style.top = modal.clientHeight/2+"px";
	previous.style.left = 0+"px";
	//previous.style.display = "block";
	
	next.style.top = modal.clientHeight/2+"px";
	next.style.left = (percent-20)+"px";
	//next.style.display = "block";
	
	return false;
}
function bldMyModal(parm) {
	viewIndex = 0;
	viewMax   = -1;
	loadImg('myContent');
	viewIndex++;
	var modal = document.getElementById(parm);
    modal.style.display = "block";
	document.getElementById('modalButton').focus();
	return false;
}
function toggleNav(parm,state) {
	for (var i =0;i<parm.children.length;i++) {
		
		if (state == 'in') {
			parm.children[i].style.display = "block";
			parm.style.display = "block";
			parm.style.cursor = "pointer";
		} else {
			//parm.children[i].style.display = "none";
			parm.style.cursor = "auto";
		}
	}
	return false;
}
function galleryAjaxReq(name,url) {
	var obj = new ajaxObj(url);
	obj.ajaxFunc = function(objResp) {
		if (objResp.ajaxDestEl.innerHTML.trim().length > 0) {
			photosB64[photosB64.length] = objResp.ajaxDestEl.innerHTML.trim();
			if (viewIndex == 0) bldMyModal("myModal2");
			viewMax = photosB64.length-1;			
			ajaxRequest(obj);
			return false;
		}
	};
	ajaxRequest(obj);
}
function bldGalleryTbl(parm) {
	photosB64.length = 0;
	viewIndex = 0;
	galleryAjaxReq(parm,"ajaxs/accessImage.xhtml");
	return false;
}
function displayDiscussionImages(idvalue,courseid) {
	var obj = new ajaxObj("ajaxs/DBAccessAllImages.xhtml?colvalue="+idvalue);
	obj.ajaxFunc = function(objResp) {
		bldGalleryTbl("galleryTbl");
	}
	ajaxRequest(obj);
	return false;
}
function uploadDiscussionImages(discussionid,courseid) {
	var obj = new ajaxObj("ajaxs/resetInfomsg.xhtml");
	obj.ajaxFunc = function(objResp) {
		var url = "loadForumFiles.xhtml?discussionid="+discussionid+"&courseid="+courseid;
		genPopUp = window.open(url,"imgLoader","toolbar=no,location=no,scrollbars=yes,directories=no,status=no,menubar=no,resizable=yes,width=500,height=350");
		genPopUp.focus();
	}
	ajaxRequest(obj);
}
