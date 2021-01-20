<%@ page language="java" contentType="text/json; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ page import="javax.naming.Context"%>
<%@ page import="javax.naming.InitialContext"%>
<%@ page import="javax.naming.NamingException"%>
<%@ page import="javax.sql.DataSource"%>

<%@ page import="java.sql.Connection"%>
<%@ page import="java.sql.PreparedStatement"%>
<%@ page import="java.sql.ResultSet"%>
<%@ page import="java.sql.SQLException"%>

<%@ page import="org.json.simple.JSONArray"%>
<%@ page import="org.json.simple.JSONObject"%>

<%
	String strDong = request.getParameter("strDong");	

	Connection conn= null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	JSONArray jsonArray = new JSONArray();
	
	try{
		Context initCtx = new InitialContext();
		Context envCtx = (Context)initCtx.lookup("java:comp/env");
		DataSource dataSource = (DataSource)envCtx.lookup("jdbc/mariadb1");
	
		conn = dataSource.getConnection();
		
		String sql = "select seq, zipcode, sido, gugun, dong, ri, bunji from zipcode where dong like ?";
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, strDong + "%");
		
		rs = pstmt.executeQuery();
		
		while(rs.next()){
			
			JSONObject obj = new JSONObject();
			obj.put("seq", rs.getString("seq"));
			obj.put("zipcode", rs.getString("zipcode"));
			obj.put("sido", rs.getString("sido"));
			obj.put("gugun", rs.getString("gugun"));
			obj.put("dong", rs.getString("dong"));
			obj.put("ri", rs.getString("ri"));
			obj.put("bunji", rs.getString("bunji"));
		
			jsonArray.add( obj );
		}
		
	}catch(NamingException e){
		System.out.println("[에러]" + e.getMessage());
	}catch(SQLException e){
		System.out.println("[에러]" + e.getMessage());
	}finally{
		if(rs != null)rs.close();
		if(pstmt != null) pstmt.close();
		if(conn != null) conn.close();
	}
	
	out.println(jsonArray);
%>