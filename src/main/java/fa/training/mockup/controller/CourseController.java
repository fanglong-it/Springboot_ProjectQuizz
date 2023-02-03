package fa.training.mockup.controller;

import fa.training.mockup.entity.CourseEntity;
import fa.training.mockup.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("course")
public class CourseController {


    @Autowired
    private CourseService service;


    @GetMapping("/search")
    public String searchCourse(@Param("keyword") String keyword, Model model){
        List<CourseEntity> courseEntityList = null;

        if(keyword!=null || !keyword.equals("")){
            courseEntityList = service.findAllByCourseName(keyword);
        }else{
            courseEntityList = service.findAll();
        }
        model.addAttribute("LIST_COURSE", courseEntityList);
        model.addAttribute("keyword", keyword);
        return "index";

    }

    @GetMapping("/")
    public String homePage(Model model){

        // ModelAndView modelAndView = new ModelAndView("index");
        List<CourseEntity> courseEntities = service.findAll();
        model.addAttribute("LIST_COURSE", courseEntities);
        return "index";

    }

    @GetMapping("/createPage")
    public String createPage(Model model){
            CourseEntity courseEntity = new CourseEntity();

            model.addAttribute("COURSE", courseEntity);
            return "createCourse";
    }

    @PostMapping("/create")
    public String createCourse(@ModelAttribute("COURSE") CourseEntity courseEntity, Model model){
        service.saveCourse(courseEntity);
        model.addAttribute("MSG", "Add success!");
        return "redirect:/";
    }



}
