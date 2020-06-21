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
import prep.model.binding.UserLoginBindingModel;
import prep.model.binding.UserRegisterBindingModel;
import prep.model.service.UserServiceModel;
import prep.service.UserService;

import javax.servlet.http.HttpSession;
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
    public String login(@Valid @ModelAttribute("user") UserLoginBindingModel userLoginBindingModel, BindingResult bindingResult) {
        return "login";
    }

    @PostMapping("/login")
    public ModelAndView loginPost(@Valid @ModelAttribute("user") UserLoginBindingModel userLoginBindingModel,
                                  BindingResult bindingResult, HttpSession httpSession,
                                  ModelAndView modelAndView, RedirectAttributes ra) {
        if (bindingResult.hasErrors()) {

            ra.addFlashAttribute("user", userLoginBindingModel);
            modelAndView.setViewName("redirect:/users/login");
        } else {
            UserServiceModel userServiceModel = this.userService.findByUsername(userLoginBindingModel.getUsername());

            if (userServiceModel == null || !userServiceModel.getPassword().equals(userLoginBindingModel.getPassword())) {

                ra.addFlashAttribute("notFound", true);
                modelAndView.setViewName("redirect:login");
            } else {
                httpSession.setAttribute("sessionUser", userServiceModel);
                httpSession.setAttribute("id", userServiceModel.getId());
                modelAndView.setViewName("redirect:/");
            }


        }
        return modelAndView;
    }

    @GetMapping("/register")
    public String register(@Valid @ModelAttribute("user") UserRegisterBindingModel userRegisterBindingModel,
                           BindingResult bindingResult) {
        return "register";
    }

    @PostMapping("/register")
    public ModelAndView registerPost(@Valid @ModelAttribute("user") UserRegisterBindingModel userRegisterBindingModel,
                                     BindingResult bindingResult, ModelAndView modelAndView, RedirectAttributes ra) {
        if (bindingResult.hasErrors() || !userRegisterBindingModel.getConfirmPassword().equals(userRegisterBindingModel.getPassword())) {
            if(!userRegisterBindingModel.getConfirmPassword().equals(userRegisterBindingModel.getPassword())) {
                ra.addFlashAttribute("missMatch", true);
            }
            ra.addFlashAttribute("user", userRegisterBindingModel);
            modelAndView.setViewName("redirect:/users/register");
        } else {
            this.userService.register(this.modelMapper.map(userRegisterBindingModel, UserServiceModel.class));
            modelAndView.setViewName("redirect:login");
        }
        return modelAndView;
    }

    @GetMapping("/logout")
    public ModelAndView logout(HttpSession httpSession, ModelAndView modelAndView) {
        httpSession.invalidate();
        modelAndView.setViewName("redirect:/");
        return modelAndView;
    }


}
