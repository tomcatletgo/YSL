package ycw.system.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import core.common.service.impl.CommonServiceImpl;
import ycw.system.dao.RoleDao;
import ycw.system.pojo.base.TSRole;
import ycw.system.pojo.base.TSRoleFunction;
import ycw.system.pojo.base.TSRoleUser;
import ycw.system.service.RoleService;
import ycw.system.vo.RoleFunctionVo;

@Service("roleService")

public class RoleServiceImpl extends CommonServiceImpl implements RoleService {
	
	public RoleDao roleDao = null;
	
	@Resource
	public void setroleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}
	
	/**
	 * 角色列表
	 * @param map
	 * @return 
	 */
	public <T> List<T> getRoleList(Map<String, Object> map) {
		return roleDao.getRoleList(map);
	}
	
	
	/**
	 * 查询角色条数
	 * @param map
	 * @return
	 */
	@Override
	public int getRoleCount(Map<String, Object> map) {
		return roleDao.getRoleCount(map);
	}

	/**
	 * 角色信息删除
	 * @param tsRole
	 * @return
	 */
	public int deletRole(TSRole tsRole) {
		return roleDao.deletRole(tsRole);
	}
	
	/**
	 * 角色信息录入
	 * @param tsRole
	 * @return
	 */
	public int saveRole(TSRole tsRole) {
		return roleDao.saveRole(tsRole);
	}
	
	/**
	 * 根据id查询已有角色信息
	 * @param id
	 * @return
	 */
	public TSRole selectRole(String id) {
		return roleDao.selectRole(id);
	}
	/**
	 * 角色编辑
	 * @param tsRole
	 * @return
	 */
	public int updateRole(TSRole tsRole) {
		return roleDao.updateRole(tsRole);
	}
	/**
	 * 校验查询角色编码
	 * @param map
	 * @return
	 */
	public List<Object> selectCheckRole(Map<String, Object> map) {
		return roleDao.selectCheckRole(map);
	}
	/**
	 * 查询TSRoleFunction
	 * @param id
	 * @return
	 */
	public List<TSRoleFunction> selectRoleid(TSRole tsrole) {
		return roleDao.selectRoleid(tsrole);
	}
	
	/**
	 * 删除TSRoleFunction数据
	 * @param tsRole
	 * @return
	 */
	public int deletRoleFunction(Map<String, Object> map) {
		return roleDao.deletRoleFunction(map);
	}
	/**
	 * 查询TSRoleFunction
	 * @param id
	 * @return
	 */
	public List<TSRoleUser> selectroleUsers(TSRole tsrole) {
		return roleDao.selectroleUsers(tsrole);
	}
	
	/**
	 * 删除TSRoleFunction数据
	 * @param tsRole
	 * @return
	 */
	public int deletRoleUser(TSRoleUser tsrole) {
		return roleDao.deletRoleUser(tsrole);
	}
	



	/**
	 * 查询菜单父级列表
	 * @param map
	 * @return
	 */
	public <T> List<T> getFunctionlist(Map<String, Object> map){
		
		return roleDao.getFunctionlist(map);
	}
	

	/**
	 * 查询菜单的子菜单
	 * @param map
	 * @return
	 */
	public <T> List<T> getChildFunlist(String id){
		return roleDao.getChildFunlist(id);
	}

	/**
	 * 根据权限查询菜单表
	 * @param String id 权限主键
	 * @return
	 */
	public <T> List<T> getRoleFun(String id){
		return roleDao.getRoleFun(id);
	}

	/**
	 * 根据权限查询权限菜单表
	 * @param String id 权限主键
	 * @return
	 */
	public <T> List<T> getrf(String id){
		return roleDao.getrf(id);
	}
	

	/**
	 * 保存角色权限
	 * @param String id 权限主键
	 * @return
	 */
	public void saveList(List<RoleFunctionVo> vo){
		roleDao.saveList(vo);
	}
	

	/**
	 * 删除角色权限
	 * @param String id 权限主键
	 * @return
	 */
	public void deleteAll(List<RoleFunctionVo> vo){
		roleDao.deleteAll(vo);
	}

	@Override
	public int selecRoUsCount(String id) {
		return roleDao.selecRoUsCount(id);
	}

	@Override
	public <T> List<T> selJurisdictionAdmin(String id) {
		return roleDao.selJurisdictionAdmin(id);
	}
}
