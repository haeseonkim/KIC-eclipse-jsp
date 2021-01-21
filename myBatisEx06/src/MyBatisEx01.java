import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import config.SqlMapperInter;
import model1.DeptTO;
import model1.EmpTO;

public class MyBatisEx01 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String resource = "myBatisConfig.xml";
		
		InputStream is = null;
		SqlSession sqlSession = null;
		
		try {
			is = Resources.getResourceAsStream( resource );
			SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
			
			sqlSession = sqlSessionFactory.openSession(true);
			
			// mapper interface 호출
			sqlSession.getConfiguration().addMapper( SqlMapperInter.class);
			
			SqlMapperInter mapper = (SqlMapperInter)sqlSession.getMapper(SqlMapperInter.class);
			
			
			DeptTO to = new DeptTO();
			to.setDeptno("70");
			to.setDname("홍보");
			to.setLoc("서울");
			
			int result = mapper.insert(to);
			System.out.println("[결과] : " + result);
			
			
			/*
			DeptTO uto = new DeptTO();
			uto.setDeptno("70");
			uto.setDname("영업");
			
			int u_result = mapper.update(uto);
			System.out.println("[결과] : " + u_result);
			*/
			
			/*
			DeptTO dto = new DeptTO();
			dto.setDeptno("60");
			
			int d_result = mapper.delete(dto);
			System.out.println("[결과] : " + d_result);
			*/
			
		} catch (IOException e) {
			System.out.println("[에러]" + e.getMessage());
		}finally {
			if(sqlSession != null) sqlSession.close();
			if(is != null) try { is.close(); } catch(IOException e) {}
		}
	}

}
