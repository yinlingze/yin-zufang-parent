package org.example.service.imp;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.example.dao.BaseDao;
import org.example.service.BaseService;
import org.example.util.CastUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Transactional
public abstract class BaseServiceImpl<T> implements BaseService<T> {
    public abstract BaseDao<T> getEntityDao();

    @Override
    public List<T> findAll(){
        return getEntityDao().findAll();
    }

    @Override
    public void save(T t) {
        getEntityDao().save(t);
    }

    @Override
    public void reSet(T t) {
        getEntityDao().reSet(t);
    }

    @Override
    public T getById(Serializable id) {
        return getEntityDao().findById(id);
    }

    @Override
    public void delectById(Serializable id) {
        getEntityDao().delectById(id);
    }

    @Override
    public PageInfo findPage(Map filters) {
        int pageNum = CastUtil.castInt(filters.get("pageNum"), 1);
        //每页显示的记录条数
        int pageSize = CastUtil.castInt(filters.get("pageSize"), 10);
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<T>(getEntityDao().findPage(filters),5);
    }
}
