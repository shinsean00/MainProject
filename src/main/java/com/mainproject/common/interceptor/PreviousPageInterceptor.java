package com.mainproject.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class PreviousPageInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        String referrer = request.getHeader("Referer");
        
        if (referrer != null) {
            // 로그인 페이지나 세션 관리 페이지 체크
            if (referrer.endsWith("/login.do") || referrer.endsWith("/session-expired")) {
                session.setAttribute("previousPage", "/index.do");
            } else {
                session.setAttribute("previousPage", referrer);
            }
        }
        return true;
    }
    
    @Override
    public void postHandle(
        HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(
        HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}
