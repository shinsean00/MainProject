package com.mainproject.board.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.mainproject.board.vo.BoardVO;

import com.mainproject.board.vo.VoteVO;

import com.mainproject.category.vo.CategoryVO;
import com.mainproject.paging.PagingVO;


@Repository
public class BoardDAOImpl implements BoardDAO {
	@Autowired
	private SqlSession sqlSession;
	

	@Override
    // 페이징 정보를 기반으로 게시글 목록 조회
    public List<BoardVO> selectArticlesWithPaging(PagingVO paging) throws Exception {
        // mapper.xml에서 지정한 SQL 쿼리를 실행하여 페이징 정보를 기반으로 게시글 목록을 조회합니다.
        return sqlSession.selectList("mapper.board.selectArticlesWithPaging", paging);
    }

    @Override
    // 검색 조건에 따라 게시글 목록 검색
    public List<BoardVO> searchArticles(String searchType, String keyword, int startRow, int perPageNum, int categoryNum) throws Exception {
        // 검색 조건과 키워드를 매개변수로 받아와서 파라미터 맵에 담습니다.
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("searchType", searchType);
        parameters.put("keyword", keyword);
        parameters.put("startRow", startRow);
        parameters.put("perPageNum", perPageNum);
        parameters.put("categoryNum", categoryNum);

        // mapper.xml에서 지정한 SQL 쿼리를 실행하여 검색 조건에 맞는 게시글 목록을 검색합니다.
        return sqlSession.selectList("mapper.board.searchArticles", parameters);
    }

    @Override
    // 전체 게시글 수 조회
    public int getTotalCount(int categoryNum) throws Exception {
        // mapper.xml에서 지정한 SQL 쿼리를 실행하여 전체 게시글 수를 조회합니다.
        return sqlSession.selectOne("mapper.board.getTotalCount", categoryNum);
    }
   
    
	@Override
	// 검색 게시글 수 조회
    public int getSelectTotalCount(String searchType, String keyword, int categoryNum) throws Exception {
        // 검색 조건과 키워드를 매개변수로 받아와서 파라미터 맵에 담습니다.
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("searchType", searchType);
        parameters.put("keyword", keyword);
        parameters.put("categoryNum", categoryNum);

        // mapper.xml에서 지정한 SQL 쿼리를 실행하여 검색 조건에 맞는 카테고리 수를 조회합니다.
        return sqlSession.selectOne("mapper.board.getSelectTotalCount", parameters);
    }
	
	@Override
    public List<BoardVO> getArticlesByCategory(int categoryNum) {
        return sqlSession.selectList("mapper.board.selectArticlesByCategory", categoryNum);
    }

    
	@Override
    public BoardVO viewArticle(int post_num) {
        return sqlSession.selectOne("mapper.board.selectArticleByPostNum", post_num);
    }

   
	@Override
    public void addNewArticle(BoardVO boardVO) {
        sqlSession.insert("mapper.board.insertArticle", boardVO);
    }
	
	@Override
	public BoardVO getBoardByPostNum(int post_num) throws DataAccessException {
		return sqlSession.selectOne("mapper.board.getBoardByPostNum", post_num);
	}
	
	//수정하기
	 @Override
	    public void updateBoard(BoardVO board) {
	        sqlSession.update("mapper.board.updateBoard", board);
	    }
	 
	 @Override
	    public void deleteBoard(int post_num) {
	        sqlSession.update("mapper.board.deleteBoard", post_num);
	    }
	 
	 @Override
	    public void increaseViews(int post_num) {
	        sqlSession.update("mapper.board.increaseViews", post_num);
	    }
  
   //추천
	 @Override
	    public void increaseGoodCount(int post_num) {
	        sqlSession.update("mapper.board.updateGoodCount", post_num);
	    }
	 //비추천
	    @Override
	    public void increaseBadCount(int post_num) {
	        sqlSession.update("mapper.board.updateBadCount", post_num);
	    }
	    
	    @Override
	    public int checkVoteDuplicate(VoteVO voteVO) {
	        return sqlSession.selectOne("mapper.vote.checkVoteDuplicate", voteVO);
	    }

	    @Override
	    public void insertVote(VoteVO voteVO) {
	        sqlSession.insert("mapper.vote.insertVote", voteVO);
	    }

	 @Override
	 public List<BoardVO> viewPetBoard() throws DataAccessException {
		 return sqlSession.selectList("mapper.board.viewPetBoard");
	 }
	 
	 @Override
	 public List<BoardVO> viewPlantBoard() throws DataAccessException {
		 return sqlSession.selectList("mapper.board.viewPlantBoard");
	 }
	 
	 @Override
	 public List<BoardVO> viewPopularBoard() throws DataAccessException {
		 return sqlSession.selectList("mapper.board.viewPopularBoard");
	 }
	 
	 @Override
	 public List<BoardVO> viewTopOwner() throws DataAccessException {
		 return sqlSession.selectList("mapper.board.viewTopOwner");
	 }

	}
	


