

import java.io.IOException;
import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MyBatisEx02 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String resource = "myBatisConfig.xml";
		
		Reader reader = null;
		
		try {
			reader = Resources.getResourceAsReader( resource );
			SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
			System.out.println("호출 성공");
		} catch (IOException e) {
			System.out.println("에러" + e.getMessage());
		} finally {
			if(reader != null) try { reader.close(); } catch(IOException e) {}
		}
	}

}
