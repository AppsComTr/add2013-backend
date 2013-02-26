function getSpeakers(select){
  $.ajax({
          type: "GET",
          url: "http://"+window.location.host+"/api/speakers",
          success: function(response){
            $.each(response.speakers, function(index, speaker) {
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
            if(response.sessions != undefined){
              $.each(response.sessions, function(index, session) {
                if(session.break == "false"){
                  $('#' + table).append('<tr id="'+session.id+'"><td>' + session.id + '</td><td>' + session.title + '</td><td>' + ((session.speaker1 == undefined)? null : session.speaker1.id) + ',' + ((session.speaker2 == undefined)? null : session.speaker2.id) + ',' + ((session.speaker3 == undefined)? null : session.speaker3.id) + '</td><td>' + session.hall + '</td><td>' + session.day + '</td><td>' + session.startHour + '</td><td>' + session.endHour + '</td><td style="display:none">' + session.description +'</td><td><button id="btnShowUpdateModal"  data-id="'+session.id+'" class="btn btn-mini btn-warning" ><i class="icon-pencil"></i></button> <button class="btn btn-mini btn-danger" data-id="'+session.id+'" id="btnShowDeleteModal"><i class="icon-remove"></i></button></td></tr>');
                }else{
                  $('#' + table).append('<tr id="'+session.id+'"><td>' + session.id + '</td><td>' + session.title + '</td><td>break</td><td>' + session.hall + '</td><td>' + session.day + '</td><td>' + session.startHour + '</td><td>' + session.endHour + '</td><td style="display:none">' + session.description +'</td><td><button id="btnShowUpdateModal"  data-id="'+session.id+'" class="btn btn-mini btn-warning" ><i class="icon-pencil"></i></button> <button class="btn btn-mini btn-danger" data-id="'+session.id+'" id="btnShowDeleteModal"><i class="icon-remove"></i></button></td></tr>');
                }
               });
            }
          }
        });
  $('#tbodyTR').hide();
}

function createSession(lang, day, start_hour, end_hour, hall, isBreak, speaker1ID, speaker2ID, speaker3ID, title, description) {
  $.ajax({
    type: "POST",
    url: "http://"+window.location.host+"/api/session/create",
    dataType: "json",
    contentType: 'application/json',
    data: JSON.stringify({
      lang: lang,
      day: day,
      startHour: start_hour,
      endHour: end_hour,
      hall: hall,
      break: isBreak,
      speaker1ID: speaker1ID,
      speaker2ID: speaker2ID,
      speaker3ID: speaker3ID,
      title: title,
      description: description
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

function updateSession(id, lang, day, start_hour, end_hour, hall, isBreak, speaker1ID, speaker2ID, speaker3ID, title, description)  {
  $.ajax({
    type: "POST",
    url: "http://"+window.location.host+"/api/session/update/" + id,
    dataType: "json",
    contentType: 'application/json',
    data: JSON.stringify({
      lang: lang,
      day: day,
      startHour: start_hour,
      endHour: end_hour,
      hall: hall,
      break: isBreak,
      Speaker1: speaker1ID,
      Speaker2: speaker2ID,
      Speaker3: speaker3ID,
      title: title,
      description: description
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