//点击查询按钮，弹出查询框
var search1=new multiSearch2($(".btn1"),"reportFS22/tcompcode",null,null);//公司代码
var search2=new multiSearch3($(".btn2"),"reportFS22/innerOrder",null,null);//内部订单
//控制表格高度的显示
var heights = $(window).height();
$(".ch-kp-twentyTwo1 .t_r_content").height((heights - 255) + "px");
$(".ch-kp-twentyTwo2 .t_r_content").height((heights - 285) + "px");
//点击收起按钮
$(".retract").off("click").on("click", function () {
    if ($(".container form").css("display") == "block") {
        $(".container form").slideUp();
        $(this).text("展开  ∨");
        var heights = $(window).height();
        $(".ch-kp-twentyTwo1 .t_r_content").animate({
            "height": (heights - 215) + "px"
        });
        $(".ch-kp-twentyTwo2 .t_r_content").animate({
            "height": (heights - 245) + "px"
        });
    } else {
        $(".container form").slideDown();
        $(this).text("收起  ∧");
        var heights = $(window).height();
        $(".ch-kp-twentyTwo1 .t_r_content").height((heights - 255) + ("px"));
        $(".ch-kp-twentyTwo2 .t_r_content").height((heights - 285) + ("px"));
    }
})
var arr = [], arrTk = [],arrNum=[];
//采购入库的表格
$("#submit").off("click").on("click", function () {
	var blog=true;
    $(".notSearch").empty();
    $("#show1").text("数据正在加载中");
    $("#show2").text("数据正在加载中");
    $("#btnChange").val("二级明细");
    $(".ch-kp-twentyTwo2").hide();
    $(".import").hide();
    var arrs = [], array1 = [], array2 = [];
    if ($(".gongsi").val() != "") {
        array1 = $(".gongsi").val().split(",");//公司代码
        if(blog){
            blog = compareInput(array1,comp_code,"公司代码");
        }
    }
    if ($(".internalOrder").val() != "") {
        array2 = $(".internalOrder").val().split(",");//内部订单
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
            "orderID": array2, //内部订单
            "dateYearMonthStart": txt1,
            "dateYearMonthEnd": txt2, //入库日期
            "startitem": item,
            "pageitem": 20
        }
        reportJson = JSON.stringify(reportJson);
        $(".export").off("click").on("click", function () {
            reportJson = {
                compValues: array1.join(','), //公司代码
                orderID:array2.join(','),//内部订单
                startTime: txt1,
                endTime: txt2, //入库日期
                exportflag:1,
                isExport: true
            };
            downloadFile(reportJson, ctx + "/crrc/reportFS22/downloadFile");
        });
         $.ajax({
             url: ctx + "/crrc/reportFS22/result01",
             data: reportJson,
             dataType: "json",
             contentType: "application/json;charset=utf-8",
             type: "post",
             success: function (data) {
                 if ('300' === data.statusCode || '301' === data.statusCode) {
                     alert(data.message);
                     $(".Mask").hide();
                     $(".ch-kp-twentyTwo1 table").html('');
                     $(".ch-kp-twentyTwo2 table").html('');
                     return true;
                 }
                 if (data.periodexpense.length == 0) {
                     $(".notSearch").next("div").hide();
                     $(".notSearch").text("未查询到数据");
                 } else {
                     $(".notSearch").next("div").show();
                     $(".dataTime1").text(getTime1(txt1, txt2));
                     if (data.periodexpense.length < 20) {
                         $("#show1").html("数据加载完成");
                     } else {
                         $("#show1").html("");
                     }
                     var str = "";
                     $.each(data.periodexpense, function (key, val) {
                    	 arrNum.push(val.qita)
                         str += "<tr>"
                                     + "<td class='aufnr' style='width: 100px;'>" + val.aufnr + "</td>"
                                     + "<td class='ktext' style='width: 280px;'>" + val.ktext + "</td>"
                                     + "<td class='all_buget' style='width: 130px;text-align: right;'>" + formatNumber(val.all_buget,2,1) + "</td>"
                                     + "<td class='bef_years_cost' style='width: 130px;text-align: right;'>" + formatNumber(val.bef_years_cost,2,1) + "</td>"
                                     + "<td class='wtjhv' style='width: 110px;text-align: right;'>" + formatNumber(val.wtjhv,2,1) + "</td>"
                                     + "<td class='curr_year_cost' style='width: 100px;text-align: right;'>" + formatNumber(val.curr_year_cost,2,1) + "</td>"
                                     + "<td class='peroid_cost' style='width: 130px;text-align: right;'>" + formatNumber(val.peroid_cost,2,1) + "</td>"
                                     + "<td class='cailiao' style='width: 120px;text-align: right;'>" + formatNumber(val.cailiao,2,1) + "</td>"
                                     + "<td class='ranliao' style='width: 120px;text-align: right;'>" + formatNumber(val.ranliao,2,1) + "</td>"
                                     + "<td class='dongli' style='width: 120px;text-align: right;'>" + formatNumber(val.dongli,2,1) + "</td>"
                                     + "<td class='rengong' style='width: 120px;text-align: right;'>" + formatNumber(val.rengong,2,1) + "</td>"
                                     + "<td class='zhejiu' style='width: 120px;text-align: right;'>" + formatNumber(val.zhejiu,2,1) + "</td>"
                                     + "<td class='wuxingzichan' style='width: 90px;text-align: right;'>" + formatNumber(val.wuxingzichan,2,1) + "</td>"
                                     + "<td class='gongju' style='width: 120px;text-align: right;'>" + formatNumber(val.gongju,2,1) + "</td>"
                                     + "<td class='shiyan' style='width: 120px;text-align: right;'>" + formatNumber(val.shiyan,2,1) + "</td>"
                                     + "<td class='jianding' style='width: 240px;text-align: right;'>" + formatNumber(val.jianding,2,1) + "</td>"
                                     + "<td class='zhuanli' style='width: 120px;text-align: right;'>" + formatNumber(val.zhuanli,2,1) + "</td>"
                                     + "<td class='jishu' style='width: 120px;text-align: right;'>" + formatNumber(val.jishu,2,1) + "</td>"
                                     + "<td class='zhuanrang' style='width: 120px;text-align: right;'>" + formatNumber(val.zhuanrang,2,1) + "</td>"
                                     + "<td class='ziliao' style='width: 100px;text-align: right;'>" + formatNumber(val.ziliao,2,1) + "</td>"
                                     + "<td class='chailv' style='width: 120px;text-align: right;'>" + formatNumber(val.chailv,2,1) + "</td>"
                                     + "<td class='bangong' style='width: 120px;text-align: right;'>" + formatNumber(val.bangong,2,1) + "</td>"
                                     + "<td class='xiuli' style='width: 120px;text-align: right;'>" + formatNumber(val.xiuli,2,1) + "</td>"
                                     + "<td class='qita1' style='text-align: right;'>" + formatNumber(val.qita,2,1) + "</td>"
                                 + "</tr>";
                     });
                     $("#t_r_content table").html(str);
                     trBg($("#t_r_content table"));
             		}
                 $(".ch-kp-twentyTwo1").show();
                 $(".Mask").hide();
                 $(".bianzhi").text(data.BZDW);
             },
             error: function () {
                 alert("数据请求失败1111!");
                 $(".Mask").hide();
             }
         })

         item = 20;
         var b = true;
         $(".t_r_content").unbind("scroll").bind("scroll", function () {
             toTop($(".toTop"),$("#t_r_content"));
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
                         "orderID": array2, //内部订单
                         "dateYearMonthStart": txt1,
                         "dateYearMonthEnd": txt2, //入库日期
                         "startitem": item,
                         "pageitem": 20
                     }
                 reportJson = JSON.stringify(reportJson);
                 if ($("#show1").text() != "数据加载完成") {
                     $.ajax({
                         url: ctx + "/crrc/reportFS22/result01",
                         data: reportJson,
                         dataType: "json",
                         contentType: "application/json;charset=utf-8",
                         type: "post",
                         success: function (data) {
                             if (data.periodexpense.length == 0) {
                                 $("#show1").html("数据加载完成");
                                 b = false;
                             }
                             var str = "";
                             $.each(data.periodexpense, function (key, val) {
                            	 arrNum.push(val.qita)
                                 str += "<tr>"
                                     + "<td class='aufnr' style='width: 100px;'>" + val.aufnr + "</td>"
                                     + "<td class='ktext' style='width: 280px;'>" + val.ktext + "</td>"
                                     + "<td class='all_buget' style='width: 130px;text-align: right;'>" + formatNumber(val.all_buget,2,1) + "</td>"
                                     + "<td class='bef_years_cost' style='width: 130px;text-align: right;'>" + formatNumber(val.bef_years_cost,2,1) + "</td>"
                                     + "<td class='wtjhv' style='width: 110px;text-align: right;'>" + formatNumber(val.wtjhv,2,1) + "</td>"
                                     + "<td class='curr_year_cost' style='width: 100px;text-align: right;'>" + formatNumber(val.curr_year_cost,2,1) + "</td>"
                                     + "<td class='peroid_cost' style='width: 130px;text-align: right;'>" + formatNumber(val.peroid_cost,2,1) + "</td>"
                                     + "<td class='cailiao' style='width: 120px;text-align: right;'>" + formatNumber(val.cailiao,2,1) + "</td>"
                                     + "<td class='ranliao' style='width: 120px;text-align: right;'>" + formatNumber(val.ranliao,2,1) + "</td>"
                                     + "<td class='dongli' style='width: 120px;text-align: right;'>" + formatNumber(val.dongli,2,1) + "</td>"
                                     + "<td class='rengong' style='width: 120px;text-align: right;'>" + formatNumber(val.rengong,2,1) + "</td>"
                                     + "<td class='zhejiu' style='width: 120px;text-align: right;'>" + formatNumber(val.zhejiu,2,1) + "</td>"
                                     + "<td class='wuxingzichan' style='width: 90px;text-align: right;'>" + formatNumber(val.wuxingzichan,2,1) + "</td>"
                                     + "<td class='gongju' style='width: 120px;text-align: right;'>" + formatNumber(val.gongju,2,1) + "</td>"
                                     + "<td class='shiyan' style='width: 120px;text-align: right;'>" + formatNumber(val.shiyan,2,1) + "</td>"
                                     + "<td class='jianding' style='width: 240px;text-align: right;'>" + formatNumber(val.jianding,2,1) + "</td>"
                                     + "<td class='zhuanli' style='width: 120px;text-align: right;'>" + formatNumber(val.zhuanli,2,1) + "</td>"
                                     + "<td class='jishu' style='width: 120px;text-align: right;'>" + formatNumber(val.jishu,2,1) + "</td>"
                                     + "<td class='zhuanrang' style='width: 120px;text-align: right;'>" + formatNumber(val.zhuanrang,2,1) + "</td>"
                                     + "<td class='ziliao' style='width: 100px;text-align: right;'>" + formatNumber(val.ziliao,2,1) + "</td>"
                                     + "<td class='chailv' style='width: 120px;text-align: right;'>" + formatNumber(val.chailv,2,1) + "</td>"
                                     + "<td class='bangong' style='width: 120px;text-align: right;'>" + formatNumber(val.bangong,2,1) + "</td>"
                                     + "<td class='xiuli' style='width: 120px;text-align: right;'>" + formatNumber(val.xiuli,2,1) + "</td>"
                                     + "<td class='qita2' style='text-align: right;'>" + formatNumber(val.qita,2,1) + "</td>"
                                         + "</tr>";
                             });
                             item = item + 20;
                             $("#t_r_content table").append(str);
                             trBg($("#t_r_content table"));
                             $(".Mask").hide();
//                             $(".sureBtns").click();
                             b = true;
                         },
                         error: function () {
                             alert("数据请求失败1111!");
                             $(".Mask").hide();
                         }
                     })
                 }
             }
         });



        $.ajax({
            url: ctx + "/crrc/reportFS22/resultSum01",
            data: reportJson,
            dataType: "json",
            contentType: "application/json;charset=utf-8",
            type: "post",
            success: function (data) {

                if ('300' === data.statusCode || '301' === data.statusCode) {
                    alert(data.message);
                    $(".Mask").hide();
                    $(".ch-kp-twentyTwo1 table").html('');
                    $(".ch-kp-twentyTwo2 table").html('');
                    return true;
                }
                if(data.length==0){
                    $("#t_r_b").text("未查询到数据");
                }else{
                    var html="<tr id='tableTr1'>"
                                +"<td style='width: 100px;'>"+data.aufnr+"</td>"
                                +"<td style='width: 280px;'>"+data.ktext+"</td>"
                                +"<td style='width: 130px;text-align: right;'>"+formatNumber(data.all_buget,2,1)+"</td>"
                                +"<td style='width: 130px;text-align: right;'>"+formatNumber(data.bef_years_cost,2,1)+"</td>"
                                +"<td style='width: 110px;text-align: right;'>"+formatNumber(data.wtjhv,2,1)+"</td>"
                                +"<td style='width: 100px;text-align: right;'>"+formatNumber(data.curr_year_cost,2,1)+"</td>"
                                +"<td style='width: 130px;text-align: right;'>"+formatNumber(data.peroid_cost,2,1)+"</td>"
                                +"<td style='width: 120px;text-align: right;'>"+formatNumber(data.cailiao,2,1)+"</td>"
                                +"<td style='width: 120px;text-align: right;'>"+formatNumber(data.ranliao,2,1)+"</td>"
                                +"<td style='width: 120px;text-align: right;'>"+formatNumber(data.dongli,2,1)+"</td>"
                                +"<td style='width: 120px;text-align: right;'>"+formatNumber(data.rengong,2,1)+"</td>"
                                +"<td style='width: 120px;text-align: right;'>"+formatNumber(data.zhejiu,2,1)+"</td>"
                                +"<td style='width: 90px;text-align: right;'>"+formatNumber(data.wuxingzichan,2,1)+"</td>"
                                +"<td style='width: 120px;text-align: right;'>"+formatNumber(data.gongju,2,1)+"</td>"
                                +"<td style='width: 120px;text-align: right;'>"+formatNumber(data.shiyan,2,1)+"</td>"
                                +"<td style='width: 240px;text-align: right;'>"+formatNumber(data.jianding,2,1)+"</td>"
                                +"<td style='width: 120px;text-align: right;'>"+formatNumber(data.zhuanli,2,1)+"</td>"
                                +"<td style='width: 120px;text-align: right;'>"+formatNumber(data.jishu,2,1)+"</td>"
                                +"<td style='width: 120px;text-align: right;'>"+formatNumber(data.zhuanrang,2,1)+"</td>"
                                +"<td style='width: 100px;text-align: right;'>"+formatNumber(data.ziliao,2,1)+"</td>"
                                +"<td style='width: 120px;text-align: right;'>"+formatNumber(data.chailv,2,1)+"</td>"
                                +"<td style='width: 120px;text-align: right;'>"+formatNumber(data.bangong,2,1)+"</td>"
                                +"<td style='width: 120px;text-align: right;'>"+formatNumber(data.xiuli,2,1)+"</td>"
                                +"<td style='text-align: right;padding-right:20px;'>"+formatNumber(data.qita,2,1)+"</td>"
                        +"</tr>";

                }
                $("#t_r_b table").html(html);
            },
            error: function () {
                alert("合计1数据请求失败");
                $(".Mask").hide();
            }
        })
         
        //点击二级明细按钮二级明细表出来，一级明细表消失；点击一级明细按钮一级明细表出来，二级明细表消失


    }
    var flag=true,nums;
    $("#btnChange").off("click").on("click",function(){
        if($(this).val()=="二级明细"){
            $(this).val("一级明细");
            $(".ch-kp-twentyTwo1").hide();

            $(".import").show();
          //二级明细表
            var array1=[],array2=[];
            if ($(".gongsi").val() != "") {
                array1 = $(".gongsi").val().split(",");//公司代码
            }
            if ($(".internalOrder").val() != "") {
                array2 = $(".internalOrder").val().split(",");//内部订单
            }
            var txt1 = $("#startTime").val().replace(/-/g, "");//年月
            var txt2 = $("#endTime").val().replace(/-/g, "");
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
                        "orderID": array2, //内部订单
                        "dateYearMonthStart": txt1,
                        "dateYearMonthEnd": txt2, //入库日期
                        "startitem": item,
                        "pageitem": 20
                    }
                    reportJson = JSON.stringify(reportJson);
                $(".export").off("click").on("click", function () {
                    reportJson = {
                        compValues: array1.join(','), //公司代码
                        orderID:array2.join(','),//内部订单
                        startTime: txt1,
                        endTime: txt2, //入库日期
                        exportflag:2,
                        isExport: true
                    };
                    downloadFile(reportJson, ctx + "/crrc/reportFS22/downloadFile");
                });
                if(flag){
                	$.ajax({
    	                url: ctx + "/crrc/reportFS22/reportDetail",
    	                data: reportJson,
    	                dataType: "json",
    	                contentType: "application/json;charset=utf-8",
    	                type: "post",
    	                success: function (data) {
    	                    if ('300' === data.statusCode || '301' === data.statusCode) {
    	                        alert(data.message);
    	                        $(".Mask").hide();
    	                        $(".ch-kp-twentyTwo1 table").html('');
    	                        $(".ch-kp-twentyTwo2 table").html('');
    	                        return true;
    	                    }
    	                    if (data.costDetail.length == 0) {
    	                        $(".notSearch").next("div").hide();
    	                        $(".notSearch").text("未查询到数据");
    	                    } else {
    	                        $(".notSearch").next("div").show();
    	                        $(".dataTime1").text(getTime1(txt1, txt2));
    	                        if (data.costDetail.length < 20) {
    	                            $("#show2").html("数据加载完成");
    	                        } else {
    	                            $("#show2").html("");
    	                        }
    	                        var str = "";
    	                        $.each(data.costDetail, function (key, val) {
    	                       	 var nums=parseFloat(arrNum[key])-val[5000769900]-val[5000650000]-val[5000330000]-val[5000160212]-val[5000160700];
    	                            str += "<tr>"
    	                                    + "<td class='aufnr' style='width: 100px;'>" + val.aufnr + "</td>"
    	                                    + "<td class='ktext' style='width: 280px'>" + val.ktext + "</td>"
    	                                    + "<td class='all_buget' style='width: 130px;text-align: right;'>" + formatNumber(val.all_buget,2,1) + "</td>"
    	                                    + "<td class='bef_years_cost' style='width: 130px;text-align: right;'>" + formatNumber(val.bef_years_cost,2,1) + "</td>"
    	                                    + "<td class='wtjhv' style='width: 130px;text-align: right;'>" + formatNumber(val.wtjhv,2,1) + "</td>"
    	                                    + "<td class='curr_year_cost' style='width: 100px;text-align: right;'>" + formatNumber( val.curr_year_cost,2,1) + "</td>"
    	                                    + "<td class='peroid_cost' style='width: 100px;text-align: right;'>" + formatNumber( val.peroid_cost,2,1) + "</td>"
    	                                    + "<td style='width: 100px;text-align: right;'>" + formatNumber( val[5000010100],2,1) + "</td>"
                                            + "<td style='width: 100px;text-align: right;'>" + formatNumber( val[5000020100],2,1) + "</td>"
                                            + "<td style='width: 100px;text-align: right;'>" + formatNumber( val[5000070100],2,1) + "</td>"
                                            + "<td style='width: 100px;text-align: right;'>" + formatNumber( val[5000930000],2,1) + "</td>"
                                            + "<td style='width: 120px;text-align: right;'>" + formatNumber( val[5000100000],2,1) + "</td>"
                                            + "<td style='width: 100px;text-align: right;'>" + formatNumber( val[5000060100],2,1) + "</td>"
                                            + "<td style='width: 100px;text-align: right;'>" + formatNumber( val[5000050100],2,1) + "</td>"
                                            + "<td style='width: 100px;text-align: right;'>" + formatNumber( val[5000130000],2,1) + "</td>"
                                            + "<td style='width: 100px;text-align: right;'>" + formatNumber( val[5000870000],2,1) + "</td>"
                                            + "<td style='width: 100px;text-align: right;'>" + formatNumber( val[5000120000],2,1) + "</td>"//材料费
                                            + "<td style='width: 120px;text-align: right;'>" + formatNumber( val[5000040100],2,1) + "</td>"//燃料费
                                            + "<td style='width: 90px;text-align: right;'>" + formatNumber( val[8200000005],2,1) + "</td>"//研发费用分摊
                                            + "<td style='width: 90px;text-align: right;'>" + formatNumber( val[8000000001],2,1) + "</td>"
                                            + "<td style='width: 90px;text-align: right;'>" + formatNumber( val[8000000002],2,1) + "</td>"
                                            + "<td style='width: 110px;text-align: right;'>" + formatNumber( val[8000000004],2,1) + "</td>"
                                            + "<td style='width: 110px;text-align: right;'>" + formatNumber( val[8000000005],2,1) + "</td>"
                                            + "<td style='width: 100px;text-align: right;'>" + formatNumber( val[8000000003],2,1) + "</td>"//动力费用
                                            + "<td style='width: 110px;text-align: right;'>" + formatNumber( val[5000760400],2,1) + "</td>"//人工工资
                                            + "<td style='width: 120px;text-align: right;'>" + formatNumber( val[8200000001],2,1) + "</td>"
                                            + "<td style='width: 100px;text-align: right;'>" + formatNumber( val[8200000003],2,1) + "</td>"
                                            + "<td style='width: 100px;text-align: right;'>" + formatNumber( val[8200000002],2,1) + "</td>"//折旧费
                                            + "<td style='width: 120px;text-align: right;'>" + formatNumber( val[8200000006],2,1) + "</td>"
                                            + "<td style='width: 140px;text-align: right;'>" + formatNumber( val[8200000004],2,1) + "</td>"//无形资产推销
                                            + "<td style='width: 100px;text-align: right;'>" + formatNumber( val[8900000001],2,1) + "</td>"
                                            + "<td style='width: 100px;text-align: right;'>" + formatNumber( val[5000290000],2,1) + "</td>"
                                            + "<td style='width: 100px;text-align: right;'>" + formatNumber( val[5000300000],2,1) + "</td>"//工卡模具费
                                            + "<td style='width: 100px;text-align: right;'>" + formatNumber( val[5000280000],2,1) + "</td>"//试验检验费
                                            + "<td style='width: 140px;text-align: right;'>" + formatNumber( val[5000760500],2,1) + "</td>"//研发成果论证、鉴定、评审、验收费用
                                            + "<td style='width: 100px;text-align: right;'>" + formatNumber( val[5000761200],2,1) + "</td>"//专利费
                                            + "<td style='width: 120px;text-align: right;'>" + formatNumber( val[5000760300],2,1) + "</td>"//技术开发费
                                            + "<td style='width: 120px;text-align: right;'>" + formatNumber( val[5000760100],2,1) + "</td>"//技术转让费
                                            + "<td style='width: 120px;text-align: right;'>" + formatNumber( val[5000760800],2,1) + "</td>"
                                            + "<td style='width: 120px;text-align: right;'>" + formatNumber( val[5000761000],2,1) + "</td>"//技术资料费用
                                            + "<td style='width: 100px;text-align: right;'>" + formatNumber( val[5000761100],2,1) + "</td>"
                                            + "<td style='width: 100px;text-align: right;'>" + formatNumber( val[5000250100],2,1) + "</td>"//差旅费
                                            + "<td style='width: 110px;text-align: right;'>" + formatNumber( val[5000240100],2,1) + "</td>"
                                            + "<td style='width: 100px;text-align: right;'>" + formatNumber( val[5000240200],2,1) + "</td>"
                                            + "<td style='width: 110px;text-align: right;'>" + formatNumber( val[5000240900],2,1) + "</td>"
                                            + "<td style='width: 100px;text-align: right;'>" + formatNumber( val[5000241000],2,1) + "</td>"
                                            + "<td style='width: 120px;text-align: right;'>" + formatNumber( val[5000240500],2,1) + "</td>"
                                            + "<td style='width: 120px;text-align: right;'>" + formatNumber( val[5000241100],2,1) + "</td>"
                                            + "<td style='width: 110px;text-align: right;'>" + formatNumber( val[5000240400],2,1) + "</td>"
                                            + "<td style='width: 100px;text-align: right;'>" + formatNumber( val[5000241200],2,1) + "</td>"
                                            + "<td style='width: 110px;text-align: right;'>" + formatNumber( val[5000240300],2,1) + "</td>"
                                            + "<td style='width: 100px;text-align: right;'>" + formatNumber( val[5000241300],2,1) + "</td>"//办公费
                                            + "<td style='width: 120px;text-align: right;'>" + formatNumber( val[5000180900],2,1) + "</td>"
                                            + "<td style='width: 110px;text-align: right;'>" + formatNumber( val[5000180700],2,1) + "</td>"
                                            + "<td style='width: 130px;text-align: right;'>" + formatNumber( val[5000180701],2,1) + "</td>"
                                            + "<td style='width: 110px;text-align: right;'>" + formatNumber( val[5000180400],2,1) + "</td>"
                                            + "<td style='width: 110px;text-align: right;'>" + formatNumber( val[5000180100],2,1) + "</td>"
                                            + "<td style='width: 110px;text-align: right;'>" + formatNumber( val[5000180200],2,1) + "</td>"
                                            + "<td style='width: 100px;text-align: right;'>" + formatNumber( val[5000180600],2,1) + "</td>"
                                            + "<td style='width: 100px;text-align: right;'>" + formatNumber( val[5000030100],2,1) + "</td>"
                                            + "<td style='width: 100px;text-align: right;'>" + formatNumber( val[5000189900],2,1) + "</td>"//修理费
                                            + "<td style='width: 120px;text-align: right;'>" + formatNumber( val[5000769900],2,1) + "</td>"
                                            + "<td style='width: 100px;text-align: right;'>" + formatNumber( val[5000650000],2,1) + "</td>"
                                            + "<td style='width: 100px;text-align: right;'>" + formatNumber( val[5000330000],2,1) + "</td>"
                                            + "<td style='width: 120px;text-align: right;'>" + formatNumber( val[5000160212],2,1) + "</td>"
                                            + "<td style='width: 130px;text-align: right;'>" + formatNumber( val[5000160700],2,1) + "</td>"
                                            + "<td style='text-align: right;'>"+formatNumber( nums,2,1)+"</td>"
    	                                    + "</tr>";
    	                        });
    	    //
    	                        $("#t_r_content2 table").html(str);
    	                        $(".ch-kp-twentyTwo2").show();
                                $("#Mask2").show();
    	                        trBg($("#t_r_content2 table"));
    	                		}
    	                    $(".Mask").hide();
    	                    //$(".sureBtns").click();
    	                    flag=false;
    	                    
    	                },
    	                error: function () {
    	                    alert("数据请求失败22222!");
    	                    $(".Mask").hide();
    	                }
    	            })
                }else{
                	$(".Mask").hide();
                	$(".ch-kp-twentyTwo2").show();
                }
	            
	            
	            item = 20;
	            var b = true;
	            $("#t_r_content2").unbind("scroll").bind("scroll", function () {
                    toTop($(".toTop1"),$("#t_r_content2"));
	                var fold = $("#t_r_content2").height() + $("#t_r_content2")[0].scrollTop;
	                if (b && fold >= $("#t_r_content2")[0].scrollHeight) {
	                    b = false;
	                    if($("#show2").html()=="数据加载完成"){
	    					$(".Mask").hide();
	    				}else{
	    					$(".Mask").show();
	    				}
	                    reportJson = {
	                            "compCodeValue": array1, //公司代码
	                            "orderID": array2, //内部订单
	                            "dateYearMonthStart": txt1,
	                            "dateYearMonthEnd": txt2, //入库日期
	                            "startitem": item,
	                            "pageitem": 20
	                        }
	                    reportJson = JSON.stringify(reportJson);
	                    if ($("#show2").text() != "数据加载完成") {
	                        $.ajax({
	                            url: ctx + "/crrc/reportFS22/reportDetail",
	                            data: reportJson,
	                            dataType: "json",
	                            contentType: "application/json;charset=utf-8",
	                            type: "post",
	                            success: function (data) {
	                                if (data.costDetail.length == 0) {
	                                    $("#show2").html("数据加载完成");
	                                    b = false;
	                                }
	                                var str = "";
	                                $.each(data.costDetail, function (key, val) {
                                        var nums=parseFloat(arrNum[key])-val[5000769900]-val[5000650000]-val[5000330000]-val[5000160212]-val[5000160700];
	                                    str += "<tr>"
                                                    + "<td class='aufnr' style='width: 100px;'>" + val.aufnr + "</td>"
                                                    + "<td class='ktext' style='width: 280px'>" + val.ktext + "</td>"
                                                    + "<td class='all_buget' style='width: 130px;text-align: right;'>" + formatNumber(val.all_buget,2,1) + "</td>"
                                                    + "<td class='bef_years_cost' style='width: 130px;text-align: right;'>" + formatNumber(val.bef_years_cost,2,1) + "</td>"
                                                    + "<td class='wtjhv' style='width: 130px;text-align: right;'>" + formatNumber(val.wtjhv,2,1) + "</td>"
                                                    + "<td class='curr_year_cost' style='width: 100px;text-align: right;'>" + formatNumber( val.curr_year_cost,2,1) + "</td>"
                                                    + "<td class='peroid_cost' style='width: 100px;text-align: right;'>" + formatNumber( val.peroid_cost,2,1) + "</td>"
                                                    + "<td style='width: 100px;text-align: right;'>" + formatNumber( val[5000010100],2,1) + "</td>"
                                                    + "<td style='width: 100px;text-align: right;'>" + formatNumber( val[5000020100],2,1) + "</td>"
                                                    + "<td style='width: 120px;text-align: right;'>" + formatNumber( val[5000070100],2,1) + "</td>"
                                                    + "<td style='width: 100px;text-align: right;'>" + formatNumber( val[5000930000],2,1) + "</td>"
                                                    + "<td style='width: 100px;text-align: right;'>" + formatNumber( val[5000100000],2,1) + "</td>"
                                                    + "<td style='width: 100px;text-align: right;'>" + formatNumber( val[5000060100],2,1) + "</td>"
                                                    + "<td style='width: 100px;text-align: right;'>" + formatNumber( val[5000050100],2,1) + "</td>"
                                                    + "<td style='width: 100px;text-align: right;'>" + formatNumber( val[5000130000],2,1) + "</td>"
                                                    + "<td style='width: 100px;text-align: right;'>" + formatNumber( val[5000870000],2,1) + "</td>"
                                                    + "<td style='width: 100px;text-align: right;'>" + formatNumber( val[5000120000],2,1) + "</td>"//材料费
                                                    + "<td style='width: 120px;text-align: right;'>" + formatNumber( val[5000040100],2,1) + "</td>"//燃料费
                                                    + "<td style='width: 90px;text-align: right;'>" + formatNumber( val[8200000005],2,1) + "</td>"
                                                    + "<td style='width: 90px;text-align: right;'>" + formatNumber( val[8000000001],2,1) + "</td>"
                                                    + "<td style='width: 90px;text-align: right;'>" + formatNumber( val[8000000002],2,1) + "</td>"
                                                    + "<td style='width: 110px;text-align: right;'>" + formatNumber( val[8000000004],2,1) + "</td>"
                                                    + "<td style='width: 110px;text-align: right;'>" + formatNumber( val[8000000005],2,1) + "</td>"
                                                    + "<td style='width: 100px;text-align: right;'>" + formatNumber( val[8000000003],2,1) + "</td>"//动力费用
                                                    + "<td style='width: 110px;text-align: right;'>" + formatNumber( val[5000760400],2,1) + "</td>"//人工工资
                                                    + "<td style='width: 120px;text-align: right;'>" + formatNumber( val[8200000001],2,1) + "</td>"
                                                    + "<td style='width: 100px;text-align: right;'>" + formatNumber( val[8200000003],2,1) + "</td>"
                                                    + "<td style='width: 100px;text-align: right;'>" + formatNumber( val[8200000002],2,1) + "</td>"//折旧费
                                                    + "<td style='width: 120px;text-align: right;'>" + formatNumber( val[8200000006],2,1) + "</td>"
                                                    + "<td style='width: 140px;text-align: right;'>" + formatNumber( val[8200000004],2,1) + "</td>"//无形资产推销
                                                    + "<td style='width: 100px;text-align: right;'>" + formatNumber( val[8900000001],2,1) + "</td>"
                                                    + "<td style='width: 100px;text-align: right;'>" + formatNumber( val[5000290000],2,1) + "</td>"
                                                    + "<td style='width: 100px;text-align: right;'>" + formatNumber( val[5000300000],2,1) + "</td>"//工卡模具费
                                                    + "<td style='width: 100px;text-align: right;'>" + formatNumber( val[5000280000],2,1) + "</td>"//试验检验费
                                                    + "<td style='width: 140px;text-align: right;'>" + formatNumber( val[5000760500],2,1) + "</td>"//研发成果论证、鉴定、评审、验收费用
                                                    + "<td style='width: 100px;text-align: right;'>" + formatNumber( val[5000761200],2,1) + "</td>"//专利费
                                                    + "<td style='width: 120px;text-align: right;'>" + formatNumber( val[5000760300],2,1) + "</td>"//技术开发费
                                                    + "<td style='width: 120px;text-align: right;'>" + formatNumber( val[5000760100],2,1) + "</td>"//技术转让费
                                                    + "<td style='width: 120px;text-align: right;'>" + formatNumber( val[5000760800],2,1) + "</td>"
                                                    + "<td style='width: 120px;text-align: right;'>" + formatNumber( val[5000761000],2,1) + "</td>"//技术资料费用
                                                    + "<td style='width: 100px;text-align: right;'>" + formatNumber( val[5000761100],2,1) + "</td>"
                                                    + "<td style='width: 100px;text-align: right;'>" + formatNumber( val[5000250100],2,1) + "</td>"//差旅费
                                                    + "<td style='width: 110px;text-align: right;'>" + formatNumber( val[5000240100],2,1) + "</td>"
                                                    + "<td style='width: 100px;text-align: right;'>" + formatNumber( val[5000240200],2,1) + "</td>"
                                                    + "<td style='width: 110px;text-align: right;'>" + formatNumber( val[5000240900],2,1) + "</td>"
                                                    + "<td style='width: 100px;text-align: right;'>" + formatNumber( val[5000241000],2,1) + "</td>"
                                                    + "<td style='width: 120px;text-align: right;'>" + formatNumber( val[5000240500],2,1) + "</td>"
                                                    + "<td style='width: 120px;text-align: right;'>" + formatNumber( val[5000241100],2,1) + "</td>"
                                                    + "<td style='width: 110px;text-align: right;'>" + formatNumber( val[5000240400],2,1) + "</td>"
                                                    + "<td style='width: 100px;text-align: right;'>" + formatNumber( val[5000241200],2,1) + "</td>"
                                                    + "<td style='width: 110px;text-align: right;'>" + formatNumber( val[5000240300],2,1) + "</td>"
                                                    + "<td style='width: 100px;text-align: right;'>" + formatNumber( val[5000241300],2,1) + "</td>"//办公费
                                                    + "<td style='width: 120px;text-align: right;'>" + formatNumber( val[5000180900],2,1) + "</td>"
                                                    + "<td style='width: 110px;text-align: right;'>" + formatNumber( val[5000180700],2,1) + "</td>"
                                                    + "<td style='width: 130px;text-align: right;'>" + formatNumber( val[5000180701],2,1) + "</td>"
                                                    + "<td style='width: 110px;text-align: right;'>" + formatNumber( val[5000180400],2,1) + "</td>"
                                                    + "<td style='width: 110px;text-align: right;'>" + formatNumber( val[5000180100],2,1) + "</td>"
                                                    + "<td style='width: 110px;text-align: right;'>" + formatNumber( val[5000180200],2,1) + "</td>"
                                                    + "<td style='width: 100px;text-align: right;'>" + formatNumber( val[5000180600],2,1) + "</td>"
                                                    + "<td style='width: 100px;text-align: right;'>" + formatNumber( val[5000030100],2,1) + "</td>"
                                                    + "<td style='width: 100px;text-align: right;'>" + formatNumber( val[5000189900],2,1) + "</td>"//修理费
                                                    + "<td style='width: 120px;text-align: right;'>" + formatNumber( val[5000769900],2,1) + "</td>"
                                                    + "<td style='width: 100px;text-align: right;'>" + formatNumber( val[5000650000],2,1) + "</td>"
                                                    + "<td style='width: 100px;text-align: right;'>" + formatNumber( val[5000330000],2,1) + "</td>"
                                                    + "<td style='width: 120px;text-align: right;'>" + formatNumber( val[5000160212],2,1) + "</td>"
                                                    + "<td style='width: 130px;text-align: right;'>" + formatNumber( val[5000160700],2,1) + "</td>"
                                                    + "<td style='text-align: right;'>"+formatNumber( nums,2,1)+"</td>"
	                                            + "</tr>";
	                                });
	                                item = item + 20;
	                                $("#t_r_content2 table").append(str);
	                                $(".ch-kp-twentyTwo2").show();
	                                trBg($("#t_r_content2 table"));
	                                $(".Mask").hide();
	//                                $(".sureBtns").click();
	                                b = true;
	                            },
	                            error: function () {
	                                alert("数据请求失败22222!");
	                                $(".Mask").hide();
	                            }
	                        })
	                    }
	                }
	            });

                $.ajax({
                    url: ctx + "/crrc/reportFS22/resultSum02",
                    data: reportJson,
                    dataType: "json",
                    contentType: "application/json;charset=utf-8",
                    type: "post",
                    success: function (data) {
                        if ('300' === data.statusCode || '301' === data.statusCode) {
                            alert(data.message);
                            $(".Mask2").hide();
                            $(".ch-kp-twentyTwo1 table").html('');
                            $(".ch-kp-twentyTwo2 table").html('');
                            return true;
                        }
                        if (data.length == 0) {
                            $("#t_r_b2").text("未查询到数据");
                        } else {
                            var html = "";
                            html += "<tr id='tableTr2'>"
                                        + "<td style='width: 100px;'>合计</td>"
                                        + "<td style='width: 280px'>-</td>"
                                        + "<td style='width: 130px;text-align: right;'>" + formatNumber(data[2],2,1) + "</td>"
                                        + "<td style='width: 130px;text-align: right;'>" + formatNumber(data[3],2,1) + "</td>"
                                        + "<td style='width: 130px;text-align: right;'>" + formatNumber(data[4],2,1) + "</td>"
                                        + "<td style='width: 100px;text-align: right;'>" + formatNumber( data[5],2,1) + "</td>"
                                        + "<td style='width: 100px;text-align: right;'>" + formatNumber( data[6],2,1) + "</td>"
                                        + "<td style='width: 100px;text-align: right;'>" + formatNumber( data[7],2,1) + "</td>"
                                        + "<td style='width: 100px;text-align: right;'>" + formatNumber( data[8],2,1) + "</td>"
                                        + "<td style='width: 120px;text-align: right;'>" + formatNumber( data[9],2,1) + "</td>"
                                        + "<td style='width: 100px;text-align: right;'>" + formatNumber( data[10],2,1) + "</td>"
                                        + "<td style='width: 100px;text-align: right;'>" + formatNumber( data[11],2,1) + "</td>"
                                        + "<td style='width: 100px;text-align: right;'>" + formatNumber( data[12],2,1) + "</td>"
                                        + "<td style='width: 100px;text-align: right;'>" + formatNumber( data[13],2,1) + "</td>"
                                        + "<td style='width: 100px;text-align: right;'>" + formatNumber( data[14],2,1) + "</td>"
                                        + "<td style='width: 100px;text-align: right;'>" + formatNumber( data[15],2,1) + "</td>"
                                        + "<td style='width: 100px;text-align: right;'>" + formatNumber( data[16],2,1) + "</td>"//材料费
                                        + "<td style='width: 120px;text-align: right;'>" + formatNumber( data[17],2,1) + "</td>"//燃料费
                                        + "<td style='width: 90px;text-align: right;'>" + formatNumber( data[18],2,1) + "</td>"//研发费用分摊
                                        + "<td style='width: 90px;text-align: right;'>" + formatNumber( data[19],2,1) + "</td>"
                                        + "<td style='width: 90px;text-align: right;'>" + formatNumber( data[20],2,1) + "</td>"
                                        + "<td style='width: 110px;text-align: right;'>" + formatNumber( data[21],2,1) + "</td>"
                                        + "<td style='width: 110px;text-align: right;'>" + formatNumber( data[22],2,1) + "</td>"
                                        + "<td style='width: 100px;text-align: right;'>" + formatNumber( data[23],2,1) + "</td>"//动力费用
                                        + "<td style='width: 110px;text-align: right;'>" + formatNumber( data[24],2,1) + "</td>"//人工工资
                                        + "<td style='width: 120px;text-align: right;'>" + formatNumber( data[25],2,1) + "</td>"
                                        + "<td style='width: 100px;text-align: right;'>" + formatNumber( data[26],2,1) + "</td>"
                                        + "<td style='width: 100px;text-align: right;'>" + formatNumber( data[27],2,1) + "</td>"//折旧费
                                        + "<td style='width: 120px;text-align: right;'>" + formatNumber( data[28],2,1) + "</td>"
                                        + "<td style='width: 140px;text-align: right;'>" + formatNumber( data[29],2,1) + "</td>"//无形资产推销
                                        + "<td style='width: 100px;text-align: right;'>" + formatNumber( data[30],2,1) + "</td>"
                                        + "<td style='width: 100px;text-align: right;'>" + formatNumber( data[31],2,1) + "</td>"
                                        + "<td style='width: 100px;text-align: right;'>" + formatNumber( data[32],2,1) + "</td>"//工卡模具费
                                        + "<td style='width: 100px;text-align: right;'>" + formatNumber( data[33],2,1) + "</td>"//试验检验费
                                        + "<td style='width: 140px;text-align: right;'>" + formatNumber( data[34],2,1) + "</td>"//研发成果论证、鉴定、评审、验收费用
                                        + "<td style='width: 100px;text-align: right;'>" + formatNumber( data[35],2,1) + "</td>"//专利费
                                        + "<td style='width: 120px;text-align: right;'>" + formatNumber( data[36],2,1) + "</td>"//技术开发费
                                        + "<td style='width: 120px;text-align: right;'>" + formatNumber( data[37],2,1) + "</td>"//技术转让费
                                        + "<td style='width: 120px;text-align: right;'>" + formatNumber( data[38],2,1) + "</td>"
                                        + "<td style='width: 120px;text-align: right;'>" + formatNumber( data[39],2,1) + "</td>"//技术资料费用
                                        + "<td style='width: 100px;text-align: right;'>" + formatNumber( data[40],2,1) + "</td>"
                                        + "<td style='width: 100px;text-align: right;'>" + formatNumber( data[41],2,1) + "</td>"//差旅费
                                        + "<td style='width: 110px;text-align: right;'>" + formatNumber( data[42],2,1) + "</td>"
                                        + "<td style='width: 100px;text-align: right;'>" + formatNumber( data[43],2,1) + "</td>"
                                        + "<td style='width: 110px;text-align: right;'>" + formatNumber( data[44],2,1) + "</td>"
                                        + "<td style='width: 100px;text-align: right;'>" + formatNumber( data[45],2,1) + "</td>"
                                        + "<td style='width: 120px;text-align: right;'>" + formatNumber( data[46],2,1) + "</td>"
                                        + "<td style='width: 120px;text-align: right;'>" + formatNumber( data[47],2,1) + "</td>"
                                        + "<td style='width: 110px;text-align: right;'>" + formatNumber( data[48],2,1) + "</td>"
                                        + "<td style='width: 100px;text-align: right;'>" + formatNumber( data[49],2,1) + "</td>"
                                        + "<td style='width: 110px;text-align: right;'>" + formatNumber( data[50],2,1) + "</td>"
                                        + "<td style='width: 100px;text-align: right;'>" + formatNumber( data[51],2,1) + "</td>"//办公费
                                        + "<td style='width: 120px;text-align: right;'>" + formatNumber( data[52],2,1) + "</td>"
                                        + "<td style='width: 110px;text-align: right;'>" + formatNumber( data[53],2,1) + "</td>"
                                        + "<td style='width: 130px;text-align: right;'>" + formatNumber( data[54],2,1) + "</td>"
                                        + "<td style='width: 110px;text-align: right;'>" + formatNumber( data[55],2,1) + "</td>"
                                        + "<td style='width: 110px;text-align: right;'>" + formatNumber( data[56],2,1) + "</td>"
                                        + "<td style='width: 110px;text-align: right;'>" + formatNumber( data[57],2,1) + "</td>"
                                        + "<td style='width: 100px;text-align: right;'>" + formatNumber( data[58],2,1) + "</td>"
                                        + "<td style='width: 100px;text-align: right;'>" + formatNumber( data[59],2,1) + "</td>"
                                        + "<td style='width: 100px;text-align: right;'>" + formatNumber( data[60],2,1) + "</td>"//修理费
                                        + "<td style='width: 120px;text-align: right;'>" + formatNumber( data[61],2,1) + "</td>"
                                        + "<td style='width: 100px;text-align: right;'>" + formatNumber( data[62],2,1) + "</td>"
                                        + "<td style='width: 100px;text-align: right;'>" + formatNumber( data[63],2,1) + "</td>"
                                        + "<td style='width: 120px;text-align: right;'>" + formatNumber( data[64],2,1) + "</td>"
                                        + "<td style='width: 130px;text-align: right;'>" + formatNumber( data[65],2,1) + "</td>"
                                        + "<td style='text-align: right;padding-right:20px;'>"+formatNumber( data[66],2,1) +"</td>"
                                + "</tr>";
                            $("#t_r_b2 table").html(html);
                            $("#Mask2").hide();
                        }
                    },
                    error: function () {
                        alert("合计2数据请求失败");
                        $("#Mask2").hide();
                    }
                })
            }
            
        }else if($(this).val()=="一级明细"){
            $(this).val("二级明细");
            $(".import").hide();
            $(".ch-kp-twentyTwo2").hide();
            $(".Mask").hide();
            $(".ch-kp-twentyTwo1").show();
        }
    })

    
    
    
//    $(".hideCol").off("click").on("click", function () {
//        var _this = $(this);
//        //$("#Mask").show();
//        if (!$(this).hasClass("bg")) {
//            $(this).addClass("bg");
//            var txt = $(this).attr("src");
//            $(this).attr("src", txt.replace("hide.png", "hidered.png"));
//            $(".hideColBox").show();
//            $("#Mask").show();
//            //allCheck($(".allCheck"));
//
//            //获取表头名称
//			var thArr=[]; 
//			for(var i=0;i<$(".t_r_title table tr").find("th").length;i++){
//				thArr.push($(".t_r_title table tr").find("th").eq(i).text())
//			}
//			//循环表头将名称添加到弹出框中
//			var str1="";
//			for(var j=0;j<thArr.length;j++){
//				str1+="<tr>"
//					+"<td style='width:50px; text-align: center;'><input type='checkbox' checked='checked' class='checks'></td>"
//					+"<td>"+thArr[j]+"</td>"
//				+"</tr>";
//			}
//			$(".hideTable .t_r_content table").html(str1);
//			trBg($(".hideTable .t_r_content table"));
//            //再次点击弹出框之前未被选中的仍未被选中
//            //弹出框里的文本
//            var arrTk = [];
//            for (var i = 0; i < $(".hideTable .t_r_content tr").length; i++) {
//                arrTk.push($(".hideTable .t_r_content tr").eq(i).find("td").text());
//            }
//
//
//            var val = "";
//            $(".hideTable .t_r_content").on("click", ".checks", function () {
//                if ($(this).prop("checked") == false) {
//                    val = $(this).parent().next("td").text();
//                    arr.push(val)
//                } else {
//                    val = $(this).parent().next("td").text();
//                    $.each(arr, function (k, v) {
//                        if (v == val) {
//                        	arr.splice(k, 1)
//                        }
//                    })
//                }
//            })
//            for (var i = 0; i < arrTk.length; i++) {
//                for (var j = 0; j < arr.length; j++) {
//                    if ($(".hideTable .t_r_content tr:eq(" + i + ") td:eq(1)").text() == arr[j]) {
//                        $(".hideTable .t_r_content tr:eq(" + i + ") td:eq(0)").find("input").prop("checked", false)
//                    }
//                }
//
//
//            }
//
// 
//            //所有的复选框默认的都是选中状态,点击一个复选框，全选的复选框不再被选中
//            $(".hideTable").on("click", ".checks", function () {
//                var inputLength = $(".checks").length;
//                var incheckedLength = $(".checks[type='checkbox']:checked").length;
//                if (incheckedLength < inputLength) {
//                    $(".allCheck").prop("checked", false)
//                } else {
//                    $(".allCheck").prop("checked", true)
//                }
//            })
//            $(".allCheck").on("click", function () {
//                if ($(this).prop("checked")) {
//                    $(this).parents(".t_r_t").next().find("table input").prop("checked", true);
//	                arr=[];
//                }else{
//                	$(this).parents(".t_r_t").next().find("table input").prop("checked", false);
//                	for(var i=0;i<$(this).parents(".t_r_t").next().find("table input").length;i++){
//	            		var t=$(this).parents(".t_r_t").next().find("table input").eq(i).parent().next().text();
//		                arr.push(t)
//	            	}
//                }
//            })
//
//
//            //点击取消和x弹出框消失，点击确定相应的列消失
//            can($(".closeBtn"));
//            can($(".canBtns"));
//            $(".sureBtns").off("click").on("click", function () {
//                //弹出框里的不被选中的已经放倒一个数组里，判断表头有这个内容就将此隐藏
//                //表头数组
//                var arrBig = [];
//                for (var j = 0; j < $(".t_r_title table tr th").length; j++) {
//                    var thBig = $(".t_r_title table tr th").eq(j).text();
//                    arrBig.push(thBig)
//                }
//                //被选中的text的字段
//                var textArr = [];
//                for (var n = 0; n < arrBig.length; n++) {
//                    if ($(".hideTable .t_r_content tr").eq(n).find("input[type='checkbox']").prop("checked")) {
//                        var checkTrue = $(".hideTable .t_r_content tr").eq(n).find("input[type='checkbox']").parent("td").next().text();
//                        textArr.push(checkTrue);
//                    }
//                }
//                //被选中的class名
//                var arrTrue = [];
//                for (var n = 0; n < textArr.length; n++) {
//                    if ($.inArray(textArr[n], arrBig) >= 0) {
//                        var className = $(".t_r_title table tr th").eq($.inArray(textArr[n], arrBig)).attr("class");
//                        arrTrue.push(className);
//                    }
//                }
//                for (var m = 0; m < arrTrue.length; m++) {
//                    $(".t_r").find("." + arrTrue[m]).show();
//                    $("#t_r_content").find("." + arrTrue[m]).show();
//                }
//
//                //不被选中的class名
//                var arrN = [];
//                for (var n = 0; n < arr.length; n++) {
//                    if ($.inArray(arr[n], arrBig) >= 0) {
//                        var className = $(".t_r_title table tr th").eq($.inArray(arr[n], arrBig)).attr("class");
//                        arrN.push(className);
//                    }
//                }
//                //不被选中的class消失
//                for (var m = 0; m < arrN.length; m++) {
//                    $(".t_r").find("." + arrN[m]).hide();
//                    $("#t_r_content").find("." + arrN[m]).hide();
//                }
//
//                $(".hideColBox").hide();
//                var txt = _this.attr("src");
//                _this.removeClass("bg");
//                _this.attr("src", txt.replace("hidered.png", "hide.png"));
//                $("#Mask").hide();
//            })
//        } else {
//            $(this).removeClass("bg");
//            var txt = $(this).attr("src");
//            $(this).attr("src", txt.replace("hidered.png", "hide.png"));
//            $(".hideColBox").hide();
//            $("#Mask").hide();
//        }
//    })
})

//function can(btn) {
//    btn.off("click").on("click", function () {
//        if ($(".hideColBox").css("display") == "block") {
//            $(".hideCol").removeClass("bg");
//            $(".hideColBox").hide();
//            $("#Mask").hide();
//            $(".hideCol").attr("src", $(".hideCol").attr("src").replace("hidered.png", "hide.png"));
//        } else if ($(".hideColBox").css("display") == "none") {
//            $(".hideCol").addClass("bg");
//            $(".hideColBox").show();
//            $(".hideCol").attr("src", $(".hideCol").attr("src").replace("hide.png", "hidered.png"));
//            $("#Mask").show();
//        }
//
//    })
//}

//Array.prototype.unique3 = function () {
//    var res = [];
//    var json = {};
//    for (var i = 0; i < this.length; i++) {
//        if (!json[this[i]]) {
//            res.push(this[i]);
//            json[this[i]] = 1;
//        }
//    }
//    return res;
//}

//合计部分左右变化
//$(".hj .lr").on("click",function(){
//	//var widthHJ=$(this).parent().width();
//	if($(this).text()=="<<"){
//		$(this).text(">>");
//		$(this).parent().animate({width:'20px'})
//		$(this).prev().css("display","none");
//	}else if($(this).text()==">>"){
//		$(this).text("<<");
//		$(this).parent().animate({width:'84.5%'})
//		$(this).prev().css("display","block");
//	}
//})