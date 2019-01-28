var storyPastureDetail = {
    init : function () {
        var _this = this;

        console.log("storyPastureDetail init");

        //DatePicker 셋팅
        _this.datePickerLoad();

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
        }).trigger('resize');
    },
    gridLoad : function () {
        $("#jqGridPop").jqGrid({
            url: '/services/story/pasture/list',
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
            colModel: jqGridPopForm.colModel,
            viewrecords: true,
            height: $(".modal-body").height() * 0.35,
            width: ($(".modal-body").width() - 2) < 1000 ? 1000 : ($(".modal-body").width() - 2),
            rowheight: 20,
            pager: "#pagerPop",
            pgbuttons: true,
            pginput : true,
            shrinkToFit: true,
            sortable: false,
            footerrow: true,
            userDataOnFooter : false,
            loadComplete : function(data){
                var col = jqGridPopForm.colModel;
                var sumData = { inputDate: '합계' };

                for(var i = 0;i < col.length;i++){
                    if(common.dataChk(col[i]["index"])){
                        var colName = col[i]["name"];
                        var sum = $("#jqGridPop").jqGrid('getCol',colName, false, 'sum');

                        if(colName.indexOf('Yn') != -1){
                            sum = sum + "_"
                        }
                        sumData[colName] = sum;
                    }
                }
                $('#jqGridPop').jqGrid('footerData', 'set', sumData);

            },onPaging: function (pgButton) {
                var gridPage = $("#jqGridPop").getGridParam("page");
                var totalPage = $("#jqGridPop").getGridParam("total");

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
                    var nowPage = Number($("#pagerPop .ui-pg-input").val());
                    // 입력한 값이 총 페이지수보다 크다면 수행
                    if (totalPage >= nowPage && nowPage > 0) {
                        gridPage = nowPage;
                    }else{
                        $("#pagerPop .ui-pg-input").val(page);
                        gridPage = page;
                    }
                } else if(pgButton == "records"){
                    gridPage = 1;
                }

                $("#jqGridPop").setGridParam("page", gridPage);
                popPage = gridPage;

                $("#jqGridPop").setGridParam({
                    postData	: jqGridPopForm.setParam()
                });
            }
        });
    },
    gridSearch : function(){
        $("#jqGridPopDtl").jqGrid('clearGridData');

        popPage = 1;

        $("#jqGridPop").setGridParam({
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
            styleUI : 'Bootstrap',
            datatype: "local",
       //     rowNum: -1,
            rownumbers: true,
            colModel: jqGridPopDtlForm.colModel,
            viewrecords: true,
            height: $(".modal-body").height() * 0.15,
            width: ($(".modal-body").width() - 2) < 1000 ? 1000 : ($(".modal-body").width() - 2),
            shrinkToFit: true,
            sortable: false,
            loadComplete : function(data){

            }
        });
    },
    setGridDtlData : function (rowId) {
        //해당 그리드 데이터 가져오기
        var data = new Array();
        data.push($("#jqGridPop").getRowData(rowId));

        $("#jqGridPopDtl").jqGrid('clearGridData');
        $("#jqGridPopDtl").jqGrid('setGridParam', { data: data });
        $("#jqGridPopDtl").trigger("reloadGrid");
    }
};

var popPage = 1;
var popFormatter = {
    dtlModal : function(cellValue,rowObject,options){
        return "<a href='javascript:void(0);' " +
            "onclick='storyPastureDetail.setGridDtlData("+ rowObject.rowId +")'>상세</a>";
    },
    YnDesc : function(cellValue,rowObject,options){
        var val = String(cellValue);

        //합계 포맷터일 경우
        if(val.indexOf('_') != -1){
            return val.replace("_", "");
        }else{
            var str = "참석";

            if(val == '0'){
                str = "불참석";
            }
            return str;
        }
    },
    YnToNum : function(cellValue,rowObject,options){
        var num = 1;

        if(cellValue == '불참석'){
            num = 0;
        }
        return num;
    }
};

var jqGridPopForm = {
    colModel : [
        { label: '모임날짜',    		    name: 'inputDate',          align: 'center', width: 120     },
        { label: '예배</br>참석여부',	    name: 'worshipYn',       	align: 'center', width: 100, 	formatter: popFormatter.YnDesc, unformat:popFormatter.YnToNum,  index:'worshipYn',        summaryType:'sum'},
        { label: '예배</br>불참사유',	    name: 'worshipDesc',    	align: 'center', width: 120     },
        { label: '리더모임</br>참석여부',	name: 'leaderYn',          	align: 'center', width: 100 , 	formatter: popFormatter.YnDesc, unformat:popFormatter.YnToNum, index:'leaderYn',         summaryType:'sum'},
        { label: '리더모임</br>불참사유',	name: 'leaderDesc',       	align: 'center', width: 120     },
        { label: '목장모임</br>참석여부',	name: 'pastureMeetYn',   	align: 'center', width: 100, 	formatter: popFormatter.YnDesc, unformat:popFormatter.YnToNum, index:'pastureMeetYn',    summaryType:'sum'},
        { label: '금요철야</br>참석여부',	name: 'fridayWorshipYn',	align: 'center', width: 100, 	formatter: popFormatter.YnDesc, unformat:popFormatter.YnToNum, index:'fridayWorshipYn',  summaryType:'sum'},
        { label: '성경',				    name: 'bibleCount',			align: 'center', width: 100,    index:'bibleCount',               summaryType:'sum'       },
        { label: 'QT',    			        name: 'qtCount',          	align: 'center', width: 100,    index:'qtCount',                  summaryType:'sum'       },
        { label: '새벽기도',    		    name: 'dawnPrayCount',   	align: 'center', width: 100,    index:'dawnPrayCount',            summaryType:'sum'       },
        { label: '상세',       		        name: 'dtl',               	align: 'center', width: 50, 	formatter: popFormatter.dtlModal},
        { label: '기도제목',                name: 'prayers',            align: 'center', width: 100,	hidden: true	},
        { label: '기타사항',                name: 'etc',            	align: 'center', width: 100,	hidden: true	},
        { label: '아이디',      		    name: 'userId',             align: 'center', width: 75,		hidden: true	},
        { label: '스토리아이디', 		    name: 'storyId',         	align: 'center', width: 90, 	hidden: true 	},
        { label: '권한ID',      		    name: 'roleId',             align: 'center', width: 100, 	hidden: true 	},
        { label: '소속ID',      		    name: 'groupId',            align: 'center', width: 100, 	hidden: true 	},
        { label: '등록일',       		    name: 'createDate',         align: 'center', width: 100, 	hidden: true 	},
        { label: '수정일',       		    name: 'updateDate',        	align: 'center', width: 100, 	hidden: true 	}]
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
        { label: '모임날짜',    name: 'inputDate',      align: 'center',    width: 100 },
        { label: '기도제목',    name: 'prayers',        align: 'left',      width: 600 },
        { label: '기타사항',    name: 'etc',            align: 'left',      width: 400 }]
};