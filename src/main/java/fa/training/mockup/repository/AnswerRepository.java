package fa.training.mockup.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fa.training.mockup.entity.AnswerEntity;

public interface AnswerRepository extends JpaRepository<AnswerEntity, Long>{

}
