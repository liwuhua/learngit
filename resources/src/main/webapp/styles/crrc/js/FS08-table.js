//采购入库的表格
$("#submit").off("click").on("click",function(){
	$(".notSearch").empty();
	var txt1=$("#startTime").val().replace(/-/g,"");//单据日期
	var txt2=$("#endTime").val().replace(/-/g,"");
	var reportJson = {
			"startTime":txt1,
			"endTime":txt2//单据日期
	}
	reportJson = JSON.stringify(reportJson);
	if(txt1==""||txt2==""){
		alert("请输入日期");
	}else if(parseInt(txt1)>parseInt(txt2)){
		alert("后一个日期必须大于或等于前一个日期");
	}else{
        $(".Mask").show();
		$.ajax({
			url:ctx+"/crrc/reportFS08/result",
			data:reportJson,
			dataType:"json",
			contentType:"application/json;charset=utf-8",
			type:"post",
			success:function(data){
				if ('300' === data.statusCode || '301' === data.statusCode) {
                    alert(data.message);
                    $(".Mask").hide();
                    $(".ch-kp-eight tbody").html('');
                    $(".notSearch").next("div").hide();
                    return true;
                }
				if(data.length==0){
					$(".notSearch").next("div").hide();
		       		$(".notSearch").text("未查询到数据");
		       	}else{
		       		$(".ch-kp-eight .datatime1").text(getTime(txt1,txt2));
		       		$(".notSearch").next("div").show();
		       		var str="";
					$.each(data,function(key,val){
						str+="<tr>"
							+"<td>"+getValue(val.bezei)+"</td>"
							+"<td>"+getValue(val.zsale)+"</td>"
							+"<td>"+getValue(val.dnwce)+"</td>"
							+"<td>"+getValue(val.dnljwcl)+"</td>"
							+"<td>"+getValue(val.sntqljwce)+"</td>"
							+"<td>"+getValue(val.ndtbzje)+"</td>"
							+"<td>"+getValue(val.dqwce)+"</td>"
							+"<td>"+getValue(val.dqwcl)+"</td>"
							+"<td>"+getValue(val.sntqwce)+"</td>"
							+"<td>"+getValue(val.dqtbzje)+"</td>"
						+"</tr>"
					});
					$(".ch-kp-eight tbody").eq(0).html(str);
		       	}
				$(".Mask").hide();
			},
			error:function(){
				alert("error");
				$(".Mask").hide();
			}
		});
	}
});
