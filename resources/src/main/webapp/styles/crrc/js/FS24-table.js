//点击查询按钮，弹出查询框
var search1=new multiSearch2($(".btn1"),"reportFS24/tcompcode",null,null);//公司代码
//控制表格高度的显示
var heights = $(window).height();
$(".ch-kp-twentyFour1 .t_r_content").height((heights - 210) + "px");
$(".ch-kp-twentyFour2 .t_r_content").height((heights - 210) + "px");
//点击收起按钮
$(".retract").off("click").on("click", function () {
    if ($(".container form").css("display") == "block") {
        $(".container form").slideUp();
        $(this).text("展开  ∨");
        var heights = $(window).height();
        $(".ch-kp-twentyFour1 .t_r_content").animate({
            "height": (heights - 170) + "px"
        });
        $(".ch-kp-twentyFour2 .t_r_content").animate({
            "height": (heights - 170) + "px"
        });
    } else {
        $(".container form").slideDown();
        $(this).text("收起  ∧");
        var heights = $(window).height();
        $(".ch-kp-twentyFour1 .t_r_content").height((heights - 210) + ("px"));
        $(".ch-kp-twentyFour2 .t_r_content").height((heights - 210) + ("px"));
    }
})
//采购入库的表格
$("#submit").off("click").on("click", function () {
	var blog=true;
    $(".notSearch").empty();
    $("#show").text("数据正在加载中");
    $("#btnChange").val("二级明细");
    $(".ch-kp-twentyFour2").hide();
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
         reportJson = {
            "compCodeValue": array1, //公司代码
            "dateYearMonthStart": txt1,
            "dateYearMonthEnd": txt2,//入库日期
             isExport: false
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
            downloadFile(reportJson, ctx + "/crrc/reportFS24/downloadFile");
        });
         $.ajax({
             url: ctx + "/crrc/reportFS24/result01",
             data: reportJson,
             dataType: "json",
             contentType: "application/json;charset=utf-8",
             type: "post",
             success: function (data) {
                 if ('300' === data.statusCode || '301' === data.statusCode) {
                     alert(data.message);
                     $(".Mask").hide();
                     $(".ch-kp-twentyFour1 table").html('');
                     $(".ch-kp-twentyFour2 table").html('');
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
		                             + "<td class='txtmd' style='width:220px;'>" + val.txtmd+ "</td>"
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
                 $(".ch-kp-twentyFour1").show();
                 $(".Mask").hide();
                 //$(".sureBtns").click();
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
            $(".ch-kp-twentyFour1").hide();
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
            } else if (parseInt(txt1) > parseInt(txt2)) {
                alert("后一个日期必须大于或等于前一个日期");
            } else {
                $(".Mask").show();
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
                    downloadFile(reportJson, ctx + "/crrc/reportFS24/downloadFile");
                });
                if(flag){
                	$.ajax({
    	                url: ctx + "/crrc/reportFS24/report02",
    	                data: reportJson,
    	                dataType: "json",
    	                contentType: "application/json;charset=utf-8",
    	                type: "post",
    	                success: function (data) {
    	                    if ('300' === data.statusCode || '301' === data.statusCode) {
    	                        alert(data.message);
    	                        $(".Mask").hide();
    	                        $(".ch-kp-twentyFour1 table").html('');
    	                        $(".ch-kp-twentyFour2 table").html('');
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
    	    //
    	                        $("#t_r_content2 table").html(str);
    	                        $(".ch-kp-twentyFour2").show();
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
                	$(".ch-kp-twentyFour2").show();
                }
	            
	            
            }  
            
        }else if($(this).val()=="一级明细"){
            $(this).val("二级明细");
            $(".import").hide();
            $(".ch-kp-twentyFour2").hide();
            $(".Mask").hide();
            $(".ch-kp-twentyFour1").show();
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