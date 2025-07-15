package com.project.miinhareceita.dtos;

import com.project.miinhareceita.entities.Favorite;

public class FavoriteDTO {
    private RecipeFavoriteDTO recipeFavoriteDTO;

    public FavoriteDTO(){

    }
    public FavoriteDTO(Favorite favorite){
        recipeFavoriteDTO = new RecipeFavoriteDTO(favorite.getId().getRecipe());

    }

    public RecipeFavoriteDTO getRecipeFavoriteDTO() {
        return recipeFavoriteDTO;
    }
}
