package org.raoul.service;

import java.util.List;

import org.raoul.domain.Criteria;
import org.raoul.domain.MemberVO;
import org.raoul.persistence.MemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService{

	@Autowired
	MemberMapper mMapper;
	
	
	@Override
	public void add(MemberVO vo) {
		mMapper.insert(vo);
	}

	@Override
	public MemberVO read(Integer mno) {
		return mMapper.read(mno);
	}

	@Override
	public int modify(MemberVO vo) {
		return mMapper.update(vo);
	}

	@Override
	public int remove(Integer mno) {
		return mMapper.delete(mno);
	}

	@Override
	public List<MemberVO> getList(Criteria cri) {
		return mMapper.getList();
	}

}
