package com.mainproject.board.vo;

import java.sql.Timestamp;

import org.springframework.stereotype.Component;

@Component("voteVO")
public class VoteVO {
	private int user_num;
	private int post_num;
	private String vote_type;
	


	public String getVote_type() {
		return vote_type;
	}


	public void setVote_type(String vote_type) {
		this.vote_type = vote_type;
	}


	public VoteVO() {
		
	}


	public int getUser_num() {
		return user_num;
	}


	public void setUser_num(int user_num) {
		this.user_num = user_num;
	}


	public int getPost_num() {
		return post_num;
	}


	public void setPost_num(int post_num) {
		this.post_num = post_num;
	}
	
	
	
	
}
