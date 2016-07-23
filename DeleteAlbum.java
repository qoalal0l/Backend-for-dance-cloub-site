package ua.dp.renessans.controller.album;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;

import ua.dp.renessans.dao.DbAlbumManager;
import ua.dp.renessans.model.entity.Album;
import ua.dp.renessans.model.manager.AlbumManager;
import ua.dp.renessans.model.manager.ManagerException;

import static ua.dp.renessans.util.FileLocations.*;

@WebServlet("/admin/DeleteAlbum")
public class DeleteAlbum extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		AlbumManager albumManager = new DbAlbumManager();

		int id = Integer.parseInt(request.getParameter("id"));
		String albumName = null;
		String coverName = null;

		try {
			Album album = albumManager.getAlbumById(id);
			albumName = album.getName();
			coverName = album.getCoverName();
			albumManager.deleteAlbum(id);

			File albumFolder = new File(IMAGE_STORAGE + ALBUMS + albumName);
			File previewFolder = new File(IMAGE_STORAGE + THUMBINALS + albumName);
			File albumCover = new File(IMAGE_STORAGE + COVERS + coverName);
			FileUtils.deleteDirectory(albumFolder);
			FileUtils.deleteDirectory(previewFolder);

			if (coverName != null) {
				FileUtils.forceDelete(albumCover);
			}
		} catch (ManagerException e) {
			throw new ServletException(e);
		}

		response.sendRedirect(request.getContextPath() + "/gallery.jsp");
	}

}
