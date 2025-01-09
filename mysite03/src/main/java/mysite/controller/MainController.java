package mysite.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import mysite.vo.UserVo;


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
	
	@ResponseBody
	@RequestMapping("/msg01")
	public String message01() {
		return "Hello World";
	}
	
	@ResponseBody
	@RequestMapping("/msg02")
	public String message02() {
		return "안녕~~";
	}
	
	@ResponseBody
	@RequestMapping("/msg03")
	public Object message03() {
		UserVo vo = new UserVo();
		vo.setId(10L);
		vo.setName("둘리");
		vo.setEmail("dooly@gmail.com");
		return vo;
	}
}
