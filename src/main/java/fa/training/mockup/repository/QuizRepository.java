package fa.training.mockup.repository;

import fa.training.mockup.entity.QuizEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuizRepository extends JpaRepository<QuizEntity, Long> {
    List<QuizEntity> findAllByCourseEntity_Id(Long id);
    List<QuizEntity> findAllByNameContains(String name);

}
