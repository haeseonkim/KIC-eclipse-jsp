<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="model1.BoardTO" %>
<%@ page import="model1.BoardMyBatisDAO" %>

<%@page import="java.util.ArrayList"%>

<%
	request.setCharacterEncoding("utf-8");
	
	// -------------- write할 값을 to객체 안에 모두 set하기
	BoardTO to = new BoardTO();
	to.setSubject(request.getParameter("subject"));
	to.setWriter(request.getParameter("writer"));
	// 필수 입력 항목이 아닌 경우
	to.setMail("");
	
	if(!request.getParameter("mail1").equals("") && !request.getParameter("mail2").equals("")){
		to.setMail(request.getParameter("mail1") + "@" + request.getParameter("mail2"));
	}
	to.setPassword(request.getParameter("password"));
	to.setContent(request.getParameter("content"));
	
	to.setWip(request.getRemoteAddr());
	
	
	// -------------- to 객체를 가지고 실제 db와 실행시킨후 제대로 됐는지 flag값 받아오기
	BoardMyBatisDAO dao = new BoardMyBatisDAO();
	int flag = dao.boardWriteOk(to);
	
	out.println("<script type='text/javascript'>");
	if(flag == 0){
		out.println("alert('글쓰기에 성공했습니다.');");
		out.println("location.href='board_list1.jsp';");
	}else{
		out.println("alert('글쓰기에 실패했습니다.');");
		out.println("history.back();");
	}
	out.println("</script>");
	

%>








