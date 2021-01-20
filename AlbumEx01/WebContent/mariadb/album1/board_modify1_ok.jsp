<%@page import="com.oreilly.servlet.MultipartRequest"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="model1.BoardTO" %>
<%@ page import="model1.BoardDAO" %>

<%@ page import="com.oreilly.servlet.multipart.DefaultFileRenamePolicy"  %>
<%@ page import="com.oreilly.servlet.MultipartRequest"  %>
<%@page import="java.io.File"%>


<%
	
	String uploadPath = "C:/Java/java/jsp-workspace/AlbumEx01/WebContent/upload";
	int maxFileSize = 1024 * 1024 * 2;
	String encType = "utf-8";
	
	MultipartRequest multi 
		= new MultipartRequest(request, uploadPath, maxFileSize, encType, new DefaultFileRenamePolicy());

	String cpage = multi.getParameter("cpage");
	String seq = multi.getParameter("seq");
	
	BoardTO to = new BoardTO();
	to.setSeq(multi.getParameter("seq"));
	to.setPassword(multi.getParameter("password"));
	
	to.setSubject(multi.getParameter("subject"));
	// 필수 입력 항목이 아닌 경우
	to.setMail("");
	if(!multi.getParameter("mail1").equals("") && !multi.getParameter("mail2").equals("")){
		to.setMail(multi.getParameter("mail1") + "@" + multi.getParameter("mail2"));
	}
	to.setContent(multi.getParameter("content"));
	
	to.setFilename(multi.getFilesystemName("upload"));
	
	BoardDAO dao = new BoardDAO();
	int flag = dao.boardModifyOk(to);
	
	out.println("<script type='text/javascript'>");
	if(flag == 0){
		out.println("alert('글수정에 성공했습니다.');");
		out.println("location.href='board_view1.jsp?cpage="+ cpage +"&seq="+seq+"';");
	}else if(flag == 1){
		out.println("alert('비밀번호가 잘못되었습니다.');");
		out.println("history.back();");
	}else{
		out.println("alert('글수정에 실패했습니다.');");
		out.println("history.back();");
	}
	out.println("</script>");
%>
