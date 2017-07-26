//点击查询按钮，弹出查询框
var search1=new multiSearch4($(".btn1"),"reportFS50/infocode",0,10);//信息编号
var search2=new multiSearch3($(".btn2"),"reportFS50/attaburea",null,null);//配属局段
var search4=new multiSearch3($(".btn4"),"reportFS50/productmodel",null,null);//产品型号
var search5=new multiSearch3($(".btn5"),"reportFS50/defalocation",null,null);//故障部位
var search6=new multiSearch3($(".btn6"),"reportFS50/accitype",null,null);//事故类别
var search7=new multiSearch3($(".btn7"),"reportFS50/severity",null,null);//严重度
var search8=new multiSearch3($(".btn8"),"reportFS50/productlife",null,null);//产品寿命阶段
var search11=new multiSearch4($(".btn11"),"reportFS50/tvendor",null,null);//供应商
var search13=new multiSearch4($(".btn13"),"reportFS50/produccode",0,10);//产品编号
var search14=new multiSearch4($(".btn14"),"reportFS50/motorcode",0,10);//机车编号
var search12=new multiSearch4($(".btn12"),"reportFS50/losstypecode",0,10);//losstypecode  质量损失类别编号




//控制表格高度的显示
var heights = $(window).height();
var heights_f=$(".container .col-sm-12 form").height();
$(".ch-kp-fifty-three .t_r_content").height((heights - heights_f - 130) + "px");
//点击收起按钮
$(".retract").off("click").on("click", function () {
    if ($(".container form").css("display") == "block") {
        $(".container form").slideUp();
        $(this).text("展开  ∨");
        var heights = $(window).height();
        $(".ch-kp-fifty-three .t_r_content").animate({
            "height": (heights - 125) + "px"
        });
    } else {
        $(".container form").slideDown();
        $(this).text("收起  ∧");
        var heights = $(window).height();
        $(".ch-kp-fifty-three .t_r_content").height((heights - heights_f - 130) + ("px"));
    }
});
var arr = [], arrTk = [];
//采购入库的表格
$("#submit").off("click").on("click", function () {
    var blog = true;
    $(".notSearch").empty();
    $("#show").text("数据正在加载中");
    //$(".hideCol").click();
    var array1 = [], array2 = [], array3 = [],array4 = [],array5 = [],array6 = [],array7 = [],array8 = [],array9 = [],array10 = [],array11 = [],array12 = [],array13 = [],array14=[];
    if ($(".xxbh").val() != "") {
        array1 = $(".xxbh").val().split(",");//信息编号
    }
    if ($(".psjd").val() != "") {
    	 array2 = $(".psjd").val().split(",");//配属局段
    }
    if ($(".jxdd").val() != "") {
        array3.push($(".jxdd").val());//检修地点
    }
    if ($(".cpxh").val() != "") {
        array4 = $(".cpxh").val().split(",");//产品型号
    }
    if ($(".cpbh").val() != "") {
        array5 = $(".cpbh").val().split(",");//产品编号
    }
    if ($(".jcbh").val() != "") {
        array6 = $(".jcbh").val().split(",");//机车编号
    }
    if ($(".gzbw").val() != "") {
        array7 = $(".gzbw").val().split(",");//故障部位
    }
    if ($(".sglb").val() != "") {
        array8 = $(".sglb").val().split(",");//事故类别
    }
    if ($(".yzd").val() != "") {
        array9 = $(".yzd").val().split(",");//严重度
    }
    if ($(".cpsmjd").val() != "") {
        array10 = $(".cpsmjd").val().split(",");//产品寿命阶段
    }
    /*if ($(".jnjw").val() != "") {
        array11 = $(".jnjw").val().split(",");//境内/境外
    }
    if ($(".xzjx").val() != "") {
        array12 = $(".xzjx").val().split(",");//新造/检修
    }*/
    if ($(".gys").val() != "") {
        array13 = $(".gys").val().split(",");//供应商
    }
    if ($(".zlsslbbh").val() != "") {
        array14 = $(".zlsslbbh").val().split(",");//质量损失类别编号
    }
    var txt1 = $("#startTime").val().replace(/-/g, "");//发现时间
    var txt2 = $("#endTime").val().replace(/-/g, "");
    var txt3 = $("#startYearMonth").val().replace(/-/g, "");//年月
    var txt4 = $("#endYearMonth").val().replace(/-/g, "");
    var reportJson={};

    if(!blog){
        return;
    }

    if(txt1==""&&txt2!=""){
    	alert("两个日期必须同时有或者同时没有");
    	return;
    }else if(txt1!=""&&txt2==""){
    	alert("两个日期必须同时有或者同时没有");
    	return;
    }
    if(txt3==""&&txt4!=""){
    	alert("两个日期必须同时有或者同时没有");
    	return;
    }else if(txt3!=""&&txt4==""){
    	alert("两个日期必须同时有或者同时没有");
    	return;
    }
    if(parseInt(txt1)>parseInt(txt2)||parseInt(txt3)>parseInt(txt4)){
        alert("后一个日期必须大于或等于前一个日期");
    } else{
        $(".Mask").show();
        var item = 0;
         reportJson = {
        	"xxbh":array1,//信息编号
        	"psjd":array2,//配属局段
        	"jxdd":array3,//检修地点
        	"cpxh":array4,//产品型号
        	"cpbh":array5,//产品编号
        	"jcbh":array6,//机车编号
        	"gzbw":array7,//故障部位
        	"sglb":array8,//事故类别
        	"yzd":array9,//严重度
        	"cpsmjd":array10,//产品寿命阶段
        	"lifnr":array13,//供应商
        	"lbbh":array14,//质量损失类别编号
        	"startTime": txt1,
            "endTime": txt2, //发现时间
             "startYearMonth": txt3,
             "endYearMonth": txt4,//年月
             "startitem": item, //分页
            "pageitem": 20,
            "isExport": false
        }
        reportJson = JSON.stringify(reportJson);
        $(".export").on("click", function () {
            reportJson = {
        		"xxbh":array1.join(","),//信息编号
            	"psjd":array2.join(","),//配属局段
            	"jxdd":array3.join(","),//检修地点
            	"cpxh":array4.join(","),//产品型号
            	"cpbh":array5.join(","),//产品编号
            	"jcbh":array6.join(","),//机车编号
            	"gzbw":array7.join(","),//故障部位
            	"sglb":array8.join(","),//事故类别
            	"yzd":array9.join(","),//严重度
            	"cpsmjd":array10.join(","),//产品寿命阶段
            	"lifnr":array13.join(","),//供应商
            	"lbbh":array14.join(","),//质量损失类别编号
            	"startTime": txt1,
                "endTime": txt2, //发现时间
                 "startYearMonth": txt3,
                 "endYearMonth": txt4,//年月	
                isExport: true
            }
            downloadFile(reportJson, ctx + "/crrc/reportFS50/downloadFile_03");
        });
        $.ajax({
            url: ctx + "/crrc/reportFS50/result_03",
            data: reportJson,
            dataType: "json",
            contentType: "application/json;charset=utf-8",
            type: "post",
            success: function (data) {
                if ('300' === data.statusCode || '301' === data.statusCode) {
                    alert(data.message);
                    $(".Mask").hide();
                    $(".ch-kp-fifty-three table:eq(1)").html('');
                    return true;
                }
                if (data.length == 0) {
                    $(".notSearch").next("div").hide();
                    $(".notSearch").text("未查询到数据");
                } else {
                    $(".notSearch").next("div").show();
                    $(".dataTime1").text(getTime(txt1, txt2));
                    if (data.length < 20) {
                        $("#show").html("数据加载完成");
                    } else {
                        $("#show").html("");
                    }
                    var str = "";
                    $.each(data, function (k, v) {
                        str += "<tr>"
		                        	+"<td width='40px' class='xh'>"+(k+1)+"</td>"
		                        	+"<td width='60px' class='yf'>"+v.month+"</td>"
		                            +"<td width='110px' class='xxbh'>"+v.xxbh+"</td>"
		                            +"<td width='120px' class='sslbbh'>"+v.lbbh+"</td>"
		                            +"<td width='110px' class='sslbdm'>"+v.lbdm+"</td>"
		                            +"<td width='110px' class='ssyydm'>"+v.yydm+"</td>"
		                            +"<td width='140px' class='psjd'>"+v.psjd+"</td>"
		                            +"<td width='90px' class='cpsmjd'>"+v.cpsmjd+"</td>" 
		                            +"<td width='90px' class='jxdd'>"+v.jxdd+"</td>"
		                            +"<td width='80px' class='fxsj' >"+v.fxsj+"</td>"
		                            +"<td width='60px' class='cpxh'>"+v.cpxh+"</td>"
		                            +"<td width='140px' class='cpbh'>"+v.cpbh+"</td>"
		                            +"<td width='40px' class='amount'>"+v.amount+"</td>"
		                            +"<td width='90px' class='xzccrq'>"+v.xzccrq+"</td>"
		                            +"<td width='110px' class='zjjxccrq'>"+v.zjjxccrq+"</td>"
		                            +"<td width='120px' class='jcbh'>"+v.jcbh+"</td>"
		                            +"<td width='80px' class='zlc'>"+v.zlc+"</td>"
		                            +"<td width='80px' class='jxhlc'>"+v.jxhlc+"</td>"
		                            +"<td width='140px' class='gzbw'>"+v.gzbw+"</td>"
		                            +"<td width='400px' class='gzms'>"+v.gzms+"</td>"
		                            +"<td width='350px' class='cljg'>"+v.cljg+"</td>"
		                            +"<td width='60px' class='clff'>"+v.clff+"</td>"
		                            +"<td width='140px' class='gzwcrq'>"+v.gzwcrq+"</td>"
		                            +"<td width='80px' class='sglb'>"+v.sglb+"</td>"
		                            +"<td width='80px' class='yzd'>"+v.yzd+"</td>"
		                            +"<td width='120px' class='zrcp'>"+v.zrcp+"</td>"
		                            +"<td width='90px' class='ss' style='text-align:right;'>"+formatNumber(v.ss,0,1)+"</td>"
		                            +"<td width='80px' class='clfy' style='text-align:right;'>"+formatNumber(v.clfy,0,1)+"</td>"
                                    +"<td width='80px' class='ysfy' style='text-align:right;'>"+formatNumber(v.ysfy,0,1)+"</td>"
                                    +"<td width='90px' class='rgfy' style='text-align:right;'>"+formatNumber(v.rgfy,0,1)+"</td>"
                                    +"<td width='80px' class='qtfy' style='text-align:right;'>"+formatNumber(v.qtfy,0,1)+"</td>"
		                            +"<td width='80px' class='pc' style='text-align:right;'>"+formatNumber(v.pc,0,1)+"</td>"
		                            +"<td width='70px' class='jnw'>"+v.jnw+"</td>"
		                            +"<td width='70px' class='xzjx'>"+v.xzjx+"</td>"
		                            +"<td width='80px' class='lifnr'>"+v.lifnr+"</td>"
		                            +"<td class='bz'>"+v.bz+"</td>"
                                + "</tr>";
                    });

                    $(".ch-kp-fifty-three table:eq(1)").html(str);
                    trBg($(".ch-kp-fifty-three table:eq(1)"))
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
            		"xxbh":array1,//信息编号
                	"psjd":array2,//配属局段
                	"jxdd":array3,//检修地点
                	"cpxh":array4,//产品型号
                	"cpbh":array5,//产品编号
                	"jcbh":array6,//机车编号
                	"gzbw":array7,//故障部位
                	"sglb":array8,//事故类别
                	"yzd":array9,//严重度
                	"cpsmjd":array10,//产品寿命阶段
                	"jnw":array11,//境内/境外
                	"xzjx":array12,//新造/检修
                	"lifnr":array13,//供应商
                	"lbbh":array14,//质量损失类别编号
                	"startTime": txt1,
                    "endTime": txt2, //发现时间
                    "startYearMonth": txt3,
                    "endYearMonth": txt4,//年月
                    "startitem": item, //分页
                    "pageitem": 20
                }
                reportJson = JSON.stringify(reportJson);
                if ($("#show").text() != "数据加载完成") {
                    $.ajax({
                        url: ctx + "/crrc/reportFS50/result_03",
                        data: reportJson,
                        dataType: "json",
                        contentType: "application/json;charset=utf-8",
                        type: "post",
                        success: function (data) {
                            if (data.length == 0) {
                                $("#show").html("数据加载完成");
                                b = false;
                            }
                            var str = "";
                            $.each(data, function (k, v) {
                                str += "<tr>"
		                                	+"<td width='40px' class='xh'>"+(k+1+item)+"</td>"
		                                	+"<td width='60px' class='yf'>"+v.month+"</td>"
				                            +"<td width='110px' class='xxbh'>"+v.xxbh+"</td>"
				                            +"<td width='120px' class='sslbbh'>"+v.lbbh+"</td>"
				                            +"<td width='110px' class='sslbdm'>"+v.lbdm+"</td>"
				                            +"<td width='110px' class='ssyydm'>"+v.yydm+"</td>"
				                            +"<td width='140px' class='psjd'>"+v.psjd+"</td>"
				                            +"<td width='90px' class='cpsmjd'>"+v.cpsmjd+"</td>" 
				                            +"<td width='90px' class='jxdd'>"+v.jxdd+"</td>"
				                            +"<td width='80px' class='fxsj' >"+v.fxsj+"</td>"
				                            +"<td width='60px' class='cpxh'>"+v.cpxh+"</td>"
				                            +"<td width='140px' class='cpbh'>"+v.cpbh+"</td>"
				                            +"<td width='40px' class='amount'>"+v.amount+"</td>"
				                            +"<td width='90px' class='xzccrq'>"+v.xzccrq+"</td>"
				                            +"<td width='110px' class='zjjxccrq'>"+v.zjjxccrq+"</td>"
				                            +"<td width='120px' class='jcbh'>"+v.jcbh+"</td>"
				                            +"<td width='80px' class='zlc'>"+v.zlc+"</td>"
				                            +"<td width='80px' class='jxhlc'>"+v.jxhlc+"</td>"
				                            +"<td width='140px' class='gzbw'>"+v.gzbw+"</td>"
				                            +"<td width='400px' class='gzms'>"+v.gzms+"</td>"
				                            +"<td width='350px' class='cljg'>"+v.cljg+"</td>"
				                            +"<td width='60px' class='clff'>"+v.clff+"</td>"
				                            +"<td width='140px' class='gzwcrq'>"+v.gzwcrq+"</td>"
				                            +"<td width='80px' class='sglb'>"+v.sglb+"</td>"
				                            +"<td width='80px' class='yzd'>"+v.yzd+"</td>"
				                            +"<td width='120px' class='zrcp'>"+v.zrcp+"</td>"
				                            +"<td width='90px' class='ss' style='text-align:right;'>"+formatNumber(v.ss,0,1)+"</td>"
				                            +"<td width='80px' class='clfy' style='text-align:right;'>"+formatNumber(v.clfy,0,1)+"</td>"
		                                    +"<td width='80px' class='ysfy' style='text-align:right;'>"+formatNumber(v.ysfy,0,1)+"</td>"
		                                    +"<td width='90px' class='rgfy' style='text-align:right;'>"+formatNumber(v.rgfy,0,1)+"</td>"
		                                    +"<td width='80px' class='qtfy' style='text-align:right;'>"+formatNumber(v.qtfy,0,1)+"</td>"
				                            +"<td width='80px' class='pc' style='text-align:right;'>"+formatNumber(v.pc,0,1)+"</td>"
				                            +"<td width='70px' class='jnw'>"+v.jnw+"</td>"
				                            +"<td width='70px' class='xzjx'>"+v.xzjx+"</td>"
				                            +"<td width='80px' class='lifnr'>"+v.lifnr+"</td>"
				                            +"<td class='bz'>"+v.bz+"</td>"
                                        + "</tr>";
                            });
                            item = item + 20;
                            $(".ch-kp-fifty-three table:eq(1)").append(str);
                            trBg($(".ch-kp-fifty-three table:eq(1)"));
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

    //}


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
}})

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