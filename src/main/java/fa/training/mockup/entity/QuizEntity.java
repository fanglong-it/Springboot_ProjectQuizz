/*
 *
 *
 * Project ProductManager
 * Copyright (C) $year by Fanglong-it. All Rights Reserved.
 * For more information : Fang.longpc@gmail.com
 * Example project exist at : https://github.com/fanglong-it/
 * 11/11/21, 2:15 PM
 *
 *
 */

package fa.training.mockup.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "Quiz")
public class QuizEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;
    @Column(name = "content")
    private String content;

    @Column(name = "password")
    private String password;

    @ManyToOne
    @JoinColumn(name = "Course_id")
    private CourseEntity courseEntity;

    @OneToMany(mappedBy = "quizEntity")
    private Set<QuestionEntity> questionEntities;

    @OneToMany(mappedBy = "quizGradeEntity")
    private Set<GradeEntity> gradeEntities;


    public Set<GradeEntity> getGradeEntities() {
        return gradeEntities;
    }

    public void setGradeEntities(Set<GradeEntity> gradeEntities) {
        this.gradeEntities = gradeEntities;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public CourseEntity getCourseEntity() {
        return courseEntity;
    }

    public void setCourseEntity(CourseEntity courseEntity) {
        this.courseEntity = courseEntity;
    }

    public Set<QuestionEntity> getQuestionEntities() {
        return questionEntities;
    }

    public void setQuestionEntities(Set<QuestionEntity> questionEntities) {
        this.questionEntities = questionEntities;
    }
}
