package ru.itis.javalab.filters;

import ru.itis.javalab.models.User;
import ru.itis.javalab.repositories.UsersRepository;
import ru.itis.javalab.services.UsersService;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebFilter("/profile")
public class AuthFilter implements Filter {
    UsersService usersService;


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        ServletContext servletContext = filterConfig.getServletContext();
        this.usersService = (UsersService) servletContext.getAttribute("usersService");

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
//        Cookie cookie=null;
//        Cookie[] cookies = request.getCookies();
//        String cookieName = "auth";
//        if (cookies != null) {
//            for (Cookie c : cookies) {
//                if (cookieName.equals(c.getName())) {
//                    cookie = c;
//                    break;
//                }
//            }if(cookie!=null){
//                List<User> users = usersService.getUsersByUuid(cookie.getValue());
//                if (users.isEmpty()) {
//                    response.sendRedirect("/login");
//                }else{
//                    return;
//                }
//            } else{
//                response.sendRedirect("/login");
//            }
//
//        } else{
//            response.sendRedirect("/login");
//        }
        HttpSession session = request.getSession();

        if(session.getAttribute("user")!=null){
            return;
        } else{
            response.sendRedirect("/login");
        }

        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }


}
