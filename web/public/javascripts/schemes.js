var lib = lib || {};

/**
 * Required options:
 *  - schemes - array of schemes codes
 */
lib.schemes = (function () {
    "use strict";

    var attrs = {
        container: "#scheme-area",
        schemes: [],
        id: 'data-scheme-id',
        disabled: 'disabled'
    }, messages = {
        noschemes: "No schemes.",
        save: "Save",
        deleting: "Deleting...",
        saving: "Saving...",
        saved: '<i class="icon-ok icon-white"></i> Saved',
        del: '<i class="icon-remove icon-white"></i> Delete'
    };

    var createDom = function () {
        var scheme_area = $(attrs.container);
        if ($('.scheme-list').length <= 0) {
            if ($.isArray(attrs.schemes) && attrs.schemes.length > 0) {
                //The array of schemes isn't empty.
                scheme_area.append('<div class="scheme-list"></div>');
                var scheme_list = $('.scheme-list');
                $.each(attrs.schemes, function (index, scheme) {
                    //Create scheme's dom
                    scheme_list.append(createSchemeDom(index, scheme));
                    //Draw a scheme
                    drawScheme(index, scheme);
                });
            } else {
                //The array of schemes is empty.
                scheme_area.append('<h4 class="scheme-list">' + messages.noschemes + '</h4>');
            }
        }
    };

    var removeDom = function () {
        $('.scheme-list').detach();
    };

    var drawScheme = function (index, scheme) {
        new Drawing.Scheme({
            container: 'scheme_' + index,
            codes: scheme.split(' + '),
            interval: 10
        })
        .draw();
    }

    var createSchemeDom = function (index, scheme) {
        return '<div class="scheme" data-scheme-index="' + index + '">'
                + '<div>'
                + '<p class="scheme-number">#' + (index + 1) + '</p>'
                + '<button class="btn btn-mini scheme-control">' + messages.save + '</button>'
                + '</div>'
                + '<div id="scheme_' + index + '" class="scheme-image"></div>'
                + '<div class="scheme-code">' + scheme
                + ' <i class="icon-question-sign"></i>'
                +'</div>'
                + '</div>';
    };

    var initEventHandlers = function () {
        //Click on the scheme button
        $(attrs.container).find('.scheme .scheme-control').click(function () {

            var button = $(this),
                parent = button.parents('.scheme'),
                scheme_id = parent.attr(attrs.id);

            if(button.hasClass('btn-warning') && scheme_id != undefined) {
                //Delete a scheme
                button.attr(attrs.disabled,attrs.disabled)
                .addClass(attrs.disabled).html(messages.deleting);
                deleteScheme(scheme_id,
                    function() {
                        //On success
                        button.removeClass('btn-warning')
                        .removeClass(attrs.disabled)
                        .removeAttr(attrs.disabled).html(messages.save);
                    },
                    function () {
                        //On error
                        button.removeClass(attrs.disabled)
                        .removeAttr(attrs.disabled).html(messages.save);
                    }
                );
            } else {
                //Save a scheme
                lib.schemes.dialog.handler(function (comment) {
                    button.addClass('btn-success').attr(attrs.disabled, attrs.disabled)
                    .addClass(attrs.disabled).html(messages.saving);
                    saveScheme(attrs.schemes[parent.attr('data-scheme-index')], comment,
                        function(data) {
                            //On success
                            parent.attr(attrs.id, data);
                            button.removeClass(attrs.disabled)
                            .removeAttr(attrs.disabled).html(messages.saved);
                        },
                        function() {
                            //On error
                            button.removeClass(attrs.disabled)
                            .removeAttr(attrs.disabled).html(messages.save);
                        }
                    );
                });
                lib.schemes.dialog.show();
            }
        });

        //Hover on the scheme button
        $(attrs.container).find('.scheme .scheme-control').hover(function() {
                var button = $(this);
                if(button.parents('.scheme').attr(attrs.id) != undefined
                    && !button.hasClass('disabled')) {
                    if(button.hasClass('btn-success')) {
                        button.removeClass('btn-success')
                        .addClass('btn-warning')
                        .html(messages.del);
                    } else if(button.hasClass('btn-warning')) {
                        button.removeClass('btn-warning')
                        .addClass('btn-success')
                        .html(messages.saved);
                    }
                }
            }
        );
    };

    var saveScheme = function (code, comment, onsuccess, onerror) {
        $.post('/schemes', {"code" : code, "comment" : comment})
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

    return {
        show: function (options) {
            if($.isEmptyObject(options.schemes)) {
                console.log('[lib.schemes] Required option "schemes" is missed!');
            }
            $.extend(attrs, options);
            removeDom();
            createDom();
            initEventHandlers();
        },
        hide: function () {
            removeDom();
        }
    };
})();

lib.schemes.dialog = (function () {

    var onhide = function() {};

    var createDom = function () {
        $('body').append(
        '<div class="modal fade" id="comment-modal">'
            + '<div class="modal-body">'
                + '<textarea id="comment" rows="2"></textarea>'
            + '</div>'
            + '<div class="modal-footer">'
                + '<button class="btn btn-primary">Leave a comment</button>'
            + '</div>'
        + '</div>');
    };

    var removeDom = function () {
      $('#comment-modal').detach();
    };

    return {
        handler : function (func) {
            onhide = func;
        },
        show : function() {
            createDom();
            $('#comment-modal').modal({
                backdrop: "static",
                keyboard : false,
                show: false
            });
            $('#comment-modal').modal('show');
            $('#comment-modal .modal-footer button').click(function(){
                $('#comment-modal').modal('hide');
                var comment = $('#comment').val();
                removeDom();
                onhide(comment);
            });
            $('#comment-modal #comment').focus();
        }
    }
})();