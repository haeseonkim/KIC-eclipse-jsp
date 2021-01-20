package filter;
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class FirstFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		System.out.println("init() 호출");
	}
	
	@Override
	public void destroy() {
		System.out.println("destroy() 호출");
	}
	
	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
			throws IOException, ServletException {
		// TODO Auto-generated method stub

		// 전처리 구간
		System.out.println("첫번째 전처리");
		//servlet/jsp 호출 부분
		
		// 사용자 요청에 의해 jsp를 요청하는 거다.
		// 이걸 안하면 filter에만 왔다 가는 거다.
		//arg2.doFilter(arg0, arg1);
		
		System.out.println("첫번째 후처리");
		// 후처리 구간
	}

}
