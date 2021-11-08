/*
Project: Recipe Site
Assignment: 1
Author: Jack Robinson, Fatih Camgoz, Jong-In Yoon
Date: November 7th
Description: Model for ingredients, which is a part of a recipe
 */
package ca.gbc.comp3095.comp3095_assignment.recipe.ingredient;

import ca.gbc.comp3095.comp3095_assignment.model.BaseEntity;
import ca.gbc.comp3095.comp3095_assignment.recipe.Recipe;

import lombok.NonNull;

import javax.persistence.*;

@Entity
@Table(name = "ingredients")
public class Ingredient extends BaseEntity {

    @ManyToOne
    @JoinColumn(name= "recipe_id")
    private Recipe recipe;

    @Column
    private String name;

    @Column
    private String amount;

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
