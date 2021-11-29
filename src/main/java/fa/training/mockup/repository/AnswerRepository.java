package fa.training.mockup.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fa.training.mockup.entity.AnswerEntity;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AnswerRepository extends JpaRepository<AnswerEntity, Long>{

    AnswerEntity getByIdAndCorrectAnswerTrue(long id);

    @Query(value = "select * from answers  where question_id=?1", nativeQuery = true)
    List<AnswerEntity> findAnswerEntityByQuestionEntity_Id(long id);



}
