//点击查询按钮，弹出查询框
var search1=new multiSearch2($(".btn1"),"reportFS01/tplant",null,null);//工厂
var search2=new multiSearch($(".btn2"),"tmaterial",0,10);//物料
var search3=new multiSearch1($(".btn3"),"reportFS01/tvendor",null,null);//供应商
var search4=new multiSearch2($(".btn4"),"reportFS01/tpurgroup",null,null);//采购组
//控制表格高度的显示
var heights = $(window).height();
$(".ch-kp-one .t_r_content").height((heights - 225) + "px");
//点击收起按钮
$(".retract").off("click").on("click", function () {
    if ($(".container form").css("display") == "block") {
        $(".container form").slideUp();
        $(this).text("展开  ∨");
        var heights = $(window).height();
        $(".ch-kp-one .t_r_content").animate({
            "height": (heights - 145) + "px"
        });
    } else {
        $(".container form").slideDown();
        $(this).text("收起  ∧");
        var heights = $(window).height();
        $(".ch-kp-one .t_r_content").height((heights - 225) + ("px"));
    }
})
var arr = [], arrTk = [];
//采购入库的表格
$("#submit").off("click").on("click", function () {
    var blog = true;
    $(".notSearch").empty();
    $("#show").text("数据正在加载中");
    //$(".hideCol").click();
    var arrs = [], array1 = [], array2 = [], array3 = [], array4 = [], array5 = [];
    if ($(".factory").val() != "") {
        array1 = $(".factory").val().split(",");//工厂
        if(blog){
            blog = compareInput(array1,plant,"工厂");
        }
    }
    if ($(".material").val() != "" && $(".material").val().indexOf(";") != -1) {
        arrs = $(".material").val().split(";");
        array2 = arrs[0].split(",");//物料多值
        array5 = arrs[1].split(",");//物料多区间
    } else if ($(".material").val() != "" && $(".material").val().indexOf(";") == -1 && $(".material").val().indexOf(":") != -1) {
        array2 = [];
        array5 = $(".material").val().split(",");
    } else if ($(".material").val() != "" && $(".material").val().indexOf(";") == -1 && $(".material").val().indexOf(":") == -1) {
        array2 = $(".material").val().split(",");
        array5 = [];
    } else if ($(".material").val() == "") {
        array2 = [];
        array5 = [];
    }
    if ($(".supplier").val() != "") {
        array3 = $(".supplier").val().split(",");//供应商
    }
    if ($(".purchase").val() != "") {
        array4 = $(".purchase").val().split(",");//采购组
        array4 = regData(array4,pur_group);
        if(blog){
            blog = compareInput(array4,pur_group,"采购组");
        }
    }
    var txt1 = $("#startTime").val().replace(/-/g, "");//入库日期
    var txt2 = $("#endTime").val().replace(/-/g, "");
    var num;//是否显示公司间交易业务
    var reportJson={};
    if ($(".trading input:eq(0)").is(":checked")) {
        num = true;
    } else if ($(".trading input:eq(1)").is(":checked")) {
        num = false;
    }

    if(!blog){
        return;
    }

    if (txt1 == "" || txt2 == "") {
        alert("请输入日期");
    } else if (parseInt(txt1) > parseInt(txt2)) {
        alert("后一个日期必须大于或等于前一个日期");
    } else {
        $(".Mask").show();
        var item = 0;
         reportJson = {
            "plantValue": array1, //工厂
            "matnrValue": array2, //物料
            "matnrInterval": array5, //物料多区间
            "vendorValue": array3, //供应商
            "purGroupValue": array4, //采购组
            "isShow": num, //是否显示公司间交易业务
            "startTime": txt1,
            "endTime": txt2, //入库日期
            "startitem": item, //分页
            "pageitem": 20,
            "isExport": false
        }
        reportJson = JSON.stringify(reportJson);
        $(".export").on("click", function () {
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
        });
        $.ajax({
            url: ctx + "/crrc/reportFS01/result",
            data: reportJson,
            dataType: "json",
            contentType: "application/json;charset=utf-8",
            type: "post",
            success: function (data) {
                if ('300' === data.statusCode || '301' === data.statusCode) {
                    alert(data.message);
                    $(".Mask").hide();
                    $(".ch-kp-one table:eq(1)").html('');
                    return true;
                }
                if (data.procurementStorage.length == 0) {
                    $(".notSearch").next("div").hide();
                    $(".notSearch").text("未查询到数据");
                } else {
                    $(".notSearch").next("div").show();
                    $(".dataTime1").text(getTime(txt1, txt2));
                    if (data.procurementStorage.length < 20) {
                        $("#show").html("数据加载完成");
                    } else {
                        $("#show").html("");
                    }
                    var str = "";
                    $.each(data.procurementStorage, function (key, val) {
                        str += "<tr>"
                                + "<td style='width:40px' class='xh'>" + (key + 1) + "</td>"
                                + "<td style='width:40px' class='werks'>" + val.werks + "</td>"
                                + "<td style='width:110px' class='matnr'>" + val.matnr + "</td>"
                                + "<td style='width:380px' class='txtmd'>" + val.txtmd + "</td>"
                                + "<td style='width:90px;text-align:right;' class='sum_menge'>" + parseFloat(val.sum_menge).toFixed(3) + "</td>"
                                + "<td style='width:70px;text-align:center;' class='meins'>" + val.meins + "</td>"
                                + "<td style='width:135px;text-align:right;padding-right:5px;' class='sum_bualt'>" + formatNumber(val.sum_bualt,2,1) + "</td>"
                                + "<td style='width:70px;text-align:center;' class='price_base'>" + formatNumber(val.price_base,2,1) + "</td>"
                                + "<td style='width:90px' class='lifnr'>" + val.lifnr + "</td>"
                                + "<td style='width:270px' class='vendor'>" + val.vendor + "</td>"
                                + "<td class='ekgrp'>" + val.ekgrp + "</td>"
                                + "</tr>";
                    });

                    $(".ch-kp-one table:eq(1)").html(str);
                    $(".hj p span:eq(0) b").text(data.SUM.menge);
                    $(".hj p span:eq(1) b").text(formatNumber(data.SUM.bualt,2,1));
                    trBg($(".ch-kp-one table:eq(1)"))
                }
                $(".Mask").hide();
                $(".sureBtns").click();
                
            },
            error: function () {
                alert("数据请求失败!");
                $(".Mask").hide();
            }
        })

        item = 20;
        var b = true;
        $(".t_r_content").unbind("scroll").bind("scroll", function () {
        	toTop($(".toTop"),$(".t_r_content"));
            var fold = $(".t_r_content").height() + $(".t_r_content")[0].scrollTop;
            if (b && fold >= $(".t_r_content")[0].scrollHeight) {
                b = false;
                if($("#show").html()=="数据加载完成"){
					$(".Mask").hide();
				}else{
					$(".Mask").show();
				}
                reportJson = {
                    "plantValue": array1, //工厂
                    "matnrValue": array2, //物料
                    "vendorValue": array3, //供应商
                    "purGroupValue": array4, //采购组
                    "matnrInterval": array5, //物料多区间
                    "isShow": num, //是否显示公司间交易业务
                    "startTime": txt1,
                    "endTime": txt2, //入库日期
                    "startitem": item, //分页
                    "pageitem": 20
                }
                reportJson = JSON.stringify(reportJson);
                if ($("#show").text() != "数据加载完成") {
                    $.ajax({
                        url: ctx + "/crrc/reportFS01/result",
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
                            $.each(data.procurementStorage, function (key, val) {
                                str += "<tr>"
                                        + "<td style='width:40px' class='xh'>" + (key + 1 + item) + "</td>"
                                        + "<td style='width:40px' class='werks'>" + val.werks + "</td>"
                                        + "<td style='width:110px' class='matnr'>" + val.matnr + "</td>"
                                        + "<td style='width:380px' class='txtmd'>" + val.txtmd + "</td>"
                                        + "<td style='width:90px;text-align:right;' class='sum_menge'>" + parseFloat(val.sum_menge).toFixed(3) + "</td>"
                                        + "<td style='width:70px;text-align:center;' class='meins'>" + val.meins + "</td>"
                                        + "<td style='width:135px;text-align:right;padding-right:5px;' class='sum_bualt'>" + formatNumber(val.sum_bualt,2,1) + "</td>"
                                        + "<td style='width:70px;text-align:center;' class='price_base'>" + formatNumber(val.price_base,2,1) + "</td>"
                                        + "<td style='width:90px' class='lifnr'>" + val.lifnr + "</td>"
                                        + "<td style='width:270px' class='vendor'>" + val.vendor + "</td>"
                                        + "<td class='ekgrp'>" + val.ekgrp + "</td>"
                                        + "</tr>";
                            });
                            item = item + 20;
                            $(".ch-kp-one table:eq(1)").append(str);
                            trBg($(".ch-kp-one table:eq(1)"));
                            $(".Mask").hide();
                            $(".sureBtns").click();
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


    $(".hideCol").off("click").on("click", function () {
        var _this = $(this);
        //$("#Mask").show();
        if (!$(this).hasClass("bg")) {
            $(this).addClass("bg");
            var txt = $(this).attr("src");
            $(this).attr("src", txt.replace("hide.png", "hidered.png"));
            $(".hideColBox").show();
            $("#Mask").show();
            //allCheck($(".allCheck"));

            //获取表头名称
			var thArr=[]; 
			for(var i=0;i<$(".t_r_title table tr").find("th").length;i++){
				thArr.push($(".t_r_title table tr").find("th").eq(i).text())
			}
			//循环表头将名称添加到弹出框中
			var str1="";
			for(var j=0;j<thArr.length;j++){
				str1+="<tr>"
					+"<td style='width:50px; text-align: center;'><input type='checkbox' checked='checked' class='checks'></td>"
					+"<td>"+thArr[j]+"</td>"
				+"</tr>";
			}
			$(".hideTable .t_r_content table").html(str1);
			trBg($(".hideTable .t_r_content table"));
            //再次点击弹出框之前未被选中的仍未被选中
            //弹出框里的文本
            var arrTk = [];
            for (var i = 0; i < $(".hideTable .t_r_content tr").length; i++) {
                arrTk.push($(".hideTable .t_r_content tr").eq(i).find("td").text());
            }


            var val = "";
            $(".hideTable .t_r_content").on("click", ".checks", function () {
                if ($(this).prop("checked") == false) {
                    val = $(this).parent().next("td").text();
                    arr.push(val)
                } else {
                    val = $(this).parent().next("td").text();
                    $.each(arr, function (k, v) {
                        if (v == val) {
                        	arr.splice(k, 1)
                        }
                    })
                }
            })
            for (var i = 0; i < arrTk.length; i++) {
                for (var j = 0; j < arr.length; j++) {
                    if ($(".hideTable .t_r_content tr:eq(" + i + ") td:eq(1)").text() == arr[j]) {
                        $(".hideTable .t_r_content tr:eq(" + i + ") td:eq(0)").find("input").prop("checked", false)
                    }
                }


            }

 
            //所有的复选框默认的都是选中状态,点击一个复选框，全选的复选框不再被选中
            $(".hideTable").on("click", ".checks", function () {
                var inputLength = $(".checks").length;
                var incheckedLength = $(".checks[type='checkbox']:checked").length;
                if (incheckedLength < inputLength) {
                    $(".allCheck").prop("checked", false)
                } else {
                    $(".allCheck").prop("checked", true)
                }
            })
            $(".allCheck").on("click", function () {
                if ($(this).prop("checked")) {
                    $(this).parents(".t_r_t").next().find("table input").prop("checked", true);
	                arr=[];
                }else{
                	$(this).parents(".t_r_t").next().find("table input").prop("checked", false);
                	for(var i=0;i<$(this).parents(".t_r_t").next().find("table input").length;i++){
	            		var t=$(this).parents(".t_r_t").next().find("table input").eq(i).parent().next().text();
		                arr.push(t)
	            	}
                }
            })


            //点击取消和x弹出框消失，点击确定相应的列消失
            can($(".closeBtn"));
            can($(".canBtns"));
            $(".sureBtns").off("click").on("click", function () {
                //弹出框里的不被选中的已经放倒一个数组里，判断表头有这个内容就将此隐藏
                //表头数组
                var arrBig = [];
                for (var j = 0; j < $(".t_r_title table tr th").length; j++) {
                    var thBig = $(".t_r_title table tr th").eq(j).text();
                    arrBig.push(thBig)
                }
                //被选中的text的字段
                var textArr = [];
                for (var n = 0; n < arrBig.length; n++) {
                    if ($(".hideTable .t_r_content tr").eq(n).find("input[type='checkbox']").prop("checked")) {
                        var checkTrue = $(".hideTable .t_r_content tr").eq(n).find("input[type='checkbox']").parent("td").next().text();
                        textArr.push(checkTrue);
                    }
                }
                //被选中的class名
                var arrTrue = [];
                for (var n = 0; n < textArr.length; n++) {
                    if ($.inArray(textArr[n], arrBig) >= 0) {
                        var className = $(".t_r_title table tr th").eq($.inArray(textArr[n], arrBig)).attr("class");
                        arrTrue.push(className);
                    }
                }
                for (var m = 0; m < arrTrue.length; m++) {
                    $(".t_r").find("." + arrTrue[m]).show();
                    $("#t_r_content").find("." + arrTrue[m]).show();
                }

                //不被选中的class名
                var arrN = [];
                for (var n = 0; n < arr.length; n++) {
                    if ($.inArray(arr[n], arrBig) >= 0) {
                        var className = $(".t_r_title table tr th").eq($.inArray(arr[n], arrBig)).attr("class");
                        arrN.push(className);
                    }
                }
                //不被选中的class消失
                for (var m = 0; m < arrN.length; m++) {
                    $(".t_r").find("." + arrN[m]).hide();
                    $("#t_r_content").find("." + arrN[m]).hide();
                }

                $(".hideColBox").hide();
                var txt = _this.attr("src");
                _this.removeClass("bg");
                _this.attr("src", txt.replace("hidered.png", "hide.png"));
                $("#Mask").hide();
            })
        } else {
            $(this).removeClass("bg");
            var txt = $(this).attr("src");
            $(this).attr("src", txt.replace("hidered.png", "hide.png"));
            $(".hideColBox").hide();
            $("#Mask").hide();
        }
    })
})

function can(btn) {
    btn.off("click").on("click", function () {
        if ($(".hideColBox").css("display") == "block") {
            $(".hideCol").removeClass("bg");
            $(".hideColBox").hide();
            $("#Mask").hide();
            $(".hideCol").attr("src", $(".hideCol").attr("src").replace("hidered.png", "hide.png"));
        } else if ($(".hideColBox").css("display") == "none") {
            $(".hideCol").addClass("bg");
            $(".hideColBox").show();
            $(".hideCol").attr("src", $(".hideCol").attr("src").replace("hide.png", "hidered.png"));
            $("#Mask").show();
        }

    })
}