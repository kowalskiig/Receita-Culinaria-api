package com.project.miinhareceita.recipe.dto;


import com.project.miinhareceita.recipe.domain.Recipe;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@NoArgsConstructor
@Getter
public class RecipeFavoriteDTO {
        private Long id;
        private String title;
        private Instant publicationDate;
        private String urlImg;




        public RecipeFavoriteDTO(Recipe recipe) {
            id = recipe.getId();
            title = recipe.getTitle();
            publicationDate = recipe.getPublicationDate();
            urlImg = recipe.getUrlImg();
        }


}
