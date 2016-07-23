package ua.dp.renessans.model.manager;

import java.util.List;

import ua.dp.renessans.model.entity.Album;

public interface AlbumManager {
	void addAlbum(Album galleryAlbum) throws ManagerException;

	void deleteAlbum(int albumId) throws ManagerException;

	void setCoverImage(int albumId, String coverName) throws ManagerException;

	List<Album> getAllAlbums() throws ManagerException;

	Album getAlbumById(int id) throws ManagerException;

	void updateAlbumDescription(int albumId, String newDescription) throws ManagerException;

	void updateAlbumCoverName(int albumId, String newCoverName) throws ManagerException;
}
