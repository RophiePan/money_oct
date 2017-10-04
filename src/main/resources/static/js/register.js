$(function(){
	$("#form1").validate({
		rules: {
			recommendId: {
				required: true,
				min: 10000,
				number: true
			},
			password: {
				required: true,
				minlength: 2
			},
			confirmPassword: {
				equalTo: "#password"
			},
			userName: {
				required: true
			},
			idCard: {
				required: true,
				minlength: 18
			},
			bankCard: {
				required: true,
				minlength: 10
			},
			bankName: {
				required: true,
				minlength: 10
			},
			phoneNumber: {
				required: true,
				minlength: 11
			}
		},
		messages: {
			recommendId: {
				required: "请输入推荐人id",
				min: "推荐人Id至少为五位",
				number: "用户名必须为五位数字"
			},
			password: {
				required: "密码不能为空",
				minlength: "密码长度最少为2位"
			},
			confirmPassword: {
				equalTo: "两次密码不一致"
			},
			userName: {
				required: "姓名不能为空"
			},
			idCard: {
				required: "身份证不能为空",
				minlength: "身份证至少为18位"
			},
			bankCard: {
				required: "银行卡不能为空",
				minlength: "银行卡至少为10位"
			},
			bankName: {
				required: "开户行不能为空",
				minlength: "开户行至少为10位"
			},
			phoneNumber: {
				required: "手机号不能为空",
				minlength: "手机号至少为11位"
			}
		},
		submitHandler: function(form){
			 $.get("/user/checkUser/" + $("#recommendId").val(), function(data, status){
				 if(data.code == 204){
					 alert("推荐人id不存在");
					 return false;
				 }else{
					 form.submit();
				 }
		    });
		}
	});
});