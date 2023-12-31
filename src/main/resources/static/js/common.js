var common = {
    init : function () {
        var _this = this;
        _this.load();

        window.onbeforeunload = function(e){
            if(e != null && e != undefined){
                $('#loadingArea').show(); //로딩바를 보여준다.
            }
        };

        //모달 열릴 때 이벤트
        $(document).on('show.bs.modal', '.modal:not(#logoutModal)', function(e){
            if($(e.target) != null && !$(e.target).hasClass("datePicker")){
                $('#loadingArea').show(); //첫 시작시 로딩바를 보여준다.
            }
        });

        //모달 열린 후 이벤트
        $(document).on('shown.bs.modal','.modal:not(#logoutModal)', function(e){
            setTimeout(function() {
                $('#loadingArea').hide(); //첫 시작시 로딩바를 숨겨준다.
            }, 400);
        });
        $(document).ready(function(){
            $('#loadingArea').hide(); //첫 시작시 로딩바를 숨겨준다.
        });
    },
    //공통 화면 로드
    load : function () {
        $("#com-menu").load("/com/menu");  //공통 메뉴 불러오기

        //메인 이미지 경로 가져오기
        var imgData = common.comCodeData("MAIN_IMG");
        $("#mainImage").attr("src", imgData[0].codeName);
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

            if(tag == "INPUT" || tag == "SELECT" || tag == "TEXTAREA"){
                if(!common.dataChk($(required[i]).val())){
                    alert("필수 입력 사항입니다.");
                    $(required[i]).focus();
                    return false;
                }
            }
        }

        return true;
    },
    //값 넣기
    dataSet : function (object, value) {
        if($(object).length > 0) {
            var tag = ($(object).prop('tagName')).toUpperCase();
            value = common.dataChkStr(value);

            if (tag == "INPUT" || tag == "SELECT") {
                $(object).val(value);
            }else if(tag == "TEXTAREA" ){
                $(object).val(value);
            } else {
                $(object).text(value);3
            }
        }
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
            groupId: groupId,
            tempYn: 'N',
            useYn: 'Y'
        };

        var rs = null;

        $.ajax({
            type: 'GET',
            url: '/services/user/group/get',
            contentType: 'application/json',
            async : false,
            data: data
        }).done(function(result) {
            console.log(result);
            rs = result;
        }).fail(function (error) {
            console.debug(error);
            alert("관리자에게 문의하거나 다시 시도해주세요.");
        });

        return rs;
    },
    groupChildData : function (groupId) {
        var data = {
            parentGroupId: groupId,
            useYn: 'Y'
        };

        var rs = null;

        $.ajax({
            type: 'GET',
            url: '/services/user/group/childList',
            contentType: 'application/json',
            async : false,
            data: data
        }).done(function(result) {
            console.log(result);
            rs = result;
        }).fail(function (error) {
            console.debug(error);
            alert("관리자에게 문의하거나 다시 시도해주세요.");
        });

        return rs;
    },
    childSelectGroupLoad : function (parentGroupId, level, selector) {
        var data = {
            parentGroupId: parentGroupId,
            tempYn: 'N',
            useYn: 'Y'
        };

        $.ajax({
            type: 'GET',
            url: '/services/user/group/childList',
            contentType: 'application/json',
            async : false,
            data: data
        }).done(function(result) {

            console.log(result);
            var len = result.length;
            var sel = common.dataChkStr(selector) + " ";

            if(len > 0){
                //빈값
                $(sel + '.group-selectBox [group-level=' + level + ']').append("<option value=''>선택</option>");
            }

            //하위 레벨의 옵션 초기화
            for(var i = 0;i < len;i++){
                $(sel + '.group-selectBox [group-level=' + level + ']').append("<option value='"+ result[i].groupId +"' parentGroupId='"+ result[i].parentGroupId +"'>"+ result[i].groupName +"</option>");
            }

        }).fail(function (error) {
            console.debug(error);
            alert("관리자에게 문의하거나 다시 시도해주세요.");
        });
    },
    selectGroupLoad : function (groupId, level, parentGroupId, readOnly, selector) {
        var sel = common.dataChkStr(selector) + " ";

        //자신의 그룹을 가져와 그리기
        common.childSelectGroupLoad(parentGroupId, level, selector);

        //옵션 선택
        $(sel + '.group-selectBox [group-level=' + level + ']').children("[value=" + groupId + "]").attr("selected", true);

        if(readOnly) {
            //선택 불가능하게
            $(sel + '.group-selectBox [group-level=' + level + ']').attr("readonly", true);
        }

        //상위 셀렉트박스 그리기
        for(var i = (level-1);i >= 1;i--){
            var tmpPGroupId = $(sel + '.group-selectBox [group-level=' + (i+1) + ']').children("[selected=selected]").attr("parentGroupId");
            //그룹 그리기
            var data = common.groupData(tmpPGroupId);

            common.childSelectGroupLoad(data.parentGroupId, i, selector);

            //옵션 선택
            $(sel + '.group-selectBox [group-level=' + i + ']').children("[value=" + data.groupId + "]").attr("selected", true);

            if(readOnly) {
                //선택 불가능하게
                $(sel + '.group-selectBox [group-level=' + i + ']').attr("readonly", true);
            }
        }

        //하위 셀렉트박스 그리기
        if($(sel + '.group-selectBox [group-level=' + (level+1) + ']').length > 0){
            //그룹 그리기
            common.childSelectGroupLoad(groupId, level+1, selector);
        }
    },
    menuData : function (menuId) {
        var data = {
            menuId: menuId
        };

        var rs = null;

        $.ajax({
            type: 'GET',
            url: '/services/menu/get',
            contentType: 'application/json',
            async : false,
            data: data
        }).done(function(result) {
            rs = result;
        }).fail(function (error) {
            console.debug(error);
            alert("관리자에게 문의하거나 다시 시도해주세요.");
        });

        return rs;
    },
    menuChildData : function (menuId) {
        var data = {
            parentMenuId: menuId
        };

        var rs = null;

        $.ajax({
            type: 'GET',
            type: 'GET',
            url: '/services/menu/childList',
            contentType: 'application/json',
            async : false,
            data: data
        }).done(function(result) {
            rs = result;
        }).fail(function (error) {
            console.debug(error);
            alert("관리자에게 문의하거나 다시 시도해주세요.");
        });

        return rs;
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
            alert("관리자에게 문의하거나 다시 시도해주세요.");
        });

        return rs;
    },
    comCodeSelectLoad: function(selector, firstOption, optionTxt){
        //불참 사유 셀렉트박스 옵션 그리기
        $(selector).each(function(){
            var optionFlag = common.dataChk(firstOption) ? firstOption : "Y";

            //가져올 코드
            var parentCodeId = $(this).attr("parentCodeId");

            //공통코드 조회
            var comCode = common.comCodeData(parentCodeId);

            if(optionFlag == "Y"){
                optionTxt = common.dataChk(optionTxt) ? optionTxt : '선택';

                //디폴트 값 추가
                var option = "<option value=''>" + optionTxt + "</option>";
                $(this).append(option);
            }

            //선택된 값
            var selVal = $(this).attr("selVal");

            //옵션 그리기
            for(var i = 0;i < comCode.length;i++){
                var selected = "";

                if(selVal == comCode[i].codeName){
                    selected = "selected";
                }

                var option = "<option value='"+ comCode[i].codeName +"' "+ selected +">"+ comCode[i].description +"</option>";
                $(this).append(option);
            }
        });
    },
    resizeGridWidth: function(gridId, area, padding){
        var resizeWidth = $(area).width() - padding; //jQuery-ui의 padding 설정 및 border-width값때문에 넘치는 걸 빼줌.

        if(resizeWidth < 1000){
            resizeWidth = 1000;
        }

        // 그리드의 width 초기화
        $(gridId).setGridWidth( resizeWidth, true);
    },
    /**
     * 기본 0 (일요일)
     * 1 : 월, 2: 화...
     * */
    calculateSundayDate : function(getDate, after, setDay){
        var year = getDate.getFullYear();
        var month = getDate.getMonth()+1;
        var date = getDate.getDate();
        var dayLabel = getDate.getDay();

        setDay = (setDay != null) ? setDay : 0;

        var toSunday = new Date(year, (month-1), date);

        if(after){
            toSunday.setDate(toSunday.getDate() + (7 - dayLabel - setDay));
        }else{
            toSunday.setDate(toSunday.getDate() - (dayLabel - setDay));
        }

        year = toSunday.getFullYear();
        month = toSunday.getMonth()+1;
        date = toSunday.getDate();

        if(month < 10){
            month = "0" + month;
        }
        if(date < 10){
            date = "0" + date;
        }

        return year + "-" + month + "-" + date;
    },
    comma: function(num){
        return num.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
    },
    unComma: function(str){
        return str.toString().replace(/,/gi, "");
    },
    parseYMD : function (str) {
        var y = str.substr(0, 4);
        var m = str.substr(4, 2);
        var d = str.substr(6, 2);

        return new Date(y,m-1,d);
    }
};
common.init();