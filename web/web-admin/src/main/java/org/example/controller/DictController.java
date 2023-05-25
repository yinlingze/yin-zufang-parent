package org.example.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.example.entity.Dict;
import com.example.result.Result;
import com.example.service.DictService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/dict")
public class DictController extends BaseController{
    @Reference
    private DictService dictService;
    //去展示数据字典页面
    @RequestMapping
    public String index(){
        return "dict/index";
    }

    //获取数据字典的数据
    @ResponseBody
    @RequestMapping("/findZnodes")
    public Result findZnodes(@RequestParam(value = "id" , defaultValue = "0") Long id){
        //调用DictService查询数据
        List<Map<String, Object>> znodes = dictService.findZnodes(id);
        return Result.ok(znodes);
    }

    /**
     * 根据id获得其子节点数据
     * @param parentId
     * @return
     */
    @GetMapping("findListByParentId/{parentId}")
    @ResponseBody
    public Result<List<Dict>> findListByParentId(@PathVariable Long parentId){
        List<Dict> listByParentId = dictService.findListByParentId(parentId);
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
