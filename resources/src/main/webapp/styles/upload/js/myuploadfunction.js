$(function () {
    var iframe = false;
    //以下进行测试
    if (Browser.ie === "8.0" || Browser.ie === "9.0") {
        iframe = true;
    }
    $('#fileupload1').off("click").on("click",function(){
        $(".downloadTable1").html("");
        $(".fileupload-file1").html("");
        $('#progress1 .bar1').css('width',"0");
    });
    $('#fileupload1').fileupload({
        dataType: 'json',
        //forceIframeTransport: forceIframeTransport,
        iframe: iframe,
        done: function (e, data) {
            $(".downloadTable1").html("");
            var str = "";
            $(".fileupload-file1").html(data.files[0].name);
            if ('300' === data._response.result.statusCode) {
                $(".downloadTable1").html(data._response.result.message);
                return true;
            }
             if (data._response.result.SUCCESSES) {
                str+="<p>" +
                    "<span>" +data._response.result.SUCCESSES+ "</span>" +
                    "</p>";
            }
              if (data._response.result.ERROR) {
                str+="<p>" +
                    "<span>" +data._response.result.ERROR+ "</span>" +
                    "</p>";
            }

            $(".downloadTable1").html(str);
        },
        progressall: function (e, data) {
            var progress = parseInt(data.loaded / data.total * 100, 10);
            $('#progress1 .bar1').css('width',progress + '%');
        }
    });
    $('#fileupload').off("click").on("click",function(){
    	$(".downloadTable").html("");
    	$(".fileupload-file").html("");
    	$('#progress .bar').css('width',"0");
    });
    $('#fileupload').fileupload({
        dataType: 'json',
        //forceIframeTransport: forceIframeTransport,
        iframe: iframe,
        done: function (e, data) {
            $(".downloadTable").html("");
            var str = "";
            $(".fileupload-file").html(data.files[0].name);
            if ('300' === data._response.result.statusCode) {
                $(".downloadTable").html(data._response.result.message);
                return true;
            }  
            if (data._response.result.SUCCESSES) {
               str+="<p>" +
           				"<span>" +data._response.result.SUCCESSES+ "</span>" +
           			"</p>";
            }
            if (data._response.result.ERRORCOMPANEY) {
               str+="<p>" +
	  					"<span>" +data._response.result.ERRORCOMPANEY+ "</span>" +
	       			"</p>";
            }
            if (data._response.result.ERRORCOMPANY) {
                str+="<p>" +
 	  					"<span>" +data._response.result.ERRORCOMPANY+ "</span>" +
 	       			"</p>";
             }
            if (data._response.result.ERRORTIME) {
                str+="<p>" +
	  					"<span>" +data._response.result.ERRORTIME+ "</span>" +
	       			"</p>";
            }
            if (data._response.result.ERRORCELLS) {
                str+="<p>" +
	  					"<span>" +data._response.result.ERRORCELLS+ "</span>" +
	       			"</p>";
            }
            if(data._response.result.ERROR1){
        		$.each(data._response.result.ERROR1,function(k,v){
        			str += "<p>" +
		                "<span>" + v + "</span>" +
		            "</p>"
        		});
        	}
            if (data._response.result.ERROR) {
            	if (data._response.result.ERROR.ERRORTIME) {
                    str+="<p>" +
    	  					"<span>" +data._response.result.ERROR.ERRORTIME+ "</span>" +
    	       			"</p>";
                }
            	if (data._response.result.ERROR.ERRORCOMPANEY) {
                    str+="<p>" +
     	  					"<span>" +data._response.result.ERROR.ERRORCOMPANEY+ "</span>" +
     	       			"</p>";
                 }
            	if (data._response.result.ERROR.ERRORCOMPANY) {
                    str+="<p>" +
     	  					"<span>" +data._response.result.ERROR.ERRORCOMPANY+ "</span>" +
     	       			"</p>";
                 }
            	if (data._response.result.ERROR.ERRORCELLS) {
                    str+="<p>" +
    	  					"<span>" +data._response.result.ERROR.ERRORCELLS+ "</span>" +
    	       			"</p>";
                }
            	if (data._response.result.ERROR.ERRORDATA) {
                    $.each(data._response.result.ERROR.ERRORDATA, function (k, v) {
                        str += "<p>" +
                                	"<span>" + v+ "</span>" +
                                "</p>"
                    });
                }
            	if(data._response.result.ERROR.ERROR0){
            		for(var i=0;i<data._response.result.ERROR.ERROR0.length;i++){
                    	str += "<p>" +
    				                "<span>第" + data._response.result.ERROR.ERROR0[i].row + "行</span>" +
    				                "<span>第" + data._response.result.ERROR.ERROR0[i].column + "列格式错误</span>" +
    				            "</p>"
                    }
            	}
            	
            	
            	
            }
            if (data._response.result.ERRORDATA) {
                $.each(data._response.result.ERRORDATA, function (k, v) {
                    str += "<p>" +
                            	"<span>" + v+ "</span>" +
                            "</p>"
                });
            }
            $(".downloadTable").html(str);
        },
        progressall: function (e, data) {
            var progress = parseInt(data.loaded / data.total * 100, 10);
            $('#progress .bar').css('width',progress + '%');
        }
    });
});