var $J = jQuery.noConflict();
$J(document)
		.ready(
				function() {
					// Korean
					var lang_kor = {
						"decimal" : "",
						"emptyTable" : "데이터가 없습니다.",
						"info" : "_START_ - _END_ (총 _TOTAL_ 개)",
						"infoEmpty" : "0개",
						"infoFiltered" : "(전체 _MAX_ 개 중 검색결과)",
						"infoPostFix" : "",
						"thousands" : ",",
						"lengthMenu" : "_MENU_ 개씩 보기",
						"loadingRecords" : "로딩중...",
						"processing" : "처리중...",
						"search" : "검색 : ",
						"zeroRecords" : "검색된 데이터가 없습니다.",
						"paginate" : {
							"first" : "첫 페이지",
							"last" : "마지막 페이지",
							"next" : "다음",
							"previous" : "이전"
						},
						"aria" : {
							"sortAscending" : " :  오름차순 정렬",
							"sortDescending" : " :  내림차순 정렬"
						}
					};

					$J('#student-test')
							.DataTable(
									{

										dom : 'lfrtip<"toolbar">',

										initComplete : function() {
											$J("div.toolbar")
													.html(
															'<br/><button type="button"  class="btn btn-warning btn-sm"  id="del_btn">삭제 </button>');
										
										},

										// Configure the drop down options.
										lengthMenu : [ [ 10, 25, 50, -1 ],
												[ '10', '25', '50', '모두' ] ],
										// Add to buttons the pageLength option.

										columnDefs : [ {
											"searchable" : false,
											"orderable" : false,
											"targets" : 0
										} ],
										order : [ [ 0, 'desc' ] ],

										columns : [ null, {
											"orderable" : false
										}, {
											"orderable" : false
										},{
											"orderable" : true
										}, {
											"orderable" : true
										}, {
											"orderable" : true
										},{
											"orderable" : true
										},{
											"orderable" : false
										}, {
											"orderable" : false
										}, {
											"orderable" : false
										}

										],
										language : lang_kor, // or lang_eng

									});
				});




		


		$J(document).on(
				"click",
				'#btn_groupInsert',
				function(event) {

					if ($("#sgName").val() == null || $("#sgName").val() == "") {
						return;
					}

					jQuery
							.ajax({
								async : true,
								type : "get",
								url : "groupInsert",
								dataType : "text",
								data : "sgName=" + $("#sgName").val(),
								success : function(jsonData) {
									var keys = JSON.parse(jsonData);

									var length = keys.jsonsgList.length;
									var html = "";
									for (var i = 0; i < length; i++) {
										html += "<li><label for ='sg_"
												+ keys.jsonsgList[i].sgIdx
												+ "'>"
												+ "<input type='radio' class='sgIdx' name='sgIdx' id='sg_"
												+ keys.jsonsgList[i].sgIdx
												+ "' value='"
												+ keys.jsonsgList[i].sgIdx
												+ "'/>"
												+ keys.jsonsgList[i].sgName
												+ "</label></li>";
									}
									$J("#grouplist").html(html);
								},
								error : function(request, status, error) {
									alert("code:" + request.status + "\n"
											+ "message:" + request.responseText
											+ "\n" + "error:" + error);
								}
							});
				});

$J(document)
		.on(
				"change",
				'.sgIdx',
				function() {

					var radioValue = $J('input:radio[name=sgIdx]:checked')
							.val();
					var text = $J("label[for='sg_" + radioValue + "']").text();

					$J("#group")
							.html(
									"<p> 그룹명 :"
											+ text
											+ "</p> <br /> <center> <Button id='btn_studentTrans' type='button' class='btn btn-primary btn-sm'>빼기</Button></center>");
					jQuery
							.ajax({
								async : true,
								type : "get",
								url : "groupStudentlist",
								dataType : "text",
								data : "sgIdx=" + radioValue,
								success : function(jsonData) {
									var keys = JSON.parse(jsonData);
									var length = keys.jsonsgList.length;
									var html = "";
									html += "<table>";
									for (var i = 0; i < length; i++) {
										html += "<tr><td><label for ='st_"
												+ keys.jsonsgList[i].stIdx
												+ "'><input type='checkbox' name='stdIdx' id='st_"
												+ keys.jsonsgList[i].stIdx
												+ "' value='"
												+ keys.jsonsgList[i].stIdx
												+ "'/>"
												+ keys.jsonsgList[i].stName
												+ ","
												+ keys.jsonsgList[i].stNum
												+ "</td></tr>";
									}
									html += "</table>";
									$J("#group").append(html);
								},
								error : function(request, status, error) {
									alert("code:" + request.status + "\n"
											+ "message:" + request.responseText
											+ "\n" + "error:" + error);
								}
							});

				});
$J(document)
		.on(
				"click",
				'#btn_studentDelete',
				function(event) {
					var checked_radio;
					var text;
					if ($J('input:radio[name=sgIdx]').is(':checked')) {
						checked_radio = $J('input:radio[name=sgIdx]:checked')
								.val(); // 선택된 radio의 value 가져오기

						text = $J("label[for='sg_" + checked_radio + "']")
								.text();
					}
					text = 1;

					if ($J('input:checkbox[name=stIdx]').is(':checked')) {

						if (confirm("삭제 하시겠습니까?")) {

							var lists = [];
							$("input:checkbox[name=stIdx]:checked").each(
									function(i) { // jQuery로 for문 돌면서 check 된값
										// 배열에 담는다
										lists.push($(this).val());
									});

							var arrays = JSON.stringify(lists);
							jQuery
									.ajax({
										type : "POST",
										data : {
											flag : "delete",
											sgIdx : 1,
											arrays : arrays
										},
										dataType : "json",
										url : "updateStudentGroup",
										contentType : "application/x-www-form-urlencoded;charset=utf-8", // 한글
										// 깨짐
										// 방지
										cache : false,
										success : function(jsonData) {

											var slength = jsonData.sjsonlist.length;
											var sglength = jsonData.sgjsonlist.length;

											var shtml = "", sghtml = "";

											if (text != 1) {
												sghtml += "<table>";
												for (var i = 0; i < sglength; i++) {
													sghtml += "<tr><td><label for ='st_"
															+ jsonData.sgjsonlist[i].stIdx
															+ "'><input type='checkbox' name='stdIdx' id='st_"
															+ jsonData.sgjsonlist[i].stIdx
															+ "' value='"
															+ jsonData.sgjsonlist[i].stIdx
															+ "'/>"
															+ jsonData.sgjsonlist[i].stName
															+ ","
															+ jsonData.sgjsonlist[i].stNum
															+ "</td></tr>";
												}
												sghtml += "</table>";

												$J("#group")
														.html(
																"<p> 그룹명 :"
																		+ text
																		+ "</p> <br /> <center> <Button id='btn_studentTrans' type='button' class='btn btn-primary btn-sm'>빼기</Button> </center>");
												$J("#group").append(sghtml);
											}

											shtml += "<table>";
											for (var i = 0; i < slength; i++) {
												shtml += "<tr><td><label for ='st_"
														+ jsonData.sjsonlist[i].stIdx
														+ "'><input type='checkbox' name='stIdx' id='st_"
														+ jsonData.sjsonlist[i].stIdx
														+ "' value='"
														+ jsonData.sjsonlist[i].stIdx
														+ "'/>"
														+ jsonData.sjsonlist[i].stName
														+ ","
														+ jsonData.sjsonlist[i].stNum
														+ "</td></tr>";
											}
											shtml += "</table>";

											$J("#student")
													.html(
															"<p> 학생명단</p> <br /> <center> <Button id='btn_studentInsert' type='button' class='btn btn-primary btn-sm'>넣기</Button> <Button id='btn_studentDelete' type='button' class='btn btn-primary btn-sm'>삭제</Button></center>");
											$J("#student").append(shtml);

										}
									});
						} 

					}else {
						alert('학생을 선택바랍니다.');
					}

				});

$J(document)
		.on(
				"click",
				'#btn_studentInsert',
				function(event) {
					var checked_radio;
					if ($J('input:radio[name=sgIdx]').is(':checked')) {
						checked_radio = $J('input:radio[name=sgIdx]:checked')
								.val(); // 선택된 radio의 value 가져오기

						var text = $J("label[for='sg_" + checked_radio + "']")
								.text();

						if ($J('input:checkbox[name=stIdx]').is(':checked')) {
							var lists = [];
							$("input:checkbox[name=stIdx]:checked").each(
									function(i) { // jQuery로 for문 돌면서 check 된값
										// 배열에 담는다
										lists.push($(this).val());
									});

							var arrays = JSON.stringify(lists);
							jQuery
									.ajax({
										type : "POST",
										data : {
											flag : "insert",
											sgIdx : checked_radio,
											arrays : arrays
										},
										dataType : "json",
										url : "updateStudentGroup",
										contentType : "application/x-www-form-urlencoded;charset=utf-8", // 한글
										// 깨짐
										// 방지
										cache : false,
										success : function(jsonData) {

											var slength = jsonData.sjsonlist.length;
											var sglength = jsonData.sgjsonlist.length;

											var shtml = "", sghtml = "";

											sghtml += "<table>";
											for (var i = 0; i < sglength; i++) {
												sghtml += "<tr><td><label for ='st_"
														+ jsonData.sgjsonlist[i].stIdx
														+ "'><input type='checkbox' name='stdIdx' id='st_"
														+ jsonData.sgjsonlist[i].stIdx
														+ "' value='"
														+ jsonData.sgjsonlist[i].stIdx
														+ "'/>"
														+ jsonData.sgjsonlist[i].stName
														+ ","
														+ jsonData.sgjsonlist[i].stNum
														+ "</td></tr>";
											}
											sghtml += "</table>";

											$J("#group")
													.html(
															"<p> 그룹명 :"
																	+ text
																	+ "</p> <br /> <center> <Button id='btn_studentTrans' type='button' class='btn btn-primary btn-sm'>빼기</Button> 	</center>");
											$J("#group").append(sghtml);

											shtml += "<table>";
											for (var i = 0; i < slength; i++) {
												shtml += "<tr><td><label for ='st_"
														+ jsonData.sjsonlist[i].stIdx
														+ "'><input type='checkbox' name='stIdx' id='st_"
														+ jsonData.sjsonlist[i].stIdx
														+ "' value='"
														+ jsonData.sjsonlist[i].stIdx
														+ "'/>"
														+ jsonData.sjsonlist[i].stName
														+ ","
														+ jsonData.sjsonlist[i].stNum
														+ "</td></tr>";
											}
											shtml += "</table>";

											$J("#student")
													.html(
															"<p> 학생명단</p> <br /> <center> <Button id='btn_studentInsert' type='button' class='btn btn-primary btn-sm'>넣기</Button> <Button id='btn_studentDelete' type='button' class='btn btn-primary btn-sm'>삭제</Button>	</center>");
											$J("#student").append(shtml);

										}
									});
						} else {
							alert('학생을 선택바랍니다.');
						}
					} else {
						alert('그룹명을 선택바랍니다.');
						return;
					}
				});

$J(document)
		.on(
				"click",
				'#btn_studentTrans',
				function(event) {

					var checked_radio;
					if ($J('input:radio[name=sgIdx]').is(':checked')) {
						checked_radio = $J('input:radio[name=sgIdx]:checked')
								.val(); // 선택된 radio의 value 가져오기

						var text = $J("label[for='sg_" + checked_radio + "']")
								.text();

						if ($J('input:checkbox[name=stdIdx]').is(':checked')) {

							var lists = [];
							$("input:checkbox[name=stdIdx]:checked").each(
									function(i) { // jQuery로 for문 돌면서 check 된값
										// 배열에 담는다
										lists.push($(this).val());
									});

							var arrays = JSON.stringify(lists);
							jQuery
									.ajax({
										type : "POST",
										data : {
											flag : "trans",
											sgIdx : checked_radio,
											arrays : arrays

										},
										dataType : "json",
										url : "updateStudentGroup",
										contentType : "application/x-www-form-urlencoded;charset=utf-8", //한글 깨짐 방지
										cache : false,
										success : function(jsonData) {

											var slength = jsonData.sjsonlist.length;
											var sglength = jsonData.sgjsonlist.length;

											var shtml = "", sghtml = "";

											sghtml += "<table>";
											for (var i = 0; i < sglength; i++) {
												sghtml += "<tr><td><label for ='st_"
														+ jsonData.sgjsonlist[i].stIdx
														+ "'><input type='checkbox' name='stdIdx' id='st_"
														+ jsonData.sgjsonlist[i].stIdx
														+ "' value='"
														+ jsonData.sgjsonlist[i].stIdx
														+ "'/>"
														+ jsonData.sgjsonlist[i].stName
														+ ","
														+ jsonData.sgjsonlist[i].stNum
														+ "</td></tr>";
											}
											sghtml += "</table>";

											$J("#group")
													.html(
															"<p> 그룹명 :"
																	+ text
																	+ "</p> <br /> <center> <Button id='btn_studentTrans' type='button' class='btn btn-primary btn-sm'>빼기</Button></center>");
											$J("#group").append(sghtml);

											shtml += "<table>";
											for (var i = 0; i < slength; i++) {
												shtml += "<tr><td><label for ='st_"
														+ jsonData.sjsonlist[i].stIdx
														+ "'><input type='checkbox' name='stIdx' id='st_"
														+ jsonData.sjsonlist[i].stIdx
														+ "' value='"
														+ jsonData.sjsonlist[i].stIdx
														+ "'/>"
														+ jsonData.sjsonlist[i].stName
														+ ","
														+ jsonData.sjsonlist[i].stNum
														+ "</td></tr>";
											}
											shtml += "</table>";

											$J("#student")
													.html(
															"<p> 학생명단</p> <br /> <center> <Button id='btn_studentInsert' type='button' class='btn btn-primary btn-sm'>넣기</Button> <Button id='btn_studentDelete' type='button' class='btn btn-primary btn-sm'>삭제</Button>	</center>");
											$J("#student").append(shtml);

										}
									});
						} else {
							alert('학생을 선택바랍니다.');
						}
					} else {
						alert('그룹명을 선택바랍니다.');
						return;
					}
				});