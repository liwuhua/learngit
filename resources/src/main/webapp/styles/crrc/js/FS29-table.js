//点击查询按钮，弹出查询框
var search1=new multiSearch2($(".btn1"),"reportFS29/factory",null,null);//工厂
var search2=new multiSearch4($(".btn2"),"reportFS29/vbeln",0,10);//销售订单
var search3=new multiSearch4($(".btn3"),"reportFS29/linenum",0,10);//销售订单行号
var search4=new multiSearch7($(".btn4"),"reportFS29/matnr",0,10);//生产订单物料编码
var search5=new multiSearch4($(".btn5"),"reportFS29/aufnr",0,10);//生产订单
var search6=new multiSearch1($(".btn6"),"reportFS29/produOrderType",null,null);//生产订单类型
var search7=new multiSearch1($(".btn7"),"reportFS29/zhwcx",null,null);//工作中心
var search8=new multiSearch1($(".btn8"),"reportFS29/comppurtype",null,null);//组件采购类型
var search9=new multiSearch7($(".btn9"),"reportFS29/tpurgroup",0,10);//采购组
var search10=new multiSearch7($(".btn10"),"reportFS29/tvendor",0,10);//供应商编码
var search11=new multiSearch2($(".btn11"),"reportFS29/zpsfs",null,null);//配送方式
var search12=new multiSearch7($(".btn12"),"reportFS29/copomatnum",0,10);//组件物料编码
var search13=new multiSearch4($(".btn13"),"reportFS29/productmodel",0,10);//产品型号



//控制表格高度的显示
var heights = $(window).height();
var heights_f = $(".container .col-sm-12 form").height();
$(".ch-kp-twentynine .t_r_content").height((heights - heights_f - 140) + "px");
//点击收起按钮
$(".retract").off("click").on("click", function () {
    if ($(".container form").css("display") == "block") {
        $(".container form").slideUp();
        $(this).text("展开  ∨");
        var heights = $(window).height();
        $(".ch-kp-twentynine .t_r_content").animate({
            "height": (heights - 140) + "px"
        });
    } else {
        $(".container form").slideDown();
        $(this).text("收起  ∧");
        var heights = $(window).height();
        $(".ch-kp-twentynine .t_r_content").height((heights  - heights_f - 140) + ("px"));
    }
})
var arr = [], arrTk = [];
//采购入库的表格
$("#submit").off("click").on("click", function () {
    var blog = true;/**/
     $(".notSearch").empty();
     $("#show").text("数据正在加载中");
     var isExclu1='',isExclu2='',isExclu3='', array1 = [], array2 = [], array3 = [], array4 = [], array5 = [], array6 = [], array7 = [], array8 = [], array9 = [], array10 = [], array11 = [], array12 = [], array13=[];
     var array14=[],array15=[];
     if ($(".werksValue").val() != "") {
         array1 = $(".werksValue").val().split(",");//工厂
         if(blog){
            blog = compareInput(array1,plant,"工厂");
         }
     }
     if ($(".vbelnValue").val() != "") {
        array2 = $(".vbelnValue").val().split(",");//销售订单
     }
    if ($(".dwerkValue").val() != "") {
        array3 = $(".dwerkValue").val().split(",");//销售订单行号
    }
    if ($(".vkorgValue").val() != "") {
        array5 = $(".vkorgValue").val().split(",");//生产订单
    }
    if ($(".matnrValue").val() != "") {
        array4 = $(".matnrValue").val().split(",");//生产订单物料编码
    }
    if($(".dauatValue").val() != ""){
    	array14 = $(".dauatValue").val().split(",");//生产订单类型
    }
    if ($(".vendorValue").val() != "") {
        array6 = $(".vendorValue").val().split(",");//工作中心
    }
    if ($(".cpxh").val() != "") {
        array15 = $(".cpxh").val().split(",");//产品型号
    }
    if ($(".comppurtype").val() != "") {
        array11 = $(".comppurtype").val().split(",");//组件采购类型
    }
    if($(".zjwlbm").val() != ""){
        array13 = $(".zjwlbm").val().split(",");//组件物料编码
    }
    if ($(".purGroupValue").val() != "") {
        array8 = $(".purGroupValue").val().split(",");//采购组
        array8 = regData(array8,pur_group);
        if(blog){
           blog = compareInput(array8,pur_group,"采购组");
        }

    }
    if ($(".oneValue").val() != "") {
        array9 = $(".oneValue").val().split(",");//供应商编码
    }
    if ($(".twoValue").val() != "") {
        array10 = $(".twoValue").val().split(",");//配送方式
    }
   /* array7=$(".mnkclMin").val();//模拟库存量
    array12=$(".mnkclMax").val();//模拟库存量*/
    var num;//模拟库存量
    if ($(".mnkclMax").prop("checked") == true && $(".mnkclMin").prop("checked") == false) {
        num = "1";
    } else if ($(".mnkclMax").prop("checked") == false && $(".mnkclMin").prop("checked") == true) {
        num = "2";
    } else if ($(".mnkclMax").prop("checked") == true && $(".mnkclMin").prop("checked") == true) {
        num = "3";
    } else if ($(".mnkclMax").prop("checked") == false && $(".mnkclMin").prop("checked") == false) {
        alert("模拟库存量范围必选其一!");
        return;
    }
    isExclu1=$(".purGroupValuePt").is(':checked');
    isExclu2=$(".oneValuePt").is(':checked');
    isExclu3=$(".twoValuePt").is(':checked');
    var txt1 = $("#startTime1").val().replace(/-/g, "");//基本开始日期
    var txt2 = $("#endTime1").val().replace(/-/g, "");//基本开始日期
    var txt3 = $("#startTime2").val().replace(/-/g, "");
    //var txt4 = $("#endTime2").val().replace(/-/g, "");//报告日期
    var reportJson = {};
    if (!blog) {
        return;
    }
    
    if (txt3 == "" ) {
        alert("请输入日期");
    } else {
        $(".Mask").show();
        var item = 0;
        reportJson = {
            "werksValue": array1, //工厂
            "vbelnValue": array2, //销售订单
            "dwerkValue": array3, //销售订单行项目
            "vkorgValue": array5, //生产订单
            "matnrValue": array4,//生产订单物料编码
            "dauatValue":array14,//生产订单类型
            "vendorValue": array6, //工作中心
            "kunnrValue":array15,//产品型号
            "comppurtypeValue": array11,//组件采购类型
            "matlGroupValue":array13,//组件物料编码
            "purGroupValue": array8,//采购组
            "oneValue": array9,//供应商编码
            "twoValue": array10,//配送方式
            "ylzd":num,//模拟库存量
            /*"startTimeTwo": array7, //模拟库存量小
            "endTimeTwo": array12, //模拟库存量大*/
            "isExclu1":isExclu1,
            "isExclu2":isExclu2,
            "isShow":isExclu3,
            "startTimeThree": txt1,
            "endTimeThree": txt2,//基本开始日期
            "startTime": txt3,
            //"endTime": txt4,//报告日期
            "startitem": item, //分页
            "pageitem": 20,
            "isExport": false
        };
        reportJson = JSON.stringify(reportJson);
        $(".export").on("click", function () {
             reportJson = {
        		 "werksValue": array1.join(","), //工厂
                 "vbelnValue": array2.join(","), //销售订单
                 "dwerkValue": array3.join(","), //销售订单行项目
                 "vkorgValue": array5.join(","), //生产订单
                 "matnrValue": array4.join(","),//生产订单物料编码
                 "dauatValue":array14.join(","),//生产订单类型
                 "vendorValue": array6.join(","), //工作中心
                 "kunnrValue":array15.join(","),//产品型号
                 "comppurtypeValue": array11.join(","),//组件采购类型
                 "matlGroupValue":array13.join(","),//组件物料编码
                 "purGroupValue": array8.join(","),//采购组
                 "oneValue": array9.join(","),//供应商编码
                 "twoValue": array10.join(","),//配送方式
                 "ylzd":num,//模拟库存量
                 /*"startTimeTwo": array7.join(","), //模拟库存量小
                 "endTimeTwo": array12.join(","), //模拟库存量大*/                 
                 "isExclu1":isExclu1,
                 "isExclu2":isExclu2,
                 "isShow":isExclu3,
                 "startTimeThree": txt1,
                 "endTimeThree": txt2,//基本开始日期
                 "startTime": txt3,
                 //"endTime": txt4,
                 isExport: true
             }
            downloadFile(reportJson, ctx + "/crrc/reportFS29/downloadFile");
         });
        $.ajax({
            url: ctx + "/crrc/reportFS29/result",
            data: reportJson,
            dataType: "json",
            contentType: "application/json;charset=utf-8",
            type: "post",
            success: function (data) {
                if ('300' === data.statusCode || '301' === data.statusCode) {
                    alert(data.message);
                    $(".Mask").hide();
                    $(".ch-kp-twentynine table:eq(1)").html('');
                    $(".notSearch").text("未查询到数据");
                    return true;
                }
                if (data.lackorder.length == 0) {
                    $(".notSearch").next("div").hide();
                    $(".notSearch").text("未查询到数据");
                } else {
                    $(".notSearch").next("div").show();
                    //$(".dataTime1").text(getTime2(txt1));
                    //$(".dataTime2").text(getTime(txt3, txt4));
                    if (data.lackorder.length < 20) {
                        $("#show").html("数据加载完成");
                    } else {
                        $("#show").html("");
                    }
                    var str = "";
                    $.each(data.lackorder, function (key, val) {
                        str += "<tr>"
                            + "<td style='width:40px' class='xh'>" + (key + 1) + "</td>"
                            + "<td style='width:80px;' class='DELIDATE'>" + val.DELIDATE + "</td>"
                            + "<td style='width:50px' class='WERKS'>" + val.WERKS + "</td>"
                            + "<td style='width:130px' class='ZHWCX'>" + val.ZHWCX + "</td>"
                            + "<td style='width:100px' class='VBLINE'>" + val.VBLINE + "</td>"
                            + "<td style='width:90px' class='RT_SIZE2'>" + val.RT_SIZE2 + "</td>"
                            + "<td style='width:100px' class='AUFNR'>" + val.AUFNR + "</td>"
                            +"<td width='120px' class='MATNR'>"+val.MATNR+"</td>"//生产订单物料编码 
                            +"<td width='190px' class='MATNRTXTMD'>"+val.MATNRTXTMD+"</td>"//生产订单物料编码描述
                            + "<td style='width:100px;' class='GSTRP'>" + val.GSTRP + "</td>"
                            + "<td style='width:120px;' class='COPOMATNUM'>" + val.COPOMATNUM + "</td>"
                            + "<td style='width:300px' class='ARKTX'>" + val.ARKTX + "</td>"
                            + "<td style='width:70px;text-align: right;' class='ZHWCX'>" + val.MNG01 + "</td>"
                            if(val.ANALINVEN<0){
                            	str+= "<td style='width:80px;text-align: right;color:#c70019;' class='ANALINVEN'>" + val.ANALINVEN + "</td>"
                            	
                            }else{
                            	str+= "<td style='width:80px;text-align: right;' class='ANALINVEN'>" + val.ANALINVEN + "</td>"
                            }
                        str+= "<td style='width:60px;text-align:center;' class='MEINS'>" + val.MEINS + "</td>"
                            + "<td style='width:120px' class='DUCUMNUM'>" + val.DUCUMNUM + "</td>"
                            + "<td style='width:100px;text-align: right;' class='ORDERNUM'>" + parseFloat(val.ORDERNUM).toFixed(3) + "</td>"
                            + "<td style='width:90px' class='PLANDELIDATE'>" + val.PLANDELIDATE + "</td>"
                            + "<td style='width:90px' class='ACTUDELIDATE'>" + val.ACTUDELIDATE + "</td>"
                            + "<td style='width:80px;text-align: right;' class='DELIVNUM'>" + parseFloat(val.DELIVNUM).toFixed(3) + "</td>"
                            + "<td style='width:180px' class='EKGRP'>" + val.EKGRP + "</td>"
                            + "<td class='DISPO'>" + val.DISPO + "</td>"
                            /*+ "<td style='width:100px' class='CAUSEANALY'>" + val.CAUSEANALY + "</td>"
                            + "<td style='width:120px' class='ECPECARRIVDATE'>" + val.ECPECARRIVDATE + "</td>"
                            + "<td class='ISREQUIPLAN'>" + val.ISREQUIPLAN + "</td>"*/
                            + "</tr>";
                    });

                    $(".ch-kp-twentynine table:eq(1)").html(str);
                    trBg($(".ch-kp-twentynine table:eq(1)"))
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
                		 "werksValue": array1, //工厂
                         "vbelnValue": array2, //销售订单
                         "dwerkValue": array3, //销售订单行项目
                         "vkorgValue": array5, //生产订单
                         "matnrValue": array4,//生产订单物料编码
                         "dauatValue":array14,//生产订单类型
                         "vendorValue": array6, //工作中心
                         "kunnrValue":array15,//产品型号
                         "comppurtypeValue": array11,//组件采购类型
                         "matlGroupValue":array13,//组件物料编码
                         "purGroupValue": array8,//采购组
                         "oneValue": array9,//供应商编码
                         "twoValue": array10,//配送方式
                         "ylzd":num,//模拟库存量
                         /*"startTimeTwo": array7, //模拟库存量小
                         "endTimeTwo": array12, //模拟库存量大*/
                         "isExclu1":isExclu1,
                         "isExclu2":isExclu2,
                         "isShow":isExclu3,
                         "startTimeThree": txt1,
                         "endTimeThree": txt2,//基本开始日期
                         "startTime": txt3,
                         //"endTime": txt4,//报告日期
                         "startitem": item, //分页
                         "pageitem": 20
                 }
                 reportJson = JSON.stringify(reportJson);
                 if ($("#show").text() != "数据加载完成") {
                     $.ajax({
                         url: ctx + "/crrc/reportFS29/result",
                         data: reportJson,
                         dataType: "json",
                         contentType: "application/json;charset=utf-8",
                         type: "post",
                         success: function (data) {
                             if (data.lackorder.length == 0) {
                                 $("#show").html("数据加载完成");
                                 b = false;
                             }
                             var str = "";
                             $.each(data.lackorder, function (key, val) {
                                 str += "<tr>"
                                     + "<td style='width:40px' class='xh'>" + (key + 1 + item) + "</td>"
                                     + "<td style='width:80px;' class='DELIDATE'>" + val.DELIDATE + "</td>"
                                     + "<td style='width:50px' class='WERKS'>" + val.WERKS + "</td>"
                                     + "<td style='width:130px' class='ZHWCX'>" + val.ZHWCX + "</td>"
                                     + "<td style='width:100px' class='VBLINE'>" + val.VBLINE + "</td>"
                                     + "<td style='width:90px' class='RT_SIZE2'>" + val.RT_SIZE2 + "</td>"
                                     + "<td style='width:100px' class='AUFNR'>" + val.AUFNR + "</td>"
                                     +"<td width='120px' class='MATNR'>"+val.MATNR+"</td>"//生产订单物料编码 
                                     +"<td width='190px' class='MATNRTXTMD'>"+val.MATNRTXTMD+"</td>"//生产订单物料编码描述
                                     + "<td style='width:100px;' class='GSTRP'>" + val.GSTRP + "</td>"
                                     + "<td style='width:120px;' class='COPOMATNUM'>" + val.COPOMATNUM + "</td>"
                                     + "<td style='width:300px' class='ARKTX'>" + val.ARKTX + "</td>"
                                     + "<td style='width:70px;text-align: right;' class='ZHWCX'>" + val.MNG01 + "</td>"
                                     if(val.ANALINVEN<0){
                                     	str+= "<td style='width:80px;text-align: right;color:#c70019;' class='ANALINVEN'>" + val.ANALINVEN + "</td>"
                                     	
                                     }else{
                                     	str+= "<td style='width:80px;text-align: right;' class='ANALINVEN'>" + val.ANALINVEN + "</td>"
                                     }
                                     str+= "<td style='width:60px;text-align:center;' class='MEINS'>" + val.MEINS + "</td>"
                                     + "<td style='width:120px' class='DUCUMNUM'>" + val.DUCUMNUM + "</td>"
                                     + "<td style='width:100px;text-align: right;' class='ORDERNUM'>" + parseFloat(val.ORDERNUM).toFixed(3) + "</td>"
                                     + "<td style='width:90px' class='PLANDELIDATE'>" + val.PLANDELIDATE + "</td>"
                                     + "<td style='width:90px' class='ACTUDELIDATE'>" + val.ACTUDELIDATE + "</td>"
                                     + "<td style='width:80px;text-align: right;' class='DELIVNUM'>" + parseFloat(val.DELIVNUM).toFixed(3) + "</td>"
                                     + "<td style='width:180px' class='EKGRP'>" + val.EKGRP + "</td>"
                                     + "<td class='DISPO'>" + val.DISPO + "</td>"
                                     /*+ "<td style='width:100px' class='CAUSEANALY'>" + val.CAUSEANALY + "</td>"
                                     + "<td style='width:120px' class='ECPECARRIVDATE'>" + val.ECPECARRIVDATE + "</td>"
                                     + "<td class='ISREQUIPLAN'>" + val.ISREQUIPLAN + "</td>"*/
                                 + "</tr>";
                             });
                             item = item + 20;
                             $(".ch-kp-twentynine table:eq(1)").append(str);
                             trBg($(".ch-kp-twentynine table:eq(1)"));
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


var Browsers = {};
var ua = navigator.userAgent.toLowerCase();
if (window.ActiveXObject) {
    Browsers.ie = ua.match(/msie ([\d.]+)/)[1]
}
if (Browsers.ie === "8.0") {
    $("#t_r_t span").removeAttr("style");
}