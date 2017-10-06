$(function() {
	
	if($("#msg").val() != null && $("#msg").val() != ""){
		alert($("#msg").val());
		return false;
	}
	
	$("#register").click(function() {
		window.location.href = "/user/toRegister";
	});
	
	$("#form1").validate({
		rules: {
			userId: {
				required: true,
				minlength: 5,
				number: true
			},
			password: {
				required: true,
				minlength: 2
			}
		},
		messages: {
			userId: {
				required: "请输入用户名",
				minlength: "用户名必须为五位数字",
				number: "用户名必须为五位数字"
			},
			password: {
				required: "密码不能为空",
				minlength: "密码长度最少为2位"
			}
		},
		submitHandler: function(form){
			$.get("/user/checkUser/" + $("#userId").val() + "/" + $("#password").val(), function(data, status){
				 if(data.code == 204){
					 alert(data.msg);
					 return false;
				 }else{
					 form.submit();
				 }
		    });
		}
	});
	
})