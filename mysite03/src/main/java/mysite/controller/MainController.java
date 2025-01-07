package mysite.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class MainController {
	
//	@Autowired
//	ApplicationContext applicationContext;
	
	//SiteVo를 바로 Autowired 할 수 없는 이유는
	
	@RequestMapping({"/","/main"})
	public String index(Model model) {
//		SiteVo vo=applicationContext.getBean(SiteVo.class);
		return "main/index";
	}
}
