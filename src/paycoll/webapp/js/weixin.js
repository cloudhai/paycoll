define(['jquery','underscore','wx','utils'],function($,_,wx,utils){
    var WX_URL_LGOIN = "https://api.weixin.qq.com/sns/auth?access_token={0}&openid={1}&jsoncallback=?";
    var WX_URL_AUTH = "https://open.weixin.qq.com/connect/oauth2/authorize?appid={0}&redirect_uri={1}&response_type=code&scope=snsapi_base&state=/index.html#wechat_redirect";
    var WX_APPID = "wxc14159166c3aa587";
    var AUTH_URL = encodeURI("http://test.kaipai.net/paycoll/api/wx/auth");


    var isLogin = function(){
        var token,openId;
        var result = false;
        if(location.href.indexOf("index.html")>0){
            token = getUrlParams("token");
            openId = getUrlParams("openId");
        }else{
            token = localData.get("token");
            openId = localData.get("openId");
        }
        if(openId == null || token == null){
            return result;
        }else{
            localData.set("token",token);
            localData.set("openId",openId);
            result = true;
        }
        return result;
    };

    if(isWx()){
        var ticket = utils.getJsTicket(window.location.href);
        wx.config({
            debug: true, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
            appId: WX_APPID, // 必填，公众号的唯一标识
            timestamp: parseInt(ticket.timestamp), // 必填，生成签名的时间戳
            nonceStr: ticket.noncestr, // 必填，生成签名的随机串
            signature: ticket.signature,// 必填，签名，见附录1
            jsApiList: ['onMenuShareTimeline','onMenuShareAppMessage','onMenuShareWeibo','onMenuShareQQ','startRecord','playVoice'] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
        });
        wx.ready(function () {
            wx.onMenuShareAppMessage({
                title: 'title', // 分享标题
                desc: '测试', // 分享描述
                link: 'www.baidu.com', // 分享链接
                imgUrl: '', // 分享图标
                type: 'link', // 分享类型,music、video或link，不填默认为link
                dataUrl: '', // 如果type是music或video，则要提供数据链接，默认为空
                success: function () {
                    alert("分享成功！");
                },
                cancel: function () {
                    alert("取消分享");
                }
            });
        });

        if(isLogin()){
            alert("user is login");
            return;
        }else{
            alert("the user don't  login");
            window.location.href = WX_URL_AUTH.format(WX_APPID, AUTH_URL);
        }
    }else{
        alert("not weixin");
    }

});