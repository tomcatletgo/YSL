package core.common.dao;


import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import core.common.model.common.UploadFile;
import core.common.model.common.UploadFileFtp;
import core.common.model.json.ComboTree;
import core.common.model.json.ImportFile;
import core.common.model.json.TreeGrid;
import core.extend.template.Template;
import tag.vo.easyui.ComboTreeModel;
import tag.vo.easyui.TreeGridModel;
import ycw.system.pojo.base.TSDepart;
import ycw.system.pojo.base.TSUser;

public interface ICommonDao extends IGenericBaseCommonDao{
	/**
	 * admin账户密码初始化
	 * @param user
	 * @param newPwd
	 */
	public void pwdInit(TSUser user,String newPwd);
	/**
	 * 检查用户是否存在
	 * @param user
	 * @return
	 */
	public TSUser getUserByUserIdAndUserNameExits(TSUser user);
	/**
	 * 
	 * @param userName
	 * @param password
	 * @return
	 */
	public TSUser getUserByUserIdAndUserNameExits(String userName, String password) ;
	/**
	 * 检查用户是否停用
	 * @param user
	 * @return
	 */
	public TSUser getUserByUserIdAndUserNameExitsAndIsDisable(TSUser user);
	/**
	 * 
	 * @param user
	 * @return
	 */
	public String getUserRole(TSUser user);
	/**
	 * 文件上传
	 * @param request
	 */
	public <T> T uploadFile(UploadFile uploadFile);
	/**
	 * 文件上传
	 * @param request
	 */
	public <T> T uploadFileFtp(UploadFileFtp uploadFile);
	/**
	 * 文件上传或预览
	 * @param uploadFile
	 * @return
	 */
	public HttpServletResponse viewOrDownloadFile(UploadFile uploadFile);
	/**
	 * 文件上传或预览
	 * @param uploadFile
	 * @return
	 */
	public HttpServletResponse viewOrDownloadFileFtp(UploadFileFtp uploadFile);
	/**
	 * 
	 * @param template
	 * @return
	 */
	public Map<Object,Object> getDataSourceMap(Template template);
	/**
	 * 生成XML文件
	 * @param fileName XML全路径
	 */
	public HttpServletResponse createXml(ImportFile importFile);
	/**
	 * 解析XML文件
	 * @param fileName XML全路径
	 */
	public void parserXml(String fileName);
	/**
	 * 
	 * @param all
	 * @param comboTree
	 * @return
	 */
	public List<ComboTree> comTree(List<TSDepart> all,ComboTree comboTree);
	/**
	 * 根据模型生成JSON
	 * @param all 全部对象
	 * @param in  已拥有的对象
	 * @param comboBox 模型
	 * @return
	 */
	public  List<ComboTree> ComboTree(List all,ComboTreeModel comboTreeModel,List in);
	/**
	 * 
	 * @param all
	 * @param treeGridModel
	 * @return
	 */
	public  List<TreeGrid> treegrid(List all,TreeGridModel treeGridModel);
}

