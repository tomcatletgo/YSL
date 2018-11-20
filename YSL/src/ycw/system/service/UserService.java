package ycw.system.service;

import java.util.List;
import java.util.Map;

import core.common.service.CommonService;
import ycw.system.pojo.base.TSBaseUser;
import ycw.system.pojo.base.TSRoleUser;
import ycw.system.pojo.base.TSUser;
import ycw.system.vo.UserVo;
/**
 * 
 * @author  张代浩
 *
 */
public interface UserService extends CommonService{

	public TSUser checkUserExits(TSUser user);
	public TSUser checkUserExits(String userName,String password);
	public TSUser checkUserDisable(TSUser user);
	public String getUserRole(TSUser user);
	public void pwdInit(TSUser user, String newPwd);
	
	/**
	 * 判断这个角色是不是还有用户使用
	 *@Author JueYue
	 *@date   2013-11-12
	 *@param id
	 *@return
	 */
	public int getUsersOfThisRole(String id);
	/**
	 * 查询用户信息
	 * @return 
	 */
	public <T> List<T> getUser(Map<String, Object> map);
	
	/**
	 * 查询用户总数
	 * @return 
	 */
	public int getUserTotle(Map<String, Object> map);
	/**
	 * 删除关系表信息
	 * @param tbTerminal 设备id
	 * @return 
	 */
	public int deleteRoleUser(TSRoleUser tsRoleUser);
	
	/**
	 * 删除用户信息
	 * @return 
	 */
	public int updateUser(TSUser tsUser);
	
	/**
	 * 修改用户状态
	 * @return 
	 */
	public int updateUserStatus(TSUser tsUser);
	
	/**
	 * 修改用户密码
	 * @return 
	 */
	public int updateUserPassword(TSUser tsUser);
	
	 /**
     * 添加User信息
     *@return int
     */
    public int addUserInf(TSUser user); 
    /**
     * 添加BaseUser信息
     *@return int
     */
    public int addBaseUserInf(TSBaseUser baseuser); 
    /**
     * 添加ROleUser信息
     *@return int
     */
    public int addRoleUserInf(TSRoleUser roleuser); 
    
    public Integer updateBaseuser(TSBaseUser baseuser);
    
    public UserVo getUserById(String id);
    
    public List<String> getrolesUserList(String id);
    
    /**
	 * 查ajax判断用户登录
	 * @return 
	 */
	public String messageLogin(Map<String, Object> map);
}
