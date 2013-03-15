<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <%
   UserService userService = UserServiceFactory.getUserService();
   User user = userService.getCurrentUser();
	if (user != null) {
		
	}else {
		response.sendRedirect(userService.createLoginURL("../index.jsp"));
	}
   %>
  <head>
    <script src="js/jquery.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="js/script.js"></script>
    <title>Create new session</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta content="text/html; charset=UTF-8" http-equiv="Content-Type">
    <link rel="stylesheet" href="themes/spacelab/bootstrap.min.css">
    <link rel="stylesheet" href="css/bootstrap-responsive.css">
    <script type="text/javascript">
    
      $(function() {
        getTags("en", "tagsEN");
        getTags("tr", "tagsTR");
      });

      $(document).ready(function() {
        var clickedID;
        var activeLang = "en";
        
        $('#btnShowTrTable').click(function(){
          $('#tagsEN').hide();
          $('#tagsTR').show();
          activeLang = "tr";
          });
        
        $('#btnShowEnTable').click(function(){
          $('#tagsEN').show();
          $('#tagsTR').hide();
          activeLang = "en";
          });
        
        $(document).on("click", "#btnPostTags", function(){
          createSession($('#language').val(), $('#day').val(), $('#startHour').val(), $('#endHour').val(), $('#hall').val(), $('#chkBreak').is(":checked"), $('#speaker1').val(), $('#speaker2').val(), $('#speaker3').val(), $('#title').val(),  $('#description').val());      
          $("#updateModal").modal('hide');
          });

      });
    </script>
  </head>
<body>
   <div class="container-fluid">
      <div class="navbar navbar-top">
        <div class="navbar-inner">
          <div class="container">
            <div class="pull-left">
              <ul class="nav pull-left">
                <li class="dropdown">
                  <a href="#" class="dropdown-toggle" data-toggle="dropdown">Sessions<b class="caret"></b></a>
                  <ul class="dropdown-menu">
                    <li>
                      <a href="#" id="showCreateModal">Create</a> 
                    </li>
                    <li class="divider"></li>
                  </ul>
                </li>
                <li><a href="./speakers.jsp">Speakers</a></li>
              </ul>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="container"><span class="divider"> </span></div>
    <div class="container">
      <span class="divider"> </span>
      <div class="row">
        <div class="span12">
          <div class="well">
            <div class="btn-toolbar">
              <button class="btn" id="btnShowEnTable">EN</button>
              <button class="btn" id="btnShowTrTable">TR</button>
            </div>
            <div class="well">
            	<textarea class="span8" placeholder="Description" name="Tags" id="tagsEN"></textarea>
            	<textarea class="span8" placeholder="Description" name="Etiketler" id="tagsTR"></textarea>
            	<button class="btn btn-warning" id="btnPostTags">Save</button>
            </div>
          </div>
        </div>
      </div>
    </div>
</body>
</html>