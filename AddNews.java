package ua.dp.renessans.controller.news;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ua.dp.renessans.dao.DbNewsManager;
import ua.dp.renessans.model.entity.Admin;
import ua.dp.renessans.model.entity.News;
import ua.dp.renessans.model.manager.ManagerException;
import ua.dp.renessans.model.manager.NewsManager;
import ua.dp.renessans.util.Timestamper;

@WebServlet("/admin/AddNews")
public class AddNews extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		HttpSession session = request.getSession();
		NewsManager newsManager = new DbNewsManager();

		String title = request.getParameter("news_title");
		String text = request.getParameter("news_text");
		Admin admin = (Admin) session.getAttribute("admin");
		int id = admin.getId();

		try {
			newsManager.addNews(new News(-1, title, text, Timestamper.now(), id, null, -1));
		} catch (ManagerException e) {
			throw new ServletException(e);
		}

		response.sendRedirect(request.getContextPath() + "/news.jsp");
	}

}
