<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
</style>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$('#btn').on('click', () => {
			console.log($('#dong').val());
			
			$.ajax({
				url:"./data/xml.jsp",
				data: {
					'strDong' : $('#dong').val()
				},
				type: 'get',
				dataType: 'text', 
				success: function(data){
					var seqs = new Array();
					var zipcodes = new Array();
					var sidos = new Array();
					var guguns = new Array();
					var dongs = new Array();
					var ris = new Array();
					var bunjis = new Array();
					
					$(data).find( 'seq' ).each( function(){
						seqs.push($(this).html());
					});
					$(data).find( 'zipcode' ).each( function(){
						zipcodes.push($(this).html());
					});
					$(data).find( 'sido' ).each( function(){
						sidos.push($(this).html());
					});
					$(data).find( 'gugun' ).each( function(){
						guguns.push($(this).html());
					});
					$(data).find( 'dong' ).each( function(){
						dongs.push($(this).html());
					});
					$(data).find( 'ri' ).each( function(){
						ris.push($(this).html());
					});
					$(data).find( 'bunji' ).each( function(){
						bunjis.push($(this).html());
					});
					
					
					let result = '<table width="800" border="1">';
					for(let i=0; i<seqs.length; i++){
						let seqValue = seqs[i];
						let zipcodeValue = zipcodes[i];
						let sidoValue = sidos[i];
						let gugunValue = guguns[i];
						let dongValue = dongs[i];
						let riValue = ris[i];
						let bunjiValue = bunjis[i];
						
						result += '<tr>';
						result += '<td>' + seqValue +'</td>';
						result += '<td>' + zipcodeValue +'</td>';
						result += '<td>' + sidoValue +'</td>';
						result += '<td>' + gugunValue +'</td>';
						result += '<td>' + dongValue +'</td>';
						result += '<td>' + riValue +'</td>';
						result += '<td>' + bunjiValue +'</td>';
						result += '</tr>';
						
					}
					result += '</table>';
					
					$('#result').append(result);
				},
				error: function(){
					alert("에러페이지");
				}
			});
						
		});
	});
</script>
</head>
<body>
	<form>
		동이름: <input type="text" id="dong" size="30" /> <input type="button"
			id="btn" value="우편번호 검색" />
	</form>
	<hr />
	<br />
	<div id="result"></div>

</body>
</html>