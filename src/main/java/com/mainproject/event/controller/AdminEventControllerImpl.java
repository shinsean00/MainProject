package com.mainproject.event.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mainproject.event.service.AdminEventService;
import com.mainproject.event.service.EventService;
import com.mainproject.event.vo.EventVO;
import com.mainproject.paging.PagingUtils;
import com.mainproject.paging.PagingVO;
import com.mainproject.user.vo.CustomUserDetails;

 
@Controller
@RequestMapping("/event") 

public class AdminEventControllerImpl implements AdminEventController  {
	 
	@Autowired
	private AdminEventService adminEventService;

 
    @GetMapping("/admincreateEventForm")
    public String showCreateEventForm(Model model) { 
        model.addAttribute("event", new EventVO());
        return "event/admincreateEventForm";  
    }     
  
    @PostMapping("/admincreateEvent")
    public void admincreateEvent(@ModelAttribute("event") EventVO eventVO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        int userNum = ((CustomUserDetails) userDetails).getUsernum(); // 현재 로그인한 사용자의 번호
        String username = ((CustomUserDetails) userDetails).getUsername();
          
        
        
        int lastEventOrder = adminEventService.getLastEventOrderForUser(userNum);
 
        // 새로운 일정의 event_order 설정  
        eventVO.setEvent_order(lastEventOrder + 1);
        eventVO.setCreated_user_num(userNum); // 생성자 정보 설정
        eventVO.setEvent_user_name(username);   
 
        adminEventService.createEvent(eventVO); 
        // 리다이렉트나 뷰 이름 등을 처리하는 로직이 있을 수 있습니다.
    }  
     
    
    
    @GetMapping("/adminlistEvents.do")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView adminlistMyEvents() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        int userNum = ((CustomUserDetails) userDetails).getUsernum();

        ModelAndView modelAndView = new ModelAndView("event/adminlistEvents");

        List<EventVO> allEvents = adminEventService.getAllEvents();

        modelAndView.addObject("allEvents", allEvents); // 모든 이벤트를 뷰로 전달
        modelAndView.addObject("userEvents", adminEventService.listEventsForUserNum(userNum)); // 사용자 이벤트도 전달
 
        return modelAndView;  
    } 
    
    
    @GetMapping("/adminviewEvent") 
    public ModelAndView viewEvent(@RequestParam("eventTitle") String eventTitle) {
        ModelAndView modelAndView = new ModelAndView("event/adminviewEvent");
        EventVO event = adminEventService.getEventByTitle(eventTitle);
        modelAndView.addObject("event", event);
        modelAndView.addObject("eventNum", event.getEvent_num()); // 
        return modelAndView; 
    } 
    
    @GetMapping("/admindeleteEvent")
    @Transactional 
    public String deleteEvent(@RequestParam("eventNum") int eventNum) {
        try {
            adminEventService.markEventAsDeleted(eventNum);
        } catch (IllegalArgumentException e) {
            
            e.printStackTrace();  
        }
        return "redirect:/event/adminlistEvents.do";     
    } 
    
    @GetMapping("/admineditEventForm")
    public String showEditEventForm(@RequestParam("eventNum") int eventNum, Model model) {
        EventVO event = adminEventService.getEventByEventNum(eventNum);
        if (event != null) {
            model.addAttribute("event", event); 
            return "event/admineditEventForm";
        } else {
            return null;  
        }    
    }
    @PostMapping("/adminupdateEvent") 
    public String updateEvent(@ModelAttribute EventVO event, @RequestParam("event_num") int eventNum, RedirectAttributes redirectAttributes) {
        try {
            adminEventService.updateEventByEventNum(eventNum, event);
 
         
            redirectAttributes.addAttribute("eventTitle", event.getTitle());
            return "redirect:/event/adminviewEvent";
        } catch (IllegalArgumentException e) { 
           
            e.printStackTrace();
            return "redirect:/event/adminlistEvents.do"; 
        }
    }
 
	 
	@Override
	public void createEvent(EventVO eventVO) {
		
		 
	}

	@Override
	public ModelAndView listEvents() { 
		
		return null;
	} 
} 
     
    