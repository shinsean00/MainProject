package com.mainproject.comment.service;

import java.util.List;

import com.mainproject.comment.dto.CommentDTO;
import com.mainproject.comment.dto.ReplyDTO;

public interface CommentService {
	List<CommentDTO> getAllComments(int postNum);

	CommentDTO getComment(int commentNum);

	List<ReplyDTO> getReplies(int commentNum);

	CommentDTO createComment(CommentDTO commentDTO);

	ReplyDTO createReply(ReplyDTO replyDTO);

	// CommentDTO createReply(ReplyDTO replyDTO); // createReply 메서드 추가
	CommentDTO updateComment(CommentDTO commentDTO);

	CommentDTO updateReply(CommentDTO commentDTO);

	CommentDTO updateComment(int commentNum, CommentDTO commentDTO);

	void deleteComment(int commentNum);

	void deleteReply(int commentNum);

	List<CommentDTO> getCommentsForArticle(int postNum);
}
