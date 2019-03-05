var storyPastorDetail = {
    init : function () {
        var _this = this;

        console.log("storyPastorDetail init");

        //DatePicker 셋팅
        _this.datePickerLoad();

        //그리드 셋팅
        _this.gridLoad();
        _this.gridSearch();

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
            url: '/services/story/pastor/list',
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
            height: $(".modal-body").height() * 0.7,
            width: ($(".modal-body").width() - 2) < 1000 ? 1000 : ($(".modal-body").width() - 2),
            rowheight: 20,
            pager: "#pagerPop",
            pgbuttons: true,
            pginput : true,
            shrinkToFit: true,
            sortable: false,
            loadComplete : function(data){

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

        //오늘 날짜
        var today = new Date();
        var year = today.getFullYear();
        var month = today.getMonth()+1;
        var date = today.getDate();

        if(month < 10){
            month = "0" + month;
        }
        if(date < 10){
            date = "0" + date;
        }

        //현재 날짜
        var toDate = year + "-" + month + "-" + date;
        //현재 달의 첫번째 날
        var fromDate = year + "-" + month + "-01";

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
            //daysOfWeekDisabled: "1,2,3,4,5,6",
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
            //daysOfWeekDisabled: "1,2,3,4,5,6",
            useCurrent: false,
            endDate: new Date()
        }).on('changeDate', function(e){
            _this.dateChk(this);
        });

        //오늘 날짜
        var today = new Date();
        var year = today.getFullYear();
        var month = today.getMonth()+1;
        var date = today.getDate();

        if(month < 10){
            month = "0" + month;
        }
        if(date < 10){
            date = "0" + date;
        }

        //현재 날짜
        var toDate = year + "-" + month + "-" + date;
        //현재 달의 첫번째 날
        var fromDate = year + "-" + month + "-01";

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
    }
};

var popPage = 1;
var jqGridPopForm = {
    colModel : [
        { label: '심방날짜',    	name: 'visitDate',          align: 'center',    width: 100      },
        { label: '심방장소',	    name: 'visitPlace',    	    align: 'left',      width: 100      },
        { label: '심방내용',	    name: 'summary',          	align: 'left',      width: 160 	    },
        { label: '기도제목',	    name: 'prayers',       	    align: 'left',      width: 160      },
        { label: '기타사항',	    name: 'etc',   	            align: 'left',      width: 160      },
        { label: '등록자',       	name: 'createUser',         align: 'center',    width: 80       },
        { label: '등록일',       	name: 'createDate',         align: 'center',    width: 100      },
        { label: '수정자',       	name: 'updateUser',        	align: 'center',    width: 80       },
        { label: '수정일',       	name: 'updateDate',        	align: 'center',    width: 100      },
        { label: '아이디',      	name: 'userId',             align: 'center',    width: 75,		hidden: true	},
        { label: '스토리아이디', 	name: 'storyId',         	align: 'center',    width: 90, 	    hidden: true 	},
        { label: '권한ID',      	name: 'roleId',             align: 'center',    width: 100, 	hidden: true 	},
        { label: '소속ID',      	name: 'groupId',            align: 'center',    width: 100, 	hidden: true 	}
        ]
    ,setParam : function(){
        var data = common.serializeObject($("#GridPopForm"));

        data["page"] = page;
        data["limit"] = $('#pagerPop  .ui-pg-selbox').val();
        data["fromDate"] = $("#fromDate").val().replace(/[^0-9]/g, "");
        data["toDate"] = $("#toDate").val().replace(/[^0-9]/g, "");

        return data;
    }
};