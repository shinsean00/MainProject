package com.mainproject.category.controller;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mainproject.category.service.CategoryService;
import com.mainproject.category.vo.CategoryVO;
import com.mainproject.paging.PagingUtils;
import com.mainproject.paging.PagingVO;
import com.mainproject.searching.SearchingUtils;

@Controller("categoryController")
public class CategoryControllerImpl implements CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryControllerImpl(CategoryService categoryService, SearchingUtils searchingUtils) {
        this.categoryService = categoryService;
    }
    
    @Autowired
    private HttpServletRequest request;

    // 카테고리 리스트
    @Override
    @RequestMapping(value = "/category/categories-list.do", method = RequestMethod.GET)
    public ModelAndView listCategories(
            HttpServletRequest request, HttpServletResponse response,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int perPageNum,
            @RequestParam(defaultValue = "false") boolean isSearch,
            @RequestParam(required = false) Integer newPerPageNum) throws Exception {

        String viewName = (String) request.getAttribute("viewName");
        List<CategoryVO> categoriesList;
        
        int totalCount;
        PagingVO paging;

        if (newPerPageNum != null) {
            // 페이지당 아이템 수가 변경된 경우의 로직
        	// System.out.println(newPerPageNum+"=====================");
            categoriesList = categoryService.getCategoriesWithPaging(page, newPerPageNum);
            totalCount = categoryService.getTotalCount();
            paging = PagingUtils.createPaging(page, newPerPageNum, totalCount, isSearch);
        } else {
            // 기존의 카테고리 목록 조회 로직
            categoriesList = categoryService.getCategoriesWithPaging(page, perPageNum);
            totalCount = categoryService.getTotalCount();
            paging = PagingUtils.createPaging(page, perPageNum, totalCount, isSearch);
        }
        
        // 페이지 계산 및 페이징 정보 설정
        List<Integer> pageNumbers = PagingUtils.calculatePageNumbers(paging.getCurrentBlock(), paging.getPageCount(), paging.getTotalPage());
        List<Integer> blockPageNumbers = PagingUtils.calculateBlockPageNumbers(paging.getCurrentBlock(), paging.getPageCount(), paging.getTotalPage());
        
        // ModelAndView 객체 생성 및 데이터 설정
        ModelAndView mav = new ModelAndView(viewName);
        mav.addObject("categoriesList", categoriesList);
        mav.addObject("paging", paging);
        mav.addObject("pageNumbers", pageNumbers);
        mav.addObject("blockPageNumbers", blockPageNumbers);
        return mav;
    }

    
    // 카테고리 리스트 검색
    @Override
    @RequestMapping(value = "/category/categories-list-search.do", method = RequestMethod.GET)
    public ModelAndView searchCategories(
            @RequestParam("searchType") String searchType,
            @RequestParam("keyword") String keyword,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int perPageNum,
            @RequestParam(defaultValue = "false") boolean isSearch,
            @RequestParam(required = false) Integer newPerPageNum) throws Exception {
       
        String viewName = (String) request.getAttribute("viewName");

        // 새로운 페이지당 아이템 수가 지정된 경우, 해당 값을 사용하도록 설정
        if (newPerPageNum != null) {
            perPageNum = newPerPageNum;
        }

        // 검색 결과에 따른 아이템 총 개수를 조회
        int totalCount = categoryService.getSelectTotalCount(searchType, keyword);

        // 검색 결과에 따른 페이징 정보 생성
        PagingVO paging = PagingUtils.createPaging(page, perPageNum, totalCount, true);

        // 페이지 계산 및 페이징 정보 설정
        List<Integer> pageNumbers = PagingUtils.calculatePageNumbers(paging.getCurrentBlock(), paging.getPageCount(), paging.getTotalPage());
        List<Integer> blockPageNumbers = PagingUtils.calculateBlockPageNumbers(paging.getCurrentBlock(), paging.getPageCount(), paging.getTotalPage());

        // 검색 결과에 따른 카테고리 목록 조회
        List<CategoryVO> categoriesList = categoryService.searchCategories(searchType, keyword, page, perPageNum);

        // ModelAndView 객체 생성 및 데이터 설정
        ModelAndView mav = new ModelAndView(viewName);
        mav.addObject("searchType", searchType);
        mav.addObject("keyword", keyword);
        mav.addObject("categoriesList", categoriesList);
        mav.addObject("paging", paging);
        mav.addObject("pageNumbers", pageNumbers);
        mav.addObject("blockPageNumbers", blockPageNumbers);

        return mav;
    }
    
    // 카테고리 등록하는 폼을 호출하는 메서드
    @Override
    @RequestMapping(value = "/category/add-category-form.do", method = RequestMethod.GET)
    public ModelAndView addCategoryForm() throws Exception {
        
    	String viewName = (String) request.getAttribute("viewName");
    	ModelAndView mav = new ModelAndView(viewName);
        // 부모 카테고리 목록 조회
        List<CategoryVO> parentCategories = categoryService.getParentCategories();
        // 부모 카테고리 목록을 콘솔에 출력
        for (CategoryVO category : parentCategories) {
            // System.out.println("Category Name: " + category.getName());
            // System.out.println("Category Number: " + category.getParent_category_num());
        } 
        
        mav.addObject("categories", parentCategories);
        // System.out.println(mav);

        
        return mav;
    }
    
    // 하위 카테고리 목록 조회
    @Override
    @RequestMapping(value = "/get-child-categories", method = RequestMethod.GET)
    @ResponseBody
    public List<CategoryVO> getChildCategories(@RequestParam("parentCategoryNum") int parentCategoryNum) throws Exception {
        // 부모 카테고리 번호를 기반으로 자식 카테고리 목록을 가져오는 로직을 추가하십시오.
        List<CategoryVO> childCategories = categoryService.getChildCategories(parentCategoryNum);
        return childCategories;
    }
    
    // 등록 기능 수행
	@Override
    @RequestMapping(value = "/category/do-add-category.do", method = RequestMethod.POST)
    public ModelAndView doAddCategory(@ModelAttribute("categoryVO") CategoryVO categoryVO) throws Exception {
        ModelAndView mav = new ModelAndView("redirect:/category/categories-list.do");
        
        // 현재 시간 생성
	    Date currentTime = new Date();
	    Timestamp created_at = new Timestamp(currentTime.getTime());

	    //System.out.println("타임스탬프 생성" + created_at);
	    categoryVO.setCreated_at(created_at);
	    //System.out.println("타임스탬프 저장 확인" + categoryVO.getCreated_at());
        
	    // 선택된 마지막 레벨의 카테고리를 Parent_category_num 필드로 설정
        if (categoryVO.getThirdLevelChildCategoryNum() != 0) {
            categoryVO.setParent_category_num(categoryVO.getThirdLevelChildCategoryNum());
        } else if (categoryVO.getSecondLevelChildCategoryNum() != 0) {
            categoryVO.setParent_category_num(categoryVO.getSecondLevelChildCategoryNum());
        } else if (categoryVO.getChildCategoryNum() != 0) {
            categoryVO.setParent_category_num(categoryVO.getChildCategoryNum());
        } else if (categoryVO.getParentCategoryNum() != 0) {
            categoryVO.setParent_category_num(categoryVO.getParentCategoryNum());
        }
        
        // 각 필드 값을 출력합니다.
        //System.out.println("Parent Category Num: " + categoryVO.getParent_category_num());
        //System.out.println("Category Name: " + categoryVO.getName());
        //System.out.println("Read Permission: " + categoryVO.getRead_permission());
        //System.out.println("Write Permission: " + categoryVO.getWrite_permission());
        //System.out.println("Created User Num: " + categoryVO.getCreated_user_num());

        categoryService.addCategory(categoryVO);
        return mav;
    }

	// 카테고리 정보 보기
	@Override
	@RequestMapping(value = "/category/view-category-detail.do", method = RequestMethod.GET)
	public ModelAndView viewCategory(@RequestParam("categoryNum") int categoryNum) throws Exception {
		// System.out.println(categoryNum);
		
		String viewName = (String) request.getAttribute("viewName");
    	ModelAndView mav = new ModelAndView(viewName);
    	// System.out.println(mav);
    	
		CategoryVO selectedcategory = categoryService.selectCategoryByNum(categoryNum);
		mav.addObject("selectedcategory", selectedcategory);
		return mav;
	}
	

	// 카테고리 정보 수정 페이지
	@Override
	@RequestMapping(value = "/category/edit-category-form.do", method = RequestMethod.GET)
	public ModelAndView editCategoryForm(@RequestParam("categoryNum") int categoryNum) throws Exception {
		String viewName = (String) request.getAttribute("viewName");
    	ModelAndView mav = new ModelAndView(viewName);
    	// 부모 카테고리 목록 조회
        List<CategoryVO> parentCategories = categoryService.getParentCategories();

        // 부모 카테고리 목록을 콘솔에 출력 (디버깅용)
        for (CategoryVO category : parentCategories) {
            // System.out.println("Category Name: " + category.getName());
            // System.out.println("Category Number: " + category.getParent_category_num());
        }

        // 부모 카테고리 목록을 뷰에 전달
        mav.addObject("categories", parentCategories);
        // System.out.println(mav);
        
        CategoryVO selectedcategory = categoryService.selectCategoryByNum(categoryNum);
		mav.addObject("selectedcategory", selectedcategory);

        return mav;
	}
	
	// 수정 기능 수행
	@Override
	@RequestMapping(value = "/category/do-edit-category.do", method = RequestMethod.POST)
	public ModelAndView doEditCategory(@ModelAttribute("categoryVO") CategoryVO categoryVO) throws Exception {
	    ModelAndView mav = new ModelAndView("redirect:/category/view-category-detail.do");

	    // 선택된 마지막 레벨의 카테고리를 Parent_category_num 필드로 설정
        if (categoryVO.getThirdLevelChildCategoryNum() != 0) {
            categoryVO.setParent_category_num(categoryVO.getThirdLevelChildCategoryNum());
        } else if (categoryVO.getSecondLevelChildCategoryNum() != 0) {
            categoryVO.setParent_category_num(categoryVO.getSecondLevelChildCategoryNum());
        } else if (categoryVO.getChildCategoryNum() != 0) {
            categoryVO.setParent_category_num(categoryVO.getChildCategoryNum());
        } else if (categoryVO.getParentCategoryNum() != 0) {
            categoryVO.setParent_category_num(categoryVO.getParentCategoryNum());
        }
        
        // 각 필드 값을 출력합니다.
        System.out.println("Parent Category Num: " + categoryVO.getParent_category_num());
        System.out.println("Parent Category Num: " + categoryVO.getChildCategoryNum());
        System.out.println("Parent Category Num: " + categoryVO.getSecondLevelChildCategoryNum());
        System.out.println("Parent Category Num: " + categoryVO.getThirdLevelChildCategoryNum());

        //System.out.println("Category Name: " + categoryVO.getName());
        //System.out.println("Read Permission: " + categoryVO.getRead_permission());
        //System.out.println("Write Permission: " + categoryVO.getWrite_permission());
        //System.out.println("Created User Num: " + categoryVO.getCreated_user_num());

	    
	    // 현재 시간 생성
	    Date currentTime = new Date();
	    Timestamp updated_at = new Timestamp(currentTime.getTime());

	    //System.out.println("타임스탬프 생성" + updated_at);
	    categoryVO.setUpdated_at(updated_at);
	    //System.out.println("타임스탬프 저장 확인" + categoryVO.getUpdated_at());
	    
	    categoryService.editCategory(categoryVO);
	    mav.addObject("categoryNum", categoryVO.getCategory_num());
	    
	    return mav;
	}

	// 삭제 기능 수행
	@Override
	@RequestMapping(value = "/category/do-delete-category.do", method = RequestMethod.GET)
	public ModelAndView deleteCategory(CategoryVO categoryVO) throws Exception {
		ModelAndView mav = new ModelAndView("redirect:/category/categories-list.do");
		
		// 현재 시간 생성
	    Date currentTime = new Date();
	    Timestamp deleted_at = new Timestamp(currentTime.getTime());

	    //System.out.println("타임스탬프 생성" + deleted_at);
	    categoryVO.setDeleted_at(deleted_at);
	    //System.out.println("타임스탬프 저장 확인" + categoryVO.getDeleted_at());
	    
	    categoryService.deleteCategory(categoryVO);
		
		return mav;
	}

}
