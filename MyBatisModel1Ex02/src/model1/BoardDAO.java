package model1;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class BoardDAO {
	
	private DataSource dataSource;
	
	public BoardDAO() {
		try {
			Context iniCtx = new InitialContext();
			Context envCtx = (Context)iniCtx.lookup("java:comp/env");
			this.dataSource = (DataSource)envCtx.lookup("jdbc/mariadb2");
		} catch (NamingException e) {
			System.out.println("[에러] " + e.getMessage());
		}
	}
	
	
	// writer - dao 통과 안해도됨
	public void boardWrite() {}
	
	// writer_ok - flag 값있어야함
	public int boardWriteOk(BoardTO to) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		// 정상처리 / 비정상 => 결과를 통합처리하기 위한 변수
		int flag = 1;
		
		try{
			conn = dataSource.getConnection();
			
			String sql = "insert board1 values(0, ?, ?, ?, ?, ?, 0, ?, now())";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, to.getSubject());
			pstmt.setString(2, to.getWriter());
			pstmt.setString(3, to.getMail());
			pstmt.setString(4, to.getPassword());
			pstmt.setString(5, to.getContent());
			pstmt.setString(6, to.getWip());
			
			// 일반적으로 0 값은 정상이다.
			int result = pstmt.executeUpdate();
			if(result == 1){
				flag = 0;
			}
		}catch(SQLException e){
			System.out.println("[에러] : "+e.getMessage());
		}finally{
			if(pstmt != null) try{pstmt.close();} catch(SQLException e) {}
			if(conn != null) try{conn.close();} catch(SQLException e) {}
		}
		
		return flag;
	}
	
	
	// list
	public ArrayList<BoardTO> boardList(){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		ArrayList<BoardTO> lists = new ArrayList<BoardTO>();
		try{
			conn = dataSource.getConnection();
			
			String sql = "select seq, subject, writer, date_format(wdate,'%Y-%m-%d') wdate, hit, datediff(now(), wdate) wgap from board1 order by seq desc";
			pstmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				BoardTO to = new BoardTO();
				to.setSeq(rs.getString("seq"));
				to.setSubject(rs.getString("subject"));
				to.setWriter(rs.getString("writer"));
				to.setWdate(rs.getString("wdate"));
				to.setHit(rs.getString("hit"));
				to.setWgap(rs.getInt("wgap"));
				
				lists.add(to);
			}
		}catch(SQLException e){
			System.out.println("[에러] : "+e.getMessage());
		}finally{
			if(rs != null) try{ rs.close();} catch(SQLException e) {}
			if(pstmt != null) try{ pstmt.close();} catch(SQLException e) {}
			if(conn != null) try{conn.close();} catch(SQLException e) {}
		}
		return lists;
	}
	
	
	// view
	public BoardTO boardView(BoardTO to) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
	
		try{
			conn = dataSource.getConnection();
			
			// 조회수 증가를 위한 update
			String sql = "update board1 set hit=hit+1 where seq=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, to.getSeq());
			
			pstmt.executeUpdate();
			
			sql = "select subject, writer, mail, wip, wdate, hit, content from board1 where seq=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, to.getSeq());
			
			rs = pstmt.executeQuery();
			// 하나만 가져오니까 if문 사용
			if(rs.next()){
				to.setSubject(rs.getString("subject"));
				to.setWriter(rs.getString("writer"));
				to.setMail(rs.getString("mail"));
				to.setHit(rs.getString("hit"));
				to.setWip(rs.getString("wip"));
				to.setWdate(rs.getString("wdate"));
				to.setContent(rs.getString("content"));
			}
		
		}catch(SQLException e){
			System.out.println("[에러] : "+e.getMessage());
		}finally{
			if(rs != null) try{rs.close();} catch(SQLException e) {}
			if(pstmt != null) try{pstmt.close();} catch(SQLException e) {}
			if(conn != null) try{conn.close();} catch(SQLException e) {}
		}
		
		return to;
	}
	
	// delete
	public BoardTO boardDelete(BoardTO to) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try{
			conn = dataSource.getConnection();
			
			String sql = "select subject, writer from board1 where seq=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, to.getSeq());
			
			rs = pstmt.executeQuery();
			// 하나만 가져오니까 if문 사용
			if(rs.next()){
				to.setSubject(rs.getString("subject"));
				to.setWriter(rs.getString("writer"));
			}
		}catch(SQLException e){
			System.out.println("[에러] : "+e.getMessage());
		}finally{
			if(rs != null) try{rs.close();} catch(SQLException e) {}
			if(pstmt != null) try{pstmt.close();} catch(SQLException e) {}
			if(conn != null) try{conn.close();} catch(SQLException e) {}
		}
		return to;
	}
	
	// delete_ok
	public int boardDeleteOk(BoardTO to) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		// 정상처리 / 비정상 => 결과를 통합처리하기 위한 변수
		int flag = 2;
		
		try{
			conn = dataSource.getConnection();
			
			String sql = "delete from board1 where seq=? and password=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, to.getSeq());
			pstmt.setString(2, to.getPassword());
			
			int result = pstmt.executeUpdate();
			if(result == 0){
				// 비밀번호 오류
				flag = 1;
			}else if(result == 1){
				// 정상
				flag = 0;
			}
		}catch(SQLException e){
			System.out.println("[에러] : "+e.getMessage());
		}finally{
			if(pstmt != null) try{pstmt.close();} catch(SQLException e) {}
			if(conn != null) try{conn.close();} catch(SQLException e) {}
		}
		return flag;
	}
	
	// modify
	public BoardTO boardModify(BoardTO to) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try{
			conn = dataSource.getConnection();
			
			String sql = "select subject, writer, content, mail from board1 where seq=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, to.getSeq());
			
			rs = pstmt.executeQuery();
			// 하나만 가져오니까 if문 사용
			if(rs.next()){
				to.setSubject(rs.getString("subject"));
				to.setWriter(rs.getString("writer"));
				to.setMail(rs.getString("mail"));
				to.setContent(rs.getString("content"));
			}
		}catch(SQLException e){
			System.out.println("[에러] : "+e.getMessage());
		}finally{
			if(rs != null) try{rs.close();} catch(SQLException e) {}
			if(pstmt != null) try{pstmt.close();} catch(SQLException e) {}
			if(conn != null) try{conn.close();} catch(SQLException e) {}
		}
		return to;
	}
	
	// modify_ok
	public int boardModifyOk(BoardTO to) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		// 정상처리 / 비정상 => 결과를 통합처리하기 위한 변수
		int flag = 2;
		
		try{
			conn = dataSource.getConnection();
			
			String sql = "update board1 set subject = ?, mail=?, content=? where seq=? and password=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, to.getSubject());
			pstmt.setString(2, to.getMail());
			pstmt.setString(3, to.getContent());
			pstmt.setString(4, to.getSeq());
			pstmt.setString(5, to.getPassword());
			
			int result = pstmt.executeUpdate();
			if(result == 0){
				// 비밀번호 오류
				flag = 1;
			}else if(result == 1){
				// 정상
				flag = 0;
			}
		}catch(SQLException e){
			System.out.println("[에러] : "+e.getMessage());
		}finally{
			if(pstmt != null) try{pstmt.close();} catch(SQLException e) {}
			if(conn != null) try{conn.close();} catch(SQLException e) {}
		}
		return flag;
	}
}
