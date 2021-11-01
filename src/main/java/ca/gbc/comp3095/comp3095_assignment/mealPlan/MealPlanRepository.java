package ca.gbc.comp3095.comp3095_assignment.mealPlan;

import ca.gbc.comp3095.comp3095_assignment.recipe.Recipe;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface MealPlanRepository extends Repository<MealPlan, Long> {

    @Query("select m from MealPlan m")
    List<MealPlan> getAll();

    void save(MealPlan mealPlan);

}
