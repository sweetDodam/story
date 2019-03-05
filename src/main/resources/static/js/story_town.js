var storyTown = {
    init : function () {
        var _this = this;

        console.log("storyTown init");

        //그룹 셀렉트박스 그리기
        _this.groupSelectLoad();

        //DatePicker 셋팅
        _this.datePickerLoad();

        //그리드 셋팅
        _this.gridLoad();
        _this.gridSearch();

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

        //달력아이콘 click 이벤트
        $('.calendar-icon').click(function(){
            var target = $(this).attr("target");
            $("#" + target).datepicker().focus();
        });

        //모달 닫기 이벤트
        $("#saveStoryModal").on('hide.bs.modal', function(e){
            $("#saveStoryModal .modal-content").empty();
            e.stopImmediatePropagation();
        });

        //모달 열린 후 이벤트
        $("#saveStoryModal").on('shown.bs.modal', function(e){
            setTimeout(function() {
                storyTownForm.init();
            }, 300);
        });

        //윈도우 resize 이벤트
        $(window).bind('resize', function() {
            //그리드 크기 변경
            //common.resizeGridWidth('#jqGrid', '.card-body', 5);
        }).trigger('resize');
    },
    updatModalLoad : function (userId, groupId, eventId, inputDate) {
        $("#saveStoryModal").modal();
        $("#saveStoryModal .modal-content").load("/story/town/form?", "userId=" + userId + "&groupId=" + groupId  + "&eventId=" + eventId + "&inputDate=" + inputDate + "&menuId=" + menuId);
    },
    groupSelectLoad : function () {
        var roleId = Number($("#formRoleId").val());

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

        //관리자, 목회자 제외
        $('.group-selectBox [group-level=1]').children("[value=1], [value=2]").remove();
    },
    gridLoad : function () {
        $("#jqGrid").jqGrid({
            url: '/services/story/town/storyList',
            mtype: "GET",
            styleUI : 'Bootstrap',
            datatype: "local",
            jsonReader: {
                root : "rows",  // list 이름
                page :  "page",
                records:  "records",
                total : "total"
            },
            rowNum:10,
            rowList:[10,20,30],
            rownumbers: true,
            colModel: jqGridForm.colModel,
            viewrecords: true,
            height: $("#content-wrapper").height() * 0.5,
            width: ($(".card-body").width() - 5) < 1000 ? 1000 : ($(".card-body").width() - 5),
            pager: "#pager",
            pgbuttons: true,
            pginput : true,
            shrinkToFit: true,
            sortable: false,
            loadComplete : function(data){

            },onPaging: function (pgButton) {
                var gridPage = $("#jqGrid").getGridParam("page");
                var totalPage = $("#jqGrid").getGridParam("total");

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
                    var nowPage = Number($("#pager .ui-pg-input").val());
                    // 입력한 값이 총 페이지수보다 크다면 수행
                    if (totalPage >= nowPage && nowPage > 0) {
                        gridPage = nowPage;
                    }else{
                        $("#pager .ui-pg-input").val(page);
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
    gridSearch : function(){
        page = 1;

        $("#jqGrid").setGridParam({
            datatype	: "json",
            postData	: jqGridForm.setParam(),
            page        : 1
        }).trigger("reloadGrid");
    },
    datePickerLoad : function () {
        var _this = this;

        $("#inputDate").datepicker({
            format: "yyyy-mm-dd(D)",
            calendarWeeks: false, //몇째주인지 표시
            autoclose: true,
            todayHighlight: true,
            language: "kr",
            daysOfWeekDisabled: "1,2,3,4,5,6",
            useCurrent: false,
            endDate: new Date()
        }).on('changeDate', function(e){
            _this.gridSearch();
        });

        //현재 날짜의 최근 주일을 가져온다
        var today = common.calculateSundayDate(new Date(), false);
        $("#inputDate").datepicker("setDate", today);
    }
};

var page = 1;
var formatter = {
    updModal : function(cellValue,rowObject,options){
            return "<a href='javascript:void(0);' " +
                "onclick='storyTown.updatModalLoad(     \""+ common.dataChkStr(options.userId) +"\""+
                                                     ", \""+ common.dataChkStr(options.groupId) +"\""+
                                                     ", \""+ common.dataChkStr(options.eventId) +"\""+
                                                     ", \""+ $("#inputDate").val().replace(/[^0-9]/g, "") +"\");$(this).parents(\"tr\").trigger(\"click\");'>"+
                cellValue +"</a>";
    },
    registFlag : function(cellValue,rowObject,options){
        if(common.dataChk(rowObject)){
            if(common.dataChkStr(options.townStoryId)){
                return "등록";
            }
            return "미등록";
        }
    }
};

var jqGridForm = {
    colModel : [
        { label: '아이디',			name: 'userId',             align: 'center', width: 45},
        { label: '이름',        	name: 'userName',           align: 'center', width: 70,     formatter: formatter.updModal},
        { label: '등록여부',    	name: 'registFlag',			align: 'center', width: 55, 	formatter: formatter.registFlag},
        { label: '소속',    	    name: 'groupDesc',          align: 'left', width: 115 },
        { label: '전화번호',    	name: 'mobile',             align: 'center', width: 100 	},
        { label: '이메일',      	name: 'email',              align: 'center', width: 120 	},
        { label: '알파날짜',    	name: 'alphaDate',          align: 'center', width: 100 	},
        { label: '등반날짜',    	name: 'pastureJoinDate',    align: 'center', width: 100 	},
        { label: 'userSeq',		    name: 'userSeq',            hidden: true},
        { label: '스토리아이디', 	name: 'storyId',			hidden: true },
        { label: '마을스토리아이디',name: 'townStoryId',		hidden: true },
        { label: '마을동정아이디',  name: 'eventId',		    hidden: true },
        { label: '권한',        	name: 'roleDesc',           hidden: true },
        { label: '권한ID',      	name: 'roleId',             hidden: true },
        { label: '소속ID',      	name: 'groupId',            hidden: true },
        { label: '관리자여부',   	name: 'isAdmin',            hidden: true },
        { label: '주소',        	name: 'address',            hidden: true },
        { label: '스토리날짜',  	name: 'inputDate',          hidden: true },
        { label: '상태',        	name: 'status',             hidden: true },
        { label: '등록일',       	name: 'createDate',         hidden: true },
        { label: '수정일',       	name: 'updateDate',         hidden: true }]

    ,setParam : function(){
        var data = common.serializeObject($("#GridForm"));

        data["page"] = page;
        data["limit"] = $('#pager .ui-pg-selbox').val();
        data["inputDate"] = $("#inputDate").val().replace(/[^0-9]/g, "");

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

storyTown.init();