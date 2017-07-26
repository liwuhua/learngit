//点击查询按钮，弹出查询框 
var search1 = new multiSearch3($(".btn1"), "reportFS18/vbeln", null, null);//销售订单 
var search2 = new multiSearch($(".btn2"), "tmaterial", 0, 10);//物料编码 
var search3 = new multiSearch2($(".btn3"), "reportFS18/saorderFactory", null, null);//销售订单工厂 
var search4 = new multiSearch2($(".btn4"), "reportFS18/proFactory", null, null);//生产工厂 
var search5 = new multiSearch1($(".btn5"), "reportFS18/produOrderType", null, null);//生产订单类型 
//收起展开控制高度
var heights = $(window).height();
var heights_f = $(".container .col-sm-12 form").height();
$(".ch-kp-eighteen #t_r_content").css("height", (heights - heights_f - 460) + "px");
$(".retract").off("click").on("click", function () {
    if ($(".container form").css("display") == "block") {
        $(".container form").slideUp();
        $(this).text("展开  ∨");
        var heights = $(window).height();
        $(".ch-kp-eighteen #t_r_content").animate({
            "height": (heights - 450) + "px"
        });
    } else {
        $(".container form").slideDown();
        $(this).text("收起  ∧");
        var heights = $(window).height();
        $(".ch-kp-eighteen #t_r_content").css("height", (heights - heights_f - 460) + "px");
    }
});


$("#submit").off("click").on("click", function () {
    var blog = true;
    $(".notSearch").empty();
    $("#show").html("");
    var arrs = [], array1 = [], array2 = [], array3 = [], array4 = [], array5 = [], array6 = [], array7 = [];
    if ($(".vbelnValue").val() != "") {
        array1 = $(".vbelnValue").val().split(",");//销售订单
    }
    if ($(".material").val() != "" && $(".material").val().indexOf(";") != -1) {
        arrs = $(".material").val().split(";");
        array2 = arrs[0].split(",");//物料多值
        array6 = arrs[1].split(",");//物料多区间
    } else if ($(".material").val() != "" && $(".material").val().indexOf(";") == -1 && $(".material").val().indexOf(":") != -1) {
        array2 = [];
        array6 = $(".material").val().split(",");
    } else if ($(".material").val() != "" && $(".material").val().indexOf(";") == -1 && $(".material").val().indexOf(":") == -1) {
        array2 = $(".material").val().split(",");
        array6 = [];
    } else if ($(".material").val() == "") {
        array2 = [];
        array6 = [];
    }
    if ($(".werksValue").val() != "") {
        array3 = $(".werksValue").val().split(",");//销售订单工厂 
        if (blog) {
            blog = compareInput(array3, plant, "销售订单工厂");
        }
    }
    if ($(".dwerkValue").val() != "") {
        array4 = $(".dwerkValue").val().split(",");//生产工厂
        if (blog) {
            blog = compareInput(array4, plant, "生产工厂");
        }
    } else {
        array4 = ["1000"];
    }
    if ($(".dauatValue").val() != "") {
        array5 = $(".dauatValue").val().split(",");//生产订单类型
    } else {
        array5 = ["ZP01"];
    }
    var txt1 = $("#startTime").val().replace(/-/g, "");//销售订单交货日期
    var txt2 = $("#endTime").val().replace(/-/g, "");

    var txt3 = $("#startTimeTwo").val().replace(/-/g, "");//计划完成日期
    var txt4 = $("#endTimeTwo").val().replace(/-/g, "");

    var txt5 = $("#startTimeThree").val().replace(/-/g, "");//生产订单收获入库日期
    var txt6 = $("#endTimeThree").val().replace(/-/g, "");
    for (var i = 0; i < 3; i++) {
        if ($(".yujing input:eq(" + i + ")").prop("checked") == true) {
            array7.push($(".yujing input:eq(" + i + ")").attr("data-show"));
        }
    }
    var reportJson = {};
    var item = 0;
    var reportJson = {
        "vbelnValue": array1, //销售订单
        "matnrInterval": array2, //物料编码
        "werksValue": array3, //销售订单工厂 
        "dwerkValue": array4, //生产工厂  
        "dauatValue": array5, //生产订单类型
        "startTime": txt1,
        "endTime": txt2, //销售订单交货日期
        "startTimeTwo": txt3,
        "endTimeTwo": txt4, //计划完成日期
        "startTimeThree": txt5,
        "endTimeThree": txt6, //生产订单收获入库日期
        "twoValue": array7, //预警状态
        "startitem": item, //分页
        "pageitem": 20
    };
    reportJson = JSON.stringify(reportJson);
    if (!blog) {
        return;
    }
    if (parseInt(txt1) > parseInt(txt2) || parseInt(txt3) > parseInt(txt4) || parseInt(txt5) > parseInt(txt6)) {
        alert("后一个日期必须大于或等于前一个日期");
    } else if (array7.length == 0) {
        alert("预警状态必选一个");
    } else {
        $(".Mask").show();
        $.ajax({
            url: ctx + "/crrc/reportFS18/result",
            data: reportJson,
            dataType: "json",
            contentType: "application/json;charset=utf-8",
            type: "post",
            success: function (data) {
                $(".ch-kp-eighteen table:eq(1)").html("")
                var str1 = "";
                if ('300' === data.statusCode || '301' === data.statusCode) {
                    alert(data.message);
                    $(".Mask").hide();
                    $(".ch-kp-eighteen table:eq(1)").html('');
                    return true;
                } else if (data.marorderdeliver.length == 0) {
                    $(".notSearch").next("div").hide();
                    $(".notSearch").text("未查询到数据");
                    $(".twentyBox").hide();
                    $(".twentyThreeTable").hide();
                    $(".Mask").hide();
                } else {
                    $(".notSearch").next("div").show();
                    if(data.marorderdeliver.length<20){
                    	$("#show").html("数据加载完成");
                    }
                    var str1 = "";
                    $.each(data.marorderdeliver, function (k, v) {
                        //红色  1   黄色  2    蓝色   0
                        var val;
                        if (v.SHOWMARK == 1) {
                            val = "rgb(224,45,57)";
                        } else if (v.SHOWMARK == 2) {
                            val = "rgb(243,131,55)";
                        } else if (v.SHOWMARK == 0) {
                            val = "rgb(43,123,191)";
                        }
                        str1 += "<tr class='trs' style='background-color:" + val + ";'>"
                                + "<td class='VBELN' style='width:90px'>" + v.VBELN + "<input type='hidden' name='field＿name' value='" + v.RSNUM + "' class='send18'></td>"
                                + "<td class='POSNR' style='width:60px'>" + v.POSNR + "</td>"
                                + "<td class='WERKS' style='width:50px'>" + v.WERKS + "</td>"
                                + "<td class='AUFNR' style='width:100px'>" + v.AUFNR + "</td>"
                                + "<td class='MATNR' style='width:120px'>" + v.MATNR + "</td>"
                                + "<td class='ARKTX' style='width:350px'>" + v.ARKTX + "</td>"
                                + "<td class='DWERK' style='width:60px'>" + v.DWERK + "</td>"
                                + "<td class='PSMNG' style='width:90px;text-align:right;'>" + parseFloat(v.PSMNG).toFixed(3) + "</td>"
                                + "<td class='SALESERDAT' style='width:120px'>" + v.SALESERDAT + "</td>"
                                + "<td class='EDATU' style='width:120px'>" + v.EDATU + "</td>"
                                + "<td class='PRODUCEERDAT' style='width:120px'>" + v.PRODUCEERDAT + "</td>"
                                + "<td class='FTRMI' style='width:120px'>" + v.FTRMI + "</td>"
                                + "<td class='GLTRP' style='width:90px'>" + v.GLTRP + "</td>"
                                + "<td class='PRODUCECOUNT' style='width:80px;text-align:right;'>" + parseFloat(v.PRODUCECOUNT).toFixed(3) + "</td>"
                                + "<td class='ORDERWAREEDATE'>" + v.ORDERWAREEDATE + "</td>"
                                + "</tr>";
                    });
                    $(".ch-kp-eighteen table:eq(1)").html(str1);
                    $("#t_r_content tr:eq(0)").css({
                        "opacity": "0.6",
                        "filter": "alpha(opacity=60)"
                    });
                    $("#t_r_content tr:eq(0) td").css({
                        "opacity": "0.6",
                        "filter": "alpha(opacity=60)"
                    });
                    var num1 = $("#t_r_content tr:eq(0)").find("td").eq(0).text();
                    var num2 = $("#t_r_content tr:eq(0)").find("td").eq(1).text();
                    var num3 = $("#t_r_content tr:eq(0)").find("td").eq(3).text();
                    var num4 = $("#t_r_content tr:eq(0)").find("td").eq(4).text();
                    dataAjax(num1, num2, num3, num4);
                    /*var num1 = $("#t_r_content tr:eq(0)").find("td").eq(0).text();
                    var num2 = $("#t_r_content tr:eq(0)").find("td").eq(1).text();
                    var num3 = $("#t_r_content tr:eq(0)").find("td").eq(4).text();
                    var num4 = $("#t_r_content tr:eq(0)").find("td").eq(6).text();
                    var num5 = $("#t_r_content tr:eq(0)").find("input").val();*/
                    //dataAjax(num1, num2, num3, num4, num5);
                    lineClick();
                }
            },
            error: function () {
                alert("error1");
                $(".Mask").hide();
            }
        })
        item = 20;
        var b = true;
        $(".ch-kp-eighteen .t_r_content").unbind("scroll").bind("scroll", function () {
            var fold = $(".t_r_content").height() + $(".t_r_content")[0].scrollTop;
            if (b && fold >= $(".t_r_content")[0].scrollHeight) {
                b = false;
                if ($("#show").html()=="数据加载完成") {
                    $(".Mask").hide();
                } else {
                    $(".Mask").show();
                }
                reportJson = {
                    "vbelnValue": array1, //销售订单
                    "matnrInterval": array2, //物料编码
                    "werksValue": array3, //销售订单工厂 
                    "dwerkValue": array4, //生产工厂  
                    "dauatValue": array5, //生产订单类型
                    "startTime": txt1,
                    "endTime": txt2, //销售订单交货日期
                    "startTimeTwo": txt3,
                    "endTimeTwo": txt4, //计划完成日期
                    "startTimeThree": txt5,
                    "endTimeThree": txt6, //生产订单收获入库日期
                    "twoValue": array7, //预警状态
                    "startitem": item, //分页
                    "pageitem": 20
                }
                reportJson = JSON.stringify(reportJson);
                if ($("#show").text() != "数据加载完成") {
                    $.ajax({
                        url: ctx + "/crrc/reportFS18/result",
                        data: reportJson,
                        dataType: "json",
                        contentType: "application/json;charset=utf-8",
                        type: "post",
                        success: function (data) {
                            if (data.marorderdeliver.length < 20) {
                                $("#show").html("数据加载完成");
                                b = false;
                            }
                            var str = "";
                            $.each(data.marorderdeliver, function (k, v) {
                                //红色  1   黄色  2    蓝色   0
                                var val;
                                if (v.SHOWMARK == 1) {
                                    val = "rgb(224,45,57)";
                                } else if (v.SHOWMARK == 2) {
                                    val = "rgb(243,131,55)";
                                } else if (v.SHOWMARK == 0) {
                                    val = "rgb(43,123,191)";
                                }
                                str += "<tr class='trs' style='background-color:" + val + ";'>"
                                        + "<td class='VBELN' style='width:90px'>" + v.VBELN + "<input type='hidden' name='field＿name' value='" + v.RSNUM + "' class='send18'></td>"
                                        + "<td class='POSNR' style='width:60px'>" + v.POSNR + "</td>"     //---
                                        + "<td class='WERKS' style='width:50px'>" + v.WERKS + "</td>"
                                        + "<td class='AUFNR' style='width:100px'>" + v.AUFNR + "</td>"   //---
                                        + "<td class='MATNR' style='width:120px'>" + v.MATNR + "</td>"  //---
                                        + "<td class='ARKTX' style='width:350px'>" + v.ARKTX + "</td>"
                                        + "<td class='DWERK' style='width:60px'>" + v.DWERK + "</td>"
                                        + "<td class='PSMNG' style='width:90px;text-align:right;'>" + parseFloat(v.PSMNG).toFixed(3) + "</td>"
                                        + "<td class='SALESERDAT' style='width:120px'>" + v.SALESERDAT + "</td>"
                                        + "<td class='EDATU' style='width:120px'>" + v.EDATU + "</td>"
                                        + "<td class='PRODUCEERDAT' style='width:120px'>" + v.PRODUCEERDAT + "</td>"
                                        + "<td class='FTRMI' style='width:120px'>" + v.FTRMI + "</td>"
                                        + "<td class='GLTRP' style='width:90px'>" + v.GLTRP + "</td>"
                                        + "<td class='PRODUCECOUNT' style='width:80px;text-align:right;'>" + parseFloat(v.PRODUCECOUNT).toFixed(3) + "</td>"
                                        + "<td class='ORDERWAREEDATE'>" + v.ORDERWAREEDATE + "</td>"
                                        + "</tr>";
                            });
                            item = item + 20;
                            $(".ch-kp-eighteen table:eq(1)").append(str);
                            lineClick();
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
        //点击trs，相应的行的边框增加阴影，将相应行的销售订单，销售订单行项传到后台，相应的20出来
        function lineClick() {
            $(".ch-kp-eighteen #t_r_content").off("dblclick").on("dblclick", ".trs", function () {
                $(".Mask").show();
                var _this = $(this);
                _this.css({
                    "opacity": "0.6"
                });
                _this.children("td").css({
                    "filter": "alpha(opacity=60)"
                });
                _this.siblings().css({
                    "opacity": "1"
                });
                _this.siblings().children("td").css({
                    "filter": "alpha(opacity=100)"
                });
                var num1 = $(this).find("td").eq(0).text();
                var num2 = $(this).find("td").eq(1).text();
                var num3 = $(this).find("td").eq(3).text();
                var num4 = $(this).find("td").eq(4).text();
                dataAjax(num1, num2, num3, num4);
            })
        }
    }
});
//20
function dataAjax(num1, num2, num3, num4) {
    $(".ch-kp-twenty .t_r_title table").html("");
    $(".ch-kp-twenty #t_r_t2 table").html("");
    $.ajax({
    	url: ctx + "/crrc/reportFS20/result?kdauf="+num1+"&kdpos="+num2+"&aufnr="+num3+"&matnr="+num4,
        dataType: "json",
        type: "post",
        success: function (data) {
            if ('300' === data.statusCode || '301' === data.statusCode) {
                alert(data.message);
                $(".Mask").hide();
                $(".twentyBox").html('');
                return true;
            } else {
                $(".twentyBox").show();
                var arrSum = [];
                if (jQuery.isEmptyObject(data)) {
                    alert("所选销售订单与订单行项目查询不到E件数据");
                    $(".twentyTable #cl_freeze2 table").html("");
                    $(".ch-kp-twenty #t_r_content2 table").html("");
                    $(".twentyTable .t_l_t table").html("");
                    $(".ch-kp-twenty .t_r_title table").html("");
                    $(".twentyThreeTable #cl_freeze3 table").html("");
                    $(".ch-kp-twentyThree #t_r_content3 table").html("");
                    $(".twentyThreeTable .t_l_t table").html("");
                    $(".ch-kp-twentyThree .t_r_title table").html("");
                    $(".Mask").hide();
                    return;
                }
                var textMax = data.max.slice(0, 4) + "-" + data.max.slice(4, 6) + "-" + data.max.slice(6, 8);
                var textMin = data.min.slice(0, 4) + "-" + data.min.slice(4, 6) + "-" + data.min.slice(6, 8);
                arrSum.push(textMax);
                arrSum.push(textMin);
                var arr = getDays(arrSum[1], arrSum[0]);
                var arrNew = arr.unique3();
                var arrYear = [], arrY = [], arrY1 = [], arrY2 = [];
                for (var i = 0; i < arrNew.length; i++) {
                    var a = arrNew[i].replace(/-/g, "").slice(0, 6);
                    arrYear.push(a);
                }
                var res = [];
                arrYear.sort();
                for (var i = 0; i < arrYear.length; ) {
                    var count = 0;
                    for (var j = i; j < arrYear.length; j++) {
                        if (arrYear[i] == arrYear[j]) {
                            count++;
                        }
                    }
                    res.push([arrYear[i], count]);
                    i += count;
                }
                //res 二维数维中保存了 值和值的重复数
                for (var i = 0; i < res.length; i++)
                {
                    arrY1.push(res[i][0]);
                    arrY2.push(res[i][1]);
                    /*console.log("值:"+res[i][0]+" 重复次数:"+res[i][1]+"<br/>"); */
                }
                var len = arrNew.length;
                var str_r_t = "", str_l_t = "", str_l_b = "", str_r_b = "";
                var width20 = len * 25;
                var width20_t = width20 + 20;
                $(".ch-kp-twenty table").css("width", width20 + "px");
                $(".ch-kp-twenty .t_r_title").css("width", width20_t + "px");
                //左侧头部
                str_l_t = "<tr>"
                        + "<th rowspan='2' width='95px' class='AUFNR'>生产订单</th>"//----
                        + "<th rowspan='2' width='100px' class='MATNR'>物料</th>"//----
                        + "<th rowspan='2' width='330px' class='TXTMD'>物料描述</th>"
                        + "<th rowspan='2' width='60px' class='PSMNG'>订单数量</th>"//----
                        + "<th rowspan='2' width='60px' class='WEMNG'>收货数量</th>"//----
                        + "<th rowspan='2' width='80px' class='GSTRP'>计划开始日期</th>"//----
                        + "<th rowspan='2' width='80px' class='GLTRP'>计划完成日期</th>"//----
                        + "<th rowspan='2' width='80px' class='LTRMI'>实际完成日期</th>"//---
                        + "</tr>"
                $(".twentyTable .t_l_t table").html(str_l_t);
                //右侧头部
                str_r_t = "<tr>";
                var i = 0;
                for (var j = 0; j < arrY2.length; j++) {
                    i < arrY1.length;
                    str_r_t += "<th colspan='" + arrY2[j] + "'>" + arrY1[i].slice(4) + "</th>";
                    i++;
                }
                str_r_t += "</tr><tr>";
                for (var i = 0; i < len; i++) {
                    str_r_t += "<th width='25px'>" + arrNew[i].slice(8) + "</th>";
                }
                str_r_t += "</tr>";
                $(".ch-kp-twenty .t_r_title table").html(str_r_t);
                $.each(data.data, function (k, v) {
                    var textSt = v.FTRMI.slice(0, 4) + "-" + v.FTRMI.slice(4, 6) + "-" + v.FTRMI.slice(6, 8);
                    var textEd = v.GLTRP.slice(0, 4) + "-" + v.GLTRP.slice(4, 6) + "-" + v.GLTRP.slice(6, 8);
                    var indSt = arrNew.indexOf(textSt);
                    var indEd = arrNew.indexOf(textEd);
                    var num = indEd - indSt + 1;
                    var numEd = len - indEd - 1;
                    //左侧身体部分
                    str_l_b += "<tr style='position:relative;'>"
                            + "<td width='95px' class='AUFNR'>" + v.AUFNR + "</td>"
                            + "<td width='100px' class='MATNR'>" + v.MATNR + "</td>"
                            + "<td width='330px' class='TXTMD'><div style='white-space: nowrap;'>"+v.TXTMD+"</div></td>"
                            + "<td width='60px' class='PSMNG' style='text-align:right;'>" + parseFloat(v.PSMNG).toFixed(3) + "</td>"
                            + "<td width='60px' class='WEMNG' style='text-align:right;'>" + parseFloat(v.WEMNG).toFixed(3) + "</td>"
                            + "<td width='80px' class='FTRMI'>" + v.FTRMI + "</td>"
                            + "<td width='80px' class='GLTRP'>" + v.GLTRP + "</td>"
                            + "<td width='80px' class='LTRMI'>" + v.LTRMI + "</td>"
                            + "</tr>";
                    //右侧身体部分
                    str_r_b += "<tr>"
                    for (var n = 0; n < indSt; n++) {
                        str_r_b += "<td width='25px'></td>";
                    }
                    //判断标识，根据标识设置颜色  1 红色  2 黄色  3 绿色  0  蓝色
                    if (v.STAT== 1) {
                        for (var j = 0; j < num; j++) {
                            str_r_b += "<td width='25px' class='colorRed20 colorLine20'><div style='background-color:#e02d39;width:23px;height:8px;'><input type='hidden' name='field＿name' value='" + v.AUFNR + "' class='send20'></div></td>";
                        }
                    } else if (v.STAT == 2) {
                        for (var j = 0; j < num; j++) {
                            str_r_b += "<td width='25px' class='colorYellow20 colorLine20'><div style='background-color:#f38337;width:23px;height:8px;'><input type='hidden' name='field＿name' value='" + v.AUFNR + "' class='send20'></div></td>";
                        }
                    } else if (v.STAT == 3) {
                        for (var j = 0; j < num; j++) {
                            str_r_b += "<td width='25px' class='colorGreen20 colorLine20'><div style='background-color:#27ae60;width:23px;height:8px;'><input type='hidden' name='field＿name' value='" + v.AUFNR + "' class='send20'></div></td>";
                        }
                    } else if (v.STAT == 0) {
                        for (var j = 0; j < num; j++) {
                            str_r_b += "<td width='25px' class='colorBlue20 colorLine20'><div style='background-color:#2b7bbf;width:23px;height:8px;'><input type='hidden' name='field＿name' value='" + v.AUFNR + "' class='send20'></div></td>";
                        }
                    }
                    for (var m = 0; m < numEd; m++) {
                        str_r_b += "<td width='25px'></td>";
                    }
                    str_r_b += "</tr>";
                });
                $(".twentyTable #cl_freeze2 table").html(str_l_b);
                $(".ch-kp-twenty #t_r_content2 table").html(str_r_b);
                $(".ch-kp-twenty #t_r_t2 table tr th:last-child").removeAttr("width");
                $(".ch-kp-twenty #t_r_content2 table tr td:last-child").removeAttr("width");
                $(".ch-kp-twenty #t_r_t2 table").after("<div width='20px' height='20px' style='display:inline-block;'></div>");
                $(".ch-kp-twenty").css("width", $(".twentyTable").innerWidth() - 890 + "px");
                /* $(".twentyTable #cl_freeze2 table tr:eq(0) td:eq(0)").css("color","#c70019");*/
                $(".twentyTable #cl_freeze2 table tr:eq(0)").css("background-color", "#ededed");
                $(".twentyTable #cl_freeze2 table tr:eq(0) div").css("background-color", "#ededed");
                /* $(".twentyTable #cl_freeze2 table tr td:eq(2)").on({
                 "mouseover":function(){
                 str="<div class='nr' style='position:absolute;left:345px;z-index:1;background-color:#fff;'>"+$(this).text()+"</div>"
                 $(this).parent("tr").append(str);
                 },
                 "mouseout":function(){
                 $(this).parent("tr").find(".nr").remove();
                 }
                 });*/
                if ($("#t_r_content2")[0].scrollWidth > $("#t_r_content2")[0].clientWidth || $("#cl_freeze2")[0].offsetWidth > $("#cl_freeze2")[0].clientWidth) {
                    $("#cl_freeze2").css("height", "127px");
                } else {
                    $("#cl_freeze2").css("height", "143px");
                }
                var num3 = $(".send20").val();
                dataAjax23(num3);
                $("#t_r_content2 table").off("dblclick").on("dblclick", "tr", function () {
                    $(".Mask").show();
                    var ind = $(this).index();
                    $("#cl_freeze2 table tr").css("background-color", "#fff");
                    $("#cl_freeze2 table tr div").css("background-color", "#fff");
                    $("#cl_freeze2 table tr").eq(ind).css("background-color", "#ededed");
                    $("#cl_freeze2 table tr").eq(ind).find("div").css("background-color", "#ededed");
                    $(".ch-kp-twentyThree table:eq(1)").html("");
                    $(".ch-kp-twentyThree #t_r_t3 table").html("");
                    var num3 = $(this).find("input").val();
                    dataAjax23(num3);
                });
                $("#cl_freeze2 table").off("dblclick").on("dblclick", "tr", function () {
                    $(".Mask").show();
                    $(".ch-kp-twentyThree table:eq(1)").html("");
                    $(".ch-kp-twentyThree #t_r_t3 table").html("");
                    /*$("#cl_freeze2 table tr td").css("color","#262626");*/
                    $("#cl_freeze2 table tr").css("background-color", "#fff");
                    $("#cl_freeze2 table tr div").css("background-color", "#fff")
                    var ind = $(this).index();
                    $(this).css("background-color", "#ededed");
                    $(this).find("div").css("background-color", "#ededed")
                    //$(this).siblings().find("td").eq(0).css("color","#000000");
                    var num3 = $("#t_r_content2 table tr").eq(ind).find("input").val();
                    dataAjax23(num3);
                });
                $(".Mask").hide();
            }
        },
        error: function () {
            alert("error20")
        }
    });

}
//23
function dataAjax23(num3) {
    $(".ch-kp-twentyThree table:eq(1)").html("");
    $(".ch-kp-twentyThree #t_r_t3 table").html("");
    var jsonTwentyThree = {
        "AUFNR": num3
    };
    $.ajax({
        url: ctx + "/crrc/reportFS23/result",
        data: jsonTwentyThree,
        dataType: "json",
        type: "post",
        success: function (data) {
            if (data.periodexpense.length == 0) {
                $(".Mask").hide();
                alert("所选生产订单查询不到E件数据");
                $(".twentyThreeTable .t_left #cl_freeze3").html('');
                $(".twentyThreeTable .t_left .t_l_t table").html('');
                $(".ch-kp-twentyThree table:eq(1)").html('');
                $(".ch-kp-twentyThree #t_r_t3 table").html('');
                return true;
            }
            if ('300' === data.statusCode || '301' === data.statusCode) {
                alert(data.message);
                $(".Mask").hide();
                $(".twentyThreeTable").html('');
                return true;
            } else {
                $(".twentyThreeTable").show();
                var arrSum = [];
                $.each(data.periodexpense, function (k, v) {
                    var textMax = v.MAXSSEDD.slice(0, 4) + "-" + v.MAXSSEDD.slice(4, 6) + "-" + v.MAXSSEDD.slice(6, 8);
                    var textMin = v.MINSSAVD.slice(0, 4) + "-" + v.MINSSAVD.slice(4, 6) + "-" + v.MINSSAVD.slice(6, 8);
                    arrSum.push(textMax);
                    arrSum.push(textMin)
                });
                var arr = getDays(arrSum[1], arrSum[0]);
                var arrNew = arr.unique3();
                var arrYear = [], arrY = [], arrY1 = [], arrY2 = [];
                for (var i = 0; i < arrNew.length; i++) {
                    var a = arrNew[i].replace(/-/g, "").slice(0, 6);
                    arrYear.push(a);
                }
                var res = [];
                arrYear.sort();
                for (var i = 0; i < arrYear.length; ) {
                    var count = 0;
                    for (var j = i; j < arrYear.length; j++) {

                        if (arrYear[i] == arrYear[j]) {
                            count++;
                        }
                    }
                    res.push([arrYear[i], count]);
                    i += count;
                }
                //res 二维数维中保存了 值和值的重复数
                for (var i = 0; i < res.length; i++)
                {
                    arrY1.push(res[i][0]);
                    arrY2.push(res[i][1]);
                    /*console.log("值:"+res[i][0]+" 重复次数:"+res[i][1]+"<br/>"); */
                }
                var len = arrNew.length;
                var str_l_t = "", str_l_b = "<table>", str_r_b = "", str_r_t = "";
                var width23 = len * 25;
                var width23_t = width23 + 20;
                $(".ch-kp-twentyThree table").css("width", width23 + "px");
                $(".ch-kp-twentyThree .t_r_title").css("width", width23_t + "px");
                //左侧头部
                str_l_t = "<tr height='50px'>"
                        + "<th rowspan='2' width='95px'>生产订单</th>"
                        + "<th rowspan='2' width='70px'>工作中心</th>"
                        + "<th rowspan='2' width='160px'>工作中心描述</th>"
                        + "<th rowspan='2' width='100px'>物料编码</th>"
                        + "<th rowspan='2' width='80px'>工序描述</th>"
                        + "<th rowspan='2' width='80px'>实际完成日期</th>"
                        + "<th rowspan='2' width='70px'>开始日期</th>"
                        + "<th rowspan='2' width='70px'>结束日期</th>"
                        + "<th rowspan='2' width='98.18px'>周期(小时／天)</th>"
                        + "</tr>";
                $(".twentyThreeTable .t_left .t_l_t table").html(str_l_t);
                //右侧头部
                str_r_t = "<tr height='25px'>";
                var i = 0;
                for (var j = 0; j < arrY2.length; j++) {
                    i < arrY1.length;
                    str_r_t += "<th colspan='" + arrY2[j] + "'>" + arrY1[i].slice(4) + "</th>";
                    i++;
                }
                str_r_t += "</tr><tr height='25px'>";
                for (var i = 0; i < len; i++) {
                    str_r_t += "<th width='25px'>" + arrNew[i].slice(8) + "</th>";
                }
                str_r_t += "</tr>";
                $(".ch-kp-twentyThree #t_r_t3 table").html(str_r_t);
                $.each(data.periodexpense, function (k, v) {
                    var textSt = v.SSAVD.slice(0, 4) + "-" + v.SSAVD.slice(4, 6) + "-" + v.SSAVD.slice(6, 8);
                    var textEd = v.SSEDD.slice(0, 4) + "-" + v.SSEDD.slice(4, 6) + "-" + v.SSEDD.slice(6, 8);
                    var indSt = arrNew.indexOf(textSt);
                    var indEd = arrNew.indexOf(textEd);
                    var num = indEd - indSt + 1;
                    var numEd = len - indEd - 1;
                    //左侧身体部分
                    str_l_b += "<tr height='25px' style='position:relative;'>"
                            + "<td width='95px'>" + v.AUFNR + "</td>"
                            + "<td width='70px'>" + v.ARBPL + "</td>"
                            + "<td width='160px'><div style='white-space: nowrap;'>" + v.KTEXT + "</div></td>"
                            + "<td width='100px'>" + v.MATNR + "</td>"
                            + "<td width='80px'><div width='70px' title='" + v.LTXA1 + "'>" + v.LTXA1 + "</div></td>"
                            + "<td width='80px'>" + v.IEAVD + "</td>"
                            + "<td width='70px'>" + v.SSAVD + "</td>"
                            + "<td width='70px'>" + v.SSEDD + "</td>"
                            + "<td width='98.18px'>" + v.PERIOD + "</td>"
                            + "</tr>";
                    //右侧身体部分
                    str_r_b += "<tr height='25px'>"
                    for (var n = 0; n < indSt; n++) {
                        str_r_b += "<td width='25px'></td>";
                    }
                    //判断标识，根据标识设置颜色  1 红色  2 黄色  3 绿色  0  蓝色
                    if (v.SHOWMARK == 1) {
                        for (var j = 0; j < num; j++) {
                            str_r_b += "<td width='25px' class='colorRed23 colorLine23'><div style='background-color:#e02d39;width:23px;height:8px;'><input type='hidden' name='field＿name' value='" + v.AUFNR + "' class='send23'></div></td>";
                        }
                    } else if (v.SHOWMARK == 2) {
                        for (var j = 0; j < num; j++) {
                            str_r_b += "<td width='25px' class='colorYellow23 colorLine23'><div style='background-color:#f38337;width:23px;height:8px;'><input type='hidden' name='field＿name' value='" + v.AUFNR + "' class='send23'></div></td>";
                        }
                    } else if (v.SHOWMARK == 3) {
                        for (var j = 0; j < num; j++) {
                            str_r_b += "<td width='25px'  class='colorGreen23 colorLine23'><div style='background-color:#27ae60;width:23px;height:8px;'><input type='hidden' name='field＿name' value='" + v.AUFNR + "' class='send23'></div></td>";
                        }
                    } else if (v.SHOWMARK == 0) {
                        for (var j = 0; j < num; j++) {
                            str_r_b += "<td width='25px' class='colorBlue23 colorLine23'><div style='background-color:#2b7bbf;width:23px;height:8px;'><input type='hidden' name='field＿name' value='" + v.AUFNR + "' class='send23'></div></td>";
                        }
                    }
                    for (var m = 0; m < numEd; m++) {
                        str_r_b += "<td width='25px'></td>";
                    }
                    str_r_b += "</tr>";
                });
                str_l_b += "</table>";
                $(".twentyThreeTable .t_left #cl_freeze3").html(str_l_b);
                ;
                $(".ch-kp-twentyThree table:eq(1)").html(str_r_b)
                $(".ch-kp-twentyThree #t_r_t3 table tr th:last-child").removeAttr("width");
                $(".ch-kp-twentyThree #t_r_content3 table tr td:last-child").removeAttr("width");
                $(".ch-kp-twentyThree #t_r_t3 table").after("<div width='20px' height='20px' style='display:inline-block;'></div>");
                $(".ch-kp-twentyThree").css("width", $(".twentyTable").innerWidth() - 825 + "px");
                /*$("#cl_freeze3 table tr").find("td").eq(2).on({
                 "mouseover":function(){
                 str="<div class='nr' style='position:absolute;left:345px;z-index:1;background-color:#fff;'>"+$(this).text()+"</div>"
                 $(this).parent("tr").append(str);
                 },
                 "mouseout":function(){
                 $(this).parent("tr").find(".nr").remove();
                 }
                 });*/
                if ($("#t_r_content3")[0].scrollWidth > $("#t_r_content3")[0].clientWidth || $("#cl_freeze3")[0].offsetWidth > $("#cl_freeze3")[0].clientWidth) {
                    $("#cl_freeze3").css("height", "75px");
                } else {
                    $("#cl_freeze3").css("height", "100px");
                }
                //点击23的颜色条，弹出25的表格
                $("#t_r_content3 table").off("dblclick").on("dblclick", "tr", function () {
                    $("#Mask").show();
                    $(".Mask1").show();
                    $(".Mask2").show();
                    $(".table-detail").show();
                    var indx = $(this).index();
                    $("#cl_freeze3 table tr").css("background-color", "#fff");
                    $("#cl_freeze3 table tr div").css("background-color", "#fff");
                    $("#cl_freeze3 table tr").eq(indx).css("background-color", "#ededed");
                    $("#cl_freeze3 table tr").eq(indx).find("div").css("background-color", "#ededed");
                    var num4 = $(this).find("input").val();
                    dataAjax25(num4);
                })
                $("#cl_freeze3 table").off("dblclick").on("dblclick", "tr", function () {
                    $("#Mask").show();
                    $(".Mask1").show();
                    $(".Mask2").show();
                    $(".table-detail").show();
                    var indx = $(this).index();
                    $("#cl_freeze3 table tr").css("background-color", "#fff");
                    $("#cl_freeze3 table tr div").css("background-color", "#fff");
                    $("#cl_freeze3 table tr").eq(indx).css("background-color", "#ededed");
                    $("#cl_freeze3 table tr").eq(indx).find("div").css("background-color", "#ededed");
                    var num4 = $("#t_r_content3 tr").eq(indx).find("input").val();
                    dataAjax25(num4);
                })
                $(".Mask").hide();
            }

        },
        error: function () {
            alert("error23")
        }
    })
}

//25
function dataAjax25(num4) {
    $(".tableBox25 table:eq(1)").html("");
    $.ajax({
        url: ctx + "/crrc/reportFS25/result?aufnr=" + num4,
        dataType: "json",
        type: "post",
        success: function (data) {
            if (data.length == 0) {
                $("#t_r_content4 table").html("未查询到数据");
                $("#t_r_content4 table").css("color", "#262626");
                $(".Mask1").hide();
                $(".Mask2").hide();
            } else {
                var str = "";
                $.each(data, function (k, v) {
                    //红色  1   黄色  2   绿色  3    蓝色   4
                    var BLOGS;
                    if (v.BLOG == 1) {
                        BLOGS = "#e02d39";
                    } else if (v.BLOG == 2) {
                        BLOGS = "#f38337";
                    } else if (v.BLOG == 3) {
                        BLOGS = "#27ae60";
                    } else if (v.BLOG == 4) {
                        BLOGS = "#2b7bbf";
                    }
                    str += "<tr style='background-color:" + BLOGS + "'>"
                            + "<td style='width:90px'>" + v.AUFNR + "</td>"
                            + "<td style='width:60px'>" + v.ZHWCX + "</td>"
                            + "<td style='width:120px'>" + v.MATNR + "</td>"
                            + "<td style='width:350px'>" + v.MAKTX + "</td>"
                            + "<td style='width:90px'>" + v.BDTER + "</td>"
                            + "<td style='width:90px;text-align:right;'>" + parseFloat(v.SUM_BDMNG).toFixed(3) + "</td>"
                            + "<td style='width:90px;text-align:right;'>" + parseFloat(v.LJFLL).toFixed(3) + "</td>"
                            + "<td style='width:90px;text-align:right;'>" + parseFloat(v.YLL).toFixed(3) + "</td>"
                            + "<td style='width:90px'>" + v.LAST_FLTIME + "</td>"
                            + "<td style='width:100px;text-align:right;'>" + parseFloat(v.MNKCL).toFixed(3) + "</td>"
                            + "<td style='width:50px'>" + v.WERKS + "</td>"
                            + "<td>" + v.DISPO + "<input type='hidden' name='field＿name' value='" + v.BLOG + "' class='send25'></td>"
                            + "</tr>";
                })
                $("#t_r_content4 table").css("color", "#fff");
                $(".tableBox25 table:eq(1)").html(str);
                //判断标识，根据标识设置颜色  1 红色  2 黄色  3 绿色  4  蓝色
                var num1 = $("#t_r_content4 tr:eq(0)").find("td").eq(0).text();
                var num2 = $("#t_r_content4 tr:eq(0)").find("td").eq(2).text();
                dataAjax27(num1, num2);
                $("#t_r_content4 tr:eq(0)").css({
                    "opacity": "0.6",
                    "filter": "alpha(opacity=60)"
                });
                $("#t_r_content4 tr:eq(0) td").css({
                    "opacity": "0.6",
                    "filter": "alpha(opacity=60)"
                });
                lineClick25();
            }
            $(".Mask1").hide();

        },
        error: function () {
            alert("error25");
            $(".Mask1").hide();
        }
    })
}

//27
function dataAjax27(num5, num6) {
    $("#t_r_content5 table").html("");
    $.ajax({
        url: ctx + "/crrc/reportFS27/result?aufnr=" + num5 + "&matnr=" + num6,
        dataType: "json",
        type: "post",
        success: function (data) {
            if (data.length == 0) {
                $("#t_r_content5 table").html("未查询到数据");
                $(".Mask2").hide();
            } else {
                var str = "";
                $.each(data, function (k, v) {
                    var orf = v.ORDFM, def = v.DELFM;
                    if (orf != "") {
                        orf = parseFloat(v.ORDFM).toFixed(3);
                    }
                    if (def != "") {
                        def = parseFloat(v.DELFM).toFixed(3);
                    }
                    str += "<tr>"
                            + "<td style='width:50px'>" + v.PLWRK + "</td>"
                            + "<td style='width:120px'>" + v.MATNR + "</td>"
                            + "<td style='width:350px'>" + v.TXTMD + "</td>"
                            + "<td style='width:200px'>" + v.LNPRS + "</td>"
                            + "<td style='width:90px'>" + v.UMDAT + "</td>"
                            + "<td style='width:90px'>" + v.CPUDT + "</td>"
                            + "<td style='width:90px;text-align:right;'>" + orf + "</td>"
                            + "<td style='width:90px;text-align:right;'>" + def + "</td>"
                            + "<td>" + v.DISPO + "</td>"
                            + "</tr>"
                })
                $("#t_r_content5 table").html(str);
            }
            $(".Mask2").hide();
        },
        error: function () {
            alert("error27");
            $(".Mask2").hide();
        }
    })
}

$(".detail-del .del").on("click", function () {
    $(this).parents(".table-detail").hide();
    $("#Mask").hide();
});

function lineClick25() {
    $(".ch-kp-twentyFive #t_r_content4").off("dblclick").on("dblclick", "tr", function () {
        $(".Mask").hide();
        $(".Mask2").show();
        var _this = $(this);
        _this.css({
            "opacity": "0.6"
        });
        _this.children("td").css({
            "filter": "alpha(opacity=60)"
        });
        _this.siblings().css({
            "opacity": "1"
        });
        _this.siblings().children("td").css({
            "filter": "alpha(opacity=100)"
        });
        var num1 = $(this).find("td").eq(0).text();
        var num2 = $(this).find("td").eq(2).text();
        dataAjax27(num1, num2);
    })
}
//20 23
// 获取间隔天数  
function getDays(day1, day2) {
    // 获取入参字符串形式日期的Date型日期  
    var st = day1.getDate();
    var et = day2.getDate();

    var retArr = [];

    // 获取开始日期的年，月，日  
    var yyyy = st.getFullYear(),
            mm = st.getMonth(),
            dd = st.getDate();

    // 循环  
    while (st.getTime() != et.getTime()) {
        retArr.push(st.getYMD());

        // 使用dd++进行天数的自增  
        st = new Date(yyyy, mm, dd++);
    }

    // 将结束日期的天放进数组  
    retArr.push(et.getYMD());
    return retArr; // 或可换为return ret;  
}

// 给Date对象添加getYMD方法，获取字符串形式的年月日  
Date.prototype.getYMD = function () {

    // 将结果放在数组中，使用数组的join方法返回连接起来的字符串，并给不足两位的天和月十位上补零  
    return [this.getFullYear(), fz(this.getMonth() + 1), fz(this.getDate())].join("-");
}

// 给String对象添加getDate方法，使字符串形式的日期返回为Date型的日期  
String.prototype.getDate = function () {
    var strArr = this.split('-');
    return new Date(strArr[0], strArr[1] - 1, strArr[2]);
}

// 给月和天，不足两位的前面补0  
function fz(num) {
    if (num < 10) {
        num = "0" + num;
    }
    return num;
}

//对时间进行去重
Array.prototype.unique3 = function () {
    var res = [];
    var json = {};
    for (var i = 0; i < this.length; i++) {
        if (!json[this[i]]) {
            res.push(this[i]);
            json[this[i]] = 1;
        }
    }
    return res;
}





var Browsers = {};
var ua = navigator.userAgent.toLowerCase();
if (window.ActiveXObject) {
    Browsers.ie = ua.match(/msie ([\d.]+)/)[1]
}
if (Browsers.ie === "8.0") {
    $("#t_r_t span").removeAttr("style");
    $("#t_r_t4 span").removeAttr("style");
    $("#t_r_t5 span").removeAttr("style");
}

