package prep.web;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
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
    public String add() {
        return "add-item";
    }

    @PostMapping("/add")
    public String addPost(@Valid @ModelAttribute("item")ItemAddBindingModel itemAddBindingModel, BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {
            return "redirect:add";
        } else {
            this.itemService.addItem(this.modelMapper.map(itemAddBindingModel, ItemServiceModel.class));
        }
        return "redirect:/";
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
