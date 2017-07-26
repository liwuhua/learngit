$.ajax({
	url: ctx + "/crrc/reportKPI2227/getDeadline",
    dataType: "json",
    type: "post",
    success:function(data){
    	$("#deadline5").html(data.deadline);
    },
    error:function(){
    	alert("error!");
    }
});
var timess=$(".data-time").val().replace(/-/g,"");
zlgl_1(timess);
function zlgl_1(timess){
	var reportJson={
		"dateYearMonth":timess
	 }
	reportJson = JSON.stringify(reportJson);
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
    var font,font1,font2,font3,font_l,sym_h,m_r,l_w,t_x,y_t_x,l_x,x_t_x,x_t_y;
	var st_y,s1_d_y,y2_y,l_y,l_i,p_c_p;
    if(client_w<=1200){
		font="10px";
		font1="10px";
		font2="8px";
		font3="6px";
		sym_h=8;
		m_r=60;
		l_w=275;
		t_x=-5;
		font_l="8px";
		y_t_x=-33;
		l_x=28;
		l_y=-6;
		x_t_x=187;
		x_t_y=-15;
		st_y=33;//副标题万元的位置
		s1_d_y=-17;//年份的位置
		y2_y=-8;//y轴右标题的y
		l_i=2;//图例之剑的距离
		p_c_p=10;
	}else if(client_w>1200&&client_w<=1500){
		$("#ssqs").css("width","37%");
		$(".zlwtfb").css("width","24%");
		$(".cpgztj").css("width","39%");
		font="12px";
		font1="12px";
		font2="10px";
		font3="8px";
		sym_h=8;
		m_r=60;
		l_w=320;
		t_x=0;
		font_l="8px";
		y_t_x=-28;
		l_x=65;
		l_y=-5;
		x_t_x=218;
		x_t_y=-23;
		st_y=37;//副标题万元的位置
		s1_d_y=-17;//年份的位置
		y2_y=-12;//y轴右标题的y
		l_i=8;//图例之剑的距离
		p_c_p=10;
	}else if(client_w>1500&&client_w<=1900){
		$("#ssqs").css("width","37%");
		if(Browser.ie === "8.0"){
			$(".cpgztj").css("width","39%");
		}else{
			$(".cpgztj").css("width","41%");
		}
		font="14px";
		font1="12px";
		font2="10px";
		font3="8px";
		sym_h=10;
		m_r=60;
		l_w=320;
		t_x=0;
		font_l="10px";
		y_t_x=-28;
		l_x=20;
		l_y=-6;
		x_t_x=245;
		x_t_y=-23;
		st_y=37;//副标题万元的位置
		s1_d_y=-17;//年份的位置
		y2_y=-12;//y轴右标题的y
		l_i=8;//图例之剑的距离
		p_c_p=12;
	}else if(client_w>1900){
		$("#ssqs").css("width","40%");
		$(".cpgztj").css("width","38%");
		/*if(Browser.ie === "8.0"){
			$(".cpgztj").css("width","36%");
		}else{
			
		}*/
		font="18px";
		font1="14px";
		font2="14px";
		font3="14px";
		sym_h=12;
		m_r=70;
		l_w=435;
		t_x=0;
		font_l="14px";
		y_t_x=-32;
		l_x=20;
		l_y=-6;
		x_t_x=346;
		x_t_y=-23;
		st_y=45;//副标题万元的位置
		s1_d_y=-20;//年份的位置
		y2_y=-12;//y轴右标题的y
		l_i=8;//图例之剑的距离
		p_c_p=14;
	}
	$.ajax({
		url: ctx + "/crrc/reportKPI2227/getPlantAccount",
        dataType: "json",
        data:reportJson,
        contentType: "application/json;charset=utf-8",
        type: "post",
        success: function (data) {
        	if ('300' === data.statusCode) {
                alert(data.message);
                return true;
            }else if('301' === data.statusCode){
                return true;
            }
        	var allArr=[],cwArr=[],allArr1=[],cwArr1=[],allArr2=[],cwArr2=[],lvArr=[],lvArr2=[],dataArrYear=[],dataArr=[],dataArrs=[],zuArr=[];
        	$.each(data[0],function(k,v){
        		allArr.push(Math.round(v.alltotal/100)/100);
        		cwArr.push(Math.round(v.outtotal/100)/100);
        		if(v.alltotal==0){
        			allArr1.push(null);
        			allArr2.push(null);
        		}else{
        			allArr1.push(Math.round(v.alltotal/100)/100);
            		allArr2.push(Math.round(v.alltotal/100)/100);
        		}
        		if(v.outtotal==0){
        			//条形图之间设置高度差别
        			cwArr1.push(null);
        			//去除数组中的null取最小值
        			cwArr2.push(null)
        		}else{
        			cwArr1.push(Math.round(v.outtotal/100)/100);
        			cwArr2.push(Math.round(v.outtotal/100)/100);
        		}
        		dataArr.push(v.ymonth.slice(4));
        		dataArrYear.push(v.ymonth.slice(0,4))
        		dataArrs.push(v.ymonth);
        	});
        	//判断当前月质量损失总额是否为零,若不为0,则加载最后一个不为0的月份
        	var index_s=0;
        	for(var i=0;i<allArr.length;i++){
        		if(allArr[i]!=0){
        			index_s=i;
        		}
        	}
        	var zlss_time=dataArrs[index_s];
        	zlsh_2(zlss_time);
        	zlgl_3(zlss_time);
        	//取金额最大值
        	var ymax1=allArr[0];
        	for(var i=0;i<allArr.length;i++){
        		if(ymax1<Number(allArr[i])){
        			ymax1=Number(allArr[i]);
        		}
        	}
        	//取每月损失金额和厂外损失金额的差值与y轴最大值的商比较
        	//var Arr_c=[]
        	for(var i=0;i<allArr1.length;i++){
        		//Arr_c.push(allArr1[i]-cwArr1[i]);
        		if((allArr1[i]-cwArr1[i])/ymax1<0.01){
        			if(allArr1[i]>cwArr1[i]){
        				allArr1[i]=allArr1[i]+ymax1*0.01;
        			}else if(allArr1[i]<cwArr1[i]){
        				cwArr1[i]=cwArr1[i]+ymax1*0.01
        			}
        		}
        	}
        	$.each(data[2],function(k,v){
        		var m=v.mate;
        		if(m==null){
        			lvArr.push(m);
        			lvArr2.push(m);
    			}else{
    				lvArr.push(Number(m));
    				lvArr2.push(Number(m));
    			}        		
        	});
        	//遍历数组allArr2,cwArr2,lvArr2去掉数组中的null
        	var sss=null;
        	for(var i=0;i<allArr2.length;i++){
                if(allArr2[i]==sss){
                	allArr2.splice(i, 1);
                	i=i-1;
                }
            }
        	for(var i=0;i<cwArr2.length;i++){
                if(cwArr2[i]==sss){
                	cwArr2.splice(i, 1);
                	i=i-1;
                }
            }
        	for(var i=0;i<lvArr2.length;i++){
                if(lvArr2[i]==sss){
                	lvArr2.splice(i, 1);
                	i=i-1;
                }
            }
        	//lvArr=[0,0,0,0,0,0,0,12,0,0,0,0,0]
        	//取损失率最大值
        	var ymax2=lvArr[0],lvsArr=[];
        	for(var i=0;i<lvArr.length;i++){
        		if(ymax2<Number(lvArr[i])){
        			ymax2=Number(lvArr[i]);
        		}
        	}
        	if(ymax2>=100000){
        		ymax2=Math.floor(ymax2/100000)*100000+100000;
            }else if(ymax2<100000&&ymax2>=10000){
            	ymax2=Math.floor(ymax2/10000)*10000+10000;
            }else if(ymax2<10000&&ymax2>=1000){
            	ymax2=Math.floor(ymax2/1000)*1000+1000;
            }else if(ymax2<1000&&ymax2>=100){
            	ymax2=Math.floor(ymax2/100)*100+100;
            }else if(ymax2<100&&ymax2>=10){
            	ymax2=Math.floor(ymax2/10)*10+10;
            }else if(ymax2<10){
            	ymax2=10;
            }
        	if(ymax1>=100000){
        		ymax1=Math.floor(ymax1/100000)*100000+100000;
            }else if(ymax1<100000&&ymax1>=10000){
            	ymax1=Math.floor(ymax1/10000)*10000+10000;
            }else if(ymax1<10000&&ymax1>=1000){
            	ymax1=Math.floor(ymax1/1000)*1000+1000;
            }else if(ymax1<1000&&ymax1>0){
            	ymax1=Math.floor(ymax1/100)*100+100;
            }else if(ymax1==0){
            	ymax1=10000;
            }
        	arrS(dataArrs,zuArr,timess,ymax1);
            //时间间隔的最大值即蓝色的
            var zuArrMax=Math.max.apply(null,zuArr);
            var zuArrMaxInd=zuArr.indexOf(zuArrMax);
            //质量损失总额标出最大最小值以及当前值
            var allArrMax=Math.max.apply(null,allArr2);
            var allArrMin=Math.min.apply(null,allArr2);
            var allArrMaxInd=allArr2.indexOf(allArrMax);
            var allArrMinInd=allArr2.indexOf(allArrMin);
            var allArr_this=allArr2[allArr2.length-1];
            var allArr_thisInd=allArr2.length-1;
            /*console.log(allArrMax+"---"+allArrMin);
            console.log(allArrMaxInd+"---"+allArrMinInd);*/
            //厂外损失金额标出最大最小值以及当前值
            var cwArrMax=Math.max.apply(null,cwArr2);
            var cwArrMin=Math.min.apply(null,cwArr2);
            var cwArrMaxInd=cwArr2.indexOf(cwArrMax);
            var cwArrMinInd=cwArr2.indexOf(cwArrMin);
            var cwArr_this=cwArr2[cwArr2.length-1];
            var cwArr_thisInd=cwArr2.length-1;
           /* console.log(cwArr2)
            console.log(cwArrMaxInd+"---"+cwArrMax)
            console.log(cwArrMinInd+"---"+cwArrMin)
            console.log(cwArr_thisInd+"---"+cwArr_this)*/
            //质量损失率标出最大最小值以及当前值
            var lvsArrMax=Math.max.apply(null,lvArr2);
            var lvsArrMin=Math.min.apply(null,lvArr2);
            var lvsArrMaxInd=lvArr2.indexOf(lvsArrMax);
            var lvsArrMinInd=lvArr2.indexOf(lvsArrMin);
            var lvsArr_this=lvArr2[lvArr2.length-1];
            var lvsArr_thisInd=lvArr2.length-1;
            var leiji=data[1].total/10000;
          
            $("#zlhshglmb-ssqs").highcharts({
            	chart: {
            		panning:false,//禁用放大
                	pinchType:'',//禁用手势操作
                    zoomType: '',
                    panKey:'shift',//
                    marginRight:m_r,
                    marginBottom:25
                },
                title: {
                    text: '质量损失趋势(年累计'+leiji.toFixed(2)+'万元)',
                    align:"left",
                    x:t_x,
                    style:{
                        fontSize:font,
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
                tooltip: {
                    shared: true,
                    useHTML: true, 
                    //tooltip中数字右对齐
                    /*headerFormat: '<small style="padding-left:8px;">{point.key}</small>月<br/><table>', 
                    pointFormat: '<tr><td><span style="display:inline-block;width:4px;height:4px;background-color:{series.color};vertical-align: middle;margin:-3px 3px 0px -3px;"></span>{series.name}: </td>' + '<td style="text-align: right"><b>{point.y} </b></td></tr>', 
                    footerFormat: '</table>',*/
                    formatter: function () {
                        var s = '<small style="padding-left:8px;">'+this.x+'</small>月<br/><table>';
                        var jine="";
                        $.each(this.points, function (k,v) {
                        	if(this.series.name=="质量损失总额(万元)"){
                        		 jine=allArr[this.point.index];
                        	}else if(this.series.name=="厂外损失金额(万元)"){
                        		jine=cwArr[this.point.index];
                        	}else if(this.series.name=="质量损失率(‱)"){
                        		jine=lvArr[this.point.index];
                        	}
                             if(typeof(jine)=="string"){
                            	 s+="";
                             }else{
                            	 jine=jine.toFixed(2);
                                 s+='<tr><td><span style="display:inline-block;width:4px;height:4px;background-color:'+v.color+';vertical-align: middle;margin:-3px 3px 0px -3px;"></span> '+ this.series.name + '</td>'
                                      + '<td style="text-align: right"><b>'+jine+this.series.tooltipOptions.valueSuffix+'</b></td></tr>';
                             }                            
                        });
                        s+='</table>';
                        return s;
                        //return console.log(this);
                    },
                    style:{
                    	color: '#595758',
                        fontSize:font1,
                        fontWeight:"normal"
                	},
                	borderColor:"#d8d8d8"
                },
                xAxis: {
                    categories: dataArr,
                    crosshair: true,
                    tickLength:3,
                    tickPixelInterval:1,
                    labels:{
                    	y:15,
                    	style:{
                    		fontSize:font2
                    	}
                    },
                    title:{
                        text:'月',
                        x:x_t_x,
                        y:x_t_y,
                        style:{
                        	fontSize:font2
                        }
                    }
                },
                exporting :{
                    enabled: false
                },
                legend: {
                	/*enabled: false,*/
                    layout: 'horizontal',
                    align: 'right',
                    itemMarginBottom:1.5,
                    width:l_w,
                    x: l_x,
                    verticalAlign: 'top',
                    y:l_y,
                    floating: true,
                    marginLeft:20,
                    //lineHeight: 14,
                    itemDistance:l_i,
                    itemStyle:{
                        fontSize : font_l,//设置图例文字的大小
                        fontWeight:"normal",
                        color:"#484848",
                        cursor:'default'
                    },
                    symbolHeight:sym_h,//设置图例符号的高
                    symbolWidth:sym_h//设置图例符号的宽
                },
                yAxis: [{ // Primary yAxis
                    min:0,
                    max:ymax1,
                    gridLineWidth:1,
                    minorTickWidth:0,
                    /*tickInterval:1000,
                    tickPixelInterval:1000,*/
                    tickPositions:[0,ymax1/5,ymax1*2/5,ymax1*3/5,ymax1*4/5,ymax1],
                    lineWidth:1,
                    labels: {
                        format: '{value:.Of}',
                        style: {
                            color: '#484848',
                            fontSize:font2
                        },
                    	x:-8
                    },
                    title: {
                        text: '',
                        /*align:"middle",*/
                        /*x:30,
                        y:-40,
                        rotation:0,//设置y轴辩题为横向
*/                        style: {
                            color:'#000',
                            fontSize:font2
                        }
                    }
                },
                 { // Secondary yAxis
                    min:0,
                    max:ymax2,
                    gridLineWidth:1,
                    minorTickWidth:0,
                    /*tickInterval:10,*/
                    tickPositions:[0,ymax2/5,ymax2*2/5,ymax2*3/5,ymax2*4/5,ymax2],
                    title: {
                        text: '‱',
                        align: 'high',//单位的位置                        
                        x:y_t_x,
                        y:y2_y,
                        rotation:0,//设置y轴辩题为横向
                       /* rotation: 0,*/
                        style: {
                            color: '#484848',
                            fontSize:font2
                        }
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
                plotOptions: {
                    column: {
                        grouping: false,//将图标叠加在一起
                        pointWidth:p_c_p,
                        dataLabels: {
                            enabled: true,//图形上是否显示数据
                            //allowOverlap:true,//是否允许数据标签重叠。
                            /*format:'{y:.0f}',*/
                            style:{
                            	//opacity:1,
                            	color:"#484848",
                                visibility:"visible",
                                fontFamily:"Times New Roman",
                            	fontSize:font2//图形上数据的字体大小
                            }/*,
                            formatter:function(){
                            	var s=allArr[this.point.index];
                            	s=s.toFixed(2);
                            	var s1=cwArr[this.point.index];
                            	s1=s1.toFixed(2);
                            	//不显示为零的值
                                if(this.point.y==0){
                                    return;
                                }
                                //显示损失总额的最大值最小值和当前值
                                if(this.point.index===allArrMaxInd && this.point.y===allArrMax){
                                    return s;
                                }else if(this.point.index===allArrMinInd && this.point.y===allArrMin){
                                    return s;
                                }else if(this.point.index===allArr_thisInd && this.point.y===allArr_this){
                                    return s;
                                }
                                //显示厂外损失的最大值最小值和当前值
                                if(this.point.index===cwArrMaxInd && cwArr[this.point.index]===cwArrMax){
                                    return s1;
                                }else if(this.point.index===cwArrMinInd && cwArr[this.point.index]===cwArrMin){
                                    return s1;
                                }else if(this.point.index===cwArr_thisInd && cwArr[this.point.index]===cwArr_this){
                                    return s1;
                                }
                                //显示年份3
                                if(this.point.y==ymax1){
                                	this.point.y=timess.slice(0,4)+"年";
                                	return this.point.y;
                                }
                            }*/

                        }
                    },
                    line:{
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
                            	var s2=Number(this.point.y);
                            	s2=s2.toFixed(2);
                            	if(this.point.y==0){
                                    return ;
                                }
                                if(this.point.index==lvsArrMaxInd && this.point.y==lvsArrMax){
                                    return s2;
                                }else if(this.point.index==lvsArrMinInd && this.point.y==lvsArrMin){
                                	return s2;
                                }else if(this.point.index==lvsArr_thisInd && this.point.y==lvsArr_this){
                                	return s2;
                                }
                            }
                        }
                    },
                    series:{
                    	cursor:"pointer",
                        events:{
                        	//控制图标的图例legend不允许切换
                            legendItemClick: function (event){   
                            	return false; //return  true 则表示允许切换
                            },
                        	dblclick:function(event){
                        		var indexs=this.data[event.point.x].index;
                        		var times=dataArrYear[indexs]+""+this.data[event.point.x].category;
                        		zlsh_2(times);
                        		zlgl_3(times);
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
                    yAxis: 0,
                    showInLegend:false,
                    //showLabels:  false,
                    pointWidth:2,
                    //showTooltip:false,
                    dataLabels:{
                    	y:s1_d_y,
                    	formatter:function(){
                            //显示年份3
                            if(this.point.y==ymax1){
                            	this.point.y=timess.slice(0,4)+"年";
                            	return this.point.y;
                            }
                        }
                    }/*,
                    tooltip: {
                    	pointFormatter: function() {
                            return null;
                        }
                    }*/
                },{
                    type: 'column',
                    name: '质量损失总额(万元)',
                    color:"#484848",
                    borderWidth : '0',
                    data: allArr1,
                    tooltip: {
                        valueSuffix: '万元'
                    },
                    dataLabels:{
                    	formatter:function(){
                        	var s=allArr[this.point.index];
                        	s=s.toFixed(2);
                            if(this.point.y==0){
                                return;
                            }
                            //显示损失总额的最大值最小值和当前值
                            if(this.point.index===allArrMaxInd /*&& this.point.y===allArrMax*/){
                                return s;
                            }else if(this.point.index===allArrMinInd /*&& this.point.y===allArrMin*/){
                                return s;
                            }else if(this.point.index===allArr_thisInd/* && this.point.y===allArr_this*/){
                                return s;
                            }
                        }
                    }
                },{
                    type: 'column',
                    name: '厂外损失金额(万元)',
                    color:"#c70019",
                    borderWidth : '0',
                    data: cwArr1,
                    tooltip: {
                        valueSuffix: '万元'
                    },
                    dataLabels:{
                    	formatter:function(){
                        	var s1=cwArr[this.point.index];
                        	s1=s1.toFixed(2);
                        	//不显示为零的值
                            if(this.point.y==0){
                                return;
                            }
                            //显示厂外损失的最大值最小值和当前值
                            if(this.point.index===cwArrMaxInd/* && cwArr[this.point.index]===cwArrMax*/){
                                return s1;
                            }else if(this.point.index===cwArrMinInd /*&& cwArr[this.point.index]===cwArrMin*/){
                                return s1;
                            }else if(this.point.index===cwArr_thisInd/* && cwArr[this.point.index]===cwArr_this*/){
                                return s1;
                            }
                        }
                    }
                },{
                    type: 'line',
                    name: '质量损失率(‱)',
                    color:"#908f8f",
                    lineWidth: 1,
                    data:lvArr,
                    yAxis: 1,
                    tooltip: {
                        valueSuffix: '‱'
                    },
                    marker: {
                        lineWidth: 1,
                        lineColor: "#908f8f",
                        radius:2,
                        outerRadius:2,
                        fillColor: '#908f8f'
                    }/*,
                    dataLabels:{
                    	y:-10
                    }*/
                }]
            });
            $("#zlhshglmb-ssqs .highcharts-xaxis-labels text:last-child").css("font-weight","bolder");
            $("#zlhshglmb-ssqs .highcharts-xaxis-labels span:last-child").css("font-weight","bolder");
            $("#zlhshglmb-ssqs .highcharts-legend-item:eq(0) shape").css({
            	"behavior":"none",
            	"width":sym_h+"px",
            	"height":sym_h+"px",
            	"margin-top":sym_h/2+"px"
            });
            str="<div style='width:"+sym_h+"px;height:"+sym_h/2+"px;background-color:#484848;'></div>" +
            	"<div style='width:"+sym_h+"px;height:"+sym_h/2+"px;background-color:#c70019;'></div>";
            $("#zlhshglmb-ssqs .highcharts-legend-item:eq(0) shape").append(str);
        },
        error:function(){
        	alert("error");
        }
	});
    
}
