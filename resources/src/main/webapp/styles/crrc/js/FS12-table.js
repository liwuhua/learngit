//点击查询按钮，弹出查询框
var search1 = new multiSearch2($(".btn1"), "reportFS12/tcompcode", null, null);//公司代码
//控制表格高度的显示
var heights = $(window).height();
var heights_f=$(".container .col-sm-12 form").height();
$("#t_r_content").height((heights - heights_f - 160) + "px");
//点击收起按钮
$(".retract").off("click").on("click", function () {
    if ($(".container .row form").css("display") == "block") {
        $(".container .row form").slideUp();
        $(this).text("展开  ∨");
        var heights = $(window).height();
        $("#t_r_content").animate({
            "height": (heights - 160) + "px"
        });
    } else {
        $(".container .row form").slideDown();
        $(this).text("收起  ∧");
        var heights = $(window).height();
        $("#t_r_content").height((heights - heights_f - 160) + "px");
    }
});
//查询结果
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
            "startTime": txt1, //日期
            "endTime": txt2,
            isExport: false
        }
        reportJson = JSON.stringify(reportJson);
        $(".export").on("click", function () {
            reportJson = {
                compCodeValue: array1.join(","), //公司代码
                startTime: txt1, //日期
                endTime: txt2,
                isExport: true
            }
            downloadFile(reportJson, ctx + "/crrc/reportFS12/downloadFile");
        });
        $.ajax({
            url: ctx + "/crrc/reportFS12/result",
            data: reportJson,
            dataType: "json",
            contentType: "application/json;charset=utf-8",
            type: "post",
            success: function (data) {
                if ('300' === data.statusCode || '301' === data.statusCode) {
                    alert(data.message);
                    $(".Mask").hide();
                    $(".ch-kp-one table:eq(1)").html('');
                    $(".ch-kp-twelve").hide();
                    return true;
                }
                var str1 = "", str2 = "", str3 = "";
                if (data == null) {
                    $(".ch-kp-twelve").hide();
                    $(".notSearch").text("未查询到数据");
                } else {
                    $(".ch-kp-twelve").show();
                    var times = txt1.slice(0, 4) + "年" + txt1.slice(4, 6) + "月" + " - " + txt2.slice(0, 4) + "年" + txt2.slice(4, 6) + "月";
                    $(".dataTime1").text(times);
                    
                    for (var i = 0; i < 41; i++) {
                        str1 += "<tr>"
                        	for(var j=0;j<3;j++){
                        		var cur1 = data.periodexpense[j*41+i].curryearcount, cur2 = data.periodexpense[j*41+i].lastyearcount;
                                if (cur1 != "") {
                                    cur1 = formatNumber(cur1, 2, 1)
                                }
                                if (cur2 != "") {
                                    cur2 = formatNumber(cur2, 2, 1)
                                }
                        		str1+= "<td width='200px'>" + data.periodexpense[j*41+i].proname + "</td>"
		                                + "<td width='40px' class='xh'>" + data.periodexpense[j*41+i].rownum + "</td>"
		                                + "<td width='100px' style='text-align:right;'>" + cur1 + "</td>"
		                                + "<td width='100px' style='text-align:right;'>" + cur2 + "</td>"
                        	} 
                             str1+= "</tr>"
                    }
                    $("#t_r_content table").html(str1);
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

