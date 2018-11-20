package ycw.om.form.common;

import java.io.InputStream;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.PutObjectResult;

public class NSOSSUtils {
	 
	/**
	 * 流的方式上传文件
	 * @return
	 */
	public static String saveFile2OSSByStream(InputStream inputStream,String fileName) throws RuntimeException{
		
		// 创建OSSClient实例
		OSSClient ossClient = new OSSClient(NSCommonConstant.endpoint, NSCommonConstant.accessKeyId,  NSCommonConstant.accessKeySecret);
		 
		PutObjectResult pResult = ossClient.putObject( NSCommonConstant.bucketName, fileName, inputStream);
		
		// 关闭client
		ossClient.shutdown();
		
		return null;
	}
}
