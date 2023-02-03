package fa.training.mockup.service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fa.training.mockup.entity.AnswerEntity;
import fa.training.mockup.repository.AnswerRepository;

@Service
public class AnswerService {


	@Autowired
	private AnswerRepository answerRepository;
	
	public List<AnswerEntity> findAllAnswer(){
		List<AnswerEntity> answerEntities = answerRepository.findAll();
		return answerEntities;
	}

	public boolean getAnswerIdCorrect(Long answerId){
		AnswerEntity answerEntity = answerRepository.getByIdAndCorrectAnswerTrue(answerId);
		if(answerEntity != null){
			System.out.println("answerId = " + answerEntity.getId());
			return true;
		}
		return false;
	}


	public List<AnswerEntity> findAllByQuestionId(Long id){
		List<AnswerEntity> answerEntityList = answerRepository.findAnswerEntityByQuestionEntity_Id(id);
		return  answerEntityList;
	}

	public void saveAnswer(AnswerEntity answerEntity){
		answerRepository.save(answerEntity);
	}


	public void removeAnswer(Long id){
		answerRepository.deleteById(id);
	}
	
}
