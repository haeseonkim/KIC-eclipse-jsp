package model1;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class ZipcodeDAO {

	
	public ArrayList<ZipcodeTO> listDong(String strDong){
		
		String resource = "myBatisConfig.xml";
		
		InputStream is = null;
		SqlSession sqlSession = null;
		
		ArrayList<ZipcodeTO> lists = new ArrayList<ZipcodeTO>();
		
		try{
			is = Resources.getResourceAsStream( resource );
			SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
			System.out.println("설정 성공");
			
			sqlSession = sqlSessionFactory.openSession(true);
			
			lists = (ArrayList)sqlSession.selectList("mybatis2.select1", strDong+"%");
			
		}catch(IOException e){
			System.out.println("[에러]" + e.getMessage());
		}finally{
			if(is != null) try{is.close();}catch(IOException e){}
		}
		
		return lists;
	}
}
