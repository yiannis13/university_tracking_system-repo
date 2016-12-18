<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Student Tracker App</title>
<link type="text/css" rel="stylesheet" href="css/style.css" />
</head>


<body>
	<div id="wrapper">
		<div id="header">
			<h2>International Hellenic University</h2>
		</div>
	</div>
	<div id="container">
		<div id="content">

			<!-- Put button: Add Student -->
			<input type="button" value="Add Student"
				onclick="window.location.href='add-student-form.jsp'"
				class="add-student-button" />

			<table>
				<tr>
					<th>First Name</th>
					<th>Last Name</th>
					<th>Email</th>
					<th>Action</th>
				</tr>
				<c:forEach var="std" items="${student_list}">
					<!-- set up url for loading and then updating a student -->
					<c:url var="updateUrl" value="StudentControllerServlet">
						<c:param name="command" value="LOAD" />
						<c:param name="studentId" value="${std.id }" />
					</c:url>

					<!-- set up url for delete a student -->
					<c:url var="deleteUrl" value="StudentControllerServlet">
						<c:param name="command" value="DELETE" />
						<c:param name="studentId" value="${std.id }" />
					</c:url>

					<tr>
						<td>${std.firstname}</td>
						<td>${std.lastname }</td>
						<td>${std.email }</td>
						<td><a href="${updateUrl }">Update</a> | <a
							href="${deleteUrl }"
							onclick="return (confirm('Are you sure you want to delete that student?'))">Delete</a>
						</td>
					</tr>
				</c:forEach>
			</table>
		</div>

	</div>


</body>

</html>