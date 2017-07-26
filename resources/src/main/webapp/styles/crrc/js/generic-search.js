//url通用处理方法
function doUrl(url, key, value) {
    if (url.charAt(url.length - 1) == "?" && value != null) {
        url = url + key + "=" + value;
    } else if (url.charAt(url.length - 1) != "?" && value != null) {
        url = url + "&" + key + "=" + value;
    }
    return url;
}

function ajaxJson(address, id, name, start, limit) {
    if (id.trim() == "") {
        id = null;
    }
    if (name.trim() == "") {
        name = null;
    }
    if (start == null) {
        start = 0;
    }
    if (limit == null) {
        limit = 10;
    }
    var url = ctx + "/crrc/" + address + "?";
    url = doUrl(url, "id", id);
    url = doUrl(url, "name", name);
    url = doUrl(url, "start", start);
    url = doUrl(url, "limit", limit);


    $.ajax({
        url: url,
        dataType: "json",
        type: "get",
        success: function (data) {
            if ('300' === data.statusCode) {
                alert(data.message);
                return true;
            }
            var str = "";
            $.each(data, function (k, v) {
                var arr = new Array;
                for (value in v) {
                    arr.push(v[value]);
                }
                str += "<tr>"
                        + "<td style='width: 70px'><input type='checkbox' class='check' name='della'></td>"
                        + "<td>" + arr[0] + "</td>"
                        + "<td>" + arr[1] + "</td>"
                        + "</tr>";
            })
            $(".multiValInterval tbody:eq(0)").html(str);
            // 全选
            var Allclick1 = new Allclick($(".multiValInterval tbody:eq(0) input"));
        },
        error: function () {
            alert("error")
        }
    })
}
//数组去重
Array.prototype.unique3 = function () {
    var res = [];
    var json = {};
    for (var i = 0; i < this.length; i++) {
        if (!json[this[i]]) {
            res.push(this[i]);
            json[this[i]] = 1;
        }
    }
    return res;
}