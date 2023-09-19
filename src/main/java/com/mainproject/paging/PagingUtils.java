package com.mainproject.paging;

import java.util.ArrayList;
import java.util.List;

public class PagingUtils {

	public static PagingVO createPaging(int page, int perPageNum, int totalCount, boolean isSearch) {
	    PagingVO paging = new PagingVO();

	    // 현재 페이지와 페이지당 아이템 수를 기반으로 Criteria 객체를 생성하고, PagingVO에 설정합니다.
	    Criteria criteria = new Criteria(page, perPageNum);
	    paging.setCri(criteria);

	    // 페이지당 아이템 수와 전체 아이템 수를 PagingVO에 설정합니다.
	    paging.setPageCount(perPageNum);
	    paging.setTotalCount(totalCount);

	    int totalPage;
	    if (isSearch) {
	        // 검색 모드인 경우 검색 결과에 따른 전체 페이지 수를 계산하여 설정합니다.
	        totalPage = (int) Math.ceil((double) totalCount / perPageNum);
	    } else {
	        // 일반 모드인 경우 기존의 전체 페이지 수를 사용합니다.
	        totalPage = (int) Math.ceil((double) paging.getTotalCount() / perPageNum);
	    }
	    paging.setTotalPage(totalPage);

	    int pageCount = 10; // 여기에 원하는 페이지 수를 설정하세요
	    int currentBlock = (page - 1) / pageCount + 1;
	    int startPage = (currentBlock - 1) * pageCount + 1;
	    int endPage = startPage + pageCount - 1;
	    paging.setCurrentBlock(currentBlock);

	    // 계산된 endPage가 실제 totalPage를 초과하지 않도록 조정합니다.
	    if (endPage > totalPage) {
	        endPage = totalPage;
	    }

	    // 시작 페이지가 더 큰 경우 조정합니다.
	    if (startPage > endPage) {
	        startPage = endPage - pageCount + 1;
	    }

	    // 페이징 정보에 계산된 시작 페이지와 종료 페이지를 설정합니다.
	    paging.setStartPage(startPage);
	    paging.setEndPage(endPage);

	    int prevPage = page - 1;
	    if (prevPage < 1) {
	        prevPage = 1;
	    }

	    int currentPage = page;

	    int nextPage = page + 1;
	    if (nextPage > totalPage) {
	        nextPage = totalPage;
	    }

	    // 페이징 정보에 현재 페이지 번호, 이전 페이지 번호, 다음 페이지 번호, 페이지당 아이템 수를 설정합니다.
	    paging.setPerPageNumVal(perPageNum);
	    paging.setPrev(prevPage);
	    paging.setCurrent(currentPage);
	    paging.setNext(nextPage);

	    return paging;
	}


    // 페이지 번호 목록을 계산하는 메서드
	public static List<Integer> calculatePageNumbers(int currentBlock, int pageCount, int totalPage) {
	    List<Integer> pageNumbers = new ArrayList<>();

	    int startPage = (currentBlock - 1) * pageCount + 1; // 페이지 블록의 시작 페이지 번호
	    int endPage = startPage + pageCount - 1; // 페이지 블록의 끝 페이지 번호

	    // endPage가 totalPage보다 크면 endPage를 totalPage로 조정
	    endPage = Math.min(endPage, totalPage);

	    // startPage가 1보다 작으면 startPage를 1로 조정
	    startPage = Math.max(startPage, 1);

	    // 페이지 번호 목록을 계산하여 리스트에 추가
	    for (int i = startPage; i <= endPage; i++) {
	        pageNumbers.add(i);
	    }

	    return pageNumbers;
	}

	public static List<Integer> calculateBlockPageNumbers(int currentBlock, int pageCount, int totalPage) {
	    List<Integer> blockPageNumbers = new ArrayList<>(); // 이름을 pageNumbers 대신 blockPageNumbers로 변경

	    int startPage = (currentBlock - 1) * pageCount + 1; // 페이지 블록의 시작 페이지 번호
	    int endPage = startPage + pageCount - 1; // 페이지 블록의 끝 페이지 번호

	    // endPage가 totalPage보다 크면 endPage를 totalPage로 조정
	    endPage = Math.min(endPage, totalPage);

	    // startPage가 1보다 작으면 startPage를 1로 조정
	    startPage = Math.max(startPage, 1);

	    // 페이지 번호 목록을 계산하여 리스트에 추가
	    for (int i = startPage; i <= endPage; i++) {
	        blockPageNumbers.add(i); // pageNumbers 대신 blockPageNumbers로 변경
	    }

	    return blockPageNumbers; // 이름을 blockPageNumbers로 변경
	}
}
