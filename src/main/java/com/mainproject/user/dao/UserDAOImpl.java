package com.mainproject.user.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.mainproject.user.vo.UserVO;

@Repository("userDAO")
public class UserDAOImpl implements UserDAO {
	@Autowired
	private SqlSession sqlSession;
	
	@Override // 로그인 실패 체크 로직
	public void increaseLoginFailCount(String username) {
		sqlSession.update("mapper.user.increaseLoginFailCount",username);
	}
	
	@Override // 로그인 성공 시 fail_count 초기화 로직
	public void resetLoginFailCount(String username) {
		sqlSession.update("mapper.user.resetLoginFailCount",username);
	}

	@Override // 회원가입 회원정보 등록 로직

	public int registerUser(UserVO userVO) throws DataAccessException {
		return sqlSession.insert("mapper.user.registerUser", userVO);
	} 
	
	@Override // 사용자 가입 아이디 중복 확인 입력
	public boolean isIdDuplicate(String value) throws DataAccessException {
		int count = sqlSession.selectOne("mapper.user.checkIdDuplicate", value);
		return count > 0;
	}
	
	@Override // 사용자 가입 정보 입력 중복 확인
	public boolean isNicknameDuplicate(String value) throws DataAccessException {
		int count = sqlSession.selectOne("mapper.user.checkNicknameDuplicate", value);
		return count > 0;
	}  
	
	@Override // 특정 username(ID)을 입력한 사용자의 모든 정보 조회
    public UserVO getUserByUsername(String username) throws DataAccessException {
		return sqlSession.selectOne("mapper.user.getUserByUsername", username);
    }
	
	@Override // 회원 정보 수정 로직 (비밀번호 변경)
	public void updateUserWithPassword(UserVO userVO) throws DataAccessException {
		sqlSession.update("mapper.user.updateUserWithPassword",userVO);
	}
	
	@Override// 회원 정보 수정 로직 (비밀번호 미변경)
	public void updateUserWithoutPassword(UserVO userVO) throws DataAccessException {
		sqlSession.update("mapper.user.updateUserWithoutPassword",userVO);
	}
	
	@Override // 회원 프로필 수정 로직
	public void updateUserProfile(Map<String, Object> params) throws DataAccessException {
		sqlSession.update("mapper.user.updateUserProfile", params);
	}
	
	@Override // 회원 탈퇴 로직
	public void deleteUser(UserVO userVO) throws DataAccessException {
		sqlSession.update("mapper.user.deleteUser", userVO);
	}
	
	// 관리자 영역
	@Override // 모든 회원 정보 가져오는 로직
	public List<UserVO> getAllUsers() throws DataAccessException {
		return sqlSession.selectList("mapper.user.getAllUsers");
	}

	@Override // 모든 관리자 정보 가져오는 로직
	public List<UserVO> getAllAdmins() throws DataAccessException {
		return sqlSession.selectList("mapper.user.getAllAdmins");
	}
	
	@Override // 회원 번호로 유저 정보 가져오는 로직
	public UserVO getUserByUserNum(int user_num) throws DataAccessException { 
		return sqlSession.selectOne("mapper.user.getUserByUserNum", user_num);
	}
	
	@Override // 회원 상세 정보수정 로직
	public void updateUserDetail(UserVO userVO) throws DataAccessException {
		sqlSession.update("mapper.user.updateUserDetail", userVO);
	}

	@Override // 회원 정지 로직
    public void suspendUser(Map<String,Object> param) throws DataAccessException {
        sqlSession.update("mapper.user.suspendUser", param);
    }

    @Override // 회원 정지 해제 로직
    public void unsuspendUser(int userNum) throws DataAccessException {
        sqlSession.update("mapper.user.unsuspendUser", userNum);
    }

	@Override // 회원 정보 삭제 로직
	public void removeUser(int user_num) throws DataAccessException {
		sqlSession.delete("mapper.user.removeUser", user_num);
	}


	@Override // 모든 계정 정보 가져오는 로직
	public List<UserVO> getAllAccounts() throws DataAccessException {
		return sqlSession.selectList("mapper.user.getAllAccounts");
	}

	@Override // 회원 등급 승격 로직
	public void rankUp(int user_num, String nextRank) throws DataAccessException {
		Map<String, Object> params = new HashMap<>();
		params.put("user_num", user_num);
		params.put("nextRank", nextRank);

		sqlSession.update("mapper.user.rankUp", params);
	}

	@Override // 회원 권한 전환 로직
	public void switchRank(int user_num, String newRole, String newRank) throws DataAccessException {
		Map<String, Object> params = new HashMap<>();
		params.put("user_num", user_num);
		params.put("newRole", newRole);
		params.put("newRank", newRank);

		sqlSession.update("mapper.user.switchRank", params);
	}

	@Override // 신규 회원 관리 페이지 이동
	public List<UserVO> getNewUsers() throws DataAccessException {
		return sqlSession.selectList("mapper.user.getNewUsers");
	}

	@Override // 이 달의 탈퇴 회원
	public List<UserVO> getWithdrawnUsersThisMonth() throws DataAccessException {
		return sqlSession.selectList("mapper.user.getWithdrawnUsersThisMonth");
	}

	@Override // 탈퇴 회원 전체
	public List<UserVO> getWithdrawnUsers() throws DataAccessException {
		return sqlSession.selectList("mapper.user.getWithdrawnUsers");
	}
	
	@Override // 정지 회원 전체
	public List<UserVO> getSuspendUsers() throws DataAccessException {
		return sqlSession.selectList("mapper.user.getSuspendUsers");
	}

	@Override // 이 달의 정지 회원
	public List<UserVO> getSuspendedUsersThisMonth() throws DataAccessException {
		return sqlSession.selectList("mapper.user.getSuspendedUsersThisMonth");
	}
}

