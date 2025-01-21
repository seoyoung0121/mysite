package mysite.exception;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mysite.dto.JsonResult;

@ControllerAdvice
public class GlobalExceptionHandler {
	private static final Log log = LogFactory.getLog(GlobalExceptionHandler.class);
	
	@ExceptionHandler(Exception.class)
	public void handler(HttpServletRequest request, HttpServletResponse response, Exception e) throws Exception{
		// 1. logging
		StringWriter errors = new StringWriter();
		e.printStackTrace(new PrintWriter(errors));
		log.error(errors.toString());
		
		// 2. 요청 구분
		// json 요청:request header의 accept: applicaion/json (o)
		// html 요청: request header의 accept: applicaion/json (x)
		
		String accept = request.getHeader("accept");
		
		if(accept.matches(".*applicaion/json.*")) {
			// 3. JSON 응답 
			JsonResult jsonResult = JsonResult.fail(e instanceof NoHandlerFoundException? "Unknown API URL" : errors.toString());
			String jsonString = new ObjectMapper().writeValueAsString(jsonResult);
			response.setStatus(HttpServletResponse.SC_OK);
			response.setContentType("application/json; chatset=utf-8");
			OutputStream os = response.getOutputStream();
			os.write(jsonString.getBytes("utf-8"));
			os.close();
			return;
		} 
		
		// 4. HTML 응답: 사과 페이지(종료)
		if(e instanceof NoHandlerFoundException) {
			request.getRequestDispatcher("/WEB-INF/views/errors/404.jsp").forward(request, response);
		}
		request.setAttribute("errors", errors.toString());
		request.getRequestDispatcher("/WEB-INF/views/errors/exception.jsp").forward(request, response);
		
	}
}
