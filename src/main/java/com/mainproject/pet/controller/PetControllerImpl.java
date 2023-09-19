package com.mainproject.pet.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.mainproject.pet.service.PetService;
import com.mainproject.pet.vo.PetVO;
import com.mainproject.user.service.UserService;
import com.mainproject.user.vo.CustomUserDetails;
import com.mainproject.user.vo.UserVO;

@Controller("petController")
public class PetControllerImpl implements PetController {
	@Autowired
	private PetService petService;

	@Autowired
	private UserService userService;

	@Autowired
	PetVO petVO;

	@Autowired
	UserVO userVO;

	@Override // 반려친구 등록페이지
	@RequestMapping(value = { "/mypage/pet-join.do" }, method = RequestMethod.GET)
	public ModelAndView viewPetJoin(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName = (String) request.getAttribute("viewName");
		ModelAndView mav = new ModelAndView();
		mav.setViewName(viewName);
		return mav;
	}

	@Override // 반려 친구 등록
	@PostMapping("/api/register-pet")
	public ResponseEntity<Map<String, String>> registerPet(@RequestBody PetVO petVO) throws Exception {
		Map<String, String> response = new HashMap<>();
		try {
			// 로그인한 사용자의 CustomUserDetails 정보를 가져오기
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

			// CustomUserDetails를 통해 원래 UserVO 객체를 얻기
			UserVO userVO = userDetails.getUser();

			// 회원 번호를 얻기
			int userNum = userVO.getUser_num();

			// 회원 번호를 petVO 객체에 설정
			petVO.setCreated_user_num(userNum);
			petVO.setAdopted_user_num(userNum);

			// Pet 등록 서비스 호출
			petService.registerPet(petVO);
			response.put("status", "success");
			response.put("redirectUrl", "/mypage/pet-list.do");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			response.put("status", "fail");
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override // 마이페이지 반려친구 리스트
	@GetMapping("/mypage/pet-list.do")
	public String getPetList(Model model) throws Exception {
		// 로그인한 사용자의 CustomUserDetails 정보를 가져오기
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

		// CustomUserDetails를 통해 원래 UserVO 객체를 얻기
		UserVO userVO = userDetails.getUser();

		// 회원 번호를 얻기
		int userNum = userVO.getUser_num();
		List<PetVO> petList = petService.getPetByUserNum(userNum);
		model.addAttribute("petList", petList);
		return "mypage/pet-list";

	}

	@Override // 반려친구 정보-수정까지 가능한 페이지
	@RequestMapping(value = { "/mypage/pet-info/{id}.do" }, method = RequestMethod.GET)
	public ModelAndView viewPetInfo(@PathVariable("id") int petNo, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PetVO petVO = petService.getPetByPetNo(petNo);

		ModelAndView mav = new ModelAndView();
		mav.addObject("pet", petVO);
		mav.setViewName("mypage/pet-info");

		return mav;
	}

	@Override // 반려친구 수정 페이지
	@RequestMapping(value = "/mypage/pet-update/{id}.do", method = RequestMethod.GET)
	public ModelAndView viewPetUpdate(@PathVariable("id") int petNo, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PetVO petVO = petService.getPetByPetNo(petNo);

		ModelAndView mav = new ModelAndView();
		mav.addObject("pet", petVO);
		mav.setViewName("mypage/pet-update2");

		return mav;
	}

	@Override // 반려 친구 수정
	@PostMapping("/api/update-pet")
	public ResponseEntity<Map<String, String>> updatePet(@RequestBody PetVO petVO) throws Exception {
		Map<String, String> response = new HashMap<>();
		try {
			// 로그인한 사용자의 CustomUserDetails 정보를 가져오기
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

			// CustomUserDetails를 통해 원래 UserVO 객체를 얻기
			UserVO userVO = userDetails.getUser();

			// 회원 번호를 얻기
			int userNum = userVO.getUser_num();

			// 회원 번호를 petVO 객체에 설정
			petVO.setUpdated_user_num(userNum);

			// Pet 등록 서비스 호출
			petService.updatePet(petVO);
			response.put("status", "success");
			response.put("redirectUrl", "/mypage/pet-list.do");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			response.put("status", "fail");
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override // 반려 친구 삭제
	@PostMapping("/api/delete-pet")
	public ResponseEntity<Map<String, String>> deletePet(@RequestBody PetVO petVO) throws Exception {
		Map<String, String> response = new HashMap<>();
		try {
			// 로그인한 사용자의 CustomUserDetails 정보를 가져오기
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

			// CustomUserDetails를 통해 원래 UserVO 객체를 얻기
			UserVO userVO = userDetails.getUser();

			// 회원 번호를 얻기
			int userNum = userVO.getUser_num();

			// 회원 번호를 petVO 객체에 설정
			petVO.setDeleted_user_num(userNum);

			// Pet 등록 서비스 호출
			petService.deletePet(petVO);
			response.put("status", "success");
			response.put("redirectUrl", "/mypage/pet-list.do");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			response.put("status", "fail");
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// 관리자 영역
	// ----------------------------------------------------------------------------------------------------------------------------------

	@Override // 관리자 반려친구 리스트
	@GetMapping("/privacy-admin/pet-management/admin-pet-list.do")
	public String getAdminPetList(Model model) throws Exception {
		// 로그인한 사용자의 CustomUserDetails 정보를 가져오기
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

		// CustomUserDetails를 통해 원래 UserVO 객체를 얻기
		UserVO userVO = userDetails.getUser();

		// 회원 번호를 얻기
		int userNum = userVO.getUser_num();
		List<PetVO> petList = petService.getAllPet();
		model.addAttribute("petList", petList);
		return "privacy-admin/pet-management/admin-pet-list";

	}

	@Override // 관리자 반려친구 리스트 검색
	@RequestMapping(value = "/privacy-admin/pet-management/search", method = RequestMethod.GET)
	public ModelAndView searchPet(@RequestParam String category, @RequestParam String keyword) throws Exception {
		List<PetVO> petList = petService.searchPet(category, keyword);

		ModelAndView mav = new ModelAndView();
		mav.setViewName("privacy-admin/pet-management/admin-pet-list");
		mav.addObject("petList", petList);

		return mav;
	}

	@Override // 관리자 반려친구 정보
	@RequestMapping(value = { "/privacy-admin/pet-management/admin-pet-info/{id}.do" }, method = RequestMethod.GET)
	public ModelAndView viewAdminPetInfo(@PathVariable("id") int petNo, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		PetVO petVO = petService.getPetByPetNo(petNo);

		int user_num = petVO.getAdopted_user_num();

		UserVO userVO = userService.getUserByUserNum(user_num);
		
		List<UserVO> userList = userService.getAllUsers();

		ModelAndView mav = new ModelAndView();
		mav.addObject("pet", petVO);
		mav.addObject("user", userVO);
		mav.addObject("userList", userList);

		// Thymeleaf 템플릿 이름으로 직접 설정
		mav.setViewName("privacy-admin/pet-management/admin-pet-info");

		return mav;
	}

	@Override // 관리자 반려 친구 수정
	@PostMapping("/api/update-admin-pet")
	public ResponseEntity<Map<String, String>> updateAdminPet(@RequestBody PetVO petVO) throws Exception {
		Map<String, String> response = new HashMap<>();
		try {
			// 로그인한 사용자의 CustomUserDetails 정보를 가져오기
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

			// CustomUserDetails를 통해 원래 UserVO 객체를 얻기
			UserVO userVO = userDetails.getUser();

			// 회원 번호를 얻기
			int userNum = userVO.getUser_num();

			// 회원 번호를 petVO 객체에 설정
			petVO.setUpdated_user_num(userNum);

			// Pet 등록 서비스 호출
			petService.updatePet(petVO);
			response.put("status", "success");
			response.put("redirectUrl", "/privacy-admin/pet-management/admin-pet-list.do");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			response.put("status", "fail");
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override // 관리자 반려 친구 삭제
	@PostMapping("/api/delete-admin-pet")
	public ResponseEntity<Map<String, String>> deleteAdminPet(@RequestBody PetVO petVO) throws Exception {
		Map<String, String> response = new HashMap<>();
		try {
			// 로그인한 사용자의 CustomUserDetails 정보를 가져오기
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

			// CustomUserDetails를 통해 원래 UserVO 객체를 얻기
			UserVO userVO = userDetails.getUser();

			// 회원 번호를 얻기
			int userNum = userVO.getUser_num();

			// 회원 번호를 petVO 객체에 설정
			petVO.setDeleted_user_num(userNum);

			// Pet 등록 서비스 호출
			petService.deletePet(petVO);
			response.put("status", "success");
			response.put("redirectUrl", "/privacy-admin/pet-management/admin-pet-list.do");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			response.put("status", "fail");
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
