package com.mainproject.category.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.mainproject.category.vo.CategoryVO;

public interface CategoryController {

    // 카테고리 목록을 조회하는 메서드
    // 페이지 번호와 페이지당 아이템 수를 받아와서 처리합니다.
    // ModelAndView를 반환하여 모델과 뷰를 설정합니다.
    public ModelAndView listCategories(HttpServletRequest request, HttpServletResponse response, int page, int perPageNum, boolean isSearch, Integer newPerPageNum) throws Exception;

    // 카테고리를 검색하는 메서드
    // 검색 유형, 검색어, 페이지 번호와 페이지당 아이템 수를 받아와서 처리합니다.
    // ModelAndView를 반환하여 모델과 뷰를 설정합니다.
    public ModelAndView searchCategories(String searchType, String keyword, int page, int perPageNum, boolean isSearch, Integer newPerPageNum) throws Exception;
    
    // 카테고리 등록하는 폼을 호출하는 메서드
    public ModelAndView addCategoryForm() throws Exception;
    
    // 하위 카테고리 목록 조회하는 메서드
    public List<CategoryVO> getChildCategories(int parentCategoryNum) throws Exception;
    
    // 등록 기능 수행하는 메서드
    public ModelAndView doAddCategory(CategoryVO categoryVO) throws Exception;

	// 카테고리 정보를 보는 메소드
    public ModelAndView viewCategory(int categoryNum) throws Exception;
    
    // 카테고리 정보 수정하는 폼을 호출하는 메소드
    public ModelAndView editCategoryForm(int categoryNum) throws Exception;
    
    // 수정 기능 수행하는 메서드
    public ModelAndView doEditCategory(CategoryVO categoryVO) throws Exception;
    
    // 카테고리 정보를 보는 메소드
    public ModelAndView deleteCategory(CategoryVO categoryVO) throws Exception;
}
