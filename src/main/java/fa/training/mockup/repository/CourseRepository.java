package fa.training.mockup.repository;

import fa.training.mockup.entity.CourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<CourseEntity, Long> {

    List<CourseEntity> findAllByCourseNameContains(String value);

}
