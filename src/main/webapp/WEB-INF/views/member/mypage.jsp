<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>PCOOP!</title>
<jsp:include page="../header/cdn.jsp"></jsp:include>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<style>
.row {
	margin-top: 10px;
}
.container{
 	font-family: 'Noto Sans KR', sans-serif; 
}
.modifymodal{
	top:100%;
	position:fixed;
	background: #fff;
	border:1px solid red;
	width:100%;
	height:100%;
	transition: all 600ms cubic-bezier(0.86, 0, 0.07, 1);
	margin:0;
	font-family: 'Noto Sans KR', sans-serif;
}
.modal-open{
	top:0;
}
</style>
</head>
<body>
	
		<div class="container">
		<header>
			<nav class="navbar navbar-expand-lg navbar-light bg-light">
				<a class="navbar-brand" href="#">Navbar</a>
				<button class="navbar-toggler" type="button" data-toggle="collapse"
					data-target="#navbarNav" aria-controls="navbarNav"
					aria-expanded="false" aria-label="Toggle navigation">
					<span class="navbar-toggler-icon"></span>
				</button>
				<div class="collapse navbar-collapse" id="navbarNav">
					<ul class="navbar-nav">
						<li class="nav-item active"><a class="nav-link" href="/">Home
								<span class="sr-only">(current)</span>
						</a></li>
						<li class="nav-item"><a class="nav-link" href="#">Features</a>
						</li>
						<li class="nav-item"><a class="nav-link" href="#">Pricing</a>
						</li>
						<li class="nav-item"><a class="nav-link disabled" href="#">Disabled</a>
						</li>
					</ul>
				</div>
			</nav>
			
			</header>
			<section>

		<div class="row">
			<!-- ㅡㅡㅡㅡㅡㅡㅡㅡㅡ회원 정보-->
			
			<div class="col-4-sm">
			<div class="row p-1">
				<div class="col-12"></div>
			</div>
				<div class="row">
					
					<div class="col-4">e-mail</div>
					<div class="col-5">${loginInfo.email}</div>
					<div class="col-3"></div>
				</div>
				<div class="row">
					
					<div class="col-4">name</div>
					<div class="col-5">${loginInfo.name}</div>
					<div class="col-3"></div>
				</div>
				<div class="row">
					<div class="col-4">password</div>
					<div class="col-5">
						<input type="password" readonly value="${loginInfo.pw}">
					</div>
					<div class="col-3"></div>
				</div>
				<div class="row">
					<div class="col-2"></div>
					<div class="col-3"></div>
					<div class="col-5">
						<button id="modifybtn">수정</button>
						<button>탈퇴</button>
					</div>
					<div class="col-2"></div>
				</div>
				
			</div>
			<!-- ㅡㅡㅡㅡㅡㅡㅡㅡㅡ프로젝트 카드  -->

			<div class="col-8">
				<div class="row">
				<c:choose>
					<c:when test='${list_size ==0}'>
					<div class="col-4-sm m-5">
					</div>
						<div class="col-8-sm m-5">
					아직 참여한 프로젝트가 없습니다.. <br>
					프로젝트를 생성하거나 참여해보세요 !
					</div>
					</c:when>
					<c:otherwise>
						<c:forEach var="i" items="${list}">
						<div class="col-6-sm">
							<div class="row">
							<div class="col-sm-10">
								<div class="card" style='width: 20rem;'>
									<div class="card-body">
										<h5 class="card-title">${i.name}</h5>
										<p class="card-text">${i.code}</p>
										<a href="#" class="btn btn-primary btn-sm">프로젝트 바로가기</a> <a href="#"
											class="btn btn-primary btn-sm">프로젝트 나가기</a>
									</div>
								</div>
							</div>
							<div class="col-sm-2"></div>
						</div>
						</div>
					</c:forEach>
					</c:otherwise>
				</c:choose>
					
				</div>
			</div>
		</div>
	</section>
		</div>

<!-- 수정하기  -->
<div class="row modifymodal">
			<div class="col-sm-2">
				
			</div>
			<div class="col-sm-8">
				
					<div class="row">
					<div class="col-4">e-mail</div>
					<div class="col-5">${loginInfo.email}</div>
					<div class="col-3"></div>
				</div>
				<div class="row">
					<div class="col-4">name</div>
					<div class="col-5"><input type="text" id="modifyname"></div>
					<div class="col-3"></div>
				</div>
				<div class="row">
					<div class="col-4">password</div>
					<div class="col-5">
						<input type="password" value="${loginInfo.pw}" id="modifypw">
					</div>
					<div class="col-3"></div>
				</div>
				<div class="row">
					<div class="col-2"></div>
					<div class="col-3"></div>
					<div class="col-5">
						<button id="cancle">취소</button>
						<button id="save">저장</button>
					</div>
			</div>
				
				
			
			</div>
			<div class="col-sm-2">
				
			</div>
		</div>
		
		<!-- 탈퇴 : 프로젝트 나가기   -->
		

		<script>
			$("#modifybtn").on("click",function(){
				$(".modifymodal").addClass('modal-open');
				$("#modifyname").val('${loginInfo.name}');
				$("#modifypw").val(""); 
			})
			$("#cancle").on("click",function(){
				$(".modifymodal").removeClass('modal-open');
			})
			$("#save").on("click",function(){
				var name = $("#modifyname").val();
				var pw = $("#modifypw").val();
				 if(name!=''&&pw!=''){
					$.ajax({
						url:"modify",
						type:"post",
						data:{
							name:name,
							pw:pw
						}
					}).done(function(resp){
						if(resp==1){
							alert("저장되었습니다.")
							$(".modifymodal").removeClass('modal-open');
						}
					}) 
				}else{
					alert("이름과 비밀번호는 필수 입력입니다.")
				} 
				
			})
		</script>
	
	
</body>
</html>