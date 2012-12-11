$(document).ready(function() {
        $('.show').click(function() {
            $(this).next('div').slideDown();
            $(this).hide();
        });

        $('.hide').click(function() {
            $(this).parent().slideUp();
            $(this).parent().prev('.show').show();
        });
});

