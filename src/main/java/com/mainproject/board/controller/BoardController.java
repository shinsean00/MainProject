package com.mainproject.board.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

public interface BoardController {

	// 카테고리 목록을 조회하는 메서드
	// 페이지 번호와 페이지당 아이템 수를 받아와서 처리합니다.
	// ModelAndView를 반환하여 모델과 뷰를 설정합니다.
	public ModelAndView listArticles(HttpServletRequest request, HttpServletResponse response, int page, int perPageNum,
			boolean isSearch, Integer newPerPageNum, int categoryNum) throws Exception;

	// 카테고리를 검색하는 메서드
	// 검색 유형, 검색어, 페이지 번호와 페이지당 아이템 수를 받아와서 처리합니다.
	// ModelAndView를 반환하여 모델과 뷰를 설정합니다.
	public ModelAndView searchArticles(String searchType, String keyword, int page, int perPageNum, boolean isSearch,
			Integer newPerPageNum, int categoryNum) throws Exception;

	public String listArticles(int categoryNum, Model model);

	public ModelAndView viewArticle(@RequestParam("post_num") int post_num, HttpServletRequest request,
			HttpServletResponse response) throws Exception;
}
