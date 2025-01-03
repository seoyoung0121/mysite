package mysite.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import mysite.security.Auth;
import mysite.service.FileUploadService;
import mysite.service.SiteService;
import mysite.vo.SiteVo;

@Auth(role="ADMIN")
@Controller
@RequestMapping("/admin")
public class AdminController {
	private SiteService siteService;
	private FileUploadService fileUploadService;
	
	public AdminController(SiteService siteService, FileUploadService fileUploadService) {
		this.siteService=siteService;
		this.fileUploadService=fileUploadService;
	}
	
	@RequestMapping({"", "/main"})
	public String main(Model model) {
		SiteVo vo= siteService.getSite();
		model.addAttribute("siteVo", vo);
		return"admin/main";
	}
	
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public String updateSite(SiteVo vo, @RequestParam("file") MultipartFile file) {
	    
		if(!file.isEmpty()) {
			vo.setProfile(fileUploadService.restore(file));
		}
		siteService.updateSite(vo);
		return"redirect:/";
	}
	
	@RequestMapping("/guestbook")
	public String guestbook() {
		return "admin/guestbook";
	}
	
	@RequestMapping("/board")
	public String board() {
		return "admin/board";
	}
	@RequestMapping("/user")
	public String user() {
		return "admin/user";
	}
	
}
