var lock1=true;
var lock2=true;
var lock3=true;
/*
 * 取当前月份
 * 并将当前月份显示在右上角的日期选择框里
 * 
 * */
var now = new Date();
var nowMonth = now.getMonth() + 1;
var nowYear = now.getFullYear();
if(nowMonth<10){
	$(".data-time").val(nowYear + "-0" + nowMonth);
}else{
	$(".data-time").val(nowYear + "-" + nowMonth);
}
var nowTime=nowYear+"-"+nowMonth;
/*
 * 获取当前时间的前一天
 * 显示在页面右下角
 * */
var time = (new Date).getTime() - 24 * 60 * 60 * 1000;
var yesday = new Date(time);//前一天
yesday = yesday.getFullYear() + "年" + (yesday.getMonth()+1) + "月" + yesday.getDate()+"日";
$("#date").html("当前数据截止"+yesday);
/*
 * WdatePicker日期插件回调函数
 * 选择日期时数据变化
 * */
$(".data-time").on("click",function(){
	WdatePicker({
		dateFmt: 'yyyy-MM',
		skin:'whyGreen',
		minDate:"2017-01",
		maxDate:nowTime,
		onpicked:function(){
			var timess=$(".data-time").val().replace(/-/g,"");
			jygl_1(timess);//经营管理-营业收入与净利润
			scmb_1(timess);//生产管理目标-1
			scmb_2(timess);//生产管理目标-2
			zlgl_1(timess);//质量管理-质量损失趋势
			/*zlsh_2(timess);//质量管理-质量问题
			zlgl_3(timess);//质量管理-产品故障
*/			scgl_1(timess);//市场管理-销售收入指标
			scgl_2(timess);//市场管理-2
			jygl_2(timess);//经营管理-2
			cggl_1(timess);//采购管理-1
			cggl_2(timess);//采购管理-2
			//右下角年月日数据显示
			/*var timessLast=parseInt(timess.slice(4));
			if(timessLast<10){   
				nowMonth="0"+timessLast;
			}*/
			var now1 = new Date();
			var nowMonth1 = now1.getMonth() + 1;
			var nowYear1 = now1.getFullYear();
			var nowtime1;
			if(nowMonth1<10){
				nowtime1=nowYear1+"0" + nowMonth;
			}else{
				nowtime1=nowYear + "" + nowMonth;
			}
			if(timess==nowtime1){
               $("#date").html("当前数据截止"+yesday);                
			}else{
				var tian;
				var nian=timess.slice(0,4);
				var yue=timess.slice(4);
				if(nian%4==0&&yue=="02"){
					tian="29";
				}else if(nian%4!=0&&yue=="02"){
					tian="28";
				}else if(yue=="01"||yue=="03"||yue=="05"||yue=="07"||yue=="08"||yue=="10"||yue=="12"){
					tian="31";
				}else if(yue=="04"||yue=="06"||yue=="09"||yue=="11"){
					tian="30";
				}
				$("#date").html("当前数据截止"+nian+"年"+yue+"月"+tian+"日");
			}			
		}
	});		
});


/*
 * 点击弹出框右上角的小叉号,移除弹出框,并让遮罩层隐藏
 * obj  被点击的小叉号
 * par  当前被点击的小叉号所在的弹出框
 * mask 需要隐藏的遮罩层(例:$(".Mask"))
 * */
function can(obj,par,mask){
	obj.on("click",function(){
		$(this).parent(par).remove();
		mask.css("display","none");
	});
}
/*
 * 点击弹出框右上角的小叉号,移除弹出框,并让遮罩层隐藏
 * obj  被点击的小叉号
 * par  当前被点击的小叉号所在的弹出框
 * mask 需要隐藏的遮罩层(例:$(".Mask"))
 * */
function can2(obj,par,mask){
    obj.on("click",function(){
        $(this).parent(par).css("display","none");
        mask.css("display","none");
    });
}
/*
 * 点击弹出框右上角的小叉号,移除弹出框,并让遮罩层隐藏
 * obj  被点击的小叉号
 * par  当前被点击的小叉号所在的弹出框
 * mask 需要隐藏的遮罩层(例:$(".Mask"))
 * */
function can1(){
	$(".can").on("click",function(){
		$(".cggl-2").remove();
		$("#Mask2").css("display","none");
	});
}


//金额采用千分位，保留两位小数
/**
 * 将数值四舍五入后格式化.
 *
 * @param num 数值(Number或者String)
 * @param cent 要保留的小数位(Number)
 * @param isThousand 是否需要千分位 0:不需要,1:需要(数值类型);
 * @return 格式的字符串,如'1,234,567.45'
 * @type String
 */
    //$("table tr td").text(formatNumber(123456,2,1))
function formatNumber(num,cent,isThousand) {
	//如果传入的值为undefined
    if(num==undefined){
    	return "";
    }else{
    	num = num.toString().replace(/\$|\,/g,'');
    	// 检查传入数值为数值类型
        if(isNaN(num))
            num = "0";
        
        // 获取符号(正/负数)
        sign = (num == (num = Math.abs(num)));

        num = Math.floor(num*Math.pow(10,cent)+0.50000000001); // 把指定的小数位先转换成整数.多余的小数位四舍五入
        cents = num%Math.pow(10,cent);       // 求出小数位数值
        num = Math.floor(num/Math.pow(10,cent)).toString();  // 求出整数位数值
        cents = cents.toString();        // 把小数位转换成字符串,以便求小数位长度

        // 补足小数位到指定的位数
        while(cents.length<cent)
            cents = "0" + cents;

        if(isThousand) {
            // 对整数部分进行千分位格式化.
            for (var i = 0; i < Math.floor((num.length-(1+i))/3); i++)
                num = num.substring(0,num.length-(4*i+3))+','+ num.substring(num.length-(4*i+3));
        }

        if (cent > 0)
            return (((sign)?'':'-') + num + '.' + cents);
        else
            return (((sign)?'':'-') + num);
    }
    

    

}
//table表格排序
function tabOrder(){
	jQuery.fn.alternateRowColors = function() {                      //做成插件的形式
        /*$('tbody tr:odd', this).removeClass('even').addClass('odd');*/ //隔行变色 奇数行
        /*$('tbody tr:even', this).removeClass('odd').addClass('even');*/ //隔行变色 偶数行
        return this;
    };

    $('.myTable').each(function() {
        var $table = $(this).find("table");    //将table存储为一个jquery对象
        $table.alternateRowColors($table);          //在排序时隔行变色
        $('th', $table).each(function(column) {
        	$(this).find(".span1").css("cursor","pointer");
        	$(this).find(".span2").css("cursor","pointer");
        	$(this).find(".span2").hide();
        	var findSortKey;
            if ($(this).is('.sort-alpha')) {       //按字母排序
                findSortKey = function($cell) {
                    return $cell.find('sort-key').text().toUpperCase() + '' + $cell.text().toUpperCase();
                };
            } else if ($(this).is('.sort-numeric')) {       //按数字排序
                findSortKey = function($cell) {
                	/*console.log($cell.text().replace(/,/, ''));*/
                    var key = parseFloat($cell.text().replace(/,/g, ''));
                    return isNaN(key) ? 0 : key;
                };
            } else if ($(this).is('.sort-date')) {          //按日期排序
                findSortKey = function($cell) {
                    return Date.parse('1 ' + $cell.text());
                };
            }
    		if (findSortKey) {
    			var newDirection = -1;
                $(this).find(".span2").click(function() {
                	$(this).hide();
                	$(this).parent().find(".span1").show();
                    //反向排序状态声明
                    var rows = $table.find('tbody>tr').get(); //将数据行转换为数组
                    $.each(rows, function(index, row) {
                        row.sortKey = findSortKey($(row).children('td').eq(column));
                    });
                    rows.sort(function(a, b) {
                        if (a.sortKey < b.sortKey) return -newDirection;
                        if (a.sortKey > b.sortKey) return newDirection;
                        return 0;
                    });
                    $.each(rows, function(index, row) {
                        $table.children('tbody').append(row);
                        row.sortKey = null;
                    });
                    $table.find('th').removeClass('sorted-asc').removeClass('sorted-desc');
                    var $sortHead = $table.find('th').filter(':nth-child(' + (column + 1) + ')');
                    //调用隔行变色的函数
                    $table.alternateRowColors($sortHead);
                    //移除已排序的列的样式,添加样式到当前列
                    $table.find('td').filter(':nth-child(' + (column + 1) + ')');
                });
                //实现反向排序
                $(this).find(".span1").click(function() {
                	$(this).hide();
                	$(this).parent().find(".span2").show();
                    var rows = $table.find('tbody>tr').get(); //将数据行转换为数组
                    $.each(rows, function(index, row) {
                        row.sortKey = findSortKey($(row).children('td').eq(column));
                    });
                    rows.sort(function(a, b) {
                        if (a.sortKey > b.sortKey) return -newDirection;
                        if (a.sortKey < b.sortKey) return newDirection;
                        return 0;
                    });
                    $.each(rows, function(index, row) {
                        $table.children('tbody').append(row);
                        row.sortKey = null;
                    });
                    $table.find('th').removeClass('sorted-desc').addClass('sorted-asc');
                    var $sortHead = $table.find('th').filter(':nth-child(' + (column + 1) + ')');
                    //移除已排序的列的样式,添加样式到当前列
                    $table.find('td').filter(':nth-child(' + (column + 1) + ')');
                });
            }
        });
    });
}


/*
* 图表年份分割线
* Arr1当前图表显示的十三个月的年月数组
* Arr2空数组,最终将年份间隔位置放上最大值
* time当前传入的日期例2016162
* max当前y轴坐标的最大值
*/
function arrS(Arr1,Arr2,time,max){
	for(var i=0;i<Arr1.length;i++){
		if(Arr1[i].slice(0,4)==time.slice(0,4)){
			Arr2.push("1000000000");
		}else{
			Arr2.push("0");
		}
	}
	var zhi;
	for(var i=0;i<Arr2.length;i++){
		if(Arr2[i]=="1000000000"){
			zhi=i;
			break;
		}
	}
	for(var i=0;i<Arr2.length;i++){
		Arr2.splice(i,1,null);
	}
	Arr2.splice(zhi,1,max);
	/*Arr2.splice(Arr2.length-1,1,1000000000);*/
	/*console.log(zuArr);*/
}


//兼容ie8的indexOf()
function indexof(){
    if(!Array.prototype.indexOf){
        Array.prototype.indexOf=function(elt/*.form*/){
            var len=this.length>>>0;
            var form = Number(arguments[1])||0;
            form=(form<0)?Math.ceil(form):Math.floor(form);
            if(form<0)
                form+=len;
            for(;form<len;form++){
                if(form in this && this[form]===elt)
                    return form;

            }
            return -1;
        }
    }
}
indexof();
//去掉text文字的fill样式
/*setTimeout(remove_fill,5000);
function remove_fill(){
	var title=$("body").find("text");
	title.css("fill","");
	$("body .highcharts-legend-item").find("text").css("fill","");
	$("body").find("tspan").css("fill","");
}*/
/*remove_fill();*/


/*
 * 信息框拖拽
 * dian 拖拽时点击的部位(例:$(".name"))
 * box  被拖拽的div(例:$("#box"))
*/
function drag(dian,box){
	dian.onmousedown = function(event){//函数里面传一个event参数，是为了兼容火狐浏览器
		var ev = event || window.event;//鼠标按下获取当前的鼠标坐标
		var startX = ev.clientX - box.offsetLeft;
		//用div的横坐标减去div的offsetLeft，算出鼠标按下那一刻鼠标到div左边的距离
		var startY = ev.clientY - box.offsetTop;
		// alert(ev.clientY)
		//用div的纵坐标减去div的offsetHeight，算出鼠标按下那一刻鼠标到div顶端边的距离
		// console.log(startX,startY)
		document.onmousemove = function(event){
			var  ev = event || window.event;//获取鼠标移动时的坐标
			var endX = ev.clientX - startX;//算出鼠标移动后的横向距离
			var endY = ev.clientY - startY;//算出鼠标移动后的纵向距离
			// console.log(startX,startY)
			box.style.left = endX+"px";
			box.style.top = endY+"px";

			//临界值判断
			if(endX<=0){
				box.style.left = "0px";
			}else if(endX>=document.documentElement.clientWidth-box.offsetWidth){
				box.style.left = document.documentElement.clientWidth-box.offsetWidth+"px";
			}
			if(endY<=0){
				box.style.top = "0px";
			}else if(endY>=document.documentElement.clientHeight-box.offsetHeight){
				box.style.top = document.documentElement.clientHeight-box.offsetHeight+"px";
			}
		}
	}
	document.onmouseup = function(){
		document.onmousemove = null;//让事件为空就相当于清除事件
	}
}
/*
 * 信息框拖拽拉大缩小
 * coor鼠标放上的div
 * box被放大的div
 * min_w 被拖拽的最小宽度
 * max_w 被拖拽的最大宽度
 * min_h 被拖拽的最小高度
 * max_h 被拖拽的最大高度
*/
function drap_b(coor,box,min_w,max_w,min_h,max_h) {
	$(document).mousemove(function(e) {
		if (!!this.move) {
			var posix = !document.move_target ? {'x': 0, 'y': 0} : document.move_target.posix,
				callback = document.call_down || function() {
					$(this.move_target).css({
						'top': e.pageY - posix.y,
						'left': e.pageX - posix.x
					});
				};

			callback.call(this, e, posix);
		}
	}).mouseup(function(e) {
		if (!!this.move) {
			var callback = document.call_up || function(){};
			callback.call(this, e);
			$.extend(this, {
				'move': false,
				'move_target': null,
				'call_down': false,
				'call_up': false
			});
		}
	});

	var $box = $(box).mousedown(function(e) {
	    var offset = $(this).offset();
	    
	    /*this.posix = {'x': e.pageX - offset.left, 'y': e.pageY - offset.top};
	    $.extend(document, {'move': true, 'move_target': this});*/
	}).on('mousedown', coor, function(e) {
	    var posix = {
	            'w': $box.width(), 
	            'h': $box.height(), 
	            'x': e.pageX, 
	            'y': e.pageY
	        };
	    
	    $.extend(document, {'move': true, 'call_down': function(e) {
	        $box.css({
	            'width': Math.max(30, e.pageX - posix.x + posix.w),
	            'height': Math.max(30, e.pageY - posix.y + posix.h),
	            'min-width': min_w,
	            'max-width': max_w,
	            'min-height': min_h,
	            'max-height': max_h
	        });
	    }});
	    return false;
	});
}

//解锁
function unlock1(){
	if(window.lock1){
		return;
	}
	window.lock1=true;
	$("#Mask").hide();
}

function unlock2(){
	if(window.lock2){
		return;
	}
	window.lock2=true;
	$("#Mask2").hide();
}
function unlock3(){
	if(window.lock3){
		return;
	}
	window.lock3=true;
	$("#Mask3").hide();
}

/*
 * 按照中文、数字、字母排序
 * s_l 需要排序的列的index值
*/
function tabletabs(s_l) {  
    var tableObject = $('.tableSort table'); //获取id为tableSort的table对象  
    var tbHead = tableObject.children('thead'); //获取table对象下的thead  
    var tbHeadTh = tbHead.find('tr th'); //获取thead下的tr下的th  
    var tbBody = tableObject.children('tbody'); //获取table对象下的tbody  
    var tbBodyTr = tbBody.find('tr'); //获取tbody下的tr  

    var sortIndex = -1;  

    $.each(tbHeadTh,function (k,v) {//遍历thead的tr下的th  
    	if(k<s_l){
    		var thisIndex = tbHeadTh.index($(this)); //获取th所在的列号  
            //给表态th增加鼠标位于上方时发生的事件  
            $(this).mouseover(function () {  
                tbBodyTr.each(function () {//编列tbody下的tr  
                    var tds = $(this).find("td"); //获取列号为参数index的td对象集合  
                    //$(tds[thisIndex]).addClass("hover");//给列号为参数index的td对象添加样式  
                });  
            }).mouseout(function () {//给表头th增加鼠标离开时的事件  
                tbBodyTr.each(function () {  
                    var tds = $(this).find("td");  
                    //$(tds[thisIndex]).removeClass("hover");//鼠标离开时移除td对象上的样式  
                });  
            });  

            $(this).click(function () {//给当前表头th增加点击事件  
                var dataType = $(this).attr("type");//点击时获取当前th的type属性值  
                checkColumnValue(thisIndex, dataType);  
            });  
    	}
        
    });  

    $("tbody tr").removeClass(); //先移除tbody下tr的所有css类  
    //table中tbody中tr鼠标位于上面时添加颜色,离开时移除颜色  
    /*$("tbody tr").mouseover(function () {  
        $(this).addClass("hover");  
    }).mouseout(function () {  
        $(this).removeClass("hover");  
    }); */ 

    //对表格排序  
    function checkColumnValue(index, type) {  
        var trsValue = new Array();  

        tbBodyTr.each(function () {  
            var tds = $(this).find('td');  
            //获取行号为index列的某一行的单元格内容与该单元格所在行的行内容添加到数组trsValue中  
            trsValue.push(type + ".separator" + $(tds[index]).html() + ".separator" + $(this).html());  
            $(this).html("");  
        });  
        var len = trsValue.length;  
        if (index == sortIndex) {  
        //如果已经排序了则直接倒序  
            trsValue.reverse();  
        } else {  
            for (var i = 0; i < len; i++) {  
                //split() 方法用于把一个字符串分割成字符串数组  
                //获取每行分割后数组的第一个值,即此列的数组类型,定义了字符串\数字\Ip  
                type = trsValue[i].split(".separator")[0];  
                for (var j = i + 1; j < len; j++) {  
                    //获取每行分割后数组的第二个值,即文本值  
                    value1 = trsValue[i].split(".separator")[1];  
                    //获取下一行分割后数组的第二个值,即文本值  
                    value2 = trsValue[j].split(".separator")[1];  
                    //接下来是数字\字符串等的比较  
                    if (type == "number") {  
                        value1 = value1 == "" ? 0 : value1;  
                        value2 = value2 == "" ? 0 : value2;  
                        if (parseFloat(value1) > parseFloat(value2)) {  
                            var temp = trsValue[j];  
                            trsValue[j] = trsValue[i];  
                            trsValue[i] = temp;  
                        }  
                    } else if (type == "ip") {  
                        if (ip2int(value1) > ip2int(value2)) {  
                            var temp = trsValue[j];  
                            trsValue[j] = trsValue[i];  
                            trsValue[i] = temp;  
                        }  
                    } else {  
                        if (value1.localeCompare(value2) > 0) {//该方法不兼容谷歌浏览器  
                            var temp = trsValue[j];  
                            trsValue[j] = trsValue[i];  
                            trsValue[i] = temp;  
                        }  
                    }  
                }  
            }  
        }
        for (var i = 0; i < len; i++) {  
            $(".tableSort tbody tr:eq(" + i + ")").html(trsValue[i].split(".separator")[2]);  
        }  
        sortIndex = index;  
    }  
    //IP转成整型  
    function ip2int(ip) {  
        var num = 0;  
        ip = ip.split(".");  
        num = Number(ip[0]) * 256 * 256 * 256 + Number(ip[1]) * 256 * 256 + Number(ip[2]) * 256 + Number(ip[3]);  
        return num;  
    }  
}
/*
 * 按照中文、数字、字母排序
 * s_l 需要排序的列的index值
*/
function tabletabs1(s_l) {  
    var tableObject = $('.tableSort1 table'); //获取id为tableSort的table对象  
    var tbHead = tableObject.children('thead'); //获取table对象下的thead  
    var tbHeadTh = tbHead.find('tr th'); //获取thead下的tr下的th  
    var tbBody = tableObject.children('tbody'); //获取table对象下的tbody  
    var tbBodyTr = tbBody.find('tr'); //获取tbody下的tr  

    var sortIndex = -1;  

    $.each(tbHeadTh,function (k,v) {//遍历thead的tr下的th  
    	if(k<s_l){
    		var thisIndex = tbHeadTh.index($(this)); //获取th所在的列号  
            //给表态th增加鼠标位于上方时发生的事件  
            $(this).mouseover(function () {  
                tbBodyTr.each(function () {//编列tbody下的tr  
                    var tds = $(this).find("td"); //获取列号为参数index的td对象集合  
                    //$(tds[thisIndex]).addClass("hover");//给列号为参数index的td对象添加样式  
                });  
            }).mouseout(function () {//给表头th增加鼠标离开时的事件  
                tbBodyTr.each(function () {  
                    var tds = $(this).find("td");  
                    //$(tds[thisIndex]).removeClass("hover");//鼠标离开时移除td对象上的样式  
                });  
            });  

            $(this).click(function () {//给当前表头th增加点击事件  
                var dataType = $(this).attr("type");//点击时获取当前th的type属性值  
                checkColumnValue(thisIndex, dataType);  
            });  
    	}
        
    });  

    $("tbody tr").removeClass(); //先移除tbody下tr的所有css类  
    //table中tbody中tr鼠标位于上面时添加颜色,离开时移除颜色  
    /*$("tbody tr").mouseover(function () {  
        $(this).addClass("hover");  
    }).mouseout(function () {  
        $(this).removeClass("hover");  
    }); */ 

    //对表格排序  
    function checkColumnValue(index, type) {  
        var trsValue = new Array();  

        tbBodyTr.each(function () {  
            var tds = $(this).find('td');  
            //获取行号为index列的某一行的单元格内容与该单元格所在行的行内容添加到数组trsValue中  
            trsValue.push(type + ".separator" + $(tds[index]).html() + ".separator" + $(this).html());  
            $(this).html("");  
        });  
        var len = trsValue.length;  
        if (index == sortIndex) {  
        //如果已经排序了则直接倒序  
            trsValue.reverse();  
        } else {  
            for (var i = 0; i < len; i++) {  
                //split() 方法用于把一个字符串分割成字符串数组  
                //获取每行分割后数组的第一个值,即此列的数组类型,定义了字符串\数字\Ip  
                type = trsValue[i].split(".separator")[0];  
                for (var j = i + 1; j < len; j++) {  
                    //获取每行分割后数组的第二个值,即文本值  
                    value1 = trsValue[i].split(".separator")[1];  
                    //获取下一行分割后数组的第二个值,即文本值  
                    value2 = trsValue[j].split(".separator")[1];  
                    //接下来是数字\字符串等的比较  
                    if (type == "number") {  
                        value1 = value1 == "" ? 0 : value1;  
                        value2 = value2 == "" ? 0 : value2;  
                        if (parseFloat(value1) > parseFloat(value2)) {  
                            var temp = trsValue[j];  
                            trsValue[j] = trsValue[i];  
                            trsValue[i] = temp;  
                        }  
                    } else if (type == "ip") {  
                        if (ip2int(value1) > ip2int(value2)) {  
                            var temp = trsValue[j];  
                            trsValue[j] = trsValue[i];  
                            trsValue[i] = temp;  
                        }  
                    } else {  
                        if (value1.localeCompare(value2) > 0) {//该方法不兼容谷歌浏览器  
                            var temp = trsValue[j];  
                            trsValue[j] = trsValue[i];  
                            trsValue[i] = temp;  
                        }  
                    }  
                }  
            }  
        }
        for (var i = 0; i < len; i++) {  
            $(".tableSort1 tbody tr:eq(" + i + ")").html(trsValue[i].split(".separator")[2]);  
        }  
        sortIndex = index;  
    }  
    //IP转成整型  
    function ip2int(ip) {  
        var num = 0;  
        ip = ip.split(".");  
        num = Number(ip[0]) * 256 * 256 * 256 + Number(ip[1]) * 256 * 256 + Number(ip[2]) * 256 + Number(ip[3]);  
        return num;  
    }  
}
/*
 * 按照中文、数字、字母排序
 * s_l 需要排序的列的index值
*/
function tabletabs2(s_l) {  
    var tableObject = $('.tableSort2 table'); //获取id为tableSort的table对象  
    var tbHead = tableObject.children('thead'); //获取table对象下的thead  
    var tbHeadTh = tbHead.find('tr th'); //获取thead下的tr下的th  
    var tbBody = tableObject.children('tbody'); //获取table对象下的tbody  
    var tbBodyTr = tbBody.find('tr'); //获取tbody下的tr  
    var sortIndex = -1;  
    $.each(tbHeadTh,function (k,v) {//遍历thead的tr下的th  
    	if(k<s_l){
    		var thisIndex = tbHeadTh.index($(this)); //获取th所在的列号  
            //给表态th增加鼠标位于上方时发生的事件  
            $(this).mouseover(function () {  
                tbBodyTr.each(function () {//编列tbody下的tr  
                    var tds = $(this).find("td"); //获取列号为参数index的td对象集合  
                    //$(tds[thisIndex]).addClass("hover");//给列号为参数index的td对象添加样式  
                });  
            }).mouseout(function () {//给表头th增加鼠标离开时的事件  
                tbBodyTr.each(function () {  
                    var tds = $(this).find("td");  
                    //$(tds[thisIndex]).removeClass("hover");//鼠标离开时移除td对象上的样式  
                });  
            });  

            $(this).click(function () {//给当前表头th增加点击事件  
                var dataType = $(this).attr("type");//点击时获取当前th的type属性值  
                checkColumnValue(thisIndex, dataType);  
            });  
    	}
    });  
    $("tbody tr").removeClass(); //先移除tbody下tr的所有css类  
    //table中tbody中tr鼠标位于上面时添加颜色,离开时移除颜色  
    /*$("tbody tr").mouseover(function () {  
        $(this).addClass("hover");  
    }).mouseout(function () {  
        $(this).removeClass("hover");  
    }); */ 
    //对表格排序  
    function checkColumnValue(index, type) {  
        var trsValue = new Array();  
        tbBodyTr.each(function () {  
            var tds = $(this).find('td');  
            //获取行号为index列的某一行的单元格内容与该单元格所在行的行内容添加到数组trsValue中  
            trsValue.push(type + ".separator" + $(tds[index]).html() + ".separator" + $(this).html());  
            $(this).html("");  
        });  
        var len = trsValue.length;  
        if (index == sortIndex) {  
        //如果已经排序了则直接倒序  
            trsValue.reverse();  
        } else {  
            for (var i = 0; i < len; i++) {  
                //split() 方法用于把一个字符串分割成字符串数组  
                //获取每行分割后数组的第一个值,即此列的数组类型,定义了字符串\数字\Ip  
                type = trsValue[i].split(".separator")[0];  
                for (var j = i + 1; j < len; j++) {  
                    //获取每行分割后数组的第二个值,即文本值  
                    value1 = trsValue[i].split(".separator")[1];  
                    //获取下一行分割后数组的第二个值,即文本值  
                    value2 = trsValue[j].split(".separator")[1];  
                    //接下来是数字\字符串等的比较  
                    if (type == "number") {  
                        value1 = value1 == "" ? 0 : value1;  
                        value2 = value2 == "" ? 0 : value2;  
                        if (parseFloat(value1) > parseFloat(value2)) {  
                            var temp = trsValue[j];  
                            trsValue[j] = trsValue[i];  
                            trsValue[i] = temp;  
                        }  
                    } else if (type == "ip") {  
                        if (ip2int(value1) > ip2int(value2)) {  
                            var temp = trsValue[j];  
                            trsValue[j] = trsValue[i];  
                            trsValue[i] = temp;  
                        }  
                    } else {  
                        if (value1.localeCompare(value2) > 0) {//该方法不兼容谷歌浏览器  
                            var temp = trsValue[j];  
                            trsValue[j] = trsValue[i];  
                            trsValue[i] = temp;  
                        }  
                    }  
                }  
            }  
        }
        for (var i = 0; i < len; i++) {  
            $(".tableSort2 tbody tr:eq(" + i + ")").html(trsValue[i].split(".separator")[2]);  
        }  
        sortIndex = index;  
    }  
    //IP转成整型  
    function ip2int(ip) {  
        var num = 0;  
        ip = ip.split(".");  
        num = Number(ip[0]) * 256 * 256 * 256 + Number(ip[1]) * 256 * 256 + Number(ip[2]) * 256 + Number(ip[3]);  
        return num;  
    }  
}

/*
 * 判断是否为0,数字取s位小数并千分位
 * m  当前需要被判断的数字
 * s  保留几位小数
*/
function Check0(m,s){
   if(m==0){
	   return m;
   }else{
	   m=formatNumber(m,s,1)
	   return m;
   }
}
/*
 * 判断是否为0或者100,数字取s位小数并千分位
 * m  当前需要被判断的数字
 * s  保留几位小数
*/
function Check_l(m,s){
   if(m==0||m==100){
	   return m;
   }else{
	   m=formatNumber(m,s,1)
	   return m;
   }
}
//判断数据是否undefined或者小于等于0
function check_data(number,arr1,arr2){
	if(number==undefined || number<=0){
		arr1.push(null);
		arr2.push(null);
	}else{
		arr1.push(number);
		arr2.push(number);
	}
}
//去掉书组中的null,arr为要去掉null的数组
function remove_null(arr){
	var sss=null;
    for(var i=0;i<arr.length;i++){
        if(arr[i]==sss){
        	arr.splice(i, 1);
        	i=i-1;
        }
    }
}
//获取当前点击的鼠标的位置
function mouseCoords(event){ 
	if(event.pageX || event.pageY){ 
		return {x:event.pageX, y:event.pageY}; 
	}else{
		return{x:event.clientX, y:event.clientY};
	}
} 