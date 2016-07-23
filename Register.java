package ua.dp.renessans.controller.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mindrot.jbcrypt.BCrypt;

import ua.dp.renessans.dao.DbAdminManager;
import ua.dp.renessans.model.entity.Admin;
import ua.dp.renessans.model.manager.AdminManager;
import ua.dp.renessans.model.manager.ManagerException;
import ua.dp.renessans.util.Timestamper;

@WebServlet("/admin/Registrator")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		Admin admin = (Admin) request.getSession().getAttribute("admin");
		AdminManager manager = new DbAdminManager();

		String login = request.getParameter("login");
		String password = request.getParameter("password");

		try {
			manager.addAdmin(new Admin(-1, login, Timestamper.now(), admin.getId()),
					BCrypt.hashpw(password, BCrypt.gensalt()));
		} catch (ManagerException e) {
			throw new ServletException(e);
		}

		response.sendRedirect(request.getContextPath() + "/register.jsp");
	}

}
