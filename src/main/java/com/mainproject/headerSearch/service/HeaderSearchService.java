package com.mainproject.headerSearch.service;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.mainproject.board.vo.BoardVO;

public interface HeaderSearchService {
	public List<Integer> getSearchResults(String searchTerm) throws DataAccessException;
	public String getGptResponse(String searchTerm) throws DataAccessException;
	public List<BoardVO> getBoardResults(List<Integer> postNumbers) throws DataAccessException;
}
