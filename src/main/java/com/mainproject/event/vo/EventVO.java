package com.mainproject.event.vo;
import java.sql.Timestamp;

import org.springframework.stereotype.Component;

@Component("eventVO")
public class EventVO {
	
	private int event_num;
	private String title;
	private Timestamp started_at;
	private Timestamp ended_at;
	private String location;
	private String content;
	private String is_admin;
	private int created_user_num;
	private Timestamp created_at;
	private int updated_user_num;
	private Timestamp updated_at; 
	private boolean is_deleted; 
	private Integer deleted_user_num;
	private Timestamp deleted_at;
	private int event_order;
	private String event_user_name;
	private boolean allday;
	private String url;


	public EventVO() { 
	 
	}  

	
	public int getEvent_num() {
		return event_num;
	}

	public void setEvent_num(int event_num) {
		this.event_num = event_num;
	}

	public String getTitle() {
		return title;
	} 

	public void setTitle(String title) {
		this.title = title;
	}

	public Timestamp getStarted_at() {
		return started_at;
	}

	public void setStarted_at(Timestamp started_at) { 
		this.started_at = started_at; 
	}

	public Timestamp getEnded_at() {  
		return ended_at;
	}

	public void setEnded_at(Timestamp ended_at) { 
		this.ended_at = ended_at;
	}

	public String getLocation() { 
		return location;
	}

	public void setLocation(String location) { 
		this.location = location;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content; 
	}
 
	public String getIs_admin() {
		return is_admin;
	}

	public void setIs_admin(String is_admin) {
		this.is_admin = is_admin;
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

	public boolean isIs_deleted() { 
		return is_deleted; 
	} 

	public void setIs_deleted(boolean is_deleted) {
		this.is_deleted = is_deleted;
	}

	public Integer getDeleted_user_num() { 
        return deleted_user_num;
    }

	public void setDeleted_user_num(Integer deleted_user_num) { 
        this.deleted_user_num = deleted_user_num;
    } 

	public Timestamp getDeleted_at() {
		return deleted_at;
	}

	public void setDeleted_at(Timestamp deleted_at) {
		this.deleted_at = deleted_at;
	}

	public int getEvent_order() {
		return event_order;
	}

	public void setEvent_order(int event_order) {
		this.event_order = event_order;
	}

	public String getEvent_user_name() {
		return event_user_name;
	}

	public void setEvent_user_name(String username) {
	    this.event_user_name = username;
	}
	
	public boolean isAllday() {
		return allday;
	}

	public void setAllday(boolean allday) {
		this.allday = allday;
	}
	
	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}
	
 
}
