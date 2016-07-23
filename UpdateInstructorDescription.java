package ua.dp.renessans.controller.instructor;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.dp.renessans.dao.DbInstructorManager;
import ua.dp.renessans.model.entity.Admin;
import ua.dp.renessans.model.entity.Instructor;
import ua.dp.renessans.model.manager.InstructorManager;
import ua.dp.renessans.model.manager.ManagerException;
import ua.dp.renessans.util.Timestamper;

@WebServlet("/admin/UpdateInstructorDescription")
public class UpdateInstructorDescription extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		InstructorManager instructorManager = new DbInstructorManager();

		String description = request.getParameter("value");
		Admin admin = (Admin) request.getSession().getAttribute("admin");
		int id = Integer.parseInt(request.getParameter("id").replace("instructor_description", ""));
		int adminId = admin.getId();

		try {
			instructorManager.updateInstructorDescription(id,
					new Instructor(-1, null, description, null, null, null, null, -1, Timestamper.now(), adminId));
		} catch (ManagerException e) {
			throw new ServletException(e);
		}

		response.getWriter().write(description);
	}

}
