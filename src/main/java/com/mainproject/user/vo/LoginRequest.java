package com.mainproject.user.vo;

public class LoginRequest {
	private String id;
    private String pwd;
    private String captchaKey;
    private String captchaInput;
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	
	public String getCaptchaKey() {
		return captchaKey;
	}
	public void setCaptchaKey(String captchaKey) {
		this.captchaKey = captchaKey;
	}
	
	public String getCaptchaInput() {
		return captchaInput;
	}
	public void setCaptchaInput(String captchaInput) {
		this.captchaInput = captchaInput;
	}

}
