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
var heights_f=$(".container .col-sm-12 form").height();
$("#t_r_content").height((heights - heights_f - 170) + "px");
$("#t_r_content1").height((heights - heights_f - 170) + "px");
//点击收起按钮
$(".retract").off("click").on("click", function () {
    if ($(".container .row form").css("display") == "block") {
        $(".container .row form").slideUp();
        $(this).text("展开  ∨");
        var heights = $(window).height();
        $("#t_r_content").animate({
            "height": (heights - 170) + "px"
        });
        $("#t_r_content1").animate({
            "height": (heights - 170) + "px"
        });
    } else {
        $(".container .row form").slideDown();
        $(this).text("收起  ∧");
        var heights = $(window).height();
        $("#t_r_content").height((heights - heights_f - 170) + "px");
        $("#t_r_content1").height((heights - heights_f - 170) + "px");
    }
});
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
    downloadFile(reportJson1,ctx + "/crrc/reportFS15/downloadFile")
})
$("#submit").off("click").on("click", function () {
    $(".notSearch").empty();
    var txt1 = $("#startTime").val().replace(/-/g, "");

    if (txt1 == "") {
        alert("请输入日期");
    } else {
        $(".Mask").show();
        dataNum(txt1);
    }

});
function dataNum(txt1) {
    var reportJson = {
        "compCodeValue": ["5003"], //公司代码
        "dateYearMonth": txt1, //日期
        isExport: false
    }
    reportJson = JSON.stringify(reportJson);
    $(".dataTime1").text($("#startTime").val());
    $(".export").on("click", function () {
        reportJson = {
            compCodeValue: "5003", //公司代码
            dateYearMonth: txt1, //日期
            isExport: true
        }
        downloadFile(reportJson, ctx + "/crrc/reportFS15/downloadFile");
    });
    $.ajax({
        url: ctx + "/crrc/reportFS15/result",
        data: reportJson,
        dataType: "json",
        contentType: "application/json;charset=utf-8",
        type: "post",
        success: function (data) {
        	if ('300' === data.statusCode || '301' === data.statusCode) {
                alert(data.message);
                $(".Mask").hide();
                $("#t_r_content table").html('');
                $("#t_r_content1 table").html('');
                $(".notSearch").next("div").hide();
                return true;
            }
            if (data.length == 0) {
                $(".notSearch").next("div").hide();
                $(".notSearch").text("未查询到数据");
            } else {
                $(".notSearch").next("div").show();
                var times = txt1.slice(0, 4) + "年" + txt1.slice(4, 6) + "月";
                $(".dataTime1").text(times);
                var str_h='',str_b='';
                str_h+="<th width='190px'>"+data[0].itemname+"</th>" +
                		"<th width='40px' class='xh'>"+data[0].itemno+"</th>" +
                		"<th width='110px'>"+data[0].finalmoney+"</th>" +
                		"<th width='110px'>"+data[0].startmoney+"</th>" +
        				"<th width='190px'>"+data[data.length/2].itemname+"</th>" +
						"<th width='40px' class='xh'>"+data[data.length/2].itemno+"</th>" +
						"<th width='110px'>"+data[data.length/2].finalmoney+"</th>" +
						"<th style='padding-right:17px;'>"+data[data.length/2].startmoney+"</th>";
                $("#t_r_t .t_r_title table tr").html(str_h);
                for(var i=1;i < data.length/2; i++){
                	str_b += "<tr>";
                	for(var j=0;j<2;j++){
                		var fm,sm;
                        if(fm=="——" || fm==""){
                            fm=data[data.length/2*j+i].finalmoney;
                        }else{
                    		fm=formatNumber(data[data.length/2*j+i].finalmoney, 2, 1);
                    	}
                        if(sm=="——" || sm==""){
                            sm=data[data.length/2*j+i].startmoney;
                        }else{
                    		sm=formatNumber(data[data.length/2*j+i].startmoney, 2, 1);
                    	}
                        str_b+="<td width='190px'>" + data[data.length/2*j+i].itemname + "</td>"
                                + "<td class='xh' width='40px'>" + data[data.length/2*j+i].itemno + "</td>"
                                + "<td width='110px' style='text-align:right;'>" + fm + "</td>"
                                + "<td width='110px' style='text-align:right;'>" + sm + "</td>"
                	}
                }
                /*for (var i = 0; i < data.length/2; i++) {
                	str1 += "<tr>"
                	for(var j=0;j<2;j++){
                		//var fm=data[data.length/2*j+i].finalmoney,sm=data[data.length/2*j+i].startmoney;
                    	var fm,sm;
                        if(fm=="——" || fm==""){
                            fm=data[data.length/2*j+i].finalmoney;
                        }else if(fm!=""){
                    		fm=formatNumber(data[data.length/2*j+i].finalmoney, 2, 1);
                    	}
                        if(sm=="——" || sm==""){
                            sm=data[data.length/2*j+i].startmoney;
                        }else if(sm!=""){
                    		sm=formatNumber(data[data.length/2*j+i].startmoney, 2, 1);
                    	}
                        str1+="<td width='190px'>" + data[data.length/2*j+i].itemname + "</td>"
                                + "<td class='xh' width='40px'>" + data[data.length/2*j+i].itemno + "</td>"
                                + "<td width='110px' style='text-align:right;'>" + fm + "</td>"
                                + "<td width='110px' style='text-align:right;'>" + sm + "</td>"
                                
                	}
                	str1+="</tr>";
                }*/
                
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


