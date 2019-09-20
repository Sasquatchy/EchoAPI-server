package org.raoul.service;

import java.util.List;

import org.raoul.domain.Criteria;

public interface CRUDGenericService<VO, K>  {
	
	public void add(VO vo);
	
	public VO read(K key);
	
	public int modify(VO vo);
	
	public int remove(K key);
	
	public List<VO> getList(Criteria cri);

}
