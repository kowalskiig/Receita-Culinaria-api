package com.project.miinhareceita.recipe.dto;


import com.project.miinhareceita.recipe.domain.Recipe;

import java.time.Instant;

public class RecipeFavoriteDTO {
        private Long id;
        private String title;
        private Instant publicationDate;
        private String urlImg;


        public RecipeFavoriteDTO(){}

        public RecipeFavoriteDTO(Recipe recipe) {
            id = recipe.getId();
            title = recipe.getTitle();
            publicationDate = recipe.getPublicationDate();
            urlImg = recipe.getUrlImg();
        }

        public Long getId() {
            return id;
        }

        public String getUrlImg() {
            return urlImg;
        }

        public Instant getPublicationDate() {
            return publicationDate;
        }

        public String getTitle() {
            return title;
        }
}
