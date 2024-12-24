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
	private static final int ARTICLE_NUM = 10;

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int page = 1;
		String pageParam = request.getParameter("page");
		if (pageParam != null && !pageParam.isEmpty()) {
			page = Integer.parseInt(pageParam);
			page = (page < 1) ? 1 : page;
		}
		BoardDao dao = new BoardDao();

		int totalArticle = dao.findTotalArticle();
		int totalPage = (int) Math.ceil((double) totalArticle / ARTICLE_NUM);
		totalPage=(totalPage<1)?1:totalPage;
		page = (page <= totalPage) ? page : totalPage;
		int startArticleNum = (page - 1) * ARTICLE_NUM;
		startArticleNum = (startArticleNum < 0) ? 0 : startArticleNum;
		List<BoardVo> list = dao.findWithLimit(startArticleNum, ARTICLE_NUM);

		request.setAttribute("list", list);
		request.setAttribute("currentPage", page);
		request.setAttribute("startPage", (page <= 5) ? 1 : page - 4);
		request.setAttribute("totalPage", totalPage);
		request.setAttribute("startArticleNum", totalArticle - startArticleNum);

		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/board/list.jsp");
		rd.forward(request, response);
	}

}
