package mysite.repository;

import java.util.Map;


import org.apache.ibatis.session.SqlSession;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;

import mysite.security.UserDetailsImpl;
import mysite.vo.UserVo;

@Repository
public class UserRepository {
	
	private SqlSession sqlSession;
	
	public UserRepository(SqlSession sqlSession) {
		this.sqlSession=sqlSession;
	}
	public int insert(UserVo vo) {
			return sqlSession.insert("user.insert", vo);
	}

	public UserVo findByEmailAndPassword(String email, String password) {
		return sqlSession.selectOne("user.findByEmailAndPassword", Map.of("email", email, "password", password));
	}
	
	public <R> R findByEmail(String email, Class<R> resultType) {
		Map<String, Object> map = sqlSession.selectOne("user.findByEmail", email);
		return new ObjectMapper().convertValue(map, resultType);
	}

	public UserVo findById(Long id) {
		return sqlSession.selectOne("user.findById", id);
	}

	public void updateById(UserVo vo) {
		sqlSession.update("user.update", vo);

	}

}
