package ycw.system.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import core.common.service.impl.CommonServiceImpl;
import ycw.system.dao.TypeDao;
import ycw.system.pojo.base.TSType;
import ycw.system.pojo.base.TSTypegroup;
import ycw.system.service.TypeService;

@Service("TypeService")
public class TypeServiceImpl extends CommonServiceImpl implements TypeService{

	public TypeDao TypeDao = null;
	
	
	@Resource
	public void setTypeDao(TypeDao typeDao) {
		TypeDao = typeDao;
	}
    /**
     * 参数管理查询
     */

	public <T> List<T> getTypeList(){
		
		return TypeDao.getTypeList();
	}
	/**
	 * 查看类型查询
	 */
	
	public <T> List<T> getTypes(Map<String, Object> map) {
		
		return TypeDao.getTypes(map);
	}
	
	/**
	 * 查看类型分类总数
	 */
	public int getTypesCount(Map<String, Object> map){
		return TypeDao.getTypesCount(map);
	}
	
	/**
	 * 类别录入
	 */
	public int saveTypes(TSType tsType) {
		return TypeDao.saveTypes(tsType);
	}

    /**
     * 更新类别
     */
	
	public int updateTypes(TSType tsType) {
		
		return TypeDao.updateTypes(tsType);
	}

    /**
     * 编辑类别查询
     */
	
	public TSType selectTypes(String id) {
		
		return TypeDao.selectTypes(id);
	}


	 /**
	  * 类别录入校验
	  */

	
	public List<Object> insertTypes(Map<String, Object> map) {
		
		return TypeDao.insertTypes(map);
	}

     /**
      * 删除类别
      */
	
	public void deleteTypes(String id) {
		
		TypeDao.deleteTypes(id);
	}

     /**
      * 根据id查看类别
      */
	
	public TSType selectTypesid(String id) {
		
		return TypeDao.selectTypesid(id);
	}
	  /**
	   * 查询父类别的信息
	   */
	public TSTypegroup selectParentType(String id){
		return TypeDao.selectParentType(id);
		
	}
	/**
     * 类型查询
     * @param typegroupcode 类型
     * @return 
     */
    public <T> List<T> getType(Map<String, Object> map){
        return TypeDao.getType(map);
    }

	


	
}
