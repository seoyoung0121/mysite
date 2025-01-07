package mysite.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mysite.service.SiteService;
import mysite.vo.SiteVo;

public class TitleInterceptor implements HandlerInterceptor {
	private SiteService siteService;
	
	@Autowired
	private ServletContext servletContext;
	
	public TitleInterceptor(SiteService siteService) {
		this.siteService=siteService;
	}
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		SiteVo vo= siteService.getSite();
		servletContext.setAttribute("siteVo", vo);
		return true;
		
	}
}
