var lock2=true;
//url通用处理方法
function doUrl(url,key,value){
    if(url.charAt(url.length -1) == "?" && value != null){
        url = url + key + "=" + value;
    }else if(url.charAt(url.length-1) != "?" && value != null){
        url = url + "&" + key + "=" + value;
    }
    return url;
}
//ajax用id和name查询
function ajaxJson(address,id,name,start,limit){
	if(id == ""){
		id = null;
	}
	if(name == ""){
		name = null;
	}else{
		name=name.replace(/\\/g,"\\\\\\\\");
	}
    var url = ctx + "/crrc/"+address+"?";
    url = doUrl(url,"id",id);
    url = doUrl(url,"name",name);
    url = doUrl(url,"start",start);
    url = doUrl(url,"limit",limit);
    //url=encodeURI(url);
    $.ajax({
        url:url,
        dataType:"json",
        type:"get",
        async:false,
        success:function(data){
        	if ('300' === data.statusCode || '301' === data.statusCode) {
                alert(data.message);
                return true;
            }
        	if(data.message=="没有分配权限"){
        		$(".multiValInterval tbody:eq(0)").html("");
        	}else{
        		if(data.length<10){
        			$(".spanNext1").css("background-color","#ededed");
            		$(".spanNext1").attr("disabled","disabled");
            	}else{
            		$(".spanNext1").css("background-color","#fff");
            		$(".spanNext1").prop("disabled",false);
            	}
                var str="";
                $.each(data,function(k,v){
                    var arr = new Array;
                    for(value in v){
                        arr.push(v[value]);
                    }
                    str+="<tr>"
    	                    +"<td><input type='checkbox' class='check' name='della'></td>"
    	                    +"<td>"+arr[0]+"</td>"
    	                    +"<td>"+arr[1]+"</td>"
                        +"</tr>";
                })
                $(".multiValInterval tbody:eq(0)").html(str);
                trBg($(".multiValInterval tbody:eq(0)"));
        	}
        	
            // 全选
            var Allclick1 = new allClick($(".multiValInterval tbody:eq(0) input"));
    		if(address.indexOf("tvendor")!=-1||address.indexOf("tcustomer")!=-1||address.indexOf("salesDocType")!=-1||address.indexOf("produOrderType")!=-1||address.indexOf("createuser")!=-1||address.indexOf("updateuser")!=-1||address.indexOf("lgort")!=-1||address.indexOf("vendortype")!=-1){
				page(0);
			}
    		//FS5005查询条件分页
    		if(address.indexOf("reportKPI2227")!=-1){
				page(0);
			}
    		//FS14
    		if(address.indexOf("matkl")!=-1||address.indexOf("valclass")!=-1||address.indexOf("lgort")!=-1){
				page(0);
			}
    		//FS28查询条件分页
    		if(address.indexOf("reportFS29/tpurgroup")!=-1||address.indexOf("reportFS29/zhwcx")!=-1){
				page(0);
			}
    		//Fs54
    		if(address.indexOf("reportFS54/fwz")!=-1||address.indexOf("reportFS54/fwd")!=-1||address.indexOf("reportFS54/cx")!=-1){
				page(0);
				$(".multiValInterval thead tr th:eq(1)").text("名称");
				$(".multiValInterval thead tr th:eq(2)").hide();
				for(var i=0;i<data.length;i++){
					$(".multiValInterval tbody tr").eq(i).find("td").eq(2).hide();
				}				
				var html="名称<input type='text' class='inp-id'><span style='display:none;'>编码<input type='text' class='inp-name'></span>"
				$(".multiSearch div:eq(1) p span").html(html);
				$(".multiSearch").css("width","275px")
			}
    		//FS39
    		if(address.indexOf("reportFS39/arbpl")!=-1){
				page(0);
			}
    		//FS21
    		if(address.indexOf("reportFS21/mrpctl")!=-1){
				page(0);
			}
    		
    		//FS50
    		if(address.indexOf("reportFS50/losstypecode")!=-1){
				page(0);
			}
    		//fs29 
    		if(address.indexOf("reportFS29/zhwcx")!=-1||address.indexOf("reportFS29/comppurtype")!=-1||address.indexOf("reportFS29/produOrderType")!=-1){
				page(0);
			}  
        },
        error:function(){
            alert("error")
        }
    })
}
/*
 * 伪分页
 * i为0的时候  多选伪分页
 * i为1的时候  单选伪分页
*/
function page(i){
//  伪分页
	var trs;
	if(i==0){
		trs=$(".multiValInterval tbody:eq(0) tr");
	}else if(i==1){
		trs=$(".intervalTwo tbody:eq(0) tr");
	}
	var pagesize=10;
	var curr=1;
	var currRow=pagesize*curr;
	var allpage=Math.ceil(trs.length/pagesize);
	var currRow=pagesize*curr;
	hideTab();
    curr=1;
    $(".spanPageNum").text(allpage);
    $(".spanTotalPage").text(curr);
    if(i==0){
    	$(".multiValInterval tbody:eq(0) tr:lt(10)").show();
    }else if(i==1){
    	$(".intervalTwo tbody:eq(0) tr:lt(10)").show();
    }
    
	//通用隐藏方法
	function hideTab(){
	    trs.hide();
	}
	//为下一页 绑定点击事件
	function spanNext(){
	  hideTab();
	    if(curr>allpage-1){
	      curr=allpage;
	    }else{
	      curr++;
	    }
	    var currRow=pagesize*curr;
	    $.each(trs,function(index,value){
	        if(index<currRow&&index>=currRow-pagesize){
	            var that=$(this);
	            that.show();
	        }
	    });
	    $(".spanTotalPage").text(curr);
	}

	//为上一页 绑定点击事件
	function spanPre(){
	  hideTab();
	    if(curr<2){
	      curr=1;
	    }else{
	      curr--;
	    }
	    var currRow=pagesize*curr;
	    $.each(trs,function(index,value){
	        if(index<currRow&&index>=currRow-pagesize){
	            var that=$(this);
	            that.show();
	        }
	    });
	    $(".spanTotalPage").text(curr);
	}
	$(".spanNext").bind("click",spanNext);
	$(".spanPre").bind("click",spanPre);
}
/*
 * ajax只用id多值查询
 * 
 * 
 * 
*/
function ajaxJson1(address,id,start,limit){
	if(id == ""){
		id = null;
	}else{
		id=escape(id);
	}
    var url = ctx + "/crrc/"+address+"?";
    url = doUrl(url,"id",id);
    url = doUrl(url,"start",start);
    url = doUrl(url,"limit",limit);
    url=encodeURI(url);
    $.ajax({
        url:url,
        dataType:"json",
        type:"get",
        async:false,
        success:function(data){
        	if ('300' === data.statusCode || '301' === data.statusCode) {
                alert(data.message);
                return true;
            }
        	if(data.length<10){
    			$(".spanNext1").css("background-color","#ededed");
        		$(".spanNext1").attr("disabled","disabled");
        	}else{
        		$(".spanNext1").css("background-color","#fff");
        		$(".spanNext1").prop("disabled",false);
        	}
            var str="";
            for(var i=0;i<data.length;i++){
            	str+="<tr>"
                    +"<td><input type='checkbox' class='check' name='della'></td>"
                    +"<td>"+data[i]+"</td>"
                    +"</tr>";
            }
            $(".multiValInterval tbody:eq(0)").html(str);
            trBg($(".multiValInterval tbody:eq(0)"));
            // 全选
            var Allclick1 = new allClick($(".multiValInterval tbody:eq(0) input"));
    		if(start==null&&limit==null){
				page(0);
			}
        },
        error:function(){
            alert("error")
        }
    })
}
//ajax只用id单值查询
function intervalAjax1(address,id,start,limit){
	if(id == ""){
		id = null;
	}
    var url = ctx + "/crrc/"+address+"?";
    url = doUrl(url,"id",id);
    url = doUrl(url,"start",start);
    url = doUrl(url,"limit",limit);
    //url=encodeURI(url);
	$.ajax({
		url:url,
		dataType:"json",
		type:"get",
		async: false,
		success:function(data){
			if ('300' === data.statusCode || '301' === data.statusCode) {
                alert(data.message);
                return true;
            }
			if(data.length<10){
				$(".spanNext1").prop("disaled","true");
				$(".spanNext1").css("background-color","#ededed");
			}else{
        		$(".spanNext1").css("background-color","#fff");
        		$(".spanNext1").prop("disabled",false);
        	}
			var str = "";
			$.each(data,function(k,v){
				// 添加信息
				str += "<tr>"
						+"<td><input type='radio' name='intervalTwo'></td>"
						+"<td>"+v+"</td>"
					+"</tr>";
			});
			$(".intervalTwo tbody").html(str);
			trBg($(".intervalTwo tbody"));
			//fs56型号
			if(url.indexOf("motormodel")!=-1){
				page(1)
			}
		},
		error:function(){
            alert("error")
        }
	});
}
//多区间查询,单选,通过id和name查询(物料)
function intervalAjax(address,id,name,start,limit){
	if(id == ""){
		id = null;
	}
	if(name == ""){
		name = null;
	}
    var url = ctx + "/crrc/"+address+"?";
    url = doUrl(url,"id",id);
    url = doUrl(url,"name",name);
    url = doUrl(url,"start",start);
    url = doUrl(url,"limit",limit);
    url=encodeURI(url);
	$.ajax({
		url:url,
		dataType:"json",
		type:"get",
		async: false,
		success:function(data){
			if ('300' === data.statusCode || '301' === data.statusCode) {
                alert(data.message);
                return true;
            }
			if(data.length<10){
				$(".spanNext1").prop("disaled","true");
				$(".spanNext1").css("background-color","#ededed");
			}else{
        		$(".spanNext1").css("background-color","#fff");
        		$(".spanNext1").prop("disabled",false);
        	}
			var str = "";
			$.each(data,function(k,v){
				// 添加信息
				var arr = new Array;
                for(value in v){
                    arr.push(v[value]);
                }
				str += "<tr>"
						+"<td><input type='radio' name='intervalTwo'></td>"
						+"<td>"+arr[0]+"</td>"
						+"<td>"+arr[1]+"</td>"
					+"</tr>";
			});
			$(".intervalTwo tbody").html(str);
			trBg($(".intervalTwo tbody"));
		},
		error:function(){
            alert("error")
        }
	});
}
//单选,通过id和name查询(工厂)
function intervalAjax2(address,id,name,start,limit){
	if(id == ""){
		id = null;
	}
	if(name == ""){
		name = null;
	}
    var url = ctx + "/crrc/"+address+"?";
    url = doUrl(url,"id",id);
    url = doUrl(url,"name",name);
    url = doUrl(url,"start",start);
    url = doUrl(url,"limit",limit);
    url=encodeURI(url);
	$.ajax({
		url:url,
		dataType:"json",
		type:"get",
		async: false,
		success:function(data){
			if(data.length<10){
				$(".spanNext1").prop("disaled","true");
				$(".spanNext1").css("background-color","#ededed");
			}else{
        		$(".spanNext1").css("background-color","#fff");
        		$(".spanNext1").prop("disabled",false);
        	}
			var str = "";
			$.each(data,function(k,v){
				// 添加信息
				str += "<tr>"
						+"<td><input type='radio' name='intervalTwo'></td>"
						+"<td>"+v.plant+"</td>"
						+"<td>"+v.txtmd+"</td>"
					+"</tr>";
			});
			$(".intervalTwo tbody").html(str);
			trBg($(".intervalTwo tbody"));
		},
		error:function(){
            alert("error")
        }
	});
}
//数组去重
Array.prototype.unique3 = function(){
   var res = [];
   var json = {};
   for(var i = 0; i < this.length; i++){
     if(!json[this[i]]){
        res.push(this[i]);
        json[this[i]] = 1;
     }
   }
   return res;
}
//点击取消按钮，隐藏弹出框，解锁查询按钮
function Cancel(btn){
	btn.off("click").on("click",function(){
		if(window.lock2){
			return;
		}
		window.lock2=true;
		$(this).closest("div").parent("div").hide();
		$(".btnAll").css("background-color","#c70019");
		$(this).parent('.multiSearch').find('tbody:eq(0)').html("");
		$(this).parent('.multiSearch').find('tbody:eq(1) input').prop("checked",false);
	});
}

//获取Value
function getValue(value){
	if(value==undefined){
		value="";
	}
	return value;
}
//获取日期
//年月日
function getTime(text1,text2){
	var times=text1.slice(0,4)+"年"+text1.slice(4,6)+"月"+text1.slice(6,8)+"日"+" - "+text2.slice(0,4)+"年"+text2.slice(4,6)+"月"+text2.slice(6,8)+"日";
	return times;
}
//年月
function getTime1(text1,text2){
	var times1=text1.slice(0,4)+"年"+text1.slice(4,6)+"月"+" - "+text2.slice(0,4)+"年"+text2.slice(4,6)+"月";
	return times1;
}
//单个年月日
function getTime2(text1){
    var times2=text1.slice(0,4)+"年"+text1.slice(4,6)+"月"+text1.slice(6,8)+"日";
    return times2;
}
//单个年月
function getTime3(text1){
    var times2=text1.slice(0,4)+"年"+text1.slice(4,6)+"月";
    return times2;
}
//点击全选，所有的复选框被选中
function allCheck(btn){
	btn.off("click").on("click",function(){
		if(!btn.prop("checked")){
			btn.parents("table").find("thead input").prop("checked",false); 
		}else{
			btn.parents("table").find("thead input").prop("checked",true); 
		}
	});	
}
allCheck($(".allCheck"));
allCheck($(".allCheck1"));
function Allclick(inp){
	var l = inp.length;
	var _this = this;
	inp.on("click",function(){
		if(inp.hasClass("allCheck")){
			_this.aa(l);
		}else if(inp.hasClass("checkallCol")){
			_this.cc(l);
		}else{
			_this.bb(l);
		}
	});
}
Allclick.prototype={
	aa:function(lengths){
		if(lengths==$(".multiValInterval tbody:eq(0) input:checked").length){
			$(".multiValInterval thead input").prop("checked",true);
		}else{
			$(".multiValInterval thead input").prop("checked",false);
		}
	},
	bb:function(lengths){
		if(lengths==$(".multiValInterval1 tbody:eq(0) input:checked").length){
			$(".multiValInterval1 thead input").prop("checked",true);
		}else{
			$(".multiValInterval1 thead input").prop("checked",false);
		}
	},
	cc:function(lengths){
		if(lengths==$(".hideTable tbody:eq(0) input:checked").length){
			$(".hideTable thead input").prop("checked",true);
		}else{
			$(".hideTable thead input").prop("checked",false);
		}
	}
}
//查询分页(每次请求十条数据)
function pagination(url){
	$(".spanPre1").attr("disabled","disabled");
	$(".spanPre1").css("background-color","#ededed");
	var i=0;
	$(".spanNext1").off("click").on("click",function(){
		$(".multiValInterval thead input").prop("checked",false);
		i++;
		if(i==0){
			$(".spanPre1").attr("disabled","disabled");
			$(".spanPre1").css("background-color","#ededed");
		}else{
			$(".spanPre1").prop("disabled",false);
			$(".spanPre1").css("background-color","#fff");
		}
		if(url.indexOf("reportFS50/losstypecode")!=-1 || url.indexOf("sernr")!=-1 || url.indexOf("productmodel")!=-1 || url.indexOf("linenum")!=-1 || url.indexOf("reportFS50/infocode")!=-1 || url.indexOf("reportFS50/produccode")!=-1 || url.indexOf("reportFS50/motorcode")!=-1 ){
			ajaxJson1(url,$(".multiSearch div:eq(1) input:eq(0)").val(),i*10,10);
		}else if(url.indexOf("reportKPI2227/losstypecode")!=-1 || url.indexOf("reportKPI2227/occurplant")!=-1 || url.indexOf("reportKPI2227/dutyplant")!=-1){
			ajaxJson1(url,$(".multiSearch div:eq(1) input:eq(0)").val(),i*10,10);
		}else if(url.indexOf("reportFS29/vbeln")!=-1 ||url.indexOf("reportFS29/linenum")!=-1 ||url.indexOf("reportFS29/aufnr")!=-1 ||url.indexOf("reportFS29/productmodel")!=-1){
			ajaxJson1(url,$(".multiSearch div:eq(1) input:eq(0)").val(),i*10,10);
		}else{
			ajaxJson(url,$(".inp-id").val(),$(".inp-name").val(),i*10,10);
			intervalAjax(url,$(".intervalTwo p input:eq(0)").val(),$(".intervalTwo p input:eq(1)").val(),i*10,10);
		}
		
		
	});
	$(".spanPre1").off("click").on("click",function(){
		$(".multiValInterval thead input").prop("checked",false);
		i--;
		if(i==0){
			$(".spanPre1").attr("disabled","disabled");
			$(".spanPre1").css("background-color","#ededed");
		}
		if(url.indexOf("reportFS50/losstypecode")!=-1 || url.indexOf("sernr")!=-1 || url.indexOf("productmodel")!=-1 || url.indexOf("linenum")!=-1 || url.indexOf("reportFS50/infocode")!=-1 || url.indexOf("reportFS50/produccode")!=-1 || url.indexOf("reportFS50/motorcode")!=-1 ){
			ajaxJson1(url,$(".multiSearch div:eq(1) input:eq(0)").val(),i*10,10);
		}else if(url.indexOf("reportKPI2227/losstypecode")!=-1 || url.indexOf("reportKPI2227/occurplant")!=-1 || url.indexOf("reportKPI2227/dutyplant")!=-1){
			ajaxJson1(url,$(".multiSearch div:eq(1) input:eq(0)").val(),i*10,10);
		}else if(url.indexOf("reportFS29/vbeln")!=-1 ||url.indexOf("reportFS29/linenum")!=-1 ||url.indexOf("reportFS29/aufnr")!=-1 ||url.indexOf("reportFS29/productmodel")!=-1){
			ajaxJson1(url,$(".multiSearch div:eq(1) input:eq(0)").val(),i*10,10);
		}else{
			ajaxJson(url,$(".inp-id").val(),$(".inp-name").val(),i*10,10);
			intervalAjax(url,$(".intervalTwo p input:eq(0)").val(),$(".intervalTwo p input:eq(1)").val(),i*10,10);
		}        
	})
}
//查询条件数据全部显示----分页
 
  
//表格滚动条
function aa(){
    var b=document.getElementById("t_r_content").scrollLeft; 
     document.getElementById("t_r_t").scrollLeft=b; 
}
function bb(){
    var b=document.getElementById("t_r_content2").scrollLeft; 
     document.getElementById("t_r_t2").scrollLeft=b; 
}
function cc(){
    var b=document.getElementById("t_r_content3").scrollLeft; 
     document.getElementById("t_r_t3").scrollLeft=b; 
}
function dd(){
    var b=document.getElementById("t_r_content4").scrollLeft; 
     document.getElementById("t_r_t4").scrollLeft=b; 
}
function ee(){
    var b=document.getElementById("t_r_content5").scrollLeft; 
     document.getElementById("t_r_t5").scrollLeft=b; 
}
function ff(){ 
    var a=document.getElementById("t_r_content2").scrollTop; 
    var b=document.getElementById("t_r_content2").scrollLeft; 
     document.getElementById("cl_freeze2").scrollTop=a; 
     document.getElementById("t_r_t2").scrollLeft=b; 
 } 
function kk(){ 
    var a=document.getElementById("t_r_content").scrollTop; 
    var b=document.getElementById("t_r_content").scrollLeft; 
     document.getElementById("cl_freeze").scrollTop=a; 
     document.getElementById("t_r_t").scrollLeft=b; 
 } 
function gg(){ 
    var a=document.getElementById("t_r_content3").scrollTop; 
    var b=document.getElementById("t_r_content3").scrollLeft; 
     document.getElementById("cl_freeze3").scrollTop=a; 
     document.getElementById("t_r_t3").scrollLeft=b; 
 }
function hh(){
    var b=document.getElementById("t_r_content2").scrollLeft;
    document.getElementById("t_r_t2").scrollLeft=b;
    document.getElementById("t_r_b2").scrollLeft=b;
}
function II(){
    var b=document.getElementById("t_r_content").scrollLeft;
    document.getElementById("t_r_t").scrollLeft=b;
    document.getElementById("t_r_b").scrollLeft=b;
}
//表格纵向滚动条
function landscape1(){
    var b=document.getElementById("t_r_content1").scrollTop; 
     document.getElementById("t_r_content").scrollTop=b; 
}
function landscape2(){
    var b=document.getElementById("t_r_content2").scrollTop; 
     document.getElementById("t_r_content").scrollTop=b;
     document.getElementById("t_r_content1").scrollTop=b; 
}
//多值和区间查询
function multiSearch(btn,address,start,limit){
	btn.on("click",function(){
		if(!window.lock2){
			return;
		}
		window.lock2=false;
		$("#Mask").show();
		$(this).css("background-color","#a40115");
		var str = "<div class='tabMask firstBox'>"
						+"<p class='cancel'></p>"
						+"<ul>"
							+"<li class='bg'>多值</li>"
							+"<li>区间</li>"
						+"</ul>"
						+"<div class='tabChoice'>"
							+"<div class='multiBox'>"
								+"<p><span>编码</span><input type='text' class='codingOne'><button class='btnOneSecond'></button></p>"
							+"</div>"
							+"<div class='intervalBox'>"
								+"<div><button class='add'><img style='height:100%;width:100%;' src='/resources/styles/common/images/add.png' /></button></div>"
								+"<p>上限：<input type='text' class='inpOne'>" +
										"<button class='searchs' style='z-index:-1;'>" +
											"<img style='height:100%;width:100%;' src='/resources/styles/common/images/searchs.png' />" +
										"</button> " +
									"下限：<input type='text' class='inpTwo'>" +
										"<button class='searchs' style='z-index:-1;'>" +
											"<img style='height:100%;width:100%;' src='/resources/styles/common/images/searchs.png' />" +
										"</button>" +
										"<button class='del' style='z-index:-1;'>" +
											"<img style='height:100%;width:100%;' src='/resources/styles/common/images/deldel.png' />" +
										"</button>" +
								"</p>"
							+"</div>"
							+"<p style='text-align: center;margin-top: 20px;'><button class='cancel'>取消</button><button class='sure sureBtn'>确定</button></p>"
						+"</div>"
					+"</div>"
		$("body").append(str);
		var lefts=$(this).offset().left-134;
		var tops=$(this).offset().top+30;
		$(".tabMask").css({
			"left":lefts+"px",
			"top":tops+"px"
		});
		//点击添加按钮,添加区间查询框
		$(".intervalBox div").on("click",".add",function(){
			var strr="<p>上限：<input type='text' class='inpOne'><button class='searchs'></button> 下限：<input type='text' class='inpTwo'><button class='searchs'></button><button class='del'></button></p>";
			if($(".intervalBox p").length<5){
				$(".intervalBox").append(strr);
			}
		});
		//点击删除按钮 ,删除当前区间查询框
		$(".intervalBox").on("click",".del",function(){
			if($(".intervalBox p").length==1){
				$(".del").off("click");
			}else{
				$(this).parent("p").remove();
			}
		});
		$(".intervalBox").on("click",".searchs",function(){
			var _this=$(this);
			var str2="<div class='intervalTwo' style='width:450px;z-index:40;'>"
							+"<div class='cancel2'></div>"
							+"<p><span>编码<input type='text'>名称<input type='text'></span><button class='searchOne'></button></p>"
                            +"<div style='height:360px;overflow:auto;'>"
                                +"<table border='1'>"
                                	+"<thead>"
	                                    +"<tr>"
	                                        +"<th width='20px'></th>"
	                                        +"<th>编码</th>"
	                                        +"<th>名称</th>"
	                                    +"</tr>"
                                    +"</thead>"
                                    +"<tbody>"
                                        
                                    +"</tbody>"
                                +"</table>"
                            +"</div>"
                            +"<p style='text-align: right;'>"
                            +"<button class='spanPre1'>上一页</button>"
		                	+"<button class='spanNext1'>下一页</button>"
	                        +"</p>"
	                        +"<p style='text-align:center;'><button class='cancel2'>取消</button><button class='sure2'>确定</button></p>"
                        +"</div>";
			$("body").append(str2);
			$(".intervalTwo").css({
				"top":tops+40+"px",
				"left":lefts+"px"
			});
			$("#Mask2").show();
			intervalAjax(address,$(".intervalTwo p input:eq(0)").val(),$(".intervalTwo p input:eq(1)").val(),start,limit);
			pagination(address);
			$(".intervalTwo").on("click",".searchOne",function(){
				intervalAjax(address,$(".intervalTwo p input:eq(0)").val(),$(".intervalTwo p input:eq(1)").val(),start,limit);
				pagination(address);
			})
			//点击确定按钮传值
			$(".sure2").on("click",function(){
				var ele=$(".intervalTwo").find('tbody input:checked').parent("td").next("td").text();
				_this.prev("input").val(ele);
				$(this).parents(".intervalTwo").remove();
				$("#Mask2").hide();
			})
//			点击取消按钮   取消当前弹出框
			$(".cancel2").on("click",function(){
				$(this).parents(".intervalTwo").remove();
				$("#Mask2").hide();
			});
		});
		//多值和区间进行切换		
		Change($(".tabMask li"));
		$(".cancel").on("click",function(){
			$(".tabMask").remove();
			$("#Mask").hide();
		});
		//点击多值的查询按钮进行条件查询
		$(".btnOneSecond").off("click").on("click",function(){
			var str1="<div class='multiSearch' style='display:block;width:450px;z-index:40;'>"
						+"<div class='cancel1'></div>"
						+"<div>"
							+"<p><span>编码<input type='text' class='inp-id'>名称<input type='text' class='inp-name'></span><button class='searchOne'></button></p>"
							+"<table class='multiValInterval' border='1'>"
								+"<thead>"
									+"<tr>"
										+"<th width='20px'><input type='checkbox' class='allCheck' id='allCheck'></th>"
                                        +"<th>编码</th>"
                                        +"<th>名称</th>"
                                    +"</tr>"
                                +"</thead>"
                                +"<tbody class='tbodyOneThird'>"
                                    
                                +"</tbody>"
                            +"</table>"
                        +"</div>"
                        +"<p style='text-align: right;'>"
	                        +"<button class='spanPre1'>上一页</button>"
		                	+"<button class='spanNext1'>下一页</button>"
	                    +"</p>"
	                    +"<p style='text-align: center'><button class='cancel1'>取消</button><button class='sure1'>确定</button></p>"
                    +"</div>";
			$("body").append(str1);
			$(".multiSearch").css({
				"top":tops+40+"px",
				"left":lefts+"px"
			});
			$("#Mask2").show();
			ajaxJson(address,$(".multiSearch div:eq(1) input:eq(0)").val(),$(".multiSearch div:eq(1) input:eq(1)").val(),start,limit);
			pagination(address);
			//多值查询传值
			var arrr=[],val="";
			$("#allCheck").off("click").on("click",function(){
				if(!$(this).prop("checked")){
					$(this).parents("table").find("tbody input").prop("checked",false);
					$.each(arrr,function(k,v){
						for(var i=0;i<$(".multiValInterval tbody tr").length;i++){
							val=$(".multiValInterval tbody tr:eq("+i+") td:eq(1)").text();
							if(v==val){
								arrr.splice(k,1);
							}
						}
					});
				}else{
					$(this).parents("table").find("tbody input").prop("checked",true);
					for(var i=0;i<$(".multiValInterval tbody tr").length;i++){
						arrr.push($(".multiValInterval tbody tr:eq("+i+") td:eq(1)").text());
					}
				}
				
			});
			$(".multiSearch").off("click").on("click",".check",function(){
				if($(this).prop("checked")==true){
					val=$(this).parent("td").next("td").text();
					arrr.push(val);
				}else if($(this).prop("checked")==false){
					val=$(this).parent("td").next("td").text();
					$.each(arrr,function(k,v){
						if(v==val){
							arrr.splice(k,1);
						}
					})
				}
			});
			$(".multiSearch").on("click",".searchOne",function(){
				ajaxJson(address,$(".multiSearch .inp-id").val(),$(".multiSearch .inp-name").val(),start,limit);
				pagination(address);
			})
			//点击确定按钮,将选中的编码传至物料多值查询框中
			$(".sure1").on("click",function(){
				arrr=arrr.unique3();
				$('.codingOne').val(arrr.join(','));
				$(this).parents(".multiSearch").remove();
				$("#Mask2").hide();
			});
			
			$(".cancel1").on("click",function(){
				$(this).parents(".multiSearch").remove();
				$("#Mask2").hide();
			});
		});
		
		$(".sure").on("click",function(){
			$("#Mask").hide();
			var txt1=$('.codingOne').val();
			var arr1=[];
			var string="",string1="";
			for(var i=0;i<$(".intervalBox .inpOne").length;i++){
				var txt2 = $(".intervalBox .inpOne:eq("+i+")").val();
				var txt3 = $(".intervalBox .inpTwo:eq("+i+")").val();
				string=txt2+":"+txt3;
				if(txt2==""||txt3==""){
					string="";
				}
				arr1.push(string);
				//去掉空的数组的值
				$.each(arr1,function(index,item){
					if(item==""){
						arr1.splice(index,1);
					}
				});
			}
			string1=arr1.join(",")
			if(txt1==""&&string1==""){
				$(".material").val("");
			}else if(txt1==""&&string1!=""){
				$(".material").val(string1);
			}else if(txt1!=""&&string1==""){
				$(".material").val(txt1)
			}else if(txt1!=""&&string1!=""){
				$(".material").val(txt1+";"+string1);
			}
			$(this).parents(".tabMask").remove();
			$(".btnAll").css("background-color","#c70019");
			if(window.lock2){
				return;
			}
			window.lock2=true;
			
		});
		$(".cancel").on("click",function(){
			$(this).parents(".tabMask").remove();
			$(".btnAll").css("background-color","#c70019");
			if(window.lock2){
				return;
			}
			window.lock2=true;
		});
	})
}
//id  分页多值查询
function multiSearch4(btn,address,start,limit){
	btn.on("click",function(){
		if(!window.lock2){
			return;
		}
		window.lock2=false;
		$("#Mask").show();
		var _this=$(this);
		$(this).css("background-color","#a40115");
		var str="<div class='multiSearch' style='width:260px;display:block;'>"
			+"<div class='cancel1'></div>"
			+"<div>"
				+"<p><span class='bianma'>编码</span><input type='text' class='inp-id'><button class='searchOne'></button></p>"
				+"<table class='multiValInterval' border='1'>"
					+"<thead>"
						+"<tr>"
							+"<th width='20px'><input type='checkbox' class='allCheck' id='allCheck'></th>"
                            +"<th>编码</th>"
                        +"</tr>"
                    +"</thead>"
                    +"<tbody class='tbodyOneThird'>"
                        
                    +"</tbody>"
                +"</table>"
            +"</div>"
            +"<p style='text-align: right;'>"
            +"<button class='spanPre1'>上一页</button>"
        	+"<button class='spanNext1'>下一页</button>"
        	+"</p>"
            +"<p style='text-align: center'><button class='cancel1'>取消</button><button class='sure1'>确定</button></p>"
        +"</div>";
		$("body").append(str);
		//按名称模糊查询
		if(address=="reportFS50/tvendor"){
			$(".multiSearch .bianma").text("名称");
			$(".multiSearch div table tr th:eq(1)").text("名称");
			
		}
		if(address=="reportKPI2227/losstypecode"){
			$(".multiSearch").css("width","310px")
		}
		var lefts=$(this).offset().left-134;
		var tops=$(this).offset().top+30;
		if(lefts>1000){
			lefts=$(this).offset().left-300;
			$(".multiSearch").css({
				"left":lefts+"px",
				"top":tops+"px"
			});
		}else{
			lefts=$(this).offset().left-134;
			$(".multiSearch").css({
				"left":lefts+"px",
				"top":tops+"px"
			});
		}
		//多值查询传值
		var arrr=[],val="";
		$("#allCheck").off("click").on("click",function(){
			if(!$(this).prop("checked")){
				$(this).parents("table").find("tbody input").prop("checked",false);
				$.each(arrr,function(k,v){
					for(var i=0;i<$(".multiValInterval tbody tr").length;i++){
						val=$(".multiValInterval tbody tr:eq("+i+") td:eq(1)").text();
						if(v==val){
							arrr.splice(k,1);
						}
					}
				});
			}else{
				$(this).parents("table").find("tbody input").prop("checked",true);
				for(var i=0;i<$(".multiValInterval tbody tr").length;i++){
					arrr.push($(".multiValInterval tbody tr:eq("+i+") td:eq(1)").text());
				}
			}
		
		});
		$(".multiSearch").off("click").on("click",".check",function(){
			if($(this).prop("checked")==true){
				val=$(this).parent("td").next("td").text();
				arrr.push(val);
			}else if($(this).prop("checked")==false){
				val=$(this).parent("td").next("td").text();
				$.each(arrr,function(k,v){
					if(v==val){
						arrr.splice(k,1);
					}
				})
			}
		})
		ajaxJson1(address,$(".multiSearch div:eq(1) input:eq(0)").val(),start,limit);
		pagination(address);
		//		点击查询按钮,通过id和name紧凑型查询start,limit
		$(".multiSearch").on("click",".searchOne",function(){
			ajaxJson1(address,$(".multiSearch .inp-id").val(),start,limit);
			pagination(address);
		});
		//点击取消按钮,移除当前弹出框,解锁按钮		
		$(".cancel1").on("click",function(){
			$(this).parents(".multiSearch").remove();
			unlock();
		});
		//点击确定按钮,移除当前弹出框,并传值给界面查询框,解锁按钮
		$(".sure1").on("click",function(){
			arrr=arrr.unique3();
			_this.prev("input").val(arrr.join(','));
			//$('.dwerkValue').val(arrr.join(','));
			//$('.dwerkValue').val(arrr.join(','));
			//sernr
			$(this).parents(".multiSearch").remove();
			unlock();
		})
		
	})
}
//点击多值和区间其内容发生变化
function Change(choice){
	choice.on("click",function(){
		var ind=$(this).index();
		$(this).addClass("bg").siblings().removeClass("bg");
		if(ind==0){
			$(".multiBox").show();
			$(".intervalBox").hide();
		}else if(ind==1){
			$(".intervalBox").show();
			$(".multiBox").hide();
		}
		
	})
}
//点击取消移除当前查询框
function cancel(){
	$(this).closest("div").parent("div.multiSearch").remove();
	$(".btnAll").css("background-color","#c70019");
}

//多值查询用id和name查询
function multiSearch1(btn,address,start,limit){
	btn.off("click").on("click",function(){
		if(!window.lock2){
			return;
		}
		window.lock2=false;
		$("#Mask").show();
		var _this=$(this);
		$(this).css("background-color","#a40115");
		var str="<div class='multiSearch' style='display:block;width:400px;'>"
					+"<div class='cancel1'></div>"
					+"<div>"
						+"<p><span><b class='bianma'>编码</b><input type='text' class='inp-id'><b class='mingcheng'>名称</b><input type='text' class='inp-name'></span><button class='searchOne'></button></p>"
						+"<table class='multiValInterval' border='1'>"
							+"<thead>"
								+"<tr>"
									+"<th width='20px'><input type='checkbox' class='allCheck' id='allCheck'></th>"
		                            +"<th>编码</th>"
		                            +"<th>名称</th>"
		                        +"</tr>"
		                    +"</thead>"
		                    +"<tbody class='tbodyOneThird'>"
		                        
		                    +"</tbody>"
		                +"</table>"
		            +"</div>"
		            +"<p style='text-align: right;'>"
		                +"<button class='spanPre'></button>"
	                	+"<span class='spanTotalPage'></span>"
	                	+"<button class='spanNext'></button>"
	                	+"共<span class='spanPageNum'></span>页"
	                +"</p>"
	                +"<p style='text-align: center'><button class='cancel1'>取消</button><button class='sure1'>确定</button></p>"
		        +"</div>";
		$("body").append(str);
		var lefts=$(this).offset().left-134;
		var tops=$(this).offset().top+30;
		if(lefts>1000){
			lefts=$(this).offset().left-300;
			$(".multiSearch").css({
				"left":lefts+"px",
				"top":tops+"px"
			});
		}else{
			lefts=$(this).offset().left-134;
			$(".multiSearch").css({
				"left":lefts+"px",
				"top":tops+"px"
			});
		}
		ajaxJson(address,$(".multiSearch div:eq(1) input:eq(0)").val(),$(".multiSearch div:eq(1) input:eq(1)").val(),start,limit);
//		点击查询按钮,通过id和name紧凑型查询
		$(".multiSearch").on("click",".searchOne",function(){
			ajaxJson(address,$(".multiSearch .inp-id").val(),$(".multiSearch .inp-name").val(),start,limit);
		});
//		点击全选按钮选中当前页全部
		checkAll($("#allCheck"));
		allClick($(".multiValInterval tbody:eq(0) input"));
		//点击取消按钮,移除当前弹出框,解锁按钮		
		$(".cancel1").on("click",function(){
			$(this).parents(".multiSearch").remove();
			unlock();
		});
		//点击确定按钮,移除当前弹出框,并传值给界面查询框,解锁按钮
		$(".sure1").on("click",function(){
			var arr=[];
			var ele=$(this).parents('.multiSearch').find('tbody input:checked');
			ele.each(function(k,v){
				if($(v).hasClass('check')){
					var parent=$(v).parents('tr');
					arr.push(parent.find('td').eq(1).html());
				}
			});
			arr=arr.unique3();
			_this.prev("input").val(arr.join(','));
			$(this).parents(".multiSearch").remove();
			unlock();
		})
	});
}

//多值查询用id或者name查询,前端分页
function multiSearch3(btn,address,start,limit){
	btn.off("click").on("click",function(){
		if(!window.lock2){
			return;
		}
		window.lock2=false;
		$("#Mask").show();
		var _this=$(this);
		$(this).css("background-color","#a40115");
		var str="<div class='multiSearch' style='width:260px;display:block;'>"
					+"<div class='cancel1'></div>"
					+"<div>"
						+"<p><b class='bianma'>编码</b><input type='text' class='inp-id'><button class='searchOne'></button></p>"
						+"<table class='multiValInterval' border='1'>"
							+"<thead>"
								+"<tr>"
									+"<th width='20px'><input type='checkbox' class='allCheck' id='allCheck'></th>"
		                            +"<th>编码</th>"
		                        +"</tr>"
		                    +"</thead>"
		                    +"<tbody class='tbodyOneThird'>"
		                        
		                    +"</tbody>"
		                +"</table>"
		            +"</div>"
		            +"<p style='text-align: right;'>"
	                	+"<button class='spanPre'></button>"
	                	+"<span class='spanTotalPage'></span>"
	                	+"<button class='spanNext'></button>"
	                	+"共<span class='spanPageNum'></span>页"
	                +"</p>"
	                +"<p style='text-align: center'><button class='cancel1'>取消</button><button class='sure1'>确定</button></p>"
		        +"</div>";
		$("body").append(str);
		//按名称模糊查询
		if(address=="reportFS53/gzlx"||address=="reportFS39/xqdw"||address=="reportFS39/ltxa1"){
			$(".multiSearch .bianma").text("名称");
			$(".multiSearch div table tr th:eq(1)").text("名称");
		}
		if(address=="reportFS54/aname"||address=="reportFS54/fwz"||address=="reportFS54/fwd"){
			$(".multiSearch .bianma").html("名称");
			$(".multiSearch div table tr th:eq(1)").text("名称");
		}
		if(address=="reportFS50/defalocation"||address=="reportFS50/accitype"||address=="reportFS50/productlife"||address=="reportFS50/attaburea"||address=="reportFS50/severity"){
			$(".multiSearch .bianma").html("名称");
			$(".multiSearch div table tr th:eq(1)").text("名称");
		}
		var lefts=$(this).offset().left-134;
		var tops=$(this).offset().top+30;
		if(lefts>1000){
			lefts=$(this).offset().left-300;
			$(".multiSearch").css({
				"left":lefts+"px",
				"top":tops+"px"
			});
		}else{
			lefts=$(this).offset().left-134;
			$(".multiSearch").css({
				"left":lefts+"px",
				"top":tops+"px"
			});
		}
		ajaxJson1(address,$(".multiSearch div:eq(1) input:eq(0)").val(),start,limit);
//		点击查询按钮,通过id和name紧凑型查询
		$(".searchOne").on("click",function(){
			ajaxJson1(address,$(".multiSearch .inp-id").val(),start,limit);
		});
		//多值查询传值
		var arrr=[],val="";
		$("#allCheck").off("click").on("click",function(){
			if(!$(this).prop("checked")){
				$(this).parents("table").find("tbody input").prop("checked",false);
				$.each(arrr,function(k,v){
					for(var i=0;i<$(".multiValInterval tbody tr").length;i++){
						val=$(".multiValInterval tbody tr:eq("+i+") td:eq(1)").text();
						if(v==val){
							arrr.splice(k,1);
						}
					}
				});
			}else{
				$(this).parents("table").find("tbody input").prop("checked",true);
				for(var i=0;i<$(".multiValInterval tbody tr").length;i++){
					arrr.push($(".multiValInterval tbody tr:eq("+i+") td:eq(1)").text());
				}
			}
		
		});
		$(".multiSearch").off("click").on("click",".check",function(){
			if($(this).prop("checked")==true){
				val=$(this).parent("td").next("td").text();
				arrr.push(val);
			}else if($(this).prop("checked")==false){
				val=$(this).parent("td").next("td").text();
				$.each(arrr,function(k,v){
					if(v==val){
						arrr.splice(k,1);
					}
				})
			}
		})
		//点击取消按钮,移除当前弹出框,解锁按钮		
		$(".cancel1").on("click",function(){
			$(this).parents(".multiSearch").remove();
			unlock();
		});
		//点击确定按钮,移除当前弹出框,并传值给界面查询框,解锁按钮
		$(".sure1").on("click",function(){
			var arr=[];
			var ele=$(this).parents('.multiSearch').find('tbody input:checked');
			ele.each(function(k,v){
				if($(v).hasClass('check')){
					var parent=$(v).parents('tr');
					arr.push(parent.find('td').eq(1).html());
				}
			});
			arr=arr.unique3();
			_this.prev("input").val(arr.join(','));
			$(this).parents(".multiSearch").remove();
			unlock();
		})
	});
}
//单值查询用id查询,前端分页
function multiSearch5(btn,address,start,limit){
	btn.off("click").on("click",function(){
		if(!window.lock2){
			return;
		}
		window.lock2=false;
		$("#Mask").show();
		var _this=$(this);
		$(this).css("background-color","#a40115");
		var str="<div class='intervalTwo' style='width:270px;z-index:40;'>"
			+"<div class='cancel2'></div>"
			+"<p><span>编码<input type='text' class='inp-id'></span><button class='searchOne'></button></p>"
            +"<div style='height:360px;overflow:auto;'>"
                +"<table border='1'>"
                	+"<thead>"
                        +"<tr>"
                            +"<th width='20px'></th>"
                            +"<th>编码</th>"
                        +"</tr>"
                    +"</thead>"
                    +"<tbody>"
                        
                    +"</tbody>"
                +"</table>"
            +"</div>"
            +"<p style='text-align: right;'>"
            +"<button class='spanPre'></button>"
        	+"<span class='spanTotalPage'></span>"
        	+"<button class='spanNext'></button>"
        	+"共<span class='spanPageNum'></span>页"
        	+"</p>"
            +"<p style='text-align:center;'><button class='cancel2'>取消</button><button class='sure2'>确定</button></p>"
        +"</div>";
		$("body").append(str);
		var lefts=$(this).offset().left-134;
		var tops=$(this).offset().top+30;
		if(lefts>1000){
			lefts=$(this).offset().left-300;
			$(".multiSearch").css({
				"left":lefts+"px",
				"top":tops+"px"
			});
		}else{
			lefts=$(this).offset().left-134;
			$(".multiSearch").css({
				"left":lefts+"px",
				"top":tops+"px"
			});
		}
		intervalAjax1(address,$(".intervalTwo p input:eq(0)").val(),start,limit);
//		点击查询按钮,通过id单值紧凑型查询
		$(".intervalTwo").on("click",".searchOne",function(){
			intervalAjax1(address,$(".intervalTwo .inp-id").val(),start,limit);
		});
		//点击取消按钮,移除当前弹出框,解锁按钮		
		$(".cancel2").on("click",function(){
			$(this).parents(".intervalTwo").remove();
			unlock();
		});
		//点击确定按钮,移除当前弹出框,并传值给界面查询框,解锁按钮
		$(".sure2").on("click",function(){
			var ele=$(".intervalTwo").find('tbody input:checked').parent("td").next("td").text();
			_this.prev("input").val(ele);
			$(this).parents(".intervalTwo").remove();
			unlock();
		})
	});
}
//多值查询权限获得条件,无分页,为下拉
function multiSearch2(btn,address,start,limit){
	btn.off("click").on("click",function(){
		if(!window.lock2){
			return;
		}
		window.lock2=false;
		$("#Mask").show();
		var _this=$(this);
		$(this).css("background-color","#a40115");
		var str="<div class='multiSearch' style='display:block;width:400px;'>"
					+"<div class='cancel1' style='margin-right:-10px;margin-bottom:5px;'></div>"
					+"<div style='height:400px;overflow:auto;margin-top: 15px;'>"
						+"<table class='multiValInterval' border='1'>"
							+"<thead>"
								+"<tr>"
									+"<th width='20px'><input type='checkbox' class='allCheck' id='allCheck'></th>"
		                            +"<th>编码</th>"
		                            +"<th>名称</th>"
		                        +"</tr>"
		                    +"</thead>"
		                    +"<tbody class='tbodyOneThird'>"
		                        
		                    +"</tbody>"
		                +"</table>"
		            +"</div>"
	                +"<p style='text-align: center'><button class='cancel1'>取消</button><button class='sure1'>确定</button></p>"
		        +"</div>";
		$("body").append(str);
		var lefts=$(this).offset().left-134;
		var tops=$(this).offset().top+30;
		if(lefts>1000){
			lefts=$(this).offset().left-300;
			$(".multiSearch").css({
				"left":lefts+"px",
				"top":tops+"px"
			});
		}else{
			lefts=$(this).offset().left-134;
			$(".multiSearch").css({
				"left":lefts+"px",
				"top":tops+"px"
			});
		}
		
		ajaxJson(address,"","",start,limit);
		
		checkAll($("#allCheck"));
//		点击取消移除当前显示框,解锁按钮
		$(".cancel1").on("click",function(){
			$(this).parents(".multiSearch").remove();
			$(".btnAll").css("background-color","#c70019");
			unlock();
		});
//		点击确定按钮,移除当前弹出框并将值传至上一页面
		$(".sure1").on("click",function(){
			var arr=[];
			var ele=$(this).parents('.multiSearch').find('tbody input:checked');
			ele.each(function(k,v){
				if($(v).hasClass('check')){
					var parent=$(v).parents('tr');
					arr.push(parent.find('td').eq(1).html());
				}
			});
			arr=arr.unique3();
			_this.prev("input").val(arr.join(','));
			$(this).parents(".multiSearch").remove();
			unlock();
		})
	});
}
/*//单值查询用id和name查询(权限条件)
//btn点击的按钮(object),address请求的地址(string),start从第几条开始(number),limit限制传过来几条数据(number)
function multiSearch7(btn,address,start,limit){
	btn.on("click",function(){
		var _this=$(this);
		if(!window.lock2){
			return;
		}
		window.lock2=false;
		$("#Mask").show();
		$(this).css("background-color","#a40115");
		var str2="<div class='intervalTwo' style='width:450px;z-index:40;'>"
					+"<div class='cancel2'></div>"
					+"<p></p>"
		            +"<div style='height:360px;overflow:auto;'>"
		                +"<table border='1'>"
		                	+"<thead>"
		                        +"<tr>"
		                            +"<th width='20px'></th>"
		                            +"<th>编码</th>"
		                            +"<th>名称</th>"
		                        +"</tr>"
		                    +"</thead>"
		                    +"<tbody>"
		                        
		                    +"</tbody>"
		                +"</table>"
		            +"</div>"
		            +"<p style='text-align: right;'>"
		            +"<button class='spanPre1'>上一页</button>"
		        	+"<button class='spanNext1'>下一页</button>"
		            +"</p>"
		            +"<p style='text-align:center;'><button class='cancel2'>取消</button><button class='sure2'>确定</button></p>"
		        +"</div>";
		$("body").append(str2);
		var lefts=$(this).offset().left-134;
		var tops=$(this).offset().top;
		$(".intervalTwo").css({
			"top":tops+40+"px",
			"left":lefts+"px"
		});
		//$("#Mask2").show();
		intervalAjax2(address,"","",start,limit);
		//pagination(address);
		$(".intervalTwo").on("click",".searchOne",function(){
			intervalAjax2(address,"","",start,limit);
			//pagination(address);
		})
		//点击确定按钮传值
		$(".sure2").on("click",function(){
			var ele=$(".intervalTwo").find('tbody input:checked').parent("td").next("td").text();
			_this.prev("input").val(ele);
			$(this).parents(".intervalTwo").remove();
			$("#Mask").hide();
			unlock();
		})
		//点击取消按钮   取消当前弹出框
		$(".cancel2").on("click",function(){
			$(this).parents(".intervalTwo").remove();
			$("#Mask").hide();
			unlock();
		});
	});
}

*/
//单值查询用id和name查询(可以进行模糊查询),后台分页
//btn点击的按钮(object),address请求的地址(string),start从第几条开始(number),limit限制传过来几条数据(number)
function multiSearch6(btn,address,start,limit){
	btn.on("click",function(){
		var _this=$(this);
		if(!window.lock2){
			return;
		}
		window.lock2=false;
		$("#Mask").show();
		$(this).css("background-color","#a40115");
		var str2="<div class='intervalTwo' style='width:450px;z-index:40;'>"
					+"<div class='cancel2'></div>"
					+"<p><span>编码<input type='text' class='inp-id'>名称<input type='text' class='inp-name'></span><button class='searchOne'></button></p>"
		            +"<div style='height:360px;overflow:auto;'>"
		                +"<table border='1'>"
		                	+"<thead>"
		                        +"<tr>"
		                            +"<th width='20px'></th>"
		                            +"<th>编码</th>"
		                            +"<th>名称</th>"
		                        +"</tr>"
		                    +"</thead>"
		                    +"<tbody>"
		                        
		                    +"</tbody>"
		                +"</table>"
		            +"</div>"
		            +"<p style='text-align: right;'>"
		            +"<button class='spanPre1'>上一页</button>"
		        	+"<button class='spanNext1'>下一页</button>"
		            +"</p>"
		            +"<p style='text-align:center;'><button class='cancel2'>取消</button><button class='sure2'>确定</button></p>"
		        +"</div>";
		$("body").append(str2);
		var lefts=$(this).offset().left-134;
		var tops=$(this).offset().top;
		$(".intervalTwo").css({
			"top":tops+40+"px",
			"left":lefts+"px"
		});
		//$("#Mask2").show();
		intervalAjax(address,$(".intervalTwo p input:eq(0)").val(),$(".intervalTwo p input:eq(1)").val(),start,limit);
		pagination(address);
		$(".intervalTwo").on("click",".searchOne",function(){
			intervalAjax(address,$(".intervalTwo .inp-id").val(),$(".intervalTwo .inp-name").val(),start,limit);
			pagination(address);
		})
		//点击确定按钮传值
		$(".sure2").on("click",function(){
			var ele=$(".intervalTwo").find('tbody input:checked').parent("td").next("td").text();
			_this.prev("input").val(ele);
			$(this).parents(".intervalTwo").remove();
			$("#Mask").hide();
			unlock();
		})
		//点击取消按钮   取消当前弹出框
		$(".cancel2").on("click",function(){
			$(this).parents(".intervalTwo").remove();
			$("#Mask").hide();
			unlock();
		});
	});
}
///
//多值查询用id和name查询(可以进行模糊查询),后台分页
//btn点击的按钮(object),address请求的地址(string),start从第几条开始(number),limit限制传过来几条数据(number)
function multiSearch7(btn,address,start,limit){
    btn.on("click",function(){
          var _this=$(this);
          if(!window.lock2){
                return;
          }
          window.lock2=false;
          $("#Mask").show();
          $(this).css("background-color","#a40115");
          var str="<div class='multiSearch' style='width:480px;display:block;'>"
                +"<div class='cancel1'></div>"
                +"<div>"
                      +"<p><span class='bianma'>编码</span><input type='text' class='inp-id'><span class='mingcheng'>名称</span><input type='text' class='inp-name'><button class='searchOne'></button></p>"
                      +"<table class='multiValInterval' border='1'>"
                            +"<thead>"
                                  +"<tr>"
                                        +"<th width='20px'><input type='checkbox' class='allCheck' id='allCheck'></th>"
                          +"<th>编码</th>"
                          +"<th>名称</th>"
                      +"</tr>"
                  +"</thead>"
                  +"<tbody class='tbodyOneThird'>"
                      
                  +"</tbody>"
              +"</table>"
          +"</div>"
          +"<p style='text-align: right;'>"
          +"<button class='spanPre1'>上一页</button>"
          +"<button class='spanNext1'>下一页</button>"
          +"</p>"
          +"<p style='text-align: center'><button class='cancel1'>取消</button><button class='sure1'>确定</button></p>"
      +"</div>";
          $("body").append(str);
          //按名称模糊查询
          var lefts=$(this).offset().left-134;
          var tops=$(this).offset().top+30;
          if(lefts>1000){
                lefts=$(this).offset().left-300;
                $(".multiSearch").css({
                      "left":lefts+"px",
                      "top":tops+"px"
                });
          }else{
                lefts=$(this).offset().left-134;
                $(".multiSearch").css({
                      "left":lefts+"px",
                      "top":tops+"px"
                });
          }
          //多值查询传值
          var arrr=[],val="";
          $("#allCheck").off("click").on("click",function(){
                if(!$(this).prop("checked")){
                      $(this).parents("table").find("tbody input").prop("checked",false);
                      $.each(arrr,function(k,v){
                            for(var i=0;i<$(".multiValInterval tbody tr").length;i++){
                                  val=$(".multiValInterval tbody tr:eq("+i+") td:eq(1)").text();
                                  if(v==val){
                                        arrr.splice(k,1);
                                  }
                            }
                      });
                }else{
                      $(this).parents("table").find("tbody input").prop("checked",true);
                      for(var i=0;i<$(".multiValInterval tbody tr").length;i++){
                            arrr.push($(".multiValInterval tbody tr:eq("+i+") td:eq(1)").text());
                      }
                }
          
          });
          $(".multiSearch").off("click").on("click",".check",function(){
                if($(this).prop("checked")==true){
                      val=$(this).parent("td").next("td").text();
                      arrr.push(val);
                }else if($(this).prop("checked")==false){
                      val=$(this).parent("td").next("td").text();
                      $.each(arrr,function(k,v){
                            if(v==val){
                                  arrr.splice(k,1);
                            }
                      })
                }
          })
          ajaxJson(address,$(".multiSearch div:eq(1) input:eq(0)").val(),$(".multiSearch div:eq(1) input:eq(1)").val(),start,limit);
          pagination(address);
          //          点击查询按钮,通过id和name紧凑型查询start,limit
          $(".multiSearch").on("click",".searchOne",function(){
                ajaxJson(address,$(".multiSearch .inp-id").val(),$(".multiSearch .inp-name").val(),start,limit);
                pagination(address);
          });
          //点击取消按钮,移除当前弹出框,解锁按钮         
          $(".cancel1").on("click",function(){
                $(this).parents(".multiSearch").remove();
                unlock();
          });
          //点击确定按钮,移除当前弹出框,并传值给界面查询框,解锁按钮
          $(".sure1").on("click",function(){
                arrr=arrr.unique3();
                _this.prev("input").val(arrr.join(','));
                //$('.dwerkValue').val(arrr.join(','));
                //$('.dwerkValue').val(arrr.join(','));
                //sernr
                $(this).parents(".multiSearch").remove();
                unlock();
          })
    });
}
//点击定确定或取消,所有查询按钮变回原色并解锁,这招层消失
function unlock(){
	$(".btnAll").css("background-color","#c70019");
	if(window.lock2){
		return;
	}
	window.lock2=true;
	$("#Mask").hide();
}
//全选
function checkAll(btn){
	btn.off("click").on("click",function(){
		if(!btn.prop("checked")){
			btn.parents("table").find("tbody input").prop("checked",false); 
		}else{
			btn.parents("table").find("tbody input").prop("checked",true); 
		}
	});	
}
function allClick(inp){
	var l = inp.length;
	var _this = this;
	inp.on("click",function(){
		_this.aa(l);
	});
}
allClick.prototype={
	aa:function(lengths){
		if(lengths==$(".multiValInterval tbody:eq(0) input:checked").length){
			$(".multiValInterval thead input").prop("checked",true);
		}else{
			$(".multiValInterval thead input").prop("checked",false);
		}
	}
}
//导出
function downloadFile(p_obj,p_url){
	var form=$("<form>");//定义一个form表单
	form.attr("style","display:none");
	form.attr("target","_self");
	form.attr("method","post");
	form.attr("action",p_url);
	$("body").append(form);//将表单放置在web中
	form.append("<input type='hidden' name='exportData' value='"+(new Date()).getMilliseconds()+"' />");
	for(var p in p_obj){  
		form.append("<input type='hidden' name='"+p+"' value='"+p_obj[p]+"' />");
  	}
	form.submit();//表单提交 
}
//导入
$("#upMb").on("click",function(){
    $(".importBox1").show();
    $("#Mask").show();
    $('#progress1 .bar1').css("width","0");
});
$(".importBox1 .closeX1").on("click",function(){
    $(".importBox1").hide();
    $("#Mask").hide();
    $(".downloadTable1").html("");
    $('#progress1 .bar1').css("width","0");
    $(".fileupload-file1").html("");
});
//导入
$(".import").on("click",function(){
	$(".importBox").show();
	$("#Mask").show();
	$('#progress .bar').css("width","0");
});
$(".importBox .closeX").on("click",function(){
	$(".importBox").hide();
	$("#Mask").hide();
	$(".downloadTable").html("");
	$('#progress .bar').css("width","0");
	$(".fileupload-file").html("");
});
//table隔行换色 兼容ie8
function trBg(table){
	var lengths=table.find("tr").length;
	for(var i=0;i<lengths;i++){
		if(i%2==0){
			table.find("tr").eq(i).css("background-color","#ffffff");
		}else if(i%2==1){
			table.find("tr").eq(i).css("background-color","#ededed");
		}
	}
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
 //物料编码,若将excel中的一列粘到input框中，失去焦点时将空格转换成逗号
 function blur(ele){
	 ele.on("input propertychange",function(){
		 if(ele.val().indexOf(" ")!=-1){
			 ele.val(ele.val().replace(/ /g,","));
		 }		 
	 })
 }
 blur($(".material"));
 //返回顶部
 function toTop(btn,divs){
 	 if(divs.scrollTop()>100){
 		 btn.css("visibility","visible");
 		 btn.off("click").on("click",function(){
 			 divs.animate({
 				 scrollTop:0
 			 },500); 
 		 });		 
 	 }else{
 		 btn.css("visibility","hidden"); 
 	 } 
 }	 
  //判断输入的是否含有中文逗号
 function contains(str, subStr, isIgnoreCase) {
 	 if (isIgnoreCase) {
 		 // 忽略大小写
 		 str = str.toLowerCase();
 		 subStr = subStr.toLowerCase();
 	 }
 	 var startChar = subStr.substring(0,1);
 	 var strLen = subStr.length;
 	 for (var j=0; j<str.length-strLen+1; j++) {
 		 if (str.charAt(j) == startChar) {
 			 /* 如果匹配起始字符,开始查找 */ 
 			 if (str.substring(j, j+strLen) == subStr) {
 				 /*如果从j开始的字符与 str 匹配 */
 				 return true; 
 			 }
 		 }
 	 }      
 	 return false;
  }
 $("input").on("input propertychange",function(){
 	var num=$(this).val();
 	if(num!=""){
 		if(contains(num,"，",true)){
 			alert("不能输入中文逗号");
 		}
 	}
 })


/**
 * 根据正则获取数据
 * @param add  传入的数组
 * @param str 需要匹配的字符串
 */
function regData(arr,str){
	var result = [];
	$.each(arr,function(key,val){
		//if(val.includes("*")){
        if(val.indexOf("*")!=-1){
			val = val.replace('*','.*?');
			var re = new RegExp("("+val+")(,|\])","g");
			var datas;
			while((datas = re.exec(str)) != null){
				result.push(datas[1]);
			}
		}else {
			result.push(val);
		}
	})

	return result;
}

/**
 * 判断用户输入是否在权限范围内
 * @param arr 用户输入数组
 * @param str 权限
 * @param 权限类别
 */
function compareInput(arr,str,type){
	var b = true;
	$.each(arr,function(key,val){
		if(str.indexOf(val)==-1){
			alert(type+" "+val+" 不在权限范围内!");
			b = false;
			return false;
		}
	})
	return b;
}

//合计部分左右变化
 $(".hj .lr").on("click",function(){
 	if($(this).text()=="<<"){
 		$(this).text(">>");
 		$(this).parent().animate({width:'20px'})
 		$(this).prev().css("display","none");
 	}else if($(this).text()==">>"){
 		$(this).text("<<");
 		$(this).parent().animate({width:'97.5%'})
 		$(this).prev().css("display","block");
 	}
 })


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

//文本框内只能输入整数
function CheckInputIntFloat(oInput)
{
    if('' != oInput.value.replace(/\d{1,}/,''))
    {
        oInput.value = oInput.value.match(/\d{1,}/) == null ? '' :oInput.value.match(/\d{1,}/);
    }
}


//判断是否为undefined
function CheckUndefined(m){
   if(m==undefined){
	   m="";
	   return m;
   }else{
	   return parseInt(m);
   }
}
//判断是否为null
function CheckNull(m){
   if(m==null){
	   m="";
	   return m;
   }else{
	   return m;
   }
}
//判断是否为空字符串
function checkStr(m){
	if(m==""){
		   return m;
   }else{
	   return Math.round(m);
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
 * 关闭弹窗
 * btn点击的关闭"×"按钮的class名,例".dele"
 * par点击的窗口的class名,例".xiazuan-2"
 * zhezhao 隐藏的朝朝层的class名,例".zhezhao1"
 * */
function dele(btn,par,zhezhao){
	$(btn).on("click",function(){
		$(this).parents(par).remove();
		$(zhezhao).hide();
	})
}
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