/**
 * Initializes the code
 */
$(document).ready(function(){
    $('#btn-synthesis').click(function (event) {
        if(validator.validate($('#tech-reqs-form'))){
            dashboard.synthesis();
        }
        event.preventDefault();
    });
    $('#debug').click(function (event) {
        event.preventDefault();
    });
    $('#debug').button();
});

/**
 * Utility module.
 */
var utils = (function () {
    return {
        toParam : function (object, keyPrefix) {
            var tmp = {};
            $.each(object, function (key, value) {
                tmp[keyPrefix + key] = value;
            });
            return $.param(tmp);
        }
    };
})();

var dashboard = (function () {

    var classification = {};
    var technicalReqs = {};
    var schemes = [];

    /**
    * Reads technical requirements from the DOM tree and saves them.
    */
    var readTechnicalReqs = function () {
        technicalReqs.apertureSpeed = $('#aperture-speed').val();
        technicalReqs.angularField = $('#angular-field').val();
        technicalReqs.focalLength = $('#focal-length').val();
        technicalReqs.imageQuality = $('#image-quality').val();
        technicalReqs.backFocalDistance = $('#backfocal-distance').val();
        technicalReqs.entrancePupilPosition = $('#entrance-pupil-position').val();
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
    };

    var afterSynthesisFinished = function(data) {
        schemes = data.schemes;
        classification = data.classification;
        writeClassification();
        lib.schemes.show({
            schemes: schemes
        });
        $('#scheme-area').height($(window).height() - $('#general-chs').height() - 130);
    }

    var beforeSendRequest = function() {
        progressbar.show();
    };

    var afterRequestCompleted = function() {
        setTimeout(
            function(){
                progressbar.hide();
            },
            600);
    };

    /**
    * Public methods and variables.
    */
    return {
        synthesis: function () {
            readTechnicalReqs();
            if(!jQuery.isEmptyObject(technicalReqs)) {
                beforeSendRequest();
                if(isDebugMode()) {
                    $.getJSON(
                        '/debug/synthesis?' + utils.toParam(technicalReqs, 'requirements.'),
                        function(data) {
                            debugwindow.open(data.logs);
                            afterSynthesisFinished(data.data);
                            afterRequestCompleted();
                        }
                    );
                } else {
                    $.getJSON(
                        '/run/synthesis?' + utils.toParam(technicalReqs, 'requirements.'),
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
   * Public methods and variables.
   */
    return {
        show: function() {
            if($('#progressbar').length <= 0) {
                createDOM();
            }
            $('#progressbar').modal({
                backdrop: 'static',
                keyboard: false,
                show: false
            });
            $('#progressbar').modal('show');
        },
        hide: function() {
            $('#progressbar').modal('hide');
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

var validator = (function () {

    var isEmptyString = function (value) {
        var tmp = $.trim(value);
        if(tmp == "" || tmp == undefined || tmp == null) {
            return true;
        } else {
            return false;
        }
    };

    var isValid = function(element) {
        if(element.attr('requred') == "true" && isEmptyString(element.val())) {
                return false;
        }
        if(element.attr('type') == "number" && !$.isNumeric(element.val())) {
            return false;
        }
        return true;
    };

    return {
        validate: function(form) {
            var flag = true;
            form.find('*:input').each(function () {
                var control_group = $(this).parents('.control-group');
                if(!isValid($(this))) {
                    $(control_group).addClass('error');
                    flag = false;
                } else {
                    $(control_group).removeClass('error');
                }
            });
            return flag;
        }
    }
})();