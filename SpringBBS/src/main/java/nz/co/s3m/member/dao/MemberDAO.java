package nz.co.s3m.member.dao;

import java.util.ArrayList;

import nz.co.s3m.member.vo.MemberVO;

public interface MemberDAO {

	/* method name of DAO interface should be same with id attribute in mapper xml */
	public ArrayList<MemberVO> getMembers();
	public MemberVO getMemberDetail(int regno);
	public void insertMember(MemberVO member);
	public void updateMember(String name);
	public int deleteMember(int regno);
	public Integer countMembers();

}

