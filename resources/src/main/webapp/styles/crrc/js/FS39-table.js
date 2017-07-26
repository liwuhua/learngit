//点击查询按钮，弹出查询框 
var search1 = new multiSearch3($(".btn1"), "reportFS39/groes", null, null);//产成品
var search2 = new multiSearch3($(".btn2"), "reportFS39/ltxa1", null, null);//工序名称
var search3 = new multiSearch($(".btn3"), "tmaterial", 0, 10);//物料编码 
var search4 = new multiSearch3($(".btn4"), "reportFS39/kdauf", null, null);//销售订单
var search5 = new multiSearch3($(".btn5"), "reportFS39/kdpos", null, null);//销售订单行号
var search6 = new multiSearch2($(".btn6"), "reportFS39/tplant", null, null);//工厂
var search7 = new multiSearch1($(".btn7"), "reportFS39/arbpl", null, null);//工作中心
var search8 = new multiSearch3($(".btn8"), "reportFS39/xqdw", null, null);//需求单位
var search9 = new multiSearch3($(".btn9"), "reportFS39/auart", null, null);//生产订单类型
//收起展开控制高度
var heights = $(window).height();
var heights_f = $(".container .col-sm-12 form").height();
$(".thirtyNineTable .t_r_content").css("height", (heights - heights_f - 260) + "px");
$(".retract").off("click").on("click", function () {
    if ($(".container form").css("display") == "block") {
        $(".container form").slideUp();
        $(this).text("展开  ∨");
        var heights = $(window).height();
        $(".thirtyNineTable .t_r_content").animate({
            "height": (heights - 260) + "px"
        });
    } else {
        $(".container form").slideDown();
        $(this).text("收起  ∧");
        var heights = $(window).height();
        $(".thirtyNineTable .t_r_content").css("height", (heights - heights_f - 260) + "px");
    }
});
var arr_data=[];
$("#submit").off("click").on("click", function () {
    var blog = true;
    $(".notSearch").empty();
    $("#show").html("");
    var arrs = [], array1 = [], array2 = [], array3 = [], array4 = [], array5 = [], array6 = [], array7 = [], array8 = [], array9 = [], array10 = [], array11 = [];
    if ($(".ccp").val() != "") {
        array1 = $(".ccp").val().split(",");//产成品
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
    if ($(".gxmc").val() != "") {
        array3 = $(".gxmc").val().split(",");//工序名称
    }
    if ($(".xsdd").val() != "") {
        array4 = $(".xsdd").val().split(",");//销售订单
    } 
    if ($(".xsddhh").val() != "") {
        array5 = $(".xsddhh").val().split(",");//销售订单行号
    }
    if ($(".factory").val() != "") {
        array7 = $(".factory").val().split(",");//工厂
        if (blog) {
            blog = compareInput(array7, plant, "工厂");
        }
    }
    if ($(".gzzx").val() != "") {
        array8 = $(".gzzx").val().split(",");//工作中心
    }
    if ($(".xqdw").val() != "") {
        array9 = $(".xqdw").val().split(",");//需求单位
    }
    if ($(".scddlx").val() != "") {
        array10 = $(".scddlx").val().split(",");//生产订单类型
    }
    var part=/^[\d]*$/g;
    if ($(".rnl").val() != "" && part.test($(".rnl").val())) {
        array11 = $(".rnl").val().split(",");//日能力
    }else if ($(".rnl").val() != "" && !part.test($(".rnl").val())) {
    	alert("请输入正确的日能力");
    	return true;
    }
    var txt1 = $("#startTime").val().replace(/-/g, "");//基本完成日期
    var txt2 = $("#endTime").val().replace(/-/g, "");

 
    var reportJson = {};
    var reportJson = {
        "groesValue": array1, //产成品
        "matnrValue": array2, //物料编码多值
        "matnrInterval": array6, //物料多区间
        "ltxa1Value": array3, //工序名称
        "kdaufValue": array4, //销售订单
        "kdposValue": array5, //销售订单行号
        "plantValue":array7,//工厂
        "arbplValue":array8,//工作中心
        "xqdwValue":array9,//需求单位
        "auartValue":array10,//生产订单类型
       // "dwerkValue":array11,//日能力
        "startTime": txt1,
        "endTime": txt2 //销售订单交货日期
    };
    reportJson = JSON.stringify(reportJson);
    if (!blog) {
        return;
    }
    if (txt1==""||txt2=="") {
    	alert("请输入日期");
    } else if (parseInt(txt1) > parseInt(txt2)) {
        alert("后一个日期必须大于或等于前一个日期");
    } else {
    
    var arrNews=[],arr=[],arrNew=[];
        $(".Mask").show();
        $.ajax({
            url: ctx + "/crrc/reportFS39/result",
            data: reportJson,
            dataType: "json",
            contentType: "application/json;charset=utf-8",
            type: "post",
            success: function (data) {
            	if ('300' === data.statusCode || '301' === data.statusCode) {
                    alert(data.message);
                    $(".Mask").hide();
                    $(".thirtyNineBox").html('');
                    return true;
                } else {
                	$(".notSearch").next("div").show();
                    $(".dataTime1").text(getTime1(txt1, txt2));
                    var arrSum = [];
                    if (jQuery.isEmptyObject(data)) {
                    	$(".notSearch").text("未查询到数据");
                        alert("未查询到数据");
                        $(".notSearch").next("div").hide();
                        $(".Mask").hide();
                        return;
                    }
                    if(data.data.length==0){
                    	$(".notSearch").text("未查询到数据");
                    	alert("未查询到数据");
                    	$(".notSearch").next("div").hide();
                        $(".Mask").hide();
                        return;
                    }
                    $(".thirtyNineTable table").html("");
                    if(data.data.length<20){
                    	$("#show").html()=="数据加载完成"
                    }
                    arr_data=data.data;
                    var textMax = data.maxTime.slice(0, 4) + "-" + data.maxTime.slice(4, 6) + "-" + data.maxTime.slice(6, 8);
                    var textMin = data.minTime.slice(0, 4) + "-" + data.minTime.slice(4, 6) + "-" + data.minTime.slice(6, 8);
                    arrSum.push(textMax);
                    arrSum.push(textMin);
                     arr = getDays(arrSum[1], arrSum[0]);
                     arrNew = arr.unique3();
                    
                    for(var i=0;i<arrNew.length; i++){
                    	arrNews.push(arrNew[i].replace(/-/g, ""))
                    }
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
                        /*console.log("值:"+res[i][0]+" 重复次数:"+res[i][1]+"<br/>");*/
                    }
                    var len = arrNew.length;
                    var str_r_t = "", str_l_t = "", str_l_b = "", str_r_b = "";
                    var width39 = len * 40+840;
                    var width39_t = width39 + 20;
                    $(".thirtyNineTable table").css("width", width39 + "px");
                    $(".thirtyNineTable .t_r_title").css("width", width39_t + "px");
                    $("#t_r_b .t_r_foot").css("width",width39_t + "px");
                    //头部
                    str_l_t = "<tr>"
	                            + "<th rowspan='2' width='40px' class='AUFNR'>序号</th>"//----
	                            + "<th rowspan='2' width='90px' class='GROES'>产成品</th>"//----
	                            + "<th rowspan='2' width='100px' class='LTXA1'>工序名称</th>"
	                            + "<th rowspan='2' width='110px' class='MATNR'>物料</th>"//----
	                            + "<th rowspan='2' width='280px' class='arktx'>物料描述</th>"//----
	                            + "<th rowspan='2' width='140px' class='KDFS'>营销订单/行号</th>"//----
	                            + "<th rowspan='2' width='40px' class='MENGA'>台<br/>用<br/>量</th>"//----
	                            + "<th rowspan='2' width='50px' class='DWERK'>工厂</th>"//----
	                            + "<th rowspan='2' width='80px' class='ARBPL'>工作中心</th>"//---
	                            + "<th rowspan='2' width='90px' class='KTEXT'>工作中心描述</th>"//---
	                            + "<th rowspan='2' width='80px' class='XQDW'>下工序</th>"//---
	                            + "<th rowspan='2' width='80px' class='XARBPL'>需求单位</th>"//---
	                            + "<th rowspan='2' width='100px' class='XKTEXT'>需求单位描述</th>"//---
	                            + "<th rowspan='2' width='50px' class='LJWWCSL'>累计未完工<br/>数量</th>"//---
	                            + "<th rowspan='2' width='40px' class='PL'>批量</th>"//---
	                            + "<th rowspan='2' width='40px' class='LJJHSL'>当年累计数量</th>"//---
	                            + "<th rowspan='2' width='40px' class='DQJH'>当期订单数量</th>"//---
	                            + "<th rowspan='2' width='40px' class='LTRMI'>计划/产出</th>"//---	                            
	                            var i = 0;
	                            for (var j = 0; j < arrY2.length; j++) {
	                                i < arrY1.length;
	                                str_l_t += "<th class='data-month' colspan='" + arrY2[j] + "'>" + arrY1[i].slice(4) + "</th>";
	                                i++;
	                            }
	                str_l_t+= "</tr><tr>"
                	for (var i = 0; i < len; i++) {
                    	str_l_t += "<th class='data-data' width='40px'>" + arrNew[i].slice(8) + "</th>";
                    }
	                str_l_t+= "</tr>"
                    $(".thirtyNineTable .t_r_title table").html(str_l_t);
                    $.each(data.data, function (k, v) {
                        //身体部分
                    	if(k<20){
                    		str_l_b += "<tr style='position:relative;'>"
		                                + "<td rowspan='2' width='40px' class='AUFNR'>" + ( k + 1 ) + "</td>"//序号
		                                + "<td rowspan='2' width='90px' class='GROES'>" + v.GROES + "</td>"//产成品
		                                + "<td rowspan='2' width='100px' class='LTXA1'>"+v.LTXA1+"</td>"//工序名称
		                                + "<td rowspan='2' width='110px' class='MATNR'>" + v.MATNR + "</td>"//物料
		                                + "<td rowspan='2' width='280px' class='arktx'>" + v.ARKTX + "</td>"//物料描述
		                                + "<td rowspan='2' width='140px' class='KDFS'>" + v.KDFS + "</td>"//营销订单/行号
		                                + "<td rowspan='2' width='40px' class='MENGA' style='text-align:right;'>" + checkStr(v.MENGA) + "</td>"//台用量
		                                + "<td rowspan='2' width='50px' class='DWERK'>" + v.DWERK + "</td>"//工厂
		                                + "<td rowspan='2' width='80px' class='ARBPL'>" + v.ARBPL + "</td>"//工作中心
		                                + "<td rowspan='2' width='90px' class='KTEXT'>" + v.KTEXT + "</td>"//工作中心描述
		                                + "<td rowspan='2' width='80px' class='XQDW'>" + v.XQDW + "</td>"//下工序
		                                + "<td rowspan='2' width='80px' class='XARBPL'>" + v.XARBPL + "</td>"//需求单位
		                                + "<td rowspan='2' width='100px' class='XKTEXT'>" + v.XKTEXT + "</td>"//需求单位描述
		                                + "<td rowspan='2' width='50px' class='LJWWCSL' style='text-align:right;'>" + v.LJWWCSL + "</td>"//累计未完工数量
		                                + "<td rowspan='2' width='40px' class='PL' style='text-align:right;'>" + CheckUndefined(v.PL) + "</td>"//批量
		                                + "<td rowspan='1' width='40px' class='LJJHSL' style='text-align:right;'>" + CheckUndefined(v.LJJHSL) + "</td>"//当年累计数量
		                                + "<td rowspan='1' width='40px' class='DQJH' style='text-align:right;'>" + CheckUndefined(v.DQJH) + "</td>"//当期订单数量
		                                + "<td rowspan='1' width='40px' class='JH'>计划</td>"//计划/产出
		                                for (var n = 0; n < arrNew.length; n++) {
		                                	str_l_b += "<td class='data-r' width='40px' style='text-align:right;'></td>";
		    	                        }
		                str_l_b+="</tr>"
		                        +"<tr>"
		                                + "<td rowspan='1' width='40px' class='LJCCSL' style='text-align:right;'>" + CheckUndefined(v.LJCCSL) + "</td>"//当年累计数量
		                                + "<td rowspan='1' width='40px' class='DQCC' style='text-align:right;'>" + CheckUndefined(v.DQCC) + "</td>"//当期订单数量
		                                + "<td rowspan='1' width='40px' class='CC'>产出</td>"//计划/产出
		                                for (var n = 0; n < arrNew.length; n++) {
		                                	str_l_b += "<td class='data-r' width='40px' style='text-align:right;'></td>";
		    	                        }
		                str_l_b+= "</tr>";
                    	}else{
                    		return;
                    	}
                    });
                    $(".thirtyNineTable .t_r_content table").html(str_l_b);
                    $.each(data.data, function (k, v) {
                    	if(k<20){
                    		//var arrNews=['20150614','20150615','20150616','20150617'];
                            var ind1,ind2,arr1=[],arr2=[],text1=[],text2=[];
                            $.each(v,function(key,val){
                                if(key.indexOf("JH")>0 && key.length==10){
                                    text1.push(parseInt(val))
                                    var keyNew1=key.slice(0,8);
                                    ind1=arrNews.indexOf(keyNew1);
                                    arr1.push(ind1)
                                }
                                if(key.indexOf("CC")>0 && key.length==10){
                                    text2.push(parseInt(val))
                                    var keyNew2=key.slice(0,8);
                                    ind2=arrNews.indexOf(keyNew2);
                                    arr2.push(ind2)
                                }
                            });
                            if(text1.length!=0){
                                for(var i=0;i<arr1.length;i++){
                                	/*if(text1[i]=="1"){*/
                                	var tops=($("#t_r_content table tr").eq(2*Number(k)).find("td.data-r").height()-14)/4+"px";
                                	var str="<div width='78px' style='padding:0px 5px;position:relative;top:0px;height:16px;background-color:#2b7bbf;color:#fff;'>"+text1[i]+"</div>";                                			
                                	$("#t_r_content table tr").eq(2*Number(k)).find("td.data-r").eq(arr1[i]).html(str);
                                	$("#t_r_content table tr").eq(2*Number(k)).find("td.data-r").eq(arr1[i]).css("padding","2px 0px");
                                	/*}else{
                                		$("#t_r_content table tr").eq(2*Number(k)).find("td.data-r").eq(arr1[i]).text(text1[i]);
                                	}*/
                                }
                            }
                            if(text2.length!=0){
                                for(var i=0;i<arr2.length;i++){
                                    $("#t_r_content table tr").eq(2*Number(k)+1).find("td.data-r").eq(arr2[i]).text(text2[i]);
                                }
                            }
                    	}else{
                    		return;
                    	}
                    });
                    var zongshu=0;
                    $.each(data,function(key,val){
                        if(key.indexOf("data")!=-1 || key.indexOf("Time")!=-1){
                            return;
                        }else{
                        	zongshu=zongshu+val;                       	
                        }
                    }); 
                    //添加底部数据
                    var str="<tr>"
		                    	 + "<td width='40px' class='AUFNR'></td>"//序号
		                         + "<td width='90px' class='GROES'></td>"//产成品
		                         + "<td width='100px' class='LTXA1'></td>"//工序名称
		                         + "<td width='110px' class='MATNR'></td>"//物料
		                         + "<td width='280px' class='arktx'></td>"//物料描述
		                         + "<td width='140px' class='KDFS'></td>"//营销订单/行号
		                         + "<td width='40px' class='MENGA'></td>"//台用量
		                         + "<td width='50px' class='DWERK'></td>"//工厂
		                         + "<td width='80px' class='ARBPL'></td>"//工作中心
		                         + "<td width='90px' class='KTEXT'></td>"//工作中心描述
		                         + "<td width='80px' class='XQDW'></td>"//下工序
		                         + "<td width='80px' class='XARBPL'></td>"//需求单位
	                             + "<td width='100px' class='XKTEXT'></td>"//需求单位描述
		                         + "<td width='50px' class='LJWWCSL'></td>"//累计未完工数量
		                         //+ "<td width='30px' class='PL'></td>"//批量
                    			 + "<td width='80px' style='text-indent:0px;'>日排产</td>"
                    			 + "<td width='80px' style='color:#fff;text-align:right;background-color:#777;'>"+formatNumber(zongshu,0,1)+"</td>"//总数
                    			for (var n = 0; n < arrNew.length; n++) {
                    				str+= "<td class='data-rpc' width='40px' style='text-align:right;text-indent:0px;'></td>";
    	                        }
                    	str+="</tr>"
                    		+"<tr>"
                    			+ "<td width='40px' class='AUFNR'></td>"//序号
                    			+ "<td width='90px' class='GROES'></td>"//产成品
                    			+ "<td width='100px' class='LTXA1'></td>"//工序名称
                    			+ "<td width='110px' class='MATNR'></td>"//物料
                    			+ "<td width='280px' class='arktx'></td>"//物料描述
                    			+ "<td width='140px' class='KDFS'></td>"//营销订单/行号
                    			+ "<td width='40px' class='MENGA'></td>"//台用量
                    			+ "<td width='50px' class='DWERK'></td>"//工厂
                    			+ "<td width='80px' class='ARBPL'></td>"//工作中心
                    			+ "<td width='90px' class='KTEXT'></td>"//工作中心描述
                    			+ "<td width='80px' class='XQDW'></td>"//下工序
                    			+ "<td width='80px' class='XARBPL'></td>"//需求单位
                                + "<td width='100px' class='XKTEXT'></td>"//需求单位描述
                    			+ "<td width='50px' class='LJWWCSL'></td>"//累计未完工数量
                    			//+ "<td width='30px' class='PL'></td>"//批量
	                			+"<td width='80px' style='text-indent:0px;'>日能力</td>"
	                			+ "<td width='80px'></td>"//总数
								for (var n = 0; n < arrNew.length; n++) {
									str+= "<td class='data-rnl' width='40px' style='text-align:right;text-indent:0px;'></td>";
								}
                    	str+="</tr>"
	                		+"<tr>"
	                			+ "<td width='40px' class='AUFNR'></td>"//序号
	                			+ "<td width='90px' class='GROES'></td>"//产成品
	                			+ "<td width='100px' class='LTXA1'></td>"//工序名称
	                			+ "<td width='110px' class='MATNR'></td>"//物料
	                			+ "<td width='280px' class='arktx'></td>"//物料描述
	                			+ "<td width='140px' class='KDFS'></td>"//营销订单/行号
	                			+ "<td width='40px' class='MENGA'></td>"//台用量
	                			+ "<td width='50px' class='DWERK'></td>"//工厂
	                			+ "<td width='80px' class='ARBPL'></td>"//工作中心
	                			+ "<td width='90px' class='KTEXT'></td>"//工作中心描述
	                			+ "<td width='80px' class='XQDW'></td>"//下工序
	                			+ "<td width='80px' class='XARBPL'></td>"//需求单位
                                + "<td width='100px' class='XKTEXT'></td>"//需求单位描述
	                			+ "<td width='50px' class='LJWWCSL'></td>"//累计未完工数量
	                			//+ "<td width='30px' class='PL'></td>"//批量 
	                			+"<td width='80px' style='text-indent:0px;'>日能力负荷(%)</td>"
	                			+ "<td width='80px'></td>"//总数
								for (var n = 0; n < arrNew.length; n++) {
									str+= "<td class='data-fh' width='40px' style='text-align:right;text-indent:0px;'></td>";
								}
                    	str+="</tr>"
	                $("#t_r_b table").html(str);
                    	$(".notSearch+div .thirtyNineTable #t_r_b td").css("height","25px");
                    //添加日排产数量
                	//var arrNews=['20150614','20150615','20150616','20150617'];
                	var ind,arr=[],text=[];
                	$.each(data,function(key,val){
                        if(key.indexOf("data")!=-1 || key.indexOf("Time")!=-1){
                            return;
                        }else{
                        	text.push(val)
                        	var keyNew=key.slice(0,8);
                        	ind=arrNews.indexOf(keyNew);
                            arr.push(ind);                        	
                        }
                    }); 
                	if(text.length!=0){
                        for(var i=0;i<arr.length;i++){
                            $("#t_r_b table tr").eq(0).find("td.data-rpc").eq(arr[i]).text(text[i]);
                        }
                    }
                	//添加日能力
                	$("td.data-rnl").text($("input.rnl").val());
                	//添加日能力负荷
                	for(var i=0;i<$("td.data-rnl").length;i++){
                		var rpc=$("#t_r_b table tr").eq(0).find("td.data-rpc").eq(i).text();
                		var rnl=$("input.rnl").val();
                		var rfh;
                		if(rpc!="" && rnl!=""){
                			rpc=Number(rpc);
                			rnl=Number(rnl);
                			rfh=rpc/rnl*100;
                			$("#t_r_b table tr").eq(2).find("td.data-fh").eq(i).text(Math.round(rfh)+"%");
                		}
                	}
                	$(".GROES").hide();//产成品
                    $(".MATNR").hide();//物料
                    $(".KDFS").hide();//营销订单行号
                    $(".DWERK").hide();//工厂
                    $(".ARBPL").hide();//工作中心
                    $(".XARBPL").hide();//需求单位
                    $(".XKTEXT").hide();//需求单位描述
                    $(".sureBtns").click();
                    $("td").css({
                    	"text-wrap":"normal",
                    	"word-break":"break-word"
                    });
                    
                    $(".Mask").hide();
                    
                }
            },
            error: function () {
                alert("error1");
                $(".Mask").hide();
            }
        });        
        var item = 20;
        var b = true;
        $(".t_r_content").unbind("scroll").bind("scroll", function () {
            var fold = $(".t_r_content").height() + $(".t_r_content")[0].scrollTop;
            if (b && fold >= $(".t_r_content")[0].scrollHeight) {
                b = false;
                $(".Mask").show();
                if($("#show").html()=="数据加载完成"){
					$(".Mask").hide();
					return;
				}
                if ($("#show").text() != "数据加载完成") {
                	if (item > arr_data.length) {
                        $("#show").html("数据加载完成");
                        $(".Mask").hide();
                        b = false;
                        return;
                    }
                	$(".Mask").show();
                	var str_r_t = "", str_l_t = "", str_l_b = "", str_r_b = "";
                	$.each(arr_data, function (k, v) {
                        //身体部分
                    	if(item<=k && k<item+20){
                    		str_l_b += "<tr style='position:relative;'>"
		                                + "<td rowspan='2' width='40px' class='AUFNR'>" + ( k + 1 ) + "</td>"//序号
		                                + "<td rowspan='2' width='90px' class='GROES'>" + v.GROES + "</td>"//产成品
		                                + "<td rowspan='2' width='100px' class='LTXA1'>"+v.LTXA1+"</td>"//工序名称
		                                + "<td rowspan='2' width='110px' class='MATNR'>" + v.MATNR + "</td>"//物料
		                                + "<td rowspan='2' width='280px' class='arktx'>" + v.ARKTX + "</td>"//物料描述
		                                + "<td rowspan='2' width='140px' class='KDFS'>" + v.KDFS + "</td>"//营销订单/行号
		                                + "<td rowspan='2' width='40px' class='MENGA' style='text-align:right;'>" +  checkStr(v.MENGA) + "</td>"//台用量
		                                + "<td rowspan='2' width='50px' class='DWERK'>" + v.DWERK + "</td>"//工厂
		                                + "<td rowspan='2' width='80px' class='ARBPL'>" + v.ARBPL + "</td>"//工作中心
		                                + "<td rowspan='2' width='90px' class='KTEXT'>" + v.KTEXT + "</td>"//工作中心描述
		                                + "<td rowspan='2' width='80px' class='XQDW'>" + v.XQDW + "</td>"//下工序
		                                + "<td rowspan='2' width='80px' class='XARBPL'>" + v.XARBPL + "</td>"//需求单位
		                                + "<td rowspan='2' width='100px' class='XKTEXT'>" + v.XKTEXT + "</td>"//需求单位描述
		                                + "<td rowspan='2' width='50px' class='LJWWCSL' style='text-align:right;'>" + v.LJWWCSL + "</td>"//累计未完工数量
		                                + "<td rowspan='2' width='40px' class='PL' style='text-align:right;'>" + CheckUndefined(v.PL) + "</td>"//批量
		                                + "<td rowspan='1' width='40px' class='LJJHSL' style='text-align:right;'>" + CheckUndefined(v.LJJHSL) + "</td>"//当年累计数量
		                                + "<td rowspan='1' width='40px' class='DQJH' style='text-align:right;'>" + CheckUndefined(v.DQJH) + "</td>"//当期订单数量
		                                + "<td rowspan='1' width='40px' class='JH'>计划</td>"//计划/产出
		                                for (var n = 0; n < arrNew.length; n++) {
		                                	str_l_b += "<td class='data-r' width='40px' style='text-align:right;'></td>";
		    	                        }
		                str_l_b+="</tr>"
		                        +"<tr>"
		                                + "<td rowspan='1' width='40px' class='LJCCSL' style='text-align:right;'>" + CheckUndefined(v.LJCCSL) + "</td>"//当年累计数量
		                                + "<td rowspan='1' width='40px' class='DQCC' style='text-align:right;'>" + CheckUndefined(v.DQCC) + "</td>"//当期订单数量
		                                + "<td rowspan='1' width='40px' class='CC'>产出</td>"//计划/产出
		                                for (var n = 0; n < arrNew.length; n++) {
		                                	str_l_b += "<td class='data-r' width='40px' style='text-align:right;'></td>";
		    	                        }
		                str_l_b+= "</tr>";
                    	}
                    });
                	$(".thirtyNineTable .t_r_content table").append(str_l_b);
                	$(".Mask").hide();
                	$.each(arr_data, function (k, v) {
                    	if(item<=k && k<item+20){
                    		//var arrNews=['20150614','20150615','20150616','20150617'];
                            var ind1,ind2,arr1=[],arr2=[],text1=[],text2=[];
                            $.each(v,function(key,val){
                                if(key.indexOf("JH")>0 && key.length==10){
                                    text1.push(parseInt(val))
                                    var keyNew1=key.slice(0,8);
                                    ind1=arrNews.indexOf(keyNew1);
                                    arr1.push(ind1)
                                }
                                if(key.indexOf("CC")>0 && key.length==10){
                                    text2.push(parseInt(val));
                                    var keyNew2=key.slice(0,8);
                                    ind2=arrNews.indexOf(keyNew2);
                                    arr2.push(ind2)
                                }
                            });
                            if(text1.length!=0){
                                for(var i=0;i<arr1.length;i++){
                                	/*if(text1[i]=="1"){*/
                                	var tops=($("#t_r_content table tr").eq(2*Number(k)).find("td.data-r").height()-14)/4+"px";
                                	var str="<div width='78px' style='padding:0px 5px;position:relative;top:0px;height:16px;background-color:#2b7bbf;color:#fff;'>"+text1[i]+"</div>";                                			
                                	$("#t_r_content table tr").eq(2*Number(k)).find("td.data-r").eq(arr1[i]).html(str);
                                	$("#t_r_content table tr").eq(2*Number(k)).find("td.data-r").eq(arr1[i]).css("padding","2px 0px");
                                	//$("#t_r_content table tr").eq(2*Number(k)).find("td.data-r").eq(arr1[i]).html("<div width='78px' style='position:relative;height:10px;background-color:#2b7bbf;'>"+text1[i]+"</div>");
                                	/*}else{
                                		$("#t_r_content table tr").eq(2*Number(k)).find("td.data-r").eq(arr1[i]).text(text1[i]);
                                	}*/
                                }
                            }
                            if(text2.length!=0){
                                for(var i=0;i<arr2.length;i++){
                                    $("#t_r_content table tr").eq(2*Number(k)+1).find("td.data-r").eq(arr2[i]).text(text2[i]);
                                }
                            }
                    	}
                    });
                	item = item + 20;
                	$(".GROES").hide();//产成品
                    $(".MATNR").hide();//物料
                    $(".KDFS").hide();//营销订单行号
                    $(".DWERK").hide();//工厂
                    $(".ARBPL").hide();//工作中心
                    $(".XARBPL").hide();//需求单位
                    $(".XKTEXT").hide();//需求单位描述
                	$(".sureBtns").click();
                    b = true;
                    $(".Mask").hide();
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
			for(var i=0;i<14;i++){
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
                    });
                }
            });
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
            });
            //设置复选框是否被选中的状态
            for(var i=0;i<14;i++){
            	if($(".thirtyNineTable .t_r_title table tr th").eq(i).css("display")=="none"){
            		$(".hideColBox .t_r_title tr").find("input").prop("checked",false);
            		$(".hideColBox .t_r_content tr").eq(i).find("input").prop("checked",false);
            	}
            }
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
                var width39=$("th.data-data").length * 40+1490;
                for(var i=0;i<14;i++){
                	if($(".thirtyNineTable .t_r_title table tr th").eq(i).css("display")=="none"){
                		width39=width39-$(".thirtyNineTable .t_r_title table tr th").eq(i).width();
                	}
                }
                var width39_dis_t = width39 + 20;
                $(".thirtyNineTable table").css("width", width39 + "px");
                $(".thirtyNineTable .t_r_title").css("width", width39_dis_t + "px");
                $("#t_r_b .t_r_foot").css("width",width39_dis_t + "px");
            })
        } else {
            $(this).removeClass("bg");
            var txt = $(this).attr("src");
            $(this).attr("src", txt.replace("hidered.png", "hide.png"));
            $(".hideColBox").hide();
            $("#Mask").hide();
            
        }
    });
});



//39
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
    $("#t_r_t4 span").removeAttr("style");
    $("#t_r_t5 span").removeAttr("style");
}

