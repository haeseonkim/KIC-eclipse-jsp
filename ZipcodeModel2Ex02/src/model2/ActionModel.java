package model2;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ActionModel {
	public void execute(HttpServletRequest request, HttpServletResponse response);
}
