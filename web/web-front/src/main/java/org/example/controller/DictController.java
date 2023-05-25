package org.example.controller;

import com.alibaba.dubbo.config.annotation.Reference;

import org.example.entity.Dict;
import org.example.result.Result;
import org.example.service.DictService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


@Controller
@RequestMapping("/dict")
public class DictController {

    @Reference
    private DictService dictService;

    /**
     * 根据id获得其子节点数据
     * @param parentId
     * @return
     */
    @GetMapping("findListByParentId/{parentId}")
    @ResponseBody
    public Result<List<Dict>> findListByParentId(@PathVariable Long parentId){
        List<Dict> listByParentId = dictService.findAll();
        return Result.ok(listByParentId);
    }

    /**
     * 根据编码或的其子节点数据
     * @param dictCode
     * @return
     */
    @GetMapping("findListByDictCode/{dictCode}")
    @ResponseBody
    public Result<List<Dict>> findListByDictCode(@PathVariable String dictCode){
        List<Dict> listByDictCode = dictService.findListByDictCode(dictCode);
        return Result.ok(listByDictCode);
    }
}
