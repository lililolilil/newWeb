package com.test.filter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import com.test.wrapper.XSLTResponseWrapper;
/**
 * 1. 응답 래퍼를 생성한다. 
 * 2. 응답 래퍼를 체인의 다음 필터에 전달한다. 
 * 3. 래퍼로 부터 servlet/jsp 에서 출력한 데이터를 읽어와서 html로 변환한다 
 * 4. 변환된 결과를 실제 응답 스트림에 출력한다. 
 */
public class XSLTFilter implements Filter{

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		res.setContentType("text/html; charset=utf-8"); // 클라이언트가 결과를 html 문서로 인식하게 함. 
		PrintWriter writer = res.getWriter();  
		XSLTResponseWrapper responseWrapper = new XSLTResponseWrapper((HttpServletResponse)res); 
		chain.doFilter(req, responseWrapper);
		// responseWrapper에는 servlet/jsp 가 출력한 xml응 답 데이터가 저장 됨. >> getBufferedString 이용 
		
		//XSL/T 변환 
		try{
			TransformerFactory factory = TransformerFactory.newInstance();  
			Reader xslReader = new BufferedReader(new FileReader("C:/book.xsl"));
			StreamSource xslSource = new StreamSource(xslReader); 
			
			Transformer transformer = factory.newTransformer(xslSource); 
			String xmlDocument = responseWrapper.getBufferedString();  
			Reader xmlReader = new StringReader(xmlDocument); 
			StreamSource xmlSource = new StreamSource(xmlReader); 
			
			StringWriter buffer = new StringWriter(4096); 
			transformer.transform(xmlSource, new StreamResult(buffer));//JAXP 1.1에서 제공
			writer.print(buffer.toString()); 
		}catch(Exception ex) {
	           throw new ServletException(ex);
	    }
	        
	        writer.flush();
	        writer.close();
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}


	
}
