package ycw.system.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import core.common.service.impl.CommonServiceImpl;
import ycw.system.dao.UserDao;
import ycw.system.pojo.base.TSBaseUser;
import ycw.system.pojo.base.TSRoleUser;
import ycw.system.pojo.base.TSUser;
import ycw.system.service.UserService;
import ycw.system.vo.UserVo;

/**
 * 
 * @author  张代浩
 *
 */
@Service("userService")
@Transactional
public class UserServiceImpl extends CommonServiceImpl implements UserService {
     
    public UserDao userDao = null;
    

	public UserDao getUserDao() {
        return userDao;
    }
	@Resource
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
    public TSUser checkUserExits(TSUser user){
		return this.commonDao.getUserByUserIdAndUserNameExits(user);
	}
	public TSUser checkUserExits(String userName,String password){
		return this.commonDao.getUserByUserIdAndUserNameExits(userName, password);
	}
	public TSUser checkUserDisable(TSUser user){
		return this.commonDao.getUserByUserIdAndUserNameExitsAndIsDisable(user);
	}
	public String getUserRole(TSUser user){
		return this.commonDao.getUserRole(user);
	}
	
	public void pwdInit(TSUser user,String newPwd) {
			this.commonDao.pwdInit(user,newPwd);
	}
	
	public int getUsersOfThisRole(String id) {
		Criteria criteria = getSession().createCriteria(TSRoleUser.class);
		criteria.add(Restrictions.eq("TSRole.id", id));
		int allCounts = ((Long) criteria.setProjection(
				Projections.rowCount()).uniqueResult()).intValue();
		return allCounts;
	}
//    @Override
//    public int addUserInf(TSUser user) {
//        return userDao.addUserInf(user);
//    }
//    @Override
//    public int addBaseUserInf(TSBaseUser baseuser) {
//        return userDao.addBaseUserInf(baseuser);
//    }
	/**
	 * 查询用户信息
	 * @return 
	 */
	public <T> List<T> getUser(Map<String, Object> map) {
		return userDao.getUser4Businesses(map);
	}
	
	/**
	 * 查询用户总数
	 * @return 
	 */
	public int getUserTotle(Map<String, Object> map){
		return userDao.getUserTotal4Businesses(map);
	}
	
	/**
	 * 删除关系表信息
	 * @param tbTerminal 设备id
	 * @return 
	 */
	public int deleteRoleUser(TSRoleUser tsRoleUser){
		return userDao.deleteRoleUser(tsRoleUser);
	}
	/**
	 * 删除用户信息
	 * @return 
	 */
	public int updateUser(TSUser tsUser){
		return userDao.updateUser(tsUser);
	}
	
	/**
	 * 修改用户密码
	 * @return 
	 */
	public int updateUserPassword(TSUser tsUser){
		return userDao.updateUserPassword(tsUser);
	}
	
	/**
	 * 修改用户状态
	 * @return 
	 */
	public int updateUserStatus(TSUser tsUser){
		return userDao.updateUserStatus(tsUser);
	}
	/**
	 * 添加用户信息
	 * @return
	 * */
	public int addUserInf(TSUser user) {
		return userDao.addUserInf(user);
	}
	/**
     * 添加用户基本信息
     * @return
     * */
	public int addBaseUserInf(TSBaseUser baseuser) {
		return userDao.addBaseUserInf(baseuser);
	}
	/**
     * 添加用户权限信息
     * @return
     * */
	public int addRoleUserInf(TSRoleUser roleuser) {
		return userDao.addRoleUserInf(roleuser);
	}
	
	/**
	 * 修改用信息
	 * @return 
	 */
	@Override
	public Integer updateBaseuser(TSBaseUser baseuser) {
		return userDao.updateBaseuser(baseuser);
	}
	
	/**
	 * 修改用信息
	 * @return 
	 */
	@Override
	public UserVo getUserById(String id) {
		return userDao.getUserById(id);
	}
	
	public List<String> getrolesUserList(String id) {
		return userDao.getrolesUserList(id);
	}
	@Override
	public String messageLogin(Map<String, Object> map) {
		return userDao.messageLogin(map);
	}
	

}
