package ua.dp.renessans.controller.news;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.dp.renessans.dao.DbNewsManager;
import ua.dp.renessans.model.manager.ManagerException;
import ua.dp.renessans.model.manager.NewsManager;

@WebServlet("/admin/DeleteNews")
public class DeleteNews extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		NewsManager newsManager = new DbNewsManager();

		int id = Integer.parseInt(request.getParameter("id"));

		try {
			newsManager.deleteNews(id);
		} catch (ManagerException e) {
			throw new ServletException(e);
		}

		response.sendRedirect(request.getContextPath() + "/news.jsp");
	}

}
