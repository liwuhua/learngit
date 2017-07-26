var timess=$(".data-time").val().replace(/-/g,"");
scgl_1(timess);
function scgl_1(timess) {
    var reportJson={
        yearmonth:timess
    }
    $.ajax({
        url: ctx + "/crrc/kpi0609/incomeAndComple",
        data:reportJson,
        dataType:"json",
        type:"post",
        success: function (data){
            if ('300' === data.statusCode){
                alert(data.message);
                $(".zbwcBox .jh span").html("--");
                return true;
            }else if('301' === data.statusCode){
                return true;
            }
            if(jQuery.isEmptyObject(data)){
            	//alert("销售收入指标及完成情况未查询到数据");
            	$("#ndht p").eq(0).find("span").html("");
            	$("#ndht p").eq(1).find("span").html("");
            	$(".zbwcBox .jh span").html("");
            	$(".zbwcBox .zd span").html("");
            	//$("#xssr-zbwc").html('');
            	return true;
            }
            //根据电脑屏幕控制图表字体大小
            var client_w= window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth;
            var zbwc_w;
        	if(client_w>1000&&client_w<1200){
        		zbwc_w=120;
        	}else if(client_w>1200&&client_w<=1900){
        		zbwc_w=150;
        	}else if(client_w>1900){
        		zbwc_w=200;
        	}
            //var ZZC=parseFloat(data.ZZC.split(",").join(""));
            //var TOTALACTUAL=parseFloat(data.TOTALACTUAL.split(",").join(""));
            var ZZC=data.ZZC;
            var TOTALACTUAL=Math.round(data.TOTALACTUAL/10000);
            var xsmb=[ZZC];
            var sjxssr=[TOTALACTUAL];
            var qyzb=data.SIGNKPI;
            var qywc=Math.round(data.TOTALSIGNED/10000);
            if(qyzb==0){
            	$("#ndht p").eq(0).find("span").html("--");
            }else{
            	$("#ndht p").eq(0).find("span").html(formatNumber(qyzb,0,1));
            }
            $("#ndht p").eq(1).find("span").html(formatNumber(qywc,0,1));
            
            var reportJson1={
                //year:Year
            	yearmonth:timess
            	//yearmonth:"201506"
            }
            $(".zbwcBox .jh span").html(formatNumber(ZZC,0,1)+'万元');
            $(".zbwcBox .zd span").html(formatNumber(TOTALACTUAL,0,1)+'万元');
        	$("#xssr-zbwc").css("width",zbwc_w+"px");
        	var rate=TOTALACTUAL/ZZC*zbwc_w;
            $("#xssr").animate({
            	"width":rate+"px"
            });
            if(TOTALACTUAL>=ZZC){
            	$("#scgl-sr .red-flag").show();
            }else{
            	$("#scgl-sr .red-flag").hide();
            }
            var lv=TOTALACTUAL/ZZC*100;
            $("#scgl-sr .srwcl").html("销售收入完成率："+lv.toFixed(2)+"%")
            $('#xssr-zbwc').on({
        	"mouseover":function(){
        		$("#scgl-sr .srwcl").show();
        	},
        	"mouseout":function(){
        		$("#scgl-sr .srwcl").hide();
        	}
            });
        	$(".zbwcBox .zd").off("dblclick").on("dblclick",function(event){
        		$("#Mask").show();
        		$.ajax({
                    url:ctx+"/crrc/kpi0609/saleIncomeFiLayer",
                    dataType: "json",
                    data:reportJson1, 
                    type: "post",
                    success:function(data) {
	                	if ('300' === data.statusCode || '301' === data.statusCode) {
	                        alert(data.message);
	                        $("#Mask").hide();
	                        $(".cggl-4").html('');
	                        return true;
	                    }
	                	if(data.length==0){
	                		alert("未查询到数据");
	                        $("#Mask").hide();  
	                        return true;
	                	}
                        var str = "";
                        str += "<div class='cggl-4'>"
                            + "<div class='name'><span>实际销售收入（单位：万元）</span></div>"
                            + "<p class='can'>×</p>"
                            + "<div class='gys-box2'>"
                            + "<div class='t_r myTable'>"
                            + "<div class='t_r_t' id='t_r_t1'>"
                            + "<div class='t_r_title'>"
                            + "<table><thead>"
                            + "<tr>"
	                            + "<th width='100px'>客户类别</th>"
	                            + "<th class='sort-numeric' width='80px'>销售收入<span class='span1'>&nbsp;↑</span><span class='span2'>&nbsp;↓</span></th>"
	                            + "<th class='sort-numeric' width='80px'>占比（%）<span class='span1'>&nbsp;↑</span><span class='span2'>&nbsp;↓</span></th>"
	                            + "<th class='sort-numeric'>其中：出口<span class='span1'>&nbsp;↑</span><span class='span2'>&nbsp;↓</span></th>"
                            + "</tr>"
                            +"<tr>"
	                            + "<th width='100px' style='text-align:left;'>"+data[0].KDGRPTXTMD+"</th>"
	                            + "<th style='text-align:right;' width='80px'>"+Check0(data[0].totalAccou/10000,0)+"</th>"
	                            +"<th style='text-align:center;' width='80px'>"+Check_l(data[0].rate,2)+"</th>"
	                            + "<th style='text-align:right;'>"+Check0(data[0].totalactual20/10000,0)+"</th>"
                            +"</tr>"
                            + "</thead></table></div></div>"
                            + "<div class='t_r_content' id='t_r_content1'> "
                            + "<table><tbody>"
                        for(var i=1;i<data.length;i++){
                        	str += "<tr>"
		                                + "<td width='100px'>" + data[i].KDGRPTXTMD  + "</td>"
		                                + "<td width='80px' style='text-align:right;'>" + Check0(data[i].totalAccou/10000,0) + "<input type='hidden' name='KDGRP＿name' value='"+data[i].KDGRP+"' class='KDGRP'></td>"
		                                + "<td width='80px' style='text-align:center;'>" + Check_l(data[i].rate,2) + "</td>"
		                                + "<td style='text-align:right;'>" + Check0(data[i].totalactual20/10000,0) + "</td>"
	                                + "</tr>";
                        }
                        str += "</tbody></table></div>"
                            + "</div></div>";
                        $("body").append(str);
                        
                        $(".cggl-4").css({"width":"440px","padding-bottom":"10px"});
                        //确定弹出框的位置
			    		var mousePos = mouseCoords(event);
			    		var p_l=mousePos.x-$(".cggl-4").width();
			    		var p_t=mousePos.y;
			    		$(".cggl-4").css({"left":p_l+"px","top":p_t+"px"});
                        $(".cggl-4 table thead tr:eq(1) th").css("color","#484848");
                        can($(".cggl-4 .can"), $(".cggl-4"), $("#Mask"));
                        //弹出框拖拽
                        var $v1=$(".cggl-4 .name");
                        var v1=$v1[0];
                        var $s1=$(".cggl-4");
                        var s1=$s1[0];
                        drag(v1,s1);
                        //排序
                        tabOrder();
                        //flag=true;
                        //按客户类别下钻
                        $(".t_r_content tr td:first-child").css("cursor","pointer");
                        $(".t_r_content tr td:first-child").off("dblclick").on("dblclick", function (event) {
                            $(".t_r_content tr td").css("background", "#ffffff");
                            $(this).css("background", "#ededed");
                            var KDGRP_val = $(this).parent().find("input").val();
                            var khmc=$(this).text();
                            var reportJson2 = {
                                kdgrp: KDGRP_val,
                                yearmonth:timess,
                                flag:0
                            }
                            $("#Mask2").show();
                            $.ajax({
                                url: ctx + "/crrc/kpi0609/getcustoByKdgrp",
                                data: reportJson2,
                                dataType: "json",
                                type: "post",
                                success: function (data) {
                                	if ('300' === data.statusCode || '301' === data.statusCode) {
	                                          alert(data.message);
	                                          $("#Mask2").hide();
	                                          $(".cggl-7").html('');
	                                          return true;
                                  }
                                    var html='';
                                    html += "<div class='cggl-7'>"
		                                    	if(KDGRP_val=="0007"){
		                                            html+= "<div class='name'><span>出口销售收入（单位：万元）</span></div>"
		                                        }else{
		                                        	html+= "<div class='name'><span>"+khmc+"客户销售收入（单位：万元）</span></div>"
		                                        }
                                                html+= "<p class='can'>×</p>"
                                                + "<div class='gys-box5'>"
                                                	+ "<div class='t_r myTable'>"
                                                		+ "<div class='t_r_t' id='t_r_t1'>"
                                                			+ "<div class='t_r_title'>"
                                                				+ "<table><thead>"
				                                                if(KDGRP_val=="0007"){
				                                                	html+= "<tr>"
							                                                	+ "<th width='200px'>客户名称</th>"
							                                                	+ "<th class='sort-numeric' width='80px'>销售收入<span class='span1'>&nbsp;↑&nbsp;</span><span class='span2'>&nbsp;↓&nbsp;</span></th>"
							                                                	+ "<th class='sort-numeric'>占比（%）<span class='span1'>&nbsp;↑&nbsp;</span><span class='span2'>&nbsp;↓&nbsp;</span></th>"
						                                                	+ "</tr>"
						                                                	+ "<tr>"
						                                                		+ "<th width='260px' class='table-heji'>合计</th>"
						                                                		+ "<th width='80px' class='table-heji-jine'>"+Check0(data[0].actual/10000,0)+"</th>"
						                                                		+ "<th class='table-heji-jine' style='text-align:center;'>"+Check_l(data[0].KDGRPRATE,2)+"</th>"
						                                                	+ "</tr>"
				                                                }else{
				                                                	html+= "<tr>"
							                                                	+ "<th width='200px'>客户名称</th>"
							                                                	+ "<th class='sort-numeric' width='80px'>总收入金额<span class='span1'>&nbsp;↑&nbsp;</span><span class='span2'>&nbsp;↓&nbsp;</span></th>"
							                                                	+ "<th class='sort-numeric' width='80px'>占比（%）<span class='span1'>&nbsp;↑&nbsp;</span><span class='span2'>&nbsp;↓&nbsp;</span></th>"
							                                                	+ "<th class='sort-numeric'>基本出口<span class='span1'>&nbsp;↑&nbsp;</span><span class='span2'>&nbsp;↓&nbsp;</span></th>"
						                                                	+ "</tr>"
						                                                	+"<tr>"
							                                                	+ "<th width='200px'>合计</th>"
							                                                	+ "<th class='table-heji-jine' width='80px'>"+Check0(data[0].actual/10000,0)+"</th>"
							                                                	+ "<th class='table-heji-jine' width='80px' style='text-align:center;'>"+Check_l(data[0].KDGRPRATE,2)+"</th>"
							                                                	+ "<th class='table-heji-jine'>"+ Check0(data[0].totalactual20/10000,0)+"</th>"
						                                                	+ "</tr>"
				                                                }
                                                				html+= "</thead></table>" 
                                                			+"</div></div>"
                                                + "<div class='t_r_content' id='t_r_content1'> "
                                                + "<table><tbody>"
			                                    $.each(data, function (k, v) {
			                                    	if(k>0){
			                                    		if(KDGRP_val!="0007"){
				                                    		html += "<tr>"
							                                    		+ "<td width='200px'>" + v.KUNRGTMD + "</td>"
							                                    		+ "<td width='80px' style='text-align:right;'>" + Check0(v.actual/10000,0) + "</td>"
							                                    		+ "<td width='80px' style='text-align:center;'>" + Check_l(v.KDGRPRATE,2) + "</td>"
							                                    		+ "<td style='text-align:right;'>" + Check0(v.totalactual20/10000,0) + "</td>"
						                                    		+ "</tr>";
				                                    	}else{
				                                    		html += "<tr>"
						                                    			+"<td width='200px'>" + (v.BZIRKTMD!=''?v.BZIRKTMD+'-':v.BZIRKTMD) + v.KUNRGTMD + "</td>"
						                                    			+ "<td width='80px' style='text-align:right;'>" + Check0(v.actual/10000,0) + "</td>"
						                                    			+ "<td style='text-align:center;'>" + Check_l(v.KDGRPRATE,2) + "</td>"
						                                    		+ "</tr>";
				                                    	}
			                                    	}
			                                    });
                                                html += "</tbody></table></div>"
                                        + "</div></div>";
                                    $("body").append(html);
                                    if(KDGRP_val!="0007"){
                                    	$(".cggl-7").css({"width":"530px"});
                                    }else{
                                    	$(".cggl-7").css({"width":"440px"});
                                    }
                                    
                                    //确定弹出框的位置
            			    		var mousePoss = mouseCoords(event);
            			    		var p_ll=mousePoss.x-1/2*$(".cggl-7").width();
            			    		var p_tt=mousePoss.y;
            			    		if(p_tt>430){
            			    			p_tt=mousePoss.y-$(".cggl-7").height()-30;
            			    		}else{
            			    			p_tt=mousePoss.y;
            			    		}
            			    		$(".cggl-7").css({"left":p_ll+"px","top":p_tt+"px"});
                                    $(".cggl-7 .t_r_content").css({"max-height":"280px"})
                                    can($(".cggl-7 .can"), $(".cggl-7"), $("#Mask2"));
                                    //弹出框拖拽
                                    var $v2=$(".cggl-7 .name");
                                    var v2=$v2[0];
                                    var $s2=$(".cggl-7");
                                    var s2=$s2[0];
                                    drag(v2,s2);
                                    //排序
                                    tabOrder();
                                },
                                error: function () {
                                    alert("销售目标和实际销售收入数据错误");
                                    $("#Mask2").hide();
                                }
                            });
                        });
                        //给不为零的其中出口添加小手
                        //console.log($(".t_r_content tr td:last-child"));
                        for(var i=0;i<$(".t_r_content tr td:last-child").length;i++){
                        	if($(".t_r_content tr td:last-child").eq(i).html()!=0){
                        		$(".t_r_content tr td:last-child").eq(i).css("cursor","pointer");
                            }
                        }
                        $(".t_r_content tr td:last-child").off("dblclick").on("dblclick",function(event){
                        	if($(this).html()!=0){
                        		 $(".t_r_content tr td").css("background", "#ffffff");
                                 $(this).css("background", "#ededed");
                                 var KDGRP_val = $(this).parent().find("input").val();
                                 var kehumc=$(this).parent("tr").find("td").eq(0).text()
                                 var reportJson2 = {
                                     kdgrp: KDGRP_val,
                                     yearmonth:timess,
                                     flag:1
                                 }
                                 $("#Mask2").show();
                        		 $.ajax({
                                    url: ctx + "/crrc/kpi0609/getcustoByKdgrp",
                                    data: reportJson2,
                                    dataType: "json",
                                    type: "post",
                                    success: function (data) {
                                    	if ('300' === data.statusCode || '301' === data.statusCode) {
	                                          alert(data.message);
	                                          $("#Mask2").hide();
	                                          $(".cggl-7").html('');
	                                          return true;
	                                    }
                                    	var html='';
                                        html += "<div class='cggl-7'>"
		                                            +"<div class='name'><span>"+kehumc+"客户年度累计出口销售收入（单位：万元）</span></div>"
                                                    + "<p class='can'>×</p>"
                                                    + "<div class='gys-box5'>"
                                                    + "<div class='t_r myTable'>"
                                                    + "<div class='t_r_t' id='t_r_t1'>"
                                                    + "<div class='t_r_title'>"
                                                    + "<table><thead>"
                                                    + "<tr>"
	                                                    + "<th width='180px'>客户名称</th>"
	                                                    + "<th width='130px'>销售地区</th>"
	                                                    + "<th class='sort-numeric'>出口额<span class='span1'>&nbsp;↑&nbsp;</span><span class='span2'>&nbsp;↓&nbsp;</span></th>"
                                                    + "</tr>"
                                                    + "<tr>"
	                                                    + "<th width='180px' class='table-heji'>合计</th>"
	                                                    + "<th width='130px'></th>"
	                                                    + "<th class='table-heji-jine'>"+Check0(data[0].actual/10000,0) +"</th>"
	                                                + "</tr>"
                                                    + "</thead></table></div></div>"
                                                    + "<div class='t_r_content' id='t_r_content1'> "
                                                    + "<table><tbody>"
                                        $.each(data, function (k, v) {
                                        	if(k>0){
                                        		html += "<tr>"
                                            		+ "<td width='180px'>" + v.KUNRGTMD + "</td>"
                                            		+ "<td width='130px'>" + v.BZIRKTMD + "</td>"
                                            		+ "<td style='text-align:right;'>" + Check0(v.actual/10000,0) + "</td>"
                                        		+ "</tr>";
                                        	}
                                            
                                        });
                                        html += "</tbody></table></div>"
                                            + "</div></div>";
                                        $("body").append(html);
                                        $(".cggl-7").css({"width":"470px"});
                                        //确定弹出框的位置
                			    		var mousePoss = mouseCoords(event);
                			    		var p_ll=mousePoss.x-$(".cggl-7").width()+200;
                			    		var p_tt=mousePoss.y;
                			    		if(p_tt>430){
                			    			p_tt=mousePoss.y-$(".cggl-7").height()-30;
                			    		}else{
                			    			p_tt=mousePoss.y;
                			    		}
                			    		$(".cggl-7").css({"left":p_ll+"px","top":p_tt+"px"});
                                        can($(".cggl-7 .can"), $(".cggl-7"), $("#Mask2"));
                                        //弹出框拖拽
                                        var $v3=$(".cggl-7 .name");
                                        var v3=$v3[0];
                                        var $s3=$(".cggl-7");
                                        var s3=$s3[0];
                                        drag(v3,s3);
                                        //排序
                                        tabOrder();
                                    },
                                    error:function(){
                                    	alert("销售收入按出口二级钻取error");
                                    }
                        		});
                        	}
                        });
                    },
                    error : function(){
                        alert("error销售收入一级错误");
                        $("#Mask").hide();
                    }
                });
        	});
            //var flag1=true;
            $("#ndht .wc").off("dblclick").on("dblclick",function(event){
            	$("#Mask").show();
                /*if(!flag1){
                    return;
                }
                flag1=false;*/
                $.ajax({
                    url:ctx+"/crrc/kpi0609/anSignedLayer",
                    dataType: "json",
                    data:reportJson1,
                    type: "post",
                    success:function(data) {
                        if ('300' === data.statusCode || '301' === data.statusCode) {
                            alert(data.message);
                            $("#ndht").html('');
                            $("#Mask").hide();
                            return true;
                        }
                        if(data.length==0){
                        	alert("未查询到数据");
                        	$("#Mask").hide();
                        	return false;
                        }
                        var str = "";
                        str += "<div class='cggl-5'>"
                                + "<div class='name'><span>年度合同累计签订情况（单位：万元）</span></div>"
                                + "<p class='can'>×</p>"
                                    + "<div class='gys-box2'>"
                                        + "<div class='t_r myTable'>"
                                            + "<div class='t_r_t' id='t_r_t1'>"
                                                + "<div class='t_r_title'>"
                                                    + "<table><thead>"
                                                        + "<tr>"
                                                            + "<th width='120px'>销售部门</th>"
                                                            + "<th width='80px' class='sort-numeric'>合同额<span class='span1'>&nbsp;↑&nbsp;</span><span class='span2'>&nbsp;↓&nbsp;</span></th>"
                                                            + "<th class='sort-numeric'>占比（%）<span class='span1'>&nbsp;↑&nbsp;</span><span class='span2'>&nbsp;↓&nbsp;</span></th>"
                                                        + "</tr>"
                                            + "</thead></table></div></div>"
                                    + "<div class='t_r_content' id='t_r_content1'> "
                                        + "<table><tbody>"
                                        $.each(data, function (k, v) {
                                        	var s=v.RATE_TOTALSIGNED;
                                            str += "<tr>"
                                                    + "<td width='120px'>" + v.VKBURTMD+ "</td>"
                                                    +"<td width='80px' style='text-align: right;'>" + Check0(v.TOTALSIGNED/10000,0) + "</td>"
                                                    +"<td style='text-align: center ;'>"+Check_l(s,2)+"</td>"
                                            	 + "</tr>";
                                        });
                            str += "</tbody></table></div>"
                                + "</div></div>";
                        $("body").append(str);
                        $(".cggl-5").css({"width":"360px"});
                        //确定弹出框的位置
			    		var mousePos = mouseCoords(event);
			    		var p_ll=mousePos.x-$(".cggl-5").width();
			    		var p_tt=mousePos.y;
			    		$(".cggl-5").css({"left":p_ll+"px","top":p_tt+"px"});
                        can($(".cggl-5 .can"), $(".cggl-5"), $("#Mask"));
                        //弹出框拖拽
                        var $v3=$(".cggl-5 .name");
                        var v3=$v3[0];
                        var $s3=$(".cggl-5");
                        var s3=$s3[0];
                        drag(v3,s3);
                        //排序
                        tabOrder();
                        //flag1=true;
                    },
                    error:function(){
                        alert("年度合同签约一级下钻数据错误");
                        $("#Mask").hide();
                    }
                });
            });
        },
        error:function(){
            alert("error销售收入指标及完成情况");
        }
    });
}