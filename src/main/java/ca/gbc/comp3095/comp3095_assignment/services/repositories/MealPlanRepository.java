package ca.gbc.comp3095.comp3095_assignment.services.repositories;

import ca.gbc.comp3095.comp3095_assignment.mealPlan.MealPlan;
import org.springframework.data.repository.CrudRepository;

public interface MealPlanRepository extends CrudRepository<MealPlan, Long> {
}
