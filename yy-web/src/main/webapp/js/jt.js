var TT = JT = {
	checkLogin : function(){
		//直接从cookie中获取value,如果有值就获取到,没有为false.
		var _ticket = $.cookie("YY_TICKET");
		var _username = $.cookie("YY_USER");
		
		if(!_ticket||!_username){ //如果cookie中没有获取到值,则直接为false
			return ;
		}
		//当dataType类型为jsonp时，jQuery就会自动在请求链接上增加一个callback的参数
		$.ajax({
			url : "http://sso.yy.com/user/query/" + _ticket+"/"+_username,
			dataType : "jsonp",
			type : "GET",
			success : function(data){
				if(data.status == 200){
					//把json串转化为js对象
					var _data = JSON.parse(data.data);
					var html =_data.username+"，欢迎来到京淘！<a href=\"http://www.yy.com/user/logout.html\" class=\"link-logout\">[退出]</a>";
					$("#loginbar").html(html);
				}
			}
		});
	}
}

$(function(){
	// 查看是否已经登录，如果已经登录查询登录信息
	TT.checkLogin();
});