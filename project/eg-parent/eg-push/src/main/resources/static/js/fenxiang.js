/**
 * 资讯分享
 */
var newsTemHeight;

function newsDetails() {
	var myData = {};
	var search = window.location.search;
	var id = getSearchString('note_id', search);
	myData.id = id;
	// myData.id = 2807
	if (id == undefined) {
		myData.id = 2807
	}
	$.ajax({
		type: 'GET',
		contentType: "application/json;charset=UTF-8",
		// url: 'http://192.168.6.12:8768/eg-api/user/pay/aliPay',
		url: 'http://www.curiousmore.com:8768/eg-api/message/noteUser/queryNoteShare?id=' + myData.id,
		// data: JSON.stringify(data),
		success: function(res) {
			console.log(res)
			var userData = res.data.noteinfo[0].map.userinfo
			var earthData = res.data.noteinfo[0]
			var pay_id = earthData.id
			var payment_id = userData.user_id


			var tuijianList = res.data.notelist


			if (res) {
				earthData.create_time = dateDiff(earthData.create_time)
				$('.myHead').attr('src', userData.avatar)
				$('.myName').text(userData.nick_name)
				$('.creat_time').text(earthData.create_time)
				if (userData.national_flag !== '') {
					$('.guoqi').attr('src', userData.national_flag)
				}
				if (userData.sex == 1) {
					$('.sex').attr('src', './img/man.png')
				} else if (userData.sex == 2) {
					$('.sex').attr('src', './img/woman.png')
				}
				if (userData.member_level == 1) {
					$('.huiyuan').attr('src', './img/huiyuan.png')
				} else if (userData.member_level == 2) {
					$('.huiyuan').attr('src', './img/baijinhuiyuan.png')
				} else if (userData.member_level == 3) {
					$('.huiyuan').attr('src', './img/zuanshihuiyuan.png')
				} else {
					$('.huiyuan').attr('src', './img/huiyuan2.png')
				}
				if (userData.national_flag != '') {
					$('.guoqi').attr('src', userData.national_flag)
				} else {

				}
				$('.contenttext').text(earthData.content)
				$('.contenttext').text(earthData.content)
				earthData.picts = earthData.picts.split(',')
				// console.log(earthData.picts)
				if (earthData.picts.length == 0) {
					$('.contentImg').hide()
				} else if (earthData.picts.length == 1) {
					if (earthData.style == 0) {
						$('.contenttext').after('<video controls="controls" style="height:' + earthData.img_height / 100 +
							'rem;width:' + earthData.img_width /
							100 + 'rem" src=' + earthData.picts[0] + '></video>')
						// if(earthData.img_height>960)
					} else {
						$('.contenttext').after('<img style="height:' + earthData.img_height / 100 + 'rem;width:' + earthData.img_width /
							100 + 'rem" src=' + earthData.picts[0] + '></img>')
					}

				} else if (earthData.picts.length > 1) {
					for (var i = 0; i < earthData.picts.length; i++) {
						$('.contenttext').after('<img style="width:1.74rem;height:1.74rem;padding-right:.1rem" src=' + earthData.picts[
							i] + '></img>')
					}
				}
				$('.liwushu').text(earthData.map.giftCount)
				$('.zhuanfashu').text(earthData.share_count)
				$('.pinglunshu').text(earthData.comment_count)
				$('.dianzanshu').text(earthData.like_count)
				getgiftList(pay_id, payment_id)

				if (tuijianList.length > 0) {
					for (var a = 0; a < tuijianList.length; a++) {
						tuijianList[a].picts = tuijianList[a].picts.split(',')
						tuijianList[a].create_time = dateDiff(tuijianList[a].create_time)
					}
					for (var j = 0; j < 3; j++) {
						var tuijianitem = '';
						if (tuijianList[j].style == 0) {
							tuijianHtml = $('.tuijian_item3').html()
						} else {
							tuijianHtml = $('.tuijian_item').html()
						}
						tuijianHtml = tuijianHtml.replace('{{content}}', tuijianList[j].content).replace('{{img}}', tuijianList[j].picts[
							0]).replace('{{time}}', tuijianList[j].create_time)

						$('#newsTem2').append(tuijianHtml)
					}
					for (var b = 3; b < tuijianList.length; b++) {
						var tuijianitem = '';
						if (tuijianList[b].style == 0) {
							tuijianHtml = $('.tuijian_item4').html()
						} else {
							tuijianHtml = $('.tuijian_item2').html()
						}
						tuijianHtml = tuijianHtml.replace('{{content}}', tuijianList[b].content).replace('{{img}}', tuijianList[b].picts[
							0]).replace('{{time}}', tuijianList[b].create_time).replace('{{city}}', tuijianList[b].location).replace(
							'{{new_id}}', tuijianList[b].id)
						$('#newsTem2').append(tuijianHtml)
					}

				}

			}

		},
	});




	function getgiftList(pay_id, user_id) {
		$.ajax({
			type: 'GET',
			contentType: "application/json;charset=UTF-8",
			url: 'http://www.curiousmore.com:8768/eg-api/user/userOrder/queryListPage?type=gift&source=note&pay_id=' + pay_id +
				'&payment_id=' + user_id,
			success: function(res) {
				console.log(res, '礼物')
				if (res.data.length > 0) {
					if (res.data.length > 5) {
						res.data.length = 5
					}
					for (var i = 0; i < res.data.length; i++) {
						var giftList = '';
						var giftUser = res.data[i].map.userinfo;
						var giftData = res.data[i].map.giftinfo[0];
						res.data[i].create_time = getDateDiff(res.data[i].create_time)
						// console.log(giftData.pic)
						giftList = $('.liwu_tab_item').html();
						console.log(giftList)
						giftList = giftList.replace('{{name}}', giftUser.nick_name).replace('{{gift}}', giftData.gift_name).replace(
							'{{num}}', res.data[i].gift_count).replace('{{count}}', res.data[i].pay_count).replace('{{headImg}}',
							giftUser.avatar).replace('{{giftimg}}', giftData.pic).replace('{{time}}', res.data[i].create_time)
						if (giftUser.national_flag !== '') {
							giftList = giftList.replace('{{country}}', giftUser.national_flag)
						} else {
							giftList = giftList.replace('{{country}}', './img/logo1.png')
						}
						if (giftUser.sex == 1) {
							giftList = giftList.replace('{{sex}}', './img/man.png')
						} else if (giftUser.sex == 2) {
							giftList = giftList.replace('{{sex}}', './img/woman.png')
						}
						if (giftUser.member_level == 1) {
							giftList = giftList.replace('{{huiyuan}}', './img/huiyuan.png')
						} else if (giftUser.member_level == 2) {
							giftList = giftList.replace('{{huiyuan}}', './img/baijinhuiyuan.png')
						} else if (giftUser.member_level == 3) {
							giftList = giftList.replace('{{huiyuan}}', './img/zuanshihuiyuan.png')
						} else {
							giftList = giftList.replace('{{huiyuan}}', './img/huiyuan2.png')
						}
						$('.liwuList').append(giftList)
					}
				}
			},

		})
	}
}

function getDateDiff(dateStr) {
	var publishTime = getDateTimeStamp(dateStr) / 1000,
		d_seconds,
		d_minutes,
		d_hours,
		d_days,
		timeNow = parseInt(System.currentTimeMillis() / 1000),
		d,

		date = new Date(publishTime * 1000),
		Y = date.getFullYear(),
		M = date.getMonth() + 1,
		D = date.getDate(),
		H = date.getHours(),
		m = date.getMinutes(),
		s = date.getSeconds();
	//小于10的在前面补0
	if (M < 10) {
		M = '0' + M;
	}
	if (D < 10) {
		D = '0' + D;
	}
	if (H < 10) {
		H = '0' + H;
	}
	if (m < 10) {
		m = '0' + m;
	}
	if (s < 10) {
		s = '0' + s;
	}

	d = timeNow - publishTime;
	d_days = parseInt(d / 86400);
	d_hours = parseInt(d / 3600);
	d_minutes = parseInt(d / 60);
	d_seconds = parseInt(d);

	if (d_days > 0 && d_days < 3) {
		return d_days + '天前';
	} else if (d_days <= 0 && d_hours > 0) {
		return d_hours + '小时前';
	} else if (d_hours <= 0 && d_minutes > 0) {
		return d_minutes + '分钟前';
	} else if (d_seconds < 60) {
		if (d_seconds <= 0) {
			return '刚刚';
		} else {
			return d_seconds + '秒前';
		}
	} else if (d_days >= 3 && d_days < 30) {
		return M + '-' + D + ' ' + H + ':' + m;
	} else if (d_days >= 30) {
		return Y + '-' + M + '-' + D + ' ' + H + ':' + m;
	}
}

function getDateTimeStamp(dateStr) {
	return Date.parse(dateStr.replace(/-/gi, "/"));
}

function getSearchString(key, Url) { //获取参数
	var str = Url;
	str = str.substring(1, str.length); // 获取URL中?之后的字符（去掉第一位的问号）
	// 以&分隔字符串，获得类似name=xiaoli这样的元素数组
	var arr = str.split("&");
	var obj = new Object();

	// 将每一个数组元素以=分隔并赋给obj对象 
	for (var i = 0; i < arr.length; i++) {
		var tmp_arr = arr[i].split("=");
		obj[decodeURIComponent(tmp_arr[0])] = decodeURIComponent(tmp_arr[1]);
	}
	return obj[key];
}




function jsGetAge(strBirthday) { //计算生日
	var returnAge;
	var strBirthdayArr = strBirthday.split("-");
	var birthYear = strBirthdayArr[0];
	var birthMonth = strBirthdayArr[1];
	var birthDay = strBirthdayArr[2];

	d = new Date();
	var nowYear = d.getFullYear();
	var nowMonth = d.getMonth() + 1;
	var nowDay = d.getDate();

	if (nowYear == birthYear) {
		returnAge = 0; //同年 则为0岁
	} else {
		var ageDiff = nowYear - birthYear; //年之差
		if (ageDiff > 0) {
			if (nowMonth == birthMonth) {
				var dayDiff = nowDay - birthDay; //日之差
				if (dayDiff < 0) {
					returnAge = ageDiff - 1;
				} else {
					returnAge = ageDiff;
				}
			} else {
				var monthDiff = nowMonth - birthMonth; //月之差
				if (monthDiff < 0) {
					returnAge = ageDiff - 1;
				} else {
					returnAge = ageDiff;
				}
			}
		} else {
			returnAge = -1; //返回-1 表示出生日期输入错误 晚于今天
		}
	}

	return returnAge; //返回周岁年龄
}

function peopleShare() {
	var search = window.location.search;
	var id = getSearchString('qmore_id', search);
	if (id == undefined) {
		id = 9120
	}
	$.ajax({
		type: 'GET',
		contentType: "application/json;charset=UTF-8",
		url: 'http://www.curiousmore.com:8768/eg-api/user/userInfo/queryUserInfo?user_id=' + id,
		success: function(res) {
			console.log(res)
			var myData = res.data.userinfo.data.userInfoEntity
			var otherUser = res.data.userlist
			$('.headImg').attr('src', myData.avatar)
			if (myData.member_level == 1) {
				$('.huiyuan').attr('src', './img/huiyuan.png')
			} else if (myData.member_level == 2) {
				$('.huiyuan').attr('src', './img/baijinhuiyuan.png')
			} else if (myData.member_level == 3) {
				$('.huiyuan').attr('src', './img/zuanshihuiyuan.png')
			} else {
				$('.huiyuan').attr('src', './img/huiyuan2.png')
			}
			$('.text1').text(myData.nick_name)
			myData.birth = jsGetAge(myData.birth)
			$('.birth').text(myData.birth)
			if (myData.sex == 1) {
				$('.sex').attr('src', './img/man.png')
			} else if (myData.sex == 2) {
				$('.sex').attr('src', './img/woman.png')
			}
			if (myData.country == '' || myData.city == '') {
				$('.add_box').text('')
			} else {
				$('.country').text(myData.country + '.' + myData.city)
			}
			if (myData.signature == '') {
				$('.qianming').text('这家伙很懒，什么也没留下')
			} else {
				$('.qianming').text(myData.signature)
			}
			$('.like_num').text(myData.like_num)
			$('.gift_num').text(res.data.userinfo.data.gift_count)
			getpic(id)
			for(var a=0;a<otherUser.length;a++){
				otherUser[a].local_time=otherUser[a].local_time.substring(10,16).split(':')
				if(otherUser[a].local_time[0]<6){
					var str='凌晨'
				}else if(otherUser[a].local_time[0]<9){
					var str='早上'
				}else if(otherUser[a].local_time[0]<12){
					var str='上午'
				}else if(otherUser[a].local_time[0]<14){
					var str='中午'
				}else if(otherUser[a].local_time[0]<17){
					var str='下午'
				}else if(otherUser[a].local_time[0]<19){
					var str='傍晚'
				}else if(otherUser[a].local_time[0]<22){
					var str='晚上'
				}else{
					var str='深夜'
				}
				otherUser[a].local_time=str+otherUser[a].local_time.join(':')
				
				// console.log(otherUser[a].local_time)
			}
			if (otherUser.length > 3) {
				for (var i = 0; i < 3; i++) {
					var otherUserHtml;
					otherUser[i].log_out_time = dateDiff(otherUser[i].log_out_time);
					otherUserHtml = $('.peoplefir').html();
					// console.log($('.peoplefir').html(),'1')
					otherUserHtml = otherUserHtml.replace('{{headImg}}', otherUser[i].avatar).replace('{{name}}', otherUser[i].nick_name)
						.replace('{{log_out_time}}', otherUser[i].log_out_time).replace('{{time}}',otherUser[i].local_time);
					if (otherUser[i].country !== '') {
						otherUserHtml = otherUserHtml.replace('{{country}}', otherUser[i].country)
					} else {
						otherUserHtml = otherUserHtml.replace('{{country}}', '未知')
					}
					if (otherUser[i].national_flag !== '') {
						otherUserHtml = otherUserHtml.replace('{{countryImg}}', otherUser[i].national_flag)
					} else {
						otherUserHtml = otherUserHtml.replace('{{countryImg}}', './img/logo1.png')
					}
					if (otherUser[i].identity_auth == false) {
						otherUserHtml = otherUserHtml.replace('prove1', 'prove2')
					}
					if (otherUser[i].languages == '') {
						otherUserHtml = otherUserHtml.replace('{{languages}}', '未知')
					} else {
						otherUserHtml = otherUserHtml.replace('{{languages}}', otherUser[i].languages)
					}
					if (otherUser[i].preference == '') {
						otherUserHtml = otherUserHtml.replace('{{preference}}', '未知')
					} else {
						otherUserHtml = otherUserHtml.replace('{{preference}}', otherUser[i].preference)
					}
					if (otherUser[i].overseas_identity_name != null) {
						otherUserHtml = otherUserHtml.replace('{{shenfen}}', otherUser[i].overseas_identity_name)
						console.log(otherUser[i].overseas_identity_name)
					} else {
						otherUserHtml = otherUserHtml.replace('{{shenfen}}', '境外身份')
					}
					if (otherUser[i].overseas_auth == true) {
						otherUserHtml = otherUserHtml.replace('{{shenfen}}', 'prove1')
					}
					if (otherUser[i].sex == 1) {
						otherUserHtml = otherUserHtml.replace('{{sex}}', './img/man.png')
					} else if (otherUser[i].sex == 2) {
						otherUserHtml = otherUserHtml.replace('{{sex}}', './img/woman.png')
					}
					$('#otherUser').append(otherUserHtml)
				}
				for (var j = 3; j < otherUser.length; j++) {
					var otherUserHtml;
					otherUser[j].log_out_time = dateDiff(otherUser[j].log_out_time);
					otherUserHtml = $('.peoplesec').html();
					otherUserHtml = otherUserHtml.replace('{{headImg}}', otherUser[j].avatar).replace('{{name}}', otherUser[j].nick_name)
						.replace('{{log_out_time}}', otherUser[j].log_out_time).replace('{{people_id}}', otherUser[j].user_id).replace('{{time}}',otherUser[j].local_time);
					if (otherUser[j].country !== '') {
						otherUserHtml = otherUserHtml.replace('{{country}}', otherUser[j].country)
					} else {
						otherUserHtml = otherUserHtml.replace('{{country}}', '未知')
					}
					if (otherUser[j].national_flag !== '') {
						otherUserHtml = otherUserHtml.replace('{{countryImg}}', otherUser[j].national_flag)
					} else {
						otherUserHtml = otherUserHtml.replace('{{countryImg}}', './img/logo1.png')
					}
					if (otherUser[j].identity_auth == false) {
						otherUserHtml = otherUserHtml.replace('prove1', 'prove2')
					}
					if (otherUser[j].languages == '') {
						otherUserHtml = otherUserHtml.replace('{{languages}}', '未知')
					} else {
						otherUserHtml = otherUserHtml.replace('{{languages}}', otherUser[j].languages)
					}
					if (otherUser[j].preference == '') {
						otherUserHtml = otherUserHtml.replace('{{preference}}', '未知')
					} else {
						otherUserHtml = otherUserHtml.replace('{{preference}}', otherUser[j].preference)
					}
					if (otherUser[j].overseas_identity_name != null) {
						otherUserHtml = otherUserHtml.replace('{{shenfen}}', otherUser[j].overseas_identity_name)
					} else {
						otherUserHtml = otherUserHtml.replace('{{shenfen}}', '境外身份')
					}
					if (otherUser[j].overseas_auth == true) {
						otherUserHtml = otherUserHtml.replace('{{shenfen}}', 'prove1')
					}
					if (otherUser[j].sex == 1) {
						otherUserHtml = otherUserHtml.replace('{{sex}}', './img/man.png')
					} else if (otherUser[j].sex == 2) {
						otherUserHtml = otherUserHtml.replace('{{sex}}', './img/woman.png')
					}
					$('#otherUser').append(otherUserHtml)
				}
			}
		},
	});
}


function getpic(id) {
	$.ajax({
		type: 'GET',
		contentType: "application/json;charset=UTF-8",
		url: 'http://www.curiousmore.com:8768/eg-api/user/userAlbum/queryListPage?user_id=' + id,
		success: function(res) {
			console.log(res, '相册')
			if (res.data[0].photo !== '') {
				res.data[0].photo = res.data[0].photo.split(',')
				for (var i = 0; i < res.data[0].photo.length; i++) {
					$('.imgList').append('<img src="' + res.data[0].photo[i] + '">')
				}

			}
		},
	})
}



var dateDiff = function(timestamp) { //时间多久前
	var T = new Date(timestamp);
	timestamp = T.getTime()
	var arrTimestamp = (timestamp + '').split('');
	for (var start = 0; start < 13; start++) {
		if (!arrTimestamp[start]) {
			arrTimestamp[start] = '0';
		}
	}
	timestamp = arrTimestamp.join('') * 1;

	var minute = 1000 * 60;
	var hour = minute * 60;
	var day = hour * 24;
	var halfamonth = day * 15;
	var month = day * 30;
	var now = System.currentTimeMillis();
	var diffValue = now - timestamp;

	// 如果本地时间反而小于变量时间
	if (diffValue < 0) {
		return '不久前';
	}

	// 计算差异时间的量级
	var monthC = diffValue / month;
	var weekC = diffValue / (7 * day);
	var dayC = diffValue / day;
	var hourC = diffValue / hour;
	var minC = diffValue / minute;

	// 数值补0方法
	var zero = function(value) {
		if (value < 10) {
			return '0' + value;
		}
		return value;
	};

	// 使用
	if (monthC > 12) {
		// 超过1年，直接显示年月日
		return (function() {
			var date = new Date(timestamp);
			return date.getFullYear() + '年' + zero(date.getMonth() + 1) + '月' + zero(date.getDate()) + '日';
		})();
	} else if (monthC >= 1) {
		return parseInt(monthC) + "月前";
	} else if (weekC >= 1) {
		return parseInt(weekC) + "周前";
	} else if (dayC >= 1) {
		return parseInt(dayC) + "天前";
	} else if (hourC >= 1) {
		return parseInt(hourC) + "小时前";
	} else if (minC >= 1) {
		return parseInt(minC) + "分钟前";
	}
	return '刚刚';
}
function peopleDetail(e) {
	var people_id = e.getAttribute("people_id");
	console.log(people_id)
	// clickTo({
	// 	namePath: "/view/share/funNewsDetails.ftl&id=" + people_id
	// });
}

function newDetail(e) {
	var news_id = e.getAttribute("new_id");

	clickTo({
		namePath: "/view/share/funNewsDetails.ftl&id=" + news_id
	});
}


function download() {
	window.location.href = "http://www.huihejituan.com/tripPictstorage/qmore/download/download.html";
}
