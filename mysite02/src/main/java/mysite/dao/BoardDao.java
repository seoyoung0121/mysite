package mysite.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import mysite.vo.BoardVo;

public class BoardDao {
	private static final int ARTICLE_NUM = 10;
	public List<BoardVo> findall() {
		List<BoardVo> result = new ArrayList<>();
		
		try (Connection conn = getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select a.id, a.title, a.contents, a.hit, date_format(a.reg_date, '%Y-%m-%d %h:%i:%s'), a.g_no, a.o_no, a.depth, a.user_id, b.name "
															+ "from board a, user b where a.user_id=b.id order by g_no desc, o_no asc");
			ResultSet rs = pstmt.executeQuery();
			){
			
			while (rs.next()) {
				Long id = rs.getLong(1);
				String title = rs.getString(2);
				String contents = rs.getString(3);
				int hit = rs.getInt(4);
				String regDate=rs.getString(5);
				int gNo = rs.getInt(6);
				int oNo = rs.getInt(7);
				int depth = rs.getInt(8);
				Long userId = rs.getLong(9);
				String userName = rs.getString(10);
				

				BoardVo vo = new BoardVo();
				vo.setId(id);
				vo.setTitle(title);
				vo.setContents(contents);
				vo.setHit(hit);
				vo.setRegDate(regDate);
				vo.setgNo(gNo);
				vo.setoNo(oNo);
				vo.setDepth(depth);
				vo.setUserId(userId);
				vo.setUserName(userName);
				result.add(vo);
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} 
		return result;
	}
	
	public void addArticle(String title, String content, Long userId) {
		try(Connection conn = getConnection();
			Statement stmt = conn.createStatement();
			PreparedStatement pstmt = conn.prepareStatement("insert into board values (null, ?, ?, 0, now(), ?, 1, 0, ? )");
			){
			ResultSet rs = stmt.executeQuery("select max(g_no) from board");
			int gNo=0;
			if(rs.next()) {
				gNo=rs.getInt(1);
			}
			rs.close();
			
	
			pstmt.setString(1, title);
			pstmt.setString(2, content);
			pstmt.setInt(3, gNo+1);
			pstmt.setLong(4, userId);
			
			pstmt.executeUpdate();
			
			
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		
	}
	
	public void addReplyArticle(int gNo, int oNo, int depth, String title, String content, Long userId) {
		
		try(Connection conn = getConnection();
				PreparedStatement pstmt1 = conn.prepareStatement("update board set o_no=o_no+1 where o_no>= ?");
				PreparedStatement pstmt2 = conn.prepareStatement("insert into board values (null, ?, ?, 0, now(), ?, ?, ?, ? )");
				
				){
				
				oNo+=1;
		
				// oNo본인보다 크거나 같은애 모두 +1
				pstmt1.setInt(1, oNo);
				pstmt1.executeUpdate();
				
				pstmt2.setString(1, title);
				pstmt2.setString(2, content);
				pstmt2.setInt(3, gNo);
				pstmt2.setInt(4, oNo);
				pstmt2.setInt(5, depth+1);
				pstmt2.setLong(6, userId);
				
				pstmt2.executeUpdate();
				
				
				
			} catch (SQLException e) {
				System.out.println("error:" + e);
			}
		
	}
	
	public void deleteById(Long id) {
		try(Connection conn = getConnection();
			PreparedStatement pstmt = conn.prepareStatement("delete from board where id = ?");
			){
			pstmt.setLong(1, id);
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		
	}
	
	public BoardVo findById(Long id) {
		BoardVo vo = new BoardVo();
		try (Connection conn = getConnection();
				PreparedStatement pstmt = conn.prepareStatement("select a.title, a.contents, a.hit, date_format(a.reg_date, '%Y-%m-%d %h:%i:%s'), a.g_no, a.o_no, a.depth, a.user_id, b.name "
																+ "from board a, user b where a.user_id=b.id and a.id= ? order by g_no desc, o_no asc");
				){
				
				pstmt.setLong(1,id);
				ResultSet rs = pstmt.executeQuery();
				if (rs.next()) {
					String title = rs.getString(1);
					String contents = rs.getString(2);
					int hit = rs.getInt(3);
					String regDate=rs.getString(4);
					int gNo = rs.getInt(5);
					int oNo = rs.getInt(6);
					int depth = rs.getInt(7);
					Long userId = rs.getLong(8);
					String userName = rs.getString(9);
					

					
					vo.setId(id);
					vo.setTitle(title);
					vo.setContents(contents);
					vo.setHit(hit);
					vo.setRegDate(regDate);
					vo.setgNo(gNo);
					vo.setoNo(oNo);
					vo.setDepth(depth);
					vo.setUserId(userId);
					vo.setUserName(userName);
				}
				
				rs.close();
			} catch (SQLException e) {
				System.out.println("error:" + e);
			} 
			return vo;
	}
	
	public void updateById(Long id, String title, String contents) {
		try(Connection conn = getConnection();
			PreparedStatement pstmt = conn.prepareStatement("update board set title = ?, contents = ? where id = ?");
			){
				pstmt.setString(1, title);
				pstmt.setString(2, contents);
				pstmt.setLong(3, id);
				
				pstmt.executeUpdate();
				
				
			} catch (SQLException e) {
				System.out.println("error:" + e);
			}
	}
	
	public void updateHitById(Long id) {
		try(Connection conn = getConnection();
			PreparedStatement pstmt= conn.prepareStatement("update board set hit = hit+1 where id = ?");
			){
			
			pstmt.setLong(1, id);
			
			pstmt.executeUpdate();

			
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		
	}
	
	public int findTotalPage() {
		double num=0;
		try(Connection conn = getConnection();
			Statement stmt = conn.createStatement();
			){
			ResultSet rs = stmt.executeQuery("select count(*) from board");
			if(rs.next()) {
				num=rs.getDouble(1);
			}
			rs.close();
			
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		return (int) Math.ceil((double) num / ARTICLE_NUM); // 여기서 하는게 맞나? 
	}
	
	public List<BoardVo> findWithPage(int page) {
		List<BoardVo> result = new ArrayList<>();
		
		try (Connection conn = getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select a.id, a.title, a.contents, a.hit, date_format(a.reg_date, '%Y-%m-%d %h:%i:%s'), "
															+ "a.g_no, a.o_no, a.depth, a.user_id, b.name "
															+ "from board a, user b "
															+ "where a.user_id=b.id "
															+ "order by g_no desc, o_no asc "
															+ "limit ?,?");
			){
			pstmt.setInt(1, (page-1)*ARTICLE_NUM);
			pstmt.setInt(2, 10);
			ResultSet rs = pstmt.executeQuery();
			
			while (rs.next()) {
				Long id = rs.getLong(1);
				String title = rs.getString(2);
				String contents = rs.getString(3);
				int hit = rs.getInt(4);
				String regDate=rs.getString(5);
				int gNo = rs.getInt(6);
				int oNo = rs.getInt(7);
				int depth = rs.getInt(8);
				Long userId = rs.getLong(9);
				String userName = rs.getString(10);
				

				BoardVo vo = new BoardVo();
				vo.setId(id);
				vo.setTitle(title);
				vo.setContents(contents);
				vo.setHit(hit);
				vo.setRegDate(regDate);
				vo.setgNo(gNo);
				vo.setoNo(oNo);
				vo.setDepth(depth);
				vo.setUserId(userId);
				vo.setUserName(userName);
				result.add(vo);
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} 
		return result;
	}
	
	private Connection getConnection() throws SQLException {
		Connection conn = null;
		try {
			Class.forName("org.mariadb.jdbc.Driver");

			String url = "jdbc:mariadb://192.168.0.18:3306/webdb";
			conn = DriverManager.getConnection(url, "webdb", "webdb");

		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패:" + e);
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		return conn;
	}

}
