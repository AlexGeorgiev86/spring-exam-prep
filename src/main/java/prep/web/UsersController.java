package prep.web;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import prep.model.binding.UserRegisterBindingModel;
import prep.model.service.UserServiceModel;
import prep.service.UserService;

import javax.validation.Valid;

@Controller
@RequestMapping("/users")
public class UsersController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    public UsersController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register(@Valid @ModelAttribute("user") UserRegisterBindingModel userRegisterBindingModel,
                           BindingResult bindingResult) {
        return "register";
    }

    @PostMapping("/register")
    public ModelAndView registerPost(@Valid @ModelAttribute("user") UserRegisterBindingModel userRegisterBindingModel,
                                     BindingResult bindingResult, ModelAndView modelAndView, RedirectAttributes ra) {
        if (bindingResult.hasErrors()) {

            ra.addFlashAttribute("user", userRegisterBindingModel);
            modelAndView.setViewName("redirect:/users/register");
        } else {
            this.userService.register(this.modelMapper.map(userRegisterBindingModel, UserServiceModel.class));
            modelAndView.setViewName("redirect:login");
        }
        return modelAndView;
    }


}
