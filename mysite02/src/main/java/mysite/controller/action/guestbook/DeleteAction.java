package mysite.controller.action.guestbook;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mysite.controller.ActionServlet.Action;
import mysite.dao.GuestbookDao;

public class DeleteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("들어");
		Long id = Long.parseLong(request.getParameter("id"));
		String password = request.getParameter("password");
		System.out.println(id);
		System.out.println(password);
		new GuestbookDao().deleteByIdAndPassword(id, password);
		response.sendRedirect(request.getContextPath()+"/guestbook");
	}

}
