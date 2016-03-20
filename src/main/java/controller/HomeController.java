package controller;

import domain.Book;
import domain.User;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import service.interfaces.BookService;

import javax.annotation.Resource;
import java.security.Principal;
import java.util.Collections;
import java.util.List;

@Controller
public class HomeController {

    protected static Logger logger = Logger.getLogger("org/controller");

    @Value("${guest_name}")
    private String guestName;

    @Resource
    @Autowired
    private BookService bookService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getMainPage(Model model, Principal principal) {
        logger.debug("Received request to show main page");
        if (principal != null){
            model.addAttribute("username", principal.getName());
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            List<Book> userBooks = bookService.getFavoritesByUser(user);
            Collections.sort(userBooks);
            model.addAttribute("userBooks", userBooks);
        } else {
            model.addAttribute("username", guestName);
        }

        return "home";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String getLoginPage(Model model) {
        logger.debug("Received request to show login page");
        return "login";
    }

}