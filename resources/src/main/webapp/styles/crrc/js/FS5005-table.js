//点击查询按钮，弹出查询框
//var search1=new multiSearch1($(".btn1"),"",null,null);//质量损失类别编号
var search1=new multiSearch4($(".btn1"),"reportKPI2227/losstypecode",0,10);//losstypecode  质量损失类别编号
var search2=new multiSearch1($(".btn2"),"reportKPI2227/getCategorytype",null,null);//质量损失类别
var search3=new multiSearch1($(".btn3"),"reportKPI2227/getReasontype",null,null);//质量损失原因代码
var search4=new multiSearch1($(".btn4"),"reportKPI2227/getProjecttype",null,null);//项目类型
var search5=new multiSearch4($(".btn5"),"reportKPI2227/occurplant",0,10);//质量问题发生单位
var search6=new multiSearch4($(".btn6"),"reportKPI2227/dutyplant",0,10);//责任单位
//控制表格高度的显示
var heights = $(window).height();
var heights_f=$(".container .col-sm-12 form").height();
$(".ch-kp-fifty-two .t_r_content").height((heights -heights_f - 140) + "px");
//点击收起按钮
$(".retract").off("click").on("click", function () {
    if ($(".container form").css("display") == "block") {
        $(".container form").slideUp();
        $(this).text("展开  ∨");
        var heights = $(window).height();
        $(".ch-kp-fifty-two .t_r_content").animate({
            "height": (heights - 135) + "px"
        });
    } else {
        $(".container form").slideDown();
        $(this).text("收起  ∧");
        var heights = $(window).height();
        $(".ch-kp-fifty-two .t_r_content").height((heights - heights_f - 140) + ("px"));
    }
});
var arr = [], arrTk = [];
//点击下载模板
$("#downMb").off("click").on("click", function () {
	reportJson1={
			/*xxbh:'xxbh'*/
	};
	downloadFile(reportJson1,ctx + "/crrc/reportKPI2227/downloadTemplate")
});
//采购入库的表格
$("#submit").off("click").on("click", function () {
    var blog = true;
    $(".notSearch").empty();
    $("#show").text("数据正在加载中");
    var array1 = [], array2 = [], array3 = [],array4 = [],array5 = [],array6 = [];
    if ($(".lbbh").val() != "") {
        array1 = $(".lbbh").val().split(",");//质量损失类别编号
    }
    if ($(".lbdm").val() != "") {
    	 array2 = $(".lbdm").val().split(",");//质量损失类别代码
    }
    if ($(".yydm").val() != "") {
        array3 = $(".yydm").val().split(",");//质量损失原因代码
    }
    if ($(".fsdw").val() != "") {
        array4 = $(".fsdw").val().split(",");//质量问题发生单位
    }
    if ($(".xmlx").val() != "") {
        array5 = $(".xmlx").val().split(",");//项目类型
    }
    if ($(".zrdw").val() != "") {
        array6 = $(".zrdw").val().split(",");//责任单位
    }
    var txt1 = $("#startTime").val().replace(/-/g, "");//发现时间
    var txt2 = $("#endTime").val().replace(/-/g, "");
    
    var reportJson={};
    if(!blog){
    	
        return;
    }
    if(txt1==""&&txt2!=""){
    	alert("两个日期必须同时有或者同时没有");
    	return;
    }else if(txt1!=""&&txt2==""){
    	alert("两个日期必须同时有或者同时没有");
    	return;
    }
    if(parseInt(txt1)>parseInt(txt2)){
        alert("后一个日期必须大于或等于前一个日期");
    }else {
        $(".Mask").show();
        var item = 0;
         reportJson = {
        	"lbbh":array1,//质量损失类别编号
        	"lbdm":array2,//质量损失类别
        	"yydm":array3,//质量损失原因
        	"fsdw":array4,//质量问题发生单位
        	"zrdw":array6,//责任单位
        	"xmlx":array5,//项目类型
        	"startTime": txt1,//发现时间上行
            "endTime": txt2, //发现时间下行
            "startitem": item, //分页
            "pageitem": 20,
            "isExport": false
        }
        reportJson = JSON.stringify(reportJson);
        $(".export").on("click", function () {
            reportJson = {
        		"lbbh":array1.join(","),//质量损失类别编号
            	"lbdm":array2.join(","),//质量损失类别
            	"yydm":array3.join(","),//质量损失原因
            	"fsdw":array4.join(","),//质量问题发生单位
            	"zrdw":array6.join(","),//责任单位
            	"xmlx":array5.join(","),//项目类型
            	"startTime": txt1,//发现时间上行
                "endTime": txt2, //发现时间下行
                "isExport": true
            }
            downloadFile(reportJson, ctx + "/crrc/reportKPI2227/downloadFile");
        });
        $.ajax({
            url: ctx + "/crrc/reportKPI2227/result",
            data: reportJson,
            dataType: "json",
            contentType: "application/json;charset=utf-8",
            type: "post",
            success: function (data) {
                if ('300' === data.statusCode || '301' === data.statusCode) {
                    alert(data.message);
                    $(".Mask").hide();
                    $(".ch-kp-fifty-two table:eq(1)").html('');
                    return true;
                }
                if (data.length == 0) {
                    $(".notSearch").next("div").hide();
                    $(".notSearch").text("未查询到数据");
                } else {
                    $(".notSearch").next("div").show();
                    $(".dataTime1").text(getTime(txt1, txt2));
                    if (data.length < 20) {
                        $("#show").html("数据加载完成");
                    } else {
                        $("#show").html("");
                    }
                    var str = "";
                    $.each(data, function (k, v) {
                        str += "<tr>"
		                        	+"<td width='40px' class='xh'>"+(k+1)+"</td>"
		                            +"<td width='70px' class='date'>"+v.date+"</td>"//时间
		                            +"<td width='60px' class='yymm'>"+v.yymm+"</td>"//月份
		                            +"<td width='160px' class='categorynum'>"+v.categorynum+"</td>"//质量损失类别编号
		                            +"<td width='120px' class='productmodel'>"+v.productmodel+"</td>"//产品型号
		                            +"<td width='110px' class='categorycode'>"+v.categorycode+"</td>"//质量损失类别
		                            +"<td width='110px' class='reasoncode'>"+v.reasoncode+"</td>"//质量损失原因
		                            +"<td width='110px' class='occurrunit'>"+v.occurrunit+"</td>"//质量问题发生单位
		                            +"<td width='100px' class='discoprocess'>"+v.discoprocess+"</td>"//发现工序
		                            +"<td width='150px' class='productname' >"+v.productname+"</td>"//产品名称
		                            +"<td width='150px' class='faultnum' style='text-align:right;'>"+v.faultnum+"</td>"//不合格数量（故障数量）
		                            +"<td width='40px' class='unit'>"+v.unit+"</td>"//单位
		                            +"<td width='420px' class='qualitydesc'>"+v.qualitydesc+"</td>"//质量问题描述
		                            +"<td width='140px' class='ducumnum'>"+v.ducumnum+"</td>"//不合格品单据编号
		                            +"<td width='80px' class='noqualistatus'>"+v.noqualistatus+"</td>"//不合格状态
		                            +"<td width='100px' class='timeloss' style='text-align:right;'>"+v.timeloss+"</td>"//工时损失
		                            +"<td width='100px' class='materialloss' style='text-align:right;'>"+v.materialloss+"</td>"//材料损失
		                            +"<td width='100px' class='otherloss' style='text-align:right;'>"+v.otherloss+"</td>"//其他损失
		                            +"<td width='140px' class='prelossmonty' style='text-align:right;'>"+v.prelossmonty+"</td>"//预计损失金额(元)
		                            +"<td width='140px' class='actlossmonty' style='text-align:right;'>"+v.actlossmonty+"</td>"//实际损失金额(元)
		                            +"<td width='100px' class='payaccou' style='text-align: right;'>"+v.payaccou+"</td>"//赔偿金额(元)
		                        	+"<td width='140px' class='dutyunit'>"+v.dutyunit+"</td>"//责任单位
		                        	+"<td class='projetype'>"+v.projetype+"</td>"//项目类型
                                + "</tr>";
                    });
                    $(".ch-kp-fifty-two table:eq(1)").html(str);
                    trBg($(".ch-kp-fifty-two table:eq(1)"));
                }
                $(".Mask").hide();
                $(".sureBtns").click();                
            },
            error: function () {
                alert("数据请求失败!");
                $(".Mask").hide();
            }
        });
        item = 20;
        var b = true;
        $(".t_r_content").unbind("scroll").bind("scroll", function () {
        	toTop($(".toTop"),$(".t_r_content"));
            var fold = $(".t_r_content").height() + $(".t_r_content")[0].scrollTop;
            if (b && fold >= $(".t_r_content")[0].scrollHeight) {
                b = false;
                if($("#show").html()=="数据加载完成"){
					$(".Mask").hide();
				}else{
					$(".Mask").show();
				}
                reportJson = {
                		"lbbh":array1,//质量损失类别编号
                    	"lbdm":array2,//质量损失类别代码
                    	"yydm":array3,//质量损失原因代码
                    	"fsdw":array4,//质量问题发生单位
                    	"xmlx":array5,//项目类型
                    	"zrdw":array6,//责任单位
                    	"startTime": txt1,//发现时间上行
                        "endTime": txt2, //发现时间下行
                        "startitem": item, //分页
                        "pageitem": 20
                }
                reportJson = JSON.stringify(reportJson);
                if ($("#show").text() != "数据加载完成") {
                    $.ajax({
                        url: ctx + "/crrc/reportKPI2227/result",
                        data: reportJson,
                        dataType: "json",
                        contentType: "application/json;charset=utf-8",
                        type: "post",
                        success: function (data) {
                            if (data.length == 0) {
                                $("#show").html("数据加载完成");
                                b = false;
                            }
                            var str = "";
                            $.each(data, function (k, v) {
                            	str += "<tr>"
		                            		+"<td width='40px' class='xh'>"+(k+item+1)+"</td>"
		                            		+"<td width='70px' class='date'>"+v.date+"</td>"//时间
				                            +"<td width='60px' class='yymm'>"+v.yymm+"</td>"//月份
				                            +"<td width='160px' class='categorynum'>"+v.categorynum+"</td>"//质量损失类别编号
				                            +"<td width='120px' class='productmodel'>"+v.productmodel+"</td>"//产品型号
				                            +"<td width='110px' class='categorycode'>"+v.categorycode+"</td>"//质量损失类别
				                            +"<td width='110px' class='reasoncode'>"+v.reasoncode+"</td>"//质量损失原因
				                            +"<td width='110px' class='occurrunit'>"+v.occurrunit+"</td>"//质量问题发生单位
				                            +"<td width='100px' class='discoprocess'>"+v.discoprocess+"</td>"//发现工序
				                            +"<td width='150px' class='productname' >"+v.productname+"</td>"//产品名称
				                            +"<td width='150px' class='faultnum' style='text-align:right;'>"+v.faultnum+"</td>"//不合格数量（故障数量）
				                            +"<td width='40px' class='unit'>"+v.unit+"</td>"//单位
				                            +"<td width='420px' class='qualitydesc'>"+v.qualitydesc+"</td>"//质量问题描述
				                            +"<td width='140px' class='ducumnum'>"+v.ducumnum+"</td>"//不合格品单据编号
				                            +"<td width='80px' class='noqualistatus'>"+v.noqualistatus+"</td>"//不合格状态
				                            +"<td width='100px' class='timeloss' style='text-align:right;'>"+v.timeloss+"</td>"//工时损失
				                            +"<td width='100px' class='materialloss' style='text-align:right;'>"+v.materialloss+"</td>"//材料损失
				                            +"<td width='100px' class='otherloss' style='text-align:right;'>"+v.otherloss+"</td>"//其他损失
				                            +"<td width='140px' class='prelossmonty' style='text-align:right;'>"+v.prelossmonty+"</td>"//预计损失金额(元)
				                            +"<td width='140px' class='actlossmonty' style='text-align:right;'>"+v.actlossmonty+"</td>"//实际损失金额(元)
				                            +"<td width='100px' class='payaccou' style='text-align: right;'>"+v.payaccou+"</td>"//赔偿金额(元)
				                        	+"<td width='140px' class='dutyunit'>"+v.dutyunit+"</td>"//责任单位
				                        	+"<td class='projetype'>"+v.projetype+"</td>"//项目类型
		                                + "</tr>";
                            });
                            item = item + 20;
                            $(".ch-kp-fifty-two table:eq(1)").append(str);
                            trBg($(".ch-kp-fifty-two table:eq(1)"));
                            $(".Mask").hide();
                            $(".sureBtns").click();
                            b = true;
                        },
                        error: function () {
                            alert("数据请求失败!");
                            $(".Mask").hide();
                        }
                    });
                }
            }
        });
    }
    $(".hideCol").off("click").on("click", function () {
        var _this = $(this);
        //$("#Mask").show();
        if (!$(this).hasClass("bg")) {
            $(this).addClass("bg");
            var txt = $(this).attr("src");
            $(this).attr("src", txt.replace("hide.png", "hidered.png"));
            $(".hideColBox").show();
            $("#Mask").show();
            //allCheck($(".allCheck"));
            //获取表头名称
			var thArr=[]; 
			for(var i=0;i<$(".t_r_title table tr").find("th").length;i++){
				thArr.push($(".t_r_title table tr").find("th").eq(i).text())
			}
			//循环表头将名称添加到弹出框中
			var str1="";
			for(var j=0;j<thArr.length;j++){
				str1+="<tr>"
					+"<td style='width:50px; text-align: center;'><input type='checkbox' checked='checked' class='checks'></td>"
					+"<td>"+thArr[j]+"</td>"
				+"</tr>";
			}
			$(".hideTable .t_r_content table").html(str1);
			trBg($(".hideTable .t_r_content table"));
            //再次点击弹出框之前未被选中的仍未被选中
            //弹出框里的文本
            var arrTk = [];
            for (var i = 0; i < $(".hideTable .t_r_content tr").length; i++) {
                arrTk.push($(".hideTable .t_r_content tr").eq(i).find("td").text());
            }
            var val = "";
            $(".hideTable .t_r_content").on("click", ".checks", function () {
                if ($(this).prop("checked") == false) {
                    val = $(this).parent().next("td").text();
                    arr.push(val)
                } else {
                    val = $(this).parent().next("td").text();
                    $.each(arr, function (k, v) {
                        if (v == val) {
                        	arr.splice(k, 1)
                        }
                    });
                }
            })
            for (var i = 0; i < arrTk.length; i++) {
                for (var j = 0; j < arr.length; j++) {
                    if ($(".hideTable .t_r_content tr:eq(" + i + ") td:eq(1)").text() == arr[j]) {
                        $(".hideTable .t_r_content tr:eq(" + i + ") td:eq(0)").find("input").prop("checked", false)
                    }
                }
            }
            //所有的复选框默认的都是选中状态,点击一个复选框，全选的复选框不再被选中
            $(".hideTable").on("click", ".checks", function () {
                var inputLength = $(".checks").length;
                var incheckedLength = $(".checks[type='checkbox']:checked").length;
                if (incheckedLength < inputLength) {
                    $(".allCheck").prop("checked", false)
                } else {
                    $(".allCheck").prop("checked", true)
                }
            });
            $(".allCheck").on("click", function () {
                if ($(this).prop("checked")) {
                    $(this).parents(".t_r_t").next().find("table input").prop("checked", true);
	                arr=[];
                }else{
                	$(this).parents(".t_r_t").next().find("table input").prop("checked", false);
                	for(var i=0;i<$(this).parents(".t_r_t").next().find("table input").length;i++){
	            		var t=$(this).parents(".t_r_t").next().find("table input").eq(i).parent().next().text();
		                arr.push(t)
	            	}
                }
            });
            //点击取消和x弹出框消失，点击确定相应的列消失
            can($(".closeBtn"));
            can($(".canBtns"));
            $(".sureBtns").off("click").on("click", function () {
                //弹出框里的不被选中的已经放倒一个数组里，判断表头有这个内容就将此隐藏
                //表头数组
                var arrBig = [];
                for (var j = 0; j < $(".t_r_title table tr th").length; j++) {
                    var thBig = $(".t_r_title table tr th").eq(j).text();
                    arrBig.push(thBig)
                }
                //被选中的text的字段
                var textArr = [];
                for (var n = 0; n < arrBig.length; n++) {
                    if ($(".hideTable .t_r_content tr").eq(n).find("input[type='checkbox']").prop("checked")) {
                        var checkTrue = $(".hideTable .t_r_content tr").eq(n).find("input[type='checkbox']").parent("td").next().text();
                        textArr.push(checkTrue);
                    }
                }
                //被选中的class名
                var arrTrue = [];
                for (var n = 0; n < textArr.length; n++) {
                    if ($.inArray(textArr[n], arrBig) >= 0) {
                        var className = $(".t_r_title table tr th").eq($.inArray(textArr[n], arrBig)).attr("class");
                        arrTrue.push(className);
                    }
                }
                for (var m = 0; m < arrTrue.length; m++) {
                    $(".t_r").find("." + arrTrue[m]).show();
                    $("#t_r_content").find("." + arrTrue[m]).show();
                }
                //不被选中的class名
                var arrN = [];
                for (var n = 0; n < arr.length; n++) {
                    if ($.inArray(arr[n], arrBig) >= 0) {
                        var className = $(".t_r_title table tr th").eq($.inArray(arr[n], arrBig)).attr("class");
                        arrN.push(className);
                    }
                }
                //不被选中的class消失
                for (var m = 0; m < arrN.length; m++) {
                    $(".t_r").find("." + arrN[m]).hide();
                    $("#t_r_content").find("." + arrN[m]).hide();
                }
                $(".hideColBox").hide();
                var txt = _this.attr("src");
                _this.removeClass("bg");
                _this.attr("src", txt.replace("hidered.png", "hide.png"));
                $("#Mask").hide();
            });
        } else {
            $(this).removeClass("bg");
            var txt = $(this).attr("src");
            $(this).attr("src", txt.replace("hidered.png", "hide.png"));
            $(".hideColBox").hide();
            $("#Mask").hide();
        }
    });
});
function can(btn) {
    btn.off("click").on("click", function () {
        if ($(".hideColBox").css("display") == "block") {
            $(".hideCol").removeClass("bg");
            $(".hideColBox").hide();
            $("#Mask").hide();
            $(".hideCol").attr("src", $(".hideCol").attr("src").replace("hidered.png", "hide.png"));
        } else if ($(".hideColBox").css("display") == "none") {
            $(".hideCol").addClass("bg");
            $(".hideColBox").show();
            $(".hideCol").attr("src", $(".hideCol").attr("src").replace("hide.png", "hidered.png"));
            $("#Mask").show();
        }
    });
}