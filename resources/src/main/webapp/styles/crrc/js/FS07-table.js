//点击查询按钮，弹出查询框
var search1=new multiSearch2($(".btn1"),"reportFS07/tcompcode",null,null);//公司代码
var search2=new multiSearch1($(".btn2"),"reportFS07/tcustomer",null,null);//客户编码
//收起展开控制高度
var heights = $(window).height();
var heights_f=$(".container .col-sm-12 form").height();
$(".ch-kp-seven #t_r_content").css("height",(heights-heights_f-160)+"px");
$(".retract").off("click").on("click", function () {
    if ($(".container form").css("display") == "block") {
        $(".container form").slideUp();
        $(this).text("展开  ∨");
        var heights = $(window).height();
        $(".ch-kp-seven #t_r_content").animate({
            "height": (heights-150) + "px"
        });
    } else {
        $(".container form").slideDown();
        $(this).text("收起  ∧");
        var heights = $(window).height();
        $(".ch-kp-seven #t_r_content").css("height",(heights-heights_f-160)+"px");
    }
});
var arr = [], arrTk = [],arrNum=[];
//应收账款统计分析
$("#submit").off("click").on("click", function () {
	var blog=true;
    $(".notSearch").empty();
    $("#show1").text("数据正在加载中");
    $("#show2").text("数据正在加载中");
    $(".ch-kp-seven").hide();
    var arrs = [], array1 = [], array2 = [];
    if ($(".gongsi").val() != "") {
        array1 = $(".gongsi").val().split(",");//公司代码
        if(blog){
            blog = compareInput(array1,comp_code,"公司代码");
        }
    }
    if ($(".kehu").val() != "") {
        array2 = $(".kehu").val().split(",");//客户
    }
    var txt1 = $("#startTime").val().replace(/-/g, "");//年月
    var txt2 = $("#endTime").val().replace(/-/g, "");
    if(!blog){
        return;
    }
    var reportJson={};
    if (txt1 == "" || txt2 == "") {
        alert("请输入日期");
    } else if (parseInt(txt1) > parseInt(txt2)) {
        alert("后一个日期必须大于或等于前一个日期");
    } else {
        $(".Mask").show();
        var item = 0;
        reportJson = {
            "compCodeValue": array1, //公司代码
            "kunnrValue": array2, //客户编码
            "dateYearMonthStart": txt1,
            "dateYearMonthEnd": txt2, //年月
            "startitem": item,
            "pageitem": 20
        }
        reportJson = JSON.stringify(reportJson);
        //处理导出
        $(".export").on("click", function () {
            reportJson = {
                "compCodeValue": array1, //公司代码
                "kunnrValue": array2, //客户编码
                "dateYearMonthStart": txt1,
                "dateYearMonthEnd": txt2,  //年月
                "pageitem": 20,
                isExport: true
            }
            downloadFile(reportJson, ctx + "/crrc/reportFS07/downloadFile");
        });
        //将输入条件返回给后台，并将查询结果进行展示
        $.ajax({
            url: ctx + "/crrc/reportFS07/result",
            data: reportJson,
            dataType: "json",
            contentType: "application/json;charset=utf-8",
            type: "post",
            success: function (data) {
                //打印数据
                if ('300' === data.statusCode || '301' === data.statusCode) {
                    alert(data.message);
                    $(".Mask").hide();
                    $(".ch-kp-seven table").html('');
                    $(".notSearch").next("div").hide();
                    return true;
                }
                if (data.length == 0) {
                    $(".notSearch").next("div").hide();
                    $(".notSearch").text("未查询到数据");
                } else {
                    $(".notSearch").next("div").show();
                    $(".dataTime1").text(getTime1(txt1, txt2));
                    if(array1.length==1){
                    	$(".table-data .bianzhi").html(data[0].gs_txt);
                    }
                    if (data.length < 20) {
                        $("#show1").html("数据加载完成");
                    } else {
                        $("#show1").html("");
                    }
                    var str = "";
                    $.each(data, function (key, val) {
                        //将输出的前20条内容，循环展示至表格当中
                        str += "<tr>"
                            + "<td class='xh' style='width: 50px;'>" + (key + 1) + "</td>"
                            + "<td class='bukrs' style='width: 60px;text-align: center;'>" + val.bukrs + "</td>"
                            + "<td class='gs_txt' style='width: 160px;'>" + val.gs_txt + "</td>"
                            + "<td class='kunnr' style='width: 85px;'>" + val.kunnr + "</td>"
                            + "<td class='kh_txt' style='width: 250px;'>" + val.kh_txt + "</td>"
                            + "<td class='hkont' style='width: 100px;'>" + val.hkont + "</td>"
                            + "<td class='dqxssr' style='width: 150px;text-align: right;'>" + formatNumber(val.dqxssr, 2, 1) + "</td>"
                            + "<td class='qcye' style='width: 110px;text-align: right;'>" + formatNumber(val.qcye, 2, 1) + "</td>"
                            + "<td class='xj_s' style='width: 120px;text-align: right;'>" + formatNumber(val.xj_s, 2, 1) + "</td>"
                            + "<td class='zyywsr' style='width: 120px;text-align: right;'>" + formatNumber(val.zyywsr, 2, 1) + "</td>"
                            + "<td class='qtywsr' style='width: 120px;text-align: right;'>" + formatNumber(val.qtywsr, 2, 1) + "</td>"
                            + "<td class='xxsj' style='width: 120px;text-align: right;'>" + formatNumber(val.xxsj, 2, 1) + "</td>"
                            + "<td class='qt' style='width: 120px;text-align: right;'>" + formatNumber(val.qt, 2, 1) + "</td>"
                            + "<td class='xj_h' style='width: 120px;text-align: right;'>" + formatNumber(val.xj_h, 2, 1) + "</td>"
                            + "<td class='xianjin' style='width: 120px;text-align: right;'>" + formatNumber(val.xianjin, 2, 1) + "</td>"
                            + "<td class='yhcd' style='width: 120px;text-align: right;'>" + formatNumber(val.yhcd, 2, 1) + "</td>"
                            + "<td class='sycd' style='width: 120px;text-align: right;'>" + formatNumber(val.sycd, 2, 1) + "</td>"
                            + "<td class='yunxin' style='width: 120px;text-align: right;'>" + formatNumber(val.yunxin, 2, 1) + "</td>"
                            + "<td class='dizhang' style='width: 120px;text-align: right;'>" + formatNumber(val.dizhang, 2, 1) + "</td>"
                            + "<td class='qmye' style='width: 120px;text-align: right;'>" + formatNumber(val.qmye, 2, 1) + "</td>"
                            + "<td class='ys_sixmonth' style='width: 120px;text-align: right;'>" + formatNumber(val.ys_sixmonth, 2, 1) + "</td>"
                            + "<td class='ys_sixone' style='width: 120px;text-align: right;'>" + formatNumber(val.ys_sixone, 2, 1) + "</td>"
                            + "<td class='ys_onetwo' style='width: 120px;text-align: right;'>" + formatNumber(val.ys_onetwo, 2, 1) + "</td>"
                            + "<td class='ys_twothree' style='width: 120px;text-align: right;'>" + formatNumber(val.ys_twothree, 2, 1) + "</td>"
                            + "<td class='ys_threefour' style='width: 120px;text-align: right;'>" + formatNumber(val.ys_threefour, 2, 1) + "</td>"
                            + "<td class='ys_fourfive' style='width: 120px;text-align: right;'>" + formatNumber(val.ys_fourfive, 2, 1) + "</td>"
                            + "<td class='ys_fiveyear' style='width: 120px;text-align: right;'>" + formatNumber(val.ys_fiveyear, 2, 1) + "</td>"
                            + "<td class='zb_sixmonth' style='width: 120px;text-align: right;'>" + formatNumber(val.zb_sixmonth, 2, 1) + "</td>"
                            + "<td class='zb_sixone' style='width: 120px;text-align: right;'>" + formatNumber(val.zb_sixone, 2, 1) + "</td>"
                            + "<td class='zb_onetwo' style='width: 120px;text-align: right;'>" + formatNumber(val.zb_onetwo, 2, 1) + "</td>"
                            + "<td class='zb_twothree' style='width: 120px;text-align: right;'>" + formatNumber(val.zb_twothree, 2, 1) + "</td>"
                            + "<td class='zb_threefour' style='width: 120px;text-align: right;'>" + formatNumber(val.zb_threefour, 2, 1) + "</td>"
                            + "<td class='zb_fourfive' style='width: 120px;text-align: right;'>" + formatNumber(val.zb_fourfive, 2, 1) + "</td>"
                            + "<td class='zb_fiveyear' style='width: 120px;text-align: right;'>" + formatNumber(val.zb_fiveyear, 2, 1) + "</td>"
                            + "<td class='zb_xiaoji' style='text-align: right;'>" + formatNumber(val.zb_xiaoji, 2, 1) + "</td>"
                            + "</tr>";
                    });
                    $("#t_r_content table").html(str);
                    trBg($("#t_r_content table"));
                }
                $(".ch-kp-seven").show();
                $(".Mask").hide();
            },
            error: function () {
                alert("数据请求失败1111!");
                $(".Mask").hide();
            }
        });
        //处理分页后的，后20条内容下拉刷新
        item = 20;
        var b = true;
        $(".t_r_content").unbind("scroll").bind("scroll", function () {
            var fold = $(".t_r_content").height() + $(".t_r_content")[0].scrollTop;
            if (b && fold >= $(".t_r_content")[0].scrollHeight) {
                b = false;
                if($("#show1").html()=="数据加载完成"){
                    $(".Mask").hide();
                }else{
                    $(".Mask").show();
                }
                reportJson = {
                    "compCodeValue": array1, //公司代码
                    "kunnrValue": array2, //客户编码
                    "dateYearMonthStart": txt1,
                    "dateYearMonthEnd": txt2, //年月
                    "startitem": item,
                    "pageitem": 20
                }
                reportJson = JSON.stringify(reportJson);
                if ($("#show1").text() != "数据加载完成") {
                    $.ajax({
                        url: ctx +  "/crrc/reportFS07/result",
                        data: reportJson,
                        dataType: "json",
                        contentType: "application/json;charset=utf-8",
                        type: "post",
                        success: function (data) {
                            if (data.length == 0) {
                                $("#show1").html("数据加载完成");
                                b = false;
                            }
                            var str = "";
                            $.each(data, function (key, val) {
                                str += "<tr>"
                                    + "<td class='xh' style='width: 50px;'>" + (key +1+item) + "</td>"
                                    + "<td class='bukrs' style='width: 60px;text-align: center;'>" + val.bukrs + "</td>"
                                    + "<td class='gs_txt' style='width: 160px;'>" + val.gs_txt + "</td>"
                                    + "<td class='kunnr' style='width: 85px;'>" + val.kunnr + "</td>"
                                    + "<td class='kh_txt' style='width: 250px;'>" + val.kh_txt + "</td>"
                                    + "<td class='hkont' style='width: 100px;'>" + val.hkont + "</td>"
                                    + "<td class='dqxssr' style='width: 150px;text-align: right;'>" + formatNumber(val.dqxssr, 2, 1) + "</td>"
                                    + "<td class='qcye' style='width: 110px;text-align: right;'>" + formatNumber(val.qcye, 2, 1) + "</td>"
                                    + "<td class='xj_s' style='width: 120px;text-align: right;'>" + formatNumber(val.xj_s, 2, 1) + "</td>"
                                    + "<td class='zyywsr' style='width: 120px;text-align: right;'>" + formatNumber(val.zyywsr, 2, 1) + "</td>"
                                    + "<td class='qtywsr' style='width: 120px;text-align: right;'>" + formatNumber(val.qtywsr, 2, 1) + "</td>"
                                    + "<td class='xxsj' style='width: 120px;text-align: right;'>" + formatNumber(val.xxsj, 2, 1) + "</td>"
                                    + "<td class='qt' style='width: 120px;text-align: right;'>" + formatNumber(val.qt, 2, 1) + "</td>"
                                    + "<td class='xj_h' style='width: 120px;text-align: right;'>" + formatNumber(val.xj_h, 2, 1) + "</td>"
                                    + "<td class='xianjin' style='width: 120px;text-align: right;'>" + formatNumber(val.xianjin, 2, 1) + "</td>"
                                    + "<td class='yhcd' style='width: 120px;text-align: right;'>" + formatNumber(val.yhcd, 2, 1) + "</td>"
                                    + "<td class='sycd' style='width: 120px;text-align: right;'>" + formatNumber(val.sycd, 2, 1) + "</td>"
                                    + "<td class='yunxin' style='width: 120px;text-align: right;'>" + formatNumber(val.yunxin, 2, 1) + "</td>"
                                    + "<td class='dizhang' style='width: 120px;text-align: right;'>" + formatNumber(val.dizhang, 2, 1) + "</td>"
                                    + "<td class='qmye' style='width: 120px;text-align: right;'>" + formatNumber(val.qmye, 2, 1) + "</td>"
                                    + "<td class='ys_sixmonth' style='width: 120px;text-align: right;'>" + formatNumber(val.ys_sixmonth, 2, 1) + "</td>"
                                    + "<td class='ys_sixone' style='width: 120px;text-align: right;'>" + formatNumber(val.ys_sixone, 2, 1) + "</td>"
                                    + "<td class='ys_onetwo' style='width: 120px;text-align: right;'>" + formatNumber(val.ys_onetwo, 2, 1) + "</td>"
                                    + "<td class='ys_twothree' style='width: 120px;text-align: right;'>" + formatNumber(val.ys_twothree, 2, 1) + "</td>"
                                    + "<td class='ys_threefour' style='width: 120px;text-align: right;'>" + formatNumber(val.ys_threefour, 2, 1) + "</td>"
                                    + "<td class='ys_fourfive' style='width: 120px;text-align: right;'>" + formatNumber(val.ys_fourfive, 2, 1) + "</td>"
                                    + "<td class='ys_fiveyear' style='width: 120px;text-align: right;'>" + formatNumber(val.ys_fiveyear, 2, 1) + "</td>"
                                    + "<td class='zb_sixmonth' style='width: 120px;text-align: right;'>" + formatNumber(val.zb_sixmonth, 2, 1) + "</td>"
                                    + "<td class='zb_sixone' style='width: 120px;text-align: right;'>" + formatNumber(val.zb_sixone, 2, 1) + "</td>"
                                    + "<td class='zb_onetwo' style='width: 120px;text-align: right;'>" + formatNumber(val.zb_onetwo, 2, 1) + "</td>"
                                    + "<td class='zb_twothree' style='width: 120px;text-align: right;'>" + formatNumber(val.zb_twothree, 2, 1) + "</td>"
                                    + "<td class='zb_threefour' style='width: 120px;text-align: right;'>" + formatNumber(val.zb_threefour, 2, 1) + "</td>"
                                    + "<td class='zb_fourfive' style='width: 120px;text-align: right;'>" + formatNumber(val.zb_fourfive, 2, 1) + "</td>"
                                    + "<td class='zb_fiveyear' style='width: 120px;text-align: right;'>" + formatNumber(val.zb_fiveyear, 2, 1) + "</td>"
                                    + "<td class='zb_xiaoji' style='text-align: right;'>" + formatNumber(val.zb_xiaoji, 2, 1) + "</td>"
                                    + "</tr>";
                            });
                            item = item + 20;
                            $("#t_r_content table").append(str);
                            trBg($("#t_r_content table"));
                            $(".Mask").hide();
                            b = true;
                        },
                        error: function () {
                            alert("数据请求失败1111!");
                            $(".Mask").hide();
                        }
                    })
                }
            }
        })

    }
});
var Browsers = {};
var ua = navigator.userAgent.toLowerCase();
if (window.ActiveXObject) {
    Browsers.ie = ua.match(/msie ([\d.]+)/)[1]
}
if (Browsers.ie === "8.0") {
    $("#t_r_t span").removeAttr("style");
}