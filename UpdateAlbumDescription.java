package ua.dp.renessans.controller.album;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.dp.renessans.dao.DbAlbumManager;
import ua.dp.renessans.model.manager.AlbumManager;
import ua.dp.renessans.model.manager.ManagerException;

@WebServlet("/admin/UpdateAlbumDescription")
public class UpdateAlbumDescription extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		AlbumManager albumManager = new DbAlbumManager();

		String description = request.getParameter("value");
		int id = Integer.parseInt(request.getParameter("id").replace("album_description", ""));

		try {
			albumManager.updateAlbumDescription(id, description);
		} catch (ManagerException e) {
			throw new ServletException(e);
		}

		response.getWriter().write(description);
	}

}
