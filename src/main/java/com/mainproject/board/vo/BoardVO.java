package com.mainproject.board.vo;

import java.sql.Timestamp;

import org.springframework.stereotype.Component;

@Component("boardVO")
public class BoardVO {
	private int post_num;
	private int category_num;
	private String title;
	private String content;
	private int views;
	private int good;
	private int bad;
	private int created_user_num;
	private Timestamp created_at;
	private int updated_user_num;
	private Timestamp updated_at;
	private boolean is_deleted;
	private int deleted_user_num;
	private Timestamp deleted_at;
	private String author;
	
	
	
	public BoardVO() {
		
	}
	
//	public BoardVO(int post_num,int category_num, String title ,String content, int views,int good,
//			int bad, int created_user_num, Timestamp created_at, int updated_user_num,
//			Timestamp updated_at,boolean is_deleted,int deleted_user_num,Timestamp deleted_at ) {
//		this.post_num = post_num;
//		this.category_num = category_num;
//		this.title = title;
//		this.content = content;
//		this.views = views;
//		this.good = good;
//		this.bad = bad;
//		this.created_user_num = created_user_num;
//		this.created_at = created_at;
//		this.updated_user_num = updated_user_num;
//		this.updated_at = updated_at;
//		this.is_deleted = is_deleted;
//		this.deleted_user_num = deleted_user_num;
//		this.deleted_at = deleted_at;
//		
//		
//	}
	
	

	public int getPost_num() {
		return post_num;
	}

	public void setPost_num(int post_num) {
		this.post_num = post_num;
	}

	public int getCategory_num() {
		return category_num;
	}

	public void setCategory_num(int category_num) {
		this.category_num = category_num;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	public int getViews() {
		return views;
	}

	public void setViews(int views) {
		this.views = views;
	}

	public int getGood() {
		return good;
	}

	public void setGood(int good) {
		this.good = good;
	}

	public int getBad() {
		return bad;
	}

	public void setBad(int bad) {
		this.bad = bad;
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
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	
}
