package cn.lfy.common.framework.exception;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;

public class ApplicationException extends RuntimeException implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -560581110872734762L;

	private boolean logged;

	private int errorCode;

	private String detailMessage;

	private String privateKey;

	private String contextParams;

	private String[] messageParams;

	private String sendRedirectURL;

	public boolean isLogged()
	{
		return logged;
	}

	public void setLogged(boolean logged)
	{
		this.logged = logged;
	}

	public int getErrorCode()
	{
		return errorCode;
	}

	public void setErrorCode(int errorCode)
	{
		this.errorCode = errorCode;
	}

	public String getDetailMessage()
	{
		return detailMessage;
	}

	public void setDetailMessage(String detailMessage)
	{
		this.detailMessage = detailMessage;
	}

	public String getPrivateKey()
	{
		return privateKey;
	}

	public void setPrivateKey(String privateKey)
	{
		this.privateKey = privateKey;
	}

	public String getContextParams()
	{
		return contextParams;
	}

	public void setContextParams(String contextParams)
	{
		this.contextParams = contextParams;
	}

	public String[] getMessageParams()
	{
		return messageParams;
	}

	public void setMessageParams(String[] messageParams)
	{
		this.messageParams = messageParams;
	}

	public String getSendRedirectURL()
	{
		return sendRedirectURL;
	}

	public void setSendRedirectURL(String sendRedirectURL)
	{
		this.sendRedirectURL = sendRedirectURL;
	}

	public ApplicationException(boolean logged, Exception e, int errorCode, String privateKey, Object contextParams, String[] messageParams, String sendRedirectURL)
	{
		super(e);
		this.logged = logged;
		this.errorCode = errorCode;
		this.privateKey = privateKey;
		this.contextParams = JSON.toJSONString(contextParams);
		this.messageParams = messageParams;
		this.sendRedirectURL = sendRedirectURL;
	}

	public ApplicationException(int errorCode, Exception e, String privateKey, Object contextParams, String[] messageParams, String sendRedirectURL)
	{
		super(e);
		this.logged = false;
		this.errorCode = errorCode;
		this.privateKey = privateKey;
		this.contextParams = JSON.toJSONString(contextParams);
		this.messageParams = messageParams;
		this.sendRedirectURL = sendRedirectURL;
	}

	public ApplicationException(int errorCode, Exception e, String privateKey, Object contextParams, String[] messageParams)
	{
		super(e);
		this.logged = false;
		this.errorCode = errorCode;
		this.privateKey = privateKey;
		this.contextParams = JSON.toJSONString(contextParams);
		this.messageParams = messageParams;
		this.sendRedirectURL = null;
	}

	public ApplicationException(int errorCode, Exception e, String privateKey, Object contextParams)
	{
		super(e);
		this.logged = false;
		this.errorCode = errorCode;
		this.privateKey = privateKey;
		this.contextParams = JSON.toJSONString(contextParams);
		this.messageParams = null;
		this.sendRedirectURL = null;
	}

	public ApplicationException(int errorCode, Exception e, String privateKey)
	{
		super(e);
		this.logged = false;
		this.errorCode = errorCode;
		this.privateKey = privateKey;
		this.contextParams = "";
		this.messageParams = null;
		this.sendRedirectURL = null;
	}

	public ApplicationException(int errorCode, Exception e)
	{
		super(e);
		this.logged = false;
		this.errorCode = errorCode;
		this.privateKey = "";
		this.contextParams = "";
		this.messageParams = null;
		this.sendRedirectURL = null;
	}
	public ApplicationException(int errorCode, String message, String[] messageParams)
	{
		this.logged = false;
		this.errorCode = errorCode;
		this.privateKey = "";
		this.contextParams = "";
		this.messageParams = messageParams;
		this.sendRedirectURL = null;
		this.detailMessage = message;
	}
	public static ApplicationException newInstance(int errorCode) {
		ApplicationException ae = new ApplicationException(errorCode, new Exception());
		return ae;
	}
	public static ApplicationException newInstance(int errorCode, String message) {
		ApplicationException ae = new ApplicationException(errorCode, message, null);
		return ae;
	}
	public static ApplicationException newInstance(int errorCode, String ...messageParams) {
		ApplicationException ae = new ApplicationException(errorCode, "", messageParams);
		return ae;
	}
}
