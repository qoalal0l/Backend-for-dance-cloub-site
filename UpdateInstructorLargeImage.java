package ua.dp.renessans.controller.instructor;

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

import ua.dp.renessans.dao.DbInstructorManager;
import ua.dp.renessans.model.entity.Admin;
import ua.dp.renessans.model.entity.Instructor;
import ua.dp.renessans.model.manager.InstructorManager;
import ua.dp.renessans.util.FileLocations;
import ua.dp.renessans.util.Timestamper;
import static ua.dp.renessans.util.FileLocations.*;

@WebServlet("/admin/UpdateInstructorLargeImage")
public class UpdateInstructorLargeImage extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		InstructorManager instructorManager = new DbInstructorManager();
		Admin admin = (Admin) session.getAttribute("admin");

		int adminId = admin.getId();
		int instructorId = Integer.parseInt(request.getParameter("id"));

		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);

		FileItem image = null;
		String newImagePath = null;
		String oldImagePath = null;

		try {
			List<FileItem> fileItems = upload.parseRequest(request);
			image = fileItems.get(0);
			Instructor instructor = instructorManager.getInstructorById(instructorId);
			newImagePath = IMAGE_STORAGE + INSTRUCTOR_LARGE + image.getName();
			oldImagePath = IMAGE_STORAGE + INSTRUCTOR_LARGE + instructor.getLargeImg();

			newImagePath = FileLocations.getValidFilePath(newImagePath);

			File newImageFile = new File(newImagePath);
			File oldImageFile = new File(oldImagePath);

			instructorManager.updateInstructorLargeImage(instructorId,
					new Instructor(-1, null, null, null, null, newImageFile.getName(), null, -1, Timestamper.now(), adminId));

			FileUtils.forceDelete(oldImageFile);
			FileUtils.writeByteArrayToFile(newImageFile, image.get());
		} catch (Exception e) {
			throw new ServletException(e);
		}

		response.sendRedirect(request.getContextPath() + "/teacher.jsp?id=" + instructorId);
	}
}
