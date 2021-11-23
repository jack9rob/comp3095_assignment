package ca.gbc.comp3095.comp3095_assignment.services.springdatajpa;

import ca.gbc.comp3095.comp3095_assignment.mealPlan.MealPlan;
import ca.gbc.comp3095.comp3095_assignment.recipe.Recipe;
import ca.gbc.comp3095.comp3095_assignment.services.MealPlanService;
import ca.gbc.comp3095.comp3095_assignment.services.repositories.MealPlanRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class MealPlanSDJpaService implements MealPlanService {

    private final MealPlanRepository mealPlanRepository;

    public MealPlanSDJpaService(MealPlanRepository mealPlanRepository) {
        this.mealPlanRepository = mealPlanRepository;
    }

    @Override
    public Set<MealPlan> findAll() {
        Set<MealPlan> mealPlans = new HashSet<>();
        mealPlanRepository.findAll().forEach(mealPlans::add);
        return mealPlans;
    }

    @Override
    public MealPlan findById(Long id) {
        return mealPlanRepository.findById(id).orElse(null);
    }

    @Override
    public MealPlan save(MealPlan object) {
        return mealPlanRepository.save(object);
    }

    @Override
    public void delete(MealPlan object) {
        mealPlanRepository.delete(object);
    }

    @Override
    public void deleteById(Long id) {
        mealPlanRepository.deleteById(id);
    }
}
