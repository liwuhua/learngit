//点击查询按钮，弹出查询框
var search1=new multiSearch2($(".btn1"),"reportFS14/tplant",null,null);//工厂
var search2=new multiSearch($(".btn2"),"tmaterial",0,10);//物料编码
var search3=new multiSearch1($(".btn3"),"matkl",null,null);//物料组
var search4=new multiSearch1($(".btn4"),"valclass",null,null);//评估类
var search5=new multiSearch1($(".btn5"),"lgort",null,null);//库存地点
var search6=new multiSearch3($(".btn6"),"reportFS14/lgpbe",null,null);//管库员（库存仓位）
var search7=new multiSearch3($(".btn7"),"reportFS14/sttyp",null,null);//库存状态
var search8=new multiSearch3($(".btn8"),"reportFS14/sobkz",null,null);//特殊库存标识

//控制表格高度的显示
var heights = $(window).height();
$(".ch-kp-fourteen1 .t_r_content").height((heights - 320) + "px");
$(".ch-kp-fourteen2 .t_r_content").height((heights - 320) + "px");
//点击收起按钮
$(".retract").off("click").on("click", function () {
    if ($(".container form").css("display") == "block") {
        $(".container form").slideUp();
        $(this).text("展开  ∨");
        var heights = $(window).height();
        $(".ch-kp-fourteen1 .t_r_content").animate({
            "height": (heights - 170) + "px"
        });
        $(".ch-kp-fourteen2 .t_r_content").animate({
            "height": (heights - 170) + "px"
        });
    } else {
        $(".container form").slideDown();
        $(this).text("收起  ∧");
        var heights = $(window).height();
        $(".ch-kp-fourteen1 .t_r_content").height((heights - 210) + ("px"));
        $(".ch-kp-fourteen2 .t_r_content").height((heights - 210) + ("px"));
    }
})
//采购入库的表格
$("#submit").off("click").on("click", function () {
	var blog=true;
    $(".notSearch").empty();
    $("#show").text("数据正在加载中");
    $("#btnChange").val("二级明细");
    $(".ch-kp-fourteen2").hide();
    var arrs = [], array1 = [], array2 = [],array3 = [],array4 = [],array5 = [],array6 = [],array7 = [],array8 = [],array9 = [];
    //工厂
    if ($(".factory").val() != "") {
        array1 = $(".factory").val().split(",");
        if(blog){
            blog = compareInput(array1,plant,"工厂");
        }
    }
    //物料
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
    //物料组
    if ($(".wlz").val() != "") {
        array3 = $(".wlz").val().split(",");
    }
    //评估类
    if ($(".pgl").val() != "") {
        array4 = $(".pgl").val().split(",");
    }
    //库存地点
    if ($(".kcdd").val() != "") {
        array6 = $(".kcdd").val().split(",");
    }
    //管库员
    if ($(".gky").val() != "") {
        array7 = $(".gky").val().split(",");
    }
    //库存状态
    if ($(".kczt").val() != "") {
        array8 = $(".kczt").val().split(",");
    }
    //特殊库存标识
    if ($(".tskcbs").val() != "") {
        array9 = $(".tskcbs").val();
    }
    //查询时间--必填
    var num;
    if($(".jzcxsj").val()!= ""){
    	num=parseInt($(".jzcxsj").val());
    }else{
    	num=null;
    }
    var txt1 = $(".checkDate").val().replace(/-/g, "");
    if(!blog){
        return;
    }
    var reportJson={};
    if (txt1 == "") {
        alert("请输入日期");
    } else {
    	$(".Mask").show();
    	var item = 0;
         reportJson = {
    		 plantValue: array1, //工厂
             matnrValue: array2, //物料多值
             matnrInterval: array5, //物料多区间
             matlGroupValue:array3,//物料组
             valClassValue:array4,//评估类
             lgortValue: array6, //库存地点
             lgpbeValue: array7, //管库员
             sttypValue:array8,//库存状态
             sobkzValue:array9,//特殊库存标识
             dayNum:num,//无动态天数
             dateTime:txt1,//查询时间
             ylzd:"a",//报表类型
             startitem: item, //分页
             pageitem: 20
        }
        reportJson = JSON.stringify(reportJson);
         //一级明细
         $.ajax({
             url: ctx + "/crrc/reportFS14/result",
             data: reportJson,
             dataType: "json",
             contentType: "application/json;charset=utf-8",
             type: "post",
             success: function (data) {
                 if ('300' === data.statusCode || '301' === data.statusCode) {
                     alert(data.message);
                     $(".Mask").hide();
                     $(".ch-kp-fourteen1 table").html('');
                     $(".ch-kp-fourteen2 table").html('');
                     return true;
                 }
                 if (jQuery.isEmptyObject(data)) {
                     $(".notSearch").next("div").hide();
                     $(".notSearch").text("未查询到数据");
                 } else {
                     $(".notSearch").next("div").show();
                     $(".dataTime1").text(getTime2(txt1));
                     if (data.data.length < 20) {
                         $("#show1").html("数据加载完成");
                     } else {
                         $("#show1").html("");
                     }
                     var str = "";
                   //formatNumber(val.year_plan,2,1)
                     $.each(data.data, function (key, val) {
                    	 str += "<tr>"
		                             + "<td class='xh' width='40px'>" + (key+1) + "</td>"
		                             + "<td class='WERKS' width='50px'>" + val.WERKS + "</td>"//工厂
		                             + "<td class='VAL_CLASS' width='60px'>" + val.VAL_CLASS + "</td>"//评估类
		                             + "<td class='VALTXT' width='120px'>" + val.VALTXT + "</td>"//评估类描述
		                             + "<td class='MATNR' width='110px'>" + val.MATNR + "</td>"//物料
		                             + "<td class='ARKTX' width='350px'>" + val.ARKTX + "</td>"//物料描述
		                             + "<td class='MATL_GROUP' width='50px'>" + val.MATL_GROUP + "</td>"//物料组
		                             + "<td class='TXTSH' width='130px'>" + val.TXTSH + "</td>"//物料组描述
		                             + "<td class='LGORT' width='60px'>" + val.LGORT + "</td>"//库存地点
		                             + "<td class='LOCTXT' width='130px'>" + val.LOCTXT + "</td>"//库存地描述
		                             + "<td class='LGPBE' width='60px'>" + val.LGPBE + "</td>"//库存仓位
		                             + "<td class='MEINS' style='text-align:center' width='60px'>" + val.MEINS + "</td>"//计量单位
		                             + "<td class='PRICE_BASE' style='text-align:center' width='60px'>" + val.PRICE_BASE + "</td>"//价格单位
		                             + "<td class='MATPRICE' width='100px' style='text-align:right;'>" + formatNumber(val.MATPRICE,2,1) + "</td>"//单价
		                             + "<td class='zjflrq' width='80px'>" + val.zjflrq + "</td>"//最近发料日期
		                             + "<td class='zjflsl' width='80px' style='text-align:right;'>" + val.zjflsl.toFixed(3) + "</td>"//最近发料数量
		                             + "<td class='zjflje' width='120px' style='text-align:right;'>" + formatNumber(val.zjflje,2,1) + "</td>"//最近发料金额
		                             + "<td class='qmkcsl' width='80px' style='text-align:right;'>" + val.qmkcsl.toFixed(3) + "</td>"//期末库存数量
		                             + "<td class='qmkcje' width='120px' style='text-align:right;'>" + formatNumber(val.qmkcje,2,1) + "</td>"//期末库存金额
		                             + "<td class='kcsl90' width='100px' style='text-align:right;'>" + val.kcsl90.toFixed(3) + "</td>"//1-3个月(数量)
		                             + "<td class='kcje90' width='100px' style='text-align:right;'>" + formatNumber(val.kcje90,2,1) + "</td>"//1-3个月(金额)
		                             + "<td class='kcsl180' width='100px' style='text-align:right;'>" + val.kcsl180.toFixed(3) + "</td>"//4-6个月(数量)
		                             + "<td class='kcje180' width='100px' style='text-align:right;'>" + formatNumber(val.kcje180,2,1) + "</td>"//4-6个月(金额)
		                             + "<td class='kcsl360' width='110px' style='text-align:right;'>" + val.kcsl360.toFixed(3) + "</td>"//半年到一年(数量)
		                             + "<td class='kcje360' width='110px' style='text-align:right;'>" + formatNumber(val.kcje360,2,1) + "</td>"//半年到一年(金额)
		                             + "<td class='kcsl720' width='110px' style='text-align:right;'>" + val.kcsl720.toFixed(3) + "</td>"//一年到两年(数量)
		                             + "<td class='kcje720' width='110px' style='text-align:right;'>" + formatNumber(val.kcje720,2,1) + "</td>"//一年到两年(金额)
		                             + "<td class='kcsl1080' width='110px' style='text-align:right;'>" + val.kcsl1080.toFixed(3) + "</td>"//两年到三年(数量)
		                             + "<td class='kcje1080' width='110px' style='text-align:right;'>" + formatNumber(val.kcje1080,2,1) + "</td>"//两年到三年(金额)
		                             + "<td class='kcsl1080s' width='110px' style='text-align:right;'>" + val.kcsl1080s.toFixed(3) + "</td>"//三年以上(数量)
		                             + "<td class='kcje1080s' style='text-align:right;'>" + formatNumber(val.kcje1080s,2,1) + "</td>"//三年以上(金额)
                                 + "</tr>";
                     });
                     $("#t_r_content table").html(str);
                     trBg($("#t_r_content table"));
                     var str_b="<tr>"
			                     + "<td class='xh' width='40px'>合计 </td>"
			                     + "<td class='WERKS' width='50px'></td>"//工厂
			                     + "<td class='VAL_CLASS' width='60px'></td>"//评估类
			                     + "<td class='VALTXT' width='120px'></td>"//评估类描述
			                     + "<td class='MATNR' width='110px'></td>"//物料
			                     + "<td class='ARKTX' width='350px'></td>"//物料描述
			                     + "<td class='MATL_GROUP' width='50px'></td>"//物料组
			                     + "<td class='TXTSH' width='130px'></td>"//物料组描述
			                     + "<td class='LGORT' width='60px'></td>"//库存地点
			                     + "<td class='LOCTXT' width='130px'></td>"//库存地描述
			                     + "<td class='LGPBE' width='60px'></td>"//库存仓位
			                     + "<td class='MEINS' width='60px' style='text-align:center'></td>"//计量单位
			                     + "<td class='PRICE_BASE' width='60px' style='text-align:center'></td>"//价格单位
			                     + "<td class='MATPRICE' width='100px'></td>"//单价
			                     + "<td class='zjflrq' width='80px'></td>"//最近发料日期
			                     + "<td class='zjflsl' width='80px'></td>"//最近发料数量
			                     + "<td class='zjflje' width='120px' style='text-align:right;'>" + formatNumber(data.sum_zjflje,2,1) + "</td>"//最近发料金额
			                     + "<td class='qmkcsl' width='80px'></td>"//期末库存数量
			                     + "<td class='qmkcje' width='120px' style='text-align:right;'>" + formatNumber(data.sum_qmkcje,2,1) + "</td>"//期末库存金额
			                     + "<td class='kcsl90' width='100px'></td>"//1-3个月(数量)
			                     + "<td class='kcje90' width='100px' style='text-align:right;'>" + formatNumber(data.sum_kcje90,2,1) + "</td>"//1-3个月(金额)
			                     + "<td class='kcsl180' width='100px'></td>"//4-6个月(数量)
			                     + "<td class='kcje180' width='100px' style='text-align:right;'>" + formatNumber(data.sum_kcje180,2,1) + "</td>"//4-6个月(金额)
			                     + "<td class='kcsl360' width='110px'></td>"//半年到一年(数量)
			                     + "<td class='kcje360' width='110px' style='text-align:right;'>" + formatNumber(data.sum_kcje360,2,1) + "</td>"//半年到一年(金额)
			                     + "<td class='kcsl720' width='110px'></td>"//一年到两年(数量)
			                     + "<td class='kcje720' width='110px' style='text-align:right;'>" + formatNumber(data.sum_kcje720,2,1) + "</td>"//一年到两年(金额)
			                     + "<td class='kcsl1080' width='110px'></td>"//两年到三年(数量)
			                     + "<td class='kcje1080' width='110px' style='text-align:right;'>" + formatNumber(data.sum_kcje1080,2,1) + "</td>"//两年到三年(金额)
			                     + "<td class='kcsl1080s' width='110px'></td>"//三年以上(数量)
			                     + "<td class='kcje1080s' style='text-align:right;padding-right:10px;'>" + formatNumber(data.sum_kcje1080s,2,1) + "</td>"//三年以上(金额)
			                 + "</tr>";
                     $("#t_r_b  table").html(str_b);
                     $("#t_r_b table td").css({"background-color":"rgba(0,0,0,0.5)","color":"#fff"});
             		}
                 $(".ch-kp-fourteen1").show();
                 $(".Mask").hide();
                 //$(".sureBtns").click();
                 $(".bianzhi").text(data.BZDW);
             },
             error: function () {
                 alert("数据请求失败1111!");
                 $(".Mask").hide();
             }
         });
         //一级明细下来加载
         item = 20;
         var b = true;
         $("#t_r_content").unbind("scroll").bind("scroll", function () {
         	toTop($(".toTop"),$("#t_r_content"));
             var fold = $("#t_r_content").height() + $(".t_r_content")[0].scrollTop;
             var s=$("#t_r_content").height();
             var t=$(".t_r_content")[0].scrollTop;
             var v=$(".t_r_content")[0].scrollHeight;
             if (b && fold >= $(".t_r_content")[0].scrollHeight) {
                 b = false;
                 if($("#show1").html()=="数据加载完成"){
 					$(".Mask").hide();
 				}else{
 					$(".Mask").show();
 				}
                 reportJson = {
            		 plantValue: array1, //工厂
                     matnrValue: array2, //物料多值
                     matnrInterval: array5, //物料多区间
                     matlGroupValue:array3,//物料组
                     valClassValue:array4,//评估类
                     lgortValue: array6, //库存地点
                     lgpbeValue: array7, //管库员
                     sttypValue:array8,//库存状态
                     sobkzValue:array9,//特殊库存标识
                     dayNum:num,//无动态天数
                     dateTime:txt1,//查询时间
                     ylzd:"a",//报表类型
                     startitem: item, //分页
                     pageitem: 20
                 }
                 reportJson = JSON.stringify(reportJson);
                 if ($("#show1").text() != "数据加载完成") {
                     $.ajax({
                         url: ctx + "/crrc/reportFS14/result",
                         data: reportJson,
                         dataType: "json",
                         contentType: "application/json;charset=utf-8",
                         type: "post",
                         success: function (data) {
                             if (data.data.length< 20) {
                                 $("#show1").html("数据加载完成");
                                 b = false;
                             }
                             var str = "";
                             $.each(data.data, function (key, val) {
                            	 str += "<tr>"
		                             + "<td class='xh' width='40px'>" + (key+1+item) + "</td>"
		                             + "<td class='WERKS' width='50px'>" + val.WERKS + "</td>"//工厂
		                             + "<td class='VAL_CLASS' width='60px'>" + val.VAL_CLASS + "</td>"//评估类
		                             + "<td class='VALTXT' width='120px'>" + val.VALTXT + "</td>"//评估类描述
		                             + "<td class='MATNR' width='110px'>" + val.MATNR + "</td>"//物料
		                             + "<td class='ARKTX' width='350px'>" + val.ARKTX + "</td>"//物料描述
		                             + "<td class='MATL_GROUP' width='50px'>" + val.MATL_GROUP + "</td>"//物料组
		                             + "<td class='TXTSH' width='130px'>" + val.TXTSH + "</td>"//物料组描述
		                             + "<td class='LGORT' width='60px'>" + val.LGORT + "</td>"//库存地点
		                             + "<td class='LOCTXT' width='130px'>" + val.LOCTXT + "</td>"//库存地描述
		                             + "<td class='LGPBE' width='60px'>" + val.LGPBE + "</td>"//库存仓位
		                             + "<td class='MEINS' width='60px'>" + val.MEINS + "</td>"//计量单位
		                             + "<td class='PRICE_BASE' width='60px'>" + val.PRICE_BASE + "</td>"//价格单位
		                             + "<td class='MATPRICE' width='100px' style='text-align:right;'>" + formatNumber(val.MATPRICE,2,1) + "</td>"//单价
		                             + "<td class='zjflrq' width='80px'>" + val.zjflrq + "</td>"//最近发料日期
		                             + "<td class='zjflsl' width='80px' style='text-align:right;'>" + val.zjflsl.toFixed(3) + "</td>"//最近发料数量
		                             + "<td class='zjflje' width='120px' style='text-align:right;'>" + formatNumber(val.zjflje,2,1) + "</td>"//最近发料金额
		                             + "<td class='qmkcsl' width='80px' style='text-align:right;'>" + val.qmkcsl.toFixed(3) + "</td>"//期末库存数量
		                             + "<td class='qmkcje' width='120px' style='text-align:right;'>" + formatNumber(val.qmkcje,2,1) + "</td>"//期末库存金额
		                             + "<td class='kcsl90' width='100px' style='text-align:right;'>" + val.kcsl90.toFixed(3) + "</td>"//1-3个月(数量)
		                             + "<td class='kcje90' width='100px' style='text-align:right;'>" + formatNumber(val.kcje90,2,1) + "</td>"//1-3个月(金额)
		                             + "<td class='kcsl180' width='100px' style='text-align:right;'>" + val.kcsl180.toFixed(3) + "</td>"//4-6个月(数量)
		                             + "<td class='kcje180' width='100px' style='text-align:right;'>" + formatNumber(val.kcje180,2,1) + "</td>"//4-6个月(金额)
		                             + "<td class='kcsl360' width='110px' style='text-align:right;'>" + val.kcsl360.toFixed(3) + "</td>"//半年到一年(数量)
		                             + "<td class='kcje360' width='110px' style='text-align:right;'>" + formatNumber(val.kcje360,2,1) + "</td>"//半年到一年(金额)
		                             + "<td class='kcsl720' width='110px' style='text-align:right;'>" + val.kcsl720.toFixed(3) + "</td>"//一年到两年(数量)
		                             + "<td class='kcje720' width='110px' style='text-align:right;'>" + formatNumber(val.kcje720,2,1) + "</td>"//一年到两年(金额)
		                             + "<td class='kcsl1080' width='110px' style='text-align:right;'>" + val.kcsl1080.toFixed(3) + "</td>"//两年到三年(数量)
		                             + "<td class='kcje1080' width='110px' style='text-align:right;'>" + formatNumber(val.kcje1080,2,1) + "</td>"//两年到三年(金额)
		                             + "<td class='kcsl1080s' width='110px' style='text-align:right;'>" + val.kcsl1080s.toFixed(3) + "</td>"//三年以上(数量)
		                             + "<td class='kcje1080s' style='text-align:right;'>" + formatNumber(val.kcje1080s,2,1) + "</td>"//三年以上(金额)
                                 + "</tr>";
                             });
                             item = item + 20;
                             $("#t_r_content table").append(str);
                             trBg($("#t_r_content table"));
                             $(".Mask").hide();
                             /*$(".sureBtns").click();*/
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


         
        //点击二级明细按钮二级明细表出来，一级明细表消失；点击一级明细按钮一级明细表出来，二级明细表消失


    }
    var flag=true,item2=0;
    $("#btnChange").off("click").on("click",function(){
        if($(this).val()=="二级明细"){
            $(this).val("一级明细");
            $(".ch-kp-fourteen1").hide();
          //二级明细表
            if (txt1 == "") {
                alert("请输入日期");
            } else {
                $(".Mask").show();
                reportJson = {
                		 plantValue: array1, //工厂
                         matnrValue: array2, //物料多值
                         matnrInterval: array5, //物料多区间
                         matlGroupValue:array3,//物料组
                         valClassValue:array4,//评估类
                         lgortValue: array6, //库存地点
                         lgpbeValue: array7, //管库员
                         sttypValue:array8,//库存状态
                         sobkzValue:array9,//特殊库存标识
                         dayNum:num,//无动态天数
                         dateTime:txt1,//查询时间
                         ylzd:"b",//报表类型
                         startitem: item2, //分页
                         pageitem: 20
                    }
                    reportJson = JSON.stringify(reportJson);
                if(flag){
                	$.ajax({
    	                url: ctx + "/crrc/reportFS14/result",
    	                data: reportJson,
    	                dataType: "json",
    	                contentType: "application/json;charset=utf-8",
    	                type: "post",
    	                success: function (data) {
    	                    if ('300' === data.statusCode || '301' === data.statusCode) {
    	                        alert(data.message);
    	                        $(".Mask").hide();
    	                        $(".ch-kp-fourteen1 table").html('');
    	                        $(".ch-kp-fourteen2 table").html('');
    	                        return true;
    	                    }
    	                    if (jQuery.isEmptyObject(data)) {
    	                        $(".notSearch").next("div").hide();
    	                        $(".notSearch").text("未查询到数据");
    	                    } else {
    	                        $(".notSearch").next("div").show();
    	                        $(".dataTime1").text(getTime2(txt1));
    	                        if (data.data.length < 20) {
    	                            $("#show2").html("数据加载完成");
    	                        } else {
    	                            $("#show2").html("");
    	                        }
    	                        var str = "";
    	                        $.each(data.data, function (key, val) {
    	                            str += "<tr>"
		    	                             + "<td class='xh' width='40px'>" + (key+1) + "</td>"
		   		                             + "<td class='WERKS' width='50px'>" + val.WERKS + "</td>"//工厂
		   		                             + "<td class='VAL_CLASS' width='60px'>" + val.VAL_CLASS + "</td>"//评估类
		   		                             + "<td class='VALTXT' width='120px'>" + val.VALTXT + "</td>"//评估类描述
		   		                             + "<td class='MATNR' width='110px'>" + val.MATNR + "</td>"//物料
		   		                             + "<td class='ARKTX' width='350px'>" + val.ARKTX + "</td>"//物料描述
		   		                             + "<td class='MATL_GROUP' width='50px'>" + val.MATL_GROUP + "</td>"//物料组
		   		                             + "<td class='TXTSH' width='130px'>" + val.TXTSH + "</td>"//物料组描述
		   		                             + "<td class='LGORT' width='60px'>" + val.LGORT + "</td>"//库存地点
		   		                             + "<td class='LOCTXT' width='130px'>" + val.LOCTXT + "</td>"//库存地描述
		   		                             + "<td class='LGPBE' width='60px'>" + val.LGPBE + "</td>"//库存仓位
		   		                             + "<td class='MEINS' width='60px'>" + val.MEINS + "</td>"//计量单位
		   		                             + "<td class='PRICE_BASE' width='60px'>" + val.PRICE_BASE + "</td>"//价格单位
		   		                             + "<td class='MATPRICE' width='100px' style='text-align:right;'>" + formatNumber(val.MATPRICE,2,1) + "</td>"//单价
		   		                             + "<td class='SOBKZ' width='90px'>" + val.SOBKZ + "</td>"//物料库存类型
		   		                             + "<td class='STTYP' width='90px'>" + val.STTYP + "</td>"//库存状态
		   		                             + "<td class='zjflrq' width='80px'>" + val.zjflrq + "</td>"//最近发料日期
		   		                             + "<td class='zjflsl' width='80px' style='text-align:right;'>" + val.zjflsl.toFixed(3) + "</td>"//最近发料数量
		   		                             + "<td class='zjflje' width='120px' style='text-align:right;'>" + formatNumber(val.zjflje,2,1)+ "</td>"//最近发料金额
		   		                             + "<td class='qmkcsl' width='80px' style='text-align:right;'>" + val.qmkcsl.toFixed(3) + "</td>"//期末库存数量
		   		                             + "<td class='qmkcje' width='120px' style='text-align:right;'>" + formatNumber(val.qmkcje,2,1) + "</td>"//期末库存金额
		   		                             + "<td class='kcsl90' width='100px' style='text-align:right;'>" + val.kcsl90.toFixed(3) + "</td>"//1-3个月(数量)
		   		                             + "<td class='kcje90' width='100px' style='text-align:right;'>" + formatNumber(val.kcje90,2,1) + "</td>"//1-3个月(金额)
		   		                             + "<td class='kcsl180' width='100px' style='text-align:right;'>" + val.kcsl180.toFixed(3) + "</td>"//4-6个月(数量)
		   		                             + "<td class='kcje180' width='100px' style='text-align:right;'>" + formatNumber(val.kcje180,2,1) + "</td>"//4-6个月(金额)
		   		                             + "<td class='kcsl360' width='110px' style='text-align:right;'>" + val.kcsl360.toFixed(3) + "</td>"//半年到一年(数量)
		   		                             + "<td class='kcje360' width='110px' style='text-align:right;'>" + formatNumber(val.kcje360,2,1) + "</td>"//半年到一年(金额)
		   		                             + "<td class='kcsl720' width='110px' style='text-align:right;'>" + val.kcsl720.toFixed(3) + "</td>"//一年到两年(数量)
		   		                             + "<td class='kcje720' width='110px' style='text-align:right;'>" + formatNumber(val.kcje720,2,1) + "</td>"//一年到两年(金额)
		   		                             + "<td class='kcsl1080' width='110px' style='text-align:right;'>" + val.kcsl1080.toFixed(3) + "</td>"//两年到三年(数量)
		   		                             + "<td class='kcje1080' width='110px' style='text-align:right;'>" + formatNumber(val.kcje1080,2,1) + "</td>"//两年到三年(金额)
		   		                             + "<td class='kcsl1080s' width='110px' style='text-align:right;'>" + val.kcsl1080s.toFixed(3) + "</td>"//三年以上(数量)
		   		                             + "<td class='kcje1080s' style='text-align:right;'>" + formatNumber(val.kcje1080s,2,1) + "</td>"//三年以上(金额)
    	                                    + "</tr>";
    	                        });
    	                        $(".ch-kp-fourteen2").show();
    	                        $("#t_r_content2 table").html(str);
    	                        trBg($("#t_r_content2 table"));
    	                        var str_b2="<tr>"
						                     + "<td class='xh' width='40px'>合计 </td>"
						                     + "<td class='WERKS' width='50px'></td>"//工厂
						                     + "<td class='VAL_CLASS' width='60px'></td>"//评估类
						                     + "<td class='VALTXT' width='120px'></td>"//评估类描述
						                     + "<td class='MATNR' width='110px'></td>"//物料
						                     + "<td class='ARKTX' width='350px'></td>"//物料描述
						                     + "<td class='MATL_GROUP' width='50px'></td>"//物料组
						                     + "<td class='TXTSH' width='130px'></td>"//物料组描述
						                     + "<td class='LGORT' width='60px'></td>"//库存地点
						                     + "<td class='LOCTXT' width='130px'></td>"//库存地描述
						                     + "<td class='LGPBE' width='60px'></td>"//库存仓位
						                     + "<td class='MEINS' width='60px'></td>"//计量单位
						                     + "<td class='PRICE_BASE' width='60px'></td>"//价格单位
						                     + "<td class='MATPRICE' width='100px'></td>"//单价
						                     + "<td class='SOBKZ' width='90px'></td>"//物料库存类型
		   		                             + "<td class='STTYP' width='90px'></td>"//库存状态
						                     + "<td class='zjflrq' width='80px'></td>"//最近发料日期
						                     + "<td class='zjflsl' width='80px'></td>"//最近发料数量
						                     + "<td class='zjflje' width='120px' style='text-align:right;'>" + formatNumber(data.sum_zjflje,2,1) + "</td>"//最近发料金额
						                     + "<td class='qmkcsl' width='80px'></td>"//期末库存数量
						                     + "<td class='qmkcje' width='120px' style='text-align:right;'>" + formatNumber(data.sum_qmkcje,2,1) + "</td>"//期末库存金额
						                     + "<td class='kcsl90' width='100px'></td>"//1-3个月(数量)
						                     + "<td class='kcje90' width='100px' style='text-align:right;'>" + formatNumber(data.sum_kcje90,2,1) + "</td>"//1-3个月(金额)
						                     + "<td class='kcsl180' width='100px'></td>"//4-6个月(数量)
						                     + "<td class='kcje180' width='100px' style='text-align:right;'>" + formatNumber(data.sum_kcje180,2,1) + "</td>"//4-6个月(金额)
						                     + "<td class='kcsl360' width='110px'></td>"//半年到一年(数量)
						                     + "<td class='kcje360' width='110px' style='text-align:right;'>" + formatNumber(data.sum_kcje360,2,1) + "</td>"//半年到一年(金额)
						                     + "<td class='kcsl720' width='110px'></td>"//一年到两年(数量)
						                     + "<td class='kcje720' width='110px' style='text-align:right;'>" + formatNumber(data.sum_kcje720,2,1) + "</td>"//一年到两年(金额)
						                     + "<td class='kcsl1080' width='110px'></td>"//两年到三年(数量)
						                     + "<td class='kcje1080' width='110px' style='text-align:right;'>" + formatNumber(data.sum_kcje1080,2,1) + "</td>"//两年到三年(金额)
						                     + "<td class='kcsl1080s' width='110px'></td>"//三年以上(数量)
						                     + "<td class='kcje1080s' style='text-align:right;padding-right:10px;'>" + formatNumber(data.sum_kcje1080s,2,1) + "</td>"//三年以上(金额)
						                 + "</tr>";
    	                        $("#t_r_b2  table").html(str_b2);
    	                        $("#t_r_b2 table td").css({"background-color":"rgba(0,0,0,0.5)","color":"#fff"});
    	                		}
    	                    $(".Mask").hide();
    	                    //$(".sureBtns").click();
    	                    flag=false;
    	                },
    	                error: function () {
    	                    alert("数据请求失败22222!");
    	                    $(".Mask").hide();
    	                }
    	            });
                	//二级明细下来加载
                	item2 = 20;
                    var d = true;
                    $("#t_r_content2").unbind("scroll").bind("scroll", function () {
                    	toTop($(".toTop"),$("#t_r_content2"));
                        var fold = $("#t_r_content2").height() + $(".t_r_content")[1].scrollTop;
                        if (d && fold >= $(".t_r_content")[1].scrollHeight) {
                            d = false;
                            if($("#show2").html()=="数据加载完成"){
            					$(".Mask").hide();
            				}else{
            					$(".Mask").show();
            				}
                            reportJson = {
                            	plantValue: array1, //工厂
                                matnrValue: array2, //物料多值
                                matnrInterval: array5, //物料多区间
                                matlGroupValue:array3,//物料组
                                valClassValue:array4,//评估类
                                lgortValue: array6, //库存地点
                                lgpbeValue: array7, //管库员
                                sttypValue:array8,//库存状态
                                sobkzValue:array9,//特殊库存标识
                                dayNum:num,//无动态天数
                                dateTime:txt1,//查询时间
                                ylzd:"b",//报表类型
                                startitem: item2, //分页
                                pageitem: 20
                            }
                            reportJson = JSON.stringify(reportJson);
                            if ($("#show2").text() != "数据加载完成") {
                                $.ajax({
                                    url: ctx + "/crrc/reportFS14/result",
                                    data: reportJson,
                                    dataType: "json",
                                    contentType: "application/json;charset=utf-8",
                                    type: "post",
                                    success: function (data) {
                                        if (data.data.length < 20) {
                                            $("#show2").html("数据加载完成");
                                            d = false;
                                        }
                                        var str = "";
                                        $.each(data.data, function (key, val) {
                                       	 str += "<tr>"
		           		                             + "<td class='xh' width='40px'>" + (key+1+item2) + "</td>"
		           		                             + "<td class='WERKS' width='50px'>" + val.WERKS + "</td>"//工厂
				   		                             + "<td class='VAL_CLASS' width='60px'>" + val.VAL_CLASS + "</td>"//评估类
				   		                             + "<td class='VALTXT' width='120px'>" + val.VALTXT + "</td>"//评估类描述
				   		                             + "<td class='MATNR' width='110px'>" + val.MATNR + "</td>"//物料
				   		                             + "<td class='ARKTX' width='350px'>" + val.ARKTX + "</td>"//物料描述
				   		                             + "<td class='MATL_GROUP' width='50px'>" + val.MATL_GROUP + "</td>"//物料组
				   		                             + "<td class='TXTSH' width='130px'>" + val.TXTSH + "</td>"//物料组描述
				   		                             + "<td class='LGORT' width='60px'>" + val.LGORT + "</td>"//库存地点
				   		                             + "<td class='LOCTXT' width='130px'>" + val.LOCTXT + "</td>"//库存地描述
				   		                             + "<td class='LGPBE' width='60px'>" + val.LGPBE + "</td>"//库存仓位
				   		                             + "<td class='MEINS' width='60px'>" + val.MEINS + "</td>"//计量单位
				   		                             + "<td class='PRICE_BASE' width='60px'>" + val.PRICE_BASE + "</td>"//价格单位
				   		                             + "<td class='MATPRICE' width='100px' style='text-align:right;'>" + formatNumber(val.MATPRICE,2,1) + "</td>"//单价
				   		                             + "<td class='SOBKZ' width='90px'>" + val.SOBKZ + "</td>"//物料库存类型
				   		                             + "<td class='STTYP' width='90px'>" + val.STTYP + "</td>"//库存状态
				   		                             + "<td class='zjflrq' width='80px'>" + val.zjflrq + "</td>"//最近发料日期
				   		                             + "<td class='zjflsl' width='80px' style='text-align:right;'>" + val.zjflsl.toFixed(3) + "</td>"//最近发料数量
				   		                             + "<td class='zjflje' width='120px' style='text-align:right;'>" + formatNumber(val.zjflje,2,1)+ "</td>"//最近发料金额
				   		                             + "<td class='qmkcsl' width='80px' style='text-align:right;'>" + val.qmkcsl.toFixed(3) + "</td>"//期末库存数量
				   		                             + "<td class='qmkcje' width='120px' style='text-align:right;'>" + formatNumber(val.qmkcje,2,1) + "</td>"//期末库存金额
				   		                             + "<td class='kcsl90' width='100px' style='text-align:right;'>" + val.kcsl90.toFixed(3) + "</td>"//1-3个月(数量)
				   		                             + "<td class='kcje90' width='100px' style='text-align:right;'>" + formatNumber(val.kcje90,2,1) + "</td>"//1-3个月(金额)
				   		                             + "<td class='kcsl180' width='100px' style='text-align:right;'>" + val.kcsl180.toFixed(3) + "</td>"//4-6个月(数量)
				   		                             + "<td class='kcje180' width='100px' style='text-align:right;'>" + formatNumber(val.kcje180,2,1) + "</td>"//4-6个月(金额)
				   		                             + "<td class='kcsl360' width='110px' style='text-align:right;'>" + val.kcsl360.toFixed(3) + "</td>"//半年到一年(数量)
				   		                             + "<td class='kcje360' width='110px' style='text-align:right;'>" + formatNumber(val.kcje360,2,1) + "</td>"//半年到一年(金额)
				   		                             + "<td class='kcsl720' width='110px' style='text-align:right;'>" + val.kcsl720.toFixed(3) + "</td>"//一年到两年(数量)
				   		                             + "<td class='kcje720' width='110px' style='text-align:right;'>" + formatNumber(val.kcje720,2,1) + "</td>"//一年到两年(金额)
				   		                             + "<td class='kcsl1080' width='110px' style='text-align:right;'>" + val.kcsl1080.toFixed(3) + "</td>"//两年到三年(数量)
				   		                             + "<td class='kcje1080' width='110px' style='text-align:right;'>" + formatNumber(val.kcje1080,2,1) + "</td>"//两年到三年(金额)
				   		                             + "<td class='kcsl1080s' width='110px' style='text-align:right;'>" + val.kcsl1080s.toFixed(3) + "</td>"//三年以上(数量)
				   		                             + "<td class='kcje1080s' style='text-align:right;'>" + formatNumber(val.kcje1080s,2,1) + "</td>"//三年以上(金额)
		    	                                  + "</tr>";
                                        });
                                        item2 = item2 + 20;
                                        $("#t_r_content2 table").append(str);
                                        trBg($("#t_r_content2 table"));
                                        $(".Mask").hide();
                                        /*$(".sureBtns").click();*/
                                        d = true;
                                    },
                                    error: function () {
                                        alert("数据请求失败!");
                                        $(".Mask").hide();
                                    }
                                })
                            }
                        }
                    });
                }else{
                	$(".Mask").hide();
                	$(".ch-kp-fourteen2").show();
                }
	            
	            
            }  
            
        }else if($(this).val()=="一级明细"){
            $(this).val("二级明细");
            $(".ch-kp-fourteen2").hide();
            $(".Mask").hide();
            $(".ch-kp-fourteen1").show();
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