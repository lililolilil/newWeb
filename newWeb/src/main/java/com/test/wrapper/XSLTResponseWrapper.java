package com.test.wrapper;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class XSLTResponseWrapper extends HttpServletResponseWrapper{

	private ResponseBufferWriter buffer = null; 
	
	public XSLTResponseWrapper(HttpServletResponse response) {
		super(response);
		buffer = new ResponseBufferWriter();   
	}
	public PrintWriter getWriter(){
		return buffer; 
	}
	public void setContentType(String contentType) {
        // do nothing
		// do Filter에서 contentType 을 변경 할 수 없도록 하기 위해서 
     }
     
     public String getBufferedString() {
        return buffer.toString();
     }

}


class ResponseBufferWriter extends PrintWriter{
    
    public ResponseBufferWriter() {
       super(new StringWriter(4096) );
    }
    
    public String toString() {
       return ((StringWriter)super.out).toString();
    }
 }

