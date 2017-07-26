var timess=$(".data-time").val().replace(/-/g,"");
scmb_1(timess);
function scmb_1(timess){
	$(".yxddzdl span").html("");
	var reportJson={
			dateYearMonth:timess
		 }
	$.ajax({
		url: ctx + "/crrc/kpi10/getPunDeliRate",
		data:reportJson,
		dataType:"json",
		type:"post",
        success: function (data) {
        	if(data==null){
        		$(".yxddzdl span").html("");
        		return true;
        	}
        	if (data.statusCode==="300") {
                alert(data.message);
                $(".yxddzdl span").html('');
                return true;
            }else if('301' === data.statusCode){
                return true;
            }
        	$(".yxddzdl span").html(data.pundelirate);
        },
        error:function(){
        	alert("error营销订单正点率");
        }
	});
	//营销订单正点率一级下钻
	//var flag=true;
	$(".yxddzdl").off("dblclick").on("dblclick",function(event){
		/*if(!flag){
			return;
		}
		flag=false;*/
		$("#Mask").show();
		$.ajax({
			url: ctx + "/crrc/kpi10/getFirstLayer",
			data:reportJson,
			dataType:"json",
			type:"post",
	        success: function (data) {
	        	if ('300' === data.statusCode || '301' === data.statusCode) {
	                alert(data.message);
	                $("#Mask").hide();
	                return true;
	            }
	        	var html="<div class='cggl-4'>"
								+"<div class='name'><span>营销订单正点完成情况</span></div>"
                                +"<p class='can'>×</p>"
								+"<div class='gys-box2'>"
									+"<div class='t_r myTable'>"
										+"<div class='t_r_t' id='t_r_t1'>" 
			    						+"<div class='t_r_title'>"	
											+"<table><thead>"
												+"<tr>"
													+"<th width='60px'>订单类别</th>"
													+"<th width='100px' class='sort-numeric'>计划数量（台）<span class='span1'>&nbsp;↑</span><span class='span2'>&nbsp↓</span></th>"
													+"<th width='100px' class='sort-numeric'>正点数量（台）<span class='span1'>&nbsp;↑</span><span class='span2'>&nbsp↓</span></th>"
													+"<th class='sort-numeric'>正点率（%）<span class='span1'>&nbsp;↑</span><span class='span2'>&nbsp↓</span></th>"
												+"</tr>"
												+"<tr>"
													+"<th width='60px' style='text-align:left;color:#484848;'>合计</th>"
													+"<th width='100px' style='text-align:right;color:#484848;'>"+formatNumber(data[0].PLAN_SUM,0,1)+"</th>"
													+"<th width='100px' style='text-align:right;color:#484848;'>"+formatNumber(data[0].ON_TIME_SUM,0,1)+"</th>"
													+"<th style='text-align:center;color:#484848;'>"+Check_l(data[0].ontimerate,2)+"</th>"
											   +"</tr>"
									+"</thead></table></div></div>"
									+"<div class='t_r_content' id='t_r_content1'> "
										+"<table><tbody>"
										$.each(data,function(k,v){
											if(k>0){
												html+="<tr>"
				    			        				+"<td width='60px'>"+v.VBTYP+"<input type='hidden' value='"+ v.VBTYPCODE+"'></td>"
				    			        				+"<td width='100px' style='text-align:right;'>"+formatNumber(v.PLAN_SUM,0,1)+"</td>"
				    			        				+"<td width='100px' style='text-align:right;'>"+formatNumber(v.ON_TIME_SUM,0,1)+"</td>"
				    			        				+"<td style='text-align:center;'>"+Check_l(v.ontimerate,2)+"</td>"
			    			        				+"</tr>";
											}
				        	        	});
							  html+="</tbody></table></div>"
								+"</div></div>";
					$("body").append(html);
					//确定弹窗的位置
					var mousePos = mouseCoords(event); 
					$(".cggl-4").css({
						"width":"460px",
						"top": mousePos.y+"px",
						"left": mousePos.x+"px"
					})
					if($(".cggl-4 .t_r_content tr:eq(0)").find("td:eq(0)").text()=="合计"){
						$(".cggl-4 .t_r_content tr:eq(0)").find("td").css({
							"font-weight":"bolder"
						})
					}
					$(".cggl-4 .can").on("click",function(){
						$(".cggl-4").remove();
						$("#Mask").hide();
					})
                    //弹出框拖拽
                    var $v=$(".cggl-4 .name");
                    var v=$v[0];
                    var $s=$(".cggl-4");
                    var s=$s[0];
                    drag(v,s);
                    //排序
					tabOrder();
					
					//flag=true;
					//营销订单正点率二层下钻
					$(".t_r_content tr").css("cursor","pointer");
					$(".t_r_content tr").off("dblclick").on("dblclick",function(event){
						if($(this).find("td:eq(0)").text()=="合计"){
							return false;
						}
                        $(".t_r_content tr").css("background","#ffffff");
                        $(this).css("background","#ededed");
						var vbtyp_val=$(this).find("td").eq(0).find("input").val();
						var reportJson1={
                                vbtypcode:vbtyp_val,
								dateYearMonth:timess
							 }
						$("#Mask2").show();
						$.ajax({
							url: ctx + "/crrc/kpi10/getSecondLayer",
							data:reportJson1,
							dataType:"json",
							type:"post",
					        success: function (data) {
					        	if ('300' === data.statusCode || '301' === data.statusCode) {
					                alert(data.message);
					                $("#Mask2").hide();
					                return true;
					            }
					        	if(data.length==0){
					        		alert(timess.slice(0,4)+"年"+timess.slice(4)+"月正点完成明细为空");
					        		$("#Mask2").hide();
					        		return true;
					        	}
					        	var html1="<div class='cggl-5'>"
					        				+"<div class='name'><span>"+timess.slice(0,4)+"年"+timess.slice(4)+"月正点完成明细</span></div>"
                                            +"<p class='can'>×</p>"
                                            +"<div class='yxdd-table-2'>"
                                                +"<div class='t_r myTable tableSort'>"
                                                    +"<div class='t_r_t' id='t_r_t2'>"
                                                        +"<div class='t_r_title'>"
                                                            +"<table><thead>"
                                                                +"<tr>"
                                                                    +"<th width='230px' type='string'>产品名称及型号</th>"
                                                                    +"<th width='80px'>销售订单</th>"
                                                                    +"<th width='40px'>行项目</th>"
                                                                    +"<th width='100px' class='sort-numeric'>计划数量（台）<span class='span1'>&nbsp;↑</span><span class='span2'>&nbsp;↓</span></th>"
                                                                    +"<th width='100px' class='sort-numeric'>正点数量（台）<span class='span1'>&nbsp;↑</span><span class='span2'>&nbsp;↓</span></th>"
                                                                    +"<th class='sort-numeric'>正点率（%）<span class='span1'>&nbsp;↑</span><span class='span2'>&nbsp;↓</span></th>"
                                                                +"</tr>"
                                                            +"</thead></table></div></div>"
                                            +"<div class='t_r_content' id='t_r_content2'> "
                                                +"<table><tbody>"
					        					$.each(data,function(k,v){
					        						html1+="<tr>"
									        					+"<td width='230px'><input type='hidden' value='"+v.MATNR+"' />"+v.ARKTX.slice(11)+"</td>"
									        					+"<td width='80px'>"+v.VBELN+"</td>"
									        					+"<td width='40px'>"+v.POSNR+"</td>"
									        					+"<td width='100px' style='text-align:right;'>"+formatNumber(v.PLAN_SUM,0,1)+"</td>"
									        					+"<td width='100px' style='text-align:right;'>"+formatNumber(v.ON_TIME_SUM,0,1)+"</td>"
									        					+"<td style='text-align:center;'>"+Check_l(v.ontimerate,2)+"</td>"
									        				+"</tr>"
					        					});
                                        html1+="</tbody></table></div>"
                                                +"</div></div>";
					        	$("body").append(html1);
					        	//确定弹窗的位置
								var mousePoss = mouseCoords(event); 
								var c_ll = mousePoss.x;
								var c_tt = mousePoss.y-$(".cggl-5").height()+50;
					        	$(".cggl-5").css({
					        		"width":"785px",
					        		"top":c_tt+"px",
									"left": c_ll+"px"
					        	});
					        	$(".cggl-5 .can").on("click",function(){
					        		$(".cggl-5").remove();
					        		$("#Mask2").hide();
					        	})
                                //弹出框拖拽
                                var $v1=$(".cggl-5 .name");
                                var v1=$v1[0];
                                var $s1=$(".cggl-5");
                                var s1=$s1[0];
                                drag(v1,s1);
                                //排序
                                tabOrder();
                                tabletabs(1);
					        	//营销订单正点率三层下钻
					        	/*$(".yxdd-table-2 tbody tr").off("dblclick").on("dblclick",function(){
                                    $(".yxdd-table-2 tbody tr").css("background","#ffffff");
                                    $(this).css("background","#ededed");
					        		var matnr_val=$(this).find("td").eq(0).find("input").val();
									var reportJson2={
											matnr:matnr_val,
                                            vbtypcode:vbtyp_val,
											dateYearMonth:timess
										 }
									$("#Mask3").show();
					        		$.ajax({
										url: ctx + "/crrc/kpi10/getThirdLayer",
										data:reportJson2,
										dataType:"json",
										type:"post",
								        success: function (data) {
                                            if ('300' === data.statusCode || '301' === data.statusCode) {
            					                alert(data.message);
            					                $("#Mask3").hide();
            					                return true;
            					            }
								        	var html2="<div class='cggl-6'>"
                                                        +"<div class='name'><span>营销订单正点率三级钻取</span></div>"
                                                        +"<p class='can'>×</p>"
                                                            +"<div class='yxdd-table-3'>"
                                                                +"<div class='t_r myTable'>"
                                                                    +"<div class='t_r_t' id='t_r_t3'>"
                                                                        +"<div class='t_r_title'>"
                                                                            +"<table><thead>"
                                                                                +"<tr>"
                                                                                    +"<th width='230px' style='text-align:left;'>订单</th>"
                                                                                    +"<th class='sort-numeric' style='text-align:left;'>正点率<span class='span1'>&nbsp;↑</span><span class='span2'>&nbsp;↓</span></th>"
                                                                                +"</tr>"
                                                                            +"</thead></table></div></div>"
                                                        +"<div class='t_r_content' id='t_r_content3'> "
                                                            +"<table><tbody>"
								        					$.each(data,function(k,v){
								        						html2+="<tr>"
												        					+"<td width='230px'>"+v.VBELN+"</td>"
												        					+"<td>"+v.ontimerate+"</td>"
											        					+"</tr>"
								        					});
                                                html2+="</tbody></table></div>"
                                                        +"</div></div>";
								        	$("body").append(html2);
								        	$(".cggl-6 .can").on("click",function(){
								        		$(".cggl-6").remove();
								        		$("#Mask3").hide();
								        	});
                                            //弹出框拖拽
                                            var $v2=$(".cggl-6 .name");
                                            var v2=$v2[0];
                                            var $s2=$(".cggl-6");
                                            var s2=$s2[0];
                                            drag(v2,s2);
                                            //排序
                                            tabOrder();
								        },
								        error:function(){
								        	alert("error营销订单正点率下钻三层");
								        	$("#Mask3").hide();
								        }
								    });
					        	});*/
					        },
					        error:function(){
					        	alert("error营销订单正点率下钻二层");
					        	 $("#Mask2").hide();
					        }
						});
					});
	        },
	        error:function(){
	        	alert("error营销订单正点率下钻一层");
	        	$("#Mask").hide();
	        }
		});
	});
	//产品正点交付率
	//根据电脑屏幕控制图表字体大小
    var client_w= window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth;
    var font,font1,font2,font3;
    var p_s_p,c_mr,cm_b;
    var x2_data=[],x1_data=[];
	if(client_w<1200){
		font="12px";
		font1="10px";
		font2="8px";
		font3="6px";
		p_s_p=12;
		x1_data=['提前', '当期', '延期'];
		x2_data=['前期', '当期'];
		/*x1_data=['提'+'<br/>'+'前', '当'+'<br/>'+'期', '延'+'<br/>'+'期'];
		x2_data=['前'+'<br/>'+'期', '当'+'<br/>'+'期'];*/
		c_mr=15;//设置柱形图间距
		cm_b=25;
	}else if(client_w>1200&&client_w<=1500){
		font="12px";
		font1="10px";
		font2="10px";
		font3="8px";
		p_s_p=20;
		x1_data=['提前', '当期', '延期'];
		x2_data=['前期', '当期'];
		c_mr=15;
		cm_b=25;
	}else if(client_w>1500&&client_w<=1900){
        font="14px";
        font1="12px";
        font2="10px";
        font3="8px";
        p_s_p=24;
        x1_data=['提前', '当期', '延期'];
        x2_data=['前期', '当期'];
        c_mr=15;
        cm_b=25;
    }else if(client_w>1900){
		font="18px";
		font1="14px";
		font2="14px";
		font3="12px";
		p_s_p=30;
		x1_data=['提前', '当期', '延期'];
		x2_data=['前期', '当期'];
		c_mr=50;
		cm_b=30;
	}
	$(".cpzdj span").html(""); 
	$.ajax({
		url: ctx + "/crrc/kpi12/resultRateAndCount?yearMonth="+timess,
		dataType:"json",
		type:"post",
        success: function (data) {
        	if ('300' === data.statusCode) {
                alert(data.message);
                $(".cpzdj span").html("");
                $('#scmb-1-1').html("");
                $('#scmb-1-2').html("");
                return true;
            }else if('301' === data.statusCode){
                return true;
            }
        	var Arr1=[],Arr2=[],Arr11=[],Arr22=[];
        	var tiqian1,dangqi1,yanqi1,zongshu1,qianqi2,dangqi2,zongshu2;
        	var lv=data.DATA.ONTIMERATE[0].on_time_rate+"%";
        	if(data.DATA.ONTIMERATE.length==0){
        		$(".cpzdj span").html("");
        		lv=""
        	}else{
        		lv=data.DATA.ONTIMERATE[0].on_time_rate+"%";
        	}
        	if(data.DATA.COMPCOUNT.length==0){
        		$('#scmb-1-1').html("");
        		$(".cpqk-title .cpwc").html("产品完成");
        		tiqian1=null;
            	dangqi1=null;
            	yanqi1=null;
            	zongshu1="";
        	}else{
        		tiqian1=data.DATA.COMPCOUNT[0].tiqian_complete_sum;
            	dangqi1=data.DATA.COMPCOUNT[0].dangqi_complete_sum;
            	yanqi1=data.DATA.COMPCOUNT[0].yanqi_complete_sum;
            	zongshu1=data.DATA.COMPCOUNT[0].product_complete_sum;
        	}
        	if(data.DATA.UNCOMPCOUNT.length==0){
                $('#scmb-1-2').html("");
                $(".cpqk-title .cpwwc").html("产品未完成");
                qianqi2=null;
            	dangqi2=null;
            	zongshu2="";
        	}else{
        		qianqi2=data.DATA.UNCOMPCOUNT[0].qianqi_uncomplete_sum;
            	dangqi2=data.DATA.UNCOMPCOUNT[0].dangqi_uncomplete_sum;
            	zongshu2=data.DATA.UNCOMPCOUNT[0].product_uncomplete_sum;
        	}
        	if(data.DATA.COMPCOUNT.length==0&&data.DATA.ONTIMERATE.length==0&&data.DATA.UNCOMPCOUNT.length==0){
        		return;
        	}
        	$(".cpzdj span").html(lv);
        	Arr1=[tiqian1,dangqi1,yanqi1];
        	Arr2=[qianqi2,dangqi2];
        	Arr11=[tiqian1,dangqi1,yanqi1];
        	Arr22=[qianqi2,dangqi2];
        	/*Arr11=[1,50,2050]
        	Arr1=[1,50,2050]*/
        	/*Arr1=[102,120,127];
        	Arr2=[77,10];*/
        	var y_max1=Math.max.apply(null,Arr1);
        	y_max1=1.6*y_max1;
        	var y_max2=Math.max.apply(null,Arr2);
        	y_max2=1.6*y_max2;
        	//console.log(y_max1);
        	for(var i=0;i<Arr11.length;i++){
        		if(Arr11[i]!=0&&Arr11[i]/y_max1<=0.05){
            		Arr11[i]=y_max1*0.05;
            	}
        	}
        	for(var i=0;i<Arr22.length;i++){
        		if(Arr22[i]!=0&&Arr22[i]/y_max2<=0.05){
            		Arr22[i]=y_max2*0.05;
            	}
        	}
        	/*if(y_max1<10){
        		y_max1=y_max1+5;
        	}else if(y_max1>=10&&y_max1<100){
        		y_max1=y_max1+50;
        	}else if(y_max1>=100&&y_max1<1000){
        		y_max1=y_max1+500;
        	}else if(y_max1>=1000&&y_max1<10000){
        		y_max1=y_max1+2000;
        	}*/
        	
        	/*if(y_max2<10){
        		y_max2=y_max2+5;
        	}else if(y_max2>=10&&y_max2<100){
        		y_max2=y_max2+0.25*y_max2;
        	}else if(y_max2>=100&&y_max2<1000){
        		y_max2=y_max2+0.25*y_max2;
        	}else if(y_max2>=1000&&y_max2<10000){
        		y_max2=y_max2+0.25*y_max2;
        	}*/
        	//产品完成总数
        	$('#scmb-1-1').highcharts({
                chart: {
                    type: 'column',
                    marginBottom:cm_b
                },
                title: {
                	text: ' '//,
                    //text: '产品完成',
                   /* align:'left',
                    y:subt_y ,*/
                    /*style:{
                        fontSize:font_t,
                     	color:"#484848"
                    }*/
                },
                /*subtitle:{
                	text:''+zongshu1 ,
                	align:'left',
                	x:subt1_x,
                	y:subt_y,
                	style: {
                        color: '#484848',
                        fontSize:font_t,
                    	color:"#484848"
                    }
                },*/
                tooltip:{
                	enabled:false,
                	borderColor:"#d8d8d8",
                	style:{
                    	color: '#595758',
                        fontSize:font1,
                        fontWeight:"normal"
                	}
                },
                xAxis: {
                    tickLength:0,//设置x轴的刻度线的长度
                    lineWidth:0,//设置刻度线的宽度
                    categories: x1_data,
                    labels:{
                    	rotation:0,
                        //y:15,//设置x轴刻度值得位置
                        style:{
                            fontSize:font2,
                        	color:"#484848",
                        	fontWeight:"normal"
                       }
                    }
                },
                yAxis: {
                    min: 0,
                    max:y_max1,
                    title:"",
                    gridLineWidth:0,//去掉y轴的线
                    labels: {
                        enabled: false
                    }//去掉y轴的刻度值
                },
                plotOptions: {
                	column: {
                        dataLabels: {
                        	//allowOverlap:true,
                        	//verticalAlign:"top",
                        	y:5,
                            enabled: true,
                            style:{
                            	fontSize:font2,
                            	color:"#484848"/*,
                            	fontWeight:"normal"*/
                            },
                            formatter:function(){
                            	return Arr1[this.point.index];
                            }
                        }
                    },
                    series: {
                    	pointWidth: p_s_p,
                        colorByPoint : true,
                        colors : [ "#c70019","#535666","#ccc"]
                    }//设置每个柱状图的颜色
                },
                credits: {
                    enabled: false
                },
                legend:{
                    enabled: false
                },//去掉图例
                series: [{
                    name: '数量',
                    data: Arr11
                }]
            });
        	//产品完成总数下钻
        	$(".cpqk-title .cpwc").html("产品完成 "+zongshu1);
        	$(".cpqk-title .cpwc").off("dblclick").on("dblclick",function(ev){
        		$("#Mask").show();
        		$.ajax({
        			url: ctx + "/crrc/kpi12/resultProCompSumGoDown?yearMonth="+timess,
        			dataType:"json",
        			type:"post",
        	        success: function (data) {
        	        	if ('300' === data.statusCode || '301' === data.statusCode) {
        	                alert(data.message);
        	                $("#Mask").hide();
        	                return true;
        	            }
        	        	var html="<div id='cpjfzd-1'>"
        								+"<div class='tab-title' style='cursor:move;'>"
        									+"<div class='fl cpjfzd-head'></div>"
        								+"</div>"
    									+"<p class='fr cancel' style='cursor:pointer;margin-top:-30px;padding-right:10px;'>×</p>"
        								+"<ul id='cpjfzd-tab'>"
        								+"<li>全部</li>"
        								+"<li style='height:17px;'>"
        									+"<select name='' id='selects'>"
        									
        									+"</select>"
        									+"<div style='position:relative;width:150px;height:17px;top:-17px;'></div>"
        								+"</li>"
        							+"</ul>"
        							+"<div id='all-c' class='cpjfzd-div'></div>"
        							+"<div id='comp' class='cpjfzd-div'></div>"
        						+"</div>";
        				$("body").append(html);
        				//确定弹出框的位置
			    		var mousePos = mouseCoords(ev); 
			    		var c_l=mousePos.x;
			    		var c_t=mousePos.y-2*$("#cpjfzd-1").height();
        				$("#cpjfzd-1").css({"width":"340px","left":c_l+"px","top":c_t+"px"});
        				ul_tab();
        				//弹出框拖拽
                        var $v4=$("#cpjfzd-1 .tab-title");
                        var v4=$v4[0];
                        var $s4=$("#cpjfzd-1");
                        var s4=$s4[0];
                        drag(v4,s4);
        	        	$("#cpjfzd-1 .cpjfzd-head").html("产品完成情况");
        	        	//获取切换中的公司名称
        	        	var html_select="";
        	        	$.each(data.DATA.COMPANY,function(k,v){
        	        		html_select+="<option value='"+v.bukrs+"'>"+v.txtmd+"</option>";
        	        	});
        	        	$("#cpjfzd-1 #cpjfzd-tab #selects").html(html_select);
        	        	//获取全部的
        	        	var th_s=data.DATA.COMPSUMGODOWN[data.DATA.COMPSUMGODOWN.length-1].complete_sum;
                		var html_all="<div class='t_r myTable tableSort'>"
            							+"<div class='t_r_t' id='t_r_t1'>" 
                							+"<div class='t_r_title'>"	
        										+"<table><thead>"
        											+"<tr>"
        												+"<th class='sort-asc' width='200px' type='string'>产品名称及型号</th>"
        												+"<th class='sort-numeric'>完成总数（台）<span class='span1'>&nbsp;↑</span><span class='span2'>&nbsp;↓</span></th>"
        											+"</tr>"
        											+"<tr>"
	    												+"<th width='200px' style='text-align:left;color:#000000;'>合计</th>"
	    												+"<th style='text-align:right;color:#000000;'>"+formatNumber(th_s,0,1)+"</th>"
	    											+"</tr>"
        										+"</thead></table></div></div>"
        							+"<div class='t_r_content' id='t_r_content1'> "
        								+"<table><tbody>"
        								$.each(data.DATA.COMPSUMGODOWN,function(k,v){
        									if(k<data.DATA.COMPSUMGODOWN.length-1){
        										html_all+="<tr>"
    	    			        					+"<td width='200px'>"+v.arktx.slice(11)+"</td>"
    	    			        					+"<td style='text-align:right;'>"+formatNumber(v.complete_sum,0,1)+"</td>"
    	    		        					+"</tr>";
        									}
            	        	        	});
        					html_all+="</tbody></table></div>";
        				$("#all-c").html(html_all);
        				tabletabs(1);
        				//当前第一个公司
        				var th_s1=data.DATA.COMPSUMBYBUKRSGODOWN[data.DATA.COMPSUMBYBUKRSGODOWN.length-1].complete_sum;
        				var html_first="<div class='t_r myTable tableSort1'>"
        									+"<div class='t_r_t' id='t_r_t1'>" 
        									+"<div class='t_r_title'>"	
        										+"<table><thead>"
        											+"<tr>"
        												+"<th width='200px' type='string'>产品名称及型号</th>"
        												+"<th class='sort-numeric'>完成总数（台）<span class='span1'>&nbsp;↑</span><span class='span2'>&nbsp;↓</span></th>"
        											+"</tr>"
        											+"<tr>"
	    												+"<th width='200px' style='text-align:left;color:#000;'>合计</th>"
	    												+"<th style='text-align:right;color:#000;'>"+formatNumber(th_s1,0,1)+"</th>"
	    											+"</tr>"
        										+"</thead></table></div></div>"
        							+"<div class='t_r_content' id='t_r_content1'> "
        								+"<table><tbody>"
        								$.each(data.DATA.COMPSUMBYBUKRSGODOWN,function(k,v){
        									if(k<data.DATA.COMPSUMBYBUKRSGODOWN.length-1){
        										html_first+="<tr>"
													+"<td width='200px'>"+v.arktx.slice(11)+"</td>"
													+"<td style='text-align:right;'>"+formatNumber(v.complete_sum,0,1)+"</td>"				        				
												+"</tr>";
        									}
        					        	});
        					html_first+="</tbody></table></div>";
        				$("#cpjfzd-1 #comp").html(html_first);
        				tabOrder();
        				tabletabs1(1);
        				//选择公司获取内容
        				$("#selects").on("change",function(){
        					var comp=$("#selects option:selected").val();
        					$.ajax({
        						url: ctx + "/crrc/kpi12/resultProCompSumByBukrsGoDown?yearMonth="+timess+"&bukrs="+comp,
        						dataType:"json",
        						type:"post",
        				        success: function (data) {
        				        	if ('300' === data.statusCode || '301' === data.statusCode) {
        				                alert(data.message);
        				                return true;
        				            }
        				        	var th_s2;
        				        	if(data.COMPSUMBYBUKRSGODOWN.length==0){
        				        		th_s2="";
        				        	}else{
        				        		th_s2=data.COMPSUMBYBUKRSGODOWN[data.COMPSUMBYBUKRSGODOWN.length-1].complete_sum;
        				        	}
        				        	 
        				        	var html_comp="<div class='t_r myTable tableSort2'>"
        												+"<div class='t_r_t' id='t_r_t1'>" 
        												+"<div class='t_r_title'>"	
        													+"<table><thead>"
        														+"<tr>"
        															+"<th width='200px' type='string'>产品名称及型号</th>"
        															+"<th class='sort-numeric'>完成总数（台）<span class='span1'>&nbsp;↑</span><span class='span2'>&nbsp;↓</span></th>"
        														+"</tr>"
        														+"<tr>"
	        	    												+"<th width='200px' style='text-align:left;color:#000;'>合计</th>"
	        	    												+"<th style='text-align:right;color:#000;'>"+formatNumber(th_s2,0,1)+"</th>"
	        	    											+"</tr>"
        													+"</thead></table></div></div>"
        										+"<div class='t_r_content' id='t_r_content1'> "
        											+"<table><tbody>"
        											$.each(data.COMPSUMBYBUKRSGODOWN,function(k,v){
        												if(k<data.COMPSUMBYBUKRSGODOWN.length-1){
        													html_comp+="<tr>"
	        													+"<td width='200px'>"+v.arktx.slice(11)+"</td>"
	        													+"<td style='text-align:right;'>"+formatNumber(v.complete_sum,0,1)+"</td>"
								        					+"</tr>";
        												}
        								        	});
        				        		html_comp+="</tbody></table></div>";
        							$("#cpjfzd-1 #comp").html(html_comp);
        	                        //排序
        							tabOrder();
        							tabletabs2(1);
        							$("#cpjfzd-1 table").css("width","100%");
        							$("#cpjfzd-1 table th").css({"border":"1px solid #ddd"});
        							$("#cpjfzd-1 table td").css({"border":"1px solid #ddd"});
        				        },
        				        error:function(){
        				        	alert("error产品完成总数按照公司钻取");
        				        }
        					});
        				});
        				cancel_this();
        				$("#cpjfzd-1 table").css("width","100%");
        				$("#cpjfzd-1 table th").css({"border":"1px solid #ddd"});
        				$("#cpjfzd-1 table td").css({"border":"1px solid #ddd"});
        	        },
        	        error:function(){
        	        	alert("error产品完成总数一级下钻");
                        $("#Mask").hide();
        	        }
        		});
        	});
        	//产品累计未完成
        	$('#scmb-1-2').highcharts({
                chart: {
                    type: 'column',
                    //marginRight:c_mr,
                    //marginBottom:cm_b,
                    //marginLeft:c2_ml,
                    //marginTop:10
                    marginBottom:cm_b
                },
                title: {
                	text: ' '//,
                	//text: '产品累计未完成',
                	//align:'left',
                	/*y:subt_y,
                	x:-8,*/
                    /*style:{
                         fontSize:font_t,
                     	color:"#484848"
                    }*/
                },
                /*subtitle:{
                	text:''+zongshu2,
                	align:'left',
                	x:subt2_x,
                	//y:subt2_y,
                	y:subt_y,
                	style: {
                        color: '#484848',
                        fontSize:font_t
                    }
                },*/
                tooltip:{
                	enabled:false,
                	borderColor:"#d8d8d8",
                	style:{
                    	color: '#595758',
                        fontSize:font1,
                        fontWeight:"normal"
                	}
                },
                xAxis: {
                    tickLength:0,//设置x轴的刻度线的长度
                    lineWidth:0,//设置刻度线的宽度
                    categories: x2_data,
                    style:{
                         fontSize:font2,
                     	color:"#484848"
                    },
                    labels:{
                    	//align:"left",
                        //y:15,//设置x轴刻度值得位置
                    	rotation:0,
                    	style:{
                    		//textAlign:"left",
                    		fontSize:font2,
                        	color:"#484848",
                        	fontWeight:"normal"
                    		
                    	}
                    }
                },
                yAxis: {
                    min: 0,
                    max:y_max2,
                    gridLineWidth:0,//去掉y轴的线
                    labels: {
                        enabled: false
                    },//去掉y轴的刻度值
                    title:{
                    	text: " ",
                    	verticalAlign:"bottom",
                    	rotation:0,
                    	//x:y_t_x,
                    	//y:y_t_y,
                    	style:{
                    		fontSize:font2,
                        	color:"#484848"
                    	}
                    }
                },
                plotOptions: {
                    column: {
                        dataLabels: {
                        	//allowOverlap:true,
                        	//inside:false,
                        	//verticalAlign:"top",
                        	y:5,
                            enabled: true,
                            style:{
                            	color:"#484848",
                            	fontSize:font2/*,
                            	fontWeight:"normal"*/
                            },
                            formatter:function(){
                            	return Arr2[this.point.index];
                            }
                        }
                    },
                    series: {
                    	pointWidth: p_s_p,
                    	/*pointPadding:0.02,*/
                        colorByPoint : true,
                        colors : [ "#c70019","#535666"]
                    }//设置每个柱状图的颜色
                },
                credits: {
                    enabled: false
                },
                legend:{
                    enabled: false
                },//去掉图例
                series: [{
                    name: '数量',
                    data: Arr22
                }]
            });
        	/*for(var i=0;i<$(".highcharts-data-labels div").length;i++){
        		var mt=$(".highcharts-data-labels div:eq("+i+")").css("margin-top");
        		mt=mt.substring(0,mt.length-2);
        		mt=Number(mt);
        		if(mt<=16){
        			$(".highcharts-data-labels div:eq("+i+")").css("margin-top","-10px");
            	}
        	}*/
        	//产品未完成总数一级下钻
        	$(".cpqk-title .cpwwc").html("累计未完成 "+zongshu2);
        	$(".cpqk-title .cpwwc").off("dblclick").on("dblclick",function(ev){
        		$("#Mask").show();
        		$.ajax({
        			url: ctx + "/crrc/kpi12/resultProUnCompSumGoDown?yearMonth="+timess,
        			dataType:"json",
        			type:"post",
        	        success: function (data) {
        	        	if ('300' === data.statusCode || '301' === data.statusCode) {
        	                alert(data.message);
        	                $("#Mask").hide();
        	                return true;
        	            }
        	        	var html="<div id='cpjfzd-1'>"
        								+"<div class='tab-title' style='cursor:move;'>"
        									+"<div class='fl cpjfzd-head'></div>"
        								+"</div>"
        								+"<p class='fr cancel' style='cursor:pointer;margin-top:-30px;padding-right:10px;'>×</p>"
        								+"<ul id='cpjfzd-tab'>"
        								+"<li>全部</li>"
        								+"<li style='height:17px;'>"
        									+"<select name='' id='selects'>"
        									
        									+"</select>"
        									//+"<div style='position:relative;width:150px;height:17px;top:-17px;'></div>"
        								+"</li>"
        							+"</ul>"
        							+"<div id='all-c' class='cpjfzd-div'></div>"
        							+"<div id='comp' class='cpjfzd-div'></div>"
        						+"</div>";
        				$("body").append(html);
        				//确定弹出框的位置
			    		var mousePos = mouseCoords(ev); 
			    		var c_l=mousePos.x;
			    		var c_t=mousePos.y-2*$("#cpjfzd-1").height();
        				$("#cpjfzd-1").css({"width":"340px","left":c_l+"px","top":c_t+"px"});
        				//弹出框拖拽
                        var $v5=$("#cpjfzd-1 .tab-title");
                        var v5=$v5[0];
                        var $s5=$("#cpjfzd-1");
                        var s5=$s5[0];
                        drag(v5,s5);
        				ul_tab();
        				$("#cpjfzd-1 .cpjfzd-head").html("截至"+timess.slice(0,4)+"年"+timess.slice(4)+"月产品未完成情况");
        	        	/*//弹出框拖拽
                        var $v4=$("#cpjfzd-1 .tab-title");
                        var v4=$v4[0];
                        var $s4=$("#cpjfzd-1");
                        var s4=$s4[0];
                        drag(v4,s4);*/
        	        	//获取切换中的公司名称
        	        	var html_select="";
        	        	$.each(data.DATA.COMPANY,function(k,v){
        	        		html_select+="<option value='"+v.bukrs+"'>"+v.txtmd+"</option>";
        	        	});
        	        	$("#cpjfzd-1 #cpjfzd-tab #selects").html(html_select);
        	        	//获取全部的
        	        	var heji_z;
        	        	if(data.DATA.UNCOMPSUMGODOWN.length==0){
        	        		heji_z=""
        	        	}else{
        	        		heji_z=data.DATA.UNCOMPSUMGODOWN[0].uncomplete_sum
        	        	}
                		var html_all="<div class='t_r myTable tableSort'>"
            							+"<div class='t_r_t' id='t_r_t1'>" 
                							+"<div class='t_r_title'>"	
        										+"<table><thead>"
        											+"<tr>"
        												+"<th width='200px'>产品名称及型号</th>"
        												+"<th class='sort-numeric'>未完成总数（台）<span class='span1'>&nbsp;↑</span><span class='span2'>&nbsp;↓</span></th>"
        											+"</tr>"
        											+"<tr>"
	    												+"<th width='200px' class='table-heji'>合计</th>"
	    												+"<th class='table-heji-jine'>"+Check0(heji_z,0)+"</th>"
	    											+"</tr>"
        										+"</thead></table></div></div>"
        							+"<div class='t_r_content' id='t_r_content1'> "
        								+"<table><tbody>"
        								$.each(data.DATA.UNCOMPSUMGODOWN,function(k,v){
        									if(k>0){
        										html_all+="<tr>"
		    			        					+"<td width='200px'>"+v.arktx.slice(11)+"</td>"
													+"<td style='text-align:right;'>"+formatNumber(v.uncomplete_sum,0,1)+"</td>"
		    		        					+"</tr>";
        									}        									
            	        	        	});
        					html_all+="</tbody></table></div>";
        				$("#all-c").html(html_all);
        				
        				tabletabs(1);
        				//当前第一个公司
        				var heji_1;
        	        	if(data.DATA.UNCOMPSUMBYBUKRSGODOWN.length==0){
        	        		heji_1=""
        	        	}else{
        	        		heji_1=data.DATA.UNCOMPSUMBYBUKRSGODOWN[0].uncomplete_sum
        	        	}
        				var html_first="<div class='t_r myTable tableSort1'>"
        									+"<div class='t_r_t' id='t_r_t1'>" 
        									+"<div class='t_r_title'>"	
        										+"<table><thead>"
        											+"<tr>"
        												+"<th width='200px'>产品名称及型号</th>"
        												+"<th class='sort-numeric'>未完成总数（台）<span class='span1'>&nbsp;↑</span><span class='span2'>&nbsp;↓</span></th>"
        											+"</tr>"
        											+"<tr>"
	    												+"<th width='200px' class='table-heji'>合计</th>"
	    												+"<th  class='table-heji-jine'>"+Check0(heji_1,0)+"</th>"
	    											+"</tr>"
        										+"</thead></table></div></div>"
        							+"<div class='t_r_content' id='t_r_content1'> "
        								+"<table><tbody>"
        								$.each(data.DATA.UNCOMPSUMBYBUKRSGODOWN,function(k,v){
        									if(k>0){
        										html_first+="<tr>"
													+"<td width='200px'>"+v.arktx.slice(11)+"</td>"
													+"<td style='text-align:right;'>"+formatNumber(v.uncomplete_sum,0,1)+"</td>"					        				
					        					+"</tr>";
        									}        									
        					        	});
        					html_first+="</tbody></table></div>";
        				$("#cpjfzd-1 #comp").html(html_first);
        				tabOrder();
        				tabletabs1(1);
        				//选择公司获取内容
        				$("#selects").on("change",function(){
        					var comp=$("#selects option:selected").val();
        					$.ajax({
        						url: ctx + "/crrc/kpi12/resultProUnCompSumByBukrsGoDown?yearMonth="+timess+"&bukrs="+comp,
        						dataType:"json",
        						type:"post",
        				        success: function (data) {
        				        	if ('300' === data.statusCode || '301' === data.statusCode) {
        				                alert(data.message);
        				                return true;
        				            }
        				        	//当前第一个公司
        	        				var heji_2;
        	        	        	if(data.UNCOMPSUMBYBUKRSGODOWN.length==0){
        	        	        		heji_2=""
        	        	        	}else{
        	        	        		heji_2=data.UNCOMPSUMBYBUKRSGODOWN[0].uncomplete_sum
        	        	        	}
        				        	var html_comp="<div class='t_r myTable tableSort2'>"
        												+"<div class='t_r_t' id='t_r_t1'>" 
        												+"<div class='t_r_title'>"	
        													+"<table><thead>"
        														+"<tr>"
        															+"<th width='200px'>产品名称及型号</th>"
        															+"<th class='sort-numeric'>未完成总数（台）<span class='span1'>&nbsp;↑</span><span class='span2'>&nbsp;↓</span></th>"
        														+"</tr>"
        														+"<tr>"
	    															+"<th width='200px' class='table-heji'>合计</th>"
	    															+"<th class='table-heji-jine'>"+Check0(heji_2,0)+"</th>"
	    														+"</tr>"
        													+"</thead></table></div></div>"
        										+"<div class='t_r_content' id='t_r_content1'> "
        											+"<table><tbody>"
        											$.each(data.UNCOMPSUMBYBUKRSGODOWN,function(k,v){
        												if(k>0){
        													html_comp+="<tr>"
																+"<td width='200px'>"+v.arktx.slice(11)+"</td>"
																+"<td style='text-align:right;'>"+formatNumber(v.uncomplete_sum,0,1)+"</td>"	
								        					+"</tr>";
        												}        												
        								        	});
        				        		html_comp+="</tbody></table></div>";
        							$("#cpjfzd-1 #comp").html(html_comp);
        							tabOrder();
        							tabletabs2(1);
        							$("#cpjfzd-1 table").css("width","100%");
        							$("#cpjfzd-1 table th").css({"border":"1px solid #ddd"});
        							$("#cpjfzd-1 table td").css({"border":"1px solid #ddd"});
        				        },
        				        error:function(){
        				        	alert("error产品未完成总数按照公司钻取");
        				        }
        					});
        				});
        				cancel_this();
        				$("#cpjfzd-1 table").css("width","100%");
        				$("#cpjfzd-1 table th").css({"border":"1px solid #ddd"});
        				$("#cpjfzd-1 table td").css({"border":"1px solid #ddd"});
        	        },
        	        error:function(){
        	        	alert("error产品未完成总数一级下钻");
        	            $("#Mask").hide();
        	        }
        		});
        	});
        },
        error:function(){
        	alert("error营销订单正点率");
        }
	});
	//tab切换
	function ul_tab(){
		$("#cpjfzd-1 ul li").on("click",function(){
			$("#cpjfzd-1 ul li").css({
				"border-bottom":"none",
				"font-weight":"normal"
			});
			var ind=$(this).index();
			$(this).css({
				"border-bottom":"2px solid #999",
				"font-weight":"bold"
			});
			$("#cpjfzd-1 .cpjfzd-div").hide();
			$("#cpjfzd-1 .cpjfzd-div:eq("+ind+")").show();
		});
	}
	//产品正点交付率下钻
	$(".cpzdj").off("dblclick").on("dblclick",function(ev){
    	$("#Mask").show();
		$.ajax({
			url: ctx + "/crrc/kpi12/resultProOTDeliverGoDown?yearMonth="+timess,
			dataType:"json",
			type:"post",
	        success: function (data) {
	        	if ('300' === data.statusCode || '301' === data.statusCode) {
	                alert(data.message);
	                $("#Mask").hide();
	                return true;
	            }
	        	var html="<div id='cpjfzd-1'>"
	   							+"<div class='tab-title' style='cursor:move;'>"
	   								+"<div class='fl cpjfzd-head'></div>"
	   							+"</div>"
	   							+"<p class='fr cancel' style='cursor:pointer;margin-top:-30px;padding-right:10px;'>×</p>"
   								+"<ul id='cpjfzd-tab'>"
									+"<li>全部</li>"
									+"<li style='height:17px;'>"
										+"<select name='' id='selects'>"
										
										+"</select>"
										//+"<div style='position:relative;width:150px;height:17px;top:-17px;'></div>"
									+"</li>"
								+"</ul>"
								+"<div id='all-c' class='cpjfzd-div'></div>"
								+"<div id='comp' class='cpjfzd-div'></div>"
							+"</div>";
	        	$("body").append(html);
	        	//确定弹出框的位置
	    		var mousePos = mouseCoords(ev); 
	    		var c_l=mousePos.x;
	    		var c_t=mousePos.y-2*$("#cpjfzd-1").height();
				$("#cpjfzd-1").css({"width":"600px","left":c_l+"px","top":c_t+"px"});
	        	ul_tab();
	        	$("#cpjfzd-1 .cpjfzd-head").html("产品正点情况");
	        	//获取切换中的公司名称
	        	var html_select="";
	        	$.each(data.DATA.COMPANY,function(k,v){
	        		html_select+="<option value='"+v.bukrs+"'>"+v.txtmd+"</option>";
	        	});
	        	$("#cpjfzd-1 #cpjfzd-tab #selects").html(html_select);
	        	//获取全部的
        		var html_all="<div class='t_r myTable tableSort'>"
    							+"<div class='t_r_t' id='t_r_t1'>" 
        							+"<div class='t_r_title'>"	
										+"<table><thead>"
											+"<tr>"
												+"<th width='230px'>产品名称及型号</th>"
												+"<th width='110px' class='sort-numeric'>计划数量（台）<span class='span1'>&nbsp;↑</span><span class='span2'>&nbsp;↓</span></th>"
												+"<th width='110px' class='sort-numeric'>正点数量（台）<span class='span1'>&nbsp;↑</span><span class='span2'>&nbsp;↓</span></th>"
												+"<th class='sort-numeric'>正点率（%）<span class='span1'>&nbsp;↑</span><span class='span2'>&nbsp;↓</span></th>"
											+"</tr>"
											+"<tr>"
												+"<th width='230px' style='text-align:left;color:#000;'>合计</th>"
												var s1=data.DATA.ONTIMEDELIVER[data.DATA.ONTIMEDELIVER.length-1].plan_menge;
        	        							var s2=data.DATA.ONTIMEDELIVER[data.DATA.ONTIMEDELIVER.length-1].on_time_menge;
        	        							var s3=data.DATA.ONTIMEDELIVER[data.DATA.ONTIMEDELIVER.length-1].on_time_rate*100;
		        					    html_all+="<th width='110px' style='text-align: right;color:#484848;'>"+formatNumber(s1,0,1)+"</th>"
		        					    		+"<th width='110px' style='text-align: right;color:#484848;'>"+formatNumber(s2,0,1)+"</th>"
				        					    +"<th style='text-align: center;color:#484848;'>"+Check_l(s3,2)+"</th>"
				        					+"</tr>"
										+"</thead></table></div></div>"
							+"<div class='t_r_content' id='t_r_content1'> "
								+"<table><tbody>"
								$.each(data.DATA.ONTIMEDELIVER,function(k,v){
									if(k<data.DATA.ONTIMEDELIVER.length-1){
										html_all+="<tr>"
		    			        					+"<td width='230px'>"+v.arktx.slice(11)+"</td>"
		    			        					+"<td width='110px' style='text-align:right;'>"+formatNumber(v.plan_menge,0,1)+"</td>"
		    			        					+"<td width='110px' style='text-align:right;'>"+formatNumber(v.on_time_menge,0,1)+"</td>"
													+"<td style='text-align:center;'>"+Check_l(v.on_time_rate,2)+"</td>"
												+"</tr>";
									}
    	        	        	});
					html_all+="</tbody></table></div>";
				$("#all-c").html(html_all);
				tabletabs(1);
				//当前第一个公司
				var html_first="<div class='t_r myTable tableSort1'>"
									+"<div class='t_r_t' id='t_r_t1'>" 
									+"<div class='t_r_title'>"	
										+"<table><thead>"
											+"<tr>"
												+"<th width='230px'>产品名称及型号</th>"
												+"<th width='110px' class='sort-numeric'>计划数量（台）<span class='span1'>&nbsp;↑</span><span class='span2'>&nbsp;↓</span></th>"
												+"<th width='110px' class='sort-numeric'>正点数量（台）<span class='span1'>&nbsp;↑</span><span class='span2'>&nbsp;↓</span></th>"
												+"<th class='sort-numeric'>正点率（%）<span class='span1'>&nbsp;↑</span><span class='span2'>&nbsp;↓</span></th>"
											+"</tr>"
											+"<tr>"
												+"<th width='230px' style='text-align:left;color:#000;'>合计</th>"
												var s1=data.DATA.ONTIMEDELIVERBYBUKRS[data.DATA.ONTIMEDELIVERBYBUKRS.length-1].plan_menge;
												var s2=data.DATA.ONTIMEDELIVERBYBUKRS[data.DATA.ONTIMEDELIVERBYBUKRS.length-1].on_time_menge;
												var s3=data.DATA.ONTIMEDELIVERBYBUKRS[data.DATA.ONTIMEDELIVERBYBUKRS.length-1].on_time_rate*100;
									 html_first+="<th width='110px' style='text-align: right;color:#484848;'>"+formatNumber(s1,0,1)+"</th>"
												+"<th width='110px' style='text-align: right;color:#484848;'>"+formatNumber(s2,0,1)+"</th>"
												+"<th style='text-align: center;color:#484848;'>"+Check_l(s3,2)+"</th>"
											+"</tr>"
										+"</thead></table></div></div>"
							+"<div class='t_r_content' id='t_r_content1'> "
								+"<table><tbody>"
								$.each(data.DATA.ONTIMEDELIVERBYBUKRS,function(k,v){
									if(k<data.DATA.ONTIMEDELIVERBYBUKRS.length-1){
										html_first+="<tr>"
														+"<td width='230px'>"+v.arktx.slice(11)+"</td>"
							        					+"<td width='110px' style='text-align:right;'>"+formatNumber(v.plan_menge,0,1)+"</td>"
							        					+"<td width='110px' style='text-align:right;'>"+formatNumber(v.on_time_menge,0,1)+"</td>"
														+"<td style='text-align:center;'>"+Check_l(v.on_time_rate,2)+"</td>"
													+"</tr>";
									}
					        	});
					html_first+="</tbody></table></div>";
				$("#cpjfzd-1 #comp").html(html_first);
				tabOrder();
				tabletabs1(1);
				//选择公司获取内容
				$("#selects").on("change",function(){
					var comp=$("#selects option:selected").val();
					$.ajax({
						url: ctx + "/crrc/kpi12/resultProOTDeliverByBukrsGoDown?yearMonth="+timess+"&bukrs="+comp,
						dataType:"json",
						type:"post",
				        success: function (data) {
				        	if ('300' === data.statusCode || '301' === data.statusCode) {
				                alert(data.message);
				                return true;
				            }
				        	var html_comp="<div class='t_r myTable tableSort2'>"
												+"<div class='t_r_t' id='t_r_t1'>" 
												+"<div class='t_r_title'>"	
													+"<table><thead>"
														+"<tr>"
															+"<th width='230px'>产品名称及型号</th>"
															+"<th width='110px' class='sort-numeric'>计划数量（台）<span class='span1'>&nbsp;↑</span><span class='span2'>&nbsp;↓</span></th>"
															+"<th width='110px' class='sort-numeric'>正点数量（台）<span class='span1'>&nbsp;↑</span><span class='span2'>&nbsp;↓</span></th>"
															+"<th class='sort-numeric'>正点率（%）<span class='span1'>&nbsp;↑</span><span class='span2'>&nbsp;↓</span></th>"
														+"</tr>"
														+"<tr>"
															+"<th width='230px' style='text-align:left;color:#000;'>合计</th>"
															var s1=data.DATA.ONTIMEDELIVERBYBUKRS[data.DATA.ONTIMEDELIVERBYBUKRS.length-1].plan_menge;
															var s2=data.DATA.ONTIMEDELIVERBYBUKRS[data.DATA.ONTIMEDELIVERBYBUKRS.length-1].on_time_menge;
															var s3=data.DATA.ONTIMEDELIVERBYBUKRS[data.DATA.ONTIMEDELIVERBYBUKRS.length-1].on_time_rate*100;
															html_comp+="<th width='110px' style='text-align: right;color:#484848;'>"+formatNumber(s1,0,1)+"</th>"
																			+"<th width='110px' style='text-align: right;color:#484848;'>"+formatNumber(s2,0,1)+"</th>"
																			+"<th style='text-align: center;color:#484848;'>"+Check_l(s3,2)+"</th>"
																		+"</tr>"
													+"</thead></table></div></div>"
										+"<div class='t_r_content' id='t_r_content1'> "
											+"<table><tbody>"
											$.each(data.DATA.ONTIMEDELIVERBYBUKRS,function(k,v){
												if(k<data.DATA.ONTIMEDELIVERBYBUKRS.length-1){
													html_comp+="<tr>"
																	+"<td width='230px'>"+v.arktx.slice(11)+"</td>"
										        					+"<td width='110px' style='text-align:right;'>"+formatNumber(v.plan_menge,0,1)+"</td>"
										        					+"<td width='110px' style='text-align:right;'>"+formatNumber(v.on_time_menge,0,1)+"</td>"
																	+"<td style='text-align:center;'>"+Check_l(v.on_time_rate,2)+"</td>"
																+"</tr>";
												}
								        	});
				        		html_comp+="</tbody></table></div>";
							$("#cpjfzd-1 #comp").html(html_comp);
							//$("#cpjfzd-1 .t_r_content").css("max-height","285px");
							tabOrder();
							tabletabs2(1);
							$("#cpjfzd-1 table").css("width","100%");
							$("#cpjfzd-1 table th").css({"border":"1px solid #ddd"});
							$("#cpjfzd-1 table td").css({"border":"1px solid #ddd"});
				        },
				        error:function(){
				        	alert("error按照公司钻取");
				        }
					});
				});
				cancel_this();
				//弹出框拖拽
                var $v3=$("#cpjfzd-1 .tab-title");
                var v3=$v3[0];
                var $s3=$("#cpjfzd-1");
                var s3=$s3[0];
                drag(v3,s3);
				$("#cpjfzd-1 table").css("width","100%");
				//$("#cpjfzd-1 .t_r_content").css("max-height","285px");
				$("#cpjfzd-1 table th").css({"border":"1px solid #ddd"});
				$("#cpjfzd-1 table td").css({"border":"1px solid #ddd"});
	        },
	        error:function(){
	        	alert("error产品正点率交付一级下钻");
	        	$("#Mask").hide();
	        }
		});
	});
	function cancel_this(){
		$("#cpjfzd-1 .cancel").on("click",function(){
			$("#cpjfzd-1").remove();
			$("#Mask").hide();
		});
	}
}
