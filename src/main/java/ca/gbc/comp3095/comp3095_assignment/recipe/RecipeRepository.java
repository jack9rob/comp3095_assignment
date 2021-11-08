/*
Project: Recipe Site
Assignment: 1
Author: Jack Robinson, Fatih Camgoz, Jong-In Yoon
Date: November 7th
Description: repo for recipes. get all recipes, search by id and title, and save recipes to the database
 */

package ca.gbc.comp3095.comp3095_assignment.recipe;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Component
public interface RecipeRepository extends Repository<Recipe, Long> {

    @Query("select r from Recipe r")
    List<Recipe> getAll();

    @Query("SELECT recipe FROM Recipe recipe WHERE recipe.id = :id")
    @Transactional(readOnly = true)
    Recipe findById(@Param("id") Long id);

    @Transactional(readOnly = true)
    @Query("SELECT recipe from Recipe recipe WHERE :title LIKE LOWER(TRIM(recipe.title)) ")
    List<Recipe> findByTitle();

    @Transactional(readOnly = true)
    @Query("SELECT recipe from Recipe recipe WHERE lower(recipe.title) LIKE %:title% ")
    List<Recipe> searchByTitle(String title);

    void save(Recipe recipe);



}
