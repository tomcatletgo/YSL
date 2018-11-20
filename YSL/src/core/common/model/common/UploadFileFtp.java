package core.common.model.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import core.util.StringUtil;
import core.util.oConvertUtils;


/**
 * 上传下载模型类
 * 
 * @author 张代浩
 * 
 */
public class UploadFileFtp {

	private String aliasField = "fileAlias";
	private String nameField = "fileName";
	private String extend = "extend";// 扩展名
	private boolean view = false;// 是否是预览
	private boolean rename =true;// 是否重命名
	private byte[] content;// 预览或下载时传入的文件二进制内容
	private Object object;// 文件对应实体对象
	private String fileKey;
	private MultipartHttpServletRequest multipartRequest;
	private HttpServletRequest request;
	private HttpServletResponse response;

	/**
	 * @param response
	 *            the response to set
	 */
	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	public UploadFileFtp(HttpServletRequest request, Object object) {
		String fileKey = oConvertUtils.getString(request.getParameter("fileKey"));// 文件ID
		if (StringUtil.isNotEmpty(fileKey)) {
			this.fileKey = fileKey;
			this.request = request;
		} else {
			this.multipartRequest = (MultipartHttpServletRequest) request;
		}
		this.object = object;
	}

	public UploadFileFtp(HttpServletRequest request) {
		this.multipartRequest = (MultipartHttpServletRequest) request;

	}

	public UploadFileFtp(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
	}

	public UploadFileFtp() {
	}
	
	public String getAliasField() {
		return aliasField;
	}

	public void setAliasField(String aliasField) {
		this.aliasField = aliasField;
	}

	public String getNameField() {
		return nameField;
	}

	public void setNameField(String nameField) {
		this.nameField = nameField;
	}

	public String getExtend() {
		return extend;
	}

	public void setExtend(String extend) {
		this.extend = extend;
	}

	public boolean isView() {
		return view;
	}

	public void setView(boolean view) {
		this.view = view;
	}

	public boolean isRename() {
		return rename;
	}

	public void setRename(boolean rename) {
		this.rename = rename;
	}

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public String getFileKey() {
		return fileKey;
	}

	public void setFileKey(String fileKey) {
		this.fileKey = fileKey;
	}

	public MultipartHttpServletRequest getMultipartRequest() {
		return multipartRequest;
	}

	public void setMultipartRequest(MultipartHttpServletRequest multipartRequest) {
		this.multipartRequest = multipartRequest;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}
}
