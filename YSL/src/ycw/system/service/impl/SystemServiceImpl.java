package ycw.system.service.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import core.common.hibernate.qbc.CriteriaQuery;
import core.common.service.impl.CommonServiceImpl;
import core.util.BrowserUtils;
import core.util.ContextHolderUtils;
import core.util.DataUtils;
import core.util.ResourceUtil;
import core.util.StringUtil;
import core.util.oConvertUtils;

import ycw.system.pojo.base.TSFunction;
import ycw.system.pojo.base.TSIcon;
import ycw.system.pojo.base.TSLog;
import ycw.system.pojo.base.TSRole;
import ycw.system.pojo.base.TSRoleFunction;
import ycw.system.pojo.base.TSRoleUser;
import ycw.system.pojo.base.TSType;
import ycw.system.pojo.base.TSTypegroup;
import ycw.system.pojo.base.TSUser;
import ycw.system.service.LogService;
import ycw.system.service.SystemService;

@Service("systemService")
@Transactional
public class SystemServiceImpl extends CommonServiceImpl implements SystemService {
	@Resource
	private LogService logService;
/*	@Autowired
	ResourcesDao resourcesDao;*/

	public TSUser checkUserExits(TSUser user) throws Exception {
		return this.commonDao.getUserByUserIdAndUserNameExits(user);
	}

	/**
	 * 添加日志
	 */
	public void addLog(String logcontent, Short loglevel, Short operatetype) {
		HttpServletRequest request = ContextHolderUtils.getRequest();
		String broswer = BrowserUtils.checkBrowse(request);
		TSLog log = new TSLog();
		log.setAccessType((short) 1);
		log.setLogcontent(logcontent);
		log.setLoglevel(loglevel);
		log.setOperatetype(operatetype);
		log.setNote(oConvertUtils.getIp());
		log.setBroswer(broswer);
		log.setOperatetime(DataUtils.gettimestamp());
		log.setTSUser(ResourceUtil.getSessionUserName());
		log.setId(UUID.randomUUID().toString().replaceAll("-", ""));
		// commonDao.save(log);

		logService.addLog(log);

	}

	/**
	 * 添加日志
	 */
	public void addLogForMobile(String logcontent, Short loglevel, Short operatetype, String deviceid, TSUser user) {
		HttpServletRequest request = ContextHolderUtils.getRequest();
		TSLog log = new TSLog();
		log.setAccessType((short) 2);
		log.setLogcontent(logcontent);
		log.setLoglevel(loglevel);
		log.setOperatetype(operatetype);
		log.setNote(deviceid);
		log.setBroswer("其他");
		log.setOperatetime(DataUtils.gettimestamp());
		log.setTSUser(user);
		commonDao.save(log);
	}

	/**
	 * 根据类型编码和类型名称获取Type,如果为空则创建一个
	 * 
	 * @param typecode
	 * @param typename
	 * @return
	 */
	public TSType getType(String typecode, String typename, TSTypegroup tsTypegroup) {
		TSType actType = commonDao.findUniqueByProperty(TSType.class, "typecode", typecode);
		if (actType == null) {
			actType = new TSType();
			actType.setTypecode(typecode);
			actType.setTypename(typename);
			actType.setTSTypegroup(tsTypegroup);
			commonDao.save(actType);
		}
		return actType;

	}

	/**
	 * 根据类型分组编码和名称获取TypeGroup,如果为空则创建一个
	 * 
	 * @param typecode
	 * @param typename
	 * @return
	 */
	public TSTypegroup getTypeGroup(String typegroupcode, String typgroupename) {
		TSTypegroup tsTypegroup = commonDao.findUniqueByProperty(TSTypegroup.class, "typegroupcode", typegroupcode);
		if (tsTypegroup == null) {
			tsTypegroup = new TSTypegroup();
			tsTypegroup.setTypegroupcode(typegroupcode);
			tsTypegroup.setTypegroupname(typgroupename);
			commonDao.save(tsTypegroup);
		}
		return tsTypegroup;
	}

	public TSTypegroup getTypeGroupByCode(String typegroupCode) {
		TSTypegroup tsTypegroup = commonDao.findUniqueByProperty(TSTypegroup.class, "typegroupcode", typegroupCode);
		return tsTypegroup;
	}

	public void initAllTypeGroups() {
		List<TSTypegroup> typeGroups = this.commonDao.loadAll(TSTypegroup.class);
		for (TSTypegroup tsTypegroup : typeGroups) {
			TSTypegroup.allTypeGroups.put(tsTypegroup.getTypegroupcode().toLowerCase(), tsTypegroup);
			List<TSType> types = this.commonDao.findByProperty(TSType.class, "TSTypegroup.id", tsTypegroup.getId());
			TSTypegroup.allTypes.put(tsTypegroup.getTypegroupcode().toLowerCase(), types);
		}

		// 获取商家支付宝信息
		Map<String, Object> resourcesMap = new HashMap<String, Object>();
		resourcesMap.put("resourcesType", "12");
		/*List resoucesList = resourcesDao.findResourcesList(resourcesMap);

		String partner = null;
		String sellerUserId = null;
		String notifyUrl = null;
		String key = null;
		if (NSCommonUtils.isListNotEmpty(resoucesList)) {

			for (Object object : resoucesList) {
				NSResourcesEntity resourcesEntity = (NSResourcesEntity) object;
				if ("partner".equals(resourcesEntity.getShowName())) {
					partner = resourcesEntity.getValue();
					AlipayConfig.partner = partner;
				} else if ("seller_user_id".equals(resourcesEntity.getShowName())) {
					sellerUserId = resourcesEntity.getValue();
					AlipayConfig.seller_user_id = sellerUserId;
				} else if ("notify_url".equals(resourcesEntity.getShowName())) {
					notifyUrl = resourcesEntity.getValue();
					AlipayConfig.notify_url = notifyUrl;
				} else if ("key".equals(resourcesEntity.getShowName())) {
					key = resourcesEntity.getValue();
					AlipayConfig.key = key;
				}
			}
		}
*/
	}

	public void refleshTypesCach(TSType type) {
		TSTypegroup tsTypegroup = type.getTSTypegroup();
		TSTypegroup typeGroupEntity = this.commonDao.get(TSTypegroup.class, tsTypegroup.getId());
		List<TSType> types = this.commonDao.findByProperty(TSType.class, "TSTypegroup.id", tsTypegroup.getId());
		TSTypegroup.allTypes.put(typeGroupEntity.getTypegroupcode().toLowerCase(), types);
	}

	public void refleshTypeGroupCach() {
		TSTypegroup.allTypeGroups.clear();
		List<TSTypegroup> typeGroups = this.commonDao.loadAll(TSTypegroup.class);
		for (TSTypegroup tsTypegroup : typeGroups) {
			TSTypegroup.allTypeGroups.put(tsTypegroup.getTypegroupcode().toLowerCase(), tsTypegroup);
		}
	}

	// ----------------------------------------------------------------
	// ----------------------------------------------------------------

	public Set<String> getOperationCodesByRoleIdAndFunctionId(String roleId, String functionId) {
		Set<String> operationCodes = new HashSet<String>();
		TSRole role = commonDao.get(TSRole.class, roleId);
		CriteriaQuery cq1 = new CriteriaQuery(TSRoleFunction.class);
		cq1.eq("TSRole.id", role.getId());
		cq1.eq("TSFunction.id", functionId);
		cq1.add();
		List<TSRoleFunction> rFunctions = getListByCriteriaQuery(cq1, false);
		if (null != rFunctions && rFunctions.size() > 0) {
			TSRoleFunction tsRoleFunction = rFunctions.get(0);
			if (null != tsRoleFunction.getOperation()) {
				String[] operationArry = tsRoleFunction.getOperation().split(",");
				for (int i = 0; i < operationArry.length; i++) {
					operationCodes.add(operationArry[i]);
				}
			}
		}
		return operationCodes;
	}

	public Set<String> getOperationCodesByUserIdAndFunctionId(String userId, String functionId) {
		Set<String> operationCodes = new HashSet<String>();
		List<TSRoleUser> rUsers = findByProperty(TSRoleUser.class, "TSUser.id", userId);
		for (TSRoleUser ru : rUsers) {
			TSRole role = ru.getTSRole();
			CriteriaQuery cq1 = new CriteriaQuery(TSRoleFunction.class);
			cq1.eq("TSRole.id", role.getId());
			cq1.eq("TSFunction.id", functionId);
			cq1.add();
			List<TSRoleFunction> rFunctions = getListByCriteriaQuery(cq1, false);
			if (null != rFunctions && rFunctions.size() > 0) {
				TSRoleFunction tsRoleFunction = rFunctions.get(0);
				if (null != tsRoleFunction.getOperation()) {
					String[] operationArry = tsRoleFunction.getOperation().split(",");
					for (int i = 0; i < operationArry.length; i++) {
						operationCodes.add(operationArry[i]);
					}
				}
			}
		}
		return operationCodes;
	}

	// ----------------------------------------------------------------
	// ----------------------------------------------------------------

	public void flushRoleFunciton(String id, TSFunction newFunction) {
		TSFunction functionEntity = this.getEntity(TSFunction.class, id);
		if (functionEntity.getTSIcon() == null || !StringUtil.isNotEmpty(functionEntity.getTSIcon().getId())) {
			return;
		}
		TSIcon oldIcon = this.getEntity(TSIcon.class, functionEntity.getTSIcon().getId());
		if (!oldIcon.getIconClas().equals(newFunction.getTSIcon().getIconClas())) {
			// 刷新缓存
			HttpSession session = ContextHolderUtils.getSession();
			TSUser user = ResourceUtil.getSessionUserName();
			List<TSRoleUser> rUsers = this.findByProperty(TSRoleUser.class, "TSUser.id", user.getId());
			for (TSRoleUser ru : rUsers) {
				TSRole role = ru.getTSRole();
				session.removeAttribute(role.getId());
			}
		}
	}

}
