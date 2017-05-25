define(['underscore','jquery'],function(_,$){
    if (!Date.prototype.format) {
        Date.prototype.format = function (format) {
            var dateRegExpList = {
                'm+': this.getMonth() + 1,
                'd+': this.getDate(),
                'h+': this.getHours(),
                'i+': this.getMinutes(),
                's+': this.getSeconds()
            }
            if (/(y+)/.test(format)) {
                format = format.replace(RegExp.$1, this.getFullYear().toString().substr(4 - RegExp.$1.length))
            }

            _.each(dateRegExpList, function (val, regExp) {
                if (new RegExp('(' + regExp + ')').test(format)) {
                    var numStr = '0' + val.toString();
                    format = format.replace(RegExp.$1, numStr.length>2 ? numStr.substr(numStr.lastIndexOf('0',numStr.length-2)+1) : numStr);
                }
            });

            return format;
        }
    }
//操作cookie
    window.localData = {
        set : function(name, value) {
            var Days = 30; // 此 cookie 将被保存 30 天
            var exp = new Date(); // new Date("December 31, 9998");
            exp.setTime(exp.getTime() + Days * 24 * 60 * 60 * 1000);
            document.cookie = name + "=" + escape(value) + "; expires="
                + exp.toGMTString() + "; path=/";
            // 判断是否打开cookie
            if (document.cookie == '' && localData.get(name) == "") {
                alert('亲!请开启cookie后再访问');
                return;
            }
        },
        get : function(name) {
            var arr = document.cookie.match(new RegExp("(^| )" + name
                + "=([^;]*)(;|$)"));
            if (arr != null) {
                return unescape(arr[2]);
            }
            return null;
        },
        remove : function(name) {
            var exp = new Date();
            exp.setTime(exp.getTime() - 1);
            var cval = localData.get(name);
            document.cookie = name + "=" + cval + "; expires=" + exp.toGMTString()
                + "; path=/";

        },
        clearCookies : function() {
            var keys = document.cookie.match(/[^ =;]+(?=\=)/g);
            if (keys) {
                for (var i = keys.length; i--;)
                    document.cookie = keys[i] + '=0;expires='
                        + new Date(0).toUTCString() + "; path=/";
            }
        }
    };
//判断 是不是微信浏览器
    window.isWx = function(){
        var ua = navigator.userAgent.toLowerCase();
        if(ua.match(/MicroMessenger/i)=="micromessenger") {
            return true;
        } else {
            return false;
        }
	}
//取得url里的参数
	window.getUrlParams = function getUrlParam(name) {
            var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
            var r = window.location.search.substr(1).match(reg);  //匹配目标参数
            if (r != null) return decodeURI(r[2]); return null; //返回参数值
        }
//string 占位
    if(!String.prototype.format){
        String.prototype.format = function () {
            if(arguments.length==0) return this;
            for(var s=this, i=0; i<arguments.length; i++)
                s=s.replace(new RegExp("\\{"+i+"\\}","g"), arguments[i]);
            return s;
        }
    }
    //产生随机字符串
    var getNonce = function (len) {
        len = len || 32;
        var $chars = 'ABCDEFGHJKMNPQRSTWXYZabcdefhijkmnprstwxyz2345678';    /****默认去掉了容易混淆的字符oOLl,9gq,Vv,Uu,I1****/
        var maxPos = $chars.length;
        var nonce = '';
        for (var i = 0; i < len; i++) {
            nonce += $chars.charAt(Math.floor(Math.random() * maxPos));
        }
        return nonce;
    };

	var getWxSigntuare = function(nonce,timestamp,ticket,url){
		var array = new Array(4);
		array[0] = "nonce={0}".format(nonce);
		array[1] = "timestamp={0}".format(timestamp);
		array[2] = "ticket={0}".format(ticket);
		array[3] = "format={0}".format(url);
		var sortedArray = array.sort();
		var str = "{0}&{1}&{2}&{3}".format(sortedArray[0],sortedArray[1],sortedArray[2],sortedArray[3]);
		 return hex_sha1(str);
	};

	var getJsTicket = function (url) {
		var ticket = {};
		$.ajax({
			url:config.host+"/wx/jssign",
			type:"GET",
			dataType:"json",
			data:{url:url},
            async:false,
			success:function (data) {
				if(data.code =="1"){
					ticket = data.result.data;
				}else{
					return;
				}
            },
			error:function (XMLHttpRequest, textStatus, errorThrown) {
                alert(textStatus);
                return;
            }
		});
		return ticket;
    };
    
	var saveLogin = function () {
		var token = getUrlParams("token");
		var openId = getUrlParams("openId");
		if(token != null){
		    localData.set("token",token);
        }
        if(openId != null){
		    localData.set("openId",openId);
        }
    };

    var ok = function () {
    	var str = "this a test for {0}";
		alert(str.format("paycoll"));
		fun();
    };
	return {
		ok:ok,
        saveLogin:saveLogin,
		getNonce:getNonce,
		getWxSigntuare:getWxSigntuare,
		getJsTicket:getJsTicket,
	}

});