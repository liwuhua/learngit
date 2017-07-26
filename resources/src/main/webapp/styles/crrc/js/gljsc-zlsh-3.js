function zlgl_3(timess) {
	//产品故障统计
	//根据电脑屏幕控制图表字体大小
    var client_w= window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth;
    var font,font1,font2,font3,plo_p,t_y,c_mt;
	if(client_w<=1200){
		font="10px";
		font1="10px";
		font2="8px";
		font3="6px";
		plo_p=8;
		t_y=0;//title的y
		c_mt=-7;
	}else if(client_w>1200&&client_w<=1500){
		font="14px";
		font1="10px";
		font2="10px";
		font3="8px";
		plo_p=10;
		t_y=0;//title的y
		c_mt=-5;
	}else if(client_w>1500&&client_w<=1900){
		font="14px";
		font1="10px";
		font2="10px";
		font3="8px";
		plo_p=10;
		t_y=0;//title的y
		c_mt=-5;
	}else if(client_w>1900){
		font="14px";
		font1="14px";
		font2="14px";
		font3="14px";
		plo_p=12;
		t_y=8;//title的y
		c_mt=-5;
	}
	$.ajax({
		url: ctx + "/crrc/reportKPI2227/getProjecttypes",
        dataType: "json",
        type: "post",
        success: function (data) {
            if ('300' === data.statusCode) {
                alert(data.message);
                return true;
            }else if('301' === data.statusCode){
                return true;
            }
        	var html="";
        	$.each(data,function(k,v){
        		html+="<span>"+v.projetype+"</span>";
        	});
        	$("#zl-cpgz-tb2").html(html);
        	var i=$("#zl-cpgz-tb2 span:eq(0)").html();
        	$("#zl-cpgz-tb2 span").on("click",function(){
                i=$(this).html();
                $("#zl-cpgz-tb2 span").css({
                    "color":"#484848",
                    "border-bottom":"none",
                    "font-weight":"normal"
                });
                $(this).css({
                    "color":"#c70019",
                    "border-bottom":"2px solid #c70019",
                    "font-weight":"bold"
                });
                cpgz_tj(i);
            });
        	cpgz_tj(i);
        	
        },
        error:function(){
        	alert("error获取产品类型");
        }
	});
    //切换
    function cpgz_tj(type_val){
    	
    	var reportJson1={
            	productType:type_val,
        		dateYearMonth:timess
        	}
    	reportJson1 = JSON.stringify(reportJson1);
    		
            var text=timess.slice(0,4)+"年"+timess.slice(4)+'月份产品厂外故障统计';
            $("#zl-cpgz-text").html(text);
            $.ajax({
            	url: ctx + "/crrc/reportKPI2227/getFaultCount",
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
                	var xArr1=[],sArr1=[];
                    $.each(data[1],function(k,v){
                    	xArr1.push(v.cpxh);
                    	sArr1.push(v.count);
                    });
                    var xArr2=[],sArr2=[];
                    $.each(data[0],function(k,v){
                    	xArr2.push(v.gzlx);
                    	sArr2.push(v.count);
                    });
                    var sArr1_max=Math.max.apply(null,sArr1);
                    var sArr2_max=Math.max.apply(null,sArr2);
                    var s;
                    if(sArr1_max>=sArr2_max){
                    	s=sArr1_max+1;
                    }else{
                    	s=sArr2_max+1;
                    }
                    sArr1.unshift(s);
                    sArr2.unshift(s);
                    xArr1.unshift(" ");
                    xArr2.unshift(" ");
                    /*xArr1=[" ","sjidhd","sjidhd","sjidhd","hyasg","gysd","HUAGSH","hyatdsg","hush","huasb","sjidhd","sjidhd","gyegg","sjidhd","husabn","sjidhd","hsdybg","husdguushuy"];
                    sArr1=[1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18];*/
                    /*xArr1=[" ","sjidhguushuy"];
                    sArr1=[1,2];
                    xArr2=[" ","sjidhd","hushsb","husabn","hiahs","guahs"];
                    sArr2=[10,9,8,7,6,5];*/
                    if(client_w<1200){
                    	if(xArr1.length<=1){
                        	$('#zl-cpgz').find('#tldjs1').css("height","48px");
                        }else if(xArr1.length==2){
                        	$('#zl-cpgz').find('#tldjs1').css("height","51px");
                        }else if(xArr1.length==3){
                        	$('#zl-cpgz').find('#tldjs1').css("height","63px");
                        }else if(xArr1.length==4){
                        	$('#zl-cpgz').find('#tldjs1').css("height","81px");
                        }else if(xArr1.length==5){
                        	$('#zl-cpgz').find('#tldjs1').css("height","99px");
                        }else if(xArr1.length==6){
                        	$('#zl-cpgz').find('#tldjs1').css("height","118px")
                        }else if(xArr1.length==7){
                        	$('#zl-cpgz').find('#tldjs1').css("height","136px")
                        }else if(xArr1.length==8){
                        	$('#zl-cpgz').find('#tldjs1').css("height","155px")
                        }else if(xArr1.length==9){
                        	$('#zl-cpgz').find('#tldjs1').css("height","174px")
                        }else if(xArr1.length==10){
                        	$('#zl-cpgz').find('#tldjs1').css("height","192px")
                        }else if(xArr1.length==11){
                        	$('#zl-cpgz').find('#tldjs1').css("height","210px")
                        }else if(xArr1.length==12){
                        	$('#zl-cpgz').find('#tldjs1').css("height","228px")
                        }else if(xArr1.length==13){
                        	$('#zl-cpgz').find('#tldjs1').css("height","246px")
                        }else if(xArr1.length==14){
                        	$('#zl-cpgz').find('#tldjs1').css("height","264px")
                        }else if(xArr1.length==15){
                        	$('#zl-cpgz').find('#tldjs1').css("height","282px")
                        }else if(xArr1.length==16){
                        	$('#zl-cpgz').find('#tldjs1').css("height","302px")
                        }else if(xArr1.length==17){
                        	$('#zl-cpgz').find('#tldjs1').css("height","320px")
                        }else if(xArr1.length>=18){
                        	$('#zl-cpgz').find('#tldjs1').css("height","340px")
                        }
                	}else if(client_w>1200&&client_w<=1500){
                		if(xArr1.length<=1){
                        	$('#zl-cpgz').find('#tldjs1').css("height","54px");
                        }else if(xArr1.length==2){
                        	$('#zl-cpgz').find('#tldjs1').css("height","58px");
                        }else if(xArr1.length==3){
                        	$('#zl-cpgz').find('#tldjs1').css("height","70px");
                        }else if(xArr1.length==4){
                        	$('#zl-cpgz').find('#tldjs1').css("height","83px");
                        }else if(xArr1.length==5){
                        	$('#zl-cpgz').find('#tldjs1').css("height","101px");
                        }else if(xArr1.length==6){
                        	$('#zl-cpgz').find('#tldjs1').css("height","120px")
                        }else if(xArr1.length==7){
                        	$('#zl-cpgz').find('#tldjs1').css("height","139px")
                        }else if(xArr1.length==8){
                        	$('#zl-cpgz').find('#tldjs1').css("height","157px")
                        }else if(xArr1.length==9){
                        	$('#zl-cpgz').find('#tldjs1').css("height","176px")
                        }else if(xArr1.length==10){
                        	$('#zl-cpgz').find('#tldjs1').css("height","194px")
                        }else if(xArr1.length==11){
                        	$('#zl-cpgz').find('#tldjs1').css("height","212px")
                        }else if(xArr1.length==12){
                        	$('#zl-cpgz').find('#tldjs1').css("height","230px")
                        }else if(xArr1.length==13){
                        	$('#zl-cpgz').find('#tldjs1').css("height","248px")
                        }else if(xArr1.length==14){
                        	$('#zl-cpgz').find('#tldjs1').css("height","268px")
                        }else if(xArr1.length==15){
                        	$('#zl-cpgz').find('#tldjs1').css("height","286px")
                        }else if(xArr1.length==16){
                        	$('#zl-cpgz').find('#tldjs1').css("height","304px")
                        }else if(xArr1.length==17){
                        	$('#zl-cpgz').find('#tldjs1').css("height","322px")
                        }else if(xArr1.length>=18){
                        	$('#zl-cpgz').find('#tldjs1').css("height","340px")
                        }
                	}else if(client_w>1500&&client_w<=1900){
                		if(xArr1.length<=1){
                        	$('#zl-cpgz').find('#tldjs1').css("height","54px");
                        }else if(xArr1.length==2){
                        	$('#zl-cpgz').find('#tldjs1').css("height","57px");
                        }else if(xArr1.length==3){
                        	$('#zl-cpgz').find('#tldjs1').css("height","77px");
                        }else if(xArr1.length==4){
                        	$('#zl-cpgz').find('#tldjs1').css("height","100px");
                        }else if(xArr1.length==5){
                        	$('#zl-cpgz').find('#tldjs1').css("height","122px");
                        }else if(xArr1.length==6){
                        	$('#zl-cpgz').find('#tldjs1').css("height","145px")
                        }else if(xArr1.length==7){
                        	$('#zl-cpgz').find('#tldjs1').css("height","168px")
                        }else if(xArr1.length==8){
                        	$('#zl-cpgz').find('#tldjs1').css("height","190px")
                        }else if(xArr1.length==9){
                        	$('#zl-cpgz').find('#tldjs1').css("height","213px")
                        }else if(xArr1.length==10){
                        	$('#zl-cpgz').find('#tldjs1').css("height","236px")
                        }else if(xArr1.length==11){
                        	$('#zl-cpgz').find('#tldjs1').css("height","258px")
                        }else if(xArr1.length==12){
                        	$('#zl-cpgz').find('#tldjs1').css("height","280px")
                        }else if(xArr1.length==13){
                        	$('#zl-cpgz').find('#tldjs1').css("height","304px")
                        }else if(xArr1.length==14){
                        	$('#zl-cpgz').find('#tldjs1').css("height","326px")
                        }else if(xArr1.length==15){
                        	$('#zl-cpgz').find('#tldjs1').css("height","348px")
                        }else if(xArr1.length==16){
                        	$('#zl-cpgz').find('#tldjs1').css("height","370px")
                        }else if(xArr1.length==17){
                        	$('#zl-cpgz').find('#tldjs1').css("height","392px")
                        }else if(xArr1.length>=18){
                        	$('#zl-cpgz').find('#tldjs1').css("height","414px")
                        }
                	}else if(client_w>1900){
                		if(xArr1.length<=1){
                        	$('#zl-cpgz').find('#tldjs1').css("height","66px");
                        }else if(xArr1.length==2){
                        	$('#zl-cpgz').find('#tldjs1').css("height","65px");
                        }else if(xArr1.length==3){
                        	$('#zl-cpgz').find('#tldjs1').css("height","93px");
                        }else if(xArr1.length==4){
                        	$('#zl-cpgz').find('#tldjs1').css("height","120px");
                        }else if(xArr1.length==5){
                        	$('#zl-cpgz').find('#tldjs1').css("height","148px");
                        }else if(xArr1.length==6){
                        	$('#zl-cpgz').find('#tldjs1').css("height","175px")
                        }else if(xArr1.length==7){
                        	$('#zl-cpgz').find('#tldjs1').css("height","202px")
                        }else if(xArr1.length==8){
                        	$('#zl-cpgz').find('#tldjs1').css("height","230px")
                        }else if(xArr1.length==9){
                        	$('#zl-cpgz').find('#tldjs1').css("height","257px")
                        }else if(xArr1.length==10){
                        	$('#zl-cpgz').find('#tldjs1').css("height","284px")
                        }else if(xArr1.length==11){
                        	$('#zl-cpgz').find('#tldjs1').css("height","312px")
                        }else if(xArr1.length==12){
                        	$('#zl-cpgz').find('#tldjs1').css("height","340px")
                        }else if(xArr1.length==13){
                        	$('#zl-cpgz').find('#tldjs1').css("height","366px")
                        }else if(xArr1.length==14){
                        	$('#zl-cpgz').find('#tldjs1').css("height","394px")
                        }else if(xArr1.length==15){
                        	$('#zl-cpgz').find('#tldjs1').css("height","422px")
                        }else if(xArr1.length==16){
                        	$('#zl-cpgz').find('#tldjs1').css("height","450px")
                        }else if(xArr1.length==17){
                        	$('#zl-cpgz').find('#tldjs1').css("height","478px")
                        }else if(xArr1.length>=18){
                        	$('#zl-cpgz').find('#tldjs1').css("height","504px")
                        }
                	}
                    
            		//按型号
                    $('#zl-cpgz').find('#tldjs1').highcharts({
                        chart: {
                            type: 'bar',
                            marginTop:c_mt,
                            marginLeft:80
                        },
                        title: {
                            text: '按型号统计（起）',
                            align:'left',
                            y:t_y,
                            style:{
                                 fontSize:font2,
                             	color:"#484848"
                            }
                        },
                        xAxis: {
                            tickLength:0,//设置x轴的刻度线的长度
                            lineWidth:0,//设置刻度线的宽度
                            tickAmount:xArr1.length,
                            categories: xArr1,
                            /*title: {
                                text: "按型号统计（起）"
                            },*/
                            
                            labels:{
                            	align:"left",
                            	x:-70,
                            	style:{
                            		textAlign:"left",
                                    fontSize:font2,
                                    //textAnchor:"end",
                                	color:"#484848"
                               }
                            }
                        },
                        yAxis: {
                            min: 0,
                            title:"",
                            gridLineWidth:0,//去掉y轴的线
                            labels: {
                                overflow: 'justify',
                                enabled: false
                            }//去掉y轴的刻度值
                        },
                        plotOptions: {
                            bar: {
                                dataLabels: {
                                    enabled: true,
                                    style:{
                                    	fontSize:font2,
                                    	fontWeight:"normal",
                                    	color:"#484848"
                                    },
                                    formatter:function(){
                                    	if(this.point.index==0){
                                            return false;
                                        }else{
                                        	return this.point.y;
                                        }                                      
                                    }
                                },
                                pointWidth:plo_p
                            },
                            series: {
                                colorByPoint : true,
                                //pointPadding:0,
                                //groupPadding:-30,
                                colors : [ "#fff","#c70019","#535666","#535666","#535666","#535666","#535666","#535666","#535666","#535666","#535666","#535666","#535666","#535666","#535666","#535666","#535666","#535666","#535666" ],
                                cursor:"pointer",
                                events:{
                                	dblclick:function(event){
                                        $("#Mask2").show();
                                        //确定弹出框的位置
    		    			    		var mousePos = mouseCoords(event);
    		    			    		
                                		var t_x=this.data[event.point.x].category;
                                    	var reportJson2={
                                    			dateYearMonth:timess,
                                    			productType:type_val,
                                    			startTime:t_x
                                			 }
                                    	reportJson2 = JSON.stringify(reportJson2);
                                		 $.ajax({
                                			 url: ctx + "/crrc/reportKPI2227/getLayerByModel",
                                	            dataType: "json",
                                	            data:reportJson2,
                                	            contentType: "application/json;charset=utf-8",
                                	            type: "post",
                                	            success: function (data) {
                                                    if ('300' === data.statusCode || '301' === data.statusCode) {
                                                        alert(data.message);
                                                        $("#Mask2").hide();
                                                        return true;
                                                    }
                        	        	        	var html="<div class='cggl-3'>"
    	        		        								+"<div class='name'><span>"+timess.slice(0,4)+"年"+timess.slice(4)+"月"+t_x+"产品故障类型分布情况</span></div>"
                                                                +"<p class='cancel1' style='font-size:18px;'>×</p>"
    	        		        								+"<div class='gys-box1'>"
    	        		            								+"<div class='t_r myTable'>"
    	        		            	    							+"<div class='t_r_t' id='t_r_t1'>" 
    	        		            	        						+"<div class='t_r_title'>"	
    	        		            										+"<table>"
    	        		            										+"<thead>"
    	        				        										+"<tr>"
    	        				        											+"<th width='130px'>故障类型</th>"
    	        				        											+"<th class='sort-numeric'>故障数量<span class='span1'>&nbsp;↑</span><span class='span2'>&nbsp;↓</span></th>"
    	        				        										+"</tr>"
    	        	        										+"</thead></table></div></div>"
    	        	                								+"<div class='t_r_content' id='t_r_content1'> "
    	        	                									+"<table><tbody>"
    	        			        									$.each(data,function(k,v){
    	        			                	        	        		html+="<tr>"
    	        			        		    			        					+"<td width='130px'>"+v.gzlx+"</td>"
    	        			        		    			        					+"<td style='text-align:right;'>"+v.count+"</td>"
    	        			        		    			        				+"</tr>";
    	        			                	        	        	});
    	        		        							  html+="</tbody></table></div>"
    	        		          							+"</div></div>"
    	        		        					$("body").append(html);
        		        							//确定弹出框的位置
    		        							    var wt_l=mousePos.x - $(".cggl-3").width()+330;
    	        		    			    		var wt_b=mousePos.y - $(".cggl-3").height();
    	        		        					$(".cggl-3").css({"width":"280px","left":wt_l+"px","top":wt_b+"px"});
    	        		        					 //弹出框拖拽
    	        		                            var $v1=$(".cggl-3 .name");
    	        		                            var v1=$v1[0];
    	        		                            var $s1=$(".cggl-3");
    	        		                            var s1=$s1[0];
    	        		                            drag(v1,s1);
    	        		                            //排序
                                                    tabOrder();
                                                    $(".cancel1").on("click",function(){
                                                    	$(this).parents(".cggl-3").remove();
                                                    	$("#Mask2").hide();
                                                    });
                                	            },
                                	            error:function(){
                                	            	alert("")
                                	            }
                                		 });
                                	}
                                }
                                
                            }//设置每个柱状图的颜色
                        },
                        tooltip: {
                            shared: true,
                            useHTML: true, 
                            followPointer: true,
                            //tooltip中数字右对齐
                            /*headerFormat: '<small style="padding-left:8px;">{point.key}</small>月<br/><table>', 
                            pointFormat: '<tr><td><span style="display:inline-block;width:4px;height:4px;background-color:{series.color};vertical-align: middle;margin:-3px 3px 0px -3px;"></span>{series.name}: </td>' + '<td style="text-align: right"><b>{point.y} </b></td></tr>', 
                            footerFormat: '</table>',*/
                            formatter: function () {
                                var s = '';
                                var jine="";
                                if(this.points[0].point.index>0){
                                	s='<table>';
                                	$.each(this.points, function (k,v) {
                                    	if(this.point.index>0){
                                    		jine=this.y;
                                            s+='<tr><td> '+ this.x + '：</td>'
                                                  + '<td><b>'+jine+this.series.name+'</b></td></tr>';
                                                
                                    	}
                                                                 
                                    });
                                    s+='</table>';
                                    return s;
                                }else{
                                	return false;
                                }
                                
                                //return console.log(this.points);
                            },
                            style:{
                            	color: '#595758',
                                fontSize:font2,
                                fontWeight:"normal"
                        	},
                        	borderColor:"#d8d8d8"
                        },
                        credits: {
                            enabled: false
                        },
                        legend:{
                            enabled: false
                        },//去掉图例
                        series: [{
                        	type:"bar",
                            name: '起',
                            data: sArr1/*,
                            pointPlacement:-0.3//设置x轴列的间隔
    */                    }]
                    });
                    if(client_w<=1200){
                    	if(xArr2.length<=1){
                        	$('#zl-cpgz').find('#tldjs2').css("height","48px");
                        }else if(xArr2.length==2){
                        	$('#zl-cpgz').find('#tldjs2').css("height","51px");
                        }else if(xArr2.length==3){
                        	$('#zl-cpgz').find('#tldjs2').css("height","63px");
                        }else if(xArr2.length==4){
                        	$('#zl-cpgz').find('#tldjs2').css("height","81px");
                        }else if(xArr2.length==5){
                        	$('#zl-cpgz').find('#tldjs2').css("height","99px");
                        }else if(xArr2.length==6){
                        	$('#zl-cpgz').find('#tldjs2').css("height","118px")
                        }else if(xArr2.length==7){
                        	$('#zl-cpgz').find('#tldjs2').css("height","136px")
                        }else if(xArr2.length==8){
                        	$('#zl-cpgz').find('#tldjs2').css("height","155px")
                        }else if(xArr2.length==9){
                        	$('#zl-cpgz').find('#tldjs2').css("height","174px")
                        }else if(xArr2.length==10){
                        	$('#zl-cpgz').find('#tldjs2').css("height","192px")
                        }else if(xArr2.length==11){
                        	$('#zl-cpgz').find('#tldjs2').css("height","210px")
                        }else if(xArr2.length==12){
                        	$('#zl-cpgz').find('#tldjs2').css("height","228px")
                        }else if(xArr2.length==13){
                        	$('#zl-cpgz').find('#tldjs2').css("height","246px")
                        }else if(xArr2.length==14){
                        	$('#zl-cpgz').find('#tldjs2').css("height","264px")
                        }else if(xArr2.length==15){
                        	$('#zl-cpgz').find('#tldjs2').css("height","282px")
                        }else if(xArr2.length==16){
                        	$('#zl-cpgz').find('#tldjs2').css("height","302px")
                        }else if(xArr2.length==17){
                        	$('#zl-cpgz').find('#tldjs2').css("height","320px")
                        }else if(xArr2.length>=18){
                        	$('#zl-cpgz').find('#tldjs2').css("height","340px")
                        }
                	}else if(client_w>1200&&client_w<=1500){
                		if(xArr2.length<=1){
                        	$('#zl-cpgz').find('#tldjs2').css("height","54px");
                        }else if(xArr2.length==2){
                        	$('#zl-cpgz').find('#tldjs2').css("height","58px");
                        }else if(xArr2.length==3){
                        	$('#zl-cpgz').find('#tldjs2').css("height","70px");
                        }else if(xArr2.length==4){
                        	$('#zl-cpgz').find('#tldjs2').css("height","83px");
                        }else if(xArr2.length==5){
                        	$('#zl-cpgz').find('#tldjs2').css("height","101px");
                        }else if(xArr2.length==6){
                        	$('#zl-cpgz').find('#tldjs2').css("height","120px")
                        }else if(xArr2.length==7){
                        	$('#zl-cpgz').find('#tldjs2').css("height","139px")
                        }else if(xArr2.length==8){
                        	$('#zl-cpgz').find('#tldjs2').css("height","157px")
                        }else if(xArr2.length==9){
                        	$('#zl-cpgz').find('#tldjs2').css("height","176px")
                        }else if(xArr2.length==10){
                        	$('#zl-cpgz').find('#tldjs2').css("height","194px")
                        }else if(xArr2.length==11){
                        	$('#zl-cpgz').find('#tldjs2').css("height","212px")
                        }else if(xArr2.length==12){
                        	$('#zl-cpgz').find('#tldjs2').css("height","230px")
                        }else if(xArr2.length==13){
                        	$('#zl-cpgz').find('#tldjs2').css("height","248px")
                        }else if(xArr2.length==14){
                        	$('#zl-cpgz').find('#tldjs2').css("height","268px")
                        }else if(xArr2.length==15){
                        	$('#zl-cpgz').find('#tldjs2').css("height","286px")
                        }else if(xArr2.length==16){
                        	$('#zl-cpgz').find('#tldjs2').css("height","304px")
                        }else if(xArr2.length==17){
                        	$('#zl-cpgz').find('#tldjs2').css("height","322px")
                        }else if(xArr2.length>=18){
                        	$('#zl-cpgz').find('#tldjs2').css("height","340px")
                        }
                	}else if(client_w>1500&&client_w<=1900){
                		if(xArr2.length<=1){
                        	$('#zl-cpgz').find('#tldjs2').css("height","54px");
                        }else if(xArr2.length==2){
                        	$('#zl-cpgz').find('#tldjs2').css("height","57px");
                        }else if(xArr2.length==3){
                        	$('#zl-cpgz').find('#tldjs2').css("height","77px");
                        }else if(xArr2.length==4){
                        	$('#zl-cpgz').find('#tldjs2').css("height","100px");
                        }else if(xArr2.length==5){
                        	$('#zl-cpgz').find('#tldjs2').css("height","122px");
                        }else if(xArr2.length==6){
                        	$('#zl-cpgz').find('#tldjs2').css("height","145px")
                        }else if(xArr2.length==7){
                        	$('#zl-cpgz').find('#tldjs2').css("height","168px")
                        }else if(xArr2.length==8){
                        	$('#zl-cpgz').find('#tldjs2').css("height","190px")
                        }else if(xArr2.length==9){
                        	$('#zl-cpgz').find('#tldjs2').css("height","213px")
                        }else if(xArr2.length==10){
                        	$('#zl-cpgz').find('#tldjs2').css("height","236px")
                        }else if(xArr2.length==11){
                        	$('#zl-cpgz').find('#tldjs2').css("height","258px")
                        }else if(xArr2.length==12){
                        	$('#zl-cpgz').find('#tldjs2').css("height","280px")
                        }else if(xArr2.length==13){
                        	$('#zl-cpgz').find('#tldjs2').css("height","304px")
                        }else if(xArr2.length==14){
                        	$('#zl-cpgz').find('#tldjs2').css("height","326px")
                        }else if(xArr2.length==15){
                        	$('#zl-cpgz').find('#tldjs2').css("height","348px")
                        }else if(xArr2.length==16){
                        	$('#zl-cpgz').find('#tldjs2').css("height","370px")
                        }else if(xArr2.length==17){
                        	$('#zl-cpgz').find('#tldjs2').css("height","392px")
                        }else if(xArr2.length>=18){
                        	$('#zl-cpgz').find('#tldjs2').css("height","414px")
                        }
                	}else if(client_w>1900){
                		if(xArr2.length<=1){
                        	$('#zl-cpgz').find('#tldjs2').css("height","66px");
                        }else if(xArr2.length==2){
                        	$('#zl-cpgz').find('#tldjs2').css("height","65px");
                        }else if(xArr2.length==3){
                        	$('#zl-cpgz').find('#tldjs2').css("height","93px");
                        }else if(xArr2.length==4){
                        	$('#zl-cpgz').find('#tldjs2').css("height","120px");
                        }else if(xArr2.length==5){
                        	$('#zl-cpgz').find('#tldjs2').css("height","148px");
                        }else if(xArr2.length==6){
                        	$('#zl-cpgz').find('#tldjs2').css("height","175px")
                        }else if(xArr2.length==7){
                        	$('#zl-cpgz').find('#tldjs2').css("height","202px")
                        }else if(xArr2.length==8){
                        	$('#zl-cpgz').find('#tldjs2').css("height","230px")
                        }else if(xArr2.length==9){
                        	$('#zl-cpgz').find('#tldjs2').css("height","257px")
                        }else if(xArr2.length==10){
                        	$('#zl-cpgz').find('#tldjs2').css("height","284px")
                        }else if(xArr2.length==11){
                        	$('#zl-cpgz').find('#tldjs2').css("height","312px")
                        }else if(xArr2.length==12){
                        	$('#zl-cpgz').find('#tldjs2').css("height","340px")
                        }else if(xArr2.length==13){
                        	$('#zl-cpgz').find('#tldjs2').css("height","366px")
                        }else if(xArr2.length==14){
                        	$('#zl-cpgz').find('#tldjs2').css("height","394px")
                        }else if(xArr2.length==15){
                        	$('#zl-cpgz').find('#tldjs2').css("height","422px")
                        }else if(xArr2.length==16){
                        	$('#zl-cpgz').find('#tldjs2').css("height","450px")
                        }else if(xArr2.length==17){
                        	$('#zl-cpgz').find('#tldjs2').css("height","478px")
                        }else if(xArr2.length>=18){
                        	$('#zl-cpgz').find('#tldjs2').css("height","504px")
                        }
                	}
                    //按故障类型
                    $('#zl-cpgz').find('#tldjs2').highcharts({
                        chart: {
                            type: 'bar',
                            marginTop:c_mt,
                            marginLeft:80
                        },
                        title: {
                            text: '按故障类型统计（起）',
                            align:'left',
                            y:t_y,
                            style:{
                            	fontSize: font2,
                                color:"#484848"
                            }
                        },
                        xAxis: {
                            tickLength:0,//设置x轴的刻度线的长度
                            lineWidth:0,//设置刻度线的宽度
                            categories: xArr2,
                            tickAmount:xArr2.length,
                            labels:{
                            	align:"left",
                            	x:-70,
                                style:{
                                	fontSize:font2,
                                	color:"#484848"
                                }
                            }
                        },
                        yAxis: {
                            min: 0,
                            title:"",
                            gridLineWidth:0,//去掉y轴的线
                            labels: {
                                overflow: 'justify',
                                enabled: false//去掉y轴的刻度值
                            }
                        },
                        plotOptions: {
                            bar: {
                                dataLabels: {
                                	allowOverlap:true,//是否允许数据标签重叠。
                                    enabled: true,
                                    style:{
                                    	fontSize:font2,
                                    	fontWeight:"normal",
                                    	color:"#484848"
                                    },
                                    formatter:function(){
                                    	if(this.point.index==0){
                                            return false;
                                        }else{
                                        	return this.point.y;
                                        }                                      
                                    }
                                },
                                pointWidth:plo_p
                            },
                            series: {
                                colorByPoint : true,
                                colors : [ "#fff","#c70019","#535666","#535666","#535666","#535666","#535666","#535666","#535666","#535666","#535666","#535666","#535666","#535666","#535666","#535666","#535666","#535666","#535666" ],
                                cursor:"pointer",
                                events:{
                                	dblclick:function(event){
                                        $("#Mask2").show();
                                        //确定弹出框的位置
    		    			    		var mousePos = mouseCoords(event);
    		    			    		
                                		var t_x=this.data[event.point.x].category;
                                    	var reportJson2={
                                    			dateYearMonth:timess,
                                    			productType:type_val,
                                    			startTime:t_x
                                			 }
                                    	reportJson2 = JSON.stringify(reportJson2);
                                		 $.ajax({
                                			 url: ctx + "/crrc/reportKPI2227/getLayerByType",
                                	            dataType: "json",
                                	            data:reportJson2,
                                	            contentType: "application/json;charset=utf-8",
                                	            type: "post",
                                	            success: function (data) {
                                                    if ('300' === data.statusCode || '301' === data.statusCode) {
                                                        alert(data.message);
                                                        $("#Mask2").hide();
                                                        return true;
                                                    }
                        	        	        	var html="<div class='cggl-3'>"
    	        		        								+"<div class='name'><span>"+timess.slice(0,4)+"年"+timess.slice(4)+"月"+t_x+"故障类型产品分布情况</span></div>"
                                                                +"<p class='cancel1'>×</p>"
    	        		        								+"<div class='gys-box1'>"
    	        		            								+"<div class='t_r myTable'>"
    	        		            	    							+"<div class='t_r_t' id='t_r_t1'>" 
    	        		            	        						+"<div class='t_r_title'>"	
    	        		            										+"<table>"
    	        		            										+"<thead>"
    	        				        										+"<tr>"
    	        				        											+"<th width='130px'>产品型号</th>"
    	        				        											+"<th class='sort-numeric'>故障数量<span class='span1'>&nbsp;↑</span><span class='span2'>&nbsp;↓</span></th>"
    	        				        										+"</tr>"
    	        	        										+"</thead></table></div></div>"
    	        	                								+"<div class='t_r_content' id='t_r_content1'> "
    	        	                									+"<table><tbody>"
    	        			        									$.each(data,function(k,v){
    	        			                	        	        		html+="<tr>"
    	        			        		    			        					+"<td width='130px'>"+v.cpxh+"</td>"
    	        			        		    			        					+"<td style='text-align:right;'>"+v.count+"</td>"
    	        			        		    			        				+"</tr>";
    	        			                	        	        	});
    	        		        							  html+="</tbody></table></div>"
    	        		          							+"</div></div>"
    	        		        					$("body").append(html);
        		        							//确定弹出框的位置
        		        							var wt_l=mousePos.x - $(".cggl-3").width()+310;
      	        		    			    		var wt_b=mousePos.y - $(".cggl-3").height();
      	        		        					$(".cggl-3").css({"width":"300px","left":wt_l+"px","top":wt_b+"px"});
    	        		        					 //弹出框拖拽
    	        		                            var $v2=$(".cggl-3 .name");
    	        		                            var v2=$v2[0];
    	        		                            var $s2=$(".cggl-3");
    	        		                            var s2=$s2[0];
    	        		                            drag(v2,s2);
                                                    tabOrder();
                                                    $(".cancel1").on("click",function(){
                                                    	$(this).parents(".cggl-3").remove();
                                                    	$("#Mask2").hide();
                                                    });
                                	            },
                                	            error:function(){
                                	            	alert("")
                                	            }
                                		 });
                                	}
                                }
                            }//设置每个柱状图的颜色
                        },
                        tooltip: {
                            shared: true,
                            useHTML: true, 
                            followPointer: true,
                            //tooltip中数字右对齐
                            /*headerFormat: '<small style="padding-left:8px;">{point.key}</small>月<br/><table>', 
                            pointFormat: '<tr><td><span style="display:inline-block;width:4px;height:4px;background-color:{series.color};vertical-align: middle;margin:-3px 3px 0px -3px;"></span>{series.name}: </td>' + '<td style="text-align: right"><b>{point.y} </b></td></tr>', 
                            footerFormat: '</table>',*/
                            formatter: function () {
                                var s = '';
                                var jine="";
                                if(this.points[0].point.index>0){
                                	s='<table>';
                                	$.each(this.points, function (k,v) {
                                    	if(this.point.index>0){
                                    		jine=this.y;
                                            s+='<tr><td> '+ this.x + '：</td>'
                                                  + '<td><b>'+jine+this.series.name+'</b></td></tr>';
                                                
                                    	}
                                                                 
                                    });
                                    s+='</table>';
                                    return s;
                                }else{
                                	return false;
                                }
                                
                                //return console.log(this.points);
                            },
                            style:{
                            	color: '#595758',
                                fontSize:font2,
                                fontWeight:"normal"
                        	},
                        	borderColor:"#d8d8d8"
                        },
                        credits: {
                            enabled: false
                        },
                        legend:{
                            enabled: false
                        },//去掉图例
                        series: [{
                        	type:"bar",
                            name: '起',
                            data: sArr2/*,
                            pointPlacement:-0.3//设置x轴列的间隔
    */                    }]
                    });
                    $(".zlhshglmb-gz .highcharts-xaxis-labels span").css({
                    	"width":"80px",
                    	"display":"block",
                    	"white-space":"nowrap",
                    	"overflow":"hidden",
                    	"text-overflow":"ellipsis"                    	
                    });
                },
                error:function(){
                	alert("error");
                }
            });
    }
}
