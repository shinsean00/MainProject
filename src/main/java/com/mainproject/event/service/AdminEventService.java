package com.mainproject.event.service;

import java.util.List;

import com.mainproject.event.vo.EventVO;
import com.mainproject.paging.Criteria;

public interface AdminEventService {
    
	void createEvent(EventVO eventVO);
	void deleteEvent(int eventId); 
	void updateEvent(EventVO event);
	void updateEventByTitle(String title, EventVO existingEvent);
	void updateEventByIdAndTitle(int eventId, String eventTitle, EventVO updatedEvent);
	void updateEventByEventNum(int event_num, EventVO event);
	void markEventAsDeleted(int eventId);
	int getLastEventOrderForUser(int userNum);
	List<EventVO> getAllEvents();
	List<EventVO> listEventsForUserNum(int userNum);
	EventVO getEventByTitle(String eventTitle); 
	EventVO getEventById(int eventId);   
	EventVO getEventByEventNum(int eventNum);  
	
}
  