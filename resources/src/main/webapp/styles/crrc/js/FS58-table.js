//点击查询按钮，弹出查询框
var search1 = new multiSearch2($(".btn1"), "reportFS58/tplant", null, null);//工厂
var search2 = new multiSearch3($(".btn2"), "reportFS58/groes", null, null);//产品型号
var search3 = new multiSearch3($(".btn3"), "reportFS58/zlevel", null, null);//维修级别
var search4 = new multiSearch7($(".btn4"), "reportFS58/pjwlbm", 0, 10);//配件物料编码
//控制表格高度的显示
var heights = $(window).height();
var heights_f = $(".container .col-sm-12 form").height();
$(".ch-kp-cb1 .t_r_content").height((heights - heights_f - 160) + "px");
//点击收起按钮
$(".retract").off("click").on("click", function () {
    if ($(".container form").css("display") == "block") {
        $(".container form").slideUp();
        $(this).text("展开  ∨");
        var heights = $(window).height();
        $(".ch-kp-cb1 .t_r_content").animate({
            "height": (heights - 150) + "px"
        });
    } else {
        $(".container form").slideDown();
        $(this).text("收起  ∧");
        var heights = $(window).height();
        $(".ch-kp-cb1 .t_r_content").height((heights - heights_f - 160) + "px");
    }
})
var arr = [], arrTk = [];
//点击提交按钮渲染表格
$("#submit").off("click").on("click", function () {
	var blog = true;
    $(".notSearch").empty();
    var array1 = [], array2 = [], array3 = [], array4 = [];
    if ($(".IWERK").val() != "") {
        array1 = $(".IWERK").val().split(",");//工厂
        if(blog){
            blog = compareInput(array1,plant,"工厂");
        }
    }
    if ($(".GROES").val() != "") {
        array2 = $(".GROES").val().split(",");//产品型号
    }
    if ($(".ZLEVEL").val() != "") {
        array3 = $(".ZLEVEL").val().split(",");//维修级别
    }
    if ($(".PJWLBM").val() != "") {
        array4 = $(".PJWLBM").val().split(",");//配件物料编码
    }
    var txt1 = $("#startTime").val().replace(/-/g, "");
    var txt2 = $("#endTime").val().replace(/-/g, "");
    if(txt1==""&&txt2!=""){
    	alert("日期两个都有或者两个都无!");
    	return;
    }
    if(txt1!=""&&txt2==""){
    	alert("日期两个都有或者两个都无!");
    	return;
    }
    if(!blog){
        return;
    }
    var reportJson1 = {};
    if (parseInt(txt1) > parseInt(txt2)) {
        alert("后一个日期必须大于或等于前一个日期");
    } else {
        $(".Mask").show();
        var item = 0;
        reportJson1 = {
        	werksValue: array1, ////工厂
        	groesValue: array2, //产品型号
        	zlevelValue: array3, //维修级别
        	matnrValue: array4, //配件物料编码
            startTime: txt1,
            endTime: txt2, //入库日期
            startitem: item, //分页
            pageitem: 20,
            isExport: false
        }
        reportJson1 = JSON.stringify(reportJson1);
        $(".dataTime1").text(getTime(txt1, txt2));
        $(".export").on("click", function () {
            reportJson1 = {
                werksValue: array1.join("."), ////工厂
            	groesValue: array2.join("."), //产品型号
            	zlevelValue: array3.join("."), //维修级别
            	matnrValue: array4.join("."), //配件物料编码
                startTime: txt1,
                endTime: txt2, //入库日期
                startTime: txt1,
                endTime: txt2, //入库日期
                isExport: true
            }
            downloadFile(reportJson1, ctx + "/crrc/reportFS58/downloadFile");
        });
//		第一张表数据请求
        $.ajax({
            url: ctx + "/crrc/reportFS58/result",
            data: reportJson1,
            dataType: "json",
            contentType: "application/json;charset=utf-8",
            type: "post",
            success: function (data) {
                if ('300' === data.statusCode || '301' === data.statusCode) {
                    alert(data.message);
                    $(".Mask").hide();
                    $(".ch-kp-cb1 .t_r_content table").html('');
                    return true;
                }
                if (data.length == 0) {
                    $(".Mask").hide();
                    $(".ch-kp-cb1").parent().hide();
                    $(".notSearch").text("未查询到数据");
                } else {
                    $(".notSearch+div").show();
                    if (data.length < 20) {
                        $(".Mask").hide();
                        $("#show").html("数据加载完成");
                    } else {
                        $("#show").html("");
                    }
                    var str = "";
                    $.each(data, function (k, v) {
                        str += "<tr>"
		                        	+"<td width='40px' class='xh'>"+(k+1)+"</td>"//序号
		                            +"<td width='50px' class='IWERK'>"+v.iwerk+"</td>"//工厂
		                            +"<td width='120px' class='MATNR'>"+v.matnr+"</td>"//产品编码
		                            +"<td width='130px' class='GROES'>"+v.groes+"</td>"//电机型号
		                            +"<td width='60px' class='ZLEVEL'>"+v.zlevel+"</td>"//维修级别
		                            +"<td width='120px' class='PJWLBM'>"+v.pjwlbm+"</td>"//配件物料编码
		                            +"<td width='300px' class='PJWLMS'>"+v.pjwlms+"</td>"//配件物料描述
		                            +"<td width='80px' class='OHJS' style='text-align:right;'>"+formatNumber(v.ohjs,2,1)+"</td>"//偶换件数
		                            +"<td width='100px' class='JXTS' style='text-align:right;'>"+formatNumber(v.jxts,2,1)+"</td>"//检修台数
		                            +"<td width='100px' class='OHTS' style='text-align:right;'>"+formatNumber(v.ohts,2,1)+"</td>"//偶换台数
		                            +"<td width='100px' class='OHTL'>"+v.ohtl+"</td>"//偶换台率
		                            +"<td width='100px' class='TYL' style='text-align:right;'>"+formatNumber(v.tyl,2,1)+"</td>"//台用量
		                            +"<td class='OHL'>"+v.ohl+"</td>"//偶换率
                                + "</tr>"

                    })
                    $(".ch-kp-cb1 table:eq(1)").html(str);
                    trBg($(".ch-kp-cb1 table:eq(1)"));
                }
                $(".sureBtns").click();
                $(".Mask").hide();
            },
            error: function () {
                alert("error1");
                $(".Mask").hide();
            }
        });
        item = 20;
        var b = true;
        $(".t_r_content").unbind("scroll").bind("scroll", function () {
            toTop($(".toTop"), $(".t_r_content"));
            var fold = $(".t_r_content").height() + $(".t_r_content")[0].scrollTop;
            if (b && fold >= $(".t_r_content")[0].scrollHeight) {
                b = false;
                if ($("#show").html() == "数据加载完成") {
                    $(".Mask").hide();
                } else {
                    $(".Mask").show();
                }
                reportJson1 = {
            		werksValue: array1, ////工厂
                	groesValue: array2, //产品型号
                	zlevelValue: array3, //维修级别
                	matnrValue: array4, //配件物料编码
                    startTime: txt1,
                    endTime: txt2, //入库日期
                    startitem: item, //分页
                    pageitem: 20
                }
                reportJson1 = JSON.stringify(reportJson1);
                $.ajax({
                    url: ctx + "/crrc/reportFS58/result",
                    data: reportJson1,
                    dataType: "json",
                    contentType: "application/json;charset=utf-8",
                    type: "post",
                    success: function (data) {
                        if (data.length < 20) {
                            $("#show").html("数据加载完成");
                            b = false;
                        }
                        var str = "";
                        $.each(data, function (k, v) {
                            str += "<tr>"
		                            	+"<td width='40px' class='xh'>"+(k+1+item)+"</td>"//序号
		                            	+"<td width='50px' class='IWERK'>"+v.iwerk+"</td>"//工厂
			                            +"<td width='120px' class='MATNR'>"+v.matnr+"</td>"//产品编码
			                            +"<td width='130px' class='GROES'>"+v.groes+"</td>"//电机型号
			                            +"<td width='60px' class='ZLEVEL'>"+v.zlevel+"</td>"//维修级别
			                            +"<td width='120px' class='PJWLBM'>"+v.pjwlbm+"</td>"//配件物料编码
			                            +"<td width='300px' class='PJWLMS'>"+v.pjwlms+"</td>"//配件物料描述
			                            +"<td width='80px' class='OHJS' style='text-align:right;'>"+formatNumber(v.ohjs,2,1)+"</td>"//偶换件数
			                            +"<td width='100px' class='JXTS' style='text-align:right;'>"+formatNumber(v.jxts,2,1)+"</td>"//检修台数
			                            +"<td width='100px' class='OHTS' style='text-align:right;'>"+formatNumber(v.ohts,2,1)+"</td>"//偶换台数
			                            +"<td width='100px' class='OHTL'>"+v.ohtl+"</td>"//偶换台率
			                            +"<td width='100px' class='TYL' style='text-align:right;'>"+formatNumber(v.tyl,2,1)+"</td>"//台用量
			                            +"<td class='OHL'>"+v.ohl+"</td>"//偶换率
                                    + "</tr>";
                        });
                        item = item + 20;
                        $(".ch-kp-cb1 table:eq(1)").append(str);
                        trBg($(".ch-kp-cb1 table:eq(1)"));
                        $(".Mask").hide();
                        $(".sureBtns").click();
                        b = true;
                    },
                    error: function () {
                        alert("数据请求失败!");

                    }
                })
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

            //获取表头名称
            var thArr = [];
            for (var i = 0; i < $(".t_r_title table tr").find("th").length; i++) {
                thArr.push($(".t_r_title table tr").find("th").eq(i).text())
            }
            //循环表头将名称添加到弹出框中
            var str1 = "";
            for (var j = 0; j < thArr.length; j++) {
                str1 += "<tr>"
                        + "<td style='width:50px; text-align: center;'><input type='checkbox' checked='checked' class='checks'></td>"
                        + "<td>" + thArr[j] + "</td>"
                        + "</tr>";
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
                    })
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
            })
            $(".allCheck").on("click", function () {
                if ($(this).prop("checked")) {
                    $(this).parents(".t_r_t").next().find("table input").prop("checked", true);
                    arr = [];
                } else {
                    $(this).parents(".t_r_t").next().find("table input").prop("checked", false);
                    for (var i = 0; i < $(this).parents(".t_r_t").next().find("table input").length; i++) {
                        var t = $(this).parents(".t_r_t").next().find("table input").eq(i).parent().next().text();
                        arr.push(t)
                    }
                }
            })


            //点击取消和x弹出框消失，点击确定相应的列消失
            can($(".closeBtn"));
            can($(".canBtns"));
            $(".sureBtns").on("click", function () {
                //弹出框里的不被选中的已经放倒一个数组里，判断表头有这个内容就将此隐藏
                //表头数组
                var arrBig = [];
                for (var j = 0; j < $(".t_r_title table tr th").length; j++) {
                    var thBig = $(".t_r_title table tr th").eq(j).text();
                    arrBig.push(thBig)
                }

                //不选中的text的字段
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
                        var className = $(".ch-kp-cb1 .t_r_title table tr th").eq($.inArray(textArr[n], arrBig)).attr("class");
                        arrTrue.push(className);
                    }
                }
                for (var m = 0; m < arrTrue.length; m++) {
                    $(".ch-kp-cb1").find("." + arrTrue[m]).show();
                }

                //不被选中的class名
                var arrN = [];
                for (var n = 0; n < arr.length; n++) {
                    if ($.inArray(arr[n], arrBig) >= 0) {
                        var className = $(".ch-kp-cb1 .t_r_title table tr th").eq($.inArray(arr[n], arrBig)).attr("class");
                        arrN.push(className);
                    }
                }
                //不被选中的class消失
                for (var m = 0; m < arrN.length; m++) {
                    $(".ch-kp-cb1").find("." + arrN[m]).hide()
                }

                $(".hideColBox").hide();
                var txt = _this.attr("src");
                _this.removeClass("bg");
                _this.attr("src", txt.replace("hidered.png", "hide.png"));
                $("#Mask").hide();
            })
        } else {
            $(this).removeClass("bg");
            var txt = $(this).attr("src");
            $(this).attr("src", txt.replace("hidered.png", "hide.png"));
            $(".hideColBox").hide();
            $("#Mask").hide();
        }
    })
})

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
            $("#Mask").show();
            $(".hideCol").attr("src", $(".hideCol").attr("src").replace("hide.png", "hidered.png"));
        }

    })
}

Array.prototype.unique3 = function () {
    var res = [];
    var json = {};
    for (var i = 0; i < this.length; i++) {
        if (!json[this[i]]) {
            res.push(this[i]);
            json[this[i]] = 1;
        }
    }
    return res;
}
