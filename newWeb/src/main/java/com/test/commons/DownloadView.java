package com.test.commons;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.FileCopyUtils;
import org.springframework.web.servlet.view.AbstractView;

public class DownloadView extends AbstractView{
	public DownloadView(){
		// 객체 생성 시 content Type  변경 
		setContentType("application/download; charset=utf-8"); 
	}
	

	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

			Map<String,Object> fileInfo = (Map<String,Object>) model.get("downloadFile");
			String fileUploadPath = (String) fileInfo.get("fileUploadPath"); 
			String fileLogicName = (String)fileInfo.get("fileLogicName"); // 화면에 표시 될 파일의 이름. 
			String filePhysicName = (String)fileInfo.get("filePhysicName");
			OutputStream out = null;
	
			String fileType = fileLogicName.substring(fileLogicName.indexOf(".")+1, fileLogicName.length()); 
			if (fileType.trim().equalsIgnoreCase("txt")) {
	            response.setContentType("text/plain");
	        } else {
	            response.setContentType("application/octet-stream");
	        }
			try{
			File file = new File(fileUploadPath, filePhysicName); 
			response.setContentType(getContentType());
			response.setContentLength((int) file.length());
			String userAgent = request.getHeader("User-Agent");  
			boolean ie = userAgent.indexOf("MSIE") != -1; //-1이 아니면 true  
			String fileName = null; 
			
			if(ie){
				fileName = URLEncoder.encode(fileLogicName, "urf-8"); 
			}else{
				fileName = new String(fileLogicName.getBytes("utf-8"), "iso-8859-1"); 
			}
			response.setHeader("Content-Disposition", "attachment;filename=\""+fileName+"\";"); 
			response.setHeader("Content-Transfer-Encoding", "binary");
	        out = response.getOutputStream();
	        FileInputStream fis = null;
	        
	        try {
	            fis = new FileInputStream(file);
	            FileCopyUtils.copy(fis, out);
                out.flush();

	        } finally {
	        	if(fis != null){
	                  try{
	                	  fis.close();
	                  }catch (Exception e) {
	                	  logger.error("fileinputStream", e); 
	                  }
	        	}
	            
	        }
		}catch (Exception e) {
			throw new RuntimeException(e);
			
		}finally {
			try {
                out.close();
                response.flushBuffer();
            } catch (IOException e) {
                e.printStackTrace();
            }
			
		}
		
	}
	
}
