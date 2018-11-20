package ycw.system.service;

import java.util.List;
import java.util.Map;

import core.common.service.CommonService;
import ycw.system.pojo.base.TSLog;

public interface LogService extends CommonService{

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
