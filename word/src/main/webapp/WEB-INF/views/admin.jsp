<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
   
    <!--
    <link href="resources/datatable/css/jquery.dataTables.css" rel="stylesheet">
    <link href="resources/datatable/css/dataTables.bootstrap.css" rel="stylesheet">
 	  -->
 	  
 	<link rel="stylesheet" type="text/css" href="resources/datatable/css/jquery.dataTables.min.css">
 	
    <link href="https://cdn.datatables.net/buttons/1.5.1/css/buttons.dataTables.min.css" type="text/css" rel="stylesheet">
 	
 	
 	<style>
 		
 		div.toolbar{
 		float:right;
 		}
 		
		ul{
		   list-style:none;
		   padding-left:0px;
		  }

		canvas {
			-moz-user-select: none;
			-webkit-user-select: none;
			-ms-user-select: none;
		}
	
 	</style>


  </head>
  <body>
    
    <div class="st-wrapper">
     	<div class = "bodytext" style="margin-top:15px">
            <span>영어학습프로그램</span> <a href="logout" type="button" class="btn btn-warning btn-sm">로그아웃</a>
        </div>
        <br/>
        <br/>
        
       <div class="container">
       	<form id="setSelectTimer" action="setSelectTimer" method="post">
       		객관식 타이머 설정 : <input type="text" name="timer" id="select_timer"  value="${selecttimer}"/>
       		<input type="submit" value="변경"/>
       	</form> 
       	<br/>
       	<form id="setTextTimer" action="setTextTimer" method="post">
       		주관식 타이머 설정 : <input type="text" name="timer" id="text_timer"  value="${texttimer}"/>
       		<input type="submit" value="변경"/>
       	</form> 
       	<br/>
             <!-- /.col-lg-12 -->
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                           	관리자페이지 
                        </div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                            <!-- Nav tabs -->
                            <ul class="nav nav-pills">
                                <li class="active"><a href="#st-pills" data-toggle="tab">학생 시험이력</a>
                                </li>
                                <li><a href="#sg-pills" data-toggle="tab">학생 그룹화</a>
                                </li>
                            </ul>

                            <!-- Tab panes -->
                            <div class="tab-content">
                            
                                <div class="tab-pane fade in active" id="st-pills">
                                    <h4>학생시험이력</h4>
                                     <table width="100%" class="table table-striped table-bordered table-hover " id="student-test">
		                                <thead>
		                                    <tr>
		                                        <th></th>
		                                        <th>시험 유형</th>
												<th>시험 본 유형</th>
		                                        <th>학생이름</th>
		                                        <th>학생번호</th>
		                                        
		                                        <th>그룹명</th>
		                                        <th>시험본 횟수</th>
		                                        <th>시험결과(백분율)</th>
		                                        <th>시험날짜</th>
		                                        <th>선택</th>
		                                    </tr>
		                                </thead>
		                                
		                                <tbody>
		                                <c:forEach var="resultItem" items="${reslist}" varStatus="status">
		                                	
		                                	<tr>
		                                	<td>${status.count}</td>
		                                	<c:if test="${resultItem.thWflag eq false}">
			                                		<td> 기본 </td>
			               	                 </c:if>
			                                <c:if test="${resultItem.thWflag eq true}">
			                                		<td> 오답 </td>
			                                </c:if>
			                                <c:if test="${resultItem.thType eq 'select'}">
			                                		<td>객관식</td>
			                                </c:if>
			                                	
			                                <c:if test="${resultItem.thType eq 'text'}">
			                                		<td>주관식</td>
			                                </c:if>
		                                	
		                                	<td><a href="javascript:openScoreModal('${resultItem.stNum}');">${resultItem.stName}</a></td>
		                                	<td>${resultItem.stNum}</td>
		                                	<td>${resultItem.groupName}</td>
		                                	<td>${resultItem.thTestNum}</td>
		                                	<td><fmt:formatNumber  value="${(resultItem.totalCount - resultItem.wrongCount)/resultItem.totalCount}" type="percent"></fmt:formatNumber> </td>
		                                	<td>
		                                		<fmt:formatDate value="${resultItem.thTestDate}" pattern="yyyy-MM-dd"/>
		                                	</td>
		                                	<td> 
		                                		<input type="checkbox" name="thIdx"  value="${resultItem.thIdx}"/>  
		                                	</td>
		                                	</tr>
		                                	
		                                </c:forEach>
		                                </tbody>
		                               <tbody>
		                               </tbody>
                                      </table>
                                </div>
                                <div class="tab-pane fade" id="sg-pills">
                               		<br/>
                               		<br/>
                               		
                             
                               		<div style=" float: left; width: 33%;" id="student">
	                               		<p>학생명단</p>
	                               		
	                               		<br/>
	                               		<center>
	                               		
	                               		<Button id="btn_studentInsert" type="button" class="btn btn-primary btn-sm">넣기</Button>		
	                               		
	                               		<Button id="btn_studentDelete" type="button" class="btn btn-primary btn-sm">삭제</Button>	
	                               		</center>
	                               		
	                               		<table border="0" id="studentList">
			                               		<c:forEach var="item" items="${slist}"  varStatus="status">
			 										<tr>
			 											<td>
			 												<label for="st_${item.stIdx}">
			 											   <input type="checkbox" name="stIdx" id="st_${item.stIdx}" value="${item.stIdx}"/>  
			 											     ${item.stName}, ${item.stNum}
			 											   </label>
			 											</td>
			 										</tr>
			 									</c:forEach>
		 								 </table>
	                            	</div>
                               			
                               		<div style=" float: left; width: 33%;" id="group">
	 									<div id="groupstudentlist">
	 									
	 									</div>
                               		</div>
                               		<div style=" float: left; width: 33%;">
                               			
                               			<p>그룹생성</p>
 									
	 									<input class="form-inline col-lg-6 col-md-6 col-sm-6 col-xs-8 " placeholder="" name="sgName" id="sgName" value="">
	                               		<Button id="btn_groupInsert" type="button" class="btn btn-primary btn-sm">생성</Button>
                               			
                               			<div class="form-group">
             							</div>
                               			
                               			<div class="alert alert-warning">
                                			중복된 그룹명을 생성할 수 없습니다. 
                            	 		</div>
                               			
                               			<div class="form-group">
             							</div>
                               			
                               			<p >그룹명단</p>
	 									
	 									<ul id="grouplist" >
	 										<c:forEach var="item" items="${sglist}"  varStatus="status">
		 						 			  	<li>	 
		 						 			  			<label for="sg_${item.sgIdx}">
		 						 			  				<input type="radio" class="sgIdx"  name="sgIdx" id="sg_${item.sgIdx}" value="${item.sgIdx}"/>
		 						 			  				${item.sgName}
		 						 			  			</label>
		 										</li>
	 										</c:forEach>
	 									</ul>	
                               		</div>
                                </div>
                            </div>
                        </div>
                        <!-- /.panel-body -->
                    </div>
                    <!-- /.panel -->
                </div>
                <!-- /.col-lg-6 -->
               
    </div>
	
	<center><a href="word" type="button" class="btn btn-primary btn-sm">단어보기</a></center>
		
	
	<div class="container">
			<!-- BEGIN CONTENT BODY -->
			<div class="page-content">
			<div class="page-head">
			<!-- BEGIN PAGE TITLE -->
			<div class="page-title">
			<h1>
			<span id="title"></span>
				<small id="titleSmall"></small>
			</h1>
			</div>
			<!-- END PAGE TITLE -->
			</div>
			<div class="col-lg-12 well">
			<div class="row">
			
			<form id="excelUpForm_student" enctype="multipart/form-data" method="post"  role="form" >
			<div class="form-inline col-sm-12">
			
				<div class="form-inline col-sm-6 " >
				<label>학생 업로드<label>
				<input id="excel" name="excel" class="file" type="file"
					multiple data-show-upload="false"
					data-show-caption="true">
				</input>
				</div>
				<button type="button" id="excelUp" ">등록</button>
			</div>
			</form>
			</div>
			
			<div id="errMsg" ></div>
			<div class="row">
			
			<form id="excelUpForm_word" enctype="multipart/form-data" method="post"  role="form" >
			<div class="form-inline col-sm-12">
			
				<div class="form-inline col-sm-6 " >
				<label>영어 단어 업로드<label>
			
				<input id="excel_word" name="excel_word" class="file" type="file"
					multiple data-show-upload="false"
					data-show-caption="true">
				</input>
				</div>
				
				
				<button type="button" id="excelUpWord">등록</button>
			</div>
			</form>
			</div>
			</div>
			<!-- END container -->
			</div>
			<!-- END CONTENT BODY -->
			</div>


    </div><!-- Common Libs JS -->
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
							     	<p id="content">
							     		<div id="container" style="width: 100%;">
											<canvas id="canvas"></canvas>
										</div>
							     	</p>
							      </div>
							      <div class="modal-footer">
							        <button type="button" class="btn btn-primary" id="close_btn">닫기</button>
							      </div>
							    </div>
							  </div>
							</div>


  </body>
  
      <script type="text/javascript"  language="javascript" src="resources/datatable/js/jquery.js"></script>
    <script type="text/javascript" language="javascript" src="resources/datatable/js/jquery.dataTables.min.js"></script>
    
    <script type="text/javascript" language="javascript" src="resources/js/admin.js"></script>
  	<script type="text/javascript" language="javascript" src="resources/js/form.js"></script>
  
  	<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.2/js/bootstrap.min.js" integrity="sha384-o+RDsa0aLu++PJvFqy8fFScvbHFLtbvScb8AjopnFD+iEQ7wo/CG0xlczd+2O/em" crossorigin="anonymous"></script>	
<!-- BEGIN CONTENT -->
	
  	<script type="text/javascript" language="javascript" 
		src="resources/js/test.js"></script>
  	
  	<script type="text/javascript"  language="javascript" src="resources/js/Chart.bundle.js"></script>
  	<script type="text/javascript"  language="javascript" src="resources/js/chartjs-plugin-labels.js"></script>
  	<script type="text/javascript"  language="javascript" src="resources/js/utils.js"></script>
  				
  	<script type="text/javascript">
  	
  	var barChartData;
  	
  	var ctx = document.getElementById('canvas').getContext('2d');
	
  		window.myBar = new Chart(ctx, {
			type: 'bar',
			data: barChartData,
			options: {
				
				plugins: {
				      labels: {
				        render: 'valueaddpercent',
				        position: 'labelwithinbar',
				      },
				},
			
				responsive: true,
				legend: {
					 labels: {
						   boxWidth: 0,
					},
					position: 'top',
				},
				
				title: {
					display: false,
					//text: 'Chart.js Bar Chart'//
				}, 
				scales: {
			        yAxes: [{
			        	ticks: {
		                	suggestedMin: 0,
		                	suggestedMax: 100
		                }
			        }], 
			    },
			    
			    tooltips: {
			    	enabled: false,
			    },
			}

		});
		
  	
  	
  	
  	
  	
  	var studentResultJson = JSON.parse('${jsonString}');
  	
  	
  	var $A = jQuery.noConflict();
  	
  	
  	function openScoreModal(stNum){
	  		
	  		$('#exampleModal').modal('show');
		
	  		
	  		var cnt=0;
	  		
	  		
	  		for(cnt=0;cnt<studentResultJson.length;cnt++){
	  			
	  			
	  			
	  			if(studentResultJson[cnt].stNum==stNum){
	  				
	  				
	  				
	  				barChartData = {
	  						labels :studentResultJson[cnt].labels,
	  						datasets:
	  							[{
	  								label:studentResultJson[cnt].datasets.label, 
	  								backgroundColor:studentResultJson[cnt].datasets.backgroundColor,
	  								borderColor:studentResultJson[cnt].datasets.borderColor,
	  								borderWidth: studentResultJson[cnt].datasets.borderWidth,
	  								data: studentResultJson[cnt].datasets.data,
	  							}]
	  							
	  				}
	  				
	  				
	  			}
	  			
	  		}
	  		
	  		
	  		window.myBar.data=barChartData;
	  			
	  		
	  		window.myBar.update();
			/*
			var barChartData = {
					labels: ['18.09.05-1회차', '18.09.12-1회차', '18.09.12-2회차', '18.09.19-1회차', '18.09.26-1회차'],
					datasets: [{
						label: 'test',
						backgroundColor: [
			                'rgba(255, 99, 132, 0.2)',
			                'rgba(54, 162, 235, 0.2)',
			                'rgba(255, 206, 86, 0.2)',
			                'rgba(75, 192, 192, 0.2)',
			                'rgba(153, 102, 255, 0.2)',
			                'rgba(255, 159, 64, 0.2)'
			            ],
			            borderColor: [
			                'rgba(255,99,132,1)',
			                'rgba(54, 162, 235, 1)',
			                'rgba(255, 206, 86, 1)',
			                'rgba(75, 192, 192, 1)',
			                'rgba(153, 102, 255, 1)',
			                'rgba(255, 159, 64, 1)'
			            ],
						borderWidth: 1,
						data: [
							'0',
							'0',
							'20',
							'90',
							'100'
						]
					}]
				};
				
	  		*/
	  		
	  		
	  };
	
	  
	  
	$A('#close_btn').on('click',function(){
			$('#exampleModal').modal('hide');
		
		})
	  
  	$A(document)
  			.ready(function () {
  				

  				
  				$A('#del_btn').on('click',function(){
  					var checked_radio;

					if ($J('input:checkbox[name=thIdx]').is(':checked')) {

						if (confirm("삭제 하시겠습니까?")) {

							var lists = [];
							$("input:checkbox[name=thIdx]:checked").each(
									function(i) { // jQuery로 for문 돌면서 check 된값
										// 배열에 담는다
										lists.push($(this).val());
									});

							var arrays = JSON.stringify(lists);
							jQuery
									.ajax({
										type : "POST",
										data : {
											arrays : arrays
										},
										dataType : "json",
										url : "deletetesthistoy",
										contentType : "application/x-www-form-urlencoded;charset=utf-8", // 한글
										// 깨짐
										// 방지
										cache : false,
										success : function(data) {
											alert("삭제 완료됨 ");
											window.location.reload();
										}
									});
						} 

					}else {
						alert('학생기록을 선택바랍니다.');
					}
  					
  			  	});
			});
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
					
				
					success:function(jsonData){
						
						var keys = jQuery.parseJSON(JSON.stringify(jsonData));
						if(keys.flag=='정상'){
							alert("업로드 완료");
							window.location.reload();
						}else if(keys.flag=='실패'){
							var html="<b> no 기준 :";
							for(var i=0;i<keys.jsonErrList.length;i++){
								if(i!=keys.jsonErrList.length-1){
									html +=keys.jsonErrList[i].woNo+",";
								}else{
									html +=keys.jsonErrList[i].woNo;
								}
								
							}
							html+="째 뜻에 문제가 있습니다. 해당 넘버의 뜻을 수정해서 재업로드 하시기 바랍니다. 다른 단어는 정상적으로 저장되었습니다.</b>";
							$('#errMsg').append(html);
							$('#excel_word').val('');
						}else{
							var html="<b> no 기준 :";
							for(var i=0;i<keys.jsonErrList.length;i++){
								if(i!=keys.jsonErrList.length-1){
									html +=keys.jsonErrList[i].woNo+",";
								}else{
									html +=keys.jsonErrList[i].woNo;
								}
								
							}
							html+="째  단어가 중복으로 들어갔습니다. 중복된 단어를 확인 후 다시 업로드 부탁드립니다.</b>";
							$('#errMsg').append(html);
							$('#excel_word').val('');
						}
				},
				error : function(request, status, error) {
					alert("업로드가 실패되었습니다. 중복된 넘버링 또는 word와 meaning의 빈값이 들어가 있는지 확인하고 업로드 부탁드립니다.");
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
  	</script>
  	
  	<script src="resources/js/libs.common.min.js"></script><!-- Common App Modules -->
    <script src="resources/js/modules/app.js"></script><!-- Page Libs JS --><!-- Page App Modules -->
  
</html>