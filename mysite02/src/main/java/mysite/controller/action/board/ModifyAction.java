package mysite.controller.action.board;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import mysite.controller.ActionServlet.Action;
import mysite.dao.BoardDao;
import mysite.vo.UserVo;

public class ModifyAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Long id = Long.parseLong(request.getParameter("id"));
		Long userId = Long.parseLong(request.getParameter("userId")); // userId를 sql 쿼리 통해서 아는게 낫나?
		String title = request.getParameter("title");
		String contents = request.getParameter("content");
		HttpSession session = request.getSession();
		UserVo vo = (UserVo) session.getAttribute("authUser");
		if (vo == null || !vo.getId().equals(userId)) { // 여기서 검증하는게 맞나?
			response.sendRedirect(request.getContextPath() + "/board?a=view&id=" + id);
			return;
		}
		new BoardDao().updateById(id, title, contents);
		response.sendRedirect(request.getContextPath() + "/board?a=view&id=" + id);

	}

}