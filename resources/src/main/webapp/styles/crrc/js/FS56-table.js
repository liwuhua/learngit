//点击查询按钮，弹出查询框
var search1 = new multiSearch5($(".btn1"), "reportFS56/motormodel", null , null);//型号

//控制表格高度的显示
var heights = $(window).height();
$(".ch-kp-fiftySix").height((heights - 150) + "px");
//点击收起按钮
$(".retract").off("click").on("click", function () {
    if ($(".container form").css("display") == "block") {
        $(".container form").slideUp();
        $(this).text("展开  ∨");
        var heights = $(window).height();
        $(".ch-kp-fiftySix").animate({
            "height": (heights - 110) + "px"
        });
    } else {
        $(".container form").slideDown();
        $(this).text("收起  ∧");
        var heights = $(window).height();
        $(".ch-kp-fiftySix").height((heights - 150) + "px");
    }
});

//点击下载模板
$("#downMb").off("click").on("click", function () {
	if($(".typeXh").val()==""){
		alert("型号不能为空")
	}else{
		reportJson1={
		        motormodel:$(".typeXh").val()
		    };
		    downloadFile(reportJson1,ctx + "/crrc/reportFS56/downloadTemplate")
	}
})
//点击提交按钮渲染表格
$("#submit").off("click").on("click", function () {
	var blog = true;
    $(".notSearch").empty();
    $("#show").text("数据正在加载中");
    var array1 = [];
    if ($(".typeXh").val() != "") {
        array1 = $(".typeXh").val().split(",");//型号
    }
    var txt1 = $("#startTime").val().replace(/-/g, "");//日期
    var txt2 = $("#endTime").val().replace(/-/g, "");
    if(txt1==""&&txt2!=""){
    	alert("日期两个都有或者两个都无!");
    	return;
    }
    if(txt1!=""&&txt2==""){
    	alert("日期两个都有或者两个都无!");
    	return;
    }

    var reportJson2 = {};
    if ($(".typeXh").val()=="") {
        alert("请输入型号");
    } else if (parseInt(txt1) > parseInt(txt2)) {
        alert("后一个日期必须大于或等于前一个日期");
    } else {
        $(".Mask").show();
        var item = 0;
        reportJson2 = {
            "motormodel": array1.join(","), //型号
            "startTime": txt1,//日期
            "endTime": txt2,//日期
            "startitem": item, //分页
            "pageitem": 20,
            "isExport": false
        }
        reportJson2 = JSON.stringify(reportJson2);

        $(".export").on("click", function () {
            reportJson2 = {
                motormodel: array1.join(","), //销售组织
                startTime: txt1, //日期
                endTime:txt2,
                isExport: true
            }
            downloadFile(reportJson2, ctx + "/crrc/reportFS56/downloadFile");
        });
        //数据请求
        $.ajax({
            url: ctx + "/crrc/reportFS56/result",
            data: reportJson2,
            dataType: "json",
            contentType: "application/json;charset=utf-8",
            type: "post",
            success: function (data) {
                if ('300' === data.statusCode || '301' === data.statusCode) {
                    alert(data.message);
                    $(".Mask").hide();
                    $(".ch-kp-fiftySix table").html('');
                    return true;
                }
                
                if (data[1].DATA.length == 0) {
                	$(".notSearch+div").hide();
                	/*var str="";
                    $.each(data[0].HEADER,function(k,v){
                        str+="<tr>";
                        $.each(v,function(key,val){
                            str+="<th colspan="+val.col+" rowspan="+val.row+" style='text-align:center;'>"+val.name+"</th>";
                        })
                        str+="</tr>";
                    })
                    $(".ch-kp-fiftySix table:eq(0)").html(str);*/
                    $(".ch-kp-fiftySix table:eq(1)").html("");
                    //$(".ch-kp-fiftySix table:eq(1)").html(html);
                    $(".notSearch").html("未查询到数据");
                   /* $("#show").html("数据加载完成");
                	$.each($(".ch-kp-fiftySix table:eq(0) tr th"),function(k,v){
                        if(v.colSpan==1){
                            $("th[colspan='1']").attr('width',"100px");
                        }
                    })
                	 var width=100*data[2].NUM;
                     $(".ch-kp-fiftySix table").css("width",width);
                     $(".ch-kp-fiftySix .t_r_title").css("width",width);*/
                     
                    //$(".ch-kp-fiftySix").hide();
                    //$(".notSearch").text("未查询到数据");
                } else {
                    $(".notSearch+div").show();
                    
                    if (data[1].DATA.length < 20) {
                        $("#show").html("数据加载完成");
                    } else {
                        $("#show").html("");
                    }
                    var str="",html="";
                    $.each(data[0].HEADER,function(k,v){
                        str+="<tr>";
                        $.each(v,function(key,val){
                            str+="<th colspan="+val.col+" rowspan="+val.row+">"+val.name+"</th>";
                        })
                        str+="</tr>";
                    })
                    $.each(data[1].DATA,function(k,v){
                        html+="<tr>"
                            //+"<td>" + (k + 1) + "</td>"
                        $.each(v,function(key,val){
                            html+="<td>"+CheckNull(val)+"</td>";
                        })
                        html+="</tr>";
                    })
                    $(".ch-kp-fiftySix table:eq(0)").html(str);
                    $(".ch-kp-fiftySix table:eq(1)").html(html);
                    $.each($(".ch-kp-fiftySix table:eq(0) tr th"),function(k,v){
                        if(v.colSpan==1){
                            $("th[colspan='1']").attr('width',"100px");
                        }
                    })
                    var width=100*data[2].COL;
                    var wid=width+20;
                    $(".ch-kp-fiftySix table").css("width",width);
                    $(".ch-kp-fiftySix .t_r_title").css("width",wid);
                    $(".ch-kp-fiftySix #t_r_content tr td").attr("width","100px");
                   /* for(var i=0;i<$(".ch-kp-fiftySix #t_r_content tr").length;i++){
                    	$(".ch-kp-fiftySix #t_r_content tr").eq(i).find("td:last-child").attr("width","");
                    }*/
                    //$(".ch-kp-fiftySix #t_r_content tr").css("width","100px");
                    //console.log($(".ch-kp-fiftySix table:eq(0) tr:eq(0) th:last-child").attr("rowspan"))
                    //if($(".ch-kp-fiftySix table:eq(0) tr:eq(0) th:last-child").attr("rowspan")==data[3].lineNum){
                        //$(".ch-kp-fiftySix table:eq(0) tr").eq(0).find("th:last-child").attr("width","");
                    //}

                    
                    
                }
                $(".Mask").hide();
            },
            error: function () {
                alert("数据请求失败!");
                $(".Mask").hide();
            }
        });
        //第2张表下拉加载
        item = 20;
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
                                html+="<td>"+CheckNull(val)+"</td>";
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
        });
    }


})