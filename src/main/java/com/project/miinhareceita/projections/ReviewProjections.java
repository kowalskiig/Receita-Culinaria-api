package com.project.miinhareceita.projections;


import java.time.OffsetDateTime;

public interface ReviewProjections {
     Long getId();
     Integer getNota();
    String getComment();
    OffsetDateTime getDataReview();
    Long getRecipe_Id();
    Long getUser_Id();
}
