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
        </div>
        <br/>
        <br/>
        
       <div class="container">
            <div class="panel panel-default">
                      
                        <div class="panel-body " >
                        <form role="form"  method="post" id="searchWord" action="searchWord">
                            <fieldset>
             					<div class="form-inline">
             						<label>범위 :</label>
	                                  <input class="form-inline" id="start" name="start" style="width:20%;" onkeydown="onlyNumber(this)" > 에서
	                                  <input class="form-inline" id="end" name="end"  style="width:20%;" onkeydown="onlyNumber(this)">  까지
             					</div>
             					<p class="help-block ">숫자만 입력해주세요</p>
             					
             					 <button type="button" class="btn btn-lg btn-success btn-block" onclick="search()">검색</a>
                               <br/>
                               
                               <c:if test="${msg eq 'no search'}">
                                 <div class="alert alert-warning">
                                	범위 내 단어가 없습니다.
                            	 </div>
                               </c:if>
                            </fieldset>
                        </form>
                    </div>
                     <center><a href="word" type="button" class="btn btn-primary btn-sm">전체 단어보기</a></center>
                        <div class="panel-heading">
                           	영어단어 리스트 
                        </div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                             <h4>영어 단어</h4>
							<table class="table table-striped table-bordered table-hover">
								<thead>
									<th  width="20%" style="text-align: center">
										
									</th>
									<th style="text-align: center; font-size: 15px;">
										철자
									</th>
									<th style="text-align: center; font-size: 15px;">
										뜻
									</th>								
								</thead>
								
								<tbody>
								<c:forEach var="item" items="${wordList}"  varStatus="status">
			 										<tr>
			 											<td style="text-align: center; font-size: 14px;">
			 												${item.woNo}
			 											</td>
			 											<td style="text-align: center; font-size: 14px;">
			 												${item.woWord}
			 											</td>
			 										
			 											<td style="text-align: center; font-size: 14px;">
			 											${item.woMeaning}</td>
			 										</tr>
			 									</c:forEach>
									<!-- 단어리스트  -->							
								</tbody>							
							</table>
                        </div>
                        <!-- /.panel-body -->
                    </div>
                    <!-- /.panel -->
    </div>
	
    </div><!-- Common Libs JS -->
   <script type="text/javascript"  language="javascript" src="resources/datatable/js/jquery.js"></script>
    
  	<script>
  	function onlyNumber(obj) {
  	    $(obj).keyup(function(){
  	         $(this).val($(this).val().replace(/[^0-9]/g,""));
  	    }); 
  	}
	function search(){
  		
  		var checkcount=$("#end").val()-$("#start").val();
  		if($("#end").val()==''){
  			alert("끝 범위가 지정되지않았습니다.");
  			return;
  		}else if($("#start").val()==''){
  			alert("시작 범위가 지정되지않았습니다.");
  			return;
  		}
  		else{
  			$("#searchWord").submit();
  		}
  	}
  	
  	</script>
  	<script src="resources/js/libs.common.min.js"></script><!-- Common App Modules -->
    <script src="resources/js/modules/app.js"></script><!-- Page Libs JS --><!-- Page App Modules -->
  </body>
</html>