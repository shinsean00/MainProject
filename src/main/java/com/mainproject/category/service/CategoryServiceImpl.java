package com.mainproject.category.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mainproject.category.dao.CategoryDAO;
import com.mainproject.category.vo.CategoryVO;
import com.mainproject.paging.Criteria;
import com.mainproject.paging.PagingVO;

@Service("categoryService")
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryDAO categoryDAO;

    @Override
    // 모든 카테고리 목록 조회
    public List<CategoryVO> listCategories() throws Exception {
        // 모든 카테고리 목록을 조회하기 위해 페이징 정보를 가진 PagingVO 객체를 생성하고 DAO에 전달합니다.
        return categoryDAO.selectCategoriesWithPaging(new PagingVO());
    }

    @Override
    // 페이징 정보를 기반으로 특정 페이지의 카테고리 목록 조회
    public List<CategoryVO> getCategoriesWithPaging(int page, int perPageNum) throws Exception {
        // 요청한 페이지와 한 페이지당 보여줄 개수를 기반으로 페이징 정보를 설정하여 DAO에 전달합니다.
        PagingVO paging = new PagingVO();
        paging.setCri(new Criteria(page, perPageNum));
        return categoryDAO.selectCategoriesWithPaging(paging);
    }

    @Override
    // 전체 카테고리 수 조회
    public int getTotalCount() throws Exception {
        // 전체 카테고리 수를 조회하여 반환합니다.
        return categoryDAO.getTotalCount();
    }

    @Override
    // 검색 조건과 키워드를 이용하여 카테고리 검색
    public List<CategoryVO> searchCategories(String searchType, String keyword, int page, int perPageNum) throws Exception {
        // 검색 결과를 페이징 처리하기 위해 시작 행을 계산합니다.
        int startRow = (page - 1) * perPageNum;

        // 검색 쿼리를 실행하여 결과를 가져옴
        List<CategoryVO> searchResult = categoryDAO.searchCategories(searchType, keyword, startRow, perPageNum);
        return searchResult;
    }

	@Override
	// 검색 카테고리 수 조회
	public int getSelectTotalCount(String searchType, String keyword) throws Exception {
		// 전체 카테고리 수를 조회하여 반환합니다.
        return categoryDAO.getSelectTotalCount(searchType, keyword);
	}
	
	@Override
	// 카테고리 등록
	public void addCategory(CategoryVO categoryVO) throws Exception {
	    // 카테고리를 추가하는 DAO 메서드를 호출하여 새로운 카테고리를 등록합니다.
	    categoryDAO.insertCategory(categoryVO);
	}

	@Override
	// 부모 카테고리 목록 조회
	public List<CategoryVO> getParentCategories() throws Exception {
	    // 부모 카테고리 목록을 조회하는 DAO 메서드를 호출하여 가져옵니다.
	    return categoryDAO.getParentCategories();
	}
	
	@Override
	// 부모 카테고리에 해당하는 자식 카테고리 목록 조회
	public List<CategoryVO> getChildCategories(int parentCategoryNum) throws Exception {
	    // 부모 카테고리에 해당하는 자식 카테고리 목록을 조회하는 DAO 메서드를 호출하여 가져옵니다.
	    return categoryDAO.getChildCategories(parentCategoryNum);
	}

	@Override
	// 카테고리 정보 조회
	public CategoryVO selectCategoryByNum(int categoryNum) throws Exception {
		// 카테고리에 번호에 해당하는 카테고리 정보를 조회하는 DAO 메서드를 호출하여 가져옵니다.
		return categoryDAO.selectCategoryByNum(categoryNum);
	}
	
	@Override
	// 카테고리 수정
	public void editCategory(CategoryVO categoryVO) throws Exception {
	    // 카테고리를 수정하는 DAO 메서드를 호출하여 카테고리를 수정합니다.
	    categoryDAO.updateCategory(categoryVO);
	}

	@Override
	public void deleteCategory(CategoryVO categoryVO) throws Exception {
		// 카테고리를 삭제하는 DAO 메서드를 호출하여 카테고리를 수정합니다.
	    categoryDAO.deleteCategory(categoryVO);
	}

	//board리스트에 필요한 부분
	@Override
	public CategoryVO getCategoryByCategoryNum(int categoryNum) {
		return categoryDAO.getCategoryByCategoryNum(categoryNum);
	}
}
