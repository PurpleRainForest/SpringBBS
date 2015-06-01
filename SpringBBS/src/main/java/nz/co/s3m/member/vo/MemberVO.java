package nz.co.s3m.member.vo;

import java.util.Date;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class MemberVO {
	private int regno;
	private String name;
	private String email;
	private String phone;
	private Date regdate;
	private String filesrc;
	private CommonsMultipartFile aFile;

	public int getRegno() {
		return regno;
	}

	public void setRegno(int regno) {
		this.regno = regno;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Date getRegdate() {
		return regdate;
	}

	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}

	public String getFilesrc() {
		return filesrc;
	}

	public void setFilesrc(String filesrc) {
		this.filesrc = filesrc;
	}

	public CommonsMultipartFile getaFile() {
		return aFile;
	}

	public void setaFile(CommonsMultipartFile aFile) {
		this.aFile = aFile;
	}



}
