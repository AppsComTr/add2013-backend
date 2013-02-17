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

function updateSession(id, title, speaker, day, hall, start_hour, end_hour, description) {
  $.ajax({
    type: "PUT",
    url: "http://localhost:8888/api/session/" + id,
    dataType: "json",
    data: {
      title: title,
      speaker: speaker,
      day: day,
      hall: hall,
      start_hour: start_hour,
      end_hour: end_hour,
      description: description
    },
    beforeSend: function() {
      log("beforeSend", "");
    },
    success: function(response) {
      log("success", response);
    },
    error: function(response) {
      log("error", response);
    },
    complete: function() {
      log("complete", response);
    }
  });
}

function log(event, msg) {
  console.log(event + ":  " + msg);
}