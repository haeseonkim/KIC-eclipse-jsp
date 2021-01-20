package model2;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import model1.ZipcodeDAO;
import model1.ZipcodeTO;

public class ZipcodeOkAction implements ActionModel {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {

		String resource = "myBatisConfig.xml";

		InputStream is = null;
		SqlSession sqlSession = null;
	
		try {
			
			request.setCharacterEncoding( "utf-8" );
			
			String strDong = request.getParameter("dong");
			
			is = Resources.getResourceAsStream( resource );
			SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
			System.out.println("설정 성공");
				
			sqlSession = sqlSessionFactory.openSession(true);
				
			List<ZipcodeTO> lists = sqlSession.selectList("mybatis2.select1", strDong+"%");
			
			request.setAttribute("lists",lists);
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(is != null) try{is.close();} catch(IOException e) {}
		}
				
	}

}
