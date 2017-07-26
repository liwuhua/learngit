


/*function getFlagDayTimes(start, end,num) {
	var flagDayTimes = Array();
	var hourNum = GetDateDiff(end, start,"hour");
	var spacing = (hourNum - hourNum % num) / num;
	for(var i=0; i<=hourNum; i++){
		if(i%spacing===0){
			flagDayTimes.push(addHours(start,i));
		}
	}
	return flagDayTimes;
}*/
/* 
* 获得查询时间段的所有数据点
* 格式:2012-12-02 10
* 返回精度为：周，小时，天
*/
function getAllDimtimes(start, num,type){
	var res = Array();
	for(var i=0; i<=num; i++){	
			res.push(addTimeByType(start,i,type));
	}
	return res;
}


//日期(格式:2012-12-02 10)加上小时后的新日期.
/*function addHours(dateTime, hours) {
	var hour = 60 * 60 * 1000;
	var timeArr=dateTime.split(" ");
	var dt=getDateTime(dateTime)+hour*hours;
	dt = new Date(dt);
	var y = dt.getFullYear();
	var m = dt.getMonth() + 1;
	var d = dt.getDate();
	var h = dt.getHours();
	if (m <= 9)
		m = "0" + m;
	if (d <= 9)
		d = "0" + d;
	var cdate = y + "-" + m + "-" + d;
	
	if(timeArr.length==2){
		if (h <= 9)
			h = "0" + h;
		cdate = cdate+" "+h;
	}
	return cdate;
}*/
/* 
* 获得时间差
* 格式:2012-12-02 10 or 2012-12-02
* 返回精度为：周，小时，天
*/
function getFlags(start,end,type,difNum,num) {
	var res = Array();
/*	var difNum = GetDateDiff(end, start,type);
	difNum=(difNum - difNum % 1) / 1;
	if(difNum<1){
		difNum=1;
	}*/
	var spacing= (difNum - difNum % num) / num;
	
	//alert(spacing);
	for(var i=0; i<=difNum; i++){
		if(i%spacing==0){
			res.push(addTimeByType(start,i,type));
		}
	}
	return res;
}
/* 
* 获取时间值 
* 格式:2012-12-02 10 
* 返回精度为： 小时 
*/
function getDateTime(d){
	var timeArr=d.split(" ");
	var dateArr=timeArr[0].split("-");
	var dt=new Date();
	dt.setFullYear(dateArr[0], dateArr[1] - 1, dateArr[2]);
	if(timeArr.length==2){
		dt.setHours(timeArr[1]);
	}
	return dt.getTime();
}

/* 
* 获得时间+num后的时间格式
* 格式:2012-12-02 10 or 2012-12-02
* 返回精度为：周，小时，天
*/
function addTimeByType(dateTime,num,type) {
	 switch (type) {
    
      case "day":
    	  
          var divNum = 1000 * 3600 * 24;
          var date = new Date(dateTime.replace(/-/g,"\/"));
          //alert(date);
          var dt=date.getTime()+divNum*num;
        	var ndate = new Date(dt);
        	
        	var y = ndate.getFullYear();
          	var m = ndate.getMonth() + 1;
          	var d = ndate.getDate();
          	if (m <= 9)
          		m = "0" + m;
        	if (d <= 9)
          		d = "0" + d;
        	var cdate = y + "-" + m + "-" + d;
        	//alert(cdate);
        	return cdate;
          break;
        //格式:2012-12-02
      case "week":
    	  var date = new Date(dateTime.replace(/-/g,"\/"));
    	  var  divNum = 1000 * 3600 * 24 * 7;
      	var dt=date.getTime()+divNum*num;
      	var ndate = new Date(dt);
      	var y = ndate.getFullYear();
      	/*var m = ndate.getMonth() + 1;
      	if (m <= 9)
      		m = "0" + m;*/
      	//alert(y + "-" + m );
      	//alert(y  + "第"+ndate.getWeekOfYear()+"周");
      	return y  + "第"+ndate.getWeekOfYear()+"周";
      	
          break;
      //格式:2012-12-02 10
      case "hour":
    	
    	  var divNum = 1000 * 3600;
          var timeArr=dateTime.split(" ");
      	var dt=getDateTime(dateTime)+divNum*num;
      	dt = new Date(dt);
      	var y = dt.getFullYear();
      	var m = dt.getMonth() + 1;
      	var d = dt.getDate();
      	var h = dt.getHours();
      	if (m <= 9)
      		m = "0" + m;
      	if (d <= 9)
      		d = "0" + d;
      	var cdate = y + "-" + m + "-" + d;
      	
      	if(timeArr.length==2){
      		if (h <= 9)
      			h = "0" + h;
      		cdate = cdate+" "+h;
      	}
        //alert(cdate);
      	return cdate;
          break;
     
      default:
          break;
  }
}

/* 
* 获得时间差
* 格式:2012-12-02 10
* 返回精度为：年，月，周，小时，天
*/
function GetDateDiff(startTime, endTime, diffType) {
   
    //作为除数的数字
    var divNum = 1;
    switch (diffType) {
       case "year":
    	    var startyear=parseInt(startTime.split("-")[0]);
    	    var endyear=parseInt(endTime.split("-")[0]);
    	    var years=startyear-endyear;
    	    //不足一个数据量的，补足1
    		if(years < 1){
    			years = 1;
    		}
    	    return years;
    	    break;
       case "month":
    	    var m1 = parseInt(startTime.split("-")[1].replace(/^0+/, "")) + parseInt(startTime.split("-")[0]) * 12; 
    	    var m2 = parseInt(endTime.split("-")[1].replace(/^0+/, "")) + parseInt(endTime.split("-")[0]) * 12; 
    	    var m3=m1-m2;
    	    //不足一个数据量的，补足1
    		if(m3 < 1){
    			m3 = 1;
    		}
    	     return m3;
            break;
        case "day":
            divNum = 1000 * 3600 * 24;
            break;
        case "week":
            divNum = 1000 * 3600 * 24 * 7;
            break;
        //两个日期的小时差值(d1 - d2).格式:2012-12-02 10
        case "hour":
            divNum = 1000 * 3600;
            break;
       
        default:
            break;
    }
    
	try {
		var difnum = (getDateTime(startTime) - getDateTime(endTime)) / divNum;
		//不足一个数据量的，补足1
		if(difnum < 1){
			difnum = 1;
		}
		//去除小数
		difnum=(difnum - difnum % 1) / 1;
		return difnum;
	} catch (e) {
		return false;
	}
}

/* 
* 获得时间差 年
* 格式:2012-12-02
* 返回:year数组
*/
function GetYearDiff(startTime,cntyear,num) {
	var years = Array();
	var spacing = (cntyear - cntyear % num) / num;
	//alert(spacing);
	
	for(var i=0; i<=cntyear; i++){
		//alert(i);
		if(i%spacing==0){
			years.push(startTime+i);
		}
		
		
	}
	return years;
}
/* 
* 获得时间差 月
* 格式:2012-12
* 返回:moth数组
*/
function GetMonthDiff(startTime, endTime,cntmonth,num) {
	var months = Array();
	
	//var startyear=parseInt(startTime.split("-")[0]);
	//var endyear=parseInt(endTime.split("-")[0]);
	//var startmonth=parseInt(startTime.split("-")[1].replace(/^0+/, ""));
	//var endmonth=parseInt(startTime.split("-")[1].replace(/^0+/, ""));
	var spacing = (cntmonth - cntmonth % num) / num;
	var date = new Date(startTime.replace(/-/g,"\/"));
	//alert(spacing);
	for(var i=0; i<=cntmonth; i++){
		//alert(i%spacing);
		if(i%spacing==0){
			
			//alert(date.getMonth());
			date.setMonth(date.getMonth() + spacing);
			if(date>=endTime){
				months.push(endTime);
			}else{
			//alert(date.getFullYear()+"0"+date.getMonth());
			var y = date.getFullYear();
			var m = date.getMonth() ;
			
			if (m <= 9)
				m = "0" + m;
			
			var cdate = y + "-" + m;
			//alert(cdate);
	        months.push(cdate);
			}
		}
		
	}
	return months;
}
/* 
* 获得查询时间段的所有数据点
* 格式:2012-12
* 返回精度为：月
*/
function getMonthDimtimes(start,endTime, num){
	var months = Array();
	var date = new Date(start.replace(/-/g,"\/"));
	var m=0;
	for(var i=0; i<=num; i++){
		date.setMonth(date.getMonth() + 1);
	
		
		
		var y = date.getFullYear();
		var m = date.getMonth();
		
		if (m <= 9)
			m = "0" + m;
		
		var cdate = y + "-" + m;
		
        months.push(cdate);
		
	}
	
	return months;
}
/* 
* 获得查询时间段的所有数据点
* 格式:2012
* 返回精度为：年
*/
function getYearDimtimes(start,end){
	var years = Array();
	if(start==end){
		
			years.push(start);
			years.push(start+1);
	
	}else{
	for(var i=start;i<=end;i++){
		years.push(i);
	}
	}
	return years;
}
Date.prototype.getWeekOfMonth = function(weekStart) {
    weekStart = (weekStart || 0) - 0;
    if(isNaN(weekStart) || weekStart > 6)
    weekStart = 0;
    var dayOfWeek = this.getDay();
    var day = this.getDate();
    return Math.ceil((day - dayOfWeek - 1) / 7) + ((dayOfWeek >= weekStart) ? 1 : 0);
    };
Date.prototype.getWeekOfYear = function(weekStart) { // weekStart：每周开始于周几：周日：0，周一：1，周二：2 ...，默认为周日
    weekStart = (weekStart || 0) - 0;
    if(isNaN(weekStart) || weekStart > 6)
    weekStart = 0;
    var year = this.getFullYear();
    var firstDay = new Date(year, 0, 1);
    var firstWeekDays = 7 - firstDay.getDay() + weekStart;
    var dayOfYear = (((new Date(year, this.getMonth(), this.getDate())) - firstDay) / (24 * 3600 * 1000)) + 1;
    return Math.ceil((dayOfYear - firstWeekDays) / 7) + 1;
    };

/* 
* 获得week dimtime by date
* 输入格式:2012-12-01
* 输出格式:2012-45
* 返回:日期在本年的那个周
*/
function getYearWeek(date){
	var dt = new Date(date.replace(/-/g,"\/"));
	var y = dt.getFullYear();
	return y  + "-"+dt.getWeekOfYear();
}

