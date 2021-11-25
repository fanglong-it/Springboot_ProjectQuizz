package fa.training.mockup.service;

import fa.training.mockup.entity.GradeEntity;
import fa.training.mockup.repository.GradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GradeService {
    @Autowired
    private GradeRepository gradeRepository;

    public void saveUserGrade(int point, Long quizId, Long userId){
        gradeRepository.insertGrade(point, quizId, userId);
    }

    public void save(GradeEntity gradeEntity){
        gradeRepository.save(gradeEntity);
    }

    public boolean checkExistInQuiz(Long userId, Long quizId){
        if(gradeRepository.getByUserEntity_IdAndQuizGradeEntity_Id(userId, quizId) != null){
            return true;
        }
        return false;
    }

    public List<GradeEntity> listGradeByUserId(Long userId){
        List<GradeEntity> gradeEntityList = gradeRepository.findGradeEntitiesByUserEntity_Id(userId);
        return gradeEntityList;
    }


}
