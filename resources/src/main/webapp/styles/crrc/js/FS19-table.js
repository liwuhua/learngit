//点击查询按钮，弹出查询框
var search1=new multiSearch2($(".btn1"),"reportFS19/tcompcode",null,null);//公司代码
function getJsonlength(Jsondata){
	var jsonLength=0;
	for(var item in Jsondata){
		jsonLength++;
	}
	return jsonLength;
}
//收起展开控制高度
var heights = $(window).height();
var heights_f=$(".container .col-sm-12 form").height();
$(".table-body").css("height",(heights-heights_f-110)+"px");
$(".retract").off("click").on("click", function () {
    if ($(".container form").css("display") == "block") {
        $(".container form").slideUp();
        $(this).text("展开  ∨");
        var heights = $(window).height();
        $(".table-body").animate({
            "height": (heights-100) + "px"
        });
    } else {
        $(".container form").slideDown();
        $(this).text("收起  ∧");
        var heights = $(window).height();
        $(".table-body").css("height",(heights-heights_f-110)+"px");
    }
});
$("#submit").off("click").on("click",function(){
	var blog=true;
	$(".notSearch").empty();
	var array1=[];
	if($(".gongsi").val()!=""){
		array1=$(".gongsi").val().split(",");//公司代码
		if(blog){
            blog = compareInput(array1,comp_code,"公司代码");
        }
	}
	var txt1=$("#startTime").val().replace(/-/g,"");
	if(!blog){
        return;
    }
	if(txt1==""){
		alert("请输入日期");
	}else{
        $(".Mask").show();
		var reportJson = {
			"compCodeValue":array1,//公司代码
			"dateYearMonth":txt1//日期
		}
		reportJson = JSON.stringify(reportJson);
		$(".dataTime1").text($("#startTime").val());
		$(".export").on("click", function () {
	        reportJson = {
	            compCodeValue: array1.join(","), //公司代码
	            dateYearMonth: txt1, //日期
	            isExport: true
	        }
	        downloadFile(reportJson, ctx + "/crrc/reportFS19/downloadFile");
	    });
		$.ajax({
			url:ctx+"/crrc/reportFS19/result",
			data:reportJson,
			dataType:"json",
			contentType:"application/json;charset=utf-8",
			type:"post",
			success:function(data){
				if ('300' === data.statusCode || '301' === data.statusCode) {
	                alert(data.message);
	                $(".Mask").hide();
	                $(".tableBox").html('');
	                return true;
	            }
				var str1="",str2="";
				var arr=[];
				if(data==null){
					$(".notSearch").next("div").hide();
		       		$(".notSearch").text("未查询到数据");
		       	}else{
		       		$(".notSearch").next("div").show();
		       		var times=txt1.slice(0,4)+"年"+txt1.slice(4,6)+"月";
		       		$(".dataTime1").text(times);
		       		$.each(data[1].company,function(k,v){
		       			arr.push(k);
		       		})
		       		str1="<tr>"+
	       				"<th rowspan='2'>栏目</th>";
		       		for(var t=0;t<arr.length;t++){
		       			str1+="<th colspan='2'>"+data[1].company[arr[t]]+"</th>";
		       		}
	   				str1+="<th colspan='2'>合计</th></tr>";
		       		str1+="<tr>";
		       		
		       		for(var t=0;t<arr.length;t++){
		       			str1+="<th>原币</th><th>本位币</th>";
		       		}
		       		str1+="<th>本位币</th>"+"<th>原币</th>"+"</tr>";
		       		
		       		for(var i=2;i<data.length;i++){
		       			str2+="<tr>"+
		       					"<td>"+data[i].lanmu+"</td>";
		       			for(var j=0;j<arr.length;j++){
		       				var a=arr[j];
		       				var yb=data[i][a][0].yuanbi;
		       				var bwb=data[i][a][0].benweibi;
			       			if(yb!=""){
			       				yb=formatNumber(data[i][a][0].yuanbi,2,1);
			       			}
			       			if(bwb!=""){
			       				bwb=formatNumber(data[i][a][0].benweibi,2,1);
			       			}
		       				str2+="<td style='text-align:right;'>"+yb+"</td>"+
	       						  "<td style='text-align:right;'>"+bwb+"</td>";
		       			}
		       			var ybs=data[i].sum[0].yuanbi;
		       			var bwbs=data[i].sum[0].benweibi;
		       			if(ybs!=""){
		       				ybs=formatNumber(data[i].sum[0].yuanbi,2,1);
		       			}
		       			if(bwbs!=""){
		       				bwbs=formatNumber(data[i].sum[0].benweibi,2,1);
		       			}
		       			str2+="<td style='text-align:right;'>"+ybs+"</td>"+
       						  "<td style='text-align:right;'>"+bwbs+"</td>"+
	       				   "</tr>";
		       		}
		       		$(".tableBox").html(str1+str2);
		       		$(".bianzhi").text(data[0].BZDW);
		       	}
				$(".Mask").hide();
			},
			error:function(){
				alert("error1");
				$(".Mask").hide();
			}
		});
	}
});

