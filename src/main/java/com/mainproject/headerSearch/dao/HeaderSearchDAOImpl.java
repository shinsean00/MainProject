package com.mainproject.headerSearch.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.apache.ibatis.session.SqlSession;

import com.mainproject.board.vo.BoardVO;

@Repository("headerSearchDAO")
public class HeaderSearchDAOImpl implements HeaderSearchDAO {
	
	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public List<Integer> getSearchResults(List<String> keywords) throws DataAccessException {
		return sqlSession.selectList("mapper.board.getSearchResults", keywords);
	}
	
	@Override
	public List<BoardVO> getBoardResults(List<Integer> postNumbers) throws DataAccessException {
		return sqlSession.selectList("mapper.board.getBoardResults", postNumbers);
	}
}
