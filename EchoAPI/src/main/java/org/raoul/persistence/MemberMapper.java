package org.raoul.persistence;


import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.raoul.domain.BoardVO;
import org.raoul.domain.Criteria;
import org.raoul.domain.MemberVO;

@Mapper
public interface MemberMapper {

	public MemberVO read(Integer mno);
	
	@Insert("insert into echo_member (uid,pw,username,mobile) values(#{uid},#{pw},#{username},#{mobile})")
	public void insert(MemberVO vo); 
	
	
	public int update(MemberVO vo);
	
	@Delete("delete from echo_member where mno = #{mno}")
	public int delete(Integer mno);
	
	@Select("select from echo_member where uid=#{uid} and pw=#{pw}")
	public MemberVO findByUidAndPw(String uid, String pw);
	
	@Select("select * from echo_member where mno>0 order by mno desc")
	public List<MemberVO> getList();
	
	public List<BoardVO> selectPage(Criteria cri);
}
