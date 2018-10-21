var $f = jQuery.noConflict();
  	$f(document).ready(function() {
	
		
		function checkFileType(filePath){
		var fileFormat = filePath.split(".");
		if(fileFormat.indexOf("xls") > -1){
		return true;
		}else if(fileFormat.indexOf("xlsx") > -1){
		return true;
		}else{
		return false;
		}
		}
		
		$f(document).on("click", '#excelUp', function(event) { 
			
			var file = $f("#excel").val();
			if(file == "" || file == null){
			alert("파일을 선택");
			return false;
			}else if(!checkFileType(file)){
			alert("엑셀 파일만 업로드");
			return false;
			}
			var fileFormat = file.split(".");
			var fileType = fileFormat[1];
			if(confirm("업로드 하시겠습니까?")){
				
				$f('#excelUpForm_student').attr("action","studentListExcelUpload");
				var options = {
					
				
					success:function(data){
					alert("업로드 완료");
					window.location.reload();
					
				},
				error : function(request, status, error) {
					alert("업로드가 실패되었습니다.");
					window.location.reload();
				},
				
				type: "POST",
				data : {"excelType" : fileType}
				};
				
				     // submit the form
				$f('#excelUpForm_student').ajaxSubmit(options);
				     // return false to prevent normal browser submit and page navigation
				 
					
				}
		});
		
		
		$f(document).on("click", '#excelUpWord', function(event) { 
			
			var file = $f("#excel_word").val();
			if(file == "" || file == null){
			alert("파일을 선택");
			return false;
			}else if(!checkFileType(file)){
			alert("엑셀 파일만 업로드");
			return false;
			}
			var fileFormat = file.split(".");
			var fileType = fileFormat[1];
			if(confirm("업로드 하시겠습니까?")){
				
				$f('#excelUpForm_word').attr("action","wordListExcelUpload");
				var options = {
					
				
					success:function(data){
						alert("업로드 완료");
						
						var errList=JSON.parse('${jsonErrList}');
						alert(errList);
						
						//window.location.reload();
					
				},
				error : function(request, status, error) {
					alert("업로드가 실패되었습니다.");
					window.location.reload();
				},
				
				type: "POST",
				data : {"excelType" : fileType}
				};
				
				     // submit the form
				$f('#excelUpForm_word').ajaxSubmit(options);
				     // return false to prevent normal browser submit and page navigation
				 
					
				}
		});
		
		
		});