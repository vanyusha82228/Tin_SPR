package edu.java.domain.repository;

import edu.java.domain.model.UserLink;
import java.util.List;

public interface GenericDao<T> {
    void add(T entity);

    void remove(Long id);

    List<T> findAll();
}
