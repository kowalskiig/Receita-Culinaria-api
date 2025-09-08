package com.project.miinhareceita.domains.favorite.dto;

import com.project.miinhareceita.domains.favorite.domain.Favorite;
import com.project.miinhareceita.domains.recipe.dto.RecipeFavoriteDTO;
import lombok.Getter;

@Getter
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
