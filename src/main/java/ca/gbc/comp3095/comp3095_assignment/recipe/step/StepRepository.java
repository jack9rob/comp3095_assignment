/*
Project: Recipe Site
Assignment: 1
Author: Jack Robinson, Fatih Camgoz, Jong-In Yoon
Date: November 7th
Description: repo to select each step in order by step number, and save a step to a recipe
 */
package ca.gbc.comp3095.comp3095_assignment.recipe.step;

import ca.gbc.comp3095.comp3095_assignment.recipe.Recipe;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface StepRepository extends Repository<Step, Long> {

    @Query("select step from Step step WHERE step.recipe.id = :recipeId order by step.stepNumber")
    List<Step> getAllByRecipeInOrder(Long recipeId);

    @Query("SELECT step from Step step WHERE step.recipe.id = :recipeId order by step.stepNumber")
    List<Step> getStepOrderStepNumber(Long recipeId);

    void save(Step step);
}
