package fa.training.mockup.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fa.training.mockup.entity.QuestionEntity;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuestionRepository extends JpaRepository<QuestionEntity, Long>{

	@Query(value = "select * from questions where quiz_id = ?1", nativeQuery = true)
	List<QuestionEntity> findAllByQuizEntity_Id(Long id);

	QuestionEntity getById(long id);

}
