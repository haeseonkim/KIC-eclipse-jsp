<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="javax.naming.Context" %>
<%@ page import="javax.naming.InitialContext" %>
<%@ page import="javax.naming.NamingException" %>
<%@ page import="javax.sql.DataSource" %>

<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.PreparedStatement" %>
<%@ page import="java.sql.SQLException" %>

<%
	
	Connection conn = null;
	PreparedStatement pstmt = null;
	
	
	try{
		
		Context iniCtx = new InitialContext();
		Context envCtx = (Context)iniCtx.lookup("java:comp/env");
		DataSource dataSource = (DataSource)envCtx.lookup("jdbc/mariadb2");
	
		conn = dataSource.getConnection();
		
		//insert into board1 values(0,'제목','이름','메일','1234','파일이름','내용',0,'000.000.000.000',now());
		String sql = "insert ab_board1 values(0, ?, ?, ?, ?, ?, ?, 0, ?, now())";
		pstmt = conn.prepareStatement(sql);
		for(int i=1; i <= 50; i++){
			pstmt.setString(1, "제주도가고싶다" + i);
			pstmt.setString(2, "글쓴이");
			pstmt.setString(3, "test@test.com");
			pstmt.setString(4, "1234");
			pstmt.setString(5, "KakaoTalk_20190130_021226773_162.jpg");
			pstmt.setString(6, "블라블라" + i);
			pstmt.setString(7, "000.000.000.000");
			
			pstmt.executeUpdate();
		}
		
	}catch(NamingException e){
		System.out.println("[에러] : "+e.getMessage());
	}catch(SQLException e){
		System.out.println("[에러] : "+e.getMessage());
	}finally{
		if(pstmt != null) pstmt.close();
		if(conn != null) conn.close();
	}
	
	out.println("<script type='text/javascript'>");
	out.println("alert('글쓰기에 성공했습니다.');");
	out.println("location.href='board_list1.jsp';");
	out.println("</script>");
	

%>








