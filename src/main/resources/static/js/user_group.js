var userGroup = {
    init : function () {
        var _this = this;

        console.log("userGroup init");

        //그리드 셋팅
        _this.gridLoad();
        _this.gridSearch(1);

    },
    gridLoad : function () {
        $("#jqGrid1").jqGrid({
            url: '/services/user/group/get',
            mtype: "GET",
            styleUI : 'Bootstrap',
            datatype: "local",
            colModel: jqGridForm.colModel,
            viewrecords: true,
            height: 500,
            autoWidth: true,
            shrinkToFit: true,
            sortable: false,
            sortname: 'groupId',
            loadComplete : function(data){

            },onSelectRow : function (rowid, status, e) {
                $("#jqGrid2").jqGrid('clearGridData');
                $("#jqGrid3").jqGrid('clearGridData');

                var data = $("#jqGrid1").getRowData(rowid);
                userGroup.dataSetting(data);

                $(".disObj").attr("disabled", false);
                $(".disObj").removeClass("disObj");

                userGroup.gridSearch(2);
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
            sortname: 'groupId',
            loadComplete : function(data){

            },onSelectRow : function (rowid, status, e) {
                $("#jqGrid3").jqGrid('clearGridData');

                var data = $("#jqGrid2").getRowData(rowid);
                userGroup.dataSetting(data);

                userGroup.gridSearch(3);
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
            sortname: 'groupId',
            loadComplete : function(data){

            },onSelectRow : function (rowid, status, e) {
                var data = $("#jqGrid3").getRowData(rowid);
                userGroup.dataSetting(data);
            }
        });
    },
    gridSearch : function(level){
        $("#jqGrid" + level).jqGrid('clearGridData');
        $("#jqGrid" + level).jqGrid('setGridParam', { data: common.groupChildData($("#groupId").val()) });
        $("#jqGrid" + level).trigger("reloadGrid");
    },
    save : function () {
        var url = "/services/user/group/update";
        var txt = "수정";

        //필수체크 검사
        if(!common.dataChk($("#groupName").val())){
            alert("필수 입력 사항입니다.");
            $("#groupName").focus();

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
            var level = Number($("#groupLevel").text()) - 1;
            userGroup.refreshGrid(level);

        }).fail(function (error) {
            console.debug(txt + "실패");
            alert("관리자에게 문의하거나 다시 시도해주세요.");
        });
    },
    delete : function () {
        var url = "/services/user/group/remove";
        var txt = "삭제";
        var form = $("#StoryForm")[0];
        var formData = new FormData(form);

        var level = Number($("#groupLevel").text());

        if(level < 3){
            var rowData = $("#jqGrid" + (level+1)).getRowData();

            if(rowData.length > 0){
                alert("하위 그룹이 존재하면 삭제할 수 없습니다.\n하위 그룹을 먼저 삭제해주세요.");
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
            var level = Number($("#groupLevel").text()) - 1;
            userGroup.refreshGrid(level);

        }).fail(function (error) {
            console.debug(txt + "실패");
            alert("관리자에게 문의하거나 다시 시도해주세요.");
        });
    },
    add : function (lowFlag) {
        var url = "/services/user/group/create";
        var txt = "현재 그룹에 추가";

        //필수체크 검사
        if(!common.dataChk($("#newGroupName").val())){
            alert("필수 입력 사항입니다.");
            $("#newGroupName").focus();

            return;
        }

        var form = $("#StoryForm")[0];
        var formData = new FormData(form);

        var groupLevel = Number($("#groupLevel").text());
        formData.set("groupName", $("#newGroupName").val());

        //하위 그룹으로 추가했다면
        if(lowFlag){
            txt = "하위 그룹으로 추가";
            groupLevel += 1;

            if(groupLevel > 3){
                groupLevel = 3;
            }else{
                formData.set("parentGroupId", $("#groupId").val());
            }
            formData.set("groupId", "");
        }
        formData.append("groupLevel", groupLevel > 3 ? 3 : groupLevel);

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

            //하위 그룹으로 추가했다면
            if(lowFlag){
                //선택된 레벨의 그리드 재조회
                var level = Number($("#groupLevel").text());

                if(level == 3){
                    level = 2;
                }
                userGroup.refreshGrid(level);
            }else{
                //선택된 레벨의 상위 그리드 재조회
                var level = Number($("#groupLevel").text()) - 1;
                userGroup.refreshGrid(level);
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
            $("#groupId").val("-1");
            $("#parentGroupId").val("-1");

            $("#StoryForm .btn").addClass("disObj");
            $(".addObj").addClass("disObj");

            userGroup.inputClear();
            userGroup.gridSearch(1);
        }else{
            var rowId = $("#jqGrid" + level).getGridParam("selrow");
            $("#jqGrid" + level + " #" + rowId).trigger("click");
        }
    },
    dataSetting : function (data) {
        $("#groupId").val(data.groupId);
        $("#parentGroupId").val(data.parentGroupId);

        $("#selGroupId").text(data.groupId);
        $("#groupLevel").text(data.groupLevel);
        $("#groupName").val(data.groupName);

        userGroup.inputClear("add");
    }
};

var jqGridForm = {
    colModel : [
        { label: '그룹 ID',			        name: 'groupId',            align: 'center', width: 80, sorttype:'number'},
        { label: '그룹 이름',               name: 'groupName',          align: 'center', width: 200 },
        { label: '그룹레벨',                name: 'groupLevel',         hidden: true   },
        { label: '부모그룹ID',				name: 'parentGroupId',      hidden: true   },
        { label: '등록일',				    name: 'createDate',         hidden: true   },
        { label: '수정일',				    name: 'update_Date',        hidden: true   }]
};

userGroup.init();