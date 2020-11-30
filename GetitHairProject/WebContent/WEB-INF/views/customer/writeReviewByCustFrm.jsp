<%@page import="reserve.model.vo.Reserve"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <% 
    	Reserve reserve = (Reserve)request.getAttribute("reserve");   
    	//int reserve.get
    %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>손님 리뷰작성</title>
</head>
<body>
	<%@ include file="/WEB-INF/views/common/header.jsp"%>
	<div class="table-wrapper" style="width:80%;margin:0 auto;text-align:center;">
			<form action="/insertReviewByCustomer" method="post" enctype="multipart/form-data">
				<!-- hidden영역 -->
				<input type="hidden" name="shopNo" value="<%=reserve.getShop().getShopNo() %>">
				<input type="hidden" name="designerNo" value="<%=reserve.getDesigner() == null ? "탈퇴한 디자이너" : reserve.getDesigner().getDesignerNo() %>">
				<input type="hidden" name="customerNo" value="<%=reserve.getCustomer() == null ? "탈퇴한 회원" : reserve.getCustomer().getCustomerNo() %>">
				<input type="hidden" name="styleNo" value="<%=reserve.getStylelist() == null ? "없는스타일 ": reserve.getStylelist()  %>">
				<table class="table table-bordered">
					<tr>
						<th colspan="2">리뷰 작성</th>
					</tr>
					<tr>
						<th>샵이름</th>
						<td><input type="text" class="form-control" name="shopName" value="<%=reserve.getShop().getShopName()%>" readonly></td>
					</tr>
						<th>디자이너이름</th>
						<td><input type="text" class="form-control" name="designerName" value="<%=reserve.getDesigner().getDesignerName()%>" readonly></td>
					<tr>
					</tr>
						<th>작성자</th>
						<td><input type="text" class="form-control" name="customerName" value="<%=reserve.getCustomer().getCustomerName()%>"readonly></td>
					<tr>
					</tr>
						<th>스타일이름</th>
						<td><input type="text" class="form-control" name="styleName" value="<%=reserve.getStylelist().getStyle().getStyleName()%>" readonly></td>
					<tr>				
					</tr>
						<th>평점</th>
						<td><input type="text" class="form-control" name="reviewRate"></td>
					<tr>
					</tr>
						<th>좋아요(찜)</th>
						<td><input type="checkbox" name="reviewLikes" value=1></td>
					<tr>
					</tr>
						<th>리뷰 내용</th>
						<td>
							<textarea name="reviewContent" class="form-control" rows="3" col="40"></textarea>
						</td>
					<tr>					
					<tr style="text-align:center">
						<th colspan="2">
							<button type="submit" class="btn btn-primary btn-lg">등록하기</button>
						</th>
					</tr>
				</table>
			</form>
		</div>
		
		<input type="file" name="filename">
</body>
</html>