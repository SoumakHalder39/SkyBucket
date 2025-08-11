<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Login</title>
<style>
	input{
		border:1px gray solid;
		border-radius:5px;
	}
	
</style>
</head>
<body>


	<div style="height:65px;align: center;background: #DB5227;font-family: Arial;color: white;"">
		<br><b>
		<a href="index.jsp" style="font-family:garamond;font-size:34px;margin:0 0 0 10px;color:white;text-decoration: none;">thrill.io</a></b>          
	</div>
	<div style="width:30%;margin:auto;">
	<form style="text-align:center;" action="<%=request.getContextPath()%>/auth" method="post">
		<fieldset>
		<legend>Log In</legend>
		<table>
			<tr>
			<td><label for="email" >Email:</label></td>
			<td> <input id="email" name="email" type="email" autocomplete="false" required/></td>
			</tr>
						
			<tr>
			 <td><label for="password">Enter  password</label></td>
			 <td><input type="password" name="password"/ required></td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td><input type="submit" name="submitLoginForm" value="Log In"></td>
			</tr>
		
		</table>
		</fieldset>
	</form>	
	</div>

</body>
</html>