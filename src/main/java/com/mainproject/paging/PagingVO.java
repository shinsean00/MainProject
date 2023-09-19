package com.mainproject.paging;

public class PagingVO {
	private Criteria cri; // 현재 페이지와 페이지당 아이템 수를 담는 Criteria 객체
    private int perPageNumVal; // 페이지당 아이템 수
    private int totalCount; // 전체 아이템 수
    private int totalPage; // 전체 페이지 수
    private int prev; // 이전 페이지 번호
    private int current; // 현재 페이지 번호
    private int next; // 다음 페이지 번호
    private int startPage; // 시작 페이지 번호
    private int endPage; // 종료 페이지 번호
    private int currentBlock; // 현재 블록 번호
    private int pageCount; // 페이지당 표시될 페이지 수 (ex: 10)
    private int blockStartPage; // 블록의 시작 페이지 번호
    private int blockEndPage; // 블록의 종료 페이지 번호
    private String searchType; // 검색 유형 (필요한 경우에 사용)
    
    private int categoryNum;
    
    
    public void setStartRow(int startRow) {
        this.cri.setStartRow(startRow);
    }

    public void setPerPageNum(int perPageNum) {
        this.cri.setPerPageNum(perPageNum);
    }
    
    public Criteria getCri() {
    	return cri;
	}
    
    public int getPerPageNumVal() {
		return perPageNumVal;
	}

	public void setPerPageNumVal(int perPageNumVal) {
		this.perPageNumVal = perPageNumVal;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getPrev() {
		return prev;
	}

	public void setPrev(int prev) {
		this.prev = prev;
	}
	
	public int getCurrent() {
		return current;
	}

	public void setCurrent(int current) {
		this.current = current;
	}

	public int getNext() {
		return next;
	}

	public void setNext(int next) {
		this.next = next;
	}

	public int getStartPage() {
		return startPage;
	}

	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}

	public int getCurrentBlock() {
		return currentBlock;
	}

	public void setCurrentBlock(int currentBlock) {
		this.currentBlock = currentBlock;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public int getBlockStartPage() {
		return blockStartPage;
	}

	public void setBlockStartPage(int blockStartPage) {
		this.blockStartPage = blockStartPage;
	}

	public int getBlockEndPage() {
		return blockEndPage;
	}

	public void setBlockEndPage(int blockEndPage) {
		this.blockEndPage = blockEndPage;
	}
	
	public String getSearchType() {
        return searchType;
    }

    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }

	public void setCri(Criteria cri) {
		this.cri = cri;
	}

	
	
	public int getCategoryNum() {
		return categoryNum;
	}

	public void setCategoryNum(int categoryNum) {
		this.categoryNum = categoryNum;
	}
	
	
	
}
