package ca.gbc.comp3095.comp3095_assignment.services.repositories;

import ca.gbc.comp3095.comp3095_assignment.recipe.step.Step;
import ca.gbc.comp3095.comp3095_assignment.services.CrudService;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Set;

public interface StepRepository extends CrudRepository<Step, Long> {
    List<Step> findByRecipeIdOrderByRecipeId(Long recipeId);
}
