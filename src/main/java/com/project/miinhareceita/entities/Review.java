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
@Table(name ="tb_review")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    private Integer nota;
    @Column(length = 555)
    private String comment ;

    private Instant data_review;


    @ManyToOne
    @JoinColumn(name = "recipe_id")
    private Recipe recipes;



}
