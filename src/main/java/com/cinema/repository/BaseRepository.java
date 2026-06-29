package com.cinema.repository;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseRepository<T> implements IRepository<T> {

    protected List<T> data = new ArrayList<>();

    @Override
    public List<T> findAll() {
        return new ArrayList<>(data);
    }

    @Override
    public T findById(int id) {
        throw new UnsupportedOperationException("findById must be override at subclass");
    }

    @Override
    public void save(T entity) {
        data.add(entity);
    }

    @Override
    public void delete(int id) {
        throw new UnsupportedOperationException("delete must be override at subclass");
    }
}
