package nz.co.s3m.member.dao;

import java.util.ArrayList;

import nz.co.s3m.member.vo.MemberVO;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MemberDAOImpl implements MemberDAO {

	@Autowired
	private SqlSession sqlSession;

	@Override
	public ArrayList<MemberVO> getMembers() {
		ArrayList<MemberVO> result = new ArrayList<MemberVO>();
		MemberDAO dao = sqlSession.getMapper(MemberDAO.class);
		result = dao.getMembers();
		
		return result;
	}

	@Override
	public Integer countMembers() {
		return sqlSession.getMapper(MemberDAO.class).countMembers();
	}

	@Override
	public void insertMember(MemberVO member) {
		MemberDAO dao = sqlSession.getMapper(MemberDAO.class);
		System.out.println("DAOImpl ]]] name:"+member.getName()+"   email:"+member.getEmail()+"    phone:"+member.getPhone() +"   filesrc:"+member.getFilesrc());
		dao.insertMember(member);
	}


	@Override
	public void updateMember(String name) {
		
	}

	@Override
	public MemberVO getMemberDetail(int regno) {
		MemberDAO dao = sqlSession.getMapper(MemberDAO.class);
		return dao.getMemberDetail(regno);
	}
	
	@Override
	public int deleteMember(int regno) {
		MemberDAO dao = sqlSession.getMapper(MemberDAO.class);
		return dao.deleteMember(regno);
	}

}
