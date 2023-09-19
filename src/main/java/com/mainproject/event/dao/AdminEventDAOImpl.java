package com.mainproject.event.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mainproject.event.vo.EventVO;
import com.mainproject.paging.Criteria;
import com.mainproject.paging.PagingVO;
import com.mainproject.user.vo.CustomUserDetails;
import com.sun.jdi.event.Event;


 
@Repository
public class AdminEventDAOImpl implements AdminEventDAO { 
	
	@Autowired       
    private SqlSession sqlSession;
	private int lastEventOrder;   
  
	@Override 
	public void insertEvent(EventVO eventVO) {
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
	        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
	        int userNum = ((CustomUserDetails) userDetails).getUsernum();  // 현재 로그인한 사용자의 번호
	        String username = ((CustomUserDetails) userDetails).getUsername();
	        
	        int lastEventOrder = getLastEventOrderForUser(userNum); // 마지막 event_order 가져오기
	        
	        eventVO.setCreated_user_num(userNum); // 생성자 정보 설정
	        eventVO.setEvent_order(lastEventOrder + 1); // 새로운 일정의 event_order 설정
	        eventVO.setEvent_user_name(username);  
	    } else { 
	        // 사용자가 인증되지 않은 경우 또는 UserDetails가 아닌 경우에 대한 처리
	        // 예: 기본 생성자 정보를 설정하거나 예외를 던지는 등의 처리
	    }   
 
	    sqlSession.insert("mapper.event.insertEvent", eventVO);
	}  
   
	
	@Override
	 public void updateEventByEventNum(int eventNum, EventVO updatedEvent) {
	   
	     EventVO existingEvent = getEventByEventNum(eventNum);

	     if (existingEvent != null) {
	         
	    	 existingEvent.setTitle(updatedEvent.getTitle());
	         existingEvent.setStarted_at(updatedEvent.getStarted_at());
	         existingEvent.setEnded_at(updatedEvent.getEnded_at());
	         existingEvent.setLocation(updatedEvent.getLocation());
	         existingEvent.setContent(updatedEvent.getContent());

	        
	         updateEvent(existingEvent);
	     } else {
	         throw new IllegalArgumentException("Event not found.");
	     }
	 }
	
	
	
	@Override
	public List<EventVO> selectEventsForUserNum(int userNum) {
	    return sqlSession.selectList("mapper.event.selectEventsForUserNum", userNum);
	}

        
	@Override
    public EventVO getEventByTitle(String eventTitle) {
        return sqlSession.selectOne("mapper.event.getEventByTitle", eventTitle);
    }
		
	
	@Override
	    public void deleteEvent(int eventId) {
	        sqlSession.delete("mapper.event.deleteEvent", eventId);
	}
 
	
	@Override
    public EventVO getEventById(int eventId) {
        return sqlSession.selectOne("mapper.event.getEventById", eventId); 
    }

	
	 @Override
	 @Transactional  
	 public void updateEvent(EventVO event) {
	      sqlSession.update("mapper.event.updateEvent", event);
	         
    } 
	 
	
	@Override
	public EventVO getEventByEventNum(int eventNum) {
	    return sqlSession.selectOne("mapper.event.getEventByEventNum", eventNum);
	} 
	 
	
	@Transactional 
	@Override 
	public void markEventAsDeleted(int eventNum) {
	    sqlSession.update("mapper.event.markEventAsDeleted", eventNum); 
	}

	 
	public List<EventVO> getAllEvents() {
        return sqlSession.selectList("mapper.event.getAllEvents");
    }
	
	
    
    @Override
	public int getLastEventOrderForUser(int userNum) {
	    Integer result = sqlSession.selectOne("mapper.event.getLastEventOrderForUser", userNum);
	    return (result != null) ? result : 0;
	}

    

	
	@Override
	public void updateEventByTitle(String eventTitle, EventVO updatedEvent) {
			
	} 
	
	
	@Override
	public void deleteEvent(Long eventId) {
		
		
	}

	
	@Override
	public List<EventVO> selectAllEvents() {
		
		return null;
	}



	@Override
	public List<EventVO> selectAllEventsForUserNum(String userNum) {
		
		return null;
	}
	 
	
	@Override
	public List<EventVO> listEventsForUserNum(String userNum) {
		
		return null;
	}


	@Override
	public List<EventVO> listEventsForUserNum(int userNum) {
		
		return null;
	}


	  

	
	
	  
	
	 
	 


 
 
	 
	
 
	 
}      
      
  
    