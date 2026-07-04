package com.cinema.repository;

import java.util.List;

/**
 * Defines the basic CRUD operations for a repository.
 *
 * @param <T> the type of entity managed by the repository
 */
public interface IRepository<T> {

    /**
     * Returns all entities stored in the repository.
     *
     * @return a list of all entities
     */
    List<T> findAll();

    /**
     * Finds an entity by its unique identifier.
     *
     * @param id the ID of the entity to find
     * @return the entity with the specified ID, or {@code null} if no matching entity exists
     */
    T findById(int id);

    /**
     * Saves an entity to the repository.
     *
     * @param entity the entity to save
     */
    void save(T entity);

    /**
     * Deletes the entity with the specified ID from the repository.
     *
     * @param id the ID of the entity to delete
     */
    void delete(int id);
}