var ajaxAbort; //jq请求返回的jqXHR对象
/**
 * 系统公用的封装JS
 * 
 * @author yangcehngfu
 * @dataTime 2016年7月20日
 */
SystemCode = {};
document.addEventListener('touchstart', function() {
	return false;
}, true);
SystemUtil = {
	is_usable: false,
	backCount: 0,
	init: function(backFunction) {
		if (window.plus) {
			pushAddEventListener(push);
			SystemUtil.is_usable = true;
			SystemUtil.Storage.Local.put("is_usable", SystemUtil.is_usable);
			if (typeof(backFunction) == "function") {
				backFunction();
			}
		} else {
			document.addEventListener('plusready', function(e) {
				pushAddEventListener(push);
				SystemUtil.is_usable = true;
				SystemUtil.Storage.Local.put("is_usable", SystemUtil.is_usable);
				if (typeof(backFunction) == "function") {
					backFunction();
				}
			}, false);
		}
	},
	/**
	 *  替换字符串
	 * @param {String} str 需要替换的字符串
	 * @param {String} repStr 替换的字符串
	 * @param {Number} startNum 开始要几位
	 * @param {Number} end_num 结束要几位
	 */
	replaceMobile: function(str, repStr, startNum, end_num) {
		try {
			var a = str.substr(str.length - startNum, str.length - end_num);
			var newStr = str.replace(a, repStr);
			console.debug("str" + str, "new str" + newStr);
			return newStr;
		} catch (e) {
			//TODO handle the exception
			console.warn("replace mobile is Error", e);
		}
	},
	/**
	 *内置定时器处理  可以实现 异步回调
	 * obj.time 时间
	 * obj.loop 是否定时执行
	 * obj.backFunction 回调函数
	 * obj.count 执行的次数
	 * @param {JSON} obj
	 * obj.time 时间
	 * obj.loop 是否定时执行
	 * obj.backFunction 回调函数
	 * obj.count 执行的次数
	 */
	setTimeOut: function(obj) {
		if (typeof(obj.time) != "number") {
			obj.time = 300;
		}
		if (typeof(obj.count) != "number") {
			obj.count = 1;
		}
		if (typeof(obj.loop) != "boolean") {
			obj.loop = false;
		}
		var timeNum = setTimeout(function(e) {
			var setTime = SystemUtil.Storage.HashMap.getInt("setTimeOut");
			if (!obj.loop) {
				clearTimeout(timeNum);
			} else if (setTime > obj.count) {
				clearTimeout(setTime);
			}
			if (typeof(obj.backFunction) != "function") {
				throw "obj.backFunction is not a function";
			}
			obj.backFunction(e);
			SystemUtil.Storage.HashMap.put("setTimeOut", SystemUtil.Storage.HashMap.getInt("setTimeOut") +
				1);
		}, obj.time)
		return timeNum;
	},
	clearTimeout: function(code) {
		clearTimeout(code);
	},
	/**
	 * 验证是否可用 是否为null 或者 ""
	 * 
	 * @param {Object}
	 *            obj
	 * @param{Boolean} isThow 是否抛出异常
	 */
	validateObj: function(obj, isThow, ThowMessage) {
		var result = false;
		if (isThow == undefined || isThow == "" || isThow == null) {
			isThow = true;
		}
		if (ThowMessage == undefined || ThowMessage == null ||
			ThowMessage == "") {
			ThowMessage = "validateObj obj is null";
		}
		if (obj != undefined && obj != null && obj != "") {
			result = true;
		} else {
			if (isThow) {
				throw ThowMessage;
			}
		}
		return result;
	},
	/**
	 * 存储的所有对象
	 */
	Storage: {
		// 内部对象
		map: {},
		/**
		 * 内部hashMap对象
		 */
		HashMap: {
			/**
			 * 存储
			 * 
			 * @param {String}
			 *            key 键
			 * @param {Object}
			 *            val 值
			 * 
			 */
			put: function(key, val) {
				return SystemUtil.Storage.map[key] = val;
			},
			/**
			 * 存储
			 * 
			 * @param {Object}
			 *            object 对象
			 */
			putAll: function(object) {
				return SystemUtil.Storage.map = object;
			},
			/**
			 * 删除
			 * 
			 * @param {String}
			 *            key
			 */
			remove: function(key) {
				return delete SystemUtil.Storage.map[key];
			},
			/**
			 * 删除全部
			 */
			removeAll: function() {
				return SystemUtil.Storage.map = {};
			},
			/**
			 * 查找是否存在该key
			 * 
			 * @param {String}
			 *            key
			 */
			containsKey: function(key) {
				var result = false;
				var keys = SystemUtil.Storage.map[key];
				if (keys != null && keys.length > 0) {
					result = true;
				}
				return result;
			},
			getAll: function() {
				return SystemUtil.Storage.map;
			},
			/**
			 * 获取对象
			 * 
			 * @param {String}
			 *            key
			 */
			getObject: function(key) {
				var obj = SystemUtil.Storage.map[key];
				if (obj == undefined) {
					obj = null;
					if (typeof(obj) != "object" && typeof(obj) == "string") {
						obj = JSON.parse(obj);
					}
				}

				return obj;
			},
			/**
			 * 获取数字
			 * @param {Number} key
			 */
			getInt: function(key) {
				var obj = SystemUtil.Storage.HashMap.getObject(key);
				obj = parseInt(obj);
				if (isNaN(obj)) {
					obj = 0;
				}
				return obj;
			},
			getBoolean: function(key) {

			}
		},
		/**
		 * 会话存储
		 */
		Session: {
			/**
			 * 键值对存储
			 * 
			 * @param {String}
			 *            key 键
			 * @param {Object}
			 *            val 值
			 * @param {Boolean}
			 *            isParseJSON 是否需要转换JSON
			 */
			put: function(key, val, isParseJSON) {
				if (isParseJSON) {
					val = SystemUtil.JSON.ObjectToString(val);
				}
				sessionStorage.setItem(key, val);
			},
			/**
			 * 根据键获取值
			 * 
			 * @param {String}
			 *            key
			 */
			getVal: function(key) {
				var str = sessionStorage.getItem(key);
				if (!(str instanceof Object)) {
					str = SystemUtil.JSON.StringToObject(str);
				}
				return str;
			},
			/**
			 * 情况
			 */
			clear: function() {
				return sessionStorage.clear();
			},
			/**
			 * 根据键删除值
			 * 
			 * @param {Object}
			 *            key
			 */
			removeVal: function(key) {
				return sessionStorage.removeItem(key);
			}
		},
		/**
		 * 本地存储
		 */
		Local: {
			/**
			 * 存
			 * 
			 * @param {String}
			 *            key
			 * @param {Object}
			 *            val
			 */
			put: function(key, val) {
				return localStorage.setItem(key, val);
			},
			/**
			 * 根据键获取值
			 * 
			 * @param {String}
			 *            key 键
			 * @param {Boolean} isremove 获取完成是否删除
			 */
			getVal: function(key, isremove) {
				var val = localStorage.getItem(key);
				if (isremove) {
					SystemUtil.Storage.Local.removeVal(key);
				}
				return val;
			},
			getInt: function(key) {
				var obj = localStorage.getItem(key);
				obj = parseInt(obj);
				if (isNaN(obj)) {
					obj = 0;
				}
				return obj;
			},
			/**
			 * 清空
			 */
			clear: function() {
				return localStorage.clear();
			},
			/**
			 * 删除
			 * 
			 * @param {String}
			 *            key 键
			 */
			removeVal: function(key) {
				return localStorage.removeItem(key);
			}
		},
	},
	JSON: {
		/**
		 * 将json对象转换成 字符串
		 * 
		 * @param {Object}
		 *            object
		 */
		ObjectToString: function(object) {
			var jsonStr = null;
			try {
				jsonStr = JSON.stringify(object);
			} catch (e) {
				console
					.log("JSON parse string is Exception,object is Array,EXception message is:" +
						e.toString());
			}
			return jsonStr;
		},
		/**
		 * 将JSON字符串转换为JSON对象
		 * 
		 * @param {String}
		 *            JSONStr
		 */
		StringToObject: function(JSONStr) {
			var obj = JSONStr;
			try {
				if (!(JSONStr instanceof Object)) {
					obj = JSON.parse(JSONStr);
				}
			} catch (e) {
				// TODO handle the exception
				console.log(e.toString());
			}
			return obj;
		}
	},
	Log: {
		warn: function(code) {
			var text = SystemCode[code];
			if (text == null || text == undefined) {
				text = code;
			}
			console.warn(text);
		},
		log: function(code) {
			var text = SystemCode[code];
			if (text == null || text == undefined) {
				text = code;
			}
			console.log(text);
		},
		error: function(code) {
			var text = SystemCode[code];
			if (text == null || text == undefined) {
				text = code;
			}
			console.error(text);
		}
	},
	App: {

	},
	getDialogue_key: function() {
		var myDate = new Date();
		var timeKey = myDate.getFullYear() + "" +
			(myDate.getMonth() + 1) + "" +
			myDate.getDate() + "" +
			myDate.getHours() + "" +
			myDate.getMinutes() + "" +
			myDate.getSeconds() + "" +
			myDate.getMilliseconds() + "";
		var dialogue_key = timeKey + SystemUtil.Storage.Local.getVal("user_id");
		return dialogue_key;
	}
}
SystemUtil.city = {
	/**
	 * 查询城市
	 * obj.data 参数
	 * obj.success 回调
	 * @param {Object} obj
	 */
	query: function(obj) {
		SystemUtil.HttpClientRequest.Execute({
			controllerName: "regionLevel",
			actionName: "query",
			data: obj.data,
			successBackFunction: function(data) {
				if (typeof(obj.success) == "function") {
					obj.success(data);
				}
				return;
			}
		})
	},
	/**
	 * 查询城市和景点
	 * obj.data 参数
	 * obj.success 回调
	 * @param {Object} obj
	 */
	queryScenic: function(obj) {
		SystemUtil.HttpClientRequest.Execute({
			controllerName: "guideInfo",
			actionName: "queryAttractionsList",
			data: obj.data,
			successBackFunction: function(data) {
				if (typeof(obj.success) == "function") {
					obj.success(data);
				}
				return;
			}
		})
	}
};
var putMap;
SystemUtil.IM = {
	messengerObject: null,
	message: {
		/**
		 * 内容
		 */
		content: null,
		/**
		 * 发送人的ID 你你自己的ID
		 */
		send_id: SystemUtil.Storage.Local.getInt("user_id"),
		/**
		 * 接收者的ID
		 */
		target_id: 0,
		/**
		 * 消息的类型
		 */
		message_type: "personal",
		/**
		 * 内容的类型
		 */
		content_type: "text"
	},
	/**
	 * 
	 * @param {JSON} IMOBJ
	 * IMOBJ.user_id (必传)
	 * IMOBJ.open(可选 连接服务器成功的回调)
	 * IMOBJ.error(可选 错误的回调)
	 * IMOBJ.close(可选 客户端关闭或者服务器关闭)
	 * IMOBJ.message(需要传，服务器返回消息的回调)
	 */
	init: function(IMOBJ) {
		if (IMOBJ == undefined || IMOBJ == null || typeof(IMOBJ) != "object") {
			throw "IMOBJ IS undefined OR IMOBJ is not a object";
		}
		var user_id = SystemUtil.Storage.Local.getInt("user_id");
		if (user_id == 0) {
			console.log("you haven't  login");
			return;
		}

		if (SystemUtil.IM.messengerObject == null) {
			SystemUtil.IM.messengerObject = new WebSocket(path.IM + user_id);
		}
		SystemUtil.IM.messengerObject.onopen = function(even) {
			console.log("connection service is open success");
			if (typeof(IMOBJ.open) != "function") {
				return;
			}
			IMOBJ.open(even);
			putMap = setInterval("addAddress();putMapLocation();", 30 * 1000); //每隔30s查询一次地理位置
		}
		SystemUtil.IM.messengerObject.onerror = function(even) {
			console.log("service is error");

			setTimeout(function() {
				plus.webview.getWebviewById('chat.html').reload();
			}, 3000);
		}
		SystemUtil.IM.messengerObject.onclose = function(even) {
			console.log("client is close or service is close connection");

			setTimeout(function() {
				plus.webview.getWebviewById('chat.html').reload();
			}, 3000);
		}
		SystemUtil.IM.messengerObject.onmessage = function(dataObject) {
			if (typeof(IMOBJ.message) != "function") {
				throw "message is not a function";
			}
			console.debug("result dataObje", dataObject);
			var obj = JSON.parse(dataObject.data);
			switch (obj.message_type) {
				//加好友消息
				case "friends":
					SystemUtil.App.Dervice.vibrate(1000);
					plus.webview.getWebviewById('notice.html').evalJS('addFriendInstanceMsg(' + JSON.stringify(
						obj) + ')');
					plus.webview.getWebviewById('my1.html').evalJS('noticeRed();');
					SystemUtil.Storage.Local.put('newMessageByFriendsNotice', 'news'); //消息提醒
					break;
					//玩客玩主认证消息
				case "updateUserAuth":
					SystemUtil.App.Dervice.vibrate(1000);
					plus.webview.getWebviewById('notice.html').evalJS('receiveInstanceMsg(' + JSON.stringify(
						obj) + ')');
					plus.webview.getWebviewById('my1.html').evalJS('noticeRed();');
					SystemUtil.Storage.Local.put('newMessageByCertificationNotice', 'news'); //消息提醒

					//弹框提醒
					if (plus.webview.getTopWebview().id !== 'main.html') {
						plus.webview.getTopWebview().evalJS("identityPop('" + obj.contentMap.userAuthType +
							"','" + obj.contentMap.status + "','" + obj.contentMap.remark + "');");
					} else {
						plus.webview.getTopWebview().evalJS("showMessage('" + obj.contentMap.userAuthType +
							"','" + obj.contentMap.status + "','" + obj.contentMap.remark + "');");
					}
					break;
				case "updateGuideIdentify":
					SystemUtil.App.Dervice.vibrate(1000);
					SystemUtil.Storage.Local.put("is_guide_auth_status", obj.contentMap.result);
					SystemUtil.Storage.Local.put("is_guide_auth", true);
					break;
				case "getNotReadTrip":
					updateTripNotReadMessage(obj.contentMap);
					break;
					//分享消息
				case "publishMessage":
					try {
						SystemUtil.App.Dervice.vibrate(1000);
						console.log(obj.contentMap.type); //commentNote collecttypenotecollect collecttypenotecollect
						//alert(obj.contentMap.type);
						eval(obj.contentMap.type + "(" + JSON.stringify(obj) + ")");
						plus.webview.getWebviewById('main.html').evalJS(
							"$('#shareLi').addClass('dynamicTip')");
					} catch (e) {
						//TODO handle the exception
					}
					break;
				default:
					if (parseInt(obj.send_id) != SystemUtil.Storage.Local.getInt("user_id")) {
						var isVisible = plus.webview.currentWebview().isVisible();
						if (!isVisible) {
							SystemUtil.App.Dervice.vibrate(1000);
						}
					}
					IMOBJ.message(obj);
					break;
			}
		}
		window.onbeforeunload = function(e) {
			try {
				SystemUtil.IM.messengerObject.close();
			} catch (e) {
				//TODO handle the exception
			}
		}
	},
	/**
	 * 发送消息
	 * @param {Object} Data
	 * Data.content (必传 消息的内容)
	 * Data.send_id(可选 默认是自己ID)
	 * Data.target_id(必传 接收人的ID)
	 * Data.message_type(可选，消息的类型 默认是个人聊天--即两个人聊,可取值[])
	 * Data.content_type(可选，内容的类型 默认是文本,取值:[])
	 * backFunction 可选
	 */
	send: function(Data, backFunction) {
		if (SystemUtil.IM.messengerObject == null) {
			throw "SystemUtil.IM is not init";
		}
		if (Data == undefined || Data == null || typeof(Data) != "object") {
			throw "Data is undefined or null or is not a object";
		}
		if (Data.content_type == undefined || Data.content_type == "") {
			Data.content_type = SystemUtil.IM.message.content_type;
		}
		if ((Data.content == undefined || Data.content == null || Data.length < 0) && Data.content_type ==
			SystemUtil.IM.message.content_type) {
			throw "content is null";
		}
		if (Data.target_id == undefined || Data.target_id == null || Data.target_id.length < 0) {
			throw "target_id is not null";
		}
		if (Data.message_type == undefined || Data.message_type == "") {
			Data.message_type = SystemUtil.IM.message.message_type;
		}
		if (Data.send_id == undefined || Data.send_id == null || Data.send_id.length <= 0) {
			Data.send_id = SystemUtil.Storage.Local.getInt(SystemUtil.HashMap.Key.user.user_id);
		}
		var dialogueParam = {
			dialogue_key: Data.dialogue_key == undefined ? "" : Data.dialogue_key,
			travel_status: Data.travel_status == undefined ? "" : Data.travel_status,
			exchange_status: Data.exchange_status == undefined ? "" : Data.exchange_status,
			play_status: Data.play_status == undefined ? "" : Data.play_status,
			brod_id: Data.brod_id == undefined ? "" : Data.brod_id,
			order_id: Data.order_id == undefined ? "" : Data.order_id,
			travel_id: Data.travel_id == undefined ? "" : Data.travel_id
		}
		var contentParam = {
			content: Data.content,
			content_type: Data.content_type,
			image: Data.image == undefined ? "" : Data.image,
			dialogue: dialogueParam,
			travel_id: Data.travel_id,
			travel_type: Data.travel_type,
			match_type: Data.match_type,
			sender_identity: Data.sender_identity == undefined ? "travel" : Data.sender_identity,
			contentAppenParam: Data.contentAppenParam == undefined ? null : Data.contentAppenParam,

			referrer_id: Data.referrer_id,
			referrer_avatar: Data.referrer_avatar,
			referrer_name: Data.referrer_name,
		}
		if (Data.business_id != undefined) {
			contentParam.business_id = Data.business_id;
		}
		var message = {
			send_id: Data.send_id,
			target_id: Data.target_id,
			message_type: Data.message_type,
			message_key: Data.message_key,
			contentParam: contentParam,
			sendParam: Data.sendParam,
			send_me: Data.send_me == undefined ? true : false
		}
		if (Data.id) {
			message.id = Data.id; //群聊id
		}

		if (Data.message_type != 'address') { //定位的发送消息不更新聊天列表
			plus.webview.getWebviewById('/view/message/message.html').evalJS("updateChatListMy(" + JSON.stringify(message) +
				");");
		}

		message = JSON.stringify(message);
		message = encodeURI(message);
		SystemUtil.IM.messengerObject.send(message);

		if (typeof(backFunction) == "function") {
			var resultObj = {
				result: true,
				remark: "发送成功,等待回应"
			};
			backFunction(resultObj);
		}
	},
	//发送语音
	sendAudio: function(Data, backFunction) {
		if (SystemUtil.IM.messengerObject == null) {
			throw "SystemUtil.IM is not init";
		}
		if (Data == undefined || Data == null || typeof(Data) != "object") {
			throw "Data is undefined or null or is not a object";
		}
		if (Data.content_type == undefined || Data.content_type == "") {
			Data.content_type = SystemUtil.IM.message.content_type;
		}
		if ((Data.content == undefined || Data.content == null || Data.length < 0) && Data.content_type ==
			SystemUtil.IM.message.content_type) {
			throw "content is null";
		}
		if (Data.target_id == undefined || Data.target_id == null || Data.target_id.length < 0) {
			throw "target_id is not null";
		}
		if (Data.message_type == undefined || Data.message_type == "") {
			Data.message_type = SystemUtil.IM.message.message_type;
		}
		if (Data.send_id == undefined || Data.send_id == null || Data.send_id.length <= 0) {
			Data.send_id = SystemUtil.Storage.Local.getInt(SystemUtil.HashMap.Key.user.user_id);
		}

		var dialogueParam = {
			brod_id: Data.brod_id == undefined ? "" : Data.brod_id,
			order_id: Data.order_id == undefined ? "" : Data.order_id,
			travel_id: Data.travel_id == undefined ? "" : Data.travel_id
		}

		var contentParam = {
			content: Data.content,
			content_type: Data.content_type,
			match_type: Data.match_type,
			speech: Data.speech == undefined ? "" : Data.speech,
			dialogue: dialogueParam,
			sender_identity: Data.sender_identity == undefined ? "travel" : Data.sender_identity,
			mypath: Data.mypath
		}
		var message = {
			send_id: Data.send_id,
			target_id: Data.target_id,
			message_type: Data.message_type,
			message_key: Data.message_key,
			contentParam: contentParam,
			sendParam: Data.sendParam,
			send_me: Data.send_me == undefined ? true : false
		}
		if (Data.id) {
			message.id = Data.id; //群聊id
		}
		plus.webview.getWebviewById('/view/message/message.html').evalJS("updateChatListMy(" + JSON.stringify(message) +
			");");

		message = JSON.stringify(message);
		message = encodeURI(message);
		SystemUtil.IM.messengerObject.send(message);
		if (typeof(backFunction) == "function") {
			var resultObj = {
				result: true,
				remark: "from " + Data.send_id + " to " + Data.target_id + ":发送成功,等待回应"
			};
			backFunction(resultObj);
		}
	}

};

SystemUtil.validate = {
	/**
	 * DataOptions.isThow 
	 * DataOptions.ThowMessage
	 * DataOptions.obj
	 * @param {JSON}
	 *            DataOptions
	 */
	obj: function(DataOptions) {
		var result = false;
		if (DataOptions.isThow == undefined ||
			DataOptions.isThow == null) {
			DataOptions.isThow = true;
		}
		if (DataOptions.ThowMessage == undefined ||
			DataOptions.ThowMessage == null ||
			DataOptions.ThowMessage == "") {
			DataOptions.ThowMessage = "validateObj obj is null";
		}
		if (DataOptions.obj != undefined && DataOptions.obj != null &&
			DataOptions.obj.toString().length > 0) {
			result = true;
		} else {
			if (DataOptions.isThow) {
				throw DataOptions.ThowMessage;
			}
		}
		return result;
	},
	/**
	 * 验证是否是Array类型
	 * @param {Object} obj 需要验证的对象
	 * @param {Function} backFunction  回调函数 可不传 不传 有返回值
	 */
	isArray: function(obj, backFunction) {
		var result = true;
		var type = "Array";
		if (obj != undefined) {
			switch (obj.length) {
				case undefined:
					type = "object";
					result = false;
					break;
				default:

					break;
			}
		}
		var obj = {
			result: result,
			type: type
		};
		if (backFunction != undefined) {
			backFunction(obj)
		} else {
			return obj;
		}

	},
	/**
	 * 验证 集合
	 * @param {Object} arrayObj 需要验证的集合 数组
	 * @param {Object} backFunction 验证成功的回调函数  不传则 return
	 */
	array: function(arrayObj, backFunction) {
		var result = true;
		if (arrayObj == undefined) {
			result = false;
		} else if (arrayObj.length == 0) {
			result = false;
		}
		if (typeof(backFunction) == "function") {
			backFunction(result);
		} else {
			return result;
		}
	},
	/**
	 * 验证是否是一个函数
	 * 
	 * @param {JSON}
	 *            DataOptions
	 * DataOptions.isThow 
	 * DataOptions.ThowMessage
	 * DataOptions.obj
	 */
	functions: function(DataOptions) {
		var result = false;
		if (DataOptions.isThow == undefined ||
			DataOptions.isThow == null) {
			DataOptions.isThow = true;
		}
		if (DataOptions.ThowMessage == undefined ||
			DataOptions.ThowMessage == null ||
			DataOptions.ThowMessage == "") {
			DataOptions.ThowMessage = SystemCode.IS_NOT_A_FUNCTION;
		}
		if (typeof(DataOptions.obj) == "function") {
			result = true;
		} else {
			if (DataOptions.isThow) {
				throw DataOptions.ThowMessage;
			}
		}
		return result;
	},
	url: function(data) {
		var result = false;
		if (SystemUtil.validate.obj({
				obj: data,
				isThow: false
			})) {
			if (data.indexOf("http") != -1) {
				result = true;
			} else if (data.indexOf("https") != -1) {
				result = true;
			}
		}
		return result;
	}
}
SystemUtil.Exception = {
	/**
	 * 抛出异常
	 * 
	 * @param {String}
	 *            message
	 */
	Thow: function(message) {
		throw message;
	}
}
/**
 * DOM 操作
 */
SystemUtil.HTML = {
	page: 1,
	obj: null,
	/**
	 * SystemUtil.HTML.DataOptions
	 * @param {SystemUtil.HTML.DataOptions} DataOptions
	 * 说明：
	 * DataOptions.type 必传 参数 id or class 默认id
	 * DataOptions.typeName dom 名称 必传
	 * DataOptions.success //初始化成功的回调函数
	 * DataOptions.error //错误的回调函数
	 */
	init: function(DataOptions) {
		debugger;
		var obj = {};
		//存储是否已经初始化
		SystemUtil.Storage.HashMap.put(SystemUtil.HashMap.Key.HTML.init.typeName, true);
		var type = "#";
		var options = SystemUtil.HTML.DataOptions;
		SystemUtil.validate.obj({
			obj: DataOptions
		});
		SystemUtil.validate.obj({
			obj: DataOptions.typeName,
			ThowMessage: SystemCode.HTML_TYPE_NAME
		});
		for (var x in DataOptions) {
			options[x] = DataOptions[x];
		}
		if (options.type == undefined || options.type == "" || options.type == null || options.type == "class") {
			type = ".";
		}
		SystemUtil.HTML.obj = document.querySelector(type + options.typeName);
		SystemUtil.Storage.HashMap.put("typeName", options.typeName);
		//存储dom的 id 或者class
		SystemUtil.Storage.HashMap.put(SystemUtil.HashMap.Key.HTML.init.domTypeName, type + options.typeName);
		try {
			//存储html内容
			var html = SystemUtil.HTML.obj.innerHTML;
			SystemUtil.HTML.obj.style.display = "block";
			var sessionObj = SystemUtil.Storage.HashMap.getObject(SystemUtil.HashMap.Key.HTML.init.htmlNameKey +
				options.typeName);
			if (sessionObj != null && sessionObj.length > 0) {
				html = sessionObj;
			} else {
				SystemUtil.Storage.HashMap.put(SystemUtil.HashMap.Key.HTML.init.htmlNameKey + options.typeName,
					html);
			}
			if (SystemUtil.HTML.page == 1) {
				SystemUtil.HTML.obj.innerHTML = "";
			}
		} catch (e) {
			//TODO handle the exception
			SystemUtil.Log.warn("Exceptions is :" + e);
			if (typeof(options.error) == "function") {
				options.error();
			}
		}
		if (typeof(options.success) == "function") {
			options.success({
				typeNmae: type + options.typeName,
				Html: SystemUtil.HTML.obj.innerHTML
			});
		}
	},
	/**
	 * 添加完成的回调函数
	 * @param {Function} backFunction 回调函数的名称
	 */
	append: function(backFunction) {
		SystemUtil.HTML.validate();
		var index = SystemUtil.Storage.HashMap.getInt(SystemUtil.HashMap.Key.HTML.Set.html.typeName);
		SystemUtil.Storage.HashMap.put(SystemUtil.HashMap.Key.HTML.Set.html.typeName, index + 1);
		var html = SystemUtil.Storage.HashMap.getObject(SystemUtil.HashMap.Key.HTML.init.htmlNameKey +
			SystemUtil.Storage.HashMap.getObject("typeName"));
		$(SystemUtil.Storage.HashMap.getObject(SystemUtil.HashMap.Key.HTML.init.domTypeName)).append(html);
		SystemUtil.Storage.HashMap.remove(SystemUtil.HashMap.Key.HTML.init.htmlNameKey + "2");
		if (SystemUtil.validate.functions({
				obj: backFunction,
				isThow: false
			})) {
			backFunction({
				currentIndex: SystemUtil.Storage.HashMap.getInt(SystemUtil.HashMap.Key.HTML.Set.html.typeName),
				currentDomId: SystemUtil.HashMap.Key.HTML.init.domTypeName,
				currentHtml: SystemUtil.HashMap.Key.HTML.init.htmlNameKey
			});
		}
	},
	/**
	 * 验证是否已经初始化
	 */
	validate: function() {
		if (!SystemUtil.Storage.HashMap.getObject(SystemUtil.HashMap.Key.HTML.init.typeName)) {
			SystemUtil.Exception.Thow(SystemCode.HTML_IS_NOT_INIT);
		}
	},
	Set: {
		/**
		 * html参数
		 * @param {JSON} DataOptions
		 * DataOptions.type 默认id 可以不传
		 * DataOptions.typeName 类型名称 id名称 或者 class名称 必传
		 * DataOptions.value 需要赋值的数据
		 * DataOptions.key 赋值的类型 可取值 "html","text","img","a","input",可自定义  标签的key
		 * (说明：可取值：'html'[更新内容],'text'[更新文本],'img'[更新图片],'a'[更新href链接],input[input标签赋值]默认 'html')
		 * DataOptions.index 列表需要用到索引
		 * DataOptions.filters 需要过滤 字段
		 * DataOptions.success 赋值完成的回调函数 可不传
		 * 
		 */
		publics: function(DataOptions) {
			SystemUtil.validate.obj({
				obj: DataOptions
			});
			if (DataOptions.filters != undefined && DataOptions.filters.length > 0) {
				if (DataOptions.filters.contains(DataOptions.typeName)) {
					return;
				}
			}
			SystemUtil.validate.obj({
				obj: DataOptions.typeName,
				ThowMessage: SystemCode.SET_HTML_VALUE_IS_NOT_NULL
			});
			var type = "#";
			if (DataOptions.type == "class") {
				type = ".";
			};
			var HtmlInitDataOptions = SystemUtil.Storage.HashMap.getObject(SystemUtil.HashMap.Key.HTML.HtmlInitDataOptions)
			var obj = $(type + HtmlInitDataOptions.typeName + " " + type + DataOptions.typeName);
			if (obj.length == 0) {
				return;
			}
			switch (DataOptions.key) {
				case "html":
					obj.html(DataOptions.value);
					break;
				case "text":
					obj.text(DataOptions.value);
					break;
				case "img":
					obj.attr("src", DataOptions.value);
					break;
				case "a":
					obj.attr("href", DataOptions.value);
					break;
				case "input":
					obj.val(DataOptions.value);
					break;
				default:
					obj.attr(DataOptions.key, DataOptions.value);
					break;
			}
			if (DataOptions.index != undefined) {
				obj.attr(DataOptions.type, DataOptions.typeName + DataOptions.index)
			}
			if (SystemUtil.validate.functions({
					obj: DataOptions.success,
					isThow: false
				})) {
				DataOptions.success(DataOptions);
			}
		},
		html: function(id, val) {
			var dom = document.getElementById(id);
			if (dom.length == 0) {
				console.warn("set html id-" + id + ", dosen't not exist")
			}
			dom.innerHTML = val;
		},
		/**
		 * 替换
		 * @param {String} Str 操作的字符串
		 * @param {JSON} obj 
		 * obj.key 操作的key值
		 * obj.val 操作的val
		 */
		replace: function(Str, obj) {
			Str = Str.replace(obj.key, obj.val);
			return Str;
		},
		/**
		 * 替换当前html
		 * @param {JSON} obj 
		 * obj.key 操作的key值
		 * obj.val 操作的val
		 */
		htmlReplace: function(obj) {
			if (typeof(obj) != "object") {
				throw "obj param is not a object";
			}
			if (obj.key == undefined || obj.key == null || obj.key.length < 0) {
				throw "obj.key is null";
			}
			if (obj.val == undefined || obj.val == null || obj.val.length < 0) {
				console.log("obj.val is null");
				return;
			}
			var html = SystemUtil.Storage.HashMap.getObject(SystemUtil.HashMap.Key.HTML.init.domTypeName);
			try {
				html = document.querySelector(html).innerHTML.toString();
				if (html == null || html.length < 0) {
					console.warn("html is null");
					return;
				}
				if (html.search(obj.key) == -1) {
					return;
				}
				html = SystemUtil.HTML.Set.replaceAll(html, {
					key: obj.key,
					val: obj.val
				});
				document.querySelector(SystemUtil.Storage.HashMap.getObject(SystemUtil.HashMap.Key.HTML.init.domTypeName))
					.innerHTML = html;
			} catch (e) {
				//TODO handle the exception
			}
		},
		/**
		 * 替换所有
		 * obj.key 操作的key值
		 * obj.val 操作的val
		 * @param {String} Str
		 * @param {JSON} obj
		 * obj.key 操作的key值
		 * obj.val 操作的val
		 */
		replaceAll: function(Str, obj) {
			Str = Str.replace(new RegExp(obj.key, 'gm'), obj.val);
			return Str;
		}
	},
	Get: {
		html: function() {
			SystemUtil.HTML.validate();
		}
	}

};
/**
 * 数据处理
 */
SystemUtil.Data = {
	/**
	 * 
	 * @param {Object} data 需要过滤处理的数据 只能传Array 和JSONObject类型数据
	 * @param {SystemUtil.Data.DataOptions} specialDataParam 特殊类型的处理参数
	 * 参数说明：
	 * specialDataParam
	 */
	filter: function(data, specialDataParam) {
		/**
		 * DataOptions.type 必传 参数 id or class 默认id 
		 * DataOptions.typeName dom 名称 必传 
		 * DataOptions.success //初始化成功的回调函数
		 * DataOptions.error //错误的回调函数
		 * @param {JSON} HtmlInitDataOptions
		 * 
		 */
		var HtmlInitDataOptions = SystemUtil.Storage.HashMap.getObject(SystemUtil.HashMap.Key.HTML.HtmlInitDataOptions);
		if (HtmlInitDataOptions != undefined) {
			if (typeof(HtmlInitDataOptions.success) != "function") {
				HtmlInitDataOptions.success = function(typeName, html) {
					SystemUtil.Log.log("SystemUtil.HTML.init is Success");
					SystemUtil.validate.isArray(data, function(e) {
						switch (e.result) {
							case true:
								console.log("array");
								SystemUtil.Data.array(data, specialDataParam);
								break;
							default:
								console.log("obj");
								SystemUtil.Data.object(data, specialDataParam);
								break;
						}
					});
				}
			}
			SystemUtil.HTML.init(HtmlInitDataOptions);
		} else {
			SystemUtil.Data.object(data, specialDataParam);
		}
	},
	/**
	 * 集合类型
	 * 
	 * @param {Array} arrayData 集合对象
	 * @param {SystemUtil.Data.DataOptions} specialDataParam 特殊处理参数
	 * DataOptions.type 参数 dom的类型 id 或者class （非必传）
	 * DataOptions.typeName 类型的名称 （必传）
	 * DataOptions.key 键  （可选） 默认‘html’
	 * DataOptions.value 需要赋的值  （可选）
	 * DataOptions.firstFunction 首次的回调函数 （可选）
	 * DataOptions.setPublicsDataSuccess 赋值完成的回调函数
	 * DataOptions.lastFunction 第二次或者最后一次的回调函数（可选）
	 */
	array: function(arrayData, specialDataParam) {
		for (var x in arrayData) {
			SystemUtil.HTML.append()
			var obj = arrayData[x];
			//是否需要回调
			if (SystemUtil.validate.functions({
					obj: specialDataParam.dataFirstFunction,
					isThow: false
				})) {
				var result = specialDataParam.dataFirstFunction(obj);
				if ((typeof(result) == "boolean" && result) || (typeof(result.result) == "boolean") && result
					.result) {
					if (result.result) {
						if (result.objList != undefined && result.objList != null && result.objList.length > 0) {
							for (var x in result.objList) {
								var results = result.objList[x];
								if ((results.typeName != undefined && results.typeName != null && results.typeName
										.length > 0) && (results.value != undefined && results.value != null)) {
									var dataParam = {
										type: results.type == (undefined || null) ? "id" : results.type,
										typeName: results.typeName,
										key: results.key == (undefined || null) ? "html" : results.key,
										value: results.value
									};
									if (result.replace) {

									}
									//处理数据问题
									SystemUtil.HTML.Set.publics({
										type: dataParam.type,
										typeName: dataParam.typeName,
										key: dataParam.key,
										value: dataParam.value,
										index: SystemUtil.Storage.HashMap.getInt(SystemUtil.HashMap.Key
											.HTML.Set.html.typeName),
										success: function(e) {
											if (typeof(result.success) == "function") {
												result.success(dataParam);
											}
										}
									});
								}
							}
						}
					}
					continue;
				}
			}
			for (var y in obj) {
				var objy = obj[y];
				var dataParamObj = specialDataParam[y];
				var value = "";
				if (dataParamObj != undefined && dataParamObj != null && typeof(dataParamObj) == "object") {
					if (dataParamObj.value == undefined || dataParamObj.value == null || dataParamObj == "") {
						value = objy;
					} else {
						value = dataParamObj.value;
					}
				} else {
					dataParamObj = {
						type: "id",
						typeName: y,
						key: "html",
						value: objy
					}
				}
				//是否需要回调
				if (SystemUtil.validate.functions({
						obj: specialDataParam.dataLastFunction,
						isThow: false
					})) {
					var result = specialDataParam.dataLastFunction(dataParamObj);
					if (typeof(result) == "boolean" && result) {
						continue;
					}
				}
				//处理数据问题
				SystemUtil.HTML.Set.publics({
					type: dataParamObj.type,
					typeName: dataParamObj.typeName,
					key: dataParamObj.key,
					value: dataParamObj.value,
					index: SystemUtil.Storage.HashMap.getInt(SystemUtil.HashMap.Key.HTML.Set.html.typeName),
					success: function(e) {
						if (typeof(specialDataParam.setPublicsDataSuccess) == "function") {
							dataParamObj.currentRow = objy;
							specialDataParam.setPublicsDataSuccess(dataParamObj);
						}
					}
				});
			}
		}
	},

	/**
	 * 对象类型
	 * 
	 * @param {Object} objectData 对象JSON类型
	 * @param {Object} specialDataParam  特殊处理参数
	 */
	object: function(objectData, specialDataParam) {
		if (typeof(objectData) == "object") {
			for (var x in objectData) {
				var dataParamObj = specialDataParam[x];
				var value = null;
				if (dataParamObj != undefined && dataParamObj != null) {
					if (dataParamObj.value == undefined || dataParamObj.value == null || dataParamObj.value ==
						"") {
						value = objectData[x]
					} else {
						value = dataParamObj.value;
					}
				} else {
					dataParamObj = {
						type: "id",
						typeName: x,
						key: "html",
						value: objectData[x]
					}
				}
				//是否需要回调
				if (SystemUtil.validate.functions({
						obj: specialDataParam.dataFirstFunction,
						isThow: false
					})) {
					specialDataParam.dataFirstFunction(dataParamObj);
					continue;
				}
				if (typeof(dataParamObj.type) != "string") {
					dataParamObj.type = "id"
				}
				if (typeof(dataParamObj.typeName) != "string") {
					dataParamObj.typeName = x;
				}
				if (dataParamObj.value == undefined || dataParamObj == null) {
					dataParamObj.value = objectData[x];
				}
				//处理数据问题
				SystemUtil.HTML.Set.publics({
					type: dataParamObj.type,
					typeName: dataParamObj.typeName,
					key: dataParamObj.key,
					value: dataParamObj.value,
					success: function(e) {
						console.log("Set.publics result is:" + e);
						if (typeof(specialDataParam.setPublicsDataSuccess) == "function") {
							dataParamObj.currentRow = objy;
							specialDataParam.setPublicsDataSuccess(dataParamObj);
						}
					}
				});
			}
		} else {
			SystemUtil.Log.warn("set object is not a object");
		}
	}
};
/**
 * URL 过滤处理
 */
SystemUtil.URL = {
	/**
	 * 
	 * @param {Boolean}
	 *            isPort 说明:是否返回带有端口的
	 * @param {Boolean}
	 *            isPojectName 是否返回带有本地项目名称
	 * @return {String} 返回url
	 */
	Filter: function(isPort, isPojectName) {
		/**
		 * 获取当前网址，如： http://localhost:8080/shanghuibao/share/meun.jsp
		 */
		curWwwPath = window.location.href;
		/**
		 * 获取主机地址之后的目录，如： shanghuibao/share/meun.jsp
		 */
		pathName = window.location.pathname;
		pos = curWwwPath.indexOf(pathName);
		/**
		 * 获取主机地址，如： http://localhost:8080
		 */
		localPortPath = curWwwPath.substring(0, pos);
		if (SystemUtil.Storage.Local.getVal("is_usable")) {
			localPortPath = path.localPortPath;
		}
		/**
		 * 获取主机地址，如： http://localhost:
		 */
		localPath = curWwwPath.substring(0, pos - 4);
		if (SystemUtil.Storage.Local.getVal("is_usable")) {
			localPath = path.localPath;
		}
		/**
		 * // 获取带"/"的项目名，如：/shanghuibao
		 */
		projectName = pathName
			.substring(0, pathName.substr(1).indexOf('/') + 1);
		if (SystemUtil.Storage.Local.getVal("is_usable") && isPojectName == null || isPojectName == null) {
			projectName = path.projectName;
		}
		var returnUrl = localPath;
		if (isPort != undefined && isPort != null) {
			returnUrl = localPath + isPort;
		} else {
			returnUrl = localPortPath;
		}
		if (isPojectName != undefined && isPojectName != null &&
			isPojectName != "") {
			returnUrl += "/" + isPojectName;
		} else {
			returnUrl += projectName;
		}
		return returnUrl + "/";
	},
	/**
	 * 获取URL 需求参数 可以不传
	 */
	params: {
		port: 8080,
		projectName: null,
	},
	/**
	 * @description 实例
	 *              ：{port:"8080",projectName:"huihebiz",controllerName:"qrcode",actionName:"create"}
	 *              说明：参数可不传，一般情况下需要传
	 * @param {SystemUtil.URL.params}
	 *            dataOptions
	 * 
	 */
	GetUrl: function(dataOptions) {
		var url = null;
		if (dataOptions == undefined || dataOptions == null) {
			dataOptions = {
				port: null,
				projectName: null
			}
			console.warn("dataOptions is undefined!");
		} else {
			isport = dataOptions.port == undefined || dataOptions.port == null;
			isPojectName = dataOptions.projectName == undefined ||
				dataOptions.projectName == null;
		}
		if (typeof(dataOptions) != "object") {
			console.warn("dataOptions is not a object!!");
		}
		url = SystemUtil.URL.Filter(dataOptions.port, dataOptions.projectName);
		//		if(!isport) {
		//			url += dataOptions.port;
		//		}
		//		if(!isPojectName) {
		//			url += "/" + dataOptions.projectName + "/";
		//		}
		if (dataOptions.controllerName != undefined &&
			dataOptions.controllerName != "") {
			if (dataOptions.actionName != undefined &&
				dataOptions.actionName != "") {
				url += dataOptions.controllerName + "/" +
					dataOptions.actionName + ".do";
			} else {
				console.error("dataOptions.actionName is not defined!");
			}

		} else {
			console.error("dataOptions.controllerName is not defined");
		}
		return url;
	}
};
/**
 * 原生函数
 */
SystemUtil.App.WebView = {
	/**
	 * WebView对象
	 * 
	 * @abstract
	 */
	APP_TYPE: {},
	web_view_name: "",
	/**
	 * 初始化
	 * 
	 * @param {String}
	 *            name 预加载
	 */
	init: function(namePath) {
		SystemUtil.App.WebView.web_view_name = namePath;
		if (window.plus) {
			SystemUtil.App.WebView.plusReady();
		} else {
			document.addEventListener('plusready', SystemUtil.App.WebView
				.plusReady(), false);
		}
	},
	/**
	 * 赋值预加载的页面路径名称
	 * 
	 * @param {Object}
	 *            webcViewName
	 */
	setWebView: function(webcViewName) {
		SystemUtil.App.WebView.web_view_name = webcViewName;
	},
	/**
	 * 获取预加载的页面路径名称
	 */
	setWebView: function() {
		return SystemUtil.App.WebView.web_view_name;
	},
	/**
	 * 设置Webview窗口是否阻塞加载页面中使用的网络图片
	 * 
	 * @param {Object}
	 *            isBlock
	 */
	setBlockNetworkImage: function(isBlock) {
		wp.setBlockNetworkImage(isBlock);
	},
	/**
	 * 原生切换动画效果
	 * 
	 * @param {Object}
	 *            type
	 */
	createWebView: function(type) {
		if (type == undefined || type == "" || type == null) {
			type = SystemUtil.WebView.AnimationAllType.POPIN;
		}
		wp.show(type);
	},
	/**
	 * 加载URL
	 * 
	 * @param {Object}
	 *            URL 路径
	 */
	loadURL: function(URL) {
		wp.loadURL(URL);
	},
	/**
	 * H5 plus事件处理 预加载
	 * 
	 * @author yangchengfu
	 */
	plusReady: function() {
		SystemUtil.App.WebView.nativeUI = new plus.nativeUI;
		SystemUtil.App.WebView.nativeObj = new plus.nativeObj;
		ws = new plus.webview.currentWebview();
		wo = ws.opener();
		wp = plus.webview.create(webView.web_view_name, webView.web_view_name, {
			scrollIndicator: 'none',
			scalable: true,
			popGesture: 'none'
		}, {
			preate: true
		});
	},
	/**
	 * 调用系统第三方地图
	 * 
	 * @author yangchengfu
	 */
	invokeThirdPartyMaps: function(cityLng, cityLat, disLat, disLng, cityName) {
		var cityLngLat = new plus.maps.Point(cityLat, cityLng); // 城市
		var dis = new plus.maps.Point(disLat, disLng); // 地点
		plus.maps.openSysMap(cityLngLat, cityName, dis);
	},
	HideOrShow: {

	},
	/**
	 * obj.showObj 显示的webview对象
	 * obj.isClose 是否关闭
	 * obj.hideType 隐藏的类型
	 * obj.showType 显示的类型
	 * @param {Object} obj
	 * obj.showObj 显示的webview对象
	 * obj.isClose 是否关闭
	 * obj.hideType 隐藏的类型
	 * obj.showType 显示的类型
	 */
	back: function(obj) {
		if (obj == undefined) {
			obj = {};
		}
		SystemUtil.App.WebView.HideOrShow.DataOptions.id_obj = "";
		if (typeof(obj.hideType) != "string" || obj.hideType == null) {
			obj.hideType = SystemUtil.App.WebView.AnimationAllType.Out.AUTO;
		}
		var view = plus.webview.currentWebview();
		view.canBack(function(e) {
			if (!e.canBack) {
				obj.isClose = true;
			}
			if (typeof(obj.isClose) == "boolean" && obj.isClose) {
				if (typeof(obj.showObj) != "undefined" && obj.showObj != null) {
					if (typeof(obj.showType) != "string" || obj.showType == null) {
						obj.showType = SystemUtil.App.WebView.AnimationAllType.In.POP_IN;
					}
					var path = SystemUtil.App.WebView.Get.getCurrentWebView().id;
					SystemUtil.App.WebView.Show.show({
						id_obj: obj.showObj,
						animationType: obj.showType,
						BackFunction: {
							successFunction: function() {
								plus.webview.close(path);
							}
						}
					});
				} else {
					SystemUtil.App.WebView.Hide.close({
						animationType: obj.hideType
					});
				}
			} else {
				SystemUtil.App.WebView.Hide.hide({
					animationType: obj.hideType
				});
			}
		})
	},
	AnimationAllType: {
		/**
		 * 显示
		 */
		In: {
			/**
			 * 原生动画从右侧平移
			 */
			POP_IN: "pop-in",

			/**
			 * 从右侧横向滑出
			 */
			SLIDE_IN_RIGHT: "slide-in-right",
			/**
			 * 从左侧横向滑出
			 */
			SLIDE_IN_LEFT: "slide-in-left",
			/**
			 * 从上侧竖向滑出
			 */
			SLIDE_IN_TOP: "slide-in-top",
			/**
			 * 从下侧竖向滑出
			 */
			SLIDE_IN_BOTTOM: "slide-in-bottom",
			/**
			 * 从小到大逐渐放大
			 */
			ZOOM_OUT: "zoom-out",
			/**
			 * 从小到大并逐渐透明显示
			 */
			ZOOM_FADE_OUT: "zoom-fade-out",
			/**
			 * 从透明到不透明逐渐显示
			 */
			FADE_IN: "fade-in",
			/**
			 * 以x轴从上到下翻转效果
			 */
			FLIP_X: "flip-x",
			/**
			 * 以x轴从下到上翻转转效果
			 */
			FLIP_RX: "flip-rx",
			/**
			 * 以y轴从左到右翻转效果
			 */
			FLIP_Y: "flip-y",
			/**
			 * 以y轴从右到左翻转效果
			 */
			FLIP_RY: "flip-ry",
			/**
			 * 向前翻书动画效果
			 */
			PAGE_FORWARD: "page-forward",
			/**
			 * 无动画
			 */
			NONE: "none"
		},
		/**
		 * 隐藏
		 */
		Out: {
			/**
			 * 自动选择显示窗口相对于的动画效果。
			 */
			AUTO: "auto",
			/**
			 * 立即关闭页面，无任何动画效果。 此效果忽略动画时间参数，立即关闭。
			 */
			NONE: "none",
			/**
			 * 页面从屏幕中横向向右侧滑动到屏幕外关闭。
			 */
			SLIDE_OUT_RIGHT: "slide-out-right",
			/**
			 * 页面从屏幕中横向向左侧滑动到屏幕外关闭。
			 */
			SLIDE_OUT_LEFT: "slide-out-left",
			/**
			 * 页面从屏幕中竖向向上侧滑动到屏幕外关闭。
			 */
			SLIDE_OUT_TOP: "slide-out-top",
			/**
			 * 页面从屏幕中竖向向下侧滑动到屏幕外关闭
			 */
			SLIDE_OUT_BOTTOM: "slide-out-bottom",
			/**
			 * 页面从不透明到透明逐渐隐藏关闭
			 */
			FADE_OUT: "fade-out",
			/**
			 * 页面逐渐向页面中心缩小关闭
			 */
			ZOOM_IN: "zoom-in",
			/**
			 * 页面逐渐向页面中心缩小并且从不透明到透明逐渐隐藏关闭
			 */
			ZOOM_FADE_IN: "zoom-fade-in",
			/**
			 * 页面从屏幕右侧滑出消失，同时上一个页面带阴影效果从屏幕左侧滑入显示
			 */
			POP_OUT: "pop-out"
		}
	},
	/**
	 * webViewObjArry.url 路径
	 * webViewObjArry.type 关闭的动画效果
	 * webViewObjArry.time 关闭的时间
	 * webViewObjArry.extras 关闭的扩展参数
	 * 关闭预加载的webview
	 * @param {Array} webViewObjArry
	 * webViewObjArry.url 路径
	 * webViewObjArry.type 关闭的动画效果
	 * webViewObjArry.time 关闭的时间
	 * webViewObjArry.extras 关闭的扩展参数
	 */
	close: function(webViewObjArry) {
		for (var x in webViewObjArry) {
			try {
				var closeWeb;
				if (webViewObjArry[x].url == null || webViewObjArry[x].url == undefined) {
					closeWeb = SystemUtil.App.WebView.Get.getCurrentWebView();
				} else {
					closeWeb = SystemUtil.App.WebView.Get.webViewId(webViewObjArry[x].url)
				}
				if (closeWeb == null) {
					continue;
				}
				var type = webViewObjArry[x].type;
				if (type == undefined || type == null) {
					type = SystemUtil.App.WebView.AnimationAllType.Out.AUTO;
				}
				closeWeb.close(type, webViewObjArry[x].time, webViewObjArry[x].extras);
			} catch (e) {
				//TODO handle the exception
				console.log("close webView error");
				continue;
			}
		}
	},
	key: {
		addEventListener: function(type) {
			if (type == undefined || type == null || type == "") {
				type = "backbutton";
			}
			plus.key.addEventListener(type, function(e) {
				if (type == "backbutton") {
					SystemUtil.App.WebView.back();
				}
			});
		}
	}
};
SystemUtil.App.WebView.Get = {
	/**
	 * 获取所有WebView 窗口对象
	 * 
	 * @return{Array}
	 */
	webViewAll: function() {
		return plus.webview.all()[0];
	},
	/**
	 * 获取当前窗口的URL路径
	 * 
	 * @return {String}
	 */
	currentUrl: function() {
		return ws.getURL();
	},
	/**
	 * 查找指定标识的WebviewObject窗口
	 * 
	 * @param {Object}
	 *            id
	 */
	webViewId: function(id) {
		return plus.webview.getWebviewById(id)
	},
	/**
	 * 获取首页的窗口对象
	 */
	getLaunchWebview: function() {
		return plus.webview.getLaunchWebview();
	},
	/**
	 * 获取应用显示栈顶的窗口对象
	 */
	getTopWebview: function() {
		return plus.webview.getTopWebview();
	},
	/**
	 * 获取当前窗口对象
	 */
	getCurrentWebView: function() {
		return plus.webview.currentWebview();
	}

};
/**
 * 创建 预加载页面
 */
SystemUtil.App.WebView.Create = {
	webView_create: null,
	/**
	 * 多个页面预加载
	 * @param {Array} arrayDataOptions
	 *  DataOptions.url 需要预加载页面的路径 本地网址或者网络地址(必传)
	 * DataOptions.webview_id 预加载页面的唯一标识(可选,默认是url地址)
	 * DataOptions.Styles 预加载的页面样式(可选，参数请参考{SystemUtil.App.WebView.Create.DataOptions.Styles})
	 * DataOptions.Extras 预加载页面的扩展参数(可选,参数请参考{SystemUtil.App.WebView.Create.DataOptions.Extras})
	 */
	arrayInit: function(arrayDataOptions) {
		for (var x in arrayDataOptions) {
			SystemUtil.App.WebView.Create.init(arrayDataOptions[x]);
		}
	},
	/**
	 * 创建Webview窗口对象 说明:创建时 页面还不会显示，需要调用show方法才会显示 dataOptions.url 必传
	 * 
	 * @param {SystemUtil.App.WebView.Create.DataOptions}
	 *            DataOptions
	 * DataOptions.loaded 页面预加载完成
	 * DataOptions.url 需要预加载页面的路径 本地网址或者网络地址(必传)
	 * DataOptions.webview_id 预加载页面的唯一标识(可选,默认是url地址)
	 * DataOptions.Styles 预加载的页面样式(可选，参数请参考{SystemUtil.App.WebView.Create.DataOptions.Styles})
	 * DataOptions.Extras 预加载页面的扩展参数(可选,参数请参考{SystemUtil.App.WebView.Create.DataOptions.Extras})
	 */
	init: function(DataOptions) {
		var options = SystemUtil.App.WebView.Create.DataOptions;
		if (SystemUtil.validate.obj({
				obj: DataOptions
			})) {
			for (var x in DataOptions) {
				options[x] = DataOptions[x];
				try {
					if (DataOptions[x].indexOf("onload") != -1) {
						for (var y in DataOptions[x]) {
							options["onload"][y] = DataOptions[x][y];
						}
					}
					if (x == "Styles") {
						for (var i in DataOptions[x]) {
							options[x].i = DataOptions[x][i];
						}
					}
				} catch (e) {
					//TODO handle the exception
				}
			}
		}
		if (options.url == null) {
			SystemUtil.Exception.Thow(SystemCode.URL_IS_NOT_NULL);
		}
		if (options.Styles == null || options.Styles == undefined) {
			options.Styles = {
				zindex: SystemUtil.Storage.Local.getInt("zindex"),
				scrollIndicator: "none"
			}
		} else {
			options.Styles.zindex = SystemUtil.Storage.Local.getInt("zindex");
			options.Styles.scrollIndicator = 'none';
		}
		if (!SystemUtil.validate.obj({
				obj: options.webView_id,
				isThow: false
			})) {
			options.webview_id = filterPath(options.url);
		}
		if (SystemUtil.App.WebView.Get.webViewId(options.webview_id) != null) {
			console.log("options.webview_id is init")
			if (typeof(options.loaded) == "function") {
				options.loaded(SystemUtil.App.WebView.Get.webViewId(options.webview_id));
			}
			return;
		}
		SystemUtil.App.WebView.Create.webView_create = plus.webview
			.create(options.url, options.webview_id, options.Styles,
				options.Extend);
		SystemUtil.App.WebView.Create.webView_create.addEventListener("loaded", function(e) {
			if (typeof(options.loaded) == "function") {
				options.loaded();
			}
		})
		if (options.parentWebView != undefined) {
			options.parentWebView.append(SystemUtil.App.WebView.Create.webView_create);
			if (options.isParentHide) {
				SystemUtil.App.WebView.Create.webView_create.hide();
			}

		}
		if (SystemUtil.validate.obj({
				obj: options.jsFile,
				isThow: false
			})) {
			SystemUtil.App.WebView.Create.webView_create
				.appendJsFile(options.jsFile);
		}
		if (SystemUtil.validate.obj({
				obj: options.executeJs,
				isThow: false
			})) {
			SystemUtil.App.WebView.Create.webView_create
				.evalJS(options.executeJs);
		}
	},
	/**
	 * 显示预加载的 窗口 参数请参考：SystemUtil.App.WebView.HideOrShow.DataOptions
	 * 
	 * @param {SystemUtil.App.WebView.HideOrShow.DataOptions}
	 *            DataOptions
	 * DataOptions.animationType 动画类型(可选)
	 * DataOptions.time 动画延迟的时间(可选)
	 * DataOptions.BackFunction.successFunction 显示完成的回调函数(可选)
	 * DataOptions.Extras 扩展参数(可选)
	 */
	show: function(DataOptions) {
		var options = SystemUtil.App.WebView.Create.filterData(DataOptions);
		SystemUtil.App.WebView.Create.webView_create.show(
			options.animationType, options.time,
			options.BackFunction.successFunction, options.Extras);
	},
	/**
	 * 隐藏预加载的 窗口 参数请参考：SystemUtil.App.WebView.HideOrShow.DataOptions
	 * 
	 * @param {SystemUtil.App.WebView.HideOrShow.DataOptions}
	 *            DataOptions
	 * DataOptions.animationType 动画类型(可选) 参数请参考{SystemUtil.App.WebView.AnimationAllType }
	 * DataOptions.time 动画延迟的时间(可选)
	 * DataOptions.Extras 扩展参数(可选)
	 */
	hide: function(DataOptions) {
		var options = SystemUtil.App.WebView.Create.filterData(DataOptions);
		SystemUtil.App.WebView.Create.webView_create.hide(
			options.animationType, options.time, options.Extras);

	},
	filterData: function(DataOptions) {
		var options = SystemUtil.App.WebView.HideOrShow.DataOptions;
		if (SystemUtil.validate.obj({
				obj: DataOptions,
				isThow: false
			})) {
			for (var x in DataOptions) {
				options[x] = DataOptions[x];
			}
		}
		try {
			SystemUtil.validate.functions({
				obj: options.BackFunction.successFunction,
				isThow: false
			});
		} catch (e) {
			//TODO handle the exception
		}
		return options;
	},
	/**
	 * 是否开启硬件加速
	 */
	isHardwareAccelerated: function() {
		return SystemUtil.App.WebView.Create.webView_create
			.isHardwareAccelerated();
	}
};

SystemUtil.App.WebView.Show = {
	showObj: null,
	/**
	 * 参数 请参考：SystemUtil.App.WebView.HideOrShow.DataOptions DataOptions.url 必传
	 * 
	 * @param {SystemUtil.App.WebView.HideOrShow.DataOptions}
	 *            DataOptions
	 * DataOptions.id_obj(必传)<br\>
	 * DataOptions.animationType(可选) 动画效果 参数请参考{SystemUtil.App.WebView.AnimationAllType }<br\>
	 * DataOptions.time(可选) 延迟的时间<br\>
	 * DataOptions.BackFunction.successFunction 显示成功的回调函数(可选)<br\>
	 */
	show: function(DataOptions) {
		var options = SystemUtil.App.WebView.HideOrShow.DataOptions;
		if (SystemUtil.validate.obj({
				obj: DataOptions
			})) {
			for (var x in DataOptions) {
				options[x] = DataOptions[x];
			};
		}
		SystemUtil.validate.obj({
			obj: options.id_obj
		});
		try {
			SystemUtil.validate.functions({
				obj: options.BackFunction.successFunction
			});
		} catch (e) {
			// TODO handle the exception
			SystemUtil.Log.warn("options.BackFunction is not a Function");
		}
		SystemUtil.App.WebView.Show.showObj = plus.webview.show(options.id_obj,
			options.animationType, options.time,
			options.BackFunction.successFunction, options.Extras);
	}
};
/**
 * 原生隐藏函数
 */
SystemUtil.App.WebView.Hide = {
	/**
	 * 隐藏窗口的方法 参数说明： id_obj：需要隐藏的窗口ID 或者对象 非必传 默认是当前窗口 animationType： 窗口隐藏的动画 可选
	 * duration：隐藏停留的时间 默认是 1000/ms 可选 extend:扩展参数 可选 backFunction 隐藏完成的回调函数
	 * 
	 * @param {SystemUtil.App.WebView.HideOrShow.DataOptions}
	 *            DataOptions
	 */
	hide: function(DataOptions) {
		var options = SystemUtil.App.WebView.HideOrShow.DataOptions;
		if (SystemUtil.validate.obj({
				obj: DataOptions,
				isThow: false
			})) {
			for (var x in DataOptions) {
				options[x] = DataOptions[x];
			}
		}
		if (options.animationType == SystemUtil.App.WebView.AnimationAllType.In.POP_IN) {
			options.animationType = SystemUtil.App.WebView.AnimationAllType.Out.AUTO
		}
		if (options.id_obj == null || options.id_obj == "") {
			options.id_obj = plus.webview.currentWebview();
		}
		plus.webview.hide(options.id_obj, options.animationType, options.time);
		if (options.backFunction != null) {
			options.backFunction();
		}
	},
	/**
	 * 关闭窗口的方法 参数说明： id_obj：需要关闭的窗口ID 或者对象 非必传 默认是当前窗口 animationType： 窗口隐藏的动画 可选
	 * duration：隐藏停留的时间 默认是 1000/ms 可选 extend:扩展参数 可选 backFunction 隐藏完成的回调函数
	 * 
	 * @param {SystemUtil.App.WebView.HideOrShow.DataOptions}
	 *            DataOptions
	 */
	close: function(DataOptions) {
		var options = SystemUtil.App.WebView.HideOrShow.DataOptions;
		if (SystemUtil.validate.obj({
				obj: DataOptions,
				isThow: false
			})) {
			for (var x in DataOptions) {
				options[x] = DataOptions[x];
			}
		}
		if (options.animationType == SystemUtil.App.WebView.AnimationAllType.In.POP_IN) {
			options.animationType = SystemUtil.App.WebView.AnimationAllType.Out.AUTO
		}
		if (options.id_obj == null || options.id_obj == "") {
			options.id_obj = plus.webview.currentWebview();
		}
		plus.webview.close(options.id_obj, options.animationType, options.time);
		if (options.backFunction != null) {
			options.backFunction();
		}
	}
}
SystemUtil.App.WebView.Open = {
	webViewObj: null,
	/**
	 * DataOptions.url 打开的路径
	 * DataOptions.webview_id  窗口标识或者窗口对象
	 * DataOptions.styles  打开的样式  参考
	 * DataOptions.aniShow  动画类型
	 * DataOptions.time  动画的时间
	 * DataOptions.successBackFunction  打开完成的回调函数
	 * 或者直接赋值 SystemUtil.App.WebView.open的参数
	 * @param {SystemUtil.App.WebView.open} DataOptions
	 * 
	 */
	show: function(DataOptions) {
		var options = SystemUtil.App.WebView.Open.DataOptions;
		if (SystemUtil.validate.obj({
				obj: DataOptions,
				isThow: false
			})) {
			for (var x in DataOptions) {
				options[x] = DataOptions[x];
			}
		}
		if (options.webview_id == null) {
			options.webviewId = options.url;
		}
		SystemUtil.App.WebView.Open.webViewObj = plus.webview.open(options.url, options.webview_id, options.styles,
			options.aniShow, options.time, options.successBackFunction);
	}
}
SystemUtil.App.WebView.AppMsg = {
	/**
	 * 加载等待对话框
	 * 
	 * @param {String}
	 *            content
	 * @param {JSObject}
	 *            jsonStyle
	 */
	loadMsg: function(content, jsonStyle) {
		return plus.nativeUI.showWaiting(content, jsonStyle);
	},
	/**
	 * 显示自动消失的提示消息
	 * @param {Object} message 自动消失的内容
	 * @param {Object} options  自动消息框样式参数
	 * optins.icon 提示消息框上显示的图标
	 * options.duration 提示消息框显示的时间 3.5s
	 * options.align 提示消息框在屏幕中的水平位置
	 * options.verticalAlign 提示消息在屏幕中的垂直位置 可选值为"top"、"center"、"bottom"，分别为垂直居顶、居中、居底，未设置时默认值为"bottom"。
	 */
	toast: function(message, options) {
		plus.nativeUI.toast(message, options);
	},
	/**
	 * 
	 * @param {Object} content 
	 * @param {Object} alertFunction
	 * @param {Object} title
	 * @param {Object} buttonName
	 */
	showMsg: function(content, alertFunction, title, buttonName) {
		plus.nativeUI.alert(content, alertFunction, title, buttonName);
	},
	/**
	 * 关闭等待对话框
	 */
	closeMsg: function() {
		plus.nativeUI.closeWaiting();
	},
	/**
	 * 原生两个按钮 对话框
	 * 
	 * @param {String}
	 *            title
	 * @param {Function}
	 *            backFunction
	 * @param {String}
	 *            content
	 * @param {Array}
	 *            btnArray
	 */
	confirm: function(title, backFunction, content, btnArray) {
		plus.nativeUI.confirm(title, backFunction, content, btnArray)
	},
	/**
	 * 弹出系统信息输入框
	 * 
	 * @param {Object}
	 *            title
	 * @param {Object}
	 *            backFunction
	 * @param {Object}
	 *            content
	 * @param {Object}
	 *            tip
	 * @param {Object}
	 *            arratBtn
	 */
	prompt: function(title, backFunction, content, tip, arratBtn) {
		plus.nativeUI.prompt(content, backFunction, title, tip);
	},
	// 显示原生日期
	showDate: function() {
		plus.nativeUI.PickDateOption();
	},
	// 显示原生时间
	showTime: function() {
		plus.nativeUI.PickTimeOption();
	},

	/**
	 * 弹出原生sheet 选择控件
	 * SystemUtil.App.WebView.AppMsg.DataOptions
	 * @param {SystemUtil.App.WebView.AppMsg.DataOptions} DataOptions 
	 * DataOptions.title 标题
	 * DataOptions.cancel 取消按钮的文字内容
	 * DataOptions.buttons sheet 列表按钮 可取值(title,style(destructive”表示警示按钮样式,“default”表示默认按钮样式))
	 * DataOptions.backFunction
	 */
	actionSheet: function(DataOptions) {
		var options = SystemUtil.App.WebView.AppMsg.DataOptions;
		SystemUtil.validate.obj({
			obj: DataOptions
		})
		for (var x in DataOptions) {
			options[x] = DataOptions[x];
		}
		SystemUtil.validate.functions({
			obj: options.backFunction
		});
		plus.nativeUI.actionSheet(options, function(e) {
			SystemUtil.Storage.HashMap.put("index", e.index - 1);
			options.backFunction(e, options.buttons[e.index - 1]);
		});
	}
};
/**
 * 对手机通讯录操作的函数
 * 
 * @author yangchengfu
 */
SystemUtil.App.mobile = {
	bookArray: [],
	multiple: true,
	name: null,
	mobile: null,
	/**
	 * 对手机操作的公用函数
	 * 
	 * @param {String}
	 *            name 名称
	 * @param {String}
	 *            mobile 手机号码
	 * @param {Function}
	 *            onSuccessFunction 成功的回调函数
	 * @param {Function}
	 *            onErrorFunction 错误的回调函数
	 * @author yangchengfu
	 */
	addressbookAll: function(name, mobile, onSuccessFunction, onErrorFunction) {
		plus.contacts.getAddressBook(plus.contacts.ADDRESSBOOK_PHONE, function(
			addressbook) {
			onSuccessFunction(addressbook, name, mobile);
		}, function(e) {
			onErrorFunction(e);
		});
	},
	/**
	 * 保存联系人信息
	 * 
	 * @param {Object}
	 *            name 姓名
	 * @param {Object}
	 *            mobile 手机号码
	 * @author yangcehngfu
	 */
	save: function(name, mobile) {
		SystemUtil.App.mobile.addressbookAll(name, mobile, saveOnSuccess,
			saveOnError)
	},
	/**
	 * 加载联系人操作成功 可以操作
	 * 
	 * @param {Object}
	 *            addressbook 返回对象
	 * @param {Object}
	 *            name 姓名
	 * @param {Object}
	 *            mobile 手机号码
	 * @author yangcehngfu
	 */
	saveOnSuccess: function(addressbook, name, mobile) {
		// 向通讯录中添加联系人
		var contact = addressbook.create();
		contact.name = {
			givenName: name
		};
		contact.phoneNumbers = [{
			type: "手机",
			value: mobile,
			preferred: true
		}];
		contact.save();
	},
	/**
	 * 保存联系人错误 回调
	 * 
	 * @param {Object}
	 *            e
	 * @author
	 */
	saveOnError: function(e) {
		console.log(e.Message);
	},
	/**
	 * 查询联系人 如果 联系人姓名和手机号码为null的话 查询全部联系人信息
	 * 
	 * @param {Object}
	 *            name 查询的联系人姓名
	 * @param {Object}
	 *            mobile 查询的联系人号码
	 * @param {Boolean}
	 *            multiple 是否查询多个
	 * @author yangchengfu
	 */
	find: function(name, mobile, multiples) {
		if (multiples == undefined || multiples == null || multiples == "") {
			multiple = multiples;
		}
		SystemUtil.App.mobile.addressbookAll(name, mobile, findOnSuccesss,
			findOnError);
	},
	findOnSuccesss: function(books, name, mobile) {
		books.find(bookArray, function(books) {
			SystemUtil.App.mobile.findOnSuccess(books);
		}, function(e) {
			findOnError(e);
		}, {
			multiple: multiple
		});
		console.log(books);
	},
	findOnSuccess: function(books) {
		console.log(books.length);
	},
	findOnError: function(e) {
		console.log(e);
	}
};
/**
 * 请求对象
 * 
 * @author yangchengfu
 */
SystemUtil.HttpClientRequest = {
	/**
	 * 参数说明： 
	 * 参数请参考:{SystemUtil.HttpClientRequest.params} 
	 *  dataOptions.controllerName (必传)，
	 * dataOptions.acationName必传,
	 * dataOptions.message 消息提示的内容
	 * dataOptions.messageExtend 消息提示内容的扩展参数
	 * dataOptions.successBackFunction 成功回调 (必传)
	 * dataOptions.errorBackFunction 错误的回调 (可选) 
	 * 如果夸端口请求数据 必须把contentType去掉 不需要传
	 * 
	 * @param {SystemUtil.HttpClientRequest.params}
	 *            dataOptions 
	 * dataOptions.controllerName (必传)，
	 * dataOptions.acationName必传,
	 * DataOptions.isLocal 是否本地请求
	 * dataOptions.successBackFunction 成功回调 (必传)
	 * dataOptions.errorBackFunction 错误的回调 (可选) 
	 * @param {SystemUtil.Data.DataOptions} specialDataParam 特殊处理参数
	 * DataOptions.type 参数 dom的类型 id 或者class （非必传）
	 * DataOptions.typeName 类型的名称 （必传）
	 * DataOptions.key键  （可选） 默认‘html’
	 * DataOptions.value 需要赋的值  （可选）
	 * DataOptions.dataFirstFunction // 数据处理的首次回调函数(可选)
	 * DataOptions.dataLastFunction //数据处理 第二次的回调函数(可选)
	 * 
	 * @param{JSON} HtmlInitDataOptions html初始化的参数(可选) 列表时必传  数组类型时 必传html初始化的参数
	 * DataOptions.domType dom 的类型 可选(id 或者class) 默认id
	 * DataOptions.domTypeName dom名称(如：id的名称或者class的名称)
	 * DataOptions.successBackFunction //初始化成功的回调函数 (可选)
	 * DataOptions.errorBackFunction //错误的回调函数(可选)
	 * 
	 * 如果夸端口请求数据 必须把contentType去掉 不需要传
	 */
	Execute: function(dataOptions, specialDataParam, HtmlInitDataOptions) {
		var defaultOption = SystemUtil.HttpClientRequest.params;

		if (typeof dataOptions.message == 'string') {
			if (dataOptions.message == "show") {
				dataOptions.message = "数据加载中";
			}
			SystemUtil.App.WebView.AppMsg.loadMsg(dataOptions.message, dataOptions.messageExtend);
		}

		SystemUtil.HttpClientRequest.FilterParams(defaultOption, dataOptions, function(e) {
			SystemUtil.HttpClientRequest.HttpClientRequestSend(e, function(data, xhr, x) {
				SystemUtil.validate.isArray(data, function(e) {
					if (e.result) {
						if (HtmlInitDataOptions == undefined && typeof(
								HtmlInitDataOptions) != "object") {
							SystemUtil.Exception.Thow(SystemCode.HTML_INIT_DATA_OPTIONS);
						}
						SystemUtil.Storage.HashMap.put(SystemUtil.HashMap.Key.HTML.HtmlInitDataOptions,
							HtmlInitDataOptions);
					}
				})
				SystemUtil.Data.filter(data, specialDataParam);
			}, HtmlInitDataOptions);
		});
	},
	/**
	 * 参数处理
	 * 
	 * @param {Object}
	 *            dataOptions
	 */
	FilterParams: function(defaultOption, dataOptions, backFunction) {
		var options = defaultOption;

		if (dataOptions == undefined || typeof(dataOptions) != "object") {
			console.warn("dataOptions is not defined");
		}
		if (SystemUtil.validate.obj({
				obj: dataOptions,
				isThow: false
			})) {
			for (var x in dataOptions) {
				options[x] = dataOptions[x];
			}
		}
		if (options.controllerName != undefined &&
			options.controllerName != "" &&
			options.actionName != undefined &&
			options.actionName != "" && !options.isLocal) {
			options.url = SystemUtil.URL
				.GetUrl(options);
		} else {
			options.data = {
				data: SystemUtil.JSON.ObjectToString(options.data),
				controName: options.controllerName,
				actionName: options.actionName
			}
			if (options.projectName == "message") {
				options.controllerName = "comm";
				options.actionName = "messageServer";
			} else {
				options.controllerName = "entrance";
				options.actionName = "server";
			}
			options.url = SystemUtil.URL
				.GetUrl(options);
		}
		if (!SystemUtil.validate.obj({
				obj: options.url,
				isThow: false
			})) {
			if (SystemUtil.validate.obj({
					obj: options.controllerName,
					ThowMessage: SystemCode.CONTRILLERNAME_IS_NOT_NULL
				}) || SystemUtil.validate.obj({
					obj: options.actionName,
					ThowMessage: SystemUtil.ACTIONnAME_IS_NOT_NULL
				})) {

			}
		}
		if (options.isGetParam &&
			options.data.length > 0) {
			var data = options.data;
			var x = 0;
			var params = "?";
			for (var i in data) {
				x++;
				params += i + "=" + data[i]
				if (data.length != x) {
					params += "&";
				}
			}
			options.url += params;
		}
		if (typeof(backFunction) == "function") {
			backFunction(options);
		}
	},
	/**
	 * 提交
	 */
	HttpClientRequestSend: function(setOption, backFunction, html) {
		var params = setOption;
		if (typeof(params.data) == "object") {

			params.data = JSON.stringify(params.data);

		}
		SystemUtil.Log.log("req url is :" + params.url);
		ajaxAbort = $
			.ajax({
				type: params.type,
				url: params.url,
				data: params.data,
				async: params.async,
				cache: false,
				contentType: params.contentType,
				success: function(data, xhr, x) {
					try {
						SystemUtil.App.WebView.AppMsg.closeMsg();
					} catch (e) {
						//TODO handle the exception
					}
					debugger;
					console.debug("request result data" + JSON.stringify(data));

					if (typeof(data) != "object") {
						try {
							data = SystemUtil.JSON.StringToObject(data);
						} catch (e) {
							console.warn("data is object");
						}
					}
					if (SystemUtil.validate.functions({
							obj: params.successBackFunction,
							isThow: false
						})) {
						var result = params.successBackFunction(data, xhr);
						if (result != undefined && result != null) {
							data = result;
						} else {
							return;
						}
					}
					if (SystemUtil.validate.functions({
							obj: backFunction,
							isThow: false
						})) {
						backFunction(data, xhr, x);
					}
				},
				error: function(e, status, xhr) {
					SystemUtil.App.WebView.AppMsg.closeMsg();
					SystemUtil.App.WebView.AppMsg.toast("请求错误");
					if (params.errorBackFunction != undefined &&
						typeof(params.errorBackFunction) == "function") {
						params
							.errorBackFunction(e, status, xhr);
					}
				}
			});
	}
};
SystemUtil.App.QRCode = {
	ScanObj: null,
	/**
	 * 初始化 扫描二维码 必备的参数 SystemUtil.App.QRCode.ScanOptions.webViewId 必传 扫描控件显示的位置
	 * SystemUtil.App.QRCode.ScanOptions.qrcodeTypeFilters[] 扫描需要过滤的 二维码 一维码类型
	 * SystemUtil.App.QRCode.ScanOptions.styles 扫描控件的颜色参数，可选
	 * SystemUtil.App.QRCode.ScanOptions.BackFunction.SuccessCallback
	 * SystemUtil.App.QRCode.ScanOptions.BackFunction.ErrorCallback
	 * 
	 * @param {SystemUtil.App.QRCode.ScanOptions}
	 *            ScanOptions
	 */
	init: function(ScanOptions) {
		var thisOptions = SystemUtil.App.QRCode.QRCodeType.ScanOptions;
		if (SystemUtil.validateObj(ScanOptions, true, "ScanOption is null")) {
			for (var x in ScanOptions) {
				thisOptions[x] = ScanOptions[x];
				if (x == "styles") {
					for (var y in ScanOptions[x]) {
						thisOptions.styles[y] = ScanOptions[x][y];
					}
				} else if (x == "BackFunction") {
					for (var y in ScanOptions[x]) {
						SystemUtil.validate.functions({
							obj: ScanOptions[x][y]
						});
						SystemUtil.App.QRCode.QRCodeType.BackFunction[y] = ScanOptions[x][y];
					}
				}
			}
		}
		SystemUtil
			.validateObj(thisOptions.webViewId, true, "webViewId is null");
		// 创建Barcode对象
		SystemUtil.App.QRCode.ScanObj = new plus.barcode.Barcode(
			thisOptions.webViewId, thisOptions.qrcodeTypeFilters,
			thisOptions.styles);
		SystemUtil.App.QRCode.ScanObj.onmarked = SystemUtil.App.QRCode.Scan.BackFunction.success;
		SystemUtil.App.QRCode.ScanObj.onerror = SystemUtil.App.QRCode.Scan.BackFunction.error;
	},
	Scan: {
		/**
		 * 开始扫码
		 * 
		 * @param {SystemUtil.App.QRCode.barcodeOptions}
		 *            barcodeOptions
		 */
		StartScan: function(barcodeOptions) {
			var options = SystemUtil.App.QRCode.barcodeOptions;
			if (barcodeOptions != undefined && barcodeOptions != null &&
				barcodeOptions != "") {
				for (var x in barcodeOptions) {
					options[x] = barcodeOptions[x];
				}
			}
			barcodeOptions = options;
			SystemUtil.App.QRCode.ScanObj.start(barcodeOptions);
		},
		/**
		 * 根据图片路径进行扫描 ScanOptions.path 必传 ScanOptions.successFunction
		 * 识别图片二维码成功的回调函数 必传 ScanOptions.errorFunction 识别图片二维码失败的回调函数 可不传
		 * ScanOptions.qrcodeTypeFilters 需要过滤的二维码类型 数组，类型
		 * 请参考：SystemUtil.App.QRCode.QRCodeType.String 或者 参考
		 * SystemUtil.App.QRCode.ScanOptions
		 * 
		 * @param {SystemUtil.App.QRCode.ScanOptions}
		 *            ScanOptions
		 * 
		 */
		ScanImage: function(ScanOptions) {
			var options = SystemUtil.App.QRCode.QRCodeType.ScanOptions;
			if (SystemUtil.validateObj(ScanOptions)) {
				for (var x in ScanOptions) {
					options[x] = ScanOptions[x];
				}
				if (!SystemUtil.validateObj(options.successFunction)) {
					SystemUtil.Exception
						.Thow("Exception SuccessFunction is not undefined");
				}
				if (!SystemUtil.validateObj(options.path)) {
					SystemUtil.Exception
						.Thow("Exception Scan image path is not undefined");
				}
			} else {
				SystemUtil.Exception.Thow("ScanOptiion is null or undefined");
			}
			plus.barcode.scan(options.path,
				SystemUtil.App.QRCode.QRCodeType.BackFunction.success,
				SystemUtil.App.QRCode.QRCodeType.BackFunction.error,
				options.qrcodeTypeFilters);
		},
		/**
		 * 关闭扫描控件
		 */
		closeScan: function() {
			SystemUtil.App.QRCode.ScanObj.close();
		},
		/**
		 * 结束条码识别
		 */
		cancelScan: function() {
			SystemUtil.App.QRCode.ScanObj.cancel();
		},
		/**
		 * 回调函数
		 */
		BackFunction: {
			/**
			 * 成功的回调函数
			 * 
			 * @param {Number}
			 *            type 扫描成功返回的二维码类型
			 * @param {String}
			 *            data 返回的数据
			 * @param {String}
			 *            file 扫描的截图文件 图片
			 */
			success: function(type, data, file) {
				var scan = SystemUtil.App.QRCode.Scan;
				scan.closeScan();
				scan.cancelScan();
				var responseData = {};
				responseData.type = SystemUtil.App.QRCode.QRCodeType.number[type];
				responseData.data = data;
				responseData.file = file;
				responseData.is_url = SystemUtil.validate.url(data);
				if (SystemUtil
					.validateObj(SystemUtil.App.QRCode.QRCodeType.BackFunction.successFunction)) {
					SystemUtil.App.QRCode.QRCodeType.BackFunction
						.successFunction(responseData);
				}
			},
			/**
			 * 扫描错误的回调函数
			 * 
			 * @param {Object}
			 *            e
			 */
			error: function(e) {
				var scan = SystemUtil.App.QRCode.Scan;
				scan.closeScan();
				scan.cancelScan();
				if (SystemUtil
					.validateObj(SystemUtil.App.QRCode.QRCodeType.BackFunction.errorFunction)) {
					SystemUtil.App.QRCode.QRCodeType.BackFunction
						.errorFunction(e);
				}
			}
		}

	}
};
/**
 * webView 下拉刷新功能
 */
SystemUtil.App.WebView.PullToRefresh = {
	webViewObj: null,
	/**
	 * 初始化下拉刷新 说明 参数 请参考： SystemUtil.App.WebView.PullToRefresh.DataOptions
	 * 
	 * @param {SystemUtil.App.WebView.PullToRefresh.DataOptions}
	 * DataOptions.support 是否开启Webview窗口的下拉刷新功能
	 * DataOptions.height 窗口的下拉刷新控件高度
	 * DataOptions.range 窗口可下拉拖拽的范围
	 * DataOptions.contentdown 支持以下属性： caption：在下拉可刷新状态时下拉刷新控件上显示的标题内容
	 * DataOptions.contentover 支持以下属性 caption：在释放可刷新状态时下拉刷新控件上显示的标题内容。
	 * DataOptions.contentrefresh 支持以下属性 caption：在正在刷新状态时下拉刷新控件上显示的标题内容。
	 */
	init: function(DataOptions, refreshFunction) {
		var options = SystemUtil.App.WebView.PullToRefresh.DataOptions;
		if (SystemUtil.validate.obj({
				obj: DataOptions,
				isThow: false
			})) {
			for (var x in DataOptions) {
				options[x] = dataoptions[x];
			}
		}
		SystemUtil.validate.functions({
			obj: refreshFunction
		});
		options.refreshFunction = refreshFunction;
		SystemUtil.App.WebView.PullToRefresh.webViewObj = plus.webview
			.currentWebview();
	},
	/**
	 * 开启下拉刷新功能
	 */
	refresh: function(obj) {
		var options = SystemUtil.App.WebView.PullToRefresh.DataOptions;
		if (obj != undefined && obj != null) {
			SystemUtil.App.WebView.PullToRefresh.webViewObj = obj;
		}
		SystemUtil.App.WebView.PullToRefresh.webViewObj.setPullToRefresh(
			options.Style, options.refreshFunction);
	}
}
/**
 * 窗口对象 监听事件
 */
SystemUtil.App.WebView.AddEventListener = {
	/**
	 * 添加监听事件 SystemUtil.App.WebView.addEventListener.DataOptions
	 * DataOptions.type 必传 DataOptions.webViewObj 必传 DataOptions.successFunction
	 * 必穿
	 * 
	 * @param {SystemUtil.App.WebView.addEventListener.DataOptions}
	 *            DataOptions
	 */
	viewListener: function(DataOptionsArray) {
		var options = SystemUtil.App.WebView.AddEventListener.DataOptions;
		if (SystemUtil.validate.obj({
				obj: DataOptions,
				isThow: false
			}) && DataOptionsArray.length > 0) {
			for (var x in DataOptionsArray) {
				SystemUtil.validate.obj({
					obj: DataOptionsArray[x].type
				});
				SystemUtil.validate.obj({
					obj: DataOptionsArray[x].webViewObj
				});
				SystemUtil.validate.functions({
					obj: DataOptionsArray[x].successFunction,
					isThow: false
				});
				DataOptionsArray[x].webViewObj.addEventListener(DataOptionsArray[x].type,
					DataOptionsArray[x].successFunction, false);
			}
		}
	}
}
/**
 * 分享对象
 */
SystemUtil.App.Share = {
	/**
	 * 分享的服务列表
	 */
	Services: {},
	/**初始化 获取服务列表
	 * SystemUtil.Share.DataOptions
	 * DataOptions.BackFunction.GetServices.success 成功的回调函数(必传)
	 * DataOptions.BackFunction.GetServices.error 错误的回调函数(选传)
	 * @param {SystemUtil.Share.DataOptions} DataOptions
	 */
	init: function(DataOptions) {
		SystemUtil.validate.obj({
			obj: DataOptions
		});
		SystemUtil.validate.functions({
			obj: DataOptions.BackFunction.GetServices.success
		});
		SystemUtil.validate.functions({
			obj: DataOptions.BackFunction.GetServices.error
		});
		SystemUtil.App.Share.DataOptions.BackFunction.GetServices.success = DataOptions.BackFunction.GetServices
			.success;
		DataOptions.BackFunction.GetServices.error = DataOptions.BackFunction.GetServices.error;
		plus.share.getServices(SystemUtil.App.Share.BackFunction.GetServices.success, SystemUtil.App.Share.BackFunction
			.GetServices.error);
	},
	/**
	 * 第三方授权认证
	 * @param {SystemUtil.App.Share.Services[0]} service
	 * @param {Function} back
	 */
	auth: function(service, success, error, type, scene) {
		SystemUtil.Storage.HashMap.put("type", type);
		SystemUtil.Storage.HashMap.put("scene", scene);
		SystemUtil.validate.obj({
			obj: service
		});
		SystemUtil.validate.functions({
			obj: success
		})
		SystemUtil.App.Share.BackFunction.Auth.success = success;
		SystemUtil.App.Share.BackFunction.error = error;
		if (service.authenticated) {
			SystemUtil.Log.warn("authenticated is true");
			SystemUtil.App.Share.BackFunction.Auth.success();
		} else {
			//认证操作
			service.authorize(SystemUtil.App.Share.BackFunction.Auth.success, SystemUtil.App.Share.BackFunction
				.Auth.error);
		}
	},
	/**
	 * 分享的授权控件对象
	 */
	Authorize: {
		obj: null,
		/**
		 * 初始化 授权控件的页面
		 * @param {String} id 页面的id
		 * @param {Boolean} display 是否显示 默认是true
		 * @param {Function} onloaded 分享控件加载完成事件
		 * @param {Function} onauthenticated 分享授权认证成功事件
		 * @param {Function} onerror 分享授权认证失败事件
		 */
		init: function(id, display, onloaded, onauthenticated, onerror) {
			SystemUtil.validate.obj({
				obj: id
			})
			if (display == undefined || display == null || display == "") {
				display = true;
			}
			SystemUtil.validate.functions({
				obj: onauthenticated
			})

			var obj = new plus.share.Authorize(id, display);
			SystemUtil.App.Share.DataOptions.BackFunction.onloaded = onloaded;
			SystemUtil.App.Share.DataOptions.BackFunction.onauthenticated = onauthenticated;
			SystemUtil.App.Share.DataOptions.BackFunction.onerror = onerror;

			obj.onloaded = SystemUtil.App.Share.Authorize.BackFunction.onloaded;
			obj.onauthenticated = SystemUtil.App.Share.Authorize.BackFunction.onauthenticated;
			obj.onerror = SystemUtil.App.Share.Authorize.BackFunction.onerror;
			obj.load("weixin");
			obj.setVisible(true);
			SystemUtil.App.Share.Authorize.obj = obj;
		},
		/**
		 * 要加载的分享服务标识
		 * @param {String} ServiceId
		 */
		load: function(ServiceId) {
			SystemUtil.App.Share.Authorize.obj.load(ServiceId);
		},
		/**
		 * 是否可见
		 * @param {Boolean} isVisible
		 */
		visible: function(isVisible) {
			SystemUtil.App.Share.Authorize.obj.setVisible(isVisible);
		},
		/**
		 * 回调函数
		 */
		BackFunction: {
			/**
			 * 认证控件加载完成的回调
			 * @param {Object} service
			 */
			onloaded: function(service) {
				if (SystemUtil.validate.functions({
						obj: SystemUtil.App.Share.DataOptions.BackFunction.onloaded,
						isThow: false
					})) {
					SystemUtil.App.Share.DataOptions.BackFunction.onloaded(service);
				}
			},
			/**
			 * 认证完成的回调
			 * @param {Object} service
			 */
			onauthenticated: function(service) {
				if (SystemUtil.validate.functions({
						obj: SystemUtil.App.Share.DataOptions.BackFunction.onauthenticated,
					})) {
					SystemUtil.App.Share.DataOptions.BackFunction.onauthenticated(service);
				}
			},
			/**
			 * 认证错误的回调
			 * @param {Object} error
			 */
			onerror: function(error) {
				if (SystemUtil.validate.functions({
						obj: SystemUtil.App.Share.DataOptions.BackFunction.onerror,
						isThow: false
					})) {
					SystemUtil.App.Share.DataOptions.BackFunction.onerror(error);
				}
			}
		}

	},
	/**
	 * 发送分享
	 * MessageOptions 参数请参考 SystemUtil.App.Share.DataOptions.Send
	 * 说明：
	 * @param {SystemUtil.App.Share.Services[0]} service 服务分享对象
	 * @param {SystemUtil.App.Share.DataOptions.Send} MessageOptions MessageOptions.content 必传
	 * MessageOptions.href 必传
	 */
	send: function(MessageOptions) {
		var options = SystemUtil.App.Share.DataOptions.Send;
		SystemUtil.validate.obj({
			obj: SystemUtil.App.Share.Services[SystemUtil.Storage.HashMap.getObject("type")]
		});
		SystemUtil.validate.obj({
			obj: MessageOptions
		});
		for (var x in MessageOptions) {
			options[x] = MessageOptions[x];
			if (x.indexOf("geo") != -1) {
				for (var y in MessageOptions[x]) {
					options[x][y] = MessageOptions[x][y];
				}
			}
			if (x.indexOf("extra") != -1) {
				for (var yy in MessageOptions[x]) {
					options[x][yy] = MessageOptions[x][yy];
				}
			}
			if (x.indexOf("BackFunction") != -1) {
				for (var yyy in MessageOptions[x]) {
					options[x][yyy] = MessageOptions[x][yyy];
				}
			}
		}
		SystemUtil.validate.obj({
			obj: options.content,
			ThowMessage: "share content is not null"
		});
		SystemUtil.validate.obj({
			obj: options.href,
			ThowMessage: "share link is not null"
		});
		SystemUtil.validate.functions({
			obj: options.BackFunction.success
		});
		SystemUtil.validate.functions({
			obj: options.BackFunction.error
		});
		SystemUtil.App.Share.Services[SystemUtil.Storage.HashMap.getObject("type")].send(options, options.BackFunction
			.success, options.BackFunction.success.error);
	},
	/**
	 * 取消分享服务
	 * @param {SystemUtil.App.Share.Services} services 分享的服务列表
	 * @param {String} type 取消单个分享的服务类型 如QQ
	 */
	away: function(services, type) {
		if (SystemUtil.validate.array({
				obj: services
			})) {
			if (SystemUtil.validate.obj({
					obj: type,
					isThow: false
				})) {
				var service = services[type];
				if (SystemUtil.validate.obj({
						obj: service
					})) {
					if (service.authenticated) {
						service.forbid();
					} else {
						SystemUtil.Log.warn("not authenticated,type is:" + type);
					}
				}
				return;
			}
			for (var x in services) {
				var obj = services[x];
				for (var x in obj) {
					if (obj[x].authenticated) {
						obj[x].forbid();
					}
				}
			}
		}
	},
	/**
	 * 回调函数
	 */
	BackFunction: {
		/**
		 * 获取分享服务列表回调
		 */
		GetServices: {
			/**
			 * 获取分享成功的回调函数
			 * @param {JSON} services
			 */
			success: function(services) {
				var serviceArray = [];
				console.log(services);
				for (var x in services) {
					var obj = services[x];
					var serviceObj = {};
					if (obj.id == "sinaweibo" || obj.id == "tencentweibo") {
						continue;
					}
					obj.title = obj.description;
					obj.type = obj.id;
					obj.code = "";
					serviceArray.push(obj);
					if (obj.nativeClient) {
						if (obj.id == "weixin") {
							obj.title = "微信朋友圈";
							var weixinObj = {}
							//							var WXSceneTimeline = {
							//								title: "微信朋友圈",
							//								type: "weixin",
							//								code: "WXSceneTimeline",
							//								id: "WXSceneFavorite"
							//							};
							//							serviceArray.push(WXSceneTimeline);
							var WXSceneSession = {
								title: "微信好友",
								type: "weixin",
								code: "WXSceneSession",
								id: "WXSceneFavorite"
							};
							serviceArray.push(WXSceneSession);
							var WXSceneFavorite = {
								title: "微信我的收藏",
								type: "weixin",
								code: "WXSceneFavorite",
								id: "WXSceneFavorite"
							};
							serviceArray.push(WXSceneFavorite);
						}
						SystemUtil.App.Share.Services[obj.id] = obj;
					}
				}
				SystemUtil.Storage.HashMap.put("serviceArray", serviceArray);
				SystemUtil.Storage.HashMap.put("serviceObj", SystemUtil.App.Share.Services);
				SystemUtil.App.Share.DataOptions.BackFunction.GetServices.success(SystemUtil.App.Share.Services,
					serviceArray);
			},
			/**
			 * 获取分享失败的回调函数
			 * @param {JSON} e
			 */
			error: function(e) {
				SystemUtil.Log.warn("GET_SERVICE_IS_ERROR");
				SystemUtil.App.Share.ResponseData.BackFunction.GetServices.error(e);
			}
		},
		/**
		 * 认证回调
		 */
		Auth: {
			/**
			 * 认证成功的回调函数
			 * @param {Object} e
			 */
			success: function(e) {
				SystemUtil.App.Share.DataOptions.BackFunction.Auth.back(true);
			},
			/**
			 * 认证失败的回调函数
			 * @param {Object} e
			 */
			error: function(e) {
				SystemUtil.Log.warn("未进行认证");
				SystemUtil.App.Share.DataOptions.BackFunction.Auth.back(false);
			}
		},
		Send: {
			success: function(e) {
				SystemUtil.App.Share.DataOptions.Send.BackFunction.success(e);
			},
			error: function(e) {
				if (e.code == -2) {
					SystemUtil.App.WebView.AppMsg.toast("取消分享", {
						icon: "../img/shareQQ.png"
					})
				}
				SystemUtil.App.Share.DataOptions.Send.BackFunction.error(e.code);
			}
		}
	}
}
/**
 * 设备信息
 */
SystemUtil.App.Dervice = {
	/**
	 * 获取设备信息
	 */
	getSystemBaseInfo: function() {
		if (!SystemUtil.is_usable) {
			throw "System is not init";
		}
		var resultObj = SystemUtil.App.Push.getClientInfo();
		//设备的国际移动设备身份码
		resultObj.imei = plus.device.imei;
		//设备的国际移动用户识别码
		resultObj.imsi = plus.device.imsi;
		//设备的型号
		resultObj.model = plus.device.model
		//设备的生产厂商
		resultObj.vendor = plus.device.vendor
		//设备的唯一标识
		resultObj.uuid = plus.device.uuid
		//系统语言信息
		resultObj.language = plus.os.language;
		//系统版本信息
		resultObj.version = plus.os.version;
		//系统的名称
		resultObj.name = plus.os.name;
		//系统的供应商信息
		resultObj.vendor = plus.os.vendor;
		return resultObj;
	},
	/**
	 * 拨打电话
	 * @param {Number} mobile
	 * @param {Boolean} confirm
	 */
	dialMphone: function(mobile, confirm) {
		plus.device.dial(mobile, confirm);
	},
	/**
	 * 发出蜂鸣声
	 * @param {Number} count
	 */
	beep: function(count) {
		plus.device.beep(count);
	},
	/**
	 * 设备震动
	 * @param {Number} time
	 */
	vibrate: function(time) {
		plus.device.vibrate(time);
	},
	/**
	 * 设置应用是否保持唤醒（屏幕常亮）状态
	 * @param {Boolean} lock
	 */
	setWakelock: function(lock) {
		plus.device.setWakelock(lock);
	},
	/**
	 * 调用此方法获取程序是否一致保持唤醒状态
	 */
	isWakelock: function() {
		var result = plus.device.isWakelock();
		return result;
	},
	/**
	 *  设置设备的系统音量
	 * @param {Number} count
	 */
	setVolume: function(count) {
		return plus.device.setVolume(count);
	},
	/**
	 * 获取设备的音量
	 */
	getVolume: function() {
		var volume = plus.device.getVolume();
		return volume;
	},
	/**
	 * 当前网络状态
	 */
	getCurrentType: function() {
		var type = plus.networkinfo.getCurrentType();
		var resultObj = {};
		switch (type) {
			case plus.networkinfo.CONNECTION_UNKNOW:
				resultObj.result = false;
				resultObj.num = 0;
				resultObj.type = "网络连接状态未知";
				break;
			case plus.networkinfo.CONNECTION_NONE:
				resultObj.result = false;
				resultObj.num = 1;
				resultObj.type = "未连接网络";
				break;
			case plus.networkinfo.CONNECTION_ETHERNET:
				resultObj.result = true;
				resultObj.num = 2;
				resultObj.type = "有线网络";
				break;
			case plus.networkinfo.CONNECTION_WIFI:
				resultObj.result = true;
				resultObj.num = 3;
				resultObj.type = "无线WIFI网络";
				break;
			case plus.networkinfo.CONNECTION_CELL2G:
				resultObj.result = true;
				resultObj.num = 4;
				resultObj.type = "蜂窝移动2G网络";
				break;
			case plus.networkinfo.CONNECTION_CELL3G:
				resultObj.result = true;
				resultObj.num = 5;
				resultObj.type = "蜂窝移动3G网络";
				break;
			case plus.networkinfo.CONNECTION_CELL4G:
				resultObj.result = true;
				resultObj.num = 6;
				resultObj.type = "蜂窝移动4G网络";
				break;
			default:
				break;
		}
		return resultObj;
	}

};
SystemUtil.App.IO = {
	/**
	 * 将本地url转换成平台url
	 * @param {String} url 需要转换的路径
	 * @return {String}
	 */
	urlToSystem: function(url) {
		return plus.io.convertLocalFileSystemURL(url);
	},
	/**
	 * 将平台的url转换成本地路径
	 * @param {String} url 需要转换的路径
	 * @return {String}
	 */
	systemToUrl: function(url) {
		return plus.io.convertAbsoluteFileSystem(url);
	}
};
/**
 * 设备的按键事件
 */
SystemUtil.App.Key = {

}
/**
 * 地图功能
 */
SystemUtil.App.Map = {

};
/**
 * 用于管理系统的消息 如短信 彩信
 */
SystemUtil.App.Message = {

};
/**
 * 管理浏览器运行环境信息
 */
SystemUtil.App.Navigator = {

};
/**
 * 设备的语音管理
 */
SystemUtil.App.Speech = {

};
SystemUtil.App.Push = {
	/**
	 * 获取客户端的基本信息
	 */
	getClientInfo: function() {
		return plus.push.getClientInfo();
	}

};
/**
 * 处理应用运行时的管理对象
 */
SystemUtil.App.Runtime = {
	/**
	 * 获取当前客户端的基本信息
	 */
	getClientBaseInfo: function() {
		/**
		 * 当前应用的Appid
		 */
		var appid = plus.runtime.appid;
		/**
		 * 当前应用的启动来源
		 */
		var launcher = plus.runtime.launcher;
		/**
		 * 当前应用的安装来源
		 */
		var origin = plus.runtime.origin;
		/**
		 * 版本号
		 */
		var version = plus.runtime.version;
		/**
		 * 客户端5+运行环境的版本号
		 */
		var innerVersion = plus.runtime.innerVersion;
		return {
			appid: appid,
			launcher: launcher,
			origin: origin,
			version: version,
			innerVersion: innerVersion
		}
	},
	/**
	 * 根据appid获取应用信息
	 * @param {Object} appid 需要获取的Appid
	 * @param {Object} backFunction 获取成功的回调函数
	 */
	getAppidBaseInfo: function(appid, backFunction) {
		if (appid == undefined || appid == null || appid.length <= 0) {
			throw "getAppBaseInfo Exception,cause:appid is null,appid:" + appid;
		}
		plus.runtime.getProperty(appid, function(info) {
			info.key = appid;
			if (typeof(backFunction) == "function") {
				backFunction(info);
			}
			initDataBase(function() {
				getData(info.key, "gt_user_info", function(e) {
					if (typeof(e) == "object" && e != null) {
						update(info, "gt_user_info");
					} else {
						putData(info, "gt_user_info");
					}
				})
			})
		})
	},
	/**
	 * 安装程序 更新
	 * options.path 安卓支持apk 安装包 ios 不支持 ipa的安装包，建议使用wgt升级版 只支持本地路径
	 * @param {JSON} options
	 * options.path 安卓支持apk 安装包 ios 不支持 ipa的安装包，建议使用wgt升级版 只支持本地路径
	 */
	install: function(options) {
		if (typeof(options) == "undefined" || typeof(options) != "object") {
			throw "install Exception,options is undefined";
		}
		if (options.path == undefined || options.path == null || options.path.length <= 0) {
			throw "install options.path is null";
		}
		if (options.options == undefined || typeof(options.options) != "object") {
			options.options = {
				force: false
			};
		}
		plus.runtime.install(options.path, options.options, function(wgtInfo, x) {
			SystemUtil.App.WebView.AppMsg.toast("升级中");
			console.log("info", wgtInfo);
			console.log("x", x);
		}, function(e) {
			console.warn("install application Exception", e);
		})
	},
	/**
	 * 退出当前应用
	 */
	quit: function() {
		plus.runtime.quit();
	},
	/**
	 * 重启当前应用
	 */
	restart: function() {
		plus.runtime.restart();
	}
};
/**
 * 下载对象
 */
SystemUtil.App.Downloader = {
	/**
	 * 创建下载任务
	 * @param {Object} options
	 */
	create: function(options) {
		var dataOptions = SystemUtil.App.Downloader.DataOptions;
		if (typeof(options) == "undefined" || typeof(options) != "object") {
			throw "downloader create Exception options is undefined";
		}
		if (options.url == undefined || options.url == null || options.url.length <= 0) {
			throw "options.url Exception,options.url is null or undefined";
		}
		for (var x in options) {
			dataOptions[x] = options[x];
		}
		var download = plus.downloader.createDownload(dataOptions.url, dataOptions, function(dow, status) {
			if (typeof(dataOptions.backSuccess) == "function") {
				dataOptions.success(dow, status);
			}
		});
		if (typeof(dataOptions.backSuccess) == "function") {
			dataOptions.createSuccess(download);
		}
		download.start();

	},
	/**
	 * 枚举上传
	 * @param {Object} state
	 */
	enumerate: function(status) {
		plus.downloader.enumerate(function(array) {
			console.debug("downloader enumerate", array);
		}, status);
	},
	/**
	 * 清空上传
	 */
	clear: function(status) {
		plus.downloader.clear(status);
	},
	startAll: function() {
		plus.downloader.startAll();
	}

};
/**
 * 上传文件管理
 */
SystemUtil.App.Uploader = {
	/**
	 * 创建上传
	 * @param {SystemUtil.App.Uploader.DataOptions} DataOptions 创建上传任务必备的参数
	 * @param {Function} backFunction 回调函数
	 */
	create: function(options, backFunction) {
		if (!SystemUtil.is_usable) {
			throw "SystemUtil is not init";
		}
		var DataOptions = SystemUtil.App.Uploader.DataOption;
		if (typeof(options.start) != "boolean") {
			options.start = false;
		}
		if (options != undefined) {
			for (var x in options) {
				DataOptions[x] = options;
			}
		}
		if (DataOptions.url == undefined || DataOptions.url == null || DataOptions.url.length < 0) {
			throw "upload create url is not null";
		}
		debugger;
		var upload = plus.uploader.createUpload(DataOptions.url, DataOptions, function(upload, status) {
			if (typeof(backFunction) == "function") {
				backFunction(upload, status);
			}
			console.debug("upload", upload);
		})
		if (options.File != undefined && typeof(options.File) == "object") {
			if (options.File.path == undefined || options.File.path == null) {
				console.warn("add file path is not null");
				return;
			}
		}
		upload.addFile(options.File.path, options.File.options);
		if (options.Data != undefined && options.Data.length > 0) {
			for (var x in options.Data) {
				upload.addData(options.Data[x].key, options.Data[x].value);
			}
		}
		if (options.start) {
			upload.start();
		}
		upload.addEventListener("statechanged", function(upload, status) {
			if (upload.state == 4 && status == 200) {
				if (typeof(options.success) == "function") {
					var result = {
						result: true,
						remark: "上传成功",
						state: upload.state,
						status: status,
						path: JSON.parse(upload.responseText)
					}
					var uploadSuccessPath = SystemUtil.Storage.HashMap.getObject("uploadSuccessPath");
					if (uploadSuccessPath == null) {
						uploadSuccessPath = [];
					}
					uploadSuccessPath = uploadSuccessPath.concat(JSON.parse(upload.responseText));
					SystemUtil.Storage.HashMap.put("uploadSuccessPath", uploadSuccessPath);
					console.log("上传成功")
					options.success(result);
				}
			}
		}, false)
	},
	enumerate: function(state) {
		plus.uploader.enumerate(function(arrar) {
			console.log("上传成功", arrar);
		}, state)
	},
	clear: function(state) {
		plus.uploader.clear(state);
	},
	startAll: function() {
		plus.uploader.startAll();
	}

}
/**
 * 文件管理
 */
SystemUtil.App.SystemFile = {
	/**
	 * 选择系统文件
	 * @param {SystemUtil.App.SystemFile.DataOptions} DataOptions
	 * @param {Object} successFunction
	 * @param {Object} errorFunction
	 */
	selectFile: function(DataOptions, successFunction, errorFunction) {
		plus.gallery.pick(function(file) {
			if (typeof(successFunction) == "function") {
				successFunction(file);
			}
		}, function(errorData) {
			console.warn("select file error", errorData);
			if (typeof(errorFunction) == "function") {
				errorFunction(errorData);
			}
		}, SystemUtil.App.SystemFile.DataOptions);
	},
	saveFileSystem: function(path, successFunction, errorFunction) {
		if (path == undefined || path == null || path.length == 0) {
			throw "path is not null";
		}
		plus.gallery.save(path, function(e) {
			if (typeof(successFunction) == "function") {
				var result = {
					result: true,
					remark: "保存图片成功"
				}
				successFunction(result);
			}
		}, function(e) {
			if (typeof(errorFunction) == "function") {
				errorFunction(e);
			}
		})
	}

};
/**
 * 设备的位置信息
 */
SystemUtil.App.Location = {
	Position: {
		coords: {
			/**
			 * 坐标纬度值
			 */
			latitude: 0,
			/**
			 * 坐标经度值
			 */
			longitude: 0,
			/**
			 * 海拔信息
			 */
			altitude: 0,
			/**
			 * 地理坐标信息的精确度信息 数据类型对象，单位为米，其有效值必须大于0
			 */
			accuracy: 0,
			/**
			 * 海拔的精确度信息
			 */
			altitudeAccuracy: 0,
			/**
			 * 表示设备移动的方向
			 */
			heading: 0,
			/**
			 * 表示设备移动的速度
			 */
			speed: 0
		},
		/**
		 * 获取到地理坐标信息的坐标系类型
		 * 可取以下坐标系类型： “gps”：表示WGS-84坐标系； “gcj02”：表示国测局经纬度坐标系； “bd09”：表示百度墨卡托坐标系； “bd09ll”：表示百度经纬度坐标系。
		 */
		coordsType: "",
		/**
		 * 取到地理坐标的时间戳信息 时间戳值为从1970年1月1日至今的毫秒数
		 */
		timestamp: "",
		/**
		 * 获取到地理位置对应的地址信息
		 */
		address: {
			/**
			 * 国家
			 */
			country: "",
			/**
			 * 省份名称
			 */
			province: "",
			/**
			 * 城市名称
			 */
			city: "",
			/**
			 * )区（县）名称
			 */
			district: "",
			/**
			 * 街道和门牌信息
			 */
			street: "",
			/**
			 * POI信息 如“电子城．国际电子总部”
			 */
			poiName: "",
			/**
			 * 邮政编码
			 */
			postalCode: "",
			/**
			 * 城市代码
			 */
			cityCode: ""

		},
		/**
		 * 获取完整地址描述信息
		 */
		addresses: ""

	},
	/**
	 * 获取系统定位信息
	 * @param {SystemUtil.App.Location.DataOptions} DataOptions //参数
	 * @param {Function} successBackFunction 定位成功的回调函数
	 * @param {Function} errorBackFunction 定位失败的回调函数
	 */
	getLocation: function(DataOptions, successBackFunction, errorBackFunction) {
		var dataOptions = "";
		if (!SystemUtil.is_usable) {
			throw "SystemUtil is not init";
		}
		var options = SystemUtil.App.Location.DataOptions;
		if (typeof(DataOptions) == "object") {
			for (var x in DataOptions) {
				options[x] = DataOptions[x];
			}
		}
		if (plus.os.name == "iOS") {
			options.provider = "system";
		}
		plus.geolocation.getCurrentPosition(function(postion) {
			if (typeof(successBackFunction) == "function") {
				successBackFunction(postion, postion.coords, postion.address);
			}
		}, function(postionError) {
			if (typeof(errorBackFunction) == "function") {
				SystemUtil.App.Location.getLocationError('定位失败，请开启定位服务');
				errorBackFunction(postionError);
			}
		}, options);
	},
	/**
	 * 监听系统定位的信息
	 * @param {SystemUtil.App.Location.DataOptions} DataOptions
	 * @param {Function} successBackFunction
	 * @param {Function} errorBackFunction
	 */
	watchPosition: function(DataOptions, successBackFunction, errorBackFunction) {
		if (!SystemUtil.is_usable) {
			throw "SystemUtil is not init";
		}
		var options = SystemUtil.App.Location.DataOptions;
		if (typeof(DataOptions) == "object") {
			for (var x in DataOptions) {
				options[x] = DataOptions[x];
			}
		}
		if (plus.os.name == "iOS") {
			options.provider = "system";
		}
		var watchid = plus.geolocation.watchPosition(function(postion) {
			if (typeof(successBackFunction) == "function") {
				successBackFunction(postion);
			}
		}, function(errorLocation) {
			if (typeof(errorBackFunction) == "function") {
				SystemUtil.App.Location.getLocationError(errorLocation.code, errorLocation.message);
				errorBackFunction(errorLocation);
			}
		}, DataOptions);
		return watchid;
	},
	clearWatch: function(watchid) {
		plus.geolocation.clearWatch(watchid);
	},
	/**
	 * 获取定位的错误回调
	 * @param {Object} code
	 * @param {Object} message
	 */
	getLocationError: function(code, message) {
		SystemUtil.App.WebView.AppMsg.toast("error message," + message);
		switch (code) {
			case 1:
				SystemUtil.App.WebView.AppMsg.toast("访问权限被拒绝,请开启GPS");
				break;
			case 2:
				SystemUtil.App.WebView.AppMsg.toast("位置信息不可用");
				break;
			case 3:
				SystemUtil.App.WebView.AppMsg.toast("获取位置信息超时");
				break;
			case 4:
				SystemUtil.App.WebView.AppMsg.toast("位置错误");
				break;
			default:
				break;
		}
	}
};

function filterPath(path) {
	var webView_id = path;
	if (webView_id.indexOf("/") != -1) {
		var obj = webView_id.split("/");
		webView_id = obj[obj.length - 1];
	}
	return webView_id;
}

/**
 * 点击预加载
 * @param {Object} obj
 * obj.namePath 预加载的路径(必传)
 * obj.twoJs 第二次显示 是否加载数据(可选(boolean/true-false))
 * obj.animType 动画类型(可选 默认 pop-in)
 * obj.jsStr 显示完成需要执行的JS字符串 (字符串JS[可选])
 * obj.showBackFunction 显示完成的回调函数 (可选)
 * obj.isReload 显示完成是否 重新加载页面(可选,取值(true/false) 默认/false)
 * obj.Extend 创建view对象的扩展参数 ，自定义对象参数
 * obj.functionAfter 是否在页面加载之前执行回调函数
 */
function clickTo(obj) {
	if (typeof(obj.twoJs) != "boolean") {
		obj.twoJs = true;
	}
	if (typeof(obj.jsafter) != "boolean") {
		obj.jsafter = true;
	}
	if (obj.isValidateLogin) {
		validateLogin(obj);
	} else {
		createShowView(obj);
	}
}

function createShowView(obj) {
	var x = 0;
	var type = SystemUtil.App.WebView.AnimationAllType.In.POP_IN;
	if (typeof(obj.animType) != "string" && obj.animType != null) {
		type = animType;
	}
	var name = filterPath(obj.namePath);
	//  console.log("to path:" + name)
	vipValidate(name, function() {

		if (SystemUtil.App.WebView.Get.webViewId(name) != null) {
			/*setTimeout(function(e) {*/
			var objWebView = SystemUtil.App.WebView.Get.webViewId(name);
			if (obj.isReload) {
				objWebView.reload()
			}
			if (typeof(obj.showBackFunction) == "function" && obj.functionAfter) {
				obj.showBackFunction(name, SystemUtil.App.WebView.Get.webViewId(name));
			}
			if (SystemUtil.validate.obj({
					obj: obj.jsStr,
					isThow: false
				}) && obj.twoJs) {
				objWebView.evalJS(obj.jsStr)
			}
			SystemUtil.App.WebView.Show.show({
				id_obj: name,
				animationType: type,
				BackFunction: {
					successFunction: function(e) {
						if (typeof(obj.showBackFunction) == "function" && !obj.functionAfter) {
							obj.showBackFunction(name, SystemUtil.App.WebView.Get.webViewId(name));
						}
					}
				}
			});
			/*}, 500)*/
			return;
		}
		//	SystemUtil.App.WebView.AppMsg.loadMsg("正在玩命加载,请稍候...");
		SystemUtil.App.WebView.Create.arrayInit([{
			url: obj.namePath,
			Styles: obj.Styles,
			Extend: obj.Extend,
			loaded: function(e) {
				SystemUtil.App.WebView.AppMsg.closeMsg();
				var objWebView = SystemUtil.App.WebView.Get.webViewId(name);
				//alert(obj.jsStr+"loaded");
				if (obj.isReload) {
					objWebView.reload()
				}
				if (typeof(obj.showBackFunction) == "function" && obj.functionAfter) {
					obj.showBackFunction(name, SystemUtil.App.WebView.Get.webViewId(name));
				}
				if (SystemUtil.validate.obj({
						obj: obj.jsStr,
						isThow: false
					})) {
					objWebView.evalJS(obj.jsStr)
				}
				SystemUtil.App.WebView.Show.show({
					id_obj: name,
					animationType: type,
					BackFunction: {
						successFunction: function(e) {
							if (typeof(obj.showBackFunction) == "function" && !obj
								.functionAfter) {
								obj.showBackFunction(name, SystemUtil.App.WebView.Get
									.webViewId(name));
							}
							x++;
						}
					}
				})
			}
		}])
	}, obj);
}
/**
 * 验证是否登陆
 */
function validateLogin(obj) {
	var login_name = SystemUtil.Storage.Local.getVal("login_name");
	if (login_name == undefined || login_name == null) {
		var currentId = plus.webview.currentWebview().id;
		if (currentId != "index.html" && currentId != "/view/trip/journeyCenter.html" && currentId !=
			"/view/dynamic/dynamic.html" && currentId != "/view/my/my.html") {
			currentId = obj.namePath;
		}
		SystemUtil.Storage.Local.put(SystemUtil.Storage.Local.Key.login, currentId);
		createShowView({
			namePath: SystemUtil.pagePath.login,
			animType: SystemUtil.App.WebView.AnimationAllType.In.SLIDE_IN_BOTTOM,
			showBackFunction: function() {

			}
		});
		return;
	} else if (obj.isReturn) {
		return true;
	} else {
		createShowView(obj);
	}
}

function setDom(dataParam) {
	SystemUtil.HTML.Set.publics({
		type: dataParam.type,
		typeName: dataParam.typeName,
		key: dataParam.key,
		value: dataParam.value,
		index: SystemUtil.Storage.HashMap.getInt(SystemUtil.HashMap.Key.HTML.Set.html.typeName),
		success: function(e) {
			if (typeof(dataParam.success) == "function") {
				dataParam.index = SystemUtil.Storage.HashMap.getInt(SystemUtil.HashMap.Key.HTML.Set.html.typeName)
				dataParam.success(dataParam);
			}
		}
	});
}

/**
 * 聊天页面
 * @param {String} path
 * @param {String} id
 */
function toChat(path, message_key) {
	if (typeof(message_key) == "undefined") {
		message_key = null;
	}
	var target_id = SystemUtil.Storage.HashMap.getInt("target_id");
	var user_id = SystemUtil.Storage.Local.getInt("user_id");
	if (target_id == 0) {
		return;
	}
	var nick_name = document.querySelector("#nick_name");
	if (target_id == user_id) {
		return;
	}
	if (nick_name != null) {
		nick_name = nick_name.innerHTML.toString().trim();
	}
	clickTo({
		namePath: path,
		jsStr: "scrollTop();SystemUtil.Storage.HashMap.put('target_id'," + target_id +
			");message.Send.messageContent('" + message_key + "');", //
		showBackFunction: function(name, obj) {
			if (nick_name != null) {
				var myNickName = SystemUtil.Storage.Local.getVal("nick_name");
				obj.evalJS("document.querySelector('#nick_name').innerHTML='" + nick_name +
					"';document.querySelector('#title').innerHTML='" + '' + '与\"' + nick_name +
					'\"的出行沟通' + "'");
			}
		}
	})
}
/**
 * 选择图片 直接上传
 * @param {HTMLDDElement} dom
 * @param {Function} success
 * @param {String} 
 */
function changeFile(dom, success, fileId) {
	SystemUtil.App.SystemFile.selectFile(null, function(file) {
		SystemUtil.App.WebView.AppMsg.loadMsg("正在上传图片,请稍候...")
		console.debug("success file", file);
		dom.src = file;
		console.log(dom.src);
		//		dom.addClass('organAddPicBig');
		if (typeof(success) == "function") {
			success(file);
		}
		addUploadFile({
			start: true,
			file: file,
			dom: dom,
			success: function(obj, f) {
				SystemUtil.App.WebView.AppMsg.closeMsg();
				success(obj, f);
			}
		});
	}, function(error) {
		console.log("error", error);
	})
};
/**
 * 单文件上传 只添加不上传
 * @param {HTMLElement} dom
 * @param {Function} success
 */
function changeFileNotUpload(dom, success) {
	SystemUtil.App.SystemFile.DataOptions.multiple = false;
	SystemUtil.Storage.HashMap.put("dom", dom);
	SystemUtil.Storage.HashMap.put("success", success);
	/**
	 * 选择文件
	 */
	this.selectFile = function(travel_id, brod_id, identity) {
		SystemUtil.App.SystemFile.selectFile(null, function(file) {
			console.debug("success file", file);
			try {
				SystemUtil.Storage.HashMap.put("file", file);
				SystemUtil.Storage.HashMap.getObject("dom").src = file;
			} catch (e) {
				//TODO handle the exception
			}
			success = SystemUtil.Storage.HashMap.getObject("success");
			if (typeof(success) == "function") {
				success(file, travel_id, brod_id, identity);
			}
		}, function(error) {
			console.log("error", error);
		});
	}
	/**
	 * 上传
	 */
	this.upload = function(backFunction) {
		var file = SystemUtil.Storage.HashMap.getObject("file");
		if (file == null || file == "") {
			console.warn("file is null");
			return;
		}
		addUploadFile({
			start: true,
			file: file,
			success: function(obj, e) {
				SystemUtil.Storage.HashMap.put("uploadFileResult", obj.path[0]);
				if (typeof(backFunction) == "function") {
					backFunction(obj.path[0], obj);
				}
			}
		})
	};
}
/**
 * 添加上传的文件
 * @param {Object} param
 */
function addUploadFile(param) {
	var type = SystemUtil.Storage.HashMap.getObject("uploadType");
	if (type == null) {
		SystemUtil.App.WebView.AppMsg.closeMsg();
		throw "you is not put uploadType key is uploadType";
	}
	var options = {
		start: param.start,
		method: "POST",
		File: {
			path: param.file,
			options: {
				key: "file"
			}
		},
		Data: [{
			key: "user_id",
			value: SystemUtil.Storage.Local.getVal("user_id")
		}, {
			key: "type",
			value: type
		}]
	};
	options.success = function(obj) {
		SystemUtil.App.WebView.AppMsg.toast(obj.remark);
		if (param.dom != undefined && typeof(param.dom) == "object") {
			console.log(param.dom);
			param.dom.attributes.path = obj.path[0];
			param.dom.setAttribute("path", obj.path[0]);
		}
		if (typeof(param.success) == "function") {
			param.success(obj, param.file);
		}
	};
	SystemUtil.App.Uploader.create(options)
}

/**
 * 定位
 * @param {Object} backFunction
 */
function localtionAddress(backFunction) {
	SystemUtil.App.Location.getLocation(null, function(location) {
			//定位失败则默认杭州市
			var sendout_code;
			if (location.coords.latitude != '5e-324') {
				SystemUtil.Storage.Local.put("city", location.address.city);
				SystemUtil.Storage.Local.put("latitude", location.coords.latitude);
				SystemUtil.Storage.Local.put("longitude", location.coords.longitude);
				sendout_code = cityName[location.address.city].code;
			} else {
				SystemUtil.Storage.Local.put("latitude", '30');
				SystemUtil.Storage.Local.put("longitude", '120');
				sendout_code = '3301';
			}
			SystemUtil.Storage.Local.put("address", location.addresses);
			SystemUtil.Storage.Local.put("sendout_code", sendout_code);
			location.address.code = sendout_code;
			var main = SystemUtil.App.WebView.Get.webViewId("main.html");
			var locationVal = JSON.stringify(location);
			var hashMapPutLocation = "SystemUtil.Storage.HashMap.put('location'," + locationVal + ")";
			console.debug(hashMapPutLocation);
			main.evalJS(hashMapPutLocation);
			main.evalJS("putLocation()");
			if (typeof(backFunction) == "function") {
				backFunction(location);
			}
		},
		function(errorLocation) {
			console.debug(errorLocation);
		}
	)
}
/**
 * 多选图片的参数
 * @param {JSON} options
 */
function changeFileArray(options) {
	this.select = function() {
		SystemUtil.App.SystemFile.DataOptions.multiple = true;
		SystemUtil.App.SystemFile.DataOptions.maximum = 5;
		SystemUtil.App.SystemFile.selectFile(null, function(e) {
			if (options.imgHtml == (null || undefined || "")) {
				console.warn("selectFile is success but options.imgHtml is null");
			}
			if (typeof(options.success) == "function") {
				options.success(e.files);
			}
			SystemUtil.Storage.HashMap.put("files", e.files);
			if (options.addFile) {
				for (var x in e.files) {
					addUploadFile({
						start: false,
						file: e.files[x]
					});
				}
			}
		}, function(fileError) {
			console.warn("select file error", JSON.stringify(fileError));
		});
	};
	/**
	 * 上传文件
	 */
	this.uploadFile = function(backFunction) {
		SystemUtil.App.WebView.AppMsg.loadMsg("正在上传图片,请耐心等待...");
		var array = [];
		var files = SystemUtil.Storage.HashMap.getObject("files");
		var y = 0;
		console.log("files length:" + files.length);
		if (files != null && files != undefined && files.length > 0) {
			for (var x in files) {
				addUploadFile({
					start: true,
					file: files[x],
					success: function(obj, f) {
						y++;
						console.log(y);
						array.push(obj.path[0]);
						var uploadSuccessPath = SystemUtil.Storage.HashMap.getObject(
							"uploadSuccessPath");
						SystemUtil.Storage.HashMap.put("files", array);
						if (files.length == uploadSuccessPath.length) {
							if (typeof(backFunction) == "function") {
								backFunction(uploadSuccessPath);
							}
						}
					}
				});
			}
		} else {
			console.warn("select file is null");
			backFunction(array);
		}
	}
}
/**
 * 接收到消息信息
 * @param {Object} message
 */
function onTripMessage(message) {
	var viewObj = SystemUtil.App.WebView.Get.webViewId("/view/message/message.html"); //行程
	var user_id = SystemUtil.Storage.Local.getInt("user_id");
	//即时通讯清单
	this.type = function(id) {
		viewObj.evalJS("updateStatus('" + id + "')");
	};
	/**
	 * 行程中心过滤类型
	 * @param{JSON} contentMap 接受到的内容
	 */
	this.filterType = function(contentMap) {
		if (contentMap.travel_type == "travel") { //出行
			travelStatus = "publishPlay";
			if (user_id == parseInt(contentMap.user_id)) {
				//我自己发布的(出行)
				this.type("myTravelPublish");
			} else {
				//我接待别人的出行
				this.type("myPlayAdd");
			}
		} else { //玩法
			if (user_id == parseInt(contentMap.user_id)) {
				//我自己发布的(玩法)
				this.type("myPlayPublish");
			} else {
				//我加入别人的玩法
				this.type("myTravelAdd");
			}
		}
	};
	if (message.message_type == "trip") {
		var contentMap = message.contentMap;
		switch (contentMap.type) {
			case "add": //出行或者接待
				//处理当前出行数量和未读
				this.filterType(contentMap);
				break;
			case "update": //对话过程
				this.filterType(contentMap);
				break;
			case "publish": //发布出行
				break;
		}
	}
}

/**
 *设置zindex
 * @param {Object} obj_id 需要设置zindex的id
 * @param{Boolean} is_back 是否隐藏当前页面
 */
function setViewIndexBack(obj_id, is_back) {
	if (typeof(obj_id) != "string" && typeof(obj_id) != "object") {
		throw "obj_id is not a string and object";
	}
	var zindex = SystemUtil.Storage.Local.getInt("zindex");
	var viewObj = obj_id
	if (typeof(obj_id) == "string") {
		viewObj = SystemUtil.App.WebView.Get.webViewId(obj_id);
	}
	if (viewObj != null) {
		viewObj.setStyle({
			zindex: zindex - 1
		});
	}
	if (is_back) {
		SystemUtil.App.WebView.back();
	}
}
/**
 * 更新行程的状态信息
 * @param {String} id 状态的ID
 */
function updateStatus(id, count) {
	if (typeof(count) != "undefined") {
		count = parseInt(count);
	} else {
		count = 1;
	}
	var travelStatus = document.querySelector("#" + id);
	if (travelStatus != null) {
		travelStatus.style.display = "block";
		var travelStatusCount = isNaN(parseInt(travelStatus.innerHTML)) ? 0 : parseInt(travelStatus.innerHTML); //未读的数量
		travelStatusCount = travelStatusCount + count;
		travelStatus.innerHTML = travelStatusCount;
	}
}

function closePath(array) {
	for (var x in array) {
		var obj = SystemUtil.App.WebView.Get.webViewId(array[x]);
		if (obj != null) {
			obj.close("none");
		}
	}
}
/**
 * 是否 成为玩主
 */
function isPlay(backFunction) {
	var is_guide = SystemUtil.Storage.Local.getVal("is_guide");
	if (is_guide == "true" || is_guide == true) {
		if (typeof(backFunction) == "function") {
			SystemUtil.Storage.Local.put("is_guide", true);
			backFunction(true);
			return;
		} else {
			SystemUtil.App.WebView.AppMsg.toast("您已经是玩主!");
			return;
		}
	} else {
		plus.nativeUI.confirm("请先成为玩主", function(e) {
			console.log((e.index == 0) ? "Yes!" : "No!");
			if (e.index == 0) {
				clickTo({
					namePath: '/view/my/playMasterOpen.html'
				})
			} else {
				return;
			}
		}, "温馨提示", ["确认", "取消"]);

	}
	/*SystemUtil.App.WebView.AppMsg.confirm("是否成为玩主?", function(e) {
		if(e.index == 0) {
			
						var mydata = {
							user_id: SystemUtil.Storage.Local.getInt("user_id")
						};
						SystemUtil.HttpClientRequest.Execute({
							controllerName: "guideInfo",
							actionName: "addGuideInfo",
							data: mydata,
							successBackFunction: function(data) {
								if(data.result) {
									if(typeof(backFunction) == "function") {
										backFunction(true);
									}
									SystemUtil.Storage.Local.put("is_guide", true);
								}
								SystemUtil.App.WebView.AppMsg.toast(data.remark);
							}
						})
					

		} else {
			if(typeof(backFunction) == "function") {
				backFunction(false);
			}
		}
	}, "温馨提醒", ["确认", "取消"]);*/
}

/**
 * 验证是否审核成功
 */
function validateIdentIfy(content, title, backFunction) {
	prove.show.myProve(function(data) {
		validateLogin({
			isReturn: true
		});
		var js = "";
		if (typeof(title) == "undefined" || title == "") {
			title = "温馨提醒！";
		} else {
			js = "SystemUtil.Storage.HashMap.put('travelDetail','" + SystemUtil.App.WebView.Get.getCurrentWebView()
				.id + "');";
		}
		if (typeof(content) == "undefined" || content == "") {
			content = "您的账号未实名认证,请实名认证";
		}
		var result = false;
		//		var identifyStatus = SystemUtil.Storage.Local.getVal("identifyStatus");
		if (data != null) {
			var identifyStatus = data.status;
		} else {
			var identifyStatus = 0
		}
		if (identifyStatus == 0 || identifyStatus == "0") {
			SystemUtil.App.WebView.AppMsg.confirm(content, function(e) {
				if (e.index == 0) {
					clickTo({
						namePath: "/view/my/prove/namePictureApprove.html",
						showBackFunction: function(name, obj) {
							obj.evalJS(js);
						}
					});
				};
			}, title, ["确认", "取消"]);
		} else if (identifyStatus == "2" || identifyStatus == 2) {
			SystemUtil.App.WebView.AppMsg.toast("正在审核,请耐心等待...");
		} else if (identifyStatus == 3 || identifyStatus == "3") {
			SystemUtil.App.WebView.AppMsg.toast("审核未通过...");
		} else if (identifyStatus == 1 || identifyStatus == "1") {
			backFunction(true);
			return true;
		}
	})
}
/**
 * 验证实名认证获取文本信息
 */
function validateIdentContent() {
	var content = "";
	var identifyStatus = SystemUtil.Storage.Local.getVal("identifyStatus");
	var status = 12;
	var result = false;
	if (identifyStatus == 0 || identifyStatus == "0") {
		content = "实名认证";
		status = 0;
	} else if (identifyStatus == "2" || identifyStatus == 2) {
		content = "正在审核";
	} else if (identifyStatus == 3 || identifyStatus == "3") {
		content = "审核未通过";
	} else if (identifyStatus == 1 || identifyStatus == "1") {
		result = true;
	}
	return {
		result: result,
		content: content,
		status: status
	}
}
/**
 * 级联
 * @param {HTMLElement} e
 */
function changeCity(e) {
	var id = e.id;
	var code = null;
	if (e instanceof HTMLSelectElement) {
		$(e).parent().children("div").children("span").html(e.selectedOptions[0].innerText);
		code = e.selectedOptions[0].getAttribute("code");
	} else {
		code = e.code;
	}
	address = e.selectedOptions[0].innerText;
	map.centerAndZoom(address, 11); //设置地图中心点
	var html = "<option code='0' parent_code='0' selected='true'>请选择</option>";
	var listObj = null;
	var nextObj = null;
	switch (id) {
		case "province":
			nextObj = document.querySelector("#city");
			break;
		case "city":
			nextObj = document.querySelector("#area");
			break;
		case "area":
			nextObj = document.querySelector("#town");
			break;
		default:
			break;
	}
	if (nextObj != null) {
		SystemUtil.city.query({
			data: {
				parent_code: code
			},
			success: function(listObj) {
				if (listObj != null) {
					for (var x in listObj) {
						html += "<option code='" + listObj[x].code + "' parent_code='" + listObj[x].parent_code +
							"'>" + listObj[x].name + "</option>";
					}
					nextObj.innerHTML = html;
				}
			}
		})
	}
}

function changeCityScenci(e) {
	var id = e.id;
	var code = null;
	if (e instanceof HTMLSelectElement) {
		$(e).prev().html(e.selectedOptions[0].innerText);
		code = e.selectedOptions[0].getAttribute("id");
		reid = e.selectedOptions[0].getAttribute("reid");
	} else {
		code = e.id;
		reid = e.reid;
	}
	var html = "<option selected='true' id='0' reid='0'>请选择</option>";
	var listObj = null;
	var nextObj = null;
	switch (id) {
		case "province":
			nextObj = document.querySelector("#city");
			break;
		case "city":
			nextObj = document.querySelector("#area");
			break;
		default:
			break;
	}
	if (nextObj != null) {
		if (id == 'city') {
			var data = {
				reid: code,
				id: reid
			}
			SystemUtil.HttpClientRequest.Execute({
				controllerName: "guideInfo",
				actionName: "queryLocalAttractions",
				data: data,
				successBackFunction: function(listObj) {
					if (listObj != null) {
						var li = '',
							liBar = '';
						for (var i in listObj.attraction) {
							liBar += '<a>' + i + '</a>';
							var scenic = listObj.attraction[i];
							li += '<li data-group="' + i +
								'" class="mui-table-view-divider mui-indexed-list-group">' + i + '</li>'
							for (var j in scenic) {
								li += '<li class="mui-table-view-cell mui-indexed-list-item">' + scenic[j].name +
									'</li>';
							}
						}
						$('#listView').html(li);
						$('.mui-indexed-list-bar').html(liBar);
						muiBarList();
					}
				}
			})
			return false;
		}
		SystemUtil.city.queryScenic({
			data: {
				reid: code,
				id: reid
			},
			success: function(listObj) {
				if (listObj != null) {
					for (var x in listObj.paramList) {
						var code = listObj.paramList[x];
						html += "<option id='" + code.id + "' reid='" + code.reid + "'>" + code.name +
							"</option>";
					}
					nextObj.innerHTML = html;
					var li = '',
						liBar = '';
					for (var i in listObj.provinceAttractions) {
						var scenic = listObj.provinceAttractions[i];
						if (i != ' ') {
							liBar += '<a>' + i + '</a>';
							li += '<li data-group="' + i +
								'" class="mui-table-view-divider mui-indexed-list-group">' + i + '</li>'
							for (var j in scenic) {
								li += '<li class="mui-table-view-cell mui-indexed-list-item">' + scenic[j].name +
									'</li>';
							}
						}
					}
					$('#listView').html(li);
					$('.mui-indexed-list-bar').html(liBar);
					muiBarList();
				}
			}
		})
	}
}
/**
 * 删除行程的未读消息
 * @param {Object} e this
 * @param {Object} type 类型
 */
function removeTripNotReadMessage(type, travel_type, travel_id, targetId) { //travel_id出行id,targetId 对方的id
	var jsStr
	if (travel_id != undefined) {

		jsStr = "initSys(" + travel_id + ");";
	} else {
		jsStr = "initSys();SystemUtil.Storage.HashMap.put('targetId'," + targetId + ");";
	}
	if (validateLogin({
			isReturn: true
		})) {
		SystemUtil.Storage.Local.put("typeText", type);
		var path = "/view/travel/travelStateList.html";
		if (travel_type == "play") {
			path = "/view/guide/playStateList.html";
		};
		clickTo({
			namePath: path,
			jsStr: jsStr, //聊天页面点同意接待或报名
		})

	}
};

function initCity(id) {
	var province = document.querySelector("#" + id);
	if (province == null) {
		province = document.querySelector("#province");
	}
	SystemUtil.city.query({
		data: {
			parent_code: "0"
		},
		success: function(data) {
			var html = "<option selected='true' code='0' parent_code='0'>请选择</option>";
			for (var x in data) {
				var code = data[x];
				first_code = code.code;
				html += "<option code='" + code.code + "' first_code='" + code.code + "' parent_code='" +
					code.parent_code + "'>" + code.name + "</option>";
			}
			province.innerHTML = html;
			province.code = first_code;
		}
	})
}

function push(type, str) {
	console.log(str);
	try {
		var payload = JSON.parse(str.payload);
		if (typeof(payload) == "object") {
			eval(payload.type + "(" + JSON.stringify(payload) + ")");
		}
	} catch (e) {
		//TODO handle the exception
	}
}
/**
 * 
 * @param {Function} back
 */
function pushAddEventListener(back) {
	plus.push.addEventListener("receive", function(str) {
		back("receive", str);
	});
	plus.push.addEventListener("click", function(str) {
		back("click", str);
	});
}

//即时通讯
function sendMessage(obj) {
	isLogin(function() {
		localStorage.setItem('chat_target_id', obj.target_id);
		plus.webview.getWebviewById('chat.html').evalJS(
			'$("#chatUl").empty();$("#lookMore").html("查看更多");page=1;selectMyHistoryMsg();scrollBottom();');
		clickTo({
			namePath: 'chat.html'
		});
	})
}

//分享
function note(obj) {
	isLogin(function() {
		clickTo({
			namePath: '/view/dynamic/shareMsgList.html'
		});
	})
}

//加好友
function friends(obj) {
	isLogin(function() {
		clickTo({
			namePath: "/view/friends/newFriendsList.html"
		});
	})
}

//认证
function updateAuth(obj) {
	isLogin(function() {
		clickTo({
			namePath: "/certificationNotice.html"
		});
	})
}

//支付成功
function payment(obj) {
	isLogin(function() {
		clickTo({
			namePath: "/view/my/wallet/billDeal.html"
		});
	})
}

//问题邀请
function invitationAnswer(obj) {
	clickTo({
		namePath: "/view/askAndAnswer/newAskQuestionDetail.html",
		Extend: {
			questionId: obj.question_id
		}
	});
}
/**
 * 验证vip查看人数的权限
 */
function vipValidate(name, cb, obj) {
	//	//移除会员判断，用于测试
	//	cb();
	//	return;

	if (obj.validate) {
		cb();
		return;
	}
	var induilds = ["userDetail.html", "chat.html"];
	var index = -1;
	if ((index = induilds.indexOf(name)) == -1) {
		if (index != 1) {
			cb();
		}
		return;
	}
	var type = "message";
	var other_id = localStorage.getItem("chat_target_id");
	if (index == 0) {
		type = "look";
		other_id = obj.userId;
	}
	console.log(index);

	if (localStorage.getItem("user_id") === null) {
		clickTo({
			namePath: '/login3New.html',
			validate: true,
			showBackFunction: function() {
				plus.webview.getWebviewById('login3New.html').evalJS("getServices();");
			}
		})
		return;
	}

	SystemUtil.HttpClientRequest.Execute({
		controllerName: "userBase",
		actionName: "queryUserLookJurisdiction",
		projectName: "/guidetravel",
		data: [{
			user_id: localStorage.getItem("user_id"),
			type: type,
			other_id: other_id
		}],
		successBackFunction: function(data) {
			if (!data.result) {
				if ($('#popMask').length === 0) {
					$("body").append('<div id="popMask" class="commonMask"></div>');
				}
				if ($('#vipValidate').length === 0) {
					if (data.level == 1) {//普通会员
						$("body").append(
							'<div id="vipValidate" class="commonPop"><p class="commonPop-txt">您今日的浏览次数已达到上限，<a>升级VIP</a>可享受更多专属特权</p><div class="commonPop-btn"><a>立即升级</a></div></div>'
						);
					} else if (data.level == 3) {//非会员
						$("body").append(
							'<div id="vipValidate" class="commonPop"><p class="commonPop-txt">您今日的浏览次数已达到上限，<a>开通VIP</a>可享受更多专属特权</p><div class="commonPop-btn"><a>立即开通</a></div></div>'
						);
					} else if (data.level == 5) {//微信用户且未绑定手机或邮箱
						clickTo({
							namePath: '/loginBindWeixin.html',
							Extend: {
								user_id: other_id
							}
						})
					}
				}

				if (data.level ==1 || data.level ==3) {
					$('#popMask').css('display', 'block');
					$('#vipValidate').css('display', 'block');
				}

				$('#popMask').click(function() {
					$(this).css('display', 'none');
					$('#vipValidate').css('display', 'none');
				})

				var vipDetail;
				$('.commonPop-btn').click(function() {
					$('#popMask').css('display', 'none');
					$('#vipValidate').css('display', 'none');

					if (data.level == 1) {
						selectMyVip();

						//查询我的会员情况
						function selectMyVip() {
							SystemUtil.HttpClientRequest.Execute({
								controllerName: "userMember",
								actionName: "findMyMember",
								data: {
									user_id: localStorage.getItem('user_id')
								},
								successBackFunction: function(data) {
									vipDetail = data;

									clickTo({
										namePath: "/view/my/member/renewMember.html",
										Extend: {
											vipDetail: vipDetail,
											upgrade: true
										}
									})
								},
								errorBackFunction: function(e) {
									console.log(e);
								}
							})
						}
					} else if (data.level == 3 || data.level == 4) {
						clickTo({
							namePath: "/view/my/member/playMember.html"
						})
					}
				})
				return;
			}
			cb();
		},
		errorBackFunction: function(e) {
			console.log(e);
		}
	});
}

//认证消息
function identityPop(type, status, remark) {
	//实名
	if (type === 'identity') {
		if (status == 1) {
			if ($('#auth').length === 0) {
				$("body").append(
					'<div id="popMask" class="commonMask"></div><div id="auth" class="commonPop"><p class="commonPop-txt">' +
					remark + '</p><div class="commonPop-btn close"><a>关闭</a></div></div>'
				);
			} else {
				$('#popMask').html(remark);
			}
		}
		if (status == 3) {
			if ($('#auth').length === 0) {
				$("body").append(
					'<div id="popMask" class="commonMask"></div><div id="auth" class="commonPop" style=""> <p class = "commonPop-txt">' +
					remark +
					'</p> <div class = "commonPop-btn commonPop-btn-confirm" ><a class = "lf cancel" > 算了 </a> <a class = "lf confirm" > 重新上传 </a> </div> </div>'
				);
			} else {
				$('#popMask').html(remark);
			}
		}
		$('.commonPop-btn-confirm .confirm').click(function() {
			$('#popMask').css('display', 'none');
			$('#auth').css('display', 'none');

			clickTo({
				namePath: '/view/my/prove/realNameAuthentication.html'
			})
		})
	}
	//寻助客
	if (type === 'travel') {
		if (status == 2) {
			if ($('#auth').length === 0) {
				$("body").append(
					'<div id="popMask" class="commonMask"></div><div id="auth" class="commonPop"><p class="commonPop-txt">' +
					remark + '</p><div class="commonPop-btn close"><a>关闭</a></div></div>'
				);
			} else {
				$('#popMask').html(remark);
			}
		}
		if (status == 3) {
			if ($('#auth').length === 0) {
				$("body").append(
					'<div id="popMask" class="commonMask"></div><div id="auth" class="commonPop" style=""> <p class = "commonPop-txt">' +
					remark +
					'</p> <div class = "commonPop-btn commonPop-btn-confirm" ><a class = "lf cancel" > 算了 </a> <a class = "lf confirm" > 重新上传 </a> </div> </div>'
				);
			} else {
				$('#popMask').html(remark);
			}
		}
		$('.commonPop-btn-confirm .confirm').click(function() {
			$('#popMask').css('display', 'none');
			$('#auth').css('display', 'none');

			clickTo({
				namePath: '/becomeGuestOrMaster.html',
				Extend: {
					travelType: 'travel'
				}
			})
		})
	}
	//助游师
	if (type === 'play') {
		if (status == 2) {
			if ($('#auth').length === 0) {
				$("body").append(
					'<div id="popMask" class="commonMask"></div><div id="auth" class="commonPop"><p class="commonPop-txt">' +
					remark + '</p><div class="commonPop-btn close"><a>关闭</a></div></div>'
				);
			} else {
				$('#popMask').html(remark);
			}
		}
		if (status == 3) {
			if ($('#auth').length === 0) {
				$("body").append(
					'<div id="popMask" class="commonMask"></div><div id="auth" class="commonPop" style=""> <p class = "commonPop-txt">' +
					remark +
					'</p> <div class = "commonPop-btn commonPop-btn-confirm" ><a class = "lf cancel" > 算了 </a> <a class = "lf confirm" > 重新上传 </a> </div> </div>'
				);
			} else {
				$('#popMask').html(remark);
			}
		}
		$('.commonPop-btn-confirm .confirm').click(function() {
			$('#popMask').css('display', 'none');
			$('#auth').css('display', 'none');

			clickTo({
				namePath: '/becomeGuestOrMaster.html'
			})
		})
	}
	//学生
	if (type === 'student') {
		if (status == 1) {
			if ($('#auth').length === 0) {
				$("body").append(
					'<div id="popMask" class="commonMask"></div><div id="auth" class="commonPop"><p class="commonPop-txt">' +
					remark + '</p><div class="commonPop-btn close"><a>关闭</a></div></div>'
				);
			} else {
				$('#popMask').html(remark);
			}
		}
		if (status == 3) {
			if ($('#auth').length === 0) {
				$("body").append(
					'<div id="popMask" class="commonMask"></div><div id="auth" class="commonPop" style=""> <p class = "commonPop-txt">' +
					remark +
					'</p> <div class = "commonPop-btn commonPop-btn-confirm" ><a class = "lf cancel" > 算了 </a> <a class = "lf confirm" > 重新上传 </a> </div> </div>'
				);
			} else {
				$('#popMask').html(remark);
			}
		}
		$('.commonPop-btn-confirm .confirm').click(function() {
			$('#popMask').css('display', 'none');
			$('#auth').css('display', 'none');

			clickTo({
				namePath: '/view/my/prove/studentApprove.html'
			})
		})
	}
	//境外身份
	if (type === 'overseas_identify') {
		if (status == 1) {
			if ($('#auth').length === 0) {
				$("body").append(
					'<div id="popMask" class="commonMask"></div><div id="auth" class="commonPop"><p class="commonPop-txt">' +
					remark + '</p><div class="commonPop-btn close"><a>关闭</a></div></div>'
				);
			} else {
				$('#popMask').html(remark);
			}
		}
		if (status == 3) {
			if ($('#auth').length === 0) {
				$("body").append(
					'<div id="popMask" class="commonMask"></div><div id="auth" class="commonPop" style=""> <p class = "commonPop-txt">' +
					remark +
					'</p> <div class = "commonPop-btn commonPop-btn-confirm" ><a class = "lf cancel" > 算了 </a> <a class = "lf confirm" > 重新上传 </a> </div> </div>'
				);
			} else {
				$('#popMask').html(remark);
			}
		}
		$('.commonPop-btn-confirm .confirm').click(function() {
			$('#popMask').css('display', 'none');
			$('#auth').css('display', 'none');

			clickTo({
				namePath: '/view/my/prove/overseasIdentity.html',
			})
		})

	}
	//境外助游师
	if (type === 'overseas_master') {
		if (status == 1) {
			if ($('#auth').length === 0) {
				$("body").append(
					'<div id="popMask" class="commonMask"></div><div id="auth" class="commonPop"><p class="commonPop-txt">' +
					remark + '</p><div class="commonPop-btn close"><a>关闭</a></div></div>'
				);
			} else {
				$('#popMask').html(remark);
			}
		}
		if (status == 3) {
			if ($('#auth').length === 0) {
				$("body").append(
					'<div id="popMask" class="commonMask"></div><div id="auth" class="commonPop" style=""> <p class = "commonPop-txt">' +
					remark +
					'</p> <div class = "commonPop-btn commonPop-btn-confirm" ><a class = "lf cancel" > 算了 </a> <a class = "lf confirm" > 重新上传 </a> </div> </div>'
				);
			} else {
				$('#popMask').html(remark);
			}
		}
		$('.commonPop-btn-confirm .confirm').click(function() {
			$('#popMask').css('display', 'none');
			$('#auth').css('display', 'none');

			clickTo({
				namePath: '/becomeGuestOrMaster.html',
				Extend: {
					personType: 'abroad'
				}
			})
		})
	}
	//导游
	if (type === 'guide') {
		if (status == 1) {
			if ($('#auth').length === 0) {
				$("body").append(
					'<div id="popMask" class="commonMask"></div><div id="auth" class="commonPop"><p class="commonPop-txt">' +
					remark + '</p><div class="commonPop-btn close"><a>关闭</a></div></div>'
				);
			} else {
				$('#popMask').html(remark);
			}
		}
		if (status == 3) {
			if ($('#auth').length === 0) {
				$("body").append(
					'<div id="popMask" class="commonMask"></div><div id="auth" class="commonPop" style=""> <p class = "commonPop-txt">' +
					remark +
					'</p> <div class = "commonPop-btn commonPop-btn-confirm" ><a class = "lf cancel" > 算了 </a> <a class = "lf confirm" > 重新上传 </a> </div> </div>'
				);
			} else {
				$('#popMask').html(remark);
			}
		}
		$('.commonPop-btn-confirm .confirm').click(function() {
			$('#popMask').css('display', 'none');
			$('#auth').css('display', 'none');

			clickTo({
				namePath: '/view/my/prove/tourGuideCertificateApprove.html'
			})
		})
	}
	//记者
	if (type === 'journalist') {
		if (status == 1) {
			if ($('#auth').length === 0) {
				$("body").append(
					'<div id="popMask" class="commonMask"></div><div id="auth" class="commonPop"><p class="commonPop-txt">' +
					remark + '</p><div class="commonPop-btn close"><a>关闭</a></div></div>'
				);
			} else {
				$('#popMask').html(remark);
			}
		}
		if (status == 3) {
			if ($('#auth').length === 0) {
				$("body").append(
					'<div id="popMask" class="commonMask"></div><div id="auth" class="commonPop" style=""> <p class = "commonPop-txt">' +
					remark +
					'</p> <div class = "commonPop-btn commonPop-btn-confirm" ><a class = "lf cancel" > 算了 </a> <a class = "lf confirm" > 重新上传 </a> </div> </div>'
				);
			} else {
				$('#popMask').html(remark);
			}
		}
		$('.commonPop-btn-confirm .confirm').click(function() {
			$('#popMask').css('display', 'none');
			$('#auth').css('display', 'none');

			clickTo({
				namePath: '/view/my/prove/enterpriseCuthentication.html',
				Extend: {
					type: 'report',
					status: 1
				}
			})
		})
	}
	//民宿
	if (type === 'hotel') {
		if (status == 1) {
			if ($('#auth').length === 0) {
				$("body").append(
					'<div id="popMask" class="commonMask"></div><div id="auth" class="commonPop"><p class="commonPop-txt">' +
					remark + '</p><div class="commonPop-btn close"><a>关闭</a></div></div>'
				);
			} else {
				$('#popMask').html(remark);
			}
		}
		if (status == 3) {
			if ($('#auth').length === 0) {
				$("body").append(
					'<div id="popMask" class="commonMask"></div><div id="auth" class="commonPop" style=""> <p class = "commonPop-txt">' +
					remark +
					'</p> <div class = "commonPop-btn commonPop-btn-confirm" ><a class = "lf cancel" > 算了 </a> <a class = "lf confirm" > 重新上传 </a> </div> </div>'
				);
			} else {
				$('#popMask').html(remark);
			}
		}
		$('.commonPop-btn-confirm .confirm').click(function() {
			$('#popMask').css('display', 'none');
			$('#auth').css('display', 'none');

			clickTo({
				namePath: '/view/hotel/merchantsEnter.html'
			})
		})
	}

	//公司
	if (type === 'company') {
		if (status == 1) {
			if ($('#auth').length === 0) {
				$("body").append(
					'<div id="popMask" class="commonMask"></div><div id="auth" class="commonPop"><p class="commonPop-txt">' +
					remark + '</p><div class="commonPop-btn close"><a>关闭</a></div></div>'
				);
			} else {
				$('#popMask').html(remark);
			}
		}
		if (status == 3) {
			if ($('#auth').length === 0) {
				$("body").append(
					'<div id="popMask" class="commonMask"></div><div id="auth" class="commonPop" style=""> <p class = "commonPop-txt">' +
					remark +
					'</p> <div class = "commonPop-btn commonPop-btn-confirm" ><a class = "lf cancel" > 算了 </a> <a class = "lf confirm" > 重新上传 </a> </div> </div>'
				);
			} else {
				$('#popMask').html(remark);
			}
		}
		$('.commonPop-btn-confirm .confirm').click(function() {
			$('#popMask').css('display', 'none');
			$('#auth').css('display', 'none');

			clickTo({
				namePath: '/view/my/prove/enterpriseCuthentication.html',
				Extend: {
					type: 'company'
				}
			})
		})
	}
	//旅行机构
	if (type === 'agency') {
		if (status == 1) {
			if ($('#auth').length === 0) {
				$("body").append(
					'<div id="popMask" class="commonMask"></div><div id="auth" class="commonPop"><p class="commonPop-txt">' +
					remark + '</p><div class="commonPop-btn close"><a>关闭</a></div></div>'
				);
			} else {
				$('#popMask').html(remark);
			}
		}
		if (status == 3) {
			if ($('#auth').length === 0) {
				$("body").append(
					'<div id="popMask" class="commonMask"></div><div id="auth" class="commonPop" style=""> <p class = "commonPop-txt">' +
					remark +
					'</p> <div class = "commonPop-btn commonPop-btn-confirm" ><a class = "lf cancel" > 算了 </a> <a class = "lf confirm" > 重新上传 </a> </div> </div>'
				);
			} else {
				$('#popMask').html(remark);
			}
		}
		$('.commonPop-btn-confirm .confirm').click(function() {
			$('#popMask').css('display', 'none');
			$('#auth').css('display', 'none');

			clickTo({
				namePath: '/view/my/prove/organJoin.html',
				Extend: {
					type: 'company'
				}
			})
		})
	}
	//组织
	if (type === 'organization') {
		if (status == 1) {
			if ($('#auth').length === 0) {
				$("body").append(
					'<div id="popMask" class="commonMask"></div><div id="auth" class="commonPop"><p class="commonPop-txt">' +
					remark + '</p><div class="commonPop-btn close"><a>关闭</a></div></div>'
				);
			} else {
				$('#popMask').html(remark);
			}
		}
		if (status == 3) {
			if ($('#auth').length === 0) {
				$("body").append(
					'<div id="popMask" class="commonMask"></div><div id="auth" class="commonPop" style=""> <p class = "commonPop-txt">' +
					remark +
					'</p> <div class = "commonPop-btn commonPop-btn-confirm" ><a class = "lf cancel" > 算了 </a> <a class = "lf confirm" > 重新上传 </a> </div> </div>'
				);
			} else {
				$('#popMask').html(remark);
			}
		}
		$('.commonPop-btn-confirm .confirm').click(function() {
			$('#popMask').css('display', 'none');
			$('#auth').css('display', 'none');

			clickTo({
				namePath: '/view/my/prove/organJoin.html',
				Extend: {
					type: 'organization'
				}
			})
		})
	}

	$('#popMask').css('display', 'block');
	$('#auth').css('display', 'block');

	$('#popMask').click(function() {
		$(this).css('display', 'none');
		$('#auth').css('display', 'none');
	})

	$('.commonPop-btn.close').click(function() {
		$('#popMask').css('display', 'none');
		$('#auth').css('display', 'none');
	})

	$('.commonPop-btn-confirm .cancel').click(function() {
		$('#popMask').css('display', 'none');
		$('#auth').css('display', 'none');
	})
}
