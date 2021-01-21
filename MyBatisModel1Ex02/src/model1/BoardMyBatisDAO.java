package model1;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import config.BoardMapperInter;

public class BoardMyBatisDAO {
	
	private SqlSession sqlSession;
	private BoardMapperInter mapperInter;
	
	public BoardMyBatisDAO() {
		
		String resource = "myBatisConfig.xml";
		
		InputStream is = null;
		
		try {
			is = Resources.getResourceAsStream(resource);
			SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
			
			this.sqlSession = sqlSessionFactory.openSession(true);
		
			// mapper interface 호출
			sqlSession.getConfiguration().addMapper( BoardMapperInter.class);
									
			this.mapperInter = (BoardMapperInter)sqlSession.getMapper(BoardMapperInter.class);
		
		} catch (IOException e) {
			System.out.println("[에러]" + e.getMessage());
		} finally {
			if(is != null) try {is.close();} catch(IOException e) {}
		}
	}
	
	
	// writer - dao 통과 안해도됨
	public void boardWrite() {}
	
	// writer_ok - flag 값있어야함
	public int boardWriteOk(BoardTO to) {
		int flag = 1;
		
		int result = mapperInter.boardWriteOk(to);
		if(result == 1) {
			flag = 0;
		}
		
		return flag;
	}
	
	
	// list
	public ArrayList<BoardTO> boardList(){
		
		ArrayList<BoardTO> boardLists = (ArrayList)mapperInter.boardList();
		if(sqlSession != null) sqlSession.close();
		
		return boardLists;
	}
	
	
	// view
	public int boardViewHit(BoardTO to) {
		int flag = 0;
		
		flag = mapperInter.boardViewHit(to);
		if(sqlSession != null) sqlSession.close();
		
		return flag;
	}
	
	// view
	public BoardTO boardView(BoardTO to) {
		
		to = mapperInter.boardView( to);
		if(sqlSession != null) sqlSession.close();
		
		return to;
	}
	
	// delete
	public BoardTO boardDelete(BoardTO to) {
		BoardTO board = mapperInter.boardDelete(to);
		
		if(sqlSession != null) sqlSession.close();
		
		return board;
	}
	
	// delete_ok
	public int boardDeleteOk(BoardTO to) {
		int flag = 2;
		
		int result = mapperInter.boardDeleteOk(to);
		if(result == 1) {
			flag = 2;
		}else if(result == 0) {
			flag = 1;
		}
		
		return flag;
	}
	
	// modify
	public BoardTO boardModify(BoardTO to) {
		BoardTO board = mapperInter.boardModify(to);
		
		if(sqlSession != null) sqlSession.close();
		
		return board;
	}
	
	// modify_ok
	public int boardModifyOk(BoardTO to) {
		int flag = 1;
		
		int result = mapperInter.boardModifyOk(to);
		if(result == 1) {
			flag = 2;
		}else if(result == 0) {
			flag = 1;
		}
		
		return flag;
	}
}
