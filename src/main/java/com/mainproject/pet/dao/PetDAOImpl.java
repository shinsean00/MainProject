package com.mainproject.pet.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.mainproject.pet.vo.PetVO;

@Repository("petDAO")
public class PetDAOImpl implements PetDAO{
	@Autowired
	private SqlSession sqlSession;
	
	@Override 
    public List<PetVO> getPetByUserNum(int userNum) throws DataAccessException {
        return sqlSession.selectList("mapper.pet.getPetsByUserNum",userNum);
    }
	
	@Override
	public int registerPet(PetVO petVO) throws DataAccessException {
		return sqlSession.insert("mapper.pet.registerPet", petVO);
	}

	@Override
	public PetVO findByPetNo(int petNo) throws DataAccessException {
	    return sqlSession.selectOne("mapper.pet.findByPetNo", petNo);
	}
	
	@Override
	public void updatePet(PetVO petVO) throws DataAccessException {
		sqlSession.update("mapper.pet.updatePet", petVO);
	}
	
	@Override // 회원 프로필 수정 로직
	public void updatePetProfile(Map<String, Object> params) throws DataAccessException {
		sqlSession.update("mapper.pet.updatePetProfile", params);
	}
	
	@Override
	public void deletePet(PetVO petVO) throws DataAccessException {
		sqlSession.update("mapper.pet.deletePet", petVO);
	}

	@Override 
    public List<PetVO> getAllPet() throws DataAccessException {
        return sqlSession.selectList("mapper.pet.getAllPet");
	}
	
	@Override
	public List<PetVO> searchPet(String category, String keyword) throws DataAccessException {
		Map<String, Object> params = new HashMap<>();
		params.put("category", category);
		params.put("keyword", keyword);
		return sqlSession.selectList("mapper.pet.searchPet",params);
	}
}
