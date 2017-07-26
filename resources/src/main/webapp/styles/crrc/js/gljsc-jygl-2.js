var timess=$(".data-time").val().replace(/-/g,"");
jygl_2(timess);
function jygl_2(timess) {
	var reportJson={
		dateYearMonthStart:timess
	 }
	 reportJson = JSON.stringify(reportJson);
	 $.ajax({
		url: ctx + "/crrc/kpi0105/getDueTo",
        dataType: "json",
        data:reportJson,
        contentType: "application/json;charset=utf-8",
        type: "post",
        success: function (data) {
        	if ('300' === data.statusCode) {
                alert(data.message);
                $("#jsc-2").html('');
                return true;
            }else if('301' === data.statusCode){
            	$("#jsc-2").html('');
                return true;
            }
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
            var font,font1,font2,font3,font_l,l_sh,l_w,l_x,l_y,x_t_x,x_t_y,p_c_p,st_y,s1_d_y,l_i;
        	if(client_w<=1200){
        		font="10px";
        		font1="10px";
        		font2="8px";
        		font3="6px";
        		l_sh=8;
        		font_l="8px";
        		l_w=210;
        		l_x=15;
        		l_y=-7;
        		x_t_x=140;//x轴标题"月"的位置
        		x_t_y=-15;
        		p_c_p=10;//每个条形图的宽度
        		st_y=33;//副标题万元的位置
        		s1_d_y=-17;//年份的位置
        		l_i=2;//每个图例间的间距
        	}else if(client_w>1200&&client_w<=1500){
        		font="12px";
        		font1="10px";
        		font2="10px";
        		font3="10px";
        		l_sh=8;
        		font_l="10px";
        		l_w=250;
        		l_x=0;
        		l_y=-7;
        		x_t_x=194;
        		x_t_y=-21;
        		p_c_p=12;
        		st_y=35;//副标题万元的位置
        		s1_d_y=-18;//年份的位置
        		l_i=4;//每个图例间的间距
        	}else if(client_w>1500&&client_w<=1900){
        		font="14px";
        		font1="12px";
        		font2="10px";
        		font3="8px";
        		l_sh=10;
        		font_l="10px";
        		l_w=250;
        		l_x=0;
        		l_y=-5;
        		x_t_x=220;
        		x_t_y=-22;
        		p_c_p=12;
        		if (Browser.ie === "8.0") {
        			x_t_x=240;
            	}
        		st_y=35;//副标题万元的位置
        		s1_d_y=-17;//年份的位置
        		l_i=8;//每个图例间的间距
        	}else if(client_w>1900){
        		font="18px";
        		font1="14px";
        		font2="14px";
        		font3="14px";
        		l_sh=12;
        		font_l="14px";
        		l_w=300;
        		l_x=0;
        		l_y=-8;
        		x_t_x=250;
        		x_t_y=-25;
        		p_c_p=14;
        		if (Browser.ie === "8.0") {
        			x_t_x=250;
            	}
        		st_y=40;
        		s1_d_y=-20;//年份的位置
        		l_i=8;//每个图例间的间距
        	}
        	
        	if(jQuery.isEmptyObject(data)){
        		var arr=[];
    			var a = timess;
    			for(var i=13;i>=1;i--){
    				var times = new Date(a.substr(0,4),Number(a.substr(4))-i);
    				var zero = (times.getMonth()+1)<10?'0'+(times.getMonth()+1):times.getMonth()+1;
    				var str = times.getFullYear()+''+zero;
    				arr.push(str);
    			}
        		var ymax1=50000,zuArr=[];
    			var sArr=[null,null,null,null,null,null,null,null,null,null,null,null,null]
    			var arrs=[],datArr=[];
    			for(var i=0;i<13;i++){
    				arrs.push(arr[i].slice(4));
    				if(arr[i]==timess){
    					datArr.push(50000);
    				}else{
    					datArr.push(null);
    				}
    			}
    			
    			//应收账款金额
                $("#jsc-2").highcharts({
                	chart:{
                		marginBottom:25/*,//设置图表与下边的间距
                		marginLeft:30*/
                	},
                    title: {
                        text: '应收账款及回款',
                        align:"left",
                        style:{
                        	fontSize: font,
                            color:"#484848"
                        }
                    },
                    subtitle:{
                    	text: '万元',
                    	align:'left',
                    	//x:10,
                    	y:st_y,
                    	style: {
                            fontSize: font2,
                        	color:"#484848"
                        }
                    },
                    tooltip: {
                        shared: true,
                        borderColor:"#d8d8d8",
                        style:{
                        	color: '#595758',
                            fontSize:font1,
                            fontWeight:"normal"
                        },
                        headerFormat: '<small>{point.key}</small>月<br/>'
                    },
                    /*legend: {
                    	width:l_w,
                        gridLineColor:"#d8d8d8",
                        layout: 'horizontal',
                        align:"right",
                        itemMarginBottom:1.5,
                        verticalAlign: 'top',
                        y: l_y,
                        x:l_x,
                        floating: true,
                        lineHeight: 14,
                        itemDistance:l_i,
                        backgroundColor: '#FFFFFF',
                        itemStyle:{
                            fontSize : font_l,//设置图例文字的大小
                            fontWeight:"normal",
                            color:"#484848",
                            fill:"",
                            cursor:'default'
                        },
                        symbolHeight:l_sh,//设置图例符号的高
                        symbolWidth:l_sh//设置图例符号的宽
                    },*/
                    xAxis: {
                        categories: arrs,
                        crosshair: true,
                        tickLength:3,
                        //textTransform: uppercase,
                        labels:{
                        	rotation:0,
                            y:15,//设置x轴刻度值得位置
                            style:{
                            	//textTransform: uppercase,
                            	fontSize:font2,
                            	color:"#484848"
                            }                           
                        },
                        title:{
                            text:'(月)',
                            x:x_t_x,
                            y:x_t_y,
                            style:{
                            	fontSize:font2,
                            	color:"#484848"
                            }
                        }
                    },
                    yAxis:{
                        min:0,
                        max:ymax1,
                        gridLineWidth:1,
                        minorTickWidth:1,
                        tickPositions:[0,ymax1/5,ymax1*2/5,ymax1*3/5,ymax1*4/5,ymax1],
                        lineWidth:1,
                        title:{
                            text:''/*,*/
                           /* x:30,
                            y:-50,
                            rotation:0,//设置y轴辩题为横向
                            style:{
                                fontStyle : 'normal'
                            }*/
                        },
                        labels: {
                        	format: '{value:.Of}',
                        	x:-8,
                        	style:{
                            	fontSize:font2,
                            	color:"#484848"
                            }
                        },
                        gridLineColor:"#d8d8d8"
                    },
                    legend: {
                    	width:l_w,
                        gridLineColor:"#d8d8d8",
                        layout: 'horizontal',
                        align:"right",
                        itemMarginBottom:1.5,
                        itemDistance:2,
                        verticalAlign: 'top',
                        y: l_y,
                        x:l_x,
                        floating: true,
                        lineHeight: 14,
                        backgroundColor: '#FFFFFF',
                        itemStyle:{
                            fontSize : font_l,//设置图例文字的大小
                            fontWeight:"normal",
                            color:"#484848",
                            fill:""                          
                        },
                        symbolHeight:l_sh,//设置图例符号的高
                        symbolWidth:l_sh//设置图例符号的宽
                    },
                    plotOptions: {
                        column: {
                            grouping: false,//将图标叠加在一起
                            pointWidth:p_c_p,
                            enableMouseTracking: false,//去掉鼠标放到文字上的阴影
                            dataLabels: {
                                enabled: true,//图形上是否显示数据
                                style:{
                                	opacity:1,
                                    visibility:"visible",
                                    fontFamily:"Times New Roman",
                                	fontSize:font2,//图形上数据的字体大小
                                	color:"#484848"
                                },
                                formatter:function(){
                                	if(this.point.y==50000){
                                		this.point.y=timess.slice(0,4)+"年";
                                        return this.point.y;
                                    }
                                }
                            },
                            enableMouseTracking: false//鼠标移动时有信息提示

                        },
                        line:{
                        	enableMouseTracking: false,//去掉鼠标放到文字上的阴影
                            dataLabels: {
                                enabled: false,//图形上是否显示数据
                            	color:"#484848"
                            },
                            enableMouseTracking: false//鼠标移动时有信息提示
                        },
                        series:{
                        	cursor:"pointer",
                            events:{
                            	//控制图标的图例legend不允许切换
                                legendItemClick: function (event){   
                                	return false; //return  true 则表示允许切换
                                }
                            }
                        }
                    },
                    series: [{
                        type: 'column',
                        name: ' ',
                        color:"#d8d8d8",
                        borderWidth : '0',
                        data: datArr,
                        showInLegend:false,
                        showInLabels:false,
                        showIntooltip:false,
                        pointWidth:2,
                        dataLabels:{
                        	y:s1_d_y,
                            marker: { 
                            	fillCollor:null
    	                    }
                        },
                        tooltip: {
                        	pointFormatter: function() {
                                return null;
                            }
                        },
                        marker: { 
	                        fillCollor:null
	                    }
                    },{
                        type: 'column',
                        name: '应收账款',
                        color:"#c6c6c6",
                        borderWidth : '0',
                        data: sArr,
                        tooltip: {
                            valueSuffix: '万元'
                        },
                        legend:{
                        	style:{
                        		width:font2,
                            	height:font2
                        	}
                        }
                    },{
                        type: 'column',
                        name: '本期新增',
                        color:"#484848",
                        borderWidth : '0',
                        data: sArr,
                        tooltip: {
                            valueSuffix: '万元'
                        }
                    },{
                        type: 'line',
                        name: '本期回款',
                        color:"#c70019",
                        lineWidth: 1,
                        data: sArr,
                        tooltip: {
                            valueSuffix: '万元'
                        },
                        marker: {
                            lineWidth: 1,
                            lineColor: "#c70019",
                            radius:2,
                            outerRadius:2,
                            fillColor: '#c70019'
                        }
                    }]
                });
                $(".highcharts-xaxis-labels text:last-child").css("font-weight","bolder");
                $(".highcharts-xaxis-labels span:last-child").css("font-weight","bolder");
                return true;
        	}
        	var zkArr=[],hkArr=[],xzArr=[],dataArr=[],dataArrYear=[],dataArrs=[],zuArr=[],zkArrs=[],hkArrs=[],xzArrs=[];
        	$.each(data.allDueto,function(k,v){
        		var sh=formatNumber(v.dmbtr_sh_all,0,0);
        		var s=formatNumber(v.dmbtr_s,0,0);
        		var h=formatNumber(v.dmbtr_h,0,0);
        		zkArr.push(Number(sh));
        		hkArr.push(Number(h));
        		xzArr.push(Number(s));
        		zkArrs.push(Number(sh));
        		hkArrs.push(Number(h));
        		xzArrs.push(Number(s));
        		dataArr.push(v.s_date.slice(4));
        		dataArrYear.push(v.s_date.slice(0,4));
        		dataArrs.push(v.s_date);
        	});        	
        	//当前显示的截止日期
        	var lateData=data.latestdate;
        	//lateData="201703";
        	var lateDataInd=dataArrs.indexOf(lateData);
        	//判断当前截止日期,只显示此日期前的所有柱子(包括当前截止日期)
        	/*if(lateDataInd==-1){
        		zkArr=zkArr;
        		hkArr=hkArr;
        		xzArr=xzArr;
        		zkArrs=zkArrs;
        		hkArrs=hkArrs;
        		xzArrs=xzArrs;
        		lateDataInd=12;
        	}else{
        		for(var i=lateDataInd+1;i<zkArr.length;i++){
        			zkArr[i]=null;
        			hkArr[i]=null;
        			xzArr[i]=null;
        			zkArrs[i]=null;
        			hkArrs[i]=null;
        			xzArrs[i]=null;
        		}
        	}*/
        	
        	/*var ymax1=zkArr[0],lvsArr=[]*/;
        	/*for(var i=0;i<zkArr.length;i++){
        		if(ymax1<Number(zkArr[i])){
        			ymax1=Number(zkArr[i]);
        		}
        		/*lvsArr.push(Number(lvArr[i]));*/
        	//}
        	/*zkArr=[1000,10001,1000,1000,1000,1000,1000,1000,1000,10000,10000,10000,10000];
        	hkArr=[1000,10001,1000,1000,1000,1000,1000,1000,1000,10000,10000,10000,10000];
        	xzArr=[1000,10001,1000,1000,1000,1000,1000,1000,10000,10000,10000,10000,10000];*/
        	//取所有数据的最大值,确定当前图表y轴的最大值与刻度
        	var ymax1 = Math.max.apply(null, hkArr.concat(xzArr).concat(zkArr));
        	if(ymax1>150000){
        		ymax1=Math.floor(ymax1/100000)*100000+150000;
            }else if(ymax1<=150000&&ymax1>=100000){
        		ymax1=Math.floor(ymax1/100000)*100000+100000;
            }else if(ymax1<100000&&ymax1>50000){
            	ymax1=Math.floor(ymax1/10000)*10000+20000;
            }else if(ymax1<=50000&&ymax1>=10000){
            	ymax1=Math.floor(ymax1/10000)*10000+10000;
            }else if(ymax1<10000&&ymax1>=1000){
            	ymax1=Math.floor(ymax1/1000)*1000+2000;
            }else if(ymax1<1000&&ymax1>0){
            	ymax1=Math.floor(ymax1/100)*100+200;
            }else if(ymax1==0){
            	ymax1=10000;
            }
        	/*
        	 * 年份分割线
        	 * dataArrs   X轴坐标数组
        	 * zuArr  当前选中的日期数组(为空)
        	 * timess  页面右上角选择的日期(例201703)
        	 * ymax1 y轴坐标的最大值
        	*/
        	arrS(dataArrs,zuArr,timess,ymax1);
        	//遍历数组zkArrs,hkArrs,xzArrs去掉数组中的null
        	remove_null(zkArrs);
            remove_null(hkArrs);
            remove_null(xzArrs);
            //应收账款标出最大最小值以及当前值
        	/*var datalabels={
        		dmbtr_sh_all:{
        			zkArrMax:Math.max.apply(null,zkArr),
        			zkArrMaxInd:zkArr.indexOf(zkArrMax),
        			zkArrMinInd:zkArr.indexOf(zkArrMin),
        			zkArrMin:Math.min.apply(null,zkArr),
        			zkArr_this:zkArr[12]
        		},
        		dmbtr_s:{
        			
        		}
        			
        			
        	}*/
            var zkArrMax=Math.max.apply(null,zkArr);
            var zkArrMin=Math.min.apply(null,zkArr);
            var zkArrMaxInd=zkArr.indexOf(zkArrMax);
            var zkArrMinInd=zkArr.indexOf(zkArrMin);
            var zkArr_this=zkArr[12];
            var zkArr_thisInd=12;
            //本期新增标出最大最小值以及当前值
            var xzArrMax=Math.max.apply(null,xzArr);
            var xzArrMin=Math.min.apply(null,xzArr);
            var xzArrMaxInd=xzArr.indexOf(xzArrMax);
            var xzArrMinInd=xzArr.indexOf(xzArrMin);
            var xzArr_this=xzArr[xzArr.length-1];
            var xzArr_thisInd=12;
            //本期回款标出最大最小值以及当前值
            var hkArrMax=Math.max.apply(null,hkArr);
            var hkArrMin=Math.min.apply(null,hkArr);
            var hkArrMaxInd=hkArr.indexOf(hkArrMax);
            var hkArrMinInd=hkArr.indexOf(hkArrMin);
            var hkArr_this=hkArr[hkArr.length-1];
            var hkArr_thisInd=12;
            
        	//应收账款金额
            $("#jsc-2").highcharts({
            	chart:{
            		panning:false,//禁用放大
                	pinchType:'',//禁用手势操作
                    zoomType: '',
                    panKey:'shift',//
            		marginBottom:25/*,//设置图表与下边的间距
            		marginLeft:30*/
            	},
                title: {
                    text: '应收账款及回款',
                    align:"left",
                    style:{
                    	fontSize: font,
                        color:"#484848"
                    }
                },
                subtitle:{
                	text: '万元',
                	align:'left',
                	//x:30,
                	y:st_y,
                	style: {
                        fontSize: font2,
                    	color:"#484848"
                    }
                },
                tooltip: {
                    shared: true,
                    followPointer: true,
                    //align:"left",
                    //headerFormat: '<small>{point.key}</small>月<br/>',
                    useHTML: true, 
                    //tooltip中数字右对齐
                    headerFormat: '<small style="padding-left:8px;">{point.key}</small>月<br/><table>', 
                    pointFormat: '<tr><td><span style="display:inline-block;width:4px;height:4px;background-color:{series.color};vertical-align: middle;margin:-3px 3px 0px -3px;"></span>{series.name}: </td>' + '<td style="text-align: right"><b>{point.y} </b></td></tr>', 
                    footerFormat: '</table>',
                    borderColor:"#d8d8d8",
                    style:{
                    	color: '#595758',
                        fontSize:font1,
                        fontWeight:"normal",
                        textAlign:"justify"
                    }
                },
                xAxis: {
                    categories: dataArr,
                    crosshair: true,
                    tickLength:3,
                    labels:{
                    	//autoRotation:0,
                        rotation:0,
                    	y:15,//设置x轴刻度值得位置
                        style:{
                        	fontSize:font2,
                        	textShadow: "none",
                        	textOutline: "none",
                        	textFill:"none",
                        	color:"#484848"
                        }
                    },
                    title:{
                        text:'月',
                        x:x_t_x,
                        y:x_t_y,
                        style:{
                        	fontSize:font2,
                        	textShadow: "none",
                        	textOutline: "none",
                        	textFill:"none",
                        	color:"#484848"
                        }
                    }
                },
                yAxis:{
                    min:0,
                    max:ymax1,
                    gridLineWidth:1,
                    minorTickWidth:1,
                    tickPositions:[0,ymax1/5,ymax1*2/5,ymax1*3/5,ymax1*4/5,ymax1],
                    lineWidth:1,
                    title:{
                        text:''/*,*/
                       /* x:30,
                        y:-50,
                        rotation:0,//设置y轴辩题为横向
                        style:{
                            fontStyle : 'normal'
                        }*/
                    },
                    labels: {
                    	format: '{value:.Of}',
                    	x:-8,
                    	style:{
                        	fontSize:font2,
                        	color:"#484848"
                        }
                    },
                    gridLineColor:"#d8d8d8"
                },
                legend: {
                	width:l_w,
                    gridLineColor:"#d8d8d8",
                    layout: 'horizontal',
                    align:"right",
                    //itemMarginBottom:1.5,
                    verticalAlign: 'top',
                    y: l_y,
                    x:l_x,
                    floating: true,
                    itemDistance:l_i,
                    backgroundColor: '#FFFFFF',
                    itemStyle:{
                        fontSize : font_l,//设置图例文字的大小
                        fontWeight:"normal",
                        color:"#484848",
                        fill:"",
                        cursor:'default'
                    },
                    symbolHeight:l_sh,//设置图例符号的高
                    symbolWidth:l_sh//设置图例符号的宽
                },
                plotOptions: {
                    column: {
                    	shadow: false, 
                        grouping: false,//将图标叠加在一起
                        pointWidth:p_c_p,
                        //enableMouseTracking: false,//去掉鼠标放到文字上的阴影
                        dataLabels: {
                            enabled: true,//图形上是否显示数据
                            //allowOverlap:true,//是否允许数据标签重叠。
                            //x:20,
                            /*crop:true,*/
                            style:{
                            	textShadow: "none",
                            	textOutline: "none",
                            	textFill:"none",
                            	/*overflow: 'none',
                                crop: false,*/
                            	//opacity:1,
                            	//filter: "progid:DXImageTransform.Microsoft.Alpha(opacity=100)",
                            	color:"#484848",
                                visibility:"visible",
                                fontFamily:"Times New Roman",
                            	fontSize:font2//图形上数据的字体大小
                            },
                            formatter:function(){
                            	//return console.log(this.point)
                            	var s=this.point.y;
                            	//s=s.toFixed(2);
                            	if(this.point.y==0){
                                    return false;
                                }
                               if(this.point.index==zkArrMaxInd && this.point.y==zkArrMax){
                            	   return s;
                                }else if(this.point.index==zkArrMinInd && this.point.y==zkArrMin){
                                	return s;
                                }else if(this.point.index==zkArr_thisInd && this.point.y==zkArr_this){
                                	return s;
                                }else if(this.point.index==xzArrMaxInd && this.point.y==xzArrMax){
                                	return s;
                                }else if(this.point.index==xzArrMinInd && this.point.y==xzArrMin){
                                	return s;
                                }else if(this.point.index==xzArr_thisInd && this.point.y==xzArr_this){
                                	return s;
                                }
                                if(this.point.y==ymax1){
	                               	this.point.y=timess.slice(0,4)+"年";
	                               	return this.point.y;
                                }
                                
                            }
                       },
                        enableMouseTracking: true//鼠标移动时有信息提示
                    },
                    line:{
                    	enableMouseTracking: false,//去掉鼠标放到文字上的阴影
                        dataLabels: {
                        	/*overflow: 'none',
                            crop: false,*/
                        	//allowOverlap:true,//是否允许数据标签重叠。
                            enabled: true,//图形上是否显示数据
                            style:{
                            	textShadow: "none",
                            	textOutline: "none",
                            	textFill:"none",
                            	//opacity:1,
                                visibility:"visible",
                                fontFamily:"Times New Roman",
                            	fontSize:font2,//图形上数据的字体大小
                            	color:"#484848"
                            },
                            formatter:function(){
                            	var s=this.point.y;
                            	//s=s.toFixed(2);
                            	if(this.point.y==0){
                                    return false;
                                }
                                if(this.point.index==hkArrMaxInd && this.point.y==hkArrMax){
                                	return s;
                                }else if(this.point.index==hkArrMinInd && this.point.y==hkArrMin){
                                	return s;
                                }else if(this.point.index==hkArr_thisInd && this.point.y==hkArr_this){
                                	return s;
                                }
                            }
                        },
                        enableMouseTracking: true//鼠标移动时有信息提示
                    },
                    series:{
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
                        		var dataTime={
                        				dateYearMonthStart:times
                        		}
                        		dataTime = JSON.stringify(dataTime);
                        		$.ajax({
                        			url:ctx+"/crrc/kpi0105/getFirstType",
            	        			dataType: "json",
            	        	        data:dataTime,
            	        	        contentType: "application/json;charset=utf-8",
            	        	        type: "post",
            	        	        success:function(data){
            	        	        	if ('300' === data.statusCode || '301' === data.statusCode) {
            	        	                alert(data.message);
            	        	                $("#Mask").hide();
            	        	                return true;
            	        	            }
            	        	        	//应收账款及回款一级下钻
            	        	        	//ERP内部客户分类详情合计
            	        	        	var s1_all=Math.round(zkArr[indexs]-Math.round(data.hybukrsDueto[data.hybukrsDueto.length-1].hyys_all/10000));//原值
            	        	        	var s2_all=Math.round(data.custDueto[data.custDueto.length-1].badloan/10000);//坏账
            	        	        	var s3_all=Math.round(data.custDueto[data.custDueto.length-1].dmbtr_sh/10000);//净值
	        							var s4_all=Math.round(hkArr[indexs]-Math.round(data.hybukrsDueto[data.hybukrsDueto.length-1].dmbtr_h/10000));//回款
	        							var s5_all=Math.round(xzArrs[indexs]-Math.round(data.hybukrsDueto[data.hybukrsDueto.length-1].dmbtr_s/10000));//新增
	        							//ERP内部公司详情合计
 	        							var s1_n=Math.round(zkArr[indexs]-Math.round(data.hybukrsDueto[data.hybukrsDueto.length-1].hyys_all/10000));//原值
            	        	        	var s2_n=Math.round(data.bukrsDueto[data.bukrsDueto.length-1].badloan/10000);//坏账
            	        	        	var s3_n=Math.round(data.bukrsDueto[data.bukrsDueto.length-1].dmbtr_sh/10000);//净值
	        							var s4_n=Math.round(hkArr[indexs]-Math.round(data.hybukrsDueto[data.hybukrsDueto.length-1].dmbtr_h/10000));//回款
	        							var s5_n=Math.round(xzArrs[indexs]-Math.round(data.hybukrsDueto[data.hybukrsDueto.length-1].dmbtr_s/10000));//新增	 
	        							//ERP外部公司详情合计
 	        							var s1_w=Math.round(data.hybukrsDueto[data.hybukrsDueto.length-1].hyys_all/10000);//原值
            	        	        	var s2_w=Math.round(data.hybukrsDueto[data.hybukrsDueto.length-1].hyhz/10000);//坏账
            	        	        	var s3_w=Math.round(data.hybukrsDueto[data.hybukrsDueto.length-1].hyys/10000);//净值
            	        	        	var s4_w=Math.round(data.hybukrsDueto[data.hybukrsDueto.length-1].dmbtr_h/10000);//回款
            	        	        	var s5_w=Math.round(data.hybukrsDueto[data.hybukrsDueto.length-1].dmbtr_s/10000);//新增
            	        	        	var str="<div class='yszk-tab1e'>"
		            	        	        	    +"<div class='name'><span>"+times.slice(0,4)+"年"+times.slice(4)+"月应收账及回款情况(单位：万元)</span></div>"
                                                    +"<p class='can1'>×</p>"
		            	        	        	    +"<ul>"
		            	        	        	        +"<li style='border-bottom:2px solid #999;font-weight:bold;'>ERP内部客户分类</li>"
		            	        	        	        +"<li>ERP内部公司</li>"
		            	        	        	        +"<li>ERP外部公司</li>"
		            	        	        	    +"</ul>"
		            	        	        	    +"<div class='kh'><div class='t_r myTable'>"
		            	    						+"<div class='t_r_t'>" 
		            	        						+"<div class='t_r_title'>"	
		            	        						+"<table width='100%'>"
		            	        	        	            +"<thead><tr>"
		            	        	        	                +"<th width='170px'>客户类别</th>"
		            	        	        	                +"<th width='80px' class='sort-numeric'>期末应收账款<br/>（原值）<span class='span1'>&nbsp;↑</span><span class='span2'>&nbsp;↓</span></th>"
		            	        	        	                +"<th width='80px' class='sort-numeric'>计提坏账<span class='span1'>&nbsp;↑</span><span class='span2'>&nbsp;↓</span></th>"
		            	        	        	                +"<th width='80px' class='sort-numeric'>期末应收账款<br/>（净值）<span class='span1'>&nbsp;↑</span><span class='span2'>&nbsp;↓</span></th>"
		            	        	        	                +"<th width='80px' class='sort-numeric'>本期回款<span class='span1'>&nbsp;↑</span><span class='span2'>&nbsp;↓</span></th>"
		            	        	        	                +"<th class='sort-numeric'>本期新增<span class='span1'>&nbsp;↑</span><span class='span2'>&nbsp;↓</span></th>"
		            	        	        	            +"</tr>" 
		            	        	        	            +"<tr>"
			            	        	        	            +"<th width='170px' class='table-heji'>合计</th>"
			            	        	        	           /* var s1=Math.round(data.custDueto[data.custDueto.length-1].dmbtr_sh/10000);//净值
            	        	        							var s2=Math.round(data.custDueto[data.custDueto.length-1].badloan/10000);//坏账
            	        	        							var s3=Math.round(data.custDueto[data.custDueto.length-1].dmbtr_h/10000);//回款
            	        	        							var s4=Math.round(data.custDueto[data.custDueto.length-1].dmbtr_s/10000);//新增
            	        	        							var s5=Math.round(data.custDueto[data.custDueto.length-1].dmbtr_sh_all/10000);//原值
*/            	        	        							
			            	        	        	            str+="<th width='80px' class='table-heji-jine' style='padding-right:10px;'>"+Check0(s1_all,0)+"</th>"//原值
            	        	        								+"<th width='80px' class='table-heji-jine' style='padding-right:10px;'>"+Check0(s1_all-s3_all,0)+"</th>"//坏账
            	        	        								+"<th width='80px' class='table-heji-jine' style='padding-right:10px;'>"+Check0(s3_all,0)+"</th>"//净值
            	        	        								+"<th width='80px' class='table-heji-jine' style='padding-right:10px;'>"+Check0(s4_all,0)+"</th>"//回款
            	        	        								+"<th class='table-heji-jine' style='padding-right:10px;'>"+Check0(s5_all,0)+"</th>"//新增
            	        	        						+"</tr>"
		            	        	        	        +"</thead>"
		            	        	        	        +"</table></div></div>"
				        								+"<div class='t_r_content'> "
				        									+"<table width='100%'>"
		            	        	        	        $.each(data.custDueto,function(k,v){
		            	        	        	        	if(k<data.custDueto.length-1){
		            	        	        	        		var s1=Math.round(v.dmbtr_sh/10000);//净值
            	        	        							var s2=Math.round(v.badloan/10000);//坏账
            	        	        							var s3=Math.round(v.dmbtr_h/10000);//回款
            	        	        							var s4=Math.round(v.dmbtr_s/10000);//新增
            	        	        							var s5=Math.round(v.dmbtr_sh_all/10000);//原值
		            	        	        	        		str+="<tr>"
	        			    			        					+"<td width='170px'>"+v.cust_type+"<input type='hidden' value='"+v.cust_group+"'></td>"
	        			    			        					+"<td width='80px' style='text-align: right;'>"+Check0(s1+s2,0)+"</td>"//原值
	        			    			        					+"<td width='80px' style='text-align: right;'>"+Check0(s2,0)+"</td>"//坏账
	        			    			        					+"<td width='80px' style='text-align: right;'>"+Check0(s1,0)+"</td>"//净值
	        			    			        					+"<td width='80px' style='text-align: right;'>"+Check0(s3,0)+"</td>"//回款
	        			    			        					+"<td style='text-align: right;'>"+Check0(s4,0)+"</td>"//新增
	        			    			        				+"</tr>"; 
		            	        	        	        	}        	        	        		
		                    	        	        	});
		            	        	        	    str+="</table></div></div></div>"
		            	        	        	    +"<div class='gs' style='display:none;'><div class='t_r myTable' >"
		            	    						+"<div class='t_r_t'>" 
		            	        						+"<div class='t_r_title'>"	
		            	        						+"<table width='100%'>"
		            	        	        	            +"<thead><tr>"
		            	        	        	                +"<th width='170px' >公司名称</th>"
		            	        	        	                +"<th width='80px' class='sort-numeric'>期末应收账款<br/>（原值）<span class='span1'>&nbsp;↑</span><span class='span2'>&nbsp;↓</span></th>"
		            	        	        	                +"<th width='80px' class='sort-numeric'>计提坏账<span class='span1'>&nbsp;↑</span><span class='span2'>&nbsp;↓</span></th>"
		            	        	        	                +"<th width='80px' class='sort-numeric'>期末应收账款<br/>（净值）<span class='span1'>&nbsp;↑</span><span class='span2'>&nbsp;↓</span></th>"
		            	        	        	                +"<th width='80px' class='sort-numeric'>本期回款<span class='span1'>&nbsp;↑</span><span class='span2'>&nbsp;↓</span></th>"
		            	        	        	                +"<th class='sort-numeric'>本期新增<span class='span1'>&nbsp;↑</span><span class='span2'>&nbsp;↓</span></th>"
		            	        	        	            +"</tr>"
		            	        	        	            +"<tr>"
		            	        	        	                +"<th width='170px' class='table-heji'>合计</th>"
		        	        	                              
			        	        							str+="<th width='80px' class='table-heji-jine' style='padding-right:10px;'>"+Check0(s1_n,0)+"</th>"//原值
			        	        								+"<th width='80px' class='table-heji-jine' style='padding-right:10px;'>"+Check0(s1_n-s3_n,0)+"</th>"//坏账
			        	        								+"<th width='80px' class='table-heji-jine' style='padding-right:10px;'>"+Check0(s3_n,0)+"</th>"//净值
			        	        								+"<th width='80px' class='table-heji-jine' style='padding-right:10px;'>"+Check0(s4_n,0)+"</th>"//回款
			        	        								+"<th class='table-heji-jine' style='padding-right:10px;'>"+Check0(s5_n,0)+"</th>"//新增
				        	        						+"</tr>" 
		            	        	        	            +"</thead>"
		            	        	        	        +"</table></div></div>"
		            	        	        	        +"<div class='t_r_content'> "
			        									+"<table width='100%'>"
		            	        	        	        $.each(data.bukrsDueto,function(k,v){
		            	        	        	        	if(k<data.bukrsDueto.length-1){		
		            	        	        	        		var s1=Math.round(v.dmbtr_sh/10000);//净值
            	        	        							var s2=Math.round(v.badloan/10000);//坏账
            	        	        							var s3=Math.round(v.dmbtr_h/10000);//回款
            	        	        							var s4=Math.round(v.dmbtr_s/10000);//新增
            	        	        							var s5=Math.round(v.dmbtr_sh_all/10000);//原值
			                    	        	        		str+="<tr>"
			        			    			        				+"<td width='170px'>"+v.comName+"<input type='hidden' value='"+v.bukrs+"'></td>"
			        			    			        				+"<td width='80px' style='text-align: right;'>"+Check0(s1+s2,0)+"</td>"//原值
			        			    			        				+"<td width='80px' style='text-align: right;'>"+Check0(s2,0)+"</td>"//坏账
			        			    			        				+"<td width='80px' style='text-align: right;'>"+Check0(s1,0)+"</td>"//净值
			        			    			        				+"<td width='80px' style='text-align: right;'>"+Check0(s3,0)+"</td>"//回款
			        			    			        				+"<td style='text-align: right;'>"+Check0(s4,0)+"</td>"//新增
			        			    			        			+"</tr>";
		            	        	        	        		}
		                    	        	        	});
		            	        	        	    str+="</table></div></div></div>"
		            	        	        	    +"<div class='hygs' style='display:none;'><div class='t_r myTable' >"
		            	    						+"<div class='t_r_t'>" 
		            	        						+"<div class='t_r_title'>"	
		            	        						+"<table width='100%'>"
		            	        	        	            +"<thead><tr>"
			            	        	        	            +"<th width='170px' >公司名称</th>"
		            	        	        	                +"<th width='80px' class='sort-numeric'>期末应收账款<br/>（原值）<span class='span1'>&nbsp;↑</span><span class='span2'>&nbsp;↓</span></th>"
		            	        	        	                +"<th width='80px' class='sort-numeric'>计提坏账<span class='span1'>&nbsp;↑</span><span class='span2'>&nbsp;↓</span></th>"
		            	        	        	                +"<th width='80px' class='sort-numeric'>期末应收账款<br/>（净值）<span class='span1'>&nbsp;↑</span><span class='span2'>&nbsp;↓</span></th>"
		            	        	        	                +"<th width='80px' class='sort-numeric'>本期回款<span class='span1'>&nbsp;↑</span><span class='span2'>&nbsp;↓</span></th>"
		            	        	        	                +"<th class='sort-numeric'>本期新增<span class='span1'>&nbsp;↑</span><span class='span2'>&nbsp;↓</span></th>"
		            	        	        	            +"</tr>" 
		            	        	        	            +"<tr>"
		            	        	        	                +"<th width='170px' class='table-heji'>合计</th>"
		            	        	        	
		            	        	        					str+="<th width='80px' class='table-heji-jine' style='padding-right:10px;'>"+Check0(s1_w,0)+"</th>"//原值
			            	        	        	                +"<th width='80px' class='table-heji-jine' style='padding-right:10px;'>"+Check0(s2_w,0)+"</th>"//坏账
			            	        	        	                +"<th width='80px' class='table-heji-jine' style='padding-right:10px;'>"+Check0(s3_w,0)+"</th>"//净值
			            	        	        	                +"<th width='80px' class='table-heji-jine' style='padding-right:10px;'>"+Check0(s4_w,0)+"</th>"//回款
			            	        	        	                +"<th class='table-heji-jine' style='padding-right:10px;'>"+Check0(s5_w,0)+"</th>"//新增
					    			        				str+="</tr>"
		            	        	        	        +"</thead>"
		            	        	        	        +"</table></div></div>"
		            	        	        	        +"<div class='t_r_content'> "
			        									+"<table width='100%'>"
		            	        	        	        $.each(data.hybukrsDueto,function(k,v){
		            	        	        	        	if(k<data.hybukrsDueto.length-1){
		            	        	        	        		var s1=Math.round(v.hyys_all/10000);//原值
		            	        	        	        		var s2=Math.round(v.hyhz/10000);//坏账
		            	        	        	        		var s3=Math.round(v.hyys/10000);//净值
		            	        	        	        		var s4=Math.round(v.dmbtr_h/10000);//回款
		            	        	        	        		var s5=Math.round(v.dmbtr_s/10000);//新增
		            	        	        	        		str+="<tr>"
		                    	        	        				+"<td width='170px'>"+v.comName+"</td>"
		                    	        	        				+"<td width='80px' style='text-align: right;'>"+Check0(s1,0)+"</td>"//原值
		                    	        	        				+"<td width='80px' style='text-align: right;'>"+Check0(s2,0)+"</td>"//坏账
		                    	        	        				+"<td width='80px' style='text-align: right;'>"+Check0(s3,0)+"</td>"//净值
		                    	        	        				+"<td width='80px' style='text-align: right;'>"+Check0(s4,0)+"</td>"//回款
		                    	        	        				+"<td style='text-align: right;'>"+Check0(s5,0)+"</td>"//新增
		                    	        	        			+"</tr>";
		            	        	        	        	}         	        	        		
		                    	        	        	});
		            	        	        	    str+="</table></div></div></div>"
		            	        	        	+"</div>"
		            	        	    $("body").append(str);
        	        	        	    
		            	        	    $(".yszk-tab1e").css({
		            	        	    	"padding-bottom":"10px",
		            	        	    	"width":"695px",
		            	        	    	"left":p_x+"px",
		            	        	    	"top":p_y+"px"
		            	        	    });
		            	        	    //$(".yszk-tab1e .t_r_content").css("height","250px");
		            	        	   // $(".hygs .t_r").css("width","71%");
		            	        	    //弹出框拖拽
		                                var $v1=$(".yszk-tab1e .name");
		                                var v1=$v1[0];
		                                var $s1=$(".yszk-tab1e");
		                                var s1=$s1[0];
		                                drag(v1,s1);
		                                //排序
		            	        	    tabOrder();
            	        	        	$(".yszk-tab1e ul li").off("click").on("click",function(){
            	        	                $(".yszk-tab1e ul li").css({"border-bottom":"none","font-weight":"normal"});
            	        	                $(this).css({"border-bottom":"2px solid #999","font-weight":"bold"});
            	        	                var ind=$(this).index();
                                            if(ind==0){
                                                $(".kh").show();
                                                $(".gs").hide();
                                                $(".hygs").hide();
                                            }else if(ind==1){
                                            	$(".kh").hide();
                                                $(".gs").show();
                                                $(".hygs").hide();
                                            }else if(ind==2){
                                                $(".kh").hide();
                                                $(".gs").hide();
                                                $(".hygs").show();
                                            }
            	        	            });
            	        	        	//按客户二级钻取
            	        	        	$(".yszk-tab1e .kh tbody tr").css("cursor","pointer");
            	        	        	$(".yszk-tab1e .kh tbody tr").off("dblclick").on("dblclick",function(event){
                                            $(".yszk-tab1e .kh tbody tr").css("background","#ffffff");
                                            $(this).css("background","#ededed");
            	        	        		var vall=$(this).find("input").val();
            	        	        		var khbm={
                                    				dateYearMonthStart:times,
                                    				productType:vall
                                    		}
            	        	        		khbm = JSON.stringify(khbm);
            	        	        		$("#Mask2").show();
            	        	        		$.ajax({
            	        	        			url:ctx+"/crrc/kpi0105/getSecondCustType",
                        	        			dataType: "json",
                        	        	        data:khbm,
                        	        	        contentType: "application/json;charset=utf-8",
                        	        	        type: "post",
                        	        	        success:function(data){
                        	        	        	if ('300' === data.statusCode || '301' === data.statusCode) {
                        	        	                alert(data.message);
                        	        	                $("#Mask2").hide();
                        	        	                return true;
                        	        	            }
                        	        	        	var html="<div class='cggl-3' style='width: 640px;'>"
		        		        								+"<div class='name'><span>"+times.slice(0,4)+"年"+times.slice(4)+"月应收账款前十大客户明细(单位：万元)</span></div>"
                                                                +"<p class='cancel1'>×</p>"
		        		        								+"<div class='gys-box1'>"
		        		            								+"<div class='t_r myTable'>"
		        		            	    							+"<div class='t_r_t' id='t_r_t1'>"
		        		            	        						+"<div class='t_r_title'>"	
		        		            										+"<table>"
		        		            										+"<thead>"
		        				        										+"<tr>"
		        				        											+"<th width='200px'>客户名称</th>"
		        				        											+"<th class='sort-numeric' width='80px'>期末应收账款<br/>（原值）<span class='span1'>&nbsp;↑</span><span class='span2'>&nbsp;↓</span></th>"
		        			            	        	        	                +"<th class='sort-numeric' width='80px'>计提坏账<span class='span1'>&nbsp;↑</span><span class='span2'>&nbsp;↓</span></th>"
		        				        											+"<th class='sort-numeric' width='80px'>期末应收账款<br/>（净值）<span class='span1'>&nbsp;↑</span><span class='span2'>&nbsp;↓</span></th>"
		        				        											+"<th class='sort-numeric' width='80px'>本期回款<span class='span1'>&nbsp;↑</span><span class='span2'>&nbsp;↓</span></th>"
		        				        											+"<th class='sort-numeric'>本期新增<span class='span1'>&nbsp;↑</span><span class='span2'>&nbsp;↓</span></th>"
		        				        										+"</tr>"
		        	        										+"</thead></table></div></div>"
		        	                								+"<div class='t_r_content' id='t_r_content1'> "
		        	                									+"<table><tbody>"
		        			        									$.each(data.custDueto,function(k,v){
		        			        										var s1=Math.round(v.dmbtr_sh);//净值
		                	        	        							var s2=Math.round(v.badloan);//坏账
		                	        	        							var s3=Math.round(v.dmbtr_h);//回款
		                	        	        							var s4=Math.round(v.dmbtr_s);//新增
		                	        	        							var s5=Math.round(v.dmbtr_sh_all);//原值
		        			                	        	        		html+="<tr>"
		        			        		    			        					+"<td width='200px'>"+v.customer+"</td>"
		        			        		    			        					+"<td width='80px' style='text-align: right'>"+Check0(s1+s2,0)+"</td>"//原值
		        			        		    			        					+"<td width='80px' style='text-align: right;'>"+Check0(s2,0)+"</td>"//坏账
		        			        		    			        					+"<td width='80px' style='text-align: right;'>"+Check0(s1,0)+"</td>"//净值
		        			        		    			        					+"<td width='80px' style='text-align: right;'>"+Check0(v.dmbtr_h,0)+"</td>"//回款
		        			        		    			        					+"<td style='text-align: right'>"+Check0(v.dmbtr_s,0)+"</td>"//新增
	        			        		    			        					+"</tr>";
		        			                	        	        	});
		        		        							  html+="</tbody></table></div>"
		        		          							+"</div></div></div>"
		        		        					$("body").append(html);
		        		        					$(".cggl-3").css({"width":"750px","padding-bottom":"10px"});
		        		        					//确定弹出框的位置
		        	        			    		var mousePoss = mouseCoords(event);
		        	        			    		var c_ll=p_x;
		        	        			    		var c_tt;
		        	        			    		if(mousePoss.y>380){
		        	        			    			c_tt=mousePoss.y-$(".cggl-3").height();
		        	        			    		}else{
		        	        			    			c_tt=mousePoss.y;
		        	        			    		}
		    		        			    		$(".cggl-3").css({"left":c_ll+"px","top":c_tt+"px"});
                                                    can2();
    		        							    function can2(){
    		        							        $(".cancel1").on("click",function(){
    		        							            $(".cggl-3").remove();
    		        							            $("#Mask2").hide();
    		        							        });
    		        							    }
    		        							    //弹出框拖拽
    				                                var $v2=$(".cggl-3 .name");
    				                                var v2=$v2[0];
    				                                var $s2=$(".cggl-3");
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
            	        	        	//按公司二级钻取
            	        	        	$(".yszk-tab1e .gs tbody tr").css("cursor","pointer");
            	        	        	$(".yszk-tab1e .gs tbody tr").off("dlclick").on("dblclick",function(event){
                                            $(".yszk-tab1e .gs tbody tr").css("background","#ffffff");
                                            $(this).css("background","#ededed");
            	        	        		var vall=$(this).find("input").val();
            	        	        		var gsmc=$(this).find("td").eq(0).text();
            	        	        		var khbm={
                                    				dateYearMonthStart:times,
                                    				compCodeValue:[vall]
                                    		}
            	        	        		khbm = JSON.stringify(khbm);
            	        	        		$("#Mask2").show();
            	        	        		$.ajax({
            	        	        			url:ctx+"/crrc/kpi0105/getSecondBukrsType",
                        	        			dataType: "json",
                        	        	        data:khbm,
                        	        	        contentType: "application/json;charset=utf-8",
                        	        	        type: "post",
                        	        	        success:function(data){
                        	        	        	if ('300' === data.statusCode || '301' === data.statusCode) {
                        	        	                alert(data.message);
                        	        	                $("#Mask2").hide();
                        	        	                return true;
                        	        	            }
                        	        	        	var html="<div class='cggl-3'>"
        		        								+"<div class='name'><span>"+times.slice(0,4)+"年"+times.slice(4)+"月"+gsmc+"应收账款前十大客户明细（单位：万元）</span></div>"
                                                        +"<p class='cancel1'>×</p>"
        		        								+"<div class='gys-box1'>"
        		            								+"<div class='t_r myTable'>"
        		            	    							+"<div class='t_r_t' id='t_r_t1'>"
        		            	        						+"<div class='t_r_title'>"	
        		            										+"<table>"
        		            										+"<thead>"
        				        										+"<tr>"
        				        											+"<th width='200px'>客户名称</th>"
        				        											+"<th class='sort-numeric' width='80px'>期末应收账款<br/>（原值）<span class='span1'>&nbsp;↑</span><span class='span2'>&nbsp;↓</span></th>"
        				        											+"<th class='sort-numeric' width='80px'>计提坏账<span class='span1'>&nbsp;↑</span><span class='span2'>&nbsp;↓</span></th>"
        				        											+"<th class='sort-numeric' width='80px'>期末应收账款<br/>（净值）<span class='span1'>&nbsp;↑</span><span class='span2'>&nbsp;↓</span></th>"
        				        											+"<th class='sort-numeric' width='80px'>本期回款<span class='span1'>&nbsp;↑</span><span class='span2'>&nbsp;↓</span></th>"
        				        											+"<th class='sort-numeric'>本期新增<span class='span1'>&nbsp;↑</span><span class='span2'>&nbsp;↓</span></th>"
        				        										+"</tr>"
        	        										+"</thead></table></div></div>"
        	                								+"<div class='t_r_content' id='t_r_content1'> "
        	                									+"<table><tbody>"
        			        									$.each(data.bukrsSecondDueto,function(k,v){
        			        										var s1=Math.round(v.dmbtr_sh);//净值
                	        	        							var s2=Math.round(v.badloan);//坏账
                	        	        							var s3=Math.round(v.dmbtr_h);//回款
                	        	        							var s4=Math.round(v.dmbtr_s);//新增
                	        	        							var s5=Math.round(v.dmbtr_sh_all);//原值
        			                	        	        		html+="<tr>"
        			        		    			        					+"<td width='200px'>"+v.customer+"</td>"
        			        		    			        					+"<td width='80px' style='text-align: right'>"+Check0(s1+s2,0)+"</td>"//原值
        			        		    			        					+"<td width='80px' style='text-align: right;'>"+Check0(s2,0)+"</td>"//坏账
        			        		    			        					+"<td width='80px' style='text-align: right;'>"+Check0(s1,0)+"</td>"//净值
        			        		    			        					+"<td width='80px' style='text-align: right;'>"+Check0(v.dmbtr_h,0)+"</td>"//回款
        			        		    			        					+"<td style='text-align: right'>"+Check0(v.dmbtr_s,0)+"</td>"//新增
        			        		    			        				+"</tr>";
        			                	        	        	});
        		        							  html+="</tbody></table></div>"
        		          							+"</div></div>"                        	        	        	
		        		        					$("body").append(html);
        		        							$(".cggl-3").css({"width":"750px","padding-bottom":"10px"});
        		        							//确定弹出框的位置
		        	        			    		var mousePoss = mouseCoords(event);
		        	        			    		var c_ll=p_x;
		        	        			    		var c_tt;
		        	        			    		if(mousePoss.y>380){
		        	        			    			c_tt=mousePoss.y-$(".cggl-3").height();
		        	        			    		}else{
		        	        			    			c_tt=mousePoss.y;
		        	        			    		}
		    		        			    		$(".cggl-3").css({"left":c_ll+"px","top":c_tt+"px"});
        		        							can2();
        		        							function can2(){
      		        							        $(".cancel1").on("click",function(){
      		        							            $(".cggl-3").remove();
      		        							            $("#Mask2").hide();
      		        							        });
      		        							    } 
        		        							//弹出框拖拽
      				                                var $v3=$(".cggl-3 .name");
      				                                var v3=$v3[0];
      				                                var $s3=$(".cggl-3");
      				                                var s3=$s3[0];
      				                                drag(v3,s3);
      				                                //排序
        		        							tabOrder();
                        	        	        },
                        	        	        error:function(){
                        	        	        	alert("error");
                        	        	        	$("#Mask2").hide();
                        	        	        }
            	        	        		});
            	        	        	});
                                        can1();
            	        	        	function can1(){
            	        	                $(".can1").on("click",function(){
            	        	                    $(".yszk-tab1e").remove();
            	        	                    $("#Mask").hide();
            	        	                })
            	        	            }
            	        	        },
            	        	        error :function(){
            	        	        	alert("error");
            	        	        	$("#Mask").hide();
            	        	        }
                        		});
                        	}
                        }
                    }                    
                },
                series: [{
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
                    type: 'column',
                    name: '应收账款',
                    color:"#c6c6c6",
                    borderWidth : '0',
                    data: zkArr,
                    tooltip: {
                        valueSuffix: '万元'
                    },
                    legend:{
                    	style:{
                    		width:font2,
                        	height:font2
                    	}
                    }
                },{
                    type: 'column',
                    name: '本期新增',
                    color:"#484848",
                    borderWidth : '0',
                    data: xzArr,
                    tooltip: {
                        valueSuffix: '万元'
                    }
                },{
                    type: 'line',
                    name: '本期回款',
                    color:"#c70019",
                    lineWidth: 1,
                    data: hkArr,
                    tooltip: {
                        valueSuffix: '万元'
                    },
                    marker: {
                        lineWidth: 1,
                        lineColor: "#c70019",
                        radius:2,
                        outerRadius:2,
                        fillColor: '#c70019'
                    }
                }]
            });
            $("#jsc-2 .highcharts-xaxis-labels text:last-child").css("font-weight","bolder");
            $("#jsc-2 .highcharts-xaxis-labels span:last-child").css("font-weight","bolder");
            
        	//设置图例为上下颜色各半
            var str="";
            $("#jsc-2 .highcharts-legend-item:eq(0) shape").css({
            	"behavior":"none",
            	"width":l_sh+"px",
            	"height":l_sh+"px",
            	"margin-top":l_sh/2+"px"
            });
            str="<div style='width:"+l_sh+"px;height:"+l_sh/2+"px;background-color:#c6c6c6;'></div>" +
            	"<div style='width:"+l_sh+"px;height:"+l_sh/2+"px;background-color:#484848;'></div>";
            $("#jsc-2 .highcharts-legend-item:eq(0) shape").append(str);
            if(client_w>1200&&client_w<=1500){
            	$("#jsc-2 .highcharts-legend-item span").css("margin-top","-1px");
            	$("#jsc-2 .highcharts-legend-item:eq(0) shape").css({
                	"behavior":"none",
                	"width":l_sh+"px",
                	"height":l_sh+"px",
                	"margin-top":"6px"
                });
            }else{
            	$("#jsc-2 .highcharts-legend-item span").css("margin-top","0px");
            }
        },
        error:function(){
        	alert("错误");
        }
	});
}



