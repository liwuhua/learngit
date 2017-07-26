function zlsh_2(timess) {
    //质量和售后管理目标---质量问题分布
	//根据电脑屏幕控制图表字体大小
    var client_w= window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth;
    var font,font1,font2,font3,s_s,s_y;
	if(client_w<=1200){
		font="10px";
		font1="10px";
		font2="8px";
		font3="6px";
		$("#zlhshglmb div .zlhshglmb-ss:eq(1)").css({
			"width":"223px",
			"height":"160px"
		});
		$("#zlhshglmb div .zlhshglmb-ss:eq(0)").css({
			"width":"223px",
			"height":"160px"
		});
		s_s=60;//series的size
		s_y=72;//副标题的y
	}else if(client_w>1200&&client_w<=1500){
		font="14px";
		font1="12px";
		font2="10px";
		font3="8px";
		$("#zlhshglmb div .zlhshglmb-ss:eq(1)").css({
			"width":"290px",
			"height":"160px"
		});
		$("#zlhshglmb div .zlhshglmb-ss:eq(0)").css({
			"width":"290px",
			"height":"160px"
		});
		s_s=78;//series的size
		s_y=78;//副标题的y
	}else if(client_w>1500&&client_w<=1900){
		font="14px";
		font1="12px";
		font2="10px";
		font3="10px";
		$("#zlhshglmb div .zlhshglmb-ss:eq(1)").css({
			"width":"339px",
			"height":"185px"
		});
		$("#zlhshglmb div .zlhshglmb-ss:eq(0)").css({
			"width":"339px",
			"height":"185px"
		});
		s_s=78;//series的size
		s_y=85;//副标题的y
	}else if(client_w>1900){
		font="18px";
		font1="14px";
		font2="14px";
		font3="14px";
		$("#zlhshglmb div .zlhshglmb-ss:eq(1)").css({
			"width":"410px",
			"height":"220px"
		});
		$("#zlhshglmb div .zlhshglmb-ss:eq(0)").css({
			"width":"410px",
			"height":"220px"
		});
		s_s=90;//series的size
		s_y=103;//副标题的y
	}
    //切换
    $("#zlhshglmb-tb1 span").on("click",function(){
        var i=$(this).index();
        $("#zlhshglmb-tb1 span").css({
            "color":"#000",
            "border-bottom":"none",
            "font-weight":"normal"
        });
        $(this).css({
            "color":"#c70019",
            "border-bottom":"2px solid #c70019",
            "font-weight":"bold"
        });
        $("#zlhshglmb div .zlhshglmb-ss").hide();
        $("#zlhshglmb div .zlhshglmb-ss:eq("+i+")").show();
    });
	var reportJson1={
			dateYearMonth:timess
	}
	reportJson1 = JSON.stringify(reportJson1);
	/*console.log(reportJson1);*/
	 //质量和售后管理目标---质量问题分布----厂外损失
    $.ajax({
    	url: ctx + "/crrc/reportKPI2227/getCauseByType",
        dataType: "json",
        data:reportJson1,
        contentType: "application/json;charset=utf-8",
        type: "post",
        success: function (data) {
        	if ('300' === data.statusCode) {
                alert(data.message);
                return true;
            }else if('301' === data.statusCode){
                return true;
            }
        	var Arr=[],ArrD=[],Arr1=[],ArrD1=[];
        	//厂内损失
        	$.each(data[0][1],function(k,v){
        		//去掉值为零的参数
        		if(v.account!=0){
        			Arr.push([v.reasoncodetxt,v.account]);//损失描述
            		ArrD.push(v.reasoncode);
        		}
        	});
        	//厂外损失
        	$.each(data[1][1],function(k,v){
        		//去掉值为零的参数
        		if(v.account!=0){
	        		Arr1.push([v.reasoncodetxt,v.account]);
	        		ArrD1.push(v.reasoncode);
        		}
        	});
        	var bw1=1,bw=1;
        	if(Arr.length==1){
        		bw1=0;
        	}
        	if(Arr1.length==1){
        		bw=0;
        	}
        	$('#zlhshglmb-cwss').highcharts({
                chart: {
                    plotBackgroundColor: null,
                    plotBorderWidth: null,
                    plotShadow: false,
                    marginBottom:40
                },
                title: {
                    text: '质量问题分布',
                    align:'left',
                    x:20,
                    style:{
                    	fontSize:font,
                    	color:"#484848"
                    }
                },
                subtitle:{
                	useHTML:true,
                	text:"<span>"+timess.slice(0,4)+"年"+timess.slice(4)+"月份厂外损失"+formatNumber(data[1][0][0].outtotal/10000,2,1)+"万元"+"</span><span style='margin-left:5px;'>"+"占"+data[1][0][1]+"</span>",
                	verticalAlign: 'middle',
                	y:s_y,
                	style:{
                		fontSize:font2,
                		fillOpacity:1,
                		fill:"",
                		color:"#484848"
                	}
                },
                tooltip: {
                	borderColor:"#d8d8d8",
                    useHTML: true,
                    //headerFormat: '<b>{series.name}</b><br><table>',
                    pointFormat: '{series.name}: <b>{point.percentage:.2f}%</b><br/><b>金额: </b><b>{point.y}元</b>',
                    //footerFormat: '</table>',
                    percentageDecimals:2,
                    style:{
                    	color: '#595758',
                        fontSize:font1,
                        fontWeight:"normal"
                    }
                },
                plotOptions: {
                    pie: {
                        allowPointSelect: true,
                        cursor: 'pointer',
                        dataLabels: {
                        	distance:10,//缩短拉出的线的长度
                            enabled: true,
                            format: '<b style="font-weight:normal;">{point.name}</b>: {point.percentage:.2f} %',
                            style:{
                            	fontSize:font3,
                            	color:"#484848",
                            	fontWeight:"normal",
                            	backgroundColor:"#c70019"
                            }
                        }
                    },
                    series:{
                    	cursor:"pointer",
                        events:{
                        	dblclick:function(event){
    								$("#Mask").show();
    								//确定弹出框的位置
		    			    		var mousePos = mouseCoords(event); 
		    			    		
                                    $(".wtfbss-3 .t_r_content tr").css("background","#ffffff");
                                    $(this).css("background","#ededed");
    								var reportJson2={
                        		    		dateYearMonth:timess,
                        		    		productType:ArrD1[event.point.index],
                        		    		ylzd:2
                        			 }
                        		     reportJson2 = JSON.stringify(reportJson2);
    								$.ajax({
                           			 url: ctx + "/crrc/reportKPI2227/getDutyunitByCause",
                           		        dataType: "json",
                           		        data:reportJson2,
                           		        contentType: "application/json;charset=utf-8",
                           		        type: "post",
                           		        success: function (data) {
                           		        	if ('300' === data.statusCode || '301' === data.statusCode) {
                           		                alert(data.message);
                           		                $("#Mask").hide();
                                                $(".wtfbss-4").hide();
                           		                return true;
                           		            }
                	        	        	var html1="<div class='wtfbss-4'>"
        		        								+"<div class='name'><span>"+timess.slice(0,4)+"年"+timess.slice(4)+"月厂外质量损失情况（单位：元）</span></div>"
                                                        +"<p class='can2' style='font-size:20px;margin-top:-40px;'>×</p>"
        		        								+"<div class='gys-box4'>"
        		            								+"<div class='t_r myTable tableSort'>"
        		            	    							+"<div class='t_r_t' id='t_r_t3'>"
        		            	        						+"<div class='t_r_title'>"	
        		            										+"<table><thead>"
        				        										+"<tr>"
        				        											+"<th width='200px' type='string'>责任单位</th>"
        				        											+"<th width='70px' type='string'>产品型号</th>"
        				        											+"<th class='sort-numeric'>金额<span class='span1'>&nbsp;↑</span><span class='span2'>&nbsp;↓</span></th>"
        				        										+"</tr>"
        				        										var heji=data[0][0].account;
                	        	        							html1+="<tr>"
	    				        											+"<th width='200px' class='table-heji'>合计</th>"
	    				        											+"<th width='70px'></th>"
	    				        											+"<th class='table-heji-jine'>"+Check0(heji,2)+"</th>"
	    				        										+"</tr>"
        	        										+"</thead></table></div></div>"
        	                								+"<div class='t_r_content' id='t_r_content3'> "
        	                									+"<table><tbody>"
        			        									$.each(data[0],function(k,v){
        			        										if(k>0){
        			        											html1+="<tr>"
	    			        		    			        					+"<td width='200px'>"+v.zrdw+"</td>"
	    			        		    			        					+"<td width='70px'>"+v.cpxh+"</td>"
	    			        		    			        					if(v.account==0){
	    			        		    			        						html1+="<td style='text-align: right;'>"+v.account+"</td>";
	    			        		    			        					}else{
	    			        		    			        						html1+="<td style='text-align: right;'>"+formatNumber(v.account,2,1)+"</td>";
	    			        		    			        					}
	        			                	        	        		html1+="</tr>";
        			        										}
        			                	        	        	});
        		        							  html1+="</tbody></table></div>"
        		          							+"</div></div>";
        		        					$("body").append(html1);
        		        					//确定弹出框的位置
        		    			    		var wt_l=mousePos.x;
        		    			    		var wt_b=mousePos.y-$(".wtfbss-4").height();
        		        					$(".wtfbss-4").css({"width":"470px","left":wt_l+"px","top":wt_b+"px"});
                                            can($(".can2"),$(".wtfbss-4"),$("#Mask"));
                                            //弹出框拖拽
        		                            var $v2=$(".wtfbss-4 .name");
        		                            var v2=$v2[0];
        		                            var $s2=$(".wtfbss-4");
        		                            var s2=$s2[0];
        		                            drag(v2,s2);
        		                            //排序
                                            tabOrder();
                                            tabletabs(2);
                           		        },
                        		        error:function(){
                        		        	alert("error质量问题分布-厂外损失一级下钻");
                        		        	$("#Mask").hide();
                        		        }
                           		    });
                        	}
                        }
                    }
                },
                series: [{
                    type: 'pie',
                    name: '占比',
                    data: Arr1,
                    size:s_s,
                    borderWidth:bw,//每部分白色的分隔线
                    //center:[120,20],
                    colors: ['#C70019','#3d3d38','#5f5e5e', '#9a9999', '#bababa', '#dbdbdb']
                }]
            });
        	//质量和售后管理目标---质量问题分布----厂内损失
        	$('#zlhshglmb-cnss').highcharts({
                chart: {
                    plotBackgroundColor: null,
                    plotBorderWidth: null,
                    plotShadow: false,
                    marginBottom:40
                },
                title: {
                    text: '质量问题分布',
                    align:'left',
                    x:20,
                    style:{
                        fontSize:font,
                    	color:"#484848"
                    }
                },
                subtitle:{
                	useHTML:true,
                	text:"<span>"+timess.slice(0,4)+"年"+timess.slice(4)+"月份厂内损失"+formatNumber(data[0][0][0].intotal/10000,2,1)+"万元"+"</span><span style='margin-left:5px;'>"+"占"+data[0][0][1]+"</span>",
                	verticalAlign: 'middle',
                	y:s_y,
                	style:{
                		fontSize:font2,
                    	color:"#484848"
                	}
                },
                tooltip: {
                	borderColor:"#d8d8d8",
                	pointFormat: '{series.name}: <b>{point.percentage:.2f}%</b><br/><b>金额: </b><b>{point.y}元</b>',
                    percentageDecimals:2,
                    style:{
                    	color: '#595758',
                        fontSize:font1,
                        fontWeight:"normal"
                    }
                },
                plotOptions: {
                    pie: {
                        allowPointSelect: true,
                        cursor: 'pointer',
                        dataLabels: {
                            distance:10,//缩短拉出的线的长度
                            enabled: true,
                            format: '<b style="font-weight:normal;">{point.name}</b>: {point.percentage:.2f} %',
                            style:{
                                fontSize:font3,
                                color:"#484848",
                                fontWeight:"normal"
                            }
                        }
                    },
                    series:{
                    	cursor:"pointer",
                        events:{
                        	dblclick:function(event){
                   		        	$("#Mask").show();
                   		        	//确定弹出框的位置
		    			    		var mousePos = mouseCoords(event);
		    			    		
                                    $(".wtfbss-1 .t_r_content tr").css("background","#ffffff");
                                    $(this).css("background","#ededed");
    								var reportJson2={
                        		    		dateYearMonth:timess,
                        		    		productType:ArrD[event.point.index],
                        		    		ylzd:1
                        			 }
                        		     reportJson2 = JSON.stringify(reportJson2);
    								$.ajax({
                           			 url: ctx + "/crrc/reportKPI2227/getDutyunitByCause",
                           		        dataType: "json",
                           		        data:reportJson2,
                           		        contentType: "application/json;charset=utf-8",
                           		        type: "post",
                           		        success: function (data) {
                           		        	if ('300' === data.statusCode || '301' === data.statusCode) {
                           		                alert(data.message);
                               		        	$("#Mask").hide();
                           		                return true;
                           		            }
                	        	        	var html1="<div class='wtfbss-2'>"
        		        								+"<div class='name'><span>"+timess.slice(0,4)+"年"+timess.slice(4)+"月厂内质量损失情况（单位：元）</span></div>"
                                                        +"<p class='can' style='font-size:20px;margin-top:-40px;'>×</p>"
        		        								+"<div class='gys-box2'>"
        		            								+"<div class='t_r myTable tableSort'>"
        		            	    							+"<div class='t_r_t' id='t_r_t2'>"
        		            	        						+"<div class='t_r_title'>"	
        		            										+"<table><thead>"
        				        										+"<tr>"
        				        											+"<th width='140px' type='string'>责任单位</th>"
        				        											+"<th width='70px' type='string'>产品型号</th>"
        				        											+"<th class='sort-numeric'>金额<span class='span1'>&nbsp;↑</span><span class='span2'>&nbsp;↓</span></th>"
        				        										+"</tr>"
        				        										var heji=data[0][0].account;
                	        	        						  html1+="<tr>"
	    				        											+"<th width='140px' class='table-heji'>合计</th>"
	    				        											+"<th width='70px'></th>"
	    				        											+"<th class='table-heji-jine'>"+Check0(heji,2)+"</th>"
	    				        										+"</tr>"
        	        										+"</thead></table></div></div>"
        	                								+"<div class='t_r_content' id='t_r_content2'> "
        	                									+"<table><tbody>"
        			        									$.each(data[0],function(k,v){
        			        										if(k>0){
        			        											html1+="<tr>"
    			        		    			        					+"<td width='140px'>"+v.zrdw+"</td>"
    			        		    			        					+"<td width='70px'>"+v.cpxh+"</td>"
    			        		    			        					if(v.account==0){
    			        		    			        						html1+="<td style='text-align: right;'>"+v.account+"</td>"
    			        		    			        					}else{
    			        		    			        						html1+="<td style='text-align: right;'>"+formatNumber(v.account,2,1)+"</td>"
    			        		    			        					}
    				                	        	        		    html1+"</tr>";
        			        										}
        			                	        	        	});
        		        							  html1+="</tbody></table></div>"
        		          							+"</div></div>";
        		        					$("body").append(html1);
        		        					//确定弹出框的位置
        		    			    		var wt_l=mousePos.x;
        		    			    		var wt_b=mousePos.y-$(".wtfbss-2").height();
        		        					$(".wtfbss-2").css({"left":wt_l+"px","top":wt_b+"px"});
        		        					can($(".can"),$(".wtfbss-2"),$("#Mask"));
        		        					//弹出框拖拽
        		                            var $v4=$(".wtfbss-2 .name");
        		                            var v4=$v4[0];
        		                            var $s4=$(".wtfbss-2");
        		                            var s4=$s4[0];
        		                            drag(v4,s4);
        		                            //排序
        		        					tabOrder();
        		        					tabletabs(2);
                           		        },
                        		        error:function(){
                        		        	alert("error质量问题分布-厂内损失一级下钻");
                        		        }
                           		    });
                        	}
                        }
                    }
                },
                series: [{
                    type: 'pie',
                    name: '占比',
                    data: Arr,
                    size:s_s,
                    borderWidth:bw1,//每部分白色的分隔线
                    //center:[120,20],
                    colors: ['#C70019','#3d3d38','#5f5e5e', '#9a9999', '#bababa', '#dbdbdb']
                }]
            });
        },
        error:function(){
        	alert("error质量问题分布");
        }
    });  

}
