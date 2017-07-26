var timess=$(".data-time").val().replace(/-/g,"");
cggl_2(timess);
function cggl_2(timess) {
	//库存资金占用
	$("#cggl-zjzy-tb1 span:eq(0)").css({
		"color":"#c70019",
        "border-bottom":"2px solid #c70019",
        "font-weight":"bold"
    });
	$("#cggl-zjzy-tb1 span:eq(1)").css({
		"color":"#484848",
        "border-bottom":"none",
        "font-weight":"normal"
    });
	$("#cggl-zjzy-tb1 span").css("cursor","pointer");
	$("#cggl-zjzy .cggl-zjzy:eq(0)").show();
	$("#cggl-zjzy .cggl-zjzy:eq(1)").hide();
	var client_w= window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth;
	var font,font1,font2,font3,font_l,l_w,l_y,c_m_r,x_t_x,x_t_y,st1_y,st2_y,y2_y;
	var y_t_x1,y_t_x2,x_t_x2;
	if(client_w<1200){
		$("#cggl-zjzy .cggl-zjzy:eq(1)").css({
			 "width": "295px",
			 "height": "160px" 
		});
		$("#cggl-zjzy .cggl-zjzy:eq(0)").css({
			 "width": "290px",
			 "height": "160px"
		});
		font="10px";
		font1="10px";
		font2="8px";
		font3="6px";
		font_l="8px";
		l_w=100;//图例宽度
		l_y=-6;//图例坐标y
		c_m_r=35;//图表距离右边的距离marginRight
		x_t_x=117;//x轴title"月"的位置
		x_t_x2=117;
		x_t_y=-15;
		st1_y=33;//副标题"万元"的位置
		st2_y=33;
		y2_y=-8;//y轴右标题的y
		y_t_x1=-25;//y轴右标题的x
		y_t_x2=-25;//y轴右标题的x
		s1_d_y=-17;//年份的位置
	}else if(client_w>1200&&client_w<=1500){
		$("#cggl-zjzy .cggl-zjzy:eq(1)").css({
			 "width": "400px",
			 "height": "160px"
		});
		$("#cggl-zjzy .cggl-zjzy:eq(0)").css({
            "width": "400px",
			 "height": "160px"
		});
		font="12px";
		font1="10px";
		font2="10px";
		font3="10px";
		font_l="10px";
		l_w=200;//图例宽度
		l_y=-7;
		c_m_r=30;
		x_t_x=173;
		x_t_x2=170;
		x_t_y=-23;
		st1_y=35;//副标题"万元"的位置
		st2_y=35;
		y2_y=-13;//y轴右标题的y
		y_t_x1=-28;//y轴右标题的x
		y_t_x2=-28;//y轴右标题的x
		s1_d_y=-17;//年份的位置
	}else if(client_w>1500&&client_w<=1900){
		$("#cggl-zjzy .cggl-zjzy:eq(1)").css({
			 "width": "490px",
			 "height": "185px"
		});
		$("#cggl-zjzy .cggl-zjzy:eq(0)").css({
			 "width": "490px",
			 "height": "185px"
		});
		font="14px";
		font1="12px";
		font2="10px";
		font3="8px";
		font_l="10px";
		l_w=200;//图例宽度
		l_y=-7;
		c_m_r=50;
		x_t_x=208;
		x_t_x2=205;
		x_t_y=-23;
		st1_y=35;//副标题"万元"的位置
		st2_y=35;
		y2_y=-13;//y轴右标题的y
		y_t_x1=-28;//y轴右标题的x
		y_t_x2=-28;//y轴右标题的x
		s1_d_y=-17;//年份的位置
	}else if(client_w>1900){
		$("#cggl-zjzy .cggl-zjzy:eq(1)").css({
			 "width": "560px",
			 "height": "220px"
		});
		$("#cggl-zjzy .cggl-zjzy:eq(0)").css({
			 "width": "560px",
			 "height": "220px"
		});
		font="18px";
		font1="14px";
		font2="14px";
		font3="14px";
		font_l="14px";
		l_w=200;//图例宽度
		l_y=-6;
		c_m_r=50;
		x_t_x=238;
		x_t_x2=235;
		x_t_y=-23;
		st1_y=45;//副标题"万元"的位置
		st2_y=45;
		y2_y=-12;//y轴右标题的y
		y_t_x1=-34;//y轴右标题的x
		y_t_x2=-34;//y轴右标题的x
		s1_d_y=-20;//年份的位置
	}		
	$("#cggl-zjzy-tb1 span").on("click",function(){
		var i=$(this).index();
        $("#cggl-zjzy-tb1 span").css({
            "color":"#484848",
            "border-bottom":"none",
            "font-weight":"normal"
        });
        $(this).css({
            "color":"#c70019",
            "border-bottom":"2px solid #c70019",
            "font-weight":"bold"
        });
        $("#cggl-zjzy .cggl-zjzy").hide();
        $("#cggl-zjzy .cggl-zjzy:eq("+i+")").show();
	});
	//原材料
	$.ajax({
		url:ctx+"/crrc/kpi1721/resultMaterial?time="+timess,
		dataType: "json",
        contentType: "application/json;charset=utf-8",
        type: "post",
        success:function(data){
        	if ('300' === data.statusCode ) {
                alert(data.message);
                return true;
            }else if( '301' === data.statusCode){
                return true;
            }
        	var time_Arr=[],days_Arr=[],days_Arrs=[],money_Arr=[],money_Arrs=[],dataYearArr=[],zuArr=[],dataArrs=[];
        	$.each(data.DATA.MATERIALDAYS,function(k,v){
        		time_Arr.push(v.TIME.slice(4));
        		dataYearArr.push(v.TIME.slice(0,4));
        		dataArrs.push(v.TIME);
        		if(v.DAYS<=0){
        			days_Arr.push(null);//天数对应数组
            		days_Arrs.push(null);//去null取最大值
        		}else{
        			var s=v.DAYS;
        			if(s==null){
        				s=s;
        			}else{
        				s=s.toFixed(1);
        				s=Number(s);
        			}
        			days_Arr.push(s);//天数对应数组
            		days_Arrs.push(s);//去null取最大值
        		}
        	});
        	$.each(data.DATA.MATERIALMONEY,function(k,v){
        		if(v.STOCK_MONEY==undefined){
        			money_Arr.push(null);//金额对应数组
            		money_Arrs.push(null);//去null取最大值最小值
        		}else{
        			var s=v.STOCK_MONEY;
        			if(s==null){
        				s=s;
        			}else{
        				s=parseInt(s);
        			}
        			money_Arr.push(s);//金额对应数组
            		money_Arrs.push(s);//去null取最大值最小值
        		}
        	});
        	//遍历数组money_Arrs,days_Arrs去掉数组中的null
            remove_null(money_Arrs);
            remove_null(days_Arrs);
            //取出金额中的最大值最小值
            var money_max=Math.max.apply(null, money_Arrs);
            var money_min=Math.min.apply(null, money_Arrs);
            
            if(money_max>=100000){
            	money_max=Math.floor(money_max/100000)*100000+100000;
            }else if(money_max<100000&&money_max>=10000){
            	money_max=Math.floor(money_max/10000)*10000+10000;
            }else if(money_max<10000&&money_max>=1000){
            	money_max=Math.floor(money_max/1000)*1000+1000;
            }else if(money_max<1000&&money_max>0){
            	money_max=Math.floor(money_max/100)*100+100;
            }else if(money_max==0){
            	money_max=10000;
            }
            if(money_Arr.length==0){
            	money_max=10000;
            }
            //取出天数中的最大值days_max_x
            var days_max_x=Math.max.apply(null, days_Arrs);
            var days_max;
            if(days_max_x==0){
            	days_max=0;
            }else{
            	 //根据天数中的最大值,计算右侧y轴的最大值days_max
                days_max=days_max_x*money_max/money_min;
            }
            if(days_max==0){
            	days_max=25;
            }else if(days_max>0&&days_max<10){
            	days_max=Math.ceil(days_max/15+1)*15;
            }else if(days_max>=10&&days_max<=30){
            	days_max=Math.ceil(days_max/30+1)*30;
            }else if(days_max>=30&&days_max<=75){
            	days_max=Math.ceil(days_max/75+1)*75;
            }else{
            	days_max=Math.ceil(days_max/150+1)*150;
            }
        	//设置年份分割线
        	arrS(dataArrs,zuArr,timess,money_max);
            //万元
            var money_ArrMax=Math.max.apply(null,money_Arrs);
            var money_ArrMin=Math.min.apply(null,money_Arrs);
            var money_ArrMaxInd=money_Arr.indexOf(money_ArrMax);
            var money_ArrMinInd=money_Arr.indexOf(money_ArrMin);
            var money_Arr_this=money_Arrs[money_Arrs.length-1];
            var money_Arr_thisInd=money_Arrs.length-1;
            //天数
            var days_ArrMax=Math.max.apply(null,days_Arrs);
            var days_ArrMin=Math.min.apply(null,days_Arrs);
            var days_ArrMaxInd=days_Arr.indexOf(days_ArrMax);
            var days_ArrMinInd=days_Arr.indexOf(days_ArrMin);
            var days_Arr_this=days_Arrs[days_Arrs.length-1];
            var days_Arr_thisInd=days_Arrs.length-1;
            
        	$("#cggl-zjzy-ycl").highcharts({
                chart: {
                	panning:false,//禁用放大
                	pinchType:'',//禁用手势操作
                    zoomType: '',
                    panKey:'shift',//
                    marginBottom:25,//设置图表与下边的间距
                    marginRight:c_m_r
                },
                title: {
                    text: '库存资金占用',
                    align:'left',
                    style:{
                    	fontSize: font,
                        color:"#484848"
                    }
                },
                subtitle:{
                	text: '万元',
                	align:'left',
                	y:st1_y,
                	style: {
                        fontSize: font2,
                        color:"#484848"
                    }
                },
                xAxis:{
                    categories: time_Arr,
                    crosshair: true,
                    tickLength:3,
                    labels:{
                    	rotation:0,
                    	y:15,
                    	style:{
                    		fontSize:font2,
                        	color:"#484848"
                    	}
                    },
                    title:{
                        text:'月',
                        x:x_t_x,
                        y:x_t_y,
                        style:{
                        	fontSize:font2,
                        	color:"#484848"
                        }
                    }
                },
                yAxis: [{ // Primary yAxis
                    min:0,
                    max:money_max,
                    gridLineWidth:0,
                    lineWidth:1,
                    tickPositions:[0,money_max/5,money_max*2/5,money_max*3/5,money_max*4/5,money_max],
                    labels: {
                        format: '{value}',
                        style: {
                            color: '#c70019',
                            fontSize:font2
                        },
                    	x:-8
                    },
                    title: {
                        text: '',
                       /* x:30,
                        y:-50,
                        rotation:0,//设置y轴辩题为横向
        */                style: {
                            color:'#c70019'
                        }
                    }
                },{ // Secondary yAxis
                    min:0,
                    max:days_max,
                    tickPositions:[0,days_max/5,days_max*2/5,days_max*3/5,days_max*4/5,days_max],
                    title: {
                        text: '天数',
                        align: 'high',//单位的位置
                        rotation: 0,
                        style:{
                        	fontSize: font2,
                            color:"#484848"
                        },
                        x:y_t_x1,
                        y:y2_y,
                        rotation:0//设置y轴辩题为横向
                    },
                    labels: {
                        //format: '{value} mm',
                        style: {
                            color: '#484848',
                            fontSize:font2
                        },
                    	x:8
                    },
                    opposite: true//y轴是否放到右侧
                }],
                tooltip: {
                    headerFormat: '<small>{point.key}</small>月<br/>',//悬浮框中的月份加上月字
                    style: {  //提示框内容的样式
                        color: '#595758',
                        fontSize:font1,
                        fontWeight:"normal"
                    },
                    borderColor:"#d8d8d8",
                    shared: true
                },
                series:[{
                    type: 'column',
                    name: ' ',
                    color:"#d8d8d8",
                    borderWidth : '0',
                    data: zuArr,
                    showInLegend:false,
                    showInLabels:false,
                    showIntooltip:false,
                    pointWidth:2,
                    dataLabels:{
                        y:s1_d_y
                    },
                    tooltip: {
                    	pointFormatter: function() {
                            return null;
                        }
                    }
                },{
                    type: 'line',
                    name: '原材料资金占用额(万元)',
                    color:"#c70019",
                    data: money_Arr,
                    tooltip: {
                        valueSuffix: '万元'
                    },
                    marker: {
                        lineWidth: 1,
                        lineColor: "#c70019",
                        fillColor: '#fff'
                    }
                },{
                    type: 'line',
                    name: '原材料周转天数(天)',
                    yAxis: 1,
                    color:"#595758",
                    data: days_Arr,
                    tooltip: {
                        valueSuffix: '天'
                    },
                    marker: {
                        lineWidth: 1,
                        lineColor: "#595758",
                        fillColor: '#fff'
                    }
                }],
                legend:{
                    width:l_w,
                    layout: 'horizontal',
                    align:"right",
                    //itemMarginBottom:1.5,
                    verticalAlign: 'top',//图标的位置
                    floating: true,
                    //lineHeight: 14,
                    y:l_y,
                    itemStyle:{
                    	color:"#484848",
                    	fontSize:font_l,
                    	fontWeight:"normal",
                    	cursor:'default'
                    },
                    symbolHeight:10,//设置图例符号的高
                    symbolWidth:10//设置图例符号的宽
                },
                plotOptions: {
                    line: {
                        dataLabels: {
                            enabled: true,//图形上是否显示数据
                            allowOverlap:true,//是否允许数据标签重叠。
                            style:{
                            	//opacity:1,
                                visibility:"visible",
                            	color:"#484848",
                                fontFamily:"Times New Roman",
                            	fontSize:font2//图形上数据的字体大小
                            },
                            formatter:function(){
                            	if(this.point.y==0){
                                    return ;
                                }
                                if(this.point.index==money_ArrMaxInd && this.point.y==money_ArrMax){
                                    return this.point.y;
                                }else if(this.point.index==money_ArrMinInd && this.point.y==money_ArrMin){
                                    return this.point.y;
                                }else if(this.point.index==money_Arr_thisInd && this.point.y==money_Arr_this){
                                    return this.point.y;
                                }else if(this.point.index==days_ArrMaxInd && this.point.y==days_ArrMax){
                                    return this.point.y;
                                }else if(this.point.index==days_ArrMinInd && this.point.y==days_ArrMin){
                                    return this.point.y;
                                }else if(this.point.index==days_Arr_thisInd && this.point.y==days_Arr_this){
                                    return this.point.y;
                                }
                            }

                        }
                    },
                    column:{
                    	dataLabels:{
                    		enabled: true,//图形上是否显示数据
                    		allowOverlap:true,//是否允许数据标签重叠。
                            style:{
                                fontSize:font2,//图形上数据的字体大小
                                //opacity:1,
                            	color:"#484848",
                                visibility:"visible",
                                fontFamily:"Times New Roman"
                            },
                            formatter:function(){
                                if(this.point.y==money_max){
	                               	this.point.y=timess.slice(0,4)+"年";
	                               	return this.point.y;
                                }
                            }
                    	}
                    },
                    series: {
                        marker: {
                            radius: 2,  //曲线点半径，默认是4
                            symbol: 'circle' //曲线点类型："circle", "square", "diamond", "triangle","triangle-down"，默认是"circle"
                        },
                    	cursor:"pointer",
                        events:{
                        	//控制图标的图例legend不允许切换
                            legendItemClick: function (event){   
                            	return false; //return  true 则表示允许切换
                            },
                        	dblclick:function(event){
                        		$("#Mask").show();
                        		//确定弹出框的位置
        			    		var mousePos = mouseCoords(event);
        			    		var p_x=mousePos.x-50;
        			    		var p_y=mousePos.y;
        			    		
                        		var indexs=this.data[event.point.x].index;
                        		var times=dataYearArr[indexs]+""+this.data[event.point.x].category;
                        		$.ajax({
                        			url:ctx+"/crrc/kpi1721/resultMaterialGoDown?time="+times+"&&flag=YCL",
                        			dataType: "json",
                        	        contentType: "application/json;charset=utf-8",
                        	        type: "post",
                        	        success:function(data){
                        	        	if ('300' === data.statusCode || '301' === data.statusCode) {
                        	                alert(data.message);
                        	                return true;
                        	            }
                        	        	var html="<div class='cg-ycl'>"
                        	        	 			+"<div class='name'><span>"+times.slice(0,4)+"年"+times.slice(4)+"月原材料资金占用情况(单位:元)</span></div>"
                        	        	 			+"<p class='can1'>×</p>"
                        	        	 			+"<ul>"
		            	        	        	        +"<li>评估类</li>"
		            	        	        	       /* +"<li style='border-bottom:2px solid #999;font-weight:bold;'>无动态</li>"
		            	        	        	        +"<li>过质保</li>"*/
		            	        	        	    +"</ul>" 
                        	        				+"<div class='pgl'><div class='t_r myTable'>"
		            	        	        	    	+"<div class='t_r_t'>" 
		            	        	        	    		+"<div class='t_r_title'>"	
		            	        	        	    			+"<table width='100%'>"
		            	        	        	    				+"<thead>" 
		            	        	        	    				+"<tr>"
		            	        	        	    					+"<th width='150px'>评估类描述</th>"
		            	        	        	    					+"<th width='60px'>评估类</th>"
		            	        	        	    					//+"<th width='100px'>在库金额</th>"
		            	        	        	    					//+"<th width='100px'>供应商库存金额</th>"
		            	        	        	    					+"<th class='sort-numeric'>占用金额<span class='span1'>&nbsp;↑&nbsp;</span><span class='span2'>&nbsp;↓&nbsp;</span></th>"
		            	        	        	    				+"</tr>"
		            	        	        	    				var heji=data.DATA.MATERIALGODOWN[data.DATA.MATERIALGODOWN.length-1].STOCK_MONEY
		            	        	        	    				html+="<tr>"
		            	        	        	    					+"<th width='150px' class='table-heji'>合计</th>"
		            	        	        	    					+"<th width='60px'></th>"
		            	        	        	    					//+"<th width='100px'></th>"
		            	        	        	    					//+"<th width='100px'></th>"
		            	        	        	    					+"<th class='table-heji-jine'>"+Check0(heji,2)+"</th>"
		            	        	        	    				+"</tr>" 
		            	        	        	    				+"</thead>"
                        	        							+"</table></div></div>";
        		            	        	        	if(data.DATA.MATERIALGODOWN.length==0){
        		            	        	        		html+="</div></div>";
        		            	        	        	}else{
        		            	        	        		html+="<div class='t_r_content' style='height: 215px;'> "
                				        							+"<table width='100%'>";
                            	        					$.each(data.DATA.MATERIALGODOWN,function(k,v){
                            	        						if(k<data.DATA.MATERIALGODOWN.length-1){
                            	        							html+="<tr>" +
		                    	        							"<td width='150px'>"+v.TXTMD+"</td>"+
		                    	        							"<td width='60px'>"+v.VAL_CLASS+"</td>"+
		                    	        							//"<td width='100px'></td>"+
		                    	        							//"<td width='100px'></td>"+
		                    	        							"<td style='text-align:right;'>"+formatNumber(v.STOCK_MONEY,2,1)+"</td>"+
		                    	        						"</tr>";
                            	        						}
                            	        					});
                            	        					html+="</table></div></div></div>";
        		            	        	        	}
        		            	        	        	/*html+="<div class='wdt' style='display:none;'><div class='t_r myTable' >"
        		            	    						+"<div class='t_r_t'>" 
        		            	        						+"<div class='t_r_title'>"	
        		            	        						+"<table  width='100%'>"
        		            	        	        	            +"<thead>" 
	        		            	        	        	            +"<tr>"
	        		            	        	        	            	+"<th>公司</th>"
	        		            	        	        	            	+"<th>占用金额</th>"
	        		            	        	        	            	+"<th>无动态资金总额</th>"
	        		            	        	        	            	+"<th>半年到一年额</th>"
	        		            	        	        	            	+"<th>1年到2年额</th>"
	        		            	        	        	            	+"<th>2年以上额</th>"
	        		            	        	        	            +"</tr>"
	        		            	        	        	            +"<tr>"
	        		            	        	        	            	+"<th class='table-heji'>合计</th>"
	        		            	        	        	            	+"<th></th>"
	        		            	        	        	            	+"<th></th>"
	        		            	        	        	            	+"<th></th>"
	        		            	        	        	            	+"<th></th>"
	        		            	        	        	            	+"<th></th>"
	        		            	        	        	            +"</tr>" 
        		            	        	        	            +"</thead>"
        				            	        	        	 +"</table></div></div>"
				            	        	        	     if(data.DATA.DYNAMICGODOWN.length==0){
		        		            	        	        		html+="</div></div>";
		        		            	        	        	}else{
		        		            	        	        		html+="<div class='t_r_content'> "
		        					        							+"<table width='100%'>"
		        					        							$.each(data.DATA.DYNAMICGODOWN,function(k,v){
		        		                    	        	        		html+="<tr>"
		        		        			    			        				+"<td>"+v.TXTMD+"</td>"
		        		        			    			        				+"<td style='text-align: right;'>"+formatNumber(v.SUM_MONEY,2,1)+"</td>"
		        		        			    			        				+"<td style='text-align: right;'>"+formatNumber(v.NODYNAMIC_MONNEY,2,1)+"</td>"
		        		        			    			        				+"<td style='text-align: right;'>"+formatNumber(v.HALFTOONE_YEAR_MONEY,2,1)+"</td>"
		        		        			    			        				+"<td style='text-align: right;'>"+formatNumber(v.ONETOTWO_YEAR_MONEY,2,1)+"</td>"
		        		        			    			        				+"<td style='text-align: right;'>"+formatNumber(v.MORETHANTWO_YEAR_MONEY,2,1)+"</td>"
		        		        			    		        				+"</tr>";            	        	        		
		        		                    	        	        	});
		                        	        					 +"</table></div></div></div>";
		        		            	        	        	} 
                        	        					 html+="<div class='gzb' style='display:none;'><div class='t_r myTable' >"
     		            	    						+"<div class='t_r_t'>" 
     		            	        						+"<div class='t_r_title'>"	
     		            	        						+"<table  width='100%'>"
     		            	        	        	            +"<thead><tr>"
     		            	        	        	            	+"<th>仓库类型</th>"
     		            	        	        	            	+"<th>物料</th>"
     		            	        	        	            	+"<th>占用资金</th>"
     		            	        	        	            +"</tr></thead>"
     				            	        	        	 +"</table></div></div>"
     				            	        	        	 if(data.DATA.DYNAMICGODOWN.length==0){
		        		            	        	        		html+="</div></div>";
		        		            	        	        	}else{
		        		            	        	        		html+="<div class='t_r_content'> "
		         					        							+"<table width='100%'>"
		         					        							$.each(data.DATA.DYNAMICGODOWN,function(k,v){
		         		                    	        	        		html+="<tr>"
		         		        			    			        				+"<td>"+v.TXTMD+"</td>"
		         		        			    			        				+"<td>"+v.TXTMD+"</td>"
		         		        			    			        				+"<td style='text-align: right;'>"+v.TXTMD+"</td>"
		         		        			    		        				+"</tr>";            	        	        		
		         		                    	        	        	});
		                         	        					 +"</table></div></div></div>";
		        		            	        	        	}*/
     				            	        	        
                     	        				html+="</div>";

                        	        	$("body").append(html);
                        	        	var cg_w=p_x-$(".cg-ycl").width()+50;
                        	        	var cg_h=p_y-$(".cg-ycl").height()+50;
                        	        	$(".cg-ycl").css({"width":"410px","left":cg_w+"px","top":cg_h+"px"})
                        	        	
                        	        	 //弹出框拖拽
    		                            var $v1=$(".cg-ycl .name");
    		                            var v1=$v1[0];
    		                            var $s1=$(".cg-ycl");
    		                            var s1=$s1[0];
    		                            drag(v1,s1);
    		                            //排序
                        	        	tabOrder();
                        	        	can($(".cg-ycl .can1"), $(".cg-ycl"), $("#Mask"));
                        	        	/*$(".cg-ycl ul li").off("click").on("click",function(){
            	        	                $(".cg-ycl ul li").css({"border-bottom":"none","font-weight":"normal"});
            	        	                $(this).css({"border-bottom":"2px solid #999","font-weight":"bold"});
            	        	                var ind=$(this).index();
                                            if(ind==0){
                                                $(".wdt").css("display","none");
                                                $(".gzb").css("display","none");
                                                $(".pgl").css("display","block");
                                            }else if(ind==1){
                                            	$(".wdt").css("display","block");
                                                $(".gzb").css("display","none");
                                                $(".pgl").css("display","none");
                                            }else if(ind==2){
                                            	$(".wdt").css("display","none");
                                                $(".gzb").css("display","block");
                                                $(".pgl").css("display","none");
                                            }
            	        	            });*/
                        	        },
                        	        error:function(){
                        	        	//alert("error原材料下钻!");
                        	        }
                        		});
                        		
                        	}
                        }
                    }
                }
            });
        	$(".highcharts-xaxis-labels text:last-child").css("font-weight","bolder");
            $(".highcharts-xaxis-labels span:last-child").css("font-weight","bolder");
        },
        error:function(){
        	alert("error原材料");
        }
	});
	//产成品
	$.ajax({
		url:ctx+"/crrc/kpi1721/resultProduct?time="+timess,
		dataType: "json",
        contentType: "application/json;charset=utf-8",
        type: "post",
        success:function(data){
        	if ('300' === data.statusCode) {
                alert(data.message);
                return true;
            }else if('301' === data.statusCode){
            	return true;
            }
        	var time_Arr=[],days_Arr=[],days_Arrs=[],money_Arr=[],money_Arrs=[],zuArr=[],dataArrs=[],dataYearArr=[];
        	$.each(data.DATA.PRODUCTDAYS,function(k,v){
        		time_Arr.push(v.TIME.slice(4));
        		if(v.DAYS<=0){
        			days_Arr.push(null);//天数对应数组
            		days_Arrs.push(null);//去null取最大值
        		}else{
        			var s=v.DAYS;
        			if(s==null){
        				s=s;
        			}else{
        				s=s.toFixed(1);
        				s=Number(s);
        			}
        			days_Arr.push(s);//天数对应数组
            		days_Arrs.push(s);//去null取最大值
        		}
        		dataArrs.push(v.TIME);
        		dataYearArr.push(v.TIME.slice(0,4))
        	});
        	$.each(data.DATA.PRODUCTMONEY,function(k,v){
        		if(v.STOCK_MONEY==undefined){
        			money_Arr.push(null);//金额对应数组
            		money_Arrs.push(null);//去null取最大值最小值
        		}else{
        			var s=v.STOCK_MONEY;
        			if(s==null){
        				s=s;
        			}else{
        				s=parseInt(s);
        			}
        			money_Arr.push(s);//金额对应数组
            		money_Arrs.push(s);//去null取最大值最小值
        		}
        	});
        	//遍历数组money_Arrs,days_Arrs去掉数组中的null
            remove_null(money_Arrs);
            remove_null(days_Arrs);
            //取出金额中的最大值最小值
            var money_max=Math.max.apply(null, money_Arrs);
            var money_min=Math.min.apply(null, money_Arrs);
            
            if(money_max>10000){
            	money_max=Math.floor(money_max/10000)*10000+10000;
            }else if(money_max<=10000&&money_max>1000){
            	money_max=Math.floor(money_max/1000)*1000+1000;
            }else if(money_max<=1000){
            	money_max=Math.floor(money_max/100)*100+100;
            }else if(money_max==0){
            	money_max=10000;
            }
            if(money_Arr.length==0){
            	money_max=10000;
            }
            //取出天数中的最大值days_max_x
            var days_max_x=Math.max.apply(null, days_Arrs);
            var days_max;
            if(days_max_x==0){
            	days_max=0
            }else{
            	 //根据天数中的最大值,计算右侧y轴的最大值days_max
                days_max=days_max_x*money_max/money_min;
            }
            if(days_max==0){
            	days_max=25;
            }else if(days_max>0&&days_max<10){
            	days_max=Math.ceil(days_max/15+1)*15;
            }else if(days_max>=10&&days_max<=30){
            	days_max=Math.ceil(days_max/30+1)*30;
            }else if(days_max>=30&&days_max<=75){
            	days_max=Math.ceil(days_max/75+1)*75;
            }else{
            	days_max=Math.ceil(days_max/150+1)*150;
            }
        	//设置年份分割线
        	arrS(dataArrs,zuArr,timess,money_max);
            //万元
            var money_ArrMax=Math.max.apply(null,money_Arrs);
            var money_ArrMin=Math.min.apply(null,money_Arrs);
            var money_ArrMaxInd=money_Arr.indexOf(money_ArrMax);
            var money_ArrMinInd=money_Arr.indexOf(money_ArrMin);
            var money_Arr_this=money_Arrs[money_Arrs.length-1];
            var money_Arr_thisInd=money_Arrs.length-1;
            //天数
            var days_ArrMax=Math.max.apply(null,days_Arrs);
            var days_ArrMin=Math.min.apply(null,days_Arrs);
            var days_ArrMaxInd=days_Arr.indexOf(days_ArrMax);
            var days_ArrMinInd=days_Arr.indexOf(days_ArrMin);
            var days_Arr_this=days_Arrs[days_Arrs.length-1];
            var days_Arr_thisInd=days_Arrs.length-1;
        	$("#cggl-zjzy-ccp").highcharts({
                chart: {
                	panning:false,//禁用放大
                	pinchType:'',//禁用手势操作
                    zoomType: '',
                    panKey:'shift',//
                    marginBottom:25,//设置图表与下边的间距
                    marginRight:c_m_r
                },
                title: {
                    text: '库存资金占用',
                    align:'left',
                    style:{
                    	fontSize: font,
                        color:"#484848"
                    }
                },
                subtitle:{
                	text: '万元',
                	align:'left',
                	y:st2_y,
                	style: {
                        fontSize: font2,
                        color:"#484848"
                    }
                },
                xAxis: {
                    categories: time_Arr,
                    tickLength:3,
                    crosshair: true,
                    labels:{
                    	y:15,
                    	rotation:0,
                    	style:{
                    		fontSize: font2,
                        	color:"#484848"
                    	}
                    },
                    title:{
                        text:'月',
                        x:x_t_x2,
                        y:x_t_y,
                        style:{
                        	fontSize:font2,
                        	color:"#484848"
                        }
                    }
                },
                yAxis: [{ // Primary yAxis
                    min:0,
                    max:money_max,
                    gridLineWidth:0,
                    lineWidth:1,
                    tickPositions:[0,money_max/5,money_max*2/5,money_max*3/5,money_max*4/5,money_max],
                    labels: {
                    	format: '{value:.Of}',
                        style: {
                            color: '#c70019',
                            fontSize: font2
                        },
                    	x:-8
                    },
                    title: {
                        text: '',
                       /* x:30,
                        y:-50,
                        rotation:0,//设置y轴辩题为横向
        */                style: {
                            color:'#c70019'
                        }
                    }
                },{ // Secondary yAxis
                    min:0,
                    max:days_max,
                    tickPositions:[0,days_max/5,days_max*2/5,days_max*3/5,days_max*4/5,days_max],
                    title: {
                        text: '天数',
                        align: 'high',//单位的位置
                        rotation: 0,
                        style:{
                        	fontSize: font2,
                            color:"#484848"
                        },
                        x:y_t_x2,
                        y:y2_y,
                        rotation:0//设置y轴辩题为横向
                    },
                    labels: {
                        //format: '{value} mm',
                        style: {
                            color: '#484848',
                            fontSize: font2
                        },
                    	x:8
                    },
                    opposite: true//y轴是否放到右侧
                }],
                tooltip: {
                    style: {  //提示框内容的样式
                        color: '#595758',
                        fontSize: font1,
                        fontWeight:"normal"
                    },
                    borderColor:"#d8d8d8",
                    headerFormat: '<small>{point.key}</small>月<br/>',//悬浮框中的月份加上月字
                    shared: true
                },
                series:[{
                    type: 'column',
                    name: ' ',
                    color:"#d8d8d8",
                    borderWidth : '0',
                    data: zuArr,
                    showInLegend:false,
                    showInLabels:false,
                    showIntooltip:false,
                    pointWidth:2,
                    dataLabels:{
                        y:s1_d_y
                    },
                    tooltip: {
                    	pointFormatter: function() {
                            return null;
                        }
                    }
                },{
                    type: 'line',
                    name: '产成品资金占用额(万元)',
                    color:"#c70019",
                    data: money_Arr,
                    tooltip: {
                        valueSuffix: '万元'
                    },
                    marker: {
                        lineWidth: 1,
                        lineColor: "#c70019",
                        fillColor: '#fff'
                    }
                },{
                    type: 'line',
                    name: '产成品周转天数(天)',
                    yAxis: 1,
                    color:"#595758",
                    data: days_Arr,
                    tooltip: {
                        valueSuffix: '天'
                    },
                    marker: {
                        lineWidth: 1,
                        lineColor: "#595758",
                        fillColor: '#fff'
                    }
                }],
                legend:{
                    width:l_w,
                    layout: 'horizontal',
                    align:"right",
                    //itemMarginBottom:1.5,
                    verticalAlign: 'top',//图标的位置
                    floating: true,
                    //lineHeight: 14,
                    y:l_y,
                    itemStyle:{
                    	color:"#484848",
                    	fontSize:font_l,
                    	fontWeight:"normal",
                    	cursor:'default'
                    },
                    symbolHeight:10,//设置图例符号的高
                    symbolWidth:10//设置图例符号的宽
                },
                plotOptions: {
                    line: {
                        dataLabels: {
                            enabled: true,//图形上是否显示数据
                            allowOverlap:true,//是否允许数据标签重叠。
                            style:{
                            	//opacity:1,
                            	color:"#484848",
                                visibility:"visible",
                                fontFamily:"Times New Roman",
                            	fontSize:font2//图形上数据的字体大小
                            },
                            formatter:function(){
                            	if(this.point.y==0){
                                    return ;
                                }
                                if(this.point.index==money_ArrMaxInd && this.point.y==money_ArrMax){
                                    return this.point.y;
                                }else if(this.point.index==money_ArrMinInd && this.point.y==money_ArrMin){
                                    return this.point.y;
                                }else if(this.point.index==money_Arr_thisInd && this.point.y==money_Arr_this){
                                    return this.point.y;
                                }else if(this.point.index==days_ArrMaxInd && this.point.y==days_ArrMax){
                                    return this.point.y;
                                }else if(this.point.index==days_ArrMinInd && this.point.y==days_ArrMin){
                                    return this.point.y;
                                }else if(this.point.index==days_Arr_thisInd && this.point.y==days_Arr_this){
                                    return this.point.y;
                                }
                            }

                        }
                    },
                    column:{
                    	dataLabels:{
                    		enabled: true,//图形上是否显示数据
                    		allowOverlap:true,//是否允许数据标签重叠。
                            style:{
                                fontSize:font2,//图形上数据的字体大小
                                //opacity:1,
                            	color:"#484848",
                                visibility:"visible",
                                fontFamily:"Times New Roman"
                            },
                            formatter:function(){
                                if(this.point.y==money_max){
	                               	this.point.y=timess.slice(0,4)+"年";
	                               	return this.point.y;
                                }
                            }
                    	}
                    },
                    series: {
                        marker: {
                            radius: 2,  //曲线点半径，默认是4
                            symbol: 'circle' //曲线点类型："circle", "square", "diamond", "triangle","triangle-down"，默认是"circle"
                        },
                        cursor:"pointer",
                        events:{
                        	//控制图标的图例legend不允许切换
                            legendItemClick: function (event){   
                            	return false; //return  true 则表示允许切换
                            },
                        	dblclick:function(event){
                        		$("#Mask").show();
                        		//确定弹出框的位置
        			    		var mousePos = mouseCoords(event);
        			    		var p_x=mousePos.x-50;
        			    		var p_y=mousePos.y;
        			    		
                        		var indexs=this.data[event.point.x].index;
                        		var times=dataYearArr[indexs]+""+this.data[event.point.x].category;
                        		$.ajax({
                        			url:ctx+"/crrc/kpi1721/resultMaterialGoDown?time="+times+"&&flag=CCP",
                        			dataType: "json",
                        	        contentType: "application/json;charset=utf-8",
                        	        type: "post",
                        	        success:function(data){
                        	        	if ('300' === data.statusCode || '301' === data.statusCode) {
                        	                alert(data.message);
                        	                return true;
                        	            }
                        	        	/*var html="<div class='cg-ycl2'>"
	                	        	 			+"<div class='name'><span>截至"+times.slice(0,4)+"年"+times.slice(4)+"月产成品无动态分布情况(单位:万元)</span></div>"
	                	        	 			+"<p class='can1'>×</p>"
	                	        				+"<div class='wdt2'><div class='t_r myTable' >"
			            	    						+"<div class='t_r_t'>" 
			            	        						+"<div class='t_r_title'>"	
			            	        						+"<table  width='100%'>"
			            	        	        	            +"<thead><tr>"
			            	        	        	            	+"<th width='180px'>公司</th>"
			            	        	        	            	+"<th width='80px'>占用总额</th>"
			            	        	        	            	+"<th width='100px'>无动态资金总额</th>"
			            	        	        	            	+"<th width='80px'>半年到一年额</th>"
			            	        	        	            	+"<th width='80px'>1年到2年额</th>"
			            	        	        	            	+"<th>2年以上额</th>"
			            	        	        	            +"</tr></thead>"
					            	        	        	 +"</table></div></div>"
					            	        	        	 if(data.DATA.DYNAMICGODOWN.length==0){
		        		            	        	        		html+="</div></div>";
		        		            	        	        	}else{
		        		            	        	        		html+="<div class='t_r_content'> "
		        					        							+"<table width='100%'>"
		        					        							$.each(data.DATA.DYNAMICGODOWN,function(k,v){
		        		                    	        	        		html+="<tr>"
		        		        			    			        				+"<td width='180px'>"+v.TXTMD+"</td>"
		        		        			    			        				+"<td style='text-align: right;' width='80px'>"+formatNumber(v.SUM_MONEY,2,1)+"</td>"
		        		        			    			        				+"<td style='text-align: right;' width='100px'>"+formatNumber(v.NODYNAMIC_MONNEY,2,1)+"</td>"
		        		        			    			        				+"<td style='text-align: right;' width='80px'>"+formatNumber(v.HALFTOONE_YEAR_MONEY,2,1)+"</td>"
		        		        			    			        				+"<td style='text-align: right;' width='80px'>"+formatNumber(v.ONETOTWO_YEAR_MONEY,2,1)+"</td>"
		        		        			    			        				+"<td style='text-align: right;'>"+formatNumber(v.MORETHANTWO_YEAR_MONEY,2,1)+"</td>"
		        		        			    		        				+"</tr>";            	        	        		
		        		                    	        	        	});
	                        	        					 +"</table></div></div></div>";
	        		            	        	        	} 
	             	        				html+="</div>";*/
                        	        	var html="<div class='cg-ycl'>"
                        	        		+"<div class='name'><span>截至"+times.slice(0,4)+"年"+times.slice(4)+"月产成品无动态分布情况(单位:元)</span></div>"
                	        	 			+"<p class='can1'>×</p>"
                	        	 			+"<ul>"
            	        	        	        +"<li>评估类</li>"
            	        	        	        //+"<li style='border-bottom:2px solid #999;font-weight:bold;'>无动态</li>"
            	        	        	    +"</ul>" 
	            	        	        	var  heji_pgl=data.DATA.MATERIALGODOWN[data.DATA.MATERIALGODOWN.length-1].STOCK_MONEY
	            	        	        	html+="<div class='pgl' ><div class='t_r myTable' >"
	            	    						+"<div class='t_r_t'>" 
	            	        						+"<div class='t_r_title'>"	
	            	        						+"<table  width='100%'>"
	            	        	        	            +"<thead>" 
		            	        	        	        +"<tr>"
        	        	        	    					+"<th width='150px'>评估类描述</th>"
        	        	        	    					+"<th width='50px'>评估类</th>"
        	        	        	    					//+"<th width='100px'>在库金额</th>"
        	        	        	    					//+"<th width='100px'>供应商库存金额</th>"
        	        	        	    					+"<th class='sort-numeric'>占用总额<span class='span1'>&nbsp;↑&nbsp;</span><span class='span2'>&nbsp;↓&nbsp;</span></th>"
        	        	        	    				+"</tr>" 
        	        	        	    				+"<tr>"
        	        	        	    					+"<th width='150px' class='table-heji'>合计</th>"
        	        	        	    					+"<th width='50px'></th>"
        	        	        	    					//+"<th width='100px'></th>"
        	        	        	    					//+"<th width='100px'></th>"
        	        	        	    					+"<th class='table-heji-jine' style='padding-right:10px;'>"+Check0(heji_pgl,2)+"</th>"
        	        	        	    				+"</tr>" 
	            	        	        	            +"</thead>"
			            	        	        	 +"</table></div></div>"
	            	        	        	     if(data.DATA.MATERIALGODOWN.length==0){
    		            	        	        		html+="</div></div>";
    		            	        	        	}else{
    		            	        	        		html+="<div class='t_r_content'> "
    					        							+"<table width='100%'>"
    					        							$.each(data.DATA.MATERIALGODOWN,function(k,v){
    					        								if(k<data.DATA.MATERIALGODOWN.length-1){
    					        									html+="<tr>"
    		        			    			        				+"<td width='150px'>"+v.TXTMD+"</td>"
    		        			    			        				+"<td width='50px'>"+v.VAL_CLASS+"</td>"
    		        			    			        				//+"<td width='100px'></td>"
    		        			    			        				//+"<td width='100px'></td>"
    		        			    			        				+"<td style='text-align: right;'>"+Check0(v.STOCK_MONEY,2)+"</td>"
    		        			    		        				+"</tr>";   
    					        								}
    		                    	        	        		         	        	        		
    		                    	        	        	});
    		            	        	        		html+="</table></div></div></div>";
    		            	        	        	}
                        	        	
                        	        	/*html+="<div class='wdt' style='display:none;'><div class='t_r myTable'>"
    	        	        	    	+"<div class='t_r_t'>" 
    	        	        	    		+"<div class='t_r_title'>"	
    	        	        	    			+"<table width='100%'>"
    	        	        	    				+"<thead>" 
    	        	        	    				+"<tr>"
        	        	        	    				+"<th width='180px'>公司</th>"
            	        	        	            	+"<th width='80px'>占用金额</th>"
            	        	        	            	+"<th width='100px'>无动态资金总额</th>"
            	        	        	            	+"<th width='80px'>半年到一年额</th>"
            	        	        	            	+"<th width='80px'>1年到2年额</th>"
            	        	        	            	+"<th>2年以上额</th>"
    	        	        	    				+"</tr>" 
    	        	        	    				+"<tr>"
        	        	        	    				+"<th width='180px'>合计</th>"
            	        	        	            	+"<th width='80px'></th>"
            	        	        	            	+"<th width='100px'><th>"
            	        	        	            	+"<th width='80px'></th>"
            	        	        	            	+"<th width='80px'></th>"
            	        	        	            	+"<th></th>"
    	        	        	    				+"</tr>" 
    	        	        	    				+"</thead>"
        	        							+"</table></div></div>";
            	        	        	if(data.DATA.DYNAMICGODOWN.length==0){
            	        	        		html+="</div></div>";
            	        	        	}else{
            	        	        		html+="<div class='t_r_content' style='height: 215px;'> "
				        							+"<table width='100%'>";
            	        					$.each(data.DATA.DYNAMICGODOWN,function(k,v){
            	        						html+="<tr>" 
	                    	        						+"<td width='180px'>"+v.TXTMD+"</td>"
	    			    			        				+"<td style='text-align: right;' width='80px'>"+formatNumber(v.SUM_MONEY,2,1)+"</td>"
	    			    			        				+"<td style='text-align: right;' width='100px'>"+formatNumber(v.NODYNAMIC_MONNEY,2,1)+"</td>"
	    			    			        				+"<td style='text-align: right;' width='80px'>"+formatNumber(v.HALFTOONE_YEAR_MONEY,2,1)+"</td>"
	    			    			        				+"<td style='text-align: right;' width='80px'>"+formatNumber(v.ONETOTWO_YEAR_MONEY,2,1)+"</td>"
	    			    			        				+"<td style='text-align: right;'>"+formatNumber(v.MORETHANTWO_YEAR_MONEY,2,1)+"</td>"
                    	        						+"</tr>";
            	        					});
            	        					html+="</table></div></div></div>";
            	        	        	}*/
             	        				html+="</div>";
                        	        	$("body").append(html);
                        	        	var cg_w=p_x-$(".cg-ycl").width()+50;
                        	        	var cg_h=p_y-$(".cg-ycl").height();
                                        $(".cg-ycl").css({"width":"390px","left":cg_w+"px","top":cg_h+"px"})
                        	        	 //弹出框拖拽
    		                            var $v2=$(".cg-ycl .name");
    		                            var v2=$v2[0];
    		                            var $s2=$(".cg-ycl");
    		                            var s2=$s2[0];
    		                            drag(v2,s2);
    		                            //排序
                        	        	tabOrder();
                        	        	can($(".cg-ycl .can1"), $(".cg-ycl"), $("#Mask"));
                        	        	/*$(".cg-ycl ul li").off("click").on("click",function(){
            	        	                $(".cg-ycl ul li").css({"border-bottom":"none","font-weight":"normal"});
            	        	                $(this).css({"border-bottom":"2px solid #999","font-weight":"bold"});
            	        	                var ind=$(this).index();
                                            if(ind==1){
                                                $(".wdt").css("display","block");
                                                $(".pgl").css("display","none");
                                                //$(".pgl").css("display","block");
                                            }else if(ind==0){
                                            	$(".pgl").css("display","block");
                                                $(".wdt").css("display","none");
                                                //$(".pgl").css("display","none");
                                            }
                                            else if(ind==2){
                                            	$(".wdt").css("display","none");
                                                $(".gzb").css("display","block");
                                                $(".pgl").css("display","none");
                                            }
            	        	            });*/
                        	        },
                        	        error:function(){
                        	        	//alert("error产成品下钻!");
                        	        }
                        		});
                        		
                        	}
                        }
                    }
                }
            });
        	$(".cggl-zjzy .highcharts-xaxis-labels text:last-child").css("font-weight","bolder");
            $(".cggl-zjzy .highcharts-xaxis-labels span:last-child").css("font-weight","bolder");
        },
        error:function(){
        	//alert("error产成品");
        }
	});
}