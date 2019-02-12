var storyPastorForm = {
    init : function () {
        var _this = this;

        console.log("storyPastorForm init");
    },
    save : function () {
        //필수체크 검사
        if(!common.formChk($("#StoryForm"))){
            return;
        }

        var form = $("#StoryForm")[0];
        var formData = new FormData(form);

        var url = "/services/story/pastor/create";
        var txt = "등록";

        if(common.dataChk($("#storyId").val())){
            url = "/services/story/pastor/update";
            txt = "수정";
        }

        if($("#myUserId").val() != $("#pastorId").val()){
            txt = "다른 목회자의 스토리입니다. " + txt;
        }

        if(!confirm(txt + "하시겠습니까?")){
            return;
        }
        $.ajax({
            type: 'POST',
            url: url,
            processData: false,
            contentType: false,
            cache : false,
            data : formData
        }).done(function() {
            alert(txt + '되었습니다.');

            //그리드 재조회
            storyPastor.gridSearch();

            //팝업창 닫기
            $("#saveStoryModal .close").trigger("click");

        }).fail(function (error) {
            console.debug(txt + "실패");
            alert("관리자에게 문의하거나 다시 시도해주세요.");
        });
    },
    delete : function () {
        var form = $("#StoryForm")[0];
        var formData = new FormData(form);

        var url = "/services/story/pastor/remove";
        var txt = "삭제";

        if($("#myUserId").val() != $("#pastorId").val()){
            txt = "다른 목회자의 스토리입니다. " + txt;
        }

        if(!confirm(txt + "하시겠습니까?")){
            return;
        }

        $.ajax({
            type: 'POST',
            url: url,
            processData: false,
            contentType: false,
            cache : false,
            data : formData
        }).done(function() {
            alert(txt + '되었습니다.');

            //그리드 재조회
            storyPastor.gridSearch();

            //팝업창 닫기
            $("#saveStoryModal .close").trigger("click");

        }).fail(function (error) {
            console.debug(txt + "실패");
            alert("관리자에게 문의하거나 다시 시도해주세요.");
        });
    }
};