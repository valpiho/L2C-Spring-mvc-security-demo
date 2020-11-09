<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<html>
<head>
	<title>Pibox Company Home Page</title>
</head>

<body>
	<h2>Pibox Company Home Page</h2>
	<hr>


	<p>Welcome to the Pibox company home page!</p>
	<hr>

	<security:authorize access="isAuthenticated()">
		<p>
			User: <security:authentication property="principal.username" />
			<br><br>
			Role(s): <security:authentication property="principal.authorities" />
		</p>
	</security:authorize>
	<security:authorize access="hasRole('MANAGER')">
		<p>
			<a href="${pageContext.request.contextPath}/leaders">LeaderShip Meeting</a>
			(Only for Manager peeps)
		</p>
	</security:authorize>
	<security:authorize access="isAuthenticated()">
		<form:form action="${pageContext.request.contextPath}/logout" method="POST">
			<input type="submit" value="Logout">
		</form:form>
	</security:authorize>
</body>

</html>
