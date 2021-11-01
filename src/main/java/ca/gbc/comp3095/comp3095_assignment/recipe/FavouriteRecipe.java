package ca.gbc.comp3095.comp3095_assignment.recipe;

import ca.gbc.comp3095.comp3095_assignment.model.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "favouriteRecipes")
public class FavouriteRecipe extends BaseEntity {

    @ManyToOne
    @JoinColumn(name= "recipe_id")
    private Recipe recipe;
}
