<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
	window.onload =()=> {
		document.getElementById('btn').onclick = function(){
			const strDong = document.getElementById('dong').value.trim();
			alert(strDong);
			
			const request = new XMLHttpRequest();
			request.onreadystatechange = () =>{
				console.log('응답', request.readyState);
				if(request.readyState == 4){
					if(request.status==200){
						
						const data = request.responseText.trim();
						console.log(data);
						
						const jsonData = JSON.parse(request.responseText);
						
						let result = '<table width="800" border="1">';
						for(let i=0; i<jsonData.length; i++){
							result += '<tr>';
							result += '<td>' + jsonData[i].seq +'</td>';
							result += '<td>' + jsonData[i].zipcode +'</td>';
							result += '<td>' + jsonData[i].sido +'</td>';
							result += '<td>' + jsonData[i].gugun +'</td>';
							result += '<td>' + jsonData[i].dong +'</td>';
							result += '<td>' + jsonData[i].ri +'</td>';
							result += '<td>' + jsonData[i].bunji +'</td>';
							result += '</tr>';
						}
						result += '</table>';
						
						document.getElementById('result').innerHTML = '';
						document.getElementById('result').innerHTML = result;
						
					}else{
						alert("에러페이지");
					}
				}
			};
		
			request.open('GET','./data/json.jsp?strDong=' + strDong,true); 
			request.send();
			
		};
	};
</script>
</head>
<body>

<form>
동이름: <input type="text" id="dong" size="30" />
<input type="button" id="btn" value="우편번호 검색" />
</form>
<hr /><br />
<div id="result"></div>
</body>
</html>