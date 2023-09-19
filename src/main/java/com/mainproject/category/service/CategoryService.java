package com.mainproject.category.service;

import java.util.List;

import com.mainproject.category.vo.CategoryVO;

public interface CategoryService {
    // 모든 카테고리 목록 조회
	public List<CategoryVO> listCategories() throws Exception;

    // 페이징 정보를 기반으로 특정 페이지의 카테고리 목록 조회
	public List<CategoryVO> getCategoriesWithPaging(int page, int perPageNum) throws Exception;

    // 전체 카테고리 수 조회
	public int getTotalCount() throws Exception;

    // 검색 조건과 키워드를 이용하여 카테고리 검색
	public List<CategoryVO> searchCategories(String searchType, String keyword, int page, int perPageNum) throws Exception;

    // 검색 카테고리 수 조회
	public int getSelectTotalCount(String searchType, String keyword) throws Exception;
	
	// 카테고리 등록
    void addCategory(CategoryVO categoryVO) throws Exception;
    
    // 부모 카테고리 목록 조회
    public List<CategoryVO> getParentCategories() throws Exception;
    
    // 부모 카테고리에 해당하는 자식 카테고리 목록 조회
    public List<CategoryVO> getChildCategories(int parentCategoryNum) throws Exception;
    
    // 카테고리 정보 조회
	public CategoryVO selectCategoryByNum(int categoryNum) throws Exception;
    
	// 카테고리 수정
	public void editCategory(CategoryVO categoryVO) throws Exception;

    // 카테고리 삭제
	public void deleteCategory(CategoryVO categoryVO) throws Exception;

	//board리스트에 필요한 부분
	public CategoryVO getCategoryByCategoryNum(int categoryNum);
}
