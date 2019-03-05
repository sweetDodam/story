var storyPastureList = {
    init : function () {
        var _this = this;

        console.log("storyPastureList init");

        //그룹 셀렉트 박스 그리기
        _this.groupSelectLoad();

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
        });

        //모달 닫기 이벤트
        $("#detailStoryModal").on('hide.bs.modal', function(e){
            $("#detailStoryModal .modal-content").empty();

            //그리드 크기 변경
            //common.resizeGridWidth('#jqGrid', '.card-body', 10);

            e.stopImmediatePropagation();
        });

        //모달 닫인 후 이벤트
        $("#detailStoryModal").on('hidden.bs.modal', function(e){
            //그리드 크기 변경
            //common.resizeGridWidth('#jqGrid', '.card-body', 10);
        });

        //모달 열린 후 이벤트
        $("#detailStoryModal").on('shown.bs.modal', function(e){
            setTimeout(function() {
                storyPastureDetail.init();
            }, 300);
        });

        //윈도우 resize 이벤트
        $(window).bind('resize', function() {
            //그리드 크기 변경
            //common.resizeGridWidth('#jqGrid', '.card-body', 10);
        }).trigger('resize');
    },
    detailModalLoad : function (userId) {
        $("#detailStoryModal").modal();
        $("#detailStoryModal .modal-content").load("/story/pasture/detail?","userId=" + userId + "&menuId=" + menuId);
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

            //마을 관리자 일때 목장그룹 선택 활성화
            if(roleId == 3){
                $('.group-selectBox select').last().attr("readonly", false);
            }
        }else{
            //그룹 셀렉트박스 최상위 그룹을 가져와 그리기
            common.childSelectGroupLoad(-1, 1);
        }

        //관리자, 목회자 제외
        $('.group-selectBox [group-level=1]').children("[value=1], [value=2]").remove();
    },
    gridLoad : function () {
        $("#jqGrid").jqGrid({
            url: '/services/story/pasture/storyList',
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
            width: ($(".card-body").width() - 10) < 1000 ? 1000 : ($(".card-body").width() - 10),
            //autowidth: true,
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
    searchClear : function () {
        var _this = this;

        //검색 조건 초기화
        $(".ch-search-area").find("input, select").not(".notClear").val("");

        //그룹 셀렉트 박스 초기화
        $('.group-selectBox select').children().remove();

        //그룹 셀렉트 박스 그리기
        _this.groupSelectLoad();
    }
};

var page = 1;
var formatter = {
    dtlModal : function(cellValue,rowObject,options){
        return "<a href='javascript:void(0);' " +
            "onclick='storyPastureList.detailModalLoad(\""+ common.dataChkStr(options.userId) +"\");$(this).parents(\"tr\").trigger(\"click\");'>"+cellValue +"</a>";
    }
};

var jqGridForm = {
    colModel : [
        { label: '아이디',      	name: 'userId',             align: 'center', width: 150		},
        { label: '이름',        	name: 'userName',           align: 'center', width: 150, 	formatter: formatter.dtlModal},
        { label: '소속',    	    name: 'groupDesc',          align: 'left', width: 250     },
        { label: '전화번호',    	name: 'mobile',             align: 'center', width: 180 	},
        { label: '이메일',      	name: 'email',              align: 'center', width: 200 	},
        { label: '알파날짜',    	name: 'alphaDate',          align: 'center', width: 180 	},
        { label: '등반날짜',    	name: 'pastureJoinDate',    align: 'center', width: 180 	},
        { label: 'userSeq',		    name: 'userSeq',            align: 'center', width: 100,	hidden: true },
        { label: '스토리아이디',	name: 'storyId',         	align: 'center', width: 90, 	hidden: true },
        { label: '권한',        	name: 'roleDesc',           align: 'center', width: 100, 	hidden: true },
        { label: '권한ID',      	name: 'roleId',             align: 'center', width: 100, 	hidden: true },
        { label: '소속ID',      	name: 'groupId',            align: 'center', width: 100, 	hidden: true },
        { label: '관리자여부',   	name: 'isAdmin',            align: 'center', width: 100, 	hidden: true },
        { label: '주소',        	name: 'address',            align: 'center', width: 165, 	hidden: true },
        { label: '스토리날짜',  	name: 'inputDate',          align: 'center', width: 100, 	hidden: true },
        { label: '상태',        	name: 'status',             align: 'center', width: 100, 	hidden: true },
        { label: '등록일',       	name: 'createDate',         align: 'center', width: 100, 	hidden: true },
        { label: '수정일',       	name: 'updateDate',        	align: 'center', width: 100, 	hidden: true }]
    ,setParam : function(){
        var data = common.serializeObject($("#GridForm"));

        data["page"] = page;
        data["limit"] = $('#pager .ui-pg-selbox').val();

        //그룹셀렉트 박스
        var selectCnt = $('.group-selectBox select').length;

        //그룹ID 초기화
        data["groupId"] = "";

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

storyPastureList.init();