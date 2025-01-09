<%@ taglib uri="jakarta.tags.core" prefix ="c" %>
<%@ taglib uri="jakarta.tags.fmt" prefix ="fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.request.contextPath}/assets/css/user.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp"/>
		<div id="content">
			<div id="user">

				<form:form 
					modelAttribute="userVo"
					id="join-form" 
					name="joinForm" 
					method="post" 
					action="${pageContext.request.contextPath}/user/join">
					<label class="block-label" for="name"><spring:message code="user.join.label.name"/></label>
					<form:input path="name"/>
					<p style="padding: 5px 0; margin:0; color:#f00">
						<form:errors path="name"/>
					</p>
					
					<label class="block-label" for="email"><spring:message code="user.join.label.email"/></label>
					<form:input path="email"/>
					<p style="padding: 5px 0; margin:0; color:#f00">
						<form:errors path="email"/>
					</p>
					<spring:message code="user.join.label.email.check" var="idCheck"/>
					<input type="button" value="${idCheck}">
					<img id = "img-check" src="${pageContext.request.contextPath}/assets/images/check.png" style="vertical-align: bottom; width:24px;">
					
					<label class="block-label"><spring:message code="user.join.label.password"/></label>
					<form:password path="password"/>
					<p style="padding: 5px 0; margin:0; color:#f00">
						<form:errors path="password"/>
					</p>
					
					<fieldset>
						<legend><spring:message code="user.join.label.gender"/></legend>
						<label><spring:message code="user.join.label.gender.female"/></label> <form:radiobutton path="gender" value="female" checked="checked"/>
						<label><spring:message code="user.join.label.gender.male"/></label> <form:radiobutton path="gender" value="male"/>
					</fieldset>
					
					<fieldset>
						<legend><spring:message code="user.join.label.terms"/></legend>
						<input id="agree-prov" type="checkbox" name="agreeProv" value="y">
						<label><spring:message code="user.join.label.terms.message"/></label>
					</fieldset>
					
					<spring:message code="user.join.button.signup" var="userSignupButtonText"/>
					<input type="submit" value="${userSignupButtonText}">
					
				</form:form>
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp"/>
		<c:import url="/WEB-INF/views/includes/footer.jsp"/>
	</div>
</body>
</html>