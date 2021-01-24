package ru.itis.javalab.servlets;

import org.springframework.security.crypto.password.PasswordEncoder;
import ru.itis.javalab.models.User;
import ru.itis.javalab.services.UsersService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    UsersService usersService;
    private PasswordEncoder passwordEncoder;

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext servletContext = config.getServletContext();
        this.usersService = (UsersService) servletContext.getAttribute("usersService");
        this.passwordEncoder = (PasswordEncoder) servletContext.getAttribute("passwordEncoder");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("login.html");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String login = req.getParameter("login");
        String password = req.getParameter("password");


        User user = usersService.getUserByLoginPassword(login, password);

        if (user != null) {
            Cookie cookie = new Cookie("auth", URLEncoder.encode(user.getUuid(), "UTF-8"));
            cookie.setMaxAge(900);
            resp.addCookie(cookie);
            session.setAttribute("user", user);
            resp.sendRedirect("/profile");
        } else {
            resp.sendRedirect("/login");
        }
    }
}

