package com.mainproject.board.dao;

import java.util.List;

import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;


import com.mainproject.board.vo.BoardVO;

import com.mainproject.board.vo.VoteVO;

import com.mainproject.paging.PagingVO;

public interface BoardDAO {

	// 페이징 정보를 기반으로 게시글 목록 조회
    public List<BoardVO> selectArticlesWithPaging(PagingVO paging) throws Exception;
    
    // 검색 조건에 따라 게시글 목록 검색
    public List<BoardVO> searchArticles(String searchType, String keyword, int startRow, int perPageNum, int categoryNum) throws Exception;
    
    // 전체 게시글 수 조회
    public int getTotalCount(int categoryNum) throws Exception;
    
    // 검색 게시글 수 조회
    public int getSelectTotalCount(String searchType, String keyword, int categoryNum) throws Exception;
    
	List<BoardVO> getArticlesByCategory(int categoryNum);

	BoardVO viewArticle(int post_num);

	void addNewArticle(BoardVO boardVO);

	public BoardVO getBoardByPostNum(int post_num) throws DataAccessException;
	
	void updateBoard(BoardVO board);

	void deleteBoard(int post_num);

	void increaseViews(int post_num);
	
	//추천
	void increaseGoodCount(int post_num);
    //비추천
	void increaseBadCount(int post_num);
	
	public List<BoardVO> viewPetBoard() throws DataAccessException;
	public List<BoardVO> viewPlantBoard() throws DataAccessException;
	public List<BoardVO> viewPopularBoard() throws DataAccessException;
	public List<BoardVO> viewTopOwner() throws DataAccessException;

	int checkVoteDuplicate(VoteVO voteVO);

	void insertVote(VoteVO voteVO);

}
