package mysite.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mysite.service.SiteService;
import mysite.vo.SiteVo;

public class TitleInterceptor implements HandlerInterceptor {
	private SiteService siteService;
	
	public TitleInterceptor(SiteService siteService) {
		this.siteService=siteService;
	}
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		SiteVo siteVo=(SiteVo)request.getServletContext().getAttribute("siteVo");
		if(siteVo==null) {
			siteVo= siteService.getSite();
			request.getServletContext().setAttribute("siteVo", siteVo);
		}

		return true;
		
	}
}