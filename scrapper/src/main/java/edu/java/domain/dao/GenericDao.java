package edu.java.domain.dao;

import java.util.List;

public interface GenericDao<T> {
    void add(T entity);
    void remove(Long id);
    List<T> findAll();
}
