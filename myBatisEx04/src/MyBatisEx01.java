
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

public class MyBatisEx01 {

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
			
			// ${} 사용 , 여기서 sql문 작성
			//String sql = "create table tb1 (col1 varchar(10))";
			//int result = sqlSession.update("createTable1", sql);
			
			int result = sqlSession.update("createTable2", "tb12");
			
			if(result == 0) {
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
