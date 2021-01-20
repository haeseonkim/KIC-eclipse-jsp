package model1;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.sun.org.apache.xalan.internal.xsltc.compiler.sym;

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
			
			String sql = "insert ab_board1 values(0, ?, ?, ?, ?, ?, ?, 0, ?, now())";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, to.getSubject());
			pstmt.setString(2, to.getWriter());
			pstmt.setString(3, to.getMail());
			pstmt.setString(4, to.getPassword());
			pstmt.setString(5, to.getFilename());
			pstmt.setString(6, to.getContent());
			//pstmt.setLong(7, to.getFilesize());
			pstmt.setString(7, to.getWip());
			
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
			
			String sql = "select seq, subject, writer, date_format(wdate,'%Y-%m-%d') wdate, hit, datediff(now(), wdate) wgap, filename from ab_board1 order by seq desc";
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
				to.setFilename(rs.getString("filename"));
				//to.setFilesize(rs.getLong("filesize"));
				
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
	
	// paging list
	public BoardListTO boardList(BoardListTO listTO) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		// paging을 위한 기본 요소
		int cpage = listTO.getCpage();
		int recordPerPage = listTO.getRecordPerPage();
		int blockPerPage = listTO.getBlockPerPage();
		
		try{
			conn = dataSource.getConnection();
			
			String sql = "select seq, subject, writer, date_format(wdate,'%Y-%m-%d') wdate, hit, datediff(now(), wdate) wgap, filename from ab_board1 order by seq desc";
			pstmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			rs = pstmt.executeQuery();
			
			rs.last();
			listTO.setTotalRecord(rs.getRow());
			rs.beforeFirst();
			
			listTO.setTotalPage(((listTO.getTotalRecord() - 1)/recordPerPage) + 1);
			
			int skip = (cpage-1) * recordPerPage;
			if(skip != 0)rs.absolute(skip);
			
			ArrayList<BoardTO> lists = new ArrayList<BoardTO>();
			
			for(int i = 0; i<recordPerPage && rs.next(); i++) {
				BoardTO to = new BoardTO();
				to.setSeq(rs.getString("seq"));
				to.setSubject(rs.getString("subject"));
				to.setWriter(rs.getString("writer"));
				to.setWdate(rs.getString("wdate"));
				to.setHit(rs.getString("hit"));
				to.setWgap(rs.getInt("wgap"));
				to.setFilename(rs.getString("filename"));
				
				lists.add(to);
			}
			
			listTO.setBoardList(lists);
			
			listTO.setStartBlock(((cpage-1)/blockPerPage) * blockPerPage + 1);
			listTO.setEndBlock(((cpage-1)/blockPerPage) * blockPerPage + blockPerPage);
			if(listTO.getEndBlock() >= listTO.getTotalPage()) {
				listTO.setEndBlock(listTO.getTotalPage());
			}
			
		}catch(SQLException e){
			System.out.println("[에러] : "+e.getMessage());
		}finally{
			if(rs != null) try{ rs.close();} catch(SQLException e) {}
			if(pstmt != null) try{ pstmt.close();} catch(SQLException e) {}
			if(conn != null) try{conn.close();} catch(SQLException e) {}
		}
		return listTO;
	}
		
	
	
	// view
	public BoardTO boardView(BoardTO to) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
	
		try{
			conn = dataSource.getConnection();
			
			// 조회수 증가를 위한 update
			String sql = "update ab_board1 set hit=hit+1 where seq=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, to.getSeq());
			
			pstmt.executeUpdate();
			
			sql = "select subject, writer, mail, wip, wdate, hit,filename, content from ab_board1 where seq=?";
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
				to.setFilename(rs.getString("filename"));
				//to.setFilesize(rs.getLong("filesize"));
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
	
	// before view
	public BoardTO beforeView(BoardTO to) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
	
		try{
			conn = dataSource.getConnection();
			
			String sql = "select seq, subject from ab_board1 where seq=(select max(seq) from ab_board1 where seq < ?);";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, to.getSeq());
			
			rs = pstmt.executeQuery();
			// 하나만 가져오니까 if문 사용
			if(rs.next()){
				to.setSeq(rs.getString("seq"));
				to.setSubject(rs.getString("subject"));
			}else {
				to.setSubject("이전글이 없습니다.");
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
	
	// after view
	public BoardTO afterView(BoardTO to) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
	
		try{
			conn = dataSource.getConnection();
			
			String sql = "select seq, subject from ab_board1 where seq=(select min(seq) from ab_board1 where seq > ?);";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, to.getSeq());
			
			rs = pstmt.executeQuery();
			// 하나만 가져오니까 if문 사용
			if(rs.next()){
				to.setSeq(rs.getString("seq"));
				to.setSubject(rs.getString("subject"));
			}else {
				to.setSubject("다음글이 없습니다.");
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
			
			String sql = "select subject, writer from ab_board1 where seq=?";
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
		ResultSet rs = null;
		
		// 정상처리 / 비정상 => 결과를 통합처리하기 위한 변수
		int flag = 2;
		
		try{
			conn = dataSource.getConnection();
			
			// filename select
			String sql = "select filename from ab_board1 where seq=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, to.getSeq());
			
			rs = pstmt.executeQuery();
			String filename = null;
			if(rs.next()){
				filename = rs.getString("filename");
			}
			
			sql = "delete from ab_board1 where seq=? and password=?";
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
				// 실제 파일을 지워줘!
				if(filename != null){
					File file = new File("C:/Java/java/jsp-workspace/AlbumEx01/WebContent/upload/" + filename);
					file.delete();
				}
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
			
			String sql = "select subject, writer, content, mail, filename from pds_board1 where seq=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, to.getSeq());
			
			rs = pstmt.executeQuery();
			// 하나만 가져오니까 if문 사용
			if(rs.next()){
				to.setSubject(rs.getString("subject"));
				to.setWriter(rs.getString("writer"));
				to.setMail(rs.getString("mail"));
				to.setContent(rs.getString("content"));
				to.setFilename( rs.getString("filename"));
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
		ResultSet rs = null;
		
		// 정상처리 / 비정상 => 결과를 통합처리하기 위한 변수
		int flag = 2;
		
		try{
			conn = dataSource.getConnection();
			
			String sql = "select filename from ab_board1 where seq=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, to.getSeq());
			
			rs = pstmt.executeQuery();
			String oldFilename = null;
			if(rs.next()){
				oldFilename = rs.getString("filename");
			}
			
			
			if(to.getFilename() != null ){
				// 새 업로드 파일이 있을 때 
				sql = "update ab_board1 set subject = ?, mail=?, content=?, filename=? where seq=? and password=?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, to.getSubject());
				pstmt.setString(2, to.getMail());
				pstmt.setString(3, to.getContent());
				pstmt.setString(4, to.getFilename());
				pstmt.setString(5, to.getSeq());
				pstmt.setString(6, to.getPassword());
			}else{
			
				sql = "update ab_board1 set subject = ?, mail=?, content=? where seq=? and password=?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, to.getSubject());
				pstmt.setString(2, to.getMail());
				pstmt.setString(3, to.getContent());
				pstmt.setString(4, to.getSeq());
				pstmt.setString(5, to.getPassword());
			}
			
			int result = pstmt.executeUpdate();
			if(result == 0){
				// 비밀번호 오류
				flag = 1;
			}else if(result == 1){
				// 정상
				flag = 0;
				if(to.getFilename() != null && oldFilename != null){
					File file = new File("C:/Java/java/jsp-workspace/AlbumEx01/WebContent/upload/" + oldFilename);
					file.delete();
				}
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
