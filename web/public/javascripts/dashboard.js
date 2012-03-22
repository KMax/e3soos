/**
 * Initializes the code
 */
$(document).ready(function(){
  $('#btn-classify').click(function(event){
    classify();
    event.preventDefault();
  });
});

/**
 * Sends a request to classify the lens.
 */
function classify() {
  $.getJSON('/classify?'+$.param(getTechReqs()),function(data){
    console.log(data);
    $('#class-J').text(data.J);
    $('#class-W').text(data.W);
    $('#class-F').text(data.F);
    $('#class-L').text(data.L);
    $('#class-Q').text(data.Q);
    $('#class-S').text(data.S);
    $('#class-D').text(data.D);
    $('#class-R').text(data.R);
  });
}

/**
 * Serializes technical requirements to a Javascript object.
 */
function getTechReqs() {
  var techReqs = {};
  techReqs['requirements.apertureSpeed'] = $('#apertureSpeed').val();
  techReqs['requirements.angularField'] = $('#angularField').val();
  techReqs['requirements.focalLength'] = $('#focalLength').val();
  techReqs['requirements.spectralRange'] = $('#spectralRange').val();
  techReqs['requirements.imageQuality'] = $('#imageQuality').val();
  techReqs['requirements.backFocalDistance'] = $('#backFocalDistance').val();
  techReqs['requirements.entrancePupilPosition'] = $('#entrancePupilPosition').val();
  techReqs['requirements.waveLengths[0]'] = $('#spectralRange-min').val();
  techReqs['requirements.waveLengths[1]'] = $('#spectralRange-max').val();
  return techReqs;
}