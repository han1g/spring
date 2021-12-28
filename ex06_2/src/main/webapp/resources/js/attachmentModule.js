

var attachmentService = (function() {
	/**
	 * jsp페이지에서 value 받기
	 * @param
	 * @returns
	 */
	function loadJspValue(values) {
		csrfHeaderName = values.csrfHeaderName;
		csrfTokenValue = values.csrfTokenValue;
	}
	/**
	 * 썸네일 클릭했을 때 커지게함
	 * @param fileCallPath
	 * @returns
	 */
	function showImage(fileCallPath) {
		console.log(fileCallPath);
		$(".bigPictureWrapper").css("display","flex").show();
		
		$(".bigPicture").html(`<img src="/thumbnail?fileName=${encodeURIComponent(fileCallPath)}">`)
		.animate({width:'100%', height:'100%'},1000);
	}
	/**
	 * 화면 클릭하면 사진 닫기
	 * @param bigPictureWrapper(jqueryObject)
	 * @param bigPicture(jqueryObject)
	 * @returns
	 */
	function setCloseImageListener(bigPictureWrapper,bigPicture) {
		bigPictureWrapper.on("click", function(e) {
			bigPicture.animate({width:'0%', height:'0%'},1000);
			setTimeout(()=>{$(this).hide()},1000);
		});
	}
	//get only
	
	/**
	 * 파일 올릴 때 파일 검증
	 */
	function checkExtension(fileName,fileSize) {
		var regex = /\.(exe|sh|zip|alz)$/;
		var maxSize = 5242880;//5mb
		console.log(fileName);
		console.log(fileSize);
		if(fileSize >= maxSize) {
			alert("파일사이즈 초과");
			return false;
		}
		if(regex.test(fileName)) {
			alert("업로드 불가능한 확장자")
			return false;
		}
		return true;
	}
	
	
	/**
	 * 첨부파일 로드하기
	 */
	function loadAttachment(result, editable) {
		var str ="";
		$(result).each(function(i,obj) {
			str += `<li data-path="${obj.uploadPath}" data-uuid="${obj.uuid}" data-filename="${obj.fileName}"
						data-type="${obj.fileType}">`;
			str = addThumbnail(str,obj,editable);
			str = addDownloadLink(str,obj,editable);
			
			if(editable) {
				str = addDeleteButton(str,obj);
			}
			str +=`</li>`;
		});
		if($(".uploadResult ul") == undefined || $(".uploadResult ul").length == 0) {
			$(".uploadResult").append("<ul></ul>");
		}
		$(".uploadResult ul").append(str);
		
		/////////////////////////////////////////////
		function addDownloadLink(str,obj,editable) {
			var pathAndUuid = encodeURIComponent(obj.uploadPath + "\\" + obj.uuid);
			var fileName = encodeURIComponent(obj.fileName);
			if(editable) {
				str += `<span>${obj.fileName}</span>`;
			} else {
				str += `<a href="/download?pathAndUuid=${pathAndUuid}&fileName=${fileName}">
						${obj.fileName}
						</a>`;	
			}
			return str;
		}
		function addThumbnail(str,obj,editable){
			console.log(obj);
			var fileCallPath = encodeURIComponent(obj.uploadPath + "\\s_" + obj.uuid + "_" +obj.fileName);
			console.log(fileCallPath);
			var originPath = obj.uploadPath + "\\" + obj.uuid + "_" +obj.fileName;
			originPath = originPath.replaceAll("\\","\\\\");//"\\\\" -(html)-> "\\" -(js)-> \
			
			if(obj.fileType) {
				if(editable) {str += `<img src="/thumbnail?fileName=${fileCallPath}">`;}
				else {str += `<a href="javascript:showImage('${originPath}')"><img src="/thumbnail?fileName=${fileCallPath}"></a>`;}
			}
			else {
				str += `<img src="/resources/img/attach.png">`;
			}
			return str;
		}
		function addDeleteButton(str,obj) {
			str += `<button type="button" data-file="${obj.uploadPath}\\${obj.uuid}_${obj.fileName}" 
				data-type="${obj.fileType}" class="btn btn-warning btn-circle">
					<i class="fa fa-times"></i>
				</button>`;
			return str;
		}
	}
	
	/**
	 * 파일 선택 폼 변경 감지시 실행할 동작
	 */
	function formChanged(e,initForm) {
		var formData = new FormData();
		var inputFile = $("input[name='uploadFile']");
		var files = inputFile[0].files;
		console.log(files);
		
		//add File to formData
		for(var i = 0 ; i< files.length; i++) {
			if(!checkExtension(files[i].name,files[i].size)) {
				return false;
			}
			
			formData.append("uploadFile",files[i]);
		}
		$.ajax({
			url: '/uploadFormAction',
			processData: false, // 이거 
			contentType: false, // 둘다 false로 해야됨
			beforesend: function(xhr) {
				console.log(csrf);
				xhr.setRequestHeader(csrf.csrfHeaderName,csrf.csrfTokenValue);//csrfToken setting in ajax request
			},
			data: formData,
			type: 'POST',
			datatype: 'json',//response datatype
			success: function(result) {
				console.log(result);
				loadAttachment(result,true);
				alert(result);
			},
			complete: function() {
				console.log("complete");
				$(".uploadDiv").html(initForm.html());
				$("#file-input").change(function(e) {formChanged(e,initForm);});//버튼에 등록했을 때와는 다르게 자기 자신을 처음상태로 되돌리므로, 이벤트 리스너도 다시 등록해줘야함
			}
		});//$.ajax
	}
	
	/**
	 * getAjax after document is loaded;
	 * editable : modify -> true, get -> false
	 */
	function getAttachment(bno,editable) {
		$.getJSON("/board/getAttachList",{bno:bno},function(arr) {
			loadAttachment(arr,editable);
		});
		//jquery.getJSON(url,param,success handler)
	}
	
	function addAttachmentDataToForm(targetForm) {
		var str = "";
		$(".uploadResult ul li").each(function(i,obj) {
			var jobj = $(obj);
			str += `<input type="hidden" name="attachList[${i}].fileName" 
						value="${jobj.data("filename")}">`;
			str += `<input type="hidden" name="attachList[${i}].uuid" 
				value="${jobj.data("uuid")}">`;
			str += `<input type="hidden" name="attachList[${i}].uploadPath" 
				value="${jobj.data("path")}">`;
			str += `<input type="hidden" name="attachList[${i}].fileType" 
				value="${jobj.data("type")}">`;
		});
		targetForm.append(str);
		
	}
	
	return {
		showImage : showImage,
		setCloseImageListener : setCloseImageListener,
		getAttachment : getAttachment,
		addAttachmentDataToForm : addAttachmentDataToForm,
		formChanged : formChanged
	};
})();
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/**
 * execute on import
 */
$(document).ready( function () { 

	/**
	 * 파일 업로드 인풋 valueChangeListener 등록
	 */
	var initForm = $(".uploadDiv").clone();
	$("#file-input").change(function(e) {attachmentService.formChanged(e,initForm);});
	
	
	/**
	 * 삭제 버튼 클릭 리스너 등록
	 */
	$(".uploadResult").on("click","button",function(e) {
		var targetFile = $(this).data("file");
		var type = $(this).data("type");
		console.log(targetFile);
		var el = $(this).closest("li");
		$.ajax({
			url: '/deleteFile',
			data: {fileName: targetFile, type:type},
			dataType : 'text',
			type : 'POST',
			success : function(result) {
				alert(result);
				console.log(el);
				el.remove();
			}
		});
	});
	
});//$(document).ready