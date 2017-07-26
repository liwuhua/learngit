var timess=$(".data-time").val().replace(/-/g,"");
scmb_2(timess);
function scmb_2(timess) {
	$.ajax({
		url: ctx + "/crrc/kpi1516/ziChanAndzhouZhuan?time="+timess,
        dataType: "json",
        /*data:reportJson,
        contentType: "application/json;charset=utf-8",*/
        type: "post",
        success: function (data) {
        	if ('300' === data.statusCode) {
                alert(data.message);
                return true;
            }else if('301' === data.statusCode){
                return true;
            }
        	/*var datas={
        		days_Arr:[],//天数
        		days_Arrs:[],//天数去null
        		money_Arr:[],//金额
        		money_Arrs:[],//金额去null
        		time_Arr:[],//x轴日期月份01
        		dataArrYear:[],//日期年2017
        		zuArr:[],//年份分割线
        		dataArrs:[]//日期201701
        	}*/
        	var days_Arr=[],days_Arrs=[],time_Arr=[],money_Arr=[],money_Arrs=[],dataArrYear=[],zuArr=[],dataArrs=[];
        	$.each(data.turnDays,function(k,v){
        		check_data(v.days,days_Arr,days_Arrs);
        	});
        	$.each(data.productMoney,function(k,v){
        		check_data(parseInt(v.balance/10000),money_Arr,money_Arrs);
        		time_Arr.push(v.time.slice(4));
        		dataArrYear.push(v.time.slice(0,4)); 
        		dataArrs.push(v.time);
        	});
        	//遍历数组money_Arrs,days_Arrs去掉数组中的null
            remove_null(money_Arrs);
            remove_null(days_Arrs);
            /*days_Arr=[8,8,8,8,8,8,8,8,8,8,8,8,8];
            days_Arrs=[8,8,8,8,8,8,8,8,8,8,8,8,8];*/
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
            //万元,取最大值最小值和当前值进行显示
            var money_ArrMax=Math.max.apply(null,money_Arrs);
            var money_ArrMin=Math.min.apply(null,money_Arrs);
            var money_ArrMaxInd=money_Arr.indexOf(money_ArrMax);
            var money_ArrMinInd=money_Arr.indexOf(money_ArrMin);
            var money_Arr_this=money_Arrs[money_Arrs.length-1];
            var money_Arr_thisInd=money_Arrs.length-1;
            //天数,取最大值最小值和当前值进行显示
            var days_ArrMax=Math.max.apply(null,days_Arrs);
            var days_ArrMin=Math.min.apply(null,days_Arrs);
            var days_ArrMaxInd=days_Arr.indexOf(days_ArrMax);
            var days_ArrMinInd=days_Arr.indexOf(days_ArrMin);
            var days_Arr_this=days_Arrs[days_Arrs.length-1];
            var days_Arr_thisInd=days_Arrs.length-1;
            /* 
        	 * To change this license header, choose License Headers in Project Properties.
        	 * To change this template file, choose Tools | Templates
        	 * and open the template in the editor.
        	 */
        	var Browser = {};
        	var ua = navigator.userAgent.toLowerCase();
        	if (window.ActiveXObject) {
        	    Browser.ie = ua.match(/msie ([\d.]+)/)[1]
        	}else if("ActiveXObject" in window){//IE11
        	    Browser.ie = "11.0";
        	}else if (ua.indexOf('firefox')) {
        	    Browser.firefox = true;
        	} else if (ua.indexOf('chrome')) {
        	    Browser.chrome = true;
        	} else if (ua.indexOf('opera')) {
        	    Browser.opera = true;
        	}
          //根据电脑屏幕控制图表字体大小
            var client_w= window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth;
            var font,font1,font2,font3,font_l,y_t2,l_y,l_x,x_t_x,x_t_y,st_y,s1_d_y,y2_y;
        	if(client_w<1200){
        		font="10px";
        		font1="10px";
        		font2="8px";
        		font3="6px";
        		font_l="8px";
        		y_t2=-25;
        		l_y=-7;
        		l_x=-12;
        		x_t_x=140;
        		x_t_y=-15;
        		st_y=33;//副标题万元的位置
        		s1_d_y=-17;//年份的位置
        		y2_y=-8;//y轴右标题的y
        	}else if(client_w>1200&&client_w<=1500){
        		font="12px";
        		font1="10px";
        		font2="10px";
        		font3="8px";
        		font_l="8px";
        		y_t2=-28;
        		l_y=-6;
        		l_x=0;
        		x_t_x=190;
        		x_t_y=-22;
        		st_y=37;//副标题万元的位置
        		s1_d_y=-17;//年份的位置
        		y2_y=-14;//y轴右标题的y
        	}else if(client_w>1500&&client_w<=1900){
                font="14px";
                font1="12px";
                font2="10px";
                font3="8px";
                font_l="10px";
                y_t2=-28;
                l_y=-6;
                l_x=0;
                x_t_x=226;
                x_t_y=-22;
                if (Browser.ie === "8.0") {
                    x_t_x=238;
                }
                st_y=37;//副标题万元的位置
                s1_d_y=-17;//年份的位置
                y2_y=-14;//y轴右标题的y
            }else if(client_w>1900){
        		font="18px";
        		font1="14px";
        		font2="14px";
        		font3="14px";
        		font_l="14px";
        		y_t2=-34;
        		l_y=-6;
        		l_x=0;
        		x_t_x=245;
        		x_t_y=-25;
        		st_y=45;//副标题万元的位置
        		s1_d_y=-20;//年份的位置
        		y2_y=-12;//y轴右标题的y
        	}
        	//生产管理目标----在产资金占用
            $('#jsc-sc-dd').highcharts({
                chart: {
                	panning:false,//禁用放大
                	pinchType:'',//禁用手势操作
                    zoomType: '',
                    panKey:'shift',//
                    marginBottom:25,//设置图表与下边的间距
                    marginRight:35
                },
                title: {
                    text: '在产资金占用',
                    align:'left',
                    style:{
                    	fontSize: font,
                        color:"#484848"
                    }
                },
                subtitle:{
                	text: '万元',
                	align:'left',
                	y:st_y,
                	style: {
                        color: '#484848',
                        fontSize: font2
                    }
                },
                xAxis: {
                    categories: time_Arr,
                    crosshair: true,
                    tickLength:3,
                    labels:{
                    	y:15,
                    	rotation:0,
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
                    tickAmount:6,
                    gridLineWidth:0,
                    lineWidth:1,
                    //tickInterval:min,
                    tickPositions:[0,money_max/5,money_max*2/5,money_max*3/5,money_max*4/5,money_max],
                    labels: {
                    	format: '{value:.Of}',
                        style: {
                            color: '#c70019',
                            fontSize:font2
                        },
                    	x:-8
                    },
                    title: {
                        text: '',
                        style: {
                            color:'#c70019'
                        }/*,
                        x:30,
                        y:-50,
                        rotation:0//设置y轴辩题为横向
        */            }
                },{ // Secondary yAxis
                    min:0,
                    max:days_max,
                    tickAmount:6,
                    //tickInterval:max,
                    tickPositions:[0,days_max/5,days_max*2/5,days_max*3/5,days_max*4/5,days_max],
                    title: {
                        text: '天数',
                        align: 'high',//单位的位置
                        style:{
                        	fontSize: font2,
                            color:"#484848"                            
                        },
                        x:y_t2,
                        y:y2_y,
                        rotation:0//设置y轴辩题为横向
                    },
                    labels: {
                    	format: '{value:.Of}',
                        style: {
                            color: '#484848',
                            fontSize:font2
                        },
                    	x:8
                    },
                    opposite: true//y轴是否放到右侧
                }],
                tooltip: {
                    style: {  //提示框内容的样式
                        color: '#595758',
                        fontSize:font1,
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
                    name: '在产资金占用额(万元)',
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
                    name: '在产周转天数(天)',
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
                    /*dataLabels:{
                    	x:-5
                    }*/
                }],
                legend:{
                	width:183,
                	align:"right",
                	y:l_y,
                	x:l_x,
                    verticalAlign: 'top',//图标的位置
                    floating:true,
                    navigation:{
                    	style:{
                    		width:"12px",
                    		height:"12px"
                    	}
                    },
                    itemStyle:{
                    	color:"#484848",
                    	fontSize:font_l,
                    	fontWeight:"normal",
                		cursor:'default'
                    }
                },
                plotOptions: {
                    line: {
                        dataLabels: {
                            enabled: true,//图形上是否显示数据
                            allowOverlap:true,//是否允许数据标签重叠。
                            crop:true,
                            style:{
                                fontSize:font2,//图形上数据的字体大小
                                //opacity:1,
                            	color:"#484848",
                                visibility:"visible",
                                fontFamily:"Times New Roman"
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
                                if(this.point.y==money_max){
	                               	this.point.y=timess.slice(0,4)+"年";
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
        			    		var p_x=mousePos.x;
        			    		var p_y=mousePos.y;
        			    		
                        		var indexs=this.data[event.point.x].index;
                        		var times=dataArrYear[indexs]+""+this.data[event.point.x].category;
                        		$.ajax({
                        			url: ctx + "/crrc/kpi1516/ziChanAndChejian?time="+times,
                        	        dataType: "json",
                        	        type: "post",
                        	        success: function (data) {
                        	        	if ('300' === data.statusCode || '301' === data.statusCode) {
                        	                alert(data.message);
                        	                $("#Mask").hide();
                        	                $(".zczjzy-box").html('');
                        	                return true;
                        	            }
                        	        	var html="<div class='zczjzy-box' style='font-size:12px;'>"
                        	        				+"<div class='name'><span>"+times.slice(0,4)+"年"+times.slice(4)+"月在产资金占用 (单位:万元)</span></div>"
                        	        				+"<p class='can'>×</p>"
                        	        				+"<div class='zczjzy-table'><table>"
                        	        				+"<thead>"
                        	        					+"<tr>"
	                        	        					+"<th>在产类别</th>"
	                        	        					+"<th>占用金额</th>"
                        	        					+"</tr>"
                        	        				+"</thead>"
                        	        				+"<tbody>"
	                        	        				+"<tr>"
		                    	        					+"<td style='text-align:left;'>订单在产</td>"
		                    	        					+"<td style='text-align:right;'>"+formatNumber(data.firstDetail.order_sum/10000,2,1)+"</td>"
		                	        					+"</tr>"
		                	        					+"<tr>"
		                    	        					+"<td style='text-align:left;'>车间仓</td>"
		                    	        					+"<td style='text-align:right;'>"+formatNumber(data.firstDetail.workshop_sum/10000,2,1)+"</td>"
	                	        					+"</tr>"
                        	        				+"</tbody>"
                        	        				+"</table></div></div>"
                        	        	$("body").append(html);
                        	        	$(".zczjzy-box").css({
		            	        	    	"left":p_x+"px",
		            	        	    	"top":p_y+"px"
		            	        	    });
                        	        	//弹出框拖拽
                                        var $v1=$(".zczjzy-box .name");
                                        var v1=$v1[0];
                                        var $s1=$(".zczjzy-box");
                                        var s1=$s1[0];
                                        drag(v1,s1);
                        	        	$(".zczjzy-table table tbody tr td").css("cursor","pointer");
                                        can($(".zczjzy-box .can"),$(".zczjzy-box"),$("#Mask"));
                        	        	$(".zczjzy-box tbody tr").off("dblclick").on("dblclick",function(event){
                        	        		$("#Mask2").show();
                        	        		var ind=$(this).index();
                        	        		if(ind==0){
                        	        			$(".zczjzy-box tbody tr").css("background-color","#fff");
                        	        			$(this).css("background-color","rgb(237, 237, 237)");
                        	        			$.ajax({
                                        			url: ctx + "/crrc/kpi1516/orderTypeSecondDetail?time="+times,
                                        	        dataType: "json",
                                        	        type: "post",
                                        	        success: function (data) {
                                        	        	if ('300' === data.statusCode || '301' === data.statusCode) {
                                        	                alert(data.message);
                                        	                $("#Mask2").hide();
                                        	                $(".cggl-5").html('');
                                        	                return true;
                                        	            }
                                        	        	var html1="<div class='cggl-5' style='font-size:12px;left:53%;'>"
    		        		        								+"<div class='name'><span>"+times.slice(0,4)+"年"+times.slice(4)+"月在产资金占用明细 (单位:元)</span></div>"
    		        		        								+"<p class='cancel1'>×</p>"
                                                                    +"<ul>"
                                                                    	+"<li>订单分类</li>"
                                                                    	//+"<li>无动态</li>"
                                                                    +"</ul>"
                                                                    html1+="<div class='gys-box1'>"
    		        		            								+"<div class='t_r myTable'>"
    		        		            	    							+"<div class='t_r_t'>" 
    		        		            	        						+"<div class='t_r_title'>"	
    		        		            										+"<table>"
    		        		            										+"<thead>"
    		        				        										+"<tr>"
    		        				        											+"<th width='150px'>订单类别</th>"
    		        				        											+"<th width='85px' class='sort-alpha'>生产订单类型<span class='span1'>&nbsp;↑</span><span class='span2'>&nbsp;↓</span></th>"
    		        				        											+"<th class='sort-numeric'>占用总额<span class='span1'>&nbsp;↑</span><span class='span2'>&nbsp;↓</span></th>"
    		        				        										+"</tr>"
    		        				        										var heji=data.secondOrderDetail[0].order_sum
    		        				        										html1+="<tr>"
			        				        											+"<th width='150px' class='table-heji'>合计</th>"
			        				        											+"<th width='85px'></th>"
			        				        											+"<th class='table-heji-jine'>"+Check0(heji,2)+"</th>"
			        				        										+"</tr>"
    		        	        										+"</thead></table></div></div>";
    		        	                									if(data.secondOrderDetail.length==0){
    		        	                										html1+="</div></div>";
    		        	                									}else{
    		        	                										html1+="<div class='t_r_content'> "
        		        	                									+"<table><tbody>";
    		        	                										$.each(data.secondOrderDetail,function(k,v){
    		        	                											if(k>0){
    		        	                												html1+="<tr>"
    		        			        		    			        					+"<td width='150px'  style='text-align:left;'>"+v.ordertype+"</td>"
    		        			        		    			        					+"<td width='85px'  style='text-align:left;'>"+v.auart+"</td>"
    		        			        		    			        					+"<td style='text-align: right;'>"+Check0(v.order_sum,2)+"</td>"
    		        			        		    		        					+"</tr>";
    		        	                											}
    		        	                										});
    		        	                										html1+="</tbody></table></div></div>"
    		        	                									}
    		        	                							/*html1+="<div class='gys-box11' style='display:none;'>"
		        		            								+"<div class='t_r myTable'>"
		        		            	    							+"<div class='t_r_t'>" 
		        		            	        						+"<div class='t_r_title'>"	
		        		            										+"<table>"
		        		            										+"<thead>"
		        				        										+"<tr>"
		        				        											+"<th width='180px'>公司</th>"
		        				        											+"<th width='80px' class='sort-numeric'>占用总额<span class='span1'>&nbsp;↑</span><span class='span2'>&nbsp;↓</span></th>"
		        				        											+"<th width='100px' class='sort-numeric'>无动态资金总额<span class='span1'>&nbsp;↑</span><span class='span2'>&nbsp;↓</span></th>"
		        				        											+"<th width='80px' class='sort-numeric'>半年到1年额<span class='span1'>&nbsp;↑</span><span class='span2'>&nbsp;↓</span></th>"
		        				        											+"<th width='80px' class='sort-numeric'>1年到2年额<span class='span1'>&nbsp;↑</span><span class='span2'>&nbsp;↓</span></th>"
		        				        											+"<th class='sort-numeric'>2年以上额<span class='span1'>&nbsp;↑</span><span class='span2'>&nbsp;↓</span></th>"
		        				        										+"</tr>"
		        				        										+"<tr>"
		        				        											+"<th width='180px' class='table-heji'>合计</th>"
		        				        											+"<th width='80px' class='table-heji-jine'></th>"
		        				        											+"<th width='100px' class='table-heji-jine'></th>"
		        				        											+"<th width='80px' class='table-heji-jine'></th>"
		        				        											+"<th width='80px' class='table-heji-jine'></th>"
		        				        											+"<th class='table-heji-jine'></th>"
		        				        										+"</tr>"
		        	        										+"</thead></table></div></div>";
		        	                									if(data.secondWorkShopDetail.length==0){
		        	                										html1+="</div></div>";
		        	                									}else{
		        	                										html1+="<div class='t_r_content'> "
    		        	                									+"<table><tbody>";
		        	                										$.each(data.secondWorkShopDetail,function(k,v){
    		        			                	        	        		html1+="<tr>"
    		        			        		    			        					+"<td width='180px'  style='text-align:left;'>"+v.val_class+"</td>"
    		        			        		    			        					+"<td width='80px' style='text-align: right;'>"+formatNumber(v.workshop_sum,2,1)+"</td>"
    		        			        		    			        					+"<td width='100px' style='text-align: right;'>"+formatNumber(v.workshop_sum,2,1)+"</td>"
    		        			        		    			        					+"<td width='80px' style='text-align: right;'>"+formatNumber(v.workshop_sum,2,1)+"</td>"
    		        			        		    			        					+"<td width='80px' style='text-align: right;'>"+formatNumber(v.workshop_sum,2,1)+"</td>"
    		        			        		    			        					+"<td style='text-align: right;'>"+formatNumber(v.workshop_sum,2,1)+"</td>"
    		        			        		    		        					+"</tr>";
		        	                										});
		        	                										html1+="</tbody></table></div></div>"
		        	                									}*/
		        	                									
    		        		          							html1+="</div>";
    		        		        					$("body").append(html1);
    		        		        					$(".cggl-5").css("width","440px")
    		        		        					var mousePoss = mouseCoords(event);
    		        		        					var c_l=mousePos.x;
    		        		        					var c_t=mousePos.y-$(".cggl-5").height()-$(".cggl-5 .gys-box1").height()+50;
    		        		        					
    		        		        					$(".cggl-5").css({
    		    		            	        	    	"left":c_l+"px",
    		    		            	        	    	"top":c_t+"px"
    		    		            	        	    });
                                                        can($(".cggl-5 .cancel1"),$(".cggl-5"),$("#Mask2"));
                                                        //弹出框拖拽
                                                        var $v2=$(".cggl-5 .name");
                                                        var v2=$v2[0];
                                                        var $s2=$(".cggl-5");
                                                        var s2=$s2[0];
                                                        drag(v2,s2);
                                                        //排序
                		            	        	    tabOrder();
                		            	        	    
                            	        	        	/*$(".cggl-5 ul li").off("click").on("click",function(){
                            	        	                $(".cggl-5 ul li").css({"border-bottom":"none","font-weight":"normal"});
                            	        	                $(this).css({"border-bottom":"2px solid #999","font-weight":"bold"});
                            	        	                var ind=$(this).index();
                                                            if(ind==0){
                                                                $(".gys-box1").show();
                                                                $(".gys-box11").hide();
                                                                c_t=mousePos.y-$(".cggl-5").height()-$(".cggl-5 .gys-box11").height()+150;
                                                                $(".cggl-5").css({
            		    		            	        	    	"left":c_l+"px",
            		    		            	        	    	"top":c_t+"px"
            		    		            	        	    });
                                                            }else if(ind==1){
                                                            	$(".gys-box1").hide();
                                                                $(".gys-box11").show();
                                                                c_t=mousePos.y-$(".cggl-5").height()-$(".cggl-5 .gys-box1").height()+150;
                                                                $(".cggl-5").css({
            		    		            	        	    	"left":c_l+"px",
            		    		            	        	    	"top":c_t+"px"
            		    		            	        	    });
                                                            }
                            	        	        	})*/
                                        	        },
                                        	        error:function(){
                                        	        	alert("erro订单在产二级下钻");
                                        	        	$("#Mask2").hide();
                                        	        }
                            	        		});
                        	        		}else if(ind==1){
                        	        			$(".zczjzy-box tbody tr").css("background-color","#fff");
                        	        			$(this).css("background-color","rgb(237, 237, 237)");
                        	        			//车间仓下钻
                        	        			$.ajax({
                                        			url: ctx + "/crrc/kpi1516/cheJianCangValClass?time="+times,
                                        	        dataType: "json",   
                                        	        type: "post",
                                        	        success: function (data) {
                                        	        	if ('300' === data.statusCode || '301' === data.statusCode) {
                                        	                alert(data.message);
                                        	                $("#Mask2").hide();
                                        	                $(".cggl-5").html('');
                                        	                return true;
                                        	            }
                                        	        	var html1="<div class='cggl-5' style='width:350px;font-size:12px;left:53%;'>"
    		        		        								+"<div class='name'><span>"+times.slice(0,4)+"年"+times.slice(4)+"月在产资金占用明细 (单位:元)</span></div>"
    		        		        								+"<p class='cancel1'>×</p>"
    		        		        								+"<div class='gys-box1'>"
    		        		            								+"<div class='t_r myTable'>"
    		        		            	    							+"<div class='t_r_t'>" 
    		        		            	        						+"<div class='t_r_title'>"	
    		        		            										+"<table>"
    		        		            										+"<thead>"
    		        				        										+"<tr>"
    		        				        											+"<th width='160px'>评估类描述</th>"
    		        				        											+"<th width='60px'>评估类</th>"
    		        				        											+"<th class='sort-numeric'>占用总额<span class='span1'>&nbsp;↑</span><span class='span2'>&nbsp;↓</span></th>"
    		        				        										+"</tr>"
    		        				        										var heji=data.vclasstDetail[data.vclasstDetail.length-1].workshop_sum
    		        				        										html1+="<tr>"
			        				        											+"<th width='160px' class='table-heji'>合计</th>"
			        				        											+"<th width='60px'></th>"
			        				        											+"<th class='table-heji-jine' style='padding-right:10px;'>"+Check0(heji,2)+"</th>"
			        				        										+"</tr>"
    		        	        										+"</thead></table></div></div>";
    		        	                									if(data.vclasstDetail.length==0){
    		        	                										html1+="</div></div>";
    		        	                									}else{
    		        	                										html1+="<div class='t_r_content'> "
        		        	                									+"<table><tbody>";
    		        	                										$.each(data.vclasstDetail,function(k,v){
    		        	                											if(k<data.vclasstDetail.length-1){
    		        	                												html1+="<tr>"
    		        			        		    			        					+"<td width='160px'  style='text-align:left;'>"+v.txtmd+"</td>"
    		        			        		    			        					+"<td width='60px'  style='text-align:left;'>"+v.val_class+"</td>"
    		        			        		    			        					+"<td style='text-align: right;'>"+formatNumber(v.workshop_sum,2,1)+"</td>"
    		        			        		    		        					+"</tr>";
    		        	                											}
        		        			                	        	        		
    		        	                										});
    		        	                										html1+="</tbody></table></div></div>"
    		        	                									}
    		        		          							html1+="</div>";
    		        		        					$("body").append(html1);
    		        		        					var mousePoss = mouseCoords(event);
    		        		        					var c_l=p_x;
    		        		        					var c_t=mousePos.y-$(".cggl-5").height()+80;
    		        		        					$(".cggl-5").css({
    		        		        						"width":"400px",
    		    		            	        	    	"left":c_l+"px",
    		    		            	        	    	"top":c_t+"px"
    		    		            	        	    });
                                                        can($(".cggl-5 .cancel1"),$(".cggl-4"),$("#Mask2"));
                                                        //弹出框拖拽
                                                        var $v3=$(".cggl-5 .name");
                                                        var v3=$v3[0];
                                                        var $s3=$(".cggl-5");
                                                        var s3=$s3[0];
                                                        drag(v3,s3);
                                                        //排序
    		                                            tabOrder();
                                        	        },
                                        	        error:function(){
                                        	        	alert("erro车间仓二级下钻");
                                        	        	$("#Mask2").hide();
                                        	        }
                            	        		});
                        	        		}
                        	        	});
                        	        },
                        	        error:function(){
                        	        	alert("error在产资金占用额一级下钻");
                        	        	$("#Mask").hide();
                        	        }
                        		});
                        	}
                        }
                    }
                }
            });
            $("#jsc-sc-dd .highcharts-xaxis-labels text:last-child").css("font-weight","bolder");
            $("#jsc-sc-dd .highcharts-xaxis-labels span:last-child").css("font-weight","bolder");
        },
        error:function(){
        	alert("error在产资金占用");
        }
	});
}