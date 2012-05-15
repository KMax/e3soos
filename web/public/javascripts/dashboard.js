$(document).ready(function(){
    $('.scheme div[id^="scheme_"]').each(function(){
        var image = new Drawing.Scheme({
                        container: $(this).attr('id'),
                        codes: $(this).parents('.scheme').attr('data-scheme-code').split(' + '),
                        interval: 10
                    });
        image.draw();
        $('#scheme-area').height($(window).height() - 130);
    });
    $('.scheme .scheme-control').click(function () {
        var button = $(this),
            parent = button.parents('.scheme'),
            d = 'disabled';
        //Delete a scheme
        button.attr(d,d).addClass(d).html('Deleting...');
        deleteScheme(parent.attr('data-scheme-id'),
            function() {
                //On success
                parent.slideUp(300, function() {
                   parent.detach();
                   if($('.scheme').length <= 0) {
                       $('#scheme-area').html('No saved schemes.');
                   }
                });
            },
            function () {
                //On error
                button.removeClass(d)
                .removeAttr(d).html('Delete');
            }
        );
    });
});

function deleteScheme (id, onsuccess, onerror) {
    $.ajax({
        url: '/scheme/' + id,
        type: 'DELETE',
        success: function() {onsuccess();},
        error: function() {onerror();}
    });
};