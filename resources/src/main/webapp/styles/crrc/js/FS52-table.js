$(function () {
    $.ajax({
        url: ctx + "/crrc/reportFS52/result",
        dataType: "json",
        type: "post",
        success: function (data) {
	    	 if ('300' === data.statusCode || '301' === data.statusCode) {
	             alert(data.message);
	             $(".Mask").hide();
	             $(".ch-kp-51").html('');
	             return true;
	         }
            var arrX=[],arrY=[];
            $.each(data,function(key,val){
                $.each(val,function(k,v){
                	arrX.push(k);
                	if(v!=null){
                		arrY.push(Number(v));
                	}else{
                		arrY.push(null)
                	}
                })

            });
            var max=Math.ceil(Math.max.apply(this,arrY));
            console.log(arrY)
            /*var ymax1=arrY[0];
            for(var i=0;i<arrY.length;i++){
                if(ymax1<parseInt(arrY[i])){
                    ymax1=parseInt(arrY[i]);
                }
            }
            ymax1=Math.ceil(ymax1/10)*10;*/
            $(".cpcwgz").highcharts({
                chart: {
                    type: 'line',
                    marginLeft:33
                },
                title: {
                    text: '近两年铁路产品厂外故障趋势图',
                    /*align:'center',*/
                    style:{
                        fontSize: '16px',
                        color:"#484848",
                        fontWeight: 700
                    }
                },
                subtitle: {
                    text:'故障台数',
                    align:'left'
                },
                xAxis: {
                    categories: arrX,
                    labels:{
                    	rotation:0
                    }
                },
                yAxis: { // Primary yAxis
                    min:0,
                    max:max,
                    lineWidth:1,
                    labels: {
                        //format: '{value}°C',
                        style: {
                            color: '#c70019'
                        }
                    },
                    title: {
                        text: '（台）',
                        x:30,
                        y:-200,
                        rotation:0,//设置y轴标题为横向
                        style: {
                            color:'#c70019'
                        }
                    }/*,
                    labels: {
                        format: '{value:.Of}'
                    }*/
                },
                tooltip: {
                    backgroundColor: '#ffffff',
                    style: {  //提示框内容的样式
                        color: '#595758'
                    }/*,
                     shared: true*/
                },
                series:[{
                    type: 'line',
                    name: '台数',
                    color:"#c70019",
                    data: arrY,
                    tooltip: {
                        valueSuffix: '台'
                    },
                    marker: {
                        lineWidth: 1,
                        lineColor: "#c70019",
                        fillColor: '#fff'
                    }
                }],
                legend:{
                    align: 'right',
                    verticalAlign: 'top',//图标的位置
                    floating:true
                },
                plotOptions: {
                    series: {
                        marker: {
                            radius: 2,  //曲线点半径，默认是4
                            symbol: 'circle' //曲线点类型："circle", "square", "diamond", "triangle","triangle-down"，默认是"circle"
                        }
                    }
                }
            });
        },
        error: function () {
            alert("error");
        }
    })
})