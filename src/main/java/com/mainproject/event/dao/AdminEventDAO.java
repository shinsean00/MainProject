package com.mainproject.event.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.mainproject.event.vo.EventVO;
import com.mainproject.paging.Criteria;
import com.mainproject.paging.PagingVO;
import com.sun.jdi.event.Event;

public interface AdminEventDAO {

	
	void insertEvent(EventVO eventVO);
	void updateEvent(EventVO existingEvent);
	void updateEventByEventNum(int eventNum, EventVO updatedEvent);
	void updateEventByTitle(String eventTitle, EventVO updatedEvent);
	void markEventAsDeleted(int eventNum);
	void deleteEvent(Long eventId);
	void deleteEvent(int eventId); 
	int getLastEventOrderForUser(int userNum);
	List<EventVO> getAllEvents();
	List<EventVO> selectAllEvents();
	List<EventVO> selectAllEventsForUserNum(String userNum);
	List<EventVO> listEventsForUserNum(String userNum);
	List<EventVO> listEventsForUserNum(int userNum);
	List<EventVO> selectEventsForUserNum(int userNum);
	EventVO getEventByTitle(String eventTitle);
	EventVO getEventById(int eventId);
	EventVO getEventByEventNum(int eventNum); 
	
	
	 
	
	
	
	

	

	

	

} 
 