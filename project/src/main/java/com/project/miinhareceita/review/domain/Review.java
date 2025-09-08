package com.project.miinhareceita.review.domain;


import com.project.miinhareceita.recipe.domain.Recipe;
import com.project.miinhareceita.domains.user.domain.User;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.time.Instant;


@Entity
@NoArgsConstructor
@Table(name ="tb_review")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private Integer nota;
    @Column(length = 555)
    private String comment ;
    private Instant dataReview;


    @ManyToOne
    @JoinColumn(name = "recipe_id")
    private Recipe recipes;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Review(Long id, User user, Recipe recipes, Instant dataReview, String comment, Integer nota) {
        this.id = id;
        this.user = user;
        this.recipes = recipes;
        this.dataReview = dataReview;
        this.comment = comment;
        this.nota = nota;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Recipe getRecipes() {
        return recipes;
    }

    public void setRecipes(Recipe recipes) {
        this.recipes = recipes;
    }

    public Instant getDataReview() {
        return dataReview;
    }

    public void setDataReview(Instant dataReview) {
        this.dataReview = dataReview;
    }

    public Integer getNota() {
        return nota;
    }

    public void setNota(Integer nota) {
        this.nota = nota;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
