//点击查询按钮，弹出查询框
var search1=new multiSearch2($(".btn1"),"reportFS44/factory",null,null);//工厂
var search2=new multiSearch1($(".btn2"),"reportFS44/zhwcx",null,null);//工作中心
var search3=new multiSearch2($(".btn3"),"reportFS44/comppurtype",null,null);//组件采购类型
var search4=new multiSearch1($(".btn4"),"reportFS44/tpurgroup",null,null);//采购组
var search5=new multiSearch1($(".btn5"),"reportFS44/tvendor",null,null);//供应商编码
var search6=new multiSearch2($(".btn6"),"reportFS44/zpsfs",null,null);//配送方式
//控制表格高度的显示
var heights = $(window).height();
$(".ch-kp-fortyFour .t_r_content").height((heights - 270) + "px");
//点击收起按钮
$(".retract").off("click").on("click", function () {
    if ($(".container form").css("display") == "block") {
        $(".container form").slideUp();
        $(this).text("展开  ∨");
        var heights = $(window).height();
        $(".ch-kp-fortyFour .t_r_content").animate({
            "height": (heights - 190) + "px"
        });
    } else {
        $(".container form").slideDown();
        $(this).text("收起  ∧");
        var heights = $(window).height();
        $(".ch-kp-fortyFour .t_r_content").height((heights - 270) + ("px"));
    }
})
//采购入库的表格
$("#submit").off("click").on("click", function () {
     var blog = true;
     $(".notSearch").empty();
     $("#show").text("数据正在加载中");
     var array1 = [], array2 = [], array3 = [], array4 = [], array5 = [],array6=[];
     var isExclu1='',isExclu2='',isExclu3='';
     if ($(".werksValue").val() != "") {
    	 array1 = $(".werksValue").val().split(",");//工厂
	     if(blog){
	    	 blog = compareInput(array1,plant,"工厂");
	     }
     }
     if ($(".vendorValue").val() != "") {
    	 array2 = $(".vendorValue").val().split(",");//工作中心
     }
     if($(".dauatValue").val()!=""){
    	 array3 = $(".dauatValue").val().split(",");//组件采购类型
     }
     if ($(".purGroupValue").val() != "") {
    	 array4 = $(".purGroupValue").val().split(",");//采购组
	     array4 = regData(array4,pur_group);
	     if(blog){
	    	 blog = compareInput(array4,pur_group,"采购组");
	     }
     }
     if($(".oneValue").val() != ""){
    	 array5=$(".oneValue").val().split(",");//供应商编码
     }
     if($(".twoValue").val() != ""){
    	 array6=$(".twoValue").val().split(",");//配送方式
     }
     isExclu1=$(".purGroupValuePt").is(':checked');
     isExclu2=$(".oneValuePt").is(':checked');
     isExclu3=$(".twoValuePt").is(':checked');
	 var txt1 = $("#startTime").val().replace(/-/g, "");//报告日期
	 var reportJson = {};
     if (!blog) {
    	 return;
     }
     if (txt1 == "") {
    	 alert("请输入报告日期");
    	 return;
    }
    $(".Mask").show();
    var item = 0;
    reportJson = {
        "werksValue":array1 , //工厂
        "vendorValue": array2, //工作中心
        "dauatValue":array3,//组件采购类型
        "purGroupValue":array4,//采购组
        "oneValue": array5,//供应商编码
        "twoValue":array6,//配送方式
        "startTimeThree": txt1,//报告日期
        "isExclu1":isExclu1,
        "isExclu2":isExclu2,
        "isShow":isExclu3,
        "startitem": item, //分页
        "pageitem": 20,
        "isExport": false
    }
    reportJson = JSON.stringify(reportJson);
    $(".export").on("click", function () {
	     reportJson = {
		    "werksValue":array1.join("."), //工厂
	        "vendorValue": array2.join("."), //工作中心
	        "dauatValue":array3.join("."),//组件采购类型
	        "purGroupValue":array4.join("."),//采购组
	        "oneValue": array5.join("."),//供应商编码
	        "twoValue":array6.join("."),//配送方式
	        "startTimeThree": txt1,//报告日期
	        "isExclu1":isExclu1,
	        "isExclu2":isExclu2,
	        "isShow":isExclu3,
	     	isExport: true
	     }
	     downloadFile(reportJson, ctx + "/crrc/reportFS44/downloadFile");
    });
    $.ajax({
        url: ctx + "/crrc/reportFS44/result",
        data: reportJson,
        dataType: "json",
        contentType: "application/json;charset=utf-8",
        type: "post",
        success: function (data) {
            if ('300' === data.statusCode || '301' === data.statusCode) {
                alert(data.message);
                $(".Mask").hide();
                $(".ch-kp-fortyFour table:eq(1)").html('');
                return true;
            }
            if (data.matnrpullcompar.length == 0) {
                $(".notSearch").next("div").hide();
                $(".notSearch").text("未查询到数据");
            } else {
                $(".notSearch").next("div").show();
                $(".dataTime1").text(getTime2(txt1));
                $(".dataTime2").text(getTime(txt2, txt3));
                if (data.matnrpullcompar.length < 20) {
                    $("#show").html("数据加载完成");
                } else {
                    $("#show").html("");
                }
                var str = "";//.toFixed(3)
                $.each(data.lackorder, function (k, v) {
                    str += "<tr>"
		                        + "<td width='40px' class='xh'>" + (k + 1) + "</td>"
		                        + "<td width='40px' class='WERKS'>" + v.WERKS + "</td>"//工厂
		                        + "<td width='110px' class='KTEXT'>" + v.KTEXT + "</td>"//工作中心
		                        + "<td width='100px' class='DELIDATE'>" + v.DELIDATE + "</td>"//报告日期
		                        + "<td width='130px' class='BEFORE_ORDERNUM,'>" + v.BEFORE_ORDERNUM, + "</td>"// 拉动前缺件订单数
		                        + "<td class='AFTER_ORDERNUM'>" + v.AFTER_ORDERNUM + "</td>"//拉动后缺件订单数
	                        + "</tr>";
                });
                $(".ch-kp-fortyFour table:eq(1)").html(str);
                trBg($(".ch-kp-fortyFour table:eq(1)"))
            }
            $(".Mask").hide();

        },
        error: function () {
            alert("数据请求失败!");
            $(".Mask").hide();
        }
     });

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
		    		"werksValue":array1 , //工厂
	    	        "vendorValue": array2, //工作中心
	    	        "dauatValue":array3,//组件采购类型
	    	        "purGroupValue":array4,//采购组
	    	        "oneValue": array5,//供应商编码
	    	        "twoValue":array6,//配送方式
	    	        "startTimeThree": txt1,//报告日期
	    	        "isExclu1":isExclu1,
	    	        "isExclu2":isExclu2,
	    	        "isShow":isExclu3,
	    	        "startitem": item, //分页
	    	        "pageitem": 20
		     }
		     reportJson = JSON.stringify(reportJson);
		     if ($("#show").text() != "数据加载完成") {
		     $.ajax({
			     url: ctx + "/crrc/reportFS44/result",
			     data: reportJson,
			     dataType: "json",
			     contentType: "application/json;charset=utf-8",
			     type: "post",
			     success: function (data) {
				     if (data.matnrpullcompar.length < 20) {
					     $("#show").html("数据加载完成");
					     b = false;
				     }
				     var str = "";
				     $.each(data.matnrpullcompar, function (key, val) {
					     str += "<tr>"
						    	 	+ "<td width='40px' class='xh'>" + (k + 1) + "</td>"
			                        + "<td width='40px' class='WERKS'>" + v.WERKS + "</td>"//工厂
			                        + "<td width='110px' class='KTEXT'>" + v.KTEXT + "</td>"//工作中心
			                        + "<td width='100px' class='DELIDATE'>" + v.DELIDATE + "</td>"//报告日期
			                        + "<td width='130px' class='BEFORE_ORDERNUM,'>" + v.BEFORE_ORDERNUM, + "</td>"// 拉动前缺件订单数
			                        + "<td class='AFTER_ORDERNUM'>" + v.AFTER_ORDERNUM + "</td>"//拉动后缺件订单数
							     + "</tr>";
				     });
				     item = item + 20;
				     $(".ch-kp-fortyFour table:eq(1)").append(str);
				     trBg($(".ch-kp-fortyFour table:eq(1)"));
				     $(".Mask").hide();
				     //$(".sureBtns").click();
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
})