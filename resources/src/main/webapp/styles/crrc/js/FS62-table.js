//点击查询按钮，弹出查询框
var search1 = new multiSearch2($(".btn1"), "reportFS62/tplant", null, null);//工厂
var search2 = new multiSearch3($(".btn2"), "reportFS62/groes", null, null);//电机型号
var search3 = new multiSearch4($(".btn3"), "reportFS62/sernr", 0, 10);//电机编号
var search4 = new multiSearch3($(".btn4"), "reportFS62/zlevel", null, null);//维修级别
var search5 = new multiSearch3($(".btn5"), "reportFS62/arbpl", null, null);//检修地点

//控制表格高度的显示
var heights = $(window).height();
$(".ch-kp-sixtyTwo  .t_r_content").height((heights - 230) + "px");
//点击收起按钮
$(".retract").off("click").on("click", function () {
    if ($(".container form").css("display") == "block") {
        $(".container form").slideUp();
        $(this).text("展开  ∨");
        var heights = $(window).height();
        $(".ch-kp-sixtyTwo .t_r_content").animate({
            "height": (heights - 195) + "px"
        });
    } else {
        $(".container form").slideDown();
        $(this).text("收起  ∧");
        var heights = $(window).height();
        $(".ch-kp-sixtyTwo .t_r_content").height((heights - 230) + ("px"));
    }
})
//表格
$("#submit").off("click").on("click", function () {
    var blog=true;
    $(".notSearch").empty();
    var array1 = [],array2 = [],array3 = [],array4 = [],array5 = [];
    if ($(".factory").val() != "") {
        array1 = $(".factory").val().split(",");//工厂
        if(blog){
            blog = compareInput(array1,comp_code,"工厂");
        }
    }
    if ($(".groes").val() != "") {
        array2 = $(".groes").val().split(",");//电机型号
    }
    var part=/^[A-Z\d-\/,]+$/g;
    if ($(".sernr").val() != ""/* && part.test($(".sernr").val())*/) {
        array3 = $(".sernr").val().split(",");//电机编号
    }/*else if($(".sernr").val() != "" && !part.test($(".sernr").val())){
        alert("请输入正确的电机编号!")
    }*/
    if ($(".zlevel").val() != "") {
        array4 = $(".zlevel").val().split(",");//维修级别
    }
    if ($(".checkAddress").val() != "") {
        array5 = $(".checkAddress").val().split(",");//检修地点
    }
    var txt1 = $("#startTime").val().replace(/-/g, "");
    var txt2 = $("#endTime").val().replace(/-/g, "");//日期
    if(!blog){
        return;
    }
    if (txt1 == "" || txt2 == "") {
        alert("请输入日期");
    } else if (parseInt(txt1) > parseInt(txt2)) {
        alert("后一个日期必须大于或等于前一个日期");
    }else {
        $(".Mask").show();
        var item = 0;
        var reportJson = {
            "plantValue": array1, //工厂
            "groesValue": array2, //电机型号
            "sernrValue": array3, //电机编号
            "zlevelValue": array4, //维修级别
            "arbplValue": array5, //维修地点
            "startTime": txt1, //日期
            "endTime": txt2,
            "startitem": item, //分页
            isExport: false
        }
        reportJson = JSON.stringify(reportJson);
        $(".export").on("click", function () {
            reportJson = {
                plantValue: array1.join(','), //工厂
                groesValue: array2.join(','), //电机型号
                sernrValue: array3.join(','), //电机编号
                zlevelValue: array4.join(','), //维修级别
                arbplValue: array5.join(','), //维修地点
                startTime: txt1, //日期
                endTime: txt2,
                isExport: true
            }
            downloadFile(reportJson, ctx + "/crrc/reportFS62/downloadFile");
        });
        $.ajax({
            url: ctx + "/crrc/reportFS62/result",
            data: reportJson,
            dataType: "json",
            contentType: "application/json;charset=utf-8",
            type: "post",
            success: function (data) {
                if ('300' === data.statusCode || '301' === data.statusCode) {
                    alert(data.message);
                    $(".Mask").hide();
                    $("#t_r_content table").html('');
                    $(".notSearch").next("div").hide();
                    return true;
                }
                var str = "";
                if (jQuery.isEmptyObject(data)) {
                    $(".notSearch").next("div").hide();
                    $(".notSearch").text("未查询到数据");
                } else {
                    $(".notSearch").next("div").show();
                    if (data.procurementStorage.length < 20) {
                        $("#show").html("数据加载完成");
                    } else {
                        $("#show").html("");
                    }
                    $.each(data.procurementStorage,function(k,v){
                        str+="<tr>"
                            +"<td width='40px' class='xh'>" + (k + 1) + "</td>"
                            + "<td width='80px' class='iwerk'>" + v.iwerk + "</td>"
                            + "<td width='150px' class='zdate'>" + v.zdate + "</td>"
                            + "<td width='150px' class='arbpl'>" + v.arbpl + "</td>"
                            + "<td width='150px' class='groes'>" + v.groes + "</td>"
                            + "<td width='150px' class='sernr'>" + v.sernr + "</td>"
                            + "<td class='zlevel'>" + v.zlevel + "</td>"
                            +"</tr>"
                    })
                    $("#t_r_content table").html(str);
                    trBg($("#t_r_content table"));
                    $("#t_r_content table tr td:last-child").removeAttr("width");
                    $(".bianzhi").text(data.BZDW);
                }
                $(".Mask").hide();
            },
            error: function () {
                alert("error1");
                $(".Mask").hide();
            }
        });

        item = 20;
        var b = true;
        $("#t_r_content").unbind("scroll").bind("scroll", function () {
            toTop($(".toTop"),$(".t_r_content"));
            var fold = $("#t_r_content").height() + $(".t_r_content")[0].scrollTop;
            if (b && fold >= $(".t_r_content")[0].scrollHeight) {
                b = false;
                if($("#show").html()=="数据加载完成"){
                    $(".Mask").hide();
                }else{
                    $(".Mask").show();
                }
                reportJson = {
                    "plantValue": array1, //工厂
                    "groesValue": array2, //电机型号
                    "sernrValue": array3, //电机编号
                    "zlevelValue": array4, //维修级别
                    "arbplValue": array5, //维修地点
                    "startTime": txt1, //日期
                    "endTime": txt2,
                    "startitem": item, //分页
                    isExport: false
                }
                reportJson = JSON.stringify(reportJson);
                if ($("#show").text() != "数据加载完成") {
                    $.ajax({
                        url: ctx + "/crrc/reportFS62/result",
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
                            $.each(data.procurementStorage,function(k,v){
                                str+="<tr>"
                                    +"<td width='40px' class='xh'>" + (k + 1 +item) + "</td>"
                                    + "<td width='80px' class='iwerk'>" + v.iwerk + "</td>"
                                    + "<td width='150px' class='zdate'>" + v.zdate + "</td>"
                                    + "<td width='150px' class='arbpl'>" + v.arbpl + "</td>"
                                    + "<td width='150px' class='groes'>" + v.groes + "</td>"
                                    + "<td width='150px' class='sernr'>" + v.sernr + "</td>"
                                    + "<td class='zlevel'>" + v.zlevel + "</td>"
                                    +"</tr>"
                            })
                            item = item + 20;
                            $(".ch-kp-sixtyTwo table:eq(1)").append(str);
                            trBg($(".ch-kp-sixtyTwo table:eq(1)"));
                            $("#t_r_content table tr td:last-child").removeAttr("width");
                            $(".Mask").hide();
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
