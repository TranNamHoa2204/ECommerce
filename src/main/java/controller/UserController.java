package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import ch.qos.logback.core.model.Model;
import entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import service.UserService;


@Controller
public class UserController {
    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping(params = "action=login")
    public String showLogin() {
        return "login";
    }

    @PostMapping(params = "action=login")
    public String login(HttpServletRequest req, Model model, HttpSession session){
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        try{
            User user = userService.dangNhap(email, password);
            session.setAttribute("currentUser", user);
            return "ADMIN".equals(user.getRole()) ? "redirect:/admin" : "redirect:/";
        }catch(RuntimeException e){
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("email", email);
            return "login";
        }
    }
    
}
