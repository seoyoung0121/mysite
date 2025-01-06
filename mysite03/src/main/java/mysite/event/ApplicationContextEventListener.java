package mysite.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.context.ApplicationContext;

import mysite.service.SiteService;

public class ApplicationContextEventListener {
	   @Autowired
	   private ApplicationContext applicationContext;

	   @EventListener({ContextRefreshedEvent.class})
	   public void handlerContextRefreshedEvent() {
	      SiteService siteService = applicationContext.getBean(SiteService.class);
	      System.out.println("-- ContextRefreshedEvent Received --" + siteService);
	   }
	}

