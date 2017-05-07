package cn.lfy.common.utils;

public enum Constants {

	NONE(-1, 300, "未定义异常"),
	
	FAIL(0, 300, "fail"),

	OK(1, 200, "ok"),
	
	BADREQ_FILE_NOT_SELECTED(-100, 300, "请选择要上传的文件"),
	BADREQ_PICTURE_SIZE_DISQUALIFY(-101, 300, "图片尺寸不合格"),
	BADREQ_UPLOAD_ICON_FAIL(-102, 300, "上传图片失败"),
	BADREQ_UPLOAD_APK_FAIL(-103, 300, "上传APK包失败"),
	BADREQ_UPLOAD_FILE_FAIL(-104, 300, "上传文件失败"),
	BADREQ_PARAMETER_EXCEPTION(-105, 300, "参数异常"),
	
	BADREQ_DIALOG_LOGIN_SUCCESS(1, 403, "登录成功"),
	BADREQ_LOGIN_PASSWORD_ERROR(-201, 403, "用户名或密码错误"),
	BADREQ_LOGIN_USER_INEXISTENCE(-200, 403, "当前用户不存在"),
	BADREQ_LOGIN_NAME_AND_PASSWORD_NOTNULL(-202, 403, "用户名和密码不能为空"),
	BADREQ_USER_LOGIN_FAIL(-203, 403, "登录失败,请联系管理员!"),

	BADREQ_AUTHENTICATION_FAIL(-300, 403, "您没有此功能的权限,请联系管理员!"),
	
	BADREQ_CHANNEL_DATA_ISNULL(-400, 300, "此大渠道数据为空"),
	
	BADREQ_USER_NOT_RELATE_BIGQN(-500, 403, "当前用户没有指定合作渠道")
	
	
	;
	
	private Integer code;

	private Integer statusCode;
	
	private String message;
	
	
	private Constants(){
		
	}
	
	private Constants(Integer _code, Integer _statusCode, String _message){
		this.code = _code;
		this.statusCode = _statusCode;
		this.message = _message;
	}
	
	
	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public Integer getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(Integer statusCode) {
		this.statusCode = statusCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
