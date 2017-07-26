//var now = new Date();
//var nowMonth = now.getMonth() + 1;
//var nowYear = now.getFullYear();
//if(nowMonth<10){
//    nowMonth="0"+nowMonth;
//}
//var val = $("#startTime").val(nowYear + "-" + nowMonth);
//var txt1 = $("#startTime").val().replace(/-/g, "");
//点击查询按钮，弹出查询框
var search1=new multiSearch2($(".btn1"),"reportFS37/tplant",null,null);//工厂
var search2=new multiSearch6($(".btn2"),"tmaterial",0,10);//物料
//控制表格高度的显示
var heights = $(window).height();
$(".ch-kp-thirtySeven .t_r_content").height((heights - 205) + "px");
//点击收起按钮
$(".retract").off("click").on("click", function () {
    if ($(".container form").css("display") == "block") {
        $(".container form").slideUp();
        $(this).text("展开  ∨");
        var heights = $(window).height();
        $(".ch-kp-thirtySeven.t_r_content").animate({
            "height": (heights - 145) + "px"
        });
    } else {
        $(".container form").slideDown();
        $(this).text("收起  ∧");
        var heights = $(window).height();
        $(".ch-kp-thirtySeven.t_r_content").height((heights - 225) + ("px"));
    }
})
var arr = [], arrTk = [];
//采购入库的表格
$("#submit").off("click").on("click", function () {
    var blog = true;
    $(".notSearch").empty();
    $("#show").text("数据正在加载中");
    //$(".hideCol").click();
    var  array1 = [], array2 = [];
    if ($(".factory").val() != "") {
        array1 = $(".factory").val().split(",");//工厂
        if(blog){
            blog = compareInput(array1,plant,"工厂");
        }
    }
    if ($(".material").val() != "") {
        array2 = $(".material").val().split(",");//物料
    }
    var reportJson={};
    if(!blog){
        return;
    }
    if(array1.length==0||array2.length==0){
    	alert("请输入必填条件");
    	return;
    }
    $(".Mask").show();
    var item = 0;
     reportJson = {
        "plantValue": array1, //工厂
        "matnrValue": array2 //物料
        //"matnrValue": ["000000Z500000189039"] //物料
    }
    reportJson = JSON.stringify(reportJson);
    /*$(".export").on("click", function () {
        reportJson = {
            plantValue: array1.join(','), //工厂
            matnrValue: array2.join(','), //物料
            matnrInterval: array5.join(','), //物料多区间
            vendorValue: array3.join(','), //供应商
            purGroupValue: array4.join(','), //采购组
            isShow: num, //是否显示公司间交易业务
            startTime: txt1,
            endTime: txt2, //入库日期
            isExport: true
        }
        downloadFile(reportJson, ctx + "/crrc/reportFS01/downloadFile");
    });*/
    $.ajax({
        url: ctx + "/crrc/reportFS37/result",
        data: reportJson,
        dataType: "json",
        contentType: "application/json;charset=utf-8",
        type: "post",
        success: function (data) {
            if ('300' === data.statusCode || '301' === data.statusCode) {
                alert(data.message);
                $(".Mask").hide();
                $(".ch-kp-thirtySeven table:eq(1)").html('');
                return true;
            }
            if (data.DATA.length == 0) {
                $(".notSearch").next("div").hide();
                $(".notSearch").text("未查询到数据");
            } else {
                $(".notSearch").next("div").show();

                if (data.DATA.length < 20) {
                    $("#show").html("数据加载完成");
                } else {
                    $("#show").html("");
                }
                var str = "";
                var zjyc=0,zjyd=0,zjnd=0,bqbz=0;
                $.each(data.DATA, function (key, val) {
                    str += "<tr>"
	                            + "<td style='width:40px' class='xh'>" + (key + 1) + "</td>"
	                            + "<td style='width:80px' class='dstuf'>" + val.dstuf + "</td>"
	                            + "<td style='width:100px' class='posnr'>" + val.posnr + "</td>"
	                            + "<td style='width:120px' class='idnrk'>" + val.idnrk + "</td>"
	                            + "<td style='width:240px' class='txtmd'>" + val.txtmd + "</td>"
	                            + "<td style='width:60px' class='meins'>" + val.meins + "</td>"
	                            + "<td style='width:60px' class='meinb'>" + val.meinb + "</td>"
	                            + "<td style='width:100px' class='menge'>" + val.menge + "</td>"
	                            + "<td style='width:140px' class='menga'>" + val.menga + "</td>"
	                            + "<td style='width:140px' class='ekgrp'>" + val.ekgrp + "</td>"
	                            + "<td style='width:110px;text-align:right;' class='last_price'>" + Number(val.last_price).toFixed(2) + "</td>"
	                            + "<td style='width:110px;text-align:right;' class='last_cost'>" + Number(val.last_cost).toFixed(2) + "</td>"
	                            + "<td style='width:110px;text-align:right;' class='last_month_price'>" + Number(val.last_month_price).toFixed(2) + "</td>"
	                            + "<td style='width:110px;text-align:right;' class='last_month_cost'>" + Number(val.last_month_cost).toFixed(2) + "</td>"
	                            + "<td style='width:110px;text-align:right;' class='last_year_price'>" + Number(val.last_year_price).toFixed(2) + "</td>"
	                            + "<td style='width:110px;text-align:right;' class='last_year_cost'>" + Number(val.last_year_cost).toFixed(2) + "</td>"
	                            + "<td style='width:110px;text-align:right;' class='standard_price'>" + Number(val.standard_price).toFixed(2) + "</td>"
	                            + "<td style='width:80px;text-align:center;' class='unit_price'>" + Number(val.unit_price).toFixed(2) + "</td>"
	                            + "<td class='standard_cost' style='text-align:right;'>" + Number(val.standard_cost).toFixed(2) + "</td>"
                            + "</tr>";
                    zjyc=zjyc+Number(Number(val.last_cost).toFixed(2));
                    zjyd=zjyd+Number(Number(val.last_month_cost).toFixed(2));
                    zjnd=zjnd+Number(Number(val.last_year_cost).toFixed(2));
                    bqbz=bqbz+Number(Number(val.standard_cost).toFixed(2));
                });
                $(".ch-kp-thirtySeven table:eq(1)").html(str);
                $(".hj .zjyc").text(zjyc.toFixed(2));
                $(".hj .zjyd").text(zjyd.toFixed(2));
                $(".hj .zjnd").text(zjnd.toFixed(2));
                $(".hj .bqbz").text(bqbz.toFixed(2));
                //$(".hj p span:eq(0) b").text(data.SUM.menge);
                //$(".hj p span:eq(1) b").text(formatNumber(data.SUM.bualt,2,1));
                trBg($(".ch-kp-thirtySeven table:eq(1)"))
            }
            $(".Mask").hide();

        },
        error: function () {
            alert("数据请求失败!");
            $(".Mask").hide();
        }
    })
})
