var api = {};
var config = {
	host:"http://"+window.location.host+"/paycoll/api"
};
require.config({
	paths:{
		"jquery":"lib/jquery-3.1.1.min",
		"underscore":"lib/underscore-min",
		"sha1":"lib/sha1",
		"utils":"utils",
		"wx":"lib/jweixin-1.2.0",
		"wxapp":"weixin"
	}
});

require(['wxapp'],function(){});

require(['utils'],function(utils){
	api.btnOk = utils.ok;
	utils.saveLogin();
});



