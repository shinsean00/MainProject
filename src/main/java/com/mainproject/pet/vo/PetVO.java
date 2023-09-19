package com.mainproject.pet.vo;
import java.sql.Timestamp;

import org.springframework.stereotype.Component;

@Component("petVO")
public class PetVO {
	
	private int pet_no;
    private String name;
    private Timestamp birth;
	private int created_user_num;
	private Timestamp created_at;
	private int updated_user_num;
	private Timestamp updated_at;
    private int giver_user_num;
    private int adopted_user_num;
    private Timestamp adopted_at;
	private boolean is_deleted;
	private int deleted_user_num;
	private Timestamp deleted_at;
	private String owner_name;
	private String profile_picture;
	
	
    public int getPet_no() {
		return pet_no;
	}
	public void setPet_no(int pet_no) {
		this.pet_no = pet_no;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Timestamp getBirth() {
		return birth;
	}
	public void setBirth(Timestamp birth) {
		this.birth = birth;
	}
	public int getCreated_user_num() {
		return created_user_num;
	}
	public void setCreated_user_num(int created_user_num) {
		this.created_user_num = created_user_num;
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
	public int getGiver_user_num() {
		return giver_user_num;
	}
	public void setGiver_user_num(int giver_user_num) {
		this.giver_user_num = giver_user_num;
	}
	public int getAdopted_user_num() {
		return adopted_user_num;
	}
	public void setAdopted_user_num(int adopted_user_num) {
		this.adopted_user_num = adopted_user_num;
	}
	public Timestamp getAdopted_at() {
		return adopted_at;
	}
	public void setAdopted_at(Timestamp adopted_at) {
		this.adopted_at = adopted_at;
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
	public String getOwner_name() {
		return owner_name;
	}
	public void setOwner_name(String owner_name) {
		this.owner_name = owner_name;
	}
	public String getProfile_picture() {
		return profile_picture;
	}

	public void setProfile_picture(String profile_picture) {
		this.profile_picture = profile_picture;
	}

}
