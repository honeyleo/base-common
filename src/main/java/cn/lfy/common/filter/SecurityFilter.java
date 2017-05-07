package cn.lfy.common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

/**
 * XSS安全过滤器
 *
 * @author liaopeng
 * @date 2014-4-10 下午2:12:02
 */
@Service
public class SecurityFilter implements Filter {

	private static final String FILTER_APPLIED = SecurityFilter.class.getName() + ".FILTERED";

	@Override
	public void init( FilterConfig filterConfig ) throws ServletException {}

	@Override
	public void doFilter( ServletRequest request, ServletResponse response, FilterChain chain ) throws IOException, ServletException {
		if( !( request instanceof HttpServletRequest ) || !( response instanceof HttpServletResponse ) ) {
			throw new ServletException( "XSSFilter just supports HTTP requests" );
		}
		HttpServletRequest httpRequest = ( HttpServletRequest )request;
		// Apply Filter
		if( null != httpRequest.getAttribute( FILTER_APPLIED ) ) {
			chain.doFilter( request, response );
			return;
		}
		try {
			request.setAttribute( FILTER_APPLIED, Boolean.TRUE );
			SecurityRequestWrapper requestWrapper = new SecurityRequestWrapper( httpRequest );
			chain.doFilter( requestWrapper, response );
		} finally {
			httpRequest.removeAttribute( FILTER_APPLIED );
		}
	}

	@Override
	public void destroy() {}
}
