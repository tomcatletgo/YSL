package core.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

/**
 * 将文件上传到远程ftp服务器中
 * 
 * @author Zhangqichao
 * 
 */
public class Upload2FTP {
	private String ip = "";
	private int port = 21;

	private String user = "";
	private String pwd = "";

	private static Upload2FTP instance;

	public static final String WORKING_DIRECTORY = "/uploadFiles";

	private static FTPClient ftp = new FTPClient();

	private Upload2FTP(String ip, int port, String user, String pwd) {
		this.ip = ip;
		this.port = port;
		this.user = user;
		this.pwd = pwd;
	}

	public static Upload2FTP instance(String ip, int port, String user, String pwd) {
		if (instance == null) {
			instance = new Upload2FTP(ip, port, user, pwd);
			return instance;
		}
		return instance;
	}

	/**
	 * 下载远程ftp上的文件
	 * 
	 * @param fileName
	 * @return 文件流
	 */
	public InputStream download(String fileName) {
		try {
			ftp.connect(ip, port);
			ftp.login(user, pwd);
			if (!ftp.changeWorkingDirectory(WORKING_DIRECTORY)) {
				ftp.makeDirectory(WORKING_DIRECTORY);
			}
			ftp.changeWorkingDirectory(WORKING_DIRECTORY);
			ftp.setBufferSize(1024);
			ftp.setControlEncoding("UTF-8");
			ftp.setFileType(FTP.BINARY_FILE_TYPE);
			return ftp.retrieveFileStream(fileName);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
		}
		return null;
	}

	/**
	 * 登出并且关闭连接
	 */
	public void release() {
		if (ftp.isConnected()) {
			try {
				ftp.logout();
				ftp.disconnect();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * web文件下载直接在浏览器中打开下载框
	 * 
	 * @param fileName
	 * @param response
	 */
	public void download(String fileKey, String fileName, HttpServletResponse response, boolean isIe) {
		OutputStream os = null;
		InputStream fis = null;
		try {
			fis = this.download(fileKey);
			os = response.getOutputStream();
			response.setHeader("Content-Type", "application/vnd.rn-realmedia-vbr");
			// response.setHeader("content-disposition", "attachment;filename="
			// + fileName);
			String varName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");
			if (isIe) {
				varName = URLEncoder.encode(fileName, "UTF-8");
			}

			response.setHeader("content-disposition", "attachment;filename=" + varName);
			// response.setContentType("application/vnd.rn-realmedia-vbr");
			byte temp[] = new byte[1000];
			int n = 0;
			while ((n = fis.read(temp)) != -1) {
				os.write(temp, 0, n);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (os != null) {
					os.close();
				}
				if (fis != null) {
					fis.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	/**
	 * 上传文件到ftp远程服务器上
	 * 
	 * @param fileName
	 * @param is
	 *            文件流
	 */
	// @Test
	public void upload(String fileName, InputStream is) {
		// FileInputStream fis = null;
		try {
			ftp.connect(ip, port);
			ftp.login(user, pwd);
			if (!ftp.changeWorkingDirectory(WORKING_DIRECTORY)) {
				ftp.makeDirectory(WORKING_DIRECTORY);
			}
			ftp.changeWorkingDirectory(WORKING_DIRECTORY);
			ftp.setBufferSize(1024);
			ftp.setControlEncoding("UTF-8");
			ftp.setFileType(FTP.BINARY_FILE_TYPE);
			System.out.println("////"+fileName+"上传结果："+ftp.storeFile(fileName, is));
			ftp.logout();  
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(ftp.isConnected()) {  
			   try {  
			     ftp.disconnect();  
			    } catch(IOException ioe) {  
			     // do nothing  
			    }  
			}
			this.release();
		}
	}

	/**
	 * 删除ftp远程服务器上的文件
	 * 
	 * @param fileName
	 */
	// @Test
	public void delete(String fileName) {
		// FileInputStream fis = null;
		try {
			ftp.connect(ip, port);
			ftp.login(user, pwd);
			if (!ftp.changeWorkingDirectory(WORKING_DIRECTORY)) {
				ftp.makeDirectory(WORKING_DIRECTORY);
			}
			ftp.changeWorkingDirectory(WORKING_DIRECTORY);
			ftp.setBufferSize(1024);
			ftp.setControlEncoding("UTF-8");
			ftp.setFileType(FTP.BINARY_FILE_TYPE);
			ftp.deleteFile(fileName);

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			this.release();
		}
	}
}
