package nz.co.s3m.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nz.co.s3m.member.dao.MemberDAOImpl;
import nz.co.s3m.member.service.MemberService;
import nz.co.s3m.member.vo.MemberVO;

import org.apache.commons.dbcp.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Controller
public class MemberController {

	@Autowired
	private MemberDAOImpl daoImpl;

	@Autowired
	private MemberService memberSvc;
	
	@Autowired
	private BasicDataSource myDatasource;

	private static final Logger logger = LoggerFactory
			.getLogger(MemberController.class);

	@RequestMapping(value = "/main.do", method = RequestMethod.GET)
	public String main(Locale locale, Model model) {
		logger.info("MemberController > main :", locale);

		List<MemberVO> memberList = memberSvc.getMemberList();
		model.addAttribute("result", memberList);

		return "kickstart/main";
	}

	// Return 'Page & Model' Type
	@RequestMapping(value = "/insert_noajax.do", method = RequestMethod.POST)
	public String insert_noajax(Model model, MemberVO member) {
		logger.info("MemberController > insert_noajax");

		// case1. public String insert(Model model, HttpServletRequest request){
		// && request.getParameter("...")
		// MemberVO member = new MemberVO();
		// member.setName(request.getParameter("name"));
		// case2. public String insert(Model model, String name, String email,
		// String phone) { ... }
		// MemberVO member = new MemberVO();
		// member.setName(name);
		// member.setEmail(email);
		// member.setPhone(phone);
		// case3. public String insert(Model model, MemberVO vo){
		// vo.getName(...) --> member variable names of VO should same as the
		// Http Request Parameter

		memberSvc.insert(member);
		List<MemberVO> memberList = memberSvc.getMemberList();
		model.addAttribute("result", memberList);

		return "kickstart/main_noajax";
	}

	// Return Request Body Content Type (ajax & json type)
	@RequestMapping(value = "/insert.do", method = RequestMethod.POST)
	@ResponseBody
	public List<MemberVO> insert(Model model, MemberVO member) {
		logger.info("MemberController > insert");

		memberSvc.insert(member);
		/*
		 * List<MemberVO> memberList = memberSvc.getMemberList();
		 * model.addAttribute("result", memberList);
		 */

		return memberSvc.getMemberList();
	}

	@RequestMapping(value = "/countMembers.do")
	@ResponseBody
	public int countMembers(
			@RequestParam(value = "currCnt", required = false) int currCnt) {
		/*
		 * @RequestParam annotation could be ommited (default value for
		 * 'required' is true)
		 */
		logger.info("MemberController > countMembers ] currCnt : " + currCnt);
		return memberSvc.countMembers();
	}

	@RequestMapping(value = "/delete.do")
	@ResponseBody
	public Map <String, String> delete(int regno) {
		logger.info("MemberController > delete ] map test ]]] regno : " + regno);

		Map <String, String> map = new HashMap<String, String>();
		int deleteCount = memberSvc.deleteMember(regno);

		if (deleteCount > 0) {
			map.put("ok", "Y");
			map.put("msg", "Deleted");
		} else {
			map.put("ok", "N");
			map.put("msg", "Error");
		}

		return map;
	}

	@RequestMapping(value = "/registerProc.do", method = RequestMethod.POST)
	public String registerProc(Locale locale, Model model,
			HttpServletRequest request, MemberVO member) {
		String realPath = request.getSession().getServletContext()
				.getRealPath("/upload");
		// String path=request.getServletContext().getRealPath("/"); // -->
		// servlet 3.0+

		File upload_dir = new File(realPath);
		if(! upload_dir.exists() ) {
			logger.warn("Creating File Upload Directory:"+ realPath);
			upload_dir.mkdirs();
		}
		
		
		logger.info("MemberController > registerProc : realPath] " + realPath);

		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		List<MultipartFile> files = multipartRequest.getFiles("aFile");

		String totalFiles = "";
		
		for (MultipartFile afile : files) {
			String afilename = afile.getOriginalFilename();
			logger.info("MemberController > registerProc : Originalfilename] "
					+ afilename);

			String now = (new SimpleDateFormat("yyyyMMdd_HHmmss"))
					.format(new Date());
			// String now = System.currentTimeMillis());
			String finalFileName = realPath + File.separator + now + "_"
					+ afilename;

			try {
				if (!afilename.trim().equals("")) {
					afile.transferTo(new File(finalFileName));
				}
				//totalFiles = totalFiles + finalFileName + "???" ;
				totalFiles = totalFiles + now + "_" + afilename + "???" ;

			} catch (Exception ex) {
				ex.printStackTrace();
			}

		}
		member.setFilesrc(totalFiles);
		memberSvc.insert(member);

		List<MemberVO> memberList = memberSvc.getMemberList();
		model.addAttribute("result", memberList);

		return "kickstart/main_noajax";
	}
	@RequestMapping(value = "/detail.do", method = RequestMethod.GET)
	public String detail(Model model, int regno) {
		logger.info("MemberController > detail ");
		
		MemberVO rtnMember = memberSvc.getMemberDetail(regno) ;
		
		String fileList = rtnMember.getFilesrc();
		List <String> attachFileList = new ArrayList<String>();
		if( (fileList != null) && (!fileList.trim().equals(""))){
			StringTokenizer st = new StringTokenizer(fileList, "???");
			while (st.hasMoreTokens()) {
				String tmpFileString = st.nextToken();
				if(!tmpFileString.trim().equals("")) {
					attachFileList.add(tmpFileString);
				}
			}
		}

		
		model.addAttribute("member", rtnMember);
		model.addAttribute("fList", attachFileList);
		return "kickstart/detail";
	}
	
	@RequestMapping(value = "/download.do")
	public void download(String p, String f, HttpServletRequest request, HttpServletResponse response) throws IOException{
		String fname = new String(f.getBytes("ISO8859_1"), "UTF-8");
		response.setHeader("Content-Disposition", "attachment;filename="+new String(fname.getBytes(), "ISO8859_1"));

		String realPath = request.getSession().getServletContext().getRealPath("/upload");
		String fullPath = realPath + File.separator +fname ;
		
		FileInputStream fin = new FileInputStream(fullPath);
		ServletOutputStream sout = response.getOutputStream();
		
		byte [] buff = new byte[1024];
		int size = 0;
		
		while( (size=fin.read(buff, 0, 1024)) != -1 ){
			sout.write(buff, 0, size);
		}
		fin.close();
		sout.close();
	
	}

	@RequestMapping(value = "/registerForm.do", method = RequestMethod.GET)
	public String registerForm(Locale locale, Model model) {
		logger.info("MemberController > registerForm :", locale);

		// List<MemberVO> memberList = memberSvc.getMemberList();
		// model.addAttribute("result", memberList);

		return "kickstart/register";
	}

	@RequestMapping(value = "/main_noajax.do", method = RequestMethod.GET)
	public String main_noajax(Locale locale, Model model) {
		logger.info("MemberController > main_noajax :", locale);

		List<MemberVO> memberList = memberSvc.getMemberList();
		model.addAttribute("result", memberList);

		return "kickstart/main_noajax";
	}
	
	@ResponseBody
	@RequestMapping(value = "/getMemListJSON.do", method = RequestMethod.GET, produces="text/plain;charset=UTF-8")
	public String getMemListJSON(Locale locale, Model model) {
		logger.info("MemberController > getMemListJSON :");
		return memberSvc.getMemberListJSON();
	}
	
	
	@RequestMapping(value = "/home.do", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG,
				DateFormat.LONG, locale);
		String formattedDate = dateFormat.format(date);
		model.addAttribute("serverTime", formattedDate);

		return "kickstart/home";
	}
	
	@RequestMapping(value = "/dbcp.do", method = RequestMethod.GET)
	public String dbcp(Locale locale, Model model) {
		logger.info("MemberController > DBCP");

		String active_status = myDatasource.getNumActive() + "/"
				+ myDatasource.getMaxActive();
		String idle_status = myDatasource.getNumIdle() + "/"
				+ myDatasource.getMaxIdle();

		model.addAttribute("active", active_status);
		model.addAttribute("idle", idle_status);

		return "kickstart/dbcp";
	}

	@ResponseBody
	@RequestMapping(value = "/ang05.do", method = RequestMethod.GET)
	public String ang05(Locale locale, Model model) {
		return "[ {\"name\" : \"Dave Jones\", \"city\" : \"Phoenix\"}, {\"name\" : \"Jamie Riley\",	\"city\" : \"Atlanta\"}, {\"name\" : \"Heedy Wahlin\",\"city\" : \"Chandler\"} ]";
	}

}
