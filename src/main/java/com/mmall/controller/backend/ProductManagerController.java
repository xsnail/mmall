package com.mmall.controller.backend;

import com.google.common.collect.Maps;
import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.Product;
import com.mmall.pojo.User;
import com.mmall.service.IFileService;
import com.mmall.service.IProductService;
import com.mmall.util.PropertiesUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/4 0004.
 */
@RestController
@RequestMapping("/admin/product/")
public class ProductManagerController {

    @Autowired
    private IProductService iProductService;
    @Autowired
    private IFileService iFileService;

    @RequestMapping(value = "list",method = RequestMethod.GET)
    public ServerResponse adminList(HttpSession httpSession,@RequestParam(value = "pageNum",defaultValue = "1")int pageNum,
                                    @RequestParam(value = "pageSize",defaultValue = "10") int pageSize){
        User user = (User) httpSession.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        if(Const.Role.ROLE_ADMIN == user.getRole().intValue()) {
            return iProductService.adminList(pageNum,pageSize);
        }
        return ServerResponse.createByErrorMessage("无权限");
    }

    @RequestMapping(value = "search.do",method = RequestMethod.GET)
    public ServerResponse search(HttpSession httpSession,String productName, Integer productId,
                                 @RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
                                 @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize){
        User user = (User) httpSession.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        if(Const.Role.ROLE_ADMIN == user.getRole().intValue()) {
            return iProductService.search(productName, productId, pageNum, pageSize);
        }
        return ServerResponse.createByErrorMessage("无权限");
    }

    @RequestMapping(value = "detail.do",method = RequestMethod.GET)
    public ServerResponse detail(HttpSession httpSession,Integer productId){
        User user = (User) httpSession.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        if(Const.Role.ROLE_ADMIN == user.getRole().intValue()) {
            return iProductService.adminDetail(productId);
        }
        return ServerResponse.createByErrorMessage("无权限");
    }

    @RequestMapping(value = "save.do",method = RequestMethod.GET)
    public ServerResponse save(Product product,HttpSession httpSession){
        User user = (User) httpSession.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        if(Const.Role.ROLE_ADMIN == user.getRole().intValue()) {
            return iProductService.save(product);
        }
        return ServerResponse.createByErrorMessage("无权限");
    }

    @RequestMapping(value = "set_sale_status.do",method = RequestMethod.GET)
    public ServerResponse set_sale_status(HttpSession httpSession,Integer productId,Integer status){
        User user = (User) httpSession.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        if(Const.Role.ROLE_ADMIN == user.getRole().intValue()) {
            return iProductService.setSaleStatus(productId,status);
        }
        return ServerResponse.createByErrorMessage("无权限");
    }

    @RequestMapping(value = "upload.do")
    public ServerResponse upload(HttpSession httpSession,
                                 @RequestParam(value = "upload_file",required = false)MultipartFile file,
                                 HttpServletRequest request){
        User user = (User) httpSession.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        if(Const.Role.ROLE_ADMIN == user.getRole().intValue()) {
            String path = request.getSession().getServletContext().getRealPath("upload");
            String targetFileName = iFileService.upload(file,path);
            String url = PropertiesUtil.getProperty("ftp.server.http.prefix")+targetFileName;

            Map fileMap = Maps.newHashMap();
            fileMap.put("uri",targetFileName);
            fileMap.put("url",url);
            return ServerResponse.createBySuccess(fileMap);
        }
        return ServerResponse.createByErrorMessage("无权限");
    }

    @RequestMapping("richtext_img_upload.do")
    public Map richTextImgUpload(HttpSession httpSession,
                                 @RequestParam(value = "upload_file",required = false)MultipartFile file,
                                 HttpServletRequest request, HttpServletResponse response){
        User user = (User) httpSession.getAttribute(Const.CURRENT_USER);
        Map resultMap = Maps.newHashMap();
        if(user == null){
            resultMap.put("success",false);
            resultMap.put("msg","请登录");
            return resultMap;
        }
        if(Const.Role.ROLE_ADMIN == user.getRole().intValue()) {
//            {
//                "file_path": "http://img.happymmall.com/5fb239f2-0007-40c1-b8e6-0dc11b22779c.jpg",
//                    "msg": "上传成功",
//                    "success": true
//            }

            String path = request.getSession().getServletContext().getRealPath("upload");
            String targetFileName = iFileService.upload(file,path);
            if(StringUtils.isBlank(targetFileName)){
                resultMap.put("success",false);
                resultMap.put("msg","上传失败");
            }
            String url = PropertiesUtil.getProperty("ftp.server.http.prefix")+targetFileName;
            resultMap.put("success",true);
            resultMap.put("msg","上传成功");
            resultMap.put("file_path",url);
            response.addHeader("Access-Control-Allow-Headers","X-File-Name");
            return resultMap;
        }

        resultMap.put("success",false);
        resultMap.put("msg","无权限");
        return resultMap;
    }
}
