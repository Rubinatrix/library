package controller;

import dto.UserDTO;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;
import service.interfaces.UserService;
import utils.OnlineLibraryException;

import javax.annotation.Resource;
import javax.validation.Valid;

@Controller
public class RegistrationController {

    protected static Logger logger = Logger.getLogger("org/controller");

    @Resource
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String getRegistrationForm(WebRequest request, Model model) {
        logger.debug("Received request to show registration page");
        UserDTO userDTO = new UserDTO();
        model.addAttribute("user", userDTO);
        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registerUserAccount(@ModelAttribute("user") @Valid UserDTO userDTO, BindingResult result) {

        logger.debug("Received request to register new user");

        if (result.hasErrors()) {
            return "registration";
        }

        try {
            userService.registerNewUser(userDTO);
            return "login";
        } catch (OnlineLibraryException e) {
            result.reject("username", e.getMessage());
            return "registration";
        }
    }

}