package filter;


import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;



/**
 * Servlet Filter implementation class EncodingFilter
 */
@WebFilter("*.jsp")
public class EncodingFilter implements Filter {

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here

		if( request.getCharacterEncoding() == null ) {
			request.setCharacterEncoding( "utf-8" );
		}
		
		String data = request.getParameter( "data" );
		if( !data.equals("") ) {
			// pass the request along the filter chain
			chain.doFilter(request, response);
		} else {
			RequestDispatcher dipaDispatcher = request.getRequestDispatcher("error.jsp");
			dipaDispatcher.forward(request, response);
		}
	}
}
