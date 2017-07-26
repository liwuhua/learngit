//点击查询按钮，弹出查询框
var search1=new multiSearch2($(".btn1"),"reportFS30/01/tcompcode",null,null);//公司代码
var search2=new multiSearch1($(".btn2"),"reportFS30/01/tvendor",null,null);//供应商
//控制表格高度的显示
var heights = $(window).height();
$(".ch-kp-thirty-one .t_r_content").height((heights - 225) + "px");
//点击收起按钮
$(".retract").off("click").on("click", function () {
    if ($(".container form").css("display") == "block") {
        $(".container form").slideUp();
        $(this).text("展开  ∨");
        var heights = $(window).height();
        $(".ch-kp-thirty-one .t_r_content").animate({
            "height": (heights - 145) + "px"
        });
    } else {
        $(".container form").slideDown();
        $(this).text("收起  ∧");
        var heights = $(window).height();
        $(".ch-kp-thirty-one .t_r_content").height((heights - 225) + ("px"));
    }
})
//采购入库的表格
$("#submit").off("click").on("click", function () {
    var blog = true;
    $(".notSearch").empty();
    $("#show").text("数据正在加载中");
    //$(".hideCol").click();
    var arrs = [], array1 = [], array2 = [];
    if ($(".gongsi").val() != "") {
        array1 = $(".gongsi").val().split(",");//公司代码
        if(blog){
            blog = compareInput(array1,comp_code,"公司代码");
        }
    }
    if ($(".supplier").val() != "") {
        array2 = $(".supplier").val().split(",");//供应商
    }
    var txt1 = $("#startTime").val().replace(/-/g, "");//日期
    var txt2 = $("#endTime").val().replace(/-/g, "");
    var reportJson={};

    if(!blog){
        return;
    }

    if (txt1 == "" || txt2 == "") {
        alert("请输入日期");
    } else if (parseInt(txt1) > parseInt(txt2)) {
        alert("后一个日期必须大于或等于前一个日期");
    } else {
        $(".Mask").show();
        var item = 0;
         reportJson = {
            "compCodeValue": array1, //公司代码
            "vendorValue": array2, //供应商
            "startTime": txt1,
            "endTime": txt2, //日期
            "startitem": item, //分页
            "pageitem": 20,
            "isExport": false
        }
        reportJson = JSON.stringify(reportJson);
        $(".export").on("click", function () {
            reportJson = {
            	compCodeValue: array1.join(','), //工厂
                vendorValue: array2.join(','), //供应商
                startTime: txt1,
                endTime: txt2, //入库日期
                isExport: true
            }
            downloadFile(reportJson, ctx + "/crrc/reportFS30/01/downloadFile");
        });
        $.ajax({
            url: ctx + "/crrc/reportFS30/01/result",
            data: reportJson,
            dataType: "json",
            contentType: "application/json;charset=utf-8",
            type: "post",
            success: function (data) {
                if ('300' === data.statusCode || '301' === data.statusCode) {
                    alert(data.message);
                    $(".Mask").hide();
                    $(".ch-kp-thirty-one table:eq(1)").html('');
                    return true;
                }
                if (data.procurementStorage.length == 0) {
                    $(".notSearch").next("div").hide();
                    $(".notSearch").text("未查询到数据");
                } else {
                    $(".notSearch").next("div").show();
                    $(".dataTime1").text(getTime(txt1, txt2));
                    if (data.procurementStorage.length < 20) {
                        $("#show").html("数据加载完成");
                    } else {
                        $("#show").html("");
                    }
                    var str = "";
                    $.each(data.procurementStorage, function (key, val) {
                        str += "<tr>"
                                + "<td style='width:40px' class='xh'>" + (key + 1) + "</td>"
                                + "<td style='width:40px' class='werks'>" + val.werks + "</td>"
                                + "<td style='width:110px' class='matnr'>" + val.matnr + "</td>"
                                + "<td style='width:380px' class='txtmd'>" + val.txtmd + "</td>"
                                + "<td style='width:90px' class='sum_menge'>" + parseFloat(val.sum_menge).toFixed(3) + "</td>"
                                + "<td style='width:70px' class='meins'>" + val.meins + "</td>"
                                + "<td style='width:135px;text-align:right;padding-right:5px;' class='sum_bualt'>" + formatNumber(val.sum_bualt,2,1) + "</td>"
                                + "<td style='width:70px;text-align:right;' class='price_base'>" + formatNumber(val.price_base,2,1) + "</td>"
                                + "<td style='width:90px' class='lifnr'>" + val.lifnr + "</td>"
                                + "<td style='width:270px' class='vendor'>" + val.vendor + "</td>"
                                + "<td class='ekgrp'>" + val.ekgrp + "</td>"
                                + "</tr>";
                    });

                    $(".ch-kp-thirty-one table:eq(1)").html(str);
                    $(".hj p span:eq(0) b").text(data.SUM.menge);
                    $(".hj p span:eq(1) b").text(formatNumber(data.SUM.bualt,2,1));
                    trBg($(".ch-kp-thirty-one table:eq(1)"))
                }
                $(".Mask").hide();
                $(".sureBtns").click();
                
            },
            error: function () {
                alert("数据请求失败!");
                $(".Mask").hide();
            }
        })

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
                    "compCodeValue": array1, //工厂
                    "vendorValue": array2, //供应商
                    "startTime": txt1,
                    "endTime": txt2, //日期
                    "startitem": item, //分页
                    "pageitem": 20
                }
                reportJson = JSON.stringify(reportJson);
                if ($("#show").text() != "数据加载完成") {
                    $.ajax({
                        url: ctx + "/crrc/reportFS30/02/result",
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
                            $.each(data.procurementStorage, function (key, val) {
                                str += "<tr>"
                                        + "<td style='width:40px' class='xh'>" + (key + 1 + item) + "</td>"
                                        + "<td style='width:40px' class='werks'>" + val.werks + "</td>"
                                        + "<td style='width:110px' class='matnr'>" + val.matnr + "</td>"
                                        + "<td style='width:380px' class='txtmd'>" + val.txtmd + "</td>"
                                        + "<td style='width:90px' class='sum_menge'>" + parseFloat(val.sum_menge).toFixed(3) + "</td>"
                                        + "<td style='width:70px' class='meins'>" + val.meins + "</td>"
                                        + "<td style='width:135px;text-align:right;padding-right:5px;' class='sum_bualt'>" + formatNumber(val.sum_bualt,2,1) + "</td>"
                                        + "<td style='width:70px;text-align:right;' class='price_base'>" + formatNumber(val.price_base,2,1) + "</td>"
                                        + "<td style='width:90px' class='lifnr'>" + val.lifnr + "</td>"
                                        + "<td style='width:270px' class='vendor'>" + val.vendor + "</td>"
                                        + "<td class='ekgrp'>" + val.ekgrp + "</td>"
                                        + "</tr>";
                            });
                            item = item + 20;
                            $(".ch-kp-thirty-one table:eq(1)").append(str);
                            trBg($(".ch-kp-thirty-one table:eq(1)"));
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
        });
    }
})

