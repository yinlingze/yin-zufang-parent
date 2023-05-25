package org.example.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.example.entity.HouseImage;
import com.example.result.Result;
import com.example.service.HouseImageService;
import com.example.until.QiniuUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;


@Controller
@RequestMapping(value="/houseImage")
@SuppressWarnings({"unchecked", "rawtypes"})
public class HouseImageController {

    @Reference
    private HouseImageService houseImageService;

    private final static String LIST_ACTION = "redirect:/house/";
    private final static String PAGE_UPLOED_SHOW = "house/upload";

    @GetMapping("/uploadShow/{houseId}/{type}")
    //去上传图片
    public String uploadShow(ModelMap model, @PathVariable Long houseId, @PathVariable Long type) {
        model.addAttribute("houseId",houseId);
        model.addAttribute("type",type);
        return PAGE_UPLOED_SHOW;
    }

    //保存文件上传
    @PostMapping("/upload/{houseId}/{type}")
    @ResponseBody
    public Result upload(@PathVariable Long houseId, @PathVariable Integer type, @RequestParam(value = "file") MultipartFile[] files) throws Exception {
        if(files != null & files.length >0){
            for (MultipartFile file : files) {
                //获取字节数组
                byte[] bytes = file.getBytes();
                //获取图片名字
                String originalFilename = file.getOriginalFilename();
                //通过UUID随机生成字符串作为上传到七牛云图片的名字
                String newFileName = UUID.randomUUID().toString();
                //使用七牛云工具类上传图片
                QiniuUtils.upload2Qiniu(bytes,newFileName);
                //图片在七牛云上的名字
                String url= "http://rl26xnrxk.hn-bkt.clouddn.com/"+ newFileName;
                //创建houseImage对象
                HouseImage houseImage = new HouseImage();
                houseImage.setHouseId(houseId);
                houseImage.setType(type);
                houseImage.setImageName(originalFilename);
                houseImage.setImageUrl(url);
                houseImageService.insert(houseImage);
            }
        }
        return Result.ok();
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @GetMapping("/delete/{houseId}/{id}")
    public String delete( @PathVariable Long houseId, @PathVariable Long id) {
        HouseImage houseImage = houseImageService.getById(id);
        houseImageService.delete(id);
        QiniuUtils.deleteFileFromQiniu(houseImage.getImageUrl());
        return LIST_ACTION + houseId;
    }




}
