//点击查询按钮，弹出查询框
var search1 = new multiSearch2($(".btn1"), "reportFS55/salesOrg", null, null);//销售组织
var search2 = new multiSearch2($(".btn2"), "reportFS55/tplant", null, null);//工厂
var search3 = new multiSearch1($(".btn3"), "reportFS55/tcustomer", null, null);//客户编号
var search4 = new multiSearch($(".btn4"), "tmaterial", 0, 10);//物料编码

//控制表格高度的显示
var heights = $(window).height();
$(".ch-kp-cb2 .t_r_content").height((heights - 170) + "px");
//点击收起按钮
$(".retract").off("click").on("click", function () {
    if ($(".container form").css("display") == "block") {
        $(".container form").slideUp();
        $(this).text("展开  ∨");
        var heights = $(window).height();
        $(".ch-kp-cb2 .t_r_content").animate({
            "height": (heights - 130) + "px"
        });
    } else {
        $(".container form").slideDown();
        $(this).text("收起  ∧");
        var heights = $(window).height();
        $(".ch-kp-cb2 .t_r_content").height((heights - 170) + "px");
    }
});
var arr = [], arrTk = [];
//点击提交按钮渲染表格
$("#submit").off("click").on("click", function () {
	var blog = true;
    $("#show").text("数据正在加载中");
    $(".notSearch").empty();
    var arrs=[],array1 = [], array2 = [], array3 = [], array4 = [], array5 = [];
    if ($(".vkorgValue").val() != "") {
        array1 = $(".vkorgValue").val().split(",");//销售组织
        if(blog){
            blog = compareInput(array1,salesOrg,"销售组织");
        }
    }
    if ($(".gc").val() != "") {
        array2 = $(".gc").val().split(",");//工厂
        if(blog){
            blog = compareInput(array2,plant,"工厂");
        }
    }
    if ($(".kh").val() != "") {
        array3 = $(".kh").val().split(",");//客户编码
    }

    if ($(".material").val() != "" && $(".material").val().indexOf(";") != -1) {
        arrs = $(".material").val().split(";");
        array4 = arrs[0].split(",");//物料多值
        array5 = arrs[1].split(",");//物料多区间
    } else if ($(".material").val() != "" && $(".material").val().indexOf(";") == -1 && $(".material").val().indexOf(":") != -1) {
        array4 = [];
        array5 = $(".material").val().split(",");
    } else if ($(".material").val() != "" && $(".material").val().indexOf(";") == -1 && $(".material").val().indexOf(":") == -1) {
        array4 = $(".material").val().split(",");
        array5 = [];
    } else if ($(".material").val() == "") {
        array4 = [];
        array5 = [];
    }
    var txt3 = $("#startTimeTwo").val().replace(/-/g, "");
    var txt4 = $("#endTimeTwo").val().replace(/-/g, "");


    if(!blog){
        return;
    }
    var reportJson2 = {};
    if (txt3 == "" || txt4 == "") {
        alert("请输入日期");
    } else if (parseInt(txt3) > parseInt(txt4)) {
        alert("后一个日期必须大于或等于前一个日期");
    } else {
        $(".Mask").show();
        var item = 0;
        reportJson2 = {
            "vkorgValue": array1, //销售组织
            "plantValue": array2, //工厂
            "kunnrValue": array3, //客户编码
            "matnrValue": array4, //物料编码
            "matnrInterval": array5, //物料多区间
            "startTime": txt3,
            "endTime": txt4, //进厂日期
            "startitem": item, //分页
            "pageitem": 20,
            "isExport": false
        }
        reportJson2 = JSON.stringify(reportJson2);
        $(".dataTime2").text(getTime(txt3, txt4));

        $(".export").on("click", function () {
            reportJson2 = {
                vkorgValue: array1.join(','), //销售组织
                plantValue: array2.join(','), //工厂
                kunnrValue: array3.join(','), //客户编码
                matnrValue: array4.join(','), //物料编码
                matnrInterval: array5.join(','), //物料多区间
                startTime: txt3,
                endTime: txt4, //进厂日期
                isExport: true
            }
            //reportJson = JSON.stringify(reportJson);
            downloadFile(reportJson2, ctx + "/crrc/reportFS55/downloadFile");
        });
        //第二张表数据请求		
        $.ajax({
            url: ctx + "/crrc/reportFS55/result",
            data: reportJson2,
            dataType: "json",
            contentType: "application/json;charset=utf-8",
            type: "post",
            success: function (data) {
                if ('300' === data.statusCode || '301' === data.statusCode) {
                    alert(data.message);
                    $(".Mask").hide();
                    $(".ch-kp-cb2 table:eq(1)").html('');
                    return true;
                }
                if (data.procurementStorage.length == 0) {
                    $(".ch-kp-cb2").parent().hide();
                    $(".notSearch").text("未查询到数据");
                } else {
                    $(".notSearch+div").show();
                    if (data.procurementStorage.length < 20) {
                        $("#show").html("数据加载完成");
                    } else {
                        $("#show").html("");
                    }
                    var html = "";
                    $.each(data.procurementStorage, function (k, v) {
                        html += "<tr>"
                                + "<td width='40px' class='xh'>" + (k + 1) + "</td>"
                                + "<td width='100px' class='kunnr'>" + v.parnr + "</td>"
                                + "<td width='200px' class='bname'>" + v.txtmd + "</td>"
                                + "<td width='130px' class='matnr'>" + v.matnr + "</td>"
                                + "<td width='350px' class='maktx'>" + v.maktx + "</td>"
                                + "<td width='60px' class='plant'>" + v.iwerk + "</td>"
                                + "<td width='80px' class='zdate'>" + v.zdate + "</td>"
                                + "<td width='85px' class='contnbr'>" + v.contnbr + "</td>"
                                + "<td width='80px' class='contitm'>" + v.contitm + "</td>"
                                + "<td width='200px' class='bstkd'>" + v.bstkd + "</td>"
                                + "<td width='100px' class='connum' style='text-align: right'>" + parseFloat(v.connum).toFixed(3) + "</td>"
                                + "<td width='100px' class='lastconnum' style='text-align: right'>" + parseFloat(v.lastconnum).toFixed(3) + "</td>"
                                + "<td width='110px' class='yfhwkpnum' style='text-align: right'>" + parseFloat(v.yfhwkpnum).toFixed(3) + "</td>"
                                if(v.fkimg==""){
                                    html+= "<td width='100px' class='fkimg' style='text-align: right'>" + v.fkimg + "</td>"
                                }else{
                                    html+= "<td width='100px' class='fkimg' style='text-align: right'>" + parseFloat(v.fkimg).toFixed(3) + "</td>"
                                }
                                html+= "<td class='netwr' style='text-align: right'>" + formatNumber(v.netwr,2,1) + "</td>"
                                    + "</tr>"
                    })
                    $(".ch-kp-cb2 table:eq(1)").html(html);
                    trBg($(".ch-kp-cb2 table:eq(1)"));
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
                reportJson2 = {
                    "vkorgValue": array1, //销售组织
                    "plantValue": array2, //工厂
                    "kunnrValue": array3, //客户编码
                    "matnrValue": array4, //物料编码
                    "matnrInterval": array5, //物料多区间
                    "startTime": txt3,
                    "endTime": txt4, //进厂日期
                    "startitem": item, //分页
                    "pageitem": 20
                }
                reportJson2 = JSON.stringify(reportJson2);
                $.ajax({
                    url: ctx + "/crrc/reportFS55/result",
                    data: reportJson2,
                    dataType: "json",
                    contentType: "application/json;charset=utf-8",
                    type: "post",
                    success: function (data) {
                        if (data.procurementStorage.length <= 10) {
                            $("#show").html("数据加载完成");
                            b = false;
                        }
                        var html = "";
                        $.each(data.procurementStorage, function (k, v) {
                            html += "<tr>"
                                + "<td width='40px' class='xh'>" + (k + 1 + item) + "</td>"
                                + "<td width='100px' class='kunnr'>" + v.parnr + "</td>"
                                + "<td width='200px' class='bname'>" + v.txtmd + "</td>"
                                + "<td width='130px' class='matnr'>" + v.matnr + "</td>"
                                + "<td width='350px' class='maktx'>" + v.maktx + "</td>"
                                + "<td width='60px' class='plant'>" + v.iwerk + "</td>"
                                + "<td width='80px' class='zdate'>" + v.zdate + "</td>"
                                + "<td width='85px' class='contnbr'>" + v.contnbr + "</td>"
                                + "<td width='80px' class='contitm'>" + v.contitm + "</td>"
                                + "<td width='200px' class='bstkd'>" + v.bstkd + "</td>"
                                + "<td width='100px' class='connum' style='text-align: right'>" + parseFloat(v.connum).toFixed(3) + "</td>"
                                + "<td width='100px' class='lastconnum' style='text-align: right'>" + parseFloat(v.lastconnum).toFixed(3) + "</td>"
                                + "<td width='110px' class='yfhwkpnum' style='text-align: right'>" + parseFloat(v.yfhwkpnum).toFixed(3) + "</td>";
                                if(v.fkimg==""){
                                    html+= "<td width='100px' class='fkimg' style='text-align: right'>" + v.fkimg + "</td>"
                                }else{
                                    html+= "<td width='100px' class='fkimg' style='text-align: right'>" + parseFloat(v.fkimg).toFixed(3) + "</td>"
                                }
                                html+= "<td class='netwr' style='text-align: right'>" + formatNumber(v.netwr,2,1) + "</td>"
                                + "</tr>";
                        })
                        item = item + 20;
                        $(".ch-kp-cb2 table:eq(1)").append(html);
                        trBg($(".ch-kp-cb2 table:eq(1)"));
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
            
          //获取表头名称
			var thArr=[]; 
			for(var i=0;i<$(".t_r_title table tr").find("th").length;i++){
				thArr.push($(".t_r_title table tr").find("th").eq(i).text())
			}
			//循环表头将名称添加到弹出框中
			var str1="";
			for(var j=0;j<thArr.length;j++){
				str1+="<tr>"
					+"<td style='width:50px;text-align:center;'><input type='checkbox' checked='checked' class='checks'></td>"
					+"<td>"+thArr[j]+"</td>"
				+"</tr>";
			}
			$("#t_r_content table").html(str1);
			trBg($("#t_r_content table"));
            //再次点击弹出框之前未被选中的仍未被选中
			//弹出框里的文本
	        var arrTk = [];
	        for (var i = 0; i < $("#t_r_content tr").length; i++) {
	            arrTk.push($("#t_r_content tr").eq(i).find("td").text());
	        }


	        var val = "";
	        $("#t_r_content").on("click", ".checks", function () {
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
	                if ($("#t_r_content tr:eq(" + i + ") td:eq(1)").text() == arr[j]) {
	                    $("#t_r_content tr:eq(" + i + ") td:eq(0)").find("input").prop("checked", false)
	                }
	            }


	        }
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
			


            //点击取消和x弹出框消失，点击确定相应的列消失
            can($(".closeBtn"));
            can($(".canBtns"));
            $(".sureBtns").on("click", function () {


                //弹出框里的不被选中的已经放倒一个数组里，判断表头有这个内容就将此隐藏
                //表头数组
                var arrBig = [];
                for (var j = 0; j < $(".t_r_title table tr th").length; j++) {
                    var thBig = $(".t_r_title table tr th").eq(j).text();
                    arrBig.push(thBig)
                }

              //不选中的text的字段
                var textArr = [];
                for (var n = 0; n < arrBig.length; n++) {
                    if ($("#t_r_content tr").eq(n).find("input[type='checkbox']").prop("checked")) {
                        var checkTrue = $("#t_r_content tr").eq(n).find("input[type='checkbox']").parent("td").next().text();
                        textArr.push(checkTrue);
                    }
                }
                //被选中的class名
                var arrTrue = [];
                for (var n = 0; n < textArr.length; n++) {
                    if ($.inArray(textArr[n], arrBig) >= 0) {
                        var className = $(".ch-kp-cb2 .t_r_title table tr th").eq($.inArray(textArr[n], arrBig)).attr("class");
                        arrTrue.push(className);
                    }
                }
                for (var m = 0; m < arrTrue.length; m++) {
                    //$(".t_r").find("."+arrTrue[m]).css("display","block");
                    $(".ch-kp-cb2").find("." + arrTrue[m]).show();
                }

                //不被选中的class名
                var arrN = [];
                for (var n = 0; n < arr.length; n++) {
                    if ($.inArray(arr[n], arrBig) >= 0) {
                        var className = $(".ch-kp-cb2 .t_r_title table tr th").eq($.inArray(arr[n], arrBig)).attr("class");
                        arrN.push(className);
                    }
                }
                //不被选中的class消失
                for (var m = 0; m < arrN.length; m++) {
                    $(".t_r").find("." + arrN[m]).hide()
                }

                $(".hideColBox").hide();
                var txt = _this.attr("src");
                _this.removeClass("bg");
                _this.attr("src", txt.replace("hidered.png", "hide.png"));
                $("#Mask").hide();
                
                
                //设置隐藏后的整个table的宽度
                var width21=1830;
                for(var i=0;i<arrBig.length;i++){
                	if($(".ch-kp-cb2 .t_r_title table tr th").eq(i).css("display")=="none"){
                		width21=width21-$(".ch-kp-cb2 .t_r_title table tr th").eq(i).width();
                	}
                }
                var width21_dis_t = width21 + 20;
                $(".ch-kp-cb2 table").css("width", width21 + "px");
                $(".ch-kp-cb2 .t_r_title").css("width", width21_dis_t + "px");
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
            $("#Mask").show();
            $(".hideCol").attr("src", $(".hideCol").attr("src").replace("hide.png", "hidered.png"));
        }

    })
}

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