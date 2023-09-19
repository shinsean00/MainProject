package com.mainproject.user.dao;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.mainproject.board.vo.BoardVO;
import com.mainproject.user.vo.UserVO;

public interface UserDAO {
	// 사용자 영역
	public int registerUser(UserVO userVO) throws DataAccessException;
	public boolean isIdDuplicate(String value) throws DataAccessException;
	public boolean isNicknameDuplicate(String value) throws DataAccessException;
	public UserVO getUserByUsername(String username) throws DataAccessException;
	public void updateUserWithPassword(UserVO userVO) throws DataAccessException;
	public void updateUserWithoutPassword(UserVO userVO) throws DataAccessException;
	public void updateUserProfile(Map<String, Object> params) throws DataAccessException;
	public void deleteUser(UserVO userVO) throws DataAccessException;
	public void increaseLoginFailCount(String username);
	public void resetLoginFailCount(String username);
	// 관리자 영역
	public List<UserVO> getAllUsers() throws DataAccessException;
	public List<UserVO> getAllAdmins() throws DataAccessException;
	public UserVO getUserByUserNum(int user_num) throws DataAccessException;
	public void updateUserDetail(UserVO userVO) throws DataAccessException;
	public void suspendUser(Map<String,Object> param) throws DataAccessException;
    public void unsuspendUser(int userNum) throws DataAccessException;
	public void removeUser(int user_num) throws DataAccessException;
	public List<UserVO> getAllAccounts() throws DataAccessException;
	public void rankUp(int user_num, String nextRank) throws DataAccessException;
	public void switchRank(int user_num, String newRole, String newRank) throws DataAccessException;
	public List<UserVO> getNewUsers() throws DataAccessException;
	public List<UserVO> getWithdrawnUsersThisMonth() throws DataAccessException;
	public List<UserVO> getWithdrawnUsers() throws DataAccessException;
	public List<UserVO> getSuspendUsers() throws DataAccessException;
	public List<UserVO> getSuspendedUsersThisMonth() throws DataAccessException;
}
 