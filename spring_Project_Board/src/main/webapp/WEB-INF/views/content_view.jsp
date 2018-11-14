<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<link href="<c:url value="./resources/css/common.css"/>" rel="stylesheet">
<script src="<c:url value="./resources/js/commonJS.js"/>"></script>
<body>
	<table border="1" class="table">
		<form action="modify" method="post" id="form">
			<input type="hidden" name="bId" value="${content_view.bId }">
			<tr>
				<td>번호</td>
				<td>${content_view.bId }</td>
			</tr>
			<tr>
				<td>조회수</td>
				<td>${content_view.bHit }</td>
			</tr>
			<tr>
				<td>이름</td>
				<td><input type="text" name="bName" value="${content_view.bName }"></td>
			</tr>
			<tr>
				<td>제목</td>
				<td><input type="text" name="bTitle" value="${content_view.bTitle }"></td>
			</tr>
			<tr>
				<td>내용</td>
				<td><textarea name="bContent" rows="10">${content_view.bContent }</textarea></td>
			</tr>
			<tr>
				<td colspan="2"> <input type="submit" value="수정"> &nbsp;&nbsp; <a href="list">목록보기</a>&nbsp;&nbsp; <a href="javascript:void(0);" onclick="boardDelete(event, ${content_view.bId});">삭제</a>&nbsp;&nbsp; <a href="reply_view?bId=${content_view.bId}">답변</a></td>
			</tr>
		</form> 
	</table>

</body>
</html>