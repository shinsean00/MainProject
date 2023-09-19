package com.mainproject.comment.model;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mainproject.comment.dto.CommentDTO;
import com.mainproject.comment.dto.ReplyDTO;

@Mapper
public interface CommentMapper {
	List<CommentDTO> getAllComments(int postNum);

	CommentDTO getComment(int commentNum);

	List<ReplyDTO> getReplies(int commentNum);

	CommentDTO createComment(CommentDTO commentDTO);

	ReplyDTO createReply(ReplyDTO replyDTO);

	CommentDTO updateComment(CommentDTO commentDTO);

	ReplyDTO updateReply(ReplyDTO replyDTO);

	void deleteComment(int commentNum);

	void deleteReply(int replyNum);
}
