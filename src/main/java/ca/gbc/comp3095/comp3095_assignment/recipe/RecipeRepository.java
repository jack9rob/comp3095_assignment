package ca.gbc.comp3095.comp3095_assignment.recipe;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Component
public interface RecipeRepository extends Repository<Recipe, Long> {

    @Query("select r from Recipe r")
    List<Recipe> getAll();

    void save(Recipe recipe);

}
