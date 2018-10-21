<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="robots" content="noindex">
    
    <title>영어단어 학습프로젝트</title>
    <link href="resources/libs/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="resources/libs/font-awesome/css/font-awesome.min.css" rel="stylesheet"><!-- Common Libs CSS -->
    <link href="resources/css/libs.common.min.css" rel="stylesheet"><!-- Page Libs CSS --><!-- Just Bootstrap Theme -->
    <link href="resources/css/just.css" rel="stylesheet">
  </head>
  <body>
    
    <div class="st-wrapper">
     	<div class = "bodytext" style="margin-top:15px">
            <span>영어학습프로그램</span>  
            <a href="logout" type="button" class="btn btn-warning btn-sm">로그아웃</a>
            <a href="mypage" type="button" class="btn btn-warning btn-sm">나의 페이지</a>
        </div>
        <br/>
        <br/>
        
       <div class="container">
          <div class="row col-md-12 center-block" > 
            <div class="col-md-4 col-md-offset-4">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h3 class="panel-title">시험지생성</h3>
                    </div>
                    <div class="panel-body " >
                        <form role="form"  id="maketestForm" action="makeTest" method="post">
                            <fieldset>
                                <span>생성 조건 </span>
                                <div class="form-group">
             					</div>
             				
             					<div class="form-inline">
             						<label>범위 :</label>
	                                  <input class="form-inline" id="start" name="start" style="width:20%;" onkeydown="onlyNumber(this)" > 에서
	                                  <input class="form-inline" id="end" name="end"  style="width:20%;" onkeydown="onlyNumber(this)">  까지
             					</div>
             					<p class="help-block ">숫자만 입력해주세요</p>
                               <br/>
                               
                               <c:if test="${msg eq 'no search'}">
                                 <div class="alert alert-warning">
                                	범위 내 단어가 없습니다.
                            	 </div>
                                </c:if>
                                
                                <c:if test="${msg eq 'samll question'}">
                                 <div class="alert alert-warning">
                                	선택한 범위 내 단어수가 입력한 문항수 보다 적습니다.
                            	 </div>
                                </c:if>
                                 
                               <c:if test="${msg eq 'no test'}">
                                 <div class="alert alert-warning">
                                	뜻에 null값이 포함되어있습니다. 
                            	 </div>
                                </c:if>
                                
                               <div class="form-inline">
             						<label>문항 수:</label>
	                                  <input class="form-inline" id="cnt" name="cnt" style="width:15%;" onkeydown="onlyNumber(this)" > 
             						     최대 50문항
             					</div>
             					<p class="help-block ">숫자만 입력해주세요</p>
             					
             					<div class="form-group">
             					</div>
             				
             					<br/>
             					<div class="form-inline">
             						  
             						  <label>문항유형 :</label>
             						  
                                      <label class="radio-inline">
                                           <input type="radio" name="typeselect"  value="select" checked>
                                          		객관식(한->영)
                                      </label>
                                      
                                        <label class="radio-inline">
                                            <input type="radio" name="typeselect" value="text">
                                              	주관식(영->한)
                                        </label>
                                      
             					</div>
 								
 								<div class="form-group">
             					</div>
             					
             					<br/>
								<!-- Change this to a button or input when using this as a form -->
                                <button type="button" class="btn btn-lg btn-success btn-block" onclick="formcheck()">생성</a>
                            </fieldset>
                        </form>
                    </div>
                </div>
                 <center><a href="word" type="button" class="btn btn-primary btn-sm">단어보기</a></center>
            </div>
    </div>
	 </center>
	</div>
    </div><!-- Common Libs JS -->
   
  </body>
  
   <script type="text/javascript"  language="javascript" src="resources/datatable/js/jquery.js"></script>
    
  	<script>
  	function onlyNumber(obj) {
  	    $(obj).keyup(function(){
  	         $(this).val($(this).val().replace(/[^0-9]/g,""));
  	    }); 
  	}
  	function formcheck(){
  		
  		var checkcount=$("#end").val()-$("#start").val()+1;
  		var cnt=$("#cnt").val();
  		
  		if(checkcount<cnt){
  			alert("문항범위보다 문제수가 더 많습니다.");
  			return;
  		}
  		else if(cnt>50){
  			alert("최대  문항수는 50문제까지 입력가능합니다.");
  			return;
  		}else if($("#end").val()==''){
  			alert("끝 범위가 지정되지않았습니다.");
  			return;
  		}else if($("#start").val()==''){
  			alert("시작 범위가 지정되지않았습니다.");
  			return;
  		}else if(cnt==''){
  			alert("문항수를 입력하시기 바랍니다.");
  			return;
  		}
  		else{
  			$("#maketestForm").submit();
  		}
  	}
  	</script>
    <script type="text/javascript" language="javascript" 
		src="resources/js/test.js"></script>    
  	<script src="resources/js/libs.common.min.js"></script><!-- Common App Modules -->
    <script src="resources/js/modules/app.js"></script><!-- Page Libs JS --><!-- Page App Modules -->
</html>