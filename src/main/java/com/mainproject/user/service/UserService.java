package com.mainproject.user.service;

import java.security.Principal;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.mainproject.user.vo.UserVO;

public interface UserService {
	// 사용자 영역
    public void registerUser(UserVO userVO) throws DataAccessException;
    public boolean isIdDuplicate(String value);
    public boolean isNicknameDuplicate(String value);
    public UserVO getUserByUsername(String username) throws DataAccessException;
    public void updateUser(UserVO userVO) throws DataAccessException;
	public void deleteUser(UserVO userVO) throws DataAccessException;
	public void increaseLoginFailCount(String username) throws DataAccessException;
	public void resetLoginFailCount(String username) throws DataAccessException;
	// 관리자 영역
	public List<UserVO> getAllUsers() throws DataAccessException;
	public List<UserVO> getAllAdmins() throws DataAccessException;
	public UserVO getUserByUserNum(int user_num) throws DataAccessException;
	public void updateUserDetail(UserVO userVO, int curUserNum) throws DataAccessException;
	public String suspendUser(int userNum, String action, int suspend_user_num, String suspended_reason, int suspension_duration) throws Exception;
	public String unsuspendUser(int userNum, String action) throws Exception;
	public void removeUser(int user_num) throws Exception;
	public List<UserVO> getAllAccounts() throws DataAccessException;
	public void rankUp(int user_num, String rank) throws DataAccessException;
	public void switchRank(int user_num, String newRole, String newRank) throws DataAccessException;
	public List<UserVO> getNewUsers() throws DataAccessException;
	public List<UserVO> getWithdrawnUsersThisMonth() throws DataAccessException;
	public List<UserVO> getWithdrawnUsers() throws DataAccessException;
	public List<UserVO> getSuspendUsers() throws DataAccessException;
	public List<UserVO> getSuspendedUsersThisMonth() throws DataAccessException;
}
