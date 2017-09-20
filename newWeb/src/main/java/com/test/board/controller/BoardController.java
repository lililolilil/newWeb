package com.test.board.controller;

import java.io.File;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.test.board.Article;
import com.test.board.AttachFile;
import com.test.board.Board;
import com.test.board.Comment;
import com.test.board.exception.AuthenticationException;
import com.test.board.service.BoardService;
import com.test.commons.NumbersForPaging;
import com.test.commons.Paginator;
import com.test.commons.WebContants;
import com.test.user.User;
import com.test.user.controller.UsersController;


@Controller
@RequestMapping("/board")
public class BoardController extends Paginator{
	Logger logger = LoggerFactory.getLogger(BoardController.class);  

	@Autowired
	private BoardService boardService;  

	// home에서 List를 가져오는거 
	@RequestMapping(value="/getRecentList", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getRecentList(HttpSession session) throws Exception {
		Map<String, Object> resultMap = new HashMap<>();
		//로그인 체크

		try{
			List<Article> recentFreeArticle = boardService.getRecentArticle("free");
			resultMap.put("articleList", recentFreeArticle); 		
		}catch (Exception e) {
			resultMap.put("err", "시스템 문제로 인해 덧글을 가져올 수 없습니다. 관리자에게 문의하세요");
			logger.error("error",e);
		}
		return resultMap;
	}

	@RequestMapping(value="/list", method=RequestMethod.GET)
	public String list(String boardCd, 
			Integer curPage, 
			String searchWord,
			HttpServletRequest req,
			HttpSession session,
			Model model) throws Exception {
		Board board = boardService.getBoard(boardCd); 

		model.addAttribute("title", board.getBoardNm_ko()); 
		model.addAttribute("menu", boardCd); 


		User user = (User) session.getAttribute(WebContants.USER_KEY);

		if (user == null) {
			//로그인 후 되돌아갈 URL을 구한다.
			String url = req.getServletPath();
			String query = req.getQueryString();
			if (query != null) url += "?" + query;
			//로그인 페이지로 리다이렉트
			url = URLEncoder.encode(url, "UTF-8");
			return "redirect:/users/login?url=" + url;
		}



		int numPerPage = 10;
		int pagePerBlock = 10;

		int totalRecord = boardService.getTotalRecord(boardCd, searchWord);
		System.out.println(totalRecord);

		NumbersForPaging numbers = this.getNumbersForPaging(totalRecord, curPage, numPerPage, pagePerBlock);
		//oracle
		Integer startRecord = (curPage - 1) * numPerPage + 1;
		Integer endRecord = curPage * numPerPage;

		System.out.println(startRecord + "/" + endRecord);

		HashMap<String, String> map = new HashMap<String, String>();
		if(searchWord== null)
			searchWord = ""; 

		System.out.println("getArticlelist param " +  boardCd +"/"+ searchWord + "/" + startRecord + "/ "+ endRecord);

		List<Article> list = boardService.getArticleList(boardCd, searchWord, startRecord, endRecord);

		String boardNm = boardService.getBoard(boardCd).getBoardNm();
		String boardNm_ko = boardService.getBoard(boardCd).getBoardNm_ko();
		Integer listItemNo = numbers.getListItemNo();
		Integer prevPage = numbers.getPrevBlock();
		Integer nextPage = numbers.getNextBlock();
		Integer firstPage = numbers.getFirstPage();
		Integer lastPage = numbers.getLastPage();

		model.addAttribute("list", list);
		model.addAttribute("boardCd", boardCd); 
		model.addAttribute("boardNm", boardNm);
		model.addAttribute("boardNm_ko", boardNm_ko);
		model.addAttribute("listItemNo", listItemNo);
		model.addAttribute("prevPage", prevPage);
		model.addAttribute("nextPage", nextPage);
		model.addAttribute("firstPage", firstPage);
		model.addAttribute("lastPage", lastPage);

		return "board/list";

	}
	@RequestMapping(value="/list.json", method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> listtoJson(String boardCd, 
			Integer curPage, 
			String searchWord,
			HttpServletRequest req,
			HttpSession session,
			Model model) throws Exception {
		Map<String,Object> resultMap = new HashMap<>();  


		Board board = boardService.getBoard(boardCd); 
		User user = (User) session.getAttribute(WebContants.USER_KEY);

		if (user == null) {
			//로그인 후 되돌아갈 URL을 구한다.
			String url = req.getServletPath();
			String query = req.getQueryString();
			if (query != null) url += "?" + query;
			//로그인 페이지로 리다이렉트
			url = URLEncoder.encode(url, "UTF-8");
			resultMap.put("err", " 로그인 하지 않은 사용자의 접근!!!" ); 
			throw new AuthenticationException(" 로그인 하셔야 게시판에 접근 할 수 있습니다."); 
		}

		int numPerPage = 10;
		int pagePerBlock = 10;
		
		int totalRecord = boardService.getTotalRecord(boardCd, searchWord);
		System.out.println(totalRecord);

		NumbersForPaging numbers = this.getNumbersForPaging(totalRecord, curPage, numPerPage, pagePerBlock);
		//oracle
		Integer startRecord = (curPage - 1) * numPerPage + 1;
		Integer endRecord = curPage * numPerPage;

		System.out.println(startRecord + "/" + endRecord);

		
		HashMap<String, String> map = new HashMap<String, String>();
		if(searchWord== null)
			searchWord = ""; 

		System.out.println("getArticlelist param " +  boardCd +"/"+ searchWord + "/" + startRecord + "/ "+ endRecord);

		List<Article> list = boardService.getArticleList(boardCd, searchWord, startRecord, endRecord);

		String boardNm = boardService.getBoard(boardCd).getBoardNm();
		String boardNm_ko = boardService.getBoard(boardCd).getBoardNm_ko();
		Integer listItemNo = numbers.getListItemNo();
		Integer prevPage = numbers.getPrevBlock();
		Integer nextPage = numbers.getNextBlock();
		Integer firstPage = numbers.getFirstPage();
		Integer lastPage = numbers.getLastPage();


		resultMap.put("list", list);
		resultMap.put("boardCd", boardCd); 
		resultMap.put("boardNm", boardNm);
		resultMap.put("boardNm_ko", boardNm_ko);
		resultMap.put("listItemNo", listItemNo);
		resultMap.put("prevPage", prevPage);
		resultMap.put("nextPage", nextPage);
		resultMap.put("firstPage", firstPage);
		resultMap.put("lastPage", lastPage);

		return resultMap;

	}

	@RequestMapping(value="/write", method=RequestMethod.GET)
	public String writeForm(String boardCd,
			HttpServletRequest req,
			HttpSession session,
			Model model) throws Exception {
		model.addAttribute("title", "글쓰기");
		model.addAttribute("menu", boardCd);
		//로그인 체크
		User user = (User) session.getAttribute(WebContants.USER_KEY);

		if (user == null) {
			//로그인 후 되돌아갈 URL을 구한다.
			String url = req.getServletPath();
			String query = req.getQueryString();
			if (query != null) url += "?" + query;
			//로그인 페이지로 리다이렉트
			url = URLEncoder.encode(url, "UTF-8");
			return "redirect:/users/login?url=" + url;
		}


		//게시판 이름
		String boardNm = boardService.getBoard(boardCd).getBoardNm_ko();
		logger.info("boardcd:"+boardCd + "/ boardNm_ko:" + boardNm); 
		model.addAttribute("boardNm", boardNm);
		model.addAttribute("boardCd", boardCd); 
		return "board/write";
	}

	@RequestMapping(value="write", method=RequestMethod.POST)
	@ResponseBody 
	public Map<String, String> write(MultipartHttpServletRequest mpRequest,
			HttpSession session) throws Exception {

		String boardCd = mpRequest.getParameter("boardCd");
		String title = mpRequest.getParameter("title");
		String content = mpRequest.getParameter("content");
		User user = (User) session.getAttribute(WebContants.USER_KEY);

		HashMap<String,String> resultMap = new HashMap<>(); 
		Article article = new Article();

		//로그인 체크
		if (user == null) {
			resultMap.put("err", "notLogin"); 
			//throw new AuthenticationException("로그인 하지 않은 사용자의 글쓰기 시도"); 
		}else{
			article.setBoardCd(boardCd);
			article.setTitle(title);
			article.setContent(content);
			article.setEmail( user.getEmail());
			int returnInt = boardService.addArticle(article);
			logger.info("addArticle return: " + returnInt);

			try{
				//파일 업로드
				Iterator<String> it = mpRequest.getFileNames();
				List<MultipartFile> fileList = new ArrayList<MultipartFile>();
				while (it.hasNext()) {
					MultipartFile multiFile = mpRequest.getFile((String) it.next());
					if (multiFile.getSize() > 0) {
						String filename = multiFile.getOriginalFilename();
						multiFile.transferTo(new File(WebContants.UPLOAD_PATH + filename));
						fileList.add(multiFile);
					}
				}
				//파일데이터 삽입
				int size = fileList.size();
				for (int i = 0; i < size; i++) {
					MultipartFile mpFile = fileList.get(i);
					AttachFile attachFile = new AttachFile();
					String filename = mpFile.getOriginalFilename();
					attachFile.setFilename(filename);
					attachFile.setFiletype(mpFile.getContentType());
					attachFile.setFilesize(mpFile.getSize());
					attachFile.setArticleNo(article.getArticleNo());
					attachFile.setEmail(user.getEmail());
					boardService.addAttachFile(attachFile);
				}
			}catch (Exception e) {
				resultMap.put("err", "첨부 파일 저장을  실패 했습니다."); 
			}
		}

		resultMap.put("page", "1"); 
		resultMap.put("boardCd", article.getBoardCd()); 

		return resultMap; 
	}

	@RequestMapping(value="/view", method=RequestMethod.GET)
	public String view(Integer articleNo, 
			String boardCd, 
			Integer curPage,
			String searchWord,
			HttpServletRequest req,
			HttpSession session,
			Model model) throws Exception {
		model.addAttribute("menu", boardCd); 
		Integer page = curPage; 
		User user = (User) session.getAttribute(WebContants.USER_KEY);

		if (user == null) {
			//로그인 후 되돌아갈 URL을 구한다.
			String url = req.getServletPath();
			String query = req.getQueryString();
			if (query != null) url += "?" + query;
			//로그인 페이지로 리다이렉트
			url = URLEncoder.encode(url, "UTF-8");
			return "redirect:/users/login?url=" + url;
		}

		/*	 상세보기를 할 때마다 조회 수를 1 증가
	   	    	하단에 목록에서 조회 수를 제대로 보기 위해서는
	        	목록 레코드를 페치하기 전에 조회 수를 먼저 증가시켜야 한다.
		 */
		//TODO : 사용자 IP와 시간을 고려해서 조회 수를 증가하도록
		boardService.increaseHit(articleNo);
		logger.info("조회수 up: "+ articleNo); 

		Article article = boardService.getArticle(articleNo);//상세보기에서 볼 게시글
		List<AttachFile> attachFileList = boardService.getAttachFileList(articleNo);
		Article nextArticle = boardService.getNextArticle(articleNo, boardCd, searchWord);
		Article prevArticle = boardService.getPrevArticle(articleNo, boardCd, searchWord);
		//List<Comment> commentList = boardService.getCommentList(articleNo);

		//상세보기에서 볼 게시글 관련 정보
		String title = article.getTitle();//제목
		String content = article.getContent();//내용
		content = content.replaceAll(WebContants.LINE_SEPARATOR, "<br/>");
		int hit = article.getHit();//조회 수
		//String name = article.getName();//작성자 이름
		String email = article.getEmail();//작성자 ID
		Date regdate = article.getRegdate();//작성일

		model.addAttribute("title", title);
		model.addAttribute("content", content);
		model.addAttribute("hit", hit);
		//model.addAttribute("name", name);
		model.addAttribute("email", email);
		model.addAttribute("regdate", regdate);
		model.addAttribute("attachFileList", attachFileList);
		model.addAttribute("nextArticle", nextArticle);
		model.addAttribute("prevArticle", prevArticle);
		//model.addAttribute("commentList", commentList);

		//목록 관련
		int numPerPage = WebContants.NUM_PER_PAGE;//페이지당 레코드 수
		int pagePerBlock = WebContants.PAGE_PER_BLOCK;//블록당 페이지 링크 수

		int totalRecord = boardService.getTotalRecord(boardCd, searchWord);

		NumbersForPaging numbers = this.getNumbersForPaging(totalRecord, page, numPerPage, pagePerBlock);

		//oracle
		Integer startRecord = (page - 1) * numPerPage + 1;
		Integer endRecord = page * numPerPage;

		List<Article> list = boardService.getArticleList(boardCd, searchWord, startRecord, endRecord);
		String boardName = boardService.getBoard(boardCd).getBoardNm();

		int listItemNo = numbers.getListItemNo();
		int prevPage = numbers.getPrevBlock();
		int nextPage = numbers.getNextBlock();
		int firstPage = numbers.getFirstPage();
		int lastPage = numbers.getLastPage();

		model.addAttribute("boardCd", boardCd); 
		model.addAttribute("boardNm", boardService.getBoard(boardCd).getBoardNm_ko()); 
		model.addAttribute("list", list);
		model.addAttribute("listItemNo", listItemNo);
		model.addAttribute("prevPage", prevPage);
		model.addAttribute("firstPage", firstPage);
		model.addAttribute("lastPage", lastPage);
		model.addAttribute("nextPage", nextPage);
		model.addAttribute("boardName", boardName);

		return "board/view";
	}

	@RequestMapping(value="/addComment", method=RequestMethod.POST, produces="application/json; charset=UTF-8")
	@ResponseBody
	public Map<String, String> addComment(@RequestBody String commentInfo,
			HttpSession session) throws Exception {
		Map<String, String> resultMap = new HashMap<>();
		//로그인 체크
		try{
			JSONObject jsonObj = new JSONObject(commentInfo);  
			Integer articleNo = Integer.parseInt(jsonObj.getString("articleNo"));  
			String boardCd = jsonObj.getString("boardCd");  
			String name = jsonObj.getString("name");
			String passwd = jsonObj.getString("passwd");
			String memo = jsonObj.getString("comment");
			User user = (User) session.getAttribute(WebContants.USER_KEY);

			if(!"free".equals(boardCd)){
				if (user == null) {
					resultMap.put("err", "회원이 아닌 사람은 해당 게시판에 댓글을 추가 할 수 없습니다."); 
					return resultMap; 
				}
			}else{
				Comment comment = new Comment();
				comment.setArticleNo(articleNo);
				comment.setName(name);
				comment.setPasswd(passwd);
				comment.setMemo(memo);
				boardService.addComment(comment);

			}

		}catch (JSONException e) {
			logger.error("json 파라미터 오류 ", e);
			resultMap.put("err", "시스템 문제로 인해 정보 변경이 불가합니다.<br>관리자에게 문의하세요."); 
		}catch (Exception e){
			e.printStackTrace();
			resultMap.put("err", "덧글을 추가 할 수 없습니다.");
		}
		return resultMap;
	}

	@RequestMapping(value="/getCommentList", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getCommentList(Integer articleNo,
			HttpSession session) throws Exception {
		Map<String, Object> resultMap = new HashMap<>();
		//로그인 체크

		try{
			List<Comment> commentList = boardService.getCommentList(articleNo);
			resultMap.put("commentList", commentList); 		
		}catch (Exception e) {
			resultMap.put("err", "시스템 문제로 인해 덧글을 가져올 수 없습니다. 관리자에게 문의하세요");
			logger.error("error",e);
		}
		return resultMap;
	}

	@RequestMapping(value="/download", method=RequestMethod.POST)
	public ModelAndView download(String filename, HttpSession session, Model model) {
		//로그인 체크
		User user = (User) session.getAttribute(WebContants.USER_KEY);
		if (user == null) {
			throw new AuthenticationException(WebContants.NOT_LOGIN);
		}

		logger.info("fileName : " + filename);
		AttachFile attachedfile = boardService.getAttachFile(filename); 

		Map<String, Object> fileInfo = new HashMap<>(); 
		fileInfo.put("fileUploadPath", WebContants.UPLOAD_PATH);
		fileInfo.put("fileLogicName", attachedfile.getFilename());
		fileInfo.put("filePhysicName", attachedfile.getFilename());

		Iterator<String> itr = fileInfo.keySet().iterator(); 
		while(itr.hasNext()){
			String key = itr.next(); 
			logger.info(key+ ":" + fileInfo.get(key).toString());
		}

		return new ModelAndView("DownloadView", "downloadFile", fileInfo); 
	}
}
/*public class BoardController extends Paginator{
	@Autowired
	private BoardService boardService;  


    @RequestMapping(value="/download", method=RequestMethod.POST)
    public String download(String filename, HttpSession session, Model model) {
        //로그인 체크
        User user = (User) session.getAttribute(WebContants.USER_KEY);
        if (user == null) {
            throw new AuthenticationException(WebContants.NOT_LOGIN);
        }

        model.addAttribute("filename", filename);
        return "inc/download";

    }

    @RequestMapping(value="/updateComment", method=RequestMethod.POST)
    public String updateComment(Integer commentNo, 
            Integer articleNo, 
            String boardCd, 
            Integer page, 
            String searchWord, 
            String memo,
            HttpSession session) throws Exception {

        User user = (User) session.getAttribute(WebContants.USER_KEY);

        Comment comment = boardService.getComment(commentNo);

        //로그인 사용자가 댓글 소유자인지  검사
        if (user == null || !user.getEmail().equals(comment.getEmail())) {
            throw new AuthenticationException(WebContants.AUTHENTICATION_FAILED);
        }

        //생성된 Comment 객체를 재사용한다.
        comment.setMemo(memo);
        boardService.modifyComment(comment);

        searchWord = URLEncoder.encode(searchWord, "UTF-8");

        return "redirect:/bbs/view?articleNo=" + articleNo + 
                "&boardCd=" + boardCd + 
                "&page=" + page + 
                "&searchWord=" + searchWord;

    }

    @RequestMapping(value="/deleteComment", method=RequestMethod.POST)
    public String deleteComment(Integer commentNo, 
            Integer articleNo, 
            String boardCd, 
            Integer page, 
            String searchWord,
            HttpSession session) throws Exception {

        User user = (User) session.getAttribute(WebContants.USER_KEY);

        Comment comment = boardService.getComment(commentNo);

        //로그인 사용자가 댓글의 소유자인지 검사
        if (user == null || !user.getEmail().equals(comment.getEmail())) {
            throw new AuthenticationException(WebContants.AUTHENTICATION_FAILED);
        }

        boardService.removeComment(commentNo);

        searchWord = URLEncoder.encode(searchWord,"UTF-8");

        return "redirect:/bbs/view?articleNo=" + articleNo + 
                "&boardCd=" + boardCd + 
                "&page=" + page + 
                "&searchWord=" + searchWord;

    }

    @RequestMapping(value="/modify", method=RequestMethod.GET)
    public String modifyForm(Integer articleNo, 
            String boardCd,
            HttpSession session,
            Model model) {

        User user = (User) session.getAttribute(WebContants.USER_KEY);

        Article article = boardService.getArticle(articleNo);

        //로그인 사용자가 글 작성자인지 검사
        if (user == null || !user.getEmail().equals(article.getEmail())) {
            throw new AuthenticationException(WebContants.AUTHENTICATION_FAILED);
        }

        //수정페이지에서의 보일 게시글 정보
        String title = article.getTitle();
        String content = article.getContent();
        String boardName = boardService.getBoard(boardCd).getBoardNm();

        model.addAttribute("title", title);
        model.addAttribute("content", content);
        model.addAttribute("boardName", boardName);

        return "bbs/modify";
    }

    @RequestMapping(value="/modify", method=RequestMethod.POST)
    public String modify(MultipartHttpServletRequest mpRequest,
            HttpSession session) throws Exception {

        User user = (User) session.getAttribute(WebContants.USER_KEY);

        int articleNo = Integer.parseInt(mpRequest.getParameter("articleNo"));
        Article article = boardService.getArticle(articleNo);

        //로그인 사용자가 글 작성자인지 검사
        if (!article.getEmail().equals(user.getEmail())) {
            throw new AuthenticationException(WebContants.AUTHENTICATION_FAILED);
        }

        String boardCd = mpRequest.getParameter("boardCd");
        int page = Integer.parseInt(mpRequest.getParameter("page"));
        String searchWord = mpRequest.getParameter("searchWord");

        String title = mpRequest.getParameter("title");
        String content = mpRequest.getParameter("content");

        //게시글 수정
        article.setTitle(title);
        article.setContent(content);
        article.setBoardCd(boardCd);
        boardService.modifyArticle(article);

        //파일 업로드
        Iterator<String> it = mpRequest.getFileNames();
        List<MultipartFile> fileList = new ArrayList<MultipartFile>();
        while (it.hasNext()) {
            MultipartFile multiFile = mpRequest.getFile((String) it.next());
            if (multiFile.getSize() > 0) {
                String filename = multiFile.getOriginalFilename();
                multiFile.transferTo(new File(WebContants.UPLOAD_PATH + filename));
                fileList.add(multiFile);
            }
        }

        //파일데이터 삽입
        int size = fileList.size();
        for (int i = 0; i < size; i++) {
            MultipartFile mpFile = fileList.get(i);
            AttachFile attachFile = new AttachFile();
            String filename = mpFile.getOriginalFilename();
            attachFile.setFilename(filename);
            attachFile.setFiletype(mpFile.getContentType());
            attachFile.setFilesize(mpFile.getSize());
            attachFile.setArticleNo(articleNo);
            attachFile.setEmail(user.getEmail());
            boardService.addAttachFile(attachFile);
        }

        searchWord = URLEncoder.encode(searchWord,"UTF-8");
        return "redirect:/bbs/view?articleNo=" + articleNo 
                + "&boardCd=" + boardCd 
                + "&page=" + page 
                + "&searchWord=" + searchWord;

    }

    @RequestMapping(value="/deleteAttachFile", method=RequestMethod.POST)
    public String deleteAttachFile(Integer attachFileNo, 
            Integer articleNo, 
            String boardCd, 
            Integer page, 
            String searchWord,
            HttpSession session) throws Exception {

        User user = (User) session.getAttribute(WebContants.USER_KEY);
        AttachFile attachFile = boardService.getAttachFile(attachFileNo);

        //로그인 사용자가 첨부 파일 소유자인지 검사
        if (user == null || !user.getEmail().equals(attachFile.getEmail())) {
            throw new AuthenticationException(WebContants.AUTHENTICATION_FAILED);
        }

        boardService.removeAttachFile(attachFileNo);

        searchWord = URLEncoder.encode(searchWord,"UTF-8");

        return "redirect:/bbs/view?articleNo=" + articleNo + 
                "&boardCd=" + boardCd + 
                "&page=" + page + 
                "&searchWord=" + searchWord;

    }

    @RequestMapping(value="/del", method=RequestMethod.POST)
    public String del(Integer articleNo, 
            String boardCd, 
            Integer page, 
            String searchWord,
            HttpSession session) throws Exception {

        User user = (User) session.getAttribute(WebContants.USER_KEY);
        Article article = boardService.getArticle(articleNo);

        //로그인 사용자가 글 작성자인지 검사
        if (user == null || !user.getEmail().equals(article.getEmail())) {
            throw new AuthenticationException(WebContants.AUTHENTICATION_FAILED);
        }

        boardService.removeArticle(articleNo);

        searchWord = URLEncoder.encode(searchWord, "UTF-8");

        return "redirect:/bbs/list?boardCd=" + boardCd + 
                "&page=" + page + 
                "&searchWord=" + searchWord;

    }

    @RequestMapping(value="/list", method=RequestMethod.GET)
    public String list(String boardCd, 
            Integer curPage, 
            String searchWord,
            HttpServletRequest req,
            HttpSession session,
            Model model) throws Exception {

        //로그인 체크
        User user = (User) session.getAttribute(WebContants.USER_KEY);
        if (user == null) {
            //로그인 후 되돌아갈 URL을 구한다.
            String url = req.getServletPath();
            String query = req.getQueryString();
            if (query != null) url += "?" + query;
            //로그인 페이지로 리다이렉트
            url = URLEncoder.encode(url, "UTF-8");
            return "redirect:/users/login?url=" + url;
        }

        int numPerPage = 10;
        int pagePerBlock = 10;

        int totalRecord = boardService.getTotalRecord(boardCd, searchWord);
        System.out.println(totalRecord);

        NumbersForPaging numbers = this.getNumbersForPaging(totalRecord, curPage, numPerPage, pagePerBlock);
        //oracle
        Integer startRecord = (curPage - 1) * numPerPage + 1;
        Integer endRecord = curPage * numPerPage;

        System.out.println(startRecord + "/" + endRecord);

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("boardCd", boardCd);
        if(searchWord!= null)
        	map.put("searchWord", searchWord);
        else 
        	map.put("searchWord","");         	
        map.put("start", startRecord.toString());
        map.put("end", endRecord.toString());
        System.out.println("getArticlelist param " +  boardCd +"/"+ searchWord + "/" + startRecord + "/ "+ endRecord);
        List<Article> list = boardService.getArticleList(boardCd, searchWord, startRecord, endRecord);
        String boardName = boardService.getBoard(boardCd).getBoardNm();

        Integer listItemNo = numbers.getListItemNo();
        Integer prevPage = numbers.getPrevBlock();
        Integer nextPage = numbers.getNextBlock();
        Integer firstPage = numbers.getFirstPage();
        Integer lastPage = numbers.getLastPage();

        model.addAttribute("list", list);
        model.addAttribute("boardName", boardName);
        model.addAttribute("listItemNo", listItemNo);
        model.addAttribute("prevPage", prevPage);
        model.addAttribute("nextPage", nextPage);
        model.addAttribute("firstPage", firstPage);
        model.addAttribute("lastPage", lastPage);

        return "bbs/list";

    }

}*/