//点击查询按钮，弹出查询框
/*var search1=new multiSearch3($(".btn1"),"reportFS46/trainmodel",null,null);//车型(必填)
var search2=new multiSearch3($(".btn1"),"reportFS46/trainmodel",null,null);//车号(必填)
var search3=new multiSearch3($(".btn1"),"reportFS46/trainmodel",null,null);//造修厂家
var search4=new multiSearch3($(".btn1"),"reportFS46/trainmodel",null,null);//隶属路局
var search5=new multiSearch3($(".btn1"),"reportFS46/trainmodel",null,null);//配属段、所
var search6=new multiSearch3($(".btn1"),"reportFS46/trainmodel",null,null);//车修程类别
var search7=new multiSearch3($(".btn1"),"reportFS46/trainmodel",null,null);//产品编号(必填)
*/
//控制表格高度的显示
var heights = $(window).height();
$(".ch-kp-fortySeven .t_r_content").height((heights - 230) + "px");
//点击收起按钮
$(".retract").off("click").on("click", function () {
    if ($(".container form").css("display") == "block") {
        $(".container form").slideUp();
        $(this).text("展开  ∨");
        var heights = $(window).height();
        $(".ch-kp-fortySeven .t_r_content").animate({
            "height": (heights - 150) + "px"
        });
    } else {
        $(".container form").slideDown();
        $(this).text("收起  ∧");
        var heights = $(window).height();
        $(".ch-kp-fortySeven .t_r_content").height((heights - 230) + ("px"));
    }
})
var arr = [], arrTk = [];
//采购入库的表格
$("#submit").off("click").on("click", function () {
    var blog = true;
    $(".notSearch").empty();
    $("#show").text("数据正在加载中");
    //$(".hideCol").click();
    var array1 = [], array2 = [], array3 = [], array4 = [], array5 = [],array6 = [],array7 = [];
    var string1="";
    if ($(".train_type").val() != "") {
    	string1 = $(".train_type").val();//车型(必填)
        /*if(blog){
            blog = compareInput(array1,plant,"工厂");
        }*/
    }
    if ($(".train_num").val() != "") {
        array2 = $(".train_num").val().split(",");//车号
    }
    if ($(".manufacturer").val() != "") {
        array3 = $(".manufacturer").val().split(",");//造修厂家
    }
    if ($(".bureau_subjection").val() != "") {
        array4 = $(".bureau_subjection").val().split(",");//隶属路局        
    }
    if ($(".place_subjection").val() != "") {
        array5 = $(".place_subjection").val().split(",");//配属段、所
    }
    if ($(".repair_level").val() != "") {
        array6 = $(".repair_level").val().split(",");//车修程类别
    }
    if ($(".productCode").val() != "") {
        array7 = $(".productCode").val().split(",");//产品编号
    }
    if(string1==""){
    	alert("请输入车型!");
    	return;
    }
    var reportJson={};
    $(".Mask").show();
     reportJson = {
    	 train_type: string1, //车型(必填)
    	 train_num: array2, //车号
    	 manufacturer: array3, //造修厂家
    	 bureau_subjection: array4, //隶属路局 
    	 place_subjection: array5, //配属段、所
    	 repair_level: array6, //车修程类别
    	 motor_serial_num: array7, //产品编号
         isExport: false
    }
    reportJson = JSON.stringify(reportJson);
    $(".export").on("click", function () {
        reportJson = {
        	 train_type: string1, //车型(必填)
           	 train_num: array2.join(","), //车号
           	 manufacturer: array3.join(","), //造修厂家
           	 bureau_subjection: array4.join(","), //隶属路局 
           	 place_subjection: array5.join(","), //配属段、所
           	 repair_level: array6.join(","), //车修程类别
           	 motor_serial_num: array7.join(","), //产品编号
             isExport: true
        }
        downloadFile(reportJson, ctx + "/crrc/reportFS47/downloadFile");
    });
    $.ajax({
        url: ctx + "/crrc/reportFS47/result",
        data: reportJson,
        dataType: "json",
        contentType: "application/json;charset=utf-8",
        type: "post",
        success: function (data) {
        	//console.log(data)
            if ('300' === data.statusCode || '301' === data.statusCode) {
                alert(data.message);
                $(".Mask").hide();
                $(".ch-kp-fortySeven table:eq(1)").html('');
                return true;
            }
            if (data.length == 0) {
                $(".notSearch").next("div").hide();
                $(".notSearch").text("未查询到数据");
            } else {
                $(".notSearch").next("div").show();
                if (data.length < 20) {
                    $("#show").html("数据加载完成");
                } else {
                    $("#show").html("");
                }
                //表头部分
                var str = "";
                str+= "<tr>"
	                	+"<th rowspan='2' width='40px' class='xh'>序号</th>"
	                	+"<th rowspan='2' width='80px'>车型</th>"
	                	+"<th rowspan='2' width='100px'>车号</th>"
	                	+"<th rowspan='2' width='100px'>造修厂家</th>"
	                	+"<th rowspan='2' width='100px'>隶属路局</th>"
	                	+"<th rowspan='2' width='100px'>配属段、所</th>"
	                	+"<th rowspan='2' width='100px'>车造/修日期</th>"
	                	+"<th rowspan='2' width='100px'>车修程类别</th>"
	                	+"<th rowspan='2' width='100px'>检修时走行里程</th>"
	                	+"<th rowspan='2' width='100px'>总走行里程</th>"
	                	+"<th rowspan='2' width='100px'>检后走行里程</th>"
		            $.each(data.HEADER,function(k,v){
	            		str+="<th colspan="+v.col+">"+v.name+"</th>"
		            })
	                str+="<th rowspan='2' width='100px'>备注</th>"
	                	+"<th rowspan='2' width='100px'>信息提报人</th>"
	                	+"<th rowspan='2' width='100px'>信息提报日期</th>"
	                +"</tr>";
                 str+="<tr>"
	        		$.each(data.HEADER,function(k,v){
	        			$.each(v.subheader,function(key,val){
		                		str+="<th width='100px'>"+val+"</th>"
	        			})
		            });
                str+="</tr>"
                $(".ch-kp-fortySeven .t_r_t table").html(str);
                var html="";
                $.each(data.DATA,function(k,v){
                	html+="<tr><td>"+(k+1)+"</td>";
                	$.each(v,function(key,val){
                		html+="<td>"+val+"</td>";
                	})
                	html+="</tr>"
                });
                $(".ch-kp-fortySeven .t_r_content table").html(html);
                $(".ch-kp-fortySeven .t_r_content table tr").find("td").eq(0).attr("width","40px");
                $(".ch-kp-fortySeven .t_r_content table tr").find("td").eq(1).attr("width","80px");
                for(var i=2;i<data.DATA[0].length-1;i++){
                	$(".ch-kp-fortySeven .t_r_content table tr").find("td").eq(i).attr("width","100px");
                }
                var table_width=100*(data.DATA[0].length-1)+120;
                $(".ch-kp-fortySeven table").css("width",table_width+"px");
	            //$(".ch-kp-fortySeven table tbody").css("width","100%");
	            $('.ch-kp-fortySeven .t_r_title').css("width",table_width+"px");
                
                
                trBg($(".ch-kp-fortySeven .t_r_content table"))
            }
            $(".Mask").hide();
            
        },
        error: function () {
            alert("数据请求失败!");
            $(".Mask").hide();
        }
    });
});