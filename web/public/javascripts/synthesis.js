"use strict";

//Declare the namespace
var e3soos = e3soos || {};

/**
 * Initializes the code
 */
$(document).ready(function () {
    e3soos.dashboard.initialize();
});

e3soos.dashboard = (function () {

    var classification = {},
    technicalReqs = {},
    schemes = [],

    /**
        * Reads technical requirements from the DOM tree and saves them.
        */
    readTechnicalReqs = function () {
        technicalReqs.apertureSpeed = $('#aperture-speed').val();
        technicalReqs.angularField = $('#angular-field').val();
        technicalReqs.focalLength = $('#focal-length').val();
        technicalReqs.imageQuality = $('#image-quality').val();
        technicalReqs.backFocalDistance = $('#backfocal-distance').val();
        technicalReqs.entrancePupilPosition = $('#entrance-pupil-position').val();
        technicalReqs['waveLengths[0]'] = $('#spectral-range-min').val();
        technicalReqs['waveLengths[1]'] = $('#spectral-range-max').val();
    },

    /**
        * Gets if the debug mode is enabled or not.
        * @return true or false
        */
    isDebugMode = function () {
        var debug = $('#debug');
        return debug.size() !== 0 && debug.is('.active');
    },

    /**
        * Writes the classification object in DOM tree.
        */
    writeClassification = function () {
        $('#class-J').text(classification.j);
        $('#class-W').text(classification.w);
        $('#class-F').text(classification.f);
        $('#class-L').text(classification.l);
        $('#class-Q').text(classification.q);
        $('#class-S').text(classification.s);
        $('#class-D').text(classification.d);
        $('#class-R').text(classification.r);
    },

    afterSynthesisFinished = function (data) {
        schemes = data.schemes;
        classification = data.classification;
        writeClassification();
        lib.schemes.show({
            schemes: schemes
        });
        $('#scheme-area').height($(window).height() - $('#general-chs').height() - 130);
    },

    onError = function () {
        e3soos.notifier.showError();
    };

    /**
    * Public methods and variables.
    */
    return {
        synthesis: function () {
            readTechnicalReqs();
            if (!jQuery.isEmptyObject(technicalReqs)) {
                e3soos.notifier.open();
                if (isDebugMode()) {
                    $.getJSON(
                        '/debug/synthesis?' + e3soos.utils.toParam(technicalReqs, 'requirements.'),
                        function (data) {
                            e3soos.debugwindow.open(data.logs);
                            afterSynthesisFinished(data.data);
                            e3soos.notifier.close();
                        }
                        ).error(function () {
                        onError();
                    });
                } else {
                    $.getJSON(
                        '/run/synthesis?' + e3soos.utils.toParam(technicalReqs, 'requirements.'),
                        function (data) {
                            afterSynthesisFinished(data);
                            e3soos.notifier.close();
                        }
                        ).error(function () {
                        onError();
                    });
                }
            }
        },

        /**
         * Initilize the module.
         */
        initialize: function () {
            $('#btn-synthesis').click(function (event) {
                if (e3soos.validator.validate($('#tech-reqs-form'))) {
                    e3soos.dashboard.synthesis();
                }
                event.preventDefault();
            });
            $('#debug').click(function (event) {
                event.preventDefault();
            });
            $('#debug').button();

            //Setup ajax request configuration
            $.ajaxSetup({
                timeout: 60000
            });
            $('#print').click(function (event) {
                if (e3soos.validator.validate($('#tech-reqs-form'))) {
                    e3soos.dashboard.print();
                }
                event.preventDefault();
            });
        },
        print: function () {
            readTechnicalReqs();
            if (!jQuery.isEmptyObject(technicalReqs)) {
                window.open(
                '/synthesis/print?' +e3soos.utils.toParam(technicalReqs, 'requirements.'));
            }
        }
    };
})();

/**
 * Progress bar.
 */
e3soos.notifier = (function () {

    var modal_id = "notifier-modal",

    /**
        * Creates the DOM representation of the progress bar.
        */
    createDOM = function () {
        $('body').append('<div id="' + modal_id + '" class="modal fade">'
            + '<div class="modal-header"><h3>Please, waiting...</h3></div>'
            + '</div>');
    };

    /**
    * Public methods and variables.
    */
    return {
        open: function () {
            if ($('#' + modal_id).length <= 0) {
                createDOM();
            }
            $('#' + modal_id).modal({
                backdrop: 'static',
                keyboard: false
            });
        },

        close: function () {
            setTimeout(function () {
                $('#' + modal_id).modal('hide');
            }, 600);
        },

        showError: function () {
            $('#' + modal_id).empty();
            $('#' + modal_id).append('<div class="alert alert-error" style="margin-bottom:0;">'
                + '<h4 class="alert-heading">Oops, error!</h4>'
                + 'Please, reload the page or contact us if it doesn\'t help.'
                + '</div>');
        }
    };
})();

e3soos.debugwindow = (function () {

    var window = $('<div></div>').dialog({
        autoOpen: false,
        title: 'Debug window',
        width: 'auto'
    });

    return {
        open: function (logs) {
            window.empty();
            var html = '<table class="table table-striped">'
            + '<thead><tr><th>Package</th><th>Rule</th><th>Facts fired a rule</th></tr></thead>';
            $.each(logs, function (index, value) {
                html += '<tr><td>' + value.packageName + '</td><td>' + value.ruleName
                + '</td><td>' + value.facts + '</td></tr>';
            });
            html += '</table>';
            window.html(html);
            window.dialog('open');
        },
        close: function () {
            window.dialog('close');
        }
    };
})();

e3soos.validator = (function () {

    var isEmptyString = function (value) {
        var tmp = $.trim(value);
        if (tmp === "" || tmp === undefined || tmp === null) {
            return true;
        } else {
            return false;
        }
    },

    isValid = function (element) {
        if (element.attr('requred') === "true" && isEmptyString(element.val())) {
            return false;
        }
        if (element.attr('type') === "number" && !$.isNumeric(element.val())) {
            return false;
        }
        if (element.attr('min') && element.val() < parseFloat(element.attr('min'))) {
            return false;
        }
        if (element.attr('max') && element.val() > parseFloat(element.attr('max'))) {
            return false;
        }
        return true;
    };

    return {
        /**
         * Validate a form.
         */
        validate: function (form) {
            var flag = true;
            form.find('*:input').each(function () {
                var control_group = $(this).parents('.control-group');
                if (!isValid($(this))) {
                    $(control_group).addClass('error');
                    flag = false;
                } else {
                    $(control_group).removeClass('error');
                }
            });
            return flag;
        }
    };
})();