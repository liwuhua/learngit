//点击查询按钮，弹出查询框
var search1 = new multiSearch3($(".btn1"), "reportFS54/cpxh", null , null);//产品型号
var search2 = new multiSearch3($(".btn2"), "reportFS54/aname", null , null);//配件名称
var search3 = new multiSearch($(".btn3"), "tmaterial", 0,10);//物料
var search4 = new multiSearch1($(".btn4"), "reportFS54/cx", null , null);//使用车型
var search5 = new multiSearch1($(".btn5"), "reportFS54/fwz", null , null);//服务站
var search6 = new multiSearch1($(".btn6"), "reportFS54/fwd", null , null);//服务点

//控制表格高度的显示
var heights = $(window).height();
$(".ch-kp-fiftyFour .t_r_content").height((heights - 300) + "px");
//点击收起按钮
$(".retract").off("click").on("click", function () {
    if ($(".container form").css("display") == "block") {
        $(".container form").slideUp();
        $(this).text("展开  ∨");
        var heights = $(window).height();
        $(".ch-kp-fiftyFour .t_r_content").animate({
            "height": (heights - 220) + "px"
        });
    } else {
        $(".container form").slideDown();
        $(this).text("收起  ∧");
        var heights = $(window).height();
        $(".ch-kp-fiftyFour .t_r_content").height((heights - 300) + "px");
    }
});

//点击下载模板
$("#downMb").off("click").on("click", function () {
	if($(".typeXh").val()==""){
		alert("型号不能为空")
	}else{
		var reportJson1={
		        motormodel:$(".typeXh").val()
		    };
		    downloadFile(reportJson1,ctx + "/crrc/reportFS54/downloadTemplate")
	}
})
//点击提交按钮渲染表格
$("#submit").off("click").on("click", function () {
	var blog = true;
    $(".notSearch").empty();
    $("#show").text("数据正在加载中");
    var array1 = [],array2=[],arrs=[],array3=[],array4=[],array5=[],array6=[],array7=[];
    if ($(".cpxh").val() != "") {
        array1 = $(".cpxh").val().split(",");//产品型号
    }
    if ($(".pjmc").val() != "") {
        array2 = $(".pjmc").val().split(",");//配件名称
    }
    if ($(".material").val() != "" && $(".material").val().indexOf(";") != -1) {
        arrs = $(".material").val().split(";");
        array3 = arrs[0].split(",");//物料多值
        array4 = arrs[1].split(",");//物料多区间
    } else if ($(".material").val() != "" && $(".material").val().indexOf(";") == -1 && $(".material").val().indexOf(":") != -1) {
        array3 = [];
        array4 = $(".material").val().split(",");
    } else if ($(".material").val() != "" && $(".material").val().indexOf(";") == -1 && $(".material").val().indexOf(":") == -1) {
        array3 = $(".material").val().split(",");
        array4 = [];
    } else if ($(".material").val() == "") {
        array3 = [];
        array4 = [];
    }
    if ($(".sycx").val() != "") {
        array5 = $(".sycx").val().split(",");//使用车型
    }
    if ($(".fwz").val() != "") {
        array6 = $(".fwz").val().split(",");//服务站
    }
    if ($(".fwd").val() != "") {
        array7 = $(".fwd").val().split(",");//服务点
    }
    var txt = $("#searchTime").val().replace(/-/g, "");//日期
   /* $(".material").val(" .")
    array3=["CNR121210"]*/
    var reportJson2 = {};
    if ($(".material").val()=="") {
        alert("请选择物料编码!!!");
    }else if($("#searchTime").val()==""){
    	 alert("请输入日期!!!");
    }else {
        $(".Mask").show();
        var item = 0;
        reportJson2 = {
            cpxh: array1, //型号
            aname: array2,//配件名称
            matnrValue: array3, //物料
            matnrInterval: array4, //物料多区间
            trainmodel: array5, //使用车型
            fwz: array6,//服务站
            fwd: array7,//服务点
            dateYearMonth:txt
        }
        reportJson2 = JSON.stringify(reportJson2);

        $(".export").on("click", function () {
            reportJson2 = {
        		cpxh: array1.join(","), //型号
                aname: array2.join(","),//配件名称
                matnrValue: array3.join(","), //物料
                matnrInterval: array4.join(","), //物料多区间
                trainmodel: array5.join(","), //使用车型
                fwz: array6.join(","),//服务站
                fwd: array7.join(","),//服务点
                dateYearMonth:txt,
                isExport: true
            }
            downloadFile(reportJson2, ctx + "/crrc/reportFS54/downloadFile");
        });
        //数据请求
        $.ajax({
            url: ctx + "/crrc/reportFS54/result",
            data: reportJson2,
            dataType: "json",
            contentType: "application/json;charset=utf-8",
            type: "post",
            success: function (data) {
            	if(data==null){
                	$(".notSearch").next("div").hide();
                    $(".notSearch").text("未查询到数据");
                    $(".Mask").hide();
                	return true;
                }
                if ('300' === data.statusCode || '301' === data.statusCode) {
                    alert(data.message);
                    $(".Mask").hide();
                    $(".ch-kp-fiftyFour table").html('');
                    return true;
                }
                
                $(".notSearch").next("div").show();
                var str="<tr>";
                $.each(data.title1,function(k,v){
                   $.each(v,function(key,val){
                	   str+="<th colspan='"+val+"'>"+key+"</th>"
                   });
                });
                str+="</tr><tr>";
                $.each(data.title2,function(k,v){
	                $.each(v,function(key,val){
	             	   str+="<th colspan='"+val+"'>"+key+"</th>"
	                });
	            });
	            str+="</tr><tr>";
	            $.each(data.title3,function(k,v){
	                $.each(v,function(key,val){
	              	   str+="<th colspan='"+val+"'>"+key+"</th>"
	                });
	            });
	            str+="</tr><tr>";
	            $.each(data.title4,function(k,v){
	                $.each(v,function(key,val){
	               	   str+="<th width='40px' colspan='"+val+"'>"+key+"</th>"
	                });
	            });
	            str+="</tr>";
	            $(".ch-kp-fiftyFour .t_r_title table").html(str);
	            for(var i=0;i<11;i++){
	            	$(".ch-kp-fiftyFour .t_r_title table tbody tr:eq(0)").find("th").eq(i).attr("rowspan","4");
	            }
	            $(".ch-kp-fiftyFour .t_r_title table tbody tr:eq(0)").find("th").eq(0).attr("width","40px");
	            $(".ch-kp-fiftyFour .t_r_title table tbody tr:eq(0)").find("th").eq(1).attr("width","60px");
	            $(".ch-kp-fiftyFour .t_r_title table tbody tr:eq(0)").find("th").eq(2).attr("width","60px");
	            $(".ch-kp-fiftyFour .t_r_title table tbody tr:eq(0)").find("th").eq(3).attr("width","320px");
	            $(".ch-kp-fiftyFour .t_r_title table tbody tr:eq(0)").find("th").eq(4).attr("width","100px");
	            $(".ch-kp-fiftyFour .t_r_title table tbody tr:eq(0)").find("th").eq(5).attr("width","100px");
	            $(".ch-kp-fiftyFour .t_r_title table tbody tr:eq(0)").find("th").eq(6).attr("width","120px");
	            $(".ch-kp-fiftyFour .t_r_title table tbody tr:eq(0)").find("th").eq(7).attr("width","120px");
	            $(".ch-kp-fiftyFour .t_r_title table tbody tr:eq(0)").find("th").eq(8).attr("width","120px");
	            $(".ch-kp-fiftyFour .t_r_title table tbody tr:eq(0)").find("th").eq(9).attr("width","60px");
	            $(".ch-kp-fiftyFour .t_r_title table tbody tr:eq(0)").find("th").eq(10).attr("width","40px");
	            
	            
	            
	            var str1='';
	            $.each(data.content,function(k,v){
	            	str1+="<tr>"
	            			+"<td width='40px'>"+k+"</td>";
	            			$.each(v,function(key,val){
	            				str1+="<td width='40px'>"+val+"</td>";
	            			});
	            		str1+="</tr>"
	            });
	            $(".ch-kp-fiftyFour .t_r_content table").html(str1);
	            $(".ch-kp-fiftyFour .t_r_content table tbody tr").find("td").eq(0).attr("width","40px");
	            $(".ch-kp-fiftyFour .t_r_content table tbody tr").find("td").eq(1).attr("width","60px");
	            $(".ch-kp-fiftyFour .t_r_content table tbody tr").find("td").eq(2).attr("width","60px");
	            $(".ch-kp-fiftyFour .t_r_content table tbody tr").find("td").eq(3).attr("width","320px");
	            $(".ch-kp-fiftyFour .t_r_content table tbody tr").find("td").eq(4).attr("width","100px");
	            $(".ch-kp-fiftyFour .t_r_content table tbody tr").find("td").eq(5).attr("width","100px");
	            $(".ch-kp-fiftyFour .t_r_content table tbody tr").find("td").eq(6).attr("width","120px");
	            $(".ch-kp-fiftyFour .t_r_content table tbody tr").find("td").eq(7).attr("width","120px");
	            $(".ch-kp-fiftyFour .t_r_content table tbody tr").find("td").eq(8).attr("width","120px");
	            $(".ch-kp-fiftyFour .t_r_content table tbody tr").find("td").eq(9).attr("width","60px");
	            $(".ch-kp-fiftyFour .t_r_content table tbody tr").find("td").eq(10).attr("width","40px");
	            $(".ch-kp-fiftyFour table").css("width",40*(data.title4.length)+1140+"px");
	            $(".ch-kp-fiftyFour table tbody").css("width","100%");
	            $('.ch-kp-fiftyFour .t_r_title').css("width",40*(data.title4.length)+1140+"px");
	            /*$(".ch-kp-fiftyFour .t_r_title table tbody tr:eq(3) th:last-child").removeAttr("width");
	            $(".ch-kp-fiftyFour .t_r_content table tbody tr td:last-child").removeAttr("width");*/
                $(".Mask").hide();
            },
            error: function () {
                alert("数据请求失败!");
                $(".Mask").hide();
            }
        });
        //第2张表下拉加载
       /* item = 20;
        var b = true;
        $(".t_r_content").unbind("scroll").bind("scroll", function () {
        	toTop($(".toTop"),$(".t_r_content"));
            var fold = $(".t_r_content").height() + $(".t_r_content").scrollTop;
            if (b && fold >= $(".t_r_content").scrollHeight) {
                b = false;
                if($("#show").html()=="数据加载完成"){
					$(".Mask").hide();
				}else{
					$(".Mask").show();
				}
                reportJson2 = {
                    "motormodel": array1.join(","), //型号
                    "startTime": txt1,//日期
                    "endTime": txt2,//日期
                    "startitem": item, //分页
                    "pageitem": 20,
                    "isExport": false
                }
                reportJson2 = JSON.stringify(reportJson2);
                $.ajax({
                    url: ctx + "/crrc/reportFS56/result",
                    data: reportJson2,
                    dataType: "json",
                    contentType: "application/json;charset=utf-8",
                    type: "post",
                    success: function (data) {
                        if (data[1].DATA.length <= 10) {
                            $("#show").html("数据加载完成");
                            b = false;
                        }
                        var html = "";
                        $.each(data[1].DATA,function(k,v){
                            html+="<tr>"
                                +"<td>" + (k + 1 + item) + "</td>"
                            $.each(v,function(key,val){
                                html+="<td>"+val+"</td>";
                            })
                            html+="</tr>";
                        })
                        item = item + 20;
                        $(".ch-kp-fiftySix table:eq(1)").append(html);
                        $(".ch-kp-fiftySix #t_r_content tr td").css("width","100px");
                        trBg($(".ch-kp-fiftySix table:eq(1)"));
                        $(".Mask").hide();
                        b = true;
                    },
                    error: function () {
                        alert("数据请求失败!");
                        $(".Mask").hide();
                    }
                })
            }
        });*/
    }


})