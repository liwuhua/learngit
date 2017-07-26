//点击查询按钮，弹出查询框
var search1=new multiSearch2($(".btn1"),"reportFS13/tplant",null,null);//工厂
var search2=new multiSearch($(".btn2"),"tmaterial",0,10);//物料编码
var search3=new multiSearch1($(".btn3"),"lgort",null,null);//库存地点
var search4=new multiSearch3($(".btn4"),"reportFS13/lgpbe",null,null);//库存员
var search5=new multiSearch1($(".btn5"),"reportFS13/tvendor",null,null);//供应商
var search6=new multiSearch1($(".btn6"),"vendortype",null,null);//供应商类别
//控制表格高度的显示
var heights = $(window).height();
$(".ch-kp-thirteen .t_r_content").height((heights - 225) + "px");
//点击收起按钮
$(".retract").off("click").on("click", function () {
    if ($(".container form").css("display") == "block") {
        $(".container form").slideUp();
        $(this).text("展开  ∨");
        var heights = $(window).height();
        $(".ch-kp-thirteen .t_r_content").animate({
            "height": (heights - 145) + "px"
        });
    } else {
        $(".container form").slideDown();
        $(this).text("收起  ∧");
        var heights = $(window).height();
        $(".ch-kp-thirteen .t_r_content").height((heights - 225) + ("px"));
    }
})
var arr = [], arrTk = [];
//采购入库的表格
$("#submit").off("click").on("click", function () {
    var blog = true;
    $(".notSearch").empty();
    $("#show").text("数据正在加载中");
    //$(".hideCol").click();
    var arrs = [], array1 = [], array2 = [], array3 = [], array4 = [], array5 = [],array6=[],array7=[];
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
    if($(".kcdd").val()!=""){
    	array3=$(".kcdd").val().split(",");//库存地点
    }
    if($(".gky").val()!=""){
    	array4=$(".gky").val().split(",");//管库员
    }
    if ($(".supplier").val() != "") {
        array6 = $(".supplier").val().split(",");//供应商
    }
    if ($(".supplier-lb").val() != "") {
        array7 = $(".supplier-lb").val().split(",");//供应商类别
    }
    var txt1 = $(".checkDate").val().replace(/-/g, "");//查询日期
    var num1=$(".startDay").val();
    var num2=$(".endDay").val();
    var reportJson={};
    if(num1!=""&&num2==""){
    	alert("库存天数为区间");
    	return;
    }else if(num1==""&&num2!=""){
    	alert("库存天数为区间");
    	return;
    }
    if(!blog){
        return;
    }
    
    if(""!=num1 && num2!="" && parseInt(num1)>=parseInt(num2) ){
    	alert("库存终止天数必须大于等于起始天数");
    }else if (txt1 == "") {
        alert("请输入查询日期");
    }else {
        $(".Mask").show();
        var item = 0;
         reportJson = {
            "plantValue": array1, //工厂
            "matnrValue": array2, //物料
            "matnrInterval": array5, //物料多区间
            "lgortValue": array3, //库存地点
            "lgpbeValue": array4, //管库员
            "vendorValue": array6, //供应商
            "vendorTypeValue": array7, //供应商类别
            "dateTime": txt1,//查询日期
            "startTime":num1,//起始天数
            "endTime":num2,//终止天数
            "startitem": item, //分页
            "pageitem": 20,
            "isExport": false
        }
        reportJson = JSON.stringify(reportJson);
        $(".export").on("click", function () {
            reportJson = {
        		plantValue: array1.join(","), //工厂
                matnrValue: array2.join(","), //物料
                matnrInterval: array5.join(","), //物料多区间
                lgortValue: array3.join(","), //库存地点
                lgpbeValue: array4.join(","), //管库员
                vendorValue: array6.join(","), //供应商
                vendorTypeValue: array7.join(","), //供应商类别
                dateTime: txt1,//查询日期
                startTime:num1,//起始天数
                endTime:num2,//终止天数
                isExport: true
            }
            downloadFile(reportJson, ctx + "/crrc/reportFS13/downloadFile");
        });
        $.ajax({
            url: ctx + "/crrc/reportFS13/result",
            data: reportJson,
            dataType: "json",
            contentType: "application/json;charset=utf-8",
            type: "post",
            success: function (data) {
                if ('300' === data.statusCode || '301' === data.statusCode) {
                    alert(data.message);
                    $(".Mask").hide();
                    $(".ch-kp-thirteen table:eq(1)").html('');
                    return true;
                }
                if (jQuery.isEmptyObject(data)) {
                    $(".notSearch").next("div").hide();
                    $(".notSearch").text("未查询到数据");
                } else {
                    $(".notSearch").next("div").show();
                    $(".dataTime1").text(getTime2(txt1));
                    if (data.length < 20) {
                        $("#show").html("数据加载完成");
                    } else {
                        $("#show").html("");
                    }
                    var str = "";
                    $.each(data.data, function (key, val) {
                        str += "<tr>"
                                + "<td width='40px' class='xh'>" + (key + 1) + "</td>"
                                + "<td width='60px' class='WERKS'>" + val.WERKS + "</td>"
                                + "<td width='80px' class='LGORT'>" + val.LGORT + "</td>"
                                + "<td width='100px' class='LOCTXT'>" + val.LOCTXT + "</td>"
                                + "<td width='70px' class='MATL_GROUP'>" + val.MATL_GROUP + "</td>"
                                + "<td width='120px' class='TXTSH'>" + val.TXTSH + "</td>"
                                + "<td width='80px' class='VAL_CLASS'>" + val.VAL_CLASS + "</td>"
                                + "<td width='150px' class='VALTXT'>" + val.VALTXT + "</td>"
                                + "<td width='80px' class='LGPBE'>" + val.LGPBE + "</td>"
                                + "<td width='120px' class='MATNR'>" + val.MATNR + "</td>"
                                + "<td width='300px' class='ARKTX'>" + val.ARKTX + "</td>"
                                + "<td width='120px' class='KDAUF'>" + val.KDAUF + "</td>"
                                + "<td width='120px' class='KDPOS'>" + val.KDPOS + "</td>"
                                + "<td width='120px' class='LIFNR'>" + val.LIFNR + "</td>"//分包商编码
                                + "<td width='200px' class='VENTXT'>" + val.VENTXT + "</td>"//分包商名称
                                + "<td width='120px' class='CHARG'>" + val.CHARG + "</td>"//批次
                                + "<td width='120px' class='SERNR'>" + val.SERNR + "</td>"//序列号
                                + "<td width='120px' class='STDAT'>" + val.STDAT + "</td>"//入库时间
                                + "<td width='120px' class='STCOU'>" + val.STCOU + "</td>"//库存天数
                                + "<td width='120px' style='text-align:center' class='MEINS'>" + val.MEINS + "</td>"//计量单位
                                + "<td width='120px' class='BWMNG'>" + val.BWMNG + "</td>"//库存数量
                                + "<td width='120px' style='text-align:center' class='PRICE_BASE'>" + val.PRICE_BASE + "</td>"//价格单位
                                + "<td width='120px' class='MATPRICE'>" + val.MATPRICE + "</td>"//单价
                                + "<td width='120px' class='kcje'>" + val.kcje + "</td>"//库存金额
                                + "<td width='120px' class='LIFNR'>" + val.LIFNR + "</td>"//供应商代码
                                + "<td width='200px' class='VENTXT'>" + val.VENTXT + "</td>"//供应商名称
                                + "<td class='XASUPPTY'>" + val.XASUPPTY + "</td>"//供应商物料类别
                                + "</tr>";
                    });

                    $(".ch-kp-thirteen table:eq(1)").html(str);
                    $(".hj p span:eq(0) b").text(data.sum_bwmng);
                    $(".hj p span:eq(1) b").text(formatNumber(data.sum_kcje,2,1));
                    trBg($(".ch-kp-thirteen table:eq(1)"))
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
                    "matnrInterval": array5, //物料多区间
                    "lgortValue": array3, //库存地点
                    "lgpbeValue": array4, //管库员
                    "vendorValue": array6, //供应商
                    "vendorTypeValue": array7, //供应商类别
                    "dateTime": txt1,//查询日期
                    "startTime":num1,//起始天数
                    "endTime":num2,//终止天数
                    "startitem": item, //分页
                    "pageitem": 20
                }
                reportJson = JSON.stringify(reportJson);
                if ($("#show").text() != "数据加载完成") {
                    $.ajax({
                        url: ctx + "/crrc/reportFS13/result",
                        data: reportJson,
                        dataType: "json",
                        contentType: "application/json;charset=utf-8",
                        type: "post",
                        success: function (data) {
                            if (data.data.length < 20) {
                                $("#show").html("数据加载完成");
                                b = false;
                            }
                            var str = "";
                            $.each(data.data, function (key, val) {
                            	str += "<tr>"
                                    + "<td width='40px' class='xh'>" + (key + 1+item) + "</td>"
                                    + "<td width='60px' class='WERKS'>" + val.WERKS + "</td>"
                                    + "<td width='80px' class='LGORT'>" + val.LGORT + "</td>"
                                    + "<td width='100px' class='LOCTXT'>" + val.LOCTXT + "</td>"
                                    + "<td width='70px' class='MATL_GROUP'>" + val.MATL_GROUP + "</td>"
                                    + "<td width='120px' class='TXTSH'>" + val.TXTSH + "</td>"
                                    + "<td width='80px' class='VAL_CLASS'>" + val.VAL_CLASS + "</td>"
                                    + "<td width='150px' class='VALTXT'>" + val.VALTXT + "</td>"
                                    + "<td width='80px' class='LGPBE'>" + val.LGPBE + "</td>"
                                    + "<td width='120px' class='MATNR'>" + val.MATNR + "</td>"
                                    + "<td width='300px' class='ARKTX'>" + val.ARKTX + "</td>"
                                    + "<td width='120px' class='KDAUF'>" + val.KDAUF + "</td>"
                                    + "<td width='120px' class='KDPOS'>" + val.KDPOS + "</td>"
                                    + "<td width='120px' class='LIFNR'>" + val.LIFNR + "</td>"//分包商编码
                                    + "<td width='200px' class='VENTXT'>" + val.VENTXT + "</td>"//分包商名称
                                    + "<td width='120px' class='CHARG'>" + val.CHARG + "</td>"//批次
                                    + "<td width='120px' class='SERNR'>" + val.SERNR + "</td>"//序列号
                                    + "<td width='120px' class='STDAT'>" + val.STDAT + "</td>"//入库时间
                                    + "<td width='120px' class='STCOU'>" + val.STCOU + "</td>"//库存天数
                                    + "<td width='120px' style='text-align:center' class='MEINS'>" + val.MEINS + "</td>"//计量单位
                                    + "<td width='120px' class='BWMNG'>" + val.BWMNG + "</td>"//库存数量
                                    + "<td width='120px' style='text-align:center' class='PRICE_BASE'>" + val.PRICE_BASE + "</td>"//价格单位
                                    + "<td width='120px' class='MATPRICE'>" + val.MATPRICE + "</td>"//单价
                                    + "<td width='120px' class='kcje'>" + val.kcje + "</td>"//库存金额
                                    + "<td width='120px' class='LIFNR'>" + val.LIFNR + "</td>"//供应商代码
                                    + "<td width='200px' class='VENTXT'>" + val.VENTXT + "</td>"//供应商名称
                                    + "<td class='XASUPPTY'>" + val.XASUPPTY + "</td>"//供应商物料类别
                                    + "</tr>";
                            });
                            item = item + 20;
                            $(".ch-kp-thirteen table:eq(1)").append(str);
                            trBg($(".ch-kp-thirteen table:eq(1)"));
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