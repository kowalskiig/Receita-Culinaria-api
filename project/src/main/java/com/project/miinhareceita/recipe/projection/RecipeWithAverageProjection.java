package com.project.miinhareceita.domains.recipe.projection;

import java.time.OffsetDateTime;

public interface RecipeWithAverageProjection {

    Long getId();
    String getTitle();
    String getShortDescription();
    Integer getTimeMinutes();
    OffsetDateTime publicationDate();
    Double getAverageRating(); // média da nota
}

