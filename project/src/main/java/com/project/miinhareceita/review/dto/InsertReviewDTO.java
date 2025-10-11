package com.project.miinhareceita.domains.review.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@NoArgsConstructor
public class InsertReviewDTO implements ReviewValidDTO {

    @Min(value = 1, message = "A nota deve ser no mínimo 1")
    @Max(value = 5, message = "A nota deve ser no máximo 5")
    @NotNull(message = "A nota não deve ser nula")
    private Integer nota;

    @NotBlank(message = "A nota não deve ser nula")
    private String comment;

    public InsertReviewDTO(Integer nota, String comment) {
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
