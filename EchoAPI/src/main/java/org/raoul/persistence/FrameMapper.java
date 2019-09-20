package org.raoul.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.raoul.domain.PhotoDTO;

@Mapper
public interface FrameMapper {

	public List<PhotoDTO> getLast20Photos( String ufid);
}
