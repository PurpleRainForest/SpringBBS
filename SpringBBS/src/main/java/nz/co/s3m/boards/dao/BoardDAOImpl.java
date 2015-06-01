package nz.co.s3m.boards.dao;

import java.util.ArrayList;

import nz.co.s3m.boards.vo.BoardVO;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BoardDAOImpl implements BoardDAO {
	
	private static final Logger logger = LoggerFactory
			.getLogger(BoardDAOImpl.class);
	
	@Autowired
	private SqlSession sqlSession;		

	@Override
	public ArrayList<BoardVO> boardList(int srow, int erow, String field, String query, String tbid) {
		//logger.info("BoardDAOImpl > boardList   ] field :"+field+",    query:"+query);
		
		
		ArrayList<BoardVO> result = new ArrayList<BoardVO>();
		BoardDAO dao = sqlSession.getMapper(BoardDAO.class);
		result = dao.boardList(srow, erow, field, query, tbid);
		return result;
	}

	@Override
	public BoardVO boardDetail(int bbsno, String tbid) {
		BoardDAO dao = sqlSession.getMapper(BoardDAO.class);
		return dao.boardDetail(bbsno, tbid);
	}

	@Override
	public int insertBoard(BoardVO vo) {
		BoardDAO dao = sqlSession.getMapper(BoardDAO.class);
		return dao.insertBoard(vo);
	}

	@Override
	public int updateBoardRefOrders(String tbid, int ref_no, int ref_step) {
		BoardDAO dao = sqlSession.getMapper(BoardDAO.class);
		return dao.updateBoardRefOrders(tbid, ref_no, ref_step);
	}

	@Override
	public int updateBoard(BoardVO vo) {
		BoardDAO dao = sqlSession.getMapper(BoardDAO.class);
		return dao.updateBoard(vo);
	}

	@Override
	public int updateBoardViewCount(int bbsno, String tbid) {
		BoardDAO dao = sqlSession.getMapper(BoardDAO.class);
		return dao.updateBoardViewCount(bbsno, tbid);
	}
	
	@Override
	public int addBoardLikes(int bbsno, String tbid) {
		BoardDAO dao = sqlSession.getMapper(BoardDAO.class);
		return dao.addBoardLikes(bbsno, tbid);
	}
	
	@Override
	public int deleteBoard(int bbsno, String tbid) {
		BoardDAO dao = sqlSession.getMapper(BoardDAO.class);
		return dao.deleteBoard(bbsno, tbid);
	}

	@Override
	public Integer totalBoardLists(String field, String query, String tbid) {
		BoardDAO dao = sqlSession.getMapper(BoardDAO.class);
		return dao.totalBoardLists(field, query, tbid);
	}
	
	@Override
	public Integer nextBbsno(@Param("tbid") String tbid) {
		BoardDAO dao = sqlSession.getMapper(BoardDAO.class);
		//logger.info("BoardDAOImpl > nextBbsno   ] tbid :"+tbid);
		return dao.nextBbsno(tbid);
	}	
	
	@Override
	public Integer calcRefStep(String tbid, int ref_no, int ref_parent, int ref_level) {
		BoardDAO dao = sqlSession.getMapper(BoardDAO.class);
		return dao.calcRefStep(tbid, ref_no, ref_parent, ref_level);
	}

	@Override
	public Integer getChildrenCount(String tbid, int bbsno, int ref_no) {
		BoardDAO dao = sqlSession.getMapper(BoardDAO.class);
		return dao.getChildrenCount(tbid, bbsno, ref_no);
	}
	
}
