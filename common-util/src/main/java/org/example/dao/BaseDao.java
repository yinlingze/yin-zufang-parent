package org.example.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface BaseDao<T> {
    T findById(Serializable id);

    void save(T role);

    void reSet(T role);

    void delectById(Serializable id);


    List<T> findPage(Map<String, Object> filters);

    List<T> findAll();

}
