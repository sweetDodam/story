var storyPastureForm = {
    init : function () {
        var _this = this;

        console.log("storyPastureForm init");

        //불참 사유 셀렉트박스 옵션 그리기
        common.comCodeSelectLoad(".comCode");

        //예배 참석 여부
        $("input[name=worshipYn]").on('change', function () {
            //참석일 경우 비활성화
            if($(this).val() == "1"){
                $("#worshipReason").children().first().attr("selected", true);
                $("#worshipReason").attr("readonly", true);
                $("#worshipReason").attr("required", false);
            }else{
                $("#worshipReason").attr("readonly", false);
                $("#worshipReason").attr("required", true);
            }
        });

        //리더모임 참석 여부
        $("input[name=leaderYn]").on('change', function () {
            //참석일 경우 비활성화
            if($(this).val() == "1"){
                $("#leaderReason").children().first().attr("selected", true);
                $("#leaderReason").attr("readonly", true);
                $("#leaderReason").attr("required", false);
            }else{
                $("#leaderReason").attr("readonly", false);
                $("#leaderReason").attr("required", true);
            }
        });
    },
    save : function () {
        //필수체크 검사
        if(!common.formChk($("#StoryForm"))){
            return;
        }

        var form = $("#StoryForm")[0];
        var formData = new FormData(form);

        //그룹셀렉트 박스
        var selectCnt = $('.group-selectBox select').length;

        //최하위부터 선택괸 그룹 값 검색
        for(var i = selectCnt;i >= 1;i--){
            var selVal = $('.group-selectBox [group-level=' + i + ']').val();
            if(common.dataChk(selVal)){
                formData.append("groupId", selVal);
                break;
            }
        }

        var url = "/services/story/pasture/create";
        var txt = "등록";

        if(common.dataChk($("#storyId").val())){
            url = "/services/story/pasture/update";
            txt = "수정";
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
            storyPasture.gridSearch();

            //팝업창 닫기
            $("#saveStoryModal .close").trigger("click");

        }).fail(function (error) {
            console.debug(txt + "실패");
            alert(error);
        });
    },
    delete : function () {
        var form = $("#StoryForm")[0];
        var formData = new FormData(form);

        var url = "/services/story/pasture/remove";
        var txt = "삭제";

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
            storyPasture.gridSearch();

            //팝업창 닫기
            $("#saveStoryModal .close").trigger("click");

        }).fail(function (error) {
            console.debug(txt + "실패");
            alert(error);
        });
    }
};