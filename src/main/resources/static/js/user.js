var user = {
    init : function () {
        var _this = this;

        console.log("user init");

        //권한 셀렉트 박스 그리기
        common.comCodeSelectLoad(".comCode");

        //그룹 셀렉트 박스 그리기
        _this.groupSelectLoad();

        //그리드 셋팅
        _this.gridLoad();
        _this.gridSearch();

        //그룹 셀렉트박스 change 이벤트
        $('#GridForm .group-selectBox select').on('change', function () {
            var level = Number($(this).attr("group-level"));
            var selectCnt = $('#GridForm .group-selectBox select').length;

            //하위 레벨의 옵션 초기화
            for(var i = (level + 1);i <= selectCnt;i++){
                $('#GridForm .group-selectBox [group-level=' + i + ']').children().remove();
            }

            //값이 있는 옵션을 선택할 경우
            if(common.dataChk($(this).val())){
                //그룹을 가져와 그리기
                common.childSelectGroupLoad($(this).val(), level+1, '#GridForm');
            }
        });

        //유저 정보 등록 팝업 클릭 이벤트
        $('.saveUserModalBtn').on('click', function () {
            var targetModal = $(this).attr("data-target");
            $(targetModal + " .modal-content").load("/user/form");
        });

        //모달 닫기 이벤트 실행시
        $("#saveUserModal").on('hide.bs.modal', function(e){
            $("#saveUserModal .modal-content").empty();
            e.stopImmediatePropagation();
        });

        //모달 열린 후 이벤트
        $("#saveUserModal").on('shown.bs.modal', function(e){
            setTimeout(function() {
                userForm.init();
            }, 100);
        });

        //윈도우 resize 이벤트
        $(window).bind('resize', function() {
            //그리드 크기 변경
            //     common.resizeGridWidth('#jqGrid', '.card-body', 5);
        }).trigger('resize');
    },
    updatModalLoad : function (userId) {
        $("#saveUserModal").modal();
        $("#saveUserModal .modal-content").load("/user/form?", "userId=" + userId);
    },
    groupSelectLoad : function () {
        //그룹 셀렉트박스 최상위 그룹을 가져와 그리기
        common.childSelectGroupLoad(-1, 1, '#GridForm');
    },
    gridLoad : function () {
        $("#jqGrid").jqGrid({
            url: '/services/user/list',
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
            // autowidth:true,
            width: 1500,
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
        $('#GridForm .group-selectBox select').children().remove();

        //그룹 셀렉트 박스 그리기
        _this.groupSelectLoad();
    }
};

var page = 1;
var formatter = {
    updModal : function(cellValue,rowObject,options){
        if(common.dataChk(rowObject)){
            return "<a href='javascript:void(0);' onclick='user.updatModalLoad(\""+ options.userId +"\");$(this).parents(\"tr\").trigger(\"click\");'>"+ cellValue +"</a>";
        }
    },isFormatter : function(cellValue,rowObject,options){
        if(cellValue){
            return "예";
        }else{
            return "아니오";
        }
    }
};

var jqGridForm = {
    colModel : [

        { label: '아이디',				    name: 'userId',             align: 'center', width: 95 	},
        { label: '이름',				    name: 'userName',           align: 'center', width: 95, formatter: formatter.updModal},
        { label: '권한',				    name: 'roleDesc',           align: 'center', width: 100 },
        { label: '소속',				    name: 'groupDesc',			align: 'center', width: 150 },
        { label: '시스템 관리자',           name: 'isAdmin',            align: 'center', width: 100, formatter: formatter.isFormatter},
        { label: '전화번호',			    name: 'mobile',             align: 'center', width: 130 },
        { label: '개인정보<br>동의 여부',   name: 'isPermission',		align: 'center', width: 90, formatter: formatter.isFormatter},
        { label: '이메일',				    name: 'email',              align: 'center', width: 140 },
        { label: '주소',				    name: 'address',            align: 'center', width: 200 },
        { label: '청년부 등록일',		    name: 'regDate',            align: 'center', width: 110 },
        { label: '알파날짜',			    name: 'alphaDate',          align: 'center', width: 110 },
        { label: '등반날짜',			    name: 'pastureJoinDate',    align: 'center', width: 110 },
        { label: 'userSeq',			        name: 'userSeq',            align: 'center', width: 100, hidden: true},
        { label: '권한ID',			        name: 'roleId',             align: 'center', width: 100, hidden: true },
        { label: '소속ID',			        name: 'groupId',            align: 'center', width: 100, hidden: true },
        { label: '상태',				    name: 'status',             align: 'center', width: 100, hidden: true },
        { label: '등록일',				    name: 'createDate',         align: 'center', width: 100, hidden: true },
        { label: '수정일',				    name: 'update_Date',        align: 'center', width: 100, hidden: true }]
    ,setParam : function(){
        var data = common.serializeObject($("#GridForm"));

        data["page"] = page;
        data["limit"] = $('#pager .ui-pg-selbox').val();

        //그룹셀렉트 박스
        var selectCnt = $('#GridForm .group-selectBox select').length;

        //그룹ID 초기화
        data["groupId"] = "";

        //최하위부터 선택괸 그룹 값 검색
        for(var i = selectCnt;i >= 1;i--){
            var selVal = $('#GridForm .group-selectBox [group-level=' + i + ']').val();
            if(common.dataChk(selVal)){
                data["groupId"] = selVal;
                break;
            }
        }

        return data;
    }
};

user.init();