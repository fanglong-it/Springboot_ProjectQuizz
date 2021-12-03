package fa.training.mockup.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fa.training.mockup.entity.QuestionEntity;
import fa.training.mockup.repository.QuestionRepository;

@Service
public class QuestionService {

	@Autowired
	private QuestionRepository repo;
	
	public List<QuestionEntity> findAllQuestion(){
		List<QuestionEntity> questionEntities = repo.findAll();
		return questionEntities;
	}

	public List<QuestionEntity> findAllQuestionByQuizId(Long id){
		List<QuestionEntity> questionEntityList = repo.findAllByQuizEntity_Id(id);
		return questionEntityList;
	}


	public void addQuestion(QuestionEntity questionEntity){
		repo.save(questionEntity);
	}

	public QuestionEntity getById(long id){
		QuestionEntity questionEntity = repo.getById(id);
		return questionEntity;
	}

	public void removeQuestion(Long id){
		repo.deleteById(id);
	}


	
}
