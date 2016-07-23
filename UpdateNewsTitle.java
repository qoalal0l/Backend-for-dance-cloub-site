package ua.dp.renessans.controller.news;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.dp.renessans.dao.DbNewsManager;
import ua.dp.renessans.model.entity.Admin;
import ua.dp.renessans.model.entity.News;
import ua.dp.renessans.model.manager.ManagerException;
import ua.dp.renessans.model.manager.NewsManager;
import ua.dp.renessans.util.Timestamper;

@WebServlet("/admin/UpdateNewsTitle")
public class UpdateNewsTitle extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		NewsManager newsManager = new DbNewsManager();

		String title = request.getParameter("value");
		Admin admin = (Admin) request.getSession().getAttribute("admin");
		int id = Integer.parseInt(request.getParameter("id").replace("news_title", ""));
		int adminId = admin.getId();

		try {
			newsManager.updateNewsTitle(id, new News(-1, title, null, null, -1, Timestamper.now(), adminId));
		} catch (ManagerException e) {
			throw new ServletException(e);
		}

		response.getWriter().write(title);
	}

}
