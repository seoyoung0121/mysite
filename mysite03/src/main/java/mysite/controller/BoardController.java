package mysite.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import mysite.service.BoardService;
import mysite.vo.BoardVo;
import mysite.vo.UserVo;

@Controller
@RequestMapping("/board")
public class BoardController {
	private BoardService boardService;
	
	public BoardController(BoardService boardService) {
		this.boardService=boardService;
	}
	
	@RequestMapping
	public String list(@RequestParam(name="page", required=false) String pageParam, Model model){
		int page = 1;
		if (pageParam != null && !pageParam.isEmpty()) {
			page = Integer.parseInt(pageParam);
			page = (page < 1) ? 1 : page;
		}	
		
		Map<String, Object> map = boardService.getContentsList(page, null);
		model.addAttribute("list", map.get("list"));
		model.addAttribute("currentPage", map.get("currentPage"));
		model.addAttribute("startPage", map.get("startPage"));
		model.addAttribute("totalPage", map.get("totalPage"));
		model.addAttribute("startArticleNum", map.get("startArticleNum"));
		return "board/list";
	}
	
	@RequestMapping("/view")
	public String view(@RequestParam("id") Long id, Model model) {
		BoardVo boardVo = boardService.getContents(id);
		boardService.addHit(id);
		model.addAttribute("vo", boardVo);
		return "board/view";
	}
	
	@RequestMapping(value="/write", method=RequestMethod.GET)
	public String write(HttpSession session) {
		// Access control
		UserVo authUser= (UserVo)session.getAttribute("authUser");
		if(authUser == null) {
			return "redirect:/board";
		}
		
		return "board/write";
	}
	
	@RequestMapping(value="/write", method=RequestMethod.POST)
	public String write(HttpSession session, BoardVo boardVo) {
		// Access control
		UserVo authUser= (UserVo)session.getAttribute("authUser");
		if(authUser == null) {
			return "redirect:/board";
		}
		
		boardVo.setUserId(authUser.getId());
		boardService.addContents(boardVo);
		return "redirect:/board";
	}
	
	@RequestMapping("/delete")
	public String delete(HttpSession session, @RequestParam("id") Long id) {
		// Access control
		UserVo authUser= (UserVo)session.getAttribute("authUser");
		if(authUser == null) {
			return "redirect:/board";
		}
		
		boardService.deleteContents(id, authUser.getId());
		
		return "redirect:/board";
	}
	
	@RequestMapping(value="/modify", method=RequestMethod.GET)
	public String modify(HttpSession session, @RequestParam("id") Long id, Model model) {
		// Access control
		UserVo authUser= (UserVo)session.getAttribute("authUser");
		if(authUser == null) {
			return "redirect:/board";
		}
		BoardVo vo=boardService.getContents(id, authUser.getId());
		if(vo==null) {
			return "redirect:/board";
		}
		model.addAttribute("vo", vo);
		return "board/modify";
	}
	
	@RequestMapping(value="/modify", method=RequestMethod.POST)
	public String modify(HttpSession session, BoardVo boardVo) {
		// Access control
		UserVo authUser= (UserVo)session.getAttribute("authUser");
		if(authUser == null || authUser.getId()!=boardVo.getUserId()) {
			return "redirect:/board";
		}
		boardService.updateContents(boardVo);
		return "redirect:/board/view?id="+boardVo.getId();
	}
	
	
}
