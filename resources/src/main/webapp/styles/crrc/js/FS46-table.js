//点击查询按钮，弹出查询框
var search1=new multiSearch3($(".btn1"),"reportFS46/trainmodel",null,null);//车型
//控制表格高度的显示
var heights = $(window).height();
$(".ch-kp-fortySix .t_r_content").height((heights - 170) + "px");
//点击收起按钮
$(".retract").off("click").on("click", function () {
    if ($(".container form").css("display") == "block") {
        $(".container form").slideUp();
        $(this).text("展开  ∨");
        var heights = $(window).height();
        $(".ch-kp-fortySix .t_r_content").animate({
            "height": (heights - 125) + "px"
        });
    } else {
        $(".container form").slideDown();
        $(this).text("收起  ∧");
        var heights = $(window).height();
        $(".ch-kp-fortySix .t_r_content").height((heights - 170) + ("px"));
    }
})
var arr = [], arrTk = [];
//采购入库的表格
$("#submit").off("click").on("click", function () {
    //var blog = true;
    $(".notSearch").empty();
    //$("#show").text("数据正在加载中");
    //$(".hideCol").click();
    var arrs = [], array1 = [], array2 = [], array3 = [], array4 = [], array5 = [];
    if ($(".carType").val() != "") {
        array1 = $(".carType").val().split(",");//车型
        /*if(blog){
            blog = compareInput(array1,plant,"工厂");
        }*/
    }
    var reportJson={};
    /*if(!blog){
        return;
    }*/
    $(".Mask").show();
     reportJson = {
         "trainmodel": array1, //车型
         "isExport": false
    }
    reportJson = JSON.stringify(reportJson);
    $(".export").on("click", function () {
        reportJson = {
            trainmodel: array1.join(','), //车型
            isExport: true
        }
        downloadFile(reportJson, ctx + "/crrc/reportFS46/downloadFile");
    });
    $.ajax({
        url: ctx + "/crrc/reportFS46/result",
        data: reportJson,
        dataType: "json",
        contentType: "application/json;charset=utf-8",
        type: "post",
        success: function (data) {
            if ('300' === data.statusCode || '301' === data.statusCode) {
                alert(data.message);
                $(".Mask").hide();
                $(".ch-kp-fortySix table:eq(1)").html('');
                return true;
            }
            if (data.length == 0) {
                $(".notSearch").next("div").hide();
                $(".notSearch").text("未查询到数据");
            } else {
                $(".notSearch").next("div").show();
                /*if (data.length < 20) {
                    $("#show").html("数据加载完成");
                } else {
                    $("#show").html("");
                }*/
                var str = "";
                $.each(data, function (key, val) {
                	var sss=val.motoramount;
                	if(sss!="/"){
                		sss=parseFloat(sss).toFixed(3);
                	}
                    str+= "<tr>"
                            + "<td style='width:80px'>" + val.trainmodel + "</td>"
                            + "<td style='width:100px'>" + val.orgnization + "</td>"
                            + "<td style='width:110px;text-align:right;'>" + parseFloat(val.trainamount).toFixed(3) + "</td>"
                            + "<td style='width:150px;text-align:right;'>" + sss + "</td>"
                            + "<td style='text-align: right;'>" + parseFloat(val.motoramountsum).toFixed(3) + "</td>"
                        +"</tr>";
                });

                $(".ch-kp-fortySix table:eq(1)").html(str);
                trBg($(".ch-kp-fortySix table:eq(1)"))
            }
            $(".Mask").hide();
            
        },
        error: function () {
            alert("数据请求失败!");
            $(".Mask").hide();
        }
    })
})

