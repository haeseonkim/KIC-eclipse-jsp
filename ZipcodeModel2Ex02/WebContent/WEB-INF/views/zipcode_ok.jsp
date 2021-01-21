<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ page import="model1.ZipcodeTO"%>
<%@ page import="java.util.ArrayList"%>


<%
	StringBuffer html = new StringBuffer();	
	ArrayList<ZipcodeTO> lists = (ArrayList)request.getAttribute("lists");
	//		
	html.append( "<table width='600' border='1'>" );
	for(ZipcodeTO to : lists) {
		html.append( "<tr>" );
		html.append( "	<td>" + to.getZipcode() + "</td>" );
		html.append( "	<td>" + to.getSido() + "</td>" );
		html.append( "	<td>" + to.getGugun() + "</td>" );
		html.append( "	<td>" + to.getDong() + "</td>" );
		html.append( "	<td>" + to.getRi() + "</td>" );
		html.append( "	<td>" + to.getBunji() + "</td>" );
		html.append( "</tr>" );
	}
	html.append( "</table>" );
	
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<%= html %>

</body>
</html>