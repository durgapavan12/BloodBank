package com.bloodbankapp.security.methods;
/*package com.hsignz.security;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.ContentCachingRequestWrapper;

import com.hsignz.common.filter.EncryAndDecryRequestWrapper;

public class RequestLoggerInterceptor extends HandlerInterceptorAdapter {

	private static final Logger log = Logger.getLogger(RequestLoggerInterceptor.class);

//	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		ContentCachingRequestWrapper cacheRequestWrapper = new ContentCachingRequestWrapper(request);;
//		if (!(request instanceof ContentCachingRequestWrapper)) {
//			cacheRequestWrapper = new ContentCachingRequestWrapper(request);
//		}
		log.info("[preHandle][" + request + "]" + "[" + request.getMethod() + "]" + request.getRequestURI()
				+ getParameters(request));
//		EncryAndDecryRequestWrapper encrRequestWrapper = new EncryAndDecryRequestWrapper(
//				(HttpServletRequest) cacheRequestWrapper.getRequest());
//		String body = IOUtils.toString(cacheRequestWrapper.getReader());
//		 String body = IOUtils.toString(request.getReader());
//		log.info("Body: " + body);
		return true;
	}

	private String getParameters(HttpServletRequest request) {
		StringBuffer posted = new StringBuffer();
		Enumeration<?> e = request.getParameterNames();
		if (e != null) {
			posted.append("?");
		}
		while (e.hasMoreElements()) {
			if (posted.length() > 1) {
				posted.append("&");
			}
			String curr = (String) e.nextElement();
			posted.append(curr + "=");
			if (curr.contains("password") || curr.contains("pass") || curr.contains("pwd")) {
				posted.append("*****");
			} else {
				posted.append(request.getParameter(curr));
			}
		}
		String ip = request.getHeader("X-FORWARDED-FOR");
		String ipAddr = (ip == null) ? getRemoteAddr(request) : ip;
		if (ipAddr != null && !ipAddr.equals("")) {
			posted.append("&_psip=" + ipAddr);
		}
		return posted.toString();
	}

	private String getRemoteAddr(HttpServletRequest request) {
		String ipFromHeader = request.getHeader("X-FORWARDED-FOR");
		if (ipFromHeader != null && ipFromHeader.length() > 0) {
			log.debug("ip from proxy - X-FORWARDED-FOR : " + ipFromHeader);
			return ipFromHeader;
		}
		return request.getRemoteAddr();
	}
}
*/