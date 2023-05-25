package org.example.service;

import com.github.pagehelper.PageInfo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface BaseService<T> {
    List<T> findAll();
    void save(T role);

    void reSet(T role);

    T getById(Serializable id);

    void delectById(Serializable id);

    PageInfo<T> findPage(Map<String, Object> filters);
}
