package com.mainproject.event.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mainproject.event.dao.AdminEventDAO;
import com.mainproject.event.dao.EventDAO;
import com.mainproject.event.vo.EventVO;
import com.mainproject.paging.Criteria;
import com.mainproject.paging.PagingVO;
import com.mainproject.user.vo.CustomUserDetails;


@Service("adminEventService")
public class AdminEventServiceImpl implements AdminEventService {
 
 
	@Autowired
	private AdminEventDAO adminEventDAO; 
	
    
	public void createEvent(EventVO eventVO) {
	    if (eventVO != null && eventVO.getTitle() != null && !eventVO.getTitle().isEmpty()) {
	        int userNum = eventVO.getCreated_user_num();
	        String username = eventVO.getEvent_user_name();

	        int lastEventOrder = adminEventDAO.getLastEventOrderForUser(userNum);
	        eventVO.setEvent_order(lastEventOrder + 1);

	        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	        eventVO.setEvent_user_name(username); // 작성자 이름 설정

	        adminEventDAO.insertEvent(eventVO);
	    } else {
	        throw new IllegalArgumentException("Event title cannot be null or empty.");
	    } 
	} 
	
	
	public void updateEventByEventNum(int eventNum, EventVO updatedEvent) {
	    EventVO existingEvent = adminEventDAO.getEventByEventNum(eventNum);
	    if (existingEvent != null) {
	        
	    	existingEvent.setTitle(updatedEvent.getTitle());
	        existingEvent.setStarted_at(updatedEvent.getStarted_at());
	        existingEvent.setEnded_at(updatedEvent.getEnded_at());
	        existingEvent.setLocation(updatedEvent.getLocation());
	        existingEvent.setContent(updatedEvent.getContent()); 

	        adminEventDAO.updateEvent(existingEvent);
	    } else {
	        throw new IllegalArgumentException("�̺�Ʈ�� ã�� �� ���ų� �̺�Ʈ ��ȣ�� ��ġ���� �ʽ��ϴ�.");
	    }
	}
    
	
	@Transactional
	public void markEventAsDeleted(int eventNum) {
	    EventVO existingEvent = adminEventDAO.getEventByEventNum(eventNum); 
	    if (existingEvent != null) {
	        existingEvent.setIs_deleted(true); 
	        adminEventDAO.updateEvent(existingEvent); 
	    } else {
	        throw new IllegalArgumentException("�̺�Ʈ�� ã�� �� ���ų� �̺�Ʈ ��ȣ�� ��ġ���� �ʽ��ϴ�.");
	    }
	} 
	 
       
	
	public int getLastEventOrderForUser(int userNum) {
		   
	    int lastEventOrder = adminEventDAO.getLastEventOrderForUser(userNum); 
	    return lastEventOrder;
	}
	
	
	
	@Override
	@Transactional
	public void updateEvent(EventVO event) {
		       adminEventDAO.updateEvent(event);
	} 
	
	

	public List<EventVO> listEventsForUserNum(int userNum) {
        return adminEventDAO.selectEventsForUserNum(userNum);
    }  
      
     

	public EventVO getEventByTitle(String eventTitle) {
		return adminEventDAO.getEventByTitle(eventTitle);
    }  

	 
	
	@Override
    public EventVO getEventById(int eventId) {
        return adminEventDAO.getEventById(eventId); 
    }
	
	 
	
	public EventVO getEventByEventNum(int eventNum) {
	    return adminEventDAO.getEventByEventNum(eventNum);
	}
	    

	  
    @Override
	public List<EventVO> getAllEvents() {
	    return adminEventDAO.getAllEvents();
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



	



	



	

	


 
	  
	 
	
	
	
	
	
