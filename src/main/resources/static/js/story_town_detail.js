var storyTownDetail = {
    init : function () {
        var _this = this;

        console.log("storyTownDetail init");

        //DatePicker 셋팅
        _this.datePickerLoad();

        //그리드 마을동정 셋팅
        _this.gridEventLoad();
        
        //그리드 셋팅
        _this.gridLoad();
        _this.gridSearch();

        //그리드 상세 셋팅
        _this.gridDtlLoad();

        //달력아이콘 click 이벤트
        $('.calendar-icon').click(function(){
            var target = $(this).attr("target");
            $("#" + target).datepicker().focus();
        });

        //윈도우 resize 이벤트
        $(window).bind('resize', function() {
            //그리드 크기 변경
            //common.resizeGridWidth('#jqGridPop', '.modal-body', 2);

            //상세 그리드 크기 변경
            //common.resizeGridWidth('#jqGridPopDtl', '.modal-body', 2);

            //상세 그리드 크기 변경
            //common.resizeGridWidth('#jqGridPopEvent', '.modal-body', 5);
        }).trigger('resize');
    },
    gridLoad : function () {
        $("#jqGridPop").jqGrid({
            url: '/services/story/town/sumList',
            mtype: "GET",
            styleUI : 'Bootstrap',
            datatype: "local",
            rownumbers: true,
            colModel: jqGridPopForm.colModel,
            viewrecords: true,
            height: $(".modal-body").height() * 0.3,
            width: ($(".modal-body").width() - 5) < 1000 ? 1000 : ($(".modal-body").width() - 5),
            rowheight: 20,
            shrinkToFit: true,
            sortable: false,
            footerrow: true,
            userDataOnFooter : false,
            loadComplete : function(data){
                var col = jqGridPopForm.colModel;
                var sumData = { userName: '합계' };

                for(var i = 0;i < col.length;i++){
                    if(common.dataChk(col[i]["index"])){
                        var colName = col[i]["name"];
                        var sum = $("#jqGridPop").jqGrid('getCol',colName, false, 'sum');

                        //재적 컬럼 제외
                        if(colName.indexOf('userCnt') == -1){
                            sum = sum + "_"
                        }
                        sumData[colName] = sum;
                    }
                }
                $('#jqGridPop').jqGrid('footerData', 'set', sumData);

            },onPaging: function (pgButton) {

            }
        });
    },
    gridSearch : function(){
        //영적돌봄 상세 그리드 초기화
        $("#jqGridPopDtl").jqGrid('clearGridData');

        popPage = 1;

        //목자 리스트 그리드
        $("#jqGridPop").setGridParam({
            datatype	: "json",
            postData	: jqGridPopForm.setParam(),
            page        : 1
        }).trigger("reloadGrid");

        //마을동정 그리드
        $("#jqGridPopEvent").setGridParam({
            datatype	: "json",
            postData	: jqGridPopForm.setParam(),
            page        : 1
        }).trigger("reloadGrid");
    },
    searchClear : function () {
        //검색 조건 초기화
        $(".ch-search-area").find("input, select").not(".notClear").val("");

        //현재 날짜
        var today = new Date();
        //현재 날짜의 최근 주일을 가져온다
        var toDate = common.calculateSundayDate(today, false);
        //현재 달의 첫번째 주일을 가져온다.
        today.setDate(1);
        var fromDate = common.calculateSundayDate(today, true);

        $("#fromDate").datepicker("setDate", fromDate);
        $("#toDate").datepicker("setDate", toDate);
    },
    datePickerLoad : function () {
        var _this = this;

        $("#fromDate").datepicker({
            format: "yyyy-mm-dd(D)",
            calendarWeeks: false, //몇째주인지 표시
            autoclose: true,
            todayHighlight: true,
            language: "kr",
            daysOfWeekDisabled: "1,2,3,4,5,6",
            useCurrent: false,
            endDate: new Date()
        }).on('changeDate', function(e){
            _this.dateChk(this);
        });

        $("#toDate").datepicker({
            format: "yyyy-mm-dd(D)",
            calendarWeeks: false, //몇째주인지 표시
            autoclose: true,
            todayHighlight: true,
            language: "kr",
            daysOfWeekDisabled: "1,2,3,4,5,6",
            useCurrent: false,
            endDate: new Date()
        }).on('changeDate', function(e){
            _this.dateChk(this);
        });

        //현재 날짜
        var today = new Date();
        //현재 날짜의 최근 주일을 가져온다
        var toDate = common.calculateSundayDate(today, false);
        //현재 달의 첫번째 주일을 가져온다.
        today.setDate(1);
        var fromDate = common.calculateSundayDate(today, true);

        $("#fromDate").datepicker("setDate", fromDate);
        $("#toDate").datepicker("setDate", toDate);
    },
    dateChk : function (obj) {
        var fromDate = Number($("#fromDate").val().replace(/[^0-9]/g, ""));
        var toDate = Number($("#toDate").val().replace(/[^0-9]/g, ""));

        if(fromDate > toDate){
            var str = "[시작일]이 종료일보다 클 수 없습니다.";

            if($(obj).hasClass('toDate')){
                str = "[종료일]이 시작일보다 작을 수 없습니다.";
            }

            alert(str);
            $(obj).focus();

            return false;
        }
        return true;
    },
    gridDtlLoad : function () {
        $("#jqGridPopDtl").jqGrid({
            url: '/services/story/town/careList',
            mtype: "GET",
            styleUI : 'Bootstrap',
            datatype: "local",
            rownumbers: true,
            colModel: jqGridPopDtlForm.colModel,
            viewrecords: true,
            height: $(".modal-body").height() * 0.16,
            width: ($(".modal-body").width() - 5) < 1000 ? 1000 : ($(".modal-body").width() - 5),
            rowheight: 15,
            shrinkToFit: true,
            sortable: false,
            loadComplete : function(data){

            },onPaging: function (pgButton) {

            }
        });
    },
    setGridDtlData : function (groupId, userId) {
        //해당 그리드 데이터 가져오기
        var data = {};

        data["groupId"] = groupId;
        data["userId"] = userId;
        data["fromDate"] = $("#fromDate").val().replace(/[^0-9]/g, "");
        data["toDate"] = $("#toDate").val().replace(/[^0-9]/g, "");

        $("#jqGridPopDtl").setGridParam({
            datatype	: "json",
            postData	: data,
            page        : 1
        }).trigger("reloadGrid");
    },
    gridEventLoad : function () {
        $("#jqGridPopEvent").jqGrid({
            url: '/services/event/list',
            mtype: "GET",
            styleUI : 'Bootstrap',
            datatype: "local",
            rownumbers: true,
            colModel: jqGridPopEventForm.colModel,
            viewrecords: true,
            height: $(".modal-body").height() * 0.16,
            width: ($(".modal-body").width() - 5) < 1000 ? 1000 : ($(".modal-body").width() - 5),
            rowheight: 15,
            shrinkToFit: true,
            sortable: false,
            loadComplete : function(data){

            },onPaging: function (pgButton) {

            }
        });
    },
};

var popPage = 1;
var popFormatter = {
    dtlModal : function(cellValue,rowObject,options){
        return "<a href='javascript:void(0);' " +
            "onclick='storyTownDetail.setGridDtlData("+ options.groupId +", "+ options.userId +");$(this).parents(\"tr\").trigger(\"click\");'>상세</a>";
    },
    nullChk : function(cellValue,rowObject,options){
        var val = String(cellValue);

        //합계 포맷터일 경우
        if(val.indexOf('_') != -1){
            return val.replace("_", "");
        }else {
            //등록된 스토리가 있는 경우
            if (common.dataChk(options.storyId)) {
                return val;
            } else {
                return '-';
            }
        }
    },
    nullToNum : function(cellValue,rowObject,options){
        if(cellValue == '-'){
            return 0;
        }
        return cellValue;
    }
};

var jqGridPopForm = {
    colModel : [
        { label: '목자이름',    		    name: 'userName',           align: 'center', width: 120     },
        { label: '재적',    		        name: 'userCnt',            align: 'center', width: 100,    index:'userCnt',       summaryType:'sum'},
        { label: '예배참석',	            name: 'worshipYn',       	align: 'center', width: 100, 	formatter: popFormatter.nullChk, unformat:popFormatter.nullToNum, index:'worshipYn',        summaryType:'sum'},
        { label: '리더모임참석',	        name: 'leaderYn',          	align: 'center', width: 100, 	formatter: popFormatter.nullChk, unformat:popFormatter.nullToNum, index:'leaderYn',         summaryType:'sum'},
        { label: '목장모임참석',	        name: 'pastureMeetYn',   	align: 'center', width: 100, 	formatter: popFormatter.nullChk, unformat:popFormatter.nullToNum, index:'pastureMeetYn',    summaryType:'sum'},
        { label: '금요철야참석',	        name: 'fridayWorshipYn',	align: 'center', width: 100, 	formatter: popFormatter.nullChk, unformat:popFormatter.nullToNum, index:'fridayWorshipYn',  summaryType:'sum'},
        { label: '성경',				    name: 'bibleCount',			align: 'center', width: 100,    formatter: popFormatter.nullChk, unformat:popFormatter.nullToNum, index:'bibleCount',       summaryType:'sum'},
        { label: 'QT',    			        name: 'qtCount',          	align: 'center', width: 100,    formatter: popFormatter.nullChk, unformat:popFormatter.nullToNum, index:'qtCount',          summaryType:'sum'},
        { label: '새벽기도',    		    name: 'dawnPrayCount',   	align: 'center', width: 100,    formatter: popFormatter.nullChk, unformat:popFormatter.nullToNum, index:'dawnPrayCount',    summaryType:'sum'},
        { label: '영적돌봄',       		    name: 'dtl',               	align: 'center', width: 100, 	formatter: popFormatter.dtlModal},
        { label: '스토리아이디', 		    name: 'storyId',         	hidden: true 	},
        { label: '소속ID(마을)', 		    name: 'groupId',            hidden: true 	},
        { label: '소속ID(목장)',            name: 'userId',             hidden: true 	}]
    ,setParam : function(){
        var data = common.serializeObject($("#GridPopForm"));

        data["page"] = page;
        data["limit"] = $('#pagerPop  .ui-pg-selbox').val();
        data["fromDate"] = $("#fromDate").val().replace(/[^0-9]/g, "");
        data["toDate"] = $("#toDate").val().replace(/[^0-9]/g, "");

        return data;
    }
};

var jqGridPopDtlForm = {
    colModel : [
        { label: '목자이름',        name: 'userName',           align: 'center',    width: 80 },
        { label: '모임날짜',        name: 'inputDate',          align: 'center',    width: 100 },
        { label: '목자영적돌봄',    name: 'leaderCareStory',    align: 'left',      width: 400 },
        { label: '목장영적돌봄',    name: 'pastureCareStory',   align: 'left',      width: 400 }]
};

var jqGridPopEventForm = {
    colModel : [
        { label: '모임날짜',    name: 'eventDate',      align: 'center',    width: 100 },
        { label: '마을동정',    name: 'eventContent',   align: 'left',      width: 900 }]
};