package edu.java.scrapper.domain.repository;

import java.util.List;

public interface GenericDao<T> {
    void add(T entity);

    void remove(Long id);

    List<T> findAll();
}
