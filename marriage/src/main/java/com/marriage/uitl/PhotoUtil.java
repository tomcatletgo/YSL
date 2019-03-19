package com.marriage.uitl;


import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by Administrator on 2017/3/29.
 */
public class PhotoUtil {

    /**
     *
     * 功能描述   保存图片
     *
     * @param filedata
     *           文件数据
     *　　返回图片位置
     */
    public static String saveFile( MultipartFile filedata, HttpServletRequest request) {
        // TODO Auto-generated method stub
        String pathval = request.getSession().getServletContext().getRealPath("/")+"WEB-INF/";
        System.err.println("URL****======="+pathval);
        // 根据配置文件获取服务器图片存放路径
        String newFileName = String.valueOf( System.currentTimeMillis());
        String saveFilePath = "images/uploadFile";
        /* 构建文件目录 */
        File fileDir = new File(pathval + saveFilePath);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        //上传的文件名
        String filename=filedata.getOriginalFilename();
        //文件的扩张名
        String extensionName = filename.substring(filename.lastIndexOf(".") + 1);
        try {
            String imgPath = saveFilePath + newFileName + "." +extensionName;
            //System.out.println(pathval + imgPath);打印图片位置
            FileOutputStream out = new FileOutputStream(pathval + imgPath);
            // 写入文件
            out.write(filedata.getBytes());
            out.flush();
            out.close();
            System.out.println("imgUrL"+imgPath);
            return imgPath;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     *
     * 功能描述：删除图片
     *
     * @param oldPic
     *
     */
    private void deleteFile(String oldPic) {
        // TODO Auto-generated method stub

        /* 构建文件目录 */
        File fileDir = new File(oldPic);
        if (fileDir.exists()) {
            //把修改之前的图片删除 以免太多没用的图片占据空间
            fileDir.delete();
        }

    }
}