package fa.training.mockup.repository;

import fa.training.mockup.entity.GradeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GradeRepository extends JpaRepository<GradeEntity, Long> {

    @Query(value = "insert into Grade (point,quiz_id,user_id) VALUES(:point,:quizId,:userId)", nativeQuery = true)
    void insertGrade(@Param("point") Integer point, @Param("quizId") Long quizId, @Param("userId") Long userId);


    GradeEntity findGradeEntitiesByUserEntity_IdAndQuizGradeEntity_Id(Long userId, Long quizId);

    @Query(value = "select * from grade g where g.user_id = ?1 and  g.quiz_id = ?2", nativeQuery = true)
    GradeEntity getByUserEntity_IdAndQuizGradeEntity_Id(Long userId,Long quizId);


    List<GradeEntity> findGradeEntitiesByUserEntity_Id(long userId);


}
