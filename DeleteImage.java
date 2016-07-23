package ua.dp.renessans.controller.image;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;

import ua.dp.renessans.dao.DbAlbumManager;
import ua.dp.renessans.dao.DbImageManager;
import ua.dp.renessans.model.entity.Album;
import ua.dp.renessans.model.entity.Image;
import ua.dp.renessans.model.manager.AlbumManager;
import ua.dp.renessans.model.manager.ImageManager;
import ua.dp.renessans.model.manager.ManagerException;

import static ua.dp.renessans.util.FileLocations.*;

@WebServlet("/admin/DeleteImage")
public class DeleteImage extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		ImageManager imageManager = new DbImageManager();
		AlbumManager albumManager = new DbAlbumManager();

		int id = Integer.parseInt(request.getParameter("id"));

		Image image = null;
		Album album = null;

		try {
			image = imageManager.getImageById(id);
			album = albumManager.getAlbumById(image.getAlbumId());

			String imagePath = IMAGE_STORAGE + ALBUMS + album.getName() + "/" + image.getName();
			String previewPath = IMAGE_STORAGE + THUMBINALS + album.getName() + "/" + image.getName();

			imageManager.deleteImage(id);

			File imageFile = new File(imagePath);
			File previewFile = new File(previewPath);
			FileUtils.forceDelete(imageFile);
			FileUtils.forceDelete(previewFile);
		} catch (ManagerException e) {
			throw new ServletException(e);
		}

		response.sendRedirect(request.getContextPath() + "/album.jsp?id=" + image.getAlbumId());
	}

}
