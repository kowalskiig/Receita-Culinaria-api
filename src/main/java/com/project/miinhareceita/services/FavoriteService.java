package com.project.miinhareceita.services;

import com.project.miinhareceita.dtos.FavoriteDTO;
import com.project.miinhareceita.entities.Favorite;
import com.project.miinhareceita.entities.User;
import com.project.miinhareceita.repositories.FavoriteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FavoriteService {
    @Autowired
    private FavoriteRepository repository;

    @Autowired
    private AuthService authService;

    @Transactional(readOnly = true)
    public List<FavoriteDTO> getFavoriteRecipesByUser() {
        User user = authService.authenticated();
        List<Favorite> result = repository.findFavoritesByUserId(user.getId());
        return result.stream().map(FavoriteDTO::new).toList();
    }
}
