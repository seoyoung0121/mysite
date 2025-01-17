package mysite.config.app;



import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
@EnableWebSecurity
public class SecurityConfig implements WebMvcConfigurer{
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	    http
	    	.formLogin((formLogin)->{
	    		formLogin.loginPage("/user/login");
	    	})
	    	.authorizeHttpRequests((authorizeRequests)->{
	    		/* ACL */
	    		authorizeRequests
	    			.requestMatchers(new RegexRequestMatcher("^/user/update$", null))
	    			.authenticated()
	    			.anyRequest().permitAll(); 
	    	});
    	return http.build();
    }
}
