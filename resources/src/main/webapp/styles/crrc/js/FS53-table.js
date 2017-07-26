//点击查询按钮，弹出查询框
var search1=new multiSearch3($(".btn1"),"reportFS53/gzlx",null,null);//故障类别
//控制表格高度的显示
var heights = $(window).height();
$(".ch-kp-53").height((heights - 150) + "px");
//点击收起按钮
$(".retract").off("click").on("click", function () {
    if ($(".container form").css("display") == "block") {
        $(".container form").slideUp();
        $(this).text("展开  ∨");
        var heights = $(window).height();
        $(".ch-kp-53").animate({
            "height": (heights - 100) + "px"
        });
    } else {
        $(".container form").slideDown();
        $(this).text("收起  ∧");
        var heights = $(window).height();
        $(".ch-kp-53").height((heights - 150) + ("px"));
    }
});
//采购入库的表格
$("#submit").off("click").on("click", function () {
    $(".notSearch").empty();
    var array1 = [];
    if ($(".gzType").val() != "") {
        array1 = $(".gzType").val().split(",");//故障类别
    }
    var txt1=$("#yearInput").val();
    if(txt1==""){
    	alert("年份必选");
    	return;
    }
     reportJson = {
        "gzlx": array1, //故障类别
        "yearInput":txt1
    }
    reportJson = JSON.stringify(reportJson);
    /*$(".export").on("click", function () {
        reportJson = {
        	gzlx: array1.join(','), //故障类别
        	 yearInput:txt1,
            isExport: true
        }
        downloadFile(reportJson, ctx + "/crrc/reportFS53/downloadFile");
    });*/
    $(".Mask").show();
    $.ajax({
        url: ctx + "/crrc/reportFS53/result",
        data: reportJson,
        dataType: "json",
        contentType: "application/json;charset=utf-8",
        type: "post",
        success: function (data) {
            if ('300' === data.statusCode || '301' === data.statusCode) {
                alert(data.message);
                $(".Mask").hide();
                $(".ch-kp-53").html('');
                return true;
            }
            if (jQuery.isEmptyObject(data)) {
                $(".notSearch").next("div").hide();
                $(".notSearch").text("未查询到数据");
            } else {
                $(".notSearch").next("div").show();
                var str = "",arrs=[];
                str="<table><tr>"
                $.each(data.title,function(k,v){
                	if(k==0){
                		str+="<th width='150px'>"+v+"</th>";  
                	}else if(k==1){
                		str+="<th width='100px'>"+v+"</th>";  
                	}else{
                		str+="<th width='50px'>"+v+"</th>";  
                	}            	   	
                });
                str+="</tr>";
                $.each(data.content,function(k,v){
                	$.each(v,function(key,val){
                		str+="<tr>";
                		$.each(val,function(m,n){
                			if(k==0){
                        		str+="<td width='150px'>"+n+"</td>";  
                        	}else if(k==1){
                        		str+="<td width='100px'>"+n+"</td>";  
                        	}else{
                        		str+="<td width='50px'>"+n+"</td>";  
                        	}
                		});
                		str+="</tr>";
                	});
                });
                str+="<tr>";
                $.each(data.total,function(k,v){
                	str+="<td>"+v+"</td>";
                });
                str+="</tr></table>";
                $(".ch-kp-53").html(str);
                var width_53=$(".ch-kp-53 table").width()+10;
                $(".ch-kp-53").css("width",width_53+"px");
                trBg($(".ch-kp-53 table"));
                $(".table-name").css("width",$(".ch-kp-53 table").width()+"px");
            }
            $(".Mask").hide();            
        },
        error: function () {
            alert("数据请求失败!");
            $(".Mask").hide();
        }
    });
});