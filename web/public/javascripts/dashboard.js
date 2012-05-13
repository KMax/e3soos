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
    var schemas = [];
    var images = [];

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

    var saveScheme = function (code, onsuccess, onerror) {
        $.post('/scheme', {"code" : code})
        .success(function(id) {onsuccess(id);})
        .error(function() {onerror();});
    };

    var deleteScheme = function (id, onsuccess, onerror) {
        $.ajax({
            url: '/scheme/' + id,
            type: 'DELETE',
            success: function() {onsuccess();},
            error: function() {onerror();}
        });
    };

    var initSchemeEventHandlers = function () {

        var saveMessage = 'Save'
            , savedMessage = '<i class="icon-ok icon-white"></i> Saved'
            , deleteMessage = '<i class="icon-remove icon-white"></i> Delete'
            , savingMessage = 'Saving...'
            , deletingMessage = 'Deleting...'
            , d = 'disabled';

        $('#scheme-list .scheme button').click(function () {
            var button = $(this);
            var parent = button.parents('.scheme');

            if(button.hasClass('btn-warning') && parent.attr('data-scheme-id') != undefined) {
                //Delete a scheme
                button.attr(d,d).addClass(d).html(deletingMessage);
                deleteScheme(parent.attr('data-scheme-id'),
                    function() {
                        //On success
                        button.removeClass('btn-warning');
                        button.removeClass(d).removeAttr(d).html(saveMessage);
                    },
                    function () {
                        //On error
                        button.removeClass(d).removeAttr(d).html(saveMessage);
                    }
                );
            } else {
                //Save a scheme
                button.addClass('btn-success').attr(d, d).addClass(d).html(savingMessage);

                saveScheme(schemas[parent.attr('data-scheme-index')],
                    function(id) {
                        //On success
                        parent.attr('data-scheme-id', id);
                        button.removeClass(d).removeAttr(d).html(savedMessage);
                    },
                    function() {
                        //On error
                        button.removeClass(d).removeAttr(d).html(saveMessage);
                    }
                );
            }
        });
        $('#scheme-list .scheme button').hover(
            function() {
                var button = $(this);
                if(button.parents('.scheme').attr('data-scheme-id') != undefined
                    && button.hasClass('btn-success')
                    && !button.hasClass('disabled')) {
                    button.removeClass('btn-success')
                    .addClass('btn-warning')
                    .html(deleteMessage);
                }
            },
            function() {
                var button = $(this);
                if(button.parents('.scheme').attr('data-scheme-id') != undefined
                    && button.hasClass('btn-warning')
                    && !button.hasClass('disabled')) {
                    button.removeClass('btn-warning')
                    .addClass('btn-success')
                    .html(savedMessage);
                }
            }
        );
    };

    /**
    * Writes the schema objects in DOM tree.
    */
    var writeSchemas = function () {
        if(!$('#scheme-list').length > 0) {
            var schemes_area = $('#schemas-area');
            if($.isArray(schemas) && schemas.length > 0) {
                schemes_area.append(
                    '<div id="scheme-list"></div>'
                    );
                var schemas_list = $('#scheme-list');
                $.each(schemas, function (index) {
                    schemas_list.append(
                        '<div class="scheme" data-scheme-index="' + index + '">'
                        + '<div>'
                        + '<p class="scheme-number">#' + (index + 1) + '</p>'
                        + '<button class="btn btn-mini">Save</button>'
                        + '</div>'
                        + '<div id="scheme_' + index + '" class="scheme-image"></div>'
                        + '<div class="scheme-code">' + schemas[index]
                        + ' <i class="icon-question-sign"></i>'
                        +'</div>'
                        + '</div>');
                    images[index] = new Drawing.Scheme({
                        container: 'scheme_' + index,
                        elements: schemas[index].split(' + '),
                        interval: 10
                    });
                    images[index].draw();
                });
                schemes_area.height($(window).height() - $('#general-chs').height() - 150);
                initSchemeEventHandlers();
            } else {
                schemes_area.append('<h4 id="scheme-list">No schemes</h4>');
            }
        }
    };

    var removeSchemas = function() {
        $('#schemas-list').detach();
    };

    var afterSynthesisFinished = function(data) {
        schemas = data.schemes;
        classification = data.classification;
        writeClassification();
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
            500
            );
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
                        '/synthesis?' + utils.toParam(technicalReqs, 'requirements.'),
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