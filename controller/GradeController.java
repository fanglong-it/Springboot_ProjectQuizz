package fa.training.mockup.controller;

import fa.training.mockup.dto.MyUser;
import fa.training.mockup.entity.GradeEntity;
import fa.training.mockup.service.GradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("grade")
public class GradeController {

    @Autowired
    private GradeService gradeService;
    @GetMapping("/view")
    public String viewGrade(Model model){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MyUser myUser = (MyUser) authentication.getPrincipal();

        List<GradeEntity> gradeEntityList = gradeService.listGradeByUserId(myUser.getId());
        model.addAttribute("LIST_GRADE", gradeEntityList);

        return "grade";



    }
}
