package com.mainproject.board.controller;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mainproject.board.service.BoardService;
import com.mainproject.board.vo.BoardVO;
import com.mainproject.board.vo.VoteVO;
import com.mainproject.category.service.CategoryService;
import com.mainproject.category.vo.CategoryVO;
import com.mainproject.paging.PagingUtils;
import com.mainproject.paging.PagingVO;
import com.mainproject.user.service.UserService;
import com.mainproject.user.vo.UserVO;

@Controller("boardController")
public class BoardControllerImpl implements BoardController{
	@Autowired
	private BoardService boardService;
	
	@Autowired
    private CategoryService categoryService; // CategoryService 주입
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private BoardVO boardVO;
	
	@Autowired
	private VoteVO voteVO;
	
	@Autowired
    private HttpServletRequest request;

	
	// 게시글 리스트
    @Override
    @RequestMapping(value = "/board/articles-list.do", method = RequestMethod.GET)
    public ModelAndView listArticles(
            HttpServletRequest request, HttpServletResponse response,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int perPageNum,
            @RequestParam(defaultValue = "false") boolean isSearch,
            @RequestParam(required = false) Integer newPerPageNum,
            @RequestParam("categoryNum") int categoryNum) throws Exception {

        String viewName = (String) request.getAttribute("viewName");
        List<BoardVO> articlesList;
        
        // 카테고리 번호로 조회하기 위해 categoryNum값을 바인딩
        CategoryVO category = getCategoryByCategoryNum(categoryNum);
        
        int totalCount;
        PagingVO paging;
        

        if (newPerPageNum != null) {
            // 페이지당 아이템 수가 변경된 경우의 로직
        	// System.out.println(newPerPageNum+"=====================");
        	articlesList = boardService.getArticlesWithPaging(categoryNum, page, newPerPageNum);
            totalCount = boardService.getTotalCount(categoryNum);
            paging = PagingUtils.createPaging(page, newPerPageNum, totalCount, isSearch);
        } else {
            // 기존의 게시글 목록 조회 로직
        	articlesList = boardService.getArticlesWithPaging(categoryNum, page, perPageNum);
            totalCount = boardService.getTotalCount(categoryNum);
            paging = PagingUtils.createPaging(page, perPageNum, totalCount, isSearch);
        }
        
        // 페이지 계산 및 페이징 정보 설정
        List<Integer> pageNumbers = PagingUtils.calculatePageNumbers(paging.getCurrentBlock(), paging.getPageCount(), paging.getTotalPage());
        List<Integer> blockPageNumbers = PagingUtils.calculateBlockPageNumbers(paging.getCurrentBlock(), paging.getPageCount(), paging.getTotalPage());
        
        // ModelAndView 객체 생성 및 데이터 설정
        ModelAndView mav = new ModelAndView(viewName);
        mav.addObject("articlesList", articlesList);
        mav.addObject("paging", paging);
        mav.addObject("pageNumbers", pageNumbers);
        mav.addObject("blockPageNumbers", blockPageNumbers);
        
        mav.addObject("category", category);
        return mav;
    }
    
    // 게시글 리스트 검색
    @Override
    @RequestMapping(value = "/board/articles-list-search.do", method = RequestMethod.GET)
    public ModelAndView searchArticles(
            @RequestParam("searchType") String searchType,
            @RequestParam("keyword") String keyword,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int perPageNum,
            @RequestParam(defaultValue = "false") boolean isSearch,
            @RequestParam(required = false) Integer newPerPageNum,
            @RequestParam("categoryNum") int categoryNum) throws Exception {
       
        String viewName = (String) request.getAttribute("viewName");

        // 새로운 페이지당 아이템 수가 지정된 경우, 해당 값을 사용하도록 설정
        if (newPerPageNum != null) {
            perPageNum = newPerPageNum;
        }
        
        // 카테고리 번호로 조회하기 위해 categoryNum값을 바인딩
        CategoryVO category = getCategoryByCategoryNum(categoryNum);
        
        // 검색 결과에 따른 아이템 총 개수를 조회
        int totalCount = boardService.getSelectTotalCount(searchType, keyword, categoryNum);

        // 검색 결과에 따른 페이징 정보 생성
        PagingVO paging = PagingUtils.createPaging(page, perPageNum, totalCount, true);

        // 페이지 계산 및 페이징 정보 설정
        List<Integer> pageNumbers = PagingUtils.calculatePageNumbers(paging.getCurrentBlock(), paging.getPageCount(), paging.getTotalPage());
        List<Integer> blockPageNumbers = PagingUtils.calculateBlockPageNumbers(paging.getCurrentBlock(), paging.getPageCount(), paging.getTotalPage());

        // 검색 결과에 따른 카테고리 목록 조회
        List<BoardVO> articlesList = boardService.searchArticles(searchType, keyword, page, perPageNum, categoryNum);
        

        // ModelAndView 객체 생성 및 데이터 설정
        ModelAndView mav = new ModelAndView(viewName);
        mav.addObject("searchType", searchType);
        mav.addObject("keyword", keyword);
        mav.addObject("articlesList", articlesList);
        mav.addObject("paging", paging);
        mav.addObject("pageNumbers", pageNumbers);
        mav.addObject("blockPageNumbers", blockPageNumbers);

        mav.addObject("category", category);
        
        return mav;
    }
	 
	// 카테고리 번호를 이용하여 게시글 목록 가져오기
	@Override
    @GetMapping("/board/listArticles.do")
    public String listArticles(@RequestParam("categoryNum") int categoryNum, Model model) {
        List<BoardVO> articlesList = boardService.getArticlesByCategory(categoryNum);
        CategoryVO category = getCategoryByCategoryNum(categoryNum);

        model.addAttribute("category", category);
        model.addAttribute("articlesList", articlesList);

        return "/board/listArticles"; 
    }

    private CategoryVO getCategoryByCategoryNum(int categoryNum) {
        return categoryService.getCategoryByCategoryNum(categoryNum);
    }

    // 게시글 등록 페이지
    @GetMapping("/board/articleForm.do")
    public String showArticleForm(Model model, Principal principal,HttpServletRequest request) {
//    	if (principal == null) {
//    		// 로그인하지 않은 경우 로그인 페이지로 이동
//           
//            return "redirect:/user/login.do";
//        }
    	
    	model.addAttribute("board", new BoardVO());
        return "board/articleForm"; 
    }

    // 게시글 수정 페이지
    @GetMapping("/board/articleEditForm.do")
    public ModelAndView showArticleEditForm(@RequestParam("post_num") int postNum, Principal principal, HttpServletRequest request) {
    	String viewName = (String) request.getAttribute("viewName");
		ModelAndView mav = new ModelAndView();
		BoardVO board = boardService.getBoardByPostNum(postNum);
		mav.addObject("board", board);
        return mav;
    }

    @PostMapping("/board/articleForm.do")
    public String endArticle(@ModelAttribute("boardVO") BoardVO boardVO,@ModelAttribute("categoryVO") CategoryVO categoryVO, @RequestParam("categoryNum") int categoryNum, Principal principal, HttpServletRequest request) {
//    	if (principal == null) {
//    		// 로그인하지 않은 경우  로그인 페이지로 이동
//            
//            return "redirect:/user/login.do";
//        }
    	// 선택된 마지막 레벨의 카테고리를 Parent_category_num 필드로 설정
        if (categoryVO.getThirdLevelChildCategoryNum() != 0) {
            categoryVO.setCategory_num(categoryVO.getThirdLevelChildCategoryNum());
        } else if (categoryVO.getSecondLevelChildCategoryNum() != 0) {
            categoryVO.setCategory_num(categoryVO.getSecondLevelChildCategoryNum());
        } else if (categoryVO.getChildCategoryNum() != 0) {
            categoryVO.setCategory_num(categoryVO.getChildCategoryNum());
        } else if (categoryVO.getParentCategoryNum() != 0) {
            categoryVO.setCategory_num(categoryVO.getParentCategoryNum());
        }
    	
    	boardVO.setCategory_num(categoryVO.getCategory_num()); // 카테고리 번호 설정
        boardService.addNewArticle(boardVO);
        return "redirect:/board/articles-list.do?categoryNum=" + boardVO.getCategory_num();
    }
    

    
    // 게시글 보기 페이지
    @GetMapping("/board/viewArticle.do")
    public ModelAndView viewArticle(@RequestParam("post_num") int post_num,
                                    HttpServletRequest request, HttpServletResponse response) throws Exception {
        String viewName = (String) request.getAttribute("viewName");
        boardVO = boardService.viewArticle(post_num);
        
        int userNum = boardVO.getCreated_user_num();
        UserVO poster = userService.getUserByUserNum(userNum);
        
        // 조회수 증가 처리
        boardService.increaseViews(post_num);
        
        ModelAndView mav = new ModelAndView();
        mav.setViewName(viewName);
        mav.addObject("board", boardVO);
        mav.addObject("poster", poster);

        return mav;
    }
    
    // 게시글 수정
    @PostMapping("/edit/{post_num}")
    public String editBoard(@PathVariable int post_num, @ModelAttribute BoardVO updatedBoard) {
    	boardService.updateBoard(updatedBoard);
        return "redirect:/board/viewArticle.do?post_num=" + post_num; // 수정된 게시글로 리다이렉트
    }
    
    // 게시글 삭제
    @PostMapping("/delete/{post_num}")
    public String deleteBoard(@PathVariable int post_num) {
        boardService.deleteBoard(post_num);
        return "redirect:/board/articles-list.do?categoryNum=" + boardVO.getCategory_num();
    }
    
    // 추천 비추천 중복체크 및 반영 로직
    @PostMapping("/vote")
    @ResponseBody
    public ResponseEntity<String> voteForPost(@RequestParam("user_num") int user_num,
                                              @RequestParam("post_num") int post_num,
                                              @RequestParam("vote_type") String vote_type) {
        VoteVO voteVO = new VoteVO();
        voteVO.setUser_num(user_num);
        voteVO.setPost_num(post_num);
        voteVO.setVote_type(vote_type);

        int existingVoteCount = boardService.checkVoteDuplicate(voteVO);

        if (existingVoteCount == 0) {
            boardService.voteForPost(voteVO);

            // 투표 유형에 따라 게시물의 추천 또는 비추천 수 증가
            if ("good".equals(vote_type)) {
                boardService.increaseGoodCount(post_num);
            } else if ("bad".equals(vote_type)) {
                boardService.increaseBadCount(post_num);
            }

            return ResponseEntity.ok("투표가 반영되었습니다.");
        } else {
            return ResponseEntity.badRequest().body("이미 투표한 게시물입니다.");
        }
    }

}
	

