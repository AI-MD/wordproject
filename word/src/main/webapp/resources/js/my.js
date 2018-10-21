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

					$J('#my-test')
							.DataTable(
									{
										// Configure the drop down options.
										lengthMenu : [ [ 10, 25, 50, -1 ],
												[ '10', '25', '50', '모두' ] ],
										// Add to buttons the pageLength option.

										columnDefs : [ {
											"searchable" : false,
											"orderable" : false,
											"targets" : 0
										} ],
										order : [ [ 0, 'asc' ] ],
										
										columns : [ null,
											{"orderable" : true}, 
											{"orderable" : true}, 
											{"orderable" : true},
											{"orderable" : false},
											{"orderable" : false}
										],
										language : lang_kor, // or lang_eng
									});
				});

