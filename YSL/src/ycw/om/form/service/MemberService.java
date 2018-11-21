package ycw.om.form.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartHttpServletRequest;

public interface MemberlistService {
	public <T> List<T> findMemberList(Map<String,Object> pMap) throws RuntimeException;
	
	public int findMemberListTotal(Map<String,Object> pMap) throws RuntimeException;
	
	public int updateMemberList(Map<String,Object> pMap) throws RuntimeException;
	
	public int saveMemberList(MultipartHttpServletRequest multiRequest,Map<String,String[]> pMap) throws RuntimeException;
	
	public int delMemberById(String memberRowId)throws RuntimeException;
}
