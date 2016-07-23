package ua.dp.renessans.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;

import ua.dp.renessans.model.entity.Album;
import ua.dp.renessans.model.manager.AlbumManager;
import ua.dp.renessans.model.manager.ManagerException;

public class DbAlbumManager implements AlbumManager {

	private final static String QUERY_MYSQL_GET_ALL_ALBUMS = "SELECT * FROM gallery_album";
	private final static String QUERY_MYSQL_ADD_ALBUM = "INSERT INTO gallery_album (name, description, cover_name, date_added, adder_id) VALUES (?, ?, ?, ?, ?)";
	private final static String QUERY_MYSQL_DELETE_ALBUM = "DELETE FROM gallery_album WHERE id = ?";
	private final static String QUERY_MYSQL_SET_COVER_IMAGE = "UPDATE gallery_album SET cover_name=? WHERE id=?";
	private final static String QUERY_MYSQL_GET_ALBUM_BY_ID = "SELECT * FROM gallery_album WHERE id = ?";
	private final static String QUERY_MYSQL_UPDATE_ALBUM_DESCRIPTION = "UPDATE gallery_album SET description=? WHERE id=?";
	private final static String QUERY_MYSQL_UPDATE_ALBUM_COVER_NAME = "UPDATE gallery_album SET cover_name=? WHERE id=?";

	@Override
	public void addAlbum(Album galleryAlbum) throws ManagerException {
		try (Connection con = DataBase.getConnection();
				PreparedStatement st = con.prepareStatement(QUERY_MYSQL_ADD_ALBUM);) {
			st.setString(1, galleryAlbum.getName());
			st.setString(2, galleryAlbum.getDescription());
			st.setString(3, galleryAlbum.getCoverName());
			st.setTimestamp(4, galleryAlbum.getAdded());
			st.setInt(5, galleryAlbum.getAdderId());
			st.executeUpdate();
		} catch (SQLException | NamingException e) {
			throw new ManagerException(e);
		}
	}

	@Override
	public void deleteAlbum(int id) throws ManagerException {
		try (Connection con = DataBase.getConnection();
				PreparedStatement st = con.prepareStatement(QUERY_MYSQL_DELETE_ALBUM);) {
			st.setInt(1, id);
			st.executeUpdate();
		} catch (SQLException | NamingException e) {
			throw new ManagerException(e);
		}

	}

	@Override
	public List<Album> getAllAlbums() throws ManagerException {
		List<Album> albums = new ArrayList<>();
		try (Connection con = DataBase.getConnection();
				PreparedStatement st = con.prepareStatement(QUERY_MYSQL_GET_ALL_ALBUMS);
				ResultSet rs = st.executeQuery();) {
			while (rs.next()) {
				albums.add(new Album(rs.getInt("id"), rs.getString("name"), rs.getString("description"),
						rs.getString("cover_name"), rs.getTimestamp("date_added"), rs.getInt("adder_id")));
			}
		} catch (SQLException | NamingException e) {
			throw new ManagerException(e);
		}

		return albums;

	}

	@Override
	public void setCoverImage(int albumId, String coverName) throws ManagerException {
		try (Connection con = DataBase.getConnection();
				PreparedStatement st = con.prepareStatement(QUERY_MYSQL_SET_COVER_IMAGE);) {
			st.setString(1, coverName);
			st.setInt(2, albumId);
			st.executeUpdate();
		} catch (SQLException | NamingException e) {
			throw new ManagerException(e);
		}
	}

	@Override
	public Album getAlbumById(int id) throws ManagerException {
		Album album = null;
		ResultSet rs = null;
		try (Connection con = DataBase.getConnection();
				PreparedStatement st = con.prepareStatement(QUERY_MYSQL_GET_ALBUM_BY_ID);) {
			st.setInt(1, id);
			rs = st.executeQuery();
			rs.next();
			album = new Album(rs.getInt("id"), rs.getString("name"), rs.getString("description"),
					rs.getString("cover_name"), rs.getTimestamp("date_added"), rs.getInt("adder_id"));

		} catch (SQLException | NamingException e) {
			throw new ManagerException(e);
		}

		return album;

	}

	@Override
	public void updateAlbumDescription(int albumId, String newDescription) throws ManagerException {
		try (Connection con = DataBase.getConnection();
				PreparedStatement st = con.prepareStatement(QUERY_MYSQL_UPDATE_ALBUM_DESCRIPTION);) {
			st.setString(1, newDescription);
			st.setInt(2, albumId);
			st.executeUpdate();
		} catch (SQLException | NamingException e) {
			throw new ManagerException(e);
		}
	}

	@Override
	public void updateAlbumCoverName(int albumId, String newCoverName) throws ManagerException {
		try (Connection con = DataBase.getConnection();
				PreparedStatement st = con.prepareStatement(QUERY_MYSQL_UPDATE_ALBUM_COVER_NAME);) {
			st.setString(1, newCoverName);
			st.setInt(2, albumId);
			st.executeUpdate();
		} catch (SQLException | NamingException e) {
			throw new ManagerException(e);
		}

	}

}
