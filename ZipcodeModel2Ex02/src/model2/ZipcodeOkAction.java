package model2;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model1.ZipcodeDAO;
import model1.ZipcodeTO;

public class ZipcodeOkAction implements ActionModel {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		
		try {
			request.setCharacterEncoding( "utf-8" );
			
			String strDong = request.getParameter("dong");

			ZipcodeDAO dao = new ZipcodeDAO();
			ArrayList<ZipcodeTO> lists = dao.searchLists( strDong );
			
			//System.out.println(lists.size());
			request.setAttribute("lists",lists);
		
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
	}

}
