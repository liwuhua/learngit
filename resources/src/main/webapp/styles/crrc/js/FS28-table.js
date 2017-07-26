//点击查询按钮，弹出查询框
var search1=new multiSearch2($(".btn1"),"reportFS28/tcompcode",null,null);//公司代码
//控制表格高度的显示
var heights = $(window).height();
$(".ch-kp-twentyEight1 .t_r_content").height((heights - 210) + "px");
$(".ch-kp-twentyEight2 .t_r_content").height((heights - 210) + "px");
//点击收起按钮
$(".retract").off("click").on("click", function () {
    if ($(".container form").css("display") == "block") {
        $(".container form").slideUp();
        $(this).text("展开  ∨");
        var heights = $(window).height();
        $(".ch-kp-twentyEight1 .t_r_content").animate({
            "height": (heights - 170) + "px"
        });
        $(".ch-kp-twentyEight2 .t_r_content").animate({
            "height": (heights - 170) + "px"
        });
    } else {
        $(".container form").slideDown();
        $(this).text("收起  ∧");
        var heights = $(window).height();
        $(".ch-kp-twentyEight1 .t_r_content").height((heights - 210) + ("px"));
        $(".ch-kp-twentyEight2 .t_r_content").height((heights - 210) + ("px"));
    }
})
//采购入库的表格
$("#submit").off("click").on("click", function () {
	var blog=true;
    $(".notSearch").empty();
    $("#show").text("数据正在加载中");
    $("#btnChange").val("二级明细");
    $(".ch-kp-twentyEight2").hide();
    $(".import").hide();
    var arrs = [], array1 = [], array2 = [];
    if ($(".gongsi").val() != "") {
        array1 = $(".gongsi").val().split(",");//公司代码
        if(blog){
            blog = compareInput(array1,comp_code,"公司代码");
        }
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
            "dateYearMonthStart": txt1,
            "dateYearMonthEnd": txt2//入库日期
        }
        reportJson = JSON.stringify(reportJson);
        $(".export").off("click").on("click", function () {
            reportJson = {
                compValues: array1.join(','), //公司代码
                startTime: txt1,
                endTime: txt2, //入库日期
                exportflag:1,
                isExport: true
            }
            downloadFile(reportJson, ctx + "/crrc/reportFS28/downloadFile");
        });
         $.ajax({
             url: ctx + "/crrc/reportFS28/result01",
             data: reportJson,
             dataType: "json",
             contentType: "application/json;charset=utf-8",
             type: "post",
             success: function (data) {
                 if ('300' === data.statusCode || '301' === data.statusCode) {
                     alert(data.message);
                     $(".Mask").hide();
                     $(".ch-kp-twentyEight1 table").html('');
                     $(".ch-kp-twentyEight2 table").html('');
                     return true;
                 }
                 if (data.list.length == 0) {
                     $(".notSearch").next("div").hide();
                     $(".notSearch").text("未查询到数据");
                 } else {
                     $(".notSearch").next("div").show();
                     $(".dataTime1").text(getTime1(txt1, txt2));
                     if (data.list.length < 20) {
                         $("#show1").html("数据加载完成");
                     } else {
                         $("#show1").html("");
                     }
                     var str = "";
                   //formatNumber(val.year_plan,2,1)
                     $.each(data.list, function (key, val) {
                    	 str += "<tr>"
		                             + "<td class='item_column' style='width:120px;'>" + val.item_column + "</td>"
		                             + "<td class='id' style='width:40px;text-align:center;'>" + val.id + "</td>"
		                             + "<td class='racct' style='width:100px;'>" + val.racct + "</td>"
		                             + "<td class='txtmd' style='width:230px;'>" + val.txtmd+ "</td>"
		                             + "<td class='last_year_cost' style='width:130px;text-align:right;'>" + formatNumber(val.last_year_cost,2,1) + "</td>"
		                             + "<td class='year_plan' style='width:100px;text-align:right;'>" + formatNumber(val.year_plan,2,1) + "</td>"
		                             + "<td class='period_plan' style='width:100px;text-align:right;'>" + formatNumber(val.period_plan,2,1) + "</td>"
		                             + "<td class='period_cost' style='width:100px;text-align:right;'>" + formatNumber(val.period_cost,2,1) + "</td>"
		                             + "<td class='exe_rate'>" + val.exe_rate + "</td>"
                                 + "</tr>";
                     });
                     $("#t_r_content table").html(str);
                     trBg($("#t_r_content table"));
             		}
                 $(".ch-kp-twentyEight1").show();
                 $(".Mask").hide();
                 $(".bianzhi").text(data.BZDW);
             },
             error: function () {
                 alert("数据请求失败1111!");
                 $(".Mask").hide();
             }
         })


         
        //点击二级明细按钮二级明细表出来，一级明细表消失；点击一级明细按钮一级明细表出来，二级明细表消失


    }
    var flag=true;
    $("#btnChange").off("click").on("click",function(){
        if($(this).val()=="二级明细"){
            $(this).val("一级明细");
            $(".ch-kp-twentyEight1").hide();
            $(".Mask").show();
            $(".import").show();
          //二级明细表
            var array1=[];
            if ($(".gongsi").val() != "") {
                array1 = $(".gongsi").val().split(",");//公司代码
            }
            var txt1 = $("#startTime").val().replace(/-/g, "");//年月
            var txt2 = $("#endTime").val().replace(/-/g, "");
            var reportJson={};
            if (txt1 == "" || txt2 == "") {
                alert("请输入日期");
                $(".Mask").hide();
            } else if (parseInt(txt1) > parseInt(txt2)) {
                $(".Mask").hide();
                alert("后一个日期必须大于或等于前一个日期");
            } else {
                reportJson = {
                        "compCodeValue": array1, //公司代码
                        "dateYearMonthStart": txt1,
                        "dateYearMonthEnd": txt2 //入库日期
                    }
                    reportJson = JSON.stringify(reportJson);
                $(".export").off("click").on("click", function () {
                    reportJson = {
                        compValues: array1.join(','), //公司代码
                        startTime: txt1,
                        endTime: txt2, //入库日期
                        exportflag:2,
                        isExport: true
                    }
                    downloadFile(reportJson, ctx + "/crrc/reportFS28/downloadFile");
                });
                if(flag){
                	$.ajax({
    	                url: ctx + "/crrc/reportFS28/report02",
    	                data: reportJson,
    	                dataType: "json",
    	                contentType: "application/json;charset=utf-8",
    	                type: "post",
    	                success: function (data) {
    	                    if ('300' === data.statusCode || '301' === data.statusCode) {
    	                        alert(data.message);
    	                        $(".Mask").hide();
    	                        $(".ch-kp-twentyEight1 table").html('');
    	                        $(".ch-kp-twentyEight2 table").html('');
    	                        return true;
    	                    }
    	                    if (data.list.length == 0) {
    	                        $(".notSearch").next("div").hide();
    	                        $(".notSearch").text("未查询到数据");
    	                    } else {
    	                        $(".notSearch").next("div").show();
    	                        $(".dataTime1").text(getTime1(txt1, txt2));
    	                        if (data.list.length < 20) {
    	                            $("#show2").html("数据加载完成");
    	                        } else {
    	                            $("#show2").html("");
    	                        }
    	                        var str = "";
    	                        $.each(data.list, function (key, val) {
    	                            str += "<tr>"
    			                            	 + "<td class='item_column' style='width:120px;'>" + val.item_column + "</td>"
    				                             + "<td class='id' style='width:40px;text-align:center;'>" + val.id + "</td>"
    				                             + "<td class='racct' style='width:100px;'>" + val.racct + "</td>"
    				                             + "<td class='txtmd' style='width:210px;'>" + val.txtmd+ "</td>"
    				                             + "<td class='last_year_cost' style='width:130px;text-align:right;'>" + formatNumber(val.last_year_cost,2,1) + "</td>"
    				                             + "<td class='year_plan' style='width:100px;text-align:right;'>" + formatNumber(val.year_plan,2,1) + "</td>"
    				                             + "<td class='period_plan' style='width:100px;text-align:right;'>" + formatNumber(val.period_plan,2,1) + "</td>"
    				                             + "<td class='period_cost' style='width:100px;text-align:right;'>" + formatNumber(val.period_cost,2,1) + "</td>"
    				                             + "<td class='exe_rate'>" + val.exe_rate + "</td>"
    	                                    + "</tr>";
    	                        });

    	                        $("#t_r_content2 table").html(str);
    	                        $(".ch-kp-twentyEight2").show();
    	                        trBg($("#t_r_content2 table"))
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
                	$(".ch-kp-twentyEight2").show();
                }
	            
	            
            }  
            
        }else if($(this).val()=="一级明细"){
            $(this).val("二级明细");
            $(".ch-kp-twentyEight2").hide();
            $(".Mask").hide();
            $(".import").hide();
            $(".ch-kp-twentyEight1").show();
        }
    })


})
