	var momentPlus = {
	  //取得具体时间设置
	  getOption: function (style) {
	    if(typeof style == 'string') {
	      style = style.toUpperCase();
	    } else if(typeof style == 'object') {
	      return style;
	    }
	    return this.options[style];
	  },
	  //取时间差值
	  getDiff: function (start, end, style) {
	    var option = this.getOption(style);
	    return Math.floor(moment(end, option.format)
	      .diff(moment(start, option.format), option.diffStyle, true));
	  },
	  //增加时间
	  addDate: function (date, num, style) {
	    var option = this.getOption(style);
	    var fn = option['fn']||this.toDisplay;
	    return fn.call(option, moment(date)
	      .add(num, option.diffStyle));
	  },
	  //取得第几周
	  getISOYearWeek: function (date) {
	    return moment(date)
	      .isoWeek();
	  },
	  //取得某周的周一
	  getWeekMondayDate: function (yearWeek) {
	    return moment(yearWeek, 'YYYY-W')
	      .add(1, 'week')
	      .format('YYYY-MM-DD');
	  },
	  //取得时间段列表
	  getDatesList: function (start, end, style) {
	    var option = this.getOption(style);
	    if(!option) {
	      alert('invalid style!');
	      return;
	    }
	    start = moment(start, option.format);
	    end = moment(end, option.format);
	    var diff = this.getDiff(start, end, option);
	    var roundMode = option.roundMode || 'floor';
	    var space = (eval('Math.' + roundMode)(diff / 5)) || 1;
	    var fn = option['fn']||this.toDisplay;
	    var re = [fn.call(option, start)];
	    for(var i = 1; i <= diff; i++) {
	      if(i % space == 0) {
	        re[i] = fn.call(option, start.add(option.diffStyle, space));
	      } else {
	        re[i] = '';
	      }
	    }
	    return re;
	  },
	  //默认返回的展示时间处理函数
	  toDisplay: function (moment) {
	    return moment.format(this.format);
	  },
	  //时间配置
	  options: {
	    'HOUR': {
	      format: 'YYYY-MM-DD HH',
	      diffStyle: 'h'
	    },
	    'DAY': {
	      format: 'YYYY-MM-DD',
	      diffStyle: 'days'
	    },
	    'WEEK': {
	      format: ['YYYY-MM-DD', 'YYYY-WW'],
	      diffStyle: 'w',
	      roundMode: 'ceil',
	      fn: function (moment) {
	        return moment.startOf('isoWeek')
	          .format(this.format[0]) + '/' + moment.endOf('isoWeek')
	          .format(this.format[0]);
	      }
	    },
	    'MONTH': {
	      format: 'YYYY-MM',
	      diffStyle: 'M'
	    },
	    'YEAR': {
	      format: 'YYYY',
	      diffStyle: 'y'
	    }
	  }
	};