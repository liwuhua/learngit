//点击查询按钮，弹出查询框
var search1=new multiSearch2($(".btn1"),"reportFS05/tcompcode",null,null);//公司代码
//收起
var heights = $(window).height();
var heights_f=$(".container .col-sm-12 form").height();
$(".table-five .t_r_content").css("height",(heights-heights_f-150)+"px");
$(".retract").off("click").on("click", function () {
    if ($(".container form").css("display") == "block") {
        $(".container form").slideUp();
        $(this).text("展开  ∨");
        var heights = $(window).height();
        $(".table-five .t_r_content").animate({
            "height": (heights-145) + "px"
        });
    } else {
        $(".container form").slideDown();
        $(this).text("收起  ∧");
        var heights = $(window).height();
        $(".table-five .t_r_content").css("height",(heights-heights_f-150)+"px");
    }
});
//采购入库的表格
$("#submit").off("click").on("click",function(){
	var blog=true;
	$(".notSearch").empty();
	$(".t_r_title table").html("");
	$(".t_r_content table").html("");
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
			"startTime":txt1,
			"endTime":txt2//年月
		}
	reportJson = JSON.stringify(reportJson);
	if(!blog){
        return;
    }
	if(txt1==""||txt2==""){
		alert("请输入日期");
	}else if(parseInt(txt1)>parseInt(txt2)){
		alert("后一个日期必须大于或等于前一个日期");
	}else{
		$(".export").on("click", function () {
	        reportJson = {
	            compCodeValue: array1.join(","), //公司代码
	            startTime:txt1,
				endTime:txt2,//年月
	            isExport: true
	        }
	        downloadFile(reportJson, ctx + "/crrc/reportFS05/downloadFile");
	    });
		$(".Mask").show();
		$.ajax({
			url:ctx+"/crrc/reportFS05/result",
			data:reportJson,
			dataType:"json",
			contentType:"application/json;charset=utf-8",
			type:"post",
			success:function(data){
				 if ('300' === data.statusCode || '301' === data.statusCode) {
                    alert(data.message);
                    $(".Mask").hide();
                    $(".ch-kp-five table:eq(1)").html('');
                    $(".ch-kp-five").hide();
                    return true;
                }
				if(data.length==0){
					$(".ch-kp-five").hide();
		       		$(".notSearch").text("未查询到数据");
		       	}else{
		       		$(".datatime1").text(getTime1(txt1,txt2));
		       		$(".bianzhi").html(data.company.company);
		       		$(".ch-kp-five").show();
		       		var str="";
		       		str="<tr><th width='300px'>项目</th>" +
		       				"<th class='xh' width='40px'>行次</th>"
		       		$.each(data.header,function(k,v){
		       			str+="<th width='160px'>"+data.header[k]+"</th>"
		       		});
		       		str+="<th class='hjs' width='150px'>合计</th>"
		       			+"</tr>";
		       		$(".table-five .t_r_title table").html(str);
		       		var html="";
		       		$.each(data.data,function(key,val){
		       			html+="<tr>"
		       					+"<td width='300px'>"+val.ITEM+"</td>"
		       					+"<td class='xh' width='40px'>"+val.ITEM_COLUMN+"</td>";
		       			$.each(data.header,function(s,t){
		       				var ss=val[s];
		       				if(val[s]!=""&&val[s]!="——"&&val[s]!=undefined){
		       					ss=formatNumber(val[s],2,1)
		       				}else if(val[s]==undefined){
		       					ss="";
		       				}
			       			html+="<td style='text-align:right;' width='160px'>"+ss+"</td>"
			       		});
		       			html+="<td class='hjs' style='text-align:right;' width='150px'>"+val.SUM+"</td>"
		       				+"</tr>";
		       		});
		       		$(".table-five .t_r_content table").html(html);
		       		var table_width=($(".table-five table tr th").length-2)*160+340;
		       		var table_width_tr=table_width+20;
		       		$(".table-five table").css("width",table_width+"px");
		       		$(".table-five .t_r_title").css("width",table_width_tr+"px");
		       		
		       		/*for(var i=0;i<$(".table-five table tr th").length-1;i++){
		       			var widths=$(".table-five table tr th:eq("+i+")").width();
		       			for(var j=0;j<$(".t_r_content table tr").length;j++){
		       				$(".t_r_content table tr").eq(j).find("td").eq(i).attr("width",widths+"px");
		       			}
		       		}*/
		       		for(var i=1;i<5;i++){
		       			$(".table-five .t_r_content table tr:eq("+i+") td:eq(0)").css("text-indent","3em")
		       		}
		       		for(var i=6;i<10;i++){
		       			$(".table-five .t_r_content table tr:eq("+i+") td:eq(0)").css("text-indent","3em")
		       		}
		       		for(var i=11;i<13;i++){
		       			$(".table-five .t_r_content table tr:eq("+i+") td:eq(0)").css("text-indent","3em")
		       		}
		       		for(var i=14;i<18;i++){
		       			$(".table-five .t_r_content table tr:eq("+i+") td:eq(0)").css("text-indent","3em")
		       		}
		       		for(var i=19;i<23;i++){
		       			$(".table-five .t_r_content table tr:eq("+i+") td:eq(0)").css("text-indent","3em")
		       		}
		       		for(var i=24;i<28;i++){
		       			$(".table-five .t_r_content table tr:eq("+i+") td:eq(0)").css("text-indent","3em")
		       		}
		       		for(var i=29;i<33;i++){
		       			$(".table-five .t_r_content table tr:eq("+i+") td:eq(0)").css("text-indent","3em")
		       		}
		       		for(var i=34;i<38;i++){
		       			$(".table-five .t_r_content table tr:eq("+i+") td:eq(0)").css("text-indent","3em")
		       		}
		       		for(var i=39;i<43;i++){
		       			$(".table-five .t_r_content table tr:eq("+i+") td:eq(0)").css("text-indent","3em")
		       		}
		       		for(var i=44;i<48;i++){
		       			$(".table-five .t_r_content table tr:eq("+i+") td:eq(0)").css("text-indent","3em")
		       		}
		       		for(var i=49;i<53;i++){
		       			$(".table-five .t_r_content table tr:eq("+i+") td:eq(0)").css("text-indent","3em")
		       		}
		       		for(var i=54;i<58;i++){
		       			$(".table-five .t_r_content table tr:eq("+i+") td:eq(0)").css("text-indent","3em")
		       		}
		       		for(var i=59;i<63;i++){
		       			$(".table-five .t_r_content table tr:eq("+i+") td:eq(0)").css("text-indent","3em")
		       		}
		       		for(var i=64;i<68;i++){
		       			$(".table-five .t_r_content table tr:eq("+i+") td:eq(0)").css("text-indent","3em")
		       		}
		       		for(var i=69;i<73;i++){
		       			$(".table-five .t_r_content table tr:eq("+i+") td:eq(0)").css("text-indent","3em")
		       		}
		       		for(var i=74;i<76;i++){
		       			$(".table-five .t_r_content table tr:eq("+i+") td:eq(0)").css("text-indent","3em")
		       		}
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