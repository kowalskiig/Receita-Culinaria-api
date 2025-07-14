package com.project.miinhareceita.entities;


import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.*;

@Entity
@Table(name ="tb_recipe")
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(length = 555)
    private String shortDescription;

    private String instructions;
    private Integer timeMinutes;
    private Integer rendiment;
    private Instant publicationDate;
    private String urlImg;

    @ManyToMany
    @JoinTable(name = "tb_recipe_category",
            joinColumns = @JoinColumn(name = "recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    Set<Category> categories = new HashSet<>();

    @OneToMany(mappedBy = "recipes")
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "id.recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<RecipeIngredients> ingredients = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Recipe(){

    }

    public Recipe(User user, String urlImg, Instant publicationDate, Integer rendiment, String instructions, Integer timeMinutes, String shortDescription, String title, Long id) {
        this.user = user;
        this.urlImg = urlImg;
        this.publicationDate = publicationDate;
        this.rendiment = rendiment;
        this.instructions = instructions;
        this.timeMinutes = timeMinutes;
        this.shortDescription = shortDescription;
        this.title = title;
        this.id = id;
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

    public Set<RecipeIngredients> getIngredients() {
        return ingredients;
    }

    public List<Review> getReviews() {
        return reviews;
    }



    public Set<Category> getCategories() {
        return categories;
    }


    public String getUrlImg() {
        return urlImg;
    }

    public void setUrlImg(String urlImg) {
        this.urlImg = urlImg;
    }

    public Instant getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Instant publicationDate) {
        this.publicationDate = publicationDate;
    }

    public Integer getRendiment() {
        return rendiment;
    }

    public void setRendiment(Integer rendiment) {
        this.rendiment = rendiment;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public Integer getTimeMinutes() {
        return timeMinutes;
    }

    public void setTimeMinutes(Integer timeMinutes) {
        this.timeMinutes = timeMinutes;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Recipe recipe = (Recipe) o;
        return Objects.equals(id, recipe.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
