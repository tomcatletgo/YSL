package ycw.system.service;

import java.util.List;
import java.util.Map;

import core.common.service.CommonService;
import ycw.system.pojo.base.TSRole;
import ycw.system.pojo.base.TSRoleFunction;
import ycw.system.pojo.base.TSRoleUser;
import ycw.system.vo.RoleFunctionVo;

public interface RoleService extends CommonService{

	/**
	 * 角色列表
	 * @param map
	 * @return
	 */
	public <T> List<T> getRoleList(Map<String, Object> map);
	
	/**
	 * 查询角色条数
	 * @param map
	 * @return
	 */
	public int getRoleCount(Map<String, Object> map);
	/**
	 * 角色信息删除
	 * @param tsRole
	 * @return
	 */
	public int deletRole(TSRole tsRole);
	/**
	 * 角色信息录入
	 * @param tsRole
	 * @return
	 */
	public int saveRole(TSRole tsRole);
	
	/**
	 * 根据id查询已有角色信息
	 * @param id
	 * @return
	 */
	public TSRole selectRole(String id);
	/**
	 * 角色编辑
	 * @param tsRole
	 * @return
	 */
	public int updateRole(TSRole tsRole);
	
	/**
	 * 校验查询角色编码
	 * @param map
	 * @return
	 */
	public List<Object> selectCheckRole(Map<String, Object> map);
	
	/**
	 * 查询TSRoleFunction
	 * @param id
	 * @return
	 */
	public List<TSRoleFunction> selectRoleid(TSRole tsrole);
	
	/**
	 * 删除TSRoleFunction数据
	 * @param tsRole
	 * @return
	 */
	public int deletRoleFunction(Map<String, Object> map);
	
	/**
	 * 查询TSRoleFunction
	 * @param id
	 * @return
	 */
	public List<TSRoleUser> selectroleUsers(TSRole tsrole);
	/**
	 * 删除TSRoleFunction数据
	 * @param tsRole
	 * @return
	 */
	public int deletRoleUser(TSRoleUser tsrole);
	


	/**
	 * 查询菜单父级列表
	 * @param map
	 * @return
	 */
	public <T> List<T> getFunctionlist(Map<String, Object> map);

	/**
	 * 查询菜单的子菜单
	 * @param map
	 * @return
	 */
	public <T> List<T> getChildFunlist(String id);
	

	/**
	 * 根据权限查询菜单表
	 * @param String id 权限主键
	 * @return
	 */
	public <T> List<T> getRoleFun(String id);
	

	/**
	 * 根据权限查询权限菜单表
	 * @param String id 权限主键
	 * @return
	 */
	public <T> List<T> getrf(String id);
	
	/**
	 * 保存角色权限
	 * @param String id 权限主键
	 * @return
	 */
	public void saveList(List<RoleFunctionVo> vo);

	/**
	 * 删除角色权限
	 * @param String id 权限主键
	 * @return
	 */
	public void deleteAll(List<RoleFunctionVo> vo);
	
	/**
	 * 查询关联表是否存在数据
	 * @param String id 权限主键
	 * @return
	 */
	public int selecRoUsCount(String id);
	
	/**
	 * 是否为管理员权限查询
	 * @param String id 权限主键
	 * @return
	 */
	public <T> List<T>  selJurisdictionAdmin(String id);
	
}
