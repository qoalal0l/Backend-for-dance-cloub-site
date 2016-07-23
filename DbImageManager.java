package ua.dp.renessans.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;

import ua.dp.renessans.model.entity.Image;
import ua.dp.renessans.model.manager.ImageManager;
import ua.dp.renessans.model.manager.ManagerException;

public class DbImageManager implements ImageManager {

	private final static String QUERY_MYSQL_GET_IMAGES_IN_ALBUM = "SELECT * FROM gallery_image WHERE album_id = ? ORDER BY date_added DESC";
	private final static String QUERY_MYSQL_ADD_IMAGE = "INSERT INTO gallery_image (album_id, name, date_added, adder_id) VALUES (?, ?, ?, ?)";
	private final static String QUERY_MYSQL_DELETE_IMAGE = "DELETE FROM gallery_image WHERE id = ?";
	private final static String QUERY_MYSQL_SET_DESCRIPTION = "UPDATE gallery_image SET description=? WHERE id=?";
	private final static String QUERY_MYSQL_GET_IMAGE_BY_ID = "SELECT * FROM gallery_image WHERE id = ?";

	@Override
	public void addImage(Image galleryImage) throws ManagerException {
		try (Connection con = DataBase.getConnection();
				PreparedStatement st = con.prepareStatement(QUERY_MYSQL_ADD_IMAGE);) {
			st.setInt(1, galleryImage.getAlbumId());
			st.setString(2, galleryImage.getName());
			st.setTimestamp(3, galleryImage.getAdded());
			st.setInt(4, galleryImage.getAdderId());
			st.executeUpdate();
		} catch (SQLException | NamingException e) {
			throw new ManagerException(e);
		}
	}

	@Override
	public void deleteImage(int id) throws ManagerException {
		try (Connection con = DataBase.getConnection();
				PreparedStatement st = con.prepareStatement(QUERY_MYSQL_DELETE_IMAGE);) {
			st.setInt(1, id);
			st.executeUpdate();
		} catch (SQLException | NamingException e) {
			throw new ManagerException(e);
		}
	}

	@Override
	public List<Image> getImagesInAlbum(int albumId) throws ManagerException {
		List<Image> news = new ArrayList<>();
		ResultSet rs = null;
		try (Connection con = DataBase.getConnection();
				PreparedStatement st = con.prepareStatement(QUERY_MYSQL_GET_IMAGES_IN_ALBUM);) {
			st.setInt(1, albumId);
			rs = st.executeQuery();
			while (rs.next()) {
				news.add(new Image(rs.getInt("id"), rs.getInt("album_id"), rs.getString("name"),
						rs.getString("description"), rs.getTimestamp("date_added"), rs.getInt("adder_id")));
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
	public void updateImageDescription(int imageId, String description) throws ManagerException {
		try (Connection con = DataBase.getConnection();
				PreparedStatement st = con.prepareStatement(QUERY_MYSQL_SET_DESCRIPTION);) {
			st.setString(1, description);
			st.setInt(2, imageId);
			st.executeUpdate();
		} catch (SQLException | NamingException e) {
			throw new ManagerException(e);
		}

	}

	@Override
	public Image getImageById(int id) throws ManagerException {
		Image image = null;
		ResultSet rs = null;
		try (Connection con = DataBase.getConnection();
				PreparedStatement st = con.prepareStatement(QUERY_MYSQL_GET_IMAGE_BY_ID);) {
			st.setInt(1, id);
			rs = st.executeQuery();
			rs.next();
			image = new Image(rs.getInt("id"), rs.getInt("album_id"), rs.getString("name"), rs.getString("description"),
					rs.getTimestamp("date_added"), rs.getInt("adder_id"));
		} catch (SQLException | NamingException e) {
			throw new ManagerException(e);
		} finally {
			try {
				rs.close();
			} catch (SQLException e) {
				throw new ManagerException(e);
			}
		}

		return image;
	}

}
