package fa.training.mockup.controller;

import fa.training.mockup.dto.MyUser;
import fa.training.mockup.entity.*;
import fa.training.mockup.repository.GradeRepository;
import fa.training.mockup.repository.UserRepository;
import fa.training.mockup.service.AnswerService;
import fa.training.mockup.service.GradeService;
import fa.training.mockup.service.QuestionService;
import fa.training.mockup.service.QuizService;
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

		if(gradeService.checkExistInQuiz(myUser.getId(), quizEntity.getId())){
			model.addAttribute("MSG", "You have been done this quiz! View at GRADE!");
			return "joinQuiz";
		}else{
			if (joinPassword.equals(quizEntity.getPassword())) {
				model.addAttribute("MSG", "Join");

				// Do load Quiz Question And Answer code HERE
				HashMap<QuestionEntity, List<AnswerEntity>> listquiz = new HashMap<>();


				List<QuestionEntity> questionEntities = questService.findAllQuestion();
				List<AnswerEntity> answerEntity = answerService.findAllAnswer();

				for (QuestionEntity quest : questionEntities) {
					long questId = quest.getId();
					List<AnswerEntity> tmpAns = new ArrayList<>();
					for (AnswerEntity ans : answerEntity) {
						if(ans.getQuestionEntity().getId() == questId) {
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

		for(String questionId : questionIds){
			if(answerService.getAnswerIdCorrect(Long.parseLong(request.getParameter("answer_"+questionId)))){
				correct++;
			}

		}
		int point = (10/questionIds.length)*correct;
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

}
