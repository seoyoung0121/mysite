package mysite.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import mysite.controller.action.guestbook.AddAction;
import mysite.controller.action.guestbook.DefaultAction;
import mysite.controller.action.guestbook.DeleteAction;
import mysite.controller.action.guestbook.DeleteFormAction;

@WebServlet("/guestbook")
public class GuestbookServlet extends ActionServlet {
	private static final long serialVersionUID = 1L;
       
	private Map<String, Action> mapAction = Map.of(
			"add", new AddAction(),
			"deleteform", new DeleteFormAction(),
			"delete", new DeleteAction());
	@Override
	public Action getAction(String actionName) {
		return mapAction.getOrDefault(actionName, new DefaultAction());
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}



}
