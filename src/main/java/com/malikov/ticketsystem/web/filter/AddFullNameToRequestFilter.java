package com.malikov.ticketsystem.web.filter;

import com.malikov.ticketsystem.AuthorizedUser;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 * @author Yurii Malikov
 */
public class AddFullNameToRequestFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        AuthorizedUser authorizedUser = AuthorizedUser.safeGet();
        if (authorizedUser != null) {
            request.setAttribute("userFullName", authorizedUser.getFullName());
        }
        chain.doFilter(request, response);
    }
}
