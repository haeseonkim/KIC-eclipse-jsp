package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model2.ActionModel;
import model2.ZipcodeAction;
import model2.ZipcodeOkAction;

/**
 * Servlet implementation class ControllerEx01
 */
@WebServlet("*.do")
public class ControllerEx01 extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doProcess(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doProcess(request, response);
	}
	
	protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		// 파라메터 방식
		// String action = request.getParameter("action");
		// url 방식
		String path = request.getRequestURI().replaceAll(request.getContextPath(), "");
		
		String url = "";
		
		ActionModel model = null;
		// 파라메터 방식
		//if(action == null || action.equals("") || action.equals("zipcode")) {
		// url 방식
		if(path.equals("/*.do") || path.equals("/zipcodeModel2.do")) {
		
			// 특별한 일이 없음
			model = new ZipcodeAction();
			model.execute(request, response);
			
			url = "/WEB-INF/views/zipcodeModel2.jsp";
		
		// 파라메터 방식	
		//}else if(action.equals("zipcode_ok")) {
		// url 방식
		}else if(path.equals("/zipcodeModel2_ok.do")) {
			// 데이터베이스 선택
			model = new ZipcodeOkAction();
			model.execute(request, response);
			
			url = "/WEB-INF/views/zipcodeModel2_ok.jsp";
		}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(url);
		dispatcher.forward(request, response);
	}

}
