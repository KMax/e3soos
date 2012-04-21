/**
 * Initializes the code
 */
$(document).ready(function(){
  $('#btn-classify').click(function(event){
    dashboard.classify();
    event.preventDefault();
  });
  $('#btn-synthesis').click(function(event){
    dashboard.synthesis();
    event.preventDefault();
  });
  $('#debug').button();
});

var dashboard = (function(){

  var classification = {};
  var technicalReqs = {};
  var schemas = [];

  var toParam = function(object, keyPrefix) {
    var tmp = {};
    $.each(object, function(key, value) {
            tmp[keyPrefix + key] = value;
          });
    return $.param(tmp);
  }

  /**
   * Reads technical requirements from the DOM tree and saves them.
   */
  var readTechnicalReqs = function() {
    technicalReqs['apertureSpeed'] = $('#aperture-speed').val();
    technicalReqs['angularField'] = $('#angular-field').val();
    technicalReqs['focalLength'] = $('#focal-length').val();
    technicalReqs['imageQuality'] = $('#image-quality').val();
    technicalReqs['backFocalDistance'] = $('#backfocal-distance').val();
    technicalReqs['entrancePupilPosition'] = $('#entrance-pupil-position').val();
    technicalReqs['waveLengths[0]'] = $('#spectral-range-min').val();
    technicalReqs['waveLengths[1]'] = $('#spectral-range-max').val();
  };

  /**
   * Writes the classification object in DOM tree.
   */
  var writeClassification = function() {
    $('#class-J').text(classification.j);
    $('#class-W').text(classification.w);
    $('#class-F').text(classification.f);
    $('#class-L').text(classification.l);
    $('#class-Q').text(classification.q);
    $('#class-S').text(classification.s);
    $('#class-D').text(classification.d);
    $('#class-R').text(classification.r);
  }

  /**
   * Writes the schema objects in DOM tree.
   */
  var writeSchemas = function() {
    if(!$('#schemas-list').length > 0) {
      if($.isArray(schemas) && schemas.length > 0) {
        $('#schemas-area').append(
          '<table id="schemas-list" class="table table-striped">' +
            '<thead><tr><th>#</th><th>Scheme</th></tr></thead>' +
          '</table>'
        );
        var schemas_list = $('#schemas-list');
        $.each(schemas, function(index) {
          schemas_list.append(
          '<tr>' +
            '<td>' + index +'</td>' +
            '<td>' + schemas[index] +'</td>' +
          '</tr>');
        });
      } else {
        $('#schemas-area').append('<h4 id="schemas-list">No schemes</h4>');
      }
    }
  }

  var removeSchemas = function() {
    $('#schemas-list').detach();
  }

  /**
   * Public methods and variables.
   */
  return {
    classify: function() {
      readTechnicalReqs();
      progressbar.show();
      $.getJSON(
        '/classify?' + toParam(technicalReqs, 'requirements.'),
        function(data) {
          classification = data;
          writeClassification();
          setTimeout(function(){
            progressbar.hide();
          }, 700);
        }
      );
    },
    synthesis: function() {
      if(!jQuery.isEmptyObject(classification)) {
        delete classification.R;
        progressbar.show();
        $.getJSON(
          '/synthesis?' + toParam(classification, 'classification.'),
          function(data) {
            schemas = data;

            removeSchemas();
            writeSchemas();
            setTimeout(function(){
              progressbar.hide();
            }, 700);
          }
        );
      }
    }
  };

})();

var progressbar = (function() {

  var createDOM = function() {
    $('body').append('<div id="progressbar" class="modal fade">'
      + '<div class="modal-header"><h3>Please, waiting...</h3></div>'
      +'</div>');
  }

  var removeDOM = function() {
    $('#progressbar').detach();
  }

  /**
   * Public methods and variables.
   */
  return {
    show: function() {
      createDOM();
      $('#progressbar').modal({
        backdrop: 'static',
        keyboard: false,
        show: false
      });
      $('#progressbar').modal('show');
    },
    hide: function() {
      $('#progressbar').modal('hide');
      removeDOM();
    }
  } ;
})();