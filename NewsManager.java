package ua.dp.renessans.model.manager;

import java.util.List;

import ua.dp.renessans.model.entity.News;

public interface NewsManager {
	void addNews(News news) throws ManagerException;

	void deleteNews(int newsId) throws ManagerException;

	void updateNewsTitle(int newsId, News newNews) throws ManagerException;

	void updateNewsText(int newsId, News newNews) throws ManagerException;

	News getNewsById(int id) throws ManagerException;

	int getNewsCount() throws ManagerException;

	List<News> getAllNews() throws ManagerException;

	List<News> getNewsInRange(int from, int count) throws ManagerException;

}
