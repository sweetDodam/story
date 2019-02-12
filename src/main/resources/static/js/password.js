var passwordForm = {
    init : function () {
        var _this = this;
        console.log("passwordForm init");
    },
    save : function () {
        //필수체크 검사
        if(!common.formChk($("#form"))){
            return;
        }

        var password = $("#password").val();
        var password2 = $("#password2").val();


        if(password != password2){
            alert("비밀번호가 일치하지 않습니다.");
            $("#password2").focus();
            return;
        }

        var form = $("#form")[0];
        var formData = new FormData(form);

        url = "/services/user/passwordUpdate";
        txt = "수정";

        $.ajax({
            type: 'POST',
            url: url,
            processData: false,
            contentType: false,
            cache : false,
            data : formData
        }).done(function(rs) {
            alert(rs.msg);
            document.location.href = "/";
        }).fail(function (error) {
            console.debug(txt + "실패");
            alert("관리자에게 문의하거나 다시 시도해주세요.");
        });
    }
};

passwordForm.init();