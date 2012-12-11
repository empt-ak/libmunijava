$(document).ready(function() {
        $(".all_books, .log_button").click(function(){
            window.location=$(this).find("a").attr("href"); 
            return false;
        });
});

