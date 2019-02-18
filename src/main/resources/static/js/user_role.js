var userRole = {
    init : function () {
        var _this = this;

        console.log("userRole init");

        //그리드 셋팅
        _this.gridLoad();
        _this.gridSearch(1);

    },
    gridLoad : function () {
        $("#jqGrid").jqGrid({
            url: '/services/user/role/list',
            mtype: "GET",
            styleUI : 'Bootstrap',
            datatype: "local",
            jsonReader: {
                root : "rows",  // list 이름
                page :  "page",
                records:  "records",
                total : "total"
            },
            colModel: jqGridForm.colModel,
            viewrecords: true,
            height: 500,
            autoWidth: true,
            shrinkToFit: true,
            sortable: false,
            loadComplete : function(data){

            },onSelectRow : function (rowid, status, e) {
                var data = $("#jqGrid").getRowData(rowid);
                userRole.dataSetting(data);

                $(".disObj").attr("disabled", false);
                $(".disObj").removeClass("disObj");
            }
        });
    },
    gridSearch : function(){
        page = 1;
        $("#jqGrid").setGridParam({
            datatype	: "json",
            page        : 1
        }).trigger("reloadGrid");
    },
    save : function () {
        var url = "/services/user/role/update";
        var txt = "수정";

        //필수체크 검사
        if(!common.dataChk($("#roleName").val())){
            alert("필수 입력 사항입니다.");
            $("#roleName").focus();

            return;
        }

        //필수체크 검사
        if(!common.dataChk($("#description").val())){
            alert("필수 입력 사항입니다.");
            $("#description").focus();

            return;
        }

        //필수체크 검사
        if(!common.dataChk($("#roleOrder").val())){
            alert("필수 입력 사항입니다.");
            $("#roleOrder").focus();

            return;
        }

        var form = $("#StoryForm")[0];
        var formData = new FormData(form);

        if(!confirm(txt + "하시겠습니까?")){
            return;
        }

        $.ajax({
            type: 'POST',
            url: url,
            processData: false,
            contentType: false,
            cache : false,
            data : formData
        }).done(function() {
            alert(txt + '되었습니다.');

            //그리드 재조회
            userRole.refreshGrid();

        }).fail(function (error) {
            console.debug(txt + "실패");
            alert("관리자에게 문의하거나 다시 시도해주세요.");
        });
    },
    delete : function () {
        var url = "/services/user/role/remove";
        var txt = "삭제";
        var form = $("#StoryForm")[0];
        var formData = new FormData(form);

        if(!confirm(txt + "하시겠습니까?")){
            return;
        }

        $.ajax({
            type: 'POST',
            url: url,
            processData: false,
            contentType: false,
            cache : false,
            data : formData
        }).done(function() {
            alert(txt + '되었습니다.');

            //그리드 재조회
            userRole.refreshGrid();

        }).fail(function (error) {
            console.debug(txt + "실패");
            alert("관리자에게 문의하거나 다시 시도해주세요.");
        });
    },
    add : function () {
        var url = "/services/user/role/create";
        var txt = "권한 추가";

        //필수체크 검사
        if(!common.dataChk($("#newRoleName").val())){
            alert("필수 입력 사항입니다.");
            $("#newRoleName").focus();

            return;
        }

        //필수체크 검사
        if(!common.dataChk($("#newDescription").val())){
            alert("필수 입력 사항입니다.");
            $("#newDescription").focus();

            return;
        }

        //필수체크 검사
        if(!common.dataChk($("#newRoleOrder").val())){
            alert("필수 입력 사항입니다.");
            $("#newRoleOrder").focus();

            return;
        }

        var form = $("#StoryForm")[0];
        var formData = new FormData(form);

        formData.set("roleName", $("#newRoleName").val());
        formData.set("description", $("#newDescription").val());
        formData.set("roleOrder", $("#newRoleOrder").val());

        if(!confirm(txt + "하시겠습니까?")){
            return;
        }

        $.ajax({
            type: 'POST',
            url: url,
            processData: false,
            contentType: false,
            cache : false,
            data : formData
        }).done(function(resultId) {
            alert(txt + '되었습니다.');

            userRole.refreshGrid();

        }).fail(function (error) {
            console.debug(txt + "실패");
            alert("관리자에게 문의하거나 다시 시도해주세요.");
        });
    },inputClear : function (clearFlag) {
        var add = true;
        var save = true;

        if(clearFlag == "add"){
            save = false;
        }else if(clearFlag == "save"){
            add = false;
        }

        if(add){
            $(".addObj").val("");
        }
        if(save) {
            $(".saveObj").val("");
            $(".saveObj").text("");
        }
    },refreshGrid : function () {
        $("#roleId").val("");

        $("#StoryForm .btn").addClass("disObj");

        userRole.inputClear();
        userRole.gridSearch();
    },
    dataSetting : function (data) {
        $("#roleId").val(data.roleId);

        $("#selRoleId").text(data.roleId);
        $("#roleName").val(data.roleName);
        $("#description").val(data.description);
        $("#roleOrder").val(data.roleOrder);

        userRole.inputClear("add");
    }
};

var jqGridForm = {
    colModel : [
        { label: '권한 ID',			 name: 'roleId',            align: 'center',      width: 80},
        { label: '권한',             name: 'roleName',          align: 'left',        width: 210 },
        { label: '권한설명',         name: 'description',       align: 'left',        width: 120 },
        { label: '권한순위',		 name: 'roleOrder',         align: 'center',      width: 80},
        { label: '등록일',			 name: 'createDate',        hidden: true   },
        { label: '수정일',			 name: 'update_Date',       hidden: true   }]
};

userRole.init();