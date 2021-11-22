package fa.training.mockup.controller;

import fa.training.mockup.entity.AnswerEntity;
import fa.training.mockup.entity.QuestionEntity;
import fa.training.mockup.entity.QuizEntity;
import fa.training.mockup.service.AnswerService;
import fa.training.mockup.service.QuestionService;
import fa.training.mockup.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
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
			
			return "doQuiz";
		} else {
			model.addAttribute("MSG", "MSG NOT TRUE?");
			model.addAttribute("joinPassword", joinPassword);
			return "joinQuiz";
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
	public String checkQuiz(HttpServletRequest request, Model model) {
		int correct = 0;
		String[] questionIds = request.getParameterValues("questionId");

		for(String questionId : questionIds){
			if(answerService.getAnswerIdCorrect(Long.parseLong(request.getParameter("answer_"+questionId)))){
				correct++;
			}

		}
		int point = (10/questionIds.length)*correct;
		model.addAttribute("SCORE", point);
		return "score";
	}

}
