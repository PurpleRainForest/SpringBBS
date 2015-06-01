package nz.co.s3m.boards.dao;

import java.util.ArrayList;

import nz.co.s3m.boards.vo.BoardVO;

import org.apache.ibatis.annotations.Param;

public interface BoardDAO {
	
	public ArrayList<BoardVO> boardList(int srow, int erow, String field, String query, String tbid);
	public BoardVO boardDetail(int bbsno, String tbid);
	public int updateBoardViewCount(int bbsno, String tbid);
	public int addBoardLikes(int bbsno, String tbid);
	
	public int insertBoard(BoardVO vo);
	public Integer nextBbsno(@Param("tbid") String tbid);
	public int updateBoardRefOrders(@Param("tbid") String tbid, @Param("ref_no") int ref_no, @Param("ref_step") int ref_step); // for hierarchical insert
	public int updateBoard(BoardVO vo);
	public int deleteBoard(int bbsno, String tbid); 
	public Integer totalBoardLists(String field, String query, String tbid);
	
	public Integer calcRefStep(@Param("tbid") String tbid, @Param("ref_no") int ref_no, @Param("ref_parent") int ref_parent, @Param("ref_level") int ref_level);
	public Integer getChildrenCount(@Param("tbid") String tbid, @Param("bbsno") int bbsno, @Param("ref_no") int ref_no);

}
