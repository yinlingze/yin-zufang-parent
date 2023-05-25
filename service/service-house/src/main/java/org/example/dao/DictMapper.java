package org.example.dao;

import org.example.entity.Dict;

import java.util.List;

public interface DictMapper extends BaseDao<Dict>{
    List<Dict> findByParentId(long id);
    Integer countByParentId(long id);
    String getNameById(long id);

    Dict getByDictCode(String dictCode);

}
