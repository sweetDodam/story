var commonCode = {
    init : function () {
        var _this = this;

        console.log("commonCode init");

        //그리드 셋팅
        _this.gridLoad();
        _this.gridSearch(1);

    },
    gridLoad : function () {
        $("#jqGrid1").jqGrid({
            url: '/services/common/code/get',
            mtype: "GET",
            styleUI : 'Bootstrap',
            datatype: "local",
            colModel: jqGridForm.colModel,
            viewrecords: true,
            height: 500,
            autoWidth: true,
            shrinkToFit: true,
            sortable: false,
            sortname: 'codeId',
            loadComplete : function(data){

            },onSelectRow : function (rowid, status, e) {
                $("#jqGrid2").jqGrid('clearGridData');

                var data = $("#jqGrid1").getRowData(rowid);
                data["codeLevel"] = 1;
                commonCode.dataSetting(data);

                $(".disObj").attr("disabled", false);
                $(".disObj").removeClass("disObj");

                commonCode.gridSearch(2);
            }
        });

        $("#jqGrid2").jqGrid({
            url: '/services/common/list',
            mtype: "GET",
            styleUI : 'Bootstrap',
            datatype: "local",
            colModel: jqGridForm.colModel,
            viewrecords: true,
            height: 500,
            autoWidth: true,
            shrinkToFit: true,
            sortable: false,
            sortname: 'codeId',
            loadComplete : function(data){

            },onSelectRow : function (rowid, status, e) {
                var data = $("#jqGrid2").getRowData(rowid);
                data["codeLevel"] = 2;
                commonCode.dataSetting(data);
            }
        });
    },
    gridSearch : function(level){
        $("#jqGrid" + level).jqGrid('clearGridData');
        $("#jqGrid" + level).jqGrid('setGridParam', { data: common.comCodeData($("#codeId").val()) });
        $("#jqGrid" + level).trigger("reloadGrid");
    },
    save : function () {
        var url = "/services/common/code/update";
        var txt = "수정";

        //필수체크 검사
        if(!common.dataChk($("#codeName").val())){
            alert("필수 입력 사항입니다.");
            $("#codeName").focus();

            return;
        }

        //필수체크 검사
        if(!common.dataChk($("#codeId").val())){
            alert("필수 입력 사항입니다.");
            $("#codeId").focus();

            return;
        }

        //필수체크 검사
        if(!common.dataChk($("#description").val())){
            alert("필수 입력 사항입니다.");
            $("#description").focus();

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
            var level = Number($("#codeLevel").val()) - 1;
            commonCode.refreshGrid(level);

        }).fail(function (error) {
            console.debug(txt + "실패");
            alert("관리자에게 문의하거나 다시 시도해주세요.");
        });
    },
    delete : function () {
        var url = "/services/common/code/remove";
        var txt = "삭제";
        var form = $("#StoryForm")[0];
        var formData = new FormData(form);

        var level = Number($("#codeLevel").val());

        if(level < 2){
            var rowData = $("#jqGrid" + (level+1)).getRowData();

            if(rowData.length > 0){
                alert("하위 코드가 존재하면 삭제할 수 없습니다.\n하위 코드를 먼저 삭제해주세요.");
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
            var level = Number($("#codeLevel").val()) - 1;
            commonCode.refreshGrid(level);

        }).fail(function (error) {
            console.debug(txt + "실패");
            alert("관리자에게 문의하거나 다시 시도해주세요.");
        });
    },
    add : function (lowFlag) {
        var url = "/services/common/code/create";
        var txt = "현재 코드에 추가";

        //필수체크 검사
        if(!common.dataChk($("#newCodeName").val())){
            alert("필수 입력 사항입니다.");
            $("#newCodeName").focus();

            return;
        }

        //필수체크 검사
        if(!common.dataChk($("#newCodeId").val())){
            alert("필수 입력 사항입니다.");
            $("#newCodeId").focus();

            return;
        }

        //필수체크 검사
        if(!common.dataChk($("#newDescription").val())){
            alert("필수 입력 사항입니다.");
            $("#newDescription").focus();

            return;
        }

        var form = $("#StoryForm")[0];
        var formData = new FormData(form);

        var codeLevel = Number($("#codeLevel").val());

        formData.set("codeName", $("#newCodeName").val());
        formData.set("description", $("#newDescription").val());
        formData.set("codeId", $("#newCodeId").val());

        //하위 코드으로 추가했다면
        if(lowFlag){
            txt = "하위 코드으로 추가";
            codeLevel += 1;

            if(codeLevel > 2){
                codeLevel = 2;
            }else{
                formData.set("parentCodeId", $("#codeId").val());
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
        }).done(function(resultId) {
            alert(txt + '되었습니다.');

            //하위 코드으로 추가했다면
            if(lowFlag){
                //선택된 레벨의 그리드 재조회
                var level = Number($("#codeLevel").val());

                if(level == 2){
                    level = 1;
                }
                commonCode.refreshGrid(level);
            }else{
                //선택된 레벨의 상위 그리드 재조회
                var level = Number($("#codeLevel").val()) - 1;
                commonCode.refreshGrid(level);
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
            $("#codeId").val("-1");
            $("#parentCodeId").val("-1");

            $("#StoryForm .btn").addClass("disObj");

            $("#jqGrid2").jqGrid('clearGridData');

            commonCode.inputClear();
            commonCode.gridSearch(1);
        }else{
            var rowId = $("#jqGrid" + level).getGridParam("selrow");
            $("#jqGrid" + level + " #" + rowId).trigger("click");
        }
    },
    dataSetting : function (data) {
        $("#codeId").val(data.codeId);
        $("#parentCodeId").val(data.parentCodeId);

        $("#selParentCodeId").text(data.parentCodeId);
        $("#selCodeId").text(data.codeId);
        $("#codeLevel").val(data.codeLevel);
        $("#codeName").val(data.codeName);
        $("#description").val(data.description);

        commonCode.inputClear("add");
    }
};

var jqGridForm = {
    colModel : [
        { label: '코드 ID',			 name: 'codeId',            align: 'left',      width: 140, sorttype:'number'},
        { label: '코드',             name: 'codeName',          align: 'left',      width: 140 },
        { label: '코드설명',         name: 'description',       align: 'left',      width: 140 },
        { label: '부모코드ID',		 name: 'parentCodeId',      hidden: true   },
        { label: '등록일',			 name: 'createDate',        hidden: true   },
        { label: '수정일',			 name: 'update_Date',       hidden: true   }]
};

commonCode.init();