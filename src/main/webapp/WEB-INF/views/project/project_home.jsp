<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>PCOOP!</title>
<jsp:include page="../header/cdn.jsp"></jsp:include>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
</head>
<body>
	<!-- Header -->
	<jsp:include page="../header/header.jsp"></jsp:include>
	<!-- 왼쪽 사이드바 -->
	<jsp:include page="../header/sidebar-left.jsp"></jsp:include>
	
	<section>
	
		<div id="container">
			<!-- 여기부터 각자 영역 설정 -->
				<c:choose>
					<c:when test="${loginInfo.seq==projectInfo.leader_seq}">
						<c:choose>
							<c:when test="${empty list}"></c:when>
							<c:otherwise>
								<div>${fn:length(list)}개의 프로젝트 참여 요청이 있습니다.</div>
								<c:forEach var ="i" items="${list}">
									<div>${i.member_name}/${i.member_email}님</div>
									<button><a href="accept?mem_seq=${i.member_seq}&project_seq=${projectInfo.seq}">수락</a></button><button><a href="refuse?mem_seq=${i.member_seq}&project_seq=${projectInfo.seq}">거절</a></button>
								</c:forEach>
							</c:otherwise>
						</c:choose>
						
					</c:when>
				</c:choose>
			<!-- 여기까지 각자 영역 설정 -->
		</div>
	
	</section>
	
	<script>
		
	</script>
</body>
</html>