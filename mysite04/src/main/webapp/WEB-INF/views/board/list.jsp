<%@ taglib uri="jakarta.tags.core" prefix ="c" %>
<%@ taglib uri="jakarta.tags.fmt" prefix ="fmt" %>
<%@ taglib uri="jakarta.tags.functions" prefix ="fn" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.request.contextPath}/assets/css/board.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp"/>
		<div id="content">
			<div id="board">
				<form id="search_form" action="" method="post">
					<input type="text" id="kwd" name="kwd" value="">
					<input type="submit" value="찾기">
				</form>
				<table class="tbl-ex">
					<tr>
						<th>번호</th>
						<th>제목</th>
						<th>글쓴이</th>
						<th>조회수</th>
						<th>작성일</th>
						<th>&nbsp;</th>
					</tr>	
					<c:forEach items="${list}" var="vo" varStatus="status">
						<tr>
						<td>${startArticleNum-status.index}</td>
						<td style="text-align:left; padding-left:${vo.depth*20}px">
							<c:if test='${vo.depth>0 }'>
								<img src="${pageContext.request.contextPath}/assets/images/reply.png">
							</c:if>
							<a href="${pageContext.request.contextPath}/board/view?id=${vo.id}">${vo.title}</a>
						</td>
						<td>${vo.userName}</td>
						<td>${vo.hit}</td>
						<td>${vo.regDate}</td>
						<td>
						<c:if test="${authUser.id==vo.userId}">
							<a href="${pageContext.request.contextPath}/board/delete?id=${vo.id}" class="del">삭제</a>
						</c:if>
						</td>
					</tr>
					</c:forEach>
				</table>
				
				<!-- pager 추가 -->
				<div class="pager">
					<ul>
						<c:if test="${startPage-1>0}">
							<li><a href="${pageContext.request.contextPath}/board?page=${startPage-1}">◀</a></li>
						</c:if>
						<c:if test="${startPage-1<=0}">
							<li>◀</li>
						</c:if>
						
						
						<c:forEach begin="${startPage}" end="${currentPage-1}" step="1" var="i">
							<li><a href="${pageContext.request.contextPath}/board?page=${i}">${i}</a></li>
						</c:forEach>
						<li class="selected">${currentPage}</li>
						<c:forEach begin="${currentPage+1}" end="${startPage+4}" step="1" var="i">
							<c:if  test="${i<=totalPage}">
								<li><a href="${pageContext.request.contextPath}/board?page=${i}">${i}</a></li>
							</c:if>
							<c:if  test="${i>totalPage}">
								<li>${i}</li>
							</c:if>
						</c:forEach>
						
						
						<c:if test="${startPage+4<totalPage}">
							<li><a href="${pageContext.request.contextPath}/board?page=${startPage+5}">▶</a></li>
						</c:if>
						<c:if test="${startPage+4>=totalPage}">
							<li>▶</li>
						</c:if>
					</ul>
				</div>	
							
				<!-- pager 추가 -->
				
				<c:if test="${not empty authUser}">
					<div class="bottom">
						<a href="${pageContext.request.contextPath}/board/write" id="new-book">글쓰기</a>
					</div>	
				</c:if>			
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp"/>
		<c:import url="/WEB-INF/views/includes/footer.jsp"/>
	</div>
</body>
</html>