package com.mainproject.category.dao;

import java.util.List;

import com.mainproject.category.vo.CategoryVO;
import com.mainproject.paging.PagingVO;

public interface CategoryDAO {

    // 페이징 정보를 기반으로 카테고리 목록 조회
    public List<CategoryVO> selectCategoriesWithPaging(PagingVO paging) throws Exception;

    // 검색 조건에 따라 카테고리 목록 검색
    public List<CategoryVO> searchCategories(String searchType, String keyword, int startRow, int perPageNum) throws Exception;

    // 전체 카테고리 수 조회
    public int getTotalCount() throws Exception;
    
    // 검색 카테고리 수 조회
    public int getSelectTotalCount(String searchType, String keyword) throws Exception;
    
    // 카테고리 등록 메서드
    public void insertCategory(CategoryVO categoryVO) throws Exception;

    // 부모 카테고리 목록 조회 메서드
    public List<CategoryVO> getParentCategories() throws Exception;
    
    // 부모 카테고리에 해당하는 자식 카테고리 목록 조회 메서드
    public List<CategoryVO> getChildCategories(int parentCategoryNum) throws Exception;
    
    // 카테고리 정보 조회
	public CategoryVO selectCategoryByNum(int categoryNum) throws Exception;

	// 카테고리 수정 메서드
	public void updateCategory(CategoryVO categoryVO) throws Exception;

	// 카테고리 삭제 메서드
	public void deleteCategory(CategoryVO categoryVO) throws Exception;

	//board리스트에 필요한 부분
	CategoryVO getCategoryByCategoryNum(int categoryNum);

}
