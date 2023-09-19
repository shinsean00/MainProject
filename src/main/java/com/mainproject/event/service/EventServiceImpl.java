package com.mainproject.event.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mainproject.event.dao.EventDAO;
import com.mainproject.event.vo.EventVO;
import com.mainproject.paging.Criteria;
import com.mainproject.paging.PagingVO;

@Service("eventService")

public class EventServiceImpl implements EventService { 
	  
	@Autowired    
    private EventDAO eventDAO;

    @Override   
    public void createEvent(EventVO eventVO) {
        if (eventVO != null && eventVO.getTitle() != null && !eventVO.getTitle().isEmpty()) {
        	 int userNum = eventVO.getCreated_user_num();
        	 String username = eventVO.getEvent_user_name();
        	 
             int lastEventOrder = eventDAO.getLastEventOrderForUser(userNum);
             eventVO.setEvent_order(lastEventOrder + 1); 
             
             eventVO.setEvent_user_name(username); // 작성자 이름 설정
             
        	eventDAO.insertEvent(eventVO);
        } else {
            throw new IllegalArgumentException("Event title cannot be null or empty.");
        }  
    } 
    
	 
	 
	@Override
	public void updateEventByEventNum(int eventNum, EventVO updatedEvent) {
	    EventVO existingEvent = eventDAO.getEventByEventNum(eventNum);
	    if (existingEvent != null) {
	        
	    	existingEvent.setTitle(updatedEvent.getTitle());
	        existingEvent.setStarted_at(updatedEvent.getStarted_at());
	        existingEvent.setEnded_at(updatedEvent.getEnded_at());
	        existingEvent.setLocation(updatedEvent.getLocation());
	        existingEvent.setContent(updatedEvent.getContent()); 

	        eventDAO.updateEvent(existingEvent);
	    } else {
	        throw new IllegalArgumentException("�̺�Ʈ�� ã�� �� ���ų� �̺�Ʈ ��ȣ�� ��ġ���� �ʽ��ϴ�.");
	    }
	}

	@Override
	@Transactional
	public void markEventAsDeleted(int eventNum) {
	    EventVO existingEvent = eventDAO.getEventByEventNum(eventNum); 
	    if (existingEvent != null) {
	        existingEvent.setIs_deleted(true); 
	        eventDAO.updateEvent(existingEvent); 
	    } else {
	        throw new IllegalArgumentException("�̺�Ʈ�� ã�� �� ���ų� �̺�Ʈ ��ȣ�� ��ġ���� �ʽ��ϴ�.");
	    }
	} 
    
	
	
	 @Override
		public int getLastEventOrderForUser(int userNum) {
		 
		    int lastEventOrder = eventDAO.getLastEventOrderForUser(userNum); 
		    return lastEventOrder;
		}
		 
 

	@Override
	@Transactional
	public void updateEvent(EventVO event) {
		      eventDAO.updateEvent(event);
    } 


	@Override
	public EventVO getEventByEventNum(int eventNum) throws DataAccessException {
	    return eventDAO.getEventByEventNum(eventNum);
	}
	    
	  
	@Override
    public List<EventVO> listEventsForUserNum(int userNum) {
        return eventDAO.selectEventsForUserNum(userNum);
    }  
      
    
	@Override
	public EventVO getEventByTitle(String eventTitle) {
		return eventDAO.getEventByTitle(eventTitle);
	}  

	
	@Override
    public EventVO getEventById(int eventId) {
        return eventDAO.getEventById(eventId); 
    }

  
	@Override
	public List<EventVO> listEventsForUserNum(String userNum) {
		
		return null;
	}
	
	
	@Override
	public List<EventVO> listEvents() {
		
		return null;
	}
	
	
	 @Override
	public void updateEventByTitle(String title, EventVO existingEvent) {
			
	}

	    
	@Override
	public void updateEventByIdAndTitle(int eventId, String eventTitle, EventVO updatedEvent) {
		
	}
  
	
	@Override
	public void deleteEvent(int eventId) {
		
		
	}
 

 


	
	
	



 

	
	
	


	 
	}