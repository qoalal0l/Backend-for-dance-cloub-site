package ua.dp.renessans.model.manager;

import java.util.List;

import ua.dp.renessans.model.entity.Image;

public interface ImageManager {
	void addImage(Image galleryImage) throws ManagerException;

	void deleteImage(int imageId) throws ManagerException;

	Image getImageById(int id) throws ManagerException;

	List<Image> getImagesInAlbum(int albumId) throws ManagerException;

	void updateImageDescription(int id, String description) throws ManagerException;

}
