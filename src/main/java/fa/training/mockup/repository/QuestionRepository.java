package fa.training.mockup.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fa.training.mockup.entity.QuestionEntity;

public interface QuestionRepository extends JpaRepository<QuestionEntity, Long>{

	

}
