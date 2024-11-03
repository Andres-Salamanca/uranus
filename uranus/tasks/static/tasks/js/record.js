  function __log(e, data) {
    console.log("\n" + e + " " + (data || ''));
  }

  var audio_context;
  var recorder;

  function startUserMedia(stream) {
    var input = audio_context.createMediaStreamSource(stream);
    __log('Media stream created.');    
    recorder = new Recorder(input);
    __log('Recorder initialised.');
  }

  function startRecording() {
    recorder && recorder.record();
    __log('Recording...');
  }

  function stopRecording() {
    recorder && recorder.stop();
    __log('Stopped recording.');
    createDownloadLink();    
    recorder.clear();
  }

  function createDownloadLink() {
    recorder && recorder.exportWAV(function(blob) {
      var formData = new FormData();
      formData.append('.wav', blob, 'cosa.wav');
      formData.append('name', fileName + '-q' + secuency +'.wav');
      sendAudio(formData, redirectNewProblem, msgError);
    });
  }

  window.onload = function init() {
    try {
      // webkit shim
      window.AudioContext = window.AudioContext || window.webkitAudioContext;
      navigator.getUserMedia = navigator.getUserMedia || navigator.webkitGetUserMedia  || navigator.mozGetUserMedia;
      window.URL = window.URL || window.webkitURL;      
      audio_context = new AudioContext;
      __log('Audio context set up.');
      __log('navigator.getUserMedia ' + (navigator.getUserMedia ? 'available.' : 'not present!'));
    } catch (e) {
      alert('No web audio support in this browser!');
    }    
    navigator.getUserMedia({audio: true}, startUserMedia, function(e) {
      __log('No live audio input: ' + e);
    });
  };

function redirectNewProblem() {
  if (secuency > 2) {
    window.location="/nuevo-problema?ses=" + sessionID + '&res=' + resolutionID;
  }
}

function msgError() {
  alert('Try agein ;)');
}

function sendAudio(dto, doneFunction, failFuntion) {
    $.ajax({
        url: "/send-audio-action",
        type: 'POST',
        data: dto,
        contentType: false,
        enctype: "multipart/form-data",
        processData: false,
        cache: false
    }).done(function(response) {               
        if (doneFunction != null) {
            doneFunction(JSON.parse(response));
        }            
    }).fail(function(jqXHR, textStatus) {
        if (failFuntion != null) {
            failFuntion(textStatus);
        } 
    }); 
}

function csrfSafeMethod(method) {
    return (/^(GET|HEAD|OPTIONS|TRACE)$/.test(method));
}

$(document).ready(function() {
  var csrftoken = $('#csrftoken > input').val();
  $.ajaxSetup({
      beforeSend: function(xhr, settings) {
          if (!csrfSafeMethod(settings.type) && !this.crossDomain) {
              xhr.setRequestHeader("X-CSRFToken", csrftoken);
          }
      }
  });
});
