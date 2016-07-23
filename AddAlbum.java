package ua.dp.renessans.controller.album;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;

import ua.dp.renessans.dao.DbAlbumManager;
import ua.dp.renessans.model.entity.Admin;
import ua.dp.renessans.model.entity.Album;
import ua.dp.renessans.model.manager.AlbumManager;
import ua.dp.renessans.model.manager.ManagerException;

import static ua.dp.renessans.util.FileLocations.*;
import ua.dp.renessans.util.Timestamper;

@WebServlet("/admin/AddAlbum")
public class AddAlbum extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();

		AlbumManager albumManager = new DbAlbumManager();

		Admin admin = (Admin) session.getAttribute("admin");
		String albumName = request.getParameter("album_name");
		String albumDescription = request.getParameter("album_description");
		int adminId = admin.getId();

		File albumFolder = new File(IMAGE_STORAGE + ALBUMS + albumName);
		if (albumFolder.exists()) {
			response.sendRedirect(request.getContextPath() + "/gallery.jsp?wrong_name=" + albumName);
			return;
		}
		File previewFolder = new File(IMAGE_STORAGE + THUMBINALS + albumName);

		try {
			albumManager.addAlbum(new Album(-1, albumName, albumDescription, null, Timestamper.now(), adminId));

			FileUtils.forceMkdir(albumFolder);
			FileUtils.forceMkdir(previewFolder);
		} catch (ManagerException e) {
			throw new ServletException(e);
		}

		response.sendRedirect(request.getContextPath() + "/gallery.jsp");
	}

}
