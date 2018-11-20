package ycw.system.dao;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import ycw.system.pojo.base.TSDepart;
import ycw.system.vo.DepartVo;

/**
 * 菜单列表DAO
 * @author 
 */
@Repository("departDao")
public interface DepartDao {
	/**
	 * 查询部门机构表
	 * @param map
	 */
	public <T> List<T> getDepartList(Map<String, Object> map);
	public int getChildList(String parentId);
	public String getIconPath(String id);
	/**
	 * 查询t_s_depart
	 */
	public <T> List<T> getList();
	/**
	 * 查询t_s_depart
	 * @param String id
	 */
	public DepartVo getById(String id);
	/**
	 * 查询t_s_depart
	 * @param map
	 */
	public <T> List<T> getComboTree(Map<String, Object> map);
	/**
	 * 查询t_s_depart（部门一致性）
	 * @param map
	 */
	public <T> List<T> checkDepartName(Map<String, Object> map);
	/**
	 * 查询t_s_depart（部门code一致性）
	 * @param map
	 */
	public <T> List<T> checkDepartCode(Map<String, Object> map);
	/**
	 * 添加部门
	 * @param map
	 */
	public void save(Map<String, Object> map);
	/**
	 * 修改部门信息
	 * @param DepartVo depart
	 */
	public void update(DepartVo depart);
	/**
	 * 查询t_s_depart（获取子部门Id）
	 * @param String parentId
	 */
	public <T> List<T> getChildDepart(String parentId);
	/**
	 * 查询t_s_base_user（部门相关用户信息）
	 * @param String id
	 */
	public <T> List<T> getUserList(String id);
	/**
	 * 查询t_b_device（相关终端信息）
	 * @param String id
	 */
	public <T> List<T> getDevice(String id);
	/**
	 * 查询t_b_sim（相关SIM卡信息）
	 * @param String id
	 */
	public <T> List<T> getSimList(String id);
	/**
	 * 查询t_b_ammeter（相关电表信息）
	 * @param String id
	 */
	public <T> List<T> getAmmeter(String id);
	/**
	 * 查询t_b_ammeterbox（相关电表信息）
	 * @param String id
	 */
	public <T> List<T> getAmmeterBox(String id);
	/**
	 * 查询t_b_collector（相关采集器信息）
	 * @param String id
	 */
	public <T> List<T> getCollector(String id);
	/**
	 * 查询t_b_concentrator（相关集中器信息）
	 * @param String id
	 */
	public <T> List<T> getConcentrator(String id);
	/**
	 * 删除
	 * @param String id
	 */
	public void deleteDepart(String id);
	/**
	 * 查询公司
	 * @param name 部门id
	 * @return 
	 */
	public TSDepart getDepart(Map<String,Object> map);
	
}
