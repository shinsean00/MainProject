package com.mainproject.comment.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mainproject.comment.dto.CommentDTO;
import com.mainproject.comment.dto.ReplyDTO;
import com.mainproject.comment.model.CommentMapper;

@Service
public class CommentServiceImpl implements CommentService {

	private final CommentMapper commentMapper;

	@Autowired
	public CommentServiceImpl(CommentMapper commentMapper) {
		this.commentMapper = commentMapper;
	}

	@Override
	public List<CommentDTO> getAllComments(int postNum) {
		return commentMapper.getAllComments(postNum);
	}

	@Override
	public CommentDTO getComment(int commentNum) {
		return commentMapper.getComment(commentNum);
	}

	@Override
	public List<ReplyDTO> getReplies(int commentNum) {
		return commentMapper.getReplies(commentNum);
	}

	@Override
	@Transactional
	public CommentDTO createComment(CommentDTO commentDTO) {
		commentMapper.createComment(commentDTO);
		return commentDTO;
	}

	@Override
	@Transactional
	public ReplyDTO createReply(ReplyDTO replyDTO) {
		commentMapper.createReply(replyDTO); // 대댓글 생성은 ReplyDTO를 사용
		return replyDTO;
	}

	@Override
	@Transactional
	public CommentDTO updateComment(CommentDTO commentDTO) {
		commentMapper.updateComment(commentDTO);
		return commentDTO;
	}

	@Override
	@Transactional
	public CommentDTO updateReply(CommentDTO commentDTO) {
		commentMapper.updateComment(commentDTO); // 대댓글 수정은 댓글과 동일한 테이블을 사용
		return commentDTO;
	}

	@Override
	public CommentDTO updateComment(int commentNum, CommentDTO commentDTO) {
		CommentDTO existingComment = commentMapper.getComment(commentNum);

		if (existingComment != null) {
			existingComment.setContent(commentDTO.getContent()); // 내용 업데이트

			commentMapper.updateComment(existingComment);

			return existingComment;
		} else {
			// 데이터베이스에서 해당 댓글을 찾을 수 없는 경우
			return null;
		}
	}

	@Override
	@Transactional
	public void deleteComment(int commentNum) {
		commentMapper.deleteComment(commentNum);
	}

	@Override
	@Transactional
	public void deleteReply(int commentNum) {
		commentMapper.deleteComment(commentNum); // 대댓글 삭제는 댓글과 동일한 테이블을 사용
	}

	@Override
	public List<CommentDTO> getCommentsForArticle(int postNum) {
		// TODO Auto-generated method stub
		return null;
	}
}
