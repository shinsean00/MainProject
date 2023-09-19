package com.mainproject.headerSearch.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.mainproject.board.vo.BoardVO;

public interface HeaderSearchDAO {
	public List<Integer> getSearchResults(List<String> keywords) throws DataAccessException;
	public List<BoardVO> getBoardResults(List<Integer> postNumbers) throws DataAccessException;
}
