package com.mainproject.category.vo;

import java.sql.Timestamp;

public class CategoryVO {
    private int category_num;               // 카테고리 번호
    private int parent_category_num;        // 부모 카테고리 번호
    private String name;                    // 카테고리 이름
    private String read_permission;         // 읽기 권한 정보
    private String write_permission;        // 쓰기 권한 정보
    private int created_user_num;           // 생성된 사용자 번호
    private Timestamp created_at;           // 생성일시
    private int updated_user_num;           // 업데이트된 사용자 번호
    private Timestamp updated_at;           // 업데이트 일시
    private boolean is_deleted;             // 삭제 여부
    private int deleted_user_num;           // 삭제된 사용자 번호
    private Timestamp deleted_at;           // 삭제 일시
	
    // 카테고리 셀렉트처리용
    private int parentCategoryNum;
    private int childCategoryNum;
    private int secondLevelChildCategoryNum;
	private int thirdLevelChildCategoryNum;
    
    
	public CategoryVO() {

	}

	public int getCategory_num() {
		return category_num;
	}

	public void setCategory_num(int category_num) {
		this.category_num = category_num;
	}

	public int getParent_category_num() {
		return parent_category_num;
	}

	public void setParent_category_num(int parent_category_num) {
		this.parent_category_num = parent_category_num;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRead_permission() {
		return read_permission;
	}

	public void setRead_permission(String read_permission) {
		this.read_permission = read_permission;
	}

	public String getWrite_permission() {
		return write_permission;
	}

	public void setWrite_permission(String write_permission) {
		this.write_permission = write_permission;
	}

	public int getCreated_user_num() {
		return created_user_num;
	}

	public void setCreated_user_num(int created_user_num) {
		this.created_user_num = created_user_num;
	}

	public Timestamp getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Timestamp created_at) {
		this.created_at = created_at;
	}

	public int getUpdated_user_num() {
		return updated_user_num;
	}

	public void setUpdated_user_num(int updated_user_num) {
		this.updated_user_num = updated_user_num;
	}

	public Timestamp getUpdated_at() {
		return updated_at;
	}

	public void setUpdated_at(Timestamp updated_at) {
		this.updated_at = updated_at;
	}

	public boolean isIs_deleted() {
		return is_deleted;
	}

	public void setIs_deleted(boolean is_deleted) {
		this.is_deleted = is_deleted;
	}

	public int getDeleted_user_num() {
		return deleted_user_num;
	}

	public void setDeleted_user_num(int deleted_user_num) {
		this.deleted_user_num = deleted_user_num;
	}

	public Timestamp getDeleted_at() {
		return deleted_at;
	}

	public void setDeleted_at(Timestamp deleted_at) {
		this.deleted_at = deleted_at;
	}

	public int getParentCategoryNum() {
		return parentCategoryNum;
	}

	public void setParentCategoryNum(int parentCategoryNum) {
		this.parentCategoryNum = parentCategoryNum;
	}

	public int getChildCategoryNum() {
		return childCategoryNum;
	}

	public void setChildCategoryNum(int childCategoryNum) {
		this.childCategoryNum = childCategoryNum;
	}

	public int getSecondLevelChildCategoryNum() {
		return secondLevelChildCategoryNum;
	}

	public void setSecondLevelChildCategoryNum(int secondLevelChildCategoryNum) {
		this.secondLevelChildCategoryNum = secondLevelChildCategoryNum;
	}

	public int getThirdLevelChildCategoryNum() {
		return thirdLevelChildCategoryNum;
	}

	public void setThirdLevelChildCategoryNum(int thirdLevelChildCategoryNum) {
		this.thirdLevelChildCategoryNum = thirdLevelChildCategoryNum;
	}
}
