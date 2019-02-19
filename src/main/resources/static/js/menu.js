var menu = {
    init : function () {
        var _this = this;

        console.log("menu init");

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
            var menuId = $(this).attr("menuId");

            $("#import-page-area").load(url+"?", "menuId=" + menuId, function(){
                $('#loadingArea').hide(); //로딩바를 보여준다.
            });  //선택된 화면 로드
        });

        //클릭 이벤트
        $('#com-menu .dropdown-menu .dropdown-item').on('click', function () {
            $('#com-menu .dropdown-menu .dropdown-item').removeClass("active");
            $(this).addClass("active");
        });
    }
};
menu.init();