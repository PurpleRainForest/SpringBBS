package nz.co.s3m.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
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

import nz.co.s3m.boards.service.BoardService;
import nz.co.s3m.boards.vo.BoardVO;
import nz.co.s3m.commons.CKEditorFileBean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Controller
public class BoardController {
	@Autowired
	private BoardService svc;

	private static final Logger logger = LoggerFactory
			.getLogger(BoardController.class);
	
	@RequestMapping(value = "/nzDevMain.do", method = RequestMethod.GET)
	public String nzDevMain(Locale locale, Model model, HttpServletRequest request)  {
		logger.info("BoardController > nzDevMain   ] ", locale);
		return "nzdev/nzDevMain";
	}
	
	
	@RequestMapping(value = "/boardList.do", method = RequestMethod.GET)
	public String boardList(Locale locale, Model model, HttpServletRequest request)  {
		logger.info("BoardController > boardList   ] ", locale);
		

		// board table name 
		String tbid = request.getParameter("tbid");
		
		// for search filter
		String field = request.getParameter("field");
		if (field == null || field.trim().equals("")) {
			field = "title";
		}
		String query = request.getParameter("query");
		if (query == null || query.trim().equals("")) {
			query = "";
		} else {
			try { 
				query = new String(query.getBytes("ISO-8859-1"), "UTF-8"); 
			} catch(Exception ex){ 
				ex.printStackTrace(); 
			};
		}			

		//request.setAttribute("query", query);
		model.addAttribute("query", query);  // using model objects is better for utf-8 characters ...
		request.setAttribute("field", field);
		request.setAttribute("tbid", tbid);
		
		// for page Navigation
		int page = 1;
		String pg = request.getParameter("pg");
		if (pg != null && !pg.trim().equals("")) {
			page = Integer.parseInt(pg);
		}

		int pageNavLen  = 0; // page Navigation List size
		String pgNavLen = (request.getParameter("pgNavLen") != null && !request.getParameter("pgNavLen").trim().equals("")) ? request.getParameter("pgNavLen") : "5" ;
		pageNavLen = Integer.parseInt(pgNavLen);

		int contentsPerPage = 0; // contents list size in a page 
		String contentPerPg = (request.getParameter("contentPerPg") != null && !request.getParameter("contentPerPg").trim().equals("")) ? request.getParameter("contentPerPg") : "10" ;
		contentsPerPage = Integer.parseInt(contentPerPg);
		
		int tRecord = svc.totalBoardLists(field, query, tbid);
		
		int sPage    = page - (page-1)%pageNavLen; // First Page in Page Nav
		int ePage      = (tRecord/contentsPerPage) + (tRecord%contentsPerPage==0 ? 0 : 1); // Last Page correction
		request.setAttribute("sPage", sPage);
		request.setAttribute("ePage", ePage);
		request.setAttribute("page", page);
		request.setAttribute("tRecord", tRecord);
		request.setAttribute("pgNavLen", pageNavLen);
		request.setAttribute("contentPerPg", contentPerPg);

		int srow = 1 + (page-1)*contentsPerPage;     // an = a1+ (n-1)*d
		int erow = contentsPerPage + (page-1)*contentsPerPage; 
		
		
		

		List<BoardVO> webList = svc.boardList(srow, erow, field, query, tbid);
		// 							(int srow, int erow, String field, String query)
		model.addAttribute("result", webList);

		logger.info("                                        ] selected: " + webList.size() + " rows");
		return "boards/boardList";
	}
	
	@RequestMapping(value = "/boardDetail.do", method = RequestMethod.GET)
	public String boardDetail(Model model, int bbsno, String tbid, String pg, String field, String query, HttpServletRequest request) {
		logger.info("BoardController > boardDetail ] bbsno :"+ bbsno);
		
		// System.out.println("-----------------------------------------------");
		// System.out.println("pg:" + pg+"   field:"+field + "   query:"+query);
		// System.out.println("-----------------------------------------------");

		BoardVO vo = svc.boardDetail(bbsno, tbid);
		
		model.addAttribute("result", vo);
		request.setAttribute("tbid", tbid);
		
		
		///// attach file link generation (download)
		if( (vo.getaFile() != null) && (! vo.getaFile().trim().equals("")) ) {
			// String realPath = request.getSession().getServletContext().getRealPath("/upload") + File.separator;
			
			List <String> storedFiles = new ArrayList<String>();
			List <String> orgFileNames = new ArrayList<String>();
			List <String> attachFileLinks = new ArrayList<String>();
			StringTokenizer st = new StringTokenizer(vo.getaFile(), "$$$");
			
			
			while(st.hasMoreElements()){
				//String storedFile = realPath+st.nextToken();
				String storedFile = st.nextToken();
				storedFiles.add(storedFile);
				// System.out.println("----------------------");
				// System.out.println("storedFile:"+storedFile);
	
				StringTokenizer st2 = new StringTokenizer(storedFile, "___");
				st2.nextToken();st2.nextToken();
				String orgFile = st2.nextToken();
				orgFileNames.add(orgFile);
				// System.out.println("orgFile:"+orgFile);
				// System.out.println("----------------------");
				
				String attachFileLink = "<a href='boardFileDownload.do?p=upload&f=" + storedFile 
						+ "' class='btn btn-default btn-sm'><span class='glyphicon glyphicon-save'></span> &nbsp;" 
						+ orgFile + "</a>&nbsp;&nbsp;&nbsp;&nbsp;";
				attachFileLinks.add(attachFileLink);
				// System.out.println("attachFileLinks:"+attachFileLink);
			}
			model.addAttribute("storedFiles", storedFiles);
			model.addAttribute("orgFileNames", orgFileNames);
			model.addAttribute("attachFileLinks", attachFileLinks);
		}
		///// attach file link generation (download)

		
		model.addAttribute("page", pg);
		model.addAttribute("field", field);
		model.addAttribute("query", query);		

		return "boards/boardDetail";
	}	
	
	@ResponseBody
	@RequestMapping(value = "/addBoardLikes.do", method = RequestMethod.POST)
	public Map <String, String> addBoardLikes(int bbsno, String tbid) {
		logger.info("BoardController > addBoardLikes ] bbsno :"+ bbsno);
		Map <String, String> map = new HashMap<String, String>();

		int procCount = svc.addBoardLikes(bbsno, tbid);

		if (procCount > 0) {
			map.put("ok", "Y");
		} else {
			map.put("ok", "N");
		}
		return map;	
	}

	
	@RequestMapping(value = "/boardEdit.do", method = RequestMethod.GET)
	public String boardEdit(Model model, int bbsno, String tbid, HttpServletRequest request) {
		logger.info("BoardController > boardEdit ] bbsno :"+ bbsno);

		BoardVO vo = svc.boardDetail(bbsno, tbid);
		model.addAttribute("result", vo);
		request.setAttribute("tbid", tbid);
		
		
		///// attach file link generation (delete)
		if( (vo.getaFile() != null) && (! vo.getaFile().trim().equals("")) ) {
			
			List <String> storedFiles = new ArrayList<String>();
			List <String> orgFileNames = new ArrayList<String>();
			List <String> removeFileLinks = new ArrayList<String>();
			StringTokenizer st = new StringTokenizer(vo.getaFile(), "$$$");
			
			int idx = 1;
			while(st.hasMoreElements()){
				//String storedFile = realPath+st.nextToken();
				String storedFile = st.nextToken();
				storedFiles.add(storedFile);
				// System.out.println("----------------------");
				// System.out.println("storedFile:"+storedFile);
	
				StringTokenizer st2 = new StringTokenizer(storedFile, "___");
				st2.nextToken();st2.nextToken();
				String orgFile = st2.nextToken();
				orgFileNames.add(orgFile);
				// System.out.println("orgFile:"+orgFile);
				// System.out.println("----------------------");
				
				/*String removeFileLink = "<a href='boardFileDelete.do?bbsno=" + vo.getBbsno() + "&storedFile=" + storedFile 
						+"' class='btn btn-warning btn-sm'><span class='glyphicon glyphicon-trash'></span> &nbsp;" + orgFile + " &nbsp; &nbsp; &nbsp; &nbsp;</a>" ;*/
				

				
				String removeFileLink = "<button type='button' class='btn btn-warning' id='removeAFileBtn"
						+ idx +"' onclick='javascript:removeAFileButton(\"aFile\", \"removeAFileBtn"+ idx +"\", \"" +storedFile+"$$$\");'>" + orgFile+ "</button>";

				//String removeFileLink = "<div class='form-horizontal'>  <a href='boardFileDelete.do?bbsno=" + vo.getBbsno() + "&storedFile=" + storedFile 
				//		+"' class='btn btn-warning btn-sm'><span class='glyphicon glyphicon-trash'></span> &nbsp;" + orgFile + " &nbsp; &nbsp; &nbsp; &nbsp;</a> </div>" ;
				
				removeFileLinks.add(removeFileLink);
				// System.out.println("removeFileLinks:"+removeFileLink);
				
				idx = idx + 1;
			}
			model.addAttribute("storedFiles", storedFiles);
			model.addAttribute("orgFileNames", orgFileNames);
			model.addAttribute("removeFileLinks", removeFileLinks);
		}
		///// attach file link generation (delete)	
		
		return "boards/boardEdit";
	}	
	
	@ResponseBody
	@RequestMapping(value = "/boardEditProc.do", method = RequestMethod.POST)
	public  Map <String, String> boardEditProc(BoardVO vo) {
		logger.info("BoardController > boardEditProc ] bbsno :"+ vo.getBbsno());
		//logger.info("BoardController > boardEditProc ] bbsno :"+ vo.getBbsno() +"   title :"+vo.getTitle() + "   tbid:"+vo.getTbid() + " aFile:"+vo.getaFile());
		/*logger.info("BoardController > boardEditProc ] bbsno :"+ vo.getBbsno() +"   title :"
						+vo.getTitle() +"   cont.:"+vo.getCont() + "   FileName:"+vo.getaFile() + "   tbid:"+vo.getTbid() + " aFile:"+vo.getaFile());*/
		
		Map <String, String> map = new HashMap<String, String>();
		int procCount = svc.updateBoard(vo);

		if (procCount > 0) {
			map.put("ok", "Y");
			map.put("msg", procCount+" rows updated");
		} else {
			map.put("ok", "N");
			map.put("msg", "Error");
		}
		
		return map;		
	}	
	
	@ResponseBody
	@RequestMapping(value = "/boardInsertProc.do", method = RequestMethod.POST)
	public Map <String, String> boardInsertProc(Model model, HttpServletRequest request, BoardVO vo, int ref_parent) {
		logger.info("BoardController > boardInsertProc ] tbid ::::"+ vo.getTbid() + "  ref_parent ::::" + ref_parent);
		request.setAttribute("tbid", vo.getTbid());

		Map <String, String> map = new HashMap<String, String>();	
		
		if( vo.getRef_no() > 0 ){
		} else {
			vo.setRef_no(svc.nextBbsno(vo.getTbid()));
			// root level article -> bbsno == ref_no
		}
		
		int procCount = svc.insertBoard(vo);

		if (procCount > 0) {
			map.put("ok", "Y");
			map.put("msg", procCount+" rows updated");
		} else {
			map.put("ok", "N");
			map.put("msg", "Error");
		}
		
		return map;		
		
	}			
	
	
	@RequestMapping(value = "/boardInsert.do", method = RequestMethod.GET)
	public String boardInsert(Model model, BoardVO vo, HttpServletRequest request, String ref_parent, String pg, String field, String query, String ref_level, String ref_step) {
		logger.info("BoardController > boardInsert ] tbid"+ vo.getTbid() + "    ref_no:"+request.getParameter("ref_no") );
		request.setAttribute("tbid", vo.getTbid());
		
		String ref_bbsno= (request.getParameter("ref_no") != null && !request.getParameter("ref_no").trim().equals("")) ? request.getParameter("ref_no") : "0" ;
		model.addAttribute("ref_no", ref_bbsno);
		
		String ref_level_param= (request.getParameter("ref_level") != null && !request.getParameter("ref_level").trim().equals("")) ? request.getParameter("ref_level") : "0" ;
		request.setAttribute("ref_level", ref_level_param);
		
		String ref_step_param= (request.getParameter("ref_step") != null && !request.getParameter("ref_step").trim().equals("")) ? request.getParameter("ref_step") : "0" ;
		request.setAttribute("ref_step", ref_step_param);		

		String ref_parent_param= (request.getParameter("ref_parent") != null && !request.getParameter("ref_parent").trim().equals("")) ? request.getParameter("ref_parent") : "0" ;
		System.out.println("]]]] ref_parent ]]]"+ref_parent_param);
		model.addAttribute("ref_parent", ref_parent_param);
		
		request.setAttribute("pg", pg);
		request.setAttribute("field", field);
		request.setAttribute("query", query);
		
		//System.out.println("******** p_bbsno:"+p_bbsno);
		
		
		return "boards/boardInsert";
	}	

	@ResponseBody
	@RequestMapping(value = "/boardDelete.do", method = RequestMethod.POST)
	public Map <String, String> boardDelete(Model model, String tbid, int bbsno, int ref_no) {
		logger.info("BoardController > boardDelete ] bbsno :"+ bbsno);
		
		Map <String, String> map = new HashMap<String, String>();

		int childrenCount = svc.getChildrenCount(tbid, bbsno, ref_no);
		//System.out.println(">>>> childrenCount <<<<:"+childrenCount);
		
		if(childrenCount > 0) {
			map.put("ok", "C");	
		}	else {
			int procCount = svc.deleteBoard(bbsno, tbid);
			if(procCount > 0) {
				map.put("ok", "Y");
			} else {
				map.put("ok", "N");
			}
		}
		
		return map;		
	}	
	
	
	
/*	@RequestMapping(value = "/boardDelete.do", method = RequestMethod.GET)
	public String boardDelete(Model model, String tbid, int bbsno, int ref_no) {
		logger.info("BoardController > boardDelete ] bbsno :"+ bbsno);

		int childrenCount = svc.getChildrenCount(tbid, bbsno, ref_no);
		
		//int procCount = svc.deleteBoard(bbsno, tbid);
		//logger.info("                                        ] deleted rows :"+ procCount);
		
		return "redirect:boardList.do?tbid="+tbid;		
	}	*/
	
	@ResponseBody
	@RequestMapping(value = "/boardListJson.do", method = RequestMethod.GET, produces="text/plain;charset=UTF-8")
	public String boardListJson(Locale locale, Model model, HttpServletRequest request) {
		logger.info("BoardController > boardListJson :", locale);	
		
		// board table name 
		String tbid = request.getParameter("tbid");
		
		String field = request.getParameter("field");
		if (field == null || field.trim().equals("")) {
			field = "title";
		}
		String query = request.getParameter("query");
		if (query == null || query.trim().equals("")) {
			query = "";
		} else {
			try { 
				query = new String(query.getBytes("ISO-8859-1"), "UTF-8"); 
			} catch(Exception ex){ 
				ex.printStackTrace(); 
			};
		}	
		
		// for page Navigation
		int page = 1;
		String pg = request.getParameter("pg");
		if (pg != null && !pg.trim().equals("")) {
			page = Integer.parseInt(pg);
		}

		int pageNavLen  = 0; // page Navigation List size
		String pgNavLen = (request.getParameter("pgNavLen") != null && !request.getParameter("pgNavLen").trim().equals("")) ? request.getParameter("pgNavLen") : "5" ;
		pageNavLen = Integer.parseInt(pgNavLen);

		int contentsPerPage = 0; // contents list size in a page 
		String contentPerPg = (request.getParameter("contentPerPg") != null && !request.getParameter("contentPerPg").trim().equals("")) ? request.getParameter("contentPerPg") : "10" ;
		contentsPerPage = Integer.parseInt(contentPerPg);
		
		int tRecord = svc.totalBoardLists(field, query, tbid);
		int sPage    = page - (page-1)%pageNavLen; // First Page in Page Nav
		int ePage      = (tRecord/contentsPerPage) + (tRecord%contentsPerPage==0 ? 0 : 1); // Last Page correction
		request.setAttribute("sPage", sPage);
		request.setAttribute("ePage", ePage);
		request.setAttribute("page", page);
		request.setAttribute("tRecord", tRecord);
		request.setAttribute("pgNavLen", pageNavLen);
		request.setAttribute("contentPerPg", contentPerPg);	
		
		int srow = 1 + (page-1)*contentsPerPage;     // an = a1+ (n-1)*d
		int erow = contentsPerPage + (page-1)*contentsPerPage; 

		return svc.boardListJSON(srow, erow, field, query, tbid);
	}
	
	@RequestMapping(value = "/boardCKEditFileUpload.do", method = RequestMethod.POST)
	public String procFileUpload(CKEditorFileBean fileBean,
			HttpServletRequest request, Model model) {
		logger.info("BoardController > procFileUpload");

		String root_path = request.getSession().getServletContext()
				.getRealPath("/");
		String attach_path = "upload_ckeditor/" ;
		//String attach_path = "upload_ckeditor" + File.separator ;

		File upload_dir = new File(root_path + attach_path);
		if(! upload_dir.exists() ) {
			logger.warn("Creating CKEditor Upload Directory:"+ root_path + attach_path);
			upload_dir.mkdirs();
		}
		
		MultipartFile upload = fileBean.getUpload();
		String filename = "";
		String CKEditorFuncNum = "";

		if (upload != null) {
			filename = upload.getOriginalFilename();
			
			String now = (new SimpleDateFormat("yyyyMMdd_HHmmss"))
					.format(new Date());
			filename = now + "_" + filename;			
			
			fileBean.setFilename(filename);
			CKEditorFuncNum = fileBean.getCKEditorFuncNum();

			try {
				File file = new File(root_path + attach_path + filename);
				logger.info("::: root_path:" + root_path + "   :::attach_path:"
						+ attach_path + "   :::filename:" + filename);
				upload.transferTo(file);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

		String file_path = "/s3m/" + attach_path + filename;
		model.addAttribute("file_path", file_path);
		model.addAttribute("CKEditorFuncNum", CKEditorFuncNum);

		return "boards/ckeditorFileupload";
	}
	
	
    @RequestMapping(value = "/boardFileUpload.do", method = RequestMethod.POST)
	@ResponseBody
    public Map <String, String> boardFileUpload(Model model, HttpServletRequest request) throws Exception {
        logger.info("BoardController > boardFileUpload ");
		
		String realPath = request.getSession().getServletContext().getRealPath("/upload");
        logger.info("BoardController > boardFileUpload ] upload path:" + realPath);

		
		File upload_dir = new File(realPath);
		if(! upload_dir.exists() ) {
			logger.warn("Creating File Upload Directory:"+ realPath);
			upload_dir.mkdirs();
		}
		String now = (new SimpleDateFormat("yyyyMMdd_HHmmss")).format(new Date());

		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		List<MultipartFile> uploadFiles = multipartRequest.getFiles("fileUpload");
		String savedFileNames = "";
		int uploadFileCounts = 0;

        if (uploadFiles != null && uploadFiles.size() > 0) {
            for (MultipartFile aFile : uploadFiles){
				String fileNM =  aFile.getOriginalFilename();
			
				String finalFileName = realPath + File.separator + now + "___" + fileNM;
				savedFileNames = savedFileNames + now + "___" + fileNM + "$$$";
                 
                if (!aFile.getOriginalFilename().equals("")) {
                    aFile.transferTo(new File(finalFileName));
					uploadFileCounts = uploadFileCounts + 1;
                }
            }
        }
 
        Map <String, String> map = new HashMap<String, String>();
		if (uploadFileCounts > 0) {
			map.put("upload", "Y");
			map.put("fileUpload", savedFileNames);
		} else {
			map.put("upload", "N");
			map.put("fileUpload", "Error");
		}

		return map;
    }
    
	@RequestMapping(value = "/boardFileDownload.do")
	public void boardFileDownload(String p, String f, HttpServletRequest request, HttpServletResponse response) throws IOException{
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

	
}
