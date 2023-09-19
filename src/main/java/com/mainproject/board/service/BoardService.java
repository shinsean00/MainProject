package com.mainproject.board.service;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.mainproject.board.vo.BoardVO;
import com.mainproject.board.vo.VoteVO;
import com.mainproject.category.vo.CategoryVO;

public interface BoardService {
	
	// 모든 게시글 목록 조회
	public List<BoardVO> listArticles() throws Exception;

    // 카테고리 번호에 따른 페이징 정보 기반으로 특정 페이지의 게시글 목록 조회
	public List<BoardVO> getArticlesWithPaging(Integer categoryNum, int page, int perPageNum) throws Exception;
	
	// 전체 게시글 수 조회
	public int getTotalCount(Integer categoryNum) throws Exception;

    // 검색 조건과 키워드를 이용하여 게시글 검색
	public List<BoardVO> searchArticles(String searchType, String keyword, int page, int perPageNum, int categoryNum) throws Exception;

    // 검색 게시글 수 조회
	public int getSelectTotalCount(String searchType, String keyword, int categoryNum) throws Exception;
 
	
	public BoardVO viewArticle(int post_num) throws Exception;


	public void addNewArticle(BoardVO boardVO);




	List<BoardVO> getArticlesByCategory(int categoryNum);

	// 게시글 번호로 게시글 정보 불러오기
	public BoardVO getBoardByPostNum(int postNum) throws DataAccessException;
	
	void updateBoard(BoardVO board);


	void deleteBoard(int post_num);


	void increaseViews(int post_num);
	
//	추천
	void increaseGoodCount(int post_num);
//    비추천
	void increaseBadCount(int post_num);

	int checkVoteDuplicate(VoteVO voteVO);

	void voteForPost(VoteVO voteVO);

	

	






	
	





	



	



	
}
