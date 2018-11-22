package ycw.om.form.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartHttpServletRequest;



import core.common.service.impl.CommonServiceImpl;
import ycw.om.form.dao.MemberDao;
import ycw.om.form.entity.MemberEntity;
import ycw.om.form.service.MemberService;

public class MemberServiceImpl extends CommonServiceImpl implements MemberService {
	private String urlFormat = "http://%s.%s/%s";

	MemberDao memberlistDao;
	

	public MemberDao getMemberlistDao() {
		return memberlistDao;
	}
	@Resource
    public void setMemberlistDao(MemberDao memberlistDao) {
		this.memberlistDao = memberlistDao;
	}

	@Override
	public <T> List<T> findMemberList(Map<String, Object> pMap) throws RuntimeException {
		
		return memberlistDao.findMemberList(pMap) ;
	}

	@Override
	public int findMemberListTotal(Map<String, Object> pMap) throws RuntimeException {
		
		return memberlistDao.findMemberListTotal(pMap);
	}

	@Override
	public int updateMemberList(Map<String, Object> pMap) throws RuntimeException {
		String memberId = getParamaterMapValue("member_id", pMap);
		String userId = getParamaterMapValue("user_id", pMap);
		String memberTime = getParamaterMapValue("member_time", pMap);
		String memberName = getParamaterMapValue("member_name", pMap);
		String memberPoints = getParamaterMapValue("member_points", pMap);
		String memberRegistrationDate = getParamaterMapValue("member_registration_date", pMap);
		String delFlag = getParamaterMapValue("del_flag", pMap);
		String createUserId = getParamaterMapValue("create_user_id", pMap);
		String updateTime = getParamaterMapValue("update_time", pMap);
		String updateUserId = getParamaterMapValue("update_user_id", pMap);
		String delTime = getParamaterMapValue("del_time", pMap);
		String delUserId = getParamaterMapValue("del_user_id", pMap);
		
		MemberEntity updatememberlistEntity =new MemberEntity();
		
		updatememberlistEntity.setMemberId(memberId);
		updatememberlistEntity.setUserId(userId);
		updatememberlistEntity.setMemberTime(memberTime);
		updatememberlistEntity.setMemberName(memberName);
		updatememberlistEntity.setMemberPoints(memberPoints);
		updatememberlistEntity.setMemberRegistrationDate(memberRegistrationDate);
		updatememberlistEntity.setDelFlag(delFlag);
		updatememberlistEntity.setCreateUserId(createUserId);
		updatememberlistEntity.setUpdateTime(updateTime);
		updatememberlistEntity.setUpdateUserId(updateUserId);
		updatememberlistEntity.setDelTime(delTime);
		updatememberlistEntity.setDelUserId(delUserId);
		
		
		
		
		return 0;
	}

	@Override
	public int saveMemberList(MultipartHttpServletRequest multiRequest, Map<String, String[]> pMap)
			throws RuntimeException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delMemberById(String memberRowId) throws RuntimeException {
		// TODO Auto-generated method stub
		return 0;
	}

}
