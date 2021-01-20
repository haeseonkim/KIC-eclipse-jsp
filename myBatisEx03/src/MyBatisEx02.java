
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

public class MyBatisEx02 {

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
			
			// 자동으로 commit되게
			sqlSession = sqlSessionFactory.openSession(true);
			System.out.println("연결 성공");
			
			DeptTO to = new DeptTO();
			
			to.setDeptno("60");
			
			int result = sqlSession.delete("delete1",to);
			if(result == 1) {
				// 트랜잭션..
				sqlSession.commit();	// 데이터 제대로 들어갔어? 그럼 들어간거 인정해줄게~~
				System.out.println("성공");
				
			}else {
				System.out.println("실패");
			}
			
		} catch (IOException e) {
			System.out.println("에러" + e.getMessage());
		} finally {
			if(sqlSession != null) sqlSession.close();
			if(is != null) try { is.close(); } catch(IOException e) {}
		}
	}

}
