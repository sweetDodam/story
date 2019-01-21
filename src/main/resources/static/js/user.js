var user = {
    init : function () {
        var _this = this;

        console.log("user init");

        //그리드 셋팅
        _this.gridLoad();
        _this.gridSearch();

        //유저 정보 등록 팝업 클릭 이벤트
        $('.saveUserModalBtn').on('click', function () {
            var targetModal = $(this).attr("data-target");

            $(targetModal + " .modal-content").load("/user/form", function() {
                    userForm.init();
                }
            );
        });

        //모달 닫기 이벤트 실행시
        $("#saveUserModal").on('hide.bs.modal', function(e){

            $("#saveUserModal .modal-content").empty();

            e.stopImmediatePropagation();

        });
    },
    updatModalLoad : function (userId) {
        $("#saveUserModal").modal();

        $("#saveUserModal .modal-content").load("/user/form?", "userId=" + userId, function() {
                userForm.init();
            }
        );
    },
    gridLoad : function () {
        $("#jqGrid").jqGrid({
            url: '/services/user/list',
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
                    user.gridTotal();

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
            url: '/services/user/listCnt',
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
    }
};

var page = 1;
var totalCnt = 0;
var totalPage = 0;

var formatter = {
    updModal : function(cellValue,rowObject,options){
        if(common.dataChk(rowObject)){
            return "<a href='javascript:void(0);' onclick='user.updatModalLoad(\""+ options.userId +"\")'>"+ cellValue +"</a>";
        }
    }
};

var jqGridForm = {
    colModel : [
        { label: 'userSeq',    name: 'userSeq',            align: 'center', width: 100, hidden: true},
        { label: '아이디',      name: 'userId',             align: 'center', width: 90 },
        { label: '이름',        name: 'userName',           align: 'center', width: 100, formatter: formatter.updModal},
        { label: '권한',        name: 'roleDesc',           align: 'center', width: 100 },
        { label: '권한ID',      name: 'roleId',             align: 'center', width: 100, hidden: true },
        { label: '소속',        name: 'groupDesc',           align: 'center', width: 100 },
        { label: '소속ID',      name: 'groupId',            align: 'center', width: 100, hidden: true },
        { label: '관리자여부',   name: 'isAdmin',            align: 'center', width: 100 },
        { label: '전화번호',    name: 'mobile',             align: 'center', width: 140 },
        { label: '주소',        name: 'address',            align: 'center', width: 100 },
        { label: '이메일',      name: 'email',              align: 'center', width: 120 },
        { label: '청년부 등록일',name: 'regDate',            align: 'center', width: 130 },
        { label: '알파날짜',    name: 'alphaDate',          align: 'center', width: 100 },
        { label: '상담날짜',    name: 'pastureJoinDate',    align: 'center', width: 100 },
        { label: '상태',        name: 'status',             align: 'center', width: 100, hidden: true },
        { label: '등록일',       name: 'createDate',         align: 'center', width: 100, hidden: true },
        { label: '수정일',       name: 'update_Date',        align: 'center', width: 100, hidden: true },
        { label: '개인정보 동의서 여부',        name: 'isPermission',        align: 'center', width: 100, hidden: true }]
    ,setParam : function(){
        var data = common.serializeObject($("#GridrForm"));

        data["page"] = page;
        data["limit"] = $('.ui-pg-selbox').val();

        return data;
    }
};

user.init();