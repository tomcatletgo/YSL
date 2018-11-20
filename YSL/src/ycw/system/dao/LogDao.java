package ycw.system.dao;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import ycw.system.pojo.base.TSLog;

/**
 * 设备管理DAO
 * @author 
 */
@Repository("logDao")
public interface LogDao {
	
	/***
	 * 日志的列表
	 * @param map
	 * @return
	 */
	public <T> List<T> getLogList(Map<String, Object> map);
	
	/***
	 * 日志的总数
	 * @param map
	 * @return
	 */
	public int getLogCount(Map<String, Object> map);
	
	/***
	 * 添加日志
	 * @param log
	 */
	public void addLog(TSLog log);

}
