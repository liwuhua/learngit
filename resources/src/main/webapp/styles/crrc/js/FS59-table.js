//点击查询按钮，弹出查询框
var search1 = new multiSearch2($(".btn1"), "reportFS59/tcompcode", null, null);//公司代码
//控制表格高度的显示
var heights = $(window).height();
$(".ch-kp-fiftyNine  .t_r_content").height((heights - 185) + "px");
//点击收起按钮
$(".retract").off("click").on("click", function () {
    if ($(".container form").css("display") == "block") {
        $(".container form").slideUp();
        $(this).text("展开  ∨");
        var heights = $(window).height();
        $(".ch-kp-fiftyNine .t_r_content").animate({
            "height": (heights - 150) + "px"
        });
    } else {
        $(".container form").slideDown();
        $(this).text("收起  ∧");
        var heights = $(window).height();
        $(".ch-kp-fiftyNine .t_r_content").height((heights - 185) + ("px"));
    }
})
//表格
$("#submit").off("click").on("click", function () {
    var blog=true;
    $(".notSearch").empty();
    var array1 = [];
    if ($(".gongsi").val() != "") {
        array1 = $(".gongsi").val().split(",");//公司代码
        if(blog){
            blog = compareInput(array1,comp_code,"公司代码");
        }
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
    } else if (parseInt(txt1.slice(0, 4)) != parseInt(txt2.slice(0, 4))) {
        alert("日期不可以跨年");
    } else {
        $(".Mask").show();
        var reportJson = {
            "compCodeValue": array1, //公司代码
            "dateYearMonthStart": txt1, //日期
            "dateYearMonthEnd": txt2,
            isExport: false
        }
        reportJson = JSON.stringify(reportJson);
        $(".export").on("click", function () {
            reportJson = {
                compCodeValue: array1.join(","), //公司代码
                dateYearMonthStart: txt1, //日期
                dateYearMonthEnd: txt2,
                isExport: true
            }
            downloadFile(reportJson, ctx + "/crrc/reportFS59/downloadFile");
        });
        $.ajax({
            url: ctx + "/crrc/reportFS59/result",
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
                    $.each(data.data,function(k,v){
                        str+="<tr>"
                            +"<td width='200px'>" + v.ZTXT + "</td>"
                            + "<td width='40px' class='xh'>" + v.ZNUM + "</td>"
                            + "<td width='150px' style='text-align:right;'>" + formatNumber(v.BALANCE, 2, 1) + "</td>"
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
    }

})
