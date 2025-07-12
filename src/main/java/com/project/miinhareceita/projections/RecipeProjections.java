package com.project.miinhareceita.projections;

import java.time.Instant;

public interface RecipeProjections {
    Long getId();
    String getTitle();
    Integer getTimeMinutes();
    Instant getPublicationDate();
    String getUrlImg();
}
