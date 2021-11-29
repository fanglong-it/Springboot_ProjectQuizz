package fa.training.mockup.controller;

import fa.training.mockup.dto.MyUser;
import fa.training.mockup.entity.*;
import fa.training.mockup.repository.GradeRepository;
import fa.training.mockup.repository.UserRepository;
import fa.training.mockup.service.*;
import org.dom4j.rule.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("quiz")
public class QuizController {
    @Autowired
    private CourseService courseService;

    @Autowired
    private QuizService quizService;

    @Autowired
    private AnswerService answerService;

    @Autowired
    private QuestionService questService;

    @Autowired
    private GradeService gradeService;

    @Autowired
    private GradeRepository gradeRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/view/{id}")
    public String viewQuiz(@PathVariable("id") Long id, Model model) {

        List<QuizEntity> quizEntityList = quizService.findALlQuizEntitiesById(id);
        model.addAttribute("LIST_QUIZ", quizEntityList);
        model.addAttribute("QUIZ_ID", id);
        return "quiz";
    }

    @GetMapping("/joinPage/{id}")
    public String joinPage(@PathVariable("id") Long id, Model model) {
        QuizEntity quizEntity = quizService.getQuizEntityById(id);
        model.addAttribute("QUIZ_ENTITY", quizEntity);
        return "joinQuiz";
    }

    @PostMapping(value = "attempt")
    public String joinQuiz(@ModelAttribute("QUIZ_ENTITY") QuizEntity quizEntity, Model model,
                           @Param("joinPassword") String joinPassword, ModelMap modelMap) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MyUser myUser = (MyUser) authentication.getPrincipal();

        if (gradeService.checkExistInQuiz(myUser.getId(), quizEntity.getId())) {
            model.addAttribute("MSG", "You have been done this quiz! View at GRADE!");
            return "joinQuiz";
        } else {
            if (joinPassword.equals(quizEntity.getPassword())) {
                model.addAttribute("MSG", "Join");

                // Do load Quiz Question And Answer code HERE
                HashMap<QuestionEntity, List<AnswerEntity>> listquiz = new HashMap<>();


                List<QuestionEntity> questionEntities = questService.findAllQuestionByQuizId(quizEntity.getId());
                List<AnswerEntity> answerEntity = answerService.findAllAnswer();

                for (QuestionEntity quest : questionEntities) {
                    long questId = quest.getId();
                    List<AnswerEntity> tmpAns = new ArrayList<>();
                    for (AnswerEntity ans : answerEntity) {
                        if (ans.getQuestionEntity().getId() == questId) {
                            tmpAns.add(ans);
                        }
                    }
                    listquiz.put(quest, tmpAns);
                }
                modelMap.put("LIST_QUESTIONS", listquiz);
                model.addAttribute("QUIZ_ID", quizEntity.getId());

                return "doQuiz";

            } else {
                model.addAttribute("MSG", "MSG NOT TRUE?");
                model.addAttribute("joinPassword", joinPassword);
                return "joinQuiz";
            }
        }
    }

    @GetMapping("/search")
    public String searchQuiz(@Param("keyword") String keyword, Model model) {
        List<QuizEntity> quizEntityList = null;

        if (keyword != null || !keyword.equals("")) {
            quizEntityList = quizService.findAllQuizByName(keyword);
        } else {
            quizEntityList = quizService.findALlQuizEntities();
        }
        model.addAttribute("LIST_QUIZ", quizEntityList);
        model.addAttribute("keyword", keyword);
        return "quiz";
    }


    @PostMapping(value = "check")
    public String checkQuiz(HttpServletRequest request, Model model, @RequestParam("quizId") Long quizId) {
        int correct = 0;
        String[] questionIds = request.getParameterValues("questionId");

        for (String questionId : questionIds) {
            if (answerService.getAnswerIdCorrect(Long.parseLong(request.getParameter("answer_" + questionId)))) {
                correct++;
            }

        }
        int point = (10 / questionIds.length) * correct;
        model.addAttribute("SCORE", point);

        //Save score here
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MyUser myUser = (MyUser) authentication.getPrincipal();
        UserEntity userEntity = userRepository.findUserEntityByEmail(myUser.getEmail());
        QuizEntity quizEntity = quizService.getQuizEntityById(quizId);

        GradeEntity gradeEntity = new GradeEntity();
        gradeEntity.setUserEntity(userEntity);
        gradeEntity.setPoint(point);
        gradeEntity.setQuizGradeEntity(quizEntity);
        gradeService.save(gradeEntity);
        return "score";
    }

    @GetMapping("/createPage/{id}")
    public String createPage(@PathVariable("id") Long id, Model model) {
        QuizEntity quizEntity = new QuizEntity();
        model.addAttribute("QUIZ", quizEntity);
        CourseEntity courseEntity = courseService.getCourseById(id);
        model.addAttribute("COURSE", courseEntity);
        return "createQuiz";

    }

    @PostMapping("/createQuiz")
    public String createQuiz(@ModelAttribute("QUIZ") QuizEntity quiz, @RequestParam("course_id") Long courseId,
                             Model model) {
        CourseEntity courseEntity = courseService.getCourseById(courseId);

        quiz.setCourseEntity(courseEntity);
        quizService.saveQuizEntity(quiz);
        model.addAttribute("MSG", "Create Quiz Success!");
        return "redirect:/quiz/view/" + courseEntity.getId();


    }

    @GetMapping("/editQuiz/{id}")
    public String editQuiz(@PathVariable("id") Long id, Model model){
        List<QuestionEntity> questionEntityList = questService.findAllQuestionByQuizId(id);
        model.addAttribute("LIST_QUESTIONS", questionEntityList);
        model.addAttribute("QUIZ_ID", id);
        return "questions";
    }

    @GetMapping("/viewAnswer/{id}")
    public String viewAnswer(@PathVariable("id") Long id, Model model){
        List<AnswerEntity> answerEntityList = answerService.findAllByQuestionId(id);
        model.addAttribute("LIST_ANSWERS", answerEntityList);
        model.addAttribute("QUESTION_ID", id);
        return "answers";
    }

    @GetMapping("/createQuestion/{id}")
    public String createQuestionPage(@PathVariable("id") Long id, Model model){
        QuestionEntity questionEntity = new QuestionEntity();
        model.addAttribute("QUESTION", questionEntity);
        QuizEntity quizEntity = quizService.getQuizEntityById(id);
        model.addAttribute("QUIZ", quizEntity);
        return "createQuestions";
    }

    @PostMapping("/createQuestion")
    public String createQuestion(@ModelAttribute("QUESTION") QuestionEntity questionEntity, @RequestParam("quiz_id") Long id){

        QuizEntity quizEntity = quizService.getQuizEntityById(id);
        questionEntity.setQuizEntity(quizEntity);
        questService.addQuestion(questionEntity);
        return "redirect:/quiz/editQuiz/"+id;

    }


    @GetMapping("/createAnswer/{id}")
    public String createAnswerPage(@PathVariable("id")Long id, Model model){
        AnswerEntity answerEntity = new AnswerEntity();
        QuestionEntity questionEntity = questService.getById(id);
        model.addAttribute("ANSWER", answerEntity);
        model.addAttribute("QUESTION", questionEntity);
        return "createAnswer";
    }

    @PostMapping("/createAnswer")
    public String createAnswer(@ModelAttribute("ANSWER") AnswerEntity answerEntity, @RequestParam("question_id") Long id,
                               @RequestParam("correctAnswer")String correct){
        QuestionEntity questionEntity = questService.getById(id);
        answerEntity.setQuestionEntity(questionEntity);
        if(correct.equals("True")){
            answerEntity.setCorrectAnswer(true);
        }else{
            answerEntity.setCorrectAnswer(false);
        }
        answerService.saveAnswer(answerEntity);
        return "redirect:/quiz/viewAnswer/"+id;
    }

    @GetMapping("/deleteAnswer/{id}/{question_id}")
    public String deleteAnswer(@PathVariable("id") Long id,@PathVariable("question_id") Long questionId, Model model){
        answerService.removeAnswer(id);

        return "redirect:/quiz/viewAnswer/"+questionId;
    }

    @GetMapping("/viewQuiz/{id}")
    public String viewQuiz(@PathVariable("id")Long id, Model model, ModelMap modelMap){

        HashMap<QuestionEntity, List<AnswerEntity>> listHashMap = new HashMap<>();
        List<QuestionEntity> questionEntities = questService.findAllQuestionByQuizId(id);
        List<AnswerEntity> answerEntity = answerService.findAllAnswer();

        for (QuestionEntity quest : questionEntities) {
            long questId = quest.getId();
            List<AnswerEntity> tmpAns = new ArrayList<>();
            for (AnswerEntity ans : answerEntity) {
                if (ans.getQuestionEntity().getId() == questId) {
                    tmpAns.add(ans);
                }
            }
            listHashMap.put(quest, tmpAns);
        }
        modelMap.put("LIST_QUESTIONS", listHashMap);
        return "viewQuiz";

    }



}
