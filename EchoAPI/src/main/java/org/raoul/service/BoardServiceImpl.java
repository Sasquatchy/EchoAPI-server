package org.raoul.service;

import java.util.List;

import org.raoul.domain.BoardVO;
import org.raoul.domain.Criteria;
import org.raoul.domain.PhotoDTO;
import org.raoul.persistence.BoardMapper;
import org.raoul.persistence.PhotoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class BoardServiceImpl implements BoardService {

	@Autowired
	BoardMapper bMapper;
	
	@Autowired
	PhotoMapper pMapper;
	
	@Transactional
	@Override
	public void add(BoardVO vo) {
		
		bMapper.insert(vo);
		log.info("=-=-=-registering vo(board serviceimpl): "+vo);
		
		if(vo.getAttachList() == null || vo.getAttachList().size() <= 0) {
			return;
		}
		
		vo.getAttachList().forEach(attach ->{
			
			attach.setBno(vo.getBno());
			attach.setUid(vo.getUid());
			log.info("attach : " + attach);
			pMapper.insert(attach);
			});
		
		
	}

	@Override
	public BoardVO read(Integer key) {
		return bMapper.select(key);	}

	@Override
	public int modify(BoardVO vo) {
		//TODO : 어떻게 할지 방식 다시 구상해보기.
		return bMapper.update(vo);
	}

	@Override
	public int remove(Integer key) {

		pMapper.deleteWithBoard(key);
		bMapper.delete(key);
		return 0;
	}

	@Override
	public List<BoardVO> getList(Criteria cri) {
		List<BoardVO> result = null;
		result = bMapper.selectPage(cri);
		return result;
	}

	@Override
	public int getListCount(Criteria cri) {
		return bMapper.selectPageCount(cri);
	}

	@Override
	public List<PhotoDTO> getAttachList(Integer bno) {
		log.info("get attach list by bno " +bno);
		
		return pMapper.findListByBoard(bno);
	}

}
