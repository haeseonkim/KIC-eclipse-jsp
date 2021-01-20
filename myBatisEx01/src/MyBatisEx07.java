import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MyBatisEx07 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String resource = "myBatisConfig.xml";
		
		// 파일 읽어 오기
		InputStream is = null;
		// 데이터베이스 연결
		SqlSession sqlSession = null;
		
		
		try {
			is = Resources.getResourceAsStream( resource );
			SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
			System.out.println("호출 성공");
			
			sqlSession = sqlSessionFactory.openSession();
			System.out.println("연결 성공");
			
			// like 검색
			//List<EmpTO> lists = sqlSession.selectList("selectList3", "S%");
			List<EmpTO> lists = sqlSession.selectList("selectList4", "S");
			System.out.println(lists.size());
			
			for(EmpTO to: lists) {
				System.out.println(to.getEmpno());
				System.out.println(to.getEname());
				System.out.println(to.getJob());
				System.out.println(to.getMgr());
				System.out.println(to.getHiredate());
				System.out.println(to.getSal());
				System.out.println(to.getComm());
				System.out.println(to.getDeptno());
			}
			
		} catch (IOException e) {
			System.out.println("에러" + e.getMessage());
		} finally {
			if(sqlSession != null) sqlSession.close();
			if(is != null) try { is.close(); } catch(IOException e) {}
		}
	}

}
