package com.project.miinhareceita.review.dto;

import com.project.miinhareceita.review.domain.Review;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UpdateReviewDTO implements ReviewValidDTO{

    @Min(value = 1, message = "A nota deve ser no mínimo 1")
    @Max(value = 5, message = "A nota deve ser no máximo 5")
    private Integer nota;

    private String comment;

    public UpdateReviewDTO(Integer nota, String comment) {
        this.nota = nota;
        this.comment = comment;

    }

    @Override
    public Integer getNota() {
        return nota;
    }

    @Override
    public String getComment() {
        return comment;
    }
}
