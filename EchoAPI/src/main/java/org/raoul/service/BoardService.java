package org.raoul.service;

import java.util.List;

import org.raoul.domain.BoardVO;
import org.raoul.domain.Criteria;
import org.raoul.domain.PhotoDTO;

public interface BoardService extends CRUDGenericService<BoardVO, Integer> {

	
	public int getListCount(Criteria cri);
	
	public List<PhotoDTO> getAttachList(Integer bno);
}
