var now = new Date();
var nowMonth = now.getMonth() + 1;
var nowYear = now.getFullYear();
if(nowMonth<10){
    nowMonth="0"+nowMonth;
}
var val = $("#startTime").val(nowYear + "-" + nowMonth);
var txt1 = $("#startTime").val().replace(/-/g, "");
dataNum(txt1);
//控制表格高度的显示
var heights = $(window).height();
$(".ch-kp-sixteen .t_r_content").height((heights - 200) + "px");
//点击收起按钮
$(".retract").off("click").on("click", function () {
    if ($(".container form").css("display") == "block") {
        $(".container form").slideUp();
        $(this).text("展开  ∨");
        var heights = $(window).height();
        $(".ch-kp-sixteen .t_r_content").animate({
            "height": (heights - 145) + "px"
        });
    } else {
        $(".container form").slideDown();
        $(this).text("收起  ∧");
        var heights = $(window).height();
        $(".ch-kp-sixteen .t_r_content").height((heights - 200) + ("px"));
    }
})
//点击下载模板
$("#downMb").off("click").on("click", function () {
    var txt = $("#startTime").val().replace(/-/g, "").slice(4);
    if(txt.length==1){
        txt="0"+txt;
        var txt2=$("#startTime").val().replace(/-/g, "").slice(0,4)+txt;
    }else if(txt.length==2){
        var txt2 = $("#startTime").val().replace(/-/g, "");
    }
    reportJson1={
        compCodeValue: "5003-template", //公司代码
        dateYearMonth: txt2, //日期
        isExport: true
    };
    downloadFile(reportJson1,ctx + "/crrc/reportFS16/downloadFile")
})
//表格
$("#submit").off("click").on("click", function () {
    $(".notSearch").empty();
    var txt1 = $("#startTime").val().replace(/-/g, "");


    if (txt1 == "") {
        alert("请输入日期");
    } else {
        $(".Mask").show();
        dataNum(txt1);
    }

})
function dataNum(txt1) {
    var reportJson = {
        "compCodeValue": ["5003"], //公司代码
        "dateYearMonth": txt1, //日期
        isExport: false
    }
    reportJson = JSON.stringify(reportJson);
    $(".export").on("click", function () {
        reportJson = {
            compCodeValue: "5003", //公司代码
            dateYearMonth: txt1, //日期
            isExport: true
        }
        downloadFile(reportJson, ctx + "/crrc/reportFS16/downloadFile");
    });
    $.ajax({
        url: ctx + "/crrc/reportFS16/result",
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
            if (data.length == 0) {
                $(".notSearch").next("div").hide();
                $(".notSearch").text("未查询到数据");
            } else {
                $(".notSearch").next("div").show();
                var times = txt1.slice(0, 4) + "年" + txt1.slice(4, 6) + "月";
                $(".dataTime1 span").text(times);
                var str_h = "", str_b = "";
                str_h="<th width='280px'>"+data[0].itemName+"</th>"+
                        "<th class='xh' width='40px'>"+data[0].itemNo+"</th>"+
                        "<th width='105px'>"+data[0].thisYearSum+"</th>"+
                        "<th width='105px'>"+data[0].monthMoney+"</th>";
                if(data[1].lastYearSum!=""){
                	str_h+="<th width='170px'>"+data[0].thisYearSum+"</th>";
                }     
                str_h+="<th width='280px'>"+data[0].itemName+"</th>"+
                        "<th class='xh' width='40px'>"+data[0].itemNo+"</th>"+
                        "<th width='105px'>"+data[0].thisYearSum+"</th>"+
                        "<th width='105px'>"+data[0].monthMoney+"</th>";
                if(data[1].lastYearSum!=""){
                	str_h+="<th style='padding-right:17px;'>"+data[0].thisYearSum+"</th>";
                }
                $(".t_r_title table tr").html(str_h);
                $(".t_r_title table tr td:last-child").removeAttr("width");
                for(var i=1;i<data.length/2;i++){
                	str_b += "<tr>";
                    for(var j=0;j<2;j++){
                        /*var byje=Number(data[data.length/2*j+i].monthMoney);
                        var bnlj=Number(data[data.length/2*j+i].thisYearSum);
                        var sntqlj=Number(data[data.length/2*j+i].lastYearSum);*/
                        var byje,bnlj,sntqlj;
                        if(byje=="——" || byje==""){
                            byje=data[data.length/2*j+i].monthMoney;
                        }else if(byje!=""){
                            byje=formatNumber(data[data.length/2*j+i].monthMoney, 2, 1);
                        }
                        if(bnlj=="——" || byje==""){
                            bnlj=data[data.length/2*j+i].thisYearSum;
                        }else if(bnlj!=""){
                            bnlj=formatNumber(data[data.length/2*j+i].thisYearSum, 2, 1);
                        }
                        str_b += "<td style='width:280px'>" + data[data.length/2*j+i].itemName + "</td>"
                              + "<td class='xh' style='width:40px'>" + data[data.length/2*j+i].itemNo + "</td>"
                              + "<td class='monthMoney' style='text-align:right;width: 105px;'>" + byje + "</td>"
                              + "<td class='thisYearSum' style='text-align:right;width: 105px;'>" + bnlj + "</td>";
                        if(data[1].lastYearSum!=""){
                        	if(sntqlj=="——" || byje==""){
                                sntqlj=data[data.length/2*j+i].lastYearSum;
                            }else if(bnlj!=""){
                                sntqlj=formatNumber(data[data.length/2*j+i].lastYearSum, 2, 1);
                            }
                        	str_b += "<td class='lastYearSum' style='text-align:right;' width='170px'>" + sntqlj + "</td>";
                        }
                              
                    }
                    str_b += "</tr>";
                }
                $("#t_r_content table").html(str_b);
                $("#t_r_content table tr td:last-child").removeAttr("width");
                trBg($("#t_r_content table"));
            }
            $(".Mask").hide();
        },
        error: function () {
            alert("error1");
            $(".Mask").hide();
        }
    });
}
