package com.mainproject.pet.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.mainproject.pet.dao.PetDAO;
import com.mainproject.pet.vo.PetVO;

@Service
public class PetServiceImpl implements PetService {

	@Autowired
	private PetDAO petDAO;

	@Override
	public void registerPet(PetVO petVO) throws DataAccessException {
		petDAO.registerPet(petVO);
	}

	@Override
	public List<PetVO> getPetByUserNum(int userNum) throws DataAccessException{
		return petDAO.getPetByUserNum(userNum);
	}
	 
	

	@Override
	public PetVO getPetByPetNo(int petNo) throws DataAccessException {
		return petDAO.findByPetNo(petNo);
	}
	
	@Override
	public void updatePet(PetVO petVO) throws DataAccessException {
		petDAO.updatePet(petVO);
	}

	@Override
	public void deletePet(PetVO petVO) throws DataAccessException {
		petDAO.deletePet(petVO);
	}
	
	@Override
	public List<PetVO> getAllPet() throws DataAccessException {
		return petDAO.getAllPet();
	}
	
	@Override
	public List<PetVO> searchPet(String category, String keyword) throws DataAccessException{
		return petDAO.searchPet(category, keyword);
	}

	@Override
	public List<PetVO> getAllPet(int userNum) {
		// TODO Auto-generated method stub
		return null;
	}
}
