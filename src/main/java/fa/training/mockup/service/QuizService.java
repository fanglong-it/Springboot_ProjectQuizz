package fa.training.mockup.service;

import fa.training.mockup.entity.QuizEntity;
import fa.training.mockup.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizService {
    @Autowired
    private QuizRepository quizRepository;

    public List<QuizEntity> findALlQuizEntitiesById(Long id){
        List<QuizEntity> quizEntityList = quizRepository.findAllByCourseEntity_Id(id);
        return quizEntityList;
    }

    public List<QuizEntity> findALlQuizEntities(){
        List<QuizEntity> quizEntityList = quizRepository.findAll();
        return quizEntityList;
    }


    public QuizEntity getQuizEntityById(Long id){
        QuizEntity quizEntity = quizRepository.getById(id);
        return quizEntity;
    }


    public List<QuizEntity> findAllQuizByName(String name){
        List<QuizEntity> quizEntityList = quizRepository.findAllByNameContains(name);
        return quizEntityList;
    }






}
