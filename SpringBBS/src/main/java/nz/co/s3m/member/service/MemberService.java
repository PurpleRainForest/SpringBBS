package nz.co.s3m.member.service;

import java.util.ArrayList;

import nz.co.s3m.member.dao.MemberDAOImpl;
import nz.co.s3m.member.vo.MemberVO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;

@Service
public class MemberService {
	private static final Logger logger = LoggerFactory
			.getLogger(MemberService.class);
	
	@Autowired
	private MemberDAOImpl daoImpl;	
	
	@Transactional(propagation = Propagation.REQUIRED)
	public ArrayList<MemberVO> getMemberList(){
		//MemberDAOImpl daoImpl = new MemberDAOImpl();
		return daoImpl.getMembers();
	}
	@Transactional(propagation = Propagation.REQUIRED)
	public String getMemberListJSON(){
		 Gson gson = new Gson();
		 String rtnString = gson.toJson(daoImpl.getMembers());
		 
		 return rtnString;
	}
	
	
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void insert(MemberVO member){
		logger.info("MemberService > insert ]]] regno:"+member.getRegno()+"   name:"+member.getName()+"   email:"+member.getEmail()+"    phone:" + member.getPhone()+"   filesrc:"+member.getFilesrc());
		daoImpl.insertMember(member);
	}
	/*@Transactional(propagation = Propagation.REQUIRED)
	public ArrayList<MemberVO> insert(MemberVO member){
		System.out.println("Service ]]] name:"+member.getName()+"   email:"+member.getEmail()+"    phone:"+member.getPhone());
		daoImpl.insertMember(member);
		return daoImpl.getMembers();
	}*/
	@Transactional(propagation = Propagation.REQUIRED)
	public int deleteMember(int regno){
		logger.info("MemberService > delete ]]] regno:"+regno);
		return daoImpl.deleteMember(regno);
	}	
	
	
	public MemberVO getMemberDetail(int regno){
		return daoImpl.getMemberDetail(regno);
	}
	
	public int countMembers(){
		return daoImpl.countMembers();
	}

}
