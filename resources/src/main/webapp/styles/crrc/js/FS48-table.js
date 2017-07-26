/*//控制表格高度的显示
var heights = $(window).height();
$(".ch-kp-fortyEight").height((heights - 225) + "px");
//点击收起按钮
$(".retract").off("click").on("click", function () {
    if ($(".container form").css("display") == "block") {
        $(".container form").slideUp();
        $(this).text("展开  ∨");
        var heights = $(window).height();
        $(".ch-kp-fortyEight").animate({
            "height": (heights - 145) + "px"
        });
    } else {
        $(".container form").slideDown();
        $(this).text("收起  ∧");
        var heights = $(window).height();
        $(".ch-kp-fortyEight").height((heights - 225) + ("px"));
    }
})*/
//采购入库的表格
/*$("#submit").off("click").on("click", function () {
    $(".notSearch").empty();
    $("#show").text("数据正在加载中");
    var txt1 = $("#startTime").val().replace(/-/g, "");//年月
    var txt2 = $("#endTime").val().replace(/-/g, "");
    var reportJson={};

    if (txt1 == "" || txt2 == "") {
        alert("请输入日期");
    } else if (parseInt(txt1) > parseInt(txt2)) {
        alert("后一个日期必须大于或等于前一个日期");
    } else {*/
        $(".Mask").show();
        var item = 0;
        /*reportJson = JSON.stringify(reportJson);*/
        $.ajax({
            url: ctx + "/crrc/reportFS48/getStations",
            //data: reportJson,
            dataType: "json",
            //contentType: "application/json;charset=utf-8",
            type: "get",
            success: function (data) {
                if ('300' === data.statusCode || '301' === data.statusCode) {
                    alert(data.message);
                    $(".Mask").hide();
                    $(".ch-kp-fortyEight").html('');
                    return true;
                }
                if (jQuery.isEmptyObject(data)) {
                    $(".notSearch").next("div").hide();
                    $(".notSearch").text("未查询到数据");
                    return true;
                } else {
                    $(".notSearch").next("div").show();
                    var serveSite=data.stations;
                    var data = [{ name: '河南', value: 0},
                        { name: '山东', value: 0},
                        { name: '四川', value: 0},
                        { name: '广东', value: 0},
                        { name: '江苏', value: 0},
                        { name: '河北', value: 0},
                        { name: '湖南', value: 0 },
                        { name: '安徽', value: 0},
                        { name: '湖北', value: 0},
                        { name: '广西', value: 0},
                        { name: '浙江', value: 0 },
                        { name: '云南', value: 0},
                        { name: '江西', value: 0 },
                        { name: '辽宁', value: 0 },
                        { name: '贵州', value: 0 },
                        { name: '黑龙江', value: 0 },
                        { name: '陕西', value: 0},
                        { name: '福建', value: 0 },
                        { name: '山西', value: 0},
                        { name: '重庆', value: 0 },
                        { name: '吉林', value: 0 },
                        { name: '甘肃', value: 0 },
                        { name: '内蒙古', value: 0 },
                        { name: '新疆', value: 0 },
                        { name: '上海', value: 0 },
                        { name: '北京', value: 0 },
                        { name: '天津', value: 0 },
                        { name: '海南', value: 0 },
                        { name: '宁夏', value: 0 },
                        { name: '青海', value: 0 },
                        { name: '西藏', value: 0 },
                        { name: '台湾', value: 0 }
                    ];
                    var mapArray = Highcharts.maps['cn-with-city'];
                    var mappoint = Highcharts.geojson(mapArray, 'mappoint');
                    var map = null;
                    $('#container').highcharts('Map', {
                        credits: {
                            enabled: false
                        },
                        chart: {
                            spacing: 10//,
                            //height: 360,
                            //backgroundColor: '#F49952'
                        },
                        title: {
                            text: '',
                            floating: true
                        },
                        xAxis: {
                            minRange: 200
                        },
                        colorAxis: {
                            min: 0,
                            stops: [
                                [0, '#ebebeb'],
                                [0.5, "#ebebeb"],
                                [1, '#ebebeb']
                            ]
                        },
                        legend: {
                            enabled: false
                        },
                        tooltip: {
                            enabled:false
                        },
                        plotOptions: {
                            series: {
                                animation: {
                                    // duration: animDuration
                                }
                            },
                            datalabels:{
                            	allowOverlap:true//是否允许数据标签重叠。
                            }
                        },
                        series: [{
                            data: data,
                            mapData: mapArray,
                            joinBy: ['name', 'name'],
                            name: '服务站点',
                            showInLegend: false,
                            /*states: {
                             hover: {
                             color: '#eeeeee'
                             }
                             },*/
                            borderWidth: 1,
                            dataLabels: {
                                enabled: false,
                                format: '{point.name}',
                                style: {
                                    fontSize: '10px',
                                    fontWeight: 'normal',
                                    color: '#333'//,
                                    //textShadow: 'none'
                                }
                            }
                        }, {
                            type: 'mappoint',
                            name: 'Cities',
                            color: "#c70019",
                            marker: {
                                symbol: 'circle',
                                radius: 3
                            },
                            data: serveSite,
                            point:{
                                events:{
                                    click:function(event){
                                        var x=this.plotX;
                                        var y=this.plotY;
                                       
                                        //console.log(this);
                                        //console.log(this.stationid)
                                        var str="<div class='fwz_1' style='position:absolute;top:"+y+"px;left:"+x+"px;width:80px;height:50px;z-index:5;background:#ffffff;text-align:center;'>" +
                                        			"<p class='cpps' style='height:25px;line-height:25px;margin:0;cursor: pointer;'>产品配属</p>" +
                                        			"<p class='bppj' style='height:25px;line-height:25px;margin:0;line-height:16px;cursor: pointer;'>备品配件</p>" +
                                        		"</div>";
                                        $(".zhezhao1").show();
                                        $("#container").append(str);
                                        /*var s_time=$("#startTime").val().replace(/-/g,"");
                                        var e_time=$("#endTime").val().replace(/-/g,"");*/
                                        var s_id=this.stationid;//点击的服务站的id
                                        var s_name=this.name;
                                        
                                        $(".cpps").on("click",function(){
                                            $(".zhezhao2").show();
                                            $(".cpps").css("background-color","#eee");
                                            $(".bppj").css("background-color","#fff");
                                            var reportJson1 = {
                                            		/*startTime:s_time,
                                            		endTime:e_time,*/
                                            		ylzd:s_id,
                                            		startitem:0,
                                            		pageitem:20000,
                                            		productType:"1"
                                                }
                                            reportJson1 = JSON.stringify(reportJson1);
                                            $.ajax({
                                            	url: ctx + "/crrc/reportFS48/result",
                                                data: reportJson1,
                                                dataType: "json",
                                                contentType: "application/json;charset=utf-8",
                                                type: "post",
                                                success: function (data) {
                                                	if(data.productattach.length == 0){
                                                		alert(s_name+"产品配属情况为空");
                                                		//alert("产品配属情况为空");
                                                		$(".zhezhao2").hide();
                                                		return;
                                                	}
                                                	
                                                	if(data.productattach[0].psds == undefined || data.productattach[0].cx == undefined ){
                                                		alert(s_name+"产品配属情况为空");
                                                		//alert("产品配属情况为空");
                                                		$(".zhezhao2").hide();
                                                		return;
                                                	}
                                                	/*"+s_name+"产品配属情况     */                                           	
                                                	html="<div class='xiazuan-2' style='width:315px;padding-bottom:10px;background-color:#fff;position:absolute;top:"+y+"px;left:"+x+"px;z-index:30;'>"
                                                			+"<div class='drag' style='cursor:move;background-color:#ededed;padding:10px;margin-bottom:10px;'>"+s_name+"产品配属情况</div>"
                                                			+"<p><span class='fr dele' style='cursor:pointer;font-size:18px;margin-top:-40px;margin-right:18px;'>×</span></p>"
                                                			+"<div class='t_r myTable'>"
    		            	    								+"<div class='t_r_t' id='t_r_t'>"
    		            	    									+"<div class='t_r_title'>"	
    		            	    										+"<table>"
    		            	    											+"<thead>"
			    				        										+"<tr>"
			    				        											+"<th width='100px'>配属段/所</th>"
			    				        											+"<th width='80px'>配属车型</th>"
			    			            	        	        	                +"<th width='100px'>配属产品数量</th>"
			    				        										+"</tr>"
			    				        									+"</thead>"
			    				        								+"</table>"
			    				        							+"</div>"
			    				        						+"</div>"
    	                								    +"<div class='t_r_content' id='t_r_content' onscroll='aa()'> "
    	                										+"<table>"
    	                											+"<tbody>"
    	                											$.each(data.productattach,function(k,v){
    	                												html+="<tr>"
	    	                													 +"<td width='100px'>"+v.psds+"</td>"
	    	                													 +"<td width='80px'>"+v.cx+"</td>"
	    	                													 +"<td width='100px'>"+v.count+"</td>"
	    	                												 +"</tr>"
    	                											});
    	                									    html+="</tbody>"
    	                										+"</table>"
    	                									+"</div>"
                                                		+"</div>"
                                                	$("#container").append(html);
            									    if(x>800){
                                                    	x=x-$(".xiazuan-2").width();
                                                    	$(".xiazuan-2").css("left",x+"px");
                                                    }else if(x>400&&x<=800){
                                                    	x=x-$(".xiazuan-2").width()/2;
                                                    	$(".xiazuan-2").css("left",x+"px");
                                                    }
                                                    if(y>320&&y<420){
                                                    	y=y-$(".xiazuan-2").height()/2;
                                                    	$(".xiazuan-2").css("top",y+"px");
                                                    }else if(y>420){
                                                    	y=y-$(".xiazuan-2").height()+50;
                                                    	$(".xiazuan-2").css("top",y+"px");
                                                    }
    	                							 //tabOrder();
                                                	 $(".zhezhao2").on("click",function(){
                                                         $(this).hide();
                                                         $(".xiazuan-2").remove();
                                                     });
                                                	 dele(".dele",".xiazuan-2",".zhezhao2");
                                                     //弹出框拖拽
                                                     var $v1=$(".xiazuan-2 .drag");
                                                     var v1=$v1[0];
                                                     var $s1=$(".xiazuan-2");
                                                     var s1=$s1[0];
                                                     drag(v1,s1);
                                                },
                                                error:function(){
                                                	alert("error产品配属下钻!");
                                                }
                                            });
                                        })
                                        $(".bppj").on("click",function(){
                                            $(".zhezhao2").show();
                                            $(".bppj").css("background-color","#eee");
                                            $(".cpps").css("background-color","#fff");
                                            var reportJson2 = {
                                            		/*startTime:s_time,
                                            		endTime:e_time,*/
                                            		ylzd:s_id,
                                            		startitem:0,
                                            		pageitem:20000,
                                            		productType:"2"
                                                }
                                            reportJson2 = JSON.stringify(reportJson2);
                                            $.ajax({
                                            	url: ctx + "/crrc/reportFS48/result",
                                                data: reportJson2,
                                                dataType: "json",
                                                contentType: "application/json;charset=utf-8",
                                                type: "post",
                                                success: function (data) {
                                                	if(data.spareLists.length==0){
                                                		alert(s_name+"备品配件情况为空");
                                                		$(".zhezhao2").hide();
                                                		return;
                                                	}
                                                	//console.log(data);
                                                	var len=data.headers.length+1;
                                                	var len_w=len*60+220;
                                                	html="<div class='xiazuan-2' style='padding-bottom:10px;background-color:#fff;position:absolute;top:"+y+"px;left:"+x+"px;z-index:30;'>"
                                                			+"<div class='drag' style='cursor: move;background-color:#ededed;padding:10px;margin-bottom:10px;'>"+s_name+"备品配件情况</div>"/*"+s_name+"备品配件情况*/
                                                			+"<p><span class='fr dele' style='cursor:pointer;font-size:18px;margin-top:-40px;margin-right:18px;'>×</span></p>"
                                                			+"<div class='t_r myTable' style='min-width:300px;width:auto;max-width:800px;'>"
    		            	    								+"<div class='t_r_t' id='t_r_t'>"
    		            	    									+"<div class='t_r_title'>"	
    		            	    										+"<table>"
    		            	    											+"<thead>"
    		            	    												+"<tr>"
    		            	    													+"<th rowspan='2' style='width:140px;'>产品名称</th>"
    		            	    													+"<th rowspan='2' style='width:80px;'>产品型号</th>"
    		            	    													+"<th colspan='"+(data.headers.length+1)+"'>备品储备数量</th>"
    		            	    												+"</tr>"
    		            	    												+"<tr>"
	    		            	    											$.each(data.headers,function(k,v){
	    		            	    												html+="<th style='width:60px;'>"+v.locomotivedepot+"</th>"
	    		            	    											});
                                                								html+="<th style='width:60px;'>备品总计数量</th>"
                                                							html+="</tr>"
			    				        									+"</thead>"
			    				        								+"</table>"
			    				        							+"</div>"
			    				        						+"</div>"
    	                								    +"<div class='t_r_content' id='t_r_content' onscroll='aa()'> "
    	                										+"<table>"
    	                											+"<tbody>"
    	                											$.each(data.spareLists,function(k,v){
    	                												html+="<tr>"
    	                													$.each(v,function(key,val){
    	                														html+="<td style='width:60px;'>"+val+"</td>"
    	                													});
    	                												html+="</tr>"
    	                											});
    	                									    html+="</tbody>"
    	                										+"</table>"
    	                									+"</div>"
                                                		+"</div>"
                                                	+"</div>"
                                                	$("#container").append(html);
            									    for(var i=0;i<data.spareLists.length;i++){
            									    	$(".xiazuan-2 .t_r_content table tbody").find("tr").eq(i).find("td").eq(0).css("width","140px")
            									    	$(".xiazuan-2 .t_r_content table tbody").find("tr").eq(i).find("td").eq(1).css("width","80px")
            									    }
    	                							//tabOrder();
    	                							$(".xiazuan-2 .t_r_content").css({
    	                								"width":"auto",
    	                								"min-width":"300px",
    	                								"max-width":"800px",
    	                								"height":"300px"
    	                							});
    	                							if(x>800){
                                                    	x=x-$(".xiazuan-2").width();
                                                    	$(".xiazuan-2").css("left",x+"px");
                                                    }else if(x>400&&x<=800){
                                                    	x=x-$(".xiazuan-2").width()/2;
                                                    	$(".xiazuan-2").css("left",x+"px");
                                                    }
                                                    if(y>320&&y<420){
                                                    	y=y-$(".xiazuan-2").height()/2;
                                                    	$(".xiazuan-2").css("top",y+"px");
                                                    }else if(y>420){
                                                    	y=y-$(".xiazuan-2").height()+50;
                                                    	$(".xiazuan-2").css("top",y+"px");
                                                    }
    	                							//$(".xiazuan-2").css("width",w_xiazuan2);
    	                							$(".xiazuan-2 table").css("width",len_w+"px");
    	                							$(".xiazuan-2 .t_r_title").css("width",len_w+20+"px");
    	                							var t_c_w=$(".xiazuan-2 .t_r_content").height();
    	                							if(t_c_w>300){
    	                								$(".xiazuan-2 .t_r_content").css("height","300px");
    	                							}
    	                							//关闭弹出框
                                                	 $(".zhezhao2").on("click",function(){
                                                         $(this).hide();
                                                         $(".xiazuan-2").remove();
                                                     });
                                                     dele(".dele",".xiazuan-2",".zhezhao2");
                                                   //弹出框拖拽
                                                     var $v1=$(".xiazuan-2 .drag");
                                                     var v1=$v1[0];
                                                     var $s1=$(".xiazuan-2");
                                                     var s1=$s1[0];
                                                     drag(v1,s1);
                                                },
                                                error:function(){
                                                	alert("error产品配属下钻!");
                                                }
                                            });
                                        })
                                       
                                        $(".zhezhao1").on("click",function(){
                                            $(this).hide();
                                            $(".fwz_1").remove();
                                        })
                                    }
                                }
                            }
                        }]
                    }, function(m) {
                        map = m;
                    });

                }
                $(".Mask").hide();
                
            },
            error: function () {
                alert("数据请求失败!");
                $(".Mask").hide();
            }
        })

/*    }
})*/
