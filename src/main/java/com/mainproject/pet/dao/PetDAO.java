package com.mainproject.pet.dao;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.mainproject.pet.vo.PetVO;

public interface PetDAO {
	public List<PetVO> getPetByUserNum(int userNum) throws DataAccessException;
	
	public List<PetVO> getAllPet() throws DataAccessException;

	public int registerPet(PetVO petVO) throws DataAccessException;

	public PetVO findByPetNo(int petNo) throws DataAccessException;
	
	public void updatePet(PetVO petVO) throws DataAccessException;
	
	public void deletePet(PetVO petVO) throws DataAccessException;
	
	public List<PetVO> searchPet(String category, String keyword) throws DataAccessException;

	public void updatePetProfile(Map<String, Object> params) throws DataAccessException;
}
