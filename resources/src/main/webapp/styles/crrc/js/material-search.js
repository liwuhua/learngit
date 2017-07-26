var data = [];
var lock2 = true;
//点击全选，所有的复选框被选中
function allCheck(btn) {
    btn.on("click", function () {
        if (!btn.prop("checked")) {
            btn.parents("table").find("tbody input").prop("checked", false);
        } else {
            btn.parents("table").find("tbody input").prop("checked", true);
        }
    });
}
allCheck($(".allCheck"));
function Allclick(inp) {
    var l = inp.length;
    var _this = this;
    inp.on("click", function () {
        _this.aa(l);
    });
}
Allclick.prototype = {
    aa: function (lengths) {
        if (lengths == $(".multiValInterval tbody:eq(0) input:checked").length) {
            $(".multiValInterval tbody:eq(1) input").prop("checked", true);
        } else {
            $(".multiValInterval tbody:eq(1) input").prop("checked", false);
        }
    }
}
function pagination(url) {
    $(".spanPre").attr("disabled", "disabled");
    $(".spanPre").css("background-color", "#ccc");
//	console.log($(".multiValInterval tbody:eq(0) tr").length)
//	if($(".multiValInterval tbody:eq(0) tr").length<10){
//		$(".spanNext").attr("disabled","disabled");
//		$(".spanNext").css("background-color","#ccc");
//	}else{
//		$(".spanNext").prop("disabled",false);
//		$(".spanNext").css("background-color","#fff");
//	}
    var i = 0;

    $(".spanNext").off("click").on("click", function () {
        i++;
        if (!window.lock) {
            return
        }
        $(".spanPre").prop("disabled", false);
        $(".spanPre").css("background-color", "#fff");
        ajaxJson(url, $(".multiBoxThird div:eq(0) input:eq(0)").val(), $(".multiBoxThird div:eq(0) input:eq(1)").val(), i * 10, 10);

    });
    $(".spanPre").off("click").on("click", function () {
        i--;

        if (!window.lock) {
            return
        }
        ajaxJson(url, $(".multiBoxThird div:eq(0) input:eq(0)").val(), $(".multiBoxThird div:eq(0) input:eq(1)").val(), i * 10, 10);


        if (i == 1) {
            $(".spanPre").attr("disabled", "disabled");
            $(".spanPre").css("background-color", "#ccc");
        }
    })
}
//封装点击显示隐藏
function Click(btn, divs) {
    btn.off("click").on("click", function () {

//		console.log($(".tabChoice .cancel"));

        if ($(this).closest("div").parent().hasClass('tabChoice')) {
            $(".multiBoxThird").find("table tbody").eq(0).removeClass().addClass("tbodyOneThird").empty();
            $(".multiBoxThird").find("button").eq(0).removeClass().addClass("searchOneThird");
            $(".multiBoxThird").find("table tbody").eq(1).find("input").prop("checked", false);

            $(".searchOneThird").off("click").on("click", function () {
                window.lock = true;
                ajaxJson("tmaterial", $(".multiBoxThird div:eq(0) input:eq(0)").val(), $(".multiBoxThird div:eq(0) input:eq(1)").val(), 0, 10);
                pagination("tmaterial");
//				ajaxJson("tmaterial",$(".multiBoxThird div:eq(0) input:eq(0)").val(),$(".multiBoxThird div:eq(0) input:eq(1)").val(),0,null);

            })
        } else if ($(this).closest("div").parent().hasClass('maskBox')) {
            $(".multiBoxThird").find("table tbody").eq(0).removeClass().addClass("tbodyTwoThird").empty();
            $(".multiBoxThird").find("button").eq(0).removeClass().addClass("searchTwoThird");
            $(".multiBoxThird").find("table tbody").eq(1).find("input").prop("checked", false)
            $(".searchTwoThird").off("click").on("click", function () {
                ajaxJson("tsalesoff", $(".multiBoxThird div:eq(0) input:eq(0)").val(), $(".multiBoxThird div:eq(0) input:eq(1)").val(), 0, 10);
                pagination("tsalesoff");

            })
        } else if ($(this).closest("div").parent().hasClass('maskBox1')) {
            $(".multiBoxThird").find("table tbody").eq(0).removeClass().addClass("tbodyThreeThird").empty();
            $(".multiBoxThird").find("button").eq(0).removeClass().addClass("searchThreeThird");
            $(".multiBoxThird").find("table tbody").eq(1).find("input").prop("checked", false)
            $(".searchThreeThird").off("click").off("c").on("click", function () {
                ajaxJson("tsalesorg", $(".multiBoxThird div:eq(0) input:eq(0)").val(), $(".multiBoxThird div:eq(0) input:eq(1)").val(), 0, 10);
                pagination("tsalesorg");
            })
        } else {
            if (!window.lock2) {
                return;
            }
            window.lock2 = false;
        }


        if (divs.css("display") == "none") {
            divs.show();
        } else {

            divs.hide();
        }
        if ($(".multiBoxThird").css("display") == "block") {
            $(".tabChoice .cancel").attr("disabled", "disabled");
            $(".tabChoice .sureBtn").attr("disabled", "disabled");
            $(".multiBox .cancel").attr("disabled", "disabled");
            $(".multiBox .sureBtn").attr("disabled", "disabled");
        }

    })
}
Click($(".btnInterval1"), $(".tabMask"))
Click($(".btnOneSecond"), $(".multiBoxThird"))
Click($(".btn1"), $(".maskBox"))
Click($(".btnTwoSecond"), $(".multiBoxThird"))
Click($(".btn2"), $(".maskBox1"))
Click($(".btnThreeSecond"), $(".multiBoxThird"))
//点击取消按钮消失
function Cancel(btn) {
    btn.on("click", function () {
        if ($(this).parents('.firstBox').length) {
            window.lock2 = true;
        }
        $(".tabChoice .cancel").prop("disabled", false);
        $(".tabChoice .sureBtn").prop("disabled", false);
        $(".multiBox .cancel").prop("disabled", false);
        $(".multiBox .sureBtn").prop("disabled", false);
        $(this).closest("div").parent("div").hide();
        window.lock = false;
    })
}
Cancel($(".cancel"))


//物料点击第二个弹出框的查询按钮，相应的编码和名称出来
// function Materials(btn,url){
// 	btn.on("click",function(){
// 		$(this).closest("div").find("tbody:first").empty();
// 		$.ajax({
// 			url:url, 
// 			dataType:"json",
// 			type:"get",
// 			success:function(data){
// 				$(".tbodyOneThird").empty(); 
// 				getNumber(data);
// 			},
// 			error:function(){
// 				console.log("error")
// 			}
// 		})
// 	})
// }
// Materials($(".searchOneThird"),"data/data2.json");

// function getNumber(data){
// 	var str=""
// 	$.each(data,function(key,val){
// 		//console.log(val.list)
// 		$.each(val.list,function(k,v){
// 			//console.log(v)
// 			str+="<tr>"
// 					+"<td style='width: 70px'><input type='checkbox' class='check'></td>"
//                     +"<td>"+v.material+"</td>"                       
//                     +"<td>"+v.txtmd+"</td>"                        
//                 +"</tr>" ;             
// 		})
// 	})
// 	$(".tbodyOneThird").append(str);

// }







//点击确定按钮，将值传到上级页面
function Sure(btn, divss) {
    btn.on("click", function () {
        $(".tabChoice .cancel").prop("disabled", false);
        $(".tabChoice .sureBtn").prop("disabled", false);
        $(".multiBox .cancel").prop("disabled", false);
        $(".multiBox .sureBtn").prop("disabled", false);
        var arr = [];
        window.lock = false;
        var ele = btn.parents('.multiBoxThird').find('tbody input:checked');
        ele.each(function (k, v) {
            if ($(v).hasClass('check')) {
                var parent = $(v).parents('tr');
                arr.push({
                    code: parent.find('td').eq(1).html(),
                    name: parent.find('td').eq(2).html(),
                })
            }

        })

        data = arr;
        data1 = arr;
        data2 = arr;
        Render(btn);
        $(this).closest("div").parent("div").hide();
    })
}
Sure($('.multiBoxThird').find(".sure"));
//Sure($('.tabChoice').find(".sure"));
function Render(btn) {
    var arr1 = [], arr2 = [], arr3 = [];
    if (btn.parents('.multiBoxThird').find("tbody").hasClass("tbodyOneThird")) {
        $.each(data, function (k, v) {
            arr1.push(v.code)
        });
//		console.log(arr1.join(','));
        if ($('.codingOne').val() == "") {
            $('.codingOne').val(arr1.join(','));
        } else if ($('.codingOne').val() != "") {
            $('.codingOne').val($('.codingOne').val() + "," + arr1.join(','))
        }

    } else if (btn.parents('.multiBoxThird').find("tbody").hasClass("tbodyTwoThird")) {
        $.each(data1, function (k, v) {
            arr2.push(v.code)
        });
        if ($('.codingTwo').val() == "") {
            $('.codingTwo').val(arr2.join(','));
        } else if ($('.codingTwo').val() != "") {
            $('.codingTwo').val($('.codingTwo').val() + "," + arr2.join(','));
        }

    } else if (btn.parents('.multiBoxThird').find("tbody").hasClass("tbodyThreeThird")) {
        $.each(data2, function (k, v) {
            arr3.push(v.code)
        })
        if ($('.codingThree').val() == "") {
            $('.codingThree').val(arr3.join(','));
        } else if ($('.codingThree').val() != "") {
            $('.codingThree').val($('.codingThree').val() + "," + arr3.join(','));
        }

    }
}

// 多区间查询
function intervalAjax() {
    $.ajax({
        async: false,
        url: ctx + "/crrc/tmaterial?",
        dataType: "json",
        type: "get",
        success: function (data) {
            if ('300' === data.statusCode) {
                alert(data.message);
                return true;
            }
            $.each(data, function (k, v) {
                var i = k + 1;
                var str = "";
                if (!!$(".intervalTwo table tr")[i]) {

                } else {
                    // 添加信息
                    str += "<tr>"
                            + "<td><input type='radio' name='intervalTwo'></td>"
                            + "<td>" + v.material + "</td>"
                            + "<td>" + v.txtmd + "</td>"
                            + "</tr>";
                    $(".intervalTwo table tr:last").after(str);
                }
            });
        },
        error: function () {
            alert("error")
        }
    });
}
$(".intervalBox button").off("click").on("click", function () {
    $(".intervalTwo").css({"display": "block"});
    $(".tabChoice .cancel").attr("disabled", "disabled");
    $(".tabChoice .sureBtn").attr("disabled", "disabled");
    var _this = $(this);
    intervalAjax();
    $(".intervalTwo .sure").off("click").on("click", function () {
        $(".intervalTwo").css({"display": "none"});
        $(".tabChoice .cancel").prop("disabled", false);
        $(".tabChoice .sureBtn").prop("disabled", false);
        var val1 = $(".intervalTwo table input:checked").parent().next().text();
        _this.prev().val(val1);
    });
});

//点击一级弹出框里的确定按钮，将编码值传导一级页面
$(".sureBtn").off("click").on("click", function () {
    var txt1 = $('.codingOne').val();
    var txt2 = $('.codingTwo').val();
    var txt3 = $(".codingThree").val();
    var arr1 = txt1.split(",").unique3();
    txt1 = arr1.join(",");
    var arr2 = txt2.split(",").unique3();
    txt2 = arr2.join(",");
    var arr3 = txt3.split(",").unique3();
    txt3 = arr3.join(",");
    $(".saleDepartment").val(txt2);
    $(".saleOrganization").val(txt3);
    window.lock2 = true;
    if ($(this).closest("div").parent().hasClass('tabMask')) {
        $(".tabMask").hide();

    } else if ($(this).closest("div").parent().hasClass('maskBox')) {
        $(".maskBox").hide();

    } else if ($(this).closest("div").parent().hasClass('maskBox1')) {
        $(".maskBox1").hide();

    }

    var txt4 = $(".inpOne").val();
    var txt5 = $(".inpTwo").val();
    if (txt1 == "" && txt4 != "" && txt5 != "") {
        $(".materialsCoding").val(txt4 + ":" + txt5);
    } else if (txt1 != "" && (txt4 == "" || txt5 == "")) {
        $(".materialsCoding").val(txt1);
    } else if (txt1 == "" && txt4 == "" && txt5 == "") {
        $(".materialsCoding").val("");
    } else {
        $(".materialsCoding").val(txt1 + ";" + txt4 + ":" + txt5);
    }
})
//点击多值和区间其内容发生变化
function Change(choice) {
    choice.on("click", function () {
        var ind = $(this).index();
        $(this).addClass("bg").siblings().removeClass("bg");
        $(this).closest("div").children("div").eq(0).find("div").eq(ind).show().siblings("div").hide();
    })
}
Change($(".tabMask li"));



//重置所有的弹出框也消失
$("#resetBtn").on('click', function () {
    $('.firstBox').hide();
    $('.multiBoxThird').hide();
    $(".intervalTwo").hide();
    $(".codingOne").val("");
    $(".codingTwo").val("");
    $(".codingThree").val("");
    $(".inpTwo").val("");
    $(".inpOne").val("");
    window.lock2 = true;
    $(".tabChoice .cancel").prop("disabled", false);
    $(".tabChoice .sureBtn").prop("disabled", false);
    $(".multiBox .cancel").prop("disabled", false);
    $(".multiBox .sureBtn").prop("disabled", false);
})