var timess=$(".data-time").val().replace(/-/g,"");
scgl_2(timess);
function scgl_2(timess) {
	//var Year=timess.slice(0,4);
    var reportJson={
        yearmonth:timess
    	//yearmonth:"201701"	
    }
    $.ajax({
        url: ctx + "/crrc/kpi0609/getRatePie",
        dataType: "json",
        data:reportJson,
        /* contentType: "application/json;charset=utf-8",*/
        type: "post",
        success: function (data) {
        	if (  data.statusCode==="300") {
                alert(data.message);
                $("#shgl-xssrjwcqk").html('');
                return true;
            }else if('301' === data.statusCode){
                return true;
            }
            /*if (data.length==0) {
                alert("目标及完成没有值");
                $("#shgl-xssrjwcqk").html('');
                return true;
            }*/
            //根据电脑屏幕控制图表字体大小
            var client_w= window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth;
            var font1,font2,font3,s_c,s_c2,s_s;
            var t_y,st_y;
        	if(client_w>1000&&client_w<1200){
        		font1="10px";
        		font2="8px";
        		font3="6px";
        		s_c=0;
        		s_c2=-40;
        		s_s=34;
        		t_y=44;//标题的位置
        		st_y=56;//副标题的位置
        	}else if(client_w>1200&&client_w<=1500){
        		font1="10px";
        		font2="10px";
        		font3="8px";
        		s_c=10;
        		s_c2=-55;
        		s_s=34;
        		t_y=40;
        		st_y=52;
        	}else if(client_w>1500&&client_w<=1900){
        		font1="12px";
        		font2="10px";
        		font3="8px";
        		s_c=15;
        		s_c2=-55;
        		s_s=34;
        		t_y=44;
        		st_y=56;
        	}else if(client_w>1900){
        		font1="14px";
        		font2="14px";
        		font3="8px";
        		s_c=35;
        		s_c2=-50;
        		s_s=46;
        		t_y=60;
        		st_y=76;
        	}
            var ljwcArr=[];
            var xsmbArr=[];
            var ZTXTArr=[];
            var ZNUMArr=[];
            for(var i=1;i<data.length;i++){
            	var ljwc=Math.round(data[i].ZNUMTOTAL/10000);
            	var xsmb=Math.round(data[i].BUDGET);
            	var ZNUM=Math.round(data[i].ZNUM/10000);
            	ljwcArr.push(ljwc);
                xsmbArr.push(xsmb);
                ZTXTArr.push(data[i].ZTXT);
                ZNUMArr.push(ZNUM);
            }
            /*console.log(ljwcArr);
            /*var ljwcArr1=[],xsmbArr1=[];
            for(var i=0;i<ljwcArr.length;i++){
            	var ljwc=ljwcArr[i].replace(/,/g,'');
            	ljwcArr1.push(parseFloat(ljwc))
            }
            for(var i=0;i<xsmbArr.length;i++){
            	var xsmb=xsmbArr[i].replace(/,/g,'');
            	xsmbArr1.push(parseFloat(xsmb))
            }*/
            /*console.log(ljwcArr1)
            console.log(xsmbArr1)*/
            //ZNUMArr=[1,2,3,4,5,6,7];
            //ZTXTArr=["国铁市场","城轨市场","直驱电机","双馈电机","服务产业","海外市场","其他多元市场"];
            //ljwcArr=[136493,13498,200806,15909,8489,27651,63592];
            //xsmbArr=[270000,26000,455832,44168,15000,27300,83888];
            /**/var sy1=xsmbArr[0]-ljwcArr[0],ZNUM1=ZNUMArr[0];
            var ljwc2=ljwcArr[1],xsmb2=xsmbArr[1],sy2=xsmb2-ljwc2,ZTXT2=ZTXTArr[1],ZNUM2=ZNUMArr[1];
            var ljwc3=ljwcArr[2],xsmb3=xsmbArr[2],sy3=xsmb3-ljwc3,ZTXT3=ZTXTArr[2],ZNUM3=ZNUMArr[2];
            var ljwc4=ljwcArr[3],xsmb4=xsmbArr[3],sy4=xsmb4-ljwc4,ZTXT4=ZTXTArr[3],ZNUM4=ZNUMArr[3];
            var ljwc5=ljwcArr[4],xsmb5=xsmbArr[4],sy5=xsmb5+ljwc5,ZTXT5=ZTXTArr[4],ZNUM5=ZNUMArr[4];
            var ljwc6=ljwcArr[5],xsmb6=xsmbArr[5],sy6=xsmb6+ljwc6,ZTXT6=ZTXTArr[5],ZNUM6=ZNUMArr[5];
            var ljwc7=ljwcArr[6],xsmb7=xsmbArr[6],sy7=xsmb7+ljwc7,ZTXT7=ZTXTArr[6],ZNUM7=ZNUMArr[6];
           /* ljwcArr=[0,1,5,0,5,0,0];
            xsmbArr=[0,5,5,0,2,0,5];*/
            hchart($("#pie0"),0);
            //zqTable($("#pie0"),0);
            hchart($("#pie1"),1);
            //zqTable($("#pie1"),1);
            hchart($("#pie2"),2);
            //zqTable($("#pie2"),2);
            hchart($("#pie3"),3);
            //zqTable($("#pie3"),3);
            hchart($("#pie4"),4);
            //zqTable($("#pie4"),4);
            hchart($("#pie5"),5);
            //zqTable($("#pie5"),5);
            hchart($("#pie6"),6);
            //zqTable($("#pie6"),6);
          
            function hchart(ele,i){
            	if(ljwcArr[i]==0 && xsmbArr[i]==0){
            		$("#pie-"+i).show();
            		ele.highcharts({
                        chart: {
                            plotBackgroundColor: null,
                            plotBorderWidth: null,
                            plotShadow: false,
                            marginBottom:10,
                            marginLeft:16
                        },
                        title: {
                            text: ''+ZTXTArr[i]+'',
                            x:0,
                            y:t_y,
                            style:{
                                fontSize:font2,
                            	color:"#484848"
                            }
                        },
                        subtitle:{
                        	useHTML:true,
                            text: '(<b style="color:#c70019;">'+formatNumber(ljwcArr[i],0,1)+'</b>/'+formatNumber(xsmbArr[i],0,1)+')',
                            x:0,
                            y:st_y,
                            style:{
                                fontSize:font2,
                            	color:"#484848"
                            }
                        },
                        tooltip: {
                            enabled:false
                        },
                        legend:{
                            verticalAlign:"top",
                            align:"right",
                            shared:true,
                            showInLegend: true,
                            dataLabels:{
                                enabled:false
                            }
                        },
                        plotOptions: {
                            pie: {
                                allowPointSelect: false,
                                cursor: 'default', 
                                events: { 
                                	mouseOver: function () { 
                                		$report.html('Moused over').css('color', 'green'); 
                                	}, 
                                	mouseOut: function () { 
                                		$report.html('Moused out').css('color', 'red'); 
                                	} 
                                }, 
                                /*cursor: 'none',*/
                                /*enableMouseTracking:false,
                                animation:false,*/
                                dataLabels: {
                                    enabled: false
                                },
                                showInLegend: false
                                /* shared:true*/
                            }
                        },
                        series: [{
                            type: 'pie',
                            name: '占比',
                            center:[s_c,s_c2],//饼图偏移
                            size:s_s,//饼图大小
                            //borderWidth:bw,
                            /*showInLegend: true,*///显示图例
                            data: [
                                ['累积完成（万元）',   1]
                            ],
                            colors:['#fff'],
                            borderWidth:0
                        }]
                    });
            	}else{
            		var xh=xsmbArr[i]-ljwcArr[i];
                	var bw=1;//饼图的边框
                	//如果目标值小于实际值,去掉边框
                	if(xh<=0){ 
                		xh=0;
                		bw=0;
                	}
                	//如果实际值为零,去掉边框
                	if(ljwcArr[i]==0){
                		bw=0;
                	}
                    ele.highcharts({
                        chart: {
                            plotBackgroundColor: null,
                            plotBorderWidth: null,
                            plotShadow: false,
                            marginBottom:10,
                            marginLeft:16
                        },
                        colors:["#c70019","#908f8f"],
                        title: {
                            text: ''+ZTXTArr[i]+'',
                            x:0,
                            y:t_y,
                            style:{
                                fontSize:font2,
                            	color:"#484848"
                            }
                        },
                        subtitle:{
                        	useHTML:true,
                            text: '(<b style="color:#c70019;">'+formatNumber(ljwcArr[i],0,1)+'</b>/'+formatNumber(xsmbArr[i],0,1)+')',
                            x:0,
                            y:st_y,
                            style:{
                                fontSize:font2,
                            	color:"#484848"
                            }
                        },
                        tooltip: {
                            enabled:false
                        },
                        legend:{
                            verticalAlign:"top",
                            align:"right",
                            shared:true,
                            showInLegend: true,
                            dataLabels:{
                                enabled:false
                            }
                        },
                        plotOptions: {
                            pie: {
                                allowPointSelect: false,
                                cursor: 'default', 
                                events: { 
                                	mouseOver: function () { 
                                		return false;
                                		//$report.html('Moused over').css('color', 'green'); 
                                	}, 
                                	mouseOut: function () { 
                                		return false;
                                		//$report.html('Moused out').css('color', 'red'); 
                                	} 
                                }, 
                                dataLabels: {
                                    enabled: false
                                },
                                showInLegend: false
                                /* shared:true*/
                            }
                        },
                        series: [{
                            type: 'pie',
                            name: '占比',
                            center:[s_c,s_c2],//饼图偏移
                            size:s_s,//饼图大小
                            borderWidth:bw,
                            /*showInLegend: true,*///显示图例
                            data: [
                                ['累积完成（万元）',   ljwcArr[i]],
                                ['销售目标（万元）',   xh]
                            ]
                        }]
                    });
            	}
            	}
            },
            /*function zqTable(ele,i){
                ele.on("dblclick",function(){
                	$("#Mask").show();
                    var reportJson1 = {
                        //year: Year,
                        yearmonth:timess,
                        znum:''+ZNUMArr[i]+''
                    }
                    $.ajax({
                        url: ctx + "/crrc/kpi0609/getBussiApart",
                        dataType: "json",
                        data: reportJson1,
                        type: "post",
                        success: function (data) {
                            if ('300' === data.statusCode || '301' === data.statusCode) {
                                alert(data.message);
                                $("#Mask").hide();
                                $(".scBox").html('');
                                return true;
                            }
                            $(".scBox").show();
                            var str='';
                            var html='';
                            var VKBURArr=[];
                            $.each(data,function(k,v){
                                VKBURArr.push(v.VKBUR);
                                str+="<li>"+ v.VKBURTMD+"</li>";
                                html+="<tr>"
                                    +"<td width='230px'>合计</td>"
                                    +"<td width='100px'>"+ v.totalAccout+"</td>"
                                    +"<td>"+ v.rate+"</td>"
                                    +"</tr>"
                            });
                            $(".tabBox ul").html(str);
                            $(".tabBox ul li").eq(0).css("color","#c70019");
                            $("#t_r_b table").html(html);
                            $("#t_r_b table tr:not(:first)").hide();
                            can2($(".scBox .can"), $(".scBox"), $("#Mask"));
                            var reportJson2 = {
                                //year: Year,
                                yearmonth:timess,
                                znum:''+ZNUMArr[i]+'',
                                vkbur:''+VKBURArr[i]+''
                            }
                            $.ajax({
                                url: ctx + "/crrc/kpi0609/getCustByApart",
                                dataType: "json",
                                data: reportJson2,
                                type: "post",
                                success: function (data) {
                                    if ('300' === data.statusCode || '301' === data.statusCode) {
                                        alert(data.message);
                                        $("#Mask").hide();
                                        $(".tableBox").html('');
                                        return true;
                                    }
                                    var str1='';
                                    str1 += "<div class='t_r myTable'>"
                                        + "<div class='t_r_t' id='t_r_t1'>"
                                        + "<div class='t_r_title'>"
                                        + "<table><thead>"
                                        + "<tr>"
                                        + "<th width='230px'>客户</th>"
                                        + "<th class='sort-numeric' width='100px'>金额<span class='span1'>&nbsp;↑&nbsp;</span><span class='span2'>&nbsp;↓&nbsp;</span></th>"
                                        + "<th class='sort-numeric'>占比<span class='span1'>&nbsp;↑&nbsp;</span><span class='span2'>&nbsp;↓&nbsp;</span></th>"
                                        + "</tr>"
                                        + "</thead></table></div></div>"
                                        + "<div class='t_r_content' id='t_r_content1'> "
                                        + "<table><tbody>"
                                    $.each(data,function(k,v) {
                                        str1+="<tr>"
                                            + "<td width='230px'>" + v.KUNRGTMD + "</td>"
                                            + "<td width='100px'>" + v.ZNUMTOTAL + "</td>"
                                            + "<td>" + v.ZNUMRATE + "</td>"
                                            + "</tr>";
                                    })
                                    str1+= "</tbody></table></div>"
                                        + "</div>";
                                    $(".tableBox").html(str1);
                                    tabOrder();
                                },
                                error:function(){
                                    alert(ZTXTArr[i]+"一级下钻数据错误");
                                }
                            })

                            $(".tabBox ul li").on("click",function(){
                                var ind=$(this).index();
                                $(".tabBox ul li").css("color","#000000");
                                $(this).css("color","#c70019");
                                $("#t_r_b table tr").hide();
                                $("#t_r_b table tr").eq(ind).show();
                                var reportJson3 = {
                                    //year: Year,
                                    yearmonth:timess,
                                    znum:''+ZNUMArr[i]+'',
                                    vkbur:''+VKBURArr[ind]+''
                                }
                                $.ajax({
                                    url: ctx + "/crrc/kpi0609/getCustByApart",
                                    dataType: "json",
                                    data: reportJson3,
                                    type: "post",
                                    success: function (data) {
                                        if ('300' === data.statusCode || '301' === data.statusCode) {
                                            alert(data.message);
                                            $(".tableBox").html('');
                                            return true;
                                        }
                                        var str2='';
                                        str2 += "<div class='t_r myTable'>"
                                            + "<div class='t_r_t' id='t_r_t1'>"
                                            + "<div class='t_r_title'>"
                                            + "<table><thead>"
                                            + "<tr>"
                                            + "<th width='230px'>客户</th>"
                                            + "<th class='sort-numeric' width='100px'>金额<span class='span1'>&nbsp;↑&nbsp;</span><span class='span2'>&nbsp;↓&nbsp;</span></th>"
                                            + "<th class='sort-numeric'>占比<span class='span1'>&nbsp;↑&nbsp;</span><span class='span2'>&nbsp;↓&nbsp;</span></th>"
                                            + "</tr>"
                                            + "</thead></table></div></div>"
                                            + "<div class='t_r_content' id='t_r_content1'> "
                                            + "<table><tbody>"
                                        $.each(data,function(k,v) {
                                            str2+="<tr>"
                                                + "<td width='230px'>" + v.KUNRGTMD + "</td>"
                                                + "<td width='100px'>" + v.ZNUMTOTAL + "</td>"
                                                + "<td>" + v.ZNUMRATE + "</td>"
                                                + "</tr>";
                                        })
                                        str2+= "</tbody></table></div>"
                                            + "</div>";
                                        $(".tableBox").html(str2);
                                    },
                                    error:function(){
                                        alert(ZTXTArr[i]+"一级下钻数据错误");
                                    }
                                })
                            })
                        },
                        error:function(){
                            alert(ZTXTArr[i]+"数据错误");
                            $("#Mask").hide();
                        }
                    });
                })
            }*/
       // },
        error: function () {
            alert("error目标及完成数据错误");
        }
    });
    
    $("#shgl-xssrjwcqk .bt div").off("dblclick").on("dblclick",function(){
    	$("#Mask-s").show();
    	$.ajax({
            url: ctx + "/crrc/kpi0609/getRatePie",
            dataType: "json",
            data:reportJson,
            /* contentType: "application/json;charset=utf-8",*/
            type: "post",
            success: function (data) {
            	if (  data.statusCode==="300" || '301' === data.statusCode) {
                    alert(data.message);
                    $("#Mask").hide();
                    return true;
                }
            	//
            	var acturl=$("#scgl-sr .zd span").text().replace(/,/g,"");
            	acturl=acturl.replace(/万元/g,"");
            	/*acturl=acturl;*/
            	acturl=Number(acturl);
            	var pie_zong=0
            	for(var i=1;i<data.length;i++){
            		pie_zong=pie_zong+Math.round(data[i].ZNUMTOTAL/10000);
            	}
            	/*console.log(pie_zong)
            	console.log(acturl)*/
            	var acturl_w=acturl-pie_zong;
            	var str="<div class='ZTXT-lj'>" 
            				+"<div>"+data[0].ZTXT+"累计完成："+acturl_w+"万元</div>";
            			+"</div>"
            			
            	$("body").append(str);
            	$(".ZTXT-lj").css({
            		"position":"absolute",
            		"right":"25px",
            		"top":"190px",
            		"padding":"5px 10px",
            		"background-color":"#fff",
            		"z-index":"5"
            	});
            	//点击弹出框外的任何区域,弹出框消失,遮罩层消失
            	$("#Mask-s").off("click").on("click",function(){
                	$("#body .ZTXT-lj").remove();
                	$(this).hide();
                });
            	
            },
            error:function(){
            	alert("error");
            	
            }
        })
    });
    
    
    
    /*document.addEventListener('click',function (e) {
		var parent=$(e.target).parents('.ZTXT-lj,.body');
		//console.log(parent);
	    if(parent.length===1){
	    	$("body").removeClass("body");
		    //console.log('不在弹层与按钮区')
		    //操作此区域
		    $('#body .ZTXT-lj').remove();
		    $("#Mask").hide();
	    }
	});*/
}



 