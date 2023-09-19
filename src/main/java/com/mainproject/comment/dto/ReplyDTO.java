package com.mainproject.comment.dto;

import java.util.Date;

public class ReplyDTO {
    private int replyNum;
    private int commentNum;
    private String content;
    private int createdUserNum;
    private Date createdAt;
    private boolean isDeleted;
    private Integer deletedUserNum;
    private Date deletedAt;
	public int getReplyNum() {
		return replyNum;
	}
	public void setReplyNum(int replyNum) {
		this.replyNum = replyNum;
	}
	public int getCommentNum() {
		return commentNum;
	}
	public void setCommentNum(int commentNum) {
		this.commentNum = commentNum;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getCreatedUserNum() {
		return createdUserNum;
	}
	public void setCreatedUserNum(int createdUserNum) {
		this.createdUserNum = createdUserNum;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public boolean isDeleted() {
		return isDeleted;
	}
	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	public Integer getDeletedUserNum() {
		return deletedUserNum;
	}
	public void setDeletedUserNum(Integer deletedUserNum) {
		this.deletedUserNum = deletedUserNum;
	}
	public Date getDeletedAt() {
		return deletedAt;
	}
	public void setDeletedAt(Date deletedAt) {
		this.deletedAt = deletedAt;
	}
	public ReplyDTO(int replyNum, int commentNum, String content, int createdUserNum, Date createdAt, boolean isDeleted,
			Integer deletedUserNum, Date deletedAt) {
		super();
		this.replyNum = replyNum;
		this.commentNum = commentNum;
		this.content = content;
		this.createdUserNum = createdUserNum;
		this.createdAt = createdAt;
		this.isDeleted = isDeleted;
		this.deletedUserNum = deletedUserNum;
		this.deletedAt = deletedAt;
	}

	// 대댓글의 유효성을 검사하는 메서드
    public boolean isValid() {
        return content != null && !content.isEmpty();
    }
}
