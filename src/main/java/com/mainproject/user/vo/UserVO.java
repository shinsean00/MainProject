package com.mainproject.user.vo;
import java.sql.Timestamp;

import org.springframework.stereotype.Component;

@Component("userVO")
public class UserVO {
	private int user_num;
	private String id;
	private String pwd;
	private String name;
	private String nickname;
	private String email;
	private Timestamp birth;
	private String country_code;
	private String first_hp;
	private String second_hp;
	private String gender;
	private String is_admin;
	private Timestamp created_at;
	private int updated_user_num;
	private Timestamp updated_at;
	private boolean is_deleted;
	private int deleted_user_num;
	private Timestamp deleted_at;
	private String deleted_reason;
	private int fail_count;
	private UserRank rank;
	private boolean is_suspended;
	private String profile_picture;
	private int suspend_user_num;
	private Timestamp suspended_at;
	private int suspension_duration;
	private String suspended_reason;

	public UserVO() {
		
	}

	public int getUser_num() {
		return user_num;
	}

	public void setUser_num(int user_num) {
		this.user_num = user_num;
	}

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Timestamp getBirth() {
		return birth;
	}

	public void setBirth(Timestamp birth) {
		this.birth = birth;
	}

	public String getCountry_code() {
		return country_code;
	}

	public void setCountry_code(String country_code) {
		this.country_code = country_code;
	}

	public String getFirst_hp() {
		return first_hp;
	}

	public void setFirst_hp(String first_hp) {
		this.first_hp = first_hp;
	}

	public String getSecond_hp() {
		return second_hp;
	}

	public void setSecond_hp(String second_hp) {
		this.second_hp = second_hp;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getIs_admin() {
		return is_admin;
	}

	public void setIs_admin(String is_admin) {
		this.is_admin = is_admin;
	}

	public Timestamp getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Timestamp created_at) {
		this.created_at = created_at;
	}

	public int getUpdated_user_num() {
		return updated_user_num;
	}

	public void setUpdated_user_num(int updated_user_num) {
		this.updated_user_num = updated_user_num;
	}

	public Timestamp getUpdated_at() {
		return updated_at;
	}

	public void setUpdated_at(Timestamp updated_at) {
		this.updated_at = updated_at;
	}

	public boolean isIs_deleted() {
		return is_deleted;
	}

	public void setIs_deleted(boolean is_deleted) {
		this.is_deleted = is_deleted;
	}

	public int getDeleted_user_num() {
		return deleted_user_num;
	}

	public void setDeleted_user_num(int deleted_user_num) {
		this.deleted_user_num = deleted_user_num;
	}

	public Timestamp getDeleted_at() {
		return deleted_at;
	}

	public void setDeleted_at(Timestamp deleted_at) {
		this.deleted_at = deleted_at;
	}

	public String getDeleted_reason() {
		return deleted_reason;
	}

	public void setDeleted_reason(String deleted_reason) {
		this.deleted_reason = deleted_reason;
	}
	
	public int getFail_count() {
		return fail_count;
	}

	public void setFail_count(int fail_count) {
		this.fail_count = fail_count;
	}
	
	public UserRank getRank() {
	    return rank;
	}

	public void setRank(UserRank rank) {
	    this.rank = rank;
	}

	public boolean isIs_suspended() {
		return is_suspended;
	}

	public void setIs_suspended(boolean is_suspended) {
		this.is_suspended = is_suspended;
	}

	public String getProfile_picture() {
		return profile_picture;
	}

	public void setProfile_picture(String profile_picture) {
		this.profile_picture = profile_picture;
	}
	
	public int getSuspend_user_num() {
		return suspend_user_num;
	}

	public void setSuspend_user_num(int suspend_user_num) {
		this.suspend_user_num = suspend_user_num;
	}

	public Timestamp getSuspended_at() {
		return suspended_at;
	}

	public void setSuspended_at(Timestamp suspended_at) {
		this.suspended_at = suspended_at;
	}

	public int getSuspension_duration() {
		return suspension_duration;
	}

	public void setSuspension_duration(int suspension_duration) {
		this.suspension_duration = suspension_duration;
	}

	public String getSuspended_reason() {
		return suspended_reason;
	}

	public void setSuspended_reason(String suspended_reason) {
		this.suspended_reason = suspended_reason;
	}

}
