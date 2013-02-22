<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>

<html>
  <head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <title>Hello App Engine</title>
  </head>

  <body>
   <%
   UserService userService = UserServiceFactory.getUserService();
   User user = userService.getCurrentUser();
	if (user != null) {
		response.sendRedirect("/dashboard/dashboard.jsp");
	}else {
		response.sendRedirect(userService.createLoginURL("/index.jsp"));
	}
   %>
  </body>
</html>
