//点击查询按钮，弹出查询框
var search1=new multiSearch2($(".btn1"),"reportFS21/tplant",null,null);//工厂
var search2 = new multiSearch($(".btn2"), "tmaterial", 0, 10);//物料编码
var search3=new multiSearch2($(".btn3"),"reportFS21/tpurgroup",null,null);//采购组
/*var search4 = new multiSearch1($(".btn4"), "reportFS21/createuser", null, null);//创建者
var search5 = new multiSearch1($(".btn5"), "reportFS21/updateuser", null, null);//更改者
*/
var search4 = new multiSearch1($(".btn4"), "reportFS21/mrpctl", null, null);//控制者
//控制表格高度的显示
var heights = $(window).height();
var heights_f=$(".container .col-sm-12 form").height();
/*$(".twenty-one .t_r_content").height((heights - heights_f - 140) + "px");*/
$(".twenty-one .t_r_content").animate({
    "max-height": (heights - heights_f - 140) + "px"
});
//点击收起按钮
$(".retract").off("click").on("click", function () {
    if ($(".container form").css("display") == "block") {
        $(".container form").slideUp();
        $(this).text("展开  ∨");
        var heights = $(window).height();
        $(".twenty-one .t_r_content").animate({
            "max-height": (heights - 135) + "px"
        });
    } else {
        $(".container form").slideDown();
        $(this).text("收起  ∧");
        var heights = $(window).height();
        /*$(".twenty-one .t_r_content").height((heights - heights_f - 140) + "px");*/
        $(".twenty-one .t_r_content").animate({
            "max-height": (heights - heights_f - 140) + "px"
        });
    }
});
var arr = [], arrTk = [];
//点击提交按钮渲染表格
$("#submit").off("click").on("click", function () {
	var blog = true;
    $(".notSearch").empty();
    
    var arrs=[],array1 = [], array2 = [], array3 = [], array4 = [], array5 = [],array6=[];
    if ($(".factory").val() != "") {
        array1 = $(".factory").val().split(",");//工厂
        if(blog){
            blog = compareInput(array1,plant,"工厂");
        }
    }
    if ($(".material").val() != "" && $(".material").val().indexOf(";") != -1) {
        arrs = $(".material").val().split(";");
        array2 = arrs[0].split(",");//物料多值
        array3 = arrs[1].split(",");//物料多区间
    } else if ($(".material").val() != "" && $(".material").val().indexOf(";") == -1 && $(".material").val().indexOf(":") != -1) {
        array2 = [];
        array3 = $(".material").val().split(",");
    } else if ($(".material").val() != "" && $(".material").val().indexOf(";") == -1 && $(".material").val().indexOf(":") == -1) {
        array2 = $(".material").val().split(",");
        array3 = [];
    } else if ($(".material").val() == "") {
        array2 = [];
        array3 = [];
    }
    if ($(".purchase").val() != "") {
        array4 = $(".purchase").val().split(",");//采购组
        array4 = regData(array4,pur_group);
        if(blog){
            blog = compareInput(array4,pur_group,"采购组");
        }
    }
    /*if ($(".creator").val() != "") {
        array5 = $(".creator").val().split(",");//创建者
    }
    if ($(".changer").val() != "") {
        array6 = $(".changer").val().split(",");//更改者
    }*/
    if ($(".mrpctl").val() != "") {
        array5 = $(".mrpctl").val().split(",");//控制者
    }
   /* var txt1 = $("#startTime").val().replace(/-/g, "");
    var txt2 = $("#endTime").val().replace(/-/g, "");
    var txt3 = $("#startTimeTwo").val().replace(/-/g, "");
    var txt4 = $("#endTimeTwo").val().replace(/-/g, "");*/
    if(!blog){
        return;
    }
    var reportJson = {};
    /*if (parseInt(txt1) > parseInt(txt2)||parseInt(txt3) > parseInt(txt4)) {
        $(".Mask").hide();
        alert("后一个日期必须大于或等于前一个日期");
    } else {*/
        var item = 0;
        reportJson = {
            "plantValue": array1, //工厂
            "matnrValue": array2, //物料编码
            "matnrInterval": array3, //物料多区间
            "purGroupValue":array4,//采购组
            "mrpctlValue":array5,//控制者
            /*"oneValue":array5,//创建者
            "twoValue":array6,//更改者
			"startTime":txt1,
            "endTime":txt2,
            "startTimeTwo": txt3,
            "endTimeTwo": txt4, //下周、月入库日期
*/            
            "startitem": item, //分页
            "pageitem": 20,
            "isExport": false
        }
        reportJson = JSON.stringify(reportJson);

        /*$(".export").on("click", function () {
            reportJson = {
        		plantValue: array1.join(","), //工厂
                matnrValue: array2.join(","), //物料编码
                matnrInterval: array3.join(","), //物料多区间
                purGroupValue:array4.join(","),//采购组
                mrpctlValue:array5.join(","),//控制者
                oneValue:array5.join(","),//创建者
                twoValue:array6.join(","),//更改者
                startTime:txt1,
                endTime:txt2,
                startTimeTwo: txt3,
                endTimeTwo: txt4, //下周、月入库日期
                
                isExport: true
            }
            //reportJson = JSON.stringify(reportJson);
            downloadFile(reportJson, ctx + "/crrc/reportFS21/downloadFile");
        });*/
        $(".Mask").show();
        //表数据请求		
        $.ajax({
            url: ctx + "/crrc/reportFS21/result",
            data: reportJson,
            dataType: "json",
            contentType: "application/json;charset=utf-8",
            type: "post",
            success: function (data) {
                if ('300' === data.statusCode || '301' === data.statusCode) {
                    alert(data.message);
                    $(".Mask").hide();
                    $(".twenty-one table:eq(1)").html('');
                    return true;
                }
                if (data.length == 0) {
                    $(".twenty-one").parent().hide();
                    $(".notSearch").text("未查询到数据");
                } else {
                    $(".notSearch+div").show();
                    if (data.length < 20) {
                        $("#show").html("数据加载完成");
                    } else {
                        $("#show").html("");
                    }
                    var html = "";
                    $.each(data, function (k, v) {
                        html += "<tr>"
		                        	+"<td width='40px' class='PLWRK'>"+v.PLWRK+"</td>"
		                            +"<td width='130px' class='MATNR'>"+v.MATNR+"</td>"
		                            +"<td width='310px' class='MAKTX'>"+v.MAKTX+"</td>"
		                            +"<td width='60px' class='EKGRP'>"+v.EKGRP+"</td>"
		                            +"<td width='50px' class='mrpctl'>"+v.MRP_CONTRL+"</td>"
		                            +"<td width='90px' class='DELNR'>"+v.DELNR+"</td>"
		                            +"<td width='80px' class='DELPS'>"+v.DELPS+"</td>"
		                            +"<td width='120px' class='ZXGYL'>"+v.ZXGYL+"</td>"
		                            +"<td width='100px' class='ZCJRY'>"+v.ZCJRY+"</td>"
		                            +"<td width='130px' class='ZCJDH'>"+v.ZCJDH+"</td>"
		                            +"<td width='100px' class='ZCJBM'>"+v.ZCJBM+"</td>"
		                            +"<td width='210px' class='ZGGNR'>"+v.ZGGNR+"</td>"
		                            +"<td width='100px' class='ZGGRY'>"+v.ZGGRY+"</td>"
		                            +"<td width='140px' class='ZGGDH'>"+v.ZGGDH+"</td>"
		                            +"<td width='120px' class='ZGGBM'>"+v.ZGGBM+"</td>"
		                            +"<td width='70px' class='ZCJRQ'>"+v.ZCJRQ+"</td>"
		                            +"<td width='70px' class='ZGGRQ'>"+v.ZGGRQ+"</td>"
		                            +"<td width='70px' class='DAT00'>"+v.DAT00+"</td>"
		                            +"<td width='70px' class='BERW1'>"+v.BERW1+"</td>"
		                            +"<td width='320px' class='ZYCXX'>"+v.ZYCXX+"</td>"
		                            +"<td class='ZBZ'>"+v.ZBZ+"</td>"
                                + "</tr>"
                    })
                    $(".twenty-one table:eq(1)").html(html);
                    trBg($(".twenty-one table:eq(1)"));
                }
                $(".sureBtns").click();
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
            var fold = $(".t_r_content").height() + $(".t_r_content")[0].scrollTop;
            if (b && fold >= $(".t_r_content")[0].scrollHeight) {
                b = false;
                if($("#show").html()=="数据加载完成"){
					$(".Mask").hide();
				}else{
					$(".Mask").show();
				}
                reportJson= {
                		"plantValue": array1, //工厂
                        "matnrValue": array2, //物料编码
                        "matnrInterval": array3, //物料多区间
                        "purGroupValue":array4,//采购组
                        "mrpctlValue":array5,//控制者
                        /*"oneValue":array5,//创建者
                        "twoValue":array6,//更改者
                        "startTime":txt1,
                        "endTime":txt2,
                        "startTimeTwo": txt3,
                        "endTimeTwo": txt4,*/ //下周、月入库日期
                        "startitem": item, //分页
                        "pageitem": 20
                }
                reportJson = JSON.stringify(reportJson);
                $.ajax({
                    url: ctx + "/crrc/reportFS21/result",
                    data: reportJson,
                    dataType: "json",
                    contentType: "application/json;charset=utf-8",
                    type: "post",
                    success: function (data) {
                        if (data.length < 20) {
                            $("#show").html("数据加载完成");
                            b = false;
                        }
                        var html = "";
                        $.each(data, function (k, v) {
                            html += "<tr>"
		                            	+"<td width='40px' class='PLWRK'>"+v.PLWRK+"</td>"
			                            +"<td width='130px' class='MATNR'>"+v.MATNR+"</td>"
			                            +"<td width='310px' class='MAKTX'>"+v.MAKTX+"</td>"
			                            +"<td width='60px' class='EKGRP'>"+v.EKGRP+"</td>"
			                            +"<td width='50px' class='mrpctl'>"+v.MRP_CONTRL+"</td>"
			                            +"<td width='90px' class='DELNR'>"+v.DELNR+"</td>"
			                            +"<td width='80px' class='DELPS'>"+v.DELPS+"</td>"
			                            +"<td width='120px' class='ZXGYL'>"+v.ZXGYL+"</td>"
			                            +"<td width='100px' class='ZCJRY'>"+v.ZCJRY+"</td>"
			                            +"<td width='130px' class='ZCJDH'>"+v.ZCJDH+"</td>"
			                            +"<td width='100px' class='ZCJBM'>"+v.ZCJBM+"</td>"
			                            +"<td width='210px' class='ZGGNR'>"+v.ZGGNR+"</td>"
			                            +"<td width='100px' class='ZGGRY'>"+v.ZGGRY+"</td>"
			                            +"<td width='140px' class='ZGGDH'>"+v.ZGGDH+"</td>"
			                            +"<td width='120px' class='ZGGBM'>"+v.ZGGBM+"</td>"
			                            +"<td width='70px' class='ZCJRQ'>"+v.ZCJRQ+"</td>"
			                            +"<td width='70px' class='ZGGRQ'>"+v.ZGGRQ+"</td>"
			                            +"<td width='70px' class='DAT00'>"+v.DAT00+"</td>"
			                            +"<td width='70px' class='BERW1'>"+v.BERW1+"</td>"
			                            +"<td width='320px' class='ZYCXX'>"+v.ZYCXX+"</td>"
			                            +"<td class='ZBZ'>"+v.ZBZ+"</td>"
                                    + "</tr>"
                        })
                        item = item + 20;
                        $(".twenty-one table:eq(1)").append(html);
                        trBg($(".twenty-one table:eq(1)"));
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
        });

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
                
                //设置隐藏后的整个table的宽度
                var width21=2490;
                for(var i=0;i<21;i++){
                	if($(".twenty-one .t_r_title table tr th").eq(i).css("display")=="none"){
                		width21=width21-$(".twenty-one .t_r_title table tr th").eq(i).width();
                	}
                }
                var width21_dis_t = width21 + 20;
                $(".twenty-one table").css("width", width21 + "px");
                $(".twenty-one .t_r_title").css("width", width21_dis_t + "px");
            })
        } else {
            $(this).removeClass("bg");
            var txt = $(this).attr("src");
            $(this).attr("src", txt.replace("hidered.png", "hide.png"));
            $(".hideColBox").hide();
            $("#Mask").hide();
        }
    });
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
            $("#Mask").show();
            $(".hideCol").attr("src", $(".hideCol").attr("src").replace("hide.png", "hidered.png"));
        }

    })
}