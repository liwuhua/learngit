var timess=$(".data-time").val().replace(/-/g,"");
jygl_1(timess);
function jygl_1(timess) {
	 var reportJson={
		dateYearMonthStart:timess
	 }
	 reportJson = JSON.stringify(reportJson);
	 $.ajax({
		url: ctx + "/crrc/kpi0105/getYearAll",
        dataType: "json",
        data:reportJson,
        contentType: "application/json;charset=utf-8",
        type: "post",
        success: function (data) {
        	if ('300' === data.statusCode || '301' === data.statusCode) {
                alert(data.message);
                $("#jsc-1").html('');
                return true;
            }
        	$("#deadline1").html("当前数据维护到"+data.latestdate.slice(0,4)+"年"+data.latestdate.slice(4)+"月");
        	var yysr,jlr,yysr_s,jlr_s;
        	if(data.yearAll==null){
        		yysr=0;
        		jlr=0;
        		yysr_s=0;
        		jlr_s=0;
        	}else{
        		yysr=data.yearAll.incomeAll;
        		jlr=data.yearAll.profitAll;
        		yysr_s=data.yearAll.incomeAll;
        		jlr_s=data.yearAll.profitAll;
        	}
        	/*var yysr1=data.yearPlan.sellMax;
        	var jlr1=data.yearPlan.profitMax;*/
        	
        	var posi1=parseFloat(data.yearPlan.sellFirst);//营业收入第一目标值
        	var posi2=parseFloat(data.yearPlan.sellAdjust);
        	var posi3=parseFloat(data.yearPlan.sellMax);//营业收入第三目标值
        	var posi4=parseFloat(data.yearPlan.profitfirst);//净利润第一目标值
        	var posi5=parseFloat(data.yearPlan.profitAdjust);
        	var posi6=parseFloat(data.yearPlan.profitMax);//净利润第三目标值
        	/*posi3=Math.ceil(posi3);
        	posi6=Math.ceil(posi6);*/
        	$(".tuli p:first-child span").html(yysr.toFixed(2)+"亿元");
        	$(".tuli p:last-child span").html(jlr.toFixed(2)+"亿元");
        	if(posi3==0){
            	posi3=100;
            }
            if(posi6==0){
            	posi6=10;            	
            }
            //判断仪表盘是否爆表---将yysr_s和jlr_s放进serious中的data里,使指针不超出仪表盘
        	if(yysr_s>posi3){
        		yysr_s=posi3;
        	}
        	if(jlr_s>posi6){
        		jlr_s=posi6;
        	}
            var kedu_Arr2=[0,posi6/2,posi4,posi5,posi6],kedu_Arr1=[0,posi3/4,posi3/2,posi3*3/4,posi1,posi2,posi3];
        	var client_w= window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth;
        	var font,font1,font2,font3;
        	var t_x,y1_o,y1_l_d,s1_d_ra,s2_d_ra,y1_tl,y1_ml,y2_tl,y2_ml,c_mT,s_d_b,c_r;
        	if(client_w<1200){
        		font="10px";
        		font1="10px";
        		font2="8px";
        		font3="6px";
        		t_x=-5;
        		y1_o=22;//y1外边距
        		y1_l_d=12;//y1刻度值的位置
        		y1_p_i="160%";
        		y1_p_o="115%";
        		y2_o=0;//y2外边距
        		y2_l_d=-18;//y2刻度值的位置
        		y2_p_i="105%";
        		y2_p_o="70%";
        		s1_d_ra='130%';//指针长度
        		s2_d_ra='85%';
        		y1_tl=10;//刻度线的长度
        		y1_ml=7;
        		y2_tl=9;
        		y2_ml=5;
        		c_mT=45;
        		s_d_b=6;//指针粗的部分的宽度
        		c_r='4';
        	}else if(client_w>1200&&client_w<=1500){
        		font="12px";
        		font1="10px";
        		font2="10px";
        		font3="6px";
        		t_x=-5;
        		y1_o=22;//y1外边距
        		y1_l_d=10;//y1刻度值的位置
        		y1_p_i="140%";
        		y1_p_o="100%";
        		y2_o=-10;//y2外边距
        		y2_l_d=-25;//y2刻度值的位置
        		y2_p_i="90%";
        		y2_p_o="60%";
        		s1_d_ra='112%';//指针长度
        		s2_d_ra='70%';
        		y1_tl=15;//刻度线的长度
        		y1_ml=10;
        		y2_tl=11;
        		y2_ml=7;
        		c_mT=20;
        		s_d_b=8;
        		c_r='4';
        	}else if(client_w>1500&&client_w<=1900){
                font="14px";
                font1="12px";
                font2="10px";
                font3="8px";
                t_x=0;
                y1_o=22;//y1外边距
                y1_l_d=12;//y1刻度值的位置
                y1_p_i="140%";
                y1_p_o="100%";
                y2_o=-10;//y2外边距
                y2_l_d=-25;//y2刻度值的位置
                y2_p_i="90%";
                y2_p_o="60%";
                s1_d_ra='122%';//指针长度
                s2_d_ra='80%';
                y1_tl=15;//刻度线的长度
                y1_ml=10;
                y2_tl=11;
                y2_ml=7;
                c_mT=20;
                s_d_b=8;
                c_r='4';
            }else if(client_w>1900){
        		font="18px";
        		font1="14px";
        		font2="14px";
        		font3="14px";
        		t_x=0;
        		y1_o=22;//y1外边距
        		y1_l_d=20;//y1刻度值的位置
        		y1_p_i="140%";
        		y1_p_o="100%";
        		y2_o=-10;//y2外边距
        		y2_l_d=-30;//y2刻度值的位置
        		y2_p_i="90%";
        		y2_p_o="60%";
        		s1_d_ra='140%';//指针长度
        		s2_d_ra='80%';
        		y1_tl=15;//刻度线的长度
        		y1_ml=10;
        		y2_tl=11;
        		y2_ml=7;
        		c_mT=20;
        		s_d_b=9;
        		c_r='4';
        	}
            //营业收入与净利润
            $('#jsc-1').highcharts({
                chart: {
                    type: 'gauge',
                    alignTicks: false,
                    plotBackgroundColor: null,
                    plotBackgroundImage: null,
                    plotBorderWidth: 0,
                    plotShadow: false,
                    marginTop:c_mT
                },
                title: {
                    text: '营业收入与净利润',
                    align:"left",
                    x:t_x,
                    style:{
                    	fontSize: font,
                        color:"#484848"
                    }
                },
                pane: {
                    startAngle:-90,//起始位置
                    endAngle: 90,//结束位置
                    center: ['50%', '95%'],
                    background:null
                },
                tooltip:{
                	shared:false,
                	borderColor:"#d8d8d8",
                	style:{
                		color: '#595758',
                        fontSize:font1,
                        fontWeight:"normal"
                	}
                },
                /*legend: {
                    layout: 'horizontal',
                    align: 'left',
                    x: 200,
                    verticalAlign: 'top',
                    y: 20,
                    floating: true,
                    lineHeight: 14,
                    backgroundColor: '#FFFFFF',
                    style:{
                        fontSize : font2
                    },
                    itemStyle:{
                        
                    }
                },*/
                /* iteml :{
                    html : "",
                    style {
                    	left: '100px',
                    	top: '100px'
                	}
                  } */
                exporting :{
                    enabled: true
                },
                yAxis: [{
                    min: 0,//刻度最小值
                    max: posi3,//刻度最大值
                    lineColor: '#fff',
                    tickColor: '#fff',
                    minorTickColor: '#fff',
                    tickPosition: 'inside',
                    minorTickPosition: 'inside',
                    offset: y1_o,//外边距
                    lineWidth: 0,
                    labels: {
                        distance: y1_l_d,
                        align:"center",/*,//刻度值的位置
                        rotation: 'auto'*/
                        style:{
                        	fontSize:font2,
                        	color:"#484848"
                        }
                    },
                    tickPositions:kedu_Arr1,
                    tickInterval:posi3/8,
                    tickWidth:2,
                    /*step:100,*/
                    plotBands: [{//分区段
                        from: 0,
                        to: posi3,
                        color: "#c70019",
                        innerRadius: y1_p_i,
                        outerRadius: y1_p_o
                    }],
                    tickLength: y1_tl,
                    minorTickLength: y1_ml,
                    minorTickWidth: 1
                }, {
                    min: 0,
                    max: posi6,
                    tickPosition: 'inside',
                    lineColor: '#fff',
                    lineWidth: 0,
                    tickWidth:1,
                    minorTickPosition: 'inside',
                    tickColor: '#fff',
                    minorTickColor: '#fff',
                    minorTickLength:y2_ml,
                    tickLength: y2_tl,//刻度线长度
                    tickInterval:posi6/8,
                    offset:y2_o,
                    tickPositions:kedu_Arr2,
                    /*minorTickLength: 7,*/
                    labels: {
                        distance: y2_l_d,
                        align:"center",
                        /*rotation: 'auto'*/
                        style:{
                        	fontSize:font2,
                        	color:"#484848"
                        }
                    },
                    plotBands: [{//分区段
                        from: 0,
                        to: posi6,
                        color: "#929292",
                        innerRadius: y2_p_i,
                        outerRadius: y2_p_o
                    }]
                }],
                plotOptions: {
                    gauge: {
                        dataLabels: {
                            enabled: false
                        },
                        dial: {//仪表盘指针
                            backgroundColor: '#484848',
                            topWidth: 0,
                            yAxis: -2
                            /*,
                            fill:"#fff"*/
                        }
                    }
                },
                series: [{
                    name:"营业收入",
                    data:[yysr_s],
                    yAxis: 0,
                    dial:{
                       radius: s1_d_ra,
                       rearLength: '0%',
                       baseLength: '20%',
                       baseWidth: s_d_b,
                       borderColor: '#484848',
                       borderWidth: 0
                    },
	                tooltip: {
	                    valueSuffix: '亿元',	                    
	                    pointFormat: '<tr><td>{series.name}: </td>' +
	                    '<td style="text-align: right"><b>'+yysr.toFixed(2)+'亿元</b></td></tr><br/>',
	                	footerFormat: '<small>集团指标：</small><b>'+posi1+'亿元</b><br/>'+'<small>奋斗目标：</small><b>'+posi2+'亿元</b>'
	                }
                },{
                    name: '净利润',
                    data: [jlr_s],//指针所指的位置
                    yAxis: 1,
                    dial:{//仪表盘指针
                        radius: s2_d_ra,
                        rearLength: '0%',
                        baseLength: '10%',
                        baseWidth: s_d_b+1,
                        borderColor: '#fff',
                        borderWidth: 1
                    },
	                tooltip: {
	                    valueSuffix: '亿元',
	                    pointFormat: '<tr><td>{series.name}: </td>' +
	                    '<td style="text-align: right"><b>'+jlr.toFixed(2)+'亿元</b></td></tr><br/>',
	                    footerFormat: '<small>集团指标：</small><b>'+posi4+'亿元</b><br/>'+'<small>奋斗目标：</small><b>'+posi5+'亿元</b>'
	                }/*,
                    dataLabels: {
                        formatter: function () {
                            return '<span>' + "111" + '亿元</span><br/>' +
                                '<span>' + "111" + ' mph</span>';
                        }
                    }*//*,
                    tooltip: {
                        enable:false
                    }*/
                }]
            });
            //修改指针旋转部分的填充颜色
            var circle = $( "#jsc-1").find('circle');
            $(circle).attr("fill", '#fff' );
            $(circle).attr("stroke", '#484848' );
            $(circle).attr("r", c_r );
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
            //以下进行测试
            if (Browser.ie === "8.0"&&client_w<1200) {
            	$("#jsc-1 .highcharts-series shape:last-child").css("behavior","none");
            	$("#jsc_1 .seris").show();
            }else if(Browser.ie === "8.0"&&client_w>=1200&&client_w<=1900){
            	$("#jsc-1 .highcharts-series shape:last-child").css("behavior","none");
            	$("#jsc_1 .seris").show();
            }else if(Browser.ie === "8.0"&&client_w>1900){
            	$("#jsc-1 .highcharts-series shape:last-child").css("behavior","none");
            	$("#jsc_1 .seris").show();
            }
            $("#jsc-1 .highcharts-series-1").css("background-color","#fff");
            $("#jsc-1 .highcharts-series-2").css("background-color","#fff");
            //改变目标值的粗细(ie8)
            $(".highcharts-yaxis-labels:eq(1) span:eq(2)").css("font-weight","700");
            $(".highcharts-yaxis-labels:eq(0) span:eq(4)").css("font-weight","700");
            $(".highcharts-yaxis-labels:eq(0) span:eq(5)").css("font-weight","700");
            ////改变目标值的粗细
            $(".highcharts-yaxis-labels:eq(1) text:eq(2)").css("font-weight","700");
            $(".highcharts-yaxis-labels:eq(0) text:eq(4)").css("font-weight","700");
            $(".highcharts-yaxis-labels:eq(0) text:eq(5)").css("font-weight","700");
            $("#jsc-1").off("dblclick").on("dblclick",function(ev){
            	$("#Mask").show();
				$.ajax({
					url: ctx + "/crrc/kpi0105/getYearAllBukrs",
			        dataType: "json",
			        data:reportJson,
			        contentType: "application/json;charset=utf-8",
			        type: "post",
			        success:function(data){
			        	if ('300' === data.statusCode || '301' === data.statusCode) {
                            alert(data.message);
                            $("#Mask").hide();
                            return true;
                        }
			        	var html="<div class='yysryjlr'>"
								    +"<div class='name'><span>营业收入与净利润(单位:亿元)</span></div>"
                                    +"<p class='can'>×</p>"
                                    +"<div class='yysryjlr-table-1'>"
                                        +"<div class='t_r myTable'>"
                                            +"<div class='t_r_t' id='t_r_t2'>"
                                                +"<div class='t_r_title'>"
                                                    +"<table><thead>"
                                                        +"<tr>"
                                                            +"<th width='190px'>公司名称</th>"
                                                            +"<th class='sort-numeric' width='70px'>营业收入<span class='span1'>&nbsp;↑</span><span class='span2'>&nbsp;↓</span></th>"
                                                            +"<th class='sort-numeric'>净利润<span class='span1'>&nbsp;↑</span><span class='span2'>&nbsp;↓</span></th>"
                                                        +"</tr>"
                                                    +"</thead></table></div></div>"
                                    +"<div class='t_r_content' id='t_r_content2'> "
                                        +"<table><tbody>"
										$.each(data.yearAllBukrs,function(k,v){
						        			html+="<tr>"
						        					+"<td width='190px'>"+v.comName+"<input type='hidden' value='"+v.profitBukrs+"'></td>"
						        				if(v.incomeAll==0){
						        					html+="<td width='70px' style='text-align: right;'>"+v.incomeAll+"</td>";
						        				}else{
						        					html+="<td width='70px' style='text-align: right;'>"+formatNumber(v.incomeAll,2,1)+"</td>";
						        				}
					        					if(v.profitAll==0){
					        						html+="<td style='text-align: right;'>"+v.profitAll+"</td>"
					        					}else{
					        						html+="<td style='text-align: right;'>"+formatNumber(v.profitAll,2,1)+"</td>"
					        					}
						        			html+="</tr>"
						        		});
						        	html+="</tbody></table></div>"
			                            +"</div></div>";
			        	$("body").append(html);
			        	$(".yysryjlr").css({"width":"390px","padding-bottom":"10px"});
			        	//确定弹出框的位置
			    		var mousePos = mouseCoords(ev); 
			    		$(".yysryjlr").css({"left":mousePos.x+"px","top":mousePos.y+"px"});			        	
                        //can($(".yysryjlr .can"),$(".yysryjlr"),$("#Mask"));
			        	$(".yysryjlr .can").on("click",function(){
			        		$(".yysryjlr").remove();
			        		$("#Mask").hide();
			        	})
                        //弹出框拖拽
                        var $v1=$(".yysryjlr .name");
                        var v1=$v1[0];
                        var $s1=$(".yysryjlr");
                        var s1=$s1[0];
                        drag(v1,s1);
                        //排序
			        	tabOrder();

			        },
			        error:function(){
			        	alert("error营业收入与净利润一级下钻");
                        $("#Mask").hide();
			        }
				});
				
            });
        },
        error:function(){
        	//alert("error营业收入与净利润");
        }
	});
}
