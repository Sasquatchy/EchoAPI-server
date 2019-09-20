package org.raoul.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.raoul.domain.PhotoDTO;

@Mapper
public interface PhotoMapper {

	public void insert(PhotoDTO dto);
	
	public void insertList(List<PhotoDTO> list);
	
	@Delete("delete from echo_photo where pno = #{pno}")
	public int delete(Integer pno);
	
	public int deleteList(List<PhotoDTO> list);
	
	@Delete("delete from echo_photo where bno = #{bno}")
	public int deleteWithBoard(Integer bno);
	
	@Select("select * from echo_photo where bno=#{bno} order by pno desc")
	public List<PhotoDTO> findListByBoard(Integer bno);

	@Select("select * from echo_photo where uid=#{uid} order by pno desc")
	public List<PhotoDTO> findListByMember(String uid);

	@Select("select * from echo_photo where pno=#{pno}")
	public PhotoDTO select(Integer pno);
	
	
}
