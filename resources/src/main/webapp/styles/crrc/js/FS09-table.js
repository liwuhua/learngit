//点击查询按钮，弹出查询框
var search1=new multiSearch2($(".btn1"),"reportFS09/tcompcode",null,null);//公司代码
//收起
var heights = $(window).height();
var heights_f=$(".container .col-sm-12 form").height();
$(".ch-kp-nine .t_r_content").css("height",(heights-heights_f-150)+"px");
$(".retract").off("click").on("click", function () {
    if ($(".container form").css("display") == "block") {
        $(".container form").slideUp();
        $(this).text("展开  ∨");
        var heights = $(window).height();
        $(".ch-kp-nine .t_r_content").animate({
            "height": (heights-140) + "px"
        });
    } else {
        $(".container form").slideDown();
        $(this).text("收起  ∧");
        var heights = $(window).height();
        $(".ch-kp-nine .t_r_content").css("height",(heights-heights_f-150)+"px");
    }
});
//采购入库的表格
$("#submit").off("click").on("click",function(){
	var blog=true;
	$(".notSearch").empty();

	var txt=$(".multiValInterval tbody:eq(0) input:checked").parents("tr").find("td").eq(2).text();

	var array1=[];
	if($(".gongsi").val()!=""){
		array1=$(".gongsi").val().split(",");//公司代码
		if(blog){
            blog = compareInput(array1,comp_code,"公司代码");
        }
	}
	var txt1=$("#startTime").val().replace(/-/g,"");//年月
	var txt2=$("#endTime").val().replace(/-/g,"");
	var reportJson = {
			"compCodeValue":array1,
			"dateYearMonthStart":txt1,
			"dateYearMonthEnd":txt2,//年月
			isExport: false
	}
	reportJson = JSON.stringify(reportJson);
	$(".export").on("click", function () {
        reportJson = {
        	compCodeValue:array1.join(","),
    		dateYearMonthStart:txt1,
    		dateYearMonthEnd:txt2,//年月
            isExport: true
        }
        downloadFile(reportJson, ctx + "/crrc/reportFS09/downloadFile");
    });
	if(!blog){
        return;
    }
	if(txt1==""||txt2==""){
		alert("请输入日期");
	}else if(parseInt(txt1)>parseInt(txt2)){
		alert("后一个日期必须大于或等于前一个日期");
	}else{
        $(".Mask").show();
		$.ajax({
			url:ctx+"/crrc/reportFS09/result",
			data:reportJson,
			dataType:"json",
			contentType:"application/json;charset=utf-8",
			type:"post",
			success:function(data){
				if ('300' === data.statusCode || '301' === data.statusCode) {
                    alert(data.message);
                    $(".Mask").hide();
                    $(".notSearch").next("div").hide();
                    return true;
                }
				if(data.length==0){
					$(".ch-kp-nine").hide();
		       		$(".notSearch").text("未查询到数据");
		       	}else{
		       		$(".ch-kp-nine .datatime1").text(getTime1(txt1,txt2))
		       		$(".ch-kp-nine").show();
		       		$.each(data,function(k,v){
		       			var vs1=v[0],vs2=v[1];
		       			if(v[0]!=null){
		       				vs1=formatNumber(v[0],2,1);
		       			}else{
		       				vs1="";
		       			}
		       			if(v[1]!=null){
		       				vs2=formatNumber(v[1],2,1);
		       			}else{
		       				vs2="";
		       			}
	       				$(".ch-kp-nine .t_r_content table tr:eq("+(k-1)+")").find("td").eq(2).text(vs1);
		       			$(".ch-kp-nine .t_r_content table tr:eq("+(k-1)+")").find("td").eq(3).text(vs2);
	       			
		       		});
                    $(".notSearch+div").show();
		       		$(".bianzhi").text(data.BZDW);
		       	}
				$(".Mask").hide();
				$(".ch-kp-nine .t_r_content").show();
				trBg($(".ch-kp-nine .t_r_content"));
			},
			error:function(){
				alert("error");
				$(".Mask").hide();
			}
		});
	}
});

