/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var Browser = {};
var ua = navigator.userAgent.toLowerCase();
if (window.ActiveXObject) {
    Browser.ie = ua.match(/msie ([\d.]+)/)[1]
}else if("ActiveXObject" in window){//IE11
    Browser.ie = "11.0";
}else if (ua.indexOf('firefox')) {
    Browser.firefox = true;
} else if (ua.indexOf('chrome')) {
    Browser.chrome = true;
} else if (ua.indexOf('opera')) {
    Browser.opera = true;
}
//以下进行测试
if (Browser.ie === "8.0" || Browser.ie === "9.0" || Browser.ie === "10.0") {
    css_load();
    document.write('<script src=' + ctx + '/styles/jquery/jquery-1.9.1.min.js type="text/javascript"></script>');
    //<script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    //<script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
}
//以下进行测试
if (Browser.ie === "11.0") {
    css_load();
    document.write('<script src=' + ctx + '/styles/common/js/jquery.min.js type="text/javascript"></script>');
}
if (Browser.firefox) {
    css_load();
    document.write('<script src=' + ctx + '/styles/common/js/jquery.min.js type="text/javascript"></script>');
}
if (Browser.chrome) {
    css_load();
    document.write('<script src=' + ctx + '/styles/common/js/jquery.min.js type="text/javascript"></script>');
}
if (Browser.opera) {
    css_load();
    document.write('<script src=' + ctx + '/styles/common/js/jquery.min.js type="text/javascript"></script>');
}

function css_load(){
    document.write('<link rel="stylesheet" href="' + ctx + '/styles/common/css/morris.css">');
    document.write('<link rel="stylesheet" href="' + ctx + '/styles/common/css/common.css">');
    document.write('<link href="' + ctx + '/styles/common/css/bootstrap.min.css" rel="stylesheet" type="text/css" />');
    document.write('<link href="' + ctx + '/styles/common/css/menu.css" rel="stylesheet" type="text/css" />');
//    document.write('<link href="' + ctx + '/styles/common/css/core.css" rel="stylesheet" type="text/css" />');
    document.write('<link href="' + ctx + '/styles/common/css/components.css" rel="stylesheet" type="text/css" />');
    document.write('<link href="' + ctx + '/styles/common/css/icons.css" rel="stylesheet" type="text/css" />');
}
