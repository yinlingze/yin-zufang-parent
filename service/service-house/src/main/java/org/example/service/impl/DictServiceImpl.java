package org.example.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import org.example.dao.BaseDao;
import org.example.dao.DictMapper;
import org.example.entity.Dict;
import org.example.service.DictService;
import org.example.service.imp.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Transactional
//import com.alibaba.dubbo.config.annotation.Service;
@Service(interfaceClass = DictService.class)
public class DictServiceImpl extends BaseServiceImpl<Dict> implements DictService {

    @Autowired
    DictMapper dictMapper;

    @Override
    public BaseDao<Dict> getEntityDao() {
        return  dictMapper;
    }

    @Override
    public List<Map> findByParentId(long id) {
//        [{ id:'0331',name:'n3.3.n1',	isParent:true}]
        List<Map> list = new LinkedList();
        List<Dict> dictList = dictMapper.findByParentId(id);

        for (Dict dict : dictList) {

            Map map = new HashMap<String,Object>();
            map.put("id",dict.getId());
            map.put("name",dict.getName());
            map.put("isParent",dictMapper.countByParentId(id)>=1?true:false);
            list.add(map);
        }
        return list;
    }

    @Override
    public List<Dict> findListByDictCode(String dictCode) {
        return null;
    }
}
