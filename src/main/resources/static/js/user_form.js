var userForm = {
    init : function () {
        var _this = this;

        console.log("user_form init");

        //그룹 셀렉트 박스 그리기
        _this.groupSelectLoad();

        //날짜 로드
        _this.datePickerLoad();
        
        //그룹 셀렉트박스 change 이벤트
        $('#UserForm .group-selectBox select').on('change', function () {
            var level = Number($(this).attr("group-level"));
            var selectCnt = $('#UserForm .group-selectBox select').length;

            //하위 레벨의 옵션 초기화
            for(var i = (level + 1);i <= selectCnt;i++){
                $('#UserForm .group-selectBox [group-level=' + i + ']').children().remove();
            }

            //값이 있는 옵션을 선택할 경우
            if(common.dataChk($(this).val())){
                //그룹을 가져와 그리기
                common.childSelectGroupLoad($(this).val(), level+1, '#UserForm');
            }
        });

        //달력아이콘 click 이벤트
        $('#UserForm .calendar-icon').click(function(){
            var target = $(this).attr("target");
            $("#" + target).datepicker().focus();
        });
    },
    groupSelectLoad : function () {
        //해당 유저에 지정된 그룹아이디가 있다면
        if(common.dataChk($("#formGroupId").val())){
            var groupId = Number($("#formGroupId").val());
            var level = Number($("#formLevel").val());
            var parentGroupId = Number($("#formParentGroupId").val());

            //자신의 그룹을 가져와 그리기
            common.selectGroupLoad(groupId, level, parentGroupId, false, '#UserForm');
        }else{
            //그룹 셀렉트박스 최상위 그룹을 가져와 그리기
            common.childSelectGroupLoad(-1, 1, '#UserForm');
        }
    },
    datePickerLoad : function () {
        $("#birthDate").datepicker({
            format: "yyyy-mm-dd(D)",
            calendarWeeks: false, //몇째주인지 표시
            autoclose: true,
            todayHighlight: true,
            language: "kr",
            useCurrent: false,
            endDate: new Date()
        }).on('changeDate', function(e){
        });

        $("#alphaDate").datepicker({
            format: "yyyy-mm-dd(D)",
            calendarWeeks: false, //몇째주인지 표시
            autoclose: true,
            todayHighlight: true,
            language: "kr",
            useCurrent: false,
            endDate: new Date()
        }).on('changeDate', function(e){
        });

        $("#pastureJoinDate").datepicker({
            format: "yyyy-mm-dd(D)",
            calendarWeeks: false, //몇째주인지 표시
            autoclose: true,
            todayHighlight: true,
            language: "kr",
            useCurrent: false,
            endDate: new Date()
        }).on('changeDate', function(e){
        });
    },
    passwordReset : function(){
        var url = "/services/user/passwordUpdate";
        var txt = "비밀번호 초기화";

        if(!confirm(txt + "하시겠습니까?")){
            return;
        }

        var form = $("#UserForm")[0];
        var formData = new FormData(form);

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
            user.gridSearch();

            //팝업창 닫기
            $("#saveUserModal .close").trigger("click");

        }).fail(function (error) {
            console.debug(txt + "실패");
            alert("관리자에게 문의하거나 다시 시도해주세요.");
        });
    },
    save : function () {
        //필수체크 검사
        if(!common.formChk($("#UserForm"))){
            return;
        }

        var form = $("#UserForm")[0];
        var formData = new FormData(form);

        //그룹셀렉트 박스
        var selectCnt = $('#UserForm .group-selectBox select').length;

        //최하위부터 선택괸 그룹 값 검색
        for(var i = selectCnt;i >= 1;i--){
            var selVal = $('#UserForm .group-selectBox [group-level=' + i + ']').val();
            if(common.dataChk(selVal)){
                formData.append("groupId", selVal);
                break;
            }
        }

        formData.set("birthDate", $("#birthDate").val().replace(/[^0-9]/g, ""));
        formData.set("alphaDate", $("#alphaDate").val().replace(/[^0-9]/g, ""));
        formData.set("pastureJoinDate", $("#pastureJoinDate").val().replace(/[^0-9]/g, ""));

        var url = "/services/user/create";
        var txt = "등록";

        if(common.dataChk($("#userId").val())){
            url = "/services/user/update";
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
            user.gridSearch();

            //팝업창 닫기
            $("#saveUserModal .close").trigger("click");

        }).fail(function (error) {
            console.debug(txt + "실패");
            alert("관리자에게 문의하거나 다시 시도해주세요.");
        });
    },
    delete : function () {
        var form = $("#UserForm")[0];
        var formData = new FormData(form);

        var url = "/services/user/remove";
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
            user.gridSearch();

            //팝업창 닫기
            $("#saveUserModal .close").trigger("click");

        }).fail(function (error) {
            console.debug(txt + "실패");
            alert("관리자에게 문의하거나 다시 시도해주세요.");
        });
    }
};