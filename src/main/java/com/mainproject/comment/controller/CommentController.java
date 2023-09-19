package com.mainproject.comment.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mainproject.comment.dto.CommentDTO;
import com.mainproject.comment.service.CommentService;

@RestController
@RequestMapping("/comments")
public class CommentController {
	@Autowired
	private CommentService commentService;

	// 댓글 불러오기 API
	@GetMapping("/viewArticle/{postNum}/comments")
	public ResponseEntity<List<CommentDTO>> getCommentsForArticle(@PathVariable int postNum) {
		List<CommentDTO> comments = commentService.getCommentsForArticle(postNum);
		return ResponseEntity.ok(comments);
	}

	// 댓글 작성 API
	@PostMapping
	public ResponseEntity<CommentDTO> createComment(@RequestBody CommentDTO commentDTO) {
		CommentDTO createdComment = commentService.createComment(commentDTO);
		return ResponseEntity.ok(createdComment);
	}

	// 댓글 수정 API
	@PutMapping("/{commentNum}")
	public ResponseEntity<CommentDTO> updateComment(@PathVariable int commentNum, @RequestBody CommentDTO commentDTO) {
		CommentDTO updatedComment = commentService.updateComment(commentNum, commentDTO);
		return ResponseEntity.ok(updatedComment);
	}

	// 댓글 삭제 API
	@DeleteMapping("/{commentNum}")
	public ResponseEntity<Void> deleteComment(@PathVariable int commentNum) {
		commentService.deleteComment(commentNum);
		return ResponseEntity.noContent().build();
	}

	// 댓글 조회 API
	@GetMapping("/{commentNum}")
	public ResponseEntity<CommentDTO> getComment(@PathVariable int commentNum) {
		CommentDTO commentDTO = commentService.getComment(commentNum);
		if (commentDTO != null) {
			return ResponseEntity.ok(commentDTO);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}