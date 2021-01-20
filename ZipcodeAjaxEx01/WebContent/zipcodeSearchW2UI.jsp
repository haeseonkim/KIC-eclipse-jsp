<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="./css/w2ui-1.5.rc1.min.css" />
<style type="text/css">
	#wrap {
		margin: 0 auto;
		width: 960px;
		height: 500px;
	}
</style>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
<script type="text/javascript" src="./js/w2ui-1.5.rc1.min.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
		let datas = [];
		$('#wrap').w2grid({	// 이안에 function에서 this는 w2grid를 가리킴
			name: 'grid',
			show: {
				toolbar: true,
				footer: true,
				lineNumbers: true,
				toolbarReload: true,
				toolbarSearch: true
			},
			columns: [
				{ field: 'zipcode', caption: '우편번호', size: '10%' },
				{ field: 'sido', caption: '시도', size: '20%' },
				{ field: 'gugun', caption: '구군', size: '20%' },
				{ field: 'dong', caption: '동', size: '15%' },
				{ field: 'ri', caption: '리', size: '15%' },
				{ field: 'bunji', caption: '번지', size: '20%' }
			],
			reloads: datas,
			onSearch: function(target, e){
				console.log('onSearch 클릭');
				
				// ajax
				// ajax 안에서 this하면 그건 grid가 아니고 ajax 객체가된다.
				// 그래서 아래처럼 변수로 정의하고 들어가야한다.
				const grid = this;
				$.ajax({	// 이안에 function에서 this는 ajax하는 url등을 가지는 객체가됨
					
					url:"./data/json.jsp",
					data: {
						'strDong' : e.searchData[0].value
					},
					type: 'get',
					dataType: 'json', 
					success: function(json){
						console.log(json);
						console.log(this);
						// 화면 지워주기
						grid.clear();
						
						$.each(json, function(key, value){
							grid.add({
								recid: grid.grid+1,
								zipcode: value.zipcode,
								sido: value.sido,
								gugun: value.gugun,
								dong: value.dong,
								ri: value.ri,
								bunji: value.bunji
							})
						});
					},
					error: function(){
						alert("에러페이지");
					}
				});
				
			}
		});
	});
</script>
</head>
<body>

<div id="wrap"></div>

</body>
</html>