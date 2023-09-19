package com.mainproject.user.service;

import java.security.Principal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.mainproject.user.dao.UserDAO;
import com.mainproject.user.vo.UserRank;
import com.mainproject.user.vo.UserVO;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDAO userDAO;
	
//	@Autowired
//	private BCryptPasswordEncoder passwordEncoder;
	
	private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	@Override // 로그인 실패 체크 로직
	public void increaseLoginFailCount(String username) throws DataAccessException {
		userDAO.increaseLoginFailCount(username);
	}
	
	@Override // 로그인 성공 시 fail_count 초기화 로직
	public void resetLoginFailCount(String username) throws DataAccessException {
		userDAO.resetLoginFailCount(username);
	}
	
	
	@Override // 사용자 등록 입력
	public void registerUser(UserVO userVO) throws DataAccessException {
		String encodedPassword = passwordEncoder.encode(userVO.getPwd()); // 鍮꾨�踰덊샇 �븫�샇�솕
		userVO.setPwd(encodedPassword);
		userDAO.registerUser(userVO);
	}
	
	@Override // 사용자 가입 아이디 중복 확인 입력
	public boolean isIdDuplicate(String value) {
		return userDAO.isIdDuplicate(value);
	}
	
	@Override // 사용자 가입 정보 입력 중복 확인
	public boolean isNicknameDuplicate(String value) {
		return userDAO.isNicknameDuplicate(value);
	}
	
	@Override // 회원 ID로 회원정보 조회 로직
	public UserVO getUserByUsername(String username) throws DataAccessException {
		return userDAO.getUserByUsername(username);
	}
	
	@Override // 회원 정보 수정 로직
	public void updateUser(UserVO userVO) throws DataAccessException {
		// 비밀번호 변경점으로 분기 처리
		if (userVO.getPwd() != null && !userVO.getPwd().isEmpty()) { // 변경했다면
	        String encodedPassword = passwordEncoder.encode(userVO.getPwd());
	        userVO.setPwd(encodedPassword);
	        userDAO.updateUserWithPassword(userVO);
	    } else { // 변경하지 않았다면
	        userDAO.updateUserWithoutPassword(userVO);
	    }
	}
	
	@Override // 회원 탈퇴 로직
	public void deleteUser(UserVO userVO) throws DataAccessException {
		userDAO.deleteUser(userVO);
	}
	
	// 관리자 영역
	@Override // 모든 회원 정보 가져오는 로직
	public List<UserVO> getAllUsers() throws DataAccessException {
		return userDAO.getAllUsers();
	}

	@Override // 모든 회원 정보 가져오는 로직
	public List<UserVO> getAllAdmins() throws DataAccessException {
		return userDAO.getAllAdmins();
	}
	
	@Override // 회원 번호로 유저 정보 가져오는 로직
	public UserVO getUserByUserNum(int user_num) throws DataAccessException {
		return userDAO.getUserByUserNum(user_num);
	}
	
	@Override // 회원 상세 정보수정 로직
	public void updateUserDetail(UserVO userVO, int curUserNum) throws DataAccessException {
	    // 회원 등급에 따른 계정 권한
		if (UserRank.PRIVACY_ADMIN.equals(userVO.getRank())) {
	        userVO.setIs_admin("PRIVACY_ADMIN");
	    } else if (UserRank.ADMIN.equals(userVO.getRank())) {
	        userVO.setIs_admin("ADMIN");
	    } else {
	        userVO.setIs_admin("USER");
	    }
		
		// 성별 입력
	    if ("남성".equals(userVO.getGender())) {
	        userVO.setGender("M");
	    } else if ("여성".equals(userVO.getGender())) {
	        userVO.setGender("F");
	    }

	    // 수정자 입력
	    userVO.setUpdated_user_num(curUserNum);
	    
	    // 수정한 시간 입력
	    Timestamp current = new Timestamp(System.currentTimeMillis());
	    userVO.setUpdated_at(current);
	    
	    // 탈퇴한 시간 입력
	    if (userVO.isIs_deleted() == false) {
	    	userVO.setDeleted_at(null);
	    }
	    
	    userDAO.updateUserDetail(userVO);
	}

	@Override // 회원 정지 로직
    public String suspendUser(int userNum, String action,int suspend_user_num, String suspended_reason, int suspension_duration) throws Exception {
        UserVO user = userDAO.getUserByUserNum(userNum);
		
		if (user == null) {
            throw new Exception("User not found with userNum: " + userNum);
        }

		int user_num = user.getUser_num();
		Timestamp suspended_at = new Timestamp(new Date().getTime());

		Map<String, Object> param = new HashMap<>();

		if (action.equals("suspend")) {
			param.put("user_num", user_num);
			param.put("suspend_user_num", suspend_user_num);
			param.put("suspension_duration",suspension_duration);
			param.put("suspended_reason", suspended_reason);
			param.put("suspended_at", suspended_at);
			userDAO.suspendUser(param);
			return "suspend";
		} else if (action.equals("unsuspend")) {
			userDAO.unsuspendUser(user_num);
			return "unsuspend";
		} else {
            throw new Exception("Unknown action: " + action);
        }
    }

	@Override // 회원 정지 해제 로직
    public String unsuspendUser(int userNum, String action) throws Exception {
        UserVO user = userDAO.getUserByUserNum(userNum);
		
		if (user == null) {
            throw new Exception("User not found with userNum: " + userNum);
        }

		int user_num = user.getUser_num();
		if (action.equals("unsuspend")) {
			userDAO.unsuspendUser(user_num);
			return "unsuspend";
		} else {
            throw new Exception("Unknown action: " + action);
        }
    }

	@Override // 회원 정보 삭제 로직
	public void removeUser(int user_num) throws Exception {
		userDAO.removeUser(user_num);
	}

	@Override // 모든 계정 정보 가져오는 로직
	public List<UserVO> getAllAccounts() throws DataAccessException {
		return userDAO.getAllAccounts();
	}

	@Override // 회원 등급 승격 로직
	public void rankUp(int user_num, String rank) throws DataAccessException {
		String nextRank;
		switch (rank) {
			case "EGG": 
				nextRank = "HATCHING_CHICK";
				break;
			case "HATCHING_CHICK":
				nextRank = "CHICK";
				break;
			case "CHICK":
				nextRank = "CHICKEN";
				break;
			case "CHICKEN":
				nextRank = "FRIED_CHICKEN";
				break;
			case "ADMIN":
				nextRank = "PRIVACY_ADMIN";
				break;
			default: 
				throw new IllegalArgumentException("Invalid rank: " + rank);
		}
		userDAO.rankUp(user_num, nextRank);
	}

	@Override // 회원 권한 전환 로직
	public void switchRank(int user_num, String newRole, String newRank) throws DataAccessException {
		userDAO.switchRank(user_num, newRole, newRank);
	}

	@Override // 신규 회원 관리 페이지 이동
	public List<UserVO> getNewUsers() throws DataAccessException {
		return userDAO.getNewUsers();
	}

	@Override // 탈퇴 회원 관리 페이지 이동 (이 달의 탈퇴 회원)
	public List<UserVO> getWithdrawnUsersThisMonth() throws DataAccessException {
		return userDAO.getWithdrawnUsersThisMonth();
	}

	@Override // 탈퇴 회원 전체 리스트 
	public List<UserVO> getWithdrawnUsers() throws DataAccessException {
		return userDAO.getWithdrawnUsers();
	}

	@Override // 정지 회원 전체 리스트 
	public List<UserVO> getSuspendUsers() throws DataAccessException {
		return userDAO.getSuspendUsers();
	}

	@Override // 이 달의 정지 회원
	public List<UserVO> getSuspendedUsersThisMonth() throws DataAccessException {
		return userDAO.getSuspendedUsersThisMonth();
	}
}
