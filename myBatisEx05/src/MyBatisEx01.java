import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import config.SqlMapperInter;
import model1.DeptTO;

public class MyBatisEx01 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String resource = "myBatisConfig.xml";
		
		InputStream is = null;
		SqlSession sqlSession = null;
		
		try {
			is = Resources.getResourceAsStream( resource );
			SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
			
			//
			sqlSession = sqlSessionFactory.openSession(true);
			
			// mapper interface 호출
			sqlSession.getConfiguration().addMapper( SqlMapperInter.class);
			
			SqlMapperInter mapper = (SqlMapperInter)sqlSession.getMapper(SqlMapperInter.class);
			
			DeptTO to = mapper.selectByDeptno("20");
			System.out.println(to.getDeptno());
			System.out.println(to.getDname());
			System.out.println(to.getLoc());
		} catch (IOException e) {
			System.out.println("[에러]" + e.getMessage());
		}finally {
			if(sqlSession != null) sqlSession.close();
			if(is != null) try { is.close(); } catch(IOException e) {}
		}
	}

}
