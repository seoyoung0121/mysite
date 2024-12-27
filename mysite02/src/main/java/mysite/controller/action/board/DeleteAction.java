package mysite.controller.action.board;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import mysite.controller.ActionServlet.Action;
import mysite.dao.BoardDao;
import mysite.vo.UserVo;

public class DeleteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Long id = Long.parseLong(request.getParameter("id"));
		Long userId = Long.parseLong(request.getParameter("userId")); 
		HttpSession session = request.getSession();
		UserVo vo = (UserVo) session.getAttribute("authUser"); 
		if(vo==null || !vo.getId().equals(userId)) { 
			response.sendRedirect(request.getContextPath()+"/board");
			return;
		}
		new BoardDao().deleteById(id);
		response.sendRedirect(request.getContextPath()+"/board");
	}

}
