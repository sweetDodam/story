var menuMgm = {
    init : function () {
        var _this = this;

        console.log("menuMgm init");

        //그리드 셋팅
        _this.gridLoad();
        _this.gridSearch(1);

    },
    gridLoad : function () {
        $("#jqGrid1").jqGrid({
            url: '/services/user/menu/get',
            mtype: "GET",
            styleUI : 'Bootstrap',
            datatype: "local",
            colModel: jqGridForm.colModel,
            viewrecords: true,
            height: 500,
            autoWidth: true,
            shrinkToFit: true,
            sortable: false,
            sortname: 'menuId',
            loadComplete : function(data){

            },onSelectRow : function (rowid, status, e) {
                $("#jqGrid2").jqGrid('clearGridData');
                $("#jqGrid3").jqGrid('clearGridData');

                var data = $("#jqGrid1").getRowData(rowid);
                menuMgm.dataSetting(data);

                $(".disObj").attr("disabled", false);
                $(".disObj").removeClass("disObj");

                menuMgm.gridSearch(2);
            }
        });

        $("#jqGrid2").jqGrid({
            url: '/services/user/list',
            mtype: "GET",
            styleUI : 'Bootstrap',
            datatype: "local",
            colModel: jqGridForm.colModel,
            viewrecords: true,
            height: 500,
            autoWidth: true,
            shrinkToFit: true,
            sortable: false,
            sortname: 'menuId',
            loadComplete : function(data){

            },onSelectRow : function (rowid, status, e) {
                $("#jqGrid3").jqGrid('clearGridData');

                var data = $("#jqGrid2").getRowData(rowid);
                menuMgm.dataSetting(data);

                menuMgm.gridSearch(3);
            }
        });

        $("#jqGrid3").jqGrid({
            url: '/services/user/list',
            mtype: "GET",
            styleUI : 'Bootstrap',
            datatype: "local",
            colModel: jqGridForm.colModel,
            viewrecords: true,
            height: 500,
            autoWidth: true,
            shrinkToFit: true,
            sortable: false,
            sortname: 'menuId',
            loadComplete : function(data){

            },onSelectRow : function (rowid, status, e) {
                var data = $("#jqGrid3").getRowData(rowid);
                menuMgm.dataSetting(data);
            }
        });
    },
    gridSearch : function(level){
        $("#jqGrid" + level).jqGrid('clearGridData');
        $("#jqGrid" + level).jqGrid('setGridParam', { data: common.menuChildData($("#menuId").val()) });
        $("#jqGrid" + level).trigger("reloadGrid");
    },
    save : function () {
        var url = "/services/menu/update";
        var txt = "수정";

        //필수체크 검사
        if(!common.dataChk($("#menuName").val())){
            alert("필수 입력 사항입니다.");
            $("#menuName").focus();

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

            //선택된 레벨의 상위 그리드 재조회
            var level = Number($("#menuLevel").text()) - 1;
            menuMgm.refreshGrid(level);

        }).fail(function (error) {
            console.debug(txt + "실패");
            alert("관리자에게 문의하거나 다시 시도해주세요.");
        });
    },
    delete : function () {
        var url = "/services/menu/remove";
        var txt = "삭제";
        var form = $("#StoryForm")[0];
        var formData = new FormData(form);

        var level = Number($("#menuLevel").text());

        if(level < 3){
            var rowData = $("#jqGrid" + (level+1)).getRowData();

            if(rowData.length > 0){
                alert("하위 메뉴가 존재하면 삭제할 수 없습니다.\n하위 메뉴를 먼저 삭제해주세요.");
                return;
            }
        }

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

            //선택된 레벨의 상위 그리드 재조회
            var level = Number($("#menuLevel").text()) - 1;
            menuMgm.refreshGrid(level);

        }).fail(function (error) {
            console.debug(txt + "실패");
            alert("관리자에게 문의하거나 다시 시도해주세요.");
        });
    },
    add : function (lowFlag) {
        var url = "/services/menu/create";
        var txt = "현재 메뉴에 추가";

        //필수체크 검사
        if(!common.dataChk($("#newMenuName").val())){
            alert("필수 입력 사항입니다.");
            $("#newMenuName").focus();

            return;
        }

        var form = $("#StoryForm")[0];
        var formData = new FormData(form);

        var menuLevel = Number($("#menuLevel").text());
        formData.set("menuName", $("#newMenuName").val());

        //하위 메뉴로 추가했다면
        if(lowFlag){
            txt = "하위 메뉴로 추가";
            menuLevel += 1;

            if(menuLevel > 3){
                menuLevel = 3;
            }else{
                formData.set("parentMenuId", $("#menuId").val());
            }
            formData.set("menuId", "");
        }
        formData.append("menuLevel", menuLevel > 3 ? 3 : menuLevel);

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

            //하위 메뉴으로 추가했다면
            if(lowFlag){
                //선택된 레벨의 그리드 재조회
                var level = Number($("#menuLevel").text());

                if(level == 3){
                    level = 2;
                }
                menuMgm.refreshGrid(level);
            }else{
                //선택된 레벨의 상위 그리드 재조회
                var level = Number($("#menuLevel").text()) - 1;
                menuMgm.refreshGrid(level);
            }
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
    },refreshGrid : function (level) {
        if(level < 1){
            $("#menuId").val("-1");
            $("#parentMenuId").val("-1");

            $("#StoryForm .btn").addClass("disObj");
            $(".addObj").addClass("disObj");

            menuMgm.inputClear();
            menuMgm.gridSearch(1);
        }else{
            var rowId = $("#jqGrid" + level).getGridParam("selrow");
            $("#jqGrid" + level + " #" + rowId).trigger("click");
        }
    },
    dataSetting : function (data) {
        $("#menuId").val(data.menuId);
        $("#parentMenuId").val(data.parentMenuId);

        $("#selMenuId").text(data.menuId);
        $("#menuLevel").text(data.menuLevel);
        $("#menuName").val(data.menuName);

        menuMgm.inputClear("add");
    }
};

var jqGridForm = {
    colModel : [
        { label: '메뉴 ID',			        name: 'menuId',            align: 'center', width: 80, sorttype:'number'},
        { label: '메뉴 이름',               name: 'menuName',          align: 'center', width: 200 },
        { label: '메뉴레벨',                name: 'menuLevel',         hidden: true   },
        { label: '부모메뉴ID',				name: 'parentMenuId',      hidden: true   },
        { label: '등록일',				    name: 'createDate',         hidden: true   },
        { label: '수정일',				    name: 'update_Date',        hidden: true   }]
};

menuMgm.init();