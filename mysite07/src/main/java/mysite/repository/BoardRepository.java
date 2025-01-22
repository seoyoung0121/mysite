package mysite.repository;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import mysite.vo.BoardVo;

@Repository
public class BoardRepository {
	private SqlSession sqlSession;
	
	public BoardRepository(SqlSession sqlSession) {
		this.sqlSession=sqlSession;
	}
	
	public List<BoardVo> findall() {
		return sqlSession.selectList("board.findAll");
	}
	
	public void addArticle(BoardVo vo) {
		if(vo.getoNo()!=0) {
			updateoNo(vo.getgNo(), vo.getoNo());
		}
		sqlSession.insert("board.insert", vo);
	}
	public void updateoNo(int gNo, int oNo) {
		sqlSession.update("board.updateoNo", Map.of("gNo", gNo, "oNo", oNo));
	}
	public void deleteById(Long id) {
		sqlSession.delete("board.deleteById", id);
	}
	
	public BoardVo findById(Long id) {
		return sqlSession.selectOne("board.findById", id);
	}
	
	public void updateById(Long id, String title, String contents) {
		sqlSession.update("board.updateById", Map.of("id", id, "title", title, "contents", contents));
	}
	
	public void updateHitById(Long id) {
		sqlSession.update("board.updateHitById", id);	
	}
	
	
	public List<BoardVo> findWithLimit(int startNum, int count) {
		return sqlSession.selectList("board.findAllByPageAndKeword", Map.of("startNum", startNum, "count", count));
	}
	
	public int findTotalArticle() {
		return sqlSession.selectOne("board.totalCount");
	}


}
