package com.cinema.repository;

import java.util.List;

public interface IRepository<T> {

    List<T> findAll();

    T findById(int id);

    void save(T entity);

    void delete(int id);
}
