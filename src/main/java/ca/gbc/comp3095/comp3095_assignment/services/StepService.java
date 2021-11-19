package ca.gbc.comp3095.comp3095_assignment.services;

import ca.gbc.comp3095.comp3095_assignment.recipe.step.Step;

import java.util.List;
import java.util.Set;

public interface StepService extends CrudService<Step, Long>{
    List<Step> findByRecipeIdOrderByStepNumber(Long recipeId);
}
