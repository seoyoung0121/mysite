package mysite.controller.action.board;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mysite.controller.ActionServlet.Action;
import mysite.dao.BoardDao;
import mysite.vo.BoardVo;

public class DefaultBoardAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int page = 1;
		String pageParam = request.getParameter("page");
		if (pageParam != null && !pageParam.isEmpty()) {
			page = Integer.parseInt(pageParam);
			page = (page < 1) ? 1 : page;
		}
		BoardDao dao = new BoardDao();

		int totalPage = dao.findTotalPage();
		page=(page <= totalPage) ? page : totalPage;
		List<BoardVo> list = dao.findWithPage(page);
		request.setAttribute("list", list);
		request.setAttribute("currentPage", page);
		request.setAttribute("startPage", (page <= 5) ? 1 : page - 4);
		request.setAttribute("totalPage", totalPage);
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/board/list.jsp");
		rd.forward(request, response);
	}

}
