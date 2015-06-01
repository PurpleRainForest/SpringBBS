package nz.co.s3m.boards.service;

import java.util.ArrayList;

import nz.co.s3m.boards.dao.BoardDAOImpl;
import nz.co.s3m.boards.vo.BoardVO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;

@Service
public class BoardService {

	private static final Logger logger = LoggerFactory
			.getLogger(BoardService.class);

	@Autowired
	private BoardDAOImpl daoImpl;

	@Transactional(propagation = Propagation.REQUIRED)
	public ArrayList<BoardVO> boardList(int srow, int erow, String field, String query, String tbid) {
		//logger.info("BoardService > getClientSideList   ] field :"+field+",    query:"+query);
		
		return daoImpl.boardList(srow, erow, field, query, tbid);
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public BoardVO boardDetail(int bbsno, String tbid) {
		BoardVO vo = daoImpl.boardDetail(bbsno, tbid);
		int updateViewsCounts = daoImpl.updateBoardViewCount(bbsno, tbid);
		return vo;
	}
	
	public int addBoardLikes(int bbsno, String tbid){
		//logger.info("ClientSideService > addClietSideLikes ] bbsno :"+ bbsno);
		return daoImpl.addBoardLikes(bbsno, tbid);
	}
	
	public int updateBoard(BoardVO vo){
		return daoImpl.updateBoard(vo);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public int insertBoard(BoardVO vo){
		System.out.println(">>>>>vo.getRef_step:"+vo.getRef_step());
		System.out.println(">>>>>p_bbsno:"+vo.getRef_parent());
		System.out.println(">>>>>p_bbsno:"+vo.getRef_parent());
				
		int finalRefStep = 0;
		try {
			if( daoImpl.calcRefStep(vo.getTbid(), vo.getRef_no(), vo.getRef_no(), vo.getRef_level()) > 0 ){
				finalRefStep = daoImpl.calcRefStep(vo.getTbid(), vo.getRef_no(), vo.getRef_parent(), vo.getRef_level());
				System.out.println("]]]]]]]]case1: with siblings:"+finalRefStep);
			}	else {
				finalRefStep = ( daoImpl.boardDetail(vo.getRef_parent(), vo.getTbid()) ).getRef_step() + 1;
				System.out.println("]]]]]]]]case2: with parent:"+finalRefStep);
	
			}
		} catch(Exception ex){// ex.printStackTrace(); 
			
		}
		
		vo.setRef_step(finalRefStep);
		if( finalRefStep > 0) { // if it is not the root element article
			daoImpl.updateBoardRefOrders(vo.getTbid(), vo.getRef_no(), finalRefStep);
		}
		
		int insCount = daoImpl.insertBoard(vo);
		return insCount;
	}
	
	
	public int deleteBoard(int bbsno, String tbid){
		return daoImpl.deleteBoard(bbsno, tbid);
	}	
	
	public int totalBoardLists(String field, String query, String tbid) {
		return (daoImpl.totalBoardLists(field, query, tbid)).intValue();
	}
	public int nextBbsno(String tbid) {
		logger.info("BoardService > nextBbsno   ] tbid :"+tbid);
		return (daoImpl.nextBbsno(tbid).intValue());
	}
	public int getChildrenCount(String tbid, int bbsno, int ref_no) {
		logger.info("BoardService > String tbid, int bbsno, int ref_no   ] tbid :"+tbid +" bbsno :"+bbsno+"  ref_no:"+ref_no);
		return (daoImpl.getChildrenCount(tbid, bbsno, ref_no).intValue());
	}
		
	
	public String boardListJSON(int srow, int erow, String field, String query, String tbid) {
		Gson gson = new Gson();
		String rtnString = gson.toJson(daoImpl.boardList(srow, erow, field, query, tbid));
		return rtnString;
	}

}
