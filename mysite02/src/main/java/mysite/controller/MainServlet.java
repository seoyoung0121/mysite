package mysite.controller;

import jakarta.servlet.annotation.WebServlet;
import mysite.controller.action.main.MainAction;



@WebServlet({"/main", ""}) // /는 모든 요청을 뜻해서 비워둬야함 
public class MainServlet extends ActionServlet {
	private static final long serialVersionUID = 1L;

	@Override
	public Action getAction(String actionName) {
		return new MainAction();
	}

}
