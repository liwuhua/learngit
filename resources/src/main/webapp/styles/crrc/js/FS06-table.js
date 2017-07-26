//点击查询按钮，弹出查询框
var search1=new multiSearch($(".btn1"),"tmaterial",0,10);//物料编码
var search2=new multiSearch1($(".btn2"),"reportFS06/tcustomer",null,null);//客户编码
var search3=new multiSearch2($(".btn3"),"reportFS06/tsalesorgparam",null,null);//销售组织
var search4=new multiSearch2($(".btn4"),"reportFS06/tsalesoff",null,null);//销售部门
var search5=new multiSearch1($(".btn5"),"reportFS06/salesDocType",null,null);//合同类型
var search6=new multiSearch3($(".btn6"),"reportFS06/vbeln",null,null);//合同号
//控制表格高度的显示
var heights = $(window).height();
$("#t_r_content").height((heights - 210) + "px");
//点击收起按钮
$(".retract").off("click").on("click", function () {
    if ($(".container form").css("display") == "block") {
        $(".container form").slideUp();
        $(this).text("展开  ∨");
        var heights = $(window).height();
        $("#t_r_content").animate({
            "height": (heights - 130) + "px"
        });
    } else {
        $(".container form").slideDown();
        $(this).text("收起  ∧");
        var heights = $(window).height();
        $("#t_r_content").height((heights - 210) + ("px"));
    }
})
var arr = [], arrTk = [],arr2 = [], arrTk2 = [];
$("#submit").off("click").on("click",function(){
	var blog = true;
	$(".notSearch").empty();
    var array1=[],array2=[],array3=[],array4=[],array5=[],array6=[],array7=[],arrs=[];
    if ($(".material").val() != "" && $(".material").val().indexOf(";") != -1) {
        arrs = $(".material").val().split(";");
        array1 = arrs[0].split(",");//物料多值
        array6 = arrs[1].split(",");//物料多区间
    } else if ($(".material").val() != "" && $(".material").val().indexOf(";") == -1 && $(".material").val().indexOf(":") != -1) {
        array1 = [];
        array6 = $(".material").val().split(",");
    } else if ($(".material").val() != "" && $(".material").val().indexOf(";") == -1 && $(".material").val().indexOf(":") == -1) {
        array1 = $(".material").val().split(",");
        array6 = [];
    } else if ($(".material").val() == "") {
        array1 = [];
        array6 = [];
    }
	if($(".kunnrValue").val()!=""){
		array2=$(".kunnrValue").val().split(",");//客户编码
	}
	if($(".vkorgValue").val()!=""){
		array3=$(".vkorgValue").val().split(",");//销售组织
		if(blog){
            blog = compareInput(array3,salesOrg,"销售组织");
        }
	}
	if($(".vkburValue").val()!=""){
		array4=$(".vkburValue").val().split(",");//销售部门
		 if(blog){
	            blog = compareInput(array4,salesOff,"销售部门");
	        }
	}
	if($(".auartValue").val()!=""){
		array5=$(".auartValue").val().split(",");//合同类型
	}
	if($(".vbeln").val()!=""){
		array7=$(".vbeln").val().split(",");//合同类型
	}
    var txt1=$("#startTime").val().replace(/-/g,"");//合同单据日期
    var txt2=$("#endTime").val().replace(/-/g,"");
    if(!blog){
        return;
    }
    if(txt1==""||txt2==""){
        alert("请输入日期");
    }else if(parseInt(txt1)>parseInt(txt2)) {
        alert("后一个日期必须大于或等于前一个日期");
    }else{
        $(".Mask").show();
        if (array5.length == 0) {
            array5 = ["ZCQ1", "ZCQ2", "ZCQ3"];
        }
        var item = 0;
        var reportJson = {
            "matnrValue": array1,//物料多值
            "kunnrValue": array2,//客户编码
            "vkorgValue": array3,//销售组织
            "vkburValue": array4,//销售部门
            "auartValue": array5,//合同类型
            "matnrInterval": array6,//物料多区间
            "vbelnValue":array7,//合同号
            "startTime": txt1,
            "endTime": txt2,//合同单据日期
            "startitem": item, //分页
            "pageitem": 20,
            "isExport": false
        }
        reportJson = JSON.stringify(reportJson);
//        导出
        $(".export").on("click", function () {
            reportJson = {
                matnrValue: array1.join(','), //物料多值
                kunnrValue: array2.join(','), //客户编码
                vkorgValue: array3.join(','), //销售组织
                vkburValue: array4.join(','), //销售部门
                auartValue: array5.join(','), //合同类型
                matnrInterval: array6.join(','), //物料多区间
                vbelnValue:array7.join(','),//合同号
                startTime: txt1,
                endTime: txt2, //合同单据日期
                isExport: true
            }
            downloadFile(reportJson, ctx + "/crrc/reportFS06/downloadFile");
        });
        $.ajax({
            url: ctx + "/crrc/reportFS06/result",
            data: reportJson,
            dataType: "json",
            contentType: "application/json;charset=utf-8",
            type: "post",
            success: function (data) {
            	if ('300' === data.statusCode || '301' === data.statusCode) {
                    alert(data.message);
                    $(".Mask").hide();
                    $(".ch-kp-six .t_r_content table").html('');
                    $(".notSearch").next("div").hide();
                    return true;
                }
                var str1 = "";
                if (data.length==0) {
                    $(".notSearch").next("div").hide();
                    $(".notSearch").text("未查询到数据");
                } else {
                    $(".notSearch").next("div").show();
                    if (data.length < 20) {
                        $("#show").html("数据加载完成");
                    } else {
                        $("#show").html("");
                    }
                    $.each(data,function(k,v){
                        str1+="<tr class='trs'>"
                                +"<td style='width:50px;text-align:center;' class='xh'>"+(k+1)+"</td>"
                                +"<td style='width:70px' class='vkbur'>"+v.vkbur+"</td>"
                                +"<td style='width:90px' class='kunnr'>"+v.kunnr+"</td>"
                                +"<td style='width:220px' class='txtmd'>"+v.txtmd+"</td>"
                                +"<td style='width:150px' class='matnr'>"+v.matnr+"</td>"
                                +"<td style='width:350px' class='arktx'>"+v.arktx+"</td>"
                                +"<td style='width:80px' class='vbeln'>"+v.vbeln+"</td>"
                                +"<td style='width:80px' class='posnr'>"+v.posnr+"</td>"
                                +"<td style='width:90px' class='erdat'>"+v.erdat+"</td>"
                                +"<td style='width:60px' class='zmeng'>"+parseFloat(v.zmeng).toFixed(3)+"</td>"
                                +"<td style='width:100px;text-align:right;' class='unit'>"+formatNumber(v.unit,2,1)+"</td>"
                                +"<td style='width:120px' class='sum_unit'>"+formatNumber(v.sum_unit,2,1)+"</td>"
                                +"<td style='width:120px' class='ddcount'>"+parseFloat(v.ddcount).toFixed(3)+"</td>"
                                +"<td style='width:70px' class='fhcount'>"+parseFloat(v.fhcount).toFixed(3)+"</td>"
                                +"<td style='width:70px' class='kpcount'>"+parseFloat(v.kpcount).toFixed(3)+"</td>"
                                +"<td style='width:70px' class='ddstock'>"+v.ddstock+"</td>"
                                +"<td class='uddstock'>"+v.uddstock+"</td>"
                            +"</tr>";
                    })
                    $(".ch-kp-six .t_r_content table").html(str1);
                    trBg( $(".ch-kp-six .t_r_content table"));
                    detail($(".t_r_content table tr"));
                }
                $(".sureBtns").click();
                $(".Mask").hide();
            },
            error: function () {
                alert("数据请求失败");
                $(".Mask").hide();
            }
        });
        item = 20;
        var b = true;
        $("#t_r_content").unbind("scroll").bind("scroll", function () {
        	toTop($(".toTop"),$("#t_r_content"));
            var fold = $("#t_r_content").height() + $("#t_r_content")[0].scrollTop+$("#show").height();
            if (b && fold >= $("#t_r_content")[0].scrollHeight) {
                b = false;
                var reportJson = {
                    "matnrValue": array1,//物料多值
                    "kunnrValue": array2,//客户编码
                    "vkorgValue": array3,//销售组织
                    "vkburValue": array4,//销售部门
                    "auartValue": array5,//合同类型
                    "matnrInterval": array6,//物料多区间
                    "vbelnValue":array7,//合同号
                    "startTime": txt1,
                    "endTime": txt2,//合同单据日期
                    "startitem": item, //分页
                    "pageitem": 20
                }
                reportJson = JSON.stringify(reportJson);
                if ($("#show").text() != "数据加载完成") {
                	$(".Mask").show();
                    $.ajax({
                        url: ctx + "/crrc/reportFS06/result",
                        data: reportJson,
                        dataType: "json",
                        contentType: "application/json;charset=utf-8",
                        type: "post",
                        success: function (data) {
                            if (data.length < 20) {
                                $("#show").html("数据加载完成");
                            }
                            var str = "";
                            $.each(data,function (key, val) {
                                str += "<tr class='trs'>"
		                                	+"<td style='width:50px' class='xh'>"+(key+1+item)+"</td>"
		                                    +"<td style='width:70px' class='vkbur'>"+val.vkbur+"</td>"
		                                    +"<td style='width:90px' class='kunnr'>"+val.kunnr+"</td>"
		                                    +"<td style='width:220px' class='txtmd'>"+val.txtmd+"</td>"
		                                    +"<td style='width:150px' class='matnr'>"+val.matnr+"</td>"
		                                    +"<td style='width:350px' class='arktx'>"+val.arktx+"</td>"
		                                    +"<td style='width:80px' class='vbeln'>"+val.vbeln+"</td>"
		                                    +"<td style='width:80px' class='posnr'>"+val.posnr+"</td>"
		                                    +"<td style='width:90px' class='erdat'>"+val.erdat+"</td>"
		                                    +"<td style='width:60px' class='zmeng'>"+parseFloat(val.zmeng).toFixed(3)+"</td>"
		                                    +"<td style='width:100px;text-align:right;' class='unit'>"+formatNumber(val.unit,2,1)+"</td>"
		                                    +"<td style='width:120px' class='sum_unit'>"+formatNumber(val.sum_unit,2,1)+"</td>"
		                                    +"<td style='width:120px' class='ddcount'>"+parseFloat(val.ddcount).toFixed(3)+"</td>"
		                                    +"<td style='width:70px' class='fhcount'>"+parseFloat(val.fhcount).toFixed(3)+"</td>"
		                                    +"<td style='width:70px' class='kpcount'>"+parseFloat(val.kpcount).toFixed(3)+"</td>"
		                                    +"<td style='width:70px' class='ddstock'>"+val.ddstock+"</td>"
		                                    +"<td class='uddstock'>"+val.uddstock+"</td>"
                                        +"</tr>";
                            });
                            item = item + 20;
                            $(".ch-kp-six .t_r_content table").append(str);
                            trBg( $(".ch-kp-six .t_r_content table"));
                            detail($(".t_r_content table tr"));
                            $(".sureBtns").click();
                            $(".Mask").hide();
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
    $(".hideCol").off("click").on("click",function(){
//        $(".hideColBox").show();
//        $("#Mask").show();
    	var _this = $(this);
    	if (!$(this).hasClass("bg")) {
            $(this).addClass("bg");
            var txt = $(this).attr("src");
            $(this).attr("src", txt.replace("hide.png", "hidered.png"));
            $(".hideColBox").show();
            $("#Mask").show();
            //allCheck($(".allCheck"));

            //获取表头名称
			var thArr=[]; 
			for(var i=0;i<$(".ch-kp-six .t_r_title table tr").find("th").length;i++){
				thArr.push($(".ch-kp-six .t_r_title table tr").find("th").eq(i).text())
			}
			//循环表头将名称添加到弹出框中
			var str1="";
			for(var j=0;j<thArr.length;j++){
				str1+="<tr>"
					+"<td style='width:50px;text-align:center;'><input type='checkbox' checked='checked' class='checks'></td>"
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
            $(".hideTable").off("click").on("click", ".checks", function () {
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
            $(".sureBtns").on("click", function () {

                //弹出框里的不被选中的已经放倒一个数组里，判断表头有这个内容就将此隐藏
                //表头数组
                var arrBig = [];
                for (var j = 0; j < $(".ch-kp-six .t_r_title table tr th").length; j++) {
                    var thBig = $(".ch-kp-six .t_r_title table tr th").eq(j).text();
                    arrBig.push(thBig)
                }
                // 不被选中的text的字段
                //被选中的text的字段
                var textArr=[];
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
                        var className = $(".ch-kp-six  .t_r_title table tr th").eq($.inArray(textArr[n], arrBig)).attr("class");
                        arrTrue.push(className);
                    }
                }
                for (var m = 0; m < arrTrue.length; m++) {
                    $(".notSearch+div .t_r").find("." + arrTrue[m]).show();
                }
                
                

                //不被选中的class名
                var arrN = [];
                for (var n = 0; n < arr.length; n++) {
                    if ($.inArray(arr[n], arrBig) >= 0) {
                        var className = $(".ch-kp-six .t_r_title table tr th").eq($.inArray(arr[n], arrBig)).attr("class");
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
            })
        } else {
            $(this).removeClass("bg");
            var txt = $(this).attr("src");
            $(this).attr("src", txt.replace("hidered.png", "hide.png"));
            $(".hideColBox").hide();
            $("#Mask").hide();
        }
    });
    $(".canBtns").on("click",function(){
        hiddenCol();
    });
    $(".closeBtn").on("click",function(){
        hiddenCol();
    });
    function hiddenCol(){
        $(".hideColBox").hide();
        $("#Mask").hide();
    }
});
function detail(btnTr){
    btnTr.off("dblclick").on("dblclick",function(){
        $("#Mask").show();
        $(".table-detail div table:eq(1)").html("");
        $("#t_r_content tr:nth-child(2n-1)").css({"background":"#ededed"});
        $("#t_r_content tr:nth-child(2n)").css({"background":"#ffffff"});
        $(this).css({"background":"#c71009"});
        var vbeln1=$(this).find(".vbeln").text();
        var posnr1=$(this).find(".posnr").text();
        $.ajax({
            url: ctx + "/crrc/reportFS06/result_sub?vbeln="+vbeln1+"&posnr="+posnr1,
            dataType: "json",
            contentType: "application/json;charset=utf-8",
            type: "get",
            success: function (data) {
            	if ('300' === data.statusCode || '301' === data.statusCode) {
                    alert(data.message);
                    $(".Mask").hide();
                    $("#t_r_content2 table").html('');
                    $(".table-detail").hide();
                    return true;
                }
                if(data.length==0){
                    $(".table-detail").css("display","none");
                    alert("未查询到明细!");
                    $("#Mask").hide();
                    
                }else{
                    $(".table-detail").css("display","block");
                    if (data.length < 20) {
                        $("#show").html("数据加载完成");
                    } else {
                        $("#show").html("");
                    }
                    var str1s = "";
                    $.each(data,function(k,v){
                        str1s+="<tr>"
	                        	+"<td style='width:50px;text-align:center;' class='xh2'>"+(k+1)+"</td>"
	                            +"<td style='width:70px' class='lips_erdat'>"+v.lips_erdat+"</td>"
	                            +"<td style='width:90px' class='kunnr'>"+v.kunnr+"</td>"
                                +"<td style='width:250px' class='kunnr'>"+v.txtmd+"</td>"
	                            +"<td style='width:90px' class='lips_vbeln'>"+v.lips_vbeln+"</td>"
	                            +"<td style='width:90px;text-align:right;' class='lgmng'>"+parseFloat(v.lgmng).toFixed(3)+"</td>"
	                            +"<td style='width:250px' class='sernr'>"+v.sernr+"</td>"
	                            +"<td style='width:80px' class='kdauf'>"+v.kdauf+"</td>"
	                            +"<td style='width:70px' class='kdpos'>"+v.kdpos+"</td>"
	                            +"<td style='width:80px' class='vbak_erdat'>"+v.vbak_erdat+"</td>"
	                            +"<td style='width:60px;text-align:right;' class='kwmeng'>"+parseFloat(v.kwmeng).toFixed(3)+"</td>"
	                            +"<td style='width:70px' class='vbrp_erdat'>"+v.vbrp_erdat+"</td>"
	                            +"<td style='width:70px;text-align:right;' class='fklmg'>"+parseFloat(v.fklmg).toFixed(3)+"</td>"
	                            +"<td class='vbrp_vbeln'>"+v.vbrp_vbeln+"</td>"
                            +"</tr>";
                    });
                    $("#t_r_content2 table").html(str1s);
                    trBg($("#t_r_content2 table"));
                }
                $(".sureBtns2").click()

            },
            error: function () {
                alert("数据请求失败!");
                $(".Mask").hide();
            }
        });
        var item2=20;
        var b = true;
        $("#t_r_content2").bind("scroll", function () {
        	toTop($(".toTop1"),$("#t_r_content2"));
            var fold = $("#t_r_content2").height() + $("#t_r_content2")[0].scrollTop+$("#show2").height();
            if (b && fold >= $("#t_r_content2")[0].scrollHeight) {
                b = false;
                if ($("#show2").text() != "数据加载完成") {
                    $.ajax({
                        url: ctx + "/crrc/reportFS06/result_sub?vbeln=4000000753&posnr=000020&startitem="+item2+"&pageitem=20",
                        dataType: "json",
                        contentType: "application/json;charset=utf-8",
                        type: "get",
                        success: function (data) {
                            if (data.length < 20) {
                                $("#show2").html("数据加载完成");
                            }
                            var strs = "";
                            $.each(data,function (key, val) {
                                strs+="<tr class='Trs'>"
                                        +"<td style='width:50px' class='xh2'>"+(key+1+item2)+"</td>"
                                        +"<td style='width:70px' class='lips_erdat'>"+val.lips_erdat+"</td>"
                                        +"<td style='width:90px' class='kunnr'>"+val.kunnr+"</td>"
                                        +"<td style='width:250px' class='kunnr'>"+val.txtmd+"</td>"
                                        +"<td style='width:90px' class='lips_vbeln'>"+val.lips_vbeln+"</td>"
                                        +"<td style='width:90px;text-align:right;' class='lgmng'>"+parseFloat(v.lgmng).toFixed(3)+"</td>"
                                        +"<td style='width:250px' class='sernr'>"+val.sernr+"</td>"
                                        +"<td style='width:80px' class='kdauf'>"+val.kdauf+"</td>"
                                        +"<td style='width:70px' class='kdpos'>"+val.kdpos+"</td>"
                                        +"<td style='width:80px' class='vbak_erdat'>"+val.vbak_erdat+"</td>"
                                        +"<td style='width:60px;text-align:right;' class='kwmeng'>"+parseFloat(v.kwmeng).toFixed(3)+"</td>"
                                        +"<td style='width:70px' class='vbrp_erdat'>"+val.vbrp_erdat+"</td>"
                                        +"<td style='width:70px;text-align:right;' class='fklmg'>"+parseFloat(v.fklmg).toFixed(3)+"</td>"
                                        +"<td class='vbrp_vbeln'>"+val.vbrp_vbeln+"</td>"
                                    +"</tr>";
                            });
                            item2 = item2 + 20;
                            $("#t_r_content2 table").append(strs);
                            trBg($("#t_r_content2 table"));
                            $(".sureBtns2").click();
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
        
        var arr2 = [], arrTk2 = [];
        //弹出框里的隐藏列
        $(".hideCol2").off("click").on("click",function(){
        	var _this = $(this);
	    	if (!$(this).hasClass("bg")) {
	            $(this).addClass("bg");
	            var txt = $(this).attr("src");
	            $(this).attr("src", txt.replace("hide.png", "hidered.png"));
	            $(".hideColBox2").show();
	            $("#Mask2").show();
	            //allCheck($(".allCheck"));
	
	            //获取表头名称
				var thArr2=[]; 
				for(var i=0;i<$(".table-detail .t_r_title table tr").find("th").length;i++){
					thArr2.push($(".table-detail .t_r_title table tr").find("th").eq(i).text())
				}
				//循环表头将名称添加到弹出框中
				if($(".hideTable2 .t_r_content table").html().indexOf('td')==-1){
					var str2="";
					for(var j=0;j<thArr2.length;j++){
						str2+="<tr>"
							+"<td style='width:50px;text-align:center;'><input type='checkbox' checked='checked' class='checks2'></td>"
							+"<td>"+thArr2[j]+"</td>"
						+"</tr>";
					}
					$(".hideTable2 .t_r_content table").html(str2);
					trBg($(".hideTable2 .t_r_content table"));
				}
				
	            //再次点击弹出框之前未被选中的仍未被选中
	            //弹出框里的文本
	            var arrTk2 = [];
	            for (var i = 0; i < $(".hideTable2 .t_r_content tr").length; i++) {
	                arrTk2.push($(".hideTable2 .t_r_content tr").eq(i).find("td").text());
	            }
	
	
	            var val2 = "";
	            $(".hideTable2 .t_r_content").on("click", ".checks2", function () {
	                if ($(this).prop("checked") == false) {
	                    val2 = $(this).parent().next("td").text();
	                    arr2.push(val2)
	                } else {
	                    val2 = $(this).parent().next("td").text();
	                    $.each(arr2, function (k, v) {
	                        if (v == val2) {
	                            arr2.splice(k, 1)
	                        }
	                    })
	                }
	            });
	            for (var i = 0; i < arrTk2.length; i++) {
	                for (var j = 0; j < arr2.length; j++) {
	                    if ($(".hideTable2 .t_r_content tr:eq(" + i + ") td:eq(1)").text() == arr2[j]) {
	                        $(".hideTable2 .t_r_content tr:eq(" + i + ") td:eq(0)").find("input").prop("checked", false)
	                    }
	                }
	            }

	 
	            //所有的复选框默认的都是选中状态,点击一个复选框，全选的复选框不再被选中
	            $(".hideTable2").on("click", ".checks2", function () {
	                var inputLength = $(".hideTable2 .checks2").length;
	                var incheckedLength = $(".hideTable2 .checks2[type='checkbox']:checked").length;
	                if (incheckedLength < inputLength) {
	                    $(".allCheck2").prop("checked", false)
	                } else {
	                    $(".allCheck2").prop("checked", true)
	                }
	            })
	            
	            $(".allCheck2").on("click", function () {
	                if ($(this).prop("checked")) {
	                    $(this).parents(".hideTable2 .t_r_t").next().find("table input").prop("checked", true);
	                    arr2=[];
	                }else{
	                	$(this).parents(".hideTable2 .t_r_t").next().find("table input").prop("checked", false);
	                	for(var i=0;i<$(this).parents(".t_r_t").next().find("table input").length;i++){
		            		var t=$(this).parents(".t_r_t").next().find("table input").eq(i).parent().next().text();
			                arr2.push(t)
		            	}
	                }
	            })
	
	
	            //点击取消和x弹出框消失，点击确定相应的列消失
	            can2($(".closeBtn2"));
	            can2($(".canBtns2"));
	            $(".sureBtns2").on("click", function () {
	
	
	
	                //弹出框里的不被选中的已经放倒一个数组里，判断表头有这个内容就将此隐藏
	                //表头数组
	                var arrBig2 = [];
	                for (var j = 0; j < $(".table-detail .t_r_title table tr th").length; j++) {
	                    var thBig2 = $(".table-detail .t_r_title table tr th").eq(j).text();
	                    arrBig2.push(thBig2)
	                }
	                 //被选中的text的字段
	                var textArr2 = [];
	                for (var n = 0; n < arrBig2.length; n++) {
	                    if ($(".hideTable2 .t_r_content tr").eq(n).find("input[type='checkbox']").prop("checked")) {
	                        var checkTrue2 = $(".hideTable2 .t_r_content tr").eq(n).find("input[type='checkbox']").parent("td").next().text();
	                        textArr2.push(checkTrue2);
	                    }
	                }
	                //被选中的class名
	                var arrTrue2 = [];
	                for (var n = 0; n < textArr2.length; n++) {
	                    if ($.inArray(textArr2[n], arrBig2) >= 0) {
	                        var className2 = $(".table-detail .t_r_title table tr th").eq($.inArray(textArr2[n], arrBig2)).attr("class");
	                        arrTrue2.push(className2);
	                    }
	                }
	                for (var m = 0; m < arrTrue2.length; m++) {
	                    $(".table-detail .t_r").find("." + arrTrue2[m]).show();
	                }
	              //不被选中的class名
	                var arrN2 = [];
	                for (var n = 0; n < arr2.length; n++) {
	                    if ($.inArray(arr2[n], arrBig2) >= 0) {
	                        var className2 = $(".table-detail .t_r_title table tr th").eq($.inArray(arr2[n], arrBig2)).attr("class");
	                        arrN2.push(className2);
	                    }
	                }
	                //不被选中的class消失
	                for (var m = 0; m < arrN2.length; m++) {
	                    $(".table-detail .t_r").find("." + arrN2[m]).hide();
	                }
	
	                $(".hideColBox2").hide();
	                var txt = _this.attr("src");
	                _this.removeClass("bg");
	                _this.attr("src", txt.replace("hidered.png", "hide.png"));
	                $("#Mask2").hide();
	            })
	        } else {
	            $(this).removeClass("bg");
	            var txt = $(this).attr("src");
	            $(this).attr("src", txt.replace("hidered.png", "hide.png"));
	            $(".hideColBox2").hide();
	            $("#Mask2").hide();
	        }
        })
    });
}
$(".detail-del .del").on("click",function(){
    $(this).parents(".table-detail").hide();
    $("#Mask").hide();
});
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
function can2(btn) {
    btn.off("click").on("click", function () {
        if ($(".hideColBox2").css("display") == "block") {
            $(".hideCol2").removeClass("bg");
            $(".hideColBox2").hide();
            $("#Mask2").hide();
            $(".hideCol2").attr("src", $(".hideCol2").attr("src").replace("hidered.png", "hide.png"));
        } else if ($(".hideColBox2").css("display") == "none") {
            $(".hideCol2").addClass("bg");
            $(".hideColBox2").show();
            $(".hideCol2").attr("src", $(".hideCol2").attr("src").replace("hide.png", "hidered.png"));
            $("#Mask2").show();
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