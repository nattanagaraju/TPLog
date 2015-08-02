package org.tlog;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * Servlet Filter implementation class TRFilter
 */
public class TRFilter implements Filter {
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// before filter
		TPath.setThreadVal(new HashMap<String, Object>());
		HttpServletRequest req = (HttpServletRequest)request;
		String requestId = req.getHeader("request-id");
		requestId = (requestId == null)? req.getParameter("request-id") : requestId;
		requestId = (requestId == null)? "requst-id-"+System.currentTimeMillis()+"-"+Math.random() : requestId;
		TPath.getThreadVal().put("request-id", requestId);
		TPath.getThreadVal().put("tcount", 0);
		TPath.getThreadVal().put("context-root", req.getContextPath());
		
		chain.doFilter(request, response);
		// after respose
		TPath.removeThreadVal();
	}

	@Override
	public void destroy() {
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

}
