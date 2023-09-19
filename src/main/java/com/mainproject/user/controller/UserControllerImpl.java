package com.mainproject.user.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.mainproject.captcha.CaptchaService;
import com.mainproject.user.dao.UserDAO;
import com.mainproject.user.service.UserDetailsServiceImpl;
import com.mainproject.user.service.UserService;
import com.mainproject.user.vo.LoginRequest;
import com.mainproject.user.vo.UserVO;

@Controller("userController")
public class UserControllerImpl implements UserController {
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImpl;
	
//	@Autowired
//	private BCryptPasswordEncoder passwordEncoder;

	private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	
	@Autowired
	UserVO userVO;
	
	@Override // 회원가입 페이지 이동
	@RequestMapping(value = {"/user/join.do"}, method = RequestMethod.GET)
	public ModelAndView viewJoin(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName = (String) request.getAttribute("viewName");
		ModelAndView mav = new ModelAndView();
		mav.setViewName(viewName);
		return mav;
	}
	
	@Override // 회원가입 로직
	@PostMapping("/api/register-user")
	public ResponseEntity<String> registerUser(@RequestBody UserVO userVO) {
	    try {
	        userService.registerUser(userVO);
	        return new ResponseEntity<>("success", HttpStatus.OK);
	    } catch (Exception e) {
	        return new ResponseEntity<>("fail", HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
	
	@Override // 아이디 중복 체크 로직
	@PostMapping("/api/check-id")
	public ResponseEntity<Map<String, Boolean>> checkId(@RequestParam("id") String id) {
		boolean isDuplicate = userService.isIdDuplicate(id);
	    Map<String, Boolean> response = new HashMap<>();
	    response.put("isDuplicate", isDuplicate);
	    return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@Override // 닉네임 중복 체크 로직
	@PostMapping("/api/check-nickname")
	public ResponseEntity<Map<String, Boolean>> checkNickname(@RequestParam("nickname") String nickname) {
		 boolean isDuplicate = userService.isNicknameDuplicate(nickname);
		 Map<String, Boolean> response = new HashMap<>();
		 response.put("isDuplicate", isDuplicate);
		 return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@Override // 회원가입 완료 페이지 이동
	@RequestMapping(value = {"/user/join-complete.do"}, method = RequestMethod.GET)
	public ModelAndView viewJoinComplete(@RequestParam("name") String name, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName = (String) request.getAttribute("viewName");
		ModelAndView mav = new ModelAndView();
		mav.setViewName(viewName);
		mav.addObject("name", name);
		return mav;
	}
	
	@Override // 로그인 페이지 이동
	@RequestMapping(value = {"/user/login.do"}, method = RequestMethod.GET)
	public ModelAndView viewLogin(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName = (String) request.getAttribute("viewName");
		ModelAndView mav = new ModelAndView();
		mav.setViewName(viewName);
		return mav;
	}
	
	@Override // 로그인 로직
	@PostMapping("/api/login")
	public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest, HttpSession session) {
	    // 현재 인증 상태를 가져옴
	    Authentication currentAuthentication = SecurityContextHolder.getContext().getAuthentication();

	    // 이미 로그인된 상태인지 확인
	    if (!(currentAuthentication instanceof AnonymousAuthenticationToken)) {
	        return createResponse("message", "이미 로그인된 상태입니다.", HttpStatus.OK);
	    }

	    String username = loginRequest.getId();
	    String password = loginRequest.getPwd();

	    // 사용자 정보를 DB에서 가져옴
	    UserVO user = userService.getUserByUsername(username);

	    // DB에 해당 사용자가 없으면 오류 반환
	    if (user == null) {
	        return createResponse("message", "로그인 정보가 올바르지 않습니다. 다시 확인해주세요.", HttpStatus.UNAUTHORIZED);
	    }

	    // 사용자의 인증 정보를 가져옴
	    UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(username);

	    // 아이디와 비밀번호가 일치하지 않는 경우
	    if (userDetails == null || !passwordEncoder.matches(password, userDetails.getPassword())) {
	        userService.increaseLoginFailCount(username);
	        
	     // 로그인 5회 이상 실패 처리
	        if (user.getFail_count() >= 5) {
	            Map<String, String> responseMap = new HashMap<>();
	            responseMap.put("message", "로그인을 5회 이상 실패하였습니다.");
	            responseMap.put("captchaRequired", "true");
	            return new ResponseEntity<>(responseMap, HttpStatus.FORBIDDEN);
	        }

	        return createResponse("message", "아이디 또는 비밀번호가 잘못되었습니다.", HttpStatus.UNAUTHORIZED);
	    }
	    
	    // 사용자의 정수 값을 설정
	    user.setUser_num(user.getUser_num());
	    
	    userService.resetLoginFailCount(username);
	    
	    // 탈퇴한 회원인 경우
	    if (user.isIs_deleted()) {
	        return createResponse("message", "이미 탈퇴한 계정입니다. 다른 계정으로 로그인 해주세요.", HttpStatus.FORBIDDEN);
	    }
	    
	    // 인증된 사용자 정보를 세션에 저장
	    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
	    SecurityContext securityContext = SecurityContextHolder.getContext();
	    securityContext.setAuthentication(authentication);
	    session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, securityContext);
	    
	    // 로그인 성공 후 리다이렉트할 URL을 결정
	    String referer = (String) session.getAttribute("previousPage");
	    if (referer == null || referer.isBlank()) {
	        referer = "/index.do";  // 기본 URL 설정
	    }
	    return createResponse("redirectUrl", referer, HttpStatus.OK);
	}
	
	// 로그인 response 메소드
	private ResponseEntity<Map<String, String>> createResponse(String key, String value, HttpStatus status) {
	    Map<String, String> result = new HashMap<>();
	    result.put(key, value);
	    return new ResponseEntity<>(result, status);
	}
	
//	@Override // 로그아웃 로직
//	@GetMapping("/api/logout")
//	public ResponseEntity<?> logout(HttpSession session) {
//	    // 세션에서 사용자 정보를 제거
//	    session.removeAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);
//	    SecurityContextHolder.clearContext();
//
//	    return ResponseEntity.ok().body("로그아웃에 성공하였습니다.");
//	}
	
	@Override // 권한 설정 테스트 페이지 이동
	@RequestMapping(value = {"/admin/test.do"}, method = RequestMethod.GET)
	public ModelAndView viewAdminTest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName = (String) request.getAttribute("viewName");
		ModelAndView mav = new ModelAndView();
		mav.setViewName(viewName);
		return mav;
	}
	
	@Override // 마이페이지 하위 GET 요청에 대한 모든 페이지 이동
	@RequestMapping(value = {"/mypage/**.do"}, method = RequestMethod.GET)
	public ModelAndView viewMyprofile(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName = (String) request.getAttribute("viewName");
		ModelAndView mav = new ModelAndView();
		mav.setViewName(viewName);
		return mav;
	}
	
	@Override // 회원정보 관리 비밀번호 확인 로직
	@PostMapping("/api/confirmUser")
	public ResponseEntity<?> confirmPWD(@RequestParam("pwd") String pwd, Principal principal) {
	    // 현재 로그인된 사용자 정보 가져오기
		UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(getCurrentUser(principal).getId());

	    if (passwordEncoder.matches(pwd, userDetails.getPassword())) {
	        Map<String, Object> response = new HashMap<>();
	        response.put("status", "success");
	        response.put("isAuthenticated", true); // 현재 사용자가 인증된 상태
	        return new ResponseEntity<>(response, HttpStatus.OK);
	    } else {
	        Map<String, String> errorResponse = new HashMap<>();
	        errorResponse.put("errorMessage", "비밀번호가 잘못되었습니다.");
	        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
	    }
	}
	
	@Override // 회원정보 수정하기 form 페이지 로드 로직
	@RequestMapping(value = {"/mypage/my-info-update.do"}, method = RequestMethod.GET)
	public ModelAndView viewMyInfoUpdate(Principal principal, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName = (String) request.getAttribute("viewName");
		// 현재 로그인된 사용자 정보 가져오기
		UserVO user = getCurrentUser(principal);
		ModelAndView mav = new ModelAndView();
		mav.setViewName(viewName);
		mav.addObject("user", user);
		return mav;
	}
	
	@Override // 회원정보 수정 로직
	@PostMapping("/api/update-user")
	public ResponseEntity<?> updateUser(@RequestBody UserVO userVO, Principal principal) {
	    // 현재 로그인된 사용자의 정보를 가져옴
	    UserVO currentUser = getCurrentUser(principal);

	    // 현재 로그인된 사용자의 정보만 수정 가능하도록 체크
	    if (!currentUser.getId().equals(userVO.getId())) {
	        return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
	    }

	    try {
	        userService.updateUser(userVO);
	        return new ResponseEntity<>("success", HttpStatus.OK);
	    } catch (Exception e) {
	    	e.printStackTrace();
	        return new ResponseEntity<>("fail", HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
	
	@Override // 프로필 수정 페이지 이동
	@GetMapping("/mypage/my-info-profile-edit.do")
	public ModelAndView editMyprofilePic(@RequestParam String currentProfilePicSrc, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName = (String) request.getAttribute("viewName");
		String currentProfilePic = currentProfilePicSrc;
		ModelAndView mav = new ModelAndView();
		mav.setViewName(viewName);
		mav.addObject("currentProfilePic", currentProfilePic);
		return mav;
	}
	
	@Override // 회원정보 탈퇴하기 form 페이지 로드 로직
	@RequestMapping(value = {"/mypage/my-info-delete.do"}, method = RequestMethod.GET)
	public ModelAndView viewMyInfoDelete(Principal principal, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName = (String) request.getAttribute("viewName");
		// 현재 로그인된 사용자의 정보를 가져옴
		UserVO user = getCurrentUser(principal);
		ModelAndView mav = new ModelAndView();
		mav.setViewName(viewName);
		mav.addObject("user", user);
		return mav;
	}
	
	@Override // 회원 탈퇴 로직
	@PostMapping("/api/delete-user")
	public ResponseEntity<?> deleteUser(@RequestBody UserVO userVO, Principal principal) {
	    // 현재 로그인된 사용자의 정보를 가져옴
	    UserVO currentUser = getCurrentUser(principal);

	    // 현재 로그인된 사용자의 정보로만 탈퇴 가능하도록 체크
	    if (!currentUser.getId().equals(userVO.getId())) {
	        return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
	    }
	    try {
	        userService.deleteUser(userVO);
	        return new ResponseEntity<>("success", HttpStatus.OK);
	    } catch (Exception e) {
	    	e.printStackTrace();
	        return new ResponseEntity<>("fail", HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
	
	// 현재 로그인된 사용자 정보 가져오는 메소드
	private UserVO getCurrentUser(Principal principal) {
	    String currentUsername = principal.getName();
	    return userService.getUserByUsername(currentUsername);
	}
	
	// 관리자 영역 ----------------------------------------------------------------------------------------------------------------------------------
	@Override // 회원 관리 메인 페이지 이동
	@GetMapping("/privacy-admin/user-management/main.do")
	public ModelAndView viewUserManagementMain(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName = (String) request.getAttribute("viewName");
		ModelAndView mav = new ModelAndView();
		mav.setViewName(viewName);

		List<UserVO> newUsers = userService.getNewUsers();
		List<UserVO> withdrawnUsers = userService.getWithdrawnUsersThisMonth();
		List<UserVO> suspendedUsers = userService.getSuspendedUsersThisMonth();

		mav.addObject("newUsers", newUsers);
		mav.addObject("withdrawnUsers", withdrawnUsers);
		mav.addObject("suspendedUsers", suspendedUsers);
		
		mav.addObject("hasMoreNewUsers", newUsers.size() > 5);
		mav.addObject("hasMoreWithdrawnUsers", withdrawnUsers.size() > 5);
		mav.addObject("hasMoreSuspendedUsers", suspendedUsers.size() > 5);

		return mav;
	}
	
	@Override // 회원 정보 관리 페이지 이동
	@GetMapping("/privacy-admin/user-management/user-list.do")
	public ModelAndView viewUserList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName = (String) request.getAttribute("viewName");
		ModelAndView mav = new ModelAndView();
		List<UserVO> users = userService.getAllUsers();
		mav.setViewName(viewName);
		mav.addObject("users", users);
		return mav;
	}

	@Override // 관리자 정보 관리 페이지 이동
	@GetMapping("/privacy-admin/user-management/admin-list.do")
	public ModelAndView viewAdminList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName = (String) request.getAttribute("viewName");
		ModelAndView mav = new ModelAndView();
		List<UserVO> users = userService.getAllAdmins();
		mav.setViewName(viewName);
		mav.addObject("users", users);
		return mav;
	}
	
	@Override // 회원 세부정보 페이지 로드
	@GetMapping("/privacy-admin/user-management/user-detail.do")
	public ModelAndView viewUserDetail(@RequestParam int user_num, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName = (String) request.getAttribute("viewName");
		ModelAndView mav = new ModelAndView();
		UserVO user = userService.getUserByUserNum(user_num);
		UserVO updatedUser = userService.getUserByUserNum(user.getUpdated_user_num());
    	UserVO deletedUser = userService.getUserByUserNum(user.getDeleted_user_num());
		
		String updatedUserDisplay;
		if (updatedUser == null) {
			updatedUserDisplay = "-";
		} else if (updatedUser.getUser_num() == user_num) {
			updatedUserDisplay = "본인";
		} else {
			updatedUserDisplay = "<img class='detail-rank-img' src='" + updatedUser.getRank().getImagePath() + "'>" + updatedUser.getNickname();
		}
		String deletedUserDisplay;
		if (deletedUser == null) {
			deletedUserDisplay = "-";
		} else if (deletedUser.getUser_num() == user_num) {
			deletedUserDisplay = "본인";
		} else {
			deletedUserDisplay = "<img class='detail-rank-img' src='" + deletedUser.getRank().getImagePath() + "'>" + deletedUser.getNickname();
		}

		mav.setViewName(viewName);
		mav.addObject("user", user);
 		mav.addObject("updatedUserDisplay", updatedUserDisplay);
    	mav.addObject("deletedUserDisplay", deletedUserDisplay);
		return mav;
	}
	
	@Override // 회원 세부정보 수정 페이지 로드
	@GetMapping("/privacy-admin/user-management/user-detail-update.do")
	public ModelAndView viewUserDetailUpdate(@RequestParam int user_num, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName = (String) request.getAttribute("viewName");
		ModelAndView mav = new ModelAndView();
		UserVO user = userService.getUserByUserNum(user_num);
		mav.setViewName(viewName);
		mav.addObject("user", user);
		return mav;
	}

	@Override // 회원 세부정보 정보수정 로직
	@PostMapping("/api/update-userDetail")
	public ResponseEntity<?> updateUserDetail(@RequestBody UserVO userVO, Principal principal) {
		try {
			UserVO curUserVO = getCurrentUser(principal);
			int curUserNum = curUserVO.getUser_num();
			userService.updateUserDetail(userVO, curUserNum);
			return new ResponseEntity<>("success", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>("fail", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override // 회원 정지 로직
	@PostMapping("/api/suspend-user")
	public ResponseEntity<Map<String, Object>> suspendUser(@RequestBody Map<String, Object> requestData, Principal principal) {
		Map<String, Object> response = new HashMap<>();
		UserVO loginedUser = getCurrentUser(principal);
		int user_num = Integer.parseInt(requestData.get("userNum").toString());
		String action = (String)requestData.get("action");
		try {
			if ("suspend".equals(action)) {
				String suspended_reason = (String)requestData.get("reason");
				int suspension_duration = Integer.parseInt(requestData.get("duration").toString());
				int suspend_user_num = loginedUser.getUser_num();
				
				String result = userService.suspendUser(user_num, action, suspend_user_num, suspended_reason, suspension_duration);
				response.put("status", "success");
				response.put("message","succeeded.");
				response.put("action", result);
			} else if ("unsuspend".equals(action)) {
				String result = userService.unsuspendUser(user_num, action);
				response.put("status", "success");
				response.put("message", "succeeded.");
				response.put("action", result);
			} else {
				response.put("status", "error");
				response.put("message", "Invalid action.");
			}
			
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();  // 로그에 예외 스택 트레이스 출력
			response.put("status", "error");
			response.put("message", e.getMessage() != null ? e.getMessage() : "Unknown error");
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override // 회원 정보 삭제 로직
	@PostMapping("/api/remove-user")
	public ResponseEntity<Map<String, Object>> removeUser(@RequestBody Map<String, Object> requestData) {
		Map<String, Object> response = new HashMap<>();
		try {
			int user_num = Integer.parseInt(requestData.get("userNum").toString());
			userService.removeUser(user_num);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			response.put("status", "error");
			response.put("message", e.getMessage());
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


	@Override // 회원 등급 관리 페이지 이동
	@GetMapping("/privacy-admin/user-management/user-rank-management.do")
	public ModelAndView viewRankList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName = (String) request.getAttribute("viewName");
		ModelAndView mav = new ModelAndView();
		List<UserVO> users = userService.getAllAccounts();
		mav.setViewName(viewName);
		mav.addObject("users", users);
		return mav;
	}
	 
	@Override // 회원 등급 승격 로직
	@PostMapping("/api/rank-up")
	public ResponseEntity<Map<String, Object>> rankUp(@RequestBody Map<String, Object> requestData) {
		Map<String, Object> response = new HashMap<>();
		int user_num = Integer.parseInt(requestData.get("userNum").toString());
		String rank = (String)requestData.get("rank");
		try {
			if (rank.equals("EGG") || rank.equals("HATCHING_CHICK") || rank.equals("CHICK") || rank.equals("CHICKEN") || (rank.equals("ADMIN"))) {
				userService.rankUp(user_num, rank);
				response.put("status","success");
				response.put("message", "님의 등급이 승격되었습니다.");
				return new ResponseEntity<>(response, HttpStatus.OK);
			} else if (rank.equals("FRIED_CHICKEN") || rank.equals("PRIVACY_ADMIN")) {
				response.put("status","fail");
				response.put("message", "님의 등급은 이미 최고 등급입니다.");
				return new ResponseEntity<>(response, HttpStatus.OK);
			} else {
				response.put("status","fail");
				response.put("message", "님의 등급이 잘못되었습니다.");
				return new ResponseEntity<>(response, HttpStatus.OK);
			}
		} catch (Exception e) {
			response.put("status", "error");
			response.put("message", e.getMessage());
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override // 회원 권한 전환 로직
	@PostMapping("/api/rank-switch")
	public ResponseEntity<Map<String, Object>> switchRank(@RequestBody Map<String, Object> requestData) {
		Map<String, Object> response = new HashMap<>();
		int user_num = Integer.parseInt(requestData.get("userNum").toString());
		String rank = (String)requestData.get("rank");
		UserVO user = userService.getUserByUserNum(user_num);
		String currentRole = user.getIs_admin();
		String newRole, newRank;
		try {
			if (currentRole.equals("USER")) {
				newRole = "ADMIN";
				newRank = "ADMIN";
				response.put("message", "님의 권한이 관리자로 전환되었습니다.");
			} else if (currentRole.equals("ADMIN") || currentRole.equals("PRIVACY_ADMIN")) {
				newRole = "USER";
				newRank = "EGG";
				response.put("message", "님의 권한이 사용자로 전환되었습니다.");
			} else {
				response.put("status","fail");
				response.put("message", "잘못된 접근입니다");
				return new ResponseEntity<>(response, HttpStatus.OK);
			}
		} catch (Exception e) {
			response.put("status", "error");
			response.put("message", e.getMessage());
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		userService.switchRank(user_num, newRole, newRank);
		response.put("status","success");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@Override // 신규 회원 관리 페이지 이동
	@GetMapping("/privacy-admin/user-management/new-users.do")
	public ModelAndView viewNewUsersList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName = (String) request.getAttribute("viewName");
		ModelAndView mav = new ModelAndView();
		List<UserVO> users = userService.getNewUsers();
		mav.setViewName(viewName);
		mav.addObject("users", users);
		return mav;
	}

	@Override // 탈퇴 회원 관리 페이지 이동
	@GetMapping("/privacy-admin/user-management/withdrawn-users.do")
	public ModelAndView viewWithdrawnUsersList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName = (String) request.getAttribute("viewName");
			ModelAndView mav = new ModelAndView();
			List<UserVO> users = userService.getWithdrawnUsers();
			mav.setViewName(viewName);
			mav.addObject("users", users);
			return mav;
	}

	@Override // 정지 회원 관리 페이지 이동
	@GetMapping("/privacy-admin/user-management/suspended-users.do")
	public ModelAndView viewSuspendUsersList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName = (String) request.getAttribute("viewName");
			ModelAndView mav = new ModelAndView();
			List<UserVO> users = userService.getSuspendUsers();
			mav.setViewName(viewName);
			mav.addObject("users", users);
			return mav;
	}

}
