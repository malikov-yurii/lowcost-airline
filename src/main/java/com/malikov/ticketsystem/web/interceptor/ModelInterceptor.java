package com.malikov.ticketsystem.web.interceptor;

import com.malikov.ticketsystem.AuthorizedUser;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This interceptor adds the user name to the model of every requests managed
 *
 * @author Yurii Malikov
 */
public class ModelInterceptor extends HandlerInterceptorAdapter {

    // TODO: 6/1/2017 consider moving userFullName dto session and access it in jsp using getSessionAttribute. It must be better
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (modelAndView != null && !modelAndView.isEmpty()) {
            AuthorizedUser authorizedUser = AuthorizedUser.safeGet();
            if (authorizedUser != null) {
                modelAndView.getModelMap().addAttribute("userFullName", authorizedUser.getFullName());
            }
        }
    }
}
