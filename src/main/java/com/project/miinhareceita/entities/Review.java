package com.project.miinhareceita.entities;


import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name ="tb_review")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private Integer nota;
    @Column(length = 555)
    private String comment ;

    private Instant data_review;


    @ManyToOne
    @JoinColumn(name = "recipe_id")
    private Recipe recipes;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;



}
