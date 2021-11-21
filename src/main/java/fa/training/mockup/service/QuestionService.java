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
	
}
