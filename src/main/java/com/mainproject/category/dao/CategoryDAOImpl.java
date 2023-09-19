package com.mainproject.category.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mainproject.category.vo.CategoryVO;
import com.mainproject.paging.PagingVO;

@Repository("categoryDAO")
public class CategoryDAOImpl implements CategoryDAO {

    @Autowired
    private SqlSession sqlSession;

    @Override
    // 페이징 정보를 기반으로 카테고리 목록 조회
    public List<CategoryVO> selectCategoriesWithPaging(PagingVO paging) throws Exception {
        // mapper.xml에서 지정한 SQL 쿼리를 실행하여 페이징 정보를 기반으로 카테고리 목록을 조회합니다.
        return sqlSession.selectList("mapper.category.selectCategoriesWithPaging", paging);
    }

    @Override
    // 검색 조건에 따라 카테고리 목록 검색
    public List<CategoryVO> searchCategories(String searchType, String keyword, int startRow, int perPageNum) throws Exception {
        // 검색 조건과 키워드를 매개변수로 받아와서 파라미터 맵에 담습니다.
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("searchType", searchType);
        parameters.put("keyword", keyword);
        parameters.put("startRow", startRow);
        parameters.put("perPageNum", perPageNum);

        // mapper.xml에서 지정한 SQL 쿼리를 실행하여 검색 조건에 맞는 카테고리 목록을 검색합니다.
        return sqlSession.selectList("mapper.category.searchCategories", parameters);
    }

    @Override
    // 전체 카테고리 수 조회
    public int getTotalCount() throws Exception {
        // mapper.xml에서 지정한 SQL 쿼리를 실행하여 전체 카테고리 수를 조회합니다.
        return sqlSession.selectOne("mapper.category.getTotalCount");
    }
    
	@Override
	// 검색 카테고리 수 조회
    public int getSelectTotalCount(String searchType, String keyword) throws Exception {
        // 검색 조건과 키워드를 매개변수로 받아와서 파라미터 맵에 담습니다.
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("searchType", searchType);
        parameters.put("keyword", keyword);

        // mapper.xml에서 지정한 SQL 쿼리를 실행하여 검색 조건에 맞는 카테고리 수를 조회합니다.
        return sqlSession.selectOne("mapper.category.getSelectTotalCount", parameters);
    }
	
	@Override
	// 카테고리 등록 메서드
	public void insertCategory(CategoryVO categoryVO) throws Exception {
		// 매개변수로 전달된 categoryVO 객체를 이용하여 쿼리를 실행합니다.
	    if (categoryVO.getName() != null && !categoryVO.getName().isEmpty()) {
	        sqlSession.insert("mapper.category.insertCategory", categoryVO);
	    } else {
	        System.out.println("입력된 categoryVO 객체의 name 속성이 null이거나 빈 문자열입니다.");
	    }
	}
	
	@Override
	// 부모 카테고리 목록 조회 메서드
	public List<CategoryVO> getParentCategories() throws Exception {
	    // 부모 카테고리 목록을 조회하는 SQL 쿼리 실행
	    return sqlSession.selectList("mapper.category.getParentCategories");
	}

	@Override
	// 부모 카테고리에 해당하는 자식 카테고리 목록 조회 메서드
	public List<CategoryVO> getChildCategories(int parentCategoryNum) throws Exception {
	    // 매개변수로 전달된 parentCategoryNum을 이용하여 부모 카테고리에 해당하는 자식 카테고리 목록을 조회합니다.
	    return sqlSession.selectList("mapper.category.getChildCategories", parentCategoryNum);
	}

	@Override
	// 카테고리번호에 해당하는 카테고리 정보 조회 메서드
	public CategoryVO selectCategoryByNum(int categoryNum) {
	    // 매개변수로 전달된 categoryNum을 이용하여 해당하는 카테고리 정보를 조회합니다.
	    return sqlSession.selectOne("mapper.category.selectCategoryByNum", categoryNum);
	}
	
	@Override
	// 카테고리 수정 메서드
	public void updateCategory(CategoryVO categoryVO) throws Exception {
		// 매개변수로 전달된 categoryVO 객체를 이용하여 쿼리를 실행합니다.
	    if (categoryVO.getName() != null && !categoryVO.getName().isEmpty()) {
	        sqlSession.update("mapper.category.updateCategory", categoryVO);
	    } else {
	        System.out.println("입력된 categoryVO 객체의 name 속성이 null이거나 빈 문자열입니다.");
	    }
	}

	@Override
	public void deleteCategory(CategoryVO categoryVO) throws Exception {
		// 매개변수로 전달된 categoryVO 객체를 이용하여 쿼리를 실행합니다.
		sqlSession.update("mapper.category.deleteCategory", categoryVO);
	}
	
	//board리스트에 필요한 부분
	@Override
    public CategoryVO getCategoryByCategoryNum(int categoryNum) {
        return sqlSession.selectOne("mapper.category.selectCategoryByCategoryNum", categoryNum);
    }
}
