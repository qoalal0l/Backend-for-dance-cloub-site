package ua.dp.renessans.controller.image;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;

import net.coobird.thumbnailator.Thumbnails;
import ua.dp.renessans.dao.DbAlbumManager;
import ua.dp.renessans.dao.DbImageManager;
import ua.dp.renessans.model.entity.Admin;
import ua.dp.renessans.model.entity.Album;
import ua.dp.renessans.model.entity.Image;
import ua.dp.renessans.model.manager.AlbumManager;
import ua.dp.renessans.model.manager.ImageManager;
import ua.dp.renessans.util.FileLocations;
import ua.dp.renessans.util.Timestamper;
import static ua.dp.renessans.util.FileLocations.*;

@WebServlet("/admin/AddImagesToAlbum")
public class AddImagesToAlbum extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		AlbumManager albumManager = new DbAlbumManager();
		ImageManager imageManager = new DbImageManager();
		int albumId = Integer.parseInt(request.getParameter("id"));
		Admin admin = (Admin) session.getAttribute("admin");

		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);

		try {
			Album album = albumManager.getAlbumById(albumId);
			List<FileItem> fileItems = upload.parseRequest(request);

			for (FileItem image : fileItems) {
				String imagePath = IMAGE_STORAGE + ALBUMS + album.getName() + "/" + image.getName();
				String previewPath = IMAGE_STORAGE + THUMBINALS + album.getName() + "/" + image.getName();

				imagePath = FileLocations.getValidFilePath(imagePath);
				previewPath = FileLocations.getValidFilePath(previewPath);

				File imageFile = new File(imagePath);

				imageManager
						.addImage(new Image(-1, albumId, imageFile.getName(), null, Timestamper.now(), admin.getId()));

				FileUtils.writeByteArrayToFile(imageFile, image.get());
				Thumbnails.of(imagePath).size(300, 400).toFile(previewPath);
			}
		} catch (Exception e) {
			throw new ServletException(e);
		}

		response.sendRedirect(request.getContextPath() + "/album.jsp?id=" + albumId);
	}
}
