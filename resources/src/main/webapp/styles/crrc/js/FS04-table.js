//点击查询按钮，弹出查询框
var search1 = new multiSearch($(".btn1"), "tmaterial", 0, 10);//物料编码
var search2 = new multiSearch3($(".btn2"), "reportFS04/ebeln", null, null);//采购订单
var search3 = new multiSearch2($(".btn3"), "reportFS04/tpurgroup", null, null);//采购组
var search4 = new multiSearch2($(".btn4"), "reportFS04/tplant", null, null);//工厂
var search5 = new multiSearch1($(".btn5"), "reportFS04/tvendor", null, null);//供应商
//控制表格高度的显示
var heights = $(window).height();
$(".ch-kp-four .t_r_content").height((heights - 210) + "px");
//点击收起按钮
$(".retract").off("click").on("click", function () {	
    if ($(".container form").css("display") == "block") {
        $(".container form").slideUp();
        $(this).text("展开  ∨");
        var heights = $(window).height();
        $(".ch-kp-four .t_r_content").animate({
            "height": (heights - 130) + "px"
        });
    } else {
        $(".container form").slideDown();
        $(this).text("收起  ∧");
        var heights = $(window).height();
        $(".ch-kp-four .t_r_content").height((heights - 210) + ("px"));
    }
})
var arr = [], arrTk = [];
//采购入库的表格
$("#submit").off("click").on("click", function () {
	var blog = true;
    $(".notSearch").empty();
    $("#show").text("数据正在加载中");
    var reportJson = {};
    var array1 = [], array2 = [], array3 = [], array4 = [], array5 = [], array6 = [], array7 = [], arrs = [];
    if ($(".material").val() != "" && $(".material").val().indexOf(";") != -1) {
        arrs = $(".material").val().split(";");
        array1 = arrs[0].split(",");//物料多值
        array7 = arrs[1].split(",");//物料多区间
    } else if ($(".material").val() != "" && $(".material").val().indexOf(";") == -1 && $(".material").val().indexOf(":") != -1) {
        array1 = [];
        array7 = $(".material").val().split(",");
    } else if ($(".material").val() != "" && $(".material").val().indexOf(";") == -1 && $(".material").val().indexOf(":") == -1) {
        array1 = $(".material").val().split(",");
        array7 = [];
    } else if ($(".material").val() == "") {
        array1 = [];
        array7 = [];
    }
    if ($(".ebelnValue").val() != "") {
        array2 = $(".ebelnValue").val().split(",");//采购订单
    }
    if ($(".purGroupValue").val() != "") {
        array3 = $(".purGroupValue").val().split(",");//采购组
        array3 = regData(array3,fs04_pur_group);
        if(blog){
            blog = compareInput(array3,fs04_pur_group,"采购组");
        }
    }
    if ($(".plantValue").val() != "") {
        array4 = $(".plantValue").val().split(",");//工厂
        if(blog){
            blog = compareInput(array4,plant,"工厂");
        }
    }
    if ($(".vendorValue").val() != "") {
        array5 = $(".vendorValue").val().split(",");//供应商
    }
    if ($(".dataDay").val() != "") {
        array6 = $(".dataDay").val().split(",");//时间差异天数
    }
    if(!blog){
        return;
    }
    if (parseInt(txt1) > parseInt(txt2)) {
        alert("后一个日期必须大于或等于前一个日期");
    } else {
        $(".Mask").show();
        var txt1 = $("#startTime").val().replace(/-/g, "");//入库日期
        var txt2 = $("#endTime").val().replace(/-/g, "");
        var item = 0;
        reportJson = {
            "plantValue": array4, //工厂
            "matnrValue": array1, //物料多值
            "matnrInterval": array7, //物料多区间
            "vendorValue": array5, //供应商
            "purGroupValue": array3, //采购组
            "ebelnValue": array2, //采购订单
            "startTime": txt1,
            "endTime": txt2, //入库日期
            "timediffValue": array6, //时间差异天数
            "startitem": item, //分页
            "pageitem": 20,
            "isExport": false
        }
        reportJson = JSON.stringify(reportJson);
        $(".export").on("click", function () {
            reportJson = {
                plantValue: array4.join(','), //工厂
                matnrValue: array1.join(','), //物料
                matnrInterval: array7.join(','), //物料多区间
                vendorValue: array5.join(','), //供应商
                purGroupValue: array3.join(','), //采购组
                ebelnValue: array2.join(','), //采购订单
                timediffValue: array6.join(','), //时间差异天数
                startTime: txt1,
                endTime: txt2, //入库日期
                isExport: true
            }
            downloadFile(reportJson, ctx + "/crrc/reportFS04/downloadFile");
        });
        $.ajax({
            url: ctx + "/crrc/reportFS04/result",
            data: reportJson,
            dataType: "json",
            contentType: "application/json;charset=utf-8",
            type: "post",
            success: function (data) {
                if ('300' === data.statusCode || '301' === data.statusCode) {
                    alert(data.message);
                    $(".Mask").hide();
                    $(".ch-kp-four table:eq(1)").html('');
                    $(".notSearch").next("div").hide();
                    return true;
                }
                if (data.procurementStorage.length == 0) {
                    $(".notSearch").next("div").hide();
                    $(".notSearch").text("未查询到数据");
                } else {
                    $(".notSearch").next("div").show();
                    if (data.procurementStorage.length < 20) {
                        $("#show").html("数据加载完成");
                    } else {
                        $("#show").html("");
                    }
                    var str = "";
                    $.each(data.procurementStorage, function (k, v) {
                        str += "<tr>"
                                + "<td style='width:40px' class='xh'>" + (k + 1) + "</td>"
                                + "<td class='matnr' style='width:120px'>" + v.matnr + "</td>"
                                + "<td class='matnrtxtmd' style='width:320px'>" + v.matnrtxtmd + "</td>"
                                + "<td class='ebeln' style='width:100px'>" + v.ebeln + "</td>"
                                + "<td class='ebelp' style='width:90px'>" + v.ebelp + "</td>"
                                + "<td class='banfn' style='width:100px'>" + v.banfn + "</td>"
                                + "<td class='lfdat' style='width:120px'>" + v.lfdat + "</td>"
                                + "<td class='lfdat' style='width:120px'>" + v.eindt + "</td>"
                                + "<td class='menge' style='width:90px'>" + parseFloat(v.menge).toFixed(3) + "</td>"
                                + "<td class='budat' style='width:90px'>" + v.budat + "</td>"
                                + "<td class='timediff' style='width:130px'>" + v.timediff + "</td>"
                                + "<td class='timediff1' style='width:130px'>" + v.timediffeindt + "</td>"
                                + "<td class='ekgrp' style='width:50px'>" + v.ekgrp + "</td>"
                                + "<td class='werks' style='width:60px'>" + v.werks + "</td>"
                                + "<td class='lifnr' style='width:120px'>" + v.lifnr + "</td>"
                                + "<td class='lifnrtxtmd'>" + v.lifnrtxtmd + "</td>"
                                + "</tr>"
                    })
                    $(".ch-kp-four table:eq(1)").html(str);
                    trBg($(".ch-kp-four table:eq(1)"));
                }
                $(".sureBtns").click();
                $(".Mask").hide();

            },
            error: function () {
                $(".Mask").hide();
                alert("error");
            }
        });
        item = 20;
        var b = true;
        $("#t_r_content").unbind("scroll").bind("scroll", function () {
        	toTop($(".toTop"),$(".t_r_content"));
            var fold = $("#t_r_content").height() + $("#t_r_content")[0].scrollTop;
            if (b && fold >= $(".t_r_content")[0].scrollHeight) {
                b = false;
                if ($("#show").html() == "数据加载完成") {
                    $(".Mask").hide();
                } else {
                    $(".Mask").show();
                }
                reportJson = {
                    "plantValue": array4, //工厂
                    "matnrValue": array1, //物料
                    "matnrInterval": array7, //物料多区间
                    "vendorValue": array5, //供应商
                    "purGroupValue": array3, //采购组
                    "ebelnValue": array2, //采购订单
                    "startTime": txt1,
                    "endTime": txt2, //入库日期
                    "timediffValue": array6, //时间差异天数
                    "startitem": item, //分页
                    "pageitem": 20
                }
                reportJson = JSON.stringify(reportJson);
                if ($("#show").text() != "数据加载完成") {
                    $.ajax({
                        url: ctx + "/crrc/reportFS04/result",
                        data: reportJson,
                        dataType: "json",
                        contentType: "application/json;charset=utf-8",
                        type: "post",
                        success: function (data) {
                            if (data.procurementStorage.length == 0) {
                                $("#show").html("数据加载完成");
                                b = false;
                            }
                            var str = "";
                            $.each(data.procurementStorage, function (k, v) {
                            	str += "<tr>"
                                    + "<td style='width:40px' class='xh'>" + (k + 1) + "</td>"
                                    + "<td class='matnr' style='width:120px'>" + v.matnr + "</td>"
                                    + "<td class='matnrtxtmd' style='width:320px'>" + v.matnrtxtmd + "</td>"
                                    + "<td class='ebeln' style='width:100px'>" + v.ebeln + "</td>"
                                    + "<td class='ebelp' style='width:90px'>" + v.ebelp + "</td>"
                                    + "<td class='banfn' style='width:100px'>" + v.banfn + "</td>"
                                    + "<td class='lfdat' style='width:120px'>" + v.lfdat + "</td>"
                                    + "<td class='lfdat' style='width:120px'>" + v.eindt + "</td>"
                                    + "<td class='menge' style='width:90px'>" + parseFloat(v.menge).toFixed(3) + "</td>"
                                    + "<td class='budat' style='width:90px'>" + v.budat + "</td>"
                                    + "<td class='timediff' style='width:130px'>" + v.timediff + "</td>"
                                    + "<td class='timediff1' style='width:130px'>" + v.timediffeindt + "</td>"
                                    + "<td class='ekgrp' style='width:50px'>" + v.ekgrp + "</td>"
                                    + "<td class='werks' style='width:60px'>" + v.werks + "</td>"
                                    + "<td class='lifnr' style='width:120px'>" + v.lifnr + "</td>"
                                    + "<td class='lifnrtxtmd'>" + v.lifnrtxtmd + "</td>"
                                    + "</tr>"
                            });
                            item = item + 20;
                            $(".ch-kp-four table:eq(1)").append(str);
                            trBg($(".ch-kp-four table:eq(1)"));
                            $(".Mask").hide();
                            $(".sureBtns").click();
                            b = true;
                        },
                        error: function () {
                            alert("数据请求失败!");
                            $(".Mask").hide();
                        }
                    })
                }
            }
        })
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
                            + "<td style='width:50px;text-align:center;'><input type='checkbox' checked='checked' class='checks'></td>"
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
                $(".sureBtns").off("click").on("click", function () {

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
                            var className = $(".t_r_title table tr th").eq($.inArray(textArr[n], arrBig)).attr("class");
                            arrTrue.push(className);
                        }
                    }
                    for (var m = 0; m < arrTrue.length; m++) {
                        $(".t_r").find("." + arrTrue[m]).show();
                        $("#t_r_t").find("." + arrTrue[m]).show();
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
                        $("#t_r_t").find("." + arrN[m]).hide();
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
    }
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
            $(".hideCol").attr("src", $(".hideCol").attr("src").replace("hide.png", "hidered.png"));
            $("#Mask").show();
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
