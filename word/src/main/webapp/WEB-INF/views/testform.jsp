<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ page session="false"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="robots" content="noindex">

<style type="text/css">
.wrapper {
	display: grid;
	grid-template-columns: repeat(3, 1fr);
	grid-auto-rows: 100px;
}

.box1 {
	grid-column-start: 1;
	grid-column-end: 4;
	grid-row-start: 1;
	grid-row-end: 3;
}

.box2 {
	grid-column-start: 1;
	grid-row-start: 3;
	grid-row-end: 5;
}

.option {
	border: 1px solid green;
	text-align: center;
}

.question_select {
	
}
</style>

<title>영어단어 학습프로젝트</title>
<link href="resources/libs/bootstrap/css/bootstrap.min.css"
	rel="stylesheet">
<link href="resources/libs/font-awesome/css/font-awesome.min.css"
	rel="stylesheet">
<!-- Common Libs CSS -->
<link href="resources/css/libs.common.min.css" rel="stylesheet">

<!-- Page Libs CSS -->
<!-- Just Bootstrap Theme -->
<link href="resources/css/just.css" rel="stylesheet">
<link href="resources/css/jquery-ui.css" rel="stylesheet">
</head>
<body>

	<div class="st-wrapper">
		<div class="bodytext" style="margin-top: 15px">
			<span>영어학습프로그램</span> <a href="logout" type="button"
				class="btn btn-warning btn-sm">로그아웃</a>
		</div>
		<br /> <br />

		<div class="container">
			<div class="row col-md-12 center-block">
				<div class="col-md-6 col-md-offset-3">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h3 class="panel-title">시험</h3>
						</div>
						<div class="panel-body ">
						
						<div id="clockdiv"></div>
						
							<!-- 객관식 유형  -->
							<c:if test="${type eq 'select'}">
								<div id="question_box" class="question_select">
									문제 <span id="question_count"></span>
									<div style="float: right">
										<span id="question_right"></span> / <span id="question_total"></span>
										<span>문제</span>
									</div>
									<input type="text" class="form-control" id="question_word"
										readonly required /> <input type="hidden"
										class="form-control" id="question_idx" readonly required />

									<div class="wrapper" id="select"></div>
								</div>
								<form id="select_result" enctype="application/json"
									action="testinsert" method="post"></form>
							</c:if>
							<!-- 아래는 주관식  -->
							<c:if test="${type eq 'text'}">
							<center>
							<h4 id="title"></h4>
							 <div >
							     <p id="content"></p>
							 </div>
							  <br/>
							 <div  >
							    <button type="button" class="btn btn-primary" id="text_save" style="display:none;">Save changes</button>
							 </div>
							 </center>
							 <br/>
							<div id="question_text">
								
							</div>
							
							<form id="text_result" enctype="application/json"
									action="testinsert" method="post"></form>
							</c:if>
						</div>
					</div>
				</div>
			</div>
			</center>
		</div>
	</div>
	
	
	
	<!-- Modal -->
							<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true" data-backdrop="static" data-keyboard="false">
							  <div class="modal-dialog" role="document">
							    <div class="modal-content">
							      <div class="modal-header">
							        <h4 class="modal-title" id="title"></h4>
							        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
							          <span aria-hidden="true">&times;</span>
							        </button>
							      </div>
							      <div class="modal-body">
							     	<p id="content"></p>
							      </div>
							      <div class="modal-footer">
							        <button type="button" class="btn btn-primary" id="select_save">Save changes</button>
							      </div>
							    </div>
							  </div>
							</div>
			

				
			<c:set var="now" value="<%=new java.util.Date()%>" />
			<c:set var="sysYear"><fmt:formatDate value="${now}" pattern="yyyy-MM-dd hh:mm:ss" /></c:set> 
			
			
			
	<script type="text/javascript" language="javascript"
		src="resources/js/core.js"></script>	
	<script type="text/javascript" language="javascript"
		src="resources/js/enc-base64.js"></script>	
	<script type="text/javascript" language="javascript"
		src="resources/js/md5.js"></script>			
	<script type="text/javascript" language="javascript"
		src="resources/js/evpkdf.js"></script>				
	<script type="text/javascript" language="javascript"
		src="resources/js/cipher-core.js"></script>	
	<script type="text/javascript" language="javascript"
		src="resources/js/lib-typedarrays.js"></script>				
	<script type="text/javascript" language="javascript"
		src="resources/js/aes.js"></script>

	<script type="text/javascript" language="javascript"
		src="resources/datatable/js/jquery.js"></script>
	<script type="text/javascript" language="javascript"
		src="resources/js/jquery-ui.js"></script>


  <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.2/js/bootstrap.min.js" integrity="sha384-o+RDsa0aLu++PJvFqy8fFScvbHFLtbvScb8AjopnFD+iEQ7wo/CG0xlczd+2O/em" crossorigin="anonymous"></script>
	<!-- Common Libs -->
	<script type="text/javascript">
	
	
	var $f = jQuery.noConflict();
	var timer = Number('${timer}');
	var clock = document.getElementById("clockdiv");
	var multipleChoiceObject;
	var threadtime;
	
	$f(document).ready(function() {
	
		
		
	var cryptoText = '${ques_cryptoText}';
	/*
		객관식 셋팅 
	*/
		
	if(cryptoText!=''){
		/*
		 * 객관식 복호화
		 */
		 var key = '${ques_key}';
		 var iv = '${ques_iv}';
		 
		var encrypted = CryptoJS.enc.Base64.parse(cryptoText);
		var key = CryptoJS.enc.Base64.parse('${ques_key}');
		var iv = CryptoJS.enc.Base64.parse('${ques_iv}');


		var jsonObj = CryptoJS.enc.Utf8.stringify(CryptoJS.AES.decrypt(
	    { ciphertext: encrypted },
	    key, 
	     // edit: changed to Pkcs5
	    { mode: CryptoJS.mode.CBC, padding: CryptoJS.pad.Pkcs7, iv: iv,  }));
		 
		

		var html="";
		
		for(var i=1;i<=6;i++){
			html+="<Button id='question_option_"+i+"' class='option'></Button>"
				+"<input type='hidden' id='question_option_idx_"+i+"'/>"
		}
		$("#select").html(html);	
		
		var questionListJson = JSON.parse(jsonObj);
			
			
		multipleChoiceObject = new multipleChoice(questionListJson);  
		multipleChoiceObject.next();
		
		var count=1;
		for(var i=1;i<=6;i++){
			
			(function(j,count) {
				$("#question_option_"+j).on("click", function(){
					count=Number(count);
					/*
						count 변수 모달창과 함께 타이머종료할 수 있도록 제어변수 선언 
					*/
					
					clearInterval(threadtime);
					
					multipleChoiceObject.next($("#question_option_idx_"+j).val());
					
					if(count!=questionListJson.length){
						threadtime = initsetTimer(timer);
					}
					count++;
					
					//alert("threadtime2 : " + threadtime);	
				});
			  })(i,count);
		}
		
		//clock.innerHTML ='seconds: ' + timer;
		threadtime = initsetTimer(timer);
		//alert("threadtime1 : " + threadtime);	
		
	}
	
	/*	
		주관식 셋팅 
	*/

	
	var cryptoText = '${question_cryptoText}';
	if(cryptoText!=''){
		var key = '${ques_key}';
		var iv = '${ques_iv}';
		 
		var encrypted = CryptoJS.enc.Base64.parse(cryptoText);
		var key = CryptoJS.enc.Base64.parse('${ques_key}');
		var iv = CryptoJS.enc.Base64.parse('${ques_iv}');

		var jsonObj = CryptoJS.enc.Utf8.stringify(CryptoJS.AES.decrypt(
	    { ciphertext: encrypted },
	    key, 
	     // edit: changed to Pkcs5
	    { mode: CryptoJS.mode.CBC, padding: CryptoJS.pad.Pkcs7, iv: iv,  }));
		 
		
		var question_wordTextList = JSON.parse(jsonObj);
		
		
		var html="<center><table><thead><th width='30px'>번호</th><th style='text-align:center;'>뜻</th><th style='text-align:center;'>영어</th></thead>";
		var count=1;
		for(var i=0;i<question_wordTextList.length;i++){
			
			html+="<tr><td >"+count+"</td> <td>"+question_wordTextList[i].meaning+"</td><td id="+count+"><input type='text' name='answer' id='answer_"+count+"_"+question_wordTextList[i].idx+"' onkeydown='onlyAlphabet(this)' /> <input type='hidden' name='answer_index' id='answer_"+question_wordTextList[i].idx+"' value='"+question_wordTextList[i].idx+"'/></td></tr>";
			
			count++;
		}
		html+="</table>  <br> <Button id='textTestComplete'>완료</Button> </center>";
		$("#question_text").html(html);	
		
		threadtime=initTextsetTimer(Number(question_wordTextList.length) * Number(timer),question_wordTextList);
		
		$f('#textTestComplete').on("click",function(){
			texttestcomplete(question_wordTextList,threadtime);
		});
	}
	});
	
	/*
	객관식 완료 
*/
	function multipleChoice(json){
		var cnt = -1;
		var right = 0;
		var wlists = [];
		var rlists = [];
		var questionListJson=json;
		var total=questionListJson.length;
		var type = '${type}';
		var testtype = '${testtype}';
		this.next = function(idx){
	        cnt++;        
	        if(cnt==0){
	        	$("#question_total").html(total);
	        }
	        else{
	        	this.checkProc(idx);
	        }
	       
	       this.setProc();
	        
	    }
	
	    this.checkProc = function(idx){				
	    	if($("#question_idx").val()==idx){
	    		if(total != (wlists.length + rlists.length)){
	    			rlists.push($("#question_idx").val());
		    		right++;
	    		}
	    	}
	    	else{
	    		var flag=true;
	    		for (var i = 0, len = wlists.length; i < len; i++) {
	    			 if(wlists[i]==$("#question_idx").val()){
	    				 flag=false;
	    			 }
	    		}
	    		if(flag && total != (wlists.length + rlists.length)){
	    			wlists.push($("#question_idx").val());
	    		}
	    	}
	    }
    
    this.setProc = function(){
    	
        if(cnt<questionListJson.length){
        	$("#question_right").html(right);
        	$("#question_count").html(cnt+1);
        	$("#question_word").val(questionListJson[cnt].word);
        	$("#question_idx").val(questionListJson[cnt].idx);
        	for(var i=1; i<=6; i++){
        		$("#question_option_"+i).html(questionListJson[cnt].choiceList[i-1].meaning);
        		$("#question_option_idx_"+i).val(questionListJson[cnt].choiceList[i-1].idx);
        	}
        	
        }
        else{
     
        	clearInterval(threadtime);
        	
        	$("#title").html("시험 결과");
        	
        	var html="";
        	html+="</br>";
        	html+=" 이름 : <c:out value='${sstd.stName}'  />  학번 :<c:out value='${sstd.stNum}'  />  ";
        	html+="</br>";
        	html+=" 시간 : <c:out value='${sysYear}' />";
        	html+="</br>";
        	html+="전체 문항 갯수 : "+ total ;
        	html+="</br>";
        	html+="맞은 문항 갯수 : "+rlists.length;
        	html+=" / ";
        	html+="틀린 문항 갯수: "+wlists.length;
        	html+="</br>";
        	/*
        		날짜 이름 백분율정도 기입 추가 
        	*/
     	    $("#content").html(html);
        	
        	$('#exampleModal').modal('show');
        	
        	$("#select_save").on("click", function(){
        		$("#select_result").append("<input type='hidden' name='testtype' value='"+testtype+"' />");
        		$("#select_result").append("<input type='hidden' name='type' value='"+type+"' />");
	          	$("#select_result").append("<input type='hidden' name='total' value='"+total+"' />");
	          	$("#select_result").append("<input type='hidden' name='wrong' value='"+wlists.length+"' />");
	          	$("#select_result").append("<input type='hidden' name='wrongList' value='"+JSON.stringify(wlists)+"' />");
	          	$("#select_result").append("<input type='hidden' name='rightList' value='"+JSON.stringify(rlists)+"' />");
	          	$("#select_result").submit();
        	});
        	
        }
    }
}
	
	function texttestcomplete(question_wordTextList,threadtime){
		
		var wlists = [];
		var rlists = [];
		var count=1;
		var wrong=0;
		var total=question_wordTextList.length;
		var type = '${type}';
		var testtype = '${testtype}';
		for(var i=0;i<question_wordTextList.length;i++){
			
			if(question_wordTextList[i].word!=$f("#answer_"+count +"_"+question_wordTextList[i].idx+"").val()&&
					question_wordTextList[i].word!=$f("#answer_"+count +"_"+question_wordTextList[i].idx+"").val().toLowerCase() 
					&& question_wordTextList[i].word!=$f("#answer_"+count +"_"+question_wordTextList[i].idx+"").val(). toUpperCase() ){
				
				wlists.push(question_wordTextList[i].idx);
				wrong++;
				var html="";
				html+="정답 : <h7 style='color:red;'>"+ question_wordTextList[i].word+"</h7>";
				
				$f("#"+count).html( $f("#answer_"+count +"_"+question_wordTextList[i].idx+"").val() + html); 
				$f("#"+count).attr("width","150px");
				
				$f("#answer_"+count +"_"+question_wordTextList[i].idx+"").hide();
			}else{
				$f("#answer_"+count +"_"+question_wordTextList[i].idx+"").attr("readonly", true);
				rlists.push(question_wordTextList[i].idx);	
				
			}
			count++;
		}
	
		clearInterval(threadtime);
				
			/*
				
			*/
			
			
			$("#title").html("시험 결과");
        	
        	var html="";
        	html+="</br>";
        	html+=" 이름 : <c:out value='${sstd.stName}'  />  학번 :<c:out value='${sstd.stNum}'  />  ";
        	html+="</br>";
        	html+=" 시간 : <c:out value='${sysYear}' />";
        	html+="</br>";
        	html+="전체 문항 갯수 : "+ total ;
        	html+="</br>";
        	html+="맞은 문항 갯수 : "+rlists.length;
        	html+=" / ";
        	html+="틀린 문항 갯수: "+wlists.length;
        	html+="</br>";
        	/*
        		날짜 이름 백분율정도 기입 추가 
        	*/
     	    $("#content").html(html);
        	
        	$("#text_save").show();
        	$("#textTestComplete").hide();
        	
			$("#text_save").on("click", function(){
				
				$("#select_result").append("<input type='hidden' name='testtype' value='"+testtype+"' />");
				$("#text_result").append("<input type='hidden' name='type' value='"+type+"' />");
				$("#text_result").append("<input type='hidden' name='total' value='"+total+"' />");
		    	$("#text_result").append("<input type='hidden' name='wrong' value='"+wlists.length+"' />");
		    	$("#text_result").append("<input type='hidden' name='wrongList' value='"+JSON.stringify(wlists)+"' />");
		    	$("#text_result").append("<input type='hidden' name='rightList' value='"+JSON.stringify(rlists)+"' />");
		    	$("#text_result").submit();
        	});
        	
		
	}
	
	/*
		주관식 타이머 초기화 및 설정 
	*/
	
	function initTextsetTimer(timer,question_wordTextList){
		var timetmp=timer;	
		var flag=0;
		clock.innerHTML ='seconds: ' + timer;

		return setInterval(function(){
			if(flag==1){
				texttestcomplete(question_wordTextList);
				flag=0;
			}
			timetmp--;
			clock.innerHTML ='seconds: ' + timetmp;
		
			if(timetmp==1){
				
				timetmp=Number(timer)+1;	
				flag=1;
			}

		},1000);
	}	
	/*
	객관식 타이머 초기화 및 설정 
	*/
	function initsetTimer(timer){
		var timetmp=timer;	
		var flag=0;
		clock.innerHTML ='seconds: ' + timer;

		return setInterval(function(){
			if(flag==1){
				multipleChoiceObject.next(0);
				flag=0;
			}
			timetmp--;
			clock.innerHTML ='seconds: ' + timetmp;
		
			if(timetmp==1){
				
				timetmp=Number(timer)+1;	
				flag=1;
			}

		},1000);
	}	
	</script>

	<script type="text/javascript" language="javascript"
		src="resources/js/test.js"></script>
	<script src="resources/js/libs.common.min.js"></script>
	<!-- Common App Modules -->
	<script src="resources/js/modules/app.js"></script>
	<!-- Page Libs JS -->
	<!-- Page App Modules -->
			
			
</body>



	
</html>