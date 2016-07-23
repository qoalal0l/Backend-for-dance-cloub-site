package ua.dp.renessans.controller.image;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.dp.renessans.dao.DbImageManager;
import ua.dp.renessans.model.manager.ImageManager;
import ua.dp.renessans.model.manager.ManagerException;

@WebServlet("/admin/UpdateImageDescription")
public class UpdateImageDescription extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		ImageManager imageManager = new DbImageManager();

		String description = request.getParameter("value");
		int id = Integer.parseInt(request.getParameter("id").replace("image_description", ""));

		try {
			imageManager.updateImageDescription(id, description);
		} catch (ManagerException e) {
			throw new ServletException(e);
		}

		response.getWriter().write(description);
	}

}
