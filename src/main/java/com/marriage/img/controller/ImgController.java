package com.marriage.img.controller;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.marriage.img.service.ImgService;
import com.marriage.user.entity.User;

/**
 * 图片上传
 */
@Controller
@RequestMapping("")
public class ImgController {


private ImgService imgService;


	@RequestMapping("addUserImg")
	public String addUserImg (HttpServletRequest request, User user, MultipartFile imgFile) throws IOException {
	  //获取文件原始名称
	  String originalFilename = imgFile.getOriginalFilename();
	  //上传图片
	  if(imgFile!=null && originalFilename!=null && originalFilename.length()>0){
	    //存储图片的物理路径
	    String pic_path = "";
	    //新的图片名称
	    String newFileName = UUID.randomUUID() + originalFilename.substring(originalFilename.lastIndexOf("."));
	    //新图片
	    File newFile = new File(pic_path+newFileName);
	    //将内存中的数据写入磁盘
	    imgFile.transferTo(newFile);
	    imgService.addUserImg(null);
	    HttpSession session = request.getSession();
	    session.setAttribute("imgUrl", newFileName);
	  }
	 
	  return "";
	}


}

