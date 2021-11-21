package fa.training.mockup.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fa.training.mockup.entity.AnswerEntity;
import fa.training.mockup.repository.AnswerRepository;

@Service
public class AnswerService {

	@Autowired
	private AnswerRepository repo;
	
	public List<AnswerEntity> findAllAnswer(){
		List<AnswerEntity> answerEntities = repo.findAll();
		return answerEntities;
	}
	
}
