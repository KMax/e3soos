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

/**
 * Utility module.
 */
var utils = (function(){
  return {
    toParam :function(object, keyPrefix) {
      var tmp = {};
      $.each(object, function(key, value) {
              tmp[keyPrefix + key] = value;
            });
      return $.param(tmp);
    }
  };
})();

var dashboard = (function(){

  var classification = {};
  var technicalReqs = {};
  var schemas = [];
  var images = [];

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
   * Gets if debug mode is enabled or not.
   * @return true or false
   */
  var isDebugMode = function() {
    var debug = $('#debug');
    return debug.size() != 0 && debug.is('.active');
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
          '<table id="schemas-list" class="table table-bordered">' +
            '<thead><tr><th>#</th><th>Scheme</th>'
            + '<th>Code '
            +'<a href="#"><i id="aperture-speed-help" class="icon-question-sign"></i></a>'
            + '</th></tr></thead>'
          +'</table>'
        );
        var schemas_list = $('#schemas-list');
        $.each(schemas, function(index) {
          schemas_list.append(
          '<tr>'
            + '<td>' + index +'</td>'
            + '<td id="scheme_' + index +'"></td>'
            + '<td>' + schemas[index] + '</td>'
           + '</tr>');
         images[index] = new Drawing.Scheme({
           container: 'scheme_' + index,
           elements: schemas[index].split(' + '),
           interval: 10
         });
         images[index].draw();
        });
      } else {
        $('#schemas-area').append('<h4 id="schemas-list">No schemes</h4>');
      }
    }
  }

  var removeSchemas = function() {
    $('#schemas-list').detach();
  };

  var afterClassificationFinished = function() {
    writeClassification();
  };

  var afterSynthesisFinished = function(data) {
    schemas = data;
    removeSchemas();
    writeSchemas();
  }

  var beforeSendRequest = function() {
    progressbar.show();
  };

  var afterRequestCompleted = function() {
    setTimeout(
      function(){
        progressbar.hide();
      },
      700
    );
  };

  /**
   * Public methods and variables.
   */
  return {
    classify: function() {
      readTechnicalReqs();
      beforeSendRequest();
      $.getJSON(
        '/classify?' + utils.toParam(technicalReqs, 'requirements.'),
        function(data) {
          classification = data;
          afterClassificationFinished();
          afterRequestCompleted();
        }
      );
    },
    synthesis: function() {
      if(!jQuery.isEmptyObject(classification)) {
        delete classification.r;
        beforeSendRequest();
        if(isDebugMode()) {
          $.getJSON(
            '/debug/synthesis?' + utils.toParam(classification, 'classification.'),
            function(data) {
              debugwindow.open(data.logs);
              afterSynthesisFinished(data.data);
              afterRequestCompleted();
            }
          );
        } else {
          $.getJSON(
            '/synthesis?' + utils.toParam(classification, 'classification.'),
            function(data) {
              afterSynthesisFinished(data);
              afterRequestCompleted();
            }
          );
        }
      }
    }
  };

})();

/**
 * Progress bar.
 */
var progressbar = (function() {

  /**
   * Creates the DOM representation of the progress bar.
   */
  var createDOM = function() {
    $('body').append('<div id="progressbar" class="modal fade">'
      + '<div class="modal-header"><h3>Please, waiting...</h3></div>'
      +'</div>');
  }

  /**
   * Removes the DOM representation of the progress bar.
   */
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
  };
})();

var debugwindow = (function(){

  var window = $('<div></div>').
    dialog({
      autoOpen: false,
      title: 'Debug window',
      width: 'auto'
    });

  return {
    open: function(logs) {
      window.empty();
      var html = '<table class="table table-striped">'
        + '<thead><tr><th>Package</th><th>Rule</th><th>Facts fired a rule</th></tr></thead>';
      $.each(logs, function(index, value){
        html += '<tr><td>' +value.packageName +'</td><td>' + value.ruleName
          + '</td><td>' + value.facts + '</td></tr>';
      });
      html += '</table>';
      window.html(html);
      window.dialog('open');
    },
    close: function() {
      window.dialog('close');
    }
  };
})();