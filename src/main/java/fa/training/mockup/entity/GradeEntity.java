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

@Entity
@Table(name = "Grade")
public class GradeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "point")
    private int point;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @ManyToOne
    @JoinColumn(name = "quiz_id")
    private QuizEntity quizGradeEntity;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public QuizEntity getQuizGradeEntity() {
        return quizGradeEntity;
    }

    public void setQuizGradeEntity(QuizEntity quizGradeEntity) {
        this.quizGradeEntity = quizGradeEntity;
    }
}
