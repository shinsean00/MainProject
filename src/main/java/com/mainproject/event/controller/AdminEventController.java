package com.mainproject.event.controller;

import org.springframework.web.servlet.ModelAndView;

import com.mainproject.event.vo.EventVO;

public interface AdminEventController {
	
	void createEvent(EventVO eventVO);
	 
	 ModelAndView listEvents();

}
