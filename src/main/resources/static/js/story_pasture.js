var storyPasture = {
    init : function () {
        var _this = this;

        console.log("storyPasture init");

        var roleId = Number($("#roleId").val());
        
        //해당 유저에 지정된 그룹아이디가 있다고 권한이 마을관리자이하일때
        if(common.dataChk($("#formGroupId").val()) && roleId >= 3){
            var groupId = Number($("#formGroupId").val());
            var level = Number($("#formLevel").val());
            var parentGroupId = Number($("#formParentGroupId").val());

            //자신의 그룹을 가져와 그리기
            common.selectGroupLoad(groupId, level, parentGroupId, true);
        }else{
            //그룹 셀렉트박스 최상위 그룹을 가져와 그리기
            common.childSelectGroupLoad(-1, 1);
        }

        //그룹 셀렉트박스 change 이벤트
        $('.group-selectBox select').on('change', function () {
            var level = Number($(this).attr("group-level"));
            var selectCnt = $('.group-selectBox select').length;

            //하위 레벨의 옵션 초기화
            for(var i = (level + 1);i <= selectCnt;i++){
                $('.group-selectBox [group-level=' + i + ']').children().remove();
            }

            //값이 있는 옵션을 선택할 경우
            if(common.dataChk($(this).val())){
                //그룹을 가져와 그리기
                common.childSelectGroupLoad($(this).val(), level+1);
            }

            //검색
            _this.gridSearch();
        });

        //DatePicker 셋팅
        _this.datePickerLoad();

        //그리드 셋팅
        _this.gridLoad();
        _this.gridSearch();

        //달력아이콘 누르면 DatePicker 활성화
        $('.calendar-icon').click(function(){
            var target = $(this).attr("target");
            $("#" + target).datepicker().focus();
        });

        //모달 닫기 이벤트 실행시
        $("#saveStoryModal").on('hide.bs.modal', function(e){

            $("#saveStoryModal .modal-content").empty();

            e.stopImmediatePropagation();

        });
    },
    updatModalLoad : function (userId, storyId, inputDate) {
        $("#saveStoryModal").modal();

        $("#saveStoryModal .modal-content").load("/story/pasture/form?"
                                                ,"userId=" + userId + "&storyId=" + storyId + "&inputDate=" + inputDate
                                                ,function() {
                                                    storyPastureForm.init();
                                                 }
        );
    },
    gridLoad : function () {
        $("#jqGrid").jqGrid({
            url: '/services/story/pasture/storyList',
            mtype: "GET",
            styleUI : 'Bootstrap',
            datatype: "local",
            postData : jqGridForm.setParam(),
            jsonReader: {
                root : "rows",  // list 이름
                page :  function(){ return page;},
                records:  function(){ return totalCnt;},
                total : function(){
                    //총 갯수 구한다
                    storyPasture.gridTotal();

                    var rowNum = Number($("#jqGrid").getGridParam("rowNum"));
                    totalPage = Math.ceil(totalCnt/rowNum);

                    return totalPage;
                }
            },
            rowNum:10,
            rowList:[10,20,30],
            rownumbers: true,
            colModel: jqGridForm.colModel,
            viewrecords: true,
            height: $("#content-wrapper").height() * 0.55,
            pager: "#pager",
            pgbuttons: true,
            pginput : true,
            shrinkToFit: true,
            sortable: false,
            loadComplete : function(data){

            },onPaging: function (pgButton) {
                var gridPage = $("#jqGrid").getGridParam("page");

                if(pgButton == "next"){            // 다음 페이지
                    if(gridPage < totalPage){
                        gridPage += 1;
                    }else{
                        gridPage = page;
                    }
                } else if (pgButton == "prev") {    // 이전 페이지
                    if(gridPage > 1){
                        gridPage -= 1;
                    }else{
                        gridPage = page;
                    }
                } else if (pgButton == "first") {    // 첫 페이지
                        gridPage = 1;
                } else if (pgButton == "last") {    // 마지막 페이지
                    gridPage = totalPage;
                } else if (pgButton == "user") {                // 사용자 입력 페이징 처리
                    var nowPage = Number($(".ui-pg-input").val());
                    // 입력한 값이 총 페이지수보다 크다면 수행
                    if (totalPage >= nowPage && nowPage > 0) {
                        gridPage = nowPage;
                    }else{
                        $(".ui-pg-input").val(page);
                        gridPage = page;
                    }
                } else if(pgButton == "records"){
                    gridPage = 1;
                }

                $("#jqGrid").setGridParam("page", gridPage);
                page = gridPage;

                $("#jqGrid").setGridParam({
                    postData	: jqGridForm.setParam()
                });
            }
        });
    },
    gridTotal : function () {
        $.ajax({
            type: 'GET',
            url: '/services/story/pasture/storyListCnt',
            contentType: 'application/json',
            async : false,
            data: jqGridForm.setParam()
        }).done(function(data) {
            totalCnt = data;
        }).fail(function (error) {
            console.debug(error);
            alert(error);
        });
    },
    gridSearch : function(){
        $("#jqGrid").setGridParam({
            datatype	: "json",
            postData	: jqGridForm.setParam()
        }).trigger("reloadGrid");
    },
    datePickerLoad : function () {
        $("#datePicker").datepicker({
            format: "yyyy-mm-dd(D)",
            calendarWeeks: false, //몇째주인지 표시
            autoclose: true,
            todayHighlight: true,
            language: "kr",
            daysOfWeekDisabled: "1,2,3,4,5,6",
            useCurrent: false
        }).on('changeDate', function(e){
            storyPasture.gridSearch();
        });

        //현재 날짜의 최근 주일을 가져온다
        var today = new Date();

        var year = today.getFullYear();
        var month = today.getMonth()+1;
        var date = today.getDate();
        var dayLabel = today.getDay();

        if(dayLabel < 7){
            var toSunday = new Date(year, (month-1), date);
            toSunday.setDate(toSunday.getDate() - dayLabel);

            year = toSunday.getFullYear();
            month = toSunday.getMonth()+1;
            date = toSunday.getDate();
        }

        if(month < 10){
            month = "0" + month;
        }
        if(date < 10){
            date = "0" + date;
        }

        setDate = year + "-" + month + "-" + date;
        $("#datePicker").datepicker("setDate", setDate);
    }
};

var page = 1;
var totalCnt = 0;
var totalPage = 0;

var formatter = {
    updModal : function(cellValue,rowObject,options){
            return "<a href='javascript:void(0);' " +
                "onclick='storyPasture.updatModalLoad(\""+ common.dataChkStr(options.userId) +"\""+
                                                     ", \""+ common.dataChkStr(options.storyId) +"\""+
                                                     ", \""+ $("#datePicker").val().replace(/[^0-9]/g, "") +"\")'>"+
                cellValue +"</a>";
    },
    registFlag : function(cellValue,rowObject,options){
        if(common.dataChk(rowObject)){
            if(common.dataChkStr(options.storyId)){
                return "등록";
            }
            return "미등록";
        }
    }
};

var jqGridForm = {
    colModel : [
        { label: 'userSeq',    name: 'userSeq',            align: 'center', width: 100, hidden: true},
        { label: '아이디',      name: 'userId',             align: 'center', width: 90, hidden: true },
        { label: '스토리아이디', name: 'storyId',             align: 'center', width: 90, hidden: true },
        { label: '이름',        name: 'userName',           align: 'center', width: 120, formatter: formatter.updModal},
        { label: '등록여부',    name: 'registFlag',          align: 'center', width: 120, formatter: formatter.registFlag},
        { label: '권한',        name: 'roleDesc',           align: 'center', width: 100, hidden: true },
        { label: '권한ID',      name: 'roleId',             align: 'center', width: 100, hidden: true },
        { label: '소속',        name: 'groupDesc',           align: 'center', width: 100, hidden: true },
        { label: '소속ID',      name: 'groupId',            align: 'center', width: 100, hidden: true },
        { label: '관리자여부',   name: 'isAdmin',            align: 'center', width: 100, hidden: true },
        { label: '주소',        name: 'address',            align: 'center', width: 165, hidden: true },
        { label: '전화번호',    name: 'mobile',             align: 'center', width: 100 },
        { label: '이메일',      name: 'email',              align: 'center', width: 120 },
        { label: '청년부 등록일',name: 'regDate',            align: 'center', width: 130 },
        { label: '알파날짜',    name: 'alphaDate',          align: 'center', width: 100 },
        { label: '상담날짜',    name: 'pastureJoinDate',    align: 'center', width: 100 },
        { label: '스토리날짜',  name: 'inputDate',          align: 'center', width: 100, hidden: true },
        { label: '상태',        name: 'status',             align: 'center', width: 100, hidden: true },
        { label: '등록일',       name: 'createDate',         align: 'center', width: 100, hidden: true },
        { label: '수정일',       name: 'updateDate',        align: 'center', width: 100, hidden: true }]
    ,setParam : function(){
        var data = common.serializeObject($("#GridrForm"));

        data["page"] = page;
        data["limit"] = $('.ui-pg-selbox').val();
        data["inputDate"] = $("#datePicker").val().replace(/[^0-9]/g, "");

        //그룹셀렉트 박스
        var selectCnt = $('.group-selectBox select').length;

        //최하위부터 선택괸 그룹 값 검색
        for(var i = selectCnt;i >= 1;i--){
            var selVal = $('.group-selectBox [group-level=' + i + ']').val();
            if(common.dataChk(selVal)){
                data["groupId"] = selVal;
                break;
            }
        }

        return data;
    }
};

storyPasture.init();