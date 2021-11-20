package fa.training.mockup.controller;

import fa.training.mockup.entity.CourseEntity;
import fa.training.mockup.entity.QuizEntity;
import fa.training.mockup.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("quiz")
public class QuizController {

    @Autowired
    private QuizService quizService;
    @GetMapping("/view/{id}")
    public String viewQuiz(@PathVariable("id")Long id, Model model){

        List<QuizEntity> quizEntityList = quizService.findALlQuizEntitiesById(id);
        model.addAttribute("LIST_QUIZ", quizEntityList);
        return "quiz";
    }

    @GetMapping("/joinPage/{id}")
    public String joinPage(@PathVariable("id")Long id, Model model){
        QuizEntity quizEntity = quizService.getQuizEntityById(id);
        model.addAttribute("QUIZ_ENTITY", quizEntity);
        return "joinQuiz";
    }


    @PostMapping(value = "attemp")
    public String joinQuiz(@ModelAttribute("QUIZ_ENTITY") QuizEntity quizEntity, Model model,
                           @Param("joinPassword") String joinPassword){
       if(joinPassword.equals(quizEntity.getPassword())){
           model.addAttribute("MSG", "Join");

           //Do load Quiz Question And Answer code HERE


           return "doQuiz";
       }else{
           model.addAttribute("MSG", "MSG NOT TRUE?");
           model.addAttribute("joinPassword", joinPassword);
           return "joinQuiz";
       }

    }

    @GetMapping("/search")
    public String searchQuiz(@Param("keyword") String keyword, Model model){
        List<QuizEntity> quizEntityList = null;

        if(keyword != null || !keyword.equals("")){
            quizEntityList = quizService.findAllQuizByName(keyword);
        }else{
            quizEntityList = quizService.findALlQuizEntities();
        }
        model.addAttribute("LIST_QUIZ", quizEntityList);
        model.addAttribute("keyword", keyword);
        return "quiz";
    }


}
