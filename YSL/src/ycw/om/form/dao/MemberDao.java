package ycw.om.form.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import ycw.om.form.entity.MemberEntity;




@Repository
public interface MemberDao {
    public <T> List<T> findMemberList(Map<String,Object> pMap) throws RuntimeException;
	
    public int findMemberListTotal(Map<String,Object> pMap) throws RuntimeException;
    
    public int saveMemberList(MemberEntity addEntity) throws RuntimeException;
    
    public int updateMemberList(Map<String,Object> pMap) throws RuntimeException;
    
    public int delMemberById(@Param("goodsRowId") String goodsRowId);

}
