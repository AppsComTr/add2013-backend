$(document).ready(function() {

  $('#btnUpdate').click(function(e) {
    //e.preventDefault();
    var id = 7; //getParameterByName('id', window.location.href);
    log("1", id);
    //updateSession(id, $('#title').val(), $('#speaker').val(), $('#day').val(), $('#hall').val(), $('#start_hour').val(), $('#end_hour').val(), $('#description').val());
    log("2", id);
  });
});

function createButtonClick() {
 
};

function getSpeakers(select){
  $.ajax({
          type: "GET",
          url: "http://"+window.location.host+"/api/speakers",
          success: function(response){
            $.each(response.speaker, function(index, speaker) {
               $('#' + select).append($('<option>', {
                value: speaker.id,
                text: speaker.id + "-" + speaker.lang + " " + speaker.name
                }));
            });
          }
  });
}

function createSpeaker(bio,blog,facebook,gplus,lang,name,photo,twitter){
  $.ajax({
    type: "POST",
    url: "http://"+window.location.host+"/api/speaker/create",
    dataType: "json",
    contentType: 'application/json',
    data: JSON.stringify({
      bio: bio,
      blog: blog,
      facebook: facebook,
      gplus: gplus,
      lang: lang,
      name: name,
      photo: photo,
      twitter: twitter
    }),
    beforeSend: function() {
    },
    success: function(response) {
      log("update", response);
    },
    error: function(response) {
      log("update", response);
    },
    complete: function() {
      location.reload();
    }
  });
}

function updateSpeaker(id,bio,blog,facebook,gplus,lang,name,photo,twitter){
  $.ajax({
    type: "POST",
    url: "http://"+window.location.host+"/api/speaker/update/" + id,
    dataType: "json",
    contentType: 'application/json',
    data: JSON.stringify({
      bio: bio,
      blog: blog,
      facebook: facebook,
      gplus: gplus,
      lang: lang,
      name: name,
      photo: photo,
      twitter: twitter
    }),
    beforeSend: function() {
    },
    success: function(response) {
      log("update", response);
    },
    error: function(response) {
      log("update", response);
    },
    complete: function() {
      location.reload();
    }
  });
}

function deleteSpeaker(id){
  $.ajax({
    type: "DELETE",
    url: "http://"+window.location.host+"/api/speaker/delete/" + id,
    success: function(response){
      log("delete", response);
    },
    complete: function() {
      location.reload();
    }
  });
}

function getSessions(lang, table){
  $.ajax({
          type: "GET",
          url: "http://"+window.location.host+"/api/sessions/" + lang,
          success: function(response){
            $.each(response.session, function(index, session) {
               $('#' + table).append('<tr id="'+session.id+'"><td>' + session.id + '</td><td>' + session.title + '</td><td>' + session.speaker + '</td><td>' + session.hall + '</td><td>' + session.day + '</td><td>' + session.startHour + '</td><td>' + session.endHour + '</td><td style="display:none">' + session.description +'</td><td><button id="btnShowUpdateModal"  data-id="'+session.id+'" class="btn btn-mini btn-warning" ><i class="icon-pencil"></i></button> <button class="btn btn-mini btn-danger" data-id="'+session.id+'" id="btnShowDeleteModal"><i class="icon-remove"></i></button></td></tr>');
            });
          }
        });
  $('#tbodyTR').hide();
}

function createSession(lang, title, speaker, day, hall, start_hour, end_hour, description) {
  $.ajax({
    type: "POST",
    url: "http://"+window.location.host+"/api/session/create",
    dataType: "json",
    contentType: 'application/json',
    data: JSON.stringify({
      title: title,
      speaker: speaker,
      day: day,
      hall: hall,
      startHour: start_hour,
      endHour: end_hour,
      description: description ,
      lang: lang
    }),
    beforeSend: function() {
    },
    success: function(response) {
      log("update", response);
    },
    error: function(response) {
      log("update", response);
    },
    complete: function() {
      location.reload();
    }
  });
}

function updateSession(id, activeLang, title, speaker, day, hall, start_hour, end_hour, description) {
  $.ajax({
    type: "POST",
    url: "http://"+window.location.host+"/api/session/update/" + id,
    dataType: "json",
    contentType: 'application/json',
    data: JSON.stringify({
      title: title,
      speaker: speaker,
      day: day,
      hall: hall,
      startHour: start_hour,
      endHour: end_hour,
      description: description ,
      lang: activeLang
    }),
    beforeSend: function() {
    },
    success: function(response) {
      log("update", response);
    },
    error: function(response) {
      log("update", response);
    },
    complete: function() {
      location.reload();
    }
  });
}

function deleteSession(id){
  $.ajax({
    type: "DELETE",
    url: "http://"+window.location.host+"/api/session/delete/" + id,
    success: function(response){
      log("delete", response);
    },
    complete: function() {
      location.reload();
    }
  });
}

function log(event, msg) {
  console.log(event + ":  " + msg);
}