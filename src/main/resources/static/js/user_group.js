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
                $("#groupId").val(data.groupId);
                $("#parentGroupId").val(data.parentGroupId);

                $("#selGroupId").text(data.groupId);
                $("#selGroupLevel").text(data.groupLevel);
                $("#selGroupName").val(data.groupName);

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
                $("#groupId").val(data.groupId);
                $("#parentGroupId").val(data.parentGroupId);

                $("#selGroupId").text(data.groupId);
                $("#selGroupLevel").text(data.groupLevel);
                $("#selGroupName").val(data.groupName);

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
                $("#groupId").val(data.groupId);
                $("#parentGroupId").val(data.parentGroupId);

                $("#selGroupId").text(data.groupId);
                $("#selGroupLevel").text(data.groupLevel);
                $("#selGroupName").val(data.groupName);
            }
        });
    },
    gridSearch : function(level){
        $("#jqGrid" + level).jqGrid('clearGridData');
        $("#jqGrid" + level).jqGrid('setGridParam', { data: common.groupChildData($("#groupId").val()) });
        $("#jqGrid" + level).trigger("reloadGrid");
    },
    searchClear : function () {
        var _this = this;

        //검색 조건 초기화
        $(".ch-search-area").find("input, select").not(".notClear").val("");
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