package com.mainproject.board.service;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mainproject.board.dao.BoardDAO;
import com.mainproject.board.vo.BoardVO;

import com.mainproject.board.vo.VoteVO;
import com.mainproject.user.service.UserService;
import com.mainproject.user.vo.UserVO;

import com.mainproject.category.vo.CategoryVO;
import com.mainproject.paging.Criteria;
import com.mainproject.paging.PagingVO;


@Repository("boardDAO")
public class BoardServiceImpl  implements BoardService{
	@Autowired
	private BoardDAO boardDAO;
	
	@Autowired
	private UserService userService;


	@Override
    // 모든 게시글 목록 조회
    public List<BoardVO> listArticles() throws Exception {
        // 모든 게시글 목록을 조회하기 위해 페이징 정보를 가진 PagingVO 객체를 생성하고 DAO에 전달합니다.
        return boardDAO.selectArticlesWithPaging(new PagingVO());
    }

    @Override
    // 카테고리 번호에 따른 페이징 정보 기반으로 특정 페이지의 게시글 목록 조회
    public List<BoardVO> getArticlesWithPaging(Integer categoryNum, int page, int perPageNum) throws Exception {
        // 요청한 페이지와 한 페이지당 보여줄 개수를 기반으로 페이징 정보를 설정하여 DAO에 전달합니다.
        PagingVO paging = new PagingVO();
        paging.setCri(new Criteria(page, perPageNum));
        paging.setCategoryNum(categoryNum); // categoryNum을 설정합니다.
        return boardDAO.selectArticlesWithPaging(paging);
    }
    
    @Override
    // 카테고리 번호에 따른 전체 게시글 수 조회
    public int getTotalCount(Integer categoryNum) throws Exception {
        // 전체 게시글 수를 조회하여 반환합니다.
        return boardDAO.getTotalCount(categoryNum);
    }

    @Override
    // 검색 조건과 키워드를 이용하여 게시글 검색
    public List<BoardVO> searchArticles(String searchType, String keyword, int page, int perPageNum, int categoryNum) throws Exception {
        // 검색 결과를 페이징 처리하기 위해 시작 행을 계산합니다.
        int startRow = (page - 1) * perPageNum;

        // 검색 쿼리를 실행하여 결과를 가져옴
        List<BoardVO> searchResult = boardDAO.searchArticles(searchType, keyword, startRow, perPageNum, categoryNum);
        return searchResult;
    }

	@Override
	// 검색 게시글 수 조회
	public int getSelectTotalCount(String searchType, String keyword, int categoryNum) throws Exception {
		// 전체 게시글 수를 조회하여 반환합니다.
        return boardDAO.getSelectTotalCount(searchType, keyword, categoryNum);
	}

	
	 @Override
	    public List<BoardVO> getArticlesByCategory(int categoryNum) {
	        return boardDAO.getArticlesByCategory(categoryNum);
	    }

	 @Override
	    public BoardVO viewArticle(int post_num) {
	        return boardDAO.viewArticle(post_num);
	    }

	 @Override
	 public void addNewArticle(BoardVO boardVO) {
		 int userNum = boardVO.getCreated_user_num();
	    	UserVO userInfo = userService.getUserByUserNum(userNum);
	    	String author = userInfo.getNickname();
	    	boardVO.setAuthor(author);
	    boardDAO.addNewArticle(boardVO);
	 }
	 
	 // 게시글 번호로 게시글 정보 불러오기
	 @Override
	 public BoardVO getBoardByPostNum(int post_num) throws DataAccessException {
		 return boardDAO.getBoardByPostNum(post_num);
	 }
	    
	    //수정하기
	    
	    @Override
	    public void updateBoard(BoardVO board) {
	        boardDAO.updateBoard(board);
	    }
	    

		@Override
	    public void deleteBoard(int post_num) {
	        boardDAO.deleteBoard(post_num);
	    }
		
		//조회수 증가
		 @Override
		 public void increaseViews(int post_num) {
		     boardDAO.increaseViews(post_num);
		 }
		 
		 //추천
		 @Override
		    public void increaseGoodCount(int post_num) {
		        boardDAO.increaseGoodCount(post_num);
		  }
		 
		 //비추천
		  @Override
		   public void increaseBadCount(int post_num) {
		      boardDAO.increaseBadCount(post_num);
		  }
		  
		  @Override
		    public int checkVoteDuplicate(VoteVO voteVO) {
		        return boardDAO.checkVoteDuplicate(voteVO);
		    }

		    @Override
		    public void voteForPost(VoteVO voteVO) {
		        int existingVoteCount = boardDAO.checkVoteDuplicate(voteVO);
		        if (existingVoteCount == 0) {
		            boardDAO.insertVote(voteVO);
		        }
		    }

}
