<%@ taglib  uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Thrill.io</title>
</head>
<body style="font-family: Arial; font-size: 20px;">
<div style="height: 10%; align: center; background: #DB5227; font-family: Arial; color: white;">
		<br>
		<b> <a href=""
			style="font-family: garamond; font-size: 2rem; margin: 0px 0px 0px 10px; color: white; text-decoration: none;">thrill.io</a></b>
		<div  style="margin-left:80%;margin-bottom:10px">
			<a href="<%=request.getContextPath()%>/bookmark"
				style= "text-decoration: none; color: white;margin-right:5px;border-bottom:1px dashed green;">Browse</a>
			<a href="<%=request.getContextPath()%>/auth/logout" style=" text-decoration:none;color:white;border-bottom:1px dashed green;">LogOut</a>		
		</div>
</div>

<div style="font-size: 24px;color: #333333;padding: 15px 0px 0px;border-bottom: #333333 1px solid;clear: both;">Saved Items</div>
	<div style="margin-top: 20px;">
		<c:choose>
		<c:when test="${!empty(books)}">
		<table>
		<c:forEach var="book" items= "${books}">
		
		<tr>
				<td><img src="${book.imageUrl}" width="175" height="200">
				</td>

				<td style="color: gray;">By <span style="color: #B13100;">${book.author[0]}</span>
					<br>
				<br> Rating: <span style="color: #B13100;">${book.amazonRating}</span>
					<br>
				<br> Publication Year: <span style="color: #B13100;">${book.publicationYear}</span>
					<br>
<!-- 				<br> <a -->
<%-- 					href="<%=request.getContextPath()%>/bookmark/save?bid=${book.id}" --%>
<!-- 					style="font-size: 18px; color: #0058A6; font-weight: bold; text-decoration: none">Save</a> -->
				</td>
			</tr>
		</c:forEach>
		
		</table>
		</c:when>
		<c:otherwise>
		<div>You don't have any bookmark saved yet</div>
		</c:otherwise>
		</c:choose>

	</div>


</body>
</html>