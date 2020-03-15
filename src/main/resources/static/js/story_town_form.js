var storyTownForm = {
    init : function () {
        var _this = this;

        console.log("storyTownForm init");

        //DatePicker 셋팅
        _this.datePickerLoad();

        //그리드 셋팅
        _this.gridLoad();
        _this.gridSearch();

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
            url: '/services/story/town/list',
            mtype: "GET",
            styleUI : 'Bootstrap',
            datatype: "local",
            rownumbers: true,
            colModel: jqGridPopForm.colModel,
            viewrecords: true,
            height: $(".modal-body").height() * 0.31,
            width: ($(".modal-body").width() - 2) < 1000 ? 1000 : ($(".modal-body").width() - 2),
            rowheight: 20,
            shrinkToFit: true,
            sortable: false,
            footerrow: true,
            userDataOnFooter : false,
            loadComplete : function(data){
                var col = jqGridPopForm.colModel;
                var sumData = { userName: '합계' };

                for(var i = 0;i < col.length;i++){
                    if(common.dataChk(col[i]["index"])){
                        var colName = col[i]["name"];
                        var sum = $("#jqGridPop").jqGrid('getCol',colName, false, 'sum');

                        //재적 컬럼 제외
                        if(colName.indexOf('userCnt') == -1){
                            sum = sum + "_"
                        }
                        sumData[colName] = sum;
                    }
                }
                $('#jqGridPop').jqGrid('footerData', 'set', sumData);

            },onPaging: function (pgButton) {

            }
        });
    },
    gridSearch : function(){
        $("#jqGridPopDtl").jqGrid('clearGridData');

        $("#jqGridPop").setGridParam({
            datatype	: "json",
            postData	: jqGridPopForm.setParam()
        }).trigger("reloadGrid");
    },
    datePickerLoad : function () {
        var _this = this;

        $("#inputDatePop").datepicker({
            format: "yyyy-mm-dd(D)",
            calendarWeeks: false, //몇째주인지 표시
            autoclose: true,
            todayHighlight: true,
            language: "kr",
            daysOfWeekDisabled: "1,2,3,4,5,6",
            useCurrent: false,
            endDate: new Date()
        }).on('changeDate', function(e){

        });

        var formInputDate =$("#formInputDate").val();
        var pattern = /(\d{4})(\d{2})(\d{2})/;
        var dt = new Date(formInputDate.replace(pattern,'$1'), Number(formInputDate.replace(pattern,'$2')) - 1, formInputDate.replace(pattern,'$3'));

        $("#inputDatePop").datepicker("setDate", dt);
    },
    setGridDtlData : function (rowId) {
        //해당 그리드 데이터 가져오기
        var data = $("#jqGridPop").getRowData(rowId);

        for(var obj in data){
            if(obj == "storyId" || obj == "townStoryId" || obj == "leaderCareStory" || obj == "pastureCareStory" || obj == "userName"){
                common.dataSet($("#" + obj), data[obj]);
            }
        }
    },
    save : function (careFlag) {
        var _this = this;
        var url = "/services/story/town/createUpdate";
        var txt = "저장";


        //영적돌봄 저장이라면
        if(careFlag){
            $(".careRequired").attr("required", true);
            $(".eventRequired").attr("required", false);

            if($("#storyId").val() == ''){
                alert("저장할 목자를 선택해주세요.");
                return;
            }
        }else{
            url = "/services/event/createUpdate";

            $(".careRequired").attr("required", false);
            $(".eventRequired").attr("required", true);
        }

        //필수체크 검사
        if(!common.formChk($("#StoryForm"))){
            return;
        }

        var form = $("#StoryForm")[0];
        var formData = new FormData(form);

        formData.append("inputDate", $("#formInputDate").val());

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

            //부모 그리드 재조회
            storyTown.gridSearch();

            //그리드 재조회
            _this.gridSearch();

            //영적돌봄 저장이라면
            if(careFlag){
                $("#townStoryId").val(resultId);
            }else{
                $("#eventId").val(resultId);
            }

        }).fail(function (error) {
            console.debug(txt + "실패");
            alert("관리자에게 문의하거나 다시 시도해주세요.");
        });
    },
    delete : function (careFlag) {
        var _this = this;
        var url = "/services/story/town/remove";
        var txt = "삭제";
        var form = $("#StoryForm")[0];
        var formData = new FormData(form);

        //영적돌봄 삭제라면
        if(careFlag){
            if($("#storyId").val() == ''){
                alert("삭제할 목자를 선택해주세요.");
                return;
            }
        }else{
            if($("#eventId").val() == ''){
                alert("삭제할 이벤트가 없습니다.");
                return;
            }
            url = "/services/event/remove";
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

            //부모 그리드 재조회
            storyTown.gridSearch();

            //그리드 재조회
            _this.gridSearch();

            //영적돌봄 삭제라면
            if(careFlag){
                $("#townStoryId").val('');
                $("#leaderCareStory").val('');
                $("#pastureCareStory").val('');
            }else{
                $("#eventId").val('');
                $("#eventContent").val('');
            }

        }).fail(function (error) {
            console.debug(txt + "실패");
            alert("관리자에게 문의하거나 다시 시도해주세요.");
        });
    }
};

var popFormatter = {
    dtlModal : function(cellValue,rowObject,options){
        //등록된 스토리가 있는 경우
        if (common.dataChk(options.storyId)) {
            if (common.dataChk(options.townStoryId)) {
                return "<a href='javascript:void(0);' " +
                    "onclick='storyTownForm.setGridDtlData("+ rowObject.rowId +");$(this).parents(\"tr\").trigger(\"click\");'>등록</a>";
            }else{
                return "<a href='javascript:void(0);' " +
                    "onclick='storyTownForm.setGridDtlData("+ rowObject.rowId +");$(this).parents(\"tr\").trigger(\"click\");'>미등록</a>";
            }
        } else {
            return '-';
        }
    },
    nullChk : function(cellValue,rowObject,options){
        var val = String(cellValue);

        //합계 포맷터일 경우
        if(val.indexOf('_') != -1){
            return val.replace("_", "");
        }else {
            //등록된 스토리가 있는 경우
            if (common.dataChk(options.storyId)) {
                return val;
            } else {
                return '-';
            }
        }
    },
    nullToNum : function(cellValue,rowObject,options){
        if(cellValue == '-'){
            return 0;
        }
        return cellValue;
    }
};

var jqGridPopForm = {
    colModel : [
        { label: '목장',    		        name: 'userName',           align: 'center', width: 95     },
        { label: '재적',    		        name: 'userCnt',            align: 'center', width: 95,    index:'userCnt',       summaryType:'sum'},
        { label: '예배참석',	            name: 'worshipYn',       	align: 'center', width: 95, 	formatter: popFormatter.nullChk, unformat:popFormatter.nullToNum, index:'worshipYn',        summaryType:'sum'},
        { label: '리더모임참석',	        name: 'leaderYn',          	align: 'center', width: 100, 	formatter: popFormatter.nullChk, unformat:popFormatter.nullToNum, index:'leaderYn',         summaryType:'sum'},
        { label: '목장모임참석',	        name: 'pastureMeetYn',   	align: 'center', width: 100, 	formatter: popFormatter.nullChk, unformat:popFormatter.nullToNum, index:'pastureMeetYn',    summaryType:'sum'},
        { label: '금요철야참석',	        name: 'fridayWorshipYn',	align: 'center', width: 100, 	formatter: popFormatter.nullChk, unformat:popFormatter.nullToNum, index:'fridayWorshipYn',  summaryType:'sum'},
        { label: '성경',				    name: 'bibleCount',			align: 'center', width: 95,    formatter: popFormatter.nullChk, unformat:popFormatter.nullToNum, index:'bibleCount',       summaryType:'sum'},
        { label: 'QT',    			        name: 'qtCount',          	align: 'center', width: 95,    formatter: popFormatter.nullChk, unformat:popFormatter.nullToNum, index:'qtCount',          summaryType:'sum'},
        { label: '새벽기도',    		    name: 'dawnPrayCount',   	align: 'center', width: 95,    formatter: popFormatter.nullChk, unformat:popFormatter.nullToNum, index:'dawnPrayCount',    summaryType:'sum'},
        { label: '영적돌봄',       		    name: 'dtl',               	align: 'center', width: 95, 	formatter: popFormatter.dtlModal},
        { label: '목자영적돌봄',            name: 'leaderCareStory',    hidden: true	},
        { label: '목장영적돌봄',            name: 'pastureCareStory',   hidden: true	},
        { label: '기타사항',                name: 'etc',            	hidden: true	},
        { label: '아이디',      		    name: 'userId',             hidden: true	},
        { label: '마을동정아이디',      	name: 'eventId',            hidden: true	},
        { label: '스토리아이디', 		    name: 'storyId',         	hidden: true 	},
        { label: '마을스토리아이디',        name: 'townStoryId',        hidden: true 	},
        { label: '권한ID',      		    name: 'roleId',             hidden: true 	},
        { label: '소속ID',      		    name: 'groupId',            hidden: true 	},
        { label: '등록일',       		    name: 'townCreateDate',     align: 'center', width: 115, hidden: false 	},
        { label: '수정일',       		    name: 'townUpdateDate',     align: 'center', width: 115, hidden: false 	}]
    ,setParam : function(){
        var data = common.serializeObject($("#StoryForm"));

        data["inputDate"] = $("#inputDatePop").val().replace(/[^0-9]/g, "");
		data["eventContent"] = null;
		
        return data;
    }
};
