package com.mainproject.paging;

public class Criteria {
    private int page; // 현재 페이지 번호
    private int perPageNum; // 페이지당 아이템 수

    public Criteria() {
        this.page = 1; // 기본값으로 1페이지를 설정
        this.perPageNum = 10; // 기본값으로 페이지당 10개의 아이템을 설정
    }

    // 현재 페이지 번호와 페이지당 아이템 수를 인자로 받아 초기화하는 생성자
    public Criteria(int page, int perPageNum) {
        this.page = page;
        this.perPageNum = perPageNum;
    }

    // 현재 페이지 번호 반환
    public int getPage() {
        return page;
    }


    public void setPage(int page) {
        // 페이지 번호가 1보다 작거나 같으면 1로 설정, 그 외의 경우에는 주어진 값 그대로 설정
        if (page <= 0) {
            this.page = 1;
        } else {
            this.page = page;
        }
    }

    // 페이지당 아이템 수 반환
    public int getPerPageNum() {
        return perPageNum;
    }

    public void setPerPageNum(int perPageNum) {
        // 페이지당 아이템 수가 1보다 작거나 같으면 1로 설정, 그 외의 경우에는 주어진 값 그대로 설정
        if (perPageNum <= 0) {
            this.perPageNum = 1;
        } else {
            this.perPageNum = perPageNum;
        }
    }

    // 페이지당 아이템 수를 기반으로 시작 행을 계산하여 반환
    public int getStartRow() {
        return (page - 1) * perPageNum;
    }

    public void setStartRow(int startRow) {
		// TODO Auto-generated method stub
		
	} 
}
