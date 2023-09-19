package com.mainproject;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.mainproject.board.dao.BoardDAO;
import com.mainproject.board.vo.BoardVO;
import com.mainproject.event.service.EventService;
import com.mainproject.event.vo.EventVO;
import com.mainproject.pet.service.PetService;
import com.mainproject.pet.vo.PetVO;
import com.mainproject.user.service.UserService;
import com.mainproject.user.vo.UserVO;

@Controller
public class MainController {
	@Autowired
	private UserService userService;
	
	@Autowired
	private PetService petService;
	
	@Autowired
	private EventService eventService;
	
	@Autowired
	private BoardDAO boardDAO;

	// 사용자 메인 페이지 이동
	@RequestMapping(value = {"/index.do"}, method = RequestMethod.GET)
    public ModelAndView viewUserMain (HttpServletRequest request, HttpServletResponse response) throws Exception {
    	String viewName = (String) request.getAttribute("viewName");
		ModelAndView mav = new ModelAndView();
		// 배너 리스트
		List<String> banners = Arrays.asList("banner1.png", "banner2.png");
		// 인기 게시글 정보 불러오기
		List<BoardVO> popularBoardList = boardDAO.viewPopularBoard();
		// 반려동물 게시판 정보 불러오기
		List<BoardVO> petBoardList = boardDAO.viewPetBoard();
		// 반려식물 게시판 정보 불러오기
		List<BoardVO> plantBoardList = boardDAO.viewPlantBoard();
		// 이 달의 주인님 정보 불러오기
		List<BoardVO> topOwnerList = boardDAO.viewTopOwner();
		mav.setViewName(viewName);
		mav.addObject("banners", banners);
		mav.addObject("popularBoardList", popularBoardList);
		mav.addObject("petBoardList", petBoardList);
		mav.addObject("plantBoardList", plantBoardList);
		mav.addObject("topOwnerList", topOwnerList);
		return mav;
    }

	// 관리자 메인 페이지 이동
	@RequestMapping(value = {"/admin/main.do"}, method = RequestMethod.GET)
    public ModelAndView viewAdminMain (HttpServletRequest request, HttpServletResponse response) throws Exception {
    	String viewName = (String) request.getAttribute("viewName");
		ModelAndView mav = new ModelAndView();
		mav.setViewName(viewName);
		return mav;
    }

	// 세션 만료 페이지
	@GetMapping("/session-expired")
    public String sessionExpired() {
        return "sessionExpired";
    }
	
	// 마이페이지 - 메인 페이지 이동
	@GetMapping("/mypage/main.do")
	public ModelAndView viewMypageMain (HttpServletRequest request, HttpServletResponse response, Principal principal) throws Exception {
		String viewName = (String) request.getAttribute("viewName");
		ModelAndView mav = new ModelAndView();
		// 회원 정보 불러오기
		String userID = principal.getName();
		UserVO userInfo = userService.getUserByUsername(userID);
		int userNum = userInfo.getUser_num();
		// 반려친구 정보 불러오기
		List<PetVO> petInfo = petService.getPetByUserNum(userNum);
		// 회원 일정 불러오기 
		List<EventVO> eventsList = eventService.listEventsForUserNum(userNum);
		
		mav.setViewName(viewName);
		mav.addObject("user", userInfo);
		mav.addObject("pet", petInfo);
		mav.addObject("eventsList", eventsList);
		return mav;
	}  
}
