package ca.gbc.comp3095.comp3095_assignment.user;

import ca.gbc.comp3095.comp3095_assignment.mealPlan.MealPlan;
import ca.gbc.comp3095.comp3095_assignment.model.BaseEntity;
import ca.gbc.comp3095.comp3095_assignment.recipe.Recipe;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @Column(unique = true)
    private String username;
    @Column
    private String password;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Set<Recipe> recipes = new HashSet<>();

    public Set<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(Set<Recipe> recipes) {
        this.recipes = recipes;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
