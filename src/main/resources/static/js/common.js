var common = {
    init : function () {
        var _this = this;
        _this.load();

    },
    //공통 화면 로드
    load : function () {
        $("#com-menu").load("/com/menu");  //공통 메뉴 불러오기
    },
    //데이터 체크(문자열리턴)
    dataChkStr : function (data) {
        if(data == null || data == undefined || data == ""){
            return "";
        }

        return data;
    },
    //데이터 체크(boolean 리턴)
    dataChk : function (data) {
        if (data == null || data == undefined || data == "") {
            return false;
        }

        return true;
    },
    //form required 속성 체크
    formChk : function (form) {
        var required = $(form).find("[required='required']");

        for(var i = 0;i < required.length;i++){
            var tag = ($(required[i]).prop('tagName')).toUpperCase();

            if(tag == "INPUT" || tag == "SELECT"){
                if(!common.dataChk($(required[i]).val())){
                    alert("필수 입력 사항입니다.");
                    $(required[i]).focus();
                    return false;
                }
            }
        }

        return true;
    },
    serializeObject : function(form) {
        var obj = null;
        try {
            if (form[0].tagName && form[0].tagName.toUpperCase() == "FORM") {
                var arr = form.serializeArray();
                if (arr) {
                    obj = {};
                    jQuery.each(arr, function() {
                        var format = $(form).find("[name="+this.name+"]").attr("format");

                        var val = this.value;
                        if(format == "num"){
                            val = Number(val);
                        }else if(format == "numToBool"){
                            val = Number(val) == 0 ? false : true;
                        }
                        obj[this.name] = val;
                    });
                }//if ( arr ) {
            }
        } catch (e) {
            alert(e.message);
        } finally {
        }

        return obj;
    },
    groupData : function (groupId) {
        var data = {
            groupId: groupId
        };

        var rs = null;

        $.ajax({
            type: 'GET',
            url: '/services/user/group/get',
            contentType: 'application/json',
            async : false,
            data: data
        }).done(function(result) {
            rs = result;
        }).fail(function (error) {
            console.debug(error);
            alert(error);
        });

        return rs;
    },
    childSelectGroupLoad : function (parentGroupId, level) {
        var data = {
            parentGroupId: parentGroupId
        };

        $.ajax({
            type: 'GET',
            url: '/services/user/group/childList',
            contentType: 'application/json',
            async : false,
            data: data
        }).done(function(result) {

            var len = result.length;

            if(len > 0){
                //빈값
                $('.group-selectBox [group-level=' + level + ']').append("<option value=''>선택</option>");
            }

            //하위 레벨의 옵션 초기화
            for(var i = 0;i < len;i++){
                $('.group-selectBox [group-level=' + level + ']').append("<option value='"+ result[i].groupId +"' parentGroupId='"+ result[i].parentGroupId +"'>"+ result[i].groupName +"</option>");
            }

        }).fail(function (error) {
            console.debug(error);
            alert(error);
        });
    },
    selectGroupLoad : function (groupId, level, parentGroupId, readOnly) {
        //자신의 그룹을 가져와 그리기
        common.childSelectGroupLoad(parentGroupId, level);

        //옵션 선택
        $('.group-selectBox [group-level=' + level + ']').children("[value=" + groupId + "]").attr("selected", true);

        if(readOnly) {
            //선택 불가능하게
            $('.group-selectBox [group-level=' + level + ']').attr("readonly", true);
        }

        //상위 셀렉트박스 그리기
        for(var i = (level-1);i >= 1;i--){
            var tmpPGroupId = $('.group-selectBox [group-level=' + (i+1) + ']').children("[selected=selected]").attr("parentGroupId");
            //그룹 그리기
            var data = common.groupData(tmpPGroupId);


            common.childSelectGroupLoad(data.parentGroupId, i);

            //옵션 선택
            $('.group-selectBox [group-level=' + i + ']').children("[value=" + data.groupId + "]").attr("selected", true);

            if(readOnly) {
                //선택 불가능하게
                $('.group-selectBox [group-level=' + i + ']').attr("readonly", true);
            }
        }

        //하위 셀렉트박스 그리기
        if($('.group-selectBox [group-level=' + (level+1) + ']').length > 0){
            //그룹 그리기
            common.childSelectGroupLoad(groupId, level+1);
        }
    },
    numberTypeChk: function (item) {
        var max = Number($(item).attr("max"));
        var min = Number($(item).attr("min"));
        var val = Number($(item).val());

        if(val > max){
            $(item).val(max);
        }

        if(val < min){
            $(item).val(min);
        }
    },
    comCodeData : function (parentCodeId) {
        var data = {
            parentCodeId: parentCodeId
        };

        var rs = null;

        $.ajax({
            type: 'GET',
            url: '/services/common/code/childList',
            contentType: 'application/json',
            async : false,
            data: data
        }).done(function(result) {
            rs = result;
        }).fail(function (error) {
            console.debug(error);
            alert(error);
        });

        return rs;
    }
};

common.init();