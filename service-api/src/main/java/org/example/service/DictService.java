package org.example.service;

import org.example.entity.Dict;

import java.util.List;
import java.util.Map;

public interface DictService extends BaseService<Dict>{

    List<Map> findByParentId(long id);

    List<Dict> findListByDictCode(String dictCode);
}
