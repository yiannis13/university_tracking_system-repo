<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add Student</title>
<link type="text/css" rel="stylesheet" href="css/style.css" />
<link type="text/css" rel="stylesheet" href="css/add-student-style.css" />
</head>

<body>

</body>
<div id="wrapper">
	<div id="header">
		<h2>International Hellenic University</h2>
	</div>
</div>

<div id="container">
	<h3>Add Student</h3>

	<form action="StudentControllerServlet" method="POST">
		<input type="hidden" name="command" value="ADD" />
		<table>
			<tbody>
				<tr>
					<td><label>First name:</label></td>
					<td><input type="text" name="firstName" /></td>
				</tr>
				<tr>
					<td><label>Last name:</label></td>
					<td><input type="text" name="lastName" /></td>
				</tr>
				<tr>
					<td><label>Email:</label></td>
					<td><input type="text" name="email" /></td>
				</tr>
				<tr>
					<td><label></label></td>
					<td><input type="submit" value="Save" class="save" /></td>
				</tr>
			</tbody>
		</table>
	</form>

	<div style="clear: both;"></div>

	<p>
		<a href="StudentControllerServlet">Back to List</a>
	</p>
</div>
</html>