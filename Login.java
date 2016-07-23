package ua.dp.renessans.controller.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.dp.renessans.dao.DbAdminManager;
import ua.dp.renessans.model.entity.Admin;
import ua.dp.renessans.model.manager.AdminManager;
import ua.dp.renessans.model.manager.ManagerException;

@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String login = request.getParameter("login");
		String password = request.getParameter("password");

		AdminManager adminManager = new DbAdminManager();

		boolean valid;
		try {
			valid = adminManager.validateCredentials(login, password);
		} catch (ManagerException e) {
			throw new ServletException(e);
		}

		if (!valid) {
			response.sendRedirect(request.getContextPath() + "/error.jsp");
			return;
		}

		Admin admin = null;

		try {
			admin = adminManager.getAdminByName(login);
		} catch (ManagerException e) {
			throw new ServletException(e);
		}

		request.getSession().setAttribute("admin", admin);
		response.sendRedirect(request.getContextPath() + "/news.jsp");
	}

}
