package core.util;

import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;
import java.io.InputStream;
import java.net.InetSocketAddress;


/**
 * Created by Administrator on 2016/3/24.
 */
public class FastDFSUtil{
    /**
     * FastDFS服务器地址
     */
    private String hostName;
    /**
     * 端口
     */
    private  Integer port;
    /**
     * 编码
     */
    private String charset;
    /**
     * 超时时间
     */
    private Integer networkTimeout;
    /**
     * http地址
     */
    private String http;
    /**
     * Tracker服务端
     */
    private TrackerServer trackerServer;
    /**
     * Storage服务端
     */
    private StorageServer storageServer;
    /**
     * Storage客户端
     */
    private StorageClient storageClient;

    private static FastDFSUtil instance;
    
	private FastDFSUtil() {
        this.hostName = ResourceUtil.getConfigByName("fastdfs_host");
        this.port = Integer.parseInt(ResourceUtil.getConfigByName("fastdfs_port"));
        this.charset = ResourceUtil.getConfigByName("fastdfs_charset");
        this.networkTimeout = Integer.parseInt(ResourceUtil.getConfigByName("fastdfs_networkTimeout"));
        this.http = ResourceUtil.getConfigByName("fastdfs_httpurl");
	}
	
	public static FastDFSUtil getInstance() {
		if (instance == null) {
			instance = new FastDFSUtil();
			return instance;
		}
		return instance;
	}
    /**
     * 初始化
     */
    private void init(){
        ClientGlobal.setG_charset(charset);
        ClientGlobal.setG_network_timeout(networkTimeout);
        TrackerGroup tg = new TrackerGroup(new InetSocketAddress[]{new InetSocketAddress(hostName, port)});
        TrackerClient tc = new TrackerClient(tg);
        try{
            trackerServer = tc.getConnection();
        }catch (Exception e){
            e.printStackTrace();
            return;
        }
        if(trackerServer!=null){
            try {
                storageServer = tc.getStoreStorage(trackerServer);
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }
        if(storageServer!=null) {
            storageClient = new StorageClient(trackerServer, storageServer);
        }
    }

    /**
     * 上传至服务器
     * @param inputStream
     * @param type
     * @return
     */
    public String upload(InputStream inputStream,String type){
        this.init();

        byte[] data=null;
        try {
            data = FileUtils.readInputStream(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        NameValuePair[] metaList = null;

        String[] sArray=null;
        try {
            sArray=storageClient.upload_appender_file(data,type,metaList);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            this.close();
        }
        StringBuffer sb = new StringBuffer();
        sb.append(sArray[0]);
        sb.append("/");
        sb.append(sArray[1]);
        return sb.toString();
    }

	/**
	 * 删除文件
	 * @param groupName
	 * @param remoteFilename
	 */
	public void delete(String groupName, String remoteFilename) {
		this.init();
		try {
			storageClient.delete_file(groupName, remoteFilename);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.close();
		}
	}
	
    /**
     * 关闭服务
     */
    private void close(){
        if(trackerServer!=null) {
            try {
                trackerServer.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            trackerServer = null;
        }
        if(storageServer!=null){
            try {
                storageServer.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            storageServer=null;
        }
        storageClient=null;
    }

	public String getHttp() {
		return http;
	}
}