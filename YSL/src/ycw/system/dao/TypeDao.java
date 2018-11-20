package ycw.system.dao;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import ycw.system.pojo.base.TSType;
import ycw.system.pojo.base.TSTypegroup;

/**
 * 类型DAO
 * @author 
 */
@Repository("typeDao")
public interface TypeDao {
	/**
	 * 参数管理查询
	 * @return
	 */
	
	public <T> List<T> getTypeList();
     /**
      *   查看类型查询
      * @param map
      * @return
      */
	public <T> List<T> getTypes(Map<String, Object> map);
	 /**
	  * 查看类型分页总数
	  * @param map
	  * @return
	  */
	public int getTypesCount(Map<String, Object> map);
	
	/**
	 * 类别录入
	 * @param tsType
	 * @return
	 */
	public int saveTypes(TSType tsType);
	 /**
	  * 编辑类别查询
	  * @param id
	  * @return
	  */
	public TSType selectTypes(String id);
	/**
	 * 更新类别
	 * @param tsType
	 * @return
	 */
	public int updateTypes(TSType tsType);
     /**
      * 删除类别
      * @param id
      */
	public void deleteTypes(String id);
	/**
	 * 根据id查询类别
	 * @param id
	 * @return
	 */
	public TSType selectTypesid(String id);
	/**
	 * 类别录入校验
	 * @param map
	 * @return
	 */
	public List<Object> insertTypes(Map<String, Object> map);
	 /**
	  * 查询父类别信息
	  * @param id
	  * @return
	  */
	public TSTypegroup selectParentType(String id);
	/**
     * 类型查询
     * @param typegroupcode 类型
     * @return 
     */
    public <T> List<T> getType(Map<String, Object> map);
}
