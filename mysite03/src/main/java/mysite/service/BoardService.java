package mysite.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import mysite.repository.BoardRepository;
import mysite.vo.BoardVo;

@Service
public class BoardService {
	private BoardRepository boardRepository;
	
	public BoardService(BoardRepository boardRepository) {
		this.boardRepository=boardRepository;
	}
	public void addContents(BoardVo vo) {
		
	}
	
	public BoardVo getContents(Long id) {
		return null;
	}
	
	public BoardVo getContents(Long id, Long userId) {
		return null;
	}
	
	public void updateContents(BoardVo vo) {
		
	}
	
	public void deleteContents(Long id, Long userId) {
		
	}
	
	public Map<String, Object> getContentsList(int currentPage, String keyword){
		//List<BoardVo> list = 
		//int beginPage
		//int endPage
		
		//view의 paginantion을 위한 데이터 값 계산
		return null;
	}
	
	
	
}
