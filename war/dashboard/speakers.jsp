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
    <title>Create new speaker</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta content="text/html; charset=UTF-8" http-equiv="Content-Type">
    <link rel="stylesheet" href="themes/spacelab/bootstrap.min.css">
    <link rel="stylesheet" href="css/bootstrap-responsive.css">
    <script type="text/javascript">
    
      $(function() {
        $.ajax({
          type: "GET",
          url: "http://"+window.location.host+"/api/speakers",
          success: function(response){
            $.each(response.speakers, function(index, speaker) {
               $('#tbody').append('<tr id="'+speaker.id+'"><td>' + speaker.id + '</td><td>' + speaker.name + '</td><td>' + speaker.blog + '</td><td>' + speaker.facebook + '</td><td>' + speaker.gplus + '</td><td>' + speaker.twitter + '</td><td>' + speaker.lang + '</td><td style="display:none">' + speaker.bio +'</td><td style="display:none">' + speaker.photo +'</td><td><button id="btnShowUpdateModal"  data-id="'+speaker.id+'" class="btn btn-mini btn-warning" ><i class="icon-pencil"></i></button> <button class="btn btn-mini btn-danger" data-id="'+speaker.id+'" id="btnShowDeleteModal"><i class="icon-remove"></i></button></td></tr>');
            });
          }
        });
      });

      $(document).ready(function() {
        var clickedID;
                
        $(document).on('click', '#showCreateModal', function(){
          $('#btnUpdateSpeaker').hide();
          $('#btnCreateSpeaker').show();
          $('#language').show();
          
          $('#updateModal').modal('show');
          
          //TODO needs refactor, doesn't work
          $('#title').val("");
          $('#speaker option').eq(0).selected;
          $('#hall option').eq(0).selected;
          $('#day option').eq(0).selected;
          $('#startHour option').eq(0).selected;
          $('#endHour option').eq(0).selected;
          $('#description').val("");
          
          });
        
        $(document).on("click", "#btnShowUpdateModal", function () {
          $('#btnUpdateSpeaker').show();
          $('#btnCreateSpeaker').hide();
          $('#language').hide();
          
          var id = $(this).data("id");
          clickedID = $(this).data("id");
          
          $("#name").val($('#'+id+'>td').eq(1).html());
          $("#blog").val($('#'+id+'>td').eq(2).html());
          $("#facebook").val($('#'+id+'>td').eq(3).html());
          $("#gplus").val($('#'+id+'>td').eq(4).html());
          $("#twitter").val($('#'+id+'>td').eq(5).html());
          $("#bio").val($('#'+id+'>td').eq(7).html());
          $("#photo").val($('#'+id+'>td').eq(8).html());
          
          var language = $('#'+id+'>td').eq(6).html();          
          $("#language option").each(function() { 
            this.selected = (this.text == language); 
            });
          
          $('#updateModal').modal('show');
        });

        $(document).on("click", "#btnShowDeleteModal", function(){
          $('#deleteModal').modal('show');
          clickedID = $(this).data("id");
          });

        $(document).on("click", "#btnCreateSpeaker", function(){
          createSpeaker($('#bio').val(),$('#blog').val(),$('#facebook').val(),$('#gplus').val(),$('#language').val(),$('#name').val(),$('#photo').val(),$('#twitter').val());
          $("#updateModal").modal('hide');
          });
        
        $(document).on("click", "#btnUpdateSpeaker", function(){  
          updateSpeaker(clickedID, $('#bio').val(),$('#blog').val(),$('#facebook').val(),$('#gplus').val(),$('#language').val(),$('#name').val(),$('#photo').val(),$('#twitter').val());        
          $("#updateModal").modal('hide');
          
          });
        
        $(document).on('click', "#btnDeleteSpeaker", function(){
          deleteSpeaker(clickedID);
          $('#deleteModal').modal('hide');
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
              	<li><a href="/dashboard.jsp">Seasons</a></li>
                <li class="dropdown">
                  <a href="#" class="dropdown-toggle" data-toggle="dropdown">Speakers<b class="caret"></b></a>
                  <ul class="dropdown-menu">
                    <li>
                      <a href="" id="showCreateModal">Create</a> 
                    </li>
                    <li class="divider"></li>
                  </ul>
                </li>
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
            <div class="well">
                <table class="table">
                  <thead>
                    <tr>
                      <th>ID</th>
                      <th>Name</th>
                      <th>Blog</th>
                      <th>Facebook</th>
                      <th>Gplus</th>
                      <th>Twitter</th>
                      <th>Language</th>
                    </tr>
                  </thead>
                  <tbody id="tbody">
                  </tbody>
                </table>
            </div>
            
            <div class="modal large hide fade" id="updateModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">Ã</button>
                    <h3 id="myModalLabel">Update</h3>
                </div>
                <div class="modal-body">
                      <div id="updateForm">
                      <select class="span4" name="language" id="language">
                        <option value="en">English</option>
                        <option value="tr">TÃ¼rkÃ§e</option>
                      </select>
                      <input class="span4" type="text" placeholder="Name" name="name" id="name" value="">
                      <input class="span4" type="text" placeholder="Blog" name="blog" id="blog" value="">
                      <input class="span4" type="text" placeholder="Facebook" name="facebook" id="facebook" value="">
                      <input class="span4" type="text" placeholder="Gplus" name="gplus" id="gplus" value="">
                      <input class="span4" type="text" placeholder="Twitter" name="twitter" id="twitter" value="">
                      <input class="span4" type="text" placeholder="Photo" name="photo" id="photo" value="">
                      <textarea class="span4" placeholder="Bio" name="bio" id="bio"></textarea>
                  </div>
                </div>
                <div class="modal-footer">
                    <button class="btn" data-dismiss="modal" aria-hidden="true">Cancel</button>
                    <button class="btn btn-warning" id="btnUpdateSpeaker">Save</button>
                    <button class="btn btn-warning" id="btnCreateSpeaker">Create</button>
                </div>
            </div>
            <div class="modal large hide fade" id="deleteModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">Ã</button>
                    <h3 id="myModalLabel">Delete Confirmation</h3>
                </div>
                <div class="modal-body">
                    <p class="error-text">Are you sure you want to delete the user?</p>
                </div>
                <div class="modal-footer">
                    <button class="btn" data-dismiss="modal" aria-hidden="true">Cancel</button>
                    <button class="btn btn-danger" id="btnDeleteSpeaker">Delete</button>
                </div>
            </div>
          </div>
        </div>
      </div>
    </div>
</body>
</html>