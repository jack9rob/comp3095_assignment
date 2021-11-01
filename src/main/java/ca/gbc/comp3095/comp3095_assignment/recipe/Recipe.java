package ca.gbc.comp3095.comp3095_assignment.recipe;

import ca.gbc.comp3095.comp3095_assignment.mealPlan.MealPlan;
import ca.gbc.comp3095.comp3095_assignment.model.BaseEntity;
import ca.gbc.comp3095.comp3095_assignment.recipe.ingredient.Ingredient;
import ca.gbc.comp3095.comp3095_assignment.recipe.step.Step;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "recipes")
public class Recipe extends BaseEntity {

    @Column
    private String title;

    @Column
    private String description;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recipe")
    private Set<MealPlan> mealPlans = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recipe")
    private Set<FavouriteRecipe> favouriteRecipes = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recipe")
    private Set<Step> steps = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recipe")
    private Set<Ingredient> ingredients = new HashSet<>();

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<MealPlan> getMealPlans() {
        return mealPlans;
    }

    public void setMealPlans(Set<MealPlan> mealPlans) {
        this.mealPlans = mealPlans;
    }

    public Set<FavouriteRecipe> getFavouriteRecipes() {
        return favouriteRecipes;
    }

    public void setFavouriteRecipes(Set<FavouriteRecipe> favouriteRecipes) {
        this.favouriteRecipes = favouriteRecipes;
    }

    public Set<Step> getSteps() {
        return steps;
    }

    public void setSteps(Set<Step> steps) {
        this.steps = steps;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(Set<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }
}
