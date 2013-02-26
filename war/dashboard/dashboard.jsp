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
        getSessions("en", "tbodyEN");
        getSessions("tr", "tbodyTR");
        
        //TODO israf yapma bi kere gelen veriyi tekrar tekrar kullan
        getSpeakers("speaker1");
        getSpeakers("speaker2");
        getSpeakers("speaker3");
        //
      });

      $(document).ready(function() {
        var clickedID;
        var activeLang = "en";
        
        $('#btnShowTrTable').click(function(){
          $('#tbodyEN').hide();
          $('#tbodyTR').show();
          activeLang = "tr";
          });
        
        $('#btnShowEnTable').click(function(){
          $('#tbodyEN').show();
          $('#tbodyTR').hide();
          activeLang = "en";
          });
        
        $(document).on('click', '#showCreateModal', function(){
          $('#btnUpdateSession').hide();
          $('#btnCreateSession').show();
          $('#language').show();
          
          $('#updateModal').modal('show');
          
          $('#title').val("");
          $('#speaker1').val(0);
          $('#speaker2').val(0);
          $('#speaker3').val(0);
          $('#hall').val(0);
          $('#day').val(0);
          $('#startHour').val(0);
          $('#endHour').val(0);
          $('#description').val("");
          $('#chkBreak').prop("checked",false);
          
          });
        
        $(document).on("click", "#btnShowUpdateModal", function () {
          $('#btnUpdateSession').show();
          $('#btnCreateSession').hide();
          $('#language').hide();
          
          var id = $(this).data("id");
          clickedID = $(this).data("id");
          
          $("#title").val($('#'+id+'>td').eq(1).html());
          
          var speakers = $('#'+id+'>td').eq(2).html().split(',');
          for(i=0;i<3;i++){
            if(speakers[i] == "break"){
              $('#chkBreak').prop("checked",true);
            }else if(speakers[i] != "" || speakers[i] != null){
              j = i + 1;
              $("#speaker" + j).val(speakers[i]);
            }
          };
          
          var hall = $('#'+id+'>td').eq(3).html();
          $("#hall option").each(function(){
            this.selected = (this.text == hall);
            });
          
          var day = $('#'+id+'>td').eq(4).html();
          $("#day option").each(function(){
            this.selected = (this.text == day);
            });
          
          var startHour = $('#'+id+'>td').eq(5).html();
          $("#startHour option").each(function(){
            this.selected = (this.text == startHour);
            });
          
          var endHour = $('#'+id+'>td').eq(6).html();
          $("#endHour option").each(function(){
            this.selected = (this.text == endHour);
            });
          
          $('#description').val($('#' + id + '>td').eq(7).html());
          
          $('#updateModal').modal('show');
        });

        $(document).on("click", "#btnShowDeleteModal", function(){
          $('#deleteModal').modal('show');
          clickedID = $(this).data("id");
          });

        $(document).on("click", "#btnCreateSession", function(){
          createSession($('#language').val(), $('#day').val(), $('#startHour').val(), $('#endHour').val(), $('#hall').val(), $('#chkBreak').is(":checked"), $('#speaker1').val(), $('#speaker2').val(), $('#speaker3').val(), $('#title').val(),  $('#description').val());      
          $("#updateModal").modal('hide');
          });
        
        $(document).on("click", "#btnUpdateSession", function(){          
          updateSession(clickedID, activeLang, $('#day').val(), $('#startHour').val(), $('#endHour').val(), $('#hall').val(), $('#chkBreak').is(":checked"), $('#speaker1').val(), $('#speaker2').val(), $('#speaker3').val(), $('#title').val(),  $('#description').val());   
          $("#updateModal").modal('hide');
          });
        
        $(document).on('click', "#btnDeleteSession", function(){
          deleteSession(clickedID);
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
                <table class="table">
                  <thead>
                    <tr>
                      <th>ID</th>
                      <th>Title</th>
                      <th>Speaker/Break</th>
                      <th>Hall</th>
                      <th>Day</th>
                      <th>Start Hour</th>
                      <th>End Hour</th>
                    </tr>
                  </thead>
                  <tbody id="tbodyEN">
                  </tbody>
                  <tbody id="tbodyTR">
                  </tbody>
                </table>
            </div>
            
            <div class="modal large hide fade" id="updateModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                    <h3 id="myModalLabel">Update</h3>
                </div>
                <div class="modal-body">
                      <div id="updateForm">
                      <input class="span4" type="text" name="title" id="title" value="">
                      <select class="span4" name="language" id="language">
                        <option value="en">English</option>
                        <option value="tr">Türkçe</option>
                      </select>
                      <input class="span4" type="checkbox" value="" id="chkBreak" name="checkbox">is Coffee Time?</input>
                      <select class="span4" name="speaker" id="speaker1">
                        <option></option>
                      </select>
                      <select class="span4" name="speaker" id="speaker2">
                        <option></option>
                      </select>
                      <select class="span4" name="speaker" id="speaker3">
                        <option></option>
                      </select>
                      <select class="span4" name="day" id="day">
                        <option>14 Haziran 2013 Cuma</option>
                        <option>15 Haziran 2013 Cumartesi</option>
                        <option>14 June 2013 Friday</option>
                        <option>15 June 2013 Saturday</option>
                      </select>
                      <select class="span4" name="hall" id="hall">
                        <option>A</option>
                        <option>B</option>
                      </select>
                      <div class="control-group">
                        <label class="control-label">Start Hour - End Hour</label>
                        <div class="controls">
                          <select class="span2" name="startHour" id="startHour">
                            <option>09:00</option>
                            <option>09:15</option>
                            <option>09:30</option>
                            <option>09:45</option>
                            <option>10:00</option>
                            <option>10:15</option>
                            <option>10:30</option>
                            <option>10:45</option>
                            <option>11:00</option>
                            <option>11:15</option>
                            <option>11:30</option>
                            <option>11:45</option>
                            <option>12:00</option>
                            <option>12:15</option>
                            <option>12:30</option>
                            <option>12:45</option>
                            <option>13:00</option>
                            <option>13:15</option>
                            <option>13:30</option>
                            <option>13:45</option>
                            <option>14:00</option>
                            <option>14:15</option>
                            <option>14:30</option>
                            <option>14:45</option>
                            <option>15:00</option>
                            <option>15:15</option>
                            <option>15:30</option>
                            <option>15:45</option>
                            <option>16:00</option>
                            <option>16:15</option>
                            <option>16:30</option>
                            <option>16:45</option>
                            <option>17:00</option>
                            <option>17:15</option>
                            <option>17:30</option>
                            <option>17:45</option>
                            <option>18:00</option>
                            <option>18:15</option>
                            <option>18:30</option>
                            <option>18:45</option>
                            <option>19:00</option>
                            <option>19:15</option>
                            <option>19:30</option>
                            <option>19:45</option>
                            <option>20:00</option>
                          </select>
                          <select class="span2" name="endHour" id="endHour">
                            <option>09:00</option>
                            <option>09:15</option>
                            <option>09:30</option>
                            <option>09:45</option>
                            <option>10:00</option>
                            <option>10:15</option>
                            <option>10:30</option>
                            <option>10:45</option>
                            <option>11:00</option>
                            <option>11:15</option>
                            <option>11:30</option>
                            <option>11:45</option>
                            <option>12:00</option>
                            <option>12:15</option>
                            <option>12:30</option>
                            <option>12:45</option>
                            <option>13:00</option>
                            <option>13:15</option>
                            <option>13:30</option>
                            <option>13:45</option>
                            <option>14:00</option>
                            <option>14:15</option>
                            <option>14:30</option>
                            <option>14:45</option>
                            <option>15:00</option>
                            <option>15:15</option>
                            <option>15:30</option>
                            <option>15:45</option>
                            <option>16:00</option>
                            <option>16:15</option>
                            <option>16:30</option>
                            <option>16:45</option>
                            <option>17:00</option>
                            <option>17:15</option>
                            <option>17:30</option>
                            <option>17:45</option>
                            <option>18:00</option>
                            <option>18:15</option>
                            <option>18:30</option>
                            <option>18:45</option>
                            <option>19:00</option>
                            <option>19:15</option>
                            <option>19:30</option>
                            <option>19:45</option>
                            <option>20:00</option>
                          </select>
                        </div>
                      </div>
                      <textarea class="span4" placeholder="Description" name="description" id="description"></textarea>
                  </div>
                </div>
                <div class="modal-footer">
                    <button class="btn" data-dismiss="modal" aria-hidden="true">Cancel</button>
                    <button class="btn btn-warning" id="btnUpdateSession">Save</button>
                    <button class="btn btn-warning" id="btnCreateSession">Create</button>
                </div>
            </div>
            <div class="modal large hide fade" id="deleteModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                    <h3 id="myModalLabel">Delete Confirmation</h3>
                </div>
                <div class="modal-body">
                    <p class="error-text">Are you sure you want to delete the user?</p>
                </div>
                <div class="modal-footer">
                    <button class="btn" data-dismiss="modal" aria-hidden="true">Cancel</button>
                    <button class="btn btn-danger" id="btnDeleteSession">Delete</button>
                </div>
            </div>
          </div>
        </div>
      </div>
    </div>
</body>
</html>