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
        <div class="row">
            <div class="col-md-4 col-md-offset-4">
                <div class="login-panel panel panel-default">
                    <div class="panel-heading">
                        <h3 class="panel-title">로그인</h3>
                    </div>
                    <div class="panel-body">
                        <form role="form" action="login" method="post">
                            <fieldset>
                                <div class="form-group">
                                    <input class="form-control" placeholder="아름을 입력하시오"  name="name"  autofocus>
                                </div>
                                
                                <div class="form-group">
                                    <input class="form-control" placeholder="학번 또는 교번를 입력하시오" name="number" value="">
                                </div>
                                
                                <!-- 로그인 에러 메시지 -->
                               <c:if test="${err eq 'wrong_student_login'}">
                                 <div class="alert alert-warning">
                                	이름과 학번이 일치하지않습니다.
                            	 </div>
                                </c:if>
                            	
                                <span>------아래는 관리자만 해당------</span>
                                <div class="form-group">
             					</div>
             					
                                <div class="form-group">
                                    <input class="form-control" placeholder="Password" name="password" type="password" value="">
                                </div>
                               
                                 <c:if test="${err eq 'wrong_admin_login'}">
                                 <div class="alert alert-warning">
                                	이름과 교번 그리고 비밀번호가 일치하지 않습니다.
                            	 </div>
                                </c:if>
                                
                               <c:if test="${msg eq 'relogin'}">
                                 <div class="alert alert-warning">
                                	변경된 비밀번호로 다시 로그인 하시길 바랍니다.
                            	 </div>
                                </c:if>
                            
                               	<button class="btn btn-lg btn-success btn-block" type="submit">로그인</button>
                                
                            </fieldset>
                        </form>
                        
                        <c:if test="${msg eq 'changepassword'}">
                     				<div class="modal-header">
									  <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
									  <h4 class="modal-title">관리자 비밀번호 변경</h4>
									</div>
									<div class="modal-body">
									  <form name="chg_passwd_form" action="chg_passwd" method="post">
									     <div class="form-group">
									      <label>교번</label>
									      <input type="text" name="userid" class="form-control" readonly required value="${number}"/>
									      <input type="hidden" name="adNum" value="${number}" />
									    </div>
									    
									    <div class="form-group">
									      <label>비밀번호 <small>(필수)</small></label>
									      <input  type="password"  id="user_pass" name="passwd1" class="form-control" required />
									    </div>
									    <div class="form-group">
									      <label>비밀번호 확인 <small>(필수)</small></label>
									      <input  type="password" id ="chpass" name="passwd2" class="form-control" required />
									    </div>

									    <div class="form-group" style="text-align: right">
									      <input class="btn btn-primary" type="submit" value="관리자 비밀번호 변경" />
									    </div>
									  </form>
									</div>
                     			</c:if>
                    </div>
                </div>
                 <center><a href="word" type="button" class="btn btn-primary btn-sm">단어보기</a></center>
            </div>
        </div>
    </div>
	
    </div><!-- Common Libs JS -->
 
  </body>
  
  
     
  	<script src="resources/js/libs.common.min.js"></script><!-- Common App Modules -->
    <script src="resources/js/modules/app.js"></script><!-- Page Libs JS --><!-- Page App Modules -->
</html>