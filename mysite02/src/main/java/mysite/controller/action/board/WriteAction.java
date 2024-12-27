package mysite.controller.action.board;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import mysite.controller.ActionServlet.Action;
import mysite.dao.BoardDao;
import mysite.vo.UserVo;

public class WriteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		HttpSession session = request.getSession();
		UserVo vo = (UserVo) session.getAttribute("authUser"); 
		if(vo==null) {
			response.sendRedirect(request.getContextPath()+"/board");
			return;
		}
		
		String gNoParam = request.getParameter("gNo");
		if(gNoParam == null || gNoParam.isEmpty()) {
			new BoardDao().addArticle(title, content, vo.getId());
		}
		else {
			int gNo = Integer.parseInt(gNoParam);
			int oNo= Integer.parseInt(request.getParameter("oNo"));
			int depth = Integer.parseInt(request.getParameter("depth"));
			new BoardDao().addReplyArticle(gNo,oNo, depth, title, content, vo.getId());
		}
		
		response.sendRedirect(request.getContextPath()+"/board");
	}

}
