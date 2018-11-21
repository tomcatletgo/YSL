package ycw.om.form.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartHttpServletRequest;



import core.common.service.impl.CommonServiceImpl;
import ycw.om.form.dao.MemberDao;
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int findMemberListTotal(Map<String, Object> pMap) throws RuntimeException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateMemberList(Map<String, Object> pMap) throws RuntimeException {
		// TODO Auto-generated method stub
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
