package cn.lfy.common.framework.exception;

public interface ErrorCode extends BaseErrorCode
{
	/**
     * 权限拒绝
     */
    static final int PERMISSION_DENIED = SERVER_ERROR + SECURITY + 1;
    /**
     * 访问令牌无效
     */
    static final int ACCESS_TOKEN_INVALID = SERVER_ERROR + SECURITY + 4;
    /**
     * 签名无效
     */
    static final int SIGN_ILLEGAL = SERVER_ERROR + SECURITY + 6;
    /**
     * 未授权操作
     */
    static final int UNAUTHORIZED_OPERATE = SERVER_ERROR + SECURITY + 7;
    /**
     * {0}参数无效
     */
    static final int PARAM_ILLEGAL = SERVER_ERROR + WEB + 3;
    /**
     * {0}必须大于0且小于{1}
     */
    static final int PARAM_OUT_OF_RANGE = SERVER_ERROR + WEB + 4;
    /**
     * {0}长度不能超过{1}
     */
    static final int PARAM_OUT_OF_LENGTH = SERVER_ERROR + WEB + 6;
    /**
     * 用户名格式错误
     */
    static final int USERNAME_FORMAT_ERROR = SERVER_ERROR + ACCOUNT + 10;
    /**
     * {}的字符数应在{}到{}之间
     */
    static final int PARAM_LENGTH_RANGE = SERVER_ERROR + ACCOUNT + 11;
    /**
     * 密码需至少包含数字、字母和符号中的两种
     */
    static final int PASSWORD_FORMAT_ERROR = SERVER_ERROR + ACCOUNT + 12;
    /**
     * 账户包含敏感词汇
     */
    static final int USERNAME_CONTAIN_SENSITIVE = SERVER_ERROR + ACCOUNT + 13;
    
    static final int EXIST = SERVER_ERROR + 12;
    
    static final int NOT_EXIST = SERVER_ERROR + 13;
}
