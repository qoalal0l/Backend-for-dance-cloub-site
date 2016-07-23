package ua.dp.renessans.controller.album;

import static ua.dp.renessans.util.FileLocations.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;

import net.coobird.thumbnailator.Thumbnails;
import ua.dp.renessans.dao.DbAlbumManager;
import ua.dp.renessans.model.entity.Album;
import ua.dp.renessans.model.manager.AlbumManager;
import ua.dp.renessans.util.FileLocations;

@WebServlet("/admin/UpdateAlbumCover")
public class UpdateAlbumCover extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		AlbumManager albumManager = new DbAlbumManager();
		int albumId = Integer.parseInt(request.getParameter("id"));
		
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		
		FileItem image = null;
		String newImagePath = null;
		String oldImagePath = null;
		
		try {
			List<FileItem> fileItems = upload.parseRequest(request);
			image = fileItems.get(0);
			Album album = albumManager.getAlbumById(albumId);			
			newImagePath = IMAGE_STORAGE + COVERS + image.getName();
			oldImagePath = IMAGE_STORAGE + COVERS + album.getCoverName();	
			
			newImagePath = FileLocations.getValidFilePath(newImagePath);
			
			File oldImageFile = new File(oldImagePath);
			File newImageFile = new File(newImagePath);
			
			albumManager.updateAlbumCoverName(albumId, newImageFile.getName());

			if (album.getCoverName() != null){
				FileUtils.forceDelete(oldImageFile);				
			}
			FileUtils.writeByteArrayToFile(newImageFile, image.get());
			Thumbnails.of(newImagePath).size(300, 400).toFile(newImagePath);
		} catch (Exception e) {
			throw new ServletException(e);
		}

		
		response.sendRedirect(request.getContextPath() + "/gallery.jsp");
	}

}
