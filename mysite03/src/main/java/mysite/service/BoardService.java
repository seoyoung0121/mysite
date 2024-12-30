package mysite.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import mysite.repository.BoardRepository;
import mysite.vo.BoardVo;

@Service
public class BoardService {
	private BoardRepository boardRepository;
	private static final int ARTICLE_NUM = 10;
	
	public BoardService(BoardRepository boardRepository) {
		this.boardRepository=boardRepository;
	}
	public void addContents(BoardVo vo) {
		if(vo.getoNo()==0) {
			boardRepository.addArticle(vo.getTitle(), vo.getContents(), vo.getUserId());
		}
		else {
			boardRepository.addReplyArticle(vo.getgNo(), vo.getoNo(), vo.getDepth(),vo.getTitle(), vo.getContents(), vo.getUserId());
		}
	}
	
	public BoardVo getContents(Long id) {
		return boardRepository.findById(id);
	}
	
	public BoardVo getContents(Long id, Long userId) {
		BoardVo vo = boardRepository.findById(id);
		if(userId.equals(vo.getUserId())) {
			return vo;
		}
		return null;
	}
	
	public void updateContents(BoardVo vo) {
		boardRepository.updateById(vo.getId(), vo.getTitle(), vo.getContents());
	}
	
	public void deleteContents(Long id, Long userId) {
		BoardVo vo = boardRepository.findById(id);
	
		if(userId.equals(vo.getUserId())){
			boardRepository.deleteById(id);
		}
	}
	
	public Map<String, Object> getContentsList(int currentPage, String keyword){
		
		int totalArticle = boardRepository.findTotalArticle();
		int totalPage = (int) Math.ceil((double) totalArticle / ARTICLE_NUM);
		totalPage=(totalPage<1)?1:totalPage;
		currentPage = (currentPage <= totalPage) ? currentPage : totalPage;
		int startArticleNum = (currentPage - 1) * ARTICLE_NUM;
		startArticleNum = (startArticleNum < 0) ? 0 : startArticleNum;
		List<BoardVo> list = boardRepository.findWithLimit(startArticleNum, ARTICLE_NUM);
		
		Map<String, Object> map = new HashMap();
		map.put("list", list);
		map.put("currentPage", currentPage);
		map.put("startPage", (currentPage-1)/5*5+1);
		map.put("totalPage", totalPage);
		map.put("startArticleNum", totalArticle - startArticleNum);
		return map;
	}
	
	public void addHit(Long id) {
		boardRepository.updateHitById(id);
	}
	
	
	
}
