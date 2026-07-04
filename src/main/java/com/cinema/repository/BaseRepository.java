package com.cinema.repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Base implementation of the {@link IRepository} interface.
 * <p>
 * This class provides common CRUD operations using an in-memory list.
 * Concrete repository classes should extend this class and override
 * methods such as {@code findById()} and {@code delete()} when needed.
 *
 * @param <T> the type of entity managed by the repository
 */
public abstract class BaseRepository<T> implements IRepository<T> {

    protected List<T> data = new ArrayList<>();

    /**
     * Returns all entities stored in the repository.
     *
     * @return a copy of the list containing all entities
     */
    @Override
    public List<T> findAll() {
        return new ArrayList<>(data);
    }

    /**
     * Finds an entity by its ID.
     * <p>
     * This method must be overridden by subclasses.
     *
     * @param id the ID of the entity to find
     * @return the entity with the specified ID
     * @throws UnsupportedOperationException if the subclass does not override this method
     */
    @Override
    public T findById(int id) {
        throw new UnsupportedOperationException("findById must be overridden in the subclass");
    }

    /**
     * Saves an entity to the repository.
     *
     * @param entity the entity to save
     */
    @Override
    public void save(T entity) {
        data.add(entity);
    }

    /**
     * Deletes an entity by its ID.
     * <p>
     * This method must be overridden by subclasses.
     *
     * @param id the ID of the entity to delete
     * @throws UnsupportedOperationException if the subclass does not override this method
     */
    @Override
    public void delete(int id) {
        throw new UnsupportedOperationException("delete must be overridden in the subclass");
    }

    /**
     * Removes all entities from the repository.
     */
    public void clear() {
        data.clear();
    }
}