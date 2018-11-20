package ycw.system.service;

import java.util.List;

import core.common.service.CommonService;
import ycw.system.pojo.base.TSAttachment;

/**
 * 
 * @author  张代浩
 *
 */
public interface DeclareService extends CommonService{
	
	public List<TSAttachment> getAttachmentsByCode(String businessKey,String description);
	
}
