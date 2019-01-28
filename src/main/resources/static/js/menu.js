var menu = {
    init : function () {
        var _this = this;

        console.log("menu init");
        //메뉴 리스트
        //_this.load();

        //메뉴 첫번쨰 클릭
        $("#com-menu li.nav-item").first().children("a").trigger("click");
        
        //클릭 이벤트
        $('#com-menu .importPage').on('click', function () {
            if($(this).hasClass("prepare")) {
                alert("준비중입니다.");
                return;
            }

            $('#loadingArea').show(); //로딩바를 보여준다.

            var url = $(this).attr("importUrl");
            $("#import-page-area").load(url, function(){
                $('#loadingArea').hide(); //로딩바를 보여준다.
            });  //선택된 화면 로드

        });

        //클릭 이벤트
        $('#com-menu .dropdown-menu .dropdown-item').on('click', function () {
            $('#com-menu .dropdown-menu .dropdown-item').removeClass("active");
            $(this).addClass("active");
        });
    },
    load : function () {
        var data = {};

        $.ajax({
            type: 'GET',
            url: '/services/menu/list',
            contentType: 'application/json',
            data: data
        }).done(function(rs) {

        }).fail(function (error) {
            console.debug(error);
            alert(error);
        });
    }
};
menu.init();