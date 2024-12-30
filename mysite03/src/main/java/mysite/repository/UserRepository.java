package mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.stereotype.Repository;

import mysite.vo.UserVo;

@Repository
public class UserRepository {

	public int insert(UserVo vo) {
		int count = 0;

		try (Connection conn = getConnection();
				PreparedStatement pstmt = conn.prepareStatement("insert into user values(null, ?, ?, ?, ?, now(),'USER')");) {

			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getEmail());
			pstmt.setString(3, vo.getPassword());
			pstmt.setString(4, vo.getGender());

			count = pstmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		return count;

	}

	public UserVo findByEmailAndPassword(String email, String password) {
		UserVo userVo = null;

		try (Connection conn = getConnection();
				PreparedStatement pstmt = conn
						.prepareStatement("select id, name, role from user where email=? and password=?");) {

			pstmt.setString(1, email);
			pstmt.setString(2, password);

			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				Long id = rs.getLong(1);
				String name = rs.getString(2);
				String role = rs.getString(3);

				userVo = new UserVo();
				userVo.setId(id);
				userVo.setName(name);
				userVo.setRole(role);
			}
			rs.close();
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		return userVo;
	}

	public UserVo findById(Long id) {
		UserVo userVo = null;

		try (Connection conn = getConnection();
				PreparedStatement pstmt = conn.prepareStatement("select email, name, gender from user where id=?");) {

			pstmt.setLong(1, id);

			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {

				String email = rs.getString(1);
				String name = rs.getString(2);
				String gender = rs.getString(3);

				userVo = new UserVo();
				userVo.setEmail(email);
				userVo.setName(name);
				userVo.setGender(gender);
			}
			rs.close();
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		return userVo;
	}

	public void updateById(UserVo vo) {
		String sql = "";
		if (vo.getPassword().isEmpty()) {
			sql = "update user set name = '" + vo.getName() + "', gender='" + vo.getGender() + "' where id=" + vo.getId();
		} else {
			sql = "update user set name = '" + vo.getName() + "', gender='" + vo.getGender() + "', password='"
					+ vo.getPassword() + "' where id=" + vo.getId();
		}
		try (Connection conn = getConnection(); 
			Statement stmt = conn.createStatement();) {

			stmt.executeUpdate(sql);

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

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
