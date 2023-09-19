package com.mainproject.event.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import com.mainproject.event.vo.EventVO;

@Controller
public interface EventController {
	
   void createEvent(EventVO eventVO);
	 
	 ModelAndView listEvents();
     
    
}  


