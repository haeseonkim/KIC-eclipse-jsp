<%@page import="java.util.ArrayList"%>
<%@page import="model1.ZipcodeDAO"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ page import="java.io.IOException" %>
<%@ page import="java.io.InputStream" %>
<%@ page import="org.apache.ibatis.io.Resources" %>
<%@ page import="org.apache.ibatis.session.SqlSession" %>
<%@ page import="org.apache.ibatis.session.SqlSessionFactory" %>
<%@ page import="org.apache.ibatis.session.SqlSessionFactoryBuilder" %>

<%@ page import="model1.ZipcodeTO" %>

<%
	request.setCharacterEncoding("utf-8");
	
	String strDong = request.getParameter("dong");
	
	// if문 밖에서 출력되니까 if문 밖에 선언
	StringBuffer html = new StringBuffer();

	ZipcodeDAO dao = new ZipcodeDAO();
	
	ArrayList<ZipcodeTO> lists = dao.listDong(strDong);
	
	if(lists != null){
		html.append("<table width='800' border='1'>");
		for(ZipcodeTO to: lists){
			html.append("<tr>");
			html.append( "<td>"+ to.getZipcode() + "</td>");
			html.append("<td>"+ to.getSido() + "</td>");
			html.append("<td>"+ to.getGugun() + "</td>");
			html.append("<td>"+ to.getDong() + "</td>");
			html.append("<td>"+ to.getRi() + "</td>");
			html.append("<td>"+ to.getBunji() + "</td>");
			html.append("</tr>");
		}
		html.append("</table>");
	}
		
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
	const checkfrm = function() {
		if( document.frm.dong.value.trim() == '' ) {
			alert( '동이름을 입력해 주세요' );
			return;
		}
		document.frm.submit();
	};
</script>
</head>
<body>

<form action="zipcodeModel1.jsp" method="post" name="frm">
동이름 <input type="text" name="dong" />
<input type="button" value="동이름 검색" onclick="checkfrm()" />
</form>
<br /><br />
<%
	out.println(html);
%>
</body>
</html>