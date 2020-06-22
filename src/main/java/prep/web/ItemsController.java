package prep.web;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import prep.model.binding.ItemAddBindingModel;
import prep.model.service.ItemServiceModel;
import prep.service.ItemService;

import javax.validation.Valid;

@Controller
@RequestMapping("/items")
public class ItemsController {

    private final ItemService itemService;
    private final ModelMapper modelMapper;

    public ItemsController(ItemService itemService, ModelMapper modelMapper) {
        this.itemService = itemService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/add")
    public String add(Model model) {

        if(!model.containsAttribute("item")) {
            model.addAttribute("item", new ItemAddBindingModel());
        }
        return "add-item";
    }

    @PostMapping("/add")
    public ModelAndView addPost(@Valid @ModelAttribute("item")ItemAddBindingModel itemAddBindingModel, BindingResult bindingResult,
                                ModelAndView modelAndView, RedirectAttributes ra) {

        if(bindingResult.hasErrors()) {
            ra.addFlashAttribute("item", itemAddBindingModel);
            ra.addFlashAttribute("org.springframework.validation.BindingResult.item", bindingResult);
            modelAndView.setViewName("redirect:/items/add");
        } else {
            this.itemService.addItem(this.modelMapper.map(itemAddBindingModel, ItemServiceModel.class));
            modelAndView.setViewName("redirect:/");
        }
        return modelAndView;
    }

    @GetMapping("/details")
    public ModelAndView details(@RequestParam("id") String id, ModelAndView modelAndView) {
        modelAndView.addObject("item", this.itemService.findById(id));
        modelAndView.setViewName("details-item");
        return  modelAndView;
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") String id) {
        this.itemService.delete(id);
        return "redirect:/";
    }

}
