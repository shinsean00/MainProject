package com.mainproject.user.controller;

import java.security.Principal;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.mainproject.user.vo.LoginRequest;
import com.mainproject.user.vo.UserVO;

public interface UserController {
	public ModelAndView viewJoin(HttpServletRequest request, HttpServletResponse response) throws Exception;
	public ResponseEntity<String> registerUser(@RequestBody UserVO userVO);
	public ResponseEntity<Map<String, Boolean>> checkId(@RequestParam("id") String id);
	public ResponseEntity<Map<String, Boolean>> checkNickname(@RequestParam("nickname") String nickname);
	public ModelAndView viewJoinComplete(@RequestParam("name") String name, HttpServletRequest request, HttpServletResponse response) throws Exception;
	public ModelAndView viewLogin(HttpServletRequest request, HttpServletResponse response) throws Exception;
	public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest, HttpSession session) throws Exception;
//	public ResponseEntity<?> logout(HttpSession session);
	public ModelAndView viewAdminTest(HttpServletRequest request, HttpServletResponse response) throws Exception;
	public ModelAndView viewMyprofile(HttpServletRequest request, HttpServletResponse response) throws Exception;
	public ResponseEntity<?> confirmPWD(@RequestParam("pwd") String pwd, Principal principal);
	public ModelAndView viewMyInfoUpdate(Principal principal, HttpServletRequest request, HttpServletResponse response) throws Exception;
	public ResponseEntity<?> updateUser(UserVO userVO, Principal principal);
	public ModelAndView editMyprofilePic(@RequestParam String currentProfilePicSrc, HttpServletRequest request, HttpServletResponse response) throws Exception;
	public ModelAndView viewMyInfoDelete(Principal principal, HttpServletRequest request, HttpServletResponse response) throws Exception;
	public ResponseEntity<?> deleteUser(UserVO userVO, Principal principal);
	
	
	// 관리자 영역
	public ModelAndView viewUserList(HttpServletRequest request, HttpServletResponse response) throws Exception;
	public ModelAndView viewAdminList(HttpServletRequest request, HttpServletResponse response) throws Exception;
	public ModelAndView viewUserDetail(int user_num, HttpServletRequest request, HttpServletResponse response) throws Exception;
	public ModelAndView viewUserDetailUpdate(int user_num, HttpServletRequest request, HttpServletResponse response) throws Exception;
	public ResponseEntity<?> updateUserDetail(@RequestBody UserVO userVO, Principal principal);
	public ResponseEntity<Map<String, Object>> suspendUser(@RequestBody Map<String, Object> requestData, Principal principal);
	public ResponseEntity<Map<String, Object>> removeUser(@RequestBody Map<String, Object> requestData);
	public ModelAndView viewRankList(HttpServletRequest request, HttpServletResponse response) throws Exception;
	public ResponseEntity<Map<String, Object>> rankUp(@RequestBody Map<String, Object> requestData);
	public ResponseEntity<Map<String, Object>> switchRank(@RequestBody Map<String, Object> requestData);
	public ModelAndView viewNewUsersList(HttpServletRequest request, HttpServletResponse response) throws Exception;
	public ModelAndView viewWithdrawnUsersList(HttpServletRequest request, HttpServletResponse response) throws Exception;
	public ModelAndView viewSuspendUsersList(HttpServletRequest request, HttpServletResponse response) throws Exception;
	public ModelAndView viewUserManagementMain(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
