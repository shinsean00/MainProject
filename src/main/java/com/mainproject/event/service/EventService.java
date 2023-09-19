package com.mainproject.event.service;

import java.util.List;

import com.mainproject.event.vo.EventVO;
import com.mainproject.paging.Criteria;
  
public interface EventService {
	
    void createEvent(EventVO eventVO);
    void deleteEvent(int eventId); 
	void updateEvent(EventVO event);
	void updateEventByTitle(String title, EventVO existingEvent);
	void updateEventByIdAndTitle(int eventId, String eventTitle, EventVO updatedEvent);
	void updateEventByEventNum(int event_num, EventVO event);
	void markEventAsDeleted(int eventId);
	int getLastEventOrderForUser(int userNum);
	List<EventVO> listEventsForUserNum(int userNum);
	List<EventVO> listEventsForUserNum(String userNum);
	List<EventVO> listEvents(); 
	EventVO getEventByTitle(String eventTitle);
	EventVO getEventById(int eventId);
	EventVO getEventByEventNum(int eventNum); 
	  
 
	

	

	

	

	

	   

	
	

	

	

	   

	  
	 
    
}