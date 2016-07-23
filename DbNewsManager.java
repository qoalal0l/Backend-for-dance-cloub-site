package ua.dp.renessans.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;

import ua.dp.renessans.model.entity.News;
import ua.dp.renessans.model.manager.ManagerException;
import ua.dp.renessans.model.manager.NewsManager;

public class DbNewsManager implements NewsManager {

	private final static String QUERY_MYSQL_GET_ALL_NEWS = "SELECT * FROM news ORDER BY date_added DESC";
	private final static String QUERY_MYSQL_GET_NEWS_BY_ID = "SELECT * FROM news WHERE id = ?";
	private final static String QUERY_MYSQL_GET_NEWS_COUNT = "SELECT count(*) FROM news";
	private final static String QUERY_MYSQL_ADD_NEWS = "INSERT INTO news (title, text, date_added, adder_id) VALUES (?, ?, ?, ?)";
	private final static String QUERY_MYSQL_DELETE_NEWS = "DELETE FROM news WHERE id = ?";
	private final static String QUERY_MYSQL_GET_NEWS_IN_RANGE = "SELECT * FROM news ORDER BY date_added DESC LIMIT ?, ?";
	private final static String QUERY_MYSQL_UPDATE_NEWS_TEXT = "UPDATE news SET text = ?, date_last_modified = ?, modifier_id = ? WHERE id = ?";
	private final static String QUERY_MYSQL_UPDATE_NEWS_TITLE = "UPDATE news SET title = ?, date_last_modified = ?, modifier_id = ? WHERE id = ?";

	@Override
	public void addNews(News news) throws ManagerException {
		try (Connection con = DataBase.getConnection();
				PreparedStatement st = con.prepareStatement(QUERY_MYSQL_ADD_NEWS);) {
			st.setString(1, news.getTitle());
			st.setString(2, news.getText());
			st.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
			st.setInt(4, news.getAdderId());
			st.executeUpdate();
		} catch (SQLException | NamingException e) {
			throw new ManagerException(e);
		}
	}

	@Override
	public void deleteNews(int id) throws ManagerException {
		try (Connection con = DataBase.getConnection();
				PreparedStatement st = con.prepareStatement(QUERY_MYSQL_DELETE_NEWS);) {
			st.setInt(1, id);
			st.executeUpdate();
		} catch (SQLException | NamingException e) {
			throw new ManagerException(e);
		}

	}

	@Override
	public int getNewsCount() throws ManagerException {
		int count = 0;
		try (Connection con = DataBase.getConnection();
				PreparedStatement st = con.prepareStatement(QUERY_MYSQL_GET_NEWS_COUNT);
				ResultSet rs = st.executeQuery();) {
			rs.next();
			count = rs.getInt(1);
		} catch (SQLException | NamingException e) {
			throw new ManagerException(e);
		}

		return count;
	}

	@Override
	public List<News> getAllNews() throws ManagerException {
		List<News> news = new ArrayList<>();
		try (Connection con = DataBase.getConnection();
				PreparedStatement st = con.prepareStatement(QUERY_MYSQL_GET_ALL_NEWS);
				ResultSet rs = st.executeQuery();) {
			while (rs.next()) {
				news.add(new News(rs.getInt("id"), rs.getString("title"), rs.getString("text"),
						rs.getTimestamp("date_added"), rs.getInt("adder_id"), rs.getTimestamp("date_last_modified"),
						rs.getInt("modifier_id")));
			}
		} catch (SQLException | NamingException e) {
			throw new ManagerException(e);
		}

		return news;

	}

	@Override
	public List<News> getNewsInRange(int fromIndex, int count) throws ManagerException {
		List<News> news = new ArrayList<>();
		ResultSet rs = null;
		try (Connection con = DataBase.getConnection();
				PreparedStatement st = con.prepareStatement(QUERY_MYSQL_GET_NEWS_IN_RANGE);) {
			st.setInt(1, fromIndex);
			st.setInt(2, count);
			rs = st.executeQuery();
			while (rs.next()) {
				news.add(new News(rs.getInt("id"), rs.getString("title"), rs.getString("text"),
						rs.getTimestamp("date_added"), rs.getInt("adder_id"), rs.getTimestamp("date_last_modified"),
						rs.getInt("modifier_id")));
			}
		} catch (SQLException | NamingException e) {
			throw new ManagerException(e);
		} finally {
			try {
				rs.close();
			} catch (SQLException e) {
				throw new ManagerException(e);
			}
		}

		return news;
	}

	@Override
	public void updateNewsTitle(int newsId, News newNews) throws ManagerException {
		try (Connection con = DataBase.getConnection();
				PreparedStatement st = con.prepareStatement(QUERY_MYSQL_UPDATE_NEWS_TITLE);) {
			st.setString(1, newNews.getTitle());
			st.setTimestamp(2, newNews.getLastModified());
			st.setInt(3, newNews.getModifierId());
			st.setInt(4, newsId);
			st.executeUpdate();
		} catch (SQLException | NamingException e) {
			throw new ManagerException(e);
		}

	}

	@Override
	public void updateNewsText(int newsId, News newNews) throws ManagerException {
		try (Connection con = DataBase.getConnection();
				PreparedStatement st = con.prepareStatement(QUERY_MYSQL_UPDATE_NEWS_TEXT);) {
			st.setString(1, newNews.getText());
			st.setTimestamp(2, newNews.getLastModified());
			st.setInt(3, newNews.getModifierId());
			st.setInt(4, newsId);
			st.executeUpdate();
		} catch (SQLException | NamingException e) {
			throw new ManagerException(e);
		}

	}

	@Override
	public News getNewsById(int id) throws ManagerException {
		News news = null;
		ResultSet rs = null;
		try (Connection con = DataBase.getConnection();
				PreparedStatement st = con.prepareStatement(QUERY_MYSQL_GET_NEWS_BY_ID);) {
			st.setInt(1, id);
			rs = st.executeQuery();
			rs.next();
			news = new News(rs.getInt("id"), rs.getString("title"), rs.getString("text"), rs.getTimestamp("date_added"),
					rs.getInt("adder_id"), rs.getTimestamp("date_last_modified"), rs.getInt("modifier_id"));
		} catch (SQLException | NamingException e) {
			throw new ManagerException(e);
		} finally {
			try {
				rs.close();
			} catch (SQLException e) {
				throw new ManagerException(e);
			}
		}

		return news;
	}

}
