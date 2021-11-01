package ca.gbc.comp3095.comp3095_assignment.mealPlan;

import ca.gbc.comp3095.comp3095_assignment.model.BaseEntity;
import ca.gbc.comp3095.comp3095_assignment.recipe.Recipe;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "mealPlans")
public class MealPlan extends BaseEntity {

    @ManyToOne
    @JoinColumn(name= "recipe_id")
    private Recipe recipe;

    @Column
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }
}
