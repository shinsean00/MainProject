package com.mainproject.headerSearch.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.mainproject.headerSearch.controller.HeaderSearchController;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mainproject.board.vo.BoardVO;
import com.mainproject.headerSearch.service.HeaderSearchService;

@Controller("headerSearchController")
public class HeaderSearchControllerImpl implements HeaderSearchController {

	@Autowired
	private HeaderSearchService headerSearchService;
	
	@Override // 통합 검색 로직
	@GetMapping("/api/search-results")
	public ResponseEntity<Map<String, Object>> search(@RequestParam("searchTerm") String searchTerm) throws Exception {
		// 검색결과 반환 로직
		List<Integer> results = headerSearchService.getSearchResults(searchTerm);
		Map<String, Object> response = new HashMap<>();
		response.put("results", results); // 검색한 게시글 리스트
		response.put("searchTerm", searchTerm);
		return ResponseEntity.ok(response);
	}
	 
	@Override // 검색 결과 페이지 이동
	@GetMapping("/user/search-results.do")
	public ModelAndView viewSearchResult(HttpServletRequest request, @RequestParam("results") String resultsJson, @RequestParam("searchTerm") String searchTerm) throws Exception {
		ModelAndView mav = new ModelAndView();
	    String viewName = (String) request.getAttribute("viewName");
	    // GPT 답변 로직
	    String gptAnswer = headerSearchService.getGptResponse(searchTerm);
	    // GPT가 선별한 키워드 포함하는 게시글 번호 가져오는 로직
	    List<Integer> postNumbers = Arrays.asList(new ObjectMapper().readValue(resultsJson, Integer[].class));
	    // 검색한 게시글 번호로 게시글 검색 로직
	    List<BoardVO> searchResults = new ArrayList<>();
	    boolean isResult = true;
	    if (postNumbers.contains(-1)) { // 검색 결과가 없다면
	    	BoardVO noResult = new BoardVO();
	    	noResult.setPost_num(-1);
	    	noResult.setContent("- 단어의 철자가 정확한지 확인해 보세요.\n- 검색어의 단어 수를 줄이거나, 보다 일반적인 검색어로 다시 검색해 보세요.\n- 두 단어 이상의 검색어인 경우, 띄어쓰기를 확인해보세요.");
	    	searchResults.add(noResult);
	    	isResult = false;
	    } else {
	    	searchResults = headerSearchService.getBoardResults(postNumbers);
	    }
	    mav.setViewName(viewName);
	    mav.addObject("gptAnswer", gptAnswer); // GPT 답변
	    mav.addObject("searchResults", searchResults); // 해당하는 게시글 데이터
	    mav.addObject("searchTerm", searchTerm); // 검색어
	    mav.addObject("isResult", isResult);
	    return mav;
	}
	
	// 검색 결과 CSS 페이지 테스트용 
	@RequestMapping(value = {"/user/search-results-csstest.do"}, method = RequestMethod.GET)
	public ModelAndView viewJoin(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName = (String) request.getAttribute("viewName");
		ModelAndView mav = new ModelAndView();
		mav.setViewName(viewName);
		return mav;
	}
}
