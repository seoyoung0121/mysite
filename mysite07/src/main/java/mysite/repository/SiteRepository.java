package mysite.repository;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import mysite.vo.SiteVo;

@Repository
public class SiteRepository {
	private SqlSession sqlSession;
	
	public SiteRepository(SqlSession sqlSession) {
		this.sqlSession=sqlSession;
	}
	
	public SiteVo findOne(){
		return sqlSession.selectOne("mysite.findOne");
	}
	
	public void update(SiteVo vo) {
		sqlSession.update("mysite.update",vo);
	}
}
