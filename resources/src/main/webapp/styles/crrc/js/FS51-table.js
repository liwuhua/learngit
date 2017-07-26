//点击查询按钮，弹出查询框
var search1=new multiSearch3($(".btn1"),"reportFS51/productModel",null,null);//产品型号
//控制表格高度的显示
var heights = $(window).height();
$(".ch-kp-51").height((heights - 150) + "px");
//点击收起按钮
$(".retract").off("click").on("click", function () {
    if ($(".container form").css("display") == "block") {
        $(".container form").slideUp();
        $(this).text("展开  ∨");
        var heights = $(window).height();
        $(".ch-kp-51").animate({
            "height": (heights - 100) + "px"
        });
    } else {
        $(".container form").slideDown();
        $(this).text("收起  ∧");
        var heights = $(window).height();
        $(".ch-kp-51").height((heights - 150) + ("px"));
    }
});
//采购入库的表格
$("#submit").off("click").on("click", function () {
    $(".notSearch").empty();
    var array1 = [];
    if ($(".productModel").val() != "") {
        array1 = $(".productModel").val().split(",");//产品型号
    }
     reportJson = {
        "productModel": array1 //产品型号
        //"isExport": false
    }
    reportJson = JSON.stringify(reportJson);
    $(".export").on("click", function () {
        reportJson = {
        	productModel: array1.join(','), //产品型号
            isExport: true
        }
        downloadFile(reportJson, ctx + "/crrc/reportFS51/downloadFile");
    });
    $(".Mask").show();
    $.ajax({
        url: ctx + "/crrc/reportFS51/result",
        data: reportJson,
        dataType: "json",
        contentType: "application/json;charset=utf-8",
        type: "post",
        success: function (data) {
            if ('300' === data.statusCode || '301' === data.statusCode) {
                alert(data.message);
                $(".Mask").hide();
                $(".ch-kp-51").html('');
                return true;
            }
            if (jQuery.isEmptyObject(data)) {
                $(".notSearch").next("div").hide();
                $(".notSearch").text("未查询到数据");
            } else {
                $(".notSearch").next("div").show();
                var str = "",arrs=[];
                str="<div><table>"
                $.each(data,function(k,v){
                	str+="<tr>"
                	for(var i=0;i<v.title.length;i++){
                		str+="<th>"+v.title[i]+"</th>"
                	}
                	str+="</tr>"
                	$.each(v.content,function(key,val){
                		str+="<tr>"
                		$.each(val,function(s,t){
                			str+="<td>"+t+"</td>";
                		});
                		str+="</tr>";
                	});
                	
                });
                str+="</table></div>"
                $(".ch-kp-51").html(str);
                trBg($(".ch-kp-51 table"))
            }
            $(".Mask").hide();            
        },
        error: function () {
            alert("数据请求失败!");
            $(".Mask").hide();
        }
    });
});