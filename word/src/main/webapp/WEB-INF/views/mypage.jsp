<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ page session="false"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="robots" content="noindex">

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

<!--
    <link href="resources/datatable/css/jquery.dataTables.css" rel="stylesheet">
    <link href="resources/datatable/css/dataTables.bootstrap.css" rel="stylesheet">
 	  -->

<link rel="stylesheet" type="text/css"
	href="resources/datatable/css/jquery.dataTables.min.css">

<link
	href="https://cdn.datatables.net/buttons/1.5.1/css/buttons.dataTables.min.css"
	type="text/css" rel="stylesheet">


<style>
div.toolbar {
	float: right;
}

ul {
	list-style: none;
	padding-left: 0px;
}
</style>

</head>
<body>
	<div class="st-wrapper">
		<div class="bodytext" style="margin-top: 15px">
			<span>영어학습프로그램</span> <a href="logout" type="button"
				class="btn btn-warning btn-sm">로그아웃</a>
		</div>
		<br /> <br />
		<div class="container">
			<!-- /.col-lg-12 -->
			<div class="col-lg-12">
				<div class="panel panel-default">
					<div class="panel-heading">나의페이지</div>
					<!-- /.panel-heading -->
					<div class="panel-body">
						<!-- Nav tabs -->
						<ul class="nav nav-pills">
							<li class="active"><a href="#st-pills" data-toggle="tab">학생
									시험이력</a>
							</li>
							<li ><a href="#sg-pills" data-toggle="tab">
									오답노트</a>
							</li>
							
						</ul>
						
						<!-- Tab panes -->
						<div class="tab-content">
							<div class="tab-pane fade in active" id="st-pills">
								<table width="100%"
									class="table table-striped table-bordered table-hover "
									id="my-test">
									<thead>
										<tr>
											<th > NO</th>
											<th >시험 유형</th>
											<th >시험 본 유형</th>
											<th >시험 본 횟수</th>
											<th >시험결과  (맞은문항 / 전체문항)</th>
											<th >시험날짜</th>
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
			                                	<td>${resultItem.thTestNum}</td>
			                                	<td>${resultItem.totalCount-resultItem.wrongCount}/${resultItem.totalCount}</td>
			                                	<td><fmt:formatDate value="${resultItem.thTestDate}" pattern="yyyy-MM-dd"/></td>
			                                	
		                                	</tr>
		                                </c:forEach>
		                                </tbody>
								</table>
							</div>
							
							
							<div class="tab-pane fade" id="sg-pills">
							<div style="float:right;">
								
								<Button id="btn_wrongWordDelete" type="button" class="btn btn-primary btn-sm">제거</Button>
								<Button id="btn_wordTest" type="button" class="btn btn-primary btn-sm">시험</Button>	
							
								<form id="wrongwordForm" method="post" action="wrongworddelete">
									<input type="hidden" id="wrongnotedatalist" name="wrongnotedatalist"/>
								</form>
							</div>
							
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
							     		<form role="form"  id="maketestForm" action="makeTest" method="post">
			                            <fieldset>
			                                <span>생성 조건 </span>
			                                <div class="form-group">
			             					</div>
			                                
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
			                            </fieldset>
			                            <input type="hidden" name="all_wrongIdx" value="" id="all_wrong"/>
			                            <input type="hidden" name="testtype" value="wrong"/>
			                        </form>
							     	</p>
							      </div>
							      <div class="modal-footer">
							        <button type="button" class="btn btn-primary" id="make_test">생성</button>
							      </div>
							    </div>
							  </div>
							</div>
			
							<table width="100%"
									class="table table-striped table-bordered table-hover "
									id="my-test2">
									<thead>
										<tr>
											<th >NO</th>
											<th >유형</th>
											<th >날짜</th>
											<th >오답</th>
											<th >비고</th>
										</tr>
									</thead>
									<tbody>
										
		                                <c:forEach var="resultItem" items="${reslist}" varStatus="prestatus">
		                     				<tr>
			                                	<td>${prestatus.count} 
			                                		<input type="hidden" id="${prestatus.count}" value="${resultItem.resIdx}">
			                                	 </td>
			                                	<c:if test="${resultItem.thWflag eq false}">
			                                		<td> 기본 </td>
			               	                 	</c:if>
			                                	<c:if test="${resultItem.thWflag eq true}">
			                  	              		<td> 오답 </td>
			                                	</c:if>
			                                	<td><fmt:formatDate value="${resultItem.thTestDate}" pattern="yyyy-MM-dd"/></td>
		                                		<td >
		                                		 	<c:forEach var="wrongItem" items="${resultItem.wrongwordList}" varStatus="status">
		                                				
		                                					<input type="hidden"  value="${wrongItem.position}" id="${prestatus.count}_${wrongItem.woIdx}"/>
		                                						
		                                						<input type="checkbox" id="${wrongItem.woWord}_${prestatus.count}_${wrongItem.position}_${resultItem.resIdx}_${resultItem.positioncount}_${wrongItem.woIdx}" class="select_${prestatus.count}_input"
		                                							name="select_${prestatus.count}_name"
													               value="${wrongItem.woIdx}" />
													       		 <label for="${wrongItem.woWord}_${prestatus.count}_${wrongItem.position}_${resultItem.resIdx}_${resultItem.positioncount}_${wrongItem.woIdx}"> ${wrongItem.woWord}</label>
		                                			</c:forEach>
		                                		</td>
		                                		<td>
		                                		<input type="checkbox" id="${prestatus.count}_all" name="wrongword_${prestatus.count}"
													               value="${prestatus.count}" />
													        <label for="${prestatus.count}_all"> 모두</label>
													        
		                                		<input type="hidden"  value="${resultItem.positioncount}" id="${prestatus.count}_positioncount"/>
		                                		</td>
		                                	</tr>
		                                	
		                                	
		                                </c:forEach>
		                                </tbody>
								</table>
							</div>
						</div>
						<!-- /.panel -->
					</div>
					<!-- /.col-lg-6 -->

				</div>
			</div>
		</div>


		
		<center>
			<a href="word" type="button" class="btn btn-primary btn-sm">단어보기</a>
			<a href="./" type="button" class="btn btn-primary btn-sm">초기화면</a>
		</center>

	</div>
	<!-- Common Libs JS -->

	<script type="text/javascript" language="javascript"
		src="resources/datatable/js/jquery.js"></script>
	<script type="text/javascript" language="javascript"
		src="resources/datatable/js/jquery.dataTables.min.js"></script>
	<script type="text/javascript" language="javascript" 
		src="resources/js/test.js"></script>
	<script type="text/javascript" language="javascript"
		src="resources/js/form.js"></script>
	<script type="text/javascript" language="javascript" 
		src="resources/js/my.js"></script>
	<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.2/js/bootstrap.min.js" integrity="sha384-o+RDsa0aLu++PJvFqy8fFScvbHFLtbvScb8AjopnFD+iEQ7wo/CG0xlczd+2O/em" crossorigin="anonymous"></script>	
	  
	<script type="text/javascript">
	 
	function onlyNumber(obj) {
	  	    $(obj).keyup(function(){
	  	         $(this).val($(this).val().replace(/[^0-9]/g,""));
	  	    }); 
	  	}
	var $f = jQuery.noConflict();
	 $f(document).ready(function() {
		 
		 
		
		 /*
		 	체크박스 컨트롤 함수 작성 
		 	비고에 있는 체크박스 클릭시 모두선택 
		 */
		 var list='${reslist}';
		 for(var i =1;i<list.length;i++){
			 $("#"+i+"_all").click(function(i){
				 	
			        var chk = $(this).is(":checked");//.attr('checked');
			        if(chk) $(".select_"+this.value+"_input").prop('checked', true);
			        else  $(".select_"+this.value+"_input").prop('checked', false);
			    });
			 
		 }
		
		
		 $f("#btn_wrongWordDelete").on("click",function(){
			 /*
			 	2중 for문으로 시험에 따라 인덱스와 포지션값 그리고 포지션 수 넘길 수 있도록 
			 */
			 var wrongnotedatalist = [];
			
			 
			 	for(var i =1;i<list.length;i++){
			 		var j=i;
					$('input:checkbox[name="select_'+i+'_name"]').each(function(j){
						
						
						if(this.checked){//checked 처리된 항목의 값
							
							
							
							var flag= true;
							for(var i=0;i<wrongnotedatalist.length;i++){
								if(wrongnotedatalist[i]==this.id){
									flag=false;
								}
							}
							if(flag){
								wrongnotedatalist.push(this.id);
								
								
								//console.log('id값 '+this.id);
								//console.log('단어의 인덱스 '+this.value);
								//console.log("포지션"+$f("#"+j+"_"+this.value).val());
								//console.log("시험인덱스"+$f("#"+j).val());
								//console.log("총 포지션"+$f("#"+j+"_positioncount").val());
								
							}
							
				      }
					})
				}
				formcheck_delete(wrongnotedatalist)
		 })
		 
		 $f("#btn_wordTest").on("click", function(){
			
			$('#exampleModal').modal('show');
			
			$f("#make_test").on("click", function(){
				 
				var wlists = [];
					
				for(var i =1;i<list.length;i++){
					$('input:checkbox[name="select_'+i+'_name"]').each(function(){
						if(this.checked){//checked 처리된 항목의 값
							
				        	var flag= true;
							for(var i=0;i<wlists.length;i++){
								if(wlists[i]==this.value){
									flag=false;
								}
							}
							if(flag){
								wlists.push(this.value);
							}
							
							
							
						
							
				      }
					})
				}
				formcheck(wlists.length,wlists);
				
				
		});
			
			
			
	});
		 function formcheck_delete(wrongnotedatalist){
		  		
		  $("#wrongnotedatalist").val(wrongnotedatalist);
	
		  $("#wrongwordForm").submit();
		  		
		 }
		 
		 function formcheck(testsize,wlists){
		  		
		  		
		  		var cnt=$("#cnt").val();
		  		if(testsize<cnt){
		  			return;
		  		}else if(cnt>50){
		  			alert("최대  문항수는 50문제까지 입력가능합니다.");
		  			return;
		  		}else if(cnt==''){
		  			alert("문항수를 입력하시기 바랍니다.");
		  			return;
		  		}
		  		else{
		  			$("#all_wrong").val(wlists);
		  			
		  			$("#maketestForm").submit();
		  		}
		 }
			
		 });
	</script>
	
	<script src="resources/js/libs.common.min.js"></script>
	<!-- Common App Modules -->
	<script src="resources/js/modules/app.js"></script>
	<!-- Page Libs JS -->
	<!-- Page App Modules -->
</body>
</html>