var initChart = function(rel, contextPath){
	var addRel = function(selector){
		return selector + rel;
	};
	var getParam = function(){
		var startTime=$(addRel("#startTime")).val();
		var endTime=$(addRel("#endTime")).val();
		var timeUnit=$(addRel("#timeUnit")).val();
		var norm=$(addRel("#norm")).val();
		return {
			'startTime':getFDate(startTime,timeUnit),
			'endTime':getFDate(endTime,timeUnit),
			'timeUnit':timeUnit,
			'perColumn':norm
		};
	};
	$(addRel("#dPie")).click(function(){
		$.getJSON(contextPath+"/"+$(addRel("#dimension")).val()+"/percent",
				 getParam(),
				function(data){
					if(data != null && data.length >0){
						new iChart.Pie2D({
							render : addRel("#jbsxBox-2"),
							data: data,
							legend : {
								enable : true
							},
							showpercent:true,
							decimalsnum:2,
							width : 1300,
							height : 400,
							radius:110
						}).draw();
						$(addRel("#chartType")).val("pie");
					}
			});
	});
	
	$(addRel("#dBar")).click(function(){
		$.get(contextPath+"/"+$(addRel("#dimension")).val()+"/normStatc",
				 getParam(),
				function(data){
					if(data.length>0){
						new iChart.Bar2D({
								render : addRel("#jbsxBox-2"),
								data: data,
								align:'center',
								width : 1300,
								height : 300,
								coordinate:{
									width:'85%',
									height:'80%',
									scale:[{
										 position:'bottom',	
										 start_scale:0,
										 listeners:{
											parseText:function(t,x,y){
												return {text:t};
											}
										 }
									}]
								},
								shadow_color:'#8d8d8d',
								background_color : '#FEFEFE',
								rectangle:{
									listeners:{
										drawText:function(r,t){
											return t+"%";
										}
									}
								}
						}).draw();
						$(addRel("#chartType")).val("bar");
					}
				},'json');
	});
	var getRandomColor = function(){
		  return (function(m,s,c){
		    return (c ? arguments.callee(m,s,c-1) : '#') +
		      s[m.floor(m.random() * 16)]
		  })(Math,'0123456789abcdef',5)
		};
	$(addRel("#dLine")).click(function(){
		
		var dimension = $(addRel("#dimension")).val();
		var startTime = $(addRel("#startTime")).val();
		var endTime = $(addRel("#endTime")).val();
		var timeUnit = $(addRel("#timeUnit")).val();
		var norm = $(addRel("#norm")).val();//指标
		$.getJSON(contextPath + "/" + dimension + "/getDimDetail", {
			'startTime' : getFDate(startTime, timeUnit),
			'endTime' : getFDate(endTime, timeUnit),
			'timeUnit' : timeUnit,
			'normName' : norm
		}, function(data) {
			if(data==null || data.length==0 ){
				return;
			}
			//x轴标签日期
			var labels = getFlags(startTime, endTime, timeUnit);
	
			var dimTimes = [],dimNames = [], dataArray = [];
			$.each(data,function(key,value){
				var values = [];
				if (value != null && value.length > 0) {
					var dimTime = value[0].dimTime;
					var dimDate =dimTime;
					if(timeUnit==='WEEK'){
						dimDate = momentPlus.getWeekMondayDate(dimTime);
					}
					if (dimDate > startTime) {
						var zeroNum = getZeroNum(startTime, dimDate, timeUnit);
	
						for (var t = 0; t < zeroNum; t++) {
							values.push(0);
							dimTimes.push("");
						}
					}
	
					if (norm === "pv_cnt") {
						$(value).each(function(t) {
							values.push(value[t].pvCnt);
							dimTimes.push(value[t].dimTime);
						});
					} else if (norm === "session_cnt") {
						$(value).each(function(t) {
							values.push(value[t].sessionCnt);
							dimTimes.push(value[t].dimTime);
						});
					} else if (norm === "avg_pv") {
						$(value).each(function(t) {
							values.push(value[t].avgPv);
							dimTimes.push(value[t].dimTime);
						});
					} else if (norm === "avg_dura") {
						$(value).each(function(t) {
							values.push(value[t].avgDura);
							dimTimes.push(value[t].dimTime);
						});
					} else if (norm === "bounce_rate") {
						$(value).each(function(t) {
							values.push(value[t].bounceRate);
							dimTimes.push(value[t].dimTime);
						});
					} else if (norm === "user_cnt") {
						$(value).each(function(t) {
							values.push(value[t].userCnt);
							dimTimes.push(value[t].dimTime);
						});
					}
	
					dimTime = value[value.length - 1].dimTime;
					dimDate = dimTime;
					
					if(timeUnit==='WEEK'){
						dimDate = momentPlus.getWeekMondayDate(dimTime);
					}
					if (dimDate < endTime) {
						var zeroNum = getZeroNum(dimDate, endTime, timeUnit);
	
						for (var t = 0; t < zeroNum; t++) {
							values.push(0);
							dimTimes.push("");
						}
					}
					dimNames.push(key);
				} else {
					var zeroNum = getZeroNum(startTime, endTime, timeUnit);
					for (var t = 0; t < zeroNum; t++) {
						values.push(0);
						dimTimes.push("");
					}
				}
				dataArray.push({
					name : key,
					value : values,
					color : getRandomColor(),
					line_width : 2
				});
			});
			
			var ptSize = 8;
			if (dimTimes.length > 100) {
				ptSize = 1;
			} else if (dimTimes.length > 30) {
				ptSize = 5;
			}
			if(dataArray.length >0)
			new iChart.LineBasic2D({
				render : addRel("#jbsxBox-2"),
				data : dataArray,
				align : 'center',
				//title : '基础流量概览',
				subtitle : '',
				footnote : '数据来源：国双科技',
				width : 1300,
				height : 300,
				background_color : '#FEFEFE',
				crosshair : {
					enable : true,
					line_width : 2,
					line_color : '#2dc8ce'//十字线的颜色
				},
				sub_option : {
					intersection : false,
					label : false
				},
				/**
				 * d:相当于data[0],即是一个线段的对象
				 * v:相当于data[0].value
				 * x:计算出来的横坐标
				 * x:计算出来的纵坐标
				 * j:序号 从0开始
				 */
				parsePoint : function(d, v, x, y, j) {
					//利用序号进行过滤春节休息期间
					if (v == 0)
						//ignored为true表示忽略该点
						return {
							ignored : true
						};
				},
				tip : {
					enable : true,
					shadow : true,
					move_duration : 400,
					border : {
						enable : true,
						radius : 5,
						width : 2,
						color : '#3f8695'
					},
					listeners : {
						//tip:提示框对象、
						//name:数据名称
						//value:数据值
						//text:当前文本
						//i:数据点的索引
						parseText : function(tip, name, value, text, i) {
							return name + ":" + value + "次";
						}
					}
				},
				tipMocker : function(tips, i) {
					return "<div style='font-weight:600'>" + dimTimes[i]
							+ "</div>" + tips.join("<br/>");
				},
				legend : {
					enable : true,
					row : 1,//设置在一行上显示，与column配合使用
					column : 'max',
					valign : 'top',
					sign : 'bar',
					background_color : null,//设置透明背景
					offsetx : -80,//设置x轴偏移，满足位置需要
					border : true
				},
				crosshair : {
					enable : true,
					line_color : '#62bce9'//十字线的颜色
				},
				sub_option : {
					label : false,
					point_size : ptSize
				},
				coordinate : {
					width : '85%',
					height : '80%',
					axis : {
						color : '#dcdcdc',
						width : 1
					},
					grids : {
						vertical : {
							way : 'share_alike',
							value : 12
						}
					},
					scale : [ {
						position : 'left',
						label : {
							color : '#355759',
							font : '微软雅黑',
							fontweight : 600
						},
						start_scale : 0,
						scale_size : 2,
						scale_color : '#9f9f9f'
					}, {
						position : 'bottom',
						label : {
							color : '#355759',
							font : '微软雅黑',
							fontweight : 600
						},
						labels : labels
					} ]
				}
			}).draw();
			$(addRel("#chartType")).val("line");
	
		});
	});
	$(addRel("#norm")).change(function(){
		var chartType=$(addRel("#chartType")).val();
		if(chartType==='line'){
			$(addRel("#dLine")).click();
		}else if(chartType==='pie'){
			$(addRel("#dPie")).click();
		}else if(chartType==='bar'){
			$(addRel("#dBar")).click();
		}else{
			$(addRel("#jbsxBox-2")).html("");
		}
	});
	//切换维度
	$(addRel("#dimension")).change(function(){
		var dimension = $(addRel("#dimension")).val();
		var startTime = $(addRel("#startTime")).val();
		var endTime = $(addRel("#endTime")).val();
		var timeUnit = $(addRel("#timeUnit")).val();
		var url = contextPath + "/"+dimension+"/statc?"
				+"timeUnit="+timeUnit
				+"&startTime="+startTime
				+"&endTime="+endTime
				+"&dimension="+dimension
				+"&rel=" + rel;
		$(addRel("#aList")).attr("href",url);
		$(addRel("#aList")).click();
		$(addRel("#norm")).change();
	});
	
	return getParam();
};

//获取显示标签的时间坐标
function getZeroNum(startTime,endTime,timeUnit){
	return momentPlus.getDiff(startTime,endTime,timeUnit);
};
//获取显示标签的时间坐标
function getFlags(startTime,endTime,timeUnit){
	return momentPlus.getDatesList(startTime,endTime,timeUnit);
};

var getRandomColor = function(){
	  return (function(m,s,c){
	    return (c ? arguments.callee(m,s,c-1) : '#') +
	      s[m.floor(m.random() * 16)]
	  })(Math,'0123456789abcdef',5)
	};
//获取显示标签的时间坐标
function getFDate(time,timeUnit){
	var d=new Date(time);
// 	if(timeUnit == 'WEEK'){
// 		var yWeek =getISOYearWeek(d);
// 		time = d.getFullYear()+"-"+yWeek;
// 	}
	return time;
};