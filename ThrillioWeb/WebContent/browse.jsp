<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>thrill.io</title>
<style ></style>
</head>
<body 
>
	<div style="align: center; background: #DB5227; font-family: Arial; color: white;">
		<br>
		<b> <a href=""
			style="font-family: garamond; font-size: 2rem; margin: 0px 0px 0px 10px; color: white; text-decoration: none;">thrill.io</a></b>
		<div  style="margin-left:80%;margin-bottom:10px">
			<a href="<%=request.getContextPath()%>/bookmark/mybooks"
				style= "text-decoration: none; color: white;margin-right:5px;border-bottom:1px dashed green;">My Books</a>
			<a href="<%=request.getContextPath()%>/auth/logout" style=" text-decoration:none;color:white;border-bottom:1px dashed green;">LogOut</a>		
		</div>
	</div>
	<br>
	<br>

	<table>
		<c:forEach var="book" items="${books}">
			<tr>
				<td><img src="${book.imageUrl}" width="175" height="200">
				</td>

				<td style="color: gray;">By <span style="color: #B13100;">${book.author[0]}</span>
					<br>
				<br> Rating: <span style="color: #B13100;">${book.amazonRating}</span>
					<br>
				<br> Publication Year: <span style="color: #B13100;">${book.publicationYear}</span>
					<br>
				<br> <a
					href="<%=request.getContextPath()%>/bookmark/save?bid=${book.id}"
					style="font-size: 18px; color: #0058A6; font-weight: bold; text-decoration: none">Save</a>
				</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
			</tr>

		</c:forEach>

	</table>

</body>
</html>