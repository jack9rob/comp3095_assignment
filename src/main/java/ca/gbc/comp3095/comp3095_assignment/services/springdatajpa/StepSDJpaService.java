package ca.gbc.comp3095.comp3095_assignment.services.springdatajpa;

import ca.gbc.comp3095.comp3095_assignment.recipe.step.Step;
import ca.gbc.comp3095.comp3095_assignment.services.StepService;
import ca.gbc.comp3095.comp3095_assignment.services.repositories.StepRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class StepSDJpaService implements StepService {

    private final StepRepository stepRepository;

    public StepSDJpaService(StepRepository stepRepository) {
        this.stepRepository = stepRepository;
    }

    @Override
    public Set<Step> findAll() {
        Set<Step> steps = new HashSet<>();
        stepRepository.findAll().forEach(steps::add);
        return steps;
    }

    @Override
    public Step findById(Long id) {
        return stepRepository.findById(id).orElse(null);
    }

    @Override
    public Step save(Step object) {
        return stepRepository.save(object);
    }

    @Override
    public void delete(Step object) {
        stepRepository.delete(object);
    }

    @Override
    public void deleteById(Long id) {
        stepRepository.deleteById(id);
    }

    @Override
    public List<Step> findByRecipeIdOrderByStepNumber(Long recipeId) {
        return stepRepository.findByRecipeIdOrderByRecipeId(recipeId);
    }
}
