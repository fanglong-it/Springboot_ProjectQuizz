package fa.training.mockup.service;

import fa.training.mockup.entity.CourseEntity;
import fa.training.mockup.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    @Autowired
    private CourseRepository repo;

    public List<CourseEntity> findAllByCourseName(String value) {
        List<CourseEntity> courseEntities = repo.findAllByCourseNameContains(value);
        return courseEntities;
    }

    public List<CourseEntity> findAll() {
        List<CourseEntity> courseEntities = repo.findAll();
        return courseEntities;
    }
}
