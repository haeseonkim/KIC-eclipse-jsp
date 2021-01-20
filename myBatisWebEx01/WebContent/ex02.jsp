<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ page import="java.io.IOException" %>
<%@ page import="java.io.InputStream" %>
<%@ page import="org.apache.ibatis.io.Resources" %>
<%@ page import="org.apache.ibatis.session.SqlSession" %>
<%@ page import="org.apache.ibatis.session.SqlSessionFactory" %>
<%@ page import="org.apache.ibatis.session.SqlSessionFactoryBuilder" %>

<%@ page import="model1.DeptTO" %>



<%
	String resource = "myBatisConfig.xml";

	InputStream is = null;
	SqlSession sqlSession = null;
	
	try{
		is = Resources.getResourceAsStream( resource );
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
		System.out.println("설정 성공");
		
		sqlSession = sqlSessionFactory.openSession(true);
		
		DeptTO to = (DeptTO)sqlSession.selectOne("select1");
		
		System.out.println(to.getDeptno());
		System.out.println(to.getDname());
		
	}catch(IOException e){
		System.out.println("[에러]" + e.getMessage());
	}finally{
		if(is != null) is.close();
	}
%>