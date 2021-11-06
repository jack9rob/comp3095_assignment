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

    // get meal in week
    //List<MealPlan> getByWeek(Long userId, String fromDate, String toDate);

    //List<MealPlan> getByUser(Long userId);

    void save(MealPlan mealPlan);

}
