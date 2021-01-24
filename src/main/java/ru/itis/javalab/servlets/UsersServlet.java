package ru.itis.javalab.servlets;

import org.springframework.security.crypto.password.PasswordEncoder;
import ru.itis.javalab.models.User;
import ru.itis.javalab.repositories.UsersRepository;
import ru.itis.javalab.services.UsersService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;


@WebServlet("/users")
public class UsersServlet extends HttpServlet {

    public UsersService usersService;
    UsersRepository usersRepository;
    private PasswordEncoder passwordEncoder;


    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext servletContext = config.getServletContext();
        this.usersService = (UsersService) servletContext.getAttribute("usersService");
        this.passwordEncoder = (PasswordEncoder) servletContext.getAttribute("passwordEncoder");

    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getSession(false) != null) {
            System.out.println(request.getSession(false).getAttribute("Hello"));
        } else {
            HttpSession session = request.getSession();
            session.setAttribute("Hello", "hello from server!");
        }

        //        System.out.println(usersService.getAllUsers());
//        System.out.println(usersRepository.findAllByAge(26));
//        super.doGet(request, response);
        List<User> users = usersService.getAllUsers();
        users.add(User.builder()
                .id(1L)
                .firstName("Marsel")
                .lastName("Sidikov")
                .age(26)
                .build());
        users.add(
                User.builder()
                        .id(2L)
                        .firstName("Rasim")
                        .lastName("Garipov")
                        .age(19)
                        .build());
        request.setAttribute("usersForJsp", users);
        request.getRequestDispatcher("/jsp/users.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String password = request.getParameter("password");
        String hashPassword = passwordEncoder.encode(password);
        System.out.println(hashPassword);
        System.out.println(passwordEncoder.matches("qwerty 007", hashPassword));
        String color = request.getParameter("color");
        Cookie cookie = new Cookie("color", color);
        cookie.setMaxAge(60 * 60 * 24);
        response.addCookie(cookie);
        response.sendRedirect("/users");
    }
}
