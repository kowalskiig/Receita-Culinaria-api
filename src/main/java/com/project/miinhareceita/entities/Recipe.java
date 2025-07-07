package com.project.miinhareceita.entities;


import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name ="tb_recipe")
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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




}
