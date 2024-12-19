package mysite.controller.action.user;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import mysite.controller.ActionServlet.Action;
import mysite.dao.UserDao;
import mysite.vo.UserVo;

public class UpdateAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		String gender = request.getParameter("gender");
		HttpSession session = request.getSession();
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		
		
		UserVo vo = new UserVo();
		vo.setId(authUser.getId());
		vo.setName(name);
		vo.setPassword(password);
		vo.setGender(gender);
		
		new UserDao().updateById(vo);
		
		vo.setPassword(null);
		vo.setGender(null);
		
		session.setAttribute("authUser", vo);
		response.sendRedirect(request.getContextPath()+"/user?a=updateform");
		
	}

}
