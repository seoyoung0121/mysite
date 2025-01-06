package mysite.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;
import mysite.service.SiteService;
import mysite.vo.SiteVo;

@Controller
public class MainController {
	private SiteService siteService;
	
	public MainController(SiteService siteService) {
		this.siteService=siteService;
	}
	
	@RequestMapping({"/","/main"})
	public String index(HttpServletRequest request, Model model) {
		SiteVo vo= siteService.getSite();
		model.addAttribute("siteVo", vo);
		return "main/index";
	}
}
