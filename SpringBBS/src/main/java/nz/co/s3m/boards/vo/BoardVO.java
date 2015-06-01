package nz.co.s3m.boards.vo;

import java.util.Date;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class BoardVO {

	private int bbsno;
	private int ref_no;
	private int ref_parent;
	private int ref_level;
	private int ref_step;
	private String title;
	private String cont;
	private String writer;
	private Date cdate;
	private Date mdate;
	private int views;
	private int likes;
	private CommonsMultipartFile multipartFile;
	private String aFile;
	private String deleted;
	private String tbid;

	public int getBbsno() {
		return bbsno;
	}

	public void setBbsno(int bbsno) {
		this.bbsno = bbsno;
	}

	public int getRef_no() {
		return ref_no;
	}

	public void setRef_no(int ref_no) {
		this.ref_no = ref_no;
	}
	
	public int getRef_parent() {
		return ref_parent;
	}

	public void setRef_parent(int ref_parent) {
		this.ref_parent = ref_parent;
	}

	public int getRef_level() {
		return ref_level;
	}

	public void setRef_level(int ref_level) {
		this.ref_level = ref_level;
	}

	public int getRef_step() {
		return ref_step;
	}

	public void setRef_step(int ref_step) {
		this.ref_step = ref_step;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCont() {
		return cont;
	}

	public void setCont(String cont) {
		this.cont = cont;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public Date getCdate() {
		return cdate;
	}

	public void setCdate(Date cdate) {
		this.cdate = cdate;
	}

	public Date getMdate() {
		return mdate;
	}

	public void setMdate(Date mdate) {
		this.mdate = mdate;
	}

	public int getViews() {
		return views;
	}

	public void setViews(int views) {
		this.views = views;
	}

	public int getLikes() {
		return likes;
	}

	public void setLikes(int likes) {
		this.likes = likes;
	}

	public CommonsMultipartFile getMultipartFile() {
		return multipartFile;
	}

	public void setMultipartFile(CommonsMultipartFile multipartFile) {
		this.multipartFile = multipartFile;
	}

	public String getaFile() {
		return aFile;
	}

	public void setaFile(String aFile) {
		this.aFile = aFile;
	}

	public String getDeleted() {
		return deleted;
	}

	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}

	public String getTbid() {
		return tbid;
	}

	public void setTbid(String tbid) {
		this.tbid = tbid;
	}
	
	
	

}