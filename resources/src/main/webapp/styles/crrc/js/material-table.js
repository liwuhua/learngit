// 将阿拉伯数字转换为中文
var _change = {
    ary0: ["零", "一", "二", "三", "四", "五", "六", "七", "八", "九"],
    ary1: ["", "十", "百", "千"],
    ary2: ["", "万", "亿", "兆"],
    init: function (name) {
        this.name = name;
    },
    strrev: function () {
        var ary = []
        for (var i = this.name.length; i >= 0; i--) {
            ary.push(this.name[i])
        }
        return ary.join("");
    }, //倒转字符串。
    pri_ary: function () {
        var $this = this
        var ary = this.strrev();
        var zero = ""
        var newary = ""
        var i4 = -1
        for (var i = 0; i < ary.length; i++) {
            if (i % 4 == 0) { //首先判断万级单位，每隔四个字符就让万级单位数组索引号递增
                i4++;
                newary = this.ary2[i4] + newary; //将万级单位存入该字符的读法中去，它肯定是放在当前字符读法的末尾，所以首先将它叠加入$r中，
                zero = ""; //在万级单位位置的“0”肯定是不用的读的，所以设置零的读法为空

            }
            //关于0的处理与判断。
            if (ary[i] == '0') { //如果读出的字符是“0”，执行如下判断这个“0”是否读作“零”
                switch (i % 4) {
                    case 0:
                        break;
                        //如果位置索引能被4整除，表示它所处位置是万级单位位置，这个位置的0的读法在前面就已经设置好了，所以这里直接跳过
                    case 1:
                    case 2:
                    case 3:
                        if (ary[i - 1] != '0') {
                            zero = "零"
                        }
                        ; //如果不被4整除，那么都执行这段判断代码：如果它的下一位数字（针对当前字符串来说是上一个字符，因为之前执行了反转）也是0，那么跳过，否则读作“零”
                        break;
                }
                newary = zero + newary;
                zero = '';
            }
            else { //如果不是“0”
                newary = this.ary0[parseInt(ary[i])] + this.ary1[i % 4] + newary; //就将该当字符转换成数值型,并作为数组ary0的索引号,以得到与之对应的中文读法，其后再跟上它的的一级单位（空、十、百还是千）最后再加上前面已存入的读法内容。
            }

        }
        if (newary.indexOf("零") == 0) {
            newary = newary.substr(1)
        }//处理前面的0
        return newary;
    }
}
//创建class类
function change() {
    this.init.apply(this, arguments);
}
change.prototype = _change;





// 表格内容获取
$("#submit").off("click").on("click", function () {
    $(".notSearch").empty();
    $(".Mask").show();
    var txt1 = $(".materialsCoding").val();
    var txt2 = $(".saleDepartment").val();
    var txt3 = $(".saleOrganization").val();
    var array = txt1.split(";");
    var reportJson = {};
    if (array[1] != null && array[1].trim() != "") {
        reportJson.matnrInterval = array[1].split(",");
    }
    if (array[0] != null && array[0].trim() != "") {
        reportJson.matnrValue = array[0].split(",");
    }
    if (txt2 != null && txt2.trim() != "") {
        reportJson.vkburValue = txt2.split(",");
    }
    if (txt3 != null && txt3.trim() != "") {
        reportJson.vkorgValue = txt3.split(",");
    }
    reportJson = JSON.stringify(reportJson);
    $(".ch-kp-bb tbody").not(".ch-kp-bb tbody:eq(0)").remove();

    this.ajaxRequest_ = $.ajax({
        url: ctx + "/crrc/reportFS00/result",
        data: reportJson,
        dataType: "json",
        contentType: "application/json;charset=utf-8",
        type: "post",
        success: function (data) {
            if ('300' === data.statusCode) {
                alert(data.message);
                return true;
            }
            if (jQuery.isEmptyObject(data)) {
                $(".ch-kp-bb").hide();
                $(".notSearch").text("未查询到数据");
            } else {
                $(".ch-kp-bb").show();
                $(".amountAll th:eq(1)").text(data.lfimgAll);
                $(".amountAll th:eq(2)").text(data.amountAll);
                $.each(data.vkorgMapList, function (k, v) {
                    var str1 = "", str2 = "", str21 = "", str22 = "", str3 = "", str4 = "";
                    var i = k + 1;
                    // 将阿拉伯数字转换为中文		 
                    //创建实例
                    var s = new change(i.toString());

                    // 阻止因为点击事件多次请求ajax
                    if (!!$(".ch-kp-bb tbody")[i]) {

                    } else {
                        // 添加部门
                        str1 = "<tbody>"
                                + "<tr><th style='width:5%;'>" + s.pri_ary() + "</th>"
                                + "<th colspan='2' style='width:55%;'>" + v.vkorgName + "</th>"
                                + "<th style='width:10%;'>" + v.lfimg + "</th>"
                                + "<th style='width:30%;'>" + v.amount + "</th></tr>"
                                + "</tbody>";
                        $(".table-body table").append(str1);
                        // 添加部门合同已签
                        if (v.augruTrue) {
                            str21 = "<tr class='finish'>"
                                    + "<td style='width:5%;'>（一）</td>"
                                    + "<td colspan='2' style='width:55%;'>合同已签</td>"
                                    + "<td style='width:10%;'>" + v.augruTrue.lfimg + "</td>"
                                    + "<td style='width:30%;'>" + v.augruTrue.amount + "</td>"
                                    + "</tr>";
                            $(".ch-kp-bb tbody:last").append(str21);
                            $.each(v.augruTrue.matnrMapList, function (m, n) {
                                str3 += "<tr>"
                                        + "<td style='width:5%;'></td>"
                                        + "<td style='width:10%;'>" + n.matnrNum + "</td>"
                                        + "<td style='width:45%;'>" + n.matnrName + "</td>"
                                        + "<td style='width:10%;'>" + n.lfimg + "</td>"
                                        + "<td style='width:30%;'>" + n.amount + "</td>"
                                        + "</tr>";
                            });
                            $(".finish").after(str3);
                        }

                        // 添加部门合同未签
                        if (v.augruFalse) {
                            str22 = "<tr class='unfinish'>"
                                    + "<td style='width:5%;'>（二）</td>"
                                    + "<td colspan='2' style='width:55%;'>合同未签</td>"
                                    + "<td style='width:10%;'>" + v.augruFalse.lfimg + "</td>"
                                    + "<td style='width:30%;'>" + v.augruFalse.amount + "</td>"
                                    + "</tr>";
                            $(".table-body table").append(str22);
                            $.each(v.augruFalse.matnrMapList, function (m, n) {
                                str4 += "<tr>"
                                        + "<td style='width:5%;'></td>"
                                        + "<td style='width:10%;'>" + n.matnrNum + "</td>"
                                        + "<td style='width:45%;'>" + n.matnrName + "</td>"
                                        + "<td style='width:10%;'>" + n.lfimg + "</td>"
                                        + "<td style='width:30%;'>" + n.amount + "</td>"
                                        + "</tr>";
                            });
                            $(".unfinish").after(str4);

                        }
                    }
                });
            }
            $(".Mask").hide();
        },
        error: function () {
            $(".ch-kp-bb").hide();
            alert("error");
        }
    });
    $(".codingOne").val("");
    $(".inpOne").val("");
    $(".inpTwo").val("");
    $(".codingTwo").val("");
    $(".codingThree").val("");

//	return false;

});

