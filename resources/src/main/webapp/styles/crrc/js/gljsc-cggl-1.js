var timess=$(".data-time").val().replace(/-/g,"");
cggl_1(timess);
function cggl_1(timess) {
    //采购到货正点率
    $.ajax({
    	url: ctx + "/crrc/kpi1721/resultRate?time="+timess,
        dataType: "json",
        type: "post",
        success: function (data) {
        	if ('300' === data.statusCode) {
                alert(data.message);
                $("#gljsc-cg-dh .name span").html('');
                return true;
            }else if('301' === data.statusCode){
            	 $("#gljsc-cg-dh .name span").html('');
        		return true;
        	}
        	if(data.RATE==undefined){
        		$("#gljsc-cg-dh .name span").html('');
        		$("#gljsc-cg-dh .zdl").html('')
        	}else{
        		$("#gljsc-cg-dh .name span").html((data.RATE*100).toFixed(2)+"%");
        		$("#gljsc-cg-dh .zdl").html("采购到货正点率："+(data.RATE*100).toFixed(2)+"%");
        	}
        	var client_w= window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth;
            var zbwc_w;
        	if(client_w>1000&&client_w<1200){
        		zbwc_w=120;
        	}else if(client_w>1200&&client_w<=1900){
        		zbwc_w=150;
        	}else if(client_w>1900){
        		zbwc_w=200;
        	}
        	$("#gljsc-cg-dh .dh-chart").css("width",zbwc_w+"px");
        	$("#gljsc-cg-dh .jh span").html(formatNumber(data.PLAN_SUM,0,1)+'项');
            $("#gljsc-cg-dh .zd span").html(formatNumber(data.ONTIME_SUM,0,1)+'项');
            var rate=data.ONTIME_SUM/data.PLAN_SUM*zbwc_w;
            $("#cg").animate({
            	"width":rate+"px"
            });
            $('#dh-chart').on({
            	"mouseover":function(){
            		$("#gljsc-cg-dh .zdl").show();
            	},
            	"mouseout":function(){
            		$("#gljsc-cg-dh .zdl").hide();
            	}
            })
            /*$('#dh-chart').highcharts({
                chart: {
                    type: 'bar'
                },
                title: {
                    text: ''
                },
                xAxis: {
                    categories: ['金额'],
                    tickLength:0,//设置x轴的刻度线的长度
                    lineWidth:0,//设置刻度线的宽度
                    labels:{
                        enabled: false
                    },
                    title: {
                        text: null
                    }
                },
                yAxis: {
                    min: 0,
                    title:{
                        text:"",
                        style:{display:"none"}
                    },
                    pointPlacement:-0.3,//设置x轴列的间隔
                    gridLineWidth:0,
                    labels: {
                        enabled: false
                    }
                },
                tooltip: {
                    enabled:false
                },
                plotOptions: {
                    bar: {
                        dataLabels: {
                            enabled: false
                        },
                        grouping:false,
                        pointWidth:14 //柱宽度
                    },
                    series: {
                        cursor: "pointer",
                        events: {
                            click: function (event) {
                                $("#Mask").show();
                                var reportJson1={
                                    year:Year
                                }
                                $.ajax({
                                    url:ctx+"/crrc/kpi0609/saleIncomeFiLayer",
                                    dataType: "json",
                                    data:reportJson1,
                                    type: "post",
                                    success:function(data) {
                                        if ('300' === data.statusCode) {
                                            alert(data.message);
                                            $("#Mask").hide();
                                            $(".cggl-4").html('');
                                            return true;
                                        }
                                        var str = "";
                                        str += "<div class='cggl-4'>"
                                            + "<div class='name'><span>销售收入一级钻取</span></div>"
                                            + "<p class='can'>×</p>"
                                            + "<div class='gys-box2'>"
                                            + "<div class='t_r myTable'>"
                                            + "<div class='t_r_t' id='t_r_t1'>"
                                            + "<div class='t_r_title'>"
                                            + "<table><thead>"
                                            + "<tr>"
                                            + "<th width='130px' style='text-align:left;'>市场板块</th>"
                                            + "<th class='sort-numeric' style='text-align:right;' width='130px'>总收入金额<span class='span1'>&nbsp;↑</span><span class='span2'>&nbsp;↓</span></th>"
                                            + "<th class='sort-numeric' style='text-align:right;'>收入占比<span class='span1'>&nbsp;↑</span><span class='span2'>&nbsp;↓</span></th>"
                                            + "</tr>"
                                            + "</thead></table></div></div>"
                                            + "<div class='t_r_content' id='t_r_content1'> "
                                            + "<table><tbody>"
                                        $.each(data, function (k, v) {
                                            str += "<tr>"
                                                + "<td width='130px'>" + v.KDGRPTMD + "</td>"
                                                + "<td width='130px' style='text-align:right;'>" + v.totalAccou + "</td>"
                                                + "<td style='text-align:right;'>" + v.rate + "</td>"
                                                + "</tr>";
                                        });
                                        str += "</tbody></table></div>"
                                            + "</div></div>";
                                        $("body").append(str);
                                        can($(".cggl-4 .can"), $(".cggl-4"), $("#Mask"));
                                        tabOrder();
                                        $(".t_r_content tr").on("click", function () {
                                            $(".t_r_content tr").css("background", "#ffffff");
                                            $(this).css("background", "#ededed");
                                            var vbtyp_val = $(this).find("td").eq(0).text();
                                            var reportJson2 = {
                                                kdgrp: vbtyp_val,
                                                year:"2016"
                                            }
                                            $("#Mask2").show();
                                            $.ajax({
                                                url: ctx + "/crrc/kpi0609/getcustoByKdgrp",
                                                data: reportJson2,
                                                dataType: "json",
                                                type: "post",
                                                success: function (data) {
                                                    if ('300' === data.statusCode) {
                                                        alert(data.message);
                                                        $("#Mask2").hide();
                                                        $(".cggl-4").html('');
                                                        return true;
                                                    }
                                                    var html='';
                                                    html += "<div class='cggl-7'>"
                                                        + "<div class='name'><span>销售收入二级钻取</span></div>"
                                                        + "<p class='can'>X</p>"
                                                        + "<div class='gys-box5'>"
                                                        + "<div class='t_r myTable'>"
                                                        + "<div class='t_r_t' id='t_r_t1'>"
                                                        + "<div class='t_r_title'>"
                                                        + "<table><thead>"
                                                        + "<tr>"
                                                        + "<th width='230px'>客户</th>"
                                                        + "<th class='sort-numeric' width='130px'>总收入金额<span class='span1'>&nbsp;↑&nbsp;</span><span class='span2'>&nbsp;↓&nbsp;</span></th>"
                                                        + "<th class='sort-numeric'>收入占比<span class='span1'>&nbsp;↑&nbsp;</span><span class='span2'>&nbsp;↓&nbsp;</span></th>"
                                                        + "</tr>"
                                                        + "</thead></table></div></div>"
                                                        + "<div class='t_r_content' id='t_r_content1'> "
                                                        + "<table><tbody>"
                                                    $.each(data, function (k, v) {
                                                        html += "<tr>"
                                                            + "<td width='230px'>" + v.KUNRGTMD + "</td>"
                                                            + "<td width='130px'>" + v.KDGRPACTUAL + "</td>"
                                                            + "<td>" + v.KDGRPRATE + "</td>"
                                                            + "</tr>";
                                                    });
                                                    html += "</tbody></table></div>"
                                                        + "</div></div>";
                                                    $("body").append(html);
                                                    can($(".cggl-7 .can"), $(".cggl-7"), $("#Mask2"));
                                                    tabOrder();
                                                },
                                                error: function () {
                                                    alert("销售目标和实际销售收入数据错误");
                                                    $("#Mask2").hide();
                                                }
                                            })
                                        })
                                    },
                                    error : function(){
                                        alert("error销售收入一级错误");
                                        $("#Mask").hide();
                                    }
                                })
                            }
                        }
                    }
                },
                legend: {
                    enabled:false
                },
                credits: {
                    enabled: false
                },
                series: [{
                    name: '采购计划项数',
                    color:'#cccccc',
                    data: [data.PLAN_SUM],
                    //data:[1122],
                    pointPlacement:-0.2
                }, {
                    name: '采购正点项数',
                    color:'#333333',
                    data: [data.ONTIME_SUM],
                    //data:[600],
                    pointPlacement:-0.2//设置x轴列的间隔
                }]
            });*/
        	//var flag=true;
        	$("#gljsc-cg-dh .name").off("dblclick").on("dblclick",function(event){
        		$("#Mask").show();
        		/*if(!flag){
        			return;
        		}
        		flag=false;*/
        		$.ajax({
        			url:ctx+"/crrc/kpi1721/resultLifnrRateAndEkgrp?time="+timess,
        			dataType: "json",
        	        /*data:reportJson,
        	        contentType: "application/json;charset=utf-8",*/
        	        type: "post",
        			success:function(data){
        				if ('300' === data.statusCode) {
        	                alert(data.message);
        	                $("#Mask").hide();
        	                return true;
        	            }else if('301' === data.statusCode){
        	        		alert(data.message);
        	        		return true;
        	        	}
        				var str="";
        				str+="<div class='cg'>"
        					+"<div class='cg-name' style='cursor: move;background-color:#ededed;padding-left:10px;height:30px;line-height:30px;'>"+timess.slice(0,4)+"年"+timess.slice(4)+"月采购到货正点情况</div>"
        					+"<p class='cancel' style='z-index:6;'>×</p>"
        					+"<ul>"
	    						+"<li class='gysLi'>供应商</li>"
	    						+"<li class='zdlLi'>正点率</li>"
	    					+"</ul>"
        					+"<div class='cgdhzdl-box'>"
        						//供应商部分
        						+"<div class='gys-box'>"
        							+"<div class='t_r myTable'>"
        	    						+"<div class='t_r_t' id='t_r_t1'>" 
        	        						+"<div class='t_r_title'>"	
        										+"<table><thead>"
        											+"<tr>"
        												+"<th width='180px'>供应商名称</th>"
        												+"<th class='sort-numeric'>正点率（%）<span class='span1'>&nbsp;↑</span><span class='span2'>&nbsp;↓</span></th>"
        											+"</tr>"
        								+"</thead></table></div></div>"
        								+"<div class='t_r_content' id='t_r_content1'> "
        									+"<table><tbody>"
        										+"<tr>";
        										$.each(data.DATAEKGRP,function(k,v){
        											str+="<td width='180px'>"+v.TXTMD+"</td>"
        												+"<td style='text-align:center;'>"+Check_l(v.RATE,2)+"</td></tr>";
        										})
        										str+="</tbody></table></div>"
        							+"</div></div>"
        						//正点率部分
        						+"<div class='zdl-box' style='display:none'>"
        							+"<div class='t_r myTable'>"
        	    						+"<div class='t_r_t' id='t_r_t2'>" 
        	        						+"<div class='t_r_title'>"	
        										+"<table><thead>"
        											+"<tr>"
        												+"<th width='180px'>正点区间</th>"
        												+"<th class='sort-numeric'>采购项数<span class='span1'>&nbsp;↑</span><span class='span2'>&nbsp;↓</span></th>"
        											+"</tr>"
        								+"</thead></table></div></div>"
        								+"<div class='t_r_content' id='t_r_content2'> "
        									+"<table><tbody>"
        									+"<tr>"
    											+"<td width='180px'><input type='hidden' value='>=0 and <20'>0~20（含0）</td>"
    											+"<td style='text-align:right;'>"+data.DATALIFNRRATE[1]+"</td></tr>"
                                                +"<td width='180px'><input type='hidden' value='>=20 and <60'>20~60（含20）</td>"
                                                +"<td style='text-align:right;'>"+data.DATALIFNRRATE[2]+"</td></tr>"
                                                +"<td width='180px'><input type='hidden' value='>=60 and <80'>60~80（含60）</td>"
                                                +"<td style='text-align:right;'>"+data.DATALIFNRRATE[3]+"</td></tr>"
                                                +"<td width='180px'><input type='hidden' value='>=80 and <90'>80~90（含80）</td>"
                                                +"<td style='text-align:right;'>"+data.DATALIFNRRATE[4]+"</td></tr>"
                                                +"<td width='180px'><input type='hidden' value='>=90 and <100'>90~100（含90）</td>"
                                                +"<td style='text-align:right;'>"+data.DATALIFNRRATE[5]+"</td></tr>"
                                                +"<td width='180px'><input type='hidden' value='=100'>100</td>"
                                                +"<td style='text-align:right;'>"+data.DATALIFNRRATE[6]+"</td></tr>"
        									+"</tbody></table></div>"
        								+"</div></div>"
        						+"</div>"
        					+"</div>";
        				
        				$("body").append(str);
        				//确定弹出框的位置
			    		var mousePos = mouseCoords(event); 
			    		$(".cg").css({"left":mousePos.x+"px","top":mousePos.y+"px"});	
        				 //弹出框拖拽
                        var $v1=$(".cg .cg-name");
                        var v1=$v1[0];
                        var $s1=$(".cg");
                        var s1=$s1[0];
                        drag(v1,s1);
                        //排序
        				tabOrder();
        				
        				$("ul li").off("click").on("click",function(){
        					var ind=$(this).index();
        					$("ul li").css({
        						"border-bottom":"none",
        						"font-weight":"normal"
        					});
        					$(this).css({
        						"border-bottom":"2px solid #999",
        						"font-weight":"bold"
        					});
        					if(ind==0){
        						$(".zdl-box").css("display","none");
        						$(".gys-box").css("display","block");
        					}else if(ind==1){
        						$(".zdl-box").css("display","block");
        						$(".gys-box").css("display","none");
        					}
        				});
        				can($(".cancel"),$(".cg"),$("#Mask"));
        				//flag=true;
        				$("#t_r_content2 tr").css("cursor","pointer");
        				$("#t_r_content2 tr").off("dblclick").on("dblclick",function(){
                            $("#t_r_content2 tr").css("background","#ffffff");
                            $(this).css("background","#ededed");
                            $("#Mask2").show();
        					var texts=$(this).find("input").val().replace(/ /g, "").replace(/>/g,"").replace(/=/g,"").replace(/</,"");
        					var cggl_zdl={
        							label:texts
        						 }
        					cggl_zdl = JSON.stringify(cggl_zdl);
        					$.ajax({
        	        			url:ctx+"/crrc/kpi1721/resultEkgrpByRate?label="+texts+"&time="+timess,
        	        			dataType: "json",
        	        	        data:cggl_zdl,
        	        	        contentType: "application/json;charset=utf-8",
        	        	        type: "post",
        	        	        success:function(data){
        	        	        	if ('300' === data.statusCode) {
                    	                alert(data.message);
                    	                $("#Mask2").hide();
                    	                return true;
                    	            }else if('301' === data.statusCode){
        	        	        		alert(data.message);
        	        	        		return true;
        	        	        	}
        	        	        	var html="<div class='cggl-2'>"
		        								+"<div class='name'><span>正点区间采购项数明细</span></div>"
		        								+"<p class='can fr' style='padding-right:10px;margin-top:-40px;cursor:pointer;font-size:20px;z-index:33;'>×</p>"
		        								+"<div class='gys-box2'>"
		            								+"<div class='t_r myTable'>"
		            	    							+"<div class='t_r_t' id='t_r_t1'>" 
		            	        						+"<div class='t_r_title'>"	
		            										+"<table><thead>"
				        										+"<tr>"
				        											+"<th width='180px'>采购组</th>"
				        											+"<th class='sort-numeric'>采购项数<span class='span1'>&nbsp;↑</span><span class='span2'>&nbsp;↓</span></th>"
				        										+"</tr>"
	        										+"</thead></table></div></div>"
	                								+"<div class='t_r_content' id='t_r_content1'> "
	                									+"<table><tbody>"
			        									$.each(data.DATA,function(k,v){
			                	        	        		html+="<tr>"
			        		    			        					+"<td width='180px'>"+v.TXTMD+"</td>"
			        		    			        					+"<td style='text-align:right;'>"+formatNumber(v.ONTIME_COUNT,0,1)+"</td>"
			        		    		        					+"</tr>";
			                	        	        	});
		        							  html+="</tbody></table></div>"
		          							+"</div></div>";
		        					$("body").append(html);
		        					var mousePoss = mouseCoords(event);
		        					var c_ll=mousePos.x;
		        					var c_tt=mousePoss.y-$(".cggl-2").height()+80;
		        					$(".cggl-2").css({"left":c_ll+"px","top":c_tt+"px"});
		        					$(".cggl-2").show();
		        					$(".can").off("click").on("click",function(){
		        						$(this).parent(".cggl-2").remove();
		        						$("#Mask2").hide();
		        					})
		        					 //弹出框拖拽
		                            var $v2=$(".cggl-2 .name");
		                            var v2=$v2[0];
		                            var $s2=$(".cggl-2");
		                            var s2=$s2[0];
		                            drag(v2,s2);
		                            //排序
		        					tabOrder();
        	        	        },
        	        	        error:function(){
        	        	        	alert("error");
                                    $("#Mask2").hide();
        	        	        }
        	        	    });
        				});
        			},
        			error:function(){
        				alert("数据请求错误");
        				 $("#Mask").hide();
        			}
        		});
        	});
        },
        error:function(){
        	alert("error");
        }
    });
}
$(".xiazuan").off("dblclick").on("dblclick",function(){
	var time=$(".data-time").val().replace(/-/g,"");
		//$(this).find("img").css("visibility","hidden");
		var xiazuan={
				yearMonth:time
		}
        $("#Mask").show();
		$.ajax({
			url: ctx + "/crrc/kpi20/ledgercheck",
	        dataType: "json",
	        data:xiazuan,
	        type: "post",
	        success:function(data){
	        	if ('300' === data.statusCode) {
	                alert(data.message);
	                $("#Mask").hide();
	                return true;
	            }else if('301' === data.statusCode){
	        		alert(data.message);
	        		return true;
	        	}
	        	var str="<div class='zzdz'>" +
		        			"<div class='zzdz-name'>后勤存货与财务存货总账对账 (元)<span class='fr'>×</span></div>" +
		        			"<div class='zzdz-table'><table border='1'><thead><tr><th>项目</th>";
					        	$.each(data[0],function(k,v){
					        		str+="<th style='text-align:right;'>"+v.YEARMONTH.slice(0,4)+"年"+v.YEARMONTH.slice(4)+"月"+"</th>"
					        	});
					        str+="</tr></thead><tbody>"
				        	for(var i=0;i<data.length;i++){
				        		str+="<tr><td>"+data[i][0].KEMU+"</td>";
					        	$.each(data[i],function(k,v){
					        		str+="<td style='text-align:right;'>"+Check0(v.MONEY,2)+"</td>"
					        	});
					        	str+="</tr>"
				        	}
					        
					        /*str+="</tr><tr><td>"+data[1][0].KEMU+"</td>";
						        $.each(data[1],function(k,v){
					        		str+="<td style='text-align:right;'>"+formatNumber(v.MONEY,2,1)+"</td>"
					        	});
						    str+="</tr><tr><td>"+data[2][0].KEMU+"</td>";
						        $.each(data[2],function(k,v){
					        		str+="<td style='text-align:right;'>"+formatNumber(v.MONEY,2,1)+"</td>"
					        	});
						    str+="</tr><tr><td>"+data[3][0].KEMU+"</td>";
						        $.each(data[3],function(k,v){
					        		str+="<td style='text-align:right;'>"+formatNumber(v.MONEY,2,1)+"</td>"
					        	});*/
                            /*str+="</tr><tr><td>合计</td>";
                            $.each(data[4],function(k,v){
                                str+="<td style='text-align:right;'>"+formatNumber(v.MONEY/10000,0,1)+"</td>"
                            });*/
						    str+="</tbody></table></div><div id='coor' style='position:absolute;bottom:0;right:0;width:12px;height:12px;border-right:1px solid #c70019;border-bottom:1px solid #c70019;cursor: se-resize;'></div></div>";
				$("body").append(str);
				$(".zzdz").css("font-size","12px")
				//弹出框拖拽
	            var $v=$(".zzdz .zzdz-name");
	            var v=$v[0];
	            var $s=$(".zzdz");
	            var s=$s[0];
	            drag(v,s);
	            //弹出框右下角拖拽放大
	            $(function(){
	            	drap_b('#coor','.zzdz','300px','1317px','206px','206px');
	            });
	            $(".zzdz .zzdz-name span").off("click").on("click",function(){
	            	$(this).parents(".zzdz").remove();
	            	$("#Mask").hide();
	            });
	        },
	        error:function(){
	        	alert("在产库存下钻error");
	        }
		});
	}
//});
)
