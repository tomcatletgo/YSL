package ycw.system.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import core.common.model.json.DataGridReturn;
import core.common.service.impl.CommonServiceImpl;
import ycw.system.dao.LogDao;
import ycw.system.pojo.base.TSLog;
import ycw.system.service.LogService;

@Service("logService")
public class LogServiceImpl extends CommonServiceImpl implements LogService{

	public LogDao logDao = null;
	
	
	@Resource
	public void setLogDao(LogDao logDao) {
		this.logDao = logDao;
	}



	/***
	 * 日志的列表
	 * @param map
	 * @return
	 */
	public <T> List<T> getLogList(Map<String, Object> map){
		
		return logDao.getLogList(map);
	}
	
	/***
	 * 日志的总数
	 * @param map
	 * @return
	 */
	public int getLogCount(Map<String, Object> map){
		return logDao.getLogCount(map);
	}
	
	/***
	 * 添加日志
	 * @param log
	 */
	public void addLog(TSLog log){
		logDao.addLog(log);
	}
}
