package ycw.system.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import ycw.system.pojo.base.TSRoleUser;
import ycw.system.pojo.base.TSUser;
import ycw.system.pojo.base.TSBaseUser;
import ycw.system.vo.UserVo;

/**
 * 类型管理DAO
 * @author 
 */
@Repository("userDao")
public interface UserDao {
	
	/**
	 * 查询用户信息
	 * @return 
	 */
	public <T> List<T> getUser(Map<String, Object> map);
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

    /**
	 * 查询用户总数
	 * @return 
	 */
	public Integer getUserTotle(Map<String, Object> map);
	
	/**
	 * 删除关系表信息
	 * @return 
	 */
	public Integer deleteRoleUser(TSRoleUser tsRoleUser);
	
	/**
	 * 删除用户信息
	 * @return 
	 */
	public Integer updateUser(TSUser tsUser);
	
	/**
	 * 修改用户状态
	 * @return 
	 */
	public Integer updateUserStatus(TSUser tsUser);
	
	/**
	 * 修改用户密码
	 * @return 
	 */
	public Integer updateUserPassword(TSUser tsUser);
	
	/**
	 * 修改用信息
	 * @return 
	 */
	public Integer updateBaseuser(TSBaseUser baseuser);
	
	/**
	 * 修改用信息
	 * @return 
	 */
	public UserVo getUserById(String id);
	
	public List<String> getrolesUserList(String id);
	public String messageLogin(Map<String, Object> map);
	
	public <T> List<T> getUser4Businesses(Map<String, Object> map);
	public Integer getUserTotal4Businesses(Map<String, Object> map);
	
}
